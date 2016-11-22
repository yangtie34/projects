package com.jhkj.mosdc.output.outputBusiness.jwxt.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.outputBusiness.jwxt.ZstjService;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;

/***
 * 招生统计service 接口实现类
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-25
 * @TIME: 下午7:15:11
 */
public class ZstjServiceImpl implements ZstjService {
	private OutPutCommonProcess outPutCommonProcess;

	
	/***
	 *  今年招生概况统计 
  		今年招生人数3000人，男生1500人，女生1500人。（文本）
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryZsgkText(FunComponent fun,String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}
	
	/***
	 *    今年新入学学生中，河南省为2800人，外省为200人。（文本）
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryHnsWsText(FunComponent fun,String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}
	
	/***
	 * 
	 *    河南省分地市统计（chart图） 
		  1 维度 河南省地市 性别 
		  2 范围 河南省
		  3 指标 统计人数
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryHnsFdsChart(FunComponent fun,String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}
	/***
	 *  今年新入学学生中，汉族为2900人，回族20人，其他民族80人
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryMzxbText(FunComponent fun,String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}
	/***
	 *   新入校学生分系部统计
		  1 维度 系部 性别
		  2 范围 汉族 回族 其他
		  3 指标 统计人数
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryMzxbChart(FunComponent fun,String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}
	
	/***
	 *  今年招生人数为3000人，去年招生人数为2800人。 
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryJnQnText(FunComponent fun,String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}
	/***
	 *   最近5年招生趋势图  （chart 图）
		   1 维度 最近5年
		   2  范围   最近5年所有学生
		   3 指标 ，按系部统计 按性别统计  
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryLastFiveYearChart(FunComponent fun,String params) {
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
