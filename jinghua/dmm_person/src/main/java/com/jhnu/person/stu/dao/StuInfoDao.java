package com.jhnu.person.stu.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.page.Page;

public interface StuInfoDao {
	/**
	 * 获取个人信息
	 * @param id
	 * @return
	 */
	public Map getInfo(String id);
	/**
	 * 获取学期注册
	 * @param id
	 * @return
	 */
	public List getXqzc(String id);
	/**
	 * 获取荣誉及奖励信息
	 * @param id
	 * @return
	 */
	public List getGlory(String id);
	/**
	 * 获取学籍异动
	 * @return
	 */
	public List getXjyd(String id);
	/**
	 * 获取宿舍住宿信息
	 * @return
	 */
	public List getSsZsxx(String id);
	/**
	 * 获取宿舍调整信息
	 * @return
	 */
	public List getSsTzxx(String id);
	/**
	 * 其他身份
	 * @param id
	 * @return
	 */	
	public List OtherUser(String id);
	/**
	 * 获取用户性别
	 * @param id
	 * @return
	 */	
	public String getUserSex(String id);

}
