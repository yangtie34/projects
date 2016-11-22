package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.BzksService;
import com.jhkj.mosdc.sc.util.BzksUtils;

public class BzksServiceImpl extends BaseServiceImpl implements BzksService {
	private List<Map> xslbs = new ArrayList<Map>();
	@Override
	public List<Map> getXsTjlb() {
		String sql ="SELECT DM,MC,DYXW,DYXL FROM DM_ZXBZ.T_SYS_XSTJLB ORDER BY PXH";
		List<Map> result = baseDao.querySqlList(sql);
		return result;
	}

	private static String wheresql = " AND XJXX.XJZT_ID = 1000000000000101 AND XJXX.XSZT_ID = 1";
	@SuppressWarnings("rawtypes")
	@Override
	public String getCountsByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String str2 = " AND ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		List<Map> temp = this.getXsTjlb();
		
		for(Map xslb : temp){
			String jdxldm = xslb.get("DM").toString();
			String sql  ="SELECT COUNT(*) AS ZS FROM TB_XJDA_XJXX XJXX WHERE JDXL_ID ="+jdxldm+" {0} {1} {2}";
			sql = MessageFormat.format(sql,str2,BzksUtils.getAndWhereSql(),wheresql);
			int count = baseDao.querySqlCount(sql);
			xslb.put("count", count);
		}
		return Struts2Utils.list2json(temp);
	}

	@Override
	public String getXbfbByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String sql  ="SELECT XB_ID,XB.MC,XB.DM,COUNT(*) ZS FROM TB_XJDA_XJXX XJXX " +
				"LEFT JOIN  dm_zxbz.t_zxbz_xb xb on xb.wid = xjxx.xb_id WHERE XB.DM IS NOT NULL  {0} {1} {2} {3} GROUP BY XB_ID,XB.MC,XB.DM";
		String str1 = " AND XJXX.JDXL_ID = "+jdxldm;
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql, str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		Map<String,Object> backVal = new HashMap<String,Object>();
		int zs = 0,nvC = 0,nanC =0;
		for(Map map : result){
			int count = Integer.parseInt(map.get("ZS").toString());
			zs+=count;
		}
		for(Map map : result){
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
				nvC =  count;
				float num= zs==0?0:(float)count/zs*100;
		        DecimalFormat df = new DecimalFormat("0.00");
				temp.put("zb", df.format(num));
				temp.put("count", count+"");
				
				backVal.put("nvs", temp);
			}
		}
		if(result.size()==0){
			Map<String,String> temp = new HashMap<String,String>();
			temp.put("zb", "0.00");
			temp.put("count", "0");
			
			backVal.put("nvs", temp);
			backVal.put("ns", temp);
		}
		
		String nnvbl = this.getNnvBl((float)nanC, (float)nvC);
		backVal.put("bl", nnvbl);
		backVal.put("text", node.get("mc")+"( "+lbmc+" ) 数据");
		return Struts2Utils.map2json(backVal);
	}

	@Override
	public String getNldfbByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String sql  ="select nld,count(*) as rs from (select case when nl <=16 then ''0-16岁'' "+
							" when nl between 17 and 20 then ''17-20岁'' "+
							" when nl between 21 and 23 then ''21-23岁'' "+
							" when nl between 24 and 26 then ''24-26岁'' "+
							" when nl between 26 and 28 then ''26-28岁'' "+
							" when nl > 28 then ''大于28'' end as nld,T.* from ( "+
							" select ceil(months_between(sysdate, to_date(" +
							" XJXX.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12) as nl," +
							" XJXX.id,yx_id,zy_id,jdxl_id from tb_xjda_xjxx XJXX where  XJXX.csrq is not null {0} {1} {2} {3} "+
							" )t) group by nld order by nld";
		
		String str1 = " AND XJXX.JDXL_ID = "+jdxldm;
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql, str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("NLD"));
			obj.put("name", "年龄段");
			obj.put("value", obj.get("RS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getMzzcByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String sql  ="SELECT MZ_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_XJDA_XJXX XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_mz xb on xb.wid = xjxx.mz_id WHERE 1=1 {0} {1} {2} {3} GROUP BY MZ_ID,XB.MC,XB.DM ORDER BY MZ_ID";
		
		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql, str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", "民族");
			obj.put("value", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getZzmmByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String sql  ="SELECT ZZMM_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_XJDA_XJXX XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_zzmm xb on xb.wid = xjxx.zzmm_id WHERE 1=1 {0} {1} {2} {3} GROUP BY ZZMM_ID,XB.MC,XB.DM ORDER BY ZZMM_ID";
		
		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql, str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("name", obj.get("MC"));
			obj.put("y", obj.get("ZS"));
			
			obj.remove("ZZMM_ID");
			obj.remove("DM");
			obj.remove("MC");
			obj.remove("ZS");
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getLydByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		
		String sql =" SELECT XZQH.MC,XZQH.YWM,NVL(T.ZS,0) AS ZS FROM DM_ZXBZ.T_ZXBZ_XZQH XZQH LEFT JOIN (" +
				" select xzqh.ls,xzqh.mc,xzqh.ywm,count(*) as zs from tb_xjda_xjxx xjxx" +
				" left join dm_zxbz.t_zxbz_xzqh xzqh on xjxx.sydsx_id = xzqh.dm where 1=1 {0} {1} {2} {3} group by xzqh.ls,xzqh.mc,xzqh.ywm" +
				") T ON XZQH.WID = T.LS WHERE XZQH.CC IS NULL";
		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		String sqlcount = "SELECT COUNT(*) AS ZS FROM TB_XJDA_XJXX XJXX WHERE 1=1 {0} {1} {2} {3}";
		sqlcount = MessageFormat.format(sqlcount,str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		int count = baseDao.querySqlCount(sqlcount);
		String xy = node.get("mc").toString();
		for(Object obj : result){
			Map map = (Map) obj;
			String qxm = map.get("YWM")==null?"":map.get("YWM").toString();
			String mc = map.get("MC")==null?"":map.get("MC").toString();
			int zs =  Integer.parseInt(map.get("ZS").toString());
			float num= count==0?0:(float)zs/count*100;
	        DecimalFormat df = new DecimalFormat("0.00");
	        
	        map.put("XY",xy);
	        map.put("LBMC", lbmc);
	        map.put("QXM", qxm);
	        map.put("ZB", df.format(num));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getRyxkByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String sql  ="SELECT * FROM (SELECT XKML_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_XJDA_XJXX XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_xkmlkj xb on xb.wid = xjxx.XKML_ID WHERE 1=1 {0} {1} {2} {3} GROUP BY XKML_ID,XB.MC,XB.DM ORDER BY XKML_ID) WHERE MC !=''未维护'' ORDER BY ZS";
		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		
		String sqlcount = "SELECT COUNT(*) AS ZS FROM TB_XJDA_XJXX XJXX WHERE 1=1 {0} {1} {2} {3}";
		sqlcount = MessageFormat.format(sqlcount,str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		int count = baseDao.querySqlCount(sqlcount);
		
		Map<String,Object> backval = new HashMap<String,Object>();
		if(result.size()==0){
			return Struts2Utils.map2json(backval);
		}else if (result.size()==1){
			Map<String,Object> top = new HashMap<String,Object>();
			int rs = Integer.parseInt(result.get(0).get("ZS").toString());
			top.put("count", rs);
			float num= count==0?0:(float)rs/count*100;
	        DecimalFormat df = new DecimalFormat("0.000");
			top.put("zb", df.format(num));
			top.put("lb", result.get(0).get("MC") );
			
			Map<String,Object> bottom = new HashMap<String,Object>();
			bottom.put("count", 0);
			bottom.put("zb", 0.000);
			bottom.put("lb", "无");
			
			backval.put("top", top);
			backval.put("bottom", bottom);
			backval.put("xy", node.get("mc")+"("+lbmc+")");
			backval.put("xk", result.get(0).get("MC"));
			
			backval.put("qxxk", "师范类");
		}else{
			DecimalFormat df = new DecimalFormat("0.000");
			Map<String,Object> top = new HashMap<String,Object>();
			int toprs  = Integer.parseInt(result.get(result.size()-1).get("ZS").toString());
			top.put("count",toprs);
			float topzb= count==0?0:(float)toprs/count*100;
			top.put("zb", df.format(topzb));
			top.put("lb", result.get(result.size()-1).get("MC") );
			
			Map<String,Object> bottom = new HashMap<String,Object>();
			int rs = Integer.parseInt(result.get(0).get("ZS").toString());
			bottom.put("count", rs);
			float num= count==0?0:(float)rs/count*100;
	        
	        bottom.put("zb", df.format(num));
	        
			bottom.put("lb", result.get(0).get("MC") );
			
			backval.put("top", top);
			backval.put("bottom", bottom);
			backval.put("xy", node.get("mc")+"("+lbmc+")");
			backval.put("xk", result.get(result.size()-1).get("MC"));
			
			backval.put("qxxk", "师范类");
		}
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getXwzcByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String sql  ="SELECT JDXW_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_XJDA_XJXX XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_XW xb on xb.wid = xjxx.JDXW_ID WHERE 1=1 {0} {1} {2} GROUP BY JDXW_ID,XB.MC,XB.DM ORDER BY JDXW_ID";
		
		String str2 = " AND xjxx.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", "就读学位");
			obj.put("value", obj.get("ZS"));
		}
//		System.out.println(result);
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getXlzcByXslb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String sql  ="SELECT JDXL_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_XJDA_XJXX XJXX "+
				" LEFT JOIN  dm_zxbz.T_ZXBZ_WHCD xb on xb.wid = xjxx.JDXL_ID WHERE 1=1 {0} {1} {2}  GROUP BY JDXL_ID,XB.MC,XB.DM ORDER BY JDXL_ID";
		
		String str2 = " AND xjxx.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("name", obj.get("MC"));
			obj.put("y", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getXyRsdb(String params) {
		String sql ="select yx_id,zzjg.mc as yxmc,xjxx.jdxl_id,whcd.mc as xlmc,count(*) as zs from tb_xjda_xjxx xjxx "+
				" left join tb_jxzzjg zzjg on zzjg.id = xjxx.yx_id "+
				" left join dm_zxbz.t_zxbz_whcd whcd on whcd.wid = xjxx.jdxl_id WHERE 1=1 {0} {1} "+
				" group by xjxx.yx_id,zzjg.mc,xjxx.jdxl_id,whcd.mc order by yx_id,whcd.mc";
		sql = MessageFormat.format(sql, BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("YXMC"));
			obj.put("name", obj.get("XLMC"));
			obj.put("value", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}

	protected Map getZzjgNode(String zzjgid){
		Map backVal = new HashMap();
		String sql ="Select * from tb_jxzzjg where id ="+zzjgid;
		List<Map> result = baseDao.querySqlList(sql);
		if(result.size()!=0){
			backVal.put("id", result.get(0).get("ID"));
			backVal.put("qxm",result.get(0).get("QXM"));
			backVal.put("mc",result.get(0).get("MC"));
		}
		
		return backVal;
	}
	/**
	 * 获取男女比例
	 * @param v1
	 * @param v2
	 * @return
	 */
	public String getNnvBl(float v1,float v2){
		if(v1==0&&v2==0){
			return "0:0";
		}else if(v1==0&&v2!=0){
			return "0:1";
		}else if(v1!=0&&v2==0){
			return "1:0";
		}else{
			if(v1>=v2){
				float num=(float)v1/v2;
				int nvbl = (int)Math.ceil(num);
				return nvbl+":1";
			}else{
				float num=(float)v2/v1;
				int nvbl = (int)Math.ceil(num);
				return "1:"+nvbl;
			}
		}
	}
	@Override
	public String getRyxkzc(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(xslbs.size()==0){
			xslbs = this.getXsTjlb();
		}
		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String sql  ="SELECT XKML_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_XJDA_XJXX XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_xkmlkj xb on xb.wid = xjxx.xkml_id WHERE 1=1 {0} {1} {2} {3} GROUP BY XKML_ID,XB.MC,XB.DM ORDER BY XKML_ID";
		
		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql, str1,str2,BzksUtils.getAndWhereSql(),wheresql);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("name", obj.get("MC"));
			obj.put("y", obj.get("ZS"));
			
			obj.remove("XKML_ID");
			obj.remove("DM");
			obj.remove("MC");
			obj.remove("ZS");
			
		}
		/*for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", "人员学科");
			obj.put("value", obj.get("ZS"));
			
		}*/
//		System.out.println(result);
		return Struts2Utils.list2json(result);
	}
}
