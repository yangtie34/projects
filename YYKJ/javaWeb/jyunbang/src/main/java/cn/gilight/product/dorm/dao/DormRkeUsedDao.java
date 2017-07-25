package cn.gilight.product.dorm.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface DormRkeUsedDao {
	
	/**
	 * 住宿学生情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * use_rate 使用率<br>
	 * use_num 使用人数<br>
	 * all_num 总人数<br>
	 */
	public Map<String,Object> getDormRkeUsed(String startDate,String endDate,Map<String,String> deptTeach);
	
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
	public List<Map<String,Object>> getDormRkeUsedBySex(String startDate,String endDate,Map<String,String> deptTeach);
	
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
	public List<Map<String,Object>> getDormRkeUsedByEdu(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分民族使用情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * use_rate 使用率<br>
	 * use_num 使用人数<br>
	 * all_num 总人数<br>
	 */
	public List<Map<String,Object>> getDormRkeUsedByMz(String startDate,String endDate,Map<String,String> deptTeach);
	
	/**
	 * 分楼宇使用情况
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * use_rate 使用率<br>
	 * use_num 使用人数<br>
	 * all_num 总人数<br>
	 */
	public List<Map<String,Object>> getDormRkeUsedByLY(String startDate,String endDate,Map<String,String> deptTeach);
	
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
	public List<Map<String,Object>> getDormRkeUsedByDept(String startDate,String endDate,Map<String,String> deptTeach);
	
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
	public Page getDormRkeUsedPage(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate,String endDate,Map<String,String> deptTeach,String type,String type_code);
	
	/**
	 * 不活跃人群
	 * @param startDate
	 * @param endDate
	 * @param deptTeach
	 * @return
	 * tl_card_use_stu_month 表所有<br>
	 */
	public Page getNoDormRkeUsed(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,String startDate,String endDate,Map<String,String> deptTeach);
	
}
