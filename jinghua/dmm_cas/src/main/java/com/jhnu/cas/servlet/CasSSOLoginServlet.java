package com.jhnu.cas.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

public class CasSSOLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 2846819435342971388L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getContextPath(); 
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
		String ticket=request.getParameter("ticket");
		if(StringUtils.hasLength(ticket)){
			response.sendRedirect(basePath+"/login?password="+ticket+"&auto=true&autoType=cas");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
