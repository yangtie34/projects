package com.jhkj.mosdc.sc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.Utils4Service;
import com.jhkj.mosdc.sc.service.YsPksService;

public class YsPksServiceImpl  extends ScServiceImpl implements YsPksService {

	//经常吃饭的学生的阈值
	public  static Integer yz = 0;
	@Override
	public void saveXfxx() {
		// 清空TB_YKT_TEMP_DXF_NAN 和TB_YKT_TEMP_DXF_NV 两张表
		System.out.println("=========");
		String drop1  ="DROP TABLE TB_YKT_TEMP_DXF_NAN";
		String drop2  ="DROP TABLE TB_YKT_TEMP_DXF_NV";
		String drop3  ="DROP TABLE TB_YKT_TEMP_DXF_RESULT";
		baseDao.update(drop1);
		baseDao.update(drop2);
		baseDao.update(drop3);
		// 再重新创造这两张表
		String create1 = "create table TB_YKT_TEMP_DXF_NV AS select t1.*,t2.pj2,case when T1.zxfje>=pj2 then 1 else 0 end sfdb from ( "
				+ " SELECT RYID,CBDM,SUBSTR(XFSJ,1,10) AS XFRQ,SUM(XFJE) ZXFJE,COUNT(XFJE) SKCS FROM (select xfmx.*,xb.mc xb from tb_ykt_xfmx xfmx "
				+ " left join tb_xjda_xjxx xjxx on xjxx.xh = xfmx.ryid "
				+ " left join dm_zxbz.t_zxbz_xb xb on xb.wid = xjxx.xb_id  "
				+ " where xfmx.jykm=210  and xb.mc ='女' and XFSJ >TO_CHAR((SYSDATE-90),'yyyy-MM-dd') ) GROUP BY SUBSTR(XFSJ,1,10),CBDM,RYID "
				+ " ) t1 left join ( "
				+ " SELECT CBDM,SUBSTR(XFSJ,1,10) AS XFRQ,SUM(XFJE) ZXFJE,COUNT(DISTINCT RYID) SKCS,round(sum(xfje)/count(DISTINCT RYID),2) pj2 FROM "
				+ " (select xfmx.* from tb_ykt_xfmx xfmx   "
				+ " left join tb_xjda_xjxx xjxx on xjxx.xh = xfmx.ryid "
				+ " left join dm_zxbz.t_zxbz_xb xb on xb.wid = xjxx.xb_id "
				+ " where xfmx.jykm=210  and xb.mc ='女' and XFSJ >TO_CHAR((SYSDATE-90),'yyyy-MM-dd') ) GROUP BY SUBSTR(XFSJ,1,10),CBDM ORDER BY XFRQ,CBDM "
				+ " ) t2 on t1.cbdm = t2.cbdm and t1.xfrq = t2.xfrq";
		String create2 ="create table TB_YKT_TEMP_DXF_NAN AS select t1.*,t2.pj2,case when T1.zxfje>=pj2 then 1 else 0 end sfdb from ( "
				+ " SELECT RYID,CBDM,SUBSTR(XFSJ,1,10) AS XFRQ,SUM(XFJE) ZXFJE,COUNT(XFJE) SKCS FROM (select xfmx.*,xb.mc xb from tb_ykt_xfmx xfmx "
				+ " left join tb_xjda_xjxx xjxx on xjxx.xh = xfmx.ryid "
				+ " left join dm_zxbz.t_zxbz_xb xb on xb.wid = xjxx.xb_id  "
				+ " where xfmx.jykm=210 and xb.mc ='男' and XFSJ >TO_CHAR((SYSDATE-90),'yyyy-MM-dd') ) GROUP BY SUBSTR(XFSJ,1,10),CBDM,RYID "
				+ " ) t1 left join ( "
				+ " SELECT CBDM,SUBSTR(XFSJ,1,10) AS XFRQ,SUM(XFJE) ZXFJE,COUNT(DISTINCT RYID) SKCS,round(sum(xfje)/count(DISTINCT RYID),2) pj2 FROM "
				+ " (select xfmx.* from tb_ykt_xfmx xfmx "
				+ " left join tb_xjda_xjxx xjxx on xjxx.xh = xfmx.ryid "
				+ " left join dm_zxbz.t_zxbz_xb xb on xb.wid = xjxx.xb_id "
				+ " where xfmx.jykm=210  and xb.mc ='男' and XFSJ >TO_CHAR((SYSDATE-90),'yyyy-MM-dd') ) GROUP BY SUBSTR(XFSJ,1,10),CBDM ORDER BY XFRQ,CBDM "
				+ " ) t2 on t1.cbdm = t2.cbdm and t1.xfrq = t2.xfrq";
		baseDao.update(create1);
		baseDao.update(create2);
		
		System.out.println("========end1=========");
		String yzsql ="SELECT CASE WHEN MEDIAN(CFCS)>=ROUND(AVG(CFCS),1) THEN ROUND(AVG(CFCS),1) ELSE MEDIAN(CFCS) END YZ FROM (SELECT RYID,COUNT(*) CFCS FROM (SELECT * FROM TB_YKT_TEMP_DXF_NAN UNION ALL SELECT * FROM TB_YKT_TEMP_DXF_NV) GROUP BY RYID ORDER BY CFCS)";
		List<Map<String,Object>> list = baseDao.querySqlList(yzsql);
		String yz = list.get(0).get("YZ").toString();
		String create3 ="CREATE TABLE TB_YKT_TEMP_DXF_RESULT AS "+
				" SELECT T1.RYID,BDBS,CFCS,ROUND(BDBS/CFCS*100,2) BDBL FROM ( "+
				" SELECT RYID,COUNT(*) BDBS FROM (SELECT * FROM TB_YKT_TEMP_DXF_NAN UNION ALL SELECT * FROM TB_YKT_TEMP_DXF_NV) WHERE SFDB=0 GROUP BY RYID ORDER BY BDBS DESC)T1,( "+
				" SELECT RYID,CFCS FROM (SELECT RYID,COUNT(*) CFCS FROM (SELECT * FROM TB_YKT_TEMP_DXF_NAN UNION ALL SELECT * FROM TB_YKT_TEMP_DXF_NV) GROUP BY RYID ORDER BY CFCS) "+
				" WHERE CFCS >="+yz+")T2 WHERE T1.RYID = T2.RYID ORDER BY BDBL DESC";
		baseDao.update(create3);
		System.out.println("========end2=========");
	}

	@Override
	public String queryGridContent(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String zzjgid = json.getString("zzjgId");
		Map node = getZzjgNode(zzjgid);
		String str2 = " AND XJXX.ZY_ID IN (SELECT ID FROM TB_JXZZJG ZZJG WHERE ZZJG.QXM LIKE '"+node.get("qxm")+"%')";
		String xsSql = "SELECT t1.* from (SELECT T1.RYID XH,XJXX.XM,ZZJG.MC YX,"
				+ " ZZJG1.MC ZY,XB.MC XB,XJXX.RXNJ_ID AS RXNJ,T1.BDBS,T1.CFCS,T1.BDBL FROM "
				+ " TB_YKT_TEMP_DXF_RESULT T1 LEFT JOIN TB_XJDA_XJXX XJXX ON XJXX.XH = T1.RYID"
				+ " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = XJXX.YX_ID"
				+ " LEFT JOIN TB_JXZZJG ZZJG1 ON ZZJG1.ID = XJXX.ZY_ID"
				+ " LEFT JOIN DM_ZXBZ.T_ZXBZ_XB XB ON XB.WID = XJXX.XB_ID WHERE 1=1 AND BDBL >70.00 "
				+ str2 + " ORDER BY BDBL DESC) t1 ";
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(xsSql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	@Override
	public String queryGridContent4xq(String params) {
		JSONObject json = JSONObject.fromObject(params);
		String xh = json.getString("XH");
		String xsSql = " SELECT T1.RYID XH,XJXX.XM,T1.XFRQ,T1.CBDM,T1.ZXFJE,T1.PJ2,T1.SFDB FROM  "
				+ " (SELECT * FROM TB_YKT_TEMP_DXF_NAN UNION ALL SELECT * FROM TB_YKT_TEMP_DXF_NV) T1 "
				+ " LEFT JOIN TB_XJDA_XJXX XJXX ON XJXX.XH = T1.RYID "
				+ " LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = XJXX.YX_ID "
				+ " LEFT JOIN TB_JXZZJG ZZJG1 ON ZZJG1.ID = XJXX.ZY_ID "
				+ " LEFT JOIN DM_ZXBZ.T_ZXBZ_XB XB ON XB.WID = XJXX.XB_ID WHERE 1=1 AND T1.RYID = "
				+ xh + "  ORDER BY XFRQ DESC,CBDM";
		Map paramsMap = Utils4Service.packageParams(params);
		Map result = baseDao.queryTableContentBySQL(xsSql,paramsMap);
		Map backval = new HashMap();
		backval.put("success", true);
		backval.put("data", result.get("queryList"));
		backval.put("count", result.get("count"));
		return Struts2Utils.map2json(backval);
	}
	private void initYz(){
		String sql ="SELECT CASE WHEN MEDIAN(CFCS)>=ROUND(AVG(CFCS),0) THEN ROUND(AVG(CFCS),1) ELSE MEDIAN(CFCS) END YZ FROM (SELECT RYID,COUNT(*) CFCS FROM (SELECT * FROM TB_YKT_TEMP_DXF_NAN UNION ALL SELECT * FROM TB_YKT_TEMP_DXF_NV) GROUP BY RYID ORDER BY CFCS)";
		yz = baseDao.getJdbcTemplate().queryForInt(sql);
	}
	
	Map getZzjgNode(String zzjgid){
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
	public String queryChartData(String params) {
		String sql ="SELECT CBDM,XB.MC,ROUND(SUM(ZXFJE)/COUNT(ZXFJE),2) PJZ FROM "+
					" (SELECT * FROM TB_YKT_TEMP_DXF_NAN UNION ALL SELECT * FROM TB_YKT_TEMP_DXF_NV) T1 "+
					" LEFT JOIN TB_XJDA_XJXX XJXX ON XJXX.XH = T1.RYID "+
					" LEFT JOIN DM_ZXBZ.T_ZXBZ_XB XB ON XB.WID = XJXX.XB_ID "+
					" GROUP BY CBDM,XB.MC ORDER BY MC,CBDM ";
		
		String cjsql ="SELECT XB.MC,ROUND(SUM(ZXFJE)/COUNT(ZXFJE),2) PJZ FROM  "+
				" (SELECT * FROM TB_YKT_TEMP_DXF_NAN UNION ALL SELECT * FROM TB_YKT_TEMP_DXF_NV) T1  "+
				" LEFT JOIN TB_XJDA_XJXX XJXX ON XJXX.XH = T1.RYID "+
				" LEFT JOIN DM_ZXBZ.T_ZXBZ_XB XB ON XB.WID = XJXX.XB_ID "+
				" GROUP BY XB.MC";
		List<Map> results = baseDao.querySqlList(sql);
		List<Map> results1 = baseDao.querySqlList(cjsql);

		Map backval = new HashMap();
		for(Map map : results){
			String xb = map.get("MC").toString();
			String je = map.get("PJZ").toString();
			int cb = Integer.parseInt(map.get("CBDM").toString());
			if("男".equals(xb)){
				switch(cb){
				case 1:
					backval.put("m_zaocje", je);
					break;
				case 2:
					backval.put("m_wucje", je);
					break;
				case 3:
					backval.put("m_wancje", je);
					break;
				}
			}else{
				switch(cb){
				case 1:
					backval.put("w_zaocje", je);
					break;
				case 2:
					backval.put("w_wucje", je);
					break;
				case 3:
					backval.put("w_wancje", je);
					break;
				}
			}
		}
		for(Map map : results1){
			String xb = map.get("MC").toString();
			String je = map.get("PJZ").toString();
			if("男".equals(xb)){
				backval.put("m_je", je);
			}else{
				backval.put("w_je", je);
			}
		}
		
		return Struts2Utils.map2json(backval);
	}
}
