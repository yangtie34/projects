package com.jhkj.mosdc.output.outputBusiness.xgxt;

import com.jhkj.mosdc.output.po.FunComponent;

/**
 * @comments:
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-24
 * @time:下午01:36:28
 * @version :
 */
public interface KqtjService {

	/**
	 * 遍历考勤统计数量-以图形形式展现
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryKqtjTotalForChart(FunComponent funCom, String params);

	/**
	 * 遍历考勤统计数量-以文本形式展现
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryKqtjTotalForText(FunComponent funCom, String params);
}
