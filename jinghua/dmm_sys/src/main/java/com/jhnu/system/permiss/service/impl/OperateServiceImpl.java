package com.jhnu.system.permiss.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.handle.HasOneException;
import com.jhnu.framework.exception.param.EmptyParamException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.permiss.dao.OperateDao;
import com.jhnu.system.permiss.entity.Operate;
import com.jhnu.system.permiss.service.OperateService;
import com.jhnu.system.permiss.service.PermssionService;

@Service("operateService")
public class OperateServiceImpl implements OperateService {

	@Autowired
    private OperateDao operateDao;
	@Autowired
	private PermssionService permssionService;
	
	private static final Logger logger = Logger.getLogger(OperateServiceImpl.class);

	@Override
	public Operate findById(Long id) {
		return operateDao.findById(id);
	}

	@Override
	public Operate createOperate(Operate operate) throws AddException,ParamException{
		Operate op = null;
		if(operate != null){
			if(StringUtils.isEmpty(operate.getName_())){
				throw new EmptyParamException("操作名称不能为空");
			}else{
				if(com.jhnu.util.common.StringUtils.hasOtherText(operate.getName_())){
					throw new ParamException("操作名称不能包含特殊字符");
				}
				Operate o = new Operate();
				o.setName_(operate.getName_());
				List<Operate> lo = operateDao.findOperateByThis(o);
				if(lo != null && lo.size()>0){
					throw new HasOneException("操作名称已存在");
				}
			}
			if(StringUtils.isEmpty(operate.getDescription())){
				throw new EmptyParamException("操作描述不能为空");
			}
			logger.info("====开始创建操作====");
			op = operateDao.createOperate(operate);
			logger.info("====创建操作结束====");
		}
		return op;
	}

	@Override
	public void deleteOperate(Long operateId) {
		logger.info("====删除操作开始====");
		operateDao.deleteOperate(operateId);
		logger.info("====删除角色结束====");
	}

	@Override
	public void updateOperate(Operate operate) throws AddException,ParamException{
		if(operate != null){
			if(StringUtils.isEmpty(operate.getId())){
				throw new EmptyParamException("要修改的操作ID不能为空");
			}else{
				Operate op = operateDao.findById(operate.getId());
				if(op == null){
					throw new ParamException("要修改的操作ID不存在");
				}
			}
			if(StringUtils.isEmpty(operate.getName_())){
				throw new EmptyParamException("修改的操作名称不能为空");
			}else{
				Operate o = new Operate();
				o.setName_(operate.getName_());
				List<Operate> lo = operateDao.findOperateByThis(o);
				if(lo != null && lo.size()>0){
					for(Operate op : lo){
						if(!operate.getId().equals(op.getId())){
							throw new HasOneException("修改的操作名称已存在");
						}
					}
				}
			}
			if(StringUtils.isEmpty(operate.getDescription())){
				throw new EmptyParamException("修改的操作描述不能为空");
			}
			logger.info("====开始修改操作====");
			operateDao.updateOperate(operate);
			logger.info("====修改操作结束====");
			//修改shiro通配符
			permssionService.updateRolePermssion(operate);
			permssionService.updateUserPermssion(operate);
		}
		
	}

	@Override
	public List<Operate> findOperateByThis(Operate operate) {
		return operateDao.findOperateByThis(operate);
	}

	@Override
	public List<Operate> findAll() {
		return operateDao.findAll();
	}

	

}
