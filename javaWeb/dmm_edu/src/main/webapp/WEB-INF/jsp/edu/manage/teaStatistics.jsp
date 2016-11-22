<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>统计管理</title>
    <link rel="stylesheet" type="text/css" href="${ctxStatic}/css/statistic/bootstrap.min.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/statistic/leftnav.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/statistic/right-common.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/statistic/right.css" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/css/statistic/xiala.css" />
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/manage/TeaReportController.js"></script>
	    <script type="text/javascript"
	src="${ctxStatic}/person/manage/common/common.js"></script>
</head>
<body ng-controller="TeaReportController">
<nav class="navbar-inverse">
  <div class="container">
    <div class="navbar-header"> <span class="edu-tit" href="#"> 各级在校新生人数变化统计 </span> </div>
    <p class="edu-t-rt"><a href="#" class="navbar-link"><i class="icon-off icon-2x"></i></a></p>
  </div>
</nav>
<div class="edu-alert alert alert-warning alert-dismissible" role="alert">
  <div class="container" style="padding: 0 25px;">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button>
    <i class="icon-exclamation-sign"></i> 从学生性别、年龄、民族、政治面貌、学历、类别、学制、户口性质、科类、年级等多方面分析在校生分布组成情况。 </div>
</div>
<!--右侧头部开始-->
<!-- <div class="shuju-right-title clearfix">
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
</div> -->
<!--右侧头部结束-->
<!--学院选择-->
<!--学院选择-->
<div class="shuju-content-list">
  <div class="shuju-title-box">
    <h2 class="shuju-title" ng-repeat="sc in school" ><span ng-if="$index != 0"> > </span><span ng-click="biaoTi(sc.ID)" ng-if="$index !=school.length-1" style="cursor:pointer;">{{sc.NAME}}</span><span ng-if="$index == school.length-1">{{sc.NAME}}</span></h2>
<!--     <div class="shuju-right-search">
      <input class="shuju-search-input" placeholder="查询组织机构">
      <a href="#" class="shuju-search-icon"></a> </div> -->
  </div>
   <div class="shuju-xueyuan-select-box"><span class="shuju-span-list" ng-repeat="yx in yxs" ng-click="daoHang(yx.ID)">{{yx.NAME}}</span>
</div></div>
<!--学院选择-->
 <div class="shuju-content-information" >
  <div class="shuju-information-table"><article class="shuju-information-23"><section class="shuju-information-left"><p>{{stu.name}}</p> 
   <p class=" shuju-title" style="float:none"> {{school[school.length-1].NAME}} </p><br>
    <p class="shuju-text-org-15" >在职教职工<br><a class="shuju-text-green-30" href="#" title="专业详情" >{{tea.xbrs[0].COUNT_+tea.xbrs[1].COUNT_}}人</a></p></sectionv></article>
  <article class="shuju-information-23">
    <section class="shuju-information-center"><dl class="shuju-center-dl">
<dt class="jiaozhi-boy"> </dt><dd class="shuju-text-blue-22">{{tea.xbrs[0].COUNT_}}人</dd><dd class="shuju-center-dd"><span class="shuju-text-12">占比</span>{{tea.xbrs[0].bl}}</dd>
</dl></section>
    <section class="shuju-information-center"><dl class="shuju-center-dl">
<dt class="jiaozhi-girl"> </dt><dd class="shuju-text-org-22">{{tea.xbrs[1].COUNT_}}人</dd><dd class="shuju-center-dd"><span class="shuju-text-12">占比</span>{{tea.xbrs[1].bl}}</dd>
</dl></section>
  </article>
     <article class="shuju-information-23">
  <table class="shuju-spacing-td shuju-spacing-td1" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr ng-repeat='xls in tea.bzlb'>
   <td><section><span class="shuju-float-left-10">{{xls.NAME_}}</span><br/><i class="shuju-text-green">{{xls.COUNT_}}人</i><em class="shuju-float-right-10"><span class="shuju-text-12">占比</span>{{xls.bl}}</em></section></td>
 </tr>
 
 <tr >
   <td><section><span class="shuju-float-left-10 ">本科生</span><br><i class="shuju-text-green ">33791人</i><em class="shuju-float-right-10 "><span class="shuju-text-12">占比</span>82.1%</em></section></td>
 </tr>
  <tr >
   <td><section><span class="shuju-float-left-10 ">本科生</span><br><i class="shuju-text-green ">33791人</i><em class="shuju-float-right-10 "><span class="shuju-text-12">占比</span>82.1%</em></section></td>
 </tr>
 
 </table></article>
  <article class="shuju-information-25">
  <table class="shuju-spacing-td shuju-spacing-td1" width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr ng-repeat='xls in tea.zglb'>
   <td><section><span class="shuju-float-left-10">{{xls.NAME_}}</span><i class="shuju-text-green">{{xls.COUNT_}}人</i><em class="shuju-float-right-10"><span class="shuju-text-12">占比</span>{{xls.bl}}</em></section></td>
 </tr>
  <tr >
   <td><section><span class="shuju-float-left-10 ">本科生</span><i class="shuju-text-green ">33791人</i><em class="shuju-float-right-10 "><span class="shuju-text-12">占比</span>82.1%</em></section></td>
 </tr>
  <tr >
   <td><section><span class="shuju-float-left-10 ">本科生</span><i class="shuju-text-green ">33791人</i><em class="shuju-float-right-10 "><span class="shuju-text-12">占比</span>82.1%</em></section></td>
 </tr>
  <tr >
   <td><section><span class="shuju-float-left-10 ">本科生</span><i class="shuju-text-green ">33791人</i><em class="shuju-float-right-10 "><span class="shuju-text-12">占比</span>82.1%</em></section></td>
 </tr>
 </table></article>

 </div>
</div> 
<!--统计图-->
 <div class="shuju-content-list shuju-content-information">
  <div id="shuju-box">
    <div class="shuju-fenbutu-box clearfix">
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>性别分布</h4>
      	<div stu-chart config = "{type:'sex',data:[tea.nanrs,tea.nvrs]}" class="shuju-border-dance" style="height:323px;"></div>
      </div>
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>学生平均年龄分布</h4>
        <div stu-chart config = "areaChart" class="shuju-border-dance"style="height:323px;"></div>
      </div>
    </div>
     <div class="shuju-fenbutu-box clearfix">
       <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>民族分布形式</h4>
        <div class="shuju-border-dance" ><h5>汉族{{mzcount.hz}}人，占比  {{((mzcount.hz/tea.rs)*100)|number:1}}%；少数民族{{mzcount.ssmz}}人，占比 {{((mzcount.ssmz/tea.rs)*100)|number:1}}%；未维护 {{mzcount.wwh}}人，占比{{((mzcount.wwh/tea.rs)*100)|number:1}}%。</h5><div stu-chart config = "column1Chart" style="height:256px;"></div></div>
      </div>
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>政治面貌</h4>
         <div class="shuju-border-dance" ><h5>中国共产党党员{{zzmm.ty}}人，占比  {{((zzmm.ty/tea.rs)*100)|number:1}}%；其他类别{{zzmm.bsty}}人，占比 {{((zzmm.bsty/tea.rs)*100)|number:1}}%；未维护 {{zzmm.wwh}}人，占比{{((zzmm.wwh/tea.rs)*100)|number:1}}%。</h5><div stu-chart config = "columnfChart" style="height:256px;"></div></div>
      </div>
    </div>
    <div class="shuju-fenbutu-box clearfix">
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>生源地分布</h4>
      	<div stu-chart config = "mapChart" class="shuju-border-dance" style="height:323px;"></div>
      </div>
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>学历组成</h4>
       
         <div stu-chart config = "pieChart" class="shuju-border-dance"style="height:323px;"></div>
      </div>
    </div>
    <div class="shuju-fenbutu-box clearfix">
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>职称级别统计</h4>
        <div stu-chart config = "vm.items[0].option" class="shuju-border-dance" style="height:250px;"></div>
      	<!-- <div stu-chart config = "mapChart" class="shuju-border-dance" style="height:323px;"></div> -->
      </div>
      <div class="shuju-left-img">
        <h4 class="shuju-tongji-title"><i class="shuju-tongji-icon"></i>职称统计</h4>
        <div stu-chart config = "vm.items[1].option" class="shuju-border-dance" style="height:250px;"></div>
      </div>
    </div>
  </div>
</div> 
<jsp:include page="/WEB-INF/jsp/common/footer.jsp"></jsp:include>
</body>
</html>
