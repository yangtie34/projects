package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface FeeRemissionService {

	/**
	 * 得到助学减免的学年
	 * @return
	 */
	List<Map<String , Object>> querySchoolYear();
	/**
	 * 得到总的学费减免人数以及金额
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生，专科生等等)
	 * @param pid   院系代码(计算机学院，文学院等等)
	 * @return     List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryJmInfo(String schoolYear,String edu,List<AdvancedParam> advancedList);
	/**
	 * 得到总的学费减免人数以及金额
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生，专科生等等)
	 * @param pid   院系代码(计算机学院，文学院等等)
	 * @param deptList 获得权限代码
	 * @return     List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryJmInfo(List<String> deptList, String schoolYear,String edu,List<AdvancedParam> advancedList);
	/**
	 * 学费减免分布
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生,专科生等等)
	 * @param pid   院系代码(计算机学院，文学院等等)
	 * @return     List<Map<String, Object>>
	 */
	Map<String, Object> queryJmfb(String schoolYear,String edu,String fb,List<AdvancedParam> advancedList);
	/**
	 * 查询历年学费减免变化
	 * @param schoolYear
	 * @param id
	 * @param pid   院系代码(计算机学院，文学院等等)
	 * @return
	 */
	Map<String, Object> queryYearJmfb(String bh,List<AdvancedParam> advancedParamList, String edu);
	Map<String, Object> queryYearJmfb(List<String> deptList,String bh,List<AdvancedParam> advancedParamList, String edu);
	/**
	 * 学生 详情
	 * @param advancedParamList 高级查询参数
	 * @param page 分页参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList, Page page, 
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 学生详情（全部）
	 * @param advancedParamList 高级查询参数
	 * @param keyValue 其他参数
	 * @param fields 查询字段
	 * @return String
	 */
	public List<Map<String, Object>> getStuDetailAll(List<AdvancedParam> advancedParamList, Page page, Map<String, Object> keyValue, List<String> fields);
}
