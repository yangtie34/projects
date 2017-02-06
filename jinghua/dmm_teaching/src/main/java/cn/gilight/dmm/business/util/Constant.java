package cn.gilight.dmm.business.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务系统级静态常量（统一维护）<br>
 * 这个文件的特点有两个：<br>
 * 1.确定不变又很常用的<br>
 * 2.变化性很大，放在这里方便统一维护
 * @author xuebl
 * @date 2016年4月21日 上午11:42:46
 */
public class Constant {
	
	/**
	 * 学生工作 ShiroTag 前置
	 */
	public static final String ShiroTag_Xg = "teaching:";
	
	/** 历年历史长度 全屏  */
	public static final int Year_His_Len = 10;
	/** 历年历史长度 半屏  */
	public static final int Year_His_Len_Half = 5;
	
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
			{0,30, "30岁及以下"},
			{31,40, "31~40岁"},
			{41,50, "41~50岁"},
			{51,60, "51~60岁"},
			{61,null, "61岁及以上"},
			{null,null, null}
	};
	/**
	 * 学生年龄分组
	 * eg：{null,25} 25及以下；{26,35} 26~35；{56,null} 56及以上
	 */
	public static final Object[][] STU_AGE_GROUP = {
			{0,16, "16岁及以下"},
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
			{27,null, "27岁及以上"},
			{null,null, null}
	};
	/**
	 * 成绩等级分组
	 */
	public static final Object[][] STU_SCORE_GROUP = {
			{90,null, "优秀",">=90分",1},
			{80,90, "良好","80-90分",3},
			{70,80, "中等","70-80分",5},
			{60,70, "及格","60-70分",7},
			{0,60, "不及格","<60分",9}
    };
	/**
	 * 获取成绩分组
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String,Object>> getScoreGroup(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=0;i<STU_SCORE_GROUP.length;i++){
			Map<String, Object> temp = new HashMap<String, Object>();
			Object[] ary = STU_SCORE_GROUP[i];
			temp.put("id", ary[0]+","+ary[1]);
			temp.put("mc", ary[2]);
			temp.put("mc1", ary[3]);
			temp.put("order", ary[4]);
			list.add(temp);
		}
		return list;
	}
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
	 * 学生无法毕业无学位代码
	 */
	public static final String[][] No_GradDegree_Code = {
		{"1", "无学位证"},{"2", "无法毕业"}
	};

	/**
	 * 奖惩助贷-表名
	 */
	public enum JCZD_Table {
		/** 奖学金表 */ 
		AWARD("t_stu_award","award_code",CODE_AWARD_CODE,"奖学金"),
		/** 助学金表 */ 
		SUBSIDY("t_stu_subsidy","subsidy_code",CODE_SUBSIDY_CODE,"助学金"),
		/** 助学贷款表 */ 
		LOAN("T_STU_LOAN","LOAN_CODE",CODE_LOAN_CODE,"助学贷款");
		private String table; private String code; private String codeType; private String name;
		JCZD_Table(String table, String code, String codeType, String name){ this.table = table;this.code = code;this.codeType=codeType;this.name=name; }
		public String getTable(){ return table; }
		public String getCode(){ return code; }
		public String getCodeType(){ return codeType; }
		public String getName(){ return name; }
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
			{0,18, "18岁及以下"},
			{19,19, "19岁"},
			{20,20, "20岁"},
			{21,21, "21岁"},
			{22,22, "22岁"},
			{23,23, "23岁"},
			{24,24, "24岁"},
			{25,null, "25岁及以上"},
			{null,null, null}
	};

	
	/**
	 * 层次类型 XX
	 */
	public static final String Level_Type_XX = "XX";
	/**
	 * 层次类型 XX对应的名称
	 */
	public static final String Level_Type_XX_Name = "学校";
	/**
	 * 层次类型 YX
	 */
	public static final String Level_Type_YX = "YX";
	/**
	 * 层次类型 YX对应的名称
	 */
	public static final String Level_Type_YX_Name = "学院";
	/**
	 * 层次类型 系 X
	 */
	public static final String Level_Type_X = "X";
	/**
	 * 层次类型 X对应的名称
	 */
	public static final String Level_Type_X_Name = "系";
	/**
	 * 层次类型 ZY
	 */
	public static final String Level_Type_ZY = "ZY";
	/**
	 * 层次类型 ZY对应的名称
	 */
	public static final String Level_Type_ZY_Name = "专业";
	/**
	 * 层次类型 BJ
	 */
	public static final String Level_Type_BJ = "BJ";
	/**
	 * 层次类型 BJ对应的名称
	 */
	public static final String Level_Type_BJ_Name = "班级";
	/**
	 * 层次类型 JXDW 教学单位
	 */
	public static final String Level_Type_JXDW = "JXDW";
	
	
	/**
	 * 表-教学组织机构表名
	 */
	public static final String TABLE_T_Code_Dept_Teach = "T_Code_Dept_Teach";
	/**
	 * 表-行政组织机构表名
	 */
	public static final String TABLE_T_Code_Dept = "T_Code_Dept";
	/**
	 * 表-学科专业表
	 */
	public static final String TABLE_T_Code_Subject_Degree = "T_Code_Subject_Degree";
	/**
	 * 表-标准代码-专业技术职务
	 */
	public static final String TABLE_T_CODE_ZYJSZW = "T_CODE_ZYJSZW";
	/**
	 * 表-标准代码-学历
	 */
	public static final String TABLE_T_CODE_EDUCATION = "T_CODE_EDUCATION";
	/**
	 * 表-标准代码-学位
	 */
	public static final String TABLE_T_CODE_DEGREE = "T_CODE_DEGREE";
	/** 表-学生预警-疑似逃课 */
	public static final String TABLE_T_STU_WARNING_SKIP_CLASSES = "T_STU_WARNING_SKIP_CLASSES";
	/** 表-学生预警-疑似未住宿 */
	public static final String TABLE_T_STU_WARNING_NOTSTAY    = "T_STU_WARNING_NOTSTAY";
	/** 表-学生预警-疑似晚归 */
	public static final String TABLE_T_STU_WARNING_STAY_LATE  = "T_STU_WARNING_STAY_LATE";
	/** 表-学生预警-疑似不在校 */
	public static final String TABLE_T_STU_WARNING_STAY_NOTIN = "T_STU_WARNING_STAY_NOTIN";
	/** 晚勤晚归-最晚时间 22:30 （>=22:30） */
	public static String WARNING_STAY_LATE_TIME = "22:30";
	/** 疑似不在校-最长无记录天数 */
	public static int WARNING_STAY_NOTIN_DAY_LEN = 3;
	/** 表-学生预警-疑似晚归 */
	public static final String TABLE_T_CARD_STAY_LATE  = "T_CARD_STAY_LATE";
	/** 表-学生预警-疑似不在校 */
	public static final String TABLE_T_CARD_STAY_NOTIN = "T_CARD_STAY_NOTIN";
	/** 疑似逃课-处理的消费类型 */
	public static final String WARNING_SKIP_CLASSES_CARD_DEAL_TYPE = "'210','215','211','220','223','214'";
	/** 疑似逃课-处理的读者类型（学生） */
	public static final String WARNING_SKIP_CLASSES_READER_IDENTITY_STU = "'04','07'";
	
	
	/**
	 * 标准代码-编码 ALL
	 */
	public static final String CODE_All = "ALL";

	/**
	 * 标准代码-其他 【没有相关代码或代码无法关联】
	 */
	public static final String CODE_Unknown = "null";
	/**
	 * 标准代码名称-其他 【没有相关代码或代码无法关联】
	 */
	public static final String CODE_Unknown_Name = "未知";
	
	/**
	 * 学生工作者-标准代码
	 */
	public static final String CODE_STU_WORKER_CODE = "STU_WORKER_CODE";
	/**
	 * 学生工作者-标准代码-专职辅导员
	 */
	public static final String CODE_STU_WORKER_CODE_2 = "2";
	/**
	 * 学生工作者-标准代码-兼职辅导员
	 */
	public static final String CODE_STU_WORKER_CODE_3 = "3";
	
	
	/**
	 * 标准代码-培养层次
	 */
	public static final String CODE_TRAINING_LEVEL_CODE = "TRAINING_LEVEL_CODE";
	/** 标准代码-培养层次-博士 */
	public static final String CODE_TRAINING_LEVEL_CODE_1 = "1";
	/** 标准代码-培养层次-硕士 */
	public static final String CODE_TRAINING_LEVEL_CODE_2 = "2";
	/** 标准代码-培养层次-本科 */
	public static final String CODE_TRAINING_LEVEL_CODE_3 = "3";
	/** 标准代码-培养层次-专科 */
	public static final String CODE_TRAINING_LEVEL_CODE_4 = "4";
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
	 * 助学贷款-标准代码
	 */
	public static final String CODE_LOAN_CODE = "LOAN_CODE";
	/**
	 * 处分类型-标准代码
	 */
	public static final String CODE_PUNISH_CODE = "PUNISH_CODE";
	
	
	/**
	 * 标准代码-专业技术职务等级
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
	/**
	 * 早餐，午餐，晚餐开始和结束时间
	 */
	public static final String[][] Meal_Time_Group ={
		{"早餐","00","10"},{"午餐","10","16"},{"晚餐","16","24"}
	};
	/**
	 * 这个时间段内算为早起（早起时间段）
	 */
	public static final String[] Early_Time = {"03","07"};
	
	
	/**
	 * 教学工作
	 */

	/**
	 * 教学工作 ShiroTag 前置
	 */
	public static final String ShiroTag_Teaching = "teaching:";

	/**
	 * 标准代码-教职工状态-在职
	 */
	public static final String CODE_TEA_STATUS_CODE_11 = "11";
	/**
	 * 教职工默认状态查询编码（在职）
	 * @return String
	 */
	public static final String getTeaStatusCodeDefault(){
		return "'"+CODE_TEA_STATUS_CODE_11+"'";
	}
	/**
	 * 高职称-副高级以上职称
	 */
	public static final String High_Technical = "01,02";
	/**
	 * 高学位-博士以上学位
	 */
	public static final String High_Degree = "1,2";
	/**
	 * 高学历-研究生博士以上学历
	 */
	public static final String High_Edu = "01";
	/**
	 * 青年教师
	 */
	public static final int Young_Age = 45;
	
	/**
	 * 职称-教授
	 */
	public static final String Teacher_Professor = "011";
	/**
	 * 教职工状态编码（在校）[在职]
	 */
	public static final String Teacher_Status_Code = "11";
	/**
	 * 代码-教职工来源-专任教师
	 */
	public static final String CODE_AUTHORIZED_STRENGTH_ID_11 = "11";
	/**
	 * 代码-教职工来源-外聘教师
	 */
	public static final String CODE_AUTHORIZED_STRENGTH_ID_50 = "50";
	/**
	 * 标准代码-高级人才
	 */
	public static final String CODE_SENIOR_CODE = "SENIOR_CODE";
	
	/**
	 * 计算教龄的字段，默认 TEACHING_DATE，一般学校没有数据，暂用 IN_DATE TODO
	 */
	public static final String Field_School_Date = "in_date";

	/**
	 * 师资队伍-年龄分组
	 */
	public static final Object[][] TeacherGroup_Age_Group = {
			{0,35, "35岁及以下"},
			{36,45, "36~45岁"},
			{46,55, "46~55岁"},
			{56,65, "56~65"},
			{66,null, "66岁及以上"},
			{null,null, null}
	};
	/**
	 * 师资队伍-教龄分组
	 */
	public static final Object[][] TeacherGroup_SchoolAge_Group = {
			{0,5, "5年及以下"},
			{6,10, "6~10年"},
			{11,15, "11~15年"},
			{16,20, "16~20年"},
			{21,25, "21~25年"},
			{26,30, "26~30年"},
			{31,null, "31年及以上"},
			{null,null, null}
	};
	
	/**
	 * 平均数
	 */
	public static final String SCORE_AVG = "avg";
	/**
	 * 中位数
	 */
	public static final String SCORE_MIDDLE = "middle";
	/**
	 * 众数
	 */
	public static final String SCORE_MODE = "mode";
	/**
	 * 方差
	 */
	public static final String SCORE_FC = "fc";
	/**
	 * 标准差
	 */
	public static final String SCORE_BZC = "bzc";
	/**
	 * 优秀
	 */
	public static final String SCORE_BETTER = "better";
	/**
	 * 及格
	 */
	public static final String SCORE_FAIL = "fail";
	/**
	 * 重修
	 */
	public static final String SCORE_REBUILD = "rebuild";
	/**
	 * 低于平均数
	 */
	public static final String SCORE_UNDER = "under";
	/**
	 * 学生行为类型-平均绩点
	 */
	public static final String BEHAVIOR_GPA = "gpa";
	/**
	 * 学生行为类型-平均早起次数
	 */
	public static final String BEHAVIOR_EARLY_AVG = "early";
	/**
	 * 学生行为类型-平均早餐次数
	 */
	public static final String BEHAVIOR_BREAKFAST_AVG = "breakfast";
	/**
	 * 学生行为类型-平均借书次数
	 */
	public static final String BEHAVIOR_BORROW_AVG = "borrow";
	/**
	 * 学生行为类型-进出图书馆次数
	 */
	public static final String BEHAVIOR_BOOK_RKE = "bookrke";
	
	/**
	 * 学生成绩指标-（平均数、中位数、众数、方差、标准差、优秀率、不及格率、重修率、低于平均数）
	 */
	public static final String[][] Score_Target_Group = {
		{SCORE_AVG, "平均数", ""},{SCORE_MIDDLE, "中位数", ""}, {SCORE_MODE, "众数", ""}, {SCORE_FC, "方差", ""}, {SCORE_BZC, "标准差", ""},
		{SCORE_BETTER, "优秀率", "%"},{SCORE_FAIL, "不及格率", "%"}, {SCORE_REBUILD, "重修率", "%"}, {SCORE_UNDER, "低于平均数", "%"}
	};
	/**
	 * 学生成绩类型-（平均数、中位数、众数、方差、标准差）
	 */
	public static final String[][] Score_Type_Group = {
		{SCORE_AVG, "平均数"},{SCORE_MIDDLE, "中位数"}, {SCORE_MODE, "众数"}, {SCORE_FC, "方差"}, {SCORE_BZC, "标准差"}
	};
	
	/**
	 * 成绩类型-百分制成绩
	 */
	public static final String CODE_CATEGORY_CODE_1 = "1";
	/**
	 * 成绩类型-等级制成绩
	 */
	public static final String CODE_CATEGORY_CODE_2 = "2";
	
	/**
	 * 最基础的GPA计算规则ID
	 */
	public static final String SCORE_GPA_BASE_CODE = "1";

	/**
	 * 成绩概况 成绩分组
	 * eg：{3.5,4} 3.5~4；{3,3.49} 3~3.49
	 */
	public static final Object[][] Score_Gpa_Group = {
			{3.5D,4.1D, "3.5~4"},
			{3D,3.49D, "3~3.49"},
			{2.5D,2.99D, "2.5~2.99"},
			{2D,2.49D, "2~2.49"},
			{1.5D,1.99D, "1.5~1.99"},
			{1D,1.49D, "1~1.49"},
			{0D,0.99D, "0~0.99"}
	};
	
	/**
	 * 标准代码-课程属性
	 */
	public static final String CODE_COURSE_ATTR_CODE = "COURSE_ATTR_CODE";
	/**
	 * 标准代码-课程性质
	 */
	public static final String CODE_COURSE_NATURE_CODE = "COURSE_NATURE_CODE";
	/**
	 * 标准代码-课程类别
	 */
	public static final String CODE_COURSE_CATEGORY_CODE = "COURSE_CATEGORY_CODE";
	/**
	 * 表-学生成绩
	 */
	public static final String TABLE_T_STU_SCORE = "T_STU_SCORE";
	/**
	 * 表-学生挂科补考成绩
	 */
	public static final String TABLE_T_STU_Gk = "T_FAILEXAMINATION";
	/**
	 * 表-预测成绩表
	 */
	public static final String TABLE_T_STU_SC_PRE = "T_STU_SCORE_PREDICT_BEH";
	/**
	 * 表-学生成绩
	 */
	public static final String TABLE_T_FAIL_EXAM = "T_STU_SCORE_HISTORY";
	/**
	 * 临时表-学生行为
	 */
	public static final String TABLE_T_STU_BEHAVIOR = "T_STU_BEHAVIOR";
	/**
	 * 临时表-学生行为-时间维度
	 */
	public static final String TABLE_T_STU_BEHAVIOR_TIME = "T_STU_BEHAVIOR_TIME";
	/**
	 * 基础数据-交易类型-餐费支出
	 */
	public static final String CODE_CARD_DEAL_210 = "210";
	/**
	 * 基础数据-专业技术职务-高等学校教师
	 */
	public static final String CODE_ZYJSZW_ID_010 = "010";
	/**
	 * 表 - 成绩预测-根据学生行为预测
	 */
	public static final String Table_T_STU_SCORE_PREDICT_BEH = "T_STU_SCORE_PREDICT_BEH";
	/**
	 * 基础数据-毕业生去向-升造(升学)
	 */
	public static final String CODE_GRADUATE_DIRECTION_ID_02="02";
	/**
	 * 生源质量
	 */
	public static final String TABLE_T_STU_SYZL="T_STU_ENCRUIT";
	/**
	 * 生源地默认选中要求的学生占比界限
	 */
	public static final Double ABSOLUTE_ORIGIN_SCALE = 0.8;
	/**
	 * 学霸标准-gpa达到这个值认为是学霸
	 */
	public static final double XB_GPA = 3.2;
	/**
	 * 学霸标准-成绩达到这个值认为是学霸
	 */
	public static final double XB_SCORE = 88;
	/**
	 * 学校所在省份行政区划id
	 */
	public static final String Code_Origin_Id_BenSheng = "410000";
	/**
	 * 高低消费-高低消费天标准类型
	 */
	public static final String Type_DayCost_Standard = "day";
	/**
	 * 高低消费-高低消费每次早餐标准类型
	 */
	public static final String Type_BreakFastCost_Standard = "breakfast";//高低消费天早餐标准类型
	/**
	 * 高低消费-高低消费每次午餐标准类型
	 */
	public static final String Type_LunchCost_Standard = "lunch";
	/**
	 * 高低消费-高低消费每次晚餐标准类型
	 */
	public static final String Type_DinnerCost_Standard = "dinner";
	/**
	 * 高低消费，如果计算的最后一个月的天数小于这个数，就舍弃，不认为他是一个月
	 */
	public static final int Cost_Month_Min = 10;
	/**
	 * 高低消费统计的交易类型
	 */
	public static final String Code_Card_Deal_Type ="'210','215'"; 
	/**
	 * 邮件发送-SMTP服务器
	 */
	public static final String MAIL_SMTP = "smtp.163.com"; //;
	/**
	 * 邮件发送-邮箱用户名
	 */
	public static final String MAIL_USERNAME = "18339919808@163.com";// "发信人";
	/**
	 * 邮件发送-邮箱密码
	 */
	public static final String MAIL_PASSWORK = "lijun5618";// "发信人密码";
	/**
	 * 转专业编码
	 */
	public static final String ChangeMajor_Code = "06";
	/**
	 * 研究生（硕士）就读学历code
	 */
	public static final String Code_Edu_Id_Stu_10="10";
	/**
	 * 博士就读学历code
	 */
	public static final String Code_Edu_Id_Stu_01="01";

	/**
	 * 下一层节点字段-代码（实际值是机构ID、班号）
	 */
	public static final String NEXT_LEVEL_COLUMN_CODE = "next_dept_code";
	/**
	 * 下一层节点字段-名称
	 */
	public static final String NEXT_LEVEL_COLUMN_NAME = "next_dept_name";
	/**
	 * 下一层节点字段-数量
	 */
	public static final String NEXT_LEVEL_COLUMN_COUNT = "next_dept_count";
	/**
	 * 下一层节点字段-排序
	 */
	public static final String NEXT_LEVEL_COLUMN_ORDER = "next_dept_order";
	
	private static long i = 1L; // 系统唯一值
	/**
	 * 获取下一个系统唯一值
	 * @return long
	 */
	public static long getNext(){
		if(i > 1000000000000000L) i=1L;
		return i++;
	}
	
	/**
	 * 新疆地区代码
	 */
	public static final String ORIGIN_ID_XJ = "'650000'";
	/**
	 * 少数民族代码（02 ~ 56）
	 */
	public static final String NATION_ID_Minority = "'02','03','04','05','06','07','08','09','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30','31','32','33','34','35','36','37','38','39','40','41','42','43','44','45','46','47','48','49','50','51','52','53','54','55','56'";
	/**
	 * 区分度的计算比例
	 */
	public static final Double  Math_Distinction_Scale = 0.27;
	/**
	 * 效度的计算比例
	 */
	public static final Double  Math_Validity_Scale = 0.33;
	/**
	 * 成绩预测分组接口-推荐预测参考课程的课程性质按优先级排序-从高到低
	 */
	public static final String[]  Code_Course_Nature_Code_Group = {"3","7","4","2","1"};
	/**
	 * 成绩预测分组接口-不推荐用作预测参考课程的课程性质-学科基础课
	 */
	public static final String[]  Code_Course_attr_Code_Group = {"1","2","3"};
}
