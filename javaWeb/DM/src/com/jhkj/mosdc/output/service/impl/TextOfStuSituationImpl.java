package com.jhkj.mosdc.output.service.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.process.OutPutCommonProcess;
import com.jhkj.mosdc.output.service.ITextOfStuSituation;
/**
 * 文本类型的学生概况。
 * 
 * @Comments: 
 * Company: 河南精华科技有限公司
 * Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-20
 * @TIME: 下午02:57:44
 */
public class TextOfStuSituationImpl implements ITextOfStuSituation{
	private OutPutCommonProcess process;
	
	public void setProcess(OutPutCommonProcess process) {
		this.process = process;
	}

	@Override
	public FunComponent initData(FunComponent fc, String params) {
		// TODO Auto-generated method stub
		return fc;
	}

	@Override
	public FunComponent queryTextAddOfStu(FunComponent fc, String params) {
		return fc;
	}

	@Override
	public FunComponent queryTextNationOfStu(FunComponent fc, String params) {
		return fc;
	}

	@Override
	public FunComponent queryTextNumOfStu(FunComponent fc, String params) {
		String str = "{componentId:\""+fc.getComponentId()+"\"}";
		Map<String,Object> map = process.queryTextResult(str);
		fc.setDisplayData(map);
		return fc;
	}

	@Override
	public FunComponent queryTextSexOfStu(FunComponent fc, String params) {
		String str = "{componentId:\""+fc.getComponentId()+"\"}";
		Map<String,Object> map = process.queryTextResult(str);
		fc.setDisplayData(map);
		return fc;
	}

	@Override
	public FunComponent queryTextZzmmOfStu(FunComponent fc, String params) {
		return fc;
	}

	@Override
	public FunComponent querySimpleChartOfStu(FunComponent fc, String params) {
		JSONObject json = JSONObject.fromObject(params);
		json.put("componentId", fc.getComponentId());
		Map<String,Object> map = process.queryCommonChartResult(json.toString());
		fc.setDisplayData(map);
		return fc;
	}

}
