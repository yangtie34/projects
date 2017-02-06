/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService','$timeout','dialog', function($scope, service, advancedService,$timeout,dialog){
	
	$scope.data = {
		mask : true,
		tag:'sex',
		add:{},//切换lab时控制局部遮罩
		dataShow:{},//切换人次 /人数比例
		dateName   : '', // 年名称；今年、去年、近五年 违纪*人
		value_year : '', // 选中的年id
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
		advance : {
			param : null
		},
		detailUrl : "pmsPunish/getStuDetail",
		exportUrl : 'pmsPunish/down',
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
	$scope.headers = ['学号','入学年级','姓名','性别','地区','院系','专业','班级','时间'];
	$scope.fields = ['no','enroll_grade','name','sexmc','shengmc||shimc||xianmc','deptmc','majormc','classmc','date_'];
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
		    detail.values['start_year'] = $scope.data.start_year;
		    detail.values['end_year'] = $scope.data.end_year;
		    detail.values['edu']=$scope.data.value_edu;
		Ice.apply(param, { values:getJson(detail.values), fields:fields, headers:headers});
		return param;
	};
	$scope.getAllStuDetail=function(type){
		var detail = $scope.data.stu_detail;
		if(type=="punish")detail.title  = "被处分学生名单";
		if(type=="violate")detail.title  = "违纪学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		detail.values["all"]=type;
		getStuDetail();
	}
	//违纪
	var violateDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = "违纪（"+param.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'violate');
	};
	//处分
	var punishDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = "处分（"+param.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'punish');
	};
	//部门 
	var deptDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		if($scope.data.deptData.id_target=='2'){
			if( $scope.data.deptData.id_type=="punish"){
				detail.title=param.name+"（处分）学生名单";
			}else{
				detail.title=param.name+"（违纪）学生名单";
			}
		}else{
			detail.title  = param.name+"（"+param.seriesName+"）学生名单";
		}
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'dept');
	};
	//月份
	var monthDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"（"+param.seriesName+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'month');
	};
	//年级
	var gradeDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"（"+param.seriesName+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'grade');
	};
	
	//年领
	var ageDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  =param.name+"（"+param.seriesName+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'age');
	};
	//二次处分
	var againDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"学年（二次处分）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'again');
	};
	//下钻
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
	};*/
	var distributionDetail = function(param, type){
		var detail = $scope.data.stu_detail;
		var obj={};
		detail.page.curpage = 1;
		detail.values[type] = param.data.code;
		if(type=='dept'){
			obj.level=$scope.data.deptData.id_type;
			if($scope.data.deptData.id_target=='1'){
				obj.deptId=param.data.id;
				//obj.type=param.data.level_;
				if( $scope.data.deptData.id_type=="punish"){obj.code=param.data.punish[param.seriesIndex].code;}
				else{ obj.code=param.data.violate[param.seriesIndex].code;}
				obj.target="1";
			}
			if($scope.data.deptData.id_target=='2'){
				obj.target="2";
				//obj.type=$scope.data.deptData.list_type[0].level_;
				obj.deptId=param.data.code;

			}
			detail.values[type] = obj;
		}
		/*if(type=='deptPunish'){
			obj.deptId=param.data.id;obj.level=param.data.level_;
			obj.punishCode=param.data.punish[param.seriesIndex].code;
			detail.values[type]=obj;
		}*/
		if(type=='month'){
			if(param.seriesName=='违纪'){
				obj.level='violate',obj.month=param.name;
				detail.values[type]=obj;
			}
			if(param.seriesName=='处分'){
				obj.level='punish',obj.month=param.name;
				detail.values[type]=obj;
			}
		}
		if(type=='grade'){
			if(param.seriesName=='违纪'){
				obj.level='violate',obj.grade=param.data.code;
				detail.values[type]=obj;
			}
			if(param.seriesName=='处分'){
				obj.level='punish',obj.grade=param.data.code;
				detail.values[type]=obj;
			}
		}
		if(type=='age'){
			if(param.seriesName=='违纪'){
				obj.level='violate',obj.age=param.data.name;
				detail.values[type]=obj;
			}
			if(param.seriesName=='处分'){
				obj.level='punish',obj.age=param.data.name;
				detail.values[type]=obj;
			}
		}
		if(type=='again'){
				obj.level='punish',obj.start=param.data.name.substring(0,4);
				detail.values[type]=obj;
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
			$timeout(function(){});
		//getStuDetail();
	};
	
	
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
		$data.start_year = id_xn;
		$data.end_year   = id_xn;
		formatDateName();
	});
	/**
	 * 清除选择年默认数据
	 */
	var clearYearSelectedFn = function(){
		var $data = $scope.data;
		$data.value_year = null;
		$data.fiveYear = false;
		$data.tenYear  = false;
	}
	$scope.changXn = function(id, data){
		var $data = $scope.data;
//		if($data.start_year==id && $data.end_year==id) return; // 这个可以去掉
		clearYearSelectedFn();
		$data.dateName   = data ? data.mc : '';
		$data.value_year = id;
		$data.start_year = id;
		$data.end_year   = id;
		refreshData();
		formatDateName();
	}
	$scope.changEdu = function(id, data){
		$scope.data.value_edu = id;
		refreshData();
	}
	$scope.chang5Year = function(){
		var $data = $scope.data, thisYear = $data.thisYear;
		if($data.fiveYear) return;
		clearYearSelectedFn(); // 清除年
		$data.dateName = '近五年';
		$data.fiveYear = true;
		var year = Number(thisYear.substr(0,4))-5+1;
		$data.start_year = year+'-'+(year+1);
		$data.end_year   = thisYear;
		refreshData();
	}
	$scope.chang10Year = function(){
		var $data = $scope.data, thisYear = $data.thisYear, bzdm_xn = $data.bzdm_xn;
		if($data.tenYear) return;
		clearYearSelectedFn(); // 清除年
		$data.dateName = '近十年';
		$data.tenYear = true;
		var year = Number(thisYear.substr(0,4))-10+1;
		$data.start_year = year+'-'+(year+1);
		$data.end_year   = thisYear;
		refreshData();
	}
	
	/**
	 * 摘要
	 */
	var getAbstract = function(){
		service.getAbstract($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu, getAdvancedParam()).then(function(data){
			$scope.data.abstract_data = {
				violate : data.violate,
				punish  : data.punish,
				scale   : data.scale
			}
		});
	}

	/**
	 * 违纪、处分 类型分布
	 */
	var getDistribution = function(){
		service.getDistribution($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu, getAdvancedParam(), function(option_volate, option_punish){
			$scope.data.distribution = {
				option_volate : option_volate,
				option_punish : option_punish
			}
			Ice.apply(option_volate, {config:{on:['CLICK', violateDetail]}});
			Ice.apply(option_punish, {config:{on:['CLICK', punishDetail]}});

		});
	}

	/**
	 * 组织机构-人次
	 */
	var getDeptDataList = function(){
		var deptData = $scope.data.deptData;
		service.getDeptDataList($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu, getAdvancedParam(), function(list, bzdm_type, bzdm_target, deptMc){
			// 初始化基本数据
			var id_type   = bzdm_type[0].id, // 每次加载后都要重置默认显示 人次、违纪；或者再重构，根据当前状态去加载
				id_target = bzdm_target[0].id;
			deptData.id_type     = id_type;
			deptData.id_target   = id_target;
			deptData.bzdm_type   = bzdm_type;
			deptData.bzdm_target = bzdm_target;
			deptData.deptMc      = deptMc;
			deptData.list_type   = list;
			deptData.option      = service.getDeptOptionByKeyList(list, id_type, deptMc);
			Ice.apply($scope.data.deptData.option, {config:{on:['CLICK', deptDetail]}});
			// 重置 人数、比例 imp!
			deptData.list_target = null;
		});
	
	}
	/**
	 * 组织机构-人数/比例
	 */
	var getDeptCountScaleList = function(){
		var deptData = $scope.data.deptData;
		$scope.data.dataShow.show=true;
		service.getDeptCountScaleList($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu, getAdvancedParam(), function(list){
			$scope.data.dataShow.show=false;
			deptData.list_target = list;
			deptData.option      = service.getDeptCountScaleOption(list, deptData.id_type, $scope.data.deptMc);
		});
	}
	/**
	 * 切换类型（违纪、处分）
	 * 无需请求 service
	 */
	$scope.changeDeptType = function(id){
		var deptData = $scope.data.deptData, id_type = id, id_target = deptData.id_target, deptMc = deptData.deptMc, option;
		if(deptData.id_type == id_type) return;
		deptData.id_type = id_type;
		if(id_target=='1'){
			option = service.getDeptOptionByKeyList(deptData.list_type, id_type, deptMc);
		}else if(id_target=='2'){
			option = service.getDeptCountScaleOption(deptData.list_target, id_type, deptMc);
		}
		deptData.option = option;
	}
	/**
	 * 切换指标（人数；人次/比例）
	 * 需要请求 service
	 */
	$scope.changeDeptTarget = function(id){
		var deptData = $scope.data.deptData, id_type = deptData.id_type, id_target = id, deptMc = deptData.deptMc;
		if(deptData.id_target == id_target) return;
		deptData.id_target = id_target;
		if(id_target=='1'){
			deptData.option = service.getDeptOptionByKeyList(deptData.list_type, id_type, deptMc);
		}else if(id_target=='2'){
			if(deptData.list_target){
				deptData.option = service.getDeptCountScaleOption(deptData.list_target, id_type, deptMc);
			}else{
				getDeptCountScaleList();
			}
		}
	}
	
	/**
	 * 月份分布
	 */
	var getMonthList = function(){
		service.getMonthList($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu, getAdvancedParam(), function(option){
			$scope.data.month = {
				option : option
			}
			Ice.apply(option, {config:{on:['CLICK', monthDetail]}});
		})
	}
	
	/**
	 * 年级分布
	 */
	var getGrade = function(){
		service.getGrade($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu, getAdvancedParam(), function(option){
			$scope.data.grade = {
					option : option
			}
			Ice.apply(option, {config:{on:['CLICK', gradeDetail]}});
		})
	}

	/**
	 * 年龄
	 */
	var getAge = function(){
		service.getAge($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu, getAdvancedParam(), function(option){
			$scope.data.age = {
				option : option
			}
			Ice.apply(option, {config:{on:['CLICK', ageDetail]}});
		})
	}

	/**
	 * 二次异动
	 */
	var getPunishAgain = function(){
		service.getPunishAgain($scope.data.value_edu, getAdvancedParam(), function(option){
			$scope.data.again = {
				option : option
			}
			hideLoading();
			Ice.apply(option, {config:{on:['CLICK', againDetail]}});
		})
	}
	/**增加内容*/
	var getLiForAdd = function(){
		service.getLiForAdd($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu, getAdvancedParam()).then(function(data){
			data.msg[0].flag=true;
			$scope.data.dataLab={labLi:data.msg};
			$scope.data.tag=data.msg[0].id;
		});
		getSexAndGradeAndOther();
	}
	$scope.changeLab=function(obj,list){
		for(var i=0;i<list.length;i++){
			list[i].flag=false;
		}
		obj.flag=true;
		$scope.data.tag=obj.id;
		getSexAndGradeAndOther();
		
	}
	var getSexAndGradeAndOther=function(){
		$scope.data.add.show=true;
		service.getSexAndGradeAndOther($scope.data.start_year, $scope.data.end_year, $scope.data.value_edu,$scope.data.tag, getAdvancedParam(),function(option1,option_violate,option2,option_punish){
			$scope.data.add.show=false;
			$scope.data.vioTree = {option:option1};
			$scope.data.punTree = {option:option2};
			$scope.data.punCir = { option : option_punish };
			$scope.data.vioCir = { option : option_violate };
		});
	}
	var refreshData = function(){
		showLoading();
		getAbstract();
		getDistribution();
		getDeptDataList();
		getMonthList();
		getGrade();
		getAge();
		getLiForAdd();
		getSexAndGradeAndOther();
		getPunishAgain();


	}
	refreshData();

	/*$scope.$watch("data.stu_detail.page.curpage",function(newVal,oldVal){
		if(newVal != oldVal){
			$scope.data.stu_detail.mask = true;
			$scope.data.stu_detail.page.curpage = newVal;
			getStuDetail();
		}
	},true);*/
	/**
	 * 高级查询-change事件
	 */
	$scope.advanceChange = function(data){
		var param = advancedService.getParam(data);
		// 高级查询-参数
		setAdvancedParam(param);
		refreshData();
	};
	$scope.stuDetailDown= function(){
		var param = stuDetailParam();
		param.fileName = $scope.data.stu_detail.title;
		service.getStuDetailDown(param,function(data){
			
		})
	};
	
	service.getAdvance(function(data){
		$scope.data.advance.source = data;
	});
	
}]);