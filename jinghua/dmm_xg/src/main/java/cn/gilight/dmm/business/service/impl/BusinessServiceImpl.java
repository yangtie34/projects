package cn.gilight.dmm.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.GetCachePermiss;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.entity.TCode;
import cn.gilight.dmm.business.entity.TreeCode;
import cn.gilight.dmm.business.job.BusinessJob;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.common.UserUtil;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;

/**
 * 业务service
 * 
 * @author xuebl
 * @date 2016年5月3日 下午12:29:58
 */
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {

	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessJob businessJob;
	@Resource
	private BaseDao baseDao;

	private static String 
			code_column = Constant.NEXT_LEVEL_COLUMN_CODE,
		   name_column = Constant.NEXT_LEVEL_COLUMN_NAME,
		   count_column = Constant.NEXT_LEVEL_COLUMN_COUNT,
		   order_column = Constant.NEXT_LEVEL_COLUMN_ORDER;

	@Override
	public String queryChangePasswdSystemUrl(){
		String sql = "SELECT T.URL_,T.NAME_,T.LEVEL_ FROM T_SYS_RESOURCES  T WHERE T.SHIRO_TAG = 'ht'";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		if (list.size() > 0) {
			Map<String, Object> sysObj = list.get(0);
			return MapUtils.getString(sysObj,"url_");
		}
		return "";
	}
	
	/** 学校数据  eg:'2016-05-01, schoolId, schoolName' */
	private static String SchoolStr = "";
	/** 学制  eg:'2016-05-01, 5' */
	private static String SchoolingStr = "";
	
	
	@Override
	public String getSchoolId(){
		if(isNeedInitSchoolData()) initSchoolData();
		return SchoolStr.split(",")[1];
	}

	@Override
	public String getSchoolName(){
		if(isNeedInitSchoolData()) initSchoolData();
		return SchoolStr.split(",")[2];
	}
	/** 是否需要初始化学校数据 */
	private boolean isNeedInitSchoolData(){
		boolean isNeed = false;
		String[] ary = SchoolStr.split(",");
		String nowDate = DateUtils.getNowDate();
		if(ary.length < 2 || !nowDate.equals(ary[0])){
			isNeed = true;
		}
		return isNeed;
	}
	/** 初始化学校数据 */
	private void initSchoolData(){
		Map<String, Object> map = businessDao.querySchoolData();
		String schoolId   = MapUtils.getString(map, "id"),
			   schoolName = MapUtils.getString(map, "name");
		if(schoolId != null)
			SchoolStr = DateUtils.getNowDate() +","+ schoolId +","+ schoolName;
	}
	
	@Override
	public int getMaxSchooling(){
		if(isNeedInitSchooling()) initMaxSchooling();
		return Integer.valueOf(SchoolingStr.split(",")[1]);
	}
	/** 是否需要初始化最大学制 */
	private boolean isNeedInitSchooling(){
		boolean isNeed = false;
		String[] ary = SchoolingStr.split(",");
		String nowDate = DateUtils.getNowDate();
		if(ary.length != 2 || !nowDate.equals(ary[0])){
			isNeed = true;
		}
		return isNeed;
	}
	/** 初始化最大学制 */
	private void initMaxSchooling(){
		int max = businessDao.queryMaxSchooling();
		max = max<EduUtils.getBzdmNj().size() ? max : EduUtils.getBzdmNj().size();
		SchoolingStr = DateUtils.getNowDate() +","+ max;
	}
	
	/**
	 * 权限
	 */
	
	/*@Override
	public List<String> getDeptDataList(String shiroTag){
		return getDeptDataList(shiroTag, null);
	}*/
	
	@Override
	public List<String> getDeptDataList(String shiroTag, String id){
		shiroTag += ":*";
		if(id == null){
			return GetCachePermiss.getDataServeSqlbyUserIdShrio(UserUtil.getCasLoginName(), shiroTag);
		}
		return packageDeptListById(id);
	}
	
	@Override
	public boolean hasPermssion(String shiroTag){
		return GetCachePermiss.hasPermssion(UserUtil.getCasLoginName(), shiroTag);
	}
	
	@Override
	public List<String> packageDeptListById(String id){
		// 节点数据
		Map<String, Object> map = businessDao.queryLevelListByIds(id).get(0); 
		// 节点组成权限集合 及 人数
		List<String> pidDeptList = PmsUtils.getDeptListByDeptMap(map);
		return pidDeptList;
	}
	
	@Override
	public String getDeptNameById(String id){
		return MapUtils.getString(businessDao.queryLevelById(id), "name");
	}

	@Override
	public String getDeptPidById(String deptId){
		Map<String, Object> map = businessDao.queryLevelById(deptId);
		return map.isEmpty() ? null : MapUtils.getString(map, "id");
	}

	@Override
	public String queryDeptDataName(List<String> deptList, List<AdvancedParam> advancedParamList){
		String name = null;
		List<Map<String, Object>> li = new ArrayList<>();
		String pid = AdvancedUtil.getPid(advancedParamList);
		// 查询这个pid对应的节点名称
		if(pid != null){
			li = businessDao.queryLevelListByIds(pid);
		}else if(PmsUtils.isAllPmsData(deptList)){
			name = getSchoolName();
		} else {
			String depts = null;
			for(int i=1,len=deptList.size(); i<len; i++){
				depts = deptList.get(i);
				if(!PmsUtils.isNullPmsData(depts)){
					li = businessDao.queryLevelListByIds(depts);
					break;
				}
			}
		}
		if(!li.isEmpty()){
			List<String> names = new ArrayList<>();
			for(Map<String, Object> m : li){
				names.add(MapUtils.getString(m, "name"));
			}
			name = StringUtils.join(names, ",");
		}
		return name;
	}
	
	
	@Override
	public String getNextLevelDataSqlByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade) {
		return getNextLevelSqlAryByDeptDataAndPid(deptList, pid, querySql, isShowAllDept, isDeptTeach, isTeaSql, hasTeach, schoolYear, grade)[0];
	}
	@Override
	public String getNextLevelGroupSqlByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade) {
		return getNextLevelSqlAryByDeptDataAndPid(deptList, pid, querySql, isShowAllDept, isDeptTeach, isTeaSql, hasTeach, schoolYear, grade)[1];
	}
	@Override
	public String getNextLevelOrderSqlByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade) {
		return getNextLevelSqlAryByDeptDataAndPid(deptList, pid, querySql, isShowAllDept, isDeptTeach, isTeaSql, hasTeach, schoolYear, grade)[2];
	}
	@Override
	public String[] getNextLevelSqlAryByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade) {
		Map<String, Object> map = getNextLevelMapByDeptDataAndPid(deptList, pid, querySql, isShowAllDept, isDeptTeach, isTeaSql, hasTeach, schoolYear, grade);
		return new String[]{MapUtils.getString(map, "sql"), MapUtils.getString(map, "sql_group"), MapUtils.getString(map, "sql_order")};
	}
	
	/**
	 * 获取下钻查询数据Map
	 * @param deptList 标准权限
	 * @param pid 父节点
	 * @param querySql 数据sql
	 * <br> 学生sql： select dept_id,major_id,class_id from t
	 * <br> 教职工sql： select dept_id from t
	 * @param isShowAllDept 是否显示这一层所有节点，没有业务数据的节点也显示
	 * @param isDeptTeach 是否教学组织机构
	 * @param isTeaSql 是教师sql
	 * @param hasTeach 包含教学单位
	 * @param schoolYear 学年
	 * @param grade 年级
	 * <br> 查询一个学年下的一个年级（大一...）的班级时需要传值
	 * @return String
	 * <br> { sql : 'select *, next_dept_code, next_dept_name from t', 
	 * <br>   sql_group : "select next_dept_code, next_dept_name, count(0) count from t group by next_dept_code, next_dept_name"
	 * <br>   sql_order : "select id, name_, path_, pid, next_dept_order from t_code_dept/t_code_dept_teach"
	 * <br>		level:'', level_type:'', level_name:'' }
	 */
	private Map<String, Object> getNextLevelMapByDeptDataAndPid(List<String> deptList, String pid, String querySql,
			boolean isShowAllDept, boolean isDeptTeach, boolean isTeaSql, boolean hasTeach, Integer schoolYear, Integer grade) {

		String tablename = isDeptTeach ? "T_CODE_DEPT_TEACH" : "T_CODE_DEPT";
		hasTeach = isDeptTeach&&hasTeach ? true : false;
		
		int level_ = 0; // 显示层
		String level_type = null; // 显示层 层次类型
//		boolean isHasX = false; // 是否包含系
		// 父节点，这里只表示这是本层节点的父节点，不要求拥有父节点下的所有节点权限，父节点在就行；真正的数据权限由真实的数据决定，这里只是配合查询下一层节点
		String pidForQuery = null;
		if(pid == null){
			// 权限不是学校
			if(PmsUtils.isAllPmsData(deptList)){
				level_ = 1;
				pidForQuery = getSchoolId(); // 权限ID 是 学校ID
			}else if(!PmsUtils.isNullPmsData(deptList)){ // 可能有其他节点
				String depts = null;
				for(int i=1,len=deptList.size(); i<len; i++){
					depts = deptList.get(i);
					if(!PmsUtils.isNullPmsData(depts)){
						/** 
						 * 当这一层有权限时，判断是否应该展示这一层
						 * 1.展示下一层：有一个节点 且 无其他节点
						 * 		1.1下一层是系（X）：判断是否有系这层节点
						 * 2.展示本层：本层有多个节点 或 有一个节点且有其他节点
						 */
						String ary[] = depts.split(",");
						// 1.展示下一层
						if(ary.length == 1 && PmsUtils.isNullPmsData(deptList.subList(i+1, len))){
							pidForQuery = depts; // 父节点，有系没系不影响取下一层节点
							level_ = i+1;
							// 1.1判断是否有系（X）
							if(level_ == 2){
								if(!isNextLevelIsX(pidForQuery)){ // 没有系
									level_++;
//									isHasX = false;
								}else{
//									isHasX = true;
								}
							}
						}else{ // 2.展示本层
							level_ = i;
							pidForQuery = getDeptPidById(depts.split(",")[0]); // 取父节点
						}
						break;
					}
				}
			}
		}else{
			/**
			 * 有pid，直接用
			 */
			pidForQuery = pid;
			// 获取 level
			Map<String,Object> map = businessDao.queryLevelById(pidForQuery);
			Integer level_p = PmsUtils.getLevelByDeptMap(map);
			level_ = level_p+1;
			// 当前层是系（2），再次确认是系或专业
			if(level_ == 2){
				if(!isNextLevelIsX(pidForQuery)){
					level_++;
//					isHasX = false;
				}else{
//					isHasX = true;
				}
			}
		}
		// 获取level_type
		if(level_ == 1){
			level_type = Constant.Level_Type_YX; // 本层是什么
		}
		else if(level_ == 2) level_type = Constant.Level_Type_X;
		else if(level_ == 3) level_type = Constant.Level_Type_ZY;
		else level_type = Constant.Level_Type_BJ;
		// 处理基本数据
		pidForQuery = PmsUtils.formatInSql(pidForQuery);
		// 关联的字段名，教职工sql比对dept_id，因为教职工表只有这一个字段
		String matchColoum = isTeaSql ? "dept_id" : "";
		if(isTeaSql){
			matchColoum = "dept_id";
		}else{
			if(level_ == 1) matchColoum = "DEPT_ID";
			else if(level_ == 2) matchColoum = "MAJOR_ID";
			else if(level_ == 3) matchColoum = "MAJOR_ID";
			else if(level_ == 4) matchColoum = "CLASS_ID";
		}
		
		// 下一层机构sql（只控制层，不控制具体节点），数据sql（已关联权限） // deptSql调整为关联具体的权限节点  20161230
		String deptSql = null, dataSql = null, deptDataIds = ListUtils.join(deptList, ",");
		if(level_ != 4){
			deptSql = "select id, name_, path_, pid from "+tablename+ " where istrue=1"+
					(hasTeach?"":" and level_type!='"+Constant.Level_Type_JXDW+"'") +" order by order_,code_";
			dataSql = "select s.*, cdt.path_ from ("+querySql+") s "+
					" inner join "+tablename+" cdt on s."+matchColoum+" = cdt.id";
		}else{
			deptSql = "select * from (select id, name_, path_, pid from "+tablename+ " where istrue=1"
					+ (hasTeach?" and level_type!='"+Constant.Level_Type_JXDW+"'":"") +" order by order_,code_)"
					+ " union all "
					+ "select * from (select cl.no_ id, cl.name_, cdt.path_ || cl.no_ path_, cl.teach_dept_id pid "
					+ " from t_classes cl inner join t_code_dept_teach cdt on cl.teach_dept_id = cdt.id"
					+ (schoolYear==null ? "" : 
						(" where " + 
							(grade!=null ? "cl.GRADE = "+(schoolYear+1-grade) : 
							" ( (cl.GRADE < "+schoolYear+" and (cl.GRADE+cl.LENGTH_SCHOOLING) > "+schoolYear+")"
							+ " or (cl.GRADE = "+schoolYear+") )") ) )
					+ " order by cl.no_)";
			dataSql = "select s.*, cdt.path_ || cl.no_ path_ from ("+querySql+") s "+
					" inner join t_classes cl on s.class_id = cl.no_ "+
					" inner join "+tablename+" cdt on cl.teach_dept_id = cdt.id";
		}
		// deptSql（加上用户自身的权限控制，只显示自身权限节点） sql
		String pidDeptSql = "select t.*,rownum "+order_column+" from ("+deptSql+") t where pid = "+pidForQuery,
			   pmsDeptSql = "select path_ from ("+deptSql+") where id in ("+deptDataIds+")";
		deptSql = "select pidDept.* from ("+pidDeptSql+") pidDept, ("+pmsDeptSql+") pmsDept"+
				" where pidDept.path_ like pmsDept.path_||'%' order by pidDept."+order_column;
		// 数据sql 数据只是添加对应的节点字段，无需判断是否显示所有节点，分组时才有全部节点的意义 20170103
		String sql = "select data.*, dept.id "+code_column+", dept.name_ "+name_column+
				" from ("+dataSql+") data "+
				" left join ("+deptSql+") dept on data.path_ like dept.path_||'%'";
		// 分组sql 优化所有节点的sql，数据全连接并分组之后再right join组织机构 20170103
		String sql_group = getNextLevelGroupOrderSql(getNextLevelGroupSql(sql), deptSql, isShowAllDept);
		Map<String, Object> map = new HashMap<>();
		map.put("sql", sql);
		map.put("sql_group", sql_group);
		map.put("sql_order", deptSql);
		map.put("level", level_);
		map.put("level_type", level_type);
		map.put("level_name", getLevelNameByLevelType(level_type));
		return map;
	}
	
	@Override
	public String getNextLevelGroupSql(String sql){
		return "select nvl("+code_column+",'"+Constant.CODE_Unknown+"') "+code_column+", nvl("+name_column+",'"+Constant.CODE_Unknown_Name+"') "+name_column+", count(0) "+count_column
				+ " from ("+sql+") group by "+code_column+","+name_column;
	}
	@Override
	public String getNextLevelGroupOrderSql(String groupSql, String orderSql, boolean isShowAllDept){
		return "select nvl(orderSql.id,groupSql."+code_column+") "+code_column+","
				+ "nvl(orderSql.name_,groupSql."+name_column+") "+name_column+","
				+ "nvl(groupSql."+count_column+", 0) "+count_column
				+ " from ("+groupSql+")groupSql "
				+ (isShowAllDept?"right":"left")+" join ("+orderSql+")orderSql on groupSql."+code_column+"=orderSql.id order by orderSql."+order_column;
	}
	
	// 这个节点的下级节点是否包含系
	private boolean isNextLevelIsX(String pid){
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getDeptDataListForGoingDown(List<String> deptList, String pid, Integer schoolYear){
		Map<String, Object> map = getDeptDataForGoingDown(deptList, pid, schoolYear);
		return (List<Map<String, Object>>) (map.get("queryList"));
	}
	@Override
	public Map<String, Object> getDeptDataForGoingDown(List<String> deptList, String pid, Integer schoolYear){
		return getDeptDataForGoingDownStuOrTeach(deptList, pid, schoolYear, false, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getDeptDataListForGoingDownStu(List<String> deptList, String pid, Integer schoolYear){
		Map<String, Object> map = getDeptDataForGoingDownStu(deptList, pid, schoolYear);
		return (List<Map<String, Object>>) (map.get("queryList"));
	}
	@Override
	public Map<String, Object> getDeptDataForGoingDownStu(List<String> deptList, String pid, Integer schoolYear){
		return getDeptDataForGoingDownStuOrTeach(deptList, pid, schoolYear, true, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getDeptDataListForGoingDownTeach(List<String> deptList, String pid, Integer schoolYear){
		Map<String, Object> map = getDeptDataForGoingDownTeach(deptList, pid, schoolYear);
		return (List<Map<String, Object>>) (map.get("queryList"));
	}
	@Override
	public Map<String, Object> getDeptDataForGoingDownTeach(List<String> deptList, String pid, Integer schoolYear){
		return getDeptDataForGoingDownStuOrTeach(deptList, pid, schoolYear, true, true);
	}

	/**
	 * 获取下钻查询的数据
	 * @param deptList 标准权限
	 * @param pid 父节点
	 * @param schoolYear 学年
	 * @param isStuOrTeach 是否是学生/教师类，行政机构为false
	 * @param hasTeach 是否教学单位
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getDeptDataForGoingDownStuOrTeach(List<String> deptList, String pid, Integer schoolYear,
			boolean isStuOrTeach, boolean hasTeach) {
		List<Map<String, Object>> queryList = new ArrayList<>(); // 需要查询的节点集合
		String level_type_pid = null;
		String column_id  = "id";
		if(pid == null){
			// 权限不是学校
			if(PmsUtils.isAllPmsData(deptList)){
				pid = getSchoolId(); // 权限ID 是 学校ID
				level_type_pid = Constant.Level_Type_XX;
				// 下级节点
				if(isStuOrTeach){
					if(hasTeach) queryList = businessDao.queryNextLevelTeachTeachList(pid, schoolYear);
					else queryList = businessDao.queryNextLevelTeachStuList(pid, schoolYear);
				}else{
					queryList = businessDao.queryNextLevelList(pid, schoolYear);
				}
			}else if(!PmsUtils.isNullPmsData(deptList)){ // 可能有其他节点
				String depts = null;
				for(int i=1,len=deptList.size(); i<len; i++){
					depts = deptList.get(i);
					if(!PmsUtils.isNullPmsData(depts)){
						if(i==1) level_type_pid = Constant.Level_Type_YX;
						else if(i==2) level_type_pid = Constant.Level_Type_X;
						else if(i==3) level_type_pid = Constant.Level_Type_ZY;
						queryList  = businessDao.queryLevelListByIds(depts);
						break;
					}
				}
				// 权限是单个节点时，获取其下级节点展示（eg：用户是院长，返回所有专业）
				if(queryList.size() == 1){
					String pid2 = MapUtils.getString(queryList.get(0), column_id);
					if(isStuOrTeach){
						if(hasTeach) queryList = businessDao.queryNextLevelTeachTeachList(pid2, schoolYear);
						else queryList = businessDao.queryNextLevelTeachStuList(pid2, schoolYear);
					}else{
						queryList = businessDao.queryNextLevelList(pid2, schoolYear);
					}
				}
			}
		}else{
			// 下级节点
			if(isStuOrTeach){
				if(hasTeach) queryList = businessDao.queryNextLevelTeachTeachList(pid, schoolYear);
				else queryList = businessDao.queryNextLevelTeachStuList(pid, schoolYear);
			}else{
				queryList = businessDao.queryNextLevelList(pid, schoolYear);
			}
			deptList = packageDeptListById(pid);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("deptList", deptList);
		map.put("queryList", queryList);
		map.put("level_type_pid", level_type_pid);
		return map;
	}
	
	@Override
	public String getSchoolStartDate(String schoolYear, String term){
		return businessDao.querySchoolStartDate(schoolYear, term);
	}
	
	/**
	 * 标准代码
	 */
	
	@Override
	public List<TCode> queryBzdmList(String codeType){
		return queryBzdmList(codeType, null);
	}
	
	@Override
	public List<TCode> queryBzdmList(String codeType, String codes){
		return businessDao.queryBzdmList(codeType, codes);
	}
	
	@Override
	public List<Map<String, Object>> queryBzdmListMap(String codeType){
		List<Map<String, Object>> reList = new ArrayList<>();
		List<TCode> list =  queryBzdmList(codeType);
		for(TCode t : list){
			Map<String, Object> map = new HashMap<>();
			map.put("id", t.getCode_());
			map.put("mc", t.getName_());
			reList.add(map);
		}
		return reList;
	}

	@Override
	public List<String> queryBzdmNameList(String codeType, String codes){
		List<String> li = new ArrayList<>();
		List<TCode> list = queryBzdmList(codeType, codes);
		for(TCode t : list)
			li.add(t.getName_());
		return li;
	}
	
	@Override
	public List<String> queryBzdmCodeList(String codeType){
		List<String> li = new ArrayList<>();
		List<TCode> list = queryBzdmList(codeType);
		for(TCode t : list)
			li.add(t.getCode_());
		return li;
	}
	
	/**
	 * 普通数据分组 转换 标准代码列表
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> group2BzdmList(String[][] array) {
		return group2BzdmList(array, null);
	}
	/**
	 * 普通数据分组 转换 标准代码列表
	 * @param key3   普通数据的第三个数据类型
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> group2BzdmList(String[][] array, String key3) {
		List<Map<String, Object>> list = new ArrayList<>();
		for(String[] ary : array){
			Map<String, Object> map = new HashMap<>();
			map.put("id", ary[0]);
			map.put("mc", ary[1]);
			if(ary.length >= 3 && key3 != null) map.put(key3, ary[2]);
			list.add(map);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> queryBzdmListMapInTreeByPid(String tableName, String pid){
		List<Map<String, Object>> reList = new ArrayList<>();
		List<TreeCode> list = businessDao.queryBzdmListInTreeByPid(tableName, pid);
		for(TreeCode t : list){
			Map<String, Object> map = new HashMap<>();
			map.put("id", t.getId());
			map.put("mc", t.getName_());
			reList.add(map);
		}
		return reList;
	}
	@Override
	public List<Map<String, Object>> queryBzdmListMapInTree(String tableName, Integer level_){
		List<Map<String, Object>> reList = new ArrayList<>();
		List<TreeCode> list = businessDao.queryBzdmListInTree(tableName, level_);
		for(TreeCode t : list){
			Map<String, Object> map = new HashMap<>();
			map.put("id", t.getId());
			map.put("mc", t.getName_());
			reList.add(map);
		}
		return reList;
	}
	@Override
	public List<Map<String, Object>> queryBzdmStuEducationList(){
		return group2BzdmList(Constant.Stu_Education_Group);
	}
	
	/** 标准代码-学年（近10年） */
	private static final List<Map<String, Object>> BZDM_SCHOOL_YEAR = new ArrayList<>();
	@Override
	public List<Map<String, Object>> queryBzdmSchoolYear(){
		List<Map<String, Object>> list = new ArrayList<>();
		if(BZDM_SCHOOL_YEAR.isEmpty()){
			int len =10;
			BZDM_SCHOOL_YEAR.addAll(packageBzdmSchoolYear(EduUtils.getSchoolYear4()+1-len));
		}
		list.addAll(BZDM_SCHOOL_YEAR);
		return list;
	}
	@Override
	public List<Map<String, Object>> queryBzdmXnXq(){
		List<Map<String, Object>> xnList = queryBzdmSchoolYear();
		return packageBzdmXnXq(xnList);
	}

	/** 标准代码-学年（近5年） */
	private static final List<Map<String, Object>> BZDM_SCHOOL_YEAR_5 = new ArrayList<>();
	@Override
	public List<Map<String, Object>> queryBzdmSchoolYear5(){
		List<Map<String, Object>> list = new ArrayList<>();
		if(BZDM_SCHOOL_YEAR_5.isEmpty()){
			int len = 5;
			BZDM_SCHOOL_YEAR_5.addAll(packageBzdmSchoolYear(EduUtils.getSchoolYear4()+1-len));
		}
		list.addAll(BZDM_SCHOOL_YEAR_5);
		return list;
	}
	@Override
	public List<Map<String, Object>> queryBzdmXnXq5(){
		List<Map<String, Object>> xnList = queryBzdmSchoolYear5();
		return packageBzdmXnXq(xnList);
	}

	@Override
	public List<Map<String, Object>> queryBzdmScoreXnXq(){
		return businessJob.getBzdmScoreXnXq();
	}
	@Override
	public List<Map<String, Object>> queryBzdmGkXnXq(){
		return businessJob.getBzdmGkXnXq();
	}	
	@Override
	public List<Map<String, Object>> queryBzdmScoreXn(){
		return businessJob.getBzdmScoreXn();
	}
	@Override
	public List<Map<String, Object>> queryBzdmSchoolYear(String table, String column_schoolYear){
		Integer year = businessDao.queryMinSchoolYear(table, column_schoolYear);
		return packageBzdmSchoolYear(year);
	}
	
	@Override
	public List<Map<String, Object>> queryBzdmXnXq(String table, String column_schoolYear){
		List<Map<String, Object>> xnList = queryBzdmSchoolYear(table, column_schoolYear);
		return packageBzdmXnXq(xnList);
	}

	/**
	 * 标准代码学年（当前学年 到 最小年）
	 * @param min_year 最小年
	 */
	private List<Map<String, Object>> packageBzdmSchoolYear(Integer min_year) {
		List<Map<String, Object>> list = new ArrayList<>();
		int this_year = EduUtils.getSchoolYear4();
		min_year = min_year!=null ? min_year : this_year;
		String year2 = null;
		for( ; this_year>=min_year; this_year--){
			Map<String, Object> map = new HashMap<>();
			year2 = this_year+"-"+(this_year+1);
			map.put("id", year2);
			map.put("mc", year2+"学年");
			list.add(map);
		}
		return list;
	}
	/**
	 * 标准代码学年-学期（到当前学期）
	 * @param xnList 学年list
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> packageBzdmXnXq(List<Map<String, Object>> xnList){
		String[] termAry = EduUtils.getSchoolYearTerm(DateUtils.getNowDate());
		String termCode = termAry[1],
			   term1 = Globals.TERM_FIRST, term2 = Globals.TERM_SECOND, middle = " ",
			   termName1 = "第一学期", termName2 = "第二学期",
			   name1 = middle+termName1, name2 = middle+termName2;
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<>();
		for(int i=0,len=xnList.size(); i<len; i++){
			map = xnList.get(i);
			String yearId = MapUtils.getString(map, "id");
			Map<String, Object> m2 = new HashMap<>(), m1 = new HashMap<>();
			if(i == 0){
				if(termCode.equals(term2)){
					m2.put("id", yearId+","+term2);
					m2.put("mc", yearId+name2);
					list.add(m2);
					m1.put("id", yearId+","+term1);
					m1.put("mc", yearId+name1);
					list.add(m1);
				}else if(termCode.equals(term1)){
					m1.put("id", yearId+","+term1);
					m1.put("mc", yearId+name1);
					list.add(m1);
				}
			}else{
				m2.put("id", yearId+","+term2);
				m2.put("mc", yearId+name2);
				list.add(m2);
				m1.put("id", yearId+","+term1);
				m1.put("mc", yearId+name1);
				list.add(m1);
			}
		}
		return list;
	}
	
	/** 标准代码-学期 */
	private static final List<Map<String, Object>> BZDM_TERM_CODE = new ArrayList<>();
	@Override
	public List<Map<String, Object>> queryBzdmTermCode(){
		List<Map<String, Object>> list = new ArrayList<>();
		if(BZDM_TERM_CODE.isEmpty()){
			Map<String, Object> m1 = new HashMap<>(), m2 = new HashMap<>();
			m1.put("id", Globals.TERM_FIRST); m1.put("mc", "第一学期");
			m2.put("id", Globals.TERM_SECOND); m2.put("mc", "第二学期");
			BZDM_TERM_CODE.add(m1); BZDM_TERM_CODE.add(m2);
		}
		list.addAll(BZDM_TERM_CODE);
		return list;
	}
	
	@Override
	public List<Map<String, Object>> queryBzdmNj(){
		List<Map<String, Object>> list = new ArrayList<>();
		list.addAll(EduUtils.getBzdmNj());
		return list.subList(0, getMaxSchooling());
	}
	@Override
	public List<Map<String, Object>> queryBzdmSex(){
		return queryBzdmListMap(Constant.CODE_SEX_CODE);
	}
	@Override
	public List<Map<String, Object>> queryBzdmScholarship(){
		return queryBzdmListMap(Constant.CODE_AWARD_CODE);
	}
	@Override
	public List<Map<String, Object>> queryBzdmSubjectDegree(){
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String, Object>> li = businessDao.querySubjectDegreeUsefulList();
		for(Map<String, Object> map : li){
			Map<String, Object> m = new HashMap<>();
			m.put("id", MapUtils.getString(map, "id"));
			m.put("mc", MapUtils.getString(map, "name_"));
			list.add(m);
		}
		return list;
	}
	@Override
	public List<Map<String, Object>> queryBzdmCourseAttr(){
		return queryBzdmListMap(Constant.CODE_COURSE_ATTR_CODE);
	}
	@Override
	public List<Map<String, Object>> queryBzdmCourseNature(){
		return queryBzdmListMap(Constant.CODE_COURSE_NATURE_CODE);
	}
	@Override
	public List<Map<String, Object>> queryBzdmScoreType(){
		return group2BzdmList(Constant.Score_Type_Group);
	}
	@Override
	public List<Map<String, Object>> queryBzdmScoreTarget(){
		return group2BzdmList(Constant.Score_Target_Group, "unit");
	}
	@Override
	public List<Map<String, Object>> queryBzdmTeaEducationList(){
		return queryBzdmListMapInTree(Constant.TABLE_T_CODE_EDUCATION, 1);
	}
	@Override
	public List<Map<String, Object>> queryBzdmDegree(){
		return queryBzdmListMapInTree(Constant.TABLE_T_CODE_DEGREE, 1);
	}

	@Override
	public Integer getLevelById(String id){
		Map<String,Object> map = businessDao.queryLevelById(id);
		return MapUtils.getInteger(map, "level_");
	}
	@Override
	public String getLevelTypeById(String id){
		Map<String,Object> map = businessDao.queryLevelById(id);
		return MapUtils.getString(map, "level_type");
	}
	@Override
	public String getLevelNameById(String id){
		return getLevelNameByLevelType(getLevelTypeById(id)); 
	}
	@Override
	public String getLevelNameByPid(String pid){
		int schoolYear = EduUtils.getSchoolYear4();
		List<String> list = businessDao.queryNextLevelTeachTeachIdList(pid, schoolYear);
	    String id = null;
		if (list != null && !list.isEmpty()){
	    	id = list.get(0);
	    }
		return getLevelNameById(id); 
	}
	@Override
	public String getLevelNameByLevelType(String levelType){
		String name  = "机构";
		if( levelType == null){return "机构";}
		switch (levelType){
		        case Constant.Level_Type_XX:
		        	name = Constant.Level_Type_XX_Name;
		        	break;
		        case Constant.Level_Type_YX:
		        	name = Constant.Level_Type_YX_Name;
		        	break;
		        case Constant.Level_Type_X:	
		        	name = Constant.Level_Type_X_Name;
		        	break;
		        case Constant.Level_Type_ZY:
		        	name = Constant.Level_Type_ZY_Name;
		        	break;
		        case Constant.Level_Type_BJ:
		        	name = Constant.Level_Type_BJ_Name;
		        	break;
		        default:
		        	break;
		}
		return name;
	}
	
	@Cacheable(value="xgTeaCache")
	@Override
	public List<Map<String, Object>> queryBzdmStuEducationList(List<String> deptList){
		String stuSql = businessDao.getStuSql(deptList,new ArrayList<AdvancedParam>());
	    return  queryBzdmStuEducationList(stuSql);
	}
	@Override
	public List<Map<String, Object>> queryBzdmStuEducationList(String stuSql){
		List<Map<String,Object>> list1 =businessDao.queryEduList(stuSql);
		List<Map<String, Object>> list = queryBzdmStuEducationList();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>(); 
		for(int i =1;i<list.size();i++){
			String temp = MapUtils.getString(list.get(i), "id");
			for(int j=0;j<list1.size();j++){
				String temp1 = MapUtils.getString(list1.get(j), "code");
				if(temp != null && temp1 !=null && temp.equals(temp1)){
					result.add(list.get(i));
				}
			}
		}
		if (result.size()!=1){
			result.add(0,list.get(0));
		}
		return result;
	}
	@Override
	public List<Map<String,Object>> queryBzdmLengthSchooling(){
		String[] ary = {"零","一","二","三","四","五"};
		String tableSql = businessDao.getStuSql(PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		List<Map<String,Object>> xzlist = businessDao.queryBzdmBySql(tableSql,"length_schooling","other"),
		                           list = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> xzMap : xzlist){
			int xz = MapUtils.getIntValue(xzMap, "id");
			if(xz > ary.length-1) continue;
			Map<String,Object> temp = new HashMap<String, Object>();
			temp.put("id", xz);
			temp.put("mc", ary[xz]+"年制");
		    list.add(temp);
		}
	    return list;
	}
	@Override
	public List<Map<String, Object>> queryBzdmByTCodeIsUse(String group,
			String column, String codeType, String orderType) {
		String tableSql = businessDao.getStuSql(PmsUtils.getPmsAll(),
				new ArrayList<AdvancedParam>());
		if (group.equals("tea")) {
			tableSql = businessDao.getTeaSql(PmsUtils.getPmsAll(),
					new ArrayList<AdvancedParam>());
		}
		List<Map<String, Object>> list = businessDao.queryBzdmBySqlOrderByCount(tableSql,column),
							  bzdmlist = queryBzdmListMap(codeType), 
							    result = new ArrayList<Map<String, Object>>();
		if (orderType != null && orderType.equals("count")) {
			result = getListFiltrateByList(list,bzdmlist,orderType);
		}else{
			result = getListFiltrateByList(bzdmlist,list,orderType);
		}
		return result;
	}
	@Override
	public List<Map<String,Object>> queryBzdmZyjszwList(){
		return queryBzdmListMapInTreeByPid(Constant.TABLE_T_CODE_ZYJSZW, Constant.CODE_ZYJSZW_ID_010);
	}
	@Override
	public List<Map<String,Object>> queryBzdmZyjszwJbList(){
		return queryBzdmListMap(Constant.CODE_ZYJSZW_JB_CODE);
	}
	@Override
	public List<Map<String,Object>> queryBzdmTeaType(){
		String tableSql = businessDao.getTeaSql(PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		List<Map<String,Object>> list= businessDao.queryBzdmBySql(tableSql,"AUTHORIZED_STRENGTH_ID", "other"),
				                 bzdmlist = queryBzdmListMapInTree("t_code_authorized_strength",1);
		return getListFiltrateByList(bzdmlist,list,"other");
	}
	@Override
	public List<Map<String,Object>> queryBzdmTeaSource(){
		String tableSql = businessDao.getTeaSql(PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		List<Map<String,Object>> list= businessDao.queryBzdmBySql(tableSql,"TEA_SOURCE_ID", "count"),
				                 bzdmlist = queryBzdmListMapInTree("t_code_tea_source",1);
		return getListFiltrateByList(list,bzdmlist,"count");
	}
	@Override
	public List<Map<String,Object>> queryBzdmSsjsList(){
		String[] ary = {"否","是"};
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		    for (int i=1;i>-1;i--){
		    	Map<String,Object> temp  = new HashMap<String, Object>();
		    	temp.put("id", i);
		    	temp.put("mc", ary[i]);
		    	result.add(temp);
		    }
		return result;
	}
   @Override
   public List<Map<String,Object>> queryBzdmTeaEduList(){
	   String tableSql = businessDao.getTeaSql(PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		List<Map<String,Object>> list= businessDao.queryBzdmBySql(tableSql,"EDU_ID", "other"),
				                 bzdmlist = queryBzdmTeaEducationList();
		return getListFiltrateByList(bzdmlist,list,"other");
   }
   @Override
   public List<Map<String,Object>> queryBzdmTeaDegreeList(){
	   String tableSql = businessDao.getTeaSql(PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		List<Map<String,Object>> list= businessDao.queryBzdmBySql(tableSql,"DEGREE_ID", "other"),
				                 bzdmlist = queryBzdmDegree();
		return getListFiltrateByList(bzdmlist,list,"other");
   }
   @Override
   @Cacheable(value="xgTeaCache")
   public List<Map<String,Object>> queryBzdmStuEduList(){
		List<String> deptList = PmsUtils.getPmsAll();
 	   return queryBzdmStuEducationList(deptList);
   }
   @Override
   public List<Map<String,Object>> queryBzdmSubjectList(String group){
	   List<Map<String,Object>> list  = group.equals("tea")?businessDao.queryBzdmTeaSubjectIsUseList():businessDao.querySubjectDegreeUsefulList(),
			                    result = new ArrayList<Map<String,Object>>();
       for (Map<String,Object> map :list){
    	   Map<String,Object> temp = new HashMap<String, Object>();
    	   String id = MapUtils.getString(map, "id");
    	   String mc = MapUtils.getString(map, "name_");
    	   temp.put("id", id);temp.put("mc", mc);
    	   result.add(temp);
       }
       return result;
   }
   
   @Cacheable(value="xgTeaCache")
   @Override
   public String getOriginIdByAbsoluteScale(){
	   int schoolYear = EduUtils.getSchoolYear4();
	   String stuSql = businessDao.getStuSql(schoolYear, PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
       Map<String, Object> map = businessDao.queryOriginMaxCountByStuSql(stuSql);
       if(map != null && !map.isEmpty()){
    	   String id = MapUtils.getString(map, "id");
           int count = MapUtils.getIntValue(map, "value"),
               all = businessDao.queryStuCount(schoolYear, PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
           Double scale = MathUtils.getDivisionResult(count, all, 2);
           if(scale >Constant.ABSOLUTE_ORIGIN_SCALE){
        	   return id;
           }
       }
       return null;
   }
	/**
	 * 两个list取交集
	 * @param bzdmlist orderType == "count" ?全部编码的集合:包含部分编码和各个编码人数的集
	 * @param list orderType == "count" ?包含部分编码和各个编码人数的集合:全部编码的集合
	 * @param orderType 排序类型
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> getListFiltrateByList(List<Map<String, Object>> bzdmlist
			,List<Map<String,Object>> list,String orderType) {
		List<Map<String, Object>> other = list,result = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> temp : bzdmlist) {
			String bzdm = MapUtils.getString(temp, "id");
			for (Map<String, Object> temp1 : other) {
				String id = MapUtils.getString(temp1, "id");
				if (bzdm != null && id != null && id.equals(bzdm)){
					if (orderType.equals("count")) {
						result.add(temp1);
					} else {
						result.add(temp);
					}
				}
			}
		}
		return result;
	}
}
