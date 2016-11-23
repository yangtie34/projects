<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<script type="text/javascript">
	var base = "<%=root %>/";
</script>
<title>微信菜单管理</title>
<jsp:include page="../../static/base.jsp"></jsp:include>
<script src="<%=root%>/static/material/js/material.min.js"></script>
<script src="<%=root%>/static/material/js/ripples.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/material/css/bootstrap-material-design.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/material/css/ripples.min.css" />
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
<base href="<%=root%>/wechat/menu/menu.jsp"/>
</head>
<body ng-controller="controller">
	<button class="btn  btn-raised btn-primary btn-block" ng-click="initMenus()">初始化菜单</button>
	<br>
	<table class="table">
		<tr>
			<td ng-repeat="it in menus.button" class="col-md-4">
				<div class="bg-primary" style="padding: 10px 10px">
					<div class="pull-left">{{it.name}} </div>
					<div class="pull-right">
						<button class="btn  btn-raised btn-danger btn-xs" ng-click="deleteButton(menus.button,$index)"><i class="fa fa-trash" ></i></button>
						<button class="btn  btn-raised btn-info btn-xs" ng-click="openUpdateMenuWin(it)"><i class="fa fa-pencil-square-o"></i></button>
					</div>
					<div class="clearfix"></div>
				</div>
				<div class="bg-info">
					<ul class="list-group">
						<li ng-repeat="sub in it.subButton" class="list-group-item">
							<div class="pull-left">
								<h4>{{sub.name}}<small class="hidden">{{sub.url}}</small></h4>
							</div>
							<div class="pull-right">
								<button class="btn  btn-raised btn-danger btn-xs" ng-click="deleteButton(it.subButton,$index)"><i class="fa fa-trash" ></i></button>
								<button class="btn  btn-raised btn-info btn-xs" ng-click="openUpdateMenuWin(sub)" ><i class="fa fa-pencil-square-o"></i></button>
							</div>
							<div class="clearfix"></div>
						</li>
						<li class="list-group-item" ng-if="it.subButton.length < 5">
							<button ng-click="openAddSubMenuWindow(it)" class="btn  btn-raised btn-warning btn-smbtn  btn-raised -block"><i class="fa fa-plus"></i></button>
						</li>
					</ul>
				</div>
			</td>
			<td ng-if="menus.button.length < 3">
				<button ng-click="openAddMenuWin()" class="btn  btn-raised btn-primary btn-sm btn-block"><i class="fa fa-plus"></i></button>
			</td>
		</tr>
	</table>
	<br />
	<button class="btn  btn-raised btn-success btn-block" ng-click="saveMenus()">保存菜单</button>
	<div ng-if="showAddSubMenuWin">
		<div class="modal show">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" ng-click="hideAddSubMenuWin()">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">添加子按钮</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="form-group">
								<label class="control-label">名称：</label> <input type="text"
									class="form-control" ng-model="addSubBtn.name">
							</div>
							<div class="form-group">
								<label class="control-label">链接地址：</label> <input type="text"
									class="form-control" ng-model="addSubBtn.url">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn  btn-raised btn-success"
							ng-click="addSubButton(sub)">添加</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-backdrop fade in"></div>
	</div>

	<div ng-if="showAddMenuWin">
		<div class="modal show">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							ng-click="hideAddMenuWin()">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">添加一级按钮</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="form-group">
								<label class="control-label">名称：</label> <input type="text"
									class="form-control" ng-model="addBtn.name">
							</div>
							<div class="form-group">
								<label class="control-label">链接地址：</label> <input type="text"
									class="form-control" ng-model="addBtn.url">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn  btn-raised btn-success"
							ng-click="addButton(sub)">添加</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-backdrop fade in"></div>
	</div>


	<div ng-if="showUpdateMenuWin">
		<div class="modal show">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close"
							ng-click="hideUpdateMenuWin()">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">添加一级按钮</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="form-group">
								<label class="control-label">名称：</label> <input type="text"
									class="form-control" ng-model="updateMenu.name">
							</div>
							<div class="form-group" ng-hide="updateMenu.subButton.length > 0">
								<label class="control-label">链接地址：</label> <input type="text"
									class="form-control" ng-model="updateMenu.url">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-raised btn-success"
							ng-click="hideUpdateMenuWin(sub)">更新</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal-backdrop fade in"></div>
	</div>
</body>
</html>