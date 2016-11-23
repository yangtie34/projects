package cn.gilight.personal.social.liao.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.social.liao.service.PersonalService;

@Service
public class PersonalServiceImpl implements PersonalService{
	@Resource
	private BaseDao baseDao;
	
	/** 
	 * @Description: 查询个人信息
	 * @return : Map<String,Object>
	 */
	@Override
	public Map<String,Object> queryInfoOfPerson(String username){
		String sql = "";
		if(isStudent(username)){
			sql = "SELECT T.USERNAME,T.REAL_NAME,W.WECHAT_HEAD_IMG ,tcd.name_ dept_name,cdt.name_ major_name,co.name_ sex,stu.enroll_grade,cl.name_ class_name,sc.tel"
					+ " , '1' is_stu FROM T_SYS_USER T LEFT JOIN T_WECHAT_BIND W ON T.USERNAME = W.USERNAME left join t_stu stu on stu.no_ = t.username "
					+ " left join t_code_dept_teach cdt on cdt.id = stu.major_id"
					+ " left join t_code_dept_teach tcd on tcd.id = stu.dept_id"
					+ " left join t_classes cl on cl.no_ = stu.class_id"
					+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = stu.sex_code"
					+ " left join t_stu_comm sc on sc.stu_id = stu.no_"
					+ " WHERE T.USERNAME = '"+username+"'";
		}else{
			sql = "SELECT T.USERNAME,T.REAL_NAME,W.WECHAT_HEAD_IMG ,cdt.name_ dept_name,cz.name_ zyjszw,cd.name_ degree_,co.name_ sex,tc.tel"
					+ " ,'0' is_stu FROM T_SYS_USER T LEFT JOIN T_WECHAT_BIND W ON T.USERNAME = W.USERNAME left join t_tea tea on tea.tea_no = t.username "
					+ " left join t_code_dept cdt on cdt.id = tea.dept_id"
					+ " left join t_code_zyjszw cz on cz.code_ = tea.zyjszw_id"
					+ " left join t_code_degree cd on cd.code_ = tea.degree_id"
					+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = tea.sex_code"
					+ " left join t_tea_comm tc on tc.tea_id = tea.tea_no"
					+ " WHERE T.USERNAME = '"+username+"'";
		}
		List<Map<String,Object>> ls = baseDao.queryListInLowerKey(sql);
		if (ls.size() > 0) {
			return ls.get(0);
		}
		return null;
	}
	
	public boolean isStudent(String username){
		String sql = "select t.* from t_tea t where t.tea_no = '"+username+"'";
		int counts = baseDao.queryForInt(sql);
		if(counts > 0){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean isPersonExist(String username) {
		String sql = "SELECT T.ID FROM T_SYS_USER T "
				+ " WHERE T.USERNAME = '"+username+"'";
		int counts = baseDao.queryForInt(sql);
		if (counts == 0) {
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 查询个人消费情况
	 */
	@Override
	public Map<String, Object> queryCard(String username) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		double total_pay = 0d;
		double day_avg = 0d;
		double pay_max = 0d;
		double zao = 0d;
		double wu = 0d;
		double wan = 0d;
		String sql1 = "select sum(tt.day_money) sums,round(sum(tt.day_money)/count(tt.day_),2) day_avg from ("
				+ " select sum(t.pay_money) day_money,substr(t.time_,0,10) day_ from t_card_pay t "
				+ " left join t_card c on c.no_ = t.card_id where c.people_id = '"+username+"' group by substr(t.time_,0,10))tt";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		if (list1 != null && list1.size() > 0) {
			total_pay = MapUtils.getDoubleValue(list1.get(0), "sums");
			day_avg = MapUtils.getDoubleValue(list1.get(0), "day_avg");
		}
		String sql2 = "select t.pay_money from t_card_pay t left join t_card c on c.no_ = t.card_id where c.people_id = '"+username+"' order by t.pay_money desc";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		if (list2 != null && list2.size() > 0) {
			pay_max = MapUtils.getDoubleValue(list2.get(0), "pay_money");
		}
		
		String sql3 = "select round(t.breakfast_pay_sum/t.breakfast_count,2) breakfast_avg,round(t.lunch_pay_sum/t.lunch_count,2) lunch_avg,"
				+ " round(t.dinner_pay_sum/t.dinner_count,2) dinner_avg from t_card_meal t where t.stu_id = '"+username+"'";
		List<Map<String,Object>> list3 = baseDao.queryListInLowerKey(sql3);
		if (list3 != null && list3.size() > 0) {
			zao = MapUtils.getDoubleValue(list3.get(0), "breakfast_avg");
			wu = MapUtils.getDoubleValue(list3.get(0), "lunch_avg");
			wan = MapUtils.getDoubleValue(list3.get(0), "dinner_avg");
		}
		
		resultMap.put("total_pay", total_pay);
		resultMap.put("day_avg", day_avg);
		resultMap.put("pay_max", pay_max);
		resultMap.put("zao", zao);
		resultMap.put("wu", wu);
		resultMap.put("wan", wan);
		return resultMap;
	}

	@Override
	public Map<String, Object> queryBook(String username) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		int total_borrow = 0;
		int in_borrow = 0;
		List<Map<String,Object>> inBorrowList = new ArrayList<Map<String,Object>>();
		String sql1 = "select count(*) total_borrow from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id where br.people_id = '"+username+"'";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		if (list1 != null && list1.size() > 0) {
			total_borrow = MapUtils.getIntValue(list1.get(0), "total_borrow");
		}
		
		String sql2 = "select count(*) in_borrow from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id "
				+ " where br.people_id = '"+username+"' and t.return_time is null";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		if (list2 != null && list2.size() > 0) {
			in_borrow = MapUtils.getIntValue(list1.get(0), "in_borrow");
		}
		
		String sql3 = "select book.name_ book_name from t_book_borrow t left join t_book book on book.no_ = t.book_id "
				+ " left join t_book_reader br on br.no_ = t.book_reader_id where br.people_id = '"+username+"' and t.return_time is null";
		inBorrowList = baseDao.queryListInLowerKey(sql3);
		
		resultMap.put("total_borrow", total_borrow);
		resultMap.put("in_borrow", in_borrow);
		resultMap.put("inBorrowList", inBorrowList);
		return resultMap;
	}

	@Override
	public Map<String, Object> queryCourse(String username) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		double gpa = 0d;
		int total_course = 0;
		List<Map<String,Object>> notPassCourse = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> bestCourse = new ArrayList<Map<String,Object>>();
		String sql1 = "select t.gpa from t_stu_score_total t where t.stu_id = '"+username+"'";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		if (list1 != null && list1.size() > 0) {
			gpa = MapUtils.getDoubleValue(list1.get(0), "gpa");
		}
		
		String sql2 = "select count(*) total_course from t_stu_score t where t.stu_id = '"+username+"'";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		if (list2 != null && list2.size() > 0) {
			total_course = MapUtils.getIntValue(list2.get(0), "total_course");
		}
		
		String sql3 = "select cou.name_ course_name,t.school_year,t.term_code from t_stu_score t "
				+ " left join t_code co on co.code_ = t.hierarchical_score_code and co.code_type = 'HIERARCHICAL_SCORE_CODE' "
				+ " left join t_course cou on cou.code_ = t.coure_code"
				+ " where t.stu_id = '"+username+"' and (co.name_ = '不及格' or t.centesimal_score < 60) order by school_year desc,term_code desc";
		notPassCourse = baseDao.queryListInLowerKey(sql3);
		
		String sql4 = "select rs.course_name,rs.centesimal_score,rs.rn from (select td.course_name,td.centesimal_score,rn,rownum r from ("
				+ " select ts.stu_id,ts.course_name,ts.centesimal_score,ts.rn from (select tt.stu_id,tt.course_name,tt.centesimal_score,"
				+ " row_number() over(partition by tt.course_name order by tt.centesimal_score desc) rn from ("
				+ " select t.stu_id, cou.name_ course_name,case when t.hierarchical_score_code is not null then csh.centesimal_score"
				+ " when t.hierarchical_score_code is null then t.centesimal_score end centesimal_score  from t_stu_score t "
				+ " left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
				+ " left join t_course cou on cou.code_ = t.coure_code"
				+ " left join t_stu stu on stu.no_ = t.stu_id"
				+ " where stu.major_id = (select major_id from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.enroll_grade = (select enroll_grade from t_stu s where s.no_ = '"+username+"'))tt where tt.centesimal_score is not null)ts "
				+ " where ts.stu_id = '"+username+"' order by ts.rn ) td) rs where rs.r <= 3";
		bestCourse = baseDao.queryListInLowerKey(sql4);
		
		resultMap.put("total_course", total_course);
		resultMap.put("gpa", gpa);
		resultMap.put("notPassCourse", notPassCourse);
		resultMap.put("bestCourse", bestCourse);
		
		return resultMap;
	}
}