package cn.gilight.dmm.teaching.service.impl;

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
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.dao.CourseDao;
import cn.gilight.dmm.teaching.service.CourseService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Repository("courseService")
public class CourseServiceImpl implements CourseService{
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private CourseDao courseDao;
	@Resource 
	private BaseDao baseDao;
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"course";

	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	
	private List<String> getDeptDataList(String id){
		return businessService.getDeptDataList(ShiroTag, id);
	}
	public Map<String,Object> getAbstract(List<AdvancedParam> advancedParamList, String edu,
			String schoolYear, String termCode){
		String pid=AdvancedUtil.getPid(advancedParamList);
		List<String> deptList = getDeptDataList(pid);
		int count =courseDao.queryAbstract(deptList, edu, schoolYear, termCode);
		Map<String,Object> m=new HashMap<>();
		m.put("value", count);
		Map<String,Object> map=DevUtils.MAP();
		map.put("map", m);
		return map;
	}
	@Override
	public Map<String, Object> getBzdm() {
		List<Map<String, Object>> list = businessService.queryBzdmXnXq5();
		Map<String, Object> map = DevUtils.MAP();
		map.put("xnxq", list);
		map.put(AdvancedUtil.Stu_EDU_ID, businessService.queryBzdmStuEducationList(getDeptDataList()));
		List<Map<String,Object>> courseList=courseDao.queryselectDown();
		map.put("sele",courseList);
		return map;
	}
	
	@Override
	public Map<String, Object> getCourseDistribution(List<AdvancedParam> advancedParamList, String edu,
			String schoolYear, String termCode) {
		List<String> deptList = getDeptDataList(AdvancedUtil.getPid(advancedParamList));
		//课程属性
		List<Map<String,Object>> attrList=courseDao.queryCourseByAttribute(deptList, advancedParamList, edu, schoolYear, termCode);
		//课程性质
		List<Map<String,Object>> natList=courseDao.queryCourseByNature(deptList, advancedParamList, edu, schoolYear, termCode);
		//课程类别
		List<Map<String,Object>> cateList=courseDao.queryCourseByCategory(deptList, advancedParamList, edu, schoolYear, termCode);
		Map<String, Object> map = DevUtils.MAP();
		int abs=courseDao.queryAbstract(deptList, edu, schoolYear, termCode);
		map.put("abs", abs);
		map.put("attr", attrList);
		map.put("nat",natList);
		map.put("cate", cateList);
		return map;
	}
	
	@Override
	public Map<String,Object> getDeptDistribution(List<AdvancedParam> advancedParamList, String edu,
			String schoolYear, String termCode,String codeType,String code){
		String pid=AdvancedUtil.getPid(advancedParamList);
		List<String> deptList = getDeptDataList(pid);
			  // instructorsSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, instructorsSql, false, true, false, false, year, null);
		/*List<Map<String, Object>> instructors_li = baseDao.queryListInLowerKey(instructorsSql2);
		List<Map<String, Object>> dataList=new ArrayList<>();
		String code_column = Constant.NEXT_LEVEL_COLUMN_CODE, name_column = Constant.NEXT_LEVEL_COLUMN_NAME, count_column = Constant.NEXT_LEVEL_COLUMN_COUNT;
		for(Map<String, Object> map:instructors_li){
			Map<String, Object> dataMap = new HashMap<String,Object>();
			dataMap.put("name", MapUtils.getString(map, name_column));
			dataMap.put("value", MapUtils.getInteger(map,count_column));
			dataMap.put("id", MapUtils.getInteger(map,code_column));
			dataMap.put("code", MapUtils.getInteger(map,code_column));
			dataList.add(dataMap);
		}*/
		List<Map<String, Object>> dataList=new ArrayList<>();
		List<Map<String, Object>> deptMapList=new ArrayList<Map<String,Object>>();
		if(PmsUtils.isAllPmsData(deptList)){
			deptMapList=businessDao.queryYxList(deptList);
		}else{
			deptMapList=businessDao.queryZyListStu(deptList);
		}
		for(Map<String,Object> map:deptMapList){
			//封装权限
			deptList=PmsUtils.getDeptListByDeptMap(map);
			String instructorsSql  = courseDao.getlevelSql(deptList,edu,schoolYear, termCode, codeType, code);
			int count =baseDao.queryForCount(instructorsSql);
			Map<String,Object> o= new HashMap<>();
			o.put("value", count);
			o.put("type", codeType);
			o.put("code",MapUtils.getString(map, "id"));
			o.put("name", MapUtils.getString(map, "name"));
			o.put("id", MapUtils.getString(map, "id"));
			dataList.add(o);
		}
		String deptMc=businessService.getLevelNameById(deptMapList.isEmpty() ? null : MapUtils.getString(deptMapList.get(0), "id"));
		Map<String, Object> map = DevUtils.MAP();
		map.put("list",dataList);
		map.put("deptMc", deptMc);
		return map;
	}
	
	@Override
	public Map<String,Object> getSubjectDistribution(List<AdvancedParam> advancedParamList, String edu,
			String schoolYear, String termCode,String codeType,String code){
		String pid=AdvancedUtil.getPid(advancedParamList);
		List<String> deptList = getDeptDataList(pid);
		List<Map<String,Object>> subList=businessService.queryBzdmSubjectDegree();
		List<Map<String, Object>> list=new ArrayList<>();
		for(Map<String,Object> map:subList){
			String id=MapUtils.getString(map, "id"),name=MapUtils.getString(map, "mc");
			String sql=courseDao.getCourseBySubject(deptList,edu,schoolYear, termCode, codeType, code,id);
			int count =baseDao.queryForCount(sql);
			Map<String, Object> nm = new HashMap<String,Object>();
			nm.put("name", name);
			nm.put("value", count);
			nm.put("id", id);
			nm.put("type", codeType);
			nm.put("code",id);
			list.add(nm);
		}
		Map<String, Object> fin = new HashMap<String,Object>();
		fin.put("list", list);
		return fin;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getDeptHistory(List<AdvancedParam> advancedParamList, String edu,String codeType,String code){
		int length=Constant.Year_His_Len;
		int year=EduUtils.getSchoolYear4();
		List<Map<String, Object>> finDataList = new ArrayList<>();
		List<String> legend_ary = new ArrayList<>(),value_ary=new ArrayList<>();
		String deptMc=null;
		for(int i=length;i>1;i--){
			String schoolYear=(year-i+2)+"-"+(year-i+3);
			Map<String,Object> map=getDeptDistribution(advancedParamList, edu, schoolYear, null, codeType, code);
			List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("list");
			if(i==2){
				deptMc=MapUtils.getString(map, "deptMc");
				for(Map<String,Object> m:list){
					legend_ary.add(MapUtils.getString(m, "name"));
					value_ary.add(MapUtils.getString(m, "id"));
				}
			}
			Map<String, Object> finMap = new HashMap<>();
			for(Map<String,Object> m:list){
				finMap.put(MapUtils.getString(m, "id"), MapUtils.getIntValue(m, "value"));
			}
			finMap.put("name", schoolYear);
			finDataList.add(finMap);//补全len_ary
		}

		Map<String, Object> map = DevUtils.MAP();
		map.put("legend_ary", legend_ary);
		map.put("value_ary", value_ary);
		map.put("data", finDataList);
		map.put("deptMc", deptMc);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getSubjectHistory(List<AdvancedParam> advancedParamList, String edu,String codeType,String code){
		int length=Constant.Year_His_Len;
		int year=EduUtils.getSchoolYear4();
		List<Map<String, Object>> finDataList = new ArrayList<>();
		List<String> legend_ary = new ArrayList<>(),value_ary=new ArrayList<>();
		for(int i=length;i>1;i--){
			String schoolYear=(year-i+2)+"-"+(year-i+3);
			Map<String,Object> map=getSubjectDistribution(advancedParamList, edu, schoolYear, null, codeType, code);
			List<Map<String, Object>> list=(List<Map<String, Object>>) map.get("list");
			if(i==2){//最后学年获取学院名字 id
				for(Map<String,Object> m:list){
					legend_ary.add(MapUtils.getString(m, "name"));
					value_ary.add(MapUtils.getString(m, "id"));
				}
			}
			Map<String, Object> finMap = new HashMap<>();
			for(Map<String,Object> m:list){
				finMap.put(MapUtils.getString(m, "id"), MapUtils.getIntValue(m, "value"));
			}
			finMap.put("name", schoolYear);
			finDataList.add(finMap);//补全len_ary
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("legend_ary", legend_ary);
		map.put("value_ary", value_ary);
		map.put("data", finDataList);
		return map;
	}

}
