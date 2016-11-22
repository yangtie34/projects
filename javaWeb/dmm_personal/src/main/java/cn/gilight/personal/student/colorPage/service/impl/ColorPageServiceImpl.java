package cn.gilight.personal.student.colorPage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.personal.student.colorPage.service.ColorPageService;
import cn.gilight.personal.student.major.dao.MyMajorDao;
import cn.gilight.personal.student.school.dao.MySchoolDao;
import cn.gilight.personal.student.score.dao.StuScoreDao;

@Service("colorPageService")
public class ColorPageServiceImpl implements ColorPageService{

	@Autowired
	private BaseDao baseDao;
	
	@Autowired
	private MySchoolDao mySchoolDao;
	
	@Autowired
	private StuScoreDao stuScoreDao;
	
	@Autowired
	private MyMajorDao myMajorDao;
	
	@Override
	public Map<String, Object> getSchool() {
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> rest = mySchoolDao.getRestaurantCounts();
		result.put("restaurant", MapUtils.getString(rest, "restaurant"));
		
		String sql = "select count(*) win_nums from t_card_dept t where t.dept_type = 'RES_WIN' or t.dept_type = 'MUSLIM_RES_WIN'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			result.put("restaurantWin", MapUtils.getString(list.get(0), "win_nums"));
		}
		
		String sql2 = "select count(*) room from t_classroom_build t where length(t.code_)>4 or t.level_type = 'FJ'";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		if(list2 != null && list2.size()>0){
			result.put("room", MapUtils.getString(list2.get(0), "room"));
		}
		
		result.put("teachingBuilding", MapUtils.getString(mySchoolDao.getTeachingBuildingCounts(), "teachingbuilding"));
		result.put("books", MapUtils.getString(mySchoolDao.getBookCounts(), "book"));
		result.put("shops", MapUtils.getString(mySchoolDao.getShopCounts(), "shop"));
		return result;
	}
	
	@Override
	public Map<String, Object> getFdy(String stu_id) {
		//String[] terms = EduUtils.getSchoolYearTerm(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		String sql = "select tea.name_,tc.tel from t_classes_instructor t left join t_stu stu on stu.class_id = t.class_id "
				+ " left join t_tea tea on tea.tea_no = t.tea_id "
				+ " left join t_tea_comm tc on tc.tea_id = tea.tea_no "
				+ " where stu.no_ = '"+stu_id+"' order by school_year desc ,term_code desc";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

	@Override
	public Map<String, Object> QueryRestaurantWinTop() {
		Map<String,Object> result = new HashMap<String,Object>();
		String sql = "select t.eat_time,t.dept_name from t_card_dept_month t where t.rn = 1";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			for(Map<String,Object> map : list){
				if("早".equals(MapUtils.getString(map, "eat_time"))){
					result.put("zao", MapUtils.getString(map , "dept_name"));
				}
				if("午".equals(MapUtils.getString(map, "eat_time"))){
					result.put("wu", MapUtils.getString(map , "dept_name"));
				}
				if("晚".equals(MapUtils.getString(map, "eat_time"))){
					result.put("wan", MapUtils.getString(map , "dept_name"));
				}
			}
		}
		String now = DateUtils.getLastMonth();
		now = now.substring(0, 7);
		String sql2 = "select ts.name_ from ( select tt.name_,tt.sums,rownum rn from ("
				+ " select cd.code_,cd.name_,sum(t.pay_money) sums from t_card_pay t left join t_card_port cp on cp.no_ = t.card_port_id"
				+ " left join t_card_dept cd on cd.code_ = cp.card_dept_id "
				+ " where cd.dept_type = 'MUSLIM_RES_WIN' and t.time_ > '"+now+"' group by cd.code_ ,cd.name_ order by sums desc) tt) ts where ts.rn = 1";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		if(list2 != null && list2.size()>0){
			result.put("qz", MapUtils.getString(list2.get(0), "name_"));
		}
		return result;
	}

	@Override
	public Map<String, Object> QueryMajorSex(String stu_id) {
		Map<String,Object> result = new HashMap<String,Object>();
		String sql1 = "select co.name_,count(*) nums from t_stu t "
				+ " left join t_code co on co.code_ = t.sex_code and co.code_type = 'SEX_CODE'"
				+ " left join t_stu stu on t.major_id = stu.major_id and t.enroll_grade = stu.enroll_grade"
				+ " where stu.no_ = '"+stu_id+"' group by co.name_";
		List<Map<String,Object>> list1 = baseDao.queryListInLowerKey(sql1);
		if(list1 != null && list1.size()>0){
			Map<String,Object> myMajor = new HashMap<String,Object>();
			for(Map<String,Object> map : list1){
				if("男".equals(MapUtils.getString(map, "name_"))){
					myMajor.put("boy",  MapUtils.getString(map , "nums"));
				}
				if("女".equals(MapUtils.getString(map, "name_"))){
					myMajor.put("gril",  MapUtils.getString(map , "nums"));
				}
			}
			result.put("myMajor", myMajor);
		}
		
		String year = DateUtils.getNowYear();
		String month = DateUtils.getNowMonth();
		if(Integer.parseInt(month)<7){
			year = String.valueOf(Integer.parseInt(year) -1);
		}
		String mysql = "select  t.major_id,cdt.name_ major_name,co.name_ sex,count(*) nums from t_stu t "
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code"
				+ " left join t_code_dept_teach cdt on cdt.id = t.major_id "
				+ " where t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"' group by t.major_id,cdt.name_,co.name_ order by nums desc";
		String sql2 = "select cc.* from ("
				+ " select aa.*,rownum rn from ("
				+ mysql +") aa where aa.sex = '女') bb"
				+ " left join ("
				+ mysql
				+ " ) cc on cc.major_id = bb.major_id where bb.rn = 1";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		if(list2 != null && list2.size()>0){
			Map<String,Object> grilMajor = new HashMap<String,Object>();
			grilMajor.put("major", MapUtils.getString(list2.get(0), "major_name"));
			for(Map<String,Object> map : list2){
				if("男".equals(MapUtils.getString(map, "sex"))){
					grilMajor.put("boy",  MapUtils.getString(map , "nums"));
				}
				if("女".equals(MapUtils.getString(map, "sex"))){
					grilMajor.put("gril",  MapUtils.getString(map , "nums"));
				}
			}
			result.put("grilMajor", grilMajor);
		}
		String sql3 = "select cc.* from ("
				+ " select aa.*,rownum rn from ("
				+ mysql +") aa where aa.sex = '男') bb"
				+ " left join ("
				+ mysql
				+ " ) cc on cc.major_id = bb.major_id where bb.rn = 1";
		List<Map<String,Object>> list3 = baseDao.queryListInLowerKey(sql3);
		if(list3 != null && list3.size()>0){
			Map<String,Object> BoyMajor = new HashMap<String,Object>();
			BoyMajor.put("major", MapUtils.getString(list3.get(0), "major_name"));
			for(Map<String,Object> map : list3){
				if("男".equals(MapUtils.getString(map, "sex"))){
					BoyMajor.put("boy",  MapUtils.getString(map , "nums"));
				}
				if("女".equals(MapUtils.getString(map, "sex"))){
					BoyMajor.put("gril",  MapUtils.getString(map , "nums"));
				}
			}
			result.put("boyMajor", BoyMajor);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryStuSex() {
		Map<String,Object> result = new HashMap<String,Object>();
		String year = DateUtils.getNowYear();
		String month = DateUtils.getNowMonth();
		if(Integer.parseInt(month)<7){
			year = String.valueOf(Integer.parseInt(year) -1);
		}
		String sql = "select co.name_,count(*) nums from t_stu t"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code where t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"'"
				+ " group by co.name_";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size() >0){
			for(Map<String,Object> map : list){
				if("男".equals(MapUtils.getString(map, "name_"))){
					result.put("boy",  MapUtils.getString(map , "nums"));
				}
				if("女".equals(MapUtils.getString(map, "name_"))){
					result.put("gril",  MapUtils.getString(map , "nums"));
				}
			}
		}
		result.put("stus", MapUtils.getString(mySchoolDao.getStudntCounts(), "student"));
		return result;
	}

	@Override
	public Map<String, Object> queryTmx(String stu_id) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		String year = DateUtils.getNowYear();
		String month = DateUtils.getNowMonth();
		if(Integer.parseInt(month)<7){
			year = String.valueOf(Integer.parseInt(year) -1);
		}
		String sql = "select count(*) tmx from ("
				+ " select t.no_ stu_id,t.name_,co.name_ from t_stu t left join  t_stu stu on stu.schooltag = t.schooltag"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code where stu.no_ = '"+stu_id+"' "
						+ " and t.no_ != '"+stu_id+"' and t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"') tt";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			result.put("tmx", MapUtils.getString(list.get(0), "tmx"));
		}else{
			result.put("tmx", 0);
		}
		
		String sql2 = "select count(*) tzy from (select t.no_ stu_id,t.name_,co.name_ sex from t_stu t "
				+ " left join  t_stu stu on stu.schooltag = t.schooltag and t.major_id = stu.major_id"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code where stu.no_ = '"+stu_id+"'"
				+ "  and t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"' and t.no_ != '"+stu_id+"') tt" ;
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		if(list2 != null && list2.size()>0){
			result.put("tzy", MapUtils.getString(list2.get(0), "tzy"));
		}else{
			result.put("tzy", 0);
		}
		
		String sql3 = "select co.name_ ,count(*) nums from t_stu t left join  t_stu stu on stu.schooltag = t.schooltag and t.major_id = stu.major_id"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code where stu.no_ = '"+stu_id+"'  "
				+ " and t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"' and t.no_ != '"+stu_id+"'group by co.name_";
		List<Map<String,Object>> list3 = baseDao.queryListInLowerKey(sql3);
		if(list3 != null && list3.size() >0){
			for(Map<String,Object> map : list3){
				if("男".equals(MapUtils.getString(map, "name_"))){
					result.put("boy",  MapUtils.getString(map , "nums"));
				}
				if("女".equals(MapUtils.getString(map, "name_"))){
					result.put("gril",  MapUtils.getString(map , "nums"));
				}
			}
		}
		if(!StringUtils.hasText(MapUtils.getString(result, "boy"))){
			result.put("boy", 0);
		}
		if(!StringUtils.hasText(MapUtils.getString(result, "gril"))){
			result.put("gril", 0);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryTx(String stu_id) {
		Map<String,Object> result = new HashMap<String,Object>();
		
		String year = DateUtils.getNowYear();
		String month = DateUtils.getNowMonth();
		if(Integer.parseInt(month)<7){
			year = String.valueOf(Integer.parseInt(year) -1);
		}
		String sql1 = "select count(*) tx_ from("
				+ " select t.no_ stu_id,t.name_ stu_name,co.name_ sex,t.enroll_grade from t_stu t "
				+ " left join t_stu stu on stu.stu_origin_id = t.stu_origin_id"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code"
				+ " where t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"' and stu.no_ = '"+stu_id+"' and t.no_ != '"+stu_id+"') ";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql1);
		if(list != null && list.size()>0){
			result.put("tx", MapUtils.getString(list.get(0), "tx_"));
		}else{
			result.put("tx", 0);
		}
		
		String sql2 = "select count(*) tzy from ("
				+ " select t.no_ stu_id,t.name_ stu_name,co.name_ sex,t.enroll_grade from t_stu t "
				+ " left join t_stu stu on stu.stu_origin_id = t.stu_origin_id and stu.major_id = t.major_id"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code"
				+ " where t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"' and stu.no_ = '"+stu_id+"' and t.no_ != '"+stu_id+"')";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		if(list2 != null && list2.size()>0){
			result.put("tzy", MapUtils.getString(list2.get(0), "tzy"));
		}else{
			result.put("tzy", 0);
		}
		
		String sql3 = "select co.name_ ,count(*) nums from t_stu t "
				+ " left join t_stu stu on stu.stu_origin_id = t.stu_origin_id and stu.major_id = t.major_id"
				+ " left join t_code co on co.code_type = 'SEX_CODE' and co.code_ = t.sex_code"
				+ " where t.stu_state_code = 1 and to_number(t.enroll_grade)+t.length_schooling > '"+year+"' and stu.no_ = '"+stu_id+"' and t.no_ != '"+stu_id+"'"
				+ " group by co.name_";
		List<Map<String,Object>> list3 = baseDao.queryListInLowerKey(sql3);
		if(list3 != null && list3.size() >0){
			for(Map<String,Object> map : list3){
				if("男".equals(MapUtils.getString(map, "name_"))){
					result.put("boy",  MapUtils.getString(map , "nums"));
				}
				if("女".equals(MapUtils.getString(map, "name_"))){
					result.put("gril",  MapUtils.getString(map , "nums"));
				}
			}
		}
		if(!StringUtils.hasText(MapUtils.getString(result, "boy"))){
			result.put("boy", 0);
		}
		if(!StringUtils.hasText(MapUtils.getString(result, "gril"))){
			result.put("gril", 0);
		}
		return result;
	}

	@Override
	public Map<String, Object> queryCourse(String stu_id) {
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("major", MapUtils.getString(myMajorDao.getMajor(stu_id), "major"));
		
		String sql = "select count(*) course_nums,nvl(sum(cou.theory_period),0) period_total from t_course_arrangement_plan t "
				+ " left join t_stu stu on stu.class_id = t.class_id"
				+ " left join t_course cou on cou.code_ = t.course_code where stu.no_ = '"+stu_id+"'";
		List<Map<String,Object>> list = baseDao.queryListInLowerKey(sql);
		if(list != null && list.size()>0){
			result.put("course_nums", MapUtils.getIntValue(list.get(0), "course_nums"));
			result.put("period_total", MapUtils.getIntValue(list.get(0), "period_total"));
		}else{
			result.put("course_nums", 0);
			result.put("period_total", 0);
		}
		
		String sql2 = "select co.name_ , sum(cou.theory_period) period from t_course_arrangement_plan t "
				+ " left join t_stu stu on stu.class_id = t.class_id"
				+ " left join t_code co on co.code_type = 'COURSE_CATEGORY_CODE' and co.code_ = t.course_category_code"
				+ " left join t_course cou on cou.code_ = t.course_code where stu.no_ = '"+stu_id+"' group by co.name_";
		List<Map<String,Object>> list2 = baseDao.queryListInLowerKey(sql2);
		double ll = 0d;
		double sy = 0d;
		if(list2 != null && list2.size()>0){
			for(Map<String,Object> map : list2){
				if(MapUtils.getString(map, "name_").contains("理论")){
					ll = ll + MapUtils.getDoubleValue(map, "period");
				}else{
					sy = sy + MapUtils.getDoubleValue(map, "period");
				}
			}
		}
		result.put("ll", ll);
		result.put("sy", sy);
		result.put("total_credit",MapUtils.getString(stuScoreDao.getTotalCredit(stu_id), "total_credit"));
		result.put("bx_credit",MapUtils.getString(stuScoreDao.getTotalCreditCourseAttr(stu_id), "bx"));
		result.put("xx_credit",MapUtils.getString(stuScoreDao.getTotalCreditCourseAttr(stu_id), "xx"));
		return result;
	}
	
}
