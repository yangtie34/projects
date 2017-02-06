
/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', '$timeout', 'dialog', 
                           function($scope, service, advancedService, $timeout, dialog){

	$scope.data = {
		mask : true,
		param   : {},
		advance : { param : null },
		model   : { lx_id : null, lx_mc : null},
		lxAry   : [{id:11,mc:'专任教师'},{id:null,mc:'全校教师'}],
		technical : {},
		degree    : {},
		edu       : {},
		detailUrl : "pmsTeacherGroup/getTeaDetail",
		exportUrl : 'pmsTeacherGroup/down',
		abstract_detail : { // 详情参数
			formConfig : { show : false },
			page : {
				curpage  : 1,
				pagesize : 10,
				sumcount : null
			},
			title  : null,
			values : {},
			fields : [],
			headers: [],
			order  : null,
			list   : null,
			mask   : false
		},
		distribution_detail : { // 分布参数
			values : {}
		},
		dept_detail : { // 组织机构参数
			values : {}			
		}
	};
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	var showLoading = function(){ dialog.showLoading(); };
	var hideLoading = function(){ dialog.hideLoading(); };
	
	/**
	 * { schoolYear:'', termCode:'', edu:'', param:'' }
	 */
	var getParam = function(){
		var obj = Ice.apply({},$scope.data.param);
		obj.param = getAdvancedParam();
		obj.lx = $scope.data.model.lx_id;
		return obj;
	}
	/**
	 * 获取分页参数
	 */
	var getJson = function(obj){
		return JSON.stringify(obj);
	}
	/** 教职工 分析类型（专任教师、全部教师） */
	var setTeaLx = function(obj){
		var model = $scope.data.model;
		model.lx_id = obj.id;
		model.lx_mc = obj.mc;
	}
	$scope.changeTeaLx = function(obj){
		setTeaLx(obj);
		refreshDistribution();
	};
	(function(){
		setTeaLx($scope.data.lxAry[0]);
	})();
	
	
	/**
	 * 概况信息
	 */
	var getAbstract = function(){
		service.getAbstract(getParam(), function(data, senior){
			$scope.data.abstract_data = data;
			$scope.data.senior = senior;
		});
	}
	/** 高级人才 翻页 */
	$scope.changeSenior = function(tag){
		var senior = $scope.data.senior;
		senior.index = senior.index+tag;
	}
	
	/** 概况信息 点击事件 */
	$scope.abstractDetailClick = function(type, title, seniorCode){
		var detail = $scope.data.abstract_detail;
		detail.title = title +'名单';
		detail.values= {};
		detail.values[type] = seniorCode||null;
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		detail.formConfig = {
			title : detail.title,
			show  : true,
			url       : $scope.data.detailUrl,
			exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
			heads  : detail.headers,
			fields : detail.fields,
			params : abstractDetailParam()
		};
		$timeout(function(){});
	}
	/** 参数 */
	var abstractDetailParam = function(){
		var detail = $scope.data.abstract_detail,
			param  = getParam(),
//			page   = getJson(detail.page), // 组件内自动添加分页参数，这里再添加会影响查询
			values = getJson(detail.values),
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
//		Ice.apply(param, {page:page, values:values, fields:fields, headers:headers, order:detail.order});
		Ice.apply(param, {values:values, fields:fields, headers:headers});
		return param;
	}
	
	
	/**
	 * 分布信息
	 */
	var getDistribution = function(){
		service.getDistribution(getParam(), function(technicalOption, technicalScale, degreeOption, degreeScale, eduOption, eduScale){
			var technical = $scope.data.technical,
				degree    = $scope.data.degree,
				edu       = $scope.data.edu;
			technical.optionThis = technicalOption;
			technical.option = technicalOption;
			technical.scale  = technicalScale;
			technical.showTag= false;
			degree.optionThis = degreeOption;
			degree.option = degreeOption;
			degree.scale  = degreeScale;
			degree.showTag= false;
			edu.optionThis = eduOption;
			edu.option = eduOption;
			edu.scale  = eduScale;
			edu.showTag= false;
			// 职称等级分布
			Ice.apply(technicalOption, {config:{on:['CLICK', technicalDetail]}});
			Ice.apply(degreeOption, {config:{on:['CLICK', degreeDetail]}});
			Ice.apply(eduOption, {config:{on:['CLICK', eduDetail]}});
		});
	}
	/** 职称级别 详情 */
	var technicalDetail = function(param, obj, data){
		var detail = $scope.data.distribution_detail;
		detail.title  = $scope.data.model.lx_mc +'（'+param.name+'职称）教师名单';
		detail.headers= ['工号','姓名','性别','部门','职称'];
		detail.fields = ['tea_no','name','sexmc','deptmc','zcdjmc'];
		distributionDetailBefore(param, 'technical');
	}
	/** 学位 详情 */
	var degreeDetail = function(param){
		var detail = $scope.data.distribution_detail;
		detail.title  = $scope.data.model.lx_mc +'（'+param.name+'学位）教师名单';
		detail.headers= ['工号','姓名','性别','部门','学位'];
		detail.fields = ['tea_no','name','sexmc','deptmc','degreemc'];
		distributionDetailBefore(param, 'degree');
	}
	/** 学历 详情 */
	var eduDetail = function(param){
		var detail = $scope.data.distribution_detail;
		detail.title  = $scope.data.model.lx_mc +'（'+param.name+'学历）教师名单';
		detail.headers= ['工号','姓名','性别','部门','学历'];
		detail.fields = ['tea_no','name','sexmc','deptmc','edumc'];
		distributionDetailBefore(param, 'edu');
	}
	/** 分布 详情-前 */
	var distributionDetailBefore = function(param, type){
		var detail = $scope.data.distribution_detail;
		detail.values = {};
		detail.values[type] = param.data.code;
		detail.formConfig = {
			title : detail.title,
			show  : true,
			url       : $scope.data.detailUrl,
			exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
			heads  : detail.headers,
			fields : detail.fields,
			params : distributionDetailParam()
		};
		$timeout(function(){});
	}
	/** 参数 */
	var distributionDetailParam = function(){
		var detail = $scope.data.distribution_detail,
			param  = getParam(),
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		detail.values['AUTHORIZED_STRENGTH_ID'] = param.lx;
		Ice.apply(param, {values:getJson(detail.values), fields:fields, headers:headers});
		return param;
	}
	
	/** 分布切换 */
	var changeHistoryOrThis = function(obj, historyFn){
		if(obj.showTag){
			obj.option = obj.optionThis;
			obj.showTag = false;
		}else{
			historyFn();
			obj.showTag = true;
		}
	}
	/** 职称级别历史分布 */
	var getHistoryTechnical = function(){
		var config = $scope.data.technical;
		config.mask = true;
		service.getHistoryTechnical(getParam(), function(option){
			config.mask = false;
			config.option = option;
		});
	}
	$scope.changeHistoryTechnical = function(){
		changeHistoryOrThis($scope.data.technical, getHistoryTechnical);
	}
	/** 学位历史分布 */
	var getHistoryDegree = function(){
		var config = $scope.data.degree;
		config.mask = true;
		service.getHistoryDegree(getParam(), function(option){
			config.mask = false;
			config.option = option;
		});
	}
	$scope.changeHistoryDegree = function(){
		changeHistoryOrThis($scope.data.degree, getHistoryDegree);
	}
	/** 学历历史分布 */
	var getHistoryEdu = function(){
		var config = $scope.data.edu;
		config.mask = true;
		service.getHistoryEdu(getParam(), function(option){
			config.mask = false;
			config.option = option;
		});
	}
	$scope.changeHistoryEdu = function(){
		changeHistoryOrThis($scope.data.edu, getHistoryEdu);
	}

	/**
	 * 学科师资分组
	 */
	var getSubjectGroup = function(){
		service.getSubjectGroup(getParam(), function(data){
			$scope.data.subjectOption = data;
			Ice.apply(data, {config:{on:['CLICK', subjectDetail]}});
		});
	}
	/** 学科 详情 */
	var subjectDetail = function(param, obj, data){
		var detail = $scope.data.distribution_detail;
		detail.title  = $scope.data.model.lx_mc +'（学科：'+param.name+'）教师名单';;
		detail.headers= ['工号','姓名','性别','部门','学科'];
		detail.fields = ['tea_no','name','sexmc','deptmc','subjectmc'];
		param.data = obj;
		distributionDetailBefore(param, 'subject');
	}
	
	/**
	 * 年龄分组
	 */
	var getAgeGroup = function(){
		service.getAgeGroup(getParam(), function(data, scale){
			$scope.data.ageOption = data;
			$scope.data.ageScale = scale;
			// 事件：
			Ice.apply(data, {config:{on:['CLICK', ageDetail]}});
		});
	}
	/** 年龄详情 */
	var ageDetail = function(param, obj, data){
		var detail = $scope.data.distribution_detail;
		detail.title  = $scope.data.model.lx_mc +'（年龄：'+param.name+'）教师名单';;
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetailBefore(param, 'age');
	}
	
	/**
	 * 教龄分组
	 */
	var getSchoolAgeGroup = function(){
		service.getSchoolAgeGroup(getParam(), function(data){
			$scope.data.schoolAgeOption = data;
			// 事件：
			Ice.apply(data, {config:{on:['CLICK', schoolAgeDetail]}});
		});
	}
	/** 教龄详情 */
	var schoolAgeDetail = function(param, obj, data){
		var detail = $scope.data.distribution_detail;
		detail.title  = $scope.data.model.lx_mc +'（教龄：'+param.name+'）教师名单';;
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetailBefore(param, 'schoolage');
	}
	
	/**
	 * 组织机构分组
	 */
	var getDeptList = function(){
		showLoading();
		service.getDeptList(getParam(), function(data){
			hideLoading();
			// 事件：图例切换、点击
			data.config.on = [['LEGEND_SELECTED', deptLegendSelectedFn],
			                  ['CLICK', deptDetailClick]]
			$scope.data.deptOption = data;
		});
	}
	/** 组织机构 详情 */
	var deptDetailClick = function(param, obj, data){
		// 人数、高职称、高学历、高学位
		var seriesIndex = param.seriesIndex;
		if(seriesIndex < 4){
			var detail = $scope.data.dept_detail,
				values = detail.values,
				code   = obj.data.code;
			detail.title = $scope.data.model.lx_mc +'（'+param.name+'，'+param.seriesName+'）教师名单';
			detail.headers= ['工号','姓名','性别','部门'];
			detail.fields = ['tea_no','name','sexmc','deptmc'];
			// 处理参数 ：组织机构...
			values.DEPT_ID = obj.id;
			if(code){
				var key;
				switch (seriesIndex) {
				case 1:
					key = 'High_Technical';
					break;
				case 2:
					key = 'High_Edu';
					break;
				case 3:
					key = 'High_Degree';
					break;
				}
				if(key) values[key] = code;
			};
			detail.formConfig = {
				title : detail.title,
				show  : true,
				url       : $scope.data.detailUrl,
				exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
				heads  : detail.headers,
				fields : detail.fields,
				params : deptDetailParam()
			};
			$timeout(function(){});
		}
	}
	/** 参数 */
	var deptDetailParam = function(){
		var detail = $scope.data.dept_detail,
			param  = getParam(),
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		detail.values['AUTHORIZED_STRENGTH_ID'] = param.lx;
		Ice.apply(param, {values:getJson(detail.values), fields:fields, headers:headers});
		return param;
	}
	/** 组织机构 图例切换 */
	var deptLegendSelectedFn = function(param){
    	var option = $scope.data.deptOption;
    	var target = param.target;
    	if(target == '平均年龄'){
    		option.yAxis[0].name = '岁';
    	}else if(target == '平均教龄'){
    		option.yAxis[0].name = '年';
    	}else{
    		option.yAxis[0].name = '人';
    	}
    	$timeout(function(){
    		$scope.data.deptOption = option;
        })
    }
	
	/**
	 * 历史分组
	 */
	var getHistoryList = function(){
		service.getHistoryList(getParam(), function(data){
			data.config.on = [['LEGEND_SELECTED',historyLegendSelectedFn]]
			$scope.data.historyOption = data;
		});
	}
	var historyLegendSelectedFn = function(param){
    	var option  = $scope.data.historyOption,
    		yAxis_0 = option.config.yAxis[0];
    	var target = param.target;
    	yAxis_0.min = 0;
    	if(target == '平均年龄'){
    		yAxis_0.name = '岁';
        	yAxis_0.min = 20;
    	}else if(target == '平均教龄'){
    		yAxis_0.name = '年';
    	}else if(target == '人数'){
    		yAxis_0.name = '人';
    		yAxis_0.min  = 500;
    	}else{
    		yAxis_0.name = '人';
    	}
    	$timeout(function(){
    		$scope.data.historyOption = option;
        })
    }
	
	/**
	 * 各机构生师比
	 */
	var getDeptScaleList = function(){
		service.getDeptScaleList(getParam(), function(data, deptMc){
			$scope.data.deptScaleOption = data;
			$scope.data.deptMc = deptMc;
		});
	}
	
	
	/** 刷新中间部分的分布 */
	var refreshDistribution = function(){
		getDistribution();
		getAgeGroup();
		getSchoolAgeGroup();
		getSubjectGroup();
		getDeptList();
		getHistoryList();
	}
	/** 刷新所有 */
	var refreshData = function(){
		getAbstract();
		refreshDistribution();
		getDeptScaleList();
	}
	refreshData();
	
	
	service.getAdvance(function(data){
		advancedService.checkedFirst(data);
		$scope.data.advance.source = data;
	});
	/**
	 * 高级查询-切换show
	 */
	$scope.advanceShow = function(){
		$scope.data.advance.show = !$scope.data.advance.show;
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
	
}]);