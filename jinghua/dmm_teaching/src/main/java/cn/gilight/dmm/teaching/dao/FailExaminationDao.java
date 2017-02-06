package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 挂科补考分析
 * @author lijun
 *
 */
public interface FailExaminationDao {

	//得到挂科基本信息(挂科人数  挂科率 环比变化 平均挂科数)
	Map<String, Object> getGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu);
	/**
	 * 挂科分类信息(学生类别 人数 挂科率 变化)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getGkflInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu);
	/**
	 * 各年级挂科分布(人数 人均挂科数)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getNjGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,List<Map<String, Object>> listNjname);
	/**
	 * 男女生挂科分布(人数 人均挂科数)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getXbGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu);
	/**
	 * 挂科课程分布--公共课/专业课(人数 人均挂科数)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getNatKcGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu);
	/**
	 * 挂科课程分布--必修课/选修课(人数 人均挂科数)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getAttrKcGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu);
	/**
	 * 各机构挂科分布(人数 人均挂科数)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getJgGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String dept);
	/**
	 * 挂科top(人数 人均挂科数)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getTopGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String lx,String gkSort,String turnPage);
	/**
	 * 学生挂科top(人数 人均挂科数)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getStuTopGkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String gkStuSort,String turnStuPage);
	/**
	 * 人均补考top(人数 人均挂科数)
	 * @param advancedList 高级搜索
	 * @param schoolYear   学年
	 * @param termCode     学期
	 * @param edu          本专科
	 * @return
	 */
	List<Map<String, Object>> getTopbkInfo(String stuSql,List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String lx,String bkTopSort,String bkturnPage);
}
