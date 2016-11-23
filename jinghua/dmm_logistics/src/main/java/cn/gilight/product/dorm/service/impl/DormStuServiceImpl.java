package cn.gilight.product.dorm.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.product.dorm.dao.DormStuDao;
import cn.gilight.product.dorm.service.DormStuService;

@Service("dormStuService")
public class DormStuServiceImpl  implements DormStuService{
	
	@Autowired
	private DormStuDao dormStuDao;

	@Override
	public Map<String, Object> getDormStuInfoByQuery(
			List<Map<String, Object>> query) {
		return dormStuDao.getDormStuInfoByQuery(query);
	}

	@Override
	public List<Map<String, Object>> getDormStuByQueryAndDrom(
			List<Map<String, Object>> query, Map<String, String> dorm) {
		return dormStuDao.getDormStuByQueryAndDrom(query, dorm);
	}

	@Override
	public List<Map<String,Object>> getQueryCode(
			List<Map<String, Object>> query) {
		return dormStuDao.getQueryCode(query);
	}

	@Override
	public List<Map<String, Object>> getDormStuByQueryAndEndId(
			List<Map<String, Object>> query, String endId) {
		return dormStuDao.getDormStuByQueryAndEndId(query, endId);
	}
	
}
