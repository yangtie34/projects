package com.jhkj.mosdc.sc.service.impl;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.TeacherXueyuanService;

public class TeacherXueyuanServiceImpl extends TeacherServiceImpl implements TeacherXueyuanService {

	@Override
	public String queryBxWxzc(String params) {// 本校、外校
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		
		return "[]";
	}

	@Override
	public String queryXslp(String params) {// 学术流派
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT NVL(XB.MC,''未维护'') AS MC,XB.wid,COUNT(*) ZS FROM TB_JZGXX XJXX "+
				" LEFT JOIN  dm_zxbz.T_ZXBZ_WHCD  xb on xb.wid = xjxx.xwd_ID WHERE 1=1 {0}  GROUP BY XB.MC,XB.wid ORDER BY XB.MC";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", "学术流派教职工人数");
			obj.put("value", obj.get("ZS"));
		}
		
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryByyxCc(String params) {// 毕业院校层次
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT NVL(XB.MC,''未维护'') AS MC,COUNT(*) ZS FROM tb_jzgxx XJXX "+
				" LEFT JOIN  dm_zxbz.T_ZXBZ_WHCD xb on xb.wid = xjxx.WHC_ID WHERE 1=1 {0}   GROUP BY XB.MC ORDER BY XB.MC";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("name", obj.get("MC"));
			obj.put("y", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryByyxLx(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT NVL(XB.MC,''未维护'') AS MC,COUNT(*) ZS FROM tb_jzgxx XJXX "+
				" LEFT JOIN  dm_zxbz.T_ZXBZ_WHCD xb on xb.wid = xjxx.WHC_ID WHERE 1=1 {0}   GROUP BY XB.MC ORDER BY XB.MC";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("name", obj.get("MC"));
			obj.put("y", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryByyxDy(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT NVL(XB.MC,''未维护'') AS MC,COUNT(*) ZS FROM tb_jzgxx XJXX "+
				" LEFT JOIN  dm_zxbz.T_ZXBZ_WHCD xb on xb.wid = xjxx.WHC_ID WHERE 1=1 {0}   GROUP BY XB.MC ORDER BY XB.MC";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		String sql3 = "select mc,0 as zs from tc_xzqh where cc=1";
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql3);
		for(Map obj : result){
			obj.put("name", obj.get("MC"));
			obj.put("y", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryByyx(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="select * from (SELECT case when dyxlbyxx is null then ''未维护'' else  dyxlbyxx end AS MC,COUNT(*) ZS FROM TB_JZGXX XJXX where 1=1 {0} GROUP BY dyxlbyxx) where zs >10  ORDER by zs desc";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", "毕业院校教职工人数");
			obj.put("value", obj.get("ZS"));
		}
		
		return Struts2Utils.list2json(result);
	}

	@Override
	public String queryInfo1(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		Map backval = new HashMap();
		backval.put("bm", node.get("mc"));
		backval.put("num", 0);
		return Struts2Utils.map2json(backval);
	}
	@Override
	public String queryInfo2(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		Map backval = new HashMap();
		backval.put("bm", node.get("mc"));
		backval.put("num", 0);
		backval.put("jw", 0);
		backval.put("jn", 0);
		return Struts2Utils.map2json(backval);
	}
}
