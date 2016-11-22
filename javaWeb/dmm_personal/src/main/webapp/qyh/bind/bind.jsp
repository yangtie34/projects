<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String root = request.getContextPath();
	String openid ;
	openid = request.getParameter("openid");
	if(openid == null){
		openid = (String)request.getAttribute("openid");
	}
%>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/qyh/bind/bind.jsp"/>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>掌上山政用户信息</title>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body ng-controller="controller" class="center-block col-md-6" style="float: none;padding:0px;">
	<p ><h3 class="text-center">掌上山政个人信息绑定</h3></p>
	<div>
		<form name="bindform" class="form-horizontal">
	    	<input type="hidden" name="openid" ng-model="user.openid" ng-init="user.openid='<%=openid%>'" >
	    	<br>
		    <input type="text" class="form-control" name="username" placeholder="职工号或学号" ng-model="user.username" required>
	    	<br>
		    <input type="text" class="form-control" name="sfzh" ng-model="user.sfzh" placeholder="身份证号" ng-pattern="/^\d{18}$/" required>
	    	<br>
		    <input type="手机号" class="form-control" name="mobile" ng-model="user.mobile" placeholder="手机号" ng-pattern="/^1\d{10}$/" required>
	    	<br>
			<div ng-show="(!bindform.username.$valid && !bindform.username.$pristine) 
		  			 || (!bindform.sfzh.$valid && !bindform.sfzh.$pristine)
		  			 || (!bindform.mobile.$valid && !bindform.mobile.$pristine)"
		  			  class="form-group alert alert-danger">
		     	<ul class=" small">
					<li ng-show="bindform.username.$dirty && bindform.username.$error.required"   >职工号不能为空</li>
					<li ng-show="bindform.sfzh.$dirty && bindform.sfzh.$error.required">身份证号不能为空</li> 
					<li ng-show="bindform.sfzh.$dirty && bindform.sfzh.$error.pattern">身份证号格式不正确</li>
					<li ng-show="bindform.mobile.$dirty && bindform.mobile.$error.required">手机号不能为空</li> 
					<li ng-show="bindform.mobile.$dirty && bindform.mobile.$error.pattern">手机号格式不正确</li>
				</ul>
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
		  		<input type="button" ng-hide="binding_goingon || binding_success"
		  		 class="btn btn-success btn-block" ng-disabled="!bindform.$valid"
		  		 ng-click="submit(user)" value="绑定" >
		    </div>
		  </div>
		</form>
	</div>
</body>
</html>