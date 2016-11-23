package cn.gilight.product.book.dao;

import java.util.List;
import java.util.Map;

/**
 * 图书馆统计分析
 *
 */
public interface BookInfoDao {
	
	public Map<String,Object> getBooks(String schoolYear);
	
	public List<Map<String,Object>> getReaders(String schoolYear);
	
	public List<Map<String,Object>> getBookLangu(String schoolYear);
	
	public List<Map<String,Object>> getBookByType(String schoolYear);
	
	public List<Map<String,Object>> getBooksTrend();
	
	public List<Map<String,Object>> getReadersTrend();
	
	public List<Map<String,Object>> getBookLanguTrend();

	public List<Map<String, Object>> getBookState(String schoolYear);

	public List<Map<String, Object>> getBookStateTrend();
	
}
