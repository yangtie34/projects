<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
	<title>Employees</title>
	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
</head>
<body>
登录成功${ctx }
<br>${ctxStatic }
方法名称：${mothodName }
<br>
权限：${rolePermi }

<form action="/dmm/employee" method="post">
	<input type="submit" value="增加">
</form>
<form action="/dmm/employee/1" method="post">
	<input type="hidden" name="_method" value="delete"/>
	<input type="submit" value="删除">
</form>
<form action="/dmm/employee/1" method="post">
	<input type="hidden" name="_method" value="put"/>
	<input type="submit" value="修改">
</form>
<form action="/dmm/employee/1" method="get">
	<input type="submit" value="查看">
</form>
<script type="text/javascript">
$(document).ready(function (){
	alert("123");
});
</script>
</body>
</html>