package cn.gilight.product.equipment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.equipment.dao.EquipmentInfoDao;
import cn.gilight.product.equipment.dao.EquipmentSqlUtil;

@Repository("equipmentInfoDao")
public class EquipmentInfoDaoImpl implements EquipmentInfoDao {
	
	@Autowired
	private BaseDao baseDao;

	
	@Override
	public Map<String, Object> getCount() {
		String sql=EquipmentSqlUtil.getNumMoney()+EquipmentSqlUtil.getIsOT(false);
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public Map<String, Object> getValuableCount() {
		String sql=EquipmentSqlUtil.getNumMoney()+EquipmentSqlUtil.getEmType("val")+EquipmentSqlUtil.getIsOT(false);
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public Map<String, Object> getNowCount() {
		String sql=EquipmentSqlUtil.getNumMoney()+EquipmentSqlUtil.getEmType("now")+EquipmentSqlUtil.getIsOT(false);
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public Map<String, Object> getNowValuableCount() {
		String sql=EquipmentSqlUtil.getNumMoney()+EquipmentSqlUtil.getEmType("val")+EquipmentSqlUtil.getEmType("now")+EquipmentSqlUtil.getIsOT(false);
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getContrastByType(String type,
			String emType) {
		String sql="";
		if(type.equals("moneyCount")){
			sql="select nvl(name,'未维护') name,nvl(code,'') code,nvl(sum(count_),0) value from (select "+EquipmentSqlUtil.getMoneyCount("name")+" , "+EquipmentSqlUtil.getMoneyCount("code")+", "+
				"count_ from t_equipment t where 1=1 "+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(false)+" ) group by name,code  order by code ";
		}else{
			sql="select nvl(c.code_,'') code,nvl(c.name_,'未维护') name,nvl(sum(t.count_),0) value from t_equipment t "+EquipmentSqlUtil.getGroup(type)+" where 1=1 "+
					EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(false)+" group by c.code_,c.name_  order by code ";
		}
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getContrastTrendByType(String type,String emType) {
		String sql="";
		if(type.equals("moneyCount")){
			sql="select year_,nvl(name,'未维护') name,nvl(code,'') code,nvl(sum(count_),0) value from (select substr(buy_date,0,4) year_,count_,"+EquipmentSqlUtil.getMoneyCount("name")+" , "+EquipmentSqlUtil.getMoneyCount("code")+" "+
				"from t_equipment t where 1=1 "+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(false)+" ) group by year_,name,code order by year_, code ";
		}else{
			sql="select nvl(c.code_,'') code,nvl(c.name_,'未维护') name,nvl(sum(count_),0) value,substr(buy_date,0,4) year_ from t_equipment t "+EquipmentSqlUtil.getGroup(type)+" "+
				"where 1=1 "+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(false)+" group by c.code_,c.name_,substr(buy_date,0,4)  order by year_,code ";
		}
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getEquipmentTrend() {
		String sql="SELECT YEAR_,NUMS,UPNUMS,ROUND(MONEYS/10000,2) MONEYS,ROUND(UPMONEYS/10000,2) UPMONEYS FROM  TL_EQUIPMENT_YEAR order by year_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
