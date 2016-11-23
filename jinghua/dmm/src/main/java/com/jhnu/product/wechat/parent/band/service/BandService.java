package com.jhnu.product.wechat.parent.band.service;

import java.util.List;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.wechat.parent.band.entity.Band;

public interface BandService {
	
	
	/**
	 * 通过密码添加一条绑定信息，用于登陆
	 * @param band 
	 * @param password
	 * @return
	 */
	public ResultBean addBandByPassword(Band band,String password);
	
	/**
	 * 添加一条绑定信息
	 * @param band 
	 * @return
	 */
	public ResultBean addBand(Band band);
	
	
	/**
	 * 解除一条绑定信息
	 * @param band
	 * @return
	 */
	public ResultBean removeBand(Band band);
	
	
	/**
	 * 通过参数信息，查找符合的绑定信息集合
	 * @param band
	 * @return
	 */
	public List<Band> getBandByThis(Band band);
	
	/**
	 * 通过微信访问的CODE，拿到绑定的学生ID，如果没有，返回空
	 * @param code 微信访问的CODE
	 * @return
	 */
	public String getStuIdByWechatCode(String code);
	
	/**
	 * 添加访问日志
	 * @param visitMenu
	 * @param is_wechat
	 * @param visitDate
	 * @param username
	 */
	public void addVisitLogging(String visitMenu,int is_wechat,String visitDate,String username);
	
	
	
}
