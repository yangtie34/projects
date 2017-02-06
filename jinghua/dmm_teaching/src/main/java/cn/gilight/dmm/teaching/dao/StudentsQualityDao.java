package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface StudentsQualityDao {

	//查询本科生各项数据
	public Map<String, Object> queryStudents(String year);
	
	//查询所有科类
	public List<Map<String, Object>> queryAllSub(String year);
	
	//选择某科类时,查询科类对应数据 sub:科类ID
	public List<Map<String, Object>> queryStudentsSub(String year, List<String> sub);
	
	//查询各省分数线
	public Map<String, Object> queryScoreLineByPro(Page page, String flag,String year,String majorId);
	
	//查询超出某分数的专业
	public Map<String, Object> queryStudentsScore(Page page,String year,String point,String flag);
	
	//查询调剂率
	public Map<String, Object> queryStudentsAdjust(Page page,String year,String flag);
	
	//查询自主招生录取
	public Map<String, Object> queryStudentsEnroll(Page page,String year,String flag);
	
	//查询报到率
	public Map<String, Object> queryStudentsNotReport(String year);
	
	//未报到学生分布
	public List<Map<String, Object>> queryStudentsNotReportByLocal(String year,String xzqh,Boolean updown);
	
	//未报到原因
	public Map<String, Object> queryStudentsNotReportReason(String year);
}
