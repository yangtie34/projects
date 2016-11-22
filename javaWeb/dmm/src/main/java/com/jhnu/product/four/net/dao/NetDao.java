package com.jhnu.product.four.net.dao;

import java.util.List;
import java.util.Map;

public interface NetDao {
	public List<Map<String, Object>> getMax();

	public List<Map<String, Object>> getSum();

	public List<Map<String, Object>> getJcsxsj();

	public void saveNetMax(List<Map<String, Object>> max);

	public void saveNetSum(List<Map<String, Object>> sum);

	public void saveNetJcsxsj(List<Map<String, Object>> jcsxsj);

	public List<Map<String, Object>> getNetMaxTime(String Id);

	public List<Map<String, Object>> getNetJcOnlineTime(String Id);

	public List<Map<String, Object>> getNetSumTime(String Id);

	public List<Map<String, Object>> getNetBq(String Id);
}
