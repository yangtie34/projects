<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
import="com.jhnu.syspermiss.*,com.alibaba.fastjson.JSON"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>后勤系统</title>
<script type="text/javascript" src="${ctx }/static/echarts/echartsUtil.js"></script>
<script type="text/javascript" src="${ctx }/static/index.js"></script>
<link href="${ctx }/resource/css/font-awesome.min.css" rel="stylesheet">
<link href="${ctx }/resource/css/yiqisbguoqiqkfx.css" rel="stylesheet">
 
</head>
<body>
<div id="wrapper" style="height: 100%">
   <jsp:include page="../../product/left.jsp"></jsp:include>
   <div class="content" style="background: #eee;	padding-left:260px;height: 100%" id="main_page"> 
    <iframe width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="YES" allowtransparency="yes" 
    name = "right" src = "${ctx }/index.html">
   </iframe>
   </div>

<div class="clearfix"></div>
</div>
</body>
</html>
