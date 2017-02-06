package cn.gilight.dmm.xg.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.management.Query;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Repository("stuCostDao")
public class StuCostDaoImpl implements StuCostDao {
	@Resource
	private BaseDao basedao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
 	
	@Override
	public List<Map<String,Object>> querySchoolStart(){
		String sql ="select t.start_date as startdate,t.end_date as enddate,t.school_year as schoolyear,"
				+ " t.term_code as term from t_school_start t order by t.school_year desc,t.term_code desc ";
		return basedao.queryListInLowerKey(sql);	
	}
	@Override
	public String queryXflx(){
		String sql ="select wm_concat(t.name_) as mc from t_code_card_deal t where t.id in ("+Constant.Code_Card_Deal_Type+")";
		return basedao.queryForString(sql);	
	}
	@Override
	public List<Map<String,Object>> querySchoolStart(String day){
		String sql ="select t.start_date as startdate,t.end_date as enddate,t.school_year as schoolyear,"
				+ " t.term_code as term from t_school_start t where t.start_date < '"+day+"' order by t.school_year desc,t.term_code desc ";
		return basedao.queryListInLowerKey(sql);	
	}
	@Override
	public List<Map<String,Object>> queryMonthList(){
		String sql ="select t.start_date as startdate,t.end_date as enddate, "
				+ "  t.school_year as schoolyear,t.term_code as term,t.month "
				+ "  from t_stu_cost_time_month t group by t.start_date,t.end_date,t.school_year,t.term_code,t.month "
				+ "  order by t.school_year desc,t.term_code desc,month desc";
		return basedao.queryListInLowerKey(sql);	
	}
	@Override
	public String queryMonthMaxEnd(){
		String sql ="select max(t.end_date) as enddate "
				+ "  from t_stu_cost_time_month t  ";
		return basedao.queryForString(sql);	
	}
	@Override
	public int queryMaxMonth(String schoolYear,String termCode){
		String sql ="select max(t.month) as max "
				+ "  from t_stu_abnormal_month_result t where t.school_year = '"+schoolYear+"'"
						+ " and t.term_code = '"+termCode+"'";
		return basedao.queryForInt(sql);	
	}
//	@Override
//	public int queryCountByDept(String schoolYear,String term,int weekth,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
//		String wheresql = " where t.school_year= '"+schoolYear+"' and t.term_code = '"+term+"' and t.weekth = "+weekth+" and t.type_ = '"+type[3]+"' ";//选择某一周的条件sql
//		int year = Integer.parseInt(schoolYear.substring(0,4));
//		String stu = businessDao.getStuSql(year, deptList, stuAdvancedList);
//		String sql = "select count(0) from t_stu_abnormal_week_result t inner join ("+stu+") stu on t.stu_id = stu.no_"
//				+ wheresql;
//	    Integer value = basedao.queryForInt(sql); 
//	  return value ==null?0:value;
//	}
	@Override
	public int getDeptCost(int schoolYear,String term,int month,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String stu =  businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
		String year = String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
		String countSql = "select nvl(count(0),0) from t_stu_abnormal_month_result t,("+stu+") a where "
				+ " t.stu_id = a.no_ and t.school_year ='"+year+"' and t.term_code ='"+term+"'"
				+ " and t.month = '"+month+"' and t.type_ = '"+type[3]+"'";
		int count = basedao.queryForInt(countSql);
		return count;
	}
	@Override
	public List<Map<String,Object>> queryCountByDept(String schoolYear,String term,int month,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String wheresql = " where t.school_year= '"+schoolYear+"' and t.term_code = '"+term+"' and t.month = "+month+" and t.type_ = '"+type[3]+"' ";//选择某一周的条件sql
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String stu = businessDao.getStuSql(year, deptList, stuAdvancedList);
		String sql = "select stu.dept_id,stu.major_id,stu.class_id ,count(0) as value from t_stu_abnormal_month_result t ,("+stu+") stu "
				+ wheresql+" and  t.stu_id = stu.no_ group by stu.dept_id,stu.major_id,stu.class_id";
		
		String deptSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), sql, false, true, false, false, year, null);
	    deptSql = "select next_dept_code,next_dept_name ,sum(value) as value from ("+deptSql+") group by next_dept_code,next_dept_name ";
		String orderSql = businessService.getNextLevelOrderSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), sql, false, true, false, false, year, null);
	    String allStuSql = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), stu,false, true, false, false, year, null);
	    String str = "select t.* from t_stu_abnormal_mail_month t "+wheresql;
	    String reSql= "select a.next_dept_code as id,a.next_dept_name as name,nvl(d.status_,2) as status,a.next_dept_count as stucount,nvl(sum(b.value),0) as count,round((nvl(sum(b.value),0)*100/a.next_dept_count),2) as scale ,"
	    		+ " c.next_dept_order from ("+allStuSql+") a left join ("+deptSql+") b on a.next_dept_code = b.next_dept_code inner join ("+orderSql+") c on a.next_dept_code = c.id "
	    		+ " left join ("+str+") d on a.next_dept_code = d.dept_id where a.next_dept_code is not null"
	    		+ " group by a.next_dept_code,a.next_dept_name,a.next_dept_count,c.next_dept_order,d.status_ order by c.next_dept_order";
	  return basedao.queryListInLowerKey(reSql);
	}
	@Override
	public List<Map<String,Object>> queryCountByDept(String schoolYear,String term,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String wheresql = " where t.school_year= '"+schoolYear+"' and t.term_code = '"+term+"' ";//选择某一周的条件sql
		Double val =queryStandard(schoolYear, term, Constant.Type_DayCost_Standard, type);//type是指高消费或者低消费。高消费：{"+",">"}
		if(val == null){return null;};
		int days = getStartAndEnd(schoolYear, term);
		val  = val*days;
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String stu = businessDao.getStuSql(year, deptList, stuAdvancedList);
		String sql = "select stu.dept_id,stu.major_id,stu.class_id,count(0) as value from t_stu_abnormal_term t inner join ("+stu+") stu on t.stu_id = stu.no_"
				+ wheresql+" and t.sum_money "+type[1]+val+" group by stu.dept_id,stu.major_id,stu.class_id";
		String deptSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), sql, false, true, false, false, year, null);
	    String orderSql = businessService.getNextLevelOrderSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), sql, false, true, false, false, year, null);
	    String allStuSql = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), stu,false, true, false, false, year, null);
	    String reSql= "select a.next_dept_code as code,a.next_dept_name as name,a.next_dept_count as stucount,nvl(sum(b.value),0) as count,round((nvl(sum(b.value),0)*100/a.next_dept_count),2) as scale ,"
	    		+ " c.next_dept_order from ("+allStuSql+") a left join ("+deptSql+") b on a.next_dept_code = b.next_dept_code inner join ("+orderSql+") c on a.next_dept_code = c.id "
	    		+ " group by a.next_dept_code,a.next_dept_name,a.next_dept_count,c.next_dept_order order by c.next_dept_order";
	  return basedao.queryListInLowerKey(reSql);
	}
	@Override
	public int queryCountByDept(String schoolYear,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
//		String wheresql = " where t.school_year= '"+schoolYear+"'";//选择某一周的条件sql
////		Double val =queryCostValue("t_stu_abnormal_year", type, wheresql);//type是指高消费或者低消费。高消费：{"+",">"}
//		int year = Integer.parseInt(schoolYear.substring(0,4));
//		String stu = businessDao.getStuSql(year, deptList, stuAdvancedList);
//		String sql = "select count(0) from t_stu_abnormal_year t inner join ("+stu+") stu on t.stu_id = stu.no_"
//				+ wheresql+" and t.sum_money "+type[1]+val+" ";
//	    Integer value = basedao.queryForInt(sql); 
//	  return value ==null?0:value;
		return 0;
	}
	@Override
	public List<Map<String,Object>> queryTermData(String schoolYear,String termCode,String column,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		//各年级、性别，高、低消费
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String stu = businessDao.getStuSql(year,deptList,stuAdvancedList);
		String wheresql = " where t.school_year='"+schoolYear+"' and  t.term_code = '"+termCode+"'";//选择某一周的条件sql
		Double val =queryStandard(schoolYear, termCode, Constant.Type_DayCost_Standard, type);//type是指高消费或者低消费。高消费：{"+",">"}
		if(val == null){return null;}
		int days = getStartAndEnd(schoolYear, termCode);
		val  = val*days;
		String sql = "select b."+column+" as name,count(0) as value from t_stu_abnormal_term t "
				+ " inner join ("+stu+") b on t.stu_id = b.no_ "
				+ wheresql +"and  t.sum_money "+type[1]+val+" group by b."+column+" ";
		return basedao.queryListInLowerKey(sql);
	}
	@Override
	public List<Map<String,Object>> queryYearData(String schoolYear,String column,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
//		//各年级、性别，高、低消费
//		int year = 0;String stu = "";
//		String[] yearAry = schoolYear.split(",");
//		for(int i=0;i<yearAry.length;i++){
//			year = Integer.parseInt(yearAry[i].substring(0, 4));
//			if( i == 0){
//				stu = businessDao.getStuSql(year,deptList,stuAdvancedList);
//			}else{
//				stu += " union "+ businessDao.getStuSql(year,deptList,stuAdvancedList);
//			}
//		}
//		String wheresql = " where t.school_year in ("+businessDao.formatInSql(schoolYear)+")";//选择某一周的条件sql
//		Double val =queryCostValue("t_stu_abnormal_year", type, wheresql);//type是指高消费或者低消费。高消费：{"+",">"}
//		String sql = "select b."+column+" as name,count(0) as value from t_stu_abnormal_year t  "
//				+ " inner join ("+stu+") b on t.stu_id = b.no_ "
//				+ wheresql +"and  t.sum_money "+type[1]+val+" group by b."+column+" ";
//		return basedao.queryListInLowerKey(sql);
		return null;
	}
	@Override
	public int queryTermByOther(String schoolYear,String termCode,String table,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		//助学金，助学贷款，学费减免，高、低消费
		String wheresql = " where t.school_year='"+schoolYear+"' and  t.term_code = '"+termCode+"'";//选择某一周的条件sql
		Double val =queryStandard(schoolYear, termCode, Constant.Type_DayCost_Standard, type);//type是指高消费或者低消费。高消费：{"+",">"}
		if(val == null){return 0;}
		String stu = businessDao.getStuSql(Integer.parseInt(schoolYear.substring(0,4)),deptList,stuAdvancedList);
		int days = getStartAndEnd(schoolYear, termCode);
		val  = val*days;
		String sql = "select count(0) as value from t_stu_abnormal_term t  "
				+ " inner join ("+table+") b on t.stu_id = b.stu_id inner join ("+stu+") c on t.stu_id = c.no_ and t.school_year = b.school_year"
				+ wheresql+" and  t.sum_money "+type[1]+val+" ";
		int count = basedao.queryForInt(sql);
		return count;
	}
	@Override
	public int queryYearByOther(String schoolYear,String table,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		//助学金，助学贷款，学费减免，高、低消费
//		String wheresql = " where t.school_year='"+schoolYear+"'";//选择某一周的条件sql
//		Double val =queryCostValue("t_stu_abnormal_term", type, wheresql);//type是指高消费或者低消费。高消费：{"+",">"}
//		String sql = "select count(0) as value from t_stu_abnormal_term t "
//				+ " inner join ("+table+") b on t.stu_id = b.stu_id and t.school_year = b.school_year"
//				+ wheresql+" and  t.sum_money "+type[1]+val+" ";
//		int count = basedao.queryForInt(sql);
//		return count;
		return 0;
	}
	@Override
	public List<Map<String,Object>> queryCountByCbdm(String schoolYear,String termCode,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		Long begin = System.currentTimeMillis();
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String stu = businessDao.getStuSql(year,deptList,stuAdvancedList);
		List<String> matchList = new ArrayList<>();
		Double bval =queryStandard(schoolYear, termCode, Constant.Type_BreakFastCost_Standard, type);
		Double lval =queryStandard(schoolYear, termCode, Constant.Type_LunchCost_Standard, type);
		Double dval =queryStandard(schoolYear, termCode, Constant.Type_DinnerCost_Standard, type);//type是指高消费或者低消费。高消费：{"+",">"}
		System.out.println(new Double(System.currentTimeMillis()-begin)/1000);
		String bSql =bval==null?"":" (t.meal_name = '"+ Constant.Meal_Time_Group[0][0]+"' and t.sum_money "+type[1]+bval+") ";
		String lSql =lval==null?"":" (t.meal_name = '"+ Constant.Meal_Time_Group[1][0]+"' and t.sum_money "+type[1]+lval+") ";
		String dSql =dval==null?"":" (t.meal_name = '"+ Constant.Meal_Time_Group[2][0]+"' and t.sum_money "+type[1]+dval+") ";
		if(!bSql.equals("")){ 
		   matchList.add(bSql);
		}
		if(!lSql.equals("")){ 
		   matchList.add(lSql);
		}
		if(!dSql.equals("")){ 
		   matchList.add(dSql);
		}
		String str = "";
		if (!matchList.isEmpty()){
			str = " and ("+StringUtils.join(matchList, " or ")+")";
		}
		String sql = "select t.meal_name as code,t.meal_name as name,count(0) as value from t_stu_abnormal_meal t,("+stu+") a "
				+" where t.school_year='"+schoolYear+"' and t.term_code ='"+termCode+"' and t.stu_id = a.no_ "
				+  str
			    + " group by t.meal_name";
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		begin = System.currentTimeMillis();
		List<Map<String,Object>> list =  basedao.queryListInLowerKey(sql);
		System.out.println(new Double(System.currentTimeMillis()-begin)/1000);
		return  list;
	}
//	@Override
//	public Map<String,Object> queryLastWeekth(String schoolYear,String termCode){
//		String sql = "select start_date as startdate,end_date as enddate from t_school_start where school_year = '"+schoolYear+"' and "
//				+ " term_code = '"+termCode+"'";
//		     Map<String,Object> map = basedao.queryMapInLowerKey(sql);
//		return map;
//	}
	@Override
	public List<Map<String,Object>> queryGrade(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
		/*
		 * 1.查询出当前学校最长的学制。
		 * 2.查询出各年级对应的年份。
		 * 3.根据学制取出年级和对应年份的结果list。
		 * 注意事项:需要维护好t_stu中的length_schooling字段，为空默认为5。
		 */
		String stu = businessDao.getStuSql(schoolYear,deptList,null);
		String str = "select max(stu.length_schooling) as xz from ("+stu+") stu ";
		Map<String,Object> map = basedao.queryMapInLowerKey(str);
		String sql = "select value,name,code from ( select "+schoolYear+" as name, '"+EduUtils.getNjNameByCode(1)+"' as value,0 as field,'1' as code from dual"
				+ " union select "+(schoolYear-1)+" as name,  '"+EduUtils.getNjNameByCode(2)+"' as value,0 as field,'2' as code  from dual"
			    + " union select "+(schoolYear-2)+" as name,  '"+EduUtils.getNjNameByCode(3)+"' as value ,0 as field,'3' as code from dual"
		        + " union select "+(schoolYear-3)+" as name,  '"+EduUtils.getNjNameByCode(4)+"' as value ,0 as field,'4' as code from dual"
		        + " union select "+(schoolYear-4)+" as name,  '"+EduUtils.getNjNameByCode(5)+"' as value,0 as field,'5' as code  from dual )"
		        + " order by name desc";//查询年级和年份的对应关系
		  List<Map<String ,Object>> list =  basedao.queryListInLowerKey(sql);
		List<Map<String ,Object>> result = new ArrayList<Map<String,Object>>();//结果集
		int xz = 5;//最长学制初始化
		if (map!=null&&!map.isEmpty()){//如果学制不是空数据的话
			  xz = MapUtils.getIntValue(map, "xz");
		}
		if(xz>5){
			xz = 5;
		}
		for (int i=0;i<xz;i++){//根据最长学制取出年级对应的年份。
			Map<String,Object> map1 = list.get(i);
			result.add(i, map1);
		}
		return result;
	}
	@Override
	public List<Map<String,Object>> querySexList(){
		String sql = "select code_ as code,code_ as name,name_ as value,0 as field from t_code where code_type='SEX_CODE'";
		List<Map<String,Object>> sex = basedao.queryListInLowerKey(sql); 
		return sex;
	}
	@Override
	public List<Map<String,Object>> queryExportData(String schoolYear,String term,int month,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList,String type_){
		String wheresql = " where t.school_year= '"+schoolYear+"' and t.term_code = '"+term+"' and t.month = "+month+" and t.type_ = '"+type_+"'";//选择某一周的条件sql;
		int year = Integer.parseInt(schoolYear.substring(0,4));
		String stu = businessDao.getStuSql(year,deptList,stuAdvancedList);
		String sql= "select t.stu_id as xh,stu.enroll_grade as rxnj,a.name_ as zy,b.name_ as bj,stu.name_ as mc,t.sum_money as je from t_stu_abnormal_month_result t inner join ("+stu+") stu"
				+ " on t.stu_id = stu.no_ inner join t_code_dept_teach a on stu.major_id = a.id inner join "
				+ " t_classes b on stu.class_id = b.no_ "+ wheresql + " order by t.sum_money desc" ;
		return basedao.queryListInLowerKey(sql);
	}
	@Override
	public int queryCount(String table,String schoolYear,String termCode,String[] type,List<String> deptList,List<AdvancedParam> stuAdvancedList){
		String term = termCode;
		int year = Integer.parseInt(schoolYear.substring(0, 4));
		if(termCode == null){
			termCode ="";
		}else{	
			termCode = " and t.term_code = '"+termCode+"' ";
		}
		String str = "";
		if(type!=null){
		Double val =queryStandard(schoolYear, term, Constant.Type_DayCost_Standard, type);//type是指高消费或者低消费。高消费：{"+",">"}
		if (val == null){return 0;}
		int days = getStartAndEnd(schoolYear, term);
		val  = val*days;
		str = " and  t.sum_money "+type[1]+val+" ";
		}
		String stu = businessDao.getStuSql(year,deptList, stuAdvancedList);
		String sql = " select count(0) from (select stu.no_ as name, count(0) as value from "+table+" t inner join ("+stu+") stu "
				+ " on  t.stu_id = stu.no_ where t.school_year = '"+schoolYear+"' "+termCode+str+"  group by stu.no_ )";
		int count = basedao.queryForInt(sql);
		return count;
	}
	/**
	 * 得到学院email地址
	 */
	@Override
	public List<Map<String, Object>> queryEmailList() {
		// 学院ID email地址
		String sql="SELECT * FROM T_EMAIL";
		return basedao.getJdbcTemplate().queryForList(sql);
	}
	@Override
    public int querySendStatus(String schoolYear,String termCode,int month,String deptid,String type){
    	String sql = "select t.status_  from t_stu_abnormal_mail_month t where t.school_year = '"+schoolYear+"' "
    			+ " and t.term_code = '"+termCode+"' and t.month = "+month+" and t.dept_id = '"+deptid+"'"
    					+ " and t.type_  = '"+type+"' ";
    	Map<String, Object> map = basedao.queryMapInLowerKey(sql);
    	int value = 0;
    	if(map==null||map.isEmpty()){
    	    value = 2;
    	}else{
    		value = MapUtils.getIntValue(map, "status_");
    	}
    	return value;
    }
	@Override
	public Double queryStandard(String schoolYear,String termCode,String type_,String[] type){
		String sql = "select t."+type[4]+" as value from t_stu_cost_standard t where t.school_year = '"+schoolYear+"' "
				+ " and t.term_code = '"+termCode+"' and t.type_ = '"+type_+"'";
		Map<String,Object> sMap = basedao.queryMapInLowerKey(sql);
		String s = MapUtils.getString(sMap, "value");
		return s== null?null:Double.valueOf(s);
	}
	@Override
	public String queryStandardName(String schoolYear,String termCode,String type_,String[] type){
		String str = "high_alg_name ";
		if("-".equals(type[0])){
			str = "low_alg_name";
		}
		String sql = "select nvl(t."+str+",'') as value from t_stu_cost_standard t where t.school_year = '"+schoolYear+"' "
				+ " and t.term_code = '"+termCode+"' and t.type_ = '"+type_+"'";
		return basedao.queryForString(sql);
	}
	@Override
	public List<Map<String,Object>> queryStandardDate(){
		String sql = "select t.school_year as schoolyear,t.term_code as term  from t_stu_cost_standard t "
				+ " group by t.school_year,t.term_code order by t.school_year desc,t.term_code desc";
		return basedao.queryListInLowerKey(sql);
	}
	@Override
	public Map<String,Object> queryStandardYear(){
		
		String sql = "select max(substr(t.school_year,0,4)) as max,min(substr(t.school_year,0,4)) as min from t_stu_cost_standard t,t_school_start a "
				+ " where t.school_year = a.school_year and t.term_code = a.term_code and a.end_date < '"+DateUtils.getNowDate()+"'";
		return basedao.queryMapInLowerKey(sql);
	}
	/**
	 * 查询高低消费的标准
	 * @param table
	 * @param type
	 * @param wheresql
	 * @return Double
	 */
	@Override
	public Map<String,Object> queryCostValue(String table,String[] type,String wheresql){
		String sql = " select round(avg(t.sum_money),2) as pj,round(STDDEV(t.sum_money),2) as bzc,"
				+ " round(max(t.sum_money),2) as max,round(min(t.sum_money),2) as min from "
				+ " "+table+" t,t_stu a  " + wheresql+" and t.stu_id = a.no_ and t.sum_money is not null";
	     Map<String, Object> val = basedao.queryMapInLowerKey(sql);
//	     Double value = 0.0;
//	     if(val!=null&&!val.equals("")){
//	    	 value = Double.valueOf(val); 
//	     }
	     Object[][] highAry={{1d,"1","均值加上一倍标准差"},{1/2d,"1/2","均值加上二分之一标准差"},{1/3d,"1/3","均值加上三分之一标准差"}};//高消费算法的严苛程度 由严到松 数值代表是多少倍的标准差
	     Object[][] lowAry={{1d,"1","均值减去一倍标准差"},{1/2d,"1/2","均值减去二分之一标准差"},{1/3d,"1/3","均值减去三分之一标准差"}};//低消费算法的严苛程度 由严到松  数值代表是多少倍的标准差
         String name = null;String code = null;
  	     Map<String,Object> map = new HashMap<String, Object>();
	     if (val == null || val.isEmpty()){
	    	 map.put("value", 0d);
	    	 map.put("name", name);
	    	 return map;
	     }
	     Double max = MapUtils.getDoubleValue(val, "max"),
			    min =  MapUtils.getDoubleValue(val, "min"),
			    bzc =  MapUtils.getDoubleValue(val, "bzc"),
			    avg =  MapUtils.getDoubleValue(val, "pj"),
//			    bzc2= MathUtils.get2Point(MathUtils.getDivisionResult(bzc, 2.0, 2)),
//	            bzc3= MathUtils.get2Point(MathUtils.getDivisionResult(bzc, 3.0, 2)),
	            value= 0D;
	     if (type[0].equals("-")){
	    	 for (Object[] obj :lowAry){
	    		 Double vl = (Double)obj[0];
	    		 code = (String)obj[1];
	    		 value = MathUtils.get2Point(avg-(vl*bzc));
	    		 name = (String) obj[2];
	    		 if (value > min){
	    			 break;
	    		 }
	    	 }
	     }else{
	    	 for (Object[] obj :highAry){
	    		 Double vl = (Double)obj[0];
	    		 code = (String)obj[1];
	    		 value = MathUtils.get2Point(avg+(vl*bzc));
	    		 name = (String) obj[2];
	    		 if (value < max){
	    			 break;
	    		 }
	    	 }
	     }
	     map.put("value", value);
	     map.put("name", name);
	     map.put("code", code);
	     return map;
	}
	@Override
	public int getStartAndEnd(String schoolYear,String termCode){
		String Sql = "select t.start_date as startdate,t.end_date as enddate from t_school_start t where t.school_year = '"+schoolYear+"'"
				+ " and t.term_code = '"+termCode+"' "
						+ " group by t.start_date,t.end_date ";
		Map<String,Object> map = basedao.queryMapInLowerKey(Sql);
		String start = MapUtils.getString(map, "startdate");
		String end = MapUtils.getString(map, "enddate");
		int days = DateUtils.getDayBetween(start, end);
		return days;
	}
	@Override
	public int getStartAndEnd(String schoolYear,String termCode,int month){
		String sql = "select days from t_stu_cost_time_month t where t.school_year = '"+schoolYear+"'"
				+ " and t.term_code = '"+termCode+"'  and t.month = "+month+"";
		return  basedao.queryForInt(sql);
	}
}
