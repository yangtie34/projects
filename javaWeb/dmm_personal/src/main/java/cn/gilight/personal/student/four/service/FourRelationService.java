package cn.gilight.personal.student.four.service;

import java.util.List;
import java.util.Map;

/**
 * 
* @Description: TODO
* @author Chenxt
* @date 2016年7月8日 下午3:34:10
 */
public interface FourRelationService {

	/**
	 * 查询学生的辅导员
	 * @param username
	 * @return
	 */
	public List<Map<String, Object>> getFdys(String username);

	/**
	 * 查询授课老师
	 * @param xn
	 * @param xq
	 * @param username
	 * @return
	 */
	public List<Map<String, Object>> queryTea(String xn, String xq,String username);
	
	/**
	 * 查询我的入校日期和学校名称
	 * @param username
	 * @return
	 */
	public Map<String,Object> mySchool (String username);	
	

}
