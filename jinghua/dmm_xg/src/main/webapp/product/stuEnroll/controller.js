/**
 * 在籍生基本概况
 */
var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','$timeout','dialog', function($scope, service,advancedService,$timeout,dialog) {

					$scope.data = {
					    mask: true,
						distribute : {},
						history : {},
						param :{},
						fromshow : true,
						stateshow : true,
						nationshow : true,
						detailUrl : "pmsStuEnroll/getStuDetail",
						exportUrl : 'pmsStuEnroll/down',
						sexshow : true,
						ageshow : true,
						politicsshow : true,
						deptshow : true,
						styleshow : true,
						formshow : true,
						schwidth : '',
						camwidth : '',
						eduwidth : '',
						grawidth : '',
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
						},
						history_detail : { 
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
						},
					};
					$scope.headers = ['学号','入学年级','姓名','性别','地区','院系','专业','班级'];
					$scope.fields = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc'];
				var showLoading = function(){$scope.data.mask = true;$timeout(function(){});};
				var hideLoading = function(){$scope.data.mask = false;$timeout(function(){});};
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
//							page   = getJson(detail.page),
							fields = getJson(detail.fields),
							headers= getJson(detail.headers);
						    detail.values['STU_ROLL_CODE'] = 1;
						    detail.values['schoolyear'] = $scope.data.schoolyear;
						Ice.apply(param, {values:getJson(detail.values), fields:fields, headers:headers});
						return param;
					};
					/** 参数 */
					var historyDetailParam = function(){
						var detail = $scope.data.history_detail,
							param  = getParam(),
							values = getJson(detail.values);
//							page   = getJson(detail.page),
							fields = getJson(detail.fields),
							headers= getJson(detail.headers);
						Ice.apply(param, { values:values, fields:fields, headers:headers});
						return param;
					};
				    var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	                var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	            	var getStuFromLine = function(){
						service.getStuFromLine(getAdvancedParam(), function(data) {
							var history = $scope.data.history;
							history.fromLineCfg = data.fromLineCfg;
							Ice.apply(data.fromLineCfg, {config:{on:['CLICK', fromHistoryDetail]}});
						});
						};	
					var getStuSexLine = function(){
						service.getStuSexLine(getAdvancedParam(), function(data) {
							var history = $scope.data.history;
							history.sexLineCfg = data.sexLineCfg;
							Ice.apply(data.sexLineCfg, {config:{on:['CLICK', sexHistoryDetail]}});
						});
					};
					var getStuCountLine = function(){
						service.getStuCountLine(getAdvancedParam(), function(data) {
							var history = $scope.data.history;
							history.stateLineCfg = data.stateLineCfg;
							Ice.apply(data.stateLineCfg, {config:{on:['CLICK', stuHistoryDetail]}});
						});
					};
					var getStuNationLine = function(){
						service.getStuNationLine(getAdvancedParam(), function(data) {
							var history = $scope.data.history;
							history.nationLineCfg = data.nationLineCfg;
							Ice.apply(data.nationLineCfg, {config:{on:['CLICK', nationHistoryDetail]}});
						});
					};
					var getStuAgeLine =function(){
						service.getStuAgeLine(getAdvancedParam(), function(data) {
							var history = $scope.data.history;
							history.ageLineCfg = data.ageLineCfg;
						});	
					};
					var getPoliticsLine = function(){
						service.getPoliticsLine(getAdvancedParam(), function(data) {
							var history = $scope.data.history;
							history.politicsLineCfg = data.politicsLineCfg;
							Ice.apply(data.politicsLineCfg, {config:{on:['CLICK', politicsHistoryDetail]}});
						});
					};
					var getDeptLine = function(){
						service.getDeptLine(getAdvancedParam(), function(data) {
							var history = $scope.data.history;
							history.deptLineCfg = data.deptLineCfg;
							$scope.levelName = data.name;
							Ice.apply(data.deptLineCfg, {config:{on:['CLICK', deptHistoryDetail]}});
						    dialog.hideLoading();
						});
					};
					var getStyleLine = function(){
						service.getStyleLine(getAdvancedParam(), function(data) {
									var history = $scope.data.history;
									history.styleLineCfg = data.styleLineCfg;
									Ice.apply(data.styleLineCfg, {config:{on:['CLICK', styleHistoryDetail]}});
								});
					};	
					var getFormLine = function(){
						service.getFormLine(getAdvancedParam(), function(data) {
									var history = $scope.data.history;
									history.formLineCfg = data.formLineCfg;
									Ice.apply(data.formLineCfg, {config:{on:['CLICK', formHistoryDetail]}});
								});
					};
//					var getStuDetail = function() {
//                        showLoading();
//						var detail = $scope.data.stu_detail;
////						var pagesize = $scope.data.stu_detail.page.pagesize;
////						var index = $scope.data.stu_detail.page.curpage;
//						service.getStuDetail(stuDetailParam(),function(data) {
//								    detail.list = data.items;
//									detail.page.sumcount = data.total;
//									detail.page.pagecount = data.pagecount;
//									detail.mask = false;
//									detail.detail_show = true;
//								},hideLoading());
//					};
//					var getHistoryDetail = function() {
//				         showLoading();
//						var detail = $scope.data.history_detail;
////						var pagesize = $scope.data.stu_detail.page.pagesize;
////						var index = $scope.data.stu_detail.page.curpage;
//						service.getStuDetail(historyDetailParam(),function(data) {
//								    detail.list = data.items;
//									detail.page.sumcount = data.total;
//									detail.page.pagecount = data.pagecount;
//									detail.mask = false;
//									detail.detail_show = true;
//								},hideLoading());
//					};
				var distributionDetail = function(param, type){
						var detail = $scope.data.stu_detail;
//						detail.page.curpage = 1;
						if(type == 'from'){
							detail.values[type] = param;
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
				var historyDetail = function(param,type){
					var detail = $scope.data.history_detail;
//					detail.page.curpage = 1;
					detail.values[type] = param.data.code;
					detail.formConfig = {
							title : detail.title,
							show  : true,
							url       : $scope.data.detailUrl,
							exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
							heads  : detail.headers,
							fields : detail.fields,
							params : historyDetailParam()
						};
					$timeout(function(){})
				};
				var sexDetail = function(param, obj, data){
						var detail = $scope.data.stu_detail;
						detail.title  = "性别（"+param.name+"）学生名单";
						detail.values = {};
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						param.data = obj;
						distributionDetail(param,'sex');
					};
				var statusDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = "学生状态（"+param.name+"）学生名单";
					detail.values = {};
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj;
					distributionDetail(param,'status');
				};
				var styleDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = param.seriesName+"（"+param.name+"）学生名单";
					detail.values = {};
					var type = "";
					if(param.seriesName =='学习方式'){
					type = "style";
					}else{
					type = "form";	
					}
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj;
					distributionDetail(param,type);
				};
				var politicsDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = "政治面貌（"+param.name+"），学历（"+param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['edu'] = obj.data.code;
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj;
					distributionDetail(param,'politics_bar');
				};
				var fromDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = "生源地（"+param+"）学生名单";
					detail.values = {};
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					distributionDetail(param,'from');
				};
				var ageDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = param.name+"（"+param.seriesName+"级）学生名单";
					detail.values = {};
					detail.values['ENROLL_GRADE'] = param.seriesName;
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj;
					distributionDetail(param,'age');
				};
				var nationDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = "民族（"+param.name+"）学生名单";
					detail.values = {};
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj;
					distributionDetail(param,'nation_bar');
				};
				var deptDetail = function(param, obj, data){
					var detail = $scope.data.stu_detail;
					detail.title  = param.name+"（"+ param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['edu'] = obj.data.code;
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj;
					distributionDetail(param,'dept');
				};
				var sexHistoryDetail = function(param, obj, data){
					var detail = $scope.data.history_detail;
					detail.title  = param.name+"学年性别（"+param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['schoolyear'] = parseInt(param.name);
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj.data;
					historyDetail(param,'sex');
				};
				var politicsHistoryDetail = function(param, obj, data){
					var detail = $scope.data.history_detail;
					detail.title  = param.name+"学年政治面貌（"+param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['schoolyear'] = parseInt(param.name);
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj.data;
					historyDetail(param,'politics_bar');
				};
				var nationHistoryDetail = function(param, obj, data){
					var detail = $scope.data.history_detail;
					detail.title  = param.name+"学年民族（"+param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['schoolyear'] = parseInt(param.name);
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj.data;
					historyDetail(param,'nation');
				};
				var stuHistoryDetail = function(param, obj, data){
					var detail = $scope.data.history_detail;
					detail.title  = param.name+"学年学生名单";
					detail.values = {};
					detail.values['schoolyear'] = parseInt(param.name);
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj;
					historyDetail(param,'');
				};
				var fromHistoryDetail = function(param, obj, data){
					var detail = $scope.data.history_detail;
					detail.title  = param.name+"学年（"+param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['schoolyear'] = parseInt(param.name);
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj.data;
					historyDetail(param,'fromline');
				};
				var deptHistoryDetail = function(param, obj, data){
					var detail = $scope.data.history_detail;
					detail.title  = param.name+"学年（"+param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['schoolyear'] = parseInt(param.name);
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj.data;
					historyDetail(param,'dept');
				};
				var styleHistoryDetail = function(param, obj, data){
					var detail = $scope.data.history_detail;
					detail.title  = param.name+"学年学习方式（"+param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['schoolyear'] = parseInt(param.name);
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj.data;
					historyDetail(param,type);
				};
				var formHistoryDetail = function(param, obj, data){
					var detail = $scope.data.history_detail;
					detail.title  = param.name+"学年学习形式（"+param.seriesName+"）学生名单";
					detail.values = {};
					detail.values['schoolyear'] = parseInt(param.name);
					detail.headers= $scope.headers;
					detail.fields = $scope.fields;
					param.data = obj.data;
					historyDetail(param,type);
				};
	                // tab
				var refreshData = function(){
					dialog.showLoading();
	                service.getStuCountByDept(getAdvancedParam()).then(function(data) {
								$scope.data.dept = data.name;
								$scope.data.deptcount = data.value;
								$scope.data.schoolyear = data.schoolyear;
								var campus = data.campus;
								$scope.data.edu = data.edu;
								$scope.data.grade = data.grade;
								$scope.data.campus = campus;
								var val = $scope.data.campus.length;
								if (val > 1) {
									$scope.data.schwidth = '17.8%';
									$scope.data.camwidth = '24%';
									$scope.data.eduwidth = '26%';
									$scope.data.grawidth = '29.9%';
								} else {
									$scope.data.schwidth = '25%';
									$scope.data.eduwidth = '34.5%';
									$scope.data.grawidth = '39%';
								}
								service.getGraduateStuCount(getAdvancedParam()).then(function(data) {
							        $scope.data.gtaCount = data.value;
							        $scope.data.gtaId = data.id;
							        $scope.data.gtaStyle = data.style;
							        $scope.data.gtaDxjy = data.dxjy;
							        $scope.data.gtaGrade = data.grade;
						         });
								service.getBsCount(getAdvancedParam()).then(function(data) {
							        $scope.data.bsId = data.id;
							        $scope.data.bsCount = data.value;
					                $scope.data.bsGrade = data.grade;
				                 });
							});
					service.getStuCountBySex(getAdvancedParam(), function(data) {
								var distribute = $scope.data.distribute;
								distribute.sexCfg = data.sexCfg;
								Ice.apply(data.sexCfg, {config:{on:['CLICK', sexDetail]}});
								distribute.styleCfg = data.styleCfg;
								Ice.apply(data.styleCfg, {config:{on:['CLICK', styleDetail]}});	
								distribute.formCfg = data.formCfg;
								Ice.apply(data.formCfg, {config:{on:['CLICK', styleDetail]}});
								distribute.stateCfg = data.stateCfg;
								Ice.apply(data.stateCfg, {config:{on:['CLICK', statusDetail]}});
					});
					service.getStuCountByAge(getAdvancedParam(), function(data) {
								var distribute = $scope.data.distribute;
								distribute.ageCfg = data.ageCfg;
								Ice.apply(data.ageCfg, {config:{on:['CLICK', ageDetail]}});
							});
					service.getContrastByDept(getAdvancedParam(), function(data) {
								var distribute = $scope.data.distribute;
								distribute.contrastCfg = data.contrastCfg;
								$scope.levelName = data.name;
								Ice.apply(data.contrastCfg, {config:{on:['CLICK', deptDetail]}});
								dialog.hideLoading();
					        });
					service.getContrastByPolitics(getAdvancedParam(), function(data) {
								var distribute = $scope.data.distribute;
								distribute.politicsCfg = data.politicsCfg;
								$scope.data.tuan = data.tuan;
								$scope.data.fei = data.fei;
								$scope.data.zzmmwwh = data.wwh;
								Ice.apply(data.politicsCfg, {config:{on:['CLICK', politicsDetail]}});
							});
					service.getStuFrom(getAdvancedParam(), function(data) {
								var distribute = $scope.data.distribute;
								distribute.fromCfg = data.fromCfg;
								$scope.data.gat = data.gat;
								$scope.data.q = data.q;
								$scope.data.gatcode = data.gatcode;
								$scope.data.qcode = data.qcode;
								Ice.apply(data.fromCfg, {config:{on:['MAP_SELECTED', fromDetail]}});
								distribute.nationCfg = data.nationCfg;
								$scope.data.han = data.han;
								$scope.data.shaoshu = data.shaoshu;
								$scope.data.mzwwh = data.wwh;
								Ice.apply(data.nationCfg, {config:{on:['CLICK', nationDetail]}});
							});
					if ($scope.data.fromshow == false) {getStuFromLine();};
					if ($scope.data.stateshow == false) {getStuCountLine();};
					if ($scope.data.nationshow == false) {getStuNationLine();};
					if ($scope.data.sexshow == false) {getStuSexLine();};
					if ($scope.data.ageshow == false) {getStuAgeLine();};
					if ($scope.data.politicsshow == false) {getPoliticsLine();};
					if ($scope.data.deptshow == false) {getDeptLine();};
					if ($scope.data.styleshow == false) {getStyleLine();};
					if ($scope.data.formshow == false) {getFormLine();};
				}	
					refreshData();		
					$scope.swichFrom = function() {
						if ($scope.data.fromshow == true) {
							$scope.data.fromshow = false;
							getStuFromLine();
						} else {
							$scope.data.fromshow = true;
						}
					};
					$scope.swichState = function(){
						if ($scope.data.stateshow == true) {
							$scope.data.stateshow = false;
							getStuCountLine();
						} else {
							$scope.data.stateshow = true;
						}
					
					};
					/** 概况信息 点击事件 */
					$scope.stuDetailClick = function(type, title, seniorCode){
						var detail = $scope.data.stu_detail;
//						detail.page.curpage = 1;
						detail.title = title;
						detail.values= {};
						detail.values[type] = seniorCode;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						detail.formConfig = {
								title : detail.title,
								show  : true,
								url       : $scope.data.detailUrl,
								exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
								heads  : detail.headers,
								fields : detail.fields,
								params : stuDetailParam()
							};
					};
					$scope.yjsDetailClick = function(type,type1,title,seniorCode,seniorCode1){
						var detail = $scope.data.stu_detail;
//						detail.page.curpage = 1;
						detail.title = title;
						detail.values= {};
						if(type1 == 'yjsStyle'){
							var x = seniorCode1;
							var ary = x.split(",");
							if(ary[0] != null && ary[0] != 'null'){
								detail.values['yjsStyle'] = ary[0];
							}
							if(ary[1] != null && ary[1] != 'null'){
								detail.values['direction'] = ary[1];
							}
						}
						detail.values[type] = seniorCode;
						if(type1 != 'yjsStyle'){
							detail.values[type1] = seniorCode1;
						}
						detail.values['graduate'] = 1;
						detail.headers= $scope.headers;
						detail.fields = $scope.fields;
						detail.formConfig = {
								title : detail.title,
								show  : true,
								url       : $scope.data.detailUrl,
								exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
								heads  : detail.headers,
								fields : detail.fields,
								params : stuDetailParam()
							};
					};
					$scope.swichNation = function(){
						if ($scope.data.nationshow == true) {
							$scope.data.nationshow = false;
							getStuNationLine();
						} else {
							$scope.data.nationshow = true;
						}
					};

					$scope.swichSex = function(){
						if ($scope.data.sexshow == true) {
							$scope.data.sexshow = false;
							getStuSexLine();
						} else {
							$scope.data.sexshow = true;
						}
					};
					$scope.swichAge = function(){
						if ($scope.data.ageshow == true) {
							$scope.data.ageshow = false;
							getStuAgeLine();
						} else {
							$scope.data.ageshow = true;
						}
					};
					$scope.swichPolitics = function(){
						if ($scope.data.politicsshow == true) {
							$scope.data.politicsshow = false;
							getPoliticsLine();
						} else {
							$scope.data.politicsshow = true;
						}
					};
					$scope.swichDept = function(){
						if ($scope.data.deptshow == true) {
							$scope.data.deptshow = false;
							getDeptLine();
						} else {
							$scope.data.deptshow = true;
						}
					};
					$scope.swichStyle = function(){
						if ($scope.data.styleshow == true) {
							$scope.data.styleshow = false;
							getStyleLine();
						} else {
							$scope.data.styleshow = true;
						}
					};
					$scope.swichForm = function(){
						if ($scope.data.formshow == true) {
							$scope.data.formshow = false;
							getFormLine();
						} else {
							$scope.data.formshow = true;
						}
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
					service.getAdvance(function(data){
						$scope.data.advance.source = data;
					});
//					$scope.stuDetailDown= function(){
//						var param = stuDetailParam();
//						param.fileName = $scope.data.stu_detail.title;
//						service.getStuDetailDown(param,function(data){
//							
//						})
//					};
					$scope.historyDetailDown= function(){
						var param = historyDetailParam();
						param.fileName = $scope.data.history_detail.title;
						service.getStuDetailDown(param,function(data){
							
						})
					};
				}]);