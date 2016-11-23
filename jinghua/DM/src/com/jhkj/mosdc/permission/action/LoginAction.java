package com.jhkj.mosdc.permission.action;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.action.BaseAction;
import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.ActionContext;

/**
 * 登录Action
 * @author Administrator
 *
 */
public class LoginAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(LoginAction.class);
	public String loginName;
	public String password;
	public String imageCode;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageCode() {
		return imageCode;
	}

	public void setImageCode(String imageCode) {
		this.imageCode = imageCode;
	}

	public String execute(){
//		long l = System.currentTimeMillis();
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		ActionContext ac = ServletActionContext.getContext();
		ac.getSession().put("path", path);
		ac.getSession().put("basePath", basePath);
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
		try {
			Method method;
			Object serviceBean = ApplicationComponentStaticRetriever.getComponentByItsName("userInfoService");
			Class cls = serviceBean.getClass();
			Class[] paraTypes = new Class[]{String.class};
			method = cls.getMethod("queryUser", paraTypes);
			String paraValues = "{loginName:'"+loginName+"',password:'"+password+"'}";
			/*单点登录集成开始*/
			Object usernameObj = request.getAttribute("credentials");
			System.out.println("打印单点登录获取的用户名------------------"+usernameObj);
			if(usernameObj == null){
				userInfo = (UserInfo) method.invoke(serviceBean, paraValues);
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
				ac.getSession().put("xnId", map.get("xnId"));
				ac.getSession().put("xqId", map.get("xqId"));
				ac.getSession().put("xndm", map.get("xndm"));
				ac.getSession().put("xqdm", map.get("xqdm"));
			}
			
			if(userInfo != null){
				ac.getSession().put(SysConstants.SESSION_USERINFO, userInfo);
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
			}else {
				this.addActionError("用户名或密码错误，请重新输入！");
				return LOGIN;
			}
		} catch (Exception e) {
//			Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_FALSE);
			this.addActionError("登陆失败，请联系管理员");
			e.printStackTrace();
			return LOGIN;
		} 
//		System.out.println("\r<br>loginAction执行耗时 : "+(System.currentTimeMillis()-l)/1000f+" 秒 ");
		return SUCCESS;
		/*String loginPage = "";
		try {
			DeskTopService service = (DeskTopService) ApplicationComponentStaticRetriever.getComponentByItsName("desktopservice");
			Map  systemstyle = service.queryUserSystemPage("");
			loginPage = (String) systemstyle.get("login_page");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return LOGIN;
		}
		if(loginPage == null)loginPage = "main1.jsp";
		return loginPage;*/
	}
	/**
	 * 获取客户端ip的方法。
	 * @param request
	 * @return
	 */
	private String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       return ip;
	   } 
}
