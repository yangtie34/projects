package cn.gilight.personal.student.book.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.page.Page;
import cn.gilight.personal.student.book.dao.BookDao;
import cn.gilight.personal.student.book.service.BookService;

@Service("bookService")
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;
	
	@Override
	public int getBorrowCounts(String stu_id) {
		return bookDao.getBorrowCounts(stu_id);
	}

	@Override
	public Map<String, Object> getBorrowProportion(String stu_id) {
		return bookDao.getBorrowProportion(stu_id);
	}

	@Override
	public List<Map<String,Object>> getRecommendBook(String stu_id) {
		return bookDao.getRecommendBook(stu_id);
	}

	@Override
	public Page getBorrowList(String stu_id,Page page) {
		return bookDao.getBorrowList(stu_id,page);
	}

	@Override
	public List<Map<String, Object>> getBorrowType(String stu_id) {
		return bookDao.getBorrowType(stu_id);
	}

}
