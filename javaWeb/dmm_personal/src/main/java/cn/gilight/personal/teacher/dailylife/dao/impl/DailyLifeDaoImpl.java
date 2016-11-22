package cn.gilight.personal.teacher.dailylife.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.personal.teacher.dailylife.dao.DailyLifeDao;

@Repository("dailyLifeDao")
public class DailyLifeDaoImpl implements DailyLifeDao {
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getMonthConsume(String monthStart,
			String tea_id) {
		String sql = " select nvl(sum(pay.pay_money),0) month_consume from t_card_pay pay "
				+ " inner join t_card c on c.no_ = pay.card_id where c.people_id = '"+tea_id+"' and pay.time_ like '"+monthStart+"%'"
				+ " group by c.people_id";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getTotalConsume(String tea_id,String monthStart) {
		String sql = " select nvl(sum(t.sum_val),0) total_consume from t_card_pay_month t where t.sno = '"+tea_id+"' and t.datetime != '"+monthStart+"'";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getInBorrow(String tea_id) {
		String sql = "select count(*) in_borrow from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id"
				+ " where br.people_id = '"+ tea_id +"' and t.return_time is null group by br.people_id";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getTotalBorrow(String tea_id) {
		String sql = "select count(*) total_borrow from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id"
				+ " where br.people_id = '"+ tea_id +"' group by br.people_id";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getRecommendedBook(String tea_id) {
		String sql = "select aa.book_name name_,aa.counts from (select tt.store_code ,tt.sums,rownum rm from ("
				+ " select book.store_code,count(*) sums from t_book_borrow t left join t_book_reader br on t.book_reader_id = br.no_"
				+ " left join t_book book on book.no_ = t.book_id where br.people_id = '"+tea_id+"' group by book.store_code order by "
				+ " sums desc) tt) dd"
				+ " right join t_recommend_book aa on aa.store_code = dd.store_code where dd.rm<=3 order by counts desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Page getMonthConsumeList(String monthStart,String nextMonth,
			String tea_id,Page page,String flag) {
		monthStart = monthStart.substring(0, 7);
		nextMonth = nextMonth.substring(0, 7);
		String sql2 = "";
		if("eat".equals(flag)){
			sql2 = " and cdd.code_ = '210' ";
		}else if ("other".equals(flag)){
			sql2 = " and (cdd.code_ != '210' or cdd.code_ is null) ";
		}
		String sql = "select pay.time_ pay_time,cdd.name_ pay_type,pay.pay_money from t_card_pay pay "
				+ " inner join t_card c on c.no_ = pay.card_id"
				+ " left join t_code_card_deal cdd on cdd.code_ = pay.card_deal_id where c.people_id = '"+ tea_id +"' "
				+ " and pay.time_ between '"+ monthStart +"' and '"+ nextMonth +"' "+ sql2 +" order by pay.time_ desc";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public List<Map<String, Object>> getMonthMeals(String monthStart,String nextMonth,
			String tea_id) {
		monthStart = monthStart.substring(0, 7);
		nextMonth = nextMonth.substring(0, 7);
		String sql  = "select c.people_id,sum(pay.pay_money) pay_eat from t_card_pay pay "
				+ " inner join t_card c on c.no_ = pay.card_id where c.people_id = '"+ tea_id +"' and pay.time_ between '"+ monthStart +"' and '"+ nextMonth +"'"
				+ " and pay.card_deal_id = '210' group by c.people_id";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getMonthOther(String monthStart,String nextMonth,
			String tea_id) {
		monthStart = monthStart.substring(0, 7);
		nextMonth = nextMonth.substring(0, 7);
		String sql = "select c.people_id,sum(pay.pay_money) pay_other from t_card_pay pay "
				+ " inner join t_card c on c.no_ = pay.card_id where c.people_id = '"+ tea_id +"' and pay.time_ between '"+ monthStart +"' and '"+ nextMonth +"'"
				+ " and (pay.card_deal_id != '210' or pay.card_deal_id is null) group by c.people_id";
		return baseDao.queryForList(sql);
	}

	@Override
	public List<Map<String,Object>> getPayHistory(String tea_id,int year) {
		String sql = "select substr(t.datetime,0,4) year_,substr(t.datetime,6,7) month_,sum(t.sum_val) pay_month_total from t_card_pay_month t "
				+ " where t.sno = '"+tea_id+"' and substr(t.datetime,0,4) = '"+year+"' group by t.datetime order by month_ desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getInBorrowList(String tea_id) {
		String sql = "select book.name_ book_name,t.borrow_time,t.should_return_time from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id "
				+ " left join t_book book on book.no_ = t.book_id"
				+ " where br.people_id = '"+ tea_id +"' and  t.return_time is null ";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getOutOfDateBorrow(String tea_id) {
		String nowDate = DateUtils.getNowDate();
		String sql = "select book.name_ book_name,t.borrow_time,t.should_return_time from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id "
				+ " left join t_book book on book.no_ = t.book_id"
				+ " where br.people_id = '"+ tea_id +"' and  t.return_time is null and t.should_return_time < '"+ nowDate +"' ";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public List<Map<String, Object>> getReturnBorrow(String tea_id) {
		String sql = "select book.name_ book_name,t.borrow_time,t.return_time from t_book_borrow t left join t_book_reader br on br.no_ = t.book_reader_id "
			+ " left join t_book book on book.no_ = t.book_id"
			+ " where br.people_id = '"+tea_id+"' and  t.return_time is not null";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getYears(String tea_id) {
		String sql  = "select substr(t.time_,0,4) year_ from t_card_pay t"
				+ " left join t_card c on c.no_ = t.card_id where c.people_id = '"+ tea_id +"' group by substr(t.time_,0,4) order by year_ desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> getStopCounts(String tea_id) {
		String sql = "select count(*) counts from t_car_record t left join t_car_card cc on t.cardno = cc.cardno where cc.username = '"+tea_id+"' and t.is_in = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getStopTimeAvg(String tea_id) {
		String sql = "select round(round(sum(s.time_)/count(*))/60,2) day_avg from ("
				+ " select to_char(t.credit_card_time,'YYYY-MM-DD') date_ ,sum(t.stay_time) time_ from t_car_record t "
				+ " left join t_car_card cc on t.cardno = cc.cardno "
				+ " where cc.username = '"+tea_id+"' and t.stay_time is not null group by to_char(t.credit_card_time,'YYYY-MM-DD')) s";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getYearsMonth(String tea_id) {
		String sql = "select to_char(t.credit_card_time,'YYYY') year_,to_char(t.credit_card_time,'MM') month_ from t_car_record t "
				+ " left join t_car_card cc on cc.cardno = t.cardno where cc.username = '"+tea_id+"'"
				+ " group by  to_char(t.credit_card_time,'YYYY'),to_char(t.credit_card_time,'MM') order by year_ desc,month_ desc";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public List<Map<String, Object>> getCarStop(String tea_id, int year,
			String month) {
		String sql = "select to_char(t.credit_card_time,'YYYY-MM-DD HH24:MI:SS') time_,case when t.is_in = 1 then '进校' when t.is_in = 0 then '离校' end flag "
				+ " from t_car_record t left join t_car_card cc on cc.cardno = t.cardno "
				+ " where cc.username = '"+tea_id+"' and to_char(t.credit_card_time,'YYYY') = '"+year+"' and to_char(t.credit_card_time,'MM') = '"+month+"' order by time_ desc";
		return baseDao.queryListInLowerKey(sql);
	}
	
}
