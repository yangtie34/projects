package com.jhnu.product.wechat.parent.student.dao;

import java.util.List;
import java.util.Map;

public interface WechatStudentDao {
	
	/**
	 * 根据学生id获取学生基本信息(学生姓名，班级，专业，院系,辅导员，系主任)
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getStudentInfo(String id);
	
	/**
	 * 查询招生处，教学主管校长，学工主管校长
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getDeptInfo();
	
	/**
	 * 根据学生id获取学生宿舍信息(学生的宿舍楼，楼层，房间号，床位)
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getDormInfo(String id);
	
	/**
	 * 根据学生id获取其所有室友
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getRoommate(String id);
	
	/**
	 * 根据学生id查询学生所获奖学金
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getAward(String id);
	
	/**
	 * 根据学生id查询学生所获助学金
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getSubsidy(String id);
	
}
