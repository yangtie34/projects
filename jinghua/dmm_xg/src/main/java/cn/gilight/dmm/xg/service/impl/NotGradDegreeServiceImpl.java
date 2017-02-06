package cn.gilight.dmm.xg.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.xg.dao.NotGradDegreeDao;
import cn.gilight.dmm.xg.service.NotGradDegreeService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Repository("notGradDegreeService")
public class NotGradDegreeServiceImpl implements NotGradDegreeService {
	
	@Resource
	private NotGradDegreeDao  notGradDegreeDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;

	/**
	 * 在籍学生 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"notGradDegree";
	/**
	 * 获取在籍学生数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	/**
	 * 获取在籍学生数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String id){
		return businessService.getDeptDataList(ShiroTag, id);
	}
	/**
	 * 得到无法毕业或者无学位证类型
	 */
	@Override
	public List<Map<String, Object>> getNoDegreeType() {
		List<Map<String , Object>> list =new ArrayList<>();
		Map<String , Object> map =new HashMap<>();
		map.put("xw", queryNoDegreeList());
		map.put("nedegreeCode", Constant.NODEGREE_CODE);
		list.add(map);
		return list;
	}

	/**
	 * 得到无法毕业和无学位学生人数
	 */
	@Override
	public Map<String, Object> queryXwInfo(List<AdvancedParam> advancedList) {
		return queryXwInfo(getDeptDataList(),advancedList);
	}
	@Override
	public Map<String, Object> queryXwInfo(List<String> deptList,List<AdvancedParam> advancedList) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return notGradDegreeDao.queryXwInfo(deptList,stuAdvancedList);
	}
	/**
	 * 得到无学位学生分布和比例
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryXwfbAndRatio(String fb,List<AdvancedParam> advancedParamList) {
		/**
		 * 根据类型获取无法毕业和无学位学生的分布和比例
		 */
		String pid = AdvancedUtil.getPid(advancedParamList);
		List<String> deptList = getDeptDataList(pid);
		int year = EduUtils.getSchoolYear4();
//		String pid = null;
//		Map<String, Object> deptDataMap = businessService.getDeptDataForGoingDownStu(deptList, pid, year);
//		deptList = (List<String>) deptDataMap.get("deptList");
		// 组织机构
		List<Map<String, Object>> reList = new ArrayList<>();
//		List<Map<String, Object>> nextLevelList = (List<Map<String, Object>>) deptDataMap.get("queryList");
		List<AdvancedParam> advancedList = new ArrayList<>();
		AdvancedParam ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
	    advancedList.add(ad_dept);
	    /****************************************************/
		String instructorsSql  = notGradDegreeDao.queryXwfbAndRatios(year, deptList, advancedParamList,fb),
//			instructorsSql2 = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, pid, instructorsSql, true, true, true, false, year, null),
				   stuSql  = businessDao.getStuSql(year, deptList, null),
				   stuSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, stuSql, true, true, false, false, year, null),
		   instructorsSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, instructorsSql, true, true, true, false, year, null);
			List<Map<String, Object>> instructors_li = baseDao.queryListInLowerKey(instructorsSql2);
			List<Map<String, Object>> stu_li = baseDao.queryListInLowerKey(stuSql2);
	/********************************************************/
			for(Map<String, Object> map : instructors_li){
				Map<String,Object> map1 =new HashMap<>();
				for(Map<String, Object> stu_map : stu_li){
					if(map.get(Constant.NEXT_LEVEL_COLUMN_CODE).equals(stu_map.get(Constant.NEXT_LEVEL_COLUMN_CODE))){
					map1.put("id",map.get(Constant.NEXT_LEVEL_COLUMN_CODE));
					map1.put("name",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
					map1.put("value_change",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
					if(Integer.parseInt((String) stu_map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))==0){
						map1.put("value_stu",0);
					}else{
					map1.put("value_stu",Math.round((Integer.parseInt((String) map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))/Integer.parseInt((String) stu_map.get(Constant.NEXT_LEVEL_COLUMN_COUNT)))*100)*0.01d);
					}
					}
				}
				reList.add(map1);
//			ad_dept.setValues(MapUtils.getString(map, "id"));
//			// 人数、比例
//			if("1".equals(fb)){
//			List<Map<String, Object>> li = notGradDegreeDao.queryXwfbAndRatio(deptList,advancedList, fb);
//			if(li.size()!=0){
//				map.put("value_change", li.get(0).get("COUNT_"));
//				map.put("value_stu", li.get(0).get("RATIO_"));
//			}else{
//				map.put("value_change", 0);
//				map.put("value_stu", 0);
//			}
//			reList.add(map);
//			}else if("2".equals(fb)){
//				List<Map<String, Object>> li = notGradDegreeDao.queryByfbAndRatio(deptList,advancedList, fb);
//				if(li.size()!=0){
//					map.put("value_change", li.get(0).get("COUNT_"));
//					map.put("value_stu", li.get(0).get("RATIO_"));
//				}else{
//					map.put("value_change", 0);
//					map.put("value_stu", 0);
//				}
//				reList.add(map);
//			}
		}
		// return
		Map<String, Object>	map = DevUtils.MAP();
		map.put("list", reList);
		return map;
	}
	/**
	 * 无学位学生学科分布
	 */
	@Override
	public List<Map<String, Object>> queryXkfb(List<AdvancedParam> advancedList) {
		return queryXkfb(getDeptDataList(),advancedList);
	}
	@Override
	public List<Map<String, Object>> queryXkfb(List<String> deptList,List<AdvancedParam> advancedList) {
		// 无学位学生学科分布
		int year = EduUtils.getSchoolYear4();//得到当前学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return notGradDegreeDao.queryXkfb(year,deptList,stuAdvancedList);
	}
	/**
	 * 无学位学生年级分布
	 */
	@Override
	public List<Map<String, Object>> queryNjfb(List<AdvancedParam> advancedList) {
		return queryNjfb(getDeptDataList(),advancedList);
	}
	@Override
	public List<Map<String, Object>> queryNjfb(List<String> deptList,List<AdvancedParam> advancedList) {
		int year = EduUtils.getSchoolYear4();//得到当前学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList); // 高级查询-学生参数
		// 无学位学生年级分布
		return notGradDegreeDao.queryNjfb(year,deptList,stuAdvancedList);
	}
	/**
	 * 无学位学生性别分布
	 */
	@Override
	public List<Map<String, Object>> queryXbfb(List<AdvancedParam> advancedList) {
		return queryXbfb(getDeptDataList(),advancedList);
	}
	@Override
	public List<Map<String, Object>> queryXbfb(List<String> deptList,List<AdvancedParam> advancedList) {
		// 无学位学生学科分布
		int year = EduUtils.getSchoolYear4();//得到当前学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		// 无学位学生性别分布
		return notGradDegreeDao.queryXbfb(year,deptList,stuAdvancedList);
	}
	/**
	 * 无学位学生原因分布
	 */
	@Override
	public List<Map<String, Object>> queryYyfb(List<AdvancedParam> advancedList) {
		return queryYyfb(getDeptDataList(),advancedList);
	}
	@Override
	public List<Map<String, Object>> queryYyfb(List<String> deptList,List<AdvancedParam> advancedList) {
		// 无学位学生学科分布
		int year = EduUtils.getSchoolYear4();//得到当前学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		// 无学位学生原因
		return notGradDegreeDao.queryYyfb(year,deptList,stuAdvancedList);
	}
	/**
	 * 最近几年无学位学生原因分布
	 */
	@Override
	public Map<String, Object> queryStatefbByYear(List<AdvancedParam> advancedList) {
		return queryStatefbByYear(getDeptDataList(),advancedList);
	}
	@Override
	public Map<String, Object> queryStatefbByYear(List<String> deptList,List<AdvancedParam> advancedList) {
		List<Object> listyear= new ArrayList<>();//得到学年
		List<Map<String, Object>> list=new ArrayList<>();
		int schoolYear = EduUtils.getSchoolYear4();//得到当前学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		for(int year=schoolYear-5;year<=schoolYear;year++){
			Map<String, Object> map =new HashMap<>();
			listyear.add(year);
			map.put("year", year);
			List<Map<String, Object>> li=notGradDegreeDao.queryYyfb(year,deptList,stuAdvancedList);
			map.put("list", li);
			list.add(map);
		}
		Map<String, Object>	map = DevUtils.MAP();
		map.put("year", listyear);
		map.put("list", list);
		return map;
//		return notGradDegreeDao.queryStatefbByYear(deptList);
	}
	/**
	 * 最近几年无学位学生年级分布
	 */
	@Override
	public Map<String, Object> queryNjfbByYear(List<AdvancedParam> advancedList) {
		return queryNjfbByYear(getDeptDataList(),advancedList);
	}
	@Override
	public Map<String, Object> queryNjfbByYear(List<String> deptList,List<AdvancedParam> advancedList) {
		List<Object> listyear= new ArrayList<>();//得到学年
		List<Map<String, Object>> list=new ArrayList<>();
		int schoolYear = EduUtils.getSchoolYear4();//得到当前学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		for(int year=schoolYear-5;year<=schoolYear;year++){
			Map<String, Object> map =new HashMap<>();
			listyear.add(year);
			map.put("year", year);
			List<Map<String, Object>> li=notGradDegreeDao.queryNjfb(year,deptList,stuAdvancedList);
			map.put("list", li);
			list.add(map);
		}
		Map<String, Object>	map = DevUtils.MAP();
		map.put("year", listyear);
		map.put("list", list);
		return map;
	}
	/**
	 * 最近几年无学位学生学科分布
	 */
	@Override
	public Map<String, Object> queryXkfbByYear(List<AdvancedParam> advancedList) {
		return queryXkfbByYear(getDeptDataList(),advancedList);
	}
	@Override
	public Map<String, Object> queryXkfbByYear(List<String> deptList,List<AdvancedParam> advancedList) {
		List<Object> listyear= new ArrayList<>();//得到学年
		List<Map<String, Object>> list=new ArrayList<>();
		int schoolYear = EduUtils.getSchoolYear4();//得到当前学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		for(int year=schoolYear-5;year<=schoolYear;year++){
			Map<String, Object> map =new HashMap<>();
			listyear.add(year);
			map.put("year", year);
			List<Map<String, Object>> li=notGradDegreeDao.queryXkfb(year,deptList,stuAdvancedList);
			map.put("list", li);
			list.add(map);
		}
		Map<String, Object>	map = DevUtils.MAP();
		map.put("year", listyear);
		map.put("list", list);
		return map;
	}
	/**
	 * 最近几年无学位学生性别分布
	 */
	@Override
	public Map<String, Object> queryXbfbByYear(List<AdvancedParam> advancedList) {
		return queryXbfbByYear(getDeptDataList(),advancedList);
	}
	@Override
	public Map<String, Object> queryXbfbByYear(List<String> deptList,List<AdvancedParam> advancedList) {
		List<Object> listyear= new ArrayList<>();//得到学年
		List<Map<String, Object>> list=new ArrayList<>();
		int schoolYear = EduUtils.getSchoolYear4();//得到当前学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		for(int year=schoolYear-5;year<=schoolYear;year++){
			Map<String, Object> map =new HashMap<>();
			listyear.add(year);
			map.put("year", year);
			List<Map<String, Object>> li=notGradDegreeDao.queryXbfb(year,deptList,stuAdvancedList);
			map.put("list", li);
			list.add(map);
		}
		Map<String, Object>	map = DevUtils.MAP();
		map.put("year", listyear);
		map.put("list", list);
		return map;
	}
	/**
	 * 标准代码-学工口-学生无法毕业/无学位证无学位证、无法毕业）
	 */
	@Override
	public List<Map<String, Object>> queryNoDegreeList() {
		List<Map<String, Object>> list = new ArrayList<>();
		String[][] array = Constant.No_GradDegree_Code;
		for(String[] ary : array){
			Map<String, Object> map = new HashMap<>();
			map.put("id", ary[0]);
			map.put("mc", ary[1]);
			list.add(map);
		}
		return list;
	}
	@Override
	public Map<String, Object> getTeaDetail(
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
	    	String  stuDetailSql=notGradDegreeDao.getNodegreeStuSql(2016,getDeptDataList(), advancedParamList);
	    	if(keyValue != null)
	    		for(Map.Entry<String, Object> entry : keyValue.entrySet()){
	    			String type = entry.getKey(), code = String.valueOf(entry.getValue());
	    		
	    			if(code.contains(",")){
	    				if("2".equals(code.split(",")[1]))
	    				{
	    				type="noGrade";
	    				}else{
	    					code=code.split(",")[0];
	    				}
	    				
	    			}else{
	    			if(code.contains(":")){
	    				int year = Integer.valueOf(code.split("-")[0]);
	    				code=code.split(":")[1];
	    				stuDetailSql=notGradDegreeDao.getNodegreeStuSql(year,getDeptDataList(), advancedParamList);
	    			}
	    			}
	    			if(("noGrade".equals(type))){
	    				stuDetailSql=notGradDegreeDao.getNoGraduationStuSql(2016,getDeptDataList(), advancedParamList);
	    				 code=code.split(",")[0];
	    				 if("null".equals(code)){
	    					 stuDetailSql+="  AND TT.NO IS NOT NULL ";
	    				 }else{
	    					 stuDetailSql+=" AND  TT.DEPTMC = '"+code+"'  AND TT.NO IS NOT NULL ";
	    				 }
	    			}else{
	    				if(!("noDegree".equals(type))){
	    					stuDetailSql+=" AND "+type+" = '"+code+"' ";
	    				}else{
	    					stuDetailSql+=" AND TT.NO IS NOT NULL ";
	    				}
	    			}
	    		}
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+")";
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
