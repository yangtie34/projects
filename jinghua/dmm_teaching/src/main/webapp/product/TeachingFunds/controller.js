/**
 * 教学经费
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', '$timeout','dialog', function($scope, service, advancedService, $timeout,dialog){	
	$scope.data={
	    mask: true,
	    dataName   : null,
	    start_year : null,
	    value_year : null,
	    value_sty  : null,
	    thisYear   : null,//当前年
		lastYear   : null,//当前年的去年
		nowData    : {},
		lastData   : {},
		distribute : {},
		history : {},
		param :{},
		SelectDownShow:true,
		styshow : true, //经费
		lineshow : true, //趋势
		countshow : true,// 数量
		zbshow : true,// 占比
		sjshow : true,// 数量
		collegeshow:true,
		advance : {
			param : null
		}
	};
	var showLoading = function(){ dialog.showLoading(); };
	var hideLoading = function(){ dialog.hideLoading(); };
	//显示年
	var formatDateName = function(){
		var $data = $scope.data;
		if($data.value_year == $data.thisYear){
			$data.dateName = '今年';
		}else if($data.value_year == $data.lastYear){
			$data.dateName = '去年';
		}
	}
	//所有年
	service.getTeaFundsYear().then(function(data){
		var sty=[{id:'1',mc:'按比例'},{id:'2',mc:'按金额'}];
		$scope.data.sty=sty;
		$scope.data.value_sty=sty[0].id;
		var date=new Date();
		var year=date.getFullYear();
		$scope.data.bzdm_xn=data.year;
		$scope.data.thisYear =year;
		$scope.data.lastYear =year-1;
		if(data.year.length==0){
			$scope.data.value_year =year
			$scope.data.start_year=year;
			$scope.data.SelectDownShow=false;
		}else{
			$scope.data.value_year =data.year[0].id;
			$scope.data.start_year=data.year[0].id;
			for(var i=0;i<data.year.length;i++){
				if(data.year[i].id==year){
					$scope.data.start_year=year;
					$scope.data.thisBtnShow=true;
				}
				if(data.year[i].id==year-1){
					$scope.data.lastBtnShow=true;
				}
			}
		}
		formatDateName();
		refalshData();
	});
	//摘要
	var getTeaFundsByAll =function(){
		service.getTeaFundsByAll($scope.data.start_year,function(data) {
			var nowData = $scope.data.nowData;
			nowData.count = data.value;
			nowData.avg=data.avg;
		});	
	};
	var getDataByLastYear =function(){
		service.getDataByLastYear($scope.data.start_year,function(data) {
			var lastData = $scope.data.lastData;
			lastData.count = data.value;
			lastData.avg=data.avg;
		});	
	};
	
	
	//概況
	var getTeaFundsBySty =function(){
		service.getTeaFundsBySty($scope.data.start_year,function(data) {
			$scope.data.distribute.fundsCfg = data.fundsCfg;
		});	
	};
	
	//5年趋势
	var getTeaFundsLine =function(){
		service.getTeaFundsLine($scope.data.start_year,function(data) {
			var history = $scope.data.history;
			history.fundsLineCfg = data.fundsLineCfg;
		});	
	};
	
	// 金额
	var getTeaFundsByCount=function(){
		service.getTeaFundsByCount($scope.data.start_year,function(data) {
			var distribute = $scope.data.distribute;
			distribute.fundsCountCfg = data.fundsCountCfg;
		});
	}
	
	// 生均
	var getTeaFundsAvg=function(){
		service.getTeaFundsAvg($scope.data.start_year,function(data) {
			hideLoading();
			$scope.chartCfg = data;
		});
	}
	
	//各学院经费信息
	var getTeaFundsByDept=function(){
		service.getTeaFundsByDept($scope.data.start_year,function(data) {
			var distribute = $scope.data.distribute;
			distribute.fundsBarCfg = data.fundsBarCfg;
			distribute.title=data.title;
		});
	}
	
	// 通过占比
	var getTeaFundsByZB=function(){
		service.getTeaFundsByZB($scope.data.start_year,function(data) {
			var distribute = $scope.data.distribute;
			distribute.fundsZBCfg = data.fundsZBCfg;
		});
	}
	//清除默认年
	var clearYearSelectedFn = function(){
		var $data = $scope.data;
		$data.value_year = null;
	};
	
	//按方式查看
	$scope.changeSty=function(id, data){
		var $data=$scope.data;
		$data.value_sty=id;
	}
	$scope.changXn = function(id, data){
		var $data = $scope.data;
		clearYearSelectedFn();
		$data.dateName = data ? data.mc : '';
		$data.value_sty=1;
		$data.value_year = id;
		$data.start_year= id;
		formatDateName();
		refalshData();
	}
	var refalshData=function(){
		showLoading();
		getTeaFundsLine();
		getTeaFundsBySty();
		getTeaFundsByCount();
		getTeaFundsByZB();
		getTeaFundsByAll();
		getDataByLastYear();
		getTeaFundsByDept();
		getTeaFundsAvg();

	}
}]);