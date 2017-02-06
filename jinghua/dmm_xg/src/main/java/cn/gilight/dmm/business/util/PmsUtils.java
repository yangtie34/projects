package cn.gilight.dmm.business.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jhnu.syspermiss.GetCachePermiss;

import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.StringUtils;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 上午11:49:47
 */
public class PmsUtils {

	
	/** 全部权限 */
	private static final String[] PMS_ALL = {"'0'", "''", "''", "''", "''"};
	/** 空权限 */
	private static final String[] PMS_NULL = {"''", "''", "''", "''", "''"};
	/** 班级权限的level */
	public static final int PMS_BJ_LEVEL = PMS_ALL.length;
	
	/** 获取空权限 */
	private static String[] getPmsNull(){
		String[] ary = new String[PMS_NULL.length];
		for(int i=0,len=PMS_NULL.length; i<len; i++){
			ary[i] = PMS_NULL[i];
		}
		return ary;
	}
	/** 全部权限 */
	private static final List<String> PMS_ALL_LIST = new ArrayList<>();
	
	/**
	 * 获取全部权限
	 * @return List<String>
	 * <br> [ "'0'", "''", "''", "''", "''" ]
	 */
	public static List<String> getPmsAll(){
		List<String> deptList = new ArrayList<>();
		if(PMS_ALL_LIST.isEmpty()){
			for(String s : PMS_ALL) deptList.add(s);
		}
		deptList.addAll(PMS_ALL_LIST);
		return deptList;
	}
	
	/**
	 * 判断权限是否是全部权限（学校权限）
	 * @param deptList
	 * @return boolean
	 */
	public static boolean isAllPmsData(List<String> deptList){
		boolean isAllPms = false;
		if(deptList!=null && !deptList.get(0).equals("''")){
			isAllPms = true;
		}
		return isAllPms;
	}
	/**
	 * 判断权限是否是空权限（所有节点为"''"）
	 * @param deptList 标准权限
	 * @return boolean
	 */
	public static boolean isNullPmsData(List<String> deptList){
		boolean isNull = true;
		for(String str : deptList){
			isNull = isNullPmsData(str);
			if(!isNull) break;
		}
		return isNull;
	}
	/**
	 * 权限节点是否是空权限
	 * @param depts 权限节点
	 * @return boolean
	 */
	public static boolean isNullPmsData(String depts){
		return "''".equals(depts);
	}

	/**
	 * 根据权限Ids和节点类型 封装为这个节点的标准权限
	 * @param depts 权限ids
	 * @param level_type 节点类型
	 * @return List<String>
	 */
	/*public static List<String> getDeptListByDeptAndLevelType(String depts, String level_type){
		String[] deptAry = getPmsNull();
		if(depts.indexOf("'")!=0){ // 对于depts不是数字的情况，需要在外面加引号 20160711
			depts = "'"+depts+"'";
		}
		switch (level_type) {
		case Constant.Level_Type_YX:
			deptAry[1] = depts;
			break;
		case Constant.Level_Type_X:
			deptAry[2] = depts;
			break;
		case Constant.Level_Type_ZY:
			deptAry[3] = depts;
			break;
		case Constant.Level_Type_BJ:
			deptAry[4] = depts;
			break;
		default:
			break;
		}
		// 定义这个节点的权限
		return ListUtils.ary2List(deptAry);
	}*/

	/**
	 * 获取节点在标准权限中的层次/下标
	 * 
	 * @param map
	 * @return int
	 */
	public static int getLevelByDeptMap(Map<String, Object> map){
		String level_type = MapUtils.getString(map, "level_type");
		Integer level_ = MapUtils.getInteger(map, "level_"); // 教学单位（JXDW）返回原值
		switch (level_type) {
		case Constant.Level_Type_YX:
			level_ = 1;
			break;
		case Constant.Level_Type_X:
			level_ = 2;
			break;
		case Constant.Level_Type_ZY:
			level_ = 3;
			break;
		case Constant.Level_Type_BJ:
			level_ = 4;
			break;
		default:
			break;
		}
		return level_;
	}
	
	/**
	 * 根据权限Ids和节点类型 封装为这个节点的标准权限，如果下级节点是班级（没有level_）同样适用
	 * <br> id必须有值；level_:'' 或 level_type:'BJ' 任一即可；
	 * @param Map<String, Object> map 下一级节点数据  {id:'', name:'', pid:'', level_type:'', [level_:''] }
	 * @return List<String>
	 */
	public static List<String> getDeptListByDeptMap(Map<String, Object> map){
		if(!map.containsKey("id")){
			return null;
		}
		List<String> list = null;
		String depts = MapUtils.getString(map, "id");
		if(map.containsKey("level_")){
			list = getDeptListByDeptAndLevel(depts, MapUtils.getInteger(map, "level_"));
		}else if(map.containsKey("level_type") && Constant.Level_Type_BJ.equals(MapUtils.getString(map, "level_type"))){
			list = getDeptListByClassIds(depts);
		}
		return list;
	}
	/**
	 * 根据权限Ids和节点类型 封装为这个节点的标准权限
	 * @param depts 权限ids
	 * @param level_type 节点类型
	 * @return List<String>
	 */
	public static List<String> getDeptListByDeptAndLevel(String depts, Integer level){
		String[] deptAry = getPmsNull();
		if(depts.indexOf("'")!=0){ // 对于depts不是数字的情况，需要在外面加引号 20160711
			depts = "'"+depts+"'";
		}
		if(level != null && level > 0 && level < deptAry.length){
			deptAry[level] = depts;
		}else if(level == 0){
			return  getPmsAll();
		}
		// 定义这个节点的权限
		return ListUtils.ary2List(deptAry);
	}
	/**
	 * 根据班级Ids 封装这个节点的标准权限
	 * @param classIds 班级ids
	 * @return List<String>
	 */
	public static List<String> getDeptListByClassIds(String classIds){
		String[] deptAry = getPmsNull();
		if(classIds.indexOf("'")!=0){
			classIds = "'"+classIds+"'";
		}
		deptAry[deptAry.length-1] = classIds;
		// 定义这个节点的权限
		return ListUtils.ary2List(deptAry);
	}
	
	/**
	 * 获取标准权限的节点；eg：'1001','1002'
	 * @param deptList
	 * @return String
	 */
	public static String getDeptIdsByDeptList(List<String> deptList){
		return ListUtils.join(deptList, ",");
	}
	
	
	/**
	 * 获取某一个节点的权限sql-教学
	 * @param deptId 节点
	 * @param deptList 标准权限
	 * @return String
	 * <br> select id from T_CODE_DEPT_TEACH union select id from t_classes
	 */
	public static String getDataTeachSqlByDeptData(String deptId, List<String> deptList){
		return getDataSqlByDeptAndData("deptTeach", deptId, deptList);
	}
	/**
	 * 获取某一个节点的权限sql-行政
	 * @param deptId 节点
	 * @param deptList 标准权限
	 * @return String
	 * <br> select id from T_CODE_DEPT union select id from t_classes
	 */
	public static String getDataSqlByDeptData(String deptId, List<String> deptList){
		return getDataSqlByDeptAndData("dept", deptId, deptList);
	}
	/**
	 * 获取某一个节点的权限sql
	 * @param deptType 节点类型；"dept"/"deptTeach"
	 * @param deptId 节点ID
	 * @param deptList 标准权限
	 * @return String
	 */
	private static String getDataSqlByDeptAndData(String deptType, String deptId, List<String> deptList){
		return GetCachePermiss.getDataSqlByDeptAndData(deptType, deptId, deptList);
		/*boolean isDeptTeach = deptType!=null && "dept".equals(deptType) ? false : true;
		String tableName = isDeptTeach ? "t_code_dept_teach" : "t_code_dept";
		String deptListString = ListUtils.join(deptList, ",");
		String deptIds = formatInSql(deptId);
		String deptSql = "select t.id from"
				+ " (select t.id,t.path_ from "+tableName+" t,"
				+ " (select t.* from "+tableName+" t where t.id in ("+deptListString+")) pa"
				+ " where substr(t.path_, 1, length(pa.path_)) = pa.path_ )t,"
				+ " (select t.* from "+tableName+" t where t.id in ("+deptIds+")) pa"
				+ " where substr(t.path_, 1, length(pa.path_)) = pa.path_";
		if(isDeptTeach){
			deptSql += " union all "
					+ " select a.no_ from"
					+ " (select class.no_ from"
					+ " (select t.id,t.path_ from "+tableName+" t,"
					+ " (select t.* from "+tableName+" t where t.id in ("+deptListString+")) pa"
					+ " where substr(t.path_, 1, length(pa.path_)) = pa.path_ )t, t_classes class"
					+ " where t.id = class.teach_dept_id) a,"
					+ " (select class.no_ from"
					+ " (select t.id,t.path_ from "+tableName+" t,"
					+ " (select t.* from "+tableName+" t where t.id in ("+deptIds+")) pa"
					+ " where substr(t.path_, 1, length(pa.path_)) = pa.path_ )t, t_classes class"
					+ " where t.id = class.teach_dept_id"
					+ " union select no_ from t_classes cls where cls.no_ in ("+deptIds+"))b where a.no_ = b.no_";
		}
		return deptSql;*/
//		String sql = "select t.id from"
//				+ " (select t.id,t.path_ from t_code_dept_teach t,"
//				+ " ( select t.* from t_code_dept_teach t where t.id in ("+deptListString+") ) pa"
//				+ " where substr(t.path_, 1, length(pa.path_)) = pa.path_ )t,"
//				+ " (select t.* from t_code_dept_teach t where t.id in ("+deptIds+") ) pa"
//				+ " where substr(t.path_, 1, length(pa.path_)) = pa.path_"
//					+ " union all"
//				+ " select a.no_ from"
//				+ " (select class.no_ from"
//				+ " (select t.id,t.path_ from t_code_dept_teach t,"
//				+ " ( select t.* from t_code_dept_teach t where t.id in ("+deptListString+") ) pa"
//				+ " where substr(t.path_, 1, length(pa.path_)) = pa.path_ )t, t_classes class"
//				+ " where t.id = class.teach_dept_id) a,"
//				+ " (select class.no_ from"
//				+ " (select t.id,t.path_ from t_code_dept_teach t,"
//				+ " ( select t.* from t_code_dept_teach t where t.id in ("+deptIds+") ) pa"
//				+ " where substr(t.path_, 1, length(pa.path_)) = pa.path_ )t, t_classes class"
//				+ " where t.id = class.teach_dept_id"
//				+ " union  select no_ from t_classes cls where cls.no_ in ("+deptIds+") )b where a.no_ = b.no_";
		
	}
	
	
	/**
	 * 获取某一个节点的权限-教学
	 * @param deptId 节点ID
	 * @param deptList 标准权限
	 * @return List<String>
	 */
	public static List<String> getDataTeachByDeptData(String deptId, List<String> deptList){
		return getDataByDeptAndData("deptTeach", deptId, deptList);
	}
	/**
	 * 获取某一个节点的权限-行政
	 * @param deptId 节点ID
	 * @param deptList 标准权限
	 * @return List<String>
	 */
	public static List<String> getDataByDeptData(String deptId, List<String> deptList){
		return getDataByDeptAndData("dept", deptId, deptList);
	}
	/**
	 * 获取某一个节点的权限
	 * @param deptType 节点类型；"dept"/"deptTeach"
	 * @param deptId 节点ID
	 * @param deptList 标准权限
	 * @return List<String>
	 */
	public static List<String> getDataByDeptAndData(String deptType, String deptId, List<String> deptList){
		return GetCachePermiss.getDataByDeptAndData(deptType, deptId, deptList);
	}

	/**
	 * 格式化sql参数 加引号用于sql条件中的 in()
	 * @param values 102,203
	 * @return String '102','203'
	 */
	public static String formatInSql(String values){
		if(values != null && values.indexOf("select") == -1){
			List<String> li = new ArrayList<>();
			String[] ary = values.split(",");
			for(int i=0,len=ary.length; i<len; i++){
				String s = ary[i];
				if(s.indexOf("'") != 0){
					s = "'"+s+"'";
				}
				li.add(s);
			}
			values = StringUtils.join(li, ",");
		}
		return values;
	}

	/**
	 * 格式化sql参数 解除sql条件中的引号 ''
	 * @param values '102','203'
	 * @return String 102,203
	 */
	public static String formatOutSql(String values){
		if(values != null){
			if(values.indexOf("'")==0 && values.lastIndexOf("'")+1 == values.length()){
				values = values.replaceAll("'", "");
			}
		}
		return values;
	}
}
