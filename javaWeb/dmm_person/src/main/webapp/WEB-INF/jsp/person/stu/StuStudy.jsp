<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>我的学业</title>
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/student/StuStudyController.js"></script>
</head>
<jsp:include page="../top.jsp"></jsp:include>
<body ng-controller="StuStudyController">
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">借阅信息</h3>
 		<div class="input-group input-group-sm" date-picker result="date1" dateFmt="yyyy-MM-dd" double="true" ></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
    --> </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">推荐图书</a> &gt;&gt;</h4>
        <div class=" ">
          <ol class="jiaozhi-ol-list"  ng-repeat="item in vm.items[0]">
            <li >
            <span class="label label-danger">{{$index+1}}</span>
            &nbsp;&nbsp;
            <a class="jiaozhi-alink-under" href="#">{{item.NAME_}}&nbsp;&nbsp;&nbsp;&nbsp;作者：{{item.WRITE_}}&nbsp;&nbsp;&nbsp;&nbsp;出版社：{{item.PRESS}}</a> 
            </li>
          </ol>
        </div>
      </section>
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">借阅分类</a> &gt;&gt;</h4>
        <div cs-chart config = "vm.items[1]" class="col-xs-12" style="height:350px;"></div>
      </section>
    </div>
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4><a class="jiaozhi-alink-under" href="#">借阅数量</a> &gt;&gt;</h4>
        <div class="row ">
          <div class="col-xs-12 ">
            <p>共借阅图书 <span class="lead"> {{vm.items[2].sum}}</span> 本  ,阅读综合排名已排列在矿大 <span class="lead">73% </span> 人之前。</p>
             <div cs-chart config = "vm.items[2].option" class="col-xs-12" style="height:350px;"></div>
        </div>
      </section>
      <section class="col-xs-12 col-md-6">
        <h4><a class="jiaozhi-alink-under" href="#">借阅明细</a> &gt;&gt;</h4>
        <div class="row">
          <div class="col-xs-12">
            <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <th scope="col" ng-repeat="item in jymxTitles">{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in vm.items[3]" init="i=0;">
                <td><span class="label label-info">{{$index+1}}</span></td>
                <td ng-repeat="(key,value) in item" ng-show="key!='NUM'"title="{{value}}">{{value| limitTo : 15 }}</td>
              </tr>
            </tbody>
            </table>
     		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="pageXf_numPerPage">
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
      </section>
    </div>
  </div>
</article>
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">个人课程</h3>
<!-- <div class="input-group input-group-sm" date-picker result="date2" dateFmt="yyyy-MM-dd" double="true" ></div>
    -->   <!-- <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
    --> </form>
  </div>
</section>
<div class="container">
    
    <div class="row">
      <section  class="col-xs-12">
        <div cg-kc-table source="vm.items[4]" class="jiaozhi-kebiao-table"></div>
      </section>
    </div>
    
    <div class="row">
      <section class="col-xs-12">
        <h4><a class="jiaozhi-alink-under" href="#">成绩查询</a> &gt;&gt;</h4>
        <div class="row">
          <div class="col-xs-12">
            <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
              <thead>
                <tr>
                  <th scope="col" ng-repeat="value in getcjcxTitles">{{value}}</th>
                </tr>
              </thead>
              <tbody>
                <tr ng-repeat="item in vm.items[5]" init="i=0">
                  <td ng-repeat="(key,value) in item">{{value}}</td>
<!--              <td >{{item.c2}}</td>
                  <td >{{item.c3}}</td>
                  <td >{{item.c4}}</td>
                  <td >{{item.c5}}</td>
                  <td >{{item.c6}}</td> -->
                </tr>
            </table>
     		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{pagecj.totalPages}} 页，数据 {{pagecj.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="pagecj.totalRows" ng-model="pagecj.currentPage" max-size="pagecj.numPerPage" items-per-page="pagecj.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="pageXf_numPerPage">
				<select ng-model="pagecj.numPerPage" style="border: 1px solid #DDD;"><option
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
      </section>
    </div>
  </div>
</body>
</html>