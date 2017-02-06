package cn.gilight.product.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.card.dao.CardTrendDao;

@Repository("cardTrendDao")
public class CardTrendDaoImpl implements CardTrendDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> getRechargeTrend(Map<String, String> deptTeach) {
		String sql="select to_char(t.year_month,'yyyy-mm') year_month,nvl(sum(t.all_money),0) all_money,nvl(sum(t.all_count),0) all_count, "
				+"nvl(sum(t.use_people),0) use_people,round(nvl(sum(t.all_money),0)/nvl(sum(t.use_people),1),2) people_money "
				+"from TL_CARD_TREND t where t.flag='recharge' "+SqlUtil.getDeptTeachTj(deptTeach,ShiroTagEnum.CARD_RE.getCode(),"t")
				+" group by t.year_month order by t.year_month ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCardUsedTrend(
			Map<String, String> deptTeach, String type_code, String flag) {
		String sql="select to_char(t.year_month,'yyyy-mm') year_month,nvl(sum(t.use_people ),0) use_people ,nvl(sum(t.all_people),0) all_people, "
				+"round(nvl(sum(t.use_people),0)/nvl(sum(t.all_people),1),2) use_rate "
				+"from TL_CARD_TREND t where t.flag='"+flag+"' and t.type_code='"+type_code+"' "
				+SqlUtil.getDeptTeachTj(deptTeach,ShiroTagEnum.CARD_HPT.getCode(),"t")
				+"group by t.year_month order by t.year_month  ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPayTypeTrend(
			Map<String, String> deptTeach, String type_code) {
		if("all".equals(type_code)){
			type_code=" in ("+CardTjUtil.ccx+") ";
		}else{
			type_code=" = '"+type_code+"' ";
		}
		
		String sql="select nvl(a.all_money,0) all_money,nvl(a.all_count,0) all_count, "+
				"round(nvl(a.all_count,0)/nvl(b.all_count,1)*100,2) count_rate, "+
				"round(nvl(a.all_money,0)/nvl(b.all_money,1)*100,2) money_rate,to_char(b.year_month,'yyyy-mm') year_month from "+
				"(select sum(all_count) all_count,sum(all_money) all_money,year_month from TL_CARD_TREND t where  "+
				"flag='pay_type'  and type_code "+type_code+
				SqlUtil.getDeptTeachTj(deptTeach,ShiroTagEnum.CARD_PP.getCode(),"t")+" group by year_month )b "+
				"left join  "+
				"(select sum(all_count) all_count,sum(all_money) all_money,year_month from TL_CARD_TREND t where  "+
				"flag='pay_type'  and type_code "+type_code+
				SqlUtil.getDeptTeachTj(deptTeach,ShiroTagEnum.CARD_PP.getCode(),"t")+" group by year_month) a "+
				"on a.year_month=b.year_month order by year_month ";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getStuPayTrend(
			Map<String, String> deptTeach, String type_code, String flag) {
		String sql=null;
		if(flag.equalsIgnoreCase("pay_area")){
			sql="";
			//TODO 区域趋势没有
		}else{
			 sql="select to_char(t.year_month,'yyyy-mm') year_month,nvl(sum(t.all_count ),0) all_count ,nvl(sum(t.all_money),0) all_money, "
						+"round(nvl(sum(t.all_money),0)/nvl(sum(t.all_count),1),2) one_money, "
						+"round(nvl(sum(t.all_money),0)/(add_months(trunc(t.year_month,'mm'),1) - trunc(t.year_month,'mm'))/nvl(sum(t.use_people), 1),2) day_money "
						+"from TL_CARD_TREND t where t.flag='"+flag+"' and t.type_code='"+type_code+"' "
						+SqlUtil.getDeptTeachTj(deptTeach,ShiroTagEnum.CARD_HPT.getCode(),"t")
						+"group by t.year_month  order by t.year_month  ";	
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getEatTrend(String type_code, String flag,
			String queryType) {
		String sql="select to_char(t.year_month,'yyyy-mm') year_month,nvl(sum(t.all_count ),0) all_count ,nvl(sum(t.all_money),0) all_money, "
				+"round(nvl(sum(t.all_money),0)/nvl(sum(t.all_count),1),2) one_money, "
				+"round(nvl(sum(t.all_money),0)/(add_months(trunc(t.year_month,'mm'),1) - trunc(t.year_month,'mm')),2) day_money, "
				+"round(nvl(sum(t.all_count),0)/(add_months(trunc(t.year_month,'mm'),1) - trunc(t.year_month,'mm')),2) day_count "
				+"from TL_CARD_TREND t where t.flag='"+flag+"' and t.type_code='"+type_code+"' "
				+CardTjUtil.getHourTj(queryType)
				+"group by t.year_month  order by t.year_month ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getPayYearTrend(
			Map<String, String> deptTeach) {
		String sql="select to_char(t.year_month,'yyyy' ) year,sum(t.all_money) all_money,sum(t.use_people) use_people, "
				+ "round(sum(t.all_money)/sum(t.use_people),2) year_money from TL_CARD_TREND t "
				+ "where t.flag='pay_year' "
				+SqlUtil.getDeptTeachTj(deptTeach,ShiroTagEnum.CARD_PP.getCode(),"t")
				+ " group by to_char(t.year_month,'yyyy' ) order by year ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
