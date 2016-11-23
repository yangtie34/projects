<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<link rel="stylesheet" type="text/css" 　media="screen and (max-device-width: 400px)" 　href="tinyScreen.css" />
<link rel="stylesheet" type="text/css" 　media="screen and (min-width: 400px) and (max-device-width: 600px)" 　href="smallScreen.css" />

<jsp:include page="../topCssJs.jsp"></jsp:include>
<link rel="stylesheet" href="${ctxStatic }/css/manager/zengjia.css">

<script type="text/javascript" src="${ctxStatic }/js/charts/d3/d3.js"></script>

<script type="text/javascript" src="${ctxStatic }/product/manager/d3tree/D3Tree.js"></script>
<meta name="description" content="">
<meta name="author" content="">
<title>学生系统</title>
</head>
<body>
	<div class="content">
		<!--左侧导航开始-->
		<jsp:include page="../menu.jsp"></jsp:include>
		<!--左侧导航结束-->
		<!--右侧内容开始-->
		<div class="con_right">
			<!--右侧头部开始-->
			<!--右侧头部结束-->
			<!--专业及人数开始-->
			<!-- 统计图表结束-->
			<!-- 分布表开始-->
			<div>
				<div class="zhibiao_box">
				<div class="zhibiao_tu">
				<div  id="deptTeachTree" style="display: block;width: 800px; height: 100%">
				</div>
				</div>
				</div>
				<!-- 分布表结束-->
			</div>
			<!--右侧内容结束-->
		</div>
	<script>
			$(function() {
				$.callService('deptTreeService', 'getDeptTeach', null, function(d) {
					var tree=new dmm.D3Tree(d,document.getElementById("deptTeachTree"));
				});
			});
	</script>
</body>
</html>
