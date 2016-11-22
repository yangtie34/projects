package cn.gilight.personal.job;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;

@Service("teachingInitService")
public class TeachingClassInitService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
		
	/** 
	* @Description: 教学班初始化
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean initTeaching(){
		log.warn("========begin : 教学班初始化任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		String delsql = "DELETE FROM t_class_teaching ";
		int nums = baseDao.delete(delsql);
		log.warn("======== running : 教学班初始化清空存储表，共"+nums+"条数据 ==============");
		try {
			String sql = " insert into t_class_teaching  select distinct t.no_ id, t.no_ code_,t.name_, ca.course_id    from t_classes t "
					+ " inner join t_course_arrangement ca  on ca.teachingclass_id = t.no_";
			baseDao.update(sql);
			result.setIsTrue(true);
			String info = "教学班初始化结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 教学班初始化出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
		
}