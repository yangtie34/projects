package com.jhnu.product.four.score;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jhnu.product.four.score.service.FourScoreService;
import com.jhnu.spring.SpringTest;

public class ScoreTest extends SpringTest{
	@Resource
	private FourScoreService fourScoreService;
	
	@Before
	public void testBefore(){
		System.out.println("==先执行我==");
	}
	
	@Test
	public void testaa(){
	//	fourScoreService.saveAvgScoreJob();
	//	fourScoreService.saveBestScoreJob();
//		ArrayList<String> list=new ArrayList<String>();
//		list.add("2010-2011");
//		list.add("2011-2012");
//		list.add("2012-2013");
//		list.add("2013-2014");
//		list.add("2014-2015");
//		for (int i = 0; i < list.size(); i++) {
//			for (int j = 0; j < 2; j++) {
//				if(j==0){
//					System.out.println(list.get(i)+"===01");
//					fourScoreService.saveScoreRankJob(list.get(i), "01");
//				}else{
//					if(!list.get(i).equals("2014-2015")){
//						System.out.println(list.get(i)+"===02");
//						fourScoreService.saveScoreRankJob(list.get(i), "02");
//					}
//				}
//			}
//		}
		
//		fourScoreService.saveBestCourseByMajorJob();
//		System.out.println("==============================");
//		fourScoreService.saveBestCourseByStuJob();
		
//		fourScoreService.saveSumScoreJob();
//		fourScoreService.saveFirstDownJob();
		fourScoreService.saveScoreAvgJob();
//		System.out.println(fourScoreService.getScore2Line("201008010045").getData().get("StuScoreLine"));
//		System.out.println(fourScoreService.getScore2Line("201008010045").getData().get("MajorScoreLine"));
//		fourScoreService.saveGPAScoreJob();
//		fourScoreService.getScore2Line("201008010045");
	}
		
	
	@After
	public void testAter(){
		System.out.println("==我最后执行==");
	}
}
