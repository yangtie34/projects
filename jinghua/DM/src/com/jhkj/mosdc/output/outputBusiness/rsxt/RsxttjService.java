package com.jhkj.mosdc.output.outputBusiness.rsxt;

import com.jhkj.mosdc.output.po.FunComponent;

/**
 * @comments:人事系统统计Service接口
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-30 
 * @time:下午02:41:18
 * @version :
 */
public interface RsxttjService {
    
	/**
	 * 人事系统统计的通用图形的接口
	 * @param funCom
	 * @param params
	 * @return
	 */
	public FunComponent queryRsxttjResultForChart(FunComponent funCom,String params);
	
	/**
	 * 人事系统统计的通用文本形的接口
	 * @param funCom
	 * @param params
	 * @return
	 */
	public FunComponent queryRsxttjResultForText(FunComponent funCom,String params);
	
	/**
	 * 人事系统统计的通用表格的接口(表格数据因为与图形类似,因而在实现时方法调用的相同)
	 * @param funCom
	 * @param params
	 * @return
	 */
	public FunComponent queryRsxttjResultForTable(FunComponent funCom,String params);
}


