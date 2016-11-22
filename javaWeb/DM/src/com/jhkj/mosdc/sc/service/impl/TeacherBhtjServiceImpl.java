package com.jhkj.mosdc.sc.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.domain.ScConstant;
import com.jhkj.mosdc.sc.service.TeacherBhtjService;

public class TeacherBhtjServiceImpl extends TeacherServiceImpl implements
		TeacherBhtjService {
	private static Map<Integer,String> TJLX = new HashMap<Integer,String>();
	private int tjzb = 2;
	static{
		TJLX.put(1,"按总人数统计");
		TJLX.put(2,"按入职人数统计");
		TJLX.put(3,"按来源类型统计入职人数");
		TJLX.put(4,"按编制类别统计入职人数");
		TJLX.put(5,"按职称类别统计入职人数");
	}
	
	@Override
	public String queryGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgId = json.get("zzjgId").toString();
		Map paramsMap = Utils4Service.packageParams(params);
		String excuteSql ="SELECT T.* FROM (SELECT JZG.ID,ZGH,XM,RXRQ,LXRQ,ZZJG.MC AS YX,LY.MC AS LYLX,BZLB.MC AS BZLB,ZC.MC AS ZC "+
										" FROM TB_JZGXX JZG LEFT JOIN TB_XZZZJG ZZJG ON ZZJG.ID = JZG.YX_ID "+
										" LEFT JOIN DM_ZXBZ.T_ZXBZ_JZGLY LY ON LY.DM = JZG.JZGLY_ID "+
										" LEFT JOIN DM_ZXBZ.T_ZXBZ_BZLB BZLB ON BZLB.DM = JZG.BZLB_ID "+
										" LEFT JOIN DM_ZXBZ.T_ZXBZ_ZYJSZW ZC ON ZC.DM = JZG.ZYJSZW_ID WHERE 1=1 {0}  ORDER BY YX,RXRQ) T";
		
		excuteSql = MessageFormat.format(excuteSql, getQxmSql(zzjgId));
		Map result = baseDao.queryTableContentBySQL(excuteSql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	@Override
	public String getTjlx(String params) {
		List<Map> backval = new ArrayList<Map>();
		Iterator it = TJLX.keySet().iterator();
		while(it.hasNext()){
			int key = Integer.parseInt(it.next().toString());
			Map temp = new HashMap();
			temp.put("id", key);
			temp.put("mc", TJLX.get(key));
			backval.add(temp);
		}
		return Struts2Utils.list2json(backval);
	}
	@Override
	public String getChartData(String params) {
		JSONObject json = JSONObject.fromObject(params);
		tjzb = json.containsKey("tjzb")?Integer.parseInt(json.getString("tjzb").toString()):2;
		String from = json.getString("from"),to = json.getString("to");
		boolean isEqu= from.equals(to)?true:false;
		String backval ="{success:false}";
		switch(tjzb){
			case 1:
				backval = isEqu?this.getCountByNf(params):this.getCountByNfd(params);
				break;
			case 2:
				backval = isEqu?this.getRzrsByNf(params):this.getRzrsByNfd(params);
				break;
			case 3:
				backval = isEqu?this.getLylxrsByNf(params):this.getLylxrsByNfd(params);
				break;
			case 4:
				backval = isEqu?this.getBzlbrsByNf(params):this.getBzlbrsByNfd(params);
				break;
			case 5:
				backval = isEqu?this.getZclbrsByNf(params):this.getZclbrsByNfd(params);
				break;
			default :
				break;
		}
		return backval;
	}
	@Override
	public String getRzrsByNf(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String nf = json.get("from").toString();
		String zzjgId = json.get("zzjgId").toString();
		String sql = "select substr(rxrq,6,2) as DM,count(*) as MC from tb_jzgxx where substr(rxrq,1,4)="+nf+" {0} group by substr(rxrq,6,2) order by dm";
		sql = MessageFormat.format(sql,getQxmSql(zzjgId));
		List<Map> result = baseDao.querySqlList(sql);
		List<Map> backval = translate(ScConstant.months,result);
		filterSet(TJLX.get(tjzb),backval);
		return Struts2Utils.list2json(backval);
	}
	@Override
	public String getRzrsByNfd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.get("zzjgId").toString();
		String sql = "select substr(rxrq,1,4) as DM,count(*) as MC from tb_jzgxx where substr(rxrq,1,4) between "+from+" and "+to+" {0} group by substr(rxrq,1,4) order by dm";
		sql = MessageFormat.format(sql,getQxmSql(zzjgId));
		List<Map> result = baseDao.querySqlList(sql);
		int[] arrs = getYears(Integer.parseInt(from),Integer.parseInt(to));
		List<Map> backval = translate(arrs,result);
		filterSet(TJLX.get(tjzb),backval);
		return Struts2Utils.list2json(backval);
	}
	
	@Override
	public String getCountByNf(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String nf = json.get("from").toString();
		String zzjgId = json.getString("zzjgId").toString();
		List<Map> backval = new ArrayList<Map>();
		String qxmsql = getQxmSql(zzjgId);
		try{
			for(int i =0,len=ScConstant.months.length;i<len;i++){
				int month = ScConstant.months[i];
				String sqlCount = "select count(*) as MC from tb_jzgxx where substr(rxrq,1,4)='"+nf+"' and substr(rxrq,6,2)="+month+" "+qxmsql;
				String sqlLx = "select count(*) as MC from tb_jzgxx where substr(lxrq,1,4)='"+nf+"' and substr(rxrq,6,2)="+month+" "+qxmsql;
				int count1 = baseDao.querySqlCount(sqlCount);
				int count2 = baseDao.querySqlCount(sqlLx);
				Map temp = new HashMap();
				temp.put("DM", month);
				int value = count1-count2<=0?0:count1-count2;
				
				temp.put("MC",value);
				backval.add(temp);
			}
			filterSet(TJLX.get(tjzb),backval);
			return Struts2Utils.list2json(backval);
		}catch(Exception e){
			return this.getEmptyChartData(ScConstant.months);
		}
	}
	
	@Override
	public String getCountByNfd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		String zzjgId = json.getString("zzjgId").toString();
		List<Map> backval = new ArrayList<Map>();
		String qxmsql = getQxmSql(zzjgId);
		int[] years = getYears(Integer.parseInt(from), Integer.parseInt(to));
		try{
			for(int i =0,len=years.length;i<len;i++){
				int year = years[i];
				String sqlCount = "select count(*) as MC from tb_jzgxx where substr(rxrq,1,4)="+year+qxmsql;
				String sqlLx = "select count(*) as MC from tb_jzgxx where substr(lxrq,1,4)="+year+qxmsql;
				int count1 = baseDao.querySqlCount(sqlCount);
				int count2 = baseDao.querySqlCount(sqlLx);
				Map temp = new HashMap();
				temp.put("DM", year);
				int value = count1-count2<=0?0:count1-count2;
				
				temp.put("MC",value);
				backval.add(temp);
			}
			filterSet(TJLX.get(tjzb),backval);
			return Struts2Utils.list2json(backval);
		}catch(Exception e){
			return this.getEmptyChartData(years);
		}
	}
	@Override
	public String getLylxrsByNf(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		try{
			String sql1 = "SELECT * FROM DM_ZXBZ.T_ZXBZ_JZGLY WHERE CC=1";
			String sql2 = "select substr(RXRQ,6,2) as DM,count(*) as MC,LY.MC AS lx from tb_jzgxx JZG "+
								  " LEFT JOIN DM_ZXBZ.T_ZXBZ_JZGLY LY ON LY.DM = JZG.JZGLY_ID where substr(RXRQ,1,4)= " +from+
								  " group by substr(JZG.RXRQ,6,2),LY.MC order by dm,lx";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
			
//			if(isDataQs(result)) return "{success:false,msg:'教职工的来源类型数据缺失严重，请联系人事部门维护人员的来源属性！'}";
			return Struts2Utils.list2json(getFhChartData(ScConstant.months,bzdm, result));
		}catch(Exception e){
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	/**
	 * 判断数据是否缺失严重
	 * @param result
	 * @return
	 */
	private boolean isDataQs(List<Map> result){
		boolean flag = true;
		for(Map temp : result){
			 if(temp.get("LX")!=null) flag = false;
		}
		return flag;
	}
	@Override
	public String getLylxrsByNfd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		try{
			String sql1 = "SELECT * FROM DM_ZXBZ.T_ZXBZ_JZGLY WHERE CC=1";
			String sql2 = "select substr(RXRQ,1,4) as DM,count(*) as MC,LY.MC AS lx from tb_jzgxx JZG "+
								  " LEFT JOIN DM_ZXBZ.T_ZXBZ_JZGLY LY ON LY.DM = JZG.JZGLY_ID where substr(RXRQ,1,4) between " +from+
								  " and "+to+" group by substr(JZG.RXRQ,1,4),LY.MC order by dm,lx";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
//			if(isDataQs(result)) return "{success:false,msg:'教职工的来源类型数据缺失严重，请联系人事部门维护人员的来源属性！'}";
			int[] years = getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(getFhChartData(years,bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}

	@Override
	public String getBzlbrsByNf(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		try{
			String sql1 = "SELECT * FROM DM_ZXBZ.T_ZXBZ_BZLB";
			String sql2 = "select substr(RXRQ,6,2) as DM,count(*) as MC,LY.MC AS lx from tb_jzgxx JZG "+
								  " LEFT JOIN DM_ZXBZ.T_ZXBZ_BZLB LY ON LY.DM = JZG.BZLB_ID where substr(RXRQ,1,4)= " +from+
								  " group by substr(JZG.RXRQ,6,2),LY.MC order by dm,lx";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
//			if(isDataQs(result)) return "{success:false,msg:'教职工的编制类别数据缺失严重，请联系人事部门维护人员的编制类别属性！'}";
			return Struts2Utils.list2json(getFhChartData(ScConstant.months,bzdm, result));
		}catch(Exception e){
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	@Override
	public String getBzlbrsByNfd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		try{
			String sql1 = "SELECT * FROM DM_ZXBZ.T_ZXBZ_BZLB";
			String sql2 = "select substr(RXRQ,1,4) as DM,count(*) as MC,LY.MC AS lx from tb_jzgxx JZG "+
								  " LEFT JOIN DM_ZXBZ.T_ZXBZ_BZLB LY ON LY.DM = JZG.BZLB_ID where substr(RXRQ,1,4) between " +from+
								  " and "+to+" group by substr(JZG.RXRQ,1,4),LY.MC order by dm,lx";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
//			if(isDataQs(result)) return "{success:false,msg:'教职工的编制类别数据缺失严重，请联系人事部门维护人员的编制类别属性！'}";
			int[] years = getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(getFhChartData(years,bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	@Override
	public String getZclbrsByNf(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		try{
			String sql1 = "SELECT * FROM DM_ZXBZ.T_ZXBZ_ZYJSZW WHERE CC=2 AND LS = '010'";
			String sql2 = "select substr(RXRQ,6,2) as DM,count(*) as MC,LY.MC AS lx from tb_jzgxx JZG "+
								  " LEFT JOIN DM_ZXBZ.T_ZXBZ_ZYJSZW LY ON LY.DM = JZG.ZYJSZW_ID where substr(RXRQ,1,4)= " +from+
								  " group by substr(JZG.RXRQ,6,2),LY.MC order by dm,lx";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
//			if(isDataQs(result)) return "{success:false,msg:'教职工的职称数据缺失严重，请联系人事部门维护人员的职称属性！'}";
			return Struts2Utils.list2json(getFhChartData(ScConstant.months,bzdm, result));
		}catch(Exception e){
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	@Override
	public String getZclbrsByNfd(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String from = json.get("from").toString();
		String to = json.get("to").toString();
		try{
			String sql1 = "SELECT * FROM DM_ZXBZ.T_ZXBZ_ZYJSZW WHERE CC=2 AND LS = '010'";
			String sql2 = "select substr(RXRQ,1,4) as DM,count(*) as MC,LY.MC AS lx from tb_jzgxx JZG "+
								  " LEFT JOIN DM_ZXBZ.T_ZXBZ_ZYJSZW LY ON LY.DM = JZG.ZYJSZW_ID where substr(RXRQ,1,4) between " +from+
								  " and "+to+" group by substr(JZG.RXRQ,1,4),LY.MC order by dm,lx";
			List<Map> bzdm = baseDao.querySqlList(sql1);
			List<Map> result = baseDao.querySqlList(sql2);
//			if(isDataQs(result)) return "{success:false,msg:'教职工的职称数据缺失严重，请联系人事部门维护人员的职称属性！'}";
			int[] years = getYears(Integer.parseInt(from), Integer.parseInt(to));
			return Struts2Utils.list2json(getFhChartData(years,bzdm, result));
		}catch(Exception e){
			e.printStackTrace();
			return "{success:false,msg:'"+ScConstant.errorMsg+"'}";
		}
	}
	/**
	 * 获取全系码条件sql
	 * @param zzjgId
	 * @return
	 */
	String getQxmSql(String zzjgId){
		Map node = getXzzzjgNode(zzjgId);
		
		String str2 = " AND YX_ID IN (SELECT id FROM TB_XZZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		return str2;
	}
	/**
	 * 获得空图形数据
	 * @param arrs
	 * @return
	 */
	String getEmptyChartData(int[] arrs ){
		List<Map> result = new ArrayList<Map>();
		List<Map> backval = translate(arrs,result);
		filterSet(TJLX.get(tjzb),backval);
		return Struts2Utils.list2json(backval);
	}
	/**
	 * 补全月份
	 * @param result
	 * @return
	 */
	List<Map> translate(int[] months,List<Map> result){
		List<Map> backval = new ArrayList<Map>();
		for(int i=0 ;i<months.length;i++){
			Map temp = new HashMap();
			temp.put("DM", months[i]);
			boolean flag = false;
			for(Map map : result){
				String yf = map.get("DM").toString();
				if(Integer.parseInt(yf)==months[i]){
					temp.put("MC", map.get("MC"));
					flag = true;
					break;
				}
				if(!flag){
					temp.put("MC", 0);
				}
			}
			
			backval.add(temp);
		}
		return backval;
	}
	/**
	 * 获得年份区间的年份列表
	 * @param from
	 * @param to
	 * @return
	 */
	int[] getYears(int from,int to){
		int a= to-from>0?to-from:0;
		int[] backval = new int[a+1];
		for(int i = 0;i<backval.length;i++){
			backval[i] = from+i;
		}
		return backval;
	}
	/**
	 * 设置属性
	 * @param result
	 */
	void filterSet(String name,List<Map> result){
		for(Map map :result){
			int dm = map.containsKey("DM")?Integer.parseInt(map.get("DM").toString()):0;
			map.put("name", name);
			map.put("field", dm);
			map.put("value", map.get("MC"));
		}
	}
	
	List<Map> getFhChartData(int[] arrs,List<Map>bzdm,List<Map>result){
		/*首先，将结果集转化成map形式的数据*/
		Map<String,List<Map>> tempMap = new HashMap<String,List<Map>>();
		for(Map tjMap : result){
			String lylx = tjMap.get("LX")==null?ScConstant.wwh:tjMap.get("LX").toString();
			if(tempMap.containsKey(lylx)){
				tempMap.get(lylx).add(tjMap);
			}else{
				List<Map> tempList = new ArrayList<Map>();
				tempList.add(tjMap);
				tempMap.put(lylx, tempList);
			}
		}
		/*循环查看map中每个键值对下的数据，补全数据*/
		for(Map dmMap : bzdm){
			String mc = dmMap.get("MC").toString();
			if(tempMap.containsKey(mc)){
				tempMap.put(mc, translate(arrs, tempMap.get(mc)));
			}else{
				tempMap.put(mc, translate(arrs, new ArrayList<Map>()));
			}
		}
		/*最后转化为LISTmap ，返回结果*/
		List<Map> backval = new ArrayList<Map>();
		Iterator it = tempMap.keySet().iterator();
		while(it.hasNext()){
			String key = it.next().toString();
			List<Map> alist = tempMap.get(key);
			for(Map tm : alist){
				tm.put("field", tm.get("DM"));
				tm.put("name", key);
				tm.put("value", tm.get("MC"));
			}
			backval.addAll(alist);
		}
		
		return backval;
	}
}
