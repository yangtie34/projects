package com.jhkj.mosdc.framework.bean;

public class PageParam {

	/**
	 * 起始记录
	 */
	private int start;

	/**
	 * 每页记录
	 */
	private int limit;

	/**
	 * 记录数
	 */
	private int recordCount;

	public PageParam(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

}
