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

import com.jhnu.syspermiss.util.DateUtils;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.AgeUtils;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.xg.dao.StuEnrollDao;
import cn.gilight.dmm.xg.service.StuEnrollService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;

@Service("stuEnrollService")
public class StuEnrollServiceImpl implements StuEnrollService{
	@Resource
	private StuEnrollDao stuEnrollDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BaseDao basedao;
	/**
	 * 在籍学生基本概况页面 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"stuEnroll";
	private static final int YEAR = 5;
	private static final int YEAR1 = 10;
	private static final String Type_Campus = "campus";
	private static final String Type_Politics = "politics";
	private static final String Type_Edu = "edu";
	private static final String Type_Grade = "grade";
	private static final String Type_Age = "age";
	private static final String Type_Nation = "nation";
	private static final String Type_Nation_Bar = "nation_bar";
	private static final String Type_From = "from";
	private static final String Type_Politics_Bar = "politics_bar";
	private static final String Type_Status = "status";
	private static final String Type_Sex = "sex";
	private static final String Type_Form = "form";
	private static final String Type_Style = "style";
	private static final String Type_Gatq = "gatq";
	private static final String Type_Stu_Roll_Code = "STU_ROLL_CODE";
	private static final String Type_SchoolYear = "schoolyear";
	private static final String Type_Dept = "dept";
	private static final String Type_FromLine = "fromline";
	private static final String Type_Graduate = "graduate";
	private static final String Type_YjsStyle = "yjsStyle";
	private static final String Type_Direction = "direction";
	private static final String Type_Dxjy = "dxjy";
    /**
     * 变量 - 团员的标准编码
     */
    public static final String CODE_POLITICS_CODE_03 = "03";
    /**
     * 变量 - 未维护的代码
     */
    public static final String WWH_CODE = "wwh";
	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	private List<String> getDeptDataList(String id){
		return businessService.getDeptDataList(ShiroTag,id);
	}
	@Override
	public  Map<String,Object> getStuCountByDept(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		Map<String,Object> map = stuEnrollDao.queryStuCountByDept(schoolYear,deptList,AdvancedUtil.getPid(stuAdvancedList),true,stuAdvancedList);
		List<Map<String,Object>> campus = stuEnrollDao.queryStuCountIncampus(schoolYear,deptList,stuAdvancedList);
		List<Map<String,Object>> edu = stuEnrollDao.queryStuCountByEdu(schoolYear,deptList,stuAdvancedList);
		List<Map<String,Object>> grade = stuEnrollDao.queryStuCountByGrade(schoolYear,deptList,stuAdvancedList);
		map.put("name", businessService.queryDeptDataName(deptList, advancedParamList));
		map.put("schoolyear", schoolYear);
		map.put("campus", campus);
		map.put("edu", edu);
		map.put("grade", grade);
		return map;
	}
	@Override
	public Map<String,Object> getGraduateStuCount(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		AdvancedParam eduAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, Constant.Code_Edu_Id_Stu_10);
		AdvancedUtil.add(stuAdvancedList, eduAdp);
		List<AdvancedParam> advancedList = new ArrayList<AdvancedParam>();
		if(stuAdvancedList.size()>1){ 
		     AdvancedUtil.add(advancedList, eduAdp);
		}
		String sql = businessDao.getGraduateStuSql(schoolYear,deptList, stuAdvancedList);
	    Integer value = basedao.queryForCount(sql);
	    List<Map<String,Object>> style = stuEnrollDao.queryYjsCountByStyle(sql,schoolYear,deptList,advancedList);
	    List<Map<String,Object>> dxjy  = stuEnrollDao.queryYjsCountByDxjy(sql, schoolYear, deptList, advancedList);
	    List<Map<String,Object>> grade = stuEnrollDao.queryYjsGrade(sql, schoolYear, deptList, advancedList);
	    Map<String,Object> map = DevUtils.MAP();
		map.put("value", value==null?0:value);
		map.put("id", Constant.Code_Edu_Id_Stu_10);
		map.put("style", style);
		map.put("dxjy", dxjy);
		map.put("grade", grade);
	    return map;
	}
	@Override
	public Map<String,Object> getBsCount(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		AdvancedParam eduAdp = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, Constant.Code_Edu_Id_Stu_01);
		AdvancedUtil.add(stuAdvancedList, eduAdp);
		List<AdvancedParam> advancedList = new ArrayList<AdvancedParam>();
		if(stuAdvancedList.size()>1){ 
		     AdvancedUtil.add(advancedList, eduAdp);
		}
		String sql = businessDao.getGraduateStuSql(schoolYear,deptList, stuAdvancedList);
	    Integer value = basedao.queryForCount(sql);
	    List<Map<String,Object>> grade = stuEnrollDao.queryBsGrade(sql,schoolYear, deptList, advancedList);
	    Map<String,Object> map = DevUtils.MAP();
	    map.put("id", Constant.Code_Edu_Id_Stu_01);
		map.put("value", value==null?0:value);
		map.put("grade", grade);
	    return map;
	}
	
	@Override
	public  Map<String,Object> getStuCountBySex(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> list = stuEnrollDao.queryStuCountBySex(schoolYear,deptList,true,stuAdvancedList);
		List<Map<String,Object>> state = stuEnrollDao.queryStuCountByState(schoolYear,deptList,stuAdvancedList);
		List<Map<String,Object>> style = stuEnrollDao.queryStuCountByStyle(schoolYear,deptList,stuAdvancedList,false);
		List<Map<String,Object>> form = stuEnrollDao.queryStuCountByForm(schoolYear,deptList,stuAdvancedList,false);
		Map<String, Object> map = DevUtils.MAP();
		map.put("sex", list);
		map.put("state", state);
		map.put("style", style);
		map.put("form", form);
		return map;
	}
	@Override
	public  Map<String,Object> getStuCountByAge(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		String year = DateUtils.getNowYear();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>(); 
		int sb = stuEnrollDao.getMaxXz(schoolYear, deptList, stuAdvancedList);
		for(int z=schoolYear;z>(schoolYear-sb);z--){
		List<Map<String,Object>> list1 = AgeUtils.CalculateAge(stuEnrollDao.queryStuCountByAge(z,deptList,stuAdvancedList), year,"name","value");
		List<Map<String,Object>>list2 = AgeUtils.groupAge(list1,"age","count",Constant.STU_AGE_GROUP);
		for (Map<String,Object> temp :list2){
			temp.put("field", z);
		}
		list.addAll(list2);
		}
		Map<String, Object> typeMap = shiftList(list,"field","name","field","code");
		Map<String, Object> map = DevUtils.MAP();
		map.put("age", typeMap);
		return map;
	}
	private  Map<String,Object> getStuCountByNation(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> list = stuEnrollDao.queryStuCountByNation(schoolYear,deptList,stuAdvancedList,false);
		List<Map<String,Object>> nationList = new ArrayList<Map<String,Object>>(); 
		int han =MapUtils.getIntValue(list.get(0), "value");
		int shaoshu =MapUtils.getIntValue(list.get(1), "value");
		int wwh = MapUtils.getIntValue(list.get(list.size()-1), "value");
		int i=2;
		int x = list.size();
		if(wwh == 0){x = list.size()-1;};
		for (;i<x;i++){
			Map<String, Object> map1 =list.get(i);
			nationList.add(map1);
		}
		compareCount(nationList,"value",false);
		Map<String, Object> map = DevUtils.MAP();
		map.put("nation", nationList);
		map.put("han", han);
		map.put("shaoshu", shaoshu);
		map.put("wwh", wwh);
		return map;
	}
	@Override
	public  Map<String,Object> getStuCountLine(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		Map<String, Object> map = DevUtils.MAP();
		Map<String,Object> map1 = new HashMap<String,Object>();
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
		int x = 0,y=0;
		for(int i=(schoolYear-YEAR+1);i<(schoolYear+1);i++){
			 map1 = stuEnrollDao.queryStuCountByDept(i,deptList,AdvancedUtil.getPid(advancedParamList),false,stuAdvancedList);
			 map1.put("field", String.valueOf(i)+"-"+String.valueOf(i+1));
			 map1.put("code", i);
			 int count = MapUtils.getIntValue(map1, "value");
			 if(i == (schoolYear-YEAR+1) || count < x ){
				 x = count;
			 }
			 if(count > y){
				 y = count;
			 }
			 list1.add(map1);
		}
		int z = x-(y-x)*4;
//		Double value = MathUtils.getDivisionResult(x*9,10, 0);
//		Double y = Double.parseDouble(String.valueOf(value).substring(1));
		map.put("line", list1);
		map.put("min", z < 0 ? 0 :z);
		return map;
	}
	@Override
	public  Map<String,Object> getContrastByDept(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String, Object>> list = stuEnrollDao.queryContrastByDept(schoolYear, deptList, stuAdvancedList);
		List<Map<String, Object>> dept = new ArrayList<Map<String, Object>>(),
                                  edu = new ArrayList<Map<String,Object>>();
		String deptid = "";int i =0;
		for (Map<String, Object> temp : list){
			Map<String, Object> deptMap = new HashMap<String, Object>(),
					            eduMap = new HashMap<String, Object>();
			String id = MapUtils.getString(temp, "id"),
				  code= MapUtils.getString(temp, "code"),
				  field = MapUtils.getString(temp, "field"),
				  name = MapUtils.getString(temp, "name");
			if(i==0){
				deptid = code;
				i++;
			}
			deptMap.put("code", (code==null||code.equals(""))?WWH_CODE:code);deptMap.put("field", (field == null||field.equals(""))?Constant.CODE_Unknown_Name:field);
			eduMap.put("id", (id==null||id.equals(""))?"null":id);eduMap.put("name", (name== null||name.equals(""))?Constant.CODE_Unknown_Name:name);
			if(!dept.contains(deptMap)){
				dept.add(deptMap);
			}
			if(!edu.contains(eduMap)){
				edu.add(eduMap);
			}
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> temp1 : dept){
			String code = MapUtils.getString(temp1, "code");
			String field =  MapUtils.getString(temp1, "field");
			for(Map<String, Object> temp2:edu){
				String id = MapUtils.getString(temp2, "id");
				String name = MapUtils.getString(temp2, "name");
				Map<String,Object> backVal = new HashMap<String, Object>();
				backVal.put("id", id);backVal.put("code", code);backVal.put("field", field);
				backVal.put("name", name);backVal.put("value", 0);
				for(Map<String, Object> temp3 :list){
					String id_1 =MapUtils.getString(temp3, "id"); 
					String code_1 =MapUtils.getString(temp3, "code");
					id_1=(id_1 == null ||id_1.equals(""))?"null":id_1;
					code_1 = (code_1 == null || code_1.equals(""))?WWH_CODE:code_1;
					int value = MapUtils.getIntValue(temp3, "value");
					if(id_1.equals(id) && code_1.equals(code)){
						backVal.put("value", value);
					}
				}
				result.add(backVal);
			}
		}
		String deptname = businessService.getLevelNameById(deptid);
		Map<String, Object> typeMap = shiftList(result,"name","field","id","code");
		Map<String, Object> map = DevUtils.MAP();
		map.put("contrast", typeMap);
		map.put("name", deptname);
		return map;
	}
	@Override
	public  Map<String,Object> getStuFrom(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> list = stuEnrollDao.queryStuFrom(schoolYear,deptList,stuAdvancedList);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		for(int i = 3;i<list.size();i++){
			Map<String, Object> map1 = list.get(i);
		    result.add(map1);
		}
		int gat =MapUtils.getIntValue(list.get(0), "value");
		String gatcode = MapUtils.getString(list.get(0), "id");
		int q =MapUtils.getIntValue(list.get(1),"value");
		String qcode = MapUtils.getString(list.get(1), "id");
		String mapType = list.get(2)==null?"china":MapUtils.getString(list.get(2), "name");
		int max = 0;
		if(result.size()>1){
			max = MapUtils.getIntValue(result.get(1), "value");
		}else if (result.size()==1){
			max = MapUtils.getIntValue(result.get(0), "value");;
		}else{
			max = 100;
		}
		max = numShift(max);
		Map<String, Object> nation = getStuCountByNation(advancedParamList);
		Map<String, Object> map = DevUtils.MAP();
		map.put("from", result);
		map.put("gat",gat);
		map.put("gatcode",gatcode);
		map.put("qcode",qcode);
		map.put("q",q);
		map.put("max",max);
		map.put("mapType",mapType);
		map.putAll(nation);
		return map;
	}
	@Override
	public  Map<String,Object> getContrastByPolitics(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String, Object>> poli = new ArrayList<Map<String, Object>>(),
				                  edu = new ArrayList<Map<String,Object>>();
	    List<Map<String, Object>> list = stuEnrollDao.queryContrastByPolitics(schoolYear, deptList,stuAdvancedList);
		for (Map<String, Object> temp : list){
			Map<String, Object> poliMap = new HashMap<String, Object>(),
					            eduMap = new HashMap<String, Object>();
			String id = MapUtils.getString(temp, "id"),
				  code= MapUtils.getString(temp, "code"),
				  field = MapUtils.getString(temp, "field"),
				  name = MapUtils.getString(temp, "name");
			poliMap.put("code", (code==null||code.equals(""))?WWH_CODE:code);poliMap.put("field", (field == null||field.equals(""))?Constant.CODE_Unknown_Name:field);
			eduMap.put("id", (id==null||id.equals(""))?"null":id);eduMap.put("name", (name== null||name.equals(""))?Constant.CODE_Unknown_Name:name);
			if(!poli.contains(poliMap)){
				poli.add(poliMap);
			}
			if(!edu.contains(eduMap)){
				edu.add(eduMap);
			}
		}
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		int ty= 0;int fty = 0;int wwh =0;
		for(Map<String, Object> temp1 : poli){
			String code = MapUtils.getString(temp1, "code");
			String field =  MapUtils.getString(temp1, "field");
			int order_ = 0;
			for(Map<String, Object> temp2:edu){
				String id = MapUtils.getString(temp2, "id");
				String name = MapUtils.getString(temp2, "name");
				Map<String,Object> backVal = new HashMap<String, Object>();
				backVal.put("id", id);backVal.put("code", code);backVal.put("field", field);
				backVal.put("name", name);backVal.put("value", 0);
				int value_1 = 0;
				for(Map<String, Object> temp3 :list){
					String id_1 =MapUtils.getString(temp3, "id"); 
					String code_1 =MapUtils.getString(temp3, "code");
					id_1=(id_1 == null ||id_1.equals(""))?"null":id_1;
					code_1 = (code_1 == null || code_1.equals(""))?WWH_CODE:code_1;
					int value = MapUtils.getIntValue(temp3, "value");
					if(id_1.equals(id) && code_1.equals(code)){
						backVal.put("value", value);
						order_+= value;
						value_1 = value;
					}
				}
				if(code.equals(CODE_POLITICS_CODE_03)){
					ty += value_1;
				}else if(!code.equals(WWH_CODE)&&!code.equals(CODE_POLITICS_CODE_03)){
					fty += value_1;
					backVal.put("order", order_);
					result.add(backVal);
				}else if(code.equals(WWH_CODE)){
					wwh += value_1;
					backVal.put("order", order_);
					result.add(backVal);
				}
			}
		}
		compareCount(result, "order", true);
	    Map<String, Object> typeMap = shiftList(result,"name","field","id","code");
	    Map<String, Object> map2 = DevUtils.MAP();
		map2.put("politics", typeMap);
		map2.put("tuan", ty);
		map2.put("fei", fty);
		map2.put("wwh", wwh);
		return map2;
	}
	@Override
	public Map<String,Object> getStuFromLine(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> list2 = new ArrayList<Map<String, Object>>();
		for(int j =(schoolYear-YEAR+1);j<(schoolYear+1);j++){
			List<Map<String,Object>> list1 = stuEnrollDao.queryStuCountByBen(j,deptList,stuAdvancedList);
			for(Map<String,Object> xx:list1){
				xx.put("field", String.valueOf(j)+"-"+String.valueOf(j+1));
				xx.put("code", j);
			}
		    list2.addAll(list1);
		}
		Map<String, Object> typeMap = shiftList(list2,"name","field","id","code");
		Map<String, Object> map = DevUtils.MAP();
		map.put("line", typeMap);
		return map;
	}
	@Override
	public Map<String,Object> getStuNationLine(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> list2 = new ArrayList<Map<String, Object>>();
		int val = schoolYear-YEAR+1;
		for(int j =(schoolYear-YEAR+1);j<(schoolYear+1);j++){
			List<Map<String,Object>> list = stuEnrollDao.queryStuCountByNation(j,deptList,stuAdvancedList,true);
			List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
			list.get(0).put("code", "1");
			list.get(1).put("code", "2");
			int wwh = MapUtils.getIntValue(list.get(2),"value");
			list.get(2).put("code", "3");
			list1.add(list.get(2));
			if (wwh==0){
				val++;
			}
			list1.add(list.get(0));list1.add(list.get(1));
			for(Map<String,Object> xx:list1){
				xx.put("name", String.valueOf(j)+"-"+String.valueOf(j+1));
			}
		    list2.addAll(list1);
		}
		if(val==schoolYear+1){
		Iterator<Map<String, Object>> ite = list2.iterator();
		while(ite.hasNext()){
		Map<String, Object> m = ite.next();
		if("3".equals(MapUtils.getString(m, "value"))){
		ite.remove();
		}
		}
		}
		Map<String, Object> typeMap = shiftList(list2,"field","name","code","name");
		Map<String, Object> map = DevUtils.MAP();
		map.put("line", typeMap);
		return map;
	}
	@Override
	public Map<String,Object> getStuSexLine(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> list2 = new ArrayList<Map<String, Object>>();
		for(int j =(schoolYear-YEAR+1);j<(schoolYear+1);j++){
			List<Map<String,Object>> list = stuEnrollDao.queryStuCountBySex(j,deptList,false,stuAdvancedList);
			for(Map<String,Object> xx:list){
				xx.put("field", String.valueOf(j)+"-"+String.valueOf(j+1));
			}
		    list2.addAll(list);
		}
		Map<String, Object> typeMap = shiftList(list2,"name","field","code","field");
		Map<String, Object> map = DevUtils.MAP();
		map.put("line", typeMap);
		return map;
	}
	@Override
	public Map<String,Object> getStuAgeLine(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(int i=(schoolYear-YEAR+1);i<(schoolYear+1);i++){
			Map<String,Object> map = new HashMap<String,Object>();
			String sb = String.valueOf(i);
			List<Integer> list = AgeUtils.CalculateAge(stuEnrollDao.queryStuAvgAge(i,deptList,stuAdvancedList), sb);
			int db = 0;int jb = list.size();
			for(Integer mb:list){
				if(mb==null){
					continue;
				}
				db +=mb;
			}
			double eb=MathUtils.get2Point(MathUtils.getDivisionResult(db,jb,2));
			map.put("field",String.valueOf(i)+"-"+String.valueOf(i+1));
			map.put("value", eb);
			result.add(map);
		}
		Map<String, Object> map1 = DevUtils.MAP();
		map1.put("line", result);
		return map1;
	}
	@Override
	public Map<String,Object> getPoliticsLine(List<AdvancedParam> advancedParamList){
		List<String> deptList = getDeptDataList(); // 权限
		int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(int i=(schoolYear-YEAR+1);i<(schoolYear+1);i++){
			List<Map<String,Object>> list = stuEnrollDao.queryPoliticsLine(i, deptList,stuAdvancedList);
			for(Map<String,Object> mb:list){
				mb.put("field", String.valueOf(i)+"-"+String.valueOf(i+1));
			}
			result.addAll(list);
		}
		Map<String, Object> typeMap = shiftList(result,"name","field","code","field");
		Map<String, Object> map = DevUtils.MAP();
		map.put("line", typeMap);
		return map;
	}
@SuppressWarnings("unchecked")
@Override
public Map<String,Object> getDeptLine(List<AdvancedParam> advancedParamList){
	List<String> deptList = getDeptDataList(); // 权限
	int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
	List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList); // 高级查询-学生参数
	List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
	int j= 0;String id = "";
	for (int i=(schoolYear-YEAR1+1);i<(schoolYear+1);i++){
			String stu = businessDao.getStuSql(i, deptList, stuAdvancedList);
			Integer grade = AdvancedUtil.getStuGrade(stuAdvancedList);
			String deptSql = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), stu, false, true, false, false, i, grade);
	        List<Map<String,Object>> list = basedao.queryListInLowerKey(deptSql);
	        String field= String.valueOf(i)+"-"+String.valueOf(i+1);
	        for(Map<String,Object> temp_1 :list){
	        	String code = MapUtils.getString(temp_1, "next_dept_code");
	        	String name = MapUtils.getString(temp_1, "next_dept_name");
	        	int value = MapUtils.getIntValue(temp_1, "next_dept_count");
	        	if(j==0){
	        		id = code;
	        		j++;
	        	}
	        	temp_1.put("id", code);temp_1.put("name", name);temp_1.put("value", value);
	        	temp_1.put("field", field);	temp_1.put("code", i);
	        }
	        result.addAll(list);
	}
	String name = businessService.getLevelNameById(id);
	Map<String, Object> typeMap = shiftList(result,"name","field","id","code");
	Map<String, Object> map = DevUtils.MAP();
    Map<String,Boolean> temp = new HashMap<String,Boolean>();
    List<String> lege = (List<String>) typeMap.get("legend_ary");
    for(int i=5;i<lege.size();i++){
    	temp.put(lege.get(i), false);
    }
	map.put("line", typeMap);
	map.put("legend", temp);
	map.put("name", name);
	return map;
}
@Override
public  Map<String,Object> getStyleLine(List<AdvancedParam> advancedParamList){
	List<String> deptList = getDeptDataList(); // 权限
	List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
	int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	for (int i=(schoolYear-YEAR+1);i<(schoolYear+1);i++){
	List<Map<String,Object>> list = stuEnrollDao.queryStuCountByStyle(i,deptList,stuAdvancedList,true);
	for (Map<String, Object> sty :list){
		sty.put("field", String.valueOf(i)+"-"+String.valueOf(i+1));
	}
	result.addAll(list);
	}
	Map<String, Object> typeMap = shiftList(result,"name","field","code","field");
	Map<String, Object> map = DevUtils.MAP();
	map.put("line", typeMap);
	return map;
}
@Override
public  Map<String,Object> getFormLine(List<AdvancedParam> advancedParamList){
	List<String> deptList = getDeptDataList(); // 权限
	int schoolYear = EduUtils.getSchoolYear4(); // 查询学年
	List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	for (int i=(schoolYear-YEAR+1);i<(schoolYear+1);i++){
	List<Map<String,Object>> list = stuEnrollDao.queryStuCountByForm(i,deptList,stuAdvancedList,true);
	for (Map<String, Object> form :list){
		form.put("field", String.valueOf(i)+"-"+String.valueOf(i+1));
	}
	result.addAll(list);
	}
	Map<String, Object> typeMap = shiftList(result,"name","field","code","field");
	Map<String, Object> map = DevUtils.MAP();
	map.put("line", typeMap);
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
@Override
public Map<String,Object> shiftList(List<Map<String,Object>> result,String a,String b,String c,String d){//a是X轴数据,b是图例数据
	/*
	 * 此方法用来转换多图例的数据，result的数据格式是
	 * [{name:'sb',field:'jb',value:'2b'},
	 *  {name:'sb',field:'jb',value:'2b'},
	 *  {name:'sb',field:'jb',value:'2b'}]
	 *  a指的是result里的哪个属性的值是前台要展示的图例，
	 *  b指的是result里那个属性的值是前台要展示的X轴的数据
	 *  c指的是result里个属性的值是前台要展示的图例对应的code
	 */
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
/**
 * 数字转化为100的倍数
 * @param num
 */
private int numShift(int num){
	if(num<100){
		return 100;
	}
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
	Integer schoolyear = null;
	List<String> matchList = new ArrayList<>(),joinList = new ArrayList<>();
	List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
	Boolean isYjs = false;
	if(keyValue != null){
		for(Map.Entry<String, Object> entry : keyValue.entrySet()){
			AdvancedUtil.add(stuAdvancedList, getDetailAdp(entry.getKey(), String.valueOf(entry.getValue())));
			if(Type_SchoolYear.equals(entry.getKey())){
				schoolyear =  (Integer) entry.getValue();
			}
			if(Type_Graduate.equals(entry.getKey())){
				isYjs = true;
			}
		}
	}
	String stuSql = businessDao.getStuSql(schoolyear,deptList,stuAdvancedList);
	if (isYjs){
		stuSql = businessDao.getGraduateStuSql(schoolyear,deptList, stuAdvancedList);
	}
	stuSql = "select stu.* from ("+stuSql+") stu ";
	Map<String,Object> temp = stuEnrollDao.queryBzdm();
	if(keyValue != null){
		for(Map.Entry<String, Object> entry : keyValue.entrySet()){
			String type = entry.getKey(), code = String.valueOf(entry.getValue());
			if(code!=null&&!code.equals("")){
			switch (type) {
			case Type_Campus:
				joinList.add(" inner join t_code_campus_class campus on stu.class_id = campus.class_id " );
				matchList.add(" campus.campus_id = '"+code+"'");
				break;
			case Type_Politics:
				if(code.equals("1")){
					matchList.add(" stu.politics_code = '"+MapUtils.getString(temp, "tuanyuan")+"'");
					}else if(code.equals("2")){
						matchList.add(" stu.politics_code <> '"+MapUtils.getString(temp, "tuanyuan")+"'");	
					}else if(code.equals("3")){
						matchList.add(" stu.politics_code is null");
					}
				break;
			case Type_Dxjy:	
					matchList.add(" stu.GRADUATE_ENROLL_CATEGORY_CODE ='"+code+"'");
				break;
			case Type_YjsStyle:	
				matchList.add(" stu.GRADUATE_LEARNING_STYLE_CODE ='"+code+"'");
			    break;
			case Type_Direction:	
				matchList.add(" stu.GRADUATE_FOSTER_DIRECTION_CODE ='"+code+"'");
			    break;
			case Type_Nation:
				if(code.equals("1")){
				matchList.add(" stu.nation_code = '"+MapUtils.getString(temp, "hanzu")+"'");
				}else if(code.equals("2")){
					matchList.add(" stu.nation_code <> '"+MapUtils.getString(temp, "hanzu")+"'");	
				}else if(code.equals("3")){
					matchList.add(" stu.nation_code is null");
				}
				break;
			case Type_Nation_Bar:
				if(code.equals(MapUtils.getString(temp, WWH_CODE))){
					matchList.add(" stu.nation_code is null");	
				}else{
				matchList.add(" stu.nation_code = '"+code+"'");
				}
				break;
			case Type_Politics_Bar:
				if(code.equals(MapUtils.getString(temp, WWH_CODE))){
					matchList.add(" stu.politics_code is null");	
				}else{
				matchList.add(" stu.politics_code = '"+code+"'");
				}
				break;
			case Type_Gatq:
				matchList.add(" stu.gatq_code in "+code+"");
				break;
			case Type_Status:
				if(code.equals(MapUtils.getString(temp, "wwh"))){
					matchList.add(" stu.stu_state_code is null");
					}else{
					matchList.add(" stu.stu_state_code = '"+code+"'");	
					}
				break;
			case Type_Form:
				if(code.equals(MapUtils.getString(temp, "wwh"))){
				matchList.add(" stu.learning_form_code is null");
				}else{
				matchList.add(" stu.learning_form_code = '"+code+"'");	
				}
				break;
			case Type_Style: 
				if(code.equals(MapUtils.getString(temp, "wwh"))){
					matchList.add(" stu.learning_style_code is null");
					}else{
					matchList.add(" stu.learning_style_code = '"+code+"'");	
					}
				break;
			case Type_Stu_Roll_Code:
				if (!isYjs){
				    matchList.add(" stu.stu_roll_code = '"+Constant.CODE_STU_ROLL_CODE_1+"'");
				}
			case Type_Age:
				String[] ageAry = code.split(",");
				if(ageAry.length == 2){
					String startAge = ageAry[0], endAge = ageAry[1], date = DateUtils.getNowDate(), field = "stu.birthday";
					if("null".equals(startAge) && "null".equals(endAge)){
						matchList.add(" "+field+" is null");
					}else if("null".equals(startAge) && !"null".equals(endAge)){
						String[] birAry = AgeUtils.CalculateBirthday(0, Integer.valueOf(endAge), date);
						matchList.add(" "+field+" >= '"+birAry[0]+"'");
					}else if(!"null".equals(startAge) && "null".equals(endAge)){
						String[] birAry = AgeUtils.CalculateBirthday(Integer.valueOf(startAge), Integer.valueOf(startAge)+1, date);
						matchList.add(" "+field+" <= '"+birAry[1]+"'");
					}
					if(!"null".equals(startAge) && !"null".equals(endAge)){
						String[] birAry = AgeUtils.CalculateBirthday(Integer.valueOf(startAge), Integer.valueOf(endAge), date);
						matchList.add(" "+field+" >= '"+birAry[0]+"' and "+field+" <= '"+birAry[1]+"'");
					}
				}
				break;
			case Type_FromLine:
				if(code.equals("1")){
					matchList.add(" substr(stu.stu_origin_id,0,2)||'0000' = '"+MapUtils.getString(temp, "ben")+"'");
				}else if(code.equals("2")){
					matchList.add(" substr(stu.stu_origin_id,0,2)||'0000' <> '"+MapUtils.getString(temp, "ben")+"'");
				}else if(code.equals("3")){
					matchList.add(" stu.gatq_code in "+MapUtils.getString(temp, "gat")+"");
				}else if(code.equals("4")){
					matchList.add(" stu.gatq_code in "+MapUtils.getString(temp, "q")+"");
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
			code = AdvancedUtil.Stu_ENROLL_GRADE;
			group = AdvancedUtil.Group_Stu;
			break;
		case AdvancedUtil.Stu_ENROLL_GRADE:
			code = AdvancedUtil.Stu_ENROLL_GRADE;
			group = AdvancedUtil.Group_Stu;
			break;
		case Type_From: 
			code = AdvancedUtil.Stu_ORIGIN_ID;
			value = stuEnrollDao.queryTableData("t_code_admini_div", "id","name_",value);
			group = AdvancedUtil.Group_Business;
			break;
		case Type_Sex:
			group = AdvancedUtil.Group_Common;
			code  = AdvancedUtil.Common_SEX_CODE;
			break;
		case Type_Dept:
			group = AdvancedUtil.Group_Common;
			code  = AdvancedUtil.Common_DEPT_TEACH_ID;
			break;
		default:
			break;
		}
		adp = new AdvancedParam(group, code, value);
	}
	return adp;
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
}
