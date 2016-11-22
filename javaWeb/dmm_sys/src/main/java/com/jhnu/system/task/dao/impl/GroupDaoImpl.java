package com.jhnu.system.task.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.task.dao.GroupDao;
import com.jhnu.system.task.entity.Group;
import com.jhnu.system.task.entity.Plan;
import com.jhnu.util.common.StringUtils;
@Repository("taskGroupDao")
public class GroupDaoImpl extends TaskBaseDaoImpl implements GroupDao {

	@Override
	public List<Group> getGroups() {
		String sql="select id,url_ from t_sys_resources where pid=0";
		return getObjs(sql+" order by id", new Group());
	}
	@Override
	public boolean updateOrInsert(Group group,String name) {
		String sql="";
		if(StringUtils.hasLength(group.getId())){
			sql="update t_code set name_='"+name+"' where code_type= 'SCHEDULE_GROUP_CODE' and code_='"+group.getId()+"'";
			 excute(sql); 
		}else{
			 group.setId(getAId());
			 sql="INSERT INTO t_code VALUES ('"+group.getId()+"','"+name+"','XB','SCHEDULE_GROUP_CODE','调度-业务系统代码',1,null)";
			 excute(sql); 
		}
		sql=sqlByBean.getUpdateSql(group);
		return excute(sql);
	}

	@Override
	public boolean del(Group Group) {
		String sql=sqlByBean.getDelSql(Group);
		excute(sql);
		sql="delete from t_code where code_type= 'SCHEDULE_GROUP_CODE' and code_='"+Group.getId()+"'";
		return excute(sql);
	}
	@Override
	public Group getGroupById(String id) {
		String sql="select id,url_ from t_sys_resources where pid=0 and id="+id;
		return (Group) getObjs(sql, new Group()).get(0);
	}
	@Override
	public List<Code> getGroupCodes() {
		StringBuffer sql=new StringBuffer("select t.id code_,t.name_ ,null code_category,null code_type,null codetype_name,t.istrue,t.order_ from t_sys_resources t "
				+"left join t_code co on co.code_type = 'RESOURCE_TYPE_CODE' and co.code_ =  t.resource_type_code where pid=0");
	return getObjs(sql.toString(), new Code());
	}

}
