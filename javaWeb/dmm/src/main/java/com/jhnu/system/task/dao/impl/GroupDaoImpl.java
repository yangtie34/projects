package com.jhnu.system.task.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jhnu.system.task.dao.GroupDao;
import com.jhnu.system.task.entity.Group;
import com.jhnu.system.task.entity.Plan;
@Repository("taskGroupDao")
public class GroupDaoImpl extends TaskBaseDaoImpl implements GroupDao {

	@Override
	public List<Group> getGroups() {
		String sql=sqlByBean.getQuerySql(new Group());
		return getObjs(sql+" order by id", new Group());
	}
	@Override
	public boolean updateOrInsert(Group Group) {
		String sql=sqlByBean.getUpdateSql(Group);
		return excute(sql);
	}

	@Override
	public boolean del(Group Group) {
		String sql=sqlByBean.getDelSql(Group);
		return excute(sql);
	}
	@Override
	public Group getGroupById(String id) {
		Group plan=new Group();
		plan.setId(id);
		String sql=sqlByBean.getQuerySqlById(plan);
		return (Group) getObjs(sql, new Group()).get(0);
	}

}
