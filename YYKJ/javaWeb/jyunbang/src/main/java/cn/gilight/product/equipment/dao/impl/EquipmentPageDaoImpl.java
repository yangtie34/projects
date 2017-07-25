package cn.gilight.product.equipment.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.syspermiss.util.StringUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.DeptEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.product.equipment.dao.EquipmentPageDao;
import cn.gilight.product.equipment.dao.EquipmentSqlUtil;

@Repository("equipmentPageDao")
public class EquipmentPageDaoImpl implements EquipmentPageDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public Page getEmDetil(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType) {
		String sql=EquipmentSqlUtil.getEmDetil()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getEmDetilByType(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType, String type,String typeValue) {
		String sql=EquipmentSqlUtil.getEmDetil()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		switch (type) {
		case "emType":
			if(StringUtils.hasText(typeValue)){
				sql+="and ec.code_='"+typeValue+"' ";
			}else{
				sql+="and ec.code_ is null ";
			}
			break;
		case "moneyFrom":
			if(StringUtils.hasText(typeValue)){
				sql+="and fsc.code_='"+typeValue+"' ";
			}else{
				sql+="and fsc.code_ is null ";
			}
			break;
		case "moneyCount":
			sql+=" and "+EquipmentSqlUtil.getMoneyCountTj(typeValue);
			break;
		default:
			break;
		}
		
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getEmDetilByDeptGroup(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType,String deptGroup) {
		String sql=EquipmentSqlUtil.getEmDetil()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				sql+="and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				sql+="and cd.dept_category_code is null ";
			}
		}
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getEmDetilByDeptId(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType,String deptGroup, String deptId) {
		String sql=EquipmentSqlUtil.getEmDetil()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				sql+="and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				sql+="and cd.dept_category_code is null ";
			}
		}
		if(StringUtils.hasText(deptId)){
			sql+="and cd.id ='"+deptId+"' ";
		}else{
			sql+="and cd.id is null ";
		}
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getEmDetilByManagerId(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType,String managerId) {
		String sql=EquipmentSqlUtil.getEmDetil()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		if(StringUtils.hasText(managerId)){
			sql+="and t.manager ='"+managerId+"' ";
		}else{
			sql+="and t.manager is null ";
		}
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getManager(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType,String deptGroup) {
		String sql=EquipmentSqlUtil.getManager()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				sql+="and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				sql+="and cd.dept_category_code is null ";
			}
		}
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}
	
	@Override
	public Page getManagerByType(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType, String deptGroup,String type,String typeValue) {
		String sql=EquipmentSqlUtil.getManager()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		switch (type) {
		case "edu":
			if(StringUtils.hasText(typeValue)){
				sql+="and edu.code_='"+typeValue+"' ";
			}else{
				sql+="and edu.code_ is null ";
			}
			break;
		case "zc":
			if(StringUtils.hasText(typeValue)){
				sql+="and zc.pid='"+typeValue+"' ";
			}else{
				sql+="and zc.pid is null ";
			}
			break;
		default:
			break;
		}
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				sql+="and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				sql+="and cd.dept_category_code is null ";
			}
		}
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getManagerByDeptGroup(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType,String deptGroup) {
		String sql=EquipmentSqlUtil.getManager()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				sql+="and cd.id='"+deptGroup+"' ";
			}else{
				sql+="and cd.id is null ";
			}
		}
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getManagerByDeptId(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isOT, String emType,String deptGroup , String deptId) {
		String sql=EquipmentSqlUtil.getManager()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				sql+="and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				sql+="and cd.dept_category_code is null ";
			}
		}
		if(StringUtils.hasText(deptId)){
			sql+="and cd.id ='"+deptId+"' ";
		}else{
			sql+="and cd.id is null ";
		}
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getEmDetilByYear(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc, boolean isOT,
			String emType, boolean isUp, int year) {
		String sql=EquipmentSqlUtil.getEmDetil()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		String tj="";
		if(isUp){
			tj=" and substr(t.buy_date,0,4)= '"+year+"' "; 
		}else{
			tj=" and substr(t.buy_date,0,4)<= '"+year+"' ";
		}
		sql+=tj;
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}

	@Override
	public Page getEmDetilByLastYear(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			boolean isOT, String emType, int year) {
		String sql=EquipmentSqlUtil.getEmDetil()+EquipmentSqlUtil.getIsOT(isOT)+EquipmentSqlUtil.getEmType(emType);
		String tj="=";
		if(year>=10){
			tj=">=";
		}
		sql+=" and (to_number(to_char(sysdate,'yyyy')) - (to_number(substr(t.buy_date,0,4))+t.usr_years))  "+tj+" '"+year+"' ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}
	
}
