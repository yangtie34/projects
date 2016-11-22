package com.jhnu.product.manager.school.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.school.dao.DeptDao;
import com.jhnu.product.manager.school.service.DeptService;

@Service("deptService")
public class DeptServiceImpl implements DeptService{

	@Autowired
	private DeptDao deptDao;
	
	@Override
	public List<Map<String, Object>> getDeptTeach(String ids) {
		return deptDao.getDeptTeach(ids);
	}

	@Override
	public List<Map<String, Object>> getMajorTeach(String dept_id) {
		return deptDao.getMajorTeach(dept_id);
	}
	@Override
	public List<Map<String, Object>> getDept(String ids) {
		return deptDao.getDept(ids);
	}

	@Override
	public List<Map<String, Object>> getDeptLeaf(String dept_id) {
		return deptDao.getDeptLeaf(dept_id);
	}

	@Override
	public List<Map<String,Object>> getDepts(String ids) {
		List<Map<String,Object>> depts = deptDao.getDepts(ids);
		return depts;
	}

	@Override
	public List<Map<String, Object>> getTeaDepts(String ids) {
		return deptDao.getTeaDepts(ids);
	}

}
