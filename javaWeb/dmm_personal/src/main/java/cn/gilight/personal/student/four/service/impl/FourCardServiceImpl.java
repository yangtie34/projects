package cn.gilight.personal.student.four.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.personal.student.four.service.FourCardService;

@Service("fourCardService")
public class FourCardServiceImpl implements FourCardService{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getCardMap(String username) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String sql  = "select nvl(sum(t.nums),0) nums , nvl(sum(t.sums),0) sums,nvl(round(nvl(sum(t.sums),0)/sum(t.day_counts),2),0) day_avg from t_card_total t where t.stu_id = '"+username+"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		int nums = 0;
		double sums = 0;
		double day_avg = 0;
		if(list != null && list.size()>0){
			nums = MapUtils.getIntValue(list.get(0), "nums");
			sums = MapUtils.getDoubleValue(list.get(0), "sums");
			day_avg = MapUtils.getDoubleValue(list.get(0), "day_avg");
		}
		
		String sql2 = "select nvl(count(*),0) nums from t_stu t where t.enroll_grade = (select stu.enroll_grade from t_stu stu where stu.no_ = '"+username+"')";
		List<Map<String,Object>> totalList = baseDao.queryListInLowerKey(sql2);
		int	total = MapUtils.getIntValue(totalList.get(0), "nums");
		
		String sql3 = "select nvl(count(distinct tt.stu_id),0) passNums from (select t.stu_id, sum(t.sums) sums from t_card_total t "
				+ " left join t_stu stu on stu.no_ = t.stu_id where stu.enroll_grade = (select s.enroll_grade from t_stu s "
				+ " where s.no_ = '"+username+"') group by t.stu_id) tt where tt.sums < '"+sums+"'";
		List<Map<String,Object>> passList = baseDao.queryListInLowerKey(sql3);
		int	passNums = MapUtils.getIntValue(passList.get(0), "passnums");
		
		String passNumPro = "0%";
		if(total != 0){
			passNumPro = MathUtils.getPercent(passNums, total);
		}
		
		resultMap.put("nums", nums);
		resultMap.put("sums", sums);
		resultMap.put("day_avg", day_avg);
		resultMap.put("passNumPro", passNumPro);
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getCardChart(String username) {
		String sql  = "select tt.school_year,tt.term_code,tt.sums,ts.avg_ from ("
				+ " select t.stu_id,t.school_year,t.term_code,sum(t.sums) sums from t_card_total t where t.stu_id = '"+username+"' group by t.stu_id,t.school_year,t.term_code) tt"
				+ " left join (select t.school_year,t.term_code, nvl(round(sum(t.sums)/count(*),2),0) avg_ from t_card_total t left join t_stu stu on stu.no_ = t.stu_id "
				+ " where stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_= '"+username+"')"
				+ " group by t.school_year,t.term_code) ts on tt.school_year = ts.school_year and tt.term_code = ts.term_code order by tt.school_year,tt.term_code";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> getCardPie(String username) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String sql = "select t.card_deal,nvl(sum(t.sum_val),0) sums from t_card_pay_month t where t.sno = '"+username+"' and(t.card_deal = '餐费支出' or t.card_deal = '商场购物') group by t.card_deal";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		double eatPay = 0;
		double shopPay = 0;
		if(list!= null && list.size()>0){
			for(Map<String,Object> m : list){
				if("餐费支出".equals(MapUtils.getString(m, "card_deal"))){
					eatPay = MapUtils.getDoubleValue(m, "sums");
				}
				if("商场购物".equals(MapUtils.getString(m, "card_deal"))){
					shopPay = MapUtils.getDoubleValue(m, "sums");
				}
			}
		}
		resultMap.put("eatPay", eatPay);
		resultMap.put("shopPay", shopPay);
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getCardWins(String username) {
		String sql = "select td.name_,td.nums,td.sums,round(td.sums/ts.total_pay,2)*100||'%' pro from ("
				+ " select sum(t.pay_money) total_pay from t_card_pay t left join t_card ca on ca.no_ = t.card_id where ca.people_id = '"+username+"') ts left join ("
				+ " select tt.name_,tt.nums,tt.sums,rownum rn from (select tcd.name_,count(*) nums,nvl(sum(t.pay_money),0) sums from t_card_pay t "
				+ " left join t_card ca on ca.no_ = t.card_id"
				+ " left join t_code_card_deal cdd on cdd.code_ = t.card_deal_id"
				+ " left join t_card_port cp on cp.no_ = t.card_port_id left join t_card_dept tcd on tcd.code_ = cp.card_dept_id "
				+ " where ca.people_id = '"+username+"' and cdd.name_ = '餐费支出' group by tcd.name_ order by sums desc) tt) td on 1=1 where td.rn <= 3";
		return baseDao.queryListInLowerKey(sql);
	}

	@Override
	public Map<String, Object> getShopCard(String username) {
		String sql = "select td.sums,round(td.sums/ts.total_pay,2)*100||'%' pro from"
				+ " (select nvl(sum(t.sum_val),0) total_pay from t_card_pay_month t where t.sno = '"+username+"')ts left join ( "
				+ " select nvl(sum(t.sum_val),0) sums from t_card_pay_month t where t.sno = '"+username+"' and t.card_deal = '商场购物') td on 1=1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getWashCard(String username) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String mysql = "select t.sno,nvl(sum(t.sum_val),0) sums from t_card_pay_month t inner join t_stu stu on stu.no_ = t.sno"
				+ " where t.card_deal = '淋浴支出' and stu.sex_code = (select s.sex_code from t_stu s where s.no_ = '"+username+"') "
				+ " and stu.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') "
				+ " group by t.sno order by sums desc";
		
		String sql1 = "select td.sums max_sums,ts.sums my_sums from (select tt.sno,tt.sums,rownum rn from ("
				+ mysql + ") tt) td left join "
				+ " (select nvl(sum(t.sum_val),0) sums from t_card_pay_month t where t.sno = '"+username+"' and t.card_deal = '淋浴支出') ts on 1=1 where td.rn = 1";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		double my_sums = 0;
		double max_sums = 0;
		if(list1 != null && list1.size()>0){
			my_sums = MapUtils.getDoubleValue(list1.get(0), "my_sums");
			max_sums = MapUtils.getDoubleValue(list1.get(0), "max_sums");
		}
		
		String totalSql = "select co.name_ sex,count(*) total from t_stu t left join t_code co on co.code_type = 'SEX_CODE'"
				+ " and co.code_ = t.sex_code where t.sex_code = (select s.sex_code from t_stu s where s.no_ = '"+username+"')"
				+ " and t.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') group by co.name_ ";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(totalSql);
		int total = 0;
		String sex = "";
		if(list2 != null && list2.size()>0){
			total = MapUtils.getIntValue(list2.get(0), "total");
			sex = MapUtils.getString(list2.get(0), "sex");
		}
		
		String sql3 = "select count(tt.sno) nums from ("
				+ mysql + ") tt where tt.sums < '"+my_sums+"'";
		List<Map<String,Object>> list3 = baseDao.queryListInLowerKey(sql3);
		int passNums = 0;
		if(list3 != null && list3.size()>0){
			passNums = MapUtils.getIntValue(list3.get(0), "nums");
		}
		
		String passPro = "0%";
		if(total != 0){
			passPro = MathUtils.getPercent(passNums, total);
		}
		
		resultMap.put("my_sums", my_sums);
		resultMap.put("max_sums", max_sums);
		resultMap.put("sex", sex);
		resultMap.put("passPro", passPro);
		return resultMap;
	}

	@Override
	public Map<String, Object> getCardMeal(String username) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		String sql  = "select nvl(t.breakfast_count,0) zccs,nvl(t.more_two_meal_count,0) mlcts,nvl(t.more_three_meal_count,0) mccts"
				+ " from t_card_meal t where t.stu_id = '"+username+"'";
		List<Map<String,Object>> myList = baseDao.queryListInLowerKey(sql);
		int zccs = 0;
		int mlcts = 0;
		int mccts = 0;
		if(myList != null && myList.size()>0){
			zccs = MapUtils.getIntValue(myList.get(0), "zccs");
			mlcts = MapUtils.getIntValue(myList.get(0), "mlcts");
			mccts = MapUtils.getIntValue(myList.get(0), "mccts");
		}
		
		String totalSql = "select co.name_ sex,count(t.no_) total from t_stu t left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code"
				+ " where t.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"')"
				+ " and t.sex_code = (select s.sex_code from t_stu s where s.no_ = '"+username+"') group by co.name_";
		String sex = "";
		int total = 0;
		List<Map<String,Object>> totalList = baseDao.queryListInLowerKey(totalSql);
		if(totalList != null && totalList.size()>0){
			sex = MapUtils.getString(totalList.get(0), "sex");
			total = MapUtils.getIntValue(totalList.get(0), "total");
		}
		
		
		String zcSql = "select nvl(count(t.stu_id),0) breakfast_pass from t_card_meal t "
				+ " left join t_stu stu on stu.no_ = t.stu_id where t.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') "
				+ " and t.sex = (select c.name_ from t_stu s left join t_code c on c.code_type = 'SEX_CODE' and c.code_ = s.sex_code"
				+ " where s.no_ = '"+username+"')and t.breakfast_count < "+zccs;
		List<Map<String,Object>> zcList = baseDao.queryListInLowerKey(zcSql);
		int zccsPass = 0;
		if(zcList != null && zcList.size()>0){
			zccsPass = MapUtils.getIntValue(zcList.get(0), "breakfast_pass");
		}
		
		String mlcSql = "select nvl(count(t.stu_id),0) more_two_pass from t_card_meal t "
				+ " left join t_stu stu on stu.no_ = t.stu_id where t.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') "
				+ " and t.sex = (select c.name_ from t_stu s left join t_code c on c.code_type = 'SEX_CODE' and c.code_ = s.sex_code"
				+ " where s.no_ = '"+username+"')and t.more_two_meal_count < "+mlcts;
		List<Map<String,Object>> mlcList = baseDao.queryListInLowerKey(mlcSql);
		int mlcPass = 0;
		if(mlcList != null && mlcList.size()>0){
			mlcPass = MapUtils.getIntValue(mlcList.get(0), "more_two_pass");
		}
		
		String mccSql = "select nvl(count(t.stu_id),0) more_three_pass from t_card_meal t "
				+ " left join t_stu stu on stu.no_ = t.stu_id where t.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"') "
				+ " and t.sex = (select c.name_ from t_stu s left join t_code c on c.code_type = 'SEX_CODE' and c.code_ = s.sex_code"
				+ " where s.no_ = '"+username+"')and t.more_three_meal_count < "+mccts;
		List<Map<String,Object>> mccList = baseDao.queryListInLowerKey(mccSql);
		int mccPass = 0;
		if(mccList != null && mccList.size()>0){
			mccPass = MapUtils.getIntValue(mccList.get(0), "more_three_pass");
		}
		String zccsPro = "0%";
		String mlctsPro = "0%";
		String mcctsPro = "0%";
		if(total != 0){
			zccsPro = MathUtils.getPercent(zccsPass, total);
			mlctsPro = MathUtils.getPercent(mlcPass, total);
			mcctsPro = MathUtils.getPercent(mccPass, total);
		}
		resultMap.put("sex", sex);
		resultMap.put("zccs", zccs);
		resultMap.put("mlcts", mlcts);
		resultMap.put("mccts", mccts);
		resultMap.put("zccsPro", zccsPro);
		resultMap.put("mlctsPro", mlctsPro);
		resultMap.put("mcctsPro", mcctsPro);
		
		return resultMap;
	}

	@Override
	public Map<String, Object> getCardMealPayAvg(String username) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		String sql = "select nvl(round(sum(tt.breakfast_avg)/count(tt.stu_id),2),0) breakfast_avg,"
				+ " nvl(round(sum(tt.lunch_avg)/count(tt.stu_id),2),0) lunch_avg,nvl(round(sum(tt.dinner_avg)/count(tt.stu_id),2),0) dinner_avg from ("
				+ " select t.stu_id,round(t.breakfast_pay_sum/t.breakfast_count,2) breakfast_avg,round(t.lunch_pay_sum/t.lunch_count,2) lunch_avg,"
				+ " round(t.dinner_pay_sum/t.dinner_count,2) dinner_avg from t_card_meal t "
				+ " where t.enroll_grade = (select s.enroll_grade from t_stu s where s.no_ = '"+username+"')"
				+ " and t.sex = (select co.name_ from t_stu s left join t_code co on co.code_type = 'SEX_CODE' "
				+ " and co.code_ = s.sex_code where s.no_ = '"+username+"')) tt ";
		List<Map<String,Object>> allAvgList = baseDao.queryListInLowerKey(sql);
		double breakfast_avg = 0;
		double lunch_avg = 0;
		double dinner_avg = 0;
		if(allAvgList != null && allAvgList.size()>0){
			breakfast_avg = MapUtils.getDoubleValue(allAvgList.get(0), "breakfast_avg");
			lunch_avg = MapUtils.getDoubleValue(allAvgList.get(0), "lunch_avg");
			dinner_avg = MapUtils.getDoubleValue(allAvgList.get(0), "dinner_avg");
		}
		
		String mySql = "select t.stu_id,t.sex,nvl(round(t.breakfast_pay_sum/t.breakfast_count,2),0) breakfast_avg,"
				+ " nvl(round(t.lunch_pay_sum/t.lunch_count,2),0) lunch_avg,nvl(round(t.dinner_pay_sum/t.dinner_count,2),0) dinner_avg from t_card_meal t "
				+ " where t.stu_id = '"+username+"'";
		List<Map<String,Object>> myList = baseDao.queryListInLowerKey(mySql);
		double my_breakfast = 0;
		double my_lunch = 0;
		double my_dinner = 0;
		String sex = "";
		if(myList != null && myList.size()>0){
			my_breakfast = MapUtils.getDoubleValue(myList.get(0), "breakfast_avg");
			my_lunch = MapUtils.getDoubleValue(myList.get(0), "lunch_avg");
			my_dinner = MapUtils.getDoubleValue(myList.get(0), "dinner_avg");
			sex = MapUtils.getString(myList.get(0), "sex");
		}
		
		resultMap.put("breakfast_avg", breakfast_avg);
		resultMap.put("lunch_avg", lunch_avg);
		resultMap.put("dinner_avg", dinner_avg);
		resultMap.put("my_breakfast", my_breakfast);
		resultMap.put("my_lunch", my_lunch);
		resultMap.put("my_dinner", my_dinner);
		resultMap.put("sex", sex);
		
		return resultMap;
	}
	
	

}
