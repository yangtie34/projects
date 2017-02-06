var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','dialog', function($scope,service,advancedService,dialog) {
		
        $scope.data= {
        		mask:false,
        		distribute : {},
        		advance : {
    				param : null
    			},
        		stu_detail : { 
    				title  : null,
    				values : {},
    				fields : [],
    				headers: [],
    				list   : null,
    				mask   : false
    			}	
        };
    	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
		var setAdvancedParam = function(param){ $scope.data.advance.param = param; }	
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
			    values = getJson(detail.values);
			Ice.apply(param, {values:values});
			return param;
		}
		service.getAdvance(function(data){
			$scope.data.advance.source = data;
		});	
		service.getEduList(function(data){$scope.edulist = data;if(data.length >0){$scope.edu = data[0].id;}});
		service.getPeriodList(function(data){$scope.periodlist = data;if(data.length >0){$scope.period = data[0].id;}});
		service.getDateList(function(data){$scope.datelist = data;if(data.length >0){$scope.date = data[0].id;}});
		var getXzList = function(){
		    var edu = $scope.edu;
		    service.getXzList(getAdvancedParam(),edu,function(data){
		    	$scope.xzlist = data;
		    	if(data.length >0){$scope.xz = data[0].id;}
		    });
		};
		getXzList();
		service.getTargetList(function(data){
			$scope.targetlist = data;
			$scope.target = data[0].id;
		});
		service.getScoreTypeList(function(data){
			$scope.scoreTypelist = data;
			$scope.scoreType = data[0].id;
		});
		service.getScoreGroup(function(data){
			$scope.scoreGroup = data;
		});
		var getScoreLine = function(){
			showLoading();
			 var edu = $scope.edu,date =$scope.date,xz =$scope.xz,
			      scoreType = $scope.scoreType == null?"gpa":$scope.scoreType,
			    target = $scope.target==null?"avg":$scope.target;
			service.getScoreLine(getAdvancedParam(),edu,date,xz,scoreType,target,function(data){
				var distribute = $scope.data.distribute;
				distribute.lineCfg = data.lineCfg;
				hideLoading();
			});
		};
		getScoreLine();
		var getScoreFb = function(){
			 var edu = $scope.edu,period =$scope.period,xz =$scope.xz;
			service.getScoreFb(getAdvancedParam(),period,xz,edu,function(data){
				var distribute = $scope.data.distribute;
				distribute.fbCfg = data.fbCfg;
			});
		};
		getScoreFb();
		$scope.eduSelect= function(edu){
			$scope.edu = edu;
			$scope.xz = null;
			getXzList();
			getScoreLine();
			getScoreFb();
		};
        $scope.xzSelect= function(xz){
        	$scope.xz = xz;
			getScoreLine();
			getScoreFb();
		}; 
		$scope.dateSelect= function(date){
			$scope.date = date;
			getScoreLine();
		}; 
		$scope.scoreTypeSelect= function(scoreType){
			$scope.scoreType = scoreType;
			getScoreLine();
		};
        $scope.targetSelect= function(target){
        	$scope.target = target;
			getScoreLine();
		}; 
		$scope.periodSelect= function(period){
			$scope.period = period;
			getScoreFb();	
		}; 
		$scope.advanceChange = function(data){
			var param = advancedService.getParam(data);
			// 高级查询-参数
			setAdvancedParam(param);
			getXzList();
			getScoreLine();
			getScoreFb();
		};	
		$scope.advanceShow = function(){
			$scope.data.advance.show = !$scope.data.advance.show;
		};
}]);