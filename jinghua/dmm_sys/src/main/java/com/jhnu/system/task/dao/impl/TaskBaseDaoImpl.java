package com.jhnu.system.task.dao.impl;


import java.util.List;

import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.system.common.getSql.SqlByBean;
import com.jhnu.system.common.page.Page;
import com.jhnu.system.task.dao.TaskBaseDao;

@Repository("taskBaseDao")
public class TaskBaseDaoImpl implements TaskBaseDao{
	@Autowired
	protected  BaseDao baseDao;
	@Autowired
	protected  SqlByBean sqlByBean;
	
/*	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ScheduleJob> getScheduleJobs() {
        String sql = "select id as jobid,name_ as jobname,group_ as jobgroup,istrue,class_path as classpath,"
        		+ "cron_Expression as cronExpression from t_sys_job";
        List<ScheduleJob> jobList = baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(ScheduleJob.class));
        return jobList;
	}
*/
	@Override
  public String getAId(){
	   String id=baseDao.getBaseDao().getSeqGenerator().nextStringValue();
	return id;
  }
	@Override
	  public Page getObjs(int currentPage, int numPerPage,String sql,Object obj){
		return new Page(sql.toString(),currentPage, numPerPage ,baseDao.getBaseDao().getJdbcTemplate(),null,obj);
	  }
	@Override
	  public List getObjs(String sql,Object obj){
		List jobList =  baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(obj.getClass()));
		return jobList;
	  }
	@Override
	  public boolean excute(String sql){
		try{
		 baseDao.getBaseDao().getJdbcTemplate().execute(sql);
		}catch(DataAccessException e){
			e.printStackTrace();
			return false;
		}
		return true;
	  }
}
