app.controller("StuSchLifeController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.echarColor=echarColor;
	page = {
			  totalPages : 0,
			  totalRows : 0,
			  currentPage : 1,
			  numPerPage : 10,
			  conditions : []
	 	};
	scope.pageGz=page;
	scope.pageXf=page;	

	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="stuSchLifeService?"; 
	  
	  var methods=[
	               'yktxf',		//0一卡通消费+余额
	               'yktcz',		//1一卡通充值记录
	               'yktxffx',	//2一卡通消费分析+走势
	               'yktxfmx',//3一卡通消费明细
	               'ieAvgTime',//4平均上网时间
	               'ieAllTime',//5总上网时间
	               'schFirst',//6大学首次
	               ];
	  
	  var getServiceData=function(){
		  for(var i=0;i<methods.length;i++){
			  htt.push({
				  params:[userId],
				  service:httservice+methods[i]
			  });
		 }
	};
	var get3woption=function(data,typ){
		var option=echarswt(data,typ,{text:'111'});
		delete option.title;//删除图形的标题
		delete option.toolbox;//option.toolbox保存为图片
		delete option.legend;//
		return option;
	};
	
	//日均上网时间
	var getData4=function(){
		htt[4].service=htt[4].service.replace("stuSchLife", "teaSchLife");
		htt[5].service=htt[5].service.replace("stuSchLife", "teaSchLife");
		htt[4].params=[userId,scope.date3.startTime,scope.date3.endTime];
		http.callService(htt[4]).success(function(data){
			//上网账号,开始时间,最小值,最大值,日均上网时间 
			data=[{CL03:5,CL04:6,CL05:4}]
			var d=[{FIELD:'最低',VALUE:data[0].CL03,NAME:'上网时间'},
			       {FIELD:'平均',VALUE:data[0].CL04,NAME:'上网时间'},
			       {FIELD:'最高',VALUE:data[0].CL05,NAME:'上网时间'},
			       ]
			vm.items[4] =get3woption(d,'line');
		  })
			htt[5].params=[userId,scope.date3.startTime,scope.date3.endTime];
			http.callService(htt[5]).success(function(data){
				data=[{grsw:[
					            {SSUM_:15} 
					             ],
						xxsw:[ {MIN_:5,MAX_:20,AVG_:15,SUM_:100} 
						      ]}]
					var d=[{FIELD:'个人累计',VALUE:data[0].grsw[0].SSUM_,NAME:'上网时间'},
					       {FIELD:'校平均',VALUE:data[0].xxsw[0].AVG_,NAME:'上网时间'},
					       {FIELD:'校累计最低',VALUE:data[0].xxsw[0].MIN_,NAME:'上网时间'},
					       ]
			var option=get3woption(d,'bar');
			var y=option.xAxis;
			option.xAxis=option.yAxis;
			option.yAxis=y;
			vm.items[5] = option;
		  });		  
	
	};
	//一卡通消费
	var getData1=function(){
		htt[0].service=htt[0].service.replace("stuSchLife", "teaSchLife");
		htt[0].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[0]).success(function(data){
			var d=[];
			var ye=null;
			ye=data[0].VALUE||0;
			for(var i=0;i<data[1].length;i++){
					d.push(data[1][i]);
			}
			vm.items[0] ={
					option:echarbzhx(d,"消费"),
					list:d,
					ye:ye
				}; 
		  });
	};
	//消费明细
	scope.xfmxTitles=['序号','消费内容','消费金额','消费时间'];
	var getXfData=function(){
		htt[3].params=[userId,scope.date2.startTime,scope.date2.endTime];
		htt[3].params.push(scope.pageXf.currentPage || 1);
		htt[3].params.push(scope.pageXf.numPerPage || 10);
		http.callService(htt[3]).success(function(data){
			vm.items[3] = data.resultList;
			 scope.pageXf.totalRows=data.totalRows;
			  scope.pageXf.totalPages=data.totalPages;
		  });
	};
	//一卡通充值
	var getData2=function(){
		htt[1].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[1]).success(function(data){

			var m={};
			for(var i=0;i<data.length;i++){
				if(!m[data[i].FIELD])m[data[i].FIELD]=0;
				m[data[i].FIELD]+=Number(data[i].VALUE); 
			}
			var list=[];
			var xjcz={allval:0};
			for(var key in m){
				if(key.indexOf("现金充值")==0){
					xjcz.val=Number(m[key]);
				};
				xjcz.allval+=Number(m[key]);
				list.push({
					FIELD:key,
					VALUE:m[key]
				});
			}
			xjcz.val=xjcz.val||0;
			xjcz.bl=Percentage(xjcz.val,xjcz.allval);
			vm.items[1] = {
					option:echarbzhx(list,"充值"),
					list1:list,
					list2:data,
					xjcz:xjcz
				}; 
		  });
		//消费分析+走势
		htt[2].service=htt[2].service.replace("stuSchLife", "teaSchLife");
		htt[2].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[2]).success(function(data){
			vm.items[2] ={
					xffx:get3woption(data[0],'line'),
					rjxf:data[1][0].VALUE
			}
			//vm.items[2] =get3woption(data,'line');
		  });
		getXfData();
	};
	//矿大首次
	var getData5=function(){
		htt[6].params=[userId];
		http.callService(htt[6]).success(function(data){
			data=data[0];
//			dt=[//{NAME_:data[0].jc[0].PAY_MANEY,TIME:data[0].jc[0].TIME_},
//			    {NAME_:data[0].gw[0].PAY_MONEY,TIME:data[0].gw[0].TIME_},
//			    {NAME_:data[0].jy[0].NAME_,TIME:data[0].jy[0].TIME_},
//			    {NAME_:data[0].ry[0].NAME_,TIME:data[0].ry[0].TIME_}]
		
			vm.items[6] = data;
		  });
	};
	getServiceData();
	getData5();
	  /*监控变化，若变更则执行后台调用*/
	  scope.$watch('date2',function(val1,val2){
		  if(val1!=null){
			  getData1();
			  getData2();
			} 
	  },true);
	  /*监控变化，日均上网时间*/
	  scope.$watch('date3',function(val1,val2){
			if(val1!=null){
				getData4();
			}
	  },true);
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('pageXf',function(val1,val2){
	  	  if(val1.currentPage != val2.currentPage && angular.toJson(val1.conditions) == angular.toJson(val2.conditions)||val1.numPerPage!=val2.numPerPage){
	  		  getXfData();
	  	  }
	  },true);
	  function Percentage(num, total) { 
		    return (Math.round(num / total * 10000) / 100.00 + "%");// 小数点后两位百分比
		}	
}]);