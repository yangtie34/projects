package com.jhkj.mosdc.sc.job.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.MapUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.sc.job.DemoAnalysisService;
import com.jhkj.mosdc.sc.util.BzksUtils;

public class DemoAnalysisServiceImpl extends BaseServiceImpl implements
		DemoAnalysisService {
	
	@Override
	public boolean saveCjpm2Temp() {
		final List<Map> vals = new ArrayList<Map>(); 
		String sql = "SELECT XJ.RXNJ_ID AS RXNJ,CJ.XH,XN,SUM(KCCJ) AS CJZF,ZZJG.MC AS ZY FROM  ( "
				+ " SELECT XH,XN,CASE WHEN DJZKSCJ='合格' THEN 60 "
				+ " WHEN DJZKSCJ='及格' THEN 60 "
				+ " WHEN DJZKSCJ='中等' THEN 70 "
				+ "  WHEN DJZKSCJ='良好' THEN 80 "
				+ " WHEN DJZKSCJ='优秀' THEN 90 "
				+ " WHEN DJZKSCJ='不合格' THEN 40 "
				+ " WHEN DJZKSCJ='不及格' THEN 40 "
				+ " ELSE BFZKSCJ END AS KCCJ "
				+ " FROM  T_XS_KSCJ ) CJ LEFT JOIN TB_XJDA_XJXX XJ ON XJ.XH = CJ.XH " +
				" LEFT JOIN TB_JXZZJG ZZJG ON ZZJG.ID = XJ.ZY_ID where cj.xn = 2014"
				+ " GROUP BY XJ.RXNJ_ID,CJ.XH,XN,ZZJG.MC ORDER BY ZY,xn,rxnj,CJZF DESC";
		boolean flag = true;
		Map<String,String> flagMap = new HashMap<String,String>();
		int g = 1;
		try{
			List<Map> results = this.querySqlList(sql);
			System.out.println("======="+results.size());
			for(int i=0,j= results.size();i<j;i++){
				
				String xh = results.get(i).get("XH").toString();
				if(results.get(i).get("ZY")==null){
					continue;
				}
				String zymc = results.get(i).get("ZY").toString();
				String xn = results.get(i).get("XN").toString();
				String rxnj = results.get(i).get("RXNJ").toString();
				if(flagMap.containsKey(zymc+xn+rxnj)){
					g++;
				}else{
					flagMap.put(zymc+xn+rxnj, "1");
					g = 1;
				}
				
				double cjzf = Double.parseDouble(results.get(i).get("CJZF")==null?"0.00":results.get(i).get("CJZF").toString());
				
				Map temp = new HashMap();
				temp.put("xh",xh);
				temp.put("zymc",zymc);
				temp.put("xn",xn);
				temp.put("g",g);
				temp.put("cjzf",cjzf);
				temp.put("rxnj", rxnj);
				vals.add(temp);
//				String insertSql ="INSERT INTO TB_YKT_XGXFX(XH,ZYMC,XN,CJPM,CJZF) VALUES('"+xh+"','"+zymc+"','"+xn+"',"+g+","+cjzf+")";
//				baseDao.insert(insertSql);
			}
			g = 1;
			baseDao.getJdbcTemplate()
			.batchUpdate("INSERT INTO TB_YKT_XGXFX(XH,ZYMC,XN,CJPM,CJZF,RXNJ) VALUES(?,?,?,?,?,?) ", 
					new BatchPreparedStatementSetter() {
						
						@Override
						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, vals.get(i).get("xh").toString());
							ps.setString(2, vals.get(i).get("zymc").toString());
							ps.setString(3, vals.get(i).get("xn").toString());
							ps.setInt(4, Integer.parseInt(vals.get(i).get("g").toString()));
							ps.setDouble(5, Double.parseDouble(vals.get(i).get("cjzf").toString()));
							ps.setString(6, vals.get(i).get("rxnj").toString());
						}
						
						@Override
						public int getBatchSize() {
							return vals.size();
						}
					});
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
	@Override
	public boolean saveTsgMjcs2Temp() {
		final List<Map> vals = new ArrayList<Map>(); 
		String sql ="SELECT XN,XGH,COUNT(*) AS ZS FROM ( "+
				" SELECT CASE WHEN SUBSTR(TSGMJ.SKSJ,6,2) BETWEEN 6 AND 12 "+
				" THEN TO_NUMBER(SUBSTR(TSGMJ.SKSJ,0,4)) "+
				" ELSE TO_NUMBER(SUBSTR(TSGMJ.SKSJ,0,4))-1 END AS XN,KH.XGH "+
				" FROM TB_YKT_KH KH,TB_YKT_MJMX_TSG TSGMJ WHERE TSGMJ.DZZH = KH.KH) GROUP BY XN,XGH";
		List<Map> results = baseDao.querySqlList(sql);
		for(Map map : results){
			String xn = map.get("XN").toString();
			String XGH = map.get("XGH").toString();
			int zs = Integer.parseInt(map.get("ZS").toString());
			Map temp = new HashMap();
			temp.put("XN", xn);
			temp.put("XGH", XGH);
			temp.put("ZS", zs);
			
			vals.add(temp);
		}
		baseDao.getJdbcTemplate()
		.batchUpdate("UPDATE TB_YKT_XGXFX SET TSGCS = ?  WHERE XN= ?  AND XH= ? ", 
				new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, Integer.parseInt(vals.get(i).get("ZS").toString()));
						ps.setString(2, vals.get(i).get("XN").toString());
						ps.setString(3, vals.get(i).get("XGH").toString());
					}
					
					@Override
					public int getBatchSize() {
						return vals.size();
					}
				});
		return false;
	}
	
	@Override
	public boolean saveZccs2Temp() {
		String sql ="SELECT XN,XGH,COUNT(*) AS ZS FROM ( "+
				" SELECT CASE WHEN SUBSTR(XFSJ,6,2) BETWEEN 6 AND 12 "+ 
				" THEN TO_NUMBER(SUBSTR(XFSJ,0,4))  "+
				" ELSE TO_NUMBER(SUBSTR(XFSJ,0,4))-1 END AS XN,RYID AS XGH,XFSJ "+
				" FROM TB_YKT_XFMX WHERE SUBSTR(XFSJ,12,2)<8 ) WHERE XN ='2014' GROUP BY XN,XGH";
		List<Map> results = baseDao.querySqlList(sql);
		final List<Map> vals = new ArrayList<Map>();
		for(Map temp:results){
			int zs = MapUtils.getIntValue(temp, "ZS");
			String xn = MapUtils.getString(temp, "XN");
			String xh = MapUtils.getString(temp, "XGH");
			Map map = new HashMap();
			map.put("zs", zs);
			map.put("xn", xn);
			map.put("xh", xh);
			vals.add(map);
		}
		System.out.println("====查询结束===开始批量更新===="+vals.size());
		String updateSql="UPDATE TB_YKT_XGXFX SET ZCXFCS =? WHERE XN=2013 AND XH=?";
		baseDao.getJdbcTemplate().batchUpdate(updateSql,new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				ps.setInt(1, Integer.parseInt(vals.get(i).get("zs").toString()));
//				ps.setString(2, vals.get(i).get("xn").toString());
				ps.setString(2, vals.get(i).get("xh").toString());
			}
			
			@Override
			public int getBatchSize() {
				return vals.size();
			}
		});
		return false;
	}
	
	@Override
	public boolean updatePm() {
		String sql = "SELECT * FROM TB_YKT_XGXFX WHERE XN=2013 ORDER BY ZYMC,CJZF DESC";
		boolean flag = true;
		Map<String,String> flagMap = new HashMap<String,String>();
		int g = 1;
		try{
			List<Map> results = this.querySqlList(sql);
			for(int i=0,j= results.size();i<j;i++){
				String xh = results.get(i).get("XH").toString();
				if(results.get(i).get("ZYMC")==null){
					continue;
				}
				String zymc = results.get(i).get("ZYMC").toString();
				String xn = results.get(i).get("XN").toString();
				if(flagMap.containsKey(zymc+xn)){
					g++;
				}else{
					flagMap.put(zymc+xn, "1");
					g = 1;
				}
				
				String insertSql ="UPDATE TB_YKT_XGXFX SET CJPM ="+g+" WHERE XN="+xn+" AND XH='"+xh+"'";
				baseDao.update(insertSql);
			}
			g = 1;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	@Override
	public String queryChartDate(String params) {
		/*String sql ="SELECT CJPM,XN,COUNT(1) AS ZS,TSGCS FROM TB_YKT_XGXFX GROUP BY CJPM,XN,TSGCS";
		// 封装成前台需要的数据
		List<Map> results = baseDao.querySqlList(sql);*/
		
		String sqlmc = "SELECT CJPM from (SELECT CJPM,XN,COUNT(1) AS ZS,TSGCS FROM TB_YKT_XGXFX GROUP BY CJPM,XN,TSGCS) where cjpm<500 GROUP BY CJPM ";
		List<Map> pmkey = baseDao.querySqlList(sqlmc);
		for(Map map : pmkey){
			String key = map.get("CJPM").toString();
			map.put("region", "周口师范学院");
			map.put("name", key);
			// 根据排名查 其所有年费的 人数和图书馆出入记录
			String getsql = "SELECT * FROM  (SELECT CJPM,XN,COUNT(1) AS ZS,TSGCS FROM TB_YKT_XGXFX GROUP BY CJPM,XN,TSGCS) T WHERE CJPM ="+key;
			List<Map> jg = baseDao.querySqlList(getsql);
			List income1 = new ArrayList();
			List lifeExpectancy1 = new ArrayList();
			List population1 = new ArrayList();
			for(Map amap : jg){
				List income = new ArrayList();
				income.add( Integer.parseInt(amap.get("XN").toString()));
				income.add( Double.parseDouble(amap.get("CJPM").toString()));
				income1.add(income);
				
				List lifeExpectancy = new ArrayList();
				lifeExpectancy.add(Integer.parseInt(amap.get("XN").toString()));
				lifeExpectancy.add(amap.get("TSGCS")==null?0.00d:Double.parseDouble(amap.get("TSGCS").toString()));
				lifeExpectancy1.add(lifeExpectancy);
				
				List population = new ArrayList();
				population.add(Integer.parseInt(amap.get("XN").toString()));
				population.add(Double.parseDouble(amap.get("ZS").toString()));
				population1.add(population);			
				
			}
			map.put("income", income1);
			map.put("lifeExpectancy", lifeExpectancy1);
			map.put("population", population1);
			
			map.remove("CJPM");
		}
		
		return Struts2Utils.list2json(pmkey);
	}
	
	@Override
	public String queryChartDate2(String params) {
		String sql = "select cjpm,xn,sum(zs) as zs,case when round(avg(TSGCS),0) is null then 0 else round(avg(TSGCS),0) end" +
				" as tsgcs from(SELECT case when cjpm between 1 and 10 then 10 "
				+ "when cjpm between 10 and 20 then 20 "
				+ "when cjpm between 20 and 50 then 50 "
				+ "when cjpm between 50 and 100 then 100 "
				+ "when cjpm between 100 and 200 then 200 "
				+ "when cjpm between 200 and 300 then 300 "
				+ "when cjpm >300 then 400 end as cjpm,XN,COUNT(1) AS ZS, round(avg(TSGCS),0) as tsgcs  "
				+ "FROM TB_YKT_XGXFX  "
				+ " GROUP BY CJPM,XN ORDER BY XN DESC "
				+ ")  " + "group by cjpm,xn";
		// 封装成前台需要的数据
		List<Map> results = baseDao.querySqlList(sql);
		// 返回结果
		List<Map> backval = new ArrayList<Map>();
		// 学年标志
		Map keyContainer = new LinkedHashMap();
		int[] yz = new int[3];// 
		for(Map temp : results){
			int xn = Integer.parseInt(temp.get("XN").toString());
			int cjpm = Integer.parseInt(temp.get("CJPM").toString());
			int tsgcs = Integer.parseInt(temp.get("TSGCS").toString());
			int rs = Integer.parseInt(temp.get("ZS").toString());
			
			if(keyContainer.containsKey(xn)){
				List<List> templist = (List<List>)keyContainer.get(xn);
				List tempyz = new ArrayList();
				tempyz.add(tsgcs);
				tempyz.add(cjpm);
				tempyz.add(rs);
				templist.add(tempyz);
			}else{
				List<List> templist = new ArrayList<List>();
				List tempyz =new ArrayList();
				tempyz.add(tsgcs);
				tempyz.add(cjpm);
				tempyz.add(rs);
				templist.add(tempyz);
				keyContainer.put(xn, templist);
			}
		}
		System.out.println(keyContainer);
		Iterator it = keyContainer.keySet().iterator();
		while(it.hasNext()){
			int xn = Integer.parseInt(it.next().toString());
			Map mapInBackval = new HashMap();
			mapInBackval.put("name", xn);
			mapInBackval.put("data", keyContainer.get(xn));
			
			backval.add(mapInBackval);
		}
		System.out.println(Struts2Utils.list2json(backval));
		return Struts2Utils.list2json(backval);
	}
	@Override
	public String queryChartDate22(String params) {
		String sql = "select cjpm,xn,sum(zs) as zs,case when round(avg(zcxfcs),0) is null then 0 else round(avg(zcxfcs),0) end" +
				" as tsgcs from(SELECT case when cjpm between 1 and 10 then 10 "
				+ "when cjpm between 10 and 20 then 20 "
				+ "when cjpm between 20 and 50 then 50 "
				+ "when cjpm between 50 and 100 then 100 "
				+ "when cjpm between 100 and 200 then 200 "
				+ "when cjpm between 200 and 300 then 300 "
				+ "when cjpm >300 then 400 end as cjpm,XN,COUNT(1) AS ZS, round(avg(zcxfcs),0) as zcxfcs  "
				+ "FROM TB_YKT_XGXFX  "
				+ " GROUP BY CJPM,XN ORDER BY XN DESC "
				+ ")  " + "group by cjpm,xn";
		// 封装成前台需要的数据
		List<Map> results = baseDao.querySqlList(sql);
		// 返回结果
		List<Map> backval = new ArrayList<Map>();
		// 学年标志
		Map keyContainer = new LinkedHashMap();
		int[] yz = new int[3];// 
		for(Map temp : results){
			int xn = Integer.parseInt(temp.get("XN").toString());
			int cjpm = Integer.parseInt(temp.get("CJPM").toString());
			int tsgcs = Integer.parseInt(temp.get("TSGCS").toString());
			int rs = Integer.parseInt(temp.get("ZS").toString());
			
			if(keyContainer.containsKey(xn)){
				List<List> templist = (List<List>)keyContainer.get(xn);
				List tempyz = new ArrayList();
				tempyz.add(tsgcs);
				tempyz.add(cjpm);
				tempyz.add(rs);
				templist.add(tempyz);
			}else{
				List<List> templist = new ArrayList<List>();
				List tempyz =new ArrayList();
				tempyz.add(tsgcs);
				tempyz.add(cjpm);
				tempyz.add(rs);
				templist.add(tempyz);
				keyContainer.put(xn, templist);
			}
		}
		System.out.println(keyContainer);
		Iterator it = keyContainer.keySet().iterator();
		while(it.hasNext()){
			int xn = Integer.parseInt(it.next().toString());
			Map mapInBackval = new HashMap();
			mapInBackval.put("name", xn);
			mapInBackval.put("data", keyContainer.get(xn));
			
			backval.add(mapInBackval);
		}
		System.out.println(Struts2Utils.list2json(backval));
		return Struts2Utils.list2json(backval);
	}
	@Override
	public String queryChartData3(String params) {
		String sql =" SELECT T1.XH,T1.PJPM,T1.PJCS,XJXX.XM,CASE WHEN XJXX.XB_ID=1000000000008265 THEN 1 ELSE 2 End AS XB FROM ( "+
 " SELECT XH,ROUND(AVG(CJPM),0) AS PJPM,CASE WHEN ROUND(AVG(TSGCS),0) IS NULL "+
 " THEN 0 ELSE ROUND(AVG(TSGCS),0) END AS PJCS FROM TB_YKT_XGXFX GROUP BY XH) T1"+
 " INNER JOIN TB_XJDA_XJXX XJXX ON XJXX.XH = T1.XH "+BzksUtils.getAndWhereSql();
		List<Map> results = baseDao.querySqlList(sql);
		Map keyContainer = new LinkedHashMap();
		for(Map temp : results){
			int xn = Integer.parseInt(temp.get("XB").toString());
			int cjpm = Integer.parseInt(temp.get("PJPM").toString());
			int tsgcs = Integer.parseInt(temp.get("PJCS").toString());
			
			if(keyContainer.containsKey(xn)){
				List<List> templist = (List<List>)keyContainer.get(xn);
				List tempyz = new ArrayList();
				tempyz.add(tsgcs);
				tempyz.add(cjpm);
				templist.add(tempyz);
			}else{
				List<List> templist = new ArrayList<List>();
				List tempyz =new ArrayList();
				tempyz.add(tsgcs);
				tempyz.add(cjpm);
				templist.add(tempyz);
				keyContainer.put(xn, templist);
			}
		}
		System.out.println(keyContainer);
		List nanList = (List) keyContainer.get(1);
		List nvList = (List) keyContainer.get(2);
		
		List nanResultList = new ArrayList();
		
		int del_num = (int) (nanList.size() * 0.11);  
        for (int i = 0; i < del_num; i++) {  
        	// 随机抽样,概率为0.11
    		Random rand = new Random();  
            // 指定读取的行号  
            int lineNumber = (int) (rand.nextDouble() * nanList.size()); 
            nanResultList.add(nanList.get(lineNumber));
        }
        
        
        List nvResultList = new ArrayList();
        int del_num1 = (int) (nvList.size() * 0.11);  
        for (int i = 0; i < del_num1; i++) {  
        	// 随机抽样,概率为0.11
    		Random rand = new Random();  
            // 指定读取的下标
            int lineNumber = (int) (rand.nextDouble() * nvList.size()); 
            
            nvResultList.add(nvList.get(lineNumber));
        } 
		
        System.out.println("女"+nvResultList);
        System.out.println("男"+nanResultList);
		return Struts2Utils.list2json(results);
	}
}
