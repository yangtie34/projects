package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.ScoreByStuLbDao;
import cn.gilight.dmm.teaching.service.ScoreByStuLbService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;

@Service("scoreByStuLbService")
public class ScoreByStuLbServiceImpl implements ScoreByStuLbService {
	
	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao baseDao;
	@Resource
	private ScoreByStuLbDao scoreByStuLbDao;
	
	
	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"scoreByStuLb";
	private static final Object[][] score_target = {{Constant.SCORE_AVG,"平均数"},{Constant.SCORE_MIDDLE,"中位数"},{Constant.SCORE_MODE,"众数"},
		{Constant.SCORE_FC,"方差"},{Constant.SCORE_BZC,"标准差"}};
	/**
	 * 获取数据权限
	 */
	@SuppressWarnings("unused")
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	
	@Override
	public List<Map<String,Object>> getXnXqList(){
		List<Map<String,Object>> list = businessService.queryBzdmScoreXnXq();
		if(list == null || list.isEmpty()){
			String[] ary = EduUtils.getSchoolYearTerm(new Date());
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", ary[0]+","+ary[1]);
			String xq = "第二学期";
			if(ary[1].equals(Globals.TERM_FIRST)){
				xq = "第一学期";
			}
			map.put("mc", ary[0]+"学年"+" "+xq);
			list.add(map);
		}
		return list;
	}
	@Override
	public List<Map<String,Object>> getTargetList(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Object[] obj : score_target){
			Map<String,Object> temp = new HashMap<String, Object>();
			temp.put("id", obj[0]);
			temp.put("mc", obj[1]);
			list.add(temp);
		}
		return list;
	}
	@Override
	public List<Map<String,Object>> getCourseTypeList(String schoolYear,String termCode,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		List<Map<String,Object>> result = scoreByStuLbDao.getCourseTypeList(schoolYear, termCode, deptList, advancedList);
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("id", "all");map.put("mc", "所有课程类型");
		result.add(0, map);
		return result;
	}
	@Override
	public List<Map<String,Object>> getCourseCategoryList(String schoolYear,String termCode,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		List<Map<String,Object>> result = scoreByStuLbDao.getCourseCategoryList(schoolYear, termCode,"COURSE_CATEGORY_CODE",deptList, advancedList);
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("id", "all");map.put("mc", "所有课程类别");
		result.add(0, map);
		return result;
	}
	@Override
	public List<Map<String,Object>> getCourseAttrList(String schoolYear,String termCode,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		List<Map<String,Object>> result = scoreByStuLbDao.getCourseCategoryList(schoolYear, termCode,"COURSE_ATTR_CODE",deptList, advancedList);
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("id", "all");map.put("mc", "所有课程属性");
		result.add(0, map);
		return result;
	}
	@Override
	public List<Map<String,Object>> getCourseNatureList(String schoolYear,String termCode,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		List<Map<String,Object>> result = scoreByStuLbDao.getCourseCategoryList(schoolYear, termCode,"COURSE_NATURE_CODE",deptList, advancedList);
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("id", "all");map.put("mc", "所有课程性质");
		result.add(0, map);
		return result;
	}
	@Override
	public List<Map<String,Object>> getCourseList(String schoolYear,String termCode,String type,String category,String attr,
			String nature,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		List<Map<String,Object>> result = scoreByStuLbDao.getCourseList(schoolYear, termCode, type, category, attr, nature, deptList, advancedList);
		Map<String,Object> map= new HashMap<String, Object>();
		map.put("id", "all");map.put("mc", "所有课程");
		result.add(0, map);
		return result;
	}
	@Override
	public Map<String,Object> getScoreByOriginList(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,String tag,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		String originId = (advancedList==null || advancedList.isEmpty())?businessService.getOriginIdByAbsoluteScale():getOriginId(advancedList);
		List<Map<String,Object>> list = scoreByStuLbDao.getNextById(originId),
				                 result = new ArrayList<Map<String,Object>>();
		for (Map<String,Object> temp: list){
			Map<String,Object> temp1 = new HashMap<String, Object>();
			String id = MapUtils.getString(temp, "id");
			String mc = MapUtils.getString(temp, "mc");
			if(advancedList == null || advancedList.isEmpty()){
				advancedList = new ArrayList<AdvancedParam>();
				AdvancedParam sydAdp =  new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, id);
				AdvancedUtil.add(advancedList,sydAdp);
			}else{
				for (AdvancedParam e : advancedList){
					if(AdvancedUtil.Stu_ORIGIN_ID.equals(e.getCode())){
						e.setValues(id);
					}
				}
			}
			Double value = scoreByStuLbDao.getValue(schoolYear, termCode, type, category, attr, nature, course, tag, deptList, advancedList);
		    temp1.put("code", id);
		    temp1.put("field", mc);
		    temp1.put("value", value);
		    result.add(temp1);
		}
		Iterator<Map<String, Object>> ite = result.iterator();
		while(ite.hasNext()){
		Map<String, Object> m = ite.next();
		if("0.0".equals(MapUtils.getString(m, "value"))){
		ite.remove();
		}
		}
		compareCount(result, "value", false);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("id", (result== null || result.isEmpty())?null:MapUtils.getString(result.get(0), "code"));
		map.put("mc", (result== null || result.isEmpty())?null:MapUtils.getString(result.get(0), "field"));
		return map;
	}
	@Override
	public List<Map<String,Object>> getScoreFbByOrigin(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,String originId,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		if(advancedList == null || advancedList.isEmpty()){
			advancedList = new ArrayList<AdvancedParam>();
			AdvancedParam sydAdp =  new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, originId);
			AdvancedUtil.add(advancedList,sydAdp);
		}else{
			for (AdvancedParam e : advancedList){
				if(AdvancedUtil.Stu_ORIGIN_ID.equals(e.getCode())){
					e.setValues(originId);
				}
			}
		}
		List<Double> list = scoreByStuLbDao.getScoreList(schoolYear, termCode, type, category, attr, nature, course, deptList, advancedList);
		return getScoreGroup(list);
	}
	@Override
	public Map<String,Object> getScoreByNationList(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,String tag,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		List<Map<String,Object>> list = businessService.queryBzdmByTCodeIsUse("stu", "NATION_CODE","NATION_CODE", "count"),
				                 result = new ArrayList<Map<String,Object>>();
		for (Map<String,Object> temp: list){
			Map<String,Object> temp1 = new HashMap<String, Object>();
			String id = MapUtils.getString(temp, "id");
			String mc = MapUtils.getString(temp, "mc");
			advancedList = new ArrayList<AdvancedParam>();
			AdvancedParam nationAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_NATION_CODE, id);
			AdvancedUtil.add(advancedList,nationAdp);
			Double value = scoreByStuLbDao.getValue(schoolYear, termCode, type, category, attr, nature, course, tag, deptList, advancedList);
		    temp1.put("code", id);
		    temp1.put("field", mc);
		    temp1.put("value", value);
		    result.add(temp1);
		}
		Iterator<Map<String, Object>> ite = result.iterator();
		while(ite.hasNext()){
		Map<String, Object> m = ite.next();
		if("0.0".equals(MapUtils.getString(m, "value"))){
		ite.remove();
		}
		}
		compareCount(result, "value", false);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("id", (result== null || result.isEmpty())?null:MapUtils.getString(result.get(0), "code"));
		map.put("mc", (result== null || result.isEmpty())?null:MapUtils.getString(result.get(0), "field"));
		return map;
	}
	@Override
	public List<Map<String,Object>> getScoreFbByNation(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,String nationId,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		advancedList = new ArrayList<AdvancedParam>();
		AdvancedParam mzAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_NATION_CODE, nationId);
		AdvancedUtil.add(advancedList,mzAdp);
		List<Double> list = scoreByStuLbDao.getScoreList(schoolYear, termCode, type, category, attr, nature, course, deptList, advancedList);
		return getScoreGroup(list);
	}
	@Override
	public Map<String,Object> getScoreByZzmmList(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,String tag,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		List<Map<String,Object>> list = businessService.queryBzdmByTCodeIsUse("stu", "POLITICS_CODE","POLITICS_CODE", "other"),
				                 result = new ArrayList<Map<String,Object>>();
		for (Map<String,Object> temp: list){
			Map<String,Object> temp1 = new HashMap<String, Object>();
			String id = MapUtils.getString(temp, "id");
			String mc = MapUtils.getString(temp, "mc");
			advancedList = new ArrayList<AdvancedParam>();
			AdvancedParam zzmmAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_POLITICS_CODE, id);
			AdvancedUtil.add(advancedList,zzmmAdp);
			Double value = scoreByStuLbDao.getValue(schoolYear, termCode, type, category, attr, nature, course, tag, deptList, advancedList);
		    temp1.put("code", id);
		    temp1.put("field", mc);
		    temp1.put("value", value);
		    result.add(temp1);
		}
		Iterator<Map<String, Object>> ite = result.iterator();
		while(ite.hasNext()){
		Map<String, Object> m = ite.next();
		if("0.0".equals(MapUtils.getString(m, "value"))){
		ite.remove();
		}
		}
		compareCount(result, "value", false);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("id", (result== null || result.isEmpty())?null:MapUtils.getString(result.get(0), "code"));
		map.put("mc", (result== null || result.isEmpty())?null:MapUtils.getString(result.get(0), "field"));
		return map;
	}
	@Override
	public List<Map<String,Object>> getScoreFbByZzmm(String schoolYear,String termCode,String type,String category,String attr,
			String nature,String course,String zzmmId,List<AdvancedParam> advancedList){
		List<String> deptList =getDeptDataList();
		advancedList = new ArrayList<AdvancedParam>();
		AdvancedParam mmAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_POLITICS_CODE, zzmmId);
		AdvancedUtil.add(advancedList,mmAdp);
		List<Double> list = scoreByStuLbDao.getScoreList(schoolYear, termCode, type, category, attr, nature, course, deptList, advancedList);
		return getScoreGroup(list);
	}
	private String getOriginId(List<AdvancedParam> advancedParamList){
		String id = AdvancedUtil.getValue(advancedParamList, AdvancedUtil.Stu_ORIGIN_ID);
		return id;
	}
	/**
	 * 集合排序
	 * 
	 * @param list
	 *            void
	 * @param compareField
	 */
	private void compareCount(List<Map<String, Object>> list,
			final String compareField, final boolean asc) {
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				Double count1 = MapUtils.getDoubleValue(o1, compareField), count2 = MapUtils
						.getDoubleValue(o2, compareField); int flag = 0, pare = asc
						? 1
						: -1; // 正序为1
				if (count1 > count2)
					flag = pare;
				else if (count1 < count2)
					flag = -pare;
				return flag;
			}
		});
	}
	private List<Map<String,Object>> getScoreGroup(List<Double> list){
		List<Map<String,Object>> group = Constant.getScoreGroup(),
				                 result =new ArrayList<Map<String,Object>>();
		for (Map<String,Object> map : group){
			Map<String,Object> temp = new HashMap<String, Object>();
			List<Double> value= new ArrayList<Double>();
			String id= MapUtils.getString(map, "id");
			String mc= MapUtils.getString(map, "mc1");
			String[] ary = id.split(",");
			String start = ary[0].equals("null") || ary[0] == null?
					"":ary[0],
					end = ary[1].equals("null") || ary[1] == null?
					"":ary[1];
			for(Double db:list){
				if (start.equals("") && !end.equals("")){
					if(db < Double.valueOf(end)){
						value.add(db);
					}
				}else if(!start.equals("") && end.equals("")){
					if(db >= Double.valueOf(start)){
						value.add(db);
					}
				}else if(!start.equals("") && !end.equals("")){
					if(db < Double.valueOf(end) && db >= Double.valueOf(start)){
						value.add(db);
					}
				}
			}
			temp.put("name", mc);
			temp.put("value", value.size());
			temp.put("code", id);
			result.add(temp);
		}
		Iterator<Map<String, Object>> ite = result.iterator();
		while(ite.hasNext()){
		Map<String, Object> m = ite.next();
		if(MapUtils.getIntValue(m, "value") == 0){
		ite.remove();
		}
		}
		return result;
	};
}
