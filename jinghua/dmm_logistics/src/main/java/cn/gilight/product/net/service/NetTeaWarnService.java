package cn.gilight.product.net.service;

import java.util.Map;

import cn.gilight.framework.page.Page;

public interface NetTeaWarnService {
	
	/**
	 * 获取预警教师名单
	 * @param currentPage
	 * @param numPerPage
	 * @param totalRow
	 * @param startDate
	 * @param endDate
	 * @param dept
	 * @param value
	 * @return
	 */
	public Page getTeaWarn(int currentPage,int numPerPage,int totalRow,String startDate, String endDate,Map<String, String> dept);
	
	/**
	 * 通过教师ID获取上网记录统计
	 * @param currentPage
	 * @param numPerPage
	 * @param totalRow
	 * @param startDate
	 * @param endDate
	 * @param teaId
	 * @return
	 */
	public Page getTeaWarnDetil(int currentPage,int numPerPage,int totalRow,String startDate, String endDate,String teaId);
	
	
}
