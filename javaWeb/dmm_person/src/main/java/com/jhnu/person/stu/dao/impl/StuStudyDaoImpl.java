package com.jhnu.person.stu.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jhnu.framework.data.base.BaseDao;
import com.jhnu.person.stu.dao.StuStudyDao;
import com.jhnu.syspermiss.util.DateUtils;
import com.jhnu.system.common.page.Page;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.product.EduUtils;

@Repository("stuStudyDao")
public class StuStudyDaoImpl implements StuStudyDao {
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
	public List pushBooks(String id, String startTime, String endTime) {
		String sql = "select t.BOOK_ID,tb.NAME_ ,tb.writer write_,tb.press press,count(t.id) count_ from T_BOOK_BORROW t  "
				+ " left join T_BOOK tb on tb.NO_=t.BOOK_ID "
				+ " where t.BOOK_READER_ID in( "
				+ " select NO_ from t_stu  where MAJOR_ID =(select MAJOR_ID from t_stu  where NO_='"
				+ id
				+ "')) "
				+ " and t.BORROW_TIME between '"
				+ startTime
				+ "' and '"
				+ endTime
				+ "' and rownum<6"
				+ " group by t.BOOK_ID ,tb.NAME_,tb.writer,tb.press order by count_ desc ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List jyfl(String id, String startTime, String endTime) {
		String sql = "select tc.name_ field,count(book.no_) value,'借阅分类' name "// --book.name_
				+ " from t_book_borrow t  "
				+ "  left join t_book_reader br on br.no_ = t.book_reader_id  "
				+ "  left join t_book book on book.no_ = t.book_id "
				+ "  left join t_code tc on book.store_code=tc.code_ and code_type like '%STORE_CODE%' "
				+ "  where br.people_id = '"
				+ id
				+ "' and t.borrow_time between'" + startTime
				+ "' and '"
				+ endTime + "'  group by tc.name_ ";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}

	@Override
	public List jysl(String id, String startTime, String endTime) {
		String sql=" select max(count(*)) max_ ,min(count(*)) min_ from T_BOOK_BORROW t "
				+ " where t.borrow_time between '" + startTime + "' and '"+ endTime + "'"
				+ " group by t.book_reader_id";//最大 最小
		List<Map<String, Object>> list= baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		
		 sql = " select count(t.id) counts,t.borrow_time time "
				+ " from t_book_borrow t  "
				+ "left join t_book_reader br on br.no_ = t.book_reader_id "
				+ "where br.people_id =  '" + id
				+ "' and t.borrow_time between'" + startTime + "' and '"
				+ endTime + "' group by t.borrow_time order by counts ";
		sql = " select sum(t.counts) sum_,min(t.counts) min_,sum(t.counts) avg_ from ("

				+ sql + ") t";
		List<Map<String, Object>> list1= baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
		list.get(0).put("AVG_", list1.get(0).get("AVG_"));
		list.get(0).put("SUM_", list1.get(0).get("SUM_"));
		return list;
	}

	@Override
	public Page jymx(String id, String startTime, String endTime,
			int currentPage, int numPerPage) {
		// id,名称,借书时间,应还时间,归还时间,借书状态
		String sql = "select book.name_ cl02, "
				+ " t.borrow_time cl03,t.SHOULD_RETURN_TIME cl04,t.return_time cl05, "

				+ "  case "
				+ "   when t.return_time is null and t.should_return_time < t.return_time then "
				+ "    '超期' "
				+ "    when t.return_time is null and t.should_return_time > t.return_time then "
				+ "     '在阅' " + "    else " + "      '已还' "
				+ "     end as cl06 " + "  from t_book_borrow t "
				+ "  left join t_book_reader br on br.no_ = t.book_reader_id "
				+ "  left join t_book book on book.no_ = t.book_id "
				+ "  where br.people_id =  '" + id
				+ "' and t.borrow_time between'" + startTime + "' and '"
				+ endTime + "'   order by t.borrow_time desc ";
		return new Page(sql.toString(), currentPage, numPerPage, baseDao
				.getBaseDao().getJdbcTemplate(), null);
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

	@Override
	public List grkb(String stu_id) {
		String date = DateUtils.getNowDate();
		List<Map<String, Object>> resultlist = new ArrayList<Map<String, Object>>();
		String year_trem[] = EduUtils.getSchoolYearTerm(stringToDate(date));
		String start = getStartSchool(year_trem[0], year_trem[1]);
		if (start != null && !"".equals(start)) {
			int zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start),
					stringToDate(date));
			List<Map<String, Object>> list = getTermClass(year_trem[0],
					year_trem[1], stu_id, zc);
			return list;
		}
		return null;
	}

	@Override
	public Page skcj(String stu_id, int currentPage, int numPerPage) {
		String sql = "select t.SCHOOL_YEAR num1, "
				+ " case "
				+ "   when t.TERM_CODE = '01' then "
				+ "    '第一学期' "
				+ "   else "
				+ "     '第二学期' "
				+ "  end num2, "
				+ "  cou.name_ num3, "
				//+ "  case "
				//+ "    when t.hierarchical_score_code is not null then "
				//+ "     co.name_ "
				//+ "    else "
				//+ "     to_char(t.centesimal_score) "
				//+ "   end num4, "
				//+ "   cou.theory_credit num5  "
				+ "  t.centesimal_score num4, "
				+ "  t.hierarchical_score_code num5 "
				+ "   from t_stu_score t "
				+ "  left join t_code co on co.code_type = 'HIERARCHICAL_SCORE_CODE' "
				+ "              and co.code_ = t.hierarchical_score_code "
				+ "  left join t_course cou on cou.code_ = t.coure_code "
				+ " where t.stu_id = '" + stu_id + "' "
						+ " order by t.school_year desc,t.term_code desc";
		// + "--and t.school_year = 'school_year' and t.term_code = 'term_code';

		return new Page(sql.toString(), currentPage, numPerPage, baseDao
				.getBaseDao().getJdbcTemplate(), null);
	}

	/**
	 * 个人课表
	 */
	@Override
	public List<Map<String, Object>> getTermClass(String school_year,
			String term_code, String stu_id, int zc) {
		String sql = "select tt.course_name,tt.classroom,tt.day_of_week,"
				// + ",case when tt.time_ is null then tt.period "
				// + " else tt.time_ end time_ ,tt.period_start "
				+ " tt.period_start  PERIODSTART"
				+ ",tt.period_start+1   PERIODEND"
				+ " from ("
				+ " select t.id,cou.name_ course_name,t.classroom_id classroom ,t.day_of_week,t.period_start,"
				+ " t.period,case when instr(t.period,'-')>0 then cpt.start_time||'-'||cp.end_time end time_ from t_course_arrangement t "
				+ " left join t_class_teaching_xzb xzb on xzb.teach_class_id = t.teachingclass_id"
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join t_classroom_build cb on cb.code_ = t.classroom_id"
				+ " left join t_course_period_time cpt on cpt.id = t.period_start"
				+ " left join t_course_period_time cp on cp.id = t.period_end"
				+ " left join t_stu stu on stu.class_id = xzb.class_id where stu.no_ = '"
				+ stu_id + "' and t.school_year = '" + school_year
				+ "' and t.term_code = '" + term_code + "'"
				+ " and t.weeks like '%," + zc + ",%' ) tt "
				// + "where tt.period_start = "+jc+" "
				+ " order by tt.day_of_week,tt.time_";

		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);

	}

	@Override
	public List<Map<String, Object>> getTodayCourse(String stu_id) {
		String date = DateUtils.getNowDate();
		String year_trem[] = EduUtils.getSchoolYearTerm(stringToDate(date));
		String start = getStartSchool(year_trem[0], year_trem[1]);
		int zc = DateUtils.getZcByDateFromBeginDate(stringToDate(start),
				stringToDate(date));
		int week = DateUtils.getWeekNo();
		String sql = "select tt.course_name,tt.classroom,tt.course_type,tt.tea_name,case when tt.time_ is null then tt.period else tt.time_ end time_ from ("
				+ " select t.id,cou.name_ course_name,t.classroom_id classroom,co.name_ course_type,tea.name_ tea_name,"
				+ " t.period,case when instr(t.period,'-')>0 then cpt.start_time||'-'||cp.end_time end time_ from t_course_arrangement t "
				+ " left join t_class_teaching_xzb xzb on xzb.teach_class_id = t.teachingclass_id"
				+ " left join t_course cou on cou.code_ = t.course_id"
				+ " left join t_tea tea on tea.tea_no = t.tea_id"
				+ " left join t_classroom_build cb on cb.code_ = t.classroom_id"
				+ " left join t_code co on co.code_type = 'COURSE_TYPE_CODE' and co.code_ = t.course_type_code"
				+ " left join t_course_period_time cpt on cpt.id = t.period_start"
				+ " left join t_course_period_time cp on cp.id = t.period_end"
				+ " left join t_stu stu on stu.class_id = xzb.class_id where stu.no_ = '"
				+ stu_id
				+ "' and t.school_year = '"
				+ year_trem[0]
				+ "' and t.term_code = '"
				+ year_trem[1]
				+ "'"
				+ " and t.weeks like '%,"
				+ zc
				+ ",%' and t.day_of_week = "
				+ week + ") tt order by time_";
		return baseDao.getBaseDao().getJdbcTemplate().queryForList(sql);
	}
}
