<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String root = request.getContextPath(); %>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="">
<!-- Bootstrap core CSS -->
<link rel="stylesheet" type="text/css" href="../../css/rxs-style.css">
<jsp:include page="../../../static/base.jsp"></jsp:include>
<base href="<%=root%>/social/lost/entire/index.jsp"/>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<title>失而复得</title>
</head>
<body class="rxs-bg" ng-controller="controller">
<div class="rxs-bg-ff6 rxs-pad-top-lr">
  <div class="media">
    <div class="media-left media-middle"><a href="../../liao/index.jsp"><img src="../../images/fw-icon.png" width="28"></a></div>
    <div class="media-body media-middle ">
      <a href="../../lost/mine/index.jsp" style="text-decoration: none;"><h4 class="rxs-cor-fff">我的发文</h4></a>
    </div>
    <div class="media-right media-middle"><a href="../../lost/mine/index.jsp" class="rxs-rgt-arw"></a></div>
  </div>
</div>
<div class="rxs-bg-fff rxs-ft-15">
  <div class="row rxs-list-link text-center">
    <div class="col-xs-4"><a href="" id="all" class="rxs-fw-all rxs-ll-active" ng-click="changeTypeCode()" ng-class="{active : vm.typeCode == null}"> 全部发文</a></div>
    <div class="col-xs-4"><a href="" id="sw" class="rxs-sw" ng-click="changeTypeCode(0)" ng-class="{active : vm.typeCode == 0}">失物招领</a></div>
    <div class="col-xs-4"><a href="" id="xw" class="rxs-xw" ng-click="changeTypeCode(1)" ng-class="{active : vm.typeCode == 1}">寻物启事</a></div>
  </div>
</div>
<div class="rxs-tools">
	<div class="input-group">
		<input type="text" class="form-control" placeholder="请输入查询关键字" ng-model="vm.keyword">
   	<span class="input-group-btn">
    	<button class="btn btn-default" type="button" ng-click="initQuery()">
    		<i class="fa fa-search" style="color: #888;"></i>
    	</button>
   	</span>
   </div>
</div>
<div class="rxs-comn-list">
  <ul class="rxs-talk rxs-mar-tb-10">
    <li class=" rxs-mar-tb-10" ng-repeat="it in vm.list">
      <div class="media">
        <div class="media-left media-middle"><img class="img-circle" width="60" src="../../images/user-img.png"></div>
        <div class="media-body media-middle  rxs-re-lab">
          <h4 class="rxs-ft-16">{{it.name_}}</h4>
          <p class="rxs-cor-9b">{{it.creat_time}}</p>
        </div>
      </div>
      <!--标注标签1-->
      <div class="rxs-lb-j"><img src="../../images/jian-icon.png" width="40" ng-show="it.lostfound_type_code == 1"></div>
      <!--标注标签1-->
      <!--标注标签2-->
      <div class="rxs-lb-j"><img src="../../images/shi-icon.png" width="40" ng-show="it.lostfound_type_code == 0"></div>
      <!--标注标签2-->
      <!--标注标签3-->
      <div class="rxs-lb-end"><img src="../../images/ending.png" width="80" ng-show="it.is_solve==1 "></div>
      <!--标注标签3-->
      <div class="rxs-fbq rxs-ft-15">{{it.message}}</div>
      <div class="text-center">
	    <img ng-src="{{it.image_url}}" alt="" width="80%" />
	  </div>
      <div class="rxs-mar-tb-10 rxs-cor-9b"> 电话：<span class="rxs-cor-ff9">{{it.tel_}}</span> </div>
    </li>
  </ul>
</div>
</body>
</html>