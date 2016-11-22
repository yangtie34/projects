package com.jhnu.product.four.book.service;

import java.util.List;
import java.util.Map;

import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.chart.Chart;
import com.jhnu.system.common.condition.Condition;
import com.jhnu.system.common.page.Page;

public interface FourBookService {
	
	/**
	 * 通过Log获取第一次借书
	 * 通过Log获取第一次进入图书馆
	 * @param id 学号ID
	 * @return
	 */
	public ResultBean getFirstBookLog(String id);
	
	
	/**
	 * 通过Log获取借书统计
	 * @param id 学号ID
	 * @return
	 */
	public ResultBean getAllBorrowLog(String id);
	
	
	/**
	 * 通过Log获取进出图书馆统计
	 * @param id 学号ID
	 * @return
	 */
	public ResultBean getAllRKELog(String id);
	
	
	
	/**
	 * 通过Job,保存第一次借书的Log
	 * @param id 学号ID
	 * @return
	 */
	public void saveFirstBorrowLogJob();
	
	
	/**
	 * 通过Job,保存第一次进入图书馆Log
	 * @param id 学号ID
	 * @return
	 */
	public void saveFirstRKELogJob();
	
	
	/**
	 * 通过Job,保存借书统计Log
	 * @param id 学号ID
	 * @return
	 */
	public void saveAllBorrowLogJob();
	
	
	/**
	 * 通过Job,保存进出图书馆统计Log
	 * @param id 学号ID
	 * @return
	 */
	public void saveAllRKELogJob();
	/**
	 * 保存学生图书借阅总次数
	 */
	public void saveBookBorrowStuJob();
	
	/**
	 * 保存学生图书出入次数
	 */
	public void saveBookRKEStuJob();
	/**
	 * 取得学生借阅的2条曲线图数据
	 * @param id
	 * @return
	 */
	public Chart getBookBorrowLines(String id);
	/**
	 * 取得学生出入次数的2条曲线图数据
	 * @param id
	 * @return
	 */
	public Chart getBookRKELines(String id);
	
	
	public Page getBookDetailLog(String id,Page page,List<Condition> conditions);
	
	public List<Map<String, Object>> getBookDetailGroupByDeal(String id);
	public List<Map<String, Object>> getBookDetailGroupByTime(String id);
	
}
