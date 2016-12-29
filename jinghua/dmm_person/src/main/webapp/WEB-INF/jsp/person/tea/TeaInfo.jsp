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
	src="${ctxStatic}/person/teacher/TeaInfoController.js"></script>
</head>

<body ng-controller="TeaInfoController">
<jsp:include page="../top.jsp"></jsp:include>

<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">个人档案</h3>
      <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事系统</small>
    
    </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
          <tr ng-repeat="(key, value) in vm.items[0]" ng-show="$index%2==0">
            <td>{{teaInfoTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
         <tr ng-repeat="(key, value) in vm.items[0]" ng-show="$index%2!=0">
            <td>{{teaInfoTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
    </div>
  </div>
</article>
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">信息变动历史</h3>
      <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事系统</small>
    </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row" ng-repeat="(key, value) in vm.items[1] " ng-init="index1=$index">
      <section class="col-xs-1">
        <div class="jiaozhi-vertical-text "
          ng-class="index1==0?'jiaozhi-vertical-text-org':'jiaozhi-vertical-text-green'"
        >{{key}}</div>
      </section>
      <section class="col-xs-11" >
        <div class="row text-center">
        <div ng-repeat="item in value" ng-init="index2=$index">
          <div class="col-xs-3" >
            <div class="jiaozhi-fang-box" 
            ng-class="index1==0?'jiaozhi-fang-box-org':'jiaozhi-fang-box-green'"
            > <span class="jiaozhi-fang-bg" 
             ng-class="index1==0?'jiaozhi-fang-bg-0{{index2+1}}':'jiaozhi-fang-bg-green-0{{index2+1}}'"
            >{{item.NAME_||'未维护'}}</span> </div>
            <p class="lead-normal">{{item.DATA_}}</p>
          </div>
          <div class="col-xs-1" ng-show="index2<value.length-1">
             <div class="jiaozhi-arrow-icon"
               ng-class="index1==0?'':'jiaozhi-arrow-icon-green'"
             ></div>
          </div>
            </div>
        </div>
      </section>
    </div>
  </div>
</article>


</html>
