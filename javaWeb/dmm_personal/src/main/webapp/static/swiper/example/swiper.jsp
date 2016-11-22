<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" />
<meta name="apple-mobile-web-app-status-bar-style" content="yes" />
<meta charset="UTF-8">
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<title>swiper使用方法</title>
<!-- angular and jquery -->
<script src="<%=root%>/static/jquery/jquery-1.9.1.min.js"></script>
<script src="<%=root%>/static/angular/angular.min.js"></script>
<script src="<%=root%>/static/angular/angular-route.min.js"></script>
<!-- bootstrap -->
<script src="<%=root%>/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/bootstrap-3.3.6/css/bootstrap.min.css" />
<!-- 自定义的angular模块 -->
<script src="<%=root%>/static/angular_expand/mobile/ng-system.min.js"></script>
<!-- 引用插件 -->
<script src="<%=root%>/static/toastr/toastr.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/toastr/toastr.min.css" />
<script src="<%=root%>/static/material/js/material.min.js"></script>
<script src="<%=root%>/static/material/js/ripples.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/material/css/bootstrap-material-design.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/material/css/ripples.min.css" />

<script src="<%=root%>/static/swiper/js/swiper.jquery.umd.min.js"></script>
<script src="<%=root%>/static/swiper/js/swiper.animate1.0.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/swiper/css/animate.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/swiper/css/swiper-3.3.1.min.css" />

<script type="text/javascript" src="js/controller.js"></script>
<base href="<%=root%>/static/swiper/example/swiper.jsp"/>
</head>
<body ng-controller="controller">
<style>
<!--
	html, body {
        position: relative;
        height: 100%;
    }
    .swiper-container {
        width: 100%;
        height: 100%;
    }
    .swiper-slide {
        text-align: center;
        font-size: 18px;
        background: #fff;
        /* Center slide text vertically */
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: center;
        -ms-flex-pack: center;
        -webkit-justify-content: center;
        justify-content: center;
        -webkit-box-align: center;
        -ms-flex-align: center;
        -webkit-align-items: center;
        align-items: center;
    }
    </style>    
 <!-- Swiper -->
 <div class="swiper-container">
    <div class="swiper-wrapper">
        <div class="swiper-slide">
        	<div class="ani" swiper-animate-effect="rollIn" swiper-animate-duration="0.5s" swiper-animate-delay="0s">
        		<button class="btn btn-raised btn-success">qwq</button>	
       		</div>
        </div>
        <div class="swiper-slide">
        	<div class="ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0">
        		<button class="btn btn-raised btn-success">qwq</button>	
       		</div>
       		<div class="ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0">
        		<button class="btn btn-raised btn-success">qwq</button>	
       		</div>
       		<div class="ani" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="0">
        		<button class="btn btn-raised btn-success">qwq</button>	
       		</div>
        </div>
        <div class="swiper-slide">
        	<div class="ani" swiper-animate-effect="bounceInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.3s">
        		<button class="btn btn-raised btn-success">2</button>	
       		</div>
       		<div class="ani" swiper-animate-effect="bounceInDown" swiper-animate-duration="0.5s" swiper-animate-delay="0s">
        		<button class="btn btn-raised btn-success">3</button>	
       		</div>
       		<div class="ani" swiper-animate-effect="bounceInRight" swiper-animate-duration="0.5s" swiper-animate-delay="0.3s">
        		<button class="btn btn-raised btn-success">4</button>	
       		</div>
        </div>
    </div>
    <!-- Add Pagination -->
    <div class="swiper-pagination"></div>
</div>

</body>
</html>