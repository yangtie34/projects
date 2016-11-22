package cn.gilight.personal.student.four.service;

import java.util.List;
import java.util.Map;

public interface FourBookService {

	//学生借书
	public Map<String, Object> borrowBook(String username);
	
	//某学期借书
	public List<Map<String, Object>> myBorrow(String username);
	
	//进出图书馆次数
	public Map<String, Object> inOutLibr(String username);
	
	//本届人均进出与我的进出对比
	public List<Map<String, Object>> myLibrs(String username);
}
