<%@page import="cn.gilight.framework.uitl.SysConfig"%>
<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
	SysConfig sys = SysConfig.instance();
%>
<!DOCTYPE html>
<html lang="zh-CN"  ng-app="app">
<head>
<meta charset="utf-8">
<jsp:include page="../../static/base.jsp"></jsp:include>
<base href="<%=root%>/student/colorPage/colorPage.jsp"/>
<title>欢迎来到<%=sys.getSchoolName() %></title>
<script src="<%=root%>/static/swiper/js/swiper.jquery.umd.min.js"></script>
<script src="<%=root%>/static/swiper/js/swiper.animate1.0.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/swiper/css/animate.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/swiper/css/swiper-3.3.1.min.css" />

<link rel="stylesheet" type="text/css" href="../css/rxs-style.css">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body  ng-controller="controller" >
<div class="swiper-container swiper-container-vertical">
  <div class="swiper-wrapper">
    <div class="swiper-slide rxs-slide rxs-green-bg" >
      <div class="rxs-slide-block" >
        <div class="ani rxs-mar-tb" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.2s">
          <div class="media">
            <div class="media-left media-middle"><span class=" rxs-lou-icon"></span></div>
            <div class="media-body media-middle">学校共有 
            <span ng-show="schoolData.teachingBuilding == null || schoolData.teachingBuilding == 0 || schoolData.teachingBuilding == ''">未维护</span>
            <span ng-show = "schoolData.teachingBuilding != null && schoolData.teachingBuilding != 0 && schoolData.teachingBuilding != ''">{{schoolData.teachingBuilding}}</span>
                                    栋教学楼，其中普通教室{{schoolData.room}}间， 可同时容纳 未维护 人学习。</div>
          </div>
        </div>
         <div class="ani rxs-mar-tb" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.4s">
          <div class="media">
            <div class="media-left media-middle"><span class=" rxs-book-icon"></span></div>
            <div class="media-body media-middle">图书馆内藏书共计 <span ng-show="schoolData.books == null || schoolData.books == 0 || schoolData.books == ''">未维护</span>
		        		<span ng-show = "schoolData.books != null && schoolData.books != 0 && schoolData.books != ''">{{schoolData.books}}</span> 册，图书馆内共
              计阅读桌位 未维护 个。</div>
          </div>
        </div>
         <div class="ani rxs-mar-tb" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.6s">
          <div class="media">
            <div class="media-left media-middle"><span class=" rxs-fan-icon"></span></div>
            <div class="media-body media-middle">校园内共有 <span ng-show="schoolData.restaurant == 0">未维护</span>
		        		<span ng-show = "schoolData.restaurant != 0">{{schoolData.restaurant}}</span> 个大型餐厅，
		        		就餐窗口  <span ng-show="schoolData.restaurantWin == null || schoolData.restaurantWin == 0 || schoolData.restaurantWin == ''">未维护</span>
		        		<span ng-show = "schoolData.restaurantWin != null && schoolData.restaurantWin != 0 && schoolData.restaurantWin != ''">{{schoolData.restaurantWin}}</span> 个。
              </div>
          </div>
        </div>
         <div class="ani rxs-mar-tb" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="0.8s">
          <div class="media">
            <div class="media-left media-middle"><span class=" rxs-tiyu-icon"></span></div>
            <div class="media-body media-middle">校园内运动场地共 未维护 平方米，可以充
              分满足你的运动生活。</div>
          </div>
        </div>
        <div class="ani rxs-mar-tb" swiper-animate-effect="fadeInLeft" swiper-animate-duration="0.5s" swiper-animate-delay="1s">
          <div class="media">
            <div class="media-left media-middle"><span class=" rxs-market-icon"></span></div>
            <div class="media-body media-middle">校园内超市、商铺共计<span ng-show="schoolData.shops == null || schoolData.shops == 0 || schoolData.shops == ''">未维护</span>
		        		<span ng-show = "schoolData.shops != null && schoolData.shops != 0 && schoolData.shops != ''">{{schoolData.shops}}</span> 家，能充分保障
              你的购物生活。</div>
          </div>
        </div>
        <div class="ani text-center" swiper-animate-effect="fadeInUp" swiper-animate-duration="0.5s" swiper-animate-delay="1s"> <a href="../../wechat/map/map.jsp" class="rxs-map-text">查看校园地图 <i class="icon-angle-right icon-large"></i></a></div>
      </div>
    </div>
    <!-- 军训 -->
    <jsp:include page="/student/colorPage/tpl/jx.jsp"></jsp:include>
    
     <!-- 辅导员 -->
    <jsp:include page="/student/colorPage/tpl/fdy.jsp"></jsp:include>
    
     <!-- 课程 -->
    <jsp:include page="/student/colorPage/tpl/kc.jsp"></jsp:include>
    
   	 <!-- 就餐窗口 -->
    <jsp:include page="/student/colorPage/tpl/ct.jsp"></jsp:include>
    
     <!-- 专业 -->
    <jsp:include page="/student/colorPage/tpl/zy.jsp"></jsp:include>
    
     <!-- 老乡 -->
    <jsp:include page="/student/colorPage/tpl/lx.jsp"></jsp:include>
    
  </div>
  <div class="rxs-btm-arw"> </div>
  <!-- 如果需要分页器 -->
  <div class="swiper-pagination"></div>
</div>

<script>
    var swiper = new Swiper('.swiper-container', {
	    keyboardControl:true,
	    mousewheelControl:true,
        pagination: '.swiper-pagination',
        paginationClickable: true,
        direction: 'vertical',
		onInit: function(swiper){ //Swiper2.x的初始化是onFirstInit
			swiperAnimateCache(swiper); //隐藏动画元素 
			swiperAnimate(swiper); //初始化完成开始动画
		}, 
		onSlideChangeEnd: function(swiper){ 
			swiperAnimate(swiper);  //每个slide切换结束时也运行当前slide动画
		} 
    });
    </script>
</body>
</html>
