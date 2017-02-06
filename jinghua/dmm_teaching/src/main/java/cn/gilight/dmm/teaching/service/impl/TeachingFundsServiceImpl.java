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
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.dao.TeachingFundsDao;
import cn.gilight.dmm.teaching.service.TeachingFundsService;
import cn.gilight.framework.uitl.common.MapUtils;

@Service("teachingFundsService")
public class TeachingFundsServiceImpl implements TeachingFundsService {
	@Resource
	private TeachingFundsDao teachingFundsDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	private static final int YEAR = Constant.Year_His_Len_Half;
	private static final int YEAR1 = Constant.Year_His_Len;
	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"teachingFunds";

	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}

	@Override
	public Map<String, Object> getTeaFundsBySty(int year) {//获取权限下经费 单位已处理
		List<String> deptList = getDeptDataList();
		String deptId=null;
		boolean tag= PmsUtils.isAllPmsData(deptList);
		if(tag){
			deptId=businessService.getSchoolId();
		}else{
			deptId=PmsUtils.getDeptIdsByDeptList(deptList);
		}
		List<Map<String, Object>> list = teachingFundsDao.queryFundsBySty(year,deptId);
		Map<String, Object> map = DevUtils.MAP();
		map.put("funds", list);//数据类型已解决
		
		return map;
	}
	
	@Override
	public Map<String, Object> getTeaFundsByAll(int year) {//获取权限下经费总额 以及生均
		List<String> deptList = getDeptDataList();
		boolean tag= PmsUtils.isAllPmsData(deptList);
		String deptId=null;
		if(tag){
			deptId=businessService.getSchoolId();
		}else{
			deptId=PmsUtils.getDeptIdsByDeptList(deptList);
		}
		Map<String, Object> map1 = teachingFundsDao.queryFundsByAll(year,deptId);
		Map<String, Object> map = DevUtils.MAP();
		map.put("funds", map1);//总额与生均  
		return map;
	}
	
	@Override
	public Map<String, Object> getTeaFundsLine(int year) {
		List<String> deptList = getDeptDataList();
		boolean tag= PmsUtils.isAllPmsData(deptList);
		String deptId=null;
		if(tag){
			deptId=businessService.getSchoolId();
		}else{
			deptId=PmsUtils.getDeptIdsByDeptList(deptList);
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String ,Object>> Codelist=teachingFundsDao.queryFundsByCode();
		for(int i = (year - YEAR + 1); i < (year + 1); i++){
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for(int j=0;j<Codelist.size();j++){
			String code=(String) (Codelist.get(j).get("code"));
			Map<String, Object> map = teachingFundsDao.queryFundsByYear(i,code,deptId);
			map.put("field", String.valueOf(i));
			list.add(map);
		}
		result.addAll(list);
		}
		Map<String, Object> typeMap = shiftList(result,"name","field","id","field");
		Map<String, Object> map = DevUtils.MAP();
		map.put("line", typeMap);
		return map;//数据类型已解决
	}

	@Override
	public Map<String, Object> getTeaFundsByZB(int year) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<String> deptList = getDeptDataList();
		boolean tag= PmsUtils.isAllPmsData(deptList);
		String deptId=null;
		if(tag){
			deptId=businessService.getSchoolId();
		}else{
			deptId=PmsUtils.getDeptIdsByDeptList(deptList);
		}
		List<Map<String ,Object>> Codelist=teachingFundsDao.queryFundsByCode();
		for (int i = (year - YEAR1 + 1); i < (year + 1); i++) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			for(int j=0;j<Codelist.size();j++){
			String code=(String) (Codelist.get(j).get("code"));
			Map<String, Object> map = teachingFundsDao.queryFundsByYear(i,code,deptId);
			list.add(map);
			}
			double sum=0;
			for(Map<String, Object> funds2 : list){
				String str=(String) funds2.get("value");
				sum=sum+Double.parseDouble(str);
			}
			for (Map<String, Object> funds : list) {
				String str=(String) funds.get("value");
				double fy=Double.parseDouble(str);
				double value=0;
				if(fy!=0.0){
					value=fy*100/sum;
					funds.put("value", String.valueOf(String.format("%.2f", value)));//String.format("%.3f", value))
				}else{
					funds.put("value", String.valueOf(0));
				}
				funds.put("year", String.valueOf(i));
			}
			result.addAll(list);
			
			
		}
		Map<String, Object> typeMap = shiftList(result,"name","year","id","field");
		Map<String, Object> map = DevUtils.MAP();
		map.put("bar", typeMap);// 将数量转化为占比
		return map;
	}

	@Override
	public Map<String, Object> getTeaFundsByCount(int year) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<String> deptList = getDeptDataList();
		boolean tag= PmsUtils.isAllPmsData(deptList);
		String deptId=null;
		if(tag){
			deptId=businessService.getSchoolId();
		}else{
			deptId=PmsUtils.getDeptIdsByDeptList(deptList);
		}
		List<Map<String ,Object>> Codelist=teachingFundsDao.queryFundsByCode();
		for(int i = (year - YEAR1 + 1); i < (year + 1); i++){
			List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
		for(int j=0;j<Codelist.size();j++){
			String code=(String) (Codelist.get(j).get("code"));
			Map<String, Object> map = teachingFundsDao.queryFundsByYear(i,code,deptId);
			map.put("year", String.valueOf(i));
			list.add(map);
		}
		result.addAll(list);
		}
		Map<String, Object> typeMap = shiftList(result,"name","year","id","field");
		Map<String, Object> map = DevUtils.MAP();
		map.put("bar", typeMap);//其中name代表年份
		return map;// 得到历年经费数量   --数据类型已解决
	}

	@Override
	public Map<String, Object> getTeaFundsAvg(int year) {
		List<String> deptList = getDeptDataList();
		boolean tag= PmsUtils.isAllPmsData(deptList);
		List<Map<String, Object>> deptMapList=new ArrayList<Map<String,Object>>();
		String deptId=null;
		if(tag){
			deptId=businessService.getSchoolId();
			deptMapList =businessDao.queryYxList(deptList);//
		}else{
			deptId=PmsUtils.getDeptIdsByDeptList(deptList);
			deptMapList=businessDao.queryZyListStu(deptList);//避免学院下是系的问题，直接取出专业
		}
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<deptMapList.size();i++){
			String id=(String) (deptMapList.get(i).get("id"));
				Map<String, Object> map = teachingFundsDao.queryFundsAvg(year,id);
				map.put("deptName", deptMapList.get(i).get("name"));
				list.add(map);
			}
		Map<String, Object> map1 = teachingFundsDao.queryFundsByAll(year,deptId);
		Map<String, Object> map = DevUtils.MAP();
		compareCount(list,"avg",false);
		map.put("collegeAvg", list);
		map.put("schoolAvg", map1);
		String str= businessService.getLevelNameById(deptId);
		String str2=businessService.getLevelNameById(deptMapList.isEmpty() ? null : MapUtils.getString(deptMapList.get(0), "id"));
		map.put("school", str+"生均");
		map.put("college", str2+"生均");
		return map;
	}

	@Override
	public Map<String, Object> getTeaFundsByDept(int year) {
		List<String> deptList=getDeptDataList();
		boolean tag= PmsUtils.isAllPmsData(deptList);
		List<Map<String, Object>> deptMapList=new ArrayList<Map<String,Object>>();
		if(tag){
			deptMapList =businessDao.queryYxList(deptList);
		}else{
			deptMapList=businessDao.queryZyListStu(deptList);//避免学院下是系的问题，直接取出专业
		}
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Map<String ,Object>> Codelist=teachingFundsDao.queryFundsByCode();
		for(int i=0;i<deptMapList.size();i++){
			String Id=(String) (deptMapList.get(i).get("id"));
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			for(int j=0;j<Codelist.size();j++){
				String code=(String) (Codelist.get(j).get("code"));
				Map<String, Object> map = teachingFundsDao.queryFundsBycollege(year,code,Id);// [{name:本科日常运行支出,deptName:粮油食品学院,value:1534}]
				map.put("deptName", deptMapList.get(i).get("name"));
				list.add(map);
			}
			result.addAll(list);
		}
		compareCount(result,"id",true);
		Map<String, Object> typeMap = shiftList(result, "name","deptName","id","field");
		Map<String, Object> map = DevUtils.MAP();
		map.put("bar", typeMap);//其中name代表年份
		String deptMc = businessService.getLevelNameById(deptMapList.isEmpty() ? null : MapUtils.getString(deptMapList.get(0), "id"));
		map.put("title", deptMc);
		return map;
	}

	@Override
	public Map<String, Object> getTeaFundsYear() {
		List<Map<String, Object>> list = teachingFundsDao.queryFundsYear();
		Map<String, Object> map = DevUtils.MAP();
		map.put("year", list);
		return map;
	}
	
	public Map<String, Object> shiftList(List<Map<String, Object>> result, String a, String b, String c, String d) {// a是X轴数据,b是图例数据
		/**
		 * 此方法用来转换多图例的数据，result的数据格式是 [{name:'sb',field:'jb',value:'2b'},
		 * {name:'sb',field:'jb',value:'2b'}, {name:'sb',field:'jb',value:'2b'}]
		 * a指的是result里的哪个属性的值是前台要展示的图例， b指的是result里那个属性的值是前台要展示的X轴的数据
		 * c指的是result里个属性的值是前台要展示的图例对应的code
		 */
		Map<String, Object> typeMap = new HashMap<>();
		List<Map<String, Object>> list3 = new ArrayList<Map<String, Object>>();
		List<String> legend_ary = new ArrayList<>(), value_ary = new ArrayList<>();
		List<String> field_ary = new ArrayList<>();
		for (Map<String, Object> map2 : result) {
			String legend = MapUtils.getString(map2, a);
			String value = MapUtils.getString(map2, c);
			String field = MapUtils.getString(map2, b);
			if (!legend_ary.contains(legend)) {
				legend_ary.add(legend);
			}
			if (!value_ary.contains(value)) {
				value_ary.add(value);
			}
			if (!field_ary.contains(field)) {
				field_ary.add(field);
			}
		}
		String name = null;
		for (String k : field_ary) {
			Map<String, Object> map3 = new HashMap<>();
			map3.put("name", k);
			for (int l = 0; l < result.size(); l++) {
				Map<String, Object> map4 = result.get(l);
				name = MapUtils.getString(map4, b);
				if (name != null && name.toString().equals(k)) {
					map3.put(MapUtils.getString(map4, c), MapUtils.getString(map4, "value"));
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

	

}
