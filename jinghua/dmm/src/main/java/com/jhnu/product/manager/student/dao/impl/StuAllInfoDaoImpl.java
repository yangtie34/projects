package com.jhnu.product.manager.student.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.student.dao.StuAllInfoDao;
import com.jhnu.util.common.MapUtils;

@Repository("stuAllInfoDao")
public class StuAllInfoDaoImpl implements StuAllInfoDao {

	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getStuInfo(String stu_id) {
		String sql = "select stu.no_ stu_id,stu.name_ stu_name, stu.sex_code, nvl((to_char(sysdate,'yyyy'))-substr(stu.birthday,0,4),0) stu_age"
				+ ",stu.birthday stu_birthday,stu.idno stu_idno ,stu.enroll_grade,sc.tel stu_tel,sc.home_addr stu_addr"
				+ "   ,ad.name_ stu_origin,stu.place_domicile place_domicile,tc.name_ stu_anmelden,co.name_ stu_roll, stu.enroll_date enroll_date                           "
				+ "   ,to_char(add_months(to_date(stu.enroll_date,'yyyy-mm-dd'),stu.length_schooling*12),'yyyy-mm-dd')  leave_date                                         "
				+ "   ,stu.length_schooling length_school,cn.name_ stu_nation ,cp.name_ stu_politics                                                                        "
				+ "   ,classes.name_ class_name,dt.name_ major_name                                                                                                         "
				+ "   ,dm.berth_name ,dorm_name,ceng_name,lou_name                                                                                                          "
				+ "   ,dept.name_ dept_name,tea.name_ class_tea_name,comm.tel class_tea_tel,ins.school_year,ins.term_code from t_stu stu                                  "
				+ "    left join t_classes classes on stu.class_id = classes.no_                                                                                            "
				+ "    left join t_code_dept_teach dt on dt.id = stu.major_id                                                                                               "
				+ "    left join t_code_dept dept on dept.id = stu.dept_id                                                                                                  "
				+ "    left join t_classes_instructor ins on ins.class_id = stu.class_id                                                                                    "
				+ "    left join t_tea tea on tea.id = ins.tea_id                                                                                                           "
				+ "    left join t_code_admini_div ad on ad.code_ = stu.stu_origin_id                                                                                      "
				+ "    left join t_code tc on tc.code_type = 'ANMELDEN_CODE' and tc.code_ = stu.anmelden_code                                                               "
				+ "    left join t_code co on co.code_type = 'STU_ROLL_CODE' and co.code_ = stu.stu_roll_code                                                               "
				+ "    left join t_code cn on cn.code_type='NATION_CODE' and cn.code_ = stu.nation_code                                                                     "
				+ "    left join t_code cp on cp.code_type='POLITICS_CODE' and cp.code_ = stu.politics_code                                                                 "
				+ "    left join t_tea_comm comm on comm.tea_id = tea.tea_no 												  "
				+ "    left join t_stu_comm sc on sc.stu_id = stu.no_                                               													  "
				+ "    left join (select ceng.stu_id stu_id, ceng.berth_name, ceng.dorm_name, ceng.ceng_name, dorm.name_ lou_name from                                      "
				+ "			 (select fj.stu_id, fj.berth_name, fj.dorm_name, dorm.name_ ceng_name, dorm.pid lou_pid from                                                 "
				+ "			 (select berth_stu.stu_id stu_id,berth.berth_name berth_name, dorm.name_ dorm_name, dorm.pid dorm_pid from t_dorm dorm                        "
				+ "			 inner join t_dorm_berth berth on berth.dorm_id = dorm.id                                                                                   "
				+ "			 inner join t_dorm_berth_stu berth_stu on berth_stu.berth_id = berth.id                                                                       "
				+ "			 ) fj inner join t_dorm dorm on dorm.id = fj.dorm_pid) ceng                                                                                   "
				+ "			 inner join t_dorm dorm on dorm.id = ceng.lou_pid) dm on dm.stu_id = stu.no_                                                                  "
				+ "    where stu.no_ = '"
				+ stu_id
				+ "' order by school_year desc,term_code desc ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getAward(String stu_id) {
		String sql = "select award.school_year,award.batch ,co.name_ name,award.money money from  t_stu_award award "
				+ " left join t_code co on co.code_type = 'AWARD_CODE' and co.code_ = award.award_code"
				+ " where award.stu_id = '" + stu_id + "'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getSubsidy(String stu_id) {
		String sql = "select sub.school_year,sub.batch,co.name_ name,sub.money money from t_stu_subsidy sub "
				+ " left join t_code co on co.code_type='SUBSIDY_CODE' and co.code_ = sub.subsidy_code"
				+ " where sub.stu_id = '" + stu_id + "'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getRoommate(String stu_id) {
		String sql = "select r.*,s.name_ stu_name,nvl((to_char(sysdate,'yyyy'))-substr(s.birthday,0,4),0) stu_age, berth.berth_name,"
				+ " comm.tel stu_tel,classes.name_ class_name,tea.name_ tea_name,cad.name_ stu_origin"
				+ " from (select stu.no_ stu_id from t_stu stu,t_dorm_berth_stu berth_stu, t_dorm_berth berth "
				+ " where stu.no_ = berth_stu.stu_id and berth_stu.berth_id = berth.id and dorm_id = "
				+ " (select dorm_id from t_dorm_berth where id = (select berth_id from t_dorm_berth_stu where stu_id ='"
				+ stu_id
				+ "')) ) r "
				+ " left join t_stu s on s.no_ = r.stu_id "
				+ " left join t_stu_comm comm on comm.stu_id = r.stu_id"
				+ " left join t_classes classes on classes.no_ = s.class_id"
				+ " left join t_classes_instructor ins on ins.class_id = s.class_id"
				+ " left join t_tea tea on tea.id = ins.tea_id "
				+ " left join t_code_admini_div cad on cad.code_ = s.stu_origin_id"
				+ " left join t_dorm_berth_stu dbs on dbs.stu_id = s.no_"
				+ " left join t_dorm_berth berth on berth.id = dbs.berth_id order by "
				+ " berth_name";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getLeaveByStu(String stu_id) {
		String sql = "select l.*, stu.name_ from t_stu_leave l left join t_stu stu on l.stu_id = stu.no_ where l.stu_id='"
				+ stu_id + "'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Map<String, Object> stusPunishmentViolation(String stuId) {
		// 学生的违纪处分信息sql
		String punishInfoSql = "select tc.name_ termName,tcd.name_ punishName, tcde.name_ violateName, tsp.school_year schoolYear, tsp.date_ stuDate from t_stu_punish tsp "
				+ "inner join t_stu s on s.no_ = tsp.stu_id "
				+ "left join t_code tc on tc.code_ = tsp.term_code "
				+ "left join t_code tcd on tcd.code_ = tsp.term_code "
				+ "left join t_code tcde on tcde.code_ = tsp.term_code "
				+ "where tsp.stu_id="
				+ "'"
				+ stuId
				+ "' "
				+ "and  tc.code_type='TERM_CODE' "
				+ "and tcd.code_type='PUNISH_CODE' "
				+ "and tcde.code_type='VIOLATE_CODE' "
				+ "group by tc.name_, tsp.school_year, tsp.date_, tcd.name_, tcde.name_ "
				+ "order by tc.name_, tsp.school_year, tsp.date_, tcd.name_, tcde.name_";

		// 学生的违纪处分中违纪类型的次数的sql
		String violateNumsSql = "select count(*) violateCounts, violateName from( "
				+ "select tc.name_ termName,tcd.name_ punishName, tcde.name_ violateName, tsp.school_year schoolYear, tsp.date_ stuDate from T_STU_PUNISH tsp "
				+ "inner join t_stu s on s.no_ = tsp.stu_id "
				+ "left join t_code tc on tc.code_ = tsp.term_code "
				+ "left join t_code tcd on tcd.code_ = tsp.term_code "
				+ "left join t_code tcde on tcde.code_ = tsp.term_code "
				+ "where tsp.stu_id="
				+ "'"
				+ stuId
				+ "' "
				+ "and  tc.code_type='TERM_CODE' "
				+ "and tcd.code_type='PUNISH_CODE' "
				+ "and tcde.code_type='VIOLATE_CODE' "
				+ "group by tc.name_, tsp.school_year, tsp.date_, tcd.name_, tcde.name_ "
				+ "order by tc.name_, tsp.school_year, tsp.date_, tcd.name_, tcde.name_) "
				+ "group by violateName";

		List<Map<String, Object>> punishInfoList = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(punishInfoSql);
		List<Map<String, Object>> violateNumsList = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(violateNumsSql);
		Map<String, Object> stusPunishmentViolationMap = new HashMap<String, Object>();
		stusPunishmentViolationMap.put("punishInfoList", punishInfoList);
		stusPunishmentViolationMap.put("violateNumsList", violateNumsList);
		return stusPunishmentViolationMap;
	}

	@Override
	public List<Map<String, Object>> stusCultureScore(String stu_id,
			String termCode, String schoolYear) {
		// TODO 单科成绩比较图和总成绩历史排名位完成，补考记录没有数据，考务相关没有表
		String sql = "select tc.name_ courseName, tss.centesimal_score cenScore, s.name_ stuName, cd.name_ sexName, s.no_ stuNo, cde.name_ termName, tss.school_year schoolYear from T_STU_SCORE tss "
				+ "inner join t_stu s on s.no_ = tss.stu_id "
				+ "left join T_COURSE tc on tc.code_ = tss.coure_code "
				+ "left join t_code cd on cd.code_ = s.sex_code "
				+ "left join t_code cde on cde.code_ = tss.term_code "
				+ "where s.no_="
				+ "'"
				+ stu_id
				+ "' "
				+ "and cd.code_type='SEX_CODE' "
				+ "and cde.code_type='TERM_CODE' "
				+ "and tss.term_code="
				+ "'"
				+ termCode
				+ "' "
				+ "and "
				+ "tss.school_year="
				+ "'"
				+ schoolYear
				+ "' "
				+ "group by tc.name_, tss.centesimal_score, s.name_, cd.name_, s.no_, cde.name_, tss.school_year "
				+ "order by tss.centesimal_score desc";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getLeaveInfoByStu(String stu_id) {
		String sql = "select leave.stu_id,leave.stuName, leave.school_year, leave.term_code,leave.leave_code, leave.codeName, count(*) leaveNum, sum(day) dayCounts from (select l.*,STU.NAME_ stuName, lc.name_ codeName from T_STU_LEAVE l left join T_STU stu on l.STU_ID=STU.NO_ LEFT JOIN (select * from t_code where code_type='LEAVE_CODE') lc  on lc.code_=l.LEAVE_CODE) leave where leave.stu_id='"
				+ stu_id
				+ "' group by leave.stu_id, leave.stuName, leave.school_year, leave.term_code,leave.leave_code, leave.codeName";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBookBorrowByStu(String stu_id) {
		String sql = "select stuborrow.*, stu.name_ stuName, book.name_ bookName from (select * from T_BOOK_BORROW where BOOK_READER_ID = '"
				+ stu_id
				+ "') stuborrow left join T_STU stu on stu.no_ = stuborrow.book_reader_id left join t_book book on book.no_=stuborrow.book_id";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCard(String startDate, String endDate) {
		String sql = "select s.stu_id,s.stu_name,s.stu_sex, nvl(round(sum(s.pay_money)/count(s.pay_date),2),0) avg_money from"
				+ " (select t.stu_id,t.stu_name,t.stu_sex,t.pay_date,sum(t.pay_money) pay_money from("
				+ " select cd.people_id stu_id,stu.name_ stu_name,co.name_ stu_sex,pay.pay_money pay_money ,substr(pay.time_,0,10) pay_date from t_card_pay pay "
				+ " left join t_card cd on cd.no_ = pay.card_id "
				+ " left join t_stu stu on stu.no_ = cd.people_id "
				+ " left join t_code co on co.code_ = stu.sex_code and co.code_type = 'SEX_CODE' "
				+ " where  pay.time_ between '"
				+ startDate
				+ "' and '"
				+ endDate
				+ "') t"
				+ " group by t.stu_id,t.stu_name,t.stu_sex, t.pay_date) s where s.stu_id is not null group by s.stu_id,s.stu_name,s.stu_sex";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCoureArrangementByStu(String stu_id, String zc) {
		/*String sql = "SELECT min(b.SCHOOL_YEAR) SCHOOL_YEAR, min(b.TERM_CODE) TERM_CODE, B.PERIOD, min(CASE WHEN B.DAY_OF_WEEK = 1 THEN b.courseName ELSE null END) AS \"星期一\",min(CASE WHEN B.DAY_OF_WEEK = 2 THEN b.courseName ELSE null END) AS \"星期二\",	min(CASE WHEN B.DAY_OF_WEEK = 3 THEN b.courseName ELSE null END) AS \"星期三\",	min(CASE WHEN B.DAY_OF_WEEK = 4 THEN b.courseName ELSE null END) AS \"星期四\",	min(CASE WHEN B.DAY_OF_WEEK = 5 THEN b.courseName ELSE null END) AS \"星期五\",	min(CASE WHEN B.DAY_OF_WEEK = 6 THEN b.courseName ELSE null END) AS \"星期六\",	min(CASE WHEN B.DAY_OF_WEEK = 7 THEN b.courseName ELSE null END ) AS \"星期日\" FROM (SELECT A .PERIOD, A .DAY_OF_WEEK, A .WEEK_START_END, A .COURSENAME, A.SCHOOL_YEAR, A.TERM_CODE FROM(SELECT COURSE.* FROM(SELECT ca.*, tc.name_ courseName FROM t_course_arrangement ca LEFT JOIN t_course tc ON ca.course_id = tc.code_ WHERE teachingclass_id IN (SELECT TX .teach_class_id FROM (SELECT * FROM t_stu WHERE no_ = '"
				+ stu_id
				+ "') stu LEFT JOIN t_class_teaching_xzb TX ON stu.class_id = TX .class_id ) and WEEKS like '%,"+zc+",%' ) course ORDER BY period, day_of_week ) A ) B GROUP BY B.PERIOD order by period";
		System.out.println(sql);*/
		String sql ="SELECT d.*, c.\"星期一\",c.\"星期二\", c.\"星期三\",c.\"星期四\",c.\"星期五\",c.\"星期六\",c.\"星期日\" FROM (SELECT DISTINCT COURSE.* FROM (SELECT ca.SCHOOL_YEAR, CA.TERM_CODE,CA.PERIOD FROM t_course_arrangement ca WHERE teachingclass_id IN (SELECT TX .teach_class_id FROM (SELECT * FROM t_stu WHERE no_ = '"+stu_id+"' ) stu LEFT JOIN t_class_teaching_xzb TX ON stu.class_id = TX .class_id ) ) course) D LEFT JOIN (SELECT B.PERIOD, min(CASE WHEN B.DAY_OF_WEEK = 1 THEN b.courseName ELSE null END) AS \"星期一\",min(CASE WHEN B.DAY_OF_WEEK = 2 THEN b.courseName ELSE null END) AS \"星期二\",	min(CASE WHEN B.DAY_OF_WEEK = 3 THEN b.courseName ELSE null END) AS \"星期三\",	min(CASE WHEN B.DAY_OF_WEEK = 4 THEN b.courseName ELSE null END) AS \"星期四\",	min(CASE WHEN B.DAY_OF_WEEK = 5 THEN b.courseName ELSE null END) AS \"星期五\",	min(CASE WHEN B.DAY_OF_WEEK = 6 THEN b.courseName ELSE null END) AS \"星期六\",	min(CASE WHEN B.DAY_OF_WEEK = 7 THEN b.courseName ELSE null END ) AS \"星期日\" FROM (SELECT A .PERIOD, A .DAY_OF_WEEK, A .WEEK_START_END, A .COURSENAME FROM(SELECT COURSE.* FROM(SELECT ca.*, tc.name_ courseName FROM t_course_arrangement ca LEFT JOIN t_course tc ON ca.course_id = tc.code_ WHERE teachingclass_id IN (SELECT TX .teach_class_id FROM (SELECT * FROM t_stu WHERE no_ = '"+stu_id+"') stu LEFT JOIN t_class_teaching_xzb TX ON stu.class_id = TX .class_id ) and WEEKS like '%,"+zc+",%' ) course ORDER BY period, day_of_week ) A ) B GROUP BY B.PERIOD order by period) C ON C.PERIOD = D.PERIOD";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getTotalAndAvgMoney() {
		String sql = "select s.stu_id ,s.stu_name,sum(s.pay_money) total_money,nvl(round(sum(s.pay_money)/count(s.pay_date),2),0) avg_money from"
				+ " (select t.stu_id,t.stu_name,t.pay_date,sum(t.pay_money) pay_money from("
				+ " select cd.people_id stu_id,stu.name_ stu_name,pay.pay_money pay_money ,substr(pay.time_,0,10) pay_date from t_card_pay pay "
				+ " left join t_card cd on cd.no_ = pay.card_id "
				+ " left join t_stu stu on stu.no_ = cd.people_id ) t"
				+ " group by t.stu_id, t.stu_name,t.pay_date) s where s.stu_id is not null group by s.stu_id,s.stu_name";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Map<String, Object> getAllCard(String startDate, String endDate) {
		String sql = "select '全校' stu_type, nvl(round(sum(avg_date_money)/count(stu_id),2),0) avg_money from("
				+ " select s.stu_id ,nvl(round(sum(s.pay_money)/count(s.pay_date),2),0) avg_date_money from"
				+ " (select t.stu_id,t.pay_date,sum(t.pay_money) pay_money from("
				+ " select cd.people_id stu_id,pay.pay_money pay_money ,substr(pay.time_,0,10) pay_date from t_card_pay pay "
				+ " left join t_card cd on cd.no_ = pay.card_id "
				+ " where pay.time_ between '"
				+ startDate
				+ "' and '"
				+ endDate
				+ "') t"
				+ " group by t.stu_id, t.pay_date) s where s.stu_id is not null group by s.stu_id)";
		return baseDao.getBaseDao().getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getSexCard(String startDate, String endDate) {
		String sql = "select stu_sex stu_type,nvl(round(sum(avg_date_money)/count(stu_id),2),0) avg_money from ("
				+ " select r.stu_id,r.avg_date_money,co.name_ stu_sex from "
				+ " (select s.stu_id ,round(sum(s.pay_money)/count(s.pay_date),2) avg_date_money from"
				+ " (select t.stu_id,t.pay_date,sum(t.pay_money) pay_money from("
				+ " select cd.people_id stu_id,pay.pay_money pay_money ,substr(pay.time_,0,10) pay_date from t_card_pay pay "
				+ " left join t_card cd on cd.no_ = pay.card_id "
				+ " where pay.time_ between '"
				+ startDate
				+ "' and '"
				+ endDate
				+ "') t"
				+ " group by t.stu_id, t.pay_date) s where s.stu_id is not null group by s.stu_id) r "
				+ " inner join t_stu stu on stu.no_ = r.stu_id"
				+ " left join t_code co on co.code_ = stu.sex_code and co.code_type = 'SEX_CODE') a group by stu_sex";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void saveStuAllCard(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		String delSql = "delete  tl_stuall_card sc where sc.school_year = '"
				+ MapUtils.getString(LIST.get(0), "school_year").toString()
				+ "' and sc.term_code = '"
				+ MapUtils.getString(LIST.get(0), "term_code").toString() + "'";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_stuall_card values (?, ?, ?, ?,?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(
										1,
										MapUtils.getString(LIST.get(i),
												"stu_type").toString());
								ps.setString(
										2,
										MapUtils.getString(LIST.get(i),
												"avg_money").toString());
								ps.setString(
										3,
										MapUtils.getString(LIST.get(i),
												"school_year").toString());
								ps.setString(
										4,
										MapUtils.getString(LIST.get(i),
												"term_code").toString());
								ps.setString(
										5,
										MapUtils.getString(LIST.get(i),
												"stu_id").toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	@Override
	public List<Map<String, Object>> getCardLog(String stuId,
			String school_year, String term_code) {
		String sql = "select t.stu_type,t.avg_money from tl_stuall_card t where t.school_year = '"
				+ school_year
				+ "' and t.term_code = '"
				+ term_code
				+ "' "
				+ " and (t.stu_id = '"
				+ stuId
				+ "' or t.stu_type='全校' or t.stu_type='男' or t.stu_type = '女')";
		return baseDao.getLogDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public void saveTotalAndAvgMoney(List<Map<String, Object>> list) {
		final int COUNT = list.size();
		final List<Map<String, Object>> LIST = list;
		String delSql = "delete  tl_stuall_total_card";
		baseDao.getLogDao().executeSql(delSql);
		baseDao.getLogDao()
				.getJdbcTemplate()
				.batchUpdate(
						"insert into tl_stuall_total_card values (?, ?, ?, ?)",
						new BatchPreparedStatementSetter() {
							// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
							public void setValues(PreparedStatement ps, int i)
									throws SQLException {
								ps.setString(
										1,
										MapUtils.getString(LIST.get(i),
												"stu_id").toString());
								ps.setString(
										2,
										MapUtils.getString(LIST.get(i),
												"stu_name").toString());
								ps.setString(
										3,
										MapUtils.getString(LIST.get(i),
												"total_money").toString());
								ps.setString(
										4,
										MapUtils.getString(LIST.get(i),
												"avg_money").toString());
							}

							// 返回更新的结果集条数
							public int getBatchSize() {
								return COUNT;
							}
						});
	}

	@Override
	public Map<String, Object> getTotalAndAvgMoneyLog(String stu_id) {
		String sql = "select * from tl_stuall_total_card where stu_id = '"
				+ stu_id + "'";
		return baseDao.getLogDao().getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> oneStuCompareClasses(String stu_id,
			String termCode, String schoolYear) {
		String sql = "select sum(tssre.centesimal_score) allScores, tc.name_ courseName from T_STU_SCORE tssre,t_course tc where tssre.stu_id in( "
				+ "select ts.no_ stuNo from t_stu ts where ts.class_id= ( "
				+ "select tcss.no_ classNo from T_CLASSES tcss left join t_stu s on s.class_id = tcss.no_ "
				+ "where s.no_= '"
				+ stu_id
				+ "')) "
				+ "and tssre.coure_code = tc.code_ "
				+ "and tssre.school_year= '"
				+ schoolYear
				+ "' "
				+ "and tssre.term_code= '" + termCode + "' group by tc.name_";

		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> oneStuCompareMajor(String stu_id,
			String termCode, String schoolYear) {
		String likeCondition = stu_id.substring(0, 4);// 过滤往届学生和以后学生的no，只要保留该届学生的no
		String sql = "select sum(tssre.centesimal_score) allScores, tc.name_ courseName from T_STU_SCORE tssre,t_course tc where tssre.stu_id in( "
				+ "select s.no_ stuNo from t_stu s left join T_CODE_DEPT_TEACH tcdt on tcdt.id = s.major_id "
				+ "where s.major_id=( "
				+ "select s.major_id from t_stu s where s.no_= '"
				+ stu_id
				+ "') "
				+ "and s.no_ like '%"
				+ likeCondition
				+ "%') "
				+ "and tssre.coure_code = tc.code_  "
				+ "and tssre.school_year= '"
				+ schoolYear
				+ "' and tssre.term_code= '" + termCode + "' group by tc.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCourseWeeks(String schoolYear, String termCode) {
		String sql = "select weeks from T_COURSE_ARRANGEMENT where SCHOOL_YEAR ='"+schoolYear+"' and TERM_CODE='"+termCode+"'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> stuClassNums(String stuId) {
		String sql = "select ts.no_ stuNo from t_stu ts where ts.class_id= ( "
				+ "select tcss.no_ classNo from T_CLASSES tcss left join t_stu s on s.class_id = tcss.no_ "
				+ "where s.no_= '" + stuId + "' )";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> stuMajorNums(String stuId) {
		String likeCondition = stuId.substring(0, 4);// 过滤往届学生和以后学生的no，只要保留该届学生的no
		String sql = "select s.no_ stuNo from t_stu s left join T_CODE_DEPT_TEACH tcdt on tcdt.id = s.major_id "
				+ "where s.major_id=("
				+ "select s.major_id from t_stu s where s.no_='"
				+ stuId
				+ "') " + "and s.no_ like '%" + likeCondition + "%'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> stuScoreDescName(String stuId,
			String termCode, String schoolYear) {
		String sql = "select tc.name_ courseName, sum(tss.centesimal_score) cenScore from T_STU_SCORE tss "
				+ "inner join t_stu s on s.no_ = tss.stu_id "
				+ "left join T_COURSE tc on tc.code_ = tss.coure_code "
				+ "where s.no_= '"
				+ stuId
				+ "' "
				+ "and tss.term_code= '"
				+ termCode
				+ "' "
				+ "and tss.school_year= '"
				+ schoolYear
				+ "' group by tc.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> stuScoreAscName(String stuId,
			String termCode, String schoolYear) {
		String sql = "select tc.name_ courseName, sum(tss.centesimal_score) cenScore from T_STU_SCORE tss "
				+ "inner join t_stu s on s.no_ = tss.stu_id "
				+ "left join T_COURSE tc on tc.code_ = tss.coure_code "
				+ "where s.no_= '"
				+ stuId
				+ "' "
				+ "and tss.term_code= '"
				+ termCode
				+ "' "
				+ "and tss.school_year= '"
				+ schoolYear
				+ "' group by tc.name_ "
				+ "order by tc.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> stuClassRankings(String stuId,
			String termCode, String schoolYear) {
		String sql = "select rankings.allScores, rankings.courseName, rankings.stuNos, dense_rank() over(partition by rankings.courseName order by rankings.allScores desc) ranks from ( "
				+ "select tssre.centesimal_score allScores, tc.name_ courseName, stus.no_ stuNos from T_STU_SCORE tssre,t_course tc, t_stu stus where tssre.stu_id in( "
				+ "select ts.no_ stuNo from t_stu ts where ts.class_id= ( "
				+ "select tcss.no_ classNo from T_CLASSES tcss left join t_stu s on s.class_id = tcss.no_ "
				+ "where s.no_= '"
				+ stuId
				+ "')) "
				+ "and tssre.coure_code = tc.code_ and stus.no_=tssre.stu_id "
				+ "and tssre.school_year= '"
				+ schoolYear
				+ "' "
				+ "and tssre.term_code= '" + termCode + "'" + ") rankings";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> stuMajorRankings(String stuId,
			String termCode, String schoolYear) {
		String likeCondition = stuId.substring(0, 4);// 过滤往届学生和以后学生的no，只要保留该届学生的no
		String sql = "select rankings.courseName, rankings.allScores, rankings.stuNos , dense_rank() over (partition by rankings.courseName order by rankings.allScores desc) ranks  from ( "
				+ "select tssre.centesimal_score allScores, tc.name_ courseName,stus.no_ stuNos from T_STU_SCORE tssre,t_course tc, t_stu stus where tssre.stu_id in( "
				+ "select s.no_ stuNo from t_stu s left join T_CODE_DEPT_TEACH tcdt on tcdt.id = s.major_id "
				+ "where s.major_id=( "
				+ "select s.major_id from t_stu s where s.no_= '"
				+ stuId
				+ "') and s.no_ like '%"
				+ likeCondition
				+ "%') and tssre.coure_code = tc.code_ and stus.no_ = tssre.stu_id "
				+ "and tssre.school_year= '"
				+ schoolYear
				+ "' "
				+ "and tssre.term_code= '" + termCode + "'" + ") rankings";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

}
