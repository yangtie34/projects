/**
 * 学霸
 */
var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','$timeout','dialog', function($scope,service,advancedService,$timeout,dialog) {
		$scope.data = {
		    mask:false,
		    stuQj:false,
			advance : {
				param : null
			},
			detailUrl : "pmsSmart/getStuList",
			exportUrl : 'pmsSmart/down',
			stu_detail : { 
				values : {},
			},
			param:{},
			season:false,
			grid : {
				header : [],
				mask   : false,
				show   : function(){ this.mask=true; },
				hide   : function(){ this.mask = false; }
			},
			grid1 : {
				header : [],
				mask   : false,
				show   : function(){ this.mask=true; },
				hide   : function(){ this.mask = false; }
			},
		    selected:"YX",
		    tselname:'按院系',
		    topGpa:[],
		    columnid:"avg",
			asc:false,
		};
		$scope.vm1 = {
				page : {
					total : 0,
					size : 10,
					index : 1,
					total:0
				},
				items : [],
				count : 0
			};
		$scope.vm2 = {
				page : {
					total : 0,
					size : 10,
					index : 1,
					total:0
				},
				items : [],
				count : 0
			};
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
		var showLoading = function(){dialog.showLoading();};
		var hideLoading = function(){dialog.hideLoading();};
		/** 参数 */
		var stuDetailParam = function(){
			var detail = $scope.data.stu_detail,
				param  = getParam(),
				fields = getJson(detail.fields),
				headers= getJson(detail.headers);
			detail.values['grade'] = $scope.data.grade;
			detail.values['schoolYear'] = $scope.data.schoolYear;
			detail.values['edu'] = $scope.data.edu;
			detail.values['xzqh'] = $scope.data.xzqh;
			detail.values['term'] = $scope.data.term;
			detail.values['updown'] = $scope.updown;
			Ice.apply(param, { values:getJson(detail.values), fields:fields, headers:headers});
			return param;
		}
		$scope.tabcolumn=[{"id":"avg","mc":"平均绩点"},{"id":"value","mc":"学霸人数"},{"id":"lv","mc":"学霸占比"},{"id":"avg1","mc":"前10%平均绩点"}]
		$scope.updown = true;
		var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
		var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
		service.getEduSelect().then(function(data) {
							$scope.data.eduSelect = data.list;
							$scope.data.xbgpa = data.xbgpa;
							if(data.list.length>0){
								$scope.data.eduName = data.list[0].id;
							}
		});
		var getTimeLine = function(){
			var schoolYear = $scope.data.schoolYear;
			var termCode = $scope.data.term;
			service.getTimeLine(schoolYear,termCode).then(function(data) {
					$scope.data.summer = data.summer;
					$scope.data.winter = data.winter;
					$scope.data.season = false;
				$scope.data.seasonList = $scope.data.summer;
	        });
		};
		getTimeLine();
		var getGradeSelect = function(){ 
		var schoolYear = $scope.data.schoolYear;
		service.getGradeSelect(getAdvancedParam(),schoolYear).then(function(data) {
							$scope.data.gradeSelect = data.list;
		});
		}
		service.getYearAndTerm().then(function(data) {
							$scope.data.yearSelect = data.list;
							if(data.list.length>0){
								$scope.data.yselName = data.list[0].id;
							}
		});	
		var getTopGpa = function(){
			var grade = $scope.data.grade;
			var term =$scope.data.term;
			var schoolYear = $scope.data.schoolYear;
			var edu =$scope.data.edu;
			var pagesize = $scope.vm1.page.size;
			var index = $scope.vm1.page.index;
		service.getTopGpa(schoolYear,term,grade,edu,getAdvancedParam(),pagesize,index).then(function(data) {
			if($scope.vm1.count<2&&$scope.vm1.count!=$scope.vm1.count1){	
				$scope.data.topGpa = $.merge($scope.data.topGpa,data.list);
				if($scope.vm1.count==1){
					$scope.vm1.page.size = 20;
				}
			}else{
				$scope.data.topGpa = data.list;
			}
			$scope.vm1.count1 =$scope.vm1.count; 
			$scope.vm1.page.total =data.total;
		});
		}
		var getXbFrom = function(){
			var grade = $scope.data.grade;
			var term =$scope.data.term;
			var schoolYear = $scope.data.schoolYear;
			var edu =$scope.data.edu;
			var xzqh = $scope.data.xzqh;
			var updown =$scope.updown;
		service.getXbFrom(schoolYear,term,grade,edu,xzqh,updown,getAdvancedParam(),function(data) {
							$scope.data.fromCfg = data.fromCfg;
							$scope.data.from = data.from;
							$scope.data.level = data.cc;
							if(data.maptype=='中国'){
							$scope.data.mapt = '全国';
							}else{
								$scope.data.mapt =data.maptype;
							}
		});
		}
		var getTable = function(){ 
			var grade = $scope.data.grade;
			var term =$scope.data.term;
			var schoolYear = $scope.data.schoolYear;
			var edu =$scope.data.edu;
			var type =$scope.data.selected;
			var pagesize =$scope.vm2.page.size;
			var index =$scope.vm2.page.index;
			var column = $scope.data.columnid;
			var asc =  $scope.data.asc;
			$scope.data.grid.show();
		service.getTable(schoolYear,term,grade,edu,pagesize,index,column,asc,type,getAdvancedParam()).then(function(data) {
			                $scope.data.grid.hide();		
			                $scope.data.tabledata = data.list;
							$scope.vm2.page.total = data.total;
		});
		}
		var getDisplayedLevelType = function(){
			service.getDisplayedLevelType(function(tabs){
				// 设置默认显示第一个
				if(tabs && tabs.length > 0){
					tabs[0].show = true;
				}
				$scope.data.tabs = tabs;
				getTable();
			});
		}
		getDisplayedLevelType();
		var getXbCountLine = function(){
			showLoading();
			var edu =$scope.data.edu;
		service.getXbCountLine(edu,getAdvancedParam(),function(data) {
							$scope.data.countLineCfg = data.countLineCfg;
							hideLoading();
		});
		}
		var getRadar = function(){
			$scope.data.grid1.show();
			var grade = $scope.data.grade;
			var term =$scope.data.term;
			var schoolYear = $scope.data.schoolYear;
			var edu =$scope.data.edu;
		    service.getRadar(schoolYear,term,grade,edu,getAdvancedParam(),function(option) {
							$scope.data.radarCfg = option;
							 $scope.data.grid1.hide();		
		});
		}
		var initParam = function(){
			$scope.vm1.page.index = 1;
			$scope.vm2.page.index = 1;
			getRadar();
			getXbFrom();
			getTopGpa();
		}
		initParam();
		$scope.selectGrade= function(value,bs){
			$scope.data.grade = value;
			$scope.data.gradebs = bs;
			$scope.vm1.page.size = 10;
			$scope.vm1.count = 0;
			$scope.vm1.count1 = 0;
		    initParam();
		    getTable();
		}
		$scope.selectYear= function(id,data){
			var list1 = id.split('-');
			var list2 = list1[1].split(',');
			var schoolYear = id==null?id:list1[0];
			var term = id==null?id:list2[1];
			$scope.data.schoolYear = schoolYear;
			$scope.data.term = term;
			$scope.vm1.page.size = 10;
			$scope.vm1.count = 0;
			$scope.vm1.count1 = 0;
			$scope.data.yselName = data.mc;
			if($scope.data.gradebs!=null){
				$scope.data.grade = eval(schoolYear-$scope.data.gradebs+1);	
			}
			getGradeSelect();
			getTimeLine();
			initParam();
			getTable();
		}
		$scope.selectEdu = function(id,data){
			$scope.data.edu = id;
			$scope.data.eduName = data.mc;
			$scope.vm1.page.size = 10;
			$scope.vm1.count = 0;
			$scope.vm1.count1 = 0;
			getXbCountLine();
		    initParam();
		    getTopGpa();
		    getTable();
		}
		getXbCountLine();
		$scope.mapClick = function(params) {
				$scope.mapType = params;
				var list = $scope.data.from;
				for (var i = 0; i < list.length; i++) {
					var mc = list[i].name;
					if ($scope.mapType == mc) {
						$scope.data.xzqh = list[i].id;
					}
				}
				$scope.updown = true;
				getXbFrom();
			};
	    $scope.selectTab = function(id,mc){
	    	$scope.data.selected  = id;
	    	$scope.data.tselname = mc;
	    	if($scope.data.selected=="COURSE"||$scope.data.selected=="TEACHER"){
	    		$scope.tabcolumn=[{"id":"avg","mc":"平均分"},{"id":"value","mc":"学霸人数"},{"id":"lv","mc":"学霸占比"},{"id":"avg1","mc":"前10%平均分"}]	
	    	}
	    	getTable();
	    }
		/** 概况信息 点击事件 */
		$scope.stuListClick = function(type, title, seniorCode){
			var detail = $scope.data.stu_detail;
			detail.title = title;
			detail.values= {};
			detail.values[type] = seniorCode||null;
			detail.headers= ['序号','学号','入学年级','姓名','性别','地区','院系','专业','班级'];
			detail.fields = ['nm','no','enroll_grade','name','sexmc','jg','deptmc','majormc','classmc'];
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
		$scope.advanceChange = function(data){
			var param = advancedService.getParam(data);
			// 高级查询-参数
			setAdvancedParam(param);
			 initParam();
			 getXbCountLine();
			 getGradeSelect();
			 getTable();
		};
		
		 getGradeSelect();
		$scope.PageDown1 = function() {
				$scope.vm1.page.index--;
				 getTopGpa();
			};
	    $scope.PageDown2 = function() {
				$scope.vm2.page.index--;
				 getTable();
			};
		$scope.PageUp2 = function() {
				$scope.vm2.page.index++;
				 getTable();
			};		
		 $scope.loadMore = function(){
			 $scope.vm1.page.index++;
			 $scope.vm1.count++;
			 getTopGpa();
		 }
		service.getAdvance(function(data){
			$scope.data.advance.source = data;
		});
		$scope.pageUp = function() {
			$scope.updown = false;
			var list = $scope.data.from;
			$scope.data.xzqh = list[0].id;
			getXbFrom();
		};
		$scope.tabsort =function(id,asc){
			$scope.data.columnid = id;
			$scope.data.asc = asc;
			$scope.vm2.page.index=1;
			 getTable();
		}
		$scope.changeSeason = function(){
			if (!$scope.data.season){
				$scope.data.seasonList = $scope.data.winter;
				$scope.data.season=true;
			}else{
				$scope.data.seasonList=$scope.data.summer;
				$scope.data.season=false;
			}
		}
		/**
		 * 高级查询-切换show
		 */
		$scope.advanceShow = function(){
			$scope.data.advance.show = !$scope.data.advance.show;
		};
		$scope.stuClick =function(id,mc){
			$scope.data.stuMc =mc;
			var grade = $scope.data.grade;
			var term =$scope.data.term;
			$scope.data.stuQj= true;
			var schoolYear = $scope.data.schoolYear;
			var edu =$scope.data.edu;
			var stuNo = id;
			service.getRadarStu(schoolYear,term,grade,edu,stuNo,getAdvancedParam(),function(option){
				$scope.data.radarMeCfg = option;
				$scope.data.stuQj= true;
			});
			service.getCostStu(schoolYear,term,grade,edu,stuNo,getAdvancedParam(),function(data){
				$scope.data.cost = data;
				$scope.data.stuQj= true;
			});
			service.getBorrowStu(schoolYear,term,grade,edu,stuNo,getAdvancedParam(),function(data){
				$scope.data.borrow = data;
				$scope.data.stuQj= true;
			});
			service.getScoreStu(schoolYear,term,grade,edu,stuNo,getAdvancedParam(),function(data){
				$scope.data.score = data;
				$scope.data.stuQj= true;
			});
			service.getScoreStuMx(schoolYear,term,stuNo,function(data){
				$scope.data.scoreMx = data.list;
				$scope.data.stuQj= true;
			});
			service.getDormStu(stuNo,function(data){
				$scope.data.dorm = data;
				$scope.data.stuQj= true;
			});
		};
		
}]);