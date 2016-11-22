package com.jhkj.mosdc.output.outputBusiness.jwxt.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.outputBusiness.jwxt.CjtjService;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;
/***
 * 成绩统计service接口实现类
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by wangyongtai(490091105@.com)
 * @DATE:2012-11-19
 * @TIME: 下午4:02:30
 */
public class CjtjServiceImp implements CjtjService {
    private OutPutCommonProcess outPutCommonProcess;
	/***
	 * 查询上学期-总人数-按成绩范围-分布（1: 60以下  2:60-79分  3:80 - 89分  4:90分以上）---文本统计
	 * @return
	 */
	public FunComponent queryNjRsXx(FunComponent fun,String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 查询培养类别-年级-成绩分布
	 * 统计功能一（柱状图）上学期平均成绩人数分布统计
	 *	一 维度 ：1 年级 2 培养类别 3 男女
	 *	二 范围 ： 1 分数： 1 60以下 2  60-79分 3 80 - 89分 4  90分以上
	 *	三 指标  ： 统计人数
	 * @param params
	 * @return
	 */
	public FunComponent queryPylbNvCjfb(FunComponent fun, String params) {
		// TODO Auto-generated method stub
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 查询在校生-按学期-平均成绩
	 * 统计功能二（线状图）在校学生平均成绩
	 * 一 维度 ： 1 学期   2 培养类别
	 * 二 范围  ： 1 一年级 2 二年级 3 三年级 
	 * 三 指标  ：1 按男生统计 2 按女生统计
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryZxsPjcjbh(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}

	/***
	 * 分培养类别 分男女查询本学期学生平均成绩
	 *  一 维度 ：  1  培养类别 2性别
		二 范围  ： 1  一年级 2 二年级 3 三年级  4 四年级
		三 指标  ： 1  平均成绩
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryLbNvPjcj(FunComponent fun, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryCommonChartResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}
	/***
	 * 本学期补缓考成绩概要统计（文本统计）
	 * 中职补考总人数67人，缓考总人数为78人，旷考总人数为87 人。
                 大专补考总人数67人，缓考总人数为78人，旷考总人数为87 人。
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryBxqBhkgk(FunComponent fun,String params){
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fun.getComponentId());
		Map disp = outPutCommonProcess.queryTextResult(json.toString());
		fun.setDisplayData(disp);
		return fun;
	}
	/***
	 *  统计功能五  分类别统计学校补考/缓考/旷考学生 
		一 维度 ： 1 年级
		二 范围  ： 1 培养类别 : 中职  2 培养类别 ： 大专 
		三 指标   ： 学生人数
	 * @param fun
	 * @param params
	 * @return
	 */
	public FunComponent queryFlbxxbhk(FunComponent fun,String params){
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
