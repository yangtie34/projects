var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','$timeout','dialog', function($scope,service,advancedService,$timeout,dialog) {
			$scope.data = {
				    mask:false,
				    jzdshow:false,
				    tbshow :false,
				    timeshow:false,
					advance : {
						param : null
					},
					detailUrl : "pmsScorePredictTea/getStuList",
					exportUrl : 'pmsScorePredictTea/down',
					stu_detail : { 
						values : {},
					},
					param:{},
					distribute : {},
					season:false,
					grid : {
						header : [],
						mask   : false,
						show   : function(){ this.mask=true; },
						hide   : function(){ this.mask = false; }
					},
				};
				var getJson = function(obj){
					return JSON.stringify(obj);
				}
				var getParam = function(){
					var obj = Ice.apply({},$scope.data.param);
					obj.param = getAdvancedParam();
					return obj;
				}
				var showLoading = function(){dialog.showLoading();};
				var hideLoading = function(){dialog.hideLoading();};
				var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
				var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
				/** 参数 */
				var stuDetailParam = function(){
					var detail = $scope.data.stu_detail,
						param  = getParam(),
						fields = getJson(detail.fields),
						headers= getJson(detail.headers);
					detail.values['course'] = $scope.data.course;
					detail.values['schoolyear'] = $scope.data.schoolYear;
					detail.values['termcode'] = $scope.data.termCode;
					detail.values['edu'] = $scope.data.edu;
					Ice.apply(param, {values:getJson(detail.values), fields:fields, headers:headers});
					return param;
				}
				service.getEduList().then(function(data) {
									$scope.data.eduList = data;
									if(data.length>0){
										$scope.data.edu = data[0].id;
									}
				});
				service.getDate().then(function(data) {
					$scope.data.lyDate = data.date;
                });
				service.getTimeList().then(function(data) {
					var list = data;
					$scope.data.allTimeList = list;
					$scope.data.subTimeList = [];
					for (var i=0;i<list.length;i++){
					  if (i<6){
						  $scope.data.subTimeList.push(list[i]);
					  }
					}
					if($scope.data.timeshow){
						$scope.data.timeList = $scope.data.allTimeList;
					}else{
						$scope.data.timeList = $scope.data.subTimeList;
					}
					if(data.length>0){
						$scope.data.time = data[0].id;
						var ary = data[0].id.split(",");
						$scope.data.schoolYear =ary[0];
	    				$scope.data.termCode = ary[1];
					}
					init();
                });
				service.getThList().then(function(data) {
					$scope.data.thList = data;
                });
				service.getFthList().then(function(data) {
					$scope.data.fthList = data;
                });
				var getCourseList = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var edu = $scope.data.edu;
					service.getCourseList(getAdvancedParam(),schoolYear,termCode,edu,function(data){
										$scope.data.courseList = data;
										if(data.length >0){
										$scope.data.course = data[0].id;
										$scope.data.courseName = data[0].mc;
										}
					});
				};
				var getTeachClassAndStuCount = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var edu = $scope.data.edu;
					service.getTeachClassAndStuCount(schoolYear,termCode,courseId,edu,getAdvancedParam(),function(data){
										$scope.data.classCount = data.bj;
										$scope.data.stuCount = data.stu;
					});
				};
				var getCourseNatureList = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var edu = $scope.data.edu;
					service.getCourseNatureList(schoolYear,termCode,courseId,edu,getAdvancedParam(),function(data){
										$scope.data.natureList = data;
										if(data.length >0){
											$scope.data.nature = data[0].id;
											$scope.data.natureName = data[0].mc;
										}
					});
				};
				var getClassScoreGk = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					service.getClassScoreGk(schoolYear,termCode,courseId,nature,edu,getAdvancedParam(),function(data){
										$scope.data.tabList = data.list;
					});
				};
				var getChartData = function(){ 
					showLoading();
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var edu = $scope.data.edu;
					service.getChartData(schoolYear,termCode,courseId,edu,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						    distribute.barCfg = data.barCfg;
						    hideLoading();
					});
				};
				var getSameData = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					service.getSameData(schoolYear,courseId,nature,edu,getAdvancedParam(),function(data){
						$scope.data.tbshow = true;				
						$scope.data.sameList = data.list;
					});
				};
				var getPrecisionList = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					service.getPrecisionList(schoolYear,courseId,nature,edu,getAdvancedParam(),function(data){
										$scope.data.precisionList = data.list;
										$scope.data.th1List = data.th;
										$scope.data.jzdshow = true;
					});
				};
				var getNowPrecision = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					service.getNowPrecision(schoolYear,termCode,courseId,nature,edu,getAdvancedParam(),function(data){
										$scope.data.scale = data.scale;
										$scope.data.isnow = data.isnow;
					});
				};
				var initnature = function(){
					getClassScoreGk();
//					getSameData();
//					getPrecisionList();
					getNowPrecision();
				};
				var initCourse = function(){
					getTeachClassAndStuCount();
					getCourseNatureList();
					getChartData();
					initnature();
				};
				var init = function(){
					getCourseList();
					initCourse();
				};
				$scope.eduSelect = function(edu){
					$scope.data.edu = edu;
					init();
				};
				$scope.timeSelect = function(id){
					$scope.data.time = id;
					var ary = id.split(",");
					$scope.data.schoolYear =ary[0];
    				$scope.data.termCode = ary[1];
    				$scope.data.course = null;
    				$scope.data.nature = null;
    				init();
				};
				$scope.courseSelect = function(id,mc){
					$scope.data.course = id;
					$scope.data.courseName = mc;
					$scope.data.nature = null;
					initCourse();
				};
				$scope.natureSelect = function(id,mc){
					$scope.data.nature = id;
					$scope.data.natureName = mc;
					initnature();
				};
				$scope.tbClick = function(){
					getSameData();
				};
				$scope.jzdClick = function(){
					getPrecisionList();
				};
				$scope.advanceChange = function(data){
					var param = advancedService.getParam(data);
					// 高级查询-参数
					setAdvancedParam(param);
					init();
				};
				$scope.$watch("data.tbshow",function(newVal,oldVal){
					if(newVal != oldVal){
						 console.log(newVal);
					}
				},true);
				/** 概况信息 点击事件 */
				$scope.stuListClick = function(type,type1,title, seniorCode ,seniorCode1){
					var detail = $scope.data.stu_detail;
					detail.title = title+"学生名单";
					detail.values= {};
					detail.values[type] = seniorCode||null;
					detail.values[type1] = seniorCode1||null;
					detail.headers= ['学号','入学年级','姓名','性别','地区','院系','专业','班级','预测成绩'];
					detail.fields = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc','score'];
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
				$scope.loadMore =function(){
					$scope.data.timeshow = !$scope.data.timeshow;
					if($scope.data.timeshow){
						$scope.data.timeList = $scope.data.allTimeList;
					}else{
						$scope.data.timeList = $scope.data.subTimeList;
					}
				}
				service.getAdvance(function(data){
					$scope.data.advance.source = data;
				});
				/**
				 * 高级查询-切换show
				 */
				$scope.advanceShow = function(){
					$scope.data.advance.show = !$scope.data.advance.show;
				};
}]);