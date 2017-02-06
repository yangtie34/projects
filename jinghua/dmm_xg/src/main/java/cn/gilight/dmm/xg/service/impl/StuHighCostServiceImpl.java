package cn.gilight.dmm.xg.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.dmm.xg.entity.TStuAbnormalMailMonth;
import cn.gilight.dmm.xg.service.StuHighCostService;
import cn.gilight.dmm.xg.util.MailUtils;
import cn.gilight.dmm.xg.util.ZipUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ExcelUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;

@Service("stuHighCostService")
public class StuHighCostServiceImpl implements StuHighCostService {

	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	public StuCostDao stuCostDao;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BaseDao baseDao;

	private Logger log = Logger.getLogger(this.getClass());

	/**
	 * 加号代表是高消费
	 */
	public static final String[] HIGHCOST = { "+", ">", "高消费学生", "highcost","high_standard"};
	/**
	 * 异常类型
	 */
	public static final String Abnormal_Type = "highcost";
	/**
	 * 历史数据显示的学期数
	 */
	public static final int XAXIS_LENGTH = 1;
	private static final String Type_Schoolyear = "schoolyear";
	private static final String Type_Term = "term";
	private static final String Type_IsYear = "isyear";
	private static final String Type_IsTerm = "isterm";
	private static final String Type_IsWeek = "ismonth";
	private static final String Type_Week = "month";
	private static final String Type_IsMeal = "ismeal";
	private static final String Type_Meal = "meal";
	private static final String Type_Loan = "loan";
	private static final String Type_Subsidy = "subsidy";
	private static final String Type_Jm = "jm";
	private static final String Type_All = "all";
	private static final String Type_Start = "start";
	/**
	 * 导出数据的字段名
	 */
	public static final List<String> EXPORT_EXCEL_FIELD = new ArrayList<String>();
	static {
		EXPORT_EXCEL_FIELD.add(0, "xh");
		EXPORT_EXCEL_FIELD.add(1, "rxnj");
		EXPORT_EXCEL_FIELD.add(2, "zy");
		EXPORT_EXCEL_FIELD.add(3, "bj");
		EXPORT_EXCEL_FIELD.add(4, "mc");
		EXPORT_EXCEL_FIELD.add(5, "je");
	}
	/**
	 * 导出数据的字段名
	 */
	public static final List<String> EXPORT_EXCEL_HEARD = new ArrayList<String>();
	static {
		EXPORT_EXCEL_HEARD.add(0, "学号");
		EXPORT_EXCEL_HEARD.add(1, "入学年级");
		EXPORT_EXCEL_HEARD.add(2, "专业");
		EXPORT_EXCEL_HEARD.add(3, "班级");
		EXPORT_EXCEL_HEARD.add(4, "姓名");
		EXPORT_EXCEL_HEARD.add(5, "消费金额");
	}
	/**
	 * 高消费分析页面 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg + "stuHighCost";

	private List<String> getDeptDataList() {
		return getDeptDataList(null);
	}

	private List<String> getDeptDataList(String id) {
		return businessService.getDeptDataList(ShiroTag, id);
	}
	@Override
    public Map<String,Object> getStandardMap(String schoolYear,String termCode){
    	return getStandardMap(schoolYear,termCode,HIGHCOST);
    }
	@Override
	public Map<String,Object> getDataEndDate(){
		String table = stuCostDao.queryMonthMaxEnd();
		String chart = "数据来源暂无";
		List<Map<String,Object>> list = stuCostDao.querySchoolStart(DateUtils.getNowDate());
		if(list != null && list.size() > 1){
			chart = MapUtils.getString(list.get(1),"enddate");
		}
		List<Map<String,Object>> month = getMonthList(),
				                 term = getTermData();
//				                 year = getYearList();
		Map<String,Object> map = DevUtils.MAP();
		map.put("table", table);
		map.put("chart", chart);
		map.put("month", month);
		map.put("term", term);
//		map.put("year", year);
		return map;
	}
    @Override
    public Map<String,Object> getStandardMap(String schoolYear,String termCode,String[] type){
       Double value = stuCostDao.queryStandard(schoolYear, termCode, Constant.Type_DayCost_Standard, type);
    	String name = stuCostDao.queryStandardName(schoolYear, termCode, Constant.Type_DayCost_Standard, type);
    	Map<String,Object> map = DevUtils.MAP();
    	map.put("value", value== null?0:value);
    	map.put("name", name);
    	map.put("type", stuCostDao.queryXflx());
       return map;
    }
	@Override
    public List<Map<String,Object>> getMonthData(){
    	String now = DateUtils.getNowDate();
    	List<Map<String,Object>> list = stuCostDao.querySchoolStart(now),
    			                 result = new ArrayList<Map<String,Object>>();
    	for(Map<String,Object> temp: list){
    		String start = MapUtils.getString(temp, "startdate");
    		String end = MapUtils.getString(temp, "enddate");
    		String schoolYear =  MapUtils.getString(temp, "schoolyear");
    		String termCode =  MapUtils.getString(temp, "term");
    		if (DateUtils.compare(end, now)){end = now;}
    		int j = 1;
    		for (String i = start;DateUtils.compare(end, i);i =DateUtils.getNextDayByLen(i, 31)){
    			Map<String,Object> temp1 = new HashMap<String, Object>();
    			String js =DateUtils.getNextMonthYesterday(i);
    			if(DateUtils.compare(js, end) && !end.equals(now)){js= end;}
    			if(DateUtils.compare(js, end) &&  end.equals(now)){continue;}
    			String id = schoolYear+","+termCode+","+String.valueOf(j); 
    			String mc = "第 "+String.valueOf(j)+" 月"+"（"+i+"~"+js+"）" ; 
    			String code = i+","+js; 
    			temp1.put("id", id);temp1.put("mc", mc);temp1.put("code", code);
    			result.add(temp1);
    			i = DateUtils.getNextDayByLen(js, 1);
    		    j++;
    		}
    	}
    	return result;
    }
	@Override
    public List<Map<String,Object>> getMonthList(){
    	List<Map<String,Object>> list = stuCostDao.queryMonthList(),
    			                 result = new ArrayList<Map<String,Object>>();
    	for(Map<String,Object> temp: list){
    		Map<String,Object> temp1 = new HashMap<String, Object>();
    		String schoolYear =  MapUtils.getString(temp, "schoolyear");
    		String termCode =  MapUtils.getString(temp, "term");
    		String month = MapUtils.getString(temp, "month");
    		String start = MapUtils.getString(temp, "startdate");
    		String end = MapUtils.getString(temp, "enddate");
    		String xx = termCode.equals(Globals.TERM_FIRST)?"第一学期":"第二学期";
    		String id = schoolYear+","+termCode+","+month; 
    		String mc = schoolYear+"学年 "+xx+"（第 "+month+" 月 "+start+"~"+end+"）" ; 
    		temp1.put("id", id);temp1.put("mc", mc);
    		result.add(temp1);
    		}
    	return result;
    }
	@Override
	public Map<String, Object> getCountByDept(String schoolYear,String term,Integer month,
			String column, Boolean asc, List<AdvancedParam> advancedParamList) {
		return getCountByDept(schoolYear,term,month,column, asc, getDeptDataList(),
				advancedParamList, HIGHCOST);
	}
	@Override
	public Map<String, Object> getCountByDept(String schoolYear,String term,Integer month,
			String column, Boolean asc, List<String> deptList,
			List<AdvancedParam> advancedParamList, String[] type) {
		column = column == null ? "count" : column;
		asc = asc == null ? true : !asc;
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(schoolYear != null ){
			List<AdvancedParam> stuAdvancedList = advancedParamList == null ? 
					new ArrayList<AdvancedParam>():advancedParamList;
	
			Map<String, Object> temp1 = getLastMonth(schoolYear, term, month);
			String oldyear = MapUtils.getString(temp1, "schoolyear"), oldterm = MapUtils
					.getString(temp1, "term");
			int oldmonth = MapUtils.getIntValue(temp1, "month");
			List<Map<String,Object>> now = stuCostDao.queryCountByDept(schoolYear, term, month,
					type, deptList, stuAdvancedList),
					last = stuCostDao.queryCountByDept(oldyear, oldterm, oldmonth,
							type, deptList, stuAdvancedList);;
			for(Map<String,Object> xMap : now){
				String id = MapUtils.getString(xMap, "id");
				int count = MapUtils.getIntValue(xMap, "count");
				int sb=0;
				for(Map<String,Object> yMap : last){
					String id1 = MapUtils.getString(yMap, "id");
					int count1 = MapUtils.getIntValue(yMap, "count");
					if(id1.equals(id)){
						xMap.put("change", count1==0?"--":String.valueOf(MathUtils
						.get2Point(MathUtils.getDivisionResult((count - count1)*100,
								count1, 2))));
						sb = 1;
					}
				}
				if(sb==0){
					xMap.put("change", "--");
				}
			}
			
//							
//			for (Map<String, Object> map : deptMapList) {
//				Map<String, Object> temp = new HashMap<String, Object>();
//				String id = MapUtils.getString(map, "id");
//				deptList = PmsUtils.getDeptListByDeptMap(map);
//				int count = 
//				int stucount = businessDao.queryStuCount(year, deptList,
//						stuAdvancedList);
//				int oldcount = stuCostDao.queryCountByDept(oldyear, oldterm,
//						oldmonth, type, deptList, stuAdvancedList);
//				int status = stuCostDao.querySendStatus(schoolYear, term, month,
//						id, type[3]);
//				String change = oldcount == 0 ? "--" : String.valueOf(MathUtils
//						.get2Point(MathUtils.getDivisionResult((count - oldcount),
//								oldcount, 4) * 100));
//				temp.put("name", MapUtils.getString(map, "name"));
//				temp.put("id", id);
//				temp.put("count", count);
//				temp.put("stucount", stucount);
//				temp.put("scale", MathUtils.get2Point(MathUtils.getDivisionResult(
//						count, stucount, 4) * 100));
//				temp.put("change", change);
//				temp.put("status", status);
//				result.add(temp);
//			}
			if(now !=null){
				result.addAll(now);
			}
			compareCount(result, column, asc);
		}
		Map<String, Object> map1 = DevUtils.MAP();
		map1.put("result", result);
		return map1;
	}
	

	@Override
	public Map<String, Object> getTimeSelectList() {
		Date date = new Date();
		int day = Integer.parseInt(DateUtils.getNowDate().replace("-", ""));
		List<Map<String, Object>> timeAry = stuCostDao
				.querySchoolStart(DateUtils.getNowDate());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < timeAry.size(); i++) {
			int week = 0;
			Date begin = new Date(), end = new Date();
			Date startdate = new Date(), enddate = new Date();
			String start = MapUtils.getString(timeAry.get(i), "startdate"), end1 = MapUtils
					.getString(timeAry.get(i), "enddate");
			int n = end1 == null ? 99999999 : Integer.parseInt(end1.replace(
					"-", ""));
			int y = Integer.parseInt((DateUtils.date2String(begin)).replace(
					"-", ""));
			begin = start == null ? date : DateUtils.string2Date(start);
			end = end1 == null || y < n ? date : DateUtils.string2Date(end1);
			end = end == null ? date : end;
			begin = begin == null ? date : begin;
			int jba = getWeekNo(begin);
			if (jba > 5) {
				begin = getNextDayByLen(begin, (7 - jba+1));
			}
			int weekth = DateUtils.getZcByDateFromBeginDate(begin, end);
			int weeks = 0;
			if (end1 == null || Integer.parseInt(end1.replace("-", "")) > day) {
				weeks = weekth - 1;
			} else {
				weeks = weekth;
			}
			for (int j = 0; j < weeks; j++) {
				Map<String, Object> temp = new HashMap<String, Object>();
				week = j + 1;
				if (j == 0) {
					int x = getWeekNo(begin);
					if (x < 6) {
						startdate = begin;
						enddate = getNextDayByLen(startdate, (7 - x));
					} else {
						startdate = getNextDayByLen(begin, (7 - x + 1));
						enddate = getNextDayByLen(startdate, 6);
					}
				} else {
					startdate = getNextDayByLen(enddate, 1);
					enddate = getNextDayByLen(startdate, 6);
				}
				String a = DateUtils.date2String(startdate), b = DateUtils
						.date2String(enddate);
				temp.put("id", a + "~" + b + "+" + String.valueOf(week));
				temp.put("mc"," 第 " + String.valueOf(week)
						+ " 周（"+a + "~" + b +"）" );
				String time = a.replace("-", "");
				temp.put("order", time);
				result.add(temp);
			}
		}
		compareCount(result, "order", false);
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", result);
		return map;
	}	
//  高低消费按周统计，暂时注释掉，因为太频繁且意义不大 2016-12-06
//	@Override 
//	public Map<String, Object> getCountByDept(String start, Integer weekth,
//			String column, Boolean asc, List<AdvancedParam> advancedParamList) {
//		return getCountByDept(start, weekth, column, asc, getDeptDataList(),
//				advancedParamList, HIGHCOST, Abnormal_Type);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public Map<String, Object> getCountByDept(String start, Integer weekth,
//			String column, Boolean asc, List<String> deptList,
//			List<AdvancedParam> advancedParamList, String[] type,
//			String abnormal_type) {
//		Map<String, Object> params = getParamsMapByParams(advancedParamList,
//				null, null, start, weekth);
//		start = MapUtils.getString(params, "start");
//		weekth = MapUtils.getIntValue(params, "weekth");
//		column = column == null ? "count" : column;
//		asc = asc == null ? true : !asc;
//		String[] ary = start.split("~");
//		start = ary[0];String end = ary[1];
//		String[] time = EduUtils.getSchoolYearTerm(start);
//		int schoolYear = Integer.parseInt(time[0].substring(0, 4)); // 查询学年
//		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
//				.get("stuAdvancedList"); // 高级查询-学生参数
//		List<Map<String, Object>> deptMapList = businessService
//				.getDeptDataListForGoingDownStu(deptList,
//						AdvancedUtil.getPid(stuAdvancedList), schoolYear);
//		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
//		Map<String, Object> temp1 = getLastWeekth(start, weekth);
//		String oldyear = MapUtils.getString(temp1, "schoolyear"), oldterm = MapUtils
//				.getString(temp1, "term");
//		int oldweekth = MapUtils.getIntValue(temp1, "weekth");
//		for (Map<String, Object> map : deptMapList) {
//			Map<String, Object> temp = new HashMap<String, Object>();
//			String id = MapUtils.getString(map, "id");
//			deptList = PmsUtils.getDeptListByDeptAndLevel(id,
//					MapUtils.getInteger(map, "level_"));
//			int count = stuCostDao.queryCountByDept(time[0], time[1], weekth,
//					type, deptList, stuAdvancedList);
//			int stucount = businessDao.queryStuCount(schoolYear, deptList,
//					stuAdvancedList);
//			int oldcount = stuCostDao.queryCountByDept(oldyear, oldterm,
//					oldweekth, type, deptList, stuAdvancedList);
//			int status = stuCostDao.querySendStatus(time[0], time[1], weekth,
//					id, abnormal_type);
//			String change = oldcount == 0 ? "--" : String.valueOf(MathUtils
//					.get2Point(MathUtils.getDivisionResult((count - oldcount),
//							oldcount, 4) * 100));
//			temp.put("name", MapUtils.getString(map, "name"));
//			temp.put("id", id);
//			temp.put("count", count);
//			temp.put("stucount", stucount);
//			temp.put("scale", MathUtils.get2Point(MathUtils.getDivisionResult(
//					count, stucount, 4) * 100));
//			temp.put("change", change);
//			temp.put("status", status);
//			result.add(temp);
//		}
//		compareCount(result, column, asc);
//		// index = index<0 ? 1 : index;
//		// int pageSize = pagesize;
//		// int size = result.size();
//		// int bb = (index-1)*pageSize; bb = bb>size ? size : bb;
//		// int end = index*pageSize; end = end>size ? size : end;
//		// List<Map<String,Object>> list = result.subList(bb, end);
//		Map<String, Object> map1 = DevUtils.MAP();
//		// map1.put("list", list);
//		// map1.put("total", size);
//		map1.put("result", result);
//		return map1;
//	}

	@Override
	public Map<String, Object> getNearLv(String xnxqList, String monthList,
			String deptid, List<AdvancedParam> advancedParamList) {
		return getNearLv(xnxqList, monthList, deptid, advancedParamList,
				HIGHCOST, Abnormal_Type);
	}

	@Override
	public Map<String, Object> getNearLv(String xnxqList, String monthList,
			String deptid, List<AdvancedParam> advancedParamList,
			String[] type, String abnormal_type) {
		String[] startAry = xnxqList.split(","), weekAry = monthList.split(",");
		List<Map<String, Object>> scaleList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> changeList = new ArrayList<Map<String, Object>>();
		for (int i = startAry.length - 1; i > -1; i--) {
			Map<String, Object> temp1 = new HashMap<String, Object>();
			Map<String, Object> temp2 = new HashMap<String, Object>();
			String[] start = startAry[i].split("[+]");
			String schoolYear = start[0];String termCode = start[1];
			int month = Integer.parseInt(weekAry[i]);
			Map<String, Object> map = getNearMap(schoolYear,termCode, month, deptid,
					advancedParamList, type, abnormal_type);
			temp1.put("field", month);
			temp1.put("value", MapUtils.getDoubleValue(map, "scale"));
			temp2.put("field", month);
			temp2.put("value", MapUtils.getDoubleValue(map, "change"));
			scaleList.add(temp1);
			changeList.add(temp2);
		}
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("scale", scaleList);
		temp.put("change", changeList);
		return temp;
	}

	private Map<String, Object> getNearMap(String schoolYear, String termCode,Integer month,
			String deptid, List<AdvancedParam> advancedParamList,
			String[] type, String abnormal_type) {
		int year = Integer.parseInt(schoolYear.substring(0, 4)); // 查询学年
//		Map<String, Object> temp1 = getLastWeekth(start, weekth);
//		String oldyear = MapUtils.getString(temp1, "schoolyear"), oldterm = MapUtils
//				.getString(temp1, "term");
//		int oldweekth = MapUtils.getIntValue(temp1, "weekth");
		List<AdvancedParam> stuAdvancedList =advancedParamList ==null? new ArrayList<AdvancedParam>():advancedParamList; // 高级查询-学生参数
		Map<String, Object> temp = new HashMap<String, Object>();
		String id = deptid;
		List<String> deptList = getDeptDataList(id);
		String stu = businessDao.getStuSql(year,deptList, stuAdvancedList);
		int count = stuCostDao.getDeptCost(year, termCode, month, type, deptList, stuAdvancedList);
		int stucount = baseDao.queryForCount(stu);
		temp.put("scale", MathUtils.get2Point(MathUtils.getDivisionResult(
				count, stucount, 4) * 100));
		temp.put("change", count);
		return temp;
	}

//	@Override
//	public Map<String, Object> getLastWeekData(
//			List<AdvancedParam> advancedParamList, String start, Integer weeks) {
//		return getLastWeekData(getDeptDataList(), advancedParamList, HIGHCOST,
//				start, weeks);
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public Map<String, Object> getLastWeekData(List<String> deptList,
//			List<AdvancedParam> advancedParamList, String[] type, String start,
//			Integer weeks) {
//		Map<String, Object> params = getParamsMapByParams(advancedParamList,
//				null, null, start, weeks);
//		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
//				.get("stuAdvancedList"); // 高级查询-学生参数
//		weeks = MapUtils.getIntValue(params, "weekth");
//		start = MapUtils.getString(params, "start");
//		String[] ary = start.split("~");
//		start = ary[0];
//		String[] time = EduUtils.getSchoolYearTerm(start);
//		Map<String, Object> temp1 = getLastWeekth(start, weeks);
//		String oldyear = MapUtils.getString(temp1, "schoolyear"), oldterm = MapUtils
//				.getString(temp1, "term");
//		int oldweekth = MapUtils.getIntValue(temp1, "weekth");
//		int count = stuCostDao.queryCountByDept(time[0], time[1], weeks, type,
//				deptList, stuAdvancedList);
//		int year = Integer.parseInt(time[0].substring(0, 4));
//		int stucount = businessDao.queryStuCount(year, deptList,
//				stuAdvancedList);
//		int oldcount = stuCostDao.queryCountByDept(oldyear, oldterm, oldweekth,
//				type, deptList, stuAdvancedList);
//		String change = oldcount == 0 ? "--" : String.valueOf(MathUtils
//				.get2Point(MathUtils.getDivisionResult((count - oldcount),
//						oldcount, 4) * 100));
//		Map<String, Object> result = DevUtils.MAP();
//		result.put("count", count);
//		result.put(
//				"scale",
//				stucount == 0 ? 0 : MathUtils.get2Point(MathUtils
//						.getDivisionResult(count, stucount, 4) * 100));
//		result.put("change", change);
//		return result;
//	}
	@Override
	public Map<String, Object> getLastMonthData(
			List<AdvancedParam> advancedParamList, String schoolYear,String termCode, Integer month) {
		return getLastMonthData(getDeptDataList(), advancedParamList, HIGHCOST,
				 schoolYear,termCode,  month);
	}

	@Override
	public Map<String, Object> getLastMonthData(List<String> deptList,
			List<AdvancedParam> advancedParamList, String[] type,  String schoolYear,String termCode, Integer month) {
		List<AdvancedParam> stuAdvancedList = advancedParamList==null?new ArrayList<AdvancedParam>():advancedParamList; 
		Map<String, Object> result = DevUtils.MAP();
		if (schoolYear !=null){
			Map<String, Object> temp1 = getLastMonth(schoolYear, termCode, month);
			String oldyear = MapUtils.getString(temp1, "schoolyear"), oldterm = MapUtils
					.getString(temp1, "term");
			int oldmonth = MapUtils.getIntValue(temp1, "month");
			int year = Integer.parseInt(schoolYear.substring(0,4));
			int count = stuCostDao.getDeptCost(year, termCode, month, type, deptList, stuAdvancedList);;
			int stucount = businessDao.queryStuCount(year, deptList,
					stuAdvancedList);
			int oldcount = stuCostDao.getDeptCost(Integer.parseInt(oldyear.substring(0,4)), oldterm, oldmonth,
					type, deptList, stuAdvancedList);
			String change = oldcount == 0 ? "--" : String.valueOf(MathUtils
					.get2Point(MathUtils.getDivisionResult((count - oldcount),
							oldcount, 4) * 100));
			result.put("count", count);
			result.put(
					"scale",
					stucount == 0 ? 0 : MathUtils.get2Point(MathUtils
							.getDivisionResult(count, stucount, 4) * 100));
			result.put("change", change);
		}else{
			result.put("count", 0);
			result.put("scale",0);
			result.put("change", "--");
		}
		return result;
	}
	private List<Map<String, Object>> getTermData() {
		List<Map<String, Object>> xqlist = businessService.queryBzdmTermCode();
//		List<Map<String, Object>> result = getBzdmXnXq("T_STU_ABNORMAL_TERM",
//				"school_year", "term_code");
		List<Map<String, Object>> list = stuCostDao.queryStandardDate(),
				                  result = new ArrayList<Map<String,Object>>();
		list = (list != null && !list.isEmpty() && list.size() > 1)?list.subList(1, list.size()):new ArrayList<Map<String,Object>>();
		for (Map<String, Object> temp : list){
			Map<String, Object> temp1 = new HashMap<String, Object>();
			String xn = MapUtils.getString(temp, "schoolyear");
			String xq = MapUtils.getString(temp, "term");
			String term = xq.equals(Globals.TERM_FIRST)?"第一学期":"第二学期";
			temp1.put("id",xn + "+" +xq);
			temp1.put("mc",xn + "学年 "+ term + "");
			result.add(temp1);
		}
		if (result.isEmpty()) {
			Date day = new Date();
			String[] date = EduUtils.getSchoolYearTerm(day);
			int xzxq = Integer.parseInt(date[1]);
			int x = 0;
			for (int k = xzxq - 1; k > -1; k--) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",
						date[0] + "+" + MapUtils.getString(xqlist.get(k), "id"));
				map.put("mc",
						date[0] + "学年 "
								+ MapUtils.getString(xqlist.get(k), "mc") + "");
				result.add(x, map);
				x++;
			}
		}
		return result;
	}

/*	private List<Map<String, Object>> getYearList() {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String,Object> listMap = stuCostDao.queryStandardYear(),
		 map = new HashMap<String, Object>(),
		 map1 = new HashMap<String, Object>(),
		 map2 = new HashMap<String, Object>();
	    if(listMap == null || listMap.isEmpty()){
	    	return result; 
	    }
		Integer max = MapUtils.getInteger(listMap, "max"),
		min = MapUtils.getInteger(listMap,"min");
		if(max != null && min != null){
			map2.put("start", max);
			map2.put("end", max);
			map2.put("name", "本学年");
			result.add(map2);
		}
		if((max-min)>1){
			map.put("start", max-1);
			map.put("end", max);
			map.put("name", "近两学年");
			map1.put("start", max-2);
			map1.put("end", max);
			map1.put("name", "近三学年");
			result.add(map);
			result.add(map1);
		}else if((max-min)==1){
			map.put("start", max-1);
			map.put("end", max);
			map.put("name", "近两学年");
			result.add(map);
		}
		return result;
	}*/

	@Override
	public Map<String, Object> getTermByGrade(String schoolYear,
			String termCode, List<AdvancedParam> advancedParamList) {
		return getTermByGrade(schoolYear, termCode, HIGHCOST,
				getDeptDataList(), advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTermByGrade(String schoolYear,
			String termCode, String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				schoolYear, termCode, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		schoolYear = MapUtils.getString(params, "schoolYear");
		termCode = MapUtils.getString(params, "termCode");
		String column = "enroll_grade";
		List<Map<String, Object>> gradelist = stuCostDao.queryGrade(
				Integer.parseInt(schoolYear.substring(0, 4)), deptList,
				new ArrayList<AdvancedParam>());
		List<Map<String, Object>> list = getTermList(schoolYear, termCode,
				column, type, deptList, stuAdvancedList, gradelist);
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", list);
		return temp;
	}

	@Override
	public Map<String, Object> getYearByGrade(int start, int end,
			List<AdvancedParam> advancedParamList) {
		return getYearByGrade(start, end, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getYearByGrade(int start, int end,
			String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String column = "enroll_grade";
		List<Map<String, Object>> result = getYearList(start, end, column,
				type, deptList, stuAdvancedList, true);
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", result);
		return temp;
	}

	@Override
	public Map<String, Object> getTermBySex(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList) {
		return getTermBySex(schoolYear, termCode, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTermBySex(String schoolYear, String termCode,
			String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				schoolYear, termCode, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		schoolYear = MapUtils.getString(params, "schoolYear");
		termCode = MapUtils.getString(params, "termCode");
		String column = "sex_code";
		List<Map<String, Object>> sex = stuCostDao.querySexList();
		List<Map<String, Object>> list = getTermList(schoolYear, termCode,
				column, type, deptList, stuAdvancedList, sex);
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", list);
		return temp;
	}

	@Override
	public Map<String, Object> getYearBySex(int start, int end,
			List<AdvancedParam> advancedParamList) {
		return getYearBySex(start, end, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getYearBySex(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String column = "sex_code";
		List<Map<String, Object>> result = getYearList(start, end, column,
				type, deptList, stuAdvancedList, false);
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", result);
		return temp;
	}

	@Override
	public Map<String, Object> getTermByLoan(String schoolYear,
			String termCode, List<AdvancedParam> advancedParamList) {
		return getTermByLoan(schoolYear, termCode, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTermByLoan(String schoolYear,
			String termCode, String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				schoolYear, termCode, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		schoolYear = MapUtils.getString(params, "schoolYear");
		termCode = MapUtils.getString(params, "termCode");
		String table = " t_stu_loan ", name = "获助学贷款学生";
		Map<String, Object> temp = getTermListOther(name, schoolYear, termCode,
				table, type, deptList, stuAdvancedList);
		return temp;
	}

	@Override
	public Map<String, Object> getYearByLoan(int start, int end,
			List<AdvancedParam> advancedParamList) {
		return getYearByLoan(start, end, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getYearByLoan(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String table = " t_stu_loan ", name = "获助学贷款学生";
		Map<String, Object> map = getYearListOther(name, start, end, table,
				type, deptList, stuAdvancedList);
		return map;
	}

	@Override
	public Map<String, Object> getTermBySubsidy(String schoolYear,
			String termCode, List<AdvancedParam> advancedParamList) {
		return getTermBySubsidy(schoolYear, termCode, HIGHCOST,
				getDeptDataList(), advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTermBySubsidy(String schoolYear,
			String termCode, String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				schoolYear, termCode, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		schoolYear = MapUtils.getString(params, "schoolYear");
		termCode = MapUtils.getString(params, "termCode");
		String table = " t_stu_subsidy ", name = "获助学金学生";
		Map<String, Object> temp = getTermListOther(name, schoolYear, termCode,
				table, type, deptList, stuAdvancedList);
		return temp;
	}

	@Override
	public Map<String, Object> getYearBySubsidy(int start, int end,
			List<AdvancedParam> advancedParamList) {
		return getYearBySubsidy(start, end, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getYearBySubsidy(int start, int end,
			String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String table = " t_stu_subsidy ", name = "获助学金学生";
		Map<String, Object> map = getYearListOther(name, start, end, table,
				type, deptList, stuAdvancedList);
		return map;
	}

	@Override
	public Map<String, Object> getTermByJm(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList) {
		return getTermByJm(schoolYear, termCode, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTermByJm(String schoolYear, String termCode,
			String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				schoolYear, termCode, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		schoolYear = MapUtils.getString(params, "schoolYear");
		termCode = MapUtils.getString(params, "termCode");
		String table = " t_stu_jm ", name = "获学费减免学生";
		Map<String, Object> temp = getTermListOther(name, schoolYear, termCode,
				table, type, deptList, stuAdvancedList);
		return temp;
	}

	@Override
	public Map<String, Object> getYearByJm(int start, int end,
			List<AdvancedParam> advancedParamList) {
		return getYearByJm(start, end, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getYearByJm(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String table = " t_stu_jm ", name = "获学费减免学生";
		Map<String, Object> map = getYearListOther(name, start, end, table,
				type, deptList, stuAdvancedList);
		return map;
	}

	@Override
	public Map<String, Object> getGradeHistory(
			List<AdvancedParam> advancedParamList) {
		return getGradeHistory(HIGHCOST, getDeptDataList(), advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGradeHistory(String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String column = "enroll_grade";
		Map<String, Object> temp = getHistory(true, column, type, deptList,
				stuAdvancedList);
		return temp;
	}

	@Override
	public Map<String, Object> getSexHistory(
			List<AdvancedParam> advancedParamList) {
		return getSexHistory(HIGHCOST, getDeptDataList(), advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSexHistory(String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String column = "sex_code";
		Map<String, Object> temp = getHistory(false, column, type, deptList,
				stuAdvancedList);
		return temp;
	}

	@Override
	public Map<String, Object> getSubsidyHistory(
			List<AdvancedParam> advancedParamList) {
		return getSubsidyHistory(HIGHCOST, getDeptDataList(), advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSubsidyHistory(String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String table = " t_stu_subsidy ";
		Map<String, Object> temp = getOtherHistory(table, type, deptList,
				stuAdvancedList);
		return temp;
	}

	@Override
	public Map<String, Object> getLoanHistory(
			List<AdvancedParam> advancedParamList) {
		return getLoanHistory(HIGHCOST, getDeptDataList(), advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getLoanHistory(String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String table = " t_stu_loan ";
		Map<String, Object> temp = getOtherHistory(table, type, deptList,
				stuAdvancedList);
		return temp;
	}

	@Override
	public Map<String, Object> getJmHistory(
			List<AdvancedParam> advancedParamList) {
		return getJmHistory(HIGHCOST, getDeptDataList(), advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getJmHistory(String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		String table = " t_stu_jm ";
		Map<String, Object> temp = getOtherHistory(table, type, deptList,
				stuAdvancedList);
		return temp;
	}

	@Override
	public Map<String, Object> getTermByMeal(String schoolYear,
			String termCode, List<AdvancedParam> advancedParamList) {
		return getTermByMeal(schoolYear, termCode, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTermByMeal(String schoolYear,
			String termCode, String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				schoolYear, termCode, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		schoolYear = MapUtils.getString(params, "schoolYear");
		termCode = MapUtils.getString(params, "termCode");
		List<Map<String, Object>> result = stuCostDao.queryCountByCbdm(schoolYear, termCode, type,
				deptList, stuAdvancedList);
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", result);
		return map;
	}

	@Override
	public Map<String, Object> getYearByMeal(int start, int end,
			List<AdvancedParam> advancedParamList) {
		return getYearByMeal(start, end, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getYearByMeal(int start, int end, String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		for (int i = start; i < end + 1; i++) {
			String schoolYear = String.valueOf(i) + "-" + String.valueOf(i + 1);
			for(int j = 0;j<2;j++){
				String termCode = j == 0?Globals.TERM_FIRST:Globals.TERM_SECOND;
			    List<Map<String,Object>> backVal = stuCostDao.queryCountByCbdm(schoolYear, termCode, type, deptList, stuAdvancedList);
			    result.addAll(backVal);
			}
		}
		result = mergeList(result, true);
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", result);
		return map;
	}

	@Override
	public Map<String, Object> getMealHistory(
			List<AdvancedParam> advancedParamList) {
		return getMealHistory(HIGHCOST, getDeptDataList(), advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getMealHistory(String[] type,
			List<String> deptList, List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				null, null, null, null);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		List<Map<String, Object>> term = getTermData(), result = new ArrayList<Map<String, Object>>();
		for (int i = term.size() >4 ? 4 :term.size()-1; i >-1 ; i--) {
			if (i == 4) {
				break;
			}
			Map<String, Object> map = term.get(i);
			String str = MapUtils.getString(map, "id");
			String mc = MapUtils.getString(map, "mc");
			String[] time = str.split("[+]");
			String schoolYear = time[0], termCode = time[1];
			List<Map<String, Object>> backVal = stuCostDao.queryCountByCbdm(schoolYear, termCode, type,
					deptList, stuAdvancedList);
			int breakfast = 0,lunch = 0,dinner=0;
		    for(Map<String,Object> temp : backVal){
		    	String name = MapUtils.getString(temp, "name");
		    	temp.put("field", mc);
		    	if (Constant.Meal_Time_Group[0][0].equals(name)){
		    		breakfast = 1;
		    	}else if(Constant.Meal_Time_Group[1][0].equals(name)){
		    	    lunch = 1;	
		    	}else if(Constant.Meal_Time_Group[2][0].equals(name)){
		    		dinner = 1;
		    	}
		    }
		    if(breakfast == 0){
		    	Map<String,Object> bMap = new HashMap<String, Object>();
		    	bMap.put("field",mc);bMap.put("name",Constant.Meal_Time_Group[0][0]);bMap.put("value",0);
		    	backVal.add(bMap);
		    }
		    if(lunch == 0){
		    	Map<String,Object> lMap = new HashMap<String, Object>();
		    	lMap.put("field",mc);lMap.put("name",Constant.Meal_Time_Group[1][0]);lMap.put("value",0);
		    	backVal.add(lMap);
		    }
		    if(dinner == 0){
		    	Map<String,Object> dMap = new HashMap<String, Object>();
		    	dMap.put("field",mc);dMap.put("name",Constant.Meal_Time_Group[2][0]);dMap.put("value",0);
		    	backVal.add(dMap);
		    }
		    result.addAll(backVal);
		}
		Map<String, Object> typeMap = shiftList(result, "name", "field");
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", typeMap);
		return temp;
	}

	@Override
	public Map<String, Object> getTermCountDept(String schoolYear,
			String termCode, List<AdvancedParam> advancedParamList) {
		return getTermCountDept(schoolYear, termCode, HIGHCOST,
				getDeptDataList(), advancedParamList);
	}

	@Override
	public Map<String, Object> getYearCountDept(int start, int end,
			List<AdvancedParam> advancedParamList) {
		return getYearCountDept(start, end, HIGHCOST, getDeptDataList(),
				advancedParamList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTermCountDept(String schoolYear,
			String termCode, String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> params = getParamsMapByParams(advancedParamList,
				schoolYear, termCode, null, null);
		schoolYear = MapUtils.getString(params, "schoolYear");
		termCode = MapUtils.getString(params, "termCode");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) params
				.get("stuAdvancedList"); // 高级查询-学生参数
		List<Map<String, Object>> result = queryDeptCount(schoolYear, termCode,
				type, deptList, stuAdvancedList);
		Map<String, Object> temp1 = DevUtils.MAP();
		temp1.put("list", result);
		return temp1;
	}

	@Override
	public Map<String, Object> getYearCountDept(int start, int end,
			String[] type, List<String> deptList,
			List<AdvancedParam> advancedParamList) {
		List<AdvancedParam> stuAdvancedList = advancedParamList == null ? new ArrayList<AdvancedParam>()
				:advancedParamList; // 高级查询-学生参数
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = start; i < end + 1; i++) {
			String schoolYear = String.valueOf(i) + "-" + String.valueOf(i + 1);
			for (int j =0;j<2;j++){
				String termCode = j==0 ? Globals.TERM_FIRST:Globals.TERM_SECOND;
			List<Map<String, Object>> list = queryDeptCount(schoolYear, termCode,
					type, deptList, stuAdvancedList);
			result.addAll(list);
			}
		}
		result = mergeList(result, false);
		for (Map<String, Object> map : result) {
			int count = MapUtils.getIntValue(map, "count");
			int stucount = MapUtils.getIntValue(map, "stucount");
			Double scale = stucount == 0 ? 0.0 : MathUtils.get2Point(MathUtils
					.getDivisionResult(count, stucount, 4) * 100);
			map.put("scale", scale);
		}
		Map<String, Object> temp1 = DevUtils.MAP();
		temp1.put("list", result);
		return temp1;
	}

	@Override
	public void exportData(String mc, String schoolYear,String termCode, Integer month, String pid,
			List<AdvancedParam> advancedParamList, HttpServletResponse response) {
		exportData(mc,schoolYear,termCode, month, pid, HIGHCOST, advancedParamList,
				response);
	}

	@Override
	public void exportData(String mc, String schoolYear,String termCode, Integer month, String pid,
			String[] type, List<AdvancedParam> advancedParamList,
			HttpServletResponse response) {
		List<AdvancedParam> stuAdvancedList = advancedParamList== null ? new ArrayList<AdvancedParam>():advancedParamList;
		List<String> deptList = getDeptDataList(pid);
		List<Map<String, Object>> list = stuCostDao.queryExportData(schoolYear,
				termCode, month, type, deptList, stuAdvancedList,type[3]);
		String title = mc.substring(0, mc.indexOf(":"));
		mc = mc.replace(":", " ");
		String fileName = title + type[2];

		response.setContentType("application/octet-stream; charset=utf-8");
		response.setCharacterEncoding("utf-8");
		if (fileName == null)
			fileName = "下载文件";
		try {
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		HSSFWorkbook workBook = ExcelUtils.createExcel(list,
				EXPORT_EXCEL_FIELD, EXPORT_EXCEL_HEARD, mc);
		try {
			OutputStream os = response.getOutputStream();
			workBook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public Map<String, Object> getExportData(String sendType, String mc,
			String schoolYear,String termCode, Integer month, String pid,
			List<AdvancedParam> advancedParamList, HttpServletRequest request) {
		return getExportData(sendType, mc, schoolYear,termCode,month, pid, HIGHCOST,
				advancedParamList, request);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getExportData(String sendType, String mc,
			String schoolYear,String termCode, Integer month, String pid, String[] type,
			List<AdvancedParam> advancedParamList, HttpServletRequest request) {
		String title = "";
		try {
			mc = URLDecoder.decode(mc.trim(), "UTF-8");
			title = mc.substring(0, mc.indexOf(":"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filename = request.getSession().getServletContext()
				.getRealPath("/static/email/" + title + type[2] + ".xls");
		List<AdvancedParam> stuAdvancedList =advancedParamList == null? new ArrayList<AdvancedParam>():advancedParamList ; // 高级查询-学生参数
		List<String> deptList = getDeptDataList(pid);
		List<Map<String, Object>> list = stuCostDao.queryExportData(schoolYear,
				termCode, month, type, deptList, stuAdvancedList,type[3]);
		String emailFileName = null;
		List<Map<String, Object>> emailList = stuCostDao.queryEmailList();// 得到所有院系的email
		for (int i = 0; i < emailList.size(); i++) {
			if (pid.equals(MapUtils.getString(emailList.get(i), "ID"))) {
				emailFileName = MapUtils.getString(emailList.get(i), "EMAIL");
			}
		}
		HSSFWorkbook workBook = ExcelUtils.createExcel(list,
				EXPORT_EXCEL_FIELD, EXPORT_EXCEL_HEARD, title + type[2] + "名单");
		try {
			FileOutputStream os = new FileOutputStream(new String(
					filename.getBytes("utf-8"), "utf-8"));
			workBook.write(os);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> reMap = new HashMap<>();
		if (!sendType.equals("")) {
			reMap.put("fh", "发送失败!");
			reMap.put("status", 0);
		}
		if ("only".equals(sendType)) {
			try {

				if (MailUtils.send(emailFileName, filename, title + type[2])) {
					reMap.put("fh", "发送成功!");
					reMap.put("status", 1);
				} else {

				}
			} catch (Exception e) {

			}
		} else if ("all".equals(sendType)) {
		}
		JobResultBean result = saveSendStatus(schoolYear, termCode, month,
				MapUtils.getIntValue(reMap, "status"), pid, title, type);
		reMap.put("list", result);
		return reMap;
	}

	@Override
	public void excelAll(HttpServletRequest request,
			HttpServletResponse response) {
		String filename = request.getSession().getServletContext()
				.getRealPath("/static/email/");
		String filenameZip = filename + "\\email.zip";
		File file = new File(filenameZip);
		if (!file.exists()) {
			ZipUtils zca = new ZipUtils(filenameZip);
			zca.compressExe(filename);
		} else {
			file.delete();
			ZipUtils zca = new ZipUtils(filenameZip);
			zca.compressExe(filename);
		}
		try {
			response.setContentType("application/x-execl");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(("email.zip").getBytes(), "UTF-8"));
			// 读取文件
			InputStream in = new FileInputStream(filenameZip);
			ServletOutputStream outputStream = response.getOutputStream();
			// 写文件
			int b;
			while ((b = in.read()) != -1) {
				outputStream.write(b);
			}

			in.close();
			outputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 全部发送
	 */
	@Override
	public Map<String, Object> sendAll(HttpServletRequest request) {
		Map<String, Object> reMap = new HashMap<>();
		String filename = request.getSession().getServletContext()
				.getRealPath("/static/email/");
		String filenameZip = filename + "\\email.zip";
		File file = new File(filenameZip);
		reMap.put("fh", "发送失败!");
		if (!file.exists()) {
			ZipUtils zca = new ZipUtils(filenameZip);
			zca.compressExe(filename);
		} else {
			file.delete();
			ZipUtils zca = new ZipUtils(filenameZip);
			zca.compressExe(filename);
		}
		String emailFileName = null;
		List<Map<String, Object>> emailList = stuCostDao.queryEmailList();// 得到所有院系的email

		try {
			for (int i = 0; i < emailList.size(); i++) {
				emailFileName = (String) emailList.get(i).get("EMAIL");

				if (MailUtils.send(filenameZip, emailFileName)) {
					reMap.put("fh", "发送成功!");
					// return reMap;
				} else {
					// return reMap;
				}
			}
			return reMap;
		} catch (Exception e) {
			return reMap;
		}
	}

	@Override
	public JobResultBean saveSendStatus(String schoolYear, String termCode, int month,
			int status, String deptid, String deptname, String[] type) {
		Date date = new Date();
		String send_date = DateUtils.date2String(date);
		String school_year = schoolYear;
		String term_code = termCode;
		try {
			deptname = URLDecoder.decode(deptname.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name_ = deptname + type[2] + "名单.xls";
		TStuAbnormalMailMonth params = new TStuAbnormalMailMonth();
		params.setName_(name_);
		params.setSend_date(send_date);
		params.setDept_id(deptid);
		params.setStatus_(status);
		params.setType_(type[3]);
		params.setSchool_year(school_year);
		params.setTerm_code(term_code);
		params.setMonth(month);
		List<TStuAbnormalMailMonth> list = new ArrayList<TStuAbnormalMailMonth>();
		list.add(params);
		if (!list.isEmpty()) {
			String oldSql = " t_stu_abnormal_mail_month where school_year = '"
					+ school_year + "' and term_code = '" + term_code + "'"
					+ " and month = " + month + " and dept_id = '" + deptid
					+ "' and type_ = '" + type[3] + "'";
			int oldCount = baseDao
					.queryForInt("select count(0) from " + oldSql);
			if (oldCount > 0) {
				baseDao.delete("delete from " + oldSql);
				info("已删除" + school_year + "学年" + term_code + "学期 第" + month
						+ "月" + deptname + type[2] + oldSql + "");
			}
		}
		JobResultBean result = doSendStatus(list, "保存发送状态");
		return result;
	}

	@Override
	public Map<String, Object> getCostCode() {
		List<String> EXCEL_FIELD = EXPORT_EXCEL_FIELD;
		List<String> EXCEL_HEARD = EXPORT_EXCEL_HEARD;
		String Abnormal = Abnormal_Type;
		String[] type = HIGHCOST;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("field", EXCEL_FIELD);
		map.put("heard", EXCEL_HEARD);
		map.put("abnormal", Abnormal);
		map.put("type", type);
		return map;
	}

	@Override
	public Map<String, Object> getStuDetail(
			List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields) {
		return getStuDetail(advancedParamList, page, keyValue, fields, HIGHCOST);
	}

	@Override
	public Map<String, Object> getStuDetail(
			List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields, String[] type) {
		String sql = getStuDetailSql(advancedParamList, keyValue, fields, type);
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(sql, page);
		return map;
	}

	@Override
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields) {
		return getStuDetailList(advancedParamList, keyValue, fields, HIGHCOST);
	}

	@Override
	public List<Map<String, Object>> getStuDetailList(
			List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields, String[] type) {
		String sql = getStuDetailSql(advancedParamList, keyValue, fields, type);
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		return list;
	}

	// public Map<String,Object> getDeptHistory(List<AdvancedParam>
	// advancedParamList){
	// return getDeptHistory(HIGHCOST,getDeptDataList(),advancedParamList);
	// }
	// public Map<String,Object> getDeptHistory(String[] type,List<String>
	// deptList,List<AdvancedParam> advancedParamList){
	// List<Map<String,Object>> year =getTwoYear(),
	// term =getFourTerm(year),
	// result = new ArrayList<Map<String,Object>>();
	// List<Map<String,Object>> list = getTermCountDept(schoolYear, termCode,
	// type, deptList, advancedParamList);
	// return null;
	// }
	private List<Map<String, Object>> queryDeptCount(String schoolYear,
			String termCode, String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList) {
		List<Map<String, Object>> result = stuCostDao.queryCountByDept(schoolYear, termCode,  type, deptList, stuAdvancedList);
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
				String x1 = MapUtils.getString(o1, compareField),
					   x2 = MapUtils.getString(o2, compareField);
				if (x1.equals("--")) {
					return -1;
				}
				if (x2.equals("--")) {
					return -1;
				}
				Double count1 = MapUtils.getDouble(o1, compareField), count2 = MapUtils
						.getDouble(o2, compareField);
				int flag = 0, pare = asc ? 1 : -1; // 正序为1
				if (count1 > count2)
					flag = pare;
				else if (count1 < count2)
					flag = -pare;
				return flag;
			}
		});
	}

//	private Map<String, Object> getLastWeekth(String start, int weekth) {
//		String[] time = EduUtils.getSchoolYearTerm(start);
//		String oldyear = time[0], oldterm = time[1];
//		int oldweekth = weekth - 1;
//		if (weekth == 1) {
//			Date date = new Date();
//			date = DateUtils.string2Date(start);
//			String[] time1 = EduUtils.getProSchoolYearTerm(date);
//			oldyear = time1[0];
//			oldterm = time1[1];
//			Map<String, Object> sb = stuCostDao.queryLastWeekth(oldyear,
//					oldterm);
//			String start1 = MapUtils.getString(sb, "startdate");
//			String end1 = MapUtils.getString(sb, "enddate");
//			if (start1 == null || end1 == null) {
//				oldweekth = 0;
//			} else {
//				Date begin = DateUtils.string2Date(start1);
//				if ( getWeekNo(begin) > 5){
//					begin = getNextDayByLen(begin, (7 - getWeekNo(begin)+1));
//				}
//				Date last = DateUtils.string2Date(end1);
//				oldweekth = DateUtils.getZcByDateFromBeginDate(begin, last);
//			}
//		}
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("schoolyear", oldyear);
//		result.put("term", oldterm);
//		result.put("weekth", oldweekth);
//		return result;
//	}
    private Map<String, Object> getLastMonth(String schoolYear,String termCode, int month) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (month == 1){
			if(Globals.TERM_FIRST.equals(termCode)){
				int year = Integer.parseInt(schoolYear.substring(0,4))-1;
				schoolYear = String.valueOf(year)+"-"+String.valueOf(year+1);
				termCode =Globals.TERM_SECOND; 
			}else{
				termCode =Globals.TERM_FIRST; 
			}
			month = stuCostDao.queryMaxMonth(schoolYear, termCode);
		}else{
			month = month-1;
		}
		result.put("schoolyear", schoolYear);
		result.put("term", termCode);
		result.put("month", month);
		return result;
    }
	/**
	 * 合并list中map的value相同的
	 * 
	 * @param list
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> mergeList(List<Map<String, Object>> list,
			Boolean bs) {
		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			Map<String, Object> newMap = bs ? isExistSame(list, map, retList)
					: isExistSame1(list, map, retList);
			if (null == newMap) {
				continue;
			} else {
				retList.add(newMap);
			}
		}
		return retList;
	}

	/**
	 * value 相同的map合并
	 * 
	 * @param i
	 * @param value
	 * @param name
	 * @param list
	 * @return Map<String,Object>
	 */
	private Map<String, Object> isExistSame(List<Map<String, Object>> list,
			Map<String, Object> map, List<Map<String, Object>> result) {
		int val = 0;
		String name = MapUtils.getString(map, "name");
		for (Map<String, Object> temp : result) {
			String mc = MapUtils.getString(temp, "name");
			if (mc.equals(name)) {
				return null;
			}
		}
		for (int j = 0; j < list.size(); j++) {
			Map<String, Object> m = list.get(j);
			int value = MapUtils.getIntValue(m, "value");
			String name1 = MapUtils.getString(m, "name");
			if (name.equals(name1)) {
				val += value;
			}
		}
		map.put("value", val);
		return map;
	}

	/**
	 * value 相同的map合并
	 * 
	 * @param i
	 * @param value
	 * @param name
	 * @param list
	 * @return Map<String,Object>
	 */
	private Map<String, Object> isExistSame1(List<Map<String, Object>> list,
			Map<String, Object> map, List<Map<String, Object>> result) {
		int count = 0, stucount = 0;
		String name = MapUtils.getString(map, "name");
		for (Map<String, Object> temp : result) {
			String mc = MapUtils.getString(temp, "name");
			if (mc.equals(name)) {
				return null;
			}
		}
		for (int j = 0; j < list.size(); j++) {
			Map<String, Object> m = list.get(j);
			int val1 = MapUtils.getIntValue(m, "count");
			int val2 = MapUtils.getIntValue(m, "stucount");
			String name1 = MapUtils.getString(m, "name");
			if (name.equals(name1)) {
				count += val1;
				stucount += val2;
			}
		}
		map.put("count", count);
		map.put("stucount", stucount);
		return map;
	}

	private List<Map<String, Object>> gradeShift(
			List<Map<String, Object>> list, List<Map<String, Object>> gradelist) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(list == null || list.isEmpty()){return result;}
		for (int i = 0; i < gradelist.size(); i++) {
			String code = MapUtils.getString(gradelist.get(i), "code");
			int id = MapUtils.getIntValue(gradelist.get(i), "name");
			String mc = MapUtils.getString(gradelist.get(i), "value");
			int field = MapUtils.getIntValue(gradelist.get(i), "field");
			for (Map<String, Object> map : list) {
				int grade = MapUtils.getIntValue(map, "name");
				int value = MapUtils.getIntValue(map, "value");
				Map<String, Object> temp = new HashMap<String, Object>();
				if (id == grade) {
					temp.put("name", mc);
					temp.put("value", value);
					temp.put("code", code);
					result.add(temp);
				}
			}
			if (result.size() < i + 1) {
				Map<String, Object> temp1 = new HashMap<String, Object>();
				temp1.put("name", mc);
				temp1.put("value", field);
				temp1.put("code", code);
				result.add(temp1);// 如果放入过后result的长度小于list1的长度，就用list1的结果填充result
			}
		}
		return result;
	}

	private List<Map<String, Object>> getYearList(int start, int end,
			String column, String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList, Boolean isGrade) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = start; i < end + 1; i++) {
			String schoolYear = String.valueOf(i) + "-" + String.valueOf(i + 1);
			List<Map<String, Object>> list = stuCostDao.queryTermData(
					schoolYear,Globals.TERM_FIRST,column, type, deptList, stuAdvancedList);
			List<Map<String, Object>> list1 = stuCostDao.queryTermData(
					schoolYear,Globals.TERM_SECOND,column, type, deptList, stuAdvancedList);
			list = list == null ? new ArrayList<Map<String,Object>>():list;
			list1 = list1 == null ? new ArrayList<Map<String,Object>>():list1;
			list.addAll(list1);
			List<Map<String, Object>> gradelist = new ArrayList<Map<String, Object>>();
			if (isGrade) {
				gradelist = stuCostDao.queryGrade(i, deptList,
						new ArrayList<AdvancedParam>());
				list = gradeShift(list, gradelist);
			}
			if (list != null && !list.isEmpty()) {
				result.addAll(list);
			}
		}
		result = mergeList(result, true);
		List<Map<String, Object>> sex = new ArrayList<Map<String, Object>>();
		if (!isGrade) {
			sex = stuCostDao.querySexList();
			result = gradeShift(result, sex);
		}
		Iterator<Map<String, Object>> ite = result.iterator();
		while (ite.hasNext()) {
			Map<String, Object> m = ite.next();
			if (MapUtils.getIntValue(m, "value") == 0) {
				ite.remove();
			}
		}
		return result;
	}

	private List<Map<String, Object>> getTermList(String schoolYear,
			String termCode, String column, String[] type,
			List<String> deptList, List<AdvancedParam> stuAdvancedList,
			List<Map<String, Object>> bzdm) {
		List<Map<String, Object>> list = stuCostDao.queryTermData(schoolYear,
				termCode, column, type, deptList, stuAdvancedList);
		list = gradeShift(list, bzdm);
		Iterator<Map<String, Object>> ite = list.iterator();
		while (ite.hasNext()) {
			Map<String, Object> m = ite.next();
			if (MapUtils.getIntValue(m, "value") == 0) {
				ite.remove();
			}
		}
		return list;
	}

	private Map<String, Object> getTermListOther(String name,
			String schoolYear, String termCode, String table, String[] type,
			List<String> deptList, List<AdvancedParam> stuAdvancedList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int inner = stuCostDao.queryTermByOther(schoolYear, termCode, table,
				type, deptList, stuAdvancedList),
				subsidy = stuCostDao.queryCount(table, schoolYear, null, null, deptList,
						stuAdvancedList),
				cost = stuCostDao.queryCount("t_stu_abnormal_term", schoolYear, termCode, type, deptList,
				stuAdvancedList);
		Double scale = subsidy == 0 ? 0.0 : MathUtils.get2Point(MathUtils
				.getDivisionResult(inner, subsidy, 4) * 100);
		Map<String, Object> innerMap = new HashMap<String, Object>(), subsidyMap = new HashMap<String, Object>(), costMap = new HashMap<String, Object>();
		innerMap.put("name", "公共");
		innerMap.put("value", inner);
		innerMap.put("code", "3");
		subsidyMap.put("name", name);
		subsidyMap.put("value", subsidy);
		subsidyMap.put("code", "1");
		costMap.put("name", type[2]);
		costMap.put("value", cost);
		costMap.put("code", "2");
		if (inner > 0 || subsidy > 0 || cost > 0) {
			result.add(subsidyMap);
			result.add(costMap);
			result.add(innerMap);
		}
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", result);
		temp.put("scale", scale);
		temp.put("count", inner);
		return temp;
	}

	private Map<String, Object> getYearListOther(String name, int start,
			int end, String table, String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int a = 0, b = 0, c = 0;
		for (int i = start; i < end + 1; i++) {
			String schoolYear = String.valueOf(i) + "-" + String.valueOf(i + 1);
			for (int j=0;j<2;j++){
				String termCode = j==0?Globals.TERM_FIRST:Globals.TERM_SECOND;
			int inner = stuCostDao.queryTermByOther(schoolYear, termCode,table, type,
					deptList, stuAdvancedList),
					subsidy = stuCostDao.queryCount(table, schoolYear, null, null, deptList,
					stuAdvancedList), 
					cost = stuCostDao.queryCount("t_stu_abnormal_term", schoolYear, termCode, type, deptList,
					stuAdvancedList);
			a += inner;
			b += subsidy;
			c += cost;
			}
		}
		Double scale = b == 0 ? 0.0 : MathUtils.get2Point(MathUtils
				.getDivisionResult(a, b, 4) * 100);
		Map<String, Object> innerMap = new HashMap<String, Object>(), subsidyMap = new HashMap<String, Object>(), costMap = new HashMap<String, Object>();
		subsidyMap.put("name", name);
		subsidyMap.put("value", b);
		subsidyMap.put("code", "1");
		costMap.put("name", type[2]);
		costMap.put("value", c);
		costMap.put("code", "2");
		innerMap.put("name", "公共");
		innerMap.put("value", a);
		innerMap.put("code", "3");
		if (a > 0 || b > 0 || c > 0) {
			result.add(subsidyMap);
			result.add(costMap);
			result.add(innerMap);
		}
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", result);
		temp.put("scale", scale);
		temp.put("count", a);
		return temp;
	}


	private Map<String, Object> shiftList(List<Map<String, Object>> result,
			String a, String b) {// a是X轴数据,b是图例数据
		/*
		 * 此方法用来转换多图例的数据，result的数据格式是 [{name:'sb',field:'jb',value:'2b'},
		 * {name:'sb',field:'jb',value:'2b'}, {name:'sb',field:'jb',value:'2b'}]
		 * a指的是result里的哪个属性的值是前台要展示的图例， b指的是result里那个属性的值是前台要展示的X轴的数据
		 */
		Map<String, Object> typeMap = new HashMap<>();
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
		List<String> legend_ary = new ArrayList<>(), value_ary = new ArrayList<>();
		List<String> field_ary = new ArrayList<>();
		for (Map<String, Object> map2 : result) {
			String legend = MapUtils.getString(map2, a);
			String value = MapUtils.getString(map2, a);
			String field = MapUtils.getString(map2, b);
			if (!legend_ary.contains(legend)) {
				legend_ary.add(legend);
			}
			if (!value_ary.contains(value)) {
				value_ary.add(value);
			}
			if (!field_ary.contains(field)) {
				field_ary.add(field);
			}
		}
		for (String k : field_ary) {
			Map<String, Object> map3 = new HashMap<>();
			map3.put("name", k);
			for (int l = 0; l < result.size(); l++) {
				Map<String, Object> map4 = result.get(l);
				if (map4.get(b).toString().equals(k)) {
					map3.put(MapUtils.getString(map4, a),
							MapUtils.getInteger(map4, "value"));
				}
			}
			list3.add(map3);
		}
		typeMap.put("legend_ary", legend_ary);
		typeMap.put("value_ary", value_ary);
		typeMap.put("data", list3);
		return typeMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getHistory(Boolean isGrade, String column,
			String[] type, List<String> deptList,
			List<AdvancedParam> stuAdvancedList) {
		List<Map<String, Object>> term =  getTermData();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for (int i = term.size() >4 ? 4:term.size()-1; i >-1; i--) {
			if (i == 4) {
				break;
			}
			Map<String, Object> map = term.get(i);
			String str = MapUtils.getString(map, "id");
			String mc = MapUtils.getString(map, "mc");
			String[] time = str.split("[+]");
			String schoolYear = time[0], termCode = time[1];
			List<Map<String, Object>> bzdm = new ArrayList<Map<String, Object>>();
			if (isGrade) {
				bzdm = stuCostDao.queryGrade(
						Integer.parseInt(schoolYear.substring(0, 4)), deptList,
						new ArrayList<AdvancedParam>());
			} else {
				bzdm = stuCostDao.querySexList();
			}
			List<Map<String, Object>> list = getTermList(schoolYear, termCode,
					column, type, deptList, stuAdvancedList, bzdm);
			for (Map<String, Object> map1 : list) {
				map1.put("field", mc);
			}
			result.addAll(list);
		}
		Map<String, Object> typeMap = shiftList(result, "name", "field");
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", typeMap);
		return temp;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getOtherHistory(String table, String[] type,
			List<String> deptList, List<AdvancedParam> stuAdvancedList) {
		List<Map<String, Object>> term = getTermData(), result = new ArrayList<Map<String, Object>>();
		for (int i = term.size() >4 ? 4:term.size()-1; i >-1; i--) {
			if (i == 4) {
				break;
			}
			Map<String, Object> map = term.get(i);
			String str = MapUtils.getString(map, "id");
			String mc = MapUtils.getString(map, "mc");
			String[] time = str.split("[+]");
			String schoolYear = time[0], termCode = time[1];
			int inner = stuCostDao.queryTermByOther(schoolYear, termCode,
					table, type, deptList, stuAdvancedList), subsidy = stuCostDao
					.queryCount(table, schoolYear, null, null, deptList,
							stuAdvancedList);
			Double scale = subsidy == 0 ? 0.0 : MathUtils.get2Point(MathUtils
					.getDivisionResult(inner, subsidy, 4) * 100);
			Map<String, Object> bakval = new HashMap<String, Object>();
			bakval.put("name", mc);
			bakval.put("scale", scale);
			bakval.put("count", inner);
			// bakval1.put("name", mc);bakval1.put("field",
			// "占比");bakval1.put("value", scale);
			result.add(bakval);
		}
		Map<String, Object> temp = DevUtils.MAP();
		temp.put("list", result);
		return temp;
	}

	/**
	 * 根据参数优化参数
	 * 
	 * @param schoolYear
	 *            学年
	 * @return Map<String,Object>
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getParamsMapByParams(
			List<AdvancedParam> advancedParamList, String schoolYear,
			String termCode, String start, Integer weekth) {
//		String day = DateUtils.getNowDate();
		List<Map<String, Object>> termList = getTermData();
		Map<String, Object> termMap =termList.get(0);
		String[] xnxq = MapUtils.getString(termMap,"id").split("[+]");
		schoolYear = schoolYear == null ? xnxq[0] : schoolYear;
		termCode = termCode == null ? xnxq[1] : termCode;
//		List<Map<String, Object>> timeList = (List<Map<String, Object>>) getTimeSelectList()
//				.get("list");
//		String[] timeAry = MapUtils.getString(timeList.get(0), "id").split(
//				"[+]");
//		weekth = weekth == null ? Integer.parseInt(timeAry[1]) : weekth;
//		start = start == null ? timeAry[0] : start;
		List<String> deptList = getDeptDataList();
		advancedParamList = advancedParamList != null ? advancedParamList
				: new ArrayList<AdvancedParam>();
		List<AdvancedParam> stuAdvancedList = AdvancedUtil
				.getAdvancedParamStu(advancedParamList), businessAdvancedList = AdvancedUtil
				.getAdvancedParamBusiness(advancedParamList);
		// return
		Map<String, Object> map = new HashMap<>();
		map.put("advancedParamList", advancedParamList);
		map.put("stuAdvancedList", stuAdvancedList);
		map.put("businessAdvancedList", businessAdvancedList);
		map.put("deptList", deptList);
		map.put("schoolYear", schoolYear);
		map.put("termCode", termCode);
//		map.put("start", start);
//		map.put("weekth", weekth);
		return map;
	}

	/**
	 * 获取len天后的日期
	 * 
	 * @param date
	 *            eg：2015-01-01
	 * @param len
	 *            eg：10
	 * @return String eg：2015-01-11
	 */
	private Date getNextDayByLen(Date date, int len) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, len);
		date = cal.getTime();
		return date;
	}

	/**
	 * 判定给定日期是星期几(阿拉伯数字) (1，星期一；2，星期二...7，星期日)
	 * 
	 * @param date
	 *            日期格式：yyyy-MM-dd
	 * @return (1、2、3...7)
	 * @throws ParseException
	 */
	private int getWeekNo(Date date) {
		Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(date);
		int weekday = calendar.get(java.util.Calendar.DAY_OF_WEEK);
		return (5 + weekday) % 7 + 1;
	}

	private JobResultBean doSendStatus(List<TStuAbnormalMailMonth> list,
			String jobName) {
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
		try {
			if (!list.isEmpty()) {
				hibernateDao.saveAllAutoCommit(list);
				info("插入" + jobName + list.size() + " 条");
			} else {
				info("插入0条");
			}
			String info = jobName + "结束执行,共耗时 ： "
					+ new Double(System.currentTimeMillis() - beginTime) / 1000
					+ " 秒 ";
			end(info);
			result.setIsTrue(true);
			result.setMsg(info);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			String info = "#" + jobName + "#数据保存出错," + e.getStackTrace();
			error(info);
			result.setIsTrue(false);
			result.setMsg(info);
			e.printStackTrace();
		}
		return result;
	}

	private void begin(String info) {
		log.warn("======== begin[" + DateUtils.getNowDate2() + "]: " + info
				+ " 初始化 ========");
	}

	private void info(String info) {
		log.warn("======== info[" + DateUtils.getNowDate2() + "]: " + info
				+ " ========");
	}

	private void end(String info) {
		log.warn("======== end[" + DateUtils.getNowDate2() + "]: " + info
				+ " ========");
	}

	private void error(String info) {
		log.warn("======== error[" + DateUtils.getNowDate2() + "]: " + info
				+ " 停止执行========");
	}

	private String getStuDetailSql(List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields, String[] type_) {
		String schoolyear = null;String[] yearList = null;
		String stuDetailSql = null;
		int count = 0,xxx =0;
		for (Map.Entry<String, Object> entry : keyValue.entrySet()){
			if (Type_IsYear.equals(entry.getKey())) {
				count++;
			}
			if (Type_Schoolyear.equals(entry.getKey())) {
				schoolyear = (String) entry.getValue();
				yearList = schoolyear.split(",");
				count++;
			}
		}
		if (keyValue.containsKey(Type_Term) &&count < 2){
			stuDetailSql = getStuDetailSql1(advancedParamList, keyValue, fields, type_);
		}else{
			for(String xx : yearList){
				keyValue.put(Type_IsTerm, 1);
				keyValue.put(Type_Schoolyear, xx);
				for(int yy = 0;yy<2;yy++){
				keyValue.put(Type_Term, yy==0?Globals.TERM_FIRST:Globals.TERM_SECOND);
				String zz = getStuDetailSql1(advancedParamList, keyValue, fields, type_);
				stuDetailSql =xxx==0?zz:stuDetailSql+ (zz.equals("")?"":" union all ")+zz;
						xxx++;
				}
			}
		}
		stuDetailSql = "select " + StringUtils.join(fields, ",")
				+ " from (" + stuDetailSql + ") order by "
				+ StringUtils.join(fields, ",");
		return stuDetailSql;
	}

	private AdvancedParam getDetailAdp(String type, String value) {
		AdvancedParam adp = null;
		if (type != null) {
			String group = AdvancedUtil.Group_Stu, code = null;
			switch (type) {
			case AdvancedUtil.Stu_GRADE:
				group = AdvancedUtil.Group_Stu;
				code = AdvancedUtil.Stu_GRADE;
				break;
			case AdvancedUtil.Common_SEX_CODE:
				group = AdvancedUtil.Group_Common;
				code = AdvancedUtil.Common_SEX_CODE;
				break;
			case AdvancedUtil.Common_DEPT_TEACH_ID:
				group = AdvancedUtil.Group_Common;
				code = AdvancedUtil.Common_DEPT_TEACH_ID;
				break;
			default:
				break;
			}
			adp = new AdvancedParam(group, code, value);
		}
		return adp;
	}
	private String getStuDetailSql1(List<AdvancedParam> advancedParamList,
			Map<String, Object> keyValue, List<String> fields, String[] type_){
		List<String> deptList = getDeptDataList(); // 权限
		String schoolyear = null, term = null, xfsql = "", tp = Constant.Type_DayCost_Standard;
		Integer weekth = null;Boolean ismeal = false;
		List<String> matchList = new ArrayList<>(), matchList1 = new ArrayList<String>(), joinList = new ArrayList<>();
		List<AdvancedParam> stuAdvancedList = AdvancedUtil
				.getAdvancedParamStu(advancedParamList);
		if (keyValue != null) {
			for (Map.Entry<String, Object> entry : keyValue.entrySet()) {
				AdvancedUtil.add(stuAdvancedList,getDetailAdp(entry.getKey(),String.valueOf(entry.getValue())));
				if (entry.getValue() != null && !"".equals(entry.getValue())) {
					if (Type_IsTerm.equals(entry.getKey())) {
						if (xfsql.equals("")) {
							xfsql = "select t.stu_id,t.sum_money from t_stu_abnormal_term t";
						}
					}
					if (Type_IsWeek.equals(entry.getKey())) {
						xfsql = "select t.stu_id,t.sum_money from t_stu_abnormal_month_result t";
					}
					if (Type_IsMeal.equals(entry.getKey())) {
						ismeal=true;
						xfsql = "select t.stu_id,t.sum_money from t_stu_abnormal_meal t";
					}
					if (Type_Start.equals(entry.getKey())) {
						String time = (String) entry.getValue();
						String[] ary = time.split("~");
						String[] timeAry = EduUtils.getSchoolYearTerm(ary[0]);
						schoolyear = timeAry[0];
						term = timeAry[1];
						matchList.add(" t.school_year = '" + schoolyear + "'");
						matchList.add(" t.term_code = '" + term + "'");
					}
					if (Type_Schoolyear.equals(entry.getKey())) {
						schoolyear = (String) entry.getValue();
						matchList.add(" t.school_year = '"+schoolyear+"'");
					}
					if (Type_Term.equals(entry.getKey())) {
						term = (String) entry.getValue();
						matchList.add(" t.term_code ='" + entry.getValue()
								+ "'");
					}
					if (Type_Meal.equals(entry.getKey())) {
						String mealName = (String) entry.getValue();
						switch(mealName){
						      case "早餐": 
						    	  tp = Constant.Type_BreakFastCost_Standard;
						    	  break;
						      case "午餐":
						    	  tp = Constant.Type_LunchCost_Standard;
						    	  break;
						      case "晚餐":
						    	  tp = Constant.Type_DinnerCost_Standard;
						    	  break;
						}
						matchList.add(" t.meal_name = '" + entry.getValue()
								+ "'");
					}
					if (Type_Week.equals(entry.getKey())) {
						weekth = Integer.parseInt((String) entry.getValue());
						matchList.add(" t.month =" + entry.getValue() + " and t.type_ = '"+type_[3]+"'");
					}
				}
			}
		}
		String wheresql = "", otherSql = "";
		if (!matchList.isEmpty()) {
			wheresql = " where " + StringUtils.join(matchList, " and ");
		}
		Integer year = schoolyear == null ? null : Integer.parseInt(schoolyear
				.substring(0, 4));
		String stuSql = businessDao.getStuSql(year, deptList, stuAdvancedList);
		Double biaozhun = stuCostDao.queryStandard(schoolyear, term, tp, type_);
		if (weekth == null && !ismeal){
		int days =stuCostDao.getStartAndEnd(schoolyear, term);
		if(biaozhun == null){
			return "";
		}
		biaozhun = biaozhun*days;
		xfsql = xfsql + (wheresql.equals("") ? " where " : wheresql + " and ")
				+ " t.sum_money " + type_[1] + biaozhun + " ";
		}else if(weekth != null && !ismeal){
		int days =stuCostDao.getStartAndEnd(schoolyear, term);
		biaozhun = biaozhun*days;
		xfsql = xfsql + (wheresql.equals("") ? " where " : wheresql );
		}else if(ismeal){
			xfsql = xfsql + (wheresql.equals("") ? " where " : wheresql + " and ")
					+ " t.sum_money " + type_[1] + biaozhun + " ";
		}
		biaozhun = MathUtils.get2Point(biaozhun);
		if (keyValue != null) {
			for (Map.Entry<String, Object> entry : keyValue.entrySet()) {
				String type = entry.getKey(), code = String.valueOf(entry
						.getValue());
				if (code != null && !code.equals("")) {
					switch (type) {
					case Type_Meal:
						matchList1.add(" meal_name = '" + code + "'");
						break;
					case Type_Loan:
						if (code.equals("3")) {
							joinList.add("inner join t_stu_loan loan on a.stu_id = loan.stu_id ");
							matchList1
									.add(" loan.school_year in ("
											+ businessDao
													.formatInSql(schoolyear)
											+ ")");
						} else if (code.equals("1")) {
							otherSql = " select loan.stu_id,replace(wm_concat(loan.money),',','和') as sum_money from t_stu_loan loan where loan.school_year in ("
									+ businessDao.formatInSql(schoolyear) + ") group by loan.stu_id ";
						}
						break;
					case Type_Subsidy:
						if (code.equals("3")) {
							joinList.add("inner join t_stu_subsidy loan on a.stu_id = loan.stu_id ");
							matchList1
									.add(" loan.school_year in ("
											+ businessDao
													.formatInSql(schoolyear)
											+ ")");
						} else if (code.equals("1")) {
							otherSql = " select loan.stu_id,replace(wm_concat(loan.money),',','和') as sum_money from t_stu_subsidy  loan where loan.school_year in ("
									+ businessDao.formatInSql(schoolyear) + ") group by loan.stu_id";
						}
						break;
					case Type_Jm:
						if (code.equals("3")) {
							joinList.add("inner join t_stu_jm loan on a.stu_id = loan.stu_id ");
							matchList1
									.add(" loan.school_year in ("
											+ businessDao
													.formatInSql(schoolyear)
											+ ")");
						} else if (code.equals("1")) {
							otherSql = " select loan.stu_id,replace(wm_concat(loan.money),',','和') as sum_money from t_stu_jm loan where loan.school_year in ("
									+ businessDao.formatInSql(schoolyear) + ") group by loan.stu_id";
						}
						break;
					case Type_All:
						xfsql = "";
						otherSql = "";
						break;
					default:
						break;
					}
				}
			}
			if (!otherSql.equals("")) {
				xfsql = otherSql;
			} else {
				if (!xfsql.equals("")) {
					if (!matchList1.isEmpty() && !joinList.isEmpty()) {
						xfsql = "select a.stu_id,a.sum_money from ("
								+ xfsql
								+ (") a " + StringUtils.join(joinList, ""))
								+ (" and " + StringUtils.join(matchList1,
										" and "));
					} else if (!matchList1.isEmpty() && joinList.isEmpty()) {
						xfsql = xfsql
								+ (" and " + StringUtils.join(matchList1,
										" and "));
					}
				}
			}
		}
		String stuDetailSql = businessDao.getStuDetailSql(stuSql);
		if (!xfsql.equals("")) {
			stuDetailSql = " select a.*,b.*,'"+biaozhun+"' as bz from (" + xfsql + ") a inner join ("
					+ stuDetailSql + ") b on a.stu_id = b.no";
		}
		if (fields != null && !fields.isEmpty()) {
			stuDetailSql = "select " + StringUtils.join(fields, ",")
					+ ",shengmc||shimc||xianmc as syd from (" + stuDetailSql + ") ";
		}
		return stuDetailSql;
	}
//	private List<Map<String, Object>> getBzdmXnXq(String tableName,
//			String columnSchoolYear, String columnTermCode) {
//		List<Map<String, Object>> list = new ArrayList<>();
//
//		// 最小学年 最小学期；最大学年 最大学期
//		String[] minAry = businessDao.queryMinSchoolYearTermCode(tableName,
//				columnSchoolYear, columnTermCode), maxAry = businessDao
//				.queryMaxSchoolYearTermCode(tableName, columnSchoolYear,
//						columnTermCode);
//		Integer minYear = minAry[0] != null ? Integer.valueOf(minAry[0]) : null, maxYear = maxAry[0] != null ? Integer
//				.valueOf(maxAry[0]) : null;
//		String minTerm = minAry[1], maxTerm = maxAry[1], yearSuf = "学年", middle = " ", term1 = Globals.TERM_FIRST, term2 = Globals.TERM_SECOND, termName1 = "（第一学期）", termName2 = "（第二学期）", name1 = middle
//				+ termName1, name2 = middle + termName2;
//		if (minYear != null && maxYear != null) {
//			if (minTerm != null && maxTerm != null) {
//				Integer year = minYear;
//				String yearId = null;
//				for (; year <= maxYear; year++) {
//					Map<String, Object> m2 = new HashMap<>(), m1 = new HashMap<>();
//					yearId = String.valueOf(year) + "-"
//							+ String.valueOf(year + 1);
//					// 最小年
//					if (year.equals(minYear)) {
//						if (minTerm.equals(term1)) {
//							m1.put("id", yearId + "+" + term1);
//							m1.put("mc", yearId + yearSuf + name1);
//							list.add(m1);
//							m2.put("id", yearId + "+" + term2);
//							m2.put("mc", yearId + yearSuf + name2);
//							list.add(m2);
//						} else if (minTerm.equals(term2)) {
//							m2.put("id", yearId + "+" + term2);
//							m2.put("mc", yearId + yearSuf + name2);
//							list.add(m2);
//						}
//					} else if (year.equals(maxYear)) {
//						if (maxTerm.equals(term2)) {
//							m1.put("id", yearId + "+" + term1);
//							m1.put("mc", yearId + yearSuf + name1);
//							list.add(m1);
//							m2.put("id", yearId + "+" + term2);
//							m2.put("mc", yearId + yearSuf + name2);
//							list.add(m2);
//						} else if (maxTerm.equals(term1)) {
//							m1.put("id", yearId + "+" + term1);
//							m1.put("mc", yearId + yearSuf + name1);
//							list.add(m1);
//						}
//					} else {
//						m1.put("id", yearId + "+" + term1);
//						m1.put("mc", yearId + yearSuf + name1);
//						list.add(m1);
//						m2.put("id", yearId + "+" + term2);
//						m2.put("mc", yearId + yearSuf + name2);
//						list.add(m2);
//					}
//				}
//				Collections.reverse(list);
//			}
//		}
//		return list;
//	}
}
