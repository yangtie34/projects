package com.jhkj.mosdc.sc.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.YktCfTjService;

public class YktCfTjServiceImpl implements YktCfTjService {
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String queryYktCf(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgPid=json.containsKey("zzjgPid")?json.get("zzjgPid").toString():"-1";
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		//第一次进入时无法判断其权限，故先判断是否是第一次进入
		if(!json.containsKey("zzjgPid")){
			User user = UserPermiss.getUser();
			String jxzzjgids = user.getCurrentJxzzjgIds();
			if("".equals(jxzzjgids)||jxzzjgids==null){
				zzjgPid ="-1";
			}else{
				String sql ="SELECT * FROM TB_JXZZJG WHERE SFKY=1 AND id in ("+jxzzjgids+") and id !=0";
				List<Map<String,Object>> result = baseService.querySqlList(sql);
				for(Map<String,Object> obj : result){
					if(obj.get("CC").toString().equals("1")){
						zzjgPid="0";
						zzjgId=obj.get("ID").toString();
						break;
					}
					zzjgPid="1";
					zzjgId=obj.get("ID").toString();
				}
			}
		}
		StringBuffer tj=new StringBuffer(" ");
		if(zzjgPid.equals("-1")){
		}else if(zzjgPid.equals("0")){
			tj.append(" and yx.id ='"+zzjgId+"' ");
		}else{
			tj.append(" and zy.id ='"+zzjgId+"' ");
		}
		
		String beginDate = json.getString("fromDate");
		String endDate = json.getString("toDate");
		String cflx=json.containsKey("cflx")?json.get("cflx").toString():"all";
		String table=json.containsKey("isfast")?json.get("isfast").toString():"1";
		String order=json.getString("order");
		if(table.equals("1")){
			table="tb_ykt_xfmx_log";
		}else{
			table="tb_ykt_xfmx";
		}
		if ("zao".equals(cflx)){
			tj.append(" and substr(xf.xfsj,12,8) between '04:00:00' and '09:59:59' ");
		}else if ("wu".equals(cflx)){
			tj.append(" and substr(xf.xfsj,12,8) between '10:00:00' and '14:59:59' ");
		}else if ("wan".equals(cflx)){
			tj.append(" and substr(xf.xfsj,12,8) between '15:00:00' and '22:59:59' ");
		}
		String sql="";
		if("all".equals(cflx)){
			String sql1=getSql(beginDate,endDate,"刷卡次数","count(lx.mc)",tj,table);
			String sql2=getSql(beginDate,endDate,"消费金额","sum(xf.xfje)",tj,table);
			sql="select * from (select t.* , rownum r "+
				" from (select * from ( "+
				sql1 + " UNION ALL " +sql2 +
				" )  order by "+order+" desc ,value ) t ) "+
				" where r > 0 and r <= 20";
		}else{
			String sql1="";
			if("je".equals(order)){
				sql1=getSql(beginDate,endDate,"消费金额","sum(xf.xfje)",tj,table);
			}else{
				sql1=getSql(beginDate,endDate,"刷卡次数","count(lx.mc)",tj,table);
			}
			sql="select * from (select t.* , rownum r "+
				" from (select * from ( "+
				sql1 +
				" )  order by "+order+" desc ,value ) t ) "+
				" where r > 0 and r <= 10";
		}
		List<?> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
	private String getSql(String beginDate,String endDate,String ch,String xs,StringBuffer tj,String table){
		String sql="select lx.mc field,'"+ch+"'as name, "+xs+" as value ,count(lx.mc) as sk ,sum(xf.xfje) as je "+
				" from "+table+" xf "+
				" inner join tb_ykt_dk dk on xf.dkh = dk.dkh "+
				" inner join tb_ykt_ywbm lx on dk.lx = lx.dm "+
				"  inner join tb_xjda_xjxx xs on xf.ryid=xs.xh "+
				"left join tb_jxzzjg yx on xs.yx_id=yx.id "+
				"left join tb_jxzzjg zy on xs.zy_id=zy.id "+
				"where xf.xfsj between '"+beginDate+"' and '"+endDate+"'"+
				tj+
				" and xf.jykm = '210' "+
				" group by lx.mc ";
		return sql;
	}
}
