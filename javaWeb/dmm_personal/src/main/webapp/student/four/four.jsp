<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN"  ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>欢迎来到郑州轻工业学院</title>
<base href="<%=root%>/student/four/four.jsp"/>
<script src="<%=root%>/static/swiper/js/swiper.jquery.umd.min.js"></script>
<script src="<%=root%>/static/swiper/js/swiper.animate1.0.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/swiper/css/animate.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/swiper/css/swiper-3.3.1.min.css" />

<link rel="stylesheet" type="text/css" href="../css/usty-style.css">
<link rel="stylesheet" type="text/css" href="../css/auto.css">
<link rel="stylesheet" type="text/css" href="../css/rxs-style.css">

<script src="<%=root%>/static/echarts/echarts.min.js"></script> 
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body  ng-controller="controller" >
<div class="swiper-container swiper-container-vertical">
  <div class="swiper-wrapper">
  <div class="swiper-slide usty-first-bottom section1" >
    <!--第一屏的第一屏-->
    <div class="usty-menu-height"></div>
    <div class=" usty-common-auto usty-first">
      <div class="usty-first-imgbg">
        <div class="usty-first-p">
          <div class="usty-first-f14">{{school.enroll_date}}进入</div>
          <div class="usty-first-f30">{{school.school_name}}</div>
          <div class="usty-first-f14">开启了<span class="usty-first-f30">4年</span>的</div>
          <div class="usty-first-f24">大学生活</div>
        </div>
        <div class="usty-first-left01">
          <article class="usty-first-leftimg01"><a href=""> </a> </article>
        </div>
        <div class="usty-first-left02">
          <article class="usty-first-leftimg02"> <a href=""> </a></article>
        </div>
        <div class="usty-first-left03">
          <article class="usty-first-leftimg03"> <a href=""> </a></article>
        </div>
        <div class="usty-first-left04">
          <article class="usty-first-leftimg04"><a href=""> </a> </article>
        </div>
        <div class="usty-first-right01">
          <article class="usty-first-rightimg01"><a href=""> </a> </article>
        </div>
        <div class="usty-first-right02">
          <article class="usty-first-rightimg02"><a href=""> </a> </article>
        </div>
        <div class="usty-first-right03">
          <article class="usty-first-rightimg03"><a href=""> </a> </article>
        </div>
        <div class="usty-first-right04">
          <article class="usty-first-rightimg04"><a href=""> </a> </article>
        </div>
      </div>
      <div class="usty-first-text">
        <ul class="usty-first-ul">
          <li><a href="" id="dyc">第一次</a></li>
          <li class="padd-left90"><a href="" id="xf">消费</a></li>
          <li class="padd-left130"><a href="" id="tsg">图书馆</a></li>
          <li class="padd-left110"><a href="" id="cj">成绩</a></li>
          <li class="padd-left170"><a href="" id="rm">人脉</a></li>
          <li class="padd-left100"><a href="" id="jc">奖惩</a></li>
          <li><a href="" id="bqq">标签墙</a></li>
        </ul>
      </div>
    </div>
    </div>
  <!--第一次-->
  <jsp:include page="/student/four/tpl/first.jsp"></jsp:include>
  
   <!--消费-->
  <jsp:include page="/student/four/tpl/card.jsp"></jsp:include>
  
   <!-- 借书 -->
  <jsp:include page="/student/four/tpl/book.jsp"></jsp:include>
  
   <!-- 成绩 -->
  <jsp:include page="/student/four/tpl/score.jsp"></jsp:include>
  
  <!-- 辅导员、授课老师 -->
  <jsp:include page="/student/four/tpl/teacher.jsp"></jsp:include>
  
  <!-- 奖惩 -->
  <jsp:include page="/student/four/tpl/award.jsp"></jsp:include>
  
  <div class="swiper-slide  usty-first-bottom section5">
    <div class="usty-menu-height"></div>
    <div class="usty-common-auto usty-biaoqian">
      <p class="usty-biaoqian-textp">我的标签墙</p>
      <div class="usty-biaoqian-imgbg">
        <div class="usty-biaoqian-left">
          <article class="usty-biaoqian-name-left usty-biaoqian-name-right">{{wall.score}}</article>
          <article class="usty-biaoqian-name-left usty-biaoqian-name-right usty-biaoqian-name-shadow"> </article>
        </div>
        <div class="usty-biaoqian-left">
          <article class="usty-biaoqian-name-left usty-biaoqian-name-bgcolor02">{{wall.card}}</article>
          <article class="usty-biaoqian-name-left usty-biaoqian-name-shadow"> </article>
        </div>
        <div class="usty-biaoqian-left">
          <article class="usty-biaoqian-name-left usty-biaoqian-name-right usty-biaoqian-name-bgcolor01">{{wall.book}}</article>
          <article class="usty-biaoqian-name-left usty-biaoqian-name-right usty-biaoqian-name-shadow"> </article>
        </div>
      </div>
    </div>
  </div>
  
  
  </div>
  <!-- 如果需要分页器 -->
  <div class="swiper-pagination"></div>
  </div>
</body>
</html>
