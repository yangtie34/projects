/**
 * 学费预警
 */
app.service("service",['httpService',function(http){
    return {
    	queryXfInfo:function(param,callback){//得到未缴费学生总体信息(总人数,总金额)
    		http.post({
        		url  : "feeWarning/queryXfInfo",
        		data : {"param":param},
    	    	success : function(data){
    	    		callback(data);
    	    	}
    		});	
    	},
    	querySelectType:function(callback){//得到未缴费学生总体信息(总人数,总金额)
    		http.post({
    			url  : "feeWarning/querySelectType",
    			success : function(data){
    				callback(data[0]);
    			}
    		});	
    	},
    	queryXflxfb:function(param,callback){//得到未缴费学费类型及金额
    	http.post({
    		url  : "feeWarning/queryXflxfb",
    		data : {"param":param},
    		success : function(data){
	    		var _data={value:0,name:''};//得到数据
	    		var data_type=[];//得到学费类型
	    		var series_data=[];
	    		var j=0;
	    		for(var i=0;i<data.length;i++)
	    			{
	    			if(data[i].NAME_!=null){
	    			_data.value=data[i].COUNT_;
	    			_data.name=data[i].NAME_;
	    			data_type[j]=angular.copy(_data.name);
	    			series_data[j]=angular.copy(_data);
	    			j++;
	    			}
	    			};
	    		option = {
	    			    title : {
	    			        text: '欠费类型分布',
	    			      	show : false,
	    			    },
	     				config :{
	    					on :['CLICK','QFNAMEMC']
	    				},
	    				noDataText:'暂无学费类型分布数据',
	    			    tooltip : {
	    			        trigger: 'item',
	    			        formatter: "{a} <br/>{b} : {c}元 ({d}%)"
	    			    },
	    			    toolbox: {
	    			        show : true,
	    			        y	 : 15,
	    			        feature : {
	    			            dataView : {show: true, readOnly: false},
	    			            restore : {show: true},
	    			            saveAsImage : {show: true}
	    			        }
	    			    },
	    			    series : [
	    			        {
	    			            name:'学费预警',
	    			            type:'pie',
	    			            radius : '70%',
	    			            center : [ '50%', '55%'],
	    			            data:series_data
	    			        }
	    			    ]
	    			};
    			callback(option);
    		}
    	});	
    },
    queryXslxfb:function(param,callback){//得到未缴费学生类型及人数
    		http.post({
    			url  : "feeWarning/queryXslxfb",
    			data : {"param":param},
    			success : function(data){
    	    		var _data={value:0,name:''};//得到数据
    	    		var data_type=[];//得到学生类型
    	    		var series_data=[];
    	    		var j=0;
    	    		for(var i=0;i<data.length;i++)
    	    			{
    	    			if(data[i].GRADE_!=null){
    	    			_data.value=data[i].COUNT_;
    	    			_data.name=data[i].GRADE_;
    	    			data_type[j]=angular.copy(_data.name);
    	    			series_data[j]=angular.copy(_data);
    	    			j++;
    	    			}
    	    			};
    	    		option = {
    	    			    title : {
    	    			        text: '欠费年级分布',
    	    			      	show : false,
    	    			    },
    	    				config :{
		    					on :['CLICK','GRADEMC']
		    				},
		    				noDataText:'暂无学生年级分布数据',
    	    			    tooltip : {
    	    			        trigger: 'item',
    	    			        formatter: "{a} <br/>{b} : {c}人 ({d}%)"
    	    			    },
    	    			    toolbox: {
    	    			        show : true,
    	    			        feature : {
    	    			            dataView : {show: true, readOnly: false},
    	    			            restore : {show: true},
    	    			            saveAsImage : {show: true}
    	    			        }
    	    			    },
    	    			    series : [
    	    			        {
    	    			            name:'学费预警',
    	    			            type:'pie',
    	    			            radius : '70%',
    	    			            center : [ '50%', '50%'],
    	    			            data:series_data
    	    			        }
    	    			    ]
    	    			};
    				callback(option);
    			}
    		});	
    	},
    	queryQfjeAndRadio:function(param,callback){//得到历年欠费人数及比例
    		http.post({
        		url  : "feeWarning/queryQfjeAndRadio",
        		data : {"param":param},
    	    	success : function(data){
    				// 处理数据
        			if(data.status){
        				var option = Ice.echartOption(data.list, {
        					yname_ary  : ['元', '%'], // 单位
        					legend_ary : ['金额', '占比'], // 图例
        					type_ary   : ['bar', 'line'], // 图标类型
        					value_ary  : ['value_change', 'value_stu'], // value所对应字段
        					config : {
        						 title : {
         	    			        text: '历年欠费金额和比例',
         	    			        show:false
         	    			    },
        						xAxis : [{ axisLabel : { rotate : -15 } }],
        						noDataText : '暂无欠费年份分布数据'
        					}
        				});
        				callback(option);
        			}
//    	    		var data_name=[];//年份
//    	    		for(var i=0;i<data.year.length;i++){//得到年份
//    	    			data_name[i]=data.year[i]+'-'+(data.year[i]+1);
//    	    		}
//    	    		var data_rs=[];//人数数据
//    	    		var data_bl=[];//比例数据
//    	    		for(var i=0;i<data.list.length;i++){
//    	    			if(data.list[i].list.length!=0){
//    	    			data_rs[i]=data.list[i].list[0].MONEY_;//得到欠费金额
//    	    			data_bl[i]=data.list[i].list[0].RADIO;//得到欠费学生人数比例
//    	    			}else{
//    		    			data_rs[i]=0;//得到欠费金额
//    		    			data_bl[i]=0;//得到欠费学生人数比例	    				
//    	    			}
//    	    		}
//    	    		option = {
//    	    			    title : {
//    	    			        text: '历年欠费金额和比例',
//    	    			        show:false
//    	    			    },
//    	    				config :{
//    	    					on :['CLICK','QFHISTORY']
//    	    				},
//    	    				noDataText:'暂无历年欠费分布数据',
//    	    			    tooltip : {
//    	    			        trigger: 'axis',
//        				        formatter: function (params,ticket,callback) {
//        				        	var res = params[0].name+'学年';
//        				            var unit='元';
//        				            for (var i = 0, l = params.length; i < l; i++) {
//        				            	if(params[i].seriesName=='金额') unit='元';
//        				            	if(params[i].seriesName=='比例') unit='%';
//        				                res += '<br/>' + params[i].seriesName + ' : ' + params[i].value+unit;
//        				            }
//        				        return res;
//        				        },
//    	    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
//    	    			            type : 'shadow',         // 默认为直线，可选为：'line' | 'shadow'
//    	    			             lineStyle : {          // 直线指示器样式设置
//    	    			                 color: '#48b',
//    	    			                 width: 2,
//    	    			                 type: 'solid'
//    	    			             }}
//    	    			    },
//    	    			    toolbox: {
//    	    			        show : true,
//    	    			        feature : {
//    	    			            dataView : {show: true, readOnly: false},
//    	    			            restore : {show: true},
//    	    			            saveAsImage : {show: true}
//    	    			        }
//    	    			    },
//    	    			    legend: {
//	    				    	   x: 70,
////	    				    	   y: 10,
//    	    			        data:['金额','比例']
//    	    			    },
//    	    			    calculable : false,
//    	    			    xAxis : [
//    	    			        {
//    	    			        	name : '学年',
//    	    			            type : 'category',
//    	    			            data : data_name
//    	    			        }
//    	    			    ],
//    	    			    grid :{x:80},
//    	    			     yAxis : [
//    	    			              
//    	    			              {
////    	    			            	  x:190,
//    	    			                  type : 'value',
//    	    			                  name : '金额/元',
//    	    			                  axisLabel : {
////    	    			                	  interval:0 
////    	    			                      formatter: '{value} 元'
//    	    			                  },
////    	    			                  max:15000000
//    	    			              },
//    	    			              {
//    	    			                  type : 'value',
//    	    			                  name : '比例/%',
//    	    			                  axisLabel : {
//    	    			                      formatter: '{value} %'
//    	    			                  }
//    	    			              }
//    	    			    ],
//    	    			    series : [
//    	    			        {
//    	    			            name:'金额',
//    	    			            type:'bar',
//    	    			            itemStyle: {normal: { label : {show: true} }},
//    	    			            data:data_rs
//    	    			        }
//    	    			        ,{
//    	    			            name:'比例',
//    	    			            type : 'line',
//    	    			            yAxisIndex: 1,
//    	    			            data:data_bl,
//    	    			          	 itemStyle: {normal:{ label:{formatter:'{b}'+'%'} }}
//    	    			        }
//    	    			    ]
//    	    			};
//    	    		callback(option);
    	    	}
    		});	
    	},
    	queryQfInfo:function(year,edu,lx,slx,callback){
    		http.post({
        		url  : "feeWarning/queryQfInfo",
        		data :{
        				"year":year,
        				"edu":edu,
        				"lx" :lx,
        				"slx":slx
        		},
    	    	success : function(data){
    	    		var data_name={CL01:'',CL02:'',CL03:'',CL04:'',CL05:''};
    	    		var data1=[];
    	    		for(var i=0;i<data.list.length;i++){
    	    			data_name.CL01=data.list[i].name;
    	    			data_name.CL02=data.list[i].list[0].CL02.CL02;
    	    			if(data.list[i].list[0].list.length!=0){
    	    			if(data.list[i].list[0].list[0].CL05!=null){
//    	    				data_name.CL01=data.list[i].list[0].list[0].CL01;
//    	    				data_name.CL02=data.list[i].list[0].CL02;
    	    				data_name.CL03=data.list[i].list[0].list[0].CL03;
    	    				data_name.CL04=data.list[i].list[0].list[0].CL04+'%';
    	    				data_name.CL05=data.list[i].list[0].list[0].CL05;
    	    			data1[i]=angular.copy(data_name);	
    	    			}else{
//    	    				data_name.CL01=data.list[i].list[0].list[0].CL01;
//    	    				data_name.CL02=data.list[i].list[0].CL02;
    	    				data_name.CL03=data.list[i].list[0].list[0].CL03;
    	    				data_name.CL04=data.list[i].list[0].list[0].CL04+'%';
    	    				data_name.CL05=0;
    	    				data1[i]=angular.copy(data_name);	
    	    			}}else{
//    	    				data_name.CL01=data.list[i].name;
//    	    				data_name.CL02=0;
    	    				data_name.CL03=0;
    	    				data_name.CL04=0+'%';
    	    				data_name.CL05=0;
    	    				data1[i]=angular.copy(data_name);	
    	    			}
    	    		}
    	    		callback(data1,data);
    	    	}
    		});	
    	},
    	//发送邮件
    	sendQfInfo : function(sendType,pid,list,year,edu,lx,slx,callback){
    		http.post({
        		url  : "feeWarning/sendQfInfo",
        		data :{
        			   "sendType":sendType,
        			   "pid":pid,
        			   "list":list,
        			   "year":year,
        			   "edu" :edu,
        			   "lx"  :lx,
        			   "slx" :slx
        			   },
    	    	success : function(data){
    	    		callback(data);
    	    	}
    		});	    		
    	},
    	//全部发送
    	sendAll : function(callback){
    		http.post({
    			url  : "feeWarning/sendAll",
    			success : function(data){
    				callback(data);
    			}
    		});	    		
    	},
    	//全部导出
    	excelQfAll : function(callback){
    		http.post({
    			url  : "feeWarning/excelQfAll",
    			success : function(data){
    				callback(data);
    			}
    		});	    		
    	},
    	//高级搜索
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Xg_feeWarning" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	});
	    }, 
	    //得到下钻详细信息
	    getStuDetail : function(param, callback,error){
        	http.post({
        		url  : "pmsFeeWarning/getStuDetail",
        		data : param,
        		success : function(data){
        			callback(data);
        		},
        		error:error
        	});
        },	
        //导出
        getStuDetailDown : function(param, callback,error){
        	http.fileDownload({
        		url  : "pmsFeeWarning/down",
        		data : param,
        		success : function(){
        			alert("success");
        		},
        	    error:error
        	})
        },
    };
}]);