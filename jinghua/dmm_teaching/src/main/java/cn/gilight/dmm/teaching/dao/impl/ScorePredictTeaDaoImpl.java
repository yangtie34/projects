package cn.gilight.dmm.teaching.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.ScorePredictTeaDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.Globals;

@Repository("scorePredictTeaDao")
public class ScorePredictTeaDaoImpl implements ScorePredictTeaDao {
 
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	
	private static final int CODE_ACCURACY = 1;
	
	private static final String CODE_Course_Attr_Code_3 = "3";
	/**
	 * 缺考
	 */
	private static final String CODE_EXAM_STATUS_CODE_1 = "1";
	/**
	 * 缓考
	 */
	private static final String CODE_EXAM_STATUS_CODE_2 = "2";
	/**
	 * 违纪
	 */
	private static final String CODE_EXAM_STATUS_CODE_3 = "3";
	private static final String Cs_Sql = "cs = 1";
	
	@Override
	public List<Map<String,Object>> queryCourseList(String schoolYear,String termCode,String teaNo){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		String sql ="select t.course_id as id,a.name_ as mc from t_course_arrangement t,t_course a where t.tea_id = '"+teaNo+"' "
				+schoolYear+termCode+" and t.course_id = a.id group by t.course_id,a.name_ order by t.course_id";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public Map<String,Object> queryAttrList(String schoolYear,String termCode,String teaNo,String course,String classid){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		String sql = "select b.code_ as id,b.name_ as name,t.credit as xf from t_course_arrangement_plan t,t_class_teaching_xzb a,t_code b "
				+ " where  t.course_code = '"+course+"' "+schoolYear+termCode+" and a.teach_class_id = '"+classid+"' "
				+ " and t.class_id = a.class_id and t.course_attr_code = b.code_ and b.code_type ='COURSE_ATTR_CODE' "
				+ " group by b.code_,b.name_,t.credit "
				+ " UNION "
				+"  select b.code_ as id,b.name_ as name,t.credit as xf  from t_stu_course_choose t,t_class_teaching_stu a,t_code b "
				+ " where t.scoure_code = '"+course+"' "+schoolYear+termCode+" and a.teach_class_id = '"+classid+"' "
				+ " and t.stu_id = a.stu_id and t.course_attr_code = b.code_ and b.code_type ='COURSE_ATTR_CODE' "
		        + " group by b.code_,b.name_,t.credit ";
	
		String teaSql = "select a.tea_no as code,a.name_ as mc from t_tea a where a.tea_no = '"+teaNo+"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql),
				                 list1 = baseDao.queryListInLowerKey(teaSql);
		Map<String,Object> map= new HashMap<String, Object>(),
				           map1 = new HashMap<String, Object>();
		if(list != null && !list.isEmpty()){
			map.put("xf", list.get(0));
		}
		if(list1 != null && !list1.isEmpty()){
			map1 = list1.get(0);
		}
		map.putAll(map1);
		return map;
	}
	@Override
	public Map<String,Object> queryYxAndBjList(String course,String attr,String classid){
		String sql= "select t.dept_id as id from t_course t where t.id = '"+course+"' ";
		String id = baseDao.queryForString(sql);
		String name = businessService.getDeptNameById(id);
		String sql1 = "";
		if(CODE_Course_Attr_Code_3.equals(attr)){
			sql1 = "select wm_concat(mc) as mc from (select c.name_ as mc from t_class_teaching_stu a,t_stu b,t_code_dept_teach c where a.teach_class_id = '"+classid+"'"
					+ " and a.stu_id = b.no_ and b.dept_id = c.id group by c.name_)";
		}else{
			sql1 = "select wm_concat(mc) as mc from (select c.name_ as mc from t_class_teaching_xzb a,t_classes c where a.teach_class_id = '"+classid+"'"
					+ " and a.class_id = c.no_  group by c.name_)";
		}
		String className = baseDao.queryForString(sql1);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("dept", name);
		map.put("cname", className);
		return map;
	}
	@Override
	public List<Map<String,Object>> getCodeAttr(){
		String sql = "select T.CODE_ as id,t.name_ as name from t_code t where t.code_type = 'COURSE_ATTR_CODE' group by t.code_,t.name_";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public String queryStuSql(String teaNo,List<String> deptList){
		String stuSql = businessDao.getStuSql(deptList, new ArrayList<AdvancedParam>());
		String sql = "select c.*  from t_course_arrangement a ,t_class_teaching_stu b,("+stuSql+") c where tea_id = '"+teaNo+"' and a.teachingclass_id = b.teach_class_id and b.stu_id = c.no_";
	    return sql;
	}
	@Override
	public Map<String,Object> queryTeachClassAndStuCount(String schoolYear,String termCode,String teaNo,String courseId,Integer year,List<String> deptList,List<AdvancedParam> advancedList){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		year = schoolYear.equals("")?null:year;
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id from t_course_arrangement t where t.tea_id = '"+teaNo+"'"
				+schoolYear+termCode+ " and t.course_id = '"+courseId+"' group by t.teachingclass_id";
		String StuSql = " select count(0) from t_class_teaching_stu t,("+teachSql+") a,("+stu+") stu  where t.teach_class_id = a.id and stu.no_ = t.stu_id";
		Map<String,Object> map = DevUtils.MAP();
		map.put("bj", baseDao.queryForCount(teachSql));
		map.put("stu", baseDao.queryForInt(StuSql));
		return map;
	}
	
	@Override
	public List<Map<String,Object>> queryCourseNatureList(String schoolYear,String termCode,String teaNo,String courseId){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		String teachSql ="select t.course_nature_code as id from t_course_arrangement t where t.tea_id = '"+teaNo+"'"
				+schoolYear+termCode+ " and t.course_id = '"+courseId+"' group by t."+Constant.CODE_COURSE_NATURE_CODE+"";
		String codeSql = "select code_ as id,name_ as mc from t_code where code_type = '"+Constant.CODE_COURSE_NATURE_CODE+"' order by code_ ";
		String sql = "select a.* from ("+codeSql+") a,("+teachSql+") b where a.id = b.id";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> queryClassList(String schoolYear,String termCode,String teaNo,String courseId,String nature,Integer year,String table,List<String> deptList,List<AdvancedParam> advancedList){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		year = schoolYear.equals("")?null:year;
		nature = nature == null ? " and t.COURSE_NATURE_CODE is null " :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id,mc.name_ as mc from t_course_arrangement t,t_class_teaching mc where t.tea_id = '"+teaNo+"'  "
				+schoolYear+termCode+  " and t.course_id = '"+courseId+"'"+nature+" and t.teachingclass_id = mc.code_ group by t.teachingclass_id,mc.name_";
		String classSql = "select a.id as id,a.mc as mc,t.predict_score as score from ("+teachSql+") a, t_class_teaching_stu xzb,("+stu+") stu ,"+table+" t"
				+ " where  a.id = xzb.teach_class_id and xzb.stu_id = stu.no_ and stu.no_ = t.stu_id "+schoolYear+" "+termCode+" and t.course_id = '"+courseId+"' "
				+ " order by a.id ";
		return baseDao.queryListInLowerKey(classSql);
	}
	@Override
	public List<Map<String,Object>> queryClassList(String schoolYear,String termCode,String teaNo,String courseId,String nature,Integer year,List<String> deptList,List<AdvancedParam> advancedList){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		year = schoolYear.equals("")?null:year;
		nature = nature == null ? " and t.COURSE_NATURE_CODE is null " :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id,mc.name_ as mc from t_course_arrangement t,t_class_teaching mc where t.tea_id = '"+teaNo+"'  "
				+schoolYear+termCode+  " and t.course_id = '"+courseId+"'"+nature+" and t.teachingclass_id = mc.code_ group by t.teachingclass_id,mc.name_";
		String classSql = "select a.id as id,a.mc as mc,case when t.hierarchical_score_code is not null then hier.centesimal_score else t.centesimal_score end score"
				+ " from ("+teachSql+") a inner join  t_class_teaching_stu xzb on a.id = xzb.teach_class_id "
				+ " inner join ("+stu+") stu on xzb.stu_id = stu.no_ left join t_stu_score_history t on stu.no_ = t.stu_id "
				+ " left join t_code_score_hierarchy hier on t.hierarchical_score_code = hier.code_ "
				+ " where  t.COURSE_CODE = '"+courseId+"' "+schoolYear+" "+termCode+" and t.EXAM_STATUS_CODE is null and t."+Cs_Sql+""
				+ " order by a.id,t.CENTESIMAL_SCORE desc ";
		return baseDao.queryListInLowerKey(classSql);
	}
	@Override
	public List<Map<String,Object>> getStuCount(String schoolYear,String termCode,String teaNo,String courseId,String nature,Integer year,List<String> deptList,List<AdvancedParam> advancedList){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		year = schoolYear.equals("")?null:year;
		nature = nature == null ? " and t.COURSE_NATURE_CODE is null " :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id,mc.name_ as mc from t_course_arrangement t,t_class_teaching mc where t.tea_id = '"+teaNo+"'  "
				+schoolYear+termCode+  " and t.course_id = '"+courseId+"'"+nature+" and t.teachingclass_id = mc.code_ group by t.teachingclass_id,mc.name_";
		String stuCountSql = " select x.id,count(0) as value from ("+teachSql+") x,t_class_teaching_stu y,("+stu+") z where x.id = y.teach_class_id and y.stu_id = z.no_ group by x.id ";
	    return  baseDao.queryListInLowerKey(stuCountSql); 
	}
	@Override
	public List<Map<String,Object>> getStuCountTrue(String schoolYear,String termCode,String teaNo,String courseId,String nature,Integer year,List<String> deptList,List<AdvancedParam> advancedList,Boolean isgd){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		year = schoolYear.equals("")?null:year;
		nature = nature == null ? " and t."+Constant.CODE_COURSE_NATURE_CODE+" is null " :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String stu = businessDao.getStuSql(deptList, advancedList);
		String str = isgd ? " and t.EXAM_STATUS_CODE is null ":"";
		String teachSql ="select t.teachingclass_id as id,mc.name_ as mc from t_course_arrangement t,t_class_teaching mc where t.tea_id = '"+teaNo+"'  "
				+schoolYear+termCode+  " and t.course_id = '"+courseId+"'"+nature+" and t.teachingclass_id = mc.code_ group by t.teachingclass_id,mc.name_";
		String stuCountSql = " select x.id,x.mc,count(0) as value from ("+teachSql+") x,t_class_teaching_stu y,("+stu+") z,t_stu_score_history t where x.id = y.teach_class_id and y.stu_id = z.no_ and z.no_= t.stu_id "
				+schoolYear+ termCode +" and t.course_code = '"+courseId+"' "+str+" and t."+Cs_Sql+" group by x.id,x.mc ";
	    return  baseDao.queryListInLowerKey(stuCountSql); 
	}
	@Override
	public Boolean getYzZt(String schoolYear,String termCode,String course,String nature,String classid){
		nature = nature == null ? " and t."+Constant.CODE_COURSE_NATURE_CODE+" is null "  :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String sql = "select * from t_stu_score_print_gd t where "
				+ " t.school_year = '"+schoolYear+"' and t.term_code = '"+termCode+"' and "
						+ " t.course_id = '"+course+"' and teach_class_id = '"+classid+"' "+nature;
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
        return (list == null || list.isEmpty())?false:true;
	}
	@Override
	public List<Map<String,Object>> getStuCountOther(String schoolYear,String termCode,String teaNo,String courseId,String nature,Integer year,List<String> deptList,List<AdvancedParam> advancedList){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		year = schoolYear.equals("")?null:year;
		nature = nature == null ? " and t."+Constant.CODE_COURSE_NATURE_CODE+" is null "  :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id,mc.name_ as mc from t_course_arrangement t,t_class_teaching mc where t.tea_id = '"+teaNo+"'  "
				+schoolYear+termCode+  " and t.course_id = '"+courseId+"'"+nature+" and t.teachingclass_id = mc.code_ group by t.teachingclass_id,mc.name_";
		String stuCountSql = " select x.id,x.mc,w.code_ as code, w.name_ as name,nvl(count(0),0) as value from ("+teachSql+") x inner join t_class_teaching_stu y on x.id = y.teach_class_id "
				+ "inner join ("+stu+") z on  y.stu_id = z.no_ inner join t_stu_score_history t on z.no_= t.stu_id left join t_code w on t.EXAM_STATUS_CODE = w.code_  where t.course_code = '"+courseId+"' "
				+schoolYear+ termCode +" and t.EXAM_STATUS_CODE is not null and w.code_type = 'EXAM_STATUS_CODE' and t."+Cs_Sql+" group by x.id,x.mc,w.code_,w.name_ ";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(stuCountSql);
		List<Map<String,Object>> bj = new ArrayList<Map<String,Object>>(),codeList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> temp : list){
			String id = MapUtils.getString(temp, "id"),
				   mc = MapUtils.getString(temp, "mc"),
				   name = MapUtils.getString(temp, "name"),
				   code = MapUtils.getString(temp, "code");
			Map<String, Object> classMap = new HashMap<String, Object>(),
					            codeMap = new HashMap<String, Object>();
			classMap.put("id", id);classMap.put("mc", mc);
			codeMap.put("code", code);codeMap.put("name", name);
			if(!bj.contains(classMap)){
				bj.add(classMap);
			}
			if(!codeList.contains(codeMap)){
				codeList.add(codeMap);
			}
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> x : bj){
			Map<String, Object> map = new HashMap<String, Object>();
			String id_1 = MapUtils.getString(x,"id");
			map.putAll(x);
			for(Map<String, Object> y : codeList){
				String code_1 = MapUtils.getString(y, "code");
				if(CODE_EXAM_STATUS_CODE_1.equals(code_1)){
					map.put("qk", 0);
				}else if(CODE_EXAM_STATUS_CODE_2.equals(code_1)){
					map.put("hk", 0);
				}else if(CODE_EXAM_STATUS_CODE_3.equals(code_1)){
					map.put("wj", 0);
				}
				for(Map<String,Object> temp1: list){
					String id_2 = MapUtils.getString(temp1, "id"),
						   code_2 = MapUtils.getString(temp1, "code");
					int value =MapUtils.getIntValue(temp1, "value");
					if(id_1.equals(id_2) && code_1.equals(code_2)){
						if(CODE_EXAM_STATUS_CODE_1.equals(code_2)){
							map.put("qk", value);
						}else if(CODE_EXAM_STATUS_CODE_2.equals(code_2)){
							map.put("hk", value);
						}else if(CODE_EXAM_STATUS_CODE_3.equals(code_2)){
							map.put("wj", value);
						}
					}
				}
				result.add(map);
			}
		}
		return result; 
	}
	@Override
	public List<Double> queryClassScoreGk(String stuSql,String schoolYear,String termCode,String courseId,String table){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		String scoreSql = "select t.PREDICT_SCORE as score from "+table+" t,("+stuSql+") stu "
				+ " where  t.course_id = '"+courseId+"' and stu.no_ = t.stu_id "+schoolYear+termCode;
		return baseDao.queryForListDouble(scoreSql);
	}
	@Override
	public Map<String,Object> queryNatureList(String schoolYear,String termCode,String teaNo,String courseId,String start,String end,Integer year,List<String> deptList,List<AdvancedParam> advancedList,String table){
		String schoolyear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		String termcode = termCode == null || termCode.equals("")?"":" and t.term_code = '"+termCode+"' ";
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and beh.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and beh.term_code = '"+termCode+"' ";
		start = start == null || start.equals("") ? "" : " and beh.PREDICT_SCORE >= "+start+"" ;
		end = end == null || end.equals("") ? "" : " and beh.PREDICT_SCORE < "+end+"" ;
		year = schoolYear.equals("")?null:year;
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id,t.course_nature_code as nature from t_course_arrangement t where t.tea_id = '"+teaNo+"' "
				+schoolyear+termcode+" and t.course_id = '"+courseId+"' group by t.teachingclass_id,t.course_nature_code";
		String codeSql = "select code_ as id,name_ as mc from t_code where code_type = '"+Constant.CODE_COURSE_NATURE_CODE+"' order by code_";
		String natureSql = "select a.id,a.mc,b.id as class_id from ("+codeSql+") a,("+teachSql+") b where a.id = b.nature group by a.id,b.id,a.mc";
	    List<Map<String,Object>> list = baseDao.queryListInLowerKey(natureSql);
	    String stuSql = "";int hasNature=0;
	    if(list == null || list.isEmpty()){
	    	stuSql =" select count(0) as value from t_class_teaching_stu t,"+table+" beh,("+stu+") stu,("+teachSql+") tea "
					+ " where t.teach_class_id = tea.id and t.stu_id = stu.no_ and beh.course_id = '"+courseId+"' "
					+ " and beh.stu_id = t.stu_id "+start+end+schoolYear+termCode+" "; 
	    }else{
	    	hasNature= 1;
		   stuSql = " select a.id,a.mc as name,count(0) as value from t_class_teaching_stu t,"+table+" beh,("+natureSql+") a,("+stu+") stu "
				+ " where t.teach_class_id = a.class_id and t.stu_id = stu.no_ and beh.course_id = '"+courseId+"' "
						+ " and beh.stu_id = t.stu_id "+start+end+schoolYear+termCode+" group by a.id,a.mc order by id ";
	    }
	    Map<String,Object> map= new HashMap<String, Object>();
	    map.put("list", baseDao.queryListInLowerKey(stuSql));
	    map.put("has", hasNature);
		return map;
	}
	@Override
	public Map<String,Object> queryNatureList(String schoolYear,String termCode,String teaNo,String courseId,String start,String end,Integer year,List<String> deptList,List<AdvancedParam> advancedList){
		String schoolyear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		String termcode = termCode == null || termCode.equals("")?"":" and t.term_code = '"+termCode+"' ";
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and beh.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and beh.term_code = '"+termCode+"' ";
		start = start == null || start.equals("") ? "" : " and beh.PREDICT_SCORE >= "+start+"" ;
		end = end == null || end.equals("") ? "" : " and beh.PREDICT_SCORE < "+end+"" ;
		year = schoolYear.equals("")?null:year;
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id,t.course_nature_code as nature from t_course_arrangement t where t.tea_id = '"+teaNo+"' "
				+schoolyear+termcode+" and t.course_id = '"+courseId+"' group by t.teachingclass_id,t.course_nature_code";
		String codeSql = "select code_ as id,name_ as mc from t_code where code_type = '"+Constant.CODE_COURSE_NATURE_CODE+"' order by code_";
		String natureSql = "select a.id,a.mc,b.id as class_id from ("+codeSql+") a,("+teachSql+") b where a.id = b.nature group by a.id,b.id,a.mc";
	    List<Map<String,Object>> list = baseDao.queryListInLowerKey(natureSql);
	    String stuSql = "";int hasNature=0;
	    String table = "(select case when t.hierarchical_score_code is not null then a.centesimal_score else t.centesimal_score end PREDICT_SCORE,"
	    		+ " t.school_year,t.term_code,t.course_code as course_id,t.stu_id from t_stu_score_history t left join t_code_score_hierarchy a "
	    		+ " on t.hierarchical_score_code = a.code_  where t."+Cs_Sql+")";
	    if(list == null || list.isEmpty()){
	    	stuSql =" select count(0) as value from t_class_teaching_stu t,"+table+" beh,("+stu+") stu,("+teachSql+") tea "
					+ " where t.teach_class_id = tea.id and t.stu_id = stu.no_ and beh.course_id = '"+courseId+"' "
					+ " and beh.stu_id = t.stu_id "+start+end+schoolYear+termCode+" "; 
	    }else{
	    	hasNature= 1;
		   stuSql = " select a.id,a.mc as name,count(0) as value from t_class_teaching_stu t,"+table+" beh,("+natureSql+") a,("+stu+") stu "
				+ " where t.teach_class_id = a.class_id and t.stu_id = stu.no_ and beh.course_id = '"+courseId+"' "
						+ " and beh.stu_id = t.stu_id "+start+end+schoolYear+termCode+" group by a.id,a.mc order by id ";
	    }
	    Map<String,Object> map= new HashMap<String, Object>();
	    map.put("list", baseDao.queryListInLowerKey(stuSql));
	    map.put("has", hasNature);
		return map;
	}
	@Override
	public List<Double> queryClassScore(String schoolYear,String teaNo,String courseId,String nature,String type,Integer year,List<String> deptList,List<AdvancedParam> advancedList,String table){
		String str = " and t.tea_id = '"+teaNo+"' ";
		type = type == null? "tea":type;
		if(type.equals("all")){
			str = "";
		}else if(type.equals("notme")){
			str = " and t.tea_id <> '"+teaNo+"' ";
		}
		year = schoolYear.equals("")?null:year;
		nature = nature == null ? "  and t.COURSE_NATURE_CODE is null " :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id from t_course_arrangement t where "
				+ " t.school_year = '"+schoolYear+"' and t.course_id = '"+courseId+"' "+nature+str;
		String stuSql = "select stu.stu_id from t_class_teaching_stu stu,("+teachSql+") tea,("+stu+") xs where stu.teach_class_id = tea.id and stu.stu_id = xs.no_ group by stu.stu_id";
		String scoreSql = "select t.stu_id,t.PREDICT_SCORE as score from "+table+" t "
				+ " where t.course_id = '"+courseId+"' and t.school_year = '"+schoolYear+"'";
		String sql= "select score from ("+stuSql+") stu,("+scoreSql+") score where stu.stu_id = score.stu_id";
		return baseDao.queryForListDouble(sql);
	}
	@Override
	public List<Double> queryClassScore(String schoolYear,String teaNo,String courseId,String nature,String type,Integer year,List<String> deptList,List<AdvancedParam> advancedList){
		String str = " and t.tea_id = '"+teaNo+"' ";
		type = type == null? "tea":type;
		if(type.equals("all")){
			str = "";
		}else if(type.equals("notme")){
			str = " and t.tea_id <> '"+teaNo+"' ";
		}
		year = schoolYear.equals("")?null:year;
		nature = nature == null ? "  and t.COURSE_NATURE_CODE is null " :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id from t_course_arrangement t where "
				+ " t.school_year = '"+schoolYear+"' and t.course_id = '"+courseId+"' "+nature+str;
		String stuSql = "select stu.stu_id from t_class_teaching_stu stu,("+teachSql+") tea,("+stu+") xs where stu.teach_class_id = tea.id and stu.stu_id = xs.no_ group by stu.stu_id";
		String scoreSql = "select t.stu_id,case when t.hierarchical_score_code is not null then hier.centesimal_score else t.centesimal_score end score from t_stu_score_history t "
				+ " left join t_code_score_hierarchy hier on t.hierarchical_score_code = hier.code_ "
				+ " where t.course_code = '"+courseId+"' and t.school_year = '"+schoolYear+"' and t.exam_status_code is null and t."+Cs_Sql+"";
		String sql= "select score from ("+stuSql+") stu,("+scoreSql+") score where stu.stu_id = score.stu_id";
		return baseDao.queryForListDouble(sql);
	}
	@Override
	public Map<String,Object> queryClassCount(String schoolYear,String teaNo,String courseId,String nature,String type,Integer year,List<String> deptList,List<AdvancedParam> advancedList,Boolean isgd){
		String str = " and t.tea_id = '"+teaNo+"' ";
		type = type == null? "tea":type;
		if(type.equals("all")){
			str = "";
		}else if(type.equals("notme")){
			str = " and t.tea_id <> '"+teaNo+"' ";
		}
		String str2 = isgd?" and t.exam_status_code is null ":" ";
		year = schoolYear.equals("")?null:year;
		nature = nature == null ? " and t.COURSE_NATURE_CODE is null " :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String stu = businessDao.getStuSql(deptList, advancedList);
		String teachSql ="select t.teachingclass_id as id from t_course_arrangement t where "
				+ " t.school_year = '"+schoolYear+"' and t.course_id = '"+courseId+"' "+nature+str;
		String stuSql = "select stu.stu_id from t_class_teaching_stu stu,("+teachSql+") tea,("+stu+") xs where stu.teach_class_id = tea.id and stu.stu_id = xs.no_ group by stu.stu_id";
		String scoreSql = "select t.stu_id,case when t.hierarchical_score_code is not null then hier.centesimal_score else t.centesimal_score end score from t_stu_score_history t inner join "
				+ " ("+stuSql+") a on t.stu_id = a.stu_id "
				+ " left join t_code_score_hierarchy hier on t.hierarchical_score_code = hier.code_ "
				+ " where t.course_code = '"+courseId+"' "+str2+" and t.school_year = '"+schoolYear+"' and t."+Cs_Sql+"";
		String scoreSql1 = "select t.stu_id,case when t.hierarchical_score_code is not null then hier.centesimal_score else t.centesimal_score end score from t_stu_score_history t inner join "
				+ " ("+stuSql+") a on t.stu_id = a.stu_id "
				+ " left join t_code_score_hierarchy hier on t.hierarchical_score_code = hier.code_ "
				+ " where t.course_code = '"+courseId+"' and t.school_year = '"+schoolYear+"' and t."+Cs_Sql+"";
		int count1 = baseDao.queryForCount(scoreSql);
		int count  =0;
		if(!isgd){
		    count = baseDao.queryForCount(stuSql);
		}else{
			count = baseDao.queryForCount(scoreSql1);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("all",count);
		map.put("scount", count1);
		return map;
	}
	@Override
	public Double queryPrecision(String schoolYear,String termCode,String courseId,String teaNo,String nature,Boolean isMe,Integer year,List<String> deptList,List<AdvancedParam> advancedList,String table){
		schoolYear = schoolYear == null || schoolYear.equals("")?"":" and t.school_year = '"+schoolYear+"' ";
		termCode = termCode == null || termCode.equals("") ? "" :" and t.term_code = '"+termCode+"' ";
		year = schoolYear.equals("")?null:year;
		String stu = businessDao.getStuSql(deptList, advancedList);
		teaNo = isMe?" and t.tea_id = '"+teaNo+"' ":" and t.tea_id <> '"+teaNo+"' ";
		nature = nature == null ? "  and t.COURSE_NATURE_CODE is null " :" and t."+Constant.CODE_COURSE_NATURE_CODE+" = '"+nature+"'";
		String teachSql ="select t.teachingclass_id as id from t_course_arrangement t where t.course_id = '"+courseId+"' "
				+schoolYear+termCode+ teaNo+nature+" group by t.teachingclass_id";
		String stuSql = "select stu.stu_id from t_class_teaching_stu stu,("+teachSql+") tea,("+stu+") xs where stu.teach_class_id = tea.id and stu.stu_id = xs.no_ group by stu.stu_id";
		String scoreSql = "select count(0) as value from "+table+" t,("+stuSql+") stu "
				+ "where t.stu_id = stu.stu_id"+schoolYear+termCode+" and t.course_id = '"+courseId+"'";
		String sql = scoreSql + " and t.ACCURACY = "+CODE_ACCURACY+"";
		int allCount = baseDao.queryForInt(scoreSql);
		int count = baseDao.queryForInt(sql);
		if(allCount == 0){return null;};
		Double scale = MathUtils.get2Point(MathUtils.getDivisionResult(count, allCount, 4)*100);
		return scale;
	}
	@Override
	public String queryDate(String table){
		String sql = "select max(date_) as value from "+table+"";
	   return baseDao.queryForString(sql);
	}
	
	@Override
	public List<Map<String,Object>> queryTimeList(String teaNo,String table){
		String sql = " select id,mc,sb from (select c.school_year||','||c.term_code as id,c.school_year|| '学年'||' '||"
				+ " (case when c.term_code = '"+Globals.TERM_SECOND+"' then '第二学期'else '第一学期' end )as mc,c.school_year as sb from t_course_arrangement a,"
				+ " t_class_teaching_stu b,"+table+" c where a.tea_id ='"+teaNo+"' "
				+ " and a.teachingclass_id  = b.teach_class_id and  b.stu_id = c.stu_id and a.school_year = c.school_year"
				+ "   and a.term_code = c.term_code and a.course_id = c.course_id ) group by id,mc,sb order by id desc ";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> queryTimeList(String teaNo){
		String sql = " select id,mc,sb from (select c.school_year||','||c.term_code as id,c.school_year|| '学年'||' '||"
				+ " (case when c.term_code = '"+Globals.TERM_SECOND+"' then '第二学期'else '第一学期' end )as mc,c.school_year as sb from t_course_arrangement a,"
				+ " t_class_teaching_stu b,t_stu_score_history c where a.tea_id ='"+teaNo+"' "
				+ " and a.teachingclass_id  = b.teach_class_id and  b.stu_id = c.stu_id and a.school_year = c.school_year"
				+ "   and a.term_code = c.term_code and a.course_id = c.course_code and c."+Cs_Sql+" ) group by id,mc,sb order by id desc ";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public String getYcSql(String schoolYear,String termCode,String courseId,String stuSql,String th,String table){
		String str = "";
		if(!th.equals("all")){
			String[] ary = th.split(",");
			String start = ary[0].equals("null") || ary[0] == null?
					"":" and a.predict_score >= '"+ary[0]+"'",
					end = ary[1].equals("null") || ary[1] == null?
					"":" and a.predict_score < '"+ary[1]+"'";
			str = start+end;
		}
		String sql = "select stu.*,a.predict_score as score from "+table+" a,("+stuSql+") stu "
				+ " where a.school_year = '"+schoolYear+"' and a.term_code = '"+termCode+"'"
				+ " and course_id = '"+courseId+"' "+str +" and a.stu_id = stu.no_";
		return sql;
	}
}
