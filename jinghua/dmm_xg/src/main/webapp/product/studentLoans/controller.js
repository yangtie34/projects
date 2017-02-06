/**
 * 助学贷款
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service','advancedService','$timeout','dialog', function($scope, service,advancedService,$timeout,dialog){

	$scope.data = {
			mask : [false,false],
			dateName   : '', // 年名称；今年、去年、近五年 违纪*人
			value_year : null, // 选中的年id
			value_edu  : null,
			thisYear   : null,
			lastYear   : null,
			start_year : null,
			end_year   : null,
			fiveYear   : false,
			tenYear    : false,
			abstract_data : null,
			distribution  : null,
			deptData : {},
			Type_lx : null,
			Type_year : null,
			advance : {
				param : null
			},
			detailUrl : "pmsStudentLoans/getStuDetail",
			exportUrl : 'pmsStudentLoans/down',
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
			lastBt:true
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
	var getSchoolYearData=function(){//得到学年
		service.getSchoolYear(function(data){
			showLoading();
				var $data = $scope.data;
				$data.bzdm_xn  = data.xn;
				$data.bzdm_edu = data.EDU_ID;
				$data.bzdm_lx = data.bzdm;
				var bzdm_first = $data.bzdm_xn[0], id_xn = bzdm_first.id;
				$data.dateName   = bzdm_first.mc;
				$data.value_year = id_xn;
				$data.value_edu  = $data.bzdm_edu[0].id;
				$data.thisYear   = id_xn;
				var year =Number(id_xn.substr(0,4))-1;
				$data.lastYear   = year+'-'+(year+1);
				$data.Type_lx   = $data.bzdm_lx[0].id;//初始化助学贷款分布类型
				$data.Type_year   = $data.bzdm_lx[0].id;//初始化历年助学贷款类型
				$data.start_year = id_xn;
				$data.end_year   = id_xn;
				if(data.xn.length<=1){
					$data.lastBt=false;	
				}
				getZxInfoData();//初始化总体信息(总人数,总金额,人均金额)
				getZxData();
				getZxfbData();
			});
	};
	/**
	 * 清除选择年默认数据
	 */
	var clearYearSelectedFn = function(){
		var $data = $scope.data;
		$data.value_year = null;
		$data.fiveYear = false;
		$data.tenYear  = false;
	};
	$scope.changXn = function(id, data){
		var $data = $scope.data, bzdm_xn = $data.bzdm_xn;
		if($data.start_year==id && $data.end_year==id) return;
		clearYearSelectedFn();
		for(var i=0,len=bzdm_xn.length; i<len; i++){
			if(bzdm_xn[i].id == id){
				$data.value_year = id;
				$data.dateName = bzdm_xn[i].mc;
				break;
			}
		}
		$data.start_year = id;
		$data.end_year   = id;
		refreshData();
	};
	$scope.changEdu = function(id, data){//改变学年
		$scope.data.value_edu = id;
		refreshData();
	};
	$scope.changLx = function(id, data){//改变减免分布类型
		$scope.data.Type_lx = id;
		getZxfbData();
	};
	$scope.changYearLx = function(id, data){//改变历年减免分布类型
		$scope.data.Type_year = id;
		getZxData();
	};
	var getZxInfoData=function(){
		service.queryZxInfo($scope.data.start_year,$scope.data.value_edu,'',function(data){//得到助学贷款的总体信息(总人数,总金额,人均金额)
//			for(var i=0;i<data.fl.length;i++){
//				var name=data.fl[i].NAME_;
//				data.fl[i].NAME_=name.substr(0,name.length-2);
//			}
			$scope.zxdkCfg=data;
		});	
	};
	//助学贷款学生行为
	var getBehaviorFn = function(){
		service.queryZxxw($scope.data.value_year, $scope.data.value_edu,getAdvancedParam() , function(list, option){
			hideLoading();
			$scope.data.behavior = {
				list  : list,
				radar : option
			};
		});
	};
	var getZxfbData=function(){//助学贷款分布
		partshowLoading(0);
		service.queryZxfb($scope.data.start_year,$scope.data.value_edu,$scope.data.Type_lx ,getAdvancedParam() ,function(data){
			data.config.on=['CLICK',function (param){
				abstractDetailAll('deptmc','typemc',param.name+'（'+param.seriesName+'）',param.name,param.seriesName,'jmfb');
				}];
			parthideLoading();
			$scope.ZxfbCfg=data;
		});	
	};
	var getZxData=function(pid){//助学贷款历年变化
		partshowLoading(1);
		service.queryYearZxfb($scope.data.Type_year,getAdvancedParam(),function(data){
			data.config.on=['CLICK',function (param){
				abstractDetailAll('school_year','typemc',param.name+'学年（'+param.seriesName+'）',parseInt((param.name).split('-')[0]),param.seriesName,'lnfb');
				}];
			parthideLoading();
			hideLoading();
		$scope.yearZxCfg=data;
	});
	};
	//刷新数据
	var refreshData=function(){
		showLoading();
		getZxInfoData();//初始化总体信息(总人数,总金额,人均金额)
		getZxfbData();
		getBehaviorFn();
	};
	getSchoolYearData();
	getBehaviorFn();
	/**
	 * 高级查询-change事件
	 */
	$scope.advanceChange = function(data){
		var param = advancedService.getParam(data);
		// 高级查询-参数
		setAdvancedParam(param);
		getZxData();//历年学费减免分布
		getZxfbData();//学费减免分布
	};
	//加载高级查询组件
	service.getAdvance(function(data){
		$scope.data.advance.source = data;
	});
	/** 概况信息 点击事件 */
	$scope.abstractDetailClick = function(type, title, seniorCode){
		var detail = $scope.data.abstract_detail;
		detail.page.curpage = 1;
		detail.values= {};
		detail.values['code'] = type||null;
		detail.values['jmtype'] = title||null;
		detail.values['year'] = $scope.data.start_year||null;
		if(type=='xrs'){
			title = '助学贷款（'+title+'）学生名单';
		}
		if(type=='je'){
			detail.headers= ['学号','学年','姓名','性别','院系','专业','班级','贷款类型','金额'];
			detail.fields = ['no','school_year','name','sexmc','deptmc','majormc','classmc','typemc','money'];
		}else{
		detail.headers= ['学号','学年','姓名','性别','院系','专业','班级'];
		detail.fields = ['no','school_year','name','sexmc','deptmc','majormc','classmc'];
		}
//		abstractDetail();
		detail.formConfig = {
				title : title,
				show  : true,
				url       : $scope.data.detailUrl,
				exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
				heads  : detail.headers,
				fields : detail.fields,
				params : abstractDetailParam()
			};
		$timeout(function(){});
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
		showLoading();
		service.getStuDetail(abstractDetailParam(), function(data){
			hideLoading();
			detail.list = data.rows;
			detail.page.sumcount = data.total;
			detail.page.pagecount = data.pagecount;
			detail.mask = false;
			detail.detail_show = true;
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
	var abstractDetailAll = function(type1,type2, title,nameCode, seniorCode,code){
		var detail = $scope.data.abstract_detail;
		detail.page.curpage = 1;
		detail.title = title;
		detail.values= {};
		detail.values[type1] = nameCode||null;
		detail.values[type2] = seniorCode||null;
		detail.values['code'] = code||null;
		detail.values['year'] = $scope.data.start_year||null;
		detail.headers= ['学号','学年','姓名','性别','院系','专业','班级','贷款类型','金额'];
		detail.fields = ['no','school_year','name','sexmc','deptmc','majormc','classmc','typemc','money'];
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
		$timeout(function(){});
	};			
}]);