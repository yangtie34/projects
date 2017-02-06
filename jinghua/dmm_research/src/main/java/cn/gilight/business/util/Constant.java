package cn.gilight.business.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务系统级静态常量（统一维护）
 * 
 * @author xuebl
 * @date 2016年4月21日 上午11:42:46
 */
public class Constant {
	
	/**
	 * 学生工作 ShiroTag 前置
	 */
	public static final String ShiroTag_Xg = "xg:";
	
	/**
	 * 学生工作口-学历（本/专、本、专）
	 */
	public static final String[][] Stu_Education_Group = {
			{"20,30", "本/专科"},{"20", "本科"}, {"30", "专科"}
	};
	
	/**
	 * 学生工作者-名称
	 */
	public static final String Worker_Name = "学生工作者";
	
	/**
	 * 专职辅导员与学生比 1:200，这里是专职辅导员管理学生数
	 */
	public static final int Worker_Instructors_Stu_Ratio = 200;
	
	/**
	 * 学生工作者年龄分组
	 * eg：{null,25} 25及以下；{26,35} 26~35；{56,null} 56及以上
	 */
	public static final Object[][] Worker_Age_Group = {
			{null,25, "25岁及以下"},
			{26,35, "26~35岁"},
			{36,45, "36~45岁"},
			{46,55, "46~55岁"},
			{56,null, "56岁及以上"}
	};
	/**
	 * 学生年龄分组
	 * eg：{null,25} 25及以下；{26,35} 26~35；{56,null} 56及以上
	 */
	public static final Object[][] STU_AGE_GROUP = {
		{null,16, "16岁及以下"},
		{17,17, "17岁"},
		{18,18, "18岁"},
		{19,19, "19岁"},
		{20,20, "20岁"},
		{21,21, "21岁"},
		{22,22, "22岁"},
		{23,23, "23岁"},
		{24,24, "24岁"},
		{25,25, "25岁"},
		{26,26, "26岁"},
		{27,null, "27岁及以上"}
};
	
	
	/**
	 * 学籍异动-不良异动code
	 */
	public static final String[] Change_Bad_Code = {
			"02","03","06","11","13","16","18","31"};
	

	/**
	 * 奖惩助贷-标准代码
	 */
	public static final List<Map<String, Object>> JCZD_Bzdm_Group_CountMoneyScale = new ArrayList<>();
	static {
		Map<String, Object> m_count = new HashMap<>(), m_money = new HashMap<>(), m_scale = new HashMap<>(); 
		m_count.put("id", "count"); m_count.put("mc", "按人数");  m_count.put("unit", "人"); 
		m_money.put("id", "money"); m_money.put("mc", "按金额");  m_money.put("unit", "元");
		m_scale.put("id", "scale"); m_scale.put("mc", "按覆盖率"); m_scale.put("unit", "%"); 
		JCZD_Bzdm_Group_CountMoneyScale.add(m_count);
		JCZD_Bzdm_Group_CountMoneyScale.add(m_money);
		JCZD_Bzdm_Group_CountMoneyScale.add(m_scale);
	}

	/**
	 * 无学位学分代码
	 */
	public static final int NODEGREE_CODE = 25;


	/**
	 * 奖惩助贷-表名
	 */
	public enum JCZD_Table {
		/** 奖学金表 */ AWARD("t_stu_award","award_code",CODE_AWARD_CODE), /** 助学金表 */ SUBSIDY("t_stu_subsidy","subsidy_code",CODE_SUBSIDY_CODE);
		private String table; private String code; private String codeType;
		JCZD_Table(String table, String code, String codeType){ this.table = table;this.code = code;this.codeType=codeType; }
		public String getTable(){ return table; }
		public String getCode(){ return code; }
		public String getCodeType(){ return codeType; }
	}
	/**
	 * 奖惩助贷-排序字段
	 */
	public enum JCZD_Desc_Column {
		/** 次数 */ count("count"), /** 金额 */ money("money"), /** 连续次数 */continue_count("continue_count");
		private String value;
		JCZD_Desc_Column(String value){ this.value = value; }
		public String getValue(){ return value; }
	}

	/**
	 * 违纪处分-年龄分组
	 */
	public static final Object[][] PUNISH_AGE_GROUP = {
			{null,18, "18岁及以下"},
			{19,19, "19岁"},
			{20,20, "20岁"},
			{21,21, "21岁"},
			{22,22, "22岁"},
			{23,23, "23岁"},
			{24,24, "24岁"},
			{25,null, "25岁及以上"}
	};

	
	/**
	 * 层次类型 XX
	 */
	public static final String Level_Type_XX = "XX";
	/**
	 * 层次类型 YX
	 */
	public static final String Level_Type_YX = "YX";
	/**
	 * 层次类型 ZY
	 */
	public static final String Level_Type_ZY = "ZY";
	/**
	 * 层次类型 BJ
	 */
	public static final String Level_Type_BJ = "BJ";
	
	/**
	 * 教学组织机构表名
	 */
	public static final String T_Code_Dept_Teach = "T_Code_Dept_Teach";
	/**
	 * 行政组织机构表名
	 */
	public static final String T_Code_Dept = "T_Code_Dept";
	
	/**
	 * 标准代码-编码 ALL
	 */
	public static final String CODE_All = "ALL";
	
	/**
	 * 标准代码-其他 【没有相关代码或代码无法关联】
	 */
	public static final String CODE_Other_Name = "其他";
	
	/**
	 * 学生工作者-标准代码
	 */
	public static final String CODE_STU_WORKER_CODE = "STU_WORKER_CODE";
	/**
	 * 学生工作者-标准代码-专职辅导员
	 */
	public static final String CODE_STU_WORKER_CODE_2 = "2";
	
	
	/**
	 * 学籍异动-标准代码
	 */
	public static final String CODE_STU_CHANGE_CODE = "STU_CHANGE_CODE";
	
	/**
	 * 奖学金-标准代码
	 */
	public static final String CODE_AWARD_CODE = "AWARD_CODE";
	/**
	 * 助学金-标准代码
	 */
	public static final String CODE_SUBSIDY_CODE = "SUBSIDY_CODE";
	/**
	 * 处分类型-标准代码
	 */
	public static final String CODE_PUNISH_CODE = "PUNISH_CODE";
	
	/**
	 * 标准代码-专业技术职务、职称
	 */
	public static final String CODE_ZYJSZW_JB_CODE = "ZYJSZW_JB_CODE";
	/**
	 * 标准代码-性别
	 */
	public static final String CODE_SEX_CODE = "SEX_CODE";
	/**
	 * 标准代码-学生学籍状态-在籍
	 */
	public static final String CODE_STU_ROLL_CODE_1 = "1";
}
