package com.jhkj.mosdc.sc.service;

import java.util.List;

import com.jhkj.mosdc.permiss.domain.TeachingOrganizationalStructureNode;
import com.jhkj.mosdc.sc.domain.ChartModel;

/**
 * 基本校情统计的基础service
 * @author Administrator
 *
 */
public interface SchoolBasicSituationService {
	
	/**
	 * 所有数据请求转发类
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String queryChart(String params) throws Exception;
	/**
	 * 查询学校教学组织结构
	 */
	public TeachingOrganizationalStructureNode queryJxzzjg() throws Exception;
	
	/**
	 * 查询学校行政组织结构
	 */
	public TeachingOrganizationalStructureNode queryXzzzjg() throws Exception;

	/*******************************宿舍概况*****************************/
	/**
	 * 查询宿舍概况
	 * @return
	 */
	public String queryDormBasic();
	/**
	 * 查询宿舍入住概况
	 */
	public List queryRzqk();
	
	/*****************************课程概况**************************/
	/**
	 * 查询课程概况
	 * @return
	 */
	public String queryKcBasic();
	/**
	 * 查询课程分类概况，按模块，课程个数
	 */
	public List queryKcfl();
	/**
	 * 查询各个系部能够承担教学任务的课程概况
	 */
	public List queryXbkc();
	
	/***************************学生基本信息统计*******************************/
	
	/**
	 * 查询学生基本概况
	 * @return
	 */
	public List<ChartModel> queryStudentBasic();
	/**
	 * 分年龄段统计学生
	 */
	public List queryStudentByAgeRange();
	/***
	 * 分民族统计学生
	 */
	public List queryStudentByMz();
	/**
	 * 分入学层次统计学生
	 */
	public List queryStudentByRxcc();
	/**
	 * 按户籍所在地(地区)统计学生
	 */
	public List queryStudentByDq();
	/**
	 * 本学期学生流失统计
	 */
	public List queryStudentWithLs();
	
	/**************************教职工基本信息统计******************************/
	
	/**
	 * 查询教职工基本概况(分专、外聘、预备)
	 */
	public List queryTeacherBasic();
	/**
	 * 查询性别统计教职工
	 */
	public List queryTeacherBYXb();
	/**
	 * 分年龄段统计教职工
	 */
	public List queryTeacherByAgeRange();
	/***
	 * 分民族统计教职工
	 */
	public List queryTeacherByMz();
	/**
	 * 按户籍所在地(地区)统计教职工
	 */
	public List queryTeacherByYx();
	/**
	 * 按学历层次统计教职工
	 */
	public List queryTeacherByXlcc();
	/**
	 * 按职称统计学生
	 */
	public List queryTeacherByZc();
	
}
