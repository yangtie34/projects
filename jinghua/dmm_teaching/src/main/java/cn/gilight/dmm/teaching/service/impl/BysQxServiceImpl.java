package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.Date;
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
import cn.gilight.dmm.teaching.dao.BysQxDao;
import cn.gilight.dmm.teaching.service.BysQxService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("bysQxService")
public class BysQxServiceImpl implements BysQxService {

	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	@Resource
	private BysQxDao bysQxDao;
	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"bysQx";
	private static final String Type_Direction_Code = "direction";
	private static final String Type_Reason_Code = "reason";
	private static final String Type_School_Year = "schoolYear";
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	
	@Override
	public List<Map<String,Object>> getTimeList(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Integer max = businessDao.queryMaxSchoolYear("t_stu_graduate", "school_year");
		Integer min = businessDao.queryMinSchoolYear("t_stu_graduate", "school_year");
		if(max == null || min == null){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id",EduUtils.getSchoolYear9());
			map.put("mc",EduUtils.getSchoolYear9()+"学年");
			list.add(map);
		}else{
			for(int i =max;i>min-1;i--){
				Map<String,Object> temp = new HashMap<String, Object>();
				temp.put("id", String.valueOf(i)+"-"+String.valueOf(i+1));
				temp.put("mc", String.valueOf(i)+"-"+String.valueOf(i+1)+"学年");
				list.add(temp);
			}
		}
		return list;
	}
	@Override
	public List<Map<String,Object>> getBysQxFb(List<AdvancedParam> advancedList,String schoolYear){
		Map<String,Object> param = getParamByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		String stuSql = MapUtils.getString(param, "stusql");
		return bysQxDao.queryBysFb(schoolYear, stuSql,1,false);
	}
	@Override
	public List<Map<String,Object>> getBysQxSzFb(List<AdvancedParam> advancedList,String schoolYear){
		Map<String,Object> param = getParamByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		String stuSql = MapUtils.getString(param, "stusql");
		return bysQxDao.queryBysSzFb(schoolYear, stuSql,1,false);
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getLnBysQxfb(List<AdvancedParam> advancedList,String schoolYear){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> param = getParamByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		List<String> deptList = (List<String>) MapUtils.getObject(param, "deptlist");
		advancedList =  (List<AdvancedParam>) MapUtils.getObject(param, "advancedList");
		List<String> yearList = getListByYear(schoolYear, 5, false);
		for(String year : yearList){
			String stuSql = businessDao.getStuSql(deptList, advancedList);
			int scale = bysQxDao.queryCountFb(year, stuSql);
			List<Map<String,Object>> list = bysQxDao.queryBysFb(year, stuSql,scale,true); 
			for(Map<String,Object> temp : list){
				temp.put("code", year);
				temp.put("field", year);
			}
			result.addAll(list);
		}
		Map<String,Object> typeMap = shiftList(result,"name","field","id","code");
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", typeMap);
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getLnBysSzQxfb(List<AdvancedParam> advancedList,String schoolYear){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> param = getParamByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		List<String> deptList = (List<String>) MapUtils.getObject(param, "deptlist");
		advancedList =  (List<AdvancedParam>) MapUtils.getObject(param, "advancedList");
		List<String> yearList = getListByYear(schoolYear, 5, false);
		for(String year : yearList){
			String stuSql = businessDao.getStuSql(deptList, advancedList);
			int scale = bysQxDao.queryCountSzFb(year, stuSql);
			List<Map<String,Object>> list = bysQxDao.queryBysSzFb(year, stuSql,scale,true); 
			for(Map<String,Object> temp : list){
				temp.put("code", year);
				temp.put("field", year);
			}
			result.addAll(list);
		}
		Map<String,Object> typeMap = shiftList(result,"name","field","id","code");
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", typeMap);
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getLnReasonfb(List<AdvancedParam> advancedList,String schoolYear){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> param = getParamByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		List<String> deptList = (List<String>) MapUtils.getObject(param, "deptlist");
		advancedList =  (List<AdvancedParam>) MapUtils.getObject(param, "advancedList");
		List<String> yearList = getListByYear(schoolYear, 10, false);
		for(String year : yearList){
			String stuSql = businessDao.getStuSql(deptList, advancedList);
			List<Map<String,Object>> list = bysQxDao.queryBysWjyyy(year, stuSql); 
			for(Map<String,Object> temp : list){
				temp.put("code", year);
				temp.put("field", year);
			}
			result.addAll(list);
		}
		Map<String,Object> typeMap = shiftList(result,"name","field","id","code");
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", typeMap);
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getScaleByDept(List<AdvancedParam> advancedList,String schoolYear){
		Map<String,Object> param = getParamByParam(advancedList, schoolYear);
		schoolYear = MapUtils.getString(param, "schoolyear");
		int year = MapUtils.getIntValue(param, "year");
		List<String> deptList = (List<String>) MapUtils.getObject(param, "deptlist");
		advancedList =  (List<AdvancedParam>) MapUtils.getObject(param, "advancedList");
        List<Map<String,Object>> result = bysQxDao.queryCountFbByDept(schoolYear, deptList, advancedList);
		String deptid = null;
        if(result != null && !result.isEmpty()){
			deptid = MapUtils.getString(result.get(0), "code");
		}
        String name = businessService.getLevelNameById(deptid);
		Map<String,Object> typeMap = shiftList(result,"name","field","id","code");
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", typeMap);
		map.put("name", name);
		return map;
	}
	private Map<String,Object> getParamByParam(List<AdvancedParam> advancedList,String schoolYear){
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
	  map.put("advancedList", advancedList);
	  map.put("deptlist", getDeptDataList());
	  map.put("year", nian);
	  map.put("stusql", businessDao.getStuSql(getDeptDataList(), advancedList));
	  return map;
	}
	@Override
	public List<String> getListByYear(String schoolYear,int Length,Boolean asc){
		Integer min = businessDao.queryMinSchoolYear("t_stu_graduate", "school_year");
		if(min == null){
			return new ArrayList<String>();
		}
		List<String> result = new ArrayList<String>();
		int year = Integer.parseInt(schoolYear.substring(0,4));
		Length = (year-Length)<min ? min-1 : (year-Length);
		if (asc){
			for(int i = year;i>Length;i--){
				String y = String.valueOf(i)+"-"+String.valueOf(i+1);
				result.add(y);
			}
		}else{
			for(int i= Length+1;i<year+1;i++){
				String y = String.valueOf(i)+"-"+String.valueOf(i+1);
				result.add(y);
			}
		}
		return result;
	}
	@Override
	public Map<String,Object> shiftList(List<Map<String,Object>> result,String a,String b,String c,String d){//a是X轴数据,b是图例数据
		Map<String, Object> typeMap = new HashMap<>();
		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
		List<String> legend_ary = new ArrayList<>(),
					 value_ary  = new ArrayList<>();
		List<String> field_ary  = new ArrayList<>();
		for(Map<String, Object> map2 :result){
			String legend = MapUtils.getString(map2, a);
			String value = MapUtils.getString(map2, c);
			String field = MapUtils.getString(map2, b);
			if (!legend_ary.contains(legend)){
			legend_ary.add(legend);
			}
			if (!value_ary.contains(value)){
			value_ary.add(value);
			}
			if (!field_ary.contains(field)){
				field_ary.add(field);
				}
			}
		String name = null;
		for (String k : field_ary){
			Map<String, Object> map3 = new HashMap<>();
			map3.put("name", k);
			for (int l =0;l<result.size();l++){
				Map<String, Object> map4 = result.get(l);
				name = MapUtils.getString(map4, b);
		      if (name != null && name.toString().equals(k)){
		    	  map3.put(MapUtils.getString(map4, c), MapUtils.getInteger(map4, "value"));
		    	  map3.put("code", MapUtils.getString(map4, d));
		      }
			}
			list3.add(map3);
		}
		typeMap.put("legend_ary", legend_ary);
		typeMap.put("value_ary", value_ary);
		typeMap.put("data", list3);
		return typeMap;
	}
	@Override
	public Map<String,Object> getStuDetail(List<AdvancedParam> advancedParamList,Page page, 
			Map<String, Object> keyValue, List<String> fields){
		String sql = getStuDetailSql(advancedParamList, keyValue, fields);
		Map<String,Object> map =baseDao.createPageQueryInLowerKey(sql, page);
		return map;
	}
	@Override
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList, 
			Map<String, Object> keyValue, List<String> fields){
		String sql = getStuDetailSql(advancedParamList, keyValue, fields);
		List<Map<String,Object>> list =baseDao.queryListInLowerKey(sql);
		return list;
	}
	private String getStuDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields){
		List<String> deptList = getDeptDataList(); // 权限
		String schoolYear = "",bysSql="",str="";
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				switch (type) {
				case Type_School_Year:
					schoolYear =  code;
					Map<String,Object> map = getParamByParam(new ArrayList<AdvancedParam>(), schoolYear);
					schoolYear = MapUtils.getString(map, "schoolyear");
					break;
				case Type_Direction_Code:
					String path = bysQxDao.getPath(code);
					String xx = "select id from t_code_graduate_direction where path_ like '"+path+"%' ";
					str = "and  a.direction_id in ("+xx+") ";
					break;
				case Type_Reason_Code:
					str = "and a.unoccupied_code in ("+businessDao.formatInSql(code)+")";
					break;
				default:
					break;
				}
			}
		String stuSql = businessDao.getStuSql(deptList,stuAdvancedList);
		bysSql = bysQxDao.getBysSql(schoolYear, stuSql, true, null);
		stuSql = "select c.* from t_stu_graduate_direction a,("+bysSql+") b,("+stuSql+") c where "
				+ " a.stu_id = b.stu_id and b.stu_id = c.no_ "+str;
		String stuDetailSql = businessDao.getStuDetailSql(stuSql);
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") order by "+StringUtils.join(fields, ",")+"";
		}
		return stuDetailSql;

	}
}
