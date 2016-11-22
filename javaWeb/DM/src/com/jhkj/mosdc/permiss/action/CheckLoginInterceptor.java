package com.jhkj.mosdc.permiss.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.action.ValidateImgAction;
import com.jhkj.mosdc.framework.util.MessageInfoJson;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permiss.domain.User;
import com.jhkj.mosdc.permiss.util.CurrentUserMenuUtil;
import com.jhkj.mosdc.permission.action.OuterSystemLoginAction;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @Comments: 判断是否会超时 Company: 河南精华科技有限公司 Created by
 *            gaodongjie(gaodongjie@126.com)
 * @DATE:2012-8-28
 * @TIME: 上午11:56:51
 */
public class CheckLoginInterceptor extends AbstractInterceptor {

	/**
	 * long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 对 LoginAction 不做该项拦截
		if (invocation.getAction() instanceof LoginAction
				|| invocation.getAction() instanceof LoginAction
				|| invocation.getAction() instanceof ValidateImgAction
				|| invocation.getAction() instanceof CasLoginAction
				|| invocation.getAction() instanceof OuterSystemLoginAction) {
			return invocation.invoke();
		}
		HttpServletRequest request = (HttpServletRequest) invocation
				.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) invocation
				.getInvocationContext().get(ServletActionContext.HTTP_RESPONSE);
		MessageInfoJson infoJson = new MessageInfoJson();
		Map session = ActionContext.getContext().getSession();
		User user = (User) session.get(SysConstants.SESSION_USER);
		
		if (user == null) {
			// true即为Ajax请求，false为Request
			if (request.getHeader("x-requested-with") != null
					&& request.getHeader("x-requested-with").equalsIgnoreCase(
							"XMLHttpRequest")) {
				response.setHeader("sessionstatus", "timeout");
				infoJson.setShowType(0);
				infoJson.setTitle("异常提示-登录超时");
				infoJson.setInfo("系统连接超时，请点击退出登录按钮后，重新登录!");
				infoJson.setInfoType(MessageInfoJson.INFOTYPE_EXCEPTION);
				Struts2Utils.renderJson(infoJson);
				return null;
			} else {
				return "sessionout";
			}
		} else {
			String uId = request.getParameter("proxyUserId");
			String mId = request.getParameter("proxyMenuId");
			
			Long userId = uId == null ? null : Long.valueOf(uId);
			Long menuId = mId == null ? null : Long.valueOf(mId);
			
			CurrentUserMenuUtil.setThreadUserMenu(userId, menuId);
			return invocation.invoke();
		}
	}

}
