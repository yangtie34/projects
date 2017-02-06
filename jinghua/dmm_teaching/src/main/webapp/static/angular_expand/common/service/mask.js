/**
 * 定义一个mask(遮罩)服务
 */
system.factory('mask',function(){
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