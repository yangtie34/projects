<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>个人档案</title>
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/student/StuInfoController.js"></script>
</head>
<jsp:include page="../top.jsp"></jsp:include>
<body ng-controller="StuInfoController">

<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">个人档案</h3>
    </form>
  </div>
</section>
</article>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
          <tr ng-repeat="(key, value) in vm.items[0]" ng-show="$index%2==0">
            <td>{{stuInfoTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
         <tr ng-repeat="(key, value) in vm.items[0]" ng-show="$index%2!=0">
            <td>{{stuInfoTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
    </div>
  </div>
</article>
<section class="jiaozhi-title" ng-show="false">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">学期注册</h3>
    </form>
  </div>
</section>
<article class="jiaozhi-content" ng-show="false">
  <div class="container">
    <section class=" ">
      <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
        <thead>
          <tr>
            <th scope="col">注册学年</th>
            <th scope="col">注册学期</th>
            <th scope="col">开放时间</th>
            <th scope="col">注册日期</th>
            <th scope="col">注册状态</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>2015学年</td>
            <td>第二学期</td>
            <td>2015-02-01至2016-02-1</td>
            <td>2015-02-01</td>
            <td>已注册</td>
          </tr>
          <tr>
            <td>2015学年</td>
            <td>第二学期</td>
            <td>2015-02-01至2016-02-1</td>
            <td>2015-02-01</td>
            <td>已注册 <span class="text-danger">延期</span></td>
          </tr>
        </tbody>
      </table>
    </section>
  </div>
</article>
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">荣誉及奖励</h3>
    </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <section class=" ">
      <p>自入学以来， {{vm.items[0].CL01}}所获得的荣誉及奖励信息 ： 荣誉奖励<span class="text-danger lead"> {{vm.items[1].length}} </span>次</p>
      <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
        <thead>
              <tr>
              <th></th>
                <th scope="col" ng-repeat="item in stugetGloryTiltles">{{item}}</th>
              </tr>
        </thead>
        <tbody>
              <tr ng-repeat="item in vm.items[1]" init="i=0;">
                <td><span class="label label-info">{{$index+1}}</span></td>
                <td ng-repeat="(key,value) in item" ng-show="key!='NUM'">{{value}}</td>
              </tr>
        </tbody>
      </table>
    </section>
  </div>
</article>
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">学籍异动</h3>
    </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row">
      <section class="col-xs-5">
        <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
          <thead>
            <tr>
				<th scope="col" ng-repeat="item in getXjydTiles" ng-show="$index<2">{{item}}</th>
            </tr>
          </thead>
          <tbody>
            <tr ng-class="$index==index_?'jiaozhi-tr-arrow':''" ng-repeat="item in vm.items[2]" init="i=0;" ng-click="xjyd($index)">
            <td ng-repeat="(key,value) in item" ng-show="key=='C1'" >{{value}}</td>
            <td ng-repeat="(key,value) in item" ng-show="key=='C2'">{{value}}</td>
            </tr>
          </tbody>
        </table>
      </section>
      <section class="col-xs-7" >
        <ul  class="jiaozhi-tr-block" ng-repeat="item in vm.items[2]" ng-show="$index==index_">
          <li>
            <p>原院系：{{item.C5}} </p>
            <p>原专业： {{item.C4}}</p>
            <p>原班级： {{item.C3}} </p>
          </li>
          <li>
            <p>现院系： {{item.C8}} </p>
            <p>现专业： {{item.C7}}</p>
            <p>现班级： {{item.C6}} </p>
          </li>
        </ul>
      </section>
    </div>
  </div>
</article>
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">宿舍信息</h3>
    </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="jiaozhi-sushe-xinxi" >
      <p ng-repeat="item in vm.items[3]" ng-show="$index==0">现在宿舍：{{item.C8}} {{item.C9}} {{item.C10}}&nbsp;&nbsp;&nbsp;&nbsp;已住 {{vm.items[3].length}}人。
      <!--  <a class="text-danger text-mar-lr" href="">以往宿舍>></a> -->
       </p>
      <div class="row">
        <section class="col-xs-4"  ng-repeat="item in vm.items[3]">
          <table class="table table-hover  jiaozhi-common-table " width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <th scope="col">{{item.C1}}号床</th>
              </tr>
            </thead>
            <tbody>
              <tr >
                <td>姓名：{{item.C2}} <span class="text-mar-lr">年龄：{{item.C3}}</span> <span class="pull-right text-danger">TEL：{{item.C4}}</span></td>
              </tr>
              <tr >
                <td>班级：{{item.C5}}<span class="pull-right">班主任：{{item.C6}}</span></td>
              </tr>
              <tr >
                <td>生源地：{{item.C7}}</td>
              </tr>
            </tbody>
          </table>
        </section>
      </div>
      <section class=" ">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" >宿舍调整</a> >></h4>
        <ul class="jiaozhi-list-border" ng-repeat="item in vm.items[4]">
          <li> {{item.C1}} 由  {{item.C2}}{{item.C3}}号宿舍楼{{item.C4}}   调至{{item.C5}}{{item.C6}}  {{item.C7}}房间</li>
        </ul>
      </section>
    </div>
  </div>
</article>

</body>
</html>