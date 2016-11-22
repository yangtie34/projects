package cn.gilight.framework.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import cn.gilight.framework.uitl.SysConfig;

import com.alibaba.fastjson.JSONObject;
import com.jhnu.syspermiss.util.ContextHolderUtils;
/**   
* @Description: 上传工具
* @author Sunwg
* @date 2016年8月2日 下午4:49:06   
*/
@Controller
@RequestMapping("/common/upload")
public class UploadController {
	Logger log = Logger.getLogger(this.getClass());
	
	/** 
	* @Description: 异步上传图片
	* @param request
	* @param response
	* @throws Exception 
	* @return: Object
	*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("/image")
	@ResponseBody
	public Object uploadTypeImage(HttpServletRequest request,HttpServletResponse response) throws Exception{
		 String rootPath_ = request.getSession().getServletContext().getRealPath("/");
		 SysConfig config = SysConfig.instance();
		 String username = "";
		 try{
			 AttributePrincipal principal = (AttributePrincipal) ContextHolderUtils.getRequest().getUserPrincipal();
			 username = principal.getName();
		 }catch(Exception e){
			 username = "ohters";
			 log.debug("未登陆用户上传图片，统一保存到 ohters");
		 }
		 String uploadSavePath =  config.getUploadImageSavePath() + username + "/";
		try {
			String savePath = rootPath_ + uploadSavePath;
			//返回URL
			String returnUrl = uploadSavePath;
			// 最大文件大小
			long maxSize = 1024000;
			response.setContentType("text/html; charset=UTF-8");

			if (!ServletFileUpload.isMultipartContent(request)) {
				return getError("请选择文件。");
			}
			// 检查目录
			File uploadDir = new File(savePath);
			if (!uploadDir.isDirectory()) {
				uploadDir.mkdir();
			}
			// 检查目录写权限
			if (!uploadDir.canWrite()) {
				return getError("上传目录没有写权限。");
			}
			// 创建文件夹
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			savePath += ymd + "/";
			returnUrl += ymd + "/";
			File dirFile = new File(savePath);
			if (!dirFile.exists()) {
				dirFile.mkdirs();
			}
			HashMap<String, String> extMap = new HashMap<String, String>();
			extMap.put("image", "gif,jpg,jpeg,png,bmp");
			DefaultMultipartHttpServletRequest mRequest = (DefaultMultipartHttpServletRequest) request;
			Map map = mRequest.getFileMap();
			Collection<MultipartFile> c = map.values();
			Iterator itr = c.iterator();
			while (itr.hasNext()) {
				CommonsMultipartFile file = (CommonsMultipartFile) itr.next();
				if (!file.isEmpty()) {
					long fileSize = file.getSize();
					String fileName = file.getOriginalFilename();
					String fileExt = fileName.substring(
							fileName.lastIndexOf(".") + 1).toLowerCase();
					if (fileSize > maxSize) {
						return getError("上传文件大小超过限制");
					}
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String newFileName = df.format(new Date()) + "_"
							+ new Random().nextInt(1000) + "." + fileExt;
					try {
						File uploadedFile = new File(savePath, newFileName);
						file.transferTo(uploadedFile);
					} catch (Exception e) {
						return getError("上传文件失败。");
					}
					JSONObject obj = new JSONObject();
					obj.put("error", 0);
					obj.put("message", "文件上传成功");
					obj.put("url", returnUrl + newFileName);
					//obj.put("path", savePath + newFileName);
					return obj;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return getError("上传文件失败。");
		}
		return null;
	}
	
	private Object getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj;
	}
}
