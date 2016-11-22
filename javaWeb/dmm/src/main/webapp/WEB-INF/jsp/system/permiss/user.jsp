<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/system/permiss/js/userController.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/xiala.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />
<body ng-controller="userController">
<div class="content">
<!--左侧导航开始-->
		<jsp:include page="../../manager/menu.jsp"></jsp:include>
		<!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
    <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>用户管理</h3>
      </div>
    </div>
    <!--右侧头部结束-->
	<!--右侧头部结束-->
    <div class="shuju-content-list">
      <div class="quanxian-content">
        <!--搜索-->
      	<div cg-mul-query-comm source="mutiSource" result="page.conditions" noborder="true"></div>	
      	<div class="quanxian-block-search" style="position:inherit;">
          <div class="quanxian-in-search"><span class="quanxian-fposition">
            <input class="quanxian-search-t" type="text" placeholder="输入姓名/学号查询..." ng-keyup="myKeyup($event)" ng-model="searchUser.usernameOrRealName">
            <a href="javascript:void(0)" class="quanxian-search-q" ng-click="queryGridContent();"></a> </span>
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
		<table class="quanxian-sub-table " width="100%" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr> <th class="quanxian-text-left">账号</th> <th>用户名</th> <th>部门</th> <th>角色</th><th>编辑角色</th> <th>禁/启用</th><th>授权</th> <th>重置密码</th></tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in vm.items"  class="quanxian-bg-hover">
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-text-left quanxian-sub-bottomsolid' : 'quanxian-text-left'" > {{item.username}} </td>
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''"> {{item.real_name}} </td>
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''"> {{item.dept_name}} </td>
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''"> {{item.role_descs}} </td>
					<td>
							<a href="#" ng-click="roleUpdate1(item);" class="quanxian-icon-a shuju-img-xiugai-icon" title="编辑角色">编辑角色</a>
							</td>
						<td ng-if="item.istrue==1" ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''">
						<a href="javascript:void(0)" ng-click="isTrue(item)" class="quanxian-icon-a shuju-img-qiyong-icon" title="当前 启用 状态，点击停用">停用</a> 
					</td>
					<td ng-if="item.istrue==0" ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''">
						<a href="javascript:void(0)" ng-click="isTrue(item)" class="quanxian-icon-a shuju-img-jinyong-icon" title="当前 停用 状态，点击启用">启动</a> 
					</td>
						<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : '' ">
						<a href="${ctx}/system/permssion/userid/{{item.id}}" target="_blank" class="quanxian-icon-a shuju-img-shouquan-icon"  title="角色授权">角色授权</a> 
					</td>
					<td>
							<a href="#" ng-click="resetPassword(item);" class="quanxian-alink" title="重置密码">重置密码</a>
							</td>
				</tr>
			</tbody>
		</table>
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
</div>
  <div cs-window show="roleUpdateDiv" autoCenter="true" offset="offset" title="'编辑角色'">
	 		 	<div>
	 		<dl class="quanxian-select-group-dl">
      <dt>基本角色：</dt>
      <dd><input type="text" ng-model="userRoles.mc" class="form-control" placeholder="角色标识" disabled></dd>
   	</dl>
   	<div style="margin-left:90px" cg-combo-check-tree source="userRoles" result="userRolesResult" 
			code="'code'" treeType='zTree'>
	 	</div>
	 	 
	</div>
	<div class="modal-footer">
	 		<button class="btn btn-primary" ng-click="roleUpdate2()">确定</button>
	</div>
	</div>
</body>
</html>
