package cn.gilight.dmm.xg.service.impl;

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
import cn.gilight.dmm.xg.dao.FeeWarningDao;
import cn.gilight.dmm.xg.service.FeeWarningService;
import cn.gilight.dmm.xg.util.ExcelUtils;
import cn.gilight.dmm.xg.util.MailUtils;
import cn.gilight.dmm.xg.util.ZipUtils;
import cn.gilight.framework.base.dao.BaseDao;
import cn.gilight.framework.entity.ExcelParam;
import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.MathUtils;
import cn.gilight.framework.uitl.common.StringUtils;
import cn.gilight.framework.uitl.product.EduUtils;
/**
 * 学费预警
 * @author lijun
 *
 */
@Service("feeWarningService")
public class FeeWarningServiceImpl implements FeeWarningService {
	@Resource
	private FeeWarningDao feeWarningDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BaseDao baseDao;
	/**
	 * 在籍学生 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg+"notGradDegree";
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
	 * 得到当前学年未缴费学生总人数和总金额
	 */
	@Override
	public Map<String, Object> queryXfInfo(List<AdvancedParam> advancedList) {
		return queryXfInfo(getDeptDataList(),advancedList);
	}
	@Override
	public Map<String, Object> queryXfInfo(List<String> deptList,List<AdvancedParam> advancedList) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return feeWarningDao.queryXfInfo(deptList,stuAdvancedList);
	}
	/**
	 * 得到欠费详细信息的筛选条件
	 */
	@Override
	public List<Map<String, Object>> querySelectType() {
		List<Map<String , Object>> list =new ArrayList<>();
		Map<String , Object> map =new HashMap<>();
		map.put("EDU_ID", businessService.queryBzdmStuEducationList(getDeptDataList()));//学生类型(本专科等)
		map.put("lx", queryfeeWarningTypeList());//欠费类型(住宿费等)
		map.put("zt", queryXjList());//学生在籍状态(在籍 往届非在籍)
		list.add(map);
		return list;
	}
	@Override
	public List<Map<String, Object>> queryfeeWarningTypeList(){
		List<Map<String, Object>> list = new ArrayList<>();
		List<Map<String , Object>> listType=feeWarningDao.queryQfTypeList();
		StringBuffer str = new StringBuffer();
		for(int i=0;i<listType.size();i++){
			if(i==listType.size()-1){
				str.append(listType.get(i).get("ID"));
			}else{
				str.append(listType.get(i).get("ID")).append(",");
			}
		}
		Map<String, Object> map1 = new HashMap<>();
		map1.put("id", str);
		map1.put("mc", "所有费用");list.add(map1);
		for(int i=0;i<listType.size();i++){
			Map<String, Object> map = new HashMap<>();
			map.put("id", listType.get(i).get("ID"));
			map.put("mc", listType.get(i).get("MC"));
			list.add(map);
		}
		return list;
	}
	/**
	 * 未缴纳学费类型分布
	 */
	@Override
	public List<Map<String, Object>> queryXflxfb(List<AdvancedParam> advancedList) {
		return queryXflxfb(getDeptDataList(),advancedList);
	}
	@Override
	public List<Map<String, Object>> queryXflxfb(List<String> deptList,List<AdvancedParam> advancedList) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return feeWarningDao.queryXflxfb(deptList,stuAdvancedList);
	}
	/**
	 * 未缴纳学费学生类型分布
	 */
	@Override
	public List<Map<String, Object>> queryXslxfb(List<AdvancedParam> advancedList) {
		return queryXslxfb(getDeptDataList(),advancedList);
	}
	@Override
	public List<Map<String, Object>> queryXslxfb(List<String> deptList,List<AdvancedParam> advancedList) {
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		return feeWarningDao.queryXslxfb(deptList,stuAdvancedList);
	}
	/**
	 * 历年未缴费金额及比例
	 */
	@Override
	public Map<String, Object> queryQfjeAndRadio(List<AdvancedParam> advancedList) {
		List<Object> listyear= new ArrayList<>();//得到学年
		List<Map<String, Object>> list=new ArrayList<>();
		List<AdvancedParam> stuAdvancedList = AdvancedUtil.getAdvancedParamStu(advancedList);
		int schoolYear = EduUtils.getSchoolYear4();//得到当前学年
		List<Map<String, Object>> li=feeWarningDao.queryQfjeAndRadio(schoolYear, getDeptDataList(),stuAdvancedList);
		for(int year=schoolYear-5;year<=schoolYear;year++){
			Map<String, Object> map =new HashMap<>();
			map.put("name", year+"-"+(year+1));
			map.put("value_change", 0);
			map.put("value_stu", 0);
			for(Map<String,Object> stu_map:li){
				if(Integer.parseInt((String)stu_map.get("YEAR_"))==year){
					map.put("value_change", stu_map.get("MONEY_"));
					map.put("value_stu", stu_map.get("RADIO"));
				}
			}
			list.add(map);
		}
		Map<String, Object>	map = DevUtils.MAP();
		map.put("list", list);
		return map;
	}
	/**
	 * 得到欠费的详细信息
	 */
	@Override
	public Map<String, Object> queryQfInfo(Integer year,String edu, String lx,
			String slx) {
		List<String> deptList = getDeptDataList();
		int schoolyear = EduUtils.getSchoolYear4();
		String pid = null;
		if(year!=1){
			year=schoolyear;//只看当前年
		}
//		Map<String, Object> deptDataMap = businessService.getDeptDataForGoingDownStu(deptList, pid, schoolyear);
//		deptList = (List<String>) deptDataMap.get("deptList");
		List<AdvancedParam> advancedList = new ArrayList<>();
		AdvancedParam ad_edu  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
	advancedList.add(ad_edu); 
		String sql = feeWarningDao.queryQfInfo(deptList, advancedList, year, edu, lx, slx);
		String dataSql = businessService.getNextLevelDataSqlByDeptDataAndPid(deptList, pid, sql, false, true, false, false, year, null),
				instructorsSql2="SELECT  T.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME,COUNT(DISTINCT T.STU_ID) COUNT_,SUM(T.ARREAR_MONEY) MONEY_ FROM  ("+dataSql+") T WHERE T.NEXT_DEPT_CODE IS NOT NULL GROUP BY T.NEXT_DEPT_CODE,T.NEXT_DEPT_NAME";
		String  stuSql  = businessDao.getStuSql(schoolyear, deptList, null),
				stuSql2 = businessService.getNextLevelGroupSqlByDeptDataAndPid(deptList, pid, stuSql, true, true, false, false, year, null);
		@SuppressWarnings("unused")
		List<Map<String, Object>> instructors_li = baseDao.queryListInLowerKey(instructorsSql2);
		@SuppressWarnings("unused")
		List<Map<String, Object>> stu_li = baseDao.queryListInLowerKey(stuSql2);
		// 组织机构
		List reList = new ArrayList<>();
		/********************************************************/
		for(Map<String, Object> map : stu_li){
			Map<String,Object> map1 =new HashMap<>();
			List<Map<String,Object>> list= new ArrayList<>();
			map1.put("id",map.get(Constant.NEXT_LEVEL_COLUMN_CODE));
			map1.put("name",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
			for(Map<String, Object> stu_map : instructors_li){
				if(map.get(Constant.NEXT_LEVEL_COLUMN_CODE).equals(stu_map.get(Constant.NEXT_LEVEL_COLUMN_CODE))){
				 Map<String,Object> map2=new HashMap<>();
				 Map<String,Object> map3=new HashMap<>();
				 Map<String,Object> map5=new HashMap<>();
				 List<Map<String,Object>> list2=new ArrayList<>();
				map2.put("CL01",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
				map2.put("CL02",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
				map2.put("CL03",stu_map.get("count_"));
				if(Integer.parseInt((String) map.get(Constant.NEXT_LEVEL_COLUMN_COUNT))==0){
					map2.put("CL04",0);
				}else{
					double stuCount=Integer.parseInt((String) stu_map.get("count_")),
							zCount=Integer.parseInt((String) map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
					  Double scale = MathUtils.getPercentNum(stuCount, zCount);
				map2.put("CL04",scale);
				}
				map2.put("CL05",stu_map.get("money_"));
				list2.add(map2);
				map3.put("list", list2);
				map5.put("CL02", map2.put("CL02",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT)));
				map3.put("CL02", map5);
				list.add(map3);
			}
		}
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> getList=(List<Map<String, Object>>) map1.get("list");
			if(getList==null){
				 Map<String,Object> map2=new HashMap<>();
				 Map<String,Object> map3=new HashMap<>();
				 Map<String,Object> map5=new HashMap<>();
				 List<Map<String,Object>> list2=new ArrayList<>();
				map2.put("CL01",map.get(Constant.NEXT_LEVEL_COLUMN_NAME));
				map2.put("CL02",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT));
				map2.put("CL03",0);
			    map2.put("CL04",0);
				map2.put("CL05",0);
				list2.add(map2);
				map3.put("list", list2);
				map5.put("CL02", map2.put("CL02",map.get(Constant.NEXT_LEVEL_COLUMN_COUNT)));
				map3.put("CL02", map5);
				list.add(map3);
					map1.put("list", list);
			}
			reList.add(map1);
		}
		/********************************************************************************/
//		@SuppressWarnings("unchecked")
//		List<Map<String, Object>> nextLevelList = (List<Map<String, Object>>) deptDataMap.get("queryList");
//		List<AdvancedParam> advancedList = new ArrayList<>();
//		AdvancedParam ad_edu  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu),
//				  ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null);
//	advancedList.add(ad_edu); advancedList.add(ad_dept);
//		
//		for(Map<String, Object> map : nextLevelList){
//			ad_dept.setValues(MapUtils.getString(map, "id"));
//			List<Map<String, Object>> li = feeWarningDao.queryQfInfo(deptList,advancedList,year,edu,lx,slx);
//			map .put("list", li);
//			reList.add(map);
//		}
		// return
		Map<String, Object>	map = new HashMap<>();
		map.put("list", reList);
		return map;
	}
	/**
	 * 发送邮件
	 */
	@Override
	public Map<String ,Object> sendQfInfo(String sendType,String pid,String list,String year,String edu, String lx,
			String slx,HttpServletRequest request) {

		String filename=request.getSession().getServletContext().getRealPath("/static/email/"+list.substring(0, list.indexOf(":"))+".xls");
		List<AdvancedParam> advancedList = new ArrayList<>();
		AdvancedParam ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null),
				ad_edu  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
	    advancedList.add(ad_dept);
	    advancedList.add(ad_edu);
		ad_dept.setValues(pid);
		Map<String, Object> li = feeWarningDao.sendQfInfo(getDeptDataList(),advancedList,year,edu,lx,slx);
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
			 param.setCL6((String) map.get("CL06"));
			 param.setCL7((String) map.get("CL07"));
			 param.setCL8((String) map.get("CL08").toString());
			 sList.add(param);
		 }
		 	String emailFileName = null;
		    List<Map<String,Object>> emailList=feeWarningDao.queryEmailList();//得到所有院系的email
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
	public void exportQfInfo(String pid, String list,String year,
			String edu, String lx, String slx, HttpServletResponse response) {
		List<AdvancedParam> advancedList = new ArrayList<>();
		AdvancedParam ad_dept = new AdvancedParam(AdvancedUtil.Group_Common, AdvancedUtil.Common_DEPT_ID, null),
				ad_edu  = new AdvancedParam(AdvancedUtil.Group_Stu, AdvancedUtil.Stu_EDU_ID, edu);
	    advancedList.add(ad_dept);
	    advancedList.add(ad_edu);
		ad_dept.setValues(pid);
		Map<String, Object> li = feeWarningDao.sendQfInfo(getDeptDataList(),advancedList,year,edu,lx,slx);
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
			 param.setCL6((String) map.get("CL06"));
			 param.setCL7((String) map.get("CL07"));
			 param.setCL8((String) map.get("CL08").toString());
			 sList.add(param);
		 }
			try {
//				String filename=new String((list.substring(0, list.indexOf(":"))+".xls").getBytes(), "UTF-8");
				response.setContentType("application/x-execl"); 
//				response.setHeader("Content-Disposition", "attachment;filename=" + new String((list.substring(0, list.indexOf(":"))+".xls").getBytes(), "UTF-8"));
//				response.setHeader("Content-Disposition", "attachment;filename=" + "email.xls");
				response.setHeader("Content-Disposition", "attachment;filename=" + "email.xls");//filename
					ServletOutputStream outputStream = response.getOutputStream();	
//					System.out.println("excel名称："+list.substring(0, list.indexOf(":")));
				ExcelUtils.exportUserExcel(list.concat(ss),sList,outputStream);//输入excel到本地
				if(outputStream != null){
					outputStream.close();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	/**
	 * 得到学籍状态
	 */
	@Override
	public List<Map<String, Object>> queryXjList() {
		List<Map<String, Object>> li=feeWarningDao.queryXjList();//查询数据库得到数据
		List<Map<String, Object>> list = new ArrayList<>();
		String array ;
		for(Map<String, Object> ary : li){
			Map<String, Object> map = new HashMap<>();
			if(ary.get("ID").equals("1")){
			map.put("id", ary.get("ID"));
			map.put("mc", ary.get("MC"));
			list.add(map);
			}else if(ary.get("ID").equals("2")){
				map.put("id", ary.get("ID"));
				map.put("mc", ary.get("MC"));
				list.add(map);
			}
		}
		return list;
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
	    List<Map<String,Object>> emailList=feeWarningDao.queryEmailList();//得到所有院系的email

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
	public void excelQfAll(HttpServletRequest request,HttpServletResponse response) {
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
		String  stuDetailSql=feeWarningDao.getNodegreeStuSql(getDeptDataList(), advancedParamList);
		if(keyValue != null)
			for(Map.Entry<String, Object> entry : keyValue.entrySet()){
				String type = entry.getKey(), code = String.valueOf(entry.getValue());
				if(!("examrs".equals(type))){
				stuDetailSql+=" AND "+type+" = '"+code+"'  ORDER BY TSWD.ARREAR_MONEY DESC ";
				}else{
					stuDetailSql+=" AND TT.NO IS NOT NULL ORDER BY TSWD.ARREAR_MONEY DESC ";
				}
			}
		
		// 所要查询的字段
		if(fields!=null && !fields.isEmpty()){
			stuDetailSql = "select "+StringUtils.join(fields, ",")+ " from ("+stuDetailSql+")";
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
