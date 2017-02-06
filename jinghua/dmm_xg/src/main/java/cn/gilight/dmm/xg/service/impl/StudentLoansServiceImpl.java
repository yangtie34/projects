package cn.gilight.dmm.xg.service.impl;

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
import cn.gilight.dmm.xg.dao.ScholarshipDao;
import cn.gilight.dmm.xg.dao.StudentLoansDao;
import cn.gilight.dmm.xg.service.ScholarshipService;
import cn.gilight.dmm.xg.service.StudentLoansService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Service("studentLoansService")
public class StudentLoansServiceImpl implements StudentLoansService {

	@Resource
	private BusinessService businessService;
	@Resource
	private StudentLoansDao studentLoansDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private ScholarshipDao scholarshipDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 助学贷款 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"studentLoans";
	/**
	 * 奖学金表
	 */
	public static Constant.JCZD_Table TABLE = Constant.JCZD_Table.LOAN;
	/**
	 * 获取助学贷款数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	/**
	 * 获取助学贷款数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String pid){
		return businessService.getDeptDataList(ShiroTag, pid);
	}
	/**
	 * 获取学年
	 * @param schoolYear
	 * @return String
	 */
	private String getRealSchoolYear(String schoolYear){
		return EduUtils.getSchoolYear9(schoolYear);
	}
	
	/**
	 * 得到学年
	 */
	@Override
	public List<Map<String , Object>> querySchoolYear() {
		List<Map<String , Object>> list =new ArrayList<>();
		Map<String , Object> map =new HashMap<>();
		map.put("EDU_ID", businessService.queryBzdmStuEducationList(getDeptDataList()));
		map.put("xn", businessService.queryBzdmSchoolYear("T_STU_LOAN","SCHOOL_YEAR"));
		map.put("bzdm", Constant.JCZD_Bzdm_Group_CountMoneyScale); // 标准代码
		list.add(map);
		return list;
	}
	/**
	 * 得到总的助学贷款人数以及金额
	 */
	@Override
	public List<Map<String, Object>> queryZxInfo(String schoolYear, String id,String pid) {
		return queryZxInfo(getDeptDataList(),schoolYear, id,pid);
	}
	@Override
	public List<Map<String, Object>> queryZxInfo(List<String> deptList,
			String schoolYear, String id, String pid) {
		return studentLoansDao.queryZxInfo(deptList, schoolYear, id, pid);
	}
	/**
	 * 查询助学金贷款学生行为
	 */
	@Override
	public Map<String, Object> queryZxxw(String schoolYear, String edu,List<AdvancedParam> advancedParamList) {
		return queryZxxw(getDeptDataList(),advancedParamList,schoolYear, edu,TABLE);
	}
	@Override
	public Map<String, Object> queryZxxw(List<String> deptList,List<AdvancedParam> advancedParamList,
			String schoolYear, String edu,Constant.JCZD_Table table) {
		/**
		 * 查获助学贷款学生、其室友、其他学生
		 * 平均绩点、借阅量、图书馆进出次数、早餐次数、早起次数
		 */
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
//		List<AdvancedParam> advancedList = new ArrayList<>();
//		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Common_EDU_ID, edu));
		String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];
		
		/**
		 * 助学贷款学生、其室友、其他学生
		 */
		String shipSql         = scholarshipDao.getScholarshipStuIdSql(year, deptList, stuAdvancedList, year, table),
				   dormTogetherSql = businessDao.getDormTogetherStuIdSql(shipSql, false),
				   stuSql  = businessDao.getStuSql(year, deptList, stuAdvancedList);
			Map<String, Object> scholarshipMap  = getBehaviorMap(shipSql, schoolYear, termCode),
							dormTogetherMap = getBehaviorMap(dormTogetherSql, schoolYear, termCode),
							stuMap			= getBehaviorMap(stuSql, schoolYear, termCode);
		scholarshipMap.put("name", "获得助学贷款学生"); dormTogetherMap.put("name", "获得助学贷款学生室友"); stuMap.put("name", "全校学生");
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(scholarshipMap); list.add(dormTogetherMap); list.add(stuMap);
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		List<Map<String, Object>> doubleList = new ArrayList<>();
		doubleList.add(scholarshipMap); doubleList.add(dormTogetherMap); doubleList.add(stuMap);
		map.putAll(getBehaviorMaxPolar(doubleList));
		return map;
	}
	/**
	 * 助学贷款分布
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryZxfb(String schoolYear, String edu,String fb,List<AdvancedParam> advancedParamList) {
		/**
		 * 根据类型获取学费减免 人数、总金额、覆盖率
		 */
		String pid = AdvancedUtil.getPid(advancedParamList);
		String fbchoose="COUNT(DISTINCT T.NO)";
		List<String> deptList = getDeptDataList();
//		schoolYear = getRealSchoolYear(schoolYear);
//		int year = Integer.valueOf(EduUtils.getSchoolYear4());
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
//		Map<String, Object> deptDataMap = businessService.getDeptDataForGoingDownStu(deptList, pid, year);
//		deptList = (List<String>) deptDataMap.get("deptList");
		if("money".equals(fb)){
			fbchoose="SUM( T.MONEY)";
		}
		String sql = studentLoansDao.getZxdkStuSql(deptList, advancedParamList, year);
		String dataSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, pid, sql, false, true, false, false, year, null),
				instructorsSql2=" SELECT T.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME,T.TYPEMC TNAME_,"+fbchoose+" COUNT_ FROM ("+dataSql+") T WHERE T.NEXT_DEPT_CODE IS NOT NULL AND T.SCHOOL_YEAR='"+schoolYear+"' GROUP BY T.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME,T.TYPEMC";
		String  stuSql  = businessDao.getStuSql(year, deptList, null),
				stuSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, stuSql, true, true, false, false, year, null);
		List<Map<String, Object>> instructors_li = baseDao.queryListInLowerKey(instructorsSql2);
		List<Map<String, Object>> stu_li = baseDao.queryListInLowerKey(stuSql2);
		// 组织机构
		List<Map<String, Object>> reList = new ArrayList<>();
		/********************************************************/
		for(Map<String, Object> map : stu_li){
			Map<String,Object> map1 =new HashMap<>();
			List<Map<String,Object>> list= new ArrayList<>();
			map1.put("id",map.get(Constant.NEXT_LEVEL_COLUMN_CODE));
			map1.put("name",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
			for(Map<String, Object> stu_map : instructors_li){
				if(map.get(Constant.NEXT_LEVEL_COLUMN_CODE).equals(stu_map.get(Constant.NEXT_LEVEL_COLUMN_CODE))){
				 Map<String,Object> map2=new HashMap<>();
				 if("scale".equals(fb)){
						if(Integer.parseInt((String) map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))==0){
							map2.put("COUNT_",0);
						}else{
							double stuCount=Integer.parseInt((String) stu_map.get("count_")),
									zCount=Integer.parseInt((String) map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
//							  double scale = (stuCount/zCount)*100;//*0.01d;
							  Double scale = MathUtils.getPercentNum(stuCount, zCount);
						map2.put("COUNT_",scale);
						}
				 }else{
				map2.put("COUNT_",stu_map.get("count_"));
				 }
				map2.put("TNAME_",stu_map.get("tname_"));
				list.add(map2);
				map1.put("list", list);
			}
		}
			reList.add(map1);
		}
		/********************************************************************************/
//		List<Map<String, Object>> nextLevelList = (List<Map<String, Object>>) deptDataMap.get("queryList");
//		List<AdvancedParam> advancedList = new ArrayList<>();
//		AdvancedParam ad_edu  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu),
//					  ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
//		advancedList.add(ad_edu);// advancedList.add(ad_dept);
//		
//		for(Map<String, Object> map : nextLevelList){
//			String id_ = MapUtils.getString(map, "id"),
//					level_type = MapUtils.getString(map, "level_type");
//			ad_dept.setValues(id_);
//			// 人数、总金额,覆盖率
//			List<Map<String, Object>> li = studentLoansDao.queryZxfb(year, deptList, advancedList,fb,id_);
//			map.put("list", li);
//			reList.add(map);
//	}
		// return
		Map<String, Object>	map = DevUtils.MAP();
		List<Map<String, Object>> lx=studentLoansDao.queryJmType();
		map.put("lx", lx);
		map.put("slx", fb);
		map.put("list", reList);
		map.put("bzdm", Constant.JCZD_Bzdm_Group_CountMoneyScale); // 标准代码
		return map;
	}

	/**
	 * 查询历年助学贷款变化
	 */
	@Override
	public Map<String, Object> queryYearZxfb(String bh,List<AdvancedParam> advancedParamList) {
		return queryYearZxfb(getDeptDataList(),bh,advancedParamList);
	}

	@Override
	public Map<String, Object> queryYearZxfb(List<String> deptList,
			String bh,List<AdvancedParam> advancedParamList) {
		List<Object> listyear= new ArrayList<>();//得到学年
		List<Map<String, Object>> list=new ArrayList<>();
		int schoolYear = EduUtils.getSchoolYear4();//得到当前学年
		for(int year=schoolYear-5;year<=schoolYear;year++){
			Map<String, Object> map =new HashMap<>();
			listyear.add(year);
			map.put("year", year);
			List<Map<String, Object>> li=studentLoansDao.queryYearZxfb(deptList,year,bh,advancedParamList);
			map.put("list", li);
			list.add(map);
		}
		Map<String, Object>	map = DevUtils.MAP();
		map.put("year", listyear);
		map.put("slx", bh);
		map.put("lx", studentLoansDao.queryJmType());
		map.put("list", list);
		return map;
	}
	/**
	 * 获取 学生在某一学年的（平均绩点、借阅量、图书馆进出次数、早餐次数、早起次数）
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getBehaviorMap(String stuSql, String schoolYear, String termCode){
		stuSql = "select stu.* from t_stu stu, ("+stuSql+")t where stu.no_=t.no_";
		Double ship_score     = businessDao.queryAvgScore(stuSql, schoolYear, termCode),
			   ship_bookCount = businessDao.queryAvgBookCount(stuSql, schoolYear, termCode),
			   ship_bookRke   = businessDao.queryAvgBookRke(stuSql, schoolYear, termCode),
			   ship_breakfastCount = businessDao.queryAvgBreakfastCount(stuSql, schoolYear, termCode),
			   ship_earlyCount = businessDao.queryAgeEarlyCount(stuSql, schoolYear, termCode);
		Map<String, Object> map = new HashMap<>();
		map.put(key_score, ship_score);
		map.put(key_earlyCount, ship_earlyCount);
		map.put(key_breakfastCount, ship_breakfastCount);
		map.put(key_bookRke, ship_bookRke);
		map.put(key_bookCount, ship_bookCount);
		return map;
	}
	/**
	 * 助学贷款行为 - key
	 */
	private static final String key_score = "score";
	private static final String key_earlyCount = "earlyCount";
	private static final String key_breakfastCount = "breakfastCount";
	private static final String key_bookRke = "bookRke";
	private static final String key_bookCount = "bookCount";
	/**
	 * 获取行为数据的最大指标
	 */
	private Map<String, Object> getBehaviorMaxPolar(List<Map<String, Object>> list){
		List<Double> scoreList     = new ArrayList<>(),
					 earlyList     = new ArrayList<>(),
					 breakfastList = new ArrayList<>(),
					 bookRkeList   = new ArrayList<>(),
					 bookList      = new ArrayList<>();
		for(Map<String, Object> map : list){
			scoreList.add(MapUtils.getDouble(map, key_score));
			earlyList.add(MapUtils.getDouble(map, key_earlyCount));
			breakfastList.add(MapUtils.getDouble(map, key_breakfastCount));
			bookRkeList.add(MapUtils.getDouble(map, key_bookRke));
			bookList.add(MapUtils.getDouble(map, key_bookCount));
		}
		Double coefficient = 1.2;
		Map<String, Object> polarMap = new HashMap<>();
		polarMap.put(key_score, MathUtils.getMaxValue(scoreList)*coefficient);
		polarMap.put(key_earlyCount, MathUtils.getMaxValue(earlyList)*coefficient);
		polarMap.put(key_breakfastCount, MathUtils.getMaxValue(breakfastList)*coefficient);
		polarMap.put(key_bookRke, MathUtils.getMaxValue(bookRkeList)*coefficient);
		polarMap.put(key_bookCount, MathUtils.getMaxValue(bookList)*coefficient);
		return polarMap;
	}
	/**
	 * 下钻详情
	 */
	@Override
	public Map<String, Object> getStuDetail(
			List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields) {
		AdvancedParam ad_year=new AdvancedParam();
		ad_year.setCode(AdvancedUtil.Common_SCHOOL_YEAR);
		ad_year.setValues(String.valueOf(EduUtils.getSchoolYear4()));
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(ad_year);
		String stuDetailSql = getStuDetailSql(stuAdvancedList, keyValue, fields);
	// 
	Map<String, Object> map = baseDao.createPageQueryInLowerKey(stuDetailSql, page);
	return map;
	}
	private String getStuDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields) {
		String year ="2016",rs = null,je = null,dept = null,type = null,code = null,jmtype=null;
		if(keyValue != null){
			year=(String) keyValue.get("year");
			if(year.contains("-")){
				year=year.split("-")[0];
			}
			rs=(String) keyValue.get("rs");
			je=(String) keyValue.get("je");
			dept=(String) keyValue.get("deptmc");
			type=(String) keyValue.get("typemc");
			code=(String) keyValue.get("code");
			jmtype=(String) keyValue.get("jmtype");
		}
		String  stuDetailSql=studentLoansDao.getZxdkStuSql(getDeptDataList(), advancedParamList,Integer.parseInt(year));
		if("rs".equals(code)){
			stuDetailSql="SELECT  T.NO,T.NAME,T.SCHOOL_YEAR,T.SEXMC,T.DEPTMC,T.MAJORMC,T.CLASSMC FROM ("+stuDetailSql+") T  WHERE SUBSTR(T.SCHOOL_YEAR,0,4)='"+year+"' AND T.NO IS NOT NULL GROUP BY T.NO,T.NAME,T.SCHOOL_YEAR,T.SEXMC,T.DEPTMC,T.MAJORMC,T.CLASSMC";
		}else if("je".equals(code)){
			stuDetailSql="SELECT  * FROM ("+stuDetailSql+") T  WHERE SUBSTR(T.SCHOOL_YEAR,0,4)='"+year+"' AND T.NO IS NOT NULL";
		}else if("jmfb".equals(code)){
			stuDetailSql="SELECT * FROM ("+stuDetailSql+") T WHERE SUBSTR(T.SCHOOL_YEAR,0,4)='"+year+"' AND T.NO IS NOT NULL AND T.DEPTMC = '"+dept+"' AND T.TYPEMC='"+type+"'";
		}else if("lnfb".equals(code)){
			stuDetailSql=studentLoansDao.getZxdkStuSql(getDeptDataList(), advancedParamList,(int) keyValue.get("school_year"));
			stuDetailSql="SELECT * FROM ("+stuDetailSql+") T WHERE SUBSTR(T.SCHOOL_YEAR,0,4)='"+keyValue.get("school_year")+"' AND T.NO IS NOT NULL AND  T.TYPEMC='"+type+"'";
		}else if("xrs".equals(code)){
			stuDetailSql="SELECT  T.NO,T.NAME,T.SCHOOL_YEAR,T.SEXMC,T.DEPTMC,T.MAJORMC,T.CLASSMC FROM ("+stuDetailSql+") T  WHERE SUBSTR(T.SCHOOL_YEAR,0,4)='"+year+"' AND T.TYPEMC='"+jmtype+"' AND T.NO IS NOT NULL   GROUP BY T.NO,T.NAME,T.SCHOOL_YEAR,T.SEXMC,T.DEPTMC,T.MAJORMC,T.CLASSMC";
		}
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") ";
		}
		return stuDetailSql;
	}
	@Override
	public List<Map<String, Object>> getStuDetailAll(List<AdvancedParam> advancedParamList,Page page, Map<String, Object> keyValue, List<String> fields){
//		return baseDao.queryListInLowerKey(getStuDetailSql(advancedParamList, keyValue, fields));
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(getStuDetailSql(advancedParamList, keyValue, fields), page);
		return (List<Map<String, Object>>) map.get("rows");
	}
}
