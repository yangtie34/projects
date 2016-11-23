package com.jhnu.product.manager.teacher.dao.impl;

import java.util.List;
import java.util.Map;










import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.teacher.dao.TeachingTaskStatisticsDao;
import com.jhnu.util.common.StringUtils;

@Repository("teachingTaskStatisticsDao")
public class TeachingTaskStatisticsDaoImpl implements TeachingTaskStatisticsDao{

	@Autowired
	private BaseDao baseDao;
	

	@Override
	public List<Map<String, Object>> getZcJxrw(String school_year,String term_code,String dept_id) {
		// TODO 教学任务中传过来的参数是教学组织机构的ID，把它当做行政组织机构的ID了，该ID须是行政组织机构和教学组织机构中的重复ID
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and tea.dept_id = '"+ dept_id +"' ";//显示某个院系
		}else{
			sql2 = "and tea.dept_id in ("+ dept_id +") "; //显示某些学院数据
		}
			
		String sql = "select co.name_,z.theory_period from t_code_zyjszw co inner join("
				+ " select t.zyjszw_id, sum(theory_period_sum) theory_period from  t_tea t inner join ("
				+ " select tea_id,sum(theory_period) theory_period_sum from ("
				+ " select tea.id tea_id,a.course_id,cou.theory_period from t_tea tea left join t_course_arrangement  a on a.tea_id = tea.id "
				+ " left join t_course cou on cou.id = a.course_id where a.school_year = '"+ school_year +"' and a.term_code = '"+ term_code +"'"
				+ sql2 + " order by tea_id ) f"
				+ " group by f.tea_id order by tea_id ) d on t.id = d.tea_id  group by  t.zyjszw_id ) z on z.zyjszw_id = co.id";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getBzlbJxrw(String school_year,String term_code, String dept_id) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="and tea.dept_id = '"+ dept_id +"' ";//显示某个院系
		}else{
			sql2 = "and tea.dept_id in ("+ dept_id +") "; //显示某些学院数据
		}
		
		String sql = "select co.name_,nvl(z.theory_period,0) theory_period from t_code co inner join("
				+ " select t.bzlb_code, sum(theory_period_sum) theory_period from  t_tea t inner join ("
				+ " select tea_id,sum(theory_period) theory_period_sum from ("
				+ " select tea.id tea_id,a.course_id,cou.theory_period from t_tea tea left join t_course_arrangement  a on a.tea_id = tea.id "
				+ " left join t_course cou on cou.id = a.course_id where a.school_year = '"+ school_year +"' and a.term_code = '"+ term_code +"'"
				+ sql2 + " order by tea_id ) f"
				+ " group by f.tea_id order by tea_id ) d on t.id = d.tea_id  group by  t.bzlb_code ) z "
				+ " on z.bzlb_code = co.code_ where co.code_type = 'BZLB_CODE'";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getXyZcJxrw(String dept_id,String school_year,String term_code) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="where t.dept_id = '"+ dept_id +"' ";//显示某个院系
		}else{
			sql2 = "where t.dept_id in ("+ dept_id +") "; //显示某些学院数据
		}
		
		String sql = "select dept.name_ dept_name, co.name_,nvl(z.theory_period,0) theory_period from t_code_zyjszw co left join("
				+ " select t.dept_id, t.zyjszw_id, sum(theory_period_sum) theory_period from  t_tea t left join ("
				+ " select tea_id,sum(theory_period) theory_period_sum from ("
				+ " select tea.id tea_id,a.course_id,cou.theory_period,a.teachingclass_id from t_tea tea left join t_course_arrangement  a on a.tea_id = tea.id "
				+ " left join t_course cou on cou.id = a.course_id where a.school_year = '"+ school_year +"' and a.term_code = '"+ term_code +"'"
				+ " order by tea_id ) f"
				+ " group by f.tea_id order by tea_id ) d on t.id = d.tea_id "+ sql2 +"group by t.dept_id,t.zyjszw_id ) z on z.zyjszw_id = co.id"
				+ " left join t_code_dept dept on dept.id = z.dept_id order by dept_name";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getXyBzlbJxrw(String dept_id,String school_year,String term_code) {
		String sql2 = "";
		if(!StringUtils.hasText(dept_id)){
			return null;
		}else if("0".equals(dept_id)){
			sql2 = "";//显示全校数据
		}else if(!dept_id.contains(",")){
			sql2="where t.dept_id = '"+ dept_id +"' ";//显示某个院系
		}else{
			sql2 = "where t.dept_id in ("+ dept_id +") "; //显示某些学院数据
		}
		
		String sql = "select dept.name_ dept_name,co.name_,nvl(z.theory_period,0) theory_period from t_code co left join("
				+ " select t.dept_id, t.bzlb_code, sum(theory_period_sum) theory_period from  t_tea t left join ( "
				+ " select tea_id,sum(theory_period) theory_period_sum from ("
				+ " select tea.id tea_id,a.course_id,cou.theory_period from t_tea tea left join t_course_arrangement  a on a.tea_id = tea.id "
				+ " left join t_course cou on cou.id = a.course_id where a.school_year = '"+ school_year +"' and a.term_code = '"+ term_code +"' "
				+ "order by tea_id ) f"
				+ " group by f.tea_id order by tea_id ) d on t.id = d.tea_id "+ sql2 +" group by  t.dept_id, t.bzlb_code ) z on z.bzlb_code = co.code_"
				+ " left join t_code_dept dept on dept.id = z.dept_id where co.code_type = 'BZLB_CODE' order by dept_name";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	
	

}
