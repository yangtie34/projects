package cn.gilight.dmm.xg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.StuWarningDao;
import cn.gilight.dmm.xg.entity.TStuWarningMailStatus;
import cn.gilight.dmm.xg.job.StuWarningMailJob;
import cn.gilight.dmm.xg.service.StuWarningService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 
 * 
 * @author xuebl
 * @date 2016年6月15日 下午2:48:34
 */
@Service("stuWarningService")
public class StuWarningServiceImpl implements StuWarningService {

	@Resource
	private StuWarningDao stuWarningDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private StuWarningMailJob stuWarningMailJob;
	@Resource
	private HibernateDao hibernateDao;

	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"stuWarning";
	/** 疑似逃课 */
	private static final String Key_skipClasses = "skipClasses";
	/** 疑似未住宿 */
	private static final String Key_notStay   = "notStay";
	/** 疑似晚勤晚归 */
	private static final String Key_stayLate  = "stayLate";
	/** 疑似不在校 */
	private static final String Key_stayNotin = "stayNotin";
	/** 疑似逃课 */
	private static final String Name_skipClasses = "疑似逃课";
	/** 疑似未住宿 */
	private static final String Name_notStay   = "疑似未住宿";
	/** 疑似晚勤晚归 */
	private static final String Name_stayLate  = "疑似晚勤晚归";
	/** 疑似不在校 */
	private static final String Name_stayNotin = "疑似不在校";
	
	/** 新疆地区 */
	private static final String Mold_Origin_Xj_Mz = "XJ_MZ";
	

	private static String 
			column_code  = Constant.NEXT_LEVEL_COLUMN_CODE,
		   column_name  = Constant.NEXT_LEVEL_COLUMN_NAME,
		   column_count = Constant.NEXT_LEVEL_COLUMN_COUNT;
	
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	
	@Override
	public Map<String, Object> getAbstract(String date, List<AdvancedParam> advancedParamList, String mold){
		/**
		 * 考勤预警数据：疑似逃课、晚勤晚归、疑似未住宿、疑似不在校
		 */
		String yesterDay = DateUtils.getYesterday();
		date = date==null ? yesterDay : date;
		// 昨天是否已经学期结束
		boolean isTermOver = isTermOver(date);
		if(isTermOver){ // 学期结束时，显示上一学期最后一天
			String[] ary = queryLastTermDates(date);
			date = ary[1];
		}
		Map<String, Object> map = DevUtils.MAP();
		if(date == null){ // 没有预警数据
			map.put("status", false);
			map.put("info", "没有预警数据");
		}else{
			/** 特殊群体处理 */
			advancedParamList = handleAdvancedParamListByMold(advancedParamList, mold);
			String[] ary = EduUtils.getSchoolYearTerm(date);
			int year = Integer.valueOf(ary[0].substring(0, 4));
			List<String> deptList = getDeptDataList();
			
			// 疑似逃课（人次） 晚勤晚归（人数）疑似未住宿（人数）疑似不在校（人数） 20170105
			int count_skipClasses = stuWarningDao.querySkipClassesRc(year, deptList, advancedParamList, date),
				count_notStay     = stuWarningDao.queryNotStayRs(year, deptList, advancedParamList, date),
				count_stayLate    = stuWarningDao.queryStayLateRs(year, deptList, advancedParamList, date),
				count_stayNotin   = stuWarningDao.queryStayNotinRs(year, deptList, advancedParamList, date),
				count             = count_skipClasses + count_notStay + count_stayLate + count_stayNotin;
			map.put(Key_skipClasses, count_skipClasses);
			map.put(Key_notStay, count_notStay);
			map.put(Key_stayLate, count_stayLate);
			map.put(Key_stayNotin, count_stayNotin);
			map.put("count", count);
			map.put("isTermOver", isTermOver);
			map.put("date", date);
			map.put("yesterDay", yesterDay);
			// 上周数据
			String[] lastWeekDates = DateUtils.getWeekDates(DateUtils.getNextDayByLen(date, -7));
			int count_skipClasses_last = stuWarningDao.querySkipClassesRc(year, deptList, advancedParamList, lastWeekDates),
				count_notStay_last     = stuWarningDao.queryNotStayRc(year, deptList, advancedParamList, lastWeekDates),
				count_stayLate_last    = stuWarningDao.queryStayLateRc(year, deptList, advancedParamList, lastWeekDates),
				count_stayNotin_last   = stuWarningDao.queryStayNotinRc(year, deptList, advancedParamList, lastWeekDates),
				count_lastweek         = count_skipClasses_last + count_notStay_last + count_stayLate_last + count_stayNotin_last;
			map.put("amp", getAmplification(count, count_lastweek)); // 相比其上周平均数据增幅
		}
		// 添加类别，新疆地区
		if(mold == null){
			List<Map<String, Object>> mold_list = new ArrayList<>();
			Map<String, Object> map_origin_xj = new HashMap<>();
			map_origin_xj.put("mc", "新疆地区（少数民族）"); map_origin_xj.put("id", Mold_Origin_Xj_Mz);
			mold_list.add(map_origin_xj);
			map.put("mold", mold_list);
		}
		return map;
	}

	/**
	 * 特殊群体处理（添加高级查询参数）
	 * 新疆地区（少数民族）
	 */
	private List<AdvancedParam> handleAdvancedParamListByMold(List<AdvancedParam> advancedParamList, String mold){
		advancedParamList = advancedParamList==null ? new ArrayList<AdvancedParam>() : advancedParamList;
		if(mold != null){
			switch (mold) {
			case Mold_Origin_Xj_Mz: // 新疆地区（少数民族）
				AdvancedParam ad_origin = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ORIGIN_ID, Constant.ORIGIN_ID_XJ);
				advancedParamList.add(ad_origin);
				AdvancedParam ad_mation = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Stu_NATION_CODE, Constant.NATION_ID_Minority);
				advancedParamList.add(ad_mation);
				break;
			}
		}
		return advancedParamList;
	}
	
	
	/**
	 * 获取增幅
	 * @param count 当前数据
	 * @param lastWeek_count 上周数据
	 * @return Double
	 */
	private Double getAmplification(int count, int lastWeek_count){
		Double lastWeek_count_avg = MathUtils.getDivisionResult(lastWeek_count, 7, 2);
		return MathUtils.getAmplificationNum(new Double(count), lastWeek_count_avg, 1);
	}
	
	/**
	 * 
	 * 获取指定日期的上学期的 上课时间段
	 * @param date
	 * @return String[] ['2016-03-08','2016-07-10']
	 */
	private String[] queryLastTermDates(String date){
		String[] ary = new String[2];
		String sql = "select t.start_date,t.end_date from t_school_start t order by t.school_year,t.term_code";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		Map<String, Object> map = null;
		for(int i=0,len=list.size(); i<len; i++){
			map = list.get(i);
			String startDate = MapUtils.getString(map, "start_date");
			if(DateUtils.compare(startDate, date) && i>0){
				ary[0] = MapUtils.getString(list.get(i-1), "start_date");
				ary[1] = MapUtils.getString(list.get(i-1), "end_date");
				break;
			}
		}
		if(ary[0]==null && list.size()>0){
			map = list.get(list.size()-1);
			ary[0] = MapUtils.getString(map, "start_date");
			ary[1] = MapUtils.getString(map, "end_date");
		}
		return ary[0]==null ? null : ary;
	}
	
	@Override
	public Map<String, Object> getDeptDataGrid(String date, String type, String valueType, String asc, 
			List<AdvancedParam> advancedParamList, String mold){
		/**
		 * 分节点查询数据
		 */
		String yesterDay = DateUtils.getYesterday();
		date = date==null ? yesterDay : date;
		// 昨天是否已经学期结束
		boolean isTermOver = isTermOver(date);
		if(isTermOver){
			String[] ary = queryLastTermDates(date);
			date = ary[1];
		}
		Map<String, Object> reMap = DevUtils.MAP();
		if(date == null){ // 没有预警数据
			reMap.put("status", false);
			reMap.put("info", "没有预警数据");
		}else{
			/** 特殊群体处理 */
			advancedParamList = handleAdvancedParamListByMold(advancedParamList, mold);
			
			String[] ary = EduUtils.getSchoolYearTerm(date);
			int year = Integer.valueOf(ary[0].substring(0, 4));
			List<String> deptList = getDeptDataList();
			String pid = AdvancedUtil.getPid(advancedParamList);
			Integer grade = AdvancedUtil.getStuGrade(advancedParamList);
			String[] thisWeekDates = new String[]{date, date};
			
			// 疑似逃课（人次） 晚勤晚归（人数）疑似未住宿（人数）疑似不在校（人数） 20170105
			String sql_xs = businessDao.getStuSql(year, deptList, advancedParamList),
					sql_skipClasses = stuWarningDao.getSkipClassesRcSql(year, deptList, advancedParamList, thisWeekDates),
					sql_notStay     = stuWarningDao.getNotStayRsSql(year, deptList, advancedParamList, thisWeekDates),
					sql_stayLate    = stuWarningDao.getStayLateRsSql(year, deptList, advancedParamList, thisWeekDates),
					sql_stayNotin   = stuWarningDao.getStayNotinRsSql(year, deptList, advancedParamList, thisWeekDates);
			String xs_sqlGroup =  businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_xs, true, true, false, false, year, grade),
					skipClasses_sqlGroup = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_skipClasses, true, true, false, false, year, grade),
					notStay_sqlGroup = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_notStay, true, true, false, false, year, grade),
					stayLate_sqlGroup = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_stayLate, true, true, false, false, year, grade),
					stayNotin_sqlGroup = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_stayNotin, true, true, false, false, year, grade);
			// 上周sql
			String[] lastWeekDates = DateUtils.getWeekDates(DateUtils.getNextDayByLen(date, -7));
			String sql_skipClasses_last = stuWarningDao.getSkipClassesRcSql(year, deptList, advancedParamList, lastWeekDates),
					sql_notStay_last     = stuWarningDao.getNotStayRsSql(year, deptList, advancedParamList, lastWeekDates),
					sql_stayLate_last    = stuWarningDao.getStayLateRsSql(year, deptList, advancedParamList, lastWeekDates),
					sql_stayNotin_last   = stuWarningDao.getStayNotinRsSql(year, deptList, advancedParamList, lastWeekDates);
			String skipClasses_sqlGroup_last = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_skipClasses_last, true, true, false, false, year, grade),
					notStay_sqlGroup_last = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_notStay_last, true, true, false, false, year, grade),
					stayLate_sqlGroup_last = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_stayLate_last, true, true, false, false, year, grade),
					stayNotin_sqlGroup_last = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_stayNotin_last, true, true, false, false, year, grade);
			// 邮件发送状态
			String sql_mailStatus = "select t.dept_id, t.status from t_Stu_Warning_mail_status t,"
					+ " (select max(time_) time_, dept_id from t_Stu_Warning_mail_status where date_='"+date+"' group by dept_id) z"
					+ " where date_='"+date+"' and t.time_=z.time_",
				sql_order = businessService.getNextLevelOrderSqlByDeptDataAndPid(deptList, pid, sql_xs, true, true, false, false, year, grade);
			sql_mailStatus = "select a.status, b.id "+column_code+" from ("+sql_mailStatus+") a right join ("+sql_order+") b on a.dept_id = b.id";
			
			List<Map<String, Object>> list = new ArrayList<>();
			List<Map<String, Object>> xs_list = baseDao.queryListInLowerKey(xs_sqlGroup),
					// 本周
					skipClasses_list = baseDao.queryListInLowerKey(skipClasses_sqlGroup),
					notStay_list = baseDao.queryListInLowerKey(notStay_sqlGroup),
					stayLate_list = baseDao.queryListInLowerKey(stayLate_sqlGroup),
					stayNotin_list = baseDao.queryListInLowerKey(stayNotin_sqlGroup),
					// 上周
					skipClasses_list_last = baseDao.queryListInLowerKey(skipClasses_sqlGroup_last),
					notStay_list_last = baseDao.queryListInLowerKey(notStay_sqlGroup_last),
					stayLate_list_last = baseDao.queryListInLowerKey(stayLate_sqlGroup_last),
					stayNotin_list_last = baseDao.queryListInLowerKey(stayNotin_sqlGroup_last),
					mail_list = baseDao.queryListInLowerKey(sql_mailStatus);
			String _count = "_count", key_lastCount = "lastCount";
			Map<String, Object> oneMapOneType = null;
			for(Map<String, Object> xsMap : xs_list){
				Map<String, Object> oneMap = new HashMap<>();
				String code = MapUtils.getString(xsMap, column_code), code2 = null;
				Integer xscount = MapUtils.getInteger(xsMap, column_count);
				oneMap.put("id", code);
				oneMap.put("name", MapUtils.getString(xsMap, column_name));
				oneMap.put("xscount", xscount); // 学生数量
				int count = 0, count_last = 0; // 预警数量
				// 疑似逃课
				oneMapOneType = getDeptDataGridOneMap(skipClasses_list, skipClasses_list_last, code, xscount, Key_skipClasses);
				count += MapUtils.getIntValue(oneMapOneType, Key_skipClasses+_count);
				count_last += MapUtils.getInteger(oneMapOneType, key_lastCount); // 累加上周数据
				oneMapOneType.remove(key_lastCount); // 移除上周数据
				oneMap.putAll(oneMapOneType);
				// 疑似未住宿
				oneMapOneType = getDeptDataGridOneMap(notStay_list, notStay_list_last, code, xscount, Key_notStay);
				count += MapUtils.getIntValue(oneMapOneType, Key_notStay+_count);
				count_last += MapUtils.getInteger(oneMapOneType, key_lastCount); // 累加上周数据
				oneMapOneType.remove(key_lastCount); // 移除上周数据
				oneMap.putAll(oneMapOneType);
				// 晚勤晚归
				oneMapOneType = getDeptDataGridOneMap(stayLate_list, stayLate_list_last, code, xscount, Key_stayLate);
				count += MapUtils.getIntValue(oneMapOneType, Key_stayLate+_count);
				count_last += MapUtils.getInteger(oneMapOneType, key_lastCount); // 累加上周数据
				oneMapOneType.remove(key_lastCount); // 移除上周数据
				oneMap.putAll(oneMapOneType);
				// 疑似未住宿
				oneMapOneType = getDeptDataGridOneMap(stayNotin_list, stayNotin_list_last, code, xscount, Key_stayNotin);
				count += MapUtils.getIntValue(oneMapOneType, Key_stayNotin+_count);
				count_last += MapUtils.getInteger(oneMapOneType, key_lastCount); // 累加上周数据
				oneMapOneType.remove(key_lastCount); // 移除上周数据
				oneMap.putAll(oneMapOneType);
				// 邮件状态
				for(Map<String, Object> map2 : mail_list){
					code2 = MapUtils.getString(map2, column_code);
					if(code.equals(code2)){
						oneMap.put("status", MapUtils.getInteger(map2, "status"));
						break;
					}
				}
				oneMap.put("amp", getAmplification(count, count_last)); // 相比其上周平均数据增幅
				oneMap.put("count", count); // 预警数量
				list.add(oneMap);
			}
			
			// 当前组织机构类型名称（院系、专业）
			String deptMc = businessService.getLevelNameById(list.isEmpty() ? null : MapUtils.getString(list.get(0), "id"));
			reMap.put("deptMc", deptMc);
			reMap.put("list", list);
			// 排序
			String order_column = type==null ? valueType : (type+"_"+valueType);
			boolean isAsc = false; // 默认倒序
			if(asc != null && "asc".equals(asc)) isAsc = true;
			sortList(list, order_column, isAsc);
		}
		return reMap;
	}
	/** 获取表格分布中的map；eg:{***_count:'',***_scale:'',***_amp:'',lastCount:''} */
	private Map<String, Object> getDeptDataGridOneMap(List<Map<String, Object>> thisWeekList, List<Map<String, Object>> lastWeekList,
			String deptId, Integer xscount, String code){
		Map<String, Object> oneMap = new HashMap<>();
		String _count = "_count", _scale = "_scale", _amp = "_amp", code2 = null;
		for(Map<String, Object> map2 : thisWeekList){
			code2 = MapUtils.getString(map2, column_code);
			if(deptId.equals(code2)){
				int count2 = MapUtils.getInteger(map2, column_count);
				oneMap.put(code+_count, count2);
				oneMap.put(code+_scale, MathUtils.getPercentNum(count2, xscount));
//				count += count2;
				// 上周
				for(Map<String, Object> map3 : lastWeekList){
					code2 = MapUtils.getString(map3, column_code);
					if(deptId.equals(code2)){
						int count3 = MapUtils.getInteger(map3, column_count);
						oneMap.put(code+_amp, getAmplification(count2, count3));
//						count_last += count3;
						oneMap.put("lastCount", count3);
						break;
					}
				}
				break;
			}
		}
		return oneMap;
	}
	/**
	 * List 排序
	 * @param list 列表
	 * @param order_column 排序字段
	 * @param isAsc 正序
	 */
	private void sortList(List<Map<String, Object>> list, final String order_column, final boolean isAsc){
		Collections.sort(list, new Comparator<Map<String, Object>>(){
			Double d1 = null, d2 = null;
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int k = 0;
				d1 = MapUtils.getDouble(o1, order_column);
				d2 = MapUtils.getDouble(o2, order_column);
				if(d1!=null && (d1>d2 || d2==null)){ k = 1; }
				else if(d1==null || (d1<d2 && d2!=null)){ k = -1; }
				if(!isAsc){ k = -k; }
				return k;
			}
		});
	}
	
	@Override
	public boolean isTermOver(String date){
		boolean isTermOver = true;
		date = date!=null ? date : DateUtils.getYesterday();
		String sql = "select t.start_date,t.end_date from t_school_start t order by t.school_year,t.term_code";
		List<Map<String, Object>> list = baseDao.queryListInLowerKey(sql);
		for(Map<String, Object> map : list){
			String startDate = MapUtils.getString(map, "start_date"),
				   endDate   = MapUtils.getString(map, "end_date");
			if(DateUtils.compareEqual(date, startDate) && DateUtils.compareEqual(endDate, date)){
				isTermOver = false;
				break;
			}
		}
		return isTermOver;
	}

	@Override
	public Map<String, Object> getBzdmXnXq(){
		List<Map<String, Object>> list = businessService.queryBzdmXnXq5();
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}

	@Override
	public Map<String, Object> getIsSetStartEndDate(String schoolYear, String termCode){
		Map<String, Object> reMap = new HashMap<>();
		Map<String, Object> paramM = getParamsMapByParams(null, schoolYear, termCode);
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		//
		reMap.put("status", isSetStartEndDate(schoolYear, termCode));
		reMap.put("schoolYear", schoolYear);
		reMap.put("termCode", termCode);
		return reMap;
	}
	
	@Override
	public boolean isSetStartEndDate(String schoolYear, String termCode){
		Map<String, Object> paramM = getParamsMapByParams(null, schoolYear, termCode);
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t where t.school_year='"+schoolYear+"' and t.term_code='"+termCode+"'");
		// 判断学年学期所对应的时间段是否存在
		if(list.isEmpty()){
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDistribution(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String mold){
		Map<String, Object> reMap = DevUtils.MAP();
		
		long time1 = System.currentTimeMillis(); // TODO
		/** 特殊群体处理 */
		advancedParamList = handleAdvancedParamListByMold(advancedParamList, mold);
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, schoolYear, termCode);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramM.get("stuAdvancedList");
		List<String> deptList = (List<String>) MapUtils.getObject(paramM, "deptList");
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		Integer year = MapUtils.getInteger(paramM, "year");
		// 获取学年学期对应时间段
		String[] dates = new String[2];
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t where t.school_year='"+schoolYear+"' and t.term_code='"+termCode+"'");
		for(Map<String, Object> map : list){
			dates[0] = MapUtils.getString(map, "start_date");
			dates[1] = MapUtils.getString(map, "end_date");
		}
		/** 各类预警分布 */
		int count_skipClasses = stuWarningDao.querySkipClassesRc(year, deptList, stuAdvancedList, dates),
			count_notStay     = stuWarningDao.queryNotStayRc(year, deptList, stuAdvancedList, dates),
			count_stayLate    = stuWarningDao.queryStayLateRc(year, deptList, stuAdvancedList, dates),
			count_stayNotin   = stuWarningDao.queryStayNotinRc(year, deptList, stuAdvancedList, dates);

		long time2 = System.currentTimeMillis(); // TODO
		DevUtils.p("getDistribution rc：" + (time2-time1));
		
//		int[] type_ary = {count_skipClasses, count_notStay, count_stayLate, count_stayNotin};
		List<Map<String, Object>> typeList = new ArrayList<>();
		Map<String,Object> skipClassesM = new HashMap<>(), notStay = new HashMap<>(), stayLate = new HashMap<>(), stayNotin = new HashMap<>();
		skipClassesM.put("code", Key_skipClasses); skipClassesM.put("name", Name_skipClasses); skipClassesM.put("value", count_skipClasses);
		notStay.put("code", Key_notStay); notStay.put("name", Name_notStay); notStay.put("value", count_notStay);
		stayLate.put("code", Key_stayLate); stayLate.put("name", Name_stayLate); stayLate.put("value", count_stayLate);
		stayNotin.put("code", Key_stayNotin); stayNotin.put("name", Name_stayNotin); stayNotin.put("value", count_stayNotin);
		typeList.add(skipClassesM); typeList.add(notStay); typeList.add(stayLate); typeList.add(stayNotin);
		/** 预警人数 年级、性别分布 */
		String skipClassesSql = stuWarningDao.getSkipClassesRcSql(year, deptList, stuAdvancedList, dates),
			   notStaySql     = stuWarningDao.getNotStayRcSql(year, deptList, stuAdvancedList, dates),
			   stayLateSql    = stuWarningDao.getStayLateRcSql(year, deptList, stuAdvancedList, dates),
			   stayNotinSql   = stuWarningDao.getStayNotinRcSql(year, deptList, stuAdvancedList, dates);
		List<Map<String, Object>> gradeList = queryAndPackageGradeGroup(year, skipClassesSql, notStaySql, stayLateSql, stayNotinSql),
								  sexList   = queryAndPackageSexGroup(year, skipClassesSql, notStaySql, stayLateSql, stayNotinSql);
		// return
		reMap.put("type", typeList);
		reMap.put("grade", gradeList);
		reMap.put("sex", sexList);
		
		long time3 = System.currentTimeMillis(); // TODO
		DevUtils.p("getDistribution：" + (time3-time2));
		return reMap;
	}
	
	/** 获取各类预警年级分组 */
	private List<Map<String, Object>> queryAndPackageGradeGroup(int year, String... sqls){
		String sql2 = ListUtils.join(ListUtils.ary2List(sqls), " union all ");
		sql2 = "select count(0) value, case when grade is null then 'null' else grade end code from ("+sql2+") group by grade order by grade";
		List<List<Map<String, Object>>> list = new ArrayList<>();
		list.add(baseDao.queryListInLowerKey(sql2));
		/*for(String sql : sqls){
			gradeAll_list.add(queryGradeGroup(sql, year));
		}*/
		return getGradeGroupList(list);
	}
	/** 获取年级数量分组 */
	@SuppressWarnings("unused")
	private List<Map<String, Object>> queryGradeGroup(String sql, int year){
		return baseDao.queryListInLowerKey("select * from (select case when grade is null then 'null' else grade end code,"
				+ " count(0) value from (select stu_id, to_char("+year+"+1-stu.enroll_grade) grade"
				+ " from ("+sql+")t,t_stu stu where t.stu_id=stu.no_) group by grade) order by code");
	}
	/** 组合多组年级数据 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getGradeGroupList(List<List<Map<String, Object>>> all_list){
		// 用于获取年级分组 [ {code:1,name:'一年级',value:54}, {code:2,name:'二年级',value:0}]
		List<Map<String, Object>> reList = new ArrayList<>();
		// 用于辅助处理reList {1:{code:1,name:''value:54}, 2:{}}
		Map<String, Object> keyMap = new HashMap<>();
		Map<String, Object>	oneMap = null; // {code:1,name:'',value:54}
		String code = null, key_code = "code", key_value = "value", key_name = "name", value_null = "'null'";
		for(List<Map<String, Object>> li : all_list){
			for(Map<String, Object> map : li){
				code = MapUtils.getString(map, key_code); // 
				int value = MapUtils.getIntValue(map, key_value);
				// set
				if(!keyMap.containsKey(code)){
					oneMap = new HashMap<>();
					oneMap.put(key_code, code);
					oneMap.put(key_value, value);
					String name = null;
					if(code==null || value_null.equals(code)){
						code = value_null;
						name = Constant.CODE_Unknown_Name;
					}else name = EduUtils.getNjNameByCode(Integer.valueOf(code));
					oneMap.put(key_name, name);
					reList.add(oneMap);
					keyMap.put(code, oneMap);
				}else{
					oneMap = (Map<String, Object>) keyMap.get(code);
					oneMap.put(key_value, MapUtils.getIntValue(oneMap, key_value)+value);
				}
			}
		}
		return reList;
	}

	/** 获取各类预警性别分组 */
	private List<Map<String, Object>> queryAndPackageSexGroup(int year, String... sqls){
		String sql2 = ListUtils.join(ListUtils.ary2List(sqls), " union all ");
		sql2 = "select value, code, nvl(sex.name_,'"+Constant.CODE_Unknown_Name+"')name from ("
				+ "select count(0) value, case when sex_code is null then 'null' else sex_code end code from ("+sql2+") group by sex_code order by sex_code"
				+ ")t left join t_code sex on sex.code_=t.code and sex.code_type='"+Constant.CODE_SEX_CODE+"' order by sex.order_,sex.code_";
		List<List<Map<String, Object>>> list = new ArrayList<>();
		list.add(baseDao.queryListInLowerKey(sql2));
		/*for(String sql : sqls){
			list.add(querySexGroup(sql, year));
		}*/
		return getSexGroupList(list);
	}
	/** 获取性别数量分组 */
	@SuppressWarnings("unused")
	private List<Map<String, Object>> querySexGroup(String sql, int year){
		return baseDao.queryListInLowerKey("select t.value,t.code,case when t.code is null then 'null' else sex.name_ end name"
				+ " from (select count(0) value, stu.sex_code code from ("+sql+") t, t_stu stu"
				+ " where t.stu_id=stu.no_ group by stu.sex_code)t"
				+ " left join t_code sex on sex.code_=t.code and sex.code_type='"+Constant.CODE_SEX_CODE+"' order by sex.order_,sex.code_");
	}
	/** 组合多组性别数据 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> getSexGroupList(List<List<Map<String, Object>>> all_list){
		// 用于获取性别分组 [ {code:1,name:'男',value:54}, {code:2,name:'女',value:0}]
		List<Map<String, Object>> reList = new ArrayList<>();
		// 用于辅助处理reList {1:{code:1,name:'',value:54}, 2:{}}
		Map<String, Object> keyMap = new HashMap<>();
		Map<String, Object>	oneMap = null; // {code:1,name:'',value:54}
		String code = null, key_code = "code", key_value = "value", key_name = "name", value_null = "'null'";
		for(List<Map<String, Object>> li : all_list){
			for(Map<String, Object> map : li){
				code = MapUtils.getString(map, key_code); // 
				int value = MapUtils.getIntValue(map, key_value);
				// set
				if(!keyMap.containsKey(code)){
					oneMap = new HashMap<>();
					String name = null;
					if(code==null || value_null.equals(code)){
						code = value_null;
						name = Constant.CODE_Unknown_Name;
					}else name = MapUtils.getString(map, key_name);
					oneMap.put(key_code, code);
					oneMap.put(key_value, value);
					oneMap.put(key_name, name);
					reList.add(oneMap);
					keyMap.put(code, oneMap);
				}else{
					oneMap = (Map<String, Object>) keyMap.get(code);
					oneMap.put(key_value, MapUtils.getIntValue(oneMap, key_value)+value);
				}
			}
		}
		return reList;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDeptDataList(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String mold){
		Map<String, Object> reMap = DevUtils.MAP();
		/** 特殊群体处理 */
		advancedParamList = handleAdvancedParamListByMold(advancedParamList, mold);
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, schoolYear, termCode);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramM.get("stuAdvancedList");
		List<String> deptList = (List<String>) MapUtils.getObject(paramM, "deptList");
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		Integer year = MapUtils.getInteger(paramM, "year");
		String pid   = AdvancedUtil.getPid(advancedParamList);
		Integer grade = AdvancedUtil.getStuGrade(advancedParamList);
		// 获取学年学期对应时间段
		String[] dates = new String[2];
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t where t.school_year='"+schoolYear+"' and t.term_code='"+termCode+"'");
		for(Map<String, Object> map : list){
			dates[0] = MapUtils.getString(map, "start_date");
			dates[1] = MapUtils.getString(map, "end_date");
		}
		// 疑似逃课（人次） 晚勤晚归（人次）疑似未住宿（人次）疑似不在校（人次） 20170121
		String  sql_skipClasses = stuWarningDao.getSkipClassesRcSql(year, deptList, stuAdvancedList, dates),
				sql_notStay     = stuWarningDao.getNotStayRcSql(year, deptList, stuAdvancedList, dates),
				sql_stayLate    = stuWarningDao.getStayLateRcSql(year, deptList, stuAdvancedList, dates),
				sql_stayNotin   = stuWarningDao.getStayNotinRcSql(year, deptList, stuAdvancedList, dates);
		String skipClasses_sqlGroup = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_skipClasses, true, true, false, false, year, grade),
			   notStay_sqlGroup     = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_notStay, true, true, false, false, year, grade),
			   stayLate_sqlGroup    = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_stayLate, true, true, false, false, year, grade),
			   stayNotin_sqlGroup   = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, sql_stayNotin, true, true, false, false, year, grade);
		List<Map<String, Object>> skipClasses_list = baseDao.queryListInLowerKey(skipClasses_sqlGroup),
								  notStay_list     = baseDao.queryListInLowerKey(notStay_sqlGroup),
								  stayLate_list    = baseDao.queryListInLowerKey(stayLate_sqlGroup),
								  stayNotin_list   = baseDao.queryListInLowerKey(stayNotin_sqlGroup),
								  nextLevelList    = new ArrayList<Map<String, Object>>();
		String code = null;
		for(Map<String, Object> map : skipClasses_list){
			Map<String, Object> oneMap = new HashMap<>();
			code = MapUtils.getString(map, column_code);
			oneMap.put("id", code);
			oneMap.put("name", MapUtils.getString(map, column_name));
			List<Map<String, Object>> oneList = new ArrayList<>();
			oneList.add(getDeptDataMapByCode(skipClasses_list, code, Key_skipClasses, Name_skipClasses));
			oneList.add(getDeptDataMapByCode(notStay_list, code, Key_notStay, Name_notStay));
			oneList.add(getDeptDataMapByCode(stayLate_list, code, Key_stayLate, Name_stayLate));
			oneList.add(getDeptDataMapByCode(stayNotin_list, code, Key_stayNotin, Name_stayNotin));
			oneMap.put("list", oneList);
			nextLevelList.add(oneMap);
		}
		
		// 当前组织机构类型名称（院系、专业）
		String deptMc = businessService.getLevelNameById(nextLevelList.isEmpty() ? null : MapUtils.getString(nextLevelList.get(0), "id"));
		reMap.put("deptMc", deptMc);
		reMap.put("list", nextLevelList);
		return reMap;
	}
	/** 获取组织机构分布中的map；eg:{name:'',code:'',value:''} */
	private Map<String, Object> getDeptDataMapByCode(List<Map<String, Object>> list, String deptId, String code, String name){
		Map<String, Object>	one2 = new HashMap<>();
		for(Map<String, Object> map2 : list){
			if(deptId.equals(MapUtils.getString(map2, column_code))){
				one2.put("name", name);
				one2.put("code", code);
				one2.put("value", MapUtils.getDouble(map2, column_count));
				break;
			}
		}
		return one2;
	}
	
	@Transactional
	@Override
	public Map<String, Object> send(String deptId, String date){
		List<String> li = ListUtils.ary2List(deptId.split(","));
		List<TStuWarningMailStatus> saveList = new ArrayList<>();
		int success = 0, fail = 0;
		for(String dept : li){
			TStuWarningMailStatus t = stuWarningMailJob.getAndSendDetail(date, dept, null, null);
			if(t == null){
				fail++;
			}else{
				saveList.add(t);
				success++;
			}
		}
		if(saveList.size() > 0)
			try {
				hibernateDao.saveAll(saveList);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		Map<String, Object> reMap = DevUtils.MAP();
		if(li.size()==1){
			if(fail==1){
				reMap.put("status", false);
				reMap.put("info", "发送失败");
			}else{
				reMap.put("info", "发送成功");
			}
		}else{
			List<String> infos = new ArrayList<>();
			if(success > 0) infos.add("发送成功"+success+"条");
			if(fail > 0) infos.add("发送失败"+fail+"条");
			reMap.put("info", ListUtils.join(infos, ","));
		}
		return reMap;
	}

	@Override
	public Map<String, Object> getDetail(List<AdvancedParam> advancedParamList, Page page, 
			Map<String, Object> keyValue, List<String> fields,String mold){
		String detailSql=null;
		/** 特殊群体处理 */
		advancedParamList = handleAdvancedParamListByMold(advancedParamList, mold);
		if(keyValue.containsKey("tag") && MapUtils.getBooleanValue(keyValue, "tag")){
			detailSql=getDetailOtherSql(advancedParamList, keyValue, fields);
		}else{
			detailSql = getDetailSql(advancedParamList, keyValue, fields);
		}
		// 
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(detailSql, page);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getDetailAll(List<AdvancedParam> advancedParamList,Page page,
			Map<String, Object> keyValue, List<String> fields,String mold){
		Map<String, Object> map= getDetail(advancedParamList, page, keyValue, fields,mold);
		return (List<Map<String, Object>>)map.get("rows");
	}

	@SuppressWarnings("unchecked")
	private String getDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields) {
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, null, null);
		List<String> deptList = (List<String>) paramM.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramM.get("stuAdvancedList");
		// 处理keyValue中的参数
		String type = null, valueType = null; // 数据类型    数据字段
//		boolean isDate = false, isRc = false;
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String key = entry.getKey(), value = entry.getValue()==null ? null : String.valueOf(entry.getValue());
				if("type".equals(key)){
					type = value;
				}else if("valueType".equals(key)){
					valueType = value;
//					if(!"count".equals(valueType)) isRc = true;
				}else{
//					if(key.equals("date")) isDate = true;
					AdvancedUtil.add(stuAdvancedList, getDetailAdp(key, value));
				}
			}
		//增加学年过滤条件，防止下钻是数据与总数不照应
		String[] ary=EduUtils.getSchoolYearTerm(MapUtils.getString(keyValue, "date"));
		ary[0]=ary[0].substring(0, 4);//转换学年
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Common_SCHOOL_YEAR, ary[0]));//增加学年过滤
		String stuSql = businessDao.getStuDetailSql(deptList, stuAdvancedList),
			   sql    = null;
		// 分析是哪中异常预警
		if(type == null){
			sql = getDetailSqlByType(stuSql, Key_skipClasses) + " union all " +
					getDetailSqlByType(stuSql, Key_stayLate) + " union all " +
					getDetailSqlByType(stuSql, Key_notStay) + " union all " +
					getDetailSqlByType(stuSql, Key_stayNotin);
		}else{
			sql = getDetailSqlByType(stuSql, type);
		}
		// 特殊参数处理
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String key = entry.getKey(), value = entry.getValue()==null ? null : String.valueOf(entry.getValue());
				// 年龄  教龄  特殊处理
				String sql2 = "select * from ("+sql+") ";
				if(key.equals("date")){
					sql2 += " where date_ = '"+value+"'";
					sql = sql2;
				}
			}

		// 是人次、还是人数、比例
		switch (valueType) {
		case "count": // 人数
			sql = "select distinct(t.no) a_,t.* from ("+sql+") t";
			break;
		case "rc": // 人次
			
			break;
		case "scale": // 比例
			
			break;
		default:
			break;
		}
		// 预警
		String detailSql = sql;
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			detailSql = "select "+StringUtils.join(fields, ",")+ " from ("+detailSql+")";
		}
		return detailSql;
	}
	
	@SuppressWarnings("unchecked")
	private String getDetailOtherSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields) {
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, MapUtils.getString(keyValue, "schoolYear"), MapUtils.getString(keyValue, "termCode"));
		List<String> deptList = (List<String>) paramM.get("deptList");
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramM.get("stuAdvancedList");
		String schoolYear=(String) paramM.get("schoolYear");
		String termCode=(String)paramM.get("termCode");
		int year= MapUtils.getIntValue(paramM, "year");
		// 获取学年学期对应时间段
		String[] dates = new String[2];
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t where t.school_year='"+schoolYear+"' and t.term_code='"+termCode+"'");
		for(Map<String, Object> map : list){
			dates[0] = MapUtils.getString(map, "start_date");
			dates[1] = MapUtils.getString(map, "end_date");
		}
		String stuSql = businessDao.getStuDetailSql(deptList, stuAdvancedList),detailSql=null,sql=null;
		String stayLateSql="select '"+Name_stayLate+"' bz,t.* from ("+stuWarningDao.getStayLateRcSql(year, deptList, stuAdvancedList, dates)+") t",
			   notStaySql="select '"+Name_notStay+"' bz,t.* from ("+stuWarningDao.getNotStayRcSql(year, deptList, stuAdvancedList, dates)+") t",
			   skipClaSql="select '"+Name_skipClasses+"' bz,t.* from ("+stuWarningDao.getSkipClassesRcSql(year, deptList, stuAdvancedList, dates)+") t",
			   stayNotionSql="select '"+Name_stayNotin+"' bz,t.* from ("+stuWarningDao.getStayNotinRcSql(year, deptList, stuAdvancedList, dates)+") t";
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String type=entry.getKey();
				switch (type) {
				case "type": //预警类型分布
					String code=(String) entry.getValue();//前台传进请求类型
					switch(code){
					case Key_skipClasses:
						sql="select s.*,t.bz from ("+skipClaSql+") t,("+Constant.TABLE_T_STU_WARNING_SKIP_CLASSES+") s where t.stu_id=s.stu_id and t.date_=s.date_";
						sql="select  t.stu_id,t.date_,t.bz||':'||t.date_||','||s.period as bz from ("+sql+") t ,T_COURSE_ARRANGEMENT s where t.course_arrangement_id = s.id";
						break;
					case Key_stayLate:
						sql=stayLateSql;
						break;	
					case Key_stayNotin:
						sql=stayNotionSql;
						break;
					case Key_notStay:
						sql=notStaySql;
						break;
					}
					break;
				case "grade": // 年级分布
					code=(String) entry.getValue();//年级信息
					sql="select * from ("+stayLateSql+" union all "+notStaySql+" union all "+skipClaSql+" union all "+stayNotionSql+")";
					stuSql="select t.* from (select t.*,to_char("+year+"+1-t.enroll_grade) grade from ("+stuSql+") t) t where grade='"+code+"'";
					break;
				case "sex": // 性别分布
					code=(String) entry.getValue();//年级信息
					sql="select * from ("+stayLateSql+" union all "+notStaySql+" union all "+skipClaSql+" union all "+stayNotionSql+")";
					stuSql="select t.* from ("+stuSql+") t where t.sexId='"+code+"'";
					break;
				case "dept": // 部门占比
					Map<String,String> map=MapUtils.getMap(keyValue, "dept");
					code=MapUtils.getString(map, "code");
					String deptId2 = MapUtils.getString(map, "deptId");
					Integer level2 = MapUtils.getInteger(map, "level");
					if(level2==null) level2 = businessService.getLevelById(deptId2);
					deptList=PmsUtils.getDeptListByDeptAndLevel(deptId2, level2);
					stuSql = businessDao.getStuDetailSql(deptList, stuAdvancedList);
					switch(code){
					case Key_skipClasses:
						sql="select s.*,t.bz from ("+skipClaSql+") t,("+Constant.TABLE_T_STU_WARNING_SKIP_CLASSES+") s where t.stu_id=s.stu_id and t.date_=s.date_";
						sql="select  t.stu_id,t.date_,t.bz||':'||t.date_||','||s.period as bz from ("+sql+") t ,T_COURSE_ARRANGEMENT s where t.course_arrangement_id = s.id";
						break;
					case Key_stayLate:
						sql=stayLateSql;
						break;	
					case Key_stayNotin:
						sql=stayNotionSql;
						break;
					case Key_notStay:
						sql=notStaySql;
						break;
					}
					break;
				case "week": // 逃课周次分布
					code=(String) entry.getValue();
					sql=stuWarningDao.getSkipClassStuSqlByWeekDay(year, deptList, stuAdvancedList, dates, code);
					sql="select  t.stu_id,t.date_,s.period as bz from ("+sql+") t ,T_COURSE_ARRANGEMENT s where t.course_arrangement_id = s.id";
					break;
				case "clas": // 逃课周次分布
					code=(String) entry.getValue();
					String[] period=code.split(",");
					sql=stuWarningDao.getSkipClassStuSqlByPeriod(year, deptList, stuAdvancedList, dates, period[0], period[1]);
					sql="select  t.stu_id,t.date_,s.period as bz from ("+sql+") t ,T_COURSE_ARRANGEMENT s where t.course_arrangement_id = s.id";
					break;
				case "again": // 逃课周次分布
					code=(String) entry.getValue();
					period=code.split(",");
					sql=stuWarningDao.getSkipClassStuByagain(year, deptList, stuAdvancedList, dates, period[0], period[1]);
					sql="select t.stu_id,t.date_,' ' as bz from ("+sql+") t ";
					break;
				default:
					break;
				}
			}
		detailSql ="select stu.*,t.date_,t.bz from ("+sql+") t,("+stuSql+") stu where t.stu_id=stu.no " ;
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			detailSql = "select "+StringUtils.join(fields, ",")+ " from ("+detailSql+") ";
		}
		return detailSql;
	}
	/**
	 * 获取高级参数
	 * @param key
	 * @param value
	 * @return AdvancedParam
	 */
	private AdvancedParam getDetailAdp(String key, String value){
		AdvancedParam adp = null;
		if(key != null){
			String group = AdvancedUtil.Group_Stu, code = null;
			switch (key) {
			case AdvancedUtil.Common_DEPT_ID:
			case AdvancedUtil.Common_DEPT_TEACH_ID:
			case AdvancedUtil.Common_DEPT_TEACH_TEACH_ID:
				group = AdvancedUtil.Group_Common;
				code  = key;
				break;
			default:
				break;
			}
			adp = new AdvancedParam(group, code, value);
		}
		return adp;
	}
	/**
	 * 根据类型获得详情Sql
	 * @param stuSql 学生sql
	 * @param type 类型
	 * @return String
	 */
	private String getDetailSqlByType(String stuSql, String type){
		String sql = null;
		switch (type) {
		case Key_skipClasses:
//			if(isRc){ // 之前目的是一天多次逃课的只显示一条，但是会造成误解
//				sql = "select t.date_,bz,stu.* from ("+stuSql+") stu, "
//					+ "(select stu_id,date_,'"+Name_skipClasses+"：'||REPLACE(REPLACE(wm_concat(t.date_||'、'||s.period),',','；'),'、',',') bz from "+Constant.TABLE_T_STU_WARNING_SKIP_CLASSES+" t,"
//					+ "T_COURSE_ARRANGEMENT s where t.course_arrangement_id = s.id group by date_, stu_id)t where stu.no=t.stu_id";
//			}else{
				sql = "select t.date_,'"+Name_skipClasses+"：'||t.date_||','||s.period bz,stu.*"
					+ " from ("+stuSql+") stu, " + Constant.TABLE_T_STU_WARNING_SKIP_CLASSES+" t,"
					+ " T_COURSE_ARRANGEMENT s where t.course_arrangement_id = s.id and stu.no=t.stu_id";
//			}
			break;
		case Key_notStay:
			sql = "select t.date_,'"+Name_notStay+"' bz,stu.* from ("+stuSql+") stu, "+Constant.TABLE_T_STU_WARNING_NOTSTAY+" t where stu.no=t.stu_id";
			break;
		case Key_stayLate:
			sql = "select t.date_ date_,'"+Name_stayLate+"' bz,stu.* from ("+stuSql+") stu, "+Constant.TABLE_T_STU_WARNING_STAY_LATE+" t where stu.no=t.stu_id";
			break;
		case Key_stayNotin:
			sql = "select t.date_ date_,'"+Name_stayNotin+"' bz,stu.* from ("+stuSql+") stu, "+Constant.TABLE_T_STU_WARNING_STAY_NOTIN+" t where stu.no=t.stu_id";
			break;
		}
		return sql;
	}
	
	/**
	 * 根据参数优化参数
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getParamsMapByParams(List<AdvancedParam> advancedParamList, String schoolYear, String termCode){
		if(schoolYear == null && termCode == null){
			List<Map<String, Object>> list = businessService.queryBzdmXnXq();
			Map<String, Object> map = (list!=null && list.size()>0) ? list.get(0) : null;
			if(map != null){
				String[] idAry = MapUtils.getString(map, "id").split(",");
				if(idAry.length == 2){
					schoolYear = idAry[0]; termCode = idAry[1];
				}
				if(schoolYear == null && termCode == null){
					String[] termAry = EduUtils.getSchoolYearTerm(DateUtils.getNowDate());
					schoolYear = schoolYear==null ? termAry[0] : schoolYear;
					termCode   = termCode==null ? termAry[1] : termCode;
				}
			}
		}
		Integer year = Integer.valueOf(schoolYear.substring(0, 4));
		List<String> deptList = getDeptDataList();
		advancedParamList = advancedParamList!=null ? advancedParamList : new ArrayList<AdvancedParam>();
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
	//学生逃课分布
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getSkipClassByWeekDayOrClas(List<AdvancedParam> advancedParamList, String schoolYear, String termCode, String mold){
		/** 特殊群体处理 */
		advancedParamList = handleAdvancedParamListByMold(advancedParamList, mold);
		//学生逃课 时间分布
		Map<String, Object> paramM = getParamsMapByParams(advancedParamList, schoolYear, termCode);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) paramM.get("stuAdvancedList");
		List<String> deptList = (List<String>) MapUtils.getObject(paramM, "deptList");
		schoolYear = MapUtils.getString(paramM, "schoolYear"); 
		termCode   = MapUtils.getString(paramM, "termCode");
		Integer year = MapUtils.getInteger(paramM, "year");
		// 获取学年学期对应时间段
		String[] dates = new String[2];
		List<Map<String, Object>> list = baseDao.queryListInLowerKey("select * from t_school_start t where t.school_year='"+schoolYear+"' and t.term_code='"+termCode+"'");
		for(Map<String, Object> map : list){
			dates[0] = MapUtils.getString(map, "start_date");
			dates[1] = MapUtils.getString(map, "end_date");
		}
		List<Map<String,Object>> weekList=getWeekday();
		for(Map<String,Object> m:weekList){
			String weekDay=MapUtils.getString(m, "id");
			String sql=stuWarningDao.getSkipClassStuSqlByWeekDay(year, deptList, stuAdvancedList, dates, weekDay);
			int count=baseDao.queryForCount(sql);
			m.put("value", count);
			m.put("code", weekDay);
		}
		List<Map<String,Object>> classList=getClasList();
		for(Map<String,Object> c:classList){
			String period_start=MapUtils.getString(c, "period_start"),period_end=org.apache.commons.collections.MapUtils.getString(c, "period_end");
			String sql=stuWarningDao.getSkipClassStuSqlByPeriod(year, deptList, stuAdvancedList, dates, period_start, period_end);
			int count=baseDao.queryForCount(sql);
			c.put("value", count);
			c.put("code", period_start+","+period_end);
		}
		Map<String,Object> map=DevUtils.MAP();
		List<Map<String,Object>> hiList=getMax(weekList);
		List<Map<String,Object>> cList=getMax(classList);
		Map<String,Object> msgMap=new HashMap<String,Object>();
		String msg1="",msg2="";
		if( !hiList.get(0).containsKey("msg") && !cList.get(0).containsKey("msg")){
			for(int i=0;i<hiList.size();i++){
				if(i==hiList.size()-1){
					msg1+=MapUtils.getString(hiList.get(i), "name");
				}else{
					msg1+=MapUtils.getString(hiList.get(i), "name")+"，";
				}
			}
			for(int i=0;i<cList.size();i++){
				if(i==cList.size()-1){
					msg2+=MapUtils.getString(cList.get(i), "name");
				}else{
					msg2+=MapUtils.getString(cList.get(i), "name")+"，";
				}
			}
			msgMap.put("msg1", msg1);msgMap.put("msg2", msg2);msgMap.put("tag", "1");
		}else{
			msgMap.put("msg1", "无逃课学生");msgMap.put("tag", "2");
		}
		// 逃课概率
		int count =stuWarningDao.getSkipClassStuByAM(year, deptList, stuAdvancedList, dates);
		String sql1=stuWarningDao.getSkipClassStuByagain(year, deptList, stuAdvancedList, dates, "1", "2");//1,2节逃课下午逃课
		String sql2=stuWarningDao.getSkipClassStuByagain(year, deptList, stuAdvancedList, dates, "3", "4");//3,4节逃课下午逃课
		String sql3=stuWarningDao.getSkipClassStuByagain(year, deptList, stuAdvancedList, dates, null,"not");//上午逃课下午不逃课
		int count1=baseDao.queryForCount(sql1),count2=baseDao.queryForCount(sql2);
		int count3=baseDao.queryForCount(sql3);//上午逃课下午不逃课
		Map<String,Object> mapAm=new HashMap<String,Object>(),mapPm=new HashMap<String,Object>(),mapNot=new HashMap<String,Object>();
		mapAm.put("name", "1,2节逃课学生");mapAm.put("value",MathUtils.getPercentNum(count1, count));mapAm.put("code", "1,2");
		mapPm.put("name", "3,4节逃课学生");mapPm.put("value",MathUtils.getPercentNum(count2, count));mapPm.put("code", "3,4");
		mapNot.put("name", "不逃课学生");mapNot.put("value",MathUtils.getPercentNum(count3, count));mapNot.put("code", "not,not");
		List<Map<String,Object>> skipList=new ArrayList<>();skipList.add(mapAm);skipList.add(mapPm);skipList.add(mapNot);
		Map<String,Object> sclsMap=new HashMap<String,Object>();
		if(count!=0){
			if(count1>count2){
				sclsMap.put("name", "1,2节逃课学生");sclsMap.put("value", MathUtils.getPercentNum(count1, count));
			} else if(count1<count2){
				sclsMap.put("name", "3,4节逃课学生");sclsMap.put("value", MathUtils.getPercentNum(count2, count));
			}else{
				sclsMap.put("name", "1,2节逃课和3,4节逃课学生");sclsMap.put("value", MathUtils.getPercentNum(count2, count));
			}
		}
		map.put("week", weekList);//逃课日期（周几）分布
		map.put("clas", classList);//逃课节次分布
		map.put("msg",msgMap);//逃课学生最高日期，节次
		map.put("scls",skipList);//上午逃课下午逃课概率
		map.put("sclsInfo",sclsMap);//1，2 与3，4逃课对比结果
		return map;
	}
	
	private List<Map<String,Object>> getWeekday(){
		List<Map<String,Object>> weekList=new ArrayList<Map<String,Object>>();
		Map<String,Object> m1 =new HashMap<>(),m2=new HashMap<>(),m3=new HashMap<>(),m4=new HashMap<>(),m5=new HashMap<>(),m6=new HashMap<>(),m7=new HashMap<>();
		m1.put("id", "1");m1.put("name", "周日");m2.put("id", "2");m2.put("name", "周一");m3.put("id", "3");m3.put("name", "周二");
		m4.put("id", "4");m4.put("name", "周三");m5.put("id", "5");m5.put("name", "周四");m6.put("id", "6");m6.put("name", "周五");
		m7.put("id", "7");m7.put("name", "周六");
		weekList.add(m2);weekList.add(m3);weekList.add(m4);weekList.add(m5);weekList.add(m6);weekList.add(m7);weekList.add(m1);
		return weekList;
	}
	
	private List<Map<String,Object>> getClasList(){
		Map<String,Object> c1 =new HashMap<>(),c2 =new HashMap<>(),c3 =new HashMap<>(),c4 =new HashMap<>();
		c1.put("id", "1");c1.put("name", "1,2节");c1.put("period_start", "1");c1.put("period_end", "2");
		c2.put("id", "2");c2.put("name", "3,4节");c2.put("period_start", "3");c2.put("period_end", "4");
		c3.put("id", "3");c3.put("name", "5,6节");c3.put("period_start", "5");c3.put("period_end", "6");
		c4.put("id", "4");c4.put("name", "7,8节");c4.put("period_start", "7");c4.put("period_end", "8");
		List<Map<String,Object>> classList=new ArrayList<Map<String,Object>>();
		classList.add(c1);classList.add(c2);classList.add(c3);classList.add(c4);
		return classList;
	}
	private List<Map<String,Object>> getMax(List<Map<String, Object>> list){
		List<Map<String,Object>> highList=new ArrayList<Map<String,Object>>();
		int max = 0;
		for(Map<String, Object> m : list){
			int val = MapUtils.getInteger(m, "value");
			max = val>max ? val : max;
		}
		if(max==0){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("msg", "无逃课学生");
			highList.add(map);
			return highList;
		}else{
			for(Map<String, Object> m : list){
				int val= MapUtils.getInteger(m, "value");
				if(max==val){
					highList.add(m);
				}
			}
			return highList;
		}
		
	}
	
}
