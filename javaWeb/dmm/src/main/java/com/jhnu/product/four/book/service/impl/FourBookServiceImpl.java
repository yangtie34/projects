package com.jhnu.product.four.book.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.product.common.stu.service.StuService;
import com.jhnu.product.four.book.dao.FourBookDao;
import com.jhnu.product.four.book.service.FourBookService;
import com.jhnu.product.four.common.entity.ResultBean;
import com.jhnu.system.common.chart.Chart;
import com.jhnu.system.common.condition.Condition;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.MathUtils;
import com.jhnu.util.product.EduUtils;
import com.jhnu.util.product.EduUtils.Code;

@Service("fourBookService")
public class FourBookServiceImpl implements FourBookService{
	
	@Autowired
	private FourBookDao fourBookDao;
	@Autowired
	private StuService stuService;
	
	private static final Logger logger = Logger.getLogger(FourBookServiceImpl.class);

	@Override
	public ResultBean getFirstBookLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("FirstBook");
		List<Map<String,Object>> list=fourBookDao.getFirstBorrowLog(id);
		List<Map<String,Object>> list2=fourBookDao.getFirstRKELog(id);
		if(list.size()>0){
			rb.getData().put("FirstBorrow", list.get(0));
		}else{
			rb.getData().put("FirstBorrow", null);
		}
		if(list2.size()>0){
			rb.getData().put("FirstRKE", list2.get(0));
		}else{
			rb.getData().put("FirstRKE", null);
		}
		return rb;
	}

	@Override
	public ResultBean getAllBorrowLog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("AllBorrow");
		List<Map<String,Object>> list=fourBookDao.getAllBorrowLog(id);
		if(list.size()>0){
			list.get(0).put("borrowLine", this.getBookBorrowLines(id));
			rb.setData(list.get(0));
		}else{
			rb.setData(null);
		}
		return rb;
	}

	@Override
	public ResultBean getAllRKELog(String id) {
		ResultBean rb=new ResultBean();
		rb.setName("AllRKE");
		List<Map<String,Object>> list=fourBookDao.getAllRKELog(id);
		if(list.size()>0){
			List<Map<String,Object>> list2=fourBookDao.getLikeRKETimeLog(id);
			rb.getData().put("AllRKELog", list.get(0));
			if(list2.size()>0){
				rb.getData().put("LikeRKETimeLog", list2.get(0));
				rb.getData().put("bookRKELine", this.getBookRKELines(id));
			}else{
				rb.getData().put("LikeRKETimeLog", null);
			}
		}else{
			rb.getData().put("AllRKELog", null);
		}
		return rb;
	}


	@Override
	@Transactional
	public void saveFirstBorrowLogJob() {
		fourBookDao.saveFirstBorrowLog(fourBookDao.getFirstBorrow());
		
	}

	@Override
	@Transactional
	public void saveFirstRKELogJob() {
		fourBookDao.saveFirstRKELog(fourBookDao.getFirstRKE());
	}

	@Override
	@Transactional
	public void saveAllBorrowLogJob() {
		List<Map<String, Object>> all=fourBookDao.getAllBorrow();
		//获得借书平均数
		List<Map<String, Object>> ac=fourBookDao.getAllBorrowAC();
		//计算超过了多少百分比学生
		int sum=-1,byn=-1,bynCon=-1,count=0;
		String moreThanS="";
		double mc=1,qmc=0;
		for (int i = 0; i < all.size(); i++) {
			if(byn!=MapUtils.getIntValue(all.get(i),"byn")){
				byn=MapUtils.getIntValue(all.get(i),"byn");
				bynCon++;
				sum=-1;
				qmc=MapUtils.getIntValue(all.get(i),"r")-1;
				count=stuService.getStuCByEndYear(byn);
			}
			if(sum!=MapUtils.getIntValue(all.get(i),"sums")){
				sum=MapUtils.getIntValue(all.get(i),"sums");
				mc=MapUtils.getIntValue(all.get(i),"r")-qmc;
				double moreThan=MathUtils.get2Point((mc-1)/count*100);
				moreThanS=moreThan+"%";
			}
			//存放计算好的值
			all.get(i).put("more_than", moreThanS);
			all.get(i).put("av", MathUtils.get2Point(MapUtils.getDoubleValue(ac.get(bynCon),"av")));
		}
		fourBookDao.saveAllBorrowLog(all);
	}

	@Override
	@Transactional
	public void saveAllRKELogJob() {
		logger.info("开始保存RKE统计");
		List<Map<String, Object>> all=fourBookDao.getAllRKE();
		//获得借书平均数
		List<Map<String, Object>> ac=fourBookDao.getAllRKEAC();
		//计算超过了多少百分比学生
		int sum=-1,byn=-1,bynCon=-1,count=0;
		String moreThanS="";
		double mc=1,qmc=0;
		for (int i = 0; i < all.size(); i++) {
			if(byn!=MapUtils.getIntValue(all.get(i),"byn")){
				byn=MapUtils.getIntValue(all.get(i),"byn");
				bynCon++;
				sum=-1;
				qmc=MapUtils.getIntValue(all.get(i),"r")-1;
				count=stuService.getStuCByEndYear(byn);
			}
			if(sum!=MapUtils.getIntValue(all.get(i),"sums")){
				sum=MapUtils.getIntValue(all.get(i),"sums");
				mc=MapUtils.getIntValue(all.get(i),"r")-qmc;
				double moreThan=MathUtils.get2Point((mc-1)/count*100);
				moreThanS=moreThan+"%";
			}
			//存放计算好的值
			all.get(i).put("more_than", moreThanS);
			all.get(i).put("av", MathUtils.get2Point(MapUtils.getDoubleValue(ac.get(bynCon),"av")));
		}
		fourBookDao.saveAllRKELog(all);
		logger.info("保存RKE统计完毕");
		
		logger.info("开始保存喜欢去图书馆时间");
		fourBookDao.saveLikeRKETimeLog(fourBookDao.getLikeRKETime());
		logger.info("保存喜欢去图书馆时间完毕");
		
	}

	@Override
	@Transactional
	public void saveBookBorrowStuJob(){
		fourBookDao.saveBookBorrowCountLog(fourBookDao.getBookBorrowCount());
	}
	
	@Override
	public Chart getBookBorrowLines(String id) {
		List<Map<String,Object>> stuLine = fourBookDao.getBookBorrowStuLine(id);
		String enrollGrade = stuLine.size()==0?"-1":stuLine.get(0).get("ENROLL_GRADE").toString();
		List<Map<String,Object>> majorLine = fourBookDao.getBookBoAvgGradeLine(enrollGrade);
		
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
		
		Set<String> legend = new LinkedHashSet<String>();
		legend.add("我的借书量");
		legend.add("本届人均量");
		Chart chart = new Chart();
		int xz=stuService.getSchoolYearsByStuId(id); //学制
		List<Code> codes = EduUtils.getFourSchoolyearByEndYear(Integer.valueOf(enrollGrade)+xz, xz);
		List<String> codeNames = new ArrayList<String>();
		List<Code> stuScoreLine = new ArrayList<Code>();
		List<Code> majorScoreLine = new ArrayList<Code>();
		for(Code code : codes){
			codeNames.add(code.getName());
			
			String value = code.getValue(),name = code.getName();
			
			String stuAvg = !stuLineMap.containsKey(value)?"-":stuLineMap.get(value).get("COUNT_BORROW").toString();
			Code codeTem = new Code(name,stuAvg);
			stuScoreLine.add(codeTem);
			
			String majorAvg = !majorLineMap.containsKey(value)?"-":majorLineMap.get(value).get("AVG_").toString();
			Code codeTem1 = new Code(name,majorAvg);
			majorScoreLine.add(codeTem1);
		}
		chart.setxAxis(codeNames);
		chart.setLegend(legend);
		
		Map<String,List<Code>> series = new HashMap<String,List<Code>>();
		series.put("我的借书量", stuScoreLine);
		series.put("本届人均量", majorScoreLine);
		chart.setSeries(series);
		return chart;
	}
	
	@Override
	public Chart getBookRKELines(String id) {
		List<Map<String,Object>> stuLine = fourBookDao.getBookRKEStuLine(id);
		String enrollGrade = stuLine.size()==0?"-1":stuLine.get(0).get("ENROLL_GRADE").toString();
		List<Map<String,Object>> majorLine =  fourBookDao.getBookRKEAvgGradeLine( enrollGrade);
		
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
		
		Set<String> legend = new LinkedHashSet<String>();
		legend.add("我的出入次数");
		legend.add("本届人均出入");
		Chart chart = new Chart();
		int xz=stuService.getSchoolYearsByStuId(id); //学制
		List<Code> codes = EduUtils.getFourSchoolyearByEndYear(Integer.valueOf(enrollGrade)+xz, xz);
		List<String> codeNames = new ArrayList<String>();
		List<Code> stuScoreLine = new ArrayList<Code>();
		List<Code> majorScoreLine = new ArrayList<Code>();
		for(Code code : codes){
			codeNames.add(code.getName());
			
			String value = code.getValue(),name = code.getName();
			
			String stuAvg = !stuLineMap.containsKey(value)?"-":stuLineMap.get(value).get("COUNT_RKE").toString();
			Code codeTem = new Code(name,stuAvg);
			stuScoreLine.add(codeTem);
			
			String majorAvg = !majorLineMap.containsKey(value)?"-":majorLineMap.get(value).get("AVG_").toString();
			Code codeTem1 = new Code(name,majorAvg);
			majorScoreLine.add(codeTem1);
		}
		chart.setxAxis(codeNames);
		chart.setLegend(legend);
		
		Map<String,List<Code>> series = new HashMap<String,List<Code>>();
		series.put("我的出入次数", stuScoreLine);
		series.put("本届人均出入", majorScoreLine);
		chart.setSeries(series);
		return chart;
	}
	
	@Override
	@Transactional
	public void saveBookRKEStuJob() {
		fourBookDao.saveBookRKECountLog(fourBookDao.getBookRKECount());
	}
	
	public Page getBookDetailLog(String id,Page page, List<Condition> conditions) {
		String tj=" and stu_id='"+id+"' ";
		for (int i = 0; i < conditions.size(); i++) {
			Condition con=conditions.get(i);
			tj+=(" and "+con.getQueryCode()+" like '"+con.getId()+"%' ");
		}
		return fourBookDao.getBookDetailLog(page.getCurrentPage(), page.getNumPerPage(), tj);
	}
	
	public List<Map<String, Object>> getBookDetailGroupByDeal(String id) {
		return fourBookDao.getBookDetailGroupByDeal(id);
	}
	
	public List<Map<String, Object>> getBookDetailGroupByTime(String id) {
		return fourBookDao.getBookDetailGroupByTime(id);
	}
	

}
