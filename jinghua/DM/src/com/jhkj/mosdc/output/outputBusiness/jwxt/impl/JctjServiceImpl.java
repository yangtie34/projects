package com.jhkj.mosdc.output.outputBusiness.jwxt.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.outputBusiness.jwxt.JctjService;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/**
 * @comments:教材统计方面的service接口实现
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-19
 * @time:下午06:24:15
 * @version :
 */
public class JctjServiceImpl implements JctjService {

	private OutPutCommonProcess outPutCommonProcess;

	public void setOutPutCommonProcess(OutPutCommonProcess outPutCommonProcess) {
		this.outPutCommonProcess = outPutCommonProcess;
	}

	@Override
	public FunComponent queryJcmlglTotalForStyleToText(FunComponent funCom,
			String params) {
		funCom.setDisplayData(this.outPutCommonProcess
				.queryTextResult(getFunComponentMap(funCom, params).toString()));
		return funCom;
	}

	@Override
	public FunComponent queryJcmlglTotalForStyleToChart(FunComponent funCom,
			String params) {
		
		funCom.setDisplayData(this.outPutCommonProcess.queryCommonChartResult(this
				.getFunComponentMap(funCom, params).toString()));
		return funCom;
	}

	private Map<String, Object> getFunComponentMap(FunComponent funCom,
			String params) {
		Map<String, Object> map = JSONObject.fromObject(params);
		map.put("componentId", funCom.getComponentId());
		return map;
	}
}