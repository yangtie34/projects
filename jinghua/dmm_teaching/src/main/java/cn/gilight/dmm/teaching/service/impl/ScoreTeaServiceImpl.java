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
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.GetCachePermiss;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.ScorePredictTeaDao;
import cn.gilight.dmm.teaching.service.ScoreTeaService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.UserUtil;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("scoreTeaService")
public class ScoreTeaServiceImpl implements ScoreTeaService{

	@Resource
	private ScorePredictTeaDao scorePredictTeaDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 成绩分析(任课教师) shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"scoreTea";
	
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	@Override
	public List<Map<String,Object>> getEduList(){
		List<String> deptList = getDeptDataList();
		String stusql = scorePredictTeaDao.queryStuSql(getUsrName(),deptList);
		return  businessService.queryBzdmStuEducationList(stusql);
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
	public List<Map<String,Object>> getTimeList(){
		List<Map<String,Object>> list = scorePredictTeaDao.queryTimeList(getUsrName());
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
	@SuppressWarnings("unchecked")
	public Map<String,Object> getClassScoreGk(String schoolYear,String termCode,String courseId,String nature,String edu,List<AdvancedParam> advancedList){
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
		Boolean isgd = getPermiss();
		List<Map<String,Object>> stuList = scorePredictTeaDao.getStuCount(schoolYear, termCode, getUsrName(), courseId, nature,year,deptList1,advancedParamList),
				                 stuList2 = scorePredictTeaDao.getStuCountTrue(schoolYear, termCode, getUsrName(), courseId, nature, year, deptList1, advancedList,isgd),
				                 stuList1= scorePredictTeaDao.getStuCountOther(schoolYear, termCode, getUsrName(), courseId, nature, year, deptList1, advancedList);
		List<Map<String,Object>> classList = scorePredictTeaDao.queryClassList(schoolYear, termCode, getUsrName(), courseId, nature,year,deptList1,advancedParamList);
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
		int allCount = 0,allScount=0,allXcount=0;
		for(Map<String,Object> temp4 : teachList){
			String  teachId3 = MapUtils.getString(temp4, "id");
			List<Double> valueList = (List<Double>) MapUtils.getObject(temp4, "valuelist");
			int count = 0;Double avg = MathUtils.getAvgValue(valueList);
			int xcount =0;
			for(Map<String,Object> yMap : stuList){
				String teachId4 = MapUtils.getString(yMap, "id");
				Integer value = MapUtils.getInteger(yMap, "value");
				if(teachId4.equals(teachId3)){
					count = value == null ? 0:value;
					allCount+= count;
				}
			}
			for(Map<String,Object> yyMap : stuList2){
				String teachId6 = MapUtils.getString(yyMap, "id");
				Integer value = MapUtils.getInteger(yyMap, "value");
				if(teachId6.equals(teachId3)){
					xcount = value == null ? 0:value;
					allXcount+= xcount;
				}
			}
			int scount = xcount;
			allScount+= scount;
			if(isgd){
				for(Map<String,Object> zMap : stuList1){
					String teachId5 = MapUtils.getString(zMap, "id");
					Integer qk = MapUtils.getInteger(zMap, "qk"),
							hk = MapUtils.getInteger(zMap, "hk"),
							wj = MapUtils.getInteger(zMap, "wj");
					if(teachId5.equals(teachId3)){
						qk = qk == null?0:qk;
						hk = hk == null?0:hk;
						wj = wj == null?0:wj;
						scount = qk+hk+wj+scount;
						allScount+= qk+hk+wj;
					}
				}
			}
			temp4.put("avg", avg==null?0:avg); temp4.put("count", isgd ? scount :count);temp4.put("scount", xcount);
			temp4.put("max",valueList.size()==0?null:MathUtils.getMaxValue(valueList));	temp4.put("min", valueList.size()==0?null:MathUtils.getMinValue(valueList));temp4.put("mid", MathUtils.getMiddleValue(valueList));
			temp4.put("mode", MathUtils.getModeValue(valueList));temp4.put("bzc", MathUtils.getStandardDeviation(valueList));
			temp4.put("ptz", MathUtils.getSkewnessValue(valueList));temp4.put("qfd", MathUtils.getDistinctionDegree(valueList, 100d));temp4.put("xd",MathUtils.getValidityValue(valueList, 100d));
			List<Map<String,Object>> temp5 = getValueByValueList(valueList,xcount);
			compareCount(temp5, "order", true);
			temp4.put("list",temp5);
			allList.addAll(valueList);
		    if (i==0){allId = teachId3;}else{allId+="+"+teachId3;};
		    i++;
		}
		if(teachList.size()>1){
			List<Map<String,Object>> allL =  getValueByValueList(allList,allXcount);
			Map<String,Object> allMap = new HashMap<String, Object>();
			allMap.put("count", isgd ? allScount :allCount);allMap.put("avg", MathUtils.getAvgValue(allList));allMap.put("scount", allXcount);
			allMap.put("max",allList.size()==0?null:MathUtils.getMaxValue(allList));	allMap.put("min", allList.size()==0?null:MathUtils.getMinValue(allList));allMap.put("mid", MathUtils.getMiddleValue(allList));
			allMap.put("mode", MathUtils.getModeValue(allList));allMap.put("bzc", MathUtils.getStandardDeviation(allList));
			allMap.put("ptz", MathUtils.getSkewnessValue(allList));allMap.put("qfd", MathUtils.getDistinctionDegree(allList, 100d));allMap.put("xd",MathUtils.getValidityValue(allList, 100d));
			allMap.put("list", allL);allMap.put("mc", "合计");allMap.put("id", allId);
			teachList.add(allMap);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", teachList);
		map.put("pms", getPermiss());
		return map;	
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getSameData(String schoolYear,String courseId,String nature,String edu,List<AdvancedParam> advancedList){
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
	    	Boolean isgd = getPermiss();
	    	if(i == 0){
	    		Map<String,Object> countMap1 = scorePredictTeaDao.queryClassCount(year, getUsrName(), courseId, nature, null, sYear, deptList, advancedList,isgd),
	    				           countMap2 = scorePredictTeaDao.queryClassCount(year, getUsrName(), courseId, nature, "notme", sYear, deptList, advancedList,isgd);
	    		valueList = scorePredictTeaDao.queryClassScore(year, getUsrName(), courseId, nature, null,sYear,deptList,advancedParamList);
	    		valueList1 = scorePredictTeaDao.queryClassScore(year, getUsrName(), courseId, nature, "notme",sYear,deptList,advancedParamList);
	    		temp = getValueByValueList(valueList,MapUtils.getIntValue(countMap1, "scount"));temp1 = getValueByValueList(valueList1,MapUtils.getIntValue(countMap2, "scount"));
	    		compareCount(temp, "order", true);compareCount(temp1, "order", true);
	    		temp2.put("count", MapUtils.getIntValue(countMap1, "all"));temp2.put("scount", MapUtils.getIntValue(countMap1, "scount"));temp2.put("avg", MathUtils.getAvgValue(valueList));
	    		temp2.put("mc", mc+"我带的班级");temp3.put("mc", mc+"其它班级");
	    		temp3.put("count", MapUtils.getIntValue(countMap2, "all"));temp3.put("scount", MapUtils.getIntValue(countMap2, "scount"));temp3.put("avg", MathUtils.getAvgValue(valueList1));
	    	    temp2.put("list", temp); temp3.put("list", temp1);
	    	    temp2.put("max",valueList.size()==0?null:MathUtils.getMaxValue(valueList));	temp2.put("min",valueList.size()==0?null: MathUtils.getMinValue(valueList));temp2.put("mid", MathUtils.getMiddleValue(valueList));
				temp2.put("mode", MathUtils.getModeValue(valueList));temp2.put("bzc", MathUtils.getStandardDeviation(valueList));
				temp2.put("ptz", MathUtils.getSkewnessValue(valueList));temp2.put("qfd", MathUtils.getDistinctionDegree(valueList, 100d));temp2.put("xd",MathUtils.getValidityValue(valueList, 100d));
	    	    temp3.put("max",valueList1.size()==0?null:MathUtils.getMaxValue(valueList1));	temp3.put("min", valueList1.size()==0?null:MathUtils.getMinValue(valueList1));temp3.put("mid", MathUtils.getMiddleValue(valueList1));
	    	    temp3.put("mode", MathUtils.getModeValue(valueList1));temp3.put("bzc", MathUtils.getStandardDeviation(valueList1));
	    	    temp3.put("ptz", MathUtils.getSkewnessValue(valueList1));temp3.put("qfd", MathUtils.getDistinctionDegree(valueList1, 100d));temp3.put("xd",MathUtils.getValidityValue(valueList1, 100d));
	    		result.add(temp2);result.add(temp3);
	    	}else{
	    		valueList = scorePredictTeaDao.queryClassScore(year, getUsrName(), courseId, nature, "all",sYear,deptList,advancedParamList);
	    		Map<String,Object> allMap = scorePredictTeaDao.queryClassCount(year, getUsrName(), courseId, nature, "all",sYear,deptList,advancedParamList,isgd);
	    		temp = getValueByValueList(valueList,MapUtils.getIntValue(allMap,"scount"));
	    		compareCount(temp, "order", true);
	    		temp2.put("count", MapUtils.getIntValue(allMap,"all"));temp2.put("avg", MathUtils.getAvgValue(valueList));
	    		
	    		temp2.put("scount", MapUtils.getIntValue(allMap,"scount"));temp2.put("mc", mc+"所有班级");
	    		temp2.put("list",temp);
	    	    temp2.put("max",valueList.size()==0?null:MathUtils.getMaxValue(valueList));	temp2.put("min", valueList.size()==0?null:MathUtils.getMinValue(valueList));temp2.put("mid", MathUtils.getMiddleValue(valueList));
				temp2.put("mode", MathUtils.getModeValue(valueList));temp2.put("bzc", MathUtils.getStandardDeviation(valueList));
				temp2.put("ptz", MathUtils.getSkewnessValue(valueList));temp2.put("qfd", MathUtils.getDistinctionDegree(valueList, 100d));temp2.put("xd",MathUtils.getValidityValue(valueList, 100d));
	    		result.add(temp2);
	    	}
	    }
	    Map<String, Object> map = DevUtils.MAP();
		map.put("list", result);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getChartData(String schoolYear,String termCode,String courseId,String edu,List<AdvancedParam> advancedList){
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
			Map<String,Object> natureMap = scorePredictTeaDao.queryNatureList(schoolYear, termCode, getUsrName(), courseId, start, end,year,deptList,advancedParamList);
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
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getReport(String schoolYear,String termCode,String courseId,String nature,String edu,List<AdvancedParam> advancedList){
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
		if( nature == null){
			List<Map<String, Object>>list1 = scorePredictTeaDao.queryCourseNatureList(schoolYear, null, getUsrName(), courseId);
			if(list1 != null && !list1.isEmpty() ){
				nature = MapUtils.getString(list1.get(0), "id");
			}
		}
		Boolean isgd = getPermiss();
		List<Map<String,Object>> x = scorePredictTeaDao.getStuCountTrue(schoolYear, termCode, getUsrName(), courseId, nature, year, deptList, advancedParamList,isgd),
				                 y = scorePredictTeaDao.getStuCountOther(schoolYear, termCode, getUsrName(), courseId, nature, year, deptList, advancedList);
		for(Map<String,Object> xMap : x){
			String id = MapUtils.getString(xMap, "id");
			Integer value = MapUtils.getInteger(xMap, "value");
			value = value ==null? 0 :value;
			xMap.put("qk", 0);	xMap.put("hk", 0);	xMap.put("wj", 0);
			xMap.put("all", value);
			for(Map<String,Object> yMap:y){
				String id1 = MapUtils.getString(yMap, "id");
				Integer qk = MapUtils.getInteger(yMap, "qk"),
				    hk = MapUtils.getInteger(yMap, "hk"),
					wj = MapUtils.getInteger(yMap, "wj");
				if(id1.equals(id)){
					qk = qk==null?0:qk;
					hk = hk== null?0:hk;
					wj = wj ==null?0:wj;
					xMap.put("qk", qk);
					xMap.put("hk", hk);
					xMap.put("wj", wj);
					xMap.put("all", qk+hk+wj+value);
				}
			}
		}
		return x;
	}
	@Override
	@Transactional
	public void saveYzZt(String schoolYear,String termCode,String course,String nature,String teachClass){
		Boolean s = scorePredictTeaDao.getYzZt(schoolYear, termCode, course, nature, teachClass);
		String sql = "insert into t_stu_score_print_gd (SCHOOL_YEAR, COURSE_ID, TEACH_CLASS_ID, COURSE_NATURE_CODE, TERM_CODE) "
				+ " values ( '"+schoolYear+"',  '"+course+"',  '"+teachClass+"', "+(nature == null ? " null" :"'"+nature+"'")+", '"+termCode+"')";
		if(!s){
		    baseDao.getJdbcTemplate().update(sql);
		}
	}
	@Override
	@Transactional
	public void saveDt(String schoolYear,String termCode,String course,String nature,String teachClass,String kslx,String kcxx,String one,String two,String three,String four,String five,String fxr,String zr,String sj,String qt){
		String str = nature == null ? "" :" and t.course_nature_code = '"+nature+"'";
        String whereSql =" where t.school_year = '"+schoolYear+"' "
        		+ " and t.term_code = '"+termCode+"' and t.course_id='"+course+"' and t.teach_class_id = '"+teachClass+"' "
        		+ str;
		String xx = "select nvl(count(0),0) from t_stu_report_lrd_gd t "+whereSql;
		int count = baseDao.queryForInt(xx);
		String xxx = "  t_stu_report_lrd_gd (SCHOOL_YEAR, TERM_CODE, COURSE_ID, TEACH_CLASS_ID, COURSE_NATURE_CODE, ONE_, TWO_, THREE_, FOUR_ONE, FOUR_THREE, KSLX, KCXX,FXR,ZR,SJ,QT) "
				+ " values ( '"+schoolYear+"',   '"+termCode+"','"+course+"',  '"+teachClass+"', "+(nature == null ? " null" :"'"+nature+"'")+",'"+one+"', '"+two+"', '"+three+"', '"+four+"', '"+five+"', '"+kslx+"', '"+kcxx+"', '"+fxr+"', '"+zr+"', '"+sj+"','"+qt+"')";
		String sql = "insert into "+xxx;
		if(count >0){
			String deletesql = "delete from t_stu_report_lrd_gd t "+whereSql;
			baseDao.getJdbcTemplate().execute(deletesql);
//			sql = " update t_stu_report_lrd_gd t set t.one_='"+one+"' , t.two_= '"+two+"' , t.three_ = '"+three+"' "
//				+ " , t.four_one = '"+four+"' , t.four_three = '"+five+"' , t.kslx = '"+kslx+"' , t.kcxx = '"+kcxx +"'"
//				+ " , t.fxr = '"+fxr+"' , t.zr = '"+zr+"' , t.sj = '"+sj+"'" 
//				+ whereSql;
		}
		baseDao.getJdbcTemplate().update(sql);
	}
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public void saveXt(String schoolYear,String termCode,String course,String nature,String teachClass,List<HashMap> tx,List<HashMap> tf,List<HashMap> df,List<String> th,List<String> thn){
		String str = nature == null ? "" :" and t.course_nature_code = '"+nature+"'";
         int i=0;
         if(th != null){
			 for(String a:th){
				 String whereSql =" where t.school_year = '"+schoolYear+"' "
			        		+ " and t.term_code = '"+termCode+"' and t.course_id='"+course+"' and t.teach_class_id = '"+teachClass+"' "
			        		+ str+" and t.th_ = '"+a+"'";
				 String xx = "select nvl(count(0),0) from t_stu_report_lr_gd t "+whereSql;
				 int count = baseDao.queryForInt(xx);
				 String xxx = " t_stu_report_lr_gd (SCHOOL_YEAR, TERM_CODE, COURSE_ID, TEACH_CLASS_ID, COURSE_NATURE_CODE, ONE_, TWO_, THREE_, TH_,ORDER_) "
							+ " values ( '"+schoolYear+"',   '"+termCode+"','"+course+"',  '"+teachClass+"', "+(nature == null ? " null" :"'"+nature+"'")+", '"+MapUtils.getString(tx.get(i), "mc")+"', '"+MapUtils.getString(tf.get(i), "mc")+"', '"+MapUtils.getString(df.get(i), "mc")+"', '"+a+"','"+thn.get(i)+"')";
				 String sql = "insert into "+xxx;
		         if(count>0){
		        	 sql = " update t_stu_report_lr_gd t set t.one_='"+tx.get(i)+"' , t.two_= '"+tf.get(i)+"', t.three_ = '"+df.get(i)+"' "
		     				+ " ,t.th_ = '"+a+"'"
		     				+ whereSql;
		         }
		         baseDao.getJdbcTemplate().update(sql);
		         i++;
			 }
         }
	}
	@Override
	public Map<String,Object> getDtXt(String schoolYear,String termCode,String course,String nature,String teachClass){
		if( course == null){
			List<Map<String, Object>>list = scorePredictTeaDao.queryCourseList(schoolYear, termCode, getUsrName());
			if(list != null && !list.isEmpty() ){
				course = MapUtils.getString(list.get(0), "id");
			}
		}
		if( nature == null){
			List<Map<String, Object>>list1 = scorePredictTeaDao.queryCourseNatureList(schoolYear, null, getUsrName(), course);
			if(list1 != null && !list1.isEmpty() ){
				nature = MapUtils.getString(list1.get(0), "id");
			}
		}
		String str = nature == null ? "" :" and t.course_nature_code = '"+nature+"'"; 
		String whereSql =" where t.school_year = '"+schoolYear+"' "
	        		+ " and t.term_code = '"+termCode+"' and t.course_id='"+course+"' and t.teach_class_id = '"+teachClass+"' "
	        		+ str;
		String sql = "select * from t_stu_report_lrd_gd t "+whereSql;
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		Map<String,Object> map = list != null && list.size()>0?list.get(0):new HashMap<String, Object>();
		String sql1 = "select wm_concat(t.one_) as tx,wm_concat(t.two_) as tf,wm_concat(t.three_) as df from t_stu_report_lr_gd t "+whereSql + " order by t.order_";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		Map<String,Object> map1 = list1 != null && list1.size()>0?list1.get(0):new HashMap<String, Object>();
		String tx = MapUtils.getString(map1, "tx"),tf = MapUtils.getString(map1, "tf"),df=MapUtils.getString(map1, "df");
		String[] txAry = tx.split(","),tfAry = tf.split(","),dfAry = df.split(",");
		List<Map<String,Object>> txList = new ArrayList<Map<String,Object>>(),
				                 tfList = new ArrayList<Map<String,Object>>(),
				                 dfList = new ArrayList<Map<String,Object>>();
	    if(txAry != null){
	    	int i =0;
			for(String ia : txAry){
				Map<String,Object> temp1 = new HashMap<String, Object>();
				temp1.put("id", i);
				temp1.put("mc", ia);
				txList.add(temp1);
				i++;
			}
	    }
	    if(tfAry != null){
	    	int k =0;
			for(String ka : tfAry){
				Map<String,Object> temp2 = new HashMap<String, Object>();
				temp2.put("id", k);
				temp2.put("mc", ka);
				txList.add(temp2);
				k++;
			}
	    }
	    if(dfAry != null){
	    	int j =0;
			for(String ja : dfAry){
				Map<String,Object> temp3 = new HashMap<String, Object>();
				temp3.put("id", j);
				temp3.put("mc", ja);
				txList.add(temp3);
				j++;
			}
	    }
	    if(!txList.isEmpty()){
	    	map.put("tx", txList);
	    }
        if(!tfList.isEmpty()){
        	map.put("tf", txList);
	    }
		if(!dfList.isEmpty()){
			map.put("df", txList);
		}
		return map;
	}
	@Override
	public Boolean getYzZt(String schoolYear,String termCode,String course,String nature,String teachClass){
		return scorePredictTeaDao.getYzZt(schoolYear, termCode, course, nature, teachClass);
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getDyData(String schoolYear,String termCode,String courseId,String classid,String nature,String edu,List<AdvancedParam> advancedList){
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
		List<Map<String,Object>> attrList = scorePredictTeaDao.getCodeAttr();
		Map<String,Object> attrMap = scorePredictTeaDao.queryAttrList(schoolYear, termCode, getUsrName(), courseId, classid);
		Map<String,Object> xfMap = (Map<String, Object>) attrMap.get("xf");
		Integer xf = MapUtils.getInteger(xfMap, "xf");
		String attr = MapUtils.getString(xfMap, "id");
		for(Map<String,Object> temp : attrList){
			String id = MapUtils.getString(temp, "id");
			if(id.equals(attr)){
				temp.put("xf", xf);
				temp.put("hasxf", 1);
			}else{
				temp.put("hasxf", 0);
			}
		}
		Map<String,Object> nameMap= scorePredictTeaDao.queryYxAndBjList(courseId, attr, classid);
	    Map<String, Object> map = DevUtils.MAP();
	    map.put("list", attrList);
	    map.put("tea", attrMap);
	    map.put("xf", xf);
	    map.put("name",nameMap);
	    return map;
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
	private List<Map<String,Object>> getValueByValueList(List<Double> valueList,int count){
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
	private Boolean getPermiss(){
		return GetCachePermiss.hasPermssion(getUsrName(), ShiroTag+":getReport");
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
}
