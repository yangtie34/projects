<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>欢迎</title>
</head>
<body>
<%  
String basePath = request.getContextPath();
int port = request.getServerPort();
String ip = request.getServerName();
String projectPath = "http://"+ip+":"+port+basePath;
response.sendRedirect(projectPath+"/main");  
%>  
</body>
</html>