package com.jhkj.mosdc.framework.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.framework.util.ReflectInvoker;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadAction extends ActionSupport {
	private static final Log logger = LogFactory.getLog(BaseAction.class);
	private String fileName;
	private InputStream zipStream;
	public InputStream inputStream;

	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String serviceAndParams = request.getParameter("serviceAndParams");
		try {
			JSONObject obj = JSONObject.fromObject(serviceAndParams);
			String servicemethod = obj.getString("servicAndMethod");
			String[] servicAndMethod = servicemethod.split("\\?");
			String params = obj.getString("params");
			Object bean = ApplicationComponentStaticRetriever
					.getComponentByItsName(servicAndMethod[0]);
			// 调用响应的service 返回excel 文件HSSFWorkbook

			File file = (File) ReflectInvoker.commonReflectInvoke(bean,
					servicAndMethod[1], new Object[] { params });
			this.fileName = new String(file.getName().getBytes("gb2312"),
					"ISO8859-1");
			this.zipStream = new FileInputStream(file);
		} catch (Throwable e) {
			logger.error(e);
			Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_FALSE);
		}
		return "success";
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getZipStream() {
		return zipStream;
	}

	public void setZipStream(InputStream zipStream) {
		this.zipStream = zipStream;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
