package com.jhnu.system.permiss.service;

import java.util.List;

import com.jhnu.framework.exception.handle.AddException;
import com.jhnu.framework.exception.param.ParamException;
import com.jhnu.system.permiss.entity.Operate;


public interface OperateService {
	/**
	 * 根据ID查询操作
	 * @return
	 */
	public Operate findById(Long id);
	
	/**
	 * 创建操作
	 * @param operate
	 * @return
	 */
	public Operate createOperate(Operate operate) throws AddException,ParamException;
	
	/**
	 * 删除操作
	 * @param operateId
	 */
	public void deleteOperate(Long operateId);
    
	/**
	 * 修改操作
	 * @param operate
	 */
	public void updateOperate(Operate operate) throws AddException,ParamException;
	
	/**
	 * 查询操作
	 * @param operate
	 * @return
	 */
	public List<Operate> findOperateByThis(Operate operate);
	
	/**
	 * 查询所有操作
	 * @return
	 */
	public List<Operate> findAll();


}
