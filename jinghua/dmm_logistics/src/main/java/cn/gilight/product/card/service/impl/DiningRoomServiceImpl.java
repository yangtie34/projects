package cn.gilight.product.card.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.card.dao.DiningRoomDao;
import cn.gilight.product.card.service.DiningRoomService;

@Service("diningRoomService")
public class DiningRoomServiceImpl implements DiningRoomService{

	@Autowired
	private DiningRoomDao diningRoomDao;

	@Override
	public Page getDiningRoom(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, String queryType) {
		
		return diningRoomDao.getDiningRoom(currentPage, numPerPage, totalRow, startDate, endDate, queryType);
	}

	@Override
	public Page getDiningPort(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, String queryType) {
		
		return diningRoomDao.getDiningPort(currentPage, numPerPage, totalRow, startDate, endDate, queryType);
	}

}
