package cn.gilight.dmm.teaching.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface CourseService {
	
	/**
	 * 学年学期,学历
	 * @return map
	 */
	 public Map<String, Object> getBzdm();
	
	/**
	 * 课程分布
	 * @param advancedParamList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return map
	 */
	public Map<String , Object> getCourseDistribution(List<AdvancedParam> advancedParamList,String edu,String schoolYear, String termCode);

	/**
	 * 获取各机构课程数据
	 * @param advancedParamList 高级查询参数
	 * @param edu 学历
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param codeType课程（属性，性质，类别）
	 * @param code 课程（属性，性质，类别）编码
	 * @return
	 */
	public Map<String,Object> getDeptDistribution(List<AdvancedParam> advancedParamList, String edu,
			String schoolYear, String termCode,String codeType,String code);
	/**
	 * 获取各学科课程数据
	 * @param advancedParamList 高级查询参数
	 * @param edu 学历
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param codeType课程（属性，性质，类别）
	 * @param code 课程（属性，性质，类别）编码
	 * @return
	 */
	public Map<String,Object> getSubjectDistribution(List<AdvancedParam> advancedParamList, String edu,
			String schoolYear, String termCode,String codeType,String code);
	
	/**
	 * 获取各机构历史趋势 
	 * @param advancedParamList 高级查询参数
	 * @param edu 学历
	 * @param codeType课程（属性，性质，类别）
	 * @param code 课程（属性，性质，类别）编码
	 * @return
	 */
	public Map<String,Object> getDeptHistory(List<AdvancedParam> advancedParamList, String edu,String codeType,String code);
	/**
	 * 获取各学科历史数据
	 * @param advancedParamList 高级查询参数
	 * @param edu 学历
	 * @param codeType课程（属性，性质，类别）
	 * @param code 课程（属性，性质，类别）编码
	 * @return
	 */
	public Map<String,Object> getSubjectHistory(List<AdvancedParam> advancedParamList, String edu,String codeType,String code);
}
