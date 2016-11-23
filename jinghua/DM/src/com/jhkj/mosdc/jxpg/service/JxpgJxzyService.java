package com.jhkj.mosdc.jxpg.service;


/**
 * 教学评估-教学资源
 * @ClassName: JxpgJxzyService 
 * @author zhangyc
 * @date 2015年1月15日 下午3:32:18 
 *
 */
public interface JxpgJxzyService {
	//3.4校园网、图书情况
	public void saveInitTs();
	//各教学单位课程开设情况
	public void saveInitDwKsQk();
	//初始化学生数-新设专业概览 优势专业概览 专业情况概览
	public void saveInitXsNum();
	
	
}