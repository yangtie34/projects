package com.jhkj.mosdc.framework.cas;

/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import org.apache.struts2.ServletActionContext;
import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.ActionContext;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

/**
 * Filter implementation to intercept all requests and attempt to authenticate
 * the user by redirecting them to CAS (unless the user has a ticket).
 * <p>
 * This filter allows you to specify the following parameters (at either the context-level or the filter-level):
 * <ul>
 * <li><code>casServerLoginUrl</code> - the url to log into CAS, i.e. https://cas.rutgers.edu/login</li>
 * <li><code>renew</code> - true/false on whether to use renew or not.</li>
 * <li><code>gateway</code> - true/false on whether to use gateway or not.</li>
 * </ul>
 *
 * <p>Please see AbstractCasFilter for additional properties.</p>
 *
 * @author Scott Battaglia
 * @version $Revision: 11768 $ $Date: 2007-02-07 15:44:16 -0500 (Wed, 07 Feb 2007) $
 * @since 3.0
 */
public class FilterProxy extends AbstractCasFilter {

    /**
     * The URL to the CAS Server login.
     */
    private String casServerLoginUrl;

    /**
     * Whether to send the renew request or not.
     */
    private boolean renew = false;

    /**
     * Whether to send the gateway request or not.
     */
    private boolean gateway = false;
    
    private GatewayResolver gatewayStorage = new DefaultGatewayResolverImpl();

    protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            setCasServerLoginUrl(getPropertyFromInitParams(filterConfig, "casServerLoginUrl", null));
            log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
            log.trace("Loaded renew parameter: " + this.renew);
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
            log.trace("Loaded gateway parameter: " + this.gateway);

            final String gatewayStorageClass = getPropertyFromInitParams(filterConfig, "gatewayStorageClass", null);

            if (gatewayStorageClass != null) {
                try {
                    this.gatewayStorage = (GatewayResolver) Class.forName(gatewayStorageClass).newInstance();
                } catch (final Exception e) {
                    log.error(e,e);
                    throw new ServletException(e);
                }
            }
        }
    }

    public void init() {
        super.init();
        CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        
        //针对掌上校园手机端用户不做单点登录集成
        if(request.getRequestURI().indexOf("zsxy_call")>=0 || request.getRequestURL().indexOf("pcm")>=0){
        	filterChain.doFilter(request, response);
        	return;
        }
        
        //设定打开MOSDC后打开的页面
        String page = request.getParameter("page");
        String params = request.getParameter("params");
        UrlVariable uv = new UrlVariable();
        uv.setPage(page);
        uv.setParams(params);
        CasThreadVariable.setThreadVariable(Thread.currentThread().getId(), uv);
        
        
        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

        if (assertion != null) {
            filterChain.doFilter(request, response);
            //this.login(request,response,assertion.getPrincipal().getName());
            //request.setAttribute("loginName", assertion.getPrincipal().getName());
            //response.sendRedirect("http://localhost:8088/MOSDC/casLoginAction");
            return;
        }

        final String serviceUrl = constructServiceUrl(request, response);
        final String ticket = CommonUtils.safeGetParameter(request,getArtifactParameterName());
        final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);

        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String modifiedServiceUrl;

        log.debug("no ticket and no assertion found");
        if (this.gateway) {
            log.debug("setting gateway attribute in session");
            modifiedServiceUrl = this.gatewayStorage.storeGatewayInformation(request, serviceUrl);
        } else {
            modifiedServiceUrl = serviceUrl;
        }

        if (log.isDebugEnabled()) {
            log.debug("Constructed service url: " + modifiedServiceUrl);
        }
        

        String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

        if (log.isDebugEnabled()) {
            log.debug("redirecting to \"" + urlToRedirectTo + "\"");
        }
        
        response.sendRedirect(urlToRedirectTo);
    }
    private void login(HttpServletRequest request,HttpServletResponse response,String loginName){
    	long l = System.currentTimeMillis();
		String path = request.getContextPath();
		HttpSession session = request.getSession();
		ServletActionContext.setRequest(request);
		ServletActionContext.setResponse(response);
		ActionContext ac = ActionContext.getContext();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//		ActionContext ac = ServletActionContext.getContext();
		session.setAttribute("path", path);
		session.setAttribute("basePath", basePath);
		/**
		String code  = (String) ac.getSession().get("validateCodeByLogin");
		if( imageCode == null || !code.equals(imageCode.toUpperCase())){
			this.addActionError("验证码有误，请重新输入！");
			return LOGIN;
		}
		*/
		try {
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			request.setCharacterEncoding("UTF-8");

		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		UserInfo userInfo = null;
        //反射方法
		try{
			
		
			Method method;
			Object serviceBean = ApplicationComponentStaticRetriever.getComponentByItsName("userInfoService");
			Class cls = serviceBean.getClass();
			Class[] paraTypes = new Class[]{String.class};
			method = cls.getMethod("queryUserByLoginName", paraTypes);
			/*单点登录集成开始*/
			Object usernameObj = request.getAttribute("credentials");
			System.out.println("打印单点登录获取的用户名------------------"+usernameObj);
			if(usernameObj == null){
				userInfo = (UserInfo) method.invoke(serviceBean, loginName);
			}else{
				String username = usernameObj.toString();
				Method methodByLoginName = cls.getMethod("queryUserByLoginName", paraTypes);
				userInfo = (UserInfo) method.invoke(serviceBean, paraTypes);
			}
			//单点登录集成结束
			
			Object baseBean = ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
			Class baseCls = baseBean.getClass();
			Class[] baseParaTypes = new Class[]{String.class};
			method = baseCls.getMethod("getDefXnxq", baseParaTypes);
			String baseParaValues = "";
			Map map = (Map) method.invoke(baseBean, baseParaValues);
			if(map!=null){
				session.setAttribute("xnId", map.get("xnId"));
				session.setAttribute("xqId", map.get("xqId"));
				session.setAttribute("xndm", map.get("xndm"));
				session.setAttribute("xqdm", map.get("xqdm"));
			}
			
			if(userInfo != null){
				session.setAttribute(SysConstants.SESSION_USERINFO, userInfo);
				request.getSession().setMaxInactiveInterval(60*60);//设置缓存失效时间
				/**
				 * TODO: 权宜之计，登录成功将用户的登录ip和登录时间保存到表（TB_XTJC_PERSONALINFO）
				 */
				/*String loginIP = getIpAddr(request);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ahh:mm:ss");
				String loginTime = sdf.format(new Date());
				ac.getSession().put("loginIP", loginIP);
				ac.getSession().put("loginTime", loginTime);
				IPersonalInfoDao idao = (IPersonalInfoDao)ApplicationComponentStaticRetriever.getComponentByItsName("personalDao");
				idao.savePersonInfo(loginTime,loginIP);*/
//				Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_TRUE);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
    }

    public final void setRenew(final boolean renew) {
        this.renew = renew;
    }

    public final void setGateway(final boolean gateway) {
        this.gateway = gateway;
    }

    public final void setCasServerLoginUrl(final String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }
    
    public final void setGatewayStorage(final GatewayResolver gatewayStorage) {
    	this.gatewayStorage = gatewayStorage;
    }
}
