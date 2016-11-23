package com.jhnu.product.four.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.four.common.dao.FourDao;
import com.jhnu.product.four.common.entity.FourMethod;
import com.jhnu.product.four.common.entity.FourNot;
import com.jhnu.util.common.DateUtils;


@Repository("fourDao")
public class FourDaoImpl implements FourDao{
	
	@Autowired
	private BaseDao baseDao;

	@Override
	public List<FourMethod> getFourMethods() {
        String sql = "SELECT * FROM T_SYS_FOUR_METHOD";
        List<FourMethod> list = baseDao.getBaseDao().getJdbcTemplate().query(sql, new BeanPropertyRowMapper<FourMethod>(FourMethod.class));
        return list;
	}
	
	@Override
	public List<FourMethod> getFourMethods(String userId) {
        String sql = "select * from t_sys_four_method t where t.id not in( select n.four_method_id from t_sys_four_not n where n.user_id=? )";
        List<FourMethod> list = baseDao.getBaseDao().getJdbcTemplate().query(sql, new Object[]{userId},new BeanPropertyRowMapper<FourMethod>(FourMethod.class));
        return list;
	}

	@Override
	public List<FourNot> getFourNotByThis(FourNot fourNot) {
		// TODO 获取不让初始化的方法
		return null;
	}

	@Override
	public List<Map<String, Object>> getFourIsShared(String id) {
		String sql ="select * from t_sys_four_shared where stu_id = ?";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{id});
	}
	
	@Override
	public void saveFourShared(String stuid) {
		String sql ="insert into t_sys_four_shared values(?,?)";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{stuid,DateUtils.getCurrentTime()});
	}
	
	@Override
	public void delFourShared(String stuid) {
		String sql ="delete from t_sys_four_shared where stu_id=?";
		baseDao.getBaseDao().getJdbcTemplate().update(sql, new Object[]{stuid});
	}
	
	@Override
	public String getSchoolName() {
		String sql ="select name_ from T_CODE_DEPT where PID=-1";
		Map<String,Object> map = baseDao.getBaseDao().getJdbcTemplate().queryForMap(sql);
		String name = map==null ? "":map.get("NAME_").toString();
		return name;
	}
}
