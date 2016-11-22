package com.jhnu.product.wechat.parent.warn.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.warn.dao.WechatWarnDao;
import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;
import com.jhnu.system.common.page.Page;

@Repository("wechatWarnDao")
public class WechatWarnDaoImpl implements WechatWarnDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public void saveWechatWarn(final List<WechatWarn> WARNS) {
		final int COUNT = WARNS.size();
		final String sql = "insert into t_wechat_warn(id,stu_id,warn_type_code,warn_level_code,warn_why,"
				+ "warn_text,warn_date,is_read,exe_time) values(ID_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";
        baseDao.getBaseDao().getJdbcTemplate().batchUpdate(sql,
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
			        	ps.setString(1, WARNS.get(i).getStu_id());
			            ps.setString(2, WARNS.get(i).getWarn_type_code());
			            ps.setString(3, WARNS.get(i).getWarn_level_code());
			            ps.setString(4, WARNS.get(i).getWarn_why());
			            ps.setString(5, WARNS.get(i).getWarn_text());
			            ps.setString(6, WARNS.get(i).getWarn_date());
			            ps.setInt(7, 0);
			            ps.setString(8, WARNS.get(i).getExe_time());
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 });
	}

	@Override
	public Page getWechatWarns(String stuId, Page page) {
		String sql="select * from t_wechat_warn where stu_id='"+stuId+"' order by warn_date desc";
		page = new Page(sql, page.getCurrentPage(), page.getNumPerPage(), baseDao.getBaseDao().getJdbcTemplate(), page.getTotalRows(),new WechatWarn());
		return page;
	}

	@Override
	public void readWechatWarns(List<WechatWarn> warns) {
		final int COUNT = warns.size();
		final List<WechatWarn> WARNS=warns;
		baseDao.getBaseDao().getJdbcTemplate().batchUpdate(
				"update t_wechat_warn set is_read=1 where id=? ",
				new BatchPreparedStatementSetter() {    
		           //为prepared statement设置参数。这个方法将在整个过程中被调用的次数    
		        public void setValues(PreparedStatement ps, int i) throws SQLException {    
		                ps.setLong(1, WARNS.get(i).getId());
		              }    
		              //返回更新的结果集条数  
		        public int getBatchSize() {
		        	return COUNT; 
		        }    
		 }); 
		
	}

	@Override
	public int countNoReadWarns(String stuId) {
		String sql="select count(*) from t_wechat_warn where stu_id = ? and is_read=0 ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql,new Object[]{stuId}, Integer.class);
	}

	@Override
	public String getLastTimeByTypeCode(String warnTypeCode) {
		String sql="select max(warn_date) from t_wechat_warn where warn_type_code=? ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForObject(sql, new Object[]{warnTypeCode},String.class);
	}
	
}
