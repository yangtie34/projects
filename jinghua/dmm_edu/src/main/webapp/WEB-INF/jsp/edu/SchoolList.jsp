<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="ctxImg" value="${pageContext.request.contextPath}/static/resource/edu/images"/>
<c:set var="edu" value="${pageContext.request.contextPath}/static/resource/edu"/>
<c:set var="schIco" value="${pageContext.request.contextPath}/static/resource/edu/schIcos"/>
<jsp:include page="/WEB-INF/jsp/common/baseIndex.jsp"></jsp:include>
<html ng-app="app">
<head>
<title>高校数据列表</title>
    <link rel="stylesheet" type="text/css" href="${edu}/css/font-awesome.min-3.2.1.css">
<link rel="stylesheet" type="text/css" href="${edu}/css/edu-style.css">
    
<script type="text/javascript" src="${ctxStatic}/edu/index.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/edu/SchoolListController.js"></script>
</head>
<body ng-controller="SchoolListController">
<nav class="navbar-inverse">
  <div class="container">
    <div class="navbar-header"> <span class="edu-tit" href="#"> 高校数据列表 </span> </div>
    <p class="edu-t-rt"><a href="${ctx}/logout"  class="navbar-link" title="退出"><i class="icon-off icon-2x"></i></a></p>
  </div>
</nav>
<div class="container" style="min-width:1000px;">
  <!--tools-->
  <div class="edu-bdr edu-pad-no edu-mt-20 clearfix">
  <div cg-mul-query-comm source="vm.items[0]" result="page.conditions"
		noborder="true" ></div>
  </div>
  <!--tools-->
  <div class=" edu-mar-tb">
    <h4 class="edu-btm-line"><span class="edu-ft-16 edu-btm edu-pad-bt"><b>共计{{page.totalRows}}所高校</b></span>
      <!-- /input-group -->
      <div class="input-group edu-right-ab"> <i></i>
        <input type="text" class="form-control edu-search " placeholder="输入名称查询..." ng-keyup="myKeyup($event)" ng-model="schoolName">
        <span class="input-group-btn">
        <button class="btn btn-success edu-btn-green" ng-click="queryGridContent();"type="button">搜索</button>
        <i></i> </span> </div>
      <!-- /input-group -->
    </h4>
    <!---->
    <div class="edu-info-list">
      <div class="row">
        
        <div class="col-xs-4" ng-repeat="item in vm.items[1]"> 
          <div class="thumbnail edu-img-hover">
            <div class="caption">
            <%--  <img src="${schIco}/zzqgyxy.jpg"   --%>
              <img src="${schIco}/{{item.icoId}}.png"
             width="150" height="150" alt="{{item.chName}}">
             <h4 class="edu-ft-16">{{item.chName}}</h4>
            </div>
            <!--显示-->
        <div class="thumbnail edu-img-hidden">
            <div class="caption">
              <h4 class="edu-ft-16"><b>{{item.chName}}</b></h4>
              <p>{{item.desc=='null'?'':item.desc}}</p> 
                <a type="button" class="btn edu-btn-link edu-btn-green btn-success  edu-btn-width" href="${ctx }/edu/SchoolQJ/{{item.id}}" target="_blank">去看看</a> 
            </div>
          </div>
          <!--显示-->
          </div>
        </div>
        
        
      </div>
      <!--分页-->
      		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">当前查询：共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
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
<!--分页-->
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/footer.jsp"></jsp:include>
</body>
</html>
