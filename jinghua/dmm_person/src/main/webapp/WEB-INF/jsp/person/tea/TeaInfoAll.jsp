<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>个人信息</title>
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/teacher/TeaInfoController.js"></script>
	 <script type="text/javascript"
	src="${ctxStatic}/person/teacher/TeaSchLifeController.js"></script>
	 <script type="text/javascript"
	src="${ctxStatic}/person/teacher/TeaKyController.js"></script>
	 	    <script type="text/javascript"
	src="${ctxStatic}/person/manage/common/common.js"></script>
	<script type="text/javascript"
	src="${ctxStatic}/resource/person/jiaozhi-hover-menu.js"></script>
</head>

<body>
<jsp:include page="../top.jsp"></jsp:include>

<ul class="jiaozhi-hover-menu" 
ng-init="menus=['个人档案','信息变动历史','工资收入','一卡通','网络生活','借阅信息','矿大首次']">
  <li class="li_menu_0{{$index+1}}" 
  ng-repeat="item in menus">
  <a href="#li_menu_0{{$index+1}}">{{item}}</a>
  </li>
</ul>

<div ng-controller="TeaInfoController">
<section class="jiaozhi-title" id="li_menu_01">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">个人档案</h3>
    </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
          <tr ng-repeat="(key, value) in vm.items[0]" ng-show="$index%2==0">
            <td>{{teaInfoTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
         <tr ng-repeat="(key, value) in vm.items[0]" ng-show="$index%2!=0">
            <td>{{teaInfoTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
    </div>
  </div>
</article>
<section class="jiaozhi-title" id="li_menu_02">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">信息变动历史</h3>
    </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row" ng-repeat="(key, value) in vm.items[1] " ng-init="index1=$index">
      <section class="col-xs-1">
        <div class="jiaozhi-vertical-text "
          ng-class="index1==0?'jiaozhi-vertical-text-org':'jiaozhi-vertical-text-green'"
        >{{key}}</div>
      </section>
      <section class="col-xs-11" >
        <div class="row text-center">
        <div ng-repeat="item in value" ng-init="index2=$index">
          <div class="col-xs-3" >
            <div class="jiaozhi-fang-box" 
            ng-class="index1==0?'jiaozhi-fang-box-org':'jiaozhi-fang-box-green'"
            > <span class="jiaozhi-fang-bg" 
             ng-class="index1==0?'jiaozhi-fang-bg-0{{index2+1}}':'jiaozhi-fang-bg-green-0{{index2+1}}'"
            >{{item.NAME_}}</span> </div>
            <p class="lead-normal">{{item.DATA_}}</p>
          </div>
          <div class="col-xs-1" ng-show="index2<value.length-1">
             <div class="jiaozhi-arrow-icon"
               ng-class="index1==0?'':'jiaozhi-arrow-icon-green'"
             ></div>
          </div>
            </div>
        </div>
      </section>
    </div>
  </div>
</article>
</div>

<div ng-controller="TeaSchLifeController" >
<section class="jiaozhi-title" id="li_menu_03">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">工资收入</h3>
     <div class="input-group input-group-sm" date-picker result="date1" dateFmt="yyyy-MM-dd" double="true" ></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
     --></form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <section>
      <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">工资组成</a> >></h4>
      <div class="row">
       <div cs-chart config = "vm.items[0].option" class="col-xs-12 col-md-5" style="height:250px;"></div>
        <div class="col-xs-12 col-md-7">
          <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
            <tr ng-repeat="item in vm.items[0].list">
              <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[$index]}};" aria-hidden="true"></span>{{item.FIELD}}</td>
              <td><span class="lead">{{item.VALUE}}</span></td>
            </tr>
            <tr>
              <td>应发工资合计</td>
              <td><span class="lead">{{vm.items[0].sum.yf}}</span></td>
            </tr>
            <tr>
              <td>代扣工资合计</td>
              <td><span class="lead">{{vm.items[0].sum.dk}}</span></td>
            </tr>
            <tr>
              <td>实发工资合计</td>
              <td><span class="lead">{{vm.items[0].sum.sf}}</span></td>
            </tr>
          </table>
        </div>
      </div>
    </section>
    <hr/>
    <section class="col-xs-12 col-md-6">
      <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">工资变化</a> >></h4>
      <div class="row">
      <div cs-chart config = "vm.items[1]"  style="height:350px;"></div>
    </section>
    <section class="col-xs-12 col-md-6">
      <h4><a class="jiaozhi-alink-under" href="#">工资条</a> >></h4>
      <div class="row">
        <div >
          <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <th scope="col" ng-repeat="item in gztTitles">{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in vm.items[2]" init="i=0;">
                <td><span class="label label-info">{{$index+1}}</span></td>
                <td ng-repeat="it in gztTitles">{{item[$index]}}</td>
              </tr>
            </tbody>
          </table>
          		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{pageGz.totalPages}} 页，数据 {{pageGz.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="pageGz.totalRows" ng-model="pageGz.currentPage" max-size="pageGz.numPerPage" items-per-page="pageGz.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="pageGz_numPerPage">
				<select ng-model="pageGz.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div>
        </div>
      </div>
    </section>
  </div>
</article>
<section class="jiaozhi-title" id="li_menu_04">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">一卡通</h3>
    <div class="input-group input-group-sm" date-picker result="date2" dateFmt="yyyy-MM-dd" double="true" ></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
    --> </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">消费+余额</a> >></h4>
        <div class="row">
          <div cs-chart config = "vm.items[3].option" class="col-xs-4 col-md-4" style="height:250px;"></div>
          <div class="col-xs-8 col-md-8">
            <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><span class="jiaozhi-money-icon">~</span> 一卡通余额</td>
                <td><span class="lead">{{vm.items[3].ye}}</span></td>
              </tr>
              <tr ng-repeat="item in vm.items[3].list">
                <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[$index]}};" aria-hidden="true"></span> {{item.FIELD}}</td>
                <td><span class="lead">{{item.VALUE}}</span></td>
              </tr>
            </table>
            <p>根据你的个人消费习惯,请及时充值，以免影响使用。</p>
          </div>
        </div>
      </section>
      <section  class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">充值记录</a> >></h4>
        <div class="row">
         <div cs-chart config = "vm.items[4].option" class="col-xs-4 col-md-4" style="height:250px;"></div>
          <div class="col-xs-8 col-md-8">
            <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
              <tr ng-repeat="item in vm.items[4].list1">
                <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[$index]}};" aria-hidden="true"></span>  {{item.FIELD}}</td>
                <td><span class="lead">{{item.VALUE}}</span></td>
              </tr>
              <tr ng-repeat="item in vm.items[4].list2"ng-show="$index<5">
                <td>{{item.DATE_}}</td>
                <td>{{item.FIELD}}{{item.VALUE}}元</td>
              </tr>
            </table>
             <small style="float: right;" ng-show="vm.items[4].list2.length>5"><a ng-click="teaczDiv=true;teaczDivindex=10;">显示更多...</a></small>
             <div cs-window show="teaczDiv" autoCenter="true" offset="offset" title="'充值记录详情'" class="col-xs-8 col-md-8">
			<table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
              <tr ng-repeat="item in vm.items[4].list2" ng-show="$index<teaczDivindex">
              <td>{{$index+1}}</td>
                <td>{{item.DATE_}}</td>
                <td>{{item.FIELD}}{{item.VALUE}}元</td>
              </tr>
            </table>
            <span ng-show="teaczDivindex<=vm.items[4].list2.length" class="djxsgd"><a ng-click="teaczDivindex=teaczDivindex+10;">点击显示更多...</a></span>
			</div>
            <p>现金充值 <span class="lead"> {{vm.items[4].xjcz.val|number:2}} </span> 占比 <span class="lead">{{vm.items[4].xjcz.bl}}</span> 。</p>
          </div>
        </div>
      </section>
      </div><div class="row jiaozhi-cross">
      <section  class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">消费分析</a> >></h4>
        <div class="row">
          <div class="col-xs-12 ">
            <p>日均消费为 <span class="lead"> {{vm.items[5].rjxf}}</span> 元 ,消费水平已排列在矿大 <span class="lead">73% </span> 人之前。</p>
             <div cs-chart config = "vm.items[5].xffx" class="col-xs-12" style="height:350px;"></div>
        </div>
      </section>
      <section  class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">消费明细</a> >></h4>
      <div class="row">
        <div class="col-xs-12">
          <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <th scope="col" ng-repeat="item in xfmxTitles">{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in vm.items[6]" init="i=0;">
                <td><span class="label label-info">{{$index+1}}</span></td>
                <td ng-repeat="(key,value) in item" ng-show="key!='NUM'">{{value}}</td>
              </tr>
            </tbody>
          </table>
          		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{pageXf.totalPages}} 页，数据 {{pageXf.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="pageXf.totalRows" ng-model="pageXf.currentPage" max-size="pageXf.numPerPage" items-per-page="pageXf.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="pageXf_numPerPage">
				<select ng-model="pageXf.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div>
        </div>
      </div>
      </section>
    </div>
  </div>
</article>
<section class="jiaozhi-title" id="li_menu_05">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">网络生活</h3>
     <div class="input-group input-group-sm" date-picker result="date3" dateFmt="yyyy-MM-dd" double="true" ></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
     --></form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <section  class="col-xs-12 col-md-6">
      <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">日均上网时间</a> >></h4>
      <div class="row">
        <div class="col-xs-12 ">
          <p>日均上网时长为    <span class="lead"> 3</span>   小时  ,在线时长已排列在矿大       <span class="lead">73% </span> 人之前。</p>
           <div cs-chart config = "vm.items[7]" class="col-xs-12" style="height:350px;"></div>
      </div>
    </section>
    <section  class="col-xs-12 col-md-6">
      <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" href="#">累计上网时间</a> >></h4>
      <div class="row">
        <div class="col-xs-12 ">
          <!-- <p>累计上网时长为       <span class="lead"> 340</span>   小时 ,在线时长已排列在矿大   <span class="lead">73% </span> 人之前。</p>
          -->  <div stu-chart config = "vm.items[8]" class="col-xs-12" style="height:350px;"></div>
      </div>
    </section>
  </div>
</article>
<div ng-controller="TeaKyController">
<section class="jiaozhi-title" id="li_menu_06">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">借阅信息</h3>
      <div class="input-group input-group-sm" date-picker result="date1" dateFmt="yyyy-MM-dd" double="true" ></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
     --></form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><a class="jiaozhi-alink-under" >推荐图书</a> >></h4>
        <div class=" " >
          <ol class="jiaozhi-ol-list"  ng-repeat="item in vm.items[0]">
            <li >
            <span class="label label-default" ng-class="$index<3?'label-danger':''">{{$index+1}}</span>
            &nbsp;&nbsp;
            <a class="jiaozhi-alink-under" >{{item.NAME_}}&nbsp;&nbsp;&nbsp;&nbsp;作者：{{item.WRITE_}}</a> 
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
        <div cs-chart config = "vm.items[1]" class="col-xs-12" style="height:350px;"></div>
      </section>
    </div>
    <div class="row jiaozhi-cross">
      <section  class="col-xs-12 col-md-6">
        <h4><a class="jiaozhi-alink-under" >借阅数量</a> >></h4>
        <div class="row ">
          <div class="col-xs-12 ">
            <p>共借阅图书 <span class="lead"> 48</span> 本  ,阅读综合排名已排列在矿大 <span class="lead">73% </span> 人之前。</p>
           <div cs-chart config = "vm.items[2]" class="col-xs-12" style="height:350px;"></div>
        </div>
      </section>
      <section  class="col-xs-12 col-md-6">
        <h4><a class="jiaozhi-alink-under" >借阅明细</a> >></h4>
        <div class="row">
          <div class="col-xs-12">
            <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <th scope="col" ng-repeat="item in jymxTitles">{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in vm.items[3]" init="i=0;">
                <td><span class="label label-info">{{$index+1}}</span></td>
                <td ng-repeat="(key,value) in item" ng-show="key!='NUM'" title="{{value}}">{{value| limitTo : 15 }}</td>
              </tr>
            </tbody>
            </table>
     		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="pageXf_numPerPage">
				<select ng-model="page.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div>
          </div>
        </div>
      </section>
    </div>
  </div>
</article>
</div>
<section class="jiaozhi-title" id="li_menu_07">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">矿大首次</h3>
   <!--   <div class="input-group input-group-sm" date-picker result="date4" dateFmt="yyyy-MM-dd" double="true" ></div> -->
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
     --></form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <section class="jiaozhi-first-list" ng-repeat="(key,value) in vm.items[9]" >
       <div class="media" ng-show="key=='cf'">
         <div class="media-left media-middle"><div class="jiaozhi-img-circle jiaozhi-can-icon" > </div></div>
         <div class="media-body media-middle"><h4 class="jiaozhi-inline-block ">{{value[0].TIME_}} </h4><p class="jiaozhi-inline-block">进行了在矿大的首次就餐。消费了<span class="lead"> {{value[0].PAY_MONEY}}</span> 元 ,本次消费在你的单次就餐消费金额中高过<span class="lead">56%</span>范围。</p></div>
       </div>
       <div class="media" ng-show="key=='gw'">
         <div class="media-left media-middle"><div class="jiaozhi-img-circle jiaozhi-shopping-icon" > </div></div>
         <div class="media-body media-middle"><h4 class="jiaozhi-inline-block ">{{value[0].TIME_}} </h4><p class="jiaozhi-inline-block">进行了在矿大的首次购物。消费了<span class="lead"> {{value[0].PAY_MONEY}}</span> 元 ,本次消费在你的单次就餐消费金额中高过<span class="lead">58%</span>范围。</p></div>
       </div>
       <div class="media" ng-show="key=='jy'">
         <div class="media-left media-middle"><div class="jiaozhi-img-circle jiaozhi-book-icon" > </div></div>
         <div class="media-body media-middle"><h4 class="jiaozhi-inline-block ">{{value[0].TIME_}} </h4><p class="jiaozhi-inline-block">进行了在矿大的首次图书馆借本次借阅内容为《{{value[0].NAME_}}》。</p></div>
       </div>
       <div class="media" ng-show="key=='ry'">
         <div class="media-left media-middle"><div class="jiaozhi-img-circle jiaozhi-jiang-icon" > </div></div>
         <div class="media-body media-middle"><h4 class="jiaozhi-inline-block ">{{value[0].TIME_}} </h4><p class="jiaozhi-inline-block">获得了在矿大的首次荣誉。本次被评为{{value[0].NAME_}}。</p></div>
       </div>
    </section>
     
  </div>
</article>
</div>
</body>
</html>