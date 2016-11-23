package com.jhkj.mosdc.framework.scheduling.service;

import java.util.List;

import com.jhkj.mosdc.framework.scheduling.po.Message;

/**
 * 提取数据接口
 * @author Administrator
 *
 */
public interface ExtractMessageService {
	
	/**
	 * 根据职工ID提取数据
	 * @param zgId
	 * @return 
	 */
	public List<Message> queryMessageByJzg();
	
	public List<Message> queryMessageInit();	
}
