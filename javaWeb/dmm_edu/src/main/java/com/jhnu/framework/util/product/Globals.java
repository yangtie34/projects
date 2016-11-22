package com.jhnu.framework.util.product;
/**  
* 项目名称：jeecg
* 类名称：Globals   
* 类描述：  全局变量定义
* 创建人：jeecg      
* @version    
*
 */
public final class Globals {
	/**保存用户到SESSION */
	public static String USER_SESSION="USER_SESSION";
	public static String USER_EXPAND_INFO="USER_EXPAND_INFO";
	public static String USER_ERROR_SESSION="USER_ERROR_SESSION";
	public static String ROLE_SESSION="ROLE_SESSION";
	public static String DANGANID_SESSION="DANGANID_SESSION";
	public static String MARKET_USER_SESSION="MARKET_USER_SESSION";
	
	/**用户访问方式 */
	public static String REQUEST_DEVICE_PC="REQUEST_PC";
	public static String REQUEST_DEVICE_MOBILE="REQUEST_MOBILE"; 
	
	/**系统默认角色id*/
	public static Long ROLE_ADMIN_ID =1L;
	public static Long ROLE_TEACHER_ID = 2l;
	public static Long ROLE_STUDENT_ID = 3L;

	/**系统默认角色名称*/
	public static String ROLE_TEACHER = "teacher";
	public static String ROLE_STUDENT = "student";
	public static String ROLE_ADMIN = "admin";
	
	/**学期代码*/
	public static String TERM_FIRST = "01";
	public static String TERM_SECOND = "02";
	
	/**分割学年学期的月份**/
	public static String CUT_YEAR="09";
	public static String CUT_TERM="03";
	
	
	
	/**不及格分数线*/
	public static double FAIL_SCORE_LINE = 60d;
}
