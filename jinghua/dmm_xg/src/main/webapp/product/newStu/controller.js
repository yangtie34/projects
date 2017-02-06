/**
 * 新生基本概况
 */
var app = angular.module('app', ['ngRoute','ngAnimate','system']).controller(
		"controller",
		['$scope', 'service', 'advancedService','$timeout','dialog',
				function($scope, service, advancedService,$timeout,dialog) {

					$scope.data = {
						mask : true,	
						distribute : {},
						history : {},
						from : [],
						talval : [],
						detailUrl : "pmsNewStu/getStuDetail",
						exportUrl : 'pmsNewStu/down',
						show1 : true,
						show2 : false,
						show3 : false,
						advance : {
							param : null
						},
						stu_detail : { 
//							page : {
//								curpage  : 1,
//								pagesize : 10,
//								sumcount : null
//							},
//							title  : null,
							values : {},
//							fields : [],
//							headers: [],
//							list   : null,
//							mask   : false
						}
					};
					$scope.headers = ['学号','入学年级','姓名','性别','地区','院系','专业','班级'];
					$scope.fields = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc'];
					var showLoading = function(){dialog.showLoading();};
					var hideLoading = function(){dialog.hideLoading();};
					/**
					 * 获取分页参数
					 */
					var getJson = function(obj){
						return JSON.stringify(obj);
					}
					var getParam = function(){
						var obj = Ice.apply({},$scope.data.param);
						obj.param = getAdvancedParam();
						return obj;
					}
					/** 参数 */
					var stuDetailParam = function(){
						var detail = $scope.data.stu_detail,
							param  = getParam(),
							values = getJson(detail.values),
//							page   = getJson(detail.page),
							fields = getJson(detail.fields),
							headers= getJson(detail.headers);
						Ice.apply(param, {values:values, fields:fields, headers:headers});
						return param;
					};
					$scope.view = {
						current : '1'
					};
					$scope.viewClick = function(value) {
						$scope.view.current = value;
					}
					var getAdvancedParam = function() {
						return $scope.data.advance.show
								? $scope.data.advance.param
								: null;
					}
					var setAdvancedParam = function(param) {
						$scope.data.advance.param = param;
					}

					var poorLine = function() {
						var year = $scope.poorYear;
						var yAxis = $scope.poorData;
						service.getPoorCount(getAdvancedParam(), year, yAxis,
								function(data) {
									var distribute = $scope.data.distribute;
									distribute.poorCountCfg = data.poorCountCfg;
									distribute.poorPieCfg = data.poorPieCfg;
									Ice.apply(data.poorPieCfg, {config:{on:['CLICK', poorDetail]}});
									$scope.data.poor = data.result;
									$scope.data.pooryear = data.year;
								});
					};
					var distributionDetail = function(param, type){
						var detail = $scope.data.stu_detail;
//						detail.page.curpage = 1;
						detail.values[type] = param.data.code;
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
//						getStuDetail();
					};
//					var getStuDetail = function() {
//						showLoading();
//						var detail = $scope.data.stu_detail;
////						var pagesize = $scope.data.stu_detail.page.pagesize;
////						var index = $scope.data.stu_detail.page.curpage;
//						service.getStuDetail(stuDetailParam(),function(data){
//								    detail.list = data.items;
////									detail.page.sumcount = data.total;
////									detail.page.pagecount = data.pagecount;
//									detail.mask = false;
//									detail.detail_show = true;
//									hideLoading();
//								},hideLoading);
//					};
					var isReDetail = function(param, obj, data){
						var detail = $scope.data.stu_detail;
						detail.title  = "已报到（"+param.name+"）学生名单";
						detail.values = {};
						detail.values["register"] = 1;
						detail.values["schoolyear"] = 1;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						param.data = obj;
						distributionDetail(param,'edu');
					};
					var noReDetail = function(param, obj, data){
						var detail = $scope.data.stu_detail;
						detail.title  = "未报到（"+param.name+"）学生名单";
						detail.values = {};
						detail.values["register"] = 0;
						detail.values["schoolyear"] = 1;	
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						param.data = obj;
						distributionDetail(param,'edu');
					};
					var countDetail = function(param, obj, data){
						var detail = $scope.data.stu_detail;
						detail.title  = param.name+"年（已报到）学生名单";
						detail.values = {};
						detail.values["register"] = 1;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						param.data = obj;
						distributionDetail(param,'schoolyear');
					};
					var deptDetail = function(param, obj, data){
						var detail = $scope.data.stu_detail;
						detail.title  = "近五年 "+param.name+"（已报道）学生名单";
						detail.values = {};
						detail.values["register"] = 1;
						detail.values["ENROLL_GRADE"] = 1;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						param.data = obj;
						distributionDetail(param,'dept');
					};
					var loanDetail = function(param, obj, data){
						var detail = $scope.data.stu_detail;
						detail.title  = "获助学贷款（户口性质："+param.name+"）学生名单";
						detail.values = {};
						detail.values["register"] = 1;
						detail.values["year"] =$scope.loanYear==null?$scope.data.loanyear:$scope.loanYear;
						detail.values["loan"] =1;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						param.data = obj;
						distributionDetail(param,'hklx');
					};
					var poorDetail = function(param, obj, data){
						var detail = $scope.data.stu_detail;
						detail.title  = "贫困生（户口性质："+param.name+"）名单";
						detail.values = {};
						detail.values["register"] = 1;
						detail.values["year"] =$scope.poorYear==null?$scope.data.pooryear:$scope.poorYear;
						detail.values["poor"] =1;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						param.data = obj;
						distributionDetail(param,'hklx');
					};
					var jmDetail = function(param, obj, data){
						var detail = $scope.data.stu_detail;
						detail.title  = "获学费减免（户口性质："+param.name+"）学生名单";
						detail.values = {};
						detail.values["register"] = 1;
						detail.values["year"] =$scope.jmYear==null?$scope.data.jmyear:$scope.jmYear;
						detail.values["jm"] =1;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						param.data = obj;
						distributionDetail(param,'hklx');
					};
					var loanLine = function() {
						var year = $scope.loanYear;
						var yAxis = $scope.loanData;
						service.getLoanCount(getAdvancedParam(), year, yAxis,
								function(data) {
									var distribute = $scope.data.distribute;
									distribute.loanCountCfg = data.loanCountCfg;
									distribute.loanPieCfg = data.loanPieCfg;
									$scope.data.loan = data.result;
									$scope.data.loanyear = data.year;
									Ice.apply(data.loanPieCfg, {config:{on:['CLICK', loanDetail]}});
								});
					};
					var jmLine = function() {
						var year = $scope.jmYear;
						var yAxis = $scope.jmData;
						service.getJmCount(getAdvancedParam(), year, yAxis,
								function(data) {
									var distribute = $scope.data.distribute;
									distribute.jmCountCfg = data.jmCountCfg;
									distribute.jmPieCfg = data.jmPieCfg;
									$scope.data.jmyear = data.year;
									Ice.apply(data.jmPieCfg, {config:{on:['CLICK', jmDetail]}});
									$scope.data.jm = data.result;
								});
					};
					var refreshData = function() {
						showLoading();
						service.getIsRegisterCount(getAdvancedParam(),
								function(data) {
									var distribute = $scope.data.distribute;
									distribute.isRegisterCfg = data.isRegisterCfg;
									distribute.notRegisterCfg = data.notRegisterCfg;
									Ice.apply(data.isRegisterCfg, {config:{on:['CLICK', isReDetail]}});
									Ice.apply(data.notRegisterCfg, {config:{on:['CLICK', noReDetail]}});
									$scope.data.yes = data.yes;
									$scope.data.no = data.no;
									$scope.data.isrete = data.isrete;
									$scope.data.notrete = data.notrete;
									$scope.data.ylist = data.ylist;
									$scope.data.nlist = data.nlist;
									$scope.data.grade = data.year;
								});
						service.getCountAndLv(getAdvancedParam(),
								function(data) {
									var distribute = $scope.data.distribute;
									distribute.countAndLvCfg = data.countAndLvCfg;
									Ice.apply(data.countAndLvCfg, {config:{on:['CLICK', countDetail]}});
								});
						service.getDeptAvgLv(getAdvancedParam(),
								function(data) {
									var distribute = $scope.data.distribute;
									distribute.deptAvgLvCfg = data.deptAvgLvCfg;
									$scope.data.levelName = data.name;
									Ice.apply(data.deptAvgLvCfg, {config:{on:['CLICK', deptDetail]}});
									hideLoading();
						        });
						$scope.poorYear = null;
						$scope.poorData = null;
						$scope.loanYear = null;
						$scope.loanData = null;
						$scope.jmYear = null;
						$scope.jmData = null;
						poorLine();
						loanLine();
						jmLine();
					}
					refreshData();
					$scope.poorClick = function(param) {
						$scope.poorYear = param.name.toString();
						$scope.poorData = param.data;
						poorLine();
					};
					$scope.loanClick = function(param) {
						$scope.loanYear = param.name.toString();
						$scope.loanData = param.data;
						loanLine();
					};
					$scope.jmClick = function(param) {
						$scope.jmYear = param.name.toString();
						$scope.jmData = param.data;
						jmLine();
					};
					/** 概况信息 点击事件 */
					$scope.stuDetailClick = function(type, title, seniorCode){
						var detail = $scope.data.stu_detail;
//						detail.page.curpage = 1;
						detail.title = title;
						detail.values= {};
						detail.values['schoolyear'] = 1;
						detail.values[type] = seniorCode;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						detail.formConfig = {
								title : title,
								show  : true,
								url       : $scope.data.detailUrl,
								exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
								heads  : detail.headers,
								fields : detail.fields,
								params : stuDetailParam()
							};
						$timeout(function(){})
					};
//					$scope.$watch("data.stu_detail.page.curpage",function(newVal,oldVal){
//						if(newVal != oldVal){
//							$scope.data.stu_detail.mask = true;
//							$scope.data.stu_detail.page.curpage = newVal;
//							getStuDetail();
//						}
//					},true);
					/**
					 * 高级查询-change事件
					 */
					$scope.advanceChange = function(data) {
						var param = advancedService.getParam(data);
						// 高级查询-参数
						setAdvancedParam(param);
						refreshData();
					};
					service.getAdvance(function(data) {
								$scope.data.advance.source = data;
							});
//					$scope.stuDetailDown= function(){
//						var param = stuDetailParam();
//						param.fileName = $scope.data.stu_detail.title;
//						service.getStuDetailDown(param,function(data){
//							
//						})
//					};
				}]);
app.filter("jdz",function(){
	 return function(input){
         return Math.abs(input);
     }
});