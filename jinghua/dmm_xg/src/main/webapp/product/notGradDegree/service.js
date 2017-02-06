app.service("service",['httpService',function(http){
    return {
    	queryXwInfo:function(param,callback){//得到各个院系的无法毕业无学位情况
    		http.post({
        		url  : "notGradDegree/queryXwInfo",
        		data : {"param":param},
    	    	success : function(data){
    	    		callback(data);
    	    	}
    		});	
    	},
    	//得到无法毕业无学位证类型
    	getNoDegreeType:function(callback){
    		http.post({
    			url  : "notGradDegree/getNoDegreeType",
    			success : function(data){
    				callback(data[0]);
    			}
    		});	
    	},
    queryXwfbAndRatio: function(fb,param,callback){//无法毕业学生分布和比例
		http.post({
    		url  : "notGradDegree/queryXwfbAndRatio",
    		data :{"fb" : fb,"param":param},
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
     	    			        text: '无法毕业学生分布和比例',
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
    //无学位学生学科分布
    queryXkfb : function(param,callback){
    	http.post({
    		url  : "notGradDegree/queryXkfb",
    		data : {"param":param},
	    	success : function(data){
	    		var _data={value:0,name:''};//得到数据
	    		var data_type=[];//得到学科
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
							noDataText:'暂无学生学科分布数据',
							 calculable : false,
							 legend:{
							 	show:true,
							 	itemGap: -5,
							 },
							 title : {
	     	    			        text: '学生学科分布',
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
    //年级分布
    queryNjfb : function(param,callback){
    	http.post({
    		url  : "notGradDegree/queryNjfb",
    		data : {"param":param},
	    	success : function(data){  
	    		var _data={value:0,name:''};//得到数据
	    		var data_type=[];//得到年级
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
								noDataText:'暂无学生年级分布数据',
								 calculable : false,
								 legend:{
								 	show:true
								 },
								 title : {
		     	    			        text: '学生年级分布',
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
    //性别分布
    queryXbfb : function(param,callback){
    	http.post({
    		url  : "notGradDegree/queryXbfb",
    		data : {"param":param},
	    	success : function(data){ 
	    		var _data={value:0,name:'女'};
	    		var series_data=[];
	    		var j=0;
	    		for(var i=0;i<data.length;i++)
	    			{
	    			if(data[i].NAME_!=null){
	    			_data.value=data[i].COUNT_;
	    			_data.name=data[i].NAME_;
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
	     	    			        text: '学生性别分布',
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
    //原因分布
    queryYyfb : function(param,callback){
    	http.post({
    		url  : "notGradDegree/queryYyfb",
    		data : {"param":param},
	    	success : function(data){ 
	    		var _data={value:0,name:'学分不够'};
	    		var series_data=[];
	    		var j=0;
	    		for(var i=0;i<data.length;i++)
	    			{
	    			if(data[i].COUNT_!='0'){
	    			_data.value=data[i].COUNT_;
	    			_data.name='学分不够';
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
	     	    			        text: '学生原因分布',
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
    /*****************************************************************************************************/
    /***********************************最近几年无学位学生数据**************************************************/
    //最近几年学生原因分布
    queryStatefbByYear : function(param,callback){
    	http.post({
    		url  : "notGradDegree/queryStatefbByYear",
    		data : {"param":param},
	    	success : function(data){ 
	    		var data_type=['学分不够'];//性别类型
	    		var data_name=[];//得到年份
	    		var series_data=[{
	    			 name:'',
		             type:'line',
		             data:[]//COUNT_
	    		}];
	    		var j=0;
	    		for(var i=0;i<data.year.length;i++){//得到学年
	    			data_name[i]=data.year[i]+'-'+(data.year[i]+1);
	    		}
	    		var data_count=[];
	    		var m=0;
	    		//得到series
	    		for(var i=0;i<data_type.length;i++){
	    			
	    			for(var k=0;k<data_name.length;k++){
	    				var flag=0;
	    			  for(var j=0;j<data.list[k].list.length;j++){
	    					if(data.list[k].list[j].NAME_==data_type[i]){//
    	    					data_count[m++]=data.list[k].list[j].COUNT_;
    	    					flag=1;
    	    				}
//	    				}
	    				}
	    			  if(flag==0){
	    				  data_count[m++]=0;  
	    			  }
	    			}
	    			series_data[i]=angular.copy(series_data[0]);
	    			series_data[i].name=angular.copy(data_type[i]);
	    			series_data[i].data=angular.copy(data_count);
	    			m=0;
	    		}
	    		series_data.data=data_count;
	    		option = {
	    				config :{
	    					on :['CLICK','DEPTMC']
	    				},
	    				noDataText:'暂无原因分布数据',
	    				 title : {
  	    			        text: '历年学生原因分布',
  	    			        show:false
  	    			    },
	    			    tooltip : {
	    			        trigger: 'axis',
    				        formatter: function (params,ticket,callback) {
    				        	var res = params[0].name+'学年';
    				            var unit=['人'];
    				            var count=0;
    				            var value='';
    				            for (var i = 0, l = params.length; i < l; i++) {
    				                value += '<br/>' + params[i].seriesName + ' : ' + params[i].value+unit[i];
    				           count=count+params[i].value;
    				            }
    				            res +=" 共"+count+"人"+value;
    				        return res;
    				        },
	    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	    			            type : 'shadow',         // 默认为直线，可选为：'line' | 'shadow'
	    			             lineStyle : {          // 直线指示器样式设置
	    			                 color: '#48b',
	    			                 width: 2,
	    			                 type: 'solid'
	    			             }}
	    			    },
	    			    legend: {
	    			    	x:'70', 
	    			        data:data_type,
	    			        y : 15
	    			    },
	    			    toolbox: {
	    			        show : true,
	    			        feature : {
	    			            dataView : {show: true, readOnly: false},
	    			            restore : {show: true},
	    			            saveAsImage : {show: true}
	    			        }
	    			    },
	    			    calculable : false,
	    			    xAxis : [
	    			        {
	    			        	name : '学年',
	    			            type : 'category',
	    			            boundaryGap : false,
	    			            data : data_name
	    			        }
	    			    ],
	    			    yAxis : [
	    			        {
	    			        	name : '人',
	    			            type : 'value'
	    			        }
	    			    ],
	    			    series :series_data
	    			};	
	    		callback(option);
	    	}    	
    	    });
    		},
	        //最近几年学生年级分布
	    	queryNjfbByYear : function(param,callback){
	        	http.post({
	        		url  : "notGradDegree/queryNjfbByYear",
	        		data : {"param":param},
	    	    	success : function(data){  
	    	    		var data_type=['大一','大二','大三','大四'];//年级类型
	    	    		var data_name=[];//年份
	    	    		var series_data=[{
	    	    			 name:'',
	    		             type:'line',
	    		             data:[]//COUNT_
	    	    		}];
	    	    		var j=0;
	    	    		for(var i=0;i<data.year.length;i++){//得到学年
	    	    			data_name[i]=data.year[i]+'-'+(data.year[i]+1);
	    	    		}
	    	    		var data_count=[];
	    	    		var m=0;
	    	    		//得到series
	    	    		for(var i=0;i<data_type.length;i++){
	    	    			
	    	    			for(var k=0;k<data_name.length;k++){
	    	    				var flag=0;
	    	    			  for(var j=0;j<data.list[k].list.length;j++){
	    	    					if(data.list[k].list[j].GRADE_==data_type[i]){//年级类型
	        	    					data_count[m++]=data.list[k].list[j].COUNT_;
	        	    					flag=1;
	        	    				}
//	    	    				}
	    	    				}
	    	    			  if(flag==0){
	    	    				  data_count[m++]=0;  
	    	    			  }
	    	    			}
	    	    			series_data[i]=angular.copy(series_data[0]);
	    	    			series_data[i].name=angular.copy(data_type[i]);
	    	    			series_data[i].data=angular.copy(data_count);
	    	    			m=0;
	    	    		}
	    	    		series_data.data=data_count;
	    	    		option = {
	    	    				config :{
	    	    					on :['CLICK','DEPTMC']
	    	    				},
	    	    				noDataText:'暂无年级分布数据',
	    	    				 title : {
		     	    			        text: '历年学生年级分布',
		     	    			        show:false
		     	    			    },
	    	    			    tooltip : {
	    	    			        trigger: 'axis',
	        				        formatter: function (params,ticket,callback) {
	        				        	var res = params[0].name+'学年';
	        				            var unit=['人'];
	        				            var count=0;
	        				            var value='';
	        				            for (var i = 0, l = params.length; i < l; i++) {
	        				            	unit[i]='人';
	        				                value += '<br/>' + params[i].seriesName + ' : ' + params[i].value+unit[i];
	        				           count=count+params[i].value;
	        				            }
    	    				            res +=" 共"+count+"人"+value;
	        				        return res;
	        				        },
	    	    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	    	    			            type : 'shadow',         // 默认为直线，可选为：'line' | 'shadow'
	    	    			             lineStyle : {          // 直线指示器样式设置
	    	    			                 color: '#48b',
	    	    			                 width: 2,
	    	    			                 type: 'solid'
	    	    			             }}
	    	    			    },
	    	    			    legend: {
	    	    			    	x:'70', 
	    	    			        data:data_type,
	    	    			        y : 15
	    	    			    },
	    	    			    toolbox: {
	    	    			        show : true,
	    	    			        feature : {
	    	    			            dataView : {show: true, readOnly: false},
	    	    			            restore : {show: true},
	    	    			            saveAsImage : {show: true}
	    	    			        }
	    	    			    },
	    	    			    calculable : false,
	    	    			    xAxis : [
	    	    			        {
	    	    			        	name : '学年',
	    	    			            type : 'category',
	    	    			            boundaryGap : false,
	    	    			            data : data_name
	    	    			        }
	    	    			    ],
	    	    			    yAxis : [
	    	    			        {
	    	    			        	name : '人',
	    	    			            type : 'value'
	    	    			        }
	    	    			    ],
	    	    			    series : series_data
	    	    			};	
	    	    		callback(option);
	    	    	}
	    	    });
    		},
	     //最近几年学生学科分布
    		queryXkfbByYear : function(param,callback){
	    	        	http.post({
	    	        		url  : "notGradDegree/queryXkfbByYear",
	    	        		data : {"param":param},
	    	    	    	success : function(data){  
	    	    	    		var data_type=[];//学科类型
	    	    	    		var data_name=[];//年份
	    	    	    		var series_data=[{
	    	    	    			 name:'',
	    				             type:'line',
	    				             data:[]//COUNT_
	    	    	    		}];
	    	    	    		for(var j=0;j<data.list.length;j++){
	    	    	    		  if(data.list[j].list.length!=0){
	    	    	    			for(var i=0;i<data.list[j].list.length;i++){//得到学科类型可能会出现bug
	    	    	    				if(data.list[j].list[i].NAME_!=null){
	    	    	    					data_type[i]=data.list[j].list[i].NAME_;
	    	    	    				}
	    	    	    			}
	    	    	    			break;
	    	    	    		  }
	    	    	    		}
	    	    	    		var j=0;
	    	    	    		for(var i=0;i<data.year.length;i++){//得到学年
	    	    	    			data_name[i]=data.year[i]+'-'+(data.year[i]+1);
	    	    	    		}
	    	    	    		var data_count=[];
	    	    	    		var m=0;
	    	    	    		//得到series
	    	    	    		for(var i=0;i<data_type.length;i++){
	    	    	    			
	    	    	    			for(var k=0;k<data_name.length;k++){
	    	    	    				var flag=0;
	    	    	    			  for(var j=0;j<data.list[k].list.length;j++){
	    	    	    					if(data.list[k].list[j].NAME_==data_type[i]){//年级类型
	    	        	    					data_count[m++]=data.list[k].list[j].COUNT_;
	    	        	    					flag=1;
	    	        	    				}
//	    	    	    				}
	    	    	    				}
	    	    	    			  if(flag==0){
	    	    	    				  data_count[m++]=0;  
	    	    	    			  }
	    	    	    			}
	    	    	    			series_data[i]=angular.copy(series_data[0]);
	    	    	    			series_data[i].name=angular.copy(data_type[i]);
	    	    	    			series_data[i].data=angular.copy(data_count);
	    	    	    			m=0;
	    	    	    		}
	    	    	    		series_data.data=data_count;
	    	    	    		option = {
	    	    	    				config :{
	    	    	    					on :['CLICK','DEPTMC']
	    	    	    				},
	    	    	    				noDataText:'暂无学科分布数据',
	    	    	    				 title : {
	 		     	    			        text: '历年学生学科分布',
	 		     	    			        show:false
	 		     	    			    },
	    	    	    			    tooltip : {
	    	    	    			        trigger: 'axis',
	    	        				        formatter: function (params,ticket,callback) {
	    	        				        	var res = params[0].name+'学年';
	    	        				            var unit=['人'];
	    	        				            var count=0;
	    	        				            var value='';
	    	        				            for (var i = 0, l = params.length; i < l; i++) {
	    	        				                value += '<br/>' + params[i].seriesName + ' : ' + params[i].value+unit[0];
	    	        				           count=count+params[i].value;
	    	        				            }
	        	    				            res +=" 共"+count+"人"+value;
	    	        				        return res;
	    	        				        },
	    	    	    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	    	    	    			            type : 'shadow',         // 默认为直线，可选为：'line' | 'shadow'
	    	    	    			             lineStyle : {          // 直线指示器样式设置
	    	    	    			                 color: '#48b',
	    	    	    			                 width: 2,
	    	    	    			                 type: 'solid'
	    	    	    			             }}
	    	    	    			    },
	    	    	    			    legend: {
	    	    	    			    	x:'70', 
	    	    	    			        data:data_type,
	    	    	    			        y : 15,
	    	    	    			        itemGap: -5,
	    	    	    			    },
	    	    	    			    toolbox: {
	    	    	    			        show : true,
	    	    	    			        feature : {
	    	    	    			            dataView : {show: true, readOnly: false},
	    	    	    			            restore : {show: true},
	    	    	    			            saveAsImage : {show: true}
	    	    	    			        }
	    	    	    			    },
	    	    	    			    calculable : false,
	    	    	    			    xAxis : [
	    	    	    			        {
	    	    	    			        	name : '学年',
	    	    	    			            type : 'category',
	    	    	    			            boundaryGap : false,
	    	    	    			            data :data_name
	    	    	    			        }
	    	    	    			    ],
	    	    	    			    yAxis : [
	    	    	    			        {
	    	    	    			        	name : '人',
	    	    	    			            type : 'value'
	    	    	    			        }
	    	    	    			    ],
	    	    	    			    series : series_data
	    	    	    			};	
	    	    	    		callback(option);
	    	    	    	}
	    	    	    });
	        		},
	    	    //最近几年学生性别分布
	    	    queryXbfbByYear : function(param,callback){
	    	    	        	http.post({
	    	    	        		url  : "notGradDegree/queryXbfbByYear",
	    	    	        		data : {"param":param},
	    	    	    	    	success : function(data){ 
	    	    	    	    		var data_type=['男','女'];//性别类型
	    	    	    	    		var data_name=[];//得到年份
	    	    	    	    		var series_data=[{
	    	    	    	    			 name:'',
	    	    				             type:'line',
	    	    				             data:[]//COUNT_
	    	    	    	    		}];
	    	    	    	    		var j=0;
	    	    	    	    		for(var i=0;i<data.year.length;i++){//得到学年
	    	    	    	    			data_name[i]=data.year[i]+'-'+(data.year[i]+1);
	    	    	    	    		}
	    	    	    	    		var data_count=[];
	    	    	    	    		var m=0;
	    	    	    	    		//得到series
	    	    	    	    		for(var i=0;i<data_type.length;i++){
	    	    	    	    			
	    	    	    	    			for(var k=0;k<data_name.length;k++){
	    	    	    	    				var flag=0;
	    	    	    	    			  for(var j=0;j<data.list[k].list.length;j++){
	    	    	    	    					if(data.list[k].list[j].NAME_==data_type[i]){//年级类型
	    	    	        	    					data_count[m++]=data.list[k].list[j].COUNT_;
	    	    	        	    					flag=1;
	    	    	        	    				}
//	    	    	    	    				}
	    	    	    	    				}
	    	    	    	    			  if(flag==0){
	    	    	    	    				  data_count[m++]=0;  
	    	    	    	    			  }
	    	    	    	    			}
	    	    	    	    			series_data[i]=angular.copy(series_data[0]);
	    	    	    	    			series_data[i].name=angular.copy(data_type[i]);
	    	    	    	    			series_data[i].data=angular.copy(data_count);
	    	    	    	    			m=0;
	    	    	    	    		}
	    	    	    	    		series_data.data=data_count;
	    	    	    	    		option = {
	    	    	    	    				config :{
	    	    	    	    					on :['CLICK','DEPTMC'],
	    	    	    	    					 title : {
	    	 	 		     	    			        text: '历年学生性别分布',
	    	 	 		     	    			        show:false
	    	 	 		     	    			    },
	    	    	    	    				},
	    	    	    	    				noDataText:'暂无性别分布数据',
	    	    	    	    			    tooltip : {
	    	    	    	    			        trigger: 'axis',
	    	    	        				        formatter: function (params,ticket,callback) {
	    	    	        				        	var res = params[0].name+'学年';
	    	    	        				            var unit=['人','人'];
	    	    	        				            var count=0;
	    	    	        				            var value='';
	    	    	        				            for (var i = 0, l = params.length; i < l; i++) {
	    	    	        				                value += '<br/>' + params[i].seriesName + ' : ' + params[i].value+unit[i];
	    	    	        				           count=count+params[i].value;
	    	    	        				            }
	    	        	    				            res +=" 共"+count+"人"+value;
	    	    	        				        return res;
	    	    	        				        },
	    	    	    	    			        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	    	    	    	    			            type : 'shadow',         // 默认为直线，可选为：'line' | 'shadow'
	    	    	    	    			             lineStyle : {          // 直线指示器样式设置
	    	    	    	    			                 color: '#48b',
	    	    	    	    			                 width: 2,
	    	    	    	    			                 type: 'solid'
	    	    	    	    			             }}
	    	    	    	    			    },
	    	    	    	    			    legend: {
	    	    	    	    			    	x:'70', 
	    	    	    	    			        data:data_type,
	    	    	    	    			        y : 15
	    	    	    	    			    },
	    	    	    	    			    toolbox: {
	    	    	    	    			        show : true,
	    	    	    	    			        feature : {
	    	    	    	    			            dataView : {show: true, readOnly: false},
	    	    	    	    			            restore : {show: true},
	    	    	    	    			            saveAsImage : {show: true}
	    	    	    	    			        }
	    	    	    	    			    },
	    	    	    	    			    calculable : false,
	    	    	    	    			    xAxis : [
	    	    	    	    			        {
	    	    	    	    			        	   x : 10,
	    	    	    	    			        	name : '学年',
	    	    	    	    			            type : 'category',
	    	    	    	    			            boundaryGap : false,
	    	    	    	    			            data : data_name
	    	    	    	    			        }
	    	    	    	    			    ],
	    	    	    	    			    yAxis : [
	    	    	    	    			        {
	    	    	    	    			        	name : '人',
	    	    	    	    			            type : 'value'
	    	    	    	    			        }
	    	    	    	    			    ],
	    	    	    	    			    series :series_data
	    	    	    	    			};	
	    	    	    	    		callback(option);
	    	    	    	    	}
	    	    	    	    });
	    	        		},
	    	        		//高级查询组件
	    	        	    getAdvance : function(callback){
	    	        	    	http.post({
	    	        	    		url  : "advanced",
	    	        	    		data : { tag : "Xg_notGradDegree" },
	    	        	    		success : function(data){
	    	        	    			callback(data);
	    	        	    		}
	    	        	    	});
	    	        	    } ,
	    	        	    //得到下钻详细信息
	    	        	    getStuDetail : function(param, callback,error){
	    	                	http.post({
	    	                		url  : "pmsNotGradDegree/getStuDetail",
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
	    	                		url  : "pmsNotGradDegree/down",
	    	                		data : param,
	    	                		success : function(){
	    	                			alert("success");
	    	                		},
	    	                		error:error
	    	                	})
	    	                },

    };
}]);
