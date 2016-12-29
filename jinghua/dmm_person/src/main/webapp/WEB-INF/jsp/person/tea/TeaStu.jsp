<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>学生管理</title>
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/teacher/TeaStuController.js"></script>
	 <script type="text/javascript"
	src="${ctxStatic}/person/manage/common/common.js"></script>
</head>
<jsp:include page="../top.jsp"></jsp:include>
<body ng-controller="TeaStuController">
<!-- <section class="jiaozhi-title"  ng-show="stujxDiv1"> -->
<section class="jiaozhi-title" >
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">班级管理</h3>
    <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：学工管理系统</small>
    </form>
  </div>
</section>
<!--<article class="jiaozhi-content" ng-show="stujxDiv2">-->
<article class="jiaozhi-content" >
  <div class="container">
    <section>
      <div class="media">
        <div class="media-left media-middle">
          <div class="jiaozhi-block-circle-border">
          <div class="jiaozhi-block-circle" ng-show="xzgradeId==''">全部</div>
            <a><div class="jiaozhi-blockcircle" ng-click="xzgradeClick('')"ng-show="xzgradeId!=''">全部</div></a>
          </div>
        </div>
        <div class="media-body  media-middle">
          <ul class="jiaozhi-block-list">
            <li ng-repeat="item in vm.items[0]" ng-class="xzgradeId!=item.ID?'jiaozhi-block-list-li':'jiaozhi-block-listli'" ng-click="xzgradeId!=item.ID?xzgradeClick(item.ID):null;">
            <span>{{item.NAME_}}</span>
            </li>
          </ul>
        </div>
      </div>
      <article class="jiaozhi-content">
  <div class="container">
    <div class="">
      <section class="col-xs-12 col-md-6">
       <div stu-chart config = "vm.items[2]" class="jiaozhi-tjt-center" style="height:223px;"></div> 
      </section>
      <section class="col-xs-12 col-md-6">
        <div stu-chart config = "vm.items[10]" class="jiaozhi-tjt-center" style="height:223px;"></div>
      </section>
    </div>
  </div>
</article>
    </section>
    <div>
<table  class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
	            <tr border="0">
	                <th>学号</th>
	                <th>姓名</th>
	                <th>性别</th>
	                <th>院系</th>
	                 <th>班级</th>
	                  <th>班长电话</th>
	                  <th>其他</th>
	            </tr>
	         </thead>
	        <tbody>
	        <tr ng-repeat="item in vm.items[1]">
						<td>{{item.NO_}}</td>
						<td>{{item.NAME_}}</td>
						<td><span ng-class="item.SEX=='女'?'jiaozhi-girl':'jiaozhi-boy'"></span></td>
						<td>{{item.DEPT}}</td>
						<td>{{item.GRADE}}</td>
						<td>{{'未维护'}}</td>
						<td><a href="" ng-click="stuClick(item.NO_)">查看 >> </a></td>
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
</article>
<!-- 行政班管理 -->
<!-- <section class="jiaozhi-title" ng-show="stuxzDiv1"> -->
<section class="jiaozhi-title"  ng-show="vm.items[4].length>0">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">行政班管理</h3>
      <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：学工管理系统</small>
    </form>
  </div>
</section>
<!-- <article class="jiaozhi-content" ng-show="stuxzDiv2"> -->
<article class="jiaozhi-content" ng-show="vm.items[4].length>0" >
  <div class="container">
    <section>
      <div class="media">
        <div class="media-left media-middle">
          <div class="jiaozhi-block-circle-border">
          <div class="jiaozhi-block-circle" ng-show="jxgradeId==''">全部</div>
            <a><div class="jiaozhi-blockcircle" ng-click="jxgradeClick('')"ng-show="jxgradeId!=''">全部</div></a>
          </div>
        </div>
        <div class="media-body  media-middle">
          <ul class="jiaozhi-block-list">
            <li ng-repeat="item in vm.items[4]" ng-class="jxgradeId!=item.ID?'jiaozhi-block-list-li':'jiaozhi-block-listli'" ng-click="jxgradeId!=item.ID?jxgradeClick(item.ID):null;">
            <span>{{item.NAME_}}</span>
            </li>
          </ul>
        </div>
      </div>
      <div class="container">
   <!--    <h4 class="" style="
    float: left;
    position: relative;
    top: 10;
    margin: 50 0 0 100;
">课程通过率</h4> -->
<br>
       <div stu-chart config = "vm.items[5]" class="col-xs-12" style="height:350px;"></div>
    <div class="">
      <section class="col-xs-12 col-md-6">
       <div stu-chart config = "vm.items[6]" class="jiaozhi-tjt-center" style="height:223px;"></div> 
      </section>
      <section class="col-xs-12 col-md-6">
        <div stu-chart config = "vm.items[7]" class="jiaozhi-tjt-center" style="height:223px;"></div>
      </section>
    </div>
  </div>
    </section>
    <div>
<table  class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
	            <tr border="0">
	                <th>学号</th>
	                <th>姓名</th>
	                <th>性别</th>
	                <th>班级</th>
	                 <th>联系电话</th>
	                  <th>通过</th>
	                  <th>挂科</th>
	                  <th>请假次数</th>
	            </tr>
	         </thead>
	        <tbody>
	        <tr ng-repeat="item in vm.items[8]">
						<td>{{item.NO_}}</td>
						<td>{{item.NAME_}}</td>
						<td><span ng-class="item.SEX_=='女'?'jiaozhi-girl':'jiaozhi-boy'"></span></td>
						<td>{{item.CLASS_}}</td>
						<td>{{item.TEL_}}</td>
						<td>{{item.TG}}</td>
						<td>{{item.GK}}</td>
						<td>{{item.QJ}}</td>
						<td><a href="" ng-click="jxstuClick(item.NO_)">详情>> </a></td>
					</tr>
	        </tbody>
	</table>
	<span class="loading"ng-show="vm.items[8]==null">正在加载。。。<br/></span>
		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{page1.totalPages}} 页，数据 {{page1.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page1.totalRows" ng-model="page1.currentPage" max-size="page1.numPerPage" items-per-page="page1.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
				<select ng-model="page1.numPerPage" style="border: 1px solid #DDD;"><option
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
</article>





<div cs-window show="stuxxDiv" autoCenter="true" offset="offset" title="'学生详情'">
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
          <tr ng-repeat="(key, value) in vm.items[3]" ng-show="$index%2==0">
            <td>{{stuTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
         <tr ng-repeat="(key, value) in vm.items[3]" ng-show="$index%2!=0">
            <td>{{stuTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
    </div>
  </div>
</article>
</div>
<!-- 行政班级信息明细窗口 -->
<div cs-window show="jxstuxxDiv" autoCenter="true" offset="offset" title="'学生详情'" class="jiaozhi-windowss">
<div class="jiaozhi-windows">
  <div class="media">
    <div class="media-left media-middle"><img src="${ctxImg}/user-{{vm.items[9].xx[0].CL02=='男'?'boy':'girl' }}.jpg"" class="img-circle" width="70" height="70">
      <div class="jiaozhi-border-tips"><span class="jiaozhi-bg-tips">{{vm.items[9].xx[0].CL03}}</span></div>
    </div>
    <div class="media-body media-middle"><p class="jiaozhi-text-20">{{vm.items[9].xx[0].CL01}}&nbsp;&nbsp;•&nbsp;&nbsp;
    <i style="margin-bottom:-5px;" class="jiaozhi-{{vm.items[9].xx[0].CL02=='男'?'boy':'girl' }}"></i></p>
    <p>{{vm.items[9].xx[0].CL04}}&nbsp;&nbsp;•&nbsp;&nbsp;{{vm.items[9].xx[0].CL05}}</p>
    <p style="margin-bottom:0"> <i class="jiaozhi-text-org">{{vm.items[9].xx[0].CL06}}</i> </p></div>
  </div>
  <h3 class="jiaozhi-table-title">成绩明细</h3>
  <table class="table table-hover  jiaozhi-common-table jiaozhi-windows-table" width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tbody>
        <tr>
        <td>学期</td><td>课程</td><td>综合成绩</td><td>学分</td>
        </tr>
          <tr ng-repeat="item in vm.items[9].cj">
            <td >{{item.CL01}}{{item.CL02}}</td>
            <td >{{item.CL03}}</td>
            <td >{{item.CL04}}</td>
            <td >{{item.CL05}}</td> 
          </tr>  
         
        </tbody>
      </table>
      <div ng-show="vm.items[9].qj.length>0">
      <h3 class="jiaozhi-table-title">请假明细</h3>
  <table  class="table table-hover  jiaozhi-common-table jiaozhi-windows-table" width="100%" border="0" cellspacing="0" cellpadding="0"> 
        <tbody>
        <tr>
        <td>开始时间</td><td>结束时间</td><td>请假天数</td><td>请假原因</td>
        </tr>
          <tr ng-repeat="item in vm.items[9].qj">
            <td>{{item.CL01}}~{{item.CL02}}</td>
            <td>{{item.CL03}}天</td>
            <td>{{item.CL04}}</td> 
          </tr> 
        </tbody>
      </table></div>
      </div>
</div>
</body>
</html>