
/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', 'dialog', function($scope, service, advancedService, dialog){

	$scope.data = {
		mask : true,
		param   : {
			schoolYear : null,
			termCode   : null,
			edu   : null,
			param : null
		},
		model : {
			xnxq : null
		},
		group : { mask : false },
		gpaCourse : { mask : false },
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
			mask   : true,
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
	 * { schoolYear:'', termCode:'', edu:'', param:'' }
	 */
	var getParam = function(){
		var obj = $scope.data.param;
		obj.param = getAdvancedParam();
		return obj;
	}
	// 设置学年学期值
	var setValueXnxq = function(ids){
		var $data = $scope.data, param = $data.param,
			idAry = ids.split(',');
		param.schoolYear = idAry[0],
		param.termCode   = idAry[1];
		$data.model.xnxq = ids;
	}
	// 设置学历值
	var setValueEdu = function(id){
		$scope.data.param.edu = id;
	}
	
	
	/**
	 * 标准代码
	 */
	service.getBzdm(function(data){
		var $data = $scope.data;
		$data.bzdm_xnxq = data.xnxq;
		$data.bzdm_edu  = data.edu;
		setValueXnxq($data.bzdm_xnxq[0].id);
		setValueEdu($data.bzdm_edu[0].id);
	});
	$scope.changeXnxq = function(value, data){
		setValueXnxq(value);
		refreshData();
	}
	$scope.changeEdu = function(value, data){
		setValueEdu(value);
		refreshData();
	}
	
	/**
	 * 概况信息
	 */
	var getAbstract = function(){
		showLoading();
		service.getAbstract(getParam(), function(data){
			hideLoading();
			$scope.data.abstract_data = data;
		});
	}
	/**
	 * 绩点分组
	 */
	var getGroup = function(){
		var group = $scope.data.group
		group.mask = true;
		service.getGroup(getParam(), function(data){
			group.mask = false;
			Ice.apply($scope.data.group, data);
		});
	}
	/**
	 * 绩点比例
	 */
	var getScale = function(){
		service.getScale(getParam(), function(data){
			Ice.apply($scope.data.group, data);
		});
	}
	/**
	 * 不同 课程属性、课程性质分组的绩点类型数据
	 */
	var getGpaCourse = function(){
		var gpaCourse = $scope.data.gpaCourse;
		var param = getParam();
		param.type = gpaCourse.type;
		gpaCourse.mask = true;
		service.getGpaCourse(param, function(list, bzdm){
			gpaCourse.mask = false;
			gpaCourse.list = list;
			if(bzdm){
				gpaCourse.bzdm = bzdm;
				gpaCourse.type = (bzdm&&bzdm[0]) ? bzdm[0].id : '';
			}
		});
	}
	$scope.changeScoreType = function(value, data){
		$scope.data.gpaCourse.type = value;
		getGpaCourse();
	}
	/**
	 * 表格标签
	 */
	var getDisplayedLevelType = function(){
		var param = getParam();
		service.getDisplayedLevelType(param, function(tabs){
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
		getAbstract();
		getGroup();
		getScale();
		getGpaCourse();
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
		var param = advancedService.getParam(data);
		// 高级查询-参数
		setAdvancedParam(param);
		refreshData();
	};
	
}]);