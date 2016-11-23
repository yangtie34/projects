package com.jhnu.system.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.system.common.code.dao.CodeDao;
import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.task.dao.GroupDao;
import com.jhnu.system.task.entity.Group;
import com.jhnu.system.task.service.GroupService;
@Service("taskGroupService")
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupDao groupDao;
	@Autowired
	private CodeDao codeDao;
	@Override
	public List<Group> getGroups() {
		return groupDao.getGroups();
	}
	@Override
	public List<Code> getGroupCodes() {
		Code code=new Code();
		code.setCode_type("SCHEDULE_GROUP_CODE");
		return codeDao.getCode(code);
	}
	@Override
	public boolean updateOrInsert(Group group) {
		return groupDao.updateOrInsert(group);
	}
	@Override
	public boolean del(Group group) {
		return groupDao.del(group);
	}
	@Override
	public Group getGroupById(String id) {
		return groupDao.getGroupById(id);
	}
}
