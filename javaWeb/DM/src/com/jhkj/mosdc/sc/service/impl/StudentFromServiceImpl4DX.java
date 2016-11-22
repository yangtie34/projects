package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.SqlParamsChange;
import com.jhkj.mosdc.newoutput.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.StudentFromService;

public class StudentFromServiceImpl4DX extends StudentFromServiceImpl
		implements StudentFromService {
	@Override
	public String getCountByZbId(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String start = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String end = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		String zbId = json.containsKey("zbId")?json.getString("zbId"):"rxzrs";
		String whereSql =" and rxrq between '"+start+"' and '"+end+"' ";
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		// 计算默认指标下的地区分布 ，默认指标为来校人数.
		String countSql ="SELECT COUNT(*) AS ZS FROM TB_XJDA_XJXX XJXX where 1=1 {0} {1}";
		countSql = MessageFormat.format(countSql, whereSql,str2);
		int qxCount = baseDao.querySqlCount(countSql);
		String zbSql = getSqlByZbId(zbId);
		// 以学生户口所在地信息进行统计分析。
		
		String statSql = "SELECT HKSZD_ID AS QXM,XZQH.MC,COUNT(*) AS ZS FROM TB_XJDA_XJXX XJXX "+
				" INNER JOIN DM_ZXBZ.T_ZXBZ_XZQH XZQH ON XZQH.DM = XJXX.HKSZD_ID" +
				" WHERE 1=1 {0} {1} {2} GROUP BY HKSZD_ID,XZQH.MC";
		
		statSql = MessageFormat.format(statSql, whereSql,zbSql,str2);
		String sql = "SELECT T1.YWM,T1.DM AS QXM,T1.MC,CASE WHEN T2.ZS IS NULL THEN 0 ELSE T2.ZS END AS ZS" +
				" FROM DM_ZXBZ.T_ZXBZ_XZQH T1 LEFT JOIN ("+statSql+") T2 ON T2.QXM = T1.DM WHERE T1.CC=1";
		List result = baseDao.querySqlList(sql);
		
		for(Object obj : result){
			Map map = (Map) obj;
			int zs =  Integer.parseInt(map.get("ZS").toString());
			float num= qxCount==0?0:(float)zs/qxCount*100;
	        DecimalFormat df = new DecimalFormat("0.00");
	        
	        map.put("ZB", df.format(num));
		}
		
		
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String queryByxxGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String start = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String end = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		String whereSql =" and rxrq between '"+start+"' and '"+end+"' ";
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		// 以学生户口所在地信息进行统计分析。
		String statSql ="SELECT T1.* FROM (SELECT ybyxx,count(*) as rs FROM TB_XJDA_XJXX XJXX where 1=1 "+whereSql+str2+" group by ybyxx ORDER BY RS DESC) T1 WHERE 1=1";
		Map paramsMap = null;
		try {
			paramsMap = SqlParamsChange.getSQLParams(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map resultMap = baseDao.queryTableContentBySQL(statSql,paramsMap);
		List temp = (List)resultMap.get("queryList");
		for(Object obj : temp){
			Map map = (Map)obj;
			String ybyxx = map.get("YBYXX")==null?"":map.get("YBYXX").toString();
			String rs = map.get("RS")==null?"":map.get("RS").toString();
			
			map.put("ybyxx", ybyxx);
			map.put("rs", rs);
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("count",  resultMap.get("count"));
		result.put("success", true);
		result.put("data", temp);
		return Struts2Utils.map2json(result);
	}
	@Override
	public String queryGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String start = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String end = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		String whereSql =" and rxrq between '"+start+"' and '"+end+"' ";
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		// 以学生户口所在地信息进行统计分析。
		/*String statSql ="SELECT T1.* FROM (select t1.sqxm as qxm,xzqh.mc,count(*) as zs from tb_xjda_xjxx_sydtj t1" +
				" inner join (SELECT id FROM TB_XJDA_XJXX where 1=1 "+whereSql+") xjxx on xjxx.id = t1.xs_id " +
				" left join tc_xzqh xzqh on xzqh.qxm = t1.sqxm where t1.sqxm like '16%' group by t1.sqxm,xzqh.mc order by zs desc) T1 WHERE 1=1";*/
		
		String statSql = "SELECT HKSZD_ID AS QXM,XZQH.MC,COUNT(*) AS ZS FROM TB_XJDA_XJXX XJXX "+
				" INNER JOIN DM_ZXBZ.T_ZXBZ_XZQH XZQH ON XZQH.DM = XJXX.HKSZD_ID" +
				" WHERE 1=1 {0} {1} GROUP BY HKSZD_ID,XZQH.MC";
		
		statSql = MessageFormat.format(statSql, whereSql,str2);
		statSql = "SELECT T1.* FROM (SELECT T1.YWM,T1.DM AS QXM,T1.MC,CASE WHEN T2.ZS IS NULL THEN 0 ELSE T2.ZS END AS ZS" +
				" FROM DM_ZXBZ.T_ZXBZ_XZQH T1 LEFT JOIN ("+statSql+") T2 ON T2.QXM = T1.DM WHERE T1.CC=1 ORDER BY ZS DESC)T1 WHERE 1=1 ";
		Map paramsMap = null;
		try {
			paramsMap = SqlParamsChange.getSQLParams(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map resultMap = baseDao.queryTableContentBySQL(statSql,paramsMap);
		List temp = (List)resultMap.get("queryList");
		for(Object obj :temp){
			Map map = (Map) obj;
			String qxm = map.get("QXM")==null?"":map.get("QXM").toString();
			String likeQxm = qxm+"%";
			String mc = map.get("MC")==null?"":map.get("MC").toString();
			int zs =  Integer.parseInt(map.get("ZS").toString());
			/*float num= zs==0?0:(float)zs/zs*100;
	        DecimalFormat df = new DecimalFormat("0.00");*/
			map.put("dq", mc);
			String sql = "select count(xjxx.id) as zs,''rxzrs'' as lx from tb_xjda_xjxx xjxx  left join dm_zxbz.t_zxbz_xzqh xzqh on xzqh.dm =xjxx.hkszd_id where 1=1 and xzqh.DM like ''{21}''  {0} {22} "
					+ "union all select count(xjxx.id) as zs,''nchkrs'' as lx from tb_xjda_xjxx xjxx left join dm_zxbz.t_zxbz_xzqh xzqh on xzqh.dm =xjxx.hkszd_id where 1=1 and hklx_id = {7}  and XZQH.DM like ''{14}'' {1} {22} "
					+ "union all select count(xjxx.id) as zs,''xzhkrs'' as lx from tb_xjda_xjxx xjxx left join dm_zxbz.t_zxbz_xzqh xzqh on xzqh.dm =xjxx.hkszd_id where 1=1 and hklx_id = {8}  and XZQH.DM like ''{15}'' {2} {22} "
					+ "union all select count(xjxx.id) as zs,''cshkrs'' as lx from tb_xjda_xjxx xjxx left join dm_zxbz.t_zxbz_xzqh xzqh on xzqh.dm =xjxx.hkszd_id where 1=1 and hklx_id = {9}  and XZQH.DM like ''{16}'' {2} {22} "
					+ "union all select count(xjxx.id) as zs,''nsrs'' as lx from tb_xjda_xjxx xjxx left join dm_zxbz.t_zxbz_xzqh xzqh on xzqh.dm =xjxx.hkszd_id where 1=1 and xb_id = {10} and XZQH.DM like ''{17}'' {3} {22} "
					+ "union all select count(xjxx.id) as zs,''nvsrs'' as lx from tb_xjda_xjxx xjxx left join dm_zxbz.t_zxbz_xzqh xzqh on xzqh.dm =xjxx.hkszd_id where 1=1 and xb_id = {11}  and XZQH.DM like ''{18}'' {4} {22} "
					+ "union all select count(xjxx.id) as zs,''czqdrs'' as lx from tb_xjda_xjxx xjxx left join dm_zxbz.t_zxbz_xzqh xzqh on xzqh.dm =xjxx.hkszd_id where 1=1 and rxqxl_id = {12} and XZQH.DM like ''{19}'' {5} {22} "
					+ "union all select count(xjxx.id) as zs,''gzqdrs'' as lx from tb_xjda_xjxx xjxx left join dm_zxbz.t_zxbz_xzqh xzqh on xzqh.dm =xjxx.hkszd_id where 1=1 and rxqxl_id = {13} and XZQH.DM like ''{20}'' {6} {22} ";
			sql = MessageFormat.format(sql, whereSql,whereSql,whereSql,whereSql,whereSql,whereSql,whereSql,
					ncid,xzid,csid,nanid,nvid,czid,gzid,likeQxm,likeQxm,likeQxm,likeQxm,likeQxm,likeQxm,likeQxm,likeQxm,str2);
			
			List result = baseDao.querySqlList(sql);
			for(Object tempObj : result){
				Map tempMap = (Map) tempObj;
				String lx = tempMap.get("LX")==null?"":tempMap.get("LX").toString();
				String tempZs = tempMap.get("ZS")==null?"":tempMap.get("ZS").toString();
				map.put(lx, tempZs);
			}
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("count", resultMap.get("count"));
		result.put("success", true);
		result.put("data", temp);
		return Struts2Utils.map2json(result);
	}
}
