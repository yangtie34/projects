package cn.gilight.dmm.xg.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.framework.page.Page;

public interface StudentLoansService {
	/**
	 * 得到助学减免的学年
	 * @return
	 */
	List<Map<String , Object>> querySchoolYear();
	/**
	 * 得到总的助学贷款人数以及金额
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生，专科生等等)
	 * @return     List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryZxInfo(String schoolYear,String id,String pid);
	List<Map<String, Object>> queryZxInfo(List<String> deptList, String schoolYear,String id,String pid);

	/**
	 * 查询助学金贷款学生行为
	 * @param schoolYear
	 * @param id
	 * @return
	 */
	Map<String, Object> queryZxxw(String schoolYear,String edu,List<AdvancedParam> advancedParamList);
	Map<String, Object> queryZxxw(List<String> deptList,List<AdvancedParam> advancedParamList,String schoolYear,String edu,Constant.JCZD_Table table);
	/**
	 * 助学贷款分布
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生,专科生等等)
	 * @return     List<Map<String, Object>>
	 */
	Map<String, Object> queryZxfb(String schoolYear,String edu,String fb,List<AdvancedParam> advancedParamList);
	/**
	 * 查询历年助学贷款变化
	 * @param schoolYear
	 * @param id
	 * @return
	 */
	Map<String, Object> queryYearZxfb(String bh,List<AdvancedParam> advancedParamList);
	Map<String, Object> queryYearZxfb(List<String> deptList,String bh,List<AdvancedParam> advancedParamList);
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
	public List<Map<String, Object>> getStuDetailAll(List<AdvancedParam> advancedParamList,Page page, Map<String, Object> keyValue, List<String> fields);
}
