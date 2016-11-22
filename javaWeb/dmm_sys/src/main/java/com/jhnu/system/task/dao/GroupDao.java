package com.jhnu.system.task.dao;

import java.util.List;

import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.task.entity.Group;
import com.jhnu.system.task.entity.Plan;

public interface GroupDao {
	
	public List<Group> getGroups();
	
	public boolean updateOrInsert(Group group,String name);
	
	public boolean del(Group group);
	
	public Group getGroupById(String id);

	public List<Code> getGroupCodes();
}
