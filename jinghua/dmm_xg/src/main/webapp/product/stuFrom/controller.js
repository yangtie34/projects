/**
 * 生源地分析
 */
var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','$timeout','dialog', function($scope, service,advancedService,$timeout,dialog) {

			$scope.data = {
				mask : true,	
				name : '生源地！！！',
				distribute : {},
				history : {},
				param:{},
				from : [],
				detailUrl : "pmsStuFrom/getStuList",
				exportUrl : 'pmsStuFrom/down',
				talval : [],
				show1 : true,
				show2 : false,
				show3 : false,
				show4 : false,
				lineshow : false,
				bili : 50,// 根据它和bili判断饼图中的最大值是否拿出去
				len : 5,
				advance:{
					param :null
				},
				stu_detail : { 
//					page : {
//						curpage  : 1,
//						pagesize : 10,
//						sumcount : null
//					},
//					title  : null,
					values : {},
//					fields : [],
//					headers: [],
//					list   : null,
//					mask   : false
				}
			};
			$scope.x = 1;
			var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	        var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	        var showLoading = function(){dialog.showLoading();};
			var hideLoading = function(){dialog.hideLoading();};
	        $scope.order = true;
			$scope.vm1 = {
				page : {
					total : 0,
					size : 10,
					index : 1
				},
				items : [],
				loading : false
			};
			$scope.vm2 = {
				page : {
					total : 0,
					size : 10,
					index : 1
				},
				items : [],
				loading : false
			};
			$scope.vm3 = {
				page : {
					total : 0,
					size : 7,
					index : 1
				},
				items : [],
				loading : false
			};
			$scope.updown = true;
			// tab
			var initParams = function() {
				$scope.vm1.page.index = 1;
				$scope.vm2.page.index = 1;
				$scope.vm3.page.index = 1;
				getStufrom();
				getStuFromTab();
				getGrowth();
				getSchoolTag();
			}
			service.getMinGrade(getAdvancedParam()).then(function(data) {
						$scope.selval = data.list;
						$scope.selval1 = [{
									id : data.max,
									mc : data.max + '年'
								}];
						$scope.minYear = data.min;
						$scope.maxYear = data.max;
						$scope.fSelVal = data.max;
						$scope.tSelVal = data.max;
						$scope.yearLen = eval($scope.maxYear - $scope.minYear
								+ 1);
					});
			service.getStuEdu(getAdvancedParam()).then(function(data) {
						$scope.data.edu = data;
					});
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
//					page   = getJson(detail.page),
//					values = getJson(detail.values),
					fields = getJson(detail.fields),
					headers= getJson(detail.headers),
				    from   = $scope.fSelVal == null? $scope.maxYear: $scope.fSelVal,
		            to     = $scope.tSelVal == null? $scope.maxYear: $scope.tSelVal;
				detail.values['edu'] = $scope.eduid;
				detail.values['updown'] = $scope.updown;
				detail.values['from'] = from;
				detail.values['to'] = to;
				Ice.apply(param, {values:getJson(detail.values), fields:fields, headers:headers/*,
					divid:divid,updown:updown,from:from,to:to,edu:edu*/});
				return param;
			}
			var pieDetail  = function(param,obj,data){
				var detail = $scope.data.stu_detail;
				detail.title  = "生源地（"+param.name+"）学生名单";
				detail.values = {};
				detail.headers= ['序号','学号','入学年级','姓名','性别','地区','院系','专业','班级'];
				detail.fields = ['nm','no','enroll_grade','name','sexmc','jg','deptmc','majormc','classmc'];
				param.data = obj;
				distributionDetail(param,'xzqh');
			}
			var distributionDetail = function(param, type){
				var detail = $scope.data.stu_detail;
//				detail.page.curpage = 1;
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
//				getStuList();
			};
			var getStufrom = function() {
				showLoading();
				var divid = $scope.divid;
				var updown = $scope.updown;
				var from = $scope.fSelVal == null
						? $scope.maxYear
						: $scope.fSelVal;
				var to = $scope.tSelVal == null
						? $scope.maxYear
						: $scope.tSelVal;
				var edu = $scope.eduid;
				var pagesize = $scope.vm2.page.size;
				var index = $scope.vm2.page.index;
				var bili = $scope.data.bili;
				var len = $scope.data.len;
				service.getStuFrom(getAdvancedParam(), from, to, edu, divid, updown,
						pagesize, index, bili, len, function(data) {
							var distribute = $scope.data.distribute;
							distribute.fromCfg = data.fromCfg;
							distribute.fromPieCfg = data.fromPieCfg;
							Ice.apply(data.fromPieCfg, {config:{on:['CLICK', pieDetail]}});
							$scope.data.from = data.list;
							$scope.data.tabval1 = data.pie;
							$scope.data.fromsum = data.sum;
							$scope.data.tabval2 = data.tablist;
							$scope.data.tabval3 = data.tablist1;
							$scope.data.fromavg = Math.abs(data.avg);
							$scope.data.fromavg1 = data.avg;
							$scope.data.fromsub = data.sub;
							$scope.data.sxq = data.sxq;
							$scope.data.sxqid = data.sxqid;
							if (data.mapt == '中国') {
								$scope.data.mapt = '全国';
							} else {
								$scope.data.mapt = data.mapt;
							}
							$scope.data.level = data.cc;
							hideLoading();
						});
			};
			var subSelval = function() {
				var value = $scope.fSelVal;
				var list1 = [];
				var list = $scope.selval;
				for (var i = 0; i < list.length; i++) {
					if (parseInt(list[i].id) > parseInt(value)
							|| parseInt(list[i].id) == parseInt(value)) {
						list1.push(list[i]);
					}
				}
				$scope.selval1 = list1;
			}
			var getStuFromTab = function() {
				$scope.vm1.loading = true;
				var pagesize = $scope.vm1.page.size;
				var index = $scope.vm1.page.index;
				var divid = $scope.divid;
				var updown = $scope.updown;
				var from = $scope.fSelVal == null
						? $scope.maxYear
						: $scope.fSelVal;
				var to = $scope.tSelVal == null
						? $scope.maxYear
						: $scope.tSelVal;
				var edu = $scope.eduid;
				service.getStuFromTab(getAdvancedParam(),from, to, edu, divid, updown,
						pagesize, index,function(data) {
							$scope.vm1.items = data.items;
							$scope.vm1.page.total = data.total;
							$scope.vm1.loading = false;
						});
			};
//			var getStuList = function() {
//				showLoading();
//				var detail = $scope.data.stu_detail;
////				var pagesize = $scope.data.stu_detail.page.pagesize;
////				var index = $scope.data.stu_detail.page.curpage;
//				service.getStuList(stuDetailParam(),function(data) {
//						    detail.list = data.items;
//							detail.page.sumcount = data.total;
//							detail.page.pagecount = data.pagecount;
//							detail.mask = false;
//							detail.detail_show = true;
//						},hideLoading());
//			};
			var getGrowth = function() {
				var pagesize = $scope.vm2.page.size;
				var index = $scope.vm2.page.index;
				var divid = $scope.divid;
				var updown = $scope.updown;
				var from = $scope.fSelVal == null
						? $scope.maxYear
						: $scope.fSelVal;
				var to = $scope.tSelVal == null
						? $scope.maxYear
						: $scope.tSelVal;
				var edu = $scope.eduid;
				service.getGrowth(getAdvancedParam(), from, to, edu, divid, updown,function(data) {
							var distribute = $scope.data.distribute;
							$scope.data.sydresult = data.items;
							$scope.vm2.page.total = data.items == null ? 0 :data.items.length;
							$scope.eduid = data.xl;
							sublistdept();
							distribute.fromLineCfg = data.fromLineCfg;
						});
			};
			getGrowth();
			var sublistdept = function(){
				var pagesize = $scope.vm2.page.size;
				var index = $scope.vm2.page.index;
				var x = pagesize*index>$scope.data.sydresult.length?$scope.data.sydresult.length:index*pagesize;
				$scope.data.sydlist = [];
				for(var i=(index-1)*pagesize;i<x;i++){
					 $scope.data.sydlist.push($scope.data.sydresult[i]);
				}
		    };
			var getCountAndLv = function(id, name){
				var from = $scope.fSelVal == null
				? $scope.maxYear
				: $scope.fSelVal;
				var to = $scope.tSelVal == null
				? $scope.maxYear
				: $scope.tSelVal;
				var divid = id;
				var edu = $scope.eduid;
				service.getCountAndLv(getAdvancedParam(),from,to, edu, divid,name, function(data) {
					$scope.data.lvShow = true;        
					$scope.data.countLvCfg = data.countLvCfg;
				});
			};
			var getSchoolTag = function() {
				$scope.vm3.loading = true;
				var pagesize = $scope.vm3.page.size;
				var index = $scope.vm3.page.index;
				var divid = $scope.divid;
				var updown = $scope.updown;
				var from = $scope.fSelVal == null
						? $scope.maxYear
						: $scope.fSelVal;
				var to = $scope.tSelVal == null
						? $scope.maxYear
						: $scope.tSelVal;
				var edu = $scope.eduid;
				var Order = $scope.order;
				service.getSchoolTag(getAdvancedParam(), from, to, edu, divid, updown,
						pagesize, index, Order, function(data) {
							$scope.vm3.items = data.items;
							$scope.vm3.page.total = data.total;
							$scope.vm3.loading = false;
						});
			}
			getStufrom();
			$scope.mapClick = function(params) {
				$scope.mapType = params;
				var list = $scope.data.from;
				var count = 0;
				for (var i = 0; i < list.length; i++) {
					var mc = list[i].name;
					if ($scope.mapType == mc) {
						count = 1;
						$scope.divid = list[i].id;
						$scope.divpid = list[i].pid;
						$scope.divcc = list[i].cc;
					}
				}
				if (count == 0){
					$scope.divid = null;
				}
				console.log($scope.x++);
				$scope.updown = true;
				initParams();
			};
			$scope.pageUp = function() {
				$scope.updown = false;
				var list = $scope.data.from;
				$scope.divid = list[0].id;
				initParams();
			};
			/** 概况信息 点击事件 */
			$scope.stuListClick = function(type, title, seniorCode){
				var detail = $scope.data.stu_detail;
//				detail.page.curpage = 1;
				detail.title = title;
				detail.values= {};
				if (type!='xzqh'){
				detail.values['xzqhmc'] = $scope.data.mapt == "全国"?"中国":$scope.data.mapt;
				}
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
//				getStuList();
			};
			$scope.fselect = function(value, name) {
				$scope.fSelVal = value;
				$scope.tSelVal = null;
				subSelval();
			};
			$scope.lvClick = function(id, name){
				$scope.data.lvName = name;
				getCountAndLv(id, name);
			}
			$scope.tselect = function(value, name) {
				$scope.tSelVal = value;
				var a = $scope.fSelVal;
				var b = $scope.tSelVal;
				if (b == $scope.maxYear) {
					if (a == $scope.maxYear) {
						$scope.data.show1 = true;
						$scope.data.show2 = false;
						$scope.data.show3 = false;
						$scope.data.show4 = false;
					} else if (a == eval($scope.maxYear - 4)) {
						$scope.data.show1 = false;
						$scope.data.show2 = true;
						$scope.data.show3 = false;
						$scope.data.show4 = false;
					} else if (a == eval($scope.maxYear - 9)) {
						$scope.data.show1 = false;
						$scope.data.show2 = false;
						$scope.data.show3 = true;
						$scope.data.show4 = false;
					} else {
						$scope.data.show1 = false;
						$scope.data.show2 = false;
						$scope.data.show3 = false;
						$scope.data.show4 = false;
					}
				} else if (a == eval($scope.maxYear - 1)&&b == eval($scope.maxYear - 1)) {
					$scope.data.show1 = false;
					$scope.data.show2 = false;
					$scope.data.show3 = false;
					$scope.data.show4 = true;
				} else {
					$scope.data.show1 = false;
					$scope.data.show2 = false;
					$scope.data.show3 = false;
					$scope.data.show4 = false;
				}
				initParams();
			};
			$scope.yearClick = function(value) {
				$scope.tSelVal = $scope.maxYear;
				if (value == '1') {
					$scope.fSelVal = $scope.maxYear;
					$scope.data.show1 = true;
					$scope.data.show2 = false;
					$scope.data.show3 = false;
					$scope.data.show4 = false;
				} else if (value == '2') {
					$scope.fSelVal = eval($scope.maxYear - 4);
					$scope.data.show1 = false;
					$scope.data.show2 = true;
					$scope.data.show3 = false;
					$scope.data.show4 = false;
				} else if (value == '3') {
					$scope.fSelVal = eval($scope.maxYear - 9);
					$scope.data.show1 = false;
					$scope.data.show2 = false;
					$scope.data.show3 = true;
					$scope.data.show4 = false;
				} else if (value == '4') {
					$scope.fSelVal = eval($scope.maxYear - 1);
					$scope.tSelVal = eval($scope.maxYear - 1);
					$scope.data.show1 = false;
					$scope.data.show2 = false;
					$scope.data.show3 = false;
					$scope.data.show4 = true;
				}
				subSelval();
				initParams();
			};
			$scope.eduSet = function(value) {
				$scope.eduid = value;
				initParams();
			};
			$scope.showLine = function(id, name,bs) {
				var divid = id;
				var from = $scope.fSelVal == null
						? $scope.maxYear
						: $scope.fSelVal;
				var to = $scope.tSelVal == null || ""
						? $scope.maxYear
						: $scope.tSelVal;
				var edu = $scope.eduid;
				service.getCountLine(getAdvancedParam(), from, to, edu, divid,
						function(data) {
							var distribute = $scope.data.distribute;
							distribute.countLineCfg = data.countLineCfg;
							$scope.data.linename = name;
							$scope.data.lineshow = true;
							$scope.data.bs = bs;
						});
			};
			$scope.$watch("vm1.page.index", function(newVal, oldVal) {
						getStuFromTab();
					}, true);
			$scope.$watch("vm3.page.index", function(newVal, oldVal) {
						getSchoolTag();
					}, true);
//			$scope.$watch("data.stu_detail.page.curpage",function(newVal,oldVal){
//				if(newVal != oldVal){
//					$scope.data.stu_detail.mask = true;
//					$scope.data.stu_detail.page.curpage = newVal;
//					getStuList();
//				}
//			},true);
			$scope.PageUp1 = function() {
				$scope.vm1.page.index--;
			};
			$scope.PageUp2 = function() {
				$scope.vm2.page.index--;
				sublistdept();
			};
			$scope.PageUp3 = function() {
				$scope.vm3.page.index--;
			};
			$scope.PageDown1 = function() {
				$scope.vm1.page.index++;
			};
			$scope.PageDown2 = function() {
				$scope.vm2.page.index++;
				sublistdept();
			};
			$scope.PageDown3 = function() {
				$scope.vm3.page.index++;
			};
			$scope.tabSort = function(value) {
				$scope.order = value;
				$scope.vm3.page.index = 1;
				getSchoolTag();
			};
		/**
		 * 高级查询-change事件
		 */
		$scope.advanceChange = function(data){
			var param = advancedService.getParam(data);
			// 高级查询-参数
			setAdvancedParam(param);
			initParams();
		};
		service.getAdvance(function(data){
			$scope.data.advance.source = data;
		});
//		$scope.stuListDown= function(){
//			var param = stuDetailParam();
//			param.fileName = $scope.data.stu_detail.title;
//			service.getStuListDown(param,function(data){
//				
//			});
//		};
		$scope.getExportData= function(bs){
			var name = "地区";
			if ($scope.data.level=='1'){
			    	name = "省份";
			    }else if($scope.data.level== '3'){
			    	name = "县镇";
			    }
			var   from   = $scope.fSelVal == null? $scope.maxYear: $scope.fSelVal,
			      to     = $scope.tSelVal == null? $scope.maxYear: $scope.tSelVal,
			      edu = $scope.eduid,
				  updown = $scope.updown,
				  divid = $scope.divid,
			headers= bs != 'from'?['地区','平均增长','平均增幅']:['地区','人数','占比'],
			fields = ['name','value','scale'],
			fileName = bs == 'from'?"各"+name+"来校人数排名":"各"+name+"平均增幅排名";
			fields = getJson(fields);
			headers= getJson(headers);
			service.getExportData(getAdvancedParam(),from,to,edu,divid,updown,
					bs,fields,headers,fileName,function(data){
				
			});
		};
		}]);