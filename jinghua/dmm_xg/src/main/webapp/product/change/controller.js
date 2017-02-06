/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService','$timeout','dialog', function($scope, service, advancedService,$timeout,dialog){
	
	$scope.data = {
		mask : true,
		tip_show : false,
		tag:'dept',
		infoShow:false,
		changeMajor:{},
		changeThis:{},
		changeHistory:{},
		distribute : {},
		history    : {},
		changeSetting : {
			history : {}
		},
		advance : {
			param : null
		},
		detailUrl : "pmsChange/getStuDetail",
		exportUrl : 'pmsChange/down',
		stu_detail : { 
			page : {
				curpage  : 1,
				pagesize : 10,
				sumcount : null
			},
			title  : null,
			values : {},
			fields : [],
			headers: [],
			list   : null,
			mask   : false
		},
	};
	$scope.headers = ['学号','入学年级','姓名','性别','地区','院系','专业','班级','异动时间'];
	$scope.fields = ['no','enroll_grade','name','sexmc','shengmc||shimc||xianmc','deptmc','majormc','classmc','changedate'];
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
		    detail.values['STU_ROLL_CODE'] = 1;
		    detail.values['schoolyear'] = $scope.data.schoolyear;
		//Ice.apply(param, {page:page, values:getJson(detail.values), fields:fields, headers:headers});
			Ice.apply(param, {values:getJson(detail.values), fields:fields, headers:headers});
		return param;
	};
	
	
	/**
	 * 摘要
	 */
	var getStuChangeAbstract = function(){
		service.getStuChangeAbstract(getAdvancedParam(), function(data){
			$scope.data.change_abstract = data;
		});
	}
	/**
	 * 分布 or 历史
	 */
	var getStuChangeOrHistory = function(){
		// 显示历史
		if($scope.data.changeSetting.history.show){ $scope.changeHistory();}
		// 显示分布
		else{ $scope.changeThis(); }
	};
	/**
	 * 组织机构
	 */
	var getDeptStuChange = function(){
		service.getDeptStuChange(getAdvancedParam(), function(data, deptMc){
			$scope.data.deptCfg = data;
			Ice.apply(data, {config:{on:['CLICK', deptDetail]}});//学院
			$scope.data.deptMc  = deptMc;
		});
	}
	/**
	 * 月份
	 */
	var getStuChangeMonth = function(){
		service.getStuChangeMonth(getAdvancedParam(), function(option, info){
			$scope.data.monthCfg = option;
			$scope.data.info = info;
			Ice.apply(option, {config:{on:['CLICK', monthDetail]}});//学院

		});
	}
	/**
	 * 历年
	 */
	var getStuChangeYear = function(){
		service.getStuChangeYear(getAdvancedParam(), function(data){
			$scope.data.yearCfg = data;
			hideLoading();
			Ice.apply(data, {config:{on:['CLICK', yearDetail]}});//学年
		});
	}
	/**
	 * 转专业（转入转出）
	 */
	var isAll=function(){
		service.getIsAll(getAdvancedParam(),function(data){
			$scope.data.btnShow=data;
			var sty=[{id:'dept',mc:'按学院'},{id:'major',mc:'按专业'}];
			$scope.data.sty=sty;
			$scope.data.value_sty=sty[0].id;
			$scope.data.deptMcTwo=sty[0].mc.substring(1,3);
		})
	};
	$scope.changeLevelType=function(id,data){
		$scope.data.tag=id;
		var $data=$scope.data;
		$data.value_sty=id;
		$scope.data.deptMcTwo=data.mc.substring(1,3);
		getStuChangeByDeptOrMajor();
	}
	var getStuChangeByDeptOrMajor = function(){
		if($scope.data.tag=='dept'){$scope.data.infoShow=true;$scope.data.distribution_info='学院内转专业不计入统计'}
		else{$scope.data.infoShow=false}
		$scope.data.changeMajor.mask=true;
		service.getStuChangeByDeptOrMajor($scope.data.tag,getAdvancedParam(),function(option,deptMc){
			$scope.data.changeMajor.mask=false;
			$scope.data.deptMcTwo=deptMc;
			$scope.data.deptOrMajor = {
					option : option
				}
			Ice.apply(option, {config:{on:['CLICK', deptOrMajorDetail]}});
		});
	}

	$scope.changeThis = function(){
		$scope.data.changeSetting.history.show = false;
		$scope.data.changeThis.show=true;
		service.getStuChange(getAdvancedParam(), function(data){
			$scope.data.changeThis.show=false;
			var distribute = $scope.data.distribute;
			distribute.typeCfg    = data.typeCfg;
			Ice.apply(data.typeCfg, {config:{on:['CLICK', typeDetail]}});
			distribute.gradeCfg   = data.gradeCfg;
			Ice.apply(data.gradeCfg, {config:{on:['CLICK', gradeDetail]}});
			distribute.subjectCfg = data.subjectCfg;
			Ice.apply(data.subjectCfg, {config:{on:['CLICK', subjectDetail]}});
			distribute.sexCfg     = data.sexCfg;
			Ice.apply(data.sexCfg, {config:{on:['CLICK', sexDetail]}});
		});
	}
	$scope.changeHistory = function(){
		var changeSettingting = $scope.data.changeSetting;
		changeSettingting.history.show = true;
		if(!changeSettingting.history.rendered){
			$scope.data.changeHistory.show=true;
			service.getStuChangeHistory(getAdvancedParam(), function(type_option, grade_option, subject_option, sex_option){
				$scope.data.changeHistory.show=false;
				var history = $scope.data.history;
				history.typeCfg    = type_option;
				history.gradeCfg   = grade_option;
				history.subjectCfg = subject_option;
				history.sexCfg     = sex_option;
				// rendered
				changeSettingting.history.rendered = true;
			});
		}
	};
	
	
	$scope.getStuDetail=function(type){
		var detail = $scope.data.stu_detail;
		if(type=='allChange'){ detail.title  = "当前在籍生（学籍异动）学生名单";}else{detail.title  = "当前在籍生（学籍特殊异动）学生名单";}
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		detail.values[type]=type;
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
		}
	//type
	var typeDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = "学籍异动（"+param.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'type');
	};
	//grade
	var gradeDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = "学籍异动（"+param.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'grade');
	};
	//subject
	var subjectDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = "学籍异动（"+param.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'subject');
	};
	
	//sex
	var sexDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = "学籍异动（"+param.name+"生）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'sex');
	};
	//dept
	var deptDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"（学籍异动）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'dept');
	};
	//month
	var monthDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"（学籍异动）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'month');
	};
	
	//year
	var yearDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"学年（学籍异动）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'year');
	};
	//deptOrMajorhange
	var deptOrMajorDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"转专业（"+param.seriesName+"）学生名单";
		detail.values = {};
		detail.headers= ['学号','入学年级','姓名','性别','地区','院系','专业','转专业时间'];
		detail.fields = ['no','enroll_grade','name','sexmc','shengmc||shimc||xianmc','deptmc','majormc','changedate'];
		obj.tag=$scope.data.tag;
		param.data = obj;
		distributionDetail(obj,'changeMajor');
	};
	//钻
	/*var getStuDetail = function() {
		var detail = $scope.data.stu_detail;
		showLoading();
		service.getStuChangeDetail(stuDetailParam(),function(data) {
					hideLoading();
				    detail.list = data.items;
					detail.page.sumcount = data.total;
					detail.page.pagecount = data.pagecount;
					detail.mask = false;
					detail.detail_show = true;
				},hideLoading);
	};*/
	
	var distributionDetail = function(param, type){
		var detail = $scope.data.stu_detail;
		detail.page.curpage = 1;
		if(type=='month'){
			detail.values[type]=param.name;
		}else if(type=='subject'){
			detail.values[type]=param.data.id;
		}else if(type=='changeMajor'){
			var obj={};obj.tag=param.tag;obj.id=param.id;obj.code=param.data.code;
			detail.values[type]=obj;
		}else if(param.data.code==null || param.data.code==""){
			detail.values[type]=param.data.name;
		}else {
			detail.values[type] = param.data.code;
		};
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

	// 刷新数据
	var refreshData = function(){
		showLoading();
		// 摘要
		getStuChangeAbstract();
		// 分布 or 历史
		getStuChangeOrHistory();
		// 各机构、 各月、 历年
		getDeptStuChange();
		getStuChangeMonth();
		isAll();
		getStuChangeByDeptOrMajor();
		getStuChangeYear();
	}
	refreshData();
	
	/*$scope.$watch("data.stu_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.stu_detail.mask = true;
			$scope.data.stu_detail.page.curpage = newVal;
			getStuDetail();
		}
	},true);*/
	/** 概况信息 点击事件 */
	$scope.stuDetailClick = function(type, title, seniorCode){
		var detail = $scope.data.stu_detail;
		detail.page.curpage = 1;
		detail.title = title;
		detail.values= {};
		detail.values[type] = seniorCode||null;
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		getStuDetail();
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
	
	$scope.stuDetailDown= function(){
		var param = stuDetailParam();
		param.fileName = $scope.data.stu_detail.title;
		service.getStuDetailDown(param,function(data){
			
		})
	};

}]);