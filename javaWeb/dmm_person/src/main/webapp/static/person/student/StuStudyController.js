app.controller("StuStudyController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	scope.pagecj={
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
	  var httservice="stuStudyService?"; 
	  
	  var methods=['pushBooks',	//0推荐图书
	               'jyfl',		//1借阅分类
	               'jysl',		//2借阅数量
	               'jymx',		//3借阅明细
	               'grkb',		//4个人课表
	               'skcj'		//5成绩查询
	               ];
	  
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  params:[userId],
			  service:httservice+methods[i]
		  });
	 }
};
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
	htt[0].params=[userId,scope.date1.startTime,scope.date1.endTime];
	http.callService(htt[0]).success(function(data){
		vm.items[0] = data;
	  });
	//借阅分类
	htt[1].params=[userId,scope.date1.startTime,scope.date1.endTime];
	http.callService(htt[1]).success(function(data){
		vm.items[1] = get3woption(data,'bar');
	  });
	//借阅数量
	htt[2].service=htt[2].service.replace("stuSchLife", "teaSchLife");
	htt[2].params=[userId,scope.date1.startTime,scope.date1.endTime];
	http.callService(htt[2]).success(function(data){
	dt=[{FIELD:'最低',VALUE:data[0].MIN_,NAME:'借阅数量'},
	{FIELD:'我的',VALUE:data[0].AVG_,NAME:'借阅数量'},
	{FIELD:'最高',VALUE:data[0].MAX_,NAME:'借阅数量'}]
	vm.items[2] ={
			sum:data[0].SUM_,
			option:get3woption(dt,'line')
		}
	  });
	getJymxData();

};
//个人课表
var getgrkbData=function(){
	htt[4].params=[userId];
	http.callService(htt[4]).success(function(data){
		//课程名，班级名，第几星期，开始节，结束节
		vm.items[4] ={kbkeys:['COURSE_NAME','CLASSROOM','DAY_OF_WEEK','PERIODSTART','PERIODEND'],
				data:data}
	  });		
}
scope.getcjcxTitles=['','学年','学期','课程','综合成绩','等级/学分'];
//成绩查询
var getcjcxData=function(){
	htt[5].params=[userId];
	htt[5].params.push(scope.pagecj.currentPage || 1);
	htt[5].params.push(scope.pagecj.numPerPage || 10);
	http.callService(htt[5]).success(function(data){
/*		data={};
		data.resultList=[{C1:'2012-2013第一学期',C2:'java基础课程',C3:'93',C4:'98',C5:'99',C6:'2'},
		      {C1:'2012-2013第一学期',C2:'java web基础',C3:'88',C4:'90',C5:'89',C6:'1'},
			  {C1:'2012-2013第一学期',C2:'软件工程概论',C3:'99',C4:'78',C5:'80',C6:'3'},
			  {C1:'2012-2013第一学期',C2:'java基础课程',C3:'93',C4:'98',C5:'99',C6:'2'},
		      {C1:'2012-2013第一学期',C2:'java web基础',C3:'88',C4:'90',C5:'89',C6:'1'},
			  {C1:'2012-2013第一学期',C2:'软件工程概论',C3:'99',C4:'78',C5:'80',C6:'3'}];*/
		  vm.items[5] = data.resultList;
		  scope.pagecj.totalRows=data.totalRows;
		  scope.pagecj.totalPages=data.totalPages;
	  });
};


/*getVmData();
getVmKyData();	*/ 
getServiceData();
getgrkbData();	  
	  
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('date1',function(val1,val2){
			if(val1!=null){
			
			    getVmData();
			    getcjcxData();
			}
	  },true);
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('date2',function(val1,val2){
			if(val1!=null){
				getServiceData();
				getgrkbData();
			}
	  },true);	  
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
	  	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
	  		getJymxData();
	  	  }
	  },true);	
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('pagecj',function(val1,val2){
	  	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
	  		getcjcxData();
	  	  }
	  },true);	
}]);