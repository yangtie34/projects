package com.jhnu.product.warn.score.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.warn.score.dao.ScoreWarnDao;
import com.jhnu.product.warn.score.service.ScoreWarnService;

@Service("scoreWarnService")
public class ScoreWarnServiceImpl implements ScoreWarnService{

	@Autowired
	private ScoreWarnDao scoreWarnDao;
	
	@Override
	public List<Map<String, Object>> getScores(String stuId) {
		return scoreWarnDao.getScores(stuId);
	}

}
