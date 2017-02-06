package cn.gilight.dmm.teaching.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

public interface BysQxDao {
    /**
     * 根据学年，学生sql查询已毕业或者获得学位证的学生
     * @param schoolYear 学年
     * @param stuSql 学生sql
     * @param graduated 是否毕业
     * @param isDegree 是否获得学位证
     * @return String
     */
	public String getBysSql(String schoolYear, String stuSql, Boolean graduated,
			Boolean isDegree);
    /**
     * 根据学年，标准权限，高级查询参数获得毕业生去向分布
     * @param schoolYear 学年
     * @param stuSql 学生sql
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryBysFb(String schoolYear,String stuSql,int scale,Boolean isScale);
	 /**
     * 根据学年，标准权限，高级查询参数获得毕业生升学去向分布
     * @param schoolYear 学年
     * @param stuSql 学生sql
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryBysSzFb(String schoolYear,String stuSql,int scale,Boolean isScale);
	/**
	 * 根据学年，标准权限，高级查询参数获得毕业生未就业原因分布
	 * @param schoolYear 学年
     * @param stuSql 学生sql
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryBysWjyyy(String schoolYear,String stuSql);
	/**
	 * 根据学年，标准权限，高级查询参数获得毕业生升学去向总人数
	 * @param schoolYear 学年
	 * @param stuSql 学生sql
	 * @return int
	 */
	public int queryCountFb(String schoolYear, String stuSql);
	 /**
     * 根据学年，标准权限，高级查询参数获得毕业生升学去向总人数
     * @param schoolYear 学年
     * @param stuSql 学生sql
     * @return int
     */
	public int queryCountSzFb(String schoolYear, String stuSql);
	/**
	 * 根据code获取全息码
	 * @param code
	 * @return
	 */
	public String getPath(String code);
	/**
	 * 获取各部门升学去向人数
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param advancedList 高级查询参数
	 * @return List<Map<String, Object>>
	 */
	public List<Map<String, Object>> queryCountFbByDept(String schoolYear,
			List<String> deptList, List<AdvancedParam> advancedList);


}
