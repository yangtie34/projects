app.service("service",['httpService', 'constant', function(http, constant){
    return {
    	getBzdm : function(callback) {
			var data1 = constant.data1;
			var data2 = constant.data2;
			var option = {
				tooltip : {
					trigger : 'axis',
					showDelay : 0,
					formatter : function(params) {
						if (params.value.length > 1) {
							return params.seriesName + ' :<br/>'
									+ params.value[0] + ' 次 '
									+ params.value[1] + ' 分 ';
						} else {
							return params.seriesName + ' :<br/>'
									+ params.name + ' : '
									+ params.value + '分 ';
						}
					},
					axisPointer : {
						show : true,
						type : 'cross',
						lineStyle : {
							type : 'dashed',
							width : 1
						}
					}
				},
				legend : {
					data : [ '女生', '男生' ]
				},
				toolbox : {
					show : true,
					feature : {
						mark : {
							show : true
						},
						dataZoom : {
							show : true
						},
						dataView : {
							show : false,
							readOnly : true
						},
						restore : {
							show : true
						},
						saveAsImage : {
							show : true
						}
					}
				},
				xAxis : [ {
					type : 'value',
					scale : true,
					axisLabel : {
						formatter : '{value} 次'
					}
				} ],
				yAxis : [ {
					type : 'value',
					scale : true,
					axisLabel : {
						formatter : '{value} 分'
					}
				} ],
				series : [
				          {
		                        name:'女生',
		                        type:'scatter',
		                        data: data2,
		                        markPoint : {
		                            data : [
		                                {type : 'max', name: '最大值'},
		                                {type : 'min', name: '最小值'}
		                            ]
		                        },
		                        markLine : {
		                            data : [
		                                {type : 'average', name: '平均值'}
		                            ]
		                        }
		                    },  
		                    {
		                        name:'男生',
		                        type:'scatter',
		                        data: data1,
		                        markPoint : {
		                            data : [
		                                {type : 'max', name: '最大值'},
		                                {type : 'min', name: '最小值'}
		                            ]
		                        },
		                        markLine : {
		                            data : [
		                                {type : 'average', name: '平均值'}
		                            ]
		                        }
		                    }]
			};
			callback(option);
		}
    }
}]);