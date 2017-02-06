var app = angular.module('app', ['ngRoute', 'system']).controller("controller",
		['$scope', 'service','advancedService','$timeout','dialog','toastrService', function($scope,service,advancedService,$timeout,dialog,toastr) {
			$scope.data = {
				    mask:false,
				    tbshow :false,
				    tb2show :false,
				    printShow:false,
				    print2Show:false,
				    kslxS1:false,
				    kslxS2:false,
				    kcxxS1:false,
				    kcxxS2:false,
				    ylshow:false,
				    timeshow:false,
				    reportShow:false,
				    report2Show:false,
				    report3Show:false,
				    ketj:true,
				    fourShow:true,
				    qkCount:"0",
				    hkCount:"0",
				    wjCount:"0",
				    tmCount:"8",
				    oneS:"命题是否能全面考察学生对知识点的记忆、理解、比较、分析、综合、评价等能力，或有所侧重； 命题是否覆盖了所有的章节，考核本课程重点、难点内容；命题是否符合教学大纲的要求，命题难度如何；命题是否丰富、题量和分值分布是否合理；命题有无错误，错误性质和程度如何；",
					twoS:"考生答题总体说明：总体情况较好，错误集中选择题第 题和第 题......，简答题......； 典型出错点分析：选择提第几题正确答案是 ，答题错误绝大数选 ；知识掌握情况说明：根据试卷难度分析情况和成绩分布，分析学生对应知应会内容掌握程度；针对学生失分和得分较多的题目，分析其失分和得分的原因；",
                    threeS:"总结教师教与学生学、考试命题和阅卷评分过程中存在的问题和有待改进之处，并提出针对性的整改措施；",				   
					advance : {
						param : null
					},
//					detailUrl : "pmsScorePredictTea/getStuList",
//					exportUrl : 'pmsScorePredictTea/down',
					stu_detail : { 
						values : {},
					},
					param:{},
					distribute : {},
					season:false,
					grid : {
						header : [],
						mask   : false,
						show   : function(){ this.mask=true; },
						hide   : function(){ this.mask = false; }
					},
				};
				var getJson = function(obj){
					return JSON.stringify(obj);
				}
				var getParam = function(){
					var obj = Ice.apply({},$scope.data.param);
					obj.param = getAdvancedParam();
					return obj;
				}
				var showLoading = function(){dialog.showLoading();};
				var hideLoading = function(){dialog.hideLoading();};
				var getAdvancedParam = function(){ return $scope.data.advance.show ? $scope.data.advance.param : null; }
				var setAdvancedParam = function(param){ $scope.data.advance.param = param; }
				$scope.ary = ["一","二","三","四","五","六","七","八","九","十"];
//				/** 参数 */
//				var stuDetailParam = function(){
//					var detail = $scope.data.stu_detail,
//						param  = getParam(),
//						fields = getJson(detail.fields),
//						headers= getJson(detail.headers);
//					detail.values['course'] = $scope.data.course;
//					detail.values['schoolyear'] = $scope.data.schoolYear;
//					detail.values['termcode'] = $scope.data.termCode;
//					detail.values['edu'] = $scope.data.edu;
//					Ice.apply(param, {values:getJson(detail.values), fields:fields, headers:headers});
//					return param;
//				}
				showLoading();
				service.getEduList().then(function(data) {
									$scope.data.eduList = data;
									if(data.length>0){
										$scope.data.edu = data[0].id;
									}
				});
				service.getTimeList().then(function(data) {
					var list = data;
					$scope.data.allTimeList = list;
					$scope.data.subTimeList = [];
					for (var i=0;i<list.length;i++){
					  if (i<6){
						  $scope.data.subTimeList.push(list[i]);
					  }
					}
					if($scope.data.timeshow){
						$scope.data.timeList = $scope.data.allTimeList;
					}else{
						$scope.data.timeList = $scope.data.subTimeList;
					}
					if(data.length>0){
						$scope.data.time = data[0].id;
						var ary = data[0].id.split(",");
						$scope.data.schoolYear =ary[0];
	    				$scope.data.termCode = ary[1];
					}
					init();
                });
				service.getThList().then(function(data) {
					$scope.data.thList = data;
                });
				service.getFthList().then(function(data) {
					$scope.data.fthList = data;
                });
				var getCourseList = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var edu = $scope.data.edu;
					service.getCourseList(getAdvancedParam(),schoolYear,termCode,edu,function(data){
										$scope.data.courseList = data;
										if(data.length >0){
										$scope.data.course = data[0].id;
										$scope.data.courseName = data[0].mc;
										}
					});
				};
				var getTeachClassAndStuCount = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var edu = $scope.data.edu;
					service.getTeachClassAndStuCount(schoolYear,termCode,courseId,edu,getAdvancedParam(),function(data){
										$scope.data.classCount = data.bj;
										$scope.data.stuCount = data.stu;
					});
				};
				var getCourseNatureList = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var edu = $scope.data.edu;
					service.getCourseNatureList(schoolYear,termCode,courseId,edu,getAdvancedParam(),function(data){
										$scope.data.natureList = data;
										if(data.length >0){
											$scope.data.nature = data[0].id;
											$scope.data.natureName = data[0].mc;
										}
					});
				};
				var getClassScoreGk = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					service.getClassScoreGk(schoolYear,termCode,courseId,nature,edu,getAdvancedParam(),function(data){
										$scope.data.tabList = data.list;
										$scope.data.isgd = data.pms;
					});
				};
				var getDyData = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					var classid = $scope.data.classid;
					service.getDyData(schoolYear,termCode,courseId,classid,nature,edu,getAdvancedParam(),function(data){
                                         $scope.data.dydata =data;
					});
				};
				var saveYzZt = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var classid = $scope.data.classid;
					service.saveYzZt(schoolYear,termCode,courseId,nature,classid,function(data){
						$timeout(function(){
							$scope.print();
						});
					});
				};
				var saveDt = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					var classid = $scope.data.classid;
					var kslx = (!$scope.data.kslxS1 && !$scope.data.kslxS2) ?"null":($scope.data.kslxS1?"1":"2");
					var kcxx = (!$scope.data.kcxxS1 && !$scope.data.kcxxS2) ?"null":($scope.data.kcxxS1?"1":"2");
					var one = $scope.data.oneS;
					var two = $scope.data.twoS;
					var three = $scope.data.threeS;
					var four = $scope.data.fourS;
					var five = $scope.data.fiveS;
					var fxr = $scope.data.fxr;
					var zr = $scope.data.zr;
					var sj = ""+"-"+""+"-"+"";
					var qt = $scope.data.qt;
					service.saveDt(schoolYear,termCode,courseId,nature,classid,kslx, kcxx, one, two, three, four, five,fxr,zr,sj,qt,function(data){
					});
				};
				var saveXt =function(){
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var classid = $scope.data.classid;
					var tx = getJson($scope.data.tx);
					var tf = getJson($scope.data.tf);
					var df = getJson($scope.data.df);
					var th = getJson($scope.data.stmh);
					var thn =getJson($scope.data.tmnList);
					service.saveXt(schoolYear,termCode,courseId,nature,classid,tx,tf,df,th,thn,function(data){
					});
				}
				var getYzZt = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					var classid = $scope.data.classid;
					service.getYzZt(schoolYear,termCode,courseId,nature,classid,function(data){
					     $scope.data.yzzt = data;
					     if(data.yzzt){
					    	 $scope.data.qkCount=$scope.data.reportMap.qk.toString();
					    	 $scope.data.hkCount=$scope.data.reportMap.hk.toString();
					    	 $scope.data.wjCount=$scope.data.reportMap.wj.toString();
					     }
					});
				};
				var getDtXt = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					var classid = $scope.data.classid;
					service.getDtXt(schoolYear,termCode,courseId,nature,classid,function(data){
					     $scope.data.dtxt = data;
						 var oneS="命题是否能全面考察学生对知识点的记忆、理解、比较、分析、综合、评价等能力，或有所侧重； 命题是否覆盖了所有的章节，考核本课程重点、难点内容；命题是否符合教学大纲的要求，命题难度如何；命题是否丰富、题量和分值分布是否合理；命题有无错误，错误性质和程度如何；",
							twoS="考生答题总体说明：总体情况较好，错误集中选择题第 题和第 题......，简答题......； 典型出错点分析：选择提第几题正确答案是 ，答题错误绝大数选 ；知识掌握情况说明：根据试卷难度分析情况和成绩分布，分析学生对应知应会内容掌握程度；针对学生失分和得分较多的题目，分析其失分和得分的原因；",
		                    threeS="总结教师教与学生学、考试命题和阅卷评分过程中存在的问题和有待改进之处，并提出针对性的整改措施；";
					     if(data != null){
					    	    $scope.data.oneS =(data.one_ == "null"|| data.one_==null||data.one_ =="") ? oneS : data.one_ ;
								$scope.data.twoS=(data.two_ == "null"|| data.two_==null||data.two_ =="") ?  twoS : data.two_ ;
							    $scope.data.threeS =(data.three_ == "null"|| data.three_==null||data.three_ =="") ? threeS : data.three_ ;
								$scope.data.fourS = data.four_one== "null" ? "" : data.four_one ;
								$scope.data.fiveS = data.four_three== "null" ? "" : data.four_three ;
								$scope.data.zr = data.zr;
								$scope.data.fxr = data.fxr;
								if(data.kslx == '1'){
									$scope.data.kslxS1 = true;
								}else if(data.kslx == '2'){
									$scope.data.kslxS2 = true;
								}
								if(data.kcxx == '1'){
									$scope.data.kcxxS1 = true;
								}else if(data.kcxx == '2'){
									$scope.data.kcxxS2 = true;
								}
								var tx =data.tx==null?[]:data.tx;
								var tf = data.tf==null?[]:data.tf;
								var df = data.df==null?[]:data.df;
								var list = $scope.data.tmList;
								for(var ii=0;ii<(tx.length/8);ii++){
									
									for (var jj =ii*8;jj<(ii+1)*8;jj++){
										if(tx.length >jj && tx[jj].value != null){
											 $scope.data.tmList[ii].tx.tx[jj].mc = tx[jj].value;
										}
									}
									for (var cc =ii*8;cc<(ii+1)*8;cc++){
										if(tf.length >cc && tf[cc].value != null){
											 $scope.data.tmList[ii].tf.tf[cc].mc = tf[cc].value;
										}
									}
									for (var dd =ii*8;dd<(ii+1)*8;dd++){
										if(df.length >dd && df[dd].value != null){
											 $scope.data.tmList[ii].df.df[dd].mc = df[dd].value;
										}
									}
								}
 								$scope.data.qt = data.qt== "null" ? "":data.qt;
					     }
					     $scope.data.report3Show = true;
					});
				};
				var getReport = function(){ 
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					service.getReport(schoolYear,termCode,courseId,nature,edu,getAdvancedParam(),function(data){
										$scope.data.reportList = data;
										$scope.data.reportShow = true;
					});
				};
				var getChartData = function(){ 
					showLoading();
					var schoolYear = $scope.data.schoolYear;
					var termCode = $scope.data.termCode;
					var courseId = $scope.data.course;
					var edu = $scope.data.edu;
					service.getChartData(schoolYear,termCode,courseId,edu,getAdvancedParam(),function(data){
						var distribute = $scope.data.distribute;
						    distribute.barCfg = data.barCfg;
						    hideLoading();
					});
				};
				var getSameData = function(id){ 
					var schoolYear = $scope.data.schoolYear;
					var courseId = $scope.data.course;
					var nature = $scope.data.nature;
					var edu = $scope.data.edu;
					service.getSameData(schoolYear,courseId,nature,edu,getAdvancedParam(),function(data){
						id == 0 ? $scope.data.tbshow = true:$scope.data.tb2show = true;				
						$scope.data.sameList = data.list;
					});
				};
				var initnature = function(){
					getClassScoreGk();
//					getSameData();
//					getPrecisionList();
//					getNowPrecision();
				};
				var initCourse = function(){
					getTeachClassAndStuCount();
					getCourseNatureList();
					getChartData();
					initnature();
				};
				var init = function(){
					getCourseList();
					initCourse();
				};
				$scope.eduSelect = function(edu){
					$scope.data.edu = edu;
					init();
				};
				$scope.timeSelect = function(id){
					$scope.data.time = id;
					var ary = id.split(",");
					$scope.data.schoolYear =ary[0];
    				$scope.data.termCode = ary[1];
    				$scope.data.course = null;
    				$scope.data.nature = null;
    				init();
				};
				$scope.courseSelect = function(id,mc){
					$scope.data.course = id;
					$scope.data.courseName = mc;
					$scope.data.nature = null;
					initCourse();
				};
				$scope.natureSelect = function(id,mc){
					$scope.data.nature = id;
					$scope.data.natureName = mc;
					initnature();
				};
				$scope.dyClick= function(id,mc,type){
					console.log($scope.data.kslx);
					$scope.data.reportShow = false;
					$scope.data.report2Show = true;
					$scope.data.classid = id;
					$scope.data.classmc = mc;
					if(type){
						$scope.data.printShow = true;
					}else{
						$scope.data.print2Show = true;
					}
					var list = $scope.data.tabList;
					if(list != null && list.length >0){
						for(var i = 0;i<list.length;i++){
							if(id == list[i].id){
								$scope.data.dycj = list[i];
							}
						}
					}
					var listx = $scope.data.dycj;
					var list1 = listx != null
					          ? listx.list:null;
					var countList=[],scaleList=[];
					for(var j = 0;j<list1.length;j++){
						if(j%2 == 0){
							countList.push(list1[j]);
						}else{
							scaleList.push(list1[j]);
						}
					}
					$scope.data.dyCount = countList;
					$scope.data.dyScale = scaleList;
					var list2 =$scope.data.reportList;
					if(list2 != null && list2.length >0){
						for(var k =0;k<list2.length;k++){
							if(id == list2[k].id){
								$scope.data.reportMap = list2[k];
							}
						}
					}
					getDyData();
					getYzZt();
				};
				$scope.yzClick= function(){
					$scope.data.yzjg= 1;
					var list = $scope.castNum($scope.data.tmCount);
					$scope.data.stmh = list;
					$scope.data.tmList = [];
					for(var i=0;i<(list.length/8);i++){
						var all = {th:{th:[]},tx:{tx:[]},tf:{tf:[]},df:{df:[]}};
						for(var j =i*8;j<(i+1)*8;j++){
							var txm = {id:j,mc:""},
							    tfm = {id:j,mc:""},
							    dfm = {id:j,mc:""};
							all.th.th.push(list[j]);
							all.tx.tx.push(txm);
							all.tf.tf.push(tfm);
							all.df.df.push(dfm);
						}
						$scope.data.tmList.push(all);
					}
					if($scope.data.qkCount == $scope.data.reportMap.qk
							&& $scope.data.hkCount ==$scope.data.reportMap.hk
							&& $scope.data.wjCount == $scope.data.reportMap.wj && $scope.data.wjCount != "" 
								&& $scope.data.hkCount != "" && $scope.data.qkCount !="" && $scope.data.tmCount != "" && 
								$scope.data.tmCount != "0" && $scope.data.tmCount != 0){
						$scope.data.report2Show = false;
						getDtXt();
					}else{
						toastr.warning("请输入正确的缺考、缓考、违纪人数！");
					}
				};
				$scope.print = function(){
					var LODOP = getLODOP();
					if(LODOP != null){
						LODOP.PRINT_INIT("课程分析报告");
						LODOP.SET_PRINT_PAGESIZE(1,0,0,"A4");
						LODOP.ADD_PRINT_HTM(0,0,'210mm', '297mm',document.getElementById("dy").innerHTML);
						LODOP.ADD_PRINT_IMAGE(44, 40, 202, 36, "URL:"+base+"/static/resource/images/school_haut_mc.jpg");
						LODOP.ADD_PRINT_IMAGE(42, 665, 42, 42, "URL:"+base+"/static/resource/images/school_haut_xh.jpg");
						if($scope.data.printShow){
							$scope.data.printShow = false;
							return LODOP.PRINT();
						}else if($scope.data.print2Show){
							$scope.data.print2Show = false;
							return LODOP.PREVIEW();	
						}
					}
					return null;
				}
				$scope.tbClick = function(){
					getSameData(0);
				};
				$scope.tb2Click = function(){
					getSameData(1);
				};
				$scope.reportClick = function(){
					getReport();
				};
				$scope.tjClick = function(){
					if(!$scope.data.kslxS1 && !$scope.data.kslxS2 && ($scope.data.kcxxS1 ||$scope.data.kcxxS2) && ($scope.data.qt == "" ||$scope.data.qt ==null)){
						toastr.warning("请选择考试方式！");
					}else if(($scope.data.kslxS1 || $scope.data.kslxS2 || ($scope.data.qt != "" && $scope.data.qt !=null)) && !$scope.data.kcxxS1 && !$scope.data.kcxxS2){
	                    toastr.warning("请选择课程性质！");
					}else if(!$scope.data.kslxS1 && !$scope.data.kslxS2 && !$scope.data.kcxxS1 && !$scope.data.kcxxS2 && ($scope.data.qt == "" || $scope.data.qt ==null)){
						 toastr.warning("请选择考试方式和课程性质！");
					}else{
						$scope.data.report3Show = false;
						$scope.data.tx = [];
						$scope.data.tf = [];
						$scope.data.df = [];
						var tx =document.getElementsByName("tx"),
						    tf =document.getElementsByName("tf"),
						    df =document.getElementsByName("df");
						var list = $scope.data.tmList;
						var count =0;
						for(var ii=0;ii<(tx.length/8);ii++){
							
							for (var jj =ii*8;jj<(ii+1)*8;jj++){
								if(tx[jj].value != ""){
									count++;
									$scope.data.tmList[ii].tx.tx[jj-(ii*8)].mc = tx[jj].value;
								}
							}
							for (var cc =ii*8;cc<(ii+1)*8;cc++){
								if(tf[cc].value != ""){
									count++;
									$scope.data.tmList[ii].tf.tf[cc-(ii*8)].mc = tf[cc].value;
								}
							}
							for (var dd =ii*8;dd<(ii+1)*8;dd++){
								if(df[dd].value != ""){
									count++;
									$scope.data.tmList[ii].df.df[dd-(ii*8)].mc = df[dd].value;
								}
							}
						}
						if(count == 0 ){
							$scope.data.fourShow = false;
						}
						for (var i = 0, j = tx.length; i < j; i++){
							var map = {id:i,mc:""};
							if(tx[i].value == null){
								map.mc = "";
							   $scope.data.tx.push(map);	
							}else{
								map.mc = tx[i].value;
								$scope.data.tx.push(map);	
							}
						}
						for (var k = 0, l = tf.length; k < l; k++){
							var map1 = {id:k,mc:""};
							if(tf[k] == null){
								map1.mc = "";
								 $scope.data.tf.push(map1);
							}else{
								map1.mc = tf[k].value;
							    $scope.data.tf.push(map1);
							}
						}
						for (var x = 0, y = df.length; x < y; x++){
							var map2 = {id:x,mc:""};
							if(df[x].value == null){
								map2.mc = "";
								$scope.data.df.push(map2);
							}else{
								map2.mc = df[x].value;
								$scope.data.df.push(map2);
							}
						}
						saveYzZt();
						saveDt();
						saveXt();
					}
				};
				$scope.$watch("data.qt",function(newVal,oldVal){
					if(newVal != oldVal){
						if(newVal != "" && newVal != null ){
						   $scope.data.kslxS1=false;
						   $scope.data.kslxS2=false;
						}
					}
				},true);
				$scope.advanceChange = function(data){
					var param = advancedService.getParam(data);
					// 高级查询-参数
					setAdvancedParam(param);
					init();
				};
//				/** 概况信息 点击事件 */
//				$scope.stuListClick = function(type,type1,title, seniorCode ,seniorCode1){
//					var detail = $scope.data.stu_detail;
//					detail.title = title+"学生名单";
//					detail.values= {};
//					detail.values[type] = seniorCode||null;
//					detail.values[type1] = seniorCode1||null;
//					detail.headers= ['学号','入学年级','姓名','性别','地区','院系','专业','班级','预测成绩'];
//					detail.fields = ['no','enroll_grade','name','sexmc','ssx','deptmc','majormc','classmc','score'];
//					detail.formConfig = {
//							title : detail.title,
//							show  : true,
//							url       : $scope.data.detailUrl,
//							exportUrl : $scope.data.exportUrl, // 为空则不显示导出按钮
//							heads  : detail.headers,
//							fields : detail.fields,
//							params : stuDetailParam()
//						};
//					$timeout(function(){})
//				};
				$scope.loadMore =function(){
					$scope.data.timeshow = !$scope.data.timeshow;
					if($scope.data.timeshow){
						$scope.data.timeList = $scope.data.allTimeList;
					}else{
						$scope.data.timeList = $scope.data.subTimeList;
					}
				}
				service.getAdvance(function(data){
					$scope.data.advance.source = data;
				});
				$scope.castNum= function(n){
					var num = parseInt(n);
					if(num % 8 >0){
						num = num + (8-num%8)+1;
					}else{
						num = num+1;
					}
					var ary = $scope.ary;
					var list = [];
					var list2 = [];
					if(num < 11){
						for(var i=1;i<num;i++){
							list.push(ary[i-1]);
							list2.push(i);
						}
					}else if(num<20){
						for(var i=1;i<num;i++){
							list2.push(i);
							if(i<11){
							  list.push(ary[i-1]);
							}else{
							  list.push(ary[ary.length-1]+ary[i-11]);
							}
						}
					}else if(num<100){
						for(var i=0;i<num;i++){
							list2.push(i);
							if(i<11){
							  list.push(ary[i]);
							}else if(i<20){
							  list.push(ary[ary.length-1]+ary[i-11]);
							}else if(i<100){
								var k = i.toString().substr(0,1);
								var j = parseInt(k);
								if(i%10==0){
							       list.push(ary[j-1]+ary[ary.length-1]);
								}else{
								   list.push(ary[j-1]+ary[ary.length-1]+ary[i-((j-1)*10+11)]);
								}
							}
						}
					}
					$scope.data.tmnList = list2;
					return list;
				};
				$scope.kslxClick = function(){
					$scope.data.kslxS1= !$scope.data.kslxS1;
					$scope.data.kslxS2= false;
					$scope.data.qt ="";
				};
				$scope.kcxxClick = function(id){
					$scope.data.kcxxS1=!$scope.data.kcxxS1;
					$scope.data.kcxxS2= false;
				};
				$scope.kslxClick1 = function(){
					$scope.data.kslxS2= !$scope.data.kslxS2;
					$scope.data.kslxS1= false;
					$scope.data.qt ="";
				};
				$scope.kcxxClick1 = function(id){
					$scope.data.kcxxS2=!$scope.data.kcxxS2;
					$scope.data.kcxxS1= false;
				};
				/**
				 * 高级查询-切换show
				 */
				$scope.advanceShow = function(){
					$scope.data.advance.show = !$scope.data.advance.show;
				};
}]);