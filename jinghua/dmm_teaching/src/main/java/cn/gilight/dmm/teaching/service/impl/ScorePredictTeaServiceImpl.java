package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import cn.gilight.dmm.teaching.dao.ScorePredictTeaDao;
import cn.gilight.dmm.teaching.service.ScorePredictTeaService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.ContextHolderUtils;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.common.UserUtil;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;

@Service("scorePredictTeaService")
public class ScorePredictTeaServiceImpl implements ScorePredictTeaService {
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private ScorePredictTeaDao scorePredictTeaDao;
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"scorePredictTea";
	
	private static final String Type_Edu = "edu";
	private static final String Type_SchoolYear = "schoolyear";
	private static final String Type_TermCode = "termcode";
	private static final String Type_Course = "course";
	private static final String Type_Class = "class";
	private static final String Type_Th = "th";
	private static final String Table = Constant.Table_T_STU_SCORE_PREDICT_BEH;
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	/**
	 * 获取数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String pid){
		return businessService.getDeptDataList(ShiroTag, pid);
	}
	
	@Override
	public Map<String,Object> getDate(){
		return getDate(Table);	
	}
	@Override
	public Map<String,Object> getDate(String table){
		Map<String,Object> map = DevUtils.MAP();
		map.put("date", scorePredictTeaDao.queryDate(table));
		return map;	
	}
	@Override
	public List<Map<String,Object>> getEduList(){
		List<String> deptList = getDeptDataList();
		String stusql = scorePredictTeaDao.queryStuSql(getUsrName(),deptList);
		return  businessService.queryBzdmStuEducationList(stusql);
	}
	@Override
	public List<Map<String,Object>> getTimeList(){
		return getTimeList(Table);
	}
	@Override
	public List<Map<String,Object>> getTimeList(String table){
		List<Map<String,Object>> list = scorePredictTeaDao.queryTimeList(getUsrName(),table);
		int i=0; 
		for( Map<String,Object> map : list){
			 Map<String,Object>  map1 = new HashMap<String, Object>();
			 String xx = MapUtils.getString(map, "sb");
			 if(i == 0){
				 map.put("order", 0);
			 }else{
				 map1 = list.get(i-1);
				 String xx1 = MapUtils.getString(map1, "sb");
				 if(xx1.equals(xx)){
					 map.put("order", i);
				 }else{
					 map.put("order", i+1);
				 }
			 }
			 i++;
		 }
		return list;
	}
	@Override
	public List<Map<String,Object>> getThList(){
		List<Map<String,Object>> list = Constant.getScoreGroup(),
				                 result = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : list){
			Map<String,Object> temp = new HashMap<String, Object>();
			temp.put("id", MapUtils.getString(map, "order"));
			temp.put("code", MapUtils.getString(map, "id"));
			temp.put("mc", MapUtils.getString(map, "mc1"));
			result.add(temp);
		}
		compareCount(result, "id", true);
		return result;
	}
	@Override
	public List<Map<String,Object>> getFthList(){
		List<Map<String,Object>> list = Constant.getScoreGroup(),
				                 result = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> map : list){
			Map<String,Object> temp = new HashMap<String, Object>(),
					           temp1= new HashMap<String, Object>();
			temp.put("id", MapUtils.getIntValue(map, "order"));
			temp1.put("id", MapUtils.getIntValue(map, "order")+1);
			result.add(temp);result.add(temp1);
		}
		compareCount(result, "id", false);
		return result;
	}
	@Override
	public List<Map<String,Object>> getCourseList(List<AdvancedParam> advancedList,String schoolYear,String termCode,String edu){
		Map<String,Object> params = getParamByParam(advancedList, schoolYear, termCode,edu);
		schoolYear = MapUtils.getString(params, "schoolyear");
		termCode = MapUtils.getString(params, "termcode");
		return scorePredictTeaDao.queryCourseList(schoolYear, termCode, getUsrName());
	}
	
	@SuppressWarnings("unchecked")
	@Override
    public Map<String,Object> getTeachClassAndStuCount(String schoolYear,String termCode,String courseId,String edu,List<AdvancedParam> advancedList){
    	Map<String,Object> params = getParamByParam(advancedList, schoolYear, termCode,edu);
		schoolYear = MapUtils.getString(params, "schoolyear");
		termCode = MapUtils.getString(params, "termcode");
		int year = MapUtils.getIntValue(params, "year");
		List<String> deptList = getDeptDataList();
		List<AdvancedParam> advancedParamList = (List<AdvancedParam>) MapUtils.getObject(params, "advancedParamList");
		if( courseId == null){
			List<Map<String, Object>>list = scorePredictTeaDao.queryCourseList(schoolYear, termCode, getUsrName());
			if(list != null && !list.isEmpty() ){
				courseId = MapUtils.getString(list.get(0), "id");
			}
		}
		return scorePredictTeaDao.queryTeachClassAndStuCount(schoolYear, termCode, getUsrName(),courseId,year,deptList,advancedParamList);
    }
	
	@Override
	public List<Map<String,Object>> getCourseNatureList(String schoolYear,String termCode,String courseId,String edu,List<AdvancedParam> advancedList){
		Map<String,Object> params = getParamByParam(advancedList, schoolYear, termCode,edu);
		schoolYear = MapUtils.getString(params, "schoolyear");
		termCode = MapUtils.getString(params, "termcode");
		if( courseId == null){
			List<Map<String, Object>>list = scorePredictTeaDao.queryCourseList(schoolYear, termCode, getUsrName());
			if(list != null && !list.isEmpty() ){
				courseId = MapUtils.getString(list.get(0), "id");
			}
		}
		return scorePredictTeaDao.queryCourseNatureList(schoolYear, termCode, getUsrName(), courseId);
	}
	@Override
	public Map<String,Object> getClassScoreGk(String schoolYear,String termCode,String courseId,String nature,String edu,List<AdvancedParam> advancedList){
		return getClassScoreGk(schoolYear, termCode, courseId, nature, edu, advancedList,Table);	
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getClassScoreGk(String schoolYear,String termCode,String courseId,String nature,String edu,List<AdvancedParam> advancedList,String table){
		Map<String,Object> params = getParamByParam(advancedList, schoolYear, termCode,edu);
		schoolYear = MapUtils.getString(params, "schoolyear");
		termCode = MapUtils.getString(params, "termcode");
		int year = MapUtils.getIntValue(params, "year");
		List<String> deptList1 = getDeptDataList();
		List<Double> allList = new ArrayList<Double>();
		List<AdvancedParam> advancedParamList = (List<AdvancedParam>) MapUtils.getObject(params, "advancedParamList");
		if( courseId == null){
			List<Map<String, Object>>list = scorePredictTeaDao.queryCourseList(schoolYear, termCode, getUsrName());
			if(list != null && !list.isEmpty() ){
				courseId = MapUtils.getString(list.get(0), "id");
			}
		}
		if( nature == null){
			List<Map<String, Object>>list1 = scorePredictTeaDao.queryCourseNatureList(schoolYear, termCode, getUsrName(), courseId);
			if(list1 != null && !list1.isEmpty() ){
				nature = MapUtils.getString(list1.get(0), "id");
			}
		}
		String allId= "";int i=0;
		List<Map<String,Object>> classList = scorePredictTeaDao.queryClassList(schoolYear, termCode, getUsrName(), courseId, nature,year,table,deptList1,advancedParamList);
	    List<Map<String,Object>> teachList = new ArrayList<Map<String,Object>>();
		for(Map<String,Object> temp : classList){
			Map<String,Object> temp1 = new HashMap<String, Object>();
			String teachId = MapUtils.getString(temp,"id");
			String mc = MapUtils.getString(temp,"mc");
			temp1.put("id", teachId);
			temp1.put("mc", mc);
			if(!teachList.contains(temp1)){
				teachList.add(temp1);
			}
		}
		for(Map<String,Object> temp2 : teachList){
			String teachId1 = MapUtils.getString(temp2, "id");
			List<Double> valueList =  new ArrayList<Double>();
			for(Map<String,Object> temp3 : classList){
				String teachId2 = MapUtils.getString(temp3, "id");
				Double score = MapUtils.getDouble(temp3, "score");
				if(teachId2.equals(teachId1) && score != null){
					valueList.add(score);
				}
			}
			temp2.put("valuelist", valueList);
		}
		for(Map<String,Object> temp4 : teachList){
			String  teachId3 = MapUtils.getString(temp4, "id");
			List<Double> valueList = (List<Double>) MapUtils.getObject(temp4, "valuelist");
			int count = valueList.size();Double avg = MathUtils.getAvgValue(valueList);
			temp4.put("avg", avg); temp4.put("count", count);
			List<Map<String,Object>> temp5 = getValueByValueList(valueList);
			compareCount(temp5, "order", true);
			temp4.put("list",temp5);
			allList.addAll(valueList);
		    if (i==0){allId = teachId3;}else{allId+="+"+teachId3;};
		    i++;
		}
		if(teachList.size()>1){
			List<Map<String,Object>> allL =  getValueByValueList(allList);
			Map<String,Object> allMap = new HashMap<String, Object>();
			allMap.put("count", allList.size());allMap.put("avg", MathUtils.getAvgValue(allList));
			allMap.put("list", allL);allMap.put("mc", "合计");allMap.put("id", allId);
			teachList.add(allMap);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", teachList);
		return map;	
	}
	@Override
	public Map<String,Object> getChartData(String schoolYear,String termCode,String courseId,String edu,List<AdvancedParam> advancedList){
		return getChartData(schoolYear, termCode, courseId, edu, advancedList,Table);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getChartData(String schoolYear,String termCode,String courseId,String edu,List<AdvancedParam> advancedList,String table){
		Map<String,Object> params = getParamByParam(advancedList, schoolYear, termCode,edu);
		schoolYear = MapUtils.getString(params, "schoolyear");
		termCode = MapUtils.getString(params, "termcode");
		int year = MapUtils.getIntValue(params, "year");
		List<String> deptList = getDeptDataList();
		List<AdvancedParam> advancedParamList = (List<AdvancedParam>) MapUtils.getObject(params, "advancedParamList");
		if( courseId == null){
			List<Map<String, Object>>list = scorePredictTeaDao.queryCourseList(schoolYear, termCode, getUsrName());
			if(list != null && !list.isEmpty() ){
				courseId = MapUtils.getString(list.get(0), "id");
			}
		}
		List<Map<String,Object>> scoreList = Constant.getScoreGroup(),
				                 result = new ArrayList<Map<String,Object>>();
		int has = 0;
		for(Map<String,Object> temp : scoreList){ 
			String id = MapUtils.getString(temp, "id");
			String mc = MapUtils.getString(temp, "mc1");
			String[] ary = id.split(",");
			String start = ary[0].equals("null") || ary[0] == null?
					"":ary[0],
					end = ary[1].equals("null") || ary[1] == null?
					"":ary[1];
			Map<String,Object> natureMap = scorePredictTeaDao.queryNatureList(schoolYear, termCode, getUsrName(), courseId, start, end,year,deptList,advancedParamList,table);
		    List<Map<String,Object>> list1 = (List<Map<String, Object>>) natureMap.get("list");
		    has = MapUtils.getIntValue(natureMap,"has");
		    for(Map<String,Object> temp1 : list1){
		    	temp1.put("field", mc);
		    	temp1.put("code", start.equals("")?0:start);
		    }
		    if(!list1.isEmpty()){result.addAll(list1);}
		}
		Map<String, Object> typeMap = new HashMap<String, Object>();
	    if (has ==1 ){typeMap = shiftList(result);}
	    Map<String, Object> map = DevUtils.MAP();
		map.put("list",has ==1 ? typeMap : result);
		map.put("has",has);
		return map;
	}
	@Override
	public Map<String,Object> getSameData(String schoolYear,String courseId,String nature,String edu,List<AdvancedParam> advancedList){
		return getSameData(schoolYear, courseId, nature, edu, advancedList,Table);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getSameData(String schoolYear,String courseId,String nature,String edu,List<AdvancedParam> advancedList,String table){
		Map<String,Object> params = getParamByParam(advancedList, schoolYear, null,edu);
		schoolYear = MapUtils.getString(params, "schoolyear");
		List<String> deptList = getDeptDataList();
		List<AdvancedParam> advancedParamList = (List<AdvancedParam>) MapUtils.getObject(params, "advancedParamList");
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		if( courseId == null){
			List<Map<String, Object>>list = scorePredictTeaDao.queryCourseList(schoolYear, null, getUsrName());
			if(list != null && !list.isEmpty() ){
				courseId = MapUtils.getString(list.get(0), "id");
			}
		}
		if( nature == null){
			List<Map<String, Object>>list1 = scorePredictTeaDao.queryCourseNatureList(schoolYear, null, getUsrName(), courseId);
			if(list1 != null && !list1.isEmpty() ){
				nature = MapUtils.getString(list1.get(0), "id");
			}
		}
	    List<Map<String,Object>> dateList = getDateList(schoolYear,4,true);
	    for(int i=0;i<dateList.size();i++){
	    	List<Map<String, Object>> temp = new ArrayList<Map<String,Object>>(),
	    			                  temp1= new ArrayList<Map<String,Object>>();
	    	Map<String,Object>        temp2= new HashMap<String, Object>(),
	    			                  temp3= new HashMap<String, Object>();
	    	String year = MapUtils.getString(dateList.get(i), "id");
	    	String mc = MapUtils.getString(dateList.get(i), "mc");
	    	int sYear = MapUtils.getIntValue(dateList.get(i), "code");
	    	List<Double> valueList = new ArrayList<Double>(),
	    			     valueList1= new ArrayList<Double>();
	    	if(i == 0){
	    		valueList = scorePredictTeaDao.queryClassScore(year, getUsrName(), courseId, nature, null,sYear,deptList,advancedParamList,table);
	    		valueList1 = scorePredictTeaDao.queryClassScore(year, getUsrName(), courseId, nature, "notme",sYear,deptList,advancedParamList,table);
	    		temp = getValueByValueList(valueList);temp1 = getValueByValueList(valueList1);
	    		compareCount(temp, "order", true);compareCount(temp1, "order", true);
	    		temp2.put("count", valueList.size());temp2.put("avg", MathUtils.getAvgValue(valueList));
	    		temp2.put("mc", mc+"我带的班级");temp3.put("mc", mc+"其它班级");
	    		temp3.put("count", valueList1.size());temp3.put("avg", MathUtils.getAvgValue(valueList1));
	    	    temp2.put("list", temp); temp3.put("list", temp1);
	    		result.add(temp2);result.add(temp3);
	    	}else{
	    		valueList = scorePredictTeaDao.queryClassScore(year, getUsrName(), courseId, nature, "all",sYear,deptList,advancedParamList,table);
	    		temp = getValueByValueList(valueList);
	    		compareCount(temp, "order", true);
	    		temp2.put("count", valueList.size());temp2.put("avg", MathUtils.getAvgValue(valueList));
	    		temp2.put("mc", mc+"所有班级");
	    		temp2.put("list",temp);
	    		result.add(temp2);
	    	}
	    }
	    Map<String, Object> map = DevUtils.MAP();
		map.put("list", result);
		return map;
	}
	@Override
	public Map<String,Object> getPrecisionList(String schoolYear,String courseId,String nature,String edu,List<AdvancedParam> advancedList){
		return getPrecisionList(schoolYear, courseId, nature, edu, advancedList,Table);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getPrecisionList(String schoolYear,String courseId,String nature,String edu,List<AdvancedParam> advancedList,String table){
		Map<String,Object> params = getParamByParam(advancedList, schoolYear, null,edu);
		schoolYear = MapUtils.getString(params, "schoolyear");
		List<String> deptList = getDeptDataList();
		List<AdvancedParam> advancedParamList = (List<AdvancedParam>) MapUtils.getObject(params, "advancedParamList");
		if( courseId == null){
			List<Map<String, Object>>list = scorePredictTeaDao.queryCourseList(schoolYear, null, getUsrName());
			if(list != null && !list.isEmpty() ){
				courseId = MapUtils.getString(list.get(0), "id");
			}
		}
		if( nature == null){
			List<Map<String, Object>>list1 = scorePredictTeaDao.queryCourseNatureList(schoolYear, null, getUsrName(), courseId);
			if(list1 != null && !list1.isEmpty() ){
				nature = MapUtils.getString(list1.get(0), "id");
			}
		}
		List<Map<String,Object>> dateList = getDateList(schoolYear, 5,false),
				                 result = new ArrayList<Map<String,Object>>();
		List<String>             thList = new ArrayList<String>();
		Map<String,Object> meMap = new HashMap<String, Object>(),
				           notmeMap = new HashMap<String, Object>();
		String[] yearNameAry = {"one","two","three","four","five"};
		meMap.put("name", "我带的班级");notmeMap.put("name", "其它教师带的班级");
//		meMap.put("nature", "我");notmeMap.put("nature", "我");
		thList.add(" ");thList.add("课程性质");
		for(int i=0;i<dateList.size();i++){
			String name =  MapUtils.getString(dateList.get(i), "mc");
			String id = MapUtils.getString(dateList.get(i), "id");
			int year = MapUtils.getIntValue(dateList.get(i), "code");
			Double meScale = scorePredictTeaDao.queryPrecision(id, null, courseId, getUsrName(), nature, true,year,deptList,advancedParamList,table); 
			Double notmeScale = scorePredictTeaDao.queryPrecision(id, null, courseId, getUsrName(), nature, false,year,deptList,advancedParamList,table); 
			meMap.put(yearNameAry[i], meScale == null?"--":meScale);notmeMap.put(yearNameAry[i], notmeScale==null?"--":notmeScale);
			thList.add(name);
		}
		result.add(meMap);result.add(notmeMap);
	    Map<String, Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("th", thList);
		return map;
	}
	@Override
	public Map<String,Object> getNowPrecision(String schoolYear,String termCode,String courseId,String nature,String edu,List<AdvancedParam> advancedList){
		return getNowPrecision(schoolYear, termCode, courseId, nature, edu, advancedList,Table);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getNowPrecision(String schoolYear,String termCode,String courseId,String nature,String edu,List<AdvancedParam> advancedList,String table){
		Map<String,Object> params = getParamByParam(advancedList, schoolYear, termCode,edu);
		schoolYear = MapUtils.getString(params, "schoolyear");
		termCode = MapUtils.getString(params, "termcode");
		int sYear = MapUtils.getIntValue(params, "year");
		List<String> deptList = getDeptDataList();
		List<AdvancedParam> advancedParamList = (List<AdvancedParam>) MapUtils.getObject(params, "advancedParamList");
		if( courseId == null){
			List<Map<String, Object>>list = scorePredictTeaDao.queryCourseList(schoolYear, termCode, getUsrName());
			if(list != null && !list.isEmpty() ){
				courseId = MapUtils.getString(list.get(0), "id");
			}
		}
		if( nature == null){
			List<Map<String, Object>>list1 = scorePredictTeaDao.queryCourseNatureList(schoolYear, termCode, getUsrName(), courseId);
			if(list1 != null && !list1.isEmpty() ){
				nature = MapUtils.getString(list1.get(0), "id");
			}
		}
		String name = "1";
		Double now = scorePredictTeaDao.queryPrecision(schoolYear, null, courseId, getUsrName(), nature, true,sYear,deptList,advancedParamList,table);
		if (now == null){
			int year = Integer.parseInt(schoolYear.substring(0,4))-1;
			now = scorePredictTeaDao.queryPrecision(String.valueOf(year)+"-"+String.valueOf(year+1), null, courseId, getUsrName(), nature, true,year,deptList,advancedParamList,table);
			name = "0";
		}
		Map<String, Object> map = DevUtils.MAP();
	    map.put("scale", now == null?"--":now);
	    map.put("isnow", name);
		return map;
	}
	@Override
	public Map<String,Object> getStuDetail(List<AdvancedParam> advancedParamList,Page page, 
			Map<String, Object> keyValue, List<String> fields){
		String sql = getStuDetailSql(advancedParamList, keyValue, fields);
		Map<String,Object> map =baseDao.createPageQueryInLowerKey(sql, page);
		return map;
	}
	@Override
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList, 
			Map<String, Object> keyValue, List<String> fields){
		String sql = getStuDetailSql(advancedParamList, keyValue, fields);
		List<Map<String,Object>> list =baseDao.queryListInLowerKey(sql);
		return list;
	}
	/**
	 * 根据参数优化参数
	 * @param advancedList 高级查询参数
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return Map<String,Object>
	 */
	private Map<String,Object> getParamByParam(List<AdvancedParam> advancedParamList,String schoolYear,String termCode,String edu){
		String[] str = EduUtils.getSchoolYearTerm(new Date());
		schoolYear = schoolYear == null ? str[0]:schoolYear;
		termCode = termCode == null?str[1]:termCode;
		int year = Integer.parseInt(schoolYear.substring(0,4));
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		advancedParamList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		List<AdvancedParam> stuAdvancedList      = AdvancedUtil.getAdvancedParamStu(advancedParamList),
				businessAdvancedList = AdvancedUtil.getAdvancedParamBusiness(advancedParamList);
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("advancedParamList", advancedParamList);
		map.put("stuAdvancedList", stuAdvancedList);
		map.put("businessAdvancedList", businessAdvancedList);
		map.put("schoolyear", schoolYear);
		map.put("termcode", termCode);
		map.put("year", year);
		return map;
	}
	/**
	 * 获取登录用户名
	 * @return String
	 */
	private String getUsrName(){
		String username = UserUtil.getCasLoginName();  
		return username;
	}
	/**
	 * 根据valueList获取计算结果
	 * @param valueList 
	 * @return
	 */
	private List<Map<String,Object>> getValueByValueList(List<Double> valueList){
       int count = valueList.size();
	   List<Map<String,Object>> group = Constant.getScoreGroup(),
			       result  = new ArrayList<Map<String,Object>>();
	   for(Map<String,Object> temp : group ){
	    String id = MapUtils.getString(temp, "id");
	    int order = MapUtils.getIntValue(temp, "order");
	    Map<String,Object> countMap = new HashMap<String, Object>(),
	    		           scaleMap = new HashMap<String, Object>(); 
	    String[] ary = id.split(",");
		String start = ary[0].equals("null") || ary[0] == null?
				"":ary[0],
				end = ary[1].equals("null") || ary[1] == null?
				"":ary[1];
	    List<Double> list = new ArrayList<Double>();
	    if(start.equals("") && !end.equals("")){
		        for(Double db : valueList ){
		        	if(db != null && !db.equals("null") && db < Integer.parseInt(end)){
		        		list.add(db);
		        	}
		        }
		    }else if(!start.equals("") && !end.equals("")){
		    	  for(Double db1 : valueList ){
			        	if(db1 != null  && !db1.equals("null") &&  db1 >= Integer.parseInt(start) && db1 < Integer.parseInt(end)){
			        		list.add(db1);
			        	}
			        }
		    }else if(!start.equals("") && end.equals("")){
		    	  for(Double db2 : valueList ){
			        	if(db2 != null && !db2.equals("null") && db2 >= Integer.parseInt(start)){
			        		list.add(db2);
			        	}
			        }
		    }
	       countMap.put("value", list.size()); countMap.put("order", order);
	       countMap.put("code", id);
	       scaleMap.put("value", MathUtils.get2Point(MathUtils.getDivisionResult(list.size(), count, 4)*100));
	       scaleMap.put("order", order+1);scaleMap.put("code", id);
	       result.add(countMap); result.add(scaleMap);
	   }
	   return result;
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
				Double count1 = MapUtils.getDouble(o1, compareField), count2 = MapUtils
						.getDouble(o2, compareField); int flag = 0, pare = asc
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
	private Map<String,Object> shiftList(List<Map<String,Object>> result){
		Map<String, Object> typeMap = new HashMap<>();
		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
		List<String> legend_ary = new ArrayList<>(),
					 value_ary  = new ArrayList<>();
		List<String> field_ary  = new ArrayList<>();
		for(Map<String, Object> map2 :result){
			String legend = MapUtils.getString(map2, "name");
			String value = MapUtils.getString(map2, "name");
			String field = MapUtils.getString(map2, "field");
			if (!legend_ary.contains(legend)){
			legend_ary.add(legend);
			}
			if (!value_ary.contains(value)){
			value_ary.add(value);
			}
			if (!field_ary.contains(field)){
				field_ary.add(field);
				}
			}
		String field = null;
		for (String k : field_ary){
			Map<String, Object> map3 = new HashMap<>();
			map3.put("name", k);
			for (int l =0;l<result.size();l++){
				Map<String, Object> map4 = result.get(l);
				field = MapUtils.getString(map4, "field");
		      if (field != null && field.equals(k)){
		    	  map3.put(MapUtils.getString(map4, "name"), MapUtils.getInteger(map4, "value"));
//		    	  map3.put("code", MapUtils.getString(map4, "id"));
		      }
			}
			list3.add(map3);
		}
		typeMap.put("legend_ary", legend_ary);
		typeMap.put("value_ary", value_ary);
		typeMap.put("data", list3);
		return typeMap;
	}
	/**
	 * 根据学年获取最近几个学年的学年数据集合
	 * @param schoolYear 学年 eg:2016-2017
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> getDateList(String schoolYear,int length,Boolean asc){
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		int year = Integer.parseInt(schoolYear.substring(0,4));
		if(asc){
		for(int i= year;i>year-length;i--){
			Map<String, Object> map = new HashMap<String, Object>();
			String name = String.valueOf(i)+"-"+String.valueOf(i+1);
			map.put("id", name);
			map.put("mc", name+"学年");
			map.put("code", i);
			result.add(map);
		}
		}else{
			for(int i= year-length+1;i<year+1;i++){
				Map<String, Object> map = new HashMap<String, Object>();
				String name = String.valueOf(i)+"-"+String.valueOf(i+1);
				map.put("id", name);
				map.put("mc", name+"学年");
				map.put("code", i);
				result.add(map);
			}
		}
		return result;
	}
	/**
	 * 根据学年学期获取上一学年学期
	 * @param schoolYear 学年
	 * @param termCode 学期
	 * @return Map<String,Object>
	 */
	private Map<String,Object> getProXnXq(String schoolYear,String termCode){
		int year = Integer.parseInt(schoolYear.substring(0, 4));
		int term = Integer.parseInt(termCode);
		if (term == Integer.parseInt(Globals.TERM_FIRST)){
			schoolYear = String.valueOf(year-1)+"-"+String.valueOf(year);
			year = year-1;
			termCode = Globals.TERM_SECOND;
		}else{
			termCode = Globals.TERM_FIRST;
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("schoolyear", schoolYear);map.put("term", termCode);
		map.put("year", year);
		return map;
	}
	private String getStuDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields){
		return getStuDetailSql(advancedParamList, keyValue, fields,Table);

	}
	private String getStuDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields,String table){
		List<String> deptList = getDeptDataList(); // 权限
		Integer schoolyear = null;String schoolYear = "",termCode = "",classStu = "",
				course="",ycSql="",th="";
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				AdvancedUtil.add(stuAdvancedList, getDetailAdp(entry.getKey(), String.valueOf(entry.getValue())));
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				switch (type) {
				case Type_SchoolYear:
					schoolYear =  code;
					schoolyear =  Integer.parseInt(schoolYear.substring(0,4));
					break;
				case Type_TermCode:
					termCode = code;
					break;
				case Type_Course:
					course = code;
					break;
				case Type_Class: 
					String[] ary = code.split("[+]");
					String yy = "";
					int zz = 0;
					for(String xx : ary){
						xx = businessDao.formatInSql(xx);
						if(zz == 0){
							yy=xx;
						}else{
							yy +=","+xx;
						}
						zz++;
					}
					classStu = "select teachstu.* from t_class_teaching_stu teachstu where teachstu.teach_class_id in ("+yy+") ";
					break;	
				case Type_Th:
					th = code;
					break;
				default:
					break;
				}
			}
		String stuSql = businessDao.getStuSql(schoolyear,deptList,stuAdvancedList);
		if(!"".equals(classStu)){
			stuSql = "select y.* from ("+stuSql+") y,("+classStu+")z where y.no_ = z.stu_id ";
		}
		ycSql = scorePredictTeaDao.getYcSql(schoolYear, termCode, course, stuSql, th,table);
		String stuDetailSql = "select a.*,b.score from ("+businessDao.getStuDetailSql(stuSql)+") a,("+ycSql+") b where a.no = b.no_";
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") order by "+StringUtils.join(fields, ",")+"";
		}
		return stuDetailSql;

	}
	private AdvancedParam getDetailAdp(String type, String value){
		AdvancedParam adp = null;
		if(type != null){
			String group = AdvancedUtil.Group_Stu, code = null;
			switch (type) {
			case Type_Edu: 
				group = AdvancedUtil.Group_Stu;
				code = AdvancedUtil.Stu_EDU_ID;
				break;
			default:
				break;
			}
			adp = new AdvancedParam(group, code, value);
		}
		return adp;
	}
}
