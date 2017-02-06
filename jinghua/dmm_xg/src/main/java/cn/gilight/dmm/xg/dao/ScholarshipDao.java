package cn.gilight.dmm.xg.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.pojo.ScholarshipTop;

/**
 * 奖学金
 * 
 * @author xuebl
 * @date 2016年5月19日 下午6:00:15
 */
public interface ScholarshipDao {

	/**
	 * 获取奖学金sql
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param ship_schoolYear 获得奖学金学年
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return String (select t.* from t_stu_award t)
	 */
	public String getScholarshipSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, Constant.JCZD_Table table);

	/**
	 * 获取奖学金sql
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param ship_schoolYear 获得奖学金学年
	 * @param ship_codes 奖学金类型s
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return String (select t.* from t_stu_award t)
	 */
	public String getScholarshipSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList,
			Integer ship_schoolYear, String ship_codes, Constant.JCZD_Table table);
	
	/**
	 * 获取奖学金学生sql
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param ship_schoolYear 获得奖学金学年
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return String 
	 * <br> (select distinct(stu_id) no_ from t_stu_award t)
	 */
	public String getScholarshipStuIdSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, Constant.JCZD_Table table);

	/**
	 * 获取奖学金学生sql
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param ship_schoolYear 获得奖学金学年
	 * @param ship_codes 奖学金类型s
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return String 
	 * <br>  (select distinct(stu_id) no_ from t_stu_award t)
	 */
	public String getScholarshipStuIdSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, String ship_codes, Constant.JCZD_Table table);
	
	/**
	 * 查询摘要
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param ship_schoolYear 获得奖学金学年
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return Map<String,Object>
	 */
	public Map<String, Object> queryCountAndMoney(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, Constant.JCZD_Table table);

	/**
	 * 分奖学金类型 查询数据
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param ship_schoolYear 获得奖学金学年
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return List<Map<String,Object>>
	 * <br> [ {name:'',code:'',money:'',count:''}, {} ]
	 */
	public List<Map<String, Object>> queryTypeList(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, Constant.JCZD_Table table);

	/**
	 * 奖学金排行榜
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param ship_schoolYear 获得奖学金学年
	 * @param desc_column 倒序字段
	 * @param Constant.JCZD_Table 奖惩助贷
	 * @return List<ScholarshipTop>
	 */
	public List<ScholarshipTop> queryTopList(int schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList, 
			Integer ship_schoolYear, String desc_column, Constant.JCZD_Table table);
	
	/**
	 * 获得奖学金学生详情
	 * @param schoolYear 学年
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @param keyValue 详细参数
	 * @param fields 查询字段
	 * @return
	 */
	public String getStuDetailSql(Integer schoolYear,List<String> deptList, List<AdvancedParam> stuAdvancedList,Map<String, Object> keyValue, List<String> fields,Constant.JCZD_Table table);
	
}