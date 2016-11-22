package com.jhnu.product.wechat.parent.band.dao;

import java.util.List;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.wechat.parent.band.entity.WechatPwd;

public interface WechatPwdDao {
	
	/**
	 * 通过学生ID获取学生密码类
	 * @param stuId
	 * @return
	 */
	public WechatPwd getOneById(String stuId);
	
	/**
	 * 修改密码
	 * @param pwd
	 */
	public void changeWeChatPassword(WechatPwd pwd);
	
	/**
	 * 重置密码
	 * @param pwd
	 */
	public int resetWeChatPassword(WechatPwd pwd);
	
	/**
	 * 获取未被初始化至微信密码表的在校生。
	 * @return
	 */
	public List<Student> getStusInSchoolNOdtInWechat();
	/**
	 * 批量增加学生微信密码数据
	 * @param pwds
	 */
	public void addWechatPasswords(List<WechatPwd> pwds);
}
