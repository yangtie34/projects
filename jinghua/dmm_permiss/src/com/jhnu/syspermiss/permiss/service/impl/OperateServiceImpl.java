package com.jhnu.syspermiss.permiss.service.impl;

import java.util.List;

import com.jhnu.syspermiss.permiss.dao.OperateDao;
import com.jhnu.syspermiss.permiss.dao.impl.OperateDaoImpl;
import com.jhnu.syspermiss.permiss.entity.Operate;
import com.jhnu.syspermiss.permiss.service.OperateService;

public class OperateServiceImpl implements OperateService {
	private OperateServiceImpl() {
		
	}  
    private static OperateServiceImpl OperateServiceImpl=null;
	
	public static OperateServiceImpl getInstance() {
		if (OperateServiceImpl == null){
			synchronized (new String()) {
				if (OperateServiceImpl == null){
					OperateServiceImpl = new OperateServiceImpl();
				}
			}
		}
		return OperateServiceImpl;
	}
    private OperateDao operateDao= OperateDaoImpl.getInstance();
	

	@Override
	public Operate findById(Long id) {
		return operateDao.findById(id);
	}

	@Override
	public List<Operate> findOperateByThis(Operate operate) {
		return operateDao.findOperateByThis(operate);
	}

	@Override
	public List<Operate> findAll() {
		return operateDao.findAll();
	}

	@Override
	public Operate createOperate(Operate operate) {
		return operateDao.createOperate(operate);
	}

	@Override
	public void deleteOperate(Long operateId) {
		 operateDao.deleteOperate(operateId);
		
	}

	@Override
	public void updateOperate(Operate operate) {
		operateDao.updateOperate(operate);
		
	}

	

}
