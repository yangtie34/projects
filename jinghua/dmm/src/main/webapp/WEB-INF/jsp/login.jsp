<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	<meta http-equiv="Content-Type" content="text/html" charset="utf-8"/>
    <title>数据挖掘分析系统</title>
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="${ctxStatic}/css/login/login-style.css">
    <style>.error{color:red;}</style>
</head>
<body>

    <header class="login-header">
	  <img style="vertical-align:top;" src="${ctxStatic}/images/login/logo.png"  />
	  <a class="login-header-infor" href="#"></a>
	  <nav><a href="#">帮助</a></nav>
	</header>
	<section class="login-bgcolor">
	<form action="${ctx}/user/login" method="post">
	  <div class="login-bg">
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
		  <ol class="carousel-indicators">
		    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
		    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
		    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
		  </ol>
		
		  <div class="carousel-inner" role="listbox">
		    <div class="item active" id="loginPage">
		      <img src="${ctxStatic}/images/login/login.jpg" alt="...">
		    </div>
		    <div class="item"> 
		      <img src="${ctxStatic}/images/login/login2.png" alt="...">
		    </div>
		    <div class="item" >
		      <img src="${ctxStatic}/images/login/login1.png" alt="...">
		    </div>
		  </div>
		</div>
	    <div class="login-windows">
	      <input name="username" class="login-zhanghao" type="text" placeholder="账号" />
	      <input name="password" class="login-mima" type="password" placeholder="密码" />
          <div class="error" style="text-align:center;">${error}</div>
	      <button class="login-button" type="submit">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button></div>
	  </div>
    </form>
	</section>
	<footer class="login-footer">
	<a  href="http://www.***.edu.cn" target="_blank"><img src="${ctxStatic}/images/login/logo-home.png" /></a>
	<nav>Copyright © 2010-2015 **** 学校</nav>
	</footer>
	
	
	
	<script type="text/javascript" src="${ctxStatic }/js/jquery/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxStatic }/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
	var bgPageFlag=true;
	$('.carousel').carousel({
		  interval: 4000,
		  pause:'pause'
	})
	</script>
</body>
</html>