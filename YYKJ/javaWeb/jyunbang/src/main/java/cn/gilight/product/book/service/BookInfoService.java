package cn.gilight.product.book.service;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

/**
 * 图书馆统计分析
 *
 */
public interface BookInfoService {
	
	
	/**
	 * 获取当前图书馆信息
	 * @return 
	 * Map内容：<br>
	 * Key:NOWBOOKS   value:当前图书数量<br>
	 * Key:NOWMONEYS  value:当前图书价值<br>
	 * Key:NEWBOOKS   value:新增图书数量<br>
	 * Key:NEWMONEYS  value:新增图书价值<br>
	 * Key:NOWREADERS value:当前读者数量<br>
	 * Key:PEOPLEHAS  value:当前人均保有量<br>
	 */
	public Map<String,Object> getNowLibraryCount();
	
	/**
	 * 获取当前中外文书籍对比
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE   value:类别ID<br>
	 * Key:NAME   value:语言类别<br>
	 * Key:VALUE  value:类别数量<br>
	 */
	
	public List<Map<String,Object>> getNowBookLangu();
	
	/**
	 * 获取中外文书籍对比趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:SCHOOLYEAR  value:学年<br>
	 * Key:CODE   value:ID<br>
	 * Key:NAME   value:语言类别<br>
	 * Key:VALUE  value:类别数量<br>
	 */
	public List<Map<String,Object>> getBookLanguTrend();
	/**
	 * 获取当前分状态书籍对比
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE   value:类别ID<br>
	 * Key:NAME   value:状态<br>
	 * Key:VALUE  value:类别数量<br>
	 */
	
	public List<Map<String,Object>> getNowBookState();
	
	/**
	 * 获取分状态书籍对比趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:SCHOOLYEAR  value:学年<br>
	 * Key:CODE   value:ID<br>
	 * Key:NAME   value:状态<br>
	 * Key:VALUE  value:类别数量<br>
	 */
	public List<Map<String,Object>> getBookStateTrend();
	
	/**
	 * 获取当前读者人员对比
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE   value:名称ID<br>
	 * Key:NAME   value:读者名称<br>
	 * Key:VALUE  value:读者人数<br>
	 */
	public List<Map<String,Object>> getNowReader();
	
	/**
	 * 获取读者人员对比趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:schoolYear  value:学年<br>
	 * Key:CODE   value:名称ID<br>
	 * Key:NAME   value:读者名称<br>
	 * Key:VALUE  value:读者人数<br>
	 */
	public List<Map<String,Object>> getReadersTrend();
	
	
	/**
	 * 获取当前图书藏书类别对比
	 * @return
	 * List中Map内容：<br>
	 * Key:CODE   value:名称ID<br>
	 * Key:NAME   value:字段名称<br>
	 * Key:BOOKS  value:藏书数量<br>
	 * Key:MONEYS  value:藏书价值<br>
	 * Key:PEOPLEHASRATE  value:保障率(%)<br>
	 */
	public List<Map<String,Object>> getNowBookByType();
	
	/**
	 * 获取藏书数量与价值以及增长数的趋势
	 * @return
	 * List中Map内容：<br>
	 * Key:SCHOOLYEAR  value:学年<br>
	 * Key:BOOKS  value:藏书数量<br>
	 * Key:MONEYS  value:藏书价值<BR>
	 * KEY:UPBOOKS  value:增长藏书数量<br>
	 * Key:UPMONEYS  value:增长藏书价值<br>
	 */
	public List<Map<String,Object>> getBooksTrend();
	

	/**
	 * 获取所有图书
	 * @param currentPage
	 * @param numPerPage
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getAllBook(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc);
	
	/**
	 * 获取当前所有读者
	 * @param currentPage
	 * @param numPerPage
	 * @param schoolYear
	 * @return
	 * tl_book_reader_detil_year 表所有内容
	 */
	public Page getNowReader(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc);
	
	/**
	 * 获取当前所有图书
	 * @param currentPage
	 * @param numPerPage
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getNowBook(int currentPage, int numPerPage,int totalRow,String sort,boolean isAsc);
	
	/**
	 * 通过学年获取所有图书
	 * @param currentPage
	 * @param numPerPage
	 * @param schoolYear
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getBookBySchoolYear(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String schoolYear);
	
	/**
	 * 通过学年获取所有新增图书
	 * @param currentPage
	 * @param numPerPage
	 * @param schoolYear
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getUpBookBySchoolYear(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String schoolYear);
	
	/**
	 * 通过学年和语言类型获取所有图书
	 * @param currentPage
	 * @param numPerPage
	 * @param isCN 是否是中文
	 * @param schoolYear
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getBookByLanguage(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isCN,String schoolYear);
	
	/**
	 * 通过学年和读者类型获取所有读者
	 * @param currentPage
	 * @param numPerPage
	 * @param type	读者类型
	 * @param schoolYear
	 * @return
	 * tl_book_reader_detil_year 表所有内容
	 */
	public Page getReaderByType(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String type,String schoolYear);
	
	/**
	 * 通过当前学年和语言类型获取所有图书
	 * @param currentPage
	 * @param numPerPage
	 * @param isCN 是否是中文
	 * @param schoolYear
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getNowBookByLanguage(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,boolean isCN);
	
	/**
	 * 通过当前学年和语言类型获取所有图书
	 * @param currentPage
	 * @param numPerPage
	 * @param isCN 是否是中文
	 * @param schoolYear
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getNowBookByState(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String state);
	
	/**
	 * 通过当前学年和读者类型获取所有读者
	 * @param currentPage
	 * @param numPerPage
	 * @param type	读者类型
	 * @param schoolYear
	 * @return
	 * tl_book_reader_detil_year 表所有内容
	 */
	public Page getNowReaderByType(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String type);
	
	/**
	 * 通过当前学年和藏书类别获取所有图书
	 * @param currentPage
	 * @param numPerPage
	 * @param store 藏书类别
	 * @param schoolYear
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getNowBookByStore(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String store);

}
