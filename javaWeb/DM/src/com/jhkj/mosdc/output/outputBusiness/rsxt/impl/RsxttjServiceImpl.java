package com.jhkj.mosdc.output.outputBusiness.rsxt.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.outputBusiness.rsxt.RsxttjService;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/**
 * @comments:人事系统统计Service实现
 * @author hanking(E-mail : IsaidIwillgoon@gmail.com)
 * @date:2012-11-30
 * @time:下午02:46:04
 * @version :
 */
public class RsxttjServiceImpl implements RsxttjService {

	private OutPutCommonProcess outPutCommonProcess;

	public void setOutPutCommonProcess(OutPutCommonProcess outPutCommonProcess) {
		this.outPutCommonProcess = outPutCommonProcess;
	}

	@Override
	public FunComponent queryRsxttjResultForChart(FunComponent funCom,
			String params) {
		funCom.setDisplayData(this.outPutCommonProcess
				.queryCommonChartResult(getFunComponentMap(funCom, params)
						.toString()));
		return funCom;
	}

	@Override
	public FunComponent queryRsxttjResultForTable(FunComponent funCom,
			String params) {
		funCom.setDisplayData(this.outPutCommonProcess
				.queryCommonChartResult(getFunComponentMap(funCom, params)
						.toString()));
		return funCom;
	}

	@Override
	public FunComponent queryRsxttjResultForText(FunComponent funCom,
			String params) {
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

}
