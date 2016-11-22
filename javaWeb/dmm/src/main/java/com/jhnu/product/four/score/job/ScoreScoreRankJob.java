package com.jhnu.product.four.score.job;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.four.score.service.FourScoreService;


@DisallowConcurrentExecution
public class ScoreScoreRankJob implements Job {

	@Autowired
	private FourScoreService fourScoreService;
	
	private static final Logger logger = Logger.getLogger(ScoreScoreRankJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("开始保存考试名次");
		ArrayList<String> list=new ArrayList<String>();
		list.add("2010-2011");
		list.add("2011-2012");
		list.add("2012-2013");
		list.add("2013-2014");
		list.add("2014-2015");
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < 2; j++) {
				if(j==0){
					fourScoreService.saveScoreRankJob(list.get(i), "01");
				}else{
					fourScoreService.saveScoreRankJob(list.get(i), "02");
				}
			}
		}
		logger.info("完毕保存考试名次");
	}
}
