package com.jhkj.mosdc.sc.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.domain.ScConstant;
import com.jhkj.mosdc.sc.service.StudentRsbhService;
import com.jhkj.mosdc.sc.util.ChartDataTranslater;
import com.jhkj.mosdc.sc.util.TreeNode;

public class StudentRsbhServiceImpl extends ScServiceImpl implements StudentRsbhService {
	private static Map<Integer,String> TJLX = new HashMap<Integer,String>();
	private int tjzb = 2;
	static{
		TJLX.put(1,"按院系统计历年学生变化趋势");
		TJLX.put(2,"按学科分类统计历年学生变化趋势");
		TJLX.put(3,"按年龄段分类统计历年学生变化趋势");
		TJLX.put(4,"按民族分类统计历年学生变化趋势");
		TJLX.put(5,"按生源地分类统计历年学生变化趋势");
		TJLX.put(6,"按性别统计历年学生变化趋势");
	}
	@Override
	public String queryChartData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		tjzb = json.containsKey("tjzb")?Integer.parseInt(json.getString("tjzb").toString()):tjzb;
		String backval ="{success:false}";
		switch(tjzb){
			case 1:
				backval = this.getZyChartData(json);
				break;
			case 2:
				backval = this.getXkflChartData(json);
				break;
			case 3:
				backval = this.getNldChartData(json);
				break;
			case 4:
				backval = this.getMzChartData(json);
				break;
			case 5:
				backval = this.getSydChartData(json);
				break;
			case 6:
				backval = this.getSexChartData(json);
				break;
			default :
				break;
		}
		return backval;
	}
    
	
	
	
	
	String getZyChartData(JSONObject json) {
		String zzjgId = json.containsKey("zzjgId") ? json.get("zzjgId")
				.toString() : ScConstant.rootid+"";
	    TreeNode node = this.getJxNodeById(zzjgId);
	    
		String from = json.getString("from"), to = json.getString("to");
		try{
			String sql1 = "SELECT * FROM TB_JXZZJG WHERE CCLX='YX' AND SFKY=1 order by dm";
			String sql2 = "select substr(XJ.RXRQ,1,4) as DM,count(*) as MC,ZZJG.MC AS lx,zzjg.dm as zzjgdm from TB_XJDA_XJXX XJ "+
								  " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = XJ.YX_ID where substr(XJ.RXRQ,1,4) between " +from+
								  " and "+to+" {0} group by substr(XJ.RXRQ,1,4),ZZJG.MC,zzjg.dm order by zzjgdm,dm,lx";
			String str2 = " AND YX_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.getQxm()+"%')";
			if(ScConstant.cclx.YX.toString().equals(node.getCclx())){
				sql1 = "select * from TB_JXZZJG WHERE CCLX='ZY' AND FJD_ID="+zzjgId;
				sql2 = "select substr(XJ.RXRQ,1,4) as DM,count(*) as MC,ZZJG.MC AS lx,zzjg.dm as zzjgdm from TB_XJDA_XJXX XJ "+
						  " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = XJ.ZY_ID where substr(XJ.RXRQ,1,4) between " +from+
						  " and "+to+" {0} group by substr(XJ.RXRQ,1,4),ZZJG.MC,zzjg.dm order by dm,zzjgdm,lx";
				str2 = " AND ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.getQxm()+"%')";
			}else if(ScConstant.cclx.ZY.toString().equals(node.getCclx())){
				sql1 = "select * from TB_JXZZJG WHERE CCLX='ZY' AND ID="+zzjgId;
			}
			sql2 = MessageFormat.format(sql2, str2);
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			int[] years = ChartDataTranslater.getYears(Integer.parseInt(from), Integer.parseInt(to));
			List<Map> backval = ChartDataTranslater.getFhChartData(years,bzdm,result);
			return Struts2Utils.list2json(backval);
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	String getXkflChartData(JSONObject json){
		String zzjgId = json.containsKey("zzjgId") ? json.get("zzjgId")
				.toString() : ScConstant.rootid+"";
	    TreeNode node = this.getJxNodeById(zzjgId);
	    
		String from = json.getString("from"), to = json.getString("to");
		try{
			String sql1 = "select * from dm_zxbz.t_zxbz_xkmlkj";
			String sql2 = "select substr(XJ.RXRQ,1,4) as DM,count(*) as MC,ZZJG.MC AS lx from TB_XJDA_XJXX XJ "+
					  " LEFT JOIN DM_ZXBZ.t_zxbz_xkmlkj ZZJG ON ZZJG.WID = XJ.xkml_id where substr(XJ.RXRQ,1,4) between " +from+
					  " and "+to+" {0} group by substr(XJ.RXRQ,1,4),ZZJG.MC order by dm,lx";
			String str2 = " AND zy_id IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.getQxm()+"%')";
			
			sql2 = MessageFormat.format(sql2, str2);
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			int[] years = ChartDataTranslater.getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(ChartDataTranslater.getFhChartData(years,bzdm,result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	String getNldChartData(JSONObject json){
		String zzjgId = json.containsKey("zzjgId") ? json.get("zzjgId")
				.toString() : ScConstant.rootid+"";
	    TreeNode node = this.getJxNodeById(zzjgId);
	    
		String from = json.getString("from"), to = json.getString("to");
		try{
			String sql  ="select nld as lx,substr(rxrq,1,4) as dm,count(*) as mc from (select case when nl <=16 then ''0-16岁'' "+
					" when nl between 17 and 20 then ''17-20岁'' "+
					" when nl between 21 and 23 then ''21-23岁'' "+
					" when nl between 24 and 26 then ''24-26岁'' "+
					" when nl between 26 and 28 then ''26-28岁'' "+
					" when nl > 28 then ''大于28'' end as nld,t.* from ( "+
					" select ceil(months_between(sysdate, to_date(" +
					" t.csrq, ''yyyy-mm-dd hh24:mi:ss'')) / 12) as nl," +
					" t.id,yx_id,zy_id,jdxl_id,t.rxrq from tb_xjda_xjxx t where  t.csrq is not null and " +
					" substr(t.RXRQ,1,4) between " +from+ " and "+to+" {0}"+
					" )t) group by nld,substr(rxrq,1,4) order by nld";
			String sql1 = "SELECT '0-16岁' AS MC FROM DUAL UNION ALL " +
					"SELECT '17-20岁' AS MC FROM DUAL UNION ALL " +
					"SELECT '21-23岁' AS MC FROM DUAL UNION ALL " +
					"SELECT '24-26岁' AS MC FROM DUAL UNION ALL " +
					"SELECT '26-28岁' AS MC FROM DUAL UNION ALL " +
					"SELECT '大于28' AS MC FROM DUAL" ;
			String str2 = " AND t.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.getQxm()+"%')";
			
			sql = MessageFormat.format(sql, str2);
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql);
			int[] years = ChartDataTranslater.getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(ChartDataTranslater.getFhChartData(years,bzdm,result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
		}
	String getMzChartData(JSONObject json){
		String zzjgId = json.containsKey("zzjgId") ? json.get("zzjgId")
				.toString() : ScConstant.rootid+"";
	    TreeNode node = this.getJxNodeById(zzjgId);
	    
		String from = json.getString("from"), to = json.getString("to");
		try{
			String sql1 = "select MZ.DM,MZ.MC from TB_XJDA_XJXX XJXX inner JOIN DM_ZXBZ.T_ZXBZ_MZ MZ ON MZ.WID = XJXX.MZ_ID GROUP BY MZ.DM,MZ.MC";
			String sql2 = "select substr(XJ.RXRQ,1,4) as DM,count(*) as MC,ZZJG.MC AS lx from TB_XJDA_XJXX XJ "+
								  " LEFT JOIN DM_ZXBZ.T_ZXBZ_MZ ZZJG ON ZZJG.WID = XJ.MZ_ID where substr(XJ.RXRQ,1,4) between " +from+
								  " and "+to+" {0} group by substr(XJ.RXRQ,1,4),ZZJG.MC order by dm,lx";
			String str2 = " AND YX_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.getQxm()+"%')";
			if(ScConstant.cclx.YX.toString().equals(node.getCclx())){
				str2 = " AND ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.getQxm()+"%')";
			}else if(ScConstant.cclx.ZY.toString().equals(node.getCclx())){
				
			}
			sql2 = MessageFormat.format(sql2, str2);
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			int[] years = ChartDataTranslater.getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(ChartDataTranslater.getFhChartData(years,bzdm,result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	String getSydChartData(JSONObject json){
		String zzjgId = json.containsKey("zzjgId") ? json.get("zzjgId")
				.toString() : ScConstant.rootid+"";
	    TreeNode node = this.getJxNodeById(zzjgId);
		String from = json.getString("from"), to = json.getString("to");
		try{
			String sql1 = "select '省内' as mc from dual union all select '省外' as mc from dual";
			String sql2 = "select dm,count(*) as mc,lx from (select substr(XJ.RXRQ,1,4) as DM,case when zzjg.MC=''河南省'' then ''省内'' else ''省外'' end AS lx from TB_XJDA_XJXX XJ "+
					  " LEFT JOIN DM_ZXBZ.t_zxbz_xzqh ZZJG ON ZZJG.WID = XJ.sydsx_id where substr(XJ.RXRQ,1,4) between " +from+
					  " and "+to+" {0} ) group by dm,lx  order by dm,lx";
			String str2 = " AND zy_id IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.getQxm()+"%')";
			
			sql2 = MessageFormat.format(sql2, str2);
			
			System.out.println("$$$$$$$$$$$$$$$$$"+sql2+"$$$$$$$$$$$$$$$$$$$$$");
			
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			int[] years = ChartDataTranslater.getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(ChartDataTranslater.getFhChartData(years,bzdm,result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	
	
	String getSexChartData(JSONObject json){
		String zzjgId = json.containsKey("zzjgId") ? json.get("zzjgId")
				.toString() : ScConstant.rootid+"";
	    TreeNode node = this.getJxNodeById(zzjgId);
		String from = json.getString("from"), to = json.getString("to");
		
		
		try{
			String sql1 = "select mc from dm_zxbz.t_zxbz_xb";
			String sql2 = "select xb.wid,xb.mc as lx,substr(xj.rxrq,0,4) as dm,count(*) as mc from tb_xjda_xjxx xj "+ 
					"left join dm_zxbz.t_zxbz_xb xb on xb.wid = xj.xb_id "+ 
					"where substr(xj.rxrq,1,4) between '"+from+"' and '"+to+"' "+ " AND zy_id IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.getQxm()+"%')"+
					"group by xb.wid,xb.mc,substr(xj.rxrq,0,4)";
			
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			int[] years = ChartDataTranslater.getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(ChartDataTranslater.getFhChartData(years,bzdm,result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	/**
	 * 获取全系码条件sql
	 * @param zzjgId
	 * @return
	 */
	String getQxmSql(String zzjgId){
		Map node = getJxzzjgNode(zzjgId);
		
		String str2 = " AND YX_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		return str2;
	}
	
	protected Map getJxzzjgNode(String zzjgid){
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
}
