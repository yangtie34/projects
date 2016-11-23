package com.jhkj.mosdc.framework.util;

import java.util.List;

/**
 */
public class Page {
	
	/**
	 * 起始记录
	 */
//	private int start;
	
	/**
	 * 每页记录
	 */
	private int limit;
	
	/**
	 * 总记录
	 */
	private Long total;
	
	/**
	 * 结果集
	 */
	private List rows;

	/*public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}*/

	public Page(long totalCount, int limit2, List list) {
		this.total = totalCount;
		this.limit = limit2;
		this.setRows(list);
	}

	public Page() {
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
	
}
