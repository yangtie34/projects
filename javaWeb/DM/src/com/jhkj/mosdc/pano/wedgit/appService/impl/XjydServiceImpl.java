package com.jhkj.mosdc.pano.wedgit.appService.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.pano.wedgit.appService.IXjydService;

public class XjydServiceImpl extends BaseServiceImpl implements IXjydService {

	@Override
	public String getDataById(String params) {
		JSONObject paramObj = JSONObject.fromObject(params);
		if(!paramObj.containsKey("xsId")){
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		long xsId = paramObj.getLong("xsId");
		Map map = new HashMap();
		map.put("title", "2012-09-09 黄蓉 发生了 转专业 学籍异动。");
		List<Map> xjyds = this.getYdxxById(xsId);
		map.put("data", "");
		return Struts2Utils.map2json(map);
	}
	@Override
	public List<Map> getYdxxById(long xsId) {
		StringBuffer sql = new StringBuffer(
				"SELECT T1.*,T2.MC AS YDLB,T3.MC AS YDYY FROM (SELECT * FROM MOSDC_PANO.TB_PANO_XJYD WHERE XS_ID=");
		sql.append(xsId).append(") T1");
		sql.append("LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T2 ON T1.YDLB_ID = T2.ID")
				.append("LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T3 ON T1.YDYY_ID = T3.ID");

		List<Map> results = baseDao.queryListMapBySQL(sql.toString());
		return results;
	}
	@Override
	public String getMiniDataById(String params) {
		return null;
	}
}
