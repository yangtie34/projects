package cn.gilight.product.bookRke.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.product.bookRke.dao.StuBookRkeDao;
import cn.gilight.product.bookRke.service.StuBookRkeService;
@Service("stuBookRkeService")
public class StuBookRkeServiceImpl implements StuBookRkeService{
	@Autowired
	private StuBookRkeDao stuBookRkeDao;

	@Override
	public Page getRankLively(int currentPage, int numPerPage, int totalRow,
			String startDate, String endDate, Map<String, String> deptTeach) {
		return stuBookRkeDao.getRankLively(currentPage, numPerPage, totalRow, startDate, endDate, deptTeach);
	}

	@Override
	public Map<String, List<Map<String, Object>>> getBlByLB(String startDate,
			String endDate, Map<String, String> deptTeach) {
		Map<String, List<Map<String, Object>>> map=new HashMap<String, List<Map<String, Object>>>();
		String[] lbs=new String[]{"xb","xl","mz"};
		for(int i=0;i<lbs.length;i++){
			map.put(lbs[i], stuBookRkeDao.getBlByLB(startDate, endDate, deptTeach, lbs[i]));
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getCountsByDeptLively(String startDate,
			String endDate, Map<String, String> deptTeach) {
		return stuBookRkeDao.getCountsByDeptLively(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getCountsByDeptNoLively(String startDate,
			String endDate, Map<String, String> deptTeach) {
		return stuBookRkeDao.getCountsByDeptNoLively(startDate, endDate, deptTeach);
	}

	@Override
	public List<Map<String, Object>> getStuRkeTrend(String stuId) {
		return stuBookRkeDao.getStuRkeTrend(stuId);
	}
}
