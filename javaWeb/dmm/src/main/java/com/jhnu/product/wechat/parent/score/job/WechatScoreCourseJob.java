package com.jhnu.product.wechat.parent.score.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.jhnu.product.common.stu.entity.Student;
import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.wechat.parent.score.entity.CourseScore;
import com.jhnu.product.wechat.parent.score.entity.StuCourseScore;
import com.jhnu.product.wechat.parent.score.entity.TlWechatScoreCourse;
import com.jhnu.product.wechat.parent.score.service.WechatScoreService;
import com.jhnu.util.product.EduUtils;
import com.jhnu.util.product.Globals;

public class WechatScoreCourseJob implements Job {
	
	@Autowired
	private WechatScoreService wechatScoreService;
	@Autowired
	private StuService stuService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String[] yt = EduUtils.getProSchoolYearTerm(new Date());
		String schoolYear =yt[0],termCode=yt[1];
		List<CourseScore> css = wechatScoreService.getStuCourseScores(schoolYear,termCode);
		List<Student> stus = stuService.getStusInSchool();
		List<StuCourseScore>scss = wechatScoreService.getAllStuCourseScores(css, stus);
		
		List<Map<String,Object>> majorMS = wechatScoreService.getMajorCourseMaxScore(schoolYear, termCode);
		Map<String,String> majorMap = new HashMap<String,String>();
		
		for(Map<String,Object> temp : majorMS){
			String key  = temp.get("MAJOR_ID").toString()+temp.get("CODE").toString();
			String value = temp.get("SCORE").toString();
			majorMap.put(key, value);
		}
		
		List<TlWechatScoreCourse> toSave = new ArrayList<TlWechatScoreCourse>();
		// 排序后 转存为数据对象
		for(StuCourseScore scs : scss){
			Student stu = scs.getStu();
			List<CourseScore> tempCss =  scs.getCss();
			Collections.sort(tempCss, new Comparator<CourseScore>(){
				@Override
				public int compare(CourseScore o1, CourseScore o2) {
					return new Double(o2.getScore()).compareTo(new Double(o1.getScore()));
				}
			} );
			
			// 最低分
			String zdfkm = tempCss.get(tempCss.size()-1).getName();
			String zdfkmdm = tempCss.get(tempCss.size()-1).getCode();
			double zdf = tempCss.get(tempCss.size()-1).getScore();
			// 最高分
			String zgfkm = tempCss.get(0).getName();
			String zgfkmdm = tempCss.get(0).getCode();
			double zgf = tempCss.get(0).getScore();
			// 不及格
			List<String> failCourse = new ArrayList<String>();
			for(CourseScore tempCs : tempCss){
				if(Globals.FAIL_SCORE_LINE>tempCs.getScore()){
					failCourse.add(tempCs.getName()+"-"+tempCs.getScore());
				}
			}
			TlWechatScoreCourse tlc = new TlWechatScoreCourse(stu.getNo_(),schoolYear,termCode,
					zgfkm,zgf+"",zdfkm,zdf+"",failCourse.toString());
			tlc.setLowCourseMs(majorMap.get(stu.getMajor()+zdfkmdm));
			tlc.setTopCourseMs(majorMap.get(stu.getMajor()+zgfkmdm));
			
			toSave.add(tlc);
		}
		
		wechatScoreService.saveAllStusCourse2Log(toSave);
	}

}
