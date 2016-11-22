var app = angular.module('app', ['system'])
.controller("controller",['$scope','httpService','toastrService','$interval',
    function(scope,http,toastr,$interval){
	$.material.init();
	$interval(
        function() {$.material.init(); },
        1000
    );
	scope.jobs = [
      {name:'一卡通日消费情况统计',code:'cardScheduleService?cardDay',time:'每天'},
      {name:'一卡通月消费情况统计',code:'cardScheduleService?cardMonth',time:'每天'},
      {name:'一卡通各学生各端口消费年报',code:'cardScheduleService?cardPortYear',time:'每周或每月'},
      {name:'一卡通日高低消费异常',code:'cardScheduleService?cardPayAbnormal',time:'每天'},
      {name:'一卡通日消费情况初始化',code:'cardScheduleInitService?cardDay',time:'部署执行一次'},
      {name:'一卡通月消费初始化',code:'cardScheduleInitService?cardMonth',time:'部署执行一次'},
      {name:'一卡通学生各端口消费年报初始化',code:'cardScheduleInitService?cardPortYear',time:'部署执行一次'},
      {name:'一卡通日高低消费异常-高低消费统计初始化',code:'cardScheduleInitService?cardPayAbnormal',time:'部署执行一次'},
      {name:'宿舍晚归学生统计',code:'warningScheduleService?lateStudents',time:'每天'},
      {name:'疑似不在校学生统计',code:'warningScheduleService?notInStudents',time:'每天'},
      {name:'收集教职工执教历史信息',code:'teacherHistoryService?collectTeaHistory',time:'半年'},
      {name:'课程安排初始化weeks',code:'initCourseService?initCourseWeekJob',time:'每学期'},
      {name:'转化学校的辅导员信息',code:'fdyChangeService?changeFdyInfo',time:'每月'},
      {name:'搜集所有的学霸信息',code:'smartStudentScheduleService?collectSmartStudents',time:'每学期'},
      {name:'搜集学霸消费信息',code:'smartStudentScheduleService?smartStuPayInfo',time:'每学期'}]
	scope.execute = function(job){
		toastr.info("《"+job.name + "》开始执行！");
		http.callService({
			 service : job.code,
	         params : []
		}).success(function(data){
			console.log(data)
			if(data.isTrue){
				toastr.success(data.msg);
			}else{
				toastr.error(data.msg);
			}
		});
	}
}]);