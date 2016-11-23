package com.jhkj.mosdc.sc.job.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.sc.job.XsCjService;
import com.jhkj.mosdc.sc.util.MathUtils;

public class XsCjServiceImpl extends BaseServiceImpl implements XsCjService{
	
	@Override
	public void getXscj() {
		clearTable();
		String sql="select id,mc from tb_jxzzjg where cc=1 and sfky=1";
		List<Map<String,Object>> listYx=baseDao.querySqlList(sql);
		Map<String,Object> MapYx=null;
		for (int xn = 2010; xn < 2015; xn++) {
			for (int xq = 0; xq < 2; xq++) {
				for (int i = 0; i < listYx.size(); i++) {
					sql="select nvl(cj.bfzkscj,0) as cj ,yx.mc as yxmc,zy.id as zyid,zy.mc as zymc from t_xs_kscj cj "+
						"inner join tb_xjda_xjxx xs on cj.xh=xs.xh "+
						"left join tb_jxzzjg yx on xs.yx_id=yx.id "+
						"left join tb_jxzzjg zy on xs.zy_id=zy.id "+
						"where cj.xn='"+xn+"' and cj.xq='"+xq+"' and cj.djzkscj is null and "+
						"yx.id="+listYx.get(i).get("ID")+ " order by zyid,cj desc";
					List<Map<String,Object>> listYxMap=baseDao.querySqlList(sql);
					List<Double> listYxCj=new ArrayList<Double>();
					List<Double> listYxZy=new ArrayList<Double>();
					sql="select id,mc from tb_jxzzjg where cc=2 and fjd_id="+listYx.get(i).get("ID")+" order by id";
					List<Map<String,Object>> listZyMap=baseDao.querySqlList(sql);
					List<String> zyidList=new ArrayList<String>();
					String zyid="",oldZyid="",zymc="";
					for (int j = 0; j < listYxMap.size(); j++) {
						zyid=listYxMap.get(j).get("zyid").toString();
						if(!zyid.equals(oldZyid)){
							if(j!=0){
								MapYx=new HashMap<String, Object>();
								MapYx.put("ID", oldZyid);
								MapYx.put("P_ID", listYx.get(i).get("ID"));
								MapYx.put("MC", zymc);
								MapYx.put("CC", 2);
								MapYx.put("XN", xn);
								MapYx.put("XQ", xq);
								MapYx.put("PFJ", MathUtils.getAvgvalue(listYxZy));
								MapYx.put("ZGF", MathUtils.getMaxValue(listYxZy));
								MapYx.put("JC", MathUtils.getRange(listYxZy));
								MapYx.put("ZWS", MathUtils.getMiddleValue(listYxZy));
								MapYx.put("FC", MathUtils.getVariance(listYxZy));
								MapYx.put("BZC", MathUtils.getStandardDeviation(listYxZy));
								MapYx.put("HGL", MathUtils.getScale(listYxZy,60d));
								MapYx.put("YXL", MathUtils.getScale(listYxZy,90d));
								insertTable(MapYx);
							}
							//开始新的专业数据计算
							oldZyid=zyid;
							zymc=listYxMap.get(j).get("ZYMC").toString();
							listYxZy=new ArrayList<Double>();
							zyidList.add(oldZyid);
						}
						listYxZy.add(Double.parseDouble(listYxMap.get(j).get("CJ").toString()));
						listYxCj.add(Double.parseDouble(listYxMap.get(j).get("CJ").toString()));
					}
					for (int k = 0; k < listZyMap.size();k++) {
						for (int j = 0; j < zyidList.size(); j++) {
							if(zyidList.get(j).equals(listZyMap.get(k).get("ID").toString())){
								listZyMap.remove(k);
							}
						}
					}
					for (int k = 0; k < listZyMap.size();k++) {
						MapYx=new HashMap<String, Object>();
						MapYx.put("ID", listZyMap.get(k).get("ID"));
						MapYx.put("P_ID", listYx.get(i).get("ID"));
						MapYx.put("MC", listZyMap.get(k).get("MC"));
						MapYx.put("CC", 2);
						MapYx.put("XN", xn);
						MapYx.put("XQ", xq);
						MapYx.put("PFJ", 0);
						MapYx.put("ZGF", 0);
						MapYx.put("JC", 0);
						MapYx.put("ZWS", 0);
						MapYx.put("FC", 0);
						MapYx.put("BZC", 0);
						MapYx.put("HGL", "0.00%");
						MapYx.put("YXL", "0.00%");
						insertTable(MapYx);
					}
					//院系数据进行计算放置到数据库
					MapYx=new HashMap<String, Object>();
					MapYx.put("ID", listYx.get(i).get("ID"));
					MapYx.put("P_ID", 0);
					MapYx.put("MC", listYx.get(i).get("MC"));
					MapYx.put("CC", 1);
					MapYx.put("XN", xn);
					MapYx.put("XQ", xq);
					MapYx.put("PFJ", MathUtils.getAvgvalue(listYxCj));
					MapYx.put("ZGF", MathUtils.getMaxValue(listYxCj));
					MapYx.put("JC", MathUtils.getRange(listYxCj));
					MapYx.put("ZWS", MathUtils.getMiddleValue(listYxCj));
					MapYx.put("FC", MathUtils.getVariance(listYxCj));
					MapYx.put("BZC", MathUtils.getStandardDeviation(listYxCj));
					MapYx.put("HGL", MathUtils.getScale(listYxCj,60d));
					MapYx.put("YXL", MathUtils.getScale(listYxCj,90d));
					insertTable(MapYx);
				}
			}
		}
	}
	private void insertTable(Map<String,Object> map){
		String insert = "INSERT INTO TB_JOB_XSCJ_YX_ZY "
				+ "VALUES('"+map.get("ID")+"','"+map.get("P_ID")+"','"+map.get("MC")+"',"
				+map.get("CC")+",'"+map.get("XN")+"',"+map.get("XQ")+",'"+map.get("PFJ")+"',"
				+ "'"+map.get("ZGF")+"','"+map.get("JC")+"','"+map.get("ZWS")+"','"+map.get("FC")+"',"
				+ "'"+map.get("BZC")+"','"+map.get("HGL")+"','"+map.get("YXL")+"')";
		System.out.println(insert);
		baseDao.insert(insert);
	}
	
	private void clearTable(){
		String sql="DELETE TB_JOB_XSCJ_YX_ZY";
		baseDao.deleteBySql(sql);
		System.out.println("---已清空学生成绩的统计信息---");
	}
	
}
