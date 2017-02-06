/**
 * 助学贷款
 */
app.service("service",['httpService',function(http){
    return {
    	getSchoolYear:function(callback){
    		http.post({
        		url  : "studentLoans/querySchoolYear",
    	    	success : function(data){
    	    		callback(data[0]);
    	    	}
    		});	
    	},
    	queryZxInfo :function(schoolYear,id,pid,callback){
    		http.post({
        		url  : "studentLoans/queryZxInfo",
        		data:{"schoolYear":schoolYear,
        			  "id"  :id,
        			  "pid":pid
        			 },
    	    	success : function(data){
    	    		var data1=[{'NAME_':'国家助学贷款','RS':0,'ZB':0,'AVG_':0,'FGL':0},
    	    		           {'NAME_':'生源地信用贷款','RS':0,'ZB':0,'AVG_':0,'FGL':0}];
    	    		if(data[0].fl.length==0){
    	    			data[0].fl=data1;
    	    		}
    	    		callback(data[0]);
    	    	}
    		});
    	},
    	queryZxxw :function(schoolYear,edu,param,callback){//助学学生行为
    		http.post({
        		url  : "studentLoans/queryZxxw",
        		data:{"schoolYear":schoolYear,
        			  "edu"  :edu,
        			  "param" :param
        			 },
    	    	success : function(data){
    				var legend_ary = [],
    				series_ary = [];
    			for(var i=0,len=data.list.length; i<len; i++){
    				var obj = data.list[i];
    				legend_ary.push(obj.name);
    				series_ary.push({
    					name  : obj.name,
    					value : [obj.score, obj.earlyCount, obj.breakfastCount, obj.bookRke, obj.bookCount]
    				});
    			}
    			// 雷达图
    			var option = {
    					 title : {
	    					 text:'助学贷款学生行为',
	    					show:false 
	    				 },
				    tooltip : {
				        trigger  : 'axis',
				      	position : function(ary){
				         	 return [ ary[0]+80, ary[1]]
				        }
				    },
				    legend: {
				        x : 'left',
				        y : 'top',
						padding : [5,40],
				        data   : legend_ary
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            dataView : {show: true, readOnly: false},
				            restore  : {show: true},
				            saveAsImage : {show: true}
				        }
				    },
				    calculable : true,
				    polar : [{
				            indicator : [
				                {text : '成绩', max  : data.score},
				                {text : '早起次数', max  : data.earlyCount},
				                {text : '早餐次数', max  : data.breakfastCount},
				                {text : '图书馆进出次数', max  : data.bookRke},
				                {text : '借阅量', max  : data.bookCount}
				            ],
				          	center : [ '50%', '60%'],
				            radius : 110,
				    }],
				    series : [{
			            type: 'radar',
			            itemStyle: {
			                normal: {
			                    areaStyle: {
			                        type: 'default'
			                    }
			                }
			            },
			            data : series_ary
			        }]
    			};
    			callback(data.list, option);
    	    	}
    		});
    	},
    	queryZxfb : function(schoolYear,edu,fb,param,callback){//学费减免分布
        	http.post({
        		url  : "studentLoans/queryZxfb",
        		data:{"schoolYear":schoolYear,
        			  "edu"  :edu,
        			  "fb"  :fb,
        			  "param" :param
        			  },
    	    	success : function(data){
    	    		var data_type=[];
    	    		var data_name=[];
    	    		var series_data=[{
    	    			 name:'',
			             type:'bar',
			             stack: '助学贷款',
			             data:[]//COUNT_
    	    		}];
    	    		for(var i=0;i<data.lx.length;i++){//得到助学贷款类型
    	    			data_type[i]=data.lx[i].TNAME_;
    	    			}
    	    		var j=0;
    	    		for(var i=0;i<data.list.length;i++){//得到学院
    	    			data_name[i]=data.list[i].name;
    	    		}
    	    		var data_count=[];
    	    		var m=0;
    	    		//得到series
    	    		for(var i=0;i<data_type.length;i++){
    	    			
    	    			for(var k=0;k<data_name.length;k++){
    	    				var flag=0;
    	    			  for(var j=0;j<data.list[k].list.length;j++){
//    	    				if(data.list[j].name==data_name[k]){
    	    					if(data.list[k].list[j].TNAME_==data_type[i]){
        	    					data_count[m++]=data.list[k].list[j].COUNT_;
        	    					flag=1;
        	    				}
//    	    				}
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
    	    		if(data.slx=='count'){
        	    		var formatter_data=	"人";
        	    		}else if(data.slx=='money'){
        	    			var formatter_data=	"元";
        	    		}else{
        	    			var formatter_data=	"%";
        	    		}
//    	    		}
    	    		option = {
    	    				 title : {
    	    					 text:'助学贷款分布',
    	    					show:false 
    	    				 },
 	    				   tooltip : {
 	    				        trigger: 'axis',
 	    				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
 	    				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
 	    				        },
	    				        formatter: function (params,ticket,callback) {
	    				            var res = params[0].name;
	    				            var count=0;
	    				            var value='';
	    				            for (var i = 0, l = params.length; i < l; i++) {
	    				            	value += '<br/>' + params[i].seriesName + ' : ' + params[i].value+formatter_data;
	    				                count=count+(parseInt(params[i].value));
	    				            }
	    				            res +=" 共"+count+formatter_data+value;
	    				        return res;
	    				        }
 	    				    },
 	    				 	config :{
		    					on :['CLICK','DEPTMC']
		    				},
 	    				    legend: {
	    				    	   x: 70,
	    				    	   y: 15,
 	    				        data:data_type
 	    				    },
 	    				    toolbox: {
 	    				        show : true,
// 	    				        orient: 'vertical',
// 	    				        x: 'right',
// 	    				        y: 'center',
 	    				        feature : {
// 	    				            mark : {show: true},
 	    				            dataView : {show: true, readOnly: false},
// 	    				            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
 	    				            restore : {show: true},
 	    				            saveAsImage : {show: true}
 	    				        }
 	    				    },
 	    				    calculable : false,
 	    				    xAxis : [
 	    				        {
 	    				        	axisLabel: {rotate: -15,},//设置x轴倾斜
// 	    				        	name : '学院',
 	    				            type : 'category',
 	    				            data : data_name
 	    				        }
 	    				    ],
 	    				    yAxis : [
 	    				        {
 	    				        	name : formatter_data,
 	    				            type : 'value'
 	    				        }
 	    				    ],
 	    				    series :series_data
 	    		};
    	    		callback(option);
    	    	}
        	});
	    },   	
	    queryYearZxfb : function(bh,param,callback){//历年学费分布变化
        	http.post({
        		url  : "studentLoans/queryYearZxfb",
        		data:{"bh":bh,
        			"param" :param
        		},
    	    	success : function(data){
    	    		var data_type=[];
    	    		var data_year=[];
    	    		var series_data=[{
    	    			 name:'',
			             type:'bar',
			             stack: '助学贷款',
			             data:[]//COUNT_
    	    		}];
    	    		for(var i=0;i<data.lx.length;i++){//得到助学贷款类型
    	    			data_type[i]=data.lx[i].TNAME_;
    	    			}
    	    		for(var i=0;i<data.year.length;i++){//得到时间
    	    					data_year[i]=data.year[i]+'-'+(data.year[i]+1);
    	    		};
    	    		var data_count=[];
    	    		var m=0;
    	    		//得到series
    	    		for(var i=0;i<data_type.length;i++){
    	    			
    	    			for(var k=0;k<data_year.length;k++){
    	    				var flag=0;
    	    			  for(var j=0;j<data.list.length;j++){
    	    				if(data.list[j].year==(data_year[k]).split('-')[0]){
    	    					for(var h=0;h<data.list[j].list.length;h++){
    	    					if(data.list[j].list[h].TNAME_==data_type[i]){
        	    					data_count[m++]=data.list[j].list[h].COUNT_;
        	    					flag=1;
        	    				}
    	    					}
    	    				}
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
    	    		if(data.slx=='count'){
        	    		var formatter_data=	"人";
        	    		}else if(data.slx=='money'){
        	    			var formatter_data=	"元";
        	    		}else{
        	    			var formatter_data=	"%";
        	    		}
    	    		option = {
 	    				   tooltip : {
 	    				        trigger: 'axis',
 	    				        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
 	    				            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
 	    				        },
	    				        formatter: function (params,ticket,callback) {
	    				            var res = params[0].name+'学年';
	    				            var count=0;
	    				            var value='';
	    				            for (var i = 0, l = params.length; i < l; i++) {
	    				            	value += '<br/>' + params[i].seriesName + ' : ' + params[i].value+formatter_data;
	    				                count=count+(params[i].value);
	    				            }
	    				            res +=" 共"+count+formatter_data+value;
	    				        return res;
	    				        }
 	    				    },
 	    				 	config :{
		    					on :['CLICK','DEPTMC']
		    				},
 	    				    legend: {
	    				    	   x: 80,
	    				    	   y: 15,
 	    				        data:data_type//['直接访问','邮件营销','联盟广告','视频广告']
 	    				    },
 	    				   title : {
  	    					 text:'历年助学贷款变化',
  	    					show:false 
  	    				 },
 	    				    toolbox: {
 	    				        show : true,
// 	    				        orient: 'vertical',
// 	    				        x: 'right',
// 	    				        y: 'center',
 	    				        feature : {
// 	    				            mark : {show: true},
 	    				            dataView : {show: true, readOnly: false},
// 	    				            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
 	    				            restore : {show: true},
 	    				            saveAsImage : {show: true}
 	    				        }
 	    				    },
 	    				    calculable : false,
 	    				    xAxis : [
 	    				        {
 	    				        	name : '学年',
 	    				            type : 'category',
 	    				            data : data_year
 	    				        }
 	    				    ],
 	    				    yAxis : [
 	    				        {
 	    				        	name : formatter_data,
 	    				            type : 'value'
 	    				        }
 	    				    ],
 	    				    series :series_data
 	    		};
    	    		callback(option);
    	    	}
        	});
	    },
	    getAdvance : function(callback){
	    	http.post({
	    		url  : "advanced",
	    		data : { tag : "Xg_studentLoans" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    } ,
	    //得到下钻详细信息
	    getStuDetail : function(param, callback,error){
        	http.post({
        		url  : "pmsStudentLoans/getStuDetail",
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
        		url  : "pmsStudentLoans/down",
        		data : param,
        		success : function(){
        			alert("success");
        		},
        		error:error
        	})
        },


    };
}]);