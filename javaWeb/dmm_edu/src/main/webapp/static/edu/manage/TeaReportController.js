app.controller("TeaReportController", [ "$scope","dialog",'mask','$timeout','http','timeAlert',function(scope,dialog,mask,timeout,http,timeAlert) {
	  scope.school = {};
	  scope.stu = {};
	  scope.tea = {};
	  scope.mzcount = {};
	  scope.zzmm = {};
	  scope.biaoTid = "0";
	  var vm = scope.vm = {};
	  vm.items = [];
	     /**
	     * 查询组织机构组件数据
	     */
	    var call_zzjg = {
	    	service : 'teaStatisticsService?getSchoolName'
	    }
	    var call_zzjg_fn = function(id){
	    	//call_zzjg.params = [id==null?'':id];
		   	http.callService(call_zzjg).success(function(reData){
		   		// reData 是数组
				scope.yxs = reData;
		    });
		   	call_data1(id)
		    call_data2(id);//查询指定单位名称，单位下的学生数，以及单位下的专业数，男生数，女生数(性别比例)
		    call_data3(id);//查询指定单位下的学生就读各个学历以及人数
		    call_data4(id);//查询指定单位下各汉族的学生数和少数民族的学生数
		    call_data5(id);//查询指定单位下的学生的来源地分布(来源地分布)
		    call_data6(id);//查询指定单位下的学生的年龄分布(年龄分布)
		    call_data7(id);//(学历组成)
		    call_data91(id);
		    call_data9(id);//查询指定单位下各民族的学生数(民族组成)
		    call_data8(id);//查询指定单位下各政治面貌各学历类别的学生数(政治面貌)
		    call_data81(id);
	    }
	    var data1= {
		      	service :'stuStatisticsService?queryYxdName'
		      }
	    var call_data1= function(id){
	          data1.params = [id==null?"":id];
	        	http.callService(data1).success(function(reData1){
		   		// reData 是数组
				scope.school = reData1;
				
		    });
	        }
	         var data2= {
	      	service :'teaStatisticsService?getRs'
	      }
	         var getdata2bl=function(data){
	        	 var all=0;
	        	 for(var i=0;i<data.length;i++){
	        		 all+=Number(data[i].COUNT_);
	        	 }
	        	 for(var i=0;i<data.length;i++){
	        		data[i].bl=Percentage(Number(data[i].COUNT_),all);
	        	 }
	        	 return data;
	         }
	        var call_data2= function(id){//性别组成
	          data2.params = [id==null?"":id];
	        	http.callService(data2).success(function(data){
	        		var xbrs=data[0].xbrs;
	        		var zglb=data[0].zglb;//职工类别
	        		var bzlb=data[0].bzlb;//编制类别
				scope.tea.xbrs = getdata2bl(xbrs);
				scope.tea.nanrs = scope.tea.xbrs[0].COUNT_;
				scope.tea.nvrs = scope.tea.xbrs[1].COUNT_;
				scope.tea.rs=scope.tea.nanrs +scope.tea.nvrs;
				scope.tea.zglb = getdata2bl(zglb);scope.tea.bzlb = getdata2bl(bzlb);
		    });
	        }
	         //职称级别统计
	         var data3= {service :'teaStatisticsService?getZcjb'};
	            var call_data3= function(id){
	          data3.params = [id==null?"":id];
	        	http.callService(data3).success(function(reData3){//职称级别统计
		   		// reData 是数组
	    			data=[{FIELD:'正高级',VALUE:'256',bz:1},
	  					{FIELD:'副高级',VALUE:'256',bz:1},
	  					{FIELD:'助理级',VALUE:'256',bz:1},
	  				{FIELD:'高级',VALUE:'256',bz:1},
	  			{FIELD:'初级',VALUE:'256',bz:0}]
	  			var sum={yf:0,dk:0,sf:0};
	  			for(var i=0;i<reData3.length;i++){
	  				sum.yf+=Number(reData3[i].VALUE);
	  				if(reData3[i].bz==0){
	  					sum.dk+=Number(reData3[i].VALUE);
	  				}
	  			}
	  			sum.sf=sum.yf-sum.dk;
	  			vm.items[0] ={
	  					option:echarbzt(data,{text:''}),
	  					list:reData3,
	  					sum:sum
	  				}; 
		    });
	        }
	            //职称统计
	         var data4= {service :'teaStatisticsService?getZc'};
	            var call_data4= function(id){
	          data4.params = [id==null?"":id];
	        	http.callService(data4).success(function(reData4){//职称统计
		   		// reData 是数组
//	    			data=[{FIELD:'基本工资',VALUE:'256',bz:1},
//	  					{FIELD:'课时费',VALUE:'256',bz:1},
//	  					{FIELD:'绩效工资',VALUE:'256',bz:1},
//	  				{FIELD:'住房补贴',VALUE:'256',bz:1},
//	  			{FIELD:'五险一金',VALUE:'256',bz:0}]
	  			var sum={yf:0,dk:0,sf:0};
	  			for(var i=0;i<reData4.length;i++){
	  				sum.yf+=Number(reData4[i].VALUE);
	  				if(reData4[i].bz==0){
	  					sum.dk+=Number(reData4[i].VALUE);
	  				}
	  			}
	  			sum.sf=sum.yf-sum.dk;
	  			vm.items[1] ={
	  					option:echarbzt(reData4,{text:''}),
	  					list:reData4,
	  					sum:sum
	  				}; 
		    });
	        }
	             var data10= {service :'teaStatisticsService?getZzmm'};
	            var call_data10= function(id){
	          data10.params = [id==null?"":id];
	        	http.callService(data10).success(function(reData10){
		   		// reData 是数组
				scope.zzmm.ty = reData10[0].RS;
				scope.zzmm.bsty = reData10[1].RS;
				scope.zzmm.wwh = reData10[2].RS;
		    });
	        }
	        var data5= {
	      	service :'teaStatisticsService?getLyd'//来源地分布
	      }
	        var call_data5= function(id){
	          data5.params = [id==null?"":id];
	        	http.callService(data5).success(function(reData5){
		   		// reData 是数组
				scope.syd = reData5;
				scope.mapChart =  {
	  	            data :scope.syd,
	  	             type :'map'
	    };
		    });
	        }
	                var data6= {
	      	service :'teaStatisticsService?getNl'//学生年龄分布
	      }
	        var call_data6= function(id){
	          data6.params = [id==null?"":id];
	        	http.callService(data6).success(function(reData6){
	        	scope.nlfb = reData6;
		   scope.areaChart =  {
		  	      type :'area',
		  	      data:scope.nlfb,
		  	      isSort:true,
		  	      legendSort:true,
	              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	               type : 'line'        // 默认为直线，可选为：'line' | 'shadow'
	              },
	              legend:{
		  	      	show : false
		  	      },
	        toolbox: {
	        show : true,
//	        orient : 'vertical',
//	        x : 'right',
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
	        show : true,
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
	      	service :'teaStatisticsService?getXl'//学历组成
	      }
	        var call_data7= function(id){
	          data7.params = [id==null?"":id];
	        	http.callService(data7).success(function(reData7){
		   		// reData 是数组
				scope.rydb = reData7;
				
				 /* scope.pieChart =  {
		  	      type :'pie',
		  	    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
//		  	      data:scope.rydb,
		  	    series : 
		  		        {
		  		            name:'学历',
		  		            type:'pie',
		  		            radius : '55%',
		  		            center: ['50%', '60%'],
		  		            data:scope.rydb
		  		        }
		  		    	
	    };*/
		 scope.pieChart= echarbzt(reData7,{text:''});
		  scope.pieChart.type ='pie';
		    });
	        };
            var data81= {service :'teaStatisticsService?queryZzmm1'};
            var call_data81= function(id){
          data81.params = [id==null?"":id];
        	http.callService(data81).success(function(reData81){
	   		// reData 是数组
			scope.zzmm.ty = reData81[0].RS;
			scope.zzmm.bsty = reData81[1].RS;
			scope.zzmm.wwh = reData81[2].RS;
	    });
        }
	        var data8= {
	      	service :'teaStatisticsService?getZzmm'
	      }
	       var call_data8= function(id){
	          data8.params = [id==null?"":id];
	        http.callService(data8).success(function(reData8){
		   		// reData 是数组
				scope.zzmm = reData8;
		  scope.columnfChart =  {
		  	      type :'columnf',
		  	      data:scope.zzmm,
		  	      isSort:true,
	              axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	               type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	              },
	        toolbox: {
//	        orient : 'vertical',
//	        x : 'right',
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
		    var data91= {
			      	service :'teaStatisticsService?getMzCount'
			      }
			       var call_data91= function(id){
			          data91.params = [id==null?"":id];
			        http.callService(data91).success(function(reData91){
			        	// reData 是数组
						scope.mzcount.hz = reData91[0].VALUE;
						scope.mzcount.ssmz = reData91[1].VALUE;
						scope.mzcount.wwh = reData91[2].VALUE;
			        });
		    }
	    var data9= {
	      	service :'teaStatisticsService?getMz'//民族分布形式
	      }
	       var call_data9= function(id){
	          data9.params = [id==null?"":id];
	        http.callService(data9).success(function(reData9){
		   		// reData 是数组
				scope.mz = reData9;
		  scope.column1Chart =  {
		  	      type :'column',
		  	      data:scope.mz,
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
//	        orient : 'vertical',
//	        x : 'right',
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
	      call_zzjg_fn("0");
	      scope.daoHang = function(id){
	    	  scope.biaoTid=id;
	    	call_zzjg_fn(id);
	    	scope.xsxlClick("");
	    };
	    scope.biaoTi = function(id){
	    	scope.biaoTid=id;
	    	call_zzjg_fn(id);
	    	scope.xsxlClick("");
	    };	
	    scope.xsxlClick=function(stu_id){
	    	scope.xlCode_=stu_id;
//	    	call_lb_data(scope.biaoTid,stu_id);
	    }
		  function Percentage(num, total) { 
			    return (Math.round(num / total * 10000) / 100.00 + "%");// 小数点后两位百分比
			}	
}]);