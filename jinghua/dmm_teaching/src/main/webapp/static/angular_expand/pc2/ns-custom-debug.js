system.config(function($provide){
    $provide.decorator('ngTranscludeDirective', ['$delegate', function($delegate) {
        // Remove the original directive
        $delegate.shift();
        return $delegate;
    }]);
}).directive( 'ngTransclude', function() {
        return {
            restrict: 'EAC',
            link: function( $scope, $element, $attrs, controller, $transclude ) {
                if (!$transclude) {
                    throw minErr('ngTransclude')('orphan',
                            'Illegal use of ngTransclude directive in the template! ' +
                            'No parent directive that requires a transclusion found. ' +
                            'Element: {0}',
                        startingTag($element));
                }

                var iScopeType = $attrs['ngTransclude'] || 'sibling';

                switch ( iScopeType ) {
                    case 'sibling':
                        $transclude( function( clone ) {
                            $element.empty();
                            $element.append( clone );
                        });
                        break;
                    case 'parent':
                        $transclude( $scope, function( clone ) {
                            $element.empty();
                            $element.append( clone );
                        });
                        break;
                    case 'child':
                        var iChildScope = $scope.$new();
                        $transclude( iChildScope, function( clone ) {
                            $element.empty();
                            $element.append( clone );
                            $element.on( '$destroy', function() {
                                iChildScope.$destroy();
                            });
                        });
                        break;
                }
            }
        }
    })


system.directive('csSelect', ['$parse','$timeout', function ($parse,$timeout) {
	var temp =
	    '<div  class="smart-input-block-position" ng-keyup="moveTo($event)">' +
	    ' <div class="smart-input-block" ng-show="listShow">' +
	    '  <ul>' +
	    '      <li  ng-repeat="item in list"  ng-click="selectOption(item,$index)" ng-transclude="child">' +
	    '      </li>' +
	    '  </ul>' +
	    '</div>' +
	    '      <input class="smart-input cs-select-select" ng-model="display" ng-readonly="true" ng-click="stopPropagation($event)"  ng-focus="focus()" type="text" placeholder="{{placeholder}}" />' +
	    '</div>';
    var liClass = "smart-input-block-li";//向下，向上按钮移动到该li的位置的时候，li显示的样式
    /**
     * 对当前移动到的索引进行上移
     */
    var up = function(currentMoveIndex,listSize){
    	if(currentMoveIndex <= 0 ){
    		return listSize-1;
    	}else{
    	   return currentMoveIndex-1<0?listSize-1:currentMoveIndex-1;
    	}
    }
    /**
     * 对当前移动到的索引进行下移
     */
    var down = function(currentMoveIndex,listSize){
    	if(currentMoveIndex<0 ||currentMoveIndex == listSize-1 ){
    		return 0;
    	}else {
    	   return currentMoveIndex+1 == listSize?0:currentMoveIndex+1;
    	}
    }
    return {
        restrict: 'A',
        scope: {
        	onSelected : '&onSelected',
        	//list : '=data',
        	value : '=ngModel',
        	disabled : '=disabled'
        },
        transclude: true,
        template: temp,
        link: function (scope, element, attrs) {
            var placeholder = attrs.placeholder,
                service = attrs.service,//服务
                valueField = attrs.valueField,//实际值，赋予ng-model的值
                displayField = attrs.displayField,//显示值字段
//                getter = $parse(attrs.ngModel),//getter
//                setter = getter.assign;
                dataGetter = $parse(attrs.data);
            scope.$parent.$watch(attrs.data,function(data){
            	scope.list = data;
            	$timeout(function(){
            		scope.$apply();
            	});
            })
            
            
            scope.list = dataGetter(scope.$parent);
            
            var selectIndex,//选中的索引
	        	currentMoveIndex = -1,//当前移动到的索引
	        	listSize = 0,//当前列表长度
	        	init = true,//是否第一次初始化
	        	selected = false;//选择下拉标志位
            
            scope.placeholder = placeholder;
            var input = element.find('input').first();
            
            
            var showList = function(){//如果列表没有显示,显示列表，返回false。否则返回true
            	if(!scope.listShow){
            		scope.listShow = true;
            		$timeout(function(){
            			scope.$apply();
            		})
            		return false;
            	}else{
            		return true;
            	}
            }
            var initData = function(){
            	if(service){
            		var req = {service : service,params : []};
            		call(req, function (data) {
//            			var value = getter(scope.$parent);//获取初始化值
            			var value = scope.value;
	                    scope.list = data;
	                    listSize = data.length;
	                    setValue(value,data,scope);
	                });
            	}else if(attrs.data){
//            		var value = getter(scope.$parent);//获取初始化值
            		var value = scope.value;
            		if(scope.list){
            			listSize = scope.list.length;
                		setValue(value,scope.list,scope);
            		}
            	}
            	scope.$watch('value',function(value){
            		if(selected == true){
            		   selected = false;
//            		   return;
            		}
            		if(scope.list){
            			setValue(value,scope.list,scope);
            		}
                });
            	scope.$watch('list',function(){
            		if(scope.list){
            			listSize = scope.list.length;
                		setValue(scope.value,scope.list,scope);
            		}
                });
            }
            var setValue = function(value,list,scope){
            	if(!value || value == null){
            		scope.display = null;
            		return;
            	}
            	for(var i=0,len = list.length;i<len;i++){
            		var item = list[i];
            		if(item[valueField] == value){
            			selected = true;//标识是下拉选择值变换
                        scope.listShow = false;
                        
                        scope.value = item[valueField];
                        scope.display = item[displayField];
                        
                        scope.unselectLi(selectIndex);//移除之前选中的元素的样式
                        selectIndex = i;//赋予当前选中行的值
                        scope.selectLi(selectIndex);//为当前选中行添加样式
            			return;
            		}
            	}
            }
            /**
             * 当下拉列表中的项目被点击的时候，触发该事件
             */
            scope.selectOption = function (item,$index) {
            	selected = true;//标识是下拉选择值变换
                scope.listShow = false;
                scope.$parent.$item = item;
                //if(setter)setter(scope.$parent, item[valueField]);
//                input.val(item[displayField]);//
                scope.value = item[valueField];
                scope.display = item[displayField];
                if(scope.onSelected)scope.onSelected();
                delete scope.$parent.$item;
                
                scope.unselectLi(selectIndex);//移除之前选中的元素的样式
                selectIndex = $index;//赋予当前选中行的值
                scope.selectLi(selectIndex);//为当前选中行添加样式
            }
            /**
             * 当input获得焦点的时候，显示列表，并为document添加只能触发一次的消失列表事件
             */
            scope.focus = function(){
            	if(scope.disabled)return;
            	scope.listShow = true;
            	scope.unselectLi(currentMoveIndex);
            	scope.selectLi(selectIndex);
            	if(selectIndex != undefined){//当前移动开始行为选中行
            		currentMoveIndex = selectIndex;
            	}
            	$(document).on('click',function(){
            		scope.listShow = false;
            		$timeout(function(){
            			scope.$apply();
            		})
            	});
            }
            /**
             * input被点击的时候，阻止冒泡事件
             */
            scope.stopPropagation = function($event){
            	$event.stopPropagation();
            }
            scope.moveTo = function($event){
            	var keycode = $event.keyCode;
            	if(keycode == 38){//向上移动
            	   if(!showList())return;//如果列表没有显示，显示列表
            	   scope.unselectLi(currentMoveIndex);//反选下一行
            	   currentMoveIndex = up(currentMoveIndex,listSize);//坐标上移
            	   scope.selectLi(currentMoveIndex);//选中当前移动的行
            	   scope.selectLi(selectIndex);
            	}else if(keycode == 40){//向下移动
            		if(!showList())return;//如果列表没有显示，显示列表
            	   scope.unselectLi(currentMoveIndex);
            	   currentMoveIndex = down(currentMoveIndex,listSize);
             	   scope.selectLi(currentMoveIndex);
             	   scope.selectLi(selectIndex);
            	}else if(keycode == 13){
            	   if(currentMoveIndex>=0){
            		  scope.selectOption(scope.list[currentMoveIndex],currentMoveIndex);
            	   }
            	}
            }
            scope.selectLi = function(index){
            	if(listSize != undefined && listSize>0){
            		if(index!=undefined && index>=0 && index <= listSize-1)
                  	   element.find('li').eq(index).addClass(liClass);//老元素移除class类
            	}
            }
            scope.unselectLi = function(index){
            	if(listSize!=undefined && listSize>0){
	            	if(index!=undefined && index>=0 && index <= listSize-1)
	            	   element.find('li').eq(index).removeClass(liClass);//老元素移除class类
            	}
            }
            initData();//初始化数据
        }
    };
}])