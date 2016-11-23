package cn.gilight.personal.job.course.service;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

public interface CourseService {
	
	public JobResultBean initCourseWeekJob();
	
	public JobResultBean updateCurrYearWeekJob();
	
}
