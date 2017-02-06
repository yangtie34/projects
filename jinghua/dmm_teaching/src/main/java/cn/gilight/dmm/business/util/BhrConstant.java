package cn.gilight.dmm.business.util;

/**
 * 学生行为常量
 * @author hanpl
 *
 */
public class BhrConstant {
	/**
	 * 学生行为-时间维度-季节时间类型
	 */
	public final static String[][] Season_Date ={{"05-01","summer"},{"10-01","winter"}};
	
	/**
	 * 学生行为-时间维度-类型-上下午分割时间
	 */
	public final static String AMPM_TIME ="12";
	/**
	 * 学生行为-时间维度-类型-上午进出图书馆时间
	 */
	public final static String AM_BOOK_RKE ="ambookrke";
	/**
	 * 学生行为-时间维度-类型-下午进出图书馆时间
	 */
	public final static String PM_BOOK_RKE ="pmbookrke";
	/**
	 * 学生行为-时间维度-类型-早上出宿舍时间
	 */
	public final static String OUT_DORM_RKE ="outdormrke";
	/**
	 * 学生行为-时间维度-类型-晚上回宿舍时间
	 */
	public final static String IN_DORM_RKE ="indormrke";
	/**
	 * 学生行为-时间维度-类型-早餐时间
	 */
	public static final String T_STU_BEHTIME_TYPE_BREAKFAST = "breakfast";
	/**
	 * 学生行为-时间维度-类型-午餐时间
	 */
	public static final String T_STU_BEHTIME_TYPE_LUNCH = "lunch";
	/**
	 * 学生行为-时间维度-类型-晚餐时间
	 */
	public static final String T_STU_BEHTIME_TYPE_DINNER= "dinner";
	/**
	 * 学生行为-时间维度-学生类型-学霸
	 */
	public static final String T_STU_BEHTIME_STUTYPE_SMART= "smart";
	/**
	 * 学生行为-时间维度-统计周期
	 */
	public static final int[] T_STU_BEHTIME_START_END= {5,24};
	/**
	 * 学生行为-时间维度-表名
	 */
	public enum BHRTIME_Table {
		/** 早餐 */ Breakfast(Constant.TABLE_T_STU_BEHAVIOR_TIME,T_STU_BEHTIME_TYPE_BREAKFAST,"早餐"), 
		/** 午餐 */ Lunch(Constant.TABLE_T_STU_BEHAVIOR_TIME,T_STU_BEHTIME_TYPE_LUNCH,"午餐"),
		/** 晚餐 */ Dinner(Constant.TABLE_T_STU_BEHAVIOR_TIME,T_STU_BEHTIME_TYPE_DINNER,"晚餐"),
		/** 上午进出图书馆 */ Ambookrke(Constant.TABLE_T_STU_BEHAVIOR_TIME,AM_BOOK_RKE,"图书馆"), 
		/** 下午进出图书馆 */ Pmbookrke(Constant.TABLE_T_STU_BEHAVIOR_TIME,PM_BOOK_RKE,"图书馆"),
		/** 早上出宿舍 */ Outdormrke(Constant.TABLE_T_STU_BEHAVIOR_TIME,OUT_DORM_RKE,"宿舍出门"),
		/** 晚上回宿舍  */ Indormrke(Constant.TABLE_T_STU_BEHAVIOR_TIME,IN_DORM_RKE,"回宿舍"); 
		private String table; private String type_; private String name;
		BHRTIME_Table(String table, String type_,String name){ this.table = table;this.type_=type_;this.name=name; }
		public String getTable(){ return table; }
		public String getType(){ return type_; }
		public String getName(){ return name; }
	}
}
