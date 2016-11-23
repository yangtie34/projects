package com.jhkj.mosdc.output.outputBusiness.xxzygl;

import com.jhkj.mosdc.output.po.FunComponent;

/**
 * @comments:学校资源统计service接口
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-27 
 * @time:上午10:06:34
 * @version :
 */
public interface XxzytjService {

	/**
	 * 校区资源统计--图形展示方法
	 * @param funCom
	 * @param params
	 * @return
	 */
	public FunComponent queryXqzyForChart(FunComponent funCom,String params);
	
	/**
	 * 学校资源--教室信息统计之 图形统计
	 * @param funCom
	 * @param params
	 * @return
	 */
	public FunComponent queryJsxxForChart(FunComponent funCom,String params);
	/**
	 * 学校资源--教室信息统计之 文本统计
	 * @param funCom
	 * @param params
	 * @return
	 */
	public FunComponent queryJsxxForText(FunComponent funCom,String params);
	
	/**
	 * 校区资源统计--文本展示方法
	 * @param funCom
	 * @param params
	 * @return
	 */
	public FunComponent queryXqzyForText(FunComponent funCom,String params);
}


