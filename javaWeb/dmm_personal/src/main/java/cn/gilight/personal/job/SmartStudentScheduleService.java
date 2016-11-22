package cn.gilight.personal.job;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.SysConfig;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.po.TStuSmart;
import cn.gilight.personal.po.TStuSmartPay;

/**
* @Description: 学霸君统计service
* @author Sunwg  
* @date 2016年5月16日 下午4:56:05   
*/
@Service("smartStudentScheduleService")
public class SmartStudentScheduleService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	/** 
	* @Description: 搜索所有的学霸信息，写入学霸表
	* @return JobResultBean
	* @throws Exception 
	*/
	@Transactional
	public JobResultBean collectSmartStudents() throws Exception{
		log.warn("========begin : 学霸信息搜索任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		JobResultBean result = new JobResultBean();
		try {
			String delSql="",querySql = "";
			int delnum = 0,insertNum = 0;
			List<Map<String, Object>> xbList = new ArrayList<Map<String,Object>>();
			/*1 清空历史信息*/
			delSql = "DELETE FROM T_STU_SMART";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除学霸信息，共" + delnum +"条数据。 ==============");
			/*2 查询所有的专业信息*/
			String zySql = "SELECT T.ID, T.CODE_ ZYDM, T.NAME_ ZYMC,S.ENROLL_GRADE RXNJ,COUNT(S.NO_) NUMS FROM T_CODE_DEPT_TEACH T"
					+ " INNER JOIN T_STU S ON T.ID = S.MAJOR_ID WHERE T.LEVEL_TYPE = 'ZY' "
					+ " AND (S.ENROLL_GRADE) > TO_NUMBER(TO_CHAR(SYSDATE,'YYYY')-5)"
					+ " GROUP BY T.ID,T.CODE_,T.NAME_ ,S.ENROLL_GRADE";
			List<Map<String,Object>> zylist = baseDao.queryListInLowerKey(zySql);
			List<TStuSmart> smartList = new ArrayList<TStuSmart>();
			for (Map<String, Object> zy : zylist) {
				String zyid = MapUtils.getString(zy, "id");
				String rxnj = MapUtils.getString(zy, "rxnj");
				int stuNums = MapUtils.getIntValue(zy, "nums");
				if(stuNums > 15){
					querySql = "SELECT * FROM ("
							+ "SELECT T.NO_ SNO, T.NAME_ NAME, T.SEX_CODE GENDER,T.CLASS_ID BJID,"
							+ "SUM(CASE WHEN S.HIERARCHICAL_SCORE_CODE IS NOT NULL THEN H.CENTESIMAL_SCORE ELSE S.CENTESIMAL_SCORE END) SUM_SCORE "
							+ " FROM T_STU T"
							+ " INNER JOIN T_STU_SCORE S ON T.NO_ = S.STU_ID "
							+ " LEFT JOIN T_CODE_SCORE_HIERARCHY H ON S.HIERARCHICAL_SCORE_CODE = H.CODE_"
							+ " WHERE T.MAJOR_ID = '"+zyid+"' AND T.ENROLL_GRADE = '"+rxnj+"' AND S.CENTESIMAL_SCORE IS NOT NULL"
							+ " GROUP BY T.NO_, T.NAME_, T.SEX_CODE,T.CLASS_ID"
							+ " ORDER BY SUM(S.CENTESIMAL_SCORE) DESC ) WHERE ROWNUM <= 10";
					xbList = baseDao.queryListInLowerKey(querySql);
					for (Map<String, Object> xb : xbList) {
						TStuSmart smart = new TStuSmart();
						smart.setName(MapUtils.getString(xb, "name"));
						smart.setSno(MapUtils.getString(xb, "sno"));
						smart.setBjid(MapUtils.getString(xb, "bjid"));
						smart.setGender(MapUtils.getString(xb, "gender"));
						smart.setRxnj(rxnj);
						smart.setZyid(zyid);
						smart.setScore(MapUtils.getDoubleValue(xb, "sum_score"));
						smartList.add(smart);
						insertNum++;
					}
				}
			}
			hibernate.saveAll(smartList);
			
			/* 结束执行，打印日志，返回结果 */
			result.setIsTrue(true);
			String info = "学霸信息统计结束执行，共插入  " + insertNum +"  条数据,耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			result.setIsTrue(false);
			result.setMsg("学霸信息统计任务执行失败！" + e.getMessage());
			log.error("======== error :学霸信息统计任务执行错误，停止执行 ==============");
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	/** 
	* @Description: 获取最近的5个入学年级
	* @return List<String>
	*/
	public List<String> getLastFiveEntrollGrade(){
		List<String> result = new ArrayList<String>();
		int currentMonth = new Integer(DateUtils.getNowMonth());
		int currentYear = new Integer(DateUtils.getNowYear());
		if(currentMonth <= 7){
			currentYear--;
		}
		for (int i = 0; i < 5; i++) {
			result.add(""+(currentYear - i));
		}
		return result;
	}
	
	/** 
	* @Description: 搜集学霸的消费情况
	* @Return: JobResultBean
	* @throws Exception 
	*/
	public JobResultBean smartStuPayInfo() throws Exception{
		log.warn("========begin : 学霸消费情况搜索任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		JobResultBean result = new JobResultBean();
		try {
			String delSql="",querySql = "";
			int delnum = 0,insertNum = 0;
			List<Map<String, Object>> resultlist = new ArrayList<Map<String,Object>>();
			//1.查询所有的入学年级
			String rxnjsql = "SELECT DISTINCT T.RXNJ FROM T_STU_SMART T";
			List<Map<String,Object>> rxnjlist = baseDao.queryForList(rxnjsql); 
			//午餐和晚餐的开始时间
			SysConfig config = SysConfig.instance();
			String lunchTime = config.getLunchTime();
			String supperTime = config.getSupperTime();			
			//遍历入学年级
			for (Map<String, Object> rxnjmap : rxnjlist) {
				String rxnj = MapUtils.getString(rxnjmap, "RXNJ");
				//2.删除的对应入学年级学霸消费数据
				delSql = "DELETE FROM T_STU_SMART_PAY WHERE SNO IN(SELECT SNO FROM T_STU_SMART WHERE RXNJ = '"+rxnj+"')"; 
				delnum = baseDao.delete(delSql);
				log.warn("======== delete : 清除入学年级为："+rxnj+"的学霸消费信息，共" + delnum +"条数据。 ==============");
				//3.查询该学年的所有专业
				String zysql = "SELECT DISTINCT T.ZYID,ZY.NAME_ ZYMC FROM T_STU_SMART T INNER JOIN T_CODE_DEPT_TEACH ZY ON T.ZYID = ZY.ID WHERE RXNJ = '"+rxnj+"'";
				List<TStuSmartPay> smartPayList = new ArrayList<TStuSmartPay>();
				List<Map<String,Object>> zylist = baseDao.queryForList(zysql); 
				for (Map<String, Object> zy : zylist) {
					log.debug("======== query : 查询"+zy.get("ZYMC")+"入学年级为："+rxnj+"的学霸消费信息。 ==============");
					//4.查询该入学年级+专业 学霸君的消费情况
					querySql = "SELECT T1.SNO,COUNT(T1.DATETIME) DAYS,SUM(T1.TIMES) TIMES,SUM(T1.SUMVAL) SUMVAL,TRUNC(AVG(T1.SUMVAL),2) AVGVAL,"
							+ "SUM(T1.ZAO_TIME) ZAO_TIME,NVL(TRUNC(AVG(T1.ZAO_PAY),2),0) ZAO_PAY,SUM(T1.WU_TIME) WU_TIME,"
							+ "NVL(TRUNC(AVG(T1.WU_PAY),2),0) WU_PAY,SUM(T1.WAN_TIME) WAN_TIME,NVL(TRUNC(AVG(T1.WAN_PAY),2),0) WAN_PAY "
						+ "FROM (SELECT D.PEOPLE_ID SNO,  COUNT(1) TIMES, SUBSTR(T.TIME_, 0, 10) DATETIME,"
								+ "SUM(T.PAY_MONEY) SUMVAL,"
								+ "MAX(CASE WHEN SUBSTR(T.TIME_,12,2) < "+lunchTime+" THEN 1 ELSE 0 END) ZAO_TIME,"
								+ "SUM(CASE WHEN SUBSTR(T.TIME_,12,2) < "+lunchTime+" THEN T.PAY_MONEY  END) ZAO_PAY,"
								+ "MAX(CASE WHEN SUBSTR(T.TIME_,12,2) >= "+lunchTime+" AND SUBSTR(T.TIME_,12,2) < "+supperTime+" THEN 1 ELSE 0  END) WU_TIME,"
								+ "SUM(CASE WHEN SUBSTR(T.TIME_,12,2) >= "+lunchTime+" AND SUBSTR(T.TIME_,12,2) < "+supperTime+" THEN T.PAY_MONEY END) WU_PAY,"
								+ "MAX(CASE WHEN SUBSTR(T.TIME_,12,2) >= "+supperTime+" THEN 1 ELSE 0  END) WAN_TIME,"
								+ "SUM(CASE WHEN SUBSTR(T.TIME_,12,2) >= "+supperTime+" THEN T.PAY_MONEY ELSE 0  END) WAN_PAY"
							+ " FROM T_CARD_PAY T"
								+ " INNER JOIN T_CARD D ON T.CARD_ID = D.NO_"
							+ " WHERE D.PEOPLE_ID IN (SELECT SNO FROM T_STU_SMART WHERE RXNJ='"+rxnj+"' AND ZYID='"+MapUtils.getString(zy, "ZYID")+"')"
							+ " GROUP BY D.PEOPLE_ID, SUBSTR(T.TIME_, 0, 10)) T1 "
						+ "GROUP BY T1.SNO";
					resultlist = baseDao.queryListInLowerKey(querySql);
					//5.将消费信息转换成实体类，并保存
					for (Map<String, Object> it : resultlist) {
						TStuSmartPay pay = new TStuSmartPay();
						pay.setSno(MapUtils.getString(it, "sno"));
						pay.setPayDays(MapUtils.getIntValue(it, "days"));
						pay.setPayTimes(MapUtils.getIntValue(it, "times"));
						pay.setAvgDay(MapUtils.getDoubleValue(it, "avgval"));
						pay.setSumVal(MapUtils.getDoubleValue(it, "sumval"));
						pay.setZaoTimes(MapUtils.getIntValue(it, "zao_time"));
						pay.setZaoAvg(MapUtils.getDoubleValue(it, "zao_pay"));
						pay.setWuTimes(MapUtils.getIntValue(it, "wu_time"));
						pay.setWuAvg(MapUtils.getDoubleValue(it, "wu_pay"));
						pay.setWanTimes(MapUtils.getIntValue(it, "wan_time"));
						pay.setWanAvg(MapUtils.getDoubleValue(it, "wan_pay"));
						smartPayList.add(pay);
					}
				}
				hibernate.saveAllWithAutoCommit(smartPayList);
				log.debug("======== goingon : 插入入学年级为："+rxnj+"的学霸消费信息，共" + smartPayList.size() +"条数据，"
						+ "已经耗时： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒。 ==============");
			}
			/* 结束执行，打印日志，返回结果 */
			result.setIsTrue(true);
			String info = "学霸消费情况统计结束执行，共插入  " + insertNum +"  条数据,耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			result.setIsTrue(false);
			result.setMsg("学霸消费情况统计任务执行失败！" + e.getMessage());
			log.error("======== error :学霸消费情况统计任务执行错误，停止执行 ==============");
			e.printStackTrace();
			throw e;
		}
		return result;
	}
}