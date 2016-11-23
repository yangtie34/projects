<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收费管理系统-查询</title>
<jsp:include page="/app/charge/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="page/controller.js"></script>
<script type="text/javascript" src="js/page.js"></script>
<link type="text/css" rel="stylesheet" href="css/example.css">
 <style>
        .page-list .pagination {float:left;}
        .page-list .pagination span {cursor: pointer;}
        .page-list .pagination .separate span{cursor: default; border-top:none;border-bottom:none;}
        .page-list .pagination .separate span:hover {background: none;}
        .page-list .page-total {float:left; margin: 25px 20px;}
        .page-list .page-total input, .page-list .page-total select{height: 26px; border: 1px solid #ddd;}
        .page-list .page-total input {width: 40px; padding-left:3px;}
        .page-list .page-total select {width: 50px;}
    </style>
</head>
<body ng-controller="controller">
	<div cg-page conf="pageconfig"></div>
</body>
</html>
