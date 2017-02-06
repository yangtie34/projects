var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','dialog', function($scope,service,advancedService,dialog) {
		
        $scope.data= {
        		mask:false,
        		size:5,
        		fyShow:true,
        		distribute : {},
        		advance : {
    				param : null
    			}
        };
    	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
		var setAdvancedParam = function(param){ $scope.data.advance.param = param; }	
		var showLoading = function(){dialog.showLoading();};
		var hideLoading = function(){dialog.hideLoading();};
		var getJson = function(obj){
			return JSON.stringify(obj);
		};
		service.getAdvance(function(data){
			$scope.data.advance.source = data;
		});	
		service.getXnXqList().then(function(data){
			$scope.data.xnxqList =data;
			var id = data[0].id;
			var ary = id.split(",");
			$scope.data.xnxq = id;
			$scope.data.schoolYear = ary[0];
			$scope.data.termCode  = ary[1];
			init();
		});
		service.getTargetList().then(function(data){
			$scope.data.tagList =data;
			var id = data[0].id;
			$scope.data.tag = id;
		});
//		var getCourseTypeList = function(){
//			var schoolYear = $scope.data.schoolYear;
//			var termCode = $scope.data.termCode;
//			service.getCourseTypeList(schoolYear,termCode,getAdvancedParam()).then(function(data){
//				$scope.data.typeList =data;
//				$scope.data.type = data[0].id;
//			});
//		};
//		var getCourseCategoryList = function(){
//			var schoolYear = $scope.data.schoolYear;
//			var termCode = $scope.data.termCode;
//			service.getCourseCategoryList(schoolYear,termCode,getAdvancedParam()).then(function(data){
//				$scope.data.categoryList =data;
//				$scope.data.category = data[0].id;
//			});
//		};
		var getCourseAttrList = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			service.getCourseAttrList(schoolYear,termCode,getAdvancedParam()).then(function(data){
				$scope.data.attrList =data;
				$scope.data.attr = data[0].id;
			});
		};
		var getCourseNatureList = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			service.getCourseNatureList(schoolYear,termCode,getAdvancedParam()).then(function(data){
				$scope.data.natureList =data;
				$scope.data.nature = data[0].id;
			});
		};
		var getCourseList = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			var type = $scope.data.type;
			var category = $scope.data.category;
			var attr = $scope.data.attr;
			var nature = $scope.data.nature;
			service.getCourseList(schoolYear,termCode,type,
					category,attr,nature,getAdvancedParam()).then(function(data){
				$scope.data.courseList =data;
				$scope.data.course = data[0].id;
			});
		};
		var getScoreByOriginList = function(){
			showLoading();
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			var type = $scope.data.type;
			var category = $scope.data.category;
			var attr = $scope.data.attr;
			var nature = $scope.data.nature;
			var course = $scope.data.course;
			var tag = $scope.data.tag;
			service.getScoreByOriginList(schoolYear,termCode,type,
					category,attr,nature,course,tag,getAdvancedParam(),function(data){
				var distribute = $scope.data.distribute;
				distribute.originCfg = data.originCfg;
				$scope.data.originId = data.id;
				$scope.data.originMc = data.mc;
				Ice.apply(data.originCfg, {config:{on:['CLICK', originClick]}});
				getScoreFbByOrigin();
				hideLoading();
			});
		};
		var getScoreByNationList = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			var type = $scope.data.type;
			var category = $scope.data.category;
			var attr = $scope.data.attr;
			var nature = $scope.data.nature;
			var course = $scope.data.course;
			var tag = $scope.data.tag;
			service.getScoreByNationList(schoolYear,termCode,type,
					category,attr,nature,course,tag,getAdvancedParam(),function(data){
				var distribute = $scope.data.distribute;
				distribute.nationCfg = data.nationCfg;
				$scope.data.nationId = data.id;
				$scope.data.nationMc = data.mc;
				Ice.apply(data.nationCfg, {config:{on:['CLICK', nationClick]}});
				getScoreFbByNation();
			});
		};
		var getScoreByZzmmList = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			var type = $scope.data.type;
			var category = $scope.data.category;
			var attr = $scope.data.attr;
			var nature = $scope.data.nature;
			var course = $scope.data.course;
			var tag = $scope.data.tag;
			service.getScoreByZzmmList(schoolYear,termCode,type,
					category,attr,nature,course,tag,getAdvancedParam(),function(data){
				var distribute = $scope.data.distribute;
				distribute.zzmmCfg = data.zzmmCfg;
				$scope.data.zzmmId = data.id;
				$scope.data.zzmmMc = data.mc;
				Ice.apply(data.zzmmCfg, {config:{on:['CLICK', zzmmClick]}});
				getScoreFbByZzmm();
			});
		};
		var getScoreFbByOrigin = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			var type = $scope.data.type;
			var category = $scope.data.category;
			var attr = $scope.data.attr;
			var nature = $scope.data.nature;
			var course = $scope.data.course;
			var originId = $scope.data.originId;
			service.getScoreFbByOrigin(schoolYear,termCode,type,
					category,attr,nature,course,originId,getAdvancedParam(),function(data){
				var distribute = $scope.data.distribute;
				distribute.originPieCfg = data.originPieCfg;
			});
		};
		var getScoreFbByNation = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			var type = $scope.data.type;
			var category = $scope.data.category;
			var attr = $scope.data.attr;
			var nature = $scope.data.nature;
			var course = $scope.data.course;
			var nationId = $scope.data.nationId;
			service.getScoreFbByNation(schoolYear,termCode,type,
					category,attr,nature,course,nationId,getAdvancedParam(),function(data){
				var distribute = $scope.data.distribute;
				distribute.nationPieCfg = data.nationPieCfg;
			});
		};
		var getScoreFbByZzmm = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.termCode;
			var type = $scope.data.type;
			var category = $scope.data.category;
			var attr = $scope.data.attr;
			var nature = $scope.data.nature;
			var course = $scope.data.course;
			var zzmmId = $scope.data.zzmmId;
			service.getScoreFbByZzmm(schoolYear,termCode,type,
					category,attr,nature,course,zzmmId,getAdvancedParam(),function(data){
				var distribute = $scope.data.distribute;
				distribute.zzmmPieCfg = data.zzmmPieCfg;
			});
		};
		var init = function(){
//			getCourseTypeList();
//			getCourseCategoryList();
			getCourseAttrList();
			getCourseNatureList();
			getCourseList();
			initChart();
			
		};
		var initChart = function(){
			getScoreByOriginList();
			getScoreByNationList();
			getScoreByZzmmList();
        };
        var originClick = function(param, obj, data){
        	$scope.data.originId= obj.code;
        	$scope.data.originMc = param.name;
        	getScoreFbByOrigin();
        };
        var nationClick = function(param, obj, data){
        	$scope.data.nationId= obj.code;
        	$scope.data.nationMc = param.name;
        	getScoreFbByNation();
        };
        var zzmmClick = function(param, obj, data){
        	$scope.data.zzmmId= obj.code;
        	$scope.data.zzmmMc = param.name;
        	getScoreFbByZzmm();
        };
		$scope.xnxqSelect = function(id){
			$scope.data.xnxq = id;
			var ary = id.split(",");
			$scope.data.schoolYear = ary[0];
			$scope.data.termCode = ary[1];
			init();
		};
		$scope.tagSelect = function(id){
			$scope.data.tag = id;
			initChart();
		};
		$scope.typeSelect = function(id){
			$scope.data.type = id;
			initChart();
		};
		$scope.categorySelect = function(id){
			$scope.data.category = id;
			initChart();
		};
		$scope.attrSelect = function(id){
			$scope.data.attr = id;
			initChart();
		};
		$scope.natureSelect = function(id){
			$scope.data.nature = id;
			initChart();
		};
		$scope.courseSelect = function(id){
			$scope.data.course = id;
			initChart();
		};
		$scope.advanceChange = function(data){
			var param = advancedService.getParam(data);
			// 高级查询-参数
			setAdvancedParam(param);
			init();
		};	
		$scope.advanceShow = function(){
			$scope.data.advance.show = !$scope.data.advance.show;
		};
}]);