package com.jhnu.product.wechat.parent.student.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.wechat.parent.student.dao.WechatStudentDao;
import com.jhnu.product.wechat.parent.student.service.WechatStudentService;

@Service("studentService")
public class WechatStudentServiceImpl implements WechatStudentService{
	@Autowired
	private WechatStudentDao studentDao;
	
	@Override
	public List<Map<String, Object>> getStudentInfo(String id) {
		return studentDao.getStudentInfo(id);
	}

	@Override
	public List<Map<String, Object>> getDeptInfo() {
		return studentDao.getDeptInfo();
	}

	@Override
	public List<Map<String, Object>> getDormInfo(String id) {
		return studentDao.getDormInfo(id);
	}

	@Override
	public List<Map<String, Object>> getRoommate(String id) {
		return studentDao.getRoommate(id);
	}

	@Override
	public List<Map<String, Object>> getAward(String id) {
		return studentDao.getAward(id);
	}

	@Override
	public List<Map<String, Object>> getSubsidy(String id) {
		return studentDao.getSubsidy(id);
	}

}
