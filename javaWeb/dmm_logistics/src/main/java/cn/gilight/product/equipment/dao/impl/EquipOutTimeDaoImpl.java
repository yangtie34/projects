package cn.gilight.product.equipment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.product.equipment.dao.EquipOutTimeDao;
import cn.gilight.product.equipment.dao.EquipmentSqlUtil;

@Repository("equipOutTimeDao")
public class EquipOutTimeDaoImpl implements EquipOutTimeDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Map<String, Object> getCount() {
		String sql=EquipmentSqlUtil.getNumMoney()+EquipmentSqlUtil.getIsOT(true);
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public Map<String, Object> getValuableCount() {
		String sql=EquipmentSqlUtil.getNumMoney()+EquipmentSqlUtil.getIsOT(true)+EquipmentSqlUtil.getEmType("val");
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getContrastByType(String type,String emType) {
		String sql="";
		if(type.equals("moneyCount")){
			sql="select nvl(name,'未维护') name,code,nvl(sum(count_),0) value from (select "+EquipmentSqlUtil.getMoneyCount("name")+" , "
				+EquipmentSqlUtil.getMoneyCount("code")+",count_ from t_equipment t where 1=1 "
				+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(true)+" ) group by name,code  order by code ";
		}else{
			sql="select c.code_ code,nvl(c.name_,'未维护') name,nvl(sum(t.count_),0) value from t_equipment t "+EquipmentSqlUtil.getGroup(type)+" where 1=1 "+
		EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(true)+" group by c.code_,c.name_  order by code ";
		}
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getContrastTrendByType(String type,String emType) {
		String sql="";
		if(type.equals("moneyCount")){
			sql="select year,nvl(name,'未维护') name,code,nvl(sum(count_),0) value from (select substr(buy_date,0,4) year,count_,"+EquipmentSqlUtil.getMoneyCount("name")+" , "
					+EquipmentSqlUtil.getMoneyCount("code")+" from t_equipment t where 1=1 "
					+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(true)+" ) group by year,name,code  order by year, code ";
		}else{
			sql="select c.code_ code,nvl(c.name_,'未维护') name,nvl(sum(t.count_),0) value,substr(buy_date,0,4) year from t_equipment t "+EquipmentSqlUtil.getGroup(type)+" "+
				"where 1=1 "+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(true)+" group by c.code_,substr(buy_date,0,4),c.name_ order by year, code ";
		}
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCountByUseDept(String emType) {
		String sql="select c.code_ code,nvl(c.name_,'未维护') name,nvl(sum(t.count_),0) NUMS ,round(nvl(sum(t.price*t.count_)/10000,0),2) MONEYS from t_equipment t left join t_code_dept cd on t.DEPT_ID=cd.id "+
					"left join t_code c on cd.dept_category_code=c.code_ and c.code_type='DEPT_CATEGORY_CODE' where 1=1 "
					+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(true)+" group by c.code_,c.name_ order by c.code_ ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCountByUseDept(String deptId,String deptGroup,String emType) {
		String pid="", sql="";
		if(!"all".equals(deptId)){
			pid="and cd.pid='"+deptId+"' ";
		}
		
	//	if(DeptEnum.JX.getCode().equals(deptGroup)){//教学分开 去教学表查询
//			sql="select cd.id code,nvl(cd.name_,'未维护') name,nvl(sum(t.count_),0) NUMS ,round(nvl(sum(t.price*t.count_)/10000,0),2) MONEYS from t_equipment t "+
//					"left join t_code_dept_teach cd on t.DEPT_ID=cd.id where 1=1 "
//					+pid+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(true)+
//					"group by cd.id,cd.name_ order by code ";
//		}else {
			if(StringUtils.hasText(deptGroup)){
				deptGroup="cd.dept_category_code='"+deptGroup+"' ";
			}else{
				deptGroup="cd.dept_category_code is null ";
			}
			sql="select cd.id code,nvl(cd.name_,'未维护') name,nvl(sum(t.count_),0) NUMS ,round(nvl(sum(t.price*t.count_)/10000,0),2) MONEYS from t_equipment t "+
					"left join t_code_dept cd on t.DEPT_ID=cd.id and cd.level_>0  where 1=1 and "+deptGroup
					+pid+EquipmentSqlUtil.getEmType(emType)+EquipmentSqlUtil.getIsOT(true)+
					"group by cd.id,cd.name_ order by code ";
	//	}
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getWillOutTime(String emType) {
		String sql="select nvl(sum(count_),0) value,years year_,years code from ( "+
					"select count_,case when years <10 then years||'年' else '十年以上' end years from  "+
				/*	"(select (to_number(substr(t.buy_date,0,4))+t.usr_years) - "+
					"to_number(to_char(sysdate,'yyyy')) years,count_ from t_equipment t where  "+*/
					"(select (to_number(to_char(sysdate,'yyyy')) - "+
					"(to_number(substr(t.buy_date,0,4))+t.usr_years)) years,count_ from t_equipment t where  "+
					//"(to_number(substr(t.buy_date,0,4))+t.usr_years) > to_number(to_char(sysdate,'yyyy')) "+
					"(to_number(substr(t.buy_date,0,4))+t.usr_years) < to_number(to_char(sysdate,'yyyy')) "+
					EquipmentSqlUtil.getEmType(emType)+
					") )group by years order by years ";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
