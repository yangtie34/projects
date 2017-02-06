package cn.gilight.dmm.teaching.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.BysScoreDao;
import cn.gilight.dmm.teaching.service.BysScoreService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;

@Service("bysScoreService")
public class BysScoreServiceImpl implements BysScoreService {
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BysScoreDao bysScoreDao;
	@Resource
	private BaseDao baseDao;
	
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"bysScore";
	
	private static final String Type_Course = "course";
	private static final String Type_Term = "term";
	private static final Object[][] score_target = {{Constant.SCORE_AVG,"平均数"},{Constant.SCORE_MIDDLE,"中位数"},{Constant.SCORE_MODE,"众数"},
		{Constant.SCORE_FC,"方差"},{Constant.SCORE_BZC,"标准差"}};
	private static final Object[][] score_type = {{"gpa","绩点"},{"score","成绩"}};
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	@Override
	public Map<String,Object> getEduList(){
		List<String> deptList = getDeptDataList();
		List<Map<String,Object>> list = businessService.queryBzdmStuEducationList(deptList);
		if(list.size() > 1){
			list = list.subList(1,list.size());
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	@Override
	public Map<String,Object> getPeriodList(){
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", getPeriod());
		return map;
	}
	@Override
	public Map<String,Object> getDateList(){
		List<Map<String, Object>> periodList = getPeriod(),
				                  list = new ArrayList<Map<String,Object>>();
        Map<String,Object> temp  = new HashMap<String, Object>(),
        		           temp1 = new HashMap<String, Object>(),
        		           temp2 = new HashMap<String, Object>(),
        		           temp3 = new HashMap<String, Object>();
        temp.put("id", MapUtils.getString(periodList.get(0), "id"));
        temp.put("mc", "本届");
        temp1.put("id", MapUtils.getString(periodList.get(1), "id"));
        temp1.put("mc", "上届");
        temp2.put("id", getPeriodStr(periodList, 3));
        temp2.put("mc", "近三届");
        temp3.put("id", getPeriodStr(periodList, 5));
        temp3.put("mc", "近五届");
        list.add(temp);list.add(temp1);list.add(temp2);list.add(temp3);
    	Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getXzList(List<AdvancedParam> advancedParamList,String edu){
		List<String> deptList = getDeptDataList();
		List<Map<String,Object>> edulist = (List<Map<String, Object>>) getEduList().get("list");
		if(edu == null) {edu = MapUtils.getString(edulist.get(0), "id");}
		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		AdvancedParam eduAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
		AdvancedUtil.add(advancedParamList,eduAdp);
		List<AdvancedParam> stuAdvancedList   = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		String stu = businessDao.getStuSql(deptList, stuAdvancedList);
		String sql = "select t.length_schooling as id,count(0) as value from ("+stu+") t where t.length_schooling <= 5 group by t.length_schooling order by count(0) desc";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		String[] ary={"零","一","二","三","四","五"};
		int max = 0,maxid = 0;
		for(Map<String,Object> temp : list){
			int id = MapUtils.getIntValue(temp,"id");
			int value = MapUtils.getIntValue(temp,"value");
			String mc = ary[id]+"年制";
			temp.put("mc", mc);
			if(value > max ){max = value;maxid = id;}
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		map.put("max", maxid);
		return map;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getScoreLine(List<AdvancedParam> advancedParamList,String edu,String date,Integer xz,String scoreType,String target){
		Map<String,Object> param = getParamsByParams(advancedParamList, edu, null, date, xz);
		List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) param.get("stuAdvancedList");
		edu = MapUtils.getString(param, "edu");
		date = MapUtils.getString(param, "date");
		xz = MapUtils.getIntValue(param, "xz");
		List<String> deptList = getDeptDataList();
		String[] dateAry = date.split(",");
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for (int i=0;i<dateAry.length;i++){
			int period  = Integer.parseInt(dateAry[i]);
			List<Map<String,Object>> temp = bysScoreDao.queryStuScoreList(deptList, stuAdvancedList, xz, period,scoreType,target);
			result.addAll(temp);
		}
		if(dateAry.length > 1){
		List<Map<String,Object>> list = avgList(result);
		result.addAll(list);
		}
		Map<String, Object> typeMap = shiftList(result);
		Map<String, Object> map = DevUtils.MAP();
		map.put("list", typeMap);
		return map;
	}
 	private String getPeriodStr(List<Map<String, Object>> periodList,int length){
		String str = "";
		for (int i= 0;i<length;i++){
			String id = MapUtils.getString(periodList.get(i), "id");
			if (i==0){
				str = id;
			}else{
				str +=","+id;
			}
		}
		return str;
	}
	@Override
	public Map<String,Object> getScoreTest(Map<String,Object> keyValue,List<AdvancedParam> advancedParamList){
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>(),
				                 list = getDateAry();
		for(int i= list.size()-1;i>-1;i--){
			Map<String,Object> year = list.get(i);
		String[] id = MapUtils.getString(year, "id").split(",");
		String mc = MapUtils.getString(year, "mc");
		keyValue.put("SCHOOL_YEAR", id[0]);
		keyValue.put("term", id[1]);
		keyValue.put("name", mc);
		String sql = getScoreSql(keyValue,getDeptDataList(),advancedParamList);
		Map<String,Object> temp = baseDao.queryMapInLowerKey(sql);
		result.add(temp);
		}
		Map<String,Object> map= DevUtils.MAP();
		map.put("list", result);
		return map;
	}
	@Override
	public Map<String,Object> getCourseList(){
		List<Map<String,Object>> list = bysScoreDao.queryCourse();
		Map<String,Object> map= DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> getScoreFb(List<AdvancedParam> advancedParamList,Integer period,Integer xz,String edu){
		Map<String,Object> param = getParamsByParams(advancedParamList, edu, period, null, xz);
        List<AdvancedParam> stuAdvancedList = (List<AdvancedParam>) param.get("stuAdvancedList");
        List<String> deptList = getDeptDataList();
        period = MapUtils.getIntValue(param, "period");
        xz =MapUtils.getIntValue(param, "xz");
        List<Map<String,Object>> list = bysScoreDao.queryScoreFb(stuAdvancedList, deptList, period, xz);
        Map<String, Object> typeMap = shiftList(list);
        Map<String,Object> map = DevUtils.MAP();
        map.put("list", typeMap);
		return map;
	}
	@Override
	public List<Map<String,Object>> getScoreGroup(){
		List<Map<String,Object>> list = Constant.getScoreGroup();
		compareCount(list, "order", true);
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
				int count1 = MapUtils.getIntValue(o1, compareField), count2 = MapUtils
						.getIntValue(o2, compareField); int flag = 0, pare = asc
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
	@Override
	public Map<String,Object> getTargetList(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Object[] obj : score_target){
			Map<String,Object> temp = new HashMap<String, Object>();
			temp.put("id", obj[0]);
			temp.put("mc", obj[1]);
			list.add(temp);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	@Override
	public Map<String,Object> getScoreTypeList(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(Object[] obj : score_type){
			Map<String,Object> temp = new HashMap<String, Object>();
			temp.put("id", obj[0]);
			temp.put("mc", obj[1]);
			list.add(temp);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	private List<Map<String,Object>> getPeriod(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//		String[] timeAry = businessDao.queryMaxSchoolYearTermCode(Constant.TABLE_T_STU_SCORE, "school_year", "term_code");
//		String year = timeAry[0];
		String[] ary = EduUtils.getSchoolYearTerm(DateUtils.getNowDate());
		int period = Integer.parseInt(ary[0].substring(0,4))+1;
		if(ary[1].equals(Globals.TERM_FIRST)){
			period = period-1;
		}
		for(int i = period;i>period-6;i--){
			Map<String,Object> temp = new HashMap<String, Object>();
			temp.put("id", i);
			temp.put("mc", String.valueOf(i)+"届");
			list.add(temp);
		}
		return list;
	}
	private String getScoreSql(Map<String,Object> keyValue,List<String> deptList,List<AdvancedParam> advancedParamList){
		String name  = "";
		List<String> matchList = new ArrayList<>(),joinList = new ArrayList<>();
		for(Map.Entry<String, Object> entry : keyValue.entrySet()){
			String type  = entry.getKey(); String value = (String) entry.getValue(); 
			if(type != null){
				switch (type) {
				case Type_Course:
					matchList.add(" t.course_id = '"+value+"'");
					break;
				case AdvancedUtil.Common_SCHOOL_YEAR:
					matchList.add(" t.school_year = '"+value+"'");
					break;
				case Type_Term:
					matchList.add(" t.term_code  = '"+value+"'");
					break;
				case "name": 
					name = value;
					break;
				default:
					break;
				}
			}
		}
		if(advancedParamList != null && advancedParamList instanceof List){
		for(AdvancedParam t : advancedParamList){
			String type1  = t.getCode(); String value1 = (String) t.getValues(); 
			if(type1 != null){
				switch (type1) {
				case AdvancedUtil.Common_SEX_CODE:
					matchList.add(" t.sex_code = '"+value1+"'");
					break;
				case AdvancedUtil.Common_DEPT_TEACH_ID:
					matchList.add(" t.dept_id = '"+value1+"'");
					break;
				case AdvancedUtil.Stu_ENROLL_GRADE:
					matchList.add(" t.enroll_grade = '"+value1+"' ");
					break;
				case AdvancedUtil.Stu_EDU_ID:
					matchList.add(" t.edu_id  = '"+value1+"'");
					break;
				default:
					break;
				}
			}
		}
		}
		String sql = " select '"+name+"' as name,"
				+ " sum(t.excellent) as y,sum(t.well) as l,sum(t.medium) as z,sum(t.pass) as j,"
				+ " sum(t.fail) as b from t_stu_score_test t "
				+(matchList.isEmpty()?"":(" where "+StringUtils.join(matchList, " and ")));
		return sql;
	}
	private List<Map<String,Object>> getDateAry(){
		List<Map<String,Object>>list = businessService.queryBzdmScoreXnXq();
		if(list.size()>8){
			list = list.subList(0,8);
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	private Map<String, Object> getParamsByParams(List<AdvancedParam> advancedParamList,String edu,Integer period,String date,Integer xz){
		List<Map<String,Object>> edulist = (List<Map<String, Object>>) getEduList().get("list"),
				                 periodList  = getPeriod();
		if(edu == null) {edu = MapUtils.getString(edulist.get(0), "id");}
 		advancedParamList = advancedParamList!= null ? advancedParamList : new ArrayList<AdvancedParam>();
		AdvancedParam eduAdp =  new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
		AdvancedUtil.add(advancedParamList,eduAdp);
		List<AdvancedParam> stuAdvancedList      = AdvancedUtil.getAdvancedParamStu(advancedParamList),
				businessAdvancedList = AdvancedUtil.getAdvancedParamBusiness(advancedParamList);
		if(period == null){period = MapUtils.getIntValue(periodList.get(0), "id");} 
		if(date == null){ date = MapUtils.getString(periodList.get(0), "id");}
		if(xz == null){ xz = MapUtils.getIntValue(getXzList(advancedParamList, edu), "max");}
		Map<String,Object> map = DevUtils.MAP();
		map.put("edu", edu);
		map.put("period", period);
		map.put("date", date);
		map.put("xz", xz);
		map.put("advancedParamList", advancedParamList);
		map.put("stuAdvancedList", stuAdvancedList);
		map.put("businessAdvancedList", businessAdvancedList);
		return map;
	}
	private Map<String,Object> shiftList(List<Map<String,Object>> result){
		Map<String, Object> typeMap = new HashMap<>();
		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
		List<String> legend_ary = new ArrayList<>(),
					 value_ary  = new ArrayList<>();
		List<String> field_ary  = new ArrayList<>();
		for(Map<String, Object> map2 :result){
			String legend = MapUtils.getString(map2, "name");
			String value = MapUtils.getString(map2, "name");
			String field = MapUtils.getString(map2, "field");
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
		String field = null;
		for (String k : field_ary){
			Map<String, Object> map3 = new HashMap<>();
			map3.put("name", k);
			for (int l =0;l<result.size();l++){
				Map<String, Object> map4 = result.get(l);
				field = MapUtils.getString(map4, "field");
		      if (field != null && field.equals(k)){
		    	  map3.put(MapUtils.getString(map4, "name"), MapUtils.getDoubleValue(map4, "value"));
//		    	  map3.put("code", MapUtils.getString(map4, "id"));
		      }
			}
			list3.add(map3);
		}
		typeMap.put("legend_ary", legend_ary);
		typeMap.put("value_ary", value_ary);
		typeMap.put("data", list3);
		return typeMap;
	}
	private List<Map<String,Object>> avgList(List<Map<String,Object>> result){
		List<Map<String,Object>> list3 = new ArrayList<Map<String,Object>>();
		List<String> field_ary  = new ArrayList<>();
		for(Map<String, Object> map2 :result){
			String field = MapUtils.getString(map2, "field");
			if (!field_ary.contains(field)){
				field_ary.add(field);
				}
			}
		String field = null;
		for (String k : field_ary){
			Map<String, Object> map3 = new HashMap<>();
			map3.put("field", k);map3.put("name", "均值");
			Double sss = 0.0;Double value = 0.0;
			for (int l =0;l<result.size();l++){
				Map<String, Object> map4 = result.get(l);
				field = MapUtils.getString(map4, "field");
				if (field != null && field.equals(k)){
//					map3.put("id", MapUtils.getString(map4, "id"));
					Double xxx = MapUtils.getDoubleValue(map4, "value");
		    	    value += xxx;
		    	    if (xxx!= 0d){
		    	      sss++;
		    	    }
		      }
			}
			map3.put("value", MathUtils.get2Point(MathUtils.getDivisionResult(value, sss, 2)));
			list3.add(map3);
		}
		return list3;
	}
}
