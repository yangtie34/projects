/*************************************************
 下拉框指令 (此指令依赖于bootstrap)
 功能介绍 ： 
	  配置数据显示数据下拉框	
 例子见 ：static/angular_expand/example/dropdown.jsp
 ************************************************/
system.directive('btnDropdown', [function () {
    return {
        restrict: 'AE',
        templateUrl : base + '/static/angular_expand/pc/directives/tpl/btnDropdown.html',
        scope: {
            source: "=",
            onChange:"&",
            displayField:"@",
            btnClass : "@",
            btnStyle : "@"
        },
        link : function(scope, element, attrs) {
        	scope.showDetail = false;
        	
        	scope.toggleDetail = function(){
        		scope.showDetail = !scope.showDetail;
        		if(scope.showDetail == true){
        			var foo = function(event){
                        if ($(element).has(event.target).length < 1) {
                            scope.$apply(function() {
                            	scope.showDetail = false;
                            });
                           $("html").unbind("click",foo);
                        }
                    };
                    $("html").unbind("click",foo).bind('click',foo);
        		}
        	}
        	
        	if(scope.btnClass == '' || scope.btnSuccess == null){
        		scope.btnClass = "btn-default"
        	}
        	
        	scope.selectChange = function(data){
        		scope.showDetail = false;
        		scope.result = data;
        		for ( var i in scope.source) {
					var it = scope.source[i]
					it.checked = false;
				}
        		data.checked = true;
        		
        	}
        	
        	scope.$watch("result",function(newval,oldval){
        		if(newval){
        			scope.onChange({
            			$data : newval
            		});
        		}
        	},true);
        	
        	scope.defaultChecked = function(){
        		for (var i = 0; i < scope.source.length; i++) {
        			var item = scope.source[i];
					if(item.checked){
						scope.selectChange(item);
						break;
					}
				}
        	}
        	scope.$watch("source",function(newval,oldval){
        		if(newval && newval.length > 0){
        			scope.defaultChecked();
        		}
        	},true);
        }
    };
}]);