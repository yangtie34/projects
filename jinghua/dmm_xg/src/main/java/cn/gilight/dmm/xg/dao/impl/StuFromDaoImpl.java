package cn.gilight.dmm.xg.dao.impl;

import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.StuFromDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;

@Repository("stuFromDao")
public class StuFromDaoImpl implements StuFromDao {
	@Resource
	private BaseDao basedao;
	@Resource
	private BusinessDao businessDao;
	public static final String FROMSQL = "select div.id as id, div.id as code ,div.name_ as name,div.pid,div.level_ as cc,div.path_ as qxm from t_code_admini_div div where ";
	//查询生源地编码
	public static final String WWH = "未维护";//无编码的数据名称
	
	public static final String[] Origin_Name_Sxq = {"市辖区","县"};
	@Override
	public Map<String, Integer> queryMinGrade(List<String> deptList) {
		//查询最小的入学年级作为时间选择的最小年份
		String stu = getStuTableSql(null, deptList,null);//获取学生sql
		String sql = "select min(stu.enroll_grade) as value from (" + stu
				+ ") stu ";//查询最小年级
		Map<String, Object> map1 = basedao.queryMapInLowerKey(sql,
				Types.NUMERIC);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("value", MapUtils.getIntValue(map1, "value"));
		return map;
	}
	private String getStuTableSql(Integer schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList) {
		// 某学年所有在校学生数据sql
		String stuSql = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
		return stuSql;
	}
	@Override
	public List<Map<String, Object>> queryStuEdu(List<String> deptList) {
		//查询学校所有就读学历的学生
		String stu = getStuTableSql(null, deptList,null);//获取学生sql
		String sql = "select stu.edu_id as code,edu.order_ as pxh,edu.name_ as name  from ("
				+ stu
				+ ") stu inner join t_code_education edu "
				+ " on stu.edu_id = edu.id group by stu.edu_id,edu.order_,edu.name_ order by edu.order_,stu.edu_id ";
		return basedao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String, Object>> queryStuFrom(int schoolYear,
			List<String> deptList, String from, String to, String edu,
			String Id, Boolean updown,List<AdvancedParam> stuAdvancedList) {
		String now = String.valueOf(schoolYear);
		String start = from == null ? now : from;
		String end = to == null ? now : to;
		String xl = edu==null?Constant.Stu_Education_Group[0][0]:edu;
		String grade = "";
		for (int i = Integer.parseInt(start);i<Integer.parseInt(end)+1;i++){
			if (grade.equals("")){
				grade = String.valueOf(i);
			}else{
				grade +=","+String.valueOf(i);
			}
		}
		AdvancedParam eduAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, xl);
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, eduAdp);
		AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, grade);
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, gradeAdp);
		String stu = businessDao.getStuSql(deptList, stuAdvancedList);
		Map<String, String> map1 = queryCode(Id,updown);//查询出来应该截取的学生生源地id
//		String str1 = map1.get("str1");//学生生源地id
		String str3 = map1.get("str3");//下层或者上层节点信息
//		String str = "select t.*,rownum as r from (select "
//				+ str1
//				+ " as id,"+str1+" as code,div.name as name,count(*) as value,div.cc,div.pid from ("
//				+ stu + ") " + " stu inner join (";
//		String str2 = ") div on " + str1 + " = div.id where stu.enroll_grade "
//				+ " between " + start + " and " + end + " and stu.edu_id in (" + xl + ") group by "
//				+ " " + str1
//				+ ",div.name,div.cc,div.pid order by count(*) desc) t ";
//		String sql = str + str3 + str2;//查询出要展示的节点的学生人数，以及节点的信息
		String sql = "select t.*,rownum as r from (select c.divid as id,c.divid as code,c.name,c.cc,c.fjd as pid,count(0) as value "
				+ " from ("+stu+") stu,"
				+ " (select a.id as divid,a.name,a.cc,a.pid as fjd,b.* from ("+str3+") a ,t_code_admini_div b "
				+ " where a.qxm = substr(b.path_,0,length(a.qxm)) ) c "
				+ " where stu.stu_origin_id = c.id group by c.divid,c.name,c.cc,c.fjd order by count(0) desc) t";
		List<Map<String, Object>> list = basedao.queryListInLowerKey(sql);
		String pid = map1.get("pid");
		String relPid = MapUtils.getString(map1, "relpid");
		List<Map<String, Object>> list1 = basedao
				.queryListInLowerKey(queryNextDiv(relPid));
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int sxqCount = 0;String sxqid="";
		for (int i = 0; i < list1.size(); i++) {//补全各地区的信息
			Map<String, Object> map2 = list1.get(i);
			String code = MapUtils.getString(map2, "id");
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					Map<String, Object> map3 = list.get(j);
					String sxqName = MapUtils.getString(map3, "name");
					if (Origin_Name_Sxq[0].equals(sxqName)){
						sxqCount = MapUtils.getIntValue(map3, "value");
						sxqid = MapUtils.getString(map3, "id");
					}
					String code1 = MapUtils.getString(map3, "id");
					if (code !=null&& code1!=null&&code1.equals(code)) {
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
		AdvancedParam divAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, pid);
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, divAdp);
		String stu1 = businessDao.getStuSql(deptList, stuAdvancedList);
		int countAll = basedao.queryForCount(stu1);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("maptype",MapUtils.getString(basedao.queryMapInLowerKey(queryNowDiv(pid)), "name"));//获取当前展示的地图的类型以及ID放到list的第一位
		map.put("id", pid);
		map.put("count", countAll);
		map.put("sxq", sxqCount);
		map.put("sxqid", sxqid);
		result.add(0,map);
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
	@Override
	public String queryNowDiv(String Id) {
		String sql = FROMSQL + " div.id ='" + Id + "'";
		return sql;
	}
	/**
	 * 获取上层地区节点信息
	 */
	private String queryLastDiv(String Id) {
		String sql1 = queryNowDiv(Id);
		Map<String, Object> map = basedao.queryMapInLowerKey(sql1);
		String code = map == null ? getChinaId() : MapUtils.getString(map, "pid");
		String sql = FROMSQL + " div.pid ='"+getChinaId()+"'";
		if (code.equals("0")) {
		} else {
			String sql2 = FROMSQL + " div.id = " + code;
			Map<String, Object> map1 = basedao.queryMapInLowerKey(sql2);
			String dm = MapUtils.getString(map1, "pid");
			sql = FROMSQL + " div.pid = " + dm;
		}
		return sql;
	}
	@Override
	public Map<String, Object> queryGrowth(int schoolYear,
			List<String> deptList, String from, String to, String edu,
			String Id, Boolean updown,List<AdvancedParam> stuAdvancedList) {
		/*
		 * 1.计算出来选中地区下级节点今年和去年的招生人数，地区名，地区id。
		 * 2.用今年的计算结果算出来总招生人数，再结合去年的计算结果计算出总的增长率和各地区的增长率。
		 */
		String now = String.valueOf(schoolYear);
		String start = from == null ? now : from;
		String end = to == null ? now : to;
		String xl = edu==null?Constant.Stu_Education_Group[0][0]:edu;
		AdvancedParam eduAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, xl);
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, eduAdp);
		String stu = businessDao.getStuSql(deptList, stuAdvancedList);
		Map<String, String> map = queryCode(Id,updown);
		List<Map<String, Object>> backVal = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> lineList = new ArrayList<Map<String, Object>>();
//		String str1 = map.get("str1");
		String str3 = map.get("str3");
//		String str = "select " + str1 + " as id,div.name,count(*) as zs from ("
//				+ stu + ") stu " + " inner join (" + str3 + ") div on " + str1
//				+ " = div.id where stu.enroll_grade = '";
//		String str2 = " group by " + str1 + ",div.name";
		String str = "select c.divid as id,c.name,count(0) as zs "
				+ " from ("+stu+") stu,"
				+ " (select a.id as divid,a.name,a.cc,a.pid as fjd,b.* from ("+str3+") a ,t_code_admini_div b "
				+ " where a.qxm = substr(b.path_,0,length(a.qxm)) ) c "
				+ " where stu.stu_origin_id = c.id and stu.enroll_grade = '";
		String str2 = " group by c.divid,c.name";
		String str4 = "select distinct(c.divid) as jg "
				+ " from ("+stu+") stu,"
				+ " (select a.id as divid,a.name,a.cc,a.pid as fjd,b.* from ("+str3+") a ,t_code_admini_div b "
				+ " where a.qxm = substr(b.path_,0,length(a.qxm)) ) c "
				+ " where stu.stu_origin_id = c.id and stu.enroll_grade between '"+start+"'"
				+ " and '"+end+"'";
		List<Map<String, Object>> jg = basedao.queryListInLowerKey(str4);
		int a = Integer.parseInt(start);
		int b = Integer.parseInt(end);
		int c = a,d =b;
		if ((d-c) <5){
			c = d-4;
		}
		for (int i = c; i < d + 1; i++) {
			Map<String, Object> line = new HashMap<String, Object>();
			String nowYear = str + i + "' "+ str2;
			String lastYear = str + (i - 1) + "' "+ str2;
			String stu1 = "select c.divid as id,c.name "
					+ " from ("+stu+") stu,"
					+ " (select a.id as divid,a.name,a.cc,a.pid as fjd,b.* from ("+str3+") a ,t_code_admini_div b "
					+ " where a.qxm = substr(b.path_,0,length(a.qxm)) ) c "
					+ " where stu.stu_origin_id = c.id and stu.enroll_grade "
					+ " between '" + start + "' and '" + end + "' group by c.divid,c.name";
			String sql = "select xs.name,xs.id "
			+ " as jg,case when  jn.zs is null and qn.zs is null then 0 "
			+ " when jn.zs is null and qn.zs is  not null then -100 when  jn.zs is not null and qn.zs is null then 100 "
			+ " else round((nvl(jn.zs,0)-nvl(qn.zs,0))/nvl(qn.zs,0)*100,2) end  as zs1, "
			+ " case when  jn.zs is null or qn.zs is null then 0 "
			+ " else 1 end  as bs, "
			+ " round((nvl(jn.zs,0)-nvl(qn.zs,0)),2) as zs2 from ("
			+ stu1 + ") xs left join (" + nowYear + ") jn on "
			+" xs.id = jn.id left join (" + lastYear
			+ ") qn on xs.id=qn.id group by xs.name,xs.id,jn.zs,qn.zs";
			List<Map<String, Object>> nowList = basedao
					.queryListInLowerKey(nowYear);
			List<Map<String, Object>> lastList = basedao
					.queryListInLowerKey(lastYear);
			List<Map<String, Object>> list = basedao.queryListInLowerKey(sql);
			int zs = 0;
			int zzs = 0;
			double zzl = 0;
			int zs1 = 0;
			for (Map<String, Object> map1 : nowList) {
				zs += MapUtils.getIntValue(map1, "zs");
			}
			for (Map<String, Object> map2 : lastList) {
				zs1 += MapUtils.getIntValue(map2, "zs");
			}
			zzs = zs - zs1;
			if (zs >= 0 && zs1 > 0) {
				zzl = MathUtils.get2Point(MathUtils.getDivisionResult(zzs* 100, zs1, 2) );
			} else if (zs == 0 && zs1 == 0) {
				zzl = 0;
			} else if (zs1 == 0 && zs > 0) {
				zzl = 100;
			}
			line.put("name", i);//时间段
			line.put("val1", zzs);//增长数
			line.put("val2", zzl);//增长率
			lineList.add(line);
			if ( i >=a){
			   backVal.addAll(list);
			}
		}
		for (Map<String, Object> temp : jg) {
			double lv1 = 0;
			double zz1 = 0;
			String jgm = "";
			String syd = "";
			int count = 0;
			String jgid = MapUtils.getString(temp, "jg");
			Map<String, Object> map4 = new HashMap<String, Object>();
			for (int k = 0; k < backVal.size(); k++) {
				String jgwid = MapUtils.getString(backVal.get(k), "jg");
				String bs = MapUtils.getString(backVal.get(k), "bs");
				if (jgid.equals(jgwid)) {
					lv1 += MapUtils.getDouble(backVal.get(k), "zs1");
					zz1 += MapUtils.getDouble(backVal.get(k), "zs2");
					jgm = MapUtils.getString(backVal.get(k), "name");
					syd = MapUtils.getString(backVal.get(k), "jg");
					if (bs.equals("0")) {
						count++;
					}
				}
			}
			if (count > 0) {
				map4.put("id", syd);//生源地id
				map4.put("name", jgm);//生源地名称
				map4.put("val1", MathUtils.get2Point(getDivisionResult(zz1, (b - a + 1), 2)));//平均增长数
				map4.put("val2", -999999);//平均增长率
				map4.put("bs", 0);//是否有数据异常，1为正常，0为不正常
			} else {
				map4.put("id", syd);
				map4.put("name", jgm);
				map4.put("val1", MathUtils.get2Point(getDivisionResult(zz1, (b - a + 1), 2)));
				map4.put("val2", MathUtils.get2Point(getDivisionResult(lv1, (b - a + 1), 2)));
				map4.put("bs", 1);
			}
			result.add(map4);
		}
		sort(result);
		Map<String, Object> map3 = new HashMap<String, Object>();
		map3.put("list", result);
		map3.put("line", lineList);
		map3.put("xl", xl);
		return map3;
	}
	@Override
	public Map<String,Object> getAllGrowth(int schoolYear,
			List<String> deptList, String from, String to, String edu,
			String Id, List<AdvancedParam> stuAdvancedList){
		String now = String.valueOf(schoolYear);
		String start = from == null ? now : from;
		String end = to == null ? now : to;
		String xl = edu==null?Constant.Stu_Education_Group[0][0]:edu;
		AdvancedParam eduAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, xl);
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, eduAdp);
		AdvancedParam sydAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, Id);
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, sydAdp);
		String stu = businessDao.getStuSql(deptList, stuAdvancedList);
		int a = Integer.parseInt(start);
		int b = Integer.parseInt(end);
		Double lv = 0d;int count =0,count1=0;
		for(int i = a;i<(b+1);i++){
			String nowSql = stu+" and t.enroll_grade = '"+i+"'";
			String lastSql = stu+" and t.enroll_grade = '"+(i-1)+"'";
			Integer nowCount = basedao.queryForCount(nowSql);
			Integer lastCount = basedao.queryForCount(lastSql);
			int nowC = nowCount == null? 0:nowCount;
			int lastC = lastCount == null?0:lastCount;
			if(i==b){
				count = nowCount;
			}
			if(i==a){
				count1 = nowCount;
			}
			Double scale = MathUtils.get2Point(MathUtils.getDivisionResult((nowC-lastC)*100, lastC, 2));
			lv+= scale;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("count", count-count1);
		map.put("scale", MathUtils.get2Point(getDivisionResult(lv,(b-a+1), 2)));
		return map;
	}
	@Override
	public Double getDivisionResult(double dividend, int divisor, int maxDigits) {
		return MathUtils.getDivisionResult(dividend, Double.valueOf(divisor), maxDigits); // 返回的是String类型
	}
	@Override
	public String queryStuList(int schoolYear,List<String> deptList, String from, String to, String edu,
	String Id, Boolean updown,List<AdvancedParam> stuAdvancedList){
		String now = String.valueOf(schoolYear);
		String start = from == null ? now : from;
		String end = to == null ? now : to;
		String xl = edu==null?Constant.Stu_Education_Group[0][0]:edu;
		String grade = "";
		for (int i = Integer.parseInt(start);i<Integer.parseInt(end)+1;i++){
			if (grade.equals("")){
				grade = String.valueOf(i);
			}else{
				grade +=","+String.valueOf(i);
			}
		}
		List<AdvancedParam> advList = new ArrayList<AdvancedParam>();
		if (stuAdvancedList != null){advList.addAll(stuAdvancedList);}
		AdvancedParam eduAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, xl);
		advList = AdvancedUtil.add(advList, eduAdp);
		AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, grade);
		advList = AdvancedUtil.add(advList, gradeAdp);
		AdvancedParam sydAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, Id);
		advList = AdvancedUtil.add(advList, sydAdp);
		String stu = businessDao.getStuSql(deptList, advList);
		String sql1 = "select x.no,x.enroll_grade,x.name,x.sexmc,"
				+" x.ssx as jg,x.deptmc,x.majormc,x.classmc from ("+businessDao.getStuDetailSql(stu)+") x order by  x.enroll_grade desc";
		return sql1;
	}
	/**
	 * 获取要展示的节点层次以及生源地id以及节点的信息查询sql
	 */
	private Map<String, String> queryCode(String Id,Boolean updown) {
		String str3 = queryNextDiv(Id);
		if (!updown
				|| basedao.queryListInLowerKey(queryNextDiv(Id)).size() == 0) {
			str3 = queryLastDiv(Id);
		}
		List<Map<String, Object>> From = basedao.queryListInLowerKey(str3);
//		String code = From.size() == 0 ? "0" : MapUtils.getString(From.get(0), "cc");
		String pid = From.size() == 0 ? getChinaId() : MapUtils.getString(From.get(0), "pid");
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
					|| basedao.queryListInLowerKey(str3).size() == 0) {
				str3 = queryLastDiv(id1);
				List<Map<String, Object>> From1 = basedao.queryListInLowerKey(str3);
				pid = From1.size() == 0 ? getChinaId() : MapUtils.getString(From1.get(0), "pid");
				relpid = pid;
			}
			
		}
	
		Map<String, String> map = new HashMap<String, String>();
//		String str1 = " stu.stu_origin_id ";
//		if (code.equals("1")) {
//			str1 = " substr(stu.stu_origin_id,0,2)||'0000' ";
//		} else if (code.equals("2")) {
//			str1 = " substr(stu.stu_origin_id,0,4)||'00'";
//		}
//		map.put("str1", str1);
		map.put("str3", str3);
		map.put("pid", pid);
		map.put("relpid", relpid);
		return map;
	}
	/**
	 * 获取排序
	 */
	private static void sort(List<Map<String, Object>> data) {

		Collections.sort(data, new Comparator<Map>() {

			public int compare(Map o1, Map o2) {

				Double a = MapUtils.getDouble(o1, "val2");
				Double b = MapUtils.getDouble(o2, "val2");
				Double c = MapUtils.getDouble(o1, "val1");
				Double d = MapUtils.getDouble(o2, "val1");

				// 升序
				if (b.compareTo(a) == 0) {
					return d.compareTo(c);
				} else {
					return b.compareTo(a);
				}

				// 降序
				// return b.compareTo(a);
			}
		});
	}
	@Override
	public List<Map<String, Object>> querySchoolTag(int schoolYear,
			List<String> deptList, String from, String to, String edu,
			String Id, Boolean updown,List<AdvancedParam> stuAdvancedList) {
		String now = String.valueOf(schoolYear);
		String start = from == null ? now : from;
		String end = to == null ? now : to;
		String xl = edu==null?Constant.Stu_Education_Group[0][0]:edu;
		String grade = "";
		for (int i = Integer.parseInt(start);i<Integer.parseInt(end)+1;i++){
			if (grade.equals("")){
				grade = String.valueOf(i);
			}else{
				grade +=","+String.valueOf(i);
			}
		}
		AdvancedParam eduAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, xl);
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, eduAdp);
		AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, grade);
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, gradeAdp);
//		AdvancedParam sydAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Business_STU_ORIGIN_ID, Id);
//		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, sydAdp);
		Map<String, String> map = queryCode(Id,updown);
		String str3 = map.get("str3");
		String stu = businessDao.getStuSql(deptList, stuAdvancedList);
//		String sql =  "select t.*,rownum as r from ( select nvl(stu.SchoolTag,'"+WWH+"') as name,count(*) as value from ("+stu+") stu "
//				+ " group by stu.SchoolTag order by count(*) desc) t ";
		String sql = "select t.*,rownum as r from (select nvl(stu.SchoolTag,'"+WWH+"') as name,count(0) as value "
				+ " from ("+stu+") stu,"
				+ " (select a.id as divid,a.name,a.cc,a.pid as fjd,b.* from ("+str3+") a ,t_code_admini_div b "
				+ " where a.qxm = substr(b.path_,0,length(a.qxm)) ) c "
				+ " where stu.stu_origin_id = c.id group by stu.SchoolTag order by count(0) desc) t";
		return basedao.queryListInLowerKey(sql);
	}
	@Override
	public String getChinaId(){
		String sql = "select t.id from t_code_admini_div t where t.istrue = 1 and t.pid = '-1' ";
		return basedao.queryForString(sql);
	}
	@Override
	public String getIdByMc(String mc){
		String sql= "select t.id from t_code_admini_div t where t.name_ = '"+mc+"' ";
		return basedao.queryForString(sql);
	}
}
