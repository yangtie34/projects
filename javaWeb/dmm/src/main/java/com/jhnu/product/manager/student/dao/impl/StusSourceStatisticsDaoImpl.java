package com.jhnu.product.manager.student.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.manager.student.dao.IStusSourceStatisticsDao;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.StringUtils;

/**
 * @title 学生生源地统计Dao实现类
 * @description 学生生源地统计
 * @author Administrator
 * @date 2015/10/14 12:20
 */
@Repository("stusSourceStatisticsDao")
public class StusSourceStatisticsDaoImpl implements IStusSourceStatisticsDao {

	// 自动注入BaseDao
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<Map<String, Object>> stusStatisticalInterval(
			String entranceStartTime, String entranceEndTime,
			String departmentMajorId, boolean isLeaf) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentMajorId)) {
			return null;
		} else if ("0".equals(departmentMajorId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentMajorId.contains(",")) {
			sql2 = "and (s.dept_id = '" + departmentMajorId
					+ "' or s.major_id = '" + departmentMajorId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = "and s.major_id in (" + departmentMajorId + ") "; // 显示某些专业数据
			} else {
				sql2 = "and s.dept_id in (" + departmentMajorId + ") "; // 显示某些学院数据
			}
		}

		String sql = "select nvl(s.sex_code, '未维护') as sexcode, nvl(s.anmelden_code, '未维护') as anmeldencode, count(*) as allCounts from t_stu s "
				//+ " left join t_code_dept_teach dt on s.dept_id = dt.id "
				+ " where s.enroll_date between '"
				+ entranceStartTime
				+ "' and '"
				+ entranceEndTime
				+ "' and s.stu_state_code <>4 and s.anmelden_code is not null "
				+ sql2 + " group by s.sex_code, s.anmelden_code";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public int stusNoMaintainedSource(String entranceStartTime,
			String entranceEndTime, String departmentMajorId, boolean isLeaf) {
		String sql2 = "";
		if (!StringUtils.hasText(departmentMajorId)) {
			return 0;
		} else if ("0".equals(departmentMajorId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentMajorId.contains(",")) {
			sql2 = "and (s.dept_id = '" + departmentMajorId
					+ "' or s.major_id = '" + departmentMajorId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = "and s.major_id in (" + departmentMajorId + ") "; // 显示某些专业数据
			} else {
				sql2 = "and s.dept_id in (" + departmentMajorId + ") "; // 显示某些学院数据
			}
		}
		String sql = "select count(*) as noMaintenance from t_stu s "
				+ " left join t_code_dept_teach dt on s.dept_id = dt.id "
				+ " where s.enroll_date between '" + entranceStartTime
				+ "' and  '" + entranceEndTime + "' "
				+ " and s.stu_state_code <>4 " + sql2
				+ " and s.anmelden_code is null " + "group by s.anmelden_code";
		return baseDao.getBaseDao().getJdbcTemplate()
				.queryForObject(sql, Integer.class);
	}

	@Override
	public Page stusNumsDistribution(String entranceStartTime,
			String entranceEndTime, String departmentMajorId, boolean isLeaf,
			String quota, int level, String sexthIdCards, int currentPage,
			int numPerPage) {
		String condition = "";
		String codeAdminiDivName = "";
		String queryCondition = "";
		// gsn来校人数
		if ("gsn".equals(quota)) {
		}
		// csn农村户口人数
		else if ("csn".equals(quota)) {
			condition = " and s.anmelden_code='1' ";
		}
		// tn县镇户口人数
		else if ("tn".equals(quota)) {
			condition = " and s.anmelden_code='2' ";
		}
		// cn城市户口人数
		else if ("cn".equals(quota)) {
			condition = " and s.anmelden_code='3' ";
		}
		// sbn男生人数
		else if ("sbn".equals(quota)) {
			condition = " and s.sex_code='1' ";
		}
		// sgn女生人数
		else if ("sgn".equals(quota)) {
			condition = " and s.sex_code='2' ";
		}

		// level = 0,来自全国各个省级或自治区和直辖市的生源地查询
		if (level == 0) {
			codeAdminiDivName = "ccc.name_, ";
			queryCondition = "";
		}
		// level = 1,某个省中各个市的生源地查询
		else if (level == 1) {
			codeAdminiDivName = "cc.name_, ";
			queryCondition = "and ccc.id= '" + sexthIdCards + "'";
		}
		// level = 2,某个市中各个区和县的生源地查询
		else if (level == 2) {
			codeAdminiDivName = "c.name_, ";
			queryCondition = "and cc.id= '" + sexthIdCards + "' ";
		}
		// 数据权限
		String sql2 = "";
		if (!StringUtils.hasText(departmentMajorId)) {
			return null;
		} else if ("0".equals(departmentMajorId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentMajorId.contains(",")) {
			sql2 = "and (s.dept_id = '" + departmentMajorId
					+ "' or s.major_id = '" + departmentMajorId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = "and s.major_id in (" + departmentMajorId + ") "; // 显示某些专业数据
			} else {
				sql2 = "and s.dept_id in (" + departmentMajorId + ") "; // 显示某些学院数据
			}
		}
		String sql = "select  nvl(name_,'未维护') as citiesname, (sum(decode(sc, '1', rs, 0))+sum(decode(sc, '2', rs, 0))) as allcounts, "
				+ " sum(decode(sc, '1', rs, 0)) as schoolboy, "
				+ " sum(decode(sc, '2', rs, 0)) as schoolgirl, "
				+ " sum(decode(ac, '1', rs, 0)) as countrysides, "
				+ " sum(decode(ac, '2', rs, 0)) as towns, "
				+ " sum(decode(ac, '3', rs, 0)) as cities from("
				+ " select "
				+ codeAdminiDivName
				+ " s.sex_code sc, s.anmelden_code ac, count(*) rs from t_stu s "
				+ " left join t_code_admini_div c on c.id = s.stu_origin_id "
				+ " left join t_code_admini_div cc on c.pid = cc.id "
				+ " left join t_code_admini_div ccc on cc.pid = ccc.id "
				+ " left join t_code_dept_teach dt on s.dept_id = dt.id "
				+ " where s.enroll_date between '"
				+ entranceStartTime
				+ "' and '"
				+ entranceEndTime
				+ "' "
				+ " and s.stu_state_code <> 4 "
				+ sql2
				+ condition
				+ queryCondition
				+ " group by "
				+ codeAdminiDivName
				+ " s.sex_code, s.anmelden_code) group by name_ order by allcounts desc ";
		Page page = new Page(sql, currentPage, numPerPage, baseDao.getBaseDao()
				.getJdbcTemplate(), null);
		return page;
	}

	@Override
	public List<Map<String, Object>> stusNumsMap(String departmentMajorId,
			boolean isLeaf, String entranceStartTime, String entranceEndTime,
			int level, String sexthIdCards, String quota) {
		String condition = "";
		String codeAdminiDivName = "";
		String queryCondition = "";
		String codeAdminiDivId = "";
		// gsn来校人数
		if ("gsn".equals(quota)) {
			condition = " ";
		}
		// csn农村户口人数
		else if ("csn".equals(quota)) {
			condition = " and s.anmelden_code='1' ";
		}
		// tn县镇户口人数
		else if ("tn".equals(quota)) {
			condition = " and s.anmelden_code='2' ";
		}
		// cn城市户口人数
		else if ("cn".equals(quota)) {
			condition = " and s.anmelden_code='3' ";
		}
		// sbn男生人数
		else if ("sbn".equals(quota)) {
			condition = " and s.sex_code='1' ";
		}
		// sgn女生人数
		else if ("sgn".equals(quota)) {
			condition = " and s.sex_code='2' ";
		}

		// level = 0,来自全国各个省级或自治区和直辖市的生源地查询
		if (level == 0) {
			codeAdminiDivName = "ccc.name_ ";
			codeAdminiDivId = "ccc.code_ ";
			queryCondition = "";
		}
		// level = 1,某个省中各个市的生源地查询
		else if (level == 1) {
			codeAdminiDivName = "cc.name_ ";
			codeAdminiDivId = "cc.code_ ";
			queryCondition = "and ccc.name_= '" + sexthIdCards + "' ";
		}
		// level = 2,某个市中各个区和县的生源地查询
		else if (level == 2) {
			codeAdminiDivName = "c.name_ ";
			codeAdminiDivId = "c.code_ ";
			queryCondition = "and cc.name_='" + sexthIdCards + "'";
		}
		// 数据权限
		String sql2 = "";
		if (!StringUtils.hasText(departmentMajorId)) {
			return null;
		} else if ("0".equals(departmentMajorId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentMajorId.contains(",")) {
			sql2 = "and (s.dept_id = '" + departmentMajorId
					+ "' or s.major_id = '" + departmentMajorId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = "and s.major_id in (" + departmentMajorId + ") "; // 显示某些专业数据
			} else {
				sql2 = "and s.dept_id in (" + departmentMajorId + ") "; // 显示某些学院数据
			}
		}
		String sql = "select " + codeAdminiDivName
				+ " as name , count(*) as value, " + codeAdminiDivId
				+ " as sourceId from t_stu s "
				+ " left join t_code_admini_div c on c.id = s.stu_origin_id "
				+ " left join t_code_admini_div cc on c.pid = cc.id "
				+ " left join t_code_admini_div ccc on cc.pid = ccc.id "
				+ " left join t_code_dept_teach dt on s.dept_id = dt.id "
				+ " where s.enroll_date between '" + entranceStartTime
				+ "' and '" + entranceEndTime + "' and s.stu_state_code <> 4 "
				+ sql2 + condition + queryCondition + "group by "
				+ codeAdminiDivName + ", " + codeAdminiDivId;

		return baseDao.getBaseDao().queryListMapInLowerKeyBySql(sql);
	}

	@Override
	public Page stusSchlloOfGraduation(String entranceStartTime,
			String entranceEndTime, int level, String departmentMajorId,
			boolean isLeaf, String quota, String sexthIdCards, int currentPage,
			int numPerPage) {
		String condition = "";
		String queryCondition = "";
		// gsn来校人数
		if ("gsn".equals(quota)) {
			condition = " ";
		}
		// csn农村户口人数
		else if ("csn".equals(quota)) {
			condition = " and s.anmelden_code='1' ";
		}
		// tn县镇户口人数
		else if ("tn".equals(quota)) {
			condition = " and s.anmelden_code='2' ";
		}
		// cn城市户口人数
		else if ("cn".equals(quota)) {
			condition = " and s.anmelden_code='3' ";
		}
		// sbn男生人数
		else if ("sbn".equals(quota)) {
			condition = " and s.sex_code='1' ";
		}
		// sgn女生人数
		else if ("sgn".equals(quota)) {
			condition = " and s.sex_code='2' ";
		}

		// level = 0,来自全国各个省级或自治区和直辖市的生源地查询
		if (level == 0) {
			queryCondition = "";
		}
		// level = 1,某个省中各个市的生源地查询
		else if (level == 1) {
			queryCondition = "and ccc.id= ' " + sexthIdCards + "' ";
		}
		// level = 2,某个市中各个区和县的生源地查询
		else if (level == 2) {
			queryCondition = "and cc.id=' " + sexthIdCards + "' ";
		}

		// 数据权限
		String sql2 = "";
		if (!StringUtils.hasText(departmentMajorId)) {
			return null;
		} else if ("0".equals(departmentMajorId)) {
			sql2 = "";// 显示全校数据
		} else if (!departmentMajorId.contains(",")) {
			sql2 = "and (s.dept_id = '" + departmentMajorId
					+ "' or s.major_id = '" + departmentMajorId + "' )";// 显示某个院系或某个专业数据
		} else {
			if (isLeaf) {
				sql2 = "and s.major_id in (" + departmentMajorId + ") "; // 显示某些专业数据
			} else {
				sql2 = "and s.dept_id in (" + departmentMajorId + ") "; // 显示某些学院数据
			}
		}

		String sql = "select nvl(s.schooltag,'未维护'), count(*) as counts from t_stu s "
				+ " left join t_code_admini_div c on c.id = s.stu_origin_id "
				+ " left join t_code_admini_div cc on c.pid = cc.id "
				+ " left join t_code_admini_div ccc on cc.pid = ccc.id "
				+ " left join t_code_dept_teach dt on s.dept_id = dt.id "
				+ " where s.enroll_date between '"
				+ entranceStartTime
				+ "' and '"
				+ entranceEndTime
				+ "' "
				+ " and s.stu_state_code <> 4 "
				+ sql2
				+ condition
				+ queryCondition + " group by s.schooltag order by counts desc";

		return new Page(sql, currentPage, numPerPage, baseDao.getBaseDao()
				.getJdbcTemplate(), null);
	}
}
