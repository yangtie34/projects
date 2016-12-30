package com.jhnu.person.tea.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.tea.dao.TeaTeachDao;
import com.jhnu.syspermiss.school.service.SchoolService;
import com.jhnu.syspermiss.util.DateUtils;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.MathUtils;
import com.jhnu.util.product.EduUtils;

@Repository("teaTeachDao")
public class TeaTeachDaoImpl implements TeaTeachDao {
	@Autowired
	private BaseDao baseDao;
	/**
	 * 通过学年学期获取开学时间
	 * 
	 * @param year
	 *            学年
	 * @param term
	 *            学期
	 * @return 开学时间 yyyy-MM-dd
	 */
	public String getStartSchool(String year, String term) {
		String sql = "select start_date from t_school_start where school_year='"
				+ year + "' and term_code='" + term + "'";
		List<Map<String, Object>> list = baseDao.getBaseDao().getJdbcTemplate()
				.queryForList(sql);
		String result = "";
		if (list != null && list.size() > 0) {
			result = MapUtils.getString(list.get(0), "START_DATE");
		}
		return result;
	}

	@Override
	public List jrkc(String id) {
		List<Map<String, Object>> list = null;
		try {
			String today =  DateUtils.getNowDate();
			String year_trem[] = EduUtils
					.getSchoolYearTerm(stringToDate(today));
			String start = getStartSchool(year_trem[0], year_trem[1]);
			if (start != null && !"".equals(start)) {
				int zc = DateUtils.getZcByDateFromBeginDate(
						stringToDate(start), stringToDate(today));
				int weak = DateUtils.getWeekNo(today);
				list = getTodayClass(year_trem[0], year_trem[1], id, zc, weak);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Date stringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public int getWeek(String day) {
		String today = day;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(today);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}
	public List<Map<String, Object>> getClassSchedule(String tea_id,String school_year,String term_code, int zc,int jc) {
		String sql = "select tt.* from("
				+ " select distinct cou.name_ course_name,cb.name_ classroom, t.day_of_week, "
				//+ "case when instr(t.period,'-')>0 then cpt.start_time||'-'||cp.end_time end time_,"
				+ "t.period_start  PERIODSTART"
				+ ",t.period_start+1   PERIODEND"
				+ " from t_course_arrangement t"
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join t_course_period_time cpt on cpt.id = t.period_start"
				+ " left join t_course_period_time cp on cp.id = t.period_end"
				+ " left join t_classroom_build cb on cb.code_ = t.classroom_id where t.school_year = '"+school_year+"' and t.term_code = '"+term_code+"' "
				+ " and t.tea_id = '"+tea_id+"' "
						+ "and t.weeks like '%"+zc+"%' "
						//+ "and t.period_start = "+jc+" "
								+ ") tt order by day_of_week ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
	@Override
	public List grkb(String tea_id) {// 需要教师id(t.tea_id)
		
		String date = DateUtils.getNowDate();
		String year_term[]=EduUtils.getSchoolYearTerm(stringToDate(date));
		String start=getStartSchool(year_term[0],year_term[1]);
		int zc = DateUtils.getZcByDateFromBeginDate(
				stringToDate(start), stringToDate(date));
		try {
			int weak = DateUtils.getWeekNo(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(zc == 0){
			if(StringUtils.hasText(start)){
				zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start),stringToDate(date));
			}
		}
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> mylist = getClassSchedule(tea_id, year_term[0], year_term[1], zc,0);
	/*	for(int i=1;i<=7;i=i+2){
			Map<String,Object> map = new HashMap<String,Object>();
			List<Map<String,Object>> mylist = getClassSchedule(tea_id, year_term[0], year_term[1], zc,i);
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
		}*/
		return mylist;
	}

	@Override
	public List skjd(String tea_id) {
		String date = DateUtils.getNowDate();
		List<Map<String,Object>> resultlist = new ArrayList<Map<String,Object>>();
		try {
			String year_trem[]=EduUtils.getSchoolYearTerm(stringToDate(date));
			String start=getStartSchool(year_trem[0],year_trem[1]);
			if(start!=null && !"".equals(start)){
				int zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start),stringToDate(date));
				List<Map<String,Object>> list =getTermClass(year_trem[0], year_trem[1], tea_id);
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

	@Override
	public Page skcj(String id, int currentPage, int numPerPage) {
		//id = "1001000000680505";
/*		String sql = "SELECT DISTINCT f.y cl01,f.z cl02,tct.name_ cl03,f.ftgl cl04,f.fzgf cl05,f.fzdf cl06 from "
				+ " t_course_arrangement t"
				+ " LEFT JOIN t_course tct on tct.code_=t.course_id "// '01133062'
																		// "
				+ // -- 目的是得到课程名 '01133062=t.course_id
				" left join (SELECT c.ccc x,c.ccs y,c.cct z,c.tgl ftgl,d.zgf fzgf,d.zdf fzdf from "
				+ // --- 查询课程号 学年 学期 通过率 最低分 最高分
				" ( SELECT TSS.COURE_CODE ddc ,TSS.SCHOOL_YEAR dds,TSS.TERM_CODE  ddt,max(TSS.CENTESIMAL_SCORE) zgf ,MIN(TSS.CENTESIMAL_SCORE) zdf  FROM T_STU_SCORE TSS group by TSS.COURE_CODE,TSS.SCHOOL_YEAR,TSS.TERM_CODE) d "
				+ " left join (SELECT a.aac ccc,a.aas ccs,a.aat cct,round((a.TSSFZ/b.TSSFM),3)*100||'%' tgl  FROM "
				+ " (SELECT TSS.COURE_CODE aac,TSS.SCHOOL_YEAR aas,TSS.TERM_CODE aat,COUNT(*) AS TSSFZ FROM T_STU_SCORE TSS WHERE TSS.CENTESIMAL_SCORE>=60 GROUP BY TSS.COURE_CODE,TSS.SCHOOL_YEAR,TSS.TERM_CODE) a"
				+ " LEFT JOIN ( SELECT TSS.COURE_CODE bbc,TSS.SCHOOL_YEAR bbs,TSS.TERM_CODE bbt,COUNT(*) AS TSSFM FROM T_STU_SCORE TSS  GROUP BY TSS.COURE_CODE,TSS.SCHOOL_YEAR,TSS.TERM_CODE) b"
				+ " on a.aac=b.bbc and a.aas =b.bbs and a.aat =b.bbt) c"
				+ " on c.ccc=d.ddc and c.ccs=d.dds and c.cct=d.ddt) f"
				+ " on f.x=t.course_id" + // -- t.course_id=f.x and
											// t.school_year=f.y and
											// t.term_code=f.z
				" where  t.tea_id='" + id + "'";// -- 教师id
		*/
		String[] sco=EduUtils.getSchoolYearTerm(new Date());
		String sql="select DISTINCT t.school_year cl01,t.term_code cl02,tcs.name_ cl03, round(sum(case when t.CENTESIMAL_SCORE >=60 then 1 else 0 end)*100/(count(t.CENTESIMAL_SCORE)+1),2)||'%' cl04,max(t.CENTESIMAL_SCORE) cl05,min(t.CENTESIMAL_SCORE) cl06 from t_stu_score t "
				+ " left join t_course tcs on tcs.code_ = t.coure_code "
				+ " left join T_CLASS_TEACHING_STU tcts on tcts.stu_id=t.stu_id  "
				+ " left join t_course_arrangement tca on tcts.teach_class_id=tca.teachingclass_id and tca.school_year=t.school_year and tca.term_code=t.term_code and tca.tea_id='" + id + "' "
				+ " where t.school_year ='"+sco[0]+"' and  t.term_code='"+sco[1]+"' "
				+ " group by t.school_year,t.term_code,tcs.name_ ";
		return new Page(sql.toString(), currentPage, numPerPage, baseDao
				.getBaseDao().getJdbcTemplate(), null);
	}

	@Override
	public List<Map<String, Object>> getTodayClass(String school_year,
			String term_code, String tea_id, int zc, int weak) {

		String sql = "select  t.id,cou.name_ course_name ,concat(t.period,'('||cpt.start_time || '-' || cpt.end_time||')') time_ ,t.classroom_id classroom_name from t_course_arrangement t "
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join t_course_period_time cpt on cpt.id = substr(t.period, 2, instr(t.period, '-') -2 )"
				//+ " left join t_classroom_build cb on cb.code_ = t.classroom_id"
				+ " where t.school_year = '"
				+ school_year
				+ "' and t.term_code = '"
				+ term_code
				+ "' and  t.tea_id = '"
				+ tea_id
				+ "' and t.day_of_week = "
				+ weak
				+ " and t.weeks like '%," + zc + ",%' "
				+ " and t.period_start=cpt.id ";
		List<Map<String, Object>> list = baseDao.getBaseDao().getJdbcTemplate()
				.queryForList(sql);
		for (int i = 0; i < list.size(); i++) {
			String courseArrangementId = list.get(i).get("ID").toString();
			list.get(i).put("list", getTodayCourse(courseArrangementId));
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getTodayCourse(String courseArrangementId) {
		String sql = "select aa.*,st.name_,sc.tel from ("
				+ " select s.course_name ,s.time_,s.classroom,s.class_name,s.school_year,s.term_code,s.no_,count(*) stus from t_stu stu inner join ("
				+ " select cou.name_ course_name,cpt.start_time ||'-'|| cpt.end_time time_ ,cb.name_ classroom ,cl.name_ class_name,"
				+ " cl.no_,t.school_year,t.term_code from t_course_arrangement t"
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join t_course_period_time cpt on cpt.id = substr(t.period, 2, instr(t.period, '-') -2)"
				+ " left join t_classroom_build cb on cb.code_ = t.classroom_id"
				+ " right join t_class_teaching_xzb xzb on t.teachingclass_id = xzb.teach_class_id"
				+ " left join t_classes cl on cl.no_ = xzb.class_id where t.id = '"
				+ courseArrangementId
				+ "') s on stu.class_id = s.no_ "
				+ " group by s.course_name ,s.time_,s.classroom,s.class_name,s.school_year,s.term_code,s.no_ order by class_name) aa"
				+ " left join t_classes_instructor ci on ci.class_id = aa.no_ and aa.school_year = ci.school_year and aa.term_code = ci.term_code"
				+ " left join t_stu st on st.no_ = ci.stu_id"
				+ " left join t_stu_comm sc on sc.stu_id = st.no_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);

	}

	@Override
	public List<Map<String, Object>> getTermClass(String school_year,
			String term_code, String tea_id) {
		String sql = "select s.teachingclass_id,s.course_name,s.course_type,s.weeks,s.day_of_week,wmsys.wm_concat(class_name) class_names from ("
				+ " select t.teachingclass_id, cou.name_ course_name,co.name_ course_type,t.day_of_week,t.weeks ,cl.name_ class_name"
				+ " from t_course_arrangement t "
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join t_class_teaching_xzb xzb on xzb.teach_class_id = t.teachingclass_id"
				+ " left join t_classes cl on cl.no_ = xzb.class_id"
				+ " left join t_code co on co.code_type = 'COURSE_TYPE_CODE' and co.code_ = t.course_type_code"
				+ " where t.school_year = '"+ school_year +"' and t.term_code = '"+ term_code +"' and t.tea_id = '"+ tea_id +"'"
				+ " group by t.teachingclass_id, cou.name_,co.name_,cl.name_,t.weeks,t.day_of_week order by t.teachingclass_id,t.weeks,t.day_of_week) s "
				+ " group by s.teachingclass_id,s.course_name,s.course_type,s.weeks,s.day_of_week order by s.teachingclass_id,s.course_name,s.course_type,s.weeks,s.day_of_week";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
}
