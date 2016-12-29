<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>学生信息</title>
     <script type="text/javascript"
	src="${ctxStatic}/resource/person/jiaozhi-hover-menu.js"></script>
	    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/student/StuInfoController.js"></script>
	 <script type="text/javascript"
	src="${ctxStatic}/person/student/StuSchLifeController.js"></script>
	 <script type="text/javascript"
	src="${ctxStatic}/person/student/StuStudyController.js"></script>
</head>
<jsp:include page="../top.jsp"></jsp:include>
<body>

<ul class="jiaozhi-hover-menu" 
ng-init="menus=['个人档案','荣誉及奖励','一卡通','借阅信息','成绩查询','矿大首次']">
  <li class="li_menu_0{{$index+1}}" 
  ng-repeat="item in menus">
  <a href="#li_menu_0{{$index+1}}">{{item}}</a>
  </li>
</ul>
<!-- 学生全景中的内容(个人档案---荣誉及奖励) -->
<div ng-controller="StuInfoController">
<!-- 个人档案 -->
<section class="jiaozhi-title" id="li_menu_01">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">个人档案</h3>
       <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：学工系统</small>
    </form>
  </div>
</section>
</article>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
          <tr ng-repeat="(key, value) in vm.items[0]" ng-show="$index%2==0">
            <td>{{stuInfoTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
      <section class="col-xs-12 col-md-6">
        <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
         <tr ng-repeat="(key, value) in vm.items[0]" ng-show="$index%2!=0">
            <td>{{stuInfoTitles[$index]}}</td>
            <td><span class="lead-normal">{{value}}</span></td>
          </tr>
        </table>
      </section>
    </div>
  </div>
</article>
<!-- 荣誉及 奖励 -->
<section class="jiaozhi-title" id="li_menu_02">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">荣誉及奖励</h3>
       <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：教务系统</small>
    </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <section class=" ">
      <p>自入学以来， {{vm.items[0].CL01}}所获得的荣誉及奖励信息 ： 荣誉奖励<span class="text-danger lead"> {{vm.items[1].length}} </span>次</p>
      <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0" ng-show="vm.items[1].length>0">
        <thead>
              <tr>
              <th></th>
                <th scope="col" ng-repeat="item in stugetGloryTiltles">{{item}}</th>
              </tr>
        </thead>
        <tbody>
              <tr ng-repeat="item in vm.items[1]" init="i=0;">
                <td><span class="label label-info">{{$index+1}}</span></td>
                <td ng-repeat="(key,value) in item" ng-show="key!='NUM'">{{value}}</td>
              </tr>
        </tbody>
      </table>
    </section>
  </div>
</article>
</div>
<!-- 校园生活 --一卡通消费+余额  -- 充值记录 -->
<div ng-controller="StuSchLifeController">
<section class="jiaozhi-title" id="li_menu_03">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">一卡通</h3>
       <div class="input-group input-group-sm" date-picker result="date2" dateFmt="yyyy-MM-dd" double="true" ></div> 
<!--       <div class="input-group input-group-sm">
        <div class="input-group-addon jiaozhi-date-icon">~~</div>
        <input type="text" class="form-control" id=" " placeholder="2016/02/04 ~ 2016/03/15">
      </div> -->
       <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：一卡通系统</small>
     </form>
  </div>
</section>
<!-- 一卡通消费+余额 -->
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" href="#">消费+余额</span> &gt;&gt;</h4>
        <div class="row">
         <div cs-chart config = "vm.items[0].option" class="col-xs-12 col-md-5" style="height:250px;"></div>
          <%-- <div class="col-xs-4 col-md-4"><img width="100%" src="${ctxImg}/tjt03.jpg"></div> --%>
          <div class="col-xs-8 col-md-8">
            <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><span class="jiaozhi-money-icon">~</span> 一卡通余额</td>
                <td><span class="lead">{{vm.items[0].ye}}</span></td>
              </tr>
              <tr ng-repeat="item in vm.items[0].list">
                <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[$index]}};" aria-hidden="true"></span> {{item.FIELD}}</td>
                <td><span class="lead">{{item.VALUE}}</span></td>
              </tr>
            </table>
            <p>根据你的个人消费习惯,请及时充值，以免影响使用。</p>
          </div>
        </div>
      </section>
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" href="#">充值记录</span> &gt;&gt;</h4>
        <div class="row">
          <%-- <div class="col-xs-4 col-md-4"><img width="100%" src="${ctxImg}/tjt04.jpg"></div> --%>
        <div cs-chart config = "vm.items[1].option" class="col-xs-4 col-md-4" style="height:250px;"></div>
          <div class="col-xs-8 col-md-8">
            <table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
              <tr ng-repeat="item in vm.items[1].list1">
                <td><span class="glyphicon glyphicon-stop" style="color:{{echarColor[$index]}};" aria-hidden="true"></span>  {{item.FIELD}}</td>
                <td><span class="lead">{{item.VALUE|number:2}}</span></td>
              </tr>
              <tr ng-repeat="item in vm.items[1].list2" ng-show="$index<5">
                <td>{{item.DATE_}}</td>
                <td>{{item.FIELD}}{{item.VALUE}}元</td>
              </tr>
            </table>
             <small style="float: right;" ng-show="vm.items[1].list2.length>5"><a ng-click="teaczDiv=true;teaczDivindex=10;">显示更多...</a></small>
             <div cs-window show="teaczDiv" autoCenter="true" offset="offset" title="'充值记录详情'" class="col-xs-8 col-md-8">
			<table width="100%" class="table table-hover jiaozhi-common-table" border="0" cellspacing="0" cellpadding="0">
              <tr ng-repeat="item in vm.items[1].list2" ng-show="$index<teaczDivindex">
              <td>{{$index+1}}</td>
                <td>{{item.DATE_}}</td>
                <td>{{item.FIELD}}{{item.VALUE}}元</td>
              </tr>
            </table>
            <span ng-show="teaczDivindex<=vm.items[1].list2.length" class="djxsgd"><a ng-click="teaczDivindex=teaczDivindex+10;">点击显示更多...</a></span>
			</div>
            <p>现金充值 <span class="lead">{{vm.items[1].xjcz.val|number:2}}元 </span> 占比 <span class="lead">{{vm.items[1].xjcz.bl}}</span> 。</p>
          </div>
        </div>
      </section>
      </div>   
    </div>
    </article>
  </div>
 <!-- 学生学业 -->     
<div ng-controller="StuStudyController">   
<section class="jiaozhi-title" id="li_menu_04">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">借阅信息</h3>
 		<div class="input-group input-group-sm" date-picker result="date1" dateFmt="yyyy-MM-dd" double="true" ></div>
       <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：图书系统</small>
     </form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" href="#">推荐图书</span> &gt;&gt;</h4>
        <span ng-show="vm.items[0].length==0" style="color: #f93d0a;" class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
        <div class=" ">
          <ol class="jiaozhi-ol-list"  ng-repeat="item in vm.items[0]">
            <li >
            <span class="label label-danger">{{$index+1}}</span>
            &nbsp;&nbsp;
            <span class="jiaozhi-alink-under" href="#">{{item.NAME_}}&nbsp;&nbsp;&nbsp;&nbsp;作者：{{item.WRITE_}}</span> 
            </li>
          </ol>
        </div>
      </section>
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" href="#">借阅分类</span> &gt;&gt;</h4>
        <div cs-chart config = "vm.items[1]" class="col-xs-12" style="height:350px;"></div>
      </section>
    </div>
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4><span class="jiaozhi-alink-under" href="#">借阅数量</span> &gt;&gt;</h4>
        <div class="row ">
          <div class="col-xs-12 ">
           <!--  <p>共借阅图书 <span class="lead"> 48</span> 本  ,阅读综合排名已排列在矿大 <span class="lead">73% </span> 人之前。</p> -->
             <div cs-chart config = "vm.items[2]" class="col-xs-12" style="height:350px;"></div>
        </div>
      </section>
      <section class="col-xs-12 col-md-6">
        <h4><span class="jiaozhi-alink-under" href="#">借阅明细</span> &gt;&gt;</h4>
        <div class="row">
        <span ng-show="page.totalRows==0" style="color: #f93d0a;" class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
          <div class="col-xs-12" ng-show="page.totalRows>0">
            <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <th scope="col" ng-repeat="item in jymxTitles">{{item}}</th>
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in vm.items[3]" init="i=0;">
                <td><span class="label label-info">{{$index+1}}</span></td>
                <td ng-repeat="(key,value) in item" ng-show="key!='NUM'"title="{{value}}">{{value| limitTo : 15 }}</td>
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
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">个人学业</h3>
<!-- <div class="input-group input-group-sm" date-picker result="date2" dateFmt="yyyy-MM-dd" double="true" ></div>
  -->      <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：教务系统</small>
     </form>
  </div>
</section>
<div class="container">
    
<!--     <div class="row">
      <section  class="col-xs-12">
        <div cg-kc-table source="vm.items[4]" class="jiaozhi-kebiao-table"></div>
      </section>
    </div> -->
    
    <div class="row" id="li_menu_05">
     <span ng-show="pagecj.totalRows==0" style="color: #f93d0a;" class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
      <section class="col-xs-12" ng-show="pagecj.totalRows>0">
        <h4><span class="jiaozhi-alink-under" href="#">成绩查询</span> &gt;&gt;</h4>
        <div class="row">
          <div class="col-xs-12">
            <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
              <thead>
                <tr>
                  <th scope="col" ng-repeat="value in getcjcxTitles">{{value}}</th>
                </tr>
              </thead>
              <tbody>
                <tr ng-repeat="item in vm.items[5]" init="i=0">
                  <td ng-repeat="(key,value) in item">{{value}}</td>
<!--              <td >{{item.c2}}</td>
                  <td >{{item.c3}}</td>
                  <td >{{item.c4}}</td>
                  <td >{{item.c5}}</td>
                  <td >{{item.c6}}</td> -->
                </tr>
            </table>
     		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{pagecj.totalPages}} 页，数据 {{pagecj.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="pagecj.totalRows" ng-model="pagecj.currentPage" max-size="pagecj.numPerPage" items-per-page="pagecj.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="pageXf_numPerPage">
				<select ng-model="pagecj.numPerPage" style="border: 1px solid #DDD;"><option
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
<div ng-controller="StuSchLifeController">
<section class="jiaozhi-title" id="li_menu_06">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">矿大首次</h3>
      <!-- <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
   -->  </form>
  </div>
</section>
<article class="jiaozhi-content" id="li_menu_07">
  <div class="container">
    <section class="jiaozhi-first-list" ng-repeat="(key,value) in vm.items[6]" >
       <div class="media" ng-show="key=='cf'">
         <div class="media-left media-middle"><div class="jiaozhi-img-circle jiaozhi-can-icon" > </div></div>
         <div class="media-body media-middle"><h4 class="jiaozhi-inline-block ">{{value[0].TIME_}} </h4><p class="jiaozhi-inline-block" ng-show="value[0].PAY_MONEY==null">未就餐！</p><p class="jiaozhi-inline-block" ng-show="value[0].PAY_MONEY!=null">进行了在矿大的首次就餐。消费了<span class="lead"> {{value[0].PAY_MONEY}}</span> 元 <!-- ,本次消费在你的单次就餐消费金额中高过<span class="lead">56%</span>范围 -->。</p></div>
       </div>
       <div class="media" ng-show="key=='gw'">
         <div class="media-left media-middle"><div class="jiaozhi-img-circle jiaozhi-shopping-icon" > </div></div>
         <div class="media-body media-middle"><h4 class="jiaozhi-inline-block ">{{value[0].TIME_}} </h4><p class="jiaozhi-inline-block" ng-show="value[0].PAY_MONEY==null">未购物！</p><p class="jiaozhi-inline-block"ng-show="value[0].PAY_MONEY!=null">进行了在矿大的首次购物。消费了<span class="lead"> {{value[0].PAY_MONEY}}</span> 元<!--  ,本次消费在你的单次就餐消费金额中高过<span class="lead">58%</span>范围 -->。</p></div>
       </div>
       <div class="media" ng-show="key=='jy'">
         <div class="media-left media-middle"><div class="jiaozhi-img-circle jiaozhi-book-icon" > </div></div>
         <div class="media-body media-middle"><h4 class="jiaozhi-inline-block ">{{value[0].TIME_}} </h4><p class="jiaozhi-inline-block" ng-show="value[0].NAME_==null">未借阅！</p><p class="jiaozhi-inline-block"ng-show="value[0].NAME_!=null">进行了在矿大的首次图书馆借阅，本次借阅内容为《{{value[0].NAME_}}》。</p></div>
       </div>
       <div class="media" ng-show="key=='ry'">
         <div class="media-left media-middle"><div class="jiaozhi-img-circle jiaozhi-jiang-icon" > </div></div>
         <div class="media-body media-middle"><h4 class="jiaozhi-inline-block ">{{value[0].TIME_}} </h4><p class="jiaozhi-inline-block" ng-show="value[0].NAME_==null">未获得荣誉！</p><p class="jiaozhi-inline-block" ng-show="value[0].NAME_!=null">获得了在矿大的首次荣誉。本次被评为{{value[0].NAME_}}。</p></div>
       </div>
    </section>
  </div>
</article>
</div>
</div>
</body>
</html>