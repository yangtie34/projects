package cn.gilight.personal.student.course.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import test.test1.util.MapUtils;
import cn.gilight.framework.school.service.SchoolService;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.student.course.dao.CourseDao;
import cn.gilight.personal.student.course.service.CourseService;

@Service("courseService")
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private SchoolService schoolService;

	@Override
	public Map<String, Object> getToday() {
		Map<String,Object> map = new HashMap<String,Object>();
		String month = DateUtils.getNowMonth();
		String day = DateUtils.getNowDay();
		String nowDate = month+"月"+day+"日";
		String week = "星期"+DateUtils.getWeekCn();
		String date = DateUtils.getNowDate();
		String[] terms = EduUtils.getSchoolYearTerm(stringToDate(date));
		String school_year = terms[0]+"学年";
		String term_code = "第一学期";
		if("02".equals(terms[1])){
			term_code = "第二学期";
		}
		String start = schoolService.getStartSchool(terms[0], terms[1]);
		if(StringUtils.hasText(start)){
			int zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start), stringToDate(date));
			map.put("nowDate", nowDate);
			map.put("week", week);
			map.put("school_year", school_year);
			map.put("term_code", term_code);
			map.put("zc", zc);
		}
		return map;
	}

	@Override
	public List<Map<String, Object>> getTodayCourse(String stu_id) {
		Map<String,Object> map = getToday();
		String date = DateUtils.getNowDate();
		String[] terms = EduUtils.getSchoolYearTerm(stringToDate(date));
		int zc = MapUtils.getIntValue(map, "zc");
		int week = DateUtils.getWeekNo();
		return courseDao.getTodayCourse(stu_id,terms[0],terms[1],zc,week);
	}
	
	@Override
	public List<Map<String, Object>> getSchedule(String stu_id,int zc) {
		String date = DateUtils.getNowDate();
		String[] terms = EduUtils.getSchoolYearTerm(stringToDate(date));
		String start = schoolService.getStartSchool(terms[0], terms[1]);
		if(zc == 0){
			if(StringUtils.hasText(start)){
				zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start),stringToDate(date));
			}
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=1;i<=9;i=i+2){
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> mylist = courseDao.getSchedule(stu_id, terms[0], terms[1], zc,i);
			for(int j=1;j<7;j++){
				Map<String,Object> m = new HashMap<String,Object>();
				boolean b = false;
				for(Map<String,Object> mymap : mylist){
					int week = MapUtils.getIntValue(mymap, "day_of_week");
					if(week == j){
					    b = true;
					}
				}
				if(!b){
					m.put("course_name", "");
					m.put("classroom", "");
					m.put("day_of_week", j);
					m.put("time_", "");
					m.put("period_start", "");
					mylist.add(j-1, m);
				}
			}
			map.put("jc", i);
			map.put("list", mylist);
			list.add(map);
		}
		return list;
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
	public Map<String, Object> getWeek(int week,String zyrq,String flag) {
		Map<String,Object> map = new HashMap<String,Object>();
		String year = DateUtils.getNowYear();
		String month = "";
		if(week == 0){
			String[] terms = EduUtils.getSchoolYearTerm(new Date());
			String start = schoolService.getStartSchool(terms[0], terms[1]);
			if(StringUtils.hasText(start)){
				int zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start), new Date());
				week = zc;
			}
			month = DateUtils.getNowMonth();
		}
		String zy = "";
		if(StringUtils.hasText(zyrq)){
			if("-".equals(flag)){
				zy = DateUtils.getLastWeek(stringToDate(zyrq));
			}else if("+".equals(flag)){
				zy = DateUtils.getNextWeek(stringToDate(zyrq));
			}
		}else{
			zy = DateUtils.getWeekFirstDay();
		}
		month = DateUtils.getNowMonth(stringToDate(zy));
		String ze = DateUtils.getNextDay(zy);
		String zs = DateUtils.getNextDay(ze);
		String zsi = DateUtils.getNextDay(zs);
		String zw = DateUtils.getNextDay(zsi);
		String zl = DateUtils.getNextDay(zw);
		map.put("year", year);
		map.put("month", month);
		map.put("zc", week);
		map.put("zy", DateUtils.getNowDay(stringToDate(zy)));
		map.put("ze", DateUtils.getNowDay(stringToDate(ze)));
		map.put("zs", DateUtils.getNowDay(stringToDate(zs)));
		map.put("zsi", DateUtils.getNowDay(stringToDate(zsi)));
		map.put("zw", DateUtils.getNowDay(stringToDate(zw)));
		map.put("zl", DateUtils.getNowDay(stringToDate(zl)));
		map.put("zyrq", zy);
		return map;
	}

	@Override
	public List<Map<String, Object>> getEmptyClassroom(String date,
			int period_start, int period_end) {
		String[] terms = EduUtils.getSchoolYearTerm(stringToDate(date));
		String start = schoolService.getStartSchool(terms[0], terms[1]);
		int zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start), stringToDate(date));
		int week = DateUtils.getWeekNo();
		return courseDao.getEmptyClassroom(terms[0], terms[1], zc, week, period_start, period_end);
	}

	

}
