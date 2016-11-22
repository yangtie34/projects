package com.jhnu.product.four.book.job;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.book.service.FourBookService;


@DisallowConcurrentExecution
public class BookAllBorrowJob implements Job {

	@Autowired
	private FourBookService fourBookService;
	
	private static final Logger logger = Logger.getLogger(BookAllBorrowJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存借书统计Log");
		try {
			fourBookService.saveAllBorrowLogJob();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("保存借书统计Log完毕");
	}
	
}
