package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.newoutput.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.YktSituationService;

public class YktSituationServiceImpl extends BaseServiceImpl implements
		YktSituationService {
	private float rsjxfje = 0.00f;// 日生均消费金额。
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public void saveMonthXfmx() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -14);
		String from =df.format(cal.getTime()),to=df.format(new Date());
		String dropsql ="drop table tb_ykt_xfmx_log";
		String dropsql2 ="drop table tb_ykt_mjmx_tsg_log";
		baseDao.update(dropsql);
		baseDao.update(dropsql2);
		String sql ="create table tb_ykt_xfmx_log as select * from tb_ykt_xfmx where xfsj between '"+from+"' and '"+to+"'";
		String sql2 ="create table tb_ykt_mjmx_tsg_log as select * from tb_ykt_mjmx_tsg where sksj between '"+from+"' and '"+to+"'";
		baseDao.update(sql);
		baseDao.update(sql2);
	}
	@Override
	public String getRsjXfbs(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.containsKey("fromDate")?json.getString("fromDate"):"2014-05-01";
		String end = json.containsKey("toDate")?json.getString("toDate"):df.format(new Date());
		
		Date date =  new Date();
		String tableName ="tb_ykt_xfmx";
		if(json.containsKey("init")){
			tableName = "tb_ykt_xfmx_log";
		}
		String sql = "select round(sum(sjxfbs)/count(*),2) as rsjxfbs,round(sum(sjxfje)/count(*),2) as rsjxfje from ("
				+ "select xfrq,round(sum(bs)/count(*),2) as sjxfbs,round(sum(je)/count(*),2) as sjxfje from (select ryId,xfrq,count(*) as bs,sum(xfje) je from "
				+ "(select substr(t.xfsj,0,10) as xfrq,t.* from "+tableName+" t where t.xfsj between '"+start+"' and '"+end+"' ) "
				+ "group by ryId,xfrq ) group by xfrq)";
		
		List result = baseDao.querySqlList(sql);
		Map map = null;
		if(result.size()!=0){
			map = (Map)result.get(0);
			rsjxfje = Float.parseFloat(map.get("RSJXFJE")==null?"0.0":map.get("RSJXFJE").toString());
		}else{
			map.put("RSJXFBS","--");
			map.put("RSJXFJE","--");
		}
		Date date1 =  new Date();
		String sql1 = "select xb_id,xbdm,round(sum(sjxfbs)/count(*),2) as rsjxfbs,round(sum(sjxfje)/count(*),2) as rsjxfje from ( "
				+ "select xfrq,xb_id,xbdm,round(sum(bs)/count(*),2) as sjxfbs,round(sum(je)/count(*),2) as sjxfje from "
				+ "(select ryId,xfrq,xb_id,xbdm,count(*) as bs,sum(xfje) je from (select substr(t.xfsj,0,10) as xfrq,t.*,xjxx.xb_id,bzdm.dm as xbdm from "+tableName+" t" +
						" inner join tb_xjda_xjxx xjxx on xjxx.id = t.ryid "
				+ "left join tc_xxbzdmjg bzdm on bzdm.id = xjxx.xb_id  where t.xfsj between '"+start+"' and '"+end+"' ) group by ryId,xfrq,xb_id,xbdm ) group by xfrq,xb_id,xbdm) group by xb_id,xbdm";
		List temp = baseDao.querySqlList(sql1);
		for(Object obj : temp){
			Map tempMap = (Map)obj;
			String xbdm = tempMap.get("XBDM")==null ? "":tempMap.get("XBDM").toString();
			if("1".equals(xbdm)){
				map.put("N_RSJXFBS",tempMap.get("RSJXFBS").toString());
				map.put("N_RSJXFJE",tempMap.get("RSJXFJE").toString());
				
			}else if("2".equals(xbdm)){
				map.put("V_RSJXFBS",tempMap.get("RSJXFBS").toString());
				map.put("V_RSJXFJE",tempMap.get("RSJXFJE").toString());
			}
		}
		Date date2 =  new Date();
	return Struts2Utils.map2json(map);
	}
	@Override
	public String getListSjxfBsAndJe(String params) {
		JSONObject json = JSONObject.fromObject(params);
		
		String start = json.containsKey("fromDate")?json.getString("fromDate"):"2014-05-01";
		String end = json.containsKey("toDate")?json.getString("toDate"):df.format(new Date());
		String tjlx =  json.containsKey("tjlx")?json.getString("tjlx"):"xb";
		
		String tableName ="tb_ykt_xfmx";
		if(json.containsKey("init")){
			tableName = "tb_ykt_xfmx_log";
		}
		String sql="select xfrq,xb_id,xbdm,round(sum(bs)/count(*),2) as sjxfbs,round(sum(je)/count(*),2) as sjxfje from "+
						"(select ryId,xfrq,xb_id,xbdm,count(*) as bs,sum(xfje) je from (select substr(t.xfsj,0,10) as xfrq" +
						",t.*,xjxx.xb_id,bzdm.dm as xbdm from "+tableName+" t left join tb_xjda_xjxx xjxx on xjxx.id = t.ryid "+
						"inner join tc_xxbzdmjg bzdm on bzdm.id = xjxx.xb_id  where t.xfsj between '"+start+"' and '"+end+"' " +
								" ) group by ryId,xfrq,xb_id,xbdm ) group by xfrq,xb_id,xbdm";
		if("hklx".equals(tjlx)){
			sql="select xfrq,hklx_id,xbdm,xbmc,round(sum(bs)/count(*),2) as sjxfbs,round(sum(je)/count(*),2) as sjxfje from "+
					"(select ryId,xfrq,hklx_id,xbdm,xbmc,count(*) as bs,sum(xfje) je from (select substr(t.xfsj,0,10) as xfrq" +
					",t.*,xjxx.hklx_id,bzdm.dm as xbdm,bzdm.mc as xbmc from "+tableName+" t left join tb_xjda_xjxx xjxx on xjxx.id = t.ryid "+
					"left join tc_xxbzdmjg bzdm on bzdm.id = xjxx.hklx_id  where t.xfsj between '"+start+"' and '"+end+"' " +
							" ) group by ryId,xfrq,hklx_id,xbdm,xbmc ) group by xfrq,hklx_id,xbdm,xbmc";
		}
		List result = baseDao.querySqlList(sql);
		for(Object obj : result){
			Map map = (Map) obj;
			map.put("field", map.get("XFRQ").toString());
			
			if("hklx".equals(tjlx)){
				map.put("name", map.get("XBMC")==null?"其他":map.get("XBMC").toString());
			}else{
				map.put("name", "1".equals(map.get("XBDM")==null?"":map.get("XBDM").toString())?"男":"女");
			}
		}
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getRsjXfje(String params) {
		return null;
	}

	@Override
	public String getGDxfrs(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.containsKey("fromDate")?json.getString("fromDate"):"2014-05-01";
		String end = json.containsKey("toDate")?json.getString("toDate"):df.format(new Date());
		
		String tableName ="tb_ykt_xfmx";
		if(json.containsKey("init")){
			tableName = "tb_ykt_xfmx_log";
		}
		String sql = "select count(*) as rs,'dxf' as lx from ("
				+ "select ryid,round(sum(je)/count(distinct xfrq),2) sjxfje,count(ryid) xfbs from ("
				+ "select ryId,xfrq,count(*) as bs,sum(xfje) je from (select to_char(substr(t.xfsj,0,10) as xfrq,t.* from "+tableName+" t " 
				+	"where t.xfsj between '"+start+"' and '"+end+"'  ) group by ryId,xfrq"
				+ ") group by ryid"
				+ ") where sjxfje<("+rsjxfje+" ) "// 低消费人数
				+ "union all "
				+ "select count(*) as rs,'gxf' as lx from ("
				+ "select ryid,round(sum(je)/count(distinct xfrq),2) sjxfje,count(ryid) xfbs from ("
				+ "select ryId,xfrq,count(*) as bs,sum(xfje) je from (select to_char(substr(t.xfsj,0,10) as xfrq,t.* from "+tableName+" t " 
				+	"where t.xfsj between '"+start+"' and '"+end+"'  ) group by ryId,xfrq"
				+ ") group by ryid"
				+ ") where sjxfje>("+rsjxfje+" )"// 高消费人数
				+ "union all "
				+ "select count(*) as rs,'zrs' as lx from (select ryid from "+tableName+" t where t.xfsj between '"+start+"' and '"+end+"'  group by ryid)";// 总人数
		List temp = baseDao.querySqlList(sql);
		Map resultMap = new HashMap();
		resultMap.put("rsjxfje", rsjxfje);// 日生均消费金额
		int dxfrs = 0;
		int gxfrs = 0;
		int zrs =0;
		for(Object obj : temp){
			Map tempMap = (Map)obj;
			String lx = tempMap.get("LX").toString();
			if("dxf".equals(lx)){
				dxfrs = Integer.parseInt(tempMap.get("RS").toString());
			}else if("gxf".equals(lx)){
				gxfrs = Integer.parseInt(tempMap.get("RS").toString());
			}else if("zrs".equals(lx)){
				zrs = Integer.parseInt(tempMap.get("RS").toString());
			}
		}
		
		resultMap.put("gxfrs", gxfrs);
		resultMap.put("dxfrs", dxfrs);
		float gxfzb= zrs==0?0:(float)gxfrs/zrs*100;
		float dxfzb= zrs==0?0:(float)dxfrs/zrs*100;
        DecimalFormat df = new DecimalFormat("0.00");
        
		resultMap.put("gxfzb", df.format(gxfzb));
		resultMap.put("dxfzb", df.format(dxfzb));
		return Struts2Utils.map2json(resultMap);
	}
	
	
	@Override
	public String getXfqj(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.containsKey("fromDate")?json.getString("fromDate"):"2014-05-01";
		String end = json.containsKey("toDate")?json.getString("toDate"):df.format(new Date());
		
		String tableName ="tb_ykt_xfmx";
		if(json.containsKey("init")){
			tableName = "tb_ykt_xfmx_log";
		}
		String sql = "select xfqj,count(*) as zs from ( "
				+ "select ryid,sjxfje,xfbs,"
				+ "case when sjxfje between 0 and 5 then '0-5元'"
				+ "when sjxfje between 5 and 8 then '05-08元'"
				+ "when sjxfje between 8 and 10 then '08-10元'"
				+ "when sjxfje between 10 and 13 then '10-13元'"
				+ "when sjxfje between 13 and 15 then '13-15元'"
				+ "when sjxfje between 15 and 20 then '15-20元' "
				+ "when sjxfje > 20 then '高于20元'  end as xfqj"
				+ " from ("
				+ "select ryid,round(sum(je)/count(distinct xfrq),2) sjxfje,count(ryid) xfbs from ("
				+ "select ryId,xfrq,count(*) as bs,sum(xfje) je from "
				+ "(select substr(t.xfsj,0,10) as xfrq"
				+" ,t.* from "+tableName+" t where t.xfsj between '"+start+"' and '"+end+"' ) group by ryId,xfrq"
				+ ") group by ryid" + ")) group by xfqj";		
		List result = baseDao.querySqlList(sql);
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String getXsjcxg(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String start = json.containsKey("fromDate")?json.getString("fromDate"):"2014-05-01";
		String end = json.containsKey("toDate")?json.getString("toDate"):df.format(new Date());
		
		String tableName ="tb_ykt_xfmx";
		if(json.containsKey("init")){
			tableName = "tb_ykt_xfmx_log";
		}
		String zzw = "SELECT CASE WHEN substr(t.xfsj,12,2) <= 9 THEN '早餐' "+
							 " WHEN substr(t.xfsj,12,2) between 11 and 14 THEN '午餐'"+
							 " WHEN substr(t.xfsj,12,2) between 16 and 24 THEN '晚餐'"+
							 " ELSE  '' END AS sj,t.*,substr(t.xfsj,1,10) as xfrq"+
							 " from "+tableName+" t where t.xfsj between '"+start+"' and '"+end+"'";
		String sql="select lx, count(*) as rs from (select ryid,"+
             "  pjscs,case when pjscs between 2.4 and 3 then 'three'"+
             "  when pjscs between 1.5 and 2.39 then 'two'"+
             "  when pjscs between 0 and 1.49 then 'one'"+
             "  end as lx from (select ryid,"+
             "  round(count(ryid) / count(distinct xfrq), 2) as pjscs"+
             "  from (select xfrq,ryid,sj,count(*) as xfbs,sum(xfje) xfje"+
             "  from ("+zzw+")where sj is not null group by xfrq, ryid, sj)"+
             "  group by ryid)) group by lx";
		List result = baseDao.querySqlList(sql);
		int count = baseDao.querySqlCount("select count(distinct ryid) as zs from "+tableName+"");
		
		Map resultMap = new HashMap();
		for(Object obj : result){
			Map temp = (Map)obj;
			String lx = temp.get("LX").toString();
			int rs = Integer.parseInt(temp.get("RS").toString());
			
			float zb= count==0?0:(float)rs/count*100;
	        DecimalFormat df = new DecimalFormat("0.00");
	        
			temp.put("ZB", df.format(zb));
			
			resultMap.put(lx, temp);
		}
		resultMap.put("list",result);
		return Struts2Utils.map2json(resultMap);
	}
}
