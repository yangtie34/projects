package com.jhnu.framework.task.dao;

import java.util.List;

import com.jhnu.framework.task.ScheduleJob;

public interface TaskBaseDao {
	
	public List<ScheduleJob> getScheduleJobs();
	
	public ScheduleJob getScheduleJob(ScheduleJob job);
	
	public ScheduleJob getScheduleJobById(Long id);
	
	public void update(ScheduleJob job);
	
}
