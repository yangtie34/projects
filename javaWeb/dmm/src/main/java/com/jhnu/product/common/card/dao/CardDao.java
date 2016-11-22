package com.jhnu.product.common.card.dao;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.TreeCode;

public interface CardDao {
	/**
	 * 获取树形结构节点
	 * @param id
	 * @return
	 */
	public TreeCode getTreeCode(String id);
	/**
	 * 依据统计分组code获取一卡通消费id列表
	 * @param groupCode
	 * @return
	 */
	public List<Map<String, Object>> getCardDealGroup(String groupCode);
	
	/**
	 * 获取分组统计code
	 * @return
	 */
	public List<Map<String,Object>> getGroupCodes();
	/**
	 * 根据学生id获取学生卡余额信息
	 * @param stuId
	 * @return
	 */
	public List<Map<String,Object>> getCardBlanceByStudentId(String stuId);
}
