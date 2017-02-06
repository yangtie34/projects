package cn.gilight.dmm.teaching.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.teaching.entity.TCourseArrangementPlan;
import cn.gilight.dmm.teaching.entity.TStuGraduateResultSubject;
import cn.gilight.dmm.teaching.entity.TStuScoreResultSubject;
import cn.gilight.dmm.teaching.service.BysDegreeService;
import cn.gilight.dmm.teaching.service.BysQxService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

@Service("graduateResultSubJob")
public class GraduateResultSubJob {
	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService ;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BysDegreeService bysDegreeService;
	@Resource
	private BysQxService bysQxService;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Transactional
	public JobResultBean doNow(){
		String jobName = "近三年各学科毕业分析";
		Integer max = businessDao.queryMaxSchoolYear("t_stu_graduate", "school_year");
		String schoolYear = max == null ? EduUtils.getLastSchoolYear9():String.valueOf(max)+"-"+String.valueOf(max+1);
		List<String> yearList = bysQxService.getListByYear(schoolYear, 10, true);
		if(yearList.size()>3){
			yearList= yearList.subList(0, 3);
		}
		List<TStuGraduateResultSubject> all = new ArrayList<TStuGraduateResultSubject>();
		for(String year : yearList){
		     List<TStuGraduateResultSubject> list = getList(year); 
		     all.addAll(list);
		}
		JobResultBean result = doResult(all,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doLs(){
		String jobName = "三年前各学科毕业分析";
		Integer max = businessDao.queryMaxSchoolYear("t_stu_graduate", "school_year");
		String schoolYear = max == null ? EduUtils.getLastSchoolYear9():String.valueOf(max)+"-"+String.valueOf(max+1);
		List<String> yearList = bysQxService.getListByYear(schoolYear, 20, true);
		if(yearList.size()>3){
			yearList= yearList.subList(3, yearList.size());
		}else{
			yearList = new ArrayList<String>();
		}
		List<TStuGraduateResultSubject> all = new ArrayList<TStuGraduateResultSubject>();
		for(String year : yearList){
		     List<TStuGraduateResultSubject> list = getList(year); 
		     all.addAll(list);
		}
		JobResultBean result = doResult(all,jobName);
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
	private JobResultBean doResult(List<TStuGraduateResultSubject> list,String jobName){
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
		try{
 		if(!list.isEmpty()){
		hibernateDao.saveAll(list);
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
	private List<TStuGraduateResultSubject> getList(String schoolYear){
		List<TStuGraduateResultSubject> result = new ArrayList<TStuGraduateResultSubject>();
		List<Map<String,Object>> list = bysDegreeService.getListBySubjectList(businessService.queryBzdmSubjectDegree(), schoolYear, new ArrayList<AdvancedParam>());
	    if(list != null && !list.isEmpty()){
	    	baseDao.delete("Delete from t_stu_graduate_result_subject t where t.school_year = '"+schoolYear+"'");
	    }
		for(Map<String,Object> map :list){
			TStuGraduateResultSubject	scoreMap = new TStuGraduateResultSubject();
			scoreMap.setSubject_id(MapUtils.getString(map, "id"));
			scoreMap.setSchool_year(schoolYear);
			scoreMap.setSum_count(MapUtils.getIntValue(map, "all"));
			scoreMap.setRel_count(MapUtils.getIntValue(map,"by"));
			scoreMap.setGraduation_scale(MapUtils.getDoubleValue(map, "bylv"));
			scoreMap.setLast_graduation_scale(MapUtils.getDouble(map, "bybh"));
			scoreMap.setDegree_count(MapUtils.getIntValue(map, "sy"));
			scoreMap.setDegree_scale(MapUtils.getDoubleValue(map, "sylv"));
			scoreMap.setLast_degree_scale(MapUtils.getDouble(map, "sybh"));
			result.add(scoreMap);
		}
	    return result;
	}
}
