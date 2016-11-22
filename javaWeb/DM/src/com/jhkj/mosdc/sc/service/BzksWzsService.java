package com.jhkj.mosdc.sc.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 本专科生未住宿服务接口
 * @author Administrator
 *
 */
public interface BzksWzsService {
	
public String queryGridContent(String params);
	
	public String queryWzsMd(String params);
	
	public HSSFWorkbook getWzsMdExport(String params);
	
	public String queryYxglyJszt(String params);
	public String sendMail(String params);
	
	public String getWgWzsColumn(String params);
}
