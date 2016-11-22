package cn.gilight.personal.student.card.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.student.card.service.CardService;

@Service("cardService")
public class CardServiceImpl implements CardService {

	@Autowired
	private BaseDao baseDao;
	
	@Override
	public Float queryCardBalance(String sno) {
		String sql = "SELECT TT.SURPLUS_MONEY VALUE FROM T_CARD_PAY TT "
				+ " INNER JOIN T_CARD D ON TT.CARD_ID = D.NO_ AND D.PEOPLE_ID = '"+sno+"'"
				+ " WHERE TT.TIME_ ="
				+ " (SELECT  MAX(T.TIME_) FROM T_CARD_PAY T INNER JOIN T_CARD D ON T.CARD_ID = D.NO_ WHERE D.PEOPLE_ID ='"+sno+"')";
		List<Map<String, Object>> result = baseDao.queryListInLowerKey(sql);
		if(result.size() > 0){
			Map<String, Object> it = result.get(0);
			return MapUtils.getFloat(it, "value");
		}else{
			return 0f;
		}
	}

	@Override
	public List<Map<String, Object>> queryConsumeOfDay(String sno, String date) {
		String sql ="SELECT C.PEOPLE_ID SNO,T.PAY_MONEY XFJE,T.SURPLUS_MONEY YE,SUBSTR(T.TIME_,12) XFSJ,PORT.NAME_ PORT,DEPT.NAME_ DEPT"
				+ " FROM T_CARD_PAY T"
				+ " INNER JOIN T_CARD C ON T.CARD_ID = C.NO_"
				+ " LEFT JOIN T_CARD_PORT PORT ON T.CARD_PORT_ID = PORT.NO_"
				+ " LEFT JOIN T_CARD_DEPT DEPT ON PORT.CARD_DEPT_ID = DEPT.CODE_"
				+ " WHERE SUBSTR(T.TIME_, 0, 10) = '"+date+"'  AND C.PEOPLE_ID = '"+sno+"'"
				+ " ORDER BY T.TIME_ DESC";
		List<Map<String, Object>> result = baseDao.queryListInLowerKey(sql);
		return result;
	}
	
	public Page queryConsumeOfMonth(String sno,Page page){
		String sql = "SELECT T.NAME,T.SNO,T.DATETIME,SUM(T.SUM_VAL) SUMVAL, "
				+ "MAX(DECODE(T.CARD_DEAL,'餐费支出',T.SUM_VAL,0)) CFZC,"
				+ "MAX(DECODE(T.CARD_DEAL,'商场购物',T.SUM_VAL,0)) SCGW"
				+ " FROM T_CARD_PAY_MONTH T "
				+ " WHERE T.SNO = '"+sno+"' "
				+ " GROUP BY T.NAME,T.SNO,T.DATETIME "
				+ " ORDER BY T.DATETIME DESC";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}
	
	public  Page queryConsumeDetailOfMonth(String sno,Page page,String month){
		String sql = " SELECT C.PEOPLE_ID SNO,T.PAY_MONEY XFJE,T.SURPLUS_MONEY YE,T.TIME_ XFSJ,substr(T.TIME_,9,2) rq,substr(T.TIME_,12) sj,"
				+ "  PORT.NAME_ PORT,DEPT.NAME_ DEPT"
				+ " FROM T_CARD_PAY T"
				+ " INNER JOIN T_CARD C ON T.CARD_ID = C.NO_"
				+ " LEFT JOIN T_CARD_PORT PORT ON T.CARD_PORT_ID = PORT.NO_"
				+ " LEFT JOIN T_CARD_DEPT DEPT ON PORT.CARD_DEPT_ID = DEPT.CODE_"
				+ " WHERE  C.PEOPLE_ID = '"+sno+"'  AND SUBSTR(T.TIME_,0,7) = '"+month+"'"
				+ " ORDER BY T.TIME_ DESC";
		return baseDao.queryWithPageInLowerKey(sql, page);
	}

	@Override
	public int querySameGradeNumsOFStudent(String sno) {
		String sql = "SELECT T.NO_ FROM T_STU T WHERE T.ENROLL_GRADE = (SELECT ENROLL_GRADE FROM T_STU WHERE NO_ = '"+sno+"') ";
		return baseDao.queryForInt(sql);
	}

	@Override
	public float queryTotalConsumeOfStudent(String sno) {
		String sql = "SELECT SUM(T.PAY_MONEY) SUMVAL FROM T_CARD_PAY T INNER JOIN T_CARD C ON T.CARD_ID  = C.NO_ AND C.PEOPLE_ID = '"+sno+"'";
		List<Map<String, Object>> list = baseDao.queryForList(sql);
		if (list.size() == 0) {
			return 0;
		}else {
			return MapUtils.getFloatValue(list.get(0), "SUMVAL");
		}
	}

	@Override
	public int queryConsumeTotalHigherThenStudentNums(String sno) {
		float total = this.queryTotalConsumeOfStudent(sno);
		String sql = "SELECT SUM(T.SUM_VAL),S.NO_ FROM T_CARD_PAY_MONTH T "
				+ "INNER JOIN T_STU S ON S.NO_ = T.SNO AND S.ENROLL_GRADE = "
				+ "(SELECT ENROLL_GRADE FROM T_STU WHERE NO_ = '"+sno+"')"
				+ " GROUP BY S.NO_ HAVING SUM(T.SUM_VAL) > " + total;
		return baseDao.queryForInt(sql);
	}

	@Override
	public List<Map<String, Object>> queryDealsOfStudentTotalConsume(String sno){
		String sql = "SELECT T.CARD_DEAL NAME,SUM(T.SUM_VAL) VALUE FROM T_CARD_PAY_MONTH T WHERE T.SNO = '"+sno+"' GROUP BY T.CARD_DEAL";
		return baseDao.queryListInLowerKey(sql);
	}
}
