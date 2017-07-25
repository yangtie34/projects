package cn.gilight.product.net.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.net.dao.NetTeaTopDao;
import cn.gilight.product.net.service.NetTeaTopService;

@Service("netTeaTopService")
public class NetTeaTopServiceImpl implements NetTeaTopService {
	
	@Autowired
	private NetTeaTopDao netTeaTopDao;

	@Override
	public Page getTeaTop(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, Map<String, String> dept,
			String type, int rank) {
		return netTeaTopDao.getTeaTop(currentPage, numPerPage, totalRow, startDate, endDate, dept, type, rank);
	}
	
}
