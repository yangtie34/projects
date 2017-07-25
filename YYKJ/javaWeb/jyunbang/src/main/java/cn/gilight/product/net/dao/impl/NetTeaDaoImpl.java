package cn.gilight.product.net.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.net.dao.NetTeaDao;

@Repository("netTeaDao")
public class NetTeaDaoImpl implements NetTeaDao{

	@Autowired
	private BaseDao baseDao;
	
	public String getWhere(String startDate,
			String endDate, Map<String, String> deptTeach){
		return SqlUtil.getWhere(startDate,endDate,deptTeach, ShiroTagEnum.NET_TEA.getCode());
	}
	public String[] getTeaBylb(String type){
		return SqlUtil.getTeaBylb(type);
	}
	
	@Override
	public List<Map<String, Object>> getCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		String[] zdlb=getTeaBylb(type);
		String tj=getWhere(startDate, endDate, deptTeach)+zdlb[2];
		String sql="SELECT "+zdlb[0]+" , "+
					"ROUND(SUM(T.USE_TIME)) ALL_TIME, "+
					"ROUND(SUM(T.USE_FLOW)) ALL_FLOW , "+
					"ROUND(AVG(T.USE_TIME/T.IN_COUNTS),2) ONE_TIME, "+
					"ROUND(AVG(T.USE_FLOW/T.IN_COUNTS),2) ONE_FLOW, "+
					"ROUND(AVG(T.USE_TIME/(TO_DATE('"+endDate+"','YYYY-MM') - TO_DATE('"+startDate+"','YYYY-MM'))),2) DAY_TIME, "+
					"ROUND(AVG(T.USE_FLOW/(TO_DATE('"+endDate+"','YYYY-MM') - TO_DATE('"+startDate+"','YYYY-MM'))),2) DAY_FLOW "+
					"FROM TL_NET_TEA_MONTH T WHERE T.TEA_NO IS NOT NULL  "+tj+
					"GROUP BY "+zdlb[1]+" ORDER BY code ";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getNetType(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		String[] zdlb=getTeaBylb(type);
		String tj=getWhere(startDate, endDate, deptTeach)+zdlb[2];
		String sql="SELECT T.ON_TYPE_CODE TYPECODE,T.ON_TYPE_NAME TYPENAME,"+zdlb[0]+
					",ROUND(SUM(T.USE_TIME)) ALL_TIME,ROUND(SUM(T.USE_FLOW)) ALL_FLOW  "+
					"FROM TL_NET_TEA_MONTH T WHERE T.TEA_NO IS NOT NULL  "+tj+
					"GROUP BY T.ON_TYPE_CODE,T.ON_TYPE_NAME ,"+zdlb[1]+" ORDER BY TYPECODE ,CODE ";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getNetHourTea(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		String[] zdlb=getTeaBylb(type);
		String tj=getWhere(startDate, endDate, deptTeach)+zdlb[2];
		String sql="SELECT T.HOUR_,"+zdlb[0]+", "+
					"SUM(T.ON_COUNTS) ON_COUNTS,SUM(T.OUT_COUNTS) OUT_COUNTS, "+
					"ROUND(SUM(T.IN_COUNTS)/months_between(to_date('"+endDate+"','yyyy-mm'),to_date('"+startDate+"','yyyy-mm'))) IN_COUNTS  "+
					"FROM TL_NET_TEA_MONTH T WHERE 1=1 "+tj+
					"GROUP BY T.HOUR_,"+zdlb[1]+" ORDER BY T.HOUR_";
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List<Map<String, Object>> getCountsTrend(
			Map<String, String> deptTeach, String type) {
		String[] zdlb=getTeaBylb(type);
		String tj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.NET_TEA.getCode(),"t")+zdlb[2];
		String sql="SELECT to_char(t.year_month,'yyyy-mm') year_month ,"+zdlb[0]+" , sum(t.all_time) all_time, "+
					"sum(t.all_flow) all_flow, round(avg(t.all_time/t.all_counts),2) one_time ,"+
					"round(avg(t.all_flow/t.all_counts),2) one_flow "+
					"from TL_NET_TREND t WHERE 1=1 "+tj+" group by t.year_month ,"+zdlb[1]+" order by t.year_month ";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
