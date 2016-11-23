package cn.gilight.personal.job;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.DateUtils;

@Service("cardTotalService")
public class CardTotalService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
		
	/** 
	* @Description: 统计学生一卡通刷卡次数及总额
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean cardTotal(){
		log.warn("========begin : 统计学生一卡通刷卡次数及总额任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		
		String date = DateUtils.getNowDate();
		try {
			String delsql = "DELETE FROM t_card_total t";
			int nums = baseDao.delete(delsql);
			log.warn("======== running : 统计学生一卡通刷卡次数及总额清空存储表，共"+nums+"条数据 ==============");
			String sql = "insert into t_card_total select tts.no_ stu_id,sum(tts.nums) nums,sum(tts.sums) sums,tts.end_time,count(*) day_counts,"
						+ " tts.school_year,tts.term_code from ("
						+ " select tt.no_,case when substr(day_,6,2)>='07' then substr(day_,1,4)||'-'||(to_number(substr(day_,1,4))+1) "
						+ " else (to_number(substr(day_,1,4))-1)||'-'||substr(day_,1,4) end school_year,case when substr(day_,6,2)>='07'"
						+ " then '01' else '02' end term_code,tt.nums,tt.sums,tt.end_time from ("
						+ " select stu.no_,substr(t.time_,0,10) day_,count(*) nums ,sum(t.pay_money) sums,'"+date+"' end_time from t_card_pay t "
						+ " left join t_card ca on ca.no_ = t.card_id inner join t_stu stu on stu.no_ = ca.people_id"
						+ " where t.time_ < '"+date+"' group by stu.no_,substr(t.time_,0,10)) tt ) tts group by tts.no_,tts.school_year,tts.term_code,tts.end_time";
			baseDao.update(sql);
			result.setIsTrue(true);
			String info = "统计学生一卡通刷卡次数及总额结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 统计学生一卡通刷卡次数及总额出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
		
}