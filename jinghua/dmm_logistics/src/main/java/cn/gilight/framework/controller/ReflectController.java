package cn.gilight.framework.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.gilight.framework.page.Page;
import cn.gilight.framework.uitl.ApplicationComponentStaticRetriever;
import cn.gilight.framework.uitl.common.ContextHolderUtils;
import cn.gilight.framework.uitl.common.DateUtils;
import cn.gilight.framework.uitl.common.ExportUtil;
import cn.gilight.framework.uitl.common.MapUtils;
import cn.gilight.framework.uitl.common.ReflectUtil;
import cn.gilight.framework.uitl.common.TypeUtil;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.syspermiss.permiss.entity.JobResultBean;
import com.jhnu.syspermiss.task.entity.PlanLogDetails;
import com.jhnu.syspermiss.task.service.PlanLogDetailsService;
import com.jhnu.syspermiss.task.service.impl.PlanLogDetailsServiceImpl;

@Controller
public class ReflectController {
	
	@RequestMapping(value="/common/nopermiss",method=RequestMethod.GET)
	public ModelAndView noPermiss(){
		ModelAndView mv=new ModelAndView("/nopermiss");
		return mv;
	}
	
	@ResponseBody	
	@RequestMapping(value="/common/getData",method=RequestMethod.POST)
	public Object getData(@RequestParam Map<String,Object> args){
		Map<String,Object> params=JSONObject.parseObject(args.get("params").toString());
		String beanName=MapUtils.getString(params, "beanName");
		String methodName=MapUtils.getString(params, "methodName"); 
		if(beanName!=null && methodName!=null && !"".equals(beanName) && !"".equals(methodName)){
			List<?> dataArray=(List<?>)params.get("dataArray");
			try {
				return this.getData(beanName, methodName, dataArray);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} 
		return new HashMap<String,Object>();
	}
	
	@ResponseBody
	@RequestMapping(value="/task/job/execute",method=RequestMethod.POST)
	public JobResultBean jobExecute(){
		HttpServletRequest request=ContextHolderUtils.getRequest();
		String beanName=request.getParameter("beanName");
		String methodName=request.getParameter("methodName");
		String planLogDetailId=request.getParameter("planLogDetailId");
		PlanLogDetailsService pls=PlanLogDetailsServiceImpl.getInstance();
		PlanLogDetails planLogDetails=new PlanLogDetails();
		JobResultBean jrb=new JobResultBean();
		if(pls.isCheck(planLogDetailId)){
			try{
				planLogDetails.setId(planLogDetailId);
				planLogDetails.setIsYes(2);
				planLogDetails.setResultDesc("已进入系统，正在执行...");
				planLogDetails.setEndTime("");
				pls.updateOrInsert(planLogDetails);
			}catch(Exception e){
				jrb.setIsTrue(false);
				jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
				return jrb;
			}
			
			try{
				jrb=(JobResultBean)this.getData(beanName, methodName, null);
			}catch(Throwable e){
				jrb.setIsTrue(false);
				jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
			}
			try{
				planLogDetails.setId(planLogDetailId);
				planLogDetails.setEndTime(DateUtils.SSS.format(new Date()));
				planLogDetails.setIsYes(jrb.getIsTrue()?1:0);
				planLogDetails.setResultDesc(jrb.getMsg());
				pls.updateOrInsert(planLogDetails);
			}catch(Exception e){
				jrb.setIsTrue(false);
				jrb.setMsg(e.getCause()==null?e.getMessage():e.getCause().toString());
				return jrb;
			}
		}else{
			jrb.setIsTrue(false);
			jrb.setMsg("验证失败");
		}
		return jrb;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/common/exportPage",method=RequestMethod.GET)
	public Object exportPage(HttpServletRequest req, HttpServletResponse resp,@RequestParam Map<String,Object> args) throws UnsupportedEncodingException{
		String asgString=args.get("params").toString();
		if(asgString.equals(new String(asgString.getBytes("ISO-8859-1"), "ISO-8859-1"))){
			asgString = new String(asgString.getBytes("ISO-8859-1"),"UTF-8");
		}
		Map<String,Object> params=JSONObject.parseObject(asgString);
		String beanName=MapUtils.getString(params, "beanName");
		String methodName=MapUtils.getString(params, "methodName"); 
		String title=MapUtils.getString(params, "title");
		List<?> dataArray=(List<?>)params.get("dataArray");
		List<String> titles=(List<String>)params.get("titles");
		List<String> titleCodes=(List<String>)params.get("titleCodes");
		
		Page page=new Page();
		try {
			page = (Page)this.getData(beanName, methodName, dataArray);
		} catch (Throwable e2) {
			e2.printStackTrace();
		}
		List<Map<String,Object>> xsList = page.getResultList();
		HSSFWorkbook hw = ExportUtil.getHSSFWorkbookByCodes(titles,titleCodes, "sheet", xsList);
		String fileName=title+".xls";
		fileName = new String(fileName.getBytes("UTF-8"), "ISO_8859_1");
		HttpServletResponse response = resp;   
	    response.setContentType("application/vnd.ms-excel");   
		response.setHeader("Content-disposition", "attachment;filename=" +  fileName);
	//    response.setCharacterEncoding("utf-8"); 
	    OutputStream ouputStream;
		try {
			ouputStream = response.getOutputStream();
			hw.write(ouputStream);   
		    ouputStream.flush();   
		    ouputStream.close();  
		} catch (IOException e) {
			e.printStackTrace();
		}   
	     
		return "customExport";
 	}
	
	
	private Object getData(String beanName,String methodName,List<?> params) throws Throwable{
		Object o=ApplicationComponentStaticRetriever.getComponentByItsName(beanName);
		if(o==null){
			return null;
		}
		Method[] ms=o.getClass().getDeclaredMethods();
		for(Method m:ms){
			if(methodName.equals(m.getName()) && (params!=null?params.size()==m.getParameterTypes().length:m.getParameterTypes().length==0)){
					List<Object> objsList=new ArrayList<Object>();
					Class<?>[] clazz=m.getParameterTypes();
					if(params!=null){
						for(int i=0;i<params.size();i++){
 							Class<?> type=params.get(i).getClass();
							
							if(TypeUtil.isBaseType(type) || TypeUtil.isPackageType(type)){
								objsList.add(ReflectUtil.basicConvert(clazz[i],params.get(i)));
							}
							else if(((params.get(i) instanceof java.util.List))){
								objsList.add(params.get(i));
							}
							else if(params.get(i) instanceof java.util.Map){
								try {
									objsList.add(ReflectUtil.toObject(clazz[i], params.get(i)));
								} catch (InstantiationException e) {
									objsList.add(params.get(i));
								}
							}
							else{
								objsList.add(params.get(i));
							}
						}
					}
					if(m.getReturnType()!=null && !m.getReturnType().toString().equals("void")){
						return m.invoke(o, objsList.toArray());
					}else{
						m.invoke(o, objsList.toArray());
						return new HashMap<String,Object>();
					}
			}
		}
		return null;
	}
	
	
}
