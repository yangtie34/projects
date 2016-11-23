<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="author" content="Jophy" />
<title>资源管理</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript"
	src="${ctxStatic}/product/system/permiss/js/resourceController.js"></script>
</head>
 <link rel="stylesheet"
	href="${ctxStatic }/bootstrap-3.3.4-dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="${ctxStatic }/js/switch/css/bootstrap-switch.min.css">
<%-- <link href="${ctxStatic }/js/mtree/css/foundation.css" rel="stylesheet"
	type="text/css">
<link href="${ctxStatic }/js/mtree/css/mtree.css" rel="stylesheet" 
	type="text/css">--%>
<link href="${ctxStatic }/js/toastr/css/toastr.css" rel="stylesheet"
	type="text/css"> 
<!--[if lte IE 9]>
	<script src="${ctxStatic }/bootstrap-3.3.4-dist/js/respond.min.js"></script>
	<script src="${ctxStatic }/bootstrap-3.3.4-dist/js/html5shiv.min.js"></script>
	<![endif]-->
<style>
a:link {
	text-decoration: none;
}

a:visited {
	text-decoration: none;
}

a:hover {
	text-decoration: none;
}

a:active {
	text-decoration: none;
}
</style>
<body ng-controller="resourceController">
<div class="content">
<!--左侧导航开始-->
		<jsp:include page="../../manager/menu.jsp"></jsp:include>
		<!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
          <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>资源管理</h3>
      </div>
    </div>
    <hr class="quanxian-hr">
    <!--右侧头部结束-->
	<div class="quanxian-left-menu">
	<div class="quanxian-menu-list">
		<div cg-combo-check-tree source="treeData" result="treeResult"
			code="'code'" treeType='listTree'>
			<!-- code 是条件对应的字段代码 -->
		</div>
	<!-- 	{{treeResult}} -->
		</div>
	</div>
	<!--  -->
	<div class="quanxian-content quanxian-right-content">
	<div>
	<div class="quanxian-tools">
	<div class="quanxian-block-search" style="position:inherit;">
		<div class="quanxian-in-search"><span class="quanxian-fposition">
            <input class="quanxian-search-t ng-pristine ng-valid" type="text" placeholder="输入资源名称查询..." 
            ng-keyup="myKeyup($event)" ng-model="searchRes.name_">
            <a href="javascript:void(0)"  class="quanxian-search-q" ng-click="getResourceByInput()"></a> 
            </span>
          </div>
          </div>
        </div>
        
        <div class="quanxian-title-term">
          <span class="quanxian-title-quanjing-icon">{{thisNode.mc}}       {{parentres.shiro_tag?'父级权限标识符：'+parentres.shiro_tag:''}}</span>
          <span class="quanxian-btn-right">
          <div class="quanxian-btn-group">
           <!--  <button type="button" style="outline:none;" class="quanxian-btn-default"> 上级节点 </button> -->
            <button type="button" style="outline:none;" class="quanxian-btn-default" 
            ng-click="addResource.flag = true;resourceUpdataflag=false;resource={};"> 添加子菜单 </button>
          </div>
          </span>
           </div>

		<div cs-window show="addResource.flag" autoCenter="true" offset="offset" title="'资源'">
		<div>
			<input type="hidden" ng-model="resource.id" /> 
			<dl class="quanxian-select-group-dl">
      <dt>资源名称：</dt>
      <dd><input type="text" class="form-control"
				ng-model="resource.name_" placeholder="资源名称" /> </dd>
   	</dl>
   	<dl class="quanxian-select-group-dl">
      <dt>资源描述：</dt>
      <dd><input type="text" class="form-control"
				ng-model="resource.description" placeholder="资源描述" /> </dd>
   	</dl>
   	<dl class="quanxian-select-group-dl">
      <dt>权限标识：</dt>
      <dd><input class="form-control"
				type="text" ng-model="resource.shiro_tag" placeholder="权限标识" /> </dd>
   	</dl>
   	<dl class="quanxian-select-group-dl">
      <dt>排序号：</dt>
      <dd><input class="form-control"
				type="number" ng-model="resource.order_" placeholder="排序号" /></dd>
   	</dl>
   	   	<dl class="quanxian-select-group-dl" ng-show="thisNode.id=='0'" >
      <dt >系统类型：</dt>
      <dd ><select ng-model="resource.sysGroup_"  class="form-control">
	 				<option ng-repeat="code in codes" value="{{code.code_}}">{{code.name_}}</option>
				</select>
		</dd>
   	</dl>
		   	<dl class="quanxian-select-group-dl">
      <dt>资源类型：</dt>
      <dd> <select class="form-control"
				ng-model="resource.resource_type" ng-change="changeResType1();">
				<option value="">---请选择---</option>
				<c:forEach items="${resTypes }" var="resType">
					<option value="${resType.code_ }">${resType.name_ }</option>
				</c:forEach>
			</select></dd>
   	</dl>
	 	<dl class="quanxian-select-group-dl" ng-show="resource.resource_type=='02'" >
      <dt>资源路径：</dt>
      <dd><input class="form-control"
				ng-model="resource.url_" type="text" placeholder="资源路径" /> </dd>
   	</dl>
   	 	<dl class="quanxian-select-group-dl">
      <dt>关键字：</dt>
      <dd><input class="form-control"
				type="text" ng-model="resource.keyWord" placeholder="关键字" /></dd>
   	</dl>			
		<dl class="quanxian-select-group-dl">
      <dt>是否可用：</dt>
      <dd>
        <div check-box-switch state="resourceistrue" result="resource.istrue" style="padding-top: 5px;">
  	</div>
      </dd>
    </dl>	
		</div>	
		    <div class="modal-footer">
		    <button type="button" class="btn btn-primary" ng-show="!resourceUpdataflag" ng-click="resource.sysGroup_==''?resource.sysGroup_=thisNode.sysGroup_;resource.istrue?resource.istrue=1:resource.istrue=0;insert_res()">添加</button>
			<button type="button" class="btn btn-primary" ng-show="resourceUpdataflag" ng-click="resource.istrue?resource.istrue=1:resource.istrue=0;update_res()">确定</button>
	</div>	
		</div>

		<div>
<table class="quanxian-sub-table" width="100%" border="0" cellspacing="0" cellpadding="0">
				<thead>
					<tr >
						<th>资源名</th>
						<th>系统类型</th>
						<th>资源路径</th>
						<th>权限标识符</th>
						<th>描述</th>
						<th>启用/禁用</th>
						<th>编辑</th>
						<th>删除</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in vm.items" class="quanxian-bg-hover">
						<td>{{item.name_}}</td>
						<td>{{codeMap[item.sysGroup_]}}</td>
						<td>{{item.url_}}</td>
						<td>{{item.shiro_tag}}</td>
						<td>{{item.description}}</td>
							<td ng-if="item.istrue==1" ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''">
						<a href="javascript:void(0)" ng-click="istrue(item)" class="quanxian-icon-a shuju-img-qiyong-icon" title="当前 启用 状态，点击停用">停用</a> 
					</td>
					<td ng-if="item.istrue==0" ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''">
						<a href="javascript:void(0)" ng-click="istrue(item)" class="quanxian-icon-a shuju-img-jinyong-icon" title="当前 停用 状态，点击启用">启动</a> 
					</td>
						<td>
							<a href="#" ng-click="update1(item);" class="quanxian-icon-a shuju-img-xiugai-icon" title="编辑">编辑</a>
							</td>
						<td>
						<a href="javascript:void(0)" ng-click="del_res(item)" class="quanxian-icon-a shuju-img-delete-icon" title="删除">删除</a> 
						</td>

					</tr>
				</tbody>
			</table>
			<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
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
</body>
</html>