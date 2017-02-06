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
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.po.TTeaHistory;

/**   
* @Description: 一卡通消费相关任务 
* @author Sunwg  
* @date 2016年4月11日 下午2:22:13   
*/
@Service("teacherHistoryService")
public class TeacherHistoryService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
	
	/** 
	* @Description: 收集教职工执教历史信息
	* @return JobResultBean 定时任务返回结果类
	 * @throws Exception 
	*/
	public JobResultBean collectTeaHistory() throws Exception{
		log.warn("========begin : 教职工历史记录生成任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		JobResultBean result = new JobResultBean();
		try {
			String INFO_SOURCE = "",delSql="",querySql = "";
			int delnum = 0,insertNum = 0;
			List<Map<String, Object>> resultList = null;
/* 1.查询教职工的入职 */
			/* 清空教职工入职 相关的信息*/
			INFO_SOURCE = "入校时间(T_TEA:IN_DATE)";
			delSql = "DELETE FROM T_TEA_HISTORY T WHERE T.INFO_SOURCE = '"+INFO_SOURCE+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除执教历史中的入校时间信息，共" + delnum +"条数据。 ==============");
			querySql = "SELECT T.TEA_NO TNO,T.IN_DATE INDATE,T.BIRTHDAY BIRTH,SUBSTR(T.IN_DATE,0,4) YEAR,(TO_NUMBER(SUBSTR(T.IN_DATE,0,4))- TO_NUMBER(SUBSTR(T.BIRTHDAY,0,4))) AGE FROM T_TEA T WHERE T.IN_DATE IS NOT NULL";
			resultList = baseDao.queryForList(querySql);
			if(resultList.size() > 0){
				List<TTeaHistory> hls1 = new ArrayList<TTeaHistory>();
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> it = resultList.get(i);
					TTeaHistory h1 = new TTeaHistory();
					h1.setTeaNo(MapUtils.getString(it, "TNO"));
					h1.setDatetime(MapUtils.getString(it, "INDATE"));
					h1.setYear(MapUtils.getString(it, "YEAR"));
					h1.setInfoSource(INFO_SOURCE);
					String content = "入校就职";
					String age = MapUtils.getString(it, "AGE");
					if(age.length() > 0) content += ",那年我"+age+"岁";
					h1.setContents(content);
					hls1.add(h1);
				}
				hibernate.saveAllWithAutoCommit(hls1);
				insertNum += resultList.size();
				log.debug("=========point End : " + INFO_SOURCE + "执行完毕，共插入  " + resultList.size()+"  条数据");
			}
/* 2.查询教职工执教时间 */
			INFO_SOURCE = "从教时间(T_TEA:TEACHING_DATE)";
			delSql = "DELETE FROM T_TEA_HISTORY T WHERE T.INFO_SOURCE = '"+INFO_SOURCE+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除执教历史中的从教时间信息，共" + delnum +"条数据。 ==============");
			querySql = "SELECT T.TEA_NO TNO,T.TEACHING_DATE TEACHDATE,T.BIRTHDAY BIRTH, SUBSTR(T.TEACHING_DATE,0,4) YEAR,(TO_NUMBER(SUBSTR(T.TEACHING_DATE,0,4))- TO_NUMBER(SUBSTR(T.BIRTHDAY,0,4))) AGE FROM T_TEA T WHERE T.TEACHING_DATE IS NOT NULL";
			resultList = baseDao.queryForList(querySql);
			if(resultList.size() > 0){
				List<TTeaHistory> hls2 = new ArrayList<TTeaHistory>();
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> it = resultList.get(i);
					TTeaHistory h2 = new TTeaHistory();
					h2.setTeaNo(MapUtils.getString(it, "TNO"));
					h2.setDatetime(MapUtils.getString(it, "TEACHDATE"));
					h2.setYear(MapUtils.getString(it, "YEAR"));
					h2.setInfoSource(INFO_SOURCE);
					String content =  "开始从教";
					String age = MapUtils.getString(it, "AGE");
					if(age.length() > 0)content += ",那年我"+age+"岁";
					h2.setContents(content);
					hls2.add(h2);
				}
				hibernate.saveAllWithAutoCommit(hls2);
				insertNum += resultList.size();
				log.debug("=========point End : " + INFO_SOURCE + "执行完毕，共插入  " + resultList.size()+"  条数据");
			}
/* 3.查询教职工辅导员担任信息 */
			INFO_SOURCE = "辅导员担任信息(T_CLASSES_INSTRUCTOR)";
			delSql = "DELETE FROM T_TEA_HISTORY T WHERE T.INFO_SOURCE = '"+INFO_SOURCE+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除执教历史中的辅导员担任信息，共" + delnum +"条数据。 ==============");
			querySql = "SELECT  TT.XN,TT.XQ, DECODE(TT.XQ-1,0,SUBSTR(TT.XN,0,4),1,SUBSTR(TT.XN,6,9)) YEAR,"
					+ "TT.TNO, SUM(TT.NUMS) NUMS, WM_CONCAT(TT.BJMC || '('|| TT.NUMS || '人)') BJMC"
					+ " FROM (SELECT T.SCHOOL_YEAR XN, T.TERM_CODE XQ,C.NAME_ BJMC,T.TEA_ID TNO,COUNT(S.NO_) NUMS"
						+ " FROM T_CLASSES_INSTRUCTOR T"
						+ " INNER JOIN T_CLASSES C ON T.CLASS_ID = C.NO_"
						+ " INNER JOIN T_STU S ON C.NO_ = S.CLASS_ID"
						+ " GROUP BY T.TEA_ID, T.SCHOOL_YEAR, T.TERM_CODE, C.NAME_) TT "
					+ " GROUP BY TT.XN,TT.XQ,TT.TNO";
			resultList = baseDao.queryForList(querySql);
			if(resultList.size() > 0){
				List<TTeaHistory> hls3 = new ArrayList<TTeaHistory>();
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> it = resultList.get(i);
					TTeaHistory h3 = new TTeaHistory();
					h3.setTeaNo(MapUtils.getString(it, "TNO"));
					String xnxq = MapUtils.getString(it, "XN")+"学年"+MapUtils.getString(it, "XQ")+"学期";
					h3.setDatetime(xnxq);
					h3.setYear(MapUtils.getString(it, "YEAR"));
					h3.setInfoSource(INFO_SOURCE);
					String content = "担任辅导员，共管理"+	MapUtils.getString(it, "NUMS")+"名学生，"
							+ "辅导的班级有：" + MapUtils.getString(it, "BJMC");
					h3.setContents(content);
					hls3.add(h3);
				}
				hibernate.saveAllWithAutoCommit(hls3);
				insertNum += resultList.size();
				log.debug("=========point End : " + INFO_SOURCE + "执行完毕，共插入  " + resultList.size()+"  条数据");
			}
/* 4.查询教职工职称评定 */
			INFO_SOURCE = "职称评定信息(T_TEA_ZCPD)";
			delSql = "DELETE FROM T_TEA_HISTORY T WHERE T.INFO_SOURCE = '"+INFO_SOURCE+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除执教历史中的职称评定信息，共" + delnum +"条数据。 ==============");
			querySql = "SELECT T.DATE_ DATETIME,SUBSTR(T.DATE_,0,4) YEAR,T.TEA_ID TNO,Z.NAME_ ZC FROM T_TEA_ZCPD T "
					+ " INNER JOIN T_CODE_ZYJSZW Z ON Z.CODE_ = T.ZYJSZW_CODE";
			resultList = baseDao.queryForList(querySql);
			if(resultList.size() > 0){
				List<TTeaHistory> hls4 = new ArrayList<TTeaHistory>();
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> it = resultList.get(i);
					TTeaHistory h4 = new TTeaHistory();
					h4.setTeaNo(MapUtils.getString(it, "TNO"));
					h4.setDatetime(MapUtils.getString(it, "DATETIME"));
					h4.setYear(MapUtils.getString(it, "YEAR"));
					h4.setInfoSource(INFO_SOURCE);
					String content =  "职称评定评为 ："+ MapUtils.getString(it, "ZC");
					h4.setContents(content);
					hls4.add(h4);
				}
				hibernate.saveAllWithAutoCommit(hls4);
				insertNum += resultList.size();
				log.debug("=========point End : " + INFO_SOURCE + "执行完毕，共插入  " + resultList.size()+"  条数据");
			}
			
/* 5.查询教职工人事变动 */
			/*INFO_SOURCE = "人事变动信息(T_TEA_PERSON_CHANGE)";
			delSql = "DELETE FROM T_TEA_HISTORY T WHERE T.INFO_SOURCE = '"+INFO_SOURCE+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除执教历史中的人事变动信息信息，共" + delnum +"条数据。 ==============");
			querySql = "SELECT T.DATE_ DATETIME,T.TEA_ID TNO,Z.NAME_ ZC FROM T_TEA_ZCPD T "
					+ " INNER JOIN T_CODE_ZYJSZW Z ON Z.CODE_ = T.ZYJSZW_CODE";
			resultList = baseDao.queryForList(querySql);
			List<TTeaHistory> hls5 = new ArrayList<TTeaHistory>();
			for (int i = 0; i < resultList.size(); i++) {
				Map<String, Object> it = resultList.get(i);
				TTeaHistory h5 = new TTeaHistory();
				h5.setTeaNo(MapUtils.getString(it, "TNO"));
				h5.setDatetime(MapUtils.getString(it, "DATETIME"));
				h5.setInfoSource(INFO_SOURCE);
				String content = MapUtils.getString(it, "DATETIME") +"岗位从"+MapUtils.getString(it, "CHANGEBEFORE")+"变更为 ："+ MapUtils.getString(it, "CHANGEAFTER");
				h5.setContents(content);
				hls5.add(h5);
			}
			hibernate.saveAll(hls5);*/
			
/* 6.查询教职工教学活动信息 */
			INFO_SOURCE = "教学活动信息 (T_TEA_PERSON_CHANGE)";
			delSql = "DELETE FROM T_TEA_HISTORY T WHERE T.INFO_SOURCE = '"+INFO_SOURCE+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除执教历史中的教学活动信息 信息，共" + delnum +"条数据。 ==============");
			
			String xnsql = "SELECT DISTINCT T.SCHOOL_YEAR XN FROM T_COURSE_ARRANGEMENT T ORDER BY T.SCHOOL_YEAR";
			List<Map<String, Object>> xnlist = baseDao.queryListInLowerKey(xnsql);
			int jxhdNums = 0;
			log.debug("========按学年查询教学活动记录=========");
			for (Map<String, Object> xn : xnlist) {
				String schoolyear = MapUtils.getString(xn, "xn");
				log.debug("========统计"+schoolyear+"学年的记录=========");
				querySql = "SELECT  DISTINCT TT.TNO,TT.XN,TT.XQ,DECODE(TT.XQ-1,0,SUBSTR(TT.XN,0,4),1,SUBSTR(TT.XN,6,9)) YEAR,"
						+ "TT.KCMC,WM_CONCAT(TT.BJMC || '('|| TT.NUMS || '人)') BJMC,SUM(TT.NUMS) NUMS FROM ("
						+ "  SELECT DISTINCT T.TEA_ID TNO,T.SCHOOL_YEAR XN,T.TERM_CODE XQ,K.NAME_ KCMC, C.NAME_ BJMC,COUNT(S.NO_) NUMS"
						+ "  FROM T_COURSE_ARRANGEMENT T"
						+ "  INNER JOIN T_COURSE K ON T.COURSE_ID = K.CODE_"
						+ "  INNER JOIN T_CLASS_TEACHING CT ON T.TEACHINGCLASS_ID = CT.CODE_"
						+ "  INNER JOIN T_CLASS_TEACHING_XZB CX ON CT.CODE_ = CX.TEACH_CLASS_ID"
						+ "  INNER JOIN T_CLASSES C ON CX.CLASS_ID = C.NO_"
						+ "  INNER JOIN T_STU S ON S.CLASS_ID = C.NO_"
						+ " WHERE T.SCHOOL_YEAR='"+schoolyear+"'"
						+ "  GROUP BY T.TEA_ID,T.SCHOOL_YEAR,T.TERM_CODE,K.NAME_, C.NAME_)TT"
						+ " GROUP BY TT.TNO,TT.XN,TT.XQ,TT.KCMC";
				resultList = baseDao.queryForList(querySql);
				if(resultList.size() > 0){
					List<TTeaHistory> hls6 = new ArrayList<TTeaHistory>();
					for (int i = 0; i < resultList.size(); i++) {
						Map<String, Object> it = resultList.get(i);
						TTeaHistory h6 = new TTeaHistory();
						h6.setTeaNo(MapUtils.getString(it, "TNO"));
						h6.setDatetime(MapUtils.getString(it, "XN")+"学年"+MapUtils.getString(it, "XQ")+"学期");
						h6.setYear(MapUtils.getString(it, "YEAR"));
						h6.setInfoSource(INFO_SOURCE);
						String content = "教授课程《"
										+ MapUtils.getString(it, "KCMC")+"》，共教授学生"
										+ MapUtils.getString(it, "NUMS")+"人,教授班级："+ MapUtils.getString(it, "BJMC");
						h6.setContents(content);
						hls6.add(h6);
					}
					hibernate.saveAllWithAutoCommit(hls6);
					jxhdNums += resultList.size();
					insertNum += resultList.size();
				}
			}
			log.debug("=========point End : " + INFO_SOURCE + "执行完毕，共插入  " + jxhdNums +"  条数据");
			
/*7.资产领用信息*/
			INFO_SOURCE = "资产领用信息(T_EQUIPMENT)";
			delSql = "DELETE FROM T_TEA_HISTORY T WHERE T.INFO_SOURCE = '"+INFO_SOURCE+"'";
			delnum = baseDao.delete(delSql);
			log.warn("======== delete : 清除执教历史中的资产领用信息，共" + delnum +"条数据。 ==============");
			List<Map<String, Object>> yearList = baseDao.queryForList("SELECT DISTINCT SUBSTR(T.MANAGER_DATE,0,4) YEAR FROM T_EQUIPMENT T");
			int zclyNums = 0;
			for (Map<String, Object> year : yearList) {
				String curYear = MapUtils.getString(year, "YEAR");
				querySql = "SELECT T.NAME_,T.MANAGER,T.MANAGER_DATE,T.MODES,C.NAME_ STATUS  FROM T_EQUIPMENT T "
						+ "INNER JOIN T_CODE C ON T.EQUI_STATUS_CODE = C.CODE_ AND C.CODE_TYPE = 'EQUI_STATUS_CODE' WHERE T.MANAGER_DATE LIKE '"+curYear+"%'";
				resultList = baseDao.queryForList(querySql);
				List<TTeaHistory> hls7 = new ArrayList<TTeaHistory>();
				for (int i = 0; i < resultList.size(); i++) {
					Map<String, Object> it = resultList.get(i);
					TTeaHistory h7 = new TTeaHistory();
					h7.setYear(curYear);
					h7.setTeaNo(MapUtils.getString(it, "MANAGER"));
					h7.setDatetime(MapUtils.getString(it, "MANAGER_DATE"));
					h7.setInfoSource(INFO_SOURCE);
					String content ="领用了"+MapUtils.getString(it, "NAME_")+"（"+MapUtils.getString(it, "MODES")+"）,现状为："+ MapUtils.getString(it, "STATUS");
					h7.setContents(content);
					hls7.add(h7);
				}
				hibernate.saveAllWithAutoCommit(hls7);
				zclyNums += resultList.size();
			}
			log.debug("=========point End : " + INFO_SOURCE + "执行完毕，共插入  " + zclyNums +"  条数据");
			
			insertNum += zclyNums;
/* 结束执行，打印日志，返回结果 */
			result.setIsTrue(true);
			String info = "教职工历史记录生成结束执行，共插入  " + insertNum +"  条数据,耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			result.setIsTrue(false);
			result.setMsg("教职工历史生成任务执行失败！" + e.getMessage());
			log.error("======== error : 教职工历史生成任务执行错误，停止执行 ==============");
			e.printStackTrace();
			throw e;
		}
		return result;
	}
}