package cn.gilight.product.equipment.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.DeptEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.product.equipment.dao.EquipManagerDao;
import cn.gilight.product.equipment.dao.EquipmentSqlUtil;

@Repository("equipManagerDao")
public class EquipManagerDaoImpl implements EquipManagerDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public int getManagers() {
		String sql="select nvl(count(count(*)),0)  from t_equipment t where t.manager is not null "+EquipmentSqlUtil.getIsOT(false)+"group by t.manager";
		return baseDao.getJdbcTemplate().queryForObject(sql, Integer.class);
	}

	@Override
	public List<Map<String, Object>> getManagersByDept() {
		String sql="select cd.dept_category_code code,nvl(c.name_,'未维护') name,nvl(count(distinct t.manager), 0) value from t_equipment t "
				+"left join t_tea tea on t.manager=tea.tea_no "
				+ "left join t_code_dept cd on tea.DEPT_ID=cd.id "+
				"left join t_code c on cd.dept_category_code=c.code_ and "+
				"c.code_type='DEPT_CATEGORY_CODE' where t.manager is not null "+
				EquipmentSqlUtil.getIsOT(false)+"group by cd.dept_category_code,c.name_  order by code ";
	return baseDao.getJdbcTemplate().queryForList(sql);
}

	@Override
	public List<Map<String, Object>> getContrastByEdu(String deptGroup) {
		String group="";
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				group=" and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				group=" and cd.dept_category_code is null ";
			}
		}
		
		String sql="select nvl(c.id,'') code,nvl(c.name_,'未维护') name,nvl(count(distinct t.manager), 0) value  from "+
					"t_equipment t left join t_tea te on t.manager=te.tea_no "+
					"left join t_code_dept cd on cd.id=te.DEPT_ID "+
					"left join t_code_education c on te.edu_id=c.id "+
					"where t.manager is not null  "+group+EquipmentSqlUtil.getIsOT(false)+
					"group by c.id,c.name_ order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getContrastTrendByEdu(String deptGroup) {
		String group="";
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				group=" and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				group=" and cd.dept_category_code is null ";
			}
		}
		String sql="select nvl(c.id,'') code,nvl(c.name_,'未维护') name,nvl(count(distinct t.manager), 0) value,substr(t.buy_date,0,4) year_ from "+
					"t_equipment t left join t_tea te on t.manager=te.tea_no "+
					"left join t_code_dept cd on cd.id=te.DEPT_ID "+
					"left join t_code_education c on te.edu_id=c.id "+
					"where t.manager is not null  "+group+EquipmentSqlUtil.getIsOT(false)+
					"group by c.id,c.name_,substr(t.buy_date,0,4) order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
	@Override
	public List<Map<String, Object>> getContrastByZC(String deptGroup) {
		String group="";
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				group=" and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				group=" and cd.dept_category_code is null ";
			}
		}
		String sql="select nvl(c.id,'') code,nvl(c.name_,'未维护') name,nvl(count(distinct t.manager), 0) value from "+
					"t_equipment t left join t_tea te on t.manager=te.tea_no "+
					"left join t_code_dept cd on cd.id=te.DEPT_ID "+
					"left join t_code_zyjszw cc on  te.zyjszw_id=cc.id "+
					"left join t_code_zyjszw c on  cc.pid=c.id "+
					"where t.manager is not null  "+group+EquipmentSqlUtil.getIsOT(false)+
					"group by c.id,c.name_ order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getContrastTrendByZC(String deptGroup) {
		String group="";
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				group=" and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				group=" and cd.dept_category_code is null ";
			}
		}
		String sql="select nvl(c.id,'') code,nvl(c.name_,'未维护') name,nvl(count(distinct t.manager), 0) value,substr(t.buy_date,0,4) year_ from "+
					"t_equipment t left join t_tea te on t.manager=te.tea_no "+
					"left join t_code_dept cd on cd.id=te.DEPT_ID "+
					"left join t_code_zyjszw cc on  te.zyjszw_id=cc.id "+
					"left join t_code_zyjszw c on  cc.pid=c.id "+
					"where t.manager is not null  "+group+EquipmentSqlUtil.getIsOT(false)+
					"group by c.id,c.name_,substr(t.buy_date,0,4) order by code ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCountByUseDept(String deptId,String deptGroup) {
		String pid="", sql="",group="",on=" tea.DEPT_ID=cd.id ",leftStu="";
		leftStu=" left join (select dept_id,count(*) stuNum from t_stu where STU_STATE_CODE=1 group by dept_id) s on s.dept_id = cd.id ";
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(!"all".equals(deptId)){
				pid=" and cd.pid='"+deptId+"' ";
				on=" tea.DEPT_ID=cd.pid ";
			}
			if(StringUtils.hasText(deptGroup)){
				group=" and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				group=" and cd.dept_category_code is null ";
			}
		}
	//	if(DeptEnum.JX.getCode().equals(deptGroup)){//教学分开 去教学表查询
//			sql="select cd.id code,nvl(cd.name_,'未维护') name,nvl(sum(t.count_),0) NUMS,round(nvl(sum(t.price*t.count_)/10000,0),2) moneys, "+
//					"case when stuNum is null then 0 else round(nvl(sum(t.price * t.count_), 0)/stuNum, 2) end AVGSTU  from t_equipment t "
//					+ "left join t_tea tea on t.manager=tea.tea_no "+
//					"inner join t_code_dept_teach cd on "+on+leftStu+" where t.manager is not null "
//					+pid+EquipmentSqlUtil.getIsOT(false)+
//					"group by cd.id,cd.name_ ,s.stuNum order by cd.id";
	//	}else {
			sql="select cd.id code,nvl(cd.name_,'未维护') name,nvl(sum(t.count_),0) NUMS ,round(nvl(sum(t.price*t.count_)/10000,0),2) moneys, "+
					"case when stuNum is null then 0 else round(nvl(sum(t.price * t.count_), 0)/stuNum, 2) end AVGSTU  from t_equipment t left join t_tea tea on t.manager=tea.tea_no "+
					"left join t_code_dept cd on "+on+leftStu+" where t.manager is not null "+group+pid+EquipmentSqlUtil.getIsOT(false)+
					"group by cd.id,cd.name_,s.stuNum order by cd.id";
	//	}
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getManagersByDept(String deptId,
			String deptGroup) {
		String pid="", sql="",group="",on=" tea.DEPT_ID=cd.id ";
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(!"all".equals(deptId)){
				pid=" and cd.pid='"+deptId+"' ";
				on=" tea.DEPT_ID=cd.pid ";
			}
			if(StringUtils.hasText(deptGroup)){
				group=" and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				group=" and cd.dept_category_code is null ";
			}
		}
	//	if(DeptEnum.JX.getCode().equals(deptGroup)){//教学分开 去教学表查询
//			sql="select cd.id code,nvl(cd.name_,'未维护') name,nvl(count(distinct t.manager), 0) USERS  from t_equipment t "
//					+ "left join t_tea tea on t.manager = tea.tea_no "+
//					"inner join t_code_dept_teach cd on "+on+" where t.manager is not null "
//					+pid+EquipmentSqlUtil.getIsOT(false)+
//					"group by cd.id,cd.name_ order by cd.id";
	//	}else {
			sql="select cd.id code,nvl(cd.name_,'未维护') name,nvl(count(distinct t.manager),0) USERS from t_equipment t "
					+ "left join t_tea tea on t.manager = tea.tea_no "+
					"left join t_code_dept cd on "+on+" where t.manager is not null "+group+pid+EquipmentSqlUtil.getIsOT(false)+
					"group by cd.id,cd.name_ order by cd.id";
	//	}
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page getEmTop(int currentPage,int numPerPage,int totalRow,String rankType,String queryType, String deptGroup,int rank) {
		
		String rankTypeTj="",show="tea.DEPT_ID dept_id,nvl(cd.name_,'未维护') dept_name,",
				queryTypeTj="",where="",
				on="";
		
		if(!DeptEnum.ALL.getCode().equals(deptGroup)){
			if(StringUtils.hasText(deptGroup)){
				where=" and cd.dept_category_code='"+deptGroup+"' ";
			}else{
				where=" and cd.dept_category_code is null ";
			}
		}
		switch (rankType) {
		case "all":
			rankTypeTj="a.nums";
			break;
		case "val":
			rankTypeTj="b.valnum";
			break;
		case "money":
			rankTypeTj="a.moneys";
			break;
		default:
			break;
		}
		if("manager".equals(queryType)){
			queryTypeTj="t.manager ,tea.name_ ,tea.DEPT_ID,cd.name_ ";
			show+="t.manager tea_id,tea.name_ tea_name ,";
			on="a.tea_id = b.tea_id";
		}else{
			queryTypeTj=" tea.DEPT_ID,cd.name_ ";
			on="a.dept_id = b.dept_id";
		}
		String sql1="select "+show+
				"round(nvl(sum(t.price*t.count_)/10000,0),2) moneys, nvl(sum(t.count_),0) nums "+
				"from t_equipment t "+
				"left join t_tea tea on t.manager=tea.tea_no "+
				"left join t_code_dept cd on tea.DEPT_ID=cd.id "+
				"where t.manager is not null "+where+
				"group by "+queryTypeTj;
		String sql2="select "+show+
				"round(nvl(sum(t.price*t.count_)/10000,0),2) valmoney, nvl(sum(t.count_),0) valnum "+
				"from t_equipment t "+
				"left join t_tea tea on t.manager=tea.tea_no "+
				"left join t_code_dept cd on tea.DEPT_ID=cd.id "+
				"where t.manager is not null "+where+EquipmentSqlUtil.getEmType("val")+
				"group by "+queryTypeTj;
		String sql="select * from (select dense_rank() over(order by nvl("+rankTypeTj+",0) desc) rank_,a.*, "+
					"nvl(b.valnum,0) valnum from ("+sql1+") a left join ("+sql2+") b on "+on+" ) where rank_<=10 ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
}
