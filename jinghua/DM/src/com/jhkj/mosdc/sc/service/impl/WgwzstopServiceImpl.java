package com.jhkj.mosdc.sc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.newoutput.util.DateUtils;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.WgwzstopService;

public class WgwzstopServiceImpl implements WgwzstopService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	@Override
	public String getXbInfoByJd(String params) {
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
				List<Map<String,Object>> result = baseDao.querySqlList(sql);
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
			tj=" and yx.id ='"+zzjgId+"'";
		}else{
			tj=" and zy.id ='"+zzjgId+"'";
		}
		Integer year = Integer.parseInt(DateUtils.getYear());
		String sql = "select * from (select p_m.*, rownum nm "
				+ " from (select t.*, xjxx.xm, xb.mc as xb, zy.mc as zy, yx.mc as yx,substr(xjxx.xh,0,4) as nj"
				+ " from (select xh, sum(bfzkscj) as zcj, round(avg(bfzkscj), 2) pjcj,round(sum(hdxf*xf)/sum(xf) ,2) gpa"
				+ " from (select t.*,case"
				+ " when t.bfzkscj between 90 and 100 then 4"
				+ " when t.bfzkscj between 85 and 89 then 3.7"
				+ " when t.bfzkscj between 82 and 84 then 3.3"
				+ "  when t.bfzkscj between 78 and 81 then 3.0"
				+ " when t.bfzkscj between 75 and 77 then 2.7"
				+ " when t.bfzkscj between 71 and 74 then 2.3"
				+ " when t.bfzkscj between 66 and 70 then 2.0"
				+ " when t.bfzkscj between 62 and 65 then 1.7"
				+ " when t.bfzkscj between 60 and 61 then 1.3"
				+ " when t.bfzkscj between 0 and 59 then 0"
				+ "  end as hdxf,kcxx.xf"
				+ " from t_xs_kscj t left join t_kcxx kcxx on kcxx.kcdm = t.kcdm where djzkscj is null)"
				+ " group by xh) t" + " left join tb_xjda_xjxx xjxx"
				+ " on xjxx.xh = t.xh" + " left join dm_zxbz.t_zxbz_xb xb"
				+ " on xjxx.xb_id = xb.wid" + " left join tb_jxzzjg zy"
				+ " on xjxx.zy_id = zy.id" + " left join tb_jxzzjg yx"
				+ " on yx.id = xjxx.yx_id"
				+ " where to_number(substr(xjxx.yjbysj, 0, 4)) -"
				+ " to_number(to_char(sysdate, 'yyyy')) = 1"
				+ " and xjxx.xh like '"+(year-3)+"%' and zcj > 3000"
				+tj
				+ " order by gpa desc,pjcj desc, zcj desc) p_m) p_n"
				+ " where p_n.nm > 0" + " and p_n.nm <= 5";
   List result = baseDao.queryListMapInLowerKeyBySql(sql);
	return Struts2Utils.list2json(result);
	}
	@Override
	public String getXbInfo(String params) {
		Integer year = Integer.parseInt(DateUtils.getYear());
		String sql = "select * from (select p_m.*, rownum nm from (select t.*,xjxx.xm,xb.mc as xb,zy.mc as zy,yx.mc as yx ,substr(xjxx.xh,0,4) as nj from "
				+ "(select xh,sum(kscj) as zcj,round(avg(kscj),2) pjcj from ( "
				+ "select t.*,case when t.djzkscj='优秀'then 95  "
				+ "when t.djzkscj is null then t.bfzkscj   "
				+ "when t.djzkscj='合格'then 60  "
				+ "when t.djzkscj='良好'then 85  "
				+ "when t.djzkscj='不及格'then 50  "
				+ "when t.djzkscj='不合格'then 50  "
				+ "when t.djzkscj='中等'then 70  "
				+ "when t.djzkscj='及格'then 60    "
				+ "end as kscj from t_xs_kscj t where djzkscj is null) group by xh  ) t "
				+ "left join tb_xjda_xjxx xjxx on xjxx.xh = t.xh  "
				+ "left join dm_zxbz.t_zxbz_xb xb on xjxx.xb_id = xb.wid "
				+ "left join tb_jxzzjg zy on xjxx.zy_id = zy.id  "
				+ " left join tb_jxzzjg yx on yx.id = xjxx.yx_id  " +
				"where to_number(substr(xjxx.yjbysj,0,4))-to_number(to_char(sysdate,'yyyy')) =1 " +
				"and xjxx.xh like '"+(year-3)+"%' and zcj>3000"
				+ " order by pjcj desc, zcj desc)p_m )p_n where p_n.nm >0 and p_n.nm <= 5";
		List result = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.sc.service.WgwzstopService#wgTopTen(java.lang.String)
	 */
	@Override
	public String wgTopTen(String params) {
		JSONObject json = JSONObject.fromObject(params);
		int num = json.getInt("num");
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
				List<Map<String,Object>> result = baseDao.querySqlList(sql);
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
			tj=" and t.yx_id ='"+zzjgId+"'";
		}else{
			tj=" and t.zy_id ='"+zzjgId+"'";
		}
		String sql = "select * from (select p_m.*, rownum nm from (" +
				"select  tt.xh, tt.xm,count(1) ts from (select t.xh,t.xm, " +
				"to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd')" +
				" from tb_ykt_temp_wg t where to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm') =  to_char(sysdate-"+(num-1)+"*30, 'yyyy-mm')" +
				tj+
				" group by  to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd')," +
				"t.xh,t.xm)tt group by tt.xh,tt.xm order by count(1) desc" +
				")p_m )p_n where p_n.nm >0 and p_n.nm <= 10";
		List result = baseDao.queryListMapInLowerKeyBySql(sql); 
		return Struts2Utils.list2json(result);
	}

	/* (non-Javadoc)
	 * @see com.jhkj.mosdc.sc.service.WgwzstopService#wzsTopTen(java.lang.String)
	 */
	@Override
	public String wzsTopTen(String params) {
		JSONObject json = JSONObject.fromObject(params);
		int num = json.getInt("num");
		String zzjgPid = json.containsKey("zzjgPid")?json.get("zzjgPid").toString():"-1";
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		//第一次进入时无法判断其权限，故先判断是否是第一次进入
		if(!json.containsKey("zzjgPid")){
			User user = UserPermiss.getUser();
			String jxzzjgids = user.getCurrentJxzzjgIds();
			if("".equals(jxzzjgids)||jxzzjgids==null){
				zzjgPid ="-1";
			}else{
				String sql ="SELECT * FROM TB_JXZZJG WHERE SFKY=1 AND id in ("+jxzzjgids+") and id !=0";
				List<Map<String,Object>> result = baseDao.querySqlList(sql);
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
			tj=" and t.yx_id ='"+zzjgId+"' ";
		}else{
			tj=" and t.zy_id ='"+zzjgId+"' ";
		}
		String sql = "select * from (select p_m.*, rownum nm from (" +
				"select  tt.xh, tt.xm,count(1) ts from (select t.xh,t.xm, " +
				"to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd')" +
				" from tb_ykt_temp_wzs t where to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm') =  to_char(sysdate-"+(num-1)+"*30, 'yyyy-mm')" +
				tj+
				" group by  to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd')," +
				"t.xh,t.xm )tt group by tt.xh,tt.xm order by count(1) desc" +
				")p_m )p_n where p_n.nm >0 and p_n.nm <= 10";
		List result = baseDao.queryListMapInLowerKeyBySql(sql); 
		return Struts2Utils.list2json(result);
	}

	@Override
	public String stuInfo(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String xh = json.getString("xh");
		String sql = "select t.xh,t.xm, xb.mc xb,yx.mc yx,zy.mc zy,syd.mc syd from tb_xjda_xjxx t" +
				" left join tb_jxzzjg zy on t.zy_id = zy.id" +
				" left join tb_jxzzjg yx on yx.id = t.yx_id" +
				" left join tc_xxbzdmjg xb on xb.id = t.xb_id" +
				" left join DM_ZXBZ.t_zxbz_xzqh syd on t.sydsx_id = syd.wid where t.xh = '"+xh+"'";
		List<Map> result = baseDao.queryListMapInLowerKeyBySql(sql); 
		return Struts2Utils.map2json(result.get(0));
	}

	@Override
	public String stuWginfo(String params) {
		JSONObject json = JSONObject.fromObject(params);
		int num = json.getInt("num");
		String xh = json.getString("xh");
		String sql = "select t.xh,t.xm,to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd') rq" +
				" from tb_ykt_temp_wg t where to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm') = " +
				" to_char(sysdate-"+(num-1)+"*30, 'yyyy-mm') and t.xh = '"+xh+"'" +
				" group by  to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd'),t.xh,t.xm  order by to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd') ";
		List result = baseDao.queryListMapInLowerKeyBySql(sql); 
		return Struts2Utils.list2json(result);
	}

	@Override
	public String stuWzsinfo(String params) {
		JSONObject json = JSONObject.fromObject(params);
		int num = json.getInt("num");
		String xh =  json.getString("xh");
		String sql = "select t.xh,t.xm, to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd') rq" +
				" from tb_ykt_temp_wzs t where to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'yyyy-mm') =  " +
				"to_char(sysdate-"+(num-1)+"*30, 'yyyy-mm') and t.xh = '"+xh+"'" +
				" group by  to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd'),t.xh,t.xm  order by to_char(to_date(t.tjsj, 'yyyy-mm-dd hh24:mi:ss'), 'dd') ";
		List result = baseDao.queryListMapInLowerKeyBySql(sql); 
		return Struts2Utils.list2json(result);
	}

	@Override
	public String stuXfinfo(String params) {
		JSONObject json = JSONObject.fromObject(params);
		int num = json.getInt("num");
		String xh =  json.getString("xh");
		String sql ="select sum(t.xfje) xfje,count(1) xfcs from tb_ykt_xfmx t left join tb_xjda_xjxx xj on xj.id = t.ryid " +
				" where xj.xh = '"+xh+"' and to_char(t.xfsj_date,'yyyymm') = to_char(sysdate-"+num+"*30,'yyyymm')";
		List result = baseDao.queryListMapInLowerKeyBySql(sql); 
		return Struts2Utils.map2json((Map) result.get(0));
	}

	@Override
	public String stuZsinfo(String params) {
		JSONObject json = JSONObject.fromObject(params);
		int num = json.getInt("num");
		String xh =  json.getString("xh");
		String sql ="select xj.xm xm,ly.mc ly,fj.fjdm fj ,cw.mc cw from tb_dorm_zy zy left join tb_dorm_cw cw on zy.cw_id = cw.id left join  tb_dorm_fj fj on fj.id = cw.fj_id " +
				" left join tb_dorm_ccjg lc on lc.id = fj.fjd_id left join tb_dorm_ccjg ly on lc.fjd_id = ly.id left join tb_xjda_xjxx xj on xj.id = zy.xs_id where xj.xh = "+xh;
		List result = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	public String stuTsjy(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String xh =  json.getString("xh");
		String sql = "select substr(t.jsrq,0,10) jsrq,t.yhrq yhrq,t.tsmc from t_ts_jy t where t.sfrzh = '"+xh+"'";
		List result = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@Override
	public String stuKscj(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String xh = json.getString("xh");
		String sql = "select t.xn,case when t.xq=0 then '第一学期' when t.xq=1 then '第二学期' end as xq,kc.kcmc" +
				",case when djzkscj is null then bfzkscj else null end bfzkscj,djzkscj,kc.xf kcxf,case"
				+ " when t.bfzkscj between 90 and 100 then 4"
				+ " when t.bfzkscj between 85 and 89 then 3.7"
				+ " when t.bfzkscj between 82 and 84 then 3.3"
				+ "  when t.bfzkscj between 78 and 81 then 3.0"
				+ " when t.bfzkscj between 75 and 77 then 2.7"
				+ " when t.bfzkscj between 71 and 74 then 2.3"
				+ " when t.bfzkscj between 66 and 70 then 2.0"
				+ " when t.bfzkscj between 62 and 65 then 1.7"
				+ " when t.bfzkscj between 60 and 61 then 1.3"
				+ " when t.bfzkscj between 0 and 59 then 0"
				+ "  end as cjjd" +
				" from t_xs_kscj t left join t_kcxx kc  on t.kcdm = kc.kcdm where t.xh = '"
				+ xh + "' order by t.xn,t.xq";
		List result = baseDao.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}
	
	@Override
	public String zjpkInfo(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String xh = json.getString("xh");
		
		// 是否是贫困生
		String sql ="SELECT BDBS,CFCS,BDBL FROM TB_YKT_TEMP_DXF_RESULT WHERE RYID='"+xh+"'";
		String zxjSql ="SELECT XN,XQDM,ZXJMC,ZXJE FROM T_BZKS_ZXJ WHERE XH='"+xh+"'";
		String jxjSql ="SELECT XN,XQDM,JXJMC,JXJE FROM T_BZKS_JXJ WHERE XH='"+xh+"'";
		
		List result = baseDao.queryListMapInLowerKeyBySql(sql);
		List zxjresult = baseDao.queryListMapInLowerKeyBySql(zxjSql);
		List jxjresult = baseDao.queryListMapInLowerKeyBySql(jxjSql);
		
		Map backval = new HashMap();
		backval.put("pks", result);
		backval.put("zxj", zxjresult);
		backval.put("jxj", jxjresult);
		return Struts2Utils.map2json(backval);
	}
}