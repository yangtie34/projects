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
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.PmsUtils;
import cn.gilight.dmm.teaching.entity.TStuScoreGpa;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.base.dao.HibernateDao;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;

/**
 * 学生平均绩点
 * 
 * @author xuebl
 * @date 2016年6月22日 下午4:59:38
 */
@Service("stuGpaJob")
public class StuGpaJob {


	@Resource
	private BaseDao baseDao;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private HibernateDao hibernateDao;
	@Resource
	private BusinessService businessService ;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	
	/**
	 * 学生学年学期平均绩点
	 */
	@Transactional
	public JobResultBean doNowGpa(){
		String jobName = "t_stu_score_gpa当前平均绩点";
		List<Map<String, Object>> temp = getYearAndTerm();
		int schoolYear = MapUtils.getIntValue(temp.get(0), "year");
		List<TStuScoreGpa> all = new ArrayList<>();
		String edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		List<AdvancedParam> advancedParamList = new ArrayList<AdvancedParam>();
		advancedParamList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		String stuSql = businessDao.getStuSql(schoolYear, PmsUtils.getPmsAll(), advancedParamList);
		List<TStuScoreGpa> list1 = queryNowGpaList(stuSql,true);
		List<TStuScoreGpa> list2 = queryNowGpaList(stuSql,false);
		List<TStuScoreGpa> list3 = getScoreGpaList(stuSql, null, null);
		all.addAll(list1);
		all.addAll(list2);
		all.addAll(list3);
		JobResultBean result = doStuGpa(all,jobName);
		return result;
	}
	@Transactional
	public JobResultBean doBeforeGpa(){
		String jobName = "t_stu_score_gpa历史平均绩点";
		List<Map<String, Object>> temp = getYearAndTerm();
		int schoolYear = MapUtils.getIntValue(temp.get(0), "year");
		List<TStuScoreGpa> all = new ArrayList<>();
		String sql = businessDao.getStuSql(schoolYear, PmsUtils.getPmsAll(), new ArrayList<AdvancedParam>());
		String edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		String stuSql = "select a.* from t_stu a left join ("+sql+") b on a.no_ = b.no_"
				+ " where b.no_ is null and a.edu_id in ("+edu+")"; 
		List<TStuScoreGpa> list1 = queryGpaList(stuSql,true);
		List<TStuScoreGpa> list2 = queryGpaList(stuSql,false);
		List<TStuScoreGpa> list3 = getScoreGpaList(stuSql, null, null);
		all.addAll(list1);
		all.addAll(list2);
		all.addAll(list3);
		JobResultBean result = doStuGpa(all,jobName);
		return result;
	}
	private String gpaId = Constant.SCORE_GPA_BASE_CODE;

	/**
	 * 获取GPA的case when sql
	 * @return String
	 * <br> case when ...... end
	 */
	public String getGpaCaseWhenSql(){
		return businessDao.getStuGpaCaseWhenSql();
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
	private List<TStuScoreGpa> queryGpaList(String stuSql,Boolean hasTerm){
		String xq ="select t.code_ as id,t.name_ as mc from t_code t where code_type ='TERM_CODE' order by t.code_ desc";
		int year = businessDao.queryMinSchoolYear(Constant.TABLE_T_STU_SCORE, "SCHOOL_YEAR");
		String day = DateUtils.getNowDate();
		List<TStuScoreGpa> list1 = new ArrayList<>();
		int nowyear = EduUtils.getSchoolYear4();
		List<Map<String,Object>> xqlist = baseDao.queryListInLowerKey(xq);
		String[] date = EduUtils.getSchoolYearTerm(day);
		String nowxq = date[1];
		for(int j=nowyear;j>year-1;j--){
		   if(hasTerm){
			for(int i=0;i<xqlist.size();i++){
			String xqid = MapUtils.getString(xqlist.get(i), "id");
			if(!xqid.equals(nowxq)||j!=nowyear){
			String schoolyear=String.valueOf(j)+"-"+String.valueOf(j+1);
			 List<TStuScoreGpa> ScoreGpaList = getScoreGpaList(stuSql,schoolyear,xqid);
			 list1.addAll(ScoreGpaList);
			}
			}
		   }else{
			   if(j!=nowyear){
				   String schoolyear=String.valueOf(j)+"-"+String.valueOf(j+1);
				   List<TStuScoreGpa> ScoreGpaList = getScoreGpaList(stuSql,schoolyear,null);
				   list1.addAll(ScoreGpaList);
			   }
		   }
		}
		return list1;
	}
	private List<TStuScoreGpa> queryNowGpaList(String stuSql,Boolean hasTerm){
		  List<TStuScoreGpa> list1 = new ArrayList<>();
		  List<Map<String, Object>> temp = getYearAndTerm();
		  String  schoolYear = MapUtils.getString(temp.get(0), "schoolyear");
		  String  term = MapUtils.getString(temp.get(0), "term");
		  if(hasTerm){
			  list1 =  getScoreGpaList(stuSql,schoolYear,term);
		  }else{
			  list1 =  getScoreGpaList(stuSql,schoolYear,null); 
		  }
		return list1;
	}
	private JobResultBean doStuGpa(List<TStuScoreGpa> list,String jobName){
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
	private List<TStuScoreGpa> getScoreGpaList(String stuSql,String schoolYear,String termCode){
		String scoreSql = businessDao.getStuScoreSql(stuSql, schoolYear, termCode);
		String gpaSql1 = "select case when score.hierarchical_score_code is not null then hier.centesimal_score else score.centesimal_score end score,stu.no_ as stu_id ,course.id as course,course.theory_credit credit  from ("+stuSql+") stu inner join t_stu_score score"
				+ " on stu.no_ = score.stu_id inner join t_course course on score.coure_code = course.id left join "
				+ " t_code_score_hierarchy hier on  score.hierarchical_score_code = hier.code_ "
				+ " where course.theory_credit is not null "
				+(schoolYear == null?"":" and score.school_year = '"+schoolYear+"'")
				+(termCode == null?"":"and score.term_code = '"+termCode+"'")
				+" group by score.hierarchical_score_code,score.centesimal_score,hier.centesimal_score,hier.code_,stu.no_,course.id,course.theory_credit ";
		String gpaCaseWhenSql = getGpaCaseWhenSql();
		schoolYear = schoolYear==null?schoolYear:"'"+schoolYear+"'";
		termCode = termCode==null?termCode:"'"+termCode+"'";
		String gpaSql = " select "+gpaCaseWhenSql+" as gpa, t.stu_id, t.coure_code, t.credit from (" +scoreSql+ ") t ";
		String str = "select "+gpaCaseWhenSql+" as gpa, t.stu_id, t.course as coure_code, t.credit from (" +scoreSql+ ") a right join "
				+ " ("+gpaSql1+") t on a.coure_code = t.course where a.coure_code is null "; 
		String sql = "select case when sum(t.credit)=0 then 0 else round(sum(t.gpa * t.credit)/sum(t.credit),1) end gpa, t.stu_id, "+schoolYear+" as school_year, "+termCode+" as term_code, '"+gpaId+"' as gpa_code from"
				+ " (select * from ("+str+" union "+gpaSql+")) t"
				+ " group by t.stu_id";
		List<TStuScoreGpa> scoreGpaList = baseDao.queryForListBean(sql, TStuScoreGpa.class);
		schoolYear = schoolYear ==null?"  t.school_year is null":"t.school_year="+schoolYear+" ";
		termCode = termCode ==null?" and t.term_code is null" :" and t.term_code= "+termCode+" "; 
		stuSql = stuSql ==null?" "+schoolYear+termCode+" and t.gpa_code='"+gpaId+"'":" exists ( select 1 from ("+stuSql+") a where t.stu_id = a.no_ and  "+schoolYear+termCode+" and t.gpa_code='"+gpaId+"')";
		if(!scoreGpaList.isEmpty()){
			String oldSql = " T_STU_SCORE_GPA t where "+stuSql;
			int oldCount = baseDao.queryForInt("select count(0) from "+oldSql);
			if(oldCount > 0){
				baseDao.delete("delete from "+oldSql);
				info("已删除"+schoolYear+"学年"+termCode+"学期平均绩点");
			}
		}
		return scoreGpaList;
	}
}
