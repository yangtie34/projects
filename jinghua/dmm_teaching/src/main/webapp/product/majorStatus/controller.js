
/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', function($scope, service, advancedService){

	$scope.data = {
		mask  : false,
		param : {
			schoolYear : null,
			param : null
		},
		model : {
			xn   : null, // 选择学年
			xn_show : false,
			do_score         : true, // 启用成绩
			do_failScale     : true, // 启用挂科
			do_evaluateTeach : true, // 启用评教
			do_byJy          : true  // 启用毕业就业
		},
		bzdm_xn : [],
		bzdm_year : [{id:'3',mc:'近三年'},{id:'5',mc:'近五年'},{id:'10',mc:'近十年'}],
		grid_score : {
			header : [],
			mask   : false,
			show   : function(){ this.mask = true; },
			hide   : function(){ this.mask = false; },
			list : [],
			page : {
				curpage  : 1,
				pagesize : 10,
				pagecount: null,
				sumcount : null,
				order  : null,
				asc    : null,
			},
			his_one : { show : false }
		},
		grid_failScale : {
			mask   : false,
			show   : function(){ this.mask = true; },
			hide   : function(){ this.mask = false; },
			page : {
				curpage  : 1,
				pagesize : 10,
				pagecount: null,
				sumcount : null,
				order  : null,
				asc    : null,
			},
			his_one : { show : false }
		},
		grid_evaluateTeach : {
			mask   : false,
			show   : function(){ this.mask = true; },
			hide   : function(){ this.mask = false; },
			page : {
				curpage  : 1,
				pagesize : 10,
				pagecount: null,
				sumcount : null,
				order  : null,
				asc    : null,
			},
			his_one : { show : false }
		},
		grid_byJy : {
			mask   : false,
			show   : function(){ this.mask = true; },
			hide   : function(){ this.mask = false; },
			page : {
				curpage  : 1,
				pagesize : 10,
				pagecount: null,
				sumcount : null,
				order  : null,
				asc    : null,
			},
			by : { show : false },
			jy : { show : false }
		},
		grid_search : {
			model : {
				year  : 3, // 检索近几年
				score : 20,
				failScale : 20, 
				evaluateTeach : 20, 
				by : 20, 
				jy : 20
			},
			list : null
		},
		advance : { param : null }
	};
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	var showLoading = function(){ $scope.data.mask = true; };
	var hideLoading = function(){ $scope.data.mask = false; };
	/**
	 * object 转 Json
	 */
	var object2Json = function(obj){ return JSON.stringify(obj); }
	/**
	 * { schoolYear:'', termCode:'', edu:'', param:'' }
	 */
	var getParamObj = function(){
		var obj = $scope.data.param;
		obj.param = getAdvancedParam();
		return obj;
	}
	// 设置学年学期值
	var setValueXn = function(ids){
		var $data = $scope.data, param = $data.param;
		$data.model.xn = ids;
		param.schoolYear = ids;
	}
	
	/**
	 * 标准代码
	 */
	service.queryXn(function(data){
		var $data = $scope.data;
		$data.bzdm_xn = data.xn;
		setValueXn($data.bzdm_xn[0].id);
	});
	$scope.changeXn = function(value, data){
		setValueXn(value);
		refreshData();
	}
	
	/**
	 * 专业成绩
	 */
	var queryMajorScoreList = function(){
		if(!$scope.data.model.do_score) return;
		var param = getParamObj(),
			$grid = $scope.data.grid_score;;
		param.page = object2Json($grid.page);
		Ice.apply(param, $scope.data.param);
		$grid.show();
		service.queryMajorScoreList(param, function(list, bzdm_attr, pagecount, sumcount){
			$grid.list = list;
			$grid.bzdm_attr = bzdm_attr;
			$grid.page.pagecount = pagecount;
			$grid.page.sumcount = sumcount;
			$grid.hide();
		})
	}
	/** 翻页 */
	$scope.$watch("data.grid_score.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.grid_score.page.curpage = newVal;
			queryMajorScoreList();
		}
	},true);
	/**
	 * 专业挂科率
	 */
	var queryMajorFailScaleList = function(){
		if(!$scope.data.model.do_failScale) return;
		var param = getParamObj(),
			$grid = $scope.data.grid_failScale;
		param.page = object2Json($grid.page);
		Ice.apply(param, $scope.data.param);
		$grid.show();
		service.queryMajorFailScaleList(param, function(list, bzdm_attr, pagecount, sumcount){
			$grid.list = list;
			$grid.bzdm_attr = bzdm_attr;
			$grid.page.pagecount = pagecount;
			$grid.page.sumcount = sumcount;
			$grid.hide();
		})
	}
	/** 翻页 */
	$scope.$watch("data.grid_failScale.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.grid_failScale.page.curpage = newVal;
			queryMajorFailScaleList();
		}
	},true);
	/**
	 * 专业评教率
	 */
	var queryMajorEvaluateTeachList = function(){
		if(!$scope.data.model.do_evaluateTeach) return;
		var param = getParamObj(),
			$grid = $scope.data.grid_evaluateTeach;
		param.page = object2Json($grid.page);
		Ice.apply(param, $scope.data.param);
		$grid.show();
		service.queryMajorEvaluateTeachList(param, function(list, bzdm_attr, pagecount, sumcount){
			$grid.list = list;
			$grid.bzdm_attr = bzdm_attr;
			$grid.page.pagecount = pagecount;
			$grid.page.sumcount = sumcount;
			$grid.hide();
		})
	}
	/** 翻页 */
	$scope.$watch("data.grid_evaluateTeach.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.grid_evaluateTeach.page.curpage = newVal;
			queryMajorEvaluateTeachList();
		}
	},true);
	/**
	 * 专业毕业就业率
	 */
	var queryMajorByJyList = function(){
		if(!$scope.data.model.do_byJy) return;
		showLoading();
		var param = getParamObj(),
		$grid = $scope.data.grid_byJy;
		param.page = object2Json($grid.page);
		Ice.apply(param, $scope.data.param);
		$grid.show();
		service.queryMajorByJyList(param, function(list, bzdm_attr, pagecount, sumcount){
			$grid.list = list;
			$grid.bzdm_attr = bzdm_attr;
			$grid.page.pagecount = pagecount;
			$grid.page.sumcount = sumcount;
			$grid.hide();
			hideLoading();
		})
	}
	/** 翻页 */
	$scope.$watch("data.grid_byJy.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.grid_byJy.page.curpage = newVal;
			queryMajorByJyList();
		}
	},true);
	/**
	 * 专业自定义查询
	 */
	var getSearchParam = function(){
		var $data = $scope.data,
			model = $data.model,
			searchModel = $data.grid_search.model;
		var obj = {year : searchModel.year};
		if(model.do_score) obj.score = emptyString2null(searchModel.score);
		if(model.do_failScale) obj.failScale = emptyString2null(searchModel.failScale);
		if(model.do_evaluateTeach) obj.evaluateTeach = emptyString2null(searchModel.evaluateTeach);
		if(model.do_byJy){ obj.by = emptyString2null(searchModel.by); obj.jy = emptyString2null(searchModel.jy); }
		return obj;
	}
	/**
	 * 空字符串转null
	 */
	var emptyString2null = function(value){
		return value=='' ? null : value;
	}
	$scope.queryMajorSearchList = function(){
		showLoading();
		var param = getParamObj();
		Ice.apply(param, getSearchParam());
		service.queryMajorSearchList(param, function(list){
			$scope.data.grid_search.list = list;
			hideLoading();
		})
	}
	
	var refreshData = function(){
		queryMajorScoreList();
		queryMajorFailScaleList();
		queryMajorEvaluateTeachList();
		queryMajorByJyList();
	}
	refreshData();
	

	/**
	 * 专业成绩-详情
	 */
	$scope.queryMajorScoreHis = function(id, obj){
		var param = getParamObj();
		param.id = id;
		service.queryMajorScoreHis(param, function(option){
			var his_one = $scope.data.grid_score.his_one;
			his_one.show = true;
			his_one.option = option;
			his_one.title = obj.name+' 成绩';
		});
	}
	/**
	 * 专业挂科-详情
	 */
	$scope.queryMajorFailScaleHis = function(id, obj){
		var param = getParamObj();
		param.id = id;
		service.queryMajorFailScaleHis(param, function(option){
			var his_one = $scope.data.grid_failScale.his_one;
			his_one.show = true;
			his_one.option = option;
			his_one.title = obj.name+' 挂科率';
		});
	}
	/**
	 * 专业评教-详情
	 */
	$scope.queryMajorEvaluateTeachHis = function(id, obj){
		var param = getParamObj();
		param.id = id;
		service.queryMajorEvaluateTeachHis(param, function(option){
			var his_one = $scope.data.grid_evaluateTeach.his_one;
			his_one.show = true;
			his_one.option = option;
			his_one.title = obj.name+' 评教';
		});
	}
	/**
	 * 专业毕业-详情
	 */
	$scope.queryMajorByHis = function(id, obj){
		var param = getParamObj();
		param.id = id;
		service.queryMajorByHis(param, function(option){
			var his_one = $scope.data.grid_byJy.by;
			his_one.show = true;
			his_one.option = option;
			his_one.title = obj.name+' 毕业';
		});
	}
	/**
	 * 专业就业-详情
	 */
	$scope.queryMajorJyHis = function(id, obj){
		var param = getParamObj();
		param.id = id;
		service.queryMajorJyHis(param, function(option){
			var his_one = $scope.data.grid_byJy.jy;
			his_one.show = true;
			his_one.option = option;
			his_one.title = obj.name+' 就业';
		});
	}
	
	
	/**
	 * 高级查询
	 */
	/*service.getAdvance(function(data){
		advancedService.checkedFirst(data);
		$scope.data.advance.source = data;
	});*/
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