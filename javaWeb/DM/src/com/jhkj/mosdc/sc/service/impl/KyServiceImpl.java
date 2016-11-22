package com.jhkj.mosdc.sc.service.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.KyService;
import com.jhkj.mosdc.sc.service.impl.BzksWzsServiceImpl.XsmdExport;
import com.jhkj.mosdc.sc.util.TreeNode;

public class KyServiceImpl extends ScServiceImpl implements KyService {
	private static String JX = "进行";
	private static String WC = "完成";
	// 获取JDBC方式执行方法
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public class XsmdExport{
		private String xmbh;
		private String xmmc;
		private String cddw;
		private String zcr;
		private String xmlb;
		private String xdbm;
		private String stime;
		private String etime;
		private String jf;
		private String bz;
		public String getXmbh() {
			return xmbh;
		}
		public void setXmbh(String xmbh) {
			this.xmbh = xmbh;
		}
		public String getXmmc() {
			return xmmc;
		}
		public void setXmmc(String xmmc) {
			this.xmmc = xmmc;
		}
		public String getCddw() {
			return cddw;
		}
		public void setCddw(String cddw) {
			this.cddw = cddw;
		}
		public String getZcr() {
			return zcr;
		}
		public void setZcr(String zcr) {
			this.zcr = zcr;
		}
		public String getXmlb() {
			return xmlb;
		}
		public void setXmlb(String xmlb) {
			this.xmlb = xmlb;
		}
		public String getXdbm() {
			return xdbm;
		}
		public void setXdbm(String xdbm) {
			this.xdbm = xdbm;
		}
		public String getStime() {
			return stime;
		}
		public void setStime(String stime) {
			this.stime = stime;
		}
		public String getEtime() {
			return etime;
		}
		public void setEtime(String etime) {
			this.etime = etime;
		}
		public String getJf() {
			return jf;
		}
		public void setJf(String jf) {
			this.jf = jf;
		}
		public String getBz() {
			return bz;
		}
		public void setBz(String bz) {
			this.bz = bz;
		}
	}
	@Override
	public String getKyjgChartData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		String sql="SELECT JGMC FROM T_KY_JG WHERE SSDWDM ="+zzjgid;
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("name", obj.get("JGMC"));
			obj.put("y", 1);
		}
		Map backval = new HashMap();
		backval.put("queryList", result);
		backval.put("count", result.size());
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getKyryChartData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		String sql ="select jgmc as mc,count(*) as zs from t_ky_jg jg " +
				"inner join  t_ky_jg_ry ry on ry.jgdm = jg.jgdm and ry.jgdm in (select dm from tb_xzzzjg where qxm like ' "+tn.getQxm()+"%')group by jgmc";
		String kyjg ="SELECT JGMC FROM T_KY_JG";
		List<Map> bzdm = baseDao.querySqlList(kyjg);
		List<Map> result = baseDao.querySqlList(sql);
		for(Map temp : bzdm){
			String jgmc = temp.get("JGMC").toString();
			temp.put("field", jgmc);
			temp.put("name", "科研人员数");
			for(Map obj : result){
				if(jgmc.equals(obj.get("MC").toString())){
					temp.put("value", obj.get("ZS"));
				}else{
					temp.put("value", 0);
				}
			}
		}
		
		Map backval = new HashMap();
		backval.put("queryList", bzdm);
		backval.put("count", result.size());
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getKyxmChartData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		String sql ="select zzjg.mc as dw,dwlb.mc as dwlb,count(1) as zs,zt.mc as zt from (" +
				"select xmzxztdm,dwdm from t_ky_zxxm union all select xmzxztdm,dwdm from t_ky_hxxm) zxxm "+ 
				"left join dm_zxbz.t_zxbz_xmzxzt zt on zt.dm = zxxm.xmzxztdm "+
				"left join tb_xzzzjg zzjg on zzjg.dm = zxxm.dwdm and zxxm.dwdm in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
				" left join dm_zxbz.t_zxbz_dwlb dwlb on zzjg.lbm = dwlb.dm where zzjg.mc is not null "+
				"group by zzjg.mc,dwlb.mc,zt.mc order by dwlb";
		
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("DW"));
			obj.put("name", obj.get("ZT"));
			obj.put("value", obj.get("ZS"));
		}
		
		Map backval = new HashMap();
		String countsql1 = "SELECT SUM(ZS) FROM ("+sql+") WHERE ZT='"+JX+"' GROUP BY ZT";
		String countsql2 = "SELECT SUM(ZS) FROM ("+sql+") WHERE ZT='"+WC+"'  GROUP BY ZT";
		backval.put("count1", baseDao.querySqlCount(countsql1));
		backval.put("count2", baseDao.querySqlCount(countsql2));
		backval.put("queryList", result);
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getKyjfChartData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		String sql ="select zzjg.mc,sum(bks) as zs from T_KY_XM_JFBR JF "+
			" LEFT JOIN (select XMBH,xmzxztdm,dwdm from t_ky_zxxm union all " +
			" select XMBH,xmzxztdm,dwdm from t_ky_hxxm) XM ON XM.XMBH = JF.XMBH "+
			" inner JOIN TB_XZZZJG ZZJG ON ZZJG.DM = XM.DWDM and" +
			" XM.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
			" group by zzjg.mc";
		
		List<Map> result = baseDao.querySqlList(sql);
		for(Map obj : result){
			obj.put("field", obj.get("MC"));
			obj.put("name", "拨入经费");
			obj.put("value", obj.get("ZS"));
		}
		
		Map backval = new HashMap();
		String countsql1 = "SELECT SUM(ZS) FROM ("+sql+")";
		backval.put("count", baseDao.querySqlCount(countsql1));
		backval.put("queryList", result);
		return Struts2Utils.map2json(backval);
	}
	@Override
	public String getKyhdChartData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		String kyjg ="SELECT JGMC FROM T_KY_JG WHERE SSDWDM ="+zzjgid;
		List<Map> bzdm = baseDao.querySqlList(kyjg);
		for(Map temp : bzdm){
			String jgmc = temp.get("JGMC").toString();
			temp.put("field", jgmc);
			temp.put("name", "学术活动场次");
			temp.put("value", 0);
		}
		
		Map backval = new HashMap();
		backval.put("queryList", bzdm);
		backval.put("count", 0);
		return Struts2Utils.map2json(backval);
	}
	@Override
	public String getKycgChartData(String params) {
		Map backval = new HashMap();// 复合图形展示对象
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		// 查询取得的项目总数
		String sql ="SELECT SUM(ZS) FROM (" +
				"select count(*) as zs from T_KY_ZZ zz where " +
				" zz.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
				" union all "+
				" select count(*) as zs from T_KY_ZL zl where " +
				" zl.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
				" union all "+
				" select count(*) as zs from T_KY_LW lw where " +
				" lw.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
				")";
		
		int xmzs =  baseDao.querySqlCount(sql);
		backval.put("count", xmzs);
		
		String lwsql ="select lw.dwdm,jg.mc ,count(*) as zs from t_ky_lw lw " +
				"left join tb_xzzzjg jg on jg.dm = lw.dwdm and jg.mc is not null  and " +
				" lw.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
				"group by dwdm,jg.mc";
		String zzsql ="select zz.dwdm,jg.mc ,count(*) as zs from t_ky_zz zz " +
				"left join tb_xzzzjg jg on jg.dm = zz.dwdm and " +
				" zz.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
				"group by dwdm,jg.mc";
		String zlsql ="select zl.dwdm,jg.mc ,count(*) as zs from t_ky_zl zl " +
				"left join tb_xzzzjg jg on jg.dm = zl.dwdm and " +
				" zl.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
				" group by dwdm,jg.mc";
		
		List<Map> lwList = baseDao.querySqlList(lwsql);
		List<Map> zzList = baseDao.querySqlList(zzsql);
		List<Map> zlList = baseDao.querySqlList(zlsql);
		Map zzMap = new HashMap();
		for(Map zzmap : zzList){
			if(zzmap.get("MC")==null){
				continue;
			}
			String dwmc = zzmap.get("MC").toString();
			zzMap.put(dwmc, zzmap.get("ZS"));
		}
		Map zlMap = new HashMap();
		for(Map zlmap : zlList){
			if(zlmap.get("MC")==null){
				continue;
			}
			String dwmc = zlmap.get("MC").toString();
			zlMap.put(dwmc, zlmap.get("ZS"));
		}
		
		for(Map lwmap : lwList){
			if(lwmap.get("MC")==null){
				continue;
			}
			String dwmc = lwmap.get("MC").toString();
			int zls = zlMap.containsKey(dwmc)?Integer.parseInt(zlMap.get(dwmc).toString()):0;
			int zzs = zzMap.containsKey(dwmc)?Integer.parseInt(zzMap.get(dwmc).toString()):0;
			lwmap.put("lw", lwmap.get("ZS"));
			lwmap.put("zl",zls);
			lwmap.put("zz",zzs);
			
		}
		List<String> dws = new ArrayList<String>();
		List<String> zls = new ArrayList<String>();
		List<String> zzs = new ArrayList<String>();
		List<String> lws = new ArrayList<String>();
		List<String> pjs = new ArrayList<String>();
		int zlcount = 0 ,zzcount = 0,lwcount=0;
		
		for(Map lwmap : lwList){
			if(lwmap.get("MC")==null){
				continue;
			}
			String zl = lwmap.get("zl").toString();
			String zz = lwmap.get("zz").toString();
			String lw = lwmap.get("lw").toString();
			
			dws.add(lwmap.get("MC").toString());
			zls.add(zl);
			zzs.add(zz);
			lws.add(lw);
			int czs = Integer.parseInt(zl)+Integer.parseInt(zz)+Integer.parseInt(lw);
			DecimalFormat df = new DecimalFormat("0.00");
			float num= (float)czs/3;
			pjs.add(df.format(num));
			
			zlcount+=Integer.parseInt(zl);
			zzcount+=Integer.parseInt(zz);
			lwcount+=Integer.parseInt(lw);
		}
		backval.put("zls", zls);
		backval.put("dws", dws);
		backval.put("zzs", zzs);
		backval.put("lws", lws);
		backval.put("pjs", pjs);
		backval.put("zzcount", zzcount);
		backval.put("lwcount", lwcount);
		backval.put("zlcount", zlcount);
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getZyxms(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		// 查询取得的项目总数
		String sql ="select zzjg.mc as dw,dwlb.mc as dwlb,count(1) as zs,zt.mc as zt from (" +
				"select xmzxztdm,dwdm from t_ky_zxxm union all select xmzxztdm,dwdm from t_ky_hxxm) zxxm "+ 
				"left join dm_zxbz.t_zxbz_xmzxzt zt on zt.dm = zxxm.xmzxztdm "+
				"left join tb_xzzzjg zzjg on zzjg.dm = zxxm.dwdm and zxxm.dwdm in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')"+
				" left join dm_zxbz.t_zxbz_dwlb dwlb on zzjg.lbm = dwlb.dm where zzjg.mc is not null "+
				"group by zzjg.mc,dwlb.mc,zt.mc order by dwlb";
		String countsql1 = "SELECT SUM(ZS) FROM ("+sql+") WHERE ZT='"+JX+"' GROUP BY ZT";
		int xmzs =  baseDao.querySqlCount(countsql1);
		
		return  xmzs+"";
	}
	
	@Override
	public String getHbjf(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		String sql ="select sum(bks) as zs from T_KY_XM_JFBR JF "+
				" LEFT JOIN (select XMBH,xmzxztdm,dwdm from t_ky_zxxm union all " +
				" select XMBH,xmzxztdm,dwdm from t_ky_hxxm) XM ON XM.XMBH = JF.XMBH "+
				" inner JOIN TB_XZZZJG ZZJG ON ZZJG.DM = XM.DWDM and" +
				" XM.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')";
			
			List<Map> list = baseDao.querySqlList(sql);
			float brjf = 0l;
			if(list.get(0)==null){
				brjf = list.size()==0?0l:Float.parseFloat(list.get(0).get("ZS").toString());
			}
		return brjf+"";
	}
	
	@Override
	public String getJfzc(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		String sql ="select nvl(sum(zcs),0) as zs from T_KY_XM_JFZC JF "+
				" LEFT JOIN (select XMBH,xmzxztdm,dwdm from t_ky_zxxm union all " +
				" select XMBH,xmzxztdm,dwdm from t_ky_hxxm) XM ON XM.XMBH = JF.XMBH "+
				" inner JOIN TB_XZZZJG ZZJG ON ZZJG.DM = XM.DWDM and" +
				" XM.DWDM in  (select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%')";
			
			List<Map> list = baseDao.querySqlList(sql);
			if(list.size()==0){
				return "0";
			}else{
				float brjf = list.get(0)==null || "".equals(list.get(0))?0l:Float.parseFloat(list.get(0).get("ZS").toString());
				return brjf+"";
			}
	}
	
	@Override
	public String getKyjfSyChartData(String params) {
		Map backval = new HashMap();// 复合图形展示对象
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		String brsql ="select zzjg.mc,sum(bks) as zs from T_KY_XM_JFBR jfbr left join ("+
					" select xmbh,dwdm from t_ky_zxxm union all select xmbh,dwdm from t_ky_hxxm"+
					" ) xm on xm.xmbh = jfbr.xmbh left join tb_xzzzjg zzjg on zzjg.dm = xm.dwdm "+
					" where jfbr.xmbh is not null and zzjg.dm in ( select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%') group by zzjg.mc";
		String zcsql ="select zzjg.mc,sum(ZCS) as zs from T_KY_XM_JFZC jfbr left join ("+
				" select xmbh,dwdm from t_ky_zxxm union all select xmbh,dwdm from t_ky_hxxm"+
				" ) xm on xm.xmbh = jfbr.xmbh left join tb_xzzzjg zzjg on zzjg.dm = xm.dwdm "+
				" where jfbr.xmbh is not null and zzjg.dm in ( select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%') group by zzjg.mc";
		String ptsql ="";// TODO
		String glsql ="";// TODO
		
		
		List<Map> zcList = baseDao.querySqlList(zcsql);
		Map zcMap = new HashMap();
		for(Map temp : zcList){
			String dwmc = temp.get("MC").toString();
			zcMap.put(dwmc, temp.get("ZS"));
		}
		
		List<Map> brList = baseDao.querySqlList(brsql);
		List<String> dws = new ArrayList<String>();
		List<Float> brs = new ArrayList<Float>();
		List<Float> zcs = new ArrayList<Float>();
		List<Float> gls = new ArrayList<Float>();
		List<Float> pts = new ArrayList<Float>();
		for(Map temp : brList){
			String dwmc = temp.get("MC").toString();
			dws.add(dwmc);
			temp.put("zcjf", zcMap.get(dwmc));
			temp.put("gljf", 0);
			temp.put("ptjf", 0);
			String zc = zcMap.get(dwmc)==null? "0": zcMap.get(dwmc).toString();
			brs.add(Float.parseFloat(temp.get("ZS").toString()));
			zcs.add(Float.parseFloat(zc));
			gls.add(Float.parseFloat("0"));
			pts.add(Float.parseFloat("0"));
		}
		
		
		backval.put("brs", brs);
		backval.put("zcs", zcs);
		backval.put("gls", gls);
		backval.put("pts", pts);
		backval.put("dws", dws);
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getKyjfPmChartData(String params) {
		Map backval = new HashMap();
		JSONObject json = JSONObject.fromObject(params);
		int cxlb = Integer.parseInt(json.get("cxlb").toString());
		String lbmc =  json.get("lbmc").toString();
		String zzjgid = json.get("zzjgId").toString();
		TreeNode tn = this.getXzNodeById(zzjgid);
		String sql ="select jfbr.bks,xm.xmmc,zzjg.mc from T_KY_XM_JFBR jfbr left join ( "+
						" select xmbh,dwdm,xmmc from t_ky_zxxm union all select xmbh,dwdm,xmmc from t_ky_hxxm"+
						" ) xm on xm.xmbh = jfbr.xmbh left join tb_xzzzjg zzjg on zzjg.dm = xm.dwdm "+
						" where jfbr.xmbh is not null and zzjg.dm in " +
						"( select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%') ";
		switch(cxlb){
			case 1:
				//划拨经费 ，按科研机构排名
				String jgsql ="SELECT * FROM (SELECT T1.*,ROWNUM FROM (" +
						"SELECT SUM(BKS) AS BKS,MC FROM ("+sql+") GROUP BY MC order by bks desc" +
						")T1 ) WHERE ROWNUM BETWEEN 1 AND 10";
				
				List<Map> jgpm = baseDao.querySqlList(jgsql);
				backval.put("jgpm", jgpm);
				
				// 统计图数据
				String chartSql ="SELECT SUM(BKS) AS BKS,MC FROM ("+sql+") GROUP BY MC order by bks desc";
				List<Map> chartData = baseDao.querySqlList(chartSql);
				for(Map temp : chartData){
					temp.put("name",temp.get("MC"));
					temp.put("y",temp.get("BKS"));
				}
				backval.put("chartData", chartData);
				
				// 划拨经费，按研究项目排名
				String xmsql="SELECT * FROM (SELECT T1.*,ROWNUM FROM (" +
						"SELECT SUM(BKS) AS BKS,XMMC AS MC FROM ("+sql+") GROUP BY XMMC order by bks desc"+
						")T1 ) WHERE ROWNUM BETWEEN 1 AND 10";
				
				List<Map> xmpm = baseDao.querySqlList(xmsql);
				backval.put("xmpm", xmpm);
				
				// 划拨经费，按负责人排名
				backval.put("rypm", new ArrayList());
				break;
			case 2:// 配套经费
				backval.put("xmpm",  new ArrayList());
				backval.put("jgpm", new ArrayList());
				backval.put("rypm", new ArrayList());
				backval.put("chartData", new ArrayList());
				break;
			case 3:// 管理经费
				backval.put("xmpm",  new ArrayList());
				backval.put("jgpm", new ArrayList());
				backval.put("rypm", new ArrayList());
				backval.put("chartData", new ArrayList());
				break;
			case 4:
				sql ="select jfbr.ZCS,xm.xmmc,zzjg.mc from T_KY_XM_JFZC jfbr left join ( "+
						" select xmbh,dwdm,xmmc from t_ky_zxxm union all select xmbh,dwdm,xmmc from t_ky_hxxm"+
						" ) xm on xm.xmbh = jfbr.xmbh left join tb_xzzzjg zzjg on zzjg.dm = xm.dwdm "+
						" where jfbr.xmbh is not null and zzjg.dm in " +
						"( select dm from tb_xzzzjg where qxm like '"+tn.getQxm()+"%') ";
				// 支出经费 ，按科研机构排名
				String zcsql ="SELECT * FROM (SELECT T1.*,ROWNUM FROM (" +
						"SELECT SUM(ZCS) AS BKS,MC FROM ("+sql+") GROUP BY MC order by bks desc" +
						")T1 ) WHERE ROWNUM BETWEEN 1 AND 10";
				List<Map> zcjgpm = baseDao.querySqlList(zcsql);
				backval.put("jgpm", zcjgpm);
				// 支出经费，按研究项目排名
				
				String zcxmsql="SELECT * FROM (SELECT T1.*,ROWNUM FROM (" +
						"SELECT SUM(ZCS) AS BKS,XMMC AS MC FROM ("+sql+") GROUP BY XMMC order by bks desc"+
						")T1 ) WHERE ROWNUM BETWEEN 1 AND 10";
				
				List<Map> zcxmpm = baseDao.querySqlList(zcxmsql);
				backval.put("xmpm", zcxmpm);
				backval.put("rypm", new ArrayList());
				backval.put("chartData", new ArrayList());
				break;
			default :
				break;
		}
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getKyjfLyChartData(String params) {
		String sql ="SELECT XMJFLYDW,SUM(BKS) AS BKS FROM T_KY_XM_JFBR GROUP BY XMJFLYDW";
		List<Map> result = baseDao.querySqlList(sql);
		for(Map temp : result){
			temp.put("name", temp.get("XMJFLYDW"));
			temp.put("y", temp.get("BKS"));
		}
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getKyjfQxChartData(String params) {
		String sql ="SELECT ZCKM,SUM(ZCS) AS BKS FROM T_KY_XM_JFBR GROUP BY ZCKM";
		List<Map> result = baseDao.querySqlList(sql);
		for(Map temp : result){
			temp.put("name", temp.get("ZCKM"));
			temp.put("y", temp.get("BKS"));
		}
		return Struts2Utils.list2json(result);
	}

	@Override
	public String getKyxmlbData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String to =  json.get("to").toString();
		String from =  json.get("from").toString();
		String projectId = json.get("projectId").toString();
		//这里应该获取三个字段，并且group by()   项目类别：CATEGORY    项目级别：LEVEL_CODE    项目状态：STATE_CODE
		String sql = "select distinct t.name_,t.code_,t.code_type, count(*) counts from T_RES_PROJECT trp "
				+ " left join t_code t on trp.level_code = t.code_ "
				+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
				+ " where t.code_type = 'RES_PROJECT_LEVEL_CODE' and substr(trp.START_TIME, 0, 4) between '"+from+"' and '"+to+"' "
				+ " and trp.project_id = '"+projectId+"' "
				+ " group by t.name_,t.code_,t.code_type";
		List<Map> result = baseDao.querySqlList(sql);//参照学生政治分布：bzksService?getZzmmByXslb
		for(Map obj : result){
			obj.put("name", obj.get("NAME_"));
			obj.put("counts", obj.get("COUNTS"));
			obj.put("levelCode", obj.get("LEVEL_CODE"));
		}
		return JSONArray.fromObject(result).toString();
	}
	
	@Override
	public String getKyxmJbData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String to =  json.get("to").toString();
		String from =  json.get("from").toString();
		String projectId = json.get("projectId").toString();
		String sql = "select distinct t.name_,t.code_,t.code_type, count(*) counts from T_RES_PROJECT trp "
				+ " left join t_code t on trp.RANK_CODE = t.code_  "
				+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
				+ " where t.code_type = 'RES_PROJECT_RANK_CODE' and substr(trp.START_TIME, 0, 4) between '"+from+"' and '"+to+"'"
				+ " and trp.project_id = '"+projectId+"' "
				+ " group by t.name_,t.code_,t.code_type";
		List<Map> result = baseDao.querySqlList(sql);
		return JSONArray.fromObject(result).toString();
	}
	
	@Override
	public String getKyxmStateData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String to =  json.get("to").toString();
		String from =  json.get("from").toString();
		String projectId = json.get("projectId").toString();
		String sql = "select distinct t.name_,t.code_,t.code_type, count(*) counts from T_RES_PROJECT trp "
				+ " left join t_code t on trp.STATE_CODE = t.code_  "
				+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
				+ " where t.code_type = 'RES_PROJECT_STATE_CODE' and substr(trp.START_TIME, 0, 4) between '"+from+"' and '"+to+"'"
				+ " and trp.project_id = '"+projectId+"' "
				+ " group by t.name_,t.code_,t.code_type";
		List<Map> result = baseDao.querySqlList(sql);
		return JSONArray.fromObject(result).toString();
	}
	/***
	 * 历年项目数量变化趋势
	 */
	@Override
	public String getKyxmLnqsData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String code =  json.get("code").toString();//code[0,1,2]代表 levelCode  rankCode  stateCode
		code = code.substring(1,code.length()-1);//01,02,03
		String[] sub = code.split(",");//[01,02,03]
		for(int i =0;i<sub.length;i++){
			if(sub[i].equals("0")){
				sub[i]="";
			}
		}
		String codeType = json.get("codeType").toString();
		String from =  json.get("from").toString();
		String to =  json.get("to").toString();
		String projectId = json.get("projectId").toString();
		String sql = "";
		sql = "select count(*) counts, nvl(substr(trp.START_TIME, 0, 4), '未维护') startTime "
				+ " from T_RES_PROJECT trp "
				+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
				+ " left join t_code t on t.code_ = trp.level_code "
				+ " where  substr(trp.START_TIME, 0, 4) between '"+from+"' and '"+to+"' "
				+ " and trp.project_id = '"+projectId+"' "
				+ " and (trp.LEVEL_CODE like '%"+sub[0]+"%' or trp.LEVEL_CODE is null) "
				+ " and (trp.RANK_CODE like '%"+sub[1]+"%' or trp.RANK_CODE is null) "
				+ " and (trp.STATE_CODE like '%"+sub[2]+"%' or trp.STATE_CODE is null) "
				+ " and t.code_type like '%PROJECT%' "
				+ " group by nvl(substr(trp.START_TIME, 0, 4), '未维护') "
				+ " order by nvl(substr(trp.START_TIME, 0, 4), '未维护')";
		List<Map> result = baseDao.querySqlList(sql);
		return JSONArray.fromObject(result).toString();
	}
	/***
	 * 学院历年趋势参数
	 */
	@Override
	public String getKyxmXylnqsData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String code =  json.get("code").toString();//code[0,1,2]代表 levelCode  rankCode  stateCode
		code = code.substring(1,code.length()-1);//01,02,03
		String[] sub = code.split(",");//[01,02,03]
		for(int i =0;i<sub.length;i++){
			if(sub[i].equals("0")){
				sub[i]="";
			}
		}
		String codeType = json.get("codeType").toString();//RES_PROJECT_LEVEL_CODE
		String from =  json.get("from").toString();
		String to =  json.get("to").toString();
		String projectId = json.get("projectId").toString();
		String sql = "";
		sql = "select count(*) counts, "
				+ " (CASE WHEN zz.mc is null THEN '未维护' "
				+ " else zz.mc  end) mc , "
				+ " (CASE WHEN zz.dm is null THEN '未维护' "
				+ " else zz.dm  end) dm , "
				+ " nvl(substr(trp.START_TIME, 0, 4), '未维护') startTime "
				+ " from t_res_project trp left join tb_xzzzjg zz on zz.dm = trp.dept_id "
				+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
				+ " left join t_code t on t.code_ = trp.level_code "
				+ " where substr(trp.START_TIME, 0, 4) between '"+from+"' and '"+to+"' and trp.project_id = '"+projectId+"' "
				+ " and (trp.LEVEL_CODE like '%"+sub[0]+"%' or trp.LEVEL_CODE is null) "
				+ " and (trp.RANK_CODE like '%"+sub[1]+"%' or trp.RANK_CODE is null) "
				+ " and (trp.STATE_CODE like '%"+sub[2]+"%' or trp.STATE_CODE is null) "
						+ " and t.code_type like '%PROJECT%' "
				+ " group by zz.mc, zz.dm, nvl(substr(trp.START_TIME, 0, 4), '未维护') "
				+ " order by nvl(substr(trp.START_TIME, 0, 4), '未维护')";
		List<Map> map = baseDao.querySqlList(sql);
		return JSONArray.fromObject(map).toString();
	}
	/**
	 * 这个需要分页--历年数据(详细信息弹窗)
	 */
	@Override
	public String getKyLnxmData(String params){
		JSONObject json = JSONObject.fromObject(params);
		Integer start = json.getInt("start");
		Integer limit = json.getInt("limit");
		PageParam pageParam = new PageParam(start, limit);
		String str = json.get("params").toString();
		JSONObject jsonParams = JSONObject.fromObject(str);
		String code =  jsonParams.get("code").toString();//code[0,1,2]代表 levelCode  rankCode  stateCode
		code = code.substring(1,code.length()-1);//01,02,03
		String[] subCode = code.split(",");//[01,02,03]
		for(int i =0;i<subCode.length;i++){
			if(subCode[i].equals("0")){
				subCode[i]="";
			}
		}
		String levelCode = jsonParams.get("levelCode").toString();
		String startTime = jsonParams.get("startTime").toString();
		String mc = jsonParams.get("mc").toString();
		String pName = jsonParams.get("pName").toString();
		String compere = jsonParams.get("compere").toString();
		String fund = jsonParams.get("fund").toString();
		String sub = Arrays.toString(levelCode.split(",")).toString();//[01,02,03]
		sub = sub.substring(1,sub.length()-1);//01,02,03
		String projectId = jsonParams.get("projectId").toString();
		//TODO 这个sub 跟 sunCode 什么区别
		String sql = "select  rownum as rn,trp.id,t.name_ tname,zz.mc,trp.compere,trp.name_ pname,trp.issued_dept,trp.start_time,trp.end_time,trp.fund,trp.remark from T_RES_PROJECT trp "
					+ " left join t_code t on t.code_=trp.level_code "
					+ " left join tb_xzzzjg zz on zz.dm = trp.dept_id "
					+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
					+ " where t.code_type like '%PROJECT%' "
					+ " and (trp.LEVEL_CODE like '%"+subCode[0]+"%' or trp.LEVEL_CODE is null) "
					+ " and (trp.RANK_CODE like '%"+subCode[1]+"%' or trp.RANK_CODE is null) "
					+ " and (trp.STATE_CODE like '%"+subCode[2]+"%' or trp.STATE_CODE is null) "
					+ " and substr(trp.START_TIME,0,4) = '"+startTime+"' "
					+ " and trp.project_id = '"+projectId+"'";
		//这个是查询按钮调用的
		String where1="";
		String where2="";
		String where3="";
		String where4="";
		if(!mc.equals("")){
			where1 = " and t1.mc like '%"+mc+"%' ";
		}
		if(!pName.equals("")){
			where2 = " and t1.pname like '%"+pName+"%' ";
		}
		if(!compere.equals("")){
			where3 = " and t1.compere like '%"+compere+"%' ";
		}
		if(!fund.equals("")){
			where4 = " and t1.fund like '%"+fund+"%' ";
		}
		sql="select t1.* from ("+sql+") t1 where 1=1" + where1 + where2 + where3 + where4;
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql, paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	/**
	 * 这个需要分页--学院历年数据(弹窗)
	 */
	@Override
	public String queryGridContent4xq(String params){
		JSONObject json=JSONObject.fromObject(params);
		Integer start=json.getInt("start");
		Integer limit=json.getInt("limit");

		PageParam pageParam=new PageParam(start, limit);
		String str = json.get("params").toString();
		JSONObject jsonParams = JSONObject.fromObject(str);
		String levelCode = jsonParams.get("levelCode").toString();
		String codeType = jsonParams.get("codeType").toString();
		String startTime = jsonParams.get("startTime").toString();
		String xyName = jsonParams.get("xyName").toString();//学院名称
		String from =  jsonParams.get("from").toString();
		String to =  jsonParams.get("to").toString();
		String queryMc = jsonParams.get("mc").toString();//点击搜索时候传过来的学院名称
		String pName = jsonParams.get("pName").toString();
		String compere = jsonParams.get("compere").toString();
		String fund = jsonParams.get("fund").toString();
//		String sub = Arrays.toString(levelCode.split(",")).toString();//[01,02,03]
//		sub = sub.substring(1,sub.length()-1);//01,02,03
		String code =  jsonParams.get("code").toString();//code[0,1,2]代表 levelCode  rankCode  stateCode
		code = code.substring(1,code.length()-1);//01,02,03
		String[] subCode = code.split(",");//[01,02,03]
		for(int i =0;i<subCode.length;i++){
			if(subCode[i].equals("0")){
				subCode[i]="";
			}
		}
		String projectId = jsonParams.get("projectId").toString();
		String sql ="";
		sql = " select trp.id, t.name_ tname, zz.mc, trp.compere, trp.name_ pname, trp.issued_dept, trp.start_time, trp.end_time, "
			+ " trp.fund, trp.remark, t.code_type, trp.level_code "
			+ " from t_res_project trp "
			+ " left join tb_xzzzjg zz on zz.dm = trp.dept_id "
			+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
			+ " left join t_code t on t.code_ = trp.level_code "
			+ " where substr(trp.START_TIME, 0, 4) = '"+startTime+"' and trp.project_id = '"+projectId+"' "
			+ " and (trp.LEVEL_CODE like '%"+subCode[0]+"%' or trp.LEVEL_CODE is null) "
			+ " and (trp.RANK_CODE like '%"+subCode[1]+"%' or trp.RANK_CODE is null) "
			+ " and (trp.STATE_CODE like '%"+subCode[2]+"%' or trp.STATE_CODE is null) "
			+ " and zz.mc = '"+xyName+"'"
			+ " and t.code_type like '%PROJECT%' "//RES_PROJECT_LEVEL_CODE RES_PROJECT_RANK_CODE,RES_PROJECT_STATE_CODE
			//+ " and t.code_type='"+codeType+"' "
			+ " order by nvl(substr(trp.START_TIME, 0, 4), '未维护')";
		//这个是查询按钮调用的
		String where1="";
		String where2="";
		String where3="";
		String where4="";
		if(!queryMc.equals("")){
			where1 = " and t1.mc like '%" + queryMc + "%' ";
		}
		if(!pName.equals("")){
			where2 = " and t1.pname like '%" + pName + "%' ";
		}
		if(!compere.equals("")){
			where3 = " and t1.compere like '%" + compere + "%' ";
		}
		if(!fund.equals("")){
			where4 = " and t1.fund like '%" + fund + "%' ";
		}
		sql="select t1.* from ("+sql+") t1 where 1=1" + where1 + where2 + where3 + where4;
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(sql, paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	/***
	 * 这是历年趋势的导出功能
	 */
	@Override
	public HSSFWorkbook getWzsMdExport(String params) {
		try {
			if(params.equals(new String(params.getBytes("ISO-8859-1"), "ISO-8859-1"))){
				params=  new String(params.getBytes("ISO-8859-1"),"UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		JSONObject json = JSONObject.fromObject(params);
		String str = json.get("params").toString();
		JSONObject jsonParams = JSONObject.fromObject(str);
		String levelCode = jsonParams.get("levelCode").toString();
		String startTime = jsonParams.get("startTime").toString();
		String code =  jsonParams.get("code").toString();//code[0,1,2]代表 levelCode  rankCode  stateCode
		String queryMc = jsonParams.get("mc").toString();//点击搜索时候传过来的学院名称
		String pName = jsonParams.get("pName").toString();
		String compere = jsonParams.get("compere").toString();
		String fund = jsonParams.get("fund").toString();
		String lnGrid = jsonParams.get("lnGrid").toString();//json.containsKey("lnGrid")
		code = code.substring(1,code.length()-1);//01,02,03
		String[] subCode = code.split(",");//[01,02,03]
		for(int i =0;i<subCode.length;i++){
			if(subCode[i].equals("0")){
				subCode[i]="";
			}
		}
		String projectId = jsonParams.get("projectId").toString();
		String sql = "";
		//历史趋势的导出
		if(jsonParams.containsKey("lnGrid")){//查看是否包含这个字段
			if(lnGrid.equals("lnGrid")){
				sql = "select  rownum as rn,trp.id,t.name_ tname,zz.mc,trp.compere,trp.name_ pname,trp.issued_dept,trp.start_time,trp.end_time,trp.fund,trp.remark from T_RES_PROJECT trp "
						+ " left join t_code t on t.code_=trp.level_code "
						+ " left join tb_xzzzjg zz on zz.dm = trp.dept_id "
						+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
						+ " where t.code_type like '%RES_PROJECT_LEVEL_CODE%' "
						+ " and trp.LEVEL_CODE like '%"+subCode[0]+"%' "
						+ " and trp.RANK_CODE like '%"+subCode[1]+"%' "
						+ " and trp.STATE_CODE like '%"+subCode[2]+"%' "
						+ " and substr(trp.START_TIME,0,4) = '"+startTime+"' "
						+ " and trp.project_id = '"+projectId+"'";
			}else if(lnGrid.equals("xylnGrid")){//这是学院历史趋势的导出
				String codeType = jsonParams.get("codeType").toString();
				String xyName = jsonParams.get("xyName").toString();//学院名称
				String sub = Arrays.toString(levelCode.split(",")).toString();//[01,02,03]
				sub = sub.substring(1,sub.length()-1);//01,02,03
				String from =  jsonParams.get("from").toString();
				String to =  jsonParams.get("to").toString();
				sql = " select trp.id, t.name_ tname, zz.mc, trp.compere, trp.name_ pname, trp.issued_dept, trp.start_time, trp.end_time, "
						+ " trp.fund, trp.remark, t.code_type, trp.level_code "
						+ " from t_res_project trp "
						+ " left join tb_xzzzjg zz on zz.dm = trp.dept_id "
						+ " left join t_code_subject tcs on tcs.code_ = trp.project_id "
						+ " left join t_code t on t.code_ = trp.level_code "
						+ " where substr(trp.START_TIME, 0, 4) between '"+from+"' and '"+to+"' and trp.project_id = '"+projectId+"' "
						+ " and (trp.LEVEL_CODE like '%"+subCode[0]+"%' or trp.LEVEL_CODE is null) "
						+ " and (trp.RANK_CODE like '%"+subCode[1]+"%' or trp.RANK_CODE is null) "
						+ " and (trp.STATE_CODE like '%"+subCode[2]+"%' or trp.STATE_CODE is null) "
						+ " and zz.mc = '"+xyName+"'"
						+ " and t.code_type like '%PROJECT%' "//RES_PROJECT_LEVEL_CODE RES_PROJECT_RANK_CODE,RES_PROJECT_STATE_CODE
						//+ " and t.code_type='"+codeType+"' "
						+ " order by nvl(substr(trp.START_TIME, 0, 4), '未维护')";
			}
		}else{
			System.out.println("不包含这个字段，请查看传过来的参数信息！");
		}
		//这个是查询按钮调用的
		String where1="";
		String where2="";
		String where3="";
		String where4="";
		if(!queryMc.equals("")){
			where1 = " and t1.mc like '%"+queryMc+"%' ";
		}
		if(!pName.equals("")){
			where2 = " and t1.pname like '%"+pName+"%' ";
		}
		if(!compere.equals("")){
			where3 = " and t1.compere like '%"+compere+"%' ";
		}
		if(!fund.equals("")){
			where4 = " and t1.fund like '%"+fund+"%' ";
		}
		sql="select t1.* from ("+sql+") t1 where 1=1" + where1 + where2 + where3 + where4;
		List<Map> yxList = baseDao.querySqlList(sql);
		String yxmc = yxList.get(0).get("MC").toString();//代表左下角sheet的Name
		List<Object> values = new ArrayList<Object>();
		for(Map xsMap : yxList){
			String xmbh = xsMap.get("ID")==null?"":xsMap.get("ID").toString();
			String xmmc = xsMap.get("PNAME")==null?"":xsMap.get("PNAME").toString();
			String cddw = xsMap.get("MC")==null?"":xsMap.get("MC").toString();
			String zcr =  xsMap.get("COMPERE")==null?"":xsMap.get("COMPERE").toString();
			String xmlb = xsMap.get("TNAME")==null?"":xsMap.get("TNAME").toString();
			String xdbm =  xsMap.get("ISSUED_DEPT")==null?"":xsMap.get("ISSUED_DEPT").toString();
			String stime = xsMap.get("START_TIME")==null ?"":xsMap.get("START_TIME").toString();
			String etime = xsMap.get("END_TIME")==null ?"":xsMap.get("END_TIME").toString();
			String jf = xsMap.get("FUND")==null ?"":xsMap.get("FUND").toString();
			String bz = xsMap.get("REMARK")==null ?"":xsMap.get("REMARK").toString();
			XsmdExport export = new XsmdExport();
			export.setXmbh(xmbh);
			export.setXmmc(xmmc);
			export.setCddw(cddw);
			export.setZcr(zcr);
			export.setXmlb(xmlb);
			export.setXdbm(xdbm);
			export.setStime(stime);
			export.setEtime(etime);
			export.setJf(jf);
			export.setBz(bz);
			values.add(export);
		}
		String[] strs = {"项目编号","项目名称","承担单位","主持人员","项目类别","下达部门","开始时间","结束时间","经费数额","备注"};
		HSSFWorkbook workbook = ExportUtil.getHSSFWorkbook(strs, yxmc, values);
		return workbook;
	}
}
