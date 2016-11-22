package cn.gilight.product.book;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.gilight.framework.SpringTest;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.product.book.dao.JobBookDao;
import cn.gilight.product.book.service.BookBorrowTopService;
import cn.gilight.product.book.service.JobBookService;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;


public class BookTest extends SpringTest{
	
	@Resource
	private JobBookService jobBookService;
	@Resource
	private JobBookDao jobBookDao;
	@Resource
	private BookBorrowTopService bookBorrowTopService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	//@Test
	public void initBook(){
		long startTime=0,endTime=0;
		JobResultBean jrb=new JobResultBean();
//		startTime=System.currentTimeMillis();jrb= jobBookService.initBookYear();
//		endTime=System.currentTimeMillis();System.out.println("1:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//105秒
//		startTime=System.currentTimeMillis();jrb= jobBookService.initBookTypeYear();
//		endTime=System.currentTimeMillis();System.out.println("2:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//135秒
//		startTime=System.currentTimeMillis();jrb= jobBookService.initBookReaderYear();
//		endTime=System.currentTimeMillis();System.out.println("3:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//2秒
//		startTime=System.currentTimeMillis();jrb= jobBookService.initBookReaderDetilYear();
//		endTime=System.currentTimeMillis();System.out.println("4:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//56秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookBorrowTypeMonth();
		endTime=System.currentTimeMillis();System.out.println("5:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//294秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookBorrowPeopleMonth();
		endTime=System.currentTimeMillis();System.out.println("6:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//101秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookOutTimeTypeMonth();
		endTime=System.currentTimeMillis();System.out.println("7:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//256秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookOutTimePeopleMonth();
		endTime=System.currentTimeMillis();System.out.println("8:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//94秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookBorrowStuMonth();
		endTime=System.currentTimeMillis();System.out.println("9:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//133秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookBorrowTeaMonth();
		endTime=System.currentTimeMillis();System.out.println("10:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//91秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookBorrowBookMonth();
		endTime=System.currentTimeMillis();System.out.println("11:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//234秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookOutTimeStuMonth();
		endTime=System.currentTimeMillis();System.out.println("12:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//125秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookOutTimeTeaMonth();
		endTime=System.currentTimeMillis();System.out.println("13:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//91秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBookOutTimeBookMonth();
		endTime=System.currentTimeMillis();System.out.println("14:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//238秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBorrowTop();
		endTime=System.currentTimeMillis();System.out.println("15:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//183秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBorrowDetil();
		endTime=System.currentTimeMillis();System.out.println("16:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//47秒
	}
	

//	@Test
	public void book(){
		long startTime=0,endTime=0;
		JobResultBean jrb=new JobResultBean();
		startTime=System.currentTimeMillis();jrb= jobBookService.bookYearByDay();
		endTime=System.currentTimeMillis();System.out.println("1:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//105秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookTypeYearByDay();
		endTime=System.currentTimeMillis();System.out.println("2:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//135秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookReaderYearByDay();
		endTime=System.currentTimeMillis();System.out.println("3:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//2秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookReaderDetilYearByDay();
		endTime=System.currentTimeMillis();System.out.println("4:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//56秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookBorrowTypeMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("5:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//294秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookBorrowPeopleMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("6:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//101秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookOutTimeTypeMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("7:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//256秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookOutTimePeopleMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("8:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//94秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookBorrowStuMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("9:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//133秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookBorrowTeaMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("10:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//91秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookBorrowBookMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("11:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//234秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookOutTimeStuMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("12:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//125秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookOutTimeTeaMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("13:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//91秒
		startTime=System.currentTimeMillis();jrb= jobBookService.bookOutTimeBookMonthByDay();
		endTime=System.currentTimeMillis();System.out.println("14:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//238秒
		startTime=System.currentTimeMillis();jrb= jobBookService.borrowTopByYear();
		endTime=System.currentTimeMillis();System.out.println("15:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//183秒
		startTime=System.currentTimeMillis();jrb= jobBookService.borrowByLastDayByDay();
		endTime=System.currentTimeMillis();System.out.println("16:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//47秒
		startTime=System.currentTimeMillis();jrb= jobBookService.initBorrowStu();
		endTime=System.currentTimeMillis();System.out.println("17:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");
	}

	//@Test
	public void testOne(){
		long startTime=0,endTime=0;
		JobResultBean jrb=new JobResultBean();
	//	startTime=System.currentTimeMillis();jrb= jobBookService.initBookStu();
	//	endTime=System.currentTimeMillis();System.out.println("1:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//74秒
		//startTime=System.currentTimeMillis();jrb= jobBookService.initBookMonth();
		/*
		endTime=System.currentTimeMillis();System.out.println("2:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//
		startTime=System.currentTimeMillis();jrb= jobBookService.initBorrowStu();
		endTime=System.currentTimeMillis();System.out.println("3:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//
*/	
		startTime=System.currentTimeMillis();
		String schoolYear = EduUtils.getSchoolYearTerm(new Date())[0];
			jobBookDao.updateBookStateYear(schoolYear);
		endTime=System.currentTimeMillis();System.out.println("1:"+jrb.getMsg()+"。"+(endTime-startTime)/1000+"秒");//105秒
	
		}
	@Test
	public void one(){
		jobBookService.updateBorrowStu();
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
