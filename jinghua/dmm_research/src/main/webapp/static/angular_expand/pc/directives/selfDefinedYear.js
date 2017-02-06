/**
 * 局部遮罩指令
 */
system.directive('selfDefinedYear',[function() {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/angular_expand/pc/directives/tpl/selfDefinedYear.html',
		scope: {
			source : "=",
			result : "="
		},
		link : function(scope, element, attrs) {
			var curYear = (new Date()).getFullYear();
			scope.result = {
				start : curYear,
				end : curYear
			};
			scope.$watch("source",function(val){
				if(val==undefined || val.length==0) return;
				else{
					scope.item= {
						result : scope.source[2]
					};
					
					// 设置开始日期下拉框的数据源
					scope.setStartDropDownSource = function(endYear,defaultVal){
						result = [];
						for (var i = endYear; i >= curYear- 15; i--) {
							var year = {
								id : i,
								mc : i +'年'
							}
							if(defaultVal ==i)year.checked=true;
							result.push(year)
						}
						scope.startDateSource = result;
					}
					
					
					
					// 设置结束日期下拉框的数据源
					scope.setEndDropDownSource = function(startYear,defaultVal){
						result = [];
						for (var i = curYear; i >= startYear; i--) {
							var year = {
								id : i,
								mc : i +'年'
							}
							if(defaultVal ==i)year.checked=true;
							result.push(year)
						}
						scope.endDateSource = result;
					}
					
					//开始时间变化
					scope.startYearChange = function(data){
						scope.result.start = data.id;
						scope.setEndDropDownSource(data.id,scope.result.end);
					}
					//结束时间变化
					scope.endYearChange = function(data){
						scope.result.end = data.id;
						scope.setStartDropDownSource(data.id,scope.result.start);
					}
					
					//文字点击
					scope.$watch("item",function(newval){
						scope.result.start = Number(newval.result.start);
						scope.result.end = Number(newval.result.end);
						scope.setStartDropDownSource(Number(newval.result.end),Number(newval.result.start));
						scope.setEndDropDownSource(Number(newval.result.start),Number(newval.result.end));
					},true);
					
					//监控结果
					scope.$watch("result",function(val){
						//如果结果和文字选中的时间不同，则将文字选中置空
						if(val.start != Number(scope.item.result.start) || val.end != Number(scope.item.result.end)){
							scope.item.result = angular.copy(scope.item.result)
						}
						//如果结果选中和文字列表中的有相同，将文字选中
						for ( var i in scope.source) {
							var sc = scope.source[i]
							if(val.start == Number(sc.start) && val.end == Number(sc.end)){
								scope.item.result = sc;
							}
						}
					},true);
				}
			});
			 
		}
	}
}]);
