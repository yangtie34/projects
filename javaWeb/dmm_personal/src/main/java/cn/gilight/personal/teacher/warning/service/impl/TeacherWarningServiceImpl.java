package cn.gilight.personal.teacher.warning.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.teacher.warning.service.TeacherWarningService;

/**   
* @Description: TODO 学生预警处理service
* @author Sunwg  
* @date 2016年3月31日 下午5:20:32   
*/
@Service("teacherWarningService")
public class TeacherWarningServiceImpl implements TeacherWarningService {
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	@Override
	public Map<String, Object> queryConsumeNums(String username,int months) {
		String[] xnxq = EduUtils.getSchoolYearTerm(new Date());
		String currentXn =xnxq[0];
		String currentXq =xnxq[1];
		String startDate = this.getDateByMonthSet(-months);
		String sql = "SELECT COUNT(T.ID) NUMS,T.ISHIGH ISHIGH, T.SNO"
				+ "  FROM T_CARD_PAY_ABNORMAL T"
				+ " INNER JOIN T_STU S ON T.SNO = S.NO_"
				+ " INNER JOIN T_CLASSES_INSTRUCTOR R ON R.CLASS_ID = S.CLASS_ID  AND R.SCHOOL_YEAR = '"+currentXn+"' AND R.TERM_CODE = '"+currentXq+"'"
				+ " WHERE T.DATETIME > '"+startDate+"'"
				  + " AND R.TEA_ID = '"+username+"'"
				+ " GROUP BY T.SNO,T.ISHIGH";
		List<Map<String, Object>> xslist = baseDao.queryForList(sql);
		int high=0,low = 0;
		for (int i = 0; i < xslist.size(); i++) {
			Map<String, Object> xs = xslist.get(i);
			int isHigh = MapUtils.getIntValue(xs, "ISHIGH");
			switch (isHigh) {
				case 0:
					low++;break;
				case 1:
					high++;	break;
				default:
					break;
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("low", low);
		result.put("high", high);
		return result;
	}
	@Override
	public Map<String, Object> queryAvgDayConsume() {
		String avgsql = "SELECT  ROUND(AVG(TT.VAL)) VAL,TT.GENDER "
				+ " FROM(SELECT SUM(T.SUM_VAL)/SUM(T.TOTAL) VAL,T.GENDER "
				+ " FROM T_CARD_PAY_DAY T GROUP BY T.DATETIME,T.GENDER)TT "
				+ " GROUP BY TT.GENDER";
		List<Map<String, Object>> ls = baseDao.queryForList(avgsql);
		Map<String, Object> result = new HashMap<String, Object>();
		for (int i = 0; i < ls.size(); i++) {
			Map<String, Object> it = ls.get(i);
			String gender = MapUtils.getString(it, "GENDER");
			if (gender.equals("男")) {
				result.put("male", MapUtils.getFloatValue(it, "VAL"));
			}else if(gender.equals("女")){
				result.put("female", MapUtils.getFloatValue(it, "VAL"));
			}
		}
		return result;
	}
	@Override
	public List<Map<String, Object>> queryXfycStudentsList(String username,
			int months,int ishigh) {
		String[] xnxq = EduUtils.getSchoolYearTerm(new Date());
		String currentXn =xnxq[0];
		String currentXq =xnxq[1];
		String startDate = this.getDateByMonthSet(-months);
		String sql = "SELECT COUNT(T.ID) NUMS, T.SNO,S.NAME_ XM,GD.NAME_ GENDER,CL.NAME_ BJ,B.TEL "
				+ "FROM T_CARD_PAY_ABNORMAL T"
				+ " INNER JOIN T_STU S ON T.SNO = S.NO_"
				+ " INNER JOIN T_CLASSES_INSTRUCTOR R  ON R.CLASS_ID = S.CLASS_ID  AND R.SCHOOL_YEAR = '"+currentXn+"' AND R.TERM_CODE = '"+currentXq+"'"
				+ " INNER JOIN T_CODE GD ON GD.CODE_ = S.SEX_CODE AND GD.CODE_TYPE = 'SEX_CODE'"
				+ " INNER JOIN T_CLASSES CL ON CL.NO_ = S.CLASS_ID"
				+ " LEFT JOIN T_STU_COMM B ON B.STU_ID = T.SNO"
				+ " WHERE T.DATETIME > '"+startDate+"'"
				+ " AND R.TEA_ID = '"+username+"' AND T.ISHIGH="+ishigh
				+ " GROUP BY T.SNO,S.NAME_,GD.NAME_,CL.NAME_,B.TEL"
				+ " ORDER BY COUNT(T.ID) DESC ";
		List<Map<String, Object>> xslist = baseDao.queryListInLowerKey(sql);
		return xslist;
	}
	@Override
	public Map<String, Object> queryStayNums(String username,String date) {
		String[] xnxq = EduUtils.getSchoolYearTerm(new Date());
		String currentXn =xnxq[0];
		String currentXq =xnxq[1];
		//晚归
		String sql1 = "SELECT COUNT(1) NUMS FROM T_CARD_STAY_LATE T"
				+ " INNER JOIN T_CLASSES_INSTRUCTOR R ON T.BJID = R.CLASS_ID AND R.SCHOOL_YEAR = '"+currentXn+"' AND R.TERM_CODE = '"+currentXq+"'"
				+ " WHERE R.TEA_ID = '"+username+"' AND T.DATETIME = '"+date+"'";
		Map<String, Object> result1 = baseDao.queryListInLowerKey(sql1).get(0);
		//不在校
		String sql2 = "SELECT COUNT(1) NUMS FROM T_CARD_STAY_NOTIN T"
				+ " INNER JOIN T_CLASSES_INSTRUCTOR R ON T.BJID = R.CLASS_ID AND R.SCHOOL_YEAR = '"+currentXn+"' AND R.TERM_CODE = '"+currentXq+"'"
				+ " WHERE R.TEA_ID = '"+username+"' AND T.DATETIME = '"+date+"'";
		Map<String, Object> result2 = baseDao.queryListInLowerKey(sql2).get(0);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("late", MapUtils.getIntValue(result1, "nums"));
		result.put("out", MapUtils.getIntValue(result2, "nums"));
		return result;
	}
	@Override
	public List<Map<String, Object>> queryLateStudentsList(String username,String date) {
		String[] xnxq = EduUtils.getSchoolYearTerm(new Date());
		String currentXn =xnxq[0];
		String currentXq =xnxq[1];
		String sql = "SELECT T.NAME NAME,T.SNO SNO,T.BJMC BJ,GENDER.NAME_ GENDER,TEL.TEL TEL,T.BACKTIME BACKTIME"
				+ " FROM T_CARD_STAY_LATE T"
				+ " INNER JOIN T_CLASSES_INSTRUCTOR R ON T.BJID = R.CLASS_ID AND R.SCHOOL_YEAR = '"+currentXn+"' AND R.TERM_CODE = '"+currentXq+"'"
				+ " INNER JOIN T_STU S ON T.SNO = S.NO_"
				+ " INNER JOIN T_CODE GENDER ON GENDER.CODE_=S.SEX_CODE AND GENDER.CODE_TYPE = 'SEX_CODE'"
				+ " LEFT JOIN T_STU_COMM TEL ON TEL.STU_ID = T.SNO"
				+ " WHERE R.TEA_ID = '"+username+"' AND T.DATETIME = '"+date+"' ORDER BY BACKTIME DESC";
		List<Map<String, Object>> result = baseDao.queryListInLowerKey(sql);
		return result;
	}
	@Override
	public List<Map<String, Object>> queryOutStudentList(String username,String date) {
		String[] xnxq = EduUtils.getSchoolYearTerm(new Date());
		String currentXn =xnxq[0];
		String currentXq =xnxq[1];
		String sql = "SELECT T.NAME NAME,T.SNO SNO,T.BJMC BJ,GENDER.NAME_ GENDER,TEL.TEL TEL,T.LASTTIME LASTTIME,"
				+ " TRUNC(SYSDATE - TO_DATE(T.LASTTIME,'YYYY-MM-DD HH24:MI:SS')) OUTDAYS"
				+ " FROM T_CARD_STAY_NOTIN T"
				+ " INNER JOIN T_CLASSES_INSTRUCTOR R ON T.BJID = R.CLASS_ID AND R.SCHOOL_YEAR = '"+currentXn+"' AND R.TERM_CODE = '"+currentXq+"'"
				+ " INNER JOIN T_STU S ON T.SNO = S.NO_"
				+ " INNER JOIN T_CODE GENDER ON GENDER.CODE_=S.SEX_CODE AND GENDER.CODE_TYPE = 'SEX_CODE'"
				+ " LEFT JOIN T_STU_COMM TEL ON TEL.STU_ID = T.SNO"
				+ " WHERE R.TEA_ID = '"+username+"' AND T.DATETIME = '"+date+"' ORDER BY OUTDAYS DESC";
		List<Map<String, Object>> result = baseDao.queryListInLowerKey(sql);
		return result;
	}
	@Override
	public List<Map<String, Object>> queryStudyNums(String username,String xn,String xq) {
		String sql = "SELECT BJ.NAME_ BJ,BJ.NO_ BJID, COUNT(1) GKRS"
				+ " FROM T_CLASSES_INSTRUCTOR T"
				+ " INNER JOIN T_STU S ON T.CLASS_ID = S.CLASS_ID"
				+ " INNER JOIN T_STU_SCORE SC ON SC.STU_ID = S.NO_  AND T.SCHOOL_YEAR = SC.SCHOOL_YEAR AND T.TERM_CODE = SC.TERM_CODE "
				+ " INNER JOIN T_CLASSES BJ ON BJ.NO_ = T.CLASS_ID "
				+ " WHERE T.TEA_ID = '"+username+"' AND SC.CENTESIMAL_SCORE < 60"
				+ " AND SC.SCHOOL_YEAR = '"+xn+"' AND SC.TERM_CODE = '"+xq+"' "
				+ " AND SC.HIERARCHICAL_SCORE_CODE IS NULL"
				+ " GROUP BY BJ.NAME_,BJ.NO_";
		List<Map<String, Object>> result = baseDao.queryListInLowerKey(sql);
		return result;
	}
	@Override
	public List<Map<String, Object>> queryCourseFailStudents(String xn,String xq,String bjid) {
		String sql = "SELECT BJ.NAME_ BJ,S.NAME_ XM,G.NAME_ GENDER, KC.NAME_ COURSE,SC.CENTESIMAL_SCORE SCORE,TEL.TEL"
				+ " FROM  T_STU S "
				+ " INNER JOIN T_STU_SCORE SC ON SC.STU_ID = S.NO_"
				+ " INNER JOIN T_CLASSES BJ ON BJ.NO_ = S.CLASS_ID"
				+ " INNER JOIN T_COURSE KC ON KC.CODE_ = SC.COURE_CODE"
				+ " INNER JOIN T_CODE G ON G.CODE_ =S.SEX_CODE AND G.CODE_TYPE = 'SEX_CODE'"
				+ "  LEFT JOIN T_STU_COMM TEL ON TEL.STU_ID = S.NO_"
				+ " WHERE SC.CENTESIMAL_SCORE < 60 AND S.CLASS_ID = '"+bjid+"'"
				+ " AND SC.SCHOOL_YEAR = '"+xn+"' AND SC.TERM_CODE = '"+xq+"'"
				+ " AND SC.HIERARCHICAL_SCORE_CODE IS NULL ORDER BY S.NO_,KC.CODE_";
		List<Map<String, Object>> result = baseDao.queryListInLowerKey(sql);
		return result;
	}
	public String getDateByMonthSet(int monthNums){
		Date dNow = new Date(); //当前时间
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.MONTH, monthNums); //设置为指定月数后的时间
		Date dBefore = calendar.getTime(); //得到指定月数的时间
		String result = DateUtils.date2String(dBefore);
		return result;
	}
}