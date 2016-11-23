package cn.gilight.product.book.dao;

import java.util.Map;

import cn.gilight.framework.page.Page;


/**
 * 图书馆统计分析
 *
 */
public interface BookBorrowStuTopPageDao {
	/**
	 * 获取根据人员ID获取所有借书列表
	 * @param currentPage
	 * @param numPerPage
	 * @param bookName
	 * @return
	 * tl_book_borrow_detil表所有字段
	 * 
	 */
	public Page getAllBorrow(int currentPage ,int numPerPage,int totalRow,String peopleId);
	/**
	 * 根据人员ID获取所有上榜TOP10次数列表
	 * @param currentPage
	 * @param numPerPage
	 * @param bookName
	 * @return
	 * school_year,year_,month_,rank_,borrow_num,id,name,ofid,ofname<br>
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
	public Page getBorrowByTime(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String peopleId);
	
	/**
	 * 根据人员ID和起止时间获取所有上榜TOP10次数列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param bookName
	 * @return
	 * school_year,year_,month_,rank_,borrow_num,id,name,ofid,ofname<br>
	 * 所处学年，年份，月份，名次，借阅次数，人员ID，人员名称，所属ID，所属名称
	 * 
	 */
	public Page getTopByTime(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,String peopleId);
	
	/**
	 * 根据学生学历和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,borrow_num,stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,GRADE,GRADE_NAME <br>
	 * 名次，借阅次数，书名，学生ID，学生名称，学院ID，学院名称，专业ID，专业名称，班级ID，班级名称，学历ID，学历名称，性别代码，姓名名称，年级ID，所处年级
	 */
	public Page getEdu(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,Map<String,String> deptId,String value);
	
	/**
	 * 根据学生性别和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,borrow_num,stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,GRADE,GRADE_NAME <br>
	 * 名次，借阅次数，书名，学生ID，学生名称，学院ID，学院名称，专业ID，专业名称，班级ID，班级名称，学历ID，学历名称，性别代码，姓名名称，年级ID，所处年级
	 */
	public Page getSex(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,Map<String,String> deptId,String value);
	
	/**
	 * 根据学生年级和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,borrow_num,stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,GRADE,GRADE_NAME <br>
	 * 名次，借阅次数，书名，学生ID，学生名称，学院ID，学院名称，专业ID，专业名称，班级ID，班级名称，学历ID，学历名称，性别代码，姓名名称，年级ID，所处年级
	 */
	public Page getGrade(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,Map<String,String> deptId,String value);
	
	/**
	 * 根据学生所属ID和起止时间与名次获取列表
	 * @param currentPage
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param storeId
	 * @return
	 * rank_,borrow_num,stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,GRADE,GRADE_NAME <br>
	 * 名次，借阅次数，书名，学生ID，学生名称，学院ID，学院名称，专业ID，专业名称，班级ID，班级名称，学历ID，学历名称，性别代码，姓名名称，年级ID，所处年级
	 */
	public Page getDept(int currentPage ,int numPerPage,int totalRow,String startDate, String endDate,int rank,Map<String,String> deptId, Map<String, String> value);
	
}
