package com.jhnu.system.task.service;

import java.util.List;

import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.task.entity.Group;

public interface GroupService {
	
	public List<Group> getGroups();
	
	public List<Code> getGroupCodes();
	
	public boolean updateOrInsert(Group group,String name);
	
	public boolean del(Group group);
	
	public Group getGroupById(String id);
}
