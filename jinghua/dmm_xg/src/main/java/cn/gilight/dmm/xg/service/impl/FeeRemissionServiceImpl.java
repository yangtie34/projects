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
import cn.gilight.dmm.xg.dao.FeeRemissionDao;
import cn.gilight.dmm.xg.service.FeeRemissionService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Service("feeRemissionService")
public class FeeRemissionServiceImpl implements FeeRemissionService {

	@Resource
	private BusinessService businessService;
	@Resource
	private FeeRemissionDao feeRemissionDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	
	/**
	 * 学费减免 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"feeRemission";
	/**
	 * 获取学费减免数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	/**
	 * 获取学费减免数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String pid){
		return businessService.getDeptDataList(ShiroTag, pid);
	}

	/**
	 * 得到学年 学生类型
	 */
	@Override
	public List<Map<String , Object>> querySchoolYear() {
		List<Map<String , Object>> list =new ArrayList<>();
		Map<String , Object> map =new HashMap<>();
		map.put("EDU_ID", businessService.queryBzdmStuEducationList(getDeptDataList()));
		map.put("xn", businessService.queryBzdmSchoolYear("T_STU_JM","SCHOOL_YEAR"));
		map.put("bzdm", Constant.JCZD_Bzdm_Group_CountMoneyScale); // 标准代码
		list.add(map);
		return list;
	}
	/**
	 * 得到总的学费减免人数以及金额
	 */
	@Override
	public List<Map<String, Object>> queryJmInfo(String schoolYear, String edu,List<AdvancedParam> advancedList) {
		return queryJmInfo(getDeptDataList(),schoolYear, edu,advancedList);
	}
	@Override
	public List<Map<String, Object>> queryJmInfo(List<String> deptList,
			String schoolYear, String edu,List<AdvancedParam> advancedList) {
//		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		return feeRemissionDao.queryJmInfo(deptList, schoolYear, edu,advancedList);
	}	
	/**
	 * 得到学费减免分布
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryJmfb(
			String schoolYear, String edu, String fb,List<AdvancedParam> advancedParamList) {
		/**
		 * 根据类型获取学费减免 人数、总金额、覆盖率
		 */
		String pid = AdvancedUtil.getPid(advancedParamList);
		List<String> deptList = getDeptDataList(pid);
		String fbchoose="COUNT(DISTINCT T.NO)";
//		schoolYear = getRealSchoolYear(schoolYear);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
//		int year = Integer.valueOf(EduUtils.getSchoolYear4());
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
//		String pid = null;
//		Map<String, Object> deptDataMap = businessService.getDeptDataForGoingDownStu(deptList, pid, year);
//		deptList = (List<String>) deptDataMap.get("deptList");
		String sql = feeRemissionDao.getXfjmStuSql(deptList, advancedParamList, year);
		if("money".equals(fb)){
			fbchoose="SUM( T.MONEY)";
			 sql = feeRemissionDao.queryJmfb(deptList, advancedParamList, year);
		}
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
//		// 组织机构
//		List<Map<String, Object>> reList = new ArrayList<>();
//		List<Map<String, Object>> nextLevelList = (List<Map<String, Object>>) deptDataMap.get("queryList");
//		List<AdvancedParam> advancedList = new ArrayList<>();
//		AdvancedParam ad_edu  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu),
//					  ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
//		advancedList.add(ad_edu); //advancedList.add(ad_dept);
//		
//		for(Map<String, Object> map : nextLevelList){
//			String id_ = MapUtils.getString(map, "id"),
//					level_type = MapUtils.getString(map, "level_type");
//			ad_dept.setValues(id_);
//			if("BJ".equals(level_type)){
//				List<Map<String, Object>> li = feeRemissionDao.queryBjjmfb(year, deptList, advancedList,fb);
//				map.put("list", li);
//			}else{
//				List<Map<String, Object>> li = feeRemissionDao.queryJmfb(year, deptList, advancedList,fb,id_);
//				map.put("list", li);
//			}
//			// 人数、总金额,覆盖率
//			reList.add(map);
//		}
		// return
		Map<String, Object>	map = DevUtils.MAP();
		List<Map<String, Object>> lx=feeRemissionDao.queryJmType();
		map.put("lx", lx);
		map.put("list", reList);
		map.put("slx", fb);
		map.put("bzdm", Constant.JCZD_Bzdm_Group_CountMoneyScale); // 标准代码
		return map;
	}
	/**
	 * 得到历年学费减免变化
	 */
	@Override
	public Map<String, Object> queryYearJmfb(String bh,List<AdvancedParam> advancedParamList, String edu) {
		return queryYearJmfb(getDeptDataList(),bh,advancedParamList,edu);
	}

	@Override
	public Map<String, Object> queryYearJmfb(List<String> deptList,
			String bh, List<AdvancedParam> advancedParamList, String edu) {
		List<Object> listyear= new ArrayList<>();//得到学年
		List<Map<String, Object>> list=new ArrayList<>();
		int schoolYear = EduUtils.getSchoolYear4();//得到当前学年
//		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		advancedParamList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		for(int year=schoolYear-5;year<=schoolYear;year++){
			Map<String, Object> map =new HashMap<>();
			listyear.add(year);
			map.put("year", year);
			List<Map<String, Object>> li=feeRemissionDao.queryYearJmfb(deptList,year,bh,advancedParamList,edu);
			map.put("list", li);
			list.add(map);
		}
		Map<String, Object>	map = DevUtils.MAP();
		map.put("year", listyear);
		map.put("slx", bh);
		map.put("lx", feeRemissionDao.queryJmType());
		map.put("list", list);
		return map;
	}
	/**
	 * 下钻详情
	 */
	@Override
	public Map<String, Object> getStuDetail(
			List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields) {
//		AdvancedParam ad_year=new AdvancedParam();
//		ad_year.setCode(AdvancedUtil.Common_SCHOOL_YEAR);
//		ad_year.setValues(String.valueOf(EduUtils.getSchoolYear4()));
//		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
//		stuAdvancedList.add(ad_year);
		String stuDetailSql = getStuDetailSql(advancedParamList, keyValue, fields);
	// 
	Map<String, Object> map = baseDao.createPageQueryInLowerKey(stuDetailSql, page);
	return map;
	}
	private String getStuDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields) {
		String year ="2016",edu="20,30",rs = null,je = null,dept = null,type = null,code = null;
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
			edu=(String) keyValue.get("edu");
		}
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		advancedParamList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		String  stuDetailSql=feeRemissionDao.getXfjmStuSql(getDeptDataList(), advancedParamList,Integer.parseInt(year));
		if("rs".equals(rs)){
			stuDetailSql="SELECT T.NO,T.NAME,T.SCHOOL_YEAR,T.SEXMC,T.DEPTMC,T.MAJORMC,T.CLASSMC FROM ("+stuDetailSql+") T  WHERE T.NO IS NOT NULL GROUP BY T.NO,T.NAME,T.SCHOOL_YEAR,T.SEXMC,T.DEPTMC,T.MAJORMC,T.CLASSMC";
		}else if("je".equals(je)){
			stuDetailSql="SELECT  * FROM ("+stuDetailSql+") T  WHERE  T.NO IS NOT NULL";
		}else if("jmfb".equals(code)){
			stuDetailSql="SELECT * FROM ("+stuDetailSql+") T WHERE  T.NO IS NOT NULL AND T.DEPTMC = '"+dept+"' AND T.TYPEMC='"+type+"'";
		}else if("lnfb".equals(code)){
			stuDetailSql=feeRemissionDao.getXfjmStuSql(getDeptDataList(), advancedParamList,(int) keyValue.get("school_year"));
			stuDetailSql="SELECT * FROM ("+stuDetailSql+") T WHERE  T.NO IS NOT NULL AND  T.TYPEMC='"+type+"'";
		}
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") ";
		}
		return stuDetailSql;
	}
	@Override
	public List<Map<String, Object>> getStuDetailAll(List<AdvancedParam> advancedParamList, Page page, Map<String, Object> keyValue, List<String> fields){
//		return baseDao.queryListInLowerKey(getStuDetailSql(advancedParamList, keyValue, fields));
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(getStuDetailSql(advancedParamList, keyValue, fields), page);
		return (List<Map<String, Object>>) map.get("rows");
	}
}
