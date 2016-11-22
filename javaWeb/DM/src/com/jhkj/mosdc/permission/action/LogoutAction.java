package com.jhkj.mosdc.permission.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.action.BaseAction;
import com.opensymphony.xwork2.ActionContext;


/**
 * @Comments: 退出系统
 * Company: 河南精华科技有限公司
 * Created by gaodongjie(gaodongjie@126.com) 
 * @DATE:2012-8-29
 * @TIME: 下午3:16:15
 */
public class LogoutAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private static final Log logger = LogFactory.getLog(LogoutAction.class);
	
	public String execute(){
		ActionContext ac = ServletActionContext.getContext();
        ac.getSession().clear();
        logger.info("退出登录！");
		return SUCCESS;
	}
	
}
