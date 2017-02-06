
/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', 'dialog', function($scope, service, advancedService, dialog){

	$scope.data = {
		mask : true,
		target_name : '平均数',
		target_unit : '',
		gridParam : {
			tab_id : null,
			tab_mc : null,
			order  : null,
			asc    : null,
			index  : 1,
			pageCount : 1
		},
		grid : {
			header : [],
			mask   : false,
			show   : function(){ this.mask=true; },
			hide   : function(){ this.mask = false; }
		},
		advance : {
			param : null
		}
	};
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	var showLoading = function(){ dialog.showLoading(); };
	var hideLoading = function(){ dialog.hideLoading(); };
	/**
	 * { param:'', target_name:'', target_unit:'' }
	 */
	var getParam = function(){
		return {
			param : getAdvancedParam(),
			target_name : $scope.data.target_name,
			target_unit : $scope.data.target_unit
		};
	}
			
	/**
	 * 历年成绩
	 */
	var getHistoryYear = function(){
		showLoading();
		service.getHistoryYear(getParam(), function(data){
			hideLoading();
			$scope.data.history_year = data;
		});
	}
	/**
	 * 性别分组
	 */
	var getSex = function(){
		service.getSex(getParam(), function(data){
			$scope.data.sex = data;
		});
	}
	/**
	 * 性别分组
	 */
	var getGrade = function(){
		service.getGrade(getParam(), function(data){
			$scope.data.grade = data;
		});
	}
	
	/**
	 * 表格标签
	 */
	var getDisplayedLevelType = function(){
		service.getDisplayedLevelType(function(tabs){
			// 设置默认显示第一个
			if(tabs && tabs.length > 0){
				tabs[0].show = true;
			}
			$scope.data.tabs = tabs;
			formatTabParam();
		});
	}
	getDisplayedLevelType();
	$scope.changeTab = function(id, tab){
		var tabs = $scope.data.tabs;
		for(var i=0,len=tabs.length; i<len; i++){
			tabs[i].show = false;
		}
		tab.show = true;
		formatTabParam();
		$scope.data.gridParam.index = 1;
		getGridList();
	}
	// 格式化 tab 参数
	var formatTabParam = function(){
		var tabs = $scope.data.tabs, gridParam = $scope.data.gridParam;
		for(var i=0,len=tabs.length; i<len; i++){
			if(tabs[i].show){
				gridParam.tab_id = tabs[i].id;
				gridParam.tab_mc = tabs[i].mc;
			}
		}
	}
	/**
	 * 列表数据
	 */
	var getGridList = function(){
		tabChangeWhich();
		var gridParam = $scope.data.gridParam, 
			param     = getParam();
		Ice.apply(gridParam, param);
		$scope.data.grid.show();
		service.getGridList(gridParam, function(data){
			$scope.data.grid.hide();
			var grid = $scope.data.grid;
			grid.list = [];
			Ice.apply(grid, data);
			gridParam.pageCount = grid.pageCount;
		});
	}
	// 处理 tab的选中情况；高级查询条件选中一个院系时，tab(按院系则不能显示数据，则要按专业显示)
	var tabChangeWhich = function(){
		// TODO
	}
	// 排序
	$scope.order = function(data, asc){
		var header    = $scope.data.grid.header,
			gridParam = $scope.data.gridParam;
		for(var i=0,len=header.length; i<len; i++){
			header[i].asc = null;
		}
		data.asc = asc;
		gridParam.order = data.id;
		gridParam.asc   = data.asc;
		gridParam.index = 1; // 设置为第一页
		getGridList();
	}
	// 表格翻页
	$scope.clickPage = function(index_){
		var gridParam = $scope.data.gridParam,
			index2    = gridParam.index+index_;
		// 如果超出可计算范围，不再取数据
		if(index2 < 1 || index2 > gridParam.pageCount)
			return;
		gridParam.index = index2;
		getGridList();
	}
	
	
	var refreshData = function(){
		getHistoryYear();
		getSex();
		getGrade();
		getGridList();
	}
	refreshData();
	
	
	service.getAdvance(function(data){
		advancedService.checkedFirst(data);
		$scope.data.advance.source = data;
	});
	/**
	 * 高级查询-切换show
	 */
	$scope.advanceShow = function(){
		$scope.data.advance.show = !$scope.data.advance.show;
	};
	/**
	 * 高级查询-change事件
	 */
	$scope.advanceChange = function(data){
		// 高级查询-参数
		var param     = advancedService.getParam(data),
			targetObj = advancedService.get(data, 'SCORE_TARGET');
		$scope.data.target_unit = targetObj==null ? '' : targetObj.unit;
		$scope.data.target_name = targetObj==null ? '' : targetObj.mc;
		setAdvancedParam(param);
		refreshData();
	};
	
	
}]);