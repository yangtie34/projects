package cn.gilight.dmm.business.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.entity.TCode;
import cn.gilight.dmm.business.entity.TreeCode;

/**
 * 业务Dao
 * 
 * @author xuebl
 * @date 2016年4月28日 下午6:58:23
 */
public interface BusinessDao {

	/**
	 * 查询学校数据
	 */
	public Map<String, Object> querySchoolData();

	/**
	 * 查询最大学制
	 * @return int
	 */
	public int queryMaxSchooling();
	
	/**
	 * 查询标准代码
	 * @param codeType 代码类型
	 * @param codes 代码 （null查询所有）
	 * @return List<TCode>
	 */
	public List<TCode> queryBzdmList(String codeType, String codes);

	/**
	 * 查询标准代码（树形表）
	 * @param tableName 表名
	 * @param level_ 层次（null查询所有）
	 * @return List<TreeCode>
	 */
	public List<TreeCode> queryBzdmListInTree(String tableName, Integer level_);
	
	/**
	 * 根据Ids 查询组织机构
	 * @param ids
	 * @return List<Map<String,Object>>
	 * <br>[{id:'', name:'', pid:'', level_type:'', level_:''}]
	 */
	public List<Map<String, Object>> queryLevelListByIds(String ids);
	/**
	 * 根据Id 查询组织机构
	 * @param id
	 * @return Map<String,Object>
	 * <br>{id:'', name:'', pid:'', level_type:'', level_:''}
	 */
	public Map<String, Object> queryLevelById(String id);

	/**
	 * 获取下一级行政组织机构ID
	 * @param pid 父节点Id
	 * @param schoolYear 学年;eg:2015
	 * @return List<String>
	 * <br>[id, id]
	 */
	public List<String> queryNextLevelIdList(String pid, Integer schoolYear);

	/**
	 * 获取下一级行政组织机构
	 * @param pid 父节点Id
	 * @param schoolYear 学年;eg:2015
	 * @return List<Map<String,Object>>
	 * <br>[{id:'', name:'', pid:'', level_type:'', level_:''}]
	 */
	public List<Map<String, Object>> queryNextLevelList(String pid, Integer schoolYear);

	/**
	 * 获取下一级教学组织机构（学生单位）ID（不包括教学单位level_type!='JXDW'）
	 * @param pid 父节点Id
	 * @param schoolYear 学年;eg:2015
	 * @return List<String>
	 * <br>[id, id]
	 */
	public List<String> queryNextLevelTeachStuIdList(String pid, Integer schoolYear);
	/**
	 * 获取下一级教学组织机构（学生单位）（不包括教学单位level_type!='JXDW'）
	 * @param pid 父节点Id
	 * @param schoolYear 学年;eg:2015
	 * @return List<Map<String,Object>> 
	 * <br>[{id:'', name:'', pid:'', level_type:'', level_:''}]
	 */
	public List<Map<String, Object>> queryNextLevelTeachStuList(String pid, Integer schoolYear);

	/**
	 * 获取下一级教学组织机构（教学单位）ID
	 * @param pid 父节点Id
	 * @param schoolYear 学年;eg:2015
	 * @return List<String>
	 * <br>[id, id]
	 */
	public List<String> queryNextLevelTeachTeachIdList(String pid, Integer schoolYear);
	/**
	 * 获取下一级教学组织机构（教学单位）
	 * @param pid 父节点Id
	 * @param schoolYear 学年;eg:2015
	 * @return List<Map<String,Object>> 
	 * <br>[{id:'', name:'', pid:'', level_type:'', level_:''}]
	 */
	public List<Map<String, Object>> queryNextLevelTeachTeachList(String pid, Integer schoolYear);
	
	/**
	 * 获取 行政组织机构 权限匹配sql
	 * @param deptList 标准权限
	 * @return String 
	 * <br>（返回id； select id from t_code_dept_teach）
	 */
	public String getDeptIdSqlByDeptList(List<String> deptList);
	/**
	 * 获取 行政组织机构 权限匹配sql
	 * @param deptIds 组织机构Ids
	 * @return String 
	 * <br>（返回id； select id from t_code_dept_teach）
	 */
	public String getDeptIdSqlByDeptIds(String deptIds);

	/**
	 * 获取 教学组织机构（学生单位）权限匹配sql（不包括教学单位level_type!='JXDW'）
	 * @param deptList 标准权限
	 * @return String 
	 * <br>（返回id； select id from t_code_dept_teach）
	 */
	public String getDeptTeachStuIdSqlByDeptList(List<String> deptList);
	/**
	 * 获取 教学组织机构（学生单位）权限匹配sql（不包括教学单位level_type!='JXDW'）
	 * @param deptIds 组织机构Ids
	 * @return String 
	 * <br>（返回id； select id from t_code_dept_teach）
	 */
	public String getDeptTeachStuIdSqlByDeptIds(String deptIds);

	/**
	 * 获取 教学组织机构（教学单位）权限匹配sql
	 * @param deptList 标准权限
	 * @return String 
	 * <br>（返回id； select id from t_code_dept_teach）
	 */
	public String getDeptTeachTeachIdSqlByDeptList(List<String> deptList);
	/**
	 * 获取 教学组织机构（教学单位）权限匹配sql
	 * @param deptIds 组织机构Ids
	 * @return String 
	 * <br>（返回id； select id from t_code_dept_teach）
	 */
	public String getDeptTeachTeachIdSqlByDeptIds(String deptIds);
	
	
	/**
	 * 获取 所有组织机构 权限匹配sql
	 * @param deptList 标准权限
	 * @return String 
	 * <br>（返回id；select distinct(id) as id from (select id from t_code_dept_teach union select id from t_code_dept) ）
	 */
	public String getDeptAllIdSqlByDeptList(List<String> deptList);

	/**
	 * 获取 所有组织机构 权限匹配sql
	 * <br> ids==null 没有权限
	 * @param deptIds 组织机构Ids
	 * @return String
	 * <br>（返回id；select distinct(id) as id from (select id from t_code_dept_teach union select id from t_code_dept) ）
	 */
	public String getDeptAllIdSqlByDeptIds(String deptIds);
	
	/**
	 * 获取下一级班级List（哪个学年在校的班级）（按名称排序）
	 * @param pid 父节点Id
	 * @param schoolYear 学年
	 * @return List<Map<String,Object>>
	 * <br>[{id:'', name:'', pid:'', level_type:''}]
	 */
	public List<Map<String, Object>> queryClassesListByPid(String pid, Integer schoolYear);

	/**
	 * 获取 行政班 权限匹配sql（根据标准权限）
	 * @param deptList
	 * @return String （返回no；select distinct(no_) no from t_classes where...）
	 */
	public String getClassesIdSqlByDeptList(List<String> deptList);

	/**
	 * 获取 行政班 权限匹配sql（根据标准权限）
	 * @param deptList 标准权限集合
	 * @param schoolYear 学年
	 * @return String
	 */
	public String getClassesIdSqlByDeptList(List<String> deptList, Integer schoolYear);
	
	/**
	 * 获取 行政班 权限匹配sql（根据机构Ids）
	 * @param deptIds 组织机构Ids；null:全部
	 * @return String （返回no；select distinct(no_) no from t_classes where...）
	 */
	public String getClassesIdSqlByDeptIds(String deptIds);

	/**
	 * 获取标准权限下的 院系
	 * @param deptList 标准权限集合
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryYxList(List<String> deptList);
	
	/**
	 * 获取标准权限下的 专业
	 * @param deptList 标准权限集合
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryZyListStu(List<String> deptList);
	/**
	 * 获取标准权限和有学生的专业
	 * @param deptList 标准权限
	 * @param stuSql 学生
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryZyListStu(List<String> deptList, String stuSql);
    /**
     * 根据标准权限查询专业sql
     * @param deptList 标准权限
     * @return String 
     */
	public String getZyIdSqlByDeptListStu(List<String> deptList);
	/**
     * 根据标准权限查询专业sql
     * @param deptList 标准权限
     * @param stuSql 学生
     * @return String 
     */
	public String getZyIdSqlByDeptListStu(List<String> deptList, String stuSql);
	/**
	 * 
	 * @param deptList
	 * @param schoolYear
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBjList(List<String> deptList, Integer schoolYear);
	
	/**
	 * 某一学年在籍的学生人数
	 * @param schoolYear 学年；eg：2015
	 * @param deptList 标准权限集合
	 * @param advancedList 高级查询参数集合
	 * @return int 数量
	 */
	public int queryStuCount(int schoolYear, List<String> deptList, List<AdvancedParam> advancedList);

	/**
	 * 某学年在籍学生数据sql
	 * @param schoolYear 学年（null:不再过滤这个学年的在校生）；eg：2015
	 * @param deptList 标准权限集合
	 * @param stuAdvancedList 高级查询参数集合
	 * @return String sql （select t.* from t_stu t where ...）
	 */
	public String getStuSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList);

	/**
	 * 某学年在籍学生数据sql
	 * @param deptList 标准权限集合
	 * @param stuAdvancedList 高级查询参数集合
	 * @return String sql （select t.* from t_stu t where ...）
	 */
	public String getStuSql(List<String> deptList, List<AdvancedParam> stuAdvancedList);

	/**
	 * 获取学生 详情sql
	 * @param deptList
	 * @param stuAdvancedList
	 * [学号,姓名,性别,性别id,院系,院系id,专业,专业id,班级,班级id,入学年级,入学年级id,学制,省,省id,市,市id,县,县id] <br>
	 * （select t.id,t.no_ no,t.name_ name, sex.name_ sexmc, sex.code_ sexid, dept.name_ deptmc, t.dept_id deptid,
		major.name_ majormc, t.major_id majorid, class.name_ classmc, t.class_id,t.enroll_grade,t.length_schooling,
		xzqh.sname shengmc, xzqh.sid shengid, shi.sname shimc, shi.sid shiid, shi.name xianmc, shi.id xianid from t_stu t）
	 * @return String
	 */
	public String getStuDetailSql(List<String> deptList, List<AdvancedParam> stuAdvancedList);
	/**
	 * 获取学生 详情sql
	 * @param String stuSql <br>
	 * [学号,姓名,性别,性别id,院系,院系id,专业,专业id,班级,班级id,入学年级,入学年级id,学制,省,省id,市,市id,县,县id] <br>
	 * （select t.id,t.no_ no,t.name_ name, sex.name_ sexmc, sex.code_ sexid, dept.name_ deptmc, t.dept_id deptid,
		major.name_ majormc, t.major_id majorid, class.name_ classmc, t.class_id,t.enroll_grade,t.length_schooling,
		xzqh.sname shengmc, xzqh.sid shengid, shi.sname shimc, shi.sid shiid, shi.name xianmc, shi.id xianid from t_stu t）
	 * @return String
	 */
	public String getStuDetailSql(String stuSql);
	
	/**
	 * 获取教职工sql
	 * <br> 如果标准权限中没有教职工状态，则默认查询在职
	 * @param deptList 标准权限集合
	 * @param teaAdvancedList 高级查询参数集合
	 * @return String sql （select t.* from t_tea t where ...）
	 */
	public String getTeaSql(List<String> deptList, List<AdvancedParam> teaAdvancedList);

	/**
	 * 获取教职工 详情sql
	 * <br> 如果标准权限中没有教职工状态，则默认查询在职
	 * @param deptList 标准权限集合
	 * @param teaAdvancedList 高级查询参数集合
	 * @return String sql <br>
	 * （select t.tea_no, t.name_ name, dept.name_ deptmc, dept.id deptid, zc.name_ zcmc, zc.id zcid, 
		zcdj.name_ zcdj, zcdj.code_ zcdjid, edu.pname edumc, edu.pid eduid, deg.pname degreemc, deg.pid degreeid, 
		subject.pname subjectmc, subject.pid subjectic from t_tea where ...）
	 */
	public String getTeaDetailSql(List<String> deptList, List<AdvancedParam> teaAdvancedList);

	/**
	 * 获取教职工 详情sql
	 * <br> 如果标准权限中没有教职工状态，则默认查询在职
	 * @return String sql <br>
	 * [工号,姓名,部门,部门ID,职称,职称ID,职称等级,职称等级ID,学位,学位ID,学历,学历ID,学科,学科ID] <br>
	 * （select t.tea_no, t.name_ name, dept.name_ deptmc, dept.id deptid, zc.name_ zcmc, zc.id zcid, zcdj.name_ zcdjmc, zcdj.code_ zcdjid,
		edu.pname edumc, edu.pid eduid, deg.pname degreemc, deg.pid degreeid, subject.pname subjectmc, subject.pid subjectid from t_tea where ...）
	 */
	public String getTeaDetailSql(String teaSql);
	
	/**
	 * 格式化sql参数 加引号用于sql条件中的 in()
	 * <br> 1,2,4  ->  '1','2','4'
	 * @param values  1,2,4
	 * @return String  '1','2','4'
	 */
	public String formatInSql(String values);

	/**
	 * 格式化sql参数 解除sql条件中的引号 ''
	 * <br> '4'  ->  4
	 * @param values
	 * @return String
	 */
	public String formatOutSql(String values);
	
	/**
	 * 获取学校开始时间（上课时间）
	 * @param schoolYear 学年
	 * @param term 学期
	 * @return String
	 */
	public String querySchoolStartDate(String schoolYear, String term);
	
	/**
	 * 对学生sql数据进行 年级分组
	 * @param stuSql 学生sql（select * from table）
	 * @param schoolYear 学年（2015）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryGradeGroup(String stuSql, int schoolYear);
	
	/**
	 * 查询有用的学科专业门类
	 * @return List<Map<String,Object>>
	 * <br> [ {name:'',id:''}, {} ]
	 */
	public List<Map<String, Object>> querySubjectDegreeUsefulList();
	/**
	 * 查询有用的学科专业门类 sql
	 * @return String
	 */
	public String getSubjectDegreeUsefulSql();
	
	/**
	 * 查询一张表中的最小学年数据
	 * @param table 表名
	 * @param columnSchoolYear 学年字段
	 * @return Integer
	 */
	public Integer queryMinSchoolYear(String table, String columnSchoolYear);
	/**
	 * 查询一张表中的最小学年、学期数据
	 * @param table 表名
	 * @param columnSchoolYear 学年字段
	 * @param columnTermCode 学期字段
	 * @return String[2]
	 */
	public String[] queryMinSchoolYearTermCode(String table, String columnSchoolYear, String columnTermCode);

	/**
	 * 查询一张表中的最大学年数据
	 * @param table 表名
	 * @param columnSchoolYear 学年字段
	 * @return Integer
	 */
	public Integer queryMaxSchoolYear(String table, String columnSchoolYear);
	/**
	 * 查询一张表中的最大学年、学期数据
	 * @param table 表名
	 * @param columnSchoolYear 学年字段
	 * @param columnTermCode 学期字段
	 * @return String[]
	 */
	public String[] queryMaxSchoolYearTermCode(String table, String columnSchoolYear, String columnTermCode);
	
	/**
	 * 获取当前同宿舍学生sql
	 * @param stuIdSql 获取哪些学生的同宿舍学生（select no_ from t_stu）
	 * @param containsOwn 是否包含自身（学生sql中的学生）
	 * @return String
	 * <br> （select no_ from t_stu）
	 */
	public String getDormTogetherStuIdSql(String stuIdSql, boolean containsOwn);

	/**
	 * 获取学生成绩（第一次考试成绩）、学分 sql
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return String 
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScoreExamSql(String stuSql, String schoolYear, String termCode);
	
	/**
	 * 获取学生成绩（综合成绩）、学分 sql
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return String 
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScoreSql(String stuSql, String schoolYear, String termCode);
	/**
	 * 获取“优秀”学生 成绩、学分 sql
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return String
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScoreBetterSql(String stuSql, String schoolYear, String termCode);
	/**
	 * 获取“优秀”学生 成绩、学分 sql
	 * @param scoreSql 学生成绩sql
	 * @return String
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScoreBetterSql(String scoreSql);
	/**
	 * 获取“及格”学生 成绩、学分 sql
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return String
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScorePassSql(String stuSql, String schoolYear, String termCode);
	/**
	 * 获取“及格”学生 成绩、学分 sql
	 * @param scoreSql 学生成绩sql
	 * @return String
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScorePassSql(String scoreSql);
	/**
	 * 获取“不及格”学生 成绩、学分 sql
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return String
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScoreFailSql(String stuSql, String schoolYear, String termCode);
	/**
	 * 获取“不及格”学生 成绩、学分 sql
	 * @param scoreSql 学生成绩sql
	 * @return String
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScoreFailSql(String scoreSql);
	/**
	 * 获取“重修”学生 成绩、学分 sql
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return String
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScoreRebuildSql(String stuSql, String schoolYear, String termCode);
	/**
	 * 获取“重修”学生 成绩、学分 sql
	 * @param scoreSql 学生成绩sql
	 * @return String
	 * <br>{ select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * <br>COURSE_ATTR_CODE:'必选/限选/任选'，COURSE_NATURE_CODE:'公共基础课/学科基础课/专业课'
	 */
	public String getStuScoreRebuildSql(String scoreSql);
	
	/**
	 * 获取学生 绩点sql
	 * <br>目前是实时计算 20160630
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return String
	 * <br>(select gpa,stu_id,school_year,term_code from t)
	 */
	public String getStuGpaSql(String stuSql, String schoolYear, String termCode);

	/**
	 * 根据学生成绩sql 获取学生绩点sql
	 * @param scoreSql
	 * @return String
	 * <br>(select gpa,stu_id from t)
	 */
	public String getStuGpaSql(String scoreSql);
	
	/**
	 * 获取学生 绩点sql（从绩点结果表查数据）
	 * <br> 学年、学期参数为null时，where school_year is null and term_code is null
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param gpaCode 绩点ID
	 * @return String
	 * <br>(select gpa,stu_id,school_year,term_code from t)
	 */
	public String getStuGpaSql(String stuSql, String schoolYear, String termCode, String gpaCode);
	
	/**
	 * 获取学生 加权平均成绩sql（从加权平均成绩结果表查数据）
	 * <br> 学年、学期参数为null时，where school_year is null and term_code is null
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return String
	 * <br>(select gpa,stu_id,school_year,term_code from t)
	 */
	public String getStuScoreAvgSql(String stuSql, String schoolYear, String termCode);
	
	/**
	 * 获取学生加权平均成绩sql
	 * @param stuScoreSql 学生成绩sql
	 * <br> { select stu_id, coure_code, credit, score, COURSE_ATTR_CODE, COURSE_NATURE_CODE from t}
	 * @return String
	 * <br> { select stu_id, score from t }
	 */
	public String getStuScoreAvgSql(String stuScoreSql);
	
	/**
	 * 获取学生平均绩点（从绩点结果表查数据）
	 * @param stuSql
	 * @param schoolYear
	 * @param termCode
	 * @param gpaCode
	 * @return Double
	 */
	public Double queryStuGpaAvg(String stuSql, String schoolYear, String termCode, String gpaCode);
	
	/**
	 * 获取学生 绩点
	 * <br>目前是实时计算 20160630
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return List<Double>
	 */
	public List<Double> queryStuGpa(String stuSql, String schoolYear, String termCode);

	/**
	 * 获取学生 绩点（从绩点结果表查数据）
	 * @param stuSql 学生sql
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @param gpaCode
	 * @return List<Double>
	 */
	public List<Double> queryStuGpa(String stuSql, String schoolYear, String termCode, String gpaCode);
	
	/**
	 * 获取GPA的case when sql
	 * @return String
	 * <br> case when ...... end
	 */
	public String getStuGpaCaseWhenSql();
	
	/**
	 * 某一类学生的平均绩点
	 * @param stuSql 学生sql（为空时默认查询所有学生）（select * from t_stu）
	 * @param schoolYear 学年（为空时默认查询所有学年）
	 * @param termCode 学期 （为空时默认查询所有学期）
	 * @return Double
	 */
	public Double queryAvgScore(String stuSql, String schoolYear,String termCode);
	
	/**
	 * 某一类学生(月)平均借阅图书本数
	 * @param stuSql 学生sql（为空时默认查询所有学生）（select * from t_stu）
	 * @param schoolYear 学年（为空时默认查询所有学年）
	 * @param termCode 学期 （为空时默认查询所有学期）
	 * @return Double
	 */
	public Double queryAvgBookCount(String stuSql, String schoolYear, String termCode);
	
	/**
	 * 某一类学生(月)平均出入图书馆次数
	 * @param stuSql 学生sql（为空时默认查询所有学生）（select * from t_stu）
	 * @param schoolYear 学年（为空时默认查询所有学年）
	 * @param termCode 学期 （为空时默认查询所有学期）
	 * @return Double
	 */
	public Double queryAvgBookRke(String stuSql, String schoolYear, String termCode);
	
	/**
	 * 某一类学生(月)平均早餐次数
	 * @param stuSql 学生sql（为空时默认查询所有学生）（select * from t_stu）
	 * @param schoolYear 学年（为空时默认查询所有学年）
	 * @param termCode 学期 （为空时默认查询所有学期）
	 * @return Double
	 */
	public Double queryAvgBreakfastCount(String stuSql, String schoolYear, String termCode);
	
	/**
	 * 某一类学生(月)平均早起次数
	 * @param stuSql 学生sql（为空时默认查询所有学生）（select * from t_stu）
	 * @param schoolYear 学年（为空时默认查询所有学年）
	 * @param termCode 学期 （为空时默认查询所有学期）
	 * @return Double
	 */
	public Double queryAgeEarlyCount(String stuSql, String schoolYear, String termCode);

	/**
	 * 获取教学班ID sql
	 * @param stuSql（select * from t_stu）
	 * @return String
	 * <br> select id from t
	 */
	public String getTeachClassIdSql(String stuSql);

	/**
	 * 获取教学班内的学生 sql
	 * @param teachClassIdSql 教学班ID sql(select id from t)
	 * @param stuSql 权限学生sql（select * from t_stu）
	 * @return String
	 * <br> select * from t_stu
	 */
	public String getStuSqlByTeachClassSql(String teachClassIdSql, String stuSql);

	/**
	 * 查询教师所带学生sql
	 * @param teachId 教职工ID
	 * @param stuSql 学生sql
	 * @return String
	 * <br> select * from t_stu
	 */
	public String getStuSqlByTeachIdSql(String teachId, String stuSql);
    /**
     * 查询所有的就读学历
     * @String stuSql 学生sql
     * @return List<Map<String, Object>> 
     */
	public List<Map<String, Object>> queryEduList(String stuSql);
	/**
     * 根据标准权限查询院系sql
     * @param deptList 标准权限
     * @return String 
     */
	public String getYxIdSqlByDeptList(List<String> deptList);
    /**
     * 查询所有学生中所有的学制
     * @return List<String>
     */
	public List<String> queryLengthSchoolingByAllStu();
    /**
     * 查询tableSql中column这个列的所有值,结果按照数量排序
     * @param tableSql 表sql
     * @param column 列
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryBzdmBySqlOrderByCount(String tableSql, String column);
    /**
     * 查询tableSql中column这个列的所有值,
     * @param tableSql 表sql
     * @param column 列
     * @param order 排序    "count":按数量,"other":按编码  
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryBzdmBySql(String tableSql, String column,
			String order);
	/**
	 * 查询标准代码（树形表）
	 * @param tableName 表名
	 * @param pid 父节点（null查询所有）
	 * @return List<TreeCode>
	 */
	public List<TreeCode> queryBzdmListInTreeByPid(String tableName, String pid);
	/**
	 * 查询有用的学科专业门类（通过teaSql过滤）
	 * @return List<Map<String,Object>>
	 * <br> [ {name:'',id:''}, {} ]
	 */
	public List<Map<String, Object>> queryBzdmTeaSubjectIsUseList();
	/**
	 * 查询有用的学科专业门类 sql（通过teaSql过滤）
	 * @return String
	 */
	public String queryBzdmTeaSubjectIsUseSql();
	/**
     * 根据stuSql查询学生最多的省或直辖市的id和学生数以及所有的学生数
     * @param stuSql 学生sql
     * @return Map<String, Object> id:省或直辖市id,value:该地区学生数,all:所有学生数 
     */
	public Map<String, Object> queryOriginMaxCountByStuSql(String stuSql);
	/**
     * 根据生源地id查询该节点下所有节点数据sql（包括该节点本身）
     * @param id 生源地id
     * @return String
     */
	public String queryOriginIdsById(String id);

	/**
	 * 根据学年，标准权限，高级查询参数获取研究生sql
	 * @param schoolYear 学年 eg:2015
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return String
	 */
	public String getGraduateStuSql(Integer schoolYear, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);

	/**
	 * 根据标准权限，高级查询参数获取研究生sql
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return String
	 */
	public String getGraduateStuSql(List<String> deptList,
			List<AdvancedParam> stuAdvancedList);

	/**
	 * 根据学年，标准权限，高级查询参数获取新生sql
	 * @param schoolYear 学年
	 * @param deptList 标准权限 
	 * @param stuAdvancedList 高级查询参数
	 * @return String
	 */
	public String getNewStuSql(Integer schoolYear, List<String> deptList,
			List<AdvancedParam> stuAdvancedList);

	/**
	 * 根据标准权限，高级查询参数获取新生sql
	 * @param deptList 标准权限
	 * @param stuAdvancedList 高级查询参数
	 * @return String
	 */
	public String getNewStuSql(List<String> deptList,
			List<AdvancedParam> stuAdvancedList);
	
}