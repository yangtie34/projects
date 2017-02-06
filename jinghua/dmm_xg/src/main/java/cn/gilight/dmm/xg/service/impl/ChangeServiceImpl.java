package cn.gilight.dmm.xg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.util.DateUtils;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.entity.TCode;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.AgeUtils;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.ChangeDao;
import cn.gilight.dmm.xg.service.ChangeService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.ListUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 学籍异动
 * 
 * @author xuebl
 * @date 2016年5月3日 下午5:57:12
 */
@Service("changeService")
public class ChangeServiceImpl implements ChangeService {

	@Resource
	private BusinessService businessService;
	@Resource
	private ChangeDao changeDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 学籍异动 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"change";
	/**
	 * 获取学籍异动数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	/**
	 * 获取学籍异动数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String id){
		return businessService.getDeptDataList(ShiroTag, id);
	}
	
	@Override
	public Map<String, Object> getStuChangeAbstract(List<AdvancedParam> advancedParamList){
		return getStuChangeAbstract(getDeptDataList(), StringUtils.join(Constant.Change_Bad_Code, ","), advancedParamList);
	}

	@Override
	public Map<String, Object> getStuChangeAbstract(List<String> deptList, String change_bad_codes, List<AdvancedParam> advancedParamList) {
		// 默认是所有不良异动
		change_bad_codes = change_bad_codes==null ? StringUtils.join(Constant.Change_Bad_Code, ",") : change_bad_codes;
		/**
		 * 1.总人次
		 * 2.不良异动人次
		 * 3.比例
		 * 4.不良异动链接
		 */
		deptList = deptList==null ? getDeptDataList() : deptList; // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		String start_date = null, end_date = null; // 查询时间段
		// 解析高级查询参数
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		
		// 查所有异动数据、不良异动数据
		List<Map<String, Object>> list = changeDao.queryChangeAbstract(schoolYear, deptList, stuAdvancedList, start_date, end_date, null);
		List<String> badCodeList = ListUtils.ary2List(change_bad_codes.split(","));
		String code = null;
		int count_all = 0, count_bad = 0;
		for(Map<String, Object> map : list){
			code = MapUtils.getString(map, "code");
			int count = MapUtils.getInteger(map, "value");
			if(badCodeList.contains(code)) count_bad += count;
			count_all += count;
		}
		// return
		Map<String, Object> map = new HashMap<>();
		map.put("count_all", count_all);
		map.put("count_bad", count_bad);
		map.put("scale", MathUtils.getPercentNum(count_bad, count_all));
		// 不良异动
		List<Map<String, Object>> bad_list = new ArrayList<>();
		List<String> name_bad_list = businessService.queryBzdmNameList(Constant.CODE_STU_CHANGE_CODE, change_bad_codes);
		map.put("name_bad", name_bad_list);
		map.put("bad_list", bad_list);
		return map;
	}
	
	@Override
	public Map<String, Object> getStuChange(List<AdvancedParam> advancedParamList) {
		return getStuChange(getDeptDataList(), null, advancedParamList);
	}

	@Override
	public Map<String, Object> getStuChange(List<String> deptList, String change_codes, List<AdvancedParam> advanceParamList) {
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		String start_date = null, end_date = null; // 查询时间段
		return getStuChange(deptList, change_codes, advanceParamList, schoolYear, start_date, end_date);
	}

	@Override
	public Map<String, Object> getStuChange(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList,
			int schoolYear, String start_date, String end_date) {
		/**
		 * 1.异动类别分布
		 * 2.年级分布
		 * 3.学科分布
		 * 4.性别分布
		 */
		deptList = deptList==null ? getDeptDataList() : deptList; // 权限
		// 解析高级查询参数
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		// 1 2 3 4
		List<Map<String, Object>> typeList    = changeDao.queryChangeType(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes),
								  gradeList   = changeDao.queryChangeGrade(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes),
								  subjectList = changeDao.queryChangeSubject(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes),
								  sexList     = changeDao.queryChangeSex(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes);
		Map<String, Object> map = DevUtils.MAP();
		map.put("type", typeList);
		for(Map<String, Object> m : gradeList){
			// 增加if判断防止学籍异动表中学年字段为空 出现异常
			if(MapUtils.getInteger(m, "code")!=null)m.put("name", EduUtils.getNjNameByCode(MapUtils.getInteger(m, "code")));
			if(MapUtils.getInteger(m, "code")==null)m.put("code", "null");
		}
		map.put("grade", gradeList);
		map.put("subject", subjectList);
		map.put("sex", sexList);
		return map;
	}

	@Override
	public Map<String, Object> getDeptStuChange(List<AdvancedParam> advancedParamList) { 
		return getDeptStuChange(getDeptDataList(), null, advancedParamList);
	}

	@Override
	public Map<String, Object> getDeptStuChange(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList) {
		/**
		 * 1.获取用户权限
		 * 2.查询各个节点的数据
		 * 		异动人数、总人数
		 * 3.排序返回
		 */
		deptList = deptList==null ? getDeptDataList() : deptList; // 权限
		int schoolYear = EduUtils.getSchoolYear4();
		String start_date = null, end_date = null; // 查询时间段
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		// 需要展示的组织机构列表
		//List<Map<String, Object>> deptMapList = businessService.getDeptDataListForGoingDownStu(deptList, AdvancedUtil.getPid(advancedParamList), schoolYear);
		/*for(Map<String, Object> map : deptMapList){
			String id = MapUtils.getString(map, "id");
//				   level_type = MapUtils.getString(map, "level_type");
//			deptList = PmsUtils.getDeptListByDeptAndLevelType(id, level_type);
			deptList = PmsUtils.getDeptListByDeptAndLevel(id, MapUtils.getInteger(map, "level_"));
			int stuChangeCount = changeDao.queryStuChangeCountByDeptId(schoolYear, deptList, stuAdvancedList, start_date, end_date, id, change_codes),
				stuCount = businessDao.queryStuCount(schoolYear, deptList, stuAdvancedList);
			map.put("value_change", stuChangeCount);
			map.put("value_stu", MathUtils.getPercentNum(stuChangeCount, stuCount));
			map.put("code", id);
		}*/
		String pid=AdvancedUtil.getPid(advancedParamList);
		List<Map<String, Object>> deptMapList = new ArrayList<>();
		String instructorsSql  = changeDao.queryStuChangeCountByDeptIdSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes),
					   stuSql  = businessDao.getStuSql(schoolYear, deptList, null),
					   stuSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, stuSql, true, true, false, false, schoolYear, null),
			   instructorsSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, instructorsSql, true, true, false, false, schoolYear, null);
				List<Map<String, Object>> instructors_li = baseDao.queryListInLowerKey(instructorsSql2);
				List<Map<String, Object>> stu_li = baseDao.queryListInLowerKey(stuSql2);
				for(Map<String, Object> map : instructors_li){
					Map<String,Object> map1 =new HashMap<>();
					for(Map<String, Object> stu_map : stu_li){
						if(map.get(Constant.NEXT_LEVEL_COLUMN_CODE).equals(stu_map.get(Constant.NEXT_LEVEL_COLUMN_CODE))){
						map1.put("code",map.get(Constant.NEXT_LEVEL_COLUMN_CODE));
						map1.put("name",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
						map1.put("value_change",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
						if(Integer.parseInt((String) stu_map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))==0){
							map1.put("value_stu",0);
						}else{
						map1.put("value_stu",MathUtils.getPercentNum((Integer.parseInt((String) map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))),Integer.parseInt((String) stu_map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))));
						}
						}
					}
					deptMapList.add(map1);
				}
		// 当前组织机构类型名称（院系、专业）
		String deptMc = businessService.getLevelNameById(deptMapList.isEmpty() ? null : MapUtils.getString(deptMapList.get(0), "id"));
		// sort
		compareCount(deptMapList, "value_change", false);
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", deptMapList);
		map.put("deptMc", deptMc);
		return map;
	}
	
	/**
	 * 集合排序
	 * @param list void
	 * @param compareField
	 */
	private void compareCount(List<Map<String, Object>> list, final String compareField, final boolean asc){
		Collections.sort(list, new Comparator<Map<String, Object>>(){
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				int count1 = MapUtils.getInteger(o1, compareField),
					count2 = MapUtils.getInteger(o2, compareField),
					flag = 0,
					pare = asc ? 1 : -1; // 正序为1
				if(count1 > count2)
					flag = pare;
				else if(count1 < count2)
					flag = -pare;
				return flag;
			}
		});
	}

	@Override
	public Map<String, Object> getStuChangeMonth(List<AdvancedParam> advancedParamList) { 
		return getStuChangeMonth(getDeptDataList(), null, advancedParamList);
	}

	@Override
	public Map<String, Object> getStuChangeMonth(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList) {
		deptList = deptList==null ? getDeptDataList() : deptList; // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		String start_date = null, end_date = null; // 查询时间段 TODO 月份分布应查近几年的数据
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String, Object>> list = changeDao.queryChangeMonth(schoolYear, deptList, stuAdvancedList, start_date, end_date, change_codes);
		// 月份数据处理（填空、排序）
		Map<String, Object> addObject = new HashMap<>(); addObject.put("value", 0);
		list = AgeUtils.sortMonthListFromXnFirstMonthByKey(list, "name", addObject);
		// 特殊处理  每年 9月、6月 是学籍异动高发期， TODO 其中 9月发生的 转专业 学生较多，6月 留降级 学生较多
		List<Double> countList = new ArrayList<>();
		for(Map<String, Object> map : list){
			countList.add(MapUtils.getDouble(map, "value"));
		}
		double avg = MathUtils.getAvgValueExcludeNull(countList), avg_coefficient = avg*1.2; // 高于平均数的 20%为高发月份
		List<String> highList = new ArrayList<>();
		if(avg_coefficient > 0){
			for(Map<String, Object> map : list){
				if(MapUtils.getDouble(map, "value") >= avg_coefficient){
					highList.add(MapUtils.getString(map, "name"));
				}
			}
		}
//		List<Map<String, Object>> infoList = new ArrayList<>(); //高发月份异动类型分析
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("dataSource", "全部"); //数据来源
		map.put("high", highList); //高发月份
//		map.put("infoList", infoList); //高发月份异动类型分析
		return map;
	}

	@Override
	public Map<String, Object> getStuChangeYear(List<AdvancedParam> advancedParamList) {
		return getStuChangeYear(getDeptDataList(), null, advancedParamList);
	}

	@Override
	public Map<String, Object> getStuChangeYear(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList) {
		deptList = deptList==null ? getDeptDataList() : deptList; // 权限
		int thisYear = EduUtils.getSchoolYear4();
		String start_date = null, end_date = null; // 查询时间段
		String deptId = businessService.getSchoolId(); // 默认查询全校
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		// data
		List<Map<String, Object>> reList = new ArrayList<>();
		for(int i=Constant.Year_His_Len; i>0; i--){
			int schoolYear = thisYear+1-i; //哪些学年
			String[] schoolYearQuant = EduUtils.getTimeQuantum(schoolYear);
			start_date = schoolYearQuant[0];
			end_date   = schoolYearQuant[1];
			Map<String, Object> map = new HashMap<>();
			int stuChangeCount = changeDao.queryStuChangeCountByDeptId(schoolYear, deptList, stuAdvancedList, start_date, end_date, deptId, change_codes),
				stuCount = businessDao.queryStuCount(schoolYear, deptList, stuAdvancedList);
			map.put("name", schoolYear+"-"+(schoolYear+1));
			map.put("value_change", stuChangeCount);
			map.put("value_stu", MathUtils.getPercentNum(stuChangeCount, stuCount));
			reList.add(map);
		}
		// return
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", reList);
		return map;
	}

	@Override
	public Map<String, Object> getStuChangeHistory(List<AdvancedParam> advancedParamList){
		return getStuChangeHistory(getDeptDataList(), null, advancedParamList);
	}

	@Override
	public Map<String, Object> getStuChangeDetail(List<AdvancedParam> advancedParamList,
			Page page, Map<String, Object> keyValue, List<String> fields) {
		List<String> deptList = getDeptDataList(); // 权限
		String change_codes=(String)keyValue.get("changeCode");
		String getStuChangeDetailSql=changeDao.getStuChangeDetailSql(advancedParamList, keyValue, fields,change_codes,deptList);
		Map<String,Object> map =baseDao.createPageQueryInLowerKey(getStuChangeDetailSql, page);
		return map;//下钻学生信息
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList,Page page, 
			Map<String, Object> keyValue, List<String> fields){
		Map<String, Object> map=getStuChangeDetail(advancedParamList, page, keyValue, fields);
		return (List<Map<String, Object>>) map.get("rows");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getStuChangeHistory(List<String> deptList, String change_codes, List<AdvancedParam> advancedParamList) {
		deptList = deptList==null ? getDeptDataList() : deptList; // 权限
		int year_length = 5;
		int thisSchoolYear = EduUtils.getSchoolYear4();
		String start_date = EduUtils.getTimeQuantum(thisSchoolYear+1-year_length)[0],
			   end_date   = EduUtils.getTimeQuantum(thisSchoolYear)[1];
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		/**
		 * 1.异动类别分布
		 * 2.年级分布
		 * 3.学科分布
		 * 4.性别分布
		 */
		// 整合基本数据
		List<Map<String, Object>> typeDataList    = new ArrayList<>(),
								  gradeDataList   = new ArrayList<>(),
								  subjectDataList = new ArrayList<>(),
								  sexDataList     = new ArrayList<>();
		Map<String, Object> oneYearMap = null;
		for( ; year_length>0; year_length--){
			int schoolYear = thisSchoolYear+1-year_length;
			String[] timeAry = EduUtils.getTimeQuantum(schoolYear);
			oneYearMap = getStuChange(deptList, change_codes, stuAdvancedList, schoolYear, timeAry[0], timeAry[1]);
			
			List<Map<String, Object>> typeList    = (List<Map<String, Object>>) MapUtils.getObject(oneYearMap, "type"),
									  gradeList   = (List<Map<String, Object>>) MapUtils.getObject(oneYearMap, "grade"),
									  subjectList = (List<Map<String, Object>>) MapUtils.getObject(oneYearMap, "subject"),
									  sexList     = (List<Map<String, Object>>) MapUtils.getObject(oneYearMap, "sex");
			Map<String, Object> typeYearMap    = new HashMap<>(), // {name:2011, 转专业:3, 休学:2, 复学:5}
								gradeYearMap   = new HashMap<>(), // {name:2011, 1:3, 2:3, 3:3, 4:3, 5:2} 1/2/3/4/5 年级
								subjectYearMap = new HashMap<>(), // {name:2011, '5':2, '6':3}
								sexYearMap     = new HashMap<>(); // {name:2011, '1':3, '2':5}
			String schoolYearName = schoolYear+"-"+(schoolYear+1); // 2014 -> 2014-2015
			// 异动类别 [{name=留级, value=1, code=02}]
			for(Map<String, Object> map : typeList) typeYearMap.put(MapUtils.getString(map, "code"), MapUtils.getInteger(map, "value"));
			typeYearMap.put("name", schoolYearName);
			typeDataList.add(typeYearMap);
			// 年级 [{name=二年级, value=3, code=2}]
			for(Map<String, Object> map : gradeList) gradeYearMap.put(MapUtils.getString(map, "code"), MapUtils.getInteger(map, "value"));
			gradeYearMap.put("name", schoolYearName);
			gradeDataList.add(gradeYearMap);
			// 学科 [{subject_id=5, name=文学, value=2}]
			for(Map<String, Object> map : subjectList) subjectYearMap.put(MapUtils.getString(map, "id"), MapUtils.getInteger(map, "value"));
			subjectYearMap.put("name", schoolYearName);
			subjectDataList.add(subjectYearMap);
			// 性别 [{name=男, value=3, code=1}]
			for(Map<String, Object> map : sexList) sexYearMap.put(MapUtils.getString(map, "code"), MapUtils.getInteger(map, "value"));
			sexYearMap.put("name", schoolYearName);
			sexDataList.add(sexYearMap);
		}
		/**
		 * 添加所需的数据；图例、字段key
		 * 异动类别；年级；学科；性别
		 * legend_data : ['人数', '占比'], // 图例
		 * value_ary   : ['value_change', 'value_stu'], // value所对应字段
		 */
		// 异动类别
		Map<String, Object> typeMap = new HashMap<>();
		List<String> legend_ary = new ArrayList<>(),
					 value_ary  = new ArrayList<>();
		List<Map<String, Object>> typeBzdmList = changeDao.queryChangeBzdmList(start_date, end_date, change_codes);
		for(Map<String, Object> bzdmM : typeBzdmList){
			legend_ary.add(MapUtils.getString(bzdmM, "name"));
			value_ary.add(MapUtils.getString(bzdmM, "code"));
		}
		typeMap.put("legend_ary", legend_ary);
		typeMap.put("value_ary", value_ary);
		typeMap.put("data", typeDataList);
		// 年级
		Map<String, Object> gradeMap = new HashMap<>();
		List<String> legend_ary_grade = new ArrayList<>(),
					 value_ary_grade  = new ArrayList<>();
		List<Map<String, Object>> njBzdmList = businessService.queryBzdmNj();
		for(Map<String, Object> bzdmM : njBzdmList){
			legend_ary_grade.add(MapUtils.getString(bzdmM, "mc"));
			value_ary_grade.add(MapUtils.getString(bzdmM, "id"));
		}
		gradeMap.put("legend_ary", legend_ary_grade);
		gradeMap.put("value_ary", value_ary_grade);
		gradeMap.put("data", gradeDataList);
		// 学科
		Map<String, Object> subjectMap = new HashMap<>();
		List<String> legend_ary_subject = new ArrayList<>(),
					 value_ary_subject  = new ArrayList<>();
		List<Map<String, Object>> subjectBzdmList = businessService.queryBzdmSubjectDegree();
		for(Map<String, Object> bzdmM : subjectBzdmList){
			legend_ary_subject.add(MapUtils.getString(bzdmM, "mc"));
			value_ary_subject.add(MapUtils.getString(bzdmM, "id"));
		}
		subjectMap.put("legend_ary", legend_ary_subject);
		subjectMap.put("value_ary", value_ary_subject);
		subjectMap.put("data", subjectDataList);
		// 性别
		Map<String, Object> sexMap = new HashMap<>();
		List<String> legend_ary_sex = new ArrayList<>(),
					 value_ary_sex  = new ArrayList<>();
		List<TCode> sexBzdmList = businessService.queryBzdmList(Constant.CODE_SEX_CODE);
		for(TCode t : sexBzdmList){
			legend_ary_sex.add(t.getName_());
			value_ary_sex.add(t.getCode_());
		}
		sexMap.put("legend_ary", legend_ary_sex);
		sexMap.put("value_ary", value_ary_sex);
		sexMap.put("data", sexDataList);
		
		// return
		Map<String, Object> map = new HashMap<>();
//		map.put("type", typeMap);
//		map.put("grade", gradeMap);
//		map.put("subject", subjectMap);
//		map.put("sex", sexMap);
		map.put("type", typeMap);
		map.put("grade", gradeMap);
		map.put("subject", subjectMap);
		map.put("sex", sexMap);
		return map;
	}
	@Override
	public boolean isAll(List<AdvancedParam> advancedParamList) {
		 List<String> deptList=getDeptDataList();
		 deptList = getDeptDataList(AdvancedUtil.getPid(advancedParamList));
		 boolean tag=PmsUtils.isAllPmsData(deptList);
		return tag;
	}
	@Override
	public Map<String, Object> getStuChangeByDeptOrMajor(List<AdvancedParam> advancedParamList,String tag) {
		return getStuChangeByDeptOrMajor(getDeptDataList(),  tag, advancedParamList);
	}
	
	@Override
	public Map<String, Object> getStuChangeByDeptOrMajor(List<String> deptList, String tag,
			List<AdvancedParam> advancedParamList) {
		deptList = deptList==null ? getDeptDataList() : deptList; // 权限
		int schoolYear = EduUtils.getSchoolYear4();
		String start_date = null, end_date = null; // 查询时间段
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		deptList = getDeptDataList(AdvancedUtil.getPid(advancedParamList));
		// 需要展示的组织机构列表
		List<Map<String, Object>> deptMapList=new ArrayList<Map<String, Object>>();
		if(PmsUtils.isAllPmsData(deptList)){
			 deptMapList=businessDao.queryYxList(deptList);
		}else{
			deptMapList=businessDao.queryZyListStu(deptList);
		}
		
		//List<Map<String, Object>> deptMapList = businessService.getDeptDataListForGoingDownStu(deptList, AdvancedUtil.getPid(advancedParamList), schoolYear);
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		
		if(tag.equals("major")){
			deptMapList=businessDao.queryZyListStu(deptList);
			for(Map<String, Object> map : deptMapList){
				String id=MapUtils.getString(map, "id");
				deptList=PmsUtils.getDeptListByDeptMap(map);
					String level1=tag;
					 id=MapUtils.getString(map, "id");
					//转出sql
					String outSql=changeDao.getStuChangeByDeptOrMajorSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, id, level1, "out");
					Integer outCount=baseDao.queryForCount(outSql);
					map.put("outValue", outCount);
					String inSql=changeDao.getStuChangeByDeptOrMajorSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, id, level1, "in");
					Integer inCount=baseDao.queryForCount(inSql);
					map.put("inValue", inCount);
					list.add(map);
			}
		}else{
		for(Map<String, Object> map : deptMapList){
			String id = MapUtils.getString(map, "id");
			String level=MapUtils.getString(map, "level_");
			if(level.equals("1")){
				level="dept";
			}else{
				level="major";
			}
			deptList = PmsUtils.getDeptListByDeptMap(map);
			//转出sql
			String outSql=changeDao.getStuChangeByDeptOrMajorSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, id, level, "out");
			Integer outCount=baseDao.queryForCount(outSql);
			map.put("outValue", outCount);
			String inSql=changeDao.getStuChangeByDeptOrMajorSql(schoolYear, deptList, stuAdvancedList, start_date, end_date, id, level, "in");
			Integer inCount=baseDao.queryForCount(inSql);
			map.put("inValue", inCount);
			list.add(map);
		}
		}
		// 当前组织机构类型名称（院系、专业）
		String deptMc = businessService.getLevelNameById(deptMapList.isEmpty() ? null : MapUtils.getString(deptMapList.get(0), "id"));
		// sort
		compareCount(list, "inValue", false);
		List<Map<String, Object>> newList = new ArrayList<>();
		for(Map<String, Object> map:list){
				Map<String, Object> oneMap = new HashMap<>(), inOneMap = new HashMap<>(), outOneMap = new HashMap<>();
				inOneMap.put("value", MapUtils.getInteger(map, "inValue"));inOneMap.put("code", "in");
				outOneMap.put("value", MapUtils.getInteger(map, "outValue"));outOneMap.put("code", "out");
				List<Map<String, Object>> oneList = new ArrayList<>(); oneList.add(inOneMap); oneList.add(outOneMap);
				oneMap.put("name", MapUtils.getString(map, "name")); oneMap.put("list", oneList);oneMap.put("id", MapUtils.getString(map, "id"));
				newList.add(oneMap);
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", newList);
		map.put("deptMc", deptMc);
		return map;
	}
	
}


