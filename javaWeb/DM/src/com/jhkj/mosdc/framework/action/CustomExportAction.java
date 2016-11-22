package com.jhkj.mosdc.framework.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.util.ReflectInvoker;
import com.opensymphony.xwork2.ActionSupport;

public class CustomExportAction extends ActionSupport {

	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private InputStream excelStream;
	
	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	
	public InputStream inputStream;

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	@Override
	public String execute() throws Exception {
		//解析request请求参数。
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setCharacterEncoding("UTF-8");
        String serviceAndParams = request.getParameter("serviceAndParams");
        JSONObject obj = JSONObject.fromObject(serviceAndParams);
        String servicemethod = obj.getString("servicAndMethod");
        String[] servicAndMethod = servicemethod.split("\\?");
        String params = obj.getString("params");
        String param="";
		try {
			param = new String(params.getBytes("ISO8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			
		}
        Object bean = ApplicationComponentStaticRetriever.getComponentByItsName(servicAndMethod[0]);
		//调用响应的service 返回excel  文件HSSFWorkbook
		HSSFWorkbook hw =(HSSFWorkbook) ReflectInvoker.commonReflectInvoke(bean, servicAndMethod[1], new Object[]{param});
		//创建导出excel的文件名。
		//String fileName=servicAndMethod[0]+"_(custom).xls";
		String fileName="data.xls";
		// 将返回的excel缓冲流赋值为该类。 this.excelStream
		exportExcelStream(hw, fileName);
		return "customExport";
	}
	/**
	 * 创建导出excel的缓冲流。
	 * @param workbook
	 * @param fileName
	 */
	private void exportExcelStream(HSSFWorkbook workbook, String fileName) {
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
	public String downLoadZipStreamFile(){
		HttpServletRequest request = ServletActionContext.getRequest();
        String serviceAndParams = request.getParameter("serviceAndParams");
        JSONObject obj = JSONObject.fromObject(serviceAndParams);
        String servicemethod = obj.getString("servicAndMethod");
        String fileName = obj.getString("fileName");//文件名
        String[] servicAndMethod = servicemethod.split("\\?");
        String params = obj.getString("params");
        Object bean = ApplicationComponentStaticRetriever.getComponentByItsName(servicAndMethod[0]);
		//调用响应的service 返回excel  文件HSSFWorkbook
        inputStream = (InputStream) ReflectInvoker.commonReflectInvoke(bean, servicAndMethod[1], new Object[]{params});
		//创建导出excel的文件名。
		//String fileName=servicAndMethod[0]+"_(custom).xls";
		if(fileName == null)fileName = "无文件名!";
		// 将返回的excel缓冲流赋值为该类。 this.excelStream
		return "customExport";
	}
}
