package com.jhkj.mosdc.sc.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.domain.ScConstant;
import com.jhkj.mosdc.sc.service.KyXmService;
import com.jhkj.mosdc.sc.util.TreeNode;

public class KyXmServiceImpl extends TeacherBhtjServiceImpl implements KyXmService {
	private static Map<Integer,String> TJLX = new HashMap<Integer,String>();
	private int tjzb = 1;
	static{
		TJLX.put(1,"项目状态");
		TJLX.put(2,"项目来源");
		TJLX.put(3,"项目类别");
	}
	
	private String view_xm="select xmbh,dwdm,xmmc,lxrq, TO_CHAR(jxrq,'yyyy-MM-dd') as jxrq,xmzxztdm,xmlydm,xmlbdm,shjjxydm,xklydm,1 as lx from t_ky_zxxm  "+
						" union all select xmbh,dwdm,xmmc,TO_CHAR(lxrq,'yyyy-MM-dd') AS LXRQ,"+
						" TO_CHAR(jxrq,'yyyy-MM-dd') as jxrq,xmzxztdm,xmlydm, xmlbdm,shjjxydm,xklydm,2 as lx from t_ky_hxxm";
	@Override
	public String getTjlx(String params) {
		List<Map> backval = new ArrayList<Map>();
		Iterator it = TJLX.keySet().iterator();
		while(it.hasNext()){
			int key = Integer.parseInt(it.next().toString());
			Map temp = new HashMap();
			temp.put("id", key);
			temp.put("mc", TJLX.get(key));
			backval.add(temp);
		}
		return Struts2Utils.list2json(backval);
	}
	@Override
	public String getChartData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		tjzb = json.containsKey("tjzb")?Integer.parseInt(json.getString("tjzb").toString()):2;
		String from = json.getString("from"),to = json.getString("to");
		boolean isEqu= from.equals(to)?true:false;
		String backval ="{success:false}";
		switch(tjzb){
			case 1:
				backval = isEqu?this.getCountByXmzt(params):this.getCountByXmztNfd(params);
				break;
			case 2:
				backval = isEqu?this.getCountByXmly(params):this.getCountByXmlyNfd(params);
				break;
			case 3:
				backval = isEqu?this.getCountByXmfl(params):this.getCountByXmflNfd(params);
				break;
			default :
				break;
		}
		return backval;
	}

	@Override
	public String queryGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.get("zzjgId").toString();
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String str2 = getWhereSql(zzjgId);
		Map paramsMap = Utils4Service.packageParams(params);
		String excuteSql ="SELECT T.* FROM (SELECT XMBH,XMMC,LXRQ,JXRQ,ZZJG.MC AS SSDW,LY.MC AS XMLY,ZXZT.MC AS XMZT,"+
						" LX FROM ("+view_xm+
						" ) T LEFT JOIN TB_XZZZJG ZZJG ON ZZJG.DM = T.DWDM "+
						" LEFT JOIN DM_ZXBZ.T_ZXBZ_XMLY LY ON LY.DM = T.XMLYDM"+
						" LEFT JOIN DM_ZXBZ.T_ZXBZ_XMZXZT ZXZT ON ZXZT.DM = T.XMZXZTDM WHERE "+
						" SUBSTR(T.LXRQ,1,4) between "+from+" and "+to+str2+" order by lxrq,xmzt,ssdw) T ";
		
		Map result = baseDao.queryTableContentBySQL(excuteSql,paramsMap);
		List<Map> queryList = (List)result.get("queryList");
		for(Map map : queryList){
			String xl = map.get("LX").toString();
			if("1".equals(xl)){
				map.put("XMLX", "纵向项目");
			}else if("2".equals(xl)){
				map.put("XMLX","横向项目");
			}
			
		}
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getCountByXmfl(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		try{
			String sql1 = "select 1 as dm,'纵向项目' as mc from dual" +
					" union all select 2 as dm,'横向项目' as mc from dual";
			String sql2 = "SELECT ZXZT.MC AS LX,substr(t.lxrq,6,2) as dm,COUNT(*) AS MC FROM ("+view_xm+
							" ) T LEFT JOIN ("+sql1+") ZXZT ON ZXZT.DM = T.LX"+ 
							" WHERE SUBSTR(T.LXRQ,1,4) = "+from+str2+" GROUP BY ZXZT.MC,substr(t.lxrq,6,2)";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			return Struts2Utils.list2json(getFhChartData(ScConstant.months,bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	
	@Override
	public String getCountByXmflNfd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		try{
			String sql1 = "select 1 as dm,'横向项目' as mc from dual" +
					" union all select 2 as dm,'纵向项目' as mc from dual";
			String sql2 = "SELECT ZXZT.MC AS LX,substr(t.lxrq,1,4) as dm,COUNT(*) AS MC FROM ("+view_xm+
							" ) T LEFT JOIN ("+sql1+") ZXZT ON ZXZT.DM = T.LX"+ 
							" WHERE SUBSTR(T.LXRQ,1,4) between "+from+" and "+to+str2+" GROUP BY ZXZT.MC,substr(t.lxrq,1,4)";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			int[] years = getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(getFhChartData(years , bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	
	@Override
	public String getCountByXmly(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		try{
			String sql1 = "select ZXZT.MC from ("+view_xm+") t LEFT JOIN dm_zxbz.t_zxbz_xmly ZXZT  ON ZXZT.DM = T.XMLYDM where zxzt.mc is not null GROUP BY ZXZT.MC";
			String sql2 = "SELECT ZXZT.MC AS LX,substr(t.lxrq,6,2) as dm,COUNT(*) AS MC FROM ("+view_xm+
							" ) T LEFT JOIN dm_zxbz.t_zxbz_xmly ZXZT ON ZXZT.DM = T.XMLYDM"+ 
							" WHERE SUBSTR(T.LXRQ,1,4) = "+from+str2+" GROUP BY ZXZT.MC,substr(t.lxrq,6,2)";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			return Struts2Utils.list2json(getFhChartData(ScConstant.months,bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	@Override
	public String getCountByXmlyNfd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		try{
			String sql1 = "select ZXZT.MC from ("+view_xm+") t LEFT JOIN dm_zxbz.t_zxbz_xmly ZXZT  ON ZXZT.DM = T.XMLYDM where zxzt.mc is not null GROUP BY ZXZT.MC";
			String sql2 = "SELECT ZXZT.MC AS LX,substr(t.lxrq,1,4) as dm,COUNT(*) AS MC FROM ("+view_xm+
							" ) T LEFT JOIN dm_zxbz.t_zxbz_xmly ZXZT  ON ZXZT.DM = T.XMLYDM"+ 
							" WHERE SUBSTR(T.LXRQ,1,4) between "+from+" and "+to+str2+" GROUP BY ZXZT.MC,substr(t.lxrq,1,4)";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			int[] years = getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(getFhChartData(years,bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	@Override
	public String getCountByXmzt(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		try{
			String sql1 = "select dm,MC from dm_zxbz.t_zxbz_xmzxzt";
			String sql2 = "SELECT ZXZT.MC AS LX,substr(t.lxrq,6,2) as dm,COUNT(*) AS MC FROM ("+view_xm+
							" ) T LEFT JOIN dm_zxbz.t_zxbz_xmzxzt ZXZT  ON ZXZT.DM = T.XMZXZTDM"+ 
							" WHERE SUBSTR(T.LXRQ,1,4) = "+from+str2+" GROUP BY ZXZT.MC,substr(t.lxrq,6,2)";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			return Struts2Utils.list2json(getFhChartData(ScConstant.months,bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	@Override
	public String getCountByXmztNfd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		String str2 = getWhereSql(zzjgId);
		try{
			String sql1 = "select dm,MC from dm_zxbz.t_zxbz_xmzxzt";
			String sql2 = "SELECT ZXZT.MC AS LX,substr(t.lxrq,1,4) as dm,COUNT(*) AS MC FROM ("+view_xm+
							" ) T LEFT JOIN dm_zxbz.t_zxbz_xmzxzt ZXZT  ON ZXZT.DM = T.XMZXZTDM"+ 
							" WHERE SUBSTR(T.LXRQ,1,4) between "+from+" and "+to+str2+" GROUP BY ZXZT.MC,substr(t.lxrq,1,4)";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			int[] years = getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(getFhChartData(years,bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	void filterSet(String name,List<Map> result){
		for(Map map :result){
			int dm = map.containsKey("DM")?Integer.parseInt(map.get("DM").toString()):0;
			map.put("name", name);
			map.put("field", dm);
			map.put("value", map.get("MC"));
		}
	}
	
	String getWhereSql(String zzjgid){
		TreeNode tn = this.getXzNodeById(zzjgid);
		String str2 = " AND t.dwdm IN (SELECT DM FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+tn.getQxm()+"%')";
		return str2;
	}
}
