package com.jhnu.syspermiss.filter;


import java.io.IOException;
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

import org.jasig.cas.client.authentication.AttributePrincipal;

import com.jhnu.syspermiss.GetCachePermiss;
import com.jhnu.syspermiss.permiss.entity.Resources;
import com.jhnu.syspermiss.util.SysConfig;


public class PermissUrlFilter implements Filter  {

	/** 
    * 忽略拦截的访问路径
    */ 
    private String casIgnoreUrls;
    
    private String permissIgnoreUrls;
	
    public PermissUrlFilter() {
    	
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest Httprequest = (HttpServletRequest) request;
		String servletPath=Httprequest.getServletPath().toLowerCase();
		if(servletPath.endsWith("/")){
			servletPath=servletPath.substring(0, servletPath.length()-1);
		}
		if(checkUrlIsPass(this.casIgnoreUrls,servletPath)){
        	chain.doFilter(request, response);
        	return;
        }
		if(checkUrlIsPass(this.permissIgnoreUrls,servletPath)){
        	chain.doFilter(request, response);
        	return;
        }
		AttributePrincipal principal = (AttributePrincipal) Httprequest.getUserPrincipal();
		String username = null;  
		final HttpServletResponse Httpresponse = (HttpServletResponse) response;
		String path = Httprequest.getContextPath(); 
		String basePath = Httprequest.getScheme()+"://"+Httprequest.getServerName()+":"+Httprequest.getServerPort()+path;
		if(principal!=null){
			username = principal.getName();  
			if(servletPath.equalsIgnoreCase("/logout")){
				Httprequest.getSession().invalidate();
				Httpresponse.sendRedirect(SysConfig.instance().getLogoutUrl());
				return;
			}
		}
		
		//获取缓存系统页面资源
		List<Resources> resList=GetCachePermiss.getSysMenusByUserName(username);
		boolean noPermiss=true;
		for(Resources res :resList){
//			if(servletPath.indexOf(res.getUrl_().toLowerCase())==0&&res.getUrl_().length()>0){
//				noPermiss=false;
//				break;
//			}
			if(servletPath.equals(res.getUrl_().toLowerCase())&&res.getUrl_().length()>0){
				noPermiss=false;
				break;
			}
		}
		if(noPermiss){
			Httpresponse.sendRedirect(basePath+SysConfig.instance().getNoPermissUrl());
			return;
		}
		chain.doFilter(request, response);
		return ;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.casIgnoreUrls=SysConfig.instance().getCasIgnoreUrls();
		this.permissIgnoreUrls=SysConfig.instance().getPermissIgnoreUrls();
	}
	
	/** 
	* @Title: checkUrlIsPass 
	* @Description: 校验访问的连接是否能pass CAS的过滤（其中所有的js和css文件全部通过，其余在web.xml中有配置项ignoreUrls）
	* @param servletPath
	* @return boolean
	*/
	private boolean checkUrlIsPass(String ignoreUrls , String checkUrl){
		boolean result = false;
		
		if (checkUrl.endsWith(".js")
				|| checkUrl.endsWith(".css") 
				|| checkUrl.endsWith(".jpg") 
				|| checkUrl.endsWith(".png") 
				|| checkUrl.endsWith(".gif") 
				|| checkUrl.endsWith(".css") 
				|| checkUrl.endsWith(".eot") 
				|| checkUrl.endsWith(".svg") 
				|| checkUrl.endsWith(".ttf") 
				|| checkUrl.endsWith(".woff")) {
			result = true;
		}else{
			//获取配置的忽略连接（字符串）
			String[]urls = ignoreUrls.split(",");
			List<String> urlset = Arrays.asList(urls);
			//CollectionUtils.addAll(urlset, urls);
			for (int i = 0; i < urlset.size(); i++) {
				String url = urlset.get(i).trim().replace("*", ".*");//检测到连接配置的有*号，换成正则表达式中对应的.*
				Pattern pattern = Pattern.compile("^"+url);
				Matcher matcher = pattern.matcher(checkUrl);
				if (matcher.matches()) {
					result = true;
					break;
				}
			}
		}
		return result;
	};
}
