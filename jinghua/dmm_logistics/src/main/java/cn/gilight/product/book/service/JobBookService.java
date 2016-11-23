package cn.gilight.product.book.service;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;


public interface JobBookService {
	
	public JobResultBean initBookYear();
	
	public JobResultBean initBookTypeYear();
	
	public JobResultBean initBookReaderYear();
	
	public JobResultBean initBookReaderDetilYear();
	
	public JobResultBean initBookBorrowTypeMonth();
	
	public JobResultBean initBookBorrowPeopleMonth();
	
	public JobResultBean initBookOutTimeTypeMonth();
	
	public JobResultBean initBookOutTimePeopleMonth();
	
	public JobResultBean initBookBorrowStuMonth();
	
	public JobResultBean initBookBorrowTeaMonth();
	
	public JobResultBean initBookBorrowBookMonth();
	
	public JobResultBean initBookOutTimeStuMonth();
	
	public JobResultBean initBookOutTimeTeaMonth();
	
	public JobResultBean initBookOutTimeBookMonth();
	
	public JobResultBean initBorrowTop();
	
	public JobResultBean initBorrowDetil();
	
	public JobResultBean borrowDetilByThisYear();
	
	public JobResultBean borrowDetilByTwoMonth();
	
	public JobResultBean borrowByLastDayByDay();
	
	
	public JobResultBean bookYearByDay();
	
	public JobResultBean bookTypeYearByDay();
	
	public JobResultBean bookReaderYearByDay();
	
	public JobResultBean bookReaderDetilYearByDay();
	
	public JobResultBean bookBorrowTypeMonthByDay();
	
	public JobResultBean bookBorrowPeopleMonthByDay();
	
	public JobResultBean bookOutTimeTypeMonthByDay();
	
	public JobResultBean bookOutTimePeopleMonthByDay();
	
	public JobResultBean bookBorrowStuMonthByDay();
	
	public JobResultBean bookBorrowTeaMonthByDay();
	
	public JobResultBean bookBorrowBookMonthByDay();

	public JobResultBean bookOutTimeStuMonthByDay();
	
	public JobResultBean bookOutTimeTeaMonthByDay();
	
	public JobResultBean bookOutTimeBookMonthByDay();
	
	public JobResultBean borrowTopByYear();
	
	
	//******************改版的JOB***********************
	
	public JobResultBean initBookStu();
	
	public JobResultBean initBookMonth();
	
	public JobResultBean initBorrowStu();
	
	
	public JobResultBean updateBookStu();
	
	public JobResultBean updateBookMonth();
	
	public JobResultBean updateBorrowStu();
	
	
}
