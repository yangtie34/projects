
/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', '$timeout', 'dialog', 
                           function($scope, service, advancedService, $timeout, dialog){
	var worker_base            = {}, // 学生工作者基础数据
		worker_distribute      = {}, // 学生工作者分布数据
		instructors_distribute = {}, // 专职辅导员分布数据
		name_worker = '学生工作者',
		name_zzfdy  = '专职辅导员'
	$scope.data = {
		mask : true,
		scale_jyb  : null, //教育部要求比例
		scale_this : null, //当前比例
		worker_base            : worker_base,
		worker_distribute      : worker_distribute,
		instructors_distribute : instructors_distribute,
		advance : {
			param : null
		},
		detailUrl : "pmsWorker/getTeaDetail",
		exportUrl : 'pmsWorker/down',
		worker_detail : { 
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
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	var showLoading = function(){ dialog.showLoading(); };
	var hideLoading = function(){ dialog.hideLoading(); };
	
	/**
	 * 学生工作者
	 */
	// 下钻
	var detailWorker = function(param,obj,data){
		if(!obj.code){
			return null;
		}
		var detail = $scope.data.worker_detail;
		detail.title  = param.name + '名单';
		detail.values = {};
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'worker');
	};
	var zcWorkerDetail = function(param,obj,data){
		var detail = $scope.data.worker_detail;
		detail.title  = name_worker +'（' +param.name+ '职称）名单';
		detail.values = {};
		detail.values['worker'] = 'ALL';
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'technical');
	};
	var degreeWorkerDetail = function(param,obj,data){
		var detail = $scope.data.worker_detail;
		detail.title  = name_worker +'（' +param.name + '学位）名单';
		detail.values = {};
		detail.values['worker'] = 'ALL';
		detail.values['degree'] = obj.degree_id_pid==null?'null':obj.degree_id_pid;
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'');
	};
	var sexWorkerDetail = function(param,obj,data){
		var detail = $scope.data.worker_detail;
		detail.title  = name_worker +'（性别：' + param.name + '）名单';
		detail.values = {};
		detail.values['worker'] = 'ALL';
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'SEX_CODE');
	};
	var ageWorkerDetail = function(param,obj,data){
		var detail = $scope.data.worker_detail;
		detail.title  = name_worker +'（' +param.name+ '）名单';
		detail.values = {};
		detail.values['worker'] = 'ALL';
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'age');
	};
	var zcFdyDetail = function(param,obj,data){
		var detail = $scope.data.worker_detail;
		detail.title  = name_zzfdy +'（' +param.name+ '职称）名单';
		detail.values = {};
		detail.values['worker'] = $scope.instructors_code;
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'technical');
	};
	var degreeFdyDetail = function(param,obj,data){
		var detail = $scope.data.worker_detail;
		detail.title  = name_zzfdy +'（' +param.name + '学位）名单';
		detail.values = {};
		detail.values['worker'] = $scope.instructors_code;
		detail.values['degree'] = obj.degree_id_pid==null?'null':obj.degree_id_pid;
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'');
	};
	var sexFdyDetail = function(param,obj,data){
		var detail = $scope.data.worker_detail;
		detail.title  = name_zzfdy +'（性别：' + param.name + '）名单';
		detail.values = {};
		detail.values['worker'] = $scope.instructors_code;
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'SEX_CODE');
	};
	var ageFdyDetail = function(param,obj,data){
		var detail = $scope.data.worker_detail;
		detail.title  = name_zzfdy +'（' +param.name+ '）名单';
		detail.values = {};
		detail.values['worker'] = $scope.instructors_code;
		detail.headers= ['工号','姓名','性别','部门'];
		detail.fields = ['tea_no','name','sexmc','deptmc'];
		param.data = obj;
		distributionDetail(param,'age');
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
	/** 参数 */
	var workerDetailParam = function(){
		var detail = $scope.data.worker_detail,
			param  = getParam(),
			values = getJson(detail.values),
//			page   = getJson(detail.page),
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
//		Ice.apply(param, {page:page, values:values, fields:fields, headers:headers});
		Ice.apply(param, {values:values, fields:fields, headers:headers});
		return param;
	};
	var distributionDetail = function(param, type){
		var detail = $scope.data.worker_detail;
		detail.values[type] = param.data.code==null?'null':param.data.code;
		detail.formConfig = {
			title : detail.title,
			show  : true,
			url       : $scope.data.detailUrl,
			exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
			heads  : detail.headers,
			fields : detail.fields,
			params : workerDetailParam()
		};
		$timeout(function(){});
	};
	/*var getTeaDetail = function(){
		var detail = $scope.data.worker_detail;
		showLoading();
		service.getTeaDetail(workerDetailParam(),function(data){
			hideLoading();
		    detail.list = data.items;
			detail.page.sumcount = data.total;
			detail.page.pagecount = data.pagecount;						
			detail.mask = false;
			detail.detail_show = true;
			}, hideLoading);
	};*/
	/*$scope.$watch("data.worker_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.worker_detail.mask = true;
			$scope.data.worker_detail.page.curpage = newVal;
			getTeaDetail();
		}
	},true);*/
	
	var getWorker = function(){
		service.getWorker(getAdvancedParam(), function(scale, option){
			// 专职辅导员学生比例
			$scope.data.scale_jyb  = scale[0];
			$scope.data.scale_this = scale[1];
			var on = ['CLICK', detailWorker];
			Ice.apply(option, {config:{on:on}});
			worker_base.option = option;
		});
	}
	/**
	 * 学生工作者分布
	 */
	var getWorkerDistribute = function(){
		service.getWorkerDistribute(getAdvancedParam(), function(wkZcCfg, wkDegreeCfg, wkAgeCfg, wkSexCfg){
			// 职称
			worker_distribute.wkZcCfg = wkZcCfg;
			// 学位
			worker_distribute.wkDegreeCfg = wkDegreeCfg;
			// 年龄分布
			worker_distribute.wkAgeCfg = wkAgeCfg;
			// 性别
			worker_distribute.wkSexCfg = wkSexCfg;
			//echarts 点击事件
			Ice.apply(wkZcCfg, {config:{on:['CLICK', zcWorkerDetail]}});
			Ice.apply(wkDegreeCfg, {config:{on:['CLICK', degreeWorkerDetail]}});
			Ice.apply(wkAgeCfg, {config:{on:['CLICK', ageWorkerDetail]}});
			Ice.apply(wkSexCfg, {config:{on:['CLICK', sexWorkerDetail]}});
		});
	}
	/**
	 * 专职辅导员分布
	 */
	var getInstructorsDistribute = function(){
		service.getInstructorsDistribute(getAdvancedParam(), function(wkZcCfg, wkDegreeCfg, wkAgeCfg, wkSexCfg,code){
			// 职称
			instructors_distribute.wkZcCfg = wkZcCfg;
			// 学位
			instructors_distribute.wkDegreeCfg = wkDegreeCfg;
			// 年龄分布
			instructors_distribute.wkAgeCfg = wkAgeCfg;
			// 性别
			instructors_distribute.wkSexCfg = wkSexCfg;
			$scope.instructors_code = code;
			//echarts点击
			Ice.apply(wkZcCfg, {config:{on:['CLICK', zcFdyDetail]}});
			Ice.apply(wkDegreeCfg, {config:{on:['CLICK', degreeFdyDetail]}});
			Ice.apply(wkAgeCfg, {config:{on:['CLICK', ageFdyDetail]}});
			Ice.apply(wkSexCfg, {config:{on:['CLICK', sexFdyDetail]}});
		});
	}
	/**
	 * 各机构专职辅导员管理学生数
	 */
	var getOrganizationInstructorsStuRatio = function(){
		showLoading();
		service.getOrganizationInstructorsStuRatio(getAdvancedParam(), null, function(info, option, deptMc){
			hideLoading();
			instructors_distribute.info = info;
			instructors_distribute.wkDeptCfg = option;
			instructors_distribute.deptMc = deptMc;
		});
	}
	
	var refreshData = function(){
		getWorker();
		getWorkerDistribute();
		getInstructorsDistribute();
		getOrganizationInstructorsStuRatio();
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
	$scope.workerDetailDown= function(){
		var param = workerDetailParam();
		param.fileName = $scope.data.worker_detail.title;
		service.down(param,function(data){
			
		})
	};
	
}]);