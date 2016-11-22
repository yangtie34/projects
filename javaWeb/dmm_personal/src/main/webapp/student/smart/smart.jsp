<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<jsp:include page="../../static/base.jsp"></jsp:include>
<base href="<%=root%>/student/smart/smart.jsp"/>
<title>我和学霸</title>
<script src="<%=root%>/static/swiper/js/swiper.jquery.umd.min.js"></script>
<script src="<%=root%>/static/swiper/js/swiper.animate1.0.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/swiper/css/animate.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/swiper/css/swiper-3.3.1.min.css" />

<link rel="stylesheet" type="text/css" href="../css/rxs-style.css">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body ng-controller="controller" >
 <div class="swiper-container  swiper-container-vertical" >
    <div class="swiper-wrapper">
    	<!-- 第一页 -->
        <div class="swiper-slide rxs-slide rxs-abs-green rxs-text-22 rxs-text-fff"  >
	      <div class="rxs-pk-me ani"  swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s"><img src="../images/user-pic.png" class="img-circle" width="60"> 我</div>
	      <div class="rxs-pk-pk ani" swiper-animate-effect="bounceInUp" swiper-animate-duration="0.8s" swiper-animate-delay="0.4s"><img src="../images/pk.png" width="120"></div>
	      <div class="rxs-pk-xb ani" swiper-animate-effect="fadeInRight" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s">学霸<img src="../images/xueba.png" class="img-circle" width="60"></div>
	    </div>
        <!-- 消费信息对比 -->
        <jsp:include page="/student/smart/tpl/consume.jsp"></jsp:include>
        
        <!-- 用餐信息信息对比 -->
        <jsp:include page="/student/smart/tpl/dinner.jsp"></jsp:include>
        
        <!-- 图书借阅对比 -->
        <jsp:include page="/student/smart/tpl/book.jsp"></jsp:include>
        
        <!-- 成绩对比 -->
        <jsp:include page="/student/smart/tpl/score.jsp"></jsp:include>
    </div>
    <!-- Add Pagination -->
     <div class="rxs-btm-arw"> </div>
    <div class="swiper-pagination"></div>
</div>
</body>
</html>