package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.business.util.ScoreUtils;
import cn.gilight.dmm.teaching.dao.ScoreDao;
import cn.gilight.dmm.teaching.job.StuGpaJob;
import cn.gilight.dmm.teaching.service.ScoreService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月22日 下午12:10:30
 */
@Service("scoreService")
public class ScoreServiceImpl implements ScoreService {

	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private ScoreDao scoreDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private StuGpaJob stuGpaJob;
	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"score";
	/**
	 * 基础GPA ID
	 */
	private static final String GpaId = Constant.SCORE_GPA_BASE_CODE;
	
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	
	private static final String SCORE_AVG = Constant.SCORE_AVG;
	private static final String SCORE_MIDDLE = Constant.SCORE_MIDDLE;
	private static final String SCORE_MODE = Constant.SCORE_MODE;
	private static final String SCORE_FC  = Constant.SCORE_FC;
	private static final String SCORE_BZC = Constant.SCORE_BZC;
	
	private static final String SCORE_BETTER = Constant.SCORE_BETTER;
	private static final String SCORE_FAIL = Constant.SCORE_FAIL;
	private static final String SCORE_REBUILD = Constant.SCORE_REBUILD;
	private static final String SCORE_UNDER = Constant.SCORE_UNDER;
	
	
	@Override
	public Map<String, Object> getBzdm(){
		Map<String, Object> map = DevUtils.MAP();
		map.put("xnxq", businessService.queryBzdmScoreXnXq());
		map.put("edu", businessService.queryBzdmStuEducationList());
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAbstract(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String edu){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, schoolYear, termCode, edu);
		List<AdvancedParam> businessAdvancedList = (List<AdvancedParam>) paramM.get("businessAdvancedList");
		List<String> deptList = (List<String>) MapUtils.getObject(paramM, "deptList");
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		String stuSql = MapUtils.getString(paramM, "stuSql"), 
			   scoreSql = null, betterSql = null, failSql = null, rebuildSql = null, target = null;
		/**
		 * 权限内学生的 平均绩点、中位数、众数、方差、标准差
		 */
		Map<String, Object> paramMap = null;
		List<Double> valueList = null;
		List<Map<String, Object>> bzdmList = businessService.queryBzdmScoreType();
		if(!bzdmList.isEmpty()){
			target = MapUtils.getString(bzdmList.get(0), "id");
			paramMap   = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, null, target, businessAdvancedList);
			valueList  = (List<Double>) paramMap.get("valueList");
			scoreSql   = MapUtils.getString(paramMap, "scoreSql");
			betterSql  = MapUtils.getString(paramMap, "betterSql");
			failSql    = MapUtils.getString(paramMap, "failSql");
			rebuildSql = MapUtils.getString(paramMap, "rebuildSql");
		}
		Map<String, Object> map = DevUtils.MAP();
		for(Map<String, Object> bzdm : bzdmList){
			target = MapUtils.getString(bzdm, "id");
			Double value = getScoreValueByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, target);
			map.put(target, value);
		}
		// 权限中文名称 （**学校、**学院）
		String name = businessService.queryDeptDataName(deptList, advancedParamList);
		// return
		map.put("name", name);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGroup(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String edu){
		long beginTime = System.currentTimeMillis();
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, schoolYear, termCode, edu);
		List<AdvancedParam> businessAdvancedList = (List<AdvancedParam>) paramM.get("businessAdvancedList");
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		String stuSql = MapUtils.getString(paramM, "stuSql"),
			   target = MapUtils.getString(paramM, "target");
		/**
		 * 绩点分组(平均绩点)
		 * 低于平均绩点
		 */
		Map<String, Object> paramMap = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, null, target, businessAdvancedList);
		List<Double> valueList = (List<Double>) paramMap.get("valueList");
		List<Map<String, Object>> list = ScoreUtils.group(valueList, Constant.Score_Gpa_Group);
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
 		DevUtils.p("分组-结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ");
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getScale(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String edu){
		long beginTime = System.currentTimeMillis();
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, schoolYear, termCode, edu);
		List<AdvancedParam> businessAdvancedList = (List<AdvancedParam>) paramM.get("businessAdvancedList");
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		String stuSql = MapUtils.getString(paramM, "stuSql"),
			   target = MapUtils.getString(paramM, "target"),
			   scoreSql = null, betterSql = null, failSql = null, rebuildSql = null;
		/**
		 * 绩点分组(平均绩点)
		 * 低于平均绩点
		 */
		Map<String, Object> paramMap = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, null, target, businessAdvancedList);
		List<Double> valueList = (List<Double>) paramMap.get("valueList");
		Double underVal = getScoreValueByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, Constant.SCORE_UNDER);
		/**
		 * 优秀率（90分及以上）、挂科率（）、重修率（）、低于平均数（低于平均绩点）
		 */
		String target2 = Constant.SCORE_BETTER;
		paramMap = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, null, target2, businessAdvancedList);
		scoreSql   = MapUtils.getString(paramMap, "scoreSql");
		betterSql  = MapUtils.getString(paramMap, "betterSql");
		failSql    = MapUtils.getString(paramMap, "failSql");
		rebuildSql = MapUtils.getString(paramMap, "rebuildSql");
		Double betterVal  = getScoreValueByTarget(null, scoreSql, betterSql, failSql, rebuildSql, Constant.SCORE_BETTER),
			   failVal    = getScoreValueByTarget(null, scoreSql, betterSql, failSql, rebuildSql, Constant.SCORE_FAIL),
			   rebuildVal = getScoreValueByTarget(null, scoreSql, betterSql, failSql, rebuildSql, Constant.SCORE_REBUILD);
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put(SCORE_BETTER, betterVal);
		map.put(SCORE_FAIL, failVal);
		map.put(SCORE_REBUILD, rebuildVal);
		map.put(SCORE_UNDER, underVal);
		String name_fix = "_name"; // 名字后缀
		map.put(SCORE_BETTER+name_fix, Globals.BETTER_SCORE_LINE);
		map.put(SCORE_FAIL+name_fix, Globals.FAIL_SCORE_LINE);
		map.put(SCORE_REBUILD+name_fix, Globals.REBUILD_SCORE_LINE);
		DevUtils.p("大于"+underVal+"的比例"+MathUtils.getScale(valueList, underVal));
 		DevUtils.p("比例-结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ");
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGpaCourse(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String edu, String type){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, schoolYear, termCode, edu);
		List<AdvancedParam> businessAdvancedList = (List<AdvancedParam>) paramM.get("businessAdvancedList");
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		String stuSql   = MapUtils.getString(paramM, "stuSql"),
			   target   = MapUtils.getString(paramM, "target"),
			   scoreSql = null, betterSql = null, failSql = null, rebuildSql = null, code = null;
		target = type!=null ? type : target;
		Map<String, Object> paramMap = null;
		List<Double> valueList = null;
		/**
		 * 课程属性、课程性质 分组查询数据；并按标准代码分组
		 */
		List<Map<String, Object>> attrBzdmList   = businessService.queryBzdmCourseAttr(),
				  				  natureBzdmList = businessService.queryBzdmCourseNature();
		AdvancedParam attrAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Business_COURSE_ATTR_CODE, null);
		businessAdvancedList.add(attrAdp);
		for(Map<String, Object> m : attrBzdmList){
			code = MapUtils.getString(m, "id");
			attrAdp.setValues(code);
			paramMap = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, scoreSql, target, businessAdvancedList);
			valueList = (List<Double>) paramMap.get("valueList");
			m.put("value", getScoreValueByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, target));
		}
		businessAdvancedList.remove(attrAdp);
		AdvancedParam natureAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Business_COURSE_NATURE_CODE, null);
		businessAdvancedList.add(natureAdp);
		for(Map<String, Object> m : natureBzdmList){
			code = MapUtils.getString(m, "id");
			natureAdp.setValues(code);
			paramMap = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, scoreSql, target, businessAdvancedList);
			valueList = (List<Double>) paramMap.get("valueList");
			m.put("value", getScoreValueByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, target));
		}
		// type类型为空，默认使用第一个编码初始化
		List<Map<String, Object>> bzdmList = null;
		if(type == null){
			bzdmList = businessService.queryBzdmScoreType();
			type = MapUtils.getString(bzdmList.get(0), "id");
		}
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("attr", attrBzdmList);
		map.put("nature", natureBzdmList);
		if(bzdmList != null) map.put("bzdm", bzdmList);
		return map;
	}
	
	@Override
	public String getDisplayedLevelType(List<AdvancedParam> advancedParamList, String schoolYear){
		String pid = AdvancedUtil.getPid(advancedParamList);
		Integer year = EduUtils.getSchoolYear4(schoolYear);
		Integer grade = AdvancedUtil.getStuGrade(advancedParamList);
		List<Map<String, Object>> deptMapList = baseDao.queryListInLowerKey(businessService.getNextLevelOrderSqlByDeptDataAndPid(getDeptDataList(), pid, "", true, true, false, false, year, grade));
		String level_type = null;
		if(!deptMapList.isEmpty()){
			level_type = businessService.getLevelTypeById(MapUtils.getString(deptMapList.get(0),"id"));
		}
		return level_type;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGridList(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, 
			String edu, String type, String order, String asc, Integer index){
		Map<String, Object> paramMap = getParamsMapByParams(advancedParamList, schoolYear, termCode, edu);
		List<AdvancedParam> stuAdvancedList      = (List<AdvancedParam>) paramMap.get("stuAdvancedList"),
							businessAdvancedList = (List<AdvancedParam>) paramMap.get("businessAdvancedList");
		List<String> deptList = (List<String>) MapUtils.getObject(paramMap, "deptList");
		schoolYear = MapUtils.getString(paramMap, "schoolYear"); 
		termCode   = MapUtils.getString(paramMap, "termCode");
		Integer year = MapUtils.getInteger(paramMap, "year");
		advancedParamList    = (List<AdvancedParam>) paramMap.get("advancedParamList");
		return getGridData(advancedParamList, stuAdvancedList, businessAdvancedList, deptList, schoolYear, termCode, edu, type, order, asc, index, year);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getHistoryGridList(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, 
			String edu, String type, String order, String asc, Integer index){
		Map<String, Object> paramMap = getParamsMapByParams(advancedParamList, schoolYear, termCode, edu);
		List<AdvancedParam> stuAdvancedList      = (List<AdvancedParam>) paramMap.get("stuAdvancedList"),
							businessAdvancedList = (List<AdvancedParam>) paramMap.get("businessAdvancedList");
		advancedParamList    = (List<AdvancedParam>) paramMap.get("advancedParamList");
		List<String> deptList = (List<String>) MapUtils.getObject(paramMap, "deptList");
		schoolYear = MapUtils.getString(paramMap, "schoolYear"); 
		Integer year = MapUtils.getInteger(paramMap, "year");
		return getGridData(advancedParamList, stuAdvancedList, businessAdvancedList, deptList, schoolYear, termCode, edu, type, order, asc, index, year);
	}
	@SuppressWarnings("unchecked")
	private Map<String, Object> getGridData(List<AdvancedParam> advancedParamList, List<AdvancedParam> stuAdvancedList,
			List<AdvancedParam> businessAdvancedList ,List<String> deptList,String schoolYear, String termCode, 
			String edu, String type, String order, String asc, Integer index,Integer year){
		long beginTime = System.currentTimeMillis();
		List<Map<String, Object>> headerList = null;
		// 设置数据字段编码 及 默认排序字段
		if(order == null){
			// 没有排序字段时，需要提取表头编码
			headerList = getGridHeaderBzdm();
			order = MapUtils.getString(headerList.get(0), "id"); //默认查第一个
		}
		boolean isAsc = false; // 默认倒序
		if(asc != null && "asc".equals(asc)) isAsc = true;
		
		int limit = 10;
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> gridMap = new HashMap<String, Object>();
		// 需要展示的组织机构列表
		if(advancedParamList.size()>1 || !AdvancedUtil.getValue(advancedParamList, AdvancedUtil.Stu_EDU_ID).equals(MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id"))){
			if(type == null || "YX".equals(type)){
				type = "YX";
				list = businessDao.queryYxList(deptList);
				setGridListDeptStuSql(list, deptList, year, stuAdvancedList);
			}else if("ZY".equals(type)){
				list = businessDao.queryZyListStu(deptList);
				setGridListDeptStuSql(list, deptList, year, stuAdvancedList);
			}else if("BJ".equals(type)){
				list = businessDao.queryBjList(deptList, year);
				setGridListDeptStuSql(list, deptList, year, stuAdvancedList);
			}else if("SUBJECT".equals(type)){
				list = businessService.queryBzdmSubjectDegree();
				setGridListSubjectStuSql(list, deptList, year, stuAdvancedList);
			}else if("COURSE".equals(type)){
				list = getGridListCourseParamAndValue(list, deptList, year, stuAdvancedList, schoolYear, termCode, order, null, businessAdvancedList);
			}else if("TEACHER".equals(type)){
				list = getGridListTeachParamAndValue(list, deptList, year, stuAdvancedList, schoolYear, termCode, order, null, businessAdvancedList);
			}
			// 院系/专业/班级/学科  处理排序字段数据
			String _order = list.size()>limit ? order : null;
			if(!"COURSE".equals(type) && !"TEACHER".equals(type)){
				list = getGridListParamAndValues(list, schoolYear, termCode, _order, null, businessAdvancedList);
			}
			// 处理 所有字段数据
			sortList(list, order, isAsc);
			gridMap = subList(list, index, limit);
			list = (List<Map<String, Object>>) gridMap.get("list");
			if("COURSE".equals(type)){ // 课程
				list = getGridListCourseParamAndValue(list, deptList, year, stuAdvancedList, schoolYear, termCode, null, _order, businessAdvancedList);
			}else if("TEACHER".equals(type)){ // 教师
				list = getGridListTeachParamAndValue(list, deptList, year, stuAdvancedList, schoolYear, termCode, null, _order, businessAdvancedList);
			}else if(_order != null){ // 其他
				list = getGridListParamAndValues(list, schoolYear, termCode, null, _order, businessAdvancedList);
			}
			gridMap.put("list", list);
		}else{
			list = getLogData(type, schoolYear, termCode, deptList);
			sortList(list, order, isAsc);
			gridMap = subList(list, index, limit);
		}
		// return
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.putAll(gridMap);
		if(headerList != null) reMap.put("header", headerList);
		DevUtils.p("表格-结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ");
		return reMap;
	}
	
	/** 获取表头标准代码 (avg, middle, mode......) */
	private List<Map<String, Object>> getGridHeaderBzdm(){
		return businessService.queryBzdmScoreTarget();
	}
	
	/** 计算 院系/专业/班级/学科 绩点列表数据 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getGridListParamAndValues(List<Map<String, Object>> list, String schoolYear, String termCode, 
			String order, String firstOrder, List<AdvancedParam> businessAdvancedList) {
		String stuSqlKey = "stuSql", stuSql = null, scoreSql = null, betterSql = null, failSql = null, rebuildSql = null;
		List<Double> gpaList = null;
		for(Map<String, Object> map : list){
			stuSql = MapUtils.getString(map, stuSqlKey);
			Map<String, Object> paramMap = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, null, order, businessAdvancedList, map);
			gpaList    = (List<Double>) paramMap.get("valueList");
			scoreSql   = MapUtils.getString(paramMap, "scoreSql");
			betterSql  = MapUtils.getString(paramMap, "betterSql");
			failSql    = MapUtils.getString(paramMap, "failSql");
			rebuildSql = MapUtils.getString(paramMap, "rebuildSql");
			Map<String, Object> scoreMap = getScoreValueMapByTarget(gpaList, scoreSql, betterSql, failSql, rebuildSql, order, firstOrder);
			map.putAll(scoreMap);
			if(order == null){
				scoreMap.remove(stuSqlKey);
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	/** 计算不同课程的 成绩列表数据 */
	private List<Map<String, Object>> getGridListCourseParamAndValue(List<Map<String, Object>> list, List<String> deptList, Integer year, List<AdvancedParam> stuAdvancedList, 
			String schoolYear, String termCode, String order, String firstOrder, List<AdvancedParam> businessAdvancedList){
		String stuSql       = businessDao.getStuSql(year, deptList, stuAdvancedList),
			   baseScoreSql = businessDao.getStuScoreSql(stuSql, schoolYear, termCode),
			   scoreSql = null, betterSql = null, failSql = null, rebuildSql = null;
		list = (list!=null&&!list.isEmpty()) ? list : scoreDao.queryCourseList(stuSql, schoolYear, termCode);
		Map<String, Object> paramMap = null, scoreMap = null;
		List<Double> valueList = null;
		for(Map<String, Object> map : list){
			scoreSql  = "select * from ("+baseScoreSql+")t where t.coure_code='"+MapUtils.getString(map, "id")+"'";
			paramMap  = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, scoreSql, order, businessAdvancedList, map);
			valueList = (List<Double>) paramMap.get("valueList");
			scoreSql  = MapUtils.getString(paramMap, "scoreSql");
			betterSql = MapUtils.getString(paramMap, "betterSql");
			failSql   = MapUtils.getString(paramMap, "failSql");
			rebuildSql= MapUtils.getString(paramMap, "rebuildSql");
			
			scoreMap  = getScoreValueMapByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, order, firstOrder);
			map.putAll(scoreMap);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	/** 计算不同教师的 成绩列表数据 */
	private List<Map<String, Object>> getGridListTeachParamAndValue(List<Map<String, Object>> list, List<String> deptList, Integer year, List<AdvancedParam> stuAdvancedList, 
			String schoolYear, String termCode, String order, String firstOrder, List<AdvancedParam> businessAdvancedList){
		String baseStuSql = businessDao.getStuSql(year, deptList, stuAdvancedList),
			   stuSql = null, scoreSql = null, betterSql = null, failSql = null, rebuildSql = null;
		list = (list!=null&&!list.isEmpty()) ? list : scoreDao.queryTeachList(baseStuSql, schoolYear, termCode);
		Map<String, Object> paramMap = null, scoreMap = null;
		List<Double> valueList = null;
		for(Map<String, Object> map : list){
			stuSql    = businessDao.getStuSqlByTeachIdSql(MapUtils.getString(map, "id"), baseStuSql);
			scoreSql  = businessDao.getStuScoreSql(stuSql, schoolYear, termCode);
			paramMap  = getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, scoreSql, order, businessAdvancedList, map);
			valueList = (List<Double>) paramMap.get("valueList");
			scoreSql  = MapUtils.getString(paramMap, "scoreSql");
			betterSql = MapUtils.getString(paramMap, "betterSql");
			failSql   = MapUtils.getString(paramMap, "failSql");
			rebuildSql= MapUtils.getString(paramMap, "rebuildSql");
			scoreMap  = getScoreValueMapByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, order, firstOrder);
			map.putAll(scoreMap);
		}
		return list;
	}
	
	/** 获取各机构 学生sql */
	private void setGridListDeptStuSql(List<Map<String, Object>> list, List<String> deptList, Integer year, List<AdvancedParam> stuAdvancedList) {
		for(Map<String, Object> map : list){
			deptList = PmsUtils.getDeptListByDeptMap(map);
			map.put("stuSql", businessDao.getStuSql(year, deptList, stuAdvancedList));
		}
	}
	/** 获取各学科 学生sql */
	private void setGridListSubjectStuSql(List<Map<String, Object>> list, List<String> deptList, Integer year, List<AdvancedParam> stuAdvancedList){
		for(Map<String, Object> map : list){
			AdvancedUtil.add(stuAdvancedList, new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_SUBJECT, MapUtils.getString(map, "id")));
			map.put("stuSql", businessDao.getStuSql(year, deptList, stuAdvancedList));
			map.put("name", MapUtils.getString(map, "mc"));
		}
	}

	/**
	 * List 排序
	 * @param list 列表
	 * @param order_column 排序字段
	 * @param asc 正序
	 */
	private void sortList(List<Map<String, Object>> list, final String order_column, final boolean asc){
		Collections.sort(list, new Comparator<Map<String, Object>>(){
			Double d1 = null, d2 = null;
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int k = 0;
				d1 = MapUtils.getDouble(o1, order_column);
				d2 = MapUtils.getDouble(o2, order_column);
				if(d1!=null && (d1>d2 || d2==null)){ k = 1; }
				else if(d1==null || (d1<d2 && d2!=null)){ k = -1; }
				if(!asc){ k = -k; }
				return k;
			}
		});
	}
	/**
	 * List 截取
	 * @param list 列表
	 * @param index 页数
	 * @param limit 每页条数
	 * @return Map<String,Object>
	 * <br> {list:'',index:'',limit:'',count:'',pageCount:''}
	 */
	private Map<String, Object> subList(List<Map<String, Object>> list, Integer index, int limit){
		int count = list.size();
		index = index==null ? 1 : index;
		int pageCount = new Double(Math.ceil(MathUtils.getDivisionResult(list.size(), limit, 1))).intValue();
		int start = (index-1)*limit; start = start>count ? count : start;
		int end = index*limit; end = end>count ? count : end;
		list = list.subList(start, end);
		Map<String, Object> m = null; String _index = "_index";
		for(int i=0,len=list.size(); i<len; i++){
			m = list.get(i);
			if(!m.containsKey(_index)){
				m.put(_index, start+i+1);
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		map.put("index", index);
		map.put("limit", limit);
		map.put("count", count); // 总数
		map.put("pageCount", pageCount); // 页数
		return map;
	}
	
	@Override
	public Map<String, Object> getParamMapForScoreOrGpa(String stuSql, String schoolYear, String termCode, 
			String scoreSql, String target, List<AdvancedParam> businessAdvancedList){
		return getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, scoreSql, target, businessAdvancedList, null);
	}
	
//	/** 计算 院系/专业/班级/学科 成绩列表数据 所需要的参数 */
	public Map<String, Object> getParamMapForScoreOrGpa(String stuSql, String schoolYear, String termCode, 
			String scoreSql, String target, List<AdvancedParam> businessAdvancedList, Map<String, Object> map){
		map = map==null ? new HashMap<String, Object>() : map;
		
		// GPA list
		List<Double> valueList = null;
		String gpaSql = null, betterSql = null, failSql = null, rebuildSql = null, values = null;
		// 处理业务参数
		Map<String, Object> paramMap = new HashMap<>();
		for(AdvancedParam t : businessAdvancedList){
			values = t.getValues();
			if(values == null) continue;
			paramMap.put(t.getCode(), values); 
		}
		// 成绩 sql
		if(target == null || target.equals(SCORE_BETTER) || target.equals(SCORE_FAIL) || target.equals(SCORE_REBUILD)
				|| paramMap.containsKey(AdvancedUtil.Business_COURSE_ATTR_CODE)
				|| paramMap.containsKey(AdvancedUtil.Business_COURSE_NATURE_CODE) ){
			scoreSql = scoreSql!=null ? scoreSql : businessDao.getStuScoreSql(stuSql, schoolYear, termCode);
			if(target == null || target.equals(SCORE_BETTER) || target.equals(SCORE_FAIL) || target.equals(SCORE_REBUILD)){
				betterSql  = businessDao.getStuScoreBetterSql(scoreSql); // 优秀
				failSql    = businessDao.getStuScoreFailSql(scoreSql); // 挂科
				rebuildSql = businessDao.getStuScoreRebuildSql(scoreSql); // 重修
			}
			List<String> sqlParamList = new ArrayList<>();
			if(paramMap.containsKey(AdvancedUtil.Business_COURSE_ATTR_CODE)){
				sqlParamList.add("COURSE_ATTR_CODE in("+MapUtils.getString(paramMap, AdvancedUtil.Business_COURSE_ATTR_CODE)+")");
			}else if(paramMap.containsKey(AdvancedUtil.Business_COURSE_NATURE_CODE)){
				sqlParamList.add("COURSE_NATURE_CODE in("+MapUtils.getString(paramMap, AdvancedUtil.Business_COURSE_NATURE_CODE)+")");
			}
			if(!sqlParamList.isEmpty()){
				scoreSql = "select * from("+scoreSql+") where "+ StringUtils.join(sqlParamList, " and ");
			}
		}
		if(scoreSql != null)
			gpaSql = businessDao.getStuGpaSql(scoreSql);
		if(target == null || target.equals(SCORE_AVG) || target.equals(SCORE_MIDDLE) || target.equals(SCORE_MODE)
			|| target.equals(SCORE_FC) || target.equals(SCORE_BZC) || target.equals(SCORE_UNDER)){
			gpaSql = gpaSql!=null ? gpaSql : businessDao.getStuGpaSql(stuSql, schoolYear, termCode, GpaId);
		}
		if(!map.containsKey("valueList")){
			if(gpaSql != null){
				valueList = baseDao.queryForListDoubleLarge("select gpa from("+gpaSql+")");
			}else if(scoreSql != null){
				valueList = baseDao.queryForListDoubleLarge("select score from("+scoreSql+")");
			}
			map.put("valueList", valueList);
		}
		
		map.put("scoreSql", scoreSql);
		map.put("betterSql", betterSql);
		map.put("failSql", failSql);
		map.put("rebuildSql", rebuildSql);
		return map;
	}
	
	@Override
	public Double getScoreValueByTarget(List<Double> valueList, String scoreSql, 
			String betterSql, String failSql, String rebuildSql, String target){
		Map<String, Object> map = getScoreValueMapByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, target, null);
		return MapUtils.getDouble(map, target);
	}
	
	@Override
	public Map<String, Object> getScoreValueMapByTarget(List<Double> valueList, String scoreSql, 
			String betterSql, String failSql, String rebuildSql, String target){
		return getScoreValueMapByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, target, null);
	}
	
	@Override
	public Map<String, Object> getScoreValueMapByTarget(List<Double> valueList, String scoreSql, 
			String betterSql, String failSql, String rebuildSql, String target, String firstTarget) {
		Map<String, Object>	scoreMap = new HashMap<>();
		Double avgValue = null; // 成绩平均数
		if(target == null || target.equals(SCORE_AVG) || target.equals(SCORE_UNDER)){
			avgValue = MathUtils.getAvgValue(valueList);
		}
		int count = 0; // 有成绩的学生数
		if(target == null || target.equals(SCORE_BETTER) || target.equals(SCORE_FAIL) || target.equals(SCORE_REBUILD)){
			count = baseDao.queryForCount(scoreSql);
		}
		// (排序字段为空 || 排序字段是“平均数”) && (第一次排序字段为空 || 第一次排序字段不是“平均数”) 后面雷同
		if((target == null || target.equals(SCORE_AVG)) && (firstTarget==null || !firstTarget.equals(SCORE_AVG))){
			scoreMap.put(SCORE_AVG, avgValue);
		}
		if((target == null || target.equals(SCORE_MIDDLE)) && (firstTarget==null || !firstTarget.equals(SCORE_MIDDLE))){
			scoreMap.put(SCORE_MIDDLE, MathUtils.getMiddleValue(valueList));
		}
		if((target == null || target.equals(SCORE_MODE)) && (firstTarget==null || !firstTarget.equals(SCORE_MODE))){
			scoreMap.put(SCORE_MODE, MathUtils.getModeValue(valueList));
		}
		if((target == null || target.equals(SCORE_FC)) && (firstTarget==null || !firstTarget.equals(SCORE_FC))){
			scoreMap.put(SCORE_FC, MathUtils.getVariance(valueList));
		}
		if((target == null || target.equals(SCORE_BZC)) && (firstTarget==null || !firstTarget.equals(SCORE_BZC))){
			scoreMap.put(SCORE_BZC, MathUtils.getStandardDeviation(valueList));
		}
		if((target == null || target.equals(SCORE_BETTER)) && (firstTarget==null || !firstTarget.equals(SCORE_BETTER))){
			scoreMap.put(SCORE_BETTER, MathUtils.getPercentNum(baseDao.queryForCount(betterSql), count));
		}
		if((target == null || target.equals(SCORE_FAIL)) && (firstTarget==null || !firstTarget.equals(SCORE_FAIL))){
			scoreMap.put(SCORE_FAIL, MathUtils.getPercentNum(baseDao.queryForCount(failSql), count));
		}
		if((target == null || target.equals(SCORE_REBUILD)) && (firstTarget==null || !firstTarget.equals(SCORE_REBUILD))){
			scoreMap.put(SCORE_REBUILD, MathUtils.getPercentNum(baseDao.queryForCount(rebuildSql), count));
		}
		if((target == null || target.equals(SCORE_UNDER)) && (firstTarget==null || !firstTarget.equals(SCORE_UNDER))){
			int underCount = 0;
			for(Double value : valueList){
				if(value < avgValue) underCount++;
			}
			scoreMap.put(SCORE_UNDER, MathUtils.getPercentNum(underCount, valueList.size()));
		}
		return scoreMap;
	}
	
	/**
	 * 根据参数优化参数
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getParamsMapByParams(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String edu){
		if(schoolYear == null && termCode == null){
			String advYear = AdvancedUtil.getValue(advancedParamList, AdvancedUtil.Common_SCHOOL_YEAR);
			if(advYear == null){
				List<Map<String, Object>> list = businessService.queryBzdmScoreXnXq();
				Map<String, Object> map = (list!=null && list.size()>0) ? list.get(0) : null;
				if(map != null){
					String[] idAry = MapUtils.getString(map, "id").split(",");
					if(idAry.length == 2){
						schoolYear = idAry[0]; termCode = idAry[1];
					}
				}
				if(schoolYear == null && termCode == null){
					String[] termAry = EduUtils.getSchoolYearTerm(DateUtils.getNowDate());
					schoolYear = schoolYear==null ? termAry[0] : schoolYear;
					termCode   = termCode==null ? termAry[1] : termCode;
				}
			}else{
				schoolYear = advYear;
			}
		}
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		
//		schoolYear = "2014-2015"; termCode = "01"; // TODO del 因为本地库目前只有这个时间段有数据
		
		Integer year = Integer.valueOf(schoolYear.substring(0, 4));
		List<String> deptList = getDeptDataList();
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		advancedParamList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		List<AdvancedParam> stuAdvancedList      = AdvancedUtil.getAdvancedParamStu(advancedParamList),
							businessAdvancedList = AdvancedUtil.getAdvancedParamBusiness(advancedParamList);
		String stuSql = businessDao.getStuSql(year, deptList, stuAdvancedList),
			   target = AdvancedUtil.getValue(advancedParamList, AdvancedUtil.Business_SCORE_TARGET);
		target = (target==null||"".equals(target)) ? Constant.SCORE_AVG : target;
		
		// return
		Map<String, Object> map = new HashMap<>();
		map.put("advancedParamList", advancedParamList);
		map.put("stuAdvancedList", stuAdvancedList);
		map.put("businessAdvancedList", businessAdvancedList);
		map.put("deptList", deptList);
		map.put("schoolYear", schoolYear);
		map.put("termCode", termCode);
		map.put("year", year);
		map.put("target", target);
		map.put("stuSql", stuSql);
		map.put("edu", edu);
		return map;
	}

	private List<Map<String, Object>> getLogData(String type,
			String schoolYear, String termCode, List<String> deptList) {
		int year = Integer.parseInt(schoolYear.substring(0, 4));
		String tableName = "t_stu_score_result_dept", columnName = "dept_id = b.id", stuSql = businessDao.getStuSql(year, deptList, new ArrayList<AdvancedParam>()),
			   resultSql = "", str = "", name = "b.name";
		String term = termCode == null ? " term_code is null " : " term_code = '"
				+ termCode + "' ";
		if (type == null || "YX".equals(type)) {
			resultSql = businessDao.getYxIdSqlByDeptList(deptList);
			str = " inner join " + Constant.TABLE_T_Code_Dept_Teach
					+ " c on b.id = c.id ";
			name = "c.name_ as name";
		} else if ("ZY".equals(type)) {
			resultSql = businessDao.getZyIdSqlByDeptListStu(deptList);
			str = " inner join " + Constant.TABLE_T_Code_Dept_Teach
					+ " c on b.id = c.id ";
			name = "c.name_ as name";
		} else if ("BJ".equals(type)) {
			resultSql = businessDao.getClassesIdSqlByDeptList(deptList, year);
			str = " inner join t_classes c on b.no = c.no_ ";
			name = "c.name_ as name";
			columnName = "dept_id = b.no";
		} else if ("SUBJECT".equals(type)) {
			resultSql = businessDao.getSubjectDegreeUsefulSql();
			tableName = "t_stu_score_result_subject";
			columnName = "subject_degree_id = b.id";
			name = "b.name_ as name";
		} else if ("COURSE".equals(type)) {
			resultSql = scoreDao.queryCourseSql(stuSql, schoolYear, termCode);
			tableName = "t_stu_score_result_course";
			columnName = "course_id = b.id";
		} else if ("TEACHER".equals(type)) {
			resultSql = scoreDao.queryTeachSql(stuSql, schoolYear, termCode);
			tableName = "t_stu_score_result_teacher";
			columnName = "tea_id = b.id";
		}
		/** 根据组织机构、学科、课程、教师sql查询要展示的数据 **/
		String sql = "select  a.id," + name + ",gpa_avg as " + SCORE_AVG + ","
				+ " gpa_middle as " + SCORE_MIDDLE + ", gpa_mode as \""
				+ SCORE_MODE + "\"," + " gpa_fc as " + SCORE_FC
				+ ", gpa_bzc as " + SCORE_BZC + ",better as " + SCORE_BETTER
				+ "," + " fail as " + SCORE_FAIL + ", rebuild as "
				+ SCORE_REBUILD + ", under as " + SCORE_UNDER + "" + " from "
				+ tableName + " a " + " inner join (" + resultSql + ") b on a."
				+ columnName + str + " where " + " school_year = '"
				+ schoolYear + "' " + " and " + term;
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return list;
	}
}
