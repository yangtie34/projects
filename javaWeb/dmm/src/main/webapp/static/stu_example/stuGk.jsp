<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html ng-app="app">
<head>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="css/right-common.css" />
<link rel="stylesheet" type="text/css" href="css/right.css" />
<link rel="stylesheet" type="text/css" href="css/xiala.css" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"/>
<meta http-equiv="X-UA-Compatible" content="text/html; charset=UTF-8">
<meta name="description" content="">
<meta name="author" content="">
<title>学生概况</title>
<jsp:include page="baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="../js/charts/echarts/dist/echarts.js"></script>
<script type="text/javascript" src="../js/charts/echarts/dist/echarts-all.js"></script>
<script type="text/javascript" src="js/stuGkController.js"></script>
<script type="text/javascript" src="js/stuCharts.js"></script>
<script type="text/javascript" src="js/echartService.js"></script>
<style type="text/css">
@CHARSET "UTF-8";
</style>
</head>
<body ng-controller="stuGkController">
<div class="shuju-con-right">
<!--右侧头部开始-->
<div class="shuju-right-title clearfix">
  <div class="shuju-title-left">
    <h3>在校生基本概况统计</h3>
    <p>从学生性别、年龄、民族、政治面貌、学历、年级、生源地等多方面分析在校生分布组成情况。</p>
  </div>
  <div class="shuju-title-right">
    <ul class="shuju-title-search">
      <li class="shuju-chaxun">高级查询</li>
      <li class="shuju-search">探索</li>
      <li class="shuju-daochu">导出</li>
    </ul>
  </div>
</div>
<!--右侧头部结束-->
<!--学院选择-->
<div class="shuju-content-list">
  <div class="shuju-title-box">
    <h2 class="shuju-title" ng-repeat="sc in school" ><span ng-if="$index != 0"> > </span><span ng-click="biaoTi(sc.ID)" ng-if="$index !=school.length-1" style="cursor:pointer;">{{sc.NAME}}</span><span ng-if="$index == school.length-1">{{sc.NAME}}</span></h2>
    <div class="shuju-right-search">
      <input class="shuju-search-input" placeholder="查询组织机构">
      <a href="#" class="shuju-search-icon"></a> </div>
  </div>
   <div class="shuju-xueyuan-select-box"><span class="shuju-span-list" ng-repeat="yx in yxs" ng-click="daoHang(yx.ID)">{{yx.NAME}}</span>
</div></div>
<!--学院选择-->
 <div class="shuju-content-information">
  <div class="shuju-information-table"><article class="shuju-information-35"><section class="shuju-information-left"><p>{{stu.name}}</p> 
   <p class="shuju-text-green-30"> {{stu.rs}}人 </p>
    <p class="shuju-text-org-15" ng-if= "stu.count >1">共{{stu.count}}个<a class="shuju-underline" href="#" title="专业详情" >专业</a></p></sectionv></article>
  <article class="shuju-information-25">
    <section class="shuju-information-center"><dl class="shuju-center-dl">
<dt class="shuju-center-img-boy"> </dt><dd class="shuju-text-blue-22">{{stu.nanrs}}人</dd><dd class="shuju-center-dd"><span class="shuju-text-12">占比</span>{{((stu.nanrs/stu.rs)*100)|number:1}}%</dd>
</dl></section>
    <section class="shuju-information-center"><dl class="shuju-center-dl">
<dt class="shuju-center-img-girl"> </dt><dd class="shuju-text-org-22">{{stu.nvrs}}人</dd><dd class="shuju-center-dd"><span class="shuju-text-12">占比</span>{{((stu.nvrs/stu.rs)*100)|number:1}}%</dd>
</dl></section>
  </article>
  <article class="shuju-information-40">
  <table class="shuju-spacing-td" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr ng-repeat='xls in xl'>
   <td><section><span class="shuju-float-left-10">{{xls.NAME}}</span><i class="shuju-text-green-26">{{xls.RS}}人</i><em class="shuju-float-right-10"><span class="shuju-text-12">占比</span>{{((xls.RS/stu.rs)*100)|number:1}}%</em></section></td>
 </tr></table></article></div>
</div> 
<!--统计图-->
 <div class="shuju-content-list">
  <div id="shuju-box">
    <div class="shuju-fenbutu-box clearfix">
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>生源地分布</h4>
      	<div stu-chart config = "mapChart" class="shuju-border-dance" style="height:323px;"></div>
      </div>
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>学生平均年龄分布</h4>
        <div stu-chart config = "areaChart" class="shuju-border-dance"style="height:323px;"></div>
      </div>
    </div>
    <div class="shuju-fenbutu-box clearfix">
      <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>院系人员分布</h4>
      <div stu-chart config = "columnChart" class="shuju-border-dance"  style="height:323px;"></div>
    </div>
    <div class="shuju-fenbutu-box clearfix">
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>政治面貌</h4>
         <div class="shuju-border-dance" ><h5>中国共青团团员{{zzmm.ty}}人，占比  {{((zzmm.ty/stu.rs)*100)|number:1}}%；其他类别{{zzmm.bsty}}人，占比 {{((zzmm.bsty/stu.rs)*100)|number:1}}%；未维护 {{zzmm.wwh}}人，占比{{((zzmm.wwh/stu.rs)*100)|number:1}}%。</h5><div stu-chart config = "columnfChart" style="height:256px;"></div></div>
      </div>
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>民族分布形式</h4>
        <div class="shuju-border-dance" ><h5>汉族{{mzcount.hz}}人，占比  {{((mzcount.hz/stu.rs)*100)|number:1}}%；少数民族{{mzcount.ssmz}}人，占比 {{((mzcount.ssmz/stu.rs)*100)|number:1}}%；未维护 {{mzcount.wwh}}人，占比{{((mzcount.wwh/stu.rs)*100)|number:1}}%。</h5><div stu-chart config = "column1Chart" style="height:256px;"></div></div>
        
      </div>
    </div>
  </div>
</div> 
</body>
</html>
