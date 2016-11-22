package cn.gilight.framework.page;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
 
/**
* * 分页函数 * *
*
*/
public class Page{
private static final Logger logger = Logger.getLogger(Page.class);
public static final int NUMBERS_PER_PAGE = 30000;
// 一页显示的记录数
private int numPerPage=10;
// 记录总数
private int totalRows;
// 总页数
private int totalPages;
// 当前页码
private int currentPage=1;
// 起始行数
private int startIndex;
// 结束行数
private int lastIndex;
// 结果集存放List
private List<Map<String,Object>> resultList;
//结果集存放List
private List<?> resultListObject;
// JdbcTemplate jTemplate
private JdbcTemplate jTemplate;
//private Pagination pagination = new Pagination();
/**
* 每页显示10条记录的构造函数,使用该函数必须先给Pagination设置currentPage，jTemplate初值
*
* @param sql
* oracle语句
*/
 
 
public Page() {
   
}
 
public Page(int currentPage){
   this.currentPage = currentPage;
   
}
public Page(String sql,JdbcTemplate jdbcTemplate) {
   this.jTemplate=jdbcTemplate;
   if (jTemplate == null) {
    throw new IllegalArgumentException(
      "com.deity.ranking.util.Pagination.jTemplate is null,please initial it first. ");
   } else if (sql.equals("")) {
    throw new IllegalArgumentException(
      "com.deity.ranking.util.Pagination.sql is empty,please initial it first. ");
   }
   new Page(sql, currentPage, NUMBERS_PER_PAGE, jTemplate,null);
}
 
/**
* 分页构造函数
*
* @param sql
*            根据传入的sql语句得到一些基本分页信息
* @param currentPage
*            当前页
* @param numPerPage
*            每页记录数
* @param jTemplate
*            JdbcTemplate实例
*/

public Page(String sql, int currentPage, int numPerPage,JdbcTemplate jTemplate,Integer totalRow) {
	this.jTemplate=jTemplate;
   this.currentPage = currentPage;
   if (jTemplate == null){
    throw new IllegalArgumentException(
      "com.deity.ranking.util.Pagination.jTemplate is null,please initial it first. ");
   } else if (sql == null || sql.equals("")) {
    throw new IllegalArgumentException(
      "com.deity.ranking.util.Pagination.sql is empty,please initial it first. ");
   }
   // 设置每页显示记录数
   setNumPerPage(numPerPage);
   // 设置要显示的页数
   setCurrentPage(currentPage);
   // 计算总记录数
   StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
   totalSQL.append(sql);
   totalSQL.append(" ) totalTable ");
   // 给JdbcTemplate赋值
   // 总记录数
   if(totalRow!=null && totalRow!=0){
	   setTotalRows(totalRow);
   }else{
	   setTotalRows(jTemplate.queryForObject(totalSQL.toString(),Integer.class));
   }
   // 计算总页数
   setTotalPages();
   // 计算起始行数
   setStartIndex();
   // 计算结束行数
   setLastIndex();
   // 构造oracle数据库的分页语句
   StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
   paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
   paginationSQL.append(sql);
   paginationSQL.append("　) temp where ROWNUM <= " + lastIndex);
   paginationSQL.append(" ) WHERE　num > " + startIndex);
   // 装入结果集
   logger.info("分页查询："+paginationSQL);
   setResultList(jTemplate.queryForList(paginationSQL.toString()));
}

/**
* 分页构造函数
*
* @param sql
*            根据传入的sql语句得到一些基本分页信息
* @param currentPage
*            当前页
* @param numPerPage
*            每页记录数
* @param jTemplate
*            JdbcTemplate实例
* @param totalRow 总数据数
* @param sort 排序字段
* @param isAsc 是否为升序
*/
public Page(String sql, int currentPage, int numPerPage,JdbcTemplate jTemplate,Integer totalRow,String sort,boolean isAsc) {
	this.jTemplate=jTemplate;
   this.currentPage = currentPage;
   if (jTemplate == null){
    throw new IllegalArgumentException(
      "com.deity.ranking.util.Pagination.jTemplate is null,please initial it first. ");
   } else if (sql == null || sql.equals("")) {
    throw new IllegalArgumentException(
      "com.deity.ranking.util.Pagination.sql is empty,please initial it first. ");
   }
   // 设置每页显示记录数
   setNumPerPage(numPerPage);
   // 设置要显示的页数
   setCurrentPage(currentPage);
   // 计算总记录数
   StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
   totalSQL.append(sql);
   totalSQL.append(" ) totalTable ");
   // 给JdbcTemplate赋值
   // 总记录数
   if(totalRow!=null && totalRow!=0){
	   setTotalRows(totalRow);
   }else{
	   setTotalRows(jTemplate.queryForObject(totalSQL.toString(),Integer.class));
   }
   // 计算总页数
   setTotalPages();
   // 计算起始行数
   setStartIndex();
   // 计算结束行数
   setLastIndex();
   // 构造oracle数据库的分页语句
   StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
   paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
   if(StringUtils.hasLength(sort)){
	   paginationSQL.append(" SELECT * FROM ("+sql+") ORDER BY "+sort+(isAsc==true?" ":" desc ") );
   }else{
	   paginationSQL.append(sql);
   }
   paginationSQL.append("　) temp where ROWNUM <= " + lastIndex);
   paginationSQL.append(" ) WHERE　num > " + startIndex);
   // 装入结果集
   logger.info("分页查询："+paginationSQL);
   setResultList(jTemplate.queryForList(paginationSQL.toString()));
}

/**
* 分页构造函数
*
* @param sql
*            根据传入的sql语句得到一些基本分页信息
* @param currentPage
*            当前页
* @param numPerPage
*            每页记录数
* @param jTemplate
*            JdbcTemplate实例
*/
@SuppressWarnings({ "unchecked", "rawtypes" })
public Page(String sql, int currentPage, int numPerPage,JdbcTemplate jTemplate,Integer totalRow,Object o) {
	this.jTemplate=jTemplate;
   this.currentPage = currentPage;
   if (jTemplate == null){
    throw new IllegalArgumentException(
      "com.deity.ranking.util.Pagination.jTemplate is null,please initial it first. ");
   } else if (sql == null || sql.equals("")) {
    throw new IllegalArgumentException(
      "com.deity.ranking.util.Pagination.sql is empty,please initial it first. ");
   }
   // 设置每页显示记录数
   setNumPerPage(numPerPage);
   // 设置要显示的页数
   setCurrentPage(currentPage);
   // 计算总记录数
 //  Long l=System.currentTimeMillis();
  // System.out.println("开始查看总条数:"+l);
   StringBuffer totalSQL = new StringBuffer(" SELECT count(*) FROM ( ");
   totalSQL.append(sql);
   totalSQL.append(" ) totalTable ");
   // 给JdbcTemplate赋值
   // 总记录数
   if(totalRow!=null && totalRow!=0){
	   setTotalRows(totalRow);
   }else{
	   setTotalRows(jTemplate.queryForObject(totalSQL.toString(),Integer.class));
   }
 //  Long tl=System.currentTimeMillis();
//   System.out.println("查询总条数用时："+(tl-l));
   // 计算总页数
   setTotalPages();
   // 计算起始行数
   setStartIndex();
   // 计算结束行数
   setLastIndex();
   // 构造oracle数据库的分页语句
   StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
   paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
   paginationSQL.append(sql);
   paginationSQL.append("　) temp where ROWNUM <= " + lastIndex);
   paginationSQL.append(" ) WHERE　num > " + startIndex);
   // 装入结果集
   logger.info("分页查询："+paginationSQL);
   
   setResultListObject(jTemplate.query(paginationSQL.toString(), (RowMapper) new BeanPropertyRowMapper(o.getClass())));
 //  Long tl2=System.currentTimeMillis();
//   System.out.println("分页查询用时："+(tl2-tl));
}
 
/**
* @param args
*/
public int getCurrentPage() {
   return currentPage;
}
 
public void setCurrentPage(int currentPage) {
   this.currentPage = currentPage;
}
 
public int getNumPerPage() {
   return numPerPage;
}
 
public void setNumPerPage(int numPerPage) {
   this.numPerPage = numPerPage;
}
 
 
public int getTotalPages() {
   return totalPages;
}
 
// 计算总页数
public void setTotalPages() {
   if(totalRows % numPerPage == 0){
   this.totalPages = totalRows / numPerPage;
   }else{
   this.totalPages= (totalRows / numPerPage) + 1;
   }
}
 
public int getTotalRows() {
   return totalRows;
}
 
public void setTotalRows(int totalRows) {
   this.totalRows = totalRows;
}
 
public int getStartIndex() {
   return startIndex;
}
 
public void setStartIndex() {
   this.startIndex = (currentPage - 1) * numPerPage;
}
 
public int getLastIndex() {
   return lastIndex;
}
 
public void setJTemplate(JdbcTemplate template) {
   jTemplate = template;
}
 
// 计算结束时候的索引
 
public void setLastIndex() {
   if (totalRows < numPerPage) {
    this.lastIndex = totalRows;
   } else if ((totalRows % numPerPage == 0)
     || (totalRows % numPerPage != 0 && currentPage < totalPages)) {
    this.lastIndex = currentPage * numPerPage;
   } else if (totalRows % numPerPage != 0 && currentPage == totalPages) {//最后一页
    this.lastIndex = totalRows;
   }
}
 
public List<Map<String,Object>> getResultList() {
   return resultList;
}
 
public void setResultList(List<Map<String,Object>> resultList) {
   this.resultList = resultList;
}

public List<?> getResultListObject() {
	return resultListObject;
}

public void setResultListObject(List<?> resultListObject) {
	this.resultListObject = resultListObject;
}
 
}