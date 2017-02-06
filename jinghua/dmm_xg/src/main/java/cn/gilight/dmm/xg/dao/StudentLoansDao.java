package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 
 * @author liyintao
 *
 */
public interface StudentLoansDao {

	/**
	 * 得到总的助学贷款人数以及金额
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生，专科生等等)
	 * @return     List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryZxInfo(List<String> deptList,String schoolYear,String id,String pid);
	/**
	 * 查询助学金贷款学生行为
	 * @param schoolYear
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> queryZxxw(List<String> deptList,String schoolYear,String id);
	/**
	 * 助学贷款分布
	 * @param schoolYear 学年
	 * @param id   学生类别代码(本科生,专科生等等)
	 * @return     List<Map<String, Object>>
	 */
	List<Map<String, Object>> queryZxfb(int schoolYear,List<String> deptList,List<AdvancedParam> advancedList,String fb,String id_);
	/**
	 * 查询历年助学贷款变化
	 * @param schoolYear
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> queryYearZxfb(List<String> deptList,int year,String bh,List<AdvancedParam> advancedList);
	/**
	 * 得到组学贷款类型
	 * @return
	 */
	List<Map<String, Object>> queryJmType();
	/**
	 * 得到学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
	String getDetailstuSql(List<String> deptList,List<AdvancedParam> advancedList);
	/**
	 * 得到助学贷款学生详细信息表
	 * @param deptList
	 * @param advancedList
	 * @return
	 */
    String getZxdkStuSql(List<String> deptList,List<AdvancedParam> advancedList,int year);

}
