package com.jhkj.mosdc.sc.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface Kycg_yangtzService {
//科研成果（获奖成果 著作 鉴定成果 专利）分布统计 按成果类型
	String cggk1(String params);
//科研成果（获奖成果 著作 鉴定成果 专利）分布统计 按学院
		String cggk2(String params);
//成果趋势
	String cgqs(String params);
	
//类别下钻
	String lbxz(String params);

//详情列表
	String xqxz(String params);
//趋势下钻
	String qsxz(String params);
	HSSFWorkbook xqxzexcel(String json);
}
