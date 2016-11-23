package com.jhkj.mosdc.output.process.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.service.impl.BaseServiceImpl;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.output.dao.OutputCommonDao;
import com.jhkj.mosdc.output.po.FunComponent;
import com.jhkj.mosdc.output.po.FunctionArea;
import com.jhkj.mosdc.output.po.PageData;
import com.jhkj.mosdc.output.po.TemplateData;
import com.jhkj.mosdc.output.process.OutputEntrance;
import com.jhkj.mosdc.output.util.ReflectInvoke;

/***
 * 输出页面统一入口函数实现。
 * 
 * @Comments: Company: 河南精华科技有限公司 Created by zhangzg(vbxnt521_edu@163.com)
 * @DATE: 2012-11-6
 * @TIME: 上午10:34:51
 */
public class OutPutEntranceImpl extends BaseServiceImpl implements
		OutputEntrance {

	private OutputCommonDao commonDao;

	public void setCommonDao(OutputCommonDao commonDao) {
		this.commonDao = commonDao;
	}

	@Override
	public String queryData(String params) {
		String pageId = "";//页面id
		String templateId = "";//模板id
		String requestType ="";//请求类型（联动，下钻）
		JSONObject paramsObj = null;
		JSONObject outputParams = null;
		String resultStr ="";// 要被返回的结果集。
		try {
			paramsObj = JSONObject.fromObject(params);
			outputParams = JSONObject.fromObject(paramsObj.get("output"));//获取output参数对象。
			pageId = outputParams.containsKey("pageId") ? outputParams.getString("pageId") : "";// 得到页面id
			templateId = outputParams.containsKey("templateId") ? outputParams.getString("templateId") : "";//得到模板id
			requestType = outputParams.containsKey("requestType") ? outputParams.getString("requestType") : "";//得到前台请求类型。
			if("".equals(templateId)&&"".equals(requestType)){//多tab页的初始化时需要加上对requestType的判断。
				// 初始化页面的请求。
				resultStr = getInitData(pageId,"");
			}else if("linkage".equals(requestType)){
				// 联动操作处理。
				JSONObject global = JSONObject.fromObject(outputParams.getString("globalParams"));
				PageData pd = new PageData(Long.parseLong(pageId));
				TemplateData td = new TemplateData(Long.parseLong(templateId), "","");
				td.setComponents(getLinkageData(global,outputParams.getJSONArray("components")));
				List<TemplateData> tds = new ArrayList<TemplateData>();
				tds.add(td);
				pd.setTemplates(tds);
				resultStr = Struts2Utils.objects2Json(pd);
			}else if("godown".endsWith(requestType)){
				// 下钻弹窗处理。
				/*解析前台传递的参数，获取文本统计功能id，并查询文本功能对象*/
				JSONObject godownParams = JSONObject.fromObject(outputParams.getString("params"));
				String wbid = (String)godownParams.get("wbid");
				List list = commonDao.queryWbgnByWbid(wbid);
				// 获取分页参数信息
				Object[] objSx = (Object[])list.get(0);
				String beanId = objSx[6].toString().split("\\?")[0];// spring容器中bean的id
				String methodName = objSx[6].toString().split("\\?")[1];
				
				Object bean = getServiceByBeanId(beanId);
				// 反射调用gridurl中对应的处理类和方法，并将分页参数传递。
				Object resultObj = ReflectInvoke.reflectInvokeInitData(bean, methodName, new Object[]{godownParams.toString()});
				resultStr = Struts2Utils.objects2Json(resultObj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultStr;
	}
	/***
	 * 联动类型的请求的处理方法。
	 * @param globalParams 公共参数
	 * @param compoParams 组件对象列表。
	 * @return
	 */
	private List<FunComponent> getLinkageData(JSONObject globalParams,JSONArray composParams){
		List<FunComponent> fcs = new ArrayList<FunComponent>();
		try{
			// 循环各个组件将公共联动参数附加到组件参数中
			for(Object obj : composParams){
				JSONObject compo = JSONObject.fromObject(obj);
				// 将公共参数组合到组件列表中。
				String compoId = compo.getString("componentId");
				compo.put("globalParams", globalParams);
				// 查询dao，获取组件数据.
				Object[] aCompo = (Object[])commonDao.queryCompoById(compoId).get(0);
				String[] serviceMethod = aCompo[5].toString().split("\\?");
				Object bean = getServiceByBeanId(serviceMethod[0]);
				// 封装组件对象。
				String componentDetall = aCompo[1]==null?"":aCompo[1].toString();//组将详述
				String name = aCompo[2]==null?"":aCompo[2].toString();// 组件名称
				String style = aCompo[3]==null?"":aCompo[3].toString();// 组件样式
				String bz = aCompo[6]==null?"":aCompo[6].toString();// 备注
				String compoType = aCompo[4]==null?"":aCompo[4].toString();// 组件类型
				FunComponent fc = new FunComponent(Long.parseLong(compoId), compoType, style, null, name, componentDetall, bz);
				// 反射调用。
				fc = (FunComponent)ReflectInvoke.reflectInvokeInitData(bean, serviceMethod[1],new Object[] {fc,compo.toString()});
				fcs.add(fc);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return fcs;
	}
	/***
	 * 下钻类型的请求的处理方法。
	 * @param params
	 * @return
	 */
	private String getGodownData(String params){
		String result ="";// 要被返回的结果。
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 根据定义好的页面数据对象和页面id获取页面初始化数据的json格式。
	 * 方法思路：
	 * 	for(dao查询的结果集){
	 * 		if(如果模板存在){
	 * 			if(缓存记录器中的模板书大于等于1，且该条记录代表的模板是第一次放进来。){
	 * 				>>>>循环下一条记录。
	 * 			}
	 * 			if(该记录的统计区是否在模板中){
	 * 				if(该记录表示的统计组件是否在统计区中){
	 * 					>>>>循环吓一条记录。
	 * 				}else{
	 * 					不存在，创建一个功能组件对象添加到统计区对象中。
	 * 				}
	 * 			}else{
	 * 				不存在，创建一个统计区对象添加到模板中。
	 * 			}
	 * 		}else{
	 * 			if(该记录代表的模板不在页面对象，且缓存记录器中的模板数大于1){
	 * 				①创建一个模板对象，放置到页面数据对象中。
	 * 				②>>>>循环下一条记录。
	 * 			}
	 * 			不存在，创建一个模板对象添加页面数据对象中。
	 * 		}
	 * 
	 * 	}
	 * 	
	 * @param pd
	 * @param pageId
	 * @return
	 */
	private String getInitData(String pageId,String templateId){
		String result="";// 返回的结果，json格式字符串。
		PageData pd = new PageData(Long.parseLong(pageId),"",new ArrayList<TemplateData>()); // 创建一个页面数据对象
		// 模板id缓存器，便于循环判断该模板是否已存在。
		List<String> tempIds = new ArrayList<String>();
		// 统计区id缓存器，便于循环结果集判断该统计区是否已存在。
		List<String> funcIds = new ArrayList<String>();
		// 功能组件id缓存器，用于循环判断该功能组件是否已存在。
		List<String> compIds = new ArrayList<String>();
		try{
			pd.setId(Long.parseLong(pageId));
			
			List<Object> allList = new ArrayList<Object>(); 
			if(templateId!=null&&!"".equals(templateId)){
				allList = commonDao.queryPageDataByPageIdAndTempId(pageId,templateId);
			}else{
				allList = commonDao.queryPageDataByPageId(pageId);
			}
			for(Object obj : allList){
				Object[] temp = (Object[])obj;
				// 将结果集中为null的值修改为空字符串
				for(int i =0;i<temp.length;i++){
					if(temp[i]==null){
						temp[i] = "";
					}
				}
				if(isIn(tempIds,temp[2].toString())){
					// 如果是第二个tab页的话， 暂时不获取数据。*****这里的判断不完善会导致，只有一个组件数据。*****
					if(tempIds.size()>1&&!tempIds.get(0).equals(temp[2].toString())){
						continue;//----------------------------循环下一行记录。
					}
					// 获取此模板数据对象。
					TemplateData td = pd.getTemplates().get(tempIds.indexOf(temp[2].toString()));
					if(isIn(funcIds,temp[5].toString())){
						FunctionArea fa = td.getPositionData().get(funcIds.indexOf(temp[5].toString()));
						if(isIn(compIds,temp[14].toString())){
							continue;
						}else{
							//FunComponent fc = new FunComponent(temp[14].toString(), temp[12].toString(), style, null, name, componentDetall, bz);
							FunComponent fc = createFunCompData(temp[14].toString(),temp[12].toString(),temp[13].toString());
							fa.getComps().add(fc);
							compIds.add(temp[14].toString());
						}
					}else{
						FunctionArea fa = createFuncAreaData(temp[5].toString(),temp[7].toString(),temp[16].toString(),temp[14].toString(),temp[12].toString(),temp[13].toString());
						td.getPositionData().add(fa);
						// --将模板id放在缓存器中。并将组件缓存器置空。
						funcIds.add(temp[5].toString());
						compIds = new ArrayList<String>();
					}
				}else{
					// 如果是第二个或第三个tab页的话 暂时不获取数据。
					if(tempIds.size()>=1){ //----------------------------循环下一行记录。
						TemplateData td = new TemplateData(Long.parseLong(temp[2].toString()),"","");
						pd.getTemplates().add(td);
						tempIds.add(temp[2].toString());
						continue;
					}
					// 创建一个模板数据对象。
					TemplateData td = createTemplateObj(temp[2].toString(),temp[3].toString(),temp[4].toString(),temp[5].toString(),temp[7].toString(),temp[16].toString(),
							temp[14].toString(),temp[12].toString(),temp[13].toString());
					// 设置页面标题。（设置多次也无所谓，因为每次他们的值都一样。）
					pd.setTitle(temp[1].toString());
					// 将创建的模板对象放置到页面模板列表中。
					pd.getTemplates().add(td);
					// --将模板id放在缓存器中。
					tempIds.add(temp[2].toString());
					funcIds.add(temp[5].toString());
					compIds.add(temp[14].toString());
				}
			}
			addCompnentsToTemp(pd.getTemplates());
			result = Struts2Utils.objects2Json(pd);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 将模板数据再行封装，设置在他内部的所有组件列表数据。
	 * @param tds
	 */
	private void addCompnentsToTemp(List<TemplateData> tds){
		for(TemplateData td : tds){
			List<FunComponent> comps = new ArrayList<FunComponent>();
			if(td.getPositionData()!=null&&td.getPositionData().size()>0){
				for(FunctionArea fa : td.getPositionData()){
					for(FunComponent fc : fa.getComps()){
						comps.add(fc);
					}
				}
			}
			td.setComponents(comps);
		}
	}
	/**
	 * 获取模板数据对象。
	 * 
	 * @param id
	 * @param templateTitle
	 * @param templateType
	 * @param funAreaId
	 * @param funAreaTitle
	 * @param compoId
	 * @param compoType
	 * @param serviceName
	 * @return
	 */
	private TemplateData createTemplateObj(String id, String templateTitle,
			String templateType, String funAreaId, String funAreaTitle,String helpInfo,
			String compoId, String compoType, String serviceName) {
		TemplateData td = new TemplateData(Long.parseLong(id), templateTitle,templateType,new ArrayList<FunctionArea>());
		try {
			td.getPositionData().add(createFuncAreaData(funAreaId,funAreaTitle,helpInfo,compoId,compoType,serviceName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return td;
	}
	/**
	 * 获取统计区数据对象。
	 * @param funAreaId
	 * @param funAreaTitle
	 * @param compoId
	 * @param compoType
	 * @param serviceName
	 * @return
	 */
	private FunctionArea createFuncAreaData(String funAreaId, String funAreaTitle,String helpInfo,
			String compoId, String compoType, String serviceName){
		FunctionArea fa = new FunctionArea(Long.parseLong(funAreaId),funAreaTitle,helpInfo,new ArrayList<FunComponent>());
		try{
			fa.getComps().add(createFunCompData(compoId,compoType,serviceName));
		}catch(Exception e){
			e.printStackTrace();
		}
		return fa;
	}
	/**
	 * 获取功能组件数据对象。
	 * @param compoId
	 * @param compoType
	 * @param serviceName 参数格式为serviceName?methodName
	 * @return 组件数据对象。
	 */
	private FunComponent createFunCompData(String compoId,String compoType,String serviceName){
		FunComponent fc = new FunComponent();
		try{
			String[] serviceMethod = serviceName.split("\\?");
			String methodName="";
			if(serviceMethod.length==2){// 说明后台serviceName字段配置的名称没有方法名信息。
				methodName =serviceMethod[1];
			}else{
				methodName ="initData";// 如果不给出方法名则调用默认方法initData。
			}
			// 根据id获取并封装统计功能组件对象。
			List result = commonDao.queryCompoById(compoId);
			Object[] compoSx = (Object[])result.get(0);
			String componentDetall = compoSx[1]==null?"":compoSx[1].toString();//组将详述
			String name = compoSx[2]==null?"":compoSx[2].toString();// 组件名称
			String style = compoSx[3]==null?"":compoSx[3].toString();// 组件样式
			String bz = compoSx[6]==null?"":compoSx[6].toString();// 备注
			fc = new FunComponent(Long.parseLong(compoId), compoType, style, null, name, componentDetall, bz);
			
			// 反射调用，并设置fc的显示数据。
			Object bean = getServiceByBeanId(serviceMethod[0]);
//			Map compDataStr = reflectInvokeInitData(bean, methodName,new Object[] {fc,new String()});
//			fc.setDisplayData(compDataStr);
			fc = (FunComponent)ReflectInvoke.reflectInvokeInitData(bean, methodName,new Object[] {fc,"{init:true}"});
		}catch(Exception e){
			e.printStackTrace();
		}
		return fc;
	}
	/**
	 * 查看id是否在list中。
	 * @param list
	 * @param id
	 * @return
	 */
	private boolean isIn(List<String> list ,String id){
		boolean isin = false;
		try{
			for(String str : list){
				if(str!=null&&str.equals(id)){
					isin = true;
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return isin;
	}
	/**
	 * 根据功能组件表中的serviceName字段获取它的对象。
	 * 此serviceName 即为spring容器中的bean 的id。
	 * @param beanId=serviceName
	 * @return
	 */
	private Object getServiceByBeanId(String beanId) {
		return ApplicationComponentStaticRetriever.getComponentByItsName(beanId);
	}
}
