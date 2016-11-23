package cn.gilight.product.net.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.net.dao.NetStuWarnDao;
import cn.gilight.product.net.service.NetStuWarnService;

/**
 * 学生上网预警
 *
 */
@Service("netStuWarnService")
public class NetStuWarnServiceImpl implements NetStuWarnService{
	@Autowired
	private NetStuWarnDao netStuWarnDao;

	@Override
	public Page getNetWarnStus(int currentPage,int numPerPage,int totalRow,String startDate, String endDate,
			Map<String, String> deptTeach, String type, String value) {
		
		return netStuWarnDao.getNetWarnStus(currentPage, numPerPage, totalRow, startDate, endDate, deptTeach, type, value);
	}

	@Override
	public List<Map<String, Object>> getNetStuType(String startDate,
			String endDate, Map<String, String> deptTeach, String type,
			String value, String codeType) {
		
		return netStuWarnDao.getNetStuType(startDate, endDate, deptTeach, type, value, codeType);
	}

	@Override
	public Page getNetWarnTypeStus(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate, String endDate,
			Map<String, String> deptTeach, String type, String value,
			String codeType, String codeValue) {
		
		return netStuWarnDao.getNetWarnTypeStus(currentPage, numPerPage, totalRow,sort,isAsc, startDate, endDate, deptTeach, type, value, codeType, codeValue);
	}
	
}
