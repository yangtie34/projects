package com.jhkj.mosdc.sc.service.impl;

import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.StringUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.UserPermiss;
import com.jhkj.mosdc.sc.service.SslzsqkService;

public class SslzsqkServiceImpl implements SslzsqkService {
	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public String queryAllDorms(String params) {
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		String sql="";
		if("".equals(jxzzjgids)){
			sql = "SELECT L.ID SSID,L.MC NAME,COUNT(DISTINCT(C.ID)) CS ," +
					"COUNT(DISTINCT(FJ.ID)) FJS,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L " +
					"LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID " +
					"LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID " +
					"LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID " +
					"WHERE L.CCLX = 'LY'GROUP BY L.MC,L.ID ORDER BY L.ID";
		}else{
			sql="SELECT SSID,NAME,CS,FJS,CWS FROM (SELECT B.SSID,B.NAME,B.CS,B.FJS,B.CWS,(A.CWS/B.CWS*100) ZSL FROM ( SELECT L.ID SSID,L.MC NAME "+
					  " ,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L" +
					     "   LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID " +
					     "   LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID " +
					     "   LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID " +
					     "  LEFT JOIN TB_DORM_ZY ZY ON ZY.CW_ID = CW.ID" +
					     "    LEFT JOIN TB_XJDA_XJXX XJ ON XJ.ID = ZY.XS_ID " +
					     "   LEFT JOIN TB_XZZZJG BZ ON BZ.ID = XJ.YX_ID" +
					     "   WHERE L.CCLX = 'LY'AND BZ.ID IN ("+jxzzjgids+") GROUP BY L.MC,L.ID ORDER BY L.ID ) A" +
					     "   INNER JOIN (SELECT L.ID SSID,L.MC NAME,COUNT(DISTINCT(C.ID)) CS ," +
						 "			COUNT(DISTINCT(FJ.ID)) FJS,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L" +
						"		LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID " +
						"		LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID " +
					    "  LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID " +
					    "  WHERE L.CCLX = 'LY'GROUP BY L.MC,L.ID ORDER BY L.ID) B ON A.SSID =B.SSID ) WHERE ZSL>=50";
		}
		
		return Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryDormZyxb(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String lyid = json.getString("ssid");
		String sql = "select count(zy.id) y,bz.mc name from tb_dorm_ccjg c" +
				" inner join tb_dorm_ccjg fj on fj.fjd_id = c.id" +
				" inner join tb_dorm_cw cw on fj.id = cw.fj_id" +
				" inner join tb_dorm_zy zy on zy.cw_id = cw.id" +
				" left join tc_xxbzdmjg bz on bz.id = zy.xb_id" +
				" where c.fjd_id = {0} group by bz.mc";
		sql = StringUtils.format(sql, lyid);
		return Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryDormXsszyx(String params){
		JSONObject json = JSONObject.fromObject(params);
		String lyid = json.getString("ssid");
		String sql = "select count(zy.id) value,bz.mc field,''院系人数'' name" +
				" from tb_dorm_ccjg c inner join tb_dorm_ccjg fj on fj.fjd_id = c.id" +
				" inner join tb_dorm_cw cw on fj.id = cw.fj_id" +
				" inner join tb_dorm_zy zy on zy.cw_id = cw.id" +
				" inner join tb_xjda_xjxx xj on xj.id = zy.xs_id " +
				" left join tb_xzzzjg bz on bz.id = xj.yx_id" +
				" where c.fjd_id = {0} group by bz.mc";
		sql = StringUtils.format(sql, lyid);
		return Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryDormInfo(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String lyid = json.getString("ssid");
		String sql = "SELECT L.ID SSID,L.MC NAME,COUNT(DISTINCT(C.ID)) CS ,COUNT(DISTINCT(FJ.ID)) FJS,COUNT(CW.ID) CWS,COUNT(ZY.ID) YZRS,ROUND(COUNT(ZY.ID)/COUNT(CW.ID),2)*100||''%''  RZL" +
				" FROM TB_DORM_CCJG L LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID " +
				" LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID" +
				" LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID" +
				" LEFT JOIN TB_DORM_ZY ZY ON ZY.CW_ID = CW.ID " +
				"WHERE L.CCLX = ''LY'' AND L.ID = {0} GROUP BY L.MC,L.ID";
		sql = StringUtils.format(sql, lyid);
		return Struts2Utils.map2json(baseDao.queryListMapInLowerKeyBySql(sql).get(0));
	}

	@Override
	public String queryDormLc(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String lyid = json.getString("ssid");
		String sql = "select c.id ,c.mc name from tb_dorm_ccjg c where c.fjd_id = {0}";
		sql = StringUtils.format(sql, lyid);
		return Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryDormFjxx(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String lc = json.get("lc").toString();
		int lx = Integer.parseInt(json.getString("lx"));
		Map paramsMap = Utils4Service.packageParams(params);
		String sql ="select tt.* from (select fj.mc fjh,fj.dm fjdm,max(cw.cwbz) sfbz,'''' zsxb, count(cw.id) cws,count(zy.id) yzsrs,count(cw.id)-count(zy.id) kcws from tb_dorm_ccjg fj " +
				" left join tb_dorm_cw cw on cw.fj_id = fj.id " +
				" left join tb_dorm_zy zy on zy.cw_id = cw.id where fj.fjd_id = {0} group by fj.mc,fj.dm order by fj.mc)tt where 1=1 {1}";
		String tj = "";
		switch (lx) {
			case 1: break;
			case 2: tj = "and tt.kcws = 0"; break;
			case 3: tj = "and tt.kcws > 0"; break;
			default: break;
		}
		sql = StringUtils.format(sql,lc,tj);
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		result.put("success", true);
		result.put("data",result.get("queryList"));
		return Struts2Utils.map2json(result);
	}

	@Override
	public String queryDormRzl(String params) {
		User user = UserPermiss.getUser();
		String jxzzjgids = user.getCurrentJxzzjgIds();
		String sql="";
		if ("".equals(jxzzjgids)||jxzzjgids==null) {
			sql = "SELECT '宿舍楼入住率(%)' name, L.MC field,COUNT(CW.ID) CWS,COUNT(ZY.ID) YZRS,ROUND(COUNT(ZY.ID)/COUNT(CW.ID),2)*100 value FROM TB_DORM_CCJG L LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID "
					+ " LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID"
					+ " LEFT JOIN TB_DORM_ZY ZY ON ZY.CW_ID = CW.ID WHERE L.CCLX = 'LY' GROUP BY L.MC,L.ID ORDER BY L.ID";
		} else {
			String sslSql = "SELECT SSID FROM (SELECT B.SSID,B.NAME,B.CS,B.FJS,B.CWS,(A.CWS/B.CWS*100) ZSL "
					+ " FROM ( SELECT L.ID SSID,L.MC NAME ,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L "
					+ " LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID  "
					+ " LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID  "
					+ " LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID  "
					+ " LEFT JOIN TB_DORM_ZY ZY ON ZY.CW_ID = CW.ID "
					+ " LEFT JOIN TB_XJDA_XJXX XJ ON XJ.ID = ZY.XS_ID  "
					+ " LEFT JOIN TB_XZZZJG BZ ON BZ.ID = XJ.YX_ID "
					+ " WHERE L.CCLX = 'LY'AND BZ.ID IN ("+ jxzzjgids+ ") GROUP BY L.MC,L.ID ORDER BY L.ID ) A "
					+ " INNER JOIN (SELECT L.ID SSID,L.MC NAME,COUNT(DISTINCT(C.ID)) CS , "
					+ " COUNT(DISTINCT(FJ.ID)) FJS,COUNT(CW.ID) CWS FROM TB_DORM_CCJG L "
					+ " LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID  "
					+ " LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID  "
					+ " LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID  "
					+ " WHERE L.CCLX = 'LY'GROUP BY L.MC,L.ID ORDER BY L.ID) B ON A.SSID =B.SSID) WHERE ZSL>=50 ";
			sql = "SELECT '宿舍楼入住率(%)' name, L.MC field,COUNT(CW.ID) CWS,COUNT(ZY.ID) YZRS,ROUND(COUNT(ZY.ID)/COUNT(CW.ID),2)*100 value "
					+ " FROM TB_DORM_CCJG L LEFT JOIN TB_DORM_CCJG C ON L.ID = C.FJD_ID "
					+ " LEFT JOIN TB_DORM_CCJG FJ ON C.ID = FJ.FJD_ID LEFT JOIN TB_DORM_CW CW ON CW.FJ_ID = FJ.ID"
					+ " LEFT JOIN TB_DORM_ZY ZY ON ZY.CW_ID = CW.ID"
					+ " WHERE L.CCLX = 'LY' AND L.ID IN ("+ sslSql + ") GROUP BY L.MC,L.ID ORDER BY L.ID";
		}
		return Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryDormjcrs(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String ssid = json.getString("ssid");
		String date = json.getString("date");
		String sql = "select '进出人次' name, count(t.id) value, to_char(t.sksj, 'hh24') field from tb_ykt_mjmx t " +
				" inner join tb_xjda_xjxx xj on t.xh = xj.xh " +
				" inner join tb_dorm_zy zy on zy.xs_id = xj.id " +
				" inner join tb_dorm_cw cw on cw.id = zy.cw_id " +
				" inner join tb_dorm_ccjg fj on cw.fj_id = fj.id " +
				" inner join tb_dorm_ccjg lc on fj.fjd_id = lc.id " +
				" where lc.fjd_id  = "+ssid+" and to_char(t.sksj, 'yyyy-mm-dd') = '"+date+"'" +
				" group by to_char(t.sksj, 'hh24') order by to_char(t.sksj, 'hh24')";
		return Struts2Utils.list2json(baseDao.queryListMapInLowerKeyBySql(sql));
	}
	
	
}
