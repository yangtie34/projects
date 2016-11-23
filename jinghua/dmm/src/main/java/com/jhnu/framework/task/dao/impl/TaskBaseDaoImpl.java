package com.jhnu.framework.task.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.framework.task.ScheduleJob;
import com.jhnu.framework.task.dao.TaskBaseDao;

@Repository
public class TaskBaseDaoImpl implements TaskBaseDao{
	@Autowired
	private BaseDao baseDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ScheduleJob> getScheduleJobs() {
        String sql = "select id as jobid,name_ as jobname,group_ as jobgroup,istrue,class_path as classpath,"
        		+ "cron_Expression as cronExpression from t_sys_job";
        List<ScheduleJob> jobList = baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(ScheduleJob.class));
        return jobList;
	}

	@Override
	public ScheduleJob getScheduleJob(ScheduleJob job) {
		//TODO 查询单个ScheduleJob没有做
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public ScheduleJob getScheduleJobById(Long id) {
        String sql = "select id as jobid,name_ as jobname,group_ as jobgroup,istrue,class_path as classpath,"
        		+ "cron_Expression as cronExpression from t_sys_job where id ="+id;
        List<ScheduleJob> jobList = baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper(ScheduleJob.class));
        if(jobList.size()==0){
        	return null;
        }
        return jobList.get(0);
	}

	@Override
	public void update(ScheduleJob job) {
        String sql = "update t_sys_job set name_=?, group_=?, istrue=?, class_path=? ,cron_Expression=? ,desc_=? where id=?";
        baseDao.getBaseDao().getJdbcTemplate().update(sql, job.getJobName(), job.getJobGroup(), job.getIsTrue(), 
        		job.getClassPath(), job.getCronExpression(),job.getDesc(),job.getJobId());
	}

}
