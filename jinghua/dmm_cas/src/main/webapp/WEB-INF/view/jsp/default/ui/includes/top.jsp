<%@ page pageEncoding="UTF-8" session="false"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>

<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
    <title>数据挖掘分析系统</title>
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/login/css/login.css">
    <style>.error{color:red;}</style>


<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/bootstrap.js"></script>
    <spring:theme code="cas.javascript.file" var="casJavascriptFile" text="" />
    <script type="text/javascript" src="<c:url value="${casJavascriptFile}" />"></script>
    
  <spring:theme code="standard.custom.css.file" var="customCssFile" />
</head>
<body>
