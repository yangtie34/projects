package com.jhnu.product.four.score.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.product.four.score.dao.FourScoreDao;
import com.jhnu.product.four.score.service.FourScoreService;
import com.jhnu.system.common.chart.Chart;
import com.jhnu.system.common.condition.Condition;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.MathUtils;
import com.jhnu.util.product.EduUtils;
import com.jhnu.util.product.EduUtils.Code;

@Service("fourScoreService")
public class FourScoreServiceImpl implements FourScoreService{
	@Autowired
	FourScoreDao fourScoreDao;
	@Autowired
	private StuService stuService;
	
	
	private static final Logger logger = Logger.getLogger(FourScoreServiceImpl.class);
	
	
	@Override
	@Transactional
	public void saveAvgScoreJob(){
		List<Map<String, Object>> all=fourScoreDao.getAvgScore();
		//获得借书平均数
		List<Map<String, Object>> ac=fourScoreDao.getAllAvgScore();
		//计算超过了多少百分比学生
		int byn=-1,bynCon=-1,count=0;
		String moreThanS="",mid="-1";
		double mc=1,qmc=0,sum=-1;
		for (int i = 0; i < all.size(); i++) {
			if(byn!=MapUtils.getIntValue(all.get(i),"end_year")){
				byn=MapUtils.getIntValue(all.get(i),"end_year");
				bynCon++;
				sum=-1;
				qmc=MapUtils.getDoubleValue(all.get(i),"r")-1;
				mid=MapUtils.getString(all.get(i),"major_id");
				count=stuService.getStuCByEndYearAndMajor(byn, mid);
			}
			if(!mid.equals(MapUtils.getString(all.get(i),"major_id"))){
				mid=MapUtils.getString(all.get(i),"major_id");
				bynCon++;
				sum=-1;
				qmc=MapUtils.getDoubleValue(all.get(i),"r")-1;
				count=stuService.getStuCByEndYearAndMajor(byn, mid);
			}
			if(sum!=MapUtils.getDoubleValue(all.get(i),"avg_")){
				sum=MapUtils.getDoubleValue(all.get(i),"avg_");
				mc=MapUtils.getDoubleValue(all.get(i),"r")-qmc;
				double moreThan=MathUtils.get2Point((mc-1)/count*100);
				moreThanS=moreThan+"%";
			}
			//存放计算好的值
			all.get(i).put("more_than", moreThanS);
			all.get(i).put("avg_all", MathUtils.get2Point(MapUtils.getDoubleValue(ac.get(bynCon),"avg_")));
		}
		fourScoreDao.saveAvgScoreLog(all);
		
	}
	
	@Override
	@Transactional
	public void saveSumScoreJob(){
		List<Map<String, Object>> all=fourScoreDao.getSumScore();
		//计算超过了多少百分比学生
		int byn=-1;
		
		String moreThanS="",mid="-1";
		double mc=1,qmc=0,sum=-1;
		int count=0;
		for (int i = 0; i < all.size(); i++) {
			if(byn!=MapUtils.getIntValue(all.get(i),"end_year")){
				byn=MapUtils.getIntValue(all.get(i),"end_year");
				sum=-1;
				qmc=MapUtils.getDoubleValue(all.get(i),"r")-1;
				mid=MapUtils.getString(all.get(i),"major_id");
				count=stuService.getStuCByEndYearAndMajor(byn, mid);
			}
			if(!mid.equals(MapUtils.getString(all.get(i),"major_id"))){
				mid=MapUtils.getString(all.get(i),"major_id");
				sum=-1;
				qmc=MapUtils.getDoubleValue(all.get(i),"r")-1;
				count=stuService.getStuCByEndYearAndMajor(byn, mid);
			}
			if(sum!=MapUtils.getDoubleValue(all.get(i),"sum_")){
				sum=MapUtils.getDoubleValue(all.get(i),"sum_");
				mc=MapUtils.getDoubleValue(all.get(i),"r")-qmc;
				double moreThan=MathUtils.get2Point((mc-1)/count*100);
				moreThanS=moreThan+"%";
			}
			//存放计算好的值
			all.get(i).put("more_than", moreThanS);
		}
		fourScoreDao.saveSumScoreLog(all);
		
	}

	@Override
	@Transactional
	public void saveBestScoreJob() {
		fourScoreDao.saveBestScoreLog(fourScoreDao.getBestScore());
	}

	@Override
	@Transactional
	public void saveScoreRankJob(String schoolYear, String termCode) {

		List<Map<String, Object>> all=fourScoreDao.getRankByTime(schoolYear, termCode);
		int mc=1,qmc=0;
		String mid="-1";
		double sum=-1;
		for (int i = 0; i < all.size(); i++) {
			if(!mid.equals(MapUtils.getString(all.get(i),"major_id"))){
				mid=MapUtils.getString(all.get(i),"major_id");
				sum=-1;
				qmc=MapUtils.getIntValue(all.get(i),"r")-1;
			}
			if(sum!=MapUtils.getDoubleValue(all.get(i),"avg_")){
				sum=MapUtils.getDoubleValue(all.get(i),"avg_");
				mc=MapUtils.getIntValue(all.get(i),"r")-qmc;
			}
			//存放计算好的值
			all.get(i).put("rank", mc);
		}
		fourScoreDao.saveRankLog(all);
	}

	@Override
	@Transactional
	public void saveBestCourseByMajorJob() {
		Page page=fourScoreDao.getBsetCourseByMajor(1,50000,null,0);
		fourScoreDao.saveBsetCourseByMajorLog(page.getResultList(),true);
		for (int i = 2; i <= page.getTotalPages(); i++) {
			if(i==page.getTotalPages()){
				fourScoreDao.saveBsetCourseByMajorLog(fourScoreDao.getBsetCourseByMajor(i,50000,page.getTotalRows(),-1).getResultList(),false);
			}else{
				fourScoreDao.saveBsetCourseByMajorLog(fourScoreDao.getBsetCourseByMajor(i,50000,page.getTotalRows(),1).getResultList(),false);
			}
			
		}
	}

	@Override
	@Transactional
	public void saveBestCourseByStuJob() {
		Page page=fourScoreDao.getBsetCourseByStu(1,50000,null,0);
		fourScoreDao.saveBsetCourseByStuLog(page.getResultList(),true);
		logger.info("saveBestCourseByStuJob:=============================="+page.getTotalPages());
		for (int i = 2; i <= page.getTotalPages(); i++) {
			if(i==page.getTotalPages()){
				fourScoreDao.saveBsetCourseByStuLog(fourScoreDao.getBsetCourseByStu(i,50000,page.getTotalRows(),-1).getResultList(),false);
			}else{
				fourScoreDao.saveBsetCourseByStuLog(fourScoreDao.getBsetCourseByStu(i,50000,page.getTotalRows(),1).getResultList(),false);
			}
		}
	}

	@Override
	public ResultBean getAvgScoreLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("AvgScore");
		List<Map<String,Object>> list=fourScoreDao.getAvgScoreLog(id);
		if(list.size()>0){
			rb.getData().put("AvgScore", list.get(0));
		}else{
			rb.getData().put("AvgScore", null);
		}
		
		rb.getData().put("AvgScoreLine", this.getScore2Line(id));
		return rb;
	}

	@Override
	public ResultBean getBestRankLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("BestRank");
		List<Map<String,Object>> list=fourScoreDao.getRankLogByStuId(id);
		if(list.size()>0){
			//序列0即最高的名次
			rb.getData().put("BestRank", list.get(0));
			//获取当学年学期该学生的考试过的科目
			rb.getData().put("ScoureList",fourScoreDao.getScoureList(
							id,
							list.get(0).get("school_year").toString(), 
							list.get(0).get("term_code").toString()));
		}else{
			rb.getData().put("BestRank", null);
			rb.getData().put("ScoureList", null);
		}
		return rb;
	}

	@Override
	public ResultBean getScoreCountLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("ScoreCount");
		
		List<Map<String,Object>> sum=fourScoreDao.getSumScoreLog(id);
		List<Map<String,Object>> avg=fourScoreDao.getAvgScoreLog(id);
		List<Map<String,Object>> gpa=fourScoreDao.getGPAScoreLog(id);
		if(sum.size()>0){
			rb.getData().put("SumScore",sum.get(0));
			rb.getData().put("AvgScore",avg.get(0));
			rb.getData().put("GpaScore",gpa.get(0));
			
			//=========计算总共平均超过的百分比==========
			//总分超过的百分比
			String SumMoreThanS=sum.get(0).get("more_than").toString();
			SumMoreThanS=SumMoreThanS.substring(0, SumMoreThanS.length()-1);
			Double SumMoreThan=Double.parseDouble(SumMoreThanS);
			//平均分超过的百分比
			String AvgMoreThanS=avg.get(0).get("more_than").toString();
			AvgMoreThanS=AvgMoreThanS.substring(0, AvgMoreThanS.length()-1);
			Double AvgMoreThan=Double.parseDouble(AvgMoreThanS);
			//综合超过的百分比
			String gpaMoreThanS=gpa.get(0).get("more_than").toString();
			gpaMoreThanS=SumMoreThanS.substring(0, gpaMoreThanS.length()-1);
			Double gpaMoreThan=Double.parseDouble(gpaMoreThanS);
			
			String AllScore=MathUtils.get2Point((SumMoreThan+AvgMoreThan+gpaMoreThan)/3)+"%";
			
			//总共平均超过的百分比
			rb.getData().put("AllScore", AllScore);
		}
		//获取该学生最好科目的前3科
		List<Map<String,Object>> list=fourScoreDao.getBsetCourseByStuLog(id, 3);
		if(list.size()>0){
			rb.getData().put("BsetCourse", list);
			//获取该毕业年下该专业的最好科目的前3科
			rb.getData().put("BsetCourseAll",fourScoreDao.getBsetCourseByMajorLog(
							list.get(0).get("end_year").toString(), 
							list.get(0).get("major_id").toString(),
							3));
		}else{
			rb.getData().put("BsetCourse", null);
			rb.getData().put("BsetCourseAll", null);
		}
		return rb;
	}

	@Override
	public ResultBean getFirstDownLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("FirstDown");
		List<Map<String,Object>> list=fourScoreDao.getFirstDownLog(id);
		if(list.size()>0){
			rb.getData().put("FirstDown", list.get(0));
		}else{
			rb.getData().put("FirstDown", null);
		}
		
		return rb;
	}

	@Override
	@Transactional
	public void saveFirstDownJob() {
		fourScoreDao.saveFirstDownLog(fourScoreDao.getFirstDown());
	}
	
	@Override
	public void saveScoreAvgJob() {
		fourScoreDao.saveScoreAvgLog(fourScoreDao.getScoreAvgFromTemp());
		fourScoreDao.dropTempScoreAvg();
	}
	
	@Override
	public Chart getScore2Line(String id) {
		List<Map<String,Object>> stuLine = fourScoreDao.getScoreStuLine(id);
		String majorId = stuLine.size()==0?"-1":stuLine.get(0).get("MAJOR_ID").toString();
		String enrollGrade = stuLine.size()==0?"-1":stuLine.get(0).get("ENROLL_GRADE").toString();
		List<Map<String,Object>> majorLine = fourScoreDao.getScoreMajorLine(majorId, enrollGrade);
		
		Map<String,Map<String,Object>> stuLineMap = new HashMap<String,Map<String,Object>>();
		Map<String,Map<String,Object>> majorLineMap = new HashMap<String,Map<String,Object>>();
		for(Map<String, Object> temp : stuLine){
			StringBuffer key = new StringBuffer(temp.get("SCHOOL_YEAR").toString());
			key.append(":").append(temp.get("TERM_CODE").toString());
			if(!stuLineMap.containsKey(key)){
				stuLineMap.put(key.toString(),temp);
			}
		}
		for(Map<String, Object> temp : majorLine){
			StringBuffer key = new StringBuffer(temp.get("SCHOOL_YEAR").toString());
			key.append(":").append(temp.get("TERM_CODE").toString());
			if(!majorLineMap.containsKey(key)){
				majorLineMap.put(key.toString(),temp);
			}
		}
		
		Set<String> legend = new HashSet<String>();
		legend.add("我的平均成绩");
		legend.add("我专业的平均");
		Chart chart = new Chart();
		int endYear = stuService.getEndYearByStuId(id);
		int schoolYear = stuService.getSchoolYearsByStuId(id); 
		List<Code> codes = EduUtils.getFourSchoolyearByEndYear(endYear, schoolYear);
		List<String> codeNames = new ArrayList<String>();
		List<Code> stuScoreLine = new ArrayList<Code>();
		List<Code> majorScoreLine = new ArrayList<Code>();
		for(Code code : codes){
			codeNames.add(code.getName());
			
			String value = code.getValue(),name = code.getName();
			
			String stuAvg = !stuLineMap.containsKey(value)?"-":stuLineMap.get(value).get("AVG_").toString();
			Code codeTem = new Code(name,stuAvg);
			stuScoreLine.add(codeTem);
			
			String majorAvg = !majorLineMap.containsKey(value)?"-":majorLineMap.get(value).get("AVG_").toString();
			Code codeTem1 = new Code(name,majorAvg);
			majorScoreLine.add(codeTem1);
		}
		chart.setxAxis(codeNames);
		chart.setLegend(legend);
		
		Map<String,List<Code>> series = new HashMap<String,List<Code>>();
		series.put("我的平均成绩", stuScoreLine);
		series.put("我专业的平均", majorScoreLine);
		chart.setSeries(series);
		return chart;
	}
	
	@Override
	public void saveGPAScoreJob() {
		List<Map<String,Object>>  all = fourScoreDao.getGPAScore();
		//计算超过了多少百分比学生
		int byn=-1;
		
		String moreThanS="",mid="-1";
		double mc=1,qmc=0,sum=-1;
		int count=0;
		
		for (int i = 0; i < all.size(); i++) {
			if(byn!=MapUtils.getIntValue(all.get(i),"END_YEAR")){
				byn=MapUtils.getIntValue(all.get(i),"END_YEAR");
				sum=-1;
				qmc=MapUtils.getDoubleValue(all.get(i),"r")-1;
				mid=MapUtils.getString(all.get(i),"major_id");
				count=stuService.getStuCByEndYearAndMajor(byn, mid+"");
			}
			if(!mid.equals(MapUtils.getString(all.get(i),"major_id"))){
				mid=MapUtils.getString(all.get(i),"major_id");
				sum=-1;
				qmc=MapUtils.getDoubleValue(all.get(i),"r")-1;
				count=stuService.getStuCByEndYearAndMajor(byn, mid+"");
			}
			if(sum!=MapUtils.getDoubleValue(all.get(i),"GPA")){
				sum=MapUtils.getDoubleValue(all.get(i),"GPA");
				mc=MapUtils.getDoubleValue(all.get(i),"r")-qmc;
//				double mmm=( mc-1)/count*100;
//				double moreThan=MathUtils.get2Point(mmm);
				double moreThan=MathUtils.get2Point(( mc-1)/count*100);
//				double moreThan=MathUtils.get2Point(MapUtils.getDoubleValue(( mc-1)/count*100));
				moreThanS=moreThan+"%";
			}
			//存放计算好的值
			all.get(i).put("MORE_THAN", moreThanS);
		}
		fourScoreDao.saveGPAScore(all);
	}

	@Override
	public Page getScoreDetailLog(String id, Page page, List<Condition> conditions) {
		String tj=" and stu_id='"+id+"' ";
		for (int i = 0; i < conditions.size(); i++) {
			Condition con=conditions.get(i);
			if("type".equals(con.getQueryCode())){
				if("dj".equals(con.getId())){
					tj += " and hierarchical_score_code is not null ";
				}else{
					tj += " and hierarchical_score_code is null ";
				}
			}else{
				tj+=(" and "+con.getQueryCode()+" like '"+con.getId()+"%' ");
			}
		}
		return fourScoreDao.getScoreDetailLog(page.getCurrentPage(), page.getNumPerPage(), tj);
	}

	@Override
	public List<Map<String, Object>> getScoreDetailGroupBySY(String id) {
		return fourScoreDao.getScoreDetailGroupBySY(id);
	}
}
