package com.jhnu.product.wechat.parent.band.service;

import java.util.List;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.wechat.parent.band.entity.WechatPwd;

public interface WechatPwdService {
	
	/**
	 * 通过学生ID获取学生密码类
	 * @param stuId
	 * @return
	 */
	public WechatPwd getOneById(String stuId);
	
	/**
	 * 验证微信中学生的密码 
	 * @param stuId 学生ID
	 * @param password 登陆密码
	 * @return
	 */
	public boolean checkWeChatPassword(String stuId,String password);
	
	/**
	 * 修改密码
	 * @param pwd
	 */
	public void changeWeChatPassword(WechatPwd pwd);
	
	/**
	 * 重置密码
	 * @param pwd
	 */
	public ResultBean resetWechatPassword(WechatPwd pwd);
	
	/**
	 * 验证微信中学生的手机号
	 * @param stuId
	 * @param phone
	 * @return
	 */
	public boolean checkWechatPhone(String stuId,String phone);
	
	/**
	 * 获取未被初始化进微信学生密码表中的数据。
	 * @return
	 */
	public List<Student> getStusInSchoolNotInWechat();
	
	/**
	 * 批量新增微信学生密码数据
	 * @param pwd
	 */
	public void addWechatPasswords(List<WechatPwd> pwds);
	
	/**
	 * 验证学生密码是否改变
	 * @param stuId
	 * @return
	 */
	public boolean checkPwdIsChange(String stuId);
}
