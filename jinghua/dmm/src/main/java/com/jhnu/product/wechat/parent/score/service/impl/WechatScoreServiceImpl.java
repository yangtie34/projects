package com.jhnu.product.wechat.parent.score.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.common.stu.dao.StuDao;
import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.wechat.parent.score.dao.WechatScoreDao;
import com.jhnu.product.wechat.parent.score.entity.CourseScore;
import com.jhnu.product.wechat.parent.score.entity.StuCourseScore;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreCourse;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreRank;
import com.jhnu.product.wechat.parent.score.entity.WechatScoreData;
import com.jhnu.product.wechat.parent.score.service.WechatScoreService;
import com.jhnu.system.common.chart.Chart;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.product.EduUtils;

@Service("wechatScoreService")
public class WechatScoreServiceImpl implements WechatScoreService {
	@Autowired
	private WechatScoreDao wechatScoreDao;
	@Autowired
	private StuDao stuDao;
	@Override
	public Chart getScoreLine(String stuId) {
		List<Map<String,Object>> temp1 = wechatScoreDao.getCountScoreLine(stuId);
		List<Map<String,Object>> temp2 = wechatScoreDao.getAvgScoreLine(stuId);
		Chart chart = new Chart();
		List<String> xAxis = new ArrayList<String>();
		List<EduUtils.Code> countCodes = new ArrayList<EduUtils.Code>();
		for(Map<String,Object> t1 : temp1){
			String x1 = MapUtils.getString(t1, "SCHOOL_YEAR")+"-"+MapUtils.getString(t1, "TERM_CODE");
			xAxis.add(x1);
			EduUtils.Code eCode = new EduUtils.Code(x1,MapUtils.getString(t1, "COUNT_"));
			countCodes.add(eCode);
		}
		List<EduUtils.Code> avgCodes = new ArrayList<EduUtils.Code>();
		for(Map<String,Object> t2 : temp2){
			String x2 = MapUtils.getString(t2, "SCHOOL_YEAR")+"-"+MapUtils.getString(t2, "TERM_CODE");
			EduUtils.Code eCode = new EduUtils.Code(x2,MapUtils.getString(t2, "AVG_"));
			avgCodes.add(eCode);
		}
		String flag1 = "总成绩",flag2="平均成绩";
		Set<String> legend = new HashSet<String>();
		legend.add("总成绩");legend.add("平均成绩");
		chart.setxAxis(xAxis);
		chart.setLegend(legend);
		Map<String,List<EduUtils.Code>> series = new HashMap<String,List<EduUtils.Code>>();
		series.put(flag1, countCodes);
		series.put(flag2, avgCodes);
		chart.setSeries(series);
		
		return chart;
	}
	
	@Override
	public WechatScoreData getWechatScoreData(String stuId) {
		WechatScoreData wsd = new WechatScoreData();
		List<Map<String,Object>> result = wechatScoreDao.getScoreAnalyzeData(stuId);
		// 根据当前日期获取最近的学年学期
		String[] sytc = EduUtils.getProSchoolYearTerm(new Date());
		String schoolYear = sytc[0],termCode=sytc[1];
		if(result.size()==0){
			return wsd;
		}else{
			Map<String,Object> temp = result.get(0);
			schoolYear = MapUtils.getString(temp, "SCHOOL_YEAR");
			termCode = MapUtils.getString(temp, "TERM_CODE");
			
			wsd.setStuId(stuId);
			wsd.setSchoolYear(schoolYear);
			wsd.setTermCode(termCode);
			wsd.setClassRanking(MapUtils.getIntValue(temp, "CLASS_RANK"));
			wsd.setMajorRanking(MapUtils.getIntValue(temp, "MAJOR_RANK"));
			wsd.setScoreCount(MapUtils.getDoubleValue(temp, "COUNT_"));
			wsd.setScoreAvg(MapUtils.getDoubleValue(temp, "AVG_"));
			
			wsd.setClassStuNum(wechatScoreDao.getClassStuCount(stuId));
			wsd.setMajorStuNum(wechatScoreDao.getMajorStuCount(stuId));
			
			List<Map<String,Object>> temp1 = wechatScoreDao.getScoreCourseData(stuId, schoolYear, termCode);
			
			if(temp1.size()!=0){
				wsd.setGfkms(MapUtils.getString(temp1.get(0), "TOP_COURSE").split(","));
				wsd.setGfkmfs(MapUtils.getString(temp1.get(0), "TOP_COURSE_S").split(","));
				wsd.setGfkmzgf(MapUtils.getString(temp1.get(0), "TOP_COURSE_MS").split(","));
				
				wsd.setDfkms(MapUtils.getString(temp1.get(0), "LOW_COURSE").split(","));
				wsd.setDfkmfs(MapUtils.getString(temp1.get(0), "LOW_COURSE_S").split(","));
				wsd.setDfkmzgf(MapUtils.getString(temp1.get(0), "LOW_COURSE_MS").split(","));
				
				wsd.setBjgkms(MapUtils.getString(temp1.get(0), "FAIL_COURSE").split(","));
			}
		}
		return wsd;
	}
	
	@Override
	public List<TlWechatScoreRank> getAllStusSumAvgScore(String schoolYear,String termCode) {
		return wechatScoreDao.getAllStusSumAvgScore(schoolYear, termCode);
	}
	
	@Override
	public void saveAllStusSumAvgScore2Log(List<TlWechatScoreRank> result) {
		wechatScoreDao.saveAllStusSumAvgScore2Log(result);
	}
	
	@Override
	public List<StuCourseScore> getAllStuCourseScores(List<CourseScore> css,List<Student> stus) {
		List<StuCourseScore> scss = new ArrayList<StuCourseScore>();
		Map<String ,Student> stuMap = new HashMap<String,Student>(); 
		Map<String, List<CourseScore>> csMap = new HashMap<String, List<CourseScore>>();
		for(Student stu : stus){
			stuMap.put(stu.getNo_(), stu);
		}
		
		for(CourseScore cs : css){
			String stuId = cs.getStuId();
			if(csMap.containsKey(stuId)){
				csMap.get(stuId).add(cs);
			}else{
				List<CourseScore> temp = new ArrayList<CourseScore>();
				temp.add(cs);
				
				csMap.put(stuId, temp);
			}
			
		}

		Iterator<String> it = csMap.keySet().iterator();
		while(it.hasNext()){
			String stuId = it.next();
			if(!stuMap.containsKey(stuId)) continue;
			StuCourseScore scs = new StuCourseScore(stuMap.get(stuId));
			scs.setCss(csMap.get(stuId));
			scss.add(scs);
		}
		
		return scss;
	}
	
	@Override
	public List<CourseScore> getStuCourseScores(String schoolYear,String termCode) {
		return wechatScoreDao.getCourseScores(schoolYear, termCode);
	}
	
	@Override
	public void saveAllStusCourse2Log(List<TlWechatScoreCourse> result) {
		wechatScoreDao.saveAllStusCourse2Log(result);
	}
	
	@Override
	public List<Map<String, Object>> getMajorCourseMaxScore(String schoolYear,
			String termCode) {
		return wechatScoreDao.getMajorCourseMaxScore(schoolYear, termCode);
	}
	
	@Override
	public List<Map<String, Object>> getAllClassGrade() {
		return wechatScoreDao.getAllClassGrade();
	}
}
