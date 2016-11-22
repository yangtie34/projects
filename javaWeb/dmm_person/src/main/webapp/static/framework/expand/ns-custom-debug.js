
/**
 * 构建directives主模块
 * @type {module|*}
 */
var directives = angular.module('directives',[]);

/**
 * 针对对象的操作
 * @param path
 * @param scope
 * @param val
 */
putObject = function(path,scope,val){
    
}
getObject = function(path,scope){
    
}
/**
 * 构建filters主模块
 * @type {module|*}
 */
var filters = angular.module('filters',[]);/**
 * 构建Services主模块
 * @type {module|*}
 */
var services = angular.module('services',[]);


/**
 * 自定义命名空间
 */
var custom = custom || {};
/**
 * 日期指令
 * <input type="text" maxValue="endDate" ng-model="startDate" datepicker/>
 * <input type="text" minValue="startDate" ng-model="endDate" datepicker/>
 */
directives.directive('csDatepicker',['$parse','$timeout',function($parse,$timeout){
    return {
        restrict : 'A',
        scope : {
            maxValue : '=maxvalue',
            minValue : '=minvalue',
            onChange : '&onChange'
        },
        link : function(scope, element, attrs){
            var onChange = scope.onChange,
                maxValue = scope.maxValue,
                minValue = scope.minValue,
                clickId = attrs.clickId,
                getter = $parse(attrs.ngModel),
                setter = getter.assign;
            var fn = function(dp){
                setter(scope.$parent,dp.cal.getDateStr());
                if(onChange){
                   onChange();
                }
                $timeout(function(){
                	scope.$apply(function(){});
                });
            };
            var config = {
                onpicked:fn,dateFmt:'yyyy-MM-dd',readOnly:true,
                doubleCalendar : true,autoUpdateOnChanged : true,
                oncleared : fn
            };
            if(maxValue)config.maxDate = maxValue;
            if(minValue)config.minDate = minValue;
            element.on('focus',function(){
            	if(scope.maxValue)config.maxDate = scope.maxValue;
                if(scope.minValue)config.minDate = scope.minValue;
                WdatePicker(config);
            });
            if(clickId)
                $("#"+clickId).on('click',function(){
                    if(scope.maxValue)config.maxDate = scope.maxValue;
                    if(scope.minValue)config.minDate = scope.minValue;
                    element.focus();
                });
        }
    }
}]);
/**
 * 日期指令
 *  <div cs-window show="flag" autoCenter="true" offset="offset">窗体</div>
 */
directives.directive('csWindow',['$timeout','$compile',function($timeout,$compile){
	//创建遮罩
	var body = $("body");//获取body元素
    var mask = $('<div class="cs-window-background"></div>');
    mask.hide();
    
    body.append(mask);
    var offset = function(el,autoCenter){
        var body = $(window),
            w = el.width(),
            h = el.height(),
            bw = body.outerWidth(true),
            bh = body.outerHeight(true),
            top = (bh-h)/2-40,
            left = (bw-w)/2;
        if(top<0)top=0;
        if(autoCenter == "true"){
              el.css("left",left + "px");
              el.css("top",top + "px");
        }
    }
    var offset1 = function(el,offset){
    	el.offset(offset);
    }
    return {
        restrict : 'A',
        scope : {
            show : '=',
            offset : '=offset',
            clickDisappear : '=clickDisappear',
            title:'='
        },
        link : function(scope, element, attrs){
            var autoCenter = attrs.autocenter || "true",
                m = attrs.mask || "true",
                disappear = attrs.clickDisappear || "true";
            var windowClickEvent = false;
            var ew=0;
            var eh=0;
            scope.$watch('title',function(newV,oldV){
            	scope.title=scope.title||"&nbsp";
            	 var headerHtml=$("<div class='modal-header'>"+
                 "<button type='button' class='close' data-dismiss='modal'><span class='aclose' >×</span></button>"+
                 "<h4 style='margin:0px;line-height:1;'>"+ scope.title+"</h4>"+
                 "</div>");
            	 element.find(".modal-header").remove();
            	 var newElem = $compile(headerHtml)(scope);
                 element.prepend(headerHtml);
                 element.find(".modal-header").next().addClass("content");
            },true);
           
            scope.$watch('show',function(newV,oldV){
                if(newV == true){
                    element.show();
                    if(m === "true")mask.show();
                    if(scope.clickDisappear){
                    	$timeout(function(){
                        	$(document).one('click',function(event){
                        		scope.show = false;
                        		$timeout(function(){
                        			scope.$apply();
                        		});
                        	});
                    	});
                    }
                    
                    $(".modal-header button.close").on('click',function(){
                     	scope.show = false;
                     	$timeout(function(){
                			scope.$apply();
                		});
                     }); 
                }else{//如果值不是通过document click事件进行改变的，需要首先对document的click事件解除绑定
                	$(document).unbind('click');
                    element.hide();
                    mask.hide();
                }
                scope.reoff();
            },true);
            if(scope.clickDisappear){
            	if(!windowClickEvent){//如果值false，则表明未初始化click事件，注册click事件，阻止冒泡
            		element.on('click',function(event){
            			event.stopPropagation();
            		});
            		windowClickEvent = true;
            	}
            }
            scope.$watch('offset',function(newV,oldV){
                offset1(element,newV);//手动移动到指定位置
            });
            //判断是否显示window窗口
            if(scope.show) element.show(); else element.hide();
            //添加window样式
            element.addClass('cs-window-windows');
            //设置元素居中
            scope.getoffset=function(){
            	ew=($(window).width()-element.width())/2;
           	 	eh=$(document).scrollTop()+200;
            };
           scope.reoff=function(){
        	   scope.getoffset();
        	   if(!scope.offset){
               	//offset(element,autoCenter);
               	element.offset({top:eh,left:ew});
               }else offset1(element,scope.offset);//手动移动到指定位置	   
           }
            $(window).resize(function () {  
            	scope.reoff();
            });
        }
    }
}]);/**
 *  自动光标定位
 *  <input auto-focus/>
 */
directives.directive('autoFocus',['$parse','$timeout',function($parse,$timeout){
    return {
        restrict : 'A',
        link : function(scope, element, attrs){
            var autoFocus = attrs.autoFocus,
                getter = $parse(autoFocus),
                focusV = getter(scope);
            if(focusV){
                element[0].focus();
            }
            if(autoFocus != "true" && autoFocus != "false"){
            	scope.$watch(autoFocus,function(value){
                    if(value == true){
                        element[0].focus();
                    }
                });
                element.on('blur',function(){
                	$timeout(function(){
                		scope.$apply(getter.assign(scope, false));
                	});
                });
            }
        }
    };
}]);/**
 * 动态输入框
 */
directives.directive('csSmartinput', ['$parse','$timeout', 'http', function ($parse,$timeout,http) {
	var temp =
	    '<div  class="smart-input-block-position" ng-keyup="moveTo($event)">' +
	    ' <div class="smart-input-block" ng-show="listShow">' +
	    '  <ul>' +
	    '      <li  ng-repeat="item in list"  ng-click="selectOption(item,$index)" ng-transclude="child">' +
	    '      </li>' +
	    '  </ul>' +
	    '</div>' +
	    '      <input class="smart-input" ng-click="stopPropagation($event)"  ng-focus="focus()" ng-model="queryParam"  ng-change="doAjax(queryParam)" type="text" placeholder="{{placeholder}}" />' +
	    '</div>';
    var call = function (request, callback) {
        http.callService(request).success(function(data){
            callback(data);
        });
    }
    var liClass = "smart-input-block-li";//向下，向上按钮移动到该li的位置的时候，li显示的样式
    var selectIndex,//选中的索引
    	currentMoveIndex = -1,//当前移动到的索引
    	listSize = 0,//当前列表长度
    	init = true;//是否第一次初始化
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
    //创建延迟函数
    var createBuffer =  function(fn, buffer, scope, args) {
        var timerId;
        return function() {
            var callArgs = args || Array.prototype.slice.call(arguments, 0),
                me = scope || this;
            if (timerId) {
                clearTimeout(timerId);
            }
            timerId = setTimeout(function(){
                fn.apply(me, callArgs);
            }, buffer);
        };
    };
    return {
        restrict: 'A',
        scope: {
        	onSelected : '&onSelected'
        },
        transclude: true,
        template: temp,
        link: function (scope, element, attrs) {
            var label = attrs.label,//input的标签
            	placeholder = attrs.placeholder,
                service = attrs.service,//服务
                params = attrs.params,//参数
                valueField = attrs.valueField,//实际值，赋予ng-model的值
                displayField = attrs.displayField,//显示值字段
                setter = $parse(attrs.ngModel).assign,
                defaultQuery = attrs.defaultQuery;//默认查询
            scope.label = label;
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
            /**
             * 当下拉列表中的项目被点击的时候，触发该事件
             */
            scope.selectOption = function (item,$index) {
                scope.listShow = false;
                scope.$parent.$item = item;
                setter(scope.$parent, item[valueField]);
                input.val(item[displayField]);
                if(scope.onSelected)scope.onSelected();
                delete scope.$parent.$item;
                
                scope.unselectLi(selectIndex);//移除之前选中的元素的样式
                selectIndex = $index;//赋予当前选中行的值
                scope.selectLi(selectIndex);//为当前选中行添加样式
            }
            /**
             * input的值改变的时候触发-ajax调用事件
             */
            scope.doAjax = createBuffer(function (queryParam) {
	                var param = [queryParam || null],
	                    req = {service : service,params : param};
	                selectIndex = undefined;
                    currentMoveIndex = -1;
                    scope.list = [];
	                showList();
	                setter(scope.$parent, undefined);
	                call(req, function (data) {
	                    scope.list = data;
	                    listSize = data.length;
	                });
               },400);
            /**
             * 当input获得焦点的时候，显示列表，并为document添加只能触发一次的消失列表事件
             */
            scope.focus = function(){
            	if(init){
            		init = false;
            	}else{
            		scope.listShow = true;
            	}
            	scope.unselectLi(currentMoveIndex);
            	scope.selectLi(selectIndex);
            	if(selectIndex != undefined){//当前移动开始行为选中行
            		currentMoveIndex = selectIndex;
            	}
            	$(document).one('click',function(){
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
        }
    };
}]);/**
 * 动态输入框
 */
directives.directive('csSelect', ['$parse','$timeout', 'http', function ($parse,$timeout,http) {
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
    var call = function (request, callback) {
        http.callService(request).success(function(data){
            callback(data);
        });
    }
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
        	list : '=data',
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
                getter = $parse(attrs.ngModel),//getter
                setter = getter.assign;
            
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
            			var value = getter(scope.$parent);//获取初始化值
	                    scope.list = data;
	                    listSize = data.length;
	                    setValue(value,data,scope);
	                });
            	}else if(attrs.data){
            		var value = getter(scope.$parent);//获取初始化值
            		if(scope.list){
            			listSize = scope.list.length;
                		setValue(value,scope.list,scope);
            		}
            	}
            	scope.$watch('value',function(value){
            		if(selected == true){
            		   selected = false;
            		   return;
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
            	if(!value)scope.display = null;
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
            	$(document).one('click',function(){
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
}]);/**
 * 遮罩指令,只遮住指定html片段
 *  <div cs-editor height="900" style="width:800px;" >窗体</div>
 */
directives.directive('csEditor',['$parse', function ($parse) {
    function addScript(url, callback) {
        var elt = document.createElement("script");
        elt.src = url;
        elt.anysc = true;
        if (elt.onload == null) {
            elt.onload = function () {
                typeof callback == 'function' && callback();
            }
        } else {
            elt.onreadystatechange = function () {
                if (elt.readyState == "loaded" || elt.readyState == "complete") {
                    typeof callback == 'function' && callback();
                }
            }
        }
        document.getElementsByTagName("body")[0].appendChild(elt);
    }

    return {
        restrict: 'A',
        require: 'ngModel',
        scope: {
            height: '@?',
            width : '@?'
        },
        link: function (scope, element, attrs,ctrl) {
            var _self = this,
                _initContent,
                editor,
                editorReady = false,
                baseURL = httpConfig.basePath+"/framework/components/ueditor/"; //写你的ue路径

            var fexUE = {
                initEditor: function () {
                    var _self = this;
                    if (typeof UE != 'undefined') {
                        editor = new UE.ui.Editor({
                            initialContent: _initContent,
                            toolbars : [
                                ['customstyle', 'paragraph', 'fontfamily', 'fontsize', '|', 'indent', '|',
                                    'justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'touppercase', 'tolowercase', '|',
                                    'horizontal', 'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|','fullscreen'],
                                ['removeformat', 'formatmatch','autotypeset','bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 'subscript',
                                    'forecolor', 'backcolor',
                                    'rowspacingtop', 'rowspacingbottom', 'lineheight', '|',
                                     'insertorderedlist', 'insertunorderedlist', '|', 'blockquote', 'pasteplain','selectall',
                                    'cleardoc', '|'],
                                ['inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'deleterow',
                                 'insertcol', 'deletecol', 'mergecells', 'mergeright', 'mergedown', 'splittocells', 'splittorows', 'splittocols', 'charts','|',
                                  'link', 'unlink', 'anchor', '|','simpleupload', 'insertimage', 'imagenone', 'imageleft', 'imageright', 'imagecenter', '|',
                                    'emotion', 'music', 'attachment', 'map', 'template', 'background', '|',
                                 ],
                                [ 'source', '|','print', 'preview', 'searchreplace','|','undo', 'redo', 'help']
                            ],
                            initialFrameHeight:scope.height || 120,
                            autoHeightEnabled:false,
                            wordCount:false,
                            elementPathEnabled: false
                        });


                        editor.render(element[0]);
                        editor.ready(function () {
                            editorReady = true;
                            _self.setContent(_initContent);

                            editor.addListener('contentChange', function () {
                                scope.$apply(function () {
                                    ctrl.$setViewValue(editor.getContent());
                                });
                            });
                        });
                    } else {

                        addScript(baseURL + 'ueditor.config.js');
                        addScript(baseURL + 'ueditor.all.min.js', function(){
                            _self.initEditor();
                        })
                    }
                },
                setContent: function (content) {
                    if (editor && editorReady) {
                        editor.setContent(content);
                    }
                }
            };

            /**
             * 当Model改变值得时候赋值。
             */
            ctrl.$render = function () {
                _initContent = ctrl.$isEmpty(ctrl.$viewValue) ? '' : ctrl.$viewValue;
                fexUE.setContent(_initContent);
            };

            fexUE.initEditor();
        }
    };
}]);/**
 * 遮罩指令,只遮住指定html片段
 *  <div cs-mask show="flag">窗体</div>
 */
directives.directive('csMask',['$parse',function($parse){
    var cls = "cs-window-background",//元素遮罩的显示
        zIndex = 2999,//遮罩的zindex
        body = $("body"),//获取body元素
        getMask = function(){
	        var mask = $('<div class="cs-mask-background"><div class="cs-mask-loading"><div class="cs-mask-img"></div><div></div>');
	        mask.hide();
	        body.append(mask);
	        return mask;
	    }
    return {
        restrict : 'A',
        scope : {
          csMask : '='
        },
        link : function(scope, element, attrs){
            var mask = getMask();//每次生成一个mask
            /**
             *
             * @param element
             */
            var show = function(){
                mask.height(element.outerHeight());
                mask.width(element.outerWidth());
                mask.offset(element.offset());
                mask.show();
            }
            var hide = function(){
                mask.offset({top : 0, left : 0});
                mask.hide();
            }
            if(scope.csMask){
                show();
            }
            scope.$watch('csMask',function(newValue,oldValue){
                if(newValue == true){
                    show();
                }else{
                    hide();
                }
            });
        }
    };
}]);/**
 * 值填充指令
 */
directives.directive('valueFillService',['http','$compile',function(http,$compile){
    function bindScope(scope,data){
        if(angular.isArray(data)){
            bindArray(scope,data);
        }else if(!angular.isArray(data) && angular.isObject(data)){
            bindMap(scope,data);
        }else{
            bindBasic(scope,data);
        }
    }
    function bindArray(scope,array){
        scope.list = array;
        for(var i= 0,len = array.length;i<len;i++){
            scope[i]=array[i];
        }
    }
    function bindMap(scope,map){
    	scope.map = map;
        for(var i in map){
            scope[i] = map[i];
        }
    }
    function bindBasic(scope,basic){
        scope['0'] = basic;
    }
    return {
        restrict: 'A',
        scope : {
            service : "@",
            params : "=",
            cls : "@class",
            style : "@style"
        },
        transclude: true,
        replace : true,
        template: "<span ng-transclude='child' class={{cls}} style='{{style}}'></span>",
        link : function($scope,element,attrs){
        	  var service = $scope.service,
                params = $scope.params,
                req = {service : service,params : params};
            http.callService(req).success(function(data){
                bindScope($scope,data);
                scope.$apply(function(){});
            });
        }
    };
}]);
/**
 *模型操作Service,操作$scope对象中的模型
 */
angular.module('services').factory('model',function(){
    return {
        /**
         * 将$scope中的VM对象取出来
         */
        getObject : function(scope,path){
            if(!path)return undefined;
            var split = path.split(".");
            var p = scope;
            for(var i=0;i<split.length;i++){
                var pName = split[i];
                if(i == split.length -1){
                    return p[pName];
                }else{
                    if(!p[pName])
                        return null;
                }
                p = p[pName];
            }
            return p;
        },
        /**
         * 设置$scope中的VM对象
         */
        putObject : function(scope,path,value){
            var split = path.split(".");
            var p = scope;
            for(var i=0;i<split.length;i++){
                var pName = split[i];
                if(i == split.length -1){
                    p[pName] = value;
                }else{
                    if(!p[pName])
                        p[pName] = {};
                }
                p = p[pName];
            }
            np = value;
        }
    }
});/**
 * 对话框服务
 * 因为对话框是以window的形式存在，故没有必要以HTML指令的形式存在于html模版中，因此
 *
 * */
angular.module('services').factory('dialog',['$compile','$rootScope',function($compile,$rootScope){
    //创建遮罩
	var body = $("body");//获取body元素
    var mask = $('<div class="cs-window-background"></div>');
    mask.hide();
    body.append(mask);
    //居中方法
    var offset = function(el){
        var body = $(window),
            w = el.width(),
            h = el.height(),
            bw = body.innerWidth(),
            bh = body.innerHeight(),
            top = (bh-h)/2-40,
            left = (bw-w)/2;
        el.css("left",left + "px");
        el.css("top",top + "px");
    }

    var errorHtml = '<div class="dialog-windows-pt-body">'+
                        '<div class="dialog-windows-pt-close dialog-windows-pt-close-red" ng-click="$dialog_error_close()"></div>'+
                        '<div class="dialog-windows-pt-img dialog-windows-pt-img-red">{replacement}</div>'+
                        '<div class="dialog-windows-pt-button dialog-windows-pt-button-red" ng-click="$dialog_error_close()">关闭</div>'+
                    '</div>';
    var warnHtml = '<div class="dialog-windows-pt-body">'+
                        '<div class="dialog-windows-pt-close dialog-windows-pt-close-orange" ng-click="$dialog_warn_close()"></div>'+
                        '<div class="dialog-windows-pt-img dialog-windows-pt-img-orange">{replacement}</div>'+
                        '<div class="dialog-windows-pt-button dialog-windows-pt-button-orange" ng-click="$dialog_warn_close()">关闭</div>'+
                    '</div>';
    var infoHtml = '<div class="dialog-windows-pt-body">'+
                        '<div class="dialog-windows-pt-close dialog-windows-pt-close-green" ng-click="$dialog_info_close()"></div>'+
                        '<div class="dialog-windows-pt-img dialog-windows-pt-img-green">{replacement}</div>'+
                        '<div class="dialog-windows-pt-button dialog-windows-pt-button-green" ng-click="$dialog_info_close()">好的</div>'+
                    '</div>';
    var confirmHtml = '<div class="dialog-windows-pt-body">'+
                            '<div class="dialog-windows-pt-close dialog-windows-pt-close-blue"  ng-click="$dialog_confirm_close()"></div>'+
                            '<div class="dialog-windows-pt-img dialog-windows-pt-img-blue">{replacement}</div>'+
                            '<div class="dialog-windows-pt-button"> <span class=" dialog-windows-pt-button-blue dialog-windows-pt-span"  ng-click="$dialog_confirm_sure()">确认</span>' +
                                '<span class=" dialog-windows-pt-button-blue dialog-windows-pt-span dialog-windows-pt-list"  ng-click="$dialog_confirm_cancel()">取消</span> ' +
                            '</div>'+
                      '</div>';
    var successHtml = '<div class="dialog-windows-pt-body">'+
					     '<div class="dialog-windows-pt-close dialog-windows-pt-close-qing" ng-click="$dialog_success_close()"></div>'+
					     '<div class="dialog-windows-pt-img dialog-windows-pt-img-qing">{replacement}</div>'+
					     '<div class="dialog-windows-pt-button dialog-windows-pt-button-qing" ng-click="$dialog_success_close()">关闭</div>'+
					  '</div>';
    /**
     * 把元素添加到body上，显示遮罩，并把dialog居中显示
     * replace message,show mask,show dialog
     * @param html
     * @param message
     * @returns {*|jQuery|HTMLElement}
     */
    var rsd = function(html,message){
        var el = $($compile(html.replace("{replacement}",message))($rootScope));
        body.append(el);
        mask.show();//显示遮罩
        el.show();
        offset(el);//居中
        return el;
    }
    var dialog = {
    		
    };
    return {
        /**
         * 显示一个错误窗口
         * @param message
         */
        error : function(message){
            $rootScope.$dialog_error_close = function(){
            	dialog.error.remove();
                mask.hide();
                delete dialog.error;
            };
            dialog.error = dialog.error||rsd(errorHtml,message);
        },
        /**
         * 显示一个警告窗口
         * @param message
         */
        warn : function(message){
        	$rootScope.$dialog_warn_close = function(){
        		dialog.warn.remove();
                mask.hide();
                delete dialog.warn;
            };
            dialog.warn = dialog.warn||rsd(warnHtml,message);
        },
        /**
         * 显示一个信息提示窗口
         * @param message
         */
        info : function(message){
            $rootScope.$dialog_info_close = function(){
            	dialog.info.remove();
                mask.hide();
                delete dialog.info;
            };
            dialog.info = dialog.info||rsd(infoHtml,message);
        },
        /**
         * 显示一个成功提示窗口
         * @param message
         */
        success : function(message){
            $rootScope.$dialog_success_close = function(){
            	dialog.success.remove();
                mask.hide();
                delete dialog.success;
            };
            dialog.success = dialog.success||rsd(successHtml,message);
        },
        /**
         * 显示一个确认窗口
         * @param message
         * @param doSure
         * @param doFailure
         */
        confirm : function(message,doSure,doCancel){
            $rootScope.$dialog_confirm_close = function(){
            	dialog.confirm.remove();
                mask.hide();
                delete dialog.confirm;
            };
            $rootScope.$dialog_confirm_sure = function(){
            	dialog.confirm.remove();
                mask.hide();
                delete dialog.confirm;
                if(doSure)doSure();
                
            };
            $rootScope.$dialog_confirm_cancel = function(){
            	dialog.confirm.remove();
                mask.hide();
                delete dialog.confirm;
                if(doCancel)doCancel();
            };
            dialog.confirm = dialog.confirm||rsd(confirmHtml,message);
       }
    }
}]);/**
 * 用户服务
 * 用来获取当前登录用户的一些个人信息
 *
 * */
angular.module('services').factory('extTemplate',['$http',function($http){
	
	return {
		/**
		 * 根据Ext的模版规则以及JSON对象，生成一个html字符串
		 */
		getHtml : function(template,json){
			var tpl = new NS.Template(template);
			return tpl.apply(json);
		},
		/**
		 * 根据模版Url加载模版，并且生成html字符串
		 */
		getHtmlByTemplateUrl : function(url,json){
			var me = this,
				callback,
				ret = {
					success : function(thenCallback){
        				callback = thenCallback;
        			}
				};
			$http({
				method : 'GET',
				url : url
			}).success(function(data){
				var html = me.getHtml(data,json);
				if (callback) {
                    callback(html);
                }
			});
			return ret;
		}
	};
}]);/**
 * 定义一个mask(遮罩)服务
 */
angular.module('services').factory('mask',function(){
    var body = $("body");//获取body元素
    //创建一个全局遮罩
    var mask = $('<div class="cs-window-background"></div>');
    var loading = $('<div class="cs-window-loading"><div class="cs-window-img"></div></div>');
    mask.hide();
    loading.hide();
    body.append(mask);
    body.append(loading);
    return {
        /**
         * 显示遮罩
         */
        show : function(){
            mask.show();
        },
        /**
         * 隐藏遮罩
         */
        hide : function(){
            mask.hide();
        },
        /**
         * 显示遮罩和加载中
         */
        showLoading : function(){
        	mask.show();
        	loading.show();
        },
        /**
         * 隐藏遮罩和加载中
         */
        hideLoading : function(){
        	mask.hide();
        	loading.hide();
        }
    }
});
/**
 * 基于angularjs的$http服务之上的封装
 * */
angular.module('services').factory('http',['$http',function($http){
    var baseUrl = httpConfig.baseUrl;
        return {
            /**
             * 请求数据,通过该形式，避免了使用回调函数的形式获取数据，极大的增强了代码的可读性
             * var req = {
             * 	    service : 'beanId?method',
             * 		params : {
             * 			name : '张三',
             * 			sex : '男'
             * 		}
             * };
             * var data = http.callService(req);
             * $scope.iterator = data;//这里需要在模版中通过iterator.data来进行数据的遍历，
             * 						  //不要在这里直接试图通过iterator.data获取数据（ajax请求是异步的，数据尚未绑定到该键上)
             *
             *********************************************************
             * 当然如果你想直接获取数据：你可以这样做：
             * http.callService(req).success(function(data){
             * 		//在这里获取你实际需要的数据
             * });
             */
            callService : function(request){
            	var requestCopy = angular.copy(request),
        		params = requestCopy.params,
        		backKey = "data",
                requests = [],
                sm = requestCopy.service.split("?"),
        		callback,
        		ret = {
        			success : function(thenCallback){
        				callback = thenCallback;
        			}
        		};
	        	requestCopy.beanName = sm[0];
	        	requestCopy.methodName = sm[1];
	        	if(!requestCopy.params)requestCopy.params = [];
	        	requestCopy.dataArray=requestCopy.params;
	        	delete requestCopy.service;
	        	delete requestCopy.params;
	            $http({
	                method: 'POST',
	                url: baseUrl,
	                data: {params:JSON.stringify(requestCopy)}
	            }).success(function(data){
	                if (callback) {
	                    callback(data);
	                } else {
	                    ret[backKey] = data;
	                    delete ret.success;
	                }
	            });
	        	return ret;
            },
            /**
             * 为了解决ajax 嵌套调用的问题，屏蔽到复杂的嵌套结构
             *       http.call({
             *         service : 'base?get'
             *       }).call(function(data1){
             *           return {
             *               service : 'base?get1'
             *           }
             *       }).call(function(data1,data2){
             *           return {
             *               service : 'base?get2'
             *           }
             *       }).end(function(data1,data2,data3){
             *
             *       })
             *
             * @param req
             * @returns {{call: call, end: end}}
             */
            call : function(req){
                var  me = this,
                     requests = [req],
                     results = [],
                     endCallback,
                     ret = {
                        call : function(req){
                            requests.push(req);
                            return this;
                        },
                        end : function(callback){
                            endCallback = callback;
                            var reqIndex = 0;
                            var callFn = function(req){
                                if(angular.isFunction(req)){
                                    req = req.apply(window,results);
                                }
                                me.callService(req).success(function(data){
                                    results.push(data);
                                    if(reqIndex < requests.length-1){
                                        var nextReq = requests[reqIndex+1];
                                        callFn(nextReq);
                                    }else if(reqIndex == requests.length-1){
                                        endCallback.apply(window,results);
                                    }
                                    reqIndex++;
                                });
                            }
                            callFn(requests[reqIndex]);
                        }
                     };
                return ret;
            }
        }
    }
]);
/**
 * 通用上传组件
 * fileUpload.upload({
		   service : 'fileUploadService?defaultUpload',
		   params : ["jw/images/"]
	   },files);
 */
angular.module('services').factory('fileUpload',['$upload',function($upload){
    var url = httpConfig.uploadUrl;
    return {
        /**
         * 上传组件，参数必须为数组
         * fileUpload.upload({
         *    service : "bean?method",
         *    params : [],
         *    files : files
         * }).success(function(data){
         *    console.log(data);
         * });
         * @param params
         */
        upload : function(req){
            var callback,
            	files = req.files,
                ret = {
	                success : function(thencallback){
	                    callback = thencallback;
	                }
            	};
            delete req.files;
            if (files && files.length) {
	            $upload.upload({
	                url: url,
	                file: files,
	                data : req
	            }).progress(function (evt) {
	                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	                console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
	            }).success(function (data, status, headers, config) {
	                if(callback)callback(data);
	            });
            }
            return ret;
        }
    };
}]);
(function(){
	var basePath = httpConfig.basePath;
    var userAgent = navigator.userAgent.toLowerCase();
    $.browser = $.browser || {
            version: (userAgent.match( /.+( :rv|it|ra|ie)[\/: ]([\d.]+)/ ) || [])[1],
            safari: /webkit/.test( userAgent ),
            Opera: /opera/.test( userAgent ),
            msie: /msie/.test( userAgent ) && !/opera/.test( userAgent ),
            mozilla: /mozilla/.test( userAgent ) && !/(compatible|webkit)/.test( userAgent )
    	};
    if(!$.browser.msie){
        FileAPI = {

        };
    }else{
        FileAPI = {
        		jsUrl : basePath+'/framework/angular/fileupload/FileAPI.min.js',
        		flashUrl : basePath+'/framework/angular/fileupload/FileAPI.flash.swf'
        };
    }
})();
/**
 * 通用下载组件
 *     fileDownload.download({
		   service : 'test?downloadFile',
	       params : [{name : '张三'}]
	   })
 */

angular.module('services').factory('fileDownload',function(){
	var downloadUrl = httpConfig.downloadUrl;
	var iframe = $("<iframe style='display:none;' id='system_for_download'></iframe>");
	$('body').append(iframe);
	iframe.attr('src','..');
    return {
        /**
         * 上传组件，参数必须为数组
         * fileDownload.download({
         *    service : "bean?method",
         *    params : []
         * });
         * @param params
         */
        download : function(request){
        	var urlP = downloadUrl+"?",
            service = request.service,
            params = request.params || {};
            smarray = service.split("?");
            urlP+="service="+smarray[0];
            urlP+="&method="+smarray[1];
            urlP+="&params="+angular.toJson(params);
	        iframe.attr("src", urlP);  
        }
    };
});/**
 * response服务封装
 * */
angular.module('services').factory('response',function(){
	var body = $('body'),
        parseParam=function(param, key){
            var paramStr="";
            if(param instanceof String||param instanceof Number||param instanceof Boolean){
                paramStr+="&"+key+"="+encodeURIComponent(param);
            }else{
                $.each(param,function(i){
                    var k=key==null?i:key+(param instanceof Array?"["+i+"]":"."+i);
                    paramStr+='&'+parseParam(this, k);
                });
            }
            return paramStr.substr(1);
        };

    return {
        /**
         * 在当前页面跳转到一个新的页面
         * @param url
         * @param params
         */
        redirect : function(url,params){
            window.location.href = httpConfig.basePath+"/"+url+"?"+parseParam(params||{});
        },
        /**
         * 打开一个新的tab页面或者页面
         */
        redirect_new : function(url,params){
        	var action = httpConfig.basePath+"/"+url+"?"+parseParam(params||{});
            var tempwindow=window.open('_blank');
            tempwindow.location = action;
        }
    }

});/**
 * 用户服务
 * 用来获取当前登录用户的一些个人信息
 *
 * */
angular.module('services').factory('user',['userObj','http',function(user,http){
	var user;
	$.ajax({
		async : false,
		url : httpConfig.baseUrl,
		data : {server : 'loginService',method : 'getUser',params : '[]'},
		dataType : 'json',
		type: 'POST',
		success : function(data){
			user = data.result;
		}
	});
    return {
        /**
         * 获取当前登录用户的姓名
         * @return {string}
         */
        getUsername : function(){
            return user.username;
        },
        /**
         * 获取上次登录时间
         */
        getLastLoginTime : function(){
            return user.logintime||"";
        },
        /**
         * 获取上次登录IP
         */
        getLastLoginIp : function(){
            return "192.168.100.5";
        },
        /**
         * 获取部门名称
         * @return {string}
         */
        getDepartmentName : function(){
            return user.bmmc;
        },
        /***
         * 获取人员ID
         * @return {string}
         */
        getRyId : function(){
        	return user.ryId;
        }
        
    }
}]);
/**
 * 定义针对controller的封装
 */
custom.controller = (function(){
    /**
     * 获取实际的控制器
     * @param array
     * @returns {*}
     */
    function getController(array){
        return array[array.length-1];
    }
    /**
     * 获取注入的对象
     * @param array
     * @returns {Array|*}
     */
    function getInjects(array){
        array.splice(array.length-1,1);
        return array;
    }

    /**
     * 获取scope对象在数组中的index
     * @param array
     * @returns {number}
     */
    function getScopeIndex(array){
        for(var i= 0,len = array.length;i<len;i++){
            if(angular.isString(array[i]) && array[i] == "$scope"){
                return i;
            }
        }
        return -1;
    }

    /**
     * 生成控制器的函数
     */
    return function(app,controller,array){
        if(!angular.isArray(array)){
            throw new Error("controller传入的第三个参数必须是一个数组!");
        }
        var appModule = angular.module(app);//获取当前模块,当前模块一定是被angular.module(app,[]);完成定义的
        var scopeIndex = getScopeIndex(array);//获取$scope对象的索引
        var controllerFn = getController(array);//获取控制器方法
        var injects = getInjects(array);//获取待注入服务

        var fn = function(){
            var $scope = arguments[scopeIndex];
            controllerFn.apply(this,arguments);
            //针对scope中的事件函数做代理处理，权限控制留的入口
            
        }
        injects.push(fn);
        appModule.controller(controller,injects);
    }
})();
/**
 * 定义针对service的封装,为了和controller保持定义行为的一致性
 */
custom.service = (function(){
    /**
     * 获取实际的控制器
     * @param array
     * @returns {*}
     */
    function getService(array){
        return array[array.length-1];
    }
    /**
     * 获取注入的对象
     * @param array
     * @returns {Array|*}
     */
    function getInjects(array){
        array.splice(array.length-1,1);
        return array;
    }
    /**
     * 生成service的函数
     */
    return function(app,service,array){
        var appModule = angular.module(app);//获取当前模块,当前模块一定是被angular.module(app,[]);完成定义的
        if(angular.isArray(array)){
        	var serviceFn = getService(array);//获取service方法
            var injects = getInjects(array);//获取待注入服务
            var fn = function(){//代理函数
                return serviceFn.apply(this,arguments);
            }
            injects.push(fn);
            appModule.factory(service,injects);
        }else if(angular.isFunction(array)){
            var fn = function(){//代理函数
                return array.apply(this,arguments);
            }
            appModule.factory(service,fn);
        }
    }
})();angular.module('directives').config(function($provide){
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
/**
 * 自定义模块，用来引导之前定义的三个模块
 * @type {module|*}
 */
angular.module('custom',['services','filters','directives','angularFileUpload']);
/**
 * 针对angularjs的特殊情况，特殊配置其http的请求参数
 */
angular.module('custom').config(['$httpProvider',function($httpProvider) {
    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
 
    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [function(data) {
        /**
         * The workhorse; converts an object to x-www-form-urlencoded serialization.
         * @param {Object} obj
         * @return {String}
         */
        var param = function(obj) {
            var query = '';
            var name, value, fullSubName, subName, subValue, innerObj, i;
 
            for (name in obj) {
                value = obj[name];
 
                if (value instanceof Array) {
                    for (i = 0; i < value.length; ++i) {
                        subValue = value[i];
                        fullSubName = name + '[' + i + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value instanceof Object) {
                    for (subName in value) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value !== undefined && value !== null) {
                    query += encodeURIComponent(name) + '='
                            + encodeURIComponent(value) + '&';
                }
            }
 
            return query.length ? query.substr(0, query.length - 1) : query;
        };
 
        return angular.isObject(data) && String(data) !== '[object File]'
                ? param(data)
                : data;
    }];
}]);
