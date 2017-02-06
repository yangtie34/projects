package cn.gilight.dmm.xg.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.util.DateUtils;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.AgeUtils;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.NewStuDao;
import cn.gilight.dmm.xg.service.NewStuService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Service("newStuService")
public class NewStuServiceImpl implements NewStuService {
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private NewStuDao newStuDao;
	@Resource
	private BaseDao basedao;
	public static final int ISREGISTER = 1;//已报到
	public static final int NOTREGISTER = 0;//未报到
	private static final String ShiroTag = Constant.ShiroTag_Xg+"newStu";
	private static final String POOR_TABLE = "T_STU_POOR";//贫困生表
	private static final String LOAN_TABLE = "T_STU_LOAN";//助学贷款表
	private static final String JM_TABLE = "T_STU_JM";//学费减免表
	private static final String Type_Dept = "dept";
	private static final String Type_Edu = "edu";
	private static final String Type_Grade = "grade";
	private static final String Type_Loan = "loan";
	private static final String Type_Poor = "poor";
	private static final String Type_Jm = "jm";
	private static final String Type_Schoolyear = "schoolyear";
	private static final String Type_Hklx = "hklx";
	private static final String Type_Year = "year";
	private static final String Type_Register = "register";
	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	private List<String> getDeptDataList(String id){
		return businessService.getDeptDataList(ShiroTag,id);
	}
	@Override
	public Map<String, Object> getIsRegisterCount(List<AdvancedParam> advancedParamList) {
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		AdvancedParam gradeAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, String.valueOf(schoolYear));
		AdvancedUtil.add(stuAdvancedList, gradeAdp);
		List<Map<String, Object>> edu = newStuDao
				.queryEdu(schoolYear, deptList,stuAdvancedList);
		List<Map<String, Object>> list = newStuDao.queryIsRegisterCount(
				schoolYear, deptList,stuAdvancedList);
		List<Map<String, Object>> isRegister = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> notRegister = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> isResult = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> notResult = new ArrayList<Map<String, Object>>();
		int total = 0;
		int yes = 0;
		int no = 0;
		for (Map<String, Object> temp : list) {
			int register = MapUtils.getIntValue(temp, "register");
			int value = MapUtils.getIntValue(temp, "value");
			if (register == ISREGISTER) {
				isRegister.add(temp);
				yes += value;
			} else if (register == NOTREGISTER) {
				notRegister.add(temp);
				no += value;
			}
			total += value;
		}
		for(int i=0;i<edu.size();i++){
			Map<String, Object> map1 = edu.get(i);
			String eduid = MapUtils.getString(map1, "code");
			int value = MapUtils.getIntValue(map1, "value");
			for(int j=0;j<isRegister.size();j++){
				Map<String, Object> map2 = isRegister.get(j);
				int num1 = MapUtils.getIntValue(map2, "value");
				String id = MapUtils.getString(map2, "code");
				if(id!=null&& eduid!=null&&id.equals(eduid)){
					map2.put("lv",MathUtils.get2Point(MathUtils.getDivisionResult(num1, value, 4) * 100));
					isResult.add(map2);
				}
			}
            for(int k=0;k<notRegister.size();k++){
            	Map<String, Object> map3 = notRegister.get(k);
            	int num2 = MapUtils.getIntValue(map3, "value");
            	String idx = MapUtils.getString(map3, "code");
            	if(idx!=null&& eduid!=null&&idx.equals(eduid)){
					map3.put("lv",MathUtils.get2Point(MathUtils.getDivisionResult(num2, value, 4) * 100));
					notResult.add(map3);
				}
			}
            if (isResult.size()<i+1){
            	map1.put("register",ISREGISTER);
            	map1.put("lv",0);
            	isResult.add(map1);
            }
            if (notResult.size()<i+1){
            	if (map1.containsKey("register")){
            	    map1.remove("register");
            	}else{
            		map1.put("register", NOTREGISTER);
            	}
            	if (map1.containsKey("lv")){
            	}else{
            		map1.put("lv",0);
            	}
            	notResult.add(map1);
            }
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("isrete", MathUtils.get2Point(MathUtils.getDivisionResult(yes, total, 4) * 100));
		map.put("notrete",MathUtils.get2Point(MathUtils.getDivisionResult(no, total, 4) * 100));
		map.put("islist", isRegister);
		map.put("notlist", notRegister);
		map.put("ylist", isResult);
		map.put("nlist", notResult);
		map.put("yes", yes);
		map.put("no", no);
		map.put("year", schoolYear);
		return map;
	}
	@Override
	public Map<String,Object> getCountAndLv(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> list = newStuDao.queryCountAndLv(schoolYear, deptList, stuAdvancedList);
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	@Override
	public Map<String,Object> getDeptAvgLv(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年	
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String, Object>> result = newStuDao.queryDeptAvgLv(schoolYear, deptList, stuAdvancedList);
		int j=0;String levelType = null;
		if(result != null && !result.isEmpty()){
			levelType = MapUtils.getString(result.get(0), "code");
		}
		String name = businessService.getLevelNameById(levelType);
		Map<String, Object> map1 = DevUtils.MAP();
		map1.put("list", result);
		map1.put("name", name);
		return map1;
	}
	@Override
	public Map<String,Object> getPoorCount(List<AdvancedParam> advancedParamList,String year){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> result = newStuDao.queryCountByAny(schoolYear, deptList, POOR_TABLE,stuAdvancedList);
		List<Map<String,Object>> temp = new ArrayList<Map<String,Object>>();
		if(result != null && !result.isEmpty()){
			temp.addAll(result);
		}
		int min = 0;
		for(Map<String,Object> xx : result){
			int xxx = MapUtils.getIntValue(xx, "field");
			if(min == 0){
				min = xxx;
			}else if(xxx<min){
				min = xxx;
			}
		}
		for(int i=min;i<(schoolYear+1);i++){
			int count = 0;
			for(Map<String,Object> yy : result){
				int xxx = MapUtils.getIntValue(yy, "field");
			    if (xxx == i){
			    	count = 1;
			    }
			}
			if(count == 0){
				Map<String,Object> xxMap = new HashMap<String, Object>();
				xxMap.put("code", i);xxMap.put("field", i);xxMap.put("value", 0);
				result.add(xxMap);
			}
		}
		compareCount(result,"field",true);
		compareCount(temp,"field",false);
		int jb = 0;String year1="";
		for(Map<String,Object> sb:temp){
			 jb = MapUtils.getIntValue(sb, "value");
			year1 = sb.get("field").toString();
			if (jb>0){
				break;
			}
		}
		if(jb==0){
			year1=String.valueOf(schoolYear);	
		}
		int sYear = year==null?Integer.parseInt(year1):Integer.parseInt(year);
		List<AdvancedParam> AdvancedList1 = new ArrayList<AdvancedParam>();
		if (stuAdvancedList != null && !stuAdvancedList.isEmpty()){
		AdvancedList1.addAll(stuAdvancedList);
		}
		AdvancedParam gradeAdp1 = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, String.valueOf(sYear));
		AdvancedUtil.add(AdvancedList1, gradeAdp1);
		List<Map<String,Object>> result1 = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>>  list1 = newStuDao.queryAnyPie(sYear, deptList, POOR_TABLE,AdvancedList1);
		for(int i=1;i<list1.size();i++){
			Map<String,Object> temp1= list1.get(i);
			result1.add(temp1);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("list1", result1);
		map.put("result",list1.get(0));
		map.put("year", year1);
		map.put("value", jb);
		return map;
	}
	@Override
	public Map<String,Object> getLoanCount(List<AdvancedParam> advancedParamList,String year){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> result = newStuDao.queryCountByAny(schoolYear, deptList, POOR_TABLE,stuAdvancedList);
		List<Map<String,Object>> temp = new ArrayList<Map<String,Object>>();
		if(result != null && !result.isEmpty()){
			temp.addAll(result);
		}
		int min = 0;
		for(Map<String,Object> xx : result){
			int xxx = MapUtils.getIntValue(xx, "field");
			if(min == 0){
				min = xxx;
			}else if(xxx<min){
				min = xxx;
			}
		}
		for(int i=min;i<(schoolYear+1);i++){
			int count = 0;
			for(Map<String,Object> yy : result){
				int xxx = MapUtils.getIntValue(yy, "field");
			    if (xxx == i){
			    	count = 1;
			    }
			}
			if(count == 0){
				Map<String,Object> xxMap = new HashMap<String, Object>();
				xxMap.put("code", i);xxMap.put("field", i);xxMap.put("value", 0);
				result.add(xxMap);
			}
		}
		compareCount(result,"field",true);
		compareCount(temp,"field",false);
		int jb = 0;String year1="";
		for(Map<String,Object> sb:temp){
			 jb = MapUtils.getIntValue(sb, "value");
			year1 = sb.get("field").toString();
			if (jb>0){
				break;
			}
		}
		if(jb==0){
			year1=String.valueOf(schoolYear);	
		}
		int sYear = year==null?Integer.parseInt(year1):Integer.parseInt(year);
		List<AdvancedParam> AdvancedList1 = new ArrayList<AdvancedParam>();
		if (stuAdvancedList != null && !stuAdvancedList.isEmpty()){
		AdvancedList1.addAll(stuAdvancedList);
		}
		AdvancedParam gradeAdp1 = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, String.valueOf(sYear));
		AdvancedUtil.add(AdvancedList1, gradeAdp1);
		List<Map<String,Object>> result1 = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>>  list1 = newStuDao.queryAnyPie(sYear, deptList, LOAN_TABLE,AdvancedList1);
		for(int i=1;i<list1.size();i++){
			Map<String,Object> temp1= list1.get(i);
			result1.add(temp1);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("list1", result1);
		map.put("result",list1.get(0));
		map.put("year", year1);
		map.put("value", jb);
		return map;
	}
	@Override
	public Map<String,Object> getJmCount(List<AdvancedParam> advancedParamList,String year){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> result = newStuDao.queryCountByAny(schoolYear, deptList, POOR_TABLE,stuAdvancedList);
		List<Map<String,Object>> temp = new ArrayList<Map<String,Object>>();
		if(result != null && !result.isEmpty()){
			temp.addAll(result);
		}
		int min = 0;
		for(Map<String,Object> xx : result){
			int xxx = MapUtils.getIntValue(xx, "field");
			if(min == 0){
				min = xxx;
			}else if(xxx<min){
				min = xxx;
			}
		}
		for(int i=min;i<(schoolYear+1);i++){
			int count = 0;
			for(Map<String,Object> yy : result){
				int xxx = MapUtils.getIntValue(yy, "field");
			    if (xxx == i){
			    	count = 1;
			    }
			}
			if(count == 0){
				Map<String,Object> xxMap = new HashMap<String, Object>();
				xxMap.put("code", i);xxMap.put("field", i);xxMap.put("value", 0);
				result.add(xxMap);
			}
		}
		compareCount(result,"field",true);
		compareCount(temp,"field",false);
		int jb = 0;String year1="";
		for(Map<String,Object> sb:temp){
			 jb = MapUtils.getIntValue(sb, "value");
			year1 = sb.get("field").toString();
			if (jb>0){
				break;
			}
		}
		if(jb==0){
			year1=String.valueOf(schoolYear);	
		}
		int sYear = year==null?Integer.parseInt(year1):Integer.parseInt(year);
		List<AdvancedParam> AdvancedList1 = new ArrayList<AdvancedParam>();
		if (stuAdvancedList != null && !stuAdvancedList.isEmpty()){
		AdvancedList1.addAll(stuAdvancedList);
		}
		AdvancedParam gradeAdp1 = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_ENROLL_GRADE, String.valueOf(sYear));
		AdvancedUtil.add(AdvancedList1, gradeAdp1);
		List<Map<String,Object>> result1 = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>>  list1 = newStuDao.queryAnyPie(sYear, deptList, JM_TABLE,AdvancedList1);
		for(int i=1;i<list1.size();i++){
			Map<String,Object> temp1= list1.get(i);
			result1.add(temp1);
		}
		Map<String,Object> map = DevUtils.MAP();
		map.put("list", result);
		map.put("list1", result1);
		map.put("result",list1.get(0));
		map.put("year", year1);
		map.put("value", jb);
		return map;
	}
	@Override
	public Map<String,Object> getStuDetail(List<AdvancedParam> advancedParamList,Page page, 
			Map<String, Object> keyValue, List<String> fields){
		String sql = getStuDetailSql(advancedParamList, keyValue, fields);
		Map<String,Object> map =basedao.createPageQueryInLowerKey(sql, page);
		return map;
	}
	@Override
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList, 
			Map<String, Object> keyValue, List<String> fields){
		String sql = getStuDetailSql(advancedParamList, keyValue, fields);
		List<Map<String,Object>> list =basedao.queryListInLowerKey(sql);
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
	private String getStuDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields){
		List<String> deptList = getDeptDataList(); // 权限
		Integer schoolyear = null;
		List<String> matchList = new ArrayList<>(),joinList = new ArrayList<>();
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		if(keyValue != null){
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				AdvancedUtil.add(stuAdvancedList, getDetailAdp(entry.getKey(), String.valueOf(entry.getValue())));
				if(Type_Schoolyear.equals(entry.getKey())){
					schoolyear =  Integer.valueOf(entry.getValue().toString());
					if(schoolyear == 1){
						schoolyear = EduUtils.getSchoolYear4();	
					}
				}
			}
		}
		String stuSql = newStuDao.queryStuSql(schoolyear,deptList,stuAdvancedList);
		stuSql = "select stu.* from ("+stuSql+") stu ";
		if(keyValue != null){
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				if(code!=null&&!code.equals("")){
				switch (type) {
				case Type_Loan:
					joinList.add(" inner join t_stu_loan t on stu.no_ = t.stu_id ");
					break;
				case Type_Register:
					matchList.add(" stu.isregister = '"+code+"'");
					break;
				case Type_Poor:
					joinList.add(" inner join t_stu_poor t on stu.no_ = t.stu_id ");
					break;
				case Type_Jm:
					joinList.add(" inner join t_stu_jm t on stu.no_ = t.stu_id ");
					break;
				case Type_Year:
					matchList.add( " substr(t.school_year,0,4) = '"+code+"'");
					break;
				case Type_Hklx:
					if(code.equals("wwh")){
						matchList.add( " stu.ANMELDEN_CODE is null");	
					}else{
					matchList.add( " stu.ANMELDEN_CODE = '"+code+"'");
					}
					break;
				default:
					break;
				}
				}
			}
			if(!matchList.isEmpty()&&!joinList.isEmpty()){
				stuSql = stuSql + (""+StringUtils.join(joinList, ""))+(" where "+StringUtils.join(matchList, " and "));
			}else if(!matchList.isEmpty()&&joinList.isEmpty()){
				stuSql = stuSql + (" where "+StringUtils.join(matchList, " and "));
			}
		}
		String stuDetailSql = businessDao.getStuDetailSql(stuSql);
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") order by "+StringUtils.join(fields, ",")+"";
		}
		return stuDetailSql;

	}
	private AdvancedParam getDetailAdp(String type, String value){
		AdvancedParam adp = null;
		if(type != null){
			String group = AdvancedUtil.Group_Stu, code = null;
			switch (type) {
			case Type_Edu: 
				group = AdvancedUtil.Group_Common;
				code = AdvancedUtil.Stu_EDU_ID;
				break;
			case Type_Grade:
				code = AdvancedUtil.Stu_GRADE;
				group = AdvancedUtil.Group_Stu;
				break;
			case Type_Schoolyear:
				if(value.equals("1")){
					value = String.valueOf(EduUtils.getSchoolYear4());	
				}
				code = AdvancedUtil.Stu_ENROLL_GRADE;
				group = AdvancedUtil.Group_Stu;
				break;
			case AdvancedUtil.Stu_ENROLL_GRADE:
				for(int i = EduUtils.getSchoolYear4();i>EduUtils.getSchoolYear4()-5;i--){
					if(i==EduUtils.getSchoolYear4()){
						value  = String.valueOf(EduUtils.getSchoolYear4());
					}else{
						value = value+","+i;
					}
				}
				code = AdvancedUtil.Stu_ENROLL_GRADE;
				group = AdvancedUtil.Group_Stu;
				break;	
			case Type_Dept:
				group = AdvancedUtil.Group_Common;
				code  = AdvancedUtil.Common_DEPT_TEACH_ID;
				break;
			case Type_Year:
				code = AdvancedUtil.Stu_ENROLL_GRADE;
				group = AdvancedUtil.Group_Stu;
				break;	
			default:
				break;
			}
			adp = new AdvancedParam(group, code, value);
		}
		return adp;
	}
}
