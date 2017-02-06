package cn.gilight.dmm.business.job;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jhnu.syspermiss.permiss.entity.JobResultBean;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.Globals;

/**
 * 业务缓存job
 * 
 * @author xuebl
 * @date 2016年8月5日 上午11:26:49
 */
@Service("businessJob")
public class BusinessJob {
	
	@Resource
	private BusinessDao businessDao;
	
	/** 成绩表学年学期编码 */
	private static List<Map<String, Object>> SCORE_XNXQ_LIST = new ArrayList<>();
	/** 成绩表学年编码 */
	private static List<Map<String, Object>> SCORE_XN_LIST = new ArrayList<>();
	/** 挂科表学年学期编码 */
	private static List<Map<String, Object>> SCORE_GK_XNXQ_LIST = new ArrayList<>();
	/** 挂科表学年编码 */
	private static List<Map<String, Object>> SCORE_GK_XN_LIST = new ArrayList<>();
	
	/**
	 * 获取成绩表学年学期编码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getBzdmScoreXnXq(){
		if(SCORE_XNXQ_LIST==null || SCORE_XNXQ_LIST.isEmpty()){
			initStuScoreXnXq();
		}
		List<Map<String, Object>> list = new ArrayList<>();
		for(Map<String, Object> map : SCORE_XNXQ_LIST){
			Map<String, Object> m = new HashMap<>();
			m.put("id", MapUtils.getString(map, "id"));
			m.put("mc", MapUtils.getString(map, "mc"));
			list.add(m);
		}
		return list;
	}
	/**
	 * 获取挂科表学年学期编码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getBzdmGkXnXq(){
		if(SCORE_GK_XNXQ_LIST==null || SCORE_GK_XNXQ_LIST.isEmpty()){
			initStuGkXnXq();
		}
		List<Map<String, Object>> list = new ArrayList<>();
		for(Map<String, Object> map : SCORE_GK_XNXQ_LIST){
			Map<String, Object> m = new HashMap<>();
			m.put("id", MapUtils.getString(map, "id"));
			m.put("mc", MapUtils.getString(map, "mc"));
			list.add(m);
		}
		return list;
	}
	
	/**
	 * 获取成绩表学年编码
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String, Object>> getBzdmScoreXn(){
		if(SCORE_XN_LIST==null || SCORE_XN_LIST.isEmpty()){
			initStuScoreXn();
		}
		List<Map<String, Object>> list = new ArrayList<>();
		for(Map<String, Object> map : SCORE_XN_LIST){
			Map<String, Object> m = new HashMap<>();
			m.put("id", MapUtils.getString(map, "id"));
			m.put("mc", MapUtils.getString(map, "mc"));
			list.add(m);
		}
		return list;
	}
	/**
	 * 定时缓存 成绩表学年学期编码
	 * 目的是显示最近有成绩的学年 学期
	 */
	public JobResultBean initStuScoreXnXq(){
		String jobName = "初始化成绩表学年学期编码";
		SCORE_XNXQ_LIST = getBzdmXnXq(Constant.TABLE_T_STU_SCORE, "SCHOOL_YEAR", "TERM_CODE");
		JobResultBean result = new JobResultBean();
		result.setIsTrue(true);
		result.setMsg(jobName+" 成功");
		return result;
	}
	/**
	 * 定时缓存 挂科表学年学期编码
	 * 目的是显示最近有成绩的学年 学期
	 */
	public JobResultBean initStuGkXnXq(){
		String jobName = "初始化挂科表学年学期编码";
		SCORE_GK_XNXQ_LIST = getBzdmXnXq(Constant.TABLE_T_STU_Gk, "SCHOOL_YEAR", "TERM_CODE");
		JobResultBean result = new JobResultBean();
		result.setIsTrue(true);
		result.setMsg(jobName+" 成功");
		return result;
	}
	/**
	 * 定时缓存 成绩表学年编码
	 * 目的是显示最近有成绩的学年
	 */
	public JobResultBean initStuScoreXn(){
		String jobName = "初始化成绩表学年编码";
		SCORE_XN_LIST = getBzdmXnXq(Constant.TABLE_T_STU_SCORE, "SCHOOL_YEAR");
		JobResultBean result = new JobResultBean();
		result.setIsTrue(true);
		result.setMsg(jobName+" 成功");
		return result;
	}
	private List<Map<String, Object>> getBzdmXnXq(String tableName, String columnSchoolYear) {
		Integer  minYear = businessDao.queryMinSchoolYear(tableName, columnSchoolYear),
				 maxYear = businessDao.queryMaxSchoolYear(tableName, columnSchoolYear);
		String   yearSuf = "学年", middle = " ";
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(minYear != null && maxYear != null){
			for(int i = maxYear;i>minYear-1;i--){
				String year =  String.valueOf(i)+"-"+String.valueOf(i+1);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id",year);
				map.put("mc", year+middle+yearSuf);
				result.add(map);
			}
		}
		return result;
	}
	private List<Map<String, Object>> getBzdmXnXq(String tableName, String columnSchoolYear, String columnTermCode) {
		List<Map<String, Object>> list = new ArrayList<>();
		
		// 最小学年 最小学期；最大学年 最大学期
		String[] minAry = businessDao.queryMinSchoolYearTermCode(tableName, columnSchoolYear, columnTermCode),
				 maxAry = businessDao.queryMaxSchoolYearTermCode(tableName, columnSchoolYear, columnTermCode);
		Integer minYear = minAry[0]!=null ? Integer.valueOf(minAry[0]) : null,
				maxYear = maxAry[0]!=null ? Integer.valueOf(maxAry[0]) : null;
		String minTerm  = minAry[1],
			   maxTerm  = maxAry[1],
			   yearSuf = "学年", middle = " ",
			   term1 = Globals.TERM_FIRST, term2 = Globals.TERM_SECOND,
			   termName1 = "第一学期", termName2 = "第二学期",
			   name1 = middle+termName1, name2 = middle+termName2;
		if(minYear != null && maxYear != null){
			if(minTerm != null && maxTerm != null){
				Integer year = minYear;
				String yearId = null;
				for( ; year<=maxYear; year++){
					Map<String, Object> m2 = new HashMap<>(), m1 = new HashMap<>();
					yearId = year+"-"+(year+1);
					// 最小年
					if(year.equals(minYear)){
						if(minTerm.equals(term1)){
							m1.put("id", yearId+","+term1);
							m1.put("mc", yearId+yearSuf+name1);
							list.add(m1);
							m2.put("id", yearId+","+term2);
							m2.put("mc", yearId+yearSuf+name2);
							list.add(m2);
						}else if(minTerm.equals(term2)){
							m2.put("id", yearId+","+term2);
							m2.put("mc", yearId+yearSuf+name2);
							list.add(m2);
						}
					}else if(year.equals(maxYear)){
						if(maxTerm.equals(term2)){
							m1.put("id", yearId+","+term1);
							m1.put("mc", yearId+yearSuf+name1);
							list.add(m1);
							m2.put("id", yearId+","+term2);
							m2.put("mc", yearId+yearSuf+name2);
							list.add(m2);
						}else if(maxTerm.equals(term1)){
							m1.put("id", yearId+","+term1);
							m1.put("mc", yearId+yearSuf+name1);
							list.add(m1);
						}
					}else{
						m1.put("id", yearId+","+term1);
						m1.put("mc", yearId+yearSuf+name1);
						list.add(m1);
						m2.put("id", yearId+","+term2);
						m2.put("mc", yearId+yearSuf+name2);
						list.add(m2);
					}
				}
				Collections.reverse(list);
			}
		}
		return list;
	}
	
}
