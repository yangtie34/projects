package com.jhkj.mosdc.framework.message.dao;

public interface JxrzEventDao {

	/**
	 * 为班主任统计每天的详细的迟到、早退、缺勤、等详细信息
	 * @throws Exception 
	 */
	public void addMessageForBzrEveryday() throws Exception;
}
