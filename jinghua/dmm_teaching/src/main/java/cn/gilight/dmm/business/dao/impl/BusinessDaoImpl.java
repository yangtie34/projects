package cn.gilight.dmm.business.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.entity.TCode;
import cn.gilight.dmm.business.entity.TreeCode;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.entity.TStuGpa;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.Globals;

/**
 * 学生工作者数据查询
 * 
 * @author xuebl
 * @date 2016年4月19日 下午5:14:24
 */
@Repository("businessDao")
public class BusinessDaoImpl implements BusinessDao  {

	@Resource
	private BaseDao baseDao;
	

	/**
	 * 获取学校数据sql
	 */
	private String getSchoolSql = "select t.id,t.name_ name from t_code_dept t where t.istrue = 1 and t.pid = '-1'";
	
	@Override
	public Map<String, Object> querySchoolData(){
		return baseDao.queryMapInLowerKey(getSchoolSql);
	}

	@Override
	public int queryMaxSchooling(){
		String max_schooling = baseDao.queryForString("select max(distinct(t.length_schooling)) from t_stu t");
		return (max_schooling==null||max_schooling.equals("")) ? 0 : Integer.valueOf(max_schooling);
	}

	@Override
	public List<TCode> queryBzdmList(String codeType, String codes){
		List<String> mathList = new ArrayList<>();
		mathList.add("t.istrue=1");
		mathList.add("t.code_type='"+codeType+"'");
		if(codes!=null) mathList.add("t.code_ in("+formatInSql(codes)+")");
		String mathSql = mathList.isEmpty() ? "" : (" where "+ListUtils.join(mathList, " and ")),
			   sql     = "select t.* from t_code t "+mathSql+" order by t.order_,t.code_";
		return baseDao.queryForListBean(sql, TCode.class);
	}
	@Override
	public List<Map<String,Object>> queryEduList(String stuSql){
		String sql = "select stu.edu_id as code,edu.order_ as pxh,edu.name_ as name  from ("
				+ stuSql
				+ ") stu inner join t_code_education edu "
				+ " on stu.edu_id = edu.id group by stu.edu_id,edu.order_,edu.name_ order by edu.order_,stu.edu_id ";
		return  baseDao.queryListInLowerKey(sql);
	}
	@Override
	public List<TreeCode> queryBzdmListInTree(String tableName, Integer level_){
		List<String> mathList = new ArrayList<>();
		mathList.add("t.istrue=1");
		if(level_!=null) mathList.add("t.level_="+level_);
		String mathSql = mathList.isEmpty() ? "" : (" where "+ListUtils.join(mathList, " and "));
		String sql = "select * from "+tableName+" t "+mathSql+" order by t.level_,t.pid,t.order_,t.code_";
		return baseDao.queryForListBean(sql, TreeCode.class);
	}

	@Override
	public List<Map<String, Object>> queryLevelListByIds(String ids){
		String pattern = "select t.id,t.name_ name,t.pid,t.level_type,t.level_",
			   deptSql = getDeptSqlByDeptIdsAndTable(ids, Constant.TABLE_T_Code_Dept, null),
			   deptTeachSql = getDeptSqlByDeptIdsAndTable(ids, Constant.TABLE_T_Code_Dept_Teach, null),
			   sql = pattern+" from("+deptSql+")t union " + pattern+" from("+deptTeachSql+")t"; // deptSql中的字符引号会被格式化掉
		sql = "select * from ("+sql+")t where t.id in("+formatInSql(ids)+")";
		return baseDao.queryListInLowerKey(sql);
	}
    @Override 
	public Map<String,Object> queryLevelById(String id){
    	List<Map<String, Object>> list = queryLevelListByIds(id);
		return list.isEmpty() ? null : list.get(0);
	}
	@Override
	public List<String> queryNextLevelIdList(String pid, Integer schoolYear){
		return ListUtils.getListValueFromListMap(queryNextLevelList(pid, schoolYear), "id");
	}

	@Override
	public List<Map<String, Object>> queryNextLevelList(String pid, Integer schoolYear){
		return queryNextLevelList(pid, schoolYear, Constant.TABLE_T_Code_Dept, null);
	}

	@Override
	public List<String> queryNextLevelTeachStuIdList(String pid, Integer schoolYear){
		return ListUtils.getListValueFromListMap(queryNextLevelTeachStuList(pid, schoolYear), "id");
	}
	@Override
	public List<Map<String, Object>> queryNextLevelTeachStuList(String pid, Integer schoolYear) {
		List<String> li = new ArrayList<>(); li.add(" t.level_type != '"+Constant.Level_Type_JXDW+"'");
		return queryNextLevelList(pid, schoolYear, Constant.TABLE_T_Code_Dept_Teach, li);
	}
	
	@Override
	public List<String> queryNextLevelTeachTeachIdList(String pid, Integer schoolYear){
		return ListUtils.getListValueFromListMap(queryNextLevelTeachTeachList(pid, schoolYear), "id");
	}
	@Override
	public List<Map<String, Object>> queryNextLevelTeachTeachList(String pid, Integer schoolYear) {
		return queryNextLevelList(pid, schoolYear, Constant.TABLE_T_Code_Dept_Teach, null);
	}

	/**
	 * 获取下一级机构 List
	 * @param pid 父节点Id
	 * @param tableName 表名["t_code_dept_teach" || "t_code_dept"]
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> queryNextLevelList(String pid, Integer schoolYear, String tableName, List<String> mathList) {
		String deptSql = getDeptSqlByDeptIdsAndTable(pid, tableName, mathList),
			   sql     = "select t.id,t.name_ name,t.pid, t.level_type,t.level_ from (" +deptSql+ ")t where t.pid='"+pid+"' order by t.order_,t.code_";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return !list.isEmpty() ? list : queryClassesListByPid(pid, schoolYear);
	}


	@Override
	public String getDeptIdSqlByDeptList(List<String> deptList){
		String deptIds = getDeptIdsFromDeptList(deptList);
		return getDeptIdSqlByDeptIds(deptIds);
	}
	@Override
	public String getDeptIdSqlByDeptIds(String deptIds){
		return getDeptIdSqlByDeptIdsAndTable(deptIds, Constant.TABLE_T_Code_Dept, null);
	}
	
	@Override
	public String getDeptTeachStuIdSqlByDeptList(List<String> deptList){
		String deptIds = getDeptIdsFromDeptList(deptList);
		return getDeptTeachStuIdSqlByDeptIds(deptIds);
	}
	@Override
	public String getDeptTeachStuIdSqlByDeptIds(String deptIds){
		List<String> li = new ArrayList<>(); li.add(" t.level_type != '"+Constant.Level_Type_JXDW+"'");
		return getDeptIdSqlByDeptIdsAndTable(deptIds, Constant.TABLE_T_Code_Dept_Teach, li);
	}
	
	@Override
	public String getDeptTeachTeachIdSqlByDeptList(List<String> deptList){
		String deptIds = getDeptIdsFromDeptList(deptList);
		return getDeptTeachStuIdSqlByDeptIds(deptIds);
	}
	@Override
	public String getDeptTeachTeachIdSqlByDeptIds(String deptIds){
		return getDeptIdSqlByDeptIdsAndTable(deptIds, Constant.TABLE_T_Code_Dept_Teach, null);
	}
	
	@Override
	public String getDeptAllIdSqlByDeptList(List<String> deptList){
		String deptIds = getDeptIdsFromDeptList(deptList);
		return getDeptAllIdSqlByDeptIds(deptIds);
	}
	
	@Override
	public String getDeptAllIdSqlByDeptIds(String deptIds){
		return "select * from("+ getDeptIdSqlByDeptIds(deptIds) +" union "+ getDeptTeachTeachIdSqlByDeptIds(deptIds) +")";
	}
	
	/**
	 * 从标准权限中解析 组织机构ids
	 * @param deptList
	 * @return String
	 */
	private String getDeptIdsFromDeptList(List<String> deptList) {
		// （没有权限限制 || 第一个权限属性是空串）：全部权限；否则取第2、3个；第四个是班级属性，教职工归属不可能是班级
		String deptIds = null;
		// 不是全部权限
		if(!PmsUtils.isAllPmsData(deptList)){
			List<String> deptList2 = new ArrayList<String>();
			String dept = "";
			if(deptList != null){
				for(int i=1,len=deptList.size(); i<len-1; i++){
					dept = deptList.get(i);
					if(!PmsUtils.isNullPmsData(dept)){
						deptList2.add(dept);
					}
				}
			}
			deptIds = deptList2.size()>0 ? StringUtils.join(deptList2, ",") : deptIds;
		}else{
			deptIds = baseDao.queryForString("select id from ("+getSchoolSql+")");
		}
		return formatInSql(deptIds);
	}
	/**
	 * 从标准权限中解析 班级ids
	 * @param deptList
	 * @return String
	 */
	private String getClassesIdsFromDeptList(List<String> deptList){
		String deptIds = null; int bjIndex = deptList.size()-1;
		if(deptList != null && !PmsUtils.isNullPmsData(deptList.get(bjIndex))){
			deptIds = deptList.get(bjIndex);
		}
		return deptIds;
	}
	/**
	 * 根据表名获取组织机构权限【节点为null时查询空数据】
	 * @param deptIds 组织机构Ids；null：空
	 * @param tableName 表名；eg：T_Code_Dept/T_Code_Dept_Teach
	 * @return String
	 * <br> { select id from t }
	 */
	private String getDeptIdSqlByDeptIdsAndTable(String deptIds, String tableName, List<String> mathList){
		return "select id from (" +getDeptSqlByDeptIdsAndTable(deptIds, tableName, mathList)+ ")";
	}
	/**
	 * 根据表名获取组织机构权限【节点为null时查询空数据】
	 * @param deptIds 组织机构Ids(不能出现上下级节点关系，因为这里不会去重)；null：空
	 * @param tableName 表名；eg：T_Code_Dept/T_Code_Dept_Teach
	 * @param mathList 参数匹配列表；eg：[ "t.level_type != 'JXDW'" ]
	 * @return String
	 * <br> { select * from t }
	 */
	private String getDeptSqlByDeptIdsAndTable(String deptIds, String tableName, List<String> mathList){
		String sql_pattern = "select * from "+tableName+" t", matchSql = null, sql = null;
		deptIds = formatInSql(deptIds);
		if(deptIds == null){
			sql_pattern += " where 1 != 1";
		}else{
			sql_pattern += " ,"
					+ " (select t.path_ from "+tableName+" t where t.id in(" +deptIds+ ")) x"
					+ " where t.istrue=1 and substr(t.path_,1,length(x.path_))=x.path_";
		}
		matchSql    = mathList!=null ? (" and "+StringUtils.join(mathList, " and ")) : "";
		sql         = sql_pattern+matchSql+" order by t.level_,t.pid,t.order_,t.code_";
		return sql;
	}

	@Override
	public List<Map<String, Object>> queryClassesListByPid(String pid, Integer schoolYear){
		String sql = "select t.no_ id, t.name_ name, t.teach_dept_id as pid, '"+Constant.Level_Type_BJ+"' as level_type from t_classes t"
				+" where t.teach_dept_id ='" +pid+"'"
				+ (schoolYear!=null ? "and t.grade <= "+schoolYear+" and (t.grade+t.length_schooling) > "+schoolYear : "")
				+ " order by t.name_" ;
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public String getClassesIdSqlByDeptList(List<String> deptList){
		String deptIdSql = getDeptAllIdSqlByDeptList(deptList),
			   classId   = getClassesIdsFromDeptList(deptList),
			   sql = getClassesIdSqlByDeptSql(deptIdSql);
		sql += classId==null ? "" : " union select no_ no from t_classes where no_ in("+classId+")";
		return sql;
	}
	@Override
	public String getClassesIdSqlByDeptList(List<String> deptList, Integer schoolYear){
		String deptIdSql = getDeptAllIdSqlByDeptList(deptList);
		return getClassesIdSqlByDeptSql(deptIdSql, schoolYear);
	}
	
	@Override
	public String getClassesIdSqlByDeptIds(String deptIds){
		String deptIdSql = getDeptAllIdSqlByDeptIds(deptIds);
		return getClassesIdSqlByDeptSql(deptIdSql);
	}
	
	/**
	 * 根据组织机构sql获取班级sql
	 * @param deptIdSql
	 * @return String
	 */
	private String getClassesIdSqlByDeptSql(String deptIdSql){
		return getClassesIdSqlByDeptSql(deptIdSql, null);
	}
	/**
	 * 根据组织机构sql获取班级sql
	 * @param deptIdSql
	 * @param schoolYear 入学年级
	 * @return String
	 */
	private String getClassesIdSqlByDeptSql(String deptIdSql, Integer schoolYear){
		return "select distinct(t.no_) no from t_classes t,("+deptIdSql+")dept where t.teach_dept_id = dept.id"
				+ (schoolYear==null ? "" : 
					" and ( (t.GRADE < "+schoolYear+" and (t.GRADE+t.LENGTH_SCHOOLING) > "+schoolYear+")"
					+ " or (t.GRADE = "+schoolYear+") )");
	}

	@Override
	public List<Map<String, Object>> queryYxList(List<String> deptList){
		return queryListByLevelType(deptList, Constant.Level_Type_YX);
	}
	@Override
	public List<Map<String, Object>> queryZyListStu(List<String> deptList){
		return queryZyListStu(deptList, null);
	}
	@Override
	public List<Map<String, Object>> queryZyListStu(List<String> deptList, String stuSql){
		String zyIdsSql = getZyIdSqlByDeptListStu(deptList, stuSql);
		String sql = "select zy.id, zy.name_ name, zy.pid, zy.level_type, zy.level_ from ("+zyIdsSql+")dept,"
				+ " t_code_dept_teach zy where dept.id=zy.id";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public String getZyIdSqlByDeptListStu(List<String> deptList){
		return getZyIdSqlByDeptListStu(deptList, null);
	}
	@Override
	public String getZyIdSqlByDeptListStu(List<String> deptList, String stuSql){
		stuSql = stuSql==null ? "select * from t_stu" : stuSql;
		String deptIdSql = getDeptTeachStuIdSqlByDeptList(deptList);
		String sql =deptIdSql+" where level_type = '"+Constant.Level_Type_ZY+"'" ;
		sql = "select distinct(dept.id) from ("+sql+")dept, ("+stuSql+")stu where dept.id=stu.major_id ";
		return sql;
	}
	@Override
	public List<Map<String, Object>> queryBjList(List<String> deptList, Integer schoolYear){
		String classIdSql = getClassesIdSqlByDeptList(deptList, schoolYear),
			   sql        = "select t.no_ id, t.name_ name, t.teach_dept_id pid, '"+Constant.Level_Type_BJ+"' level_type, "+PmsUtils.PMS_BJ_LEVEL+" as level_ from t_classes t,("+classIdSql+")cla where t.no_=cla.no";
		return baseDao.queryListInLowerKey(sql);
	}
	@Override
	public String getYxIdSqlByDeptList(List<String> deptList){
		String deptIdSql = getDeptTeachStuIdSqlByDeptList(deptList);
		String sql =deptIdSql+" where level_type = '"+Constant.Level_Type_YX+"'" ;
		return sql;
	}
	/**
	 * 根据levelType获取标准权限下的列表数据
	 * @param deptList
	 * @param levelType
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> queryListByLevelType(List<String> deptList, String levelType) {
		String deptTeachIdSql = getDeptTeachStuIdSqlByDeptList(deptList),
			   sql     = "select t.id, t.name_ name, t.pid, t.level_type, t.level_ from "+Constant.TABLE_T_Code_Dept_Teach+" t,("+deptTeachIdSql+")t2"
			   		+ " where t.id=t2.id and t.level_type='"+levelType+"' order by t.order_,t.code_";
		return baseDao.queryListInLowerKey(sql);
	}
	
	@Override
	public int queryStuCount(int schoolYear, List<String> deptList, List<AdvancedParam> advancedList){
		// 获取学生sql
		String stuSql = getStuSql(schoolYear, deptList, advancedList),
			   sql    = "select count(0) from (" +stuSql+ ") t";
		return baseDao.queryForInt(sql);
	}
	
	@Override
	public String getStuSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList){
		AdvancedParam schoolYearAdp = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_SCHOOL_YEAR, 
												schoolYear==null ? null : schoolYear+"");
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, schoolYearAdp);
		return getStuSql(deptList, stuAdvancedList);
	}
	
	@Override
	public String getStuSql(List<String> deptList, List<AdvancedParam> stuAdvancedList){
	    return getStuSql("t_stu", false,deptList, stuAdvancedList);
	}
	
	@Override
	public String getGraduateStuSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList){
		AdvancedParam schoolYearAdp = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_SCHOOL_YEAR, 
												schoolYear==null ? null : schoolYear+"");
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, schoolYearAdp);
		return getGraduateStuSql(deptList, stuAdvancedList);
	}
	
	@Override
	public String getGraduateStuSql(List<String> deptList, List<AdvancedParam> stuAdvancedList){
	    return getStuSql("t_stu_graduate_stu",true, deptList, stuAdvancedList);
	}
	
	@Override
	public String getNewStuSql(Integer schoolYear, List<String> deptList, List<AdvancedParam> stuAdvancedList){
		AdvancedParam schoolYearAdp = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_SCHOOL_YEAR, 
												schoolYear==null ? null : schoolYear+"");
		stuAdvancedList = AdvancedUtil.add(stuAdvancedList, schoolYearAdp);
		return getNewStuSql(deptList, stuAdvancedList);
	}
	
	@Override
	public String getNewStuSql(List<String> deptList, List<AdvancedParam> stuAdvancedList){
	    return getStuSql("t_stu_welcome",false,deptList, stuAdvancedList);
	}
	
	/**
	 * 设置权限匹配sql-教学
	 * @param deptList
	 * @param deptId
	 * @param innerJoinList
	 * @param matchList void
	 * @deprecated 由于 or 用法有不可改进的效率问题，所以弃用
	 */
	private void setStuDeptTeachMatchList(List<String> deptList, String deptId, List<String> innerJoinList, List<String> matchList, String table){
		if(deptId == null) deptId = MapUtils.getString(baseDao.queryMapInLowerKey(getSchoolSql), "id");
		String sql = PmsUtils.getDataTeachSqlByDeptData(deptId, deptList),
			   tableAsName = "deptT_"+Constant.getNext(),
			   matchSql = null;
		switch (table) {
		case "t_stu":
		case "t_stu_welcome":
			matchSql = "t.CLASS_ID = "+tableAsName+".id";
			break;
		case "t_stu_graduate_stu":
			matchSql = "( t.DEPT_ID = "+tableAsName+".id or t.MAJOR_ID = "+tableAsName+".id )";
			break;
		default:
			break;
		}
		innerJoinList.add("("+sql+") "+tableAsName);
		matchList.add(matchSql);
	}
	/**
	 * 获取统一的权限匹配sql
	 */
	private String getStuDeptTeachSql(List<String> deptList, String deptId){
		if(deptId == null) deptId = MapUtils.getString(baseDao.queryMapInLowerKey(getSchoolSql), "id");
		return PmsUtils.getDataTeachSqlByDeptData(deptId, deptList);
	}
	
	private String getStuSql(String table,Boolean isGraduate,List<String> deptList, List<AdvancedParam> stuAdvancedList){
		// （没有权限限制 || 第一个权限属性是空串）：全部权限；否则取第2、3个；第四个是班级属性，教职工归属不可能是班级（但是在某种情况下可能是班级 20160712）
		List<String> matchList = new ArrayList<>(),
					 innerJoinList = new ArrayList<String>();
		List<String> unionDeptSqlList = new ArrayList<>();
		boolean isAlreadyMatchDept = false; // 是否已匹配权限 20170120
		boolean isOr = true, isUnion = false; // 是用 or的形式 还是 union
		table = table.toLowerCase();
		if(stuAdvancedList != null && stuAdvancedList instanceof List){
			// 获取入学年级
			String schoolYear_ = AdvancedUtil.getValue(stuAdvancedList, AdvancedUtil.Common_SCHOOL_YEAR);
			Integer schoolYear = schoolYear_==null ? null : Integer.valueOf(schoolYear_);
			String values = null,isNullSql = null;
			for(AdvancedParam t : stuAdvancedList){
				values = t.getValues();
				if(values == null) continue; // 重大修复 20160617
				values = formatInSql(values);
				switch (t.getCode()) {
				case AdvancedUtil.Common_DEPT_ID: // 行政组织机构
				case AdvancedUtil.Common_DEPT_TEACH_ID: // 教学组织机构（学生单位）
					if(isOr) setStuDeptTeachMatchList(deptList, formatOutSql(values), innerJoinList, matchList, table); // 启用标准权限sql 并使用连接查询 20161226
					if(isUnion) unionDeptSqlList.add(getStuDeptTeachSql(deptList, formatOutSql(values))); // 优化权限查询效率低的问题 20170105
					isAlreadyMatchDept = true;
					break;
				case AdvancedUtil.Common_CLASS_ID:
					matchList.add("t.CLASS_ID in("+values+")");
					break;
				case AdvancedUtil.Common_SEX_CODE: // 性别
					matchList.add("t.SEX_CODE in("+values+")");
					break;
				case AdvancedUtil.Stu_EDU_ID: // 培养层次
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.EDU_ID "+isNullSql+"");
					break;
				case AdvancedUtil.Common_SCHOOL_YEAR: // 学年
					matchList.add("(t.ENROLL_GRADE <= "+values+" and (t.ENROLL_GRADE+t.LENGTH_SCHOOLING) > "+values+")"); // 重要优化(去掉or) 20160713
					break;
				case AdvancedUtil.Stu_ENROLL_GRADE: // 入学年级
					matchList.add("t.ENROLL_GRADE in("+values+")");
					break;
				case AdvancedUtil.Stu_GRADE: // 年级； 年级转入学年级
					if(!isGraduate && schoolYear != null){
						String[] ary = values.split(",");
						List<String> li = new ArrayList<>();
						for(String nj : ary){
							nj = formatOutSql(nj); // 对于加引号的年级 需要解除引号用于运算 20160714
							li.add("'"+(schoolYear+1-Integer.valueOf(nj))+"'");
						}
						matchList.add("t.ENROLL_GRADE in("+StringUtils.join(li, ",")+")");
					}
					break;
				case AdvancedUtil.Stu_SUBJECT: // 学科门类
					// 学科门类Id -> 转成专业Id
					String zyIdSql = "select dept.id from t_code_dept_teach dept, "+Constant.TABLE_T_Code_Subject_Degree+" t, "+Constant.TABLE_T_Code_Subject_Degree+" p"
							+ " where dept.subject_id=t.id and t.path_ like p.path_||'%' and p.id in("+values+")";
					matchList.add("t.MAJOR_ID in("+zyIdSql+")"); // 使用专业查询 20161226
					break;
				case AdvancedUtil.Stu_STU_ROLL_CODE://学籍状态
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					if(!isGraduate){
					    matchList.add("t.STU_ROLL_CODE "+isNullSql+"");
					}
					break;
				case AdvancedUtil.Stu_NATION_CODE://民族
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.NATION_CODE "+isNullSql+"");
					break;
				case AdvancedUtil.Stu_POLITICS_CODE://政治面貌
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.POLITICS_CODE "+isNullSql+"");
					break;
				case AdvancedUtil.Stu_LENGTH_SCHOOLING://学制
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.LENGTH_SCHOOLING "+isNullSql+"");
					break;
				case AdvancedUtil.Stu_STU_STATE_CODE://学生状态
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.STU_STATE_CODE "+isNullSql+"");
					break;
				case AdvancedUtil.Stu_LEARNING_FORM_CODE://学习形式
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
				    if (!isGraduate){
					    matchList.add("t.LEARNING_FORM_CODE "+isNullSql+"");
				    }
					break;
				case AdvancedUtil.Stu_STU_FROM_CODE://生源类型
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.STU_FROM_CODE "+isNullSql+"");
					break;
				case AdvancedUtil.Stu_RECRUIT_CODE://招生类型
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.RECRUIT_CODE "+isNullSql+"");
					break;	
				case AdvancedUtil.Stu_LEARNING_STYLE_CODE://学习方式
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					if (!isGraduate){
					   matchList.add("t.LEARNING_STYLE_CODE "+isNullSql+"");
					}
					break;
				case AdvancedUtil.Stu_ANMELDEN_CODE://户口性质
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.ANMELDEN_CODE "+isNullSql+"");
					break;
				case AdvancedUtil.Stu_ORIGIN_ID://生源地
					innerJoinList.add("("+queryOriginIdsById(formatOutSql(values))+") origin ");
					matchList.add("t.STU_ORIGIN_ID = origin.id");
					break;
				default:
					break;
				}
			}
		}
		if( !PmsUtils.isAllPmsData(deptList) && !isAlreadyMatchDept ){
			if(isOr) setStuDeptTeachMatchList(deptList, null, innerJoinList, matchList, table); // 启用标准权限sql 并使用连接查询 20161226
			if(isUnion) unionDeptSqlList.add(getStuDeptTeachSql(deptList, null)); // 优化权限查询效率低的问题 20170105
		}
		String sql = "select t.* from "+table+" t " 
		       +(innerJoinList.isEmpty() ? "" : (","+StringUtils.join(innerJoinList, ",")))
			   +(matchList.isEmpty() ? "" : (" where "+StringUtils.join(matchList, " and ")));
		// 解决 (dept_id=a.id or major_id=a.id or class.id=a.id) or 带来的效率低下问题 20170105
		if(!unionDeptSqlList.isEmpty()){
			// 多个权限sql自身先连接
			String deptSql = "select a0.id from ";
			List<String> wheres = new ArrayList<>();
			for(int i=0,len=unionDeptSqlList.size(); i<len; i++){
				unionDeptSqlList.set(i, "("+unionDeptSqlList.get(i)+") a"+i);
				if(i > 0){
					wheres.add("a"+i+".id = a0.id");
				}
			}
			// select a0.id from a0, a1, a2 where a1.id=a0.id and a2.id=a0.id
			deptSql += ListUtils.join(unionDeptSqlList, ", ");
			if(!wheres.isEmpty()){
				deptSql += " where "+ListUtils.join(wheres, " and ");
			}
			// 与学生数据进行 union all
			switch (table) {
			case "t_stu":
			case "t_stu_welcome":
				sql = "select t.* from ("+sql+")t, ("+deptSql+") class where t.class_id = class.id";
				break;
			case "t_stu_graduate_stu":
				sql = "select t.* from ("+sql+")t, ("+deptSql+") dept where t.dept_id = dept.id"
						+ " union "
						+ "select t.* from ("+sql+")t, ("+deptSql+") major where t.major_id = major.id";
				break;
			default:
				break;
			}
		}
		return sql;
	}
	@Override
	public String getStuDetailSql(List<String> deptList, List<AdvancedParam> stuAdvancedList){
		String stuSql = getStuSql(deptList, stuAdvancedList);
		return getStuDetailSql(stuSql);
	}
	
	@Override
	public String getStuDetailSql(String stuSql){
		String sql = "select t.no_ no,t.name_ name, sex.name_ sexmc, sex.code_ sexid, dept.name_ deptmc, t.dept_id deptid,"
				+ " major.name_ majormc, t.major_id majorid, class.name_ classmc, t.class_id as classid,t.enroll_grade,t.length_schooling,"
				+ " xzqh.sname shengmc, xzqh.sid shengid, shi.sname shimc, shi.sid shiid, shi.name xianmc, shi.id xianid,"
				+ " xzqh.sname||shi.sname||shi.name as ssx "
				+ " from ("+stuSql+")t"
				+ " left join t_code sex on t.sex_code=sex.code_ and sex.code_type='"+Constant.CODE_SEX_CODE+"'"
				+ " left join t_code_dept_teach dept on t.dept_id=dept.id"
				+ " left join t_code_dept_teach major on t.major_id=major.id"
				+ " left join t_classes class on t.class_id=class.no_"
				+ " left join (select s.id sid, s.name_ sname, q.id from t_code_admini_div s, t_code_admini_div q"
				+ " where substr(q.path_,1,8)=s.path_) xzqh on t.STU_ORIGIN_ID=xzqh.id"
				+ " left join (select s.id sid, s.name_ sname, q.id, q.name_ name from t_code_admini_div s, t_code_admini_div q"
				+ " where substr(q.path_,1,12)=s.path_) shi on t.stu_origin_id=shi.id";
		return sql;
	}
	

	/**
	 * 设置权限匹配sql-行政
	 * @param deptList
	 * @param deptId
	 * @param innerJoinList
	 * @param matchList void
	 */
	private void setStuDeptMatchList(List<String> deptList, String deptId, List<String> innerJoinList, List<String> matchList){
		if(deptId == null) deptId = MapUtils.getString(baseDao.queryMapInLowerKey(getSchoolSql), "id");
		String sql = PmsUtils.getDataSqlByDeptData(deptId, deptList);
		String tableAsName = "dept_"+Constant.getNext();
		innerJoinList.add("("+sql+") "+tableAsName);
		matchList.add("t.DEPT_ID = "+tableAsName+".id");
	}
	
	@Override
	public String getTeaSql(List<String> deptList, List<AdvancedParam> teaAdvancedList){
		List<String> matchList = new ArrayList<>();
		String values = null, isNullSql = null;
		Integer year = null;
		List<String> deptIdSqlList = new ArrayList<>(); // 将 deptId的处理整合一起 20160722
		List<String> innerJoinList = new ArrayList<>(); 
		if( !PmsUtils.isAllPmsData(deptList) ){
			values = deptList.get(1);
			if(!PmsUtils.isNullPmsData(values)){
//				values = getDeptIdSqlByDeptIds(values);
//				deptIdSqlList.add(values);
				setStuDeptMatchList(deptList, formatOutSql(values), innerJoinList, matchList); // 启用标准权限sql 并使用连接查询 20161226
			}
		}else{
//			values = getDeptIdSqlByDeptList(deptList);
//			deptIdSqlList.add(values);
			setStuDeptMatchList(deptList, formatOutSql(values), innerJoinList, matchList); // 启用标准权限sql 并使用连接查询 20161226
		}
		// 参数中是否有教职工状态
		boolean hasStatus = false;
		if(teaAdvancedList != null && teaAdvancedList instanceof List){
			for(AdvancedParam t : teaAdvancedList){
				values = t.getValues();
				if(values == null) continue;
				values = formatInSql(values);
				switch (t.getCode()) {
				case AdvancedUtil.Common_DEPT_ID: // 行政组织机构
				case AdvancedUtil.Common_DEPT_TEACH_ID: // 教学组织机构（学生单位）
				case AdvancedUtil.Common_DEPT_TEACH_TEACH_ID: // 教学组织机构（教学单位）
//					values = getDeptIdSqlByDeptIds(values);
//					deptIdSqlList.add(values);
					setStuDeptMatchList(deptList, formatOutSql(values), innerJoinList, matchList); // 启用标准权限sql 并使用连接查询 20161226
					break;
				case AdvancedUtil.Common_SEX_CODE: // 性别
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.SEX_CODE "+isNullSql);
					break;
				case AdvancedUtil.Common_YEAR: // 年
					year = Integer.valueOf(formatOutSql(values));
					matchList.add("t.IN_DATE < '"+(year+1)+"'");
					break;
				case AdvancedUtil.Tea_SUBJECT: // 学科专业
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in(select id from("+getTreeSubjectDegreeSql()+") where pid in("+values+") or id in("+values+"))";
					matchList.add("t.SUBJECT_ID "+isNullSql);
					break;
				case AdvancedUtil.Tea_TEA_STATUS_CODE: // 教职工状态
					hasStatus = true;
					matchList.add("t.tea_status_code in("+values+")");
					break;
				case AdvancedUtil.Tea_EDU_ID: // 学历
//					matchList.add("t.EDU_ID in("+values+")");
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in(select id from("+getTreeEduSql()+") where pid in("+values+") or id in("+values+"))";
					matchList.add("t.EDU_ID "+isNullSql);
					break;
				case AdvancedUtil.Tea_DEGREE_ID: // 学位
					if("'null'".equals(values)) isNullSql = " not in(select id from t_code_degree) ";
					else isNullSql = "in(select id from("+getTreeDegreeSql()+") where pid in("+values+") or id in("+values+"))";
					matchList.add("t.DEGREE_ID "+isNullSql);
					break;
				case AdvancedUtil.Tea_ZYJSZW_ID: // 专业技术职务
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.zyjszw_id "+isNullSql);
					break;
				case AdvancedUtil.Tea_ZYJSZW_JB_CODE: // 专业技术职务等级
					if("'null'".equals(values)){
						isNullSql = "( (t.zyjszw_id is null) or "
								+ "(t.zyjszw_id in(select t.id from "+Constant.TABLE_T_CODE_ZYJSZW+" t where t.zyjszw_jb_code is null)) )";
					}else isNullSql = "t.zyjszw_id in(select t.id from "+Constant.TABLE_T_CODE_ZYJSZW+" t where t.zyjszw_jb_code in("+values+"))";
					matchList.add(isNullSql);
					break;
				case AdvancedUtil.Tea_AUTHORIZED_STRENGTH_ID: // 教师类别
					matchList.add("t.AUTHORIZED_STRENGTH_ID in"
							+ " (select t.id from t_code_AUTHORIZED_STRENGTH t inner join t_code_AUTHORIZED_STRENGTH x"
							+ " on t.path_ like x.path_||'%' and x.id in("+values+"))");
					break;
				case AdvancedUtil.Tea_SENIOR_CODE: // 高层次人才
					String seniorWhereSql = formatInSql(AdvancedUtil.ALL_ID_ALL).equals(values) ? "" : "where t.SENIOR_CODE in("+values+")",
						   seniorSql = "select t.no_ from T_TEA_SENIOR t,"
							+ " (select no_,max(get_date) get_date from T_TEA_SENIOR t "+seniorWhereSql+" group by no_)x"
							+ " where t.no_=x.no_ and (t.get_date=x.get_date or (t.get_date is null and x.get_date is null))";
					matchList.add("t.tea_no in("+seniorSql+")");
					break;
				case AdvancedUtil.Tea_ANMELDEN_CODE: //户口性质
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.ANMELDEN_CODE "+isNullSql);
					break;
				case AdvancedUtil.Tea_NATION_CODE://民族
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.NATION_CODE "+isNullSql);
					break;
				case AdvancedUtil.Tea_POLITICS_CODE://政治面貌
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.POLITICS_CODE "+isNullSql);
					break;
				case AdvancedUtil.Tea_TEA_SOURCE_ID://教职工来源
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.TEA_SOURCE_ID "+isNullSql);
					break;
				case AdvancedUtil.Tea_BZLB_CODE://编制类别
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.BZLB_CODE "+isNullSql);
					break;
				case AdvancedUtil.Tea_SFSSJS://是否双师教师
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.SFSSJS "+isNullSql);
					break;
				case AdvancedUtil.Tea_SKILL_MOVES_CODE://工人技术等级
					if("'null'".equals(values)) isNullSql = "is null";
					else isNullSql = "in("+values+")";
					matchList.add("t.SKILL_MOVES_CODE "+isNullSql);
					break;
				default:
					break;
				}
			}
		}
		// 处理不同年份，职称、学历的即时性问题
		List<String> selectColumnList = new ArrayList<>(), leftJoinList = new ArrayList<>(); // 查询字段、join字段
		selectColumnList.add("t.tea_no,t.name_,t.idno,t.fomer_name,t.birthday,t.dept_id,t.sex_code,"
				+ "t.nation_code,t.married,t.subject_id,t.domicile_id,t.place_domicile,"
				+ "t.anmelden_code,t.politics_code,t.join_party_date,t.in_date,"
				+ "t.teaching_date,t.work_date,t.authorized_strength_id,t.tea_source_id,t.bzlb_code,"
				+ "t.skill_moves_code,t.gatq_code,t.tea_status_code,t.sfssjs");
		if(year != null){
			// 职称变动
			selectColumnList.add("case when zyjszw.zyjszw_code is not null then zyjszw.zyjszw_code else t.zyjszw_id end zyjszw_id");
			leftJoinList.add("left join (select t.tea_id,t.zyjszw_code from T_TEA_ZCPD t,"
					+ " (select t.tea_id,max(t.date_) date_ from T_TEA_ZCPD t where t.date_ is not null and t.date_<'"+(year+1)+"' group by t.tea_id)ma"
					+ " where t.tea_id=ma.tea_id and t.date_ = ma.date_)zyjszw on t.tea_no=zyjszw.tea_id");
			// 学历变动
			selectColumnList.add("case when edu.edu_id is not null then edu.edu_id else t.edu_id end edu_id");
			leftJoinList.add("left join (select t.tea_id,t.edu_id from T_TEA_EDU t,"
					+ " (select t.tea_id,max(t.order_) order_ from T_TEA_EDU t where t.graduate_years<'"+(year+1)+"' and t.edu_id is not null group by t.tea_id)ma"
					+ " where t.tea_id=ma.tea_id and t.order_ = ma.order_)edu on t.tea_no=edu.tea_id");
			// 学位变动
			selectColumnList.add("case when degree.degree_id is not null then degree.degree_id else t.degree_id end degree_id");
			leftJoinList.add("left join (select t.tea_id,t.degree_id from T_TEA_EDU t,"
					+ " (select t.tea_id,max(t.order_) order_ from T_TEA_EDU t where t.graduate_years<'2012' and t.degree_id is not null group by t.tea_id)ma"
					+ " where t.tea_id=ma.tea_id and t.order_=ma.order_)degree on t.tea_no=degree.tea_id");
			// 职称等级
			selectColumnList.add("zcdj.code_ zyjszw_jb_code");
			leftJoinList.add("left join t_code_zyjszw zc on"
					+ " ((zyjszw.zyjszw_code is not null and zyjszw.zyjszw_code=zc.id) or (zyjszw.zyjszw_code is null and t.zyjszw_id=zc.id))"
					+ " left join t_code zcdj on zc.zyjszw_jb_code=zcdj.code_ and zcdj.code_type='"+Constant.CODE_ZYJSZW_JB_CODE+"'");
		}else{
			selectColumnList.add("t.zyjszw_id");
			selectColumnList.add("t.edu_id");
			selectColumnList.add("t.degree_id");
			// 职称等级
			selectColumnList.add("zcdj.code_ zyjszw_jb_code");
			leftJoinList.add("left join t_code_zyjszw zc on t.zyjszw_id=zc.id"
					+ " left join t_code zcdj on zc.zyjszw_jb_code=zcdj.code_ and zcdj.code_type='"+Constant.CODE_ZYJSZW_JB_CODE+"'");
		}
		String sql = "select " +ListUtils.join(selectColumnList, ",")+ " from t_tea t ";
		sql += leftJoinList.isEmpty() ? "" : (ListUtils.join(leftJoinList, " "));
		/** where 条件 */
		// 组织机构交集
		if(!deptIdSqlList.isEmpty()){
			matchList.add("t.dept_id in("+ListUtils.join(deptIdSqlList," INTERSECT ")+")");
		}
		// 默认在职
		if(!hasStatus) matchList.add("t.tea_status_code in("+Constant.getTeaStatusCodeDefault()+")");
		sql += (
			      ( innerJoinList.isEmpty() ? "" : (","+StringUtils.join(innerJoinList, ",")) )
				 +( matchList.isEmpty() ? "" : (" where "+StringUtils.join(matchList, " and ")) )
				);
		return sql;
	}

	@Override
	public String getTeaDetailSql(List<String> deptList, List<AdvancedParam> teaAdvancedList){
		String teaSql = getTeaSql(deptList, teaAdvancedList);
		return getTeaDetailSql(teaSql);
	}
	
	@Override
	public String getTeaDetailSql(String teaSql){
		String sql = "select t.tea_no, t.name_ name, dept.name_ deptmc, dept.id deptid, sex.name_ sexmc, sex.code_ sexid, zc.name_ zcmc, zc.id zcid,"
				+ " zcdj.name_ zcdjmc, zcdj.code_ zcdjid, edu.pname edumc, edu.pid eduid, deg.pname degreemc, deg.pid degreeid, subject.pname subjectmc, subject.pid subjectid"
				+ " from ("+teaSql+")t"
				+ " left join t_code_dept dept on t.dept_id=dept.id"
				+ " left join t_code sex on t.sex_code=sex.code_ and sex.code_type='"+Constant.CODE_SEX_CODE+"'"
				+ " left join "+Constant.TABLE_T_CODE_ZYJSZW+" zc on t.zyjszw_id=zc.id"
				+ " left join t_code zcdj on zc.zyjszw_jb_code=zcdj.code_ and zcdj.code_type='"+Constant.CODE_ZYJSZW_JB_CODE+"'"
				+ " left join ("+getTreeEduSql()+") edu on t.edu_id=edu.id"
				+ " left join ("+getTreeDegreeSql()+") deg on t.degree_id=deg.id"
				+ " left join ("+getTreeSubjectDegreeSql()+") subject on t.subject_id=subject.id";
		return sql;
	}
	/**
	 * 获取 学历 树形编码sql 【顶级节点的父节点仍是顶级节点】
	 * @return String <br>
	 * ( select id,pid,name,pname from table )
	 */
	private String getTreeEduSql(){
		return getTreeCodePathSql(Constant.TABLE_T_CODE_EDUCATION);
	}
	/**
	 * 获取 学位 树形编码sql 【顶级节点的父节点仍是顶级节点】
	 * @return String <br>
	 * ( select id,pid,name,pname from table )
	 */
	private String getTreeDegreeSql(){
		return getTreeCodePathSql(Constant.TABLE_T_CODE_DEGREE);
	}
	/**
	 * 获取 学科专业 树形编码sql 【顶级节点的父节点仍是顶级节点】
	 * @return String <br>
	 * ( select id,pid,name,pname from table )
	 */
	private String getTreeSubjectDegreeSql(){
		return getTreeCodePathSql(Constant.TABLE_T_Code_Subject_Degree);
	}
	/**
	 * 获取 树形编码sql 【顶级节点的父节点仍是顶级节点】
	 * @param tablename
	 * @return String <br>
	 * ( select id,pid,name,pname from table )
	 */
	private String getTreeCodePathSql(String tablename){
		return "select code.id,case when code.path_=pcode.path_ then code.id else pcode.id end pid,code.name_ name,pcode.name_ pname from "
				+tablename+" code, "+tablename+ " pcode where code.istrue=1 and substr(code.path_,1,4)=pcode.path_";
	}

	@Override
	public String formatInSql(String values){
		return PmsUtils.formatInSql(values);
	}
	
	@Override
	public String formatOutSql(String values){
		return PmsUtils.formatOutSql(values);
	}
	
	@Override
	public String querySchoolStartDate(String schoolYear, String term){
		return baseDao.queryForString("select start_date from t_school_start where school_year = '"+schoolYear+"' and term_code = '"+term+"'");
	}
	
	@Override
	public List<Map<String, Object>> queryGradeGroup(String stuSql, int schoolYear){
		String sql = "select count(0) value, t.grade code from"
				+ " (select t.no_, stu.enroll_grade, "+schoolYear+"+1-stu.enroll_grade as grade"
				+ " from ("+stuSql+") t, t_stu stu"
				+ " where t.no_=stu.no_)t group by t.grade order by grade";
		return baseDao.queryListInLowerKey(sql, Types.NUMERIC, Types.NUMERIC);
	}
	
	@Override
	public List<Map<String, Object>> querySubjectDegreeUsefulList(){
		return baseDao.queryListInLowerKey(getSubjectDegreeUsefulSql());
	}
	@Override
	public String getSubjectDegreeUsefulSql(){
		String sql = "select distinct(subject.pid) id,tp.code_,tp.name_,tp.pid,tp.path_,tp.level_,tp.level_type,tp.order_,tp.istrue"
				+ " from "+Constant.TABLE_T_Code_Dept_Teach+" dept,("+getTreeSubjectDegreeSql()+")subject, t_Code_Subject_Degree tp"
				+ " where dept.subject_id=subject.id and subject.pid=tp.id order by tp.level_,tp.order_,tp.code_";
		return sql;
	}
	
	@Override
	public Integer queryMinSchoolYear(String table, String columnSchoolYear){
		String sql  = "select min("+columnSchoolYear+") from " +table,
			   year = baseDao.queryForString(sql);
		return year==null ? null : Integer.valueOf(year.substring(0, 4));
	}
	@Override
	public String[] queryMinSchoolYearTermCode(String table, String columnSchoolYear, String columnTermCode){
		String[] ary = new String[2];
		Integer minYear = queryMinSchoolYear(table, columnSchoolYear);
		if(minYear != null){
			String termCode = baseDao.queryForString("select min("+columnTermCode+") from "+table+" where "+columnSchoolYear+" like '"+minYear+"%'");
			ary[0] = minYear+"";
			ary[1] = termCode;
		}
		return ary;
	}
	
	@Override
	public Integer queryMaxSchoolYear(String table, String column){
		String sql  = "select max("+column+") from " +table,
				year = baseDao.queryForString(sql);
		return year==null ? null : Integer.valueOf(year.substring(0, 4));
	}
	@Override
	public String[] queryMaxSchoolYearTermCode(String table, String columnSchoolYear, String columnTermCode){
		String[] ary = new String[2];
		Integer maxYear = queryMaxSchoolYear(table, columnSchoolYear);
		if(maxYear != null){
			String termCode = baseDao.queryForString("select max("+columnTermCode+") from "+table+" where "+columnSchoolYear+" like '"+maxYear+"%'");
			ary[0] = maxYear+"";
			ary[1] = termCode;
		}
		return ary;
	}
	
	@Override
	public String getDormTogetherStuIdSql(String stuIdSql, boolean containsOwn){
		String sql = "select stu.stu_id no_ from T_DORM_BERTH_STU stu, T_DORM_BERTH be,"
				+ " (select distinct(be.dorm_id) from T_DORM_BERTH be, T_DORM_BERTH_STU t,("+stuIdSql+")stu"
				+ "   where t.stu_id=stu.no_ and be.id=t.berth_id) b"
				+ " where stu.berth_id=be.id and be.dorm_id=b.dorm_id"
				+ (containsOwn ? "" : " and stu.stu_id not in (select no_ from("+stuIdSql+"))");
		return sql;
	}

	@Override
	public String getStuScoreExamSql(String stuSql, String schoolYear, String termCode){
		stuSql = (stuSql==null||"".equals(stuSql)) ? "select * from t_stu" : stuSql;
//		stuSql = "select * from t_stu where no_ =  '201311050101'";
		String matchSql = (schoolYear==null||"".equals(schoolYear)) ? "" : " and t.school_year='"+schoolYear+"'";
		matchSql += (termCode==null||"".equals(termCode)) ? "" : " and t.term_code='"+termCode+"'";
		String scoreSql = "select t.stu_id, t.COURSE_CODE coure_code, t.credit,"
				+ " t.score,t.COURSE_ATTR_CODE, t.COURSE_NATURE_CODE,t.COURSE_CATEGORY_CODE from "
				+ " t_stu_score_history t,("+stuSql+") stu where t.stu_id = stu.no_"
				+ " and t.cs=1 "
				+  matchSql;
		return scoreSql;
	}
	@Override
	public String getStuScoreSql(String stuSql, String schoolYear, String termCode){
		stuSql = (stuSql==null||"".equals(stuSql)) ? "select * from t_stu" : stuSql;
		String matchSql = (schoolYear==null||"".equals(schoolYear)) ? "" : " and t.school_year='"+schoolYear+"'";
		matchSql += (termCode==null||"".equals(termCode)) ? "" : " and t.term_code='"+termCode+"'";
		String scoreSql = "select t.stu_id, t.coure_code, t.credit,"
				+ " t.score,t.COURSE_ATTR_CODE, t.COURSE_NATURE_CODE,t.COURSE_CATEGORY_CODE from "
				+ " t_stu_score t,("+stuSql+") stu where t.stu_id = stu.no_ "
				+  matchSql;
		return scoreSql;
	}
	
	@Override
	public String getStuScoreBetterSql(String stuSql, String schoolYear, String termCode){
		String scoreSql = getStuScoreSql(stuSql, schoolYear, termCode);
		return getStuScoreBetterSql(scoreSql);
	}
	@Override
	public String getStuScoreBetterSql(String scoreSql){
		return "select * from ("+scoreSql+") t where t.score >=" +Globals.BETTER_SCORE_LINE;
	}
	@Override
	public String getStuScorePassSql(String stuSql, String schoolYear, String termCode){
		String scoreSql = getStuScoreSql(stuSql, schoolYear, termCode);
		return getStuScorePassSql(scoreSql);
	}
	@Override
	public String getStuScorePassSql(String scoreSql){
		return "select * from ("+scoreSql+") t where t.score >=" +Globals.FAIL_SCORE_LINE;
	}
	@Override
	public String getStuScoreFailSql(String stuSql, String schoolYear, String termCode){
		String scoreSql = getStuScoreSql(stuSql, schoolYear, termCode);
		return getStuScoreFailSql(scoreSql);
	}
	@Override
	public String getStuScoreFailSql(String scoreSql){
		return "select * from ("+scoreSql+") t where t.score <" +Globals.FAIL_SCORE_LINE;
	}
	@Override
	public String getStuScoreRebuildSql(String stuSql, String schoolYear, String termCode){
		String scoreSql = getStuScoreSql(stuSql, schoolYear, termCode);
		return getStuScoreRebuildSql(scoreSql);
	}
	@Override
	public String getStuScoreRebuildSql(String scoreSql){
		return "select * from ("+scoreSql+") t where t.score <" +Globals.REBUILD_SCORE_LINE;
	}
	
	@Override
	public String getStuGpaSql(String stuSql, String schoolYear, String termCode){
		String scoreSql = getStuScoreSql(stuSql, schoolYear, termCode),
			   gpaSql   = getStuGpaSql(scoreSql);
		return "select t.*, '"+schoolYear+"' as school_year, '"+termCode+"' as term_code from ("+gpaSql+") t";
	}

	@Override
	public String getStuGpaSql(String scoreSql){
		String gpaCaseWhenSql = getStuGpaCaseWhenSql(),
			   gpaSql = "select "+gpaCaseWhenSql+" as gpa, t.stu_id, t.coure_code, t.credit from ("+scoreSql+") t";
		return "select case when sum(t.credit) =0 then 0 else sum(t.gpa * t.credit)/sum(t.credit) end as gpa, t.stu_id from ("+gpaSql+") t group by t.stu_id";
	}
	
	@Override
	public String getStuGpaSql(String stuSql, String schoolYear, String termCode, String gpaCode){
		stuSql = (stuSql==null||"".equals(stuSql)) ? "select * from t_stu" : stuSql;
		List<String> matchList = new ArrayList<>();
		String sqlMa = null;
		if(schoolYear!=null&&!"".equals(schoolYear)){ sqlMa = "t.school_year='"+schoolYear+"'"; }
		else{ sqlMa = "t.school_year is null"; }
		matchList.add(sqlMa);
		if(termCode!=null&&!"".equals(termCode)){ sqlMa = "t.term_code='"+termCode+"'"; }
		else{ sqlMa = "t.term_code is null"; }
		matchList.add(sqlMa);
		if(gpaCode!=null&&!"".equals(gpaCode)) matchList.add("t.gpa_code='"+gpaCode+"'");
		String matchSql = StringUtils.join(matchList, " and ");
		matchSql = matchList.isEmpty() ? "" : (" where "+matchSql);
		String gpaSql = "select t.gpa, t.stu_id, '"+schoolYear+"' as school_year, '"+termCode+"' as term_code from t_stu_score_gpa t " +matchSql,
			   sql    = "select t.* from ("+gpaSql+")t, ("+stuSql+")stu where t.stu_id = stu.no_ and t.gpa is not null ";
		// 优化查询 但效果不明显
		/*matchSql = matchList.isEmpty() ? "" : (" and "+StringUtils.join(matchList, " and "));
		sql = "select t.gpa, t.stu_id, '"+schoolYear+"' as school_year, '"+termCode+"' as term_code from t_stu_score_gpa t, ("+stuSql+")stu"
				+ "  where t.stu_id = stu.no_" +matchSql;*/
		return sql;
	}
	@Override
	public String getStuScoreAvgSql(String stuSql, String schoolYear, String termCode){
		stuSql = (stuSql==null||"".equals(stuSql)) ? "select * from t_stu" : stuSql;
		List<String> matchList = new ArrayList<>();
		String sqlMa = null;
		if(schoolYear!=null&&!"".equals(schoolYear)){ sqlMa = "t.school_year='"+schoolYear+"'"; }
		else{ sqlMa = "t.school_year is null"; }
		matchList.add(sqlMa);
		if(termCode!=null&&!"".equals(termCode)){ sqlMa = "t.term_code='"+termCode+"'"; }
		else{ sqlMa = "t.term_code is null"; }
		matchList.add(sqlMa);
		String matchSql = StringUtils.join(matchList, " and ");
		matchSql = matchList.isEmpty() ? "" : (" where "+matchSql);
		String gpaSql = "select t.weight_avg as score, t.stu_id, '"+schoolYear+"' as school_year, '"+termCode+"' as term_code from t_stu_score_avg t " +matchSql,
			   sql    = "select t.* from ("+gpaSql+")t, ("+stuSql+")stu where t.stu_id = stu.no_ and t.score is not null ";
		return sql;
	}
	
	@Override
	public String getStuScoreAvgSql(String stuScoreSql){
		String sql = "select stu_id, case when sum(t.credit)=0 then 0 else round(sum(t.score * t.credit)/sum(t.credit),1) end score"
				+ " from ("+stuScoreSql+") t group by t.stu_id";
		return sql;
	}

	@Override
	public Double queryStuGpaAvg(String stuSql, String schoolYear, String termCode, String gpaCode){
		String gpaSql = getStuGpaSql(stuSql, schoolYear, termCode, gpaCode),
			   value  = baseDao.queryForString("select round(sum(gpa)/count(0), 2) from ("+gpaSql+")");
		return Double.valueOf(value==null ? "0" : value);
	}
	
	@Override
	public List<Double> queryStuGpa(String stuSql, String schoolYear, String termCode){
		String gpaSql = getStuGpaSql(stuSql, schoolYear, termCode);
		return baseDao.queryForListDouble("select gpa from ("+gpaSql+")");
	}
	@Override
	public List<Double> queryStuGpa(String stuSql, String schoolYear, String termCode, String gpaCode){
		String gpaSql = getStuGpaSql(stuSql, schoolYear, termCode, gpaCode);
		return baseDao.queryForListDouble("select gpa from ("+gpaSql+")");
	}
	
	@Override
	public String getStuGpaCaseWhenSql(){
		String gpaId = Constant.SCORE_GPA_BASE_CODE;
		String _gpaSql = "select * from T_STU_GPA t where t.istrue=1";
		if(baseDao.queryForCount(_gpaSql+" and t.id = "+gpaId) != 0)
			_gpaSql += " and t.id = "+gpaId;
		List<TStuGpa> gpaList = baseDao.queryForListBean(_gpaSql, TStuGpa.class);
		// 分组
		List<List<TStuGpa>> groupList = new ArrayList<>();
		groupList.add(gpaList);
		String code = null; Double gpa = null;
//		for(List<TStuGpa> gpaList : groupList){
//			if(!list.isEmpty()){
				code = gpaList.get(0).getCategory_code();
				String sql = "";
				for(TStuGpa t : gpaList){
					gpa = t.getGpa();
					if(Constant.CODE_CATEGORY_CODE_1.equals(code)){
						sql += " when t.score>="+t.getStart_()+" and t.score<="+t.getEnd_()+" then "+gpa;
					}else if(Constant.CODE_CATEGORY_CODE_2.equals(code)){
						sql += " when t.score = '"+t.getStart_()+"' then "+gpa;
					}
				}
				sql = " case " +sql+ " else 0 end";
//			}
//		}
		return sql;
	}
	
	@Override
	public Double queryAvgScore(String stuSql, String schoolYear,String termCode){
		String gpaSql = getStuGpaSql(stuSql, schoolYear, termCode, Constant.SCORE_GPA_BASE_CODE);
		String sql = "select round(avg(t.gpa),2) from ("+gpaSql+") t";
		 String val = baseDao.queryForString(sql);
		 if(val==null||val.equals("")){
			 return 0.0; 
		 }
		 Double value = Double.valueOf(val);
		return value;
	}
	
	@Override
	public Double queryAvgBookCount(String stuSql, String schoolYear, String termCode){
		String type = Constant.BEHAVIOR_BORROW_AVG;
		return getStuBehavior(stuSql,schoolYear,termCode,type);
	}
	
	@Override
	public Double queryAvgBookRke(String stuSql, String schoolYear, String termCode){
		String type = Constant.BEHAVIOR_BOOK_RKE;
		return getStuBehavior(stuSql,schoolYear,termCode,type);
	}
	
	@Override
	public Double queryAvgBreakfastCount(String stuSql,String schoolYear, String termCode){
		String type = Constant.BEHAVIOR_BREAKFAST_AVG;
		return getStuBehavior(stuSql,schoolYear,termCode,type);
	}
	
	@Override
	public Double queryAgeEarlyCount(String stuSql,String schoolYear, String termCode) {
		String type = Constant.BEHAVIOR_EARLY_AVG;
		return getStuBehavior(stuSql,schoolYear,termCode,type);
	}

	private Double getStuBehavior(String stuSql,String schoolYear,String termCode,String type){
		stuSql = (stuSql==null||"".equals(stuSql)) ? "select * from t_stu" : stuSql;
		String matchSql = (schoolYear==null||"".equals(schoolYear)) ? "" : "and bhr.school_year='"+schoolYear+"'";
		matchSql += (termCode==null||"".equals(termCode)) ? "" : "and bhr.term_code='"+termCode+"'";
		String sql = "select round(avg(bhr.value_),2) from "+Constant.TABLE_T_STU_BEHAVIOR+" bhr,("+stuSql+") stu "
				+ " where bhr.stu_id = stu.no_ "+ matchSql +" and bhr.type_ = '"+type+"'";
		 String val = baseDao.queryForString(sql);
		 if(val==null||val.equals("")){
			 return 0.0; 
		 }
		 Double value = Double.valueOf(val);
		return value;
	}

	@Override
	public String getTeachClassIdSql(String stuSql){
		// 通过教学班-行政班关联表解析
//		String sql = "select distinct(glb.teach_class_id) id from T_CLASS_TEACHING_XZB glb,("+stuSql+")stu where glb.class_id=stu.class_id";
		// 通过教学班-学生关联表解析
		String sql = "select distinct(glb.teach_class_id) id from T_CLASS_TEACHING_STU glb,("+stuSql+")stu where glb.stu_id=stu.no_";
		return sql;
	}

	@Override
	public String getStuSqlByTeachClassSql(String teachClassIdSql, String stuSql){
		String sql = "select stu.* from T_CLASS_TEACHING_STU t, ("+stuSql+")stu where t.stu_id=stu.no_ and t.teach_class_id in("+teachClassIdSql+")";
		return sql;
	}
	
	@Override
	public String getStuSqlByTeachIdSql(String teachId, String stuSql){
		String sql = "select stu.* from T_COURSE_ARRANGEMENT t, T_CLASS_TEACHING_STU x, ("+stuSql+")stu"
				+ " where t.tea_id = '"+teachId+"' and t.teachingclass_id=x.teach_class_id and x.stu_id=stu.no_";
		return sql;
	}
	
	@Override
	public List<String> queryLengthSchoolingByAllStu(){
		String stuSql = getStuSql(PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		String xzSql = "select t.length_schooling from ("+stuSql+") t where "
				+ " t.length_schooling is not null group by t.length_schooling "
				+ " order by t.length_schooling"; 
	    return baseDao.queryForListString(xzSql);
	}
	@Override
	public List<Map<String, Object>> queryBzdmBySqlOrderByCount(String tableSql,String column){
		return queryBzdmBySql(tableSql, column, "count");
	}
	
	@Override
	public List<Map<String, Object>> queryBzdmBySql(String tableSql,String column,String order){
		String str = "order by t."+column+" ";
		if(order.equals("count")){
			str =" order by count(0) desc";
		}
		String sql = "select t."+column+" as id,count(0) as count from ("+tableSql+") t where t."+column+" is not null "
				+ " group by t."+column+" "+str;
		return baseDao.queryListInLowerKey(sql,Types.VARCHAR,Types.NUMERIC);
		
	}
	@Override
	public List<TreeCode> queryBzdmListInTreeByPid(String tableName, String pid){
		List<String> mathList = new ArrayList<>();
		mathList.add("t.istrue=1");
		if(pid!=null) mathList.add("t.pid= '"+pid+"'");
		String mathSql = mathList.isEmpty() ? "" : (" where "+ListUtils.join(mathList, " and "));
		String sql = "select * from "+tableName+" t "+mathSql+" order by t.level_,t.pid,t.order_,t.code_";
		return baseDao.queryForListBean(sql, TreeCode.class);
	}
	@Override
	public List<Map<String, Object>> queryBzdmTeaSubjectIsUseList(){
		return baseDao.queryListInLowerKey(queryBzdmTeaSubjectIsUseSql());
	}
	@Override
	public String queryBzdmTeaSubjectIsUseSql(){
    	String teaSql = "select distinct(subject_id) from ("+getTeaSql(PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>())+")";
    	String subjectSql = getTreeSubjectDegreeSql();
    	String sql = "select distinct(subject.pid) id,tp.code_,tp.name_,tp.pid,tp.path_,tp.level_,tp.level_type,tp.order_,tp.istrue"
				+ " from ("+teaSql+") tea,("+subjectSql+") subject, t_Code_Subject_Degree tp"
				+ " where tea.subject_id=subject.id and subject.pid=tp.id order by tp.level_,tp.order_,tp.code_";
    	return sql;
    }
    @Override
	public Map<String,Object>  queryOriginMaxCountByStuSql(String stuSql){
		String sql = "select b.id,count(0) as value from ("+stuSql+") a,t_code_admini_div b "
				+ " where substr(a.stu_origin_id,0,2)||'0000' = b.id group by b.id order by count(0) desc";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if (list == null || list.isEmpty()){
			return null;
		}
		Map<String,Object> map = list.get(0);
		return map;
	}
    @Override
    public String queryOriginIdsById(String id){
    	String pathSql = "select path_ as qxm from t_code_admini_div where id = '"+id+"'";
    	String path = baseDao.queryForString(pathSql);
    	String sql = "select t.* from t_code_admini_div t where t.path_ like '"+path+"%'";
    	return sql;
    }
}
