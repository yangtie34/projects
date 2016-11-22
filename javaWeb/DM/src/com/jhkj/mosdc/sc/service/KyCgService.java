package com.jhkj.mosdc.sc.service;
/**
 * 科研成果统计接口
 * @author Administrator
 *
 */
public interface KyCgService {

	public String getZlxx(String params);
	
	public String getZzxx(String params);
	
	public String getLwxx(String params);
	
	public String getXmxx(String params);
	
	public String getHjcgxx(String params);
	
	public String getChartData(String params);
	
	public String queryGridContent(String params);
	
	public String getTimeShaftData(String params);
}
