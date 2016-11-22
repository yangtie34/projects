package com.jhkj.mosdc.sc.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 预警-不在校学生名单service
 * @author Administrator
 *
 */
public interface BzxService {
	
	public String queryGridContent(String params);
	
	public String queryYxgly(String params);
	public String queryYxglyJszt(String params);
	
	public String sendMail(String params);
	
	public String getBzxMd(String params);
	
	public HSSFWorkbook getBzxMdExport(String params);
}
