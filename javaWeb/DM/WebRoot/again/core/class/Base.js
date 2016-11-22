/**
 * @class NS.Base
 * ## 基类，所有类的父类
 * @author wangyongtai
 */
(function() {
    var alias = NS.Function.alias;
	var setListeners = function(config){//根据组件的监听参数为组件增加监听
        if(config&&config.listeners){
            var listeners = config.listeners;
            if(NS.isObject(listeners)){
                for(var i in listeners){
                    var listener = listeners[i];
                    if(NS.isObject(listener[i])){
                        this.addListener(i,listener.fn,listener.scope||this);
                    }else if(NS.isFunction(listener[i])){
                        this.addListener(i,listener[i],this);
                    }
                }
            }
        }
	};
	var Base = function(config) {
		setListeners.apply(this,arguments);
	};
	var Manager = NS.ClassManager;
	Manager.register('NS.Base', Base);// 注册NS.Base类
	NS.apply(Base, {
				$classname : 'NS.Base',
				
				statics : function(config) {
					
				},
				/**
		         * @private
		         * @param {String} name
		         * @param {Function/Object/Boolean..}member
		         */
		        addMember: function(name, member) {
		            if (typeof member == 'function' && member !== NS.emptyFn) {
		                member.$owner = this;
		            }
		            this.prototype[name] = member;

		            return this;
		        },
		        /***
                 * @private
		         * 将一个传递的对象的参数当作成员添加到类的原形链上
		         * @param {Object} config 需要添加的对象属性
		         */
		        addMembers : function(config){
		        	if(config){
		        		for(var i in config){
		        			if(config.hasOwnProperty(i)){
		        			   this.addMember(i,config[i]);
		        			}
		        		}
		        	}
		        }
			});
	Base.addMembers({
		/**
		 * @private
		 * @property {String} $classname
		 */
		$classname : 'NS.Base',
		/**
		 * 当前对象对应的类
		 * @type {NS.Base}
		 */
		self : Base,
		/**
		 *  调用父方法
		 * @param args arguments/参数数组
		 */
		callParent : function(args) {
			var method = this.callParent.caller;
			if (method.caller && method.caller.$owner) {
				method = method.caller;
				return method.$owner.superclass[method.$name].apply(
						this, args||[]);
			} else if (method.superclass)
				return method.superclass.constructor.apply(this,
						args||[]);
		},
		/**
		 * 获取当前类名
		 * @return {String} className
		 */
		getName : function() {
			return Manager.getName(this);
		},
        /**
         * 代码如下
         *
         *     var base = new NS.Base();
         *     base.addEvents('myevent1','myevent2');
         *
         *  为组件增加事件
         * @param {String} eventName 增加的事件名称可以是一个可变参数序列
         */
        addEvents : function(obj) {
            var me = this, i = 0, events = me.events = me.events || {};
            if (typeof obj == 'string') {
                for (args = arguments, i = args.length; i--;) {
                    arg = args[i];
                    if (!events[arg]) {
                        events[arg] = {};
                    }
                }
            } else {
                NS.applyIf(me.events, o);
            }
        },
        /**
         *   为组件增加监听事件监听
         *  你可以这样做
         *
         *        var component = new NS.Component();
         *        component.on('click',function(){},this);
         *
         *  也可以这样
         *
         *        var component = new NS.Component();
         *        component.on({
         *            click : this.onclick,
         *            hover : this.onhover
         *        });
         *
         *   也可以这样
         *
         *        component.on({
         *            click : {fn : this.onclick,scope:this}
         *        });
         *
         * @param {String} eventName 事件名称
         * @param {Function} callback 回调函数
         * @param {Object} scope 作用域
         */
        addListener : function(eventName, callback,scope) {
            var me = this,item,
            events = me['events'] || {};
            if(NS.isString(eventName)){
                events[eventName] = {};
                events[eventName].callback = callback;
                events[eventName].scope = scope;
            }else if(NS.isObject(eventName)){//如果第一个参数是对象
                for(var i in eventName){
                    if(events[i]){
                        item = eventName[i];
                        if(NS.isFunction(item)){
                           events[i].callback = item;
                        }else if(NS.isObject(item)){
                           if(item.fn){
                               events[i].callback = item.fn;
                               events[i].scope = item.scope;
                           }
                        }
                    }
                }
            }
        },
        /**
         * 移除对对象的监听事件
         * @param {String} event 事件名
         */
        removeListener : function(event){
            var events = this['events']||{};
            if(NS.isString(event)){
               delete events[event];
            }
        },
        /**
         *  触发组件的某个事件事件
         *
         *      var com = new NS.Component();
         *      com.fireEvent('click',arg1,arg2,....);
         *
         * @param {String} eventName 触发的事件名称
         * @param {Object...} args 可变参数列表
         */
        fireEvent : function(eventName) {
            var me = this, eArray, // 需要被触发的事件---的监听函数数组
                args = arguments,// 参数数组
                copyargs = [], // 需要传递给监听函数的参数数组---排除可能的（第一个代理处理对象，事件名，参数1，参数2，参数3。。。。。。。）
                i = 0, // 事件监听函数索引
                eFun,//事件相应函数
                eFunArray,//事件对应数组
                j = 1;// 参数数组循环索引
            if (typeof eventName == 'string' && me.events) {
                eFunArray = me.events[eventName]||[];//事件数组
            } else if (typeof eventName == 'object') {
                for (; j < args.length; j++) {
                    copyargs.push(args[j]);
                }
                //this.fireEvent.apply(this, copyargs);
            }
            if (args.length > 1) {
                for (; j < args.length; j++) {
                    copyargs.push(args[j]);
                }
            }
            if(typeof eFunArray === 'object'){
                if(eFunArray.callback!=undefined){
                   return eFunArray.callback.apply(eFunArray.scope||me,copyargs);
                }
            }
        }
	});

    /**
     *
     * @member NS.Base
     * @method on
     * @inheritdoc NS.Base#addListener
     */
    NS.Base.prototype.on = Base.prototype.addListener;
    /**
     *
     * @member NS.Base
     * @method addEvent
     * @inheritdoc NS.Base#addEvents
     */
    NS.Base.prototype.addEvent = Base.prototype.addEvents;
})();