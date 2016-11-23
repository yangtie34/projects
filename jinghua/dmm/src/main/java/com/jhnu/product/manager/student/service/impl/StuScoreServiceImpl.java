package com.jhnu.product.manager.student.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.student.dao.StuScoreDao;
import com.jhnu.product.manager.student.service.StuScoreService;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.MathUtils;
import com.jhnu.util.common.StringUtils;
import com.jhnu.util.product.EduUtils;

@Service("stuScoreService")
public class StuScoreServiceImpl implements StuScoreService{

	@Autowired
	private StuScoreDao stuScoreDao;
	
	@Override
	public void saveStuScoreLog() {
		String[] strs = EduUtils.getProSchoolYearTerm(new Date());
		String school_year = strs[0];
		String term_code = strs[1];
		List<Map<String,Object>> deptScore = stuScoreDao.getDeptScore(school_year, term_code);
		List<Map<String,Object>> majorScore = stuScoreDao.getMajorScore(school_year, term_code);
		
		List<Map<String,Object>> depts = stuScoreDao.getDept();
		
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		
		//获取院系成绩
		if(deptScore != null){
			for(Map<String,Object> map : depts){
				List<Double> deptCj = new ArrayList<Double>();
				for(Map<String,Object> smap : deptScore){
					if(MapUtils.getString(map, "ID").equals(MapUtils.getString(smap, "DEPT_ID"))){
						deptCj.add(MapUtils.getDouble(smap, "SCORE"));
					}
				}
				Map<String,Object> m = new HashMap<String,Object>();
				m.put("ID", MapUtils.getString(map, "ID"));
				m.put("PID", 0);
				m.put("MC", MapUtils.getString(map, "NAME_"));
				m.put("LEVEL_", 1);
				m.put("SCHOOL_YEAR", school_year);
				m.put("TERM_CODE", term_code);
				m.put("AVG", MathUtils.getAvgvalue(deptCj));
				m.put("MAX", MathUtils.getMaxValue(deptCj));
				m.put("JC", MathUtils.getRange(deptCj));
				m.put("ZWS", MathUtils.getMiddleValue(deptCj));
				m.put("FC", MathUtils.getVariance(deptCj));
				m.put("BZC", MathUtils.getStandardDeviation(deptCj));
				m.put("HGL", MathUtils.getScale(deptCj, 60d));
				m.put("YXL", MathUtils.getScale(deptCj, 90d));
				results.add(m);
			}
		}
		
		//获取专业成绩
		if(majorScore != null){
			for(Map<String,Object> map : depts){
				List<Map<String,Object>> majors = stuScoreDao.getMajor(MapUtils.getString(map, "ID"));
				for(Map<String,Object> mm : majors){
					List<Double> majorCj = new ArrayList<Double>();
					for(Map<String,Object> smap : majorScore){
						if(MapUtils.getString(mm, "ID").equals(MapUtils.getString(smap, "MAJOR_ID"))){
							majorCj.add(MapUtils.getDouble(smap, "SCORE"));
						}
					}
					Map<String,Object> m = new HashMap<String,Object>();
					m.put("ID", MapUtils.getString(mm, "ID").toString());
					m.put("PID", MapUtils.getString(mm, "PID").toString());
					m.put("MC", MapUtils.getString(mm, "NAME_").toString());
					m.put("LEVEL_", 2);
					m.put("SCHOOL_YEAR", school_year);
					m.put("TERM_CODE", term_code);
					m.put("AVG", MathUtils.getAvgvalue(majorCj));
					m.put("MAX", MathUtils.getMaxValue(majorCj));
					m.put("JC", MathUtils.getRange(majorCj));
					m.put("ZWS", MathUtils.getMiddleValue(majorCj));
					m.put("FC", MathUtils.getVariance(majorCj));
					m.put("BZC", MathUtils.getStandardDeviation(majorCj));
					m.put("HGL", MathUtils.getScale(majorCj, 60d));
					m.put("YXL", MathUtils.getScale(majorCj, 90d));
					results.add(m);
				}
			}
		}
		stuScoreDao.saveStuScoreLog(results);
	}

	@Override
	public List<Map<String, Object>> getTermScoreByDept(String dept_id) {
		if(!StringUtils.hasText(dept_id)){
			dept_id = "0";
		}
		return stuScoreDao.getTermScoreByDept(dept_id);
	}

	@Override
	public List<Map<String, Object>> getScoreFb(String dept_id,String school_year, String term_code) {
		if(!StringUtils.hasText(dept_id)){
			dept_id = "0";
		}
		if(!StringUtils.hasText(school_year)){
			school_year = EduUtils.getProSchoolYearTerm(new Date())[0];
		}
		if(!StringUtils.hasText(term_code)){
			term_code = EduUtils.getProSchoolYearTerm(new Date())[1];
		}
		
		List<Map<String,Object>> scoreFb = stuScoreDao.getScoreFb(dept_id, school_year, term_code);
		/*int sum=0;
		int nextNum=90;
		List<Map<String,Object>> reslut=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=null;
		for (int i = 0; i < scoreFb.size(); i++) {
			double cj=Double.parseDouble(MapUtils.getString(scoreFb.get(i), "SCORE").toString());
			double nextCj;
			if((i+1)!=scoreFb.size()){
				nextCj=Double.parseDouble(MapUtils.getString(scoreFb.get(i+1), "SCORE").toString());
			}else{
				nextCj=cj;
			}
			if(cj>=90){
				if(nextNum==90){
					map=new HashMap<String, Object>();
					map.put("field", "90-100");
					map.put("name", "人数");
					nextNum=80;
				}
				sum+=Double.parseDouble(MapUtils.getString(scoreFb.get(i), "NUMBERS").toString());
				if(nextCj<90 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=80){
				if(nextNum==80){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "80-89");
					map.put("name", "人数");
					nextNum=70;
				}
				sum+=Double.parseDouble(MapUtils.getString(scoreFb.get(i), "NUMBERS").toString());
				if(nextCj<80 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=70){
				if(nextNum==70){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "70-79");
					map.put("name", "人数");
					nextNum=60;
				}
				sum+=Double.parseDouble(MapUtils.getString(scoreFb.get(i), "NUMBERS").toString());
				if(nextCj<70 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=60){
				if(nextNum==60){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "60-69");
					map.put("name", "人数");
					nextNum=50;
				}
				sum+=Double.parseDouble(MapUtils.getString(scoreFb.get(i), "NUMBERS").toString());
				if(nextCj<60 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=50){
				if(nextNum==50){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "50-59");
					map.put("name", "人数");
					nextNum=30;
				}
				sum+=Double.parseDouble(MapUtils.getString(scoreFb.get(i), "NUMBERS").toString());
				if(nextCj<50 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else if(cj>=30){
				if(nextNum==30){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "30-59");
					map.put("name", "人数");
					nextNum=0;
				}
				sum+=Double.parseDouble(MapUtils.getString(scoreFb.get(i), "NUMBERS").toString());
				if(nextCj<30 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}else{
				if(nextNum==0){
					map=new HashMap<String, Object>();
					sum=0;
					map.put("field", "0-39");
					map.put("name", "人数");
					nextNum=-1;
				}
				sum+=Double.parseDouble(MapUtils.getString(scoreFb.get(i), "NUMBERS").toString());
				if(nextCj<=0 || cj==nextCj){
					map.put("value", sum);
					reslut.add(map);
				}
			}
		}*/
		return scoreFb;
	}

	@Override
	public List<Map<String, Object>> getScoreLog(String dept_id,boolean isLeaf,String school_year, String term_code) {
		if(!StringUtils.hasText(school_year)){
			school_year = EduUtils.getProSchoolYearTerm(new Date())[0];
		}
		if(!StringUtils.hasText(term_code)){
			term_code = EduUtils.getProSchoolYearTerm(new Date())[1];
		}
		return stuScoreDao.getScoreLog(dept_id, isLeaf, school_year, term_code);
	}

}
