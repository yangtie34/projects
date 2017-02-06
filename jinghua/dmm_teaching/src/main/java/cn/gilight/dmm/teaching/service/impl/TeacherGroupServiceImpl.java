package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.AgeUtils;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.dao.TeacherGroupDao;
import cn.gilight.dmm.teaching.service.TeacherGroupService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;


/**
 * 师资队伍
 * 
 * @author xuebl
 * @date 2016年7月12日 上午11:18:13
 */
@Service("teacherGroupService")
public class TeacherGroupServiceImpl implements TeacherGroupService{

	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private TeacherGroupDao teacherGroupDao;

	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"teacherGroup";
	// 详情
//	private static final String ShiroTag_detail = Constant.ShiroTag_Teaching+":getTeaDetail";
	// 详情-下载
//	private static final String ShiroTag_down = Constant.ShiroTag_Teaching+":down";
	
	/** 副高级以上 */
	private static final String Type_deputyAbove = "deputyAbove";
	/** 教授 */
	private static final String Type_professor = "professor";
	/** 专任教师 */
	private static final String Type_zrjs = "zrjs";
	/** 外聘教师 */
	private static final String Type_wpjs = "wpjs";
	/** 高级人才 */
	private static final String Type_senior = "senior";
	/** 教职工类型 */
	private static final String Type_AUTHORIZED_STRENGTH = AdvancedUtil.Tea_AUTHORIZED_STRENGTH_ID;
	/** 职称等级 */
	private static final String Type_technical = "technical";
	/** 学位 */
	private static final String Type_degree = "degree";
	/** 学历 */
	private static final String Type_edu = "edu";
	/** 学科 */
	private static final String Type_subject = "subject";
	/** 年龄 */
	private static final String Type_age = "age";
	/** 教龄 */
	private static final String Type_schoolage = "schoolage";
	/** 高职称 */
	private static final String High_Technical = "High_Technical";
	/** 高学位 */
	private static final String High_Degree = "High_Degree";
	/** 高学历 */
	private static final String High_Edu = "High_Edu";
	

	private static String 
			code_column = Constant.NEXT_LEVEL_COLUMN_CODE,
		   name_column = Constant.NEXT_LEVEL_COLUMN_NAME,
		   count_column = Constant.NEXT_LEVEL_COLUMN_COUNT,
		   order_column = Constant.NEXT_LEVEL_COLUMN_ORDER;
	
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAbstract(List<AdvancedParam> advancedParamList){
		/**
		 * 教职工、副高级以上、教授、专任、外聘、高层次
		 */
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList"),
							stuAdvancedList = (List<AdvancedParam>) paramM.get("stuAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		// 教职工
		int count = baseDao.queryForCount(businessDao.getTeaSql(deptList, teaAdvancedList));
		// 副高级以上
		AdvancedParam zyjszwJbAdp = getAbstractAdp(Type_deputyAbove);
		AdvancedUtil.add(teaAdvancedList, zyjszwJbAdp);
		int deputyAbove = baseDao.queryForCount(businessDao.getTeaSql(deptList, teaAdvancedList));
		teaAdvancedList.remove(zyjszwJbAdp);
		// 教授
		AdvancedParam zyjszwAdp = getAbstractAdp(Type_professor);
		AdvancedUtil.add(teaAdvancedList, zyjszwAdp);
		int professor = baseDao.queryForCount(businessDao.getTeaSql(deptList, teaAdvancedList));
		teaAdvancedList.remove(zyjszwAdp);
		// 专任、外聘
		AdvancedParam authorizedStrengthAdp = getAbstractAdp(Type_zrjs); AdvancedUtil.add(teaAdvancedList, authorizedStrengthAdp);
		int zrjs = baseDao.queryForCount(businessDao.getTeaSql(deptList, teaAdvancedList));
		authorizedStrengthAdp = getAbstractAdp(Type_wpjs); AdvancedUtil.add(teaAdvancedList, authorizedStrengthAdp);
		int wpjs = baseDao.queryForCount(businessDao.getTeaSql(deptList, teaAdvancedList));
		teaAdvancedList.remove(authorizedStrengthAdp);
		// 高层次
		AdvancedParam seniorAdp = getAbstractAdp(Type_senior);
		AdvancedUtil.add(teaAdvancedList, seniorAdp);
		String teaSeniorSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		int senior = baseDao.queryForCount(teaSeniorSql);
		teaAdvancedList.remove(seniorAdp);
		// return
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("count", count);
		reMap.put(Type_deputyAbove, deputyAbove);
		reMap.put(Type_professor, professor);
		reMap.put(Type_zrjs, zrjs);
		reMap.put(Type_wpjs, wpjs);
		reMap.put(Type_senior, senior);
		// 生师比
		reMap.put("stuTeaScale", getStuTeaScale(getStuConvertCount(deptList, stuAdvancedList), getTeaConvertCount(zrjs, wpjs)));
		// 外聘教师与专任教师比 (1:x)
		reMap.put("wpjsZrjsScale", MathUtils.getDivisionResult(zrjs, wpjs, 1));
		// 高级人才分组
		reMap.put("seniorGroup", teacherGroupDao.querySeniorGroup(teaSeniorSql));
		return reMap;
	}
	
	/**
	 * 获取师资详情中的 高级参数
	 * @param type 师资类型
	 * @return AdvancedParam
	 */
	private AdvancedParam getAbstractAdp(String type){
		AdvancedParam adp = null;
		if(type != null){
			if(Type_deputyAbove.equals(type)){
				adp = new AdvancedParam(AdvancedUtil.Group_Tea, AdvancedUtil.Tea_ZYJSZW_JB_CODE, Constant.High_Technical);
			}else if(Type_professor.equals(type)){
				adp = new AdvancedParam(AdvancedUtil.Group_Tea, AdvancedUtil.Tea_ZYJSZW_ID, Constant.Teacher_Professor);
			}else if(Type_senior.equals(type)){
				adp = new AdvancedParam(AdvancedUtil.Group_Tea, AdvancedUtil.Tea_SENIOR_CODE, AdvancedUtil.ALL_ID_ALL);
			}else if(Type_zrjs.equals(type)){
				adp = new AdvancedParam(AdvancedUtil.Group_Tea, AdvancedUtil.Tea_AUTHORIZED_STRENGTH_ID, Constant.CODE_AUTHORIZED_STRENGTH_ID_11);
			}else if(Type_wpjs.equals(type)){
				adp = new AdvancedParam(AdvancedUtil.Group_Tea, AdvancedUtil.Tea_AUTHORIZED_STRENGTH_ID, Constant.CODE_AUTHORIZED_STRENGTH_ID_50);
			}
		}
		return adp;
	}
	/**
	 * 获取师资详情中的 高级参数
	 * @param type 师资类型
	 * @param seniorCode 高级人才代码
	 * @return AdvancedParam
	 */
	private AdvancedParam getAbstractAdp(String type, String seniorCode){
		AdvancedParam adp = null;
		if(Type_senior.equals(type) && seniorCode != null && !"null".equals(seniorCode))
			adp = new AdvancedParam(AdvancedUtil.Group_Tea, AdvancedUtil.Tea_SENIOR_CODE, seniorCode);
		else adp = getAbstractAdp(type);
		return adp;
	}
	
	/**
	 * 获取生师比
	 * 生师比 = 折合在校生数 / 教师总数
	 * @param stuCount 折合学生数量
	 * @param teaCount 折合教师数量
	 * @return double
	 * <br> 22.5 -> 22.5 : 1
	 */
	public double getStuTeaScale(double stuCount, double teaCount){
		return MathUtils.getDivisionResult(stuCount, teaCount, 1);
	}
	
	/**
	 * 获取生师比中的折合学生数 <br>
	 * <br>
	 * 折合在校生数 = 普通本、专科（高职）生数 + 硕士生数*1.5 + 博士生数*2 + 留学生数*3 
	 * 			+ 预科生数 + 进修生数 + 成人托产班学生数 + 夜大(业务)学生数*0.3 + 函授生数*0.1 <br>
	 * <br>
	 * 这里只处理    本、专、 硕士 、 博士
	 * @return int
	 */
	public double getStuConvertCount(List<String> deptList, List<AdvancedParam> stuAdvancedList){
		double count = 0;
		String stuSql = businessDao.getStuSql(deptList, stuAdvancedList);
		List<Map<String, Object>> list = teacherGroupDao.queryStuTrainingGroup(stuSql);
		String code = null; Double value = 0D;
		for(Map<String, Object> map : list){
			code = MapUtils.getString(map, "code");
			if(code==null) continue;
			value = MapUtils.getDouble(map, "value");
			switch (code) {
			case Constant.CODE_TRAINING_LEVEL_CODE_1:
				count += value*2;
				break;
			case Constant.CODE_TRAINING_LEVEL_CODE_2:
				count += value*1.5;
				break;
			case Constant.CODE_TRAINING_LEVEL_CODE_3:
			case Constant.CODE_TRAINING_LEVEL_CODE_4:
				count += value;
				break;
			default:
				break;
			}
		}
		return count;
	}
	/**
	 * 获取生师比中的折合教师数
	 * 教师总数 = 专任教师数 + 外聘教师数*0.5
	 * @return double
	 */
	public double getTeaConvertCount(List<String> deptList, List<AdvancedParam> teaAdvancedList){
		AdvancedParam authorizedStrengthAdp = new AdvancedParam(AdvancedUtil.Group_Tea, AdvancedUtil.Tea_AUTHORIZED_STRENGTH_ID, Constant.CODE_AUTHORIZED_STRENGTH_ID_11);
		AdvancedUtil.add(teaAdvancedList, authorizedStrengthAdp);
		int zrjs = baseDao.queryForCount(businessDao.getTeaSql(deptList, teaAdvancedList));
		authorizedStrengthAdp.setValues(Constant.CODE_AUTHORIZED_STRENGTH_ID_50);
		int wpjs = baseDao.queryForCount(businessDao.getTeaSql(deptList, teaAdvancedList));
		return getTeaConvertCount(zrjs, wpjs);
	}
	/**
	 * 获取生师比中的折合教师数
	 * 教师总数 = 专任教师数 + 外聘教师数*0.5
	 * @return double
	 */
	public double getTeaConvertCount(int zrjsCount, int wpjsCount){
		return zrjsCount+wpjsCount*0.5;
	}
	
	/**
	 * 专任教师/全校教师分析
	 */
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDistribution(List<AdvancedParam> advancedParamList, String lx){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, lx);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");

		String teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		// 职称、学位、学历
		List<Map<String, Object>> technicalLi = teacherGroupDao.queryTechnicalGroup(teaSql),
								  degreeLi    = teacherGroupDao.queryDegreeGroup(teaSql),
								  eduLi       = teacherGroupDao.queryEduGroup(teaSql);
		// 高职称、高学位、高学历
		double technicalScale = getHighScale(technicalLi, Constant.High_Technical),
			   degreeScale    = getHighScale(degreeLi, Constant.High_Degree),
			   eduScale       = getHighScale(eduLi, Constant.High_Edu);
		
		Map<String, Object> map = DevUtils.MAP();
		map.put("technical", technicalLi);
		map.put("degree", degreeLi);
		map.put("edu", eduLi);
		map.put("technicalScale", technicalScale);
		map.put("degreeScale", degreeScale);
		map.put("eduScale", eduScale);
		return map;
	}

	@Override
	public Map<String, Object> getHistoryTechnical(List<AdvancedParam> advancedParamList, String lx){
		return getHistoryByType(advancedParamList, lx, HistoryType_technical);
	}
	
	@Override
	public Map<String, Object> getHistoryDegree(List<AdvancedParam> advancedParamList, String lx){
		return getHistoryByType(advancedParamList, lx, HistoryType_degree);
	}
	
	@Override
	public Map<String, Object> getHistoryEdu(List<AdvancedParam> advancedParamList, String lx){
		return getHistoryByType(advancedParamList, lx, HistoryType_edu);
	}
	
	private static final String HistoryType_technical = "technical";
	private static final String HistoryType_degree = "degree";
	private static final String HistoryType_edu = "edu";
	@SuppressWarnings("unchecked")
	private Map<String, Object> getHistoryByType(List<AdvancedParam> advancedParamList, String lx, String type) {
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, lx);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		Integer year = MapUtils.getInteger(paramM, "year");
		// 历年
		AdvancedParam yearAdp = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_YEAR, null);
		AdvancedUtil.add(teaAdvancedList, yearAdp);
		int yearLen = 5;
		String teaSql = null, code = null;
		List<Map<String, Object>> list = new ArrayList<>(),
								  li   = null;
		List<String> legend_ary = new ArrayList<>(),
				 	 value_ary  = new ArrayList<>();
		Map<String, Object> m = null;
		for( ; yearLen>0; yearLen--){
			int year_ = year-yearLen+1;
			yearAdp.setValues(year_+"");
			teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
			if(type.equals(HistoryType_technical))
				li = teacherGroupDao.queryTechnicalGroupRightJoin(teaSql);
			else if(type.equals(HistoryType_degree))
				li = teacherGroupDao.queryDegreeGroupRightJoin(teaSql);
			else if(type.equals(HistoryType_edu))
				li = teacherGroupDao.queryEduGroupRightJoin(teaSql);
			Map<String, Object> map = new HashMap<>();
			for(int k=0,len=li.size(); k<len; k++){
				m = li.get(k);
				code = MapUtils.getString(m, "code");
				map.put(code, MapUtils.getInteger(m, "value"));
				if(value_ary.size() < k+1){
					value_ary.add(code);
					legend_ary.add(MapUtils.getString(m, "name"));
				}
			}
			map.put("name", year_);
			list.add(map);
		}
		//
		Map<String, Object> map = new HashMap<>();
		map.put("legend_ary", legend_ary);
		map.put("value_ary", value_ary);
		map.put("data", list);
		return map;
	}
	
	/**
	 * 获取高职称、学位、学历比例
	 * @param list
	 * @param codes
	 * @return double
	 */
	private double getHighScale(List<Map<String, Object>> list, String codes){
		double countAll = 0D, count = 0D, val = 0D;
		List<String> li = ListUtils.string2List(codes);
		if(list != null)
			for(Map<String, Object> m : list){
				val = MapUtils.getInteger(m, "value");
				if(li.contains(MapUtils.getString(m, "code"))){
					count += val;
				}
				countAll += val;
			}
		return MathUtils.getPercentNum(count, countAll);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSubjectGroup(List<AdvancedParam> advancedParamList, String lx){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, lx);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		
		String teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		List<Map<String, Object>> list = teacherGroupDao.querySubjectGroup(teaSql);
		// 
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAgeGroup(List<AdvancedParam> advancedParamList, String lx){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, lx);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		
		String teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		List<String> birthdayList = baseDao.queryForListString("select birthday from ("+teaSql+")");
		List<Integer> ageList = AgeUtils.CalculateAge(birthdayList, DateUtils.getNowDate());
		List<Map<String, Object>> list = AgeUtils.groupAge(ageList, Constant.TeacherGroup_Age_Group);
		// 青年教师
		int count = 0, countAll = ageList.size();
		for(Integer age : ageList){
			if(age != null && age <= Constant.Young_Age){
				count++;
			}
		}
		double scale = MathUtils.getPercentNum(count, countAll);
		// 
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("scale", scale);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSchoolAgeGroup(List<AdvancedParam> advancedParamList, String lx){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, lx);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		
		String teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		List<String> dateList = baseDao.queryForListString("select "+Constant.Field_School_Date+" date_ from ("+teaSql+")");
		List<Integer> schoolAgeList = AgeUtils.CalculateSchoolAge(dateList, DateUtils.getNowDate());
		List<Map<String, Object>> list = AgeUtils.groupAge(schoolAgeList, Constant.TeacherGroup_SchoolAge_Group);
		// 
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDeptList(List<AdvancedParam> advancedParamList, String lx){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, lx);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		Integer year = MapUtils.getInteger(paramM, "year");
		String pid = AdvancedUtil.getPid(advancedParamList);
		
		String teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		String[] sqlAry = businessService.getNextLevelSqlAryByDeptDataAndPid(deptList, pid, teaSql, false, false, true, true, year, null);
		String dataSql = sqlAry[0], groupSql = sqlAry[1], orderSql = sqlAry[2];
		boolean isShowAllDept = true; // 是否显示所有节点
		// 高职称、高学历、高学位
		String sql_High_Technical = "select * from ("+dataSql+") t where t.zyjszw_jb_code in("+PmsUtils.formatInSql(Constant.High_Technical)+")",
			   sql_High_Edu       = "select * from ("+dataSql+") t where t.edu_id in("+PmsUtils.formatInSql(Constant.High_Edu)+")",
			   sql_High_Degree    = "select * from ("+dataSql+") t where t.degree_id in("+PmsUtils.formatInSql(Constant.High_Degree)+")";
		sql_High_Technical = getGroupOrderSql(getGroupSql(sql_High_Technical), orderSql, isShowAllDept);
		sql_High_Edu       = getGroupOrderSql(getGroupSql(sql_High_Edu), orderSql, isShowAllDept);
		sql_High_Degree    = getGroupOrderSql(getGroupSql(sql_High_Degree), orderSql, isShowAllDept);
		// 平均年龄、平均教龄
		List<Map<String, Object>> data_list = baseDao.queryListInLowerKey(dataSql),
								count_list = baseDao.queryListInLowerKey(groupSql),
								High_Technical_list = baseDao.queryListInLowerKey(sql_High_Technical),
								High_Edu_list       = baseDao.queryListInLowerKey(sql_High_Edu),
								High_Degree_list    = baseDao.queryListInLowerKey(sql_High_Degree);
		// 将数据按组织机构分组
		Map<String, List<Map<String, Object>>> groupDept_map = new HashMap<>();
		for(Map<String, Object> mapOne : data_list){
			String id = MapUtils.getString(mapOne, code_column);
			id = "".equals(id) ? Constant.CODE_Unknown : id;
			if(groupDept_map.containsKey(id)){
				groupDept_map.get(id).add(mapOne);
			}else{
				List<Map<String, Object>> li = new ArrayList<>();
				li.add(mapOne);
				groupDept_map.put(id, li);
			}
		}
		String nowDate = DateUtils.getNowDate();
		List<Map<String, Object>> reList = new ArrayList<>();
		for(Map<String, Object> mapOne : count_list){
			List<Map<String, Object>> valueList = new ArrayList<>();
			String id = MapUtils.getString(mapOne, code_column);
			// value
			Map<String, Object> countM = new HashMap<>();
			countM.put("value", MapUtils.getString(mapOne, count_column));
			valueList.add(countM);
			// 高职称
			valueList.add(getMapCodeValueById(id, High_Technical_list));
			// 高学位
			valueList.add(getMapCodeValueById(id, High_Degree_list));
			// 高学历
			valueList.add(getMapCodeValueById(id, High_Edu_list));
			// 平均年龄
			Map<String, Object> ageM = new HashMap<>();
			ageM.put("value", getAvgAge(groupDept_map.get(id), nowDate));
			valueList.add(ageM);
			// 平均教龄
			Map<String, Object> schoolAgeM = new HashMap<>();
			schoolAgeM.put("value", getAvgSchoolAge(groupDept_map.get(id), nowDate));
			valueList.add(schoolAgeM);
			// 
			Map<String, Object> map = new HashMap<>();
			reList.add(map);
			map.put("id", id);
			map.put("name", MapUtils.getString(mapOne, name_column));
			map.put("list", valueList);
		}
		// 
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", reList);
		return map;
	}
	/** 获取分组sql */
	private String getGroupSql(String sql){
		return businessService.getNextLevelGroupSql(sql);
	}
	/** 获取分组排序sql */
	private String getGroupOrderSql(String groupSql, String orderSql, boolean isShowAllDept){
		return businessService.getNextLevelGroupOrderSql(groupSql, orderSql, isShowAllDept);
	}
	/** 获取分组中相同code的基础数据map */
	private Map<String, Object> getMapCodeValueById(String id, List<Map<String, Object>> list){
		Map<String, Object> map = new HashMap<>();
		// 高职称
		for(Map<String, Object> m : list){
			if(MapUtils.getString(m, code_column).equals(id)){
				map.put("value", MapUtils.getString(m, count_column));
				map.put("code", Constant.High_Technical);
				break;
			}
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getHistoryList(List<AdvancedParam> advancedParamList, String lx){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, lx);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		Integer year = MapUtils.getInteger(paramM, "year");
		
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		AdvancedParam yearAdp = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_YEAR, null);
		AdvancedUtil.add(teaAdvancedList, yearAdp);
		int yearLen = Constant.Year_His_Len;
		String teaSql = null;
		// TODO
		long time1 = System.currentTimeMillis();
		for( ; yearLen>0; yearLen--){
			int year_  = year-yearLen+1;
			yearAdp.setValues(year_+"");
			teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
			List<Map<String, Object>> li = baseDao.queryListInLowerKey(teaSql);
			Map<String, Object> map = new HashMap<>();
			int size = li.size();
			if(size > 0){
				map.put("name", year_);
				map.put("list", li);
				map.put("count", size);
				map.put("teaSql", teaSql);
				list2.add(map);
			}
		}
		long time2 = System.currentTimeMillis();
		DevUtils.p("query:"+(time2-time1));
		// 计算
		List<Map<String, Object>> li = null;
		String date_suf = "-12-31";
		for(Map<String, Object> map : list2){
			li = (List<Map<String, Object>>) map.get("list");
			String date = MapUtils.getString(map, "name")+date_suf;
			// 人数、高职称、高学历、高学位、平均年龄、平均教龄
			List<Map<String, Object>> valueList = new ArrayList<>();
			Map<String, Object> countM = new HashMap<>(), technicalM = new HashMap<>(), degreeM = new HashMap<>(),
								eduM = new HashMap<>(), ageM = new HashMap<>(), schoolAgeM = new HashMap<>(); 
			countM.put("value", map.get("count")); 
			technicalM.put("value", getCount(li, Constant.High_Technical, "zyjszw_jb_code")); technicalM.put("code", Constant.High_Technical);
			degreeM.put("value", getCount(li, Constant.High_Degree, "degree_id")); 
			eduM.put("value", getCount(li, Constant.High_Edu, "edu_id")); eduM.put("code", Constant.High_Edu);
			ageM.put("value", getAvgAge(li, date)); 
			schoolAgeM.put("value", getAvgSchoolAge(li, date));
			valueList.add(countM); valueList.add(technicalM); valueList.add(degreeM);
			valueList.add(eduM); valueList.add(ageM); valueList.add(schoolAgeM);
			map.put("list", valueList);
			map.remove("teaSql");
		}
		// 
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list2);
		return map;
	}
	
	/**
	 * 获取数据量
	 * @param list 教职工列表
	 * @param codes 计算编码
	 * @param key 判断字段
	 * @return int
	 */
	public int getCount(List<Map<String, Object>> list, String codes, String key){
		int count = 0; String val = null;
		List<String> li = ListUtils.string2List(codes);
		if(list != null)
			for(Map<String, Object> m : list){
				val = MapUtils.getString(m, key);
				if(li.contains(val)){
					count++;
				}
			}
		return count;
	}
	/**
	 * 获取平均年龄
	 * @param list 教职工列表
	 * @return Double
	 */
	private Double getAvgAge(List<Map<String, Object>> list, String date){
		date = date!=null ? date : DateUtils.getNowDate();
		List<Double> ageList = new ArrayList<>();
		for(Map<String, Object> m : list){
			Integer age = AgeUtils.CalculateAge(MapUtils.getString(m, "birthday"), date);
			if(age != null) ageList.add(new Double(age));
		}
		return MathUtils.getAvgValueExcludeNull(ageList);
	}
	/**
	 * 获取平均教龄
	 * @param list
	 * @return Double
	 */
	private Double getAvgSchoolAge(List<Map<String, Object>> list, String date){
		date = date!=null ? date : DateUtils.getNowDate();
		List<Double> ageList = new ArrayList<>();
		for(Map<String, Object> m : list){
			Integer age = AgeUtils.CalculateSchoolAge(MapUtils.getString(m, Constant.Field_School_Date), date);
			if(age != null) ageList.add(new Double(age));
		}
		return MathUtils.getAvgValueExcludeNull(ageList);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDeptScaleList(List<AdvancedParam> advancedParamList){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList);
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList"),
							stuAdvancedList = (List<AdvancedParam>) paramM.get("stuAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		Integer year = MapUtils.getInteger(paramM, "year");
		String pid = AdvancedUtil.getPid(advancedParamList);
		
		String stuSql = businessDao.getStuSql(deptList, stuAdvancedList);
		String teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		String[] stuSqlAry = businessService.getNextLevelSqlAryByDeptDataAndPid(deptList, pid, stuSql, false, true, false, false, year, null);
		String stu_dataSql = stuSqlAry[0], stu_orderSql = stuSqlAry[2],
			   tea_dataSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, pid, teaSql, false, true, true, false, year, null);
		boolean isShowAllDept = true; // 是否显示所有节点
		// 学生培养层次分组
		stu_dataSql = "select dataSql."+code_column+",code.code_ code, nvl(count(0),0) "+count_column+" from ("+stu_dataSql+")dataSql,t_code code"
				+ " where code.istrue=1 and dataSql.training_level_code=code.code_ and code.code_type='"+Constant.CODE_TRAINING_LEVEL_CODE+"'"
				+ " group by dataSql."+code_column+",code.code_";
		String stu_orderSql2 = "select dataSql.code, orderSql.id,orderSql.name_,orderSql."+order_column+" from (select distinct(code) code from ("+stu_dataSql+"))dataSql,("+stu_orderSql+")orderSql";
		stu_dataSql = "select orderSql.id "+code_column+",orderSql.name_ "+name_column+",orderSql.code code, nvl(dataSql."+count_column+",0) "+count_column+" from ("+stu_dataSql+")dataSql right join ("+stu_orderSql2+")orderSql"
				+ " on dataSql."+code_column+"=orderSql.id and dataSql.code=orderSql.code order by orderSql.id";
		// 专任教师、外聘教师 AUTHORIZED_STRENGTH_ID
		String tea_dataSql_11 = "select * from ("+tea_dataSql+")t where t.AUTHORIZED_STRENGTH_ID"
				+ " in (select t.id from t_code_AUTHORIZED_STRENGTH t inner join t_code_AUTHORIZED_STRENGTH x"
				+ " on t.path_ like x.path_||'%' and x.id ='"+Constant.CODE_AUTHORIZED_STRENGTH_ID_11+"')";
		String tea_dataSql_50 = "select * from ("+tea_dataSql+")t where t.AUTHORIZED_STRENGTH_ID"
				+ " in (select t.id from t_code_AUTHORIZED_STRENGTH t inner join t_code_AUTHORIZED_STRENGTH x"
				+ " on t.path_ like x.path_||'%' and x.id ='"+Constant.CODE_AUTHORIZED_STRENGTH_ID_50+"')";
		tea_dataSql_11 = getGroupOrderSql(getGroupSql(tea_dataSql_11), stu_orderSql, isShowAllDept);
		tea_dataSql_50 = getGroupOrderSql(getGroupSql(tea_dataSql_50), stu_orderSql, isShowAllDept);
		
		List<Map<String, Object>> stu_data_list   = baseDao.queryListInLowerKey(stu_dataSql),
								  tea11_data_list = baseDao.queryListInLowerKey(tea_dataSql_11),
								  tea50_data_list = baseDao.queryListInLowerKey(tea_dataSql_50);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Map<String, Object>> groupMap = new HashMap<>();
		String key_stu = "stuCount", key_tea = "teaCount";
		for(Map<String, Object> map : stu_data_list){
			String id = MapUtils.getString(map, code_column);
			Double stuCount = 0D, teaCount = 0D;
			Map<String, Object> m = null;
			if(groupMap.containsKey(id)){
				m = groupMap.get(id);
				stuCount = MapUtils.getDouble(m, key_stu);
				teaCount = MapUtils.getDouble(m, key_tea);
			}else{
				m = new HashMap<>();
				m.put("id", MapUtils.getString(map, code_column));
				m.put("name", MapUtils.getString(map, name_column));
				groupMap.put(id, m);
				list.add(m);
			}
			// stu
			String code = MapUtils.getString(map, "code");
			if(code==null) continue;
			Double xsVal = MapUtils.getDouble(map, count_column);
			switch (code) {
			case Constant.CODE_TRAINING_LEVEL_CODE_1:
				xsVal = xsVal*2;
				break;
			case Constant.CODE_TRAINING_LEVEL_CODE_2:
				xsVal = xsVal*1.5;
				break;
//			case Constant.CODE_TRAINING_LEVEL_CODE_3:
//			case Constant.CODE_TRAINING_LEVEL_CODE_4:
//				break;
			}
			stuCount += xsVal;
			m.put(key_stu, stuCount);
			// tea 专任、外聘
			for(Map<String, Object> teaMap : tea11_data_list){
				if(id.equals(MapUtils.getString(teaMap, code_column))){
					teaCount += MapUtils.getDouble(teaMap, count_column);
					break;
				}
			}
			for(Map<String, Object> teaMap : tea50_data_list){
				if(id.equals(MapUtils.getString(teaMap, code_column))){
					teaCount += MapUtils.getDouble(teaMap, count_column)*0.5;
				}
			}
			m.put(key_tea, teaCount);
		}
		for(Map<String, Object> map : list){
			map.put("value", getStuTeaScale(MapUtils.getDouble(map, key_stu), MapUtils.getDouble(map, key_tea)));
			map.remove(key_stu);
			map.remove(key_tea);
		}
		String deptMc = businessService.getLevelNameById(list.isEmpty() ? null : MapUtils.getString(list.get(1), "id"));
		ListUtils.sort(list, "value", true);
		
		/*List<Map<String, Object>> list  = businessService.getDeptDataListForGoingDownStu(deptList, pid, year);
		AdvancedParam deptAdp = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_TEACH_ID, null);
		AdvancedParam schoolYearAdp = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_SCHOOL_YEAR, year+"");
		AdvancedUtil.add(teaAdvancedList, deptAdp);
		AdvancedUtil.add(stuAdvancedList, deptAdp); AdvancedUtil.add(stuAdvancedList, schoolYearAdp);
		// 去除没有人的节点
		for(Map<String, Object> map : list){
			deptAdp.setValues(MapUtils.getString(map, "id"));
			double stuCount = getStuConvertCount(deptList, stuAdvancedList);
			double teaCount = getTeaConvertCount(deptList, teaAdvancedList);
			map.put("value", getStuTeaScale(stuCount, teaCount));
		}
		// 当前组织机构类型名称（院系、专业）
		String deptMc = businessService.getLevelNameById(list.isEmpty() ? null : MapUtils.getString(list.get(0), "id"));
		ListUtils.sort(list, "value", true);*/
		// 
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("deptMc", deptMc);
		return map;
	}
	
	
	@Override
	public Map<String, Object> getTeaDetail(List<AdvancedParam> advancedParamList, Page page, 
			Map<String, Object> keyValue, List<String> fields){
		/*if(!businessService.hasPermssion(ShiroTag_detail)){
			return null;
		};*/
//		String sortColumn = page.getSortColumn(), order = page.getOrder();
		String teaDetailSql = getTeaDetailSql(advancedParamList, keyValue, fields);
		// 
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(teaDetailSql, page);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getTeaDetailAll(List<AdvancedParam> advancedParamList, Page page, Map<String, Object> keyValue, List<String> fields){
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(getTeaDetailSql(advancedParamList, keyValue, fields), page);
		return (List<Map<String, Object>>) map.get("rows");
	}
	
	@SuppressWarnings("unchecked")
	private String getTeaDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields
//			, String sortColumn, String order
			) {
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList);
		List<String> deptList = (List<String>) paramM.get("deptList");
		List<AdvancedParam> teaAdvancedList = (List<AdvancedParam>) paramM.get("teaAdvancedList");
		if(keyValue != null){
			String key = null, val = null;
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				key = entry.getKey();
				val = entry.getValue()==null ? null : String.valueOf(entry.getValue());
				// 全部教师（AUTHORIZED_STRENGTH_ID==null）不能添加高级参数，否则查询错误 20161207 
				if(!AdvancedUtil.Tea_AUTHORIZED_STRENGTH_ID.equals(key) || (val != null && !"".equals(val))){
					AdvancedUtil.add(teaAdvancedList, getDetailAdp(key, val));
				}
			}
		}
		String teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				// 年龄  教龄  特殊处理
				if(Type_age.equals(type) || Type_schoolage.equals(type)){
					String[] ageAry = code.split(",");
					if(ageAry.length == 2){
						teaSql = "select * from ("+teaSql+") ";
						String startAge = ageAry[0], endAge = ageAry[1], date = DateUtils.getNowDate(), field = null, ageSql = "";
						if(Type_age.equals(type)) field = "birthday";
						else if(Type_schoolage.equals(type)) field = Constant.Field_School_Date;
						if("null".equals(startAge) && "null".equals(endAge)){
							ageSql = "where "+field+" is null";
						}else if("null".equals(startAge) && !"null".equals(endAge)){
							String[] birAry = AgeUtils.CalculateBirthday(0, Integer.valueOf(endAge), date);
							ageSql = "where "+field+" >= '"+birAry[0]+"'";
						}else if(!"null".equals(startAge) && "null".equals(endAge)){
							String[] birAry = AgeUtils.CalculateBirthday(Integer.valueOf(startAge), Integer.valueOf(startAge)+1, date);
							ageSql = "where "+field+" <= '"+birAry[1]+"'";
						}
						if(!"null".equals(startAge) && !"null".equals(endAge)){
							String[] birAry = AgeUtils.CalculateBirthday(Integer.valueOf(startAge), Integer.valueOf(endAge), date);
							ageSql = "where "+field+" >= '"+birAry[0]+"' and "+field+" <= '"+birAry[1]+"'";
						}
						teaSql += ageSql;
					}
				}
			}
		// 教职工
		String teaDetailSql = businessDao.getTeaDetailSql(teaSql);
		// 所要查询的字段
		/*if(fields!=null && !fields.isEmpty()){
			teaDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+teaDetailSql+")";
			if(sortColumn != null && fields.contains(sortColumn))
				teaDetailSql += " order by "+sortColumn+" "+order;
		}*/
		return teaDetailSql;
	}
	
	/**
	 * 获取师资分布中的 高级参数
	 * @param type
	 * @param value
	 * @return AdvancedParam
	 */
	private AdvancedParam getDetailAdp(String type, String value){
		AdvancedParam adp = null;
		if(type != null){
			String group = AdvancedUtil.Group_Tea, code = null;
			switch (type) {
			case Type_technical: // 职称等级
			case High_Technical: // 高职称
				code = AdvancedUtil.Tea_ZYJSZW_JB_CODE;
				break;
			case Type_degree: // 学位
			case High_Degree: // 高学历
				code = AdvancedUtil.Tea_DEGREE_ID;
				break;
			case Type_edu: // 学历
			case High_Edu: // 高学历
				code = AdvancedUtil.Tea_EDU_ID;
				break;
			case Type_subject:
				code = AdvancedUtil.Tea_SUBJECT;
				group = AdvancedUtil.Group_Tea;
				break;
			case Type_AUTHORIZED_STRENGTH:
				code = AdvancedUtil.Tea_AUTHORIZED_STRENGTH_ID;
				break;
			case AdvancedUtil.Common_DEPT_ID:
			case AdvancedUtil.Common_DEPT_TEACH_ID:
			case AdvancedUtil.Common_DEPT_TEACH_TEACH_ID:
				group = AdvancedUtil.Group_Common;
				code  = type;
				break;
			default:
				adp = getAbstractAdp(type, value);
				if(adp != null){
					group = adp.getGroup();
					code  = adp.getCode();
					value = adp.getValues();
				}
				break;
			}
			adp = new AdvancedParam(group, code, value);
		}
		return adp;
	}
	
	/**
	 * 根据参数优化参数
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getParamsMapByParams(List<AdvancedParam> advancedParamList){
		return getParamsMapByParams(advancedParamList, null);
	}
	/**
	 * 根据参数优化参数
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getParamsMapByParams(List<AdvancedParam> advancedParamList, String lx){
		AdvancedParam schoolYearAdp = AdvancedUtil.get(advancedParamList, AdvancedUtil.Common_SCHOOL_YEAR);
		String schoolYear = schoolYearAdp.getValues(), termCode = null;
		String[] termAry = EduUtils.getSchoolYearTerm(DateUtils.getNowDate());
		schoolYear = schoolYear==null ? termAry[0] : schoolYear;
//		schoolYear = "2014-2015"; termCode = "01"; // TODO del 因为本地库目前只有这个时间段有数据
		Integer year = Integer.valueOf(schoolYear.substring(0, 4));
		List<String> deptList = getDeptDataList();
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		if(lx != null && !"".equals(lx)){
			AdvancedParam authorizedStrengthAdp = new AdvancedParam(AdvancedUtil.Group_Tea, AdvancedUtil.Tea_AUTHORIZED_STRENGTH_ID, lx);
			AdvancedUtil.add(advancedParamList, authorizedStrengthAdp);
		}
		List<AdvancedParam> teaAdvancedList = AdvancedUtil.getAdvancedParamTea(advancedParamList),
							stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList),
							businessAdvancedList = AdvancedUtil.getAdvancedParamBusiness(advancedParamList);
		// return
		Map<String, Object> map = new HashMap<>();
		map.put("advancedParamList", advancedParamList);
		map.put("teaAdvancedList", teaAdvancedList);
		map.put("stuAdvancedList", stuAdvancedList);
		map.put("businessAdvancedList", businessAdvancedList);
		map.put("deptList", deptList);
		map.put("schoolYear", schoolYear);
		map.put("termCode", termCode);
		map.put("year", year);
		return map;
	}
	
}
