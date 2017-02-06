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
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.ScoreHistoryDao;
import cn.gilight.dmm.teaching.service.ScoreHistoryService;
import cn.gilight.dmm.teaching.service.ScoreService;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月30日 上午11:18:13
 */
@Service("scoreHistoryService")
public class ScoreHistoryServiceImpl implements ScoreHistoryService {

	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private ScoreHistoryDao scoreHistoryDao;
	@Resource
	private ScoreService scoreService;

	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"scoreHistory";

	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getHistoryYear(List<AdvancedParam> advancedParamList){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList);
		List<AdvancedParam> stuAdvancedList      = (List<AdvancedParam>) paramM.get("stuAdvancedList"),
							businessAdvancedList = (List<AdvancedParam>) paramM.get("businessAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		String target = MapUtils.getString(paramM, "target"),
			   schoolYear = MapUtils.getString(paramM, "schoolYear"),
			   termCode   = MapUtils.getString(paramM, "termCode"),
			   stuSql = null, scoreSql = null, betterSql = null, failSql = null, rebuildSql = null;
		Map<String, Object> paramMap = null;
		List<Double> valueList = null;
		List<Map<String, Object>> list = new ArrayList<>();
		// 判断需要显示到哪一学年
		int thisYear = EduUtils.getSchoolYear4(), len = Constant.Year_His_Len;
		List<Map<String, Object>> xnxqList = businessService.queryBzdmScoreXnXq();
		if(xnxqList!=null && xnxqList.size()>0){
			String[] idAry = MapUtils.getString(xnxqList.get(0), "id").split(",");
			if(idAry.length == 2) thisYear = Integer.valueOf(idAry[0].substring(0, 4));
		}
		for( ; len>0; len--){
			int year_  = thisYear-len+1;
			stuSql     = businessDao.getStuSql(year_, deptList, stuAdvancedList);
			schoolYear = year_+"-"+(year_+1);
			paramMap   = scoreService.getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, null, target, businessAdvancedList);
			valueList  = (List<Double>) paramMap.get("valueList");
			scoreSql   = MapUtils.getString(paramMap, "scoreSql");
			betterSql  = MapUtils.getString(paramMap, "betterSql");
			failSql    = MapUtils.getString(paramMap, "failSql");
			rebuildSql = MapUtils.getString(paramMap, "rebuildSql");
			Double value = scoreService.getScoreValueByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, target);
			Map<String, Object> oneMap = new HashMap<>();
			oneMap.put("name", year_);
			oneMap.put("value", value);
			list.add(oneMap);
		}
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", list);
		return reMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSex(List<AdvancedParam> advancedParamList){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList);
		List<AdvancedParam> stuAdvancedList      = (List<AdvancedParam>) paramM.get("stuAdvancedList"),
							businessAdvancedList = (List<AdvancedParam>) paramM.get("businessAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		Integer year = MapUtils.getInteger(paramM, "year");
		String target = MapUtils.getString(paramM, "target"),
			   schoolYear = MapUtils.getString(paramM, "schoolYear"),
			   termCode   = MapUtils.getString(paramM, "termCode"),
			   stuSql = null, scoreSql = null, betterSql = null, failSql = null, rebuildSql = null, code = null;
		Map<String, Object> paramMap = null;
		List<Double> valueList = null;
		AdvancedParam sexAdp = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_SEX_CODE, null);
		stuAdvancedList.add(sexAdp);
		List<Map<String, Object>> bzdmList = businessService.queryBzdmSex();
		List<Map<String, Object>> list = new ArrayList<>();
		for(Map<String, Object> m : bzdmList){
			code = MapUtils.getString(m, "id");
			sexAdp.setValues(code);
			stuSql = businessDao.getStuSql(year, deptList, stuAdvancedList);
			paramMap   = scoreService.getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, null, target, businessAdvancedList);
			valueList  = (List<Double>) paramMap.get("valueList");
			scoreSql   = MapUtils.getString(paramMap, "scoreSql");
			betterSql  = MapUtils.getString(paramMap, "betterSql");
			failSql    = MapUtils.getString(paramMap, "failSql");
			rebuildSql = MapUtils.getString(paramMap, "rebuildSql");
			Double value = scoreService.getScoreValueByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, target);
			Map<String, Object> oneMap = new HashMap<>();
			oneMap.put("name", MapUtils.getString(m, "mc"));
			oneMap.put("value", value);
			oneMap.put("code", code);
			list.add(oneMap);
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGrade(List<AdvancedParam> advancedParamList){
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList);
		List<AdvancedParam> stuAdvancedList      = (List<AdvancedParam>) paramM.get("stuAdvancedList"),
							businessAdvancedList = (List<AdvancedParam>) paramM.get("businessAdvancedList");
		List<String> deptList = (List<String>) paramM.get("deptList");
		Integer year = MapUtils.getInteger(paramM, "year");
		String target = MapUtils.getString(paramM, "target"),
		   schoolYear = MapUtils.getString(paramM, "schoolYear"),
		   termCode   = MapUtils.getString(paramM, "termCode"),
		   stuSql = null, scoreSql = null, betterSql = null, failSql = null, rebuildSql = null, code = null;
		Map<String, Object> paramMap = null;
		List<Double> valueList = null;
		AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_GRADE, null);
		stuAdvancedList.add(gradeAdp);
		List<Map<String, Object>> bzdmList = businessService.queryBzdmNj();
		List<Map<String, Object>> list = new ArrayList<>();
		for(Map<String, Object> m : bzdmList){
			code = MapUtils.getString(m, "id");
			gradeAdp.setValues(code);
			stuSql = businessDao.getStuSql(year, deptList, stuAdvancedList);
			paramMap = scoreService.getParamMapForScoreOrGpa(stuSql, schoolYear, termCode, null, target, businessAdvancedList);
			valueList  = (List<Double>) paramMap.get("valueList");
			scoreSql   = MapUtils.getString(paramMap, "scoreSql");
			betterSql  = MapUtils.getString(paramMap, "betterSql");
			failSql    = MapUtils.getString(paramMap, "failSql");
			rebuildSql = MapUtils.getString(paramMap, "rebuildSql");
			Double value = scoreService.getScoreValueByTarget(valueList, scoreSql, betterSql, failSql, rebuildSql, target);
			Map<String, Object> oneMap = new HashMap<>();
			oneMap.put("name", MapUtils.getString(m, "mc"));
			oneMap.put("value", value);
			oneMap.put("code", code);
			list.add(oneMap);
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	
	/**
	 * 根据参数优化参数
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getParamsMapByParams(List<AdvancedParam> advancedParamList){
		// 历年页面 只处理学年 不处理学期 学期默认为null
		AdvancedParam schoolYearAdp = AdvancedUtil.get(advancedParamList, AdvancedUtil.Common_SCHOOL_YEAR);
		String schoolYear = schoolYearAdp.getValues(), termCode = null;
		if(schoolYear == null && termCode == null){
			List<Map<String, Object>> list = businessService.queryBzdmScoreXnXq();
			Map<String, Object> map = (list!=null && list.size()>0) ? list.get(0) : null;
			if(map != null){
				String[] idAry = MapUtils.getString(map, "id").split(",");
				if(idAry.length == 2){
					schoolYear = idAry[0]; 
//					termCode = idAry[1];
				}
				if(schoolYear == null && termCode == null){
					String[] termAry = EduUtils.getSchoolYearTerm(DateUtils.getNowDate());
					schoolYear = schoolYear==null ? termAry[0] : schoolYear;
//					termCode   = termCode==null ? termAry[1] : termCode;
				}
			}
		}
//		schoolYear = "2014-2015"; termCode = "01"; // TODO del 因为本地库目前只有这个时间段有数据
		
		Integer year = Integer.valueOf(schoolYear.substring(0, 4));
		List<String> deptList = getDeptDataList();
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList),
							businessAdvancedList = AdvancedUtil.getAdvancedParamBusiness(advancedParamList);
		String target = AdvancedUtil.getValue(advancedParamList, AdvancedUtil.Business_SCORE_TARGET),
			   stuSql = businessDao.getStuSql(year, deptList, stuAdvancedList);
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
		return map;
	}
	
}
