package com.jhkj.mosdc.output.outputBusiness.xxzygl.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.outputBusiness.xxzygl.XxzytjService;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/**
 * @comments:学校资源统计service实现
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-27
 * @time:上午10:07:30
 * @version :
 */
public class XxzytjServiceImpl implements XxzytjService {

	private OutPutCommonProcess outPutCommonProcess;

	public void setOutPutCommonProcess(OutPutCommonProcess outPutCommonProcess) {
		this.outPutCommonProcess = outPutCommonProcess;
	}

	@Override
	public FunComponent queryXqzyForChart(FunComponent funCom, String params) {
		funCom.setDisplayData(this.outPutCommonProcess
				.queryCommonChartResult(getFunComponentMap(funCom, params)
						.toString()));
		return funCom;
	}

	@Override
	public FunComponent queryXqzyForText(FunComponent funCom, String params) {
		funCom
				.setDisplayData(this.outPutCommonProcess
						.queryTextResult(getFunComponentMap(funCom, params)
								.toString()));
		return funCom;
	}

	private Map<String, Object> getFunComponentMap(FunComponent funCom,
			String params) {
		Map<String, Object> map = JSONObject.fromObject(params);
		map.put("componentId", funCom.getComponentId());
		return map;
	}

	@Override
	public FunComponent queryJsxxForChart(FunComponent funCom, String params) {
		funCom.setDisplayData(this.outPutCommonProcess
				.queryCommonChartResult(getFunComponentMap(funCom, params)
						.toString()));
		return funCom;
	}

	@Override
	public FunComponent queryJsxxForText(FunComponent funCom, String params) {
		funCom
				.setDisplayData(this.outPutCommonProcess
						.queryTextResult(getFunComponentMap(funCom, params)
								.toString()));
		return funCom;
	}
}