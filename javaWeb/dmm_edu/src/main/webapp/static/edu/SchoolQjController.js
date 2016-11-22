app.controller("SchoolQjController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	scope.echarColor=echarColor;
	scope.warning=function(){$('.alert-warning').hide('slow');}
	scope.title=[{name:'学校基本信息',show:true,now:true},
	             {name:'在校生(招生、在校、毕业)',show:true,now:false},
	             {name:'教职工情况',show:true,now:false},
	             {name:'校舍情况',show:true,now:false},
	             {name:'资产情况',show:true,now:false},
	             {name:'教学维度',show:true,now:false},
	             {name:'科研维度',show:true,now:false},
	             {name:'其他维度',show:true,now:false},
	             ];
	scope.titleNow=function(item){
		var index=0;
		for(var i=0;i<scope.title.length;i++){
			scope.title[i].now=false;
			if(scope.title[i].name==item.name){
				index=i;	
			}
		}
		item.show=false;
		item.now=true;
		scope.titleShow(item,index);
	}
	scope.titleShow=function(item,index){
		item.show=!item.show;
		if(item.show){
			$("#edu-0"+(index+1)).next().show('slow');;
		}else{
			$("#edu-0"+(index+1)).next().hide('slow');;
		}
	}
	var map = {};
	//1m²=0.0015亩
	var getMapData1=function(){
		map = {};
		map['11C']='成立日期';
		map['115']='邮编';
		map['118']='办公电话';
		map['11A']='网址';
		map['119']='传真电话';
		map['114']='学校地址';
		map['112']='学校(机构)代码结构(全部)';
		map['1121']='学校(机构)标识码';
		map['11221']='地址代码';
		map['11222']='属地管理教育行政部门代码';
		map['11223']='举办者代码';
		map['11224']='办学类型代码';
		map['11225']='性质类别代码';
		map['11226']='城乡分类代码';
		map['11227']='独立设置少数民族校代码';
		map['1135']='举办者名称';
		map['1131']='办别';
		map['1132']='举办者性质';
		map['1133']='举办者类型分组';
		map['1134']='举办者编码';
	}
	//在校生情况
	var getMapData2=function(){
		map = {};
		map['121']='在校生情况';
		map['1211']='在校生数';
		map['121111']='普通本科、专科生';
		map['1211111']='普通专科生';
		map['1211112']='普通本科生';
		map['121112']='成人本科、专科生';
		map['1211121']='成人专科生';
		map['1211122']='成人本科生';
		map['121113']='网络本科、专科生';
		map['1211131']='网络专科生';
		map['1211132']='网络本科生';
		map['121114']='研究生';
		map['1211141']='硕士研究生';
		map['1211142']='博士研究生';
		map['12112']='女';
		map['121121']='普通本科、专科生';
		map['1211211']='普通专科生';
		map['1211212']='普通本科生';
		map['121122']='成人本科、专科生';
		map['1211221']='成人专科生';
		map['1211222']='成人本科生';
		map['121123']='网络本科、专科生';
		map['1211231']='网络专科生';
		map['1211232']='网络本科生';
		map['121124']='研究生';
		map['1211241']='硕士研究生';
		map['1211242']='博士研究生';
		map['1212']='招生数';
		map['121211']='普通本科、专科生';
		map['1212111']='普通专科生';
		map['1212112']='普通本科生';
		map['121212']='成人本科、专科生';
		map['1212121']='成人专科生';
		map['1212122']='成人本科生';
		map['121213']='网络本科、专科生';
		map['1212131']='网络专科生';
		map['1212132']='网络本科生';
		map['121214']='研究生';
		map['1212141']='硕士研究生';
		map['1212142']='博士研究生';
		map['12122']='女';
		map['121221']='普通本科、专科生';
		map['1212211']='普通专科生';
		map['1212212']='普通本科生';
		map['121222']='成人本科、专科生';
		map['1212221']='成人专科生';
		map['1212222']='成人本科生';
		map['121223']='网络本科、专科生';
		map['1212231']='网络专科生';
		map['1212232']='网络本科生';
		map['121224']='研究生';
		map['1212241']='硕士研究生';
		map['1212242']='博士研究生';
		map['1213']='毕业生数';
		map['12131']='总计';
		map['121311']='普通本科、专科生';
		map['1213111']='普通专科生';
		map['1213112']='普通本科生';
		map['121312']='成人本科、专科生';
		map['1213121']='成人专科生';
		map['1213122']='成人本科生';
		map['121313']='网络本科、专科生';
		map['1213131']='网络专科生';
		map['1213132']='网络本科生';
		map['121314']='研究生';
		map['1213141']='硕士研究生';
		map['1213142']='博士研究生';
		map['12132']='女';
		map['121321']='普通本科、专科生';
		map['1213211']='普通专科生';
		map['1213212']='普通本科生';
		map['121322']='成人本科、专科生';
		map['1213221']='成人专科生';
		map['1213222']='成人本科生';
		map['121323']='网络本科、专科生';
		map['1213231']='网络专科生';
		map['1213232']='网络本科生';
		map['121324']='研究生';
		map['1213241']='硕士研究生';
		map['1213242']='博士研究生';
		
	}
	//教职工情况
	var getMapData3=function(){
		map = {};
		map['1221']='教职工数';
		map['12211']='女';
		map['12213']='校本部教职工';
		map['1222']='聘请校外教师(人次)';
		map['1223']='离退休人员';
		//部门类别
		map['12213']='校本部教职工';
		map['12214']='科研机构人员';
		map['12215']='校办企业职工';
		map['12216']='其他附设机构人员';
		//人员类别
		map['12261']='专任教师';
		map['1222']='聘请校外教师(人次)';
		map['1223']='离退休人员';
		map['1224']='附属中小学幼儿园教职工';
		map['1225']='集体所有制人员';
		//职称
		map['1221331']='正高级';
		map['1221332']='副高级';
		map['1221333']='中级';
		map['1221334']='初级';
		map['1221335']='未定职级';
		//学历
		map['122612']='博士研究生';
		map['122613']='硕士研究生';
		map['122614']='本科';
		map['122615']='专科及以下';
		//学位
		map['1226111']='博士';
		map['1226112']='硕士';
	}
	//校舍情况
	var getMapData4=function(){
		map = {};
		map['1234']='生活用房';
		map['12341']='学生宿舍(公寓)';
		map['12342']='学生食堂';
		map['12343']='教工宿舍(公寓)';
		map['12344']='教工食堂';
		map['12345']='生活福利及附属用房';
	}
	//资产情况
	var getMapData5=function(){
		map = {};
		map['1241']='占地面积(平方米)';
		map['12411']='绿化用地面积';
		map['12412']='运动场地面积';
		map['1243']='计算机数(台)';
		map['12431']='教学用计算机';
		map['12451']='教学、科研仪器设备资产值';
		map['12452']='信息化设备资产值';
		map['1242']='图书(万册)';
		map['12421']='当年新增';
		map['1244']='教室（间）';
		map['12441']='网络多媒体教室';
	}
	var getMapkeys=function(){
		var keys=[];
		for(var key in map){
			keys.push(key);
		}
		htt[1].params.push(keys);		
	}
	 var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="schoolQJService?"; 
	  
	  var methods=['getSchoolInfo',	//0获取筛选信息
	               'getSchoolInfoDetails'//1获取学校
	               ];
	  
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  service:httservice+methods[i],
			  params:[schoolId]
		  });
	 }
};
var quhao='';//区号
scope.xsbzt=[];
getServiceData();

var getDetails=function(params,index){
	htt[1].params=[schoolId];getMapkeys();
	for(var i=0;i<params.length;i++){
		htt[1].params.push(params[i]);
	}
	 http.callService(htt[1]).success(function(d){
		 if(index==1){
			 for(var key in d){
			 var sss=d[key].name+" ："+d[key].value;
			 d[key].out=sss;
			 if(key=='118'||key=='119'){
					 var a=d[key].value.split('-');
					 if(a.length>1) quhao=a[0];
			 }
		 }
			 if(d['118'].value.split('-').length==1)
			 d['118'].out=d['118'].out.replace(' ：',' ：'+quhao+'-');
			 if(d['119'].value.split('-').length==1)
				 d['119'].out=d['119'].out.replace(' ：',' ：'+quhao+'-');
		 }
		 if(index==4){
			 function round(v,e){
				 var t=1;
				 for(;e>0;t*=10,e--);
				 for(;e<0;t/=10,e++);
				 return Math.round(v*t)/t;
				 }
			 var xsbzt=[];
			 for(var key in d){
				 if(key!='1234'){
					 xsbzt.push({FIELD:d[key].name,VALUE:round(d[key].value*0.0015, 2 ),NAME:'面积(亩)'});	  
				 }
			 }
			 scope.xsbzt=echarbzt(xsbzt,{text:''}); 
			 scope.xsbzt.type='pie';
		 }
		 vm.items[index][1]=d;
		 
   }); 
}
var getInfo=function(){
	 http.callService(htt[0]).success(function(data){
		 if(!data.id){
			 alert("系统不存在此学校的信息！！！");
			 throw new exception;
		 }
		  vm.items[0]=data;
   });
}
var getDatacom=function(index){
	getMapkeys();
	vm.items[index]=[];
	vm.items[index].push(map);
	getDetails([],index);
}
getMapData1();
getInfo();
getDatacom(1);

getMapData3();
getDatacom(3);
getMapData4();
getDatacom(4);
getMapData5();
getDatacom(5);
scope.$watch('year',function(val1,val2){
	if(val1!=null){
		getMapData2();
		getMapkeys();
		vm.items[2]=[];
		vm.items[2].push(map);
		getDetails([val1.start,val1.end],2);
	}
},true);
}]);