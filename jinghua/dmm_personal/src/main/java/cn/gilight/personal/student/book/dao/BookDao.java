package cn.gilight.personal.student.book.dao;

import java.util.List;
import java.util.Map;

import cn.gilight.framework.page.Page;

public interface BookDao {

	public Page getBorrowList(String stu_id,Page page);

	public int getBorrowCounts(String stu_id);
	
	public Map<String,Object> getBorrowProportion(String stu_id);

	public List<Map<String,Object>> getRecommendBook(String stu_id);

	public List<Map<String, Object>> getBorrowType(String stu_id);

}
