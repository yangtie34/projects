package com.jhnu.product.four.common.service;

import java.util.List;

import com.jhnu.product.four.common.entity.FourMethod;
import com.jhnu.product.four.common.entity.FourNot;
import com.jhnu.product.four.common.entity.ResultBean;

public interface FourService {
	
	public List<FourMethod> getFourMethods();
	
	public List<FourMethod> getFourMethods(String userid);
	
	public List<FourNot> getFourNotByThis(FourNot fourNot);
	
	public ResultBean exetMethod(String bean,String methodName,String userId);
	/**
	 * 依据入学年级及学制获取对应的在校天数
	 * @param enrollYear
	 * @param lengthSchooling
	 * @return
	 */
	public int getFourYearDays(String enrollYear,int lengthSchooling);
	/**
	 * 获取该生是否分享大学生活应用
	 * @param stuid
	 * @return
	 */
	public boolean isFourShared(String stuid);
	/**
	 * 设置分享大学生活应用
	 * @param stuid
	 */
	public void saveShared(String stuid);
	/**
	 * 取消分享大学生活
	 * @param stuid
	 */
	public void delFourShared(String stuid);
	/**
	 * 获取学校名称
	 * @return
	 */
	public String getSchoolName();
}
