package com.jhnu.product.four.common.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.product.four.common.entity.FourMethod;
import com.jhnu.product.four.common.entity.FourNot;

public interface FourDao {
	
	public List<FourMethod> getFourMethods();
	
	public List<FourMethod> getFourMethods(String userId);
	
	public List<FourNot> getFourNotByThis(FourNot fourNot);
	/**
	 * 查询是否分享大学生活
	 * @param stuid
	 * @return
	 */
	public List<Map<String,Object>> getFourIsShared(String stuid);
	/**
	 * 保存 分享 学生大学生活
	 * @param stuid
	 */
	public void saveFourShared(String stuid);
	/**
	 * 取消 分享 学生大学生活
	 * @param stuid
	 */
	public void delFourShared(String stuid);
	/**
	 * 查询学校姓名
	 * @return
	 */
	public String getSchoolName();
}
