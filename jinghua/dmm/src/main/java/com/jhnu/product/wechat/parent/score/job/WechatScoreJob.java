package com.jhnu.product.wechat.parent.score.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreRank;
import com.jhnu.product.wechat.parent.score.service.WechatScoreService;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.product.EduUtils;

public class WechatScoreJob implements Job {
	
	@Autowired
	private WechatScoreService wechatScoreService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String[] yt = EduUtils.getProSchoolYearTerm(new Date());
		String schoolYear = yt[0],termCode = yt[1];
		List<TlWechatScoreRank> rs = wechatScoreService.getAllStusSumAvgScore(schoolYear, termCode);
		// 更新class排名
		Map<String,List<TlWechatScoreRank>> classRank = new HashMap<String,List<TlWechatScoreRank>>();
		Map<String,List<TlWechatScoreRank>> majorRank = new HashMap<String,List<TlWechatScoreRank>>();
		Map<String,String> classGrade = new HashMap<String,String>();
		List<Map<String,Object>> classGs = wechatScoreService.getAllClassGrade();
		
		for(Map<String,Object> temp : classGs){
			classGrade.put(MapUtils.getString(temp, "NO_"), MapUtils.getString(temp, "GRADE"));
		}
		
		for(TlWechatScoreRank tl : rs){
			String classId = tl.getClassId(),majorId = tl.getMajorId(),grade = classGrade.get(classId);
			if(classRank.containsKey(classId)){
				classRank.get(classId).add(tl);
			}else{
				List<TlWechatScoreRank> tempList = new ArrayList<TlWechatScoreRank>();
				tempList.add(tl);
				classRank.put(classId, tempList);
			}
			
			if(majorRank.containsKey(majorId+grade)){
				majorRank.get(majorId+grade).add(tl);
			}else{
				List<TlWechatScoreRank> tempList = new ArrayList<TlWechatScoreRank>();
				tempList.add(tl);
				majorRank.put(majorId+grade, tempList);
			}
		}
		
		Iterator<String> it = classRank.keySet().iterator();
		while(it.hasNext()){
			String classId = it.next();
			List<TlWechatScoreRank> tls = classRank.get(classId);
			// 获取专业排名
			Collections.sort(tls, new Comparator<TlWechatScoreRank>() {
				@Override
				public int compare(TlWechatScoreRank o1, TlWechatScoreRank o2) {
					return o2.getScoreAvg().compareTo(o1.getScoreAvg());
				}
			});
			
			for(int i=0,j=tls.size();i<j;i++){
				tls.get(i).setClassRank(i+1);
			}
		}
		
		
		Iterator<String> it1 = majorRank.keySet().iterator();
		while(it1.hasNext()){
			String majorId = it1.next();
			List<TlWechatScoreRank> tls = majorRank.get(majorId);
			// 获取专业排名，（专业里是分年级的）
			Collections.sort(tls, new Comparator<TlWechatScoreRank>() {
				@Override
				public int compare(TlWechatScoreRank o1, TlWechatScoreRank o2) {
					return o2.getScoreAvg().compareTo(o1.getScoreAvg());
				}
			});
			
			for(int i=0,j=tls.size();i<j;i++){
				tls.get(i).setMajorRank(i+1);
			}
		}
				
		wechatScoreService.saveAllStusSumAvgScore2Log(rs);
	}
	
}
