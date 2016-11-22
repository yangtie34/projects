package com.jhkj.mosdc.sc.job.dao;

import java.util.List;
import java.util.Map;

import com.jhkj.mosdc.framework.dao.BaseDao;

public interface UpdateSydsxDao extends BaseDao {
	/**
	 * 批量更新学生的生源地属性信息
	 * @param xslb
	 */
	public void batchUpdateSydsx(List<Map<String,String>> xslb);
}
