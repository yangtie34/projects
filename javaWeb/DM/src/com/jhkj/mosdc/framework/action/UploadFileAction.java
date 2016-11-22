package com.jhkj.mosdc.framework.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;
import com.jhkj.mosdc.framework.dao.BaseDao;
import com.jhkj.mosdc.framework.po.TbXsjsPhoto;
import com.jhkj.mosdc.framework.service.BaseService;
import com.jhkj.mosdc.framework.statics.StorePath;
import com.jhkj.mosdc.framework.util.Struts2Utils;
import com.jhkj.mosdc.framework.util.SysConstants;
import com.jhkj.mosdc.permission.po.UserInfo;
import com.opensymphony.xwork2.ActionSupport;

public class UploadFileAction extends ActionSupport {
	private File file;

	private String fileFileName;

	private String fileContentType;

	private static Logger logger = Logger.getLogger(UploadFileAction.class);

	private boolean success;// 后台Ajax传给前台是的标志位

	private String message;// 传递到前台的校验信息

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 导入excel文件
	 * 
	 * @return
	 */
	public void importExcel() {
		logger.info("开始上传文件.... ");

		String serverPath = ServletActionContext.getServletContext()
				.getRealPath("/")
				+ "import\\";

		File dataFile = new File(serverPath + "\\" + this.getFileFileName());

		try {
			if (dataFile.exists()) {
				dataFile.delete();
			}
			// 将第一个参数对应的 文件 copy 到 第二个参数对应的文件中
			FileUtil.copyFile(this.file, dataFile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_FALSE);
		}

		logger.info("------上传文件成功======");
		BaseService baseService = (BaseService) ApplicationComponentStaticRetriever
				.getComponentByItsName("baseService");
		try {

			// baseService.importExcel(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_TRUE);
		}
		success = true;
		logger.info("导入完成------------------------");
		Struts2Utils.renderJson(SysConstants.JSON_SUCCESS_TRUE);
	}

	public void fileUpLoad() {
//		String newFileName = null;
//		BufferedOutputStream bos = null;
//		BufferedInputStream bis = null;
//		long now = new Date().getTime();
//		String uploadDir = "";
//		bos = null;
//		bis = null;
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();	
//		BaseService baseService = (BaseService) ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
//		String fileName = request.getParameter("fileName");
//		String fId = request.getParameter("fId");
//		String fjml =  request.getParameter("fjml");
//		String fjlbDivId =  request.getParameter("fjlbDivId");
//		try {
//			//中文转码
//			fileName = new  String(fileName.getBytes("ISO-8859-1"),"GBK");
//			//父表id
//			//附件目录
//			int index = fileName.lastIndexOf('.');
//			String path = ServletActionContext.getServletContext().getRealPath(
//					uploadDir);
//			String xiangDuiPath = "/uploadFile/"+fjml+"/";
//			File dir = new File(path+xiangDuiPath);
//			if (!dir.exists())
//				dir.mkdir();// 创建个文件夹
//			if (index != -1)
//				newFileName = fileName.substring(0, index) + "-" + now
//				+ fileName.substring(index);// 生成新文件名
//			else
//				newFileName = fileName + "-" + now;
//			FileInputStream fis = new FileInputStream(file); 
//			bis = new BufferedInputStream(fis);
//			FileOutputStream fos = new FileOutputStream(new File(dir,
//					newFileName));
//			bos = new BufferedOutputStream(fos);
//			byte[] buf = new byte[4096];
//			int len = -1;
//			while ((len = bis.read(buf)) != -1) {
//				bos.write(buf, 0, len);
//			}
//			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(SysConstants.SESSION_USERINFO); 
//			//保存到附件表
//			TbOaFjb fjb = new TbOaFjb();
//			//文件名
//			//文件类型
//			String fileType = "";
//			if (index != -1)
//				fileType = fileName.substring(index+1);
//			fjb.setFId(new Long(fId));
//			fjb.setCjr(userInfo.getZwm());
//			fjb.setCjrId(userInfo.getZgId());
//			fjb.setCjsj(DateUtils.date2StringV2(new Date()));
//			fjb.setFjbclj(xiangDuiPath);
//			fjb.setFjbcmc(newFileName);
//			fjb.setFjdx(new Long(file.length()));
//			fjb.setFjlx(fileType);
//			fjb.setFjymc(fileName);
//			Long id = baseService.getId();
//			fjb.setId(id);
//			baseService.insert(fjb);
//		} catch (Exception e) {
//			// ///错误返回
//			e.printStackTrace();
//			try {
//				responseWrite(response, "false");
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			return;
//		} finally {
//			try {
//				if (null != bis)
//					bis.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			try {
//				if (null != bos)
//					bos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		// 最后若没错误返回就这里返回了
//		try {
//	
//			List list = baseService.queryTableContent("TbOaFjb", new String[]{"FId"}, new Object[]{fId}, null, null);
//			StringBuffer html = new StringBuffer();
//			genDiv(list, html,fjlbDivId,true);
//			success= true;
//			responseWrite(response, html.toString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	
	public void wdwdFileUpLoad() {
//		String newFileName = null;
//		BufferedOutputStream bos = null;
//		BufferedInputStream bis = null;
//		long now = new Date().getTime();
//		String uploadDir = "";
//		bos = null;
//		bis = null;
//		HttpServletRequest request = ServletActionContext.getRequest();
//		HttpServletResponse response = ServletActionContext.getResponse();
//		BaseService baseService = (BaseService) ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
//		String fileName = request.getParameter("fileName");
//		String fId = request.getParameter("fId");
//		String fjml =  request.getParameter("fjml");
////		String fjlbDivId =  request.getParameter("fjlbDivId");
//		try {
//			//中文转码
//			fileName = new  String(fileName.getBytes("ISO-8859-1"),"GBK");
//			//父表id
//			//附件目录
//			int index = fileName.lastIndexOf('.');
//			String path = ServletActionContext.getServletContext().getRealPath(
//					uploadDir);
//			String xiangDuiPath = "/uploadFile/"+fjml+"/";
//			File dir = new File(path+xiangDuiPath);
//			if (!dir.exists())
//				dir.mkdir();// 创建个文件夹
//			if (index != -1)
//				newFileName = fileName.substring(0, index) + "-" + now
//				+ fileName.substring(index);// 生成新文件名
//			else
//				newFileName = fileName + "-" + now;
//			FileInputStream fis = new FileInputStream(file); 
//			bis = new BufferedInputStream(fis);
//			FileOutputStream fos = new FileOutputStream(new File(dir,
//					newFileName));
//			bos = new BufferedOutputStream(fos);
//			byte[] buf = new byte[4096];
//			int len = -1;
//			while ((len = bis.read(buf)) != -1) {
//				bos.write(buf, 0, len);
//			}
//			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(SysConstants.SESSION_USERINFO); 
//			//保存到附件表
//			TbOaWd wd = new TbOaWd();
//			//文件名
//			//文件类型
//			String fileType = "";
//			if (index != -1)
//				fileType = fileName.substring(index+1);
//			wd.setCjr(userInfo.getUsername());
//			wd.setCjrId(new Long(userInfo.getZgId()));
//			wd.setCjsj(DateUtils.date2StringV2(new Date()));
//			wd.setWdbclj(xiangDuiPath);
//			wd.setWdbcmc(newFileName);
//			wd.setWddx(new Long(file.length()));
//			wd.setWdlx(fileType);
//			wd.setWdymc(fileName);
//			wd.setId(new Long(fId));
//			baseService.insert(wd);
//		} catch (Exception e) {
//			// ///错误返回
//			e.printStackTrace();
//			try {
//				responseWrite(response,"false");
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			return;
//		} finally {
//			try {
//				if (null != bis)
//					bis.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			try {
//				if (null != bos)
//					bos.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//
//		// 最后若没错误返回就这里返回了
//		try {
//			List list = baseService.queryTableContent("TbOaFjb", new String[]{"FId"}, new Object[]{fId}, null, null);
//			StringBuffer html = new StringBuffer();
////			genDiv(list, html,fjlbDivId);
//			responseWrite(response, html.toString());
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public void downLoad(){
//		try {
//			HttpServletResponse response = ServletActionContext.getResponse();
//			HttpServletRequest request = ServletActionContext.getRequest();
//			String id = request.getParameter("id");
//			BaseDao baseDao = (BaseDao) ApplicationComponentStaticRetriever.getComponentByItsName("baseDao");
//			TbOaFjb fjb = (TbOaFjb) baseDao.get(TbOaFjb.class, new Long(id));
//			String fileName = fjb.getFjbcmc();
//			String realName =  fjb.getFjymc();
//			String path =  fjb.getFjbclj();
//			String realpath = request.getRealPath("");
//			response.setContentType("application/multipart");
//			response.setHeader("Content-Disposition", "attachment;filename="
//					+ new  String(realName.getBytes("UTF-8"),"ISO-8859-1"));
//			ServletOutputStream out = response.getOutputStream();
//			int count = 0;
//			int size = 254;
//			byte[] data = new byte[254];
//			File file = new File(realpath+"/"+path+"/"+fileName);
//			if(file.exists()){
//				FileInputStream inputStream = new FileInputStream(file);
//				BufferedInputStream buInputStream = new BufferedInputStream(
//						inputStream, size);
//				
//				while ((count = buInputStream.read(data, 0, size)) != -1) {
//					out.write(data, 0, count);
//					out.flush();
//				}
//				inputStream.close();
//				out.close();
//			}else{
//				response.setHeader("Content-Disposition", "attachment;filename="
//						+ new  String("文件不存在或已删除".getBytes("UTF-8"),"ISO-8859-1"));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		}

	}
	
	public void downLoadWdwd(){
//		try {
//			HttpServletResponse response = ServletActionContext.getResponse();
//			HttpServletRequest request = ServletActionContext.getRequest();
//			String id = request.getParameter("id");
//			BaseDao baseDao = (BaseDao) ApplicationComponentStaticRetriever.getComponentByItsName("baseDao");
//			TbOaWd wd = (TbOaWd) baseDao.get(TbOaWd.class, new Long(id));
//			String fileName = wd.getWdbcmc();
//			String realName =  wd.getWdymc();
//			String path =  wd.getWdbclj();
//			String realpath = request.getRealPath("");
////			response.setContentType("application/x-msdownload");
//			response.setContentType("application/multipart");
////			response.setContentType("application/octet-stream");
//			response.setHeader("Content-Disposition", "attachment;filename="
//					+ new String(realName.getBytes("UTF-8"),"ISO-8859-1"));
//			OutputStream out = response.getOutputStream();
//			int count = 0;
//			int size = 254;
//			byte[] data = new byte[254];
//			File file = new File(realpath+"/"+path+"/"+fileName);
//			if(file.exists()){
//				FileInputStream inputStream = new FileInputStream(file);
//				BufferedInputStream buInputStream = new BufferedInputStream(
//						inputStream, size);
//				
//				while ((count = buInputStream.read(data, 0, size)) != -1) {
//					out.write(data, 0, count);
//					out.flush();
//				}
//				inputStream.close();
//				out.close();
//			}else{
//				response.setHeader("Content-Disposition", "attachment;filename="
//						+ new  String("文件不存在或已删除".getBytes("UTF-8"),"ISO-8859-1"));
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		}

	}
	
	public void deleteFuJian(){
//		HttpServletResponse response = ServletActionContext.getResponse();
//		HttpServletRequest request = ServletActionContext.getRequest();
//		try {
//			String id = request.getParameter("id");
//			String fId = request.getParameter("fId");
//			String fjlbDivId =  request.getParameter("fjlbDivId");
//			BaseDao baseDao = (BaseDao) ApplicationComponentStaticRetriever.getComponentByItsName("baseDao");
//			BaseService baseService = (BaseService) ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
//			TbOaFjb delfjb = new TbOaFjb();
//			delfjb.setId(new Long(id));
//			delfjb =(TbOaFjb) baseDao.get(TbOaFjb.class, new Long(id));
//			if(null!=delfjb){
//				baseDao.delete(delfjb);
//			}
//			String path = ServletActionContext.getServletContext().getRealPath("");
//			String xdPath = delfjb.getFjbclj();
//			File file = new File(path+"/"+xdPath+"/"+delfjb.getFjbcmc());
//			if(file.isFile()){
//				if(file.exists()){
//					file.delete();
//				}
//			}
//			List list = baseService.queryTableContent("TbOaFjb", new String[]{"FId"}, new Object[]{fId}, null, null);
//			StringBuffer html = new StringBuffer();
//			genDiv(list, html,fjlbDivId,false);
//			responseWrite(response, html.toString());
//		} catch (Exception e) {
//			responseWrite(response,"false");
//			logger.error(e);
//			e.printStackTrace();
//		}
	}
	
	public void queryFuJian(){
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String fId = request.getParameter("fId");
			String deltmp = request.getParameter("noDelete");
			boolean noDelete = false;
			if(null!=deltmp&&"true".equals(deltmp.trim())){
				noDelete =true;
			}else if(null!=deltmp&&"false".equals(deltmp.trim())){
				noDelete =false;
			}
			String fjlbDivId =  request.getParameter("fjlbDivId");
			BaseService baseService = (BaseService) ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
			List list = baseService.queryTableContent("TbOaFjb", new String[]{"FId"}, new Object[]{fId}, null, null);
			StringBuffer html = new StringBuffer();
			genDiv(list, html,fjlbDivId,noDelete);
			responseWrite(response, html.toString());
		} catch (Exception e) {
			responseWrite(response,"false");
			logger.error(e);
			e.printStackTrace();
		}
	}
	
	public void uploadImage() throws Exception{
		String newFileName = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;
		long now = new Date().getTime();
//		String uploadDir = storePath.getXgxjImgPath()+"/";
		bos = null;
		bis = null;
		HttpServletRequest request = ServletActionContext.getRequest();
		/*String enCoding = request.getCharacterEncoding();
		System.out.println(enCoding);*/
		HttpServletResponse response = ServletActionContext.getResponse();	
		BaseService baseService = (BaseService) ApplicationComponentStaticRetriever.getComponentByItsName("baseService");
		String fileName = request.getParameter("fileName");
		String fjml =  request.getParameter("zpml");
//		String fjlbDivId =  request.getParameter("zplbDivId");
		TbXsjsPhoto fjb = new TbXsjsPhoto();
		try {
			//中文转码
			fileName = new  String(fileName.getBytes("ISO-8859-1"),"GBK");
			//父表id
			//附件目录
			int index = fileName.lastIndexOf('.');
			String path = StorePath.getXgxjImgPath();
			String xiangDuiPath = "/"+fjml+"/";
			File dir = new File(path+xiangDuiPath);
			if (!dir.exists())
				dir.mkdir();// 创建个文件夹
			if (index != -1)
				newFileName = now
				+ fileName.substring(index);// 生成新文件名
			else
				newFileName = ""+now;
			FileInputStream fis = new FileInputStream(file); 
			bis = new BufferedInputStream(fis);
			FileOutputStream fos = new FileOutputStream(new File(dir,
					newFileName));
			bos = new BufferedOutputStream(fos);
			byte[] buf = new byte[4096];
			int len = -1;
			while ((len = bis.read(buf)) != -1) {
				bos.write(buf, 0, len);
			}
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute(SysConstants.SESSION_USERINFO); 
			//保存到附件表
			//文件名
			//文件类型
			String fileType = "";
			if (index != -1)
				fileType = fileName.substring(index+1);
			fjb.setZpbclj(xiangDuiPath);
			fjb.setZpbcmc(newFileName);
			fjb.setZpdx(new Long(file.length()));
			fjb.setZplx(fileType);
			fjb.setZpymc(fileName);
			Long id = baseService.getId();
			fjb.setId(id);
			baseService.insert(fjb);
		} catch (Exception e) {
			// ///错误返回
			e.printStackTrace();
			try {
				response.setContentType("text/html");
				response.getWriter().write("{success:false}");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;
		} finally {
			try {
				if (null != bis)
					bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (null != bos)
					bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 最后若没错误返回就这里返回了
		try {
	
			List list = baseService.queryTableContent("TbXsjsPhoto", new String[]{"id"}, new Object[]{fjb.getId()}, null, null);
			StringBuffer html = new StringBuffer();
			genPhotoJson(list, html);
			response.setContentType("text/html");
			response.getWriter().write(html.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void downLoadImage(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			String id = request.getParameter("id");
			BaseDao baseDao = (BaseDao) ApplicationComponentStaticRetriever.getComponentByItsName("baseDao");
			Long idl=0l;
			if (!id.isEmpty()) {  //如果照片ID不为空
				idl=Long.parseLong(id);
			
				TbXsjsPhoto fjb = (TbXsjsPhoto) baseDao.get(TbXsjsPhoto.class, idl);
				String fileName = fjb.getZpbcmc();
				String realName =  fjb.getZpymc();
				String path =  fjb.getZpbclj();
				String realpath = StorePath.getXgxjImgPath();
				response.setContentType("application/multipart");
				response.setHeader("Content-Disposition", "attachment;filename="
						+ new  String(realName.getBytes("UTF-8"),"ISO-8859-1"));
				ServletOutputStream out = response.getOutputStream();
				int count = 0;
				int size = 254;
				byte[] data = new byte[254];
				File file = new File(realpath+"/"+path+"/"+fileName);
				if(file.exists()){
					FileInputStream inputStream = new FileInputStream(file);
					BufferedInputStream buInputStream = new BufferedInputStream(
							inputStream, size);
					
					while ((count = buInputStream.read(data, 0, size)) != -1) {
						out.write(data, 0, count);
						out.flush();
					}
					inputStream.close();
					out.close();
			}
		}else{
				response.setHeader("Content-Disposition", "attachment;filename="
						+ new  String("文件不存在或已删除".getBytes("UTF-8"),"ISO-8859-1"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}

	}
	
	private void responseWrite(HttpServletResponse response, String html) {
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/plain;charset=GBK");
//		ServletUtils.setNoCacheHeader(response);
//		Struts2Utils.renderText(html, "no-cache:true");
		try {
			response.setContentType("text/html");
			response.getWriter().write("{success:true,info:'"+html.toString()+"'}");
		} catch (IOException e) {
			response.setContentType("text/html");
			try {
				response.getWriter().write("{success:'false'}");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	public  static void genDiv(List list, StringBuffer html,String fjlbDivId,boolean noDelete) {
//		html.append("<div style=\"background:#dfe9f5;border:#dfe9f5;\" id=\""+fjlbDivId+"\">");
//		if(null!=list&&list.size()>0){
//			for(int i=0;i<list.size();i++){
//				TbOaFjb fjb = (TbOaFjb) list.get(i);
//				if(noDelete){
//					html.append("<a href=\"uploadFileAction!downLoad.action?id="+fjb.getId()+"\" target=_blank>"+fjb.getFjymc()+"</a><br>");
//				}else{
//					html.append("<a href=\"uploadFileAction!downLoad.action?id="+fjb.getId()+"\" target=_blank>"+fjb.getFjymc()+"</a><img src=images/cross.gif align=middle alt=删除 onclick=\"deleteFuJian("+fjb.getId()+","+fjb.getFId()+","+fjlbDivId+");\"><br>");
//				}
//			}
//		}
//		html.append("</div>");
	}
	private void genPhotoJson(List list, StringBuffer html) {
		html.append("{");
		html.append("\"success\":true,");
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				TbXsjsPhoto fjb = (TbXsjsPhoto) list.get(i);
				html.append("\"id\":"+fjb.getId()+",\"zpymc\":\""+fjb.getZplx()+fjb.getZpymc()+"\"");
			}
		}
		html.append("}");
	}
}
