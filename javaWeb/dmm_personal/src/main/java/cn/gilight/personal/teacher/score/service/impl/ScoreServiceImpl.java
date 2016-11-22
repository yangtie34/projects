package cn.gilight.personal.teacher.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.personal.teacher.score.dao.ScoreDao;
import cn.gilight.personal.teacher.score.service.ScoreService;

@Service("scoreService")
public class ScoreServiceImpl implements ScoreService{
	
	@Autowired
	private ScoreDao scoreDao;

	@Override
	public List<Map<String, Object>> getScoreClasses(String school_year,String term_code,String tea_id) {
		return scoreDao.getScoreClasses(school_year,term_code,tea_id);
	}
	
	@Override
	public List<Map<String, Object>> getCourseScore(String school_year,String term_code,String tea_id) {
		return scoreDao.getCourseScore(school_year, term_code, tea_id);
	}
	
	

	@Override
	public List<Map<String, Object>> getStuScore(String school_year,
			String term_code, String class_id,String course_id,String param,String paramFlag) {
		List<Map<String, Object>> list = null;
		if("xzb".equals(paramFlag)){
			list = scoreDao.getStuScore(school_year,term_code,class_id,param);
		}else if("jxb".equals(paramFlag)){
			list = scoreDao.getStuScoreJxb(school_year,term_code,class_id,course_id,param);
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getStuTotalScore(String school_year,
			String term_code, String stu_id) {
		return scoreDao.getStuTotalScore(school_year,term_code,stu_id);
	}

	@Override
	public List<Map<String, Object>> getStuScoreDetail(String school_year,
			String term_code, String stu_id) {
		return scoreDao.getStuScoreDetail(school_year,term_code,stu_id);
	}
	
	
}
