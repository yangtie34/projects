package com.jhkj.mosdc.framework.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.service.DeskTopService;
import com.jhkj.mosdc.framework.util.ServletUtils;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/***
 * 通用上传类
 * 
 * @Comments: Company: 河南精华科技有限公司 Created by wangyongtai(490091105@.com)
 * @DATE:2012-12-11
 * @TIME: 下午6:47:07
 */
public class CommonFileUploadAction extends ActionSupport {

	private File[] files;

	private String basePath = "uploadFile/";// 基本文件上传路径

	private String[] filesFileName;

	private String[] filesContentType;
	
	private String deleteFileName;//删除文件路径
	
	private String loadFilePath;//下载文件路径
	
	private String loadFileName;//下载文件 名称
	
	private InputStream stream;//流
	
	private List<?>ret = new ArrayList(); 

	private boolean success;
	
	private String message;

	public String loadFiles() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String savePath = ServletActionContext.getServletContext().getRealPath(
				"/")
				+ "mainPageEntrance\\desktop\\wallpapers\\";
		List<String> saveNames = new ArrayList<String>();

		// List<String> saveNames = Arrays.asList((String[])
		// request.getAttribute("names"));
		List<String> imgsUrl = new ArrayList<String>();
		request.setCharacterEncoding("UTF-8");
		String[] fileNameArray = (String[]) request
				.getAttribute("filesFileName");
		for (int i = 0; i < files.length; i++) {
			String[] splits = filesFileName[i].replace(".", ",").split(",");
			String saveName = filesFileName[i].replace(",", ".") + "_"
					+ Calendar.getInstance().getTimeInMillis() + "."
					+ splits[splits.length - 1];
			saveNames.add(saveName);
			imgsUrl.add(filesFileName[i].substring(0,
					filesFileName[i].lastIndexOf(".")));
			// 以服务器的文件保存地址和原文件名建立上传文件输出流
			FileOutputStream fos = new FileOutputStream(savePath + saveName);
			FileInputStream fis = new FileInputStream(files[i]);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			files[i].delete();
		}
		DeskTopService service = (DeskTopService) ApplicationComponentStaticRetriever
				.getComponentByItsName("desktopservice");
		boolean flag = service.saveImgs(saveNames, imgsUrl);
		success = flag;
		return Action.SUCCESS;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String uploadFile() throws Exception {
		List<Map> list = new ArrayList<Map>();
		String savePath = ServletActionContext.getServletContext().getRealPath(
				"/")
				+ basePath;
		for (int i = 0; i < files.length; i++) {
			Map nM = new HashMap();
			String filename = filesFileName[i].trim();
			File file = files[i];
			// 以服务器的文件保存地址和原文件名建立上传文件输出流
			String suffix = filename
					.substring(filename.lastIndexOf("."),
							filename.length());
			String newfilename = new Date().getTime() + suffix.trim();
			FileOutputStream fos = new FileOutputStream(savePath+newfilename);
			FileInputStream fis = new FileInputStream(files[i]);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			fis.close();
			newfilename = newfilename.replace("\\", "/").trim();
			nM.put("filename", filesFileName[i]);//原始名
			nM.put("storename", newfilename);//存储名"
			list.add(nM);
			files[i].delete();
		}
		ret = list;
		return SUCCESS;
	}
    public String deleteFile(){
    	String savePath = ServletActionContext.getServletContext().getRealPath(
				"/");
    	String deleteFilePath = savePath + basePath + deleteFileName;
    	File file = new File(deleteFilePath);
    	file.delete();
    	success = true;
    	return Action.SUCCESS;
    }
    public String loadFile(){
    	String savePath = ServletActionContext.getServletContext().getRealPath(
				"/");
    	String filePath = savePath + basePath + loadFilePath;
    	try {
			stream = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return Action.SUCCESS;
    }
	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public String[] getFilesFileName() {
		return filesFileName;
	}

	public void setFilesFileName(String[] filesFileName) {
		this.filesFileName = filesFileName;
	}

	public String[] getFilesContentType() {
		return filesContentType;
	}

	public void setFilesContentType(String[] filesContentType) {
		this.filesContentType = filesContentType;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getDeleteFileName() {
		return deleteFileName;
	}

	public void setDeleteFileName(String deleteFileName) {
		this.deleteFileName = deleteFileName;
	}

	public List getRet() {
		return ret;
	}

	public void setRet(List ret) {
		this.ret = ret;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLoadFileName() {
		return loadFileName;
	}

	public void setLoadFileName(String loadFileName) {
		this.loadFileName = loadFileName;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}

	public String getLoadFilePath() {
		return loadFilePath;
	}

	public void setLoadFilePath(String loadFilePath) {
		this.loadFilePath = loadFilePath;
	}
}
