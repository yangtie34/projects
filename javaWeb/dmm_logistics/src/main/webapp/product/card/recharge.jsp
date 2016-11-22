<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
  <head>
  <%--   <base href="<%=basePath%>"> --%>
    <jsp:include page="../../static/base.jsp"></jsp:include>
    <title>学生充值统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body ng-controller="rechargeController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/card/js/recharge.js"></script>   
  <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">学生充值统计</h3>
    <p class="xscz-default">对学生充值情况进行统计分析。</p>
  </div>
  

  <div class="xscz-box content_main">
  <div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	  
    <div class="xscz-nav">
      <div class="xscz-tab-ct xscz-ft-18">
        <div class="row">
          <div class="col-xs-3">
            <h3 class="xscz-ct-1">充值总金额 <span class="icon-time"  ng-click="qushiClick(0);" title="趋势统计"></span></h3>
            <h4>{{vm.items[0].ALL_MONEY}}</h4>
            <p>元</p>
          </div>
          <div class="col-xs-3">
            <h3 class="xscz-ct-2">充值总次数 <span class="icon-time"  ng-click="qushiClick(1);"title="趋势统计"></span></h3>
            <h4>{{vm.items[0].ALL_COUNT}}</h4>
            <p>次</p>
          </div>
          <div class="col-xs-3">
            <h3 class="xscz-ct-3">充值总人数 <span class="icon-time"  ng-click="qushiClick(2);"title="趋势统计"></span></h3>
            <h4>{{vm.items[0].ALL_STU}}</h4>
            <p>人</p>
          </div>
          <div class="col-xs-3">
            <h3 class="xscz-ct-4">人均充值 <span class="icon-time"  ng-click="qushiClick(3);"title="趋势统计"></span></h3>
            <h4>{{vm.items[0].STU_MONEY|number:2}}</h4>
            <p>元</p>
          </div>
        </div>
      </div>
      <!-- /* 统计*/-->
      <div class="xscz-tj">
        <div class="xscz-cit text-center"><span>各{{deptlname}}充值情况</span></div> 
        <div class="row text-center">
          <div class="col-xs-12">
          <div stu-chart config="vm.items[1]" style="height:310px;"class="img-responsive img-top"> </div>
             <%-- <img src="${images}/01.jpg">  --%>
          </div> 
        </div>
      </div>
      <!-- /* 统计*/-->
      <div class="xscz-tj">
        <div class="xscz-cit text-center"><span>充值习惯分析</span></div> 
        <div class="row text-center">
          <div class="col-xs-6">
             <h4 class="xscz-ft-16">单次充值金额次数分布</h4>
            <div stu-chart config="vm.items[2]" style="height:310px;"class="img-responsive img-top"> </div>
           <%--  <img src="${images}/02.jpg">  --%>
          </div>
          <div class="col-xs-6">
             <h4 class="xscz-ft-16">充值前所剩余额分布</h4>
            <div stu-chart config="vm.items[3]" style="height:310px;"class="img-responsive img-top"> </div>
            <%-- <img src="${images}/03.jpg">  --%>
          </div> 
        </div>
      </div>
    <!-- /* 统计*/-->
    <div class="xscz-tj">
        <div class="xscz-cit text-center"><span>各类型充值情况</span></div> 
        <div class="row text-center">
          <div class="col-xs-6">
             <h4 class="xscz-ft-16">充值次数</h4>
            <div stu-chart config="vm.items[4][0]" style="height:310px;"class="img-responsive img-top"> </div>
           <%--  <img src="${images}/04.jpg">  --%>
          </div>
          <div class="col-xs-6">
             <h4 class="xscz-ft-16">充值总金额</h4>
            <div stu-chart config="vm.items[4][1]" style="height:310px;"class="img-responsive img-top"> </div>
           <%--  <img src="${images}/04.jpg">  --%>
          </div> 
        </div>
      </div>
    <!-- /* 统计*/-->
    <div class="xscz-tj">
        <div class="xscz-cit text-center"><span>分时段统计各类型充值情况</span></div> 
        <div class="row text-center">
          <div class="col-xs-6">
             <h4 class="xscz-ft-16">充值次数对比趋势</h4>
            <div stu-chart config="vm.items[5][0]" style="height:310px;"class="img-responsive img-top"> </div>
            <%--  <img src="${images}/05.jpg">  --%>
          </div>
          <div class="col-xs-6">
             <h4 class="xscz-ft-16">充值金额对比趋势</h4>
             <div stu-chart config="vm.items[5][1]" style="height:310px;"class="img-responsive img-top"> </div>
           <%--  <img src="${images}/05.jpg">  --%>
          </div> 
        </div>
      </div>

    <!-- /* 统计*/-->
    <div class="xscz-tj">
        <div class="xscz-cit text-center"><span>充值历史情况</span></div> 
        <div class="row text-center">
          <div class="col-xs-12">
             <div class="xscz-slt text-left" >
             <input class="xscz-radio" type="radio" id="rd-1" name="rd0" value="all" ng-model="type67"><label for="rd-1">总体</label>
             <input class="xscz-radio" type="radio" name="rd0" id="rd-2" value="alx" ng-model="type67"><label for="rd-2">按类型</label></div>
            <!--  <h4 class="xscz-ft-16">2004充值类型情况</h4> -->
             <div stu-chart config="type67data" style="height:310px;"class="img-responsive img-top"> </div>
         <%--     <img src="${images}/06.jpg">  --%>
          </div>  
        </div>
      </div>
    <!-- /* 统计*/-->
    </div>
  </div>
 </div>
</div> 
  </body>
  <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
	<div stu-chart config="qushiData" class="qsdivc" ></div>
</div>
</html>
