package com.jhnu.product.four.wall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.four.wall.dao.FourWallDao;
import com.jhnu.product.four.wall.service.FourWallService;

@Service("fourWallService")
public class FourWallServiceImpl implements FourWallService {
	
	@Autowired
	private FourWallDao wallDao;

	@Override
	public ResultBean getWallForLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("wall");
		rb.setObject(wallDao.getWallForLog(id));
		rb.setTrue(true);
		return rb;
	}
	
}
