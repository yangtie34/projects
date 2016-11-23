package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.SqlParamsChange;
import com.jhkj.mosdc.newoutput.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.StudentFromService;

public class StudentFromServiceImpl extends BaseServiceImpl implements StudentFromService{
	static String ncid = "";
	static String xzid = "";
	static String csid = "";
	static String nanid = "";
	static String nvid = "";
	static String czid = "";
	static String gzid = "";
	@Override
	public String getCountZbs(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String start = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String end = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String whereSql =" and rxrq between '"+start+"' and '"+end+"' ";
		String str2 = " AND ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		if("".equals(ncid)){
			ncid = baseDao.queryIdByBm("1", "XXDM-HKXZ").toString();// 农村id
			xzid = baseDao.queryIdByBm("2", "XXDM-HKXZ").toString();// 县镇id
			csid = baseDao.queryIdByBm("3", "XXDM-HKXZ").toString();// 城市id
			nanid = baseDao.queryIdByBm("1", "DM-RDXB").toString();// 男id
			nvid = baseDao.queryIdByBm("2", "DM-RDXB").toString();// 女id
			czid = baseDao.queryIdByBm("4", "XXDM-JDXL").toString();// 初中id
			gzid = baseDao.queryIdByBm("5", "XXDM-JDXL").toString();// 高中id
		}
		
		String sql = "select count(id) as zs,''rxzrs'' as lx from tb_xjda_xjxx where 1=1 {0} {6} "
				+ "union all select count(id) as zs,''wwhrs'' as lx from tb_xjda_xjxx where 1=1 and hkszd_id is null {0} {6} "
				+ "union all select count(id) as zs,''nchkrs'' as lx from tb_xjda_xjxx where 1=1 and hklx_id = {1} {0} {6} "
				+ "union all select count(id) as zs,''xzhkrs'' as lx from tb_xjda_xjxx where 1=1 and hklx_id = {2} {0} {6} "
				+ "union all select count(id) as zs,''cshkrs'' as lx from tb_xjda_xjxx where 1=1 and hklx_id = {3} {0} {6} "
				+ "union all select count(id) as zs,''nsrs'' as lx from tb_xjda_xjxx where 1=1 and xb_id = {4} {0} {6} "
				+ "union all select count(id) as zs,''nvsrs'' as lx from tb_xjda_xjxx where 1=1 and xb_id = {5} {0} {6} "
				/*+ "union all select count(id) as zs,''czqdrs'' as lx from tb_xjda_xjxx where 1=1 and rxqxl_id = {12} {5} "
				+ "union all select count(id) as zs,''gzqdrs'' as lx from tb_xjda_xjxx where 1=1 and rxqxl_id = {13} {6} "*/;
		
		sql = MessageFormat.format(sql, whereSql,ncid,xzid,csid,nanid,nvid,str2);
		
		List result = baseDao.querySqlList(sql);
		int rxzrs = 1;
		for(Object obj : result){
			Map map = (Map)obj;
			int zs = Integer.parseInt(map.get("ZS").toString());
			String lx = map.get("LX").toString();
			if("rxzrs".equals(lx)){
				rxzrs = zs;
			}
			
			float num= rxzrs==0?0:(float)zs/rxzrs*100;
	        DecimalFormat df = new DecimalFormat("0.00");
			map.put("ZB", df.format(num));
		}
		Map backval = new HashMap();
		backval.put("list", result);
		backval.put("from", start);
		backval.put("to", end);
		return Struts2Utils.map2json(backval);
	}
	protected Map getZzjgNode(String zzjgid){
		Map backVal = new HashMap();
		String sql ="Select * from tb_jxzzjg where id ="+zzjgid;
		List<Map> result = baseDao.querySqlList(sql);
		if(result.size()!=0){
			backVal.put("id", result.get(0).get("ID"));
			backVal.put("qxm",result.get(0).get("QXM"));
			backVal.put("mc",result.get(0).get("MC"));
		}
		
		return backVal;
	}
	@Override
	public String getCountByZbId(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String start = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String end = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		String zbId = json.containsKey("zbId")?json.getString("zbId"):"rxzrs";
		String whereSql =" and rxrq between '"+start+"' and '"+end+"' ";
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String str2 = " AND ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		// 计算默认指标下的地区分布 ，默认指标为来校人数.
		String countSql ="SELECT COUNT(*) AS ZS FROM TB_XJDA_XJXX where 1=1 {0}";
		countSql = MessageFormat.format(countSql, whereSql);
		int qxCount = baseDao.querySqlCount(countSql);
		String zbSql = getSqlByZbId(zbId);
		// 以学生户口所在地信息进行统计分析。
		
		String statSql = "select t1.qxm,t1.mc,case when t2.zs is null then 0 else t2.zs end as zs from tc_xzqh t1 left join ("
				+ "select t1.sqxm as qxm, xzqh.mc, count(*) as zs from tb_xjda_xjxx_sydtj t1 inner join (SELECT id "
				+ "FROM TB_XJDA_XJXX where 1 = 1 " + whereSql + zbSql + str2+") xjxx on xjxx.id = t1.xs_id "
				+ "left join tc_xzqh xzqh on xzqh.qxm = t1.sqxm group by t1.sqxm, xzqh.mc) t2 on t2.qxm = t1.qxm "
				+ "where t1.qxm like '16%' and cc =2 ";
		List result = baseDao.querySqlList(statSql);
		
		for(Object obj : result){
			Map map = (Map) obj;
			String qxm = map.get("QXM")==null?"":map.get("QXM").toString();
			String mc = map.get("MC")==null?"":map.get("MC").toString();
			int zs =  Integer.parseInt(map.get("ZS").toString());
			float num= qxCount==0?0:(float)zs/qxCount*100;
	        DecimalFormat df = new DecimalFormat("0.00");
	        
	        map.put("ZB", df.format(num));
		}
		
		
		return Struts2Utils.list2json(result);
	}
	@Override
	public String queryByxxGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String start = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String end = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		String whereSql =" and rxrq between '"+start+"' and '"+end+"' ";
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String str2 = " AND ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		// 以学生户口所在地信息进行统计分析。
		String statSql ="SELECT T1.* FROM (SELECT ybyxx,count(*) as rs FROM TB_XJDA_XJXX where 1=1 "+whereSql+str2+" group by ybyxx ORDER BY RS DESC) T1 WHERE 1=1";
		Map paramsMap = null;
		try {
			paramsMap = SqlParamsChange.getSQLParams(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map resultMap = baseDao.queryTableContentBySQL(statSql,paramsMap);
		List temp = (List)resultMap.get("queryList");
		for(Object obj : temp){
			Map map = (Map)obj;
			String ybyxx = map.get("YBYXX")==null?"":map.get("YBYXX").toString();
			String rs = map.get("RS")==null?"":map.get("RS").toString();
			
			map.put("ybyxx", ybyxx);
			map.put("rs", rs);
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("count",  resultMap.get("count"));
		result.put("success", true);
		result.put("data", temp);
		return Struts2Utils.map2json(result);
	}
	@Override
	public String queryGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String[] startend = getStartEndDate();
		String start = json.containsKey("fromDate")?json.getString("fromDate"):startend[0];
		String end = json.containsKey("toDate")?json.getString("toDate"):startend[1];
		String whereSql =" and rxrq between '"+start+"' and '"+end+"' ";
		String zzjgId = json.containsKey("zzjgId")?json.get("zzjgId").toString():"0";
		Map node = getZzjgNode(zzjgId);
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		// 以学生户口所在地信息进行统计分析。
		String statSql ="SELECT T1.* FROM (select t1.sqxm as qxm,xzqh.mc,count(*) as zs from tb_xjda_xjxx_sydtj t1" +
				" inner join (SELECT id FROM TB_XJDA_XJXX where 1=1 "+whereSql+") xjxx on xjxx.id = t1.xs_id " +
				" left join tc_xzqh xzqh on xzqh.qxm = t1.sqxm where t1.sqxm like '16%' group by t1.sqxm,xzqh.mc order by zs desc) T1 WHERE 1=1";
		Map paramsMap = null;
		try {
			paramsMap = SqlParamsChange.getSQLParams(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map resultMap = baseDao.queryTableContentBySQL(statSql,paramsMap);
		List temp = (List)resultMap.get("queryList");
		for(Object obj :temp){
			Map map = (Map) obj;
			String qxm = map.get("QXM")==null?"":map.get("QXM").toString();
			String likeQxm = qxm+"%";
			String mc = map.get("MC")==null?"":map.get("MC").toString();
			int zs =  Integer.parseInt(map.get("ZS").toString());
			/*float num= zs==0?0:(float)zs/zs*100;
	        DecimalFormat df = new DecimalFormat("0.00");*/
			map.put("dq", mc);
			String sql = "select count(xjxx.id) as zs,''rxzrs'' as lx from tb_xjda_xjxx xjxx  left join tc_xzqh xzqh on xzqh.id =xjxx.hkszd_id where 1=1 and xzqh.qxm like ''{21}''  {0} {22} "
					+ "union all select count(xjxx.id) as zs,''nchkrs'' as lx from tb_xjda_xjxx xjxx left join tc_xzqh xzqh on xzqh.id =xjxx.hkszd_id where 1=1 and hklx_id = {7}  and xzqh.qxm like ''{14}'' {1} {22} "
					+ "union all select count(xjxx.id) as zs,''xzhkrs'' as lx from tb_xjda_xjxx xjxx left join tc_xzqh xzqh on xzqh.id =xjxx.hkszd_id where 1=1 and hklx_id = {8}  and xzqh.qxm like ''{15}'' {2} {22} "
					+ "union all select count(xjxx.id) as zs,''cshkrs'' as lx from tb_xjda_xjxx xjxx left join tc_xzqh xzqh on xzqh.id =xjxx.hkszd_id where 1=1 and hklx_id = {9}  and xzqh.qxm like ''{16}'' {2} {22} "
					+ "union all select count(xjxx.id) as zs,''nsrs'' as lx from tb_xjda_xjxx xjxx left join tc_xzqh xzqh on xzqh.id =xjxx.hkszd_id where 1=1 and xb_id = {10} and xzqh.qxm like ''{17}'' {3} {22} "
					+ "union all select count(xjxx.id) as zs,''nvsrs'' as lx from tb_xjda_xjxx xjxx left join tc_xzqh xzqh on xzqh.id =xjxx.hkszd_id where 1=1 and xb_id = {11}  and xzqh.qxm like ''{18}'' {4} {22} "
					+ "union all select count(xjxx.id) as zs,''czqdrs'' as lx from tb_xjda_xjxx xjxx left join tc_xzqh xzqh on xzqh.id =xjxx.hkszd_id where 1=1 and rxqxl_id = {12} and xzqh.qxm like ''{19}'' {5} {22} "
					+ "union all select count(xjxx.id) as zs,''gzqdrs'' as lx from tb_xjda_xjxx xjxx left join tc_xzqh xzqh on xzqh.id =xjxx.hkszd_id where 1=1 and rxqxl_id = {13} and xzqh.qxm like ''{20}'' {6} {22} ";
			sql = MessageFormat.format(sql, whereSql,whereSql,whereSql,whereSql,whereSql,whereSql,whereSql,
					ncid,xzid,csid,nanid,nvid,czid,gzid,likeQxm,likeQxm,likeQxm,likeQxm,likeQxm,likeQxm,likeQxm,likeQxm,str2);
			
			List result = baseDao.querySqlList(sql);
			for(Object tempObj : result){
				Map tempMap = (Map) tempObj;
				String lx = tempMap.get("LX")==null?"":tempMap.get("LX").toString();
				String tempZs = tempMap.get("ZS")==null?"":tempMap.get("ZS").toString();
				map.put(lx, tempZs);
			}
		}
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("count", resultMap.get("count"));
		result.put("success", true);
		result.put("data", temp);
		return Struts2Utils.map2json(result);
	}
	@Override
	public void saveXsLyd2Temp() {
		// 每天执行一次。将每个学生的来源地数据存储到统计结果表中。
		// 关联tb_xjda_xjxx表与tb_xjda_xjxx_sydtj表 ，查询出没有统计学生来源的学生数据
		String sql ="select t.id,t.hkszd_id,t.qxm,t.sqxm from (select t1.id,t1.hkszd_id,t1.qxm,t2.qxm as sqxm from " +
				" (select xjxx.id,xjxx.hkszd_id,qxm.qxm from tb_xjda_xjxx xjxx left join tc_xzqh qxm on qxm.id = xjxx.hkszd_id ) t1 "+ 
				" left join (select qxm,mc from tc_xzqh where cc = 2)t2 on t1.qxm like t2.qxm||'%' and t2.qxm is not null) t " +
				" where not exists (select xs_id from tb_xjda_xjxx_sydtj syd where syd.xs_id = t.id)";
		List result = baseDao.querySqlList(sql);
		for(Object obj : result){
			Map map = (Map)obj;
			String id = map.get("ID").toString();
			String hkszdId = map.get("HKSZD_ID")==null?"":map.get("HKSZD_ID").toString();
			String qxm = map.get("QXM")==null?"":map.get("QXM").toString();
			String sqxm = map.get("SQXM")==null?"":map.get("SQXM").toString();
			if("".equals(qxm)||"".equals(hkszdId)){
				continue;
			}
			String insertSql ="INSERT INTO tb_xjda_xjxx_sydtj(XS_ID,HKSZD_ID,QXM,SQXM) VALUES({0},{1},''{2}'',''{3}'')";
			insertSql = MessageFormat.format(insertSql, id,hkszdId,qxm,sqxm);
			baseDao.update(insertSql);
		}
	}

	@Override
	public void saveByxx2Temp() {
		
	}
	
	/**
	 * 根据指标id获取相应指标表的条件sq语句。
	 * @param params
	 * @return
	 */
	public String getSqlByZbId(String zbId){
		String zbSql ="";
		if("rxzrs".equals(zbId)){
			zbSql =" and 1= 1 ";
		}
		if("nchkrs".equals(zbId)){
			zbSql =" and hklx_id = "+ncid;
		}
		if("xzhkrs".equals(zbId)){
			zbSql =" and hklx_id = "+xzid;
		}
		if("cshkrs".equals(zbId)){
			zbSql =" and hklx_id = "+csid;
		}
		if("nsrs".equals(zbId)){
			zbSql =" and xb_id = "+nanid;
		}
		if("nvsrs".equals(zbId)){
			zbSql =" and xb_id = "+nvid;
		}
		if("czqdrs".equals(zbId)){
			zbSql =" and rxqxl_id = "+czid;
		}
		if("gzqdrs".equals(zbId)){
			zbSql =" and rxqxl_id = "+gzid;
		}
		
		return zbSql;
	}
	/**
	 * 获取当前学年学期的开始日期和结束日期。
	 * @return
	 */
	public String[] getStartEndDate(){
		String[] startend = new String[2];
		// 如果月份在1-7 则学年为当前年份-1
		Calendar cal = Calendar.getInstance();
	     int month = cal.get(Calendar.MONTH) + 1;
	     int year = cal.get(Calendar.YEAR);
		// 如果月份在8-12月份 ，则学年为当前年份
//		Map xnxq = this.getCurrentXnxq();
		startend[0] =month<10?year-1+"-06-01":year+"-06-01";
		startend[1] =month<10?year+"-08-01":(year+1)+"-08-01";
		return startend;
	}
	
	public static void main(String[] args){
		String str =" select * from {0}{2}{0}{1}";
		System.out.println(MessageFormat.format(str,"2", "1","3"));
	}
}
