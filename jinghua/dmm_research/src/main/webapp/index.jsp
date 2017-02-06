<%@page import="com.jhnu.syspermiss.GetCachePermiss"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<%  
	String basePath = request.getContextPath();
	int port = request.getServerPort();
	String ip = request.getServerName();
	String projectPath = "http://"+ip+":"+port+basePath;
	String username=request.getUserPrincipal().getName();
	GetCachePermiss.init(username,projectPath);
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
<meta name="apple-mobile-web-app-status-bar-style" content="yes" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科研系统</title>
</head>
<body>
	<jsp:forward page="/ky/common/index.jsp"></jsp:forward>
</body>
</html>