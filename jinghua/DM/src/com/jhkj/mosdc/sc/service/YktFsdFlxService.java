package com.jhkj.mosdc.sc.service;
/**
 * 分时段分类型消费统计
 * @author Administrator
 *
 */
public interface YktFsdFlxService {
	public String getTextData(String params);
	public String getChartData1(String params);
	
	public String getChartData2(String params);
	
	
	public String getTableData(String params);
	public String getTableData1(String params);
}
