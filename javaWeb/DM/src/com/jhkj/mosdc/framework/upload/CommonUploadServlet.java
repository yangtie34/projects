package com.jhkj.mosdc.framework.upload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nobject.common.file.FileUtils;
import org.nobject.common.lang.StringUtils;
import org.nobject.mmvc.upload.UploadContext;
import org.nobject.mmvc.upload.UploadPolicy;
import org.nobject.mmvc.upload.UploadServletHandler;

import com.jhkj.mosdc.framework.bean.ApplicationComponentStaticRetriever;

/**
 * 通用上传Servlet
 *
 * @author bianrongjun
 * @version 1.0
 */
public class CommonUploadServlet extends HttpServlet{
	
	/** jsrpcInvoker */
	UploadServletHandler uploadServletHandler;
	
	public static String path=null;
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {
		uploadServletHandler=new CommonUploadServletHandler();
		path=this.getServletContext().getRealPath("/");
		
		//清空Session上传文件夹
		try {
			FileUtils.delete(path+File.separator+"uploadFile"+File.separator+"session");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPost(req, resp);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//同步字符集
		if(StringUtils.isEmpty(request.getCharacterEncoding())) request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding(request.getCharacterEncoding());
		
		uploadServletHandler.handle0(this, request, response);
	}
	
}

/**
 * CommonUploadServletHandler
 *
 * @author bianrongjun
 * @version 1.0
 */
class CommonUploadServletHandler extends UploadServletHandler{

	/* (non-Javadoc)
	 * @see org.nobject.mmvc.upload.UploadServletHandler#getPolicy(org.nobject.mmvc.upload.UploadContext)
	 */
	@Override
	public UploadPolicy getPolicy(UploadContext context) throws Exception {
		String policyName=(String)context.queriesMap.get("policy");
		return (UploadPolicy)ApplicationComponentStaticRetriever.getComponentByItsName(policyName);
	}
	
}
