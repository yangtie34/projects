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
  <div class="index_title">重点关注学生</div>
  <input type="button" value=" " id="back" onClick="javascript:window.location.href='${ctx}/wechat/teacher/instructor/web/classbase/${tea.no_}' ">
</div>
<div class="body_main">
	<c:if test="${!empty classWarn }">
		<div class="fd-content">
			同步时间：${classWarn[0].exe_time }
		</div>
	</c:if>
	
	<c:forEach items="${classWarn }" var="warn">
	  <div class="fd-content">
		  <div style="margin-bottom: 0.4em;">
		  	<span style="font-size: 1.2em;">${warn.CLASS_NAME}</span><span style="font-size: 0.95em;color: #8C8C8C;margin-left: 0.8em;">${warn.ZY_NAME}</span>
		  </div>
		  <div class="fd-border">
		      <p class="fd-p">昨日晚寝晚归人数：${warn.RS}人</p>
		      <p class="fd-right"><a class="fd-link" href="${ctx }/wechat/teacher/instructor/web/classwarn/latedorm/stu/${warn.CLASS_ID}">晚寝学生名单</a></p>
		  </div>
	  </div>
	</c:forEach>
</div>
</body>
</html>