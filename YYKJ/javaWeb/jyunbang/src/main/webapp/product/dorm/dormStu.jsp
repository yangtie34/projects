<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>学生入住情况</title>
</head>
<body ng-controller="dormStuController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/dorm/js/dormStu.js"></script>    
    
        <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">学生入住情况</h3>
    <p class="xscz-default">统计分析学生宿舍入住的相关信息</p>
  </div>
   <div class="xscz-box" style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
  <div cg-mul-query-comm source="mutiSource" result="filterResult" expand="true" noborder="true"></div>	 

	<div>
	<h2 ng-repeat='sc in list' >
	<span ng-if='$index != 0'> > </span>
	<span ng-click='biaoTi(sc,$index)' ng-if='$index !=list.length-1'>{{sc.NAME}}</span>
	<span ng-if='$index == list.length-1'>{{sc.NAME}}</span>
	</h2>
	</div>
	<div><a ng-repeat='yx in yxs' ng-click="daoHang(yx)">----{{yx.NAME}}----</a></div>
	   
  </div>            	
        </div>
        <div class="clearfix"></div>
    </div>
</body>
 <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
 <div stu-chart config="qushiData" class="qsdivc" ></div>
 </div>
 <!-- <div cg-combo-xz data="pagexq" type=''></div> -->
</html>