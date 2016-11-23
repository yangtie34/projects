package com.jhnu.product.wechat.parent.warn.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.enums.WarnLevelEnum;
import com.jhnu.enums.WarnTypeEnum;
import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.parent.warn.dao.CheckWarnDao;
import com.jhnu.product.wechat.parent.warn.entity.WechatWarn;

@Repository("checkWarnDao")
public class CheckWarnDaoImpl implements CheckWarnDao{

	@Autowired
	private BaseDao baseDao;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<WechatWarn> getCheckWarn(String last_time,String exe_time) {
		//TODO text要修改成模板，不用硬编码。
		String text=" '你的孩子在'|| sc.date_ || c.name_ || sc.period || co.name_ ";
		
		String sql="select sc.stu_id,'"+WarnTypeEnum.KQYJ.getCode()+"' warn_type_code,'"+WarnLevelEnum.JG.getCode()+"' warn_level_code,co.name_ warn_why, "+
				 text+" warn_text,sc.add_time warn_date , "+
				 "? exe_time from T_STU_CHECK  sc inner join  T_COURSE c on sc.course_id=c.id  "+
				 "inner join t_code co on sc.check_code=co.code_ and co.code_type='CHECK_CODE' "+
				 "where sc.check_code <> 1 and sc.add_time between ? and ? "; 
		return baseDao.getBaseDao().getJdbcTemplate().
				query(sql,new Object[]{exe_time,last_time,exe_time}, 
						(RowMapper) new BeanPropertyRowMapper(WechatWarn.class));
	}
	
	
}