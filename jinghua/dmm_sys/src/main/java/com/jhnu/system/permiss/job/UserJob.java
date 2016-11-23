package com.jhnu.system.permiss.job;

import com.jhnu.framework.entity.JobResultBean;

public interface UserJob {
	
	/**
	 * 同步学生、教师 用户数据
	 * @return
	 */
	public JobResultBean SyncedUserJob();
	
	/**
	 * 冻结毕业学生账号
	 * @return
	 */
	public JobResultBean FreezeUserJob();
	
}
