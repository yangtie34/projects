package com.jhnu.system.log.dao.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.log.dao.UserLoginLogDao;
import com.jhnu.system.log.entity.UserLoginLog;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.StringUtils;

@Repository("UserLoginLogDao")
public class UserLoginLogDaoImpl implements UserLoginLogDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public UserLoginLog addUserLoginLog(UserLoginLog UserLoginLog) {
        final String SQL = "insert into T_SYS_USER_LOGIN_LOG values(?,?,?,?,?,?)";
        String id=UUID.randomUUID().toString().replace("-", "0");
        baseDao.getBaseDao().getJdbcTemplate().update(SQL, new Object[]{id,UserLoginLog.getUsername(),DateUtils.SSS.format(new Date()),
        		UserLoginLog.getLogin_way_code(),UserLoginLog.getLogin_type_code(),UserLoginLog.getLogin_ip()});
        UserLoginLog.setId(id);
        return UserLoginLog;
    }

	@Override
	public Page getUserLoginLog(int currentPage, int numPerPage, int totalRow,
			String sort, boolean isAsc, UserLoginLog UserLoginLog) {
		StringBuffer sqlb= new StringBuffer("SELECT U.ID,U.USERNAME,U.LOGIN_TIME,U.LOGIN_WAY_CODE,U.LOGIN_TYPE_CODE,U.LOGIN_IP, "+
					"CC.NAME_ LOGIN_WAY_NAME,C.NAME_ LOGIN_TYPE_NAME FROM T_SYS_USER_LOGIN_LOG U "+
					"LEFT JOIN T_CODE C ON U.LOGIN_TYPE_CODE=C.CODE_ AND C.CODE_TYPE='LOGIN_TYPE_CODE' "+
					"LEFT JOIN T_CODE CC ON U.LOGIN_WAY_CODE=CC.CODE_ AND CC.CODE_TYPE='LOGIN_WAY_CODE' WHERE 1=1 ");
		if(UserLoginLog !=null ){
			if(StringUtils.hasLength(UserLoginLog.getId())){
				sqlb.append(" AND U.ID = '"+UserLoginLog.getId()+"' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getUsername())){
				sqlb.append(" AND U.USERNAME LIKE '%"+UserLoginLog.getUsername()+"%' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getLogin_way_code())){
				sqlb.append(" AND U.LOGIN_WAY_CODE LIKE '%"+UserLoginLog.getLogin_way_code()+"%' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getLogin_type_code())){
				sqlb.append(" AND U.LOGIN_TYPE_CODE = '"+UserLoginLog.getLogin_type_code()+"' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getLogin_time_start())){
				sqlb.append(" AND U.LOGIN_TIME >= '"+UserLoginLog.getLogin_time_start()+"' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getLogin_time_end())){
				sqlb.append(" AND U.LOGIN_TIME <= '"+UserLoginLog.getLogin_time_end()+"' ");
			}
		}
		return new Page(sqlb.toString(),currentPage, numPerPage ,baseDao.getBaseDao().getJdbcTemplate(),null,sort,isAsc);
	}

	@Override
	public int clearUserLoginLog(UserLoginLog UserLoginLog) {
		StringBuffer sqlb= new StringBuffer("DELETE FROM T_SYS_USER_LOGIN_LOG U WHERE 1=1 ");
		if(UserLoginLog !=null ){
			if(StringUtils.hasLength(UserLoginLog.getId())){
				sqlb.append(" AND U.ID = '"+UserLoginLog.getId()+"' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getUsername())){
				sqlb.append(" AND U.USERNAME LIKE '%"+UserLoginLog.getUsername()+"%' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getLogin_way_code())){
				sqlb.append(" AND U.LOGIN_WAY_CODE LIKE '%"+UserLoginLog.getLogin_way_code()+"%' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getLogin_type_code())){
				sqlb.append(" AND U.LOGIN_TYPE_CODE = '"+UserLoginLog.getLogin_type_code()+"' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getLogin_time_start())){
				sqlb.append(" AND U.LOGIN_TIME >= '"+UserLoginLog.getLogin_time_start()+"' ");
			}
			if(StringUtils.hasLength(UserLoginLog.getLogin_time_end())){
				sqlb.append(" AND U.LOGIN_TIME <= '"+UserLoginLog.getLogin_time_end()+"' ");
			}
		}
		return baseDao.getBaseDao().getJdbcTemplate().update(sqlb.toString());
	}
	
}
