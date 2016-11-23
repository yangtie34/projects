package com.jhnu.product.common.card.service;

import java.util.List;
import java.util.Map;

import com.jhnu.system.common.TreeCode;

public interface CardService {
	/**
	 * 获取树形结构节点
	 * @param id
	 * @return
	 */
	public TreeCode getTreeCode(String id);
	
	/**
	 * 获取消费统计分组列表
	 * @return
	 */
	public List<String> getCardDealGroup(String groupCode);
	
	/**
	 * 获取分组统计code
	 * @return
	 */
	public List<Map<String,Object>> getGroupCodes();
	/**
	 * 根据学生查询 卡余额
	 * @param stuId
	 * @return
	 */
	public double getCardBlanceById(String stuId);
}
