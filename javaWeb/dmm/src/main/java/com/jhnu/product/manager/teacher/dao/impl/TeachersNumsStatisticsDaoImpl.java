package com.jhnu.product.manager.teacher.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.teacher.dao.ITeachersNumsStatisticsDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.StringUtils;

/**
 * @title 教职工人数变化统计Dao实现类
 * @description 教职工人数变化统计
 * @author Administrator
 * @date 2015/10/15 17:02
 */
@Repository("teachersNumsStatisticsDao")
public class TeachersNumsStatisticsDaoImpl implements
		ITeachersNumsStatisticsDao {

	// 自动注入BaseDao
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> teachersClassificationNums(int startYear,
			int endYear, String departmentId, String conditions) {
		//TODO 教职工是否在职状态对应T_CODE表中的状态不对，并且没有值，T_TEA表中的字段'tea_state_code'，T_CODE表中的字段'tea_status_code'
		//String wrongConditions1 = " left join t_code tc on tc.code_ = thr.tea_state_code ";
		//String wrongConditions2 = " thr.tea_state_code between '11' and '17' and tc.code_type='tea_status_code' and ";
		
		String sql2 = "";
		if(!StringUtils.hasText(departmentId)){
			return null;
		}else if("0".equals(departmentId)){
			sql2 = "";//显示全校数据
		}else if(!departmentId.contains(",")){
			sql2=" and thr.dept_id = '"+ departmentId +"' ";//显示某个院系
		}else{
			sql2 = "and thr.dept_id in ("+ departmentId +") "; //显示某些学院数据
		}
		
		String queryConditions = "";
		String getConditions = "";
		// allTeasCounts：按总人数统计或者是按入职人数统计
		if ("allTeasCounts".equals(conditions)) {
			getConditions = ",'入职人员' lable ";
			queryConditions = "";
		}
		// sourcesType:按来源类型统计入职人数
		else if ("sourcesType".equals(conditions)) {
			getConditions = ", thr.tea_souce_id lable ";
			queryConditions = ", thr.tea_souce_id ";
		}
		// formationType:按编制类别统计入职人数
		else if ("formationType".equals(conditions)) {
			getConditions = ", thr.bzlb_code lable ";
			queryConditions = ", thr.bzlb_code ";
		}
		// technicalTitleType:按职称类别统计入职人数
		else if ("technicalTitleType".equals(conditions)) {
			getConditions = ", thr.zyjszw_id lable ";
			queryConditions = ", thr.zyjszw_id ";
		}
		String sql = "select substr(thr.in_date,0,4) name , count(*) counts "
				+ getConditions
				+ " from t_tea thr "
				+ " where substr(thr.in_date, 0, 4) between '"+ startYear +"' and '"+ endYear+"' "
				+ sql2 + " group by substr(thr.in_date,0,4) "
				+ queryConditions + " order by substr(thr.in_date,0,4)";


		return baseDao
				.getBaseDao()
				.getJdbcTemplate()
				.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> teachersClassificationNumsByMonth(
			int year, String departmentId, String conditions) {
		String sql2 = "";
		if(!StringUtils.hasText(departmentId)){
			return null;
		}else if("0".equals(departmentId)){
			sql2 = "";//显示全校数据
		}else if(!departmentId.contains(",")){
			sql2="and thr.dept_id = '"+ departmentId +"' ";//显示某个院系
		}else{
			sql2 = "and thr.dept_id in ("+ departmentId +") "; //显示某些学院数据
		}
		
		String queryConditions = "";
		String getConditions = "";
		// allTeasCounts：按总人数统计或者是按入职人数统计
		if ("allTeasCounts".equals(conditions)) {
			getConditions = ",'入职人员' lable ";
			queryConditions = "";
		}
		// sourcesType:按来源类型统计入职人数
		else if ("sourcesType".equals(conditions)) {
			getConditions = ", thr.tea_souce_id lable ";
			queryConditions = ", thr.tea_souce_id ";
		}
		// formationType:按编制类别统计入职人数
		else if ("formationType".equals(conditions)) {
			getConditions = ", thr.bzlb_code lable ";
			queryConditions = ", thr.bzlb_code ";
		}
		// technicalTitleType:按职称类别统计入职人数
		else if ("technicalTitleType".equals(conditions)) {
			getConditions = ", thr.zyjszw_id lable ";
			queryConditions = ", thr.zyjszw_id ";
		}

		String sql = "select substr(thr.in_date,6,2) name, count(*) counts "
				+ getConditions + " from t_tea thr "
				+ " where substr(thr.in_date,0,4) = '"+ year +"' " + sql2
				+ " group by substr(thr.in_date,6,2) " + queryConditions
				+ " order by substr(thr.in_date,6,2)";

		return baseDao.getBaseDao().getJdbcTemplate()
				.queryForList(sql);
	}

	@Override
	public Page teachersClassificationNumsTable(int startYear, int endYear, String departmentId,int currentPage,int numPerPage) {
		String sql2 = "";
		if(!StringUtils.hasText(departmentId)){
			return null;
		}else if("0".equals(departmentId)){
			sql2 = "";//显示全校数据
		}else if(!departmentId.contains(",")){
			sql2="and thr.dept_id = '"+ departmentId +"' ";//显示某个院系
		}else{
			sql2 = "and thr.dept_id in ("+ departmentId +") "; //显示某些学院数据
		}
		String sql = "select thr.tea_no, thr.name_ names, tcd.name_ tcdname, thr.in_date thrdate, tcz.name_ tczname, tts.name_ ttsname, tc.name_ tcname from t_tea thr "
				+ " left join t_code_dept tcd on thr.dept_id = tcd.id "
				+ " left join t_code_zyjszw tcz on thr.zyjszw_id = tcz.id "
				+ " left join t_code_tea_source tts on thr.tea_source_id = tts.id "
				+ " left join t_code tc on thr.bzlb_code = tc.code_ "
				+ " where substr(thr.in_date,0,4) between "+ startYear +" and "+ endYear + sql2
				+ " group by thr.name_, thr.tea_no, tcd.name_, thr.in_date, tcz.name_, tts.name_, tc.name_ order by thrdate desc";
		
		return new Page(sql,currentPage, numPerPage,baseDao.getBaseDao().getJdbcTemplate(),null);
	}
}
