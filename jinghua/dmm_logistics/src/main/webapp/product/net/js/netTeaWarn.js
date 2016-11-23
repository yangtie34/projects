var jsidxq=null;
app.controller("netTeaWarnController", [ "$scope","dialog",'mask','$timeout','http','timeAlert','exportPage',function(scope,dialog,mask,timeout,http,timeAlert,exportPage) {
	var vm = scope.vm = {};
	  vm.items = [];

	  var htt=[];
	  var httservice="netTeaWarnService?"; //
	  var methods=[	
	        	'getTeaWarn',					//0获取预警教师名单
	        	'getTeaWarnDetil'		//1通过教师ID获取上网记录统计
	               ]; 
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i],
			  params:[]
		  });
	 }
};
getServiceData();

/*DEPT_ID: "0"
	NUM: 1
	ON_IP: "172.16.162.31"
	ON_WORK_NUM: 9
	OUT_WORK_NUM: 0

	STU_NUM: 0
	YEAR_MONTH: 1448899200000*/
	
	scope.titlesCode="PEOPLE_ID,PEOPLE_NAME,SEX_NAME,DEPT_NAME,ON_IP,NUM,ON_WORK_NUM,OUT_WORK_NUM,STU_NUM".split(',');
	scope.titles="工号,姓名,性别,部门,上线IP,次数,工作时间上线次数,非工作时间上线次数,学生上线此ip次数".split(',');
//下钻
scope.getxqlb=function(i){
	var params=[];
	var title='';
	var titles='';var titlesCode='';
	
		title='教师上网记录统计明细';
		titles=scope.titles;
		titlesCode=scope.titlesCode;
	
	var query=function(pg){
		htt[i].params=[pg.currentPage || 1,
				          pg.numPerPage || 10,  pg.totalRows||0,
				          startTime,endTime
				          ,scope.teaId
				          ];
	 if(pg.exportExcel){
		 var invoke=angular.copy(htt[i]);
		 invoke.params[0]=1;
		 invoke.params[1]=pg.totalRows;
		 return{
			 invoke:invoke,
			 title:title,
			 titles:titles,
			 titleCodes:titlesCode
		 }
	 }
	  http.callService(htt[i]).success(function(d){
		  d.title=title+'详情列表';
		  d.titles=titles;
		  d.titlesCode=titlesCode;
		  d.func=query;
		  scope.pageXq=angular.copy(d);
	  });
	};
	scope.pageXq={func:query};
	timeout();
}	  
var getDeptData=function(method){
http.callService({
	  service:'deptTreeService?get'+method,
	  params:['hq:net:netTeaWarn:'+method+':*']
  }).success(function(data){
	var getChildren=function(data){
		var item={};
		item.id=data.id;
		item.mc=data.name_;
		item.level=data.level_;
		item.istrue=data.istrue;
		if(data.children!=null){
			item.children=[];
			for(var i=0;i<data.children.length;i++){
				item.children.push(getChildren(data.children[i]));
			}
		}
		return item;
	};
	scope.mutiSource=null;
	var mutisource=[];
	var setCheck=function(item){
		if(item.level!=null)
		item.checked=true;
		return item;
	}
	mutisource.push({
		queryName : '所属部门',
		queryCode : "comboTree",
		queryType : "comboTree",
		items : setCheck(getChildren(data))
	});
	scope.mutiSource=mutisource;
})
};
getDeptData("DeptTeach");

var initvm=function(){
	vm = scope.vm = {};
	  vm.items = [];
}
 jsidxq=function(id){
	scope.teaId=id;
	scope.getxqlb(1);
}
var getvmData=function(i){
	http.callService(htt[i]).success(function(data){
		 if(i==0){
			 var titles={
					code:"PEOPLE_ID,PEOPLE_NAME,SEX_NAME,DEPT_NAME,xs,WP,OWP,WNP,OWNP",
					name:"id,姓名,性别,部门,异常系数,工作时间公共ip登陆次数,非工作时间公共ip登陆次数,工作时间内非公共ip登陆次数,非工作时间非公共ip登陆次数",
				}
			  scope.tableData={
	  					titles:titles,
						func:function(){
							 mask.showLoading();
							 var invoke=angular.copy(htt[i]);
							 var ex={
							 	 invoke:invoke,
							 	 title:'教师上网账号预警情况',
							 	 titles:titles.name.split(','),
							 	 titleCodes:titles.code.split(',')
							 }
							 mask.hideLoading(); 
							 exportPage.callService(ex).success(function(ret){
							 })
						},
						data:function(){
							var d=angular.copy(data.resultList);
							d.sort(function(a,b){
					            return b.xs-a.xs;
					            });
							for(var i =0;i<d.length;i++){
								d[i].INDEX=i+1;
								d[i].PEOPLE_ID="<a href='javascript:void(0)' onclick='jsidxq(\""+d[i].PEOPLE_ID+"\");'>"+d[i].PEOPLE_ID+"</a>";
							}
							return d;
						}()
						};
		 }else if(i==1){
			 
		 }
		 mask.hideLoading(); 
	  });
	
};
var getparams=function(i){
		 var params=[];
		 if(i==0){
			 params=[1,
			         10, 0,
			          startTime,endTime,deptTeach];
			 htt[i].params=params;
			 getvmData(i);
		 }
		 
};
var getAllData=function(m){
	for(var i=0;i<m.length;i++){
		 mask.showLoading();
		getparams(m[i]);
	}
};
var m=[0];
var startTime=null;
var endTime=null;
var deptTeach=null;
var initId=0;

/*监控时间*/
scope.$watch('date',function(val1,val2){
	if(val1!=null){
		startTime=angular.copy(val1.startTime);
		endTime=angular.copy(val1.endTime);
		if(initId>0){
		getAllData(m);}
	}
},true);
	  
	  /*监控dept*/
		scope.$watch('deptResult',function(val1,val2){
			if(val1==null||val1.length==0)return;
				 deptTeach=val1[0];
				if(initId>0){initvm();getAllData(m);}
		},true);
		
	//初始化数据
		var initData=function(){
			if(initId==0&&startTime!=null&&deptTeach!=null&&initvm!=null){
				initvm();getAllData(m);initId++;
			}else{
				setTimeout(initData,100);
			}
		};
		initData(); 
}]);