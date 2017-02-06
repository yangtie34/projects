/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService','$timeout', 'dialog',function($scope, service, advancedService,$timeout,dialog){
	
	$scope.data = {
		mask : true,
		dateName   : '', // 年名称；今年、去年、近五年、2012-2013年
		value_year : null,
		value_edu  : null,
		thisYear   : null,
		lastYear   : null,
		advance : {
			param : null
		},
		detailUrl : "pmsSubsidy/getStuDetail",
		exportUrl : 'pmsSubsidy/down',
		stu_detail : {
			page : {
				curpage :1,
				pagesize:10,
				sumcount:null
			},
			title  : null,
			values : {},
			fields : [],
			headers: [],
			list   : null,
			mask   : false
		}
	
	};
	$scope.headers=['学号','入学年级','姓名','性别','院系','专业','班级','获得奖项','获奖金额（元）'];
	$scope.fields=['no','enroll_grade','name','sexmc','deptmc','majormc','classmc','shipname','money']
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	var showLoading = function(){ dialog.showLoading(); };
	var hideLoading = function(){ dialog.hideLoading(); };
	
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
			//page   = getJson(detail.page),
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		    detail.values['value_year'] = $scope.data.value_year;
		    detail.values['value_edu']= $scope.data.value_edu;
		Ice.apply(param, { values:getJson(detail.values), fields:fields, headers:headers});
		return param;
	};
	//机构获奖学生信息
	var deptDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail,obj2={};
		detail.title  = param.name+"（"+param.seriesName+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		obj2.level=obj.level_,obj2.deptId=obj.id,obj2.code=obj.list[param.seriesIndex].code;
		detail.values['dept']=obj2;
		if($scope.data.deptData.id=='count')distributionDetail();
		
	};
	//历年获得各奖项人员
	var yearDetail=function(param,obj,data){
		var detail=$scope.data.stu_detail,obj2={};
		detail.title=param.name+"学年（"+param.seriesName+"）学生名单";
		detail.headers=$scope.headers;
		detail.fields=$scope.fields;
		obj2.code=obj.data.code;obj2.schoolYear=obj.name;
		detail.values['year']=obj2;
		distributionDetail();
	}
	$scope.getAllStu=function(type){
		var detail = $scope.data.stu_detail;
		detail.title  = "获得助学金学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		detail.values[type]=type;
		distributionDetail();
	}
	$scope.getShipType=function(obj,type){
		var detail = $scope.data.stu_detail;
		detail.title  = "获得"+obj.name+"学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		detail.values[type]=obj;
		distributionDetail();
	}
	//下钻
	var distributionDetail = function(){
		var detail = $scope.data.stu_detail;
		detail.formConfig = {
				title : detail.title,
				show  : true,
				url       : $scope.data.detailUrl,
				exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
				heads  : detail.headers,
				fields : detail.fields,
				params : stuDetailParam()
			};
			$timeout(function(){});
		//getStuDetail();
	};
	/*var getStuDetail = function() {
		var detail = $scope.data.stu_detail;
		showLoading();
		service.getStuDetail(stuDetailParam(),function(data) {
			hideLoading();
				    detail.list = data.items;
					detail.page.sumcount = data.total;
					detail.page.pagecount = data.pagecount;
					detail.mask = false;
					detail.detail_show = true;
				},hideLoading);
	};
	*/
	/**
	 * 显示年处理
	 */
	var formatDateName = function(){
		var $data = $scope.data;
		if($data.value_year == $data.thisYear){
			$data.dateName = '今年';
		}else if($data.value_year == $data.lastYear){
			$data.dateName = '去年';
		}
	}

	service.getBzdm().then(function(data){
		var $data = $scope.data;
		$data.bzdm_xn  = data.xn;
		$data.bzdm_edu = data.EDU_ID_STU;
		var bzdm_first = $data.bzdm_xn[0], id_xn = bzdm_first.id;
		$data.dateName   = bzdm_first.mc;
		$data.value_year = id_xn;
		$data.value_edu  = $data.bzdm_edu[0].id;
		$data.thisYear   = id_xn;
		var year = Number(id_xn.substr(0,4))-1;
		$data.lastYear   = year+'-'+(year+1);
		formatDateName();
	});
	$scope.changXn = function(id, data){
		$scope.data.dateName   = data ? data.mc : '';
		$scope.data.value_year = id;
		refreshData();
		formatDateName();
	}
	$scope.changEdu = function(id, data){
		$scope.data.value_edu = id;
		refreshData();
	}
	
	var getAbstractFn = function(){
		service.getAbstract($scope.data.value_year, $scope.data.value_edu, getAdvancedParam()).then(function(data){
			$scope.data.abstract_data = data;
		});
	}
	
	var getTypeListFn = function(){
		service.getTypeList($scope.data.value_year, $scope.data.value_edu, getAdvancedParam(), function(list, option){
			addTitleOption(option,{text:'各助学金对比',show:false});
			$scope.data.type = {
				list  : list,
				radar : option
			}
		});
	}

	var deptData_config = {
		config : {
			xAxis : [{ axisLabel : { rotate : -15 } }]
		}
	};
	var queryDeptDataListFn = function(){
		service.queryDeptDataList($scope.data.value_year, $scope.data.value_edu, getAdvancedParam(), function(bzdm, list, deptMc){
			var id = bzdm[0]["id"],
				option = service.getStackOptionByColumn(list, id, bzdm[0]["unit"], null, deptData_config);
			addTitleOption(option,{text:'助学金'+deptMc+'分布（'+bzdm[0]["mc"]+'）',show:false});
			addNoDataText2Option(option, '暂无'+deptMc+'分布数据');
			$scope.data.deptData = {
				id   : id,
				bzdm : bzdm,
				list : list,
				deptMc : deptMc,
				option : option
			}
			Ice.apply(option, {config:{on:['CLICK', deptDetail]}});
		});
	}
	$scope.changeDeptData = function(id,obj){
		var deptData = $scope.data.deptData, id = obj.id,
			option = service.getStackOptionByColumn(deptData.list, id, obj.unit, null, deptData_config);
		addTitleOption(option,{text:'助学金'+deptData.deptMc+'分布（'+obj.mc+'）',show:false});
		addNoDataText2Option(option, '暂无'+deptData.deptMc+'分布数据');
		deptData.id = id;
		deptData.option = option;
	}
	
	var getBehaviorFn = function(){
		service.getBehavior($scope.data.value_year, $scope.data.value_edu, getAdvancedParam(), function(list, option){
			addTitleOption(option,{text:'获得助学金学生行为',show:false});
			$scope.data.behavior = {
				list  : list,
				radar : option
			}
		});
	}
	
	var getCoverageGradeFn = function(){
		service.getCoverageGrade($scope.data.value_year, $scope.data.value_edu, getAdvancedParam(), function(data){
			$scope.data.coverageGrade = data;
		});
	}

	var coverageDept_config = {
		stack  : false,
		config : {
			legend : {show:false},
			xAxis  : [{ axisLabel : { rotate : -15 } }]
		}
	};
	var getCoverageDeptFn = function(){
		showLoading();
		service.getCoverageDept($scope.data.value_year, $scope.data.value_edu, getAdvancedParam(), function(bzdm, list, deptMc){
			hideLoading();
			var bzdm0 = bzdm[0], id = bzdm0['id'], mc = bzdm0['mc'], unit = bzdm0['unit']||'%',
				option = service.getStackOptionByColumn(list, id, unit, mc, coverageDept_config);
			addTitleOption(option,{text:'助学金'+deptMc+'累计覆盖率（'+mc+'）',show:false});
			addNoDataText2Option(option, '暂无'+deptMc+'覆盖率数据');
			$scope.data.coverageDept = {
				id   : id,
				mc   : mc,
				bzdm : bzdm,
				list : list,
				deptMc : deptMc,
				option : option
			}
			// 判断其他提示信息
			getCoverageDeptInfoFn(option);
		});
	}
	$scope.changeCoverageDept = function(id,obj){
		var coverageDept = $scope.data.coverageDept, id = obj.id;
		coverageDept.id = id;
		coverageDept.mc = obj.mc;
		var option = service.getStackOptionByColumn(coverageDept.list, id, obj['unit']||'%', obj['mc'], coverageDept_config);
		addTitleOption(option,{text:'助学金'+coverageDept.deptMc+'累计覆盖率（'+coverageDept.mc+'）',show:false});
		addNoDataText2Option(option, '暂无'+coverageDept.deptMc+'覆盖率数据');
		coverageDept.option = option;
		// 判断其他提示信息
		getCoverageDeptInfoFn(option);
	}
	/**
	 * 组织机构图表 其他信息获取
	 */
	var getCoverageDeptInfoFn = function(option){
		var sortList = service.getSortDataFromOption(option, true), length = sortList.length,
			coverageDept = $scope.data.coverageDept;
		coverageDept.id_min = sortList[0].id;
		coverageDept.mc_min = sortList[0].mc;
		coverageDept.id_max = sortList[length-1].id;
		coverageDept.mc_max = sortList[length-1].mc;
	}
	
	var history_config = {
		xname_ary : ['学年']
	}
	var getHistoryFn = function(){
		service.getHistory($scope.data.value_year, $scope.data.value_edu, getAdvancedParam(), function(bzdm, list){
			var id = bzdm[0]["id"],
				option = service.getStackOptionByColumn(list, id, bzdm[0]["unit"], null, history_config);
			addTitleOption(option,{text:'历年助学金变化（'+bzdm[0]["mc"]+'）',show:false});
			addNoDataText2Option(option, '暂无历年数据');
			$scope.data.history = {
				id   : id,
				bzdm : bzdm,
				list : list,
				option : option
			}
			Ice.apply(option, {config:{on:['CLICK', yearDetail]}});
		});
	}
	$scope.changeHistory = function(id,obj){
		var history = $scope.data.history, id = obj.id,
			option = service.getStackOptionByColumn(history.list, id, obj.unit, null, history_config);
		addTitleOption(option,{text:'历年助学金变化（'+obj.mc+'）',show:false});
		addNoDataText2Option(option, '暂无历年数据');
		history.id = id;
		history.option = option;
	}
	
	// 向option中添加 noDataText
	var addNoDataText2Option = function(option, text){
		option.config = option.config || {};
		option.config.noDataText = text;
	}
	// 向option中添加 title
	var addTitleOption = function(option, obj){
		option.config = option.config || {};
		option.config.title=obj
	}

	var refreshData = function(){
		getAbstractFn();
		getTypeListFn();
		queryDeptDataListFn();
		getBehaviorFn();
		getCoverageGradeFn();
		getCoverageDeptFn();
		getHistoryFn();
	}
	refreshData();

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
	
	//换页
	$scope.$watch("data.stu_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.stu_detail.mask = true;
			$scope.data.stu_detail.page.curpage = newVal;
			getStuDetail();
		}
	},true);
	
	$scope.stuDetailDown= function(){
		var param = stuDetailParam();
		param.fileName = $scope.data.stu_detail.title;
		service.getStuDetailDown(param,function(data){
			
		})
	};
	
}]);