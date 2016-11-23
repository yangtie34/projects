<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<title>微信消息管理</title>
<jsp:include page="../../static/base.jsp"></jsp:include>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<base href="<%=root%>/wechat/msg/msg.jsp"/>
</head>
<body ng-controller="controller">
	<textarea ng-model="massText"></textarea>
	<button class="btn btn-primary" ng-click="sendMassText()">发送</button>
	{{userSelect}}
	<div>
		<div class="col-md-1 col-sm-1 col-xs-4" ng-repeat="user in userList" style="word-break: break-all; height:160px;max-width: 160px;">
			<img ng-src="{{user.wechatHeadImg}}" alt="{{user.name}}"  class="img-responsive img-rounded"/>
			 <input type="radio" ng-model="userSelect.openid" name="userSelect" value="{{user.openid}}" /> {{user.wechatName}} <br />  {{user.wechatSex==1 ? '男':'女'}} {{user.city}} 
		</div>
		<div class=clearfix></div>
	</div>
	
</body>
</html>