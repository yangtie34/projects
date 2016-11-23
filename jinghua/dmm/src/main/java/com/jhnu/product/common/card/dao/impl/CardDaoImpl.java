package com.jhnu.product.common.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.common.card.dao.CardDao;
import com.jhnu.system.common.TreeCode;

@Repository("cardDao")
public class CardDaoImpl implements CardDao{
	@Autowired
	private BaseDao baseDao;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public TreeCode getTreeCode(String id) {
		String sql ="SELECT * FROM T_CODE_CARD_DEAL WHERE ID=?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(TreeCode.class));
	}
	
	@Override
	public List<Map<String, Object>> getCardDealGroup(String groupCode) {
		String sql = "select g.code_ group_code,g.treecode_id cdid,g.name_,deal.name_ pname,dea.name_ name_,dea.id from t_code_group g "
				+ "left join t_code_card_deal deal on deal.id = g.treecode_id "
				+ "left join t_code_card_deal dea on dea.path_ like deal.path_||'%' "
				+ "where g.code_=? and g.group_type='CARD_DEAL_GROUP'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, new Object[]{groupCode});
	}
	
	@Override
	public List<Map<String,Object>> getGroupCodes() {
		String sql = "select g.code_ value,g.name_ name from t_code_group g where g.group_type ='CARD_DEAL_GROUP' group by g.code_,g.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	} 
	
	@Override
	public List<Map<String, Object>> getCardBlanceByStudentId(String stuId) {
		String sql = "SELECT T.NO_,CARD_BALANCE SURPLUS_MONEY FROM T_CARD C,T_STU T "
				+ "WHERE T.NO_ = C.PEOPLE_ID AND T.NO_ = ? ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, new Object[]{stuId});
	}
}
