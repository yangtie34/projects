/**
 * 
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService','$timeout','dialog', function($scope, service, advancedService,$timeout,dialog){
	
	$scope.data = {
		mask : true,
		tabShow    : { name:'学籍特殊异动', code:null }, // 当前异动代码
		distribute : {}, // 分布
		history    : {},
		changeThis:{},
		changeHistory:{},
		changeSetting : {
			history : {}
		},
		advance : {
			param : null
		},
		detailUrl : "pmsChangeBad/getStuDetail",
		exportUrl : 'pmsChangeBad/down',
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
	
	//下钻表头
	$scope.headers = ['学号','入学年级','姓名','性别','地区','院系','专业','班级','学籍特殊异动时间'];
	$scope.fields = ['no','enroll_grade','name','sexmc','shengmc||shimc||xianmc','deptmc','majormc','classmc','changedate'];
	
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	var showLoading = function(){ dialog.showLoading(); };
	var hideLoading = function(){ dialog.hideLoading(); };
	
	var getTabCode = function(){ return $scope.data.tabShow.code || null; };
	
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
	/** 发送的请求参数 */
	var stuDetailParam = function(){
		var detail = $scope.data.stu_detail,
			param  = getParam(),
			//page   = getJson(detail.page),
			fields = getJson(detail.fields),
			headers= getJson(detail.headers);
		    detail.values['STU_ROLL_CODE'] = 1;
		    detail.values['schoolyear'] = $scope.data.schoolyear;
		    detail.values['changeCode'] = getTabCode();
		Ice.apply(param, {values:getJson(detail.values), fields:fields, headers:headers});
		return param;
	};
	
	// tab
	service.getChangeBadList().then(function(data){
		var ary = data.list;
		ary.splice(0, 0, $scope.data.tabShow);
		$scope.data.tabs = ary;
	})
	// 摘要
	var getStuChangeAbstract = function(){
		service.getStuChangeAbstract(getTabCode(), getAdvancedParam(), function(data){
			$scope.data.change_abstract = data;
		});
	}
	/**
	 * 分布 or 历史
	 */
	var getStuChangeOrHistory = function(){
		var code = getTabCode();
		// 显示历史
		if($scope.data.changeSetting.history.show){ $scope.changeHistory(code);}
		// 显示分布
		else{ $scope.changeThis(code); }
	};
	// 组织机构分布
	var getDeptStuChange = function(){
		service.getDeptStuChange(getTabCode(), getAdvancedParam(),$scope.data.tabShow.name, function(data, deptMc){
			$scope.data.deptCfg = data;
			$scope.data.deptMc  = deptMc;
			Ice.apply(data, {config:{on:['CLICK', deptDetail]}});//学院
		});
	}
	// 月份分布
	var getStuChangeMonth = function(){
		service.getStuChangeMonth(getTabCode(), getAdvancedParam(),$scope.data.tabShow.name, function(option, info){
			$scope.data.monthCfg = option;
			$scope.data.info = info;
			Ice.apply(option, {config:{on:['CLICK', monthDetail]}});

		});
	}
	// 历年分布
	var getStuChangeYear = function(){
		service.getStuChangeYear(getTabCode(), getAdvancedParam(),$scope.data.tabShow.name, function(data){
			$scope.data.yearCfg = data;
			Ice.apply(data, {config:{on:['CLICK', yearDetail]}});
		});
	}
	// 二次异动
	var getChangeAgain = function(){
		service.getChangeAgain(getTabCode(), getAdvancedParam(),$scope.data.tabShow.name, function(option){
			$scope.data.again=option;
			Ice.apply(option, {config:{on:['CLICK', againDetail]}});
		});
		hideLoading();
	}
	
	/**
	 * 显示当前分布
	 */
	$scope.changeThis = function(code){
		$scope.data.changeSetting.history.show = false;
		$scope.data.changeThis.show=true;
		service.getStuChange(code, getAdvancedParam(),$scope.data.tabShow.name, function(data){
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
			hideLoading();

		});
	}
	/**
	 * 显示历史分布
	 */
	$scope.changeHistory = function(code){
		$scope.data.changeSetting.history.show = true; // 设置历史 show
		$scope.data.changeHistory.show=true;
		service.getStuChangeHistory(code, getAdvancedParam(), function(type_option, grade_option, subject_option, sex_option){
			$scope.data.changeHistory.show=false;
			var history = $scope.data.history;
			history.typeCfg    = type_option;
			history.gradeCfg   = grade_option;
			history.subjectCfg = subject_option;
			history.sexCfg     = sex_option;
		});
	};

	/**
	 * 切换tab
	 */
	$scope.tabChange = function(tab){
		var ary = $scope.data.tabs;
		for(var i=0,len=ary.length; i<len;){
			var show = false;
			if(ary[i].code == tab.code){
				show = true;
			}
			ary[i++].show = show;
		}
		$scope.data.tabShow = {code:tab.code, name:tab.name};
		// 显示基本分布
		refreshData($scope.data.tabShow.code, getAdvancedParam());
	}
	//摘要下钻
	$scope.getStuDetail=function(type){
		var detail = $scope.data.stu_detail;
		detail.title  = "当前在籍生（"+$scope.data.tabShow.name+"）学生名单";
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
		if($scope.data.tabShow.name==param.name){
			detail.title  = $scope.data.tabShow.name+"学生名单";
		}else{ 
		detail.title  = $scope.data.tabShow.name+"（"+param.name+"）学生名单";
		}
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'type');
	};
	//grade
	var gradeDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = $scope.data.tabShow.name+"（"+param.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'grade');
	};
	//subject
	var subjectDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = $scope.data.tabShow.name+"（"+param.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'subject');
	};
	//sex
	var sexDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = $scope.data.tabShow.name+"（"+param.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'sex');
	};
	//dept
	var deptDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"（"+$scope.data.tabShow.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'dept');
	};
	//month
	var monthDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"（"+$scope.data.tabShow.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'month');
	};
	
	//year
	var yearDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"学年（"+$scope.data.tabShow.name+"）学生名单";
		detail.values = {};
		detail.headers= $scope.headers;
		detail.fields = $scope.fields;
		param.data = obj;
		distributionDetail(param,'year');
	};
	//again
	var againDetail = function(param, obj, data){
		var detail = $scope.data.stu_detail;
		detail.title  = param.name+"学年二次异动（"+$scope.data.tabShow.name+"）学生名单";
		detail.values = {};
		detail.headers= ['学号','入学年级','姓名','性别','地区','院系','专业','班级'];
		detail.fields = ['no','enroll_grade','name','sexmc','shengmc||shimc||xianmc','deptmc','majormc','classmc'];
		param.data = obj;
		distributionDetail(param,'again');
	};
	//下钻
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
	//添加请求code
	var distributionDetail = function(param, type){
		var detail = $scope.data.stu_detail;
		detail.page.curpage = 1;
		if(type=='month'){
			detail.values[type]=param.name;
		}else if(type=='subject'){
			detail.values[type]=param.data.id;
		}
		else if(param.data.code==null || param.data.code==""){
			detail.values[type]=param.data.name;
		}else {
			detail.values[type] = param.data.code;
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
		getChangeAgain();
		getStuChangeYear();
	}
	// 获取数据
	refreshData(null);

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
	
	
	// 复合查询组件模拟数据
	/*$scope.data.querySource = [ {
			group : 'common',
			queryCode : "SEX_CODE",
			queryName : '性别',// 名称
			items : [{	id : '1',mc : '男'}, 
			          {	id : '2',mc : '女'}
			         ],// 条件数据
		}, {
			group : 'stu',
			queryCode : "ENROLL_GRADE",
			queryName : '入学年级',
			items : [{id : '2015', mc : '2015级'},
			         {id : '2014', mc : '2014级'},
			         {id : '2013', mc : '2013级'}
			        ],
		}, {
			group : 'common',
			queryCode : "DEPT_ID",
			queryName : '组织机构',
			queryType : "comboTree",
			items : {
				  id :0,
				  mc: "学校",
				  children : [
				           {id:308,mc:"计算机科学与技术学院",children:[{id:8010308,mc:"计算机科学与技术"},{id:8040308,mc:"计算机应用技术"}]},
				           {id:301,mc:"文学院",children:[{id:1050301,mc:'语文教育'},{id:1040301,mc:'汉语言文学（专升本）'}]},
				           {id:4,mc:"建筑工程工程学院",children:[{id:3,mc:'造价'},{id:4,mc:'城市工程'}]},
				           {id:5,mc:"会计学院",children:[{id:6,mc:'国际贸易'},{id:7,mc:'银行电算'}]}]
			}
	} ];*/
	 
}]);