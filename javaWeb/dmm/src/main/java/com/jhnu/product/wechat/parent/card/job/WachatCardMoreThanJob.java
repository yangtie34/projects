package com.jhnu.product.wechat.parent.card.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.card.service.CardService;
import com.jhnu.product.wechat.parent.card.entity.TlWechatConsumAnalyze;
import com.jhnu.product.wechat.parent.card.service.WechatCardService;
import com.jhnu.util.common.MathUtils;

public class WachatCardMoreThanJob implements Job {
	@Autowired
	private WechatCardService wechatCardService;
	@Autowired
	private CardService cardService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		List<TlWechatConsumAnalyze> result = wechatCardService.getTlWechatConsumAnalyzes("rxyl", "05");
		Map<String,List<TlWechatConsumAnalyze>> temp = new HashMap<String,List<TlWechatConsumAnalyze>>();
		for(TlWechatConsumAnalyze ta : result){
			String sex = ta.getSex();
			if(temp.containsKey(sex)){
				temp.get(sex).add(ta);
			}else{
				List<TlWechatConsumAnalyze> temp1 = new ArrayList<TlWechatConsumAnalyze>();
				temp1.add(ta);
				temp.put(sex, temp1);
			}
		}
		
		Iterator<String> it = temp.keySet().iterator();
		while(it.hasNext()){
			List<TlWechatConsumAnalyze> temp2 = temp.get(it.next());
			// 先排序
			Collections.sort(temp2,new Comparator<TlWechatConsumAnalyze>(){
				@Override
				public int compare(TlWechatConsumAnalyze o1, TlWechatConsumAnalyze o2) {
					return o2.getXflxAvg().compareTo(o1.getXflxAvg());
				}
			} );
			
			// 计算
			for(int i=0,t=temp2.size();i<t;i++){
				TlWechatConsumAnalyze ta = temp2.get(i);
				double pm = (double)(t-i-1)/t*100;
				String mt = MathUtils.get2Point(pm)+"";
				ta.setMoreThan(mt);
			}
			
		}
		
		wechatCardService.updateTlWechatConsumAnalyzes(result);
		
	}
}
