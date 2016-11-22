package cn.gilight.product.book.dao;

import java.util.Map;

import cn.gilight.framework.page.Page;


/**
 * 逾期还书平均逾期时长统计
 *
 */
public interface BookOutTimeDayTeaTopPageDao {
	/**
	 * 获取根据人员ID获取所有借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param bookName
	 * @return
	 * tl_book_borrow_detil表所有字段
	 * 
	 */
	public Page getAllOutTime(int currentPage ,int numPerPage,int totalRow,String peopleId);
	
	/**
	 * 根据人员ID获取所有上榜TOP10次数列表
	 * @param currentPage
	 * @param numPerPage
	 * @param bookName
	 * @return
	 * school_year,year_,month_,days_rank,days,id,name,ofid,ofname<br>
	 * 所处学年，年份，月份，名次，借阅次数，人员ID，人员名称，所属ID，所属名称
	 */
	public Page getAllTop(int currentPage ,int numPerPage,int totalRow,String peopleId);
	
	/**
	 * 获取根据人员ID和起止时间获取所有借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param bookName
	 * @return
	 * tl_book_borrow_detil表所有字段
	 * 
	 */
	public Page getOutTimeByTime(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String peopleId);
	
	/**
	 * 根据人员ID和起止时间获取所有上榜TOP10次数列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param bookName
	 * @return
	 * school_year,year_,month_,days_rank,days,id,name,ofid,ofname<br>
	 * 所处学年，年份，月份，名次，借阅次数，人员ID，人员名称，所属ID，所属名称
	 * 
	 */
	public Page getTopByTime(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String peopleId);
	
	/**
	 * 根据教师学历和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,outtime_day,TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME <br>
	 * 名次，借阅次数，书名，教师ID，教师名称，单位ID，单位名称，学历ID，学历名称，性别代码，姓名名称，职称ID，职称名称
	 */
	public Page getEdu(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,Map<String,String> deptId,String value);
	
	/**
	 * 根据教师性别和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,outtime_day,TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME <br>
	 * 名次，借阅次数，书名，教师ID，教师名称，单位ID，单位名称，学历ID，学历名称，性别代码，姓名名称，职称ID，职称名称
	 */
	public Page getSex(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,Map<String,String> deptId,String value);
	
	/**
	 * 根据教师职称和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,outtime_day,TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME <br>
	 * 名次，借阅次数，书名，教师ID，教师名称，单位ID，单位名称，学历ID，学历名称，性别代码，姓名名称，职称ID，职称名称
	 */
	public Page getZc(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,Map<String,String> deptId,String value);
	
	/**
	 * 根据教师单位和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,outtime_day,TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME <br>
	 * 名次，借阅次数，书名，教师ID，教师名称，单位ID，单位名称，学历ID，学历名称，性别代码，姓名名称，职称ID，职称名称
	 */
	public Page getDept(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,Map<String,String> deptId,Map<String,String> value);
	
}
