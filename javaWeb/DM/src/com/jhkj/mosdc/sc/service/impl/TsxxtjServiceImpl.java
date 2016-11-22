package com.jhkj.mosdc.sc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.TsxxtjService;

public class TsxxtjServiceImpl implements TsxxtjService {
	private BaseService baseService;
	
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	@Override
	public String queryBookTypes(String jsonParams) {
		String sql = "select count(1) num from dm_zxbz.tb_zxbz_gcdm t";
		return Struts2Utils.map2json(baseService.queryListMapInLowerKeyBySql(sql).get(0));
	}

	@Override
	public String queryBookNumber(String jsonParams) {
		String sql = "select count(1) num from t_ts t";
		return Struts2Utils.map2json(baseService.queryListMapInLowerKeyBySql(sql).get(0));
	}

	@Override
	public String queryBookCosts(String jsonParams) {
		String sql = "select sum(t.jg) num from t_ts t";
		return Struts2Utils.map2json(baseService.queryListMapInLowerKeyBySql(sql).get(0));
	}

	@Override
	public String queryBookNumberByType(String jsonParams) {
		String sql = "select count(t.wid) value, nvl(fl.mc,'未分类') field, '图书数量' name " +
				"from t_ts t left join dm_zxbz.tb_zxbz_gcdm fl on t.gcdm = fl.dm group by fl.mc";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryBookCostByType(String jsonParams) {
		String sql = "select sum(t.jg) value, nvl(fl.mc,'未分类') field, '资金投入' name " +
				"from t_ts t left join dm_zxbz.tb_zxbz_gcdm fl on t.gcdm = fl.dm group by fl.mc";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryBookCostByTime(String jsonParams) {
		String sql = "select sum(t.jg) value ,substr(t.rdrq,0,4) field,'资金投入' name from t_ts t " +
				" where t.rdrq is not null group by substr(t.rdrq,0,4) order by substr(t.rdrq,0,4)";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	

	@Override
	public String queryBookJyxxByType(String jsonParams) {
		String sql = "select round(jycs/cs*100,4) as ltl,t.* from ( "+
				" select nvl(fl.mc,'未分类') lb,count(t.wid) cs, sum(t.jg) trzj,count(jy.dztm) jycs  "+
				" from t_ts t left join t_ts_jy jy on jy.tstm = t.tstm  "+
			"	left join dm_zxbz.tb_zxbz_gcdm fl on t.gcdm = fl.dm group by fl.mc)t order by lb desc";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryBookJylxxByday(String jsonParams) {
		String sql = "select sum(count(1)) fwzl,max(count(1)) zgfwl, round(avg(count(1))) rjfwl" +
				" from t_ts_jy t group by to_char(to_date(t.jsrq,'yyyy-mm-ddhh24:mi:ss'),'yyyymmdd')";
		
		String sql2 = "select sum(count(1)) fwzl2,max(count(1)) zgfwl2, round(avg(count(1))) rjfwl2 "+
				" from tb_ykt_mjmx_tsg t group by substr(sksj,0,10)";
		Map map1 = baseService.queryListMapInLowerKeyBySql(sql).get(0);
		Map map2 = baseService.queryListMapInLowerKeyBySql(sql2).get(0);
		map1.putAll(map2);
		return Struts2Utils.map2json(map1);
	}

	@Override
	public String queryXsjctsgqk(String params) {
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
			tj=" and txx.yx_id ='"+zzjgId+"'";
		}else{
			tj=" and txx.zy_id ='"+zzjgId+"'";
		}
		String beginDate = json.getString("fromDate");
		String endDate = json.getString("toDate");
		String isfast = json.containsKey("isfast")?json.get("isfast").toString():"1";
		String table="";
		if(isfast.equals("1")){
			table="tb_ykt_mjmx_tsg_log";
		}else{
			table="tb_ykt_mjmx_tsg_log";
		}
		String sql = "  select count(t.xh) value,to_char(t.sksj,'hh24') field,'学生出入图书馆人次' name from (" +
				" select to_date(mj.sksj,'yyyy-mm-dd hh24:mi:ss') sksj,kh.xgh xh from "+table+" mj" +
				" left join tb_ykt_kh kh on kh.kh = mj.dzzh" +
				" left join tb_xjda_xjxx txx on kh.xgh = txx.xh "+
				" where mj.sksj between '" + beginDate  + "' and '" + endDate + "' "
				+ tj
				+ ") t  group by to_char(t.sksj,'hh24') order by to_char(t.sksj,'hh24')";
		List result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
		}


	@Override
	public String queryGyxxsjctsgqk(String params) {
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
		String titleName="";
		if(zzjgPid.equals("-1")){
			titleName="各院系学生出入图书馆人次";
			lj=" left join tb_jxzzjg yx on xj.yx_id = yx.id ";
		}else if(zzjgPid.equals("0")){
			titleName="各专业学生出入图书馆人次";
			tj=" and xj.yx_id ='"+zzjgId+"'";
			lj=" left join tb_jxzzjg yx on xj.zy_id = yx.id ";
		}else{
			titleName="各专业学生出入图书馆人次";
			tj=" and xj.yx_id ='"+zzjgPid+"'";
			lj=" left join tb_jxzzjg yx on xj.zy_id = yx.id ";
		}
		String beginDate = json.getString("fromDate");
		String endDate = json.getString("toDate");
		String isfast = json.containsKey("isfast")?json.get("isfast").toString():"1";
		String table="";
		if(isfast.equals("1")){
			table="tb_ykt_mjmx_tsg_log";
		}else{
			table="tb_ykt_mjmx_tsg";
		}
		String sql = "select count(t.sksj) value,t.yx field, '"+titleName+"' name from (" +
				"  select to_date(mj.sksj,'yyyy-mm-dd hh24:mi:ss') sksj,yx.mc yx from "+table+" mj" +
				"  left join tb_ykt_kh kh on kh.kh = mj.dzzh" +
				"  inner join tb_xjda_xjxx xj on kh.xgh = xj.xh" +
				lj +
				" where mj.sksj between '" + beginDate  + "' and '" + endDate + "' " +
				tj +
				" ) t group by t.yx";
	    List result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
}
