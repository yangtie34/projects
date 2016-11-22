package com.jhkj.mosdc.output.outputBusiness.xgxt.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.outputBusiness.xgxt.JxjZxjtjService;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/**
 * @comments:奖助统计service实现
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-24 
 * @time:下午08:13:13
 * @version :
 */
public class JxjZxjtjServiceImpl implements JxjZxjtjService {
	
	private OutPutCommonProcess outPutCommonProcess;

	public void setOutPutCommonProcess(OutPutCommonProcess outPutCommonProcess) {
		this.outPutCommonProcess = outPutCommonProcess;
	}

	@Override
	public FunComponent queryJxjZxjtjTotalForChart(FunComponent funCom,
			String params) {
		funCom.setDisplayData(this.outPutCommonProcess
				.queryCommonChartResult(getFunComponentMap(funCom, params).toString()));
		return funCom;
	}

	@Override
	public FunComponent queryJxjZxjtjTotalForText(FunComponent funCom,
			String params) {
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


