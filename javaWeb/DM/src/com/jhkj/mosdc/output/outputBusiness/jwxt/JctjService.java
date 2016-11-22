package com.jhkj.mosdc.output.outputBusiness.jwxt;

import com.jhkj.mosdc.output.po.FunComponent;

/**
 * @comments:教材统计的service（涵盖所有教材方面的统计）
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-19
 * @time:下午06:17:09
 * @version : 第二版
 */
public interface JctjService {

	/**
	 * 根据教材类型分类计算其教材数量 显示类型 ：文本 eg: 据统计显示，教材目录共计XY类图书，其中会计类共有X种书籍，计算机系共有Y种书籍....
	 * 
	 * @param funCom
	 *            组件对象
	 * @param params
	 *            参数格式：初始化时：组件id/初始化为真；然后就是联动参数/非初始化的话组件id+联动数据
	 * @return
	 */
	public FunComponent queryJcmlglTotalForStyleToText(FunComponent funCom, String params);

	/**
	 * 根据教材类型分类计算其教材数量，用图形展示。
	 *        如：x轴：会计类  数学类  计算机类..  y轴对应的数据则为相应的数量。 展示类型可以选为：饼图、柱图（设计为2维统计）
	 * @param funCom
	 *            组件对象
	 * @param params
	 *            参数格式：初始化时：组件id init不为空/非初始化时：组件id fw/wd/zb的id。联动数据
	 * @return
	 */
	public FunComponent queryJcmlglTotalForStyleToChart(FunComponent funCom,
			String params);
    
	
	
}