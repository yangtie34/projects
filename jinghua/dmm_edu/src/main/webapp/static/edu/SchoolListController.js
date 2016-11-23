app.controller("SchoolListController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 var page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 20,
			  conditions : []
	 	};
	 scope.page=angular.copy(page);
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="schoolListService?"; 
	  
	  var methods=['getFilter',	//0获取筛选信息
	               'getSchools'//1获取学校
	               ];
	  
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i]
		  });
	 }
};
var getfilter=function(){
	var data=htt[0];
	  data.params=[];
	 http.callService(data).success(function(data){
		  vm.items[0]=data;
   }); 
}
scope.schoolName='';
getServiceData();
getfilter();
scope.queryGridContent = function(){
	  mask.showLoading();
	  var data=htt[1];
	  data.params=[{schoolName:scope.schoolName,list:scope.page.conditions}];
	  data.params.push(scope.page.currentPage || 1);
	  data.params.push(scope.page.numPerPage || 20);
	  http.callService(data).success(function(data){
		  vm.items[1]=data.resultList;
		  scope.page.totalRows=data.totalRows;
		  scope.page.totalPages=data.totalPages;
		  mask.hideLoading();
		  alert(pinyin("我是中国人"));
    }); 
};
scope.queryGridContent();
/*监控分页工具选择页码的变化，若变更则执行后台调用*/
scope.$watch('page',function(val1,val2){
	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
		  scope.queryGridContent();
	  }
	  if(angular.toJson(val1.conditions)!= angular.toJson(val2.conditions)){
		scope.page.currentPage =1;
		scope.page.numPerPage = 20;
		  scope.queryGridContent();
	  }
},true);
scope.myKeyup=function(e){
	  var keynum;
		if(window.event) 
	  	{
	  		keynum = e.keyCode;
	  	} else if(e.which) 
	  	{
	  		keynum = e.which;
	  	}
	  	if(keynum==13){
	  		scope.page.currentPage =1;
			scope.page.numPerPage = 20;
	  		scope.queryGridContent();
	  	}
};	  
}]);