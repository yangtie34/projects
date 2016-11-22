package com.jhnu.system.task.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.jhnu.system.common.code.dao.CodeDao;
import com.jhnu.system.common.code.entity.Code;
import com.jhnu.system.task.dao.GroupDao;
import com.jhnu.system.task.entity.Group;
import com.jhnu.system.task.service.GroupService;
import com.jhnu.util.common.StringUtils;
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
		return groupDao.getGroupCodes();
	}
	@Override
	public boolean updateOrInsert(Group group,String name) {
		return groupDao.updateOrInsert(group,name);
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
