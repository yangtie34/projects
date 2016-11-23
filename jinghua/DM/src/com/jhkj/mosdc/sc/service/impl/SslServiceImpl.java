package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.SslService;

public class SslServiceImpl extends ScServiceImpl implements SslService {
	private DecimalFormat df = new DecimalFormat("0.00");
	@Override
	public String getTitleData(String params) {
		Map backval = new HashMap();
		// 计算楼宇数
		String lys ="SELECT COUNT(*) FROM TB_DORM_CCJG WHERE CCLX='LY'";
		// 计算房间数
		String fjs ="SELECT COUNT(*) FROM TB_DORM_CCJG WHERE CCLX='QS'";
		// 计算床位数
		String cws ="SELECT COUNT(*) FROM TB_DORM_CW";
		// 计算已入住比率
		String yrzs = "select count(*) from tb_dorm_cw cw inner join tb_dorm_zy zy on cw.id = zy.cw_id";
		// 计算空床位数
		String kcws = "select count(*) from tb_dorm_cw cw left join tb_dorm_zy zy on zy.cw_id = cw.id where zy.xs_id is null";
		// 计算可入住男生数
		String nscws = "select count(*) from tb_dorm_cw cw left join tb_dorm_zy zy on zy.cw_id = cw.id "+
									" left join tb_dorm_fj fj on cw.fj_id = fj.id where zy.xs_id is null and fj.xb = '男'";
		// 计算可入住女生数
		String nvscws = "select count(*) from tb_dorm_cw cw left join tb_dorm_zy zy on zy.cw_id = cw.id "+
				" left join tb_dorm_fj fj on cw.fj_id = fj.id where zy.xs_id is null and fj.xb = '女'";
		int lycount = baseDao.querySqlCount(lys);
		int fjcount = baseDao.querySqlCount(fjs);
		int cwcount = baseDao.querySqlCount(cws);
		int yrzcount = baseDao.querySqlCount(yrzs);
		int kcwcount = baseDao.querySqlCount(kcws);
		int nscwcount = baseDao.querySqlCount(nscws);
		int nvscwcount = baseDao.querySqlCount(nvscws);
		backval.put("lycount", lycount);
		backval.put("fjcount", fjcount);
		backval.put("cwcount", cwcount);
		float num= cwcount==0?0:(float)yrzcount/cwcount*100;
		backval.put("yrzcount", df.format(num));
		backval.put("kcwcount", kcwcount);
		backval.put("nscwcount", nscwcount);
		backval.put("nvscwcount", nvscwcount);
		return Struts2Utils.map2json(backval);
	}
	
	@Override
	public String getSslRzColumn(String params) {
		String ztsql = "select ly,zt,count(*) AS ZS from "
				+ " (select ccjg.mc as ly,cw.id as cw_id,cw.fj_id,zy.xs_id,case when zy.xs_id is null then '空床位' else '已入住' end as zt "
				+ "	from tb_dorm_cw cw left join tb_dorm_zy zy on zy.cw_id = cw.id "
				+ " left join tb_dorm_fj fj on fj.id = cw.fj_id left join tb_dorm_ccjg ccjg on ccjg.id = fj.ly_id ) group by ly,zt order by ly,zt";
		List<Map> ztList = baseDao.querySqlList(ztsql);
		List<Map<String,String>> flag = new ArrayList<Map<String,String>>();
		
		String ly="",zt="";
		int i=1;
		
		for (Map obj : ztList) {
			if(i==1){
				ly = obj.get("LY").toString();
				zt=obj.get("ZT").toString();
				i++;
			}else{
				if(!ly.equals(obj.get("LY").toString())){
					Map temp = new HashMap();
					temp.put("field", ly);
					if(zt.equals("已入住")){
						temp.put("name", "空床位");
					}else{
						temp.put("name", "已入住");
					}
					temp.put("value", 0);
					
					flag.add(temp);
					
					ly = obj.get("LY").toString();
					zt=obj.get("ZT").toString();
					i++;
				}else{
					i=1;	
				}
				
			}
			
			obj.put("field", obj.get("LY"));
			obj.put("name", obj.get("ZT"));
			obj.put("value", obj.get("ZS"));
			
		}
		ztList.addAll(flag);
		return Struts2Utils.list2json(ztList);
	}
	
	@Override
	public String getTable1Data(String params) {
		List<Map> results = new ArrayList<Map>();
		
		// 获得校区数据
		String sql ="SELECT * FROM TB_DORM_CCJG WHERE CCLX='XQ'";
		List<Map> xqs = baseDao.querySqlList(sql);
		
		if(xqs.size()==0){
			String sqllys ="SELECT * FROM TB_DORM_CCJG WHERE CCLX='LY'";
			List lists = queryList4Ly(sqllys);
			Map backval = new HashMap();
			backval.put("mc","本校区");
			backval.put("list", lists);
			results.add(backval);
		}else{
			for(Map temp : xqs){
				String xqid = temp.get("ID").toString();
				String xqMc = temp.get("MC").toString();
				String sqllys ="SELECT * FROM TB_DORM_CCJG WHERE CCLX='LY' and FJD_ID="+xqid;
				List lists = queryList4Ly(sqllys);
				Map backval = new HashMap();
				backval.put("mc",xqMc);
				backval.put("list", lists);
				results.add(backval);
			}
		}
		
		return Struts2Utils.list2json(results);
	}
	
	private List<Map> queryList4Ly(String sql){
		List<Map> lys = baseDao.querySqlList(sql);
		for(Map map : lys){
			String lyid = map.get("ID").toString();//楼宇id
			// 获得总房间数 
			String zfjs = "select count(*) from tb_dorm_fj fj where ly_id = "+lyid;
			// 获得住宿性别
			String zsxb = "select xb,count(*) zs from tb_dorm_cw cw left join tb_dorm_fj fj on fj.id = cw.fj_id where ly_id = "+lyid
					+" group by xb order by zs desc";
			// 获得床位数
			String cws ="select count(*) from tb_dorm_cw cw left join tb_dorm_fj fj on fj.id = cw.fj_id where ly_id = "+lyid;
			// 获得已入住学生、获得空床位数
			String rzkcw ="select zt,count(*) as rs from ( "+
				"select ccjg.mc as ly,cw.id as cw_id,cw.fj_id,zy.xs_id,case when zy.xs_id is null then '空床位' else '已入住' end as zt "+
				"from tb_dorm_cw cw left join tb_dorm_zy zy on zy.cw_id = cw.id "+
				"left join tb_dorm_fj fj on fj.id = cw.fj_id left join tb_dorm_ccjg ccjg on ccjg.id = fj.ly_id where fj.ly_id =  " +lyid+
				" ) group by zt";
			// 获得空房间数
		    String kfjs ="select fj_id,count(*) from ("+
		    		" select ccjg.mc as ly,cw.id as cw_id,cw.fj_id,zy.xs_id,case when zy.xs_id is null then '空床位' else '已入住' end as zt "+
					" from tb_dorm_cw cw left join tb_dorm_zy zy on zy.cw_id = cw.id "+
					 " left join tb_dorm_fj fj on fj.id = cw.fj_id left join tb_dorm_ccjg ccjg on ccjg.id = fj.ly_id where fj.ly_id = "+lyid
					 +" and zy.xs_id is null "+ 
					" ) group by fj_id";
			
			int zfj = baseDao.querySqlCount(zfjs);
			map.put("zfjs", zfj);
			int cwcount = baseDao.querySqlCount(cws);
			map.put("cws", cwcount);
			int kfj = baseDao.querySqlList(kfjs).size();
			map.put("kfjs", kfj);
			List<Map> xb = baseDao.querySqlList(zsxb);
			if(xb.size()==0){
				map.put("zsxb", "--");
			}else{
				map.put("zsxb",xb.get(0).get("XB"));
			}
			List<Map> rzjkcw = baseDao.querySqlList(rzkcw);
			if(rzjkcw.size()==0){
				map.put("yrzs", "--");
				map.put("kcws","--");
			}else{
				for(Map temp : rzjkcw){
					String zt = temp.get("ZT").toString();
					int sl = Integer.parseInt(temp.get("RS").toString());
					if("已入住".equals(zt)){
						map.put("yrzs", sl);
					}else if("空床位".equals(zt)){
						map.put("kcws",sl);
					}
					if(!map.containsKey("yrzs")){
						map.put("yrzs",0);
					}
					if(!map.containsKey("kcws")){
						map.put("kcws",0);
					}
				}
			}
		}
		return lys;
	}
	
	@Override
	public String getTable2Data(String params) {
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		String yxSql="";
		if("".equals(jxzzjgids)){
			yxSql="SELECT * FROM TB_JXZZJG WHERE CCLX='YX' AND SFKY='1' order by dm";
		}else{
			yxSql ="SELECT * FROM TB_JXZZJG WHERE CCLX='YX' AND SFKY='1' AND ID IN ("+jxzzjgids+") order by dm";
		}
		List<Map> yxs = baseDao.querySqlList(yxSql);
		for(Map yx :yxs){
			String yxid = yx.get("ID").toString();
			// 计算学院学生总数
			Calendar c = Calendar.getInstance();
			int month = c.getTime().getMonth();
			String zrs ="SELECT count(*) FROM TB_XJDA_XJXX xjxx WHERE xjxx.YX_ID="+yxid+" AND xjxx.XJZT_ID=1000000000000101 AND XJXX.XSZT_ID = 1 and substr(xjxx.yjbysj,0,4) " +
				((month < 7)?">=":">" ) +
				"to_char(sysdate,'yyyy')";
			int xss = baseDao.querySqlCount(zrs);
			// 计算学院已入住学生
			String yrz ="select count(*) from tb_dorm_zy zy inner join tb_xjda_xjxx xj on xj.xh = zy.xs_id and xj.yx_id = "+yxid;
			int yrzs = baseDao.querySqlCount(yrz);
			// 计算学院各年级分布楼宇
			String njxbfb ="select ly,rxnj_id,xb,count(*) as zs from ( "+
				" select ccjg.mc as ly,cw.mc as cwh,qs.mc as qs,"+
				" xj.xm,xj.xh,xj.rxnj_id,zzjg.mc as yx,fj.xb from tb_dorm_zy zy"+ 
				" left join tb_dorm_cw cw on cw.id = zy.cw_id "+
				" left join tb_dorm_fj fj on fj.id = cw.fj_id "+
				" left join tb_dorm_ccjg ccjg on ccjg.id = fj.ly_id"+ 
				" left join tb_xjda_xjxx xj on xj.xh = zy.xs_id and xj.yx_id = "+ yxid+
				" left join tb_jxzzjg zzjg on zzjg.id = xj.yx_id"+
				" left join tb_dorm_ccjg qs on qs.dm = fj.fjdm "+
				" where xm is not null and qs.mc is not null ) group by ly,rxnj_id,xb order by rxnj_id,xb";
			String njxbfb2 ="select rxnj_id,xb,count(*) as zs from ( "+
					" select ccjg.mc as ly,cw.mc as cwh,qs.mc as qs,"+
					" xj.xm,xj.xh,xj.rxnj_id,zzjg.mc as yx,fj.xb from tb_dorm_zy zy"+ 
					" left join tb_dorm_cw cw on cw.id = zy.cw_id "+
					" left join tb_dorm_fj fj on fj.id = cw.fj_id "+
					" left join tb_dorm_ccjg ccjg on ccjg.id = fj.ly_id"+ 
					" left join tb_xjda_xjxx xj on xj.xh = zy.xs_id and xj.yx_id = "+ yxid+
					" left join tb_jxzzjg zzjg on zzjg.id = xj.yx_id"+
					" left join tb_dorm_ccjg qs on qs.dm = fj.fjdm "+
					" where xm is not null and qs.mc is not null ) group by rxnj_id,xb order by rxnj_id,xb";
			List<Map> njxbfbList = baseDao.querySqlList(njxbfb);
			List<Map> njxbfbList2 = baseDao.querySqlList(njxbfb2);
			Map<String,Map<String,List<String>>> tempMap = new TreeMap<String,Map<String,List<String>>>();
			for(Map map : njxbfbList){
				String nj = map.get("RXNJ_ID").toString();
				if(map.get("XB")==null||"".equals(map.get("XB").toString()))continue;
				String xb = map.get("XB").toString();
				String zs = map.get("ZS").toString();
				String ly = map.get("LY").toString();
				if(tempMap.containsKey(nj)){
					Map<String,List<String>> xbMap = tempMap.get(nj);
					if(xbMap.containsKey(xb)){
						List<String> lyList = xbMap.get(xb);
						lyList.add(ly+"("+zs+")");
					}else{
						List<String> lyList = new ArrayList();
						lyList.add(ly+"("+zs+")");
						xbMap.put(xb, lyList);
					}
				}else{
					
					Map<String,List<String>> xbMap = new HashMap<String,List<String>>();
					List<String> lyList = new ArrayList();
					lyList.add(ly+"("+zs+")");
					xbMap.put(xb, lyList);
					
					tempMap.put(nj, xbMap);
				}
				
			}
			for(Map map : njxbfbList2){
				String nj = map.get("RXNJ_ID").toString();
				if(map.get("XB")==null||"".equals(map.get("XB").toString()))continue;
				String xb = map.get("XB").toString();
				StringBuffer lys = new StringBuffer();
				List<String> lyList = tempMap.get(nj).get(xb);
				for(String str : lyList){
					lys.append(str).append(",");
				}
				if(lys.length()!=0){
					lys.substring(lys.length()-1);
				}
				map.put("lys", lys.toString());
			}
			float num= xss==0?0:(float)yrzs/xss*100;
	        
			yx.put("rzzb", df.format(num));
			yx.put("list", njxbfbList2);
			yx.put("zxss", xss);
			yx.put("yrzs", yrzs);
		}
		return Struts2Utils.list2json(yxs);
	}
}
