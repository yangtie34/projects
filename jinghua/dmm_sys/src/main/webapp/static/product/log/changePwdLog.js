
app.controller("changePwdLogController", [ "$scope","dialog",'mask','$timeout','http','timeAlert','exportPage',function(scope,dialog,mask,timeout,http,timeAlert,exportPage) {
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  scope.page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	  var htt={};
	  htt.codeData={service:"codeService?getCode"};
	  htt.logData={service:"changePwdLogService?getChangePwdLog"};
	  //getChangePwdLog(int currentPage,int numPerPage,int totalRow,String sort,boolean isAsc,ChangePwdLog changePwdLog);
	  htt.logClearData={service:"changePwdLogService?clearChangePwdLog"};
	  
	  
	  scope.code={excType:[]};
	  htt.codeData.params=[{code_type:'EXC_TYPE_CODE'}];
	  http.callService(htt.codeData).success(function(data){
			  for(var i=0;i<data.length;i++){
				  scope.code.excType.push({id:data[i].code_,mc:data[i].name_});
			  };
	      }); 
	  
	  scope.searchLog={};
	  // 复合查询组件模拟数据
	  scope.mutiSource = [];
	  scope.mutiSource=[{
			queryName : '业务类型',// 名称
			queryCode : "excType",
			items :  scope.code.excType,// 条件数据
		},{
			queryName : '操作者',// 名称
			queryCode : "excUser",
			queryType : 'comboInput',
			items :  [{id:0,mc:'',placeholder:'修改者用户名...'}]// 条件数据
		},{
			queryName : '被改者',// 名称
			queryCode : "changeUser",
			queryType : 'comboInput',
			items :  [{id:0,mc:'',placeholder:'被修改者用户名...'}]// 条件数据
		},{
			queryName : '修改时间',// 名称
			queryCode : "changeTime",
			queryType : 'comboDate'			
		}];
	  scope.title={
			  codes:'USERNAME,EXC_TIME,EXC_USERNAME,EXC_IP,EXC_TYPE_NAME'.split(','),
			  names:'被改者,修改时间,操作者,操作IP,修改类型'.split(','),
			  click:[]
	  }
	  scope.queryGridContent = function(){
		  mask.showLoading();
		  var params=[];
		  params.push(scope.page.currentPage || 1);
		  params.push(scope.page.numPerPage || 10);
		  params.push(scope.page.totalRow || 0);
		  params.push(scope.page.sort||'EXC_TIME');
		  params.push(scope.page.isAsc||false);
		  params.push(scope.searchLog);
		  htt.logData.params=params;
  		  http.callService(htt.logData).success(function(data){
			  vm.items=data.resultList;
			  scope.page.totalRows=data.totalRows;
			  scope.page.totalPages=data.totalPages;
			  mask.hideLoading();
	      }); 
		  
	  };
	  scope.queryGridContent();
		scope.orderReload=function(index){
			var sort=scope.title.codes[index];
			var isAsc=scope.title.click[index];
			if(isAsc==null){
				isAsc=true;
			}else if(isAsc==true){
				isAsc=false;
			}else if(isAsc==false){
				isAsc=true;
			}
			scope.title.click=[];
			scope.title.click[index]=isAsc;
			scope.page.sort=sort;
			scope.page.isAsc=isAsc;
			 scope.queryGridContent();
		};
	 scope.exportExcel=function(){
		var invoke=angular.copy(htt.logData);
		 invoke.params[0]=1;
		 invoke.params[1]=scope.page.totalRows;
		 var ex= {
			 invoke:invoke,
			 title:'修改密码日志记录',
			 titles:scope.title.names,
			 titleCodes:scope.title.codes
		 }
		exportPage.callService(ex).success(function(ret){
			
		})
	 }
	  scope.logClear=function(){
		  mask.showLoading();
		  var params=[];
		  params.push(scope.searchLog);
		  htt.logClearData.params=params;
  		  http.callService(htt.logClearData).success(function(data){
  			timeAlert.success("成功删除"+data+"条日志记录！");
			  mask.hideLoading();
	      }); 
	  }
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('page',function(val1,val2){
		  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
			  scope.queryGridContent();
		  }
		  if(angular.toJson(val1.conditions)!= angular.toJson(val2.conditions)){
			  scope.searchLog={};
			  if(val1.conditions.length>0){
				 for(var i=0;i<val1.conditions.length;i++){
					 	switch(val1.conditions[i].queryCode){
					 		case "excType":
							scope.searchLog.exc_type_code=val1.conditions[i].id;
					 		break;
					 		case "excUser0":
							scope.searchLog.exc_username=val1.conditions[i].val;
						 	break;
					 		case "changeUser0":
								scope.searchLog.username=val1.conditions[i].val;
							 	break;
					 		case "changeTime":
					 		scope.searchLog.exc_time_start=val1.conditions[i].date.startTime;
					 		scope.searchLog.exc_time_end=val1.conditions[i].date.endTime;
					 		break;
					 	}
					 }
			  }
			  scope.queryGridContent();
		  }
	  },true);	
}]);
