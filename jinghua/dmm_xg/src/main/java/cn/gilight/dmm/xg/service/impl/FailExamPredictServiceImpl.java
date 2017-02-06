package cn.gilight.dmm.xg.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import cn.gilight.dmm.xg.dao.FailExamPredictDao;
import cn.gilight.dmm.xg.service.FailExamPredictService;
import cn.gilight.dmm.xg.util.MailUtils;
import cn.gilight.dmm.xg.util.ZipUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.entity.ExcelParam;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ExcelUtils;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
import cn.gilight.framework.uitl.product.Globals;
/**
 * 学挂科预测
 * @author lijun
 *
 */
@Service("failExamPredictService")
public class FailExamPredictServiceImpl implements FailExamPredictService {
	@Resource
	private FailExamPredictDao failExamPredictDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 在籍学生 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"failExamPredict";
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
		Map<String,Object> map = new HashMap<>();
			String termCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[1];
			String yearCode = EduUtils.getSchoolYearTerm(DateUtils.getNowDate())[0];
			String term ;
			String sql="SELECT  SUBSTR(TFP.DATE_,0,10) date_now,to_char((to_date(SUBSTR(TFP.DATE_,0,10),'yyyy-mm-dd')-1),'yyyy-mm-dd') date_pro  FROM T_STU_SCORE_PREDICT_BEH TFP WHERE ROWNUM=1";
			List<Map<String,Object>> time_= baseDao.getJdbcTemplate().queryForList(sql);
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
//			try {
//				Date d = sf.parse(time);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if(termCode.equals(Globals.TERM_SECOND)){
				term="第二学期";
			}else{
				term="第一学期";
			}
			String yearAndTerm = yearCode+"学年"+term;
			map.put("xnxq", yearAndTerm);
			map.put("date", time_);
			return map;
	}
	/**
	 * 得到当前学年挂科预测学生期末考试人数和预测挂科概率
	 */
	@Override
	public Map<String, Object> queryGkInfo(List<AdvancedParam> advancedList) {
		return queryGkInfo(getDeptDataList(),advancedList);
	}
	@Override
	public Map<String, Object> queryGkInfo(List<String> deptList,List<AdvancedParam> advancedList) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return failExamPredictDao.queryGkInfo(deptList,stuAdvancedList);
	}
	/**
	 * 挂科预测学生性别类型分布
	 */
	@Override
	public List<Map<String, Object>> queryGklxfb(List<AdvancedParam> advancedList) {
		return queryGklxfb(getDeptDataList(),advancedList);
	}
	@Override
	public List<Map<String, Object>> queryGklxfb(List<String> deptList,List<AdvancedParam> advancedList) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return failExamPredictDao.queryGklxfb(deptList,stuAdvancedList);
	}
	/**
	 * 挂科预测学生类型分布
	 */
	@Override
	public List<Map<String, Object>> queryXslxfb(List<AdvancedParam> advancedList) {
		return queryXslxfb(getDeptDataList(),advancedList);
	}
	@Override
	public List<Map<String, Object>> queryXslxfb(List<String> deptList,List<AdvancedParam> advancedList) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return failExamPredictDao.queryXslxfb(deptList,stuAdvancedList);
	}
	/**
	 * 各院系挂科预测学生人数和预测挂科概率
	 */
	@Override
	public Map<String, Object> queryGkrsAndRadio(List<AdvancedParam> advancedParamList) {
		String pid = AdvancedUtil.getPid(advancedParamList);
		List<String> deptList = getDeptDataList();
		int year = EduUtils.getSchoolYear4();
//		String pid = null;
		Map<String, Object> deptDataMap = businessService.getDeptDataForGoingDownStu(deptList, pid, year);
		deptList = (List<String>) deptDataMap.get("deptList");
		// 组织机构
		List<Map<String, Object>> reList = new ArrayList<>();
		List<Map<String, Object>> nextLevelList = (List<Map<String, Object>>) deptDataMap.get("queryList");
		List<AdvancedParam> advancedList = new ArrayList<>();
		AdvancedParam ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
	    advancedList.add(ad_dept);
	    /****************************************************/
		String instructorsSql  = failExamPredictDao.queryGkrsAndRadio(deptList, advancedList),
//			instructorsSql2 = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, pid, instructorsSql, true, true, true, false, year, null),
				   stuSql  = businessDao.getStuSql(year, deptList, null),
				   stuSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, stuSql, true, true, false, false, year, null),
		   instructorsSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, instructorsSql, true, true, true, false, year, null);
			List<Map<String, Object>> instructors_li = baseDao.queryListInLowerKey(instructorsSql2);
			List<Map<String, Object>> stu_li = baseDao.queryListInLowerKey(stuSql2);
	/********************************************************/
			for(Map<String, Object> map : instructors_li){
				Map<String,Object> map1 =new HashMap<>();
				for(Map<String, Object> stu_map : stu_li){
					if(map.get(Constant.NEXT_LEVEL_COLUMN_CODE).equals(stu_map.get(Constant.NEXT_LEVEL_COLUMN_CODE))){
					map1.put("id",map.get(Constant.NEXT_LEVEL_COLUMN_CODE));
					map1.put("name",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
					map1.put("value_change",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
					if(Integer.parseInt((String) stu_map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))==0){
						map1.put("value_stu",0);
					}else{
					map1.put("value_stu",Math.round((Integer.parseInt((String) map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))/Integer.parseInt((String) stu_map.get(Constant.NEXT_LEVEL_COLUMN_COUNT)))*100)*0.01d);
					}
					}
				}
				reList.add(map1);
			}
//		for(Map<String, Object> map : nextLevelList){
//			ad_dept.setValues(MapUtils.getString(map, "id"));
//			// 人数、比例
//			List<Map<String, Object>> li = failExamPredictDao.queryGkrsAndRadio(deptList,advancedList);
////			map.put("list", li);
//			if(li.size()!=0){
//				map.put("value_change", li.get(0).get("COUNT_"));
//				map.put("value_stu", li.get(0).get("RATIO_"));
//			}else{
//				map.put("value_change", 0);
//				map.put("value_stu", 0);
//			}
//			reList.add(map);
//		}
		// return
		Map<String, Object>	map = DevUtils.MAP();
		map.put("list", reList);
		return map;
	}
	/**
	 * 得到挂科预测学生的详细信息
	 */
	@Override
	public Map<String, Object> queryGkxxInfo(List<AdvancedParam> advancedParamList) {
		String pid = AdvancedUtil.getPid(advancedParamList);
		List<String> deptList = getDeptDataList();
		int year = EduUtils.getSchoolYear4();
//		String pid = null;
//		Map<String, Object> deptDataMap = businessService.getDeptDataForGoingDownStu(deptList, pid, year);
//		deptList = (List<String>) deptDataMap.get("deptList");
		String sql = failExamPredictDao.queryGkxxInfo(deptList, advancedParamList);
		String dataSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, pid, sql, false, true, false, false, year, null);
//				instructorsSql2=" SELECT T.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME,T.TYPEMC TNAME_,"+fbchoose+" COUNT_ FROM ("+dataSql+") T WHERE T.NEXT_DEPT_CODE IS NOT NULL AND T.SCHOOL_YEAR='"+schoolYear+"' GROUP BY T.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME,T.TYPEMC";
		String  stuSql  = businessDao.getStuSql(year, deptList, null),
				stuSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, stuSql, true, true, false, false, year, null);
		List<Map<String, Object>> instructors_li = baseDao.queryListInLowerKey(dataSql);
		List<Map<String, Object>> stu_li = baseDao.queryListInLowerKey(stuSql2);
		// 组织机构
		List<Map<String, Object>> reList = new ArrayList<>();
		/********************************************************/
		for(Map<String, Object> map : stu_li){
			Map<String,Object> map1 =new HashMap<>();
			List<Map<String,Object>> list= new ArrayList<>();
			map1.put("id",map.get(Constant.NEXT_LEVEL_COLUMN_CODE));
			map1.put("name",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
			for(Map<String, Object> stu_map : instructors_li){
				if(map.get(Constant.NEXT_LEVEL_COLUMN_CODE).equals(stu_map.get(Constant.NEXT_LEVEL_COLUMN_CODE))){
				 Map<String,Object> map2=new HashMap<>();
				map2.put("CL01",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
				map2.put("CL02",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
				map2.put("CL03",stu_map.get("cl03"));
				map2.put("CL04",stu_map.get("cl04"));
				list.add(map2);
				map1.put("list", list);
			}
		}
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> getList=(List<Map<String, Object>>) map1.get("list");
			if(getList==null){
				 Map<String,Object> map2=new HashMap<>();
					map2.put("CL01",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
					map2.put("CL02",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
					map2.put("CL03",0);
					map2.put("CL04",0);
					list.add(map2);
					map1.put("list", list);
			}
			reList.add(map1);
		}
		/********************************************************************************/
//		List<Map<String, Object>> nextLevelList = (List<Map<String, Object>>) deptDataMap.get("queryList");
//		List<AdvancedParam> advancedList = new ArrayList<>();
//		AdvancedParam ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
//	    advancedList.add(ad_dept);
//		
//		for(Map<String, Object> map : nextLevelList){
//			ad_dept.setValues(MapUtils.getString(map, "id"));
//			// 人数、比例
//			List<Map<String, Object>> li = failExamPredictDao.queryGkxxInfo(deptList, advancedList);
//			map.put("list", li);
//			reList.add(map);
//		}
		// return
		Map<String, Object>	map = DevUtils.MAP();
		map.put("list", reList);
		return map;
	}
	/**
	 * 发送邮件
	 */
	@Override
	public Map<String ,Object> sendGkInfo(String sendType,String pid,String list,HttpServletRequest request) {

		String filename=request.getSession().getServletContext().getRealPath("/static/email/"+list.substring(0, list.indexOf(":"))+".xls");
		List<AdvancedParam> advancedList = new ArrayList<>();
		AdvancedParam ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
	    advancedList.add(ad_dept);
		ad_dept.setValues(pid);
		Map<String, Object> li = failExamPredictDao.sendGkInfo(getDeptDataList(),advancedList);
		List xl=new ArrayList();
		 xl=(List) li.get("lx");
		 String s;
		 String ss = "";
		 for(int i=0;i<xl.size();i++){
			s=xl.get(i).toString().replace("{NAME_=", ""); 
			ss=ss.concat(s.replace(", COUNT_=", "  ").replace("}", "  ")); 
			System.out.println(ss); 
		 }
		 List reList =new ArrayList<>();
		 List sList =new ArrayList<>();
		 reList=(List) li.get("xx");
		 for(int i=0;i<reList.size();i++){
			 Map map=new HashMap<>();
			 ExcelParam param=new ExcelParam();
			 map=(Map) reList.get(i);
			 param.setCL1((String) map.get("CL01"));
			 param.setCL2((String) map.get("CL02"));
			 param.setCL3((String) map.get("CL03"));
			 param.setCL4((String) map.get("CL04"));
			 param.setCL5((String) map.get("CL05"));
			 sList.add(param);
		 }
		 	String emailFileName = null;
		    List<Map<String,Object>> emailList=failExamPredictDao.queryEmailList();//得到所有院系的email
		    for(int i=0;i<emailList.size();i++){
		    	if(pid.equals(emailList.get(i).get("ID"))){
		    		emailFileName=(String) emailList.get(i).get("EMAIL");
		    	}
		    }
			ExcelUtils.exportUserExcel(list.concat(ss),sList,filename);//输入excel到本地
			Map<String,Object> reMap=new HashMap<>();
			reMap.put("fh", "发送失败!");
			if("only".equals(sendType)){
				try {
					
					if(MailUtils.send(filename,emailFileName)){
						reMap.put("fh", "发送成功!");
						return reMap;
					}else{
						return reMap;
					}
				} catch (Exception e) {
					return reMap;
				}}else if("all".equals(sendType)){
					
				}
			return reMap;
	}
	/**
	 * 导出excel文件
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void exportGkInfo(String pid, String list, HttpServletResponse response) {
		List<AdvancedParam> advancedList = new ArrayList<>();
		AdvancedParam ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
	    advancedList.add(ad_dept);
		ad_dept.setValues(pid);
		Map<String, Object> li = failExamPredictDao.sendGkInfo(getDeptDataList(),advancedList);
		List xl=new ArrayList();
		 xl=(List) li.get("lx");
		 String s;
		 String ss = "";
		 for(int i=0;i<xl.size();i++){
			s=xl.get(i).toString().replace("{NAME_=", ""); 
			ss=ss.concat(s.replace(", COUNT_=", "  ").replace("}", "  ")); 
			System.out.println(ss); 
		 }
		 List reList =new ArrayList<>();
		 List sList =new ArrayList<>();
		 reList=(List) li.get("xx");
		 for(int i=0;i<reList.size();i++){
			 Map map=new HashMap<>();
			 ExcelParam param=new ExcelParam();
			 map=(Map) reList.get(i);
			 param.setCL1((String) map.get("CL01"));
			 param.setCL2((String) map.get("CL02"));
			 param.setCL3((String) map.get("CL03"));
			 param.setCL4((String) map.get("CL04"));
			 param.setCL5((String) map.get("CL05"));
			 sList.add(param);
		 }
			try {
				response.setContentType("application/x-execl"); 
				response.setHeader("Content-Disposition", "attachment;filename=" + new String((list.substring(0, list.indexOf(":"))+".xls").getBytes(), "iso-8859-1"));
					ServletOutputStream outputStream = response.getOutputStream();		 
				ExcelUtils.exportUserExcel(list.concat(ss),sList,outputStream);//输入excel到本地
				if(outputStream != null){
					outputStream.close();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/**
	 * 全部发送
	 */
	@Override
	public Map<String, Object> sendAll(HttpServletRequest request) {
		Map<String,Object> reMap=new HashMap<>();
		String filename=request.getSession().getServletContext().getRealPath("/static/email/");
		String filenameZip=filename+"\\email.zip";
		File file=new File(filenameZip);
		reMap.put("fh", "发送失败!");
		if(!file.exists()){
			ZipUtils zca = new ZipUtils(filenameZip);
			zca.compressExe(filename);
		}
		else{
			file.delete();
			ZipUtils zca = new ZipUtils(filenameZip);
			zca.compressExe(filename);
		}
	 	String emailFileName = null;
	    List<Map<String,Object>> emailList=failExamPredictDao.queryEmailList();//得到所有院系的email

		try {
		    for(int i=0;i<emailList.size();i++){
	    		emailFileName=(String) emailList.get(i).get("EMAIL");
	    
			if(MailUtils.send(filenameZip,emailFileName)){
				reMap.put("fh", "发送成功!");
//				return reMap;
			}else{
//				return reMap;
			}
		    }
		    return reMap;
		} catch (Exception e) {
			return reMap;
		}
	}
	@Override
	public void excelGkAll(HttpServletRequest request,HttpServletResponse response) {
		String filename=request.getSession().getServletContext().getRealPath("/static/email/");
		String filenameZip=filename+"\\email.zip";
		File file=new File(filenameZip);
		if(!file.exists()){
			ZipUtils zca = new ZipUtils(filenameZip);
			zca.compressExe(filename);
		}
		else{
			file.delete();
			ZipUtils zca = new ZipUtils(filenameZip);
			zca.compressExe(filename);
		}
		try {
			response.setContentType("application/x-execl"); 
			response.setHeader("Content-Disposition", "attachment;filename=" + new String(("email.zip").getBytes(), "UTF-8"));
		      //读取文件  
	        InputStream in = new FileInputStream(filenameZip);  
			ServletOutputStream outputStream = response.getOutputStream();
	        //写文件  
	        int b;  
	        while((b=in.read())!= -1)  
	        {  
	        	outputStream.write(b);  
	        }  
	          
	        in.close();  
	        outputStream.close(); 
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
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
		String  stuDetailSql=failExamPredictDao.getNodegreeStuSql(getDeptDataList(), advancedParamList);
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				if(!("examrs".equals(type))){
				stuDetailSql+=" AND "+type+" = '"+code+"' ORDER BY T.PREDICT_SCORE DESC";
				}else{
					stuDetailSql+=" AND T.STU_ID IS NOT NULL ORDER BY T.PREDICT_SCORE DESC";
				}
			}
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+") ";
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
