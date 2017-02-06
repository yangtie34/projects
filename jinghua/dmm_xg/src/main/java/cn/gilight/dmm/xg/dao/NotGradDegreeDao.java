package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 无法毕业无学位学生
 * @author lijun
 *
 */
public interface NotGradDegreeDao {

	/**
	 * 得到无法毕业和无学位证的人数
	 * @param deptList
	 * @return
	 */
	Map<String, Object> queryXwInfo(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 无学位证学生人数及比例（按院系）
	 * @param schoolYear
	 * @param id
	 * @param pid
	 * @return
	 */
	List<Map<String, Object>> queryXwfbAndRatio(List<String> deptList,List<AdvancedParam> advancedList,String fb);
	/**
	 * 无法毕业学生人数及比例（按院系）
	 * @param schoolYear
	 * @param id
	 * @param pid
	 * @return
	 */
	List<Map<String, Object>> queryByfbAndRatio(List<String> deptList,List<AdvancedParam> advancedList,String fb);
	String queryXwfbAndRatios(int year,List<String> deptList,List<AdvancedParam> advancedList,String fb);
	/**
	 * 无学位学生学科分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXkfb(int year,List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 无学位学生年级分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryNjfb(int year,List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 无学位学生性别分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryXbfb(int year,List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 无学位学生院系分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryYyfb(int year,List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 最近几年无学位学生状态分布
	 * @param deptList
	 * @return
	 */
	List<Map<String, Object>> queryStatefbByYear(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	String getDetailstuSql(int year,List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到无学位学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
    String getNodegreeStuSql(int year,List<String> deptList,List<AdvancedParam> advancedList);
    /**
     * 得到无毕业学生详细信息表
     * @param deptList
     * @param advancedList
     * @return
     */
    String getNoGraduationStuSql(int year,List<String> deptList,List<AdvancedParam> advancedList);
}
