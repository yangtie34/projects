package cn.gilight.dmm.xg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
import cn.gilight.dmm.xg.dao.StuFromDao;
import cn.gilight.dmm.xg.service.StuEnrollService;
import cn.gilight.dmm.xg.service.StuFromService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("stuFromService")
public class StuFromServiceImpl implements StuFromService {
	@Resource
	private StuFromDao stuFromDao;
	@Resource
	private StuEnrollService stuEnrollService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao basedao;
	/**
	 * 生源地分析页面 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg + "stuFrom";
	private List<String> getDeptDataList() {
		return getDeptDataList(null);
	}
	private List<String> getDeptDataList(String id) {
		return businessService.getDeptDataList(ShiroTag, id);
	}
	@Override
	public Map<String, Object> getMinGrade() {
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		Map<String, Integer> map = stuFromDao.queryMinGrade(deptList);
		Map<String, Object> map1 = DevUtils.MAP();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int min = map.get("value");
		for (int i = schoolYear; i > (min - 1); i--) {
		Map<String, Object>	temp = new HashMap<String, Object>();
		    temp.put("id", i);
		    temp.put("mc", i+"年");
			list.add(temp);
		}
		map1.put("list", list);
		map1.put("min", min);
		map1.put("max", schoolYear);
		return map1;
	}
	@Override
	public Map<String, Object> getCountLine(List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid) {
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		from = from == null ? String.valueOf(schoolYear) : from;
		to = to == null ? String.valueOf(schoolYear) : to;
		int a = Integer.parseInt(from);
		int b = Integer.parseInt(to);
		edu = edu==null?Constant.Stu_Education_Group[0][0]:edu;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		int start= a-1;
		if((b-a)<3){
			start = b-4;
		}
		for(int i = start;i<b+1;i++){
			Map<String,Object> temp = getCountLv(String.valueOf(i), edu, divid, deptList, advancedParamList),
					           temp1 = new HashMap<String, Object>();
			temp1.put("field", i);
			temp1.put("value", MapUtils.getIntValue(temp, "count"));
		    list.add(temp1);
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("line", list);
		return map;
	}
	@Override
	public Map<String, Object> getCountAndLv(List<AdvancedParam> advancedParamList, String from,String to,
			String edu, String divid) {
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		from = from==null?String.valueOf(schoolYear):from;
		to = to==null?String.valueOf(schoolYear):to;
		int a = Integer.parseInt(from);
		int b = Integer.parseInt(to);
		divid = divid == null ? stuFromDao.getChinaId():divid;
		edu = edu==null?Constant.Stu_Education_Group[0][0]:edu;
		int nian = Integer.parseInt(to);
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		int start= a;
		if((b-a)<4){
			start = b-4;
		}
		for (int year = start;year<(b+1);year++){
			Map<String,Object> temp = getCountLv(String.valueOf(year), edu, divid, deptList, advancedParamList);
			result.add(temp);
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("line", result);
		return map;
	}
	@Override
	public List<Map<String, Object>> getStuEdu() {
		List<String> deptList = getDeptDataList(); // 权限
		List<Map<String, Object>> list1 = stuFromDao.queryStuEdu(deptList);
		List<Map<String, Object>> list = businessService.queryBzdmStuEducationList();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>(); 
		for(int i =1;i<list.size();i++){
			String temp = MapUtils.getString(list.get(i), "id");
			for(int j=0;j<list1.size();j++){
				String temp1 = MapUtils.getString(list1.get(j), "code");
				if(temp != null && temp1 != null && temp.equals(temp1)){
					result.add(list.get(i));
				}
			}
		}
		if (result.size()==2){
			result.add(0,list.get(0));
		}
		return result;
	}
	@Override
	public Map<String, Object> getStuFrom(List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid, Boolean updown,int pagesize,int index) {
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = new ArrayList<AdvancedParam>(); // 高级查询-学生参数
		if (advancedParamList != null){stuAdvancedList.addAll(advancedParamList);}
		String id = divid == null ? stuFromDao.getChinaId() : divid;
		List<Map<String, Object>> list = stuFromDao.queryStuFrom(schoolYear,
				deptList, from, to, edu, id, updown,stuAdvancedList);
		// String ids = list.size()==0?"0" :list.get(0).get("id").toString();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < list.size(); i++) {
			Map<String, Object> temp = list.get(i);
			list1.add(temp);
			list2.add(temp);
		}
		Iterator<Map<String, Object>> ite = list2.iterator();
		while (ite.hasNext()) {
			Map<String, Object> m = ite.next();
			if (MapUtils.getIntValue(m, "value") == 0) {
				ite.remove();
			}
		}
		Map<String, Object> result = stuFromDao.getAllGrowth(schoolYear,
				deptList, from, to, edu, id, advancedParamList);
		Map<String, Object> map = DevUtils.MAP();
		compareCount(list1, "value", false);
		compareCount(list2, "r", true);
		int max=0;
		if(list1.size()>1){
			max = MapUtils.getIntValue(list1.get(1), "value");
			if (max==0){
				max=100;
			}
		}else{
			max = 100;
		}
		if(max<100){
			max = 100;
		}
	    max = numShift(max);
		map.put("list", list1);
		map.put("pie", list2);
		map.put("maptype", MapUtils.getString(list.get(0), "maptype"));
		map.put("sum", MapUtils.getIntValue(list.get(0), "count"));
		map.put("sxq", MapUtils.getIntValue(list.get(0), "sxq"));
		map.put("sxqid", MapUtils.getString(list.get(0), "sxqid"));
		map.put("sub", MapUtils.getIntValue(result, "count"));
		map.put("avg",MapUtils.getDoubleValue(result, "scale"));
		map.put("cc", MapUtils.getString(list1.get(0), "cc"));
		map.put("max", max);
		return map;
	}
    @Override
	public Map<String, Object> getSchoolTag(List<AdvancedParam> advancedParamList, String from,
			String to, String edu, String divid, Boolean updown,int pagesize,int index,Boolean Order) {
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		String id = divid == null ? stuFromDao.getChinaId() : divid;
		List<AdvancedParam> advList = new ArrayList<AdvancedParam>();
		if (stuAdvancedList != null){advList.addAll(stuAdvancedList);}
		List<Map<String, Object>> list = stuFromDao.querySchoolTag(schoolYear,
				deptList, from, to, edu, id, updown,advList);
     	compareCount(list, "r", Order);
		index = index<0 ? 1 : index;
//		int pageCount = new Double(Math.ceil(MathUtils.getDivisionResult(list.size(), pagesize, 1))).intValue();
		int size = list.size();
		int start = (index-1)*pagesize; start = start>size ? size : start;
		int end = index*pagesize; end = end>size ? size : end;
		list = list.subList(start, end);
		Map<String, Object> map = DevUtils.MAP();
		map.put("total", size);
		map.put("list", list);
		return map;
	}
    @Override
	public Map<String, Object> getGrowth(List<AdvancedParam> advancedParamList, String from,
			String to, String edu, String divid, Boolean updown) {
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		String id = divid == null ? stuFromDao.getChinaId() : divid;
		List<AdvancedParam> advList = new ArrayList<AdvancedParam>();
		if (stuAdvancedList != null){advList.addAll(stuAdvancedList);}
		Map<String, Object> result = stuFromDao.queryGrowth(schoolYear,
				deptList, from, to, edu, id, updown,advList);
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", result.get("list"));
		map.put("line", result.get("line"));
		map.put("xl", result.get("xl"));
		return map;
	}
    @Override
	public Map<String, Object> getStuFromTab(List<AdvancedParam> advancedParamList, String from, String to,
			String edu, String divid, Boolean updown,int pagesize,int index) {
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		String id = divid == null ? stuFromDao.getChinaId() : divid;
		List<AdvancedParam> advList = new ArrayList<AdvancedParam>();
		if(stuAdvancedList != null){advList.addAll(stuAdvancedList);}
		List<Map<String, Object>> list = stuFromDao.queryStuFrom(schoolYear,
				deptList, from, to, edu, id, updown,advList);
		// String ids = list.size()==0?"0" :list.get(0).get("id").toString();
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		for (int i = 1; i < list.size(); i++) {
			Map<String, Object> temp = list.get(i);
			list1.add(temp);
		}
		Iterator<Map<String, Object>> ite = list1.iterator();
		while (ite.hasNext()) {
			Map<String, Object> m = ite.next();
			if (MapUtils.getIntValue(m, "value") == 0) {
				ite.remove();
			}
		}
		int zs = 0;
		for (Map<String, Object> map1 : list1) {
			zs += MapUtils.getIntValue(map1, "value");
		}
		index = index<0 ? 1 : index; 
		int pageSize  = pagesize;
		Map<String, Object> map = DevUtils.MAP();
		compareCount(list1, "r", true);
		List<Map<String,Object>> result = list1;
//		int pageCount = new Double(Math.ceil(MathUtils.getDivisionResult(result.size(), pageSize, 1))).intValue();
		int size = result.size();
		int start = (index-1)*pageSize; start = start>size ? size : start;
		int end = index*pageSize; end = end>size ? size : end;
		result = result.subList(start, end);
		map.put("list", result);
		map.put("total", size);
		return map;
	}
    @Override
    public Map<String,Object> getStuList(List<AdvancedParam> advancedParamList,Page page, 
			Map<String, Object> keyValue, List<String> fields/*,String divid,Boolean updown,String from, String to,String edu*/){
		String sql = getStuDetailSql(advancedParamList, keyValue, fields);
		Map<String,Object> map = basedao.createPageQueryInLowerKey(sql, page);
		return map;
    }
    @Override
    public List<Map<String,Object>> getStuDetail(List<AdvancedParam> advancedParamList, 
			Map<String, Object> keyValue, List<String> fields/*,String divid,Boolean updown,String from, String to,String edu*/){
    	String sql = getStuDetailSql(advancedParamList, keyValue, fields);
		List<Map<String,Object>> list = basedao.queryListInLowerKey(sql);
		return list;
    }
    @Override
    public List<Map<String,Object>> getExportData(List<AdvancedParam> advancedParamList,String from, String to,
			String edu, String divid, Boolean updown,String bs){
    	List<AdvancedParam> stuAdvancedList = new ArrayList<AdvancedParam>(); // 高级查询-学生参数
		if (advancedParamList != null){stuAdvancedList.addAll(advancedParamList);}
		String id = divid == null ? stuFromDao.getChinaId() : divid;
		int schoolYear = EduUtils.getSchoolYear4();
		List<String> deptList = getDeptDataList();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if (bs.equals("from")){
			result = getFromExport(stuAdvancedList, from, to, edu, id, updown, deptList, schoolYear);
		}else{
			result = getGrowthExport(stuAdvancedList, from, to, edu, id, updown, deptList, schoolYear);
		}
    	return result;
    }
    private List<Map<String, Object>> getFromExport(List<AdvancedParam> stuAdvancedList,String from, String to,
			String edu, String id, Boolean updown,List<String> deptList,int schoolYear){
		List<Map<String, Object>> list = stuFromDao.queryStuFrom(schoolYear,
				deptList, from, to, edu, id, updown,stuAdvancedList),
				result = new ArrayList<Map<String,Object>>();
		int all = MapUtils.getIntValue(list.get(0), "count");
	    list = list.subList(1, list.size());
		Iterator<Map<String, Object>> ite = list.iterator();
		while (ite.hasNext()) {
			Map<String, Object> m = ite.next();
			if (MapUtils.getIntValue(m, "value") == 0) {
				ite.remove();
			}
		}
		compareCount(list, "value", false);
		for (int i=0;i<list.size();i++){
			Map<String,Object> temp = list.get(i),
					           temp1 = new HashMap<String, Object>();
			int count = MapUtils.getIntValue(temp, "value");
			String name = MapUtils.getString(temp, "name");
			Double scale = MathUtils.get2Point(MathUtils.getDivisionResult(count, all, 4)*100);
			temp1.put("name", name);temp1.put("value", count);temp1.put("scale", scale+"%");
			result.add(temp1);
		}
    	return result;
    }
    @SuppressWarnings("unchecked")
	private List<Map<String, Object>> getGrowthExport(List<AdvancedParam> stuAdvancedList,String from, String to,
			String edu, String id, Boolean updown,List<String> deptList,int schoolYear){
		Map<String, Object> reMap = stuFromDao.queryGrowth(schoolYear,
				deptList, from, to, edu, id, updown,stuAdvancedList);
		List<Map<String,Object>> list = (List<Map<String, Object>>) reMap.get("list"),
				result = new ArrayList<Map<String,Object>>();
		for (int i=0;i<list.size();i++){
			Map<String,Object> temp = list.get(i),
					           temp1 = new HashMap<String, Object>();
			String count = MapUtils.getString(temp, "val1");
			String name = MapUtils.getString(temp, "name");
			String bs = MapUtils.getString(temp,"bs");
			String scale = MapUtils.getString(temp, "val2")+"%";
			if ("0".equals(bs)){
				scale = "数据异常";
			}
			temp1.put("name", name);temp1.put("value", count);temp1.put("scale", scale);
			result.add(temp1);
		}
    	return result;
    }
    private Map<String,Object> getCountLv(String enrollGrade,String edu,String divid,List<String> deptList,List<AdvancedParam> advancedParamList){
		Map<String,Object> temp = new HashMap<String, Object>();
		String sql = stuFromDao.queryNowDiv(divid);
		Map<String,Object> map = basedao.queryMapInLowerKey(sql);
		String pid = MapUtils.getString(map, "pid");
		List<AdvancedParam> advancedList = new ArrayList<AdvancedParam>();
		if(advancedParamList != null){advancedList.addAll(advancedParamList);} 
		AdvancedParam enrollAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, enrollGrade);
		advancedList =AdvancedUtil.add(advancedList, enrollAdp);
		AdvancedParam eduAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
		advancedList =AdvancedUtil.add(advancedList, eduAdp);
		List<AdvancedParam> advancedList1 = new ArrayList<AdvancedParam>();
		if(advancedList != null){advancedList1.addAll(advancedList);}
		AdvancedParam sydAdp1 = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, pid);
		advancedList = AdvancedUtil.add(advancedList1, sydAdp1);
		String allStuSql = businessDao.getStuSql(deptList,advancedList1);
		int all = basedao.queryForCount(allStuSql);
		AdvancedParam sydAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, divid);
		advancedList = AdvancedUtil.add(advancedList, sydAdp);
		String stuSql = businessDao.getStuSql(deptList, advancedList);
		int count = basedao.queryForCount(stuSql);
		temp.put("name", enrollGrade);
		temp.put("count", count);
		temp.put("lv", MathUtils.get2Point(MathUtils.getDivisionResult(count, all, 4)*100));
		return temp;
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
				int count1 = MapUtils.getInteger(o1, compareField), count2 = MapUtils
						.getInteger(o2, compareField), flag = 0, pare = asc
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
	/**
	 * 数字转化为100的倍数
	 * @param num
	 */
	private int numShift(int num){
		String a = String.valueOf(num);
		int val1 = Integer.parseInt(a.substring(a.length()-2, a.length()));
		int val2 = Integer.parseInt(a.substring(0, a.length()-2));
		if(val1>0){
			val2=val2+1;
		}
		String temp = String.valueOf(val2)+"00";	
		
		return Integer.parseInt(temp);
	}
	private String getStuDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields){
		List<String> deptList = getDeptDataList(); // 权限
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		String xzqhmc = MapUtils.getString(keyValue, "xzqhmc");
		String xzqh = MapUtils.getString(keyValue, "xzqh")== null ?stuFromDao.getChinaId():MapUtils.getString(keyValue, "xzqh"),
		    	   from = MapUtils.getString(keyValue, "from"),
		    	   to   = MapUtils.getString(keyValue, "to"),
		    	   edu  = MapUtils.getString(keyValue, "edu")== null ?null:MapUtils.getString(keyValue, "edu");
		xzqh = xzqhmc != null ? stuFromDao.getIdByMc(xzqhmc):xzqh;
		Boolean	updown = MapUtils.getBooleanValue(keyValue, "updown");
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		AdvancedParam eduAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
		AdvancedUtil.add(advancedParamList,eduAdp);
		List<AdvancedParam> stuAdvancedList      = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		String sql = stuFromDao.queryStuList(schoolYear, deptList, from, to, edu, xzqh, updown, stuAdvancedList);
		return sql;	
	}
	
}
