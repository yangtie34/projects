package cn.gilight.product.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.card.dao.RechargeDao;

@Repository("rechargeDao")
public class RechargeDaoImpl implements RechargeDao {
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getRecharge(String startDate, String endDate,
			Map<String, String> deptTeach) {
		String sql="select nvl(count(*),0) all_count,round(sum(t.up_money)) all_money, "+
					"count(distinct t.stu_id) all_stu, round(sum(t.up_money)/nvl(count(distinct t.stu_id),1),2) stu_money "+
					"from tl_card_recharge_stu_detil t where 1=1 "+
					CardTjUtil.getDateTJ(startDate, endDate)+
					SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_RE.getCode(),"t");
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getRechargeByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		String querySql="select * from tl_card_recharge_stu_detil t where 1=1 "+
						CardTjUtil.getDateTJ(startDate, endDate)+
						SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_RE.getCode(),"t");
		
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeach,querySql,false,"inner join");
		
		String sql="select next_dept_code code,nvl(next_dept_name,'未维护') name,count(*) all_count, "+
					"sum(t.up_money) all_money from ("+sqldept+") t "+
					"group by next_dept_code,next_dept_name  order by code  ";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getRechargeRegion(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql="select sum(value) value,name,code from (                      "+
				"select count(*) value,                                        "+
				"case when t.up_money <=50 then '50元(含)以下'                 "+
				"when t.up_money >50 and t.up_money <=100 then '50-100(含)'    "+
				"when t.up_money >100 and t.up_money <=150 then '100-150(含)'  "+
				"when t.up_money >150 and t.up_money <=200 then '150-200(含)'  "+
				"when t.up_money >200  then '200元以上'                        "+
				"else  '未维护' end name,                                      "+
				"  case when t.up_money <=50 then 50                           "+
				"when t.up_money >50 and t.up_money <=100 then 100             "+
				"when t.up_money >100 and t.up_money <=150 then 150            "+
				"when t.up_money >150 and t.up_money <=200 then 200            "+
				"when t.up_money >200  then 201                                "+
				"else 0 end code                                               "+
				"from tl_card_recharge_stu_detil t where 1=1                   "+
				CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_RE.getCode(),"t")+
				"group by t.up_money ) group by code,name order by code        ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getLastMoneyRegion(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql="select sum(value) value,name,code from (                       "+
				"select count(*) value,                                         "+
				"case when t.old_money <=10 then '10元(含)以下'                 "+
				"when t.old_money >10 and t.old_money <=25 then '10-25(含)'   "+
				"when t.old_money >25 and t.old_money <=50 then '25-50(含)' "+
				"when t.old_money >50 and t.old_money <=100 then '50-100(含)' "+
				"when t.old_money >100  then '100元以上'                        "+
				"else  '未维护' end name,                                       "+
				"  case when t.old_money <=10 then 10                           "+
				"when t.old_money >10 and t.old_money <=25 then 25            "+
				"when t.old_money >25 and t.old_money <=50 then 50           "+
				"when t.old_money >50 and t.old_money <=100 then 100           "+
				"when t.old_money >100  then 101                                "+
				"else 0 end code                                                "+
				"from tl_card_recharge_stu_detil t where 1=1                    "+
				CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_RE.getCode(),"t")+
				"group by t.old_money ) group by code,name order by code        ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getRechargeByType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql="select t.card_deal_id code,t.card_deal_name name,  "+
				"count(*) all_count,sum(t.UP_money) all_money from  "+
				"tl_card_recharge_stu_detil t where 1=1             "+
				CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_RE.getCode(),"t")+
				"group by t.card_deal_id,t.card_deal_name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getRechargeByHour(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_RE.getCode(),"t");
		String sql="select * from (                                                                        "+
				"select t.hour_,'0'code,'总体' name,count(*) all_count,sum(t.UP_money) all_money from   "+
				"tl_card_recharge_stu_detil t where 1=1                                                 "+
				tj+
				"group by t.hour_                                                                       "+
				"union all                                                                              "+
				"select t.hour_,t.card_deal_id code,t.card_deal_name name,                              "+
				"count(*) all_count,sum(t.UP_money) all_money from                                      "+
				"tl_card_recharge_stu_detil t where 1=1                                                 "+
				tj+
				"group by t.hour_ ,t.card_deal_id,t.card_deal_name ) order by code,hour_                ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getRechargeTrend(
			Map<String, String> deptTeach) {
		String sql="select to_char(year_month,'yyyy') year,sum(ALL_COUNT) ALL_COUNT,sum(ALL_MONEY) ALL_MONEY "+
					"from TL_CARD_TREND T where flag='recha_year' "+
					SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_RE.getCode(),"t")+
					" group by year_month order by year";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getRechargeTrendByType(
			Map<String, String> deptTeach) {
		String sql="select to_char(year_month,'yyyy') year,type_code,type_name,sum(ALL_COUNT) ALL_COUNT,sum(ALL_MONEY) ALL_MONEY "+
				"from TL_CARD_TREND T where flag='recha_year' "+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_RE.getCode(),"t")+
				" group by year_month,type_code,type_name order by year,type_code";
	return baseDao.getJdbcTemplate().queryForList(sql);
}
	
}
