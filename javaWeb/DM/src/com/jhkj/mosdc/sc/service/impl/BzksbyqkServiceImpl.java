package com.jhkj.mosdc.sc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.newoutput.util.JSONUtil;
import com.jhkj.mosdc.sc.service.BzksbyqkService;

public class BzksbyqkServiceImpl implements BzksbyqkService {
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	
	/** 
	* @Title: queryYbyqk  
	* @Description: TODO 应毕业情况 查询
	* @return String
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryYbyqk(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = JSONUtil.getString(params, "xn");
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		int ybyrs = 0, byrs = 0,yyrs = 0; //应毕业人数， 毕业人数，肄业人数
		String yby = "select count(1) num from tb_xjda_xjxx xjxx where (to_number(substr(xjxx.rxrq,1,4)) + xjxx.xz_id ) =  substr('"+ xn +"',6,4) "+str2;
		ybyrs = Integer.parseInt((String) baseService.queryListMapInLowerKeyBySql(yby).get(0).get("num"));
		
		String jyfs = "select f.mc jyfs,count(jy.wid) num from dm_zxbz.T_ZXBZ_JSXYFS f " +
				" left join t_bzks_jy jy on jy.jsxyfsdm = f.dm and to_char(jy.jsxyny,'yyyy') = substr('"+xn+"',6,4) " +
				" left join tb_xjda_xjxx xjxx on xjxx.xh = jy.xh where 1=1 "+str2+
				" group by f.mc ";
		
		List<Map> jyfsList = baseService.queryListMapInLowerKeyBySql(jyfs);
		for (int i = 0; i < jyfsList.size(); i++) {
			if (jyfsList.get(i).get("jyfs").equals("毕业")) {
				byrs = Integer.parseInt((String) jyfsList.get(i).get("num"));
			}else if(jyfsList.get(i).get("jyfs").equals("肄业")){
				yyrs = Integer.parseInt((String)jyfsList.get(i).get("num"));
			}
		}
		Map bytxt = new HashMap();
		bytxt.put("text", "实际毕业<br>" + byrs + "人<br>占比 " +100*(byrs/(ybyrs==0?1:ybyrs))+"%");
		Map yytxt = new HashMap();
		yytxt.put("text", "肄业<br>" + yyrs + "人<br>占比 " +100*(yyrs/(ybyrs==0?1:ybyrs))+"%");
		Map qttxt = new HashMap();
		qttxt.put("text", "未维护<br>" + (ybyrs - byrs -yyrs) + "人<br>占比 " +100*((ybyrs - byrs -yyrs)/(ybyrs==0?1:ybyrs))+"%");
		List texts = new ArrayList();
		texts.add(bytxt);
		texts.add(yytxt);
		texts.add(qttxt);
		
		Map result = new HashMap();
		result.put("title", "本届学生应毕业 "+ ybyrs +"人");
		result.put("texts", texts);
		return Struts2Utils.map2json(result);
	}
	
	/** 
	* @Title: querySjbyqk 
	* @Description: TODO 实际毕业情况查询
	* @return String
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String querySjbyqk(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = JSONUtil.getString(params, "xn");
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		int byrs = 0; //毕业人数
		String jyfs = "select f.mc jyfs,count(jy.wid) num from dm_zxbz.T_ZXBZ_JSXYFS f " +
				" left join t_bzks_jy jy on jy.jsxyfsdm = f.dm and to_char(jy.jsxyny,'yyyy') = substr('"+xn+"',6,4) " +
						" left join tb_xjda_xjxx xjxx on xjxx.xh = jy.xh "+str2+" where f.mc = '毕业'  group by f.mc ";
		
		byrs = Integer.parseInt((String)baseService.queryListMapInLowerKeyBySql(jyfs).get(0).get("num"));
		Map bytxt = new HashMap();
		bytxt.put("text", "选择就业<br>0人<br>占比  0%");
		Map yytxt = new HashMap();
		yytxt.put("text", "选择考研<br>0人<br>占比  0%");
		Map qttxt = new HashMap();
		qttxt.put("text", "未维护<br>0人<br>占比 0%");
		List texts = new ArrayList();
		texts.add(bytxt);
		texts.add(yytxt);
		texts.add(qttxt);
		
		Map result = new HashMap();
		result.put("title", "本届学生实际毕业 "+ byrs +"人");
		result.put("texts", texts);
		return Struts2Utils.map2json(result);
	}
	
	/** 
	* @Title: queryXzkyqk 
	* @Description: TODO 选择考研学生情况
	* @return String
	*/
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String queryXzkyqk(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = JSONUtil.getString(params, "xn");
		Map bytxt = new HashMap();
		bytxt.put("text", "被录取<br>0人<br>占比  0%");
		Map yytxt = new HashMap();
		yytxt.put("text", "落榜就业<br>0人<br>占比  0%");
		Map qttxt = new HashMap();
		qttxt.put("text", "其他<br>0人<br>占比 0%");
		List texts = new ArrayList();
		texts.add(bytxt);
		texts.add(yytxt);
		texts.add(qttxt);
		Map result = new HashMap();
		result.put("title", "本届学生选择考研 0人");
		result.put("texts", texts);
		return Struts2Utils.map2json(result);
	}
}
