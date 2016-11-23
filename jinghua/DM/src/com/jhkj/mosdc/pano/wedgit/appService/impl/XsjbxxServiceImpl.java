package com.jhkj.mosdc.pano.wedgit.appService.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.pano.wedgit.appService.IXsjbxxService;

public class XsjbxxServiceImpl extends BaseServiceImpl implements
		IXsjbxxService {
	@Override
	public String getDataById(String params) {
		JSONObject paramObj = JSONObject.fromObject(params);
		if(!paramObj.containsKey("xsId")){
			return SysConstants.JSON_SUCCESS_FALSE;
		}
		long xsId = paramObj.getLong("xsId");
		Map map = new HashMap();
		// 计算信息完整度
		map.put("title","学籍信息完善度:100%!");
		// 组装学生基本信息json
		List<Map> xsjbxxs = this.getXjxxByXsId(xsId);
		map.put("jbxx", xsjbxxs!=null&&xsjbxxs.size()!=0?"":xsjbxxs.get(0));
		
		List<Map> jtgxs = this.getJtqkByXsId(xsId);
		map.put("jtgx", jtgxs!=null&&jtgxs.size()!=0?"":jtgxs.get(0));
		
		List<Map> bzrs = this.getBzrxxByXsId(xsId);
		map.put("bzr", bzrs!=null&&bzrs.size()!=0?"":bzrs.get(0));
		
		List<Map> ssxxs = this.getSsxxByXsId(xsId);
		map.put("ssxx", ssxxs!=null&&ssxxs.size()!=0?"":ssxxs.get(0));
		
		List<Map> zwxxs = this.getZwxxByXsId(xsId);
		map.put("zwxx", zwxxs!=null&&zwxxs.size()!=0?"":zwxxs.get(0));
		
		// 计算预计毕业时间
		map.put("yjbysj", "");
		// 计算年龄
		map.put("nl", "");
		
		return Struts2Utils.map2json(map);
	}

	@Override
	public List<Map> getXjxxByXsId(long xsId) {
		StringBuffer sql = new StringBuffer("SELECT T1.ID,T1.XH,T1.XM,T1.XB_ID,T2.MC AS XB,T1.MZ_ID,T3.MC AS MZ,");
	    sql.append("T4.MC AS ZZMM,T1.ZZMM_ID,T1.SFZH,T1.CSRQ,T1.LXDH,T1.JTDZ,T1.HKLX_ID,T1.ZPLJ,")
		    .append("T5.MC AS HJXZ ,T1.HKSZD_ID,T14.MC AS HKSZD,T1.SYDSX_ID,T13.MC AS SYD,T1.XJZT_ID,T6.MC AS XJZT,T1.PC_ID,")
			.append("T5.MC AS HJXZ ,T1.HKSZD_ID,T14.MC AS HKSZD,T1.SYDSX_ID,T13.MC AS SYD,T1.XJZT_ID,T6.MC AS XJZT,T1.PC_ID,")
			.append("T7.PC AS ZSPC,T1.KSLB_ID ,T8.MC AS KSLB,T1.RXRQ,T1.XXXS_ID,T9.MC AS XXXS,")
			.append("T1.XZ_ID,T10.MC AS XZ,T1.ZY_ID,T1.BJ_ID,T11.MC AS BJ,T12.MC AS ZY  ")
			.append("FROM (SELECT * FROM MOSDC_PANO.TB_XS_JBXXB WHERE ID = ")
			.append(xsId)
			.append("T1 ")
			.append("LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T2 ON T1.XB_ID = T2.ID ")
			.append("LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T3 ON T1.MZ_ID = T3.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T4 ON T1.ZZMM_ID = T4.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T5 ON T1.HKLX_ID = T5.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T6 ON T1.XJZT_ID = T6.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_ZSPCXXB T7 ON T7.ID = T1.PC_ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T8 ON T1.KSLB_ID = T8.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T9 ON T1.XXXS_ID = T9.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T10 ON T1.XZ_ID = T10.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_BJXXB T11 ON T1.BJ_ID = T11.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_JXZZJG T12 ON T11.FJD_ID = T12.ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_XZQH T13 ON T13.ID = T1.SYDSX_ID ")
			.append( "LEFT JOIN MOSDC_PANO.TB_BASE_XZQH T14 ON T14.ID = T1.HKSZD_ID");
		List<Map> xsjbxxs = baseDao.queryListMapBySQL(sql.toString());
		return xsjbxxs;
	}

	@Override
	public List<Map> getJtqkByXsId(long xsId) {
		StringBuffer sql = new StringBuffer(
				"SELECT T1.XS_ID,T1.QSGX_ID,T2.MC AS QSGX,T1.XM,T1.LXFS");
		sql.append("FROM (SELECT * FROM MOSDC_PANO.TB_XS_JTQK WHERE XS_ID = ")
				.append(xsId).append(") T1")
				.append("LEFT JOIN MOSDC_PANO.TB_BASE_XXBZDMJG T2 ON T1.QSGX_ID = T2.ID ");

		List<Map> xsjbxxs = baseDao.queryListMapBySQL(sql.toString());
		return xsjbxxs;
	}

	@Override
	public List<Map> getBzrxxByXsId(long xsId) {
		StringBuffer sql = new StringBuffer(
				"SELECT T2.ID,T2.ZGH,T2.XM,T2.LXDH FROM MOSDC_PANO.TB_BASE_BJXXB T1,MOSDC_PANO.TB_JZG_JBXXB T2,MOSDC_PANO.TB_XS_JBXXB T3 ");
		sql.append("WHERE T1.BZR_ID = T2.ID AND T3.ID = ").append(xsId)
				.append(" AND T3.BJ_ID = T1.ID");
		List<Map> xsjbxxs = baseDao.queryListMapBySQL(sql.toString());
		return xsjbxxs;
	}

	@Override
	public List<Map> getSsxxByXsId(long xsId) {
		StringBuffer sql = new StringBuffer("SELECT T1.XS_ID,T6.MC||'#'||T5.MC||'#'||T4.MC||'#'||T3.MC||'#'||T2.MC AS SSXX ");
		sql.append("FROM (SELECT * FROM MOSDC_PANO.TB_PANO_SSZY WHERE XS_ID = ")
			.append(xsId)
			.append(") T1")
			.append("LEFT JOIN MOSDC_PANO.TB_PANO_SSCCJG T2 ON T2.ID = T1.CW_ID ")
			.append("LEFT JOIN MOSDC_PANO.TB_PANO_SSCCJG T3 ON T2.FJD_ID = T3.ID ")
			.append("LEFT JOIN MOSDC_PANO.TB_PANO_SSCCJG T4 ON T3.FJD_ID = T4.ID ")
			.append("LEFT JOIN MOSDC_PANO.TB_PANO_SSCCJG T5 ON T4.FJD_ID = T5.ID ")
			.append("LEFT JOIN MOSDC_PANO.TB_PANO_SSCCJG T6 ON T5.FJD_ID = T6.ID ");
		
		List<Map> xsjbxxs = baseDao.queryListMapBySQL(sql.toString());
		return xsjbxxs;
	}

	@Override
	public List<Map> getZwxxByXsId(long xsId) {
//		StringBuffer sql = new StringBuffer("");
//		List<Map> xsjbxxs = baseDao.queryListMapBySQL(sql.toString());
//		return xsjbxxs;
		return null;
	}
	@Override
	public String getMiniDataById(String params) {
		
		return null;
	}

}
