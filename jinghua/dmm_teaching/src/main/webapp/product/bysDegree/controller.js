var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','dialog', function($scope,service,advancedService,dialog) {
		
        $scope.data= {
        		mask:false,
        		size:5,
        		fyShow:true,
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
    	$scope.vm1 = {
				page : {
					total : 0,
					size : $scope.data.size,
					index : 1,
					total:0
				},
				items : [],
				count : 0
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
		service.getAdvance(function(data){
			$scope.data.advance.source = data;
		});	
		service.getTimeList().then(function(data){
			$scope.data.timeList = data;
			if(data.length >0){
			$scope.data.schoolYear = data[0].id;
			}
			getThList();
		});
		/** 参数 */
		var stuDetailParam = function(){
			var detail = $scope.data.stu_detail,
				param  = getParam();
			detail.values['schoolYear'] = $scope.data.schoolYear;
			Ice.apply(param, {values:getJson(detail.values)});
			return param;
		}
		var getThList = function(){
			service.getThList().then(function(data){
				$scope.data.thList = data.th;
				var list = data.th;
				$scope.data.tabLx = list[0].id;
				$scope.data.tabMc = list[0].mc;
				$scope.data.fthList = data.fth;
				$scope.data.order = 'bylv';
				$scope.data.asc = false;
				var list2 = data.select;
				$scope.data.chartList = list2;
				$scope.data.chartLx = list2[0].id;
				var list3 = data.type;
				$scope.data.typeList = list3;
				$scope.data.type =list3[0].id; 
				init();
			});
		}
		var getTopData = function(){
			var schoolYear = $scope.data.schoolYear;
			service.getTopData(getAdvancedParam(),schoolYear,function(data){
			 $scope.topData = data;
			});
		};
		var getTableData = function(){
			var schoolYear = $scope.data.schoolYear;
			var Lx = $scope.data.tabLx;
			var order = $scope.data.order;
			var asc = $scope.data.asc;
			var pagesize = $scope.vm1.page.size;
			var index = $scope.vm1.page.index;
			service.getTableData(getAdvancedParam(),schoolYear,Lx,order,asc,pagesize,index,function(data){
				$scope.data.tableList = data.list;
				 $scope.vm1.page.total =data.total;
				 $scope.vm1.pagecount = data.total==0 ? 0 : (data.total%pagesize==0 ? data.total/pagesize : (parseInt(data.total/pagesize)+1))
			});
		};
		var getLvByDept = function(){
			showLoading();
			var schoolYear = $scope.data.schoolYear;
			service.getLvByDept(getAdvancedParam(),schoolYear,function(data){
				var distribute = $scope.data.distribute;
				distribute.barDeptCfg = data.barDeptCfg;
				$scope.data.deptName = data.name;
				Ice.apply(data.barDeptCfg, {config:{on:['CLICK', lineChartDetail]}});
			    hideLoading();
			});
		};
		var getBysScore = function(){
			var schoolYear = $scope.data.schoolYear;
			var Lx = $scope.data.chartLx;
			var type = $scope.data.type;
			service.getBysScore(getAdvancedParam(),schoolYear,Lx,type,function(data){
				var distribute = $scope.data.distribute;
				distribute.barScoreCfg = data.barScoreCfg;
			});
		};
		var getBySyLvList = function(){
			var Lx =   $scope.tableLx;
			var detail = $scope.data.stu_detail;
			service.getBySyLvList(stuDetailParam(),Lx,function(data){
				var distribute = $scope.data.distribute;
				distribute.lineCfg = data.lineCfg;
				detail.detail_show = true;
			});
		};
		var getBysGpa = function(){
			var schoolYear = $scope.data.schoolYear;
			var type = $scope.data.type;
			service.getBysGpa(getAdvancedParam(),schoolYear,type,function(data){
				var distribute = $scope.data.distribute;
				distribute.barGpaCfg = data.barGpaCfg;
			});
		};
		var lineChartDetail = function(param, obj, data){
			var detail = $scope.data.stu_detail;
			$scope.tableLx  = param.seriesName;
			detail.values = {};
			detail.values['dept'] = obj.code||null;
			detail.values[obj.data.code] = "1"||null;
			param.data = obj.data;
			getBySyLvList();
		};
		var init = function(){
			$scope.vm1.page.index = 1;
			getTopData();
			getTableData();
			getLvByDept();
			getBysScore();
			getBysGpa();
		} 
		$scope.timeSelect = function(id){
			$scope.data.schoolYear= id;
			init();
		}
		$scope.thSelect = function(id,mc){
			$scope.data.tabLx = id;
			$scope.data.tabMc = mc;
			$scope.vm1.page.index = 1;
			getTableData();
		}
		$scope.fthSelect = function(order,asc){
			$scope.data.order = order;
			$scope.data.asc = asc;
			$scope.vm1.page.index = 1;
			getTableData();
		}
		$scope.chartSelect = function(id){
			$scope.data.chartLx = id;
			getBysScore();
		}
		$scope.typeSelect = function(id){
			$scope.data.type = id;
			getBysScore();
			getBysGpa();
		}
		 $scope.loadAll = function(){
			 $scope.data.fyShow = false;
			 $scope.vm1.page.size = $scope.vm1.page.total;
			 $scope.vm1.page.index = 1;
			 getTableData();
		 }
		 $scope.packUp = function(){
			 $scope.data.fyShow = true;
			 $scope.vm1.page.index = 1;
			 $scope.vm1.page.size = $scope.data.size;
			 getTableData();
		 }
		$scope.$watch("vm1.page.index",function(newVal,oldVal){
			if(newVal != oldVal){
				 $scope.vm1.page.index = newVal;
				 getTableData();
			}
		},true);
		/** 概况信息 点击事件 */
		$scope.stuListClick = function(type,type1,title, seniorCode ,seniorCode1){
			var detail = $scope.data.stu_detail;
		    $scope.tableLx = title;
			detail.values= {};
			detail.values[type] = seniorCode||null;
			detail.values[type1] = seniorCode1||null;
			getBySyLvList();
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