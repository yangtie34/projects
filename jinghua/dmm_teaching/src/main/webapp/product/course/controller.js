/**
 * 课程分析
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService', '$timeout','dialog', function($scope, service, advancedService, $timeout,dialog){	
	$scope.data={
	    mask: true,
	    courShow:'',
	    param :{
	    	schoolYear : '',//当前年
	    	termCode   : '',//当前学期
	    	edu : ''
	    },
	    dept:{},
	    sub:{},
	    subHist : {},
	    deptHist:{},
	    course:{
	    	codeType :'',
	    	code: '',
	    },
	    model:{
	    	edu:null,
	    	xnxq:null,
	    	deptCourse:null,
	    	subCourse:null,
	    	deptHisCourse:null,
	    	subHisCourse:null
	    },
		advance : {
			param : null
		}
	};
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
	var showLoading = function(){ dialog.showLoading(); };
	var hideLoading = function(){ dialog.hideLoading(); };
	service.getBzdm(function(data){
			var $data = $scope.data,
			param = $data.param;
			var edu=data.EDU_ID_STU,xnxq=data.xnxq,course=data.sele;
			$scope.data.edu_list=data.EDU_ID_STU;
			$data.model.edu = edu[0].id;
			$data.xnxq_list=data.xnxq;
			$data.model.xnxq = xnxq[0].id;
			$data.course_list=data.sele;
			$data.model.deptCourse=course[0].id;
			$data.model.subCourse=course[0].id;
			$data.model.deptHisCourse=course[0].id;
			$data.model.subHisCourse=course[0].id;
			var ary = xnxq[0].id.split(',');
			$data.param.schoolYear = ary[0];
			$data.param.termCode   = ary[1];
			$data.param.edu=edu[0].id;
			$data.course.codeType=course[0].type;
			$data.course.code=course[0].code;
			$data.edu=edu[0].id;
			refalshData();
			
		});
	$scope.changeEdu=function(obj){
		var data=$scope.data;
		data.edu=obj.id;
		data.param.edu=obj.id;
		refalshData();
	}
	$scope.changeXnxq=function(id,obj){
		var data=$scope.data;
		var ary = id.split(',');
		data.param.schoolYear = ary[0];
		data.param.termCode   = ary[1];
		refalshDataTop();
	}
	//课程分布
	var getCourseDistribution=function(){
		$scope.data.courShow=true;
		var $data = $scope.data,
		param = $data.param;
		var termCode=param.termCode,schoolYear=param.schoolYear,edu=param.edu;
		service.getCourseDistribution(getAdvancedParam(),edu,schoolYear,termCode,function(abs,attrOption,natOption,cateOption) {
			$scope.data.abs=abs;
			$scope.data.attr={
					option:attrOption,
			}
			$scope.data.nat={
					option:natOption,
			}
			$scope.data.cate={
					option:cateOption,
			}
			$scope.data.courShow=false;
		});	
	}
	
	//机构分布
	var getDeptDistribution=function(){
		var $data = $scope.data,
		param = $data.param,course=$data.course;
		var termCode=param.termCode,schoolYear=param.schoolYear,edu=param.edu,codeType=course.codeType,code=course.code;
		service.getDeptDistribution(getAdvancedParam(),edu,schoolYear,termCode,codeType,code,function(option,deptMc) {
			$scope.data.dept={
					option:option,
			}
			$scope.data.deptMc=deptMc;
		});	
		$scope.data.dept.mask=false;
	};
	
	$scope.changCourseBydept=function(id,obj){
		$scope.data.dept.mask=true;
		var data=$scope.data,course=data.course;
		course.codeType=obj.type;
		course.code=obj.code;
		getDeptDistribution();
	}
	//学科分布
	var getSubjectDistribution=function(){
		var $data = $scope.data,
		param = $data.param,course=$data.course;
		var termCode=param.termCode,schoolYear=param.schoolYear,edu=param.edu,codeType=course.codeType,code=course.code;
		service.getSubjectDistribution(getAdvancedParam(),edu,schoolYear,termCode,codeType,code,function(option) {
			$scope.data.subject={
					option:option,
			}
			$scope.data.sub.mask=false;
		});	
	
	}
	$scope.changCourseBysub=function(id,obj){
		$scope.data.sub.mask=true;
		var data=$scope.data,course=data.course;
		course.codeType=obj.type;
		course.code=obj.code;
		getSubjectDistribution();
	}
	//机构历史
	var getDeptHistory=function(){
		var $data = $scope.data,
		param = $data.param,course=$data.course;
		var edu=param.edu,codeType=course.codeType,code=course.code;
		service.getDeptHistory(getAdvancedParam(),edu,codeType,code,function(option) {
			$scope.data.deptHistory={
					option:option,
			}
			$scope.data.deptHist.mask=false;
			hideLoading();
		});	
	}
	$scope.changCourseBydeptHistory=function(id,obj){
		$scope.data.deptHist.mask=true;
		var data=$scope.data,course=data.course;
		course.codeType=obj.type;
		course.code=obj.code;
		getDeptHistory();
	}
	//学科历史
	var getSubjectHistory=function(){
		var $data = $scope.data,
		param = $data.param,course=$data.course;
		var edu=param.edu,codeType=course.codeType,code=course.code;
		service.getSubjectHistory(getAdvancedParam(),edu,codeType,code,function(option) {
			$scope.data.subjectHistory={
					option:option,
			}
			$scope.data.subHist.mask=false;
		});	
	}
	$scope.changCourseBysubHistory=function(id,obj){
		$scope.data.subHist.mask=true;
		var data=$scope.data,course=data.course;
		course.codeType=obj.type;
		course.code=obj.code;
		getSubjectHistory();
	}
	//上半部分
	var refalshDataTop=function(){
		getCourseDistribution();
		getDeptDistribution();
		getSubjectDistribution();
	}
	var refalshDataDown=function(){
		getDeptHistory();
		getSubjectHistory();
	}
	var refalshData=function(){
		showLoading();
		refalshDataTop();
		refalshDataDown();
	}
	
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