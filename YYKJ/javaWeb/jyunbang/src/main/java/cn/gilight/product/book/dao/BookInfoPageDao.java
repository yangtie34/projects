package cn.gilight.product.book.dao;

import cn.gilight.framework.page.Page;


/**
 * 图书馆统计分析
 *
 */
public interface BookInfoPageDao {
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
	 * 通过学年获取所有读者
	 * @param currentPage
	 * @param numPerPage
	 * @param schoolYear
	 * @return
	 * tl_book_reader_detil_year 表所有内容
	 */
	public Page getReaderBySchoolYear(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String schoolYear);
	
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
	 * 通过学年和藏书类别获取所有图书
	 * @param currentPage
	 * @param numPerPage
	 * @param store 藏书类别
	 * @param schoolYear
	 * @return
	 * NO_,NAME_,ISBN,PERSS,BIRTH_DATE,WRITER,PRICE,STORE_DATE,STORE_CODE,STATE_CODE,STATE_NAME,STORE_NAME<BR>
	 * 图书ID，图书名称，ISBN，出版社，出版日期，出版人，图书价格，藏书日期，藏书代码，状态代码，状态名称，藏书名称
	 */
	public Page getBookByStore(int currentPage ,int numPerPage,int totalRow,String sort,boolean isAsc,String store,String schoolYear);
	public Page getBookByState(int currentPage, int numPerPage, int totalRow,String sort,boolean isAsc,String state, String string);
}
