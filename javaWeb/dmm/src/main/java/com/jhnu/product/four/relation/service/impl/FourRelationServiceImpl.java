package com.jhnu.product.four.relation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.four.relation.dao.FourRelationDao;
import com.jhnu.product.four.relation.service.FourRelationService;

@Service("fourRelationService")
public class FourRelationServiceImpl implements FourRelationService{
	@Autowired
	private FourRelationDao fourRelationDao;

	@Override
	public ResultBean getRoommateLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("Roommate");
		rb.getData().put("roommate", fourRelationDao.getRoommateLog(id));
		return rb;
	}

	@Override
	public ResultBean getTutorLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("Tutor");
		rb.getData().put("rutor", fourRelationDao.getTutorLog(id));
		return rb;
	}

	@Override
	@Transactional
	public void saveRoommateJob() {
		fourRelationDao.saveRoommateLog(fourRelationDao.getRoommate());
	}

	@Override
	@Transactional
	public void saveTutorJob() {
		fourRelationDao.saveTutorLog(fourRelationDao.getTutor());
	}
	
	

}
