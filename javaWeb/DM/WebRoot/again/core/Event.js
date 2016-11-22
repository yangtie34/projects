/***
 *@class NS.Event
 * 事件封装类
 */
NS.Event = (function(){
    var event;
    return {
        /**
         * 设置事件对象的包装对象
         * @param {Ext.EventObj} e
         * @private
         */
        setEventObj : function(e){
            event = e;
        },
        /**
         * 获取事件的触发元素
         * @return {HtmlElement}
         */
        getTarget : function(){
            return event.getTarget();
        },
        /**
         * 获取事件在浏览器上触发的X轴坐标
         * @return {Number}
         */
        getX : function(){
            return event.getX();
        },
        /**
         * 获取事件在浏览器上触发的Y轴坐标
         * @return {Number}
         */
        getY : function(){
            return event.getY();
        },
        /**
         * 获取事件在浏览器上触发的X轴、Y轴坐标
         * @return {Number[]} x和y的值像[x,y]这样
         */
        getXY : function(){
            return event.getXY();
        },
        /**
         * 阻止浏览器默认事件
         */
        preventDefault: function(){
            event.preventDefault();
        },
        /**
         * 阻止浏览器默认事件，并且阻止事件上浮
         */
        stopEvent : function(){
            event.stopEvent();
        },
        /**
         * 取消事件的上浮
         */
        stopPropagation : function(){
            event.stopPropagation();
        },
        /**
         * 获取触发事件的键盘的值
         * @return {Number}
         */
        getKey : function(){
            return event.getKey();
        }
    };
})();