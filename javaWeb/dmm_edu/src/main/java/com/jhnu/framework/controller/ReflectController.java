package com.jhnu.framework.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jhnu.framework.util.ApplicationComponentStaticRetriever;
import com.jhnu.framework.util.common.ContextHolderUtils;
import com.jhnu.framework.util.common.MapUtils;
import com.jhnu.framework.util.common.ReflectUtil;
import com.jhnu.framework.util.common.TypeUtil;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/common")
public class ReflectController {
	//@RequestParam(value="args") Map args
	
	@ResponseBody	
	@RequestMapping(value="/getData",method=RequestMethod.POST)
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
	@RequestMapping(method=RequestMethod.GET, value="/nopermiss")
	public ModelAndView nopermiss(){
		ModelAndView mv=new ModelAndView("/common/error/nopermiss");
		return mv;
	}
	@RequestMapping(method=RequestMethod.GET, value="/errorUser")
	public ModelAndView errorUser(){
		ModelAndView mv=new ModelAndView("/common/error/errorUser");
		return mv;
	}
	@ResponseBody
	@RequestMapping(value="/getDatas",method=RequestMethod.POST)
	public Object getData(){
		HttpServletRequest request=ContextHolderUtils.getRequest();
		String beanName=request.getParameter("beanName");
		String methodName=request.getParameter("methodName");
		List<?> params=JSONObject.parseArray(request.getParameter("data"),Object.class);
		return this.getData(beanName, methodName, params);
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
