package cn.gilight.personal.teacher.teaching.service.impl;

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

import cn.gilight.framework.school.service.SchoolService;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.teacher.teaching.dao.TeacherTeachingDao;
import cn.gilight.personal.teacher.teaching.service.TeacherTeachingService;

@Service("teacherTeachingService")
public class TeacherTeachingServiceImpl implements TeacherTeachingService{

	@Autowired
	private SchoolService schoolService;
	@Autowired
	private TeacherTeachingDao teacherTeachingDao;
	
	@Override
	public List<Map<String, Object>> getTodayClass(String tea_id) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			String today = DateUtils.getNowDate();
			String year_trem[]=EduUtils.getSchoolYearTerm(stringToDate(today));
			String start=schoolService.getStartSchool(year_trem[0],year_trem[1]);
			if(start!=null && !"".equals(start)){
				int zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start),stringToDate(today));
				int weak=DateUtils.getWeekNo(today);
				list = teacherTeachingDao.getTodayClass(year_trem[0], year_trem[1], tea_id, zc, weak);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> getClassSchedule(String tea_id, int zc) {
		String date = DateUtils.getNowDate();
		String year_term[]=EduUtils.getSchoolYearTerm(stringToDate(date));
		String start=schoolService.getStartSchool(year_term[0],year_term[1]);
		if(zc == 0){
			if(StringUtils.hasText(start)){
				zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start),stringToDate(date));
			}
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for(int i=1;i<=9;i=i+2){
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> mylist = teacherTeachingDao.getClassSchedule(tea_id, year_term[0], year_term[1], zc,i);
			for(int j=1;j<=7;j++){
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
				month = DateUtils.getNowMonth();
			}
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
		String zr = DateUtils.getNextDay(zl);
		map.put("year", year);
		map.put("month", month);
		map.put("zc", week);
		map.put("zy", DateUtils.getNowDay(stringToDate(zy)));
		map.put("ze", DateUtils.getNowDay(stringToDate(ze)));
		map.put("zs", DateUtils.getNowDay(stringToDate(zs)));
		map.put("zsi", DateUtils.getNowDay(stringToDate(zsi)));
		map.put("zw", DateUtils.getNowDay(stringToDate(zw)));
		map.put("zl", DateUtils.getNowDay(stringToDate(zl)));
		map.put("zr", DateUtils.getNowDay(stringToDate(zr)));
		map.put("zyrq", zy);
		return map;
	}
	
	public List<Map<String,Object>> getTodayCourse(String courseArrangementId,String course_id){
		return teacherTeachingDao.getTodayCourse(courseArrangementId,course_id);
	}

	@Override
	public List<Map<String, Object>> getTermClass(String tea_id) {
		String date = DateUtils.getNowDate();
		List<Map<String,Object>> resultlist = new ArrayList<Map<String,Object>>();
		try {
			String year_trem[]=EduUtils.getSchoolYearTerm(stringToDate(date));
			String start=schoolService.getStartSchool(year_trem[0],year_trem[1]);
			if(start!=null && !"".equals(start)){
				int zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start),stringToDate(date));
				List<Map<String,Object>> list = teacherTeachingDao.getTermClass(year_trem[0], year_trem[1], tea_id);
				if(list != null && list.size() >0){
					String teachingClassId = MapUtils.getString(list.get(0), "TEACHINGCLASS_ID");
					int count = 0;
					for(int i=0;i<list.size();i++){
						if(teachingClassId.equals(MapUtils.getObject(list.get(i), "TEACHINGCLASS_ID"))){
							count++;
							if(i == list.size()-1){
								String weeks = MapUtils.getString(list.get(i), "WEEKS");
								String[] weekStr = weeks.split(",");
								String startWeek = "0";
								String endWeek = "0";
								if(weekStr.length>1){
									startWeek = weekStr[1];
									endWeek = weekStr[weekStr.length-1];
								}
								String week = MapUtils.getString(list.get(i), "DAY_OF_WEEK");
								Date startDate = DateUtils.getDateByZcAndWeekFromBeginDate(stringToDate(start), Integer.parseInt(startWeek),Integer.parseInt(week));
								Date endDate = DateUtils.getDateByZcAndWeekFromBeginDate(stringToDate(start), Integer.parseInt(endWeek),Integer.parseInt(week));
								int lastweek = 0;
								for(int j=1;j<weekStr.length;j++){
									if(zc > Integer.parseInt(weekStr[j])){
										lastweek ++;
									}
								}
								int classCount = count * (weekStr.length-1)*2;
								int lastclass = lastweek * count*2;
								if(!startDate.equals(endDate)){
									Map<String,Object> m = new HashMap<String,Object>();
									m.put("COURSE_NAME", MapUtils.getString(list.get(i-1), "COURSE_NAME"));
									m.put("START_TIME", DateUtils.date2String(startDate));
									m.put("END_TIME", DateUtils.date2String(endDate));
									m.put("COURSE_TYPE_NAME", MapUtils.getString(list.get(i-1), "COURSE_TYPE"));
									m.put("TEACHINGCLASS", MapUtils.getString(list.get(i-1), "CLASS_NAMES"));
									m.put("CLASSCOUNTS", classCount);
									m.put("ALREADY_CLASS", lastclass);
									m.put("ALREADY_COUNTS", MathUtils.getPercent(lastclass, classCount));
									resultlist.add(m);
								}
							}
						}else{
							String weeks = MapUtils.getString(list.get(i-1), "WEEKS");
							String[] weekStr = weeks.split(",");
							String startWeek = "0";
							String endWeek = "0";
							if(weekStr.length>1){
								startWeek = weekStr[1];
								endWeek = weekStr[weekStr.length-1];
							}
							String week = MapUtils.getString(list.get(i-1), "DAY_OF_WEEK");
							Date startDate = DateUtils.getDateByZcAndWeekFromBeginDate(stringToDate(start), Integer.parseInt(startWeek),Integer.parseInt(week));
							Date endDate = DateUtils.getDateByZcAndWeekFromBeginDate(stringToDate(start), Integer.parseInt(endWeek),Integer.parseInt(week));
							int lastweek = 0;
							for(int j=1;j<weekStr.length;j++){
								if(zc > Integer.parseInt(weekStr[j])){
									lastweek ++;
								}
							}
							int classCount = count * (weekStr.length-1)*2;
							int lastclass = lastweek * count*2;
							if(!startDate.equals(endDate)){
								Map<String,Object> m = new HashMap<String,Object>();
								m.put("COURSE_NAME", MapUtils.getString(list.get(i-1), "COURSE_NAME"));
								m.put("START_TIME", DateUtils.date2String(startDate));
								m.put("END_TIME", DateUtils.date2String(endDate));
								m.put("COURSE_TYPE_NAME", MapUtils.getString(list.get(i-1), "COURSE_TYPE"));
								m.put("TEACHINGCLASS", MapUtils.getString(list.get(i-1), "CLASS_NAMES"));
								m.put("CLASSCOUNTS", classCount);
								m.put("ALREADY_CLASS", lastclass);
								m.put("ALREADY_COUNTS", MathUtils.getPercent(lastclass, classCount));
								resultlist.add(m);
							}
							teachingClassId = MapUtils.getString(list.get(i), "TEACHINGCLASS_ID");
							count = 1;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultlist;
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

	
}
