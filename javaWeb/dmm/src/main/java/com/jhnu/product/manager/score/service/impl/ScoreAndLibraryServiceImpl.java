package com.jhnu.product.manager.score.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.manager.score.dao.ScoreAndLibraryDao;
import com.jhnu.product.manager.score.service.ScoreAndLibraryService;
import com.jhnu.util.common.StringUtils;
import com.jhnu.util.product.EduUtils;

@Service("scoreAndLibraryService")
public class ScoreAndLibraryServiceImpl implements ScoreAndLibraryService {
	@Autowired
	private ScoreAndLibraryDao scoreAndLibraryDao;

	@Override
	public void saveScoreAndLibraryLog() {
		String[] strs = EduUtils.getProSchoolYearTerm(new Date());
		List<Map<String, Object>> results = scoreAndLibraryDao
				.getScoreAndLibrary(strs[0], strs[1]);
		scoreAndLibraryDao.saveScoreAndLibraryLog(results);
	}

	@Override
	public List<Map<String, Object>> getBoyScoreAndLibraryLog(
			String school_year, String term_code, String dept_id, boolean isLeaf) {
		if (!StringUtils.hasText(school_year)) {
			school_year = EduUtils.getProSchoolYearTerm(new Date())[0];
		}
		if (!StringUtils.hasText(term_code)) {
			term_code = EduUtils.getProSchoolYearTerm(new Date())[1];
		}
		return scoreAndLibraryDao.getBoyScoreAndLibraryLog(school_year,
				term_code, dept_id, isLeaf);
	}

	@Override
	public List<Map<String, Object>> getGrilScoreAndLibraryLog(
			String school_year, String term_code, String dept_id, boolean isLeaf) {
		if (!StringUtils.hasText(school_year)) {
			school_year = EduUtils.getProSchoolYearTerm(new Date())[0];
		}
		if (!StringUtils.hasText(term_code)) {
			term_code = EduUtils.getProSchoolYearTerm(new Date())[1];
		}
		return scoreAndLibraryDao.getGrilScoreAndLibraryLog(school_year,
				term_code, dept_id, isLeaf);
	}

	@Override
	public List<Map<String, Object>> getStusScoreAndLibraryLog(
			String school_year, String term_code, String dept_id, boolean isLeaf) {
		List<Map<String, Object>> girlScoreAndLibrary = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> boyScoreAndLibrary = new ArrayList<Map<String, Object>>();
		Map<String, Object> stusMap = new HashMap<String, Object>();
		boyScoreAndLibrary = scoreAndLibraryDao.getBoyScoreAndLibraryLog(school_year, term_code,
				dept_id, isLeaf);
		girlScoreAndLibrary = scoreAndLibraryDao.getGrilScoreAndLibraryLog(school_year, term_code,
				dept_id, isLeaf);
		stusMap.put("boyScoreAndLibrary", boyScoreAndLibrary);
		stusMap.put("girlScoreAndLibrary", girlScoreAndLibrary);
		List<Map<String, Object>> allStusScoreAndBreakfast = new ArrayList<Map<String, Object>>();
		allStusScoreAndBreakfast.add(stusMap);
		return allStusScoreAndBreakfast;
	}
}
