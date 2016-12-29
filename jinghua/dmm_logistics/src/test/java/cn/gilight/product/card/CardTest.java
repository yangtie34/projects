package cn.gilight.product.card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cn.gilight.framework.SpringTest;
import cn.gilight.product.card.service.CardTrendService;
import cn.gilight.product.card.service.CardUsedService;
import cn.gilight.product.card.service.DiningRoomService;
import cn.gilight.product.card.service.HabitPayTypeService;
import cn.gilight.product.card.service.HabitStuTypeService;
import cn.gilight.product.card.service.JobCardService;
import cn.gilight.product.card.service.PayPowerService;
import cn.gilight.product.card.service.RechargeService;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;


public class CardTest extends SpringTest{
	
	@Resource
	private CardUsedService cardUsedService;
	@Resource
	private DiningRoomService diningRoomService;
	@Resource
	private HabitPayTypeService habitPayTypeService;
	@Resource
	private HabitStuTypeService habitStuTypeService;
	@Resource
	private PayPowerService payPowerService;
	@Resource
	private RechargeService rechargeService;
	@Resource
	private JobCardService jobCardService;
	@Resource
	private CardTrendService cardTrendService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
//	@Test
	public void testOne(){
		String startDate="2013-09";
		String endDate="2013-10";
		Map<String,String> deptTeach=new HashMap<String, String>();
		deptTeach.put("istrue", "1");
		deptTeach.put("level", "0");
		Map<String,Object> map=new HashMap<String, Object>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		map=cardUsedService.getCardUsed(startDate, endDate, deptTeach);
		printMap(map);
		list=cardUsedService.getCardUsedByDept(startDate, endDate, deptTeach);
		printList(list);
		list=cardUsedService.getCardUsedByEdu(startDate, endDate, deptTeach);
		printList(list);
		list=cardUsedService.getCardUsedBySex(startDate, endDate, deptTeach);
		printList(list);

		list=cardUsedService.getNoCardUsed(1, 5, 0,startDate, endDate, deptTeach).getResultList();
		printList(list);
		
//		String queryType="all";
//		list=diningRoomService.getDiningPort(1, 5, 0, startDate, endDate, queryType).getResultList();
//		printList(list);
//		list=diningRoomService.getDiningPortTrend(queryType);
//		printList(list);
//		list=diningRoomService.getDiningRoom(1, 5, 0, startDate, endDate, queryType).getResultList();
//		printList(list);
//		list=diningRoomService.getDiningRoomTrend(queryType);
//		printList(list);
		
//		map=habitPayTypeService.getHabitCount(startDate, endDate, deptTeach);
//		printMap(map);
//		list=habitPayTypeService.getHabitCountByType(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitPayTypeService.getHabitHour(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitPayTypeService.getHabitHourByType(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitPayTypeService.getHabitZao(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitPayTypeService.getHabitZaoByType(startDate, endDate, deptTeach);
//		printList(list);
		
//		System.out.println("--1--");
//		list=habitStuTypeService.getHabitEat(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitStuTypeService.getHabitEatByEdu(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitStuTypeService.getHabitEatBySex(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitStuTypeService.getHabitHour(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitStuTypeService.getHabitHourByEdu(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitStuTypeService.getHabitHourBySex(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitStuTypeService.getHabitZao(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitStuTypeService.getHabitZaoByEdu(startDate, endDate, deptTeach);
//		printList(list);
//		list=habitStuTypeService.getHabitZaoBySex(startDate, endDate, deptTeach);
//		printList(list);
		
//		System.out.println("--2--");
//		list=payPowerService.getPayRegion(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPayRegionByEdu(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPayRegionBySex(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPayType(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPayTypeByEdu(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPayTypeBySex(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPower(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPowerByDept(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPowerByEdu(startDate, endDate, deptTeach);
//		printList(list);
//		list=payPowerService.getPowerBySex(startDate, endDate, deptTeach);
//		printList(list);
		
//		System.out.println("--3--");
//		map=rechargeService.getRecharge(startDate, endDate, deptTeach);
//		printMap(map);
//		list=rechargeService.getLastMoneyRegion(startDate, endDate, deptTeach);
//		printList(list);
//		list=rechargeService.getRechargeByDept(startDate, endDate, deptTeach);
//		printList(list);
//		list=rechargeService.getRechargeByHour(startDate, endDate, deptTeach);
//		printList(list);
//		list=rechargeService.getRechargeByType(startDate, endDate, deptTeach);
//		printList(list);
//		list=rechargeService.getRechargeRegion(startDate, endDate, deptTeach);
//		printList(list);
//		list=rechargeService.getRechargeTrend(deptTeach);
//		printList(list);
//		list=rechargeService.getRechargeTrendByType(deptTeach);
//		printList(list);
//		
//		list=cardTrendService.getCardUsedTrend(deptTeach, "all", "use_all");
//		printList(list);
//		list=cardTrendService.getEatTrend("107", "eat_room", "all");  //107   166
//		printList(list);
//		list=cardTrendService.getPayTypeTrend(deptTeach, "all");
//		printList(list);
//		list=cardTrendService.getRechargeTrend(deptTeach);
//		printList(list);
//		list=cardTrendService.getStuPayTrend(deptTeach, "all", "pay_all"); 
//		printList(list);
//		
		
	}
	
	@Test
	public void testInit(){
		JobResultBean jrb=null;
		jrb=jobCardService.initCardPeople();System.out.println(jrb.getMsg());
		jrb=jobCardService.initCardUsed();System.out.println(jrb.getMsg());
		//jrb=jobCardService.updateStuPay();

		jrb=jobCardService.initRecharge();System.out.println(jrb.getMsg());
		jrb=jobCardService.initStuEat();System.out.println(jrb.getMsg());
		jrb=jobCardService.initStuPay();System.out.println(jrb.getMsg());
		jrb=jobCardService.initStuPayDetil();System.out.println(jrb.getMsg());
		jrb=jobCardService.initCardHot();System.out.println(jrb.getMsg());
		
		//jrb=jobCardService.updateStuPay();
		System.out.println(jrb.getMsg());
	}
	
	private void printMap(Map<String,Object> map){
		for (Entry<String, Object> entry : map.entrySet() ) {  
		    System.out.print("   Key = " + entry.getKey() + ", Value = " + entry.getValue());  
		}  
		System.out.println("");
	}
	private void printList(List<Map<String,Object>> list){
		System.out.println("List:");
		System.out.println("Map:");
		for(Map<String,Object> map:list){
			printMap(map);
		}
	}
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}

}
