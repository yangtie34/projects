package cn.gilight.personal.student.card.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**   
* @Description: TODO
* @author Sunwg  
* @date 2016年4月27日 下午2:34:09   
*/
public interface CardService {

	/** 
	* @Description: 查询学生卡余额
	* @param @param sno
	* @return List<Map<String,Object>>
	*/
	public abstract Float queryCardBalance(String sno);
	
	/** 
	* @Description: 查询指定日期的学生的消费明细
	* @param @param sno
	* @param @param date
	* @return List<Map<String,Object>>
	*/
	public abstract List<Map<String,Object>> queryConsumeOfDay(String sno,String date);
	
	/** 
	* @Description: 查询学生的月消费记录
	* @param @param sno
	* @return Page
	*/
	public abstract Page queryConsumeOfMonth(String sno,Page page);
	
	/** 
	* @Description: 查询月消费明细
	* @param @param sno
	* @param @param page
	* @param @param month
	* @return Page
	*/
	public abstract Page queryConsumeDetailOfMonth(String sno,Page page,String month);
	
	/** 
	* @Description: 查询和学生同年级的学生的人数
	* @param @param sno
	* @return int
	*/
	public abstract int querySameGradeNumsOFStudent(String sno);
	
	/** 
	 * @Description: 查询学生的一卡通消费总额
	 * @param @param sno
	 * @return float
	 */
	public abstract float queryTotalConsumeOfStudent(String sno);
	
	/** 
	 * @Description: 查询
	 * @param @param sno
	 * @return int
	 */
	public abstract int queryConsumeTotalHigherThenStudentNums(String sno);
	
	/** 
	* @Description: 查询学生的消费总额的消费类别分布
	* @param @param sno
	* @return List<Map<String,Object>>
	*/
	public abstract List<Map<String, Object>> queryDealsOfStudentTotalConsume(String sno);
	
}
