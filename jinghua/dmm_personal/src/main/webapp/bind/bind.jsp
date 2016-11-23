<%@ page language="java" isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String root = request.getContextPath();
	String openid =(String)request.getAttribute("openid");
	//if(openid == null) response.sendRedirect(root+ "/index.jsp");
%>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/bind/bind.jsp"/>
<jsp:include page="../static/base.jsp"></jsp:include>
<title>微信绑定中心</title>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body ng-controller="controller" style="background-color: #F1F1F1;">
<div class="container">
	<div class="row">
		<div class="center-block col-md-12">
			<h3 class="text-center">个人信息绑定</h3>
			<img src="<%=root%>/teacher/images/bind.png"  class="img-thumbnail img-responsive"  style="max-height: 300px;margin: 0 auto;display: block;">
			<br />
			<form name="bindform" class="form-horizontal">
			  <div class="form-group">
			    <div class="col-md-12">
			    	<input type="hidden" name="openid" ng-model="user.openid" ng-init="user.openid='<%=openid%>'" >
			        <input type="text" class="form-control" name="username" placeholder="用户名" ng-model="user.username" required>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-md-12">
			      <input type="password" class="form-control" name="password" ng-model="user.password" placeholder="登录密码" required>
			    </div>
			  </div>
			  <div ng-show="(!bindform.username.$valid && !bindform.username.$pristine) || (!bindform.password.$valid && !bindform.password.$pristine)" class="form-group">
			    <div class="col-md-12">
			     	<div class="bg-danger text-danger small" style="padding:10px 15px;">
						<div ng-show="!bindform.username.$valid && !bindform.username.$pristine" >* 用户名不能为空</div>
						<div ng-show="!bindform.password.$valid && !bindform.password.$pristine">* 密码不能为空</div> 
					</div>
			    </div>
			  </div>
			  <div class="form-group" ng-show="binding_goingon">
			    <div class="col-md-12">
			      <div class="bg-warning text-warning" style="padding:8px 15px;">
					<span>
						<i class="fa fa-spinner fa-spin"></i>
					</span> 绑定中...
				  </div>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-md-12">
			  		<input type="submit" ng-hide="binding_goingon || binding_success" class="btn btn-success btn-block" ng-disabled="!bindform.$valid" ng-click="submit(user)" value="绑定" >
			    </div>
			  </div>
			</form>
		</div>
	</div>
</div>
</body>
</html>