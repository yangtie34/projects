package com.jhkj.mosdc.sc.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.TsJYService;

public class TsJYServiceImpl implements TsJYService {
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}


	@Override
	@SuppressWarnings("unchecked")
	public String queryBookTypeByYX(String params) {
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
		String tj=" ";
		String lj=" ";
		if(zzjgPid.equals("-1")){
			lj=" left join tb_jxzzjg jx on xs.yx_id=jx.id ";
		}else if(zzjgPid.equals("0")){
			tj=" and jx.fjd_id ='"+zzjgId+"' ";
			lj=" left join tb_jxzzjg jx on xs.zy_id=jx.id ";
		}else{
			tj=" and jx.fjd_id ='"+zzjgPid+"' ";
			lj=" left join tb_jxzzjg jx on xs.zy_id=jx.id ";
		}
		String beginDate = json.getString("fromDate");
		String endDate = json.getString("toDate");
		String sql="select jx.mc,nvl(ts.gcdmc,'未维护') lb from t_ts_jy jy "+
				"inner join t_ts ts on jy.tstm=ts.tstm "+
				"inner join tb_xjda_xjxx xs on jy.dztm=xs.xh "+
				lj+
				"where to_date(jy.jsrq,'yyyy-mm-dd hh24:mi:ss') between  "+
				"         to_date('"+beginDate+"','yyyy-mm-dd')  "+
				"         and to_date('"+endDate+"','yyyy-mm-dd') "+
				tj+
				"      group by jx.mc ,ts.gcdmc order by jx.mc ";

		List<?> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String queryBookNumberByYX(String params) {
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
		String tj=" ";
		String lj=" ";
		if(zzjgPid.equals("-1")){
			lj=" left join tb_jxzzjg jx on xs.yx_id=jx.id ";
		}else if(zzjgPid.equals("0")){
			tj=" and jx.fjd_id ='"+zzjgId+"' ";
			lj=" left join tb_jxzzjg jx on xs.zy_id=jx.id ";
		}else{
			tj=" and jx.fjd_id ='"+zzjgPid+"' ";
			lj=" left join tb_jxzzjg jx on xs.zy_id=jx.id ";
		}
		String beginDate = json.getString("fromDate");
		String endDate = json.getString("toDate");
		String sql="select * from (select jx.mc field,count(jx.mc) value ,'借书数量(本)' name from t_ts_jy jy "+
					"inner join t_ts ts on jy.tstm=ts.tstm "+
					"inner join tb_xjda_xjxx xs on jy.dztm=xs.xh "+
					lj+
					"where to_date(jy.jsrq,'yyyy-mm-dd hh24:mi:ss') between  "+
					"         to_date('"+beginDate+"','yyyy-mm-dd')  "+
					"         and to_date('"+endDate+"','yyyy-mm-dd') "+
					tj+
					"     group by  jx.mc)  order by value desc";

		List<?> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String queryBookTop(String params) {
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
		String tj=" ";
		if(zzjgPid.equals("-1")){
		}else if(zzjgPid.equals("0")){
			tj=" and yx.id ='"+zzjgId+"' ";
		}else{
			tj=" and zy.id ='"+zzjgId+"' ";
		}
		String beginDate = json.getString("fromDate");
		String endDate = json.getString("toDate");
		String sql="select * from ( select a.*,rownum r from "+
					"( select * from ( select jy.tsmc mc,count(*) sl from t_ts_jy jy "+ 
					"inner join t_ts ts on jy.tstm=ts.tstm "+
					"inner join tb_xjda_xjxx xs on jy.dztm=xs.xh "+
					"left join tb_jxzzjg yx on xs.yx_id=yx.id "+
					"left join tb_jxzzjg zy on xs.zy_id=zy.id "+
					"where to_date(jy.jsrq,'yyyy-mm-dd hh24:mi:ss') between  "+
					"         to_date('"+beginDate+"','yyyy-mm-dd')  "+
					"         and to_date('"+endDate+"','yyyy-mm-dd') "+
					tj+
					 "     group by jy.tsmc )  order by sl desc ) a ) "+
					 "     where r>0and r<=10"; 

		List<?> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	@SuppressWarnings("unchecked")
	public String queryStudentTop(String params) {
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
		String tj=" ";
		if(zzjgPid.equals("-1")){
		}else if(zzjgPid.equals("0")){
			tj=" and yx.id ='"+zzjgId+"' ";
		}else{
			tj=" and zy.id ='"+zzjgId+"' ";
		}
		String beginDate = json.getString("fromDate");
		String endDate = json.getString("toDate");
		String sql="select * from ( select a.*,rownum r from " +
					"( select * from ( select xs.xh xh,xs.xm mc,count(*) sl from t_ts_jy jy  " +
					"inner join t_ts ts on jy.tstm=ts.tstm " +
					"inner join tb_xjda_xjxx xs on jy.dztm=xs.xh " +
					"left join tb_jxzzjg yx on xs.yx_id=yx.id " +
					"left join tb_jxzzjg zy on xs.zy_id=zy.id " +
					"where to_date(jy.jsrq,'yyyy-mm-dd hh24:mi:ss') between  " +
					"         to_date('"+beginDate+"','yyyy-mm-dd')  " +
					"         and to_date('"+endDate+"','yyyy-mm-dd') " +
					tj+
					"      group by xs.xh,xs.xm )  order by sl desc ) a ) " +
					"      where r>0and r<=10";  

		List<?> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}


	@Override
	@SuppressWarnings("unchecked")
	public String queryBookNumberByType(String params) {
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
		String tj=" ";
		if(zzjgPid.equals("-1")){
		}else if(zzjgPid.equals("0")){
			tj=" and yx.id ='"+zzjgId+"' ";
		}else{
			tj=" and zy.id ='"+zzjgId+"' ";
		}
		String beginDate = json.getString("fromDate");
		String endDate = json.getString("toDate");
		String sql="select * from ( select nvl(ts.gcdmc,'未维护') field,count(ts.gcdmc) value,'借书数量(本)' name  from t_ts_jy jy "+
					"inner join t_ts ts on jy.tstm=ts.tstm "+
					"inner join tb_xjda_xjxx xs on jy.dztm=xs.xh "+
					"left join tb_jxzzjg yx on xs.yx_id=yx.id "+
					"left join tb_jxzzjg zy on xs.zy_id=zy.id "+
					"where to_date(jy.jsrq,'yyyy-mm-dd hh24:mi:ss') between  "+
					"         to_date('"+beginDate+"','yyyy-mm-dd')  "+
					"         and to_date('"+endDate+"','yyyy-mm-dd') "+
					tj+
					"      group by ts.gcdmc )  order by value desc";   

		List<?> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
}
