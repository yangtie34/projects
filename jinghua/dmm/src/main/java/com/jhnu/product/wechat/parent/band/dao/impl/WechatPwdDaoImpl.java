package com.jhnu.product.wechat.parent.band.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.wechat.parent.band.dao.WechatPwdDao;
import com.jhnu.product.wechat.parent.band.entity.WechatPwd;

@Repository("wechatPwdDao")
public class WechatPwdDaoImpl implements WechatPwdDao{
	@Autowired
	private BaseDao baseDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public WechatPwd getOneById(String stuId) {
		String sql="select * from t_wechat_pwd where stu_id=?";
		List<WechatPwd> pwds=baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[] { stuId }, (RowMapper) new BeanPropertyRowMapper(WechatPwd.class));
        if(pwds.size()==0){
        	return null;
        }
		return pwds.get(0);
	}

	@Override
	public void changeWeChatPassword(WechatPwd pwd) {
		String sql="update t_wechat_pwd set pwd=?,phone_no=?,is_change=1 where stu_id=?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[] { pwd.getPwd(),pwd.getPhone_no(),pwd.getStu_id() });
	}

	@Override
	public int resetWeChatPassword(WechatPwd pwd) {
		String sql="update t_wechat_pwd set pwd=?,is_change=1 where phone_no=? and stu_id=?";
		return baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[] { pwd.getPwd(),pwd.getPhone_no(),pwd.getStu_id() });
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Student> getStusInSchoolNOdtInWechat() {
		String sql ="SELECT NO_,NAME_,IDNO FROM T_STU STU WHERE NOT EXISTS (SELECT 1 FROM T_WECHAT_PWD USER_ WHERE USER_.STU_ID = STU.NO_) AND STU.STU_STATE_CODE=1";
		List<Student> stus = (List<Student>) baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Student.class));
		return stus;
	}
	
	@Override
	public void addWechatPasswords(final List<WechatPwd> pwds) {
		final String sql = "insert into T_WECHAT_PWD values(?,?,?,?)";
    	baseDao.getBaseDao().getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				WechatPwd pwd = pwds.get(i);
				ps.setString(1, pwd.getStu_id());
				ps.setString(2, pwd.getPwd());
				ps.setString(3, pwd.getPhone_no());
				ps.setInt(4, pwd.getIs_change());
			}
			
			@Override
			public int getBatchSize() {
				return pwds.size();
			}
		});
	}
}
