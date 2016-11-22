package com.jhnu.product.wechat.teacher.instructor.classbase.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.product.wechat.teacher.instructor.classbase.dao.ClassBaseDao;

@Repository("classBaseDao")
public class ClassBaseDaoimpl implements ClassBaseDao{

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public List<Map<String, Object>> getClassBaseByTeacherId(String teacherId) {
		String sql="select t.class_id,t.class_name,t.zy_name,t.nan_,t.nv_,t.wwh_,s.name_ bz,sc.tel bztel from  "+
					"(select no_ class_id ,name_ class_name,zy zy_name, "+
					"sum(decode(sex_code, '01', rs,0)) as nan_ ,sum(decode(sex_code, '02', rs,0)) as nv_,sum(decode(sex_code, null, rs,0)) as wwh_ from  "+
					"(select  c.no_ , c.name_, cd.name_ zy,s.sex_code,count(*) rs from T_CLASSES_INSTRUCTOR ci inner join T_STU s on ci.CLASS_ID=s.class_id  "+
					"        inner join T_CODE_DEPT_TEACH cd on s.MAJOR_ID = cd.id inner join T_CLASSES c on c.no_= ci.class_id  "+
					"        where ci.tea_id=? and (s.LENGTH_SCHOOLING+c.grade)>to_number(to_char(sysdate,'yyyy')) group by c.no_ , c.name_, cd.name_,s.sex_code  ) "+
					"        group by no_ ,name_,zy ) t "+
					" left join T_STU_CLASS_DUTIES scd on (t.class_id=scd.class_id and scd.class_duties_code='01') "+
					" left join t_stu s on scd.stu_id=s.no_ "+
					" left join T_STU_COMM sc on s.no_=sc.stu_id order by class_id ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{teacherId});
	}

	@Override
	public List<Map<String, Object>> getClassStuByClassId(String classId,boolean isDuties) {
		String sql="select dorm.name_ || ceng.ceng_name || NVL2(ceng.dorm_name,ceng.dorm_name||'房','' )  || NVL2(ceng.berth_name,ceng.berth_name || '床','' ) address,s.name_,s.no_,s.sex_code,sc.tel,sc.home_tel,s.class_id,c.name_ duties from "
				+ "t_stu s left join (select fj.stu_id, fj.berth_name, fj.dorm_name, dorm.name_ ceng_name, dorm.pid lou_pid from "
				+ " (select berth_stu.stu_id stu_id,berth.berth_name berth_name, dorm.name_ dorm_name, dorm.pid dorm_pid from t_dorm dorm "
				+ " inner join t_dorm_berth berth on berth.dorm_id = dorm.id "
				+ " inner join t_dorm_berth_stu berth_stu on berth_stu.berth_id = berth.id "
				+ " ) fj inner join t_dorm dorm on dorm.id = fj.dorm_pid) ceng on s.no_=ceng.stu_id "
				+ " left join t_dorm dorm on dorm.id = ceng.lou_pid"
				+" left join T_STU_COMM sc on s.no_=sc.stu_id "
				+ "left join T_STU_CLASS_DUTIES scd on s.no_=scd.stu_id left join t_code c on (scd.class_duties_code=c.code_ and c.CODE_TYPE='CLASS_DUTIES') "
				+ "where s.class_id= ? "+ (isDuties?" and c.name_ is not null ":"") +" order by s.no_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql,new Object[]{classId});
	}
	
}
