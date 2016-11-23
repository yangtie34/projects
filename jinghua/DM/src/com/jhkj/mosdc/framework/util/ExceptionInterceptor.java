package com.jhkj.mosdc.framework.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;

public class ExceptionInterceptor extends AbstractInterceptor {
	private static final Logger log = Logger.getLogger(ExceptionInterceptor.class);
	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try {
			return invocation.invoke();
		} catch (Exception e) {
			e.printStackTrace();
			invocation.getStack().push(new ExceptionHolder(e));

			// 若为AJAX请求返回Json串，否则跳转到自定义异常页面
			// 判断true即为Ajax请求，false为Request
			if ("XMLHttpRequest".equalsIgnoreCase(ServletActionContext.getRequest().getHeader("x-requested-with"))) {
				MessageInfoJson infoJson = new MessageInfoJson();
				infoJson.setInfo(e.getMessage());
				if (e instanceof MOSDCException) { // 为自定义业务异常,不返回堆栈信息
					log.warn("违例操作：" + e == null ? "" : e.getMessage());
					MOSDCException de = (MOSDCException)e;
					infoJson.setInfoType(de.getInfoType());
					infoJson.setShowType(de.getShowType());
					infoJson.setInfo(de.getInfo());
					infoJson.setDetialInfo(de.getDetailInfo());
				} else {
					log.error("系统出现异常：" + e == null ? "" : e.getMessage(), e);
					infoJson.setInfoType(MessageInfoJson.INFOTYPE_EXCEPTION);
					Writer w = new StringWriter();
					e.printStackTrace(new PrintWriter(w));
					infoJson.setDetialInfo(w.toString());
					infoJson.setInfo(e.getMessage() == null ? e.toString() : e.getMessage());
				}
				Struts2Utils.renderJson(infoJson);
				return null;
			}
		}

		return "error";
	}

}
