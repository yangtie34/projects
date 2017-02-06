package cn.gilight.dmm.xg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
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
import cn.gilight.dmm.xg.dao.ScholarshipDao;
import cn.gilight.dmm.xg.pojo.ScholarshipTop;
import cn.gilight.dmm.xg.service.ScholarshipService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 奖学金
 * 
 * @author xuebl
 * @date 2016年5月12日 下午5:17:45
 */
@Service("scholarshipService")
public class ScholarshipServiceImpl implements ScholarshipService {

	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private ScholarshipDao scholarshipDao;
	
	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"scholarship";
	/**
	 * 奖学金表
	 */
	public static Constant.JCZD_Table TABLE = Constant.JCZD_Table.AWARD;
	
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	
	@Override
	public Map<String, Object> getBzdm(){
		Map<String, Object> map = DevUtils.MAP();
		map.put("xn", businessService.queryBzdmSchoolYear());
		map.put(AdvancedUtil.Stu_EDU_ID, businessService.queryBzdmStuEducationList(getDeptDataList()));
//		map.put(AdvancedUtil.Stu_EDU_ID, businessService.queryBzdmStuEducationList());
		return map;
	}
	
	@Override
	public Map<String, Object> getAbstract(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return getAbstract(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}

	@Override
	public Map<String, Object> getAbstract(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table) {
		/**
		 * 人数、金额、覆盖率
		 * 学年：schoolYear
		 * 学生：权限、培养层次、
		 */
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		// query
		Map<String, Object> absMap = scholarshipDao.queryCountAndMoney(year, deptList, stuAdvancedList, year, table);
		long money = MapUtils.getLong(absMap, "money");
		int count   = MapUtils.getInteger(absMap, "count"),
			xsCount = businessDao.queryStuCount(year, deptList, stuAdvancedList);
		Map<String, Object> map = new HashMap<>();
		map.put("count", count);
		map.put("money", money);
		map.put("scale", MathUtils.getPercentNum(count, xsCount));
		return map;
	}
	
	
	@Override
	public Map<String, Object> queryTypeList(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return queryTypeList(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}

	@Override
	public Map<String, Object> queryTypeList(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table) {
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		// query
		List<Map<String, Object>> list = scholarshipDao.queryTypeList(year, deptList, stuAdvancedList, year, table);
		int xsCount = businessDao.queryStuCount(year, deptList, stuAdvancedList);
		// 雷达图：单项金额、覆盖率、总金额
		Double money_max = 0D, money_one_max = 0D, scale_max = 0D;
		for(Map<String, Object> map : list){
			int count = MapUtils.getInteger(map, "count"),
				money = MapUtils.getInteger(map, "money");
			Double money_one = MathUtils.getDivisionResult(money, count, 0),
				   scale = MathUtils.getPercentNum(count, xsCount);
			money_max = money>money_max ? money : money_max;
			money_one_max = money_one>money_one_max ? money_one : money_one_max;
			scale_max = scale>scale_max ? scale : scale_max;
			map.put("scale", scale);
			map.put("money_one", money_one);
		}
		money_max = money_max*1.2;
		money_one_max = money_one_max*1.2;
		scale_max = scale_max*1.2;
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("money_max", money_max);
		map.put("money_one_max", money_one_max);
		map.put("scale_max", scale_max);
		return map;
	}
	
	@Override
	public Map<String, Object> queryDeptDataList(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return queryDeptDataList(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryDeptDataList(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table) {
		/**
		 * 根据类型获取奖学金 人数、总金额、覆盖率
		 */
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		String pid = AdvancedUtil.getPid(advancedParamList);
		Map<String, Object> deptDataMap = businessService.getDeptDataForGoingDownStu(deptList, pid, year);
		deptList = (List<String>) deptDataMap.get("deptList");
		// 组织机构
		List<Map<String, Object>> reList = new ArrayList<>();
		List<Map<String, Object>> nextLevelList = (List<Map<String, Object>>) deptDataMap.get("queryList");
		advancedParamList = advancedParamList!=null ? advancedParamList : new ArrayList<AdvancedParam>();
		AdvancedParam ad_edu  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
//					  ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
		advancedParamList.add(ad_edu);
//		advancedParamList.add(ad_dept);
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		for(Map<String, Object> map : nextLevelList){
			deptList = PmsUtils.getDeptListByDeptMap(map);
			// 人数、总金额
			List<Map<String, Object>> li = scholarshipDao.queryTypeList(year, deptList, stuAdvancedList, year, table);
			// 覆盖率
			int xsCount = businessDao.queryStuCount(year, deptList, stuAdvancedList);
			Collections.reverse(li);
			for(Map<String, Object> m : li){
				int count = MapUtils.getInteger(m, "count");
				Double scale = MathUtils.getPercentNum(count, xsCount);
				m.put("scale", scale);
			}
			map.put("list", li);
			reList.add(map);
		}
		// 当前组织机构类型名称（院系、专业）
		String deptMc = businessService.getLevelNameById(nextLevelList.isEmpty() ? null : MapUtils.getString(nextLevelList.get(0), "id"));
		// return
		Map<String, Object>	map = DevUtils.MAP();
		map.put("list", reList);
		map.put("bzdm", Constant.JCZD_Bzdm_Group_CountMoneyScale); // 标准代码
		map.put("deptMc", deptMc);
		return map;
	}
	
	/**
	 * 获取学年
	 * @param schoolYear
	 * @return String
	 */
	private String getRealSchoolYear(String schoolYear){
		return EduUtils.getSchoolYear9(schoolYear);
	}
	
	@Override
	public Map<String, Object> getBehavior(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return getBehavior(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}

	@Override
	public Map<String, Object> getBehavior(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table) {
		/**
		 * 查获奖学金学生、其室友、其他学生
		 * 平均绩点、借阅量、图书馆进出次数、早餐次数、早起次数
		 */
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
//		String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];
		String termCode = null;
		/**
		 * 奖学金学生、其室友、其他学生
		 */
		String shipSql         = scholarshipDao.getScholarshipStuIdSql(year, deptList, stuAdvancedList, year, table),
			   dormTogetherSql = businessDao.getDormTogetherStuIdSql(shipSql, false),
			   stuSql  = businessDao.getStuSql(year, deptList, stuAdvancedList);
		Map<String, Object> scholarshipMap  = getBehaviorMap(shipSql, schoolYear, termCode),
							dormTogetherMap = getBehaviorMap(dormTogetherSql, schoolYear, termCode),
							stuMap			= getBehaviorMap(stuSql, schoolYear, termCode);
		scholarshipMap.put("name", "获得奖学金学生"); dormTogetherMap.put("name", "获得奖学金学生室友"); stuMap.put("name", "全校学生");
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
	 * 奖学金行为 - key
	 */
	private static final String key_score = "score";
	private static final String key_earlyCount = "earlyCount";
	private static final String key_breakfastCount = "breakfastCount";
	private static final String key_bookRke = "bookRke";
	private static final String key_bookCount = "bookCount";
	/**
	 * 获取 学生在某一学年的（平均绩点、借阅量、图书馆进出次数、早餐次数、早起次数）（平均是指每月）
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
		map.put(key_bookRke, ship_bookRke);
		map.put(key_breakfastCount, ship_breakfastCount);
		map.put(key_bookCount, ship_bookCount);
		return map;
	}
	
	@Override
	public Map<String, Object> getCoverageGrade(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return getCoverageGrade(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}

	@Override
	public Map<String, Object> getCoverageGrade(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table) {
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		advancedParamList = advancedParamList!=null ? advancedParamList : new ArrayList<AdvancedParam>();
		advancedParamList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		/**
		 * 一二三四五年级 奖学金覆盖率、累计覆盖率
		 * 
		 * 三年级学生：学年(2014)、年级(3)、
		 * 覆盖率：奖学金学生(学年)/学生(三年级)；奖学金学生：学年(2014)
		 * 累计覆盖率：奖学金学生(总计去重)/学生(总计去重)
		 */
		// 各年级
		List<Map<String, Object>> list       = new ArrayList<>(),
								  bzdmNjList = businessService.queryBzdmNj();
		AdvancedParam ad_grade  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_GRADE, null);
		advancedParamList.add(ad_grade);
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		for(Map<String, Object> map : bzdmNjList){
			ad_grade.setValues(MapUtils.getString(map, "id"));
			// 判断这个年级是否有学生
			int grade_stu_count = businessDao.queryStuCount(year, deptList, stuAdvancedList);
			if(grade_stu_count == 0){
				break;
			}
			/**
			 * 指定学年、指定年级学生
			 * 指定学年、指定年级学生，指定获奖学年学生
			 * 指定学年、指定年级学生，不指定获奖学年学生
			 */
			int ship_grade_stu_count = baseDao.queryForCount(scholarshipDao.getScholarshipStuIdSql(year, deptList, stuAdvancedList, year, table));
			int ship_stu_count = baseDao.queryForCount(scholarshipDao.getScholarshipStuIdSql(year, deptList, stuAdvancedList, null, table));
			Map<String, Object>	oneMap = new HashMap<>();
			oneMap.put("name", MapUtils.getString(map, "mc"));
			oneMap.put("scale_one", MathUtils.getPercentNum(ship_grade_stu_count, grade_stu_count));
			oneMap.put("scale_all", MathUtils.getPercentNum(ship_stu_count, grade_stu_count));
			list.add(oneMap);
		}
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", list);
		return reMap;
	}
	
	@Override
	public Map<String, Object> getCoverageDept(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return getCoverageDept(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}

	@Override
	public Map<String, Object> getCoverageDept(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table) {
		/**
		 * 初始化基础数据：学年、学历、封装的高级查询参数、奖学金标准代码
		 */
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		String pid = AdvancedUtil.getPid(advancedParamList);
		// 获取标准代码
		List<Map<String, Object>> bzdmList = businessService.queryBzdmListMap(table.getCodeType());
		List<String> codeList = ListUtils.getListValueFromListMap(bzdmList, "id"); // 必须紧跟bzdmList之后，因为后面list已经被改变了
		// 返回值
		Map<String, Object> reMap   = new HashMap<>(),
							bzdmAll = new HashMap<>();
		bzdmAll.put("id", Constant.CODE_All); bzdmAll.put("mc", "所有"+table.getName());
		bzdmList.add(0, bzdmAll);
		reMap.put("bzdm", bzdmList);
		/**
		 * 不同院系、不同奖学金类别累计覆盖率
		 * 1.获取下级组织机构
		 * 2.循环院系
		 * 3.查询各个类型奖学金覆盖率：奖学金人数、（哪个学年学生、奖学金类型、获奖学年不限），总人数（学年），除法
		 */
		// 需要展示的组织机构列表
		List<Map<String, Object>> deptMapList = businessService.getDeptDataListForGoingDownStu(deptList, pid, year);
		for(Map<String, Object> map : deptMapList){
			deptList = PmsUtils.getDeptListByDeptMap(map);
			// re
			List<Map<String, Object>> oneList = new ArrayList<>();
			Map<String, Object> oneMap = new HashMap<>();
			oneList.add(oneMap);
			map.put("list", oneList);
			/**
			 * 指定学年学生
			 * 指定学年学生，不指定获奖学年学生
			 */
			String grade_stu_sql = businessDao.getStuSql(year, deptList, stuAdvancedList),
				   ship_stu_sql  = scholarshipDao.getScholarshipStuIdSql(year, deptList, stuAdvancedList, null, table);
			int grade_stu_count  = baseDao.queryForCount(grade_stu_sql),
				ship_stu_count   = baseDao.queryForCount(ship_stu_sql);
			oneMap.put(Constant.CODE_All, MathUtils.getPercentNum(ship_stu_count, grade_stu_count));
			for(String code : codeList){
				int ship_stu_code_count = baseDao.queryForCount(scholarshipDao.getScholarshipStuIdSql(year, deptList, stuAdvancedList, null, code, table));
				oneMap.put(code, MathUtils.getPercentNum(ship_stu_code_count, grade_stu_count));
			}
		}
		// 当前组织机构类型名称（院系、专业）
		String deptMc = businessService.getLevelNameById(deptMapList.isEmpty() ? null : MapUtils.getString(deptMapList.get(0), "id"));
		reMap.put("list", deptMapList);
		reMap.put("deptMc", deptMc);
		return reMap;
	}
	
	@Override
	public Map<String, Object> getCoverageGradeSex(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList();
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		advancedParamList = advancedParamList!=null ? advancedParamList : new ArrayList<AdvancedParam>();
		advancedParamList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		/**
		 * 一二三四五年级 奖学金覆盖率、累计覆盖率
		 * 
		 * 三年级学生：学年(2014)、年级(3)、
		 * 覆盖率：奖学金学生(学年)/学生(三年级)；奖学金学生：学年(2014)
		 * 累计覆盖率：奖学金学生(总计去重)/学生(总计去重)
		 */
		// 各年级
		List<Map<String, Object>> relist     = new ArrayList<>(),
								  bzdmNjList = businessService.queryBzdmNj(),
								  bzdmSexList= businessService.queryBzdmSex();
		AdvancedParam ad_grade  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_GRADE, null),
						ad_sex  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Common_SEX_CODE, null);
		advancedParamList.add(ad_grade); advancedParamList.add(ad_sex);
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		for(Map<String, Object> map : bzdmNjList){
			ad_grade.setValues(null);
			// 判断这个年纪是否有学生
			if(businessDao.queryStuCount(year, deptList, stuAdvancedList) == 0){
				break;
			}
			// 设置年级条件
			ad_grade.setValues(MapUtils.getString(map, "id"));
			/**
			 * 指定学年、指定年级学生、指定获奖学年 的条件下
			 * (男、女)
			 */
			// re
			List<Map<String, Object>> oneList = new ArrayList<>();
			map.put("list", oneList);
			ad_sex.setValues(null);
			int count_all = baseDao.queryForCount(scholarshipDao.getScholarshipStuIdSql(year, deptList, stuAdvancedList, year, TABLE));
			int count_sum = 0;
			for(Map<String, Object> sex : bzdmSexList){
				String id = MapUtils.getString(sex, "id");
				ad_sex.setValues(id);
				int count_one = baseDao.queryForCount(scholarshipDao.getScholarshipStuIdSql(year, deptList, stuAdvancedList, year, TABLE));
				Map<String, Object> oneMap = new HashMap<>();
				oneMap.put("name", MapUtils.getString(sex, "mc"));
				oneMap.put("value", count_one);
				oneMap.put("code", id);
				oneList.add(oneMap);
				count_sum += count_one;
			}
			// 有未知性别
			if(count_sum < count_all){
				Map<String, Object> oneMap = new HashMap<>();
				oneMap.put("name", Constant.CODE_Unknown_Name);
				oneMap.put("value", count_all-count_sum);
				oneMap.put("code", null);
				oneList.add(oneMap);
			}
			for(Map<String, Object> oneMap : oneList){
				oneMap.put("scale", MathUtils.getPercentNum(MapUtils.getInteger(oneMap, "value"), count_all));
			}
			// 设置属性
			map.put("name", MapUtils.getString(map, "mc"));
			relist.add(map);
		}
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", relist);
		return reMap;
	}
	
	@Override
	public Map<String, Object> getTop(String schoolYear, String edu, List<AdvancedParam> advancedParamList, String desc_column, Integer index){
		List<String> deptList = getDeptDataList();
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		// 查询并排序，只支持 次数和金额排序，连续获奖次数不支持sql排序
		String do_desc_column = null; //去排序的字段
		if(desc_column == null || desc_column.equals(Constant.JCZD_Desc_Column.continue_count.getValue())){
			do_desc_column = Constant.JCZD_Desc_Column.count.getValue();
		}else{
			do_desc_column = desc_column;
		}
		List<ScholarshipTop> list = scholarshipDao.queryTopList(year, deptList, stuAdvancedList, null, do_desc_column, TABLE);
		if(desc_column != null && desc_column.equals(Constant.JCZD_Desc_Column.continue_count.getValue())){
			Collections.sort(list);
		}
		index = index==null ? 1 : index;
		int pageSize  = 5;
		int pageCount = new Double(Math.ceil(MathUtils.getDivisionResult(list.size(), pageSize, 1))).intValue();
		int size = list.size();
		int start = (index-1)*pageSize; start = start>size ? size : start;
		int end = index*pageSize; end = end>size ? size : end;
		list = list.subList(start, end);
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("pageCount", pageCount);
		return map;
	}
	
	@Override
	public Map<String, Object> getHistory(String schoolYear, String edu, List<AdvancedParam> advancedParamList){
		return getHistory(getDeptDataList(), advancedParamList, schoolYear, edu, TABLE);
	}

	@Override
	public Map<String, Object> getHistory(List<String> deptList, List<AdvancedParam> advancedParamList, 
			String schoolYear, String edu, Constant.JCZD_Table table) {
		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));

		List<Map<String, Object>> reList = new ArrayList<>();
		int year_length = Constant.Year_His_Len;
		for( ; year_length>0; year_length--){
			int year_2 = year+1-year_length;
			// 人数、总金额
			List<Map<String, Object>> li = scholarshipDao.queryTypeList(year_2, deptList, stuAdvancedList, year_2, table);
			// 覆盖率
			int xsCount = businessDao.queryStuCount(year_2, deptList, stuAdvancedList);
			Collections.reverse(li);
			for(Map<String, Object> m : li){
				int count = MapUtils.getInteger(m, "count");
				Double scale = MathUtils.getPercentNum(count, xsCount);
				m.put("scale", scale);
			}
			Map<String, Object> map = new HashMap<>();
			map.put("name", year_2+"-"+(year_2+1));
			map.put("list", li);
			reList.add(map);
		}
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", reList);
		reMap.put("bzdm", Constant.JCZD_Bzdm_Group_CountMoneyScale); // 标准代码
		return reMap;
	}
	@Override
	public Map<String ,Object> getStuScholarshipDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields){
		return getStuScholarshipDetail(advancedParamList,page,keyValue,fields,TABLE);
	}
	@Override
	public Map<String ,Object> getStuScholarshipDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields,Constant.JCZD_Table table){
		List<String> deptList = getDeptDataList(); // 权限
		String schoolYear=MapUtils.getString(keyValue, "value_year"),edu=MapUtils.getString(keyValue, "value_edu");
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		String stuDetailSql=scholarshipDao.getStuDetailSql(year, deptList, stuAdvancedList, keyValue, fields, table);
		Map<String,Object> map=baseDao.createPageQueryInLowerKey(stuDetailSql, page);
		return map;
	}
	@Override
	public List<Map<String, Object>> getStuDetailList(List<AdvancedParam> advancedParamList,Page page,
			Map<String, Object> keyValue, List<String> fields){
		return getStuDetailList(advancedParamList,page,keyValue,fields,TABLE);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String, Object>> getStuDetailList(List<AdvancedParam> advancedParamList,Page page,
			Map<String, Object> keyValue, List<String> fields,Constant.JCZD_Table table) {
		Map<String,Object> map=getStuScholarshipDetail(advancedParamList, page, keyValue, fields,table);
		return (List<Map<String, Object>>)map.get("rows");
	}
}