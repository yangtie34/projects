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
				"t.card_deal_id code,t.card_deal_name name from tl_card_stu_deal t "+
				"where 1=1  "+tj+"  "+
				"group by t.card_deal_id,t.card_deal_name order by code";
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
	public List<Map<String, Object>> getPowerBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return getPowerByType(startDate, endDate, deptTeach, "sex");
	}

	@Override
	public List<Map<String, Object>> getPayTypeBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return getPayTypeByType(startDate, endDate, deptTeach, "sex");
	}

	@Override
	public List<Map<String, Object>> getPayRegionBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return getPayRegionByType(startDate, endDate, deptTeach, "sex");
	}

	@Override
	public List<Map<String, Object>> getPowerByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return getPowerByType(startDate, endDate, deptTeach, "edu");
	}

	@Override
	public List<Map<String, Object>> getPayRegionByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return getPayRegionByType(startDate, endDate, deptTeach, "edu");
	}

	@Override
	public List<Map<String, Object>> getPayTypeByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return getPayTypeByType(startDate, endDate, deptTeach, "edu");
	}

	@Override
	public List<Map<String, Object>> getPowerByArea(String startDate,
			String endDate, Map<String, String> deptTeach) {
		return getPowerByType(startDate, endDate, deptTeach, "area");
	}


	@Override
	public List<Map<String, Object>> getPayTypeByArea(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return getPayTypeByType(startDate, endDate, deptTeach, "area");
	}


	@Override
	public List<Map<String, Object>> getPayRegionByArea(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		return getPayRegionByType(startDate, endDate, deptTeach, "area");
	}
	
	@Override
	public List<Map<String, Object>> getPowerByMZ(String startDate, String endDate, Map<String, String> deptTeach) {
		return getPowerByType(startDate, endDate, deptTeach, "mz");
	}

	@Override
	public List<Map<String, Object>> getPayTypeByMZ(String startDate, String endDate, Map<String, String> deptTeach) {
		return getPayTypeByType(startDate, endDate, deptTeach, "mz");
	}

	@Override
	public List<Map<String, Object>> getPayRegionByMZ(String startDate, String endDate,
			Map<String, String> deptTeach) {
		return getPayRegionByType(startDate, endDate, deptTeach, "mz");
	}
	
	
	private List<Map<String, Object>> getPowerByType(String startDate, String endDate, Map<String, String> deptTeach,String type) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String code[]=getCardTypeBylb(type);
		String sql="SELECT SUM(PAY_MONEY) ALL_MONEY,SUM(PAY_COUNT) ALL_COUNT, "+
					"ROUND(AVG(PAY_MONEY/PAY_COUNT),2) ONE_MONEY,ROUND(AVG(PAY_MONEY/PAY_DAYS),2) DAY_MONEY, "+
					code[0]+
					"FROM TL_CARD_USE_STU_MONTH T WHERE PAY_COUNT<>0  "+tj+
					"GROUP BY "+code[1]+" ORDER BY TYPE_CODE ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	private List<Map<String, Object>> getPayTypeByType(String startDate, String endDate, Map<String, String> deptTeach,String type) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String code[]=getCardTypeBylb(type);
		String sql="select sum(t.pay_money) all_money,sum(t.pay_count) all_count, "+code[0]+
				",t.card_deal_id code,t.card_deal_name name from tl_card_stu_deal t     "+
				"where 1=1  "+tj+"  "+
				"group by t.card_deal_id,t.card_deal_name,"+code[1]+" order by type_code,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	private List<Map<String, Object>> getPayRegionByType(String startDate, String endDate,
			Map<String, String> deptTeach,String type) {
		String tj=CardTjUtil.getDateTJ(startDate, endDate)+
				SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_PP.getCode(),"t");
		String code[]=getCardTypeBylb(type);
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
				"AVG(t.pay_money/PAY_DAYS) pay_money, "+code[0]+
				"from tl_card_use_stu_month t "+
				"where T.PAY_COUNT<>0 "+tj+" group by t.stu_id ,"+code[1]+
				")  group by pay_money ,type_code,type_name "+
				") group by type_code,type_name,code,name order by type_code,code";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
	public static String[] getCardTypeBylb(String lb){
		if(lb.equalsIgnoreCase("sex")){
			return new String[]{" nvl(t.sex_code,'null') type_code,nvl(t.sex_name,'未维护') type_name "," t.sex_code,t.sex_name "};
		}else if(lb.equalsIgnoreCase("edu")){
			return new String[]{" nvl(t.edu_id,'null') type_code,nvl(t.edu_name,'未维护') type_name "," t.edu_id,t.edu_name "};
		}else if(lb.equalsIgnoreCase("mz")){
			return new String[]{" nvl(t.nation_code,'null') type_code,nvl(t.nation_name,'未维护') type_name "," t.nation_code,t.nation_name "};
		}else if(lb.equalsIgnoreCase("area")){
			return new String[]{" nvl(t.province_id,'null') type_code,nvl(t.province_name,'未维护') type_name "," t.province_id,t.province_name "};
		}
		return null;
	}


	
	
}
