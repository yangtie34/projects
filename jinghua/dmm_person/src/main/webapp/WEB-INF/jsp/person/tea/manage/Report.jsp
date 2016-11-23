<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>高基报表</title>
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/manage/TeaReportController.js"></script>
</head>

<body ng-controller="TeaReportController">
<jsp:include page="../../top.jsp"></jsp:include>
<section>
</section>
<article>

高基报表

</article>
</body>