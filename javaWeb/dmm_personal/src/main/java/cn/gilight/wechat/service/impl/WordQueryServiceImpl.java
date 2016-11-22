package cn.gilight.wechat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.wechat.service.WordQueryService;

@Service("wordQueryService")
public class WordQueryServiceImpl implements WordQueryService{
	
	@Autowired
	private BaseDao baseDao;


	@Override
	public Map<String, Object> queryTyrant(String sno,String month) {
		String sql  = "select tl.sno,tl.name,tl.month_pay from ("
				+ " select tt.sno, tt.name,tt.month_pay ,rownum rn from ("
				+ " select t.sno,t.name,sum(sum_val) month_pay from t_card_pay_month t"
				+ " inner join t_stu stu on stu.no_ = t.sno"
				+ " left join t_stu s on s.class_id = stu.class_id where s.no_ = '"+sno+"'"
				+ " and t.datetime = '"+month+"' group by t.sno ,t.name order by month_pay desc) tt)tl where tl.rn = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public Map<String, Object> queryInstructor(String school_year,String term_code, String stu_id) {
		String sql  ="select tea.name_ instructor_,nvl(tc.tel,'未维护') instructor_tel from ("
          + " select t.tea_id from t_classes_instructor t"
          + " left join t_stu stu on stu.class_id = t.class_id "
          + " where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and stu.no_ = '"+stu_id+"') tt"
          + " left join t_tea tea on tea.tea_no = tt.tea_id"
          + " left join t_tea_comm tc on tc.tea_id = tea.tea_no";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public List<Map<String, Object>> queryRoomie(String username) {
		String sql  ="select s.stu_id,stu.name_,cad.name_ origin_name from ("
				+ " select stu_id from t_dorm_berth_stu dbs "
				+ " inner join t_dorm_berth tdb on tdb.id = dbs.berth_id"
				+ " inner join (select dorm_id from t_dorm_berth_stu t left join t_dorm_berth db on db.id = t.berth_id"
				+ " where t.stu_id = '"+username+"') tt on tt.dorm_id = tdb.dorm_id) s left join t_stu stu on stu.no_ = s.stu_id"
				+ " left join t_code_admini_div cad on cad.code_ = stu.stu_origin_id"
				+ " where s.stu_id != '"+username+"' ";
		return baseDao.queryListInLowerKey(sql);
	}


	@Override
	public List<Map<String, Object>> queryStuInBorrow(String username) {
		String sql = "select book.name_ book_name,t.borrow_time,t.should_return_time from t_book_borrow t left join t_book_reader br "
				+ " on br.people_id = t.book_reader_id "
				+ " left join t_book book on book.no_ = t.book_id"
				+ " where br.people_id = '"+username+"' and  t.return_time is null  order by t.should_return_time ";
		return baseDao.queryListInLowerKey(sql);
	}


	@Override
	public List<Map<String, Object>> queryStuPaisan(String username) {
		String sql  = "select t.no_ stu_id,t.name_ stu_name,co.name_ sex,t.enroll_grade,comm.tel,bind.wechat_head_img from t_stu t "
				+ " left join t_stu stu on stu.stu_origin_id = t.stu_origin_id"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code"
				+ " left join t_stu_comm comm on comm.stu_id = t.no_"
				+ " left join t_wechat_bind bind on bind.username = t.no_"
				+ " where stu.no_ = '"+username+"' and  t.major_id = stu.major_id and t.no_ != '201403010102'";
		return baseDao.queryListInLowerKey(sql);
	}


	@Override
	public List<Map<String, Object>> getCardDeptMonth() {
		String sql = "select * from t_card_dept_month t order by eat_time desc,rn";
		return baseDao.queryListInLowerKey(sql);
	}


	@Override
	public List<Map<String, Object>> getStuFlunk(String username,String school_year, String term_code) {
		String sql = "select cou.name_ course_name,t.centesimal_score,co.name_ score from t_stu_score t "
				+ " left join t_course cou on cou.code_ = t.coure_code"
				+ " left join t_code co on co.code_type = 'HIERARCHICAL_SCORE_CODE' and co.code_ = t.hierarchical_score_code"
				+ " where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' and t.stu_id = '"+username+"' "
				+ " and (t.centesimal_score < 60 or t.centesimal_score is null ) and (co.name_ = '不及格' or co.name_ is null)";
		return baseDao.queryListInLowerKey(sql);
	}


	@Override
	public List<Map<String, Object>> getMajorSmart(String username) {
		String sql= "select t.name,co.name_ sex from t_stu_smart t left join t_stu stu on stu.enroll_grade = t.rxnj and stu.major_id = t.zyid "
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.gender where stu.no_ = '"+username+"'";
		return baseDao.queryListInLowerKey(sql);
	}
}
