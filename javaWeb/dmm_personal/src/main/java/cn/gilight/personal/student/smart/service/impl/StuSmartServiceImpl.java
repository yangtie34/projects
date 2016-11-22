package cn.gilight.personal.student.smart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.SysConfig;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.student.smart.service.StuSmartService;

@Service("stuSmartService")
public class StuSmartServiceImpl implements StuSmartService {

	@Resource
	private BaseDao baseDao;
	
	@Resource
	private HibernateDao hibernate;

	/**
	 * @Description: 根据学号查询成绩信息
	 * @Return: Map<String,Object>
	 * @param sno 学号
	 */
	public List<Map<String, Object>> queryScoreOfStudent(String sno) {
		String sql  = "SELECT C.CODE_ CNO,C.NAME_ CNAME,T.SCHOOL_YEAR XN, T.TERM_CODE XQ,"
				+ " CASE WHEN T.HIERARCHICAL_SCORE_CODE IS NOT NULL THEN H.CENTESIMAL_SCORE ELSE T.CENTESIMAL_SCORE END SCORE,"
				+ "  H.NAME_ DJ  FROM T_STU_SCORE T"
				+ " INNER JOIN T_COURSE C ON T.COURE_CODE = C.CODE_"
				+ " LEFT JOIN T_CODE_SCORE_HIERARCHY H ON T.HIERARCHICAL_SCORE_CODE = H.CODE_"
				+ " WHERE T.STU_ID = '"+sno+"'"
				+ " ORDER BY T.SCHOOL_YEAR,T.TERM_CODE,T.COURE_CODE";
		return baseDao.queryListInLowerKey(sql);
	}

	/**
	 * @Description: 查询指定年级指定专业下的学霸的成绩信息
	 * @Return: Map<String,Object>
	 * @param zyid 专业ID
	 * @param rxnj 入学年级
	 */
	public List<Map<String, Object>> queryScoreOfSmartStudent(String zyid, String rxnj) {
		String sql = "SELECT C.CODE_ CNO,C.NAME_ CNAME,"
				+ "ROUND(AVG( CASE WHEN S.HIERARCHICAL_SCORE_CODE IS NOT NULL THEN H.CENTESIMAL_SCORE ELSE S.CENTESIMAL_SCORE END)) AVG_SCORE,"
				+ "MAX( CASE WHEN S.HIERARCHICAL_SCORE_CODE IS NOT NULL THEN H.CENTESIMAL_SCORE ELSE S.CENTESIMAL_SCORE END) MAX_SCORE,"
				+ "MIN( CASE WHEN S.HIERARCHICAL_SCORE_CODE IS NOT NULL THEN H.CENTESIMAL_SCORE ELSE S.CENTESIMAL_SCORE END) MIN_SCORE "
				+ " FROM T_STU_SMART T"
				+ " LEFT JOIN T_STU_SCORE S ON T.SNO = S.STU_ID "
				+ " LEFT JOIN T_COURSE C ON S.COURE_CODE = C.CODE_"
				+ " LEFT JOIN T_CODE_SCORE_HIERARCHY H ON H.CODE_ = S.HIERARCHICAL_SCORE_CODE"
				+ " WHERE T.RXNJ = '"+rxnj+"' AND T.ZYID = '"+zyid+"'"
				+ " GROUP BY C.CODE_,C.NAME_,S.SCHOOL_YEAR,S.TERM_CODE,S.COURE_CODE "
				+ "ORDER BY S.SCHOOL_YEAR,S.TERM_CODE,S.COURE_CODE";
		return baseDao.queryListInLowerKey(sql);
	}

	/**
	 * @Description: 根号学号查询学生的消费信息
	 * @Return: Map<String,Object>
	 * @param sno 学号
	 */
	public Map<String, Object> queryConsumeOfStudent(String sno) {
		String sql = "SELECT SUM(TT.TIMES) TIMES,COUNT(1) DAYS,ROUND(AVG(TT.SUMVAL),2) AVGVAL,SUM(TT.SUMVAL) SUMVAL FROM ("
				+ "SELECT COUNT(1) TIMES,SUM(T.PAY_MONEY) SUMVAL,SUBSTR(T.TIME_,0,10) DATETIME FROM T_CARD_PAY T INNER JOIN T_CARD D ON T.CARD_ID = D.NO_ "
				+ "WHERE D.PEOPLE_ID = '"+sno+"' GROUP BY SUBSTR(T.TIME_,0,10)) TT";
		List<Map<String, Object>> items = baseDao.queryListInLowerKey(sql);
		Map<String, Object> result = new HashMap<String, Object>();
		if (items.size() > 0) {
			Map<String, Object> item = items.get(0);
			result.put("times", MapUtils.getIntValue(item, "times"));
			result.put("days", MapUtils.getIntValue(item, "days"));
			result.put("avgval", MapUtils.getFloatValue(item, "avgval"));
			result.put("sumval", MapUtils.getFloatValue(item, "sumval"));
		};
		return result;
	}

	/**
	 * @Description: 查询指定入学年级指定性别的学霸的消费情况
	 * @Return: Map<String,Object>
	 * @param zyid 专业ID
	 * @param rxnj 入学年级
	 * @param gender 性别
	 */
	public Map<String, Object> queryConsumeOfSmartStudent(String rxnj,String gender) {
		String sql = "SELECT MAX(P.PAY_TIMES) MAX_TIMES,ROUND(AVG(P.PAY_TIMES)) AVG_TIMES,"
				+ "MAX(P.SUM_VAL) MAX_SUM,"
				+ "ROUND(AVG(P.SUM_VAL)) AVG_SUM,"
				+ "MAX(P.AVG_DAY) MAX_DAY,"
				+ "ROUND(AVG(P.AVG_DAY),2) AVG_DAY,"
				+ "MIN(P.AVG_DAY) MIN_DAY,"
				+ "GENDER.NAME_ GENDER FROM T_STU_SMART T"
				+ " INNER JOIN T_STU_SMART_PAY P ON T.SNO = P.SNO"
				+ " INNER JOIN T_CODE GENDER ON GENDER.CODE_ = t.GENDER AND GENDER.CODE_TYPE = 'SEX_CODE'"
				+ " WHERE T.RXNJ = '"+rxnj+"' AND T.GENDER = '"+gender+"'"
				+ " GROUP BY GENDER.NAME_";
		List<Map<String, Object>> items = baseDao.queryListInLowerKey(sql);
		Map<String, Object> result = new HashMap<String, Object>();
		if (items.size() > 0) {
			Map<String, Object> item = items.get(0);
			result.put("max_times", MapUtils.getIntValue(item, "max_times"));
			result.put("avg_times", MapUtils.getIntValue(item, "avg_times"));
			result.put("max_sum", MapUtils.getFloatValue(item, "max_sum"));
			result.put("avg_sum", MapUtils.getFloatValue(item, "avg_sum"));
			result.put("max_day", MapUtils.getFloatValue(item, "max_day"));
			result.put("avg_day", MapUtils.getFloatValue(item, "avg_day"));
			result.put("min_day", MapUtils.getFloatValue(item, "min_day"));
			result.put("gender", MapUtils.getString(item, "gender"));
		};
		return result;
	}

	/**
	 * @Description: 根据学号查询学生的用餐情况
	 * @Return: Map<String,Object>
	 * @param sno 学号
	 */
	public Map<String, Object> queryDinnerOfStudent(String sno) {
		//午餐和晚餐的开始时间
		SysConfig config = SysConfig.instance();
		String lunchTime = config.getLunchTime();
		String supperTime = config.getSupperTime();	
		String sql = "SELECT SUM(T1.ZAO_TIME) ZAO_TIME,NVL(ROUND(AVG(T1.ZAO_PAY),2),0) ZAO_PAY,SUM(T1.WU_TIME) WU_TIME,"
				+ " NVL(ROUND(AVG(T1.WU_PAY),2),0) WU_PAY,SUM(T1.WAN_TIME) WAN_TIME,NVL(ROUND(AVG(T1.WAN_PAY),2),0) WAN_PAY "
				+ " FROM (SELECT "
				+ "MAX(CASE WHEN SUBSTR(T.TIME_,12,2) < "+lunchTime+" THEN 1 ELSE 0 END) ZAO_TIME,"
				+ "SUM(CASE WHEN SUBSTR(T.TIME_,12,2) < "+lunchTime+" THEN T.PAY_MONEY  END) ZAO_PAY,"
				+ "MAX(CASE WHEN SUBSTR(T.TIME_,12,2) >= "+lunchTime+" AND SUBSTR(T.TIME_,12,2) < "+supperTime+" THEN 1 ELSE 0  END) WU_TIME,"
				+ "SUM(CASE WHEN SUBSTR(T.TIME_,12,2) >= "+lunchTime+" AND SUBSTR(T.TIME_,12,2) < "+supperTime+" THEN T.PAY_MONEY END) WU_PAY,"
				+ "MAX(CASE WHEN SUBSTR(T.TIME_,12,2) >= "+supperTime+" THEN 1 ELSE 0  END) WAN_TIME,"
				+ "SUM(CASE WHEN SUBSTR(T.TIME_,12,2) >= "+supperTime+" THEN T.PAY_MONEY ELSE 0  END) WAN_PAY "
			    + " FROM T_CARD_PAY T INNER JOIN T_CARD D ON T.CARD_ID = D.NO_"
			    + "  WHERE D.PEOPLE_ID ='"+sno+"' GROUP BY SUBSTR(T.TIME_, 0, 10)) T1";
		List<Map<String, Object>> items = baseDao.queryListInLowerKey(sql);
		Map<String, Object> result = new HashMap<String, Object>();
		if (items.size() > 0) {
			Map<String, Object> item = items.get(0);
			result.put("zao_time", MapUtils.getIntValue(item, "zao_time"));
			result.put("zao_pay", MapUtils.getFloatValue(item, "zao_pay"));
			result.put("wu_time", MapUtils.getIntValue(item, "wu_time"));
			result.put("wu_pay", MapUtils.getFloatValue(item, "wu_pay"));
			result.put("wan_time", MapUtils.getIntValue(item, "wan_time"));
			result.put("wan_pay", MapUtils.getFloatValue(item, "wan_pay"));
		};
		return result;
	}

	/**
	 * @Description: 查询指定年级指定性别学霸的一日三餐用餐情况
	 * @Return: Map<String,Object>
	 * @param zyid 专业ID
	 * @param rxnj 入学年级
	 */
	public Map<String, Object> queryDinnerOfSmartStudent(String rxnj,
			String gender) {
		String sql = "SELECT GENDER.NAME_ GENDER,"
				+ " MAX(T.ZAO_TIMES) MAX_ZAO_TIME,"
				+ " ROUND(AVG(T.ZAO_TIMES)) AVG_ZAO_TIME,"
				+ " MAX(T.ZAO_AVG) MAX_ZAO_PAY,"
				+ " ROUND(AVG(T.ZAO_AVG),2) AVG_ZAO_PAY,"
				+ " MAX(T.WU_TIMES) MAX_WU_TIME,"
				+ " ROUND(AVG(T.WU_TIMES)) AVG_WU_TIME,"
				+ " MAX(T.WU_AVG) MAX_WU_PAY,"
				+ " ROUND(AVG(T.WU_AVG),2) AVG_WU_PAY,"
				+ " MAX(T.WAN_TIMES) MAX_WAN_TIME,"
				+ " ROUND(AVG(T.WAN_TIMES)) AVG_WAN_TIME,"
				+ " MAX(T.WAN_AVG) MAX_WAN_PAY,"
				+ " ROUND(AVG(T.WAN_AVG),2) AVG_WAN_PAY"
				+ "  FROM T_STU_SMART P"
				+ " INNER JOIN T_STU_SMART_PAY T"
				+ "    ON T.SNO = P.SNO"
				+ " INNER JOIN T_CODE GENDER ON GENDER.CODE_ = P.GENDER AND GENDER.CODE_TYPE = 'SEX_CODE'"
				+ "WHERE P.RXNJ = '"+rxnj+"' AND P.GENDER = '"+gender+"'"
				+ " GROUP BY GENDER.NAME_";
		List<Map<String, Object>> items = baseDao.queryListInLowerKey(sql);
		Map<String, Object> result = new HashMap<String, Object>();
		if (items.size() > 0) {
			Map<String, Object> item = items.get(0);
			result.put("max_zao_time", MapUtils.getIntValue(item, "max_zao_time"));
			result.put("avg_zao_time", MapUtils.getIntValue(item, "avg_zao_time"));
			result.put("max_zao_pay", MapUtils.getFloatValue(item, "max_zao_pay"));
			result.put("avg_zao_pay", MapUtils.getFloatValue(item, "avg_zao_pay"));
			result.put("max_wu_time", MapUtils.getIntValue(item, "max_wu_time"));
			result.put("avg_wu_time", MapUtils.getIntValue(item, "avg_wu_time"));
			result.put("max_wu_pay", MapUtils.getFloatValue(item, "max_wu_pay"));
			result.put("avg_wu_pay", MapUtils.getFloatValue(item, "avg_wu_pay"));
			result.put("max_wan_time", MapUtils.getIntValue(item, "max_wan_time"));
			result.put("avg_wan_time", MapUtils.getIntValue(item, "avg_wan_time"));
			result.put("max_wan_pay", MapUtils.getFloatValue(item, "max_wan_pay"));
			result.put("avg_wan_pay", MapUtils.getFloatValue(item, "avg_wan_pay"));
			result.put("gender", MapUtils.getString(item, "gender"));
		};
		return result;
	}

	/**
	 * @Description: 根据学号查询图书借阅量
	 * @Return: Map<String,Object>
	 * @param sno 学号
	 */
	public Map<String, Object> queryBookOfStudent(String sno) {
		String sql = "SELECT T.BOOK_ID FROM T_BOOK_BORROW T WHERE T.BOOK_READER_ID = '"+sno+"'";
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("nums", baseDao.queryForInt(sql));
		return result;
	}

	/**
	 * @Description: 查询指定专业，指定入学年级的学霸的图书借阅量
	 * @Return: Map<String,Object>
	 * @param zyid 专业ID
	 * @param rxnj 入学年级
	 */
	public Map<String, Object> queryBookOfSmartStudent(String zyid, String rxnj) {
		String sql = "SELECT NVL(MAX(TT.NUMS),0) MAXVAL,NVL(ROUND(AVG(TT.NUMS)),0) AVGVAL,NVL(MIN(TT.NUMS),0) MINVAL FROM ("
				+ "SELECT T.SNO,COUNT(B.BOOK_ID) NUMS  FROM T_STU_SMART T"
				+ "  LEFT JOIN T_BOOK_BORROW B ON T.SNO = B.BOOK_READER_ID"
				+ " WHERE T.RXNJ = '"+rxnj+"'  AND T.ZYID = '"+zyid+"'"
				+ " GROUP BY T.SNO) TT";
		List<Map<String, Object>> items = baseDao.queryListInLowerKey(sql);
		Map<String, Object> result = new HashMap<String, Object>();
		if (items.size() > 0) {
			Map<String, Object> item = items.get(0);
			result.put("maxval", MapUtils.getIntValue(item, "maxval"));
			result.put("avgval", MapUtils.getIntValue(item, "avgval"));
			result.put("minval", MapUtils.getFloatValue(item, "minval"));
		};
		return result;
	}

	/**
	 * @Description: 查询指定入学年级的学霸的图书借阅量
	 * @Return: Map<String,Object>
	 * @param zyid 专业ID
	 * @param rxnj 入学年级
	 */
	public Map<String, Object> queryBookOfSmartStudent(String rxnj) {
		String sql = "SELECT NVL(MAX(TT.NUMS),0) MAXVAL,NVL(ROUND(AVG(TT.NUMS)),0) AVGVAL,NVL(MIN(TT.NUMS),0) MINVAL FROM ("
				+ "SELECT T.SNO,COUNT(B.BOOK_ID) NUMS  FROM T_STU_SMART T"
				+ "  LEFT JOIN T_BOOK_BORROW B ON T.SNO = B.BOOK_READER_ID"
				+ " WHERE T.RXNJ = '"+rxnj+"'  GROUP BY T.SNO) TT";
		List<Map<String, Object>> items = baseDao.queryListInLowerKey(sql);
		Map<String, Object> result = new HashMap<String, Object>();
		if (items.size() > 0) {
			Map<String, Object> item = items.get(0);
			result.put("maxval", MapUtils.getIntValue(item, "maxval"));
			result.put("avgval", MapUtils.getIntValue(item, "avgval"));
			result.put("minval", MapUtils.getFloatValue(item, "minval"));
		};
		return result;
	}

	/**
	 * @Description: 查询指学号查询学生的借阅偏好
	 * @Return: Map<String,Object>
	 * @param zyid 专业ID
	 * @param rxnj 入学年级
	 */
	public Map<String, Object> queryBookTypeOfStudent(String sno) {
		//该部分内容待定
		
		return null;
	}

	/**
	 * @Description: 查询指定专业，指定入学年级的学霸的借阅偏好
	 * @Return: Map<String,Object>
	 * @param zyid 专业ID
	 * @param rxnj 入学年级
	 */
	public Map<String, Object> queryBookTypeOfSmartStudent(String zyid,String rxnj) {
		//该部分内容待定
		return null;
	}

	/**
	 * @Description: 查询指定入学年级的学霸的借阅偏好
	 * @Return: Map<String,Object>
	 * @param zyid 专业ID
	 * @param rxnj 入学年级
	 */
	public Map<String, Object> queryBookTypeOfSmartStudent(String rxnj) {
		//该部分内容待定
		return null;
	}
}