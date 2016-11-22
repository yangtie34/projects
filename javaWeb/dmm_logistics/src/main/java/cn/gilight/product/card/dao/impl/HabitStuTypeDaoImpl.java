package cn.gilight.product.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.card.dao.HabitStuTypeDao;

@Repository("habitStuTypeDao")
public class HabitStuTypeDaoImpl implements HabitStuTypeDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getHabitZao(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select sum(all_count) all_count,sum(all_money) all_money,code,name from (    "+
				"select sum(t.pay_count) all_count,sum(t.pay_money) all_money,  "+
				"case when t.hour_ <='09' then '早'     "+
				"when t.hour_ >'09' and t.hour_ <='14' then '中'      "+
				"else  '晚' end name,  "+
				"case when t.hour_ <='09' then 1 "+
				"when t.hour_ >'09' and t.hour_ <='14' then 2  "+
				"else  3 end code      "+
				"from tl_card_pay_stu_month t where 1=1  "+tj+
				"and t.card_deal_id in ("+CardTjUtil.ccx+")     "+
				" group by t.hour_ ) group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitZaoBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select sum(all_count) all_count,sum(all_money) all_money,code,name ,type_code,type_name from ( "+
				"select sum(t.pay_count) all_count,sum(t.pay_money) all_money,  "+
				"case when t.hour_ <='09' then '早'     "+
				"when t.hour_ >'09' and t.hour_ <='14' then '中'      "+
				"else  '晚' end name,  "+
				"case when t.hour_ <='09' then 1 "+
				"when t.hour_ >'09' and t.hour_ <='14' then 2  "+
				"else  3 end code,     "+
				"t.sex_code type_code, t.sex_name type_name    "+
				"from tl_card_pay_stu_month t where 1=1"+tj+" and t.card_deal_id in ("+CardTjUtil.ccx+")"+
				"group by t.sex_code, t.sex_name, t.hour_ ) group by type_code,type_name,code,name order by type_code,code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitZaoByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select sum(all_count) all_count,sum(all_money) all_money,code,name ,type_code,type_name from ( "+
				"select sum(t.pay_count) all_count,sum(t.pay_money) all_money,  "+
				"case when t.hour_ <='09' then '早'     "+
				"when t.hour_ >'09' and t.hour_ <='14' then '中'      "+
				"else  '晚' end name,  "+
				"case when t.hour_ <='09' then 1 "+
				"when t.hour_ >'09' and t.hour_ <='14' then 2  "+
				"else  3 end code,     "+
				"t.edu_id type_code, t.edu_name type_name      "+
				"from tl_card_pay_stu_month t where 1=1 "+tj+
				"and t.card_deal_id in ("+CardTjUtil.ccx+") "+
				"group by t.edu_id, t.edu_name, t.hour_ ) group by type_code,type_name,code,name order by type_code,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitHour(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select t.hour_, "+
				"sum(pay_money) all_money,"+
				"sum(pay_count) all_count "+
				"  from tl_card_pay_stu_month t  "+
				" where 1=1 "+tj+
				"   and t.card_deal_id in ("+CardTjUtil.ccx+")  "+
				" group by  t.hour_    "+
				" order by  t.hour_  ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitHourBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select t.sex_code type_code, "+
				"t.sex_name type_name, "+
				"t.hour_, "+
				"sum(pay_money) all_money, "+
				"sum(pay_count) all_count "+
				"  from tl_card_pay_stu_month t  "+
				" where 1=1 "+tj+
				"   and t.card_deal_id in ("+CardTjUtil.ccx+")  "+
				" group by t.sex_code, t.sex_name, t.hour_     "+
				" order by t.sex_code, t.hour_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitHourByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select t.edu_id type_code, "+
				"t.edu_name type_name,      "+
				"t.hour_,     "+
				"sum(pay_money) all_money,  "+
				"sum(pay_count) all_count   "+
				"  from tl_card_pay_stu_month t    "+
				" where 1=1   "+tj+
				"   and t.card_deal_id in ("+CardTjUtil.ccx+")    "+
				" group by t.edu_id, t.edu_name, t.hour_  "+
				" order by t.edu_id, t.hour_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitEat(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select count(*) value,eat code,'日均'||eat||'餐' name from (  "+
				"select round(sum(a)/sum(value)) eat,stu_id from (      "+
				"select count(*) value,t.eat,count(*)*t.eat a,t.stu_id from tl_card_stu_eat t "+
				"where 1=1 "+tj+
				"group by t.eat,t.stu_id ) group by stu_id ) group by eat order by code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitEatBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select count(*) value,eat code,'日均'||eat||'餐' name,type_code,type_name from (   "+
				"select round(sum(a)/sum(value)) eat,stu_id,type_code,type_name from ( "+
				"select count(*) value,t.eat,count(*)*t.eat a,t.stu_id,t.sex_code type_code,t.sex_name type_name  "+
				"from tl_card_stu_eat t where 1=1 "+tj+
				"group by t.eat,t.stu_id,t.sex_code,t.sex_name )    "+
				"group by stu_id,type_code,type_name ) group by eat,type_code,type_name order by type_code,code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitEatByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HST.getCode(),"t");
		String sql="select count(*) value,eat code,'日均'||eat||'餐' name,type_code,type_name from (   "+
				"select round(sum(a)/sum(value)) eat,stu_id,type_code,type_name from ("+
				"select count(*) value,t.eat,count(*)*t.eat a,t.stu_id,t.edu_id type_code,t.edu_name type_name    "+
				"from tl_card_stu_eat t where 1=1 "+tj+
				"group by t.eat,t.stu_id,t.edu_id,t.edu_name )      "+
				"group by stu_id,type_code,type_name ) group by eat,type_code,type_name order by type_code,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
}
