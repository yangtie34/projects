package cn.gilight.product.net.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.code.Code;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.net.dao.NetCountDao;

@Repository("netCountDao")
public class NetCountDaoImpl implements NetCountDao{

	@Autowired
	private BaseDao baseDao;
	
	public String getWhere(String startDate,
			String endDate, Map<String, String> deptTeach){
		return SqlUtil.getWhere(startDate,endDate,deptTeach, ShiroTagEnum.NET_STU.getCode());
	}
	public String[] getZDBylb(String type){
		return SqlUtil.getZDBylb(type);
	}
	
	@Override
	public List<Map<String, Object>> getCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String sql="";
		if(type.equalsIgnoreCase("dq")){
			String area_id=Code.getKey("area.xz.code");
			String table ="select t.*, "+
					"case when c.id='"+area_id+"' then '"+area_id+"' else 'other' end origin_id, "+
					"case when c.id='"+area_id+"' then c.name_ else '其他' end origin_name "+
					"from TL_NET_STU_MONTH t inner join t_stu s on t.stu_id=s.no_ "+
					"left join t_code_admini_div c on substr(s.stu_origin_id,0,2)||'0000'=c.id where T.STU_ID IS NOT NULL "+tj;
			
			sql="SELECT origin_id code,origin_name name, "+
			"ROUND(SUM(T.USE_TIME)) ALL_TIME, "+
			"ROUND(SUM(T.USE_FLOW)) ALL_FLOW , "+
			"ROUND(AVG(T.USE_TIME/T.IN_COUNTS),2) ONE_TIME, "+
			"ROUND(AVG(T.USE_FLOW/T.IN_COUNTS),2) ONE_FLOW, "+
			"ROUND(AVG(T.USE_TIME/(TO_DATE('"+endDate+"','YYYY-MM') - TO_DATE('"+startDate+"','YYYY-MM'))),2) DAY_TIME, "+
			"ROUND(AVG(T.USE_FLOW/(TO_DATE('"+endDate+"','YYYY-MM') - TO_DATE('"+startDate+"','YYYY-MM'))),2) DAY_FLOW "+
			"FROM ("+table+") T "+
			"GROUP BY origin_id,origin_name ORDER BY code ";
		}else {
			String[] zdlb=getZDBylb(type);
			sql="SELECT "+zdlb[0]+" , "+
						"ROUND(SUM(T.USE_TIME)) ALL_TIME, "+
						"ROUND(SUM(T.USE_FLOW)) ALL_FLOW , "+
						"ROUND(AVG(T.USE_TIME/T.IN_COUNTS),2) ONE_TIME, "+
						"ROUND(AVG(T.USE_FLOW/T.IN_COUNTS),2) ONE_FLOW, "+
						"ROUND(AVG(T.USE_TIME/(TO_DATE('"+endDate+"','YYYY-MM') - TO_DATE('"+startDate+"','YYYY-MM'))),2) DAY_TIME, "+
						"ROUND(AVG(T.USE_FLOW/(TO_DATE('"+endDate+"','YYYY-MM') - TO_DATE('"+startDate+"','YYYY-MM'))),2) DAY_FLOW "+
						"FROM TL_NET_STU_MONTH T WHERE T.STU_ID IS NOT NULL  "+tj+
						"GROUP BY "+zdlb[1]+" ORDER BY code ";
			
			if(type.equalsIgnoreCase("mz")){
				sql="select t.code,t.name, "+
					" SUM(t.ALL_TIME) ALL_TIME, "+
					" SUM(t.ALL_FLOW) ALL_FLOW, "+
					" ROUND(AVG(t.ONE_TIME),2) ONE_TIME, "+
					" ROUND(AVG(t.ONE_FLOW),2) ONE_FLOW, "+
					" ROUND(AVG(t.DAY_TIME),2) DAY_TIME, "+
					" ROUND(AVG(t.DAY_FLOW),2) DAY_FLOW "+
					" from( "+
					sql+
					") t group by t.code,t.name ORDER BY t.code";
			}
		}
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getNetType(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		String tj=getWhere(startDate, endDate, deptTeach);
		String sql="";
		if(type.equalsIgnoreCase("dq")){
			String area_id=Code.getKey("area.xz.code");
			String table ="select t.*, "+
					"case when c.id='"+area_id+"' then '"+area_id+"' else 'other' end origin_id, "+
					"case when c.id='"+area_id+"' then c.name_ else '其他' end origin_name "+
					"from TL_NET_STU_MONTH t inner join t_stu s on t.stu_id=s.no_ "+
					"left join t_code_admini_div c on substr(s.stu_origin_id,0,2)||'0000'=c.id where T.STU_ID IS NOT NULL "+tj;
			
			sql="SELECT T.ON_TYPE_CODE TYPECODE,T.ON_TYPE_NAME TYPENAME,origin_id code,origin_name name, "+
			"ROUND(SUM(T.USE_TIME)) ALL_TIME,ROUND(SUM(T.USE_FLOW)) ALL_FLOW  "+
			"FROM ("+table+") T "+
			"GROUP BY T.ON_TYPE_CODE,T.ON_TYPE_NAME ,origin_id,origin_name ORDER BY TYPECODE ,CODE ";
		} else{
			String[] zdlb=getZDBylb(type);
			sql="SELECT T.ON_TYPE_CODE TYPECODE,T.ON_TYPE_NAME TYPENAME,"+zdlb[0]+
						",ROUND(SUM(T.USE_TIME)) ALL_TIME,ROUND(SUM(T.USE_FLOW)) ALL_FLOW  "+
						"FROM TL_NET_STU_MONTH T WHERE T.STU_ID IS NOT NULL  "+tj+
						"GROUP BY T.ON_TYPE_CODE,T.ON_TYPE_NAME ,"+zdlb[1]+" ORDER BY TYPECODE ,CODE ";
			if(type.equalsIgnoreCase("mz")){
				sql="SELECT T.TYPECODE,T.TYPENAME,T.CODE,T.NAME, "+
					" SUM(T.ALL_TIME) ALL_TIME, "+
					" SUM(T.ALL_FLOW) ALL_FLOW "+
					" FROM( "+
					sql+
					") T GROUP BY T.TYPECODE,T.TYPENAME,T.CODE,T.NAME ORDER BY TYPECODE,CODE ";
			}
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getNetStuRatio(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		String[] zdlb=getZDBylb(type);
		
		String deptTj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.NET_STU.getCode(),"t");
		
		String dateTj1=SqlUtil.getDateTJ(startDate, endDate, "t");
		
		String dateTj2=" and '"+endDate+"' > substr(T.enroll_date, 0, 7) and '"+startDate+"' < to_char "+
		" (add_months(to_date(T.enroll_date, 'yyyy-mm-dd'),T.length_schooling * 12),'yyyy-mm') ";
		
		String sql1="SELECT "+zdlb[0]+" ,COUNT(DISTINCT STU_ID) ON_STU,'1' FLAG "+
					"FROM TL_NET_STU_MONTH T  "+
					"WHERE T.STU_ID IS NOT NULL   "+deptTj+dateTj1+
					"GROUP BY "+zdlb[1]+" ORDER BY CODE ";
		
		if(type.equalsIgnoreCase("mz")){
			sql1="SELECT T.CODE,T.NAME,SUM(T.ON_STU) ON_STU,'1' FLAG FROM( "+sql1+") T GROUP BY T.CODE,T.NAME ";
		}
		
		String sql2="SELECT COUNT(NO_) ALL_STU ,'1' FLAG FROM T_STU T WHERE 1=1 "+deptTj+dateTj2;
		
		String sql="SELECT A.CODE,A.NAME,ROUND(A.ON_STU/B.ALL_STU*100) stu_ratio FROM "+
					"( "+sql1+" ) A LEFT JOIN ( "+sql2+" ) B ON A.FLAG=B.FLAG ORDER BY A.CODE ";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getNetHourStu(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		String tj=getWhere(startDate, endDate, deptTeach); 
		String sql="";
		if(type.equalsIgnoreCase("dq")){
			type="mz";
		}
		if(type.equalsIgnoreCase("dq")){
			String area_id=Code.getKey("area.xz.code");
			String table ="select t.*, "+
					"case when c.id='"+area_id+"' then '"+area_id+"' else 'other' end origin_id, "+
					"case when c.id='"+area_id+"' then c.name_ else '其他' end origin_name "+
					"from TL_NET_TYPE_MONTH t inner join t_stu s on t.stu_id=s.no_ "+
					"left join t_code_admini_div c on substr(s.stu_origin_id,0,2)||'0000'=c.id where 1=1 "+tj;
			
			sql="SELECT T.HOUR_,origin_id code,origin_name name, "+
					"SUM(T.ON_COUNTS) ON_COUNTS,SUM(T.OUT_COUNTS) OUT_COUNTS, "+
					"ROUND(SUM(T.IN_COUNTS)/months_between(to_date('"+endDate+"','yyyy-mm'),to_date('"+startDate+"','yyyy-mm'))) IN_COUNTS  "+
					"FROM ("+table+") T "+
					"GROUP BY T.HOUR_,origin_id,origin_name ORDER BY T.HOUR_";
		}else{
			String[] zdlb=getZDBylb(type);
			sql="SELECT T.HOUR_,"+zdlb[0]+", "+
						"SUM(T.ON_COUNTS) ON_COUNTS,SUM(T.OUT_COUNTS) OUT_COUNTS, "+
						"ROUND(SUM(T.IN_COUNTS)/months_between(to_date('"+endDate+"','yyyy-mm'),to_date('"+startDate+"','yyyy-mm'))) IN_COUNTS  "+
						"FROM TL_NET_TYPE_MONTH T WHERE 1=1 "+tj+
						"GROUP BY T.HOUR_,"+zdlb[1]+" ORDER BY T.HOUR_";
			if(type.equalsIgnoreCase("mz")){
				sql="SELECT T.HOUR_,T.CODE,T.NAME, "+
						" SUM(T.ON_COUNTS) ON_COUNTS, "+
						" SUM(T.OUT_COUNTS) OUT_COUNTS, "+
						" ROUND(SUM(T.IN_COUNTS)/months_between(to_date('"+endDate+"','yyyy-mm'),to_date('"+startDate+"','yyyy-mm'))) IN_COUNTS "+
						" FROM( "+sql+") T GROUP BY T.HOUR_,T.CODE,T.NAME ORDER BY T.HOUR_";
			}
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List<Map<String, Object>> getCountsTrend(
			Map<String, String> deptTeach, String type) {
		String tj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.NET_STU.getCode(),"t");
		String[] zdlb=getZDBylb(type);
		String sql="SELECT to_char(t.year_month,'yyyy-mm') year_month ,"+zdlb[0]+" , sum(t.all_time) all_time, "+
					"sum(t.all_flow) all_flow, round(avg(t.all_time/t.all_counts),2) one_time ,"+
					"round(avg(t.all_flow/t.all_counts),2) one_flow "+
					"from TL_NET_TREND t WHERE 1=1 "+tj+" group by t.year_month ,"+zdlb[1]+" order by t.year_month ";
		
		if(type.equalsIgnoreCase("mz")){
			sql="select t.year_month, t.code,t.name, "+
					" SUM(t.ALL_TIME) ALL_TIME, "+
					" SUM(t.ALL_FLOW) ALL_FLOW, "+
					" ROUND(AVG(t.ONE_TIME),2) ONE_TIME, "+
					" ROUND(AVG(t.ONE_FLOW),2) ONE_FLOW "+
					" from( "+sql+" ) t group by t.year_month,t.code,t.name ORDER BY t.year_month";
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List<Map<String, Object>> getNetStuRatioTrend(Map<String, String> deptTeach, String type) {
		String tj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.NET_STU.getCode(),"t");
		String[] zdlb=getZDBylb(type);
		String sql="SELECT to_char(t.year_month,'yyyy-mm') year_month ,"+zdlb[0]+" , "+
				    "sum(t.use_stu) use_stu,sum(t.all_stu) all_stu, "+
					"sum(t.use_stu)/sum(t.all_stu)*100 stu_ratio "+
					"from TL_NET_TREND t WHERE 1=1 "+tj+" group by t.year_month ,"+zdlb[1]+" order by t.year_month ";
		
		if(type.equalsIgnoreCase("mz")){
			sql="select t.year_month, t.code,t.name, "+
					" sum(t.use_stu) use_stu,sum(t.all_stu) all_stu, "+
					" SUM(t.use_stu)/SUM(T.all_stu) stu_ratio "+
					" from( "+sql+" ) t group by t.year_month,t.code,t.name ORDER BY t.year_month";
		}
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
