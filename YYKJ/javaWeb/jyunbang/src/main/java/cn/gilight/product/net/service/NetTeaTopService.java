package cn.gilight.product.net.service;

import java.util.Map;

import cn.gilight.framework.page.Page;

public interface NetTeaTopService {
	
	/**
	 * 获取教师上网排名
	 * @param currentPage
	 * @param numPerPage
	 * @param totalRow
	 * @param startDate
	 * @param endDate
	 * @param dept
	 * @param type 类型 flow，time
	 * @param rank 名次
	 * @return
	 */
	public Page getTeaTop(int currentPage,int numPerPage,int totalRow,String startDate, String endDate,Map<String, String> dept,String type,int rank);
	
}
