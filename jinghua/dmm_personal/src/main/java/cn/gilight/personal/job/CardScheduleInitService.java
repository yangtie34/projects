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
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.personal.po.TCardPayAbnormal;
import cn.gilight.personal.po.TCardPayDay;
import cn.gilight.personal.po.TCardPayMonth;
import cn.gilight.personal.po.TCardPayPortYear;

/**   
* @Description: 一卡通消费相关任务 
* @author Sunwg  
* @date 2016年4月11日 下午2:22:13   
*/
@Service("cardScheduleInitService")
public class CardScheduleInitService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
		
	/** 
	* @Description: 一卡通日消费情况统计初始化
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardDay(){
		log.warn("========begin : 一卡通消费日统计初始化任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		String delsql = "DELETE FROM T_CARD_PAY_DAY ";
		int nums = baseDao.delete(delsql);
		log.warn("======== running : 一卡通消费日统计初始化清空存储表，共"+nums+"条数据 ==============");
		String sql = "SELECT SEX.NAME_ GENDER,"
				+ "T.CARD_DEAL_ID,"
				+ "T.CARD_DEAL,"
				+ "SUM(T.NUMS) TOTAL_COUNT,"
				+ "COUNT(T.CARD_ID) PERSON_COUNT,"
				+ "SUM(T.PAY_MONEY) SUM_VAL,"
				+ "MAX(T.PAY_MONEY) MAX_VAL,"
				+ "MIN(T.PAY_MONEY) MIN_VAL,"
				+ "trunc(AVG(T.PAY_MONEY)) AVG_VAL,"
				+ "T.DATETIME "
				+ " FROM (SELECT TT.CARD_ID,SUM(TT.PAY_MONEY) PAY_MONEY,COUNT(TT.ID) NUMS,SUBSTR(TT.TIME_,0,10) DATETIME,TT.CARD_DEAL_ID,DL.NAME_ CARD_DEAL"
					+ " FROM T_CARD_PAY TT LEFT JOIN T_CODE_CARD_DEAL DL ON TT.CARD_DEAL_ID = DL.ID"
					+ " GROUP BY TT.CARD_ID,SUBSTR(TT.TIME_,0,10),TT.CARD_DEAL_ID,DL.NAME_) T"
				+ " INNER JOIN T_CARD C ON T.CARD_ID = C.NO_ "
				+ " INNER JOIN T_STU S ON C.PEOPLE_ID = S.NO_"
				+ " INNER JOIN T_CODE SEX ON S.SEX_CODE = SEX.CODE_ AND SEX.CODE_TYPE = 'SEX_CODE'"
				+ " GROUP BY SEX.NAME_,T.DATETIME,T.CARD_DEAL_ID, T.CARD_DEAL";
		try {
			List<Map<String, Object>> datalist = baseDao.queryForList(sql);
			log.warn("======== running : 一卡通消费日统计初始化开始插入结果，共"+datalist.size() +"条数据 ==============");
			if (datalist.size() > 0) {
				List<TCardPayDay> saveData = new ArrayList<TCardPayDay>();
				for (int i = 0; i < datalist.size(); i++) {
					Map<String, Object> it = datalist.get(i);
					TCardPayDay item = new TCardPayDay();
					item.setDatetime(MapUtils.getString(it, "DATETIME"));
					item.setTotal(MapUtils.getLongValue(it, "PERSON_COUNT"));
					item.setGender(MapUtils.getString(it, "GENDER"));
					item.setCardDealId(MapUtils.getString(it, "CARD_DEAL_ID"));
					item.setCardDeal(MapUtils.getString(it, "CARD_DEAL"));
					item.setSumVal(MapUtils.getDoubleValue(it, "SUM_VAL"));
					item.setAvgVal(MapUtils.getDoubleValue(it, "AVG_VAL"));
					item.setMaxVal(MapUtils.getDoubleValue(it, "MAX_VAL"));
					item.setMinVal(MapUtils.getDoubleValue(it, "MIN_VAL"));
					item.setPaytimes(MapUtils.getLongValue(it, "TOTAL_COUNT"));
					saveData.add(item);
				}
				hibernate.saveAll(saveData);
			}
			result.setIsTrue(true);
			String info = "一卡通消费日统计初始化结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 一卡通消费日统计初始化出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
	
	/** 
	* @Description: 一卡通月消费初始化任务统计 
	* @return JobResultBean 任务调度返回结果
	*/
	public JobResultBean cardMonth(){
		log.warn("========begin : 一卡通月消费初始化任务任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		List<Map<String, Object>> datelist = baseDao.queryForList("SELECT DISTINCT SUBSTR(T.TIME_,0,7) DATETIME FROM T_CARD_PAY T ORDER BY SUBSTR(T.TIME_,0,7) DESC");
		try {
			for (int i = 0; i < datelist.size(); i++) {
				String nowDate  = MapUtils.getString(datelist.get(i), "DATETIME");
				String delsql = "DELETE FROM T_CARD_PAY_MONTH WHERE DATETIME='"+nowDate+"'";
				int nums = baseDao.delete(delsql);
				log.warn("======== running : 一卡通月消费初始化任务清除数据，共"+nums+"条数据 ==============");
				
				String sql = " SELECT T.CARD_ID, T.CARD_DEAL_ID, SUM(T.PAY_MONEY) SUM_VAL,DL.NAME_ CARD_DEAL,D.PEOPLE_ID SNO,NVL(S.NAME_,TEA.NAME_) NAME "
						+ " FROM T_CARD_PAY T  INNER JOIN T_CODE_CARD_DEAL DL ON DL.ID = T.CARD_DEAL_ID"
						+ " INNER JOIN T_CARD D ON T.CARD_ID = D.NO_"
						+ " LEFT JOIN T_STU S ON S.NO_ = D.PEOPLE_ID"
						+ " LEFT JOIN T_TEA TEA ON TEA.TEA_NO= D.PEOPLE_ID"
						+ " WHERE SUBSTR(T.TIME_, 0, 7) = '"+nowDate+"'"
						+ " GROUP BY T.CARD_ID, T.CARD_DEAL_ID,DL.NAME_,D.PEOPLE_ID,S.NAME_,TEA.NAME_";
				List<Map<String, Object>> datalist = baseDao.queryForList(sql);
				log.warn("======== running : 一卡通月消费初始化任务 开始插入"+nowDate+"结果，共"+datalist.size() +"条数据 ==============");
				if (datalist.size() > 0) {
					List<TCardPayMonth> saveData = new ArrayList<TCardPayMonth>();
					for (int k = 0; k < datalist.size(); k++) {
						Map<String, Object> it = datalist.get(k);
						TCardPayMonth item = new TCardPayMonth();
						item.setDatetime(nowDate);
						item.setCardid(MapUtils.getString(it, "CARD_ID"));
						item.setSno(MapUtils.getString(it, "SNO"));
						item.setName(MapUtils.getString(it, "NAME"));
						item.setSumVal(MapUtils.getDoubleValue(it, "SUM_VAL"));
						item.setCardDealId(MapUtils.getString(it, "CARD_DEAL_ID"));
						item.setCardDeal(MapUtils.getString(it, "CARD_DEAL"));
						saveData.add(item);
					}
					hibernate.saveAllWithAutoCommit(saveData);
					String info = "已经耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
					log.warn("======== "+nowDate+" :" + info+" ============== ");
				}
			} 
			result.setIsTrue(true);
			String info = "一卡通月消费初始化任务 结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		}catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 一卡通月消费初始化任务 出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		
		return result;
	}
	
	
	/** 
	* @Description: 一卡通学生各端口消费年报初始化
	* @return JobResultBean
	*/
	public JobResultBean cardPortYear(){
		log.warn("========begin : 一卡通学生各端口消费年报初始化任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		String delsql = "DELETE FROM T_CARD_PAY_PORT_YEAR";
		int nums = baseDao.delete(delsql);
		log.warn("======== running : 一卡通学生各端口消费年报初始化 清除数据，共"+nums+"条数据 ==============");

		List<Map<String, Object>> datelist = baseDao.queryForList("SELECT DISTINCT SUBSTR(T.TIME_,0,4) DATETIME FROM T_CARD_PAY T ORDER BY SUBSTR(T.TIME_,0,4) DESC");
		//查询所有院系
		List<Map<String, Object>> yxlist= baseDao.queryForList("SELECT DISTINCT T.ID,T.NAME_ NAME FROM T_CODE_DEPT_TEACH T INNER JOIN T_STU S ON T.ID = S.DEPT_ID WHERE T.LEVEL_TYPE = 'YX'");
		try {
			for (int k = 0; k < datelist.size(); k++) {
				String nowYear  = MapUtils.getString(datelist.get(k), "DATETIME");
				for (int j = 0; j < yxlist.size(); j++){
					String yxid = MapUtils.getString(yxlist.get(j), "ID");
					String yxname = MapUtils.getString(yxlist.get(j), "NAME");
					String sql = "SELECT T.CARD_ID, T.CARD_PORT_ID, SUM(T.PAY_MONEY) SUM_VAL,P.NAME_ CARD_PORT,"
						+ "S.NO_ SNO,S.NAME_ NAME,COUNT(T.ID) PAY_TIMES,MAX(T.PAY_MONEY) MAX_VAL"
						+ "  FROM T_CARD_PAY T"
						+ " INNER JOIN T_CARD_PORT PORT ON PORT.NO_ = T.CARD_PORT_ID"
						+ " INNER JOIN T_CARD_DEPT P ON PORT.CARD_DEPT_ID = P.CODE_"
						+ " INNER JOIN T_CARD D ON T.CARD_ID = D.NO_"
						+ " INNER JOIN T_STU S ON S.NO_ = D.PEOPLE_ID"
						+ " WHERE SUBSTR(T.TIME_, 0, 4) = '"+nowYear+"' AND S.DEPT_ID = '"+yxid+"'"
						+ " GROUP BY T.CARD_ID, T.CARD_PORT_ID,P.NAME_,S.NO_,S.NAME_";
					List<Map<String, Object>> datalist = baseDao.queryForList(sql);
					log.warn("======== running : 一卡通学生各端口消费年报初始化 开始插入"+nowYear+"年结果，共"+datalist.size() +"条数据 ==============");
					if (datalist.size() > 0) {
						List<TCardPayPortYear> saveData = new ArrayList<TCardPayPortYear>();
						for (int i = 0; i < datalist.size(); i++) {
							Map<String, Object> it = datalist.get(i);
							TCardPayPortYear item = new TCardPayPortYear();
							item.setDatetime(nowYear);
							item.setCardid(MapUtils.getString(it, "CARD_ID"));
							item.setSno(MapUtils.getString(it, "SNO"));
							item.setName(MapUtils.getString(it, "NAME"));
							item.setPayTimes(MapUtils.getLongValue(it, "PAY_TIMES"));
							item.setSumVal(MapUtils.getDoubleValue(it, "SUM_VAL"));
							item.setMaxVal(MapUtils.getDoubleValue(it, "MAX_VAL"));
							item.setCardPortId(MapUtils.getString(it, "CARD_PORT_ID"));
							item.setCardPort(MapUtils.getString(it, "CARD_PORT"));
							saveData.add(item);
						}
						hibernate.saveAllWithAutoCommit(saveData);
						log.warn("一卡通学生各端口消费年报初始化 "+nowYear+"年 "+yxname+",累计"+saveData.size()+"条数据,已耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ");
					}
				}
			}
			result.setIsTrue(true);
			String info = "一卡通学生各端口消费年报初始化 结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 一卡通学生各端口消费年报初始化 出现错误，停止执行 ==============");
			e.printStackTrace();
		}
		return result;
	}
	
	
	/** 
	* @Description: 一卡通日高低消费异常-高低消费统计初始化，按专业统计（统计人数在系统配置文件中配置）
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardPayAbnormal(){
		//定义结果
		JobResultBean result = new JobResultBean();
		log.warn("========begin : 一卡通消费异常 ：高低消费统计初始化任务开始执行 ==============");
		//查询学生平均日消费金额
		String avgsql = "SELECT  ROUND(AVG(TT.VAL)) VAL FROM(SELECT SUM(T.SUM_VAL)/SUM(T.TOTAL) VAL FROM T_CARD_PAY_DAY T GROUP BY T.DATETIME,T.GENDER)  TT";
		List<Map<String, Object>> a = baseDao.queryForList(avgsql);
		if (a.size() == 0) {
			result.setIsTrue(false);
			result.setMsg("一卡通日消费统计接口未执行成功，表中没有数据无法计算日均值");
			return result;
		}
		int avgPay = MapUtils.getIntValue(a.get(0),	"VAL"); //消费平均值
		
		Long beginTime = System.currentTimeMillis();
		
		//清除重复的数据
		String delsql = "DELETE FROM T_CARD_PAY_ABNORMAL";
		int nums = baseDao.delete(delsql);
		log.warn("======== running : 一卡通消费异常 ：高低消费统计初始化 清除数据，共"+nums+"条数据 ==============");
		
		//查询所有院系
		List<Map<String, Object>> yxlist= baseDao.queryForList("SELECT DISTINCT T.ID,T.NAME_ NAME FROM T_CODE_DEPT_TEACH T INNER JOIN T_STU S ON T.ID = S.DEPT_ID WHERE T.LEVEL_TYPE = 'YX'");
		List<Map<String, Object>> datelist = baseDao.queryForList("SELECT * FROM (SELECT DISTINCT SUBSTR(T.TIME_,0,10) DATETIME FROM T_CARD_PAY T ORDER BY SUBSTR(T.TIME_,0,10) DESC)  WHERE ROWNUM <= 30");
		int countNum = SysConfig.instance().getCardPayAbnormalNum();
		try {
			for (int k = 0; k < datelist.size(); k++) {
				String nowDate  = MapUtils.getString(datelist.get(k), "DATETIME");
				List<TCardPayAbnormal> saveData = new ArrayList<TCardPayAbnormal>();
				for (int i = 0; i < yxlist.size(); i++) {
					String yxid = MapUtils.getString(yxlist.get(i), "ID");
					String yxname = MapUtils.getString(yxlist.get(i), "NAME");
					log.warn("======== running : 一卡通消费异常 ： "+yxname+" 高消费的前  "+ countNum +" 名学生==============");
					String highsql = "SELECT * FROM (SELECT S.NO_ SNO,S.NAME_ SNAME,SUM(T.PAY_MONEY) SUM_VAL,T.CARD_ID"
							+ " FROM T_CARD_PAY T"
							+ " INNER JOIN T_CARD C ON T.CARD_ID = C.NO_"
							+ " INNER JOIN T_STU S ON S.NO_ = C.PEOPLE_ID"
							+ " WHERE S.DEPT_ID = '"+yxid+"' AND SUBSTR(T.TIME_, 0, 10) = '"+nowDate+"'"
							+ " GROUP BY S.NO_, S.NAME_,T.CARD_ID"
							+ " HAVING SUM(T.PAY_MONEY)>"+ avgPay
							+ " ORDER BY SUM(T.PAY_MONEY) DESC) "
							+ " WHERE ROWNUM <= "+countNum ;
					List<Map<String, Object>> highlist = baseDao.queryForList(highsql);
					if (highlist.size() > 0) {
						for (int j = 0; j < highlist.size(); j++) {
							Map<String, Object> it = highlist.get(j);
							TCardPayAbnormal item = new TCardPayAbnormal();
							item.setDatetime(nowDate);
							item.setCardNo(MapUtils.getString(it, "SUM_VAL"));
							item.setIshigh(true);
							item.setSno(MapUtils.getString(it, "SNO"));
							item.setSname(MapUtils.getString(it, "SNAME"));
							item.setYxId(yxid);
							item.setYxName(yxname);
							item.setSumVal(MapUtils.getDoubleValue(it, "SUM_VAL"));
							saveData.add(item);
						}
					}
					
					log.warn("======== running : 一卡通消费异常 ： "+yxname+" 低消费的前  "+ countNum +" 名学生==============");
					String lowsql = "SELECT * FROM (SELECT S.NO_ SNO,S.NAME_ SNAME,SUM(T.PAY_MONEY) SUM_VAL,T.CARD_ID"
							+ " FROM T_CARD_PAY T"
							+ " INNER JOIN T_CARD C ON T.CARD_ID = C.NO_"
							+ " INNER JOIN T_STU S ON S.NO_ = C.PEOPLE_ID"
							+ " WHERE S.DEPT_ID = '"+yxid+"' AND SUBSTR(T.TIME_, 0, 10) = '"+nowDate+"'"
							+ " GROUP BY S.NO_, S.NAME_,T.CARD_ID"
							+ " HAVING SUM(T.PAY_MONEY) < "+ avgPay
							+ " ORDER BY SUM(T.PAY_MONEY) ASC) "
							+ " WHERE ROWNUM <= "+countNum ;
					List<Map<String, Object>> lowlist = baseDao.queryForList(lowsql);
					if (lowlist.size() > 0) {
						for (int j = 0; j < lowlist.size(); j++) {
							Map<String, Object> it = lowlist.get(j);
							TCardPayAbnormal item = new TCardPayAbnormal();
							item.setDatetime(nowDate);
							item.setCardNo(MapUtils.getString(it, "SUM_VAL"));
							item.setIshigh(false);
							item.setSno(MapUtils.getString(it, "SNO"));
							item.setSname(MapUtils.getString(it, "SNAME"));
							item.setYxId(yxid);
							item.setYxName(yxname);
							item.setSumVal(MapUtils.getDoubleValue(it, "SUM_VAL"));
							saveData.add(item);
						}
					}
				}
				if(saveData.size() > 0){
					log.warn("======== running : 一卡通消费异常 ：高低消费统计初始化开始插入结果，共"+saveData.size() +"条数据 ==============");
					hibernate.saveAllWithAutoCommit(saveData);
				}
				String info = "已经耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
				log.warn("======== stop :" + info+" ============== ");
			}
			String info = "一卡通消费异常 -高低消费统计初始化结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			log.warn("======== stop :" + info+" ============== ");
			result.setIsTrue(true);
			result.setMsg("高低消费异常统计初始化完成");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 一卡通消费异常 ：高低消费统计初始化出现错误，停止执行 ==============");
			e.printStackTrace();
		}
		return result;
	}
}