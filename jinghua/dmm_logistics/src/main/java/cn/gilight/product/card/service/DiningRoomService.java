package cn.gilight.product.card.service;


import cn.gilight.framework.page.Page;

/**
 * 餐厅窗口分析
 *
 */
public interface DiningRoomService {
	
	/**
	 * 餐厅情况
	 * @param startDate
	 * @param endDate
	 * @param queryType 查询条件：'all','zao','zhong','wan'
	 * @return
	 * id <br>
	 * name 名称 <br>
	 * all_money 总消费 <br>
	 * all_count 总消费次数 <br>
	 * day_money 日均消费 <br>
	 * day_count 日均消费次数 <br>
	 * one_money 笔均消费金额 <br>
	 */
	public Page getDiningRoom(int currentPage,int numPerPage,int totalRow,String startDate,String endDate,String queryType);
	
	/**
	 * 窗口情况
	 * @param startDate
	 * @param endDate
	 * @param queryType 查询条件：'all','zao','zhong','wan'
	 * @return
	 * id <br>
	 * name 名称 <br>
	 * all_money 总消费 <br>
	 * all_count 总消费次数 <br>
	 * day_money 日均消费 <br>
	 * day_count 日均消费次数 <br>
	 * one_money 笔均消费金额 <br>
	 */
	public Page getDiningPort(int currentPage,int numPerPage,int totalRow,String startDate,String endDate,String queryType);
	
	
}
