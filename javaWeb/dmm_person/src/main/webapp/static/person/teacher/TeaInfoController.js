app.controller("TeaInfoController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="teaInfoService?"; 
	  
	  var methods=['getInfo',	//0获取教师信息
	               'getHisInfo'//1教师信息变动历史
	               ];
	  
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  params:[userId],
			  service:httservice+methods[i]
		  });
	 }
};
scope.teaInfoTitles=['姓名','职工编号','性别','政治面貌','民族','来校时间','出生日期','入党时间','身份证号','教职工来源',
                      '户籍地址','教职工类别','婚姻状况','学历','联系方式','学位','现在部门','教职工状态','用人方式','是否双师教师'];
var getVmData=function(){
	http.callService(htt[0]).success(function(data){
		vm.items[0] = data;
	  });
	http.callService(htt[1]).success(function(data){
		vm.items[1] = data;
	  });
};
getServiceData();
getVmData();
	  
}]);