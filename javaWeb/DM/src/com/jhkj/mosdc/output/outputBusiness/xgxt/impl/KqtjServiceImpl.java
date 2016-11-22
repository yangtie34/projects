package com.jhkj.mosdc.output.outputBusiness.xgxt.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.outputBusiness.xgxt.KqtjService;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/**
 * @comments:考勤统计模块统计service实现
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-24 
 * @time:下午01:40:24
 * @version :
 */
public class KqtjServiceImpl implements KqtjService{

	private OutPutCommonProcess outPutCommonProcess;

	public void setOutPutCommonProcess(OutPutCommonProcess outPutCommonProcess) {
		this.outPutCommonProcess = outPutCommonProcess;
	}

	@Override
	public FunComponent queryKqtjTotalForChart(FunComponent funCom, String params) {
		funCom.setDisplayData(this.outPutCommonProcess
				.queryCommonChartResult(getFunComponentMap(funCom, params).toString()));
		return funCom;
	}

	@Override
	public FunComponent queryKqtjTotalForText(FunComponent funCom, String params) {
		funCom.setDisplayData(this.outPutCommonProcess
				.queryTextResult(getFunComponentMap(funCom, params).toString()));
		return funCom;
	}
	
	private Map<String, Object> getFunComponentMap(FunComponent funCom,
			String params) {
		Map<String, Object> map = JSONObject.fromObject(params);
		map.put("componentId", funCom.getComponentId());
		return map;
	}
	
}


