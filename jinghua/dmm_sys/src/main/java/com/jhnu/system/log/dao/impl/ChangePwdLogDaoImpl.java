package com.jhnu.system.log.dao.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.log.dao.ChangePwdLogDao;
import com.jhnu.system.log.entity.ChangePwdLog;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.StringUtils;

@Repository("changePwdLogDao")
public class ChangePwdLogDaoImpl implements ChangePwdLogDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public ChangePwdLog addChangePwdLog(ChangePwdLog changePwdLog) {
        final String SQL = "insert into T_SYS_USER_CHANGE_PWD_LOG values(?,?,?,?,?,?)";
        String id=UUID.randomUUID().toString().replace("-", "0");
        baseDao.getBaseDao().getJdbcTemplate().update(SQL, new Object[]{id,changePwdLog.getUsername(),DateUtils.SSS.format(new Date()),
        		changePwdLog.getExc_username(),changePwdLog.getExc_ip(),changePwdLog.getExc_type_code()});
        changePwdLog.setId(id);
        return changePwdLog;
    }

	@Override
	public Page getChangePwdLog(int currentPage, int numPerPage, int totalRow,
			String sort, boolean isAsc, ChangePwdLog changePwdLog) {
		StringBuffer sqlb= new StringBuffer("SELECT U.ID,U.USERNAME,U.EXC_TIME,U.EXC_USERNAME,U.EXC_IP,U.EXC_TYPE_CODE, "+
					"C.NAME_ EXC_TYPE_NAME FROM T_SYS_USER_CHANGE_PWD_LOG U "+
					"LEFT JOIN T_CODE C ON U.EXC_TYPE_CODE=C.CODE_ AND C.CODE_TYPE='EXC_TYPE_CODE' WHERE 1=1 ");
		if(changePwdLog !=null ){
			if(StringUtils.hasLength(changePwdLog.getId())){
				sqlb.append(" AND U.ID = '"+changePwdLog.getId()+"' ");
			}
			if(StringUtils.hasLength(changePwdLog.getUsername())){
				sqlb.append(" AND U.USERNAME LIKE '%"+changePwdLog.getUsername()+"%' ");
			}
			if(StringUtils.hasLength(changePwdLog.getExc_username())){
				sqlb.append(" AND U.EXC_USERNAME LIKE '%"+changePwdLog.getExc_username()+"%' ");
			}
			if(StringUtils.hasLength(changePwdLog.getExc_type_code())){
				sqlb.append(" AND U.EXC_TYPE_CODE = '"+changePwdLog.getExc_type_code()+"' ");
			}
			if(StringUtils.hasLength(changePwdLog.getExc_time_start())){
				sqlb.append(" AND U.EXC_TIME >= '"+changePwdLog.getExc_time_start()+"' ");
			}
			if(StringUtils.hasLength(changePwdLog.getExc_time_end())){
				sqlb.append(" AND U.EXC_TIME <= '"+changePwdLog.getExc_time_end()+"' ");
			}
		}
		return new Page(sqlb.toString(),currentPage, numPerPage ,baseDao.getBaseDao().getJdbcTemplate(),null,sort,isAsc);
	}

	@Override
	public int clearChangePwdLog(ChangePwdLog changePwdLog) {
		StringBuffer sqlb= new StringBuffer("DELETE FROM T_SYS_USER_CHANGE_PWD_LOG U WHERE 1=1 ");
		if(changePwdLog !=null ){
			if(StringUtils.hasLength(changePwdLog.getId())){
				sqlb.append(" AND U.ID = '"+changePwdLog.getId()+"' ");
			}
			if(StringUtils.hasLength(changePwdLog.getUsername())){
				sqlb.append(" AND U.USERNAME LIKE '%"+changePwdLog.getUsername()+"%' ");
			}
			if(StringUtils.hasLength(changePwdLog.getExc_username())){
				sqlb.append(" AND U.EXC_USERNAME LIKE '%"+changePwdLog.getExc_username()+"%' ");
			}
			if(StringUtils.hasLength(changePwdLog.getExc_type_code())){
				sqlb.append(" AND U.EXC_TYPE_CODE = '"+changePwdLog.getExc_type_code()+"' ");
			}
			if(StringUtils.hasLength(changePwdLog.getExc_time_start())){
				sqlb.append(" AND U.EXC_TIME >= '"+changePwdLog.getExc_time_start()+"' ");
			}
			if(StringUtils.hasLength(changePwdLog.getExc_time_end())){
				sqlb.append(" AND U.EXC_TIME <= '"+changePwdLog.getExc_time_end()+"' ");
			}
		}
		return baseDao.getBaseDao().getJdbcTemplate().update(sqlb.toString());
	}
	
}
