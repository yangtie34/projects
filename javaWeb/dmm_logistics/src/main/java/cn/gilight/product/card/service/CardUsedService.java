package cn.gilight.product.card.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 学生一卡通使用状况分析
 *
 */
public interface CardUsedService {
	
	/**
	 * 一卡通所有学生情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * use_rate 使用率<br>
	 * use_num 使用人数<br>
	 * all_num 总人数<br>
	 */
	public Map<String,Object> getCardUsed(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分性别使用情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * use_rate 使用率<br>
	 * use_num 使用人数<br>
	 * all_num 总人数<br>
	 */
	public List<Map<String,Object>> getCardUsedBySex(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学历使用情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * use_rate 使用率<br>
	 * use_num 使用人数<br>
	 * all_num 总人数<br>
	 */
	public List<Map<String,Object>> getCardUsedByEdu(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分学院使用情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * use_rate 使用率<br>
	 * use_num 使用人数<br>
	 * all_num 总人数<br>
	 */
	public List<Map<String,Object>> getCardUsedByDept(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 活跃人群
	 * @param currentPage
	 * @param numPerPage
	 * @param totalRow
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @param type 'use_all','use_sex','use_edu'
	 * @param type_code 对应的ID值
	 * @return
	 * tl_card_use_stu_month 表所有<br>
	 */
	public Page getCardUsedPage(int currentPage,int numPerPage,int totalRow,String startDate,String endDate,Map<String,String> deptTeach,String type,String type_code);
	
	/**
	 * 不活跃人群
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * tl_card_use_stu_month 表所有<br>
	 */
	public Page getNoCardUsed(int currentPage,int numPerPage,int totalRow,String startDate,String endDate,Map<String,String> deptTeach);
	
}
