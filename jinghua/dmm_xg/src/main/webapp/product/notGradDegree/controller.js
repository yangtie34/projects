/**
 * 无法毕业无学位学生
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','$timeout','service','advancedService','dialog', function($scope,$timeout, service,advancedService,dialog){
	$scope.changePie=true;
	$scope.changeLine=false;
	$scope.data = {
			mask : [false,false,false,false,false],
			nedegreeCode:25,
			value_xw : '1', // 选中的年id
			advance : {
				param : null
			},
			detailUrl : "pmsNotGradDegree/getStuDetail",
			exportUrl : 'pmsNotGradDegree/down',
			model   : {},
			distribution_detail : { // 分布参数
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
			abstract_detail : { // 详情参数
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
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; };
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; };
	var showLoading = function(){ dialog.showLoading();};
	var hideLoading = function(){ dialog.hideLoading(); };
	var partshowLoading = function(sort){for(var i=0;i<$scope.data.mask.length;i++){$scope.data.mask[i]=false;} $scope.data.mask[sort] = true; $timeout(function(){});};
	var parthideLoading = function(){ for(var i=0;i<$scope.data.mask.length;i++){$scope.data.mask[i]=false;};$timeout(function(){}); };
	/**
	 * { schoolYear:'', termCode:'', edu:'', param:'' }
	 */
	var getParam = function(){
		var obj = Ice.apply({},$scope.data.param);
		obj.param = getAdvancedParam();
		obj.lx = $scope.data.model.lx_id;
		return obj;
	};
	/**
	 * 获取分页参数
	 */
	var getJson = function(obj){
		return JSON.stringify(obj);
	};
	//得到无法毕业无学位证类型
	var getNoDegreeType=function(){
		showLoading();
		service.getNoDegreeType(function(data){//得到无学位证学生学科分布
			var $data = $scope.data;
			$data.bzdm_xw = data.xw;//类型
			$scope.data.nedegreeCode=data.nedegreeCode;
			$data.value_xs = $data.bzdm_xw[0].id;//初始化类型值
		});
		getxffbData($scope.data.value_xw);
	};
	//改变类型
	$scope.changType = function(id, data){//改变类型
		$scope.data.value_xw = id;
		getxffbData($scope.data.value_xw);
	};
	var getNoDegreeData=function(){
		service.queryXwInfo(getAdvancedParam(),function(data){//得到无法毕业人数及无学位学生人数
			$scope.getxwCfg=data;
		});
		service.queryXkfb(getAdvancedParam(),function(data){//得到无学位证学生学科分布
//			data.config.on=['CLICK',function (param){
//				abstractDetailAll('submc',param.name,param.name,'学科');
//				}];
			$scope.getXkfbCfg=data;
			Ice.apply(data, {config:{on:['CLICK', function (param){
				abstractDetailAll('submc','无学位（学科：'+param.name+'）',param.name,'学科');
				}]}});
		});
		service.queryNjfb(getAdvancedParam(),function(data){//得到无学位证学生年级分布
			$scope.getNjfbCfg=data;
			Ice.apply(data, {config:{on:['CLICK', function (param){
				abstractDetailAll('grademc','无学位（年级：'+param.name+'）',param.name,'年级');
				}]}});
		});
		service.queryXbfb(getAdvancedParam(),function(data){//得到无学位证学生性别分布
			$scope.getXbfbCfg=data;
			Ice.apply(data, {config:{on:['CLICK', function (param){
				abstractDetailAll('sexmc','无学位（性别：'+param.name+'）',param.name);
				}]}});
		});
		service.queryYyfb(getAdvancedParam(),function(data){//得到无学位证学生原因分布
			$scope.getYyfbCfg=data;
			Ice.apply(data, {config:{on:['CLICK', function (param){
				abstractDetailAll('reasonmc','无学位（原因：'+param.name+'）',param.name,'原因');
				}]}});
//			hideLoading();s
		});
	};
	var getYearData=function(){
		/**
		 * 得到最近几年的数据分布(学生状态,学生年级,学科,学生性别)
		 */
		partshowLoading(1);
		service.queryStatefbByYear(getAdvancedParam(),function(data){//得到无学位证学生状态分布
			data.config.on=['CLICK',function (param){
				abstractDetailAll('reasonmc',param.name+'学年（原因：'+param.seriesName+'）',(param.name+':'+param.seriesName));
				}];
			$scope.getStatefbByYearCfg=data;
		});
		service.queryNjfbByYear(getAdvancedParam(),function(data){//得到无学位证学生年级分布
			data.config.on=['CLICK',function (param){
				abstractDetailAll('grademc',param.name+'学年（年级：'+param.seriesName+'）',(param.name+':'+param.seriesName),'年级');
				}];
			$scope.getNjfbByYearCfg=data;
		});
		service.queryXkfbByYear(getAdvancedParam(),function(data){//得到无学位证学生学科分布
			data.config.on=['CLICK',function (param){
				abstractDetailAll('submc',param.name+'学年（学科：'+param.seriesName+'）',(param.name+':'+param.seriesName),'学科');
				}];
			$scope.getXkfbByYearCfg=data;
		});
		service.queryXbfbByYear(getAdvancedParam(),function(data){//得到无学位证学生性别分布
			data.config.on=['CLICK',function (param){
				abstractDetailAll('sexmc',param.name+'学年（'+param.seriesName+'）',(param.name+':'+param.seriesName),'性别');
				}];
			$scope.getXbfbByYearCfg=data;
			hideLoading();
			parthideLoading();
		});
		
	};
	var getxffbData=function(fb){//得到无法毕业和无学位学生人数及比例
//		showLoading();
		partshowLoading(0);
	service.queryXwfbAndRatio(fb,getAdvancedParam(),function(data){
		Ice.apply(data, {config:{on:['CLICK', function (param){
			var xName;
			if($scope.data.value_xw==1){xName='无学位'}else{xName='无法毕业'};
			abstractDetailAll('deptmc',param.name+'（'+xName+'）',param.name+','+$scope.data.value_xw);
			}]}});
		parthideLoading();
		hideLoading();
		$scope.XffbCfg=data;
	});			
};
	getNoDegreeData();
	getNoDegreeType();
//	getYearData();
//	getxffbData('');
	$scope.xffbType=function(fb){
		getxffbData(fb);
	};
	//面积图与折现图切换
	$scope.changeType=function(lx){
		getNoDegreeData();
		if(lx=='pie'){
			$scope.changePie=true;
			$scope.changeLine=false;
			getNoDegreeData();
			hideLoading();
		}else if(lx=='line'){
			$scope.changePie=false;
			$scope.changeLine=true;	
			getYearData();
		}
	};
	/**
	 * 高级查询-change事件
	 */
	$scope.advanceChange = function(data){
		var param = advancedService.getParam(data);
		// 高级查询-参数
		setAdvancedParam(param);
		showLoading();
		getNoDegreeData();
		getYearData();
//		getxffbData($scope.data.value_xw);
		service.queryXwfbAndRatio($scope.data.value_xw,getAdvancedParam(),function(data){
			Ice.apply(data, {config:{on:['CLICK', function (param){
				var xName;
				if($scope.data.value_xw==1){xName='无学位'}else{xName='无法毕业'};
				abstractDetailAll('deptmc',param.name+'（'+xName+'）',param.name+','+$scope.data.value_xw);
				}]}});
			parthideLoading();
			hideLoading();
			$scope.XffbCfg=data;
		});	
//		refreshData();
	};
	//加载高级查询组件
	service.getAdvance(function(data){
		$scope.data.advance.source = data;
	});
	/** 概况信息 点击事件 */
	$scope.abstractDetailClick = function(type, title, seniorCode){
		var detail = $scope.data.abstract_detail;
		detail.page.curpage = 1;
		detail.title = title;
		detail.values= {};
		detail.values[type] = seniorCode||null;
		detail.headers= ['学号','姓名','性别','院系','专业','班级','挂科学分','原因'];
		detail.fields = ['no','name','sexmc','deptmc','majormc','classmc','credits','reasonmc'];
		detail.formConfig = {
				title : title,
				show  : true,
				url       : $scope.data.detailUrl,
				exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
				heads  : detail.headers,
				fields : detail.fields,
				params : abstractDetailParam()
			};
		hideLoading();
		$timeout(function(){});
		//		abstractDetail();
	};
	/** 参数 */
	var abstractDetailParam = function(){
		var detail = $scope.data.abstract_detail,
			param  = getParam(),
//			page   = getJson(detail.page),
			values = getJson(detail.values),
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		Ice.apply(param, {values:values, fields:fields, headers:headers});
//		Ice.apply(param, {page:page, values:values, fields:fields, headers:headers});
		return param;
	};
	/** 请求 */
	var abstractDetail = function(){
		var detail = $scope.data.abstract_detail;
		service.getStuDetail(abstractDetailParam(), function(data){
			detail.list = data.rows;
			detail.page.sumcount = data.total;
			detail.page.pagecount = data.pagecount;
			detail.mask = false;
			detail.detail_show = true;
			hideLoading();
		},hideLoading);
	};
	/** 翻页 */
	$scope.$watch("data.abstract_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.abstract_detail.mask = true;
			$scope.data.abstract_detail.page.curpage = newVal;
			abstractDetail();
		}
	},true);
	// down
	$scope.abstractDetailDown = function(){
		var param = abstractDetailParam();
		param.fileName = $scope.data.abstract_detail.title;
		service.getStuDetailDown(param, function(data){
		});
	};
	/** 概况信息 点击事件 */
	var abstractDetailAll = function(type, title, seniorCode,typemc){
		showLoading();
		var detail = $scope.data.abstract_detail;
		detail.page.curpage = 1;
		detail.title = title;
		detail.values= {};
		detail.values[type] = seniorCode||null;
		if(type=="sexmc"||type=="deptmc"||type=="reasonmc"){
			detail.headers= ['学号','姓名','性别','院系','专业','班级','挂科学分','原因'];
			detail.fields = ['no','name','sexmc','deptmc','majormc','classmc','credits','reasonmc'];
		}else{
			detail.headers= ['学号','姓名','性别','院系','专业','班级','挂科学分','原因',typemc];
			detail.fields = ['no','name','sexmc','deptmc','majormc','classmc','credits','reasonmc',type];
		}
//		abstractDetail();
		detail.formConfig = {
				title : title+'学生名单',
				show  : true,
				url       : $scope.data.detailUrl,
				exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
				heads  : detail.headers,
				fields : detail.fields,
				params : abstractDetailParam()
			};
		hideLoading();
		$timeout(function(){});
	};
}]);