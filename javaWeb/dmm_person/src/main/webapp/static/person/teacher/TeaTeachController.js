app.controller("TeaTeachController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="teaTeachService?"; 
	  
	  var methods=['jrkc',	//0今日课程
	               'grkb',	//1个人课表
	               'skjd',	//2授课进度
	               'skcj'	//3授课成绩
	               ];
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  params:[userId],
			  service:httservice+methods[i]
		  });
	 }
};
scope.skcjTitles=['学年','学期','课程名','通过率','最高分','最低分'];
var getData3=function(){
	vm.items[3]=null;
	htt[3].params=[userId];
	htt[3].params.push(scope.page.currentPage || 1);
	htt[3].params.push(scope.page.numPerPage || 10);
	http.callService(htt[3]).success(function(data){
		vm.items[3] = data.resultList;
		 scope.page.totalRows=data.totalRows;  
		 scope.page.totalPages=data.totalPages;  
	  });
}
var getData=function(){
	http.callService(htt[0]).success(function(data){
		vm.items[0] = data;
	  });

	http.callService(htt[1]).success(function(data){
		//课程名，班级名，第几星期，开始节，结束节
		vm.items[1] ={kbkeys:['COURSE_NAME','CLASSROOM','DAY_OF_WEEK','PERIODSTART','PERIODEND'],
				data:data}
	  });
	http.callService(htt[2]).success(function(data){
		vm.items[2] = data;
	  });
	getData3();
}
vm.items[3]=null;
getServiceData();
getData();

/*监控分页工具选择页码的变化，若变更则执行后台调用*/
scope.$watch('page',function(val1,val2){
	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
		 getData3();
	  }
},true);
}]);