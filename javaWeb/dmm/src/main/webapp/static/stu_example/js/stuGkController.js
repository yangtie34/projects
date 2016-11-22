var app=angular.module("app",["custom"]);
app.controller("stuGkController", [ "$filter","$scope","dialog",'mask','$timeout','http',function($filter,$scope,dialog,mask,timeout,http) {
  $scope.school = {};
  $scope.stu = {};
  $scope.mzcount = {};
  $scope.zzmm = {};
      /**
     * 查询组织机构组件数据
     */
    var call_zzjg = {
    	service : 'studentService1?querySchoolName'
    }
    var call_zzjg_fn = function(id){
    	call_zzjg.params = [id==null?'':id];
	   	http.callService(call_zzjg).success(function(reData){
	   		// reData 是数组
			console.log(reData);
			$scope.yxs = reData;
	    });
	    call_data1(id);
	    call_data2(id);
	    call_data3(id);
	    call_data4(id);
	    call_data5(id);
	    call_data6(id);
	    call_data7(id);
	    call_data8(id);
	    call_data9(id);
	    call_data10(id);
    }
        var data1= {
      	service :'studentService1?queryYxdName'
      }
        var call_data1= function(id){
          data1.params = [id==null?"":id];
        	http.callService(data1).success(function(reData1){
	   		// reData 是数组
			console.log(reData1);
			$scope.school = reData1;
			
	    });
        }
         var data2= {
      	service :'studentService1?queryRs'
      }
        var call_data2= function(id){
          data2.params = [id==null?"":id];
        	http.callService(data2).success(function(reData2){
	   		// reData 是数组
			console.log(reData2);
			$scope.stu.name = reData2[0].NAME;
			$scope.stu.rs = reData2[1].RS;
			$scope.stu.count = reData2[2].COUNT;
			$scope.stu.nanrs = reData2[3].NANRS;
			$scope.stu.nvrs = reData2[4].NVRS;
	    });
        }
      var data3= {
      	service :'studentService1?queryXl'
      }
        var call_data3= function(id){
          data3.params = [id==null?"":id];
        	http.callService(data3).success(function(reData3){
	   		// reData 是数组
			console.log(reData3);
			$scope.xl = reData3;
	    });
        }
         var data4= {service :'studentService1?queryMzCount'};
            var call_data4= function(id){
          data4.params = [id==null?"":id];
        	http.callService(data4).success(function(reData4){
	   		// reData 是数组
			console.log(reData4);
			$scope.mzcount.hz = reData4[0].VALUE;
			$scope.mzcount.ssmz = reData4[1].VALUE;
			$scope.mzcount.wwh = reData4[2].VALUE;
	    });
        }
             var data10= {service :'studentService1?queryZzmm1'};
            var call_data10= function(id){
          data10.params = [id==null?"":id];
        	http.callService(data10).success(function(reData10){
	   		// reData 是数组
			console.log(reData10);
			$scope.zzmm.ty = reData10[0].RS;
			$scope.zzmm.bsty = reData10[1].RS;
			$scope.zzmm.wwh = reData10[2].RS;
	    });
        }
        var data5= {
      	service :'studentService1?querySyd'
      }
        var call_data5= function(id){
          data5.params = [id==null?"":id];
        	http.callService(data5).success(function(reData5){
	   		// reData 是数组
			$scope.syd = reData5;
			console.log($scope.syd);
			$scope.mapChart =  {
  	            data :$scope.syd,
  	             type :'map'
    };
	    });
        }
                var data6= {
      	service :'studentService1?queryNl'
      }
        var call_data6= function(id){
          data6.params = [id==null?"":id];
        	http.callService(data6).success(function(reData6){
        	$scope.nlfb = reData6;
	   $scope.areaChart =  {
	  	      type :'area',
	  	      data:$scope.nlfb,
	  	      isSort:true,
	  	      legendSort:true,
              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
               type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
              },
              legend:{
	  	      	show : true
	  	      },
        toolbox: {
        show : true,
//        orient : 'vertical',
//        x : 'right',
        feature : {
            mark : {show: false},
            dataView : {show: false, readOnly: false},
            magicType : {show: true, type: ['stack']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
     series : {
            type:'line',
            stack:''
     },
  	  xAxis : [
        {
            name:'岁数',
            boundaryGap : false
        }
    ], 
        dataZoom : {
        show : false,
        realtime : false,
        start : 0,
        end : 100
     },
      yAxis : [
        {
            name:'人数',
            type:'value'
        }
    ]        
    };
	    });
        };
      var data7= {
      	service :'studentService1?queryRydb'
      }
        var call_data7= function(id){
          data7.params = [id==null?"":id];
        	http.callService(data7).success(function(reData7){
	   		// reData 是数组
			$scope.rydb = reData7;
			
	  $scope.columnChart =  {
	  	      big:'10',
	  	      type :'column',
	  	      data:$scope.rydb,
	  	      legendSort:false,
	  	      isSort:false,
              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
               type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              },
        toolbox: {
//        orient : 'vertical',
//        x : 'right',
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
            name:'人数',
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
        };
        var data8= {
      	service :'studentService1?queryZzmm'
      }
       var call_data8= function(id){
          data8.params = [id==null?"":id];
        http.callService(data8).success(function(reData8){
	   		// reData 是数组
			$scope.zzmm = reData8;
//			var maxrs = parseInt(reData8[reData8.length-1].RS)+100;
//			if (eval(maxrs%100)==0){
//				var maxrs1 = maxrs;
//			}else{
//				var maxrs2 = maxrs.toString();
//				var maxrs3 = '';
//				if (parseInt(maxrs2[maxrs2.length-2])>4){
//					for (var i= 0;i<maxrs2.length;i++){
//						if(i<2){
//							maxrs3.push(0);
//						}else if (i=2){
//						maxrs3.push(0);	
//					}
//				}else {
//			
//				}
//				var maxrs3 = parseInt(maxrs2);
//			}
	  $scope.columnfChart =  {
	  	      type :'columnf',
	  	      data:$scope.zzmm,
	  	      isSort:true,
              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
               type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              },
        toolbox: {
//        orient : 'vertical',
//        x : 'right',
        show : true,
        feature : {
            mark : {show: false},
            dataView : {show: false, readOnly: false},
            magicType : {show: false, type: ['line', 'bar', 'stack', 'tiled']},
            restore : {show: false},
            saveAsImage : {show: true}
        }
    },
     series : {
            type:'bar',
            stack:'总人数',
            itemStyle : { normal: {label : {show: false, position: 'insideRight'}}}
     },
  	  xAxis : [
         {
            name:'人数',
            type:'value',
            max:'defualt',
            min:0
        }
    ], 
      grid :{
    	x  :120,
    	x2 :80
    },
      yAxis : [
         {
            name:'政治面貌',
            boundaryGap : true
        }
    ]  
    };
	    });
        };
    var data9= {
      	service :'studentService1?queryMz'
      }
       var call_data9= function(id){
          data9.params = [id==null?"":id];
        http.callService(data9).success(function(reData9){
	   		// reData 是数组
			$scope.mz = reData9;
	  $scope.column1Chart =  {
	  	      type :'column',
	  	      data:$scope.mz,
	  	      legendSort:false,
	  	      legend:{
	  	      	show : false
	  	      },
	  	      big:'4',
	  	      isSort:false,
              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
               type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              },
        toolbox: {
//        orient : 'vertical',
//        x : 'right',
        show : true,
        feature : {
            mark : {show: false},
            dataView : {show: false, readOnly: false},
            magicType : {show: false, type: ['line', 'bar', 'stack', 'tiled']},
            restore : {show: false},
            saveAsImage : {show: true}
        }
    },
     series : {
            type:'bar'
     },
  	  xAxis : [
         {
           boundaryGap : true
        }
    ], 
      yAxis : [
         {
              name:'人数',
              type:'value'
        }
    ],  
     dataZoom : {
        show : true,
        realtime : true,
        start : 0,
        end :80 
     }
    };
	    });
        };
      // 默认加载
      call_zzjg_fn();
    // 点击事件
//    $scope.daoHang = function(id){
//    	call_zzjg_fn(id);
//    	$scope.lastclick= id
//    };
//    $scope.biaoTi = function(id){
//    	if($scope.lastclick!=id)
//    	{
//    	call_zzjg_fn(id);
//    	$scope.lastclick = id;
//    	}else{}
//    };
      $scope.daoHang = function(id){
    	call_zzjg_fn(id);
    };
    $scope.biaoTi = function(id){
    	call_zzjg_fn(id);
    };
}]);
