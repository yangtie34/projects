package com.jhnu.product.wechat.parent.card;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.card.service.CardService;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.wechat.parent.card.entity.TlWechatConsumAnalyze;
import com.jhnu.product.wechat.parent.card.service.WechatCardService;
import com.jhnu.spring.SpringTest;
import com.jhnu.util.common.MapUtils;

public class CardAnaDataInitTest extends SpringTest {
	@Autowired
	private WechatCardService wechatCardService;
	@Autowired
	private StuService stuService;
	@Autowired
	private CardService cardService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}
	
	@Test
	public void testaa(){
		List<Map<String,Object>> codes = cardService.getGroupCodes();
		for(Map<String,Object> code : codes){
			String groupCode = MapUtils.getString(code, "VALUE");
			Map<String, List<TlWechatConsumAnalyze>>  tempMap = wechatCardService.getWachatCardAnaData(groupCode);
			
			Iterator<String> it = tempMap.keySet().iterator();
			while(it.hasNext()){
				wechatCardService.saveWachatCardAna2Log(tempMap.get(it.next()));
			}
		}
	}
}
