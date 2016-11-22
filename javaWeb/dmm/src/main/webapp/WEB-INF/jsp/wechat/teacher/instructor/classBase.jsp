<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/wechat/style2.css" />
	<title>辅导员中心</title>
</head>
<body>

<div class="index">
  <div class="index_title">辅导员中心</div>
</div>
<div class="body_main">
	<div class="fd-content" style="float: right;">
		<a class="fd-link" style="color: #fb0202;" href="${ctx}/wechat/teacher/instructor/web/classwarn/latedorm/${tea.no_}">重点关注</a>
	</div><br>
	<div class="fd-content">
		欢迎您 ${tea.name_ } 老师，您现在负责 ${fn:length(classes)} 个班
	</div>
	<c:forEach items="${classes }" var="clas">
	  <div class="fd-content">
	  <div style="margin-bottom: 0.4em;">
	  	<span style="font-size: 1.2em;">${clas.class_NAME}</span><span style="font-size: 0.95em;color: #8C8C8C;margin-left: 0.8em;">${clas.ZY_NAME}</span>
	  </div>
	    <div class="fd-border">
	      <p class="fd-p">人数：${clas.NAN_+clas.NV_}人<span class="fd-imp"> (男：${clas.NAN_}人，女：${clas.NV_}人)</span></p>
	      <hr class="fd-hr" />
	      <p  class="fd-p">班长：${clas.BZ} </p>
	      <p class="fd-p">联系电话：${clas.BZTEL}</p>
	      <p class="fd-right"><a class="fd-link" href="${ctx }/wechat/teacher/instructor/web/classbase/${clas.class_ID}/duties">班干部</a>
	      <a class="fd-link" href="${ctx }/wechat/teacher/instructor/web/classbase/${clas.class_ID}/all">花名册</a></p>
	    </div>
	  </div>
	</c:forEach>
</div>
</body>
</html>