package cn.gilight.personal.student.school.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.personal.student.school.dao.MySchoolDao;

@Repository("mySchoolDao")
public class MySchoolDaoImpl implements MySchoolDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public Map<String, Object> getTeachingBuildingCounts() {
		String sql = "select count(*) teachingbuilding from t_classroom_build t where t.level_type = 'LY'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getDormitoryBuildingCounts() {
		String sql = "select count(*) dormitorybuilding from t_dorm t where t.level_type = 'LY'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getRestaurantCounts() {
		String sql = "select count(*) restaurant from t_card_dept t where t.dept_type = 'RES' or t.dept_type = 'MUSLIM_RES'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getShopCounts() {
		String sql = "select count(*) shop from t_card_dept t where t.dept_type = 'SHOP'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getBookCounts() {
		String sql = "select count(*) book from t_book t ";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getTeacherCounts() {
		String sql = "select count(*) teacher from t_tea t where t.tea_status_code = '11'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Map<String, Object> getStudntCounts() {
		String year = DateUtils.getNowYear();
		String month = DateUtils.getNowMonth();
		if(Integer.parseInt(month)<7){
			year = String.valueOf(Integer.parseInt(year) -1);
		}
		String sql = "select count(*) student from t_stu t where t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
