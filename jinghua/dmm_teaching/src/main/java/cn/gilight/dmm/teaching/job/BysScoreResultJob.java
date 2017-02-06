package cn.gilight.dmm.teaching.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.entity.TStuScoreAvg;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
@Service("bysScoreResultJob")
public class BysScoreResultJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService ;
	@Resource
	private HibernateDao hibernateDao;
	
	private Logger log = Logger.getLogger(this.getClass());
	@Transactional
	public JobResultBean doNowAvg(){
		String jobName = "当前学期所有学生平均成绩";
		Long beginTime = System.currentTimeMillis();
		List<Map<String, Object>> temp = getYearAndTerm();
		if(!temp.isEmpty()){
			temp = temp.subList(0, 1);
		}
		JobResultBean result = getList(temp, jobName,beginTime,true);
		return result;
	}
	@Transactional
	public JobResultBean doNowXnAvg(){
		String jobName = "当前学年所有学生平均成绩";
		Long beginTime = System.currentTimeMillis();
		List<Map<String, Object>> temp = getYearAndTerm();
		if(!temp.isEmpty()){
			temp = temp.subList(0, 1);
		}
		JobResultBean result = getList(temp, jobName,beginTime,false);
		return result;
	}
	@Transactional
	public JobResultBean doLsXnAvg(){
		String jobName = "历史学年所有学生平均成绩";
		Long beginTime = System.currentTimeMillis();
		List<Map<String, Object>> temp = getYearAndTerm();
		if(!temp.isEmpty()){
			temp = temp.subList(1, temp.size());
		}
		JobResultBean result = getList(temp, jobName,beginTime,false);
		return result;
	}
	@Transactional
	public JobResultBean doNowStuAvg(){
		String jobName = "在校学生平均成绩";
		Long beginTime = System.currentTimeMillis();
		List<Map<String, Object>> temp = getYearAndTerm();
		int year = EduUtils.getSchoolYear4();
		if(!temp.isEmpty()){
		  year = MapUtils.getIntValue(temp.get(0), "year");
		}
		String stuSql = businessDao.getStuSql(year, PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		JobResultBean result = getListNoXn(stuSql, jobName, beginTime);
		return result;
	}
	@Transactional
	public JobResultBean doLsStuAvg(){
		String jobName = "毕业学生平均成绩";
		Long beginTime = System.currentTimeMillis();
		List<Map<String, Object>> temp = getYearAndTerm();
		int year = EduUtils.getSchoolYear4();
		if(!temp.isEmpty()){
		  year = MapUtils.getIntValue(temp.get(0), "year");
		}
		String sql = businessDao.getStuSql(year, PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		String stuSql = "select a.* from t_stu a left join ("+sql+") b on a.no_ = b.no_"
				+ " where b.no_ is null "; 
		JobResultBean result = getListNoXn(stuSql, jobName, beginTime);
		return result;
	}
	@Transactional
	public JobResultBean doLsAvg(){
		String jobName = " 历史学期所有学生平均成绩";
		Long beginTime = System.currentTimeMillis();
		List<Map<String, Object>> temp = getYearAndTerm();
		if(temp.size()>1){
			temp = temp.subList(1, temp.size());
		}
		JobResultBean result = getList(temp, jobName,beginTime,true);
		return result;
	}
	private void begin(String info){
		log.warn("======== begin["+DateUtils.getNowDate2()+"]: "+info+" 初始化 ========");
	}
	private void info(String info){
		log.warn("======== info["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	private void end(String info){
		log.warn("======== end["+DateUtils.getNowDate2()+"]: "+info+" ========");
	}
	private void error(String info){
		log.warn("======== error["+DateUtils.getNowDate2()+"]: "+info+" 停止执行========");
	}
	private JobResultBean doResult(List<TStuScoreAvg> list,String jobName){
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
		try{
 		if(!list.isEmpty()){
		hibernateDao.saveAllAutoCommit(list);
		info("插入"+jobName+list.size()+" 条");
 		}else{
 		info("插入0条");
 		}
 		String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
		end(info);
		result.setIsTrue(true);
		result.setMsg(info);
		}catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			String info = "#"+jobName+"#数据保存出错,"+e.getStackTrace();
			error(info);
			result.setIsTrue(false);
			result.setMsg(info);
			e.printStackTrace();
		}
		return result;
	}
	private List<Map<String, Object>> getYearAndTerm(){
		List<Map<String, Object>> list = businessService.queryBzdmScoreXnXq();
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> map : list){
			Map<String, Object> temp = new HashMap<String, Object>();
			String id = MapUtils.getString(map, "id");
			String[] ary = id.split(",");
			temp.put("schoolyear", ary[0]);
			temp.put("term", ary[1]);
			temp.put("year", Integer.parseInt(ary[0].substring(0,4)));
			result.add(temp);	
 		}
		return result;
	}
	private JobResultBean getList(List<Map<String,Object>> temp,String jobName,Long beginTime,Boolean hasXq){
		JobResultBean result = new JobResultBean();
		for(Map<String, Object> temp1 : temp){
			String schoolYear = MapUtils.getString(temp1, "schoolyear"),
					termCode = MapUtils.getString(temp1, "term");
			String termStr = hasXq ? " '"+termCode+"' as term_code, ":" null as term_code,";
			String term = hasXq ? termCode:null;
			String scoreSql = businessDao.getStuScoreSql(null, schoolYear, term);
			String sql = "select t.stu_id,'"+schoolYear+"' as school_year, "+termStr+" "
					+ " case when sum(t.credit)= 0 then 0 "
					+ " else round(sum(t.score * t.credit)/sum(t.credit),1) end as weight_avg,"
					+ " round(avg(t.score),1) as score_avg from ("+scoreSql+") t group by t.stu_id";
			List<TStuScoreAvg> list = baseDao.queryForListBean(sql, TStuScoreAvg.class);
			if(list != null && !list.isEmpty()){
				String termStr1 = hasXq?" and term_code = '"+termCode+"'":" and term_code is null";
				String oldSql = " t_stu_score_avg  where school_year = '"+schoolYear+"' "+termStr1;
				int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
				if(oldCount > 0){
					baseDao.delete("delete from "+oldSql);
					info("已删除"+schoolYear+" 学年 "+term+"学期的数据");
				}
			}
			result = doResult(list, jobName);
			if(result.getIsTrue()==false){
				break;
			}
		}
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
		return result;
	}
	private JobResultBean getListNoXn(String stuSql,String jobName,Long beginTime){
		JobResultBean result = new JobResultBean();
			String scoreSql = businessDao.getStuScoreSql(stuSql, null, null);
			String sql = "select t.stu_id,null as school_year, null as term_code,"
					+ " case when sum(t.credit)= 0 then 0 "
					+ " else round(sum(t.score * t.credit)/sum(t.credit),1) end as weight_avg,"
					+ " round(avg(t.score),1) as score_avg from ("+scoreSql+") t group by t.stu_id";
			List<TStuScoreAvg> list = baseDao.queryForListBean(sql, TStuScoreAvg.class);
			if(list != null && !list.isEmpty()){
				String oldSql = " t_stu_score_avg t where exists ( select 1 from ("+stuSql+") a where t.stu_id = a.no_ and  "
						+ " t.school_year is null and t.term_code is null)";
				int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
				if(oldCount > 0){
					baseDao.delete("delete from "+oldSql);
					info("已删除成绩数据");
				}
			}
		result = doResult(list, jobName);
		if(result.getIsTrue()){
			String info = jobName+"结束执行,共耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			end(info);
			result.setMsg(info);
			DevUtils.p(info);
		}
		return result;
	}
}
