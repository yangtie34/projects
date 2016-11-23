<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html ng-app="app">
<head>
<script type="text/javascript">
var plan=${plan};
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"/>
<title>计划日志</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/system/task/js/planLogController.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/xiala.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />
<body ng-controller="planLogController">
<div class="content">
<!--左侧导航开始-->
<jsp:include page="../../manager/menu.jsp"></jsp:include>
  <!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
    <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>计划日志</h3>
      </div>
    </div>
    <!--右侧头部结束-->
    <div class="shuju-content-list">
      <div class="quanxian-content">
        <!--条件-->
        <div class="quanxian-header"> 
          <div class="quanxian-tools">
          <!--   <div class="quanxian-btn-group">
              <button type="button" style="outline:none;" ng-click="showCreateRoleDiv()" class="quanxian-btn-default"><span class=" quanxian-btn-add-icon">新增</span></button> 
            </div> -->
            <!--搜索-->
<!--             <div class="quanxian-block-search">
              <div class="quanxian-in-search"><span class="quanxian-fposition">
                <input class="quanxian-search-t" type="text" placeholder="计划名称 模糊匹配" ng-keyup="myKeyup($event)" ng-model="plan.name_">
                <a href="javascript:void(0)" class="quanxian-search-q"></a> </span>
              自动补全功能暂时没有做，之后完善
                 <div class="quanxian-search-tblock" style="display:block">
                   display:none:隐藏
                  <ul>
                    <li> 王苏 </li>
                    <li> 王文娟 </li>
                    <li> 王娟 </li>
                    <li> 王文 </li>
                  </ul>
                </div>
              </div>
            </div> -->
            <!--搜索-->
          </div>
        </div>
        <!--条件-->
        <!-- 时间导航条 -->
<!-- <div class="bs-example bs-navbar-top-example" data-example-id="navbar-fixed-to-top">
    <nav class="navbar navbar-default navbar-fixed-top">
      We use the fluid option here to avoid overriding the fixed width of a normal container within the narrow content columns.
      <div class="container-fluid">
        <div class="navbar-header">
          
          <a class="navbar-brand" href="#">Brand</a>
        </div>

        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-6">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#">Link</a></li>
            <li><a href="#">Link</a></li>
          </ul>
        </div>/.navbar-collapse
      </div>
    </nav>
  </div> -->
      <!--  -->
        <!--表格 -->
        <div class="panel panel-success" ng-class="panelClass[$index%5]"ng-repeat="item in vm.items"> 
      <div class="panel-heading">
        <h3  class="panel-title">{{plan.name_}}{{$index}}:{{item.startTime}}开始 执行结果：{{item.isYes=='1'?'成功':'失败'}} {{item.endTime}}结束
        <span ng-class="table{{item.id}}?'glyphicon-minus':'glyphicon-plus'" class="glyphicon" aria-hidden="true" style="float:right"
        ng-click="plusClick(item);" class="quanxian-alink"></span>
        </h3>
      </div>
      <div class="panel-body" ng-show="table{{item.id}}">
              <table class="quanxian-sub-table" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr> <th class="quanxian-text-left" >业务名称</th> <th>开始时间</th> <th>结束时间</th> <th>验证结果</th><th>执行结果</th><th></th><th></th> </tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in vm['items'+item.id]" class="quanxian-bg-hover">
				<td>{{item.logType}}</td>
				<td>{{item.startTime}}</td>
				<td>{{item.endTime}}</td>
				<td>
				<a ng-click="getVerify(item)" class="quanxian-alink" target="_blank" title="查看验证信息">查看验证信息</a> 
				</td>
				<td>{{item.isYes==1?'成功':'失败'}}</td>
				</tr>
			</tbody>
		</table>
        <!--表格-->
        		<div class="quanxian-pager" ng-show="p['page'+item.id].totalRows>10" ><span class="quanxian-pager-text-ccc">共 {{p['page'+item.id].totalPages}} 页，数据 {{p['page'+item.id].totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="p['page'+item.id].totalRows" ng-model="p['page'+item.id].currentPage" max-size="p['page'+item.id].numPerPage" items-per-page="p['page'+item.id].numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
				<select ng-model="p['page'+item.id].numPerPage" style="border: 1px solid #DDD;"><option
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
    
        
        
		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
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
      	
      </div>
    </div>
  </div>
  <!--右侧内容结束-->
</div>
<!--content-->
  <div cs-window show="getVerifyDiv" autoCenter="true" offset="offset" title="'验证结果'">
	 	<div>
	 		<ol>
 	 <li ng-repeat="item in vm[getVerifyVm]"> 
 	 {{item.logType}}:{{item.resultDesc}}
 	 </li>
		</ol>
	 	</div>
	 </div>	
	
</body>
</html>
