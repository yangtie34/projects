<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" %>
<!DOCTYPE html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<%  
String basePath = request.getContextPath();
int port = request.getServerPort();
String ip = request.getServerName();
String projectPath = "http://"+ip+":"+port+basePath;
/* String username=request.getUserPrincipal().getName();
GetCachePermiss.init(username,projectPath); */
response.sendRedirect(projectPath+"/main");  
%>  


</body>
</html>
