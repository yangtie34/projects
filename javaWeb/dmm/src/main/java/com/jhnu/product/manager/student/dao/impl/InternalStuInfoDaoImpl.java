package com.jhnu.product.manager.student.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.student.dao.IInternalStuInfoDao;
import com.jhnu.util.common.StringUtils;

/**
 * @title 学生概况Dao实现类
 * @description 在校生基本组成概况统计
 * @author Administrator
 * @date 2015/10/13 10:13
 */
@Repository("internalStuInfoDao")
public class InternalStuInfoDaoImpl implements IInternalStuInfoDao {

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
	public List<Map<String, Object>> typeNumsOfStus(String deptMajor,
			boolean isLeaf) {
		String sql2 = "";
		if (!StringUtils.hasText(deptMajor)) {
			return null;
		} else if ("0".equals(deptMajor)) {
			sql2 = "";// 显示全校数据
		} else if (!deptMajor.contains(",")) {
			sql2 = " and (s.dept_id = '" + deptMajor + "' or s.major_id = '"
					+ deptMajor + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + deptMajor + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + deptMajor + ") "; // 显示某些学院数据
			}
		}

		String sql = "select count(*) counts, s.edu_id, ce.name_ eduName from t_stu s left join t_code_education ce on s.edu_id = ce.id where s.stu_state_code <>4 "
				+ sql2
				+ " group by s.edu_id, ce.name_ order by s.edu_id, ce.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> stusOfNumsContrastStatistics(
			String deptMajor, boolean isLeaf) {
		String sql2 = "";
		String deptOrMajorQueryConditions = ""; // 根据传过来deptMajor进行不同的查询
		if (!StringUtils.hasText(deptMajor)) {
			return null;
		} else if ("0".equals(deptMajor)) {
			sql2 = "";// 显示全校数据
			deptOrMajorQueryConditions = "s.dept_id = dt.id ";
		} else if (!deptMajor.contains(",")) {
			sql2 = " and (s.dept_id = '" + deptMajor + "' or s.major_id = '"
					+ deptMajor + "' )";// 显示某个院系或某个专业数据
			deptOrMajorQueryConditions = "s.major_id = dt.id ";
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + deptMajor + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + deptMajor + ") "; // 显示某些学院数据
			}
		}
		String sql = "select "
				+ "dt.name_ dept_name, "
				+ "s.edu_id, ce.name_ eduName, count(*) nums from t_stu s "
				+ "left join t_code_dept_teach dt on "
				+ deptOrMajorQueryConditions
				+ "left join t_code_education ce on "
				+ "s.edu_id = ce.id "
				+ "where s.stu_state_code <>4"
				+ sql2
				+ " group by dt.name_, s.edu_id, ce.name_ order by dt.name_, s.edu_id, ce.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> stusSexComposition(String educationId,
			String departmentId, boolean isLeaf) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and (s.dept_id = '" + departmentId + "' or s.major_id = '"
					+ departmentId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + departmentId + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + departmentId + ") "; // 显示某些学院数据
			}
		}

		String sql = "select count(*) counts, s.sex_code sexCode, tc.name_ sexName from t_stu s "
				+ "inner join t_code_dept_teach dt on s.dept_id = dt.id "
				+ "left join t_code tc on tc.code_ = s.sex_code"
				+ " where s.stu_state_code <>4 and s.edu_id= '"
				+ educationId
				+ "' "
				+ sql2
				+ " and tc.code_type='SEX_CODE' "
				+ " group by s.sex_code, tc.name_ order by s.sex_code, tc.name_";
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> stusAgesComposition(String educationId,
			String departmentId, boolean isLeaf) {

		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and (stu_info.dept_id = '" + departmentId
					+ "' or stu_info.major_id = '" + departmentId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and stu_info.major_id in (" + departmentId + ") "; // 显示某些专业数据
			} else {
				sql2 = " and stu_info.dept_id in (" + departmentId + ") "; // 显示某些学院数据
			}
		}

		String sql = "select count(*) counts, age_bracket as ageName from("
				+ " select case when age <= 16 then '0-16岁' "
				+ " when age between 17 and 20 then '17-20岁' "
				+ " when age between 21 and 23 then '21-23岁' "
				+ " when age between 24 and 26 then '24-26岁' "
				+ " when age between 26 and 28 then '26-28岁' "
				+ " when age > 28 then '大于28岁' end as age_bracket, t.* from("
				+ " select ceil(months_between(sysdate, to_date(stu_info.birthday, 'yyyy-mm-dd hh24:mi:ss'))/12)"
				+ " as age, stu_info.id from t_stu stu_info left join t_code_dept_teach dt on stu_info.dept_id = dt.id "
				+ " where stu_info.birthday is not null and stu_info.stu_state_code<>4 and"
				+ " stu_info.edu_id= '" + educationId + "' " + sql2
				+ ") t) group by age_bracket order by age_bracket";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> stusNationsComposition(String educationId,
			String departmentId, boolean isLeaf) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and (s.dept_id = '" + departmentId + "' or s.major_id = '"
					+ departmentId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + departmentId + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + departmentId + ") "; // 显示某些学院数据
			}
		}

		String sql = "select count(*) counts, s.nation_code nationCode, tc.name_ nationName from t_stu s "
				+ "inner join t_code_dept_teach dt on s.dept_id = dt.id "
				+ "left join t_code tc on s.nation_code = tc.code_ "
				+ "where "
				+ " s.stu_state_code <>4 and s.edu_id = '"
				+ educationId
				+ "' "
				+ sql2
				+ "and tc.code_type = 'NATION_CODE' "
				+ "group by s.nation_code, tc.name_ "
				+ "order by s.nation_code, tc.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> stuPoliticalStatus(String educationId,
			String departmentId, boolean isLeaf) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and (s.dept_id = '" + departmentId + "' or s.major_id = '"
					+ departmentId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + departmentId + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + departmentId + ") "; // 显示某些学院数据
			}
		}
		/*
		 * String sql =
		 * "select count(*), s.politics_code from t_stu s left join t_code_dept_teach dt on s.dept_id = dt.id where "
		 * + " s.stu_state_code <>4 and s.edu_id = '" + educationId + "' " +
		 * sql2 + " group by s.politics_code order by s.politics_code";
		 */

		String sql = "select count(*) counts, s.edu_id, tc.name_ policalName, ce.name_ eduName from t_stu s "
				+ "inner join t_code_education ce on s.edu_id = ce.id  and s.edu_id = '"
				+ educationId
				+ "' "
				+ sql2
				+ " left join t_code tc on tc.code_ = s.politics_code where s.stu_state_code <>4 and tc.code_type='POLITICS_CODE' group by s.edu_id, ce.name_, tc.name_ order by s.edu_id, ce.name_, tc.name_";
		// System.out.println("sql:"+sql);
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<java.lang.String, Object>> stuSourceLand(int level,
			String educationId, String departmentId, boolean isLeaf,
			String sexthIdCards) {

		String codeAdminiDivId = "";
		String codeAdminiDivName = "";
		String queryCondition = "";
		// level = 0,来自全国各个省级或自治区和直辖市的生源地查询
		if (level == 0) {
			codeAdminiDivName = "ccc.name_ ";
			codeAdminiDivId = "ccc.code_ ";
			queryCondition = "";
		}
		// level = 1,某个省中各个市的生源地查询
		else if (level == 1) {
			codeAdminiDivName = "cc.name_";
			codeAdminiDivId = "cc.code_ ";
			queryCondition = "and ccc.id='" + sexthIdCards + "'";
		}
		// level = 2,某个市中各个区和县的生源地查询
		else if (level == 2) {
			codeAdminiDivName = "c.name_";
			codeAdminiDivId = "c.code_ ";
			queryCondition = "and cc.id='" + sexthIdCards + "'";
		}
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and (s.dept_id = '" + departmentId + "' or s.major_id = '"
					+ departmentId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + departmentId + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + departmentId + ") "; // 显示某些学院数据
			}
		}
		//TODO 把 substr(s.idno,0,6) 修改成 s.stu_origin_id
		String sql = "select nvl("
				+ codeAdminiDivName
				+ ", '未维护') name, count(*) counts, nvl("
				+ codeAdminiDivId
				+ ", '未维护') sourceId from t_stu s "
				+ " left join t_code_admini_div c on c.id = substr(s.idno,0,6)"
				+ " left join t_code_admini_div cc on c.pid=cc.id "
				+ " left join t_code_admini_div ccc on cc.pid=ccc.id "
				+ " left join t_code_dept_teach dt on s.dept_id= dt.id where "
				+ " s.stu_state_code <>4 " + queryCondition + " and s.edu_id='"
				+ educationId + "'" + sql2 + "group by " + codeAdminiDivName
				+ ", " + codeAdminiDivId;

		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> stuSubjectsComposition(String educationId,
			String departmentId, boolean isLeaf) {
		String sql2 = "";

		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and (s.dept_id = '" + departmentId + "' or s.major_id = '"
					+ departmentId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + departmentId + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + departmentId + ") "; // 显示某些学院数据
			}
		}
		String sql = "select count(*) counts, dt.subject_id subjectId, tcs.name_ subjectName from t_stu s "
				+ " inner join t_code_dept_teach dt on s.dept_id=dt.id "
				+ "left join t_code_subject tcs on tcs.id = dt.subject_id "
				+ " where s.stu_state_code <>4 "
				+ " and s.edu_id='"
				+ educationId
				+ "' "
				+ sql2
				+ "group by dt.subject_id, tcs.name_ "
				+ " order by dt.subject_id, tcs.name_";
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> stuEducationalBackground(
			String departmentId, boolean isLeaf) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and (s.dept_id = '" + departmentId + "' or s.major_id = '"
					+ departmentId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + departmentId + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + departmentId + ") "; // 显示某些学院数据
			}
		}

		String sql = "select count(*) counts, tce.name_ eduName from t_stu s "
				+ " left join t_code_dept_teach dt on s.dept_id=dt.id "
				+ " left join t_code_education tce on s.edu_id = tce.id"
				+ " where s.stu_state_code <>4 " + sql2
				+ " group by s.edu_id, tce.name_ order by s.edu_id, tce.name_";
		List<Map<String, Object>> results = baseDao.getBaseDao()
				.getJdbcTemplate().queryForList(sql);
		return judgeListIsNull(results);
	}

	@Override
	public List<Map<String, Object>> stuDegreeComposition(String departmentId,
			boolean isLeaf) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentId)) {
			return null;
		} else if ("0".equals(departmentId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentId.contains(",")) {
			sql2 = " and (s.dept_id = '" + departmentId + "' or s.major_id = '"
					+ departmentId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = " and s.major_id in (" + departmentId + ") "; // 显示某些专业数据
			} else {
				sql2 = " and s.dept_id in (" + departmentId + ") "; // 显示某些学院数据
			}
		}
		String sql = "select count(*) counts, s.degree_id, ecd.name_ eduname from t_stu s "
				+ " inner join t_code_dept_teach dt on s.dept_id=dt.id "
				+ "left join t_code_degree ecd on s.degree_id = ecd.id"
				+ " where s.stu_state_code <>4 "
				+ sql2
				+ " group by s.degree_id, ecd.name_ order by s.degree_id, ecd.name_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
}
