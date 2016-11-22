package com.jhkj.mosdc.sc.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.OaService;

public class OaServiceImpl implements OaService{

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Override
	public String getAllLoginLog(String params) {
		String sql = "select nvl(a.all_login_counts,0) all_login_counts, nvl(trunc(a.all_login_counts/b.days,2),0) avg_day_login from"
				+" (select count(*) all_login_counts from t_oa_login lg) a "
				+" inner join"
				+" (select (trunc(sysdate)) - (to_date(substr((select min(login_time) from t_oa_login),0,10),'yyyy-mm-dd')) days from dual) b on 1=1";
		return Struts2Utils.list2json(baseDao.querySqlList(sql));
	}

	@Override
	public String getLoginLog(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String fromTime = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String endTime = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		
		String sql = "select nvl(a.login_counts,0) login_counts,nvl(trunc(a.login_counts/b.days,2),0) avg_day_login from"
				+ " (select count(*) login_counts from t_oa_login lg where lg.login_time between '"
				+ fromTime + "' and '" + endTime + "')a"
				+ " inner join (select (to_date('"+ endTime +"','yyyy-mm-dd')) - (to_date('"+ fromTime +"','yyyy-mm-dd')) days from dual)b on '1'= '1'";
		return Struts2Utils.list2json(baseDao.querySqlList(sql));
	}

	@Override
	public String getAllUserLoginCounts(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String fromTime = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String endTime = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		
		String excuteSql = " select jzgxx.xm jzg_name, nvl(l.login_counts,0) login_counts from tb_jzgxx jzgxx left join"
				+ " (select lg.user_id, count(*) login_counts from t_oa_login lg where lg.login_time between '"
				+ fromTime+ "' and  '"+ endTime
				+ "'	group by lg.user_id) l on l.user_id = jzgxx.zgh order by login_counts desc";
		
		Map paramsMap = Utils4Service.packageParams(params);
		
		Map result = baseDao.queryTableContentBySQL(excuteSql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
		
		
		
	}

	@Override
	public String getAllNotice(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String fromTime = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String endTime = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		
		String sql = "select t.id notice_id, t.theme theme,nvl(c.check_counts,0) check_counts, t.create_time create_time from t_oa_notice t "
				+ "	left join (select ch.notice_id,count(*) check_counts from t_oa_notice_check ch where ch.check_time between '"
				+ fromTime + "' and '"+ endTime +"' group by ch.notice_id order by check_counts desc) c "
				+ " on c.notice_id = t.id order by check_counts desc";
		
		Map paramsMap = Utils4Service.packageParams(params);
		
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
		
	}

	@Override
	public String getNoticeCheckByDept(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String fromTime = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String endTime = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		String notice_id = json.containsKey("notice_id")?json.getString("notice_id"):"10004";
		
		String sql = "select t.*,rownum n from ("
				+ "  select dept.name_ dept_name,nvl(c.notice_check_counts,0) check_counts from t_oa_dept dept left join"
				+ "  (select ch.dept_id dept_id, count(*) notice_check_counts from t_oa_notice_check ch where ch.notice_id = '"+notice_id+"' and ch.check_time between "
				+ " '"+fromTime+"' and '"+endTime+"'"
				+ " group by ch.dept_id order by notice_check_counts desc) c on c.dept_id = dept.id order by check_counts desc) t";
		
		Map paramsMap = Utils4Service.packageParams(params);
		
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getFormUse(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String fromTime = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String endTime = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		
		String sql = "select t.*,rownum n from(select f.name form_name, nvl(c.form_use_counts,0) use_counts,  nvl(trunc(c.form_use_counts/d.days,2),0) avg_day_use from t_oa_form f left join"
				+ " (select fu.form_id form_id, count(*) form_use_counts from t_oa_form_use fu where fu.use_time between '"+fromTime+"' and '"+endTime+"' group by fu.form_id order by form_use_counts desc) c "
				+ " on f.id = c.form_id"
				+ " left join "
				+ " (select fm.id form_id,(to_date('"+ endTime +"','yyyy-mm-dd')) - (to_date('"+ fromTime +"','yyyy-mm-dd')) days from t_oa_form fm) d on f.id = d.form_id order by use_counts desc)t";

		Map paramsMap = Utils4Service.packageParams(params);
		
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getCheckByDept(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String fromTime = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String endTime = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		
		String sql = "select t.*, rownum n from"
				+ " (select dept.name_ dept_name,nvl(d.dept_check_counts,0) dept_check_counts from t_oa_dept dept "
				+ "  left join (select nc.dept_id,count(*)dept_check_counts from t_oa_notice_check nc where nc.check_time between "
				+ " '"+ fromTime +"' and '"+ endTime +"' group by nc.dept_id  order by dept_check_counts desc) d"
				+ " on d.dept_id = dept.id order by dept_check_counts desc) t";

		Map paramsMap = Utils4Service.packageParams(params);
		
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}

	@Override
	public String getNoticeCountsByUser(String params) {
		String sql = "select jzgxx.xm jzg_name,nvl(c.check_notices,0) check_notices, "
				+ " nvl((a.all_notices - c.check_notices),0) no_check_notices from tb_jzgxx jzgxx "
				+ " left join(select count(*) all_notices from t_oa_notice) a on '1' = '1'"
				+ " left join (select ch.user_id user_id,count(*) check_notices from t_oa_notice_check ch inner join t_oa_notice notice "
				+ " on notice.id = ch.notice_id group by ch.user_id) c "
				+ " on jzgxx.zgh = c.user_id "
				+ " order by check_notices desc";

		Map paramsMap = Utils4Service.packageParams(params);
		
		Map result = baseDao.queryTableContentBySQL(sql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	
	public String[] getStartEndDate(){
		String[] startend = new String[2];
		// 如果月份在1-7 则学年为当前年份-1
		Calendar cal = Calendar.getInstance();
	     int month = cal.get(Calendar.MONTH) + 1;
	     int year = cal.get(Calendar.YEAR);
		// 如果月份在8-12月份 ，则学年为当前年份
		startend[0] = month<7?year-1+"-06-01":year+"-06-01";
		startend[1] = month<7?year+"-07-01":(year+1)+"-07-01";
		return startend;
	}


}
