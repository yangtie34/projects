app.controller("TeaStuController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	var page = {
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
	scope.page=angular.copy(page);
	scope.page1=angular.copy(page);
	  scope.stujxDiv1=true;
	  scope.stujxDiv2=true;
	  scope.stuxzDiv1=true;
	  scope.stuxzDiv2=true;
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="teaStuService?"; 
	  
	  var methods=['stuxzGrade',	//0班级信息
	               'stuxzbxx',	//1学生信息列表
	               'stuxbbl',	//2学生性别比例
	                'getStuxx'	    ,  //3学生信息
	                
	                'jxStuGrade', //4教学班级信息
	                'jxStutgl' ,  //5学生学科通过率
	                'jxStuxbbl',  //6全部人数性别比例
	                'jxStuqjxb',  //7请假人数性别比例
	                'getjxStuxx',  //8获得教学学生信息
	                'getStuxxmx',   //9获得教学学生信息明细
	                
	                'xzStuqjxb'//10行政请假性别比例
	               ];
	  scope.xzgradeId=''; scope.jxgradeId='';
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  params:[],
			  service:httservice+methods[i]
		  });
	 }
};
var getalldata=function(){
	htt[0].params=[userId];
	http.callService(htt[0]).success(function(data){
		vm.items[0] = data;
	  });
	getxzData('');
	htt[4].params=[userId];
	http.callService(htt[4]).success(function(data){
		vm.items[4] = data;
	  });
	getjxData('');
}
var getxzData=function(){
	htt[2].params=[userId,scope.xzgradeId];
	http.callService(htt[2]).success(function(data){
		vm.items[2]={type:'sex',data:[data[0].VALUE,data[1].VALUE],toolbox:false};
		//vm.items[2]=echarbzhx(data,"男女比例");
	  });
	htt[10].params=[userId,scope.xzgradeId];
	http.callService(htt[10]).success(function(data){
		vm.items[10]={type:'sex',data:[data[0].VALUE,data[1].VALUE],toolbox:false};
		//vm.items[2]=echarbzhx(data,"男女比例");
	  });
	getxzlb();
}
var xzinit=0;
var getxzlb=function(){
	htt[1].params=[userId,scope.xzgradeId];
	htt[1].params.push(scope.page.currentPage || 1);
	htt[1].params.push(scope.page.numPerPage || 10);
	mask.showLoading();
	http.callService(htt[1]).success(function(data){
		vm.items[1] = data.resultList;
		 scope.page.totalRows=data.totalRows;
		  scope.page.totalPages=data.totalPages;
		  mask.hideLoading();
		  if(data.resultList.length==0&&xzinit==0){
			  scope.stujxDiv1=false;
			  scope.stujxDiv2=false;
			  xzinit++;
		  }
	  });
}
var getjxData=function(gradeId){
	htt[5].params=[userId,scope.jxgradeId];
	http.callService(htt[5]).success(function(data){
		vm.items[5]= {
				  title : {
				        text: '课程通过率',
				    },
		  	      big:'10',
		  	      type :'column',
		  	      data:data,
		  	      legendSort:false,
		  	      isSort:false,
	              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	               type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
	              },
	        toolbox: {
	        	 formatter : '{value}%' ,
//	        orient : 'vertical',
//	        x : 'right',
	        show : true,
	        feature : {
	            mark : {show: false},
	            dataView : {show: false, readOnly: false},
	            magicType : {show: true, type: ['tiled']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	     series : {
	            type:'bar',
	            stack:'总人数'
	     },
	  	  xAxis : [
	        {
	            name:'',
	            boundaryGap : true
	        }
	    ], 
	     legend:{
		  	      	show : true
		  	      },
	      yAxis : [
	        {
	            name:'通过率%',
	            splitLine:{ 
                    show:false 
             },
	            axisLabel : { 
                    formatter : '{value}%' 
	            	}, 
	            	min:0, 
	            	max:100 ,
	            	type:'value'
	        }
	    ],  
	     dataZoom : {
	        show : true,
	        realtime : true,
	        start : 0,
	        end : 80
	     }
	    };
	  });
	//全部人数--性别比例
	htt[6].params=[userId,scope.jxgradeId];
	http.callService(htt[6]).success(function(data){
		vm.items[6]={type:'sex',data:[data[0].VALUE,data[1].VALUE]};
	  });
	//请假--性别比例
	htt[7].params=[userId,scope.jxgradeId];
	http.callService(htt[7]).success(function(data){
		vm.items[7]={type:'sex',data:[data[0].VALUE,data[1].VALUE]};
	  });
	getjxlb();
}
var jxinit=0;
var getjxlb=function(){
	vm.items[8]=null;
	htt[8].params=[userId,scope.jxgradeId];
	htt[8].params.push(scope.page1.currentPage || 1);
	htt[8].params.push(scope.page1.numPerPage || 10);
	http.callService(htt[8]).success(function(data){
		vm.items[8] = data.resultList;
		 scope.page1.totalRows=data.totalRows;
		  scope.page1.totalPages=data.totalPages;
		  if(vm.items[8].length==0&&jxinit==0){
			  scope.stuxzDiv1=false;
			  scope.stuxzDiv2=false;
			  jxinit++;
		  }
	  });	
}
scope.stuTitles=['姓名','学士学号','性别','政治面貌','民族','来校时间','出生日期','院系专业','身份证号',
            	   '班级','户籍地址','学习方式','婚姻状况','学籍状态','联系方式'];
  var getxzstuxx=function(stuId){
  	htt[3].params=[stuId];
  	htt[3].service='stuInfoService?getInfo';
  	http.callService(htt[3]).success(function(data){
  		vm.items[3] = data;
  		scope.stuxxDiv=true;
  	  });
  }
  
var getjxstuxx=function(stuId){
	//信息明细
	htt[9].params=[stuId];
	http.callService(htt[9]).success(function(data){
		vm.items[9] = data[0];
		scope.jxstuxxDiv=true;
	  });
}


mask.showLoading();
getServiceData();
getalldata();

mask.hideLoading();

scope.xzgradeClick=function(gradeId){
	scope.page=angular.copy(page);
	scope.xzgradeId=gradeId;
	getxzData();
};
scope.jxgradeClick=function(gradeId){
	scope.page1=angular.copy(page);
	scope.jxgradeId=gradeId;
	getjxData();
};
scope.stuClick=function(stuId){
	getxzstuxx(stuId);
	//getjxstuxx(stuId);
};
scope.jxstuClick=function(stuId){
	getjxstuxx(stuId);
};
/*监控分页工具选择页码的变化，若变更则执行后台调用*/
scope.$watch('page',function(val1,val2){
	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
		  getxzData();
	  }
},true);
/*监控分页工具选择页码的变化，若变更则执行后台调用*/
scope.$watch('page1',function(val1,val2){
	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
		  getjxData();
	  }
},true);
}]);