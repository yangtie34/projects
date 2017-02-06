package cn.gilight.dmm.teaching.dao.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.BhrConstant;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.SmartDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Repository("smartDao")
public class SmartDaoImpl implements SmartDao {
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	public static final double XB_GPA = Constant.XB_GPA;
	public static final double XB_SCORE = Constant.XB_SCORE;
	public static final String SYDID = "0";
	public static final String FROMSQL = "select div.id as id,div.name_ as name,div.pid,div.level_ as cc,div.path_ as qxm from t_code_admini_div div where ";

	public static final String[] Origin_Name_Sxq = {"市辖区","县"};
	@Override
	public int queryGradeLen(List<String> deptList,List<AdvancedParam> stuAdvancedList,int schoolYear) {
		String stu = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
		String sql = " select max(stu.length_schooling) as value from ("+stu+") stu ";
		Map<String,Object> map = baseDao.queryMapInLowerKey(sql);
		int max =5;
		if(map==null||map.isEmpty()||MapUtils.getIntValue(map, "value")==0||MapUtils.getIntValue(map, "value")>5){
		}else{
			max = MapUtils.getIntValue(map, "value");
		}
		return max;
	}
	@Override
	public int queryYearAndTerm(){
		int schoolYear = EduUtils.getSchoolYear4();
		String xn ="select min(substr(stu.school_year,0,4)) as xn from t_stu_score stu group by stu.school_year";
		Map<String,Object> xnmap = baseDao.queryMapInLowerKey(xn);
		int year = schoolYear;
		if (xnmap==null||xnmap.isEmpty()||MapUtils.getIntValue(xnmap, "xn")==0){
		}else{
			year = MapUtils.getIntValue(xnmap, "xn");
		}
		return year;
	}
	@Override
	public List<Map<String,Object>> queryEdu(List<String> deptList){
		String stu = businessDao.getStuSql(null, deptList, new ArrayList<AdvancedParam>());
		String sql = "select stu.edu_id as code,edu.order_ as pxh,edu.name_ as name  from ("
				+ stu
				+ ") stu inner join t_code_education edu "
				+ " on stu.edu_id = edu.id group by stu.edu_id,edu.order_,edu.name_ order by edu.order_,stu.edu_id ";
		return  baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> queryGpa(Integer schoolYear,String term,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stu =getStuSql(schoolYear,term,deptList,stuAdvancedList);
		String sex = "select * from t_code where code_type='SEX_CODE'";
		String sql ="select sb.*,rownum r from (select stu.enroll_grade as grade,stu.stu_id as code,stu.name_ as name,teach.name_ as yx, "
				+ "  tea.name_ as zy,sex.name_ as sex,to_char(stu.gpa,'fm9990.0') as gpa from ("+stu+") stu "
				+ " left join ("+sex+") sex on stu.sex_code = sex.code_ "
			    + " left join t_code_dept_teach teach on stu.dept_id = teach.id"
			    + " left join t_code_dept_teach tea on stu.major_id = tea.id"
				+ " where stu.gpa>="+XB_GPA+" order by stu.gpa desc) sb";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> queryFrom(Integer schoolYear,String term,String xzqh,Boolean updown,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stu =getStuSql(schoolYear,term,deptList,stuAdvancedList);
		Map<String, String> map1 = queryCode(xzqh,updown);//查询出来应该截取的学生生源地id
		String str3 = map1.get("str3");//下层或者上层节点信息
//		String str = "select t.*,rownum as r from (select "
//				+ str1
//				+ " as id,div.name as name,count(*) as value,div.cc,div.pid from ("
//				+ stu + ") " + " stu inner join (";
//		String str2 = ") div on " + str1 + " = div.id where stu.gpa>="+XB_GPA+" group by "
//				+ " " + str1
//				+ ",div.name,div.cc,div.pid order by count(*) desc) t ";
//		String sql = str + str3 + str2;//查询出要展示的节点的学生人数，以及节点的信息
		String sql = "select t.*,rownum as r from (select c.divid as id,c.divid as code,c.name,c.cc,c.fjd as pid,count(0) as value "
				+ " from ("+stu+") stu,"
				+ " (select a.id as divid,a.name,a.cc,a.pid as fjd,b.* from ("+str3+") a ,t_code_admini_div b "
				+ " where a.qxm = substr(b.path_,0,length(a.qxm)) ) c "
				+ " where stu.stu_origin_id = c.id and stu.gpa>="+XB_GPA+" group by c.divid,c.name,c.cc,c.fjd order by count(0) desc) t";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		String pid = map1.get("pid");
		String relpid = map1.get("relpid");
		List<Map<String, Object>> list1 = baseDao
				.queryListInLowerKey(queryNextDiv(relpid));
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list1.size(); i++) {//补全各地区的信息
			Map<String, Object> map2 = list1.get(i);
			String code = MapUtils.getString(map2, "id");
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Map<String, Object> map3 = list.get(j);
					String code1 = MapUtils.getString(map3, "id");
					if (code != null && code1 != null && code1.equals(code)) {
						result.add(map3);
					}
				}
			} else {
				map2.put("value", 0);
				result.add(map2);
			}
			if (result.size() < i + 1) {
				map2.put("value", 0);
				result.add(map2);// 如果放入过后result的长度小于list1的长度，就用list1的结果填充result
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maptype",MapUtils.getString(baseDao.queryMapInLowerKey(queryNowDiv(pid)), "name"));//获取当前展示的地图的类型以及ID放到list的第一位
		map.put("id", pid);
		result.add(0, map);
	    return result;
	}
	@Override
	public String queryStuListSql(Integer schoolYear,String term,String xzqh,Boolean updown,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		Map<String, String> map = queryCode(xzqh,updown);//查询出来应该截取的学生生源地id
		String pid = MapUtils.getString(map, "pid");
		List<AdvancedParam> advList = new ArrayList<AdvancedParam>();
		if (stuAdvancedList != null){advList.addAll(stuAdvancedList);}
		AdvancedParam sydAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, pid);
		advList = AdvancedUtil.add(advList, sydAdp);
		String stu =getStuSql(schoolYear,term,deptList,advList);
		String stusql = "select * from ("+stu+") stu where stu.gpa>="+XB_GPA+" ";
		String sql1 = "select x.no,x.enroll_grade,x.name,x.sexmc,"
				+ "x.shengmc||x.shimc||x.xianmc as jg,x.deptmc,x.majormc,x.classmc from ("+businessDao.getStuDetailSql(stusql)+") x order by  x.enroll_grade desc,x.deptid,x.majorid,x.classid,x.shengmc||x.shimc||x.xianmc";
		return sql1;
	}
	@Override
	public Map<String,Object> queryTable(Integer schoolYear,String term,List<String> deptList,int pagesize,int index,String column,Boolean asc,String type,List<AdvancedParam> stuAdvancedList){
		String stu =getStuSql(schoolYear,term,deptList,stuAdvancedList);
		String str = "";String tab =" t_code_dept_teach ";String x =" teach.id ";
		if(type == null || "YX".equals(type)){
			str = " stu.dept_id = teach.id ";
		}else if("ZY".equals(type)){
			str = " stu.major_id = teach.id ";
		}else if("BJ".equals(type)){
			x=" teach.no_ ";
			str = " stu.class_id = teach.no_ ";
			tab =" t_classes ";
		}else if("SUBJECT".equals(type)){
			str = " stu.major_id = teach.dept ";
			tab = "(select tp.name_, tp.id id,t.dept from t_code_subject_degree tp,"
				+ "(select distinct(t.id),t.name_,t.path_,dept.id dept from t_code_subject_degree t,"
				+ "t_code_dept_teach dept where  t.id=dept.subject_id)t where tp.istrue=1 "
				+ "and substr(t.path_,1,4)=tp.path_ order by tp.level_,tp.order_,tp.code_)";
		}
		String sql = "select "+x+" as id,teach.name_ as name,round(avg(stu.gpa),1) as avg,count(*) as value"
				+ "  from ("+stu+") stu inner join "+tab+" teach"
				+ " on "+str+" ";
		String xb = sql+" where stu.gpa >="+XB_GPA+" group by teach.name_,"+x+"";
		String all = sql+" group by teach.name_,"+x+"";
		String zb = "select a.id,a.name,round(b.value/a.value*100,2) as lv from ("+all+") a "
				+ " inner join ("+xb+") b on a.id=b.id ";
		String jg = "select a.id,a.name,a.avg,b.value,c.lv from ("+all+") a inner join ("+xb+") b on a.id = b.id"
				+ " inner join ("+zb+") c on a.id = c.id ";
		List<Map<String,Object>> list = queryBeforeAvg(stu,type,baseDao.queryListInLowerKey(all));
		List<Map<String,Object>> result = baseDao.queryListInLowerKey(jg);
		index = index<0 ? 1 : index;
//		int pageCount = new Double(Math.ceil(MathUtils.getDivisionResult(list.size(), pagesize, 1))).intValue();
		if(!column.equals("avg1")){
		sortList(result,column,asc);
		}
		int size = result.size();
		int start = (index-1)*pagesize; start = start>size ? size : start;
		int end = index*pagesize; end = end>size ? size : end;
		if(!column.equals("avg1")){
			sortList(result,column,asc);
			result = result.subList(start, end);
			}
		for(int i=0;i<result.size();i++){
			Map<String,Object> temp = result.get(i);
			String id = MapUtils.getString(temp, "id");
			for(int j=0;j<list.size();j++){
				Map<String,Object> temp1 = list.get(j);
				String id1 = MapUtils.getString(temp1, "id");
				if(id != null && id1 != null && id.equals(id1)){
					result.get(i).put("avg1", MapUtils.getDoubleValue(temp1, "avg1"));
				}
			}
		}
		if (column.equals("avg1")){
	    sortList(result,column,asc);
		result = result.subList(start, end);
		}
		int i=((index-1)*pagesize+1);
		for(int j=0;j<result.size();j++){
			result.get(j).put("r", i);
			i++;
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("total",size);
		return map;
	}
	@Override
	public Map<String,Object> queryCounts(List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stu = businessDao.getStuSql(deptList, stuAdvancedList);
		String gpa = " gpa.gpa >= "+XB_GPA;
		String sql = "select substr(gpa.school_year,0,4) as name,nvl(count(0),0) as value from t_stu_score_gpa gpa,("+stu+") stu where gpa.school_year is not null and gpa.term_code is null and "
				+ " gpa.stu_id = stu.no_ and gpa.gpa_code = '"+ Constant.SCORE_GPA_BASE_CODE+"' group by substr(gpa.school_year,0,4)";
		String xbsql ="select substr(gpa.school_year,0,4) as name,nvl(count(0),0) as value from t_stu_score_gpa gpa,("+stu+") stu where gpa.school_year is not null and gpa.term_code is null and "
				+ " gpa.stu_id = stu.no_ and "+gpa+" and gpa.gpa_code = '"+ Constant.SCORE_GPA_BASE_CODE+"' group by substr(gpa.school_year,0,4)";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("xb", baseDao.queryListInLowerKey(xbsql));
		map.put("all",baseDao.queryListInLowerKey(sql));
		return map;
	}
	@Override
	public Map<String,Object> queryTableCourse(Integer schoolYear,String term,List<String> deptList,int pagesize,int index,String column,Boolean asc,String type,List<AdvancedParam> stuAdvancedList){
		String year =schoolYear+"-"+(schoolYear+1);
		String stuSql = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
		String scoreSql = businessDao.getStuScoreSql(stuSql, year, term);
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> result = new ArrayList<>();
		list = (list!=null&&!list.isEmpty()) ? list : queryCourseList(stuSql,  year, term);
		for(Map<String, Object> map : list){
			String id = MapUtils.getString(map, "id");
			String avgs = "select nvl(round(avg(score),1),0) as avg from ("+scoreSql+")t where t.coure_code='"+id+"'";
			String counts = "select nvl(count(*),0) as value from ("+scoreSql+")t where t.coure_code='"+id+"' and t.score >="+XB_SCORE;
			String alls = "select nvl(count(*),0) as value from ("+scoreSql+")t where t.coure_code='"+id+"'";
			int count = baseDao.queryForInt(counts);int all= baseDao.queryForInt(alls);
			double lv = MathUtils.get2Point(MathUtils.getDivisionResult(count, all, 4)*100);
			double lv1 = MathUtils.getDivisionResult(all, 10, 0);
			String avg1s = "select nvl(round(avg(score),1),0) as avg1 from (select a.*,rownum r from (select score from ("+scoreSql+")t where t.coure_code='"+id+"' order by score desc) a) where r>="+lv1;
			Double avg = MapUtils.getDoubleValue(baseDao.queryMapInLowerKey(avgs), "avg");
			Double avg1 = MapUtils.getDoubleValue(baseDao.queryMapInLowerKey(avg1s), "avg1");
			Map<String, Object> temp = new HashMap<>();
			temp.put("avg",avg);
			temp.put("value",count);
			temp.put("lv",lv);
			temp.put("avg1",avg1);
			temp.put("name", MapUtils.getString(map, "name"));
			result.add(temp);
		}
		sortList(result,column,asc);
		index = index<0 ? 1 : index;
		int size = result.size();
		int start = (index-1)*pagesize; start = start>size ? size : start;
		int end = index*pagesize; end = end>size ? size : end;
		result = result.subList(start, end);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("total",size);
		return map;
	}
	@Override
	public Map<String, Object> queryTableTeach(Integer schoolYear,String term,List<String> deptList,int pagesize,int index,String column,Boolean asc,String type,List<AdvancedParam> stuAdvancedList){
		String baseStuSql = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
		String year =schoolYear+"-"+(schoolYear+1);
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> result = new ArrayList<>();
		list = (list!=null&&!list.isEmpty()) ? list : queryTeachList(baseStuSql, year, term);
		for(Map<String, Object> map : list){
			String stuSql = businessDao.getStuSqlByTeachIdSql(MapUtils.getString(map, "id"), baseStuSql);
			String scoreSql = businessDao.getStuScoreSql(stuSql, year, term);
			String avgs = "select nvl(round(avg(score),1),0) as avg from ("+scoreSql+")t ";
			String counts = "select nvl(count(*),0) as value from ("+scoreSql+")t where t.score >="+XB_SCORE;
			String alls = "select nvl(count(*),0) as value from ("+scoreSql+")t ";
			int count = baseDao.queryForInt(counts);int all= baseDao.queryForInt(alls);
			double lv = MathUtils.get2Point(MathUtils.getDivisionResult(count, all, 4)*100);
			double lv1 = MathUtils.getDivisionResult(all, 10, 0);
			String avg1s = "select nvl(round(avg(score),1),0) as avg1 from (select a.*,rownum r from (select score from ("+scoreSql+")t order by score desc) a) where r>="+lv1;
			Double avg = MapUtils.getDoubleValue(baseDao.queryMapInLowerKey(avgs), "avg");
			Double avg1 = MapUtils.getDoubleValue(baseDao.queryMapInLowerKey(avg1s), "avg1");
			Map<String, Object> temp = new HashMap<>();
			temp.put("avg",avg);
			temp.put("value",count);
			temp.put("lv",lv);
			temp.put("avg1",avg1);
			temp.put("name", MapUtils.getString(map, "name"));
			result.add(temp);
		}
		sortList(result,column,asc);
		index = index<0 ? 1 : index;
		int size = result.size();
		int start = (index-1)*pagesize; start = start>size ? size : start;
		int end = index*pagesize; end = end>size ? size : end;
		result = result.subList(start, end);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("total",size);
		return map;
	}
	@Override
	public List<Map<String,Object>> queryRadar(Integer schoolYear,String term,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stuSql = getStuSql(schoolYear,term,deptList,stuAdvancedList);
		String year = String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
		String gpa = " and gpa.gpa > "+XB_GPA;
     	String xbSql = stuSql+gpa;
     	List<Map<String,Object>> result = new ArrayList<>();
     	result.add(queryRadarMap(xbSql,"学霸",year,term));
    	result.add(queryRadarMap(stuSql,"在校学生",year,term));
		return result;
	}
	@Override
	public List<Map<String,Object>> queryRadar(Integer schoolYear,String term,String stuNo,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stuSql = getStuSql(schoolYear,term,deptList,stuAdvancedList);
		String year = String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
		String gpa = " and gpa.gpa > "+XB_GPA;
     	String xbSql = stuSql+gpa;
     	String stu = stuSql+" and gpa.stu_id = '"+stuNo+"'";
     	String sql = "select t.name_ from t_stu t where t.no_ = '"+stuNo+"'";
     	String name = baseDao.queryForString(sql);
     	List<Map<String,Object>> result = new ArrayList<>();
     	result.add(queryRadarMap(xbSql,"学霸",year,term));
    	result.add(queryRadarMap(stuSql,"在校学生",year,term));
    	result.add(queryRadarMap(stu,name,year,term));
		return result;
	}
	@Override
	public double queryXbGpa(){
		double xb_gpa = XB_GPA;
		return  xb_gpa;
	}
	@Override
	public Map<String,Object> getDormStu(String stuNo){
		String sql = "select c.name_ as ly,b.name_ as ss,a.berth_name as cw from t_dorm_berth_stu t,t_dorm_berth a,"
				+ " t_dorm b,(select * from t_dorm where level_ = 1) c "
				+ " where t.berth_id = a.id and t.stu_id = '"+stuNo+"' "
				+ " and a.dorm_id = b.id and substr(b.path_,0,length(c.path_)) = c.path_";
		return baseDao.queryMapInLowerKey(sql);
	}
	@Override
	public Map<String,Object> getCost(Integer schoolYear,String term,String stuNo,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stuSql = getStuSql(schoolYear,term,deptList,stuAdvancedList);
		String gpa = " and gpa.gpa > "+XB_GPA;
     	String xbSql = stuSql+gpa;
     	String stu = stuSql+" and gpa.stu_id = '"+stuNo+"'";
     	Double allAvg = getCostDouble(stuSql, schoolYear, term, true,0d),
     	       gpaAvg = getCostDouble(xbSql, schoolYear, term, true,0d),
     	       stuAvg = getCostDouble(stu, schoolYear, term, true,0d),
     	       highAll= getCostDouble(stuSql, schoolYear, term, false, stuAvg),
     	       highGpa= getCostDouble(xbSql, schoolYear, term, false, stuAvg);
     	Map<String,Object> map  =new HashMap<String, Object>();
     	String sql= getCostByStuSql(stuSql, schoolYear, term, false);
     	String str = "select count(0) from ("+sql+") a where a.value > '"+stuAvg+"'";
     	int rank = baseDao.queryForInt(str)+1;
     	map.put("all", allAvg);map.put("xb", gpaAvg);map.put("me", stuAvg);
     	map.put("highall", highAll);map.put("highxb", highGpa);map.put("rank", rank);
     	return map;
	}
	@Override
	public Map<String,Object> getBorrow(Integer schoolYear,String term,String stuNo,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stuSql = getStuSql(schoolYear,term,deptList,stuAdvancedList);
		String gpa = " and gpa.gpa > "+XB_GPA;
     	String xbSql = stuSql+gpa;
     	String stu = stuSql+" and gpa.stu_id = '"+stuNo+"'";
     	Double allAvg = getBorrowDouble(stuSql, schoolYear, term, true,0d),
     	       gpaAvg = getBorrowDouble(xbSql, schoolYear, term, true,0d),
     	       stuAvg = getBorrowDouble(stu, schoolYear, term, true,0d),
     	       highAll= getBorrowDouble(stuSql, schoolYear, term, false, stuAvg),
     	       highGpa= getBorrowDouble(xbSql, schoolYear, term, false, stuAvg);
     	Map<String,Object> map  =new HashMap<String, Object>();
     	String sql= getBorrowByStuSql( schoolYear, term,stuSql, false);
     	String str = "select count(0) from ("+sql+") a where a.value > '"+stuAvg+"'";
     	int rank = baseDao.queryForInt(str)+1;
     	map.put("all", allAvg);map.put("xb", gpaAvg);map.put("me", stuAvg);
     	map.put("highall", highAll);map.put("highxb", highGpa);map.put("rank", rank);
     	return map;
	}
	@Override
	public Map<String,Object> getScoreGk(Integer schoolYear,String term,String stuNo,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stuSql = getStuSql(schoolYear,term,deptList,stuAdvancedList);
		String gpa = " and gpa.gpa > "+XB_GPA;
     	String xbSql = stuSql+gpa;
     	String stu = stuSql+" and gpa.stu_id = '"+stuNo+"'";
     	Double allAvg = getScoreDouble(stuSql,  true,0d),
      	       gpaAvg = getScoreDouble(xbSql, true,0d),
      	       stuAvg = getScoreDouble(stu, true,0d),
      	       highAll= getScoreDouble(stuSql,false, stuAvg),
      	       highGpa= getScoreDouble(xbSql, false, stuAvg);
      	Map<String,Object> map  =new HashMap<String, Object>();
      	String sql= getScoreSql(stuSql, false);
      	String str = "select count(0) from ("+sql+") a where a.value > '"+stuAvg+"'";
      	int rank = baseDao.queryForInt(str)+1;
      	map.put("all", allAvg);map.put("xb", gpaAvg);map.put("me", stuAvg);
      	map.put("highall", highAll);map.put("highxb", highGpa);map.put("rank", rank);
      	return map;
	}
	@Override
	public List<Map<String,Object>> getScoreTable(Integer schoolYear,String term,String stuNo){
		String year = String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
//	    String stuSql = "select * from t_stu where no_ = '"+stuNo+"'";
		String sql = "select b.name_ as mc,case when t.hierarchical_score_code is not null "
				+ " then hier.centesimal_score else t.centesimal_score end cj,c.credit xf from t_stu_score t inner join t_stu h on t.stu_id = h.no_  "
				+ " inner join  t_course b on t.coure_code = b.id left join  t_code_score_hierarchy hier on t.hierarchical_score_code = hier.code_ left join "
				+ " t_course_arrangement_plan c on t.school_year = c.school_year and c.term_code = t.term_code and "
				+ " t.coure_code = c.course_code and c.class_id = h.class_id "
				+ "  where t.stu_id = '"+stuNo+"' and t.school_year ='"+year+"' and t.term_code ='"+term+"' "
				+ " ";
	    return baseDao.queryListInLowerKey(sql);
	}
	private String getScoreSql(String stuSql,Boolean isAvg){
		String str = isAvg ? "nvl(round(avg(stu.gpa),1),0) ":" stu.gpa ";
		String sql = "select "+str+" as value from ("+stuSql+") stu ";
		return sql;
	}
	private Double getScoreDouble(String stuSql,Boolean isAvg,Double val){
		String sql = getScoreSql(stuSql,isAvg);
		List<Double> list = baseDao.queryForListDouble(sql);
		Double value = 0d;
		if (isAvg){
			value = (list!=null&& !list.isEmpty())? list.get(0):0d;
		}else{
			int allCount = baseDao.queryForCount(sql);
			String str = "select count(0) from ("+sql+") a where a.value < '"+val+"'";
			int count = baseDao.queryForInt(str);
			value = MathUtils.get2Point(MathUtils.getDivisionResult(count, allCount, 4)*100);
		}
		return value;
	}
	private String getBorrowByStuSql(Integer schoolYear,String term,String stuSql,Boolean isavg){
		String year = String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
		String str = "select start_date as start_,end_date as end_ from t_school_start where school_year = '"+year+"'"
				+ " and term_code = '"+term+"'";
		Map<String, Object> map = baseDao.queryMapInLowerKey(str);
		String[] ary = EduUtils.getTimeQuantum(year, term);
		String start = ary[0],end = ary[1];
		if (map!= null && !map.isEmpty()){
		 start = MapUtils.getString(map, "start_");
		 end = MapUtils.getString(map, "end_");
		}
		start = start.substring(0,7);end = end.substring(0,7);
		String sss = isavg? " nvl(round(avg(value),2),0) ": " value ";
		String sql = "select "+sss+" as value from (select * from (select t.stu_id,sum(borrow_num) as value from tl_book_borrow_stu_month t,("+stuSql+") stu where t.year_||'-'||t.month_ "
				+ " between '"+start+"' and '"+end+"' and t.stu_id = stu.no_ group by t.stu_id) ) ";
		return sql;
	}
	private Double getBorrowDouble(String stuSql,Integer schoolYear, String term, Boolean isavg,Double val){
		String sql = getBorrowByStuSql(schoolYear, term, stuSql,isavg);
		List<Double> list = baseDao.queryForListDouble(sql);
		Double value = 0d;
		if (isavg){
			value = (list!=null&& !list.isEmpty())? list.get(0):0d;
		}else{
			int allCount = baseDao.queryForCount(sql);
			String str = "select count(0) from ("+sql+") a where a.value < '"+val+"'";
			int count = baseDao.queryForInt(str);
			value = MathUtils.get2Point(MathUtils.getDivisionResult(count, allCount, 4)*100);
		}
		return value;
	}
	private String getCostByStuSql(String stuSql,Integer schoolYear,String term,Boolean isavg){
		String str = isavg ? " nvl(round(avg(t.sum_money ),2),0) ":" t.sum_money ";
		String year = String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
		String sql = "select "+str+" as value from t_stu_abnormal_term t,("+stuSql+") stu where t.stu_id = stu.no_ "
				+ " and t.school_year = '"+year+"' and t.term_code ='"+term+"'";
		return sql;
	}
	private Double getCostDouble(String stuSql,Integer schoolYear, String term, Boolean isavg,Double val){
		String sql = getCostByStuSql(stuSql, schoolYear, term, isavg);
		List<Double> list = baseDao.queryForListDouble(sql);
		Double value = 0d;
		if (isavg){
			value = (list!=null&& !list.isEmpty())? list.get(0):0d;
		}else{
			int allCount = baseDao.queryForCount(sql);
			String str = "select count(0) from ("+sql+") a where a.value < '"+val+"'";
			int count = baseDao.queryForInt(str);
			value = MathUtils.get2Point(MathUtils.getDivisionResult(count, allCount, 4)*100);
		}
		return value;
	}
	private List<Map<String,Object>> queryBeforeAvg(String stu,String type,List<Map<String,Object>> list){
		String x= " ",y=" ";
		if("YX".equals(type)){
			y="stu.dept_id";
		}else if("ZY".equals(type)){
			y="stu.major_id";
		}else if("BJ".equals(type)){
			y="stu.class_id";
		}else if("SUBJECT".equals(type)){
			x="inner join (select tp.name_ name, tp.id id,t.dept from t_code_subject_degree tp,"
				+ "(select distinct(t.id),t.name_,t.path_,dept.id dept from t_code_subject_degree t,"
				+ "t_code_dept_teach dept where  t.id=dept.subject_id)t where tp.istrue=1 "
				+ "and substr(t.path_,1,4)=tp.path_ order by tp.level_,tp.order_,tp.code_) xk on xk.dept = stu.major_id ";
			y=" xk.id ";
		}
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(int i=0;i<list.size();i++){
			Map<String,Object> temp = list.get(i);
		    String id = MapUtils.getString(temp, "id");
		    double count = MathUtils.getDivisionResult(MapUtils.getIntValue(temp, "value"), 10, 0);
		    String sql ="select a.id,round(avg(a.gpa),1) as avg1 from (select sb.* from(select t.*,rownum r from (select "+y+" as id,stu.gpa from ("+stu+") stu "
		    		+x+" where "+y+" = '"+id+"' order by stu.gpa desc) t ) sb where sb.r <"+count+") a group by a.id";
		    Map<String,Object> map = baseDao.queryMapInLowerKey(sql);
		    result.add(map);
		}
		return result;
	}
	/**
	 * 获取下层地区节点信息
	 */
	private String queryNextDiv(String Id) {
		String sql = FROMSQL + " div.pid in (" + businessDao.formatInSql(Id) + ")";
		return sql;
	}
	/**
	 * 获取当前节点信息
	 */
	private String queryNowDiv(String Id) {
		String sql = FROMSQL + " div.id ='" + Id + "'";
		return sql;
	}
	/**
	 * 获取上层地区节点信息
	 */
	private String queryLastDiv(String Id) {
		String sql1 = queryNowDiv(Id);
		Map<String, Object> map = baseDao.queryMapInLowerKey(sql1);
		String code = map == null ? "0" : MapUtils.getString(map, "pid");
		String sql = FROMSQL + " div.pid ='0'";
		if (code.equals("0")) {
		} else {
			String sql2 = FROMSQL + " div.id = " + code;
			Map<String, Object> map1 = baseDao.queryMapInLowerKey(sql2);
			String dm = MapUtils.getString(map1, "pid");
			sql = FROMSQL + " div.pid = " + dm;
		}
		return sql;
	}
	/**
	 * 获取要展示的节点层次以及生源地id以及节点的信息查询sql
	 */
	private Map<String, String> queryCode(String Id,Boolean updown) {
		String str3 = queryNextDiv(Id);
		if (!updown
				|| baseDao.queryListInLowerKey(queryNextDiv(Id)).size() == 0) {
			str3 = queryLastDiv(Id);
		}
		List<Map<String, Object>> From = baseDao.queryListInLowerKey(str3);
		String pid = From.size() == 0 ? "0" : MapUtils.getString(From.get(0), "pid");
	    int count = 0;String id1 ="", id2 = "",relpid=pid;
			if (From.size()== 2){
				for (Map<String, Object> temp : From ){
					String id = MapUtils.getString(temp, "id");
			    	String name = MapUtils.getString(temp, "name");
			    	if (Origin_Name_Sxq[0].equals(name)){
			    		id1 = id;
			    		count++;
			    	}
			    	if (Origin_Name_Sxq[1].equals(name)){
			    		id2 = id;
			    		count++;
			    	}
			    }
		    }
			if (count == 2){
				str3 = queryNextDiv(id1)+" union all "+queryNextDiv(id2);
				relpid= id1+","+id2;
				if (!updown
						|| baseDao.queryListInLowerKey(str3).size() == 0) {
					str3 = queryLastDiv(id1);
					List<Map<String, Object>> From1 = baseDao.queryListInLowerKey(str3);
					pid = From1.size() == 0 ? getChinaId() : MapUtils.getString(From1.get(0), "pid");
					relpid = pid;
				}
				
			}
		Map<String, String> map = new HashMap<String, String>();
		map.put("str3", str3);
		map.put("pid", pid);
		map.put("relpid", relpid);
		return map;
	}
	@Override
	public Map<String,Object> queryTimeline(String schoolyear,String term,BhrConstant.BHRTIME_Table table,String season){
		String sql = " select t.value_ from "+table.getTable()+" t where t.type_= '"+table.getType()+"' and "
				+ " t.stu_type = '"+BhrConstant.T_STU_BEHTIME_STUTYPE_SMART+"' and t.season = '"+season+"'"
						+ " and t.school_year ='"+schoolyear+"' and t.term_code ='"+term+"'";
		Map<String,Object> result  = baseDao.queryMapInLowerKey(sql);
		if(result == null){
			return null;
		}
		result.put("type",table.getType());
		result.put("name",table.getName());
		return result;
	}
	@Override
	public String getStuSql(Integer schoolYear,String termCode,List<String> deptList,
			List<AdvancedParam> stuAdvancedList) {
		String year = schoolYear==null?null:String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
		String stusql = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
		String stugpasql = businessDao.getStuGpaSql(stusql, year, termCode, Constant.SCORE_GPA_BASE_CODE);
		String sql ="select * from ("+stugpasql+") gpa,("+stusql+") stu where stu.no_ = gpa.stu_id";
		return sql;
	}
	/**
	 * List 排序
	 * @param list 列表
	 * @param order_column 排序字段
	 * @param asc 正序
	 */
	@Override
	public void sortList(List<Map<String, Object>> list, final String order_column, final boolean asc){
		Collections.sort(list, new Comparator<Map<String, Object>>(){
			Double d1 = null, d2 = null;
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int k = 0;
				d1 = MapUtils.getDouble(o1, order_column);
				d2 = MapUtils.getDouble(o2, order_column);
				if(d1!=null && (d1>d2 || d2==null)){ k = 1; }
				else if(d1==null || (d1<d2 && d2!=null)){ k = -1; }
				if(!asc){ k = -k; }
				return k;
			}
		});
	}
	/**
	 * 查询课程list
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> queryCourseList(String stuSql, String schoolYear, String termCode){
		String scoreSql = businessDao.getStuScoreSql(stuSql, schoolYear, termCode);
		String sql = "select distinct(score.coure_code) id, course.name_ name from ("+scoreSql+")score,t_course course"
				+ " where score.coure_code=course.code_";
		return baseDao.queryListInLowerKey(sql);
	}
	/**
	 * 查询教师list
	 * @param stuSql 学生sql
	 * @param schoolYear学年 
	 * @param termCode 学期
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> queryTeachList(String stuSql, String schoolYear, String termCode){
		String teachClassSql = businessDao.getTeachClassIdSql(stuSql);
		String sql = "select t.tea_id id, tea.name_ name from"
				+ " (select distinct(arr.tea_id) from T_COURSE_ARRANGEMENT arr, ("+teachClassSql+") teachClass "
				+ " where arr.teachingclass_id = teachClass.id "
				+ " and arr.school_year='"+schoolYear+"' and arr.term_code='"+termCode+"' group by arr.tea_id) t, t_tea tea where t.tea_id = tea.tea_no";
		return baseDao.queryListInLowerKey(sql);
	}
	/**
	 * 查询雷达图某一类人的数据
	 * @param stuSql 学生sql
	 * @param name 类型名称 eg:学霸
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return Map<String,Object>
	 */
	private Map<String,Object> queryRadarMap(String stuSql,String name,String schoolYear,String termCode){
		Map<String,Object> map  = new HashMap<>();
		String sql = "select round(avg(t.gpa),2) from ("+stuSql+") t";
		 String val = baseDao.queryForString(sql);
		 Double value =0.0;
		 if(val==null||val.equals("")){
		 }else{
		 value = Double.valueOf(val);
		 }
		map.put("score",value); 
		map.put("risecount",businessDao.queryAgeEarlyCount(stuSql, schoolYear, termCode));
		map.put("zccount", businessDao.queryAvgBreakfastCount(stuSql, schoolYear, termCode));
		map.put("bookcount",businessDao.queryAvgBookCount(stuSql, schoolYear, termCode));
		map.put("bookrke",businessDao.queryAvgBookRke(stuSql, schoolYear, termCode));
		map.put("name", name);
		return map;
	}
	/**
	 * 获取学生行为-时间维度表中的最新学年，学期
	 * @return
	 */
	private Map<String,Object> queryNewYear(){
		String day = DateUtils.getNowDate();
		String[] date = EduUtils.getSchoolYearTerm(day);
		String sql = "select max(substr(t.school_year,0,4)||t.term_code) from t_stu_behavior_time t";
		String str = baseDao.queryForString(sql);
		Map<String,Object> map = new HashMap<String,Object>();
		if(str==null||str.equals("")){
			map.put("schoolyear", date[0]);
			map.put("term", date[1]);
		}else{
			String year = str.substring(0,4);
			String year1 = String.valueOf((Integer.parseInt(year)+1));
			map.put("schoolyear", year+"-"+year1);
			map.put("term", str.substring(4));
		}
		return map;
	}
	@Override
	public String getChinaId(){
		String sql = "select t.id from t_code_admini_div t where t.istrue = 1 and t.pid = '-1' ";
		return baseDao.queryForString(sql);
	}
}
