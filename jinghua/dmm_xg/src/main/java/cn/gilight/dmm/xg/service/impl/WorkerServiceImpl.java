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
import cn.gilight.dmm.business.util.AgeUtils;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.dao.WorkerDao;
import cn.gilight.dmm.xg.service.WorkerService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("workerService")
public class WorkerServiceImpl implements WorkerService {
	
	@Resource
	private WorkerDao workerDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao baseDao;
	
	/**
	 * 学生工作者页面 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"worker";
	private static final String Type_Worker = "worker";// 工作者
	/** 教职工类型 */
	private static final String Type_AUTHORIZED_STRENGTH = "AUTHORIZED_STRENGTH_ID";
	/** 职称等级 */
	private static final String Type_technical = "technical";
	/** 学位 */
	private static final String Type_degree = "degree";
	/** 年龄 */
	private static final String Type_age = "age";
	/**
	 * 获取学生工作者数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String id){
		return businessService.getDeptDataList(ShiroTag, id);
	}
	
	@Override
	public Map<String, Object> getWorker(List<AdvancedParam> advancedParamList) {
		Map<String, Object> map = DevUtils.MAP();
		/**
		 * 1.获取用户教学权限
		 * 2.获取学生工作者数据
		 * 		学生工作者、学生工作领导、专职管理员、兼职管理员（数量）
		 * 3.专职辅导员与学生比
		 */
		// 1 
		List<String> deptList = getDeptDataList(AdvancedUtil.getPid(advancedParamList));
		// 2 
		List<Map<String, Object>> list = getWorkerCountListThisYear(deptList, AdvancedUtil.getAdvancedParamTea(advancedParamList));
		int showCount = 4; // 显示的个数
		int count = list.size();
		map.put("worker", list.subList(0, count>=showCount+1 ? showCount : count));
		// 3 
		map.put("scale", getInstructorsStuRatio(list, deptList, advancedParamList));
		return map;
	}
	
//	@Override
	public int[] getInstructorsStuRatio(List<Map<String, Object>> list, List<String> deptList, List<AdvancedParam> advancedParamList) {
		/**
		 * 1.获取用户教学权限
		 * 2.专职辅导员与学生比
		 */
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList), // 高级查询-学生参数
							teaAdvancedList = AdvancedUtil.getAdvancedParamTea(advancedParamList); // 高级查询-教职工参数
		// 1 
		list = list==null ? getWorkerCountListThisYear(deptList, teaAdvancedList) : list;
		// 2 专职辅导员人数 和 学生人数
		int instructors_count = 0;
		for(Map<String, Object> map : list){
			String code = MapUtils.getString(map, "code");
			if(code != null && Constant.CODE_STU_WORKER_CODE_2.equals(code)){
				instructors_count = MapUtils.getIntValue(map, "value");
				break;
			}
		}
		// 学生人数
		int stu_count = businessDao.queryStuCount(EduUtils.getSchoolYear4(), deptList, stuAdvancedList);
		// 国家标准比例、学校比例
		int[] x = {Constant.Worker_Instructors_Stu_Ratio, MathUtils.getDivisionResult(stu_count, instructors_count, 0).intValue()};
		
		return x;
	}

	/**
	 * 获取今年学生工作者数量 List
	 * @return List<Map<String,Object>>
	 */
	private List<Map<String, Object>> getWorkerCountListThisYear(List<String> deptList, List<AdvancedParam> teaAdvancedList) {
		String[] this_Xn_Ary = EduUtils.getTimeQuantumXn();
		String start_date = this_Xn_Ary[0],
			   end_date   = this_Xn_Ary[1];
		return workerDao.queryWorkerCountList(start_date, end_date, deptList, teaAdvancedList);
	}
	
	/**
	 * 获取时间段内任职的专职辅导员数量
	 * @param start_date
	 * @param end_date
	 * @param deptList
	 * @return int
	 */
	private int getInstructorsCount(String start_date, String end_date, List<String> deptList){
//		List<List<String>> xnxqList = EduUtils.getXnXqByDate(start_date, end_date);
//		List<String> list = workerDao.queryInstructorsList(deptList, xnxqList, true);
		List<String> list = workerDao.queryInstructorsList(deptList, start_date, end_date, true);
		return list.size();
	}
	/**
	 * 获取时间段内任职的 某一个类型的 学生工作者数量
	 * @param start_date
	 * @param end_date
	 * @param deptList
	 * @param stu_worker_code 学生工作者 标准代码
	 * @return int
	 */
	@SuppressWarnings("unused")
	private int queryWorkerCount(String start_date, String end_date, List<String> deptList, String stu_worker_code){
		List<Map<String, Object>> list = workerDao.queryWorkerCountList(start_date, end_date, deptList, null);
		int count = 0;
		String column_code  = "code",
			   column_value = "value";
		for(Map<String, Object> m : list){
			if(MapUtils.getString(m, column_code).equals(stu_worker_code)){
				count = MapUtils.getIntValue(m, column_value);
			}
		}
		// 没有code取第一个
		if(stu_worker_code == null) count = MapUtils.getIntValue(list.get(0), column_value);
		return count;
	};
	
	@Override
	public Map<String, Object> getWorkerDistribute(List<AdvancedParam> advancedParamList){
		Map<String, Object> map = DevUtils.MAP();
		/**
		 * 1.获取用户教学权限
		 * 		所管理的哪些院系，及对应的人员
		 * 2.学生工作者人员分布
		 * 		职称分布
		 * 		学位分布
		 * 		年龄分布
		 * 		性别分布
		 */
		String pid = AdvancedUtil.getPid(advancedParamList);
		String[] this_Xn_Ary = EduUtils.getTimeQuantumXn();
		String this_year = DateUtils.getNowYear(),
			   start_date= this_Xn_Ary[0], 
			   end_date  = this_Xn_Ary[1], 
			   stu_worker_code = null;
		List<String> deptList = getDeptDataList(pid);
		// 职称
		List<Map<String, Object>> zcList = workerDao.queryWorkerZcCountList(start_date, end_date, deptList, stu_worker_code);
		map.put("zc", zcList);
		// 学位
		List<Map<String, Object>> degreeList = workerDao.queryWorkerDegreeCountList(start_date, end_date, deptList, stu_worker_code);
		map.put("degree", degreeList);
		// 年龄   日期转年龄、分组
		List<String> birthdayList = workerDao.queryWorkerBirthdayList(start_date, end_date, deptList, stu_worker_code);
		List<Integer> ageList = AgeUtils.CalculateAge(birthdayList, this_year);
		map.put("age", AgeUtils.groupAge(ageList, Constant.Worker_Age_Group));
		// 性别
		map.put("sex", workerDao.queryWorkerSexCountList(start_date, end_date, deptList, stu_worker_code));
		return map;
	}
	
	@Override
	public Map<String, Object> getInstructorsDistribute(List<AdvancedParam> advancedParamList){
		Map<String, Object> map = DevUtils.MAP();
		/**
		 * 1.获取用户教学权限
		 * 		所管理的哪些院系，及对应的专职辅导员
		 * 2.专职辅导员人员分布
		 * 		职称分布
		 * 		学位分布
		 * 		年龄分布
		 * 		性别分布
		 */
		String pid = AdvancedUtil.getPid(advancedParamList);
		String[] this_Xn_Ary = EduUtils.getTimeQuantumXn();
		String this_year = DateUtils.getNowYear(),
			   start_date= this_Xn_Ary[0], 
			   end_date  = this_Xn_Ary[1], 
			   stu_worker_code = Constant.CODE_STU_WORKER_CODE_2;
		List<String> deptList = getDeptDataList(pid);
		// 职称
		List<Map<String, Object>> zcList = workerDao.queryWorkerZcCountList(start_date, end_date, deptList, stu_worker_code);
		map.put("zc", zcList);
		// 学位
		List<Map<String, Object>> degreeList = workerDao.queryWorkerDegreeCountList(start_date, end_date, deptList, stu_worker_code);
		map.put("degree", degreeList);
		// 年龄   日期转年龄、分组
		List<String> birthdayList = workerDao.queryWorkerBirthdayList(start_date, end_date, deptList, stu_worker_code);
		List<Integer> ageList = AgeUtils.CalculateAge(birthdayList, this_year);
		map.put("age", AgeUtils.groupAge(ageList, Constant.Worker_Age_Group));
		// 性别
		map.put("sex", workerDao.queryWorkerSexCountList(start_date, end_date, deptList, stu_worker_code));
		map.put("code",stu_worker_code);
		return map;
	}
	
	@Override
	public List<Map<String ,Object>> getOrganizationInstructorsStuRatio(List<AdvancedParam> advancedParamList, Integer schoolYear) {
		/**
		 * 1.获取用户教学权限
		 * 2.获取下级节点
		 * 3.专职辅导员与学生比
		 */
		String pid = AdvancedUtil.getPid(advancedParamList);
		schoolYear = schoolYear==null ? EduUtils.getSchoolYear4() : schoolYear;
		List<String> deptList = getDeptDataList(pid); // 权限集合
		String[] dateAry  = EduUtils.getTimeQuantum(schoolYear.intValue());
		String start_date = dateAry[0], 
			   end_date   = dateAry[1];
		
		// 上级机构或权限内的 专职辅导员数量、学生数量
		int instructors_count_all = getInstructorsCount(start_date, end_date, deptList),
			stu_count_all         = businessDao.queryStuCount(schoolYear, deptList, null);
		/*
		 * 重构 20161230
		 * 现在是先查出数据集，然后再分组
		 */
		List<Map<String, Object>> scaleList = new ArrayList<>();
		String instructorsSql  = workerDao.getInstructorsSql(deptList, start_date, end_date, true),
			   instructorsSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, instructorsSql, true, true, true, false, schoolYear, null),
			   stuSql  = businessDao.getStuSql(schoolYear, deptList, null),
			   stuSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, stuSql, true, true, false, false, schoolYear, null);
		List<Map<String, Object>> instructors_li = baseDao.queryListInLowerKey(instructorsSql2);
		List<Map<String, Object>> stu_li = baseDao.queryListInLowerKey(stuSql2);
		// 将学生与辅导员数量组合显示
		String code = null, code2 = null, code_column = Constant.NEXT_LEVEL_COLUMN_CODE, name_column = Constant.NEXT_LEVEL_COLUMN_NAME, count_column = Constant.NEXT_LEVEL_COLUMN_COUNT;
		for(Map<String, Object> ins_map : instructors_li){
			code = MapUtils.getString(ins_map, code_column);
			for(Map<String, Object> stu_map : stu_li){
				code2 = MapUtils.getString(stu_map, code_column);
				if(code.equals(code2)){
					// 每个机构对象
					Map<String, Object> scaleMap = new HashMap<>();
					scaleMap.put("name", MapUtils.getString(ins_map, name_column));
					scaleMap.put("value", MathUtils.getDivisionResult(MapUtils.getInteger(stu_map,count_column), MapUtils.getInteger(ins_map,count_column), 0).intValue());
					scaleMap.put("id", code);
					scaleList.add(scaleMap);
					break;
				}
			}
		}
		
		// 当前组织机构类型名称（院系、专业）
		String deptMc = businessService.getLevelNameById(scaleList.isEmpty() ? null : code);
		// 国家要求数据；当前平均数据；当前父节点数据
		Map<String, Object> instructors_Stu_Ratio_Map = new HashMap<>(),
							instructors_Stu_Pid_Map = new HashMap<>(),
							deptMc_Map = new HashMap<>();
		instructors_Stu_Ratio_Map.put("value", Constant.Worker_Instructors_Stu_Ratio);
		instructors_Stu_Pid_Map.put("value", MathUtils.getDivisionResult(stu_count_all, instructors_count_all, 0).intValue());
		deptMc_Map.put("value", deptMc);
		scaleList.add(0, instructors_Stu_Ratio_Map);
		scaleList.add(1, instructors_Stu_Pid_Map);
		scaleList.add(2, deptMc_Map);
		return scaleList;
	}
	@Override
	public Map<String,Object> getTeaDetail(List<AdvancedParam> advancedParamList, Page page, 
			Map<String, Object> keyValue, List<String> fields){
		String sql = getTeaDetailSql(advancedParamList, keyValue, fields);
		Map<String,Object> map = baseDao.createPageQueryInLowerKey(sql, page);
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Map<String,Object>> getTeaDetailList(List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields){
		Map<String, Object> map = getTeaDetail(advancedParamList, page, keyValue, fields);
		return (List<Map<String, Object>>) map.get("rows");
	}
	private String getTeaDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue,List<String> fields){
		List<AdvancedParam> teaAdvancedList = AdvancedUtil.getAdvancedParamTea(advancedParamList); // 高级查询-教职工参数
		List<String> deptList = getDeptDataList(AdvancedUtil.getPid(advancedParamList));
		String[] this_Xn_Ary = EduUtils.getTimeQuantumXn();
		String start_date = this_Xn_Ary[0],end_date   = this_Xn_Ary[1];
		String workerSql = "";
		if(keyValue != null){
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				AdvancedUtil.add(teaAdvancedList, getDetailAdp(entry.getKey(), String.valueOf(entry.getValue())));
				if(Type_Worker.equals(entry.getKey())){
					String x = (String) entry.getValue();
					if(Constant.CODE_All.equals(x)){
						x = null;
					}
					workerSql = workerDao.getWorkerMatchingTeacherIdSql(start_date, end_date, deptList, x);
				}
			}
		}
		String teaSql = businessDao.getTeaSql(deptList, teaAdvancedList);
		if(!workerSql.equals("")){
			teaSql = "select t.* from ("+teaSql+") t inner join ("+workerSql+") c on t.tea_no = c.tea_id";
		}
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				// 年龄  特殊处理
				if(Type_age.equals(type)){
					String[] ageAry = code.split(",");
					if(ageAry.length == 2){
						teaSql = "select * from ("+teaSql+") ";
						String startAge = ageAry[0], endAge = ageAry[1], date = DateUtils.getNowDate(), field = "birthday", ageSql = "";
						if("null".equals(startAge) && "null".equals(endAge)){
							ageSql = "where "+field+" is null";
						}else if("null".equals(startAge) && !"null".equals(endAge)){
							String[] birAry = AgeUtils.CalculateBirthday(0, Integer.valueOf(endAge), date);
							ageSql = "where "+field+" >= '"+birAry[0]+"'";
						}else if(!"null".equals(startAge) && "null".equals(endAge)){
							String[] birAry = AgeUtils.CalculateBirthday(Integer.valueOf(startAge), Integer.valueOf(startAge)+1, date);
							ageSql = "where "+field+" <= '"+birAry[1]+"'";
						}
						if(!"null".equals(startAge) && !"null".equals(endAge)){
							String[] birAry = AgeUtils.CalculateBirthday(Integer.valueOf(startAge), Integer.valueOf(endAge), date);
							ageSql = "where "+field+" >= '"+birAry[0]+"' and "+field+" <= '"+birAry[1]+"'";
						}
						teaSql += ageSql;
					}
				}
			}
		// 教职工
		String teaDetailSql = businessDao.getTeaDetailSql(teaSql);
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			teaDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+teaDetailSql+") ";
		}
		return teaDetailSql;
	}
	/**
	 * 获取高级参数
	 * @param type
	 * @param value
	 * @return AdvancedParam
	 */
	private AdvancedParam getDetailAdp(String type, String value){
		AdvancedParam adp = null;
		if(type != null){
			String group = AdvancedUtil.Group_Tea, code = null;
			switch (type) {
			case Type_technical: // 职称等级
				code = AdvancedUtil.Tea_ZYJSZW_JB_CODE;
				break;
			case Type_degree: // 学位
				code = AdvancedUtil.Tea_DEGREE_ID;
				break;
			case AdvancedUtil.Common_SEX_CODE:
				code = AdvancedUtil.Common_SEX_CODE;
				group = AdvancedUtil.Group_Common;
				if(value.equals("0")){
					value = "null";
				}
				break;
			case AdvancedUtil.Common_DEPT_ID:
			case AdvancedUtil.Common_DEPT_TEACH_ID:
			case AdvancedUtil.Common_DEPT_TEACH_TEACH_ID:
				group = AdvancedUtil.Group_Common;
				code  = type;
				break;
			default:
				break;
			}
			adp = new AdvancedParam(group, code, value);
		}
		return adp;
	}
	
}
