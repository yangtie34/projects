package cn.gilight.dmm.xg.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.StuEnrollDao;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;
/**
 * 在籍生基本概况数据查询
 * 
 * @author hanpl
 * @date 2016年5月10日 上午10:08:24
 */
@Repository("stuEnrollDao")
public class StuEnrollDaoImpl implements StuEnrollDao{
	@Resource
	private BaseDao basedao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	public final static String STUROLLSQL = " stu.stu_roll_code = '"+Constant.CODE_STU_ROLL_CODE_1+"' ";
	/**
	 * 变量-某地区学生人数占比
	 */
    public static final int AREA_PERCENT = 100;
    /**
	 * 字符串-港澳台学生编码类别
	 */
    public static final String  STU_GATQ_CODE = " ('01','03','05') ";//港澳台学生在中国上学
    /**
     * 标准代码-汉族编码
     */
    public static final String HANZU_CODE = "01";
    /**
  	 * 字符串-港澳台学生编码类别
  	 */
    public static final String STU_ABROAD_CODE = "('31','41','51')";	//非中国国籍在中国上学
    /**
     * 变量 - 本省的行政区划代码
     */
    public static final String PROVINCE_CODE = Constant.Code_Origin_Id_BenSheng;
    /**
     * 变量 - 团员的标准编码
     */
    public static final String CODE_POLITICS_CODE_03 = "03";
    /**
     * 变量 - 外省的命名
     */
    public static final String OTHER_PROVINCE_NAME = "外省";
    /**
     * 变量 - 港澳台地区的命名
     */
    public static final String GAT_NAME = "港澳台";
    /**
     * 变量 - 留学生的命名
     */
    public static final String ABROAD_NAME = "留学生";
    /**
     * 变量 - 港澳台行政区划id
     */
    public static final String GAT_ID = "('710000','810000','820000')";
    /**
     * 变量 - 未维护的命名
     */
    public static final String WWH_NAME = Constant.CODE_Unknown_Name;
    /**
     * 变量 - 未维护的代码
     */
    public static final String WWH_CODE = "wwh";
@Override	
public Map<String,Object> queryStuCountByDept(int schoolYear, List<String> deptList, String Id,Boolean bs,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.根据传递过来的组织结构id，查询出单位的名称，和单位下的学生。
	 * 2.当在教学组织机构中查找不到单位的名称，则在班级表中查询。
	 * 3.bs是为了标示出是否加上学籍状态判断
	 */
	 String str = getStuTableSql(schoolYear, deptList, stuAdvancedList);
	 String  sql = "select count(0) as value from ("+str+")stu "
			    + " where "+STUROLLSQL+"";//查询对应单位下的学生人数
	 if (bs){
		
	 }else{
		 sql = "select count(0) as value from ("+str+") stu ";
	 }
	 Map<String ,Object> list = basedao.queryMapInLowerKey(sql);
	return list;
}
@Override	
public List<Map<String,Object>> queryStuCountIncampus(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.查询出所选单位在各个校区都有哪些班级。
	 * 2.根据学生信息表中的班级id对应上一步查出的班级查出学生都在哪个校区。
	 * 3.下钻到某些单位时，校区数据可能为0，前台也要显示为0，不能不现实
	 */
	String sql = "",stu=getStuTableSql(schoolYear, deptList, stuAdvancedList);
	if (stuAdvancedList == null || stuAdvancedList.isEmpty()){
		sql = "select CAMPUS1.id,CAMPUS1.NAME_ as name,CAMPUS1.ORDER_,count(0) as value from ("+stu+") stu left join T_CODE_CAMPUS_CLASS class on stu.class_id = "
				+ " class.class_id left join T_CODE_CAMPUS CAMPUS1 on class.CAMPUS_id = "
				+ " CAMPUS1.id where campus1.istrue = 1 and "+ STUROLLSQL+" group by CAMPUS1.id,CAMPUS1.NAME_,CAMPUS1.ORDER_ order by CAMPUS1.ORDER_,CAMPUS1.id";
	}else{
		String str = getStuTableSql(schoolYear, deptList, new ArrayList<AdvancedParam>());
		sql = "select a.id,a.name,(a.value+nvl(b.value,0)) as value from (select CAMPUS1.id,CAMPUS1.NAME_ as name,CAMPUS1.ORDER_,0 as value from ("+str+") stu inner join T_CODE_CAMPUS_CLASS class on stu.class_id = "
				+ " class.class_id inner join T_CODE_CAMPUS CAMPUS1 on class.CAMPUS_id = "
				+ " CAMPUS1.id where campus1.istrue = 1 and "+ STUROLLSQL+" group by CAMPUS1.id,CAMPUS1.NAME_,CAMPUS1.ORDER_ order by CAMPUS1.ORDER_,CAMPUS1.id) a left join "
				+ "(select CAMPUS1.id,CAMPUS1.NAME_ as name,CAMPUS1.ORDER_,count(0) as value from ("+stu+") stu left join T_CODE_CAMPUS_CLASS class on stu.class_id = "
				+ " class.class_id left join T_CODE_CAMPUS CAMPUS1 on class.CAMPUS_id = "
				+ " CAMPUS1.id where campus1.istrue = 1 and "+ STUROLLSQL+" group by CAMPUS1.id,CAMPUS1.NAME_,CAMPUS1.ORDER_) b on a.id =b.id ";
	}
	return basedao.queryListInLowerKey(sql);
}
@Override
public List<Map<String,Object>> queryYjsCountByStyle(String stuSql,int schoolYear,List<String>deptList,List<AdvancedParam> stuAdvancedList){
	String codeSql = "select * from t_code where code_type = 'GRADUATE_FOSTER_DIRECTION_CODE'";
	String styleSql = "select * from t_code where code_type = 'GRADUATE_LEARNING_STYLE_CODE'";
	String sql = "";
	if (stuAdvancedList == null || stuAdvancedList.isEmpty()){
		sql = "select style.code_||','||code.code_ as id,style.name_||code.name_ as mc,count(0) as value from ("+stuSql+") stu,("+codeSql+") code,("+styleSql+") style"
				+ " where stu.GRADUATE_FOSTER_DIRECTION_CODE = code.code_ and stu.graduate_learning_style_code = style.code_ group by "
				+ " style.code_,code.code_,style.name_,code.name_ order by style.code_,code.code_";
	}else{
		String str = businessDao.getGraduateStuSql(schoolYear, deptList, stuAdvancedList);
		sql = " select a.id,a.mc,(a.value+nvl(b.value,0)) as value from "
			+ " (select style.code_||','||code.code_ as id,style.name_||code.name_ as mc,0 as value from ("+str+") stu,("+codeSql+") code,("+styleSql+") style"
			+ " where stu.GRADUATE_FOSTER_DIRECTION_CODE = code.code_ and stu.graduate_learning_style_code = style.code_ group by "
			+ " style.code_,code.code_,style.name_,code.name_ order by style.code_,code.code_) a left join ("
			+ " select style.code_||','||code.code_ as id,style.name_||code.name_ as mc,count(0) as value from ("+stuSql+") stu,("+codeSql+") code,("+styleSql+") style"
			+ " where stu.GRADUATE_FOSTER_DIRECTION_CODE = code.code_ and stu.graduate_learning_style_code = style.code_ group by "
			+ " style.code_,code.code_,style.name_,code.name_ ) b on a.id =b.id";

	}
	return basedao.queryListInLowerKey(sql);
}
@Override
public List<Map<String,Object>> queryYjsCountByDxjy(String stuSql,int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	String codeSql = "select * from t_code where code_type = 'GRADUATE_ENROLL_CATEGORY_CODE'";
	String sql = "";
	if (stuAdvancedList == null || stuAdvancedList.isEmpty()){
		sql = "select code.code_ as id,code.name_ as mc,count(0) as value from ("+stuSql+") stu,("+codeSql+") code "
			+ " where stu.GRADUATE_ENROLL_CATEGORY_CODE = code.code_ group by "
			+ " code.code_,code.name_ order by code.code_";
	}else{
		String str = businessDao.getGraduateStuSql(schoolYear, deptList, stuAdvancedList);
		sql = " select a.id,a.mc,(a.value+nvl(b.value,0)) as value from "
			+ " ( select code.code_ as id,code.name_ as mc,0 as value from ("+str+") stu,("+codeSql+") code "
			+ " where stu.GRADUATE_ENROLL_CATEGORY_CODE = code.code_ group by "
			+ " code.code_,code.name_ order by code.code_) a left join ("
			+ " select code.code_ as id,code.name_ as mc,count(0) as value from ("+stuSql+") stu,("+codeSql+") code "
			+ " where stu.GRADUATE_ENROLL_CATEGORY_CODE = code.code_ group by "
			+ " code.code_,code.name_ ) b on a.id =b.id ";

	}
	return basedao.queryListInLowerKey(sql);
}
@Override
public List<Map<String,Object>> queryYjsGrade(String stuSql,int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	String sql = "";
	if (stuAdvancedList == null || stuAdvancedList.isEmpty()){
		sql = "select stu.enroll_grade as id,("+schoolYear+"-stu.enroll_grade) as mc,count(0) as value "
			+ " from ("+stuSql+") stu group by stu.enroll_grade order by stu.enroll_grade desc";
	}else{
		String str = businessDao.getGraduateStuSql(schoolYear, deptList, stuAdvancedList);
		sql = " select a.id,a.mc,(a.value+nvl(b.value,0)) as value from "
			+ " ( select stu.enroll_grade as id,("+schoolYear+"-stu.enroll_grade) as mc,0 as value "
			+ " from ("+str+") stu group by stu.enroll_grade order by stu.enroll_grade desc ) a left join ("
			+ " select stu.enroll_grade as id,("+schoolYear+"-stu.enroll_grade) as mc,count(0) as value "
			+ " from ("+stuSql+") stu group by stu.enroll_grade order by stu.enroll_grade desc ) b on a.id =b.id ";

	}
	List<Map<String,Object>> list =  basedao.queryListInLowerKey(sql);
	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	String[] ary = {"研一","研二","研三"};
	for(Map<String,Object> map: list){
		int mc = MapUtils.getIntValue(map, "mc");
		if (mc <3){
			map.put("mc", ary[mc]);
			result.add(map);
		}
	}
	return result;
}
@Override
public List<Map<String,Object>> queryBsGrade(String stuSql,int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	String sql = "";
	if (stuAdvancedList == null || stuAdvancedList.isEmpty()){
		sql = "select stu.enroll_grade as id,("+schoolYear+"-stu.enroll_grade) as mc,count(0) as value "
			+ " from ("+stuSql+") stu group by stu.enroll_grade order by stu.enroll_grade desc";
	}else{
		String str = businessDao.getGraduateStuSql(schoolYear, deptList, stuAdvancedList);
		sql = " select a.id,a.mc,(a.value+nvl(b.value,0)) as value from "
			+ " ( select stu.enroll_grade as id,("+schoolYear+"-stu.enroll_grade) as mc,0 as value "
			+ " from ("+str+") stu group by stu.enroll_grade order by stu.enroll_grade desc ) a left join ("
			+ " select stu.enroll_grade as id,("+schoolYear+"-stu.enroll_grade) as mc,count(0) as value "
			+ " from ("+stuSql+") stu group by stu.enroll_grade order by stu.enroll_grade desc ) b on a.id =b.id ";

	}
	List<Map<String,Object>> list =  basedao.queryListInLowerKey(sql);
	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	String[] ary = {"博一","博二","博三","博四"};
	for(Map<String,Object> map: list){
		int mc = MapUtils.getIntValue(map, "mc");
		if (mc <4){
			map.put("mc", ary[mc]);
			result.add(map);
		}
	}
	return result;
}
@Override
public List<Map<String,Object>> queryStuCountByEdu(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.查出全校所有就读学历的学生作为数据补全。
	 * 2.根据传入的标准权限查出该机构下各学历的学生数。
	 * 3.从学历编码表获取的学生就读学历的名字，需要学生信息表的edu_id对应上
	 * 编码表的名字改为本科生，专科生，研究生等。
	 */
	String sql= "",stu= getStuTableSql(schoolYear, deptList, stuAdvancedList);
	String ss = " stu inner join t_code_education edu on stu.edu_id = edu.id where ";
	if(stuAdvancedList == null || stuAdvancedList.isEmpty()){
		sql = "select edu.id,edu.name_ as name,count(0) as value from ("+stu+") "+ss 
				+" " +STUROLLSQL+" group by edu.id,edu.name_ order by edu.id ";
	}else{
		String str = getStuTableSql(schoolYear, deptList,new ArrayList<AdvancedParam>());
		sql = "select a.id,a.name,(a.value+nvl(b.value,0)) as value from (select edu.id,edu.name_ as name,0 as value from ("+str+") "+ss 
				+" " +STUROLLSQL+" group by edu.id,edu.name_ order by edu.id ) a left join "
				+ " (select edu.id,edu.name_ as name,count(0) as value from ("+stu+") "+ss 
				+" " +STUROLLSQL+" group by edu.id,edu.name_ ) b on a.id =b.id";
	}
	return basedao.queryListInLowerKey(sql);
}
@Override
public  List<Map<String,Object>> queryStuCountByGrade(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.根据传入的标准权限查出该机构下各年级的学生数。
	 * 2.根据传入的当前学年来把年级转化汉字，用来补全查询结果。
	 * 注意事项：enroll_grade字段数据只要年份例如：2015
	 */
	String sql= "",stu =getStuTableSql(schoolYear, deptList, stuAdvancedList);
	if (stuAdvancedList == null || stuAdvancedList.isEmpty()){
		sql = "select stu.enroll_grade as id,count(0) as value "
		+ " from ("+stu+") stu where "+STUROLLSQL +" and stu.enroll_grade is not null"
		+ "  group by stu.enroll_grade order by stu.enroll_grade desc";
	}else{
		String str = getStuTableSql(schoolYear, deptList, new ArrayList<AdvancedParam>());
		sql = " select a.id,(a.value+nvl(b.value,0)) as value from (select stu.enroll_grade as id,"
		+ " 0 as value from ("+str+") stu where "+STUROLLSQL +" and stu.enroll_grade is not null "
		+ " group by stu.enroll_grade order by stu.enroll_grade desc) a left join "
		+ " (select stu.enroll_grade as id,count(0) as value "
		+ " from ("+stu+") stu where "+STUROLLSQL +" and stu.enroll_grade is not null group by stu.enroll_grade ) b on a.id = b.id  order by a.id desc";
	}
    List<Map<String ,Object>> list = basedao.queryListInLowerKey(sql);
	for(Map<String ,Object> map: list){
		int grade = MapUtils.getIntValue(map, "id");
		String name = EduUtils.getNjName(schoolYear, grade);
		map.put("name", name);
	}
	return list;
}
@Override 
public List<Map<String,Object>> queryStuCountBySex(int schoolYear, List<String> deptList,Boolean bs,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.根据传入的标准权限查出该机构下各性别的学生数。
	 * 2.性别关联t_code编码表
	 * 3.bs是为了标示出是否加上学籍状态判断
	 */
	String str =getStuTableSql(schoolYear, deptList, stuAdvancedList);
	String str1 = "(select * from t_code where code_type='SEX_CODE') sex";//查询出性别编码
	String str2 = "select nvl(sex.name_,'"+WWH_NAME+"') as name,nvl(sex.code_,'"+WWH_CODE+"') as code,count(0) as value  "
			+ " from ("+str+") stu  left join "+str1+" on stu.sex_code = sex.code_ " ;//基本查询
    String sql = str2+ " where " +STUROLLSQL +" group by sex.name_,sex.code_"
	        + " ";//查询条件和分组
    if (bs){
    	
    }else{
    	 sql = str2+" group by sex.name_,sex.code_" ;
    }
	return basedao.queryListInLowerKey(sql);
}
@Override
public  List<Map<String,Object>> queryStuCountByAge(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.根据传入的标准权限查出该机构下的学生的出生日期list。
	 */
	int year = EduUtils.getSchoolYear4();
	String str =getStuTableSql(year, deptList, stuAdvancedList);
	String sql = " select stu.birthday as name ,count(0) as value from ("+str+") stu where "+STUROLLSQL+" and "
			+ " stu.enroll_grade = "+schoolYear+" group by stu.birthday";
	return basedao.queryListInLowerKey(sql);
}
@Override
public  List<Map<String,Object>> queryStuCountByNation(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList,Boolean isOld){
	/*
	 * 1.根据传入的标准权限查出该机构下各民族的学生数。
	 * 2.汉族的标准代码尽量设置为'01',否则上边维护汉族的标准编码
	 */
	String str =getStuTableSql(schoolYear, deptList, stuAdvancedList);
	String str1 = "(select * from t_code where code_type='NATION_CODE') nation";//查询出民族的编码
       String str3 = "select field,value,code from (select nvl(nation.name_,'"+WWH_NAME+"') as field,count(0) as value,nvl(nation.code_,'"+WWH_CODE+"') as code from ("+str+") stu left join "+str1+" on stu.nation_code =nation.code_"
	    	+ "  where  ";
		String str2= " and "+STUROLLSQL+" group by nation.name_,nation.code_ ) group by field,value,code order by value desc";
		if(isOld){str2= " group by nation.name_,nation.code_ ) group by field,value,code order by value desc";};
		    String sql =str3+ "  nation.code_ <> '"+HANZU_CODE+"'"+str2;//查询出非汉族各族的人数
		    String sql1 =str3+ "  nation.code_ = '"+HANZU_CODE+"'"+str2;//查询出汉族的人数
		    String wwh = "select * from ("+str3+" 1 =1 "+str2+") a where a.field ='"+WWH_NAME+"'";
		    List<Map<String ,Object>> list =  basedao.queryListInLowerKey(sql);
		    List<Map<String ,Object>> list1 =  basedao.queryListInLowerKey(sql1);
		    Map<String,Object> list2 = basedao.queryMapInLowerKey(wwh);
			List<Map<String ,Object>> result = new ArrayList<Map<String,Object>>();
			Map<String ,Object> map = new HashMap<String,Object>();
			Map<String ,Object> map1 = new HashMap<String,Object>();
			map1.put("field", "汉族");map1.put("value", 0);//防止list1为null的时候list1.get(0)报错
			int j = 0;
			for(int i=0;i<list.size();i++){//根据list算出少数民族的总人数
				j+=MapUtils.getIntValue(list.get(i), "value");
			}
			if(list2==null||list2.isEmpty()){
				  list2 = new HashMap<String,Object>();
				  list2.put("field",WWH_NAME);
				  list2.put("value",0);
				}
			map.put("field", "少数民族");map.put("value", j);//把计算的少数民族人数放到map中
			result.add(0, list1.size()==0?map1:list1.get(0));//把汉族人数放到list中的第一个map上
			result.add(1, map);//把少数民族总人数放到list中的第二个map上
			result.addAll(list);//把各个少数民族的人数集合放到最后
			result.add(list2);
    return result;
}
@Override
public List<Map<String,Object>> queryStuCountByState(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.查询指定单位下处于在籍状态的各个学生状态下的学生人数
	 */
	 String str =getStuTableSql(schoolYear, deptList, stuAdvancedList);
	 String str1 = "(Select * from t_code where code_type='STU_STATE_CODE') state";//查询学生状态编码
	    String sql = "select nvl(state.name_,'"+WWH_NAME+"') as name,count(0) as value,nvl(state.code_,'"+WWH_CODE+"') as code from ("+str+") stu left join "+str1+" "
	     + " on stu.STU_STATE_CODE = state.code_ "
	     + " where  "+STUROLLSQL+" group by state.name_,state.code_ order by state.code_";
	
	return basedao.queryListInLowerKey(sql);
}
@Override
public List<Map<String,Object>> queryStuCountByForm(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList,Boolean isOld){
	/*
	 * 1.查询标准权限下各个学习形式的在籍学生数
	 * 
	 */
	 String ss = WWH_NAME;  
	 String bb = WWH_CODE; 
	 String x = isOld?"":"where "+STUROLLSQL;
	 String str =getStuTableSql(schoolYear, deptList, stuAdvancedList);
	 String str1 = "(Select * from t_code where code_type='LEARNING_FORM_CODE') form";//查询学生状态编码
	String sql = "select nvl(form.name_,'"+ss+"') as name,count(0) as value,nvl(form.code_,'"+bb+"') as code from ("+str+") stu left join "+str1+" "
			+ " on stu.LEARNING_FORM_CODE = form.code_   "
			+x+ " group by  form.name_,form.code_ order by form.code_";
	return basedao.queryListInLowerKey(sql);
}
private String getStuTableSql(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	stuAdvancedList = stuAdvancedList==null? new ArrayList<AdvancedParam>():stuAdvancedList;
	// 某学年所有在校学生数据sql
	String stuSql = businessDao.getStuSql(schoolYear, deptList, stuAdvancedList);
	return stuSql;
}
@Override
public List<Map<String,Object>> queryStuCountByStyle(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList,Boolean isOld){
	/*
	 * 1.查询标准权限下各个学习方式的在籍学生数
	 * 
	 */
	 String ss = WWH_NAME; 
	 String bb = WWH_CODE; 
	 String x = isOld?"":"where "+STUROLLSQL;
	 String str =getStuTableSql(schoolYear, deptList, stuAdvancedList);
	 String str1 = "(Select * from t_code where code_type='LEARNING_STYLE_CODE') style";//查询学生状态编码
	String sql = "select 'style' as id,nvl(style.name_,'"+ss+"') as name,count(0) as value,nvl(style.code_,'"+bb+"') as code from ("+str+") stu left join "+str1+" "
			+ " on stu.LEARNING_STYLE_CODE = style.code_ "
			+x+ "  group by style.name_,style.code_ order by style.code_ desc";
	return basedao.queryListInLowerKey(sql);
}
@Override
public List<Map<String,Object>> queryStuFrom(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.查询各地区的在籍学生人数，以及港澳台的学生数和留学生数
	 * 2.判断港澳台学生以及留学生的编码仍待确定，可以在Constant中维护
	 */
	String divid = getChinaId(); 
	String str =getStuTableSql(schoolYear, deptList, stuAdvancedList);
	 String str1 = "(select * from t_code code where code.code_type = 'GATQ_CODE') gatq ";//查询出港澳台侨编码表
	 String str2 = "select count(0) as value from ("+str+") stu inner join "+str1+""
	 		+ "  on stu.GATQ_CODE = gatq.code_ where "+STUROLLSQL+" and ";
//	 String syd = "substr(stu.stu_origin_id,0,2)||'0000' "+tj;//地图显示全国
//	 String syd1 = "substr(stu.stu_origin_id,0,4)||'00' "+tj;//地图显示人数最多的省
//	 String str3 = " select div.id as code,div.name_ as name,count(*) as value from ("+str+") stu left join t_code_admini_div div"
//			+ " on ";
	 String sql = querySql(divid, str);
	 String maxsql = " select t.id, t.name,t.value as value  from ("+sql +") t where rownum =1";
	 String sumsql = " select sum(t.value) as value from ("+sql+") t ";
	 String gatsql = str2+" gatq.code_ in  "+STU_GATQ_CODE;//此处硬编码了港澳台学生的编码，暂时不知道怎么搞合适
	 String qsql = str2+" gatq.code_  in "+STU_ABROAD_CODE;//此处硬编码了港澳台学生的编码，暂时不知道怎么搞合适
	Map<String,Object> max = basedao.queryMapInLowerKey(maxsql);//在籍学生数最多的地区和学生人数
	Map<String,Object> sum = basedao.queryMapInLowerKey(sumsql);//在籍生总人数
	Map<String,Object> map1 = new HashMap<String,Object>();
	int a = 0;
	if (max==null||max.isEmpty()){//非空验证
	
	}else{
		 a =MapUtils.getIntValue(max, "value");
	}
	int b = MapUtils.getIntValue(sum, "value");;
	double j = 0;
	if (b==0||a==0){//非空验证
		
	}else {
	  j = MathUtils.getDivisionResult(a,b,2)*100;
	}
	if(j<AREA_PERCENT){//当在籍学生人数最多的地区的在籍生人数大于或等于一定的比例之后显示该地区的地图，否则显示全国地图
		map1.put("name","china");//ecarts需要的maptype
	}else{
		divid = MapUtils.getString(max, "id");
		sql = querySql(divid, str);
		map1 = max;
	}
	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	Map<String,Object> gat = basedao.queryMapInLowerKey(gatsql);//港澳台学生人数
	Map<String,Object> q = basedao.queryMapInLowerKey(qsql);//留学生人数
	gat.put("id",STU_GATQ_CODE); q.put("id",STU_ABROAD_CODE);
	List<Map<String,Object>> list = basedao.queryListInLowerKey(sql);//各地区在籍生人数，value转化为number类型
	result.add(0,gat);result.add(1,q);result.add(2,map1);result.addAll(list);//港澳台数据放到第一，留学生放到第二，ecarts的maptype放到第三
	return result;
}
@Override
public List<Map<String,Object>> queryContrastByDept(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	String stu = getStuTableSql(schoolYear, deptList, stuAdvancedList);
	String str = "select t.*, nvl(a.id,'null') as eduid, nvl(a.name_,'"+WWH_NAME+"') as edumc,a.order_ from ("+stu+") t left join t_code_education a on "
			+ " t.edu_id = a.id where t.stu_roll_code = '"+Constant.CODE_STU_ROLL_CODE_1+"' ";
	Integer grade = AdvancedUtil.getStuGrade(stuAdvancedList);
	String deptSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), str, false, true, false, false, schoolYear, grade);
	String orderSql = businessService.getNextLevelOrderSqlByDeptDataAndPid(deptList, AdvancedUtil.getPid(stuAdvancedList), str, false, true, false, false, schoolYear, grade);
	String sql = "select  a.id as code, a.name_ as field,a.next_dept_order,dept.eduid as id,dept.edumc as name,count(0) as value from ("+deptSql+") dept "
			+ " ,("+orderSql+") a where  dept.next_dept_code = a.id group by  a.id,a.name_,a.next_dept_order,dept.eduid,dept.edumc "
			+ " order by a.next_dept_order,dept.eduid";
	return basedao.queryListInLowerKey(sql);
}
@Override
public List<Map<String,Object>> queryContrastByPolitics(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.各政治面貌的在籍生人数对比。
	 */
	String stu = getStuTableSql(schoolYear, deptList, stuAdvancedList);
    String sql = "select t.politics_code code, a.name_ field,t.edu_id id,"
    		+ " b.name_ name, count(0) value "
    		+ " from ("+stu+") t left join "
    		+ " (select * from t_code where code_type = 'POLITICS_CODE') "
    		+ " a on t.politics_code = a.code_ left join  t_code_education  b"
    		+ " on t.edu_id = b.id group by t.politics_code, a.name_,t.edu_id, b.name_ order by t.politics_code,t.edu_id";
	return basedao.queryListInLowerKey(sql);
}
@Override
public List<Map<String,Object>> queryPolitics(){
	//查询出所有政治面貌编码
	String str = " <> ";
	String sql  = "select code.code_ as id,code.name_ as name,code.order_ as pxh from t_code code where code.code_type = 'POLITICS_CODE' and code.istrue = 1 "
			+ " and code.code_ "+str+" '"+CODE_POLITICS_CODE_03+"' order by code.order_,code.code_";
		return basedao.queryListInLowerKey(sql);
}
@Override
public int getMaxXz(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	String stu = getStuTableSql(schoolYear,deptList,stuAdvancedList);
	String str = "select nvl(max(stu.length_schooling),0) as xz from ("+stu+") stu where "+STUROLLSQL+" and stu.length_schooling<6 ";
    return basedao.queryForInt(str);
}
private List<Map<String,Object>> queryGrade(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.查询出当前学校最长的学制。
	 * 2.查询出各年级对应的年份。
	 * 3.根据学制取出年级和对应年份的结果list。
	 * 注意事项:需要维护好t_stu中的length_schooling字段，为空默认为5。
	 */
	String stu = getStuTableSql(schoolYear,deptList,null);
	String str = "select max(stu.length_schooling) as xz from ("+stu+") stu where "+STUROLLSQL+" and stu.length_schooling<6 ";
	Map<String,Object> map = basedao.queryMapInLowerKey(str);
	String sql = "select field,value,name,id from ( select "+schoolYear+" as field,  0 as value, '"+EduUtils.getNjNameByCode(1)+"' as name ,1 as id from dual"
			+ " union select "+(schoolYear-1)+" as field,  0 as value, '"+EduUtils.getNjNameByCode(2)+"' as name,2 as id from dual"
		    + " union select "+(schoolYear-2)+" as field,  0 as value, '"+EduUtils.getNjNameByCode(3)+"' as name,3 as id from dual"
	        + " union select "+(schoolYear-3)+" as field,  0 as value, '"+EduUtils.getNjNameByCode(4)+"' as name,4 as id from dual"
	        + " union select "+(schoolYear-4)+" as field,  0 as value, '"+EduUtils.getNjNameByCode(5)+"' as name,5 as id from dual )"
	        + " order by field desc";//查询年级和年份的对应关系
	  List<Map<String ,Object>> list =  basedao.queryListInLowerKey(sql);
	List<Map<String ,Object>> result = new ArrayList<Map<String,Object>>();//结果集
	int xz = 5;//最长学制初始化
	if (map!=null&&!map.isEmpty()){//如果学制不是空数据的话
		  xz = MapUtils.getIntValue(map, "xz");
	}
	for (int i=0;i<xz;i++){//根据最长学制取出年级对应的年份。
		Map<String,Object> map1 = list.get(i);
		result.add(i, map1);
	}
	return result;
}
@Override
public List<Map<String,Object>> queryStuCountByBen(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.本省的代码需要设置
	 */
	List<AdvancedParam> advancedList = new ArrayList<AdvancedParam>(),
			            advancedList1 = new ArrayList<AdvancedParam>();
	if (stuAdvancedList != null){advancedList.addAll(stuAdvancedList);advancedList1.addAll(stuAdvancedList);};
	AdvancedParam sydAdp = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, getChinaId());
	advancedList = AdvancedUtil.add(advancedList, sydAdp);
	AdvancedParam sydAdp1 = new AdvancedParam(AdvancedUtil.Group_Business, AdvancedUtil.Stu_ORIGIN_ID, PROVINCE_CODE);
	advancedList1 = AdvancedUtil.add(advancedList1, sydAdp1);
	String stu = getStuTableSql(schoolYear,deptList, advancedList);
	String stu1 = getStuTableSql(schoolYear,deptList, advancedList1);
	String stu2 = getStuTableSql(schoolYear,deptList, stuAdvancedList);
	String str = "count(0) as value from ("+stu2+") stu ";
	String bssql = "select '1' as id,div.name_ as name from t_code_admini_div div  "
			+ " where div.id = '"+PROVINCE_CODE +"' ";
	String gatsql = "select '"+GAT_NAME+"' as name,'3' as id, "+str+" where stu.gatq_code in "+STU_GATQ_CODE;
	String qsql = "select '"+ABROAD_NAME+"' as name,'4' as id, "+str+" where stu.gatq_code in "+STU_ABROAD_CODE;
	Map<String,Object> bs = basedao.queryMapInLowerKey(bssql);
	int count = basedao.queryForCount(stu1);
	bs.put("value",count);
	Map<String,Object> ws = new HashMap<String, Object>();
	int all = basedao.queryForCount(stu);
	ws.put("id", '2');ws.put("name", OTHER_PROVINCE_NAME);ws.put("value",all-count);
	Map<String,Object> gat = basedao.queryMapInLowerKey(gatsql);
	Map<String,Object> q = basedao.queryMapInLowerKey(qsql);
	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	if(ws==null||ws.isEmpty()){
		ws.put("name", OTHER_PROVINCE_NAME);
		ws.put("value", 0);
	}
	result.add(bs);result.add(ws);result.add(gat);result.add(q);
		return result;
}
@Override
public List<String> queryStuAvgAge(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.查询某一年入学学生的出生日期集合，出生日期不为空的参与计算
	 */
	String str =getStuTableSql(schoolYear, deptList,stuAdvancedList);
	String sql = " select stu.birthday from ("+str+") stu where "
			+ " stu.enroll_grade = "+schoolYear+" ";
    return basedao.queryForListString(sql);
}
@Override
public List<Map<String,Object>> queryPoliticsLine(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.查出政治面貌编码，
	 * 2.查出某一学年各个政治面貌的学生人数，
	 * 3.政治面貌编码的名字尽量缩短
	 */
	String stu =getStuTableSql(schoolYear, deptList,stuAdvancedList);
	String str = "select code.code_ as id,code.name_ as name,code.order_ "
			 + " as pxh from t_code code where code.code_type = 'POLITICS_CODE'"
			 + "   and code.istrue = 1 order by code.order_,code.code_";
	String sql = " select  nvl(politics.name,'"+WWH_NAME+"') as name ,nvl(politics.id,'"+WWH_CODE+"') as code ,count(0) as value from ("+stu+") stu "
			+ " left join ("+str+") politics on "
			+ " stu.politics_code = politics.id  group by politics.id,politics.name";
    return basedao.queryListInLowerKey(sql);
}
@Override 
public List<Map<String,Object>> queryPoliticsCount(int schoolYear, List<String> deptList,List<AdvancedParam> stuAdvancedList){
	/*
	 * 1.查出政治面貌编码，
	 * 2.查出某一学年团员和非团员的学生人数，
	 * 3.团员的编码设置为‘03’
	 */
	String stu =getStuTableSql(schoolYear, deptList,stuAdvancedList);
	String str = "select code.code_ as id,code.name_ as name,code.order_ "
			 + " as pxh from t_code code where code.code_type = 'POLITICS_CODE'"
			 + "   and code.istrue = 1 order by code.order_,code.code_";
	String str1 = " select count(0) as value from ("+stu+") stu "
			+ " left join ("+str+") politics on "
			+ " stu.politics_code = politics.id where stu.politics_code "; 
	String str2 =  "'"+CODE_POLITICS_CODE_03 + "' and "+STUROLLSQL+" and stu.politics_code is not null ";
	String sql = str1+" <> "+ str2;
	String sql1 = str1+" = "+ str2;
	String sql2 = "select '"+WWH_NAME+"' as name,count(0) as value from ("+stu+") stu where stu.politics_code is null and "+STUROLLSQL;
	Map<String,Object> map = basedao.queryMapInLowerKey(sql);
	Map<String,Object> map1 = basedao.queryMapInLowerKey(sql1);
	Map<String,Object> map2 = basedao.queryMapInLowerKey(sql2);
	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>(); 
	if (map==null||map.isEmpty()){
		map = new HashMap<String,Object>();
		map.put("name", "其它");
		map.put("value", 0);
	}
	if (map1==null||map1.isEmpty()){
		map1 = new HashMap<String,Object>();
		map1.put("name", "团员");
		map1.put("value", 0);
	}
	if (map2==null||map2.isEmpty()){
		map2 = new HashMap<String,Object>();
		map2.put("name", WWH_NAME);
		map2.put("value", 0);
	}
	result.add(0,map);
	result.add(1,map1);
	result.add(2,map2);
	return result;
}
@Override
public Map<String,Object> queryBzdm(){
	Map<String,Object> map = new HashMap<String, Object>();
	String hanzu = HANZU_CODE;
	String tuanyuan = CODE_POLITICS_CODE_03;
	String wwh =WWH_CODE;
	String ben = PROVINCE_CODE;
	String gat = STU_GATQ_CODE;
	String q = STU_ABROAD_CODE;
	map.put("hanzu", hanzu);
	map.put("tuanyuan", tuanyuan);
	map.put("wwh", wwh);
	map.put("ben", ben);
	map.put("gat", gat);
	map.put("q", q);
	return map;
}
@Override
public String queryTableData(String table,String result,String column,String name){
	String sql = "select "+result+" code from "+table +" t where t."+column+" = '"+name+"'";
    return basedao.queryForString(sql);
}
private String getChinaId(){
	String sql = "select t.id from t_code_admini_div t where t.istrue = 1 and t.pid = '-1' ";
	return basedao.queryForString(sql);
}
/**
 * 获取下层地区节点信息
 */
private String querySql(String Id,String stuSql) {
	String str = "select div.id as id, div.id as code ,div.name_ as name,div.pid,div.level_ as cc,div.path_ as qxm from t_code_admini_div div where div.pid ='" + Id + "'";
	 String sql = "select c.divid as id,c.name,count(0) as value "
				+ " from ("+stuSql+") stu,"
				+ " (select a.id as divid,a.name,a.cc,a.pid as fjd,b.* from ("+str+") a ,t_code_admini_div b "
				+ " where a.qxm = substr(b.path_,0,length(a.qxm)) ) c "
				+ " where stu.stu_origin_id = c.id and "+STUROLLSQL+" group by c.divid,c.name order by count(0) desc";
	return sql;
}
}
