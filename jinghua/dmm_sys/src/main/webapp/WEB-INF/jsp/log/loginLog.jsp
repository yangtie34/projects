<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录日志</title>
<jsp:include page="../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/log/loginLog.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />
<body ng-controller="loginLogController">
<div class="content">
<!--左侧导航开始-->
		<jsp:include page="../manager/menu.jsp"></jsp:include>
		<!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
    <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>登陆日志</h3>
      </div>
    </div>
    <!--右侧头部结束-->
	<!--右侧头部结束-->
    <div class="shuju-content-list">
      <div class="quanxian-content">
        <!--搜索-->
      	<div cg-mul-query-comm source="mutiSource" result="page.conditions" noborder="true" expand="false"></div>	
      	<br>
      	<div class="quanxian-block-search" style="position:inherit;">
          <div class="" style="float:right;">
              <button type="button" style="outline:none;" ng-click="logClear()" class="quanxian-btn-default"><span class=" ">清空日志</span></button> 
            <button type="button" style="outline:none;" ng-click="exportExcel()" class="quanxian-btn-default"><span class=" ">导出</span></button> 
            </div>
		<table class="quanxian-sub-table " width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr> <th ng-repeat="item in title.names">
				<a href="" ng-click="orderReload($index);" ng-class="title.click[$index]==true?'':'dropup'">{{item}} <span ng-class="title.click[$index]!=null?'caret':''"></span></a>
				</th> </thead>
			<tbody>
				<tr ng-repeat="item in vm.items"  class="quanxian-bg-hover">
					<td ng-repeat="code in title.codes">{{item[code]}}</td>
				</tr>
			</tbody>
		</table>
		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
				<select ng-model="page.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div>
      </div>
    </div>
  </div>
</div>
</div>
</body>
</html>
