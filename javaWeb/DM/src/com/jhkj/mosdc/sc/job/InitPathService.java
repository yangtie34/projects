package com.jhkj.mosdc.sc.job;

import java.util.Map;

public interface InitPathService {
	
	/**
	 * 自动补全全息码
	 * Map参数：
	 * key:TableName 所查询的表名（必须有，无默认值）
	 * key:Pid 父节点字段名    默认值：pid
	 * key:Id  主键字段名    默认值：id
	 * key:PidStart 最底节点的父ID值    默认值：-1
	 * key:Path 全息码字段名    默认值：path_
	 * key:PathLength 全息码字段单个长度    默认值：5
	 * 
	 * @param dataMap 
	 */
	public void saveInitPath(Map<String,Object> dataMap);
	
	
}
