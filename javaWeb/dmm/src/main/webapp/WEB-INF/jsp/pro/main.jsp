<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
	<title>数据挖掘分析系-${main.name_ }</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
</head>
<body>
<%@ include file="../layouts/top.jsp"%>
	<div style="margin-left: 25%; margin-top: 15%;"  class="container">
	名称：${main.name_ }<br>
	描述：${main.description }<br>
	<table>
		<c:forEach var="menu" items="${menus }">
			<tr>
				<td>
					<a href="${ctx}${menu.url_ }" target="_blank">${menu.name_ }</a>
				</td>
			</tr>
		</c:forEach>
		
	</table>
</div>
	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
</body>
</html>