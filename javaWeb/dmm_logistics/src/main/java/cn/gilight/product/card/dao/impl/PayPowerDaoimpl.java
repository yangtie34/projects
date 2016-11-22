package cn.gilight.product.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.card.dao.PayPowerDao;

@Repository("payPowerDao")
public class PayPowerDaoimpl implements PayPowerDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getPower(String startDate, String endDate,
			Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="SELECT SUM(PAY_MONEY) ALL_MONEY,SUM(PAY_COUNT) ALL_COUNT, "+
					"ROUND(AVG(PAY_MONEY/PAY_COUNT),2) ONE_MONEY,ROUND(AVG(PAY_MONEY/PAY_DAYS),2) DAY_MONEY  "+
					"FROM TL_CARD_USE_STU_MONTH T WHERE PAY_COUNT<>0  "+tj;
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPowerBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="SELECT SUM(PAY_MONEY) ALL_MONEY,SUM(PAY_COUNT) ALL_COUNT, "+
					"ROUND(AVG(PAY_MONEY/PAY_COUNT),2) ONE_MONEY,ROUND(AVG(PAY_MONEY/PAY_DAYS),2) DAY_MONEY, "+
					"SEX_CODE TYPE_CODE,SEX_NAME TYPE_NAME "+
					"FROM TL_CARD_USE_STU_MONTH T WHERE PAY_COUNT<>0  "+tj+
					"GROUP BY SEX_CODE,SEX_NAME ORDER BY TYPE_CODE ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPowerByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="SELECT SUM(PAY_MONEY) ALL_MONEY,SUM(PAY_COUNT) ALL_COUNT, "+
					"ROUND(AVG(PAY_MONEY/PAY_COUNT),2) ONE_MONEY,ROUND(AVG(PAY_MONEY/PAY_DAYS),2) DAY_MONEY, "+
					"EDU_ID TYPE_CODE,EDU_NAME TYPE_NAME "+
					"FROM TL_CARD_USE_STU_MONTH T WHERE PAY_COUNT<>0  "+tj+
					"GROUP BY EDU_ID,EDU_NAME ORDER BY TYPE_CODE ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPowerByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		
		String querySql="SELECT * FROM TL_CARD_USE_STU_MONTH T WHERE PAY_COUNT<>0 "+tj;

		String sqldept=SqlUtil.getDeptTeachGroup(deptTeach,querySql,false,"inner join");

		String sql="SELECT SUM(PAY_MONEY) ALL_MONEY,SUM(PAY_COUNT) ALL_COUNT, "+
					"ROUND(AVG(PAY_MONEY/PAY_COUNT),2) ONE_MONEY,ROUND(AVG(PAY_MONEY/PAY_DAYS),2) DAY_MONEY, "+
					"NEXT_DEPT_CODE TYPE_CODE,NEXT_DEPT_NAME TYPE_NAME "+
					"FROM ("+sqldept+") T "+
					"GROUP BY NEXT_DEPT_CODE,NEXT_DEPT_NAME ORDER BY TYPE_CODE ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	
	}

	@Override
	public List<Map<String, Object>> getPayType(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="select sum(t.pay_money) all_money,sum(t.pay_count) all_count,     "+
				"t.card_deal_id code,t.card_deal_name name from tl_card_pay_stu_month t "+
				"where 1=1  "+tj+"  "+
				"group by t.card_deal_id,t.card_deal_name order by code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPayTypeBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="select sum(t.pay_money) all_money,sum(t.pay_count) all_count,t.sex_code type_code,    "+
				"t.sex_name type_name,t.card_deal_id code,t.card_deal_name name from tl_card_pay_stu_month t     "+
				"where 1=1  "+tj+"  "+
				"group by t.card_deal_id,t.card_deal_name,t.sex_code, t.sex_name order by type_code,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPayTypeByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="select sum(t.pay_money) all_money,sum(t.pay_count) all_count,t.edu_id type_code, "+
				"t.edu_name type_name,t.card_deal_id code,t.card_deal_name name from tl_card_pay_stu_month t     "+
				"where 1=1  "+tj+"  "+
				"group by t.card_deal_id,t.card_deal_name,t.edu_id, t.edu_name order by type_code,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPayRegion(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="select sum(all_stu) all_stu,code,name from "+
				"(select sum(all_stu) all_stu, "+
				"case when  pay_money <5 then 5 "+
				"when pay_money >=5  and pay_money <10 then 10 "+
				"when pay_money >=10 and pay_money <20 then 20   "+
				"when pay_money >=20 and pay_money <30 then 30   "+
				"when pay_money >=30 and pay_money <50 then 50   "+
				"else 51 end code, "+
				"case when  pay_money <5 then '5元以下' "+
				"when pay_money >=5  and pay_money <10 then '5-10元' "+
				"when pay_money >=10 and pay_money <20 then '10-20元'    "+
				"when pay_money >=20 and pay_money <30 then '20-30元'   "+
				"when pay_money >=30 and pay_money <50 then '30-50元'   "+
				"else '50元以上'   end name "+
				"from (select count(distinct t.stu_id) all_stu, "+
				"AVG(t.pay_money/PAY_DAYS) pay_money "+
				"from TL_CARD_USE_STU_MONTH t  "+
				"where T.PAY_COUNT<>0 "+tj+" group by t.stu_id "+ 
				")  group by pay_money  "+
				") group by code,name order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPayRegionBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="select sum(all_stu) all_stu,code,name,type_code,type_name from "+
				"(select sum(all_stu) all_stu,type_code,type_name,      "+
				"case when  pay_money <5 then 5                         "+
				"when pay_money >=5  and pay_money <10 then 10          "+
				"when pay_money >=10 and pay_money <20 then 20          "+
				"when pay_money >=20 and pay_money <30 then 30          "+
				"when pay_money >=30 and pay_money <50 then 50          "+
				"else 51 end code,  "+
				"case when  pay_money <5 then '5元以下'                 "+
				"when pay_money >=5  and pay_money <10 then '5-10元'    "+
				"when pay_money >=10 and pay_money <20 then '10-20元'   "+
				"when pay_money >=20 and pay_money <30 then '20-30元'   "+
				"when pay_money >=30 and pay_money <50 then '30-50元'   "+
				"else '50元以上'   end name                             "+
				"from (select count(distinct t.stu_id) all_stu,         "+
				"AVG(t.pay_money/PAY_DAYS) pay_money, "+
				"t.sex_code type_code,t.sex_name type_name "+
				"from tl_card_use_stu_month t "+
				"where T.PAY_COUNT<>0 "+tj+" group by t.stu_id ,t.sex_code,t.sex_name "+
				")  group by pay_money ,type_code,type_name "+
				") group by type_code,type_name,code,name order by type_code,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPayRegionByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String sql="select sum(all_stu) all_stu,code,name,type_code,type_name from "+
				"(select sum(all_stu) all_stu,type_code,type_name,      "+
				"case when  pay_money <5 then 5                         "+
				"when pay_money >=5  and pay_money <10 then 10          "+
				"when pay_money >=10 and pay_money <20 then 20          "+
				"when pay_money >=20 and pay_money <30 then 30          "+
				"when pay_money >=30 and pay_money <50 then 50          "+
				"else 51 end code,  "+
				"case when  pay_money <5 then '5元以下'                 "+
				"when pay_money >=5  and pay_money <10 then '5-10元'    "+
				"when pay_money >=10 and pay_money <20 then '10-20元'   "+
				"when pay_money >=20 and pay_money <30 then '20-30元'   "+
				"when pay_money >=30 and pay_money <50 then '30-50元'   "+
				"else '50元以上'   end name                             "+
				"from (select count(distinct t.stu_id) all_stu,         "+
				"AVG(t.pay_money/PAY_DAYS) pay_money, "+
				"t.edu_id type_code,t.edu_name type_name "+
				"from tl_card_use_stu_month t "+
				"where T.PAY_COUNT<>0 "+tj+" group by t.stu_id ,t.edu_id,t.edu_name "+
				")  group by pay_money ,type_code,type_name "+
				") group by type_code,type_name,code,name order by type_code,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
}
