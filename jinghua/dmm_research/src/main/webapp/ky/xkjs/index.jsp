<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/ky/xkjs/index.jsp"/>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>科研项目主持人分析</title>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
<%-- 这是页面的整体架子 --%>
<div class="container-fluid">
	<div class="keyan-main"  ng-controller="tabController">
		<div class="header">
			<div class="main-tit">
				<h4 class="main-tit-b">全校学科建设情况</h4>
				<h5 class="main-tit-s">从多项指标对学校的学科建设情况进行分析呈现。</h5>
			</div>
		</div>
		<div style="margin:0 30px;">
			<div class="p-top-10 pull-right" >
				<button class="btn btn-default" ng-click="refreshPageData()">刷新数据</button>
			</div>
			<!-- Nav tabs -->
			<ul class="nav nav-tabs p-top-10" role="tablist">
		  	  <li ng-class="{active : currentTab == 1}"><a href="#/ryzc">学科点及人员信息</a></li>
			  <li ng-class="{active : currentTab == 2}"><a href="#/zbjz">学科业绩指标进展</a></li>
			  <li ng-class="{active : currentTab == 3}"><a href="#/fxzb">各学科分项指标建设进展</a></li>
			  <li ng-class="{active : currentTab == 4}"><a href="#/zb">学科业绩指标</a></li>
			  <li ng-class="{active : currentTab == 5}"><a href="#/zbwcl">学科业绩指标完成率</a></li>
			  <li ng-class="{active : currentTab == 6}"><a href="#/grzbwcl">个人指标完成率</a></li>
			</ul>
		</div>
		<ng-view></ng-view>
	</div>
</div>
</body>
</html>