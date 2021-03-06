app.controller("stuQjController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var methods=['teaSchLifeService?yktxf',		//0一卡通消费+余额
	               'teaSchLifeService?yktcz',		//1一卡通充值记录
	               'teaSchLifeService?yktxffx',	//2一卡通消费分析
	               
	               'stuStudyService?getTodayCourse',	//3今日课程
	               'teaSchLifeService?ieAvgTime',//4平均上网时间
	               'stuStudyService?pushBooks',	//5推荐图书
	               'stuStudyService?jyfl',		//6借阅分类
	              
	               ];
	  var getServiceData=function(){
		  for(var i=0;i<methods.length;i++){
			  htt.push({
				  params:[userId],
				  service:methods[i]
			  });
		 }
	};
	var getwddt=function(){
		htt[0].params=[userId,scope.date1.startTime,scope.date1.endTime];
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
		htt[1].params=[userId,scope.date1.startTime,scope.date1.endTime];
		http.callService(htt[1]).success(function(data){

			var m={};
			for(var i=0;i<data.length;i++){
				if(!m[data[i].FIELD])m[data[i].FIELD]=0;
				m[data[i].FIELD]+=Number(data[i].VALUE);
			}
			var list=[];
			var xjcz={allval:0};
			for(var key in m){
				if(key.indexOf("现金")==0){
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
					option:echarbzhx(list,"重置记录"),
					list1:list,
					list2:data,
					xjcz:xjcz
				}; 
		  });
		
		htt[2].params=[userId,scope.date1.startTime,scope.date1.endTime];
		http.callService(htt[2]).success(function(data){
			vm.items[2] ={
					xffx:get3woption(data[0],'line'),
					rjxf:data[1][0].VALUE
			}
		  });
	}
	var get3=function(){
		http.callService(htt[3]).success(function(data){
			vm.items[3] = data;
		  });
	}
	var getkdsh=function(){
		htt[4].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[4]).success(function(data){
			//上网账号,开始时间,最小值,最大值,日均上网时间 
			//data=[{CL03:5,CL04:6,CL05:4}]
			scope.rjswsj=data[0].CL04||0;
			var d=[{FIELD:'最低',VALUE:data[0].CL03,NAME:'上网时间'},
			       {FIELD:'平均',VALUE:data[0].CL04,NAME:'上网时间'},
			       {FIELD:'最高',VALUE:data[0].CL05,NAME:'上网时间'},
			       ]
			vm.items[4] =get3woption(d,'line');
		  });
		//推荐图书
		htt[5].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[5]).success(function(data){
			vm.items[5] = data;
		  });
		//借阅分类
		htt[6].params=[userId,scope.date2.startTime,scope.date2.endTime];
		http.callService(htt[6]).success(function(data){
			vm.items[6] = get3woption(data,'bar');
		  });
	}
	var get3woption=function(data,typ){
		var option=echarswt(data,typ,{text:''});
		delete option.title;
		delete option.toolbox;
		delete option.legend;
		return option;
	}
	 function Percentage(num, total) { 
		    return ((Math.round(num / total * 10000) / 100.00)||0 + "%");// 小数点后两位百分比
		}
	 scope.echarColor=echarColor;
	 getServiceData();
	 get3();
	  /*监控分页工具选择页码的变化，若变更则执行后台调用*/
	  scope.$watch('date1',function(val1,val2){
			if(val1!=null){
			  getwddt();
			}
	  },true);
	  scope.$watch('date2',function(val1,val2){
		  if(val1!=null){
			   getkdsh();	
			} 
	  },true);

}]);