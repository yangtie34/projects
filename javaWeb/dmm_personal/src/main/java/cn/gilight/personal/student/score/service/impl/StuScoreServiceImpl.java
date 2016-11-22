package cn.gilight.personal.student.score.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import test.test1.util.MapUtils;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.student.score.dao.StuScoreDao;
import cn.gilight.personal.student.score.service.StuScoreService;

@Service("stuScoreService")
public class StuScoreServiceImpl implements StuScoreService {

	@Autowired
	private StuScoreDao stuScoreDao;

	@Override
	public Map<String, Object> getLastScore(String stu_id) {
		String date = DateUtils.getNowDate();
		String[] terms = EduUtils.getProSchoolYearTerm(stringToDate(date));
		return stuScoreDao.getScore(stu_id,terms[0],terms[1]);
	}
	
	@Override
	public Map<String, Object> getProportion(String stu_id) {
		Map<String,Object> map = getLastScore(stu_id);
		int rank = MapUtils.getIntValue(map, "rank_");
		int total = stuScoreDao.getMajorCounts(stu_id);
		int beat = total - rank;
		double d = 0;
		if(total != 0){
			d = (double)beat/total;
		}
		int pro = (int) (MathUtils.get2Point(d)*100);
		
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("beat", beat);
		m.put("rank", rank);
		m.put("proportion", pro+"%");
		return m;
	}
	

	@Override
	public List<Map<String, Object>> getScoreList(String stu_id) {
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list = stuScoreDao.getScoreTerm(stu_id);
		if(list != null && list.size()>0){
			for(Map<String,Object> map : list){
				String school_year = MapUtils.getString(map, "school_year");
				String term_code = MapUtils.getString(map, "term_code");
				String schoolYear = school_year+"学年";
				String termCode = "第一学期";
				if("02".equals(term_code)){
					termCode = "第二学期";
				}
				Map<String,Object> resultMap = new HashMap<String,Object>();
				resultMap.put("schoolYear", schoolYear);
				resultMap.put("termCode", termCode);
				resultMap.put("school_year", school_year);
				resultMap.put("term_code", term_code);
				List<Map<String,Object>> myList = stuScoreDao.getScoreBySchoolTerm(stu_id,school_year,term_code);
				resultMap.put("list", myList);
				resultList.add(resultMap);
			}
		}
		return resultList;
	}
	
	private Date stringToDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	@Override
	public Map<String, Object> getCredit(String stu_id) {
		Map<String,Object> map = new HashMap<String,Object>();
		Map<String,Object> map1 = stuScoreDao.getTotalCredit(stu_id);
		Map<String,Object> map2 = stuScoreDao.getMyCredit(stu_id);
		map.put("total_credit", MapUtils.getDoubleValue(map1, "total_credit"));
		map.put("my_credit", MapUtils.getDoubleValue(map2, "my_credit"));
		return map;
	}

	@Override
	public Map<String, Object> getCreditType(String stu_id) {
		Map<String,Object> map1 = stuScoreDao.getTotalCreditCourseAttr(stu_id);
		Map<String,Object> map2 = stuScoreDao.getMyCreditCourseAttr(stu_id);
		Map<String,Object> result = new HashMap<String,Object>();
		result.putAll(map1);
		result.putAll(map2);
		return result;
	}




	
}
