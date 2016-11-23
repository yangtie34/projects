package cn.gilight.product.net.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.net.dao.NetStuDeptDao;

@Repository("netStuDeptDao")
public class NetStuDeptDaoImpl implements NetStuDeptDao{

	@Autowired
	private BaseDao baseDao;
	
	public String getWhere(String startDate,String endDate){
		return CardTjUtil.getDateTJ(startDate, endDate);
	}
	
	public String getWhere(Map<String, String> deptTeach){
		return SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.NET_STU_DEPT.getCode(),"t");
	}
	
	@Override
	public List<Map<String, Object>> getNetStus(String startDate,
			String endDate, Map<String, String> deptTeach) {
		
		String dateTj=getWhere(startDate, endDate);
		String deptTj=getWhere(deptTeach);
		
		String dateTj2=" and '"+endDate+"' > substr(T.enroll_date, 0, 7) and '"+startDate+"' < to_char "+
		" (add_months(to_date(T.enroll_date, 'yyyy-mm-dd'),T.length_schooling * 12),'yyyy-mm') ";
		
		String querySql="select * from tl_net_stu_month t where t.stu_id is not null  "+dateTj+deptTj ;
		String querySql2="select * from T_STU t where 1=1 "+dateTj2+deptTj;
		
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeach,querySql,true,"inner join");
		querySql=sqldept;
		
		String sqldept2=SqlUtil.getDeptTeachGroup(deptTeach,querySql2,true,"inner join");
		querySql2=sqldept2;
		
		String sql="select a.next_dept_code code,a.next_dept_name name,a.on_stu,round(a.on_stu/b.all_stu*100) stu_ratio from "+
					"(select t.next_dept_code ,t.next_dept_name ,count(distinct t.STU_ID) on_stu "+
					"FROM ("+querySql+") t  "+
					"group by t.next_dept_code,t.next_dept_name ) a "+
					"left join ( "+
					"select COUNT(NO_) all_stu ,t.next_dept_code from ("+querySql2+") t group by t.next_dept_code  "+
					") b on a.next_dept_code=b.next_dept_code order by stu_ratio desc";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getNetCounts(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		
		String dateTj=getWhere(startDate, endDate);
		String deptTj=getWhere(deptTeach);
		String querySql="select * from tl_net_stu_month t where 1=1  "+dateTj+deptTj ;
		
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeach,querySql,true,"inner join");
		querySql=sqldept;
		String show="";
		if("all".equalsIgnoreCase(type)){
			show="round(sum(t.use_time)/60) time_, round(sum(t.use_flow)/1024) flow_ ";
		}else{
			show="round(avg(t.use_time/t.in_counts),2) time_, round(avg(t.use_flow/t.in_counts),2) flow_ ";
		}
		
		String sql="select t.next_dept_code code,t.next_dept_name name, "+show+
					"FROM ("+querySql+") t  "+
					"group by t.next_dept_code,t.next_dept_name order by code";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getNetTimes(String startDate,
			String endDate, Map<String, String> deptTeach, String type) {
		
		String dateTj=getWhere(startDate, endDate);
		String deptTj=getWhere(deptTeach);
		
		String querySql="select * from tl_net_type_month t where 1=1  "+dateTj+deptTj ;
		
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeach,querySql,true,"inner join");
		querySql=sqldept;
		
		String sql="select next_dept_code code,next_dept_name name,max(counts_) value,hour_ from ( "+
					"select t.hour_,t.next_dept_code ,t.next_dept_name ,sum(t."+type+"_counts) counts_ "+
					"FROM ("+querySql+") t  "+
					"group by t.next_dept_code,t.next_dept_name,t.hour_) group by hour_,next_dept_code,next_dept_name";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getNetWarnStus(String startDate,
			String endDate, Map<String, String> deptTeach, String type,String value) {
		
		String dateTj=getWhere(startDate, endDate);
		String deptTj=getWhere(deptTeach);
		String dateTj2=" and '"+endDate+"' > substr(T.enroll_date, 0, 7) and '"+startDate+"' < to_char "+
				" (add_months(to_date(T.enroll_date, 'yyyy-mm-dd'),T.length_schooling * 12),'yyyy-mm') ";
		
		String querySql="select sum(t.USE_time) all_time,sum(t.USE_FLOW) ALL_FLOW,sum(t.USE_MONEY) ALL_MONEY, t.stu_id, "+
				        "t.DEPT_ID, t.DEPT_NAME ,t.MAJOR_ID,t.MAJOR_NAME ,t.CLASS_ID   ,t.CLASS_NAME "+
						"from tl_net_stu_month t where 1=1 "+dateTj+deptTj+
						"group by t.stu_id,t.DEPT_ID, t.DEPT_NAME ,t.MAJOR_ID,t.MAJOR_NAME ,t.CLASS_ID,t.CLASS_NAME  ";
		
		String querySql2="select * from T_STU t where 1=1 "+dateTj2+deptTj;
		
		String sqldept=SqlUtil.getDeptTeachGroup(deptTeach,querySql,true,"inner join");
		querySql=sqldept;
		
		String sqldept2=SqlUtil.getDeptTeachGroup(deptTeach,querySql2,true,"inner join");
		querySql2=sqldept2;
		
		String sql="select a.next_dept_code code,a.next_dept_name name,a.WARN_STU,round(a.WARN_STU/b.all_stu*100) stu_ratio from "+
					"(select t.next_dept_code ,t.next_dept_name ,COUNT(DISTINCT t.STU_ID) WARN_STU  "+
					"FROM ("+querySql+") t where STU_ID IS NOT NULL AND T.all_"+type+">="+value+" "+
					"group by  t.next_dept_code,t.next_dept_name ) a "+
					"left join ( "+
					"select COUNT(NO_) all_stu ,t.next_dept_code from ("+querySql2+") t group by t.next_dept_code  "+
					") b on a.next_dept_code=b.next_dept_code order by stu_ratio desc";
		
		return baseDao.getJdbcTemplate().queryForList(sql);
	}
	
}
