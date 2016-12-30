<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/person/images"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
    <title>职工科研</title>
    <script type="text/javascript" src="${ctxStatic}/person/index.js"></script>
    <script type="text/javascript"
	src="${ctxStatic}/person/teacher/TeaKyController.js"></script>
</head>
<jsp:include page="../top.jsp"></jsp:include>
<body ng-controller="TeaKyController">
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">借阅信息</h3><small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：图书馆系统</small>
      <div class="input-group input-group-sm" date-picker result="date1" dateFmt="yyyy-MM-dd" double="true" ></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
     --></form>
  </div>
</section>
<article class="jiaozhi-content">
  <div class="container">
    <div class="row jiaozhi-cross">
      <section class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" >推荐图书</span> >></h4>
         <span ng-show="vm.items[0].length==0" style="color: #f93d0a;" class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
        <div class=" " >
          <ol class="jiaozhi-ol-list"  ng-repeat="item in vm.items[0]">
            <li >
            <span class="label label-default" ng-class="$index<3?'label-danger':''">{{$index+1}}</span>
            &nbsp;&nbsp;
            <span class="jiaozhi-alink-under" >{{item.NAME_}}&nbsp;&nbsp;&nbsp;&nbsp;作者：{{item.WRITE_}}&nbsp;&nbsp;&nbsp;&nbsp;出版社：{{item.PRESS}}</span> 
            </li>
            
         <!--    <li><span class="label label-danger">2</span>&nbsp;&nbsp;<a class="jiaozhi-alink-under" >工艺发展史    作者：李砚祖 </a></li>
            <li><span class="label label-danger">3</span>&nbsp;&nbsp;<a class="jiaozhi-alink-under" >中国艺术精神    作者：徐复观</a></li>
            <li><span class="label label-default">4</span>&nbsp;&nbsp;<a class="jiaozhi-alink-under" >JAVA编程技术    作者：Bruce Eckel  译：张昌佳 </a></li>
            <li><span class="label label-default">5</span>&nbsp;&nbsp;<a class="jiaozhi-alink-under" >JAVA编程技术    Bruce Eckel </a></li> -->
          </ol>
        </div>
      </section>
      <section  class="col-xs-12 col-md-6">
        <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" >借阅分类</span> >></h4>
        <div cs-chart config = "vm.items[1]" class="col-xs-12" style="height:350px;"></div>
      </section>
    </div>
    <div class="row jiaozhi-cross">
      <section  class="col-xs-12 col-md-6">
        <h4><span class="jiaozhi-alink-under" >借阅数量</span> >></h4>
        <div class="row ">
          <div class="col-xs-12 ">
            <p>在借阅图书 <span class="lead"> {{vm.items[2].sum}}</span> 本。</p>
           <div cs-chart config = "vm.items[2].option" class="col-xs-12" style="height:350px;"></div>
        </div>
      </section>
      <section  class="col-xs-12 col-md-6">
        <h4><span class="jiaozhi-alink-under" >借阅明细</span> >></h4>
         <span ng-show="page.totalRows==0" style="color: #f93d0a;" class="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
        <div class="row" ng-show="page.totalRows>0">
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
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">科研信息</h3><small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：科研系统</small>
       <div class="input-group input-group-sm" date-picker result="date2" dateFmt="yyyy-MM-dd" double="true" timemonths="36"></div>
     <!--  <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：人事部（2016-03-01）</small>
    --> </form>
  </div>
</section>
<article class="jiaozhi-content">
<div class="container"  >
<div class="row jiaozhi-cross" >
  <section  class="col-xs-12 col-md-6">
    <h4 class="jiaozhi-section-title">
    <span class="jiaozhi-alink-under" >科研信息 <!-- <span class="badge jiaozhi-section-badge">5</span></a> --> >></p></h4>
    <div class="row">
<!--       <div class="col-xs-6 ">
        <ul class="jiaozhi-square-ul">
          <li>参与科研项目<span class="lead text-danger"> 6</span> 个</li>
          <li>科研成果<span class="lead text-danger"> 5</span> 个</li>
          <li>获得专利<span class="lead text-danger"> 6</span> 个</li>
          <li>发表文献 <span class="lead text-danger"> 6</span> 个</li>
          <li>发表文献<span class="lead text-danger"> 6</span> 个</li>
        </ul>
      </div> -->
      <div class="col-xs-6 ">
        <ul class="jiaozhi-square-ul"  ng-repeat="(key,value) in vm.items[4]" ng-show="key=='kyxm'">
          <li>参与科研项目<span class="lead text-danger"> {{value.length}}</span> 个</li>
     <!-- <li>科研成果<span class="lead text-danger"> 5</span> 个</li>
          <li>获得专利<span class="lead text-danger"> 6</span> 个</li>
          <li>发表文献 <span class="lead text-danger"> 6</span> 个</li>
          <li>发表文献<span class="lead text-danger"> 6</span> 个</li> -->
        </ul>
        <ul class="jiaozhi-square-ul"  ng-repeat="(key,value) in vm.items[4]" ng-show="key=='lw'">
          <li>发表文献<span class="lead text-danger"> {{value.length}}</span> 个</li>
        </ul>
        <ul class="jiaozhi-square-ul"  ng-repeat="(key,value) in vm.items[4]" ng-show="key=='hdzl'">
          <li>获得专利<span class="lead text-danger"> {{value.length}}</span> 个</li>
        </ul>
      </div>
    </div>
    <hr>
    <!-- 科研成果 -->
    <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" >获得成果</span> >></h4>
             <span ng-show="vm.items[4].hjcg.length==0" style="color: #f93d0a;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
    <div class=" " ng-repeat="(key,value) in vm.items[4]" >
      <ul class="jiaozhi-square-ul jiaozhi-square-ul-hr">
        <li ng-repeat="item in value" init="i=0;" ng-show ="key=='hjcg'&&$index<3">
          <dl class="jiaozhi-square-dl">
            <dt>成果：{{item.CL01}}</dt>
            <dd class="jiaozhi-square-dd">开始时间：{{item.CL02}}</dd>
            <dd>参与人：{{item.CL03}}</dd>
          </dl>
        </li>
<!--         <li>
          <dl class="jiaozhi-square-dl">
            <dt>成果：课题《校企合作的人才培养机制的研究》</dt>
            <dd class="jiaozhi-square-dd">开始时间：2015/02/08</dd>
            <dd>项目信息：主要研究解决水稻倒伏的问题</dd>
            <dd>参与人：张无忌  赵敏  周芷若</dd>
          </dl>
        </li> -->
      </ul>
    </div>
  </section>
  <section  class="col-xs-12 col-md-6"  x>
    <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" >科研项目</span> >></h4>
             <span ng-show="vm.items[4].kyxm.length==0" style="color: #f93d0a;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
    <div class=" "  ng-repeat="(key,value) in vm.items[4]" >
      <ul class="jiaozhi-square-ul jiaozhi-square-ul-hr">
        <li ng-repeat="item in value" init="i=0;" ng-show ="key=='kyxm'&&$index<4">
          <dl class="jiaozhi-square-dl">
            <dt>项目：{{item.CL01}}</dt>
             <dd class="jiaozhi-square-dd">开始时间：{{item.CL02}}</dd>
            <dd>参与人：{{item.CL03}}</dd> 
          </dl>
        </li>
      </ul>
    </div>
  </section>
</div>
<div class="row jiaozhi-cross">
  <section  class="col-xs-12 col-md-6">
    <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" >项目经费</span> >></h4>
             <span ng-show="vm.items[4].xmjf.length==0" style="color: #f93d0a;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
    <div class=" " ng-repeat="(key,value) in vm.items[4]" >
      <ul class="jiaozhi-square-ul jiaozhi-square-ul-hr">
        <li ng-repeat="item in value" init="i=0;" ng-show ="key=='xmjf'&&$index<3">
          <dl class="jiaozhi-square-dl">
            <dt>部门号/项目号：{{item.CL01}}/{{item.CL02}}</dt>
            <dd>部门名称：{{item.CL03}}</dd>
            <dd>项目信息：{{item.CL04}}</dd>
            <dd>项目金额：{{item.CL05}}元</dd>
          </dl>
        </li>
<!--         <li>
          <dl class="jiaozhi-square-dl">
            <dt>成果：课题《校企合作的人才培养机制的研究》</dt>
            <dd class="jiaozhi-square-dd">开始时间：2015/02/08</dd>
            <dd>项目信息：主要研究解决水稻倒伏的问题</dd>
            <dd>参与人：张无忌  赵敏  周芷若</dd>
          </dl>
        </li> -->
      </ul>
    </div>
  </section>
  <section  class="col-xs-12 col-md-6">
    <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" >发表文献</span> >></h4>
             <span ng-show="vm.items[4].lw.length==0" style="color: #f93d0a;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;无数据</span>
    <div class=" "  ng-repeat="(key,value) in vm.items[4]" >
      <ul class="jiaozhi-square-ul jiaozhi-square-ul-hr">
        <li ng-repeat="item in value" init="i=0;" ng-show ="key=='lw'&&$index<3">
          <dl class="jiaozhi-square-dl">
            <dt>名称：{{item.CL01}}</dt>
            <dd class="jiaozhi-square-dd">开始时间：{{item.CL02}}</dd>
            <dd>参与人：{{item.CL03}}</dd>
          </dl>
        </li>
     <!--    <li>
           <dl class="jiaozhi-square-dl">
            <dt>名称：外包中知识吸收能力的影响因素分析</dt>
            <dd class="jiaozhi-square-dd">开始时间：2015/02/08</dd>
            <dd>项目信息：主要研究解决水稻倒伏的问题</dd>
            <dd>参与人：张无忌  赵敏  周芷若</dd>
          </dl>
        </li> -->
      </ul>
    </div>
  </section>
</div>
</article>
<!-- 项目经费 -->
<section class="jiaozhi-title">
  <div class="container">
    <form class="form-inline">
      <h3 class="jiaozhi-page-header">资产状况</h3><small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：资产系统</small>
       <!-- <div class="input-group input-group-sm" date-picker result="date2" dateFmt="yyyy-MM-dd" double="true" ></div> -->
     <!--   <small class="text-danger"> <span class="glyphicon glyphicon-star" style="font-size:12px;" aria-hidden="true"></span> 信息来源：资产平台（2016-03-01）</small>
      --></form>
  </div>
</section>
<article>
  <div class="container">
<div class="row jiaozhi-cross">
  <section  class="col-xs-12 col-md-6">
    <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" >设备资产</span> >></h4>
    <div class="row"> 
      <div class="col-xs-12 ">
        <ul class="jiaozhi-square-ul jiaozhi-square-ul-hr jiaozhi-square-ul-half">
          <li ng-repeat="item in vm.items[5]" init="i=0;"> 
          <dl class="jiaozhi-square-dl" ng-repeat="(key, value) in item" >
            <dt>{{sbzcTitles[$index]}}</dt>
            <dd class="jiaozhi-square-dd">{{value}}</dd> 
          </dl>
          </li> 
      
        </ul>
      </div>
    </div>
  </section>
   <section  class="col-xs-12 col-md-6">
    <h4 class="jiaozhi-section-title"><span class="jiaozhi-alink-under" >公房使用</span> >></h4>
        <div class="row"> 
      <div class="col-xs-12 ">
        <ul class="jiaozhi-square-ul jiaozhi-square-ul-hr jiaozhi-square-ul-half">
          <li ng-repeat="item in vm.items[6]" init="i=0;"> 
          <dl class="jiaozhi-square-dl" ng-repeat="(key, value) in item" >
            <dt>{{sbzcTitles[$index]}}</dt>
            <dd class="jiaozhi-square-dd">{{value}}</dd> 
          </dl>
          </li> 
      
        </ul>
      </div>
    </div>
  </section> 
</div></div>
</article>
</body>
</html>
