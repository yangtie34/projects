<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html ng-app="app">
<head>
<script type="text/javascript">
var work=${work};
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"/>
<title>业务验证</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/system/task/js/workVerifyController.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/xiala.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />
<body ng-controller="workVerifyController">
<div class="content">
<!--左侧导航开始-->
<jsp:include page="../../manager/menu.jsp"></jsp:include>
  <!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
    <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>业务验证</h3>
      </div>
    </div>
    <!--右侧头部结束-->
    <div class="shuju-content-list">
      <div class="quanxian-content">
        <!--条件-->
        <div class="quanxian-header"> 
          <div class="quanxian-tools">
            <div class="quanxian-btn-group">
              <button type="button" style="outline:none;" ng-click="createWorkVerifyDiv=true;verify={};workVerify={};" class="quanxian-btn-default"><span class=" quanxian-btn-add-icon">添加验证</span></button> 
            </div>
            <!--搜索-->
            <div class="quanxian-block-search">
              <div class="quanxian-in-search"><span class="quanxian-fposition">业务名称：
                <input class="quanxian-search-t" type="text" placeholder="计划名称 模糊匹配" ng-keyup="myKeyup($event)" ng-model="work.name_" disabled>
                <!-- <a href="javascript:void(0)" class="quanxian-search-q"></a> --> </span>
              <!-- 自动补全功能暂时没有做，之后完善
                 <div class="quanxian-search-tblock" style="display:block">
                   display:none:隐藏
                  <ul>
                    <li> 王苏 </li>
                    <li> 王文娟 </li>
                    <li> 王娟 </li>
                    <li> 王文 </li>
                  </ul>
                </div> -->
              </div>
            </div>
            <!--搜索-->
          </div>
        </div>
        <!--条件-->
        <!--表格 -->
        <table class="quanxian-sub-table" width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr> <th class="quanxian-text-left" >验证顺序</th> <th>验证名称</th> <th>验证规则</th> <th>删除</th><th></th><th></th><th></th> </tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in vm.items" class="quanxian-bg-hover">
					<td><a class="quanxian-alink" ng-click="">{{vm.itemsOrder[item.id+''+$index]}}</a></td>
					<td>{{item.name_}}</td>
					<td>{{workVerifys[$index].rule==1?'必须':'非必须'}}</td>
					<td><a href="javascript:void(0)" ng-click="delWorkVerify($index)" class="quanxian-icon-a shuju-img-delete-icon" title="删除">删除</a> </td>
				</tr>
			</tbody>
		</table>
        <!--表格-->
        
        
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
  <div cs-window show="createWorkVerifyDiv" autoCenter="true" offset="offset" title="'添加验证'">
	 	<div>
	 	<dl class="quanxian-select-group-dl">
      <dt>已选择：</dt>
      <dd><input type="text" class="form-control" ng-model="verify.name_" disabled placeholder="请选择验证"></dd>
   	</dl>
	 	 	<dl class="quanxian-select-group-dl">
      <dt>验证规则：</dt>
      <dd><select ng-model=workVerify.rule class="form-control">
	 					<option value="">---请选择---</option>
			 			<option value=1>非必须</option>
			 			<option value=0>必须</option>
			 	   </select></dd>
   	</dl>	
   	 	<dl class="quanxian-select-group-dl">
      <dt>验证顺序：</dt>
      <dd><input type="number" class="form-control" ng-model="workVerify.order_" ></dd>
   	</dl>
	 		<div class="windowTable">
<table  width="100%" border="0" cellpadding="0" cellspacing="0" class="table table-striped">
			<thead>
	            <tr border="0">
	            	<th></th>
	                <th>验证名称</th>
	                <th>验证类型</th>
	            </tr>
	         </thead>
	        <tbody>
	        <tr ng-repeat="item in vmW.items">
	        	<td><input type="radio" name="radio1" ng-click="checkVerify(item);"/></td>
					<td>{{item.name_}}</td>
					<td>{{codeMap[item.group_]}}</td>
					</tr>
	        </tbody>
	</table>
		<div class="quanxian-pager" ng-show="pageW.totalRows>10"><span class="quanxian-pager-text-ccc">共 {{pageW.totalPages}} 页，数据 {{pageW.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="pageW.totalRows" ng-model="pageW.currentPage" max-size="pageW.numPerPage" items-per-page="pageW.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
				<select ng-model="pageW.numPerPage" style="border: 1px solid #DDD;"><option
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
	 	<div class="modal-footer">
	 			<button  class="btn btn-primary"  ng-click="createWorkVerify()">确定</button>
	</div>
	 </div>	
	
</body>
</html>
