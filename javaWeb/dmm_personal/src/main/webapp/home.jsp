<%@page import="com.jhnu.syspermiss.util.SysConfig"%>
<%@page import="com.jhnu.syspermiss.GetCachePermiss"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>

<%  
	String basePath = request.getContextPath();
	int port = request.getServerPort();
	String ip = request.getServerName();
	String projectPath = "http://"+ip+":"+port+basePath;
	String username=request.getUserPrincipal().getName();
	GetCachePermiss.init(username,projectPath);
	SysConfig sys = SysConfig.instance();
%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
<meta name="apple-mobile-web-app-status-bar-style" content="yes" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人服务主页</title>
</head>
<body>
	个人服务页面主页
	<br />
	<a href="teacher/index.jsp">教职工个人主页</a>
	<br />
	<a href="student/index.jsp">学生个人主页</a>
	<br />
	<div style="display: none;">
		<a href="permiss.jsp">权限测试页</a>
		<br />
		<a href="common/jobs.jsp">系统中配置的任务调度</a>
		<br />
		<a href="wechat/menu/menu.jsp">微信菜单管理</a>
		<br />
		<a href="wechat/msg/msg.jsp">微信消息管理</a>
		<br />
	</div>
	<a href="<%=sys.getCasServerUrl() %>/logout?service=<%=sys.getCasServerLoginUrl()%>?service=<%=sys.getServerUrl()%>/">退出登录</a>
</body>
</html>