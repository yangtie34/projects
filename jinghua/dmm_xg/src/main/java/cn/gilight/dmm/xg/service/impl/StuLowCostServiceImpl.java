package cn.gilight.dmm.xg.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
import cn.gilight.dmm.business.util.Constant;
import cn.gilight.dmm.xg.dao.StuCostDao;
import cn.gilight.dmm.xg.service.StuHighCostService;
import cn.gilight.dmm.xg.service.StuLowCostService;
import cn.gilight.dmm.xg.util.MailUtils;
import cn.gilight.dmm.xg.util.ZipUtils;
import cn.gilight.framework.page.Page;
@Service("stuLowCostService")
public class StuLowCostServiceImpl implements StuLowCostService {
	@Resource
	private BusinessDao businessDao;
	@Resource
	private BusinessService businessService;
	@Resource
	private StuCostDao stuCostDao;
	@Resource
	private StuHighCostService stuHighCostService;
	
	
	/**
	 * 加号代表是高消费
	 */
	public static final String[] LOWCOST = {"-","<","低消费学生","lowcost","low_standard"};
	/**
	 * 异常类型
	 */
	public static final String Abnormal_Type = "lowcost";
	/**
	 * 高消费分析页面 shiroTag
	 */
	private static final String ShiroTag = Constant.ShiroTag_Xg + "stuLowCost";
	private List<String> getDeptDataList() {
		return getDeptDataList(null);
	}
	private List<String> getDeptDataList(String id) {
		return businessService.getDeptDataList(ShiroTag, id);
	}
	@Override
    public Map<String,Object> getStandardMap(String schoolYear,String termCode){
    	return stuHighCostService.getStandardMap(schoolYear,termCode,LOWCOST);
    }
	@Override
	public Map<String,Object> getCountByDept(String schoolYear,String termCode, Integer month,String column,Boolean asc,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getCountByDept(schoolYear,termCode, month,column,asc,getDeptDataList(),advancedParamList, LOWCOST);
	}
	@Override
	public Map<String,Object> getLastMonthData(List<AdvancedParam> advancedParamList,String schoolYear,String termCode, Integer month){
		return stuHighCostService.getLastMonthData(getDeptDataList(),advancedParamList,LOWCOST,schoolYear,termCode, month);
	}
	@Override
	public Map<String,Object> getTermByGrade(String schoolYear,String termCode,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getTermByGrade(schoolYear,termCode,LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public Map<String,Object> getYearByGrade(int start,int end,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getYearByGrade(start,end,LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public Map<String,Object> getTermBySex(String schoolYear,String termCode,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getTermBySex(schoolYear,termCode,LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public Map<String,Object> getNearLv(String xnxqList,String monthList,String deptid,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getNearLv(xnxqList, monthList, deptid, advancedParamList,  LOWCOST,Abnormal_Type);
	}
	@Override
	public Map<String,Object> getYearBySex(int start,int end,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getYearBySex(start,end,LOWCOST,getDeptDataList(),advancedParamList);
	}

	@Override
	public Map<String,Object> getTermByLoan(String schoolYear,String termCode,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getTermByLoan(schoolYear,termCode,LOWCOST,getDeptDataList(),advancedParamList);
	}

	@Override
	public Map<String,Object> getYearByLoan(int start,int end,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getYearByLoan(start,end,LOWCOST,getDeptDataList(),advancedParamList);
	}

	@Override
	public Map<String,Object> getTermBySubsidy(String schoolYear,String termCode,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getTermBySubsidy(schoolYear,termCode,LOWCOST,getDeptDataList(),advancedParamList);
	}

	@Override
	public Map<String,Object> getYearBySubsidy(int start,int end,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getYearBySubsidy(start,end,LOWCOST,getDeptDataList(),advancedParamList);
	}
	
	@Override
	public Map<String,Object> getTermByJm(String schoolYear,String termCode,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getTermByJm(schoolYear,termCode,LOWCOST,getDeptDataList(),advancedParamList);
	}

	@Override
	public Map<String,Object> getYearByJm(int start,int end,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getYearByJm(start,end,LOWCOST,getDeptDataList(),advancedParamList);
	}

	@Override
	public Map<String,Object> getGradeHistory(List<AdvancedParam> advancedParamList){
		return stuHighCostService.getGradeHistory(LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public Map<String,Object> getSexHistory(List<AdvancedParam> advancedParamList){
		return stuHighCostService.getSexHistory(LOWCOST,getDeptDataList(),advancedParamList);
	}

	@Override
	public Map<String,Object> getSubsidyHistory(List<AdvancedParam> advancedParamList){
		return stuHighCostService.getSubsidyHistory(LOWCOST,getDeptDataList(),advancedParamList);
	}

	@Override
	public Map<String,Object> getLoanHistory(List<AdvancedParam> advancedParamList){
		return stuHighCostService.getLoanHistory(LOWCOST,getDeptDataList(),advancedParamList);
	}
	
	@Override
	public Map<String,Object> getJmHistory(List<AdvancedParam> advancedParamList){
		return stuHighCostService.getSubsidyHistory(LOWCOST,getDeptDataList(),advancedParamList);
	}
	
	@Override
	public Map<String,Object> getTermByMeal(String schoolYear,String termCode,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getTermByMeal(schoolYear,termCode,LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public Map<String,Object> getYearByMeal(int start,int end,List<AdvancedParam> advancedParamList){
		return stuHighCostService.getYearByMeal(start,end,LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public Map<String,Object> getMealHistory(List<AdvancedParam> advancedParamList){
		return stuHighCostService.getSubsidyHistory(LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public Map<String, Object> getTermCountDept(String schoolYear, String termCode,
			List<AdvancedParam> advancedParamList){
		return stuHighCostService.getTermCountDept(schoolYear, termCode,LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public Map<String, Object> getYearCountDept(int start, int end,
			List<AdvancedParam> advancedParamList){
		return stuHighCostService.getYearCountDept(start, end,LOWCOST,getDeptDataList(),advancedParamList);
	}
	@Override
	public void exportData(String mc,String schoolYear,String termCode, Integer month, String pid,
			 List<AdvancedParam> advancedParamList,HttpServletResponse response){
		 stuHighCostService.exportData(mc,schoolYear,termCode, month, pid,LOWCOST,advancedParamList,response);
	}
	@Override
	public Map<String,Object> getExportData(String sendType,String mc,String schoolYear,String termCode, Integer month,String pid,List<AdvancedParam> advancedParamList,HttpServletRequest request){
		return stuHighCostService.getExportData(sendType,mc,schoolYear,termCode, month,pid,LOWCOST,advancedParamList,request);
	}
	@Override
	public Map<String,Object> getStuDetail(List<AdvancedParam> advancedParamList,Page page, 
			Map<String, Object> keyValue, List<String> fields){
		return stuHighCostService.getStuDetail(advancedParamList, page, keyValue, fields,LOWCOST);
	}
	@Override
	public List<Map<String,Object>> getStuDetailList(List<AdvancedParam> advancedParamList, 
			Map<String, Object> keyValue, List<String> fields){
		return stuHighCostService.getStuDetailList(advancedParamList, keyValue, fields, LOWCOST);
	}
	@Override
	public void excelAll(HttpServletRequest request,HttpServletResponse response) {
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
	public Map<String,Object> getCostCode(){
		String Abnormal = Abnormal_Type;
		String[] type = LOWCOST;
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("abnormal", Abnormal);
		map.put("type",type);
		return map;
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
	    List<Map<String,Object>> emailList=stuCostDao.queryEmailList();//得到所有院系的email

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
}
