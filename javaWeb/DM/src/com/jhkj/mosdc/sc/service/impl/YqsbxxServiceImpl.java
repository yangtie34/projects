package com.jhkj.mosdc.sc.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.collections.MapUtils;

import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.service.YqsbxxService;
import com.jhkj.mosdc.sc.util.BzksUtils;

@SuppressWarnings("rawtypes")
public class YqsbxxServiceImpl implements YqsbxxService {
	private BaseService baseService;
	private BaseDao baseDao;
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}	

	@Override
	public String queryYqgk(String params) {
		String sql = "select count(1) total,NVL(sum(t.dj),0) worth from t_yqsb t";
		List<Map> result = baseService.queryListMapInLowerKeyBySql(sql);
		return Struts2Utils.list2json(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryYqly(String json) {
		String sql = "SELECT YQLY.MC LYFS,COUNT(1) NUMS FROM T_YQSB T " +
				"LEFT JOIN DM_ZXBZ.TB_ZXBZ_YQSBLY YQLY ON YQLY.DM = ZCLYFS GROUP BY YQLY.MC order by nums desc";
		List<Map> res1 = baseService.queryListMapInLowerKeyBySql(sql);
		List<Map> res2 = new ArrayList<Map>();
		int cg = 0,total = 0;
		for (int i = 0; i < res1.size(); i++) {
			total += MapUtils.getIntValue(res1.get(i), "nums");
		}
		total = (total == 0 ? 1 : total);
		for(int i = 0; i < res1.size(); i++){
			Map m1 = new HashMap();
			cg = MapUtils.getIntValue(res1.get(i), "nums");
			m1.put("name", MapUtils.getString(res1.get(i), "lyfs"));
			m1.put("total", cg);
			m1.put("persent", String.format("%.2f", 100*(double)cg/total));
			
			res2.add(m1);
		}
		return Struts2Utils.list2json(res2);
	}

	@Override
	public String queryYqlyByYx(String json) {
		String sql = "SELECT ZZJG.FJDID,ZZJG.FJD AS FIELD,NVL(SBLY.MC,'其他') AS NAME,COUNT(YQ.WID) VALUE "+
				" FROM (SELECT T1.*,CASE WHEN T1.CC = 1 THEN T1.MC ELSE T2.MC END AS FJD," +
				" CASE WHEN T1.CC = 1 THEN T1.ID ELSE T2.ID END AS FJDID FROM TB_XZZZJG T1 " +
				" LEFT JOIN TB_XZZZJG T2 ON T1.FJD_ID = T2.ID) ZZJG LEFT JOIN T_YQSB YQ ON ZZJG.ID = YQ.LYDWDM"+  
				" LEFT JOIN DM_ZXBZ.TB_ZXBZ_YQSBLY SBLY ON SBLY.DM = YQ.ZCLYFS WHERE ZZJG.LBM=03 AND ZZJG.FJD NOT IN('思想政治理论教研部','公共艺术与职业技能教学部','软件学院','继续教育学院') "+
				" GROUP BY ZZJG.FJDID,ZZJG.FJD,SBLY.MC ORDER BY ZZJG.FJDID";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryYqyt(String json) {
		String sql = "SELECT SYFX.MC YQYT,COUNT(1) NUMS FROM T_YQSB T " +
				"LEFT JOIN DM_ZXBZ.TB_ZXBZ_SYFX SYFX ON SYFX.DM = T.SYFXDM GROUP BY SYFX.MC ORDER BY NUMS DESC";
		List<Map> res1 = baseService.queryListMapInLowerKeyBySql(sql);
		List<Map> res2 = new ArrayList<Map>();
		int cg = 0,total = 0;
		for (int i = 0; i < res1.size(); i++) {
			total += MapUtils.getIntValue(res1.get(i), "nums");
		}
		for(int i = 0; i < res1.size(); i++){
			Map m1 = new HashMap();
			cg = MapUtils.getIntValue(res1.get(i), "nums");
			m1.put("name", MapUtils.getString(res1.get(i), "yqyt"));
			m1.put("total", cg);
			m1.put("persent", String.format("%.2f", 100*(double)cg/total));
			
			res2.add(m1);
		}
		return Struts2Utils.list2json(res2);
	}

	@Override
	public String queryYqytByYx(String json) {
		
		String sql = "SELECT ZZJG.FJDID,ZZJG.FJD AS FIELD,NVL(SBLY.MC,'其他') AS NAME,COUNT(YQ.WID) VALUE "+
				" FROM (SELECT T1.*,CASE WHEN T1.CC = 1 THEN T1.MC ELSE T2.MC END AS FJD," +
				" CASE WHEN T1.CC = 1 THEN T1.ID ELSE T2.ID END AS FJDID FROM TB_XZZZJG T1 " +
				" LEFT JOIN TB_XZZZJG T2 ON T1.FJD_ID = T2.ID) ZZJG LEFT JOIN T_YQSB YQ ON ZZJG.ID = YQ.LYDWDM"+  
				" LEFT JOIN DM_ZXBZ.TB_ZXBZ_SYFX SBLY ON SBLY.DM = YQ.SYFXDM WHERE ZZJG.LBM=03 AND ZZJG.FJD NOT IN('思想政治理论教研部','公共艺术与职业技能教学部','软件学院','继续教育学院') "+
				" GROUP BY ZZJG.FJDID,ZZJG.FJD,SBLY.MC ORDER BY ZZJG.FJDID";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String queryYqzt(String json) {
		String sql = "SELECT SYFX.MC YQYT,COUNT(1) NUMS FROM T_YQSB T" +
				" LEFT JOIN DM_ZXBZ.Tb_Zxbz_Yqxz SYFX ON SYFX.DM = T.Xz GROUP BY SYFX.MC ORDER BY NUMS DESC";
		List<Map> res1 = baseService.queryListMapInLowerKeyBySql(sql);
		List<Map> res2 = new ArrayList<Map>();
		int cg = 0,total = 0;
		for (int i = 0; i < res1.size(); i++) {
			total += MapUtils.getIntValue(res1.get(i), "nums");
		}
		for(int i = 0; i < res1.size(); i++){
			Map m1 = new HashMap();
			cg = MapUtils.getIntValue(res1.get(i), "nums");
			m1.put("name", MapUtils.getString(res1.get(i), "yqyt"));
			m1.put("total", cg);
			m1.put("persent", String.format("%.2f", 100*(double)cg/total));
			
			res2.add(m1);
		}
		return Struts2Utils.list2json(res2);
	}

	@Override
	public String queryYqztByYx(String json) {
		String sql = "SELECT ZZJG.FJDID,ZZJG.FJD AS FIELD,NVL(SBLY.MC,'其他') AS NAME,COUNT(YQ.WID) VALUE "+
				" FROM (SELECT T1.*,CASE WHEN T1.CC = 1 THEN T1.MC ELSE T2.MC END AS FJD," +
				" CASE WHEN T1.CC = 1 THEN T1.ID ELSE T2.ID END AS FJDID FROM TB_XZZZJG T1 " +
				" LEFT JOIN TB_XZZZJG T2 ON T1.FJD_ID = T2.ID) ZZJG LEFT JOIN T_YQSB YQ ON ZZJG.ID = YQ.LYDWDM"+  
				" LEFT JOIN DM_ZXBZ.Tb_Zxbz_Yqxz SBLY ON SBLY.DM = YQ.Xz WHERE ZZJG.LBM=03 AND ZZJG.FJD NOT IN('思想政治理论教研部','公共艺术与职业技能教学部','软件学院','继续教育学院') "+
				" GROUP BY ZZJG.FJDID,ZZJG.FJD,SBLY.MC ORDER BY ZZJG.FJDID";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String queryYzzkByYx(String json) {
		int total = baseDao.querySqlCount("SELECT SUM(DJ) FROM T_YQSB");
		String innerSql ="SELECT YQ.*,ZZJG.QXM FROM T_YQSB YQ LEFT JOIN TB_XZZZJG ZZJG ON ZZJG.ID = YQ.LYDWDM";
		String yxsql ="SELECT mc yx,id zzjgid,t.* FROM TB_XZZZJG t WHERE LBM='03' AND CC=1 AND MC NOT IN ('思想政治理论教研部','公共艺术与职业技能教学部','软件学院','继续教育学院') ORDER BY DM";
		List<Map> results = baseService.queryListMapInLowerKeyBySql(yxsql);
		for (Map temp : results) {
			String qxm = MapUtils.getString(temp, "qxm");
			String yxid = MapUtils.getString(temp, "id");
			String sql1 = "SELECT COUNT(YQ.YQBH) sbzl,SUM(YQ.DJ) zjzje FROM ("
					+ innerSql + ") YQ WHERE YQ.QXM LIKE '" + qxm + "%'";
			String sql2 = "select count(id) xsnums from TB_XJDA_XJXX xjxx where 1=1 AND xjxx.XJZT_ID = 1000000000000101 AND xjxx.XSZT_ID = 1 and xjxx.yx_id="
					+ yxid + BzksUtils.getAndWhereSql();
			String sql3 = "SELECT COUNT(YQ.YQBH) SBZL,SUM(YQ.DJ) SBZJE,DJDJ FROM ( SELECT YQ.*,ZZJG.QXM, CASE  WHEN YQ.DJ <10000 THEN 0 "
					+ " WHEN YQ.DJ BETWEEN 10000 AND 50000 THEN 1 WHEN YQ.DJ BETWEEN 50000 AND 100000 THEN 2  WHEN YQ.DJ BETWEEN 100000 AND 400000 THEN 3 "
					+ " WHEN YQ.DJ >=400000 THEN 4 END AS DJDJ  FROM T_YQSB YQ LEFT JOIN TB_XZZZJG ZZJG ON ZZJG.ID = YQ.LYDWDM)YQ WHERE YQ.QXM LIKE '"
					+ qxm + "%' GROUP BY DJDJ";
			List<Map> list1 = baseDao.queryListMapInLowerKeyBySql(sql1);
			List<Map> list2 = baseDao.queryListMapInLowerKeyBySql(sql2);
			List<Map> list3 = baseDao.queryListMapInLowerKeyBySql(sql3);
			
			temp.putAll(list1.get(0));
			temp.putAll(list2.get(0));
			for(Map atemp : list3){
				int djdj = MapUtils.getIntValue(atemp, "djdj");
				switch(djdj){
				case 0:
					temp.put("dj0_num",MapUtils.getIntValue(atemp, "sbzl"));
					temp.put("dj0_sum",String.format("%.2f", MapUtils.getIntValue(atemp, "sbzje")/10000.00));
					temp.put("dj0_zb",String.format("%.2f", 100*MapUtils.getIntValue(atemp, "sbzje")/(double)total)+"%");
					break;
				case 1:
					temp.put("dj1_num",MapUtils.getIntValue(atemp, "sbzl"));
					temp.put("dj1_sum",String.format("%.2f", MapUtils.getIntValue(atemp, "sbzje")/10000.00));
					temp.put("dj1_zb",String.format("%.2f", 100*MapUtils.getIntValue(atemp, "sbzje")/(double)total)+"%");
					break;
				case 2:
					temp.put("dj2_num",MapUtils.getIntValue(atemp, "sbzl"));
					temp.put("dj2_sum",String.format("%.2f", MapUtils.getIntValue(atemp, "sbzje")/10000.00));
					temp.put("dj2_zb",String.format("%.2f", 100*MapUtils.getIntValue(atemp, "sbzje")/(double)total)+"%");
					break;
				case 3:
					temp.put("dj3_num",MapUtils.getIntValue(atemp, "sbzl"));
					temp.put("dj3_sum",String.format("%.2f", MapUtils.getIntValue(atemp, "sbzje")/10000.00));
					temp.put("dj3_zb",String.format("%.2f", 100*MapUtils.getIntValue(atemp, "sbzje")/(double)total)+"%");
					break;	
				case 4:
					temp.put("dj4_num",MapUtils.getIntValue(atemp, "sbzl"));
					temp.put("dj4_sum",String.format("%.2f", MapUtils.getIntValue(atemp, "sbzje")/10000.00));
					temp.put("dj4_zb",String.format("%.2f", 100*MapUtils.getIntValue(atemp, "sbzje")/(double)total)+"%");
					break;	
				}
			}
		}
		return Struts2Utils.list2json(results);
	}

	@Override
	public String querySbjzqj(String json) {
		String sql = "select '一万以下' name,count(1) y  from t_yqsb t where t.dj < 10000 union " +
				" select '1万~5万' name,count(1) y  from t_yqsb t where t.dj between 10000 and 50000 union " +
				" select '5万~10万' name,count(1) y  from t_yqsb t where t.dj between 50000 and 100000 " +
				" union select '10万~40万' name,count(1) y  from t_yqsb t where t.dj between 100000 and 400000" +
				" union select '40万以上' name,count(1) y  from t_yqsb t where t.dj >=400000";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}

	@Override
	public String querySbflxx(String json) {
		String sql = "select bz.mc name,sum(t.dj) y from t_yqsb t left join dm_zxbz.tb_zxbz_yqgbdl bz on t.gdzcfldm = bz.dm group by bz.mc";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	@Override
	public String querySbdwlb(String params) {
		String sql = "select dwlb.mc name,sum(t.dj) y from t_yqsb t left join tb_xzzzjg zzjg on t.lydwdm = zzjg.id" +
				" left join dm_zxbz.t_zxbz_dwlb dwlb on dwlb.dm = zzjg.lbm where dwlb.mc is not null group by dwlb.mc";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	@Override
	public String querySbjfzc(String params) {
		String sql = "select bz.mc name,sum(t.dj) y from t_yqsb t left join dm_zxbz.tb_zxbz_jfkm bz on t.jfkmdm = bz.dm group by bz.mc";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	/** 
	* @Title: queryZcxxList 
	* @Description: TODO 查询资产信息列表
	* @return String
	*/
	@Override
	public String queryZcxxList(String params){
		String sql = "select * from tb_zc_temp order by type";
		return Struts2Utils.list2json(baseService.queryListMapInLowerKeyBySql(sql));
	}
	
	/** 
	* @Title: updateZcxx 
	* @Description: TODO 修改资产信息列表
	* @return String
	*/
	@SuppressWarnings("unchecked")
	@Override
	public String updateZcxx(String params){
		JSONObject json = JSONObject.fromObject(params);
		String value = json.getString("value");
		String type = json.getString("type");
		String code = json.getString("code");
		String sql = "update tb_zc_temp t set t.value = '"+ value +"' where t.code = '"+code+"' and t.type = '" + type+"'";
		Map result = new HashMap();
		boolean b = true;
		try {
			baseDao.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			b = false;
		}
		result.put("success", b);
		return Struts2Utils.map2json(result);
	}
	

	/** 
	* @Title: queryZcztgk 
	* @Description: TODO 查询资产总体概况
	* @return String
	*/
	@SuppressWarnings("unchecked")
	@Override
	public String queryZcztgk(String params){
		String sql = "select * from tb_zc_temp t where t.page = 'zcztgk'";
		List list = baseDao.queryListMapInLowerKeyBySql(sql);
		Map result = new HashMap();
		for (int i = 0; i < list.size(); i++) {
			Map item = (Map) list.get(i);
			result.put(item.get("code"), item.get("value"));
		}
		return Struts2Utils.map2json(result);
	}
}
