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

import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.po.TCardStayLate;
import cn.gilight.personal.po.TCardStayNotin;

/**   
* @Description: 预警相关任务调度service
* @author Sunwg  
* @date 2016年5月18日 上午11:30:09   
*/
@Service("warningScheduleService")
public class WarningScheduleService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	/** 
	* @Title: lateStudents 
	* @Description: 晚归学生统计Job
	* @Return: JobResultBean
	* @throws Exception 
	*/
	@Transactional
	public JobResultBean lateStudents() throws Exception{
		log.warn("========begin : 晚归学生统计任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		JobResultBean result = new JobResultBean();
		if(!checkIsAbleToCountYestoday()) {
			result.setIsTrue(true);
			result.setMsg("放假期间，不用统计该项内容");
			return result;
		}
		try {
			String delSql="",querySql = "";
			int delnum = 0,insertNum = 0;
			List<Map<String, Object>> srclist = new ArrayList<Map<String,Object>>();
			String nowDate  = DateUtils.getYesterday();
			//1 清空历史信息
			delSql = "DELETE FROM T_CARD_STAY_LATE WHERE DATETIME = '"+nowDate+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除晚归学生信息，共" + delnum +"条数据。 ==============");
			List<TCardStayLate> wglist = new ArrayList<TCardStayLate>();
			querySql = "SELECT  SUBSTR(T.TIME_,0,10) DATETIME, SUBSTR(T.TIME_,12,5) BACKTIME,"
						+ " S.NO_ SNO,S.NAME_ SNAME,S.MAJOR_ID ZYID,S.CLASS_ID BJID,BJ.NAME_ BJMC "
					+ " FROM T_DORM_RKE T"
						+ " INNER JOIN T_DORM_PROOF D ON T.DORM_PROOF_ID = D.NO_"
						+ " INNER JOIN T_STU S ON D.PEOPLE_ID = S.NO_ AND S.STU_STATE_CODE = '1'"
						+ " INNER JOIN T_CLASSES  BJ ON BJ.NO_ = S.CLASS_ID"
					+ " WHERE T.TIME_ LIKE '"+nowDate+"%' AND T.TIME_ > '"+nowDate+" 22:30'";
			srclist = baseDao.queryListInLowerKey(querySql);
			for (Map<String, Object> wg : srclist) {
				TCardStayLate it = new TCardStayLate();
				it.setSno(MapUtils.getString(wg, "sno"));
				it.setName(MapUtils.getString(wg, "sname"));
				it.setBjid(MapUtils.getString(wg, "bjid"));
				it.setBjmc(MapUtils.getString(wg, "bjmc"));
				it.setDatetime(nowDate);
				it.setBacktime(MapUtils.getString(wg, "backtime"));
				wglist.add(it);
				insertNum++;
			}
			if (wglist.size() > 0) {
				hibernate.saveAll(wglist);
			}
			// 结束执行，打印日志，返回结果 
			result.setIsTrue(true);
			String info = "晚归学生信息统计结束执行，共插入  " + insertNum +"  条数据,耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			result.setIsTrue(false);
			result.setMsg("晚归学生信息统计任务执行失败！" + e.getMessage());
			log.error("======== error : 晚归学生信息任务执行错误，停止执行 ==============");
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	/*public JobResultBean lateStudents() throws Exception{
		log.warn("========begin : 晚归学生统计任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		JobResultBean result = new JobResultBean();
		if(!checkIsAbleToCountYestoday()) {
			result.setIsTrue(true);
			result.setMsg("放假期间，不用统计该项内容");
			return result;
		}
		try {
			String delSql="",querySql = "";
			int delnum = 0,insertNum = 0;
			List<Map<String, Object>> srclist = new ArrayList<Map<String,Object>>();
			String nowDate  = DateUtils.getYesterday();
			int i = 100;
			do{
				List<TCardStayLate> wglist = new ArrayList<TCardStayLate>();
				querySql = "SELECT  SUBSTR(T.TIME_,0,10) DATETIME, SUBSTR(T.TIME_,12,5) BACKTIME,"
							+ " S.NO_ SNO,S.NAME_ SNAME,S.MAJOR_ID ZYID,S.CLASS_ID BJID,BJ.NAME_ BJMC "
						+ " FROM T_DORM_RKE T"
							+ " INNER JOIN T_DORM_PROOF D ON T.DORM_PROOF_ID = D.NO_"
							+ " INNER JOIN T_STU S ON D.PEOPLE_ID = S.NO_ AND S.STU_STATE_CODE = '1'"
							+ " INNER JOIN T_CLASSES  BJ ON BJ.NO_ = S.CLASS_ID"
						+ " WHERE T.TIME_ LIKE '"+nowDate+"%' AND T.TIME_ > '"+nowDate+" 22:30'";
				srclist = baseDao.queryListInLowerKey(querySql);
				for (Map<String, Object> wg : srclist) {
					TCardStayLate it = new TCardStayLate();
					it.setSno(MapUtils.getString(wg, "sno"));
					it.setName(MapUtils.getString(wg, "sname"));
					it.setBjid(MapUtils.getString(wg, "bjid"));
					it.setBjmc(MapUtils.getString(wg, "bjmc"));
					it.setDatetime(nowDate);
					it.setBacktime(MapUtils.getString(wg, "backtime"));
					wglist.add(it);
					insertNum++;
				}
				if (wglist.size() > 0) {
					hibernate.saveAllWithAutoCommit(wglist);
				}
				nowDate = DateUtils.getPrevDay(nowDate);
				i--;
			}while(i >0);
			result.setIsTrue(true);
			String info = "晚归学生信息统计结束执行，共插入  " + insertNum +"  条数据,耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			result.setIsTrue(false);
			result.setMsg("晚归学生信息统计任务执行失败！" + e.getMessage());
			log.error("======== error : 晚归学生信息任务执行错误，停止执行 ==============");
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	*/
	
	/** 
	* @Description: 疑似不在校学生统计
	* @Return: JobResultBean
	* @throws Exception 
	*/
	@Transactional
	public JobResultBean notInStudents() throws Exception{
		log.warn("========begin : 疑似不在校学生统计任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		JobResultBean result = new JobResultBean();
		if(!checkIsAbleToCountYestoday()) {
			result.setIsTrue(true);
			result.setMsg("放假期间，不用统计该项内容");
			return result;
		}
		try {
			String delSql="",querySql = "";
			int delnum = 0,insertNum = 0;
			List<Map<String, Object>> srclist = new ArrayList<Map<String,Object>>();
			String nowDate  = DateUtils.getYesterday();
			/*1 清空历史信息*/
			delSql = "DELETE FROM T_CARD_STAY_NOTIN WHERE DATETIME = '"+nowDate+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除疑似不在校学生，共" + delnum +"条数据。 ==============");
			//查询所有院系
			List<Map<String, Object>> yxlist= baseDao.queryForList("SELECT DISTINCT T.ID,T.NAME_ NAME FROM T_CODE_DEPT_TEACH T INNER JOIN T_STU S ON T.ID = S.DEPT_ID WHERE T.LEVEL_TYPE = 'YX'");
			for (int j = 0; j < yxlist.size(); j++){
				String yxid = MapUtils.getString(yxlist.get(j), "ID");
				String yxname = MapUtils.getString(yxlist.get(j), "NAME");
				List<TCardStayNotin> wglist = new ArrayList<TCardStayNotin>();
				querySql = "SELECT TT.SNO,TT.SNAME,TT.BJID,TT.BJMC,MAX(TT.LASTTIME) LASTTIME FROM ("
						+ "(SELECT T.NO_ SNO,T.NAME_ SNAME,T.CLASS_ID BJID,BJ.NAME_ BJMC,MAX(P.TIME_) LASTTIME FROM T_STU T"
							+ " INNER JOIN T_CLASSES BJ ON BJ.NO_ = T.CLASS_ID"
							+ " INNER JOIN T_CARD D ON T.NO_ = D.PEOPLE_ID"
							+ " INNER JOIN T_CARD_PAY P ON D.NO_ = P.CARD_ID"
							+ " WHERE T.STU_STATE_CODE = '1' AND T.DEPT_ID = '"+yxid+"'"
							+ " GROUP BY T.NO_,T.NAME_,T.CLASS_ID,BJ.NAME_ ) "
						+ " UNION"
						+ "(SELECT T.NO_ SNO,T.NAME_ SNAME,T.CLASS_ID BJID,BJ.NAME_ BJMC,MAX(P.TIME_) LASTTIME FROM T_STU T"
							+ " INNER JOIN T_CLASSES BJ ON BJ.NO_ = T.CLASS_ID"
							+ " INNER JOIN T_DORM_PROOF D ON T.NO_ = D.PEOPLE_ID"
							+ " INNER JOIN T_DORM_RKE P ON D.NO_ = P.DORM_PROOF_ID"
							+ " WHERE T.STU_STATE_CODE = '1' AND T.DEPT_ID = '"+yxid+"'"
							+ " GROUP BY T.NO_,T.NAME_,T.CLASS_ID,BJ.NAME_ )"
						+ " UNION"
						+ "(SELECT T.NO_ SNO,T.NAME_ SNAME,T.CLASS_ID BJID,BJ.NAME_ BJMC,MAX(P.TIME_) LASTTIME FROM T_STU T "
							+ " INNER JOIN T_CLASSES BJ ON BJ.NO_ = T.CLASS_ID "
							+ " INNER JOIN T_BOOK_READER R ON T.NO_ = R.PEOPLE_ID "
							+ " INNER JOIN T_BOOK_RKE P ON R.NO_ = P.BOOK_READER_ID "
							+ " WHERE T.STU_STATE_CODE = '1' AND T.DEPT_ID = '"+yxid+"' "
							+ " GROUP BY T.NO_,T.NAME_,T.CLASS_ID,BJ.NAME_ )"
						+ " )TT GROUP BY TT.SNO,TT.SNAME,TT.BJID,TT.BJMC "
						+ " HAVING SYSDATE - TO_DATE(MAX(TT.LASTTIME),'YYYY-MM-DD HH24:MI:SS') > 3 ";
				srclist = baseDao.queryListInLowerKey(querySql);
				for (Map<String, Object> wg : srclist) {
					TCardStayNotin it = new TCardStayNotin();
					it.setSno(MapUtils.getString(wg, "sno"));
					it.setName(MapUtils.getString(wg, "sname"));
					it.setBjid(MapUtils.getString(wg, "bjid"));
					it.setBjmc(MapUtils.getString(wg, "bjmc"));
					it.setDatetime(nowDate);
					it.setLasttime(MapUtils.getString(wg, "lasttime"));
					wglist.add(it);
					insertNum++;
				}
				if(wglist.size() > 0){
					hibernate.saveAll(wglist);
					log.debug("========= "+yxname+"疑似不在校统计结束，共计"+wglist.size()+"条数据！ =========");
				}
			}
			/* 结束执行，打印日志，返回结果 */
			result.setIsTrue(true);
			String info = "疑似不在校学生统计结束执行，共插入  " + insertNum +"  条数据,耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			result.setIsTrue(false);
			result.setMsg("疑似不在校学生统计任务执行失败！" + e.getMessage());
			log.error("======== error : 疑似不在校学生统计任务执行错误，停止执行 ==============");
			e.printStackTrace();
			throw e;
		}
		return result;
	}
	
	private boolean checkIsAbleToCountYestoday(){
		String yestoday = DateUtils.getYesterday();
		String sql = "select count(1) from t_school_start T where '"+yestoday+"' between t.start_date and t.end_date";
		int nums = baseDao.queryForIntBase(sql);
		if(nums > 0) return true;
		else return false;
	}
}