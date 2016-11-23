<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
		<meta name="description" content="">
		<meta name="author" content="">
		<title>家长中心-关注</title>
		<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/wechat/login-style.css">
		<script type="text/javascript">
			//定义获取URL参数的方法
			var thisHref=window.location.href;
			function getUrlParam(name) {
				var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
				var r = window.location.search.substr(1).match(reg); //匹配目标参数
				if (r != null){
					return unescape(r[2]);
				}
				return null;
			}
			var code = getUrlParam("code");
			if(code == null || typeof code =='undefined' || code == ''){
				var msg = getUrlParam("msg");
				if(msg != null && typeof msg !='undefined' && msg!=''){
					alert("账号或密码错误");
				}
				window.location.href="https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx14934742aa2b43a4&redirect_uri="+thisHref+
				"&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect"; //返回参数值
			}
		 </script>
	</head>
	<body>
	<div class="login-bg">
	  <div class="login-menu">关注孩子</div>
	  <div class="login-user"></div>
	  <div class="login-form">
		  <form action="${ctx}/wechat/parent/band" method="post">
		    <input type="text" class="login-input" name="stuIdNo" placeholder="请输入身份证号" required="required">
		    <input type="password" class="login-input" name="password" placeholder="请填写登录密码" required="required">
		    <input type="hidden" name="w" id="w">
		    <button type="submit" class="login-btn">关注</button>
		  </form>
	    <p class="login-help"><a href="${ctx }/wechat/parent/lostpwd">忘记密码？</a></p>
	    <p class="login-hr-line"><span class="login-hr-icon"></span></p>
	    <p class="login-tishi">提醒：初始密码为您的孩子的身份证号的12-17位</p>
	  </div>
	</div>
	${msg}
	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
	$(function(){
		$("#w").val(code);
	});
	</script>
	</body>
</html>
