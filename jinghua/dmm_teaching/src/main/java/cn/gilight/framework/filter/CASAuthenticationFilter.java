package cn.gilight.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jasig.cas.client.authentication.DefaultGatewayResolverImpl;
import org.jasig.cas.client.authentication.GatewayResolver;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.CommonUtils;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;

import cn.gilight.framework.uitl.SysConfig;

/**   
* @Description: TODO 重写了CAS的权限过滤器，让可以忽略CAS的连接可配置
* @author Sunwg  
* @date 2016年3月9日 下午4:32:38   
*/
public class CASAuthenticationFilter extends AbstractCasFilter {
	private final Logger log = Logger.getLogger(this.getClass());
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
    
    /** 
    * 忽略拦截的访问路径
    */ 
    private String ignoreUrls;

	protected void initInternal(final FilterConfig filterConfig) throws ServletException {
        if (!isIgnoreInitConfiguration()) {
            super.initInternal(filterConfig);
            setCasServerLoginUrl(SysConfig.instance().getCasServerLoginUrl());
            
            log.trace("Loaded CasServerLoginUrl parameter: " + this.casServerLoginUrl);
            setRenew(parseBoolean(getPropertyFromInitParams(filterConfig, "renew", "false")));
            log.trace("Loaded renew parameter: " + this.renew);
            setGateway(parseBoolean(getPropertyFromInitParams(filterConfig, "gateway", "false")));
            log.trace("Loaded gateway parameter: " + this.gateway);
           
            setIgnoreUrls(SysConfig.instance().getCasIgnoreUrls());
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
    	super.setServerName(SysConfig.instance().getServerName());
        super.init();
        CommonUtils.assertNotNull(this.casServerLoginUrl, "casServerLoginUrl cannot be null.");
    }

    public final void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain filterChain) throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final HttpSession session = request.getSession(false);
        final Assertion assertion = session != null ? (Assertion) session.getAttribute(CONST_CAS_ASSERTION) : null;

        if (assertion != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String serviceUrl = constructServiceUrl(request, response);
        final String ticket = CommonUtils.safeGetParameter(request,getArtifactParameterName());
        final boolean wasGatewayed = this.gatewayStorage.hasGatewayedAlready(request, serviceUrl);
        
        
        String servletPath = request.getServletPath();
        if (CommonUtils.isNotBlank(ticket) || wasGatewayed) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if(checkUrlIsPass(servletPath)){
        	log.debug("在web.xml中配置的忽略CAS拦截的访问路径："+servletPath);
        	filterChain.doFilter(request, response);
        	return;
        }

        final String modifiedServiceUrl;

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

        final String urlToRedirectTo = CommonUtils.constructRedirectUrl(this.casServerLoginUrl, getServiceParameterName(), modifiedServiceUrl, this.renew, this.gateway);

        if (log.isDebugEnabled()) {
            log.debug("redirecting to \"" + urlToRedirectTo + "\"");
        }

        response.sendRedirect(urlToRedirectTo);
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
    
    public String getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(final String ignoreUrls) {
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
				|| servletPath.endsWith(".woff")) {
			result = true;
		}else{
			//获取配置的忽略连接（字符串）
			String urlstr = this.getIgnoreUrls();
			String[]urls = urlstr.split(",");
			List<String> urlset = new ArrayList<String>();
			CollectionUtils.addAll(urlset, urls);
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