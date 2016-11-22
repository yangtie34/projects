package cn.gilight.product.book.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.product.book.dao.BookInfoDao;
import cn.gilight.product.book.dao.BookInfoPageDao;
import cn.gilight.product.book.service.BookInfoService;

@Repository("bookInfoService")
public class BookInfoServiceImpl implements BookInfoService {
	
	@Autowired
	private BookInfoDao bookInfoDao;

	@Autowired
	private BookInfoPageDao bookInfoPageDao;
	
	@Override
	public Map<String, Object> getNowLibraryCount() {
		return bookInfoDao.getBooks(EduUtils.getSchoolYearTerm(new Date())[0]);
	}

	@Override
	public List<Map<String, Object>> getNowBookLangu() {
		return bookInfoDao.getBookLangu(EduUtils.getSchoolYearTerm(new Date())[0]);
	}

	@Override
	public List<Map<String, Object>> getBookLanguTrend() {
		return bookInfoDao.getBookLanguTrend();
	}
	@Override
	public List<Map<String, Object>> getNowBookState() {
		return bookInfoDao.getBookState(EduUtils.getSchoolYearTerm(new Date())[0]);
	}

	@Override
	public List<Map<String, Object>> getBookStateTrend() {
		return bookInfoDao.getBookStateTrend();
	}
	@Override
	public List<Map<String, Object>> getNowReader() {
		return bookInfoDao.getReaders(EduUtils.getSchoolYearTerm(new Date())[0]);
	}

	@Override
	public List<Map<String, Object>> getReadersTrend() {
		return bookInfoDao.getReadersTrend();
	}

	@Override
	public List<Map<String, Object>> getNowBookByType() {
		return bookInfoDao.getBookByType(EduUtils.getSchoolYearTerm(new Date())[0]);
	}

	@Override
	public List<Map<String, Object>> getBooksTrend() {
		return bookInfoDao.getBooksTrend();
	}

	@Override
	public Page getAllBook(int currentPage, int numPerPage,int totalRow) {
		return bookInfoPageDao.getAllBook(currentPage, numPerPage,totalRow);
	}

	@Override
	public Page getNowReader(int currentPage, int numPerPage,int totalRow) {
		return bookInfoPageDao.getReaderBySchoolYear(currentPage,numPerPage,totalRow, EduUtils.getSchoolYearTerm(new Date())[0]);
	}

	@Override
	public Page getNowBook(int currentPage, int numPerPage,int totalRow) {
		return bookInfoPageDao.getUpBookBySchoolYear(currentPage,numPerPage,totalRow, EduUtils.getSchoolYearTerm(new Date())[0]);
	}
	
	@Override
	public Page getBookBySchoolYear(int currentPage, int numPerPage,int totalRow,
			String schoolYear) {
		return bookInfoPageDao.getBookBySchoolYear(currentPage,numPerPage,totalRow, schoolYear);
	}

	@Override
	public Page getUpBookBySchoolYear(int currentPage, int numPerPage,int totalRow,
			String schoolYear) {
		return bookInfoPageDao.getUpBookBySchoolYear(currentPage,numPerPage,totalRow, schoolYear);
	}

	@Override
	public Page getNowBookByLanguage(int currentPage, int numPerPage,int totalRow,boolean isCN) {
		return bookInfoPageDao.getBookByLanguage(currentPage,numPerPage,totalRow, isCN, EduUtils.getSchoolYearTerm(new Date())[0]);
	}
	@Override
	public Page getNowBookByState(int currentPage, int numPerPage,int totalRow,String state) {
		return bookInfoPageDao.getBookByState(currentPage,numPerPage,totalRow, state, EduUtils.getSchoolYearTerm(new Date())[0]);
	}
	@Override
	public Page getBookByLanguage(int currentPage, int numPerPage,int totalRow,
			boolean isCN, String schoolYear) {
		return bookInfoPageDao.getBookByLanguage(currentPage,numPerPage,totalRow, isCN, schoolYear);
	}

	@Override
	public Page getNowReaderByType(int currentPage, int numPerPage,int totalRow, String type) {
		return bookInfoPageDao.getReaderByType(currentPage,numPerPage,totalRow, type, EduUtils.getSchoolYearTerm(new Date())[0]);
	}
	
	@Override
	public Page getReaderByType(int currentPage, int numPerPage,int totalRow, String type,
			String schoolYear) {
		return bookInfoPageDao.getReaderByType(currentPage,numPerPage,totalRow, type, schoolYear);
	}

	@Override
	public Page getNowBookByStore(int currentPage, int numPerPage,int totalRow, String store) {
		return bookInfoPageDao.getBookByStore(currentPage,numPerPage,totalRow, store, EduUtils.getSchoolYearTerm(new Date())[0]);
	}
	
	
}
