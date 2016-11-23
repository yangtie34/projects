package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.KyRyService;

public class KyRyServiceImpl extends TeacherServiceImpl implements KyRyService {
	private static Map<Integer,String> TJLX = new HashMap<Integer,String>();
	static{
		TJLX.put(1,"成果获奖人员");
		TJLX.put(2,"论文人员");
		TJLX.put(3,"著作人员");
		TJLX.put(4,"专利人员");
	}
	private String view_jzg ="SELECT JZG.*,T.LX FROM (SELECT ZGH,lx FROM ( "+
			" SELECT ZGH,4 AS LX FROM T_KY_ZL_RY GROUP BY ZGH UNION ALL "+
			" SELECT ZGH,1 AS LX FROM T_KY_CGHJ_RY UNION ALL "+
			" SELECT ZGH,2 AS LX FROM T_KY_LW_RY UNION ALL "+
			" SELECT ZGH,3 AS LX FROM T_KY_ZZ_RY ) GROUP BY ZGH,lx)T "+
			" LEFT JOIN TB_JZGXX JZG ON JZG.ZGH= T.ZGH";
	
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
	public String getXbfbByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String tjzb = json.get("tjzb").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT XB_ID,XB.MC,XB.DM,COUNT(*) ZS FROM ("+view_jzg+") XJXX " +
				"LEFT JOIN  dm_zxbz.t_zxbz_xb xb on xb.wid = xjxx.xb_id WHERE 1=1  {0} GROUP BY XB_ID,XB.MC,XB.DM";
		String str2 = " AND XJXX.YX_ID IN (SELECT DM FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%') AND XJXX.LX ="+tjzb;
		sql = MessageFormat.format(sql, str2);
		List<Map> result = baseDao.querySqlList(sql);
		Map<String,Object> backVal = new HashMap<String,Object>();
		int zs = 0,nvC = 0,nanC =0;
		for(Map map : result){
			int count = Integer.parseInt(map.get("ZS").toString());
			zs+=count;
		}
		for(Map map : result){
			if(map.get("DM")==null){
				continue;
			}
			String dm = map.get("DM").toString();
			int count = Integer.parseInt(map.get("ZS").toString());
			
			if("1".equals(dm)){
				Map<String,String> temp = new HashMap<String,String>();
				temp.put("count", count+"");
				nanC = count;
				float num= zs==0?0:(float)count/zs*100;
		        DecimalFormat df = new DecimalFormat("0.00");
				temp.put("zb", df.format(num));
				
				backVal.put("ns", temp);
			}else if("2".equals(dm)){
				Map<String,String> temp = new HashMap<String,String>();
				temp.put("count", count+"");
				nvC =  count;
				float num= zs==0?0:(float)count/zs*100;
		        DecimalFormat df = new DecimalFormat("0.00");
				temp.put("zb", df.format(num));
				
				backVal.put("nvs", temp);
			}
		}
		if(result.size()==0){
			Map<String,String> temp = new HashMap<String,String>();
			temp.put("zb", "0.00");
			temp.put("count", "0");
			
			backVal.put("nvs", temp);
			backVal.put("ns", temp);
		}else if(result.size()==1){
			Map<String,String> temp = new HashMap<String,String>();
			temp.put("zb", "0.00");
			temp.put("count", "0");
			if("1".equals(result.get(0).get("DM").toString())){
				backVal.put("nvs", temp);
			}else{
				backVal.put("ns", temp);
			}
		}
		
		String nnvbl = this.getNnvBl((float)nanC, (float)nvC);
		backVal.put("bl", nnvbl);
		backVal.put("text", node.get("mc")+" 数据");
		return Struts2Utils.map2json(backVal);
	}
	
	@Override
	public String getNldfbByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		String tjzb = json.get("tjzb").toString();
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="select nld,count(*) as rs from (select case when nl <=18 then ''0-22岁'' "+
							" when nl between 23 and 29 then ''23-29岁'' "+
							" when nl between 30 and 40 then ''30-40岁'' "+
							" when nl between 41 and 50 then ''41-50岁'' "+
							" when nl between 51 and 60 then ''51-60岁'' "+
							" when nl > 60 then ''大于60'' end as nld,t.* from ( "+
							" select ceil(months_between(sysdate, to_date(" +
							" t.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12) as nl," +
							" t.id,yx_id from ("+view_jzg+") t where  t.csrq is not null {0}"+
							" )t) group by nld order by nld";
		
		String str2 = " AND t.YX_ID IN (SELECT DM FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%') AND t.LX ="+tjzb;
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("NLD"));
			obj.put("name", "年龄段");
			obj.put("value", obj.get("RS"));
		}
		return Struts2Utils.list2json(result);
	}
	@Override
	public String getXlzcByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		String tjzb = json.get("tjzb").toString();
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT NVL(XB.MC,''未维护'') AS MC,COUNT(*) ZS FROM ("+view_jzg+") XJXX "+
				" LEFT JOIN  dm_zxbz.T_ZXBZ_WHCD xb on xb.wid = xjxx.WHC_ID WHERE 1=1 {0}   GROUP BY XB.MC ORDER BY XB.MC";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT DM FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%') AND XJXX.LX ="+tjzb;
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("name", obj.get("MC"));
			obj.put("y", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getKyryZc(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		String tjzb = json.get("tjzb").toString();
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT NVL(XB.MC,''未维护'') AS MC,COUNT(*) ZS FROM ("+view_jzg+") XJXX "+
				" LEFT JOIN  dm_zxbz.T_ZXBZ_ZYJSZW xb on xb.wid = xjxx.ZYJSZW_ID WHERE 1=1 {0}   GROUP BY XB.MC ORDER BY XB.MC";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT DM FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%') AND XJXX.LX ="+tjzb;
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("value", obj.get("ZS"));
			obj.put("name", "职称组成");
		}
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getCountByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		String tjzb = json.get("tjzb").toString();
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT COUNT(*) ZS FROM ({0}) XJXX WHERE 1=1 {1} ";
		String str2 = " AND xjxx.YX_ID IN (SELECT DM FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%') AND XJXX.LX ="+tjzb;
		sql = MessageFormat.format(sql,view_jzg,str2);
		int count = baseDao.querySqlCount(sql);
		return count+"";
	}
}
