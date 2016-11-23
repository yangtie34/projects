package cn.gilight.product.bookRke.dao;

import java.util.Map;

public interface JobBookRkeDao {
	/**
	 * 图书馆门禁信息log
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateBookRkeStuMonth(String yearMonth);
	/**
	 * 学生出入图书馆门禁信息log
	 * @param year
	 * @param month
	 * @return
	 */
	public Map<String,Integer> updateStuRkeBookMonth(String yearMonth);
}
