package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.TeacherService;

public class TeacherServiceImpl extends ScServiceImpl implements
		TeacherService {
	private List<Map> tjlbs = new ArrayList<Map>();
	@Override
	public String getCountsByTjlb(String params) {
		// TODO Auto-generated method stub
		return null;
	}
	protected Map getXzzzjgNode(String zzjgid){
		Map backVal = new HashMap();
		String sql ="Select * from tb_xzzzjg where id ="+zzjgid;
		List<Map> result = baseDao.querySqlList(sql);
		if(result.size()!=0){
			backVal.put("id", result.get(0).get("ID"));
			backVal.put("qxm",result.get(0).get("QXM"));
			backVal.put("mc",result.get(0).get("MC"));
		}
		
		return backVal;
	}
	@Override
	public String getXbfbByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		if(tjlbs.size()==0){
//			tjlbs = this.getXsTjlb();
		}
//		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():tjlbs.get(0).get("DM").toString();
//		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():tjlbs.get(0).get("MC").toString();
		
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT XB_ID,XB.MC,XB.DM,COUNT(*) ZS FROM TB_JZGXX XJXX " +
				"LEFT JOIN  dm_zxbz.t_zxbz_xb xb on xb.wid = xjxx.xb_id WHERE 1=1  {0} GROUP BY XB_ID,XB.MC,XB.DM";
//		String str1 = " AND XJXX.JDXL_ID = "+jdxldm;
		String str2 = " AND XJXX.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql, /*str1,*/str2);
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
		backVal.put("text", node.get("mc")+" 数据");
		return Struts2Utils.map2json(backVal);
	}

	@Override
	public String getNldfbByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
//		if(xslbs.size()==0){
//			xslbs = this.getXsTjlb();
//		}
//		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
//		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="select nld,count(*) as rs from (select case when nl <=18 then ''0-22岁'' "+
							" when nl between 23 and 29 then ''23-29岁'' "+
							" when nl between 30 and 40 then ''30-40岁'' "+
							" when nl between 41 and 50 then ''41-50岁'' "+
							" when nl between 51 and 60 then ''51-60岁'' "+
							" when nl > 60 then ''大于60'' end as nld,t.* from ( "+
							" select ceil(months_between(sysdate, to_date(" +
							" t.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12) as nl," +
							" t.id,yx_id from TB_JZGXX t where  t.csrq is not null {0}"+
							" )t) group by nld order by nld";
		
//		String str1 = " AND t.JDXL_ID = "+jdxldm;
		String str2 = " AND t.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,/* str1,*/str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("NLD"));
			obj.put("name", "年龄段");
			obj.put("value", obj.get("RS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getMzzcByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
//		if(xslbs.size()==0){
//			xslbs = this.getXsTjlb();
//		}
//		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
//		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT MZ_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_JZGXX XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_mz xb on xb.wid = xjxx.mz_id WHERE 1=1 {0}  GROUP BY MZ_ID,XB.MC,XB.DM ORDER BY MZ_ID";
		
//		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,/* str1,*/str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", "民族");
			obj.put("value", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getZzmmByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
//		if(xslbs.size()==0){
//			xslbs = this.getXsTjlb();
//		}
//		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
//		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT ZZMM_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_JZGXX XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_zzmm xb on xb.wid = xjxx.zzmm_id WHERE 1=1 {0}  GROUP BY ZZMM_ID,XB.MC,XB.DM ORDER BY ZZMM_ID";
		
//		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql, /*str1,*/str2);
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
	public String getLydByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
//		if(xslbs.size()==0){
//			xslbs = this.getXsTjlb();
//		}
//		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
//		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		
		String sql =" SELECT XZQH.MC,XZQH.YWM,NVL(T.ZS,0) AS ZS FROM DM_ZXBZ.T_ZXBZ_XZQH XZQH LEFT JOIN (" +
				" select xzqh.ls,xzqh.mc,xzqh.ywm,count(*) as zs from tb_jzgxx xjxx" +
				" left join dm_zxbz.t_zxbz_xzqh xzqh on xjxx.csd_id = xzqh.dm where 1=1 {0} group by xzqh.ls,xzqh.mc,xzqh.ywm" +
				") T ON XZQH.WID = T.LS WHERE XZQH.CC IS NULL";
//		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,/*str1,*/str2);
		List<Map> result = baseDao.querySqlList(sql);
		String sqlcount = "SELECT COUNT(*) AS ZS FROM TB_JZGXX XJXX WHERE 1=1 {0} ";
		sqlcount = MessageFormat.format(sqlcount,/*str1,*/str2);
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
	        map.put("LBMC", "");
	        map.put("QXM", qxm);
	        map.put("ZB", df.format(num));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getRyxkByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
//		if(xslbs.size()==0){
//			xslbs = this.getXsTjlb();
//		}
//		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
//		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT * FROM (SELECT XK_ID,NVL(XB.MC,''未维护'') AS MC,XB.DM,COUNT(*) ZS FROM TB_JZGXX XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_xkmlkj xb on xb.wid = xjxx.XK_ID WHERE 1=1 {0}  GROUP BY XK_ID,XB.MC,XB.DM ORDER BY XK_ID) WHERE MC !=''未维护'' ORDER BY ZS";
//		String str1 = " AND xjxx.JDXL_ID = "+jdxldm;
		String str2 = " AND xjxx.yx_id IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,/*str1,*/str2);
		List<Map> result = baseDao.querySqlList(sql);
		
		String sqlcount = "SELECT COUNT(*) AS ZS FROM TB_JZGXX XJXX WHERE 1=1 {0} ";
		sqlcount = MessageFormat.format(sqlcount,/*str1,*/str2);
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
			backval.put("xy", node.get("mc"));
			backval.put("xk", result.get(0).get("MC"));
			
			backval.put("qxxk", "");
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
			backval.put("xy", node.get("mc"));
			backval.put("xk", result.get(result.size()-1).get("MC"));
			
			backval.put("qxxk", "");
		}
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getXwzcByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
//		if(xslbs.size()==0){
//			xslbs = this.getXsTjlb();
//		}
//		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
//		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT NVL(XB.MC,''未维护'') AS MC,XB.xwid,COUNT(*) ZS FROM TB_JZGXX XJXX "+
				" LEFT JOIN  (SELECT xw.wid,xw2.mc,xw2.wid as xwid FROM dm_zxbz.T_ZXBZ_XW xw,dm_zxbz.t_zxbz_xw xw2 where xw.ls = xw2.wid) xb on xb.wid = xjxx.xwd_ID WHERE 1=1 {0}  GROUP BY XB.MC,XB.xwid ORDER BY XB.MC";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", "就读学位");
			obj.put("value", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getXlzcByTjlb(String params) {
		JSONObject json = JSONObject.fromObject(params);
//		if(xslbs.size()==0){
//			xslbs = this.getXsTjlb();
//		}
//		String jdxldm = json.containsKey("xslb")?json.get("xslb").toString():xslbs.get(0).get("DM").toString();
//		String lbmc = json.containsKey("lbmc")?json.get("lbmc").toString():xslbs.get(0).get("MC").toString();
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
	public String getRyxkByTjlb2(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getXzzzjgNode(zzjgId);
		String sql  ="SELECT NVL(XB.MC,''未维护'') AS MC,COUNT(*) ZS FROM tb_jzgxx XJXX "+
				" LEFT JOIN  dm_zxbz.t_zxbz_xkmlkj xb on xb.wid = xjxx.XK_ID WHERE 1=1 {0}   GROUP BY XB.MC ORDER BY XB.MC";
		
		String str2 = " AND xjxx.YX_ID IN (SELECT ID FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		sql = MessageFormat.format(sql,str2);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("name", obj.get("MC"));
			obj.put("y", obj.get("ZS"));
		}
		return Struts2Utils.list2json(result);
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
}
