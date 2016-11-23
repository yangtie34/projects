package com.jhkj.mosdc.output.outputBusiness.xgxt;

import com.jhkj.mosdc.output.po.FunComponent;

/**
 * @comments:
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-24 
 * @time:下午08:12:47
 * @version :
 */
public interface JxjZxjtjService {

	/**
	 * 遍历奖学金、助学金统计数量-以图形形式展现
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryJxjZxjtjTotalForChart(FunComponent funCom, String params);

	/**
	 * 遍历奖学金、助学金统计数量-以文本形式展现
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryJxjZxjtjTotalForText(FunComponent funCom, String params);
	
}


