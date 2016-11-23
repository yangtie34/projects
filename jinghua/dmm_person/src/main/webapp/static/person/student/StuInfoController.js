app.controller("StuInfoController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	//分页指令的测试数据
	  var vm = scope.vm = {};
	  vm.items = [];
	  var htt=[];
	  var httservice="stuInfoService?"; 
	  scope.a=false;
	  var methods=['getInfo',	//0获取学生信息
	               'getGlory',  //1获得荣誉
	               'getXjyd',	//2学籍异动
	               'getSsZsxx',	//3获得学生宿舍信息
	               'getSsTzxx'	//4获得宿舍调整信息
	               
	               ];
	  
var getServiceData=function(){
	  for(var i=0;i<methods.length;i++){
		  htt.push({
			  params:[userId],
			  service:httservice+methods[i]
		  });
	 }
};
scope.stuInfoTitles=['姓名','学士学号','性别','政治面貌','民族','来校时间','出生日期','院系专业','身份证号',
              	   '班级','户籍地址','培养方式','婚姻状况','学籍状态','联系方式'];
scope.stugetGloryTiltles=['学年','学期','类型','金额','奖励日期'];
scope.getXjydTiles=['异动日期','异动类型','原院系','原专业','原班级','现院系','现专业','现班级'];
var getVmData=function(){
	//获得学生信息
	http.callService(htt[0]).success(function(data){
		vm.items[0] = data;
	  });
	//学生获得荣誉及奖励
	http.callService(htt[1]).success(function(data){
//		data=[{C1:'2012-2013',C2:'第一学期',C3:'校二等奖学金',C4:'400',C5:'2013-0-12'},
//		      {C1:'2013-2014',C2:'第二学期',C3:'国家励志奖学金',C4:'4000',C5:'2014-10-10'}];
		vm.items[1] = data;
	  });
	//获得学生学籍异动信息
	http.callService(htt[2]).success(function(data){
		
//		data=[{C1:'2015-01-11',C2:'转专业',C3:'电子信息工程学院',C4:'电子信息工程',C5:'电信12-01',C6:'计算机学院',C7:'软件工程',C8:'软件工程1班'},
//		      {C1:'2016-02-11',C2:'休学',C3:'电子信息工程学院1',C4:'电子信息工程1',C5:'电信12-02',C6:'计算机学院1',C7:'软件工程1',C8:'软件工程2班'}];

		vm.items[2] = data;
	  });
	//获得学生宿舍信息
	http.callService(htt[3]).success(function(data){
//		data=[{C1:'1',C2:'李俊',C3:'22',C4:'18339919808',C5:'电信12-02',C6:'杜保强',C7:'河南省信阳市',C8:'一区',C9:'3号楼',C10:'513'},
//		      {C1:'2',C2:'程大江',C3:'22',C4:'15138910983',C5:'电信12-02',C6:'杜保强',C7:'河南省南阳市',C8:'一区',C9:'3号楼',C10:'513'},
//		      {C1:'3',C2:'杜维敏',C3:'23',C4:'18339919937',C5:'电信12-02',C6:'杜保强',C7:'云南省昆明市',C8:'一区',C9:'3号楼',C10:'513'},
//		      {C1:'4',C2:'马海龙',C3:'24',C4:'18937518355',C5:'电信12-02',C6:'杜保强',C7:'河南省平顶山',C8:'一区',C9:'3号楼',C10:'513'},
//		      {C1:'5',C2:'孔天培',C3:'23',C4:'13700869624',C5:'电信12-02',C6:'杜保强',C7:'河南省商丘市',C8:'一区',C9:'3号楼',C10:'513'},
//		      {C1:'6',C2:'张安新',C3:'23',C4:'18339934131',C5:'电信12-02',C6:'杜保强',C7:'河南省信阳市',C8:'一区',C9:'3号楼',C10:'513'}];
		vm.items[3] = data;
	  });
	//获得宿舍调整信息
	http.callService(htt[4]).success(function(data){
//		data=[{C1:'2015-01-11',C2:'一区',C3:'3号楼',C4:'513',C5:'二区',C6:'1号楼',C7:'513'},
//		      {C1:'2016-02-11',C2:'二区',C3:'2号楼',C4:'513',C5:'一区',C6:'4号楼',C7:'312'}];

		vm.items[4] = data;
	  });
};
    getServiceData();
    getVmData();
    scope.index_=0;
    scope.xjyd=function($index){
	scope.index_=$index;
}	  
}]);