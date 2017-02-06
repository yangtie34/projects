/**
 * 日期指令
 *  <div cs-window show="flag" autoCenter="true" offset="offset">窗体</div>
 */
system.directive('csWindow',['$timeout',function($timeout){
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
        // 修复弹出框超出窗口顶部，左部
        top  = top<0 ? 0 : top;
        left = left<0 ? 0 : left;
        if(autoCenter == "true"){
              el.css("left",left + "px");
              el.css("top",top + "px");
//              el.css("right", "8%"); // 取消自适应：自适应会让小窗口内容显示效果不好 20161221
        }
    }
    var offset1 = function(el,offset){
    	el.offset(offset);
    }
    return {
        restrict : 'A',
        scope : {
            show : '=show',
            offset : '=offset',
            clickDisappear : '=clickDisappear'
        },
        link : function(scope, element, attrs){
            var autoCenter = attrs.autoCenter || "true",
                m = attrs.mask || "true",
                disappear = attrs.clickDisappear || true; // 默认鼠标点击外部区域窗口消失 20161221
            
            var windowClickEvent = false;
            
            scope.$watch('show',function(newV,oldV){
                if(newV == true){
                    element.show();
                    if(m === "true")mask.show();
                    if(disappear){
                    	$timeout(function(){
                        	$(document).one('click',function(event){
                        		scope.show = false;
                        		$timeout(function(){
                        			scope.$apply();
                        		});
                        	});
                    	});
                    }
                    $timeout(function(){
                    	scope.reOffset();
                	});
                }else{//如果值不是通过document click事件进行改变的，需要首先对document的click事件解除绑定
//                	$(document).unbind('click'); // 解除文档click事件影响巨大 20160808
                    element.hide();
                    mask.hide();
                }
            });
            if(disappear){
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
            scope.reOffset=function(){
	           	 if(!scope.offset)offset(element,autoCenter);
	             else offset1(element,scope.offset);//手动移动到指定位置
            };
            $(window).resize(function () {  
            	scope.reOffset();
            });
            scope.reOffset();
        }
    }
}]);