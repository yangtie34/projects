<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0"/>
<title>角色管理</title>
<jsp:include page="../../common/baseIndex.jsp"></jsp:include>
<script type="text/javascript" src="${ctxStatic}/product/index.js"></script>
<script type="text/javascript" src="${ctxStatic}/product/system/permiss/js/roleController.js"></script>
</head>
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-common.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic}/resource/permiss/css/right-quanxian.css" />
<body ng-controller="roleController">
<div class="content">
<!--左侧导航开始-->
		<jsp:include page="../../manager/menu.jsp"></jsp:include>
  <!--左侧导航结束-->
    <!--右侧内容开始-->
  <div class="shuju-con-right">
    <!--右侧头部开始-->
    <div class="shuju-right-title clearfix">
      <div class="shuju-title-left">
        <h3>角色管理</h3>
      </div>
    </div>
    <!--右侧头部结束-->
    <div class="shuju-content-list">
      <div class="quanxian-content">
        <!--条件-->
        <div class="quanxian-header"> 
          <div class="quanxian-tools">
            <div class="quanxian-btn-group">
              <button type="button" style="outline:none;" ng-click="showCreateRoleDiv()" class="quanxian-btn-default"><span class=" quanxian-btn-add-icon">新增</span></button> 
            </div>
            <!--搜索-->
            <div class="quanxian-block-search">
              <div class="quanxian-in-search"><span class="quanxian-fposition">
                <input class="quanxian-search-t" type="text" placeholder="输入标识/名称查询..." ng-keyup="myKeyup($event)" ng-model="searchRole.name_OrDescription">
                <a href="javascript:void(0)" class="quanxian-search-q" ng-click="queryGridContent()"></a> </span>
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
				<tr> <th class="quanxian-text-left" >角色标识</th> <th>角色名称</th> <th>角色类型</th> <th>启用/禁用</th><th>授权</th><th>查看人员</th><th>设置主页/删除</th> </tr>
			</thead>
			<tbody>
				<tr ng-repeat="item in vm.items" class="quanxian-bg-hover">
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-text-left quanxian-sub-bottomsolid' : 'quanxian-text-left'" >
				<!-- 	 <span class="green-font">{{item.name_}}</span> -->
					 <a href="" class="quanxian-alink" ng-click="update(item);" title="编辑角色">{{item.name_}}</a> 
					  </td>
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''"> {{item.description}} </td>
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''"> {{item.role_type}} </td>
					<td ng-if="item.istrue==1" ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''">
						<a href="javascript:void(0)" ng-click="updateRole(item)" class="quanxian-icon-a shuju-img-qiyong-icon" title="当前 启用 状态，点击停用">停用</a> 
					</td>
					<td ng-if="item.istrue==0" ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''">
						<a href="javascript:void(0)" ng-click="updateRole(item)" class="quanxian-icon-a shuju-img-jinyong-icon" title="当前 停用 状态，点击启用">启动</a> 
					</td>
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : '' ">
						<a href="${ctx}/system/permssion/roleid/{{item.id}}" class="quanxian-icon-a shuju-img-shouquan-icon"  title="角色授权">角色授权</a> 
					</td>
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : '' ">
						<a href="${ctx}/user/list?roleId={{item.id}}" class="quanxian-alink" target="_blank" title="查看人员">查看人员</a> 
					</td>
					<td ng-class="$index + 1==vm.items.length ? 'quanxian-sub-bottomsolid' : ''">
						<a href="javascript:void(0)" ng-click="showSetMain(item)" ng-if="item.ismain==1" class="quanxian-icon-a shuju-img-xiugai-icon" title="设置主页">设置主页</a> 
						<a href="javascript:void(0)" ng-click="deleteRole(item)" ng-if="item.ismain!=1" class="quanxian-icon-a shuju-img-delete-icon" title="删除角色">删除角色</a> 
					</td>
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
  <div cs-window show="createRoleDiv" autoCenter="true" offset="offset" title="'设置角色'">
	 		 	<div>
	 		<dl class="quanxian-select-group-dl">
      <dt>角色标识：</dt>
       <dd ng-if="role.ismain==1"><input type="text" ng-model="role.name_" class="form-control" placeholder="角色标识" disabled></dd>
      <dd ng-if="role.ismain!=1"><input type="text" ng-model="role.name_" class="form-control" placeholder="角色标识"></dd>
   	</dl>
   		 		<dl class="quanxian-select-group-dl">
      <dt>角色名称：</dt>
      <dd><input type="text" ng-model="role.description" class="form-control" placeholder="角色名称"></dd>
   	</dl>
   	 		<dl class="quanxian-select-group-dl">
      <dt>角色类型：</dt>
      <dd><select ng-model="roleType" ng-options="rt.name for rt in roleTypeSelect" class="form-control">
	 				<option value="">---请选择---</option>
	 				</select></dd>
   	</dl>
   		<dl class="quanxian-select-group-dl">
      <dt>是否可用：</dt>
      <dd>
        <div check-box-switch state="role.istrue==1?true:false;" result="role.istrue" style="padding-top: 5px;">
  	</div>
      </dd>
    </dl>
   	 	 	<!-- 	<dl class="quanxian-select-group-dl">
      <dt>角色状态：</dt>
      <dd><select ng-model="role.istrue" class="form-control">
	 				<option value="">---请选择---</option>
			 			<option value="1">正常</option>
			 			<option value="0">冻结</option>
	 				</select></dd>
   	</dl>
   	<br/> -->
	 	</div>
	 	 <div class="modal-footer">
	 		<button class="btn btn-primary" ng-click="role.istrue=role.istrue==true?1:0;createRole()">保存</button>
	</div>
	</div>
  <div cs-window show="showWinFlag" autoCenter="true" offset="offset" title="'设置主页面'">
 	<div>
      <dt>主页面：</dt>
 		<div cg-combo-box source="boxSource" result="boxResult" on-select="boxSelect($data)" style="position: absolute;"></div><br>
 		</div>
 	<br/>
 	 <div class="modal-footer" style="width: 200px;">
	 		<button class="btn btn-primary" ng-click="setMain()">保存</button>
	</div>
  </div>
	
	
</body>
</html>
