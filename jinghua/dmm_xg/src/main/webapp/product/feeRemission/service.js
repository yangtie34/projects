app.service("service",['httpService',function(http){
    return {
    	getSchoolYear:function(callback){
    		http.post({
        		url  : "feeRemission/querySchoolYear",
    	    	success : function(data){
    	    		callback(data[0]);
    	    	}
    		});	
    	},
    	queryDeptDataList : function(schoolYear, edu,fb, param, callback){
    		var me = this;
    		http.post({
    			url  : "feeRemission/queryJmfb",
        		data:{"schoolYear":schoolYear,
        			  "edu"  :edu,
        			  "fb"  :fb,
        			  "param" :param
        			 },
    			success : function(data){
    				callback(data.bzdm, data.list);
    			}
    		});
    	},
	    /**
	     * 获取列表中一个列的值并封装option
	     * @param list 数据列表
	     * @param value_column 列
	     * @param yname Y轴单位
	     * @param legend 图例
	     * @param config 自定义配置
	     */
    	getStackOptionByColumn : function(list, value_column, yname, legend, config){
    		var cfg = {
    			legend_ary : legend ? [legend] : [],
				yname_ary  : [yname], // 单位
				type_ary   : ['bar'], // 图标类型
				value_ary  : [value_column], // value所对应字段
				stack  : 'a'
			}
    		for(var key in config){
    			cfg[key] = config[key];
    		}
	    	return Ice.echartOption(list, cfg);
	    },
    	queryJmInfo :function(schoolYear,edu,param,callback){//得到学费减免的基本信息
    		http.post({
        		url  : "feeRemission/queryJmInfo",
        		data:{"schoolYear":schoolYear,
        			  "edu"  :edu,
        			  "param" :param
        			 },
    	    	success : function(data){
    	    		callback(data[0]);
    	    	}
    		});
    	},
	    queryJmfb : function(schoolYear,edu,fb,param,callback){//学费减免分布
        	http.post({
        		url  : "feeRemission/queryJmfb",
        		data:{"schoolYear":schoolYear,
        			  "edu"  :edu,
        			  "fb"  :fb,
        			  "param" :param
        			 },
    	    	success : function(data){
    	    		var formatter_data=	" {b}:{c}人";//鼠标悬停显示内容
    	    		var data_type=[];
    	    		var data_name=[];
    	    		var series_data=[{
    	    			 name:'',
			             type:'bar',
			             stack: '学费分布',
			             data:[]//COUNT_
    	    		}];
    	    		for(var i=0;i<data.lx.length;i++){//得到学费减免类型
    	    			if(data.lx[i].TNAME_!=null){
    	    			data_type[i]=data.lx[i].TNAME_;
    	    			}
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
    	    					 text:'学费减免分布',
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
    	    				        feature : {
    	    				            dataView : {show: true, readOnly: false},
    	    				            restore : {show: true},
    	    				            saveAsImage : {show: true}
    	    				        }
    	    				    },
    	    				    calculable : false,
    	    				    xAxis : [
    	    				        {
    	    				        	axisLabel: {rotate: -15,},//设置x轴倾斜
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
    	queryYearJmfb : function(bh,param,edu,callback){//历年学费分布变化
        	http.post({
        		url  : "feeRemission/queryYearJmfb",
        		data:{"bh":bh,
        			  "param" :param,
        			  "edu"   :edu
        		     },
    	    	success : function(data){
    	    		var data_type=[];
    	    		var data_year=[];
    	    		var series_data=[{
    	    			 name:'',
			             type:'bar',
			             stack: '学费减免',
			             data:[]//COUNT_
    	    		}];
    	    		for(var i=0;i<data.lx.length;i++){//得到学费减免类型
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
    	    				 title : {
    	    					 text:'历年学费减免变化',
    	    					show:false 
    	    				 },
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
 	    				    legend: {
	    				    	   x: 80,
	    				    	   y: 15,
 	    				        data:data_type
 	    				    },
 	    				 	config :{
		    					on :['CLICK','DEPTMC']
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
	    		data : { tag : "Xg_feeRemission" },
	    		success : function(data){
	    			callback(data);
	    		}
	    	})
	    }, 
	    //得到下钻详细信息
	    getStuDetail : function(param, callback,error){
        	http.post({
        		url  : "pmsFeeRemission/getStuDetail",
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
        		url  : "pmsFeeRemission/down",
        		data : param,
        		success : function(){
        			alert("success");
        		},
        		error:error
        	})
        },


    };
}]);