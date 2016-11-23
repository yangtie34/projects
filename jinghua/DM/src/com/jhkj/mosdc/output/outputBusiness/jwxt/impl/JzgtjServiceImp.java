package com.jhkj.mosdc.output.outputBusiness.jwxt.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/***
 * 教职工统计service接口实现类
 * 
 * @Comments: Company: 河南精华科技有限公司 Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-24
 * @TIME: 下午4:37:01
 */
public class JzgtjServiceImp {
	private OutPutCommonProcess outPutCommonProcess;

	/***
	 * 教职工人数总计 我校共有1987名教师，其中男教师560人，女教师340人。
	 * 
	 * @return
	 */
	public FunComponent queryJzgRsText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 学历统计 我校教师博士生学历21人，研究生学历45人，本科学历45人。
	 * 
	 * @return
	 */
	public FunComponent queryJzgXlrsText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 学历统计 1 指标 统计人数 2 维度 按性别统计 ，按系部统计 3 范围 博士生 研究生 本科
	 * 
	 * @return
	 */
	public FunComponent queryJzgXlrsChart(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 职称统计 我校有讲师职称教师21人，副教授职称教师45人，教授职称教师45人。
	 * 
	 * @return
	 */
	public FunComponent queryJzgZcText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 职称统计 1 指标 统计人数 2 维度 按性别统计 ，按系部统计 3 范围 讲师 副教授 教授
	 * 
	 * @return
	 */
	public FunComponent queryJzgZcChart(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 民族统计 我校教师中，汉族450人，回族为34人，其他民族为23人
	 * 
	 * @return
	 */
	public FunComponent queryJzgMzText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 民族统计 1 指标 统计人数 2 维度 按性别统计 ，按系部统计 3 范围 汉族 回族 其他民族
	 * 
	 * @return
	 */
	public FunComponent queryJzgMzChart(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 职工类型统计 我校在编人员343人，人事代理34人，临时工233人。
	 * 
	 * @return
	 */
	public FunComponent queryJzgZglxText(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 1 指标 统计人数 2 维度 按性别统计 ，按系部统计 3 范围 在编 人事代理 临时工
	 * 
	 * @return
	 */
	public FunComponent queryJzgZglxChart(FunComponent fun, String params) {
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
