/**
 * 不及格补考分析
 */
var app = angular.module('app', ['ngRoute','system'])
.controller("controller", ['$scope','service', 'advancedService','dialog', function($scope, service, advancedService,dialog){

	$scope.data = {
			mask : true,
			value_term:'',
			dateName:null,
			type:'course',
			bktype:'course',
			yeartype:null,
			thisYear   : null,
			lastYear   : null,
			start_year : null,
		param   : {
			schoolYear : null,
			termCode   : null,
			edu : null
		},
		advance : {
			param : null
		}
	};
	$scope.show=false;
	$scope.cssType=['active','','',''];
	$scope.gkcssSort=['','on','',''];//不及格top排序
	$scope.bkcssSort=['','on'];//不及格补考top排序
	$scope.gkcssStuSort=['','on','',''];//不及格top排序(学生)
	$scope.bkcssType=['active','',''];
	$scope.turncssType=['prevv','prevv disable'];
	$scope.turnnexcssType=['nextt','nextt disable'];
	$scope.yearType=['has-green','','',''];
	$scope.gkTopSort='CL02 DESC';
	$scope.gkTopStuSort='CL02 DESC';
	$scope.bkTopSort='CL02 DESC';
	$scope.gktopTitles=['课程','教师','专业','班级'];
	$scope.bktopTitles=['课程','任课教师','专业'];
	$scope.turnPage=1;
	$scope.turnStuPage=1;
	$scope.bkturnPage=1;
	$scope.gktopTitlesInfo=$scope.gktopTitles[0];
	$scope.bktopTitlesInfo=$scope.bktopTitles[0];
	$scope.turncssTop=$scope.turncssType[1];
	$scope.turncssTop1=$scope.turncssType[1];
	$scope.turncssTop2=$scope.turncssType[1];
	$scope.turnnexcssTop=$scope.turnnexcssType[0];
	$scope.turnnexcssTop1=$scope.turnnexcssType[0];
	$scope.turnnexcssTop2=$scope.turnnexcssType[0];
	var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; };
	var setAdvancedParam = function(param){ $scope.data.advance.param = param; };
	var showLoading = function(){ dialog.showLoading();};
	var hideLoading = function(){ dialog.hideLoading(); };
	var getParam = function(){
		var obj = $scope.data.param;
		obj.param = getAdvancedParam();
		return obj;
	};
	var querySelectType=function(){//得到学年,本专科生
		service.querySelectType(function(data){
			showLoading();
				var $data = $scope.data;
				$data.bzdm_edu = data.edu;//学生类型(本专科生)
				$data.bzdm_xn = data.xnxq;//学年
				$data.value_edu  = $data.bzdm_edu[0].id;//初始化学历的值
				$data.value_year  = $data.bzdm_xn[0].id.split(',')[0];//初始化学年
				$data.thisyear  = $data.bzdm_xn[0].id.split(',')[0];//初始化当前学年
				$data.lastyear  = $data.bzdm_xn[1].id.split(',')[0];//初始化上一个学年
				$data.lastterm  = $data.bzdm_xn[1].id.split(',')[1];//初始化上一个学期
				$data.this_term  = $data.bzdm_xn[0].id.split(',')[1];//初始化当前学期
				$data.value_term=$data.bzdm_xn[0].id.split(',')[1];//初始化学期
				getInfoData();
				$data.value_year  = $data.bzdm_xn[0].id;
				$data.thisYear  = $data.bzdm_xn[0].id;//初始化当前学年
				$data.lastYear  = $data.bzdm_xn[1].id;//初始化上一个学年
				$data.dateName = $data.bzdm_xn[0].mc;
			});
	};
	/**
	 * 清除选择年默认数据
	 */
	var clearYearSelectedFn = function(){
		var $data = $scope.data;
		$data.value_year = null;
		$data.fiveYear = false;
		$data.tenYear  = false;
	};
	$scope.changXn = function(id, data){
		var $data = $scope.data, bzdm_xn = $data.bzdm_xn;
		if($data.start_year==id && $data.end_year==id) return;
		clearYearSelectedFn();
		for(var i=0,len=bzdm_xn.length; i<len; i++){
			if(bzdm_xn[i].id == id){
				$data.value_year = id.split(',')[0];
				$data.value_term = id.split(',')[1];
				if(id==$data.thisYear){
					$scope.yearType=['has-green','','',''];
				}else if(id==$data.lastYear){
					$scope.yearType=['','has-green','',''];
				}else{
					$scope.yearType=['','','',''];
				}
				$data.dateName = bzdm_xn[i].mc;
				break;
			}
		}
		$data.start_year = id;
		$data.end_year   = id;
		getInfoData();
	};
	$scope.changEdu = function(id, data){//改变学历
		$scope.data.value_edu = id;
		getInfoData();
	};
	querySelectType();
	//得到数据
	var getInfoData=function(){
		showLoading();
		//(不及格人数  不及格率 环比变化 平均不及格数)
		service.getGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,function(data){
			if($scope.data.value_year=='3333'||$scope.data.value_year=='5555') data.CL03='--';
			$scope.data.gkInfo_data = data;
		});
		//学生类别 人数 不及格率 变化
		service.getGkflInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,function(data){
			if($scope.data.value_year=='3333'||$scope.data.value_year=='5555'){
				for(var i=0;i<data.length;i++){
					data[i].CL04='--';
				}
			}
			$scope.data.gkflInfo_data = data;
		});
		//各年级不及格分布(人数 人均不及格数)
		service.getNjGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,function(data){
			$scope.GknjCfg = data;
		});
		//男女生不及格分布(人数 人均不及格数)
		service.getXbGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,function(data){
			$scope.GkxbCfg = data;
		});
		//不及格课程分布--公共课/专业课(人数 人均不及格数)
		service.getNatKcGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,function(data){
			$scope.GkkcNatCfg = data;
		});
		//不及格课程分布--必修课/选修课(人数 人均不及格数)
		service.getAttrKcGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,function(data){
			$scope.GkkcAttCfg = data;
		});
		//各机构不及格分布(人数 人均不及格数)
		service.getJgGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,function(data){
			$scope.GkjgCfg = data;
		});
		//不及格top(人数 人均不及格数)
		service.getTopGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.data.type,$scope.gkTopSort,$scope.turnPage,function(data){
			$scope.GkTopList= data;
		});
		//学生不及格top(人数 人均不及格数)
		service.getStuTopGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.gkTopStuSort,$scope.turnStuPage,function(data){
			$scope.GkStuTopList= data;
		});
		//人均不及格补考top(人数 人均不及格数)
		service.getTopbkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.data.bktype,$scope.bkTopSort,$scope.bkturnPage,function(data){
			hideLoading();
			$scope.bkTopList= data;
			if(data.length==0){
				$scope.show=true;
			}else{
				$scope.show=false;
			}
		});
	};
	 var getTopData=function(){
	    //不及格top(人数 人均不及格数)
		service.getTopGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.data.type,$scope.gkTopSort,$scope.turnPage,function(data){
			
			if(data.length!=0){
				$scope.GkTopList= data;
				$scope.befdata=$scope.turnPage;
			 }else{
				 $scope.turnnexcssTop=$scope.turnnexcssType[1];
				 $scope.turnPage=$scope.befdata;
			 }
		});
	 };
	 var getTopbkData=function(){
		 //人均补考top( 人均补考数)
		 service.getTopbkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.data.bktype,$scope.bkTopSort,$scope.bkturnPage,function(data){
			 if(data.length!=0){
				 $scope.bkTopList= data;
				 $scope.bkbefdata=$scope.bkturnPage;
				 $scope.show=false;
			 }else{
				 $scope.turnnexcssTop2=$scope.turnnexcssType[1];
				 $scope.bkturnPage=$scope.bkbefdata;
				 $scope.show=true;
			 }
		 });
	 };
	 $scope.gkSort=function(sort,id){//不及格top排序
		 $scope.turnPage=1;
		 $scope.gkcssSort=['','','',''];
		 $scope.gkTopSort=sort;
		 getTopData();
		 $scope.gkcssSort[id]='on';
	 };
	 $scope.gkTrunPage=function(sort){//不及格top翻页
		 $scope.turncssTop=$scope.turncssType[0];
		 $scope.turnnexcssTop=$scope.turnnexcssType[0];
		 if(sort=='pre'){
			 if($scope.turnPage>=10){
			 $scope.turnPage=$scope.turnPage-10;
			 }
			 else {
				 $scope.turnPage=1;
				 $scope.turncssTop=$scope.turncssType[1];
				 }
			 getTopData();
		 }else if(sort=='nex'){
		 $scope.turnPage=$scope.turnPage+10; 
		 getTopData();
		 }
	 };
	 $scope.bkTrunPage=function(sort){//不及格补考top翻页
		 $scope.turncssTop2=$scope.turncssType[0];
		 $scope.turnnexcssTop2=$scope.turnnexcssType[0];
		 if(sort=='pre'){
			 if($scope.bkturnPage>=10){
				 $scope.bkturnPage=$scope.bkturnPage-10;
				 }
			 else  {
				 $scope.bkturnPage=1;
				 $scope.turncssTop2=$scope.turncssType[1];
				 }
		 }else if(sort=='nex'){
			 $scope.bkturnPage=$scope.bkturnPage+10; 
		 }
		 getTopbkData();
	 };
	 $scope.gkStuTrunPage=function(sort){//不及格top翻页(学生)
		 $scope.turncssTop1=$scope.turncssType[0];
		 $scope.turnnexcssTop1=$scope.turnnexcssType[0];
		 if(sort=='pre'){
			 if($scope.turnStuPage>=10){
				 $scope.turnStuPage=$scope.turnStuPage-10;
				 }
			 else {
				 $scope.turnStuPage=1;
				 $scope.turncssTop1=$scope.turncssType[1];
				 }
		 }else if(sort=='nex'){
			 $scope.turnStuPage=$scope.turnStuPage+10; 
		 }
			//学生不及格top(人数 人均不及格数)
			service.getStuTopGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.gkTopStuSort,$scope.turnStuPage,function(data){
				if(data.length!=0){
					$scope.GkStuTopList= data;
					$scope.stubefdata=$scope.turnStuPage;
				}else{
					$scope.turnnexcssTop1=$scope.turnnexcssType[1];
					$scope.turnStuPage=$scope.stubefdata;
				}
			});
	 };
	 $scope.gkStuSort=function(sort,id){//不及格top排序(学生)
		 $scope.gkcssStuSort=['','','',''];
		 $scope.gkTopStuSort=sort;
		 $scope.turnStuPage=1;
			//学生不及格top(人数 人均不及格数)
			service.getStuTopGkInfo(getAdvancedParam(),$scope.data.value_year,$scope.data.value_term,$scope.data.value_edu,$scope.gkTopStuSort,$scope.turnStuPage,function(data){
				$scope.GkStuTopList= data;
			});
		 $scope.gkcssStuSort[id]='on';
	 };
	 $scope.bkSort=function(sort,id){//不及格补考top排序
		 $scope.bkcssSort=['',''];
		 $scope.bkTopSort=sort;
		 $scope.bkturnPage=1;
		 getTopbkData();
		 $scope.bkcssSort[id]='on';
	 };
	 $scope.changeType=function(type){//改变不及格Top类型
		 if('course'==type){
			 if($scope.data.type=='course') return;
			 $scope.cssType=['','','',''];
			 $scope.turnnexcssTop=$scope.turnnexcssType[0];
			 $scope.gktopTitlesInfo='';
			 $scope.turnPage=1;
			 $scope.data.type='course';
			 $scope.cssType[0]='active';
			 $scope.gktopTitlesInfo=$scope.gktopTitles[0];
			 getTopData();
		 }else if('tea'==type){
			 if($scope.data.type=='tea') return;
			 $scope.cssType=['','','',''];
			 $scope.turnnexcssTop=$scope.turnnexcssType[0];
			 $scope.gktopTitlesInfo='';
			 $scope.turnPage=1;
			 $scope.data.type='tea';
			 $scope.cssType[1]='active';
			 $scope.gktopTitlesInfo=$scope.gktopTitles[1];
			 getTopData();
		 }else if('major'==type){
			 if($scope.data.type=='major') return;
			 $scope.cssType=['','','',''];
			 $scope.turnnexcssTop=$scope.turnnexcssType[0];
			 $scope.gktopTitlesInfo='';
			 $scope.turnPage=1;
			 $scope.data.type='major';
			 $scope.cssType[2]='active';
			 $scope.gktopTitlesInfo=$scope.gktopTitles[2];
			 getTopData();
		 }else if('class'==type){
			 if($scope.data.type=='class') return;
			 $scope.cssType=['','','',''];
			 $scope.turnnexcssTop=$scope.turnnexcssType[0];
			 $scope.gktopTitlesInfo='';
			 $scope.turnPage=1;
			 $scope.data.type='class';
			 $scope.cssType[3]='active';
			 $scope.gktopTitlesInfo=$scope.gktopTitles[3];
			 getTopData();
		 }
	};
	//补考top
	$scope.changebkType=function(type){
		if('course'==type){
			if($scope.data.bktype=='course') return;
			$scope.bkcssType=['','',''];
			$scope.bktopTitlesInfo='';
			$scope.turnnexcssTop2=$scope.turnnexcssType[0];
			$scope.bkturnPage=1;
			$scope.data.bktype='course';
			$scope.bkcssType[0]='active';
			$scope.bktopTitlesInfo=$scope.bktopTitles[0];
			getTopbkData();
		}else if('tea'==type){
			if($scope.data.bktype=='tea') return;
			$scope.bkcssType=['','',''];
			$scope.bktopTitlesInfo='';
			$scope.turnnexcssTop2=$scope.turnnexcssType[0];
			$scope.bkturnPage=1;
			$scope.data.bktype='tea';
			$scope.bkcssType[1]='active';
			$scope.bktopTitlesInfo=$scope.bktopTitles[1];
			getTopbkData();
		}else if('major'==type){
			if($scope.data.bktype=='major') return;
			$scope.bkcssType=['','',''];
			$scope.bktopTitlesInfo='';
			$scope.turnnexcssTop2=$scope.turnnexcssType[0];
			$scope.bkturnPage=1;
			$scope.data.bktype='major';
			$scope.bkcssType[2]='active';
			$scope.bktopTitlesInfo=$scope.bktopTitles[2];
			getTopbkData();
		}
	};
	 //改变年
	 $scope.changeYear=function(yearType){
		 var $data = $scope.data;
		 $scope.yearType=['','','',''];
		 if('0'==yearType){
			 $scope.data.value_year=$scope.data.thisyear;
			 $scope.data.value_term=$scope.data.this_term;
			 $scope.yearType[0]='has-green';
			 getInfoData();
			 $scope.data.dateName  = $data.bzdm_xn[0].mc;
		 }else if('1'==yearType){
			 $scope.data.value_year=$scope.data.lastyear;
			 $scope.data.value_term=$scope.data.lastterm;
			 $scope.yearType[1]='has-green';
			 getInfoData();
			 $scope.data.dateName  = $data.bzdm_xn[1].mc;
		 }else if('2'==yearType){
			 $scope.data.value_year='3333';
			 $scope.yearType[2]='has-green';
			 getInfoData();
			 $scope.data.dateName  = '近三年';
//			 $scope.data.gkInfo_data.CL03='--';
//			 for(var i=0;i<$scope.data.gkflInfo_data.length;i++){
//				 $scope.data.gkflInfo_data[i].CL04='--';
//			 }
		 }else if('3'==yearType){
			 $scope.data.value_year='5555';
			 $scope.yearType[3]='has-green';
			 getInfoData();
			 $scope.data.dateName  = '近五年';
//			 $scope.data.gkInfo_data.CL03='--';
//			 for(var i=0;i<$scope.data.gkflInfo_data.length;i++){
//				 $scope.data.gkflInfo_data[i].CL04='--';
//			 }
		 }
	 };

		service.getAdvance(function(data){
			advancedService.checkedFirst(data);
			$scope.data.advance.source = data;
		});
		/**
		 * 高级查询-切换show
		 */
		$scope.advanceShow = function(){
			$scope.data.advance.show = !$scope.data.advance.show;
		};
		/**
		 * 高级查询-change事件
		 */
		$scope.advanceChange = function(data){
			var param = advancedService.getParam(data);
			// 高级查询-参数
			setAdvancedParam(param);
			getInfoData();
		};
	
}]);