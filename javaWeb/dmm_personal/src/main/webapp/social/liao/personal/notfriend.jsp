<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% String root = request.getContextPath(); %>
<!DOCTYPE html >
<html ng-app="app"> 
<head>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<base href="<%=root%>/social/liao/personal/notfriend.jsp"/>
<link rel="stylesheet" type="text/css" href="../../css/rxs-style.css">
<script type="text/javascript" src="js/notFriendController.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<title>好友申请</title>
</head>
<body ng-controller="controller" class="rxs-bg">
			<input type="text" hidden="hidden" id="um" value="${username}"/>
			<div class="rxs-menu-top rxs-bg-f4 text-center"> <h4>
			<img class="img-circle" width="85" ng-src="{{personalInfo.wechat_head_img}}">
			<i class="fa fa-user"  ng-if="personalInfo.wechat_head_img == '' " style="height: 60px;width: 60px;font-size: 40px;color: #fff;"></i>
			</h4>
			   <h4 class="rxs-ft-15">{{personalInfo.real_name}}不是你的好友</h4>
			   </div>
			<div class="rxs-comn-list rxs-mar-tb">
			   <h3 class="text-center"><button type="button" ng-click="sendRequest();" class="btn btn-warning rxs-btn-org rxs-ft-16">发送好友申请</button></h3>
			</div>
</body>		
</html>