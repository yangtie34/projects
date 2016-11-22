var app = angular.module('app', ['system'])
.controller("controller",['$scope','wechatMenuService','dialog','toastrService','$interval',function(scope,menu,dialog,toastr,$interval){
	$interval(function() {$.material.init(); }, 1000);
//	查询菜单 
	dialog.showLoading();
	menu.getMenus().then(function(data){
		scope.menus = data;
		dialog.hideLoading();
	});
//保存菜单	
	scope.saveMenus = function(){
		dialog.showLoading()
		menu.saveMenus(scope.menus)
		.then(function(data){
			if(data.success){
				 menu.getMenus().then(function(dt){
					scope.menus = dt;
					dialog.hideLoading();
					toastr.success("保存成功");
				 });
			}else{
				dialog.hideLoading();
				toastr.error("保存失败("+data.errmsg+")");
			}
		});
	}
//	添加菜单窗口
	scope.addBtn = {
		type:'view',
		subButton : []
	};
	
	scope.showAddMenuWin = false;
	scope.openAddMenuWin = function(){
		scope.showAddMenuWin = true;
	}
	
	scope.hideAddMenuWin = function(){
		scope.addBtn = {
			type:'view' ,
			subButton : []
		};
		scope.showAddMenuWin = false;
	}
	
	scope.addButton = function(){
		var btnInsert = {};
		angular.copy(scope.addBtn,btnInsert);
		scope.menus.button.push(btnInsert);
		scope.addBtn = {
			type:'view' ,
			subButton : []
		};
		scope.showAddMenuWin = false;
	}
//	添加子菜单窗口
	scope.showAddSubMenuWin = false;
	
	scope.openAddSubMenuWindow = function(sub){
		scope.addSubBtn = {
			type:'view' 
		};
		scope.mainButtonTemp = sub;
		scope.showAddSubMenuWin = true;
	}
	
	scope.hideAddSubMenuWin = function(){
		scope.mainButtonTemp = null;
		scope.addSubBtn = {
			type:'view' 
		};
		scope.showAddSubMenuWin = false;
	}
	
	scope.addSubButton = function(){
		var btnInsert = {};
		angular.copy(scope.addSubBtn,btnInsert);
		scope.mainButtonTemp.subButton.push(btnInsert);
		scope.addSubBtn = {
			type:'view' 
		};
		scope.showAddSubMenuWin = false;
	}
//	删除菜单
	scope.deleteButton = function(list,index){
		list.splice(index,1);
	}
//	更新菜单
	scope.showUpdateMenuWin = false;
	scope.openUpdateMenuWin = function(it){
		scope.showUpdateMenuWin = true;
		scope.updateMenu = it;
	}
	scope.hideUpdateMenuWin = function(it){
		scope.updateMenu = {};
		scope.showUpdateMenuWin = false;
	}
	
	scope.initMenus = function(){
		dialog.showLoading();
		menu.initMenus().then(function(data){
			if(data.success){
				toastr.success("初始化成功！");
				menu.getMenus().then(function(dt){
					scope.menus = dt;
					dialog.hideLoading();
				 });
			}else{
				toastr.error("初始化失败！");
			}
		});
	}
}]);