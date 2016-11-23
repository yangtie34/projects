package com.jhkj.mosdc.sc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.KyCgService;

public class KyCgServiceImpl extends KyXmServiceImpl implements KyCgService {

	@Override
	public String getZlxx(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		String sql ="SELECT COUNT(1) AS ZS FROM T_KY_ZL T WHERE 1=1" +str2 +
				" AND substr(to_char(T.SQRQ,'yyyy-MM-dd'),1,4) BETWEEN "+from+" AND "+to;
		int count = baseDao.querySqlCount(sql);
		return count+"";
	}

	@Override
	public String getZzxx(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		String sql ="SELECT COUNT(1) AS ZS FROM T_KY_ZZ T WHERE 1=1" +str2+
				" AND substr(t.cbny,1,4)  BETWEEN "+from+" AND "+to;;
		int count = baseDao.querySqlCount(sql);
		return count+"";
	}

	@Override
	public String getLwxx(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		String sql ="SELECT COUNT(1) AS ZS FROM T_KY_LW T WHERE 1=1" +str2+
				" AND substr(T.fbny,1,4) BETWEEN "+from+" AND "+to;;
		int count = baseDao.querySqlCount(sql);
		return count+"";
	}

	@Override
	public String getXmxx(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		String sql ="SELECT COUNT(1) AS ZS FROM (select lxrq,dwdm from t_ky_zxxm union all " +
				" select TO_CHAR(lxrq,'yyyy-MM-dd') AS LXRQ,dwdm from t_ky_hxxm) T WHERE 1=1" +str2+
				" AND substr(T.lxrq,1,4) BETWEEN "+from+" AND "+to;;
		int count = baseDao.querySqlCount(sql);
		return count+"";
	}

	@Override
	public String getHjcgxx(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		String sql ="SELECT COUNT(1) AS ZS FROM T_KY_CGHJ T WHERE 1=1" +str2+
				" AND substr(to_char(T.hjrq,'yyyy-MM-dd'),1,4) BETWEEN "+from+" AND "+to;
		int count = baseDao.querySqlCount(sql);
		return count+"";
	}

	
	@Override
	public String getChartData(String params) {
		List<Map> backval = new ArrayList<Map>();
		int zls = Integer.parseInt(this.getZlxx(params));
		Map zlMap = new HashMap();
		zlMap.put("name", "专利");
		zlMap.put("y", zls);
		backval.add(zlMap);
		int zzs = Integer.parseInt(this.getZzxx(params));
		Map zzMap = new HashMap();
		zzMap.put("name", "著作");
		zzMap.put("y", zzs);
		backval.add(zzMap);
		int lws = Integer.parseInt(this.getLwxx(params));
		Map lwMap = new HashMap();
		lwMap.put("name", "论文");
		lwMap.put("y", lws);
		backval.add(lwMap);
		int xms = Integer.parseInt(this.getXmxx(params));
		Map xmMap = new HashMap();
		xmMap.put("name", "科研项目");
		xmMap.put("y", xms);
		backval.add(xmMap);
		int hjcgs = Integer.parseInt(this.getHjcgxx(params));
		Map hjcgMap = new HashMap();
		hjcgMap.put("name", "获奖成果");
		hjcgMap.put("y", hjcgs);
		backval.add(hjcgMap);
		
		return Struts2Utils.list2json(backval);
	}
	
	@Override
	public String queryGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		String zlsql ="SELECT ZLCGMC AS CGMC,DWDM,to_char(T.SQRQ,'yyyy-MM-dd') AS CGRQ, 1 as lx FROM T_KY_ZL T WHERE 1=1" +str2 +
				" AND substr(to_char(T.SQRQ,'yyyy-MM-dd'),1,4) BETWEEN "+from+" AND "+to;
		
		String zzsql ="SELECT ZZZWMC AS CGMC,DWDM,CBNY AS CGRQ, 2 as lx FROM T_KY_ZZ T WHERE 1=1" +str2+
				" AND substr(t.cbny,1,4)  BETWEEN "+from+" AND "+to;
				
		String lwsql ="SELECT LWZWMC AS CGMC,DWDM,FBNY AS CGRQ , 3 as lx FROM T_KY_LW T WHERE 1=1" +str2+
						" AND substr(T.fbny,1,4) BETWEEN "+from+" AND "+to;
		
		String xmsql ="SELECT XMMC,DWDM,LXRQ AS CGRQ, 4 as lx FROM (select lxrq,dwdm,XMMC from t_ky_zxxm union all " +
				" select TO_CHAR(lxrq,'yyyy-MM-dd') AS LXRQ,dwdm,XMMC from t_ky_hxxm) T WHERE 1=1" +str2+
				" AND substr(T.lxrq,1,4) BETWEEN "+from+" AND "+to;
		
		String hjcgsql ="SELECT HJCGMC AS XMMC,DWDM,to_char(T.hjrq,'yyyy-MM-dd') AS CGRQ, 5 as lx FROM T_KY_CGHJ T WHERE 1=1" +str2+
				" AND substr(to_char(T.hjrq,'yyyy-MM-dd'),1,4) BETWEEN "+from+" AND "+to;
		
		
		String excuteSql ="SELECT T.* FROM (SELECT T.cgmc,t.dwdm,t.cgrq,zzjg.mc as ssdw,t.lx FROM  (SELECT * FROM ("+zlsql+") UNION all ("+zzsql
		+") UNION all ("+lwsql+") UNION all ("+xmsql+") UNION all ("+hjcgsql+"))T  left join tb_xzzzjg zzjg on zzjg.dm = t.dwdm order by t.dwdm,t.lx,t.cgrq)T";
		
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(excuteSql,paramsMap);
		List<Map> queryList = (List)result.get("queryList");
		for(Map map : queryList){
			String xl = map.get("LX").toString();
			if("1".equals(xl)){
				map.put("CGLX", "专利");
			}else if("2".equals(xl)){
				map.put("CGLX","著作");
			}else if("3".equals(xl)){
				map.put("CGLX","论文");
			}else if("4".equals(xl)){
				map.put("CGLX","科研项目");
			}else if("5".equals(xl)){
				map.put("CGLX","获奖成果");
			}
			
		}
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getTimeShaftData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		String zlsql ="SELECT ZLCGMC AS CGMC,DWDM,to_char(T.SQRQ,'yyyy-MM-dd') AS CGRQ, 1 as lx FROM T_KY_ZL T WHERE 1=1" +str2 +
				" AND substr(to_char(T.SQRQ,'yyyy-MM-dd'),1,4) BETWEEN "+from+" AND "+to;
		
		String zzsql ="SELECT ZZZWMC AS CGMC,DWDM,CBNY AS CGRQ, 2 as lx FROM T_KY_ZZ T WHERE 1=1" +str2+
				" AND substr(t.cbny,1,4)  BETWEEN "+from+" AND "+to;
				
		String lwsql ="SELECT LWZWMC AS CGMC,DWDM,FBNY AS CGRQ , 3 as lx FROM T_KY_LW T WHERE 1=1" +str2+
						" AND substr(T.fbny,1,4) BETWEEN "+from+" AND "+to;
		
		String xmsql ="SELECT XMMC,DWDM,LXRQ AS CGRQ, 4 as lx FROM (select lxrq,dwdm,XMMC from t_ky_zxxm union all " +
				" select TO_CHAR(lxrq,'yyyy-MM-dd') AS LXRQ,dwdm,XMMC from t_ky_hxxm) T WHERE 1=1" +str2+
				" AND substr(T.lxrq,1,4) BETWEEN "+from+" AND "+to;
		
		String hjcgsql ="SELECT HJCGMC AS XMMC,DWDM,to_char(T.hjrq,'yyyy-MM-dd') AS CGRQ, 5 as lx FROM T_KY_CGHJ T WHERE 1=1" +str2+
				" AND substr(to_char(T.hjrq,'yyyy-MM-dd'),1,4) BETWEEN "+from+" AND "+to;
		
		
		String excuteSql ="SELECT T.* FROM (SELECT T.cgmc,t.dwdm,t.cgrq,zzjg.mc as ssdw,t.lx FROM  (SELECT * FROM ("+zlsql+") UNION all ("+zzsql
		+") UNION all ("+lwsql+") UNION all ("+xmsql+") UNION all ("+hjcgsql+"))T  left join tb_xzzzjg zzjg on zzjg.dm = t.dwdm order by t.cgrq desc)T";
		
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(excuteSql,paramsMap);
		List<Map> queryList = (List)result.get("queryList");
		for(Map map : queryList){
			String xl = map.get("LX").toString();
			if("1".equals(xl)){
				map.put("CGLX", "专利");
			}else if("2".equals(xl)){
				map.put("CGLX","著作");
			}else if("3".equals(xl)){
				map.put("CGLX","论文");
			}else if("4".equals(xl)){
				map.put("CGLX","科研项目");
			}else if("5".equals(xl)){
				map.put("CGLX","获奖成果");
			}
			
		}
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
}
