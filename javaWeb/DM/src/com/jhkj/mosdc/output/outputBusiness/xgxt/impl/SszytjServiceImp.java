package com.jhkj.mosdc.output.outputBusiness.xgxt.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.outputBusiness.xgxt.SszytjService;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/***
 * 宿舍资源以及使用情况统计service实现类
 * 
 * @Comments: Company: 河南精华科技有限公司 Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-27
 * @TIME: 下午2:44:19
 */
public class SszytjServiceImp implements SszytjService {
	private OutPutCommonProcess outPutCommonProcess;

	/***
	 * 查询宿舍资源使用信息
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySszyText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 查询宿舍入住情况
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsRzqkText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 查询宿舍入住情况(分系部/性别)
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryRzqkChart(FunComponent fun, String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 查询宿舍入住率
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsRzlText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 查询宿舍入住率(分系部/性别)
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsRzlChart(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 查询宿舍分年级入住情况统计
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsFnjRzqkText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 查询宿舍分年级入住情况统计(分系部/性别)
	 * 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent querySsFnjRzqkChart(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	public OutPutCommonProcess getOutPutCommonProcess() {
		return outPutCommonProcess;
	}

	public void setOutPutCommonProcess(OutPutCommonProcess outPutCommonProcess) {
		this.outPutCommonProcess = outPutCommonProcess;
	}
}
