package cn.gilight.product.card.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.code.Code;
import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.SqlUtil;
import cn.gilight.product.card.dao.CardTjUtil;
import cn.gilight.product.card.dao.CardUsedDao;


@Repository("cardUsedDao")
public class CardUsedDaoImpl implements CardUsedDao{
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getCardUsed(String startDate, String endDate,
			Map<String, String> deptTeach) {
		String dateTj=CardTjUtil.getDateTJ(startDate, endDate);
		String deptTj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_CU.getCode(),"t");
		String sql="select round(a.nums/DECODE(b.nums, 0,1,b.nums)*100,2) use_rate,a.nums use_Num,DECODE(b.nums, 0,1,b.nums) all_Num from "+
					"(select count(*) nums,'1' flag from ( "+
						"select sum(t.pay_count) pay_count,t.stu_id,t.stu_name, "+
						"t.sex_code,t.sex_name,t.dept_id,t.dept_name,t.major_id,t.major_name from  "+
						"tl_card_use_stu_month t where 1=1  "+ dateTj+deptTj+
						"group by t.stu_id,t.stu_name,t.sex_code,t.sex_name,t.dept_id,t.dept_name,t.major_id,t.major_name "+
						") where  pay_count >= (MONTHS_BETWEEN(to_date('"+endDate+"','yyyy-mm'),to_date('"+startDate+"','yyyy-mm'))*"+Code.getKey("card.uesd")+") )a "+
						"left join  "+
						"(select count(*) nums ,'1' flag from t_stu t where '"+endDate+"' > substr(t.enroll_date,0,7)  "+
						"and '"+startDate+"' <to_char(add_months(to_date(t.enroll_date,'yyyy-mm-dd'),t.length_schooling*12),'yyyy-mm') "+
						deptTj+" )b on a.flag=b.flag";
		return baseDao.getJdbcTemplate().queryForMap(sql);
	}

	@Override
	public List<Map<String, Object>> getCardUsedBySex(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getCardUsedSql(startDate, endDate, deptTeach, "sex");
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCardUsedByEdu(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getCardUsedSql(startDate, endDate, deptTeach, "edu");
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getCardUsedByDept(String startDate,
			String endDate, Map<String, String> deptTeach) {
		String sql=getCardUsedSql(startDate, endDate, deptTeach, "deptTeach");
		return baseDao.getJdbcTemplate().queryForList(sql);
	}

	@Override
	public Page getCardUsedPage(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc,
			String startDate, String endDate, Map<String, String> deptTeach,String type,String type_code) {
		String queryTj="";
		if("use_sex".equals(type)){
			queryTj="and sex_code='"+type_code+"' ";
		}else if("use_edu".equals(type)){
			queryTj="and edu_id='"+type_code+"' ";
		}
		String dateTj=CardTjUtil.getDateTJ(startDate, endDate);
		String deptTj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_CU.getCode(),"t");
		String sql="select * from ( "+
					"select sum(t.pay_count) pay_count,t.stu_id,t.stu_name,t.sex_code,t.sex_name,t.dept_id, "+
					"t.dept_name,t.major_id,t.major_name,t.class_id,t.class_name,t.edu_id,t.edu_name from  "+
					"tl_card_use_stu_month t where 1=1  "+dateTj+deptTj+
					"group by t.stu_id,t.stu_name,t.sex_code,t.sex_name,t.dept_id,t.dept_name,t.major_id, "+
					"t.major_name,t.class_id,t.class_name,t.edu_id,t.edu_name "+
					") where  pay_count >= (MONTHS_BETWEEN(to_date('"+endDate+"','yyyy-mm'),"+
					"to_date('"+startDate+"','yyyy-mm'))*"+Code.getKey("card.uesd")+") "+
					queryTj+" order by pay_count desc ";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow,sort,isAsc);
	}
	
	@Override
	public Page getNoCardUsed(int currentPage, int numPerPage,int totalRow,
			String startDate, String endDate, Map<String, String> deptTeach) {
		String dateTj=CardTjUtil.getDateTJ(startDate, endDate);
		String deptTj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_CU.getCode(),"t");
		String sql="select * from ( "+
					"select sum(t.pay_count) pay_count,t.stu_id,t.stu_name,t.sex_code,t.sex_name,t.dept_id, "+
					"t.dept_name,t.major_id,t.major_name,t.class_id,t.class_name,t.edu_id,t.edu_name from  "+
					"tl_card_use_stu_month t where 1=1  "+dateTj+deptTj+
					"group by t.stu_id,t.stu_name,t.sex_code,t.sex_name,t.dept_id,t.dept_name,t.major_id, "+
					"t.major_name,t.class_id,t.class_name,t.edu_id,t.edu_name "+
					") where  pay_count < (MONTHS_BETWEEN(to_date('"+endDate+"','yyyy-mm'),"+
					"to_date('"+startDate+"','yyyy-mm'))*"+Code.getKey("card.uesd")+") "+
					"order by pay_count";
		return new Page(sql, currentPage, numPerPage, baseDao.getJdbcTemplate(), totalRow);
	}
	
	private static String getCardUsedSql(String startDate,String endDate, Map<String, String> deptTeach,String queryType){
		String dateTj=CardTjUtil.getDateTJ(startDate, endDate);
		String deptTj=SqlUtil.getDeptTeachTj(deptTeach, ShiroTagEnum.CARD_CU.getCode(),"t");
		String id="",name="";
		String querySql="select * from tl_card_use_stu_month t where 1=1 "+dateTj+deptTj ;
		String querySql2="select * from t_stu t where '"+endDate+"' > substr(t.enroll_date,0,7)  "+
						"and '"+startDate+"' <to_char(add_months(to_date(t.enroll_date,'yyyy-mm-dd'),t.length_schooling*12),'yyyy-mm') "+deptTj;
		if("sex".equals(queryType)){
			id="sex_code";name="sex_name";
		}else if("edu".equals(queryType)){
			id="edu_id";name="edu_name";
		}else if("deptTeach".equals(queryType)){
			
			String sqldept=SqlUtil.getDeptTeachGroup(deptTeach,querySql,true,"inner join");
			querySql=sqldept;
			
			String sqldept2=SqlUtil.getDeptTeachGroup(deptTeach,querySql2,true,"inner join");
			querySql2=sqldept2;
			
			id="next_dept_code";name="next_dept_name";
		}
		String sql="select round(a.nums/DECODE(b.nums, 0,1,b.nums)*100,2) use_rate,a.nums use_Num,DECODE(b.nums, 0,1,b.nums) all_Num,nvl(a."+id+",'') type_code,nvl(a."+name+",'未维护') type_name from "+
				"(select count(*) nums,"+id+","+name+" from ( "+
				"select sum(t.pay_count) pay_count,t.stu_id,t.stu_name,t."+id+",t."+name+" from ("+querySql+") t "+
				"group by t.stu_id,t.stu_name,t."+id+",t."+name+
				" ) where  pay_count >= (MONTHS_BETWEEN(to_date('"+endDate+"','yyyy-mm'),to_date('"+startDate+"','yyyy-mm'))*"+Code.getKey("card.uesd")+")  group by "+id+","+name+" )a "+
				"left join  "+
				"(select count(*) nums , "+id+" from ("+querySql2+") t "+
				" group by "+id+" )b on a."+id+"=b."+id+" order by type_code ";
		return sql;
	}
	
}
