package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.BhrConstant;

public interface SmartDao {
	/**
	 * 查询条件下最大的学制
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return int
	 */
	public int queryGradeLen(List<String> deptList,List<AdvancedParam> stuAdvancedList,int schoolYear);
    /**
     * 学年学期选择数据
     * @return int
     */
	public int queryYearAndTerm();
    /**
     * 学历选择数据
     * @param deptList 标准权限
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryEdu(List<String> deptList);
    /**
     * 查询学霸排名
     * @param schoolYear 学年
     * @param term 学期
     * @param grade 年级
     * @param edu 就读学历
     * @param deptList 标准权限
     * @param stuAdvancedList 高级查询参数
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryGpa(Integer schoolYear, String term,
			 List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询学霸来源地区
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 就读学历
	 * @param xzqh 行政区划编码
	 * @param updown 上钻还是下钻
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryFrom(Integer schoolYear, String term,
		 String xzqh, Boolean updown,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询学霸人数以及占比
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryCounts(List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询表格数据
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 就读学历
	 * @param deptList 标准权限
	 * @return Map<String, Object>
	 */
	public  Map<String, Object> queryTable(Integer schoolYear, String term,
		List<String> deptList,int pagesize,int index,String column,Boolean asc,String type,List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询雷达图数据
	 * @param schoolYear 学年 
	 * @param term 学期
	 * @param grade 年级
	 * @param edu 就读学历
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryRadar(Integer schoolYear, String term,
			 List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 表格按课程查询数据
	 * @param schoolYear
	 * @param term
	 * @param grade
	 * @param edu
	 * @param selected
	 * @param deptList
	 * @param pagesize
	 * @param index
	 * @param column
	 * @param asc
	 * @param type
	 * @param stuAdvancedList
	 * @return
	 */
	public Map<String, Object> queryTableCourse(Integer schoolYear, String term,
		 List<String> deptList,
			int pagesize, int index, String column, Boolean asc, String type,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 表格按教师查询数据
	 * @param schoolYear
	 * @param term
	 * @param grade
	 * @param edu
	 * @param deptList
	 * @param pagesize
	 * @param index
	 * @param column
	 * @param asc
	 * @param type
	 * @param stuAdvancedList
	 * @return
	 */
	public Map<String, Object> queryTableTeach(Integer schoolYear, String term,
			 List<String> deptList,
			int pagesize, int index, String column, Boolean asc, String type,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询学霸标准
	 * @return
	 */
	public double queryXbGpa();
	/**
	 * 查询学霸gpa的sql
	 * @param schoolYear
	 * @param termCode
	 * @param deptList
	 * @param stuAdvancedList
	 * @return String
	 */
	public String getStuSql(Integer schoolYear, String termCode,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询时间轴数据
	 * @param table 
	 * @param season 季节
	 * @return Map<String, Object>
	 */
	public Map<String, Object> queryTimeline(String schoolyear,String term,BhrConstant.BHRTIME_Table table,String season);
	/**
	 * 查询学生明细sql
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param xzqh 行政区划
	 * @param updown 上钻还是下钻
	 * @param deptList 标准权限
	 * @param stuAdvancedList 学生高级查询参数
	 * @return String 
	 */
	public String queryStuListSql(Integer schoolYear, String term, String xzqh,
			Boolean updown, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 集合排序
	 * @param list
	 * @param order_column
	 * @param asc
	 */
	public void sortList(List<Map<String, Object>> list, String order_column,
			boolean asc);
	/**
	 * 获取中国id
	 * @return
	 */
	public String getChinaId();
	/**
	 * 雷达图某一个学生
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param stuNo 学号
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryRadar(Integer schoolYear, String term,
			String stuNo, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 查询某个学生住宿的楼、宿舍、床位
	 * @param stuNo 学号
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getDormStu(String stuNo);
	/**
	 * 获取某个学生的消费情况
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param stuNo 学号
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getCost(Integer schoolYear, String term, String stuNo,
			List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取某个学生的借书情况
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param stuNo 学号
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getBorrow(Integer schoolYear, String term,
			String stuNo, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 *  获取某个学生的成绩情况
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param stuNo 学号
	 * @param deptList 标准权限 
	 * @param stuAdvancedList 高级查询参数
	 * @return Map<String, Object>
	 */
	public Map<String, Object> getScoreGk(Integer schoolYear, String term,
			String stuNo, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取该学生某学期成绩明细
	 * @param schoolYear 学年
	 * @param term 学期
	 * @param stuNo 学号
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> getScoreTable(Integer schoolYear, String term,
			String stuNo);
}
