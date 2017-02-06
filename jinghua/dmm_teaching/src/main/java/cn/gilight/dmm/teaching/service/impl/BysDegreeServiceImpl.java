package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import cn.gilight.dmm.teaching.dao.BysDegreeDao;
import cn.gilight.dmm.teaching.dao.BysQxDao;
import cn.gilight.dmm.teaching.service.BysDegreeService;
import cn.gilight.dmm.teaching.service.BysQxService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("bysDegreeService")
public class BysDegreeServiceImpl implements BysDegreeService {
	
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private BysQxDao bysQxDao;
	@Resource
	private BysDegreeDao bysDegreeDao;
	@Resource
	private BysQxService bysQxService;

	private static final String Type_Dept = "dept";
	private static final String Type_Bylv = "bylv";
	private static final String Type_Sylv = "sylv";
	private static final String Type_SchoolYear = "schoolYear";
	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"bysDegree";
	
	private static final Object[][] Table_Th = {{"YX","院系"},{"ZY","专业"},{"XK","学科"}};
	private static final Object[][] Table_Th1 = {{"all","人数"},{"by","毕业人数"},{"bylv","毕业率"},
		{"bybh","相比上一年毕业率"},{"sy","学位授予"},{"sylv","学位授予率"},{"sybh","相比上一年学位授予率"}};
	private static final Object[][] Chart_Select = {{Constant.SCORE_AVG,"平均成绩"},{Constant.SCORE_MIDDLE,"中位数成绩"},
		{Constant.SCORE_MODE,"众数成绩"},{Constant.SCORE_FC,"方差"},{Constant.SCORE_BZC,"标准差"}};
	private static final Object[][] Type_List = {{"gpa","绩点"},{"score","百分制成绩"}};
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	@Override
	public Map<String,Object> getThList(){
		List<Map<String,Object>> list1 = getObjectToList(Table_Th),
				                 list2 = getObjectToList(Table_Th1),
				                 list3 = getObjectToList(Chart_Select),
		                         list4 = getObjectToList(Type_List);
		Map<String,Object> map = DevUtils.MAP();
		map.put("th", list1);
		map.put("fth", list2);
		map.put("select", list3);
		map.put("type", list4);
		return map;
	}
	@Override
	public Map<String,Object> getTopData(List<AdvancedParam> advancedList,String schoolYear){
		Map<String,Object> param = getParamsByParam(advancedList, schoolYear);
		String stuSql = MapUtils.getString(param, "stusql"); 
		schoolYear = MapUtils.getString(param, "schoolyear");
		Map<String,Object> temp =  getCount(schoolYear, stuSql);
		Map<String,Object> map = DevUtils.MAP();
		map.putAll(temp);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getLvByDept(List<AdvancedParam> advancedList,String schoolYear){
		Map<String,Object> param = getParamsByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		int year = MapUtils.getIntValue(param, "year");
		List<String> deptList = (List<String>) MapUtils.getObject(param, "deptlist");
		advancedList =  (List<AdvancedParam>) MapUtils.getObject(param, "advancedlist");
        List<Map<String,Object>> list = bysDegreeDao.getLvByDept(schoolYear, deptList, advancedList),
        		result = new ArrayList<Map<String,Object>>();
    	String deptid = "";int i =0;
        for(Map<String,Object> temp : list){
			Map<String,Object> temp1 = new HashMap<String, Object>(),
					           temp2 = new HashMap<String, Object>();
			String code = MapUtils.getString(temp, "code"),
				   field = MapUtils.getString(temp, "field");
			Double bylv = MapUtils.getDouble(temp, "bylv"),
				   sylv = MapUtils.getDouble(temp, "sylv");
			if(i==0){
				deptid =code; 
				i++;
			}
			temp1.put("code", code);temp1.put("field", field);temp1.put("name", "毕业率");
			temp1.put("id", "bylv");temp1.put("value", bylv);
			temp2.put("code", code);temp2.put("field", field);temp2.put("name", "学位授予率");
			temp2.put("id", "sylv");temp2.put("value", sylv);
			result.add(temp1);result.add(temp2);
		}
        String name = businessService.getLevelNameById(deptid);
        Map<String,Object> typeMap = bysQxService.shiftList(result, "name", "field", "id", "code");
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", typeMap);
		map.put("name", name);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getTableData(List<AdvancedParam> advancedList,String schoolYear,String Lx,String order,Boolean asc,int pagesize,int index){
		Map<String,Object> param = getParamsByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		List<String> deptList = getDeptDataList();
		advancedList =  (List<AdvancedParam>) MapUtils.getObject(param, "advancedlist");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();	
		if (advancedList != null && !advancedList.isEmpty()){
			switch (Lx) {
				case "YX":
					list = getListByDeptList(businessDao.queryYxList(deptList), schoolYear, advancedList);
					break;
				case "XK":
					list = getListBySubjectList(businessService.queryBzdmSubjectDegree(), schoolYear, advancedList);
					break;
				case "ZY":
					list =getListByDeptList(businessDao.queryZyListStu(deptList), schoolYear, advancedList);
					break;
				default:
					break;
				}
		}else{
			switch (Lx) {
			case "YX":
				list = bysDegreeDao.getListByDeptList(schoolYear,businessDao.getYxIdSqlByDeptList(deptList));
				break;
			case "XK":
				list = bysDegreeDao.getListBySubjectList(schoolYear,businessDao.getSubjectDegreeUsefulSql());
				break;
			case "ZY":
				list =bysDegreeDao.getListByDeptList(schoolYear,businessDao.getZyIdSqlByDeptListStu(deptList));
				break;
			default:
				break;
			}
			
		}
		compareCount(list, order, asc);
		int size = list.size();
		int start = (index-1)*pagesize; start = start>size ? size : start;
		int end = index*pagesize; end = end>size ? size : end;
		list = list.subList(start,end);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("total", size);
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getBysScore(List<AdvancedParam> advancedList,String schoolYear,String Lx,String type){
		Map<String,Object> param = getParamsByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		advancedList =  (List<AdvancedParam>) MapUtils.getObject(param, "advancedlist");
		List<String> deptList = getDeptDataList(),
				     yearList = bysQxService.getListByYear(schoolYear, 10, false);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(String year : yearList){
			Map<String,Object> temp = new HashMap<String, Object>();
			String stuSql = businessDao.getStuSql(deptList,advancedList);
			stuSql= bysQxDao.getBysSql(year, stuSql, null, null);
		    List<Double> list = bysDegreeDao.getValueByList(stuSql, type, year);	
		    Double value = 0D;
			switch (Lx) {
				case Constant.SCORE_AVG:
					value = MathUtils.getAvgValue(list);
					break;
				case Constant.SCORE_MIDDLE:
					value = MathUtils.getMiddleValue(list);
					break;
				case Constant.SCORE_MODE:
					value = MathUtils.getModeValue(list);
					break;
				case Constant.SCORE_FC:	
					value = MathUtils.getVariance(list);
					break;
				case Constant.SCORE_BZC:	
					value = MathUtils.getStandardDeviation(list);
					break;
				default:
					break;
			}
			temp.put("field", year);temp.put("value", value);
			result.add(temp);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getBysGpa(List<AdvancedParam> advancedList,String schoolYear,String type){
		Map<String,Object> param = getParamsByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		advancedList =  (List<AdvancedParam>) MapUtils.getObject(param, "advancedlist");
		List<String> deptList = getDeptDataList(),
				     yearList = bysQxService.getListByYear(schoolYear, 10, false);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(String year : yearList){
			String stuSql = businessDao.getStuSql(deptList,advancedList);
			stuSql= bysQxDao.getBysSql(year, stuSql, null, null);
			List<Map<String, Object>> list = bysDegreeDao.getGpaGroup(stuSql, type);
			for(Map<String, Object> temp : list){
				temp.put("code", year);
				temp.put("field", year);
			}
			result.addAll(list);
		}
		Map<String,Object> typeMap = bysQxService.shiftList(result, "name", "field", "id", "code");
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", typeMap);
		return map;
	}
	private Map<String,Object> getParamsByParam(List<AdvancedParam> advancedList,String schoolYear){
		if(schoolYear == null){
			Integer year = businessDao.queryMaxSchoolYear("t_stu_graduate", "school_year");
			if(year == null){
				schoolYear = EduUtils.getSchoolYear9();
			}else{
				schoolYear = String.valueOf(year)+"-"+String.valueOf(year+1);
			}
	   }
		 int nian = Integer.parseInt(schoolYear.substring(0,4));
	   advancedList = advancedList==null ? new ArrayList<AdvancedParam>():advancedList;
	  Map<String,Object> map = new HashMap<String, Object>();
	  map.put("schoolyear", schoolYear);
	  map.put("advancedlist", advancedList);
	  map.put("deptlist", getDeptDataList());
	  map.put("year", nian);
	  map.put("stusql", businessDao.getStuSql(getDeptDataList(), advancedList));
	  return map;
	}
	
	private Map<String,Object> getCount(String schoolYear,String stuSql){
		Map<String,Object> map = new HashMap<String, Object>();
		String   lastYear = EduUtils.getLastSchoolYear9(schoolYear),
				 allSql = bysQxDao.getBysSql(schoolYear, stuSql, null, null),//所有将要毕业的学生
				 ybySql = bysQxDao.getBysSql(schoolYear, stuSql, true, null),//已毕业学生
				 ysySql = bysQxDao.getBysSql(schoolYear, stuSql, null, true),//已授予学位学生
			     qnallSql = bysQxDao.getBysSql(lastYear, stuSql, null, null),//去年所有毕业生
				 qnybySql = bysQxDao.getBysSql(lastYear, stuSql, true, null),//去年已毕业学生
				 qnysySql = bysQxDao.getBysSql(lastYear, stuSql, null, true);//去年已授予学位学生
		int      allCount = baseDao.queryForCount(allSql),
				 ybyCount = baseDao.queryForCount(ybySql),
				 ysyCount = baseDao.queryForCount(ysySql),
				 qnallCount = baseDao.queryForCount(qnallSql),
				 qnybyCount = baseDao.queryForCount(qnybySql),
				 qnysycount = baseDao.queryForCount(qnysySql);
		Double bylv = MathUtils.get2Point(MathUtils.getDivisionResult(ybyCount, allCount, 3)*100),
			   sylv = MathUtils.get2Point(MathUtils.getDivisionResult(ysyCount, allCount, 3)*100),
			   qnbylv = MathUtils.get2Point(MathUtils.getDivisionResult(qnybyCount, qnallCount, 3)*100),
			   qnsylv = MathUtils.get2Point(MathUtils.getDivisionResult(qnysycount, qnallCount, 3)*100);
		map.put("all",allCount);map.put("by",ybyCount);map.put("bylv",bylv);map.put("bybh",qnbylv == 0|| bylv == 0?null:bylv-qnbylv);
		map.put("sy",ysyCount);map.put("sylv",sylv);map.put("sybh",qnsylv == 0 ||sylv == 0 ?null:sylv-qnsylv);
		return map;
	}
	@Override
	public List<Map<String,Object>> getListByDeptList(List<Map<String,Object>> list,String schoolYear,List<AdvancedParam> advancedList){
		for(Map<String,Object> map :list){
			List<String> deptList = PmsUtils.getDeptListByDeptMap(map);
			String stuSql = businessDao.getStuSql(deptList, advancedList);
			Map<String,Object> temp = getCount(schoolYear, stuSql);
			map.putAll(temp);
		}
		return list;
	}
	@Override
	public List<Map<String,Object>> getListBySubjectList(List<Map<String,Object>> list,String schoolYear,List<AdvancedParam> advancedList){
		for(Map<String,Object> map :list){
			String id = MapUtils.getString(map, "id");
			String mc = MapUtils.getString(map, "mc");
			List<String> deptList = PmsUtils.getPmsAll();
			AdvancedUtil.add(advancedList, new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_SUBJECT, id));
			String stuSql = businessDao.getStuSql(deptList, advancedList);
			Map<String,Object> temp = getCount(schoolYear, stuSql);
			map.putAll(temp);map.put("name", mc);
		}
		return list;
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
				Double count1 = MapUtils.getDouble(o1, compareField), count2 = MapUtils
						.getDouble(o2, compareField); int flag = 0, pare = asc
						? 1
						: -1; // 正序为1
				if (count1 > count2)
					flag = pare;
				else if (count1 < count2)
					flag = -pare;
				return flag;
			}
		});
	}
	private List<Map<String,Object>> getObjectToList(Object[][] list){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for (int i =0;i<list.length;i++){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id", list[i][0]);
			map.put("mc", list[i][1]);
			result.add(map);
		}
		return result;
	}
	@Override
	public List<Map<String,Object>> getBySyLvList(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue){
		List<String> deptList = getDeptDataList(); // 权限
		String schoolYear = "",by="";Boolean hasBy= false,hasSy=false;
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				AdvancedUtil.add(stuAdvancedList, getDetailAdp(entry.getKey(), String.valueOf(entry.getValue())));
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				switch (type) {
				case Type_SchoolYear:
					schoolYear =  code;
					break;
				case Type_Bylv:
					hasBy = true;
					by = "bylv";
					break;
				case Type_Sylv:
					hasSy = true;
					by = "sylv";
					break;
				default:
					break;
				}
			}
	    String stuSql = businessDao.getStuSql(deptList, stuAdvancedList);
	    List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    List<String> yearList = bysQxService.getListByYear(schoolYear, 10, false);
	    for(String year : yearList){
	    	Map<String,Object> temp = new HashMap<String, Object>();
	    	 Map<String,Object> map = getBySyLvByYear(year, stuSql, hasBy, hasSy);	
	    	 temp.put("value", MapUtils.getDoubleValue(map, by));
	    	 temp.put("field", year);
	    	 result.add(temp);
	    }
		return result;

	}
	private Map<String,Object> getBySyLvByYear(String schoolYear,String stuSql,Boolean hasBy,Boolean hasSy){
		String   allSql = bysQxDao.getBysSql(schoolYear, stuSql, null, null),//所有将要毕业的学生
		         ybySql = hasBy ?bysQxDao.getBysSql(schoolYear, stuSql, true, null):"",//已毕业学生
				 ysySql = hasSy ?bysQxDao.getBysSql(schoolYear, stuSql, null, true):"";//已授予学位学生
	    int      allCount = baseDao.queryForCount(allSql),
				 ybyCount = hasBy?baseDao.queryForCount(ybySql):0,
				 ysyCount = hasSy?baseDao.queryForCount(ysySql):0;
	    Double   bylv = hasBy? MathUtils.get2Point(MathUtils.getDivisionResult(ybyCount, allCount, 3)*100):0,
				 sylv = hasSy? MathUtils.get2Point(MathUtils.getDivisionResult(ysyCount, allCount, 3)*100):0;
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("bylv", bylv);map.put("sylv", sylv);
		return map;
	}
 	private AdvancedParam getDetailAdp(String type, String value){
		AdvancedParam adp = null;
		if(type != null){
			String group = AdvancedUtil.Group_Stu, code = null;
			switch (type) {
			case Type_Dept: 
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
}
