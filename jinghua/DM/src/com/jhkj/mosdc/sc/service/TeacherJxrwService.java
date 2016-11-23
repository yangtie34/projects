package com.jhkj.mosdc.sc.service;

public interface TeacherJxrwService {

	/**
	 * 按照职称统计教学任务占比
	 * @param params
	 * @return
	 */
	public String queryZyjszwJxrw(String params);
	/**
	 * 按照编制类别教学任务占比
	 * @param params
	 * @return
	 */
	public String queryBzlbJxrw(String params);
	/**
	 * 院系编制类别教学任务对比
	 * @param params
	 * @return
	 */
	public String queryYxBzlbJxrw(String params);
	/**
	 * 院系专业技术职务教学任务对比
	 * @param params
	 * @return
	 */
	public String queryYxZyjszwJxrw(String params);
}
