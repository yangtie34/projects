package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.framework.page.Page;

public interface SmartService {
    /**
     * 获取年级选择
     * @param advancedParamList 高级查询参数
     * @return Map<String, Object>
     */
	public Map<String, Object> getGradeSelect(List<AdvancedParam> advancedParamList,Integer schoolYear);
    /**
     * 获取学年学期选择
     * @param advancedParamList
     * @return Map<String, Object>
     */
	public Map<String, Object> getYearAndTerm();
	/**
	 * 获取学历选择
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getEduSelect();
	/**
	 * 获取学霸列表
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 就读学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getTopGpa(Integer schoolYear, String term,
			String grade, String edu, List<AdvancedParam> advancedParamList,int pagesize,int index);
	/**
	 * 获取学霸来源地
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 就读学历
	 * @param xzqh 行政区划
	 * @param updown 上钻下钻条件
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getXbFrom(Integer schoolYear, String term,
			String grade, String edu, String xzqh, Boolean updown,
			List<AdvancedParam> advancedParamList);
	/**
	 * 获取表格数据
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 就读学历
	 * @param selected 选中条件
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getTable(Integer schoolYear, String term, String grade,
			String edu,int pagesize,int index,String column,Boolean asc,String type,List<AdvancedParam> advancedParamList);
	/**
	 * 学霸人数以及占比
	 * @param edu 就读学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getXbCountLine( String edu,
			List<AdvancedParam> advancedParamList);
	/**
	 * 雷达图数据
	 * @param schoolYear 学年  
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 就读学历
	 * @param advancedParamList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getRadar(Integer schoolYear, String term, String grade,
			String edu, List<AdvancedParam> advancedParamList);
	/**
	 * 表格筛选条件
	 * @return Map<String, Object>
	 */
	public Map<String,Object> getDisplayedLevelType();
	/**
	 * 获取学霸生物钟数据
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getTimeLine(Integer schoolYear, String termCode);
	/**
	 * 获取学霸明细 
	 * @param advancedParamList 高级查询参数
	 * @param page 分页数据
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return Map<String,Object>
	 */
	public Map<String, Object> getStuList(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields);
	/**
	 * 学霸明细导出
	 * @param advancedParamList 高级查询参数
	 * @param page 分页数据
	 * @param keyValue 其他参数
	 * @param fields 字段名
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getStuDetail(
			List<AdvancedParam> advancedParamList, 
			Map<String, Object> keyValue, List<String> fields);
	/**
	 * 某一个学生的能力雷达图
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 学历
	 * @param stuNo 学号
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getRadarStu(Integer schoolYear, String term,
			String grade, String edu, String stuNo,List<AdvancedParam> advancedParamList);
	/**
	 * 查询某个学生的住宿地址
	 * @param stuNo 学号
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getDormStu(String stuNo);
	/**
	 * 某一个学生的消费情况
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 学历
	 * @param stuNo 学号
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCostStu(Integer schoolYear, String term,
			String grade, String edu, String stuNo,
			List<AdvancedParam> advancedParamList);
	/**
	 * 某一个学生的借书情况
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 学历
	 * @param stuNo 学号
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getBorrowStu(Integer schoolYear, String term,
			String grade, String edu, String stuNo,
			List<AdvancedParam> advancedParamList);
	/**
	 * 某一个学生的成绩情况
     * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 学历
	 * @param advancedParamList
	 * @return
	 */
	public Map<String, Object> getScoreStu(Integer schoolYear, String term,
			String grade, String edu, String stuNo,
			List<AdvancedParam> advancedParamList);
	/**
	 * 某一个学生的成绩明细
     * @param schoolYear 学年
	 * @param term 学期
	 * @param stuNo
	 * @return
	 */
	public Map<String, Object> getScoreStuMx(Integer schoolYear, String term,
			String stuNo);


}
