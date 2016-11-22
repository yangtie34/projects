package com.jhkj.mosdc.sc.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface KyTopService {
	
	public void saveInitData();
	
	public void saveInitDataByYear();
	
	public String getTopNum(String params);
	
	public String getTopUpNum(String params);
	
	public String getTopGoodNum(String params);
	
	public String getPeopleTypeByPie(String params);
	
	public String getDataList(String params);
	
	public HSSFWorkbook getDataMdExport(String params) ;
	
	public HSSFWorkbook getDataGMdExport(String params);
	
	public HSSFWorkbook getPeopleExport(String params);
	
	public String getUpNumYear(String params);
	
}
