package com.jhkj.mosdc.framework.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.nobject.common.js.JSONUtils;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.bean.PageParam;
import com.jhkj.mosdc.framework.po.TsStsx;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.util.ExportUtil;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.MenuNode;
import com.jhkj.mosdc.permission.po.MenuTree;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.jhkj.mosdc.permission.service.PermissService;
import com.jhkj.mosdc.permission.util.UserPermissionUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 在控制层建立本基类,为以后扩展
 *
 * @author tianfei
 * @version 1.0 2012-1-30 15:15
 */
@SuppressWarnings("rawtypes")
public class BaseAction extends ActionSupport {
    /**
     * long  serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(BaseAction.class);
    //获取当前用户信息
 	private  UserInfo userInfo;
 	//session管理
//    private Map<String, Object> session;
    private String fileName;
    private InputStream excelStream;
    //
    private PermissService permissService;
 
    public InputStream getExcelStream() {
        return excelStream;
    }

    public void setExcelStream(InputStream excelStream) {
        this.excelStream = excelStream;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public PermissService getPermissService() {
		return permissService;
	}

	public void setPermissService(PermissService permissService) {
		this.permissService = permissService;
	}
	
	/*public Map<String, Object> getSessionMap() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}*/

	/**
     * 通过实体名对相应的表进行查询，查询出所有的记录
     */

	public void queryTableContent() {
        BaseService baseService = (BaseService) ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
        HttpServletRequest request = ServletActionContext.getRequest();
        String entityName = (String) request.getParameter("entityName");
        String limitStr = request.getParameter("limit");
        String startStr = request.getParameter("start");
        int limit = null == limitStr ? 0 : new Integer(limitStr).intValue();
        int start = null == startStr ? 0 : new Integer(startStr).intValue();
        try {
			Map requestMap = (Map) ActionContext.getContext().getParameters();
            List listTmp = this.requestMapToList(requestMap, entityName);
            String[] paramNames = new String[listTmp.size()];
            String[] values = new String[listTmp.size()];
            for (int i = 0; i < listTmp.size(); i++) {
                String[] aa = (String[]) listTmp.get(i);
                paramNames[i] = aa[0];
                values[i] = aa[1];
            }
            //likeParams
            List listLike = this.requestMapToList(requestMap, "likeParams");
            String[] likeParamNames = new String[listLike.size()];
            String[] likeValues = new String[listLike.size()];
            for (int i = 0; i < listLike.size(); i++) {
                String[] aa = (String[]) listLike.get(i);
                likeParamNames[i] = aa[0];
                likeValues[i] = aa[1];
            }
            //orderBy
            List listOrderBy = this.requestMapToList(requestMap, "orderBy");
            String[] orderByParamNames = new String[listOrderBy.size()];
            String[] orderByValues = new String[listOrderBy.size()];
            for (int i = 0; i < listOrderBy.size(); i++) {
                String[] aa = (String[]) listOrderBy.get(i);
                orderByParamNames[i] = aa[0];
                orderByValues[i] = aa[1];
            }
            PageParam pageParam = new PageParam(start, limit);
            List list = baseService.queryTableContent(entityName, paramNames, values, likeParamNames, likeValues, orderByParamNames, orderByValues, pageParam);
            //String json = Struts2Utils.list2json(list, entityName);
            String json = Struts2Utils.list2json(list);
            int recordCount = pageParam.getRecordCount();
            String tmp = "{success:true,\"data\":" + json + " ,\"count\":\"" + recordCount + "\"}";
            Struts2Utils.renderJson(tmp);
        } catch (Exception e) {
            logger.error(e);
            Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_FALSE);
        } finally {

        }
    }


    @SuppressWarnings("unchecked")
	public void queryTableContentByServerName() {
        BaseService baseService = (BaseService) ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
        HttpServletRequest request = ServletActionContext.getRequest();
        String entityName = (String) request.getParameter("entityName");
        String limitStr = request.getParameter("limit");
        String startStr = request.getParameter("start");
        String serviceName = request.getParameter("serviceName");
        String methodName = request.getParameter("methodName");
        int limit = null == limitStr ? 0 : new Integer(limitStr).intValue();
        int start = null == startStr ? 0 : new Integer(startStr).intValue();
        try {
//			Map requestMap = (Map) ActionContext.getContext().getParameters();
//			List listTmp = this.requestMapToList(requestMap, entityName);
//			String [] paramNames= new String [listTmp.size()] ;
//			String [] values= new String [listTmp.size()] ;
//			for(int i=0;i<listTmp.size();i++){
//				String [] aa = (String [])listTmp.get(i);
//				paramNames[i]=aa[0];
//				values[i]=aa[1];
//			}
//			//likeParams
//			List listLike = this.requestMapToList(requestMap, "likeParams");
//			String [] likeParamNames= new String [listLike.size()] ;
//			String [] likeValues= new String [listLike.size()] ;
//			for(int i=0;i<listLike.size();i++){
//				String [] aa = (String [])listLike.get(i);
//				likeParamNames[i]=aa[0];
//				likeValues[i]=aa[1];
//			}
//			//orderBy
//			List listOrderBy = this.requestMapToList(requestMap, "likeParams");
//			String [] orderByParamNames= new String [listOrderBy.size()] ;
//			String [] orderByValues= new String [listOrderBy.size()] ;
//			for(int i=0;i<listOrderBy.size();i++){
//				String [] aa = (String [])listOrderBy.get(i);
//				orderByParamNames[i]=aa[0];
//				orderByValues[i]=aa[1];
//			}
            PageParam pageParam = new PageParam(start, limit);
//			List list =  baseService.queryTableContent(entityName, paramNames, values, likeParamNames, likeValues,orderByParamNames,orderByValues,pageParam);
//			String json = Struts2Utils.list2json(list,entityName);

            //获取服务对象
            Object serviceBean = ApplicationComponentStaticRetriever.getComponentByItsName(serviceName);
            Class cls = serviceBean.getClass();
            Class[] paraTypes = new Class[]{PageParam.class};
            //反射方法
            Method method = cls.getMethod(methodName, paraTypes);
            Object[] paraValues = new Object[]{pageParam};
            //调用方法
            Object list = method.invoke(serviceBean, paraValues);
            String json = Struts2Utils.list2json((List) list, entityName);
            int recordCount = pageParam.getRecordCount();
            String tmp = "{success:true,\"data\":" + json + " ,\"count\":\"" + recordCount + "\"}";
            Struts2Utils.renderJson(tmp);
        } catch (Exception e) {
            logger.error(e);
            Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_FALSE);
        } finally {

        }
    }


    /**
     * 通过实体名对相应的表进行查询，查询出所有的记录
     */

    @SuppressWarnings("unchecked")
	public void queryViewContent() {
        HttpServletRequest request = ServletActionContext.getRequest();
        ActionContext act = ActionContext.getContext();
        Map requestMap = (HashMap) act.getApplication().get(SysConstants.REQUEST_SERVICES);
        try {
            //请求名称
            String requestName = (String) request.getParameter("requestName");
            //参数
            Map serviceMap = (HashMap) requestMap.get(requestName);
            String service = (String) serviceMap.get(SysConstants.REQUEST_SERVICE_NAME);
            String methodName = (String) serviceMap.get(SysConstants.REQUEST_SERVICE_METHOD);
            //获取服务对象
            Object serviceBean = ApplicationComponentStaticRetriever.getComponentByItsName(service);
            Class cls = serviceBean.getClass();
            Class[] paraTypes = new Class[]{String.class};
            //反射方法
            Method method = cls.getMethod(methodName, paraTypes);
            Object[] paraValues = new Object[]{""};
            //调用方法
            Object json = method.invoke(serviceBean, paraValues);
            Struts2Utils.objects2Json(json);
        } catch (Exception e) {
            logger.error(e);
            Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_FALSE);
        } finally {

        }
    }


    /**
     * 搜索查询字段，转换到list对象
     *
     * @param requestMap map对象
     * @param entityName 实体名称
     * @return
     */

    @SuppressWarnings("unchecked")
    private List requestMapToList(Map requestMap, String entityName) throws Exception {
        Set set = requestMap.keySet();
        List list = new ArrayList();
        for (Iterator it = set.iterator(); it.hasNext();) {
            String tmp = (String) it.next();
            if (null != tmp && !"".equals(tmp.trim())) {
                if (tmp.trim().startsWith(entityName)) {
                    String[] name = tmp.trim().split("\\.");
                    if (null != name && name.length >= 2) {
                        String[] value = (String[]) requestMap.get(tmp);
                        String sx = tmp.substring(entityName.length() + 1, tmp.length());
                        String[] tmp2 = new String[]{sx, Struts2Utils.isoToUTF8(value[0])};
                        list.add(tmp2);
                    }
                }
            }
        }
        return list;
    }
    
    /**
     * 统一的请求入口方法
     * @throws Exception 
     */
    @SuppressWarnings({ "unchecked", "unused" })
	public void queryByComponents() throws Exception {
        ActionContext act = ActionContext.getContext();
        //获取当前的会话
        HttpServletRequest request = ServletActionContext.getRequest(); 
        //判断当前会话的ＩＤ是否存在
        /*boolean haveSession = request.isRequestedSessionIdValid();
        //获取当前会话的开始时间
        long nowTime = request.getSession().getCreationTime();
        //获取会话的时长
        int s =request.getSession().getMaxInactiveInterval();
        //获取会话最后一次请求时间
        long lastTime = request.getSession().getLastAccessedTime();
        Date date = new Date(nowTime);
        long nowT = date.getTime()/1000;
        date = new Date(lastTime);
        long lastT = date.getTime()/1000;*/
//        if((nowT-lastT)>s){
//        	request.
//        };
//        System.out.println(nowT+"QQQQQ"+ lastT+"YYYYYYYYYYYYY"+(lastT-nowT) +"OOOOOOOOOOOO"+ s+":::::::::"+haveSession);
        //获取当前用户信息
         userInfo = (UserInfo) act.getSession().get(SysConstants.SESSION_USERINFO);
         userInfo = new UserInfo();
        //判断用户信息是否为空，为空时，返回信息提示重新登录．
        if(userInfo == null ){
        	Struts2Utils.renderJson("{success:false,errorInfo:'登录超时，请重新登录！'}");
        }else{
        	Map requestMap = (HashMap) act.getApplication().get(SysConstants.REQUEST_SERVICES);
        	
	        Map requestParams = request.getParameterMap();
	        String[] type = (String[]) requestParams.get("requestType");
	        String requestType = null;
	        if(type != null){
	        	requestType = type[0];
	        }
	        String requestParamFilter = "";
			try {
				requestParamFilter = Struts2Utils.requestParamFilter(requestParams);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
	        String[] componentList = (String[]) requestParams.get("components");
//	        boolean checkFlag = this.checkMenuPermiss("1001000000005977", "1001000000005978");
	        String components = componentList[0];
	        //处理json
	        JSONObject obj = JSONObject.fromObject(components);
	        StringBuffer outJson = new StringBuffer();
	        String tmpJson = "";
	        //代码重构if eles去掉
	        StringBuffer jsons = new StringBuffer();
	        Iterator it = obj.keys();
	        boolean flag = false;
	        String component = "";
	        while(it.hasNext()){
	        	component = (String) it.next();
	        	JSONObject obj1 = JSONObject.fromObject(obj.getString(component));
	        	//请求名称
	        	String requestName = obj1.getString("request");
	                 String paramsStr = obj1.getString("params");
	        	String requsetParams = obj1.getString("params").replaceFirst("\\{", "");
	        	//把前台传过来所有参数合并为一个对象
	        	String str = "{ ";
	        	try {
	                 if(!"".equals(requestParamFilter)){
	                	 requsetParams = requestParamFilter + requsetParams;
	                 }
	                 requsetParams = str + requsetParams;
	                 /**
	                  * 判断是否有访问当前service或process的权限
	                  */
	                 //获取前台传入的参数
	                 JSONObject map = JSONObject.fromObject(requsetParams);
	                 if(requestType == null){
		                 //菜单ID
		                 String menuId = null; //map.get("menuId").toString();
		                 //按钮ID
		                 String buttonId = null;//map.get("buttonId").toString();
		                 //判断菜单Id和按钮ID是否为空
		                 if(menuId != null && buttonId != null){
		                	 //检查menuId和buttonId是否存在此权限
		                	 boolean menuFlag = checkMenuPermiss(menuId,buttonId);
		                	 if(menuFlag == false){
		                		 Struts2Utils.renderJson("{success:false,errorInfo:'当前功能无权限，请访问其它功能！'}");
		                	 };
		                 };
	                 }
	                 if(map.containsKey("htmleditFlag")){
	                	 String htmleditFlag = map.getString("htmleditFlag");
	                	 map.remove("htmleditFlag");
	                	 if(null!=htmleditFlag&&"true".equals(htmleditFlag)){
	                		 if(map.containsKey("requestType")){
	                			 map.remove("requestType");
	                		 }
//	                		 if(map.containsKey("menuId")){
//	                			 map.remove("menuId");
//	                		 }
//	                		 if(map.containsKey("buttonId")){
//	                			 map.remove("buttonId");
//	                		 }
	                		 requsetParams = map.toString();
	                	 }else{
	                		 requsetParams = Struts2Utils.getParams(map);
	                	 }
	                 }else{
	                	 requsetParams = Struts2Utils.getParams(map);
	                 }
	                 //添加针对service的处理，请求一部分将会直接被转发到对应的bean以及方法,原有的保持不变
	                 String service = null;
	                 String methodName = null;
	                 if(requestName.contains("?")){
	                	 String [] beanAndMethod = StringUtils.split(requestName, "?");
	                	 service = beanAndMethod[0];
	                	 methodName = beanAndMethod[1];
	                 }else{
	                	 Map serviceMap = (HashMap) requestMap.get(requestName);
		                 service = (String) serviceMap.get(SysConstants.REQUEST_SERVICE_NAME);
		                 methodName = (String) serviceMap.get(SysConstants.REQUEST_SERVICE_METHOD);
	                 }
	                 
	                 //获取服务对象
	                 Object serviceBean = ApplicationComponentStaticRetriever.getComponentByItsName(service);
	                 Class cls = serviceBean.getClass();
	                 Class[] paraTypes = new Class[]{String.class};
	                 //反射方法
	                 Method method = cls.getMethod(methodName, paraTypes);
	                 String[] paraValues = new String[]{requsetParams};
	                 Object json = method.invoke(serviceBean, paraValues);
	                 //调用方法
	                 if (null != json) {
	                	 //table单一请求数据格式
	                     if (requsetParams.contains("singleReturnNoComponent") && obj.size() == 1) {
	                    	 flag = true;
	                         jsons.append(json);
	                     //多条请求返回的数据格式
	                     } else if(obj.size() > 1 ){
	                    	 outJson.append("\"" + component + "\":" + json + ", ");
	                     //单一请求返回数据格式
	                     }else {
	                    	 //BENSON
	                    	 if(json instanceof String||json instanceof JSONObject||json instanceof JSONArray){
	                    		 outJson.append("{ \"" + component + "\":" + json + " }");
	                    	 }else{
	                    		 outJson.append("{ \"" + component + "\":" + JSONUtils.toString(json) + " }"); 
	                    	 }
	                    	 //outJson.append("{ \"" + component + "\":" + json + " }");
	                     }
	                 }
	             } catch (Exception e) {
	            	 if(logger.isInfoEnabled()){
	            		 e.printStackTrace();
	            	 }
	            	 String tmp = e.getCause().getMessage();
	            	 String errorMsg="";
	            	 if(null!=tmp&&tmp.startsWith("errorMsg:")){
	            		 String msg = tmp.substring(9, tmp.length());
	            		 errorMsg = "{\"success\":false,\"errorMsg\":\""+msg+"\"}";
	            	 }else{
	            		 errorMsg = "{\"success\":false,\"errorMsg\":\"\"}";
	            	 }
	                 logger.error(e.toString());
                	 //table单一请求数据格式
                     if (requsetParams.contains("singleReturnNoComponent") && obj.size() == 1) {
                    	 flag = true;
                         jsons.append(errorMsg);
                     //多条请求返回的数据格式
                     } else if(obj.size() > 1 ){
                    	 outJson.append("\"" + component + "\":" + errorMsg + ", ");
                     //单一请求返回数据格式
                     }else {
                    	 outJson.append("{ \"" + component + "\":" + errorMsg + " }");
                     }
	                 
	                 
	             } finally {
	
	             }
	        }
	        //table数据查询 
	        if(obj.size() == 1 && flag == true){
	        	tmpJson = jsons.toString();
	        //非table查询的单一组件数据查询
	        }else if (!"".equals(component) && obj.size() == 1){
	        	tmpJson = outJson.toString();
	        //多条请求的数据集合
	        }else {
	        	tmpJson = "{ " + outJson.substring(0, outJson.length() - 1)+" }";
	        }
	        //返回json字符串
	        Struts2Utils.renderGZip(tmpJson);
        }
    }

  /**
     * STRUTS2要求 (一个输入流,一个文件名)
     * @author LJH
     * @param workbook
     * @param fileName
     */
     private void exportExcel(HSSFWorkbook workbook, String fileName) {
        this.fileName = fileName;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            baos.flush();
            byte[] aa = baos.toByteArray();
            this.excelStream = new ByteArrayInputStream(aa, 0, aa.length);
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * 导出EXCEL
     * @author LJH
     * @return
     */
    public String execute() {
        HttpServletRequest request = ServletActionContext.getRequest();
        
        String exportFields = request.getParameter("exportFields");
        ActionContext act = ActionContext.getContext();
      //获取当前用户信息
        userInfo = (UserInfo) act.getSession().get(SysConstants.SESSION_USERINFO);
    	/*if(userInfo == null ){
        	Struts2Utils.renderJson("{success:false,errorInfo:'登录超时，请重新登录！'}");
        }*/
    	Map requestMap = (HashMap) act.getApplication().get(SysConstants.REQUEST_SERVICES);
        Map requestParams = request.getParameterMap();
         try {
            exportFields = new String(exportFields.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //将前台传入的参数转化为JSON
        JSONObject jsonObj = JSONObject.fromObject(exportFields);
        String entityCN = jsonObj.getString("entityCN");
        String entityName = jsonObj.getString("entityName");
        //获取前台传入的列字段转化为List
        List fields = jsonObj.getJSONArray("fields");
        String fieldstr = "";
        for(int i= 0;i<fields.size();i++){
        	fieldstr = fieldstr.concat("'"+fields.get(i).toString()+"'");
        	if(i != fields.size()-1){
        		fieldstr = fieldstr.concat(",");
        	}
        }
        
        String queryCondition = request.getParameter("queryCondition");
        String component = request.getParameter("components");
         try {
            queryCondition = new String(queryCondition.getBytes("ISO-8859-1"), "GBK");
            queryCondition = new String(queryCondition.getBytes("UTF-8"), "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
         List<TsStsx> headers = null;
        BaseService baseService = (BaseService) ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
        try{
        	headers = baseService.queryExportTableHeader(entityName,fieldstr);
        }catch (Exception e) {
        	e.printStackTrace();
		}
       /* for(int i = 0;i<headers.length;i++){
        	String field = headers[i][0].toUpperCase();
        	if(field.length()>2 && field.indexOf("ID")>0){
        		headers[i][0] = field.replace("ID", "_ID");
        	}else{
        		headers[i][0] = field;
        	}
        }*/
        Object content ="";
        try{
        	content = queryTableData(queryCondition,component,requestMap);
        }catch (Exception e) {
			e.printStackTrace();
		}
        
        List list = JSONArray.fromObject(JSONObject.fromObject(content).getString("data"));
        HSSFWorkbook workbook = ExportUtil.exportExcel(entityCN, headers, list);

        Calendar c = Calendar.getInstance();
        this.exportExcel(workbook, c.getTimeInMillis()+ "Data.xls");

        return "export";
    }
    
    private Object queryTableData(String queryCondition,String components,Map requestMap){
    	Object outJson =null;
    	JSONObject obj = JSONObject.fromObject(components);
        //代码重构if eles去掉
        StringBuffer jsons = new StringBuffer();
        Iterator it = obj.keys();
        boolean flag = false;
        String component = "";
        while(it.hasNext()){
        	component = (String) it.next();
        	JSONObject obj1 = JSONObject.fromObject(obj.getString(component));
        	JSONObject queryObj = JSONObject.fromObject(queryCondition);
        	queryObj.remove("start");
        	queryObj.remove("limit");
        	queryCondition = queryObj.toString();
	    	//请求名称
	    	String requestName = obj1.getString("request");
	//	                 String paramsStr = obj1.getString("params");
	    	String requsetParams = obj1.getString("params").replaceFirst("\\{", "");
	    	//把前台传过来所有参数合并为一个对象
	    	String str = "{ start:0,limit:60000,";
	    	try {
	    		//单字段、表头、高级查询的条件
	    		if(!queryCondition.equals("{}")){
	    			requsetParams = queryCondition.replaceFirst("\\{", "").replaceFirst("\\}", "").concat(",").concat(requsetParams);
	    		}
	             requsetParams = str.concat(requsetParams);
	             /**
	              * 判断是否有访问当前service或process的权限
	              */
	             //获取前台传入的参数
	             JSONObject map = JSONObject.fromObject(requsetParams);
	             requsetParams = Struts2Utils.getParams(map);
	             
	             
	             String service = null;
                 String methodName = null;
                 if(requestName.contains("?")){
                	 String [] beanAndMethod = StringUtils.split(requestName, "?");
                	 service = beanAndMethod[0];
                	 methodName = beanAndMethod[1];
                 }else{
                	 Map serviceMap = (HashMap) requestMap.get(requestName);
	                 service = (String) serviceMap.get(SysConstants.REQUEST_SERVICE_NAME);
	                 methodName = (String) serviceMap.get(SysConstants.REQUEST_SERVICE_METHOD);
                 }
	             
	             //获取服务对象
	             Object serviceBean = ApplicationComponentStaticRetriever.getComponentByItsName(service);
	             Class cls = serviceBean.getClass();
	             Class[] paraTypes = new Class[]{String.class};
	             //反射方法
	             Method method = cls.getMethod(methodName, paraTypes);
	             String[] paraValues = new String[]{requsetParams};
	             outJson = method.invoke(serviceBean, paraValues);
	             //调用方法
	         } catch (Exception e) {
	        	e.printStackTrace();
	         }
        }
	    return outJson;
    }
    
    private boolean checkMenuPermiss(String menuId,String buttonId) {
    	//初始化menuTree;
//    	MenuTree menuTree = new MenuTree();
//		try {
//			menuTree = UserPermissionUtil.getMenuPermiss();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		//获取树的顶级节点
//		MenuNode treeNode =  (MenuNode) menuTree.getRoot();
//		//子节点的数量
//		int count = treeNode.children.size();
//		if(count > 0){
//			//检查是否存在对应的节点对象
//			MenuNode childNode = permissService.checkChildNode(treeNode, Long.valueOf(menuId));
//			//session中是否存在当前菜单
//			if(childNode != null){
//				String buttonMap = childNode.getPermiss();
//				JSONArray jsonArray = JSONArray.fromObject(buttonMap);
//				for(int i = 0;i<jsonArray.size();i++){
//					JSONObject obj = jsonArray.getJSONObject(i);
//					if(obj != null){
//						Long btnId = obj.getLong("buttonId");
//						return btnId.equals(Long.valueOf(buttonId));
//					}
//				}
//			}
//		}
////		TreeNode treeNode = (TreeNode) menuTree.root;
//		/*try {
//			map = Struts2Utils.jsonToMap(menuTree);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
//    	/*JSONObject obj = new JSONObject().fromObject(menuTree);
//    	String id = obj.getString("id");
//    	System.out.println(id);*/
//    	/*ActionContext ac = ServletActionContext.getContext();
//		try {
//			userInfo = UserPermissionUtil.getUserInfo();
//			Method method;
//			Object permissBean = ApplicationComponentStaticRetriever.getComponentByItsName("permissProcess");
//			Class permissCls = permissBean.getClass();
//			Class[] paraTypes = new Class[]{String.class};
//			method = permissCls.getMethod("queryPermissCdzy", paraTypes);
//			menuTree = (String) method.invoke(permissBean, "");
//			ac.getSession().put(SysConstants.SESSION_PERMISSION, menuTree);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}*/
    	return false;
    }
}


