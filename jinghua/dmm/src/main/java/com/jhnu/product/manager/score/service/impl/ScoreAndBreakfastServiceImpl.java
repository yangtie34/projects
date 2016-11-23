package com.jhnu.product.manager.score.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.score.dao.ScoreAndBreakfastDao;
import com.jhnu.product.manager.score.service.ScoreAndBreakfastService;
import com.jhnu.util.common.StringUtils;
import com.jhnu.util.product.EduUtils;

@Service("scoreAndBreakfastService")
public class ScoreAndBreakfastServiceImpl implements ScoreAndBreakfastService {

	@Autowired
	private ScoreAndBreakfastDao scoreAndBreakfastDao;

	@Override
	public void saveScoreAndBreakfast() {
		String[] strs = EduUtils.getProSchoolYearTerm(new Date());
		List<Map<String, Object>> results = scoreAndBreakfastDao
				.getScoreAndBreakfast(strs[0], strs[1]);
		scoreAndBreakfastDao.saveScoreAndBreakfast(results);
	}

	@Override
	public List<Map<String, Object>> getBoyScoreAndBreakfastLog(
			String school_year, String term_code, String dept_id, boolean isLeaf) {
		if (!StringUtils.hasText(school_year)) {
			school_year = EduUtils.getProSchoolYearTerm(new Date())[0];
		}
		if (!StringUtils.hasText(term_code)) {
			term_code = EduUtils.getProSchoolYearTerm(new Date())[1];
		}
		return scoreAndBreakfastDao.getBoyScoreAndBreakfastLog(school_year,
				term_code, dept_id, isLeaf);
	}

	@Override
	public List<Map<String, Object>> getGrilScoreAndBreakfastLog(
			String school_year, String term_code, String dept_id, boolean isLeaf) {
		if (!StringUtils.hasText(school_year)) {
			school_year = EduUtils.getProSchoolYearTerm(new Date())[0];
		}
		if (!StringUtils.hasText(term_code)) {
			term_code = EduUtils.getProSchoolYearTerm(new Date())[1];
		}
		return scoreAndBreakfastDao.getGrilScoreAndBreakfastLog(school_year,
				term_code, dept_id, isLeaf);
	}

	@Override
	public List<Map<String, Object>> getAllStusScoreAndBreakfastLog(
			String school_year, String term_code, String dept_id, boolean isLeaf) {
		List<Map<String, Object>> girlBreakfastAndScore = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> boyBreakfastAndScore = new ArrayList<Map<String, Object>>();
		Map<String, Object> stusMap = new HashMap<String, Object>();
		boyBreakfastAndScore = scoreAndBreakfastDao
				.getBoyScoreAndBreakfastLog(school_year, term_code, dept_id,
						isLeaf);
		girlBreakfastAndScore = scoreAndBreakfastDao
				.getGrilScoreAndBreakfastLog(school_year, term_code, dept_id,
						isLeaf);
		stusMap.put("boyBreakfastAndScore", boyBreakfastAndScore);
		stusMap.put("girlBreakfastAndScore", girlBreakfastAndScore);
		List<Map<String, Object>> allStusScoreAndBreakfast = new ArrayList<Map<String, Object>>();
		allStusScoreAndBreakfast.add(stusMap);
		return allStusScoreAndBreakfast;
	}

}
