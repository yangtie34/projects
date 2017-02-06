package cn.gilight.dmm.xg.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.StuWarningDao;
import cn.gilight.framework.base.dao.BaseDao;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月15日 下午3:08:17
 */
@Repository("stuWarningDao")
public class StuWarningDaoImpl implements StuWarningDao {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;

	private static final String T_SkipClasses = Constant.TABLE_T_STU_WARNING_SKIP_CLASSES;
	private static final String T_Notstay     = Constant.TABLE_T_STU_WARNING_NOTSTAY;
	private static final String T_Stay_Late   = Constant.TABLE_T_STU_WARNING_STAY_LATE;
	private static final String T_Stay_Notin  = Constant.TABLE_T_STU_WARNING_STAY_NOTIN;
	
	@Override
	public int querySkipClassesRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date){
//		return 0;
		String[] dates = {date,date};
		return querySkipClassesRc(schoolYear, deptList, stuAdvancedList, dates);
	}
	@Override
	public int querySkipClassesRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return queryWarningCountRcOrRs(getSkipClassesRcSql(schoolYear, deptList, stuAdvancedList, dates));
	}
	@Override
	public String getSkipClassesRcSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return getWarningRcOrRsSql(schoolYear, deptList, stuAdvancedList, T_SkipClasses, "stu_id", "date_", dates, true);
	}
	@Override
	public int querySkipClassesRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date){
		String[] dates = {date,date};
		return querySkipClassesRs(schoolYear, deptList, stuAdvancedList, dates);
	}
	@Override
	public int querySkipClassesRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return queryWarningCountRcOrRs(getSkipClassesRsSql(schoolYear, deptList, stuAdvancedList, dates));
	}
	@Override
	public String getSkipClassesRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return getWarningRcOrRsSql(schoolYear, deptList, stuAdvancedList, T_SkipClasses, "stu_id", "date_", dates, false);
	}
	
	@Override
	public int queryNotStayRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date){
		String[] dates = {date,date};
		return queryNotStayRc(schoolYear, deptList, stuAdvancedList, dates);
	}
	@Override
	public int queryNotStayRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return queryWarningCountRcOrRs(getNotStayRcSql(schoolYear, deptList, stuAdvancedList, dates));
	}
	@Override
	public String getNotStayRcSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return getWarningRcOrRsSql(schoolYear, deptList, stuAdvancedList, T_Notstay, "stu_id", "date_", dates, true);
	}
	@Override
	public int queryNotStayRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date){
		String[] dates = {date,date};
		return queryNotStayRs(schoolYear, deptList, stuAdvancedList, dates);
	}
	@Override
	public int queryNotStayRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return queryWarningCountRcOrRs(getNotStayRsSql(schoolYear, deptList, stuAdvancedList, dates));
	}
	@Override
	public String getNotStayRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return getWarningRcOrRsSql(schoolYear, deptList, stuAdvancedList, T_Notstay, "stu_id", "date_", dates, false);
	}
	
	@Override
	public int queryStayLateRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date){
		String[] dates = {date,date};
		return queryStayLateRc(schoolYear, deptList, stuAdvancedList, dates);
	}
	@Override
	public int queryStayLateRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return queryWarningCountRcOrRs(getStayLateRcSql(schoolYear, deptList, stuAdvancedList, dates));
	}
	@Override
	public String getStayLateRcSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return getWarningRcOrRsSql(schoolYear, deptList, stuAdvancedList, T_Stay_Late, "stu_id", "date_", dates, true);
	}
	@Override
	public int queryStayLateRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date){
		String[] dates = {date,date};
		return queryStayLateRs(schoolYear, deptList, stuAdvancedList, dates);
	}
	@Override
	public int queryStayLateRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return queryWarningCountRcOrRs(getStayLateRsSql(schoolYear, deptList, stuAdvancedList, dates));
	}
	@Override
	public String getStayLateRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return getWarningRcOrRsSql(schoolYear, deptList, stuAdvancedList, T_Stay_Late, "stu_id", "date_", dates, false);
	}
	
	@Override
	public int queryStayNotinRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date){
		String[] dates = {date,date};
		return queryStayNotinRc(schoolYear, deptList, stuAdvancedList, dates);
	}
	@Override
	public int queryStayNotinRc(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return queryWarningCountRcOrRs(getStayNotinRcSql(schoolYear, deptList, stuAdvancedList, dates));
	}
	@Override
	public String getStayNotinRcSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return getWarningRcOrRsSql(schoolYear, deptList, stuAdvancedList, T_Stay_Notin, "stu_id", "date_", dates, true);
	}
	@Override
	public int queryStayNotinRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String date){
		String[] dates = {date,date};
		return queryStayNotinRs(schoolYear, deptList, stuAdvancedList, dates);
	}
	@Override
	public int queryStayNotinRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return queryWarningCountRcOrRs(getStayNotinRsSql(schoolYear, deptList, stuAdvancedList, dates));
	}
	@Override
	public String getStayNotinRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		return getWarningRcOrRsSql(schoolYear, deptList, stuAdvancedList, T_Stay_Notin, "stu_id", "date_", dates, false);
	}
	
	/**
	 * 查询预警学生 人次/人数 数量
	 * @param schoolYear 哪个学年的在校学生
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询条件
	 * @param tablename 表名
	 * @param stu_column 学生学号 列名
	 * @param date_column 日期 列名
	 * @param dates 查询日期数组；（ >= date1 and <= date2 ）
	 * @param isRc 是否是人次（人数）
	 * @return int
	 */
	@SuppressWarnings("unused")
	private int queryWarningRcOrRs(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList,
			String table, String stu_column, String date_column, String[] dates, boolean isRc){
		String sql = getWarningRcOrRsSql(schoolYear,deptList,stuAdvancedList,table,stu_column,date_column,dates,isRc);
		return queryWarningCountRcOrRs(sql);
	}
	/**
	 * 查询预警学生 人次/人数
	 * @param sql
	 * @return int
	 */
	private int queryWarningCountRcOrRs(String sql){
		return baseDao.queryForCount(sql);
	}

	/**
	 * 查询预警学生 人次/人数 sql
	 * @param schoolYear 哪个学年的在校学生
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询条件
	 * @param tablename 表名
	 * @param stu_column 学生学号 列名
	 * @param date_column 日期 列名
	 * @param dates 查询日期数组；（ >= date1 and <= date2 ）
	 * @param isRc 是否是人次（人数）
	 * @return String <br>
	 * （select stu_id, date_, dept_id, major_id, class_id, grade, sex_code from t）
	 */
	private String getWarningRcOrRsSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList,
			String tablename, String stu_column, String date_column, String[] dates, boolean isRc){
		String stuSql     = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList),
			   queryKey   = isRc ? (stu_column+" stu_id,"+date_column+" date_") : "distinct("+stu_column+") stu_id",
			   sql        = "select "+queryKey+", stu.dept_id, stu.major_id, nvl(t.class_id, stu.class_id) class_id,"
			   				+ (schoolYear==null ? "" : "to_char("+schoolYear+"+1-stu.enroll_grade)")+" grade, stu.sex_code"
			   				+ " from "+tablename+" t, ("+stuSql+")stu where t."+stu_column+"=stu.no_",
			   start_date = dates[0],
			   end_date   = dates[1];
		sql += start_date.equals(end_date) ? (" and t."+date_column+"='"+start_date+"'") 
				: (" and t."+date_column+">='"+start_date+"' and t."+date_column+"<='"+end_date+"'");
		return sql;
	}
	//得到周几学生逃课
	@Override
	public String getSkipClassStuSqlByWeekDay(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates,String weekDay){
		String sql=getSkipSql(schoolYear, deptList, stuAdvancedList, dates);
		sql="select t.stu_id,t.date_,t.COURSE_ARRANGEMENT_ID from (select t.stu_id,t.date_,to_char(to_date(t.date_,'YYYY-MM-DD'),'d')as week,t.COURSE_ARRANGEMENT_ID from (" +sql+ ") t ) t "
				+ " where t.week='"+weekDay+"'";
		return sql;
	}
	
	@Override
	public String getSkipClassStuSqlByPeriod(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates,String period_start,String period_end){
		String sql=getSkipSql(schoolYear, deptList, stuAdvancedList, dates);
		       sql="select t.stu_id , t.date_,t.COURSE_ARRANGEMENT_ID from ( "+sql+") t,T_COURSE_ARRANGEMENT t2 where "
				+ " t.COURSE_ARRANGEMENT_ID=t2.id and  t2.period_end in ('"+period_start+"','"+period_end+"')";
			return sql;
	}
	
	//获取学生上午逃课总量
	@Override
	public int getSkipClassStuByAM(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		String sql=getSkipSql(schoolYear, deptList, stuAdvancedList, dates);
	      String sql1="select t.stu_id , t.date_ from ( "+sql+") t , (select id from T_COURSE_ARRANGEMENT where period_end in ('1','2','3','4') ) t2 "
			+ " where t.COURSE_ARRANGEMENT_ID=t2.id ";//逃课学生
	      int count=baseDao.queryForCount(sql1);
		return count;
	}
	
	@Override
	public String getSkipClassStuByagain(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates,String period_start,String period_end){
		String sql=getSkipSql(schoolYear, deptList, stuAdvancedList, dates),finSql=null;
		String sqlPm="select t.stu_id , t.date_ from ( "+sql+") t , (select id from T_COURSE_ARRANGEMENT where period_end in ('5','6','7','8') ) t2 "
					+ " where t.COURSE_ARRANGEMENT_ID=t2.id ";//下午逃课学生
		if(period_end.equals("not")){
			sql="select t.stu_id , t.date_ from ( "+sql+") t , (select id from T_COURSE_ARRANGEMENT where period_end in ('1','2','3','4') ) t2 "
					+ " where t.COURSE_ARRANGEMENT_ID=t2.id ";//上午逃课学生
			finSql=sql+" minus "+sqlPm;
		}else{
			sql="select t.stu_id , t.date_ from ( "+sql+") t, ( select id from T_COURSE_ARRANGEMENT where period_end in ('"+period_start+"','"+period_end+"')) t2 "
					+ " where t.COURSE_ARRANGEMENT_ID=t2.id ";//上午逃课某节次学生
			finSql=" select t.stu_id,t.date_ from ("+sql+") t , ("+sqlPm+") t2 where t.stu_id=t2.stu_id and "
		      		+ " t.date_=t2.date_";
		}
		return finSql;
	}
	
	private String getSkipSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, String[] dates){
		String stuSql = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
		String sql="select stu_id ,date_ ,t.COURSE_ARRANGEMENT_ID from "+Constant.TABLE_T_STU_WARNING_SKIP_CLASSES+" t, "
					+"("+stuSql+") stu where t.stu_id=stu.no_ ";
		String start_date = dates[0], 
				   end_date   = dates[1];
		sql += start_date.equals(end_date) ? ("and t.date_='"+start_date+"'") 
					: (" and t.date_>='"+start_date+"' and t.date_<='"+end_date+"'");
		return sql;
	}
}
