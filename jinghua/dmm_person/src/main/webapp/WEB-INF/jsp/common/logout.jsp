<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.net.URLEncoder" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
session.invalidate();
String casLogoutURL=application.getInitParameter("casServerLogoutUrl");	  
String redirectURL=casLogoutURL+"?service="+URLEncoder.encode(basePath);
response.sendRedirect(redirectURL);
%>
