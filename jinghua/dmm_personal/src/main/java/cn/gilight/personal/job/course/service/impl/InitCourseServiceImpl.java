package cn.gilight.personal.job.course.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.personal.job.course.dao.CourseDao;
import cn.gilight.personal.job.course.service.CourseService;


@Service("initCourseService")
public class InitCourseServiceImpl implements CourseService{

	@Autowired
	private CourseDao Coursedao;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * 初始化weeks
	 */
	@Override
	@Transactional
	public JobResultBean initCourseWeekJob() {
		log.warn("========begin : 初始化课程周次任务开始执行 ==============");
		//定义结果
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
		List<Map<String,Object>> xnxq = Coursedao.getSchoolYear();
		int counts = 0;
		try{
			for(Map<String,Object> m : xnxq){
				int count = 0;
				List<Map<String,Object>> list=Coursedao.getCourseWeek(MapUtils.getString(m, "school_year"),MapUtils.getString(m, "term_code"));
				List<Map<String,Object>> zcList=new ArrayList<Map<String,Object>>();
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> map=new HashMap<String, Object>();
					String zcs= getWeek(MapUtils.getString(list.get(i), "WEEK_START_END"),MapUtils.getString(list.get(i), "LECTURE_TYPE"));
					map.put("WEEKS", zcs);
					map.put("COURSE_ARRANGEMENT_ID", MapUtils.getString(list.get(i), "ID"));
					map.put("FIRST_PERIOD", MapUtils.getString(list.get(i), "FIRST_PERIOD"));
					map.put("END_PERIOD", MapUtils.getString(list.get(i), "END_PERIOD"));
					zcList.add(map);
				}
				count = count + zcList.size();
				counts += count;
				Coursedao.updateCourseWeek(zcList);
				log.warn("===========初始化："+MapUtils.getString(m, "school_year")+"学年，"+MapUtils.getString(m, "term_code")+"学期完毕，共"+count+"条数据=======");
			}
			String info = "=========point End : 初始化课程周次执行完毕，共更新  " + counts +"  条数据,耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒 ";
			log.warn(info);
			result.setMsg(info);
			result.setIsTrue(true);
		}catch(Exception e){
			result.setIsTrue(false);
			result.setMsg("数据保存出错:"+e.getStackTrace());
			log.error("======== error : 初始化课程周次任务出现错误，停止执行 ==============");
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 更新当前学期weeks
	 */
	@Override
	@Transactional
	public JobResultBean updateCurrYearWeekJob(){
		log.warn("========begin : 本学期课程周次任务开始执行 ==============");
		JobResultBean result = new JobResultBean();
		Long beginTime = System.currentTimeMillis();
		String[] terms = EduUtils.getSchoolYearTerm(new Date());
		List<Map<String,Object>> list=Coursedao.getCourseWeek(terms[0],terms[1]);
		List<Map<String,Object>> zcList=new ArrayList<Map<String,Object>>();
		try{
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> map=new HashMap<String, Object>();
				String zcs= getWeek(MapUtils.getString(list.get(i), "WEEK_START_END"),MapUtils.getString(list.get(i), "LECTURE_TYPE"));
				map.put("WEEKS", zcs);
				map.put("COURSE_ARRANGEMENT_ID", MapUtils.getString(list.get(i), "ID"));
				map.put("FIRST_PERIOD", MapUtils.getString(list.get(i), "FIRST_PERIOD"));
				map.put("END_PERIOD", MapUtils.getString(list.get(i), "END_PERIOD"));
				zcList.add(map);
			}
			Coursedao.updateCourseWeek(zcList);
			String info = "=========point End : 本学期课程周次执行完毕，共更新  " + zcList.size()+"  条数据,耗时 ： " +new Double(System.currentTimeMillis() - beginTime) /1000+ " 秒";
			log.warn(info);
			result.setIsTrue(true);
			result.setMsg(info);
		}catch(Exception e){
			result.setIsTrue(false);
			result.setMsg("本学期课程周次任务出现错误，停止执行");
			log.error("======== error : 本学期课程周次任务出现错误，停止执行 ==============");
			e.printStackTrace();
		}
		return result;
	}

	private String getWeek(String week_start_end,String lecture_type){
		String weeks[] = week_start_end.split(",");
		if(week_start_end.contains("，")){
			weeks = week_start_end.split("，");
		}
		String zcs=",";
		for(String week : weeks){
			if(StringUtils.hasText(week)){
				if(week.contains("-")){
					String[] a = week.split("-");
					if(a[0].contains(".")){
						a[0] = a[0].substring(0, a[0].indexOf("."));
					}
					if(a[1].contains(".")){
						a[1] = a[1].substring(0, a[1].indexOf("."));
					}
					
					int start = Integer.parseInt(a[0]);
					int end =Integer.parseInt( a[1]);
					for(int i=start;i<=end;i++){
						if(StringUtils.hasText(lecture_type) && "单".equals(lecture_type)){
							if(!MathUtils.isEven(i)){
								zcs+=i+",";
							}
						}else if(StringUtils.hasText(lecture_type) && "双".equals(lecture_type)){
							if(MathUtils.isEven(i)){
								zcs+=i+",";
							}
						}else{
							zcs+=i+",";
						}
					}
				}else{
					if(StringUtils.hasText(lecture_type) && "单".equals(lecture_type)){
						if(!MathUtils.isEven(Integer.parseInt(week))){
							zcs+=week+",";
						}
					}else if(StringUtils.hasText(lecture_type) && "双".equals(lecture_type)){
						if(MathUtils.isEven(Integer.parseInt(week))){
							zcs+=week+",";
						}
					}else{
						zcs+=week+",";
					}
				}
			}
		}
		return zcs;
	}
	
	
}
