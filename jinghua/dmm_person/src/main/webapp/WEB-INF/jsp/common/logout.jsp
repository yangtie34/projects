<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.net.URLEncoder" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
session.invalidate();
String casLogoutURL=application.getInitParameter("casServerLogoutUrl");
	   casLogoutURL="http://localhost:8080/dmm_cas/logout";
String redirectURL=casLogoutURL+"?service="+URLEncoder.encode(basePath);
response.sendRedirect(redirectURL);
%>
