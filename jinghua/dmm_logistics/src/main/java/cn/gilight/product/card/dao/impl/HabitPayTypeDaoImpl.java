package cn.gilight.product.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.card.dao.HabitPayTypeDao;

/**
 * 学生分消费类型消费习惯分析
 *
 */
@Repository("habitPayTypeDao")
public class HabitPayTypeDaoImpl implements HabitPayTypeDao {
	
	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Map<String, Object> getHabitCount(String startDate, String endDate,
			Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HPT.getCode(),"t");
		String sql="select sum(t.pay_count) all_count,sum(t.pay_money) all_money from tl_card_dept_hour t "+
				"where 1=1 "+tj+
				" and t.card_deal_id in ("+CardTjUtil.ccx+") ";
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitCountByType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HPT.getCode(),"t");
		String sql="select sum(t.pay_count) all_count,sum(t.pay_money) all_money,t.card_deal_id type_code, "+
				"t.card_deal_name type_name from tl_card_dept_hour t "+
				"where 1=1 "+tj+
				"and t.card_deal_id in ("+CardTjUtil.ccx+") group by t.card_deal_id, t.card_deal_name order by type_code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitZao(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HPT.getCode(),"t");
		String sql="select sum(all_count) all_count,sum(all_money) all_money,code,name from ( "+
				"select sum(t.pay_count) all_count,sum(t.pay_money) all_money, "+
		"case when t.hour_ <='09' then '早' "+
		"when t.hour_ >'09' and t.hour_ <='14' then '中' "+
		"else  '晚' end name, "+
		"case when t.hour_ <='09' then 1 "+
		"when t.hour_ >'09' and t.hour_ <='14' then 2 "+
		"else  3 end code "+
		"from tl_card_dept_hour t where 1=1 "+tj+
		"and t.card_deal_id in ("+CardTjUtil.ccx+") "+
		"group by t.hour_ ) group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitZaoByType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HPT.getCode(),"t");
		String sql="select sum(all_count) all_count,sum(all_money) all_money,code,name ,type_code,type_name from ( "+
				"select sum(t.pay_count) all_count,sum(t.pay_money) all_money, "+
		"case when t.hour_ <='09' then '早'  "+
		"when t.hour_ >'09' and t.hour_ <='14' then '中'  "+
		"else  '晚' end name, "+
		"case when t.hour_ <='09' then 1  "+
		"when t.hour_ >'09' and t.hour_ <='14' then 2  "+
		"else  3 end code, "+
		"t.card_deal_id type_code, t.card_deal_name type_name "+
		"from tl_card_dept_hour t where 1=1 "+tj+
		"and t.card_deal_id in ("+CardTjUtil.ccx+")  "+
		"group by t.card_deal_id, t.card_deal_name, t.hour_ )  "+
		"group by type_code,type_name,code,name order by type_code,code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitHour(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HPT.getCode(),"t");
		String sql="select t.hour_,sum(pay_money) all_money,sum(pay_count) all_count "+
				"from tl_card_dept_hour t "+
				"where 1=1 "+tj+
				"and t.card_deal_id in ("+CardTjUtil.ccx+") "+
				"group by  t.hour_ order by  t.hour_ ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getHabitHourByType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_HPT.getCode(),"t");
		String sql="select t.card_deal_id type_code,t.card_deal_name type_name, "+
				"t.hour_,sum(pay_money) all_money,sum(pay_count) all_count "+
				"from tl_card_dept_hour t "+
				"where 1=1 "+tj+
				"and t.card_deal_id in ("+CardTjUtil.ccx+") "+
				"group by t.card_deal_id, t.card_deal_name, t.hour_ "+
				"order by t.card_deal_id, t.hour_ ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
