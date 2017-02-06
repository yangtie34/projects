package cn.gilight.dmm.teaching.service.impl;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.gilight.dmm.business.dao.BusinessDao;
import cn.gilight.dmm.business.entity.AdvancedParam;
import cn.gilight.dmm.business.service.BusinessService;
import cn.gilight.dmm.business.util.AdvancedUtil;
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.business.util.DevUtils;
import cn.gilight.dmm.teaching.dao.FailExaminationDao;
import cn.gilight.dmm.teaching.service.FailExaminationService;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;
/**
 * 挂科补考分析
 * @author lijun
 *
 */
@Repository("failExaminationService")
public class FailExaminationServiceImpl implements FailExaminationService {

	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private FailExaminationDao failExaminationDao;
	/**
	 * shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Teaching+"failExamination";
	
	/**
	 * 获取数据权限
	 */
	private List<String> getDeptDataList(){
		return businessService.getDeptDataList(ShiroTag, null);
	}
	/**
	 * 获取挂科补考分析数据权限
	 * @return List<String>
	 */
	private List<String> getDeptDataList(String pid){
		return businessService.getDeptDataList(ShiroTag, pid);
	}
	/**
	 * 得到学年 本专科
	 */
	@Override
	public Map<String, Object> querySelectType() {
		List<Map<String, Object>> yearList = businessService.queryBzdmSchoolYear(Constant.TABLE_T_STU_Gk, "SCHOOL_YEAR"),
				  xnxqList = new ArrayList<>(),
				  eduList  = businessService.queryBzdmStuEducationList();
			String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];
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
			Map<String, Object> map = DevUtils.MAP();
			map.put("xnxq", businessService.queryBzdmGkXnXq());//xnxqList);//
			map.put("edu", businessService.queryBzdmStuEducationList(getDeptDataList()));//eduList);
			return map;
	}
	/**
	 * 得到挂科基本数据(挂科人数 挂科率 环比变化 平均挂科数)
	 */

	@Override
	public Map<String, Object> getGkInfo(List<AdvancedParam> advancedList,
			String schoolYear, String termCode, String edu) {
		return getGkInfo(getDeptDataList(),advancedList,schoolYear,termCode,edu);
	}
	@Override
	public Map<String, Object> getGkInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
//		year=schoolYear!=null?year:year;
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		return failExaminationDao.getGkInfo(stuSql,deptList, advancedList, year, termCode, edu);
	}
	/**
	 * 挂科分类信息(学生类别 人数 挂科率 变化)
	 */
	@Override
	public List<Map<String, Object>> getGkflInfo(
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
		return getGkflInfo(getDeptDataList(), advancedList, schoolYear, termCode, edu);
	}
	@Override
	public List<Map<String, Object>> getGkflInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		return failExaminationDao.getGkflInfo(stuSql,deptList, advancedList, year, termCode, edu);
	}
	/**
	 * 各年级挂科分布(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getNjGkInfo(
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
		return getNjGkInfo(getDeptDataList(), advancedList, schoolYear, termCode, edu);
	}
	@Override
	public List<Map<String, Object>> getNjGkInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
		List<Map<String, Object>> listNjname = businessService.queryBzdmNj();
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		return failExaminationDao.getNjGkInfo(stuSql,deptList, advancedList, year, termCode, edu,listNjname);
	}
	/**
	 * 男女生挂科分布(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getXbGkInfo(
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
		return getXbGkInfo(getDeptDataList(), advancedList, schoolYear, termCode, edu);
	}
	@Override
	public List<Map<String, Object>> getXbGkInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		return failExaminationDao.getXbGkInfo(stuSql,deptList, advancedList, year, termCode, edu);
	}
	/**
	 * 挂科课程分布--公共课/专业课(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getNatKcGkInfo(
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
		return getNatKcGkInfo(getDeptDataList(), advancedList, schoolYear, termCode, edu);
	}
	@Override
	public List<Map<String, Object>> getNatKcGkInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		return failExaminationDao.getNatKcGkInfo(stuSql,deptList, advancedList, year, termCode, edu);
	}
	/**
	 * 挂科课程分布--必修课/选修课(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getAttrKcGkInfo(
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
		return getAttrKcGkInfo(getDeptDataList(), advancedList, schoolYear, termCode, edu);
	}
	@Override
	public List<Map<String, Object>> getAttrKcGkInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu) {
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		return failExaminationDao.getAttrKcGkInfo(stuSql,deptList, advancedList, year, termCode, edu);
	}
	/**
	 * 各机构挂科分布(人数 人均挂科数)
	 */
	@Override
	public Map<String, Object> getJgGkInfo(
			List<AdvancedParam> advancedParamList, String schoolYear,
			String termCode, String edu) {
		/**
		 * 根据类型获取学费减免 人数、总金额、覆盖率
		 */
		String pid = AdvancedUtil.getPid(advancedParamList);
		List<String> deptList = getDeptDataList(pid);
		int year = Integer.valueOf(schoolYear.substring(0, 4));
		if(edu == null) edu = MapUtils.getString(businessService.queryBzdmStuEducationList().get(0), "id");
		Map<String, Object> deptDataMap = businessService.getDeptDataForGoingDownStu(deptList, pid, year);
		deptList = (List<String>) deptDataMap.get("deptList");
		// 组织机构
		List<Map<String, Object>> reList = new ArrayList<>();
		List<Map<String, Object>> nextLevelList = (List<Map<String, Object>>) deptDataMap.get("queryList");
		List<AdvancedParam> advancedList = new ArrayList<>();
		AdvancedParam ad_edu  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu),
					  ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
		advancedList.add(ad_edu); advancedList.add(ad_dept);
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String schoolyear = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		
		for(Map<String, Object> map : nextLevelList){
			String id_ = MapUtils.getString(map, "id"),
					level_type = MapUtils.getString(map, "level_type");
			ad_dept.setValues(id_);
				List<Map<String, Object>> li = failExaminationDao.getJgGkInfo(stuSql,deptList, advancedList, schoolyear, termCode, id_);
				map.put("list", li);
			reList.add(map);
		}
		Map<String, Object>	map = DevUtils.MAP();
		map.put("list", reList);
		return map;
	}
	/**
	 * 挂科top(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getTopGkInfo(
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu,String lx,String gkSort,String turnPage) {
		return getTopGkInfo(getDeptDataList(), advancedList, schoolYear, termCode, edu,lx,gkSort,turnPage);
	}
	@Override
	public List<Map<String, Object>> getTopGkInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu,String lx,String gkSort,String turnPage) {
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		return failExaminationDao.getTopGkInfo(stuSql,deptList, advancedList, year, termCode, edu,lx,gkSort,turnPage);
	}
	/**
	 * 学生挂科top(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getStuTopGkInfo(
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu,String gkStuSort,String turnStuPage) {
		return getStuTopGkInfo(getDeptDataList(), advancedList, schoolYear, termCode, edu,gkStuSort,turnStuPage);
	}
	@Override
	public List<Map<String, Object>> getStuTopGkInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu,String gkStuSort,String turnStuPage) {
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		return failExaminationDao.getStuTopGkInfo(stuSql,deptList, advancedList, year, termCode, edu,gkStuSort,turnStuPage);
	}

	/**
	 * 挂科top(人数 人均挂科数)
	 */
	@Override
	public List<Map<String, Object>> getTopbkInfo(
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu,String lx,String bkTopSort,String bkturnPage) {
		return getTopbkInfo(getDeptDataList(), advancedList, schoolYear, termCode, edu,lx,bkTopSort, bkturnPage);
	}
	@Override
	public List<Map<String, Object>> getTopbkInfo(List<String> deptList,
			List<AdvancedParam> advancedList, String schoolYear,
			String termCode, String edu,String lx,String bkTopSort,String bkturnPage) {
//		int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
		advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
		advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu));
		Map<String, Object> listMap=getStuSqlByYear(schoolYear, termCode, advancedList,edu);
		String year = (String) listMap.get("schoolYear");
		termCode=(String) listMap.get("termCode");
		String stuSql=(String) listMap.get("stuSql");
		return failExaminationDao.getTopbkInfo(stuSql,deptList, advancedList, year, termCode, edu,lx, bkTopSort, bkturnPage);
	}

	public Map<String , Object> getStuSqlByYear(String schoolYear,String termCode, List<AdvancedParam> advancedList,String edu){
		if("3333".equals(schoolYear)){
//			String stuSql=" ";
//			Map<String, Object> queryYearAndTerm= querySelectType();
//			List<Map<String,Object>> listxnxq=(List<Map<String, Object>>) queryYearAndTerm.get("xnxq");
//			advancedList = advancedList!= null ? advancedList : new ArrayList<AdvancedParam>();
//			advancedList.add(new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Common_EDU_ID, edu));
//		    List<Map<String,Object>> list=new ArrayList<>();
//		    List<List<Map<String,Object>>> list1=new ArrayList<>();
//		    String yearAndTerm;
//		    int schoolyear;
//		    String term;
//		    int i=0;
////		    int data1=0,data2=0,data3=0,data4 = 0;
//		    int[] data={0,0,0,0},data1={0,0,0,0};
//			for(Map<String,Object> yearMap:listxnxq){
//				if(i<Integer.valueOf(schoolYear.substring(0, 1))){
//				yearAndTerm=(String) yearMap.get("id");
//				schoolyear=Integer.valueOf(yearAndTerm.substring(0, 4));
//				term=yearAndTerm.split(",")[1];
//				stuSql = businessDao.getStuSql(schoolyear, getDeptDataList(), advancedList);
//			    Map<String ,Object> listMap	= failExaminationDao.getGkInfo(stuSql,getDeptDataList(), advancedList, yearAndTerm.substring(0, 4), term, edu);
//			    List<Map<String,Object>> listMap1= failExaminationDao.getGkflInfo(stuSql,getDeptDataList(), advancedList, yearAndTerm.substring(0, 4), term, edu);
//			    list.add(listMap);
//			    list1.add(listMap1);
//				}else{
//					break;
//				}
//			    i++;
//			}
//			int j=0;
//			String dataName;
//			for(List<Map<String,Object>> relist :list1){
//				
//				if(relist.size()!=0){
//					for(Map<String,Object> remap :relist){
//						relist.get(j).get("CL01");
//						if(remap.get("CL01").equals(relist.get(j).get("CL01"))){
//							dataName=(String) remap.get("CL01").toString();
////							data[0]+=Integer.valueOf((String)remap.get("CL01").toString());
//							data1[1]+=Double.valueOf((String) remap.get("CL02").toString());
//							data1[2]+=Double.valueOf((String) remap.get("CL03").toString());
//							data1[3]+=Double.valueOf((String) remap.get("CL04").toString());
//						}
//						j++;
//					}
//				}
//				
//			}
//			Map<String,Object> map=getMapData(data, list);
//			return map;
			int year = Integer.valueOf(EduUtils.getSchoolYear4());
			String stuSql=" ";
			StringBuffer sql=new StringBuffer();
			StringBuffer sYear=new StringBuffer();
			for(int i=year;i>=year-2;i--){
				if(i==year-2){
					stuSql = businessDao.getStuSql(i, getDeptDataList(), advancedList);
					sql.append(stuSql);
					sYear.append(i);
				}else{
					stuSql = businessDao.getStuSql(i, getDeptDataList(), advancedList)+" union ";
					sql.append(stuSql);
					sYear.append(i).append(",");
				}
			}
			Map<String , Object> map1 =new HashMap<>();
			map1.put("stuSql", sql.toString());
			map1.put("schoolYear", sYear.toString());
			map1.put("termCode", "'01','02'");
			return map1;
		}else if("5555".equals(schoolYear)){
			int year = Integer.valueOf(EduUtils.getSchoolYear4());
			String stuSql=" ";
			StringBuffer sql=new StringBuffer();
			StringBuffer sYear=new StringBuffer();
			for(int i=year;i>=year-4;i--){
				if(i==year-4){
					stuSql = businessDao.getStuSql(i, getDeptDataList(), advancedList);
					sql.append(stuSql);
					sYear.append(i);
				}else{
					stuSql = businessDao.getStuSql(i, getDeptDataList(), advancedList)+" union ";
					sql.append(stuSql);
					sYear.append(i).append(",");
				}
			}
			Map<String , Object> map1 =new HashMap<>();
			map1.put("stuSql", sql.toString());
			map1.put("schoolYear", sYear.toString());
			map1.put("termCode", "'01','02'");
			return map1;
		}else{
			int year = Integer.valueOf(schoolYear.substring(0, 4));//学年
			String stuSql = businessDao.getStuSql(year, getDeptDataList(), advancedList);
//			termCode="'"+termCode+"'";
			Map<String , Object> map1 =new HashMap<>();
			map1.put("stuSql", stuSql);
			map1.put("schoolYear", schoolYear.substring(0, 4));
			map1.put("termCode", termCode);
			return map1;
		}
	}
	public Map<String , Object> getMapData(int[] data,List<Map<String,Object>> list){
	for(Map<String,Object> map :list){
		data[0]+=Integer.valueOf((String) map.get("CL01").toString());
		data[1]+=Double.valueOf((String) map.get("CL02").toString());
		data[2]+=Double.valueOf((String) map.get("CL03").toString());
		data[3]+=Double.valueOf((String) map.get("CL04").toString());
	}
	Map<String , Object> map =new HashMap<>();
	map.put("CL01", data[0]);
	map.put("CL02", Double.valueOf(data[1]/3));
	map.put("CL03", "无法计算");
	map.put("CL04", Double.valueOf(data[3]/3));
	return map;
	}
}
