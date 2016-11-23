app.controller("TeaKyController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	var get3woption=function(data,typ){
		var option=echarswt(data,typ,{text:''});
		delete option.title;
		delete option.toolbox;
		delete option.legend;
		return option;
	}
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="teaKyService?"; 
	  
	  var methods=['pushBooks',	//0推荐图书
	               'jyfl',		//1借阅分类
	               'jysl',		//2借阅数量
	               'jymx',		//3借阅明细
	               'kyxx',		//4科研信息
	               'zczk',		//5科研信息
	               ];
	  
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  params:[userId,scope.date1.startTime,scope.date1.endTime],
			  service:httservice+methods[i]
		  });
	 }
};
 scope.sbzcTitles=['资产编号','存放地','资产名称','使用状况','分类','盘点结果','单价','损益类型','购置日期'];
 scope.gfsyTitles=['房间号','面积','位置','用途','房间名称','开始时间'];
 scope.jymxTitles=['序号','名称','借书时间','应还时间','归还时间','借书状态'];//id,名称,借书时间,应还时间,归还时间,借书状态
getJymxData=function(){
	htt[3].params=[userId,scope.date1.startTime,scope.date1.endTime];
	htt[3].params.push(scope.page.currentPage || 1);
	htt[3].params.push(scope.page.numPerPage || 10);
	http.callService(htt[3]).success(function(data){
		vm.items[3] = data.resultList;
		 scope.page.totalRows=data.totalRows;
		  scope.page.totalPages=data.totalPages;
	  });	
}
var getVmData=function(){
	//推荐图书
	http.callService(htt[0]).success(function(data){
		vm.items[0] = data;
	  });
	//借阅分类
	htt[1].params=[userId,scope.date1.startTime,scope.date1.endTime];
	http.callService(htt[1]).success(function(data){
		vm.items[1] = get3woption(data,'bar');
	  });
	//借阅数量
	htt[2].params=[userId,scope.date1.startTime,scope.date1.endTime];
	http.callService(htt[2]).success(function(data){
	var dt=[{FIELD:'最低',VALUE:data[0].MIN_,NAME:'借阅数量'},
	{FIELD:'我的',VALUE:data[0].AVG_,NAME:'借阅数量'},
	{FIELD:'最高',VALUE:data[0].MAX_,NAME:'借阅数量'}]
		vm.items[2] ={
		sum:data[0].SUM_,
		option:get3woption(dt,'line')
	}
	  });
	getJymxData();
};
//科研信息
var getVmKyData=function(){
	htt[4].params=[userId,scope.date2.startTime,scope.date2.endTime];
	http.callService(htt[4]).success(function(data){
		vm.items[4] = data[0];
	  });
};
//资产状况
var getzcData=function(){
	//htt[5].params=[userId,scope.date2.startTime,scope.date2.endTime];
	//http.callService(htt[5]).success(function(data){
		data=[{CL01:200916,CL03:'办公桌',CL05:'04070708 家具类',CL07:4900,CL09:'2000-07-01',CL02:'教2楼1302',CL04:'正常',CL06:'正常',CL08:'不折旧'},
		      {CL01:200916,CL03:'微型计算机',CL05:'04070708 电子仪器类设备',CL07:4900,CL09:'2000-07-01',CL02:'教2楼1302',CL04:'正常',CL06:'正常',CL08:'不折旧'}];
		vm.items[5] = data;
	 // });	
		//htt[5].params=[userId,scope.date2.startTime,scope.date2.endTime];
		//http.callService(htt[5]).success(function(data){
			data=[{CL01:1201,CL03:'21#3层',CL05:'副校长办公室',CL02:'43平米',CL04:'办公室',CL06:'2013-12-01'},
			      {CL01:1202,CL03:'22#3层',CL05:'副校长办公室',CL02:'43平米',CL04:'办公室',CL06:'2013-12-01'},
			      {CL01:1203,CL03:'23#3层',CL05:'副校长办公室',CL02:'43平米',CL04:'办公室',CL06:'2013-12-01'}];
			vm.items[6] = data;
		 // });	
}
getzcData();
/*getVmData();
getVmKyData();	*/  
	  
	  
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('date1',function(val1,val2){
			if(val1!=null){
				getServiceData();
			  getVmData();
			}
	  },true);
	  scope.$watch('date2',function(val1,val2){
		  if(val1!=null){
			   getVmKyData();	
			} 
	  },true);
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
	  	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
	  		getJymxData();
	  	  }
	  },true);
}]);