<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成绩明细-查询</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/four/score/js/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/four/score/js/controller.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/four/score/js/service.js"></script>
<link type="text/css" rel="stylesheet" href="${ctxStatic}/module/conditions/css/example.css">
</head>
<body ng-controller="controller">
	<div style="width: 1000px;margin: 30px auto 0;">
		<div cg-mul-query-comm source="mutiSource" result="page.conditions">
		</div>	
	</div>
	<!-- 内容布局开始 -->
<div class="sub-content">
  <table class="sub-table" width="100%" border="0" cellspacing="0" cellpadding="0">
     <tr>
        <th  width="17%" class="sub-btm-none" >学年</th> 
        <th  width="17%" class="sub-btm-none" >学期</th>
        <th  width="17%" class="sub-btm-none" >科目</th>
        <th  width="17%" class="sub-btm-none" >百分制成绩</th>
        <th  width="17%" class="sub-btm-none" >等级制成绩</th>
    </tr>
    <tr ng-repeat="item in vm.items">
    <td width="17%" > {{item.SCHOOL_YEAR}} </td>
	<td width="17%" > {{item.TERM_NAME}} </td>
	<td width="17%" > {{item.COURSE_NAME}} 	</td>
	<td width="17%" > {{item.CENTESIMAL_SCORE}} </td>
	<td width="17%" > {{item.HIERARCHICAL_SCORE_CODE}} </td>
    </tr>
  </table>
<!-- 内容布局结束 -->
  <p class="table-info table-inline-block"><span>共{{page.totalPages}}页</span><span>数据 {{page.totalRows}}条</span></p> 
  <div class="table-info table-inline-block set-page-width fright">
      <div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage" 
      	class="pagination-sm pull-right" boundary-links="true"></div>
  </div>
</div>
</body>
</html>
