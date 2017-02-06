system.directive('cgSelect', ['$parse', '$timeout', function ($parse,$timeout) {
	var temp = '<div class="btn-group cg-select-dropdown">' +
				'<button type="button" class="btn btn-default ss-drop-input dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
	            	'<span class="input-area">{{display}}</span>' +
	            	'<span class="pull-right">' +
	                    '<span class="line"></span>' +
	                    '<span class="caret"></span>' +
	                    '<span class="sr-only">Toggle Dropdown</span>' +
	                '</span>' +
	            '</button>' +
	            '<ul class="dropdown-menu dropdown-menu-scrollbar">' +
	                '<li ng-repeat="item in list" ng-click="selectOption(item,$index)" ng-class="{active:value==item.id}"><a href="">{{item.mc}}</a></li>' +
	            '</ul>' +
           '</div>';
	return {
		restrict: 'A',
		scope: {
			list  : '=data',
			value : '=ngModel',
        	valueField   : '@valueField',
        	displayField : '@displayField',
        	onChange : '&'
		},
		template: temp,
		link: function (scope, element, attrs) {
			var displayField = scope.displayField || 'mc',
				valueField   = scope.valueField || 'id';
			scope.$parent.$watch(attrs.data, function(data){ // watch attr.data与list的区别
//			scope.$parent.$watch("list", function(data){
            	scope.list = data;
            	$timeout(function(){
            		scope.$apply();
            	});
            })
			scope.$watch("value", function(newVal, oldVal){
				if(newVal != oldVal){
					setValue(newVal, scope.list, scope);
				}
			})
			scope.selectOption = function(item, $index){
				var val = item[valueField];
				if(val == scope.value) return;
				scope.value   = val;
				scope.display = item[displayField];
				if(scope.onChange){
					scope.onChange({
						$value : item[valueField],
                		$data  : item
                	});
				}
			}
			var setValue = function(value, list, scope){
            	if(!value || value == null){
            		scope.display = null;
            		return;
            	}
            	for(var i=0,len = list.length;i<len;i++){
            		var item = list[i];
            		if(item[valueField] == value){
                        scope.value = item[valueField];
                        scope.display = item[displayField];
            			return;
            		}
            	}
            }
			setValue(scope.value, scope.list, scope); // 加载默认值
		}
	}
	
}]);
/*system.directive('select', ['$parse', '$timeout', function ($parse,$timeout) {
	var temp = '<div class="dropdown">' +
		  '<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aropup="true" aria-haspia-expanded="true">' +
		    '{{value}}' +
		    '<span class="caret"></span>' +
		  '</button>' +
		  '<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">' +
		    '<li ng-repeat="item in list" ng-click="selectOption(item,$index)"><a href="">{{item.mc}}</a></li>' +
		  '</ul>' +
		'</div>';
	return {
        restrict: 'A',
        scope: {
        	onSelected : '&',
        	list : '=data',
        	valueField : '&valueField',
        	displayField : '=displayField',
        	value : '=ngModel'
        },
        transclude: true,
        template: temp,
        link: function (scope, element, attrs) {
        	var valueField   = attrs.valueField,//实际值，赋予ng-model的值
            	displayField = attrs.displayField;//显示值字段
        	var listSize = null, selectIndex;
        	scope.$parent.$watch(attrs.data,function(data){
            	scope.list = data;
            	if(scope.list){
        			listSize = scope.list.length;
            		setValue(scope.value, scope.list, scope);
        		}
            	$timeout(function(){
            		scope.$apply();
            	});
            })
            scope.selectOption = function (item,$index) {
//            	selected = true;//标识是下拉选择值变换
//                scope.listShow = false;
//                scope.$parent.$item = item;
                //if(setter)setter(scope.$parent, item[valueField]);
//                input.val(item[displayField]);//
                scope.value   = item[valueField];
                scope.display = item[displayField];
                
//                delete scope.$parent.$item;
                
                scope.unselectLi(selectIndex);//移除之前选中的元素的样式
                selectIndex = $index;//赋予当前选中行的值
                scope.selectLi(selectIndex);//为当前选中行添加样式
                
                if(scope.onSelected)
                	scope.onSelected({
                		$data : 123
                	});
            }
        	scope.selectLi = function(index){
            }
            scope.unselectLi = function(index){
            }
            var setValue = function(value, list, scope){
            	if(!value || value == null){
            		scope.display = null;
            		return;
            	}
            	for(var i=0,len = list.length;i<len;i++){
            		var item = list[i];
            		if(item[valueField] == value){
            			selected = true;//标识是下拉选择值变换
                        scope.value   = item[valueField];
                        scope.display = item[displayField];
                        scope.unselectLi(selectIndex);//移除之前选中的元素的样式
                        selectIndex = i;//赋予当前选中行的值
                        scope.selectLi(selectIndex);//为当前选中行添加样式
            			return;
            		}
            	}
            };
        }
        
	}
}]);*/