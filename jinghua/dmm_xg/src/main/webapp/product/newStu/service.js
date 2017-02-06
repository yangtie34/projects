app.service("service", ['httpService', function(http) {
		return {
			getIsRegisterCount : function(param, callback) {
				return http.post({
								url : "newStu/getIsRegisterCount",
								data : {"param":param},
								success : function(data) {
									var distribute = {};
									distribute.isRegisterCfg = {
										name : '人',
										type : "pie",
										data : data.islist,
										config:{
											title:{
												text:'已报到新生就读学历分布',
												show:false
											},
											calculable : true,
											noDataText : '暂无已报到新生学历分布数据'
										}
									};
									distribute.notRegisterCfg = {
										name : '人',
										type : "pie",
										data : data.notlist,
										config:{
											title:{
												text:'未报到新生就读学历分布',
												show:false
											},	
											calculable : true,
											noDataText : '暂无未报到新生学历分布数据'
										}
									};
									distribute.yes = data.yes;
									distribute.no = data.no;
									distribute.isrete = data.isrete;
									distribute.notrete = data.notrete;
									distribute.ylist = data.ylist;
									distribute.nlist = data.nlist;
									distribute.year = data.year;
									callback(distribute);
								}
							});
						},
		    getStuDetail : function(param,callback,error){
							return http.post({
								url : "pmsNewStu/getStuDetail",
								data : param,
								success : function(data) {
									var page = {};
									page.total = data.total;
									page.items = data.rows;
									page.pagecount = data.pagecount;
									callback(page);
								},
								error:error
							});
						},
			getStuDetailDown : function(param, callback){
					        	http.fileDownload({
					        		url  : "pmsNewStu/down",
					        		data : param,
					        		success : function(){
					        			alert("success");
					        		}
					        	})
					        },			
			getCountAndLv : function(param, callback) {
				return http.post({
								url : "newStu/getCountAndLv",
								data : {"param":param},
								success : function(data) {
									var distribute = {};
									distribute.countAndLvCfg = Ice.echartOption(data.list,{
	    					            yname_ary  : ['人', '%'], // 单位
	    					            xname_ary  : ['年'], // 单位
	    					            legend_ary : ['报到人数', '报到率'], // 图例
	    					            type_ary   : ['bar','line'], // 图标类型
	    					            value_ary  : ['count','lv'], // value所对应字段
									    config:{
									    	title:{
												text:'历年新生人数与报到率',
												show:false
											},	
									    	noDataText:'暂无历年新生人数与报到率数据'
									    }
										});
										var y = distribute.countAndLvCfg.yAxis;
										y[1].min=80;
									callback(distribute);
								}
							});
						},
		   getDeptAvgLv : function(param, callback) {
				return http.post({
								url : "newStu/getDeptAvgLv",
								data : {"param":param},
								success : function(data) {
									var distribute = {};
									distribute.name =data.name;
									distribute.deptAvgLvCfg = {
										name:'%',
										data:data.list,
										type:'bar',
										config : {
											title:{
												text:'近五年各'+data.name+'新生平均报到率',
												show:false
											},	
											tooltip : {
												formatter : "{b} <br/> 报到率：{c}%"
											},
											xAxis : [{
				    							axisLabel : { rotate : -15 }
											}
				    						],
				    						noDataText:'暂无近五年各'+data.name+'新生平均报到率数据',
				    						yAxis:[{
				    					     scale:true,
				    						 min:50
				    						}]
										}
									}
									callback(distribute);
								}
							});
						},						
	        getPoorCount : function(param,year,yAxis,callback) {
				return http.post({
								url : "newStu/getPoorCount",
								data : {"param":param,
								        "year":year},
								success : function(data) {
									var distribute = {};
									var name,value;
									if (yAxis==null){
									 name =data.year;
									 value = data.value;
									}else{
									  name =year;
									  value =yAxis;
									}
									distribute.poorCountCfg = {
										name:'人',
										data:data.list,
										type:'line',
										config : {
											title:{
												text:'新生贫困生人数变化趋势',
												show:false
											},	
											tooltip : {
												formatter : "{b} <br/> 人数：{c}人"
											},
											noDataText:'暂无新生贫困生数据',
											xAxis:[{
												name:'年'
											}],
											series : [{
												markPoint:{
													clickable:false, 
													data:[
													{name:name+'年',
													 value:value,
													 xAxis:name,
													 yAxis:value,
													 symbol:'emptyCircle'
													  }
													],
													itemStyle:{
									                     normal:{
									                       label:{
									                         position:'top'
									                     }
									                 }
									                 }
												 }
											   }
											]
										}
									};
									distribute.poorPieCfg = {
										name:'人',
										data:data.list1,
										type:'pie',
										config : {
											title:{
												text:'新生贫困生户口分布',
												show:false
											},	
											tooltip : {
												formatter : "{b} <br/> 人数：{c}人"
											},
											legend:{
												show:false
											},
											noDataText:'暂无新生贫困生户口分布数据',
											series:[{
											    radius:'55%'
											}]
										}
									};
									distribute.year = data.year;
									distribute.value = data.value;	
									distribute.result = data.result;
									callback(distribute);
								}
							});
						},
			getLoanCount : function(param,year,yAxis,callback) {
				return http.post({
								url : "newStu/getLoanCount",
								data : {"param":param,
								        "year":year},
								success : function(data) {
									var distribute = {};
										var name,value;
									if (yAxis==null){
									 name =data.year;
									 value = data.value;
									}else{
									  name =year;
									  value =yAxis;
									}
									distribute.loanCountCfg = {
										name:'人',
										data:data.list,
										type:'line',
										config : {
											title:{
												text:'获助学贷款新生人数变化趋势',
												show:false
											},
											tooltip : {
												formatter : "{b} <br/> 人数：{c}人"
											},
											xAxis:[{
												name:'年'
											}],
											noDataText:'暂无新生助学贷款数据',
											series : [{
												markPoint:{
													clickable:false,
													data:[
													{name:name,
													 value:value,
													 xAxis:name,
													 yAxis:value,
													 symbol:'emptyCircle'}
													],
													itemStyle:{
									                     normal:{
									                       label:{
									                         position:'top'
									                     }
									                 }
									                 }
												 }
											   }
											]
										}
									};
									distribute.loanPieCfg = {
										name:'人',
										data:data.list1,
										type:'pie',
										config : {
											title:{
												text:'获助学贷款新生户口分布',
												show:false
											},
											tooltip : {
												formatter : "{b} <br/> 人数：{c}人"
											},
											noDataText:'暂无获助学贷款新生户口分布数据',
											legend:{
												show:false
											},
											series:[{
											    radius:'55%'
											}]
										}
									};
									distribute.year = data.year;
									distribute.value = data.value;
									distribute.result = data.result;
									callback(distribute);
								}
							});
						},
			getJmCount : function(param,year,yAxis,callback) {
				return http.post({
								url : "newStu/getJmCount",
								data : {"param":param,
								        "year":year},
								success : function(data) {
									var distribute = {};
										var name,value;
									if (yAxis==null){
									 name =data.year;
									 value = data.value;
									}else{
									  name =year;
									  value =yAxis;
									}
									distribute.jmCountCfg = {
										name:'人',
										data:data.list,
										type:'line',
										config : {
											title:{
												text:'获学费减免新生人数变化趋势',
												show:false
											},	
											tooltip : {
												formatter : "{b} <br/> 人数：{c}人"
											},
											xAxis:[{
												name:'年'
											}],
											noDataText:'暂无新生获学费减免数据',
											series : [{
												markPoint:{
													clickable:false,
													data:[
													{name:name,
													 value:value,
													 xAxis:name,
													 yAxis:value,
													 symbol:'emptyCircle'}
													],
													itemStyle:{
									                     normal:{
									                       label:{
									                         position:'top'
									                     }
									                 }
									                 }
												 }
											   }
											]
										}
									};
									distribute.jmPieCfg = {
										name:'人',
										data:data.list1,
										type:'pie',
										config : {
											title:{
												text:'获学费减免新生户口分布',
												show:false
											},	
											tooltip : {
												formatter : "{b} <br/> 人数：{c}人"
											},
											legend:{
												show:false
											},
											noDataText:'暂无获学费减免新生户口分布数据',
											series:[{
											    radius:'55%'
											}]
										}
									};
									distribute.year = data.year;
									distribute.value = data.value;
									distribute.result = data.result;
									callback(distribute);
								}
							});
						},
			getAdvance : function(callback) {
							http.post({
										url : "advanced",
										data : {
											tag : "Xg_newStu"
										},
										success : function(data) {
											callback(data);
										}
									})
						}
					}
				}]);