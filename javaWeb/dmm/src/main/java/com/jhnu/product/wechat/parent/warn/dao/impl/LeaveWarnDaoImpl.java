package com.jhnu.product.wechat.parent.warn.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.enums.WarnLevelEnum;
import com.jhnu.enums.WarnTypeEnum;
import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.warn.dao.LeaveWarnDao;
import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;

@Repository("leaveWarnDao")
public class LeaveWarnDaoImpl implements LeaveWarnDao{

	@Autowired
	private BaseDao baseDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatWarn> getLeaveWarn(String exe_time, String last_time) {
		//TODO text要修改成模板，不用硬编码。
		String text = "  '你的孩子在 '|| leave.add_time || '请假离校，请假截止时间：'|| leave.end_time||',请假原因：'|| leave.leave_reason ";
		
		String sql = "select leave.stu_id,'"+WarnTypeEnum.QJYJ.getCode()+"' warn_type_code, '"+WarnLevelEnum.JG.getCode()+"' warn_level_code, co.name_ warn_why,"
            + text + "warn_text, leave.add_time warn_date, ? exe_time"
       		+ " from t_stu_leave leave "
       		+ " inner join t_code co on co.code_ = leave.leave_code and co.code_type = 'LEAVE_CODE'"
       		+ " where leave.add_time between ? and ?";
		
		return  baseDao.getBaseDao().getJdbcTemplate().
				query(sql,new Object[]{exe_time,last_time,exe_time}, 
						(RowMapper) new BeanPropertyRowMapper(WechatWarn.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatWarn> getCancelLeaveWarn(String exe_time, String last_time) {
		
		String text = "'你的孩子在 '|| leave.cancel_time || ' 返校销假' ";
		
		String sql = "select leave.stu_id,'"+WarnTypeEnum.QJYJ.getCode()+"' warn_type_code, '"+WarnLevelEnum.JG.getCode()+"' warn_level_code,co.name_ warn_why,"
				+ text +" warn_text, leave.cancel_time warn_date, ? exe_time"
						+ " from t_stu_leave leave"
						+ " inner join t_code co on co.code_ = leave.leave_code and co.code_type = 'LEAVE_CODE' "
						+ " where leave.cancel_time between ? and ?";
		
		return  baseDao.getBaseDao().getJdbcTemplate().
				query(sql,new Object[]{exe_time,last_time,exe_time}, 
						(RowMapper) new BeanPropertyRowMapper(WechatWarn.class));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatWarn> getKeepLeaveWarn(String exe_time) {
		String text = "'你的孩子已续假至 '|| leave.end_time ";
		
		String sql = "select leave.stu_id,'"+WarnTypeEnum.QJYJ.getCode()+"' warn_type_code, '"+WarnLevelEnum.JG.getCode()+"' warn_level_code,co.name_ warn_why,"
				+ text +" warn_text, leave.end_time warn_date, ? exe_time"
						+ " from t_stu_leave leave"
						+ " inner join t_code co on co.code_ = leave.leave_code and co.code_type = 'LEAVE_CODE' "
						+ " where leave.is_inform is null and leave.is_tokeep =1 ";
		
		return  baseDao.getBaseDao().getJdbcTemplate().
				query(sql,new Object[]{exe_time},(RowMapper) new BeanPropertyRowMapper(WechatWarn.class));
	}

	@Override
	public void informKeepLeave() {
		String sql="update t_stu_leave set is_inform=1 where leave.is_inform is null and leave.is_tokeep =1 ";
		baseDao.getBaseDao().getJdbcTemplate().update(sql);
	}
	

}
