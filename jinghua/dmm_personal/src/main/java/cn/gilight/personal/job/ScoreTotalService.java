package cn.gilight.personal.job;

import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import cn.gilight.framework.uitl.common.DateUtils;

@Service("scoreTotalService")
public class ScoreTotalService {
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private BaseDao baseDao;
	@Resource
	private HibernateDao hibernate;
		
	/** 
	* @Description: 统计学生总成绩平均成绩及学分绩点
	* @return JobResultBean 任务调度返回结果
	*/
	@Transactional
	public JobResultBean scoreTotal(){
		log.warn("========begin : 统计学生总成绩平均成绩及学分绩点任务开始执行 ==============");
		Long beginTime = System.currentTimeMillis();
		//定义结果
		JobResultBean result = new JobResultBean();
		String date = DateUtils.getNowDate();
		try {
			String delsql = "DELETE FROM t_stu_score_total t";
			int nums = baseDao.delete(delsql);
			log.warn("======== running : 统计学生总成绩平均成绩及学分绩点清空存储表，共"+nums+"条数据 ==============");
			String sql = "insert into t_stu_score_total select tt.stu_id,tt.major_id,tt.end_year,tt.zcj,tt.pjcj,tt.gpa,'"+date+"' date_ from "
					+ " (select stu_id,major_id,end_year, sum(centesimal_score) as zcj, round(avg(centesimal_score), 2) pjcj,"
					+ " round(sum(hdxf*THEORY_CREDIT)/sum(THEORY_CREDIT) ,2) gpa from (select sc.*,case when sc.centesimal_score between 90 and 100 then 4"
					+ " when sc.centesimal_score between 85 and 89 then 3.7 "
					+ " when sc.centesimal_score between 82 and 84 then 3.3 when sc.centesimal_score between 78 and 81 then 3.0 "
					+ " when sc.centesimal_score between 75 and 77 then 2.7 when sc.centesimal_score between 71 and 74 then 2.3 "
					+ " when sc.centesimal_score between 66 and 70 then 2.0 when sc.centesimal_score between 62 and 65 then 1.7 "
					+ " when sc.centesimal_score between 60 and 61 then 1.3  when sc.centesimal_score between 0 and 59 then 0 "
					+ " end as hdxf,cou.THEORY_CREDIT,stu.major_id,(stu.enroll_grade + stu.length_schooling) end_year from ("
					+ " select t.stu_id,t.school_year,t.term_code,t.coure_code,case when t.hierarchical_score_code is not null "
					+ " then csh.centesimal_score else t.centesimal_score end centesimal_score"
					+ " from  t_stu_score t  left join t_code_score_hierarchy csh on csh.code_ = t.hierarchical_score_code"
					+ " ) sc left join t_stu stu on stu.no_ = sc.stu_id left join t_course cou on sc.coure_code = cou.code_ "
					+ " where sc.centesimal_score is not null) group by stu_id,major_id,end_year order by end_year,major_id,gpa)tt"
					+ " where end_year>=to_number(to_char(sysdate,'yyyy')) and end_year is not null";
			baseDao.update(sql);
			result.setIsTrue(true);
			String info = "统计学生总成绩平均成绩及学分绩点结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			result.setMsg(info);
			log.warn("======== stop :" + info+" ============== ");
		} catch (Exception e) {
			e.printStackTrace();
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 统计学生总成绩平均成绩及学分绩点出现错误，停止执行 ==============");
			e.printStackTrace();
		} 
		return result;
	}
		
}