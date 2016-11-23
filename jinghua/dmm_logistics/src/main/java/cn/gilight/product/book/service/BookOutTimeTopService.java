package cn.gilight.product.book.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 图书逾期排名统计分析
 *
 */
public interface BookOutTimeTopService {
	
	/**
	 * 图书逾期Top10排名
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:rank_		value:名次<br>
	 * Key:no_			value:上榜者ID<br>
	 * Key:name			value:上榜者名称<br>
	 * key:ofName 		value:所属名称<br>
	 * key:ofId			value:所属ID<br>
	 * key:outtime_num	value:借书次数<br>
	 * key:topNum		value:上榜次数<br>
	 */
	public Page getOutTimeTop(int currentPage ,int numPerPage,int totalRow,String type,String numOrDay,Map<String,String> ofId,String startDate,String endDate,int rank);
	
	/**
	 * 类型对比
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	   value:ID<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeTopByPeopleType(String type,String numOrDay,Map<String,String> ofId,String startDate,String endDate,int rank);
	
	/**
	 * 类型对比趋势
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:code   	   value:ID<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeTopByPeopleTypeTrend(String type,String numOrDay,Map<String,String> ofId,int rank);
	
	/**
	 * 性别对比
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	   value:ID<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeTopBySex(String type,String numOrDay,Map<String,String> ofId,String startDate,String endDate,int rank);
	
	/**
	 * 性别对比趋势
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:code   	   value:ID<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeTopBySTrend(String type,String numOrDay,Map<String,String> ofId,int rank);
	
	/**
	 * 年级对比
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	   value:ID<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeTopByGrade(String type,String numOrDay,Map<String,String> ofId,String startDate,String endDate,int rank);
	
	/**
	 * 年级对比趋势
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:code   	   value:ID<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeTopByGradeTrend(String type,String numOrDay,Map<String,String> ofId,int rank);
	
	/**
	 * 所属情况对比
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @param startDate 'yyyy-MM' (含)
	 * @param endDate 	'yyyy-MM' (含)
	 * @return
	 * List中Map内容：<br>
	 * Key:code   	   value:ID<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeTopByOf(String type,String numOrDay,Map<String,String> ofId,String startDate,String endDate,int rank);
	
	/**
	 * 所属情况对比趋势
	 * @param type 查询类型，学生：stu,教师：tea，书籍：book
	 * @param ofId 	所属ID，学生、教师为单位，书籍为藏书类别
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:code   	   value:ID<br>
	 * Key:name   	   value:字段名称<br>
	 * Key:value       value:字段数值<br>
	 */
	public List<Map<String,Object>> getOutTimeTopByOfTrend(String type,String numOrDay,Map<String,String> ofId,int rank);
	
	/**
	 * 获取借阅列表
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param id
	 * @return
	 * tl_book_borrow_detil表所有字段
	 */
	public Page getOutTime(int currentPage ,int numPerPage,int totalRow,String type,String numOrDay,String startDate,String endDate,String id);
	
	/**
	 * 获取Top10名次列表
	 * @param type
	 * @param startDate
	 * @param endDate
	 * @param id
	 * @return
	 * school_year,year_,month_,rank_,borrow_num,id,name,ofid,ofname <br>
	 * 所处学年，年份，月份，名次，借阅次数，人员ID，人员名称，所属ID，所属名称
	 */
	public Page getTop(int currentPage ,int numPerPage,int totalRow,String type,String numOrDay,String startDate,String endDate,String id);
	
	/**
	 * 
	 * @param type
	 * @param ofId
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param value
	 * @return
	 * 
	 * type:stu<br>
	 * rank_,borrow_num,stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,GRADE,GRADE_NAME<br> 
	 * 名次，借阅次数，书名，学生ID，学生名称，学院ID，学院名称，专业ID，专业名称，班级ID，班级名称，学历ID，学历名称，性别代码，姓名名称，年级ID，所处年级<br>
	 *
	 * type:tea<br>
	 * rank_,borrow_num,TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME <br>
	 * 名次，借阅次数，书名，教师ID，教师名称，单位ID，单位名称，学历ID，学历名称，性别代码，姓名名称，职称ID，职称名称<br>
	 * 
	 */
	public Page getEdu(int currentPage ,int numPerPage,int totalRow,String type,String numOrDay,Map<String,String> ofId,String startDate,String endDate,int rank,String value);
	
	/**
	 * 
	 * @param type
	 * @param ofId
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param value
	 * @return
	 * type:stu<br>
	 * rank_,borrow_num,stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,GRADE,GRADE_NAME<br> 
	 * 名次，借阅次数，书名，学生ID，学生名称，学院ID，学院名称，专业ID，专业名称，班级ID，班级名称，学历ID，学历名称，性别代码，姓名名称，年级ID，所处年级<br>
	 *
	 * type:tea<br>
	 * rank_,borrow_num,TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME <br>
	 * 名次，借阅次数，书名，教师ID，教师名称，单位ID，单位名称，学历ID，学历名称，性别代码，姓名名称，职称ID，职称名称<br>
	 */
	public Page getSex(int currentPage ,int numPerPage,int totalRow,String type,String numOrDay,Map<String,String> ofId,String startDate,String endDate,int rank,String value);
	
	/**
	 * 
	 * @param type
	 * @param ofId
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @param value
	 * @return
	 * type:stu<br>
	 * rank_,borrow_num,stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,GRADE,GRADE_NAME<br> 
	 * 名次，借阅次数，书名，学生ID，学生名称，学院ID，学院名称，专业ID，专业名称，班级ID，班级名称，学历ID，学历名称，性别代码，姓名名称，年级ID，所处年级<br>
	 *
	 * type:tea<br>
	 * rank_,borrow_num,TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME <br>
	 * 名次，借阅次数，书名，教师ID，教师名称，单位ID，单位名称，学历ID，学历名称，性别代码，姓名名称，职称ID，职称名称<br>
	 */
	public Page getGrade(int currentPage ,int numPerPage,int totalRow,String type,String numOrDay,Map<String,String> ofId,String startDate,String endDate,int rank,String value);
	
	/**
	 * 
	 * @param type
	 * @param ofId
	 * @param startDate
	 * @param endDate
	 * @param rank
	 * @return
	 * type:stu<br>
	 * rank_,borrow_num,stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,GRADE,GRADE_NAME<br> 
	 * 名次，借阅次数，书名，学生ID，学生名称，学院ID，学院名称，专业ID，专业名称，班级ID，班级名称，学历ID，学历名称，性别代码，姓名名称，年级ID，所处年级<br>
	 *
	 * type:tea<br>
	 * rank_,borrow_num,TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME <br>
	 * 名次，借阅次数，书名，教师ID，教师名称，单位ID，单位名称，学历ID，学历名称，性别代码，姓名名称，职称ID，职称名称<br>
	 * 
	 * type:book<br>
	 * rank_,borrow_num,BOOK_NAME,STORE_CODE,STORE_NAME <br>
	 * 名次，借阅次数，书名，藏书类别，类别名称<br>
	 */
	public Page getOf(int currentPage ,int numPerPage,int totalRow,String type,String numOrDay,Map<String,String> ofId, Map<String, String> value,String startDate,String endDate,int rank);
}
