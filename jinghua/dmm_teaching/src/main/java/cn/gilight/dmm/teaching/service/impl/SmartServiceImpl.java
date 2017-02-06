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

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.BhrConstant;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.ScoreDao;
import cn.gilight.dmm.teaching.dao.SmartDao;
import cn.gilight.dmm.teaching.service.SmartService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Service("smartService")
public class SmartServiceImpl implements SmartService{
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private SmartDao smartDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private ScoreDao scoreDao;
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"smart";
	/**
	 * 基础GPA ID
	 */
	private static final int YEAR_LENGTH = 10;
	/**
	 * 年级筛选条件
	 */
	public static final String[][] GRADE_GROUP={{"0","全年级"},{"1","大一"},{"2","大二"},{"3","大三"},{"4","大四"},{"5","大五"}};
 	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGradeSelect(List<AdvancedParam> advancedParamList,Integer schoolYear){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, null, null,null);
		List<Map<String, Object>> list = new ArrayList<>();
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
	    schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear");
		int yearlen = smartDao.queryGradeLen(deptList,stuAdvancedList,schoolYear);
		String[][] array = GRADE_GROUP;
		for(int i=0;i<(yearlen+1);i++){
			Map<String, Object> map = new HashMap<>();
			map.put("mc", array[i][1]);
			if(i==0){
				map.put("id", null);
			}else{
				map.put("id", (schoolYear-i+1));
			}
			map.put("bs",array[i][0]);
			list.add(map);
		}
		Map<String,Object> temp = DevUtils.MAP();
		temp.put("list", list);
		return temp;
	}
	@Override
	public Map<String, Object> getYearAndTerm(){
//		List<Map<String,Object>> year  = businessService.queryBzdmSchoolYear(Constant.TABLE_T_STU_SCORE, "SCHOOL_YEAR");
//		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
//		List<Map<String,Object>> xqlist = businessService.queryBzdmTermCode();
//		for(int j=1;j<year.size();j++){
//			String nian = MapUtils.getString(year.get(j), "id");
//			for(int i=xqlist.size()-1;i>-1;i--){
//			String xqid = MapUtils.getString(xqlist.get(i), "id");
//			String xqmc = MapUtils.getString(xqlist.get(i), "mc");
//			Map<String,Object> temp = new HashMap<String,Object>();
//			String id = nian+"+"+xqid;
//			String mc = MapUtils.getString(year.get(j), "mc")+" "+xqmc;
//			temp.put("id", id);
//			temp.put("mc", mc);
//			result.add(temp);
//			}
//		}
//		Date day = new Date();
//		String[] date = EduUtils.getSchoolYearTerm(day);
//		int xzxq = Integer.parseInt(date[1]);int x = 0;
//		for(int k=xzxq-1;k>-1;k--){
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("id",date[0]+"+"+MapUtils.getString(xqlist.get(k),"id"));
//		map.put("mc",date[0]+"学年 "+MapUtils.getString(xqlist.get(k),"mc"));
//		result.add(x,map);
//		x++;
//		}
		Map<String,Object> temp = DevUtils.MAP();
		temp.put("list",  businessService.queryBzdmScoreXnXq());
		return temp;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getEduSelect(){
		Map<String, Object> paramsMap= getParamsMapByParams(null, null, null, null,null);
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", businessService.queryBzdmStuEducationList(deptList));
		map.put("xbgpa",smartDao.queryXbGpa());
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getTopGpa(Integer schoolYear,String term,String grade,String edu, List<AdvancedParam> advancedParamList,int pagesize,int index){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, term, edu,grade);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		grade  =  MapUtils.getString(paramsMap, "grade");
		edu  =  MapUtils.getString(paramsMap, "edu");
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		List<Map<String,Object>> list = smartDao.queryGpa(schoolYear, term, deptList, stuAdvancedList);
		index = index<0 ? 1 : index;
//		int pageCount = new Double(Math.ceil(MathUtils.getDivisionResult(list.size(), pagesize, 1))).intValue();
		int size = list.size();
		int start = (index-1)*pagesize; start = start>size ? size : start;
		int end = index*pagesize; end = end>size ? size : end;
		list = list.subList(start, end);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = DevUtils.MAP(),
		                   map1 = new HashMap<String, Object>(),
						   map2 = new HashMap<String, Object>(),
						   map3 = new HashMap<String, Object>(),
						   map4 = new HashMap<String, Object>();
	    if(list.size()<=5){
			map1.put("list", list);
		}else if(list.size()<=10){
			map1.put("list", list.subList(0,5));
			map2.put("list", list.subList(5,list.size()));
		}else if(list.size()<=15){
			map1.put("list", list.subList(0,5));
			map2.put("list", list.subList(5,10));
			map3.put("list", list.subList(10,list.size()));
		}else if(list.size()<=20){
			map1.put("list", list.subList(0,5));
			map2.put("list", list.subList(5,10));
			map3.put("list", list.subList(10,15));
			map4.put("list", list.subList(15,list.size()));
		}
	    if(map1!=null&&!map1.isEmpty()){
	    	result.add(map1);
	    }
        if(map2!=null&&!map2.isEmpty()){
        	result.add(map2);
	    }
        if(map3!=null&&!map3.isEmpty()){
        	result.add(map3);
        }
        if(map4!=null&&!map4.isEmpty()){
        	result.add(map4);
        }
		map.put("total",size);
		map.put("list",result);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getXbFrom(Integer schoolYear,String term,String grade,String edu,String xzqh,Boolean updown,List<AdvancedParam> advancedParamList){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, term, edu,grade);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		grade  =  MapUtils.getString(paramsMap, "grade");
		edu  =  MapUtils.getString(paramsMap, "edu");
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		xzqh = xzqh==null?"0":xzqh;
		schoolYear = schoolYear==null?EduUtils.getSchoolYear4():schoolYear;
		List<Map<String,Object>> list = smartDao.queryFrom(schoolYear, term,xzqh,updown,deptList,stuAdvancedList);
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < list.size(); i++) {
			Map<String, Object> temp = list.get(i);
			list1.add(temp);
		}
		int max=0;
		if(list1.size()>1){
			max = MapUtils.getIntValue(list1.get(1), "value");
			if (max==0){
				max=100;
			}
		}else{
			max = 100;
		}
		if(max<100){
			max = 100;
		}
	    max = numShift(max);
		Map<String,Object> map = DevUtils.MAP();
		map.put("max",max);
		map.put("list", list1);
		map.put("cc", MapUtils.getString(list1.get(0), "cc"));
		map.put("maptype", MapUtils.getString(list.get(0), "maptype"));
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public  Map<String,Object> getTable(Integer schoolYear,String term,String grade,String edu,int pagesize,int index,String column,Boolean asc,String type,List<AdvancedParam> advancedParamList){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, term, edu,grade);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		edu = MapUtils.getString(paramsMap, "edu");
		advancedParamList = (List<AdvancedParam>) paramsMap.get("advancedParamList");
		grade  =  AdvancedUtil.getValue(advancedParamList, AdvancedUtil.Stu_ENROLL_GRADE);
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<>();
    	if(advancedParamList.size()>2 || !edu.equals(MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id")) || grade != null){
			if(!"COURSE".equals(type) && !"TEACHER".equals(type)){
	    		map = smartDao.queryTable(schoolYear, term,deptList,pagesize,index,column,asc,type,stuAdvancedList);
			}else if("TEACHER".equals(type)){
				map = smartDao.queryTableTeach(schoolYear,term,deptList,pagesize,index,column,asc,type,stuAdvancedList);
			}else if("COURSE".equals(type)){
				map = smartDao.queryTableCourse(schoolYear,term,deptList,pagesize,index,column,asc,type,stuAdvancedList);
			}
    	}else{
    		list = getLogData(type, String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1), term, deptList);
			smartDao.sortList(list, column, asc);
		
			int size = list.size();
			int start = (index-1)*pagesize; start = start>size ? size : start;
			int end = index*pagesize; end = end>size ? size : end;
			list = list.subList(start,end);
			int i=((index-1)*pagesize+1);
			for(Map<String, Object> temp : list){
				temp.put("r", i);
				i++;
			}
			map.put("list", list);
			map.put("total", size);
    	}
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getXbCountLine(String edu,List<AdvancedParam> advancedParamList){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, null, null, edu,null);
		edu  =  MapUtils.getString(paramsMap, "edu");
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		List<Map<String,Object>> result = businessService.queryBzdmScoreXnXq();
		String timeStr = result.isEmpty()?"":MapUtils.getString(result.get(0), "id");
		String[] year = timeStr.split(",");
		int schoolYear = timeStr.equals("")?EduUtils.getSchoolYear4():Integer.parseInt(year[0].substring(0,4));
		Map<String,Object> temp = smartDao.queryCounts(deptList, stuAdvancedList);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(),
				                  xbList = (List<Map<String, Object>>) MapUtils.getObject(temp, "xb"),
				                  allList = (List<Map<String, Object>>) MapUtils.getObject(temp, "all");
		for(int i=(schoolYear-YEAR_LENGTH+1);i<(schoolYear+1);i++){
			int xbCount = 0,all = 0; 
			for(Map<String,Object> xbMap : xbList){
				int year_1 = MapUtils.getIntValue(xbMap, "name"),
				    count_1 = MapUtils.getIntValue(xbMap, "value");
				if( i == year_1){
					xbCount = count_1;
				}
			}
			
			for(Map<String,Object> allMap : allList){
				int year_2 = MapUtils.getIntValue(allMap, "name"),
				    count_2 = MapUtils.getIntValue(allMap, "value");
				if( i == year_2){
					all = count_2;
				}
			}
			Double lv = all == 0? 0 : MathUtils.get2Point(MathUtils.getDivisionResult(xbCount*100, all, 2));
			Map<String,Object> temp_1 = new HashMap<String, Object>();
			temp_1.put("name", i);
			temp_1.put("count", xbCount);
			temp_1.put("lv", lv);
			list.add(temp_1);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getRadar(Integer schoolYear,String term,String grade,String edu,List<AdvancedParam> advancedParamList){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, term, edu,grade);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		grade  =  MapUtils.getString(paramsMap, "grade");
		edu  =  MapUtils.getString(paramsMap, "edu");
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		List<Map<String, Object>> list = smartDao.queryRadar(schoolYear,term,deptList,stuAdvancedList);
		// 雷达图：单项金额、覆盖率、总金额
		Double score_max = 0D, bookcount_max = 0D, bookrke_max = 0D,zccount_max =0D,risecount_max=0D;
		Double score = MapUtils.getDoubleValue(list.get(0), "score");
		Double bookcount = MapUtils.getDoubleValue(list.get(0), "bookcount");
		Double bookrke = MapUtils.getDoubleValue(list.get(0), "bookrke");
		Double zccount = MapUtils.getDoubleValue(list.get(0), "zccount");
		Double risecount = MapUtils.getDoubleValue(list.get(0), "risecount");
		Double score1 = MapUtils.getDoubleValue(list.get(1), "score");
		Double bookcount1 = MapUtils.getDoubleValue(list.get(1), "bookcount");
		Double bookrke1 = MapUtils.getDoubleValue(list.get(1), "bookrke");
		Double zccount1 = MapUtils.getDoubleValue(list.get(1), "zccount");
		Double risecount1 = MapUtils.getDoubleValue(list.get(1), "risecount");
		score_max =dobShift(score,score1,score_max);
		bookcount_max = dobShift(bookcount,bookcount1,bookcount_max);
		bookrke_max = dobShift(bookrke,bookrke1,bookrke_max);
		zccount_max = dobShift(zccount,zccount1,zccount_max); 
		risecount_max = dobShift(risecount,risecount1,risecount_max);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("score_max", score_max*1.2);
		map.put("bookcount_max", bookcount_max*1.2);
		map.put("bookrke_max", bookrke_max*1.2);
		map.put("zccount_max", zccount_max*1.2);
		map.put("risecount_max", risecount_max*1.2);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getRadarStu(Integer schoolYear,String term,String grade,String edu,String stuNo,List<AdvancedParam> advancedParamList){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, term, edu,grade);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		grade  =  MapUtils.getString(paramsMap, "grade");
		edu  =  MapUtils.getString(paramsMap, "edu");
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		List<Map<String, Object>> list = smartDao.queryRadar(schoolYear,term,stuNo,deptList,stuAdvancedList);
		// 雷达图：单项金额、覆盖率、总金额
		Double score_max = 0D, bookcount_max = 0D, bookrke_max = 0D,zccount_max =0D,risecount_max=0D;
		Double score = MapUtils.getDoubleValue(list.get(0), "score");
		Double bookcount = MapUtils.getDoubleValue(list.get(0), "bookcount");
		Double bookrke = MapUtils.getDoubleValue(list.get(0), "bookrke");
		Double zccount = MapUtils.getDoubleValue(list.get(0), "zccount");
		Double risecount = MapUtils.getDoubleValue(list.get(0), "risecount");
		Double score1 = MapUtils.getDoubleValue(list.get(1), "score");
		Double bookcount1 = MapUtils.getDoubleValue(list.get(1), "bookcount");
		Double bookrke1 = MapUtils.getDoubleValue(list.get(1), "bookrke");
		Double zccount1 = MapUtils.getDoubleValue(list.get(1), "zccount");
		Double risecount1 = MapUtils.getDoubleValue(list.get(1), "risecount");
		Double score2 = MapUtils.getDoubleValue(list.get(2), "score");
		Double bookcount2 = MapUtils.getDoubleValue(list.get(2), "bookcount");
		Double bookrke2 = MapUtils.getDoubleValue(list.get(2), "bookrke");
		Double zccount2 = MapUtils.getDoubleValue(list.get(2), "zccount");
		Double risecount2 = MapUtils.getDoubleValue(list.get(2), "risecount");
		score_max =dobShift(score,score1,score2,score_max);
		bookcount_max = dobShift(bookcount,bookcount1,bookcount2,bookcount_max);
		bookrke_max = dobShift(bookrke,bookrke1,bookrke2,bookrke_max);
		zccount_max = dobShift(zccount,zccount1,zccount2,zccount_max); 
		risecount_max = dobShift(risecount,risecount1,risecount2,risecount_max);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("score_max", score_max*1.2);
		map.put("bookcount_max", bookcount_max*1.2);
		map.put("bookrke_max", bookrke_max*1.2);
		map.put("zccount_max", zccount_max*1.2);
		map.put("risecount_max", risecount_max*1.2);
		return map;
	}
	@Override
	public Map<String,Object> getDormStu(String stuNo){
		Map<String,Object> temp = smartDao.getDormStu(stuNo);
		Map<String,Object> map = DevUtils.MAP();
		map.putAll((temp==null||temp.isEmpty())?new HashMap<String, Object>():temp);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getCostStu(Integer schoolYear,String term,String grade,String edu,String stuNo,List<AdvancedParam> advancedParamList){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, term, edu,grade);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		grade  =  MapUtils.getString(paramsMap, "grade");
		edu  =  MapUtils.getString(paramsMap, "edu");
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		Map<String,Object> temp = smartDao.getCost(schoolYear, term, stuNo, deptList, stuAdvancedList);
		Map<String,Object> map = DevUtils.MAP();
		map.putAll(temp);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getBorrowStu(Integer schoolYear,String term,String grade,String edu,String stuNo,List<AdvancedParam> advancedParamList){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, term, edu,grade);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		grade  =  MapUtils.getString(paramsMap, "grade");
		edu  =  MapUtils.getString(paramsMap, "edu");
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		Map<String,Object> temp = smartDao.getBorrow(schoolYear, term, stuNo, deptList, stuAdvancedList);
		Map<String,Object> map = DevUtils.MAP();
		map.putAll(temp);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getScoreStu(Integer schoolYear,String term,String grade,String edu,String stuNo,List<AdvancedParam> advancedParamList){
		Map<String, Object> paramsMap= getParamsMapByParams(advancedParamList, schoolYear, term, edu,grade);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		grade  =  MapUtils.getString(paramsMap, "grade");
		edu  =  MapUtils.getString(paramsMap, "edu");
		List<String> deptList = (List<String>) paramsMap.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramsMap.get("stuAdvancedList");// 高级查询-学生参数
		Map<String,Object> temp = smartDao.getScoreGk(schoolYear, term, stuNo, deptList, stuAdvancedList);
		Map<String,Object> map = DevUtils.MAP();
		map.putAll(temp);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getScoreStuMx(Integer schoolYear,String term,String stuNo){
		Map<String, Object> paramsMap= getParamsMapByParams(null, schoolYear, term, null,null);
		schoolYear = MapUtils.getIntValue(paramsMap, "schoolYear"); 
		term   = MapUtils.getString(paramsMap, "termCode");
		List<Map<String,Object>> temp = smartDao.getScoreTable(schoolYear, term, stuNo);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list",temp);
		return map;
	}
	@Override
	public Map<String,Object> getDisplayedLevelType(){
		List<Map<String, Object>> deptMapList = businessService.getDeptDataListForGoingDownStu(getDeptDataList(), null, null);
		String level_type = null;
		if(!deptMapList.isEmpty()){
			level_type = MapUtils.getString(deptMapList.get(0), "level_type");
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("level_type", level_type);
		return map;
	}
	@Override
	public Map<String,Object> getTimeLine(Integer schoolYear,String termCode){
		Map<String,Object> param = getParamsMapByParams(null, schoolYear, termCode, null, null);
		schoolYear = MapUtils.getIntValue(param, "schoolYear"); 
		termCode   = MapUtils.getString(param, "termCode");
		String year = String.valueOf(schoolYear)+"-"+String.valueOf(schoolYear+1);
		Map<String,Object> map = DevUtils.MAP();
		List<Map<String,Object>> summer = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> winter = new ArrayList<Map<String,Object>>();
		for(BhrConstant.BHRTIME_Table table:queryEnum()){
		Map<String,Object> temp1 = smartDao.queryTimeline(year,termCode,table,BhrConstant.Season_Date[0][1]);
		Map<String,Object> temp2 = smartDao.queryTimeline(year,termCode,table,BhrConstant.Season_Date[1][1]);
		if(temp1!=null){
		summer.add(temp1);
		}
		if(temp2!=null){
		winter.add(temp2);
		}
		}
		compareCount(summer,"value_",true);
		compareCount(winter,"value_",true);
	
		summer=convertList(mergeList(getDelete(summer)));
		winter=convertList(mergeList(getDelete(winter)));
		map.put("summer", summer);
		map.put("winter", winter);
		return map;
	}
	@Override
	public Map<String,Object> getStuList(List<AdvancedParam> advancedParamList,Page page, 
			Map<String, Object> keyValue, List<String> fields){
		String sql =getStuDetailSql(advancedParamList, keyValue, fields);
		Map<String,Object> map = baseDao.createPageQueryInLowerKey(sql, page);
		return map;
	}
@Override
public List<Map<String,Object>> getStuDetail(List<AdvancedParam> advancedParamList,
		Map<String, Object> keyValue, List<String> fields){
	String sql =getStuDetailSql(advancedParamList, keyValue, fields);
	List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
	return list;
}
	/**
	 * 数字转化为100的倍数
	 * @param num
	 */
	private int numShift(int num){
		String a = String.valueOf(num);
		int val1 = Integer.parseInt(a.substring(a.length()-2, a.length()));
		int val2 = Integer.parseInt(a.substring(0, a.length()-2));
		if(val1>0){
			val2=val2+1;
		}
		String temp = String.valueOf(val2)+"00";	
		
		return Integer.parseInt(temp);
	}
	private double dobShift(double a,double b,double result){
		if(a>b){
			result = a>result?a:result;
		}else{
			result = b>result?b:result;
		}
		return result;
	}
	private double dobShift(double a,double b,double c,double result){
		if(a>b&&a>c){
			result = a>result?a:result;
		}else if(b>a&&b>c){
			result = b>result?b:result;
		}else if(c>a&&c>b){
			result = c>result?c:result;
		}
		return result;
	}
	/**
	 * 根据参数优化参数
	 * @param schoolYear 学年
	 * @param edu 学历
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getParamsMapByParams(List<AdvancedParam> advancedParamList, Integer schoolYear, String termCode, String edu,String grade){
		List<Map<String,Object>> lx = (List<Map<String, Object>>) getYearAndTerm().get("list");
		String ary = MapUtils.getString(lx.get(0), "id");
		String[] termAry = ary.equals("")?EduUtils.getSchoolYearTerm(DateUtils.getNowDate()):ary.split(",");
		schoolYear = schoolYear==null ? Integer.valueOf(termAry[0].substring(0, 4)) : schoolYear;
		termCode   = termCode==null ? termAry[1] : termCode;
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		
		String year = termAry[0];
		List<String> deptList = getDeptDataList();
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		AdvancedParam eduAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu),
                      gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu,AdvancedUtil.Stu_ENROLL_GRADE,grade);  				
		AdvancedUtil.add(advancedParamList,eduAdp);
		AdvancedUtil.add(advancedParamList,gradeAdp);
		List<AdvancedParam> stuAdvancedList      = AdvancedUtil.getAdvancedParamStu(advancedParamList),
							businessAdvancedList = AdvancedUtil.getAdvancedParamBusiness(advancedParamList);
		// return
		Map<String, Object> map = new HashMap<>();
		map.put("advancedParamList", advancedParamList);
		map.put("stuAdvancedList", stuAdvancedList);
		map.put("businessAdvancedList", businessAdvancedList);
		map.put("deptList", deptList);
		map.put("schoolYear", schoolYear);
		map.put("termCode", termCode);
		map.put("edu", edu);
		return map;
	}
	@SuppressWarnings("unchecked")
	private String getStuDetailSql(List<AdvancedParam> advancedParamList,Map<String,Object> keyValue,List<String> fields){
		List<String> deptList = getDeptDataList(); // 权限
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		List<Map<String,Object>> lx = (List<Map<String, Object>>) getYearAndTerm().get("list");
		String ary = MapUtils.getString(lx.get(0), "id");
		String[] termAry = ary== null?EduUtils.getSchoolYearTerm(DateUtils.getNowDate()):ary.split(",");
		String schoolYear = MapUtils.getString(keyValue, "schoolYear"); 
		String term   = MapUtils.getString(keyValue, "termCode");
		String grade  =  MapUtils.getString(keyValue, "grade")==null?null:MapUtils.getString(keyValue, "grade");
		String edu  =  MapUtils.getString(keyValue, "edu");
		String xzqh = MapUtils.getString(keyValue, "xzqh")==null?smartDao.getChinaId():MapUtils.getString(keyValue, "xzqh");
		int schoolyear = (schoolYear == null || schoolYear.equals("")) ? Integer.valueOf(termAry[0].substring(0, 4)) : Integer.parseInt(schoolYear);
		String termCode  = (term == null|| term.equals("")) ? termAry[1] : term;
		Boolean updown = MapUtils.getBooleanValue(keyValue, "updown"); 
		if(edu == null || edu.equals("")) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		AdvancedParam eduAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu),
                gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu,AdvancedUtil.Stu_ENROLL_GRADE,grade);  				
	    AdvancedUtil.add(advancedParamList,eduAdp);
	    AdvancedUtil.add(advancedParamList,gradeAdp);
	    List<AdvancedParam> stuAdvancedList      = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		String sql = smartDao.queryStuListSql(schoolyear, termCode, xzqh, updown, deptList, stuAdvancedList);
		return sql;	
	}
	private List<BhrConstant.BHRTIME_Table> queryEnum(){
		List<BhrConstant.BHRTIME_Table> list = new ArrayList<BhrConstant.BHRTIME_Table>(); 
		list.add(BhrConstant.BHRTIME_Table.Breakfast);
		list.add(BhrConstant.BHRTIME_Table.Lunch);
		list.add(BhrConstant.BHRTIME_Table.Dinner);
		list.add(BhrConstant.BHRTIME_Table.Ambookrke);
		list.add(BhrConstant.BHRTIME_Table.Pmbookrke);
		list.add(BhrConstant.BHRTIME_Table.Outdormrke);
		list.add(BhrConstant.BHRTIME_Table.Indormrke);
		return list;
	}
	/**
	 * 根据时间获取百分比
	 * @param list
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> convertList(List<Map<String, Object>> list) {
		int start = BhrConstant.T_STU_BEHTIME_START_END[0];
		int  end = BhrConstant.T_STU_BEHTIME_START_END[1];
		int length =(end-start)*60;int i=1;
		for(Map<String, Object> map : list){
			String time = MapUtils.getString(map, "value_"); 
			if(time == null){
				map.put("lv", 0);
			}else{
				int hour = Integer.parseInt(time.substring(0,2))-start;
				int min  = Integer.parseInt(time.substring(3));
				int count =  hour*60+min;
				double lv = MathUtils.get2Point(MathUtils.getDivisionResult(count, length, 4)*100);
				map.put("lv", lv);
			}
			if( i%2 ==1 ){
				map.put("change", true);
			}else{
				map.put("change", false);
			}
			i++;
		}
		return list;
	}
	/**
	 * list排序
	 * @param list 排序list
	 * @param compareField 排序字段
	 * @param asc 正序倒序
	 */
	private void compareCount(List<Map<String, Object>> list,
			final String compareField, final boolean asc) {
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				if(MapUtils.getString(o1, compareField)== null||MapUtils.getString(o2, compareField)== null){
					return -1;
				}
				int count1 = Integer.parseInt(MapUtils.getString(o1, compareField).substring(0,2)), 
					count2 = Integer.parseInt(MapUtils.getString(o2, compareField).substring(0,2)),
					count3 = Integer.parseInt(MapUtils.getString(o1, compareField).substring(3)), 
					count4 = Integer.parseInt(MapUtils.getString(o2, compareField).substring(3)),
					flag = 0, pare = asc? 1: -1; // 姝ｅ簭涓�
				if (count1 > count2){
					flag = pare;
				}else if (count1 < count2){
					flag = -pare;
				}else if (count1 == count2){
					if(count3>count4){
						flag = pare;
					}else if(count3 < count4){
						flag = -pare;
					}
				}
				return flag;
			}
		});
	}
	/**
	 * 合并list中map的value相同的
	 * @param list 
	 * @return  List<Map<String,Object>>
	 */
	private List<Map<String,Object>> mergeList(List<Map<String,Object>> list){
        List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
        for(int i=0;i<list.size();i++){
            Map<String,Object> map = list.get(i);
            Map<String,Object> newMap=isExistSame(list,map,retList);
            if(null==newMap){
              continue;
            }
            else{
                retList.add(newMap);
            }
        }
        return retList;
    }
    /**
     * value 相同的map合并
     * @param i  
     * @param value  
     * @param name
     * @param list
     * @return Map<String,Object>
     */
    private Map<String,Object> isExistSame(List<Map<String,Object>> list,Map<String,Object> map,List<Map<String,Object>> result){
    	String val ="";String value = MapUtils.getString(map, "value_");
    	for(Map<String,Object> temp :result){
    		String mc = MapUtils.getString(temp, "value_");
    		if(mc.equals(value)){
    			return null;
    		}
    	}
    	for(int j=0;j<list.size();j++){
			Map<String, Object> m = list.get(j);
			String value1 = MapUtils.getString(m,"value_");
	        String name =  MapUtils.getString(m,"name");
	        int k = 0;
	        if(value.equals(value1)){
	        	if(k==0){
	        		val = name;
	        	}else{
	        		val = val+"&"+name;
	        	}
	        	k++;
            }
		}
    	map.put("name", val);
        return map;
    }
private List<Map<String,Object>> getDelete(List<Map<String,Object>> list){
	if(!list.isEmpty()){
		if(!BhrConstant.OUT_DORM_RKE.equals(MapUtils.getString(list.get(0), "type"))){
			Iterator<Map<String, Object>> ite = list.iterator();
			while (ite.hasNext()) {
				Map<String, Object> m = ite.next();
				if (BhrConstant.OUT_DORM_RKE.equals(MapUtils.getString(m, "type"))) {
					ite.remove();
				}
			}
		}
	}
	return list;
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
		String sql = "select  a.id," + name + ",gpa_avg as avg,"
				+ " smart_count as value, smart_scale as lv ,"
				+ " gpa_before as avg1 from "
				+ tableName + " a " + " inner join (" + resultSql + ") b on a."
				+ columnName + str + " where " + " school_year = '"
				+ schoolYear + "' " + " and " + term;
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return list;
	}
}
