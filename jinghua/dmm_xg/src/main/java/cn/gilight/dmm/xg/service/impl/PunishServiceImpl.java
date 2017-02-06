package cn.gilight.dmm.xg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.AgeUtils;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.PunishDao;
import cn.gilight.dmm.xg.service.PunishService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 违纪处分
 * 
 * @author xuebl
 * @date 2016年5月31日 上午10:50:29
 */
@Service("punishService")
public class PunishServiceImpl implements PunishService {

	@Resource
	private BusinessService businessService;
	@Resource
	private PunishDao punishDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
    public static final String HANZU_CODE = "01";

	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"punish";
	
	private static final String KEY_VIOLATE = "violate";
	private static final String KEY_PUNISH  = "punish";
	private static final String KEY_GRADE = "grade";
	private static final String KEY_AGE = "age";
	
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
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAbstract(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList){
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		Integer schoolYear = (Integer) paramMap.get("schoolYear");
		String start_date = (String) paramMap.get("start_date"),
			   end_date   = (String) paramMap.get("end_date");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		// query
		int violateCount = punishDao.queryViolateCount(schoolYear, deptList, advancedList, start_date, end_date);
		int punishCount  = punishDao.queryPunishCount(schoolYear, deptList, advancedList, start_date, end_date);
		Double scale = MathUtils.getPercentNum(punishCount, violateCount);
		
		Map<String, Object> map = DevUtils.MAP();
		map.put(KEY_VIOLATE, violateCount);
		map.put(KEY_PUNISH, punishCount);
		map.put("scale", scale);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDistribution(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList){
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		start_year = (String) paramMap.get("start_year");
		end_year   = (String) paramMap.get("end_year");
		Integer schoolYear = (Integer) paramMap.get("schoolYear");
		String start_date = (String) paramMap.get("start_date"),
			   end_date   = (String) paramMap.get("end_date");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		// query
		List<Map<String, Object>> volateList = punishDao.queryViolateTypeList(schoolYear, deptList, advancedList, start_date, end_date),
								  volateTypeList = new ArrayList<>(),
								  punishList  = punishDao.queryPunishTypeList(schoolYear, deptList, advancedList, start_date, end_date);
		Map<String, Map<String, Object>> volM = new HashMap<>();
		Map<String, Object> m2 = null; String pid = null;
		for(Map<String, Object> m : volateList){
			pid = MapUtils.getString(m, "pid");
			int value = MapUtils.getInteger(m, "value");
			if(volM.containsKey(pid)){
				m2 = volM.get(pid);
				m2.put("value", value+MapUtils.getIntValue(m2, "value"));
			}else{
				m2 = new HashMap<>();
				m2.put("value", value);
				m2.put("name", MapUtils.getString(m, "pname"));
				m2.put("code", pid);
				volM.put(pid, m2);
				volateTypeList.add(m2);
			}
		}
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("violateType", volateTypeList);
		map.put(KEY_VIOLATE, volateList);
		map.put(KEY_PUNISH, punishList);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDeptPersonTimeList(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList){
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		Integer schoolYear = (Integer) paramMap.get("schoolYear");
		String start_date = (String) paramMap.get("start_date"),
			   end_date   = (String) paramMap.get("end_date");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		// zzjg
		
		/*String pid = null;
		List<Map<String, Object>> deptMapList = businessService.getDeptDataListForGoingDownStu(deptList, pid, schoolYear);
		String violateBzdmSql = punishDao.getViolateBzdmSql(schoolYear, deptList, advancedList, start_date, end_date),
			   punishBzdmSql  = punishDao.getPunishBzdmSql(schoolYear, deptList, advancedList, start_date, end_date);
		for(Map<String, Object> map : deptMapList){
			deptList = PmsUtils.getDeptListByDeptMap(map);
			List<Map<String, Object>> violateList = punishDao.queryViolateDeptList(schoolYear, deptList, advancedList, start_date, end_date, violateBzdmSql),
									  punishList  = punishDao.queryPunishDeptList(schoolYear, deptList, advancedList, start_date, end_date, punishBzdmSql);
			map.put(KEY_VIOLATE, violateList);
			map.put(KEY_PUNISH, punishList);
		}
		// 当前组织机构类型名称（院系、专业）
		String deptMc = businessService.getLevelNameById(deptMapList.isEmpty() ? null : MapUtils.getString(deptMapList.get(0), "id"));
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", deptMapList);
		map.put("bzdm_type", getBzdmType());
		map.put("bzdm_target", getBzdmTarget());
		map.put("deptMc", deptMc);
		return map;*/
	//改进方法
		String	stuSql  = businessDao.getStuSql(schoolYear, deptList, null),
				stuSql2 = businessService.getNextLevelOrderSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(advancedList), stuSql, false, true, false, false,schoolYear,null);
			List<Map<String, Object>> deptMapList = baseDao.queryListInLowerKey(stuSql2);
			String violateBzdmSql = punishDao.getViolateBzdmSql(schoolYear, deptList, advancedList, start_date, end_date),
			 punishBzdmSql  = punishDao.getPunishBzdmSql(schoolYear, deptList, advancedList, start_date, end_date);
			List<Map<String, Object>> bzdmVList=baseDao.queryListInLowerKey(violateBzdmSql);
			List<Map<String, Object>> bzdmPList=baseDao.queryListInLowerKey(punishBzdmSql);
			List<Map<String, Object>> vioList = punishDao.queryViolateDeptList(schoolYear, deptList, advancedList, start_date, end_date, violateBzdmSql);//businessService.getDeptDataListForGoingDownStu(deptList, pid, schoolYear);
			List<Map<String, Object>> punList = punishDao.queryPunishDeptList(schoolYear, deptList, advancedList, start_date, end_date, punishBzdmSql);
			for(Map<String, Object> map : deptMapList){
				List<Map<String, Object>> violateList=new ArrayList<>();
				List<Map<String, Object>> finViolateList=new ArrayList<>();
				for(Map<String, Object> stu_map : vioList){
					if(stu_map.get("id").equals(map.get("id"))){
						Map<String,Object> map1 =new HashMap<>();
						map1.put("code",stu_map.get("code"));
						map1.put("name",stu_map.get("name"));
						map1.put("value",stu_map.get("value"));
						violateList.add(map1);
					}
				}
				finViolateList.addAll(violateList);
				List<String> idList=new ArrayList<>();
				for(int i=0;i<violateList.size();i++){
					String id=(String) violateList.get(i).get("code");
					idList.add(id);
				}
				for(Map<String,Object> m:bzdmVList){
					if(!idList.contains(m.get("code_"))){
						Map<String,Object> map1 =new HashMap<>(); 
						map1.put("code",m.get("code_"));
						map1.put("name",m.get("name_"));
						map1.put("value",0);
						finViolateList.add(map1);
					}
				}
				map.put("id",map.get("id"));
				map.put("name",map.get("name_"));
				map.put(KEY_VIOLATE, finViolateList);//违纪添加后继续使用此list添加处分
			}
			for(Map<String, Object> map : deptMapList){
				List<Map<String, Object>> punishList=new ArrayList<>();
				List<Map<String, Object>> finPunishList=new ArrayList<>();
				for(Map<String, Object> stu_map : punList){
					if(stu_map.get("id").equals(map.get("id"))){
						Map<String,Object> map1 =new HashMap<>();
						map1.put("code",stu_map.get("code"));
						map1.put("name",stu_map.get("name"));
						map1.put("value",stu_map.get("value"));
						punishList.add(map1);
					}
				}
				finPunishList.addAll(punishList);
				List<String> idList=new ArrayList<>();
				for(int i=0;i<punishList.size();i++){
					String id=(String) punishList.get(i).get("code");
					idList.add(id);
				}
				for(Map<String,Object> m:bzdmPList){
					if(!idList.contains(m.get("code_"))){
						Map<String,Object> map1 =new HashMap<>(); 
						map1.put("code",m.get("code_"));
						map1.put("name",m.get("name_"));
						map1.put("value",0);
						finPunishList.add(map1);
					}
				}
				map.put(KEY_PUNISH, finPunishList);//违纪添加后继续使用此list添加处分
			}
			Map<String, Object> map = DevUtils.MAP();
			String deptMc = businessService.getLevelNameById(deptMapList.isEmpty() ? null : MapUtils.getString(deptMapList.get(0), "id"));
			map.put("list", deptMapList);
			map.put("bzdm_type", getBzdmType());
			map.put("bzdm_target", getBzdmTarget());
			map.put("deptMc", deptMc);
			return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getDeptCountScaleList(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList){
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		start_year = (String) paramMap.get("start_year");
		end_year   = (String) paramMap.get("end_year");
		Integer schoolYear = (Integer) paramMap.get("schoolYear");
		String start_date = (String) paramMap.get("start_date"),
			   end_date   = (String) paramMap.get("end_date");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		// zzjg 违纪处分 人数、比例
		String pid = null, date1 = null, date2 = null;
		String[][] timeQuant = getTimeQuantum(start_year, end_year);
		String[] quant = null;
		List<Map<String, Object>> deptMapList = businessService.getDeptDataListForGoingDownStu(deptList, pid, schoolYear);
		for(Map<String, Object> map : deptMapList){
			deptList = PmsUtils.getDeptListByDeptMap(map);
			int violate_count = punishDao.queryViolateStuIdCount(schoolYear, deptList, advancedList, start_date, end_date);
			int punish_count  = punishDao.queryPunishStuIdCount(schoolYear, deptList, advancedList, start_date, end_date);
			// 算几年的平均比例
			List<Double> violateScaleList = new ArrayList<>(), punishScaleList = new ArrayList<>();
			for(int i=0,len=timeQuant.length; i<len; i++){
				quant = timeQuant[i]; date1 = quant[0]; date2 = quant[1];
				int school_year = Integer.valueOf(date1.substring(0, 4));
				int stuCount  = businessDao.queryStuCount(Integer.valueOf(date1.substring(0, 4)), deptList, advancedList);
				int violateStuCount = punishDao.queryViolateStuIdCount(school_year, deptList, advancedList, date1, date2);
				int punishStuCount  = punishDao.queryPunishStuIdCount(school_year, deptList, advancedList, date1, date2);
				violateScaleList.add(MathUtils.getPercentNum(violateStuCount, stuCount));
				punishScaleList.add(MathUtils.getPercentNum(punishStuCount, stuCount));
			}
			map.put("violate_count", violate_count);
			map.put("violate_scale", MathUtils.getAvgValue(violateScaleList));
			map.put("punish_count", punish_count);
			map.put("punish_scale", MathUtils.getAvgValue(punishScaleList));
			map.put("code", MapUtils.getString(map, "id"));
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", deptMapList);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getMonthList(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList){
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		Integer schoolYear = (Integer) paramMap.get("schoolYear");
		String start_date = (String) paramMap.get("start_date"),
			   end_date   = (String) paramMap.get("end_date");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		
		List<Map<String, Object>> violateList = punishDao.queryViolateMonthList(schoolYear, deptList, advancedList, start_date, end_date),
								  punishList  = punishDao.queryPunishMonthList(schoolYear, deptList, advancedList, start_date, end_date);
		// 根据学年开始月份重新排序
		Map<String, Object> addObject = new HashMap<>(); addObject.put("value", 0); // 需要补全的基本数据
		violateList = AgeUtils.sortMonthListFromXnFirstMonthByKey(violateList, "name", addObject);
		punishList = AgeUtils.sortMonthListFromXnFirstMonthByKey(punishList, "name", addObject);
		// 将违纪、处分数据组合起来
		Map<String, Object> map_one = null;
		for(int i=0,len=violateList.size(); i<len; i++){
			map_one = violateList.get(i);
			List<Map<String, Object>> list_one = new ArrayList<>();
			Map<String, Object> m1 = new HashMap<>(), m2 = new HashMap<>();
			m1.put("value", MapUtils.getInteger(map_one, "value"));
			m2.put("value", MapUtils.getInteger(punishList.get(i),"value"));
			list_one.add(m1); list_one.add(m2);
			map_one.remove("value");
			map_one.put("list", list_one);
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", violateList);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getGrade(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList){
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		start_year = (String) paramMap.get("start_year");
		end_year   = (String) paramMap.get("end_year");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		/**
		 * 获取每学年的数据、再处理
		 * 每学年 各年级（违纪、处分人数、学生总人数）
		 */
		List<Map<String, Object>> baseList = new ArrayList<>();
		String[][] timeQuant = getTimeQuantum(start_year, end_year);
		String quant[] = null;
		String date1 = null, date2 = null;
		for(int i=0,len=timeQuant.length; i<len; i++){
			quant = timeQuant[i]; date1 = quant[0]; date2 = quant[1];
			int school_year = Integer.valueOf(date1.substring(0, 4));
			String vioSql = punishDao.getViolateStuIdSql(school_year, deptList, advancedList, date1, date2),
				   punSql = punishDao.getPunishStuIdSql(school_year, deptList, advancedList, date1, date2),
				   stuSql = businessDao.getStuSql(school_year, deptList, advancedList);
			List<Map<String, Object>> vioList = businessDao.queryGradeGroup(vioSql, school_year),
									  punList = businessDao.queryGradeGroup(punSql, school_year),
									  stuList = businessDao.queryGradeGroup("select no_ from ("+stuSql+")", school_year);
			Map<String, Object> map = new HashMap<>();
			map.put(KEY_VIOLATE, vioList);
			map.put(KEY_PUNISH, punList);
			map.put("stu", stuList);
			map.put("year", school_year);
			baseList.add(map);
		}
		// 处理比例
		setMaxAll(baseList);
		/** 处理数据
		 [{ name : 1, vio  : [1.2, 1.3], pun  : [1.0, 0.9] },
		  { name : 2, vio  : [1.2, 1.3], pun  : [1.0, 0.9] }]
		 */
		Map<Integer, Map<String, Object>> gradeMap = new HashMap<>();
		// 每一学年
		for(Map<String, Object> oneGradeMap : baseList){
			List<Map<String, Object>> vioList = (List<Map<String, Object>>) oneGradeMap.get(KEY_VIOLATE),
									  punList = (List<Map<String, Object>>) oneGradeMap.get(KEY_PUNISH),
									  stuList = (List<Map<String, Object>>) oneGradeMap.get("stu");
			// 每个年级
			for(int i=0,len=stuList.size(); i<len; i++){
				Map<String, Object> stuMap = stuList.get(i), // {value:132, code:1}
									vioMap = vioList.get(i),
									punMap = punList.get(i);
				int grade = MapUtils.getInteger(stuMap, "code");
				int stuVal = MapUtils.getInteger(stuMap, "value");
				int vioVal = MapUtils.getInteger(vioMap, "value");
				int punVal = MapUtils.getInteger(punMap, "value");
				Double vioScale = (stuVal==0||vioVal==0) ? null : MathUtils.getPercentNum(vioVal, stuVal),
					   punScale = (stuVal==0||punVal==0) ? null : MathUtils.getPercentNum(punVal, stuVal);
				if(!gradeMap.containsKey(grade)){
					Map<String, Object> newOneGradeMap = new HashMap<>();
					List<Double> vioScaleList = new ArrayList<>(), punScaleList = new ArrayList<>(); 
					vioScaleList.add(vioScale); punScaleList.add(punScale);
					newOneGradeMap.put("name", grade);
					newOneGradeMap.put(KEY_VIOLATE, vioScaleList);
					newOneGradeMap.put(KEY_PUNISH, punScaleList);
					gradeMap.put(grade, newOneGradeMap);
				}else{
					Map<String, Object> newOneGradeMap = gradeMap.get(grade);
					((List<Double>)newOneGradeMap.get(KEY_VIOLATE)).add(vioScale);
					((List<Double>)newOneGradeMap.get(KEY_PUNISH)).add(punScale);
				}
			}
		}
		/**
		 * 计算平均比例 
		 * [{ name : 1, list : [{ value : '' },{ value : '' }] }]
		 */
		for(Map.Entry<Integer, Map<String, Object>> oneGradeEntry : gradeMap.entrySet()){
			Map<String, Object> newOneGradeMap = oneGradeEntry.getValue();
			List<Double> vioScaleList = (List<Double>) newOneGradeMap.get(KEY_VIOLATE),
						 punScaleList = (List<Double>) newOneGradeMap.get(KEY_PUNISH);
			// 获取平均比例（null不参与计算）
			Double vioScale = MathUtils.getAvgValueExcludeNull(vioScaleList),
				   punScale = MathUtils.getAvgValueExcludeNull(punScaleList);
			List<Map<String, Object>> oneList = new ArrayList<>();
			Map<String, Object> vioM = new HashMap<>(), punM = new HashMap<>();
			vioM.put("value", vioScale); punM.put("value", punScale);
			oneList.add(vioM); oneList.add(punM);
			newOneGradeMap.put("code", MapUtils.getInteger(newOneGradeMap, "name"));
			newOneGradeMap.put("name", EduUtils.getNjNameByCode(MapUtils.getInteger(newOneGradeMap, "name")));
			newOneGradeMap.put("list", oneList);
			newOneGradeMap.remove(KEY_VIOLATE);
			newOneGradeMap.remove(KEY_PUNISH);
		}
		// 组装集合
		List<Map<String, Object>> reList = new ArrayList<>();
		for(Map.Entry<Integer, Map<String, Object>> oneGradeEntry : gradeMap.entrySet()){
			reList.add(oneGradeEntry.getValue());
		}
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", reList);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getAge(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList){
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		start_year = (String) paramMap.get("start_year");
		end_year   = (String) paramMap.get("end_year");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		/**
		 * 获取每学年的数据、再处理
		 * 每学年 （违纪、处分学生出生日期）
		 */
		List<Integer> vilateAgeList = new ArrayList<>(), punishAgeList = new ArrayList<>();
		String[][] timeQuant = getTimeQuantum(start_year, end_year);
		String quant[] = null;
		String date1 = null, date2 = null;
		List<String> vioBirthdayList = new ArrayList<>(), punBirthdayList = new ArrayList<>();
		for(int i=0,len=timeQuant.length; i<len; i++){
			quant = timeQuant[i]; date1 = quant[0]; date2 = quant[1];
			int school_year = Integer.valueOf(date1.substring(0, 4));
			vioBirthdayList = punishDao.queryViolateBirthdayList(school_year, deptList, advancedList, date1, date2);
			punBirthdayList = punishDao.queryPunishBirthdayList(school_year, deptList, advancedList, date1, date2);
			vilateAgeList.addAll(AgeUtils.CalculateAge(vioBirthdayList, date1));
			punishAgeList.addAll(AgeUtils.CalculateAge(punBirthdayList, date1));
		}
		List<Map<String, Object>> violateList = AgeUtils.groupAge(vilateAgeList, Constant.PUNISH_AGE_GROUP),
								  punishList  = AgeUtils.groupAge(punishAgeList, Constant.PUNISH_AGE_GROUP);
		// 转换为 echart 数据
		List<Map<String, Object>> reList = new ArrayList<>();
		for(int i=0,len=violateList.size(); i<len; i++){
			Map<String, Object> vioMap = violateList.get(i), punMap = punishList.get(i);
			Map<String, Object> oneMap = new HashMap<>(), vioOneMap = new HashMap<>(), punOneMap = new HashMap<>();
			vioOneMap.put("value", MapUtils.getInteger(vioMap, "value"));
			punOneMap.put("value", MapUtils.getInteger(punMap, "value"));
			List<Map<String, Object>> oneList = new ArrayList<>(); oneList.add(vioOneMap); oneList.add(punOneMap);
			oneMap.put("name", MapUtils.getString(vioMap, "name")); oneMap.put("list", oneList);
			reList.add(oneMap);
		}
		// return
		Map<String, Object> reMap = DevUtils.MAP();
		reMap.put("list", reList);
		return reMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getLiForAdd(String start_year, String end_year, String edu,List<AdvancedParam> advancedParamList){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		String[] ary={"sex","grade","subject","nation"};
		for(int i=0;i<ary.length;i++){
			Map<String,Object> maps=getSexAndGradeAndOther(start_year, end_year, edu, ary[i], advancedParamList);
			Map<String,Object> mapUtil=MapUtils.getMap(maps, "tree");
			List<Map<String,Object>> vioList = (List<Map<String, Object>>) mapUtil.get(KEY_VIOLATE);
			List<Map<String,Object>> punList = (List<Map<String, Object>>) mapUtil.get(KEY_PUNISH);
			String name=getMaxName(vioList, "value");
			if(ary[i].equals("sex")){
				name=name+"更容易违纪";
			}else{
				name=name+"学生更容易违纪";
			}
			String name2=getMaxName(punList, "value");name2="违纪学生中"+name2+"处分率较高";
			name=name+","+name2;
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("id", ary[i]);map.put("mc", name);list.add(map);
		}
		Map<String,Object> map=DevUtils.MAP();
		map.put("msg",list);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSexAndGradeAndOther(String start_year, String end_year, String edu,String tag,
			List<AdvancedParam> advancedParamList) {
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		start_year=MapUtils.getString(paramMap, "start_year");end_year=MapUtils.getString(paramMap, "end_year");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<Map<String,Object>> codeList=baseDao.queryListInLowerKey("select code_ as code,name_ ||'生' as name from t_code where code_type='"+Constant.CODE_SEX_CODE+"'");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		String[][] timeQuant = getTimeQuantum(start_year, end_year);
		String quant[] = null;
		String date1 = null, date2 = null;
		List<Map<String,Object>> pieList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> finList=new ArrayList<Map<String,Object>>();
		/**获取性别比例*/
		switch (tag) {
		case "sex":
			for(Map<String,Object> sexCode:codeList){
				StringBuffer punSql = new StringBuffer();
				StringBuffer vioSql = new StringBuffer();
				vioSql.append("select * from ( ");//拼违纪所有年份sql
				punSql.append("select * from ( ");//拼处分所有年份sql
				String code=MapUtils.getString(sexCode, "code");
				List<Double> vioList=new ArrayList<Double>();
				List<Double> punList=new ArrayList<Double>();
				for(int i=0,len=timeQuant.length; i<len; i++){
				quant = timeQuant[i]; date1 = quant[0]; date2 = quant[1];
				int school_year = Integer.valueOf(date1.substring(0, 4));
					Map<String,Object> vioMap=punishDao.getViolateOrPunishBySex(school_year, deptList, advancedList, date1, date2,code,KEY_VIOLATE);
					vioList.add(MapUtils.getDouble(vioMap, "value"));
					Map<String,Object> punMap=punishDao.getViolateOrPunishBySex(school_year, deptList, advancedList, date1, date2,code,KEY_PUNISH);
					punList.add(MapUtils.getDouble(punMap, "value"));
					String vioStuSql=punishDao.getSexGradeSubjectNationSql(school_year, deptList, advancedList, date1, date2, code, KEY_VIOLATE, tag);
					String punStuSql=punishDao.getSexGradeSubjectNationSql(school_year, deptList, advancedList, date1, date2, code, KEY_PUNISH, tag);
					//拼凑sql
					if(i==len-1){
						vioSql.append("("+vioStuSql+" ) )");punSql.append("("+punStuSql+" ) )");
					}else{
						vioSql.append("("+vioStuSql+" ) union all");punSql.append("("+punStuSql+" ) union all");
					}				
				}
				Double vioScale = MathUtils.getAvgValueExcludeNull(vioList),
						   punScale = MathUtils.getAvgValueExcludeNull(punList);
				Map<String,Object> map1=new HashMap<String,Object>();Map<String,Object> map2=new HashMap<String,Object>();
				map1.put("name", MapUtils.getString(sexCode, "name"));map1.put("value", vioScale);map1.put("code", code);
				map2.put("name", MapUtils.getString(sexCode, "name"));map2.put("value", punScale);map2.put("code", code);
				map1.put("type", KEY_VIOLATE);map2.put("type", KEY_PUNISH);
				finList.add(map1);finList.add(map2);
				//计算违纪人数，处分人数
				Map<String,Object> pieMap1=new HashMap<String,Object>();Map<String,Object> pieMap2=new HashMap<String,Object>();
				Integer vioCount=baseDao.queryForCount(vioSql.toString());
				Integer punCount=baseDao.queryForCount(punSql.toString());
				pieMap1.put("name", MapUtils.getString(sexCode, "name"));pieMap1.put("value", vioCount);pieMap1.put("code", code);
				pieMap2.put("name", MapUtils.getString(sexCode, "name"));pieMap2.put("value", punCount);pieMap2.put("code", code);
				pieMap1.put("type", KEY_VIOLATE);pieMap2.put("type", KEY_PUNISH);
				pieList.add(pieMap1);pieList.add(pieMap2);
			}
			break;
		case "grade":
			Map<String,Object> gradeMap=getGrade(start_year, end_year, edu, advancedList);
			List<Map<String,Object>>listMap=(List<Map<String, Object>>) gradeMap.get("list");
			for(Map<String,Object> map:listMap){
				String code=MapUtils.getString(map, "code");
				StringBuffer punSql = new StringBuffer();
				StringBuffer vioSql = new StringBuffer();
				vioSql.append("select * from ( ");//拼违纪所有年份sql
				punSql.append("select * from ( ");//拼处分所有年份sql
				Map<String,Object> map1=new HashMap<String,Object>();
				Map<String,Object> map2=new HashMap<String,Object>();
				List<Map<String,Object>> list =(List<Map<String, Object>>) map.get("list");
				double vioValue=(double) list.get(0).get("value");
				double punValue=(double) list.get(1).get("value");
				map1.put("name", MapUtils.getObject(map, "name"));map1.put("code",MapUtils.getObject(map, "code"));map1.put("value", vioValue);
				map2.put("name", MapUtils.getObject(map, "name"));map2.put("code",MapUtils.getObject(map, "code"));map2.put("value", punValue);
				map1.put("type", KEY_VIOLATE);map2.put("type", KEY_PUNISH);
				finList.add(map1);finList.add(map2);
				for(int i=0,len=timeQuant.length; i<len; i++){
					quant = timeQuant[i]; date1 = quant[0]; date2 = quant[1];
					int school_year = Integer.valueOf(date1.substring(0, 4));
						String vioStuSql=punishDao.getSexGradeSubjectNationSql(school_year, deptList, advancedList, date1, date2, code, KEY_VIOLATE, tag);
						String punStuSql=punishDao.getSexGradeSubjectNationSql(school_year, deptList, advancedList, date1, date2, code, KEY_PUNISH, tag);
						//拼凑sql
						if(i==len-1){
							vioSql.append("("+vioStuSql+" ) )");punSql.append("("+punStuSql+" ) )");
						}else{
							vioSql.append("("+vioStuSql+" ) union all");punSql.append("("+punStuSql+" ) union all");
						}				
					}
				//计算违纪人数，处分人数
				Map<String,Object> pieMap1=new HashMap<String,Object>();Map<String,Object> pieMap2=new HashMap<String,Object>();
				Integer vioCount=baseDao.queryForCount(vioSql.toString());
				Integer punCount=baseDao.queryForCount(punSql.toString());
				pieMap1.put("name", MapUtils.getString(map, "name"));pieMap1.put("value", vioCount);pieMap1.put("code", code);
				pieMap2.put("name", MapUtils.getString(map, "name"));pieMap2.put("value", punCount);pieMap2.put("code", code);
				pieMap1.put("type", KEY_VIOLATE);pieMap2.put("type", KEY_PUNISH);
				pieList.add(pieMap1);pieList.add(pieMap2);
			}
			break;
		case "subject":
			List<Map<String,Object>> subList=businessService.queryBzdmSubjectDegree();
			for(Map<String,Object> map:subList){
				StringBuffer punSql = new StringBuffer();
				StringBuffer vioSql = new StringBuffer();
				vioSql.append("select * from ( ");//拼违纪所有年份sql
				punSql.append("select * from ( ");//拼处分所有年份sql
				String code=MapUtils.getString(map, "id");
				String name=MapUtils.getString(map, "mc");name=name.trim();
				List<Double> vioList=new ArrayList<Double>();
				List<Double> punList=new ArrayList<Double>();
				for(int i=0,len=timeQuant.length; i<len; i++){
				quant = timeQuant[i]; date1 = quant[0]; date2 = quant[1];
				int school_year = Integer.valueOf(date1.substring(0, 4));
					Map<String,Object> vioMap=punishDao.getViolateOrPunishBySubject(school_year, deptList, advancedList, date1, date2,code,KEY_VIOLATE);
					vioList.add(MapUtils.getDouble(vioMap, "value"));
					Map<String,Object> punMap=punishDao.getViolateOrPunishBySubject(school_year, deptList, advancedList, date1, date2,code,KEY_PUNISH);
					punList.add(MapUtils.getDouble(punMap, "value"));
					String vioStuSql=punishDao.getSexGradeSubjectNationSql(school_year, deptList, advancedList, date1, date2, code, KEY_VIOLATE, tag);
					String punStuSql=punishDao.getSexGradeSubjectNationSql(school_year, deptList, advancedList, date1, date2, code, KEY_PUNISH, tag);
					//拼凑sql
					if(i==len-1){
						vioSql.append("("+vioStuSql+" ) )");punSql.append("("+punStuSql+" ) )");
					}else{
						vioSql.append("("+vioStuSql+" ) union all");punSql.append("("+punStuSql+" ) union all");
					}	
				}
				Double vioScale = MathUtils.getAvgValueExcludeNull(vioList),
						   punScale = MathUtils.getAvgValueExcludeNull(punList);
				Map<String,Object> map1=new HashMap<String,Object>();
				Map<String,Object> map2=new HashMap<String,Object>();
				map1.put("name", name);map1.put("value", vioScale);map1.put("code", code);
				map2.put("name", name);map2.put("value", punScale);map2.put("code", code);
				map1.put("type", KEY_VIOLATE);map2.put("type", KEY_PUNISH);
				finList.add(map1);finList.add(map2);
				//计算违纪人数，处分人数
				Map<String,Object> pieMap1=new HashMap<String,Object>();Map<String,Object> pieMap2=new HashMap<String,Object>();
				Integer vioCount=baseDao.queryForCount(vioSql.toString());
				Integer punCount=baseDao.queryForCount(punSql.toString());
				pieMap1.put("name", name);pieMap1.put("value", vioCount);pieMap1.put("code", code);
				pieMap2.put("name", name);pieMap2.put("value", punCount);pieMap2.put("code", code);
				pieMap1.put("type", KEY_VIOLATE);pieMap2.put("type", KEY_PUNISH);
				pieList.add(pieMap1);pieList.add(pieMap2);
			}
			break;
		case "nation":
				Map<String,Object> m1=new HashMap<String,Object>();Map<String,Object> m2=new HashMap<String,Object>();
				m1.put("code",HANZU_CODE);m1.put("name", "汉族");m2.put("code", "not");m2.put("name", "少数民族");
				List<Map<String,Object>> nationList=new ArrayList<Map<String,Object>>();nationList.add(m1);nationList.add(m2);
				for(Map<String,Object> nationMap:nationList){
					List<Double> vioList=new ArrayList<Double>();
					List<Double> punList=new ArrayList<Double>();
					StringBuffer punSql = new StringBuffer();
					StringBuffer vioSql = new StringBuffer();
					vioSql.append("select * from ( ");//拼违纪所有年份sql
					punSql.append("select * from ( ");//拼处分所有年份sql
					String code=MapUtils.getString(nationMap, "code");
					String name=MapUtils.getString(nationMap, "name");
					for(int i=0,len=timeQuant.length; i<len; i++){
						quant = timeQuant[i]; date1 = quant[0]; date2 = quant[1];
						int school_year = Integer.valueOf(date1.substring(0, 4));
							Map<String,Object> vioMap=punishDao.getViolateOrPunishByNation(school_year, deptList, advancedList, date1, date2,code,KEY_VIOLATE);
							vioList.add(MapUtils.getDouble(vioMap, "value"));
							Map<String,Object> punMap=punishDao.getViolateOrPunishByNation(school_year, deptList, advancedList, date1, date2,code,KEY_PUNISH);
							punList.add(MapUtils.getDouble(punMap, "value"));
							String vioStuSql=punishDao.getSexGradeSubjectNationSql(school_year, deptList, advancedList, date1, date2, code, KEY_VIOLATE, tag);
							String punStuSql=punishDao.getSexGradeSubjectNationSql(school_year, deptList, advancedList, date1, date2, code, KEY_PUNISH, tag);
							//拼凑sql
							if(i==len-1){
								vioSql.append("("+vioStuSql+" ) )");punSql.append("("+punStuSql+" ) )");
							}else{
								vioSql.append("("+vioStuSql+" ) union all");punSql.append("("+punStuSql+" ) union all");
							}	
						}
					Double vioScale = MathUtils.getAvgValueExcludeNull(vioList),
							   punScale = MathUtils.getAvgValueExcludeNull(punList);
					Map<String,Object> map1=new HashMap<String,Object>();
					Map<String,Object> map2=new HashMap<String,Object>();
					map1.put("name",name );map1.put("value", vioScale);map1.put("code", code);
					map2.put("name", name);map2.put("value", punScale);map2.put("code", code);
					map1.put("type", KEY_VIOLATE);map2.put("type", KEY_PUNISH);
					finList.add(map1);finList.add(map2);
					Map<String,Object> pieMap1=new HashMap<String,Object>();Map<String,Object> pieMap2=new HashMap<String,Object>();
					Integer vioCount=baseDao.queryForCount(vioSql.toString());
					Integer punCount=baseDao.queryForCount(punSql.toString());
					pieMap1.put("name", name);pieMap1.put("value", vioCount);pieMap1.put("code", code);
					pieMap2.put("name", name);pieMap2.put("value", punCount);pieMap2.put("code", code);
					pieMap1.put("type", KEY_VIOLATE);pieMap2.put("type", KEY_PUNISH);
					pieList.add(pieMap1);pieList.add(pieMap2);
				}
			break;
		}
		Map<String,Object> map=	new HashMap<String,Object>() ;
		List<Map<String,Object>> vioList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> punList=new ArrayList<Map<String,Object>>();
		Map<String,Object> map2=new HashMap<String,Object>() ;
		List<Map<String,Object>> vioList2=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> punList2=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> m:pieList){
			if(m.containsValue(KEY_PUNISH)){
				punList2.add(m);
			}else{
				vioList2.add(m);
			}
		}
		for(Map<String,Object> m:finList){
			if(m.containsValue(KEY_PUNISH)){
				punList.add(m);
			}else{
				vioList.add(m);
			}
		}
		map.put("punish", punList);
		map.put("violate", vioList);
		map2.put("punish", punList2);
		map2.put("violate", vioList2);
		Map<String,Object> finMap=DevUtils.MAP();
		finMap.put("tree", map);
		finMap.put("pie", map2);
		return finMap;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getStuDetail(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields){
		Map<String,Object> map=new HashMap<String,Object>();
		String start_year=MapUtils.getString(keyValue, "start_year"),end_year=MapUtils.getString(keyValue, "end_year"),
				edu=MapUtils.getString(keyValue, "edu");
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		Integer schoolYear = (Integer) paramMap.get("schoolYear");
		String start_date = (String) paramMap.get("start_date"),
			   end_date   = (String) paramMap.get("end_date");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		String getStuDetailSql="";
		if(keyValue.containsKey(KEY_GRADE) || keyValue.containsKey(KEY_AGE)){
			 getStuDetailSql=getGradeOrAgeStuDetail(advancedList, keyValue, fields);
		}else{
			 getStuDetailSql=punishDao.getStuDetailSql(schoolYear, deptList, advancedList, start_date, end_date, keyValue, fields);
		}
		map =baseDao.createPageQueryInLowerKey(getStuDetailSql, page);
		return map;//下钻学生信息
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields){
		Map<String, Object> map =getStuDetail(advancedParamList, page, keyValue, fields);
		return (List<Map<String, Object>>) map.get("rows");
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getPunishAgain(String edu, List<AdvancedParam> advancedParamList){
		Map<String, Object> paramMap = getParamsMapByParams(null, null, edu, advancedParamList);
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		/**
		 * 近8年 再次处分比例
		 * 2015学年再次处分比例：2015学年处分学生中之间有过处分的/2015学年处分学生
		 */
		List<Map<String, Object>> list = new ArrayList<>();
		int schoolYear = EduUtils.getSchoolYear4(), year_len = Constant.Year_His_Len;
		for( ; year_len>0; year_len--){
			int year = schoolYear+1-year_len;
			String[] times = EduUtils.getTimeQuantum(year);
			String punishStuIdSql = punishDao.getPunishStuIdSql(year, deptList, advancedList, times[0], times[1]);
			int count_again = punishDao.queryPunishAgainCount(punishStuIdSql, times[1]);
			int count = baseDao.queryForCount(punishStuIdSql);
			Map<String, Object> map = new HashMap<>();
			map.put("name", year+"-"+(year+1));
			map.put("value", MathUtils.getPercentNum(count_again, count));
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 根据数据的最大编码补全数据
	 * @param list void
	 */
	@SuppressWarnings("unchecked")
	private void setMaxAll(List<Map<String, Object>> list){
		// 将数据组合一起获取最大编码
		List<Map<String, Object>> maxList = new ArrayList<>();
		for(Map<String, Object> m : list){
			maxList.addAll((List<Map<String, Object>>) m.get(KEY_VIOLATE));
			maxList.addAll((List<Map<String, Object>>) m.get(KEY_PUNISH));
			maxList.addAll((List<Map<String, Object>>) m.get("stu"));
		}
		String key_code = "code";
		int max = getMax(maxList, key_code);
		// 根据最大编码补全数据
		for(Map<String, Object> m : list){
			setAll((List<Map<String, Object>>) m.get(KEY_VIOLATE), key_code, max);
			setAll((List<Map<String, Object>>) m.get(KEY_PUNISH), key_code, max);
			setAll((List<Map<String, Object>>) m.get("stu"), key_code, max);
		}
	}
	/**
	 * 获取最大编码
	 * @param list 数据列表
	 * @param key_code 编码对应的key
	 * @return int
	 */
	private int getMax(List<Map<String, Object>> list, String key_code){
		int max = 0;
		for(Map<String, Object> m : list){
			int val = MapUtils.getInteger(m, key_code);
			max = val>max ? val : max;
		}
		return max;
	}
	/**
	 * 根据最大编码补全数据
	 * @param list1
	 * @param max void
	 */
	private void setAll(List<Map<String, Object>> list1, String key_code, int max){
		for(int i=1; i<=max; i++){
			boolean flag = false;
			for(Map<String, Object> m : list1){
				if(i == MapUtils.getInteger(m, key_code)){
					flag = true; break;
				}
			}
			if(!flag){
				Map<String, Object> m2 = new HashMap<>();
				m2.put("value", 0);
				m2.put(key_code, i);
				list1.add(m2);
			}
		}
	}
	
	/**
	 * 违纪处分 选项编码
	 */
	private List<Map<String, Object>> getBzdmType(){
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>(), map2 = new HashMap<>();
		map1.put("id", KEY_VIOLATE);	map1.put("mc", "违纪");
		map2.put("id", KEY_PUNISH);	map2.put("mc", "处分");
		list.add(map1); list.add(map2);
		return list;
	}
	/**
	 * 人次、人数/比例 编码
	 */
	private List<Map<String, Object>> getBzdmTarget(){
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map1 = new HashMap<>(), map2 = new HashMap<>();
		map1.put("id", "1"); map1.put("mc", "人次");
		map2.put("id", "2"); map2.put("mc", "人数/比例");
		list.add(map1); list.add(map2);
		return list;
	}
	
	/**
	 * 根据参数优化参数
	 * @param start_year 开始年
	 * @param end_year 结束年
	 * @param edu 学历
	 * @return Map<String,Object>
	 */
	private Map<String, Object> getParamsMapByParams(String start_year, String end_year, String edu, List<AdvancedParam> advancedParamList){
		start_year = EduUtils.getSchoolYear9(start_year);
		end_year   = EduUtils.getSchoolYear9(end_year);
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		Integer schoolYear = null;
		if(start_year.equals(end_year)) schoolYear = Integer.valueOf(start_year.substring(0, 4));
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		advancedParamList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		String start_date = EduUtils.getTimeQuantum(start_year)[0],
			   end_date   = EduUtils.getTimeQuantum(end_year)[1];
		List<String> deptList = getDeptDataList();
		// return
		Map<String, Object> map = new HashMap<>();
		map.put("start_year", start_year);
		map.put("end_year", end_year);
		map.put("schoolYear", schoolYear);
		map.put("edu", edu);
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		map.put("advancedList", advancedParamList);
		map.put("deptList", deptList);
		return map;
	}
	/**
	 * 获取学年段之间的 日期段
	 * @param start_year 开始年
	 * @param end_year 结束年
	 * @return String[][]
	 * <br> [ [2014-08-01,2015-07-31],[2015-08-01,2016-07-31] ]
	 */
	private String[][] getTimeQuantum(String start_year, String end_year){
		int year1 = Integer.valueOf(start_year.substring(0,4)),
			year2 = Integer.valueOf(end_year.substring(0,4)),
			len   = year2-year1+1;
		String[][] ary = new String[len][2];
		for(int i=0; i<len; i++){
			ary[i] = EduUtils.getTimeQuantum(year1+i);
		}
		return ary;
	}
	//获取违纪处分年级和年龄学生信息
	@SuppressWarnings("unchecked")
	private String getGradeOrAgeStuDetail(List<AdvancedParam> advancedParamList,Map<String, Object> keyValue, List<String> fields){
		String start_year=MapUtils.getString(keyValue, "start_year"),end_year=MapUtils.getString(keyValue, "end_year"),edu=MapUtils.getString(keyValue, "edu");
		Map<String, Object> paramMap = getParamsMapByParams(start_year, end_year, edu, advancedParamList);
		start_year = (String) paramMap.get("start_year");
		end_year   = (String) paramMap.get("end_year");
		List<AdvancedParam> advancedList = (List<AdvancedParam>) paramMap.get("advancedList");
		List<String> deptList = (List<String>) paramMap.get("deptList");
		String[][] timeQuant = getTimeQuantum(start_year, end_year);
		String quant[] = null;
		String start = null, end = null,type=null;
		StringBuffer sql = new StringBuffer();
		if(keyValue.containsKey(KEY_GRADE)){
			Map<String,String> map=(Map<String, String>)MapUtils.getMap(keyValue, KEY_GRADE);
			String num=MapUtils.getString(map, KEY_GRADE),level=MapUtils.getString(map, "level");
			type=KEY_GRADE;
			sql.append("select * from ( ");
			for(int i=0,len=timeQuant.length; i<len; i++){
				quant = timeQuant[i]; start = quant[0]; end = quant[1];
				int school_year = Integer.valueOf(start.substring(0, 4));
				String stuDetailSql=punishDao.getGradeOrAgeDetailSql(school_year, deptList, advancedList, start, end, num, level,type);
				stuDetailSql =  "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") ";
				if(i==len-1){
					sql.append("("+stuDetailSql+" ) )");
				}else{
					sql.append("("+stuDetailSql+" ) union all");
				}
		}
	}else{
		Map<String,String> map=(Map<String, String>)MapUtils.getMap(keyValue, KEY_AGE);
		String num=MapUtils.getString(map, KEY_AGE),level=MapUtils.getString(map, "level");//num指年级 年龄
		num=num.replaceAll("[^(0-9)]", "");
		type=KEY_AGE;
		sql.append("select * from ( ");
		for(int i=0,len=timeQuant.length; i<len; i++){
			quant = timeQuant[i]; start = quant[0]; end = quant[1];
			int school_year = Integer.valueOf(start.substring(0, 4));
			String stuDetailSql=punishDao.getGradeOrAgeDetailSql(school_year, deptList, advancedList, start, end, num, level,type);
			stuDetailSql =  "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") ";
			if(i==len-1){
				sql.append("("+stuDetailSql+" ) )");
			}else{
				sql.append("("+stuDetailSql+" ) union all");
			}
	}
	}
		String sql1=sql.toString();
		return sql1;
	}
	
	//获取最大值的name
	private String getMaxName(List<Map<String, Object>> list, String key_code){
		String name=new String();
		double max=0;
		for(Map<String, Object> m : list){
			double val = MapUtils.getDoubleValue(m, key_code);
			max = val>max ? val : max;
		}
		for(Map<String, Object> m : list){
			if(m.containsValue(max)){
				name=MapUtils.getString(m, "name");
			};
		}
		return name;
	}
}
