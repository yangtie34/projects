<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
	String username = request.getParameter("teaId");
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<jsp:include page="../../../static/base.jsp"></jsp:include>
<title>高层次研究成果个人奖励明细</title>
<base href="<%=root%>/ky/award/personal/index.jsp"/>
<script type="text/javascript">
	var usernameparam = <%=username%>;
</script>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
<div class="container-fluid">
<ng-view></ng-view>
</div>
</body>
</html>