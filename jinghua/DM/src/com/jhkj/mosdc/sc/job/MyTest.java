package com.jhkj.mosdc.sc.job;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.jhkj.mosdc.sc.service.KyTopService;
import com.jhkj.mosdc.sc.service.YktSituationService;
import com.jhkj.mosdc.sc.service.YsPksService;


public class MyTest {

	// spring容器引用
	public ApplicationContext ac = null;
	// spring配置文件
	public static String CONFIG_FILES = "applicationContext.xml";

	// 启动spring容器
	@Before
	public void setUp() throws Exception {
		ac = new ClassPathXmlApplicationContext(CONFIG_FILES);
	}
	@Test
	public void testSaveYesterDayWG() {
//		BzxrstjDao dao = (BzxrstjDao)ac.getBean("bzxrstjDao");
//		ExportBzxXsService export = (ExportBzxXsService) ac.getBean("exportBzxService");
//		BzxMailSenderService mailSender = (BzxMailSenderService)ac.getBean("bzxmailService");
//		UpdateSydsxService service = (UpdateSydsxService)ac.getBean("updateSydsxService");
//		DemoAnalysisService demoService = (DemoAnalysisService)ac.getBean("demoService");
//		YsPksService yspksService = (YsPksService)ac.getBean("ysPksService");
//		YktSituationService yktService = (YktSituationService)ac.getBean("yktSituationService");
//		KyTopService kyTopService = (KyTopService)ac.getBean("kyTopService");
//		kyTopService.getUpNumYear("{}");
		InitPathService initPathService=(InitPathService)ac.getBean("initPathService");
		Map<String,Object >map=new HashMap<String, Object>();
		map.put("TableName", "t_code_education_text");  //t_code_education  t_code_zyjszw  t_code_degree
		map.put("PidStart", "-2");
		map.put("PathLength", "3");
		initPathService.saveInitPath(map);
		
		
		try {
//			demoService.saveCjpm2Temp();
//			demoService.updatePm();
//			demoService.saveTsgMjcs2Temp();
//			demoService.saveZccs2Temp();
//			System.out.println(demoService.queryChartDate(""));
//			demoService.queryChartDate2("");
//			String[] ss=new String[]{
//					"2014-12-01","2014-12-02","2014-12-03","2014-12-04","2014-12-05",
//					"2014-12-05","2014-12-06","2014-12-07","2014-12-08","2014-12-09",
//					"2014-12-10","2014-12-11","2014-12-12","2014-12-13","2014-12-14",
//					"2014-12-15","2014-12-16","2014-12-17","2014-12-18","2014-12-19",
//					"2014-12-20","2014-12-21","2014-12-22","2014-12-23","2014-12-24",
//					"2014-12-25","2014-12-26","2014-12-27","2014-12-28","2014-12-29"};
//			for(String str : ss){
//				System.out.println("------晚归-------"+str);
//				dao.saveYesterDayWG(str);
//				
//			}
//			for(String str : ss){
//				System.out.println("------未住宿-------"+str);
//				dao.saveYesterDayWZS(str);
//				
//			}
//			dao.saveYesterDayWG();
//			dao.saveDayWZS();
//			dao.bzxxsThreeDay();
//			export.saveExport4Wg();
//			export.export4Yx();
//			mailSender.sendMail4WG("302", "2013-03-29");
//			mailSender.sendMail4WG();
//			mailSender.sendMail4WZS();
//			mailSender.sendMail4WZS("302", "2013-03-28");
//			export.saveExport4Wzs();
//			service.updateStaticData("");
//			yspksService.saveXfxx();
//			yktService.saveMonthXfmx();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}