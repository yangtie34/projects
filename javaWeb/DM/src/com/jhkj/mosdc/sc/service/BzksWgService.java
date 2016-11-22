package com.jhkj.mosdc.sc.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 本专科生晚归接口服务
 * @author Administrator
 *
 */
public interface BzksWgService {
	
	public String queryGridContent(String params);
	
	public String queryWgMd(String params);
	
	public HSSFWorkbook getWgMdExport(String params);
	
	public String queryYxglyJszt(String params);
	public String sendMail(String params);
}
