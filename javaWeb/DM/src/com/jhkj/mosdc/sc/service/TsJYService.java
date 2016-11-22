package com.jhkj.mosdc.sc.service;

/**
 * 图书借阅统计
 * @author Administrator
 *
 */
public interface TsJYService {
	/**
	 * 展示各学院借阅图书的藏书类别组成
	 * @param params
	 * @return
	 */
	public String queryBookTypeByYX(String params);
	
	/**
	 * 图书借阅量按院系排名
	 * @param params
	 * @return
	 */
	public String queryBookNumberByYX(String params);
	
	/**
	 * 图书借阅按藏书类别排名
	 * @param params
	 * @return
	 */
	public String queryBookNumberByType(String params);
	
	/**
	 * 借出图书TOP10
	 * @param params
	 * @return
	 */
	public String queryBookTop(String params);
	
	/**
	 * 借书量高的学生的TOP10
	 * @param params
	 * @return
	 */
	public String queryStudentTop(String params);
	
}
