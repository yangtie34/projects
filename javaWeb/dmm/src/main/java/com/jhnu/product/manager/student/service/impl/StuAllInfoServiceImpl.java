package com.jhnu.product.manager.student.service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.parboiled.common.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhnu.product.common.school.service.SchoolService;
import com.jhnu.product.manager.student.dao.StuAllInfoDao;
import com.jhnu.product.manager.student.service.IStuAllInfoService;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.product.EduUtils;

/**
 * @title 学生全景信息Service实现类
 * @description 学生全景信息
 * @author Administrator
 * @date 2015/10/28 14:43
 */
@Service("stuAllInfoService")
public class StuAllInfoServiceImpl implements IStuAllInfoService {

	// 自动注入StuAllInfoDao
	@Autowired
	private StuAllInfoDao stuAllInfoDao;

	@Autowired
	private SchoolService schoolService;

	@Override
	public List<Map<String, Object>> getStuInfo(String stu_id) {
		return stuAllInfoDao.getStuInfo(stu_id);
	}

	@Override
	public Map<String, Object> stusPunishmentViolation(String stuId) {
		return stuAllInfoDao.stusPunishmentViolation(stuId);
	}

	@Override
	public List<Map<String, Object>> getAward(String stu_id) {
		return stuAllInfoDao.getAward(stu_id);
	}

	@Override
	public List<Map<String, Object>> getSubsidy(String stu_id) {
		return stuAllInfoDao.getSubsidy(stu_id);
	}

	@Override
	public List<Map<String, Object>> getRoommate(String stu_id) {
		return stuAllInfoDao.getRoommate(stu_id);
	}

	public Map<String, Object> stusCultureScore(String stu_id, String termCode, String schoolYear) {
		Map<String, Object> cultureScoreMap = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		DecimalFormat df = new DecimalFormat("0.00");
		int totalScore = 0; // 总成绩
		int oneScoreMax = 0; // 单科成绩最高分
		int oneScoreMin = 0;// 单科成绩最低分
		String oneScoreMaxName = "";// 单科成绩最高课程名称
		String oneScoreMinName = "";// 单科成绩最低课程名称
		List<Map<String, Object>> cultureScoreList = new ArrayList<Map<String, Object>>();
		cultureScoreList = stuAllInfoDao.stusCultureScore(stu_id, termCode, schoolYear);
		for (Map<String, Object> scoreMap : cultureScoreList) {
			totalScore += Integer.parseInt(MapUtils.getString(scoreMap, "CENSCORE"));
		}
		Map<String, Object> stuIfo = new HashMap<String,Object>();
		if(cultureScoreList!= null && cultureScoreList.size()>0){
			oneScoreMax = Integer.parseInt(MapUtils.getString(cultureScoreList.get(0), "CENSCORE"));
			oneScoreMaxName = MapUtils.getString(cultureScoreList.get(0), "COURSENAME");
	
			oneScoreMin = Integer.parseInt(MapUtils.getString(cultureScoreList.get(cultureScoreList.size() - 1), "CENSCORE"));
			oneScoreMinName = MapUtils.getString(cultureScoreList.get(cultureScoreList.size() - 1), "COURSENAME");
			
			stuIfo = cultureScoreList.get(0);
		}
		cultureScoreMap.put("oneScoreMax", oneScoreMax);
		cultureScoreMap.put("oneScoreMaxName", oneScoreMaxName);
		cultureScoreMap.put("avgScore", df.format(((float) (totalScore)) / ((float) (cultureScoreList.size())))); // 平均成绩
		cultureScoreMap.put("totalScore", totalScore);
		cultureScoreMap.put("oneScoreMin", oneScoreMin);
		cultureScoreMap.put("oneScoreMinName", oneScoreMinName);

		resultMap.put("cultureScoreMap", cultureScoreMap);
		resultMap.put("cultureScoreList", cultureScoreList);
		resultMap.put("stuIfo", stuIfo);

		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getLeaveByStu(String stu_id) {
		return stuAllInfoDao.getLeaveByStu(stu_id);
	}

	@Override
	public List<Map<String, Object>> getLeaveInfoByStu(String stu_id) {
		return stuAllInfoDao.getLeaveInfoByStu(stu_id);
	}

	@Override
	public List<Map<String, Object>> getBookBorrowByStu(String stu_id) {
		return stuAllInfoDao.getBookBorrowByStu(stu_id);
	}

	@Override
	public List<Map<String, Object>> getCoureArrangementByStu(String stu_id, String zc) {
		if(StringUtils.isEmpty(zc)){
			String[] yearTerm = EduUtils.getSchoolYearTerm(new Date());
			String startDate = this.schoolService.getStartSchool(yearTerm[0], yearTerm[1]);
			try {
				zc = new Integer(DateUtils.getZcByDateFromBeginDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate), new Date())).toString();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return stuAllInfoDao.getCoureArrangementByStu(stu_id, zc);
	}

	@Override
	public Map<String, Object> getCardLog(String stu_id) {
		return stuAllInfoDao.getTotalAndAvgMoneyLog(stu_id);
	}

	@Override
	public void saveCard() {
		List<Map<String, Object>> list = stuAllInfoDao.getTotalAndAvgMoney();
		stuAllInfoDao.saveTotalAndAvgMoney(list);
	}

	@Override
	public List<Map<String, Object>> getCardByYear(String stu_id, String school_year, String term_code) {
		String year = school_year.substring(0, 9);
		String term = "01";
		if ("第二学期".equals(term_code)) {
			term = "02";
		}
		return stuAllInfoDao.getCardLog(stu_id, year, term);
	}

	@Override
	public void saveStuAllCard(String school_year, String term_code) {
		String[] date = EduUtils.getTimeQuantum(school_year, term_code);
		Map<String, Object> allCard = stuAllInfoDao.getAllCard(date[0], date[1]);
		allCard.put("school_year", school_year);
		allCard.put("term_code", term_code);
		List<Map<String, Object>> sexCard = stuAllInfoDao.getSexCard(date[0], date[1]);
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		result.add(allCard);
		if (sexCard != null) {
			for (Map<String, Object> m : sexCard) {
				m.put("school_year", school_year);
				m.put("term_code", term_code);
				result.add(m);
			}
		}
		List<Map<String, Object>> stuCard = stuAllInfoDao.getCard(date[0], date[1]);
		for (Map<String, Object> map : stuCard) {
			String stu_name = MapUtils.getString(map, "stu_name");
			String stu_sex = MapUtils.getString(map, "stu_sex");
			String stu_type = stu_name + "(" + stu_sex + ")";
			map.put("stu_type", stu_type);
			map.put("school_year", school_year);
			map.put("term_code", term_code);
			result.add(map);
		}
		stuAllInfoDao.saveStuAllCard(result);
	}

	@Override
	public List<String> getCoureArrangementInitByStu(String year, String term, String zc) {
		List<String> result = new ArrayList<String>();
		String startDate = this.schoolService.getStartSchool(year, term);

		List<Map<String, Object>> weeks = this.stuAllInfoDao.getCourseWeeks(year, term);
		Set<Integer> setWeek = new HashSet<Integer>();
		for (Iterator<Map<String, Object>> i = weeks.iterator(); i.hasNext();) {
			Map<String, Object> map = i.next();
			String weekStr = (String) map.get("weeks");
			String[] weekStrArr = weekStr.split(",");
			setWeek.add(Integer.parseInt(weekStrArr[weekStrArr.length - 1]));
		}
		for (int i = 0; i < Collections.max(setWeek); i++) {
			try {
				// 一周的开始日期
				String weekStartDate = DateUtils.getDateByStartZcWeak(startDate, i + 1, 1);
				// 一周的结束日期
				String weekendDate = DateUtils.getDateByStartZcWeak(startDate, i + 1, 7);
				int zcNum = StringUtils.isEmpty(zc) ? DateUtils.getZcByDateFromBeginDate(new SimpleDateFormat("yyyy-MM-dd").parse(startDate), new Date()) : Integer.parseInt(zc);
				if (zcNum == (i + 1)) {
					result.add("<option value=\"" + (i + 1) + "\"  selected = \"selected\">第" + (i + 1) + "周(" + weekStartDate + "至" + weekendDate + ")</option>");
				} else {
					result.add("<option value=\"" + (i + 1) + "\">第" + (i + 1) + "周(" + weekStartDate + "至" + weekendDate + ")</option>");
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> oneStuCompareClassAndMajor(String stu_id, String termCode, String schoolYear) {
		Map<String, Object> stuCompareMajorClasses = new HashMap<String, Object>();
		List<Map<String, Object>> stuScores = stuAllInfoDao.stuScoreDescName(stu_id, termCode, schoolYear);
		List<Map<String, Object>> classScores = stuAllInfoDao.oneStuCompareClasses(stu_id, termCode, schoolYear);
		List<Map<String, Object>> majorScores = stuAllInfoDao.oneStuCompareMajor(stu_id, termCode, schoolYear);
		List<Map<String, Object>> classList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> majorList = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("0.00");
		int classNums = stuAllInfoDao.stuClassNums(stu_id).size();
		int majorNums = stuAllInfoDao.stuMajorNums(stu_id).size();
		if (classNums > 0 && majorNums > 0) {
			for (Map<String, Object> classMap : classScores) {
				Map<String, Object> avgScoresMapClass = new HashMap<String, Object>();
				String courseName = MapUtils.getString(classMap, "COURSENAME");
				int totalScores = Integer.parseInt(MapUtils.getString(classMap, "ALLSCORES"));
				avgScoresMapClass.put("courseName", courseName);
				avgScoresMapClass.put("avgScore", df.format(((float) totalScores) / ((float) classNums)));
				classList.add(avgScoresMapClass);
			}
			for (Map<String, Object> majorMap : majorScores) {
				Map<String, Object> avgScoresMapMajor = new HashMap<String, Object>();
				String courseName = MapUtils.getString(majorMap, "COURSENAME");
				int totalScores = Integer.parseInt(MapUtils.getString(majorMap, "ALLSCORES"));
				avgScoresMapMajor.put("courseName", courseName);
				avgScoresMapMajor.put("avgScore", df.format(((float) totalScores) / ((float) majorNums)));
				majorList.add(avgScoresMapMajor);
			}
		}
		stuCompareMajorClasses.put("stuScores", stuScores);
		stuCompareMajorClasses.put("majorList", majorList);
		stuCompareMajorClasses.put("classList", classList);
		return stuCompareMajorClasses;
	}

	@Override
	public Map<String, Object> stuClassMajorRank(String stuId, String termCode, String schoolYear) {
		List<Map<String, Object>> classRankings = stuAllInfoDao.stuClassRankings(stuId, termCode, schoolYear);
		List<Map<String, Object>> majorRankings = stuAllInfoDao.stuMajorRankings(stuId, termCode, schoolYear);
		List<Map<String, Object>> stuScores = stuAllInfoDao.stuScoreAscName(stuId, termCode, schoolYear);
		List<Map<String, Object>> classresultList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> majorresultList = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();

		for (Map<String, Object> classMaps : classRankings) {
			String stuNos = MapUtils.getString(classMaps, "STUNOS");
			if (stuNos.equals(stuId)) {
				Map<String, Object> stusClassMap = new HashMap<String, Object>();
				stusClassMap.put("classRanks", MapUtils.getString(classMaps, "RANKS"));
				stusClassMap.put("courseNames", MapUtils.getString(classMaps, "COURSENAME"));
				classresultList.add(stusClassMap);
			}
		}

		for (Map<String, Object> majorMaps : majorRankings) {
			String stuNos = MapUtils.getString(majorMaps, "STUNOS");
			if (stuNos.equals(stuId)) {
				Map<String, Object> stusMajorMap = new HashMap<String, Object>();
				stusMajorMap.put("majorRanks", MapUtils.getString(majorMaps, "RANKS"));
				stusMajorMap.put("courseNames", MapUtils.getString(majorMaps, "COURSENAME"));
				majorresultList.add(stusMajorMap);
			}

		}
		resultMap.put("stuScores", stuScores);
		resultMap.put("classresultList", classresultList);
		resultMap.put("majorresultList", majorresultList);
		return resultMap;
	}

}
