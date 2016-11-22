package com.jhnu.product.four.wall.dao;

import java.util.List;
import java.util.Map;

public interface FourWallDao {
	
	/**
	 * 获取标签墙
	 * @param id 学生ID
	 * @return
	 */
	public List<Map<String,Object>> getWallForLog(String id);

}
