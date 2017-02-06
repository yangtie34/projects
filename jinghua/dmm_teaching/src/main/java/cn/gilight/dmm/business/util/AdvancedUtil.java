package cn.gilight.dmm.business.util;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.gilight.dmm.business.entity.AdvancedParam;

/**
 * 高级查询-常量
 * 
 * @author xuebl
 * @date 2016年5月17日 下午6:05:31
 */
public class AdvancedUtil {

	/**
	 * 分组-公共（学生、教师可用，业务不可用）；common
	 */
	public static final String Group_Common = "common";
	/**
	 * 分组-学生；stu
	 */
	public static final String Group_Stu = "stu";
	/**
	 * 分组-教师；tea
	 */
	public static final String Group_Tea = "tea";
	/**
	 * 分组-业务；business
	 */
	public static final String Group_Business = "business";

	/**
	 * 不限-id-ALL
	 */
	public static final String ALL_ID_ALL = "ALL";
	
	/**
	 * 公共参数-行政组织机构
	 */
	public static final String Common_DEPT_ID = "DEPT_ID";
	/**
	 * 公共参数-教学组织机构（学生单位）（不包括教学单位 level_type!='JXDW'）
	 */
	public static final String Common_DEPT_TEACH_ID = "DEPT_TEACH_ID";
	/**
	 * 公共参数-教学组织机构（教学单位）
	 */
	public static final String Common_DEPT_TEACH_TEACH_ID = "DEPT_TEACH_TEACH_ID";
	/**
	 * 公共参数-班级
	 */
	public static final String Common_CLASS_ID = "CLASS_ID";
	/**
	 * 公共参数-性别
	 */
	public static final String Common_SEX_CODE = "SEX_CODE";
	/**
	 * 公共参数-学年；eg：2014、2015、2016（2016-2017学年）
	 */
	public static final String Common_SCHOOL_YEAR = "SCHOOL_YEAR";
	/**
	 * 公共参数-年；eg：2014、2015、2016（2016年）
	 */
	public static final String Common_YEAR = "YEAR";
	/**
	 * 学生参数-学科门类；eg：学科专业门类ID
	 */
	public static final String Stu_SUBJECT = "SUBJECT_STU";
	/**
	 * 学生参数-入学年级；eg：2014、2015、2016
	 */
	public static final String Stu_ENROLL_GRADE = "ENROLL_GRADE";
	/**
	 * 学生参数-年级；eg:1、2、3、4、5
	 */
	public static final String Stu_GRADE = "GRADE";
	/**
	 * 学生参数-培养层次；eg：20,30 、 20 、 30
	 */
	public static final String Stu_EDU_ID = "EDU_ID_STU";
	/**
	 * 学生参数-学籍状态；
	 */
	public static final String Stu_STU_ROLL_CODE = "STU_ROLL_CODE";
	/**
	 * 学生参数-民族代码；
	 */
	public static final String Stu_NATION_CODE = "NATION_CODE_STU";
	/**
	 * 学生参数-政治面貌代码；
	 */
	public static final String Stu_POLITICS_CODE = "POLITICS_CODE_STU";
	/**
	 * 学生参数-学制；eg:1、2、3、4、5
	 */
	public static final String Stu_LENGTH_SCHOOLING = "LENGTH_SCHOOLING";
	/**
	 * 学生参数-学生状态；
	 */
	public static final String Stu_STU_STATE_CODE = "STU_STATE_CODE";
	/**
	 * 学生参数-学习形式；
	 */
	public static final String Stu_LEARNING_FORM_CODE = "LEARNING_FORM_CODE";
	/**
	 * 学生参数-生源类别；
	 */
	public static final String Stu_STU_FROM_CODE = "STU_FROM_CODE";
	/**
	 * 学生参数-招生类别；
	 */
	public static final String Stu_RECRUIT_CODE = "RECRUIT_CODE";
	/**
	 * 学生参数-学习方式；
	 */
	public static final String Stu_LEARNING_STYLE_CODE = "LEARNING_STYLE_CODE";
	/**
	 * 学生参数-户口性质；
	 */
	public static final String Stu_ANMELDEN_CODE = "ANMELDEN_CODE_STU";
	/**
	 * 学生参数-生源地树形结构
	 */
	public static final String Stu_ORIGIN_ID = "STU_ORIGIN_ID";
	
	
	/**
	 * 教职工参数-教职工状态
	 */
	public static final String Tea_TEA_STATUS_CODE = "TEA_STATUS_CODE";
	/**
	 * 教职工参数-学历
	 */
	public static final String Tea_EDU_ID = "EDU_ID";
	/**
	 * 教职工参数-学位
	 */
	public static final String Tea_DEGREE_ID = "DEGREE_ID";
	/**
	 * 教职工参数-专业技术职务
	 */
	public static final String Tea_ZYJSZW_ID = "ZYJSZW_ID";
	/**
	 * 教职工参数-专业技术职务等级
	 */
	public static final String Tea_ZYJSZW_JB_CODE = "ZYJSZW_JB_CODE";
	/**
	 * 教职工参数-教师类别；eg：专任、外聘、其他...
	 */
	public static final String Tea_AUTHORIZED_STRENGTH_ID = "AUTHORIZED_STRENGTH_ID";
	/**
	 * 教职工参数-高层次人才
	 */
	public static final String Tea_SENIOR_CODE = "SENIOR_CODE";
	/**
	 * 教职工参数-学科门类；eg：学科专业门类ID
	 */
	public static final String Tea_SUBJECT = "SUBJECT_TEA";
	/**
	 * 教职工参数-户口性质；
	 */
	public static final String Tea_ANMELDEN_CODE = "ANMELDEN_CODE_TEA";
	/**
	 * 教职工参数-民族代码；
	 */
	public static final String Tea_NATION_CODE = "NATION_CODE_TEA";
	/**
	 * 教职工参数-政治面貌代码；
	 */
	public static final String Tea_POLITICS_CODE = "POLITICS_CODE_TEA";
	/**
	 * 教职工参数-教职工来源代码；
	 */
	public static final String Tea_TEA_SOURCE_ID = "TEA_SOURCE_ID";
	/**
	 * 教职工参数-编制类别；
	 */
	public static final String Tea_BZLB_CODE = "BZLB_CODE";
	/**
	 * 教职工参数-是否双师教师；eg:0、1 
	 */
	public static final String Tea_SFSSJS = "SFSSJS";
	/**
	 * 教职工参数-工人技术等级；
	 */
	public static final String Tea_SKILL_MOVES_CODE = "SKILL_MOVES_CODE";
	
	
	/**
	 * 业务参数-成绩指标
	 */
	public static final String Business_SCORE_TARGET = "SCORE_TARGET";
	/**
	 * 业务参数-课程属性
	 */
	public static final String Business_COURSE_ATTR_CODE = "COURSE_ATTR_CODE";
	/**
	 * 业务参数-课程性质
	 */
	public static final String Business_COURSE_NATURE_CODE = "COURSE_NATURE_CODE";
	

	/**
	 * 高级查询参数-转换
	 * @param param Json
	 * @return List< AdvancedParam>
	 */
	public static List<AdvancedParam> converAdvancedList(String param){
		return JSONObject.parseArray(param, AdvancedParam.class);
	}
	/**
	 * 获取高级查询参数-学生
	 * @param advancedParamList 高级查询参数
	 * @return List<AdvancedParam>
	 */
	public static List<AdvancedParam> getAdvancedParamStu(List<AdvancedParam> advancedParamList){
		return getParam(advancedParamList, Group_Common, Group_Stu);
	}
	/**
	 * 获取高级查询参数-教师
	 * @param advancedParamList 高级查询参数
	 * @return List<AdvancedParam>
	 */
	public static List<AdvancedParam> getAdvancedParamTea(List<AdvancedParam> advancedParamList){
		return getParam(advancedParamList, Group_Common, Group_Tea);
	}
	/**
	 * 获取高级查询参数-业务
	 * @param advancedParamList 高级查询参数
	 * @return List<AdvancedParam>
	 */
	public static List<AdvancedParam> getAdvancedParamBusiness(List<AdvancedParam> advancedParamList){
		return getParam(advancedParamList, Group_Business);
	}
	/**
	 * 获取高级查询参数
	 * @param advancedParamList 高级查询参数
	 * @param groups 分组；eg：
	 * @return List<AdvancedParam>
	 */
	private static List<AdvancedParam> getParam(List<AdvancedParam> advancedParamList, String... groups) {
		List<AdvancedParam> list = new ArrayList<>();
		if(advancedParamList == null || !(advancedParamList instanceof List)) return list;
		String group = null;
		for(AdvancedParam t : advancedParamList){
			group = t.getGroup();
			for(int i=0,len=groups.length; i<len; i++){
				if(groups[i].equals(group)){
					list.add(t);
					break;
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取高级查询参数中的组织机构Id
	 * @param advancedParamList
	 * @return String
	 */
	public static String getPid(List<AdvancedParam> advancedParamList){
		String pid = getValue(advancedParamList, Common_DEPT_ID);
		pid =  pid!=null ? pid : getValue(advancedParamList, Common_DEPT_TEACH_ID);
		pid =  pid!=null ? pid : getValue(advancedParamList, Common_DEPT_TEACH_TEACH_ID);
		return pid;
	}
	
	/**
	 * 获取高级查询参数中 学生年级
	 * @param advancedParamList
	 * @return Integer
	 * <br> null/1/2 ...
	 */
	public static Integer getStuGrade(List<AdvancedParam> advancedParamList){
		String grade_ = AdvancedUtil.getValue(advancedParamList, Stu_GRADE);
		return grade_==null ? null : Integer.valueOf(grade_);
	}
	
	/**
	 * 获取高级查询参数中的value
	 * @param advancedParamList
	 * @param code
	 * @return String
	 */
	public static String getValue(List<AdvancedParam> advancedParamList, String code){
		String value = null;
		AdvancedParam t = get(advancedParamList, code);
		return t!=null ? t.getValues() : value;
	}

	/**
	 * 获取高级查询参数中的 对象
	 * <br> 查不到则返回 new AdvancedParam()
	 * @param advancedParamList
	 * @param code
	 * @return AdvancedParam
	 */
	public static AdvancedParam get(List<AdvancedParam> advancedParamList, String code){
		AdvancedParam obj = new AdvancedParam();
		if(advancedParamList != null && code != null){
			for(AdvancedParam t : advancedParamList){
				if(code.equals(t.getCode())){
					obj = t;
				}
			}
		}
		return obj;
	}
	/**
	 * 向高级查询参数中添加对象
	 * <br> 添加时会移除相同编码的原参数
	 * @param advancedParamList 为null时会自动初始化为空List
	 * @param t 
	 * @return List<AdvancedParam>
	 */
	public static List<AdvancedParam> add(List<AdvancedParam> advancedParamList, AdvancedParam t){
		advancedParamList = advancedParamList==null ? new ArrayList<AdvancedParam>() : advancedParamList;
		if(advancedParamList != null && t != null){
			String code = t.getCode();
			if(code != null){
				for(AdvancedParam entity : advancedParamList){
					if(code.equals(entity.getCode())){
						advancedParamList.remove(entity);
						break;
					}
				}
				advancedParamList.add(t);
			}
		}
		return advancedParamList;
	}
	
}
