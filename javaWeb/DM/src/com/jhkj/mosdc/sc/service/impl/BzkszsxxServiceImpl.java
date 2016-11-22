package com.jhkj.mosdc.sc.service.impl;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.newoutput.util.JSONUtil;
import com.jhkj.mosdc.sc.service.BzkszsxxService;

public class BzkszsxxServiceImpl implements BzkszsxxService {
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	/** 
	* @Title: queryYxzsqk 
	* @Description:  各院系招生情况
	* @return String
	*/
	public String queryYxzsqk(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = JSONUtil.getString(params, "xn");
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String cclx = params.containsKey("cclx")?params.get("cclx").toString():"";

		String str2 = " AND xj.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		String sql = "select '院系招生人数' name, t.mc field,count(xj.id) value from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xj on xj.yx_id = t.id and substr(xj.rxrq,1,4) = substr('"+ xn +"',1,4) where t.cc = 1 and t.sfky=1 group by t.mc";
		if(!"XX".equals(cclx)){
			sql ="select '院系招生人数' name, t.mc field,count(xj.id) value from tb_jxzzjg t " +
					" left join tb_xjda_xjxx xj on xj.zy_id = t.id and substr(xj.rxrq,1,4) = substr('"+ xn +"',1,4)  where t.cc = 2 and t.sfky=1 and t.qxm like '"+qxm+"%'  group by t.mc";
		}
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
	/** 
	* @Title: queryFsdzsqk 
	* @Description:  招生各分数段学生数
	* @return String
	*/
	public String queryFsdzsqk(String json){
		JSONObject params = JSONObject.fromObject(json);
		String xn = JSONUtil.getString(params, "xn");
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String cclx = params.containsKey("cclx")?params.get("cclx").toString():"";
		String str2 = " AND t.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		String sql = 
				"select '高考成绩分布' name,t.cj field,count(1) value from (" +
				" select case  when floor(t.kscj/10) > 50 then '500以上'" +
				" when floor(t.kscj/10) >45 then '450-500'" +
				" when floor(t.kscj/10) > 40 then  '400-450'" +
				" when floor(t.kscj/10) > 35 then  '350-400' " +
				" when floor(t.kscj/10) > 30 then  '300-350' " +
				" when floor(t.kscj/10) <= 30 or t.kscj is null then '未维护' end   cj " +
				" from tb_xjda_xjxx t where substr(t.rxrq,1,4) = substr('"+ xn +"',1,4) "+str2+")t group by t.cj order by t.cj desc";
		
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
	/** 
	* @Title: queryYearszs 
	* @Description:  查询历年招生数
	* @return String
	*/
	public String queryYearszs(String json){
		JSONObject params = JSONObject.fromObject(json);
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String str2 = " AND t.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		String sql = "select '招生人数' name, nvl(substr(t.rxrq,1,4),'未知') field,count(1) value from tb_xjda_xjxx t where 1=1 "+str2+" group by substr(t.rxrq,1,4) order by field";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
	
	/** 
	* @Title: queryLngkfs 
	* @Description: TODO 查询历年高考分数变化
	* @return String
	*/
	public String queryLngkfs(String json){
		JSONObject params = JSONObject.fromObject(json);
		String qxm = params.containsKey("qxm")?params.get("qxm").toString():"";
		String str2 = " AND t.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+qxm+"%')";
		String sql = "select gk.gknf nf,count(gk.wid) lqrs,max(zf.gkzf) lqzgf,min(zf.gkzf) lqzdf from t_bzks_gk gk left join t_bzks_gkzf zf on gk.kh = zf.kh group by gk.gknf";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
}
