<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String root = request.getContextPath(); %>
<!DOCTYPE html >
<html ng-app="app"> 
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="">
<jsp:include page="../../../static/base.jsp"></jsp:include>
<base href="<%=root%>/social/lost/mine/index.jsp"/>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<link rel="stylesheet" type="text/css" href="../../css/rxs-style.css">

<!-- 插件引用 -->
<script type="text/javascript" src="<%=root%>/static/jqform/jquery.form.js"></script>
<title>我的发文</title>
</head>
<body >
<div class="container">
	<div class="row">
		<div class="center-block col-md-8" style="float: none;">
			<div ng-view></div>
		</div>
	</div>
</div>
</body>
</html>