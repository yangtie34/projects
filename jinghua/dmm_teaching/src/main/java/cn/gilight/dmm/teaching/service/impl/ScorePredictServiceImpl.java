package cn.gilight.dmm.teaching.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.ScorePredictDao;
import cn.gilight.dmm.teaching.service.ScorePredictService;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.entity.ExcelParam;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ExcelUtils;
import cn.gilight.framework.uitl.common.MailUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.common.UserUtil;
import cn.gilight.framework.uitl.common.ZipUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;
/**
 * 成绩预测（辅导员）
 * @author lijun
 *
 */
@Service("scorePredictService")
public class ScorePredictServiceImpl implements ScorePredictService {
	@Resource
	private ScorePredictDao scorePredictDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 在籍学生 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"scorePredict";
	/**
	 * 获取在籍学生数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(){
		return getDeptDataList(null);
	}
	/**
	 * 获取在籍学生数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String id){
		return businessService.getDeptDataList(ShiroTag, id);
	}
	/**
	 * 得到学年 本专科
	 */
	@Override
	public Map<String, Object> getYearAndTerm() {
		List<Map<String, Object>> yearList = businessService.queryBzdmSchoolYear(Constant.TABLE_T_STU_SC_PRE, "SCHOOL_YEAR"),
				  xnxqList = new ArrayList<>(),
				  eduList  = businessService.queryBzdmStuEducationList();
			String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];
			String sql="SELECT  SUBSTR(TFP.DATE_,0,10) date_now,to_char((to_date(SUBSTR(TFP.DATE_,0,10),'yyyy-mm-dd')-1),'yyyy-mm-dd') date_pro  FROM T_STU_SCORE_PREDICT_BEH TFP WHERE ROWNUM=1";
			List<Map<String,Object>> time_= baseDao.getJdbcTemplate().queryForList(sql);
			Map<String, Object> m = null;
			for(int i=0,len=yearList.size(); i<len; i++){
			m = yearList.get(i);
			Map<String, Object> m2 = new HashMap<>(), m1 = new HashMap<>();
			if(i!=0 || (i==0 && termCode.equals(Globals.TERM_SECOND))){
			m2.put("id", MapUtils.getString(m, "id")+","+Globals.TERM_SECOND);
			m2.put("mc", MapUtils.getString(m, "mc")+" 第二学期");
			xnxqList.add(m2);
			}
			m1.put("id", MapUtils.getString(m, "id")+","+Globals.TERM_FIRST);
			m1.put("mc", MapUtils.getString(m, "mc")+" 第一学期");
			xnxqList.add(m1);
			}
			int j=0; 
			for( Map<String,Object> map : xnxqList){
				 Map<String,Object>  map1 = new HashMap<String, Object>();
				 String xx = MapUtils.getString(map, "id").split(",")[0];
				 if(j == 0){
					 map.put("order", 0);
				 }else{
					 map1 = xnxqList.get(j-1);
					 String xx1 = MapUtils.getString(map1, "id").split(",")[0];
					 if(xx1.equals(xx)){
						 map.put("order", j);
					 }else{
						 map.put("order", j+1);
					 }
				 }
				 j++;
			 }
			Map<String, Object> map = DevUtils.MAP();
			map.put("xnxq", xnxqList);//businessService.queryBzdmGkXnXq());//
			map.put("edu", businessService.queryBzdmStuEducationList(getDeptDataList()));//eduList);
			map.put("date", time_);
			sql="SELECT TC.CODE_ ,TC.NAME_ FROM T_CODE TC WHERE TC.CODE_TYPE='COURSE_NATURE_CODE' ORDER BY TC.CODE_ ASC";
			List<Map<String,Object>> listType=baseDao.getJdbcTemplate().queryForList(sql);
			List<Map<String,Object>> list1=new ArrayList<>();
			for(int i=0;i<listType.size();i++){
				Map<String, Object> m1 = new HashMap<>();
				if(i==0){
				m1.put("id", "all");
				m1.put("mc", "选择课程属性");
				list1.add(m1);
				Map<String, Object> m2 = new HashMap<>();
				m2.put("id", listType.get(i).get("CODE_"));
				m2.put("mc", listType.get(i).get("NAME_"));
				list1.add(m2);
				}else{
					m1.put("id", listType.get(i).get("CODE_"));
					m1.put("mc", listType.get(i).get("NAME_"));
					list1.add(m1);
				}
			}
			Map<String, Object> m3 = new HashMap<>();
			List<Map<String,Object>> list2=new ArrayList<>();
			m3.put("id","all" );
			m3.put("mc", "全部课程");
			list2.add(m3);
			map.put("coursesx", list1);
			map.put("course",list2 );//初始化课程
			return map;
	}
	/**
	 * 得到课程
	 */
	@Override
	public Map<String, Object> queryCourseByType(String id, String schoolYear, String termCode) {
		List<Map<String,Object>> list1=new ArrayList<>();
		if(schoolYear.contains(",")){
			schoolYear=schoolYear.split(",")[0];
		}
		List<Map<String,Object>> courseList=scorePredictDao.queryCourseByType(id, schoolYear, termCode);
		for(int i=0;i<courseList.size();i++){
			Map<String, Object> m1 = new HashMap<>();
			if(i==0){
			m1.put("id", "all");
			m1.put("mc", "选择课程");
			list1.add(m1);
			Map<String, Object> m2 = new HashMap<>();
			m2.put("id", courseList.get(i).get("CODE_"));
			m2.put("mc", courseList.get(i).get("NAME_"));
			list1.add(m2);
			}else{
				m1.put("id", courseList.get(i).get("CODE_"));
				m1.put("mc", courseList.get(i).get("NAME_"));
				list1.add(m1);
			}
		}
		Map<String, Object> map = DevUtils.MAP();
		map.put("course", list1);
		return map;
	}
	/**
	 * 得到辅导员所带班级数，总人数，以及所带专业
	 */
	@Override
	public List<Map<String, Object>> queryGkInfo(List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String majorType) {
		return queryGkInfo(getDeptDataList(),advancedList,schoolYear,termCode,edu,majorType);
	}
	@Override
	public List<Map<String, Object>> queryGkInfo(List<String> deptList,List<AdvancedParam> advancedList, String schoolYear, String termCode, String edu,String majorType) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		String userId=UserUtil.getCasLoginName();
		return scorePredictDao.queryGkInfo(stuSql,deptList, advancedList, year, termCode, edu,userId,majorType);
	}
	/**
	 * 预测成绩概况
	 */
	@Override
	public List<Map<String, Object>> queryScoreInfo(List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String courseType,String courseid,String majorType) {
		return queryScoreInfo(getDeptDataList(),advancedList, schoolYear,  termCode,  edu, courseType, courseid,majorType);
	}
	@Override
	public List<Map<String, Object>> queryScoreInfo(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String courseType,String courseid,String majorType) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return scorePredictDao.queryScoreInfo(deptList,stuAdvancedList, schoolYear,  termCode,  edu, courseType, courseid,majorType);
	}
	/**
	 * 成绩分布(按课程)
	 */
	@Override
	public List<Map<String, Object>> queryScorefb(List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType) {
		return queryScorefb(getDeptDataList(),advancedList, schoolYear,  termCode,  edu, majorType);
	}
	@Override
	public List<Map<String, Object>> queryScorefb(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return scorePredictDao.queryScorefb(deptList,stuAdvancedList, schoolYear,  termCode,  edu,majorType);
	}
	/**
	 * 成绩分布(按课程性质)
	 */
	@Override
	public List<Map<String, Object>> queryScorefbByNature(List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType) {
		return queryScorefbByNature(getDeptDataList(),advancedList, schoolYear,  termCode,  edu, majorType);
	}
	@Override
	public List<Map<String, Object>> queryScorefbByNature(List<String> deptList,List<AdvancedParam> advancedList,String schoolYear, String termCode, String edu,String majorType) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		List<Map<String,Object>> scoreList=scorePredictDao.queryScorefbByNature(deptList,stuAdvancedList, schoolYear,  termCode,  edu,majorType);
		List<Map<String,Object>> list=new ArrayList<>();
		for(int i=0;i<scoreList.size();i++){
			Map<String,Object> map1 =new HashMap<>();
			map1.put("field", "90-100");
			map1.put("name", scoreList.get(i).get("NAME_"));
			map1.put("value", scoreList.get(i).get("CL01"));
			Map<String,Object> map2 =new HashMap<>();
			map2.put("field", "80-90");
			map2.put("name", scoreList.get(i).get("NAME_"));
			map2.put("value", scoreList.get(i).get("CL02"));
			Map<String,Object> map3 =new HashMap<>();
			map3.put("field", "70-80");
			map3.put("name", scoreList.get(i).get("NAME_"));
			map3.put("value", scoreList.get(i).get("CL03"));
			Map<String,Object> map4 =new HashMap<>();
			map4.put("field", "60-70");
			map4.put("name", scoreList.get(i).get("NAME_"));
			map4.put("value", scoreList.get(i).get("CL04"));
			Map<String,Object> map5 =new HashMap<>();
			map5.put("field", "40-60");
			map5.put("name", scoreList.get(i).get("NAME_"));
			map5.put("value", scoreList.get(i).get("CL05"));
			Map<String,Object> map6 =new HashMap<>();
			map6.put("field", "0-40");
			map6.put("name", scoreList.get(i).get("NAME_"));
			map6.put("value", scoreList.get(i).get("CL06"));
			list.add(map1);list.add(map2);list.add(map3);
			list.add(map4);list.add(map5);list.add(map6);
		}
		return list;
	}
	public Map<String , Object> getStuSqlByYear(String schoolYear,String termCode, List<AdvancedParam> advancedList,String edu){
			int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
			String stuSql = businessDao.getStuSql(year, getDeptDataList(), advancedList);
//			termCode="'"+termCode+"'";
			Map<String , Object> map1 =new HashMap<>();
			map1.put("stuSql", stuSql);
			map1.put("schoolYear", schoolYear);
			map1.put("termCode", termCode);
			return map1;
		}
	@Override
	public Map<String, Object> getTeaDetail(
			List<AdvancedParam> advancedParamList, Page page,
			Map<String, Object> keyValue, List<String> fields) {
		AdvancedParam ad_year=new AdvancedParam();
		ad_year.setCode(AdvancedUtil.Common_SCHOOL_YEAR);
		ad_year.setValues(String.valueOf(EduUtils.getSchoolYear4()));
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedParamList);
		stuAdvancedList.add(ad_year);
		String stuDetailSql = getStuDetailSql(stuAdvancedList, keyValue, fields);
	// 
	Map<String, Object> map = baseDao.createPageQueryInLowerKey(stuDetailSql, page);
	return map;
	}
	private String getStuDetailSql(List<AdvancedParam> advancedParamList, Map<String, Object> keyValue, List<String> fields) {
		String majorType="all",schoolYear="",classes="",choose="",termCode="",edu="",xtype="",coursemc="",coursetypemc="", stuDetailSql="";
		if(keyValue != null){
			schoolYear=(String) keyValue.get("schoolYear");
			if(schoolYear.contains(",")){
				schoolYear=schoolYear.split(",")[0];
			}
			termCode=(String) keyValue.get("termCode");
			edu=(String) keyValue.get("edu");
			majorType=(String) keyValue.get("majorType");
			xtype=(String) keyValue.get("type");
			coursemc=(String) keyValue.get("coursemc");
			coursetypemc=(String) keyValue.get("coursetypemc");
			classes=(String) keyValue.get("class");
			choose=(String) keyValue.get("choose");
		}
		if("look".equals(xtype)){
			stuDetailSql=scorePredictDao.getlookStuSql(getDeptDataList(), advancedParamList, schoolYear,  termCode,  edu,majorType);
		}else if("contrast".equals(xtype)){
			stuDetailSql=scorePredictDao.getContrastStuSql(getDeptDataList(), advancedParamList, schoolYear,  termCode,  edu,majorType,coursemc,coursetypemc);
		}else if("accuracy".equals(xtype)){
			List<Map<String, Object>> yearList = businessService.queryBzdmSchoolYear(Constant.TABLE_T_STU_SC_PRE, "SCHOOL_YEAR");
			int i=0;
			String stuSql="";
			for(Map<String,Object> map:yearList){
				schoolYear=(String) map.get("id");
				if(i==0){
					stuDetailSql=scorePredictDao.getAccuracyStuSql(getDeptDataList(), advancedParamList, schoolYear, majorType,coursemc,coursetypemc);
				}else{
					if(i<yearList.size()){
						stuSql=scorePredictDao.getAccuracyStuSql(getDeptDataList(), advancedParamList, schoolYear, majorType,coursemc,coursetypemc);
						stuDetailSql=stuDetailSql+" UNION ALL "+stuSql;
					}
				}
				i++;
			}
		}else if("gk".equals(xtype)){
			if("rs".equals(choose)||"kc".equals(choose)){choose="all";}
			stuDetailSql=scorePredictDao.getDetailstuSql(getDeptDataList(), advancedParamList, schoolYear, termCode, edu, majorType, coursemc, coursetypemc, choose,classes);
		}else if("fb".equals(xtype)){
			coursemc=classes;
			stuDetailSql=scorePredictDao.getDetailstuSql(getDeptDataList(), advancedParamList, schoolYear, termCode, edu, majorType, coursemc, coursetypemc, choose,"all");
		}else if("tbfb".equals(xtype)){
			coursetypemc=classes;
			stuDetailSql=scorePredictDao.getDetailstuSql(getDeptDataList(), advancedParamList, schoolYear, termCode, edu, majorType, "all", coursetypemc, choose,"all");
		}
		return stuDetailSql;
	}
	@Override
	public List<Map<String, Object>> getStuDetailAll(List<AdvancedParam> advancedParamList,Page page, Map<String, Object> keyValue, List<String> fields){
//		return baseDao.queryListInLowerKey(getStuDetailSql(advancedParamList, keyValue, fields));
		Map<String, Object> map = baseDao.createPageQueryInLowerKey(getStuDetailSql(advancedParamList, keyValue, fields), page);
		return (List<Map<String, Object>>) map.get("rows");
	}
}
