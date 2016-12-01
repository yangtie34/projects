<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>学生全景</title>
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/student/StuQjController.js"></script>
	 	    <script type="text/javascript"
	src="${ctxStatic}/person/manage/common/common.js"></script>
	
</head>

<body ng-controller="stuQjController">
<jsp:include page="../top.jsp"></jsp:include>
<section class="jiaozhi-title" >
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">我的动态</h3>
    <div class="input-group input-group-sm" date-picker result="date1" dateFmt="yyyy-MM-dd" double="true" ></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
    --> </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">消费</a> >></h4>
        <div class="row">
          <div cs-chart config = "vm.items[0].option" class="col-xs-4 col-md-4" style="height:250px;"></div>
          <div class="col-xs-8 col-md-8">
            <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
             <!--  <tr>
                <td><span class="jiaozhi-money-icon">~</span> 一卡通余额</td>
                <td><span class="lead">{{vm.items[0].ye}}</span></td>
              </tr> -->
              <tr ng-repeat="item in vm.items[0].list">
                <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[$index]}};" aria-hidden="true"></span> {{item.FIELD}}</td>
                <td><span class="lead">{{item.VALUE}}</span></td>
              </tr>
            </table>
          <!--   <p>根据你的个人消费习惯,请及时充值，以免影响使用。</p> -->
          </div>
        </div>
      </section>
      <section  class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">充值记录</a> >></h4>
        <div class="row">
         <div cs-chart config = "vm.items[1].option" class="col-xs-4 col-md-4" style="height:250px;"></div>
          <div class="col-xs-8 col-md-8">
            <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
              <tr ng-repeat="item in vm.items[1].list1">
                <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[$index]}};" aria-hidden="true"></span>  {{item.FIELD}}</td>
                <td><span class="lead">{{item.VALUE}}</span></td>
              </tr>
              <tr ng-repeat="item in vm.items[1].list2"ng-show="$index<5">
                <td>{{item.TIME_}}</td>
                <td>{{item.FIELD}}{{item.VALUE}}元</td>
              </tr>
            </table>
             <small style="float: right;" ng-show="vm.items[1].list2.length>5"><a ng-click="teaczDiv=true;teaczDivindex=10;">显示更多...</a></small>
       
            <p>现金充值 <span class="lead"> {{vm.items[1].xjcz.val|number:2}} </span> 占比 <span class="lead">{{vm.items[1].xjcz.bl}}</span> 。</p>
          </div>
        </div>
      </section>
      </div><div class="row jiaozhi-cross">
      <section  class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">消费分析</a> >></h4>
        <div class="row">
          <div class="col-xs-12 ">
            <p>日均消费为 <span class="lead"> {{vm.items[2].rjxf}}</span> 元 ,消费水平已排列在矿大 <span class="lead">73% </span> 人之前。</p>
             <div cs-chart config = "vm.items[2].xffx" class="col-xs-12" style="height:250px;"></div>
        </div>
      </section>
<section class="col-xs-12 col-md-6"> 
  <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">余额</a> >></h4>
        <div class="jiaozhi-card-info"> 
            <span class="jiaozhi-money-top-icon"> 一卡通余额</span> 
            <h2 class="jiaozhi-card-jine">{{vm.items[0].ye}}</h2>
             <p>根据你的个人消费习惯,请及时充值，以免影响使用。 </p>
          
        </div>
      </section>
    </div>
  </div>
</article>
<section class="jiaozhi-title"  >
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">矿大生活</h3>
    <div class="input-group input-group-sm" date-picker result="date2" dateFmt="yyyy-MM-dd" double="true" ></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
    --> </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
  <div class="row jiaozhi-cross">
        <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">今日课程<span class="badge jiaozhi-section-badge">{{vm.items[0].length}}</span></a> >></h4>
        <div class=" " ng-repeat="item in vm.items[3]" init="i=0;">
          <dl class="jiaozhi-biaoji-dl">
            <dt>{{$index+1}}、{{item.COURSE_NAME}} </dt>
            <dd>{{item.TIME_}}&nbsp;&nbsp;|&nbsp;&nbsp; 上课教室：{{items.CLASSROOM_NAME||'未维护'}}</dd>
          </dl>
          <ul class="jiaozhi-banji-ul" ng-repeat="items in item.list">
            <li>上课教室：{{items.CLASS_NAME}} <em class="jiaozhi-middle-dot">&#183;</em> 学生人数：{{items.STUS}}</li>
           <!--  <li><a class="jiaozhi-more-link" href="#">更多>></a></li> -->
          </ul>
        </div>
      </section>
 <section  class="col-xs-12 col-md-6">
      <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">日均上网时间</a> >></h4>
      <div class="row">
        <div class="col-xs-12 ">
          <p>日均上网时长为    <span class="lead"> 3</span>   小时  ,在线时长已排列在矿大       <span class="lead">73% </span> 人之前。</p>
           <div cs-chart config = "vm.items[4]" class="col-xs-12" style="height:350px;"></div>
      </div>
    </section>
 </div>
     <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" >推荐图书</a> >></h4>
        <div class=" " >
          <ol class="jiaozhi-ol-list"  ng-repeat="item in vm.items[5]">
            <li >
            <span class="label label-default" ng-class="$index<3?'label-danger':''">{{$index+1}}</span>
            &nbsp;&nbsp;
            <a class="jiaozhi-alink-under" >{{item.NAME_}}&nbsp;&nbsp;&nbsp;&nbsp;作者：{{item.WRITE_}}&nbsp;&nbsp;&nbsp;&nbsp;出版社：{{item.PRESS}}</a> 
            </li>
            
         <!--    <li><span class="label label-danger">2</span>&nbsp;&nbsp;<a class="jiaozhi-alink-under" >工艺发展史    作者：李砚祖 </a></li>
            <li><span class="label label-danger">3</span>&nbsp;&nbsp;<a class="jiaozhi-alink-under" >中国艺术精神    作者：徐复观</a></li>
            <li><span class="label label-default">4</span>&nbsp;&nbsp;<a class="jiaozhi-alink-under" >JAVA编程技术    作者：Bruce Eckel  译：张昌佳 </a></li>
            <li><span class="label label-default">5</span>&nbsp;&nbsp;<a class="jiaozhi-alink-under" >JAVA编程技术    Bruce Eckel </a></li> -->
          </ol>
        </div>
      </section>
      <section  class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" >借阅分类</a> >></h4>
        <div cs-chart config = "vm.items[6]" class="col-xs-12" style="height:350px;"></div>
      </section>
    </div>

</article>
</body>
      <div cs-window show="teaczDiv" autoCenter="true" offset="offset" title="'充值记录详情'" class="col-xs-8 col-md-8"style="width:450px">
			<table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
              <tr ng-repeat="item in vm.items[1].list2" ng-show="$index<teaczDivindex">
              <td>{{$index+1}}</td>
                <td>{{item.TIME_}}</td>
                <td>{{item.FIELD}}{{item.VALUE}}元</td>
              </tr>
            </table>
            <span ng-show="teaczDivindex<=vm.items[1].list2.length" class="djxsgd"><a ng-click="teaczDivindex=teaczDivindex+10;">点击显示更多...</a></span>
			</div>
</html>