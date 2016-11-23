package com.jhnu.product.wechat.teacher.instructor.classwarn.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.teacher.instructor.classwarn.dao.ClassWarnDao;

@Repository("classWarnDao")
public class ClassWarnDaoImpl implements ClassWarnDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String, Object>> getClassLateDormByTeacherId(
			String teacherId, String action_date) {
		String sql="select t.no_ class_id,t.name_ class_name,t.zy zy_name,nvl(ls.rs,0) rs,ls.exe_time  from "+
					"(select c.no_, c.name_,cd.name_ zy from T_CLASSES_INSTRUCTOR ci "+
					 "inner join T_STU s on ci.CLASS_ID = s.class_id "+
					 " inner join T_CODE_DEPT_TEACH cd on s.MAJOR_ID = cd.id "+
					 "inner join T_CLASSES c on c.no_ = ci.class_id "+
					 "where ci.tea_id = ? and (s.LENGTH_SCHOOLING + c.grade) > to_number(to_char(sysdate, 'yyyy')) "+
					 "group by c.no_, c.name_ ,cd.name_ ) t left join  "+
					 "(select class_id,exe_time,count(*) rs from  t_latedorm_stu where action_date=? group by class_id,exe_time )  "+
					 "ls on t.no_= ls.class_id  order by t.no_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{teacherId,action_date});
	}
	
}
