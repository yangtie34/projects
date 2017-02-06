package cn.gilight.dmm.teaching.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.dao.ScorePredictGroupDao;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermGpMd;
import cn.gilight.dmm.teaching.entity.TStuScorepredTermGroup;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;
@Repository("scorePredictGroupDao")
public class ScorePredictGroupDaoImpl implements ScorePredictGroupDao {
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;

	@Override
	public List<String> queryGroupGradeList(int schoolYear,List<String> deptList){
		String classSql = businessDao.getClassesIdSqlByDeptList(deptList, schoolYear);
	    String sql = "select to_number(a.grade) as value from ("+classSql+") t,t_classes a "
	    		+ " where a.grade is not null and t.no = a.no_  "
	    		+ " group by a.grade ";
	    return baseDao.queryForListString(sql);
	}
	@Override
	public List<Map<String,Object>> queryAllCourseList(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode,String grade,
			  String yc_start_schoolYear,String yc_start_termCode,String yc_end_schoolYear,
			  String yc_end_termCode,Boolean isXx){
	    List<Map<String,Object>> termList = getYearTermAry(start_schoolYear, start_termCode, end_schoolYear, end_termCode),
	    		                 termList1 = getYearTermAry(yc_start_schoolYear, yc_start_termCode, yc_end_schoolYear, yc_end_termCode);
	    int i = 0,j=0;
	    String sql = "",str="",ycSql="";
	    for(Map<String,Object> termMap : termList){
	    	String schoolYear = MapUtils.getString(termMap, "schoolYear"),
	    		   termCode   = MapUtils.getString(termMap,"termCode"),
	    		   ycXq       = MapUtils.getString(termMap, "ycXq");
	    	String termSql = isXx?getStuXxCourseSql(schoolYear, termCode, grade, ycXq, true):getStuBxCourseSql(schoolYear, termCode, grade, ycXq, true),
	    		   strSql = isXx?getStuXxCourseSql(schoolYear, termCode, grade, ycXq, false):getStuBxCourseSql(schoolYear, termCode, grade, ycXq, false);
	    	if(i ==0){
	    		sql = termSql;
	    		str = strSql;
	    	}else{
	    		sql = sql+" union all "+termSql;
	    		str = str+" union all "+strSql;
	    	}
	    	i++;
	    }
	    String zStr = isXx?"":" and xx.dept = zz.dept ",
		       yStr =  isXx?"":" and xx.dept = yy.dept ";
	    if(termList.containsAll(termList1) && termList1.containsAll(termList)){
	    	sql = "select xx.*,yy.stu as allstu from ("+sql+") xx,("+str+") yy where "
//	        		+ " xx.stu > '"+Stu_Count_least+"'"
//	        		+ " and "
	        		+ " xx.kc = yy.kc and xx.xn = yy.xn and xx.xq = yy.xq "+yStr;
	    	return baseDao.queryListInLowerKey(sql);
	    }
	    int nj = EduUtils.getGradeByYearAndEnrollGrade(end_schoolYear, grade);
	    String yc_grade = EduUtils.getEnrollGradeByYearAndGrade(yc_end_schoolYear, nj);
	    for(Map<String,Object> termMap1 : termList1){
	    	String yc_schoolYear = MapUtils.getString(termMap1, "schoolYear"),
	    		   yc_termCode   = MapUtils.getString(termMap1,"termCode"),
	    		   yc_ycXq       = MapUtils.getString(termMap1, "ycXq");
	    	Boolean istrue = yc_schoolYear.equals(yc_end_schoolYear)?false:true;
	    	String yc_termSql =isXx?getStuXxCourseSql(yc_schoolYear, yc_termCode, yc_grade, yc_ycXq, istrue):getStuBxCourseSql(yc_schoolYear, yc_termCode, yc_grade, yc_ycXq, istrue);
	    	if(j ==0){
	    		ycSql = yc_termSql;
	    	}else{
	    		ycSql = ycSql+" union all "+yc_termSql;
	    	}
	    	j++;
	    }
    	sql = "select xx.*,yy.stu as allstu from ("+sql+") xx,("+str+") yy,("+ycSql+") zz where "
//    		+ " xx.stu > '"+Stu_Count_least+"'"
//    		+ " and "
    		+ " xx.kc = zz.kc "+zStr
    		+ " and xx.kc = yy.kc and xx.xn = yy.xn and xx.xq = yy.xq "+yStr;
	    return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> getWfzCourseList(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode,String type){
		 List<Map<String,Object>> termList = getYearTermAry(start_schoolYear, start_termCode, end_schoolYear, end_termCode);
		 String sql = "";int i = 0;   
		 for(Map<String,Object> termMap : termList){
		    	String schoolYear = MapUtils.getString(termMap, "schoolYear"),
		    		   termCode   = MapUtils.getString(termMap,"termCode"),
		    		   ycXq = MapUtils.getString(termMap,"ycXq");
		    	String str = " select a.course_code||','||a.course_attr_code||','||a.course_nature_code||','||a.course_category_code as kc,"
		    			+ " t.dept_id as dept,t.major_id as major,b.grade as grade,a.school_year as xn,a.term_code as xq,count(0) as stu,'"+ycXq+"' as ycxq "
		    			+ " from t_stu t,t_course_arrangement_plan a,t_classes b where "
		    			+ " a.school_year = '"+schoolYear+"' and a.term_code = '"+termCode+"' and  "
		    			+ " b.no_ = t.class_id and a.class_id = b.no_ group by a.course_code,a.course_attr_code,a.course_nature_code,a.course_category_code,"
		    			+ " t.dept_id,b.grade,a.school_year,a.term_code,t.major_id ";
		    	String str1 = " select a.scoure_code||','||a.course_attr_code||','||a.course_nature_code||','||a.course_category_code as kc,"
		    			+ " t.dept_id as dept,t.major_id as major,b.grade as grade,a.school_year as xn,a.term_code as xq,count(0) as stu,'"+ycXq+"' as ycxq "
		    			+ " from t_stu t,t_stu_course_choose a,t_classes b where "
		    			+ " a.school_year = '"+schoolYear+"' and a.term_code = '"+termCode+"' "
		    			+ " and a.stu_id = t.no_ and t.class_id = b.no_  group by a.scoure_code,a.course_attr_code,a.course_nature_code,a.course_category_code,"
		    			+ " t.dept_id,b.grade,a.school_year,a.term_code,t.major_id ";
		    	
		    	if(type.equals("xx")){
		    		str = str1;
		    	}else if(type.equals("all")){
		    		str = str+" union all "+str1;
		    	}
		    	
		    	if(i==0){
		    		sql = str;
		    	}else{
		    		sql += " union all "+str;
		    	}
		    	i++;
		    }
		 return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> getWfzCourseList(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode,String grade,Boolean hasScore,Boolean isXx){
		 List<Map<String,Object>> termList = getYearTermAry(start_schoolYear, start_termCode, end_schoolYear, end_termCode);
		 String sql = "",sql1="";int i = 0;   
		 for(Map<String,Object> termMap : termList){
		    	String schoolYear = MapUtils.getString(termMap, "schoolYear"),
		    		   termCode   = MapUtils.getString(termMap,"termCode"),
		    		   ycXq = MapUtils.getString(termMap,"ycXq");
		    	if(schoolYear.equals(end_schoolYear) && termCode.equals(end_termCode)){
		    		hasScore = false;
		    	}
		    	String temp = " select kc,xq,xn,count(0) as bj,ycxq,sum(stu) as stu,dept from (select y.course_code||','||y.course_attr_code||','||y.course_nature_code||','||y.course_category_code as kc,"
		    			+ " y.term_code as xq, y.school_year as xn,x.class_id as bj,'"+ycXq+"' as ycxq,count(0) as stu,x.dept_id as dept from t_stu x,t_course_arrangement_plan y,t_classes z where "
		    			+ " y.school_year = '"+schoolYear+"' and y.term_code = '"+termCode+"' "
		    			+ " and z.no_ = y.class_id and x.class_id = z.no_ "
		    			+ " and z.grade = '"+grade+"' "
		    			+ " group by y.course_code,y.course_attr_code,y.course_nature_code,course_category_code,y.school_year,y.term_code,x.dept_id,x.class_id)"
		    			+ " group by kc,xq,xn,ycxq,dept ";
		    	if(isXx){
		    		temp = getStuXxCourseSql(schoolYear, termCode, grade, ycXq, false);
		    	}
		    	String str = hasScore?(isXx?getStuXxCourseSql(schoolYear, termCode, grade, ycXq, true):getStuBxCourseSql(schoolYear, termCode, grade, ycXq, true)):temp;
		    	String str1 = getStuBxCourseSql(schoolYear, termCode, grade, ycXq, false);
		    	if(i==0){
		    		sql = str;
		    		sql1 = str1;
		    	}else{
		    		sql += " union all "+str;
		    		sql1 += " union all "+str1;
		    	}
		    	i++;
		    }
		 String yStr =  isXx?"":" and xx.dept = yy.dept ";
		 sql = "select xx.*,yy.stu as allstu from ("+sql+") xx,("+sql1+") yy where "
//		    		+ " xx.stu > '"+Stu_Count_least+"'"
//		    		+ " and "
		    		+ "  xx.kc = yy.kc and xx.xn = yy.xn and xx.xq = yy.xq "+yStr;
		 return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public Map<String,Object> getBjXsList(Map<String,Object> courseMap,String grade,Boolean hasScore){
			String kc = MapUtils.getString(courseMap, "kc"),
				   xn = MapUtils.getString(courseMap,"xn"),
				   xq = MapUtils.getString(courseMap, "xq"),
				   deptId = MapUtils.getString(courseMap,"dept");
			String[] ary = kc.split(",",-1);
			String kcId = ary[0],
				   kcSx= (ary[1].equals("")|| ary[1].equals("null"))?null:ary[1],
				   kcXz= (ary[2].equals("")|| ary[1].equals("null"))?null:ary[2],
				   kcLb= (ary[3].equals("")|| ary[1].equals("null"))?null:ary[3];
			String allXsSql =" select t.no_||','||t.major_id from t_stu t,t_course_arrangement_plan a,t_classes b where "
					+ " a.course_code = '"+kcId+"' and t.dept_id = '"+deptId+"' "
					+ " and a.school_year = '"+xn+"' and a.term_code = '"+xq+"' and b.no_ = a.class_id and t.class_id = b.no_ "
					+ " and  b.grade = '"+grade+"' "
					+ (kcSx==null?" and a.course_attr_code is null ":" and a.course_attr_code ='"+kcSx+"' ")
					+ (kcXz==null?" and a.course_nature_code is null ":" and a.course_nature_code = '"+kcXz+"' ")
					+ (kcLb==null?" and a.course_category_code is null ":" and a.course_category_code = '"+kcLb+"' ")
					+ " group by t.no_ ,t.major_id ";
			String xsSql = " select t.no_||','||t.major_id from t_stu t,t_course_arrangement_plan a,t_stu_score_history b,t_classes c where "
					+ " a.course_code = '"+kcId+"' and t.dept_id = '"+deptId+"' "
					+ " and a.school_year = '"+xn+"' and a.term_code = '"+xq+"' "
					+ " and c.grade = '"+grade+"' "
					+ " and a.school_year = b.school_year and a.term_code = b.term_code "
					+ " and a.course_code = b.course_code and t.no_ = b.stu_id and b.cs ='1' and c.no_ = t.class_id "
					+ " and t.class_id = c.no_ "
					+ (kcSx==null?" and a.course_attr_code is null ":" and a.course_attr_code ='"+kcSx+"' ")
					+ (kcXz==null?" and a.course_nature_code is null ":" and a.course_nature_code = '"+kcXz+"' ")
					+ (kcLb==null?" and a.course_category_code is null ":" and a.course_category_code = '"+kcLb+"' ")
					+ " group by t.no_,t.major_id ";
			String bjSql = " select t.class_id from t_stu t,t_course_arrangement_plan a,t_stu_score_history b,t_classes c where "
					+ " a.course_code = '"+kcId+"' and t.dept_id = '"+deptId+"' "
					+ " and a.school_year = '"+xn+"' and a.term_code = '"+xq+"' "
					+ " and c.grade = '"+grade+"' "
					+ " and a.class_id = c.no_ "
					+ " and a.school_year = b.school_year and a.term_code = b.term_code "
					+ " and a.course_code = b.course_code and t.no_ = b.stu_id and b.cs ='1' and c.no_ = t.class_id "
					+ (kcSx==null?" and a.course_attr_code is null ":" and a.course_attr_code ='"+kcSx+"' ")
					+ (kcXz==null?" and a.course_nature_code is null ":" and a.course_nature_code = '"+kcXz+"' ")
					+ (kcLb==null?" and a.course_category_code is null ":" and a.course_category_code = '"+kcLb+"' ")
					+ " group by t.class_id ";
			String allBjSql = " select t.class_id from t_stu t,t_course_arrangement_plan a,t_classes b where "
					+ " a.course_code = '"+kcId+"' and t.dept_id = '"+deptId+"' "
					+ " and a.school_year = '"+xn+"' and a.term_code = '"+xq+"' and t.class_id = b.no_ and a.class_id = b.no_ "
					+ " and b.grade = '"+grade+"' "
					+ (kcSx==null?" and a.course_attr_code is null ":" and a.course_attr_code ='"+kcSx+"' ")
					+ (kcXz==null?" and a.course_nature_code is null ":" and a.course_nature_code = '"+kcXz+"' ")
					+ (kcLb==null?" and a.course_category_code is null ":" and a.course_category_code = '"+kcLb+"' ")
					+ " group by t.class_id  ";
			List<String> allStuList = baseDao.queryForListString(allXsSql),
					     stu = hasScore?baseDao.queryForListString(xsSql):allStuList,
					     bj = hasScore?baseDao.queryForListString(bjSql):baseDao.queryForListString(allBjSql);
			courseMap.put("allStuList", allStuList);
			courseMap.put("stuList", stu);
			courseMap.put("bjList", bj);
		return courseMap;
	}
	@Override
	public Map<String,Object> getXxCourseStuList(Map<String,Object> courseMap,String grade,Boolean hasScore){
			String kc = MapUtils.getString(courseMap, "kc"),
				   xn = MapUtils.getString(courseMap,"xn"),
				   xq = MapUtils.getString(courseMap, "xq");
			String[] ary = kc.split(",",-1);
			String kcId = ary[0],
				   kcSx= (ary[1].equals("")|| ary[1].equals("null"))?null:ary[1],
				   kcXz= (ary[2].equals("")|| ary[1].equals("null"))?null:ary[2],
				   kcLb= (ary[3].equals("")|| ary[1].equals("null"))?null:ary[3];
			String allXsSql =" select t.no_ from t_stu t,t_stu_course_choose a,t_classes b where "
					+ " a.scoure_code = '"+kcId+"' "
					+ " and a.school_year = '"+xn+"' and a.term_code = '"+xq+"' and t.no_ = a.stu_id "
					+ " and b.grade = '"+grade+"' and t.class_id = b.no_ "
					+ (kcSx==null?" and a.course_attr_code is null ":" and a.course_attr_code ='"+kcSx+"' ")
					+ (kcXz==null?" and a.course_nature_code is null ":" and a.course_nature_code = '"+kcXz+"' ")
					+ (kcLb==null?" and a.course_category_code is null ":" and a.course_category_code = '"+kcLb+"' ")
					+ " group by t.no_ ";
			String xsSql = " select t.no_ from t_stu t,t_stu_course_choose a,t_stu_score_history b,t_classes c where "
					+ " a.scoure_code = '"+kcId+"' "
					+ " and a.school_year = '"+xn+"' and a.term_code = '"+xq+"' "
					+ " and c.grade = '"+grade+"' and t.class_id = c.no_ "
					+ " and a.school_year = b.school_year and a.term_code = b.term_code "
					+ " and a.scoure_code = b.course_code and t.no_ = b.stu_id and b.cs ='1' and a.stu_id = t.no_ "
					+ (kcSx==null?" and a.course_attr_code is null ":" and a.course_attr_code ='"+kcSx+"' ")
					+ (kcXz==null?" and a.course_nature_code is null ":" and a.course_nature_code = '"+kcXz+"' ")
					+ (kcLb==null?" and a.course_category_code is null ":" and a.course_category_code = '"+kcLb+"' ")
					+ " group by t.no_";
			List<String> allStuList = baseDao.queryForListString(allXsSql),
					     stu = hasScore?baseDao.queryForListString(xsSql):allStuList;
			courseMap.put("allStuList", allStuList);
			courseMap.put("stuList", stu);
		return courseMap;
	}
	/**
	 * 根据开始学年，开始学期，结束学年，结束学期获取之前所有的学年学期
	 * @param start_schoolYear
	 * @param start_termCode
	 * @param end_schoolYear
	 * @param end_termCode
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getYearTermAry(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode){
            int start_year = Integer.parseInt(start_schoolYear.substring(0,4)),
                end_year   = Integer.parseInt(end_schoolYear.substring(0,4));
            List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
            for(int i=start_year;i<end_year+1;i++){
            	for(int k =0;k<2;k++){
            		if(i==start_year && start_termCode.equals(Globals.TERM_SECOND) && k==0){
            			continue;
            		}else if(i==end_year && end_termCode.equals(Globals.TERM_FIRST) && k==1){
            			continue;
            		}
            		String schoolYear = String.valueOf(i)+"-"+String.valueOf(i+1);
            		String termCode = "",ycXq ="last";
            		if(k==0){
            			termCode = Globals.TERM_FIRST;
            		}else{
            			termCode=Globals.TERM_SECOND;
            		}
            		if(schoolYear.equals(end_schoolYear) && termCode.equals(end_termCode)){
            			ycXq = "next";
            		}
            		Map<String,Object> ary = new HashMap<String, Object>();
            		ary.put("schoolYear",schoolYear);
            		ary.put("termCode",termCode);
            		ary.put("ycXq",ycXq);
            		result.add(ary);
            	}
            }
            return result;
	}
	@Override
	public List<TStuScorepredTermGroup> getGroup(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode,int truth,int iselective){
		String str = " from t_Stu_scorepred_term_group t,t_stu_scorepred_term_gp_md a where "
				+ " t.id = a.group_id  and t.start_schoolyear = '"+start_schoolYear+"' "
				+ " and t.start_termcode = '"+start_termCode+"' and t.end_schoolyear = '"+end_schoolYear+"'"
				+ " and t.end_termcode = '"+end_termCode+"' and t.truth = "+truth+" and t.iselective = '"+iselective+"'";
		String kcSql="select a.* "+str;
		String groupSql = "select t.* "+str;
		List<TStuScorepredTermGpMd> kcList = baseDao.queryForListBean(kcSql, TStuScorepredTermGpMd.class);
		List<TStuScorepredTermGroup> groupList = baseDao.queryForListBean(groupSql, TStuScorepredTermGroup.class);
		for(TStuScorepredTermGroup group : groupList){
			String id = group.getId();
			List<TStuScorepredTermGpMd> kcList_1 = new ArrayList<TStuScorepredTermGpMd>();
			for(TStuScorepredTermGpMd kc : kcList){
				String groupid = kc.getGroupId();
				if(id.equals(groupid)){
					kcList_1.add(kc);
				}
			}
			group.setList(kcList_1);
		}
		return groupList;
	}
	private String getStuBxCourseSql(String schoolYear,String termCode,String grade,String ycXq,Boolean hasScore){
		String sql = " select kc||','||sx||','||xz||','||lb as kc, xq,xn,count(0) as bj,'"+ycXq+"' as ycxq,sum(value) as stu,dept from ( "
	               +"  select b.course_code kc,a.class_id bj,c.course_attr_code sx,a.dept_id as dept, "
	               +"  c.course_nature_code xz,'"+schoolYear+"' as xn,'"+termCode+"' as xq,c.course_category_code as lb,count(0) as value "
	               +"  from t_stu a,t_stu_score_history b,t_course_arrangement_plan c,t_classes d"
	               +"  where a.no_ = b.stu_id and a.class_id = d.no_ and d.no_ = c.class_id "
	               +"  and b.school_year = c.school_year "
	               +"  and b.term_code = c.term_code and b.term_code = '"+termCode+"'"
	               +"  and b.school_year = '"+schoolYear+"' and d.grade = '"+grade+"'"
	               +"  and b.course_code = c.course_code "
	               + " and b.cs ='1' and a.dept_id is not null "
	               +"  group by b.course_code,a.class_id,c.course_attr_code,"
	               +"  c.course_nature_code,c.course_category_code,a.dept_id) "
	               +"  group by kc, sx, xz,xn,xq,lb,dept ";
		if(!hasScore){
			sql = " select y.course_code||','||y.course_attr_code||','||y.course_nature_code||','||y.course_category_code as kc,"
	    			+ " y.term_code as xq, y.school_year as xn,0 as bj,'"+ycXq+"' as ycxq,count(0) as stu,x.dept_id as dept from t_stu x,t_course_arrangement_plan y,t_classes z where "
	    			+ " y.school_year = '"+schoolYear+"' and y.term_code = '"+termCode+"' "
	    			+ " and x.class_id = z.no_ and y.class_id = z.no_ and z.grade = '"+grade+"' "
	    			+ " group by y.course_code,y.course_attr_code,y.course_nature_code,course_category_code,y.school_year,y.term_code,x.dept_id ";
		}
		return sql;
	}
	private String getStuXxCourseSql(String schoolYear,String termCode,String grade,String ycXq,Boolean hasScore){
		String sql = " select kc||','||sx||','||xz||','||lb as kc, xq,xn,'"+ycXq+"' as ycxq,sum(value) as stu from ( "
	               +"  select b.course_code kc,c.course_attr_code sx, "
	               +"  c.course_nature_code xz,'"+schoolYear+"' as xn,'"+termCode+"' as xq,c.course_category_code as lb,count(0) as value "
	               +"  from t_stu a,t_stu_score_history b,t_stu_course_choose c,t_classes d"
	               +"  where a.no_ = b.stu_id and a.no_ = c.stu_id "
	               +"  and b.school_year = c.school_year "
	               +"  and b.term_code = c.term_code and b.term_code = '"+termCode+"'"
	               +"  and b.school_year = '"+schoolYear+"' and d.grade = '"+grade+"'"
	               +"  and b.course_code = c.scoure_code "
	               + " and a.class_id = d.no_ "
	               + " and b.cs ='1' and a.dept_id is not null "
	               +"  group by b.course_code,a.class_id,c.course_attr_code,"
	               +"  c.course_nature_code,c.course_category_code) "
	               +"  group by kc, sx, xz,xn,xq,lb ";
		if(!hasScore){
			sql = "select y.scoure_code||','||y.course_attr_code||','||y.course_nature_code||','||y.course_category_code as kc,"
	    			+ " y.term_code as xq,y.school_year as xn,'"+ycXq+"' as ycxq,count(0) as stu from t_stu x,t_stu_course_choose y,t_classes z where "
	    			+ " y.school_year = '"+schoolYear+"' and y.term_code = '"+termCode+"' "
	    			+ " and x.no_ = y.stu_id  and z.grade = '"+grade+"' and z.no_ = x.class_id "
	    			+ " group by y.scoure_code,y.course_attr_code,y.course_nature_code,course_category_code,y.school_year,y.term_code ";
		}
		return sql;
	}
/*	@Override
	public List<TStuScorepredTermGroup> queryGroupData(String start_schoolYear,String start_termCode,String end_schoolYear,String end_termCode){

		return ;
	}*/
	public static void main(String[] args){
		String str ="1,,";
		String[] ary = str.split(",",-1);
		System.out.println(ary[0]);
		System.out.println(ary[1]);
		System.out.println(ary[2]);
	}
}
