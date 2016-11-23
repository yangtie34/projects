package com.jhnu.product.manager.teacher.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.enums.ActionEnum;
import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.teacher.dao.ITeacherEmpInfoDao;
import com.jhnu.util.common.StringUtils;

/**
 * @title 教职工概况Dao实现类
 * @description 教职工基本概况统计
 * @author Administrator
 * @date 2015/10/13 18:12
 */
@Repository("teacherEmpInfoDao")
public class TeacherEmpInfoDaoImpl implements ITeacherEmpInfoDao {

	// 自动注入BaseDao
	@Autowired
	private BaseDao baseDao;

	/**
	 * @description 判断查询得到的结果集是否为空
	 * @param results
	 *            查询并返回的结果集
	 * @param ratio
	 *            占比
	 * @param count
	 *            数量
	 * @return List<Map>
	 */
	private List<Map<String, Object>> judgeListIsNull(
			List<Map<String, Object>> results) {
		if (results.size() == 0) {
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("ratio", "0.00");
			maps.put("count", "0");
			results.add(maps);
		}
		return results;
	}

	@Override
	public List<Map<String, Object>> teachersSexComposition(String departmentId) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = "and thr.dept_id = '" + departmentId + "' ";// 显示某个院系
		} else {
			sql2 = "and thr.dept_id in (" + departmentId + ") "; // 显示某些学院数据
		}

		String sql = "select thr.sex_code, tc.name_, count(*) counts from t_tea thr "
				+ "left join t_code tc on tc.code_ = thr.sex_code  "
				+ "where tc.code_type='SEX_CODE' "
				+ sql2
				+ " and tc.code_type='SEX_CODE' "
				+ " group by thr.sex_code, tc.name_ order by thr.sex_code, tc.name_";
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> teachersAgesComposition(String departmentId) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = "and tea_info.dept_id = '" + departmentId + "' ";// 显示某个院系
		} else {
			sql2 = "and tea_info.dept_id in (" + departmentId + ") "; // 显示某些学院数据
		}

		String sql = "select count(*) counts, age_bracket as rs from ("
				+ "  select case when age <= 22 then '0-22岁'  "
				+ " when age between 23 and 29 then '23-29岁' "
				+ " when age between 30 and 40 then '30-40岁' "
				+ " when age between 41 and 50 then '41-50岁' "
				+ " when age between 51 and 60 then '51-60岁' "
				+ " when age > 60 then '大于60岁' end as age_bracket, t.* from ("
				+ " select ceil(" + " months_between(" + " sysdate, to_date("
				+ " tea_info.birthday, 'yyyy-mm-dd hh24:mi:ss'))/12) "
				+ " as age, tea_info.id from t_tea tea_info "
				+ " where tea_info.birthday is not null " + sql2 + ") t) "
				+ " group by age_bracket order by age_bracket";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> teachersNationsComposition(
			String departmentId) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and thr.dept_id = '" + departmentId + "' ";// 显示某个院系
		} else {
			sql2 = " and thr.dept_id in (" + departmentId + ") "; // 显示某些学院数据
		}

		String sql = "select count(*) counts, thr.nation_code, tc.name_ nationName from t_tea thr "
				+ " left join t_code tc on tc.code_ = thr.nation_code "
				+ " where tc.code_type='NATION_CODE' "
				+ sql2
				+ " and tc.code_type='NATION_CODE' "
				+ "group by thr.nation_code, tc.name_ order by thr.nation_code, tc.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> teachersPoliticalStatus(String departmentId) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " where thr.dept_id = '" + departmentId + "' ";// 显示某个院系
		} else {
			sql2 = " where thr.dept_id in (" + departmentId + ") "; // 显示某些学院数据
		}

		String sql = "select nvl(thr.politics_code,'未维护') pol_code, count(*) counts, nvl(tc.name_,'未维护') politicalName from t_tea thr "
				+ "left join t_code tc on tc.code_ = thr.politics_code and tc.code_type= '"
				+ ActionEnum.ZZMM.getName()
				+ "'"
				+ sql2
				+ " and tc.code_type='POLITICS_CODE' "
				+ "group by thr.politics_code, tc.name_ order by thr.politics_code, tc.name_";
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> teachersSourceLand(int level,
			String departmentId, String sexthIdCard) {
		String codeAdminiDivName = "";
		String queryCondition = "";
		String codeAdminiDivId = "";

		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " where thr.dept_id = '" + departmentId + "' ";// 显示某个院系
		} else {
			sql2 = " where thr.dept_id in (" + departmentId + ") "; // 显示某些学院数据
		}
		// level = 0,来自全国各个省级或自治区和直辖市的生源地查询
		if (level == 0) {
			// TODO 这个sql语句现在只是测试，假如上线部署的时候 一定要换成这个
			/*
			 * String sql = "select ccc.name_, count(*) from t_tea thr " +
			 * "left join t_code_admini_div c on c.id = thr.domicile_id " +
			 * "left join t_code_admini_div cc on c.pid = cc.id " +
			 * "left join t_code_admini_div ccc on cc.pid = ccc.id " + sql2 +
			 * " group by ccc.name_";
			 */
			String sql = "select nvl(ccc.name_,'未维护') name, count(*) counts, nvl(ccc.code_, '未维护') sourceId from t_tea thr "
					+ "left join t_code_admini_div c on c.id =  substr(thr.idno, 0, 6) "
					+ "left join t_code_admini_div cc on c.pid = cc.id "
					+ "left join t_code_admini_div ccc on cc.pid = ccc.id "
					+ sql2 + " group by ccc.name_, ccc.code_";
			List<Map<String, Object>> results = baseDao.getBaseDao()
					.getJdbcTemplate().queryForList(sql);
			return judgeListIsNull(results);
		}
		// level = 1,某个省中各个市的生源地查询
		else if (level == 1) {
			codeAdminiDivName = "cc.name_ ";
			codeAdminiDivId = "cc.code_ ";
			queryCondition = "and cc.id='" + sexthIdCard + "'";
		}
		// level = 2,某个市中各个区和县的生源地查询
		else if (level == 2) {
			codeAdminiDivName = "c.name_";
			codeAdminiDivId = "c.code_ ";
			queryCondition = "and cc.id='" + sexthIdCard + "'";
		}
		// TODO 这个sql语句现在只是测试，假如上线部署的时候 一定要换成这个
		/*
		 * String sql = "select " + codeAdminiDivName +
		 * ", count(*) from t_tea thr " +
		 * "left join t_code_admini_div c on c.id = thr.domicile_id " +
		 * "left join t_code_admini_div cc on c.pid = cc.id " +
		 * "left join t_code_admini_div ccc on cc.pid = ccc.id " + sql2 +
		 * queryCondition + " group by " + codeAdminiDivName;
		 */
		String sql = "select "
				+ "nvl("
				+ codeAdminiDivName
				+ ", '未维护') name"
				+ ", count(*) counts, nvl("
				+ codeAdminiDivId
				+ ", '未维护') as sourceId from t_tea thr "
				+ "left join t_code_admini_div c on c.id = substr(thr.idno, 0, 6) "
				+ "left join t_code_admini_div cc on c.pid = cc.id "
				+ "left join t_code_admini_div ccc on cc.pid = ccc.id " + sql2
				+ queryCondition + " group by " + codeAdminiDivName + ", "
				+ codeAdminiDivId;
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> teachersSubjectsComposition(
			String departmentId) {
		// TODO 数据库数据不正确 暂时没实现
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " where thr.dept_id = '" + departmentId + "' ";// 显示某个院系
		} else {
			sql2 = " where thr.dept_id in (" + departmentId + ") "; // 显示某些学院数据
		}
		String sql = "select thr.subject_id, count(*) counts from t_tea thr "
				+ sql2 + " group by thr.subject_id order by thr.subject_id";
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> teachersEducationalBackground(
			String departmentId) {
		// TODO 教师学历表没有数据，这个sql语法没有错误，没有数据，逻辑没有测试
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " where thr.dept_id = '" + departmentId + "' ";// 显示某个院系
		} else {
			sql2 = " where thr.dept_id in (" + departmentId + ") "; // 显示某些学院数据
		}
		String sql = "select nvl(thr.EDU_ID, '未维护') eduId, count(*) counts, nvl(tte.graduate_school, '未维护') graduateSchool from t_tea thr "
				+ " left join T_TEA_EDU tte on tte.edu_id = thr.EDU_ID "
				+ sql2
				+ " group by thr.EDU_ID, tte.graduate_school order by thr.EDU_ID, tte.graduate_school";
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> teachersDegreeComposition(
			String departmentId) {
		// TODO 跟上面一样
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " where thr.dept_id = '" + departmentId + "' ";// 显示某个院系
		} else {
			sql2 = " where thr.dept_id in (" + departmentId + ") "; // 显示某些学院数据
		}
		String sql = "select nvl(thr.degree_id, '未维护') firstDegreeId, count(*) counts, nvl(tte.graduate_school, '未维护') graduateSchool from t_tea thr "
				+ " left join T_TEA_EDU tte on tte.degree_id = thr.degree_id "
				+ sql2
				+ " group by thr.degree_id, tte.graduate_school order by thr.degree_id, tte.graduate_school";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

}
