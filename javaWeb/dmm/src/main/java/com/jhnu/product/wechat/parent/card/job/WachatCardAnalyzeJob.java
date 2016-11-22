package com.jhnu.product.wechat.parent.card.job;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.card.service.CardService;
import com.jhnu.product.wechat.parent.card.entity.TlWechatConsumAnalyze;
import com.jhnu.product.wechat.parent.card.service.WechatCardService;
import com.jhnu.util.common.MapUtils;

public class WachatCardAnalyzeJob implements Job {
	@Autowired
	private WechatCardService wechatCardService;
	@Autowired
	private CardService cardService;
	
	private static final Logger logger = Logger.getLogger(WachatCardAnalyzeJob.class);
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<Map<String,Object>> codes = cardService.getGroupCodes();
		logger.info("-----------------开始消费分析-----------");
		for(Map<String,Object> code : codes){
			String groupCode = MapUtils.getString(code, "VALUE");
			logger.info("-----------------计算消费数据-----------");
			Map<String, List<TlWechatConsumAnalyze>>  tempMap = wechatCardService.getWachatCardAnaData(groupCode);
			
			Iterator<String> it = tempMap.keySet().iterator();
			logger.info("-----------------保存消费分析数据-----------");
			while(it.hasNext()){
				wechatCardService.saveWachatCardAna2Log(tempMap.get(it.next()));
				logger.info("-----------------保存消费分析数据结束-----------");
			}
		}
		logger.info("-----------------结束消费分析-----------");
	}
}
