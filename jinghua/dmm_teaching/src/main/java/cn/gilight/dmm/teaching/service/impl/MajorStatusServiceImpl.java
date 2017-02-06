package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.service.MajorStatusService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 专业开设
 * 
 * @author xuebl
 * @date 2016年10月18日 下午5:19:00
 */
@Repository("majorStatusService")
public class MajorStatusServiceImpl implements MajorStatusService {

	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao baseDao;

	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"majorStatus";
	
	/**
	 * 获取数据权限
	 */
	@SuppressWarnings("unused")
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	
	@Override
	public Map<String, Object> queryXn(){
		// 以成绩数据查询存在多少学年学期
		List<Map<String, Object>> xnList = businessService.queryBzdmScoreXn();
		Map<String, Object> map = DevUtils.MAP();
		map.put("xn", xnList);
		return map;
	}
	
	/**
	 * 课程属性编码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> queryBzdmCourseAttrCode(){
//		String sql = "select distinct(code.code_) id, code.name_ mc, code.order_ from t_major_score t, t_code code"
//				+ " where t.course_attr_code=code.code_ and code.code_type='"+Constant.CODE_COURSE_ATTR_CODE+"'"
//				+ " order by code.order_,code.code_";
//		return baseDao.queryListInLowerKey(sql);
		return businessService.queryBzdmCourseNature();
	}
	
	@Override
	public Map<String, Object> queryMajorScoreList(List<AdvancedParam> advancedParamList, Page page,
			String schoolYear, String order, String asc){
		// 初始化参数
		schoolYear = getSchoolYear(schoolYear);
		String schoolYear_last = EduUtils.getLastSchoolYear9(schoolYear);
		boolean isAsc = getIsAsc(asc);
		String table  = "T_MAJOR_SCORE",
			   key_id = "id",
			   key_name = "name",
			   key_code = "code",
			   key_val  = "value",
			   key_ranking_change = "ranking_change";
		order = order==null ? key_val : order;
		
		// query data
		String sql = "select * from (select t.major_id "+key_id+", dept.name_ "+key_name+", t.school_year school_year,"
				+ " t.course_attr_code "+key_code+", t.score_avg "+key_val+","
//				+ " case when t.ranking is null then 0 else case when t2.ranking is null then t.ranking"
				+ " case when t.ranking is null or t2.ranking is null then null"
				+ " else t2.ranking-t.ranking end "
//				+ " end "
				+ key_ranking_change
				+ " from t_code_dept_teach dept, "+table+" t"
				+ " left join "+table+" t2 on t.major_id=t2.major_id and t.course_attr_code=t2.course_attr_code and t2.school_year='"+schoolYear_last+"'"
				+ " where t.major_id=dept.id and t.school_year = '"+schoolYear+"') order by "+key_val+" asc,"+key_ranking_change+" asc";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		// 
		return handlerListForGrid(list, page, order, isAsc);
	}
	
	@Override
	public Map<String, Object> queryMajorFailScaleList(List<AdvancedParam> advancedParamList, Page page,
			String schoolYear, String order, String asc){
		// 初始化参数
		schoolYear = getSchoolYear(schoolYear);
		String schoolYear_last = EduUtils.getLastSchoolYear9(schoolYear);
		boolean isAsc = getIsAsc(asc);
		String table  = "T_MAJOR_FAIL_CLASS",
			   key_id = "id",
			   key_name = "name",
			   key_code = "code",
			   key_val  = "value",
			   key_ranking_change = "ranking_change";
		order = order==null ? key_val : order;
		
		// query data
		String sql = "select * from (select t.major_id "+key_id+", dept.name_ "+key_name+", t.school_year school_year,"
				+ " t.course_attr_code "+key_code+", t.FAIL_CLASS_RATE "+key_val+","
//				+ " case when t.ranking is null then 0 else case when t2.ranking is null then t.ranking"
				+ " case when t.ranking is null or t2.ranking is null then null"
				+ " else t2.ranking-t.ranking end "
//				+ " end "
				+ key_ranking_change
				+ " from t_code_dept_teach dept, "+table+" t"
				+ " left join "+table+" t2 on t.major_id=t2.major_id and t.course_attr_code=t2.course_attr_code and t2.school_year='"+schoolYear_last+"'"
				+ " where t.major_id=dept.id and t.school_year = '"+schoolYear+"') order by "+key_val+" asc,"+key_ranking_change+" asc";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		// 
		return handlerListForGrid(list, page, order, isAsc);
	}
	
	@Override
	public Map<String, Object> queryMajorEvaluateTeachList(List<AdvancedParam> advancedParamList, Page page,
			String schoolYear, String order, String asc){
		// 初始化参数
		schoolYear = getSchoolYear(schoolYear);
		String schoolYear_last = EduUtils.getLastSchoolYear9(schoolYear);
		boolean isAsc = getIsAsc(asc);
		String table  = "T_MAJOR_EVALUATE_TEACHING",
			   key_id = "id",
			   key_name = "name",
			   key_code = "code",
			   key_val  = "value",
			   key_ranking_change = "ranking_change";
		order = order==null ? key_val : order;
		
		// query data
		String sql = "select * from (select t.major_id "+key_id+", dept.name_ "+key_name+", t.school_year school_year,"
				+ " t.course_attr_code "+key_code+", t.EVALUATE_TEACHING_SCORE_AVG "+key_val+","
//				+ " case when t.ranking is null then 0 else case when t2.ranking is null then t.ranking"
				+ " case when t.ranking is null or t2.ranking is null then null"
				+ " else t2.ranking-t.ranking end "
//				+ " end "
				+ key_ranking_change
				+ " from t_code_dept_teach dept, "+table+" t"
				+ " left join "+table+" t2 on t.major_id=t2.major_id and t.course_attr_code=t2.course_attr_code and t2.school_year='"+schoolYear_last+"'"
				+ " where t.major_id=dept.id and t.school_year = '"+schoolYear+"') order by "+key_val+" asc,"+key_ranking_change+" asc";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		// 
		return handlerListForGrid(list, page, order, isAsc);
	}

	/**
	 * 处理列表数据
	 * @param list
	 * @param page 分页数据
	 * @param orderType 排序字段
	 * @param isAsc 是否正序
	 * @return Map<String,Object>
	 */
	private Map<String, Object> handlerListForGrid(List<Map<String, Object>> list, Page page, 
			String orderType, boolean isAsc) {
		String key_id = "id",
			   key_name = "name",
			   key_code = "code",
			   key_val  = "value",
//			   key_ranking = "ranking",
			   key_ranking_change = "ranking_change";
		
		Map<String, Map<String, Object>> zyMap = new HashMap<>();
		List<Map<String, Object>> zyList = new ArrayList<>();
		Map<String, Object> oneZyMap = null;
		for(Map<String, Object> map : list){
			String majorId = MapUtils.getString(map, key_id);
			if(zyMap.containsKey(majorId)){
				oneZyMap = zyMap.get(majorId);
			}else{
				oneZyMap = new HashMap<>();
				oneZyMap.put(key_id, majorId);
				oneZyMap.put(key_name, MapUtils.getString(map, key_name));
				zyMap.put(majorId, oneZyMap);
				zyList.add(oneZyMap);
			}
			String course_attr_code = MapUtils.getString(map, key_code);
			// code为空，是平均成绩，同时取排名数据
			if(course_attr_code==null || "".equals(course_attr_code) || "ALL".equals(course_attr_code)){
				course_attr_code = key_val;
				oneZyMap.put(key_ranking_change, MapUtils.getInteger(map, key_ranking_change));
			}
			oneZyMap.put(course_attr_code, MapUtils.getDouble(map, key_val));
		}
		// sort
		sort(zyList, orderType, isAsc);
		
		// 提取 ID、名称、平均数、排名、各属性成绩
		List<Map<String, Object>> reList = new ArrayList<>();
		List<Map<String, Object>> attrList = queryBzdmCourseAttrCode();
		String val = null;
		for(int i=0,len=zyList.size(); i<len; i++){
			Map<String, Object> oneMap = zyList.get(i);
			oneZyMap = new HashMap<>();
			val = MapUtils.getString(oneMap, key_val);
			oneZyMap.put(key_id, MapUtils.getString(oneMap, key_id));
			oneZyMap.put(key_name, MapUtils.getString(oneMap, key_name));
			oneZyMap.put(key_val, (val==null||"".equals(val))?"-":val); // 空值
			oneZyMap.put(key_ranking_change, MapUtils.getString(oneMap, key_ranking_change));
			List<String> attrValList = new ArrayList<>();
			for(Map<String, Object> attrMap : attrList){
				String val_code = MapUtils.getString(oneMap, MapUtils.getString(attrMap, "id"));
				attrValList.add((val_code==null||"".equals(val_code))?"-":val_code); // 空值
			}
			oneZyMap.put("list", attrValList);
//			oneZyMap.put("index", i+1);
			reList.add(oneZyMap);
		}
		// 分页处理
		reList = (List<Map<String, Object>>) getPageList(reList, page);
		// reMap
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", reList);
		reMap.put("bzdm", attrList);
		reMap.put("pagecount", page.getPagecount());
		reMap.put("sumcount", page.getSumcount());
		return reMap;
	}

	/**
	 * List排序
	 * @param list
	 * @param key 排序字段
	 * @param isAsc 是否正序 void
	 */
	private void sort(List<Map<String, Object>> list, final String key, final boolean isAsc){
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			Double d1 = null, d2 = null;
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int k = 0;
				d1 = MapUtils.getDouble(o1, key);
				d2 = MapUtils.getDouble(o2, key);
				if(d1!=null && (d2==null || d1>d2)){ k = 1; }
				else if(d1==null || (d2!=null && d1<d2)){ k = -1; }
				if(!isAsc){ k = -k; }
				return k;
			}
		});
	}
	
	/**
	 * 获取分页list数据
	 * @param list
	 * @param page 分页对象
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> getPageList(List<Map<String, Object>> list, Page page){
		int pagesize = page.getPagesize();
		int curpage = page.getCurpage();
		curpage = curpage==0 ? 1 : curpage;
		int start = (curpage-1)*pagesize;
		int end = curpage*pagesize;
		int size = list.size();
		start = start<0 ? 0 : start;
		end = end>size ? size : end;
		// 设置总页数
		if(pagesize != 0){
			if(size % pagesize == 0){
				page.setPagecount(size/pagesize);
			}else{
				page.setPagecount(size/pagesize + 1);
			}
		}
		page.setSumcount(size);
		// 返回值
		list = list.subList(start, end);
		return list;
	}
	
	/**
	 * 获取学年(获取上一学年)
	 * @param schoolYear
	 * @return String
	 */
	private String getSchoolYear(String schoolYear){
		return schoolYear==null ? EduUtils.getLastSchoolYear9() : schoolYear;
	}
	/**
	 * 获取是否正序排序
	 * @param asc
	 * @return boolean
	 */
	private boolean getIsAsc(String asc){
		return (asc==null || "asc".equals(asc))?true:false;
	}
	
	@Override
	public Map<String, Object> queryMajorByJyList(List<AdvancedParam> advancedParamList, Page page,
			String schoolYear, String order, String asc){
		// 初始化参数
		schoolYear = getSchoolYear(schoolYear);
		String schoolYear_last = EduUtils.getLastSchoolYear9(schoolYear);
		final boolean isAsc = getIsAsc(asc);
		order = order==null ? "graduation_rate" : order;
		final String key = order;
		
		// query data
		String sql = "select x.*, dept.name_ name from ("
				+ " select case when t.major_id is not null then t.major_id else t2.major_id end id,"
				+ " t.graduation_rate, t.graduation_ranking_change,"
				+ " t2.employment_rate, t2.employment_ranking_change, '"+schoolYear+"' school_year"
//				+ " t.ranking graduation_ranking, t2.ranking employment_ranking,"
				+ " from (select t1.*,"
//				+ " case when t1.ranking is null then 0 else case when t2.ranking is null then t1.ranking"
				+ " case when t1.ranking is null or t2.ranking is null then null"
				+ " else t2.ranking-t1.ranking end "
//				+ " end "
				+ " graduation_ranking_change"
				+ " from T_MAJOR_GRADUATION t1 left join T_MAJOR_GRADUATION t2 on t1.major_id=t2.major_id"
				+ " and t2.school_year='"+schoolYear_last+"' where t1.school_year='"+schoolYear+"') t"
				+ " full join (select t1.*,"
//				+ " case when t1.ranking is null then 0 else case when t2.ranking is null then t1.ranking"
				+ " case when t1.ranking is null or t2.ranking is null then null"
				+ " else t2.ranking-t1.ranking end "
//				+ " end "
				+ " employment_ranking_change"
				+ " from T_MAJOR_EMPLOYMENT t1 left join T_MAJOR_EMPLOYMENT t2 on t1.major_id=t2.major_id"
				+ " and t2.school_year='"+schoolYear_last+"' where t1.school_year='"+schoolYear+"') t2"
				+ " on t.major_id = t2.major_id and t.school_year=t2.school_year"
				+ ")x , t_code_dept_teach dept where x.id = dept.id";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		// sort
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			Double d1 = null, d2 = null;
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int k = 0;
				d1 = MapUtils.getDouble(o1, key);
				d2 = MapUtils.getDouble(o2, key);
				if(d1!=null && (d2==null || d1>d2)){ k = 1; }
				else if(d1==null || (d2!=null && d1<d2)){ k = -1; }
				if(!isAsc){ k = -k; }
				return k;
			}
		});
		// 将空值转为 -
		for(Map<String, Object> map : list){
			map.put("graduation_rate", null2_(MapUtils.getString(map, "graduation_rate")));
			map.put("employment_rate", null2_(MapUtils.getString(map, "employment_rate")));
			// 前台对null统一处理，排名数据无需转换
//			map.put("graduation_ranking_change", null2_(MapUtils.getString(map, "graduation_ranking_change")));
//			map.put("employment_ranking_change", null2_(MapUtils.getString(map, "employment_ranking_change")));
		}
		list = (List<Map<String, Object>>) getPageList(list, page);
		// reMap
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", list);
		reMap.put("pagecount", page.getPagecount());
		reMap.put("sumcount", page.getSumcount());
		return reMap;
	}
	
	@Override
	public Map<String, Object> queryMajorSearchList(List<AdvancedParam> advancedParamList, Integer year, String score, 
			String failScale, String evaluateTeach, String by, String jy){
		year = year==null ? 3 : year;
		String schoolYear = getSchoolYear(null);
		int lastYear = Integer.valueOf(schoolYear.substring(0, 4));
		
		Double score_scale    = score==null ? null : Double.valueOf(score),
			   failScale_scale = failScale==null ? null : Double.valueOf(failScale),
			   evaluateTeach_scale = evaluateTeach==null ? null : Double.valueOf(evaluateTeach),
			   by_scale = by==null ? null : Double.valueOf(by),
			   jy_scale = jy==null ? null : Double.valueOf(jy);
		String key = "id";
		
		boolean isEveryYearSearched = true;
		List<List<Map<String, Object>>> list = new ArrayList<>();
		for( ; year>0; year--){
			String schoolYear_2 = lastYear+"-"+(lastYear+1);
			// 成绩最差的 10%
			String sql1 = "select major_id id, score_avg from T_MAJOR_SCORE t where t.school_year = '"+schoolYear_2+"'"
					+ " and t.course_attr_code is null order by t.score_avg";
			List<Map<String, Object>> list1 = baseDao.queryListInLowerKey(sql1);
			list1 = subListByScale(list1, score_scale);
			// 挂科率最高的 10%
			String sql2 = "select major_id id, fail_class_rate from T_MAJOR_FAIL_CLASS t where t.school_year = '"+schoolYear_2+"'"
					+ " and t.course_attr_code is null order by t.fail_class_rate desc";
			List<Map<String, Object>> list2 = baseDao.queryListInLowerKey(sql2);
			list2 = subListByScale(list2, failScale_scale);
			// 评教分最低的 10%
			String sql3 = "select major_id id, evaluate_teaching_score_avg from T_MAJOR_EVALUATE_TEACHING t"
					+ " where t.school_year = '"+schoolYear_2+"' and t.course_attr_code is null order by t.evaluate_teaching_score_avg";
			List<Map<String, Object>> list3 = baseDao.queryListInLowerKey(sql3);
			list3 = subListByScale(list3, evaluateTeach_scale);
			// 毕业率最低的 10%
			String sql4 = "select major_id id, graduation_rate from T_MAJOR_GRADUATION t where t.school_year = '"+schoolYear_2+"' order by t.graduation_rate";
			List<Map<String, Object>> list4 = baseDao.queryListInLowerKey(sql4);
			list4 = subListByScale(list4, by_scale);
			// 就业率最低的 10%
			String sql5 = "select major_id id, employment_rate from T_MAJOR_EMPLOYMENT t where t.school_year = '"+schoolYear_2+"' order by t.employment_rate";
			List<Map<String, Object>> list5 = baseDao.queryListInLowerKey(sql5);
			list5 = subListByScale(list5, jy_scale);
			
			// 获取它们的交集
			List<Map<String, Object>> list_one = getIntersectionByKey(list1, list2, key);
			list_one = getIntersectionByKey(list_one, list3, key);
			list_one = getIntersectionByKey(list_one, list4, key);
			list_one = getIntersectionByKey(list_one, list5, key);
			if(list_one.isEmpty()){
				isEveryYearSearched = false;
				break;
			}
			list.add(list_one);
			lastYear--;
		}
		if(!isEveryYearSearched)
			list.clear();
		List<Map<String, Object>> reList = new ArrayList<>();
		switch (list.size()) {
		case 0:
			break;
		case 1:
			reList = list.get(0);
			break;
		default:
			reList = getIntersectionAndAvgByKey(list, key);
			break;
		}
		for(Map<String, Object> map : reList){
			map.put("name", businessService.getDeptNameById(MapUtils.getString(map, key)));
		}
		// return
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", reList);
		return reMap;
	}
	
	/**
	 * 按百分比截取list
	 * @param list
	 * @param scale 百分比；eg：10，10%，null返回全部
	 * @return List
	 */
	private List<Map<String, Object>> subListByScale(List<Map<String, Object>> list, Double scale){
		if(scale == null)
			return list;
		int size = list.size();
		double len = size * scale / 100;
		int size2 = (int) Math.floor(len);
		return list.subList(0, size2);
	}
	
	/**
	 * 获取list交集
	 * @param list1
	 * @param list2
	 * @param key 比对键值
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getIntersectionByKey(List<Map<String, Object>> list1, List<Map<String, Object>> list2, String key){
		List<Map<String, Object>> list = new ArrayList<>();
		for(Map<String, Object> map1 : list1){
			String val1 = MapUtils.getString(map1, key);
			for(Map<String, Object> map2 : list2){
				String val2 = MapUtils.getString(map2, key);
				if(val1.equals(val2)){
					for(Map.Entry<String, Object> entry : map2.entrySet()){
						map1.put(entry.getKey(), entry.getValue());
					}
					list.add(map1);
				}
			}
		}
		return list;
	}
	
	/**
	 * 取List中各个list都存在的key交集
	 * @param list
	 * @param key
	 * @return List<Map<String,Object>>
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getIntersectionAndAvgByKey(List<List<Map<String, Object>>> list, String key){
		List<Map<String, Object>> reList = new ArrayList<>();
		// 拿第一个数据取交集
		List<Map<String, Object>> list1 = list.get(0);
		for(Map<String, Object> map : list1){
			String val1 = MapUtils.getString(map, key);
			boolean isEqual = true;
			for(int i=1,len=list1.size(); i<len; i++){
				String val2 = MapUtils.getString(list1.get(i), key);
				if(!val1.equals(val2)){
					isEqual = false;
					break;
				}
			}
			// 找到相同数据 map1 val1
			if(isEqual){
				for(List<Map<String, Object>> li : list){
					// 每一个li中都有一个相同专业map
					for(Map<String, Object> zyMap : li){
						if(val1.equals(MapUtils.getString(zyMap, key))){
							for(Map.Entry<String, Object> entry : zyMap.entrySet()){
								String key_ = entry.getKey(),
									   key_append = key_+"_list";
								// 处理其他key
								if(!key.equals(key_)){
									List<Double> valList = new ArrayList<>();
									if(map.containsKey(key_append)){
										valList = (List<Double>) map.get(key_append);
									}else{
										map.put(key_append, valList);
									}
									valList.add(Double.valueOf(String.valueOf(entry.getValue())));
									// 获取平均值
									map.put(key_, MathUtils.getAvgValueExcludeNull(valList));
								}
							}
							break;
						}
					}
				}
				// 处理完成
				reList.add(map);
			}
		}
		return reList;
	}


	@Override
	public Map<String, Object> queryMajorScoreHis(List<AdvancedParam> advancedParamList, String id){
		String key_id = "id",
				key_attr_code = "code",
				key_val       = "value",
				key_ranking_val = "ranking_value";
		String sql = "select t.major_id "+key_id+", t.course_attr_code "+key_attr_code+", t.score_avg "+key_val+", t.ranking "+key_ranking_val+", t.school_year"
				+ " from t_major_score t where t.major_id = '"+id+"' and t.school_year is not null order by t.school_year";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return handlerListForOption(list);
	}
	
	@Override
	public Map<String, Object> queryMajorFailScaleHis(List<AdvancedParam> advancedParamList, String id){
		String key_id = "id",
				key_attr_code = "code",
				key_val       = "value",
				key_ranking_val = "ranking_value";
		String sql = "select t.major_id "+key_id+", t.course_attr_code "+key_attr_code+", t.fail_class_rate "+key_val+", t.ranking "+key_ranking_val+", t.school_year"
				+ " from T_MAJOR_FAIL_CLASS t where t.major_id = '"+id+"' and t.school_year is not null order by t.school_year";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return handlerListForOption(list);
	}

	@Override
	public Map<String, Object> queryMajorEvaluateTeachHis(List<AdvancedParam> advancedParamList, String id){
		String key_id = "id",
				key_attr_code = "code",
				key_val       = "value",
				key_ranking_val = "ranking_value";
		String sql = "select t.major_id "+key_id+", t.course_attr_code "+key_attr_code+", t.EVALUATE_TEACHING_SCORE_AVG "+key_val+", t.ranking "+key_ranking_val+", t.school_year"
				+ " from T_MAJOR_EVALUATE_TEACHING t where t.major_id = '"+id+"' and t.school_year is not null order by t.school_year";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return handlerListForOption(list);
	}
	
	@Override
	public Map<String, Object> queryMajorByHis(List<AdvancedParam> advancedParamList, String id){
		String key_id = "id",
				key_val       = "value",
				key_ranking_val = "ranking_value";
		String sql = "select t.major_id "+key_id+", t.GRADUATION_RATE "+key_val+", t.ranking "+key_ranking_val+", t.school_year"
				+ " from T_MAJOR_GRADUATION t where t.major_id = '"+id+"' and t.school_year is not null order by t.school_year";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return handlerListForOption(list);
	}
	
	@Override
	public Map<String, Object> queryMajorJyHis(List<AdvancedParam> advancedParamList, String id){
		String key_id = "id",
				key_val       = "value",
				key_ranking_val = "ranking_value";
		String sql = "select t.major_id "+key_id+", t.EMPLOYMENT_RATE "+key_val+", t.ranking "+key_ranking_val+", t.school_year"
				+ " from T_MAJOR_EMPLOYMENT t where t.major_id = '"+id+"' and t.school_year is not null order by t.school_year";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return handlerListForOption(list);
	}
	
	/**
	 * 查看一个专业历年的数据情况
	 * @param list
	 * @return Map<String,Object>
	 */
	private Map<String, Object> handlerListForOption(List<Map<String, Object>> list) {
		String key_id = "id",
				key_attr_code = "code",
				key_score_val   = "value",
				key_ranking_val = "ranking_value",
				key_schooYear = "school_year";
		List<Map<String, Object>> attrList = queryBzdmCourseAttrCode();
		/**
		 * 历年数据
		 * 最小年、最大年
		 * 平均成绩、基础课成绩... 平均排名
		 */
		Map<String, Map<String, Object>> yearMap = new HashMap<>();
		List<Map<String, Object>> yearList = new ArrayList<>();
		Map<String, Object> oneYearMap = null;
		Integer year_min = null, year_max = null;
		for(Map<String, Object> map : list){
			int year = EduUtils.getSchoolYear4(MapUtils.getString(map, key_schooYear));
			if(year_min==null || year_min>year) year_min = year;
			if(year_max==null || year_max<year) year_max = year;
		}
		// 每一年数据 以年为key
		for( ; year_min<=year_max; year_min++){
			String schoolYear = EduUtils.getSchoolYear9(year_min);
			for(Map<String, Object> map : list){
				if(schoolYear.equals(MapUtils.getString(map, key_schooYear))){
					// 每一年的不同专业进行组合
					String majorId = MapUtils.getString(map, key_id),
						   key_map = schoolYear;
					if(yearMap.containsKey(key_map)){
						oneYearMap = yearMap.get(key_map);
					}else{
						oneYearMap = new HashMap<>();
						oneYearMap.put(key_id, majorId); // id
						oneYearMap.put(key_ranking_val, MapUtils.getInteger(map, key_ranking_val)); // 排名数据
						oneYearMap.put("name", MapUtils.getString(map, key_schooYear)); // name
						oneYearMap.put("code", MapUtils.getString(map, key_schooYear)); // name
						yearMap.put(key_map, oneYearMap);
						yearList.add(oneYearMap);
					}
					String course_attr_code = MapUtils.getString(map, key_attr_code);
					// code为空，是平均成绩
					if(course_attr_code==null || "".equals(course_attr_code)){
						course_attr_code = key_score_val;
						oneYearMap.put(course_attr_code, MapUtils.getDouble(map, key_score_val));
					}else{
						for(Map<String, Object> attrMap : attrList){
							String code_2 = MapUtils.getString(attrMap, "id");
							if(course_attr_code.equals(code_2)){
								oneYearMap.put(course_attr_code, MapUtils.getString(map, key_score_val)); // 只put value，不处理排名
								break;
							}
						}
					}
				}
			}
		}
		// return
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", yearList);
		reMap.put("bzdm", attrList);
		return reMap;
	}

	/**
	 * 将null、""转为 -
	 * @param value
	 * @return String
	 */
	private String null2_(String value){
		return (null==value||"".equals(value)) ? "-" : value;
	}
}