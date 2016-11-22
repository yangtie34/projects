package cn.gilight.personal.student.four.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.personal.student.four.service.FourFirstService;

@Service("fourFirst")
public class FourFirstServiceImpl implements FourFirstService{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getFirstCard(String username) {
		String sql  ="select time_,pay_money,card_deal,card_win from ("
				+ " select tt.time_,tt.pay_money,tt.card_deal,tt.card_win ,rownum rn from ("
				+ " select t.time_,t.pay_money,ccd.name_ card_deal,tcd.name_ card_win from t_card_pay t left join t_card ca on ca.no_ = t.card_id"
				+ " left join t_card_port cp on cp.no_ = t.card_port_id left join t_card_dept tcd on tcd.code_ = cp.card_dept_id"
				+ " left join t_code_card_deal ccd on ccd.code_ = t.card_deal_id"
				+ " where ca.people_id = '"+username+"' order by t.time_) tt) where rn = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getFirstNotPass(String username) {
		String sql = "select ts.school_year,ts.term_code,ts.course_name from ( select tt.*,rownum rn from ("
				+ " select t.school_year,t.term_code,cou.name_ course_name,t.centesimal_score,t.hierarchical_score_code,t.stu_id,"
				+ " case when t.hierarchical_score_code is not null and co.name_ = '不及格' then 0"
				+ " when t.hierarchical_score_code is null and t.centesimal_score <60 then 0 else 1 end ispass  from t_stu_score t "
				+ " left join t_code co on co.code_type = 'HIERARCHICAL_SCORE_CODE' and co.code_ = t.hierarchical_score_code "
				+ " left join t_course cou on cou.code_ = t.coure_code"
				+ " where t.stu_id = '"+username+"' order by t.school_year,t.term_code) tt where tt.ispass = 0) ts where ts.rn = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getFirstPunish(String username) {
		String sql = "select ts.violate_date,ts.name_ from ("
				+ " select tt.violate_date,tt.name_,rownum rn from ("
				+ " select t.violate_date,co.name_ from t_stu_punish t"
				+ " left join t_code co on co.code_type = 'PUNISH_CODE' and co.code_ = t.punish_code"
				+ " where t.stu_id = '"+username+"' order by t.violate_date ) tt) ts where ts.rn = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getFirstBook(String username) {
		String sql = "select substr(ts.borrow_time,0,10) borrow_date,substr(ts.borrow_time,12,8) borrow_time,ts.book_name from ("
				+ " select tt.borrow_time ,tt.book_name,rownum rn from ("
				+ " select t.borrow_time,book.name_ book_name from t_book_borrow t left join t_book_reader br on t.book_reader_id = br.no_"
				+ " left join t_book book on book.no_ = t.book_id where br.people_id = '"+username+"' order by t.borrow_time) tt) ts where ts.rn =1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getFirstAward(String username) {
		String sql = "select ts.school_year,ts.money from (select tt.school_year,tt.money,rownum rn from("
				+ " select t.school_year,t.money from t_stu_award t where t.stu_id = '"+username+"' order by t.school_year ) tt) ts where ts.rn = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getFirstSubsidy(String username) {
		String sql = "select ts.school_year,ts.money from (select tt.school_year,tt.money ,rownum rn from ("
				+ " select t.school_year,t.money from t_stu_subsidy t where t.stu_id = '"+username+"' order by t.school_year) tt ) ts where ts.rn = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Map<String,Object> getFirstBookRke(String username){
		String sql = "select substr(ts.time_,0,10) date_,substr(ts.time_,12,8) time_ from (select tt.time_ ,rownum rn from (select t.time_ from t_book_rke t"
				+ " left join t_book_reader br on br.no_ = t.book_reader_id where br.people_id = '"+username+"' order by time_) tt) ts where ts.rn = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size() >0){
			return list.get(0);
		}
		return null;
	}

}
