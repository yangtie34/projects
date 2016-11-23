package com.jhnu.person.tea.service;

import java.util.List;
import java.util.Map;

public interface TeaInfoService {
	/**获取教师信息
	 * 
	 */
	public Map getInfo(String id);
	/**教师信息变动历史
	 * 
	 */
	public Map getHisInfo(String id);
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
	boolean isTeacher(String id);
}
