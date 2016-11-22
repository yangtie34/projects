package com.jhnu.framework.filter;


import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jhnu.framework.spring.ApplicationComponentStaticRetriever;
import com.jhnu.system.permiss.service.ResourcesService;
import com.jhnu.util.common.PropertiesUtils;


/**
 * Servlet Filter implementation class dd
 */
public class PermissUrlFilter implements Filter  {

	/** 
    * 忽略拦截的访问路径
    */ 
    private String ignoreUrls;
    public PermissUrlFilter() {
    	
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest Httprequest = (HttpServletRequest) request;
		String servletPath=Httprequest.getServletPath().toLowerCase();
		if(checkUrlIsPass(servletPath)){
        	chain.doFilter(request, response);
        	return;
        }
		Principal principal = Httprequest.getUserPrincipal();
		String username=null; 
		if(principal!=null){
			username = principal.getName();  
		}else{
			chain.doFilter(request, response);
			return;
		}
		final HttpServletResponse Httpresponse = (HttpServletResponse) response;
		String path = Httprequest.getContextPath(); 
		String basePath = Httprequest.getScheme()+"://"+Httprequest.getServerName()+":"+Httprequest.getServerPort()+path;
		boolean yesPermiss=true;
		ResourcesService res=(ResourcesService) ApplicationComponentStaticRetriever.getComponentByItsName("resourcesService");
			if(res.hasMenuByUserName(username,basePath, servletPath)==false){
				yesPermiss=false;
			}
		if(yesPermiss==false){
			Httpresponse.sendRedirect(basePath+"/user/nopermi");
			return;
		}
		chain.doFilter(request, response);
		return ;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		setIgnoreUrls(PropertiesUtils.getDBPropertiesByName("sys.permissIgnoreUrls"));
	}
	
	public String getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(String ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}

	/** 
	* @Title: checkUrlIsPass 
	* @Description: 校验访问的连接是否能pass CAS的过滤（其中所有的js和css文件全部通过，其余在web.xml中有配置项ignoreUrls）
	* @param servletPath
	* @return boolean
	*/
	private boolean checkUrlIsPass(String servletPath){
		boolean result = false;
		
		if (servletPath.endsWith(".js")
				|| servletPath.endsWith(".css") 
				|| servletPath.endsWith(".jpg") 
				|| servletPath.endsWith(".png") 
				|| servletPath.endsWith(".gif") 
				|| servletPath.endsWith(".css") 
				|| servletPath.endsWith(".eot") 
				|| servletPath.endsWith(".svg") 
				|| servletPath.endsWith(".ttf") 
				|| servletPath.endsWith(".woff")
				|| servletPath.endsWith("index.jsp")) {
			result = true;
		}else{
			//获取配置的忽略连接（字符串）
			String urlstr = this.getIgnoreUrls();
			String[]urls = urlstr.split(",");
			List<String> urlset = Arrays.asList(urls);
			for (int i = 0; i < urlset.size(); i++) {
				String url = urlset.get(i).trim().replace("*", ".*");//检测到连接配置的有*号，换成正则表达式中对应的.*
				Pattern pattern = Pattern.compile("^"+url);
				Matcher matcher = pattern.matcher(servletPath);
				if (matcher.matches()) {
					result = true;
					break;
				}
			}
		}
		return result;
	};
}

