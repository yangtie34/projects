package cn.gilight.product.book.dao;

import java.util.Map;

import cn.gilight.framework.enums.ShiroTagEnum;
import cn.gilight.framework.uitl.common.SqlUtil;


public class BookBorrowTjUtil {

	public static final  String BORROWSQL="select t.*,case when T.grade is null then T.DEPT_NAME else T.DEPT_NAME||'-'||T.MAJOR_NAME||'-'||T.CLASS_NAME end of_name,case when T.grade is null then T.DEPT_ID else T.DEPT_ID||'-'||T.MAJOR_ID||'-'||T.CLASS_ID end of_id from tl_book_borrow_detil t where 1=1 ";
	public static final String ORDERSQL="";//" order by t.borrow_time desc ";
	
	public static final String OUTTIMESQL="select t.*,case when T.grade is null then T.DEPT_NAME else T.DEPT_NAME||'-'||T.MAJOR_NAME||'-'||T.CLASS_NAME end of_name,case when T.grade is null then T.DEPT_ID else T.DEPT_ID||'-'||T.MAJOR_ID||'-'||T.CLASS_ID end of_id from tl_book_borrow_detil t where t.return_time>t.should_return_time ";
	public static final String OUTTIMEORDERSQL="";//" order by t.return_time desc ";
	
	public static String getDateTJ(String startDate,String endDate){
		String tj="and t.borrow_time>= '"+startDate+"' and t.borrow_time< '"+endDate+"' ";
		return tj;
	}
	
	
	public static String getReturnDateTJ(String startDate,String endDate){
		String tj="and t.return_time>= '"+startDate+"' and t.return_time< '"+endDate+"' ";
		return tj;
	}
	
	public static String getNewDateTJ(String startDate,String endDate){
		String tj=" and t.year_month>=to_date('"+startDate+"','yyyy-mm') and t.year_month<to_date('"+endDate+"','yyyy-mm') ";
		return tj;
	}
	
	public static String getNewBorrowDateTJ(String startDate,String endDate){
		String tj=" and t.BORROW_DATE>=to_date('"+startDate+"','yyyy-mm') and t.BORROW_DATE<to_date('"+endDate+"','yyyy-mm') ";
		return tj;
	}
	
	public static String getNewReturnDateTJ(String startDate,String endDate){
		String tj=" and t.RETURN_DATE>=to_date('"+startDate+"','yyyy-mm') and t.RETURN_DATE<to_date('"+endDate+"','yyyy-mm') ";
		return tj;
	}
	
	public static String getBorrowTopPage(String type,String people,String startDate,String endDate,Map<String,String> deptTeachs,Map<String,String> deptValue,String storeId,int rank,String tjSql){
		String sumAvg="",sumAvgTag="",table="",peo="",group="",tj="",shiroTag="";
		if("borrow".equals(type)){
			sumAvg=" sum(borrow_num) ";
			sumAvgTag=" borrow_num ";
			table="borrow";
			
		}else{
			if("num".equals(type)){
				sumAvg=" sum(nums) ";
				sumAvgTag=" outtime_num ";
				table="outtime";
			}else{
				sumAvg="avg(days)";
				sumAvgTag=" outtime_day ";
				table="outtime";
			}
		}
		if("stu".equals(people)){
			if("borrow".equals(type)){
				shiroTag=ShiroTagEnum.BOOK_RDT.getCode();
			}else{
				shiroTag=ShiroTagEnum.BOOK_ODT.getCode();
			}
			tj=SqlUtil.getDeptTeachTj(deptTeachs, shiroTag, null);
			tjSql+=SqlUtil.getDeptTeachTj(deptValue, shiroTag, null);
			peo="stu";
			group="stu_id,user_name,DEPT_ID,DEPT_NAME,MAJOR_ID,MAJOR_NAME,CLASS_ID,CLASS_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME ";
		}else if("tea".equals(people)){
			if("borrow".equals(type)){
				shiroTag=ShiroTagEnum.BOOK_RD.getCode();
			}else{
				shiroTag=ShiroTagEnum.BOOK_OD.getCode();
			}
			tj=SqlUtil.getDeptTj(deptTeachs, shiroTag, null);
			tjSql+=SqlUtil.getDeptTj(deptValue, shiroTag, null);
			peo="tea";
			group="TEA_ID,user_name,DEPT_ID,DEPT_NAME,EDU_ID,EDU_NAME,SEX_CODE,SEX_NAME,ZC_ID,ZC_NAME ";
		}else {
			tj=getBookTj(storeId);
			peo="book";
			group="BOOK_NAME,STORE_CODE,STORE_NAME ";
		}
			String sql="select * from (select dense_rank() over(order by "+sumAvg+" desc) rank_ ,"+sumAvg+sumAvgTag+", "+
					group+
			        "from tl_book_"+table+"_"+peo+"_month   "+
			        "where year_||'-'||month_>='"+startDate+"' and  year_||'-'||month_<'"+endDate+"' "+
			        tj+
			        "group by "+group+
			        ") where rank_<="+rank+tjSql+" order by rank_ ";
		return sql;
	}
	
	public static String getBookTj(String storeId){
		String tj="";
		if(storeId!=null && !"all".equals(storeId)){
			tj="and STORE_CODE='"+storeId+"' ";
		}
		return tj;
	}
	
}
