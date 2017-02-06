/**
 * 不及格预测
 */
app.service("service",['httpService',function(http){
    return {
    	getYearAndTerm:function(callback){//得到学年学期
    		http.post({
        		url  : "failExamPredict/getYearAndTerm",
    	    	success : function(data){
    	    		callback(data);
    	    	}
    		});	
    	},
    	queryXfInfo:function(param,callback){//得到学生总体信息
    		http.post({
    			url  : "failExamPredict/queryGkInfo",
    			data : {"param":param},
    			success : function(data){
    				callback(data);
    			}
    		});	
    	},
    	queryXflxfb:function(param,callback){//不及格预测学生类型(男,女)
    	http.post({
    		url  : "failExamPredict/queryGklxfb",
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
	    				type : "pie",
						data : series_data,
						name : '人',
						config:{
							noDataText:'暂无学生性别分布数据',
							 calculable : false,
							 legend:{
							 	show:true
							 },
							 title : {
    	    					 text:'预测不及格性别分布',
    	    					show:false 
    	    				 },
							 toolbox:{
							 	show:true
							 },
							 series:[{
							 	radius:'60%',
							    center:['50%','50%']
							 }]
						}
	    			};
    			callback(option);
    		}
    	});	
    },
    queryXslxfb:function(param,callback){//得到未缴费学生类型及人数
    		http.post({
    			url  : "failExamPredict/queryXslxfb",
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
    	    				type : "pie",
    						data : series_data,
    						name : '人',
    						config:{
    							noDataText:'暂无学生原因分布数据',
    							 calculable : false,
    							 legend:{
    							 	show:true
    							 },
    							 title : {
        	    					 text:'预测不及格年级分布',
        	    					show:false 
        	    				 },
    							 toolbox:{
    							 	show:true
    							 },
    							 series:[{
    							 	radius:'60%',
    							    center:['50%','50%']
    							 }]
    						}
    	    			};
    				callback(option);
    			}
    		});	
    	},
    	queryQfjeAndRadio:function(param,callback){//得到预测不及格人数及比例
    		http.post({
        		url  : "failExamPredict/queryGkrsAndRadio",
        		data : {"param":param},
    	    	success : function(data){
    	   			// 处理数据
        			if(data.status){
        				var option = Ice.echartOption(data.list, {
        					yname_ary  : ['人', '%'], // 单位
        					legend_ary : ['人数', '占比'], // 图例
        					type_ary   : ['bar', 'line'], // 图标类型
        					value_ary  : ['value_change', 'value_stu'], // value所对应字段
        					config : {
        						 title : {
        	    					 text:'预测不及格人数及比例',
        	    					show:false 
        	    				 },
        						xAxis : [{ axisLabel : { rotate : -15 } }],
        						noDataText : '暂无异动学院分布'
        					}
        				});
        				callback(option);
        			}
    	    	}
    		});	
    	},
    	queryQfInfo:function(param,callback){
    		http.post({
        		url  : "failExamPredict/queryGkxxInfo",
        		data : {"param":param},
    	    	success : function(data){
    	    		var data_name={CL01:'',CL02:'',CL03:'',CL04:''};
    	    		var data1=[];
    	    		for(var i=0;i<data.list.length;i++){
    	    			if(data.list[i].list.length!=0){
    	    				data_name.CL01=data.list[i].list[0].CL01;
    	    				data_name.CL02=data.list[i].list[0].CL02;
    	    				data_name.CL03=data.list[i].list[0].CL03;
    	    				data_name.CL04=data.list[i].list[0].CL04;
    	    			data1[i]=angular.copy(data_name);	
    	    			}else{
    	    				data_name.CL01=data.list[i].name;
    	    				data_name.CL02=0;
    	    				data_name.CL03=0;
    	    				data_name.CL04=0;
    	    				data1[i]=angular.copy(data_name);	
    	    			}
    	    		}
    	    		callback(data1,data);
    	    	}
    		});	
    	},
    	//发送邮件
    	sendQfInfo : function(sendType,pid,list,callback){
    		http.post({
        		url  : "failExamPredict/sendGkInfo",
        		data :{
        			   "sendType":sendType,
        			   "pid":pid,
        			   "list":list
        			   },
    	    	success : function(data){
    	    		callback(data);
    	    	}
    		});	    		
    	},
    	//全部发送
    	sendAll : function(callback){
    		http.post({
    			url  : "failExamPredict/sendAll",
    			success : function(data){
    				callback(data);
    			}
    		});	    		
    	},
    	//全部导出
    	excelQfAll : function(callback){
    		http.post({
    			url  : "failExamPredict/excelGkAll",
    			success : function(data){
    				callback(data);
    			}
    		});	    		
    	},
    	//高级搜索
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Xg_failExamPredict" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	});
	    },
	    //得到下钻详细信息
	    getStuDetail : function(param, callback,error){
        	http.post({
        		url  : "pmsFailExamPredict/getStuDetail",
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
        		url  : "pmsFailExamPredict/down",
        		data : param,
        		success : function(){
        			alert("success");
        		},
        		error:error
        	})
        },
    };
}]);