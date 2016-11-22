package cn.gilight.personal.student.smart.service;

import java.util.List;
import java.util.Map;

public interface StuSmartService {
	
	/** 
	* @Description: 根据学号查询成绩信息
	* @Return: List<Map<String, Object>>
	* @param sno
	*/
	public List<Map<String, Object>> queryScoreOfStudent(String sno);
	
	/** 
	* @Description: 查询指定年级指定专业下的学霸的成绩信息
	* @Return: List<Map<String, Object>>
	* @param zyid
	* @param rxnj
	*/
	public List<Map<String, Object>> queryScoreOfSmartStudent(String zyid,String rxnj);

	/** 
	* @Description: 根号学号查询学生的消费信息
	* @Return: Map<String,Object>
	* @param sno
	*/
	public Map<String,Object> queryConsumeOfStudent(String sno);
	
	/** 
	* @Description: 查询指定入学年级指定性别的学霸的消费情况
	* @Return: Map<String,Object>
	* @param zyid
	* @param rxnj
	* @param gender
	*/
	public Map<String,Object> queryConsumeOfSmartStudent(String rxnj,String gender);
	
	/** 
	* @Description: 根据学号查询学生的用餐情况
	* @Return: Map<String,Object>
	* @param sno 
	*/
	public Map<String,Object> queryDinnerOfStudent(String sno);
	
	/** 
	* @Description: 查询指定年级指定性别学霸的一日三餐用餐情况
	* @Return: Map<String,Object>
	* @param zyid
	* @param rxnj
	*/
	public Map<String,Object> queryDinnerOfSmartStudent(String zyid,String rxnj);
	
	/** 
	* @Description: 根据学号查询图书借阅量
	* @Return: Map<String,Object>
	* @param sno
 	*/
	public Map<String,Object> queryBookOfStudent(String sno);
	
	/** 
	* @Description: 查询指定专业，指定入学年级的学霸的图书借阅量
	* @Return: Map<String,Object>
	* @param zyid
	* @param rxnj
	*/
	public Map<String,Object> queryBookOfSmartStudent(String zyid,String rxnj);

	/** 
	* @Description: 查询指定入学年级的学霸的图书借阅量
	* @Return: Map<String,Object>
	* @param zyid
	* @param rxnj
	*/
	public Map<String,Object> queryBookOfSmartStudent(String rxnj);
	
	/** 
	* @Description: 查询指学号查询学生的借阅偏好
	* @Return: Map<String,Object>
	* @param zyid
	* @param rxnj
	*/
	public Map<String,Object> queryBookTypeOfStudent(String sno);
	
	/** 
	* @Description: 查询指定专业，指定入学年级的学霸的借阅偏好
	* @Return: Map<String,Object>
	* @param zyid
	* @param rxnj
	*/
	public Map<String,Object> queryBookTypeOfSmartStudent(String zyid,String rxnj);

	/** 
	* @Description: 查询指定入学年级的学霸的借阅偏好
	* @Return: Map<String,Object>
	* @param zyid
	* @param rxnj
	*/
	public Map<String,Object> queryBookTypeOfSmartStudent(String rxnj);
}