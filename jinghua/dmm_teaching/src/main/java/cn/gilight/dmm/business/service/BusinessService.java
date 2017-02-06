package cn.gilight.dmm.business.service;

import java.util.List;
import java.util.Map;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.entity.TCode;

/**
 * 业务service
 * 
 * @author xuebl
 * @date 2016年5月3日 下午12:31:07
 */
public interface BusinessService {

	/**
	 * 获取修改密码的服务器地址
	 * @return String
	 */
	public String queryChangePasswdSystemUrl();
	
	/**
	 * 获取学校ID
	 * @return String
	 */
	public String getSchoolId();

	/**
	 * 获取学校 name
	 * @return String
	 */
	public String getSchoolName();

	/**
	 * 获取最大学制
	 * @return int
	 */
	public int getMaxSchooling();
	
	/**
	 * 获取学生工作者数据权限
	 * @param shiroTag 
	 * @return List<String>
	 */
//	public List<String> getDeptDataList(String shiroTag);
	
	/**
	 * 获取学生工作者数据权限
	 * @param shiroTag 
	 * @param id 节点
	 * @return List<String>
	 */
	public List<String> getDeptDataList(String shiroTag, String id);
	
	/**
	 * 判断是否有权限
	 * @param shiroTag
	 * @return boolean
	 */
	public boolean hasPermssion(String shiroTag);
	
	/**
	 * 封装这个节点的标准数据权限
	 * @param id
	 * @return List<String>
	 */
	public List<String> packageDeptListById(String id);

	/**
	 * 根据ID获取节点名称
	 * @param id
	 * @return String
	 */
	public String getDeptNameById(String id);
	
	/**
	 * 查询权限对应的名称（**学校、**院系）
	 * @param deptList 标准权限集合
	 * @param advancedParamList 高级查询参数
	 * @return String
	 */
	public String queryDeptDataName(List<String> deptList, List<AdvancedParam> advancedParamList);

	/**
	 * 获取节点的父节点ID
	 * @param deptId
	 * @return String
	 */
	public String getDeptPidById(String deptId);
	
	/**
	 * 获取下钻查询 数据sql
	 * @param deptList 标准权限
	 * @param pid 父节点
	 * <br> pid==null，按标准权限
	 * @param querySql 数据sql
	 * <br> 学生sql： select dept_id,major_id,class_id ... from t
	 * <br> 教职工sql： select dept_id ... from t
	 * @param isShowAllDept 是否显示这一层所有节点，没有业务数据的节点也显示
	 * @param isDeptTeach 是否教学组织机构
	 * @param isTeaSql 是否是教职工sql
	 * @param hasTeach 不包含教学单位
	 * @param schoolYear 学年
	 * @param grade 年级
	 * <br> 查询一个学年下的一个年级（大一...）的班级时需要传值
	 * @return String
	 * <br> "select *, next_dept_code, next_dept_name from t"
	 */
	public String getNextLevelDataSqlByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade);
	/**
	 * 获取下钻查询 数据分组sql
	 * @param deptList 标准权限
	 * @param pid 父节点
	 * <br> pid==null，按标准权限
	 * @param querySql 数据sql
	 * <br> 学生sql： select dept_id,major_id,class_id ... from t
	 * <br> 教职工sql： select dept_id ... from t
	 * @param isShowAllDept 是否显示这一层所有节点，没有业务数据的节点也显示
	 * @param isDeptTeach 是否教学组织机构
	 * @param isTeaSql 是教职工sql
	 * @param hasTeach 包含教学单位
	 * @param schoolYear 学年
	 * @param grade 年级
	 * <br> 查询一个学年下的一个年级（大一...）的班级时需要传值
	 * @return String
	 * <br> "select next_dept_code, next_dept_name, count(0) next_dept_count from t group by next_dept_code, next_dept_name"
	 */
	public String getNextLevelGroupSqlByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade);
	/**
	 * 获取下级节点 排序sql
	 * @param deptList 标准权限
	 * @param pid 父节点
	 * <br> pid==null，按标准权限
	 * @param querySql 数据sql
	 * <br> 学生sql： select dept_id,major_id,class_id ... from t
	 * <br> 教职工sql： select dept_id ... from t
	 * @param isShowAllDept 是否显示这一层所有节点，没有业务数据的节点也显示
	 * @param isDeptTeach 是否教学组织机构
	 * @param isTeaSql 是教职工sql
	 * @param hasTeach 包含教学单位
	 * @param schoolYear 学年
	 * @param grade 年级
	 * <br> 查询一个学年下的一个年级（大一...）的班级时需要传值
	 * @return String
	 * <br> "select id, name_, path_, pid, next_dept_order from t_code_dept/t_code_dept_teach"
	 */
	public String getNextLevelOrderSqlByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade);
	/**
	 * 获取下钻查询数据sql数组（数据sql，分组sql，排序sql）
	 * @param deptList 标准权限
	 * @param pid 父节点
	 * <br> pid==null，按标准权限
	 * @param querySql 数据sql
	 * <br> 学生sql： select dept_id,major_id,class_id ... from t
	 * <br> 教职工sql： select dept_id ... from t
	 * @param isShowAllDept 是否显示这一层所有节点，没有业务数据的节点也显示
	 * @param isDeptTeach 是否教学组织机构
	 * @param isTeaSql 是教职工sql
	 * @param hasTeach 包含教学单位
	 * @param schoolYear 学年
	 * @param grade 年级
	 * <br> 查询一个学年下的一个年级（大一...）的班级时需要传值
	 * @return String
	 * <br> ["select *, next_dept_code, next_dept_name from t",
	 * <br>	"select next_dept_code, next_dept_name, count(0) next_dept_count from t group by next_dept_code, next_dept_name",
	 * <br>	"select id, name_, path_, pid, next_dept_order from t_code_dept/t_code_dept_teach"]
	 */
	public String[] getNextLevelSqlAryByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade);
	
	/**
	 * 获取下钻查询数据分组（根据机构ID，机构名称）
	 * @param sql 数据sql，包含字段 next_dept_code,next_dept_name
	 * @return String
	 * <br> "select next_dept_code, next_dept_name, count(0) next_dept_count from t group by next_dept_code, next_dept_name"
	 */
	public String getNextLevelGroupSql(String sql);
	/**
	 * 获取下钻查询分组sql
	 * @param groupSql 数据分组sql （eg：select next_dept_code, next_dept_name, next_dept_count from t）
	 * @param orderSql 排序机构sql （eg：select id, name_, path_, pid, next_dept_order from t_code_dept/t_code_dept_teach）
	 * @param isShowAllDept 是否显示所有机构 （左右连接）
	 * @return String
	 */
	public String getNextLevelGroupOrderSql(String groupSql, String orderSql, boolean isShowAllDept);
	
	/**
	 * 获取下钻查询的组织机构列表（行政机构）
	 * @param pid 父节点
	 * @param shiroTag
	 * @param schoolYear
	 * @return List<Map<String,Object>>
	 * <br> [{id:'', name:'', pid:'', level_type:''}]
	 * @deprecated 这里返回的只是下级节点，再循环查询太慢，固不推荐使用  20161230
	 */
	public List<Map<String, Object>> getDeptDataListForGoingDown(List<String> deptList, String pid, Integer schoolYear);
	/**
	 * 获取下钻查询的组织机构列表（行政机构）
	 * @param pid 父节点
	 * @param shiroTag
	 * @param schoolYear
	 * @return List<Map<String,Object>>
	 * <br> [{id:'', name:'', pid:'', level_type:''}]
	 * @deprecated 这里返回的只是下级节点，再循环查询太慢，固不推荐使用  20161230
	 */
	public Map<String, Object> getDeptDataForGoingDown(List<String> deptList, String pid, Integer schoolYear);
	
	/**
	 * 获取下钻查询的组织机构列表（学生单位）（不包括教学单位level_type!='JXDW'）
	 * @param pid 父节点
	 * @param shiroTag
	 * @param schoolYear
	 * @return List<Map<String,Object>>
	 * <br> [{id:'', name:'', pid:'', level_type:''}]
	 * @deprecated 这里返回的只是下级节点，再循环查询太慢，固不推荐使用  20161230
	 */
	public List<Map<String, Object>> getDeptDataListForGoingDownStu(List<String> deptList, String pid, Integer schoolYear);
	/**
	 * 获取下钻查询的数据（学生单位）（不包括教学单位level_type!='JXDW'）
	 * @param pid 父节点
	 * @param shiroTag 
	 * @param schoolYear
	 * @return Map<String,Object>
	 * <br> {deptList:[], queryList:[], level_type_pid:''}
	 * @deprecated 这里返回的只是下级节点，再循环查询太慢，固不推荐使用  20161230
	 */
	public Map<String, Object> getDeptDataForGoingDownStu(List<String> deptList, String pid, Integer schoolYear);

	/**
	 * 获取下钻查询的组织机构列表（包括教学单位）
	 * @param pid 父节点
	 * @param shiroTag
	 * @param schoolYear
	 * @return List<Map<String,Object>>
	 * <br> [{id:'', name:'', pid:'', level_type:''}]
	 * @deprecated 这里返回的只是下级节点，再循环查询太慢，固不推荐使用  20161230
	 */
	public List<Map<String, Object>> getDeptDataListForGoingDownTeach(List<String> deptList, String pid, Integer schoolYear);
	/**
	 * 获取下钻查询的数据（包括教学单位）
	 * @param pid 父节点
	 * @param shiroTag 
	 * @param schoolYear
	 * @return Map<String,Object>
	 * <br> {deptList:[], queryList:[], level_type_pid:''}
	 * @deprecated 这里返回的只是下级节点，再循环查询太慢，固不推荐使用  20161230
	 */
	public Map<String, Object> getDeptDataForGoingDownTeach(List<String> deptList, String pid, Integer schoolYear);
	
	/**
	 * 获取学校开始时间（上课时间）
	 * @param schoolYear 学年
	 * @param term 学期
	 * @return String
	 */
	public String getSchoolStartDate(String schoolYear, String term);
	
	/**
	 * 标准代码
	 * @param codeType 代码类型
	 * @return List<TCode>
	 */
	public List<TCode> queryBzdmList(String codeType);

	/**
	 * 标准代码
	 * @param codeType 代码类型
	 * @param codes 代码（null查询所有）
	 * @return List<TCode>
	 */
	public List<TCode> queryBzdmList(String codeType, String codes);
	
	/**
	 * 标准代码
	 * @param codeType 代码类型
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmListMap(String codeType);

	/**
	 * 标准代码-名称
	 * @param codeType 代码类型
	 * @param codes 代码（null查询所有）
	 * @return List<String> [name1, name2]
	 */
	public List<String> queryBzdmNameList(String codeType, String codes);

	/**
	 * 标准代码-代码
	 * @param codeType 代码类型
	 * @return List<String> [code1, code2]
	 */
	public List<String> queryBzdmCodeList(String codeType);

	/**
	 * 查询标准代码（树形表）
	 * @param tableName 表名
	 * @param level_ 层次（null查询所有）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmListMapInTree(String tableName, Integer level_);
	
	/**
	 * 标准代码-学工口-学生学历标准代码（本/专、本、专）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmStuEducationList();

	/**
	 * 标准代码-学年（默认近10个学年）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmSchoolYear();
	/**
	 * 标准代码-学年、学期（默认近10个学年）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmXnXq();
	/**
	 * 标准代码-学年（默认近5个学年）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmSchoolYear5();
	/**
	 * 标准代码-学年、学期（默认近5个学年）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmXnXq5();
	/**
	 * 获取成绩表学年学期编码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmScoreXnXq();
	/**
	 * 获取挂科表学年学期编码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmGkXnXq();	
	/**
	 * 标准代码-学年（表中实际学年数据，当前学年 到 最早学年）
	 * @param table 表名
	 * @param column_schoolYear 列名
	 */
	public List<Map<String, Object>> queryBzdmSchoolYear(String table, String column_schoolYear);

	/**
	 * 标准代码-学年学期（表中实际学年数据，当前学年 到 最早学年，当前学期数据）
	 * @param table 表名
	 * @param column_schoolYear 列名
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmXnXq(String table, String column_schoolYear);
	
	/**
	 * 标准代码-学期
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmTermCode();
	
	/**
	 * 标准代码-年级
	 * @return List<Map<String,Object>>
	 * <br> [ {mc:'一年级', id:1}, {} ]
	 */
	public List<Map<String, Object>> queryBzdmNj();
	/**
	 * 标准代码-性别
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmSex();
	/**
	 * 标准代码-奖学金类型
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmScholarship();
	/**
	 * 标准代码-查询学科专业门类（系统使用中的）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmSubjectDegree();
	/**
	 * 标准代码-学科属性（系统使用中的）；eg：必须、限选、任选
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmCourseAttr();
	/**
	 * 获取成绩表学年编码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmScoreXn();
	/**
	 * 标准代码-学科性质（系统使用中的）；eg：公共基础课、学科基础课、专业课
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmCourseNature();
	/**
	 * 标准代码-成绩类型（平均数、中位数、众数、方差、标准差）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmScoreType();
	/**
	 * 标准代码-成绩指标（平均数、中位数、众数、方差、标准差、优秀率、挂科率、重修率、低于平均数）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmScoreTarget();
	/**
	 * 标准代码-学历
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmTeaEducationList();
	/**
	 * 标准代码-学位
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmDegree();
	 /**
     * 根据标准权限查询当前所有的就读学历（应该展示的就读学历集合，以queryBzdmStuEducationList()为基础）
     * @param deptList
     * @return
     */
	public List<Map<String, Object>> queryBzdmStuEducationList(List<String> deptList);

	/**
     * 根据id查询level_
     * @param id
     * @return Integer
     */
	public Integer getLevelById(String id);
	/**
	 * 根据id查询level_type
	 * @param id
	 * @return String
	 */
	public String getLevelTypeById(String id);
	/**
     * 根据id查询level_type对应的名称
     * @param id
     * @return String
     */
	public String getLevelNameById(String id);
    /**
     * 根据pid查询level_type对应的名称
     * @param pid
     * @return String
     */
	public String getLevelNameByPid(String pid);
    /**
     * 根据level_type查询对应的名称
     * @param levelType
     * @return String
     */
	public String getLevelNameByLevelType(String levelType);
    /**
     * 查询学制编码集合（根据所有学生）
     * @return List<Map<String, Object>> 
     */
	public List<Map<String, Object>> queryBzdmLengthSchooling();
    /**
     * 查询能用的标准代码（实际中学校存在过的，仅适用于t_code中的代码）
     * @param group 查询的代码是学生表还是教师表 学生 :"stu",教师:"tea"
     * @param column 列名
     * @param codeType 代码类型  
     * @param orderType 排序类型 如果按人数排序  orderType = "count";
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryBzdmByTCodeIsUse(String group, String column,
			String codeType, String orderType);
	/**
	 * 查询标准代码（树形表）
	 * @param tableName 表名
	 * @param pid 父节点（null查询所有）
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmListMapInTreeByPid(String tableName,
			String pid);
    /**
     * 查询教学职称编码集合
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryBzdmZyjszwList();
	 /**
     * 查询专业技术职务级别编码集合
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryBzdmZyjszwJbList();
    /**
     * 查询教职工类型编码集合
     * @return
     */
	public List<Map<String, Object>> queryBzdmTeaType();
	/**
     * 查询教职工来源编码集合
     * @return
     */
	public List<Map<String, Object>> queryBzdmTeaSource();
    /**
     * 查询教职工是否是双师教师
     * @return
     */
	public List<Map<String, Object>> queryBzdmSsjsList();
    /**
     * 查询教师学历标准代码集合
     * @return
     */
	public List<Map<String, Object>> queryBzdmTeaEduList();
	/**
     * 查询教师学位标准代码集合
     * @return
     */
	public List<Map<String, Object>> queryBzdmTeaDegreeList();
	/**
     * 查询学生就读学历标准代码集合
     * @return
     */
	public List<Map<String, Object>> queryBzdmStuEduList();
    /**
     * 根据分组判断是用来刷筛选学生还是教师，然后查询出应该显示的学科
     * @param group 教师:'tea',学生:'stu'
     * @return
     */
	public List<Map<String, Object>> queryBzdmSubjectList(String group);
    /**
     * 根据学生sql查询应该展示的学历集合
     * @param stuSql 学生sql
     * @return List<Map<String, Object>>
     */
	public List<Map<String, Object>> queryBzdmStuEducationList(String stuSql);
    /**
     * 获取生源地id,如果有一个生源地的学生数大于一定比例,返回该生源地id,否则返回null
     * @return String
     */
	public String getOriginIdByAbsoluteScale();
	
}