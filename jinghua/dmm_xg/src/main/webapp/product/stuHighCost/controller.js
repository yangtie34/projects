/**
 * 高消费分析
 */
var app = angular.module('app', [ 'ngRoute', 'system' ]).controller(
	"controller",['$scope','service','service_gdxf','advancedService','$timeout','dialog',
    function($scope, service, service_gdxf, advancedService,$timeout,dialog) {

				$scope.data = {
					mask:true,
					distribute : {},
					history : {},
					advance : {
						param : null
					},
					detailUrl : "pmsStuHighCost/getStuDetail",
					exportUrl : 'pmsStuHighCost/down',
					stu_detail : { 
//						page : {
//							curpage  : 1,
//							pagesize : 10,
//							sumcount : null
//						},
//						title  : null,
						values : {},
//						fields : [],
//						headers: [],
//						list   : null,
//						mask   : false
					},
					week_detail : { 
//						page : {
//							curpage  : 1,
//							pagesize : 10,
//							sumcount : null
//						},
//						title  : null,
						values : {},
//						fields : [],
//						headers: [],
//						list   : null,
//						mask   : false
					},
					scale_detail : { 
//						page : {
//							curpage  : 1,
//							pagesize : 10,
//							sumcount : null
//						},
//						title  : null,
						values : {},
//						fields : [],
//						headers: [],
//						list   : null,
//						mask   : false
					},
					isYear:false,
					grid : {
						header : [],
						mask   : false,
						show   : function(){ this.mask=true; },
						hide   : function(){ this.mask =false; }
					},
					gridMeal : {
						header : [],
						mask   : false,
						show   : function(){ this.mask=true; },
						hide   : function(){ this.mask=false; }
					},
				};
				$scope.gd = 300;
				$scope.tabcolumn = "count";
				$scope.tabasc = true;
				$scope.mealShow = true;
				$scope.gradeShow = true;
				$scope.loanShow = true;
				$scope.vm1 = {
						page : {
							total : 0,
							size : 4,
							index : 1
						},
						items : [],
						loading : false
			    };
				$scope.headers = ['学号','入学年级','姓名','性别','地区','院系','专业','班级','高消费标准','月总消费金额'];
				$scope.fields = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc','bz','sum_money'];
				$scope.headers1 = ['学号','入学年级','姓名','性别','地区','院系','专业','班级'];
				$scope.fields1 = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc'];
				$scope.headers2 = ['学号','入学年级','姓名','性别','地区','院系','专业','班级','高消费标准','学期总消费金额'];
				$scope.fields2 = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc','bz','sum_money'];
				var showLoading = function(){$scope.data.mask = true;$timeout(function(){});};
				var hideLoading = function(){$scope.data.mask = false;$timeout(function(){});};
				/**
				 * 获取分页参数
				 */
				var getJson = function(obj){
					return JSON.stringify(obj);
				}
				var getAdvancedParam = function() {
					return $scope.data.advance.show ? $scope.data.advance.param
							: null;
				}
				var setAdvancedParam = function(param) {
					$scope.data.advance.param = param;
				}
				var getParam = function(){
					var obj = Ice.apply({},$scope.data.param);
					obj.param = getAdvancedParam();
					return obj;
				}
				/** 参数 */
				var weekDetailParam = function(){
					var detail = $scope.data.week_detail,
						param  = getParam(),
//						page   = getJson(detail.page),
						fields = getJson(detail.fields),
						headers= getJson(detail.headers);
 						detail.values['ismonth'] = 1;
 						detail.values['schoolyear'] = $scope.data.monthYear;
 						detail.values['term'] = $scope.data.monthTerm;
 						detail.values['month'] = $scope.data.month;
					Ice.apply(param, { values:getJson(detail.values), fields:fields, headers:headers});
					return param;
				};
				/** 参数 */
				var stuDetailParam = function(){
					var detail = $scope.data.stu_detail,
						param  = getParam(),
//						page   = getJson(detail.page),
						fields = getJson(detail.fields),
						headers= getJson(detail.headers);
			/*		if($scope.data.isYear){
						detail.values['isyear'] = 1;
						var start = $scope.data.yearstart;
						var end = $scope.data.yearend;
						var schoolyear = "",year = "";
						for(var i= start;i<end+1;i++){
							schoolyear = i+"-"+(i+1);
							if(i==start){
							year = schoolyear;
							}else{
								year = year+","+schoolyear;
							}
						}
						detail.values['schoolyear'] =year;
 					}else{*/
 						var ary = $scope.data.termname;
 						var timeAry = ary.split("+");
 						detail.values['isterm'] = 1;
 						detail.values['schoolyear'] =$scope.data.schoolyear || timeAry[0] ;
						detail.values['term'] = $scope.data.termcode || timeAry[1];
				/*	}*/
					Ice.apply(param, { values:getJson(detail.values), fields:fields, headers:headers});
					return param;
				};
				service_gdxf.getDataEndDate().then(function(data) {
					$scope.data.tableDate = data.table;
					$scope.data.chartDate = data.chart;
			/*		$scope.data.yearlist = data.year;*/
					var term = data.term;
					$scope.data.termlist = term;
					if (term.length > 0) {
						$scope.data.termname = term[0].id;
					}
					var list = data.month;
					$scope.data.monthlist = list;
					if (list.length > 0) {
						$scope.data.monthname = list[0].id;
						var timeAry = $scope.data.monthname.split(",");
						$scope.data.monthYear = timeAry[0];
						$scope.data.monthTerm = timeAry[1];
						$scope.data.month = timeAry[2];
					}
					getCountByDept();
					getLastWeekData();
					getStandardMap();
				});
				var getStandardMap =function(){
					var schoolYear = $scope.data.monthYear;
					var termCode = $scope.data.monthTerm;
					service.getStandardMap(schoolYear,termCode,function(data){
						$scope.data.standard = data.value;
						$scope.data.standardMc = data.name;
						$scope.data.xflx = data.type;
					});
				};
				var getNearLv = function(id){
					var detail = $scope.data.scale_detail;
					var list =$scope.data.monthlist;
					var xnxqList = "",monthList="";
					for(var i=0;i<5;i++){
						var xxid =list[i].id;
						var timeAry = xxid.split(",");
						var xnxq = timeAry[0]+"+"+timeAry[1];
						var month = timeAry[2];
						if(i==0){
							xnxqList = xnxq;
							monthList = month;
						}else{
							xnxqList = xnxqList+","+xnxq;
							monthList = monthList+","+month;
						}
					}
					service.getNearLv(xnxqList,monthList,id,getAdvancedParam(),function(data) {
						var distribute = $scope.data.distribute;
						   distribute.scaleLineCfg = data.scaleLineCfg; 
						   distribute.changeLineCfg = data.changeLineCfg; 
						   detail.detail_show = true;
					});
				};
//				var getStuDetail = function() {
//					showLoading();
//					var detail = $scope.data.stu_detail;
////					var pagesize = $scope.data.stu_detail.page.pagesize;
////					var index = $scope.data.stu_detail.page.curpage;
//					service.getStuDetail(stuDetailParam(),function(data) {
//							    detail.list = data.items;
//								detail.page.sumcount = data.total;
//								detail.page.pagecount = data.pagecount;
//								detail.mask = false;
//								detail.detail_show = true;
//							},hideLoading());
//				};
//				var getweekDetail = function() {
//					showLoading();
//					var detail = $scope.data.week_detail;
//					service.getStuDetail(weekDetailParam(),function(data) {
//							    detail.list = data.items;
//								detail.page.sumcount = data.total;
//								detail.page.pagecount = data.pagecount;
//								detail.mask = false;
//								detail.detail_show = true;
//							},hideLoading());
//				};
				var sublistdept = function(){
						var pagesize = $scope.vm1.page.size ;
						var x = pagesize>$scope.data.deptresult.length?$scope.data.deptresult.length:pagesize;
						$scope.data.deptlist = [];
						for(var i=0;i<x;i++){
							 $scope.data.deptlist.push($scope.data.deptresult[i]);
						}
				}
				var getLastWeekData = function(){
					var schoolYear = $scope.data.monthYear;
					var termCode = $scope.data.monthTerm;
					var month = $scope.data.month;
				service.getLastMonthData(getAdvancedParam(),schoolYear,termCode,month).then(function(data){
					$scope.data.lastmonthcount = data.count;
					$scope.data.lastmonthscale = data.scale;
					$scope.data.lastmonthchange = data.change;
				});
				}
				var distributionDetail = function(param, type){
					var detail = $scope.data.stu_detail;
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
				};
				var mealDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = "高消费（"+param.name+"）明细";
					detail.values = {};
					detail.values['ismeal']=1;
					detail.headers= ['学号','入学年级','姓名','性别','地区','院系','专业','班级','高消费标准',param.name+'消费金额'];;
					detail.fields = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc','bz','sum_money'];;
					param.data = obj;
					distributionDetail(param,'meal');
				};
				var gradeDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = "高消费（"+param.name+"）学生名单";
					detail.values = {};
					detail.headers= $scope.headers2;
					detail.fields = $scope.fields2;
					param.data = obj;
					distributionDetail(param,'GRADE');
				};
				var sexDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = "高消费（性别："+param.name+"）学生名单";
					detail.values = {};
					detail.headers= $scope.headers2;
					detail.fields = $scope.fields2;
					param.data = obj;
					distributionDetail(param,'SEX_CODE');
				};
				var deptDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = "高消费（"+param.name+"）学生名单";
					detail.values = {};
					detail.headers= $scope.headers2;
					detail.fields = $scope.fields2;
					param.data = obj;
					distributionDetail(param,'DEPT_TEACH_ID');
				};
				var subsidyDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = param.name+"学生名单";
					detail.values = {};
					detail.headers= $scope.headers2;
					detail.fields = $scope.fields2;
					param.data = obj;
					distributionDetail(param,'subsidy');
				};
				var loanDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = param.name+"学生名单";
					detail.values = {};
					detail.headers= $scope.headers2;
					detail.fields = $scope.fields2;
					param.data = obj;
					distributionDetail(param,'loan');
				};
				var jmDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = param.name+"学生名单";
					detail.values = {};
					detail.headers= $scope.headers2;
					detail.fields = $scope.fields2;
					param.data = obj;
					distributionDetail(param,'jm');
				};
				var getCountByDept = function() {
					$scope.data.grid.show();
					var schoolYear = $scope.data.monthYear;
					var termCode = $scope.data.monthTerm;
					var month = $scope.data.month;
					var pagesize = $scope.vm1.page.size;
					var index = $scope.vm1.page.index;
					var column = $scope.tabcolumn;
					var asc  = $scope.tabasc;
					service.getCountByDept(schoolYear, termCode,month,column,asc,
							getAdvancedParam(),
							function(data) {
					     	var distribute = $scope.data.distribute;
//					     	if ($scope.data.deptlist==null){$scope.data.deptlist=data.list;}
//					     	else{
//					     		 $scope.data.deptlist = $.merge($scope.data.deptlist,data.list);	
//					     	}
								$scope.vm1.page.total =  data.result.length;
								$scope.data.deptresult = data.result;
//								$scope.data.deptlist = [];
//								var x = pagesize>$scope.data.deptresult.length?$scope.data.deptresult.length:pagesize;
//								for(var i=0;i<x;i++){
//									 $scope.data.deptlist.push($scope.data.deptresult[i]);
//								}
								sublistdept();
								$scope.data.grid.hide();	
							});
				}
				var getTermByMeal = function(){
					$scope.data.gridMeal.show();
					var schoolYear=$scope.data.schoolyear;
					var termCode=$scope.data.termcode; 
					service.getTermByMeal(schoolYear,termCode,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.termMealCfg = data.termMealCfg;
						   Ice.apply(data.termMealCfg, {config:{on:['CLICK', mealDetail]}});
							$scope.data.gridMeal.hide();
					});
				}
				var getTermByGrade = function(){
					var schoolYear=$scope.data.schoolyear;
					var termCode=$scope.data.termcode; 
					service.getTermByGrade(schoolYear,termCode,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.termGradeCfg = data.termGradeCfg;
						   Ice.apply(data.termGradeCfg, {config:{on:['CLICK', gradeDetail]}});
					});
				}
				var getTermBySex = function(){
					var schoolYear=$scope.data.schoolyear;
					var termCode=$scope.data.termcode; 
					service.getTermBySex(schoolYear,termCode,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.termSexCfg = data.termSexCfg;
						   Ice.apply(data.termSexCfg, {config:{on:['CLICK', sexDetail]}});
					});
				}
				var getTermByLoan = function(){
					var schoolYear=$scope.data.schoolyear;
					var termCode=$scope.data.termcode; 
					service.getTermByLoan(schoolYear,termCode,getAdvancedParam(),function(data){
//						var distribute = $scope.data.distribute;
//						   distribute.termLoanCfg = data.termLoanCfg;
						var myChart =echarts.init(document.getElementById('Xg_stuHighCost_loan'), 'macarons');
						var options = data.termLoanCfg;
						 myChart.setOption(options, true);
						 shiftOption(options,myChart,data.list,loanDetail);
						   $scope.data.loanscale = data.scale;
						   $scope.data.loancount = data.count;
					});
				}
				var getTermBySubsidy = function(){
					var schoolYear=$scope.data.schoolyear;
					var termCode=$scope.data.termcode;  
					service.getTermBySubsidy(schoolYear,termCode,getAdvancedParam(),function(data){
//						var distribute = $scope.data.distribute;
//						   distribute.termSubsidyCfg = data.termSubsidyCfg;
						var myChart =echarts.init(document.getElementById('Xg_stuHighCost_subsidy'), 'macarons');
						var options = data.termSubsidyCfg;
						 myChart.setOption(options, true);
						 shiftOption(options,myChart,data.list,subsidyDetail);
						   $scope.data.subsidyscale = data.scale;
						   $scope.data.subsidycount = data.count;
					});
				}
				var getTermByJm = function(){
					var schoolYear=$scope.data.schoolyear;
					var termCode=$scope.data.termcode; 
					service.getTermByJm(schoolYear,termCode,getAdvancedParam(),function(data){
//						var distribute = $scope.data.distribute;
//						   distribute.termJmCfg = data.termJmCfg;
						var myChart =echarts.init(document.getElementById('Xg_stuHighCost_jm'), 'macarons');
						var options = data.termJmCfg;
						 myChart.setOption(options, true);
						 shiftOption(options,myChart,data.list,jmDetail);
						   $scope.data.jmscale = data.scale;
						   $scope.data.jmcount = data.count;
					});
				}
				var getYearCountDept = function(){
					dialog.showLoading();
					var start = $scope.data.yearstart;
					var end = $scope.data.yearend; 
					service.getYearCountDept(start,end,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.yearDeptCfg = data.yearDeptCfg;
						   Ice.apply(data.yearDeptCfg, {config:{on:['CLICK', deptDetail]}});
						   dialog.hideLoading();
					});
				}
				var getTermCountDept = function(){
					dialog.showLoading();
					var schoolYear=$scope.data.schoolyear;
					var termCode=$scope.data.termcode; 
					service.getTermCountDept(schoolYear,termCode,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.termDeptCfg = data.termDeptCfg;
						   Ice.apply(data.termDeptCfg, {config:{on:['CLICK', deptDetail]}});
						   dialog.hideLoading();
					});
				}
				var getYearByMeal = function(){
					$scope.data.gridMeal.show();
					var start = $scope.data.yearstart;
					var end = $scope.data.yearend;
					service.getYearByMeal(start,end,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.yearMealCfg = data.yearMealCfg;
//						   Ice.apply(data.yearMealCfg, {config:{on:['CLICK', mealDetail]}});
							$scope.data.gridMeal.hide();
					});
				}
				var getYearByGrade = function(){
					var start = $scope.data.yearstart;
					var end = $scope.data.yearend;
					service.getYearByGrade(start,end,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.yearGradeCfg = data.yearGradeCfg;
//						   Ice.apply(data.yearGradeCfg, {config:{on:['CLICK', gradeDetail]}});  
					});
				}
				var getYearBySex = function(){
					var start = $scope.data.yearstart;
					var end = $scope.data.yearend;
					service.getYearBySex(start,end,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.yearSexCfg = data.yearSexCfg;
//						   Ice.apply(data.yearSexCfg, {config:{on:['CLICK', sexDetail]}});
					});
				}
				var getYearByLoan = function(){
					var start = $scope.data.yearstart;
					var end = $scope.data.yearend;
					service.getYearByLoan(start,end,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						   distribute.yearLoanCfg = data.yearLoanCfg;
						var myChart =echarts.init(document.getElementById('Xg_stuHighCost_loan'), 'macarons');
						var options = data.yearLoanCfg;
						 myChart.setOption(options, true);
//						 shiftOption(options,myChart,data.list,loanDetail);
						   $scope.data.loanscale = data.scale;
						   $scope.data.loancount = data.count;
					});
				}
				var getYearBySubsidy = function(){
					var start = $scope.data.yearstart;
					var end = $scope.data.yearend;
					service.getYearBySubsidy(start,end,getAdvancedParam(),function(data){
//						var distribute = $scope.data.distribute;
//					    distribute.yearSubsidyCfg = data.yearSubsidyCfg;
						var myChart =echarts.init(document.getElementById('Xg_stuHighCost_subsidy'), 'macarons');
						var options = data.yearSubsidyCfg;
						 myChart.setOption(options, true);
//						 shiftOption(options,myChart,data.list,subsidyDetail);
						$scope.data.subsidyscale = data.scale;
						$scope.data.subsidycount = data.count;
					});
				}
				var getYearByJm = function(){
					var start = $scope.data.yearstart;
					var end = $scope.data.yearend;
					service.getYearByJm(start,end,getAdvancedParam(),function(data){
//						var distribute = $scope.data.distribute;
//						   distribute.yearJmCfg = data.yearJmCfg;
						var myChart =echarts.init(document.getElementById('Xg_stuHighCost_jm'), 'macarons');
						var options = data.yearJmCfg;
						 myChart.setOption(options, true);
//						 shiftOption(options,myChart,data.list,jmDetail);
						   $scope.data.jmscale = data.scale;
						   $scope.data.jmcount = data.count;
					});
				}
				var getMealHistory = function(){
					service.getMealHistory(getAdvancedParam(),function(data){
						var history = $scope.data.history;
						history.mealHistoryCfg = data.mealHistoryCfg;
					});
				}
				var getGradeHistory = function(){
					service.getGradeHistory(getAdvancedParam(),function(data){
						var history = $scope.data.history;
						history.gradeHistoryCfg = data.gradeHistoryCfg;
					});
				}
				var getSexHistory = function(){
					service.getSexHistory(getAdvancedParam(),function(data){
						var history = $scope.data.history;
						history.sexHistoryCfg = data.sexHistoryCfg;
					});
				}
				var getLoanHistory = function(){
					service.getLoanHistory(getAdvancedParam(),function(data){
						var history = $scope.data.history;
						history.loanHistoryCfg = data.loanHistoryCfg;
					});
				}
				var getSubsidyHistory = function(){
					service.getSubsidyHistory(getAdvancedParam(),function(data){
						var history = $scope.data.history;
						history.subsidyHistoryCfg = data.subsidyHistoryCfg;
					});
				}
				var getJmHistory = function(){
					service.getJmHistory(getAdvancedParam(),function(data){
						var history = $scope.data.history;
						history.jmHistoryCfg = data.jmHistoryCfg;
					});
				}
				var getExportData = function(sendType,pid,mc,i,x){
					var schoolYear = $scope.data.monthYear;
					var termCode = $scope.data.monthTerm;
					var month = $scope.data.month;
					service.getExportData(sendType,mc,schoolYear,termCode,month,pid,getAdvancedParam()).then(function(data){
						if(sendType=="only"){
							var deptname =encodeURI($scope.data.deptresult[i].name);
							$scope.data.deptresult[i].status = data.status;
							sublistdept();
						}
						if(x){$scope.data.grid.hide();}
					})
				};
				var initTerm = function(){
					if($scope.mealShow&&!$scope.data.isYear){getTermByMeal();};
					if($scope.gradeShow&&!$scope.data.isYear){getTermByGrade();getTermBySex();};
					if($scope.loanShow&&!$scope.data.isYear){getTermBySubsidy();getTermByLoan();getTermByJm();}
					if(!$scope.data.isYear){getTermCountDept();};
				};
				initTerm();
				var initYear = function(){
					if($scope.mealShow&&$scope.data.isYear){getYearByMeal();};
					if($scope.gradeShow&&$scope.data.isYear){getYearByGrade();getYearBySex();};
					if($scope.loanShow&&$scope.data.isYear){getYearBySubsidy();getYearByLoan();getYearByJm();};
					if($scope.data.isYear){getYearCountDept();};
				};
				var initHistory = function(){
					if(!$scope.mealShow){getMealHistory();};
					if(!$scope.gradeShow){getGradeHistory();getSexHistory();};
					if(!$scope.loanShow){getLoanHistory();getSubsidyHistory();getJmHistory();};
				};
				$scope.monthSelect = function(id, value) {
					$scope.data.monthname = id;
					var timeAry = id.split(",");
					$scope.data.monthYear = timeAry[0];
					$scope.data.monthTerm = timeAry[1];
					$scope.data.month = timeAry[2];
					$scope.vm1.page.size = 4;
					getCountByDept();
					getLastWeekData();
					getStandardMap();
				};
				$scope.termSelect = function(id, value) {
					$scope.data.termname = id;
					$scope.data.yearstart = null;
					$scope.data.yearend = null;
					var timeAry = id.split("+");
					$scope.data.schoolyear = timeAry[0];
					$scope.data.termcode = timeAry[1];
		/*			$scope.data.isYear = false;*/
					initTerm();initHistory();
				};
				/*$scope.yearSelect = function(start,end) {
					$scope.data.yearstart = start;
					$scope.data.yearend = end;
					$scope.data.schoolyear = null;
					$scope.data.termcode = null;
					$scope.data.isYear = true;
					initYear();initHistory();
				};*/
				$scope.PageUp1 = function() {
					$scope.vm1.page.size = $scope.vm1.page.total;
					sublistdept();
//					getCountByDept();
				};
				$scope.PageDown1 = function() {
					$scope.vm1.page.size= 4
					sublistdept();
//					$scope.vm1.page.index=1;
//					$scope.data.deptlist = null;
//					getCountByDept();
				};
				/**
				 * 高级查询-change事件
				 */
				$scope.advanceChange = function(data) {
					var param = advancedService.getParam(data);
					// 高级查询-参数
					setAdvancedParam(param);
//					if($scope.data.isYear){
//						initYear();
//					}else{
						initTerm();
//					}
					$scope.vm1.page.size = 4;
					sublistdept();
					getCountByDept();
					initHistory();
				};
				service.getAdvance(function(data) {
					$scope.data.advance.source = data;
				});
				$scope.mealSwich=function(){
					if($scope.mealShow==false){
					$scope.mealShow = true;
					if($scope.data.isYear ==true){
						getYearByMeal();
					}else{
						getTermByMeal();
					}
					}else{
						$scope.mealShow= false;
						getMealHistory();
					}
				};
//				$scope.$watch("data.stu_detail.page.curpage",function(newVal,oldVal){
//					if(newVal != oldVal){
//						$scope.data.stu_detail.mask = true;
//						$scope.data.stu_detail.page.curpage = newVal;
//						getStuDetail();
//					}
//				},true);
//				$scope.$watch("data.week_detail.page.curpage",function(newVal,oldVal){
//					if(newVal != oldVal){
//						$scope.data.week_detail.mask = true;
//						$scope.data.week_detail.page.curpage = newVal;
//						getweekDetail();
//					}
//				},true);
				$scope.gradeSwich=function(){
					if($scope.gradeShow==false){
					$scope.gradeShow = true;
					if($scope.data.isYear ==true){
						getYearByGrade();
						getYearBySex();
					}else{
						getTermByGrade();
						getTermBySex();
					}
					}else{
						$scope.gradeShow= false;
						getGradeHistory();getSexHistory();	
					}
				};
				$scope.loanSwich=function(){
					if($scope.loanShow==false){
					   $scope.loanShow = true;
						if($scope.data.isYear ==true){
							getYearByLoan();
							getYearBySubsidy();
							getYearByJm();
						}else{
							getTermByLoan();
							getTermBySubsidy();
							getTermByJm();
						}
					}else{
						$scope.loanShow= false;
						   getLoanHistory();
						   getSubsidyHistory();
						   getJmHistory();
					}
				};
				$scope.tabSort = function(column,asc){
					$scope.tabcolumn = column;
					$scope.tabasc = asc;
					$scope.data.deptlist = null;
					$scope.vm1.page.index = 1;
					getCountByDept();
				};
				$scope.loanClick = function(type, title, seniorCode){
					var detail = $scope.data.stu_detail;
//					detail.page.curpage = 1;
					detail.title = title;
					detail.values= {};
					detail.values[type] = seniorCode||null;
					detail.headers= $scope.headers2;
					detail.fields = $scope.fields2;	
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
				$scope.weekDetailClick = function(type, title, seniorCode){
					var detail = $scope.data.week_detail;
//					detail.page.curpage = 1;
					detail.title = title;
					detail.values= {};
					if(type!='lastmonth'){
					detail.values['DEPT_TEACH_ID'] = seniorCode||null;
					}
					if(type == 'all'){
					detail.headers= $scope.headers1;
					detail.fields = $scope.fields1;
					detail.values['all'] = 1;
					}else{
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;	
					}
					detail.formConfig = {
							title : detail.title,
							show  : true,
							url       : $scope.data.detailUrl,
							exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
							heads  : detail.headers,
							fields : detail.fields,
							params : weekDetailParam()
						};
					$timeout(function(){})
				};
				/**
				 * 单击导出按钮
				 */
				$scope.exportExcel=function(i){
					var pid=$scope.data.deptlist[i].id;//得到当前院系的id
					var map =$scope.data.deptlist[i];
					var schoolYear = $scope.data.monthYear;
					var termCode = $scope.data.monthTerm;
					var month =$scope.data.month;
					var mc=map.name+":高消费人数"+map.count+"人，占比"+map.scale+"%, "+schoolYear+"学年"+termCode+"学期 第"+month+"月。";
					var param = getAdvancedParam();
					service.exportData(mc,schoolYear,termCode,month,pid,param,function(data){
						
					})
				};
				/**
				 * 单击发送邮件按钮
				 */
				$scope.send=function(sendType,i,deptname,deptid,x){
					if(x){
					$scope.data.grid.show();
					}
					var pid=$scope.data.deptresult[i].id;//得到当前院系的id
					var map =$scope.data.deptresult[i];
					var schoolYear = $scope.data.monthYear;
					var termCode = $scope.data.monthTerm;
					var month =$scope.data.month;
					var mc=map.name+":高消费人数"+map.count+"人，占比"+map.scale+"%,"+schoolYear+"学年"+termCode+"学期 第"+month+"月。";
					var sb =  encodeURI(mc);
					getExportData(sendType,pid,sb,i,x);
				};
				/**
				 * 单击全部导出按钮
				 */
				$scope.exportAll=function(){
					for(var i=0;i<$scope.data.deptresult.length;i++){
						$scope.send('',i,$scope.data.deptresult[i].name,$scope.data.deptresult[i].id,false);//生成所有的excel文件
					}
					window.open("stuHighCost/excelAll");
				};
				/**
				 * 单击全部发送按钮
				 */
				$scope.sendAll=function(){
					$scope.data.grid.show();
					for(var i=0;i<$scope.data.deptresult.length;i++){
						if( i == $scope.data.deptresult.length-1){
							$scope.send('only',i,$scope.data.deptresult[i].name,$scope.data.deptresult[i].id,true);//生成所有的excel文件
						}
						$scope.send('only',i,$scope.data.deptresult[i].name,$scope.data.deptresult[i].id,false);//生成所有的excel文件
					}
//					service.sendAll(function(data){//生成压缩包并发送
//						alert(data.fh);
//					});
				};
//				$scope.stuDetailDown= function(){
//					var param = stuDetailParam();
//					param.fileName = $scope.data.stu_detail.title;
//					service.getStuDetailDown(param,function(data){
//						
//					})
//				};
//				$scope.weekDetailDown= function(){
//					var param = weekDetailParam();
//					param.fileName = $scope.data.week_detail.title;
//					service.getStuDetailDown(param,function(data){
//						
//					})
//				};
				$scope.weekLvClick = function(type,id,name){
					var detail = $scope.data.scale_detail;
					if(type=='scale'){
						$scope.scaleshow = true;
						$scope.changeshow = false;
						detail.title = name+"高消费人数占比变化趋势";
					}else{
						$scope.scaleshow = false;
						$scope.changeshow = true;
						detail.title = name+"高消费人数变化趋势";
					}
					getNearLv(id);
				}
				var shiftOption = function(option,myChart,data,fn){
					require.config({ 
        				paths: { 'echarts': './static/echarts/2' }
                    });
                    require(['echarts' ]);
                    var ecConfig = require('echarts/config');
                    var zrEvent = require('zrender/tool/event');
                    myChart.on(ecConfig.EVENT.CLICK, function(param){
                    	var obj = param.data, seriesIndex = param.seriesIndex, dataIndex = param.dataIndex;
                    	if(data instanceof Array && data.length > dataIndex){
        					obj = data[dataIndex];
                    	}
                    	fn(param,obj,data);
                    })
				}
			}]);
app.filter("jdz",function(){
	 return function(input){
        return Math.abs(input);
    }
});