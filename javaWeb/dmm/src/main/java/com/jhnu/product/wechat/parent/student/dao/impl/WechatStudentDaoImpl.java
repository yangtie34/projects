package com.jhnu.product.wechat.parent.student.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.student.dao.WechatStudentDao;


@Repository("studentDao")
public class WechatStudentDaoImpl implements WechatStudentDao{

	@Autowired
	private BaseDao baseDao;
	@Override
	public List<Map<String, Object>> getStudentInfo(String id) {
		String sql = "select stu.no_ stu_id,stu.name_ stu_name,classes.name_ class_name,teach.name_ major_name,dept.name_ dept_name,tea.name_ class_tea_name,comm.tel class_tea_tel,t.name_ dept_tea_name,dept_tea_comm.tel dept_tea_tel,ins.school_year,ins.term_code from t_stu stu "
				+ " left join t_classes classes on stu.class_id = classes.no_"
				+ " left join t_code_dept_teach teach on teach.id = stu.major_id"
				+ " left join t_code_dept dept on dept.id = stu.dept_id"
				+ " left join t_dept_tea dept_tea on dept_tea.dept_id = stu.dept_id"
				+ " left join t_tea_comm dept_tea_comm on dept_tea_comm.id = dept_tea.tea_id"
				+ " left join t_tea t on t.tea_no = dept_tea_comm.tea_id"
				+ " left join t_classes_instructor ins on ins.class_id = stu.class_id"
				+ " left join t_tea tea on tea.id = ins.tea_id"
				+ " left join t_tea_comm comm on comm.tea_id = tea.tea_no where stu.no_ = ? order by school_year desc,term_code desc";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public List<Map<String, Object>> getDeptInfo() {
		String sql = "select position.name_,t.name_ tea_name, c.tel  from t_position position "
				+ " left join t_tea t on t.tea_no = position.tea_id"
				+ " left join t_tea_comm c on c.tea_id = position.tea_id";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> getDormInfo(String id) {
		String sql = "select ceng.berth_name, ceng.dorm_name, ceng.ceng_name, dorm.name_ lou_name from "
				+ " (select fj.stu_id, fj.berth_name, fj.dorm_name, dorm.name_ ceng_name, dorm.pid lou_pid from "
				+ " (select berth_stu.stu_id stu_id,berth.berth_name berth_name, dorm.name_ dorm_name, dorm.pid dorm_pid from t_dorm dorm "
				+ " inner join t_dorm_berth berth on berth.dorm_id = dorm.id "
				+ " inner join t_dorm_berth_stu berth_stu on berth_stu.berth_id = berth.id and berth_stu.stu_id= ?"
				+ " ) fj inner join t_dorm dorm on dorm.id = fj.dorm_pid) ceng"
				+ " inner join t_dorm dorm on dorm.id = ceng.lou_pid";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql, new Object[]{id});
	}

	@Override
	public List<Map<String,Object>> getRoommate(String id) {
		String sql = "select stu.name_ roommate from t_stu stu,t_dorm_berth_stu berth_stu, t_dorm_berth berth where stu.no_ = berth_stu.stu_id and berth_stu.berth_id = berth.id and dorm_id = "
				+ "(select dorm_id from t_dorm_berth where id = (select berth_id from t_dorm_berth_stu where stu_id =?"
				+" )) and stu_id != ?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{id,id});
	}
	
	@Override
	public List<Map<String, Object>> getAward(String id) {
		String sql = "select award.stu_id, award.batch, co.name_ award_name, money from t_stu_award award "
				+ "inner join t_code co on co.code_type = 'AWARD_CODE' and award.award_code = co.code_ where stu_id = ?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{id});
	}


	@Override
	public List<Map<String, Object>> getSubsidy(String id) {
		String sql = "select subsidy.stu_id, subsidy.batch, co.name_ subsidy_name, money from t_stu_subsidy subsidy "
				+ "inner join t_code co on co.code_type = 'SUBSIDY_CODE' and subsidy.subsidy_code = co.code_ where stu_id = ?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{id});
	}

	

}
