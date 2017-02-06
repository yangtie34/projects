var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','$timeout','dialog', function($scope,service,advancedService,$timeout,dialog) {
		
        $scope.data= {
        		mask:false,
        		distribute : {},
        		advance : {
    				param : null
    			},
    			detailUrl : "pmsBysQx/getStuList",
				exportUrl : 'pmsBysQx/down',
        		stu_detail : { 
    				values : {},
    			}	
        };
    	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
		var setAdvancedParam = function(param){ $scope.data.advance.param = param; }	
		$scope.headers = ['学号','入学年级','姓名','性别','地区','院系','专业','班级'];
		$scope.fields = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc'];
		var showLoading = function(){dialog.showLoading();};
		var hideLoading = function(){dialog.hideLoading();};
		var getJson = function(obj){
			return JSON.stringify(obj);
		};
		var getParam = function(){
			var obj = Ice.apply({},$scope.data.param);
			obj.param = getAdvancedParam();
			return obj;
		}; 
		/** 参数 */
		var stuDetailParam = function(){
			var detail = $scope.data.stu_detail,
				param  = getParam(),
				fields = getJson(detail.fields),
				headers= getJson(detail.headers);
			Ice.apply(param, { values:getJson(detail.values), fields:fields, headers:headers});
			return param;
		}
		service.getAdvance(function(data){
			$scope.data.advance.source = data;
		});	
		service.getTimeList().then(function(data){
			$scope.data.timeList = data;
			if(data.length >0){
			$scope.data.schoolYear = data[0].id;
			}
			init();
		});
		var getBysQxFb = function(){
			var schoolYear = $scope.data.schoolYear;
			service.getBysQxFb(getAdvancedParam(),schoolYear,function(data){
				var distribute = $scope.data.distribute;
				distribute.pieQxCfg = data.pieQxCfg;
				Ice.apply(data.pieQxCfg, {config:{on:['CLICK', pieQxDetail]}});
			});
		};
		var getBysQxSzFb = function(){
			var schoolYear = $scope.data.schoolYear;
			service.getBysQxSzFb(getAdvancedParam(),schoolYear,function(data){
				var distribute = $scope.data.distribute;
				distribute.pieSzCfg = data.pieSzCfg;
				Ice.apply(data.pieSzCfg, {config:{on:['CLICK', pieSzDetail]}});
			});
		};
		var getLnBysQxfb = function(){
			var schoolYear = $scope.data.schoolYear;
			service.getLnBysQxfb(getAdvancedParam(),schoolYear,function(data){
				var distribute = $scope.data.distribute;
				distribute.barQxCfg = data.barQxCfg;
			});
		};
		var getLnBysSzQxfb = function(){
			var schoolYear = $scope.data.schoolYear;
			service.getLnBysSzQxfb(getAdvancedParam(),schoolYear,function(data){
				var distribute = $scope.data.distribute;
				distribute.barSzCfg = data.barSzCfg;
			});
		};
		var getLnReasonfb = function(){
			var schoolYear = $scope.data.schoolYear;
			service.getLnReasonfb(getAdvancedParam(),schoolYear,function(data){
				var distribute = $scope.data.distribute;
				distribute.barReasonCfg = data.barReasonCfg;
				Ice.apply(data.barReasonCfg, {config:{on:['CLICK', barReasonDetail]}});
			});
		};
		var getScaleByDept = function(){
			showLoading();
			var schoolYear = $scope.data.schoolYear;
			service.getScaleByDept(getAdvancedParam(),schoolYear,function(data){
				var distribute = $scope.data.distribute;
				$scope.data.deptname= data.name;
				distribute.barDeptCfg = data.barDeptCfg;
				hideLoading();
			});
		};
		var pieQxDetail = function(param, obj, data){
			var detail = $scope.data.stu_detail;
			detail.title  =  "毕业去向（"+param.name+"）学生名单";
			detail.values = {};
			detail.values['schoolYear'] = $scope.data.schoolYear;
			detail.headers= $scope.headers;
			detail.fields = $scope.fields;
			param.data = obj;
			distributionDetail(param,'direction');
		};
		var pieSzDetail = function(param, obj, data){
			var detail = $scope.data.stu_detail;
			detail.title  = "毕业去向（"+param.name+"）学生名单";
			detail.values = {};
			detail.values['schoolYear'] = $scope.data.schoolYear;
			detail.headers= $scope.headers;
			detail.fields = $scope.fields;
			param.data = obj;
			distributionDetail(param,'direction');
		};
		var barReasonDetail = function(param, obj, data){
			var detail = $scope.data.stu_detail;
			detail.title  = "未就业原因（"+param.seriesName+"）学生名单";
			detail.values = {};
			detail.values['schoolYear'] = obj.code;
			detail.headers= $scope.headers;
			detail.fields = $scope.fields;
			param.data = obj.data;
			distributionDetail(param,'reason');
		};
		var distributionDetail = function(param, type){
			var detail = $scope.data.stu_detail;
			if(type=='direction'){
			detail.values[type] = param.data.id;
			}else{
				detail.values[type] = param.data.code;
			}
			detail.formConfig = {
					title : detail.title,
					show  : true,
					url       : $scope.data.detailUrl,
					exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
					heads  : detail.headers,
					fields : detail.fields,
					params : stuDetailParam()
				};
			$timeout(function(){})
		};
		var init = function(){
			getBysQxFb();
			getBysQxSzFb();
			getLnBysQxfb();
			getLnBysSzQxfb();
			getLnReasonfb();
			getScaleByDept();
		} 
		$scope.timeSelect = function(id){
			$scope.data.schoolYear= id;
			init();
		}
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