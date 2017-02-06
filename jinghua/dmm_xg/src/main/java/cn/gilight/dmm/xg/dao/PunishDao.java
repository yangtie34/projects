package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 违纪处分
 * 
 * @author xuebl
 * @date 2016年5月31日 下午3:39:03
 */
public interface PunishDao {

	/**
	 * 获取违纪数据 sql
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String （select t.* from t_stu_punish t）
	 */
	public String getViolateSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);
	/**
	 * 获取违纪学生 人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String
	 */
	public int queryViolateCount(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);
	/**
	 * 获取违纪学生 sql
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String （select distinct(stu_id) from t_stu_punish t）
	 */
	public String getViolateStuIdSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);
	/**
	 * 获取违纪学生 数量
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String （select distinct(stu_id) from t_stu_punish t）
	 */
	public int queryViolateStuIdCount(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);

	/**
	 * 获取处分数据 sql
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String （select t.* from t_stu_punish t）
	 */
	public String getPunishSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);
	/**
	 * 获取处分学生 人次
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String
	 */
	public int queryPunishCount(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);
	/**
	 * 获取处分学生 sql
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String
	 */
	public String getPunishStuIdSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);
	/**
	 * 获取处分学生 数量
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String
	 */
	public int queryPunishStuIdCount(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);
	
	/**
	 * 获取违纪类型 分组数据（人次）
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String
	 */
	public List<Map<String, Object>> queryViolateTypeList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date);

	/**
	 * 获取处分类型 分组数据（人次）
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String
	 */
	public List<Map<String, Object>> queryPunishTypeList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date);

	/**
	 * 查询组织机构节点的 违纪分组数据（人次）
	 * <br> 与queryViolateType的区别是，这里会出现类型为0的数据
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @param violateBzdmSql 违纪原因sql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryViolateDeptList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date, String violateBzdmSql);

	/**
	 * 查询组织机构节点的 处分分组数据（人次）
	 * <br> 与queryPunishType的区别是，这里会出现类型为0的数据
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @param violateBzdmSql 违纪原因sql
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryPunishDeptList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date, String punishBzdmSql);

	/**
	 * 违纪数据 月份分组
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryViolateMonthList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date);

	/**
	 * 处分数据 月份分组
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryPunishMonthList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date);

	/**
	 * 违纪学生 出生日期
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return List<String>
	 */
	public List<String> queryViolateBirthdayList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date);
	
	/**
	 * 处分学生 出生日期
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return List<String>
	 */
	public List<String> queryPunishBirthdayList(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, 
			String start_date, String end_date);

	/**
	 * 二次异动数量
	 * @param punishStuIdSql 异动学生Id sql（select no_ from t_stu）
	 * @param end_date 计算截止日期
	 * @return int
	 */
	public int queryPunishAgainCount(String punishStuIdSql, String end_date);
	
	/**
	 * 查询目标权限下所包含的 违纪编码
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String （select code.* from t_code_violate code）
	 */
	public String getViolateBzdmSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);

	/**
	 * 查询目标权限下所包含的 处分编码
	 * @param schoolYear 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @return String （select code.* from t_code code）
	 */
	public String getPunishBzdmSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date);
	/**
	 * 获取学生违纪 处分sql
	 * @param schoolYear 学年
	 * @param deptList  权限
	 * @param advancedList 该机参数
	 * @param start_date 开始日期
	 * @param end_date 结束日期
	 * @param keyValue 详细参数
	 * @param fields 表头
	 * @return
	 */
	public String getStuDetailSql(Integer schoolYear,List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,Map<String, Object> keyValue, List<String> fields);

	/**
	 * 获取年级违纪信息
	 * @param school_year 学年
	 * @param deptList 数据权限
	 * @param advancedList 高级查询参数
	 * @param start 开始时间
	 * @param end 介素时间
	 * @param grade 年级
	 * @param level 违纪/处分分类
	 * @return
	 */
	public String getGradeOrAgeDetailSql(Integer school_year,List<String> deptList, List<AdvancedParam> advancedList, String start,
			String end,String grade,String level,String type);
	
	/**
	 * 获取违纪处分学生男女比例
	 * @param schoolYear 学年
	 * @param deptList	数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @return
	 */
	public Map<String,Object> getViolateOrPunishBySex(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,String code,String type);
	/**
	 * 获取违纪处分学生民族比例
	 * @param schoolYear 学年
	 * @param deptList	数据权限
	 * @param advancedList 高级查询参数
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @return
	 */
	public Map<String,Object> getViolateOrPunishByNation(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,String code,String type);
	/**
	 * 获取违纪处分学生 学科比例
	 * @param schoolYear
	 * @param deptList
	 * @param advancedList
	 * @param start_date
	 * @param end_date
	 * @param code
	 * @param type
	 * @return
	 */
	public Map<String,Object> getViolateOrPunishBySubject(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,String code,String type);
	
	/**
	 * 获取各种sql
	 * @param schoolYear 学年
	 * @param deptList 权限
	 * @param advancedList 高级参数
	 * @param start_date 开始时间
	 * @param end_date 结束时间
	 * @param code 参数编码
	 * @param type 违纪处分类型
	 * @param tag lab类型
	 * @return
	 */
	public String getSexGradeSubjectNationSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> advancedList, String start_date, String end_date,String code,String type,String tag);
}