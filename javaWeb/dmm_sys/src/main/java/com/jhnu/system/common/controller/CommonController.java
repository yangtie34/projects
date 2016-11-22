package com.jhnu.system.common.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.framework.entity.JobResultBean;
import com.jhnu.framework.spring.ApplicationComponentStaticRetriever;
import com.jhnu.system.task.entity.PlanLogDetails;
import com.jhnu.system.task.service.PlanLogDetailsService;
import com.jhnu.util.common.ContextHolderUtils;
import com.jhnu.util.common.DateUtils;
import com.jhnu.util.common.MapUtils;
import com.jhnu.util.common.ReflectUtil;
import com.jhnu.util.common.TypeUtil;

@Controller
public class CommonController {
	
	@Autowired
	private PlanLogDetailsService PlanLogDetailsService;
	
	
	@ResponseBody	
	@RequestMapping(value="/common/getData",method=RequestMethod.POST)
	public Object getData(@RequestParam Map<String,Object> args){
		Map<String,Object> params=JSONObject.parseObject(args.get("params").toString());
		String beanName=MapUtils.getString(params, "beanName");
		String methodName=MapUtils.getString(params, "methodName"); 
		if(beanName!=null && methodName!=null && !"".equals(beanName) && !"".equals(methodName)){
			List<?> dataArray=(List<?>)params.get("dataArray");
			return this.getData(beanName, methodName, dataArray);
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
		PlanLogDetails planLogDetails=new PlanLogDetails();
		JobResultBean jrb=new JobResultBean();
		if(PlanLogDetailsService.isCheck(planLogDetailId)){
			try{
				planLogDetails=PlanLogDetailsService.getPlanLogDetailsById(planLogDetailId);
				planLogDetails.setId(planLogDetailId);
				planLogDetails.setIsYes(2);
				planLogDetails.setResultDesc("已进入系统，正在执行...");
				planLogDetails.setEndTime("");
				PlanLogDetailsService.updateOrInsert(planLogDetails);
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
				PlanLogDetailsService.updateOrInsert(planLogDetails);
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
	
	
	private Object getData(String beanName,String methodName,List<?> params){
		Object o=ApplicationComponentStaticRetriever.getComponentByItsName(beanName);
		if(o==null){
			return null;
		}
		Method[] ms=o.getClass().getDeclaredMethods();
		for(Method m:ms){
			if(methodName.equals(m.getName()) && (params!=null?params.size()==m.getParameterTypes().length:m.getParameterTypes().length==0)){
				try{
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
				}catch(Throwable e){
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}
	
	
}
