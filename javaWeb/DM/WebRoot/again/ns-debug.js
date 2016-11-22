/**
 * @class NS
 * 自定义命名空间
 * @author wangyongtai
 */
var NS = NS || {};
(function() {
    var global = this,
    objectPrototype = Object.prototype,
    toString = objectPrototype.toString,
    emptyFn = function(){};

    NS.global = global;
    /**
     * 将所有config中的属性复制到object中.
     * 注意:如果是用来递归的合并对象，而不是简简单单引用对象/数组的话,清使用{@link NS#merge}.
     * @param {Object} object 属性的接受者
     * @param {Object} config 属性源
     * @param {Object} defaults 一个默认的同样需要将属性复制到object中.
     * @return {Object} returns obj
     */
	NS.apply = function(object, config, defaults) {
		if (defaults) {
			NS.apply(object, defaults); // TODO ?? 何意
		}
		if (object && config && typeof config === "object") {
			var i, j, k;
			for (i in config) {
				object[i] = config[i];
			}
		}
	};

	NS.apply(NS,{
		/***
		 * {property} Boolean USE_NATIVE_JSON
		 * 如果值为true那么使用浏览器json解析策略（如果浏览器有的话），如果为false则使用方法解析策略
		 * **注意:**JSON解析方法不能够解析带有方法的对象。 
		 */
		USE_NATIVE_JSON : true,
		userAgent: navigator.userAgent.toLowerCase(),
		 /**
         * 可重复使用的空函数
         */
        emptyFn: emptyFn,
		/**
		 * 讲config中的所有不存在于object中的属性复制到object中
		 * @param {Object}object  属性的接受者             
		 * @param {Object}config  属性源
		 */
		applyIf : function(object, config) {
			var property;
			for (property in config) {
				if (typeof object[property] === 'undefined') {
					object[property] = config[property];
				}
			}
		},
		/**
	     * Merges any number of objects recursively without referencing them or their children.
	     *
	     *     var extjs = {
	     *         companyName: 'Ext JS',
	     *         products: ['Ext JS', 'Ext GWT', 'Ext Designer'],
	     *         isSuperCool: true,
	     *         office: {
	     *             size: 2000,
	     *             location: 'Palo Alto',
	     *             isFun: true
	     *         }
	     *     };
	     *
	     *     var newStuff = {
	     *         companyName: 'Sencha Inc.',
	     *         products: ['Ext JS', 'Ext GWT', 'Ext Designer', 'Sencha Touch', 'Sencha Animator'],
	     *         office: {
	     *             size: 40000,
	     *             location: 'Redwood City'
	     *         }
	     *     };
	     *
	     *     var sencha = NS.merge(extjs, newStuff);
	     *
	     *     // extjs and sencha then equals to
	     *     {
	     *         companyName: 'Sencha Inc.',
	     *         products: ['Ext JS', 'Ext GWT', 'Ext Designer', 'Sencha Touch', 'Sencha Animator'],
	     *         isSuperCool: true,
	     *         office: {
	     *             size: 40000,
	     *             location: 'Redwood City'
	     *             isFun: true
	     *         }
	     *     }
	     *
	     * @param {Object...} object Any number of objects to merge.
	     * @return {Object} merged The object that is created as a result of merging all the objects passed in.
	     */
	    merge: function(source) {
	        var i = 1,
	            ln = arguments.length,
	            mergeFn = ExtObject.merge,
	            cloneFn = Ext.clone,
	            object, key, value, sourceKey;

	        for (; i < ln; i++) {
	            object = arguments[i];

	            for (key in object) {
	                value = object[key];
	                if (value && value.constructor === Object) {
	                    sourceKey = source[key];
	                    if (sourceKey && sourceKey.constructor === Object) {
	                        mergeFn(sourceKey, value);
	                    }
	                    else {
	                        source[key] = cloneFn(value);
	                    }
	                }
	                else {
	                    source[key] = value;
	                }
	            }
	        }

	        return source;
	    },
		/**
         * 如果传递的值为一个JavaScript数组类型的话，反回值为true,其他情况反回false.
         * @param {Object} value 待测试的值
         * @return {Boolean}
         * @method
         */
        isArray: ('isArray' in Array) ? Array.isArray : function(value) {
            return toString.call(value) === '[object Array]';
        },

        /**
         * 如果传递的值为一个JavaScript日期对对象类型的话，反回值为true,其他情况反回false
         * @param {Object} value 测试的值
         * @return {Boolean}
         */
        isDate: function(value) {
            return toString.call(value) === '[object Date]';
        },

        /**
         * 如果传递的值为一个JavaScript对象类型的话，反回值为true,其他情况反回false.
         * @param {Object} value 测试的值
         * @return {Boolean}
         * @method
         */
        isObject: (toString.call(null) === '[object Object]') ?
		        function(value) {
		            // check ownerDocument here as well to exclude DOM nodes
		            return value !== null && value !== undefined && toString.call(value) === '[object Object]' && value.ownerDocument === undefined;
		        } :
		        function(value) {
		            return toString.call(value) === '[object Object]';
		        },
        /**
         * 如果传递的值为一个JavaScript基本类型，string,number 或者boolean则反回值为true,其他情况反回false.
         * @param {Object} value 测试的值
         * @return {Boolean}
         */
        isPrimitive: function(value) {
            var type = typeof value;

            return type === 'string' || type === 'number' || type === 'boolean';
        },

        /**
         * 如果传递的值为一个JavaScript函数类型的话，反回值为true,其他情况反回值为false.
         * @param {Object} value 测试的值
         * @return {Boolean}
         * @method
         */
        isFunction:
        // Safari 3.x and 4.x returns 'function' for typeof <NodeList>, hence we need to fall back to using
        // Object.prototype.toString (slower)
        (typeof document !== 'undefined' && typeof document.getElementsByTagName('body') === 'function') ? function(value) {
            return toString.call(value) === '[object Function]';
        } : function(value) {
            return typeof value === 'function';
        },

        /**
         * 如果传递的值为一个JavaScript数值类型的话，反回值为true,如果返回值为无穷大则反回flase.
         * @param {Object} value The value to test
         * @return {Boolean}
         */
        isNumber: function(value) {
            return typeof value === 'number' && isFinite(value);
        },
        /**
         * 如果传递的值为一个字符串的话，返回值为true,其他情况反回值为false.
         * @param {Object} value The value to test
         * @return {Boolean}
         */
        isString: function(value) {
            return typeof value === 'string';
        },
        /**
         * 如果传递的值为一个boolean 类型的话，返回值为true,其他情况反回值为false.
         * @param {Object} value The value to test
         * @return {Boolean}
         */
        isBoolean: function(value) {
            return typeof value === 'boolean';
        },

        /**
         * 如果传递的值为一个HTMLElement 类型的话，返回值为true,其他情况反回值为false.
         * @param {Object} value The value to test
         * @return {Boolean}
         */
        isElement: function(value) {
            return value ? value.nodeType === 1 : false;
        },

        /**
         * 如果传递的值为一个TextNode 类型的话，返回值为true,其他情况反回值为false.
         * @param {Object} value The value to test
         * @return {Boolean}
         */
        isTextNode: function(value) {
            return value ? value.nodeName === "#text" : false;
        },

        /**
         * 如果传递的值为一个定义过的类型的话，返回值为true,如果为undefined则反回值为false.
         * @param {Object} value The value to test
         * @return {Boolean}
         */
        isDefined: function(value) {
            return typeof value !== 'undefined';
        },
        /**
         * 如果传递的值为空，那么返回true，其他情况返回false
         *
         * - `null`
         * - `undefined`
         * - 长度为0的数据
         * - 零长度的字符串 (除了allowEmptyString的值为true的情况)
         *
         * @param {Object} value 测试的值
         * @param {Boolean} allowEmptyString (optional) 如果为真的话，则允许空字符串
         * @return {Boolean}
         * @markdown
         */
        isEmpty: function(value, allowEmptyString) {
            return (value === null) || (value === undefined) || (!allowEmptyString ? value === '' : false) || (NS.isArray(value) && value.length === 0);
        },
        /***
         * 判断是否为底层类库对象
         * @param obj
         * @returns {Boolean}
         */
        isLibObj : function(obj){
        	return NS.isExtObj(obj);
        },
        isExtObj : function(obj){
        	return obj instanceof Ext.Base;
        },
        /**
         * 是否是NS对象
         * @param {Object} obj
         * @return {boolean}
         */
        isNSObj : function(obj){
            return obj instanceof NS.Base;
        },
        /**
         * 是否是NS.Component 对象
         * @param {Object} obj
         * @return {boolean}
         */
        isNSComponent : function(obj){
            return obj instanceof NS.Component;
        },
        /**
         * 是否是NS.dom.Element 对象
         * @param {Object} obj
         * @return {boolean}
         */
        isNSElement : function(obj){
            return obj instanceof NS.dom.Element;
        }
	});
})();

(function(){
	 var check = function(regex){
         return regex.test(NS.userAgent);
     },
     version = function (is, regex) {
         var m;
         return (is && (m = regex.exec(NS.userAgent))) ? parseFloat(m[1]) : 0;
     },
     docMode = document.documentMode,
     isChrome = check(/\bchrome\b/),
     isOpera = check(/opera/),
     isWebKit = check(/webkit/),
     isGecko = !isWebKit && check(/gecko/),
     isGecko3 = isGecko && check(/rv:1\.9/),
     isIE = !isOpera && check(/msie/),
     isIE7 = isIE && ((check(/msie 7/) && docMode != 8 && docMode != 9) || docMode == 7),
     isIE8 = isIE && ((check(/msie 8/) && docMode != 7 && docMode != 9) || docMode == 8),
     isIE9 = isIE && ((check(/msie 9/) && docMode != 7 && docMode != 8) || docMode == 9),
     isFF3_0 = isGecko3 && check(/rv:1\.9\.0/),
     isFF3_5 = isGecko3 && check(/rv:1\.9\.1/),
     isFF3_6 = isGecko3 && check(/rv:1\.9\.2/),
     isLinux = check(/linux/),
     isWindows = check(/windows|win32/),
     isMac = check(/macintosh|mac os x/),
     chromeVersion = version(true, /\bchrome\/(\d+\.\d+)/),
     firefoxVersion = version(true, /\bfirefox\/(\d+\.\d+)/),
     ieVersion = version(isIE, /msie (\d+\.\d+)/);
     
     NS.apply(NS,{
    	 /**
          * 如果检测到的浏览器为IE浏览器则为true,其他为false
          * @type Boolean
          */
         isIE : isIE,
         /**
          * 如果检测到的浏览器为 IE7 则为true,其他为false
          * @type Boolean
          */
         isIE7 : isIE7,

         /**
          * 如果检测到的浏览器为 IE8 则为true,其他为false
          * @type Boolean
          */
         isIE8 : isIE8,

         /**
          * 如果检测到的浏览器为 IE9 则为true,其他为false
          * @type Boolean
          */
         isIE9 : isIE9,

         /**
          * 如果检测到的浏览器为 火狐3.0 则为true,其他为false
          * @type Boolean
          */
         isFF3_0 : isFF3_0,

         /**
          * 如果检测到的浏览器为 火狐3.5 则为true,其他为false
          * @type Boolean
          */
         isFF3_5 : isFF3_5,

         /**
          * 如果检测到的浏览器为 火狐3.6 则为true,其他为false
          * @type Boolean
          */
         isFF3_6 : isFF3_6,

         /**
          * 如果检测到的浏览器为 火狐4 则为true,其他为false
          * @type Boolean
          */
         isFF4 : 4 <= firefoxVersion && firefoxVersion < 5,

         /**
          * 如果检测到的浏览器为 火狐5 则为true,其他为false
          * @type Boolean
          */
         isFF5 : 5 <= firefoxVersion && firefoxVersion < 6,
         /**
          * 如果检测到的浏览器为 谷歌浏览器 则为true,其他为false
          * @type Boolean
          */
         isChrome : isChrome,
         /**
          * 如果检测到的平台是Linux 则为true,其他则为false.
          * @type Boolean
          */
         isLinux : isLinux,

         /**
          * 如果检测到的平台是Windows 则为true,其他则为false.
          * @type Boolean
          */
         isWindows : isWindows,

         /**
          * 如果检测到的平台是Mac 则为true,其他则为false.
          * @type Boolean
          */
         isMac : isMac,
         /**
          * 当前Chrome浏览器的版本 (如果不是Chrome浏览器则为0).
          * @type Number
          */
         chromeVersion: chromeVersion,

         /**
          * 当前FireFox浏览器的版本 (如果不是FireFox浏览器则为0).
          * @type Number
          */
         firefoxVersion: firefoxVersion,

         /**
          * 当前IE浏览器的版本(如果不是IE浏览器则为0). This does not account
          * for the documentMode of the current page, which is factored into {@link NS#isIE7},
          * {@link NS#isIE8} and {@link NS#isIE9}. Thus this is not always true:
          *
          *     NS.isIE8 == (NS.ieVersion == 8)
          *
          * @type Number
          */
         ieVersion: ieVersion
     });
})();
NS.onReady = function(callback){
	NS.ClassLoader.onReady(callback);
};
(function(){
    /**
     * NS 的id生成器,生成全局唯一id
     * @member NS
     * @method id
     */
    NS.id = (function(){
        var id =1000;
        return function(){
            id+=1;
            return 'ns_'+id;
        }
    })();
})();/**
 * @class NS.Function
 *
 * 一个有用的用来处理回调函数的静态方法集合
 * @singleton
 */
NS.Function = {
		/**
	     * 创建一个提供的对象的方法的别名.
	     * 注意:该方法的执行环境仍然绑定在提供的对象上.
	     *
	     * @param {Object/Function} object
	     * @param {String} methodName
	     * @return {Function} aliasFn
	     */
	    alias: function(object, methodName) {
	        return function() {
	            return object[methodName].apply(object, arguments);
	        };
	    },
	    /**
	     * 通过传递的方法创建一个新的方法，然后将执行环境绑定到scope上,
	     * {@link NS#bind NS.bind} 是 {@link NS.Function#bind NS.Function.bind}的别名
	     *
	     * @param {Function} fn 委托的函数.
	     * @param {Object} scope (可选项) 该函数执行的环境，如果没有提供,那么其执行环境为window.
	     * @param {Array} args (可选项) 覆盖绑定的函数调用的参数. (调用者的默认参数)
	     * @param {Boolean/Number} appendArgs (可选项) 如果其值为true,那么将args添加到函数传递的参数后面,而不是覆盖函数的参数.
	     * 如果是一个数字的话，那么参数将会被插入到对应的数字对应的位置上
	     * @return {Function} 新创建的函数
	     */
	    bind: function(fn, scope, args, appendArgs) {
	        if (arguments.length === 2) {
	            return function() {
	                return fn.apply(scope, arguments);
	            };
	        }
	        var method = fn,
	            slice = Array.prototype.slice;

	        return function() {
	            var callArgs = args || arguments;

	            if (appendArgs === true) {
	                callArgs = slice.call(arguments, 0);
	                callArgs = callArgs.concat(args);
	            }
	            else if (typeof appendArgs == 'number') {
	                callArgs = slice.call(arguments, 0); // copy arguments first
	                NS.util.Array.insert(callArgs, appendArgs, args);
	            }

	            return method.apply(scope || NS.global, callArgs);
	        };
	    },
	    /**
	     * 创建一份所提供函数的备份，反回的函数将会调用所提供的方法和传递所有参数,沿用this执行环境,并且提供的方法调用的结果.
	     * @param {Function} method 
	     * @return {Function} cloneFn 复制的函数
	     */
	    clone: function(method) {
	        return function() {
	            return method.apply(this, arguments);
	        };
	    },
	    /**
	     * 创建一个代理函数，一个绑定的（可选的）执行环境,当调用的时候,函数将会延迟（配置的缓冲时间）后执行.如果在缓冲时间(函数未被执行)内再次调用,
	     * 那么之前的尚未真正执行的调用将被取消,然后函数将重新开始缓冲调用过程.
	     * @param {Function} fn 将被延迟调用的函数.
	     * @param {Number} buffer 函数延迟调用的时间.
	     * @param {Object} scope (optional) 函数执行的作用域. 如果省略该参数，那么默认作用域为调用者的作用域.
	     * @param {Array} args (optional) 覆盖调用的参数. 默认参数为arguments.
	     * passed by the caller.
	     * @return {Function}  返回一个延迟（指定时间）后执行的函数.
	     */
	    createBuffered: function(fn, buffer, scope, args) {
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
	    },
	    /**
	     * 创建一个拦截函数.传递的函数将会在原函数之前执行,如果传递的函数反回值为false,那么原函数将不会被执行. 
	     * 传递的函数将会反回原始的函数返回的结果,传递的函数调用的时候会使用原始函数的参数.用法示例如下:
	     *     var sayHi = function(name){
	     *         alert('Hi, ' + name);
	     *     }
	     *
	     *     sayHi('Fred'); // alerts "Hi, Fred"
	     *
	     *     // create a new function that validates input without
	     *     // directly modifying the original function:
	     *     var sayHiToFriend = NS.Function.createInterceptor(sayHi, function(name){
	     *         return name == 'Brian';
	     *     });
	     *
	     *     sayHiToFriend('Fred');  // no alert
	     *     sayHiToFriend('Brian'); // alerts "Hi, Brian"
	     *
	     * @param {Function} origFn 初始函数.
	     * @param {Function} newFn 在初始函数之前调用的函数.
	     * @param {Object} scope (optional) 传递的函数执行的作用域.
	     * **如果没有该选项, 默认的执行范围为初始函数的作用域或者浏览器的window对象.**
	     * @param {Object} returnValue (optional) 如果传递的函数返回为false的时候返回的值 (默认为 null).
	     * @return {Function} 新的函数
	     */
	    createInterceptor: function(origFn, newFn, scope, returnValue) {
	        var method = origFn;
	        if (!NS.isFunction(newFn)) {
	            return origFn;
	        }
	        else {
	            return function() {
	                var me = this,
	                    args = arguments;
	                newFn.target = me;
	                newFn.method = origFn;
	                return (newFn.apply(scope || me || NS.global, args) !== false) ? origFn.apply(me || NS.global, args) : returnValue || null;
	            };
	        }
	    },
	    /**
	     * 通过传递的两个函数参数 originalFn newFn(可选) scope(可选) 创建一个新的函数，该函数将会同时执行传递的两个函数。 如果newFn不存在,则返回originalFn,
	     * 如果newFn存在,那么newFn的执行将会绑定到scope上(存在的话),或者绑定到返回的创建的新函数上。示例如下所示:
	     * 
	     *
	     *     var sayHi = function(name){
	     *         alert('Hi, ' + name);
	     *     }
	     *
	     *     sayHi('Fred'); // alerts "Hi, Fred"
	     *
	     *     var sayGoodbye = NS.Function.createSequence(sayHi, function(name){
	     *         alert('Bye, ' + name);
	     *     });
	     *
	     *     sayGoodbye('Fred'); // both alerts show
	     *
	     * @param {Function} originalFn 初始函数
	     * @param {Function} newFn 待序列化的函数
	     * @param {Object} scope (optional) 指定了newFn函数的执行的作用域，如果省略，newFn执行的作用域为返回的函数
	     * @return {Function} 新的函数
	     */
	    createSequence: function(originalFn, newFn, scope) {
	        if (!newFn) {
	            return originalFn;
	        }
	        else {
	            return function() {
	                var result = originalFn.apply(this, arguments);
	                newFn.apply(scope || this, arguments);
	                return result;
	            };
	        }
	    },
	    /**
	     * Creates a throttled version of the passed function which, when called repeatedly and
	     * rapidly, invokes the passed function only after a certain interval has elapsed since the
	     * previous invocation.
	     * 根据传递的函数创建一个特殊的封装函数，该函数重复执行的时候必须和上次的执行时间相差指定的时间。
	     * 
	     * 这是一个针对重复调用的一个非常有用的封装，比如类似处理鼠标移动事件这类比较昂贵的处理过程。
	     *
	     * @param {Function} fn 将要在指定时间后执行的函数
	     * @param {Number} interval 传递的函数延迟interval毫秒后执行.
	     * @param {Object} scope (可选的) 函数执行的作用域
	     * 函数的执行环境，如果省略，函数的执行环境将会是调用者本身
	     * @returns {Function} 一个延迟调用传递的函数参数的函数.
	     */
	    createThrottled: function(fn, interval, scope) {
	        var lastCallTime, elapsed, lastArgs, timer, execute = function() {
	            fn.apply(scope || this, lastArgs);
	            lastCallTime = new Date().getTime();
	        };

	        return function() {
	            elapsed = new Date().getTime() - lastCallTime;//上次调用时间和现在时间的差值
	            lastArgs = arguments;

	            clearTimeout(timer);
	            if (!lastCallTime || (elapsed >= interval)) {
	                execute();
	            } else {
	                timer = setTimeout(execute, interval - elapsed);
	            }
	        };
	    },
	    /**
	     * 创建一个延迟若干毫秒执行的函数,函数可能会需要绑定作用域. 示例如下所示:
	     *
	     *     var sayHi = function(name){
	     *         alert('Hi, ' + name);
	     *     }
	     *
	     *     // 立即执行:
	     *     sayHi('Fred');
	     *
	     *     // 2秒后执行:
	     *     NS.Function.defer(sayHi, 2000, this, ['Fred']);
	     *
	     *     // this syntax is sometimes useful for deferring
	     *     // execution of an anonymous function:
	     *     NS.Function.defer(function(){
	     *         alert('Anonymous');
	     *     }, 100);
	     *
	     * {@link NS#defer NS.defer} is alias for {@link NS.Function#defer NS.Function.defer}
	     *
	     * @param {Function} fn 延迟执行的函数.
	     * @param {Number} millis 延迟执行的时间.
	     * (如果小于等于0,那么立即执行)
	     * @param {Object} scope (可选项) 函数执行的作用域.
	     * **如果省略，默认执行环境是window对象.**
	     * @param {Array} args  (可选项) 覆盖绑定的函数调用的参数. (调用者的默认参数)
	     * @param {Boolean/Number} appendArgs (可选项) 如果其值为true,那么将args添加到函数传递的参数后面,而不是覆盖函数的参数.
	     * 如果是一个数字的话，那么参数将会被插入到对应的数字对应的位置上
	     * @return {Number} 返回一个可以用clearTimeout来取消执行的对象
	     */
	    defer: function(fn, millis, scope, args, appendArgs) {
	        fn = NS.Function.bind(fn, scope, args, appendArgs);
	        if (millis > 0) {
	            return setTimeout(fn, millis);
	        }
	        fn();
	        return 0;
	    },
	    /**
	     * Adds behavior to an existing method that is executed before the
	     * original behavior of the function.  For example:
	     * 将一个函数和对象指定的函数进行绑定，让其在指定函数之前执行。示例如下所示:
	     *     var soup = {
	     *         contents: [],
	     *         add: function(ingredient) {
	     *             this.contents.push(ingredient);
	     *         }
	     *     };
	     *     NS.Function.interceptBefore(soup, "add", function(ingredient){
	     *         if (!this.contents.length && ingredient !== "water") {
	     *             // Always add water to start with
	     *             this.contents.push("water");
	     *         }
	     *     });
	     *     soup.add("onions");
	     *     soup.add("salt");
	     *     soup.contents; // will contain: water, onions, salt
	     * 
	     * @param {Object} object The target object
	     * @param {String} methodName Name of the method to override
	     * @param {Function} fn Function with the new behavior.  It will
	     * be called with the same arguments as the original method.  The
	     * return value of this function will be the return value of the
	     * new method.
	     * @param {Object} [scope] The scope to execute the interceptor function. Defaults to the object.
	     * @return {Function} The new function just created.
	     */
	    interceptBefore: function(object, methodName, fn, scope) {
	        var method = object[methodName] || NS.emptyFn;

	        return (object[methodName] = function() {
	            var ret = fn.apply(scope || this, arguments);
	            method.apply(this, arguments);

	            return ret;
	        });
	    },
	    /**
	     * Adds behavior to an existing method that is executed after the
	     * original behavior of the function.  For example:
	     * 将一个函数和对象指定的函数进行绑定，让该函数在对象的函数之后执行。示例如下所示:
	     *     var soup = {
	     *         contents: [],
	     *         add: function(ingredient) {
	     *             this.contents.push(ingredient);
	     *         }
	     *     };
	     *     NS.Function.interceptAfter(soup, "add", function(ingredient){
	     *         // Always add a bit of extra salt
	     *         this.contents.push("salt");
	     *     });
	     *     soup.add("water");
	     *     soup.add("onions");
	     *     soup.contents; // will contain: water, salt, onions, salt
	     * 
	     * @param {Object} object 目标对象
	     * @param {String} methodName 需要重写的方法名
	     * @param {Function} fn 新的函数.  It will
	     * be called with the same arguments as the original method.  The
	     * return value of this function will be the return value of the
	     * new method.
	     * @param {Object} [scope] The scope to execute the interceptor function. Defaults to the object.
	     * @return {Function} The new function just created.
	     */
	    interceptAfter: function(object, methodName, fn, scope) {
	        var method = object[methodName] || NS.emptyFn;

	        return (object[methodName] = function() {
	            method.apply(this, arguments);
	            return fn.apply(scope || this, arguments);
	        });
	    }
};
(function(){
	var alias = NS.Function.alias;
	/**
	 * 别名 for {@link NS.Function#bind}
	 * @member NS
	 * @method bind
	 * @inheritdoc NS.Function#bind
	 */
	NS.bind = alias(NS.Function,'bind');
	/**
	 *  复制对象，生成一个新的对象
	 * @member NS
	 * @method clone
     * @param {Object} obj 原始对象
     * @return {Object} 新对象
	 */
	NS.clone = alias(Ext,'clone');
	/**
	 * 别名 for {@link NS.Function#defer}
	 * @member NS
	 * @method defer
	 * @inheritdoc NS.Function#defer
	 */
	NS.defer = alias(NS.Function,'defer');
})();/***
 * @class NS.Error
 * 错误报告类
 * @author wangyongtai
 */
NS.Error = (function(){
   var toString = function(err){
	   var msg;
	   if(err){
		  msg = (err.sourceClass||"") +"类:-"+(err.sourceMethod||"")+"方法:-"+(err.msg||'');
	   }
	   return msg||"";
   };
   return {
	 /***
	  * 报告错误
	  *   		   NS.Error.raise({
	  *  			sourceClass : 'ClassName'//报告错误出现的类
	  *  			sourceMethod : 'methodName'//报告错误出现的类的方法
	  *  			msg : '错误信息'//报告错误信息
	  *            })
	  */
	 raise : function(err){
		 var msg = toString(err);
		 throw new Error(msg);
	 }
   };
})();
/**
 * 别名 for {@link NS.Error#raise}
 * @member NS
 * @method error
 * @inheritdoc NS.Error#raise
 */
NS.error = NS.Function.alias(NS.Error,'raise');/**
 * @class NS.JSON
 * JSON解析工具类
 * @singleton
 */
NS.JSON = new(function() {
    var me = this,
    encodingFunction,
    decodingFunction,
    useNative = null,
    useHasOwn = !! {}.hasOwnProperty,
    isNative = function() {
        if (useNative === null) {
            useNative = NS.USE_NATIVE_JSON && window.JSON && JSON.toString() == '[object JSON]';
        }
        return useNative;
    },
    pad = function(n) {
        return n < 10 ? "0" + n : n;
    },
    doDecode = function(json) {
        return eval("(" + json + ')');
    },
    doEncode = function(o, newline) {
        // http://jsperf.com/is-undefined
        if (o === null || o === undefined) {
            return "null";
        } else if (NS.isDate(o)) {
            return NS.JSON.encodeDate(o);
        } else if (NS.isString(o)) {
            return encodeString(o);
        }
        // Allow custom zerialization by adding a toJSON method to any object type.
        // Date/String have a toJSON in some environments, so check these first.
        else if (o.toJSON) {
            return o.toJSON();
        } else if (NS.isArray(o)) {
            return encodeArray(o, newline);
        } else if (typeof o == "number") {
            //don't use isNumber here, since finite checks happen inside isNumber
            return isFinite(o) ? String(o) : "null";
        } else if (NS.isBoolean(o)) {
            return String(o);
        } else if (NS.isObject(o)) {
            return encodeObject(o, newline);
        } else if (typeof o === "function") {
            return "null";
        }
        return 'undefined';
    },
    m = {
        "\b": '\\b',
        "\t": '\\t',
        "\n": '\\n',
        "\f": '\\f',
        "\r": '\\r',
        '"': '\\"',
        "\\": '\\\\',
        '\x0b': '\\u000b' //ie doesn't handle \v
    },
    charToReplace = /[\\\"\x00-\x1f\x7f-\uffff]/g,
    encodeString = function(s) {
        return '"' + s.replace(charToReplace, function(a) {
            var c = m[a];
            return typeof c === 'string' ? c : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"';
    },

    //<debug>
    encodeArrayPretty = function(o, newline) {
        var len = o.length,
            cnewline = newline + '   ',
            sep = ',' + cnewline,
            a = ["[", cnewline], // Note newline in case there are no members
            i;

        for (i = 0; i < len; i += 1) {
            a.push(doEncode(o[i], cnewline), sep);
        }

        // Overwrite trailing comma (or empty string)
        a[a.length - 1] = newline + ']';

        return a.join('');
    },

    encodeObjectPretty = function(o, newline) {
        var cnewline = newline + '   ',
            sep = ',' + cnewline,
            a = ["{", cnewline], // Note newline in case there are no members
            i;

        for (i in o) {
            if (!useHasOwn || o.hasOwnProperty(i)) {
                a.push(doEncode(i) + ': ' + doEncode(o[i], cnewline), sep);
            }
        }

        // Overwrite trailing comma (or empty string)
        a[a.length - 1] = newline + '}';

        return a.join('');
    },
    //</debug>

    encodeArray = function(o, newline) {
        //<debug>
        if (newline) {
            return encodeArrayPretty(o, newline);
        }
        //</debug>

        var a = ["[", ""], // Note empty string in case there are no serializable members.
            len = o.length,
            i;
        for (i = 0; i < len; i += 1) {
            a.push(doEncode(o[i]), ',');
        }
        // Overwrite trailing comma (or empty string)
        a[a.length - 1] = ']';
        return a.join("");
    },

    encodeObject = function(o, newline) {
        //<debug>
        if (newline) {
            return encodeObjectPretty(o, newline);
        }
        //</debug>

        var a = ["{", ""], // Note empty string in case there are no serializable members.
            i;
        for (i in o) {
            if (!useHasOwn || o.hasOwnProperty(i)) {
                a.push(doEncode(i), ":", doEncode(o[i]), ',');
            }
        }
        // Overwrite trailing comma (or empty string)
        a[a.length - 1] = '}';
        return a.join("");
    };

    /**
     * The function which {@link #encode} uses to encode all javascript values to their JSON representations
     * when {@link NS#USE_NATIVE_JSON} is `false`.
     * 
     * This is made public so that it can be replaced with a custom implementation.
     * @private
     * @param {Object} o Any javascript value to be converted to its JSON representation
     * @return {String} The JSON representation of the passed value.
     * @method
     */
    me.encodeValue = doEncode;

    /**
     * Encodes a Date. This returns the actual string which is inserted into the JSON string as the literal expression.
     * **The returned value includes enclosing double quotation marks.**
     *
     * The default return format is "yyyy-mm-ddThh:mm:ss".
     *
     * To override this:
     *    Ext.JSON.encodeDate = function(d) {
     *        return Ext.Date.format(d, '"Y-m-d"');
     *    };
     * @private
     * @param {Date} d The Date to encode
     * @return {String} The string literal to use in a JSON string.
     */
    me.encodeDate = function(o) {
        return '"' + o.getFullYear() + "-"
        + pad(o.getMonth() + 1) + "-"
        + pad(o.getDate()) + "T"
        + pad(o.getHours()) + ":"
        + pad(o.getMinutes()) + ":"
        + pad(o.getSeconds()) + '"';
    };

    /**
     * 解析Object、Array 或者其他对象
     * 将JSON对象解析成为JSON字符串
     * 如果浏览器JSON解析没有被使用,那么使用JS解析方法。
     * @param {Object} o 被解析的对象
     * @return {String} JSON字符串
     */
    me.encode = function(o) {
        if (!encodingFunction) {
            // setup encoding function on first access
            encodingFunction = isNative() ? JSON.stringify : me.encodeValue;
        }
        return encodingFunction(o);
    };

    /**
     * Decodes (parses) 把一个JSON字符串解析成为一个对象,如果safe 为真返回null,否则抛出一个异常.
     * @param {String} json JSON字符串
     * @param {Boolean} safe (可选的) 如果JSON是非法的，safe 为真则返回null,否则抛出一个异常.
     * @return {Object} 返回的对象
     */
    me.decode = function(json, safe) {
        if (!decodingFunction) {
            // setup decoding function on first access
            decodingFunction = isNative() ? JSON.parse : doDecode;
        }
        try {
            return decodingFunction(json);
        } catch (e) {
            if (safe === true) {
                return null;
            }
            NS.error({
                sourceClass: "NS.JSON",
                sourceMethod: "decode",
                msg: "你在解析一个非法的JSON格式: " + json
            });
        }
    };
})();
/**
 * 别名 for {@link NS.JSON#encode}
 * @member NS
 * @method encode
 * @inheritdoc NS.JSON#encode
 */
NS.encode = NS.JSON.encode;
/**
 * 别名 for {@link NS.JSON#decode}
 * @member NS
 * @method decode
 * @inheritdoc NS.JSON#decode
 */
NS.decode = NS.JSON.decode;/**
 * 自定义array工具类库
 * @class NS.Array
 * @author hawking
 */
NS.Array = {
	/**
	 * 将一个元素插入一个数组中
	 * @param {Array} array 原数组
	 * @param {Number} index 插入位子索引
	 * @param {Object} value 插入的值
     * @return {Array} 新数组
	 */
	insert : function(array, index, value) {
		// this.isArray(array);
		var newArray = [];
		// 这种用法不可多用
		for ( var i = 0; i < array.length; i++) {
			if (i == index) {
				newArray.push(array[i], value);
			} else {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 循环遍历方法 fn是个匿名回调函数 包含item（Object） index allItems()三个参数
	 * 
	 * @param {Array} array 所需遍历的数组
	 * @param {Function} fn 回调函数
	 */
	each : function(array, fn) {
		for ( var i = 0; i < array.length; i++) {
			var isreturn = fn(array[i], i);
			// isreturn 如果为undefined时  默认为false
			if (isreturn == false) {
				break;
			}
		}
	},
	/**
	 * 
	 * 替换给定数组的某个下标对应的值，并返回一个新的数组
	 * 
	 * @param {Array} array 原数组
	 * @param {Number} index 索引位置
	 * @param {Object} value 替换值
	 * @return {Array}
	 */
	replace : function(array, index, value) {
		var newArray = new Array();
		for ( var i = 0; i < array.length; i++) {
			if (index == i) {
				newArray.push(value);
			} else {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 判断一个数组中是否包含某个元素
	 * 
	 * @param {Array} array 数组
	 * @param {Object}  item 判断的元素
	 * @return {Boolean}
	 */
	contains : function(array, item) {
		for ( var i = 0; i < array.length; i++) {
			var value = array[i];
			if (value == item) {
				return true;
			}
		}
		return false;
	},
	/**
	 * 过滤数组元素中的空元素
	 * 
	 * @param {Array} array 数组
	 * @return {Array}
	 */
	clean : function(array) {
		var results = [], i = 0, ln = array.length, item;
		for (; i < ln; i++) {
			item = array[i];
			if (!this.isEmpty(item)) {
				results.push(item);
			}
		}
		return results;
	},
	/**
	 * 克隆数组
	 * 
	 * @param {Array}  array 原数组
	 * @return {Array} 返回克隆数组
	 */
	clone : function(array) {
		// var newArray = new Array();
		// for ( var i = 0; i < array.length; i++) {
		// newArray.push(array[i]);
		// }
		// return newArray;
		return Array.prototype.slice.call(array);
	},
	/**
	 * 清除对应的数组元素，返回相应的数组
	 * 
	 * @param {Array} array 需要修改的数组
	 * @param {Number} index 数组索引
	 * @param {Number} removeCount 移除的数量（从索引开始计算）
	 * @return {Array}
	 */
	erase : function(array, index, removeCount) {
		var newArray = new Array();
		for ( var i = 0; i < array.length; i++) {
			// index index+removeCount
			if (i >= index && i < (index + removeCount)) {
			} else {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 遍历每个值，当有值与scope对象一致的时候，返回true,否则返回false
	 * @private
	 * @param {Array} array
	 * @param {Function} fn
	 * @param {Object} scope
	 * @return {Boolean}
	 */
	every : function(array, fn, scope) {
		for ( var i = 0; i < array.length; i++) {
			var falsy = fn(array[i], i);
			if (falsy == scope) {
				return true;
			}
		}
		return false;
	},
	/**
	 * 
	 * 循环遍历对应的数组
	 * 
	 * @param array 所需遍历的数组
	 * @param fn 回调函数
	 * @param  scope 一般默认this,即函数本身
	 */
	forEach : function(array, fn, scope) {
       for(var i=0,len=array.length;i<len;i++){
    	   var flag = fn(i,array[i],scope||this);
    	   if(flag==false){return;}
       }
	},
	/**
	 * 判断一个元素在数组中的下标，如果数组中没有该元素，返回-1
	 * 
	 * @param {Array} array 传递来的数组
	 * @param {Object} item 匹配的数值
	 * @param {Number} from 从某个下标开始匹配，之前的下标不进行匹配
	 * @return {Number}
	 */
	indexOf : function(array, item, from) {
		var i, ln, newArray = [];
		if (typeof from == 'undefined') {
			for (i = 0, ln = array.length; i < ln; i++) {
				if (item == array[i]) {
					return i;
				}
			}
			return -1;
		} else {
			for (i = 0, ln = array.length; i < ln; i++) {
				if (i >= from) {
					newArray.push(array[i]);
				}
			}
			// get newArray
			for ( var j = 0; j < newArray.length; j++) {
				if (item == newArray[j]) {
					return j;
				}
			}
			return -1;
		}
       return -1;
	},
	/**
	 * 
	 * 找共同项
	 * 
	 * @param {Array} array1
	 * @param {Array} array2
	 * @param {Array} etc
	 * @return {Array}
	 */
	intersect : function(array1, array2, etc) {
		// return this.complexElement(array1, array2, etc);
		var newArray = [];
		for ( var i = 0, len = array1.length; i < len; i++) {
			for ( var j = 0, length = array2.length; j < length; j++) {
				if (array1[i] == array2[j]) {
					newArray.push(array1[i]);
				}
			}
		}
		// 再去重
		var map = {}, result = [];
		for ( var i = 0, len = newArray.length; i < len; i++) {
			if (!map[newArray[i]]) {
				result.push(newArray[i]);
			}
		}
		return result;
	},
	/**
	 * 返回数组内数值最大数
	 * 
	 * @param {Array} array
	 * @return {Number}
	 */
	max : function(array) {
		var max = 0;
		for ( var i = 0; i < array.length; i++) {
			if (array[i] > array[i + 1]) {
				max = array[i];
				array[i] = array[i + i];
				array[i + 1] = max;
			}
		}
		return max;
	},
	/**
	 * 获取数组内最小值
	 * 
	 * @param {Array} array
	 * @return {Number}
	 */
	min : function(array) {
		var min = null;
		for ( var i = 0; i < array.length; i++) {
			if (array[i] < array[i + 1]) {
				min = array[i];
				array[i] = array[i + 1];
				array[i + 1] = array[i];
			}
		}
		return min;
	},
	/**
	 * 计算数组内元素平均值
	 * 
	 * @param {Array} array
	 * @return {Number}
	 */
	mean : function(array) {
		var sum = 0;
		var length = array.length;
		for ( var i = 0; i < length; i++) {
			sum += array[i];
		}
		/*
		 * this.each(array,function(value){ sum+=value; });
		 */
		return parseFloat(sum / length);
	},
	/**
	 * 
	 * 合并多个数组到一个一个新数组,并返回该新数组
	 * 
	 * @param {Array} array1
	 * @param {Array} array2
	 * @param {Array} etc
	 */
	merge : function(array1, array2, etc) {
		// 思路受困
		/*
		 * var args = arguments[0]; var sumArrayLength = args.length; var
		 * newArray = newArray(); for(var i = 0; i<sumArrayLength;i++){ var
		 * Oarray = args[i]; for(var j = 0;j<Oarray.length;j++){
		 * newArray.push(Oarray[j]); } } return newArray;
		 */
		var newArray = new Array();
		if (array1) {
			for ( var i = 0; i < array1.length; i++) {
				newArray.push(array1[i]);
			}
		}
		if (array2) {
			for ( var j = 0; j < array2.length; j++) {
				newArray.push(array2[j]);
			}
		}
		return newArray;
	},
	/**
	 * 
	 * pluck方法 返回对象数组，中具有相同键对应的值的集合。
	 * 
	 * @param {Array} array是json类似的数组集合
	 * @param {Object} propertyName是json的key
	 * @return {Array} 返回的是key对应的值的数组集合
	 */
	pluck : function(array, propertyName) {
		var newArray = [], i, ln;
		for (i = 0, ln = array.length; i < ln; i++) {
			newArray.push(array[i][propertyName]);
		}
		return newArray;
	},
	/**
	 * 移除数组中某个相同的对象，并返回一个新数组
	 * 
	 * @param {Array} array
	 * @param {Object} item 待移除项
	 * @return {Array}
	 */
	remove : function(array, item) {
		var i, ln, newArray = [];
		for (i = 0, ln = array.length; i < ln; i++) {
			if (item != array[i]) {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 提取一个数组中的开始下标和结束下标中间的元素，并构成一个新数组，返回这个新数组
	 * 
	 * @param {Array} array
	 * @param {Number} begin 开始下标
	 * @param {Number}  end 结束下标
	 * @return {Array}
	 */
	slice : function(array, begin, end) {
		var i, ln, newArray = [];
		for (i = 0, ln = array.length; i < ln; i++) {
			if (i >= begin && i <= end) {
				newArray.push(array[i]);
			}
		}
		return newArray;
		//return Array.prototype.slice(array,begin,end);
	},
	/**
	 * 
	 * splice方法
	 * 
	 * @param {Array} array 传递的原数组
	 * @param {Number} index 开始索引
	 * @param {Number} removeCount 从index开始，移除的数量
	 * @param {Object} elements  需要添加的元素
	 * @return {Array}
	 */
	splice : function(array, index, removeCount, elements) {
		var i, ln, newArray = [];
		var j = 0;
		for (i = 0, ln = array.length; i < ln; i++) {

			if (i >= index && i < (index + removeCount)) {
				// 在多个循环内之添加一次
				j++;
				if (typeof elements != 'object'
						&& typeof elements != 'undefined') {
					if (j == 1) {
						newArray.push(elements);
					}
				} else {
					// 暂不处理
				}
			} else {
				newArray.push(array[i]);
			}
		}
		return newArray;
	},
	/**
	 * 数组内所有元素总和
	 * 
	 * 		NS.Array.sum([1,2,3]);//6
	 * @param {Array} array number类型元素的数组
	 * @return {Number}
	 */
	sum : function(array) {
		var sum = 0, i = 0, ln = array.length, item;
		for (; i < ln; i++) {
			item = array[i];
			sum += item;
		}
		return sum;
	},
	/**
	 * 将传递来的数组，对应的生成统一的新的数组，且该数组的属性都是唯一的
	 * 
	 * 		var array = NS.Array.union([1,2,3],[2,4,5]);
	 *      console.log(array);//[1,2,3,4,5]
	 * @param {Array} array1
	 * @param {Array} array2
	 * @param {Array} etc
	 * @return {Array}
	 */
	union : function(array1, array2, etc) {
		var array = [];
		if (array1 instanceof Array && array2 instanceof Array) {
			array1.concat(array2);
			array = this.unique(array1);
		}
		return array;
	},
	/**
	 * 返回唯一的的元素(非重复元素)
	 * 
	 * 		NS.Array.unique([1,2,2,3,4,5]);//[1,3,4,5]
	 * @param {Array} array 
	 * @return {Array}
	 */
	unique : function(array) {
		var newArray = [], i, ln, map = {};
		for (i = 0, ln = array.length; i < ln; i++) {
			var oneValue = array[i];
			if (!map[oneValue]) {
				map[oneValue] = 1;
			} else {
				map[oneValue] += 1;
			}
		}
		for ( var i in map) {
			if (map[i] == 1) {
				newArray.push(i);
			}
		}
		return newArray;
	},
	/**
	 * 找到两个数组中重复元素（交集）
	 * 
	 * 		NS.Array.complexElements([1,2,2,3,4,5]);//[2]
	 * @param {Array} array1
	 * @param {Array} array2
	 * @param {Array} etc
	 * @return {Array}
	 */
	complexElements : function(array1, array2, etc) {
		var newArray = [], i, ln, map = {};
		for (i = 0, ln = array.length; i < ln; i++) {
			var oneValue = array[i];
			if (!map[oneValue]) {
				map[oneValue] = 1;
			} else {
				map[oneValue] += 1;
			}
		}
		for ( var i in map) {
			if (map[i] > 1) {
				newArray.push(i);
			}
		}
		return newArray;
	},
    /***
     * 删除一个数组中指定下标的元素
     * @param {Array} array 数组
     * @param {Number} index 数据下标
     * @return {Array}
     */
    removeAt : function(array,index){
        array.splice(index,1);
        return array;
    },
    /**
     * 可以被枚举的对象(数组对象，字符串对象等)
     * @param {Object} iterable 迭代对象
     * @param {Number} start 开始元素个数
     * @param {Number} end 结束元素个数
     * @return
     */
    toArray: function(iterable, start, end){
    	if (!iterable || !iterable.length) {
			return [];
		}
		if (typeof iterable === 'string') {
			iterable = iterable.split('');
		}
		if (true) {
			return Array.prototype.slice.call(iterable, start || 0, end || iterable.length);
		}
		var array = [], i;
		start = start || 0;		end = end ? ((end < 0) ? iterable.length + end : end) : iterable.length;

		for (i = start; i < end; i++) {
			array.push(iterable[i]);
		}
		return array;
    }
    
};
/**
 * 
 * 日期工具类
 * 
 * @author haw_king
 * @class  NS.Date
 */
NS.Date = {
	/**
	 * 返回当前时间戳(毫秒)
	 * 
	 * @return {Number} 当前时间戳
	 */
	now : Date.now || function() {
		return +new Date();
	},
	/**
	 * 
	 * 转换为字符串
	 * 
	 * @param {Date} date
	 * @returns {String}
	 */
	toString : function(date) {
		if (date instanceof Date) {
			return date.toString();
		} else if (date instanceof String) {
			return date;
		}else{
			return new String(date);
		}
	},
	/**
	 * 
	 * 得到与过去时间差
	 * 
	 */
	getElapsed : function() {

	},
	/**
	 * 
	 * 得到今天日期（Y-m-d）
	 * 
	 *    var today = NS.Date.getToday();//当前日期
	 *     
	 * @returns {String} 返回当前日期的字符串表示 默认无参数 
	 */
	getToday : function() {
		var arg = arguments[0], year, month, _date;
		if (arg) {
			year = arg.year;
			month = arg.month;
			_date = arg.date;
		} else {
			var d = new Date();
			year = d.getYear() + 1900;
			month = d.getMonth() + 1;
			_date = d.getDate();
		}
		month = month < 10 ? ('0' + month) : month;
		_date = _date < 10 ? ('0' + _date) : _date;
		return year + '-' + month + '-' + _date;
	},
	/**
	 * 
	 * 得到指定或当前日期的前后相距天数的日期
	 * 
	 *     NS.Date.beforeOrAfterDate(-1,'2013-01-31');//'2013-01-30'
	 *     NS.Date.beforeOrAfterDate(1,'2013-01-31');//'2013-02-01'
	 *     
	 * @param {Number} numberDays 
	 *            天数(-31~31)如果超过这个范围 暂不考虑
	 *            正数指向后相距的天数，负数指向前相距的天数
	 * @param {String} date 
	 *            日期 暂定格式'Y-m-d';
	 * @returns {String} 返回指定日期的前后指定天数的日期，默认不填写日期为当前日期
	 */
	beforeOrAfterDate : function(numberDays, date) {
		var year, month, _date;
		if (!date) {
			date = new Date();
			year = date.getYear() + 1900;
			month = date.getMonth() + 1;
			_date = date.getDate();
		} else {
			year = this.getYear(date);
			month = this.getMonth(date);
			_date = this.getDate(date);
		}

		if (-31 < numberDays && numberDays < 0) {
			// 负数则指向前numberDays天
			_date = _date + numberDays;// 如果为负数 则加上 上一个月份的天数（判断平年或者闰年）以及月份减1
			// 如果月份减1后小于0 则加上12 ， 年份减一
			if (_date <= 0) {
				_date = 12 + _date;
				year = year - 1;
			}
		} else if (numberDays >= 0 && numberDays < 31) {
			// numberDays大于等于0时
			// 包含 年份的更改 月份的更改 月份日期的更改
			// 判断月份先 如果大约这个月的天数则月份加1 再判断月份是否大于12 大于则年份加1
			_date = _date + numberDays;
			var monthDays = this.getMonthDays(date);
			if (_date > monthDays) {
				_date = _date - monthDays;
				month += 1;
				if (month > 12) {
					year += 1;
				}
			}
		} else {
			// 不符合本方法范围内
			return null;
		}
		// 根据指定日期 找出其之前的日期
		return this.getToday({
			year : year,
			month : month,
			date : _date
		});
	},
	/**
	 * 
	 * 是否是闰年
	 * 
	 * 			NS.Date.isLeapYear('2013-01-10');//false
	 *          NS.Date.isLeapYear('2012-02-29');//true 
	 * @param {String} date 
	 *            格式要求尽量是 'Y-m-d'
	 * @returns {Boolean} true表示为闰年，flase则表示为平年
	 */
	isLeapYear : function(date) {
		if (!date) {
			date = new Date();
		}
		var year = this.getYear(date);
		return year % 400 == 0 || (year % 4 == 0 && year % 100 != 0);// 根据闰年的算法所得
	},
	/**
	 * 
	 * 得到所在日期的年份
	 * 
	 *     NS.Date.getYear();//如当前年份为2013
	 * @param {String} date
	 *            日期(包含年-月-日)
	 * @returns {Number} 返回所在日期的年份，默认返回当前年份
	 */
	getYear : function(date) {
		if (!date) {
			return (new Date()).getYear() + 1900;
		}
		return (new Date(date)).getYear() + 1900;
	},
	/**
	 * 
	 * 得到所在日期的月份
	 * 
	 *     NS.Date.getMonth();//如当前月份为 1
	 * @param {String} date
	 *            日期(包含年-月-日)
	 * @returns {Number} 返回所传递的日期的月份，默认返回当前日期的月份
	 */
	getMonth : function(date) {
		if (!date) {
			return (new Date()).getMonth() + 1;
		}
		return Number((new Date(date)).getMonth()) + 1;
	},
	/**
	 * 
	 * 得到日期
	 *  
	 *     NS.Date.getDate();//如当前日期为30
	 * @param {String}  date
	 *            日期(包含年-月-日)
	 * @returns {Number} 返回指定日期的所在月份的日期，默认返回当前月的日期
	 */
	getDate : function(date) {
		if (date) {
			return (new Date(date)).getDate();
		}
		return (new Date()).getDate();
	},
	/**
	 * 
	 * 得到日期下的周几
	 *  
	 *     NS.Date.getDay();//如当前为周三 即返回3
	 * @param {String} date
	 *            指定日期
	 * @returns {Number} 返回周几，默认当前周数
	 */
	getDay : function(date) {
		if (!date) {
			return (new Date()).getDay();
		}
		return (new Date(date)).getDay();
	},
	/**
	 * 
	 * 得到日期月份下的月份日期数
	 *  
	 *     NS.Date.getMonthDays();//如当前月份的天数为31
	 * @param {String} date 
	 *            日期(包含年-月-日)
	 * @returns {Number} 返回指定日期的月份，默认当前月份下的天数
	 */
	getMonthDays : function(date) {
		if (!date) {
			date = this.getToday();
		}
		var _monthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];// 月份天数维护
		if (this.isLeapYear(date)) {
			_monthDays[1] = 29;
		}
		return _monthDays[this.getMonth(date) - 1];
	},
    /**
     *
     * @param {Date} date
     * @param {String} format Y-m-d : H:i:s
     * @returns {String|*}
     */
    format : function(date,format){
        return Ext.Date.format(date,format);
    }
};
/**
 * @class NS.Object
 * @author haw_king
 *    Object 扩展类
 */
NS.Object = (function() {
	var TemplateClass = function() {
	};
	return {
		/**
		 * 返回一个新的对象与给定对象的原型链。
		 * 
		 * @param {Object}
		 *            object 含有新对原型链的对象
		 */
		chain : function(object) {
			TemplateClass.prototype = object;
			var result = new TemplateClass();
			TemplateClass.prototype = null;
			return result;
		},
		/**
		 * 转换成`name` - `value`对到一个数组中的对象支持嵌套结构。构建查询字符串.
		 * 
		 * 
		 * 		var objects = NS.Object.toQueryObjects('hobbies', ['reading',
		 * 'cooking', 'swimming']); // objects then equals: [ { name: 'hobbies',
		 * value: 'reading' }, { name: 'hobbies', value: 'cooking' }, { name:
		 * 'hobbies', value: 'swimming' }, ];
		 * 
		 * 		var objects = NS.Object.toQueryObjects('dateOfBirth', { day: 3,
		 * month: 8, year: 1987, extra: { hour: 4 minute: 30 } }, true); //
		 * Recursive // objects then equals: [ { name: 'dateOfBirth[day]',
		 * value: 3 }, { name: 'dateOfBirth[month]', value: 8 }, { name:
		 * 'dateOfBirth[year]', value: 1987 }, { name:
		 * 'dateOfBirth[extra][hour]', value: 4 }, { name:
		 * 'dateOfBirth[extra][minute]', value: 30 }, ];
		 * 
		 * @param {String}
		 *            name
		 * @param {Object/Array}
		 *            value
		 * @param {Boolean}
		 *            [recursive=false] True to traverse object recursively
		 * @return {Array}
		 */
		toQueryObjects : function(name, value, recursive) {
			var self = ExtObject.toQueryObjects, objects = [], i, ln;

			if (Ext.isArray(value)) {
				for (i = 0, ln = value.length; i < ln; i++) {
					if (recursive) {
						objects = objects.concat(self(name + '[' + i + ']',
								value[i], true));
					} else {
						objects.push({
							name : name,
							value : value[i]
						});
					}
				}
			} else if (Ext.isObject(value)) {
				for (i in value) {
					if (value.hasOwnProperty(i)) {
						if (recursive) {
							objects = objects.concat(self(name + '[' + i + ']',
									value[i], true));
						} else {
							objects.push({
								name : name,
								value : value[i]
							});
						}
					}
				}
			} else {
				objects.push({
					name : name,
					value : value
				});
			}

			return objects;
		},

		/**
		 * 接受一个对象，并且将它转换为一个编码的查询字符串.
		 * 
		 * 
		 * 		NS.Object.toQueryString({foo: 1, bar: 2}); // returns
		 * "foo=1&bar=2" NS.Object.toQueryString({foo: null, bar: 2}); //
		 * returns "foo=&bar=2" NS.Object.toQueryString({'some price':
		 * '$300'}); //returns "some%20price=%24300"
		 * 		NS.Object.toQueryString({date: new Date(2011, 0, 1)}); //
		 * returns "date=%222011-01-01T00%3A00%3A00%22"
		 * 		NS.Object.toQueryString({colors: ['red', 'green', 'blue']}); //
		 * returns "colors=red&colors=green&colors=blue"
		 * 
		 * 
		 * 		NS.Object.toQueryString({ username: 'Jacky', dateOfBirth: { day:
		 * 1, month: 2, year: 1911 }, hobbies: ['coding', 'eating',
		 * 'sleeping',['nested', 'stuff']] }, true); // returns the following
		 * string (broken down and url-decoded for ease of reading purpose): //
		 * username=Jacky //
		 * &dateOfBirth[day]=1&dateOfBirth[month]=2&dateOfBirth[year]=1911 //
		 * &hobbies[0]=coding&hobbies[1]=eating&hobbies[2]=sleeping&hobbies[3][0]=nested&hobbies[3][1]=stuff
		 * 
		 * @param {Object}
		 *            object The object to encode
		 * @param {Boolean}
		 *            [recursive=false] Whether or not to interpret the object
		 *            in recursive format. (PHP / Ruby on Rails servers and
		 *            similar).
		 * @return {String} queryString
		 */
		toQueryString : function(object, recursive) {
			var paramObjects = [], params = [], i, j, ln, paramObject, value;

			for (i in object) {
				if (object.hasOwnProperty(i)) {
					paramObjects = paramObjects.concat(ExtObject
							.toQueryObjects(i, object[i], recursive));
				}
			}

			for (j = 0, ln = paramObjects.length; j < ln; j++) {
				paramObject = paramObjects[j];
				value = paramObject.value;

				if (Ext.isEmpty(value)) {
					value = '';
				} else if (Ext.isDate(value)) {
					value = Ext.Date.toString(value);
				}

				params.push(encodeURIComponent(paramObject.name) + '='
						+ encodeURIComponent(String(value)));
			}

			return params.join('&');
		},

		/**
		 * 
		 * 将一个查询字符串转换回对象.
		 * 
		 * 		NS.Object.fromQueryString("foo=1&bar=2"); // returns {foo: 1,
		 * bar: 2} NS.Object.fromQueryString("foo=&bar=2"); // returns
		 * {foo: null, bar: 2}
		 * 		NS.Object.fromQueryString("some%20price=%24300"); // returns
		 * {'some price': '$300'}
		 * 		NS.Object.fromQueryString("colors=red&colors=green&colors=blue");
		 * //returns {colors: ['red', 'green', 'blue']}
		 * 
		 * 		NS.Object.fromQueryString(
		 * "username=Jacky&"+"dateOfBirth[day]=1&dateOfBirth[month]=2&dateOfBirth[year]=1911&"+"hobbies[0]=coding&hobbies[1]=eating&hobbies[2]=sleeping&"+"hobbies[3][0]=nested&hobbies[3][1]=stuff",
		 * true);// returns { username: 'Jacky', dateOfBirth: { day: '1', month:
		 * '2',year: '1911' }, hobbies: ['coding', 'eating', 'sleeping',
		 * ['nested','stuff']] }
		 * 
		 * @param {String}
		 *            查询字符串查询字符串进行解码
		 * @param {Boolean}
		 *            [recursive=false] 是否要进行转码. 支持此格式的PHP / Ruby on
		 *            Rails的服务器和类似的。
		 * @return {Object}
		 */
		fromQueryString : function(queryString, recursive) {
			var parts = queryString.replace(/^\?/, '').split('&'), object = {}, temp, components, name, value, i, ln, part, j, subLn, matchedKeys, matchedName, keys, key, nextKey;

			for (i = 0, ln = parts.length; i < ln; i++) {
				part = parts[i];

				if (part.length > 0) {
					components = part.split('=');
					name = decodeURIComponent(components[0]);
					value = (components[1] !== undefined) ? decodeURIComponent(components[1])
							: '';

					if (!recursive) {
						if (object.hasOwnProperty(name)) {
							if (!Ext.isArray(object[name])) {
								object[name] = [ object[name] ];
							}

							object[name].push(value);
						} else {
							object[name] = value;
						}
					} else {
						matchedKeys = name.match(/(\[):?([^\]]*)\]/g);
						matchedName = name.match(/^([^\[]+)/);

						// <debug error>
						if (!matchedName) {
							throw new Error(
									'[Ext.Object.fromQueryString] Malformed query string given, failed parsing name from "'
											+ part + '"');
						}
						// </debug>

						name = matchedName[0];
						keys = [];

						if (matchedKeys === null) {
							object[name] = value;
							continue;
						}

						for (j = 0, subLn = matchedKeys.length; j < subLn; j++) {
							key = matchedKeys[j];
							key = (key.length === 2) ? '' : key.substring(1,
									key.length - 1);
							keys.push(key);
						}

						keys.unshift(name);

						temp = object;

						for (j = 0, subLn = keys.length; j < subLn; j++) {
							key = keys[j];

							if (j === subLn - 1) {
								if (Ext.isArray(temp) && key === '') {
									temp.push(value);
								} else {
									temp[key] = value;
								}
							} else {
								if (temp[key] === undefined
										|| typeof temp[key] === 'string') {
									nextKey = keys[j + 1];

									temp[key] = (Ext.isNumeric(nextKey) || nextKey === '') ? []
											: {};
								}

								temp = temp[key];
							}
						}
					}
				}
			}

			return object;
		},
		/**
		 * 迭代遍历对象中的所有元素，可以控制回调函数里返回false，来停止对该对象的遍历。
		 * 
		 * 			 var person = { name: 'Jacky' hairColor: 'black' loves:
		 *          ['food', 'sleeping', 'wife'] };
		 * 
		 * 			NS.Object.each(person, function(key, value, myself) {
		 * console.log(key + ":" + value);
		 * 
		 * if (key === 'hairColor') { return false; // stop the iteration } });
		 * 
		 * @param {Object}
		 *            object 需迭代的对象
		 * @param {Function}
		 *            fn 回调函数
		 * @param {Object}
		 *            [scope] scope默认应是this,即回调函数自身
		 */
		each : function(object, fn, scope) {
			for ( var property in object) {
				if (object.hasOwnProperty(property)) {
					if (fn.call(scope || object, property, object[property],
							object) === false) {
						return;
					}
				}
			}
		},

		/**
		 * 合并source的对象，但不包含其子节点
		 * 
		 * 		var extjs = { companyName: 'Ext JS', products: ['Ext JS', 'Ext GWT',
		 * 'Ext Designer'], isSuperCool: true, office: { size: 2000, location:
		 * 'Palo Alto', isFun: true } };
		 * 
		 * 		var newStuff = { companyName: 'Sencha Inc.', products: ['Ext JS',
		 * 'Ext GWT', 'Ext Designer', 'Sencha Touch', 'Sencha Animator'],
		 * office: { size: 40000, location: 'Redwood City' } };
		 * 
		 *		var sencha = NS.Object.merge(extjs, newStuff); // extjs and
		 * sencha then equals to { companyName: 'Sencha Inc.', products: ['Ext
		 * JS', 'Ext GWT', 'Ext Designer', 'Sencha Touch', 'Sencha Animator'],
		 * isSuperCool: true, office: { size: 40000, location: 'Redwood City'
		 * isFun: true } }
		 * 
		 * @param {Object}
		 *            object 任意数量的对象集合
		 * @return {Object} merged 返回一个合并的所有对象被创建的对象
		 */
		merge : function(source) {
			var i = 1, ln = arguments.length, mergeFn = ExtObject.merge, cloneFn = Ext.clone, object, key, value, sourceKey;

			for (; i < ln; i++) {
				object = arguments[i];

				for (key in object) {
					value = object[key];
					if (value && value.constructor === Object) {
						sourceKey = source[key];
						if (sourceKey && sourceKey.constructor === Object) {
							mergeFn(sourceKey, value);
						} else {
							source[key] = cloneFn(value);
						}
					} else {
						source[key] = value;
					}
				}
			}

			return source;
		},

		/**
		 * @private
		 * @param source
		 */
		mergeIf : function(source) {
			var i = 1, ln = arguments.length, cloneFn = Ext.clone, object, key, value;

			for (; i < ln; i++) {
				object = arguments[i];

				for (key in object) {
					if (!(key in source)) {
						value = object[key];

						if (value && value.constructor === Object) {
							source[key] = cloneFn(value);
						} else {
							source[key] = value;
						}
					}
				}
			}

			return source;
		},

		/**
		 * 通过value查找第一次出现的key,如果该对象中无该值,则返回null
		 * 
		 * 		var person = { name: 'Jacky', loves: 'food' };
		 * 
		 * 		alert(NS.Object.getKey(person, 'food')); // alerts 'loves'
		 * 
		 * @param {Object}
		 *            object
		 * @param {Object}
		 *            value 要找到值
		 */
		getKey : function(object, value) {
			for ( var property in object) {
				if (object.hasOwnProperty(property)
						&& object[property] === value) {
					return property;
				}
			}

			return null;
		},

		/**
		 * 以数组的形式返回该对象里的value集合
		 * 
		 * 		var values = NS.Object.getValues({ name: 'Jacky', loves: 'food'
		 * }); // ['Jacky', 'food']
		 * 
		 * @param {Object}
		 *            object
		 * @return {Array} 返回这个对象的value结合数组
		 */
		getValues : function(object) {
			var values = [], property;

			for (property in object) {
				if (object.hasOwnProperty(property)) {
					values.push(object[property]);
				}
			}

			return values;
		},
		/**
		 * 以数组的形式返回这个对象里的所有key的集合。
		 * 
		 * 		var values = NS.Object.getKeys({ name: 'Jacky', loves: 'food'
		 * }); // ['name', 'loves']
		 * 
		 * @param {Object} object 要操作的对象
		 * @return {String[]} 返回这个对象的键的数组集合
		 * 
		 */
		getKeys : (typeof Object.keys == 'function') ? function(object) {
			if (!object) {
				return [];
			}
			return Object.keys(object);
		} : function(object) {
			var keys = [], property;
			for (property in object) {
				if (object.hasOwnProperty(property)) {
					keys.push(property);
				}
			}

			return keys;
		},

		/**
		 * 得到这个对象的元素长度
		 * 
		 * 		var size = NS.Object.getSize({ name: 'Jacky', loves: 'food' }); //
		 * size 等于 2
		 * 
		 * @param {Object}
		 *            object
		 * @return {Number} 集合长度
		 */
		getSize : function(object) {
			var size = 0, property;

			for (property in object) {
				if (object.hasOwnProperty(property)) {
					size++;
				}
			}

			return size;
		},

		/**
		 * @private
		 */
		classify : function(object) {
			var prototype = object, objectProperties = [], propertyClassesMap = {}, objectClass = function() {
				var i = 0, ln = objectProperties.length, property;

				for (; i < ln; i++) {
					property = objectProperties[i];
					this[property] = new propertyClassesMap[property];
				}
			}, key, value;

			for (key in object) {
				if (object.hasOwnProperty(key)) {
					value = object[key];

					if (value && value.constructor === Object) {
						objectProperties.push(key);
						propertyClassesMap[key] = ExtObject.classify(value);
					}
				}
			}

			objectClass.prototype = prototype;

			return objectClass;
		},
		pluck:function(obj,callback){
			for(var i in obj){
				if(i)callback(i,obj[i],obj);
			}
		}
	};
})();/**
 * @class NS.String
 * 字符串操作扩展工具类。 注意：此类并非扩展自原生String的prototype属性。
 * @author zhangzg
 */
NS.String = (function() {
	var trimReg 		= 	/(^\s*)|(\s*$)/g,
		formatRe      	=   /\{(\d+)\}/g;
	return {
		/**
		 * 去除字符串的前后空格。
		 * @method trim
		 * @param {String} str 要被操作的字符串。
		 * @return {String} 去除前后空格后的字符串。
		 */
		trim : function(str) {
			return str.replace(trimReg, "");
		},
		/**
		 * 根据指定的CSS样式生成相应样式的文字。
		 * @method createByStyle
         * @param {String} str 需要被加上样式的字符串。
		 * @param {String} style 样式的字符串形式。
		 * @return {String} 以span标签包括的具有指定样式的字符串。
		 */
		createByStyle : function(str, style) {
			return "<span style=\"".concat(style, "\">", str, "</span>");
		},
		/**
		 * 比较2个字符串是否相等，忽略大小写。
		 * @method equals
		 * @param {String} str1 第一个字符串。
		 * @param {String} str2 第二个字符串。
		 * @return {Boolean} 相等返回true，不相等返回false。
		 */
		equals : function(str1, str2) {
			return str1.toLowerCase() == str2.toLowerCase();
		},
		/**
		 * 比较2个字符串的大小，区分大小写。
         *      第一个字符串小于第二个字符串返回 -1
         *      第一个字符串等于第二个字符串返回 0
         *      第一个字符串大于第二个字符串返回 1
		 * @method compare
		 * @param {String} str1 第一个字符串。
		 * @param {String} str2 第二个字符串。
		 * @return {Number}
		 */
		compare : function(str1, str2) {
			if (str1 < str2) {
				return -1;
			} else if (str1 == str2) {
				return 0;
			} else {
				return 1;
			}
		},
		/**
		 * 比较2个字符串的大小，不区分大小写。
		 * @method compareByLowercase
         * 第一个字符串小于第二个字符串返回 -1
         * 第一个字符串等于第二个字符串返回 0
         * 第一个字符串大于第二个字符串返回 1
		 * @param {String} str1 第一个字符串。
		 * @param {String} str2 第二个字符串。
		 * @return {Number}
		 */
		compareByLowercase : function(str1, str2) {
			return this.compare(str1.toLowerCase(), str2.toLowerCase());
		},
		/**
         * 在指定的字符串左侧追加指定的字符串。追加的几个指定的字符取决于最后的字符串长度。
         * 
         * 		var str ='the string will be added..';
         * 		NS.String.leftPad(str,28,'a');
         * 		此时str的值为aathe string will be added..
         * 
         * @param {String} string  被追加的字符串
         * @param {Number} size 返回字符串的总长度
         * @param {String} character 追加字符串，不传递该参数则为" "字符串。
         * @return {String} 经过追加后的字符串。
         */
		leftPad: function(string, size, character) {
            var result = String(string);
            character = character || " ";
            while (result.length < size) {
                result = character + result;
            }
            return result;
        },
        /**
		 * 在url字符串后追加字符串。
		 * 
		 * 		var url ='http://localhost:9000/MOSDC';
		 * 		url = NS.String.urlAppend(url,'aaa=101');
		 * 		此时url的值为 http://localhost:9000/MOSDC?aaa=101
		 * 
		 * @param {String} url 要被追加的url字符串。
		 * @param {String} string 追加到url后的字符串。
		 * @return {String} 结果字符串。
		 */
		urlAppend : function(url, string) {
			if (!NS.isEmpty(string)) {
				return url + (url.indexOf('?') === -1 ? '?' : '&') + string;
			}
			return url;
		},
        /**
         * 以首字母大写转换给定字符串。
         * @param {String} string 指定字符串。
         * @return {String} 大写开头的字符串。
         */
        capitalize: function(string) {
            return string.charAt(0).toUpperCase() + string.substr(1);
        },
        /**
         * 首字母小写转换给定字符串。
         * @param {String} string 指定字符串。
         * @return {String} 小写开头的字符串。
         */
        uncapitalize: function(string) {
            return string.charAt(0).toLowerCase() + string.substr(1);
        },
        /**
         * 如果字符串的长度超过了指定的长度，则用省略号替换后三个字符。
         * @param {String} value 要被转换的字符串。
         * @param {Number} len 允许的最大长度。
         * @param {Boolean} word 如果值为true则从断句标点符号开始补省略号。
         * @return {String} 被转化的字符串。
         */
        ellipsis: function(value, len, word) {
            if (value && value.length > len) {
                if (word) {
                    var vs = value.substr(0, len - 2),
                    index = Math.max(vs.lastIndexOf(' '), vs.lastIndexOf('.'), vs.lastIndexOf('!'), vs.lastIndexOf('?'));
                    if (index !== -1 && index >= (len - 15)) {
                        return vs.substr(0, index) + "...";
                    }
                }
                return value.substr(0, len - 3) + "...";
            }
            return value;
        },
        /**
         * Allows you to define a tokenized string and pass an arbitrary number of arguments to replace the tokens.  Each
         * token must be unique, and must increment in the format {0}, {1}, etc.  Example usage:
         * 
			    var cls = 'my-class', text = 'Some text';
			    var s = Ext.String.format('&lt;div class="{0}">{1}&lt;/div>', cls, text);
    			// s now contains the string: '&lt;div class="my-class">Some text&lt;/div>'
    	 *
         * @param {String} string The tokenized string to be formatted
         * @param {String} value1 The value to replace token {0}
         * @param {String} value2 Etc...
         * @return {String} The formatted string
         */
        format: function(format) {
            var args = NS.Array.toArray(arguments, 1);
            return format.replace(formatRe, function(m, i) {
                return args[i];
            });
        },
        /**
         * 对format的扩展(本想更改原生方法,但后来想想就不动了)
         * 更改办法:
         *    var args_ = arguments[1];//但如果里面包含数组和字符对象的话,就不好了
         *    if(args_不是数组){
         *       args_ = NS.Array.toArray(arguments,1);
         *    }
         * @param {String} str 待转换字符串
         * @param {Array} arr 数组对象
         * @return {String} 转换后字符串
         */
        format2:function(str,arr){
        	return str.replace(formatRe, function(m, i) {
                return arr[i];
            });
        },
		/**
		 * 是否字符串以指定字符串开始。
		 * @method isStartWith
		 * @param {String} str 被判断的字符串。
		 * @param {String} subStr 判断字符串。
		 * @return {Boolean} 以指定字符串开头则返回true 否则返回false
		 */
		isStartWith : function(str, subStr) {
			return str.substr(0, subStr.length) == subStr;
		},
		/**
		 * 是否字符串以指定字符串结束。
		 * @method isEndWith
		 * @param {String} str 被判断的字符串。
		 * @param {String} subStr 判断字符串。
		 * @return {Boolean} 以指定字符串结尾则返回true 否则返回false
		 */
		isEndWidth : function(str, subStr) {
			return str.substr(str.length - subStr.length) == subStr;
		},
        /***************************************************************************
         * 移除字符串中的HTML标签,并返回新的字符串
         *
         * @param {String} str 包含html标签的字符串
         * @return {String}
         */
        delHtmlTag : function(str) {
            return str.replace(/<\/?.+?>/g, "");// 去掉所有的html标记
        },
        /**
         * 首字母转换大写方法
         * @param str
         * @return
         */
        upFirstWord:function(str){
        	 return str.substr(0,1).toUpperCase().concat(str.substr(1,str.length-1));
        }
	}
})();/**
 * @class NS.ClassManager
 * 管理在框架内所有的定义的类 
 * @author wangyongtai
 */
NS.ClassManager = (function() {
	var classmap = {};
	/**
	 * 将一个引用标识到对应的命名空间下
	 */
	var setNameSpace = function(nameSpace, Class) {
			var splits, i = 0,v="", upObj = window;
			splits = nameSpace.split('.');
			if(splits.length == 1){
			   window[nameSpace] = Class;
			}else{
			   if(!window[splits[0]])
				   window[splits[0]] = {};
			   for (i; i < splits.length; i++) {
				    v = splits[i];
					if(i == splits.length-1){
					   upObj[v] = Class;
					}else{
					   if(!upObj[v])
						   upObj[v] = {};
					   upObj = upObj[v];
					}
			   }
			}
	};
	return {
		/**
		 * 将一个类注册到类名对应的命名空间下
		 * @param classname
		 * @param Class
		 */
		register : function(classname, Class) {
			classmap[classname] = Class || {};
			setNameSpace(classname, Class);
		},
        unregister : function(classname){
            if(classname){
                delete classmap[classname];
            }
        },
		/**
		 * 通过类名获取类
		 * @param {String} classname 类名
		 */
		get : function(classname) {
			return classmap[classname]
		},
        /***
         * 通过命名空间获取类
         */
        getClassByNameSpace : function(classname){
            //if(classname)return null;
            var splits, i = 0, v, upObj = window;
            splits = classname.split('.');
            for (i; i < splits.length; i++) {
                v = splits[i];
                if(i == splits.length-1)
                    return upObj[v];
                upObj = upObj[v];
            }
            return null;
        },
		/**
		 * 通过提供的对象获取类
		 * @param {Object} obj
		 * @return {NS.Base} class
		 */
		getClass : function(obj) {
			return obj && obj.self || null;
		},
		/**
		 * 通过提供的对象获取其类名
		 * @param {Object}
		 *            obj
		 * @return {NS.Base} class
		 */
		getName : function(obj) {
			return obj && obj.$classname || '';
		},
		/**
		 * 获取父类实例，用于继承
		 * @param {Object}
		 *            o prototype of superClass
		 */
		getSuperInstance : function(o) {
			var F = function() {
			};
			F.prototype = o.prototype;
			return new F();
		},
		/**
		 * 继承一个类，并通过配置参数重写
		 * @param {NS.Base} superClass
		 *            父类
		 * @param {Object} config
		 *            创建类的参数
		 */
		extend : function(superClass, config) {
			var extend = superClass.$classname;
			config.extend = extend;
			subClass = this.createExtendClass(extend, config);
			return subClass;
		},
		/**
		 * 创建一个类
		 * @private
		 * @param {String}
		 *            className 需要被创建的类名
		 * @param {Object}
		 *            config 类的成员变量和成员函数集合
		 */
		createClass : function(className, config) {
			config.extend = config.extend || 'NS.Base';// 设定继承的父类信息
			this.createExtendClass(className, config);
		},
		/**
		 * 创建通过继承而生成的类
		 * @private
		 * @param {String}
		 *            className 需要被创建的类名
		 * @param {Object}
		 *            config 类的成员变量和成员函数集合
		 */
		createExtendClass : function(className, config) {
			var subClass, // 定义子类
                 mixins = config.mixins ||[],//混入的类
			superClass = this.get(config.extend),
            subProperties = this.getSuperInstance(superClass),
            supInstance = this.getSuperInstance(superClass); // 对象的属性集合;// 获取父类
			//supInstance = this.getSuperInstance(superClass);// 获得父类属性和方法集
//            supInstance = superClass.prototype;
			if (config.hasOwnProperty('constructor')) {
				subClass = config['constructor'];
                subProperties['constructor'] = config['constructor'];
				delete config.constructor;//删除构造函数的引用
			} else {
                subClass = (function(superClass){
                    var subClass = function(){// 初始化构造函数
                        superClass.apply(this,arguments);
                    };
                    return subClass;
                })(superClass);

			}
			delete config.extend;// 删除继承属性
			
			//给子类赋加属性(类名，父类)
			  
			var clone = NS.Function.clone;//获取复制函数方法
			//生成子方法，父方法关系链
			for ( var field in subProperties) {
				var superElement = subProperties[field];
				var subElement = config[field];
				if (subElement) {
					if (typeof subElement === "function") {
						var fun = clone(subElement);
						fun.$owner = subClass;
						fun.$name = field;
						subProperties[field] = fun;
					} else if (typeof subElement === "object") {
						subProperties[field] = subElement;
					} else if (typeof subElement === "string") {
						subProperties[field] = subElement;
					} else {
						subProperties[field] = subElement;
					}
				} else {
					subProperties[field] = superElement;
				}
			}
			NS.applyIf(subProperties, config);// 增加方法以及属性--增强对象行为

			subProperties.constructor = subClass;// 指定类的构造函数
			subProperties.$classname = className;// 删除类名属性
			subClass.prototype = subProperties;
			this.addProperties(subClass,{
									self : subClass,//自身
									superclass : supInstance,//父类信息,
									$classname : className//类名 
								});
			this.addStatics(subClass,{
									superclass : supInstance,//父类信息,
									$classname : className//类名 
								});
            /***添加针对混入分类的属性的绑定**/
            for(var k= 0;k<mixins.length;k++){
                var mclass = this.get(mixins[k]);
                if(mclass){
                   this.toMix(subClass,mclass);
                }
            }
            if (config.singleton === true) {
                var obj = new subClass();
				this.register(className, obj);
			} else {
				this.register(className, subClass);
			}
		},
		/***
		 * 给类添加静态属性
		 * @param Class
		 * @param config
		 * @return
		 */
		addStatics : function(Class,config,defaults){
			if(defaults){
				NS.apply(Class,defaults);
			}
			NS.apply(Class,config);
		},
		/***
		 * 为类添加prototype属性
		 */
		addProperties : function(Class,config,defaults){
			if(defaults){
				this.addProperties(Class,defaults);
			} 
			for(var i in config){
				Class.prototype[i] = config[i];
			}
		},
        /**
         * 混入另一个类的属性(另一个类的除继承NS.Base之外的属性，都将被混入)
         */
        toMix : function(cls,mixclass){
            var mixPrototype = mixclass.prototype,add = {}, i,item,owner,classname;
            for(i in mixPrototype){
                switch(i){
                    case '$classname':continue;
                    case 'superclass':continue;
                    default : {
                        item = mixPrototype[i];
                        owner = item.$owner;
                        if(owner){
                            classname = owner.$classname;
                            if(classname != "NS.Base"){
                                add[i] = item;
                            }
                        }else{
                            add[i] = item;
                        }
                    }
                }
            }
            this.addProperties(cls,add);
        },
		/**
		 * 复制方法,复制并返回的方法中将执行传递的方法，返回传递的方法返回的结果
		 * 
		 * @param {Function}
		 *            method 传递的方法
		 * @return {Function}
		 */
		clone : function(method) {
			return function() {
				return method.apply(this, arguments);
			};
		},
        /**
         * 定义并创建Class
         *
         *      NS.define('Test',{
         *          constructor : function(){
         *
         *          },
         *          show : function(){
         *              alert('test');
         *          }
         *      });
         *      var test = new Test();
         *      test.show();//alert  "test"
         *
         * @method define
         * @param {String} className 类名
         * @param {Object} config 对象的参数
         */
		define : function(className, config) {
			Loader = NS.ClassLoader;// 类加载器
			Loader.pushConfigByClassName(className, config);// 将类以及对应的参数压到栈中
			var needClass = (config.mixins||[]).concat((config.requires || []).concat(config.extend || []));// 所有需要加载的类
			if (needClass.length > 0) {
				Loader.pushReadyToLoadClass(needClass);
			}
			var flag = Loader.checkClassReady();// 检查各个类的创建参数是否到位
			if (flag) {
				var readyclasses = Loader.classStack;// 获取栈
				while (readyclasses.length != 0) {
					var obj = readyclasses.pop();
					var classname = obj.classname;// 类名
					var config = obj.config;// 配置参数
					if (this.get(classname)) {
						continue;
					} else {
						this.createClass(classname, config);
					}
				}
				Loader.clear();
                if(Loader.ready){
                    Loader.ready();
                    Loader.ready = null;
                }
			}
		},
		/**
		 * 为系统添加命名空间
		 * 
		 *  	NS.namespace('Company');
		 *  	Company.getName = function(){ 
		 *  						return "韩庆";
		 *      				  };
		 *      
		 * @method namespace
		 * @param {String} namespace 将要添加的命名空间
		 */
		namespace : function(namespace) {
			var lastName, // 需要定义的命名空间最后一个点---后面的名称
			names = namespace;// 待定义的命名空间的---父命名空间
            this.register(namespace,{});
		},
        /**
         * 根据类名创建该类的实例
         * @param {String} classname
         * @param {Object} config
         */
        create : function(classname,config){
//            try{
                return new classmap[classname](config);
//            }catch(e){
//                NS.ClassLoader.clear();
//            }
        }
	};
})();
(function(){
	var Manager = NS.ClassManager;
	var alias = NS.Function.alias;
    Manager.register('NS',NS);
	/**
	 * @member NS
     * @method namespace
	 * @inheritdoc NS.ClassManager#namespace
	 */
	NS.namespace = alias(Manager,'namespace');
	 /**
     * 别名 {@link NS#namespace NS.namespace}.
     * @inheritdoc NS#namespace
     * @member NS
     * @method ns
     */
	NS.ns = NS.namespace;
    /**
     * @member NS
     * @method define
     * @inheritdoc NS.ClassManager#define
     */
	NS.define = alias(Manager,'define');
    /**
     * @member NS
     * @method create
     * @inheritdoc NS.ClassManager#create
     */
    NS.create = alias(Manager,'create');
})();
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
})();/**
 * @class NS.ClassLoader
 * NS.ClassLoader 一个框架的类加载系统类,用于动态加载类
 * @author wangyongtai
 */
NS.ClassLoader = {
	/**
	 * 类加载器，目前实际应用类加载系统为LAB的工具
	 * @property {$LAB}
	 * @type {Object}
	 */
    loader : $LAB,
    baseNumber : 100000,
    /**
     * 类加载器，目前实际应用类加载系统为LAB的工具
	 * @property {Function}
	 * @type  {Function}
     */
	ready : function(){},
	onReady : function(callback){
		this.ready = callback;
	},
	/**
	 * 存储命名空间的类加载路径
	 * 
	 * @property {Object}
	 */
	paths : {
		"NS" : ''
	},
	/**
	 * 将类名对应的创建参数放置到栈中
	 * @param classname
	 * @param config
	 */
	pushConfigByClassName : function(classname,config){
		var classStack = this.classStack;
		var classMap = this.classMap;
		if(classMap[classname]){
			for(var i=0,len=classStack.length;i<len;i++){
				var obj = classStack[i];
				if(obj.classname == classname){
					obj.config = config;
					classMap[classname].canBeCreate = true;//标识该类的配置参数已经到位
					obj.canBeCreate = true;//标明该类的配置参数已经到位
				}
			}
		}else{
			var obj = {
					classname : classname,
					config : config,
					require : null,
					canBeCreate : true
			};
			classMap[classname] = obj;
			classStack.push(obj);
		}
	},
	/**
	 * 将待加载的JS类压入栈中
	 * @param classname
	 */
	pushReadyToLoadClass : function(classname){
		var CM = NS.ClassManager;
		if( typeof classname === "string"){
			if(!CM.get(classname)){
				var obj = {
						classname : classname,
						config : null,
						require : null
					};
				this.classStack.push(obj);
				this.classMap[classname] = obj;
				this.load(classname);
			}
		}else if(typeof classname === "object" && classname instanceof Array){
			for(var i=0,len = classname.length;i<len;i++){
				if(!CM.get(classname[i])){
					var obj = {
							classname : classname[i],
							config : null,
							require : null
						};
					this.classStack.push(obj);
					this.classMap[classname[i]] = obj;
					this.load(classname[i]);
				}
			}
		}
	},
	/***
	 * 检查参数是否到位
	 * @returns {Boolean}
	 */
	checkClassReady : function(){
		var classMap = this.classMap;
	    var flag = true;
		for(var i in classMap){
			var obj = classMap[i];
			if(obj.canBeCreate != true){//如果该类已经被创建
            flag = false;
			   break;
			}
		}
		return flag;
	},
	/***
	 * 清空类栈
	 */
	clear : function(){
	    this.classMap = {};
        this.classStack = [];
	},
	/***
	 * 待创建的类Hash表
	 */
	classMap : {},
	/***
	 * 待创建类栈
	 */
	classStack : [],
	/**
	 * 设置类加载路径 的 命名空间
	 * 
	 * @param {String} name 顶层命名空间名
	 * @param {String} path 命名空间对应路径名
	 */
	setLoadPath : function(name, path) {
		this.paths[name] = path;
	},
	/**
	 * 获取加载路径
	 */
	getClassUrl : function(classname) {
		var splits = classname.split('.');
		var baseUrl = this.paths[splits.shift()];
		baseUrl += "/"+splits.join('/');
        this.baseNumber += 100000;
		baseUrl += ".js?t="+this.baseNumber;//加载js文件的时候请求防止缓存
		return baseUrl;
	},
	/**
	 * 获取所有类的Url
	 */
	getClassesUrl : function(classname) {
		if (typeof classname == "string") {
			return this.getClassUrl(classname);
		}
	},
	/**
	 * 动态加载Class
	 * @private 动态加载一个或者多个类
	 */
	loadClass : function(classname, callback) {
		this.load(classname, callback);
	},
	/**
	 * @private 动态加载类
	 * @param {String/Array} classname 需要加载的类名
	 * @param {Function} callback 回调函数
	 */
	load : function(classname, callback) {
		var loader = this.loader, Manager = NS.ClassManager;
		if (typeof classname == "string") {
			if (!Manager.get(classname)) {
				this.loader = loader.script(this.getClassUrl(classname));
			}
		} else if (typeof classname == 'object' && classname instanceof Array) {
			for (var i = 0; i < classname.length; i++) {
				if (!Manager.get(classname[i]))
				    this.loader = loader.script(this.getClassUrl(classname[i]));
			}
		}
        if(!this.ready)
            this.ready = callback;
//        this.loader.wait(callback);
	},
    init : function(){
        this.loader.setGlobalDefaults(false,true,true,"",true,true);
    }
};
(function(){
    var loader = NS.ClassLoader;
    loader.init();
    var alias = NS.Function.alias;
    /**
     * @member NS
     * @method load
     * @inheritdoc NS.ClassLoader#load
     */
    NS.load = alias(loader,'load');
    /**
     * @member NS
     * @method setLoadPath
     * @inheritdoc NS.ClassLoader#setLoadPath
     */
    NS.setLoadPath = alias(loader,'setLoadPath');
})();/**
 * @class NS.Connection
 * @extends NS.Base
 *       Ajax请求对象，用以和后台进行ajax请求交互
 */
NS.define('NS.Connection', {
    /***
     * 后台基本请求路径
     */
    baseAction:'baseAction!queryByComponents.action',
    /***
     *  创建一个ajax交互对象
     */
    constructor:function () {
        this.addEvents('beforerequest');
        this.initConnection.apply(this, arguments);
    },
    /***
     * 初始化{Ext.data.Connection}对象
     */
    initConnection:function (config) {
//        this.conn = Ext.create('Ext.data.Connection', config || {});
        this.conn = Ext.Ajax;
    },
    /***
     * 异步请求组件数据
     *      var conn = new NS.Connection();
     *      conn.callService(,{name : 张三,age : 15});
     *
     * @param request  serviceName
     * @param params  Object
     * @return
     */
    callService:function (services, callback, scope, timeout) {
        this.asyncRequest({
            params:{components:NS.encode(services)},
            callback:callback,
            scope:scope,
            timeout : timeout || 60000
        });
    },
    /***
     * 同步调用后台service方法
     * @param {Object}services services对象
     * @return {Object} 后台返回的请求数据
     */
    syncCallService:function (services) {
        var data = this.syncRequest({
            params:{components:NS.encode(services)},
            callback:callback,
            scope:scope
        });
        return data;
    },
    /**
     * 用于获取同步加载数据
     * @param config 请求配置参数
     */
    syncRequest:function (config) {
        var me = this;
        var basic = {
            url:this.baseAction,
            params:{},
            method:'POST',
            async:false,
            success:function (response) {
                me.dataJson = Ext.JSON.decode(
                    response.responseText, true);
            },
            failure:function (response) {
                me.dataJson = Ext.JSON.decode(
                    response.responseText, true);
                Ext.Msg.alert('警告', '数据请求失败！');
            }
        };
        NS.apply(basic, config);//将参数对象合并
        this.conn.request(basic);
        return me.dataJson;
    },
    /***
     * 异步请求后台数据
     * @param config 请求配置参数
     */
    asyncRequest:function (config) {
        var me = this;
        var callback = config.callback;
        delete config.callback;
        var basic = {
            url:this.baseAction,
            params:{},
            method:'POST',
            header : {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
            success:function (response) {
                var sessionstatus=response.getResponseHeader("sessionstatus");
                if(sessionstatus=="timeout"){
                    window.location.href = "session.jsp";
                }
                var dataJson = Ext.JSON.decode(
                    response.responseText, true);
                callback.call(this, dataJson,response.responseText);
            },
            failure:function () {
                var dataJson = Ext.JSON.decode(
                    response.responseText, true);
                callback.call(this, dataJson);
                Ext.Msg.alert('警告', '数据请求失败！');
            }
        };
        NS.apply(basic, config);//将参数对象合并
        this.conn.request(basic);
    }
});/**
 * @class NS.dom.Element
 * @extends NS.Base
 *    DOM操作对象的封装
 */
NS.define('NS.dom.Element', {
    constructor:function (HtmlElement) {
        this.element = new Ext.dom.Element(HtmlElement);
        this.dom = this.element.dom;
    },
    /**
     * 设置Ext.dom.Element的包装对象
     * @private
     */
    set:function (element) {
        this.element = element;
        this.dom = element.dom;
        return this;
    },
    /**
     * 为dom元素添加class类
     * @param className
     */
    addCls:function (className) {
        this.element.addCls(className)
        return this;
    },
    /**
     * 移除dom元素所带的Class类
     */
    removeCls:function (cls) {
        this.element.removeCls(cls);
        return this;
    },
    /**
     * 获取innerHtml 串
     * @return {String}
     */
    getHTML:function () {
        return this.element.getHTML();
    },
    /**
     * 设置dom元素的innerHtml 串
     * @return {String}
     */
    setHTML:function (html) {
        this.element.setHTML(html);
        return this;
    },
    /**
     * 获取高度
     * @return {Number}
     */
    getHeight:function () {
        return this.element.getHeight();
    },
    /**
     * 获取宽度
     * @return {Number}
     */
    getWidth:function () {
        return this.element.getWidth();
    },
    /**
     * 获取Left位置
     * @return {Number}
     */
    getLeft:function () {
        return this.element.getLeft();
    },
    /**
     * 获取Top位置
     * @return {Number}
     */
    getTop:function () {
        return this.element.getTop();
    },
    /**
     * 设置样式
     * @param {String} property 属性名
     * @param {String} value 值
     */
    setStyle:function (property, value) {
        this.element.setStyle(property, value);
        return this;
    },
    /**
     * 根据传入的键，获取dom元素的属性值
     * @return {String} value attribute value
     */
    getAttribute:function (key) {
        return this.element.getAttribute(key);
    },
    /**
     * 返回dom元素的value属性的值
     */
    getValue : function(){
        return this.element.getValue();
    },
    /**
     * 获取dom元素的XY值
     * @return {Array} [x,y]
     */
    getXY : function(){
        return this.element.getXY();
    },
    /**
     * 获取dom元素的X轴上的值
     * @return {Number}
     */
    getX : function(){
        return this.element.getX();
    },
    /**
     * 获取dom元素的Y轴上的值
     * @return {Number}
     */
    getY : function(){
        return this.element.getY();
    },
    /**
     * 设置dom元素的XY值
     * @param {Array} pos 位置数组[x,y]
     * @param {Boolean} 是否以动画形式显示
     */
    setXY : function(pos,flag){
        this.element.setXY(pos,flag);
        return this;
    },
    /**
     * 根据查询规则，查询对应的DOM元素集合
     * @param {String} selector
     * @return {NS.dom.Element}
     */
    query : function(selector){
        return new NS.dom.Element(this.element.query(selector,true));
    },
    /**
     * 根据CSS选择器,查询子元素
     * @param {String} selector
     * @return {NS.dom.Element}
     */
    child : function(selector){
        return new NS.dom.Element(this.element.child(selector));
    }
});
(function () {
    var instance = new NS.dom.Element();
    /**
     *   把dom元素进行包装,获取dom对象的包装类,该包装类是单例的，也就是说，
     *   该包装类中包装的dom对象是可变的，因此，该方法获取的NS.dom.Element对象
     *   比较适合用于一些dom节点的一次性操作，获取的dom对象在下次调用NS.fly之前是指向同一个dom引用
     * @param {String/HtmlElement} el
     * @return {NS.dom.Element}
     * @member NS
     * @method fly
     */
    NS.fly = function (dom) {
        instance.set(Ext.fly(dom));
        return instance;
    }
    /**
     * 通过提供的id或者htmlelement元素，获取封装的NS.dom.Element对象
     * @param {String/HtmlElement} el
     * @return {NS.dom.Element}
     * @member NS
     * @method get
     */
    NS.get = function (el) {
        var element = new NS.dom.Element();
        var extEl = Ext.get(el);
        element.set(extEl);
        return element;
    }
    /**
     * @param {String/HtmlElement} el
     * @return {NS.dom.Element}
     * @member NS
     * @method getBody
     */
    NS.getBody = function(){
        var element = new NS.dom.Element();
        var extel = Ext.getBody();
        element.set(extel);
        return element;
    };
    /**
     * @return {HTMLElement[]}
     * @member NS
     * @method query
     */
    NS.query = function(path,root){
       return Ext.query(path,root);
    };
})();
NS.ns('NS.util');
/**
 *
 * 文件加载工具类
 *
 * @author wangyt
 * @class  NS.util.FileLoader
 */
NS.define('NS.util.FileLoader',{
    singleton : true,
    constructor : function(){
        this.connection = new Ext.data.Connection();
    },
    /**
     * 加载文件
     * @private
     * @param {String} url 文件路径
     * @param {Function} fun 回调函数
     * @param {Object} scope 作用域
     *
     *          NS.util.FileLoader.loadFile('app/pages/myfile',function(text,response){
     *                  alert(text);//是你加载的文件
     *
     *          });
     */
    loadFile : function(url,fun,scope){
        this.connection.request({
            url : url,
            success : function(response){
                fun.call(scope,response.responseText,response);
            }
        });
    },
    /**
     * 加载样式表
     * @private
     * @param {String} url 样式表路径
     *
     *      NS.util.FileLoader.loadCss('app/pages/mycss.css');
     */
    loadCss : function(url){
        var head = document.getElementsByTagName("head")[0];
        var style = document.createElement("link");
        style.rel = "stylesheet";
        style.type = "text/css";
        style.href = url;
        head.appendChild(style);
    }
});
(function(){
    var alias = NS.Function.alias;
    /**
     * @member NS
     * @method loadCss
     * @inheritdoc NS.util.FileLoader#loadCss
     */
    NS.loadCss = alias(NS.util.FileLoader,'loadCss');
    /**
     * @member NS
     * @method loadFile
     * @inheritdoc NS.util.FileLoader#loadCss
     */
    NS.loadFile = alias(NS.util.FileLoader,'loadFile');
})();/**
 * 任务调度管理
 * @author wangyt
 * @class  NS.util.TaskManager
 */
NS.define('NS.util.TaskManager',{
    singleton : true,
    constructor : function(){
        this.component = Ext.TaskManager;
    },
    /**
     * 开启任务
     *
     *      var task = {
     *          run : function(){console.log(11111)},
     *          interval : 1000,//执行时间间隔
     *          args : {num:121},//传给run方法的参数
     *          scope : this,//run方法执行的作用域
     *          duration : 1000000,//执行时间区间
     *          repeat : 123//重复执行次数
     *      };
     *      NS.util.TaskManager.start(task);
     *
     * @param {Object} task
     */
    start : function(task){
        this.component.start(task);
    },
    /**
     * 停止运行任务
     *
     *      var task = {
     *          run : function(){console.log(11111)},
     *          interval : 1000,//执行时间间隔
     *          args : {num:121},//传给run方法的参数
     *          scope : this,//run方法执行的作用域
     *          duration : 1000000,//执行时间区间
     *          repeat : 123//重复执行次数
     *      };
     *      NS.util.TaskManager.start(task);
     *      ....
     *      NS.util.TaskManager.stop(task);
     *
     * @param {Object} task
     */
    stop : function(task){
        this.component.stop(task);
    },
    /***
     * 停止运行所有任务
     */
    stopAll : function(){
        this.component.stopAll();
    }
});
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
})();/**
 * @class Format
 * 格式化工具类。提供字符转换、设置默认值、截取字符串等功能。
 * @author zhangzg
 */
NS.util.Format = (function() {
	var stripTagsRE    = /<\/?[^>]+>/gi,
        stripScriptsRe = /(?:<script.*?>)((\n|\r|.)*?)(?:<\/script>)/ig,
        nl2brRe        = /\r?\n/g;
	return {
		/**
		 * 检查一个引用是否为空，如果为空将它转换成一个空字符串。
		 * @param {Object} value Reference to check
		 * @return {Object} Empty string if converted, otherwise the original value
		 */
		undef : function(value) {
			return value !== undefined ? value : "";
		},

		/**
		 * 检查引用是否为空或者未定义，如果为空或未定义则给他一个默认值。
		 * @param {Object} value 被检测的引用。
		 * @param {String} defaultValue 默认值，这个默认值一般为空字符串''。
		 * @return {String}
		 */
		defaultValue : function(value, defaultValue) {
			return value !== undefined && value !== '' ? value : defaultValue;
		},

		/**
		 * 按照指定的开始位置和长度截取字符串。截取的字符串包含起始处的字符串。
		 * @param {String} value 被截取的字符串。
		 * @param {Number} start 起始位置
		 * @param {Number} length 截取的长度
		 * @return {String} 子字符串。
		 */
		substr : function(value, start, length) {
			return String(value).substr(start, length);
		},

		/**
		 * 将传递给方法的字符串中每一个字符转成它的小写形式。
		 * @param {String} value 要被转换的字符串
		 * @return {String} result 转换后的字符串
		 */
		lowercase : function(value) {
			return String(value).toLowerCase();
		},

		/**
		 * 将传递给方法的字符串中每一个字符转成它的大写形式。
		 * @param {String} value 要被转换的字符串
		 * @return {String} The 转换后的字符串
		 */
		uppercase : function(value) {
			return String(value).toUpperCase();
		},
		/**
         * 截取所有HTML标签。
         *
         * 		var string ='<html>testStripTags</html>';
         * 		string = NS.util.format.stripTags(string);
         * 		此时string的值为testStripTags。
         * 
         * @param {Object} value 要被截取的字符串。
         * @return {String} 截取后的字符串。
         */
        stripTags : function(v) {
            return !v ? v : String(v).replace(stripTagsRE, "");
        },

        /**
         * 截取所有的&lt;Stript *>标签。
         *  
         * 		var string ='Test<script src=''>stripScripts</script>result';
         * 		string = NS.util.format.stripTags(string);
         * 		此时string的值为Testresult。
         * 
         * @param {Object} value 要被截取的字符串。
         * @return {String} 截取后的字符串。
         */
        stripScripts : function(v) {
            return !v ? v : String(v).replace(stripScriptsRe, "");
        },
        /**
         * 将给定值中的回车符换成&lt;br/>html标签。
         * @param {String} 需要被格式化的字符串。
         * @return {String} 结果字符串。
         */
        nl2br : function(v) {
            return Ext.isEmpty(v) ? '' : v.replace(nl2brRe, '<br/>');
        }
	}

})();/*******************************************************************************
 * 重写panelHeader 属性方法 *
 */
Ext
    .override(
    Ext.panel.Panel,
    {
        getHeaderConfig : function(headerCfg, defaultCfg) {
            return Ext.applyIf(headerCfg, defaultCfg);
        },

        updateHeader : function(force) {
            var me = this, header = me.header, title = me.title, tools = me.tools, config = me.headerConfig;

            if (!me.preventHeader
                && (force || title || (tools && tools.length))) {
                if (header) {
                    header.show();
                } else {
                    var defaultConfig = {
                        title : title,
                        orientation : (me.headerPosition == 'left' || me.headerPosition == 'right') ? 'vertical'
                            : 'horizontal',
                        dock : me.headerPosition || 'top',
                        textCls : me.headerTextCls,
                        iconCls : me.iconCls,
                        icon : me.icon,
                        baseCls : me.baseCls + '-header',
                        tools : tools,
                        ui : me.ui,
                        id : me.id + '_header',
                        indicateDrag : me.draggable,
                        frame : me.frame && me.frameHeader,
                        ignoreParentFrame : me.frame
                            || me.overlapHeader,
                        ignoreBorderManagement : me.frame
                            || me.ignoreHeaderBorderManagement,
                        listeners : me.collapsible
                            && me.titleCollapse ? {
                            click : me.toggleCollapse,
                            scope : me
                        } : null
                    };
                    var econfig = Ext.apply(defaultConfig, config);
                    header = me.header = new Ext.panel.Header(
                        econfig);
                    me.addDocked(header, 0);

                    // Reference the Header's tool array.
                    // Header injects named references.
                    me.tools = header.tools;
                }
                me.initHeaderAria();
            } else if (header) {
                header.hide();
            }
        }
    });/**
 * @class NS.Component
 * @extends NS.Base
 * @author wangyt
 * 组件类-基类
 *
 *              var component = new NS.Component({
 *                  width : 400,
 *                  height : 200,
 *                  style : {
 *                      backgroundColor : 'yellow'
 *                  },
 *                  renderTo : NS.getBody()
 *              });
 *
 */
NS.define('NS.Component', {
    /**
     * @cfg {Boolean} autoShow 是否自动显示 默认false
     */
    /**
     * @cfg {String/Number} margin 边框距 以此为例:'1 2 3 4' 分别指:'top right bottom left'处的边框大小，单位px
     */
    /**
     *@cfg {Boolean} hidden 组件是否隐藏现实，默认是false
     */
    /**
     * @cfg {Boolean} disabled 是否可用 默认为false
     */
    /**
     * @cfg {Boolean} floating 是否浮动，默认为false
     */
    /**
     * @cfg {Boolean} frame 是否框架背景颜色（浅蓝色）
     */
    /**
     * @cfg {String/Number} padding 定义组件的内边距，其可以为数字，或者字符串。例如：“10 15 10 15”
     */
    /**
     * @cfg {Number} width 定义组件的宽度
     */
    /**
     * @cfg {Number} height 定义组件的高度
     */
    /**
     * @cfg {Number} border 定义组件的边框宽度，其可以为数字，或者字符串。例如：“10 15 10 15”
     */
    /**
     * @cfg {Object} style 定义组件的样式，其可为css基本样式
     *
     *              @example
     *              var panel = new NS.container.Panel({
     *                  width : 400,
     *                  height : 200,
     *                  style : {
     *                      backgroundColor : 'yellow'
     *                  },
     *                  renderTo : NS.getBody()
     *              });
     *
     */
    /**
     * @cfg {String} html 组件内包含的html
     */
    /**
     * @cfg {Object/Object[]} plugins 定义组件的插件
     */
    /**
     * @cfg {String/HtmlElement} renderTo 定义组件的渲染到哪个元素下,其可以为DOM对象的id，DOM对象
     */
    /**
     * @cfg {Boolean} autoScroll true则当浏览器内容溢出盒子之后，允许使用滚动条来显示其余被
     *                              修剪的内容，false则不允许
     */
    /**
     * @cfg {NS.Template} tpl 模版对象
     */
    /**
     * @cfg {Object} data  为NS.Template准备的数据
     */
    /**
     * 构造函数
     * @param {Object} config 构造函数配置对象
     */
    constructor: function (config) {
        this.initConfigMapping();//初始化配置属性映射容器
        var libConfig = this.processConfigProperties(config);//处理键值映射关系
        this.processExtPlugins.apply(this, [libConfig]);//调用处理Ext插件
        this.initEvents();//初始化组件事件
        this.initData.apply(this, arguments);//初始化数据
        this.initComponent.apply(this, [libConfig]);//初始化组件
        //this.packEvents();//封装事件,需要被封装组件已经创建完毕
        this.processNSPlugins();//调用处理NS插件
        this.addPackContainer();//添加Ext对象的包装对象属性
    },
    /***
     * 初始化属性映射（为了和底层框架隔离）
     * @private
     */
    initConfigMapping: function () {
        var getTpl = function (value, property, config) {
            config[property] = value.tpl;
        };
        var renderTo = function(value,property,config){
            if(NS.isElement(value)){
                config[property] = value.element;
            }else if(NS.isString(value)){
                config[property] = value;
            }
        }
        this.configPropertiesMapping = {
            baseCls : true,
            cls : true,
            disabled : true,
            margin: true,
            padding: true,
            autoShow : true,
            autoScroll : true,
            floating:true,
            width: true,
            height: true,
            maxHeight : true,
            maxWidth  :true,
            minHeight : true,
            minWidth  :true,
            name : true,//name属性标识
            hidden : true,//是否显示组件
            colspan : true,//table中占几列
            rowspan : true,//table占几行
            columnWidth : true,//column布局使用
            style : true,
            border: true,
            plugins: true,
            items: true,
            layout: true,
            region: true,
            frame : true,
            renderTo: true,
            tpl : getTpl,
            data : true,
            html: true
            //listeners : 'listeners'
        };

    },
    /***
     * 处理配置项
     * @private
     */
    processConfigProperties: function (config) {
        var cpm = this.configPropertiesMapping, item, i, libconfig = {}, ownerproperties = {};
        for (var i in config) {
            item = cpm[i];
            if (item) {
                libconfig[i] = config[i];
            } else {
                ownerproperties[i] = config[i];
            }
        }
        NS.apply(this, ownerproperties);//将不支持的属性绑定到封装组件上
        for (var i in libconfig) {
            item = cpm[i];
            if (NS.isFunction(item)) {
                item.call(this, libconfig[i], i, libconfig);
            }
        }
        return libconfig;
    },
    /**
     * 为组件添加属性对应的处理策略
     *  this.addProcessForConfig('margin','margin');
     *  this.addProcessForConfig({
     *      name : 'value'
     *      fn : function(value){return value+1;}
     *  });
     *  @private
     */
    addConfigMapping: function (pn, fn) {
        var pp = this.configPropertiesMapping, args = arguments, item;
        switch (args.length) {
            case 1 :
            {
                if (NS.isObject(pn)) {
                    NS.apply(pp, pn);
                }
            }
            case 2 :
            {
                if (NS.isString(pn) && NS.isFunction(fn)) {
                    pp[pn] = fn;
                    break;
                }
            }
            default :
            {
                for (var i = 0, len = args.length; i < len; i++) {
                    item = args[i];
                    if (NS.isString(item)) {
                        pp[item] = true;
                    }
                }
                break;
            }

        }
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents: function () {
        this.addEvents(
            /***
             * @event activate 渲染之前
             * @param {NS.Component} this 组件
             */
            'activate',
            /***
             * @event beforerender 渲染之前
             * @param {NS.Component} this 组件
             */
            'beforerender',
            /***
             * @event afterrender 渲染之后
             * @param {NS.Component} this 组件
             */
            'afterrender',
            /**
             * @event disable 组件被禁用后触发
             * @param {NS.Component} this 组件
             */
            'disable',
            /**
             * @event enable 组件被启用后触发
             * @param {NS.Component} this 组件
             */
            'enable',
            /**
             * @event hide 组件被禁用后触发
             * @param {NS.Component} this 组件
             */
            'hide',
            /**
             * @event focus 组件获取焦点后触发
             * @param {NS.Component} this 组件
             */
            'focus',
            /**
             * @event blur 组件失去焦点后触发
             * @param {NS.Component} this 组件
             */
            'blur',
            /**
             * @event click 当组件被点击后触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'click',
            /**
             * @event dbclick 当组件被双击后触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'dbclick',
            /**
             * @event contextmenu 当鼠标右击的时候触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'contextmenu',
            /**
             * @event mouseover 当鼠标在组件上方的时候触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'mouseover',
            /**
             * @event mouseenter 当鼠标进入组件后触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'mouseenter',
            /**
             * @event mouseleave 当鼠标离开组件后触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'mouseleave',
            /**
             * @event mousemove 当鼠标在组件上移动时触发该事件
             * @param {NS.Event} event 事件对象
             * @param {HTMLElement} element 点击到的html元素
             */
            'mousemove',
            /**
             * @event keydown 在一个组件内，当一个键盘按下的时候触发
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'keydown',
            /**
             * @event keyup 在一个组件内，当一个键盘按下后松触发
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'keyup',
            /**
             * @event keypress 在一个组件内，当一个键盘按下后，松开的时候触发
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'keypress',
            /***
             * @event destroy 组件被销毁时，触发该事件
             * @params {Ext.Component} this
             */
            'destroy',
            /**
             * @event select 在一个组件内，当用户选择一些field组件的文本的时候，包括input、textarea
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'select',
            /**
             * @event change 当一个input失去焦点，并且值发生改变的时候触发该事件
             * @param {NS.Event} this
             * @param {HTMLElement} this
             */
            'change'

        );
    },
    /**
     * 组件被激活触发该事件
     * @private
     */
    onActivate : function () {
        this.component.on('activate',function(event, element){
            this.fireEvent('activate', this);
        },this);
    },
    /**
     * 组件被渲染到DOM Tree上后触发
     * @private
     */
    onAfterrender : function () {
        this.component.on('afterrender',function(event, element){
            this.fireEvent('afterrender', this);
        },this);
    },

    /**
     * 组件被渲染到DOM Tree上后触发
     * @private
     */
    onBeforerender : function () {
        this.component.on('beforerender',function(event, element){
            this.fireEvent('beforerender', this);
        },this);
    },
    /**
     * 组件被禁用后触发
     * @private
     */
    onDisable : function () {
        this.component.on('disable',function(event, element){
            this.fireEvent('disable', this);
        },this);
    },
    /**
     * 组件被启用后触发
     * @private
     */
    onEnable:   function () {
        this.component.on('enable',function(event, element){
            this.fireEvent('enable', this);
        },this);
    },
    /**
     * 组件获得焦点后触发
     * @private
     */
    onFocus:  function () {
        this.component.on('focus',function(event, element){
            this.fireEvent('focus', this);
        },this);
    },
    /**
     * 组件失去焦点后触发
     * @private
     */
    onBlur:  function () {
        this.component.on('blur',function(){
            this.fireEvent('blur', this);
        },this);
    },
    /**
     * 销毁的时候触发
     * @private
     */
    onDestroy : function(){
        this.component.on('destroy',function(){
            this.fireEvent('destroy', this);
        },this);
    },
    /**
     * 组件隐藏后触发
     * @private
     */
    onHide: function () {
        this.component.on('hide',function(){
            this.fireEvent('hide', this);
        },this);
    },
    /**
     * 组件被单击后触发
     * @private
     */
    onClick: function (event, element) {
        this.component.on({
            click : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('click', NS.Event, element);
                }
            }
        });
    },
    /**
     * 鼠标右击后触发
     * @private
     */
    onContextmenu : function(){
        this.component.on({
            contextmenu : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('contextmenu', NS.Event, element);
                }
            }
        });
    },
    onChange : function(){
        this.component.on({
            change : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('change', NS.Event, element);
                }
            }
        });
    },
    /**
     * 组件被双击后触发
     * @private
     */
    onDbclick:function () {
        this.component.on({
            dblclick : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('dbclick', NS.Event, element);
                }
            }
        });
    },
    /**
     * 鼠标移动到组件上后触发
     * @private
     */
    onMouseover: function () {
        this.component.on({
            mouseover : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('mouseover', NS.Event, element);
                }
            }
        });
    },
    onSelect : function(){
        this.component.on({
            select : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('select', NS.Event, element);
                }
            }
        });
    },
    /**
     * 鼠标进入组件后触发
     * @private
     */
    onMouseenter: function () {
        this.component.on({
            mouseenter : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('mouseenter', NS.Event, element);
                }
            }
        });
    },
    /**
     * 鼠标离开组件后触发
     * @private
     */
    onMouseleave: function () {
        this.component.on({
            mouseleave : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('mouseleave', NS.Event, element);
                }
            }
        });
    },
    /**
     * 当键盘按下后触发该事件
     * @private
     */
    onKeydown: function () {
        this.component.on({
            keydown : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('keydown', NS.Event, element);
                }
            }
        });
    },
    /**
     * 当键盘按下松开后触发该事件
     * @private
     */
    onKeyup: function () {
        this.component.on({
            keyup : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('keyup', NS.Event, element);
                }
            }
        });
    },
    /**
     * 当键盘按下松开后触发该事件
     * @private
     */
    onKeypress: function () {
        this.component.on({
            keypress : {
                element : 'el',
                scope : this,
                fn : function(event, element){
                    NS.Event.setEventObj(event, element);
                    this.fireEvent('keypress', NS.Event, element);
                }
            }
        });
    },
    addListener : function(event,callback,scope){
          var eventFunName,item;
          if(NS.isString(event)){
              eventFunName = 'on'+NS.String.capitalize(event);
              if(this[eventFunName]){
                  this[eventFunName]();
              }
              this.callParent(arguments);
          }else if(NS.isObject(event)){
              for(var i in event){
                  eventFunName = 'on'+NS.String.capitalize(i);
                  if(this[eventFunName]){
                      this[eventFunName]();
                  }
                  item = event[i];
                  if(NS.isFunction(item)){
                      this.callParent([i,item]);
                  }else if(NS.isObject(item)){
                      if(item.fn){
                         this.callParent([i,item,item.scope||this]);
                      }
                  }
              }
          }

    },
    /**
     * 移除监听事件
     * @param {String} event 事件名
     */
    removeListener : function(event){
        var events = this['events']||{};
        if(NS.isString(event)){
            delete events[event];
            this.component.removeListener(event);
        }
    },

    /**
     * 增加on事件方法
     *
     * @param {String}eventName 事件名称
     * @param {Funcion} fn 回调函数
     * @param {Object} scope 作用域
     */
    on: function (event, fn, scope, options) {
        var eventFunName,item;
        if(NS.isString(event)){
            eventFunName = 'on'+NS.String.capitalize(event);
            if(this[eventFunName]){
                this[eventFunName]();
            }
            this.callParent(arguments);
        }else if(NS.isObject(event)){
            for(var i in event){
                eventFunName = 'on'+NS.String.capitalize(i);
                if(this[eventFunName]){
                    this[eventFunName]();
                }
                item = event[i];
                if(NS.isFunction(item)){
                    this.callParent([i,item]);
                }else if(NS.isObject(item)){
                    if(item.fn){
                        this.callParent([i,item,item.scope||this]);
                    }
                }
            }
        }
    },
    /**
     * @method initData
     * 初始化数据
     * @private
     */
    initData: NS.emptyFn,
    /**
     * 初始化组件
     * @private
     * @param {Object} config 配置对象
     */
    initComponent: function (config) {
        this.component = new Ext.Component(config);
    },
    /**
     * 为原生类添加一个属性，属性指向当前包装类
     * @private
     */
    addPackContainer: function () {
        this.component.NSContainer = this;
    },
    /***
     * 获取底层类库组件,用以和底层类库进行交互
     * @private
     * @return Ext.Component
     */
    getLibComponent: function () {
        return this.component;
    },
    /***
     * 处理Ext插件
     * @private
     * @param {Object} config 组件的配置对象
     */
    processExtPlugins: function (config) {
        if (config) {
            var plugins = config.plugins;//插件对象
            var extPlugins = [];
            var nsPlugins = this.nsPlugins = [];
            if (NS.isArray(plugins)) {
                for (var i = 0, len = plugins.length; i < len; i++) {
                    var p = plugins[i];
                    if (p.getLibComponent) {
                        extPlugins.push(p.getLibComponent());
                    } else {
                        nsPlugins.push(p);
                    }
                }
            } else if (NS.isObject(plugins)) {
                if (plugins.getLibComponent) {
                    extPlugins.push(plugins.getLibComponent());
                } else {
                    nsPlugins.push(plugins);
                }
            }
            config.plugins = extPlugins;
        }
    },
    /***
     * 处理NS插件对象
     * @private
     */
    processNSPlugins: function () {
        if (this.nsPlugins) {
            var nsPlugins = this.nsPlugins;
            for (var i = 0, len = nsPlugins.length; i < len; i++) {
                var p = nsPlugins[i];
                p.init(this);
            }
            delete this.nsPlugins;
        }
    },
    /**
     * 设置组件的包装对象
     * @param {Ext.component.Component} com 需要被包装的底层类库对象
     * @private
     */
    setComponent: function (com) {
        this.component = com;
        return this;
    },
    /***
     * 销毁本对象方法
     * @private
     */
    destroy: function () {
        this.component.destroy();
        for (var i in this) {
            if (i)
                delete this[i];
        }
    },
    /***
     * 将组件渲染到一个某个元素下，该元素的id为传入的id
     * @param {String} id 要渲染到的html的元素的id
     */
    render: function (id) {
        this.component.render(id);
    },
    /**
     * 显示组件
     */
    show: function () {
        this.component.show();
    },
    /**
     * 显示在某个特定的位置
     * @param {Number} x x轴的位置
     * @param {Number} y y轴的位置
     * @param {Boolean} [animate] 是否以动画效果显示
     */
    showAt : function(x,y,animate){
        this.component.showAt(x,y,animate);;
    },
    /**
     * 隐藏组件
     */
    hide: function() {
        this.component.hide();
    },
    /**
     * 判断组件是否隐藏,如果隐藏返回true，如果不隐藏显示false
     * @return {Boolean}
     */
    isHidden : function(){
        return this.component.isHidden();
    },
    /**
     * 设置组件高度
     * @param {Number}  width 宽度
     */
    setWidth: function (width) {
        this.component.setWidth(width);
    },
    /**
     * 设置组件宽度
     * @param {Number} height 高度
     */
    setHeight: function (height) {
        this.component.setHeight(height);
    },
    /**
     * 设置组件是否可用,true可用，false不可用
     * @param {Boolean} flag
     */
    setDisabled: function (flag) {
        this.component.setDisabled(flag);
    },
    /**
     * 组件是否可用
     * @return {Boolean}
     */
    isDisabled: function () {
       return this.component.isDisabled();
    },
    /**
     * 更新Component包含的Html内容
     * @param {String} html html字符串
     */
    updateHtml : function(html){
        this.component.update(html);
    },
    /**
     * 刷新模版数据
     * @param {Object} data 模版的数据
     */
    refreshTplData : function(data){
        this.component.update(data);
    },
    /**
     * 尝试把焦点转移到组件上
     */
    focus : function(){
        this.component.focus();
    },
    /**
     * 获取NS.dom.Element对象
     * @return {NS.dom.Element}
     */
    getEl : function(){
        return new NS.dom.Element(this.component.getEl().dom);
    },
    /**
     * 设置组件显示的位置
     * @param {Number} x 在浏览器上X轴的位置
     * @param {Number} y 在浏览器上Y轴的位置
     * @param {Boolean} animate 是否有动画效果
     */
    setPosition : function(x,y,animate){
        this.component.setPosition(x,y,animate);
    },
    /**
     * 获取组件的宽度
     * @return {Number}
     */
    getWidth : function(){
        return this.component.el.getWidth();
    },
    /**
     * 获取组件的高度
     * @return {Number}
     */
    getHeight : function(){
        return this.component.el.getHeight();
    },
    /**
     * 返回组件X轴所处位置
     * @return {Number}
     */
    getX : function(){
        return this.component.el.getX();
    },
    /***
     * 返回组件Y轴所处位置
     * @return {Number}
     */
    getY : function(){
        return this.component.el.getY();
    },
    contains:function(e){
    	return this.component.el.contains(e);
    }
});
/**
 * @class NS.Img
 * @extends NS.Component
 * @author yongtaiwang
 *      图片类
 *
        var img = new NS.Img({
            src : '../../show.jpg'
        });
 */
NS.define('NS.Img',{
    extend : 'NS.Component',
    /**
     *@cfg {String} src 图片的路径
     */
    initComponent : function(config){
        this.component = Ext.create('Ext.Img',config);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('src');
    },
    /**
     * 设置组件显示的图片的路径
     * @param {String} url
     */
    setSrc : function(url){
        this.component.setSrc(url);
    }
});/**
 * @class NS.Template
 * @extends NS.Base
 *   具体使用格式以及规范--参照Ext.XTemplate
 */
NS.define('NS.Template',{
    /***
     * @param {String} tplString tpl字符串
     */
    constructor : function(){
        var s = "",cls = Ext.XTemplate;
        for(var i=0;i<arguments.length;i++){
            var item = arguments[i];
            s+="arguments["+i+"]"+",";
        }
        s = s.substr(s,s.length-1);
        this.tpl = eval("new cls("+s+");");
    },
    /**
     * 把传入的数据迭代成html标签，并写入component的dom节点下面
     * @param {Sting/HtmlElement/NS.dom.ELement} component
     * @param {Object}data
     */
    writeTo : function(component,data){
        if(NS.isNSComponent(component)){
            var ec = component.getLibComponent();
            this.tpl.overwrite(ec.el,data);
        }else if(component instanceof NS.dom.Element){
            this.tpl.overwrite(component.dom,data);
        }else if(NS.isString(component)){
            this.tpl.overwrite(component,data);
        }else if(NS.isElement(component)){
            this.tpl.overwrite(component,data);
        }
    },
    insertFirst : function(component,data){
        if(NS.isNSComponent(component)){
            var ec = component.getLibComponent();
            this.tpl.insertFirst(ec.el,data);
        }else if(component instanceof NS.dom.Element){
            this.tpl.insertFirst(component.dom,data);
        }else if(NS.isString(component)){
            this.tpl.insertFirst(component,data);
        }else if(NS.isElement(component)){
            this.tpl.insertFirst(component,data);
        }
    },
    insertBefore : function(component,data){
        if(NS.isNSComponent(component)){
            var ec = component.getLibComponent();
            this.tpl.insertBefore(ec.el,data);
        }else if(component instanceof NS.dom.Element){
            this.tpl.insertBefore(component.dom,data);
        }else if(NS.isString(component)){
            this.tpl.insertBefore(component,data);
        }else if(NS.isElement(component)){
            this.tpl.insertBefore(component,data);
        }
    },
    insertAfter : function(component,data){
        if(NS.isNSComponent(component)){
            var ec = component.getLibComponent();
            this.tpl.insertAfter(ec.el,data);
        }else if(component instanceof NS.dom.Element){
            this.tpl.insertAfter(component.dom,data);
        }else if(NS.isString(component)){
            this.tpl.insertAfter(component,data);
        }else if(NS.isElement(component)){
            this.tpl.insertAfter(component,data);
        }
    }
});/***
 * @class NS.Plugin
 * @extends NS.Base
 * 所有组件的插件类的基类
 *      var component = new NS.Component({
 *          plugins : [new NS.Plugin]
 *      });
 */
NS.define('NS.Plugin',{
    /**
     * 插件的宿主对象初始化方法
     * @param {NS.Component} component 宿主对象
     */
    init : NS.emptyFn
});
/**
 * @class NS.button.Button
 * @extends NS.Component
 * 例如
 *
              var button = new NS.button.Button({
                   text : '新增',
                   name :'add',
                   iconCls : 'page_add'
              });

 */
NS.define('NS.button.Button',{
    extend : 'NS.Component',
    /**
     *@cfg {String} text 按钮的显示名称
     */
    /**
     *@cfg {String} name 按钮的name属性
     */
    /**
     *@cfg {String} iconCls 按钮背景图片的Class类
     */

    initComponent : function(config){
        this.component = new Ext.button.Button(config);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
                text : true,
                name : true,
                handler : true,
                iconCls : true
            }
        );
    },
    onClick : function(){
        this.component.on('click',function(button,event){
            NS.Event.setEventObj(event);
            this.fireEvent('click',this,NS.Event);
        },this);
    },
    /**
     * 设置按钮的文字显示
     * @param {String} text
     */
    setText : function(text){
        this.component.setText(text);
    },
    /**
     * 获取按钮的文字显示
     * @return {String}
     */
    getText : function(){
        return this.component.getText();
    },
    /**
     * 设置按钮的图标样式
     * @param {String} cls css类样式
     */
    setIconCls : function(cls){
        this.component.setIconCls(cls);
    }
});NS.define('NS.Editor',{
    extend : 'NS.Component',
    /**
     * @cfg {NS.form.field.BaseField} field 编辑组件
     */
    /**
     * @cfg {Array} offsets 位置信息
     */
    /**
     * @param config
     */
    initComponent : function(config){
        var basic = {
            updateEl : true
        }
        this.component = new Ext.Editor(basic);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        var getField = function(value, property, config){
            config[property] = value.getLibComponent();
        };
        this.addConfigMapping({
            updateEl : true,
            field : true,
            offsets : true//位置信息
        });
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents: function () {
        this.callParent();
        this.addEvents(
            /***
             * @event complete 组件渲染完毕后
             * @param {NS.Component} this
             */
            'complete'
        );
    },
    onComplete : function(){
        this.component.on('complete',function(editor,value,startValue){
            this.fireEvent('complete', this,value,startValue,this.component.boundEl);
        },this);
    },
    /**
     * 要编辑的元素
     * @param element
     */
    startEdit : function(element){
        this.component.startEdit(element);
    },
    /**
     * 取消编辑
     */
    cancelEdit : function(){
        this.component.cancelEdit();
    }
});/**
 * @class NS.mask.LoadMask
 * @extends NS.Component
 *    var mask = new NS.mask.LoadMask({
 *        target : component1,
 *        msg : '数据加载中'
 *    });
 *    mask.show();
 *    //....do something
 *    mask.hide
 */
NS.define("NS.mask.LoadMask",{
   extend : 'NS.Component',
    /**
     *@cfg {NS.Component} target 待使用遮罩的组件
     */
    /**
     * @cfg {String} msg 遮罩的提示信息
     */
   initComponent : function(config){
       this.config = config;
       var target = config.target;
       if(NS.isNSComponent(target)){
           target = target.getLibComponent();
           this.component = Ext.create('Ext.LoadMask',target,{msg : config.msg||"加载中..."});
       }else if(NS.isNSElement(target)){
           this.component = target.element;
       }

   },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
                target : true,//遮罩目标
                msg : true//提示信息
        });
    },
    /**
     * 显示遮罩
     */
   show : function(){
       if(this.component instanceof Ext.LoadMask){
          this.component.show();
       }else if(this.component instanceof Ext.dom.Element){
           this.component.mask(this.config.msg);
       }
   },
    /**
     * 隐藏遮罩遮罩
     */
   hide : function(){
        if(this.component instanceof Ext.LoadMask){
            this.component.hide();
        }else if(this.component instanceof Ext.dom.Element){
            this.component.unmask();
        }
   }
});/**
 * @class NS.container.Container
 * @extends NS.Component
 * @author yongtaiwang
 *      组件类-基类
 *
 *      var container = new NS.container.Container({
 *              plugins : [new Plugin()],
 *              items :  [
 *                 component1,component2
 *              ]
 *      });
 *
 */
NS.define('NS.container.Container',{
    extend : 'NS.Component',
    /**
     *@cfg {Component/Object[]} items 容器的子组件
     */

    /**
     * @cfg {Object/String} layout 定义容器的布局形式
     *
     *   它可以是这种形式
     *
     *          var container = new NS.container.Container({
     *              layout : {
     *                    type : 'table',
     *                    columns : 2
     *              }});
     *
     *   也可以是这种形式
     *
     *          var container = new NS.container.Container({
     *                      layout : 'border'
     *                  });
     *
     */

    /**
     * @param｛Object｝config 配置对象
     * @private
     *     创建一个NS.Component 对象实例
     */
    constructor : function(config){
        this.procressItems(config);
        this.callParent(arguments);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
            layout : true,
            items : true
        });
    },
    /**
     * 初始化组件
     * @param {Object} config 配置对象
     * @private
     */
    initComponent : function(config){
        this.component = new Ext.container.Container(config);
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents : function(){
        this.callParent();
    },
    /***
     * 将组件添加到容器子元素列表最后面
     * @param {NS.Component} component 组件
     * @return {NS.Component} 被添加的组件
     */
    add : function(component){
        if(NS.isArray(component)){
            var array = [];
            for(var i= 0;i<component.length;i++){
                var com = component[i];
                if(NS.isNSComponent(com)){
                    array.push(com.getLibComponent());
                }
            }
            this.component.add(array);
        }else{
            if(NS.isNSComponent(component)){
                this.component.add(component.getLibComponent());
            }
        }
        return component;
    },
    /**
     * 移除容器中的组件
     * @param {NS.Component} component 组件
     * @param {Boolean} flag 是否销毁组件
     * @return {NS.Component} 被移除的组件
     */
    remove : function(component,flag){
        this.component.remove(component.getLibComponent(),flag);
        return component;
    },
    /**
     * 将组件插入到容器的指定位置
     * @param {Number} index 组件将要插入的位置
     * @param {NS.Component} component 要插入的组件
     * @return {NS.Component} 被插入的组件
     */
    insert : function(index,component){
        this.component.insert(index,component.getLibComponent());
    },
    /**
     * 移除容器中的所有组件
     * @param {Boolean} flag
     */
    removeAll : function(flag){
        this.component.removeAll(flag);
    },
    /**
     * 根据组件索引获取容器内的组件
     * @param {Number} index 组件在容器的子组件列表中存在的位置
     * @return {NS.Component} 查询到的组件
     */
    getComponent : function(index){
        var component  = this.component.getComponent(index);
        return NS.util.ComponentInstance.getInstance(component);
    },
    /**
     * 执行容器的重新布局
     */
    doLayout : function(){
        this.component.doLayout();
    },
    /**
     * 根据组件的name 查找组件 (前提组件必须拥有name属性)
     * @param {String} name 组件的name属性的值
     * @return {NS.Component} 查询到的第一个组件
     */
    queryComponentByName: function (name) {
        var queryString = 'component[name="{0}"]';
        var components = this.component.query(NS.String.format(queryString, name));
        if (components.length == 1) {
            var com = components[0];
            return NS.util.ComponentInstance.getInstance(com);
        }else{
            NS.error({
                sourceClass : this.$classname,
                sourceMethod : 'queryComponentByName',
                msg : '查询到name为'+name+"的组件共有"+components.length+"个"
            });
        }
    },
    /**
     * 根据给定的属性名，查找组件集合
     * @return {NS.Component[]} 查询到的组件数组
     */
    queryComponentsByName : function(name){
        var queryString = 'component[name="{0}"]',
            components = this.component.query(NS.String.format(queryString, name)),
            i= 0,len=components.length,com,comArray = [];
        for(;i<len;i++){
            com = components[i];
            comArray.push(NS.util.ComponentInstance.getInstance(com));
        }
        return comArray;
    },
    /***
     * 根据组件name查找组件，并且绑定该组件的监听函数
     *
     *      var component = new NS.container.Container({
     *        items : [
     *            {xtype : 'button',text : '新增',name : 'add'},
     *            {xtype : 'button',text : '修改',name : 'update'},
     *            {xtype : 'button',text : '删除',name : 'delete'}
     *         ]
     *     });
     *    component.bindEvent('add','click',function(){},component);//你可以这样做
     *    component.bindEvent({
     *        add : {
     *            event : 'click',
     *            fn : function(){},
     *            scope : component//scope默认是调用绑定事件的容器类
     *        }
     *    });
     * @param {String/Object} cname 组件名称绑定单个组件的事件的时候/绑定多个组件的事件的时候是配置对象
     * @param {String} event 事件名称
     * @param {Function} fn 监听函数
     * @param {Object} scope 作用域
     */
    bindItemsEvent : function(cname,event,fn,scope){
        var component, i,obj = arguments[0],ed;
        if(NS.isString(cname) && NS.isString(event) && NS.isFunction(fn)){
            component = this.queryComponentByName(cname);
            if(!component){
                NS.error({
                    sourceClass : this.$classname,
                    sourceMethod : 'bindItemsEvent',
                    msg : '没有找到对应的组件，绑定事件的组件的名称为:'+cname
                });
            }
            component.on(event,fn,scope);
        }else if(arguments.length == 1 && NS.isObject(obj)){
            for(i in obj){
                component = this.queryComponentByName(i);
                if(!component){//如果组件没有找到，则抛出异常
                    NS.error({
                        sourceClass : this.$classname,
                        sourceMethod : 'bindItemsEvent',
                        msg : '没有找到对应的组件，绑定事件的组件的名称为:'+cname
                    });
                }
                ed = obj[i];
                component.on(ed.event,ed.fn,ed.scope||this);
            }
        }
    },
    /***
     * 解封装，去掉包装，将容器还原成为底层类库组件（这里是Ext组件）
     * @param {Object} config 待处理的配置参数对象
     * @private
     */
    procressItems : function(config){
        if(config){
            var items = config.items||[],
                item;
            this.processChildItems(config);
        }
    },
    /***
     * 处理嵌套的组件层次
     * @param {Array} items 子组件数组
     * @private
     */
    processChildItems : function(config){
        var items = config.items,item;
//        if(config.tbar && NS.isNSComponent(config.tbar)){
//           config.tbar = config.tbar.getLibComponent();
//        }
        if(NS.isArray(items)){
            for(var i= 0,len=items.length;i<len;i++){
                item = items[i];
                if(item.getLibComponent){
                    items[i] = item.getLibComponent();
                }else if(NS.isObject(item)){
                	if(item.items){
                		arguments.callee(item);
                	}
                    if(item.tbar){
                        item.tbar = item.tbar.getLibComponent();
                    }
                }
            }
        }else if(NS.isNSComponent(items)){
            config.items = items.getLibComponent()
        }else if(NS.isObject(items)){
            if(items.items){
                arguments.callee(items);
            }
            if(items.tbar){
                items.tbar = items.tbar.getLibComponent();
            }
        }
    }
});/**
 * @class NS.container.Panel
 * @extends NS.container.Container
 *        面板容器
 *
 *          var component = new NS.container.Container({
                width: 500,
                height: 500,
                layout : 'border',
                items : [new NS.Component({
                    width: '100%',
                    height: '40%',
                    region : 'north',
                    padding: 20,
                    style: {
                        color: 'blue',
                        backgroundColor:'blue'
                    }
                }),
                        new NS.Component({
                            width: '100%',
                            height: '40%',
                            region : 'south',
                            padding: 20,
                            style: {
                                color: 'green',
                                backgroundColor:'green'
                            }
                        })
                    ]
            });
         var tbar = new NS.toolbar.Toolbar({
                        items : [{
                            xtype : 'button',
                            name : 'add',
                            text : '新增'
                        },{
                            xtype : 'button',
                            text : '修改',
                            name : 'update'
                        },{
                            xtype : 'button',
                            name : 'delete',
                            text : '删除'
                        }]});
         var panel = new NS.container.Panel({
                        width : 600,
                        height : 600,
                        items : component,
                        title : '1212',
                        tbar : tbar,
                        renderTo: Ext.getBody()
                    });

 */
NS.define('NS.container.Panel', {
    extend: 'NS.container.Container',
    /**
     * @cfg {String} buttonAlign 按钮的位置 含:'right','center','left',默认右侧
     */
    /**
     * @cfg {Object/Object[]} buttons 便利配置用于停靠到面板底部的添加按钮
     */
    /**
     * @cfg {Boolean} collapsed 是否可收缩 默认false
     */
    /**
     * @cfg {Boolean} closable 显示面板的关闭按钮
     */
    /**
     * @cfg {Object} bodyStyle 面板body的样式，配置对象
     */
    /**
     * @cfg {Boolean} autoShow 面板的右部工具栏
     */
    /**
     * @cfg {String} closeAction 关闭模式 'destroy' 销毁, 'hide' 则为隐藏
     */
    /**
     * @cfg {String} title 面板的标题
     */
    /**
     * @cfg {Object/Component} tbar 面板的顶部工具栏
     */
    /**
     * @cfg {Object/Component} bbar 面板的底部工具栏
     */
    /**
     * @cfg {Object/Component} lbar 面板的左部工具栏
     */
    /**
     * @cfg {Object/Component} rbar 面板的右部工具栏
     */
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping: function () {
        this.callParent();
        var getBar = function (value, property, config) {
            config[property] = value.getLibComponent();
            },
            getheader = function(value, property, config){
                config[property] = value;
                config['preventHeader'] = !value;
            };
        this.addConfigMapping({
                buttonAlign:true,
                buttons:true,
                title: true,
                closable: true,
                closeAction:true,
                collapsible : true,//可折叠属性
                collapsed : true,//如果拥有collapsible属性，那么false表示展开，true表示收缩
                collapseDirection  :true,//表示收缩的方向
                tbar: getBar,
                bodyCLs : true,
                header : getheader,
                plain : true,
                bbar: getBar,
                lbar: getBar,
                rbar: getBar,
                buttons:true,//按钮属性
                frame : true,//框架默认背景颜色
                bodyStyle : true
            }
        );
    },
    /**
     *  创建panel
     * @param {Object} config 配置对象
     * @private
     */
    initComponent: function (config) {
        this.component = Ext.create('Ext.panel.Panel', config);
    },
    /**
     * 向panel中更新html(以前的加现在传入的)
     * @param {String} html html串
     * */
    addHtml: function (html1) {
        var ownHtml = this.panel.body.html;
        this.panel.body.update(ownHtml + html1);
    },
    /**
     * 将panel的html置空
     * */
    clearHtml: function () {
        this.panel.body.update("");
    },
    /**
     *  设置标题，用于动态变更标题
     * @param {String} title 标题
     */
    setTitle: function (title) {
        this.component.setTitle(title);
    },
    /**
     * 显示面板容器
     */
    show : function(){
        this.component.show();
    },
    /**
     * 关闭面板容器
     */
    close : function(){
        this.component.close();
    }
});/**
 * @class NS.container.TabPanel
 * @extends NS.container.Panel
 *      标签页容器
 *
 *      var c = new NS.container.Container({
                width: '100%',
                title : '第一个页面',
                height: '20%',
                layout : 'fit',
                region : 'center',
                html : '第一个页面'
            });
        var panel = new NS.container.Panel({
                width : 600,
                height : 600,
                items : component,
                title : '1212',
                tbar : tbar,
            });
        var tab = new NS.container.TabPanel({
                width : 600,
                height : 600,
                title : '1212',
                renderTo: NS.getBody(),
                items : [c,panel]
            });
 */
NS.define('NS.container.TabPanel',{
    extend : 'NS.container.Panel',
    /**
     *  创建一个tabpanel
     * @param config
     */
    initComponent : function(config) {
        this.component = Ext.create('Ext.tab.Panel', config);
    },
    /**
     * 设置该tab为当前显示页面
     * @param {NS.Component} component 将要被设置为激活tab页的组件
     */
    setActiveTab : function(component) {
        this.component.setActiveTab(component.getLibComponent());
    },
    /**
     *获取当前激活的tab页
     * @return {NS.Component} 获取的激活的页面组件对象
     */
    getActiveTab : function(){
        var tab = this.component.getActiveTab();
        return NS.util.ComponentInstance.getInstance(tab);
    }
});
/**
 * @class NS.container.Tree
 * @extends NS.container.Container
 *         树形容器 支持普通树、多选树、多列树、过滤树
 *
         var treeData =[{"checked":true,
                                "cc":"2",
                                "cclx":"ZY",
                                "dm":"0103",
                                "fjdId":"1001000000372700",
                                "id":"1001000000372711",
                                "mc":"工程测量技术",
                                "sfky":"1",
                                "sfyzjd":"0"},........];// 具有树结果关系的数组(Array)数据。

         var treeConfig = {
                            data:treeData,
                            title : null,
                            rootVisible : true,
                            border : true,
                            margin : '0 0 0 0',
                            checkable : true,// 是否是多选树
                            multiple:	true,// 多列树
                            multyFields:[{"columnName":"节点","dataIndex":"text"},
                                         {"columnName":"描述","dataIndex":"cclx"}],
                            filter:true,// 是否具有过滤器
                            iconClsCfg : {
                                'YX':"page-add", // 院系层次上的节点图标样式
                                'ZY':"page-update",// 专业层次上的节点图标样式
                                'BJ':"page-search",
                                'XJD':"page-xtsz",
                                'KM':"page-book"
                            }// 配置各个层次类型上节点的图标样式。
                }
         var treePanel = new NS.container.Tree(obj);

 */
NS.define('NS.container.Tree', {
    extend: 'NS.container.Panel',
    /**
     * 创建panel
     * @param {Object} obj 配置对象
     */
    initComponent: function (obj) {
        var me = this;
        this.configObj = obj;
        this.configFilter(obj);
        var treeStore = Ext.create('Ext.data.TreeStore', {
            root : obj.root
        });
        delete obj.root;
        obj.store = treeStore;
        this.component = Ext.create('Ext.tree.Panel', obj);
        this.editor = new Ext.tree.TreeEditor({
            updateEl: true,
            field: {
                xtype: 'textfield'
            }
        });
        this.modifyValue = {};
        this.editor.addListener('complete',function(editor,newValue,oldValue){
            var node = editor.editorNode,
                id = node.data.id;
            me.modifyValue[id] = newValue;
            node.set('text',newValue);
        });
        this.initMyEvent(obj);
//        this.requestTransfer(obj);
    },
    configFilter: function(obj){
        if(obj.filter){
            this.filterSet(obj);
        }
        if(obj.multiple==true && typeof obj.multyFields !='undefined'){
            this.multipleSet(obj);
        }
        this.translate(obj);
        obj.useArrows = (typeof obj.useArrows !='undefined') ? obj.useArrows : true;
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping: function () {
        this.callParent();
        this.addConfigMapping(
            { checkable: {name: 'checkable'},
                treeData: {name: 'treeData'},
                rootVisible: {name: 'rootVisible'},
                multyFields: {name: 'multyFields'},
                multiple: {name: 'multiple'},
                filter: {name: 'filter'},
                iconClsCfg :{name :'iconClsCfg'},
                useArrows : {name :'useArrows'},
                treeEditor : {name:'treeEditor'}
            }
        );
    },
    initEvents: function () {
        this.callParent();
        this.addEvents(
            /**
             * @event
             */
            'itemclick',
            /**
             * @event
             */
            'itemcontextmenu'
        );
    },
    onItemclick: function () {
        this.component.on('itemclick',function (com, record, item) {
                var data = record.data;
                NS.applyIf(data,record.raw);
                this.fireEvent('itemclick', this, data, item);
            },this);
    },
    onItemcontextmenu: function () {
        this.component.on('itemcontextmenu',function (com, record, item, index, e) {
            this.fireEvent('itemcontextmenu', this, record, record, item, index, e);
        },this);
    },

    /**
     * 转换数据
     * @private
     * @param {Object}
     *                 datacfg 树形面板的实际数据。
     */
    translate: function (obj) {
        var checkable = Boolean(obj.checkable),
            datacfg = obj.treeData,
            nodeHash = {},
            rootId = 0,
            dataObj = null,
            node={},
            hashNode = null;
        if (datacfg instanceof Array) {
        	datacfg.push({
        		children:[],
        		text:'',
        		expanded: true,
        		id:0
        	});
            if (datacfg.length == 0) {
                this.configObj.root = {
                    text: "根节点",
                    expanded: true,
                    children: []
                };
            }
            for (var i = 0,len=datacfg.length; i < len; i++) {
                dataObj = datacfg[i];
                node = {
                    pid: dataObj.fjdId||dataObj.fjdid,
                    text: dataObj.mc,
                    expanded: dataObj.expanded!=undefined ? dataObj.expanded : false,
                    leaf: dataObj.leaf!=undefined ? dataObj.leaf : false,
                    children: [],
                    id: dataObj.id,
                    cc: dataObj.cc,
                    cclx: dataObj.cclx
                }
                if(obj.iconClsCfg){
                    node.iconCls = obj.iconClsCfg[dataObj.cclx];
                }
                NS.applyIf(node,dataObj);
                if (checkable) {
                    node.checked = Boolean(Number(dataObj.checked));
                }
                nodeHash[dataObj.id] = node;
            }
            for (var key in nodeHash) {
                hashNode = nodeHash[key];
                var nodesPid = hashNode.pid;
                if (nodeHash[nodesPid]) {
                    nodeHash[nodesPid].children.push(hashNode);
                    nodeHash[nodesPid].leaf = false;
                } else {
                    rootId = hashNode.id;
                }
            }
            this.configObj.root = nodeHash[rootId];
        } else {
            this.configObj.root = datacfg;
        }
    },
    /**
     * @private
     * @param {Object} 多行树。
     */
    multipleSet: function (obj) {
        var fields = new Array();
        var columns = new Array();
        var fieldObj = obj.multyFields;
        if (obj.multiple && fieldObj) {
            for (var i = 0,len=fieldObj.length; i < len; i++) {
                var afieldObj = fieldObj[i];
                var columnObj = {
                    text: afieldObj.columnName,
                    width: afieldObj.width||200,
                    dataIndex: afieldObj.dataIndex,
                    hidden : afieldObj.hidden||false
                }
                if (afieldObj.dataIndex == 'text') {
                    columnObj.xtype = 'treecolumn';
                }
                fields.push(afieldObj.dataIndex);
                columns.push(columnObj);
            }
            obj.fields = fields;
            obj.columns = columns;
        }
    },
    /**
     * @private
     * @param {Object} obj 树节点过滤器的配置转换方法。
     */
    filterSet: function (obj) {
        var me = this;
        if (obj.filter) {
            var filterField = obj.filter.field || 'text';
            var triggerCfg = {
                xtype: 'trigger',
                triggerCls: 'x-form-clear-trigger',
                onTriggerClick: function () {
                    this.setValue('');
                },
                enableKeyEvents: true,
                listeners: {
                    keyup: {buffer: 150, fn: function (field, e) {
                        if (Ext.EventObject.ESC == e.getKey() && !this.getRawValue()) {
                            field.onTriggerClick();
                        }
                        else {
                            var val = this.getRawValue();
                            var re = new RegExp('.*' + val + '.*', 'i');
                            var rootNode = me.component.getRootNode();

                            me.component.getStore().setRootNode(rootNode);
                        }
                    }}
                }
            }
            obj.tbar = [obj.filter.labelName || '过滤', triggerCfg];
        }
    },
    /**
     * 初始化树的默认事件，例如：节点选择行为。
     * @private
     * @param {Object}
     *                 obj 配置参数
     */
    initMyEvent: function (obj) {
        var me = this;
        var changeFun = function (node, checked) {
            node.expand();
            node.set('checked', checked);
            node.eachChild(function (child) {
                child.set('checked', checked);
                changeFun(child, checked);
            });
        }
        var checkedParent = function (node, checked) {
            var parentNode = node.parentNode;
            if (checked && parentNode) {
                parentNode.set('checked', checked);
                arguments.callee(parentNode, checked);
            } else if (parentNode) {
                var temp = 0;
                parentNode.eachChild(function (child) {
                    if (child.get('checked')) {
                        temp++;
                    }
                });
                if (temp == 0) {
                    parentNode.set('checked', false);
                }
            }
        }
        this.component.addListener('checkchange', function (node, checked, opts) {
            changeFun(node, checked);
            checkedParent(node, checked)
        }, this.component);

        if(obj.treeEditor){
            this.component.addListener('itemdblclick', function (com, record, item, index, e) {
                var div = Ext.fly(item).down('div');
                me.editor.startEdit(div,record.getData().text);
                me.editor.editorNode = record;
            }, this.component);
        }
    },
    requestTransfer : function(obj){
        this.component.addListener('beforeitemexpand',function(node,index,item,opts){
            if(this.configObj.serviceKey){
                return;
            }
            var nodedata = node.data;
            if(node.childNodes.length==0 && nodedata.leaf==false){
                var model = new NS.mvc.Model();
                var params ={
                    id:nodedata.id,
                    parendId : nodedata.parendId,
                    text : nodedata.text,
                    leaf : nodedata.leaf
                }
                model.callService([{key:this.serviceKey,params:params}],function(respData){
                    if(respData[this.serviceKey] instanceof Array){
                        node.appendChild(this.translateForExpendEvent(respData[this.serviceKey]));
                    }
                },this);
            }
        },this);
    },
    translateForExpendEvent :function(respArray){
        var i= 0,nodei = new Array(),len=respArray.length,newCfg={},respi=null;
        for(;i<len;i++){
            respi= respArray[i];
            newCfg = {
                pid: respi.fjdId,
                text: respi.mc,
                expanded: true,
                leaf: respi.sfyzjd==0?false:true,
                children:[]
            }
            nodei.push(newCfg);
        }
        return nodei;
    },
    itemRightClickFun: function (com, record, item, index, e) {
        e.preventDefault();
        e.stopEvent();
        Ext.create('Ext.menu.Menu', {
            width: 60,
            margin: '0 0 10 0',
            floating: true,
            items: [
                /*{
                    iconCls: 'page-add',
                    text: '新增',
                    handler: function () {
                        record.appendChild({text: '新建节点', leaf: true, checked: false, cclx: "ZY"});
                    }
                },
                {
                    iconCls: 'page-delete',
                    text: '删除',
                    handler: function () {
                        record.remove();
                    }
                },*/
                {
                    iconCls: 'page-expand',
                    text: '展开',
                    disabled: record.data.leaf,// 叶子节点不能展开
                    handler: function () {
                        var store = com.getStore();
                        var node = store.treeStore.getNodeById(record.data.id);
                        node.expand();
                    }
                },
                {
                    iconCls: 'page-collapse',
                    text: '收缩',
                    disabled: record.data.leaf,// 叶子节点不能收缩
                    handler: function () {
                        var store = com.getStore();
                        var node = store.treeStore.getNodeById(record.data.id);
                        node.collapse();
                    }
                }
            ]
        }).showAt(e.getXY());
    },
    /**
     * 获取树中所有被选择的节点的数据。
     * @return {Array} 返回数中所有被选择的节点的数据Model数组。
     */
    getChecked: function () {
        var extModels = this.component.getChecked(),
            result = new Array(),
            len = extModels.length,
            i=0;
        for (; i < len; i++) {
            result.push(extModels[i].raw);
        }
        return result;
    },
    /**
     * 获取树中最后一次被选择的节点。
     * @return {Object}
     */
    getSelectionModel : function(){
        var model = this.component.getSelectionModel().getLastSelected();
        return model==null?null:model.raw;
    },
    /**
     * 获取树中所有被选择的叶子节点的数据。
     * @return {Array} 返回数中所有被选择的节点的数据Model数组。
     */
    getCheckedLeaf: function () {
        var extModels = this.component.getChecked();
            result = new Array(),
            len = extModels.length,
            i=0;
        for (; i < len; i++) {
            if (extModels[i].getData().leaf) {
                result.push(extModels[i].raw);
            }
        }
        return result;
    },
    /**
     * 刷新树。
     * @param {Object} 格式为{data:[你的数据]}
     */
    refresh: function (data) {
        this.configObj.treeData = data;
        this.translate(this.configObj);
        this.component.getStore().setRootNode(this.configObj.root);
        /*this.component.expandAll();*/
    },
    /**
     * 返回树面板的工具栏组件。
     * @returns {Object} 工具栏。
     */
    getTbar: function () {
        var treeTbar = new NS.toolbar.Toolbar();
        treeTbar.component = this.component.getComponent('tbar');
        return treeTbar;
    },
    /**
     * 根据节点id获取节点数据。
     * @param {Object} id  节点id
     * @return {Object} 节点数据对象
     */
    getNodeDataById: function (id) {
        var store = this.component.getStore(),
            node = store.getNodeById(id);
        return node.getData();
    },
    /**
     * 全选树中所有节点。
     */
    checkAllNode : function(){
//        this.component.fireEvent("checkchange",this.component.getRootNode(),true,this.component);
        this.checkedChildren(this.component.getRootNode(),true);
    },
    checkedChildren : function(node,check){
        var childs = node.childNodes;
        var itertor = childs;// 需要迭代的节点
        //向下子节点
        if(itertor&&itertor.length>0){
            var needToIter = [];// 待被迭代的节点
            while (itertor.length != 0) {
                for (var i = 0, len = itertor.length; i < len; i++) {
                    var c = itertor[i];// 获取子节点
                    c.set('checked', check);// 设置该节点被选择
                    if (c.childNodes)
                        needToIter = needToIter.concat(c.childNodes);
                }
                itertor = needToIter;
                needToIter = [];
            }
        }
    },
    /**
     * 反选树中所有节点。
     */
    unCheckAllNode : function(){
        //this.component.fireEvent("checkchange",this.component.getRootNode(),false,this.component);
        this.checkedChildren(this.component.getRootNode(),false);
    },
    /**
     * 展开树所有节点。
     */
    expandAll : function(){
        this.component.expandAll();
    },
    /**
     * 收缩树所有节点。
     */
    collapseAll:function(){
        this.component.collapseAll();
    },
    getModifyValue : function(){
        var modifyValue = this.modifyValue,
            values = new Array();
        for(var i in modifyValue){
            values.push({
                id:i,
                text:modifyValue[i]
            })
        }
        return values;
    }
});/**
 * @class NS.container.SwitchContainer
 * @extends NS.container.Container
 *  层切换容器，用于不同层之间的切换
 *
 *      var switch = new NS.container.SwitchContainer({
 *          items : [
 *              {
 *                  name : 'component1',
 *                  scope : this,//init方法的作用域
 *                  init : this.initComponent1// init方法会接受到三个参数，第一个参数是SwitchContainer
 *              },                          // 第二个参数是：一个容器，你可以将你自己组装好的组件放置到该容器里
 *                                          //第三个参数是：配置参数（该参数是通过switchTo方法传递的）
 *              {
 *                  name : 'component2',
 *                  scope : this,
 *                  init : this.initComponent2
 *              }
 *          ]
 *      });
 *      switch.switchTo('component1',{name : 'age'});
 *
 */
NS.define('NS.container.SwitchContainer',{
    extend : 'NS.container.Container',
    /**
     * @cfg {Object[]} items 子页面配置项
     *
     *     对象配置数组，配置对象有3个键，name、scope、init
     *     1 name 代表对应子页面的名字
     *     2 init 代表对应子页面的初始化函数// init方法会接受到三个参数，第一个参数是SwitchContainer本身,
     *                                      // 第二个参数是：一个容器，你可以将你自己组装好的组件放置到该容器里
     *                                          //第三个参数是：配置参数（该参数是通过switchTo方法传递的）
     *     3 scope 代表init函数的作用域
     *     参照类前面的代码块
     */
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('defaultIndex');
    },
    /**
     * @cfg {Number} defaultIndex 默认切换到第几个页面，如果不进行设置，那么默认会切换到第一个页面
     * @private
     */
    initComponent : function(config){
        var basic = {
            layout : 'card'
        };
        if(config.layout){
        	delete config.layout;//禁止外部覆盖layout属性
        }
        NS.apply(basic,config);
        this.component = Ext.create('Ext.container.Container',basic);
        if(!config.defaultIndex)
           config.defaultIndex = 0;
           this.switchIndex(config.defaultIndex);

    },
    /**
     * @private
     * @param config
     */
    procressItems : function(config){
        var map = this.initMap = {},items = config.items||[],i= 0,len=items.length,item,container;
        for(i;i<len;i++){
            item = items[i];
            container = Ext.create('Ext.container.Container',{
                width : '100%',
                height : '100%',
                layout : 'fit'
            });
            map[item.name]  = {
                scope : item.scope,
                name : item.name,
                init : item.init,
                index : i,
                isInit : false,
                container : container
            }
            items[i] = container
        }
    },
    /**
     * 根据索引获取配置项
     * @param {Number}index
     * @return {Object}
     * @private
     */
    getItemByIndex : function(index){
        var initMap = this.initMap, i,item;
        for(i in this.initMap){
            item = initMap[i]
            if(item['index'] == index){
               return item;
            }
        }
    },
    /**
     * 切换到name为指定值的页,默认是如果这个页面的组件已经被初始化，那么init方法将不在被调用，如果想强制调用init方法的话，
     *   需要多传递一个标识符forceToCall ，并且其值为true
     * @param {String} name 待切换的页面的name
     * @param {Object} params 需要给该页面传递的初始化函数的参数
     * @param {Boolean} [forceToCall] true强制调用初始化init方法,false则不调用init方法
     */
    switchTo : function(name,params,forceToCall){
        var layout = this.component.getLayout(),item = this.initMap[name],container = item.container,packContainer;
        if(item){
            layout.setActiveItem(container);
            if(!item.isInit || forceToCall){
                packContainer = NS.util.ComponentInstance.getInstance(container);
                item.init.call(item.scope||this,this,packContainer,params||{});
                item.isInit = true;
            }
        }
    },
    /**
     * 将页面切到items数组配置项中某个下标对应的页面上
     * @param {Number} index 组件索引
     * @param {Object} params 参数对象
     */
    switchIndex : function(index,params){
        var layout = this.component.getLayout(),item = this.getItemByIndex(index),container = item.container,packContainer;
        if(item){
            layout.setActiveItem(container);
            if(!item.isInit){
                packContainer = NS.util.ComponentInstance.getInstance(container);
                item.init.call(item.scope||this,this,packContainer,params||{});
                item.isInit = true;
            }
        }
    }
});/**
 * @class NS.container.TabSwitchContainer
 * @extends NS.container.Panel
 *  tab页切换容器，用于不同tab页之间的切换
 *
 *      var switch = new NS.container.TabSwitchContainer({
 *          items : [
 *              {
 *                  name : 'component1',
 *                  title : 'tab1',
 *                  scope : this,//init方法的作用域
 *                  init : this.initComponent1// init方法会接受到三个参数，第一个参数是SwitchContainer
 *              },                          // 第二个参数是：一个容器，你可以将你自己组装好的组件放置到该容器里
 *                                          //第三个参数是：配置参数（该参数是通过switchTo方法传递的）
 *              {
 *                  name : 'component2',
 *                  title : 'tab2',
 *                  scope : this,
 *                  init : this.initComponent2
 *              }
 *          ]
 *      });
 */
NS.define('NS.container.TabSwitchContainer',{
    extend : 'NS.container.Panel',
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('defaultIndex');
    },
    /**
     *  创建一个tabpanel
     * @param config
     */
    initComponent : function(config) {
        this.component = Ext.create('Ext.tab.Panel', config);
        this.component.on('tabchange',function(tabpanel,newcard,oldcard){
              this.switchTo(newcard.name);
        },this);
        if(!config.defaultIndex)
            config.defaultIndex = 0;
        this.switchIndex(config.defaultIndex);
    },
    /**
     * 处理items项
     * @private
     * @param {配置参数}config
     */
    procressItems : function(config){
        var map = this.initMap = {},items = config.items||[],i= 0,len=items.length,item,container;
        for(i;i<len;i++){
            item = items[i];
            container = Ext.create('Ext.container.Container',{
                width : '100%',
                height : '100%',
                title : item.title,
                name : item.name,
                layout : 'fit'
            });
            map[item.name]  = {
                scope : item.scope,
                name : item.name,
                init : item.init,
                index : i,
                isInit : false,
                container : container
            }
            items[i] = container
        }
    },
    /**
     * 切换到name为指定值的页,默认是如果这个页面的组件已经被初始化，那么init方法将不在被调用，如果想强制调用init方法的话，
     *   需要多传递一个标识符forceToCall ，并且其值为true
     * @param {String} name 待切换的页面的name
     * @param {Object} params 需要给该页面传递的初始化函数的参数
     * @param {Boolean} [forceToCall] true强制调用初始化init方法,false则不调用init方法
     */
    switchTo : function(name,params,forceToCall){
        var component = this.component,item = this.initMap[name],container = item.container,packContainer;
        if(item){
            component.setActiveTab(container);
            if(!item.isInit || forceToCall){
                packContainer = NS.util.ComponentInstance.getInstance(container);
                item.init.call(item.scope||this,this,packContainer,params);
                item.isInit = true;
            }
        }
    },
    /**
     * 根据索引获取配置项
     * @param {Number}index
     * @return {Object}
     */
    getItemByIndex : function(index){
        var initMap = this.initMap, i,item;
        for(i in this.initMap){
            item = initMap[i]
            if(item['index'] == index){
                return item;
            }
        }
    },
    /**
     * 将页面切到items数组配置项中某个下标对应的页面上
     * @param {Number} index 组件索引
     * @param {Object} params 参数对象
     */
    switchIndex : function(index,params){
        var layout = this.component.getLayout(),item = this.getItemByIndex(index),container = item.container,packContainer;
        if(item){
            layout.setActiveItem(container);
            if(!item.isInit){
                packContainer = NS.util.ComponentInstance.getInstance(container);
                item.init.call(item.scope||this,this,packContainer,params||{});
                item.isInit = true;
            }
        }
    },
    /**
     * 设置该tab为当前显示页面
     * @private
     * @param {NS.component.Component} 将要被设置为激活tab页的组件
     */
    setActiveTab : function(component) {
        this.component.setActiveTab(component.getLibComponent());
    },
    /**
     *获取当前激活的tab页
     * @private
     */
    getActiveTab : function(){
        var tab = this.component.getActiveTab();
        return NS.util.ComponentInstance.getInstance(tab);
    }
});
/**
 * 快速提示工具 
 */
NS.define('NS.tip.QuickTip', {
    extend: 'NS.container.Panel',
    /**
     * @cfg {Boolean} hideCollapseTool true隐藏工具条栏
     */
    /**
     * @cfg {Boolean} overlapHeader 
     */
    /**
     * @cfg {Boolean} autoShow　true自动显示
     */
    /**
     * @cfg {Number} dismissDelay 自动展现时间,这个时间段之外组件隐藏
     */
    /**
     * @cfg {Number} hideDelay 隐藏延时
     */
    initComponent:function(cfg){
    	var basic = {
            width: 200,
            autoShow:true,
            shadow: false,
            bodyBorder: false,
            frameHeader: false,
            hideCollapseTool: true,
            overlapHeader: true,
            bodyBorder: false
        };
    	NS.apply(basic,cfg);
    	this.component = Ext.create('Ext.tip.QuickTip',basic);
    },
    initConfigMapping:function(){
    	this.callParent();
    	this.addConfigMapping({
    		hideCollapseTool:true,
    		overlapHeader:true,
    		autoShow:true,
    		dismissDelay:true,
    		hideDelay:true
    	});
    }
    //其他方法均基础于panel
});/**
 * Internal utility class that provides default configuration for cell editing.
 * @private
 */
Ext.define('Ext.tree.TreeEditor', {
    extend:'Ext.Editor',
    constructor:function (config) {
        config = Ext.apply({}, config);

        if (config.field) {
            config.field.monitorTab = false;
        }
        this.callParent([config]);
    },

    /**
     * @private
     * Hide the grid cell text when editor is shown.
     *
     * There are 2 reasons this needs to happen:
     *
     * 1. checkbox editor does not take up enough space to hide the underlying text.
     *
     * 2. When columnLines are turned off in browsers that don't support text-overflow:
     *    ellipsis (firefox 6 and below and IE quirks), the text extends to the last pixel
     *    in the cell, however the right border of the cell editor is always positioned 1px
     *    offset from the edge of the cell (to give it the appearance of being "inside" the
     *    cell.  This results in 1px of the underlying cell text being visible to the right
     *    of the cell editor if the text is not hidden.
     *
     * We can't just hide the entire cell, because then treecolumn's icons would be hidden
     * as well.  We also can't just set "color: transparent" to hide the text because it is
     * not supported by IE8 and below.  The only remaining solution is to remove the text
     * from the text node and then add it back when the editor is hidden.
     */
    onShow:function () {
        var me = this,
            innerNode = me.boundEl.first(),
            lastChild,
            textNode;

        if (innerNode) {
            lastChild = innerNode.dom.lastChild;
            if (lastChild && lastChild.nodeType === 3) {
                // if the cell has a text node, save a reference to it
                textNode = me.nodeTextNode = innerNode.dom.lastChild;
                // save the cell text so we can add it back when we're done editing
                me.nodeTextValue = textNode.nodeValue;
                // The text node has to have at least one character in it, or the cell borders
                // in IE quirks mode will not show correctly, so let's use a non-breaking space.
                textNode.nodeValue = '\u00a0';
            }
        }
        me.callParent(arguments);
    },
    getTextNode : function(innerNode){
        var me = this,
            textNode,
            lastChild;
        if (innerNode) {
            lastChild = innerNode.dom.lastChild;
            if (lastChild && lastChild.nodeType === 3) {
                // if the cell has a text node, save a reference to it
                textNode = me.nodeTextNode = innerNode.dom.lastChild;
                // save the cell text so we can add it back when we're done editing
                me.nodeTextValue = textNode.nodeValue;
                // The text node has to have at least one character in it, or the cell borders
                // in IE quirks mode will not show correctly, so let's use a non-breaking space.
                textNode.nodeValue = '\u00a0';
            }
        }
        return textNode;
    },
    /**
     * @private
     * Show the node  text when the editor is hidden by adding the text back to the text node
     */
    onHide:function () {
        var me = this,
            innerNode = me.boundEl,
            textNode = this.getTextNode(innerNode);

        if (innerNode && me.nodeTextNode) {
            textNode.nodeValue = me.nodeTextValue;
//            delete me.nodeTextNode;
//            delete me.nodeTextValue;
        }
        me.callParent(arguments);
    },

    /**
     * @private
     * Fix checkbox blur when it is clicked.
     */
    afterRender:function () {
        var me = this,
            field = me.field;

        me.callParent(arguments);
        if (field.isXType('checkboxfield')) {
            field.mon(field.inputEl, {
                mousedown:me.onCheckBoxMouseDown,
                click:me.onCheckBoxClick,
                scope:me
            });
        }
    },

    /**
     * @private
     * Because when checkbox is clicked it loses focus  completeEdit is bypassed.
     */
    onCheckBoxMouseDown:function () {
        this.completeEdit = Ext.emptyFn;
    },
    /**
     * Ends the editing process, persists the changed value to the underlying field, and hides the editor.
     * @param {Boolean} [remainVisible=false] Override the default behavior and keep the editor visible after edit
     */
    completeEdit:function (remainVisible) {
        var me = this,
            field = me.field,
            value;

        if (!me.editing) {
            return;
        }

        // Assert combo values first
        if (field.assertValue) {
            field.assertValue();
        }

        value = me.getValue();
        if (!field.isValid()) {
            if (me.revertInvalid !== false) {
                me.cancelEdit(remainVisible);
            }
            return;
        }

        if (String(value) === String(me.startValue) && me.ignoreNoChange) {
            me.hideEdit(remainVisible);
            return;
        }

        if (me.fireEvent('beforecomplete', me, value, me.startValue) !== false) {
            // Grab the value again, may have changed in beforecomplete
            value = me.getValue();
            if (me.updateEl && me.boundEl) {
                var textNode = me.getTextNode(me.boundEl);
                textNode.nodeValue = value;
            }
            me.hideEdit(remainVisible);
            me.fireEvent('complete', me, value, me.startValue);
        }
    },

    // private
    onShow:function () {
        var me = this;

        me.callParent(arguments);
        if (me.hideEl !== false) {
            me.boundEl.hide();
        }
        me.fireEvent("startedit", me.boundEl, me.startValue);
    },

    /**
     * @private
     * Restore checkbox focus and completeEdit method.
     */
    onCheckBoxClick:function () {
        delete this.completeEdit;
        this.field.focus(false, 10);
    },
    /**
     * @cfg {Number[]} offsets
     * The offsets to use when aligning (see {@link Ext.Element#alignTo} for more details.
     */
    offsets: [15, 0],
    alignment:"tl-tl",
    hideEl:false,
    cls:Ext.baseCSSPrefix + "small-editor " + Ext.baseCSSPrefix + "grid-editor",
    shim:false,
    shadow:false
});

/**
 * @class NS.toolbar.Toolbar
 * @extends NS.container.Container
 *      工具栏容器
 */
NS.define('NS.toolbar.Toolbar', {
    extend: 'NS.container.Container',
    /**
     * 创建一个tbar
     * @param {Object} config 配置对象
     */
    initComponent: function (config) {
        this.component = Ext.create('Ext.toolbar.Toolbar', config);
    },
    /***
     * 处理嵌套的组件层次
     * @param {Array} items 子组件数组
     */
    processChildItems: function (config) {
        var item, items = config.items || [];
        if (NS.isArray(items)) {
            for (var i = 0, len = items.length; i < len; i++) {
                item = items[i];
                if (NS.isNSComponent(item)) {
                    items[i] = item.getLibComponent();
                } else if (item.items) {
                    items[i] = arguments.callee(item.items);
                } else if (NS.isObject(item)) {
                		if (item.xtype == "button") {
                            items[i] = new Ext.button.Button(item);
                        }else{
                        	items[i] = NS.util.FieldCreator.createField(item);
                        }
                }
            }
            return;
        } else if (NS.isNSComponent(items)) {
            config.items = items.getLibComponent();
            return;
        } else if (NS.isObject(items)) {
            var component = NS.util.FieldCreator.createField(item);
            if (component) {
                config.items = item;
            } else {
                if (items.xtype == "button") {
                    config.items = new Ext.button.Button(item);
                }
            }
            return;
        }
    },
    /**
     *获取tbar内所有field的值,如果field的值为空，则不返回
     * @return {Object}
     */
    getValues: function () {
        var fields = this.component.query("field"), i = 0, field, len, values = {}, value, name;
        for (i, len = fields.length; i < len; i++) {
            field = fields[i];
            value = field.getValue();
            name = field.name;
            if (value) {
                values[name] = value;
            }
        }
        return values;
    },
    /**
     *获取tbar内指定的name的field的值,如果field的值为空，则不返回
     * var tbar = new NS.toolbar.MultiLineTbar();
     * var values = tbar.getValuesByName('name1','name2','name3');
     * @param {String..} name 多个名称参数
     * @return {Object} 参数对象
     */
    getValuesByName: function (name) {
        var fields = this.component.query("field"), i = 0, field, len, values = {}, value, na;
        for (i, len = arguments.length; i < len; i++) {
            values[arguments[i]] = undefined;
        }
        for (i = 0, len = fields.length; i < len; i++) {
            field = fields[i];
            value = field.getValue();
            na = field.name;
            if (value && values.hasOwnProperty(na)) {
                values[na] = value;
                if (!values[na]) {
                    delete values[na];
                }
            }
        }
        return values;
    },
    /**
     * 将属性name为'name'的对象替换为obj
     * @param name 属性name的值
     * @param obj 待替换对象
     */
    replace:function(name,obj){
    	//实现思路：查找到name位置,先移除,再添加（因为关系到ext对象与封装对象转换问题这里仅提出实现思路）
    	//先将items里对象存储到map里,通过name为键 对象为值,同时动态添加index属性标识该对象所处的位置
    	//if(!this.configMap){this.comfigMap = this.configMapping();}
    	//将待移除对象的index取出，
    	//现将该对象移除 调用this.component.remove(对象,false);建议用false 是不销毁这个对象,因为不知道这个对象是否在之后还是用
    	//然后调用this.component.add(this.component,待替换对象,index);
    	//整个功能完成，注意事项：'-','->'这些均不在此替换范围,如果今后添加对这些对象支持,还需进一步处理
    },
    /**
     * items里参数对象映射表 name为键 参数对象为值,同时添加index属性标识位置,如果参数里包含'-','->'等目前
     * 封装对象暂不支持的对象，index++即可
     */
    configMapping:function(){
    	//遍历items 得到map
    }
});/**
 * @class NS.toolbar.EntityToolbar
 * @extend NS.Base
 *  实体Toolbar ，用于根据实体属性表中的组件来创建tbar工具栏
 */
NS.define('NS.toolbar.EntityToolbar',{
    singleton : true,
    /**
     * var util = NS.toolbar.EntityToolbar;
     * var toolbar = util.create({
     *     data : header,
     *     items : ['xn','xq',new NS.form.field.Text({name : 张三})]
     * });
     * var xn = toolbar.queryComponentByName(xn);
     * xn.on('select',function(){
     *
     * });
     * @param config
     */
    create : function(config){
        var data = config.data;
        this.initFieldsConfig(config);
        var items = this.processItems(config);
        var basic = {
            items : items
        }
        var toolbar = new NS.toolbar.Toolbar(basic);
        return toolbar;
    },
    processItems : function(config){
        var items = config.items||[],array = [],item,
            createField = NS.Function.alias(NS.util.FieldCreator,'createField'),basic,component;
        for(var i = 0,len=items.length;i<len;i++){
            item = items[i];
            if(NS.isString(item)){
               basic = this.getFieldConfig(item);
               component = createField(this.fcMap[item],basic);
            }else if(NS.isObject(item)){
               component = item;
            }else if(NS.isNSComponent(item)){
               component = item.getLibComponent();
            }
            array.push(component);
        }
        return array;
    },
    /**
     * 初始化fields配置
     * @param config
     * @private
     */
    initFieldsConfig : function(config){
        var data = config.data
        var map = this.fcMap = {};
        for(var i= 0,len = data.length;i<len;i++){
            var item = data[i];
            map[item.name] = item;
        }
    },
    /**
     * 通过name来获取field的配置参数
     * @param {String} name 组件对应的name
     * @private
     */
    getFieldConfig : function(name){
        var C = this.fcMap[name],xtype = C.xtype;
        if(!C){
            NS.error({
                sourceClass : 'NS.toolbar.EntityToolbar',
                sourceMethod : 'getFieldConfig',
                msg : 'name对应的组件没有查找到：name为'+name
            });
        }
        var basic = {
            width : 200,
            labelWidth : 60,
            fieldLabel : C.nameCh,// 属性字段标签
            maxLength : C.dbLength,
            readOnly : !C.editable//是否可编辑1 可编辑 0 不可编辑
        };
        return basic;
    }
});/**
 * 多行工具栏组件。
 * @class NS.toolbar.MultiLineTbar
 * @extends NS.toolbar.Toolbar
 */
NS.define('NS.toolbar.MultiLineTbar',{
	extend:'NS.toolbar.Toolbar',
    /**
     *@cfg {Number} contentWidth tbar内容的宽度,定义了所有的子组件所能占tbar的宽度
     */
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('contentWidth');
    },
	initComponent:function(config){
        var basic = {
            //layout : 'fit',
            margin : '1',
            items : {
                xtype : 'container',
                layout : 'column',
                items : config.items,
                defaults : {
                    margin : '4'
                },
                width : config.contentWidth||'100%'
            }
        };
        delete config.items;
        delete config.contentWidth;
        NS.apply(basic,config);
		this.component = Ext.create('Ext.toolbar.Toolbar',basic);
	}
});/***
 * @class NS.grid.Grid
 * @extends NS.container.Panel
 *   例如

         var data = NS.E2S(headerData);
         var grid = new NS.grid.Grid({
                        plugins : [new NS.grid.plugin.CellEditor(),
                                    new NS.grid.plugin.HeaderQuery()],
                        columnData : data,
                        serviceKey : 'queryGridData',//你也可以写成带参数的形式serviceKey : {
                                                                                //key : 'queryGridData',
                                                                                // params : {
                                                                                //    entityName : this.entityName
                                                                                // }
                                                                                //}
                        proxy : new NS.mvc.Model(
                                        {serviceConfig:{
                                                    'queryGridData' : 'baseservice'
                                                    }
                                        }),
                        fields : ['xh','xm'],
                        data : showData.data,
                        columnConfig : [{
                              name : 'xh',
                              locked : true,able
                              editable : false,//设置该列是否可编辑
                              index : 4
                        },
                         {
                         name : 'xh',
                         editor : new NS.form.field.Text({})
                         },
                         {
                         name : 'xh',
                         editorConfig : {//后台实体属性表组件的扩展数据配置
                                    displayField : 'dm',
                                    fields : ['id','dm','mc']
                                    hidden : true
                            }
                         },
                            { text: '查看列',
                              name :'see',
                              xtype : 'buttoncolumn',
                              buttons : [
                                {
                                    buttonText : '查看',
                                    style : {
                                        color : 'red',
                                        font : '18px'
                                    }
                                },
                                {
                                    buttonText : '审核'
                                }
                        ]}
                        ]
                    });

 */
NS.define('NS.grid.Grid', {
	extend : 'NS.container.Panel',
    /**
     *@cfg {Boolean} paging 是否开启分页，默认为true。
     */
    /**
     * @cfg {Object} columnConfig 列配置
     */
    /**
     * @cfg {Object} modelConfig 域属性配置以及数据配置
     */
    /**
     * @cfg {Object} data 配置的grid要显示的数据，数据格式必须为 {data:[],success:true,count:13}
     */
    /**
     * @cfg {Object[]} columnData  列组件数据配置
     */
    /**
     * @cfg {Boolean} multiSelect  是否多选,默认为true
     */
    /**
     * @cfg {Boolean} disableSelect  禁止选择，默认为false
     */
    /**
     * @cfg {Boolean} lineNumber  是否显示行号,默认为false
     */
	/**
     * @cfg {String/Object} serviceKey  service对应的key值,该值需要配置在model中
     */
	/**
     * @cfg {NS.mvc.Model} proxy  grid的数据加载器,即{NS.mvc.Model}的一个实例
     */
    /**
     * @cfg {Boolean} checked  grid是否出现选择框
     */
    /**
     * @cfg {Array} fields 用于追加对象域属性的配置
     */
    /**
     * @cfg {Number} pageSize  grid 的每页数据量
     */
	/**
	 *构造函数
	 *@constructor
	 */
//	constructor : function() {
//		this.callParent(arguments);//调用父类构造函数
//	},
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping(
            'data',
            'fields',
            'modelConfig',
            'columnConfig',
            'columnData',
            'columnLines',
            'paging',
            'multiSelect',
            'disableSelect',
            'lineNumber',
            'defaultColumnConfig',//column默认配置属性
            'checked'//是否有选择框
        );
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents : function(){
        this.callParent();
        this.addEvents(
            /**
             * @event viewready grid的所有应该展示的界面展示完毕(包括表头，数据区的展示数据)
             * @param {NS.grid.Grid} grid
             */
            'viewready',
            /**
             * @event rowdbclick grid行被双击后触发的事件
             * @param {NS.grid.Grid} grid
             * @param {Object} rowData 行json数据
             * @param {HtmlElement} item  行的html对象
             * @param {Number} rowIndex  行序号
             * @param {NS.Event} event 事件对象
             */
            'rowdbclick',
            /**
             * @event rowclick grid行被单击后触发的事件
             * @param {NS.grid.Grid} grid
             * @param {Object} rowData 行json数据
             * @param {HtmlElement} item  行的html对象
             * @param {Number} rowIndex  行序号
             * @param {NS.Event} event 事件对象
             */
            'rowclick',
            /**
             * @event select grid行被选中后触发的事件
             * @param {NS.grid.Grid} grid
             * @param {Object} rowData 行json数据
             * @param {Number} rowIndex  行序号
             */
            'select',
            /**
             * @event beforeload grid自加载数据之前触发该事件
             * @param {NS.grid.Grid} grid
             * @param {Object} params 参数对象
             */
            'beforeload',
            /**
             * @event load grid自加载数据之后触发该事件
             * @param {NS.grid.Grid} grid
             * @param {Array[]} data 加载过来的数据
             */
            'load',
            /**
             * @event beforeedit grid编辑组件显示前触发
             * @param {NS.grid.Grid} grid
             * @param {Object} params 参数对象
             */
            'beforeedit',
            /**
             * @event beforeedit grid编辑结束后触发
             * @param {NS.grid.Grid} grid
             * @param {Object} params 参数对象
             */
            'afteredit'

        );
    },
    /**
     * 当添加viewready事件的时候调用
     * @private
     */
    onViewready : function(){
        this.component.on('viewready',function(event, element){
            this.fireEvent('viewready', this);
        },this);
    },
    /**
     * 当行被双击的时候触发
     * @private
     */
    onRowdbclick : function(){
        this.component.on('itemdblclick',function(view, record,item,rowindex,e){
            NS.Event.setEventObj(e);
            this.fireEvent('rowdbclick', this,record.getData(),item,rowindex,NS.Event);
        },this);
    },
    /**
     * 当行被双击的时候触发
     * @private
     */
    onRowclick : function(){
        this.component.on('itemclick',function(view, record,item,rowindex,e){
            NS.Event.setEventObj(e);
            this.fireEvent('rowclick', this,record.getData(),item,rowindex,NS.Event);
        },this);
    },
    /**
     * 当行被选中后触发
     * @private
     */
    onSelect : function(){
        this.component.on('select',function(rowmodel, record,rowindex){
            this.fireEvent('select', this,record.getData(),rowindex);
        },this);
    },
    /**
     * 标识Grid被编辑之前触发
     * @private
     */
    onBeforeedit : function(){
        this.component.on('beforeedit',function(editing,e){
            var params = {
                name :e.column.dataIndex,
                rowIndex :e.rowIdx,
                colIndex :e.colIdx,
                originalValue :e.originalValue,
                value :e.value,
                data :e.record.getData()
            };
            this.record = e.record;
            this.fireEvent('beforeedit', this,params);
        },this);
    },
    /**
     * 标识Grid被编辑之前触发
     * @private
     */
    onAfteredit : function(){
        this.component.on('edit',function(editing,e){
            var params = {
                name :e.column.dataIndex,
                rowIndex :e.rowIdx,
                colIndex :e.colIdx,
                value :e.value,
                originalValue :e.originalValue,
                data :e.record.getData()
            };
            this.record = e.record;
            this.fireEvent('afteredit', this,params);
        },this);
    },
    /**
     * 唯有在进入编辑状态的时候使用
     * @param fieldname
     * @param value
     */
    setEditRowValue : function(fieldname,value){
//        if(!record){
//           var records = this.component.getSelectionModel().getSelection();
//           if(records.length == 1){
//              var record = records[0];
//              this.record = record;
//           }else{
//              return;
//           }
//        }
        if(!this.record){return;}
        this.record.set(fieldname,value)
        this.record.save();
        this.record.commit();
        //this.component.store.fireEvent('update',this.component.store,this.record,Ext.data.Model.COMMIT);
    },
    /**
     * 唯有在进入编辑状态的时候使用
     * @param fieldname
     * @param value
     */
    getEditRowValue : function(fieldname){
        return this.record.get(fieldname);
        //this.component.store.fireEvent('update',this.component.store,this.record,Ext.data.Model.COMMIT);
    },
	/**
	 * 初始化组件配置参数数据
	 * @param {Object} config 组件配置参数对象
     * @private
	 */
	initData : function(config) {
		this.config = config;
		this.initLoader();
		this.initColumns();
        this.initStore();// 创建Store

	},
    /**
     * 初始化加载遮罩
     */
    initLoadMask : function(){
        this.loadmask = new Ext.LoadMask(this.component, {
            msg : "数据加载中,请稍等......"
        });
    },
	/**
	 * 初始化数据加载器loader
	 */
	initLoader : function(){
		this.key = this.config.serviceKey;//service对应的key值
		this.loader = this.config.proxy;//数据请求代理
	},
	/***************************************************************************
	 * 初始化组件
     * @private
	 */
	initComponent : function() {
		this.initGrid.apply(this,arguments);// 初始化grid
        this.initLoadMask();//初始化加载遮罩
	},
	/***************************************************************************
	 * 初始化Grid
	 * @private
	 */
	initGrid : function(config) {
		var me = this,
		    store = this.store,
		    columns = this.columnManager.columns,
            pbar,
		    data = this.config.columnData;
		var basic = {// 为其添加几个参数
			store : store,
			columns : columns,
			layout : 'fit',
            //disableSelection : config.disableSelect,//禁止选择
			//selModel : Ext.create('Ext.selection.CheckboxModel'),
			dockedItems: []
		};
        if(config.checked){
        	this.selModel = Ext.create('Ext.selection.CheckboxModel');
            NS.apply(basic,{selModel : this.selModel});
            basic.selModel.setSelectionMode(config.multiSelect === true? 'MULTI' : 'SINGLE');
        }
		if(config){
		   NS.apply(basic,config)
		}

		if(!this.config.hasOwnProperty('paging') || this.config.paging){//判断是否开启分页工具栏
            pbar = this.initPagingBar();//初始化分页栏
		    basic.dockedItems.push(pbar);
		}
		
		this.component = Ext.create('Ext.grid.Panel', basic);// 创建grid
	},
	/***************************************************************************
	 * 设置表头的样式  --此方法只能在grid组件初始化完成之后才能够被调用
	 * 
	 * @param {Number/String} columninfo 行信息
	 * @param {String} style 属性
	 * @param {String} value 值
	 */
	setHeadStyle : function(columninfo, style, value) {
		var grid = this.component, // 所有columns信息
		column = this.getExtColumn(columninfo);
		if (column) {
			var el = column.getEl();
			el.setStyle(style, value);
		}
	},
	/**
	 * 设置某单元格样式 --此方法只能在grid组件初始化完成之后才能够被调用
	 * 
	 * @param {HtmlElement/String/Number} rowinfo 行信息  建议用Number
	 * @param {Number/String}  columninfo 列信息 列名/列的属性名
	 * @param {String} style 样式
	 * @param {String} value 值
	 */
	setCellStyle : function(rowinfo, columninfo, style, value) {
		var grid = this.component, 
		    view = grid.getView(), 
		    row = view.getNode(rowinfo), // 获取当前行Html元素
		    column = this.getExtColumn(columninfo), // 列对象
		    cell;// 单元格元素
		cell = Ext.fly(row).down(column.getCellSelector());
		Ext.fly(cell).setStyle(style, value);
	},
	/***************************************************************************
	 * 设置某一列的样式信息--此方法只能在grid组件初始化完成之后才能够被调用
	 * 
	 * @param {Number/String}  columninfo 代表某一列的标识，属性名--name/列号--Number
	 * @param {String} style  样式
	 * @param {String} value  值
	 */
	setColumnStyle : function(columninfo, style, value) {
		var grid = this.component,
		    store = this.store, 
		    view = grid.getView(), 
		    count = store.getCount(), // 行数量
		    column = this.getExtColumn(columninfo), 
		    row, 
		    cell;
		for (var i = 0; i < count; i++) {
			row = view.getNode(i);// 获取当前行Html元素
			cell = Ext.fly(row).down(column.getCellSelector());//查找cell DOM节点
			Ext.fly(cell).setStyle(style, value);//设置cell节点样式
		}
	},
	/**
	 * 设置行样式   --此方法只能在grid组件初始化完成之后才能够被调用
	 * @param {String/Number}  rowinfo  建议用Number
	 * @param {String} style 样式属性
	 * @param {String} value 值
	 */
	setRowStyle : function(rowinfo, style, value) {
		var grid = this.component;
		var view = grid.getView();
		var row = view.getNode(rowinfo);// 获取当前行Html元素
		if (row) {
			Ext.fly(row).setStyle(style, value);
		}
	},
	/**
	 * 设置grid分页数据量
	 * 
	 * @param {Number}  number
	 */
	setPageRowCount : function(number) {
		var store = this.store;
		store.pageSize = number;
		store.load();
	},
//	/***
//	 * 设置Grid的数据过滤条件
//	 */
//	setFilter : function(){
//		var args = arguments;//参数数组
//	    var proxy = this.store.getProxy();
//	    if(args.length == 2){
//	       proxy.setExtraParam.apply(proxy,arguments);
//	    }else if(args.length == 1){
//	       for(var i in args[0]){
//	          proxy.setExtraParam(i,args[0][i]);
//	       }
//	    }
//	},
//	/***
//	 * 执行Grid的数据过滤
//	 */
//	runFilter : function(){
//	    this.getData(this.getParams());
//	},
	/***************************************************************************
	 * 根据传递的行号设定该列被选中,传递参数 为1个或者多个行号
	 * 
	 * @param {Number..|Object...} rowInfo....
	 */
	setSelectRows : function() {
		var grid = this.component;// 获取grid
		var selectModel = grid.getSelectionModel();// 获取选中对象
		NS.Array.each(arguments, function(obj) {
					selectModel.select(obj,true);
				});
	},
	/**
	 * 设置选中或者将选中的设置不选中的方法(扩展至setSelectRows)
	 * @param arg 参数类型为number或者array 是要选中或不选总的行数
	 * @param flag true表示选中指定参数的行，false则不选中指定参数的行
	 */
	setUnOrSelectRows:function(arg,flag){
		var grid = this.component;// 获取grid
		var selectModel = grid.getSelectionModel();// 获取选中对象
		if(NS.isNumber(arg)){//如果是数字
			selectModel.select(arg,flag);
		}else if(NS.isArray(arg)){//如果是数组
			NS.Array.each(arg, function(obj) {
				selectModel.select(obj,flag);
			});
		}
	},
	/***************************************************************************
	 * 获得当列所有选中列的数据
	 * 
	 * @return {Object[]}
	 */
	getSelectRows : function() {
		var array = [];
		var grid = this.component;// 获取grid
		var records = grid.getSelectionModel().getSelection();// 获取选中行数据
        NS.Array.each(records, function(record) {
					array.push(record.data);
				});
		return array;
	},
    /**
     * 获取当前选中列的行数
     * @return {Number}
     */
    getSelectCount : function(){
        var grid = this.component;// 获取grid
        var records = grid.getSelectionModel().getSelection();// 获取选中行数据
        return records.length;
    },
	/***************************************************************************
	 * 获取 指定的行，指定的列的单元格的数据
	 * 
	 * @param {Number} rowIndex 行号
	 * @param {Number/String} columninfo 列号或者列属性名
	 */
	getCellData : function(rowIndex, columninfo) {
		var grid = this.component, 
		    store = this.store,
		    record = store.getAt(rowIndex),
		    column = this.getExtColumn(columninfo);
		return record.get(column.dataIndex);
	},
	/***************************************************************************
	 * 设置某一个单元格的值{可能需要在设置值之前进行格式化}
	 * 
	 * @param {Number} rowIndex  行号/行信息
	 * @param {Number/String}  columninfo  列号或者列属性
	 * @param {String} value  需要设置的值
	 */
	setCellData : function(rowIndex, columninfo, value) {
		var grid = this.component, 
		    store = this.store,
		    record = store.getAt(rowIndex),
		    column = this.getExtColumn(columninfo);
		if(record)record.set(column.dataIndex,value);
	},
	/***************************************************************************
	 * 获取当前store 的数据总量
	 * @return {Number}
	 */
	getCount : function() {
		return this.store.getCount();
	},
	/***
	 * 获取所有修改，新增的数据的总数
	 * @param {String} type insert/delete/update
	 * @return {Number}
	 */
	getModifyCount : function(type){
	    return this.getModify(type).length;
	},
	/***************************************************************************
	 * 增加一列，譬如审核列 ---该方法必须在grid创建前被执行
	 * 
	 * @param {Number/String} columninfo 行号/列属性
	 * @param {NS.column.Column} column
	 */
	addColumn : function(columninfo,column) {
		var columns = this.component.columns;
//		this.;
	},
	/***
	 * 根据传递的行号设置其图片信息
	 * @param {Number/String} columninfo 列号或者列属性名
	 * @param {Number} rowIndex
	 * @param {String} pic
	 * @param {Function} callback
	 */
	setPictureByRow : function(columninfo,rowIndex,pic,callback){
	    var gird = this.component,
	        view = grid.getView(),
	        column = this.getExtColumn(columninfo),
	        row = view.getNode(rowIndex);// 获取当前行Html元素
	        
	    cell = Ext.fly(row).down(column.getCellSelector());
		Ext.fly(cell).setStyle('background-image', 'url('+pic+")");
		Ext.fly(cell).addListener('click',callback);
	},
	/***
	 *   通过传递的列属性或者列号信息获取Column对象
	 * @param {Number/String} columninfo 列索引，也可以是列的name
     * @return {Ext.grid.column.Column}
     * @private
     */
	getExtColumn : function(columninfo){
	    var columns = this.component.columns,cproxy, // 列对象数组
		column, // 列对象
		cell;// 单元格节点对象
		if (typeof columninfo == 'number') {
			column = columns[columninfo];
		} else if (typeof columninfo == 'string') {
			NS.Array.each(columns, function(c) {
						if (c.dataIndex == columninfo) {
							column = c;
							return false;
						}
					});
		}
        return column;
	},
    /***
     * @private   通过传递的列属性或者列号信息获取Column对象
     * @param {Number/String} columninfo 列索引，也可以是列的name
     * @return {NS.grid.Column}
     */
    getColumn : function(columninfo){
        var extcolumn = this.getExtColumn(columninfo),cproxy;
        return NS.util.ComponentInstance.getInstance(extcolumn);
    },
    /**
     * 获取一列的编辑器
     * @param {String/Number} columninfo 列索引，也可以是列的name
     * @return {NS.form.field.BaseField}
     */
    getColumnEditor : function(columninfo){
        var column = this.getColumn(columninfo);
        return column.getEditor();
    },
	/***************************************************************************
	 * 设置列的正则表达式校验
	 * 
	 * @param {String/Number} columninfo  列号或者列属性
	 * @param  regex  正则表达式
	 * @param {String} regexText  正则表达式校验信息
	 */
	setColumnValidate : function(columninfo, regex, regexText) {
		var column = this.getExtColumn(columninfo);
		var com = column.getEditor()||column.editor;
		com.regex = regex;
		com.regexText = regexText;
	},
	/***************************************************************************
	 * 设置Column的格式化字符串
	 * 
	 * @param {Number/String} columninfo 列属性/列号
	 * @param {String} format 格式化字符串
	 */
	setColumnFormat : function(columninfo, format) {
		var column = this.getExtColumn(columninfo);
		column.format = format;
	},
	/***************************************************************************
	 * 设置Column属性类型--String/Integer/Boolean/Collection
	 * 
	 * @param {Number/String} columninfo 列属性/列号
	 * @param {String} type   类型
	 */
	setColumnType : function(columninfo, type) {
		var column = this.getExtColumn(columninfo);
		column.type = type;
	},
	/***
	 * 根据列号，列组类型，列组件数据设置---列编辑组件
	 * @param {Number/String} columninfo  列属性/列号
	 * @param {String}  comType
	 * @param {Object} data
	 */
	setColumnData : function(columninfo,comType,data){
	    var column = this.getExtColumn(columninfo),
	        editor = column.getEditor();
	        if(editor == 'combobox'){
	           editor.getStore().loadData(data);
	        }else{
	           
	        }
	},
    setColumnHidden : function(columnInfo,isHidden){
        var column = this.getExtColumn(columnInfo);
        if(isHidden){
            column.hide();
        }else{
            column.show();
        }
    },
	/***
	 * 
	 */
	setColumnDataOnTree : function(columninfo,data){
	
	},
	/**
	 * 获取Column数据类型
	 * @param {Number/String}  columninfo 列属性/列号
	 * @return   {String}
	 */
	getExtColumnType : function(columninfo) {
		var column = this.getExtColumn(columninfo);
		return column.type;
	},
	/**
	 * 向列表中插入一行数据，默认插入第一行
	 * @param {Object} rowData 行数据---默认为｛key:value,...｝形式
	 */
	addRow : function(rowData,index,startEditColumnIndex) {
		var grid = this.component,
		    store = this.store, 
		    index = index||0,
		    model = store.model,
		    cellEditor = grid.plugins[0] || undefined,// 获取表编辑插件
		    record;
		if (rowData) {
			record = model.create(rowData);
		} else {
			record = model.create();
		}
		store.insert(0+index, record);
		if(cellEditor){
		   cellEditor.cancelEdit();// 取消编辑
		   cellEditor.startEdit(record, startEditColumnIndex||1);
		}
	},
	/**
	 * 想列表中插入一行或者多行数据，默认插入第一行以及往后
	 * 
	 * @param {Object[]} rowsData []  json数组
	 */
	addRows : function(rowsData) {
		var me = this;
        NS.Array.each(rowsData, function(rowData,index) {
						me.addRow(rowData,index);
					});
	},
	/***************************************************************************
	 * 向列表中指定位置--插入一行数据，
	 * 
	 * @param {Object} rowData
	 * @param {Number} rowIndex 行号
	 */
	insertRow : function(rowData, rowIndex,colIndex) {
		this.addRow(rowData,rowIndex,colIndex);
	},
	/***************************************************************************
	 * 想列表中的指定位置插入一行或者多行数据
	 * 
	 * @param {Object} rowsData []  多行数据集合
	 * @param {Number}  rowIndex  要插到的行号
	 */
	insertRows : function(rowsData, rowIndex,colIndex) {
		var me = this;
        NS.Array.each(rowsData, function(rowData,index) {
					 me.insertRow(rowData,rowIndex+index,colIndex);
				});
	},
	/***************************************************************************
	 * 删除给定行号的行数据
	 * 
	 * @param {Number} rowIndex
	 */
	deleteRow : function(rowIndex) {
        var record = this.store.getAt(rowIndex);
		this.store.remove(record);
	},
	/***************************************************************************
	 * 删除给定的多个行号的数据
     *   deleteRows(1,2,3,4);
	 * @param  {Number/...} rowindex... 多个行号参数
	 */
	deleteRows : function() {
		var me = this,
		    array = arguments;
        NS.Array.each(array, function(index) {
					me.deleteRow(index);
				});
	},
    /**
     * 移除所选中的列
     */
    removeSelectRows : function(){
        var grid = this.component,
            store = grid.getStore(),
            sm = grid.getSelectionModel();
        store.remove(sm.getSelection());
    },
	/***************************************************************************
	 * 清空store中的数据
	 */
	clear : function() {
		this.store.removeAll();
	},
	/***
	 * 根据提供的行号获取--行数据
	 * @param {Number} rowIndex
	 * @return {Object}
	 */
    getRow : function(rowIndex){
        var record = this.store.getAt(rowIndex);
        return record.getData();
    },
    /***
     * 根据提供的多个行号获取--一行或者多行数据
     * @param  {Number...} rowNumber...
     *
     *          var data = getRows(1,2,3,4);
     *
     * @return {Object[]}
     */
    getRows : function(){
        var me = this,
            array = [];
        NS.Array.each(arguments,function(index){
            array.push(me.getRow(index));
        });
        return array;
    },
    /**
     * 根据提供的域属性{Field}和值{Value}查询行对象
     */
    findRow : function(field,value){
    	var me = this,
    		store = this.component.getStore(),
    		matches=[];
    	store.each(function(item,index,length){
    		if(item.get(field) == value){
    			matches.push(item.getData());
    		}
    	});
    	return matches;
    },
    /**
     * 根据提供的域属性{Field}和值{Value}查询匹配到的所有行索引
     */
    findRowIndex : function(field,value){
    	var me = this,
		store = this.component.getStore(),
		matches=[];
		store.each(function(item,index,length){
			if(item.get(field) == value){
				matches.push(index);
			}
		});
		return matches;
    },
    /**
     * 通过id获取Row对象
     * @param id
     */
    getRowById : function(id){
    	var store = this.component.getStore();
    	store.each(function(item,index,length){
			if(item.get("id") == id){
				return item.getData();
			}
		});
    },
    /**
     * 通过id获取行号
     * @param id
     */
    getRowIndexById : function(id){
    	var store = this.component.getStore();
    	var number;
    	store.each(function(item,index,length){
			if(item.get("id") == id){
			   number = index;
			}
		});
    	return number;
    },
    /**
     * 获取第一个选中记录的索引
     */
    getFirstSelectIndex : function(){
        var records = this.component.getSelectionModel().getSelection();//获取选中的行的对象
        var collection = this.component.store.data;
        if(records.length>0){
            return collection.indexOf(records[0]);
        }else{
            return -1;
        }
    },
    /**
     *获取所有被选中的行的索引数组
     * @return {Array}
     */
    getAllSelectIndex : function(){
        var records = this.component.getSelectionModel().getSelection();//获取选中的行的对象
        var collection = this.component.store.data;
        var array = [];
        NS.Array.forEach(function(record){
            array.push(collection.indexOf(record));
        });
        return array;
    },
    /***
     * 获取store中的所有数据
     * @return {Object[]}
     */
    getAllRow  : function(){
        var array = [];
        NS.Array.each(this.component.store.data.items,function(record){
            array.push(record.data);
        });
       return array;
    },
    /***
     * 获取修改过的所有数据集
     * @param {String} type 变动数据类型 insert/update/delete
     * @return {Object[]}
     */
    getModify : function(type){
       var store = this.store,
           array = [];
           if(type == 'update'){
              records = store.getUpdatedRecords();
           }else if(type == 'insert'){
              records = store.getNewRecords(); 
           }else if(type == 'delete'){
              records = store.getRemovedRecords(); 
           }else{
           	  records = store.getModifiedRecords();
           }
        NS.Array.each(records,function(record){
           array.push(record.data);
       });
       return array;
    },
	/***************************************************************************
	 * 初始化Store
     * @private
	 */
	initStore : function() {
		var config = this.config.modelConfig,data;
        if(config){
           data = config.data;
        }else{
           data = this.config.data
        }
        this.queryParam = {};
        var basic = {
            fields : this.getFields(this.config),
            proxy : {
                type : 'memory'
            },
            pageSize : this.config.pageSize||25,
            autoLoad : false//是否自动加载数据
        };
//        if(data && data.data){
//            NS.apply(basic,{data:data.data,totalCount : data.count});
//        }else if(config && config.data){
//            NS.apply(basic,{data : config.data});
//        }
        
		this.store = Ext.create('Ext.data.Store', basic);
		if(data && data.data){
//            NS.apply(basic,{data : data.data||[],totalCount : data.count||0});
			this.loadData(data,1);
        }
	},
    /***
     * 获取域对象（用于store的fields）
     * @param {Object} config 配置对象
     * @return {Array}
     * @private
     */
    getFields : function(config){
        var fields = [],data = config.columnData||[],fieldsappend = config.fields||[],item;
        var getType = function(type){
            switch(type){
                case 'String' : return "string";
                case 'Long'   : return "int"
            }
        };
        for(var i= 0,len=data.length;i<len;i++){
            item = data[i];
            fields.push({name : item.name});
        }
        for(var i= 0,len = fieldsappend.length;i<len;i++){//将追加的域属性配置进模型中
            item = fieldsappend[i];
            if(NS.isString(item)){
                fields.push({name : item});
            }else if(NS.isObject(item)){
                fields.push(item);
            }
        }
        return fields;
    },
	/***************************************************************************
	 * 初始化Column
     * @private
	 */
	initColumns : function() {
          var  basic = {
		    data:this.config.columnData,
			columnConfig : this.config.columnConfig,
            lineNumber : this.config.lineNumber,
            defaultConfig : this.config.defaultColumnConfig//column默认配置
		};
		this.columnManager = new NS.grid.ColumnManager(basic);
	},
    /**
     * 获取用于{Ext.grid.Panel}显示的数据,并且刷新界面
     */
     loadData: function(json,page){
        var store = this.store;
        if(NS.isObject(json)){
            if(json.success == false){
                NS.error({
                    sourceClass : 'NS.grid.Grid',
                    sourceMethod : 'loadData',
                    msg : '加载数据异常，数据不是grid所需要的格式！'
                });
                store.loadRawData([]);
                return;
            }
            store.loadRawData(json.data);
//          store.loadData(json.data);
            store.totalCount = json.count;
        }else if(NS.isArray(json)){
            store.loadRawData(json);
//          store.loadData(json);
            store.totalCount = json.length;
        }
        store.currentPage  = page||1;
        this.fireEvent('load',json);
        if(this.pbar) this.pbar.onLoad();
    },
    /***
     * 讲数据加载器，注册进入Grid组件中
     * @private
     * @param key 在模型层配置的service的key值
     * @param component NS.mvc.Model
     */
    registerDataLoader : function(key,component){
        this.loader = component;
        this.key = key;
    },
    /**
     * 根据传递的请求参数，加载数据
     * @param {Object} params 请求参数
     */
    load : function(params){
        var baseParams = params||{};
        var returnParams = this.fireEvent('beforeload',this,baseParams);
        this.getData(returnParams||(baseParams||{}),1);
        if(this.selModel){this.selModel.setLocked(false);}
    },
    /**
     * 刷新当前结果集
     */
    refresh : function(){
        var page = this.store.currentPage;
        this.getData(this.queryParams,page);

    },
    /**
     * 获取用于grid显示的数据, 并加载显示
     * @private
     */
    getData : function(params,page){
        if(this.component.rendered){
            this.loadmask.show();
        }
        this.component.getSelectionModel().deselectAll(true);//全部反选
        var me = this,key = NS.clone(this.key),basic = {},pagesize = this.store.pageSize,endParams = {};
        this.queryParams = params;

        if(NS.isString(key)){
           endParams = params;
           basic = {key : key,params : endParams||{}}
        }else if(NS.isObject(key)){
           if(key.params){
               NS.apply(endParams,key.params);
               NS.apply(endParams,params||{});
           }else{
//               key.params = params ||{};
               endParams = params || {};
           }
           basic = {key : key.key,params : endParams};
        }
        if(!basic.params)basic.params = {};
        if(!basic.params.start){
            basic.params.start = 0*pagesize;
            basic.params.limit = 1*pagesize;
        }
        this.loader.callService(basic,function(json){
            this.loadData(json[basic['key']],page);
            this.loadmask.hide();
        },this);
    },
    /***
     * 初始化分页栏
     * @private
     */
    initPagingBar : function(){
        var me = this,ome = this;
        this.pbar = Ext.create('Ext.toolbar.Paging',{
            store : this.store,
            dock: 'bottom',
            displayInfo: true,
            moveFirst : function(){
                var page = 0,
                    baseParams = {
                    start : page*this.store.pageSize,
                    limit : this.store.pageSize
                    },
                    params = me.getParams();
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page+1);
            },
            movePrevious : function(){
                var page = this.store.currentPage- 2,
                    baseParams = {
                        start : page*this.store.pageSize,
                        limit : this.store.pageSize
                    },
                    params = me.getParams();
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page+1);
            },
            moveNext : function(){
                var page = this.store.currentPage,
                    baseParams = {
                        start : page*this.store.pageSize,
                        limit : this.store.pageSize
                    },
                    params = me.getParams();
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page+1);
            },
            moveLast : function(){
                var store = this.store,
                    totalCount =  store.getTotalCount(),
                    page = Math.ceil(totalCount / store.pageSize),
                    params = me.getParams();
                var baseParams = {
                    start : (page-1)*this.store.pageSize,
                    limit : this.store.pageSize
                };
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page);
            },
            doRefresh : function(){
                    var params = me.getParams(),
                        page = this.store.currentPage,
                    baseParams = {
                        start : (page-1)*this.store.pageSize,
                        limit : this.store.pageSize
                    };
                this.store.currentPage = page;
                NS.applyIf(baseParams,params);
                me.getData(baseParams,page);
            },
            // private
            onPagingKeyDown : function(field, e){
                var me = this,
                    k = e.getKey(),
                    pageData = me.getPageData(),
                    increment = e.shiftKey ? 10 : 1,
                    pageNum,
                    baseParams,
                    params;

                if (k == e.RETURN) {
                    e.stopEvent();
                    pageNum = me.readPageFromInput(pageData);
                    if (pageNum !== false) {
                        pageNum = Math.min(Math.max(1, pageNum), pageData.pageCount);
                        if(me.fireEvent('beforechange', me, pageNum) !== false){
                            //me.store.loadPage(pageNum);//更改ext默认行为
                                baseParams = {
                                    start : (pageNum-1)*this.store.pageSize,
                                    limit : this.store.pageSize
                                },
                                params = ome.getParams();
                                NS.applyIf(baseParams,params);
                                ome.getData(baseParams,pageNum);
                        }
                    }
                } else if (k == e.HOME || k == e.END) {
                    e.stopEvent();
                    pageNum = k == e.HOME ? 1 : pageData.pageCount;
                    field.setValue(pageNum);
                } else if (k == e.UP || k == e.PAGE_UP || k == e.DOWN || k == e.PAGE_DOWN) {
                    e.stopEvent();
                    pageNum = me.readPageFromInput(pageData);
                    if (pageNum) {
                        if (k == e.DOWN || k == e.PAGE_DOWN) {
                            increment *= -1;
                        }
                        pageNum += increment;
                        if (pageNum >= 1 && pageNum <= pageData.pageCount) {
                            field.setValue(pageNum);
                        }
                    }
                }
            }
        });
        return this.pbar;
    },
    /**
     * 获取当前的可变参数
     * @private
     * @return {Object} 当前store的查询参数
     */
    getParams : function(){
        return this.queryParams||{};
    },
    /**
     * 获取当前的查询参数
     */
    getQueryParams : function(){
        var key = this.key,params = this.getParams(),ret = {};
        if(NS.isObject(key)){
            NS.apply(ret,params,key.params||{});
        }else{
            NS.apply(ret,params);
        }
        return ret;
    },
    /**
     * 获取显示的column对象name集合 ,用于导出
     * @private
     */
    getShowColumnNames : function(){
        var columns = this.component.columns;
        var array = [];
        Ext.each(columns,function(g){
            if(g && g.dataIndex!="" && g.dataIndex != null && g.hidden == false)
                array.push(g);
        });
        return array;
    }
});
/**
 *@class NS.grid.SimpleGrid
 *@extends NS.grid.Grid
 */
NS.define('NS.grid.SimpleGrid',{
	extend : 'NS.grid.Grid',
    /***************************************************************************
     * 初始化Column
     * @private
     */
    initColumns : function() {
        NS.apply(this.config,{
            defaultColumnConfig : {
                style : {
                    color : 'white',
                    background : '#3598FE',
                    overCls : ''
                },
                onTitleMouseOver : NS.emptyFn
            }
        });
        this.callParent();
    },
    initComponent : function(){
        this.callParent(arguments);
        if(this.component.headerCt.style){
            NS.apply(this.component.headerCt.style,{background:'#3598FE'});
        }else{
            this.component.headerCt.style = {background:'#3598FE'};
        }
        this.onRowclick();
    },
    /***
     * 初始化分页栏
     * @private
     */
    initPagingBar : function(){
        var me = this,ome = this;
        this.pbar = Ext.create('Ext.toolbar.NumberPaging',{
            store : this.store,
            dock: 'bottom',
            displayInfo: true
        });
        this.pbar.on('linkclick',function(index){
            var page = index,
                baseParams = {
                    start : (page-1)*this.store.pageSize,
                    limit : this.store.pageSize
                },
                params = me.getParams();
            NS.applyIf(baseParams,params);
            var returnParams = me.fireEvent('beforeload',me,baseParams);
            me.getData(returnParams||(baseParams||{}),page);
        });
        return this.pbar;
    },
    /**
     * 当行被双击的时候触发
     * @private
     */
    onRowclick : function(){
        this.component.on('itemclick',function(view, record,item,rowindex,e){
            //scrollTo(e.getX(), e.getY());
            NS.Event.setEventObj(e);
            this.fireEvent('rowclick', this,record.getData(),item,rowindex,NS.Event);
        },this);
    }
});/***
 * @class NS.grid.ColumnManager
 * @extends NS.Base
 * 负责管理NS.grid.Grid 中Column的容器管理类
 */
NS.define('NS.grid.ColumnManager', {
    constructor: function (config) {
        this.config = config;
        this.initColumns();
    },
    data : [],
    /***
     * 初始化列集合
     * @private
     */
    initColumns: function () {
        var data = this.config.data;//组件配置数据
        this.generateColumns(data||[]);//生成column组件
        this.dealWithColumnConfig();//处理列配置数据
        //this.dealForceFit();//进行自适应处理
        //this.makeAssociate();//将组件进行级联设置
    },
    /**
     * 进行自适应处理
     */
    dealForceFit : function(){
        var len = this.columns.length;
        if(len>0){
            delete this.columns[len-1].width;
            this.columns[len-1].flex = 1;
            this.columns[len-1].align = "left";
        }
    },
    /***
     * 处理列配置数据
     * @private
     */
    dealWithColumnConfig: function () {
        var config = this.config.columnConfig;//列配置数据
        if (!this.columnset) {
            this.columnset = {};
            this.columns = [];
        }
        if (NS.isArray(config)) {
            for (var i = 0, len = config.length; i < len; i++) {
                var c = config[i];
                if (c && c.name &&  !this.columnset[c.name]) {
                    var column = this.generateColumn1(c);
                    this.registerColumn(column, c.name, c.index);
                }
            }
        }

    },
    /**
     * 进行组件之间的级联配置
     * @private
     */
    makeAssociate: function () {
        var columns = this.columns;
        var map = new Object();
        Ext.each(columns, function (column) {// 将编码实体相同的column放置到一起
            var bmst = column.codeEntityName;
            if (bmst == "tree" && map[bmst]
                && bmst) {
                map[bmst].push(column);
            } else if (!map[bmst] && bmst) {
                map[bmst] = [];
                map[bmst].push(column);
            }
        });
        for (var i in map) {
            if (i && map[i] instanceof Array) {
                map[i].sort(function (a, b) {
                    if (a.cc > b.cc) {
                        return 1;
                    }
                    if (a.cc < b.cc) {
                        return -1;
                    }
                    return 0;
                });
            }
        }
        for (var i in map) {
            if (i && map[i] instanceof Array) {
                this.associate(map[i]);
            }
        }
    },
    /***************************************************************************
     * 对组件进行关联
     *
     * @param {Array} array
     */
    associate: function (array) {
        var me = this;
        for (var i = 0; i < array.length; i++) {
            if (i == array.length - 1) {
                return;
            }
            var column1 = array[i];
            var column2 = array[i + 1];
            var editor1 = column1.editor;
            var editor2 = column2.editor;
            if (editor1 == null) {// 如果editor为空，说明当前可编辑grid未开启
                return;
            }
            var filterData;
            var pid;
            editor1.addListener('select', function (combox, records) {
                pid = records[0].get('id');
                filterData = me.filterAndSetComboBoxData(pid, column2);
                editor2.getStore().loadData(filterData);
                editor2.setValue();
                var grid = column1.up('gridpanel');
                var e1 = null;
                grid.on('beforeedit', function (editorp, e) {
                    if (editor2 == e.column.getEditor()) {
                        if (editor1.getValue() == ""
                            || editor1.getValue() == null) {
                            Ext.Msg.alert('提示', "请先选择｛" + column1.text + "}信息");
                            editorp.cancelEdit();
                            editorp.startEdit(e.record, column1);
                        } else {
                            var nowid = e.record.get(column2.dataIndex);
                            var store = editor2.getStore();
                            var record = store.getById(nowid);
                            if (nowid != "") {// 如果值存在
                                if (!record) {// 如果查找不到record
                                    if (me.getParentId(nowid, column2) == e.record
                                        .get(column1.dataIndex)) {// 如果当前组件Id的父Id和组件1的id相同
                                        editor2.getStore().loadData(me
                                            .getStoreDataById(nowid,
                                                column2));
                                        editor2.setValue(nowid);
                                    }
                                }
                            } else {
                                if (e.record.get(column1.dataIndex) != "")
                                    editor2
                                        .getStore()
                                        .loadData(me
                                            .filterAndSetComboBoxData(
                                                e.record.get(column1.dataIndex),
                                                column2));
                            }
                        }
                    }
                });
            });

            editor2.addListener('focus', function (combox) {

            });
        }
    },
    /**
     * 通过子节点Id查询父节点Id
     *
     * @param {String/Number}  childId
     * @param {Ext.grid.column.Column}  column
     * @return {String/Number}
     */
    getParentId: function (childId, column) {
        var data = column.data;
        var parentId;
        for (var i = 0; i < data.length; i++) {
            if (data[i].id == childId) {
                parentId = data[i].pid;
                break;
            }
        }
        return parentId;
    },
    /**
     * 获得该节点的父节点id 对应的数据
     *
     * @param {String/Number} childId
     * @param {Ext.grid.column.Column} column
     * @return {Array}
     */
    getStoreDataById: function (childId, column) {
        var data = column.data;
        var array = [];
        var parentId = this.getParentId(childId, column);
        for (var i = 0; i < data.length; i++) {
            if (data[i].pid == parentId) {
                array.push(data[i]);
            }
        }
        return array;
    },
    /**
     * 过滤并设置级联ComboBox数据
     * @param {String/Number} parentId
     * @param {Ext.grid.column.Column} column
     */
    filterAndSetComboBoxData: function (parentId, column) {
        var store = (column.editor || column.getEditor()).getStore();
        var data = column.data;// 数据
        var array = [];
        for (var i = 0; i < data.length; i++) {
            if (data[i].pid == parentId) {
                array.push(data[i]);
            }
        }
        store.loadData(array);
        return array;
    },
    /***
     * 生成列集合
     * @param obj
     */
    generateColumns: function (dataset) {
        if (this.config.lineNumber) {//如果行号属性为true，则显示行号
            this.registerColumn(this.generateColumn1({xtype: 'rownumberer',text : ""}));
        }
        for (var i = 0, len = dataset.length; i < len; i++) {
//            if(dataset.length == i+1){
//               dataset[i].flex = 1;
//            }
            var column = this.generateColumn(dataset[i]);

            this.registerColumn(column);
        }
    },
    /**
     * 将column注册进ColumnManager中
     * @param column
     * @param name
     */
    registerColumn: function (column, name, index) {
        if (!this.columnset) {
            this.columnset = {};
            this.columns = [];
        }
        if (column) {
            if (name) {
                this.columnset[name] = column;
            } else {
                this.columnset[column.dataIndex] = column;
            }
            if (index) {
                this.columns = NS.Array.insert(this.columns, column.index, column);
            } else {
                this.columns.push(column);
            }
        }
    },
    /**
     * 生成Column对象
     * @param config
     * @return {Ext.column.Column}
     * @private
     */
    generateColumn1: function (config) {
        var basic = {
            width: config.width || 70,//默认长度为70
            dataIndex: config.name,//列的索引
            sortable: false,// 不允许排序
            draggable: false//不允许拖动
        };
        if(config.flex == 1)delete basic.width;
        if(config.editor){
           if(NS.isNSComponent(config.editor)){
               config.editor = config.editor.getLibComponent();
           }
        }
        if(config.renderer){//处理renderer函数的过程
            config.renderer = this.processRenderer(config.renderer);
        }
        NS.apply(basic, config, this.config.defaultConfig);
        switch (config.xtype) {
            case "buttoncolumn":
                return Ext.create('Ext.grid.column.ButtonColumn', basic);
            case "linkcolumn" :
                return Ext.create("Ext.grid.column.LinkColumn", basic);
            case "rownumberer" :
                var rowConfig = {
                    text: ''
                };
                NS.apply(rowConfig, this.config.defaultConfig);
                return Ext.create('Ext.grid.RowNumberer', rowConfig);
            case "progresscolumn" :
                return Ext.create("Ext.grid.column.ProgressColumn",config);
            default :
                return Ext.create('Ext.grid.column.Column', basic);
        }
    },
    /***
     * 生成列对象
     * @param {Object} data
     * @return {Ext.grid.column.Column}
     */
    generateColumn: function (data) {
        if (!data.isColumnCreate)
            return null;
        var columnConfig = this.getConfigColumn(data.name);//获取从grid中传递的column的配置数据
        var field = NS.util.FieldCreator.createField(data,columnConfig.editorConfig||{});//创建列对应的组件
        this.registerField(data.name, field);//将组件注册到columnManager中
        var config = {
            hidden: !(data.isColumnShow),//是否显示
            codeEntityName: data.codeEntityName,// 编码实体
            cc: data.cc,// 编码层次
            width: data.columnWidth || 70,//默认长度为70
            sortable: false,// 不允许排序
            draggable: false,//不允许拖动
            header: data.nameCh,
            dataIndex: data.name,
            align : 'center',
            //align: data.dataType == "Long" ? "left" : "right",//数据显示位置
            renderer: this.getRender(data),//获取列的绑定函数
            editor: field//创建可编辑组件
        };
        NS.apply(config, columnConfig, this.config.defaultConfig);
        if(config.flex == 1)delete config.width;
        if(config.editor){
            this.registerField(data.name, config.editor);//将组件注册到columnManager中
        }
        if(columnConfig.renderer){//处理renderer函数的过程
            config.renderer = this.processRenderer(columnConfig.renderer);
        }
        if(columnConfig.columnStyle){
            config.renderer = this.processRendererStyle(config);//处理ColumnConfig新增的样式
        }
        if(data.editable === false || config.editable === false){
            delete config.editor;//如果组件不可编辑，设置不可编辑属性
        }
        switch (config.xtype) {
            case "buttoncolumn":
                return Ext.create('Ext.grid.column.ButtonColumn', config);
            case "linkcolumn" :
                return Ext.create("Ext.grid.column.LinkColumn", config);
            case "progresscolumn" :
                return Ext.create("Ext.grid.column.ProgressColumn", config);
            default :
                return Ext.create('Ext.grid.column.Column', config);
        }
    },
    /**
     * 处理renderer函数
     * @param renderder
     */
    processRenderer : function(renderer){
        return function(value,metaData,record,rowIndex,colIndex){
            return renderer.call(this.NSContainer,value,record.data,rowIndex,colIndex);
        }
    },
    /**
     * 处理添加的renderer样式
     * @param columnConfig
     * @return {Function}
     */
    processRendererStyle : function(columnConfig){
    	var me = this,style = columnConfig.columnStyle,styleString = me.generateStyleString(style),renderer = columnConfig.renderer;
	    return function(){
	       return "<div style='width:100%;height:100%;"+styleString+"'>"+renderer.apply(this,arguments)+"</div>";
	    }
    },
    /**
     * 生成样式字符串
     * @param {Object} obj 样式对象
     * @return {string}
     */
    generateStyleString : function(obj){
        var style = "";
        for(var i in obj){
            style += i + ":" + obj[i]+";";
        }
        return style;
    },
    /***
     * 通过name来查询配置列参数
     * @param {String} name 配置数据中name属性为传入的值的对象
     */
    getConfigColumn: function (name) {
        var array = this.config.columnConfig || [], item,result = {};
        for (var i = 0, len = array.length; i < len; i++) {
            item = array[i];
            if (item.name == name) {
                result = item;
                break;
            }
        }
        if(result.editor){
           if(NS.isNSComponent(result.editor)){
              result.editor = result.editor.getLibComponent();
           }
        }
        return result;
    },
    /**
     * 获取column列绑定函数
     * @param columnData
     * @return {Function} 返回的需要render的函数
     */
    getRender: function (columnData) {
        var me = this, renderer;//column列附加的函数
        switch (columnData.xtype) {
            case 'combobox' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    var dataIndex = this.columns[colIndex].dataIndex;
                    return me.getComboxDisplayValue(value, record, dataIndex);// 获取显示值
                };
                break;
            case 'treecombobox' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    var dataIndex = this.columns[colIndex].dataIndex;
                    return me.getTreeComDisplayValue(value, record, dataIndex);// 获取显示值
                };
                break;
            case 'tree' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    var dataIndex = this.columns[colIndex].dataIndex;
                    return me.getComboxDisplayValue(value, record, dataIndex);// 获取显示值
                };
                break;
            case 'checkbox' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    if (value == "true" || value == "1") {
                    	//record.set(this.columns[colIndex].dataIndex, "1");
                        return "是";
                    } else {
                    	//record.set(this.columns[colIndex].dataIndex, "0");
                        return "否";
                    }
                };
                break;
            case 'datefield' :
                renderer = function (value, metadata, record, rowIndex, colIndex) {
                    var v = value;
                    if (value instanceof Date) {
                        v = Ext.util.Format.date(value, 'Y-m-d');
                        record.set(this.columns[colIndex].dataIndex, v);
                    }
                    return v;
                };
                break;
            default : 
             	renderer = function(v){
             		return v;
             	}
//            case 'textfield' :
//                var expression = columnData.drillExp;//是否下钻属性
//                if (expression) {
//                    (function (expression) {
//                        var array = columnData.drillExp.toString().split('-');
//                        if (array.length == 3 && array[0] == 1) {
//                            renderer = function (value, metadata, record, rowIndex, colIndex) {
//                                var id = record.get(array[2]);
//                                if (array[1] == 'student') {
//                                    return "<a class= 'gobal_grid_link' href=\"javascript:XsxxWindow.showExportWindow(\'"
//                                        + id
//                                        + "\')\">" + value + "</a>";
//                                } else if (array[1] == "teacher") {
//                                    return "<a class= 'gobal_grid_link' href=\"javascript:JsxxWindow.showExportWindow(\'"
//                                        + id
//                                        + "\')\">" + value + "</a>";
//                                } else {
//                                    return value;
//                                }
//                            };
//                        }
//                    })(expression);
//                }
                break;
        }
        return renderer;
    },
    /***
     * @private
     * 通过name获取Field组件
     * @param {String} 获取name属性为传入的值的可编辑组件
     */
    getField: function (name) {
        return this.fieldset[name];
    },
    /***
     * 将生成的field注册进入ColumnManager的管理中
     * @private
     * @param {String}name  名称
     * @param {Ext.form.field.Base}field 组件
     */
    registerField: function (name, field) {
        if (!this.fieldset) {
            this.fieldset = {};
            this.fieldlist = [];
        }
        this.fieldset[name] = field;
        this.fieldlist.push(field);
    },
    /**
     * 此方法为combobox专用，通过后台传递的id的值，获取其对应名称，并且用来显示
     *
     * @param {String/Number} value
     * @param {Object} record
     * @param {String/Number} colIndex
     * @return {String/Number}
     */
    getComboxDisplayValue: function (value, record, dataIndex) {
        if (value != "" || value != null) {
//            var field = this.getField(dataIndex);
//            if(field.multiSelect === true){
//                field.setValue(value.split(','));
//            }else{
//                field.setValue(value);
//            }
//            var rawvalue = field.getRawValue();
//            if(rawvalue != 0 ||  rawvalue != false){
//               return rawvalue;
//            }else{
//               return value;
//            }
            var com = this.getField(dataIndex);
            var displayField = com.displayField;
            var valueField = com.valueField;
            var store = com.getStore();
            var crecord = store.findRecord(valueField, new RegExp("^"+value+"$"));
            if (crecord != null) {
                var displayValue =  crecord.get(com.displayField);//获取显示值
                var valuefield = crecord.get(valueField);//获取实际值
                record.data[dataIndex] = valuefield;//设置实际值
                return displayValue;//设置显示值
            } else {
                return value;
            }
        } else {
            return value;
        }
    },
    /***
     * 获取用于显示的树的节点的数据
     * @param {String/Number} value
     * @param {Object} record
     * @param {String/Number} dataIndex
     */
    getTreeComDisplayValue: function (value, record, dataIndex) {
        var me = this;
        if (value != "" || value != null) {
            var com = me.getField(dataIndex);
            var store = com.picker.getStore();
            var crecord = store.getNodeById(value);
            if (crecord != null) {
                var displayValue = crecord.get('text');
                return displayValue;
            } else {
                return value;
            }
        } else {
            return value;
        }
    }
});/**
 * @class NS.grid.Column
 * @extends NS.Component
 *     目前支持的类型为buttoncolumn,linkcolumn,progresscolumn
 *
 * 定义grid的column列的抽象类
 */
NS.define('NS.grid.Column',{
    extend : 'NS.Component',
    /**
     * @cfg  {NS.form.field}editor 可编辑表格组件
     */
    /**
     * @cfg  {Function} renderer  渲染函数，其返回值决定列的最终渲染
     */
    /**
     * @cfg  {Function} name  列名，其返回值决定列的最终渲染
     */
    /**
     * @cfg  {Function} text  列文本内容，显示grid列头上显示的内容
     */
    /**
     * @cfg  {Boolean} flex  自伸展
     */
    /**
     * 初始化组件
     * @private
     */
   initComponent : function(config){
       this.component = Ext.create('Ext.grid.column.Column',config||{});
   },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('text','name','renderer','xtype','locked','flex');
    },
    /**
     * 初始化事件
     * @private
     */
    initEvents : function(){
        this.callParent();
        this.addEvents(
            /**
             * 当列上的链接被点击的时候触发该事件
             * @event linkclick
             * @param {String} linktext 链接的文本内容
             * @param {Number} rowIndex 行索引
             * @param {Number} columnIndex 列索引
             * @param {Object} rowData 行数据
             */
            'linkclick',
            /**
             * 当列上的按钮被点击的时候触发该事件
             * @event buttonclick
             * @param {String} buttontext 按钮的文本内容
             * @param {Number} rowIndex  行索引
             * @param {Number} columnIndex 列索引
             * @param {Object} rowData 行数据
             */
            'buttonclick');
    },
    onLinkclick : function(){
        this.component.on('linkclick',function(linktext,rowIndex,columnIndex,rowData,cell){
            this.fireEvent('linkclick',linktext,rowIndex,columnIndex,rowData,cell);
        },this)
    },
    onButtonclick : function(){
        this.component.on('buttonclick',function(buttontext,rowIndex,columnIndex,rowData,name,cell){
            this.fireEvent('buttonclick',buttontext,rowIndex,columnIndex,rowData,name,cell);
        },this)
    },
    /**
     * 获取Column对象的编辑组件
     * @param {NS.form.field.BaseField}
     */
    getEditor : function(){
        var editor = this.component.getEditor()||this.editor;
        return NS.util.ComponentInstance.getInstance(editor);
    },
    /**
     * 设置列的文本信息
     * @param {String} text 列的显示文本信息
     */
    setText : function(text){
        this.component.setText(text);
    },
    /**
     * 设置当前列的可编辑组件
     * @param {NS.form.field.BaseField} field
     * @private (something wrong)
     */
    setEditor : function(field){
       this.component.setEditor(field.getLibComponent());
    },
    /**
     * 获取列属性
     */
    getDataIndex : function(){
        return this.component.dataIndex;
    }
});/***
 * @class Ext.grid.column.ButtonColumn
 * @extends Ext.grid.column.Column
 *    针对ext列的扩展类
 *    var column = new Ext.grid.column.ButtonColumn({
                        header: 'Phone',
                        dataIndex: 'phone',
                        width:200,
                        buttons : [
                        {
                            buttonText : '第一个按钮',
                            style : {
                                color : 'red',
                                font : '18px'
                            }
                        },
                        {
                            buttonText : '第二个按钮'
                        }
                    ]}
 *    });
 *    当然你也可以在column中添加render方法，方法最后返回html字符串，但是字符串必须包含您所需要的button标签，
 *    这样，你可以监听该组件的{@link Ext.grid.column.ButtonColumn#buttonclick}buttonclick事件，事件会返回一些属性供您使用。
 */
Ext.define('Ext.grid.column.ButtonColumn',{
    extend : "Ext.grid.column.Column",
    alias: ['widget.buttoncolumn'],
    /**
     * 创建buttoncolumn对象
     * @param {Object} config
     */
    constructor : function(){
        var me = this;
        this.callParent(arguments);
        /***
         * @event buttonclick
         *         当点击此列中任意一个按钮的时候触发该事件
         * @param {String} buttonValue 按钮的名称
         * @param {Number} recordIndex 行索引
         * @param {Number} cellIndex 列索引
         * @param {Object} data 行数据
          */
        this.addEvents('buttonclick');
        if(!this.renderer){
            this.renderer = function(){
                var basicHL = '<input type="button" value={0} style="{1}" name="{2}">';
                var items = me.buttons;
                var html = "";
                var format = Ext.String.format;
                for(var i=0;i<items.length;i++){
                    var item = items[i];
                    var style = me.generateStyleString(item.style||{});
                    html += format(basicHL,item.buttonText,style,item.name);
                }
                return html;
            };
        }
    },
    /**
     * 生成样式字符串
     * @param {Object} obj 样式对象
     * @return {string}
     */
    generateStyleString : function(obj){
        var style = "";
        for(var i in obj){
            style += i + ":" + obj[i]+";";
        }
        return style;
    },
    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * Also fires any configured click handlers. By default, cancels the mousedown event to prevent selection.
     * Returns the event handler's status to allow canceling of GridView's bubbling process.
     */
    processEvent : function(type, view, cell, recordIndex, cellIndex, e, record, row){
        var me = this,
            item, fn;
        var dataIndex = view.getGridColumns()[cellIndex].dataIndex;//当前点击列的属性
        if(dataIndex == this.dataIndex){
            if(type == "click"){
                var target = e.getTarget();
                if(cell.contains(target) && target.nodeName == "INPUT" && target.type == "button"){
//                            this.onClick.call(this.scope||this,item.buttonText,recordIndex,cellIndex);
                	this.fireEvent('buttonclick',target.value,recordIndex,cellIndex,record.data,target.name||'',cell);
                }else if (type == 'mousedown' && item.stopSelection !== false) {
                    return false;
                }
            }
        }
        return me.callParent(arguments);
    }
});/***
 * @class Ext.grid.column.LinkColumn
 * @extends Ext.grid.column.Column
 *    针对ext列的扩展类
 *    var column = new Ext.grid.column.LinkColumn({
                        header: 'Phone',
                        dataIndex: 'phone',
                        width:200,
                        links : [
                        {
                            linkText : '第一个链接',
                            style : {
                                color : 'red',
                                font : '18px'
                            }
                        },
                        {
                            linkText : '第二个链接'
                        }
                    ]}
 *    });
 *    当然你也可以在column中添加render方法，方法最后返回html字符串，但是字符串必须包含您所需要的button标签，
 *    这样，你可以监听该组件的{@link Ext.grid.column.LinkColumn#linkclick}linkclick事件，事件会返回一些属性供您使用。
 */
Ext.define('Ext.grid.column.LinkColumn',{
    extend : "Ext.grid.column.Column",
    alias: ['widget.linkcolumn'],
    /**
     * 配置的固定列的链接
     *      配置项参照元素参照
     *              var item = [{
                             linkText : '第一个链接',
                             style : {
                                 color : 'red',
                                 font : '18px'
                             }
                         }];
     */
    link : [],
    /**
     * 创建linkcolumn对象
     * @param {Object} config
     */
    constructor : function(){
        var me = this;
        this.callParent(arguments);
        /***
         * @event linkclick
         *     当点击此列中任意一个链接的时候触发该事件
         * @param {String} linkValue 链接的名称
         * @param {Number} recordIndex 行索引
         * @param {Number} cellIndex 列索引
         * @param {Object} data 行数据
         * @param {HTMLElement} element 链接对象
         */
        this.addEvents('linkclick');
        if(!this.renderer){
            this.renderer = function(){
                var basicHL = '<a href="javascript:void(0);" style="{0}">{1}</a>';
                var items = me.links;
                var html = "";
                var format = Ext.String.format;
                for(var i=0;i<items.length;i++){
                    var item = items[i];
                    var style = me.generateStyleString(item.style||{});
                    html += format(basicHL,style,item.linkText)+"&nbsp;";
                }
                return html;
            };
        }
    },
    /**
     * 生成样式字符串
     * @param {Object} obj 样式对象
     * @return {string}
     */
    generateStyleString : function(obj){
        var style = "";
        for(var i in obj){
           style += i + ":" + obj[i]+";";
        }
        return style;
    },
    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * Also fires any configured click handlers. By default, cancels the mousedown event to prevent selection.
     * Returns the event handler's status to allow canceling of GridView's bubbling process.
     */
    processEvent : function(type, view, cell, recordIndex, cellIndex, e, record, row){
        var me = this,
            item, fn;
        var dataIndex = view.getGridColumns()[cellIndex].dataIndex;//当前点击列的属性
        if(dataIndex == this.dataIndex){
            if(type == "click"){
                var target = e.getTarget();
                if(cell.contains(target) && target.nodeName == "A"){
                    e.stopEvent();
                    this.fireEvent('linkclick',this.deleteHtmlTag(target.innerHTML),recordIndex,cellIndex,record.data,target,1);
                }else if (type == 'mousedown' && item.stopSelection !== false) {
                    return false;
                }
            }
        }
        return me.callParent(arguments);
    },
    /***
     * 移除html字符串
     * @private
     */
    deleteHtmlTag : function(str){
        return str.replace(/<\/?.+?>/g, "");// 去掉所有的html标记
    }
});/***
 * @class Ext.grid.column.ProgressColumn
 * @extends Ext.grid.column.Column
 *    针对ext列的扩展类
 *    var column = new Ext.grid.column.ProgressColumn({
                        header: 'Phone',
                        dataIndex: 'phone',
                        width:200,
                        color:'red'
                      });
 *    当然你也可以在column中添加render方法，方法最后返回html字符串，但是字符串必须包含您所需要的button标签，
 *    这样，你可以监听该组件的{@link Ext.grid.column.LinkColumn#linkclick}linkclick事件，事件会返回一些属性供您使用。
 */
Ext.define('Ext.grid.column.ProgressColumn',{
    extend : "Ext.grid.column.Column",
    alias: ['widget.progresscolumn'],
    /**
     * 创建progresscolumn对象
     * @param {Object} config
     */
    constructor : function(){
        var me = this;
        this.callParent(arguments);
        this.renderer = function(value){
            var basicHL = '<div style="height:16px;border:1px {0} solid;">' +
                              '<div style="background-color:{1};height:14px;width:{2}%;text-align:center;">{3}%</div>' +
                          '</div>';
            var items = me.buttons;
            var html = "";
            var format = Ext.String.format;
            html += format(basicHL,me.color,me.color,value,value);
            return html;
        };
    },
    /**
     * @cfg {String} 进度条的颜色
     */
    color : 'blue'
});/**
 * 自定义grid分页栏
 */
Ext.define('Ext.toolbar.NumberPaging', {
    extend: 'Ext.toolbar.Toolbar',
    tpl1 : new Ext.XTemplate([
        '<span style="margin-left:12px;display: inline-block;float: left">',
         '<tpl for="list">',
            '<tpl if="link == true"><a href="javascript:void(0);" style="font-size: 12;color: blue;margin-left: 2px;" index="{index}">{mc}</a></tpl>',
            '<tpl if="link === false"><a index="{index}" style="font-size: 16;font-weight:bolder;margin-left: 2px;">{mc}</a></tpl>',
         '</tpl>' +
            '</span>',
        '<span style="margin-right:14px;display:inline-block;float:right;color: blue;">共<span style="color: #000000;"> {count} </span>条记录，当前显示第 {start} 到 {end} 条记录</span>'
    ]),
    layout : 'fit',
    height : 20,
    initComponent : function(){
        this.addEvents('linkclick');
        var component = this.content = new Ext.Component({
            tpl : this.tpl1
        });
        component.on({
            click : {
                element : 'el',scope : this,
                fn : function(event,he){
                    if(he.nodeName == "A"){
                        var e = Ext.fly(he);
                        var index = e.getAttribute('index');
                        this.onLinkclick(parseInt(index));
                        this.fireEvent('linkclick',this.index);
                    }
                }
            }
        });
        this.callParent();
    },
    onRender: function () {
        var me = this;
        this.add(this.content);
        this.callParent(arguments);
        this.onLinkclick(1);
    },
    onLinkclick : function(index){
        this.index = parseInt(index);
        this.content.update(this.createLinkData(this.index));
    },
    /**
     * @cfg {Number} 最大链接数 1,2,3,4,5,6,7,8,9,10
     */
    maxLink: 7,
    onLoad : function(){
        this.content.update(this.createLinkData(this.store.currentPage));
    },
    createLinkData : function(index){
        var store = this.store, count = parseInt(store.totalCount)||0,maxLink = this.maxLink,
            pageSize = store.pageSize, currentPage = store.currentPage,
            maxPage = Math.ceil(count / pageSize), i = index, iteLength,array = [],allData = {};
        if (maxPage > maxLink) {
            iteLength = maxLink;
        } else {
            iteLength = maxPage;
        }
        iteLength  += index;
        if(index>7){
            iteLength = iteLength-3;
            i = i-3;
        }
        if(iteLength>maxPage){
            iteLength = maxPage+1;
            if(iteLength>maxLink){
                i = iteLength-maxLink;
            }else{
                i = 1;
            }
        }
        array.push({mc : '首页',index : 1,link : true});//将第一页的链接放入首页
        if(index > 1){
            array.push({mc : '上一页',index : index-1,link : true});//将上一页的链接放入
        }
        for (i; i < iteLength;i++) {
            if( i == index){
                array.push({mc : i,index : i,link : false});
            }else{
                array.push({mc : i,index : i,link : true});
            }
        }
        if(index<maxPage){
            array.push({mc : '下一页',index : index+1,link : true});//将下一页的链接放入
        }
        array.push({mc : '末页',index : maxPage,link : true});//将最后一页的链接放入

        allData.list = array;
        allData.maxPage = maxPage;
        allData.count = count;
        var start = pageSize*(index-1)+1;
        var end = pageSize*index;
        allData.end = end<count?end:count;
        allData.start = start<allData.end?start:allData.end;

        return allData;
    }
});/***
 * @class NS.grid.plugin.CellEditor
 * @extends NS.Plugin
 *    {NS.grid.Grid}的插件
 * @author wangyongtai
 *
 *      var grid = NS.component.grid.Grid({
 *          plugins : [new NS.grid.plugin.CellEditor()]
 *      });
 */
NS.define('NS.grid.plugin.CellEditor',{
	/**
	 * 构造函数
	 * @param config 配置参数
	 */
	constructor : function(config){
		this.component = Ext.create('Ext.grid.plugin.CellEditing',config||{clicksToEdit: 2});
	},
	/***
	 * 获取底层类库组件
	 * @return
	 */
	getLibComponent : function(){
		return this.component;
	}
});/**
 * @class NS.grid.plugin.HeaderQuery
 * @extends NS.Plugin
 *    {NS.grid.Grid}的插件
 * @author wangyongtai
 *      var grid = NS.grid.Grid({
 *          plugins : [new NS.grid.plugin.HeaderQuery()]
 *      });
 */
NS.define('NS.grid.plugin.HeaderQuery',{
    extend : 'NS.Plugin',
    /**
     * 构造函数
     * @constructor
     * @param config 组件配置对象
     */
	constructor : function(config){
		this.addEvents('headerclick','query');//添加表头点击事件
	},
	/***
	 * 插件初始化函数
	 * @param grid
	 */
	init : function(grid){
		this.registerGrid(grid);
		this.addHeaderClick();
        this.addListener('headerclick',this.onHeaderClick,this);
        this.addListener('query',this.onQuery,this);
	},

	/***
	 * 将grid进行注册
	 * @param grid 
	 */
	registerGrid : function(grid){
		this.grid = grid;
	},
	/***
	 * 清空查询组件状态
	 */
	clear : function(){
		this.bbar.remove(this.panel);
		//this.bbar.insert(12, { xtype: 'tbfill' });
		delete this.params;//删除查询参数
        delete this.panel;
		delete this.showInfos;//删除显示条件
        this.grid.load({});
	},
	/***
	 * 获取查询组件参数(用于和grid进行交互)
	 * @return {Object}
	 */
	getParams : function(){
		var item = this.columnData,// 创建表头所需数据
			component = this.component,// 创建的组件
			params = this.params = this.params||{},// 查询参数
			xtype = item.xtype,
			name = item.name;
		if (xtype == 'textfield'||xtype == 'textarea') {
			name = item.name + ".like";
			var value = component.text.getValue();
			params[name] = value;
		} else if(xtype=='numberfield'){
			var value = component.text.getValue();
			params[name] = value;
		}else if (xtype == "combobox"
				|| xtype == "tree"
				|| xtype == "checkbox"
				|| xtype == 'treecombobox') {
			var value = component.combox.getValue();
			params[name] = value;
		} else if (xtype == 'datefield') {
			var name1 = name + ".date1";
			var name2 = name + ".date2";
			var value1 = component.date1.getRawValue();
			var value2 = component.date2.getRawValue();
			params[name1] = value1;
			params[name2] = value2;
		}else if(xtype == 'timefield'||xtype == "yearmonth"){
			var value = component.combox.getRawValue();
			params[name] = value;
		}else{
			var value = component.combox.getValue();
			params[name] = value;
		}
		return params;
	},
	/**
	 * 为添加表头点击事件
	 */
	addHeaderClick : function(){
		var me = this,
		    grid = this.grid.getLibComponent(),
			dataSet = this.grid.config.columnData;
        if(grid.headerCt){
            grid.headerCt.on('headerclick', function(ct, column, event, t,
                                                     eOpts) {
                Ext.each(dataSet, function(item) {
                    if (item.name == column.dataIndex
                        && item.isQuery == 1) {
                        me.fireEvent('headerclick',column,item,event);
                    }
                });
            });
        }else if(grid.lockedGrid.headerCt && grid.normalGrid.headerCt ){
            grid.lockedGrid.headerCt.on('headerclick', function(ct, column, event, t,
                                                     eOpts) {
                Ext.each(dataSet, function(item) {
                    if (item.name == column.dataIndex
                        && item.isQuery == 1) {
                        me.fireEvent('headerclick',column,item,event);
                    }
                });
            });
            grid.normalGrid.headerCt.on('headerclick', function(ct, column, event, t,
                                                                eOpts) {
                Ext.each(dataSet, function(item) {
                    if (item.name == column.dataIndex
                        && item.isQuery == 1) {
                        me.fireEvent('headerclick',column,item,event);
                    }
                });
            });
        }

	},
	/***
	 * 当表头被点击的时候
	 * @param itemData 该列的表头对应的数据
	 */
	onHeaderClick : function(column,itemData,event){
		this.columnData = itemData;
		this.createHeaderWindow.apply(this,arguments);
        if(this.columnData.xtype!='treecombobox'){
        	this.doListeners();//执行监听
        }else{
//        	this.component.combox.on({
//        		
//        	});
        }
	},
    /**
     * 执行特殊的条件监听，用以检测是否让表头查询组件消失
     */
    doListeners : function() {
        var win = this.window;
        var winEl = win.getEl();// 获取window所在的元素
        var body = Ext.getBody();
        var bdom = body.dom;
        var close = false;
//        if(this.columnData.xtype=='treecombobox'){
//        	winEl = this.component.combox.getEl();//如果是下拉树,其作用域范围扩大
//        }
        body.on('click', function(event) {// 针对body的监听事件
            var parent = event.getTarget();
            if (event.within(winEl)) {
                return;
            }else if(!event.within(winEl)) {
                while (parent != bdom) {
                    if (parent !== null
                        && (parent.id.match("datepicker") != null)) {
                        return;
                    } else {
                        parent = (parent != null
                            ? parent.parentNode
                            : bdom);
                    }
                }
                if (close) {
                    win.close();
                } else {
                    close = true;
                }
            }
        });
    },
	/***
	 * 当query事件被触发后
	 */
	onQuery : function(){
		var params = this.getParams();
		var grid = this.grid;
		//对null值的判断
        grid.load(params);
//		if(grid.query == this){
//			grid.load(params);
//		}else{
//            if(grid.query) grid.query.clear();
//            //grid.load(params);
//            grid.query = this;
//		}
        this.window.close();
        delete this.window;
        this.setPagingBar();
	},
	/**
	 * 设置分页栏上的显示情况
	 */
	setPagingBar : function(){
		this.setShowMessage();//设置pagingbar上显示的信息
        this.insertPanelIntoBottom();//将显示的字符串 插入进分页面栏中
	},
	/**
	 * 将显示的查询条件的面板插入PagingBar中
	 */
	insertPanelIntoBottom : function(){
		var me = this;
        var barsize = this.barsize = this.getSizeForBarPanel();
		var panel = this.panel = this.panel||Ext.create('Ext.panel.Panel', {
					height : barsize.height - 5,
					frame : true,
					border : false,
					baseCls : '{solid #B5B8C8;}',
					margin : 0,
					html : ''
				});// 获取数据显示面板
        if(!this.bbar.items.contains(panel)){
            this.bbar.insert(11, panel);
        }
		var id = Ext.id();
		var query = this.getQueryString() + "&nbsp;&nbsp;<span id='" + id + "'></span>";
		panel.body.update(query);
		var button = Ext.create('Ext.button.Button', {
			iconCls : 'page-delete',
			handler : function() {
				me.clear();
			},
			renderTo : id
		});
		
	},
    /**
     * 获取refresh button的位置以及pagingbar的高度
     */
    getSizeForBarPanel : function() {
        var me = this;
        var bbar = this.bbar = me.grid.getLibComponent().getDockedComponent(1);// 底部bbar
        var el = bbar.getEl();
        var height = el.getHeight();
        var button = bbar.getComponent('refresh');
        var elr = button.getEl();// 刷新按钮的el
        var displayItem = bbar.child('#displayItem');
        var eld = displayItem.getEl();// 显示信息的el
        var params = {
            x : elr.getX() + elr.getWidth(),// 信息的初始化x位置
            y : el.getY(),// 信息窗体的初始化y位置
            width : eld.getX() - elr.getX() - elr.getWidth() - 40,// 最大宽度
            height : height
        };
        return params;
    },
	/**
	 * 获取显示的查询字符串
	 */
	getQueryString : function(){
		var basequery = "";
		var params = this.showInfos;//显示的信息
		for (var i = 0; i < params.length; i++) {
			if (i == params.length - 1) {
				basequery += "\"" + params[i].name + "\"" + " 等于 " + "\""
						+ params[i].value + "\"";
			} else {
				basequery += "\""
						+ params[i].name
						+ "\""
						+ " 等于 "
						+ "\""
						+ params[i].value
						+ "\"";
						
			}
			if (this.getStrLength(this.delHtmlTag(basequery)) >= this.barsize.width-40
				) {
                basequery += "&nbsp;&nbsp;<span style='color:red;'>并且......</span>&nbsp;&nbsp;";
				break;
			}
		}
		return basequery;
	},
    /***
     * 删除字符串中的HTML标签
     *
     * @param {String} str 待删除字符串
     * @return {String}
     */
    delHtmlTag : function(str) {
        return str.replace(/<\/?.+?>/g, "");// 去掉所有的html标记
    },
    /**
     * 获取字符串的像素
     *
     * @param {String} str
     * @return {Number}
     */
    getStrLength : function(str) {
        var len = 0;
        for (var i = 0;; i++) {
            if (!str.charCodeAt(i))
                break;
            if (str.charCodeAt(i) > 255)
                len += 2*6;
            else
                len += 1*6;
        }
        return len;
    },
	/***
	 * 当鼠标悬停查询条件面板一秒后
	 */
	onPanelHover : function(){
		var el = this.panel.getEl();// 获得Window的Element元素
		var timeflag = true;
		el.on('mouseover', function(event, html, obj) {// 增加监听事件
					timeflag = true;
					Ext.defer(function() {
								if (timeflag && !PopWindow.popDiv)
									me.showPop(event);// 显示弹泡信息
							}, 1000);
				});
		el.on('mouseout', function(event, html, obj) {// 增加监听事件
					timeflag = false;
				});
	},
	showPop : function(event){
		var query = this.getQueryString();
		if (this.popDiv&&this.popDiv.parentNode == document.body) {
			document.body.removeChild(this.popDiv);
		}
		var config = {};
		if(Ext.isIE8){
			   config.x = obj.getX();
			   config.y = obj.getY();
		}else{
			   config.x = event.getX();
			   config.y = event.getY();
		}
		var me = this;
		//var client = this.getBodySize();// 获取body的宽度和高度
		var div = document.createElement('div');
		this.popDiv = div; 
		var style = div.style;
		style.webkitBorderRadius = 8;
		style.mozBorderRadius = 8;
		style.borderRadius = 8;
		style.boxShadow = "#666 0px 0px 6px";
		style.width = config.width;
//		style.left = config.x + 10;
//		style.top = config.y - 70;
		style.padding = "16 10 16 16";
		style.position = 'absolute';// 绝对位置
		style.zIndex = 1000000;// 设置层的级别
		div.innerHTML = "<div style=\'float:left;width:32;height:100%;\'><img src=\'images/020.png\' style = \'width:32;height:32;\'/></div>" +
							"<div style='margin-left:16;float:right;line-height:2;height:100%;padding-top:0;width:260px;'>"+config.message+"</div>";// div的内容
		div.className = "x-window-body x-window-body-default x-closable x-window-body-closable x-window-body-default-closable x-window-body-default x-window-body-default-closable x-box-layout-ct";
		Ext.getBody().appendChild(div);
		Ext.fly(div).setXY([config.x + 10,config.y - 70]);
		var size = this.getSize(div);//获取div的高度和宽度
		Ext.fly(div).setY(config.y- Ext.fly(div).getWidth().height);
		Ext.defer(function() {
					if (div.parentNode == document.body)
						Ext.fly(div).remove();
						delete me.popDiv;
				}, 3000);
	},
	/**
	 * 获取refresh button的位置以及pagingbar的高度
	 */
	getBarSize : function() {
		var me = this;
		var bbar = this.bbar = this.bbar||this.grid.getLibComponent().getDockedComponent('bottom');// 底部bbar
		var el = bbar.getEl();
		var height = el.getHeight();
		var button = bbar.getComponent('refresh');
		var elr = button.getEl();// 刷新按钮的el
		var displayItem = bbar.child('#displayItem');
		var eld = displayItem.getEl();// 显示信息的el
		var params = {
			x : elr.getX() + elr.getWidth(),// 信息的初始化x位置
			y : el.getY(),// 信息窗体的初始化y位置
			width : eld.getX() - elr.getX() - elr.getWidth() - 40,// 最大宽度
			height : height
		};
		return params;
	},
	/**
	 * 将查询的条件信息加入到查询信息数组中
	 * 
	 * @private
	 * @param config
	 *            配置参数对象
	 * @return
	 */
	setShowMessage : function() {
		var item = this.columnData,
			xtype = item.xtype,
			name = item.nameCh,
			component = this.component;
		var infos = this.showInfos = this.showInfos||[];
		
		if (xtype == "textfield"||xtype == "textarea"||xtype=='numberfield') {// 组件类型为text时
			for (var i = 0; i < infos.length; i++) {
				if (infos[i].name == name) {
					infos[i].value = component.text.getValue();
					return;
				}
			}
			infos.push({
						name : name,
						value : component.text.getValue()
					});
		} else if (xtype == "combobox"
				|| xtype == "tree"
				|| xtype == "timefield"
				|| xtype == "yearmonth"
				|| xtype == 'checkbox'
				|| xtype == 'treecombobox') {// 组件类型为combox时
			for (var i = 0; i < infos.length; i++) {
				if (infos[i].name == name) {
					infos[i].value = component.combox.getRawValue();
					return;
				}
			}
			infos.push({
						name : name,
						value : component.combox.getRawValue()
					});
		} else if (xtype == "datefield") {// 组件类型为datefield时
			for (var i = 0; i < infos.length; i++) {
				if (infos[i].name == name) {
					infos[i].value = component.date1.getRawValue() + " 到 "
							+ component.date2.getRawValue();
					return;
				}
			}
			infos.push({
						name : name,
						value : component.date1.getRawValue() + " 到 "
								+ component.date2.getRawValue()
					});
		}
	},
	/**
	 * 创建表头窗体
	 * @param itemData 该列的表头对应的数据
	 */
	createHeaderWindow : function(column,itemData,event){
		var defaultConfig  = {
		    		closable : false,
		    		draggable : false,
		    		header : false,
		    		height : 100,
		    		border : false,
		    		plain : true
		    	};
        var component = this.createComponent(itemData);
		if (itemData.xtype == "textfield"
			||itemData.xtype == "textarea"
				||itemData.xtype == "numberfield") {
			NS.apply(defaultConfig,{
						width : 178,
						height : 63,
						minWidth : 162,
						minHeight : 63,
						buttonAlign : 'center',
						layout : {
							type : 'vbox',
							align : 'center'
						},
						items : [component.text],
						buttons : [component.button1, component.button2]
					});
		} else if (itemData.xtype == "combobox"
				|| itemData.xtype == "tree"
				|| itemData.xtype == "timefield"
				|| itemData.xtype == "checkbox"
				|| itemData.xtype == "treecombobox") {
			NS.apply(defaultConfig,{
						width : 160,
						height : 30,
						items : component.combox
					});
		} else if (itemData.xtype == 'datefield') {
			NS.apply(defaultConfig,{
						width : 220,
						height : 100,
						buttonAlign : 'center',
						layout : {
							//type : 'vbox',
							align : 'center'
						},
						items : [component.date1, component.date2],
						buttons : [component.button1, component.button2]
					});
		}
		if(this.window){
			this.window.close();
		}
		this.window = Ext.create('Ext.window.Window',defaultConfig);
		if(component.text){
		   component.text.focus(true, true);// 获得焦点
		}
		//设置显示窗体的位置以及大小
		this.setWindowPositionAndSize(this.window,column);
	},
	/**
	 * 设定window的位置
	 */
	setWindowPositionAndSize : function(window,column){
		var el = column.getEl();
		var pagey = el.getY() + el.getHeight();// y轴位置
		var pagex = el.getX();//x轴位置
		var body = Ext.getBody();//获取body的Ext.Element对象
		var x = body.getWidth();
		if (pagex + window.width > x) {
			pagex = x - window.width-20;
		}
		window.setPosition(pagex, pagey, false);
		window.setSize({
			width : window.width,
			height : window.height
		});
		window.show();// 显示window窗体
	},
	createWindowContainer : function(itemData){
		
	},
	/**
	 * 创建conditionWindow所需要的组件
	 * 
	 * @param config
	 *            配置参数对象
	 */
	createComponent : function(itemData) {
		var me = this;
        var codeData = itemData.codeData.data,//编码数据
            name = itemData.name,//组件name
            xtype = itemData.xtype;//组件类型
        var date1_date2 = true;//针对日期查询
		var component = this.component = {};// 声明组件
		if (xtype == 'textfield'||xtype == 'textarea') {
			component.text = Ext.create('Ext.form.field.Text', {
						hiddenName : name,
						border : false,
						width : 150,
						listeners : {
							'specialkey' : function(textfield, e) {
								if (e.getKey() == e.ENTER) {
									component.button1.fireEvent('click');
								}
							}
						}
					});
		}else if(xtype=='numberfield'){
			component.text = Ext.create('Ext.form.field.Number', {
				hiddenName : name,
				border : false,
				width : 150,
				listeners : {
					'specialkey' : function(textfield, e) {
						if (e.getKey() == e.ENTER) {
							component.button1.fireEvent('click');
						}
					}
				}
			});
		}else if (xtype == 'timefield') {
			component.combox = Ext.create('Ext.form.field.Time', {
				hiddenName : name,
				border : false,
				format :"G时i分",
	            submitFormat:"G时i分",
				width : 150,
				listeners : {
					'select' : function() {
						me.fireEvent('query');
					}
				}
			});
		} else if (xtype == "combobox") {
			component.combox = Ext.create('Ext.form.field.ComboBox', {
						width : 150,
						valueField : 'id',
						displayField : 'mc',
						hiddenName : name,
                        lastQuery : '',
                        queryMode : 'local',
						store : {data : codeData,fields : ['id','mc']},
						allowBlank : false,
						editabled : false,
						listeners : {
							'select' : function() {
								me.fireEvent('query');

							}
						}
					});
		} else if (xtype == 'treecombobox') {
			var config = {
						//emptyText : '选择叶子节点...',
						expanded : true,
						editable : true,
						isLeafSelect:true,
						//isParentSelect:false,
						rootVisible:false,
					    width : 150,
					    treeData: NS.clone(codeData.children[0]),
						listeners : {
							'treeselect' : function(self,records) {
								me.fireEvent('query');
							}
						}
					};
			component.combox = Ext.create('Ext.ux.comboBoxTree', config);
		} else if (xtype == "tree") {
			component.combox = Ext.create('Ext.form.field.ComboBox', {
						width : 150,
						valueField : 'id',
						store : {data : codeData,fields : ['id','text','pid']},
						displayField : 'text',
						queryMode : 'local',
						editable : false,
						allowBlank : false,
						editabled : false,
						listeners : {
							'select' : function() {
								me.fireEvent('query');
							}
						}
					});
		} else if (xtype == "checkbox") {
			component.combox = Ext.create('Ext.form.field.ComboBox', {
				width : 150,
				editable : false,
				store : {
				     fields : ['id','mc'],
				     data : [
				        {'id':'1','mc' : '是'},
				        {'id':'0','mc' : '否'}
				     ]
				   },
				valueField : 'id',// 实际值
				displayField : 'mc',// 显示值
				allowBlank : false,
				editabled : false,
				listeners : {
					'select' : function() {
						me.fireEvent('query');
					}
				}
			});
		} else if (xtype == 'datefield') {
			var dId1 = Ext.id();
			var dId2 = Ext.id();
			component.date1 = Ext.create('Ext.form.field.Date', {
						id : dId1,
						fieldLabel : '起始',
						emptyText:'应早于结束时间...',
						format : 'Y-m-d',
						labelWidth : 40,
						editable : false,
						name : 'startValue',
						//vtype : 'daterange',// daterange类型为上代码定义的类型
						endDateField : dId2,// 必须跟endDate的id名相同
						listeners : {
//							'specialkey' : function(date1, e) {
//								if (e.getKey() == e.ENTER) {
//									component.button1.fireEvent('click');
//								}
//							},
							change:function(text,nV,oV){
								if(component.date2.getValue()!=null&&(text.getValue()>component.date2.getValue())){
									date1_date2 = false;
									text.setValue(null);
								}else{
									date1_date2 = true;
								}
							}
						}
					});
			component.date2 = Ext.create('Ext.form.field.Date', {
						id : dId2,
						fieldLabel : '结束',
						emptyText:'应晚于起始时间...',
						format : 'Y-m-d',
						labelWidth : 40,
						name : 'endValue',
						editable : false,
						//vtype : 'daterange',// daterange类型为上代码定义的类型
						startDateField : dId1,// 必须跟startDate的id名相同
						listeners : {
//							'specialkey' : function(date2, e) {
//								if (e.getKey() == e.ENTER) {
//									component.button1.fireEvent('click');
//								}
//							},
							change:function(text,nV,oV){
								if(component.date2.getValue()!=null&&(text.getValue()<component.date1.getValue())){
									date1_date2 = false;
									text.setValue(null);
								}else{
									date1_date2 = true;
								}
							}
						}
					});
		}
		component.button1 = Ext.create("Ext.button.Button", {
					text : "确认",
					textAlign : "center",
					//tooltip:'起始时间需晚于结束时间,且均不可为空时方可执行.',
					listeners : {
                        click : {
                            fn : function() {
                                if(xtype == 'datefield'){
                                	if(!(component.date1.getValue()==component.date2.getValue()&&component.date1.getValue()==null)){
                                    	if(date1_date2 ==true){
                                    		this.fireEvent('query');	
                                    	}
                                    }
                                }else{
                                	this.fireEvent('query');
                                }
                            	
                            },
                            scope : this
                        }
					}
				});
		component.button2 = Ext.create("Ext.button.Button", {
					text : "取消",
					textAlign : "center",
					listeners : {
                           click : {
                               fn : function() {
                                    this.window.close();
                               },
                               scope : this
                           }
					}
				});

		return component;
	}
});/**
 * @class NS.grid.query.SingleFieldQuery
 * @extends NS.Component
 *  单字段查询组件,包含查询字段下拉表和字段值两部分。 字段值组件可以根据查询字段下拉表中的字段类型显示不同的组件。
 *              目前字段值组件只有3种：输入框、下拉框、日期组件。
 *             var single = new NS.grid.query.SingleFieldQuery(config);
 */
NS.define('NS.grid.query.SingleFieldQuery', {
    extend: 'NS.Component',
    /**
     *  初始化构造函数
     */
    constructor: function (config) {
        this.initData.apply(this, arguments);// 初始化数据
        this.initComponent();//初始化组件
    },
    /***
     * 注册下将要关联的grid
     * @param grid
     */
    registerGrid: function (grid) {
        this.grid = grid;
    },
    /**
     * 清空查询组件在grid的参数
     * @private
     */
    clear: function () {
        this.field.setValue();
    },
    /**
     *  配置组件数据源
     * @param config
     *            需要配置的数据源
     */
    initData: function (config) {
        var tranData = [], i = 0, item, data = config.data,
            obj = {// 配置参数
                compWidth: 300,
                firstColumnWidth: .4,
                secondColumnWidth: .6
            };
        this.fieldsConfig = config.fieldsConfig;
        this.config = config;

        NS.apply(config, obj);// 合并参数对象

        for (i, len = data.length; i < len; i++) {
            item = data[i];
            if (item.isQuery) {
                tranData.push(item);
            }
        }
        this.tranData = tranData;
        this.registerGrid(config.grid);
    },
    /**
     *  初始化组件
     * @param config 配置参数
     */
    initComponent: function () {

        var config = this.config;
        this.component = this.createFieldSet(config);// 创建fieldset
        var fieldCombox = this.createFieldComboBox();// 创建属性列表combox
        this.component.add(fieldCombox);// 将属性列表combox添加进fieldset中
        this.setDefaultComponent();
        this.setQueryButton();
    },
    setQueryButton : function(){
        var me = this;
        var button = new Ext.button.Button({
            text : '查询',
            iconCls : 'ns-btn-search',//背景图片
            margin : '1 0 0 4',
            frame : true,
            handler :function(){
                me.grid.load(me.getParams());
            }
        });
        button.ui = button.ui+'-toolbar';//针对toolbar的特殊情况修正
        this.component.add(button);
    },
    /**
     *  获取查询参数
     * @return Object 查询参数
     */
    getParams: function () {
        var field = this.fieldCombox.getValue();// 属性名
        var type = this.field.xtype;// 组件类型
        var com = this.field;
        var obj = {};
        switch (type) {
            case 'textfield' :
                obj[field + ".like"] = com.getValue();
                break;
            case 'combobox' :
                if(com.getValue()==com.getRawValue()){//不做处理
                }else{
                	obj[field] = com.getValue();
                }
                break;
            case 'datefield' :
                obj[field] = com.getRawValue();
                break;
            case 'timefield' :
                obj[field] = com.getRawValue();
                break;
            case 'monthfield' :
                obj[field] = com.getRawValue();
                break;
            case 'numberfield' :
            	obj[field] = com.getRawValue();
            	break;
            case 'textarea' :
            	obj[field+'.like'] = com.getValue();
            	break;
            case 'checkbox' ://对checkbox做特殊处理
            	obj[field] = com.getValue();//单字段中已更改,不需做特殊处理==true?'1':'0';
            	break;
            case 'treecombobox' :
            	obj[field] = com.getValue();break;
            default :
                obj[field] = com.getValue();
                break;
        }
        if(obj[field] == null){
            delete obj[field];
        }
        return obj;
    },
    /**
     *  获取默认的组件组件，默认为第一个下拉属性对应的组件
     */
    setDefaultComponent: function () {
        //如果不配置查询数据的话，该处会报错
        var me = this,
            fieldStore = this.fieldStore,
            item = this.tranData[0];
        this.fieldCombox.setValue(item.name);
        this.addComponentForFieldSet(item);
    },
    /**
     *  获得fieldset
     * @return Ext.form.FieldSet
     */
    getFieldSet: function () {
        return this.fieldSet;
    },
    /**
     *  创建fieldset
     * @param config
     *            配置参数
     */
    createFieldSet: function (config) {
        var fieldSet = Ext.create('Ext.container.Container', {
            layout: 'column',
            padding: 0,
            frame : true,
//            margin: '0 0 0 0',
            width: config.compWidth,
            bubbleEvents: ['add'],//移除默认冒泡事件remove
            height: 22,
            border: 0
        });
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)) {
            Ext.apply(fieldSet, {
                baseCls: '{border:0px solid #B5B8C8;}'
            });
        }
        return fieldSet;
    },
    /**
     *  创建属性列表combox
     *  @return Ext.form.field.ComboBox
     */
    createFieldComboBox: function () {
        var me = this;
        var config = this.config;// 配置参数
        var store = this.createFieldStore();// 创建store
        var combox = this.fieldCombox = Ext.create('Ext.form.field.ComboBox', {// 创建combobox
            store: store,
            valueField: 'value',// 实际值
            displayField: 'display',// 显示值
            editable: false,//不可编辑
            columnWidth: config.firstColumnWidth,
            border: 0,
            listeners: {
                'select': {
                    scope: this,
                    fn: function (combo, records) {
                        var name = records[0].data.value;
                        var componentData;
                        Ext.each(this.tranData, function (component) {
                            if (component.name == name) {
                                componentData = component;// 数据相同
                            }
                        });
                        this.componentData = componentData;// 组件数据
                        this.addComponentForFieldSet(componentData);// 为fieldset添加组件
                    }
                }
            }
        });
        return combox;
    },
    /**
     *  创建属性列表store
     * @return Ext.data.Store
     */
    createFieldStore: function () {
        var tranData = this.tranData,// 转换后的数据
            fieldsData = this.fieldsData = [],
            item;
        for (var i = 0, len = tranData.length; i < len; i++) {
            item = tranData[i];
            if (item.isQuery) {
                fieldsData.push({value: item.name, display: item.nameCh});
            }
        }
        var store = this.fieldStore = Ext.create('Ext.data.Store', {
            fields: [ 'value', 'display' ],
            data: fieldsData
        });
        return store;
    },
    /**
     *  为fieldset添加组件
     * @param componentData
     *            创建组件的数据
     */
    addComponentForFieldSet: function (componentData) {
        var me = this;
        var field = me.createComponent(componentData);
        var fieldSet = this.component;
        if (fieldSet.items.length == 3) {
            fieldSet.remove(me.field);
            fieldSet.insert(1,field);
        } else {
        	var xtype = componentData.xtype; 
            if (xtype == 'checkbox') {
                fieldSet.add(field);
            }else{
            	fieldSet.add(field);
            }//..有待陆续维护
        }
    },
    /**
     *  根据属性列表的选择创建对应的组件
     * @param componentData
     *            组件数据
     * @return Array 组件数组
     */
    createComponent: function (item) {
        //如果是下拉或者时间选择之类的，我考虑选择时触发查询事件。交互起来会更好点。
        var me = this, C = item;
        //var config = this.config;// 配置参数
        var component, basic = {
            name: 'fieldValue',
            itemId: 'remove',
            editable : true,
            columnWidth: me.config.secondColumnWidth,
            listeners: {
                specialkey: {
                    scope: this,
                    fn: function (textField, e) {
                        if (e.getKey() == e.ENTER) {
                            //this.fireEvent('doQuery');//直接调用这个事件也可以,目前因这个事件有问题，暂代替
                        	var params = this.getParams();
                        	if(NS.Object.getSize(params)==0){return;}
                        	this.grid.load(params);
                        }
                    }
                }
            }
        };
        if(C.xtype=='textarea'){
        	C = NS.clone(C);//克隆备份
        	C.xtype = 'textfield';
        }
        if(C.xtype=='checkbox'){
        	C = NS.clone(C);
        	C.xtype = 'combobox';
        	C.codeData.data=[{id:'1',mc:'是',dm:'1'},{id:'0',mc:'否',dm:'0'}];
        }
        var fieldConfig = this.getComponentConfig(item);
        NS.apply(basic,fieldConfig);
        this.field = component = NS.util.FieldCreator.createField(C,basic);
        
        if(C.xtype=="treecombobox"){
			component.on('treeselect',function(){
				this.grid.load(this.getParams());
			},this);
		}else{
			//这个仅仅针对下拉起作用//下拉之类的应有change或者select事件
		   component.on('select',function(){
	             this.grid.load(this.getParams());
           },this);
		}
        delete component.maxLength;//删除最大长度提示信息,因为查询不需要做严格校验
        delete component.regex;//删除正则表达式校验,因为查询不需要做严格校验
        delete component.regexText;//删除正则表达式校验提示信息,因为查询不需要做严格校验
        return component || {};
    },
    getComponentConfig: function(cdata){
        var sx = cdata.name, i =0,item,
            fieldsConfig = this.fieldsConfig ||{},
            len = fieldsConfig.length;
        for(;i<len;i++){
            item = fieldsConfig[i];
            if(item.name == sx){
               return item;
            }
        }
    }
});/**
 * @class NS.grid.query.SeniorQuery
 * @extend NS.Component
 * 高级查询
 */
NS.define('NS.grid.query.SeniorQuery', {
    extend: 'NS.Component',
    /**
     *  初始化构造函数
     *
     */
    constructor: function () {
        this.initData.apply(this, arguments);
        this.initComponent();
    },
    /***
     * 注册下将要关联的grid
     * @param grid
     */
    registerGrid: function (grid) {
        this.grid = grid;
    },
    /**
     * 清空查询组件在grid的参数
     * @private
     */
    clear: function () {

    },
    /**
     *
     *  配置组件数据源
     * @param config 要配置的数据
     * @private
     */
    initData: function (config) {
        this.config = config;
        this.data = config.data;
        this.initFieldsStoreData(config.data);
        this.registerGrid(config.grid);
    },
    /***
     * 初始化字段数据
     * @private
     */
    initFieldsStoreData: function (data) {
        var fieldsData = [],item;
        for (var i = 0, len = data.length; i < len; i++) {
            item = data[i];
            if (item.isQuery) {
                fieldsData.push({value: item.name, display: item.nameCh});
            }
        }
        this.fieldsData = fieldsData;
    },
    /**
     * @private
     */
    initComponent: function () {
        this.component = Ext.create('Ext.button.Button', {
            text: '高级查询',
            iconCls : 'ns-btn-advanced-search',
            listeners: {
                click: {
                    scope: this,
                    fn: function () {
                        this.initWindow();
                    }
                }
            }
        });
    },
    /**
     * 初始化组件
     * @param {Object} config 配置参数
     * @private
     */
    initWindow: function () {
        this.condition = new NS.grid.query.Condition({// 创建条件面板生成器
            SeniorQuery: this,
            fieldsStoreData: this.fieldsData,// 默认属性字段store数据
            componentsData: this.data
            // 默认组件数据
        });
        this.condition.initComponent();//初始化组件
        this.createWindow({
            width: 800,
            height: 400,
            autoScroll: true,
            autoShow: true,
            title: '高级查询',
            modal: true,
            resizable: false,
            items: this.condition.getPanel(),
            buttons: this.createButtons(this.config)
        });
    },
    /**
     * 创建窗体
     * @private
     */
    createWindow: function (config) {
        this.window = Ext.create('Ext.window.Window', config);
    },
    /**
     * 创建组件
     * @private
     */
    createButtons: function (config) {
        var me = this;
        var button1 = Ext.create('Ext.button.Button', {
            text: '提交查询',
            handler: function () {
                var params = me.getParams();
                if (params.error == "yes") {
                    return;
                }
                delete params.error;
                me.window.close();
                me.grid.load(params);
            }
        });
        var button2 = Ext.create('Ext.button.Button', {
            text: '取消',
            handler: function () {
                me.window.close();
            }
        });
        return [button1, button2];
    },
    /**
     * 向后台提交数据
     */
    getParams: function (config) {
        var me = this;
        var error = "no";
        var params = [];// 查询参数
        var ids = this.condition.ids;
        for (var i = 0; i < ids.length; i++) {
            var field = me.getValueById(ids[i] + "_fields");// 属性名
            var operator = me.getValueById(ids[i] + "_operator");// 操作符
            var value = me.getValueById(ids[i] + "_remove");// 值
            if (field == "") {
                Ext.Msg.alert("警告", "条件" + (i + 1) + "-属性不能为空！");
                error = "yes";
                break;
            } else if (operator == "") {
                Ext.Msg.alert("警告", "条件" + (i + 1) + "-关系不能为空！");
                error = "yes";
                break;
            } else if (value == "") {
                Ext.Msg.alert("警告", "条件" + (i + 1) + "-值不能为空");
                error = "yes";
                break;
            }
            var logical = "";
            if (i != ids.length - 1) {
                logical = me.getValueById(ids[i] + "_logical");// 逻辑操作符
                if (logical == "") {
                    Ext.Msg.alert("警告", "条件" + (i + 1) + "-连接符不能为空！");
                    error = "yes";
                    break;
                }
            }
            params.push({
                'field': field,
                'operator': operator,
                'value': value,
                'logical': logical
            });
        }
//        var paramString = JSON.stringify(params);
        return {
            "seniorQuery": params,
            "error": error
        };
    },
    /**
     * 通过id获取组件的值
     * @param {String} id 需要获取值的组件的id
     * @private
     */
    getValueById: function (id) {
        var com = Ext.getCmp(id);
        var value;
        if (com instanceof Ext.form.field.Date || com instanceof Ext.form.field.Time)
            value = com.getRawValue();
        else
            value = com.getValue();
        if (!value || value == "null" || value == "undefined ") {
            value = "";
        }
        return value;
    }
});/**
 * 
 * 创建一个查询条件组件
 * 
 * @class NS.grid.query.Condition
 * @author:wangyongtai
 * @private
 */
NS.define('NS.grid.query.Condition',{
   /**
    * 初始化构造函数
    * 
    */
   constructor:function(){
      this.initData.apply(this,arguments);
   },
   /**
	 *  配置组件数据源
	 * @param {Object} config 需要配置的数据源
	 * */
	initData:function(config){
		this.config = config;
		this.ids = [];
		this.num = 1;
	},
	/**
	 *  初始化组件
	 * @param config
	 *            配置参数
	 */
	initComponent : function() {
		var config = this.config;
		this.SeniorQuery = config.SeniorQuery;// 设置父类容器
		this.setFieldsStore({
					data : config.fieldsStoreData
				});// 设置字段属性store
		this.componentsData = config.componentsData;// 组件数据
		this.createLogicalOperatorStore();// 创建逻辑操作符store
		this.createOperatorStore();// 创建操作符store

	},
	/**
	 *  获取前面显示的 类似 条件1、条件2、条件3。。。。
	 *  
	 * @return string 返回的拼写字符串
	 */
	getShowText : function() {
		var text = "条件" + this.num++;
		return text;
	},
	/**
	 * 获取组件panel
	 * @return {Ext.panel.Panel}
	 */
	getPanel : function() {
		var me = this;
		var id = Ext.id();
		this.ids.push(id);
		var fieldCombobox = this.createFieldsCombox({// 创建字段属性combox
			id : id + "_fields",
			configId : id,
			width : 220,
			fieldLabel : me.getShowText() + "-  属性列表"
		});
		var operatorCombox = this.createOperatorCombox({// 操作符combox
			id : id + "_operator",
			width : 150,
			columnWidth : .20
		});
		var text = Ext.create('Ext.form.field.Text', {// 默认text框
			fieldLabel : '值',
			labelWidth : 25,
			width : 150,
			id : id + "_remove"
		});
		var logicalOperatorCombox = this.createLogicalOperatorCombox({// 逻辑操作符combox
			id : id + "_logical",
			width : 150
		});
		var button = Ext.create('Ext.button.Button', {
					text : '添加条件',
					id : id + "_button",
					listeners : {
						'click' : function(button) {
							if (this.text == "添加条件") {
								me.SeniorQuery.window.add(me.getPanel());// 将新生成的面版加入window中
								this.setText("删除条件");
							} else if (this.text == "删除条件") {
								me.SeniorQuery.window.remove(Ext.getCmp(id));
								me.deleteIdFromIds(id);
								me.resizeLabel();
							}

						}
					}
				});
		var panel = Ext.create('Ext.form.Panel', {// 创建 panel
						width : '100%',
						height : 45,
						id : id,
						border : false,
						frame : true,
						margin : '5,5,0,5',
						padding : '10,0,0,0',
						layout : 'column',
						items : [fieldCombobox, operatorCombox, text,
								logicalOperatorCombox, button]
		});
		return panel;
	},
	/**
	 * 设置条件标签属性
	 */
	resizeLabel : function() {
		for (var i = 0; i < this.ids.length; i++) {
			var com = Ext.getCmp(this.ids[i] + "_fields");
			com.setFieldLabel(" 条件" + (i + 1) + "-  属性列表");
		}
		this.num = this.ids.length + 1;// 重置长度
	},
	/**
	 * 删除已经无效的id
	 * 
	 * @param deleteId
	 *            需要在自维护列表中删除的id
	 */
	deleteIdFromIds : function(deleteId) {
		var ids = [];
		Ext.each(this.ids, function(id) {
					if (id != deleteId) {
						ids.push(id);
					}
				});
		this.ids = ids;
	},
	/**
	 * 创建fields store
	 * 
	 * @param config
	 */
	setFieldsStore : function(config) {
		var store = this.createStore({
					fields : ['value', 'display'],
					data : config.data
				});
		this.fieldStore = store;
	},
	/**
	 * 创建fields combox
	 * 
	 * @param config
	 *            配置参数
	 */
	createFieldsCombox : function(config) {
		var me = this;
		var configId = config.configId;// 默认id
		var combox = this.createCombox({
					valueField : 'value',
					displayField : 'display',
					fieldLabel : config.fieldLabel,
					store : this.fieldStore,
					labelWidth : 100,
					id : config.id,
					editable : false,
					width : config.width,
					listeners : {
						'select' : function(combo, records, obj) {
							var dataIndex = records[0].data.value,component,form = this.up("form"),id = configId+ "_remove";
                            form.remove(Ext.getCmp(id));
							Ext.each(me.componentsData, function(c) {
										if (c.name == dataIndex) {
											component = me.createComponent(c,id);
										}
									});
							form.insert(2, component);
							component.setValue("");
						}
					}
				});
		return combox;
	},
	/**
	 * 创建组件 combox,textfield,datefield
	 * 
	 * @param config
	 *            配置参数
	 * @return 一个包含各种组件的对象
	 */
	createComponent : function(item,id) {
		var component;// 声明组件
        //如果是下拉或者时间选择之类的，我考虑选择时触发查询事件。交互起来会更好点。
        var me = this,C = item;
        //var config = this.config;// 配置参数
        var component,basic = {
            name : 'fieldValue',
            xtype : item.xtype,
            codeData : item.codeData,
            id : id,
            itemId : 'remove',
            columnWidth : me.config.secondColumnWidth,
            emptyText : '请输入查询内容...',
            listeners : {
//                specialkey : {
//                    scope : this,
//                    fn : function(textField, e) {
//                        if (e.getKey() == e.ENTER) {
//                            this.fireEvent('doQuery');
//                        }
//                    }
//                }
            }
        };
        if(C.xtype=='textarea'){
        	C = NS.clone(C);//克隆备份
        	C.xtype = 'textfield';
        }
        if(C.xtype=='checkbox'){
        	C = NS.clone(C);
        	C.xtype = 'combobox';
        	C.codeData.data=[{id:'1',mc:'是',dm:'1'},{id:'0',mc:'否',dm:'0'}];
        }
        component = NS.util.FieldCreator.createField(C,basic);
        return component || {};
	},
	/**
	 * 创建operator combox 操作符combox
	 * 
	 * @param config
	 *            创建combox的配置参数
	 * @return Ext.form.field.ComboBox
	 */
	createOperatorCombox : function(config) {
		var combox = this.createDefaultCombox({
					store : this.operatoreStore,
					id : config.id,
					editable : false,
					fieldLabel : '关系',
					width : config.width
				});
		return combox;
	},
	/**
	 * 创建logicalOperator 逻辑操作符combox
	 * 
	 * @param config
	 *            创建combox的配置参数
	 * @return Ext.form.field.ComboBox
	 */
	createLogicalOperatorCombox : function(config) {
		var combox = this.createDefaultCombox({
					store : this.logicalOperatorStore,
					id : config.id,
					editable : false,
					fieldLabel : '连接符',
					width : config.width
				});
		return combox;
	},
	/**
	 * 创建默认combox
	 * 
	 * @param config
	 *            创建combox的配置参数
	 * @return Ext.form.field.ComboBox
	 */
	createDefaultCombox : function(config) {
		var combox = this.createCombox({
					valueField : 'value',
					displayField : 'display',
					fieldLabel : config.fieldLabel,
					queryMode : 'local',
					labelWidth : 45,
					editable : false,
					store : config.store,
					width : config.width,
					id : config.id
				});
		return combox;
	},
	/**
	 * 创建combox
	 * 
	 * @param {Object} config 配置参数
	 */
	createCombox : function(config) {
		var combox = Ext.create('Ext.form.field.ComboBox', config);
		return combox;
	},
	/**
	 * 创建store
	 * 
	 * @param config
	 *            配置参数
	 * @return Ext.data.Store
	 */
	createStore : function(config) {
		var store = Ext.create('Ext.data.Store', config);
		return store;
	},
	/**
	 * 创建操作符store
	 */
	createOperatorStore : function() {
		var store = Ext.create('Ext.data.Store', {
					fields : ['value', 'display'],
					data : [{
								"value" : "=",
								"display" : "等于"
							}, {
								"value" : "like",
								"display" : "相似于"
							}, {
								"value" : "<>",
								"display" : "不等于"
							}, {
								"value" : ">",
								"display" : "大于"
							}, {
								"value" : "<",
								"display" : "小于"
							}, {
								"value" : ">=",
								"display" : "大于等于"
							}, {
								"value" : "<=",
								"display" : "小于等于"
							}]
				});
		this.operatoreStore = store;
	},
	/**
	 * 创建逻辑操作符store
	 */
	createLogicalOperatorStore : function() {
		var store = Ext.create('Ext.data.Store', {
					fields : ['value', 'display'],
					data : [{
								"value" : "and",
								"display" : "并且"
							}, {
								"value" : "or",
								"display" : "或"
							}]
				});
		this.logicalOperatorStore = store;
	}
});
/**
 * @class NS.util.DataConverter
 * @extends NS.Base
 * @private
 *    数据转换工具类
 */
NS.define('NS.util.DataConverter',{
    singleton : true,
	/**
	 * 
	 * 编码数据标准格式(暂未用到，保留)
	 * 
	 * @private
	 * @param {Array} conf 待转换的编码数据
	 * @returns {Array}
	 */
	codedataFormat : function(conf) {
		if (conf == null || conf == 'null') return null;
		if (conf instanceof Array) {
			var arr = [];
			for ( var c in conf) {
				//如果可用的话(暂不确定后台数据是否只将可用信息传到前端)
				if(c.sfky=='1') arr.push({name : c.mc, value : c.id });
			}
			if (arr.length == 0) return null;
			return arr;
		}
		return null;//信息错误
	},
	
	/**
	 * 
	 * 数据标准格式
	 * 
	 * @private
	 * @param{Object}
	 *            conf 待转换的数据
	 * @return {Object}
	 */
	dataFormat :  function(conf) {
		var codeData = {entityName : conf.bmst,data : conf.bmsj||[]};//this.codedataFormat(conf.bmsj); 编码数据暂不作转换
		return {
			id 					 : conf.id,// id--ID
			name 				 : conf.sx,// 字段属性名--SX
			nameCh 				 : conf.sxzwm,// 字段属性中文名--SXZWM
			dataType 			 : conf.sxxslx,// 数据类型--SXXSLX
			isAddFormField 		 : conf.sfxszd == 1,// 是否为表单新增字段（先判断新增字段属性再判断此字段）--SFXSZD
			isQuery 			 : conf.sfcxzd == 1,// 是否为查询字段（控制单字段查询、表头查询、高级查询的显示）--SFCXZD
			dbLength 			 : conf.cd,// 数据库字段长度--CD
			entityName 			 : conf.ssstm,// 所属实体名--SSSTM
			codeData    		 : codeData,// 编码数据--BMBSTM+BMBBZDM
			xtype 				 : conf.sxzjlx,// 组件类型--SXZJLX
			drillExp 		  	 : conf.sfxz,// 下钻属性表达式--SFXZ
			regExp 				 : conf.jyzzbds,// 正则表达式--JYZZBDS
			regValidateErrorMesg : conf.zzjycwxx,// 正则表达式校验错误信息--ZZJYCWXX
			validateType 		 : conf.jylx,// 校验类型--JYLX
			fieldsetGroup 		 : conf.fl,// 分组信息--FL<(目前暂时弃用)>
			isColumnShow 		 : conf.sflbxszd == 1,// Grid中是否显示该列（此属性依赖isColumnCreate）--SFLBXSZD
			isColumnCreate 		 : conf.sflbzd == 1,// Grid中是否创建该列--SFLBZD
			columnWidth 		 : conf.lbkd==null? 150:conf.lbkd,// Grid中该列宽度,默认150--LBKD
			isAddField 			 : conf.xzzdsx == '1',// 新增Form组件中，该列是否出现--XZZDSX
			isUpdateField 		 : conf.xgzdsx == '1',// 修改Form组件中，该列是否出现--XGZDSX
			isBlank 			 : conf.sffk == 1,// 可为空的 --SFFK
			editable 			 : conf.sfkbj == 1,// 可编辑的 --SFKBJ,在可编辑grid中生效
			cc 					 : conf.cc// 树形数据中的层次--CC
		};
	},
	/**
	 * 对单一的JSON对象进行转换的方法
	 * @param {Object} json
	 * @return
	 */
	entity2Standard : function(json){
        return this.dataFormat(json);  	
	},
	/**
	 * 
	 * 从实体数据转换为标准数据的标准接口方法
	 * 
	 * @param {Array} dataArr 实体数据集
	 * @returns {Array} 转换后的标准数据集
	 */
	entitysToStandards:function(dataArr){
		if (dataArr instanceof Array) {
			var arr = [];
			for ( var d in dataArr) {
				arr.push(this.dataFormat(dataArr[d]));
			}
			return arr;
		}
		return null;//数据有误时返回
	}
});
(function(){
  var alias = NS.Function.alias;
   /**
     * @member NS
     * @method E2S
     * @inheritdoc NS.util.DataConverter#entitysToStandards
     */
	NS.E2S = alias(NS.util.DataConverter,'entitysToStandards');
})();/**
 * @class NS.util.FieldCreator
 * @extends NS.Base
 * @private
 *    Field组件创建工具类
 */
NS.define('NS.util.FieldCreator',{
    singleton : true,
    /**
	 * 组件创建器可以创建的组件类型。
	 */
	supportedType :{
		timefield : 'Ext.form.field.Time',// 时间段组件
		combobox : 'Ext.form.field.ComboBox',// 下拉框
		datefield : 'Ext.form.field.Date',// 日期组件
		checkbox : 'Ext.form.field.Checkbox',// 复选框
		textarea :'Ext.form.field.TextArea',// 文本域
		tree : 'Ext.form.field.ComboBox',// 标识有级联功能的下拉框
		textfield : 'Ext.form.field.Text',// 文本框
		numberfield : 'Ext.form.field.Number',// 数字框
        button : 'Ext.button.Button',//按钮
		treecombobox : 'Ext.ux.comboBoxTree',// 树形下拉框（是否复选待定）
        forumSearch:'Ext.form.field.ForumSearch'
		
	},
	/**
	 * 将实体类配置转化成基本组件标准配置项。
	 * @param {Object} entityConfig
	 */
	initStandardConfig : function(entityConfig){
		/**
		 * 基本组件通用配置项。
		 */
		var standardConfig = {
			name : cdata.dataIndex,
			/**级联数据准备** */
			bmst : cdata.bmst,// 编码实体
			bmcc : cdata.bmcc,// 编码层次
			bmtype : cdata.componentType,// 数组类型
			data : componentData.data,// 存储的数据
			fieldLabel : cdata.header,// 属性字段标签
			allowBlank : cdata.allowBlank,// 是否允许为空
			maxLength : cdata.length,
			ssfl : cdata.ssfl,// 所属分组
			hidden : cdata.sfxs == 1 ? false : true
		};
		NS.apply(standardConfig,entityConfig);
		return standardConfig;
	},
	/**
	 * 根据传递的实体配置数组创建组件列表。
	 * @param {Array} 实体配置数据。
	 */
	createFields : function(standardDataSets){
		var components = new Array();
		if(standardDataSets instanceof Array && standardDataSets.length > 1){
			for(var i = 0 ;i<standardDataSets.length;i++){
				var aCompoConfig = this.initStandardConfig(standardDataSets[i]);
				components.push(this.createField(aCompoConfig));
			}
		}else {
			var aCompoConfig = this.initStandardConfig(standardDataSets[0]);
			components.push(this.createField(aCompoConfig));
		}
	},
	/**
	 * 根据组件类型和配置数据创建组件。
	 * @param {String} 组件类型。
	 */
	createField : function(C,config){
        var xtype = C.xtype,cName = this.supportedType[xtype],component;
        var defaultConfig= this.configConvert(C);
        NS.apply(defaultConfig,config);
        this.processOwnerProperty(defaultConfig);
        if(cName){
            var component = Ext.create(this.supportedType[xtype],defaultConfig);
        }else{
            NS.error({
                sourceClass : 'NS.util.FieldCreator',
                sourceMethod : 'createField',
                msg : '组件类型错误！类型为：'+xtype
            });
        }
        return component;
	},
    /**
     * 处理自定义的属性
     * @param config
     */
    processOwnerProperty : function(config){
//        if(config.editable === false){
//            config.fieldStyle = "background:#E6E6E6;";
//            config.readOnly = true;
//        }
        if(config.fields&&config.xtype!='forumSearch'){
           config.store.fields = config.fields;
           delete config.fields;
        }
        if(config.data && config.xtype == "combobox"){//处理数据
            config.store.data = config.data;
            delete config.data;
        }
    },
    /**
     * 配置参数转换
     * @param {Object} config 配置对象
     * @return {Object} 转换后的配置对象
     */
    configConvert : function(C){
        var basic,xtype = C.xtype;
        if(C.codeData){
            basic = {
                name : C.name,
                xtype : C.xtype,
                codeData : C.codeData,
                /** **级联数据准备** */
                bmst : C.codeData.bmst,// 编码实体
                bmcc : C.cc,// 编码层次
                fl :C.fieldsetGroup,//之前是Fieldset分组，目前暂时需要将其置为级联分组的标识
                bmtype : C.xtype,// 数组类型
                data : C.codeData.data,// 存储的数据
                allowBlank : C.isBlank,// 是否允许为空
                // vtype : cdata.vtype,// 校验类型---参照前台详细文档，校验类型
                maxLength : C.dbLength
            };
        }else{
            basic = C;
        }


        switch(xtype){
            case 'textfield': {
                if (C.regExp != null && C.regExp != "") {
                    NS.apply(basic, {
                        regex : new RegExp(C.regExp),// 正则表达式
                        regexText : C.regValidateErrorMesg // 正则表达式校验--错误信息
                    });
                }
                break;
            }
            case 'numberfield': {
                if (C.regExp != null && C.regExp != "") {
                    NS.apply(basic, {
                        regex : new RegExp(C.regExp),// 正则表达式
                        regexText : C.regValidateErrorMesg // 正则表达式校验--错误信息
                    });
                }
                break;
            }
            case 'combobox' : {
                NS.apply(basic, {
                    valueField : 'id',// 值域
                    displayField : 'mc',// 显示域
                    lastQuery : '',
                    queryMode : 'local',
                    editable : false,
                    store : {
//                        fields : [
//                            {name: 'id', type: 'string'},
//                            {name: 'mc', type: 'string'},
//                            {name: 'dm', type: 'string'}
//                        ],
                        fields : ['id','mc','dm','pid'],
                        data : C.codeData.data
                    }
                });
                break;
            }
            case 'tree': {
                NS.apply(basic, {
                    valueField : 'id',
                    displayField : 'text',
                    editable : false,
                    queryMode : 'local',
                    lastQuery : '',
                    store : {
//                        fields : [
//                            {name: 'id', type: 'string'},
//                            {name: 'text', type: 'string'},
//                            {name: 'pid', type: 'string'}
//                        ],
                        fields : ['id','text','dm','pid'],
                        data : C.codeData.data
                    }
                });
                break;
            }
            case 'timefield' : {
                NS.apply(basic, {
                    format :"G时i分",
                    submitFormat:"G:i"
                });
                break;
            }
            case 'textarea' : {
                NS.apply(basic, {
                    height : 50
                });
                break;
            }
            case "treecombobox" : {
                NS.apply(basic, {
//                	root : {
//                	   children :  NS.clone(C.codeData.data)
//                	},
                    editable : false,
                	treeData:NS.clone(C.codeData.data.children),//默认过滤第一个
                	//width:150,
                	autoWidth:true,
                    //isParentSelect : false,
                    rootVisible : false
                });
                delete basic['data'];
                delete basic['codeData'];
                break;
            }
            case 'checkbox' : {
                NS.apply(basic, {
                	boxLabel : '是',
                    inputValue : '1',
                    uncheckedValue : '0'
                });
                break;
            }
            case 'datefield' : {
            	NS.apply(basic,{
            		format : 'Y-m-d'
            	});
                break;
            }
            case 'forumSearch':{
                NS.apply(basic,{
                    service:{}
                });
                break;
            }
        }
        return basic;
    }
});/**
 * @class NS.Chart
 * @extend NS.SimpleComponent
 *  该类不能直接被示例化并使用,如果需要创建Chart类，请参照其子类
 *
 *      var chart = new NS.chart.Column({
                 chartType : 'Column2D',//图表的类型
                 chartConfig : {//针对报表的基本配置
                     "caption":"图表标题",
                     "subcaption":"图表副标题",
                     "xaxisname":"X轴名称",
                     "yaxisname":"Y轴名称",
                     "numberprefix":"$"//y轴单位
                 },
                 renderTo : '123',
                 data ://多图表的数据
                 {
                     labels : ['一月','二月'],
                     dataSet : [
                     {
                     name : '2006',
                     data : [13,14]
                     },{
                     name : '2007',
                     data : [15,17]
                     }
                     ]
                 },

                 data : [//单图表的数据格式
                     {label : '一月',value : 12},
                     {label : '二月',value : 13},
                     {label : '三月',value : 12},
                     {label : '四月',value : 13},
                     {label : '五月',value : 12},
                     {label : '六月',value : 13}
                 ]
 *      });
 */
NS.define('NS.AbstractChart',{
    extend : 'NS.Component',

    id_Sign : '_chart',

    /**
     * @cfg {String} chartType 图表类型
     */
    chartType : '',
    /**
     * swf 文件的路径
     * @private
     */
    baseSwfUrl : 'FusionCharts/charts/',
    chartConfig : {
        "caption":"图表标题",
        "subcaption":"图表副标题",
        "xaxisname":"X轴名称",
        "yaxisname":"Y轴名称",
        "numberprefix":"Y轴值单位"
    },
    /**
     * 构造组件
     * @private
     *
     */
    initComponent : function(){
        var width = this.width,
            height = this.height,
            chartType = this.support(this.chartType),//判断是否支持该组件类型
            swfUrl = this.baseSwfUrl,
            data = this.translate(this.data);//将数据做下转换

        if(!width)width = '100%';
        if(!height)height = '100%';

        this.chartId = this.id+"_fusionchart";
//        this.chart = new FusionCharts({
//            swfUrl: swfUrl+chartType+".swf",
//            id : this.chartId,//id
//            width: width,
//            height: height,
//            debugMode : false
//        });
        this.chart = new FusionCharts(swfUrl+chartType+".swf",this.chartId,width,height,'0','1');
        this.chart.setJSONData(data);
        //配置数据项
        this.chart.configure({
            ParsingDataText: '数据读取中,请稍候...',
            PBarLoadingText: '数据加载中,请稍候...',
            RenderingChartText: '数据正在渲染,请稍候...',
            LoadDataErrorText: '数据加载错误!',
            ChartNoDataText: "无数据可供显示!"
            // add more...-->key:value
        });

        this.callParent(arguments);
    },
    /**
     * @private
     */
    afterRender : function(){
        this.chart.render(this.component.id);
    },
    /**
     * 根据传入的数据，重绘chart图表
     */
    redraw : function(data){
        this.chart.setJSONData(this.translate(data));
    },
    /**
     * 数据转置为Single Series Chart JSON（单图表图系列）
     * 数据格式必须以固定的格式，如下
     *
     *      var chartData = [
     *         {mc : '一月',value : '12'},
     *         {mc : '二月',value : '12'},
     *         {mc : '三月',value : '12'},
     *         {mc : '四月',value : '12'},
     *         {mc : '五月',value : '12'}
     *      ];
     *
     * @private
     * @param {Object} data 需要转置的数据
     */
    translate : function(data){
        var chartData = this.getChartData(data),
            chartParams = this.getChartParams(),
            styles = this.getChartStyles();
        var ret = {};
        NS.apply(ret,chartParams,chartData);
        NS.apply(ret,styles);
        return ret;
    },
    /**
     * 获取Chart的数据
     *  简单格式数据为
     *
          var simpleData = [
                {
                    label : '一月',
                    value : 12
                },
                 {
                     label : '二月',
                     value : 14
                 }
          ];
     *
     *    var complexData = {
     *       labels : ['一月','二月'],
     *       dataSet : [
     *           {
     *              name : '2006',
     *              data : [13,14]
     *           },
     *           {
     *              name : '2007',
     *              data : [13,14]
     *           }
     *       ]
     *    };
     *
     * @private
     * @param {Array} data
     */
    getChartData : function(data){
        var chartType = this.chartType,
            i = 0,
            j = 0,
            item,
            len,
            clen,
            list = [],
            category = [],
            dataset = [],
            categories = [],
            child,
            fields = data.fields,//域属性

            ret = {};
        if(this.isSingleSeriesChart(chartType)){
            ret.data = data;
        }else if(this.isMultiSeriesChart(chartType)){
            for(len = data.labels.length;i<len;i++){
                category.push({label : data.labels[i]});
            }
            for(i = 0;i<data.dataSet.length;i++){
                item = data.dataSet[i];
                child = {};
                child.seriesname = item.name;
                child.data = [];
                for(j = 0,clen = item.data.length;j<clen;j++){
                    child.data.push({value : item.data[j]});
                }
                dataset.push(child);
            }

            categories.push({category : category});
            ret.dataset = dataset;
            ret.categories = categories;

        }
        return ret;
    },
    /**
     * 获取chart的样式
     * @private
     */
    getChartStyles : NS.emptyFn,
    /**
     * 获取单图表系列转换数据
     * @private
     * @param {Array}data
     * @return {Array}
     */
    getSscChartData : function(data){
        var i = 0,len = data.length,item,list = [];
        for(;i<len;i++){
            item = data[i];
            list.push({label : item.mc,value : item.value});
        }
        return list;
    },
    /**
     * 获取图表的参数
     * @private
     * @return {Object}
     */
    getChartParams : function(){
        return {chart : this.getBasicParams()};
    },
    /**
     * @private
     * @return {Object}
     */
    getBasicParams : function(){
        var basic =  {
            // yaxisname : yaxisname || "",
            // xaxisname : xaxisname || "",
            baseFontSize : '12',// 基本字体
            // outCnvBaseFontSize : '12',//画布之外字体
            baseFontColor : '6B6B6B',// 基本字体颜色
            showvalues : '0',// 是否显示值 1 是 0 否
            formatNumberScale : '0',// 是否格式化数字 1 是 会添加k m等英文单位
            showlabels : '1',// 是否显示标签
            legendBgColor : 'FFFFFF',// legend的背景色
            showlegend : '1',// 是否显示legend
            legendposition : 'right',// legend显示位置为右侧(四周均可)
            legendShadow : '1',// legend 的阴影显示
            legendBorderColor : 'FFFFFF',
            legendScrollBgColor : 'DEECFD',
            legendScrollBarColor : 'BAD1EF',
            legendScrollBtnColor : 'BAD1EF',
            // legendborderalpha : "0",// legend的边框宽度
            bgcolor : 'FFFFFF,FFFFFF',// 图形背景色
            showborder : '0',// 图形边框是否显示
            manageLabelOverflow : '1',// 字溢出时会自动隐藏
            useEllipsesWhenOverflow : '1',// 当溢出时,当Label溢出时候使用...将鼠标移至label处会显示
            // showAboutMenuItem : 0,// 是否显示关于fusioncharts
            aboutMenuItemLabel : '河南省精华科技有限公司',// 覆盖目录label
            aboutMenuItemLink : 'n-http://www.gilight.cn',
            zeroPlaneAlpha : '10',// 0线的像素
            use3DLighting : 1,// 是否使用3d光效果
            // defaultAnimation : 1,// 第一次是关闭动态展现
            // 添加下载
            // exportEnabled : 1,
            // exportShowMenuItem : 1,
            //exportFormats : 'PNG=导出png格式图片|JPG=导出jpg格式图片|PDF=导出pdf文件',
            // exportAction : 'download',
            // showExportDialog : 1,
            // exportAtClient : 0,
            // exportTargetWindow : '_slef',
            animation : '1'// 动态展现
            },
            config = this.chartConfig;
        NS.apply(basic,config);
        return basic;
    },
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : '',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : '',
    /**
     * 是否单图系列
     * @param {String} chartType
     */
    isSingleSeriesChart : function(chartType){
        return this.SingleSeriesChartSupport.indexOf(chartType)!=-1;
    },
    /**
     * 是否复合图形系列
     * @param {String} chartType
     */
    isMultiSeriesChart : function(chartType){
        return this.MultiSeriesChartSupport.indexOf(chartType)!=-1;
    },
    /**
     * 合并参数
     * @private
     */
    mergeParams : function(params1,params2){
        NS.apply(params1,params2);
        return params1;
    },
    /**
     * 判断是否支持该图表类型
     *
     * @param  {String} type 图形组件类型
     */
    support: function (type) {
        // 维护的图形组件类型列表
        var chartMap = {
            //1、饼形
            Pie2D: "Pie2D",
            Pie3D: "Pie3D",
            //2、线形
            Line: "Line",
            MSLine: "MSLine",
            ScrollLine2D: "ScrollLine2D",
            //3、环形
            Doughnut2D: "Doughnut2D",
            Doughnut3D: "Doughnut3D",
            //4、条形
            Bar2D: "Bar2D",
            MSBar2D: "MSBar2D",
            MSBar3D: "MSBar3D",
            StackedBar2D: "StackedBar2D",
            StackedBar3D: "StackedBar3D",
            //5、柱形
            Column2D: "Column2D",
            Column3D: "Column3D",
            MSColumn2D: "MSColumn2D",
            MSColumn3D: "MSColumn3D",
            ScrollColumn2D: "ScrollColumn2D",// 滚动条的柱形图
            StackedColumn2D: "StackedColumn2D",
            StackedColumn3D: "StackedColumn3D",
            ScrollStackedColumn2D: "ScrollStackedColumn2D",
            //6、区域形
            Area2D: "Area2D",
            ScrollArea2D: "ScrollArea2D",
            StackedArea2D: "StackedArea2D",
            MSArea: "MSArea"
            // 陆续待加...24
        };
        var chartType = chartMap[type] || "MSColumn2D";// 如果不支持该类型，则将其置为MSColumn2D
        return chartType;
    }
});NS.define('NS.chart.Area',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Area2D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : 'MSArea,StackedArea2D,ScrollArea2D',

    /**
     * 得到chart的参数配置
     *@private
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            basicParams = this.getBasicParams(),
            chartParams,
            commonParams = {
                useroundedges: '1',
                legendShadow: '0',
                labelDisplay: 'AUTO',// 自动下移字
                decimals: '2',// 小数点的精确
                legendBorderColor: '',
                canvasBorderThickness: '0',
                canvaspadding: "10",
                showlabels: '1',
                chartLeftMargin: 0
            };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Area2D') {
            chartParams = commonParams;// 样式
        } else if (chartType == 'MSArea') {
            chartParams = {
//					palette : "2",
                canvasBorderThickness: '0',
                canvasBgColor: 'F5FFFA,FFFFFF',// 画布颜色
                canvasbgAlpha: '10'
//					canvaspadding : "40"
            };
            chartParams = this.mergeParams(chartParams,
                commonParams);
        } else if (chartType == "ScrollArea2D") {
            chartParams = commonParams;
        } else if (chartType == "StackedArea2D") {
            chartParams = {
                showsum: "1"// 显示总和
            };
            chartParams = this.mergeParams(chartParams,
                commonParams);
        } else {
            chartParams = commonParams;
        }

        return {chart : chartParams};
    }
});NS.define('NS.chart.Bar',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Bar2D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : 'MSBar2D,MSBar3D,StackedBar2D,StackedBar3D',

    /**
     * 得到chart的参数配置
     *
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            basicParams = this.getBasicParams(),
            chartParams,
            commonParams = {
                useroundedges : '1',
                legendShadow : '0',
                labelDisplay : 'AUTO',// 自动下移字
                decimals : '2',// 小数点的精确
                showlabels : '1',
                chartTopMargin : 22,
                chartRightMargin : 22,
                chartLeftMargin : 0
            };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Bar2D') {
            chartParams = {};
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'MSBar2D') {
            chartParams = {
                legendShadow : '1'
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'MSBar3D') {
            chartParams = {};
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'StackedBar2D') {
            chartParams = {
                legendShadow : '1',
                showsum : "1"// 显示总和
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else {
            chartParams = {};
            chartParams = this.mergeParams(chartParams, commonParams);
        }

        return {chart : chartParams};
    }
});NS.define('NS.chart.Column',{
   extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Column2D,Column3D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : 'MSColumn2D,MSColumn3D,ScrollColumn2D,StackedColumn2D,StackedColumn3D,ScrollStackedColumn2D',

    /**
     * 得到chart的参数配置
     *
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            chartParams,
            basicParams = this.getBasicParams(),
            //柱形图的属性
            commonParams = {
            labelDisplay: 'AUTO',
            useEllipsesWhenOverflow: '1',
            useroundedges: '1',
            showlabels: '1',
            chartTopMargin: 22,
            chartLeftMargin: 0,
            decimals: '2',// 小数点的精确
            valuePadding: 5
            // 显示值到图形的距离
        };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Column2D') {
            chartParams = commonParams;
        } else if (chartType == 'Column3D') {
            chartParams = {
                canvasBgColor: 'F5FFFA,FFFFFF',
                legendBgColor: 'FFFFFF'
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'MSColumn2D') {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
                anchorMinRenderDistance: '6',
                drawanchors: "0",
                labelpadding: "10"
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'MSColumn3D') {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
//				drawanchors : "0",
                labelpadding: "10",
                canvasBgColor: 'FFFFFF,FFFFFF',
                legendBgColor: 'FFFFFF'
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'ScrollColumn2D') {
            chartParams = {
                pinLineThicknessDelta: 30
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == "StackedColumn2D"
            || chartType == "ScrollStackedColumn2D") {
            chartParams = {
                numvdivlines: "10",
                divlinealpha: "30",
                anchorMinRenderDistance: '6',
                drawanchors: "0",
                showsum: "1",
                useroundedges: "1",
                bgcolor: "FFFFFF,FFFFFF",
                labelpadding: "10"
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else {
            chartParams = {};
            chartParams = this.mergeParams(chartParams, commonParams);
        }
        return {chart : chartParams};
    }
});NS.define('NS.chart.Doughnut',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Doughnut2D,Doughnut3D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : '',

    /**
     * 得到chart的参数配置
     *
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            basicParams = this.getBasicParams(),
            chartParams,
            commonParams = {
                skipOverlapLabels: '1',
                legendShadow: '0',// legend 的阴影显示
//					enableRotation:1,//是否开启旋转,这个属性跟点击图形展开冲突（它俩是互斥关系）
                // legendBorderColor : 'FFFFFF',
                showvalues: '1',
                palette: 2,// 内置样式1-5
                // showZeroPies:'0',//是否显示0值
                enableSmartLabels: 0
                // 设置是否连接线
                // chartTopMargin : 0,
                // chartLeftMargin:20,
            };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Doughnut2D') {
            chartParams = {};
            chartParams = this.mergeParams(chartParams,
                commonParams);
        } else if (chartType == 'Doughnut3D') {
            chartParams = {
//						pieYScale:'180'//图立起来的角度,角度越大,图形展示就越大
            };
            chartParams = this.mergeParams(chartParams,
                commonParams);
        }

        return {chart : chartParams};
    }
});NS.define('NS.chart.Line',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Line',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : 'MSLine,ScrollLine2D',

    /**
     * 得到chart的参数配置
     *
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            basicParams = this.getBasicParams();
        var commonParams = {
            useroundedges: '1',
            legendShadow: '0',
            labelDisplay: 'AUTO',// 自动下移字
            decimals: '2',// 小数点的精确
            showlabels: '1',
            legendBorderColor: '',
            chartTopMargin: 22,
            chartLeftMargin: 0
        };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Line') {
            chartParams = {
                palette: "2",
                canvasBorderThickness: '0',
                canvasbgAlpha: '10',
                canvaspadding: "40"
            };
            chartParams = this.mergeParams(chartParams,
                commonParams);
        } else if (chartType == 'MSLine') {
            chartParams = {
                palette: "2",
                // labelpadding : "10",
//						divlinealpha : "30",
                canvasBorderThickness: '0',//边框款第
                // canvasBorderColor:'FFFFFF',//边框颜色
                canvasBgColor: 'F5FFFA,FFFFFF',// 画布颜色
                canvasbgAlpha: '10',
//						alternatehgridalpha : "20",
                canvaspadding: "40"
            };
            chartParams = this.mergeParams(chartParams,
                commonParams);
        } else if (chartType == "ScrollLine2D") {
            chartParams = {
                canvasbgAlpha: '10',
                canvaspadding: "40"
            };
            chartParams = this.mergeParams(chartParams,
                commonParams);
        }

        return {chart : chartParams};
    }
});NS.define('NS.chart.Pie',{
    extend : 'NS.AbstractChart',
    /**
     * 简单图表类型支持
     */
    SingleSeriesChartSupport : 'Pie2D,Pie3D',
    /**
     * 多图表支持
     */
    MultiSeriesChartSupport : '',

    /**
     * 得到chart的参数配置
     *
     * @return {Object} chart 参数配置
     */
    getChartParams: function () {
        var chartType = this.chartType,
            basicParams = this.getBasicParams(),
            chartParams,
            commonParams = {
            enableSmartLabels: 0,
//			enableRotation:1,//是否开启旋转,这个属性跟点击图形展开冲突（它俩是互斥关系）
            // showZeroPies:'0',//是否显示0的饼 默认1
            skipOverlapLabels: '1',
            legendShadow: '0',// legend 的阴影显示
            // legendBorderColor : 'FFFFFF',
            palette: 2,// 内置样式1-5
            showvalues: '1'
            // ,
            // captionPadding : '10'// 标题到画布的距离
        };

        commonParams = this.mergeParams(commonParams,  basicParams);

        if (chartType == 'Pie2D') {
            chartParams = {
                bgalpha: "60"
                // ,pieRadius : '100'// 饼图的半径
                // bgratio : "100"//背景比例
            };
            chartParams = this.mergeParams(chartParams, commonParams);
        } else if (chartType == 'Pie3D') {
            chartParams = commonParams;
        }
        return {chart : chartParams};
    }
});/**
 * @class NS.form.FieldSet
 * @extends NS.container.Container
 */
NS.define('NS.form.FieldSet', {
	extend : 'NS.container.Container',
	initData : NS.emptyFn,
    /**
     * @cfg title fieldset显示的标题
     */
	/***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
        	checkboxName : true,
        	checkboxToggle : true,
        	collapsed : true,
        	collapsible : true,
        	focusOnToFront : true,
        	saveDelay : true,
        	shadow : true,
        	stateEvents : true,
        	stateful : true,
        	title : true
        });
    },
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.FieldSet', config);
	},
	initEvents:function(){
		this.callParent();
	}
});/**
 * @class NS.form.BasicForm
 * @extends NS.container.Container
 * 例如
 *
 *          var layoutData={
                        height:300,// 高度
                        width:300,// 宽度
                        padding : '10 10 10 10',
                        margin : '10 10 10 10',
                        modal:true,// 魔态，值为true是弹出窗口的。
                                                title : '新增Form',
                        items : [{
                                xtype : 'fieldset',
                                columns : 3,
                                colspan : 2,
                                rowspan : 2,
                                padding : '10 10 10 10',
                                margin: '10 10 10 10',
                                groupName : '',
                                border : true,
                                items : [{
                                    xtype : 'entityField',
                                    propertyName : 'xh',
                                    colspan : 2,
                                    rowspan : 2,
                                    width: 2
                                }]
                        }]
                    };
            var form = new NS.form.BasicForm(layoutData);
 */
NS.define('NS.form.BasicForm',{
    extend : 'NS.container.Panel',
    /**
     * @cfg {Boolean} modal 定义Form是否是窗体，能够单独显示
     */
    /**
     * @private
     * @param config
     */
    initComponent : function(config){
    	if(config && config.modal){
            NS.apply(config,{
                closable : true,
                floating : true,
                draggable : true
            });
        }
        this.component = Ext.create('Ext.form.Panel',config);
    },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
        	modal:true
        });
    },
    initEvents:function(){
        this.callParent(arguments);
        /**
         * 针对form本身添加beforeshow事件
         */
        this.addEvents('beforeshow');
    },
    /**
     * 执行beoreshow事件监听
     */
    onBeforeshow:function(){
        this.component.on('beforeshow',function(t, e){
            this.fireEvent('beforeshow', this);
        },this);
    },
    /***
     * 设置Form中所有字段的属性的值
     * @param {Object} data 值集合
     */
    setValues : function(values){
        var vs = this.removeNUSL(values);
        this.component.form.setValues(vs);
    },
    /***
     * 获取form中所所有field的值集合对象
     * @return {Object}
     */
    getValues : function(){
//        return this.removeNUSL(this.component.form.getValues());
        return this.component.form.getValues();
    },
    /***
     * 根据field的name获取一个field对象
     * @param {String} name 组件的name
     * @return {NS.form.field.BaseField}
     */
    getField : function(name){
        var field = this.component.form.findField(name);
        if(NS.isExtObj(field)){
            return NS.util.ComponentInstance.getInstance(field);
        }
    },
    /***
     * 通过field的name属性来查找对应的field对象，并设置其值
     * @param {String} name  field组件的name
     * @param {Object} value 要设置的值
     */
    setFieldValueByName : function(name,value){
        var field = this.component.form.findField(name);
        if(field && (value === false || value)){
            field.setValue(value);
        }
    },
    /**
     * 根据field组件的name，获取该field组件的值
     * @param {String} name  field组件的name
     * @return {Object}
     */
    getFieldValueByName:function(name){
    	var field = this.component.form.findField(name);
        if(field){
            return field.getValue();
        }
        throw 'BasicForm。js:name为'+name+'的组件不存在！';
    },
    /**
     * 移除属性中为“” null undefined 的属性
     * @private
     * @param object
     */
    removeNUSL: function(obj){
        var object = NS.clone(obj);
        for(var i in object){
//            if(object !== false && object != 0 && object[i]!="0" && object[i]!="false" && !object[i]){
//                delete object[i];
//            }
        	if(NS.isString(object[i]) || NS.isNumber(object[i]) || NS.isBoolean(object[i]) || NS.isObject(object[i]) || NS.isArray(object[i])){}
            else{delete object[i];}
//            if(NS.isDefined(object[i]) && object[i]!==null){}else delete object[i];
        }
        return object;
    },
    /**
     * 对{NS.form.BasicForm} 中的field的值进行校验，如果校验不通过，返回false，通过返回true
     * @return {Boolean}
     */
    isValid : function(){
        return this.component.getForm().isValid();
    },
    /**
     * 对{NS.form.BasicForm} 中的字段的值进行重置
     * @return {NS.form.BasicForm}
     */
    reset : function(){
        this.component.getForm().reset();
        return this;
    },
    /**
     * 清空Form中所有Field的值
     */
    clearValues : function(){
        var c = this.component;
        if(c.trackResetOnLoad == true){
            c.trackResetOnLoad = false;//将标志位置为false
            this.reset();
            this.component.trackResetOnLoad = true;
        }else{
            this.reset();
        }

    },
    /**
     * 提交Form
     *
     *      var form =new NS.form.BasicForm();
     *
     *      form.submit({
     *          url : 'sfsfs.action',
     *          params : {},
     *          callback : function(result){
     *
     *          },
     *          timeout : 300000,请求中断时间
     *          scope ： this
     *      });
     * @param {Object} obj obj 参数对象必须包含的属性为url callback,
     *         params 和scope可以根据自己的需要添加
     */
    submit : function(obj){
        var me = this;
        obj = obj || {};
        this.component.submit({
            clientValidation : true,
            url : obj.url,
            params : obj.params,
            timeout : obj.timeout,
            success : function(form,action){
                if(obj.callback){
                    obj.callback.call(obj.scope||me,action.result);
                }
            },
            failure : function(form,action){
                if(obj.callback){
                    obj.callback.call(obj.scope||me,action.result);
                }
            },
            scope : obj.scope || this
        });
    }
});/**
 * @class NS.form.field.BaseField
 * @extends NS.Component field的基类
 */
NS.define('NS.form.field.BaseField', {
	extend : 'NS.Component',
	/**
	 * @cfg {Function} enterFn 回车事件产生后的回调函数
	 */
    /**
     * @cfg {Boolean} editable 组件是否可编辑
     */
	/**
	 * @cfg {String} fieldLabel 文本标签名称
	 */
	/**
	 * @cfg {Boolean} hideLabel 是否隐藏文本标签名称 默认false
	 */
	/**
	 * @cfg {String} labelAlign 文本标签位置 三个可选项："left","right","top".默认"left"
	 */
    /**
     * @cfg {String} invalidText 校验错误时的提示信息
     */
	/**
	 * @cfg {Number} labelWidth 文本标签宽度
	 */
	/**
	 * @cfg {Number} labelSeparator 文本分隔符
	 */
	/**
	 * @cfg {String} name 组件名称
	 */
	/**
	 * @cfg {Boolean/String} shadow 是否显示阴影,true表示显示,false则不显示 默认"sides"
	 */
	/**
	 * @cfg {String} style 组件样式 语法同css标准语法
	 */
	/**
	 * @cfg {Object} value 组件的值 
	 */
    /**
     * @cfg {Boolean} allowBlank 是否允许空值 默认false
     */
    /**
     * @cfg {String} blankText  不允许为空值是，若组件值为空则提示该信息
     */
    /**
     * @cfg {Boolean} disabled 是否可用 默认true
     */
    /**
     * @cfg {Boolean} editable 是否可编辑  默认true
     */
    /**
     * @cfg {String} emptyText 组件值为空是显示在组件的文字
     */
    /**
     * @cfg {Boolean} readOnly 是否只读 默认false
     */
    /**
     * @cfg {String} regex 正则表达式 对字段值进行测试验证过程中的一个JavaScript RegExp对象。如果测试失败，本场将被标记为无效使用要么regexText或invalidText。
     */
    /**
     * @cfg {RegExp} regexText 正则表达式校验的信息提示
     */
    /**
     * @cfg {Number} size  在文字输入元素的'大小'属性的初始值。这是仅用于域没有配置的宽度并没有给出其容器的布局宽度。默认为20。
     */
    /**
     * @cfg {String} vtype 验证类型名称定义
     */
    /**
     * @cfg {String} vtypeText 自定义错误消息显示Vtype域提供的消息，目前此字段设置的默认到位。注：仅适用于如果Vtype域设置，否则忽略。
     */
	/**
	 * @cfg {Number} tabIndex 此字段的tabIndex。注意，这仅适用于所呈现的领域，而不是那些通过内置applyTo
	 */
    /**
     * @cfg {Number} maxLength 输入的值的最大长度
     */
	/**
	 *  {Object} listeners 监听配置项 该项保留，暂不建议使用
	 */
	initData : NS.emptyFn,
	/**
	 * 创建一个Field组件
	 * @private
	 * @param {Object}
	 *            config 配置对象
	 */
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.field.Base', config);
	},
	/**
	 * 执行enterFn方法,为扩展方法,
	 * 需在组件生成时添加enterFn对象,
	 * 该对象key对应的value为function(text,textValue,textRawValue){}
	 * @param enterFn
	 */
	doEnterFn:function(enterFn){
		//Ext.form.field.Text,Ext.EventObject,Object
		this.component.on('specialkey',function(text,e,eOpts){
			if(e.getKey()==13){
		     	enterFn(text,text.getValue(),text.getRawValue());
				//enterFn.call(enterFn,text,text.getValue(),text.getRawValue());
			}
		});
	},
	/**
	 * @private
	 * 初始化参数配置mapping映射关系
	 */
	initConfigMapping:function(){
		this.callParent();
        var processEditable = function(value,property,config){
               config.readOnly = Boolean(!value)
               if(!value){
                  config.fieldStyle = "background:#E6E6E6;";
               }
        }
		this.addConfigMapping({
			isEnter:true,
			enterFn:true,
			fieldLabel:true,
            fieldStyle : true,
			autoScroll:true,
			autoShow:true,
			hideLabel:true,
			labelAlign:true,
			labelWidth:true,
			labelSeparator:true,
			name:true,
			shadow:true,
			style:true,
			value:true,
			xtype:true,
			tabIndex:true,
			data:true,
            editable : processEditable,
			listeners:true,
            maxLength : true,
            validator : true
		});
	},
    /**
     * @private
     */
    initEvents:function(){
        this.callParent();
        this.addEvents(
            /**
             * @event keydown 键盘点下时触发
             * @param {NS.Component} this
             */
            'keydown',
            /**
             * @event keypress 按键输入字段事件。此事件只触发，如果enableKeyEvents设置为true。
             * @param {NS.Component} this
             */
            'keypress',
            /**
             * @event keyup 键盘键松开时触发
             * @param {NS.Component} this
             */
            'keyup',
            /**
             * @event select 数据被选中时触发
             * @param {NS.Component} this
             * @param {Array[]} array 对象数组
             */
            'select',
            /**
             * @event  change Field的值变换的时候触发该事件
             * @param {NS.Component} this
             * @param {String} newvalue 新值
             * @param {String} oldvalue 旧值
             */
            'change',
            /**
             * @event  blur Field失去焦点时触发该事件
             * @param {NS.Component} this
             */
            'blur',
            /**
             * @event  focus Field获得焦点时触发该事件
             * @param {NS.Component} this
             */
            'focus',
            /**
             * 特殊按钮事件
             * @event specialkey 特殊案件事件
             * @param {NS.Component} com
             * @param {NS.Event} event
             */
            'specialkey'

        );
    },
	onKeydown : function(){
        this.component.on('keydown',function(){
            this.fireEvent('keydown',this);
        },this);
    },
    onKeyup : function(){
        this.component.on('keyup',function(){
            this.fireEvent('keyup',this);
        },this);
    },
    onKeypress : function(){
        this.component.on('keypress',function(){
            this.fireEvent('keypress',this);
        },this);
    },
    onSelect : function(){
        this.component.on('select',function(com,records){
            var array = NS.Array.pluck(records,'data');
            this.fireEvent('select', this,array);
        },this);
    },
    onChange : function(){
        this.component.on('change',function(com,newvalue,oldvalue){
            this.fireEvent('change', this,newvalue,oldvalue);
        },this);
    },
    onBlur : function(){
        this.component.on('blur',function(com){
            this.fireEvent('blur', this);
        },this);
    },
    onFocus : function(){
        this.component.on('focus',function(com){
            this.fireEvent('focus', this);
        },this);
    },
    onSpecialkey : function(){
        this.component.on('specialkey',function(com,event){
            NS.Event.setEventObj(event);
            this.fireEvent('specialkey', this,event);
        },this);
    },
	/**
	 * 得到组件名称
	 * 
	 * @return {String}
	 */
	getName : function() {
		return this.component.getName();
	},
	/**
	 * 设置实际值
	 * 
	 * @param {Object} name
	 */
	setValue : function(name) {
		this.component.setValue(name);
	},
	/**
	 * 设置显示值
	 * 
	 * @param {Object} value
	 */
	setRawValue : function(value) {
		this.component.setRawValue(value);
	},
	/**
	 * 设置组件宽度
	 * 
	 * @param {Number}  width
	 */
	setWidth : function(width) {
		this.component.setWidth(width);
	},
	/**
	 * 判断是否能通过过校验
	 * 
	 * @return {Boolean} 通过校验返回true,反之false
	 */
	isValid : function() {
		return this.component.isValid();
	},
	/**
	 * 得到组件的值
	 * 
	 * @return {Object}
	 */
	getValue : function() {
		return this.component.getValue();
	},
	/**
	 * 设置是否可用
	 * 
	 * @param {Boolean} flag
	 * 
	 */
	setDisabled : function(flag) {
		this.component.setDisabled(flag);
	},
	/**
	 * 设置是否可见
	 * 
	 * @param {Boolean}  flag
	 */
	setVisible : function(flag) {
		this.component.setVisible(flag);
	},
    /**
     * 设置组件是否可编辑
     *
     * @param {Boolean}　editable
     */
    setEditable : function(editable) {
        this.component.setEditable(editable);
    },
	/**
     * 设置是否只读
     *
     * @param {Boolean} flag
     */
    setReadOnly : function(flag) {
        this.component.setReadOnly(flag);
        if(this.component.setFieldStyle){
            if(flag === true){
                this.component.setFieldStyle("background:#E6E6E6;");
            }else if(flag === false){
                this.component.setFieldStyle("background:white;");
            }
        }
    },
//	/**
//	 * 增加监听事件
//	 *
//	 * @param {String}  eName 事件名
//	 * @param {Function} fn 相应回调函数
//	 * @param {Object}  scope 作用域
//	 * @param {Object}  options
//	 */
//	addListener : function(eName, fn, scope, options) {
//		this.component.addListener(eName, fn, scope, options);
//	},
//	/**
//	 * 销毁组件
//	 */
//	destroy : function() {
//		this.component.destroy();
//	},
	/**
	 * 找到该组件的上级组件,如果fn返回true,则上级组件被返回
	 * @private
	 * @param {Function}
	 *            fn
	 * @return {Ext.container.Container}
	 */
	findParentBy : function(fn) {
		return this.component.findParentBy(fn);
	},
	/**
	 * 得到显示值
	 * @return {Number/String}
	 */
	getRawValue:function(){
		return this.component.getRawValue();
	},
    /**
     * 设置field的标签名
     * @param {String} label 标签名
     */
    setFieldLabel : function(label){
        this.component.setFieldLabel(label);
    },
    /**
     * 获取field的标签字符串
     * @param {String}
     */
    getFieldLabel : function(){
        return this.component.getFieldLabel();
    }
});/**
 * @class NS.form.field.Text
 * @extends NS.form.field.BaseField
 */
NS.define('NS.form.field.Text', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} grow 是否允许自增长和收缩其内容 默认false
	 */
	/**
	 * @cfg {String} growAppend 一个字符串，将被追加到字段的当前值为计算目标字段大小的目的。仅用于配置成长是真实的。默认为一个大写的“W”（常见的字体中最宽字符），留出足够的空间，为下键入的字符，避免转移前的宽度字段值进行调整。
	 */
	/**
	 * @cfg {Number} growMax 允许自增长的最大宽度，当grow为true时生效
	 */
	/**
	 * @cfg {Number} growMin 允许自增长的最小宽度，当grow为true时生效 
	 */
	initComponent:function(cfg){
		this.component = Ext.create('Ext.form.field.Text',cfg);
		if(cfg.enterFn)this.doEnterFn(cfg.enterFn);
	},
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			allowBlank:true,
			blankText:true,
			editable:true,
			emptyText:true,
            inputType : true,
			disabled:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			maxText:true,
			maxValue:true,
			minText:true,
			minValue:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			vtype:true,
			vtypeText:true
		});
	}
});		/**
 * @class NS.form.EntityForm
 * @extends NS.Base
 * @singleton
 *
 *      创建具体实体form组件
 *
        var form =  NS.form.EntityForm;
        var form = form.create({
                    data : data,
                    autoScroll : true,
                    columns : 2,
                    margin : '5px',
                    autoShow : true,
                    modal:true,// 模态，值为true是弹出窗口的。
                  //items : ['id','xh','xm','yyxId','yzy','ybjId','ynj',
                  //  'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'],
                    items : [
                        {
                            xtype : 'fieldset',
                            columns : 2,
                            title : '分组1',
                            height : 250,
                            items : [{name : 'id',hidden : true},'xh','xm','yyxId']
                        },
                        {xtype : 'fieldset',
                            title : '分组2',
                            columns : 1,
                            items : [
                                'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'
                            ]},
                        {xtype : 'fieldset',
                            title : '分组2',
                            columns : 3,
                            colspan : 2,
                            items : [
                                'yzy','ybjId','ynj'
                            ]}
                    ]
                });
 */
NS.define('NS.form.EntityForm',{
    singleton : true,
    /**
     * 构造函数
     * @param {Object} config 配置对象
     * @private
     */
    constructor : function(){
        this.callParent(arguments);
    },
    /**
     * 创建具体实体form组件,如以下代码所示。
     * 在较为复杂的情况下对Form进行布局的时候，如果某个field直接按照后台实体属性表的形式来的话，那么只需要写上属性名即可，
     * 如果需要做特殊配置的话，就以配置对象的形式走。
     *
         var form =  NS.form.EntityForm;
         var form = form.create({
                        data : data,
                        autoScroll : true,
                        columns : 2,
                        margin : '5px',
                        autoShow : true,
                        fieldConfig : {width : 200,height:200},//默认全局Field配置属性
                        modal:true,// 模态，值为true是弹出窗口的。
                      //items : ['id','xh','xm','yyxId','yzy','ybjId','ynj',
                      //'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'],
                        items : [
                            {
                                xtype : 'fieldset',
                                columns : 2,
                                title : '分组1',
                                height : 250,
                                items : [{name : 'id',hidden : true},'xh','xm','yyxId']
                            },
                            {xtype : 'fieldset',
                                title : '分组2',
                                columns : 1,
                                items : [
                                    'xyxId','xzy','bjId','xnj','ydlb','ydyy','xxz'
                                ]},
                            {xtype : 'fieldset',
                                title : '分组2',
                                columns : 3,
                                colspan : 2,
                                items : [
                                    'yzy','ybjId','ynj'
                                ]}
                        ]
                    });

     * @param {Object} config 创建BasicForm的配置对象
     * @return {NS.form.BasicForm}
     */
    create : function(config){
        this.fieldConfig = config.fieldConfig || {};
        this.formType = config.formType;//待创建Form类型（新增，修改，查看）
        this.fields = [];
        this.cascade = {};//级联集合
        this.initFieldsConfig(config);
        this.processItems(config);
        this.processCascade(this.fields);
        var layout = config.layout;
        NS.applyIf(config,{
                            layout : {type : 'table',columns : config.columns||2},
                            frame : true
                          });
        if(layout)config.layout = layout;

        if(config.modal){
            NS.apply(config,{
                closable : true,
                closeAction : config.closeAction||'destroy',
                floating : true,
                shadow : false,
                //trackResetOnLoad : true,//当为true时,reset方法是还原到之前一次的默认值,默认false
                border : false,
                frame : true,
                bodyBorder : false
            });
        }
        delete this.formType;
        delete this.fieldConfig;
        var form = new Ext.form.Panel(config);
        var bform = NS.util.ComponentInstance.getInstance(form);
        if(config.values){
//          bform.setValues(config.values);
            bform.setValues(config.values);
        }
        return bform;
    },
    /**
     * 初始化fields 配置
     * @param {Object} config 配置参数
     * @private
     */
    initFieldsConfig : function(config){
        var data = config.data;
        var map = this.fcMap = {};
        for(var i= 0,len = data.length;i<len;i++){
            var item = data[i];
            map[item.name] = item;
        }
    },
    /**
     * 创建Fields对象
     * @param config
     * @private
     */
    processItems : function(config){
        var me = this,
            items = config.items||[],
            createField = function(){
                return me.createField.apply(me,arguments);
            },
            item,
            name = null;
        if(items.length !== 0){
            for(var i= 0,len = items.length;i<len;i++){
                item = items[i];
                if(NS.isNSComponent(item)){
                    items[i] = item.component;//属性不变
                }else if(item.xtype === 'fieldset'){
                    items[i] = this.createFieldSet(item);
                }else{
                    if(NS.isString(item)){
                        name = item;
                        item = this.getFieldConfig(name);
                    }else if(item && NS.isObject(item)){
                        name = item.name;
                        var c = this.getFieldConfig(name);
                        NS.apply(c,item);
                        item = c;
                        if(item.readOnly == true){ //额外补丁处理--如果配置属性readOnly为true，将对应字段置为灰色
                                item['fieldStyle'] = "background:#E6E6E6;";
                        }
                    }
                    items[i] = createField(this.fcMap[name],item);
                }
            }
        }else{
            config.items = this.getDefaultItems(config);
        }
    },
    /**
     * 获取默认的field集合
     * @private
     * @return {Array}
     */
    getDefaultItems : function(config){
        var me = this,
            data = config.data,array = [],item,
            createField = function(){
                return me.createField.apply(me,arguments);
            };
        for(var i= 0,len=data.length;i<len;i++){
            item = data[i];
            array.push(createField(item,this.getFieldConfig(item.name)));
        }
        return array;
    },
    /**
     * 创建Fieldset容器
     * @param {Object} config 创建fieldset 的配置对象
     * @private
     */
    createFieldSet : function(config){
        var me = this,
            items = config.items||[],
//            createField = NS.Function.alias(NS.util.FieldCreator,'createField'),
            createField = function(){
                return me.createField.apply(me,arguments);
            },
            item,
            name = null;
        for(var i= 0,len = items.length;i<len;i++){
            item = items[i];
            if(item.xtype === 'fieldset'){
                items[i] = this.createFieldSet(item);
            }else if(NS.isNSComponent(item)){
                items[i] = item.component;
            }else{
                if(NS.isString(item)){
                    name = item;
                    item = this.getFieldConfig(name);
                }else if(item && NS.isObject(item)){
                    name = item.name;
                    var c = this.getFieldConfig(name);
                    NS.apply(c,item);
                    item = c;
                    if(item.readOnly == true){ //额外补丁处理--如果配置属性readOnly为true，将对应字段置为灰色
                        item['fieldStyle'] = "background:#E6E6E6;";
                    }
                }
                items[i] = createField(this.fcMap[name],item);
            }
        }
        NS.applyIf(config,{layout : {type : 'table',columns : config.columns||2}});
        delete config.columns;
        return new Ext.form.FieldSet(config);
    },
    /**
     * 创建组件的代理，处理其他额外的事情
     * @private
     * @return {Object}
     */
    createField : function(C,config){
        var fields = this.fields,//field存储集合
            cascade = this.cascade;//级联集合

        if(C.xtype == "combobox" && config.editable == true){
            config.listeners  = {
                blur : function(com){
                    var value = com.getValue();
                    var rawValue = com.getRawValue();
                    if(value == rawValue)com.setValue();
                }
            }
        }
        var f = NS.util.FieldCreator.createField.apply(NS.util.FieldCreator,arguments);
        fields.push(f);
        var fl = f.fl;
        if(cascade[fl] && f.bmcc){
            cascade[fl].push(f);
        }else if(!cascade[fl] && f.bmcc){
            cascade[fl] = [];
            cascade[fl].push(f);
        }
        return f;
    },
    /**
     * 通过name来获取field的配置参数
     * @private
     * @param {String} name 组件对应的name
     * @private
     */
    getFieldConfig : function(name){
        var C = this.fcMap[name]||{};
        if(!C){
            NS.error({
                sourceClass : 'NS.form.EntityForm',
                sourceMethod : 'getFieldConfig',
                msg : 'name对应的组件没有查找到：name为'+name
            });
        };
//        var xtype = C.xtype;
        var basic = {
            width : 270,
            labelWidth : 70,
            fieldLabel : C.nameCh,// 属性字段标签
            maxLength : C.dbLength
            },
            fieldConfig = this.fieldConfig||{};
        NS.apply(basic,fieldConfig);

        if(!C.isBlank){
            basic['labelSeparator'] = "<span >[</span><span style='color:red;'>*</span><span>]:</span>";
        }
        if(C.editable == false){
            basic['readOnly'] = true;
            basic['fieldStyle'] = "background:#E6E6E6;";
        }
        NS.apply(basic,this.getFieldConfigByFormType(C));//获取不同类型的Form的字段的配置参数
        return basic;
    },
    /***
     * 获取不同类型Form的Field配置参数
     * @private
     */
    getFieldConfigByFormType : function(config){
        var type = this.formType,basic = {};
        switch (type){
            case 'add' : {
                NS.apply(basic,{
                    hidden : config.isAddField == 1 ? false : true// 如果修改字段属性为0
                });
                break;
            }
            case 'update' : {
                NS.apply(basic,{
                    hidden : config.isUpdateField == 1 ? false : true// 如果修改字段属性为0
                });
                break;
            }
            case 'see'  : {
                NS.apply(basic,{
                    readOnly : true,
                    fieldStyle : 'background:#E6E6E6;'
                });
            }
        }
        return basic;
    },
    /**
     * 处理实体Form的级联问题
     * @private
     */
    processCascade : function(){
        var  cascade = this.cascade,map = cascade;
        for (var i in map) {//将级联分组内的组件按照层次进行排序
            if (i && map[i] instanceof Array) {
                map[i].sort(function(a, b) {
                    if (a.bmcc > b.bmcc) {
                        return 1;
                    }
                    if (a.bmcc < b.bmcc) {
                        return -1;
                    }
                    return 0;
                });
            }
        }
        for (var i in map) {
            if (i && map[i] instanceof Array) {
                this.associate(map[i]);
            }
        }
    },
    /**
     * 使得组件之间产生事件关联
     * @private
     */
    associate : function(array){
        var item;
        for(var i= 0;i<array.length;i++){
            if (i == array.length - 1) {
                return;
            }
            (function(array,i,me){
                  var com1 = array[i],com2 = array[i+1];
                      com1["nextSelectCore"] = com2;
                  com1.on('select',function(combox, records){
                      var id = records[0].get('id'),next = com2;
                      com2.getStore().clearFilter();
                      com2.getStore().filter('pid',id);
                      com2.setValue();
                      while(next){
                          next.setValue();
                          next = next.nextSelectCore;
                      }
                  });
                  com2.on('expand',function(combox, records){
                      if(com1.getValue()){
                            com2.getStore().clearFilter();
                            com2.getStore().filter(com2.associateField||'pid',com1.getValue());
                      }
                      return true;
                  });
            })(array,i,this);
        }
    }
});/**
 * @class NS.form.field.Checkbox
 * @extends NS.form.field.BaseField
 * 复选框组件
 */
NS.define('NS.form.field.Checkbox', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {String} boxLabel 复选框标签名
	 */
	/**
	 * @cfg {String/Number} inputValue 复选框实际值
	 */
	/**
	 * @cfg {Boolean} checked 是否被选中 默认false
	 */
	/**
	 * @cfg {Function} handler 调用的函数时，选中的值的变化（可以用来代替处理的变化事件）。包含两个属性checkbox 当前复选框 checked 新选中的复选框 
	 */
	/**
	 * @cfg {String} uncheckedValue 如果配置的话，这将被提交复选框的值如果复选框是选中的，在形式提交。默认情况下，这是不确定的，从而导致没有提交提交表单时（HTML复选框的默认行为）复选框字段。
	 */
	/**
	 * @cfg {Boolean} validateOnBlur 该字段是否应向验证失去焦点时。这将导致作为用户验证步骤通过表格中的字段的字段，而不管它们是否正在改变前进的道路上的那些字段。另见validateOnChange 默认true
	 */
	/**
	 * @cfg {Boolean} validateOnChange 该字段是否应向验证whSpecifies，这一领域是否应立即进行验证时，发现其价值的变化。如果验证结果字段的有效性的变化，一个validitychange事件将被解雇。这允许字段用户键入立即显示其内容的有效性的反馈意见。当设置为false，反馈不会立竿见影。但形式仍然会被验证，然后再提交如果的clientValidation的Ext.form.Basic.doAction选项被启用，如果字段或形式手动验证。默认true
	 */
	/**
	 * @private
	 * @param config
	 */
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.field.Checkbox', config);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			inputValue:true,
			boxLabel:true,
			checked:true,
			draggable:true,
			handler:true,
			invalidText:true,
			readOnly:true,
			uncheckedValue:true,
			validateOnBlur:true,
			validateOnChange:true
		});
	},
	/**
	 * 
	 * @param {Number/String/Object} width 宽度 为Ojbect时应如：{wdith:wValue,height:hValue},为undefeind/无参数时大小不更改
	 * @param {Number/String} height
	 * @return {Ext.component} 返回this
	 */
	setSize:function(width,height ){
		return this.component.setSize(width,height);
	},
    /**
     * 获取实际值的方法
     * @return {*}
     */
    getInputValue : function(){
        return this.component.getSubmitValue();
    }
});/**
 * @class NS.form.field.ComboBox
 * @extends NS.form.field.BaseField 下拉框组件
 * 
 */
NS.define('NS.form.field.ComboBox', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Array} fields 域属性集合
	 */
	/**
	 * @cfg {Array} data 数据 隶属于Store
	 */
	/**
	 * @cfg {String} valueField 值域
	 */
	/**
	 * @cfg {Boolean} autoSelect  真正可自动突出显示所收集的数据存储在下拉列表中打开时，它的第一个结果。值为false会导致没有自动突出显示在列表中，所以用户必须手动突出一个项目，然后按Enter或Tab键选择（除非是真实的价值（TYPEAHEAD）），或使用鼠标选择一个值。
	 */
	/**
	 * @cfg {String} displayField 显示域
	 */
	/**
	 * @cfg {Boolean} multiSelect如果设置为true，允许多选，并用,号分割
	 */
	/**
	 * @cfg {Number} pageSize 每页显示条数
	 */
	/**
	 * @cfg {String} pickerAlign  对齐位置对齐选择器。默认为“TL-BL？”
	 */
	/**
	 * @cfg {Number[]} pickerOffset 偏移[X，Y]定位选择器时在除了pickerAlign的使用。默认为undefined。
	 */
	/**
	 * @cfg {Number} queryDelay 延迟遍历 默认100
	 */
	/**
	 * @cfg {String} queryMode 在哪种模式下的ComboBox使用配置的商店 默认有：remote、local、remote,默认'remote'
	 *  
	 */
	/**
	 * @cfg {String} queryParam 商店所使用的参数，通过键入字符串的当ComboBox与queryMode配置的“远程”的名称。如果明确设置为一个falsy值就不会被发送。默认为：“query”
	 */
	/**
	 * @cfg {Array} data 数据源
	 */
	/**
	 * @cfg {String} vtype 验证类型名称定义
	 */
	/**
	 * @cfg {Object} listConfig 下拉时数据配置
	 */

	/**
	 * @private
	 * @param {Object} config
	 */
	initComponent : function(config) {
		var fields = config.fields||['id','mc'];//默认为id和名称mc
		var data = config.data||[];//默认为空值
		//因model、store、proxy三者关系密切,且独立,所以这样的封装适用性不广,暂有缺陷（暂无想到更好办法）
		var store  = Ext.create('Ext.data.Store', {
			fields : fields,
			data : data
		});
		delete config.fields;
		delete config.data;
		var obj = {
				store:store,
				displayField:'mc',
				valueField:'id'
		};
		Ext.apply(obj,config);
		this.component = Ext.create('Ext.form.field.ComboBox', obj);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			data:true,
			valueField:true,
			autoSelect:true,
			allowBlank:true,
			blankText:true,
			displayField:true,
			disabled:true,
			editable:true,
			emptyText:true,
			multiSelect:true,
			pageSize:true,
			pickerAlign:true,
			pickerOffset:true,
			queryDelay:true,
			queryMode:true,
			queryParam:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			fields:true,
			vtype:true,
			listConfig:true,
			vtypeText:true
		});
	},
	/**
	 * 得到行值
	 * 
	 * @return {String/Number}
	 */
	getRawValue : function() {
		return this.component.getRawValue();
	},
	/**
	 * 设置行值（显示的是显示值，设置的是隐藏的实际值）
	 * 
	 * @param {String/Number} value一般是设置id的值
	 */
	setRawValue : function(value) {
		this.component.setRawValue(value);
	},

//	/**
//	 * 设置是否只读
//	 * @param readOnly
//	 */
//	setReadOnly:function(readOnly){
//		this.component.setReadOnly(readOnly);
//	},
    /**
     * 根据传入的条件，过滤当前结果集的数据
     * @param {String} fieldname fields中的字段名
     * @param {String} value  字段对应的值
     */
    filter : function(fieldname,value){
        this.component.store.filter(fieldname,value);
    },
    /**
     * 清空过滤条件
     */
    clearFilter : function(){
        this.component.store.clearFilter();
    },
	/**
	 * 用于queryMode为remote时的请求加载
     * @private
	 * @param data
	 */
	load:function(data){
		this.component.getStore().load(data);
	},
	/**
	 * 重新加载ComboBox的数据queryMode为'local'生效 方法同loadRawData
	 * @param {Ext.data.Model[]/Object[]} data 待加载数据
	 * @param {Boolean} append 是否在原有数据基础上添加 默认false 即加载的数据仅为data,而不包含之前的数据
	 */
	loadData:function(data,append){
		if(typeof append =='undefined') append = false;
		this.component.getStore().loadData(data,append);
	},
	loadRawData:function(data,append){
		if(typeof append =='undefined') append = false;
		this.component.getStore().loadRawData(data,append);
	},
    /**
     * 通过id获取id对应的一行数据
     * @param {String/Number} id
     * @return {Object}
     */
    getItemById : function(id){
        return this.component.getStore().getById(id).data;
    },
    /**
     * 设置是否可用
     * @param disabled
     * @returns
     */
    setDisabled:function(disabled){
    	return this.component.setDisabled(disabled);
    }
});
/**
 * @class NS.form.field.Date
 * @extends NS.form.field.BaseField
 * 日期组件
 */
NS.define('NS.form.field.Date', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {String} format 可以覆盖默认的日期格式字符串的本地化支持(Y-m-d H:i:s)。格式必须是有效的根据Ext.Date.parse。 默认是：月/日/年
	 */
	/**
	 * @cfg {Boolean} grow 是否允许自增长和收缩其内容 默认false
	 */
	/**
	 * @cfg {String} growAppend 一个字符串，将被追加到字段的当前值为计算目标字段大小的目的。仅用于配置成长是真实的。默认为一个大写的“W”（常见的字体中最宽字符），留出足够的空间，为下键入的字符，避免转移前的宽度字段值进行调整。
	 */
	/**
	 * @cfg {Number} growMax  允许自增长的最大宽度，当grow为true时生效
	 */
	/**
	 * @cfg {Number} growMin 允许自增长的最小宽度，当grow为true时生效
	 */
	/**
	 * @cfg {String} pickerAlign 对齐位置对齐选择器。默认为“TL-BL？”
	 */
	/**
	 * @cfg {String/Date} maxValue 最大允许的日期。可以是一个Javascript日期对象或一个字符串，日期格式有效。
	 */
	/**
	 * @cfg {String/Date} minValue 最小允许的日期。可以是一个Javascript日期对象或一个字符串，日期格式有效。 
	 */
	/**
	 * @private
	 * @param {Object} cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.form.field.Date', cfg);
		if(cfg.enterFn)this.doEnterFn(cfg.enterFn);
	},
//	/**
//	 * @private
//	 */
//	initEvents:function(){
//		this.callParent();
//		this.addEvents(
//				/**
//				 * @event keydown 键盘点下时触发
//				 * @param {NS.Component} this
//				 */
//				'keydown',
//				/**
//				 * @event keypress 按键输入字段事件。此事件只触发，如果enableKeyEvents设置为true。
//				 * @param {NS.Component} this
//				 */
//				'keypress',
//				/**
//				 * @event keyup 键盘键松开时触发
//				 * @param {NS.Component} this
//				 */
//				'keyup',
//				/**
//				 * @event select 数据被选中时触发
//				 * @param {NS.Component} this
//				 */
//				'select'
//				);
//	},
//	/**
//	 * @private
//	 */
//	packEvents:function(){
//		this.callParent();
//		this.component.on({
//			keydown : {
//				fn : function() {
//					this.fireEvent('keydown', this);
//				},
//				scope : this
//			},
//			keypress : {
//				fn : function() {
//					this.fireEvent('keypress', this);
//				},
//				scope : this
//			},
//			keyup : {
//				fn : function() {
//					this.fireEvent('keyup', this);
//				},
//				scope : this
//			},
//			select : {
//				fn : function() {
//					this.fireEvent('select', this);
//				},
//				scope : this
//			}
//		});
//	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			allowBlank:true,
			blankText:true,
			editable:true,
			emptyText:true,
			format:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			pickerAlign:true,
			readOnly:true,
			regex:true,
			regexText:true,
			vtype:true,
			vtypeText:true,
			maxValue:true,
            disabled:true,
			minValue:true
		});
	},
	/**
	 * 得到行值
	 * @return {String}
	 */
	getRawValue:function(){
		return this.component.getRawValue();
	},
	/**
	 * 是否有效
	 * @return {Boolean}
	 */
	isValid : function(){
		return this.component.isValid();
	},
	/**
	 * 得到这行提交值
	 * @return {String/Null}
	 */
	getSubmitValue:function(){
		return this.component.getSubmitValue();
	},
	/**
	 * 设置该组件是否可编辑
	 * @param {Boolean} editable
	 */
	setEditable:function(editable){
		this.component.setEditable(editable);
	},
	/**
	 * 设置这个filed的默认参数值
	 * @param {Object} defaults
	 */
	setFieldDefaults:function(defaults){
		this.component.setFieldDefaults(defaults);
	},
	/**
	 * 设置最大值
	 * @param {Date} value
	 */
	setMaxValue:function(value){
	   this.component.setMaxValue(value);	
	},
	/**
	 * 设置最小值
	 * @param {Date} value
	 */
	setMinValue:function(value){
		this.component.setMinValue(value);
	},
	/**
	 * @return {Ext.Component}
	 */
	getPicker:function(){
		return this.component.getPicker();
	},
    /**
     * 设置开始日期 对应组件(和vtype:'daterange'联合使用)
     * @param startField
     */
    setStartDateField : function(startField){
        this.component.startDateField = startField.getLibComponent();
    },
    /**
     * 设置结束日期对应组件(和vtype:'daterange'联合使用)
     * @param endField
     */
    setEndDateField : function(endField){
        this.component.endDateField = endField.getLibComponent();
    },
    /**
     * 设置是否可用
     * @param disabled
     * @returns
     */
    setDisabled:function(disabled){
    	return this.component.setDisabled(disabled);
    }
});/**
 * @class NS.form.field.Display
 * @extends NS.form.field.BaseField
 * 仅作为值显示的组件
 */
NS.define('NS.form.field.Display', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} htmlEncode 当setfalse跳过HTML编码文本渲染它时。这可能是有用的，如果你要包括在该领域的innerHTML的标签，而不是使他们作为默认逻辑的字符串.
	 */
	/**
	 * @cfg {Number} saveDelay 延迟加载 默认100
	 */
	/**
	 * @cfg {Boolean} validateOnBlur 该字段是否应向验证失去焦点时。这将导致作为用户验证步骤通过表格中的字段的字段，而不管它们是否正在改变前进的道路上的那些字段。另见validateOnChange 默认true
	 */
	/**
	 * @cfg {Boolean} validateOnChange 该字段是否应向验证whSpecifies，这一领域是否应立即进行验证时，发现其价值的变化。如果验证结果字段的有效性的变化，一个validitychange事件将被解雇。这允许字段用户键入立即显示其内容的有效性的反馈意见。当设置为false，反馈不会立竿见影。但形式仍然会被验证，然后再提交如果的clientValidation的Ext.form.Basic.doAction选项被启用，如果字段或形式手动验证。默认true
	 */
	/**
	 * @private
	 * @param config
	 */
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.field.Display', config);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			disabled:true,
			htmlEncode:true,
			readOnly:true,
			saveDelay:true,
			validateOnBlur:true,
			validateOnChange:true
		});
	}
});
/**
 * @class NS.form.field.File
 * @extends NS.form.field.BaseField
 * 文件上传组件
 */
NS.define('NS.form.field.File', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} grow 是否允许自增长和收缩其内容 默认false
	 */
	/**
	 * @cfg {String} growAppend 一个字符串，将被追加到字段的当前值为计算目标字段大小的目的。仅用于配置成长是真实的。默认为一个大写的“W”（常见的字体中最宽字符），留出足够的空间，为下键入的字符，避免转移前的宽度字段值进行调整。
	 */
	/**
	 * @cfg {Number} growMax 允许自增长的最大宽度，当grow为true时生效
	 */
	/**
	 * @cfg {Number} growMin 允许自增长的最小宽度，当grow为true时生效
	 */
	/**
	 * @private
	 * @param {Object} cfg
	 */
	initComponent : function(cfg) {
		//var callBack = cfg.callBack;
		delete cfg.callBack;
		
		var basic = {
				buttonText:'上传'
		};
		Ext.apply(basic,cfg);
		this.component = Ext.create('Ext.form.field.File', basic);
//		this.component.addListener('change',function(file,value,eOpts){
//			var name = file.buttonText;
//			Ext.Msg.confirm('提示','确定'+name+'文件:&nbsp;<b>'+file.getValue()+'</b>&nbsp;吗？',function(btn){
//				if(typeof callBack != 'undefined'){
//					callBack(btn);
//				}else{
//					//系统默认提供的处理
////					alert(btn+'系统未做默认处理！');					
//				}
//			});
//		});
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			callBack:true,
			allowBlank:true,
			blankText:true,
			buttonOnly:true,
			buttonText:true,
            buttonConfig : true,
			editable:true,
			emptyText:true,
			disabled:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			vtype:true,
			vtypeText:true
		});
	},
//	/**
//	 * @private
//	 */
//	initEvents:function(){
//		this.callParent();
//		this.addEvents(
//				/**
//				 * @event keydown 键盘点下时触发
//				 * @param {NS.Component} this
//				 */
//				'keydown',
//				/**
//				 * @event keypress 按键输入字段事件。此事件只触发，如果enableKeyEvents设置为true。
//				 * @param {NS.Component} this
//				 */
//				'keypress',
//				/**
//				 * @event keyup 键盘键松开时触发
//				 * @param {NS.Component} this
//				 */
//				'keyup'
//				);
//	},
//	/**
//	 * @private
//	 */
//	packEvents:function(){
//		this.callParent();
//		this.component.on({
//			keydown : {
//				fn : function() {
//					this.fireEvent('keydown', this);
//				},
//				scope : this
//			},
//			keypress : {
//				fn : function() {
//					this.fireEvent('keypress', this);
//				},
//				scope : this
//			},
//			keyup : {
//				fn : function() {
//					this.fireEvent('keyup', this);
//				},
//				scope : this
//			}
//		});
//	},
	/**
	 * 返回值（s）应该保存到的Ext.data.Model实例的这一领域，
	 * 被称为Ext.form.Basic.updateRecord时。
	 * 通常情况下，这将是一个单一的名称 - 值对这个字段的名字，
	 * 这个名字和值是其当前的数据值的对象。
	 * 更先进的领域实现可能返回多个名称 - 值对。
	 * 返回的值将被保存在模型中的相应的字段名称。
     * 请注意，这个方法返回的值都不能保证成功验证。
     * @return {Object} 提交的映射值的参数名称，如果那个特定的名称有多个值，
     * 每个值应该是一个字符串，或者一个字符串数组。
     * 如果没有要提交的参数，它也可以返回null。
	 */
	getModelData:function(){
		return this.component.getModelData();
	},
	/**
	 * 设置此组件在其父面板的停靠位置
	 * @param {Object} dock 定位位置
	 * @param {Boolean} layoutParent True为重新布局父类 默认false
	 * @return {Ext.Component} this
	 */
	setDocked:function(dock,layoutParent){
		return this.component.setDocked();
	}
});/**
 * @class NS.form.field.TextArea
 * @extends NS.form.field.BaseField 文字域组件
 */
NS.define('NS.form.field.TextArea', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} grow 是否允许自增长和收缩其内容 默认false
	 */
	/**
	 * @cfg {String} growAppend 一个字符串，将被追加到字段的当前值为计算目标字段大小的目的。仅用于配置成长是真实的。默认为一个大写的“W”（常见的字体中最宽字符），留出足够的空间，为下键入的字符，避免转移前的宽度字段值进行调整。
	 */
	/**
	 * @cfg {Number} growMax  允许自增长的最大宽度，当grow为true时生效
	 */
	/**
	 * @cfg {Number} growMin 允许自增长的最小宽度，当grow为true时生效
	 */
	/**
	 * @private
	 * @param cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.form.field.TextArea', cfg);
		//if(cfg.enterFn)this.doEnterFn(cfg.enterFn);
		//文本域暂不添加回车事件,因为它本身的回车应该是换行,如有需求会稍做更改，比如Ctrl+Enter的的形式
	},
	/**
	 * @private
	 */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			allowBlank:true,
			blankText:true,
			emptyText:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			vtype:true,
			vtypeText:true
		});
	},
	/**
	 * 中心在其容器组件(Center this Component in its container.)
	 * 
	 * @return {Ext.Component} this
	 */
	center : function() {
		return this.component.center();
	},
	/**
	 * 启用组件
	 * 
	 * @param {Boolean}
	 *            silent 默认false
	 */
	enable : function(silent) {
		this.component.enable(silent);
	},
	/**
	 * 设置这个filed的默认参数值
	 * 
	 * @param {Object}
	 *            defaults
	 */
	setFieldDefaults : function(defaults) {
		this.component.setFieldDefaults(defaults);
	},
	/**
	 * 隐藏该组件
	 * 
	 * @param {String/Ext.Element/Ext.Component}
	 *            animateTarget
	 * @param {Function}
	 *            callback
	 * @param {Object}
	 *            scope
	 */
	hide : function(animateTarget, callback, scope) {
		this.component.hide(animateTarget, callback, scope);
	},
	/**
	 * 重置（还原）该组件的值
	 */
	reset : function() {
		this.component.reset();
	}
// initValue、initConfig...等几个应该有用,不过暂不加入了.
});
/**
 * @class NS.form.field.Radio
 * @extends NS.form.field.BaseField 单选按钮组件
 */
NS.define('NS.form.field.Radio', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {} boxLabel 复选框标签名
	 */
	/**
	 * @cfg {} inputValue 复选框实际值
	 */
	/**
	 * @cfg {Boolean} checked 是否被选中 默认false
	 */
	/**
	 * @cfg {} uncheckedValue 如果配置的话，这将被提交复选框的值如果复选框是选中的，在形式提交。默认情况下，这是不确定的，从而导致没有提交提交表单时（HTML复选框的默认行为）复选框字段。
	 */
	/**
	 * @cfg {} validateOnBlur validateOnBlur 该字段是否应向验证失去焦点时。这将导致作为用户验证步骤通过表格中的字段的字段，而不管它们是否正在改变前进的道路上的那些字段。另见validateOnChange 默认true
	 */
	/**
	 * @cfg {} validateOnChange 该字段是否应向验证whSpecifies，这一领域是否应立即进行验证时，发现其价值的变化。如果验证结果字段的有效性的变化，一个validitychange事件将被解雇。这允许字段用户键入立即显示其内容的有效性的反馈意见。当设置为false，反馈不会立竿见影。但形式仍然会被验证，然后再提交如果的clientValidation的Ext.form.Basic.doAction选项被启用，如果字段或形式手动验证。默认true
	 */
	/**
	 * @cfg {} handler 调用的函数时，选中的值的变化（可以用来代替处理的变化事件）。包含两个属性checkbox 当前复选框 checked 新选中的复选框
	 */
	/**
	 * @private
	 * @param config
	 */
	initComponent : function(config) {
		this.component = Ext.create('Ext.form.field.Radio', config);
	},
	/**
	 * @private
	 */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			boxLabel:true,
			inputValue:true,
			checked:true,
			disabled:true,
			readOnly:true,
			uncheckedValue:true,
			validateOnBlur:true,
			validateOnChange:true,
			handler:true,
			invalidText:true
		});
	},
	/**
	 * 如果该组件是单选组队一部分，则返回被选中的值
	 * 
	 * @return {String}
	 */
	getGroupValue : function() {
		return this.component.getGroupValue();
	},
	/**
	 * 重置该组件的值
	 */
	reset : function() {
		this.component.reset();
	}
});
/**
 * @class NS.form.field.Number
 * @extends NS.form.field.BaseField
 * 数字组件
 */
NS.define('NS.form.field.Number', {
	extend : 'NS.form.field.BaseField',
	/**
	 * @cfg {Boolean} hideTrigger 隐藏filed的 增长和减少键
	 */
	/**
	 * @cfg {Boolean} grow 是否允许自增长和收缩其内容 默认false
	 */
	/**
	 * @cfg {String} growAppend 一个字符串，将被追加到字段的当前值为计算目标字段大小的目的。仅用于配置成长是真实的。默认为一个大写的“W”（常见的字体中最宽字符），留出足够的空间，为下键入的字符，避免转移前的宽度字段值进行调整。
	 */
	/**
	 * @cfg {Number} growMax 允许自增长的最大宽度，当grow为true时生效
	 */
	/**
	 * @cfg {Number} growMin 允许自增长的最小宽度，当grow为true时生效 
	 */
	/**
	 * @cfg {String} maxText 最大值验证失败时的文本信息。
	 */
	/**
	 * @cfg {Number} maxValue 最大值
	 */
	/**
	 * @cfg {String} minText 最小值验证失败时的文本信息
	 */
	/**
	 * @cfg {Number} minValue 最小值
	 */
	/**
	 * @cfg {String} nanText 错误文本显示，如果该值是不是一个有效的数字。例如，这可能发生，如果一个有效的字符，如'。'或' - '留在现场，没有数。
	 */
	/**
	 * @cfg {String} negativeText 错误要显示的文本，如果该值是负数和minValue（最小值）被设置为0。这是用，而不是仅在该情况下的minText。
	 */
	/**
	 * @cfg {Boolean} readOnly 是否只读 默认false
	 */
	/**
	 * @cfg {RegExp} regex 正则表达式
	 */
	/**
	 * @cfg {String} regexText 正则表达式错误显示信息
	 */
	/**
	 * @cfg {Number} size 在文字输入元素的'大小'属性的初始值。这是仅用于域没有配置的宽度并没有给出其容器的布局宽度。默认为20。
	 */
	/**
	 * @cfg {Number} step 指定一个数值字段的值将递增或递减当用户调用微调的时间间隔。
	 */
	/**
	 * @cfg {String} vtype 验证类型名称定义
	 */
	/**
	 * @cfg {String} vtypeText vtypeText 自定义错误消息显示Vtype域提供的消息，目前此字段设置的默认到位。注：仅适用于如果Vtype域设置，否则忽略。
	 */
	/**
	 * @private
	 * @param cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.form.field.Number', cfg);
		if(cfg.enterFn)this.doEnterFn(cfg.enterFn);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			hideTrigger:true,
			allowBlank:true,
			blankText:true,
			editable:true,
			emptyText:true,
			disabled:true,
			grow:true,
			growAppend:true,
			growMax:true,
			growMin:true,
			maxText:true,
			maxValue:true,
			minText:true,
			minValue:true,
			nanText:true,
			negativeText:true,
			readOnly:true,
			regex:true,
			regexText:true,
			size:true,
			step:true,
			vtype:true,
			vtypeText:true
		});
	},
	/**
	 * 返回两者是否相等
	 * 
	 * @param {Object} v1
	 * @param {Object} v2
	 * @return {Boolean}
	 */
	isEqual : function(v1, v2) {
		return this.component.isEqual(v1, v2);
	},
	/**
	 * 重置（还原）该组件的值
	 */
	reset:function(){
		this.component.reset();
	},
    /**
     * 设置该组件的最小值。
     * @param number
     */
    setMinValue : function(number){
    this.component.setMinValue(number);
    },
    /**
     * 设置该组件的最大值。
     * @param number
     */
    setMaxValue: function(number){
        this.component.setMaxValue(number);
    }
});/**
 * @class NS.form.field.Time
 * @extends NS.form.field.BaseField
 */
NS.define('NS.form.field.Time',{
    extend : 'NS.form.field.BaseField',
    initComponent : function(config){
        var basic = {
            format :"G时i分",
            submitFormat:"G:i"
        };
        NS.apply(basic,config);
        this.component = Ext.create('Ext.form.field.Time',basic);
    },
    setMinValue:function(value){
        this.component.setMinValue(value);
    },
    setMaxValue:function(value){
        this.component.setMaxValue(value);
    }
});/**
 * @class NS.form.field.ComboBoxTree
 * @extends NS.form.field.BaseField
 * 下拉树
 */
NS.define('NS.form.field.ComboBoxTree', {
	extend : 'NS.form.field.BaseField',

	/**
	 * @cfg {Number} width 组件宽度 默认210
	 */
	/**
	 * @cfg {Number} height 下拉树形的高度 默认300
	 */
	/**
	 * @cfg {Boolean} expanded 是否展开  默认false
	 */
	/**
	 * @cfg {Boolean} rootVisible 是否使用根节点 默认false
	 */
	/**
	 * @cfg {String} rootName 根节点名称
	 */
	/**
	 * @cfg {String} fieldLabel 下拉树文本标签名
	 */
	/**
	 * @cfg {Boolean} isLeafSelect 是否允许选择叶子节点
	 */
	/**
	 * @cfg {String} notLeafInfo 不是子节点信息时，使用者点击父节点时触发此条信息
	 */
	/**
	 * @cfg {String} notParentInfo  不是父节点信息时使用者点击子节点时触发此条信息
	 */
	/**
	 * @cfg {Boolean} isParentSelect 是否允许选择父节点
	 */
	/**
	 * @cfg {Boolean} autoScroll 是否自动显示滚动条 默认true
	 */
	/**
	 * @cfg {Array} treeData 树形数据 不包含根节点数据
	 */
	/**
	 * @cfg {Number} pickerWidth 下拉出树形picker的宽度 默认与原生保持一致:width - lableWidth - 2||0
	 */

	/**
	 * @private
	 */
	initEvents : function() {
		this.callParent();
	},
    onSelect : function(){
        this.component.on('treeselect',function(records){
        	//console.log(records);
            this.fireEvent('select', this,records.raw,records);
        },this);
    },
	/**
	 * @private
	 * @param cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.ux.comboBoxTree', cfg);
	},
	/**
	 * @private
	 */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			width:true,
			fieldLabel:true,
			editable:true,
			expanded:true,
			editable:true,
			height:true,
			treeData:true,
			pickerWidth:true,
			rootVisible:true,
			isLeafSelect:true,
			isParentSelect:true,
			rootName:true,
			emptyText:true,
			isLeafSelect : true,
			isParentSelect:true,
			notParentInfo:true,
			notLeafInfo : true
		});
	},
	/**
	 * 设置值
	 * 
	 * @param {Array/Number/String} id 这个值存在的话，显示对应的显示值，不存在的话显示为空
	 */
	setValue : function(id) {
		this.component.setValue(id);
	},
	/**
	 * 得到下拉树的实际值
	 * 
	 * @return {Object}
	 */
	getValue : function() {
		return this.component.getValue();
	},
    /**
     * 设置下拉树的显示值
     * @param {Object} value
     */
	setRawValue:function(value){
		this.component.setRawValue(value);
	},
	/**
	 * 得到下拉树的显示值
	 * 
	 * @return {String}
	 */
	getRawValue : function() {
		return this.component.getRawValue();
	},
    onChange : function(){
        //console.log(this.getValue()+"----"+this.getRawValue());
    },
	/**
	 * 重置下拉树的显示值
	 */
	reset : function(){
		this.component.reset();
	},
	/**
	 * 重新加载下拉树的数据
	 * @param {Object} data 重新加载的下拉树的数据
	 */
	loadData : function(data){
		this.component.reset();
		this.component.loadData(data);
	},
	/**
	 * 得到当前node的model对象集合值
	 * @param value
	 */
	getCurrentNodeRaw:function(value){
		return this.component.getCurrentNodeRaw(value);
	}
});
/**
 * @class NS.form.field.ItemSelector
 * @extend NS.form.field.BaseField
 */
NS.define('NS.form.field.ItemSelector', {
    extend: 'NS.form.field.BaseField',
    /**
     * @cfg {Object}  data 数据
     */
    /**
     * @cfg {String}  displayField 数据
     */
    /**
     * @cfg {String}  valueField 数据
     */
    initComponent: function (config) {
        this.processConfig(config);
        this.component = Ext.create('Ext.ux.form.ItemSelector', config);
    },
    /**
     * 处理组件配置项
     * @param {Object} config
     */
    processConfig: function (config) {
        var data = this.backData = config.data;
        var store = new Ext.data.Store({
            fields: config.fields||['id', 'mc'],
            data: data
        });
        NS.applyIf(config, {
            store: store,
            displayField: 'mc',
            valueField: 'id',
            imagePath: '../ux/images/'
        });
        delete config.data;
    },
    /**
     * @private
     * 初始化参数配置mapping映射关系
     */
    initConfigMapping : function () {
        this.callParent();
        this.addConfigMapping({
            fromTitle: true,
            title : true,
            toTitle: true
        });
    },
    /**
     * 把传入的id从可选项中移除
     * @param id
     */
    removeOptionsByIds : function(id){
        var com = this.component,
            fromStore = com.fromField.getStore(),
            toStore = com.toField.getStore(),
            process = function(id){
            for(var i= 0,len=id.length;i<len;i++){
                var record = fromStore.findRecord(com.valueField,id[[i]])
                fromStore.remove(record);
            }
        }
        if(NS.isArray(id)){
            process(id);
        }else if(NS.isString(id)){
            process(id.split(","));
        }else if(NS.isNumber(id)){
            process([id.toString()]);
        }
        com.setValue();
    },
    /**
     * 把id对应的数据添加到可选项中
     */
    addOptionsWithIds : function(id){
        var data = this.backData,nowdata,com = this.component,
            fromStore = com.fromField.getStore(),
            toStore = com.toField.getStore(),
            removeRecords = fromStore.getRemovedRecords(),
            process = function(ids){
                for(var i= 0,len=ids.length;i<len;i++){
                    var id = ids[i];
                    for(var k=0;k<removeRecords.length;k++){
                        if(id == removeRecords[k].get('id')){
                           fromStore.add(removeRecords[k]);
                        }
                    }
                }
            };
        if(NS.isArray(id)){
            process(id);
        }else if(NS.isString(id)){
            process(id.split(","));
        }else if(NS.isNumber(id)){
            process([id.toString()]);
        }
        com.setValue();
    },
    /**
     * 获取组件的显示值
     */
    getRawValue : function(){
        var array = this.component.getValue(),
                      toStore = this.component.toField.getStore(),item,id;
        var rawvalue = [];
        for(var i= 0;i<array.length;i++){
            id = array[i];
            item = toStore.findRecord(this.component.valueField,id);
            rawvalue.push(item.get(this.component.displayField));
        }
        return rawvalue;
    },
    /**
     * 重新加载ItemSelect的可选项
     */
    loadData : function(data){
        var com = this.component,
            fromStore = com.fromField.getStore(),
            toStore = com.toField.getStore();
        com.getStore().removeAll();
        com.getStore().loadData(data);
        fromStore.removeAll();
        toStore.removeAll();
        fromStore.add(com.getStore().getRange());
        com.syncValue();
    }
});NS.define('NS.form.field.CheckboxGroup',{
    extend : 'NS.form.field.BaseField',
    /**
     * @cfg {Number} columns 你可以设置让checkbox几列显示
     */
    /**
     * @cfg {Boolean} vertical 控制checkboxgroup的显示方式(true为纵向，false为横向)，默认为false
     */
    /**
     * @cfg {Array} items  checkboxgroup子项
     *  var checkbox = new NS.form.field.CheckboxGroup({
             fieldLabel: '两列',
             columns: 2,
             vertical: true,
             items: [
             { boxLabel: 'Item 1', name: 'rb', inputValue: '1' },
             { boxLabel: 'Item 2', name: 'rb', inputValue: '2', checked: true },
             { boxLabel: 'Item 3', name: 'rb', inputValue: '3' },
             { boxLabel: 'Item 4', name: 'rb', inputValue: '4' },
             { boxLabel: 'Item 5', name: 'rb', inputValue: '5' },
             { boxLabel: 'Item 6', name: 'rb', inputValue: '6' }
             ]
     *  });
     */
    initComponent : function(config){
        this.component = Ext.create('Ext.form.CheckboxGroup',config);
    },
    /**
     * @private
     * 初始化参数配置mapping映射关系
     */
    initConfigMapping:function(){
        this.callParent();
        var processEditable = function(value,property,config){
            config.readOnly = Boolean(!value)
            if(!value){
                config.fieldStyle = "background:#E6E6E6;";
            }
        }
        this.addConfigMapping({
            items : true,
            columns : true,//几列分布显示
            vertical : true
        });
    },
    /**
     * 设置checkboxgroup的值
     * var myCheckboxGroup = new Ext.form.CheckboxGroup({
         columns: 3,
         items: [{
             name: 'cb1',
             boxLabel: 'Single 1'
             }, {
             name: 'cb2',
             boxLabel: 'Single 2'
             }, {
             name: 'cb3',
             boxLabel: 'Single 3'
             }, {
             name: 'cbGroup',
             boxLabel: 'Grouped 1'
             inputValue: 'value1'
             }, {
             name: 'cbGroup',
             boxLabel: 'Grouped 2'
             inputValue: 'value2'
             }, {
             name: 'cbGroup',
             boxLabel: 'Grouped 3'
             inputValue: 'value3'
             }]
         });

         myCheckboxGroup.setValue({
             cb1: true,//cb1--->被选中
             cb3: false,//cb3--->未被选中
             cbGroup: ['value1', 'value3']//名字为cbGroup的前2个值为value1 和value3
         });
     */
    setValue : function(){
        this.component.setValue.apply(this.component,arguments);
    },
    /**
     * 重置 
     */
    reset : function(){
        this.component.reset();
    }
});NS.define('NS.form.field.RadioGroup',{
    extend : 'NS.form.field.CheckboxGroup',
    /**
     * @cfg {Number} columns 你可以设置让checkbox几列显示
     */
    /**
     * @cfg {Boolean} vertical 控制checkboxgroup的显示方式(true为纵向，false为横向)，默认为false
     */
    /**
     * @cfg {Array} items  checkboxgroup子项
     *  var checkbox = new NS.form.field.CheckboxGroup({
     fieldLabel: '两列',
     columns: 2,
     vertical: true,
     items: [
     { boxLabel: 'Item 1', name: 'rb', inputValue: '1' },
     { boxLabel: 'Item 2', name: 'rb', inputValue: '2', checked: true },
     { boxLabel: 'Item 3', name: 'rb', inputValue: '3' },
     { boxLabel: 'Item 4', name: 'rb', inputValue: '4' },
     { boxLabel: 'Item 5', name: 'rb', inputValue: '5' },
     { boxLabel: 'Item 6', name: 'rb', inputValue: '6' }
     ]
     *  });
     */
    initComponent : function(config){
        this.component = Ext.create('Ext.form.RadioGroup',config);
    },
    /**
     * @private
     * 初始化参数配置mapping映射关系
     */
    initConfigMapping:function(){
        this.callParent();
        var processEditable = function(value,property,config){
            config.readOnly = Boolean(!value)
            if(!value){
                config.fieldStyle = "background:#E6E6E6;";
            }
        }
        this.addConfigMapping({
            items : true,
            columns : true,//几列分布显示
            vertical : true
        });
    },
    /**
     * 设置checkboxgroup的值
     * var myCheckboxGroup = new Ext.form.CheckboxGroup({
     columns: 3,
     items: [{
     name: 'cb1',
     boxLabel: 'Single 1'
     }, {
     name: 'cb2',
     boxLabel: 'Single 2'
     }, {
     name: 'cb3',
     boxLabel: 'Single 3'
     }, {
     name: 'cbGroup',
     boxLabel: 'Grouped 1'
     inputValue: 'value1'
     }, {
     name: 'cbGroup',
     boxLabel: 'Grouped 2'
     inputValue: 'value2'
     }, {
     name: 'cbGroup',
     boxLabel: 'Grouped 3'
     inputValue: 'value3'
     }]
     });

     myCheckboxGroup.setValue({
     cb1: true,//cb1--->被选中
     cb3: false,//cb3--->未被选中
     cbGroup: ['value1', 'value3']//名字为cbGroup的前2个值为value1 和value3
     });
     */
    setValue : function(){
        this.component.setValue.apply(this.component,arguments);
    }
});/**
 * @class NS.form.field.HtmlEditor
 * @extends NS.Component field的基类
 */
NS.define('NS.form.field.HtmlEditor', {
	extend : 'NS.form.field.BaseField',
	
	initComponent:function(config){
		this.component = Ext.create('Ext.form.HtmlEditor',config);
//		if(config.enterFn) this.doEnterFn(config.enterFn);
	},
	
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			
			frame : true
		});
	}
	
	
})/**
 * @class NS.form.field.ForumSearch
 * @extends NS.form.field.ComboBox
 * 查询下拉组件 
 * 
 */
NS.define('NS.form.field.ForumSearch',{
	extend:'NS.form.field.ComboBox',
	/**
	 * @cfg {String} name 组件名称
	 */
	/**
	 * @cfg {String} queryParam 查询的参数名
	 */
	/**
	 * @cfg {Boolean} readOnly 是否只读 默认false
	 */
	/**
	 * @cfg {String} fieldLabel 组件显示标签名称
	 */
	/**
	 * @cfg {Boolean} editable 是否可编辑 默认true 
	 */
	/**
	 * @cfg {Number} minChars 最小查询字符数  默认4
	 */
	/**
	 * @cfg {String} displayField 显示值域名称
	 */
	/**
	 * @cfg {String} valueField 实际值域值
	 */
	/**
	 * @cfg {String} emptyText 组件值为空是显示的文本
	 */
	/**
	 * @cfg {String} labelAlign 标签的位置left center right 默认 left
	 */
	/**
	 * @cfg {Boolean} allowBlank 是否允许为空 默认false
	 */
	/**
	 * @cfg {Boolean} typeAhead true其余的文字键入一个可配置的的延迟（typeAheadDelay,默认250）后，它是否符合一个已知值来填充和自选。默认true
	 */
	/**
	 * @cfg {Boolean} hideTrigger 隐藏下拉图标 默认true
	 */
	/**
	 * @cfg {Number} labelWidth 标签长度 默认70
	 */
	/**
	 * @cfg {Number} width 组件宽度 默认280
	 */
	/**
	 * @cfg {Object} listConfig 下拉列表配置项
	 */
	/**
	 * @cfg {Number} pageSize 分页条数 默认10
	 */
	/**
	 * @cfg {String} url 链接路径
	 */
	/**
	 * @cfg {Array} fields 域配置属性 name:'',mapping:'',type:'',format:'',以及其他配置信息
	 */
	/**
	 * @cfg {Function} getInnerTpl 得到下拉列表的内部配置模板
	 */
	/**
	 * @cfg {Object} service 服务配置
	 */
	/**
	 * @cfg {String} valueNotFoundText  
	 */
	/**
	 * @cfg {Number} queryDelay 默认500,当mode为remote/local时
	 */
	/**
	 * @private
	 */
	initComponent:function(cfg){
		this.component = Ext.create('Ext.form.field.ForumSearch',cfg);
	},
	getValue:function(){
		return this.component.getValue();
	},
	getRawValue:function(){
		return this.component.getRawValue();
	},
	getSubmitValue:function(){
		return this.component.getValue();
	},
	//setValue不建议使用,因为初始时根本没有值,所以setValue(id),该组件的值会变更为id,而非对应的mc
	setValue:function(id){
		this.component.setValue(id);
	},
	//loadData方法可能无法正常使用,因为queryMode已经为remote才能这么做.而该方法需要为local
	loadData:function(data){
		this.component.loadData(data);
	},
    /**
     * 初始化事件
     * @private
     */
    initEvents: function () {
        this.callParent();
        this.addEvents(
            /***
             * @event beforeload 加载数据之前
             */
            'beforeload'
        );
    },
    onBeforeload:function(){
        this.component.on('beforeload',function(params,opts){
            return this.fireEvent('beforeload', params);
        },this);
    },
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			service:true,
			name:true,
			queryParam:true,
			readOnly:true,
			fieldLabel:true,
			editable:true,
			minChars:true,
			displayField:true,
			valueField:true,
			emptyText:true,
			labelAlign:true,
			allowBlank:true,
			typeAhead:true,
			hideTrigger:true,
			labelWidth:true,
			width:true,
			valueNotFoundText:true,
			listConfig:true,
			pageSize:true,
			url:true,
			queryDelay:true,
			fields:true,
			getInnerTpl:true
		});
	},
    setQueryParams : function(params){
        this.component.setQueryParams(params);
    }
});

/**\
 * 日期组件，可以精确到时分秒
 * var datetime = new Ext.form.field.DateTime({
         format : 'Y-m-d H:i:s',
         width : 300,
         labelWidth:60,
         fieldLabel : '发送时间'
     });
 */
NS.define('NS.form.field.DateTime',{
    extend : 'NS.form.field.Date',
    initComponent : function(config){
        this.component = new  Ext.form.field.DateTime(config);
    }
});NS.define('NS.form.field.DatePicker',{
    extend : 'NS.form.field.BaseField',
    /**
     * @cfg {String} format 可以覆盖默认的日期格式字符串的本地化支持(Y-m-d H:i:s)。格式必须是有效的根据Ext.Date.parse。 默认是：Y-m-d
     */
    /**
     * @cfg {String/Date} maxValue 最大允许的日期。可以是一个Javascript日期对象或一个字符串，日期格式有效。
     */
    /**
     * @cfg {String/Date} minValue 最小允许的日期。可以是一个Javascript日期对象或一个字符串，日期格式有效。
     */
    initComponent : function(cfg){
    	var value = cfg.value;
    	delete cfg.value;
        this.component = Ext.create('Ext.picker.Date', cfg);
        if(value)this.setValue(value);
    },
    /**
     * @private
     */
    initConfigMapping:function(){
        this.callParent();
        this.addConfigMapping({
            format:true,
            maxValue:function(value,property,config){
                config['maxDate'] = value;
                delete config.maxValue;
            },
            minValue:function(value,property,config){
                config['minDate'] = value;
                delete config.minValue;
            }
        });
    },
    /**
     * 获取Picker的值
     * @returns {String|*}
     */
    getValue : function(){
        return Ext.Date.format(this.component.getValue(),this.component.format || 'Y-m-d');
    },
    /**
     * 设置picker的值
     * @param v {String}  设置的picker的值
     */
    setValue : function(v){
        if(NS.isDate(v)){
            this.component.setValue(v);
        }else if(NS.isString(v)){
            this.component.setValue(Ext.Date.parse(v,this.component.format||'Y-m-d'));
        }
    }
});/**
 * 时间输入框, 三个整数框分别输入时,分,秒.
 * @author wangzilong
 * @private
 * update Ext - 4.1 2012/04/27
 */
Ext.define('Ext.form.field.TimePicker', {
    extend: 'Ext.form.field.Base',
    alias: 'widget.timepicker',
    alternateClassName: 'Ext.form.field.TimePickerField',
    requires: ['Ext.form.field.Number'],
    // 隐藏BaseField的输入框 , hidden basefield's input
    inputType: 'hidden',
    style: 'padding:4px 0 0 0;margin-bottom:0px',
    /**
     * @cfg {String} value
     * initValue, format: 'H:i:s'
     */
    value: null,
    /**
     * @cfg {Object} spinnerCfg
     * 数字输入框参数, number input config
     */
    spinnerCfg: {
        width: 40
    },
    /** Override. */
    initComponent: function() {
        var me = this;
        me.value = me.value || Ext.Date.format(new Date(), 'H:i:s');
        me.callParent();// called setValue
        me.spinners = [];
        var cfg = Ext.apply({}, me.spinnerCfg, {
            readOnly: me.readOnly,
            disabled: me.disabled,
            style: 'float: left',
            listeners: {
                change: {
                    fn: me.onSpinnerChange,
                    scope: me
                }
            }
        });
        me.hoursSpinner = Ext.create('Ext.form.field.Number', Ext.apply({}, cfg, {
            minValue: 0,
            maxValue: 23
        }));
        me.minutesSpinner = Ext.create('Ext.form.field.Number', Ext.apply({}, cfg, {
            minValue: 0,
            maxValue: 59
        }));
        // TODO 使用timeformat 判断是否创建秒输入框, maybe second field is not always need.
        me.secondsSpinner = Ext.create('Ext.form.field.Number', Ext.apply({}, cfg, {
            minValue: 0,
            maxValue: 59
        }));
        me.spinners.push(me.hoursSpinner, me.minutesSpinner, me.secondsSpinner);
    },
    /**
     * @private
     * Override.
     */
    onRender: function() {
        var me = this, spinnerWrapDom, spinnerWrap;
        me.callParent(arguments);
        // render to original BaseField input td
        // spinnerWrap = Ext.get(Ext.DomQuery.selectNode('div', this.el.dom)); // 4.0.2
        spinnerWrapDom = Ext.dom.Query.select('td', this.getEl().dom)[1]; // 4.0 ->4.1 div->td
        spinnerWrap = Ext.get(spinnerWrapDom);
        me.callSpinnersFunction('render', spinnerWrap);
        Ext.core.DomHelper.append(spinnerWrap, {
            tag: 'div',
            cls: 'x-form-clear-left'
        });
        this.setRawValue(this.value);
    },
    _valueSplit: function(v) {
        if(Ext.isDate(v)) {
            v = Ext.Date.format(v, 'H:i:s');
        }
        var split = v.split(':');
        return {
            h: split.length > 0 ? split[0] : 0,
            m: split.length > 1 ? split[1] : 0,
            s: split.length > 2 ? split[2] : 0
        };
    },
    onSpinnerChange: function() {
        if(!this.rendered) {
            return;
        }
        this.fireEvent('change', this, this.getValue(), this.getRawValue());
    },
    // 依次调用各输入框函数, call each spinner's function
    callSpinnersFunction: function(funName, args) {
        for(var i = 0; i < this.spinners.length; i++) {
            this.spinners[i][funName](args);
        }
    },
    // @private get time as object,
    getRawValue: function() {
        if(!this.rendered) {
            var date = this.value || new Date();
            return this._valueSplit(date);
        } else {
            return {
                h: this.hoursSpinner.getValue(),
                m: this.minutesSpinner.getValue(),
                s: this.secondsSpinner.getValue()
            };
        }
    },
    // private
    setRawValue: function(value) {
        value = this._valueSplit(value);
        if(this.hoursSpinner) {
            this.hoursSpinner.setValue(value.h);
            this.minutesSpinner.setValue(value.m);
            this.secondsSpinner.setValue(value.s);
        }
    },
    // overwrite
    getValue: function() {
        var v = this.getRawValue();
        return Ext.String.leftPad(v.h, 2, '0') + ':' + Ext.String.leftPad(v.m, 2, '0') + ':'
            + Ext.String.leftPad(v.s, 2, '0');
    },
    // overwrite
    setValue: function(value) {
        this.value = Ext.isDate(value) ? Ext.Date.format(value, 'H:i:s') : value;
        if(!this.rendered) {
            return;
        }
        this.setRawValue(this.value);
        this.validate();
    },
    // overwrite
    disable: function() {
        this.callParent(arguments);
        this.callSpinnersFunction('disable', arguments);
    },
    // overwrite
    enable: function() {
        this.callParent(arguments);
        this.callSpinnersFunction('enable', arguments);
    },
    // overwrite
    setReadOnly: function() {
        this.callParent(arguments);
        this.callSpinnersFunction('setReadOnly', arguments);
    },
    // overwrite
    clearInvalid: function() {
        this.callParent(arguments);
        this.callSpinnersFunction('clearInvalid', arguments);
    },
    // overwrite
    isValid: function(preventMark) {
        return this.hoursSpinner.isValid(preventMark) && this.minutesSpinner.isValid(preventMark)
            && this.secondsSpinner.isValid(preventMark);
    },
    // overwrite
    validate: function() {
        return this.hoursSpinner.validate() && this.minutesSpinner.validate() && this.secondsSpinner.validate();
    }
});/**
 * @class Ext.form.field.DateTimePicker
 * @private
 */
Ext.define('Ext.form.field.DateTimePicker', {
    extend: 'Ext.picker.Date',
    alias: 'widget.datetimepicker',
    todayText: '确定',
    timeLabel: '时间',
    initComponent: function() {
        // keep time part for value
        var value = this.value || new Date();
        this.callParent();
        this.value = value;
    },
    onRender: function(container, position) {
        if(!this.timefield) {
            this.timefield = Ext.create('Ext.form.field.TimePicker', {
                fieldLabel: this.timeLabel,
                labelWidth: 40,
                value: Ext.Date.format(this.value, 'H:i:s')
            });
        }
        this.timefield.ownerCt = this;
        this.timefield.on('change', this.timeChange, this);
        this.callParent(arguments);
        var table = Ext.get(Ext.DomQuery.selectNode('table', this.el.dom));
        var tfEl = Ext.core.DomHelper.insertAfter(table, {
            tag: 'div',
            style: 'border:0px;',
            children: [{
                tag: 'div',
                cls: 'x-datepicker-footer ux-timefield'
            }]
        }, true);
        this.timefield.render(this.el.child('div div.ux-timefield'));
        var p = this.getEl().parent('div.x-layer');
        if(p) {
            p.setStyle("height", p.getHeight() + 31);
        }
    },
    // listener 时间域修改, timefield change
    timeChange: function(tf, time, rawtime) {
        // if(!this.todayKeyListener) { // before render
        this.value = this.fillDateTime(this.value);
        // } else {
        // this.setValue(this.value);
        // }
    },
    // @private
    fillDateTime: function(value) {
        if(this.timefield) {
            var rawtime = this.timefield.getRawValue();
            value.setHours(rawtime.h);
            value.setMinutes(rawtime.m);
            value.setSeconds(rawtime.s);
        }
        return value;
    },
    // @private
    changeTimeFiledValue: function(value) {
        this.timefield.un('change', this.timeChange, this);
        this.timefield.setValue(this.value);
        this.timefield.on('change', this.timeChange, this);
    },
    /* TODO 时间值与输入框绑定, 考虑: 创建this.timeValue 将日期和时间分开保存. */
    // overwrite
    setValue: function(value) {
        this.value = value;
        this.changeTimeFiledValue(value);
        return this.update(this.value);
    },
    // overwrite
    getValue: function() {
        return this.fillDateTime(this.value);
    },
    // overwrite : fill time before setValue
    handleDateClick: function(e, t) {
        var me = this,
            handler = me.handler;
        e.stopEvent();
        //样式的处理，让当前被选中项边框为红
        var tds = Ext.fly(t.parentNode.parentNode.parentNode).query('TD');
//        Ext.each(tds,function(t1){
//        	var te = Ext.fly(t1);
//        	te.removeCls(' x-datepicker-selected');
//        });
        
        
        if(!me.disabled && t.dateValue && !Ext.fly(t.parentNode).hasCls(me.disabledCellCls)) {
            me.doCancelFocus = me.focusOnSelect === false;
            me.setValue(this.fillDateTime(new Date(t.dateValue))); // overwrite: fill time before setValue
            delete me.doCancelFocus;
            me.fireEvent('select', me, me.value,false);
            if(handler) {
                handler.call(me.scope || me, me, me.value);
            }
            //me.onSelect();
            Ext.fly(t.parentNode).addCls(' x-datepicker-selected');
        }
    },
    // overwrite : fill time before setValue
    selectToday: function() {
        var me = this,
            btn = me.todayBtn,
            handler = me.handler;
        if(btn && !btn.disabled) {
            me.fireEvent('select', me, me.value,true);
            if(handler) {
                handler.call(me.scope || me, me, me.value);
            }
            me.onSelect();
        }
        return me;
    }
});/**
 * @class Ext.form.field.DateTime
 * @private
 */
Ext.define('Ext.form.field.DateTime', {
    extend: 'Ext.form.field.Date',
    alias: 'widget.datetimefield',
    minText : "日期的值必须大于或等于 {0}",
    maxText : "日期的值必须小于或等于 {0}",
    initComponent: function() {
        this.format = this.format;
        this.callParent();
    },
    // overwrite
    createPicker: function() {
        var me = this,
            format = Ext.String.format;
        return Ext.create('Ext.form.field.DateTimePicker', {
            ownerCt: me.ownerCt,
            renderTo: document.body,
            floating: true,
            hidden: true,
            focusOnShow: true,
            minDate: me.minValue,
            maxDate: me.maxValue,
            disabledDatesRE: me.disabledDatesRE,
            disabledDatesText: me.disabledDatesText,
            disabledDays: me.disabledDays,
            disabledDaysText: me.disabledDaysText,
            format: me.format,
            showToday: me.showToday,
            startDay: me.startDay,
            minText: format(me.minText, me.formatDate(me.minValue)),
            maxText: format(me.maxText, me.formatDate(me.maxValue)),
            listeners: {
                scope: me,
                select: me.onSelect
            },
            keyNavConfig: {
                esc: function() {
                    me.collapse();
                }
            }
        });
    },
    onSelect: function(m, d,flag) {
        var me = this;

        me.setValue(d);
        me.fireEvent('select', me, d);
        if(flag)
        me.collapse();
    },
    getErrors: function(value) {
        var me = this,
        /***********text校验过程，为了屏蔽Date的校验***********/
        errors = [],
            validator = me.validator,
            emptyText = me.emptyText,
            allowBlank = me.allowBlank,
            vtype = me.vtype,
            vtypes = Ext.form.field.VTypes,
            regex = me.regex,
            format = Ext.String.format,
            msg;

        value = value || me.processRawValue(me.getRawValue());

        if (Ext.isFunction(validator)) {
            msg = validator.call(me, value);
            if (msg !== true) {
                errors.push(msg);
            }
        }

        if (value.length < 1 || value === emptyText) {
            if (!allowBlank) {
                errors.push(me.blankText);
            }
            //if value is blank, there cannot be any additional errors
            return errors;
        }

        if (value.length < me.minLength) {
            errors.push(format(me.minLengthText, me.minLength));
        }

        if (value.length > me.maxLength) {
            errors.push(format(me.maxLengthText, me.maxLength));
        }

        if (vtype) {
            if(!vtypes[vtype](value, me)){
                errors.push(me.vtypeText || vtypes[vtype +'Text']);
            }
        }

        if (regex && !regex.test(value)) {
            errors.push(me.regexText || me.invalidText);
        }
        /*********屏蔽结束***********/
        /*********日期校验开始***********/
            var format = Ext.String.format,
            clearTime = Ext.Date.clearTime,
//            errors = me.callParent(arguments),
            disabledDays = me.disabledDays,
            disabledDatesRE = me.disabledDatesRE,
            minValue = me.minValue,
            maxValue = me.maxValue,
            len = disabledDays ? disabledDays.length : 0,
            i = 0,
            svalue,
            fvalue,
            day,
            time;

        value = me.formatDate(value || me.processRawValue(me.getRawValue()));

        if (value === null || value.length < 1) { // if it's blank and textfield didn't flag it then it's valid
            return errors;
        }

        svalue = value;
        value = me.parseDate(value);
        if (!value) {
            errors.push(format(me.invalidText, svalue, me.format));
            return errors;
        }

        time = value.getTime();
        if (minValue && time < minValue.getTime()) {
            errors.push(format(me.minText, me.formatDate(minValue)));
        }

        if (maxValue && time > maxValue.getTime()) {
            errors.push(format(me.maxText, me.formatDate(maxValue)));
        }

        if (disabledDays) {
            day = value.getDay();

            for(; i < len; i++) {
                if (day === disabledDays[i]) {
                    errors.push(me.disabledDaysText);
                    break;
                }
            }
        }

        fvalue = me.formatDate(value);
        if (disabledDatesRE && disabledDatesRE.test(fvalue)) {
            errors.push(format(me.disabledDatesText, fvalue));
        }

        return errors;
    }
});/**
 * =========================================== 日期（开始日期--结束日期）校验类型以及方法重写
 * ===========================================
 * @private
 */
Ext.apply(Ext.form.VTypes, {
    /**
     * 针对起始日期和截至日期的值校验
     */
    'daterange' : function(val, field) {
        var date = field.parseDate(val);
        if (!date) {
            return;
        }
        if (field.startDateField
            && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax
            .getTime()))) {
            var start = field.startDateField;
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } else if (field.endDateField
            && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin
            .getTime()))) {
            var end = field.endDateField;
            end.setMinValue(date);
            end.validate();
            this.dateRangeMin = date;
        }
        return true;
    },
    'datetimerange' : function(val,field){
        var date = field.parseDate(val);
        //******************************校验日期
        if (!date) {
            return;
        }
        if (field.startDateField
            && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax
            .getTime()))) {
            var start = field.startDateField;
            start.setMaxValue(date);
            start.validate();
            this.dateRangeMax = date;
        } else if (field.endDateField
            && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin
            .getTime()))) {
            var end = field.endDateField;
            end.setMinValue(date);
            this.dateRangeMin = date;
            end.validate();
        }
        return true;
    }
});/**
 * @class NS.menu.Menu
 * @extends NS.container.Panel
 */
NS.define('NS.menu.Menu', {
	extend : 'NS.container.Panel',
	initData : NS.emptyFn,
	/**
	 * @cfg {Object} defaults 默认配置属性
	 */
	/**
	 * @cfg {String} defaultType 组件默认类型
	 */
	/**
	 * @cfg {Object/Object[]} dockedItems 作为停靠此面板项目的一个组件或一系列的组件被添加,默认'right','center','left'
	 */
	/**
	 * @cfg {Boolean} hideCollapseTool 是否隐藏收缩工具栏 默认false  
	 */
	/**
	 * @cfg {Boolean} ignoreParentClicks  true以忽略这是一个父项在此菜单中点击任何项目（显示一个子菜单），这样子菜单中单击父项时，没有被驳回 默认false
	 */
	/**
	 * @cfg {Boolean} plain 真正删除阴刻线下的左侧菜单中，并且不缩进一般组件项目 默认false
	 */
	/**
	 * @cfg {Boolean} showSeparator 是否显示图标分隔符  默认true
	 */
	/***************************************************************************
	 * 初始化组件所支持的属性的映射
	 * 
	 * @private
	 */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			defaults:true,
			defaultType:true,
			disabled:true,
			dockedItems:true,
			hideCollapseTool:true,
			//items:true,
			ignoreParentClicks:true,
			plain:true,
			showSeparator:true
		});
	},
	initComponent : function(config) {
		this.component = Ext.create('Ext.menu.Menu', config);
	},
    onClick : function(){
        this.component.on('click',function(menu,item,event){
            NS.Event.setEventObj(event);
            this.fireEvent('click',NS.util.ComponentInstance.getInstance(menu),item,NS.Event);
        },this);
    }
});
/**
 * @class NS.window.Window
 * @extends NS.container.Panel
 * 
 */
NS.define('NS.window.Window',{
	extend:'NS.container.Panel',
	/**
	 * @cfg {String} title 窗口标题
	 */
	/**
	 * @cfg {String/Number} bodyPadding 窗体的边框距
	 */
	/**
	 * @cfg {Boolean} closable 是否可用 默认true
	 */
	/**
	 * @cfg {String} closeAction 窗口上关闭图标的关闭的模型，‘destroy’、‘hide’,分别指销毁、隐藏这个窗口,默认destroy
	 */
    /**
     * @cfg {Boolean} modal 是否模态窗口,默认false
     */
	/**
	 * @cfg {String/Boolean} shadow 是否显示阴影 默认"sides"  
	 */
    /**
     * @cfg {Number} x 窗体元素在窗口上显示的横轴的位置
     */
    /**
     *  @cfg {Number} x 窗体元素在窗口上显示的横轴的位置(以屏幕左上角坐标轴为 原点，向左为横轴，向下为纵轴)
     */
    /**
     *  @cfg {Number} y 窗体元素在窗口上显示的纵轴的位置(以屏幕左上角坐标轴为 原点，向左为横轴，向下为纵轴)
     */
	/**
	 * @private
	 */
	initComponent:function(config){
        config.constrainTo = config.constrainTo||Ext.getBody();
        config.constrain = config.constrain !== undefined ? config.constrain : true;
		this.component = Ext.create('Ext.window.Window',config);
	},
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			autoShow:true,
			bodyPadding:true,
			shadow:true,
			buttonAlign:true,
            constrain : true,
			buttons:true,
            tools : true,
            resizable : true,//是否允许改变window的宽度和高度
            modal : true,
			closable:true,
			closeAction:true,
			collapsed:true,
            x : true,
            y : true
		});
	}
});/**
 * ext扩展组件
 */
Ext.define('Ext.form.field.ForumSearch',{
	extend:'Ext.form.ComboBox',
	allowBlank : false,
	typeAhead : false,
	hideTrigger : true,
	labelWidth : 70,
    width : 280,
    pageSize : 10,
    queryDelay:500,//默认延时
    queryCaching : false,//查询缓存禁用
    queryParam:'mc',//查询参数
	minChars:3,//最小触发查询数
	displayField:'mc',//默认显示(值)名称
	valueField:'id',//默认的实际值
    
	initComponent:function(){
		var cfg = this.initialConfig;//初始参数
		
		this.callParent();
		this.addEvents('beforeload');
		
		this.initStore(cfg.service,cfg.fields,typeof(cfg.pageSize)!='undefined'?cfg.pageSize:this.pageSize);
		var getInnerTpl = cfg.getInnerTpl;
		//删除Store里的配置属性
		delete cfg.url;
		delete cfg.fields;
		if(getInnerTpl)delete cfg.getInnerTpl;
		
		this.listConfig = {
			loadingText : '请稍后...',
			emptyText : '无查询结果',
			getInnerTpl : getInnerTpl || function() {
				return '<a class="search-item" id={id}>名称：<b>{mc}</b></a>';
			}
		};
		
	},
	_baseAction:'baseAction!queryByComponents.action',//同样可以覆盖,暂不保留覆盖接口
	/**
	 * 初始化Store
	 * @private
	 * @param {Object} service 链接路径 {
	 *                                  serviceName:'',
	 *                                  params:{}
	 *                                }
	 * @param {Array} fields 域配置属性 name:'',mapping:'',type:'',format:'',以及其他配置信息
	 * @param {Number} pageSize 分页条数
	 */
	initStore:function(service,fields,pageSize){
        service = service||{};
		var params = service.params||{};
		    params.singleReturnNoComponent = 1;//加上该属性 用于后台判断返回值时无key属性
		    params = JSON.stringify(params);
       	    
		var url =  this._baseAction+'?components={"queryTableContent": {"request": "'+service.serviceName+'","params":'+params+' }}';	

		Ext.define("model", {
	        extend: 'Ext.data.Model',
	        proxy: {
	            type: 'ajax',
	            url : url,
	            reader: {
	            	type : 'json',
					root : 'data',
					totalProperty : 'count'
	            },
	            doRequest : function(operation, callback, scope) {
                var writer  = this.getWriter(),
                    request = this.buildRequest(operation, callback, scope);

                if (operation.allowWrite()) {
                    request = writer.write(request);
                }

                Ext.apply(request, {
                    headers       : this.headers,
                    timeout       : this.timeout,
                    scope         : this,
                    callback      : this.createRequestCallback(request, operation, callback, scope),
                    method        : "post",
                    disableCaching: false // explicitly set it to false, ServerProxy handles caching
                });

                Ext.Ajax.request(request);

                return request;
            }
	        },
	        fields: fields||["id","mc"]
            
	    });
	
		this.store =  Ext.create('Ext.data.Store', {
	        pageSize: pageSize||10,
	        model: 'model'
	    });
		this.store.addListener('beforeload',function(store,operation,eOpts){
				return this.fireEvent('beforeload',operation['params'],eOpts);
		},this);
	},
    /**
     * 设置查询参数
     * @param params
     */
    setQueryParams : function(params){
        for(var i in params){
            this.store.getProxy().setExtraParam(i,params[i]);
        }
    }
});
/**
 * 时间段组件
 * User: zhangzg
 * Date: 13-11-20
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
NS.define('NS.appExpand.DateSection',{
    extend:'NS.Component',
    initConfigMapping:function(){
        this.callParent();
        this.addConfigMapping({});// 不接受任何配置参数
    },
    initEvents:function(){
        this.callParent();
    },
    packEvents: function(){
        this.callParent();
        /*增加事件*/
        this.addEvents(
            /**
             * @event 日期组件校验通过事件。
             *         即开始日期不大于结束日期，并且开始日期和结束日期都不为空时触发的事件。
             * @param {NS.Component} this
             */
            'validatepass'
        );
    },
    /*增加事件监听器*/
    onValidatepass:function(){
        this.component.on('validatepass',function(){
            this.fireEvent('validatepass',this);
        },this);
    },
    initComponent:function(config){
        this.callParent();
        this.createFieldSet();
    },
    createFieldSet : function(){
        var fromDateField = this.fromDateField = Ext.create('Ext.form.field.Date', {
            width:120,
            fieldLabel: '起',
            labelWidth:20,
            name: 'from_date',
            format:'Y-m-d',
            editable:false
        });

        var toDateField = this.toDateField  = Ext.create('Ext.form.field.Date', {
            width:120,
            margin:'0 0 0 5',
            fieldLabel: '止',
            labelWidth:20,
            name: 'to_date',
            format:'Y-m-d',
            editable:false
        });

        this.component = Ext.create('Ext.container.Container', {
            layout: 'hbox',
            padding: 0,
            margin: '0 0 0 0',
            width: 250,
            height: 26,
            border: false,
            items:[fromDateField,toDateField]
        });
        this.validate();
    },
    validate:function(){
        this.toDateField.addListener('change',function(comp,newValue,oldValue){
            var toDate = comp.getRawValue(),
                fromDate = this.fromDateField.getRawValue();
            if(fromDate!=''){
                if(toDate>=fromDate){
                    this.component.fireEvent('validatepass');
                }else{
                    this.toDateField.focus();
                    this.toDateField.setValue(null);
                }
            }
        },this);
        this.fromDateField.addListener('change',function(comp,newValue,oldValue){
            this.toDateField.setValue(null);
            var fromDate = comp.getRawValue(),
                toDate = this.toDateField.getRawValue();
            this.toDateField.setMinValue(fromDate);
            if(toDate!=''){
                if(toDate>=fromDate){
                    this.component.fireEvent('validatepass');
                }else{
                    this.fromDateField.setValue(null);
                    this.fromDateField.focus();
                }
            }
        },this);
    },
    /**
     * 渲染组件到制定的dom节点id上。
     * @param id
     */
    render : function(id) {
        this.component.render(id);
    },
    /**
     * 获取日期段组件的值。
     * @return {Object}
     */
    getRawValue : function(){
        var fromDate = this.component.getComponent(0).getRawValue(),
            toDate = this.component.getComponent(1).getRawValue();
        return {fromDate:fromDate,toDate:toDate};
    },
    setDisabled:function(){
        this.component.getComponent(0).setDisabled(true);
        this.component.getComponent(1).setDisabled(true);
    },
    setValue2Today:function(){
        this.component.getComponent(0).setValue(new Date());
        this.component.getComponent(1).setValue(new Date());
    },
    setValue : function(config){
        this.component.getComponent(0).setValue(new Date(config.from));
        this.component.getComponent(1).setValue(new Date(config.to));
    }
});/**
 * 组件区间
 * User: zhangzg
 * Date: 13-11-20
 * Time: 下午12:22
 * To change this template use File | Settings | File Templates.
 */
NS.define('NS.appExpand.CompoSection',{
    extend:'NS.Component',
    initConfigMapping:function(){
        this.callParent();
        this.addConfigMapping({
            fromCompo:true,
            toCompo:true
        });
    },
    initEvents:function(){
        this.callParent();
    },
    packEvents: function(){
        this.callParent();
        /*增加事件*/
        this.addEvents(
            /**
             * @event 日期组件校验通过事件。
             *         即开始日期不大于结束日期，并且开始日期和结束日期都不为空时触发的事件。
             * @param {NS.Component} this
             */
            'validatepass'
        );
    },
    /*增加事件监听器*/
    onValidatepass:function(){
        this.component.on('validatepass',function(){
            this.fireEvent('validatepass',this);
        },this);
    },
    initComponent:function(config){
        this.callParent();
        this.createFieldSet(config);
    },
    createFieldSet : function(config){
        this.component = Ext.create('Ext.container.Container', {
            layout: 'hbox',
            padding: 0,
            margin: '0 0 0 0',
            width:250,
            height: 26,
            border: false,
            items:[config.fromCompo.getLibComponent(),config.toCompo.getLibComponent()]
        });
        this.validate(config);
    },
    validate:function(config){
        var fromCompo = config.fromCompo.getLibComponent(),
            toCompo = config.toCompo.getLibComponent();
        toCompo.addListener('change',function(comp,newValue,oldValue){
            var toDate = comp.getValue(),
                fromDate = fromCompo.getValue();
            if(fromDate!=''){
                if(toDate>=fromDate){
                    this.component.fireEvent('validatepass');
                }else{
                    toCompo.focus();
                    toCompo.setValue(null);
                }
            }
        },this);
        fromCompo.addListener('change',function(comp,newValue,oldValue){
            toCompo.setValue(null);
        },this);
    },
    /**
     * 渲染组件到制定的dom节点id上。
     * @param id
     */
    render : function(id) {
        this.component.render(id);
    },
    getValue : function(){
        var from = this.component.getComponent(0).getValue(),
            to = this.component.getComponent(1).getValue();
        return {from:from,to:to};
    },
    getRawValue : function(){
        var from = this.component.getComponent(0).getRawValue(),
            to = this.component.getComponent(1).getRawValue();
        return {from:from,to:to};
    },
    setDisabled:function(){
        this.component.getComponent(0).setDisabled(true);
        this.component.getComponent(1).setDisabled(true);
    },
    setValue : function(config){
        this.component.getComponent(0).setValue(config.from),
        this.component.getComponent(1).setValue(config.to);
    }
});/**
 * 单选框容器组件。这不同于原生的Ext的radioGroup组件！
 * User: zhangzg
 * Date: 13-11-20
 * Time: 下午3:06
 * To change this template use File | Settings | File Templates.
 */
NS.define('NS.appExpand.RadioGroup',{
    extend:'NS.Component',
    initConfigMapping:function(){
        this.callParent();
        this.addConfigMapping({
            items:true
        });
    },
    initEvents:function(){
        this.callParent();
    },
    packEvents: function(){
        this.callParent();
    },
    initComponent:function(config){
        this.radioCompoMap = {};
        this.radioNSCompoMap = {};
        this.callParent();
        this.createFieldSet(config);
    },
    createFieldSet : function(config){
        var items = config.items,
            length = items.length,
            i = 0,
            firstCheckRadio = false,
            isChecked = false;
        this.component = Ext.create('Ext.container.Container',{
            layout: {
                type:'hbox',
                align:'middle'
            },
            padding: 0,
            margin: '0 0 0 0',
            height: 26,
            border: false
        });
        var width = 0;
        for(;i<length;i++){
            var item = items[i];
            (function(item,me){
                isChecked = !firstCheckRadio&&item.checked ? true : false;
                var radio = new Ext.form.field.Radio({
                    name:item.radioGroupName,
                    inputValue:item.radioKey,
                    checked:isChecked,
                    labelWidth:item.labelWidth,
                    fieldLabel:item.fieldLabel
                });
                var fieldSet = Ext.create('Ext.container.Container', {
                    layout: 'column',
                    padding: 0,
                    margin: '0 0 0 0',
                    width: item.width,
                    height: 26,
                    border: false,
                    items:[radio,item.compo.getLibComponent()]
                });
                if(!isChecked){
                    item.compo.getLibComponent().setDisabled(true);
                }else{
                    me.checkedCompo = item.compo;
                }
                me.radioCompoMap[item.radioKey] = fieldSet;
                me.radioNSCompoMap[item.radioKey] = item.compo;
                radio.addListener('change',function(compo,newValue,oldValue){
                    var compoValue = compo.inputValue;
                    if(!newValue&&oldValue){// 由选中变为 不选中
                        me.radioCompoMap[compoValue].getComponent(1).setDisabled(true);
                    }else if(newValue&&!oldValue){// 由不选中 变为 选中
                        me.checkedCompo = me.radioNSCompoMap[compoValue];
                        me.radioCompoMap[compoValue].getComponent(1).setDisabled(false);
                    }
                },me);
                width = width + item.width;
                me.component.add(fieldSet);
            })(item,this);
        }
        this.component.setWidth(width);
    },
    /**
     * 渲染组件到制定的dom节点id上。
     * @param id
     */
    render : function(id) {
        this.component.render(id);
    },
    getRawValue :function(){
        return this.checkedCompo.getRawValue();
    },
    getValue :function(){
        return this.checkedCompo.getValue();
    }
});/**
 * 原生Ext扩展插件
 * @private
 */
Ext.define('Ext.ux.comboBoxTree',{
	extend:'Ext.form.field.Picker',
    enableKeyEvents : true,
	//width:210,//默认宽度210
	/**
	 * 重写Ext.form.field.Picker的initComponent方法
	 */
	initComponent:function(){
		this.callParent(arguments);
		this.addEvents('treeselect', 'selectcheck');
		var cfg = this.initialConfig;

		var rt=false,
		    ex=true,
		    ro=cfg.rootName,
		    tr=cfg.treeData,
		    he=cfg.height,
		    width = cfg.width||210;
			var pickerWidth = cfg.pickerWidth||0;
	    
		if(cfg.rootVisible==true){rt=true;}
		if(cfg.expanded==false){ex=false;}
		var rootBasic = {//Store的默认配置参数
				expanded : ex,// expanded 默认true 展开
				text : ro||'根',  // 根节点名称
				children : tr||[]// 树形数据
			},treeBasic = {  //treePanel的默认配置参数
				rootVisible : rt,//默认跟节点不显示
				height : he||300//默认高度300
			};
		
		//移除非picker所需的属性
		if(tr)delete cfg.treeData;
		if(rt)delete cfg.rootVisible;
		if(ex)delete cfg.expanded;
		if(ro)delete cfg.rootName;
		if(he)delete cfg.height;
		
		var comBasic = {//组件基础配置
				isLeafSelect:true,//是否允许选择叶子节点
				notLeafInfo:'此节点已设置为不可选,可更改isLeafSelect为true时可选',
			    isParentSelect:true,//是否允许选择父节点
			    notParentInfo:'此节点已设置为不可选,可更改isParentSelect为true时可选',
				editable : false//这里默认不可编辑
		};
		NS.apply(comBasic,cfg);
		//将转换后的值赋予root\tree属性,传递给ext原生对象
		
		var rootBasic_ ={
		     expanded: true,
             children:[]
		}; 

        var treeBasic_ = {
        		rootVisible:false,
				floating : true,
				height:300,//tree默认高度300
				autoScroll:true,
				focusOnToFront : false,
				useArrows : true
		};
        Ext.apply(rootBasic_,rootBasic);
		var self=this,store = this.__store =  Ext.create('Ext.data.TreeStore', {
			root: rootBasic_
		});
		//this.__store = Ext.clone(store);
        Ext.apply(treeBasic_,treeBasic);
        treeBasic_.store =  store;
        Ext.apply(comBasic,treeBasic_);

		this.picker = Ext.create('Ext.tree.Panel', comBasic);
		
		//此处为了解决按键时picker弹出,具体加该事件的用处,此事不知晓,先行隐藏(修改人:hanqing)
		/*this.on('keydown',function(picker,event){
            var he = event.getTarget();
            var el = Ext.fly(he);
            this.setValue(el.getAttribute('value'));
            this.expand();
        },this);*/
		
        this.on('blur',function(picker,event){
            this.setValue(this.getValue());
        },this);
        
        this.on('expand',function(){
        	if(pickerWidth!=0){
        		this.picker.setWidth(pickerWidth);//重新设置宽度
        	}
        },this);
        
		this.picker.on({
			checkchange : function(node,check) {
				self.checkedAllChildren(node,check);//设置子节点的状态
				
				//向上父节点
				var parent = node.parentNode;
				if(check == true){
				   while(parent){
					   parent.set('checked',true);
					   parent = parent.parentNode;
				   }
				}else if(check == false){
				   while(parent){
					   var flag = self.judgeHasChildChecked(node,parent);
					   if(flag == true){
						   break;
					   }else if(flag == false){
						   parent.set('checked',false);
						   parent = parent.parentNode;
						   //到root时break;
					   }
				   }
				}
				var records = self.picker.getView().getChecked(),values = [];
				Ext.Array.each(records, function(rec) {
						values.push(rec.get('id'));
				});
				self.setValue(values.join(','));// 实际值
				self.fireEvent('selectcheck', node);
			},
			itemclick : function(view, record) {
				
				
				
				if(record.isLeaf()){//如果是叶子节点并且叶子节点不可选择
					if(comBasic.isLeafSelect){
					}else{
						Ext.Msg.alert("提示",comBasic.notLeafInfo+"!");
						return;
					}
				}
				if(!record.isLeaf()){//如果是父节点,且父节点不可选
					if(comBasic.isParentSelect){
					}else{
                        Ext.Msg.alert("提示",comBasic.notParentInfo+"!");
						return;
					}
				}
				if(record.get('checked')==undefined){
					self.setValue(record.get('id'));// 显示值
					self.collapse();
				}else{
				    record.set('checked',!record.get('checked'));
				    this.fireEvent('checkchange',record,record.get('checked'));
				}
				self.fireEvent('treeselect', record);
			}
		});
	},
	/**
	 * 特殊方法扩展
	 * @param value 该node对应的值
	 */
	getCurrentNodeRaw:function(value){
		var node = this.picker.getStore().getNodeById(value);
		if(node) return node['raw'];
		return {};
	},
	/**
	 * 选中所有子节点以及子节点的子节点
	 * @param node
	 * @private
	 */
	checkedAllChildren : function(node,check){
		var childs = node.childNodes;
		var itertor = childs;// 需要迭代的节点
		//向下子节点
		if(itertor&&itertor.length>0){
			var needToIter = [];// 待被迭代的节点
			while (itertor.length != 0) {
				for (var i = 0, len = itertor.length; i < len; i++) {
					var c = itertor[i];// 获取子节点
					c.set('checked', check);// 设置该节点被选择
					if (c.childNodes)
						needToIter = needToIter.concat(c.childNodes);
				}
				itertor = needToIter;
				needToIter = [];
			}
		}
	},
	/**
	 * 判断当前节点是否有子节点被选中
	 * @param node
	 * @private
	 */
	judgeHasChildChecked : function(node,parent){
		//这里需要检测在同一节点里是否有已经被选中的值,如果有则break;
		var flag = false;
		if (parent.parentNode) {
				var c = parent;// 获取子节点
				var it_ = c.childNodes;
				for(var i=0,len=it_.length;i<len;i++){
					if(it_[i].get('checked')==true){
						flag=true;
						break;
					}
				}
		}
		return flag;
	},
	
	/**
	 * 得到picker
	 * @return {Ext.tree.Panel}
	 */
	getPicker: function() {
		var me = this;
		me.picker.ownerCt =  me.ownerCt;//添加了ownerCt属性.
		return me.picker || (me.picker = me.createPicker());
	},
	/**
	 * 设置值
	 * @param {Array/String/Number} id
	 */
	setValue:function(id){
		if (id==null||typeof id== 'undefined') {
			this.values = null;
			this.setRawValue('');
			return;
		}
		var array = id.toString().split(',');
		var names = [], values = [], store = this.picker.getStore();
		for (var i = 0, len = array.length; i < len; i++) {
			var _id = array[i];
			var node = store.getNodeById(_id);
			
			if (node) {
				if(node.get('id')!='root'){//根节点root取消
					names.push(node.get('text'));
					values.push(node.get('id'));
					if(typeof node.get('checked')!='undefined'&&node.get('checked')!=null){
					   node.set('checked',true);
					   node.commit(true);
					}
				}
			}
		}
		this.values = values.join(',');
		var text = this.text = names.join(',');
		this.setRawValue(text);
	},
	/**
	 * 得到值
	 * @return {String} ids 值集合
	 */
	getValue:function(){
	    return this.values;
	},
	/**
	 * 提供的提交时调用的方法
	 * @return
	 */
	getSubmitValue : function(){
		return this.values;
	},
    setRawValue : function(text){
        this.texts = text;
        this.callParent([text]);
    },
	/**
	 * 得到显示值
	 * @return {String}
	 */
	getRawValue:function(){
		return this.texts||'';
	},
	reset:function(){
		this.callParent();//先重置
		var store = this.picker.getStore();
		var node = store.getRootNode();//得到根节点,从根节点开始遍历将cheked置为false
		if(typeof node.get('checked')!='undefined'&&node.get('checked')!=null){
			this.checkedAllChildren(node,false);//将checked全部置为false
		}
		//以下是源码
		this.setValue(this.originalValue);
		this.clearInvalid();
		// delete here so we reset back to the original state
		delete this.wasValid;
		//this.setValue(this.values);//再将之前value属性的值还原
		//以上的实现算是真正实现了组件自身的reset方式.重置是还原其第一次展现的状态,并不是清空所有值
	},
	loadData : function(data){
		var store = this.picker.getStore();
		store.removeAll();
		store.setRootNode({expanded: true, text:'根', children:data});
	}
//	isValid:function(){
//		var me=this;
//		isValid = me.disabled || Ext.isEmpty(me.getErrors());
//		var allowBlank = this.picker.allowBlank;
//		if(typeof allowBlank =='undefined'||allowBlank==true){//如果可以为空
//			isValid = true;
//		}else{//如果不可为空
//			if((this.values!=null&&this.values!='')){//不空
//				isValid = true;
//			}else{
//				isValid = false;
//			}
//		}
//		return isValid;
//	},
//	validate : function() {
//	    var me = this,
//	        isValid = me.isValid();
//	    if (isValid !== me.wasValid) {
//	          me.wasValid = isValid;
//	          me.fireEvent('validitychange', me, isValid);
//	    }
//
//	    return isValid;
//	 }
});/**
 * @class NS.form.field.ForumSearch
 * @extends NS.form.field.ComboBox
 * 查询下拉组件 
 * 
 */
NS.define('NS.form.field.ForumSearch',{
	extend:'NS.form.field.ComboBox',
	/**
	 * @cfg {String} name 组件名称
	 */
	/**
	 * @cfg {String} queryParam 查询的参数名
	 */
	/**
	 * @cfg {Boolean} readOnly 是否只读 默认false
	 */
	/**
	 * @cfg {String} fieldLabel 组件显示标签名称
	 */
	/**
	 * @cfg {Boolean} editable 是否可编辑 默认true 
	 */
	/**
	 * @cfg {Number} minChars 最小查询字符数  默认4
	 */
	/**
	 * @cfg {String} displayField 显示值域名称
	 */
	/**
	 * @cfg {String} valueField 实际值域值
	 */
	/**
	 * @cfg {String} emptyText 组件值为空是显示的文本
	 */
	/**
	 * @cfg {String} labelAlign 标签的位置left center right 默认 left
	 */
	/**
	 * @cfg {Boolean} allowBlank 是否允许为空 默认false
	 */
	/**
	 * @cfg {Boolean} typeAhead true其余的文字键入一个可配置的的延迟（typeAheadDelay,默认250）后，它是否符合一个已知值来填充和自选。默认true
	 */
	/**
	 * @cfg {Boolean} hideTrigger 隐藏下拉图标 默认true
	 */
	/**
	 * @cfg {Number} labelWidth 标签长度 默认70
	 */
	/**
	 * @cfg {Number} width 组件宽度 默认280
	 */
	/**
	 * @cfg {Object} listConfig 下拉列表配置项
	 */
	/**
	 * @cfg {Number} pageSize 分页条数 默认10
	 */
	/**
	 * @cfg {String} url 链接路径
	 */
	/**
	 * @cfg {Array} fields 域配置属性 name:'',mapping:'',type:'',format:'',以及其他配置信息
	 */
	/**
	 * @cfg {Function} getInnerTpl 得到下拉列表的内部配置模板
	 */
	/**
	 * @cfg {Object} service 服务配置
	 */
	/**
	 * @cfg {String} valueNotFoundText  
	 */
	/**
	 * @cfg {Number} queryDelay 默认500,当mode为remote/local时
	 */
	/**
	 * @private
	 */
	initComponent:function(cfg){
		this.component = Ext.create('Ext.form.field.ForumSearch',cfg);
	},
	getValue:function(){
		return this.component.getValue();
	},
	getRawValue:function(){
		return this.component.getRawValue();
	},
	getSubmitValue:function(){
		return this.component.getValue();
	},
	//setValue不建议使用,因为初始时根本没有值,所以setValue(id),该组件的值会变更为id,而非对应的mc
	setValue:function(id){
		this.component.setValue(id);
	},
	//loadData方法可能无法正常使用,因为queryMode已经为remote才能这么做.而该方法需要为local
	loadData:function(data){
		this.component.loadData(data);
	},
    /**
     * 初始化事件
     * @private
     */
    initEvents: function () {
        this.callParent();
        this.addEvents(
            /***
             * @event beforeload 加载数据之前
             */
            'beforeload'
        );
    },
    onBeforeload:function(){
        this.component.on('beforeload',function(params,opts){
            return this.fireEvent('beforeload', params);
        },this);
    },
	/**
	 * @private
	 */
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			service:true,
			name:true,
			queryParam:true,
			readOnly:true,
			fieldLabel:true,
			editable:true,
			minChars:true,
			displayField:true,
			valueField:true,
			emptyText:true,
			labelAlign:true,
			allowBlank:true,
			typeAhead:true,
			hideTrigger:true,
			labelWidth:true,
			width:true,
			valueNotFoundText:true,
			listConfig:true,
			pageSize:true,
			url:true,
			queryDelay:true,
			fields:true,
			getInnerTpl:true
		});
	},
    setQueryParams : function(params){
        this.component.setQueryParams(params);
    }
});

/**
 * @class NS.grid.TransformGrid
 * @extends NS.Component
 * 无样式表格
 */
NS.define('NS.grid.TransformGrid',{
	extend:'NS.Component',
	initComponent:function(cfg){
		
		//1、基础的表格搭建--OK
		//2、数据的转换解析-->表头的转换和数据的转换:由数据抓变换器得到，再顺序执行，要支持添加移除？！
		//3、事件控制（暂时不做）
		//4、样式控制--O_O
		 var header = cfg.fields;//NS.util.DataConverter.entitysToStandards(cfg.fields);
		 var headerStr = '';
		 var tbodyStr = '';
		 var fnObj = {};
		 for(var i=0,len=header.length;i<len;i++){
			 //顺序暂时不写
			 headerStr+= '<td>'+header[i].nameCh+'</td>';
			 tbodyStr+='<td>{[this.'+header[i].name+'(values.'+header[i].name+')]}</td>';
			 fnObj[header[i].name] = function(value){
				 return value;
			 };
		 }
		 var obj = cfg.format;
		 Ext.apply(fnObj,obj);//有些许问题+","的产生
		 delete cfg.format;
		 var tpl = new Ext.XTemplate('<table class="transformgrid-base">',
			       '<tr>',
			      		headerStr,
		           '</tr>',
		       '<tpl for=".">',
		            '<tr>',
		            	tbodyStr,
		             '</tr>',
		       '</tpl>',
		    '</table>',fnObj);
		if(!cfg.tpl) cfg.tpl = tpl;
		this.component = Ext.create('Ext.Component',cfg);
	},
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
			tpl:true,
			padding:true,
			fields:true,
			format:true,
			data:true
		});
	}
});/**
 * @class NS.button.Download
 * @extends NS.button.Button
 * 下载组件
 */
NS.define('NS.button.Download', {
	extend : 'NS.button.Button',
	initComponent : function(cfg) {
		var me = this,basic = {
			text:'导出'
		};
		Ext.apply(basic,cfg);
		
		me.component = Ext.create('Ext.button.Button',basic);
		me.component.addListener('click',function(){
			//加入相应条件/当前结果集/全部结果集
			//通过条件解析（分字段）--实体属性表
			//另一种表单
			//向后台发送的链接
			//me.show();
			alert('---');
		});
	}
});/**
 * @class NS.window.MessageBox
 * @extends NS.window.Window 消息框
 */
NS.define('NS.window.MessageBox', {
	extend : 'NS.window.Window',
	/**
     * {property} 按钮配置属性,为'确定'
     * @type Number
     */
    OK : Ext.Msg.OK,
    /**
     * {property}按钮配置属性,为'是'
     * @type Number
     */
    YES : Ext.Msg.YES,
    /**
     * {property} 按钮配置属性,为'否'
     * @type Number
     */
    NO : Ext.Msg.NO,
    /**
     * {property}按钮配置属性,为'取消'
     * @type Number
     */
    CANCEL : Ext.Msg.CANCEL,
    /**
     * {property}按钮配置属性,分别为'确定','取消'
     * @type Number
     */
    OKCANCEL : Ext.Msg.OKCANCEL,
    /**
     * {property}按钮配置属性,分别为'是','否'
     * @type Number
     */
    YESNO : Ext.Msg.YESNO,
    /**
     * {property} 按钮配置属性,分别为'是','否','取消'
     * @type Number
     */
    YESNOCANCEL : Ext.Msg.YESNOCANCEL,
    /**
     * {property} 图标ICON配置属性,为'消息图标'
     * @type String
     */
    INFO : Ext.Msg.INFO,
    /**
     * {property} 图标ICON配置属性,为'警告图标'
     * @type String
     */
    WARNING : Ext.Msg.WARNING,
    /**
     * {property}图标ICON配置属性,为'疑问图标'
     * @type String
     */
    QUESTION : Ext.Msg.QUESTION,
    /**
     * {property}图标ICON配置属性,为'错误图标'
     * @type String
     */
    ERROR : Ext.Msg.ERROR,
    /**
     * @cfg {Boolean} autoShow 是否创建即自动显示，默认false
     */
    /**
     * @cfg {Object/Object[]} buttons 放置的按钮对象集合，一般系统默认可满足，不需要额外更改
     */
    /**
     * @cfg {String} buttonAlign 按钮显示位置，有以下几个可选项: 'right', 'left' 和 'center' 
     */
    /**
     * @cfg {Boolean} closable 是否可关闭，默认true
     */
    /**
     * @cfg {String} closeAction 关闭模式：'destroy','hide',默认destroy
     */
    /**
     * @cfg {Boolean} hidden 隐藏属性 默认 false
     */
    /**
     * @cfg {Number} height 组件高度
     */
    /**
     * @cfg {String/Object} html  html元素内的内容，默认""
     */
    /**
     * @cfg {String} icon 图标(这里值配置属性内的值范围)
     */
    /**
     * @cfg {String} iconCls 图标样式 
     */
    /**
     * @cfg {String} title 组件的标题
     */
    /**
     * @cfg {String} msg 组件的信息提示内容
     */
    /**
     * @cfg {Number} width 组件宽度
     */
    /**
     * @private
     */
	initConfigMapping : function() {
		this.callParent();
		this.addConfigMapping({
			autoShow:true,
			buttons:true,
			buttonAlign:true,
			closable:true,
			closeAction:true,
			hidden:true,
			height:true,
			html:true,
			icon:true,
			iconCls:true,
			title:true,
			msg:true,
			listener:true,
			width:true
		});
	},
	/**
	 * @private
	 * @param {Object} cfg
	 */
	initComponent : function(cfg) {
		this.component = Ext.create('Ext.window.MessageBox', cfg);
	},
	/**
	 * 显示消息框
	 * @param {Object} cfg 配置参数对象
	 * @return 返回这个ext对象本身
	 */
	show : function(cfg) {
		this.component.show(cfg);
		return this.component;
	},
	/**
	 * 提示框
	 * @param {String/Object} cfg 配置对象或者是标题
	 * @param {String} msg 信息内容
	 * @param {Function} fn 回调函数
	 * @param {Object} scope 作用对象
	 * @return 返回ext这个对象本身
	 */
	alert : function(cfg, msg, fn, scope) {
		return this.component.alert(cfg, msg, fn, scope);
	},
	/**
	 * 选择框
	 * @param {String/Object}cfg 配置对象或者是标题
	 * @param {String} msg 信息内容
	 * @param {Function} fn 回调函数
	 * @param {Object} scope 作用对象
	 * @return 返回ext这个对象本身
	 */
	confirm : function(cfg, msg, fn, scope) {
		return this.component.confirm(cfg, msg, fn, scope);
	},
	/**
	 * 输入提示框
	 * @param {Object/String} cfg 配置对象或者是标题
	 * @param {String} msg 信息内容
	 * @param {Function} fn 回调函数
	 * @param {Object} scope 作用对象
	 * @param {Boolean/Number} multiline 默认false,为true时表示创建一个多文本输入框对象,为数字时则表示创建number行的多文本对象
	 * @param {String} value 获取输入的值
	 * @return 返回ext这个对象本身
	 */
	prompt : function(cfg, msg, fn, scope, multiline, value) {
		return this.component.prompt(cfg, msg, fn, scope, multiline, value);
	},
	/**
	 * 组件隐藏方法
	 */
	hide:function(){
		this.component.hide();
	},
	/**
	 * 关闭组件方法
	 */
	close:function(){
		this.component.close();
	},
	/**
	 * 信息提示框,扩展方法，相对于alert,添加了样式,默认提示信息
     * @param {String/Object} info 字符串信息或者配置对象
	 */
	info:function(){
		var cfg = arguments[0];
		if(NS.isObject(cfg)){
			cfg.title = cfg.title || '信息提示';
			cfg.icon = cfg.icon || this.INFO;
			cfg.buttons = cfg.buttons || this.OK;
		}else if(NS.isString(cfg)){
			var len = arguments.length;
			if(len==1){
				cfg = {};
				cfg.msg = arguments[0];
			}else if(len==2){
				cfg = {};
				cfg.title = arguments[0];
				cfg.msg = arguments[1];
			}else{
				throw 'NS.Msg.info() : length is not valid!';
			}
			cfg.title = cfg.title || '信息提示';
			cfg.icon = cfg.icon || this.INFO;
			cfg.buttons = cfg.buttons || this.OK;
		}else{
			throw 'NS.Msg.info() : type is not valid !';
		}
		return this.show(cfg);
	},
	/**
	 * 警告提示框，扩展方法,默认了警告提示框的样式、按钮
	 * @param {Object} cfg 提示框配置信息
	 * @return Ext组件自身
	 */
	warning:function(){
		var cfg = arguments[0];
		if(NS.isObject(cfg)){
			cfg.title = cfg.title || '警告提示';
			cfg.icon = cfg.icon || this.WARNING;
			cfg.buttons = cfg.buttons || this.OK;
		}else if(NS.isString(cfg)){
			var len = arguments.length;
			if(len==1){
				cfg = {};
				cfg.msg = arguments[0];
			}else if(len==2){
				cfg = {};
				cfg.title = arguments[0];
				cfg.msg = arguments[1];
			}else{
				throw 'NS.Msg.warning() : length is not valid!';
			}
			cfg.title = cfg.title || '警告提示';
			cfg.icon = cfg.icon || this.WARNING;
			cfg.buttons = cfg.buttons || this.OK;
		}else{
			throw 'NS.Msg.warning() : type is not valid !';
		}
		return this.show(cfg);
	},
	/**
	 * 错误提示框，扩展方法,默认了错误提示框的样式、按钮
	 * @param {Object} cfg 提示框配置信息
	 * @return Ext组件自身
	 */
	error:function(){
		var cfg = arguments[0];
		if(NS.isObject(cfg)){
			cfg.title = cfg.title || '错误提示';
			cfg.icon = cfg.icon || this.ERROR;
			cfg.buttons = cfg.buttons || this.OK;
		}else if(NS.isString(cfg)){
			var len = arguments.length;
			if(len==1){
				cfg = {};
				cfg.msg = arguments[0];
			}else if(len==2){
				cfg = {};
				cfg.title = arguments[0];
				cfg.msg = arguments[1];
			}else{
				throw 'NS.Msg.error() : length is not valid!';
			}
			cfg.title = cfg.title || '错误提示';
			cfg.icon = cfg.icon || this.ERROR;
			cfg.buttons = cfg.buttons || this.OK;
		}else{
			throw 'NS.Msg.error() : type is not valid !';
		}
		return this.show(cfg);
	},
	/**
	 * 选择提示框，扩展方法,用于选择时，默认按钮是'是、确定'等为true的返回方法处理
	 * @param {Object/String} cfg 配置参数对象或者title
	 * @param {String} msg 提示内容 
	 * @param {Function} fn 回调函数(默认为真时的回调)
	 * @return Ext组件本身
	 */
	changeTip:function(cfg,msg,fn,scope){
		var title = '',msg_,fn_,scope_;
		if(NS.isObject(cfg)){//如果是对象
			fn_ = cfg.fn;
			msg_ = cfg.msg;
			title = cfg.title;
			scope_ = cfg.scope;
		}else if(NS.isString(cfg)){
			title = cfg;
			msg_ = msg;
			fn_ = fn;
			scope_=scope;
		}
		return this.confirm(title,msg_,function(btn){
			if(btn=='yes'){//以后可以根据需要，更改confirm的icon\buttons,这样可以在内部更改btn的实际值即可
				fn_.call(scope_||this);
			}
		});
	}
});
/**
 * @class NS.Msg
 * @extends NS.window.MessageBox
 */
/**
 * @class NS.MessageBox
 * @extends NS.window.MessageBox
 */
NS.MessageBox = NS.Msg = new NS.window.MessageBox();/***
 * @class NS.mvc.Controller
 * @extends NS.Base
 * mvc中控制层类
 *
 *      NS.define('App.com.MyController',{
                extend : 'NS.mvc.Controller',
                cssRequires : ['path1','path2'],
                dataRequires : [//类初始化准备数据,fieldname代表指定的内部变量名
                    {fieldname : 'headerData',
                    key : 'xsHeader_data',params : {a:12},
                    process : {scope : this,fn : function(data){return data;}}
                },
                    {fieldname : 'headerData2',key : 'xsHeader_data'}
                ],
                advanceDataRequires : [//数据提前加载，该配置项需要和this.getAdvanceData(['headerData1'],function(data){});共同使用
                    {fieldname : 'headerData1',key : 'xsHeader_data'}
                ],
                tplRequires : [
                    {fieldname : 'xxtpl',path : 'template/path/pat.html'}
                ],
                modelConfig : {
                    serviceConfig : {
                        'xsHeader_data' : {
                            service : "base_Service",
                            params : {}
                        },
                        'xs_show_data' :  "xsxx_showData"
                        }
                    }
                },
                init : function(){//子类processHeaderData1需重写的入口方法
                    this.(this.headerData1);
                }
            });

 *
 */
NS.define('NS.mvc.Controller',{
    /**
     *@param {Array} cssRequires css的相对路径
     * */
    /**
     *@param {Array} dataRequires 具体加载的数据，在init方法之前加载
     * */
    /**
     *@param {Array} tplRequires  tpl加载，在init方法之前加载
     * */
    /**
     *@param {Array} advanceDataRequires  数据预加载，//数据提前加载，该配置项需要和this.getAdvanceData(['headerData1'],function(data){});共同使用
     * */
      /***
     * 构造函数
     * @param {Object} config 配置对象
     */
    constructor : function(config){
        var me = this;
        this.addEvents(
            /**
             * @event pageready  当页面可以被渲染的时候触发该事件
             */
            'pageready',
            /**
             * @event tplready 当tpl加载完毕之后触发该事件
             */
            'tplready'
        );
        NS.apply(this,config);
        this.initCss();//初始化Css加载
        this.initModel();//初始化模型层
        this.initView();//初始化视图层
        this.initAdvanceDataRequires();
        this.initTplRequires(function(){
            this.fireEvent('tplready');
            this.tplready = true;
            this.initDataRequires(function(){
                this.init(config);//对象初始化
            });//初始化数据依赖
        });
    },
    /**
     * 数据提前加载，该方法主要用于:当前端某个操作较大量的数据的时候，但是该操作并不是打开页面的必须数据的时候，预先加载数据，提升用户操作的流畅度
     * @private
     **/
    initAdvanceDataRequires : function(){
        var dr = this.advanceDataRequires,fieldKeyMap = {},i= 0,item;
        this.advanceDataRequiresFlag = false;
        this.advanceDataRequiresExcute = function(){};
        if(dr && dr.length!=0){
            for(;i<dr.length;i++){
                item = dr[i];
                fieldKeyMap[item.key] = item.fieldname;
            }
            this.callService(dr,function(data){
                var i,item,fieldname;
                for(var i in fieldKeyMap){
                    fieldname = fieldKeyMap[i];
                    item = data[i];
                    this[fieldname] = item;
                }
                this.advanceDataRequiresFlag = true;
                this.advanceDataRequiresExcute();//数据提前加载函数执行
            });
        }
    },
    /**
     * 获取预加载数据，异步
     * @param datafields 获取预加载的数据的域属性数组
     * @param callback 回调函数
     */
    getAdvanceData : function(datafields,callback){
        var me = this,data = {},i= 0,item,len = datafields.length;
        if(this.advanceDataRequiresFlag){
            for(;i<len;i++){
                item = datafields[i];
                data[item] = this[item];
            }
            callback.call(this,data);
        }else{
            this.advanceDataRequiresExcute = function(){
                 me.getAdvanceData(datafields,callback);
            };
        }
    },
    /**
     * @private
     * @param callback
     */
    initTplRequires : function(callback){
        var dr = this.tplRequires||[],fieldKeyMap = {},i = 0,item,tplFlag = dr.length;
        if(dr.length!=0){
            for(;i<dr.length;i++){
                item = dr[i];
                (function(name){
                    NS.loadFile(item.path,function(txt){
                        this[name] = new NS.Template(txt);
                        tplFlag = tplFlag - 1;
                        if(tplFlag == 0){
                            callback.apply(this);
                        }
                    },this);
                }).call(this,item.fieldname);
            }
        }else{
            callback.apply(this);
        }
    },
    /**
     * 初始化数据依赖
     * @private
     */
    initDataRequires : function(callback){
        var dr = this.dataRequires||[],fieldKeyMap = {},filterMap = {},i = 0,item;
        if(dr.length!=0){
            for(;i<dr.length;i++){
                item = dr[i];
                fieldKeyMap[item.key] = item.fieldname;
                filterMap[item.key] = item.process;
            }
            this.callService(dr,function(data){
                var i,item,fieldname,process;
                for(var i in fieldKeyMap){
                    fieldname = fieldKeyMap[i];
                    process = filterMap[i];
                    item = data[i];
                    if(process && NS.isFunction(process)){
                        this[fieldname]=process(item);
                    }else if(NS.isObject(process)){
                        this[fieldname]=process.fn.call(process.scope,item);
                    }else{
                        this[fieldname] = item;
                    }
                }
                callback.apply(this);
            });
        }else{
            callback.apply(this);
        }
    },
    /**
     * 初始化Css加载
     * @private
     */
    initCss : function(){
        var array = this.cssRequires||[], i=0,len;
        for(len=array.length;i<len;i++){
            NS.loadCss(array[i]);
        }
    },
    /***
     * 初始化模型层
     * @private
     */
    initModel : function(){
        var serviceBasic = {"baseSave" : "base_save",
                            "baseUpdate" : "base_update",
                            "baseDelete" : "base_deleteByIds",
                            'baseQuery' : 'base_queryTableContent',
                            'baseTableHeader' : 'base_queryForAddByEntityName'};
        if(this.modelConfig && this.modelConfig.serviceConfig){
            NS.apply(this.modelConfig.serviceConfig,serviceBasic);
        }
        this.model = new NS.mvc.Model(this.modelConfig);
    },
    /**
     * 初始化视图层
     * @private
     */
    initView : function(){
        this.view = new NS.mvc.View(this.viewConfig);
    },
    /***
     * 子类需重写方法，子类的入口从该方法开始
     * @method init
     */
    init : NS.emptyFn,
    /**
     * 获取模型层组件
     * @return {NS.mvc.Model}
     */
    getModel : function(){
        return this.model;
    },
    /***
     * 获取view层组件
     * @return {NS.mvc.View}
     */
    getView : function(){
        return this.view;
    },
    /**
     * 别名 {@link NS.mvc.View#register NS.mvc.View}.
     * @private
     * @inheritdoc NS.mvc.View#register
     * @member NS.mvc.Controller
     * @method registerComponent
     */
    registerComponent : function(component){
        var register = this.view.register;
        register.apply(this.view,arguments);
    },
    /**
     * 别名 {@link NS.mvc.View#get NS.mvc.View}.
     * @private
     * @inheritdoc NS.mvc.View#get
     * @member NS.mvc.Controller
     * @method getComponent
     */
    getComponent : function(key){
        return this.view.get(key);
    },
    /**
     * NS.mvc.Controller 代理NS.mvc.Model的方法
     *
     *       NS.define('App.com.MyController',{
                extend : 'NS.mvc.Controller',
                modelConfig : {
                    serviceConfig : {
                        'xsHeader_data' : {
                            service : "base_Service",
                            params : {}
                        },
                        'xs_show_data' : {
                            service : "xsxx_showData",
                            params : {
                                age : 12,
                                sex : '12121212'
                            }
                        }
                    }
                }
             });
             var ct = new App.com.MyController();
     *       ct.callService(['xsHeader_data','xs_show_data'],function(data){
                alert(data);//即为你获取的JSON数据
            });

         回调函数里默认函数执行作用域为当前Controller,默认等待时间是30000毫秒
     * @param {Object[]/String[]/String} params
     * @param {Function} callback 回调函数
     * @param {Object} scope 作用域
     */
    callService : function(params,callback,scope){
        this.model.callService(params,callback,scope||this);
    },
    /**
     *       NS.mvc.Controller 代理NS.mvc.Model的方法
     *
     *       NS.define('App.com.MyController',{
                 extend : 'NS.mvc.Controller',
                 modelConfig : {
                 serviceConfig : {
                 'xsHeader_data' : {
                 service : "base_Service",
                 params : {}
                 },
                 'xs_show_data' : {
                 service : "xsxx_showData",
                 params : {
                 age : 12,
                 sex : '12121212'
         }
         }
         }
         }
         });

            var ct = new App.com.MyController();
            ct.callServiceWithTimeOut(['xsHeader_data','xs_show_data'],function(data){
            alert(data);//即为你获取的JSON数据
            },30000000);

     回调函数里默认函数执行作用域为当前Controller
     *@param {Object[]/String[]/String} params
     *@param {Function} callback 回调函数
     *@param {Number} timeout 等待时间，如果超过这个时间，回调函数将不会被执行
     *@param {Object} scope 作用域
     */
    callServiceWithTimeOut : function(params,callback,timeout,scope){
        this.model.callService(params,callback,scope||this,timeout);
    },
    /**
     * NS.mvc.Controller 代理NS.mvc.Model的方法
     * 调用单独的service，并直接返回数据
     * 回调函数里默认函数执行作用域为当前Controller
     */
    callSingle : function(serviceName,fun,scope){
        this.model.callSingle.call(this.model,serviceName,fun,scope||this);
    },
    /**
     * @private
     * 为组件注册数据加载器，通常情况下，需要特殊指定的数据加载器的组件通常为{NS.grid.Grid}
     * 或者{NS.container.Tree}
     * 这样把组件对数据的依赖路径由内部转向{NS.mvc.Model}组件，这样我们的所有数据依赖全部指向
     * {NS.mvc.Model}
     */
    registerDataLoader : function(key,component){
        component.registerDataLoader(key,this.model);
    },
    /**
     * 设置要渲染的顶层组件
     * @param {NS.Component} component 组件对象
     */
    setPageComponent : function(component){
        this.view.setComponent(component);
        this.fireEvent('pageready');
        this.pageReady = true;//表示页面已经准备就绪
    },
    /**
     * 获取底层类库组件
     * @private
     * @return {Ext.component.Component}
     * @private
     */
    getLibComponent : function(){
        return this.view.getLibComponent();
    },
    /**
     * 告诉框架表明可以渲染页面了
     * 此为Controller中必掉方法之一
     * @private
     */
    ready : function(){
        this.fireEvent('pageready');
    },
    /**
     *@inheritdoc NS.mvc.Model#updateServiceConfig
     */
    updateServiceConfig : function(){
        this.model.updateServiceConfig.apply(this.model,arguments);
    },
    /**
     * 更新service的固定请求参数
     *@inheritdoc NS.mvc.Model#updateServiceParams
     */
    updateServiceParams : function(key,params){
        this.model.updateServiceParams.apply(this.model,arguments);
    },
    /**
     * 单表新增处理函数
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Object} params 实例对象
     * @param {Function}fn 回调函数
     * @param {Object} scope  回调函数执行作用域
     */
    baseAdd: function (entityName, params, fn, scope) {
        var basic = {entityName: entityName};
        NS.apply(basic, params);
        this.callService({key: 'baseSave', params: basic}, function(data){
            fn.call(scope||this,data['baseSave']);
        });
    },
    /**
     * 单表删除处理函数
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Array} ids 待删除的实例对象的id集合
     * @param {Function}fn 回调函数
     * @param {Object} scope  回调函数执行作用域
     */
    baseDelete: function (entityName, ids, fn, scope) {
        var basic = {entityName: entityName, ids: ids.toString()};
        this.callService({key: 'baseDelete', params: basic}, function(data){
            fn.call(scope||this,data['baseDelete']);
        });
    },
    /**
     * 单表修改处理函数
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Object} params 实例对象
     * @param {Function}fn 回调函数
     * @param {Object} scope  回调函数执行作用域
     */
    baseUpdate: function (entityName, params, fn, scope) {
        var basic = {entityName: entityName};
        NS.apply(basic, params);
        this.callService({key: 'baseUpdate', params: basic}, function(data){
            fn.call(scope||this,data['baseUpdate']);
        })
    },
    /**
     * 单独请求表头数据
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Function}fn 回调函数
     * @param {Object} scope  回调函数执行作用域
     */
    baseTableHeader: function (entityName, fn, scope) {
        var basic = {entityName: entityName};
        this.callService({key: 'baseTableHeader', params: basic}, function(data){
            var headerData = data['baseTableHeader'],
                sdata = NS.E2S(headerData);
            fn.call(scope||this,sdata);
        })
    },
    /**
     * 请求虚表和实体表的数据
     * @param {String} entityName 实体名(该实体必须配置在entity.xml中)
     * @param {Function} fn 回调函数
     * @param {Object} scope 作用域
     */
    baseEntityHeaderAndTableData : function(entityName,fn,scope){
        var header = {
            key : 'baseTableHeader',
            params : {entityName : entityName}
            },
        table = {
            key : 'baseQuery',
            params : {entityName : entityName}
        }
        this.callService([header,table],function(data){
            var headerData = data['baseTableHeader'],
                sdata = NS.E2S(headerData),
                tdata = data['baseQuery'];
                fn.call(scope||this,sdata,tdata);
        });
    },
    /**
     * 请求虚表:表头数据和表体数据
     * @param {String} entityName 表头数据实体名称
     * @param {String/Object} queryTableObj 表体查询数据service配置
     * @param {} fn  回调函数
     */
    baseHeaderAndData : function(entityName,queryTableObj,fn,scope){
        var basic = {
            key : 'baseTableHeader',
            params : {entityName: entityName}
        };
        if(!queryTableObj){
            NS.Error({sourceClas : 'Template.Page',sourceMethod : 'baseHeaderAndData',msg:'必须输入请求表数据Service！'});
        }
        this.callService([basic,queryTableObj],function(data){
            var headerData = data['baseTableHeader'],
                sdata = NS.E2S(headerData);
            if(NS.isString(queryTableObj)){
                fn.call(scope||this,sdata,data[queryTableObj]);
            }else{
                fn.call(scope||this,sdata,data[queryTableObj['key']]);
            }

        });
    },
    /**
     * 绑定页面的节点信息
     * @private
     * @param node
     */
    bindNode : function(node){
        this.CD_NODE = node;
    },
    /**
     * 绑定页面遮罩
     * @private
     */
    bindLoadMask : function(mask){
        this.loadMask = mask;
    },
    /**
     * 显示遮罩
     * @private
     */
    showLoadMask : function(){
        this.loadMask.hide();
    },
    /**
     * 根据传递的功能标识，判断是否有该功能权限
     * @param funTag (String) 功能标识
     * @returns {Boolean}
     */
    hasFun : function(funTag){
        var children = this.CD_NODE.children,
            flag = false;
            buttons = [];
        for(var i=0;i<children.length;i++){
            var item = children[i];
            if(item && item.funTag == funTag){
                return true;
            }
        }
        return flag;
    },
    /**
     * 根据功能标识，获取内容标识的集合['sex','age','username']
     * @param tag {String} 功能标识
     * @returns {Array}
     */
    getContentsByFun : function(tag){
        var children = this.CD_NODE.children,
            fun,
            contents = [];
        for(var i=0;i<children.length;i++){
            var item = children[i];
            if(item.funTag == tag){
               fun = item;
               break;
            }
        }
        for(var i=0;i<fun.children.length;i++){
            contents.push(fun.children[i].funTag);
        }
        return contents;
    }
});
/***
 * @class NS.mvc.Model
 * @extends NS.Base
 * mvc中模型层类
 *
 *     var model = new NS.mvc.Model({
 *         serviceConfig : {
 *             'xsHeader_data' : {
 *                 service : "base_Service",
 *                 params : {}
 *             },
 *             'xs_show_data' : {
 *                 service : "xsxx_showData",
 *                 params : {
 *                     age : 12,
 *                     sex : '12121212'
 *                 }
 *             }
 *         }
 *     });
 *       var data1 = model.callService('xsHeader_data',function(data1){
 *              //data1.xsHeader_data 就是你想要的数据
 *       });
 *       var data2 = model.callService([{
 *              key : 'xsHeader_data'
 *       },{
 *              key : 'xs_show_data',
 *              params : {
 *                   age : 12,
 *                   sex : '12121212'
 *              }
 *       }],function(data2){
 *              //data2 ={xsHeader_data : headerdata,xs_show_data : showdata}就是你想要的数据
 *       });
 *       model.callService({
 *              key : 'xs_show_data',
 *              params : {
 *                   age : 12,
 *                   sex : '12121212'
 *              }},function(data3){
 *                  //data3 = {xs_show_data:showdata} 就是需要的数据
 *              });

 *
 */
NS.define('NS.mvc.Model',{
    /***
     * 构造函数
     */
    constructor : function(){
        this.initData.apply(this,arguments);
        this.allServiceConfig = {};
        NS.apply(this.allServiceConfig,this.serviceConfig);
        this.init();
    },
    /**
     * @private
     * @param config
     */
    initData : function(config){
        this.initConnection();
        NS.apply(this,config);
    },
    /***
     * 模型层初始化方法
     * @private
     */
    init : NS.emptyFn,
    /***
     * 初始化{NS.Connection}
     * @private
     */
    initConnection : function(){
        this.connection = new NS.Connection();
    },
    /***
     * 通过key值来获取key值对应的service数据
     *
     *      var model = new NS.mvc.Model({
     *      serviceConfig : {
     *             'xsHeader_data' : {
     *                 service : "base_Service",
     *                 params : {}
     *             },
     *             'xs_show_data' : {
     *                 service : "xsxx_showData",
     *                 params : {
     *                     age : 12,
     *                     sex : '12121212'
     *                 }
     *             },
     *             'xs_add_data' : {
     *                 service : "xsxx_addData",
     *                 params : {
     *                     age : 12,
     *                     sex : '12121212'
     *                 }
     *             }
     *         }});
     *       model.callService('xsHeader_data',function(data){
     *              //data 就是你想要的数据
     *       });
     *       model.callService([{
     *              key : 'xsHeader_data'
     *       },{
     *              key : 'xs_show_data',
     *              params : {
     *                   age : 12,
     *                   sex : '12121212'
     *              }
     *       },'xs_add_data'],function(data){
     *
     *       });
     *
     * @param {Object[]/String} params
     * @param params
     */
    callService : function(params,callback,scope,timeout){
        var me = this,basic = {};
        var process = function(item){
            var obj = {};
            if(NS.isObject(item)){
                var key = item['key'];
                var service = me.getServiceConfig(key);//通过key值获取service配置
                obj[key] = {};
                obj[key]['request'] = service.service;
                obj[key]['params'] = NS.clone(service['params'])||{};//获取基础参数params
                NS.apply(obj[key].params,item['params']);//讲传递的params和配置的params合并
            }else if(NS.isString(item)){
               var service = me.getServiceConfig(item);//通过key值获取service配置
               if(service){
                   obj[item] = {};
                   obj[item]['request'] = service.service;
                   obj[item]['params'] = NS.clone(service.params)||{};//获取传递参数的params
               }
            }
            return obj;
        }
        var obj = Ext.Object;
        if(NS.isArray(params)){
            for(var i= 0,len=params.length;i<len;i++){
                obj.merge(basic,process(params[i]));
            }
        }else if(NS.isString(params)){
            obj.merge(basic,process(params));
        }else if(NS.isObject(params)){
            obj.merge(basic,process(params));
        }
        this.connection.callService(basic,callback,scope,timeout);
    },
    /***
     * 同步调用后台service方法，并返回数据
     * @private
     * @param {Object/String/Array} params
     * @return {Object}
     * @private
     */
    syncCallService : function(params){
        var me = this,basic = {};
        var process = function(item){
            var obj = {};
            if(NS.isObject(item)){
                var key = item['key'];
                var service = me.getServiceConfig(key);//通过key值获取service配置
                obj[key] = {};
                obj[key]['request'] = service.service;
                obj[key]['params'] = service['params']||{};//获取基础参数params
                NS.apply(obj[key].params,item['params']);//讲传递的params和配置的params合并
            }else if(NS.isString(item)){
                var service = me.getServiceConfig(item);//通过key值获取service配置
                if(service){
                    obj[item] = {};
                    obj[item]['request'] = service.service;
                    obj[item]['params'] = service.params||{};//获取传递参数的params
                }
            }
            return obj;
        }
        if(NS.isArray(params)){
            for(var i= 0,len=params.length;i<len;i++){
                NS.apply(basic,process(params[i]));
            }
        }else if(NS.isString(params)){
            NS.apply(basic,process(params));
        }else if(NS.isObject(params)){
            NS.apply(basic,process(params));
        }
        return this.connection.callService(basic,callback,scope);
    },
    /**
     * 单独调用service，直接返回所需数据，不需要再通过key值进行访问
     *
     *        var model = new NS.mvc.Model({});
     *        model.call('basequery',{entityName:'Tbxsjbxx'},function(data){
     *                  //data 就是你所需要的数据
     *        },this);
     *
     * @param {String} key 需要调用的service的key值
     * @param {Function} fun 回调函数
     * @param {Object} scope 函数作用域
     */
    callSingle : function(service,fun,scope){
        this.callService(service,function(data){
             if(NS.isString(service)){
                fun.call(scope||this,data[service]);
             }else if(NS.isObject(service)){
                fun.call(scope||this,data[service['key']]);
             }
        });
    },
    /**
     * 同步调用service方法
     * @param {String} serviceName service名称
     * @param {Object} params 参数对象
     * @return {Object}
     * @private
     */
    syncCall : function(serviceName,params){
        var obj = {
            result : {
                request : serviceName,
                params : NS.encode(params||{})
            }
        };
        var data = this.connection.syncCallService(obj);
        return data.result;
    },
    /***
     * 通过key值获取service配置对象
     * @param ｛String｝key 用来获取service配置的键
     * @return {Object}
     * @private
     */
    getServiceConfig : function(key){
        var service,obj = {};
        if(key){
           var service = this.allServiceConfig[key];
           if(service){
              if(NS.isObject(service)){
                 return service;
              }else if(NS.isString(service)){
                 obj['service'] = service;
                 return obj;
              }
           }else{
               NS.error({
                   sourceClass : 'NS.mvc.Model',
                   sourceMethod : 'getServiceConfig',
                   msg : '找不到请求的service：'+key
               })
           }
        }
        return NS.clone(this.allServiceConfig[key]);
    },
    /**
     * 替换key值映射到的service
     * @param {String} key service 对应的key值
     * @param {Object/String}serviceObj  需要更新的service的配置对象
     *
     *     var model = new NS.mvc.Model({
     *         serviceConfig : {
     *             add : 'add_service'
     *         }
     *     });
     *     model.updateServiceConfig('add','base_addService');//这是修改
     *     model.updateServiceConfig('add',{
     *         service : 'base_addService',
     *         params : {entityName : 'Tbsxs'}
     *     });
     *     model.updateServiceConfig('update','update_service');//这是修改
     *
     *     也可以用这种形式修改和添加
     *
     *     model.updateServiceConfig({
     *         'add' : 'add_service',
     *         'update' : 'update_service'
     *     });
     *
     */
    updateServiceConfig : function(key,serviceObj){
        var serviceConfig = this.allServiceConfig,i;
        if(NS.isString(key)){
            serviceConfig[key] = serviceObj;
        }else if(NS.isObject(key)){
            for(i in key){
                serviceConfig[i] = key[i];
            }
        }
    },
    /**
     * 更新key值对应的service的请求参数
     * @param {String} key 需要更新的service的key值
     * @param {Object} params 需要更新的参数对象，更新的参数遵循（有则覆盖，无则添加的规则）
     */
    updateServiceParams : function(key,params){
        var serviceConfig = this.allServiceConfig, i,service = serviceConfig[key];
        if(NS.isString(service)){
            serviceConfig[key] = {service : service,params : params};
        }else if(NS.isObject(service)){
            if(service.params){
               NS.apply(service.params,params);
            }else{
               service.params = params;
            }
        }
    }
});
/***
 * @class NS.mvc.View
 * @extends NS.Base
 * mvc中视图层类
 *
 */
NS.define('NS.mvc.View',{
    /***
     * 构造函数
     */
    constructor : function(){
        this.initComponent.apply(this,arguments);
        this.init();
    },
    /***
     * 在init方法内完成对页面组件的组装
     */
    init : NS.emptyFn,
    /***
     * 初始化组件
     * @private
     */
    initComponent : function(){
        this.compManager = {};
    },
    /**
     * 设置页面渲染的最顶层组件
     */
    setComponent : function(component){
        this.component = component;
    },
    /**
     * 将组件注册进入View层的组件管理器中
     *
     *      var view = new NS.mvc.View();
     *      view.register({
     *          grid : new NS.component.grid.Grid()
     *      });
     *      view.register({
     *          grid : new NS.grid.Grid(),
     *          form : new NS.form.Form()
     *      });
     *      //可以这样做
     *      view.getGrid();//return NS.grid.Grid();
     *      view.getForm();//return NS.form.Form();
     *      //你也可以这样做
     *      view.get('grid');//return NS.grid.Grid(),
     *
     * @private
     * @param {Object} component 组件或者组件集合
     */
    register : function(component){
        var ST = NS.String,i;
        if(this.compManager){
            NS.apply(this.compManager,component);
        }
        if(arguments.length == 1){
            if(NS.isObject(component)){
                for(i in component){
                    (function(me,property,component){
                        //根据注册进来的组件的名称，生成get方法
                        me['get'+ST.capitalize(property)] = function(){
                            return component[property];
                        };
                    })(this,i,component);
                }
            }
        }else if(arguments.length == 2){//如果参数个数为2，那么参数的形式可以是
                                        // ('name',new NS.component.Component())
            if(NS.isString(component)){
                (function(me,i,component){
                    //根据注册进来的组件的名称，生成get方法
                    me['get'+ST.capitalize(i)] = function(){
                        return component;
                    };
                })(this,arguments[0],arguments[1]);
            }
        }

    },
    /**
     * 根据传递的注册进来的组件名称来获取组件
     *      var view = new NS.mvc.View();
     *      view.register({
     *          grid : new NS.grid.Grid()
     *      });
     *      view.register({
     *          grid : new NS.grid.Grid(),
     *          form : new NS.form.Form()
     *      });
     *      //你可以这样做
     *      view.getGrid();//return NS.grid.Grid(),
     *      //你也可以这样做
     *      view.get('grid');//return NS.grid.Grid(),
     *
     * @private
     * @param {String} cname
     * @return {NS.Component}
     */
    get : function(cname){
        return this.comManager[cname];
    },
    /**
     * 获取底层类库组件
     * @private
     * @return {Ext.Component}
     */
    getLibComponent : function(){
        return this.component.getLibComponent();
    }
});
/***
 * @class NS.util.ComponentInstance
 * @extends NS.Base
 * @singleton
 *    用以获取包装对象
 * @private
 */
NS.define('NS.util.ComponentInstance',{
    singleton : true,
    CM : NS.ClassManager,
    /**
     * 构造函数
     */
    constructor : function(){
        this.callParent(arguments);
        this.instanceMap = {
            'Ext.Component' : 'NS.Component',
            'Ext.container.Container' : 'NS.container.Container',
            'Ext.tab.Panel' : 'NS.container.TabPanel',
            'Ext.panel.Panel' : 'NS.container.Panel',
            'Ext.form.Panel' : 'NS.form.BasicForm',

            'Ext.toolbar.ToolBar' : 'NS.toolbar.Toolbar',
            'Ext.button.Button' : 'NS.button.Button',
            'Ext.grid.Panel' : 'NS.grid.Grid',
            'Ext.menu.Menu' : 'NS.menu.Menu',
            'Ext.window.Window' : 'NS.window.Window',
            /**
             * grid
             */
            'Ext.grid.column.Column' : 'NS.grid.Column',
            'Ext.grid.column.ButtonColumn' : 'NS.grid.Column',
            'Ext.grid.column.LinkColumn' : 'NS.grid.Column',
            'Ext.grid.column.ProgressColumn' : 'NS.grid.Column',
            'Ext.grid.RowNumberer' : 'NS.grid.Column',






            /**
             * FieldInstance Field组件的映射关系
             */
            'Ext.form.field.Base' : 'NS.form.field.BaseField',
            'Ext.form.field.Text' : 'NS.form.field.Text',
            'Ext.form.field.Checkbox' : 'NS.form.field.Checkbox',
            'Ext.form.field.ComboBox' : 'NS.form.field.ComboBox',
            'Ext.form.field.Date' : 'NS.form.field.Date',
            'Ext.form.field.Display' : 'NS.form.field.Display',
            'Ext.form.field.File' : 'NS.form.field.File',
            'Ext.form.field.Number' : 'NS.form.field.Number',
            'Ext.form.field.Radio' : 'NS.form.field.Radio',
            'Ext.form.field.TextArea' : 'NS.form.field.TextArea',
            'Ext.ux.comboBoxTree' : 'NS.form.field.ComboBoxTree',
            'Ext.form.field.Time' : 'NS.form.field.Time',
            'Ext.form.CheckboxGroup' : 'NS.form.field.CheckboxGroup',
            'Ext.form.RadioGroup' : 'NS.form.field.RadioGroup',
            'Ext.form.field.ForumSearch':'NS.form.field.ForumSearch',
            'Ext.form.FieldSet':'NS.form.FieldSet',
            'Ext.form.field.HtmlEditor':'NS.form.field.HtmlEditor'
        };
        this.fieldInstanceMap = {

        };
    },
    /**
     * 获取组件的实例
     * @param {Ext.Component} component
     * @return {NS.Component}
     * @private
     */
    getInstance : function(component){
//       var NSCom = this.instanceMap[component.$className];
       if(component.NSContainer){
          return component.NSContainer;
       }else{
          var NSCom = this.CM.create(this.instanceMap[component.$className],{});
          NSCom.setComponent(component);
          component.NSContainer = NSCom;
          return NSCom;
       }
    }
});
/**
 * 文本统计组件。
 * 
 * @class NS.appExpand.TextStatistics
	
	cData = {
				height:"60",
				width:"80%",
				htmlstring :"上述变更数据，已同步数据节点 {0} 处，被动同步数据节点数 {1} 处，未完成同步数据节点数 {2} 处。",
				showMaxItems:0, 
	            items : [
	            	{ text : '286', params : { name : 'zhangsan', age : 12 } }, 
	            	{ text :'224', params : { name : 'lisi', age : 12 } }
	            ] 
            }
 */
NS.define("NS.appExpand.TextStatistics", {
			extend : 'NS.Component',
			initComponent : function(obj) {
				this.config = obj;
				this.callParent();
				this.createComponent(obj);
			},
			initConfigMapping : function() {
				this.callParent();
				this.addConfigMapping(
		            { htmlstring : {name : 'htmlstring'},
		              items:{name:'items'}
		            }
		        );
			},
			initEvents : function() {
				this.callParent();
				/*this.addEvents(
				*//**
				 * @click
				 *//*
				'linkclick'
				);*/
			},
			/**
			 * @private
			 * @param {Object}
			 *            cData
			 */
			createDom : function(cData) {
				var me = this,
                    doc = document;
				// 创建一个组件内的父节点div，以便挂接到外界的DOM节点上，完成组件的相互组装。
				var interfaceDIV = doc.createElement('DIV');
				interfaceDIV.style.width = '90%';
				interfaceDIV.className = 'opTextRoot';
				var itemsContent = cData.items;
				var htmlString = cData['htmlstring'];
				// 获取要显示的最大连接数。
				var showMaxItems = this.showMaxItems = cData['showMaxItems']||itemsContent.length;
				// 正则表达式，分割htmlString字符串。
				var strs = htmlString.split(/\{[0-9]*\}/);
				// 循环数据和分割后得到的字符串数据，来创建div子节点元素。
				for (var index in strs) {
					if (index > showMaxItems) {
						break;
					}
					interfaceDIV.appendChild(doc.createTextNode(strs[index]));
					// 如果有连接数据，则添加连接元素。
					if (index < itemsContent.length) {
						(function(index, interfaceDIV, item) {
								var a = document.createElement('A');
								a.href = "#";
								a.id ="statistics"+index+id;
                                if(!item.noEvent){
                                    a.onclick = function() {
                                        me.fireEvent('linkclick', item.params,item.text);// 触发弹窗事件
                                        return false;
                                    };
                                }
								a.appendChild(document.createTextNode(itemsContent[index]['text']));
								interfaceDIV.appendChild(a);
							})(index, interfaceDIV, itemsContent[index]);
					}
				}
				this.myDom = interfaceDIV;
			},
			/**
			 * 创建容器组件，将createDOM中创建的节点放到这个容器中。
			 * @param {Object} obj
			 */
			createComponent : function(obj){
				var me = this;
				this.createDom(obj);
				var basic = {
					width:obj.width||'100%',
					height:obj.height||'100%',
					listeners : {
						'afterrender' : function() {
							this.el.appendChild(me.myDom);
						}
					}
				};
				this.component = new Ext.container.Container(basic);
			},
			/**
			 * 将创建的原生dom节点渲染到制定的dom节点上。
			 * 
			 * @param {}
			 *            id 该参数可以是dom节点的id，也可以是dom对象。
			 */
			render : function(id) {
				this.component.render(id);
			},
			/**
			 * 刷新相应下标连接上的显示数据。
			 * @param {Object} obj
			 * 			被刷新数据
			 
			 	var obj = [{
			 		index : 0,
			 		text :"289"
			 	}]
			 */
			refreshByIndex : function(obj){
				if(obj instanceof Array){
					for(var i=0;i<obj.length;i++){
						var domid = "statistics"+obj[i].index+id;
						var extDom = Ext.get(domid);
						extDom.dom.innerHTML = obj[i].text;
						this.config.items[obj[i].index].text = obj[i].text;
					}
				}
				
			},
			/**
			 * 重新刷新组件
			 * @param {Object} obj
			 * 				使用数据刷新组件
			 */
			refresh : function(obj){
				Ext.fly(this.myDom).remove();
				this.createDom(obj);
				this.component.el.appendChild(this.myDom);
                this.config = obj;
			},
            /**
             * 根据下标获取值。
             * @param index
             * @return {String}
             */
            getDataByIndex : function(index){
                return this.config.items[index].text;
            }
		});/**
 * 省市地级联菜单组件。
 * @class NS.appExpand.Provinces2Cities
 */
NS.define('NS.appExpand.Provinces2Cities',{
	extend:'NS.Component',
	initComponent:function(obj){
		this.callParent();
		
	},
	initConfigMapping:function(){
		this.callParent();
	},
	initEvents:function(){
		this.callParent();
	},
	packEvents: function(){
		this.callParent();
	}
	
});/**
 * 组织结构级联查询组件。
 * @class NS.appExpand.CascadeQuery
 */
NS.define('NS.appExpand.CascadeQuery',{
	extend:'NS.container.Container',
	initComponent:function(obj){
        this.holeConfig = obj;
		this.callParent();
        this.translateData(obj);
        this.createContainer(obj);
        this.bindComboboxEvent();
	},
	initConfigMapping:function(){
		this.callParent();
		this.addConfigMapping({
                crosswise : {name :'crosswise'},
            	holistic : {name : 'holistic'},
                cascadeType : {name :'cascadeType'},
            	data : {name :'data'}
        });
	},
	initEvents:function(){
		this.callParent();
	},
	packEvents: function(){
		this.callParent();
	},
	createContainer : function(obj){
        /*是否横向参数 如果未定义或者定义为true 布局的值设为column 否则设置为table;*/
        var isCrosswise = typeof obj.crosswise == 'undefined' || obj.crosswise==true;
            layout = isCrosswise ? 'column':'anchor',
            fsHeight = isCrosswise ? (obj.height||22):(obj.height||obj.cascadeType.length*26),
            defaults = isCrosswise ? {}:{anchor: '100%'};
		this.component = Ext.create('Ext.form.FieldSet', {
            layout: layout,
            defaults:defaults,
            padding: 0,
            margin: '0 0 0 0',
            width: obj.width||200,
            height: fsHeight,
            border: obj.border||false
        });
        this.createComboboxs(obj);
	},
    /**
     * @private
     * @param {Object} obj待转换数据
     *          将转换后的数据作为obj的一个属性。
     */
    translateData : function(obj){
        var datacfg = obj.data,nodeHash = {},rootId = 0;
        obj.nodeHash = {};//传递进来的数据，经过转换后变成nodehash座位obj的一个属性。
        obj.firstCCData = [];//第一层的数据。
        if(datacfg instanceof Array){
            if(datacfg.length==0){
                return ;
            }
            for(var i =0;i<datacfg.length;i++){
                var dataObj = datacfg[i];
                var node = {
                    pid : dataObj.fjdId||dataObj.fjdid,
                    text : dataObj.mc,
                    expanded : true,
                    leaf : true,
                    children : [],
                    id : dataObj.id,
                    cc : dataObj.cc,
                    cclx : dataObj.cclx,
                    qxm : dataObj.qxm
                }
                nodeHash[dataObj.id] = node;
                if(node.cclx==obj.cascadeType[0].cclx){
                    obj.firstCCData.push(node);
                }
            }
            for(var key in nodeHash){
                var hashNode = nodeHash[key];
                if(nodeHash.hasOwnProperty(hashNode.pid)){
                    nodeHash[hashNode.pid].children.push(hashNode);
                    nodeHash[hashNode.pid].leaf = false;
                }else{
                    rootId=hashNode.id;
                }
            }
            obj.nodeHash = nodeHash;
        }
    },
    /**
     * @private
     * @param {Object} obj
     *          根据配置项创建下拉表。
     */
    createComboboxs : function(obj){
        var me = this;
        var cascadeType = obj.cascadeType;
        for(var i=0;i<cascadeType.length;i++){
            var typeObj = cascadeType[i];
            (function (i,labelName,valueName,displayName,num,cclx,obj){
                if(i==0){
                    var comboboxStore = Ext.create('Ext.data.Store', {
                        fields: ['id', 'text','cclx','qxm'],
                        data : obj.firstCCData
                    });
                }else{
                    var comboboxStore = Ext.create('Ext.data.Store', {
                        fields: ['id', 'text','cclx','qxm']
                    });
                }
                var testCombobox = Ext.create('Ext.form.field.ComboBox', {
                    fieldLabel: labelName,
                    labelWidth:30,
                    store: comboboxStore,
                    queryMode: 'local',
                    columnWidth:1/num,
                    displayField: displayName,
                    valueField: valueName,
                    editable : false,
                    name:cclx,
                    emptyText :'请选择...'
                });
                me.component.add(testCombobox);
            })(i,typeObj.labelName,typeObj.valueName||"id",typeObj.displayName||"text",cascadeType.length,typeObj.cclx,obj);
        }
    },
    /**
     * @private
     *      为级联组件中各下拉组件绑定事件。
     */
    bindComboboxEvent : function(){
        var me = this;
        var i = 0;
        for(;i<this.holeConfig.cascadeType.length;i++){
            (function(i,length){
                var aComponent = me.component.getComponent(i);
                if(i!=(length-1)){
                    aComponent.addListener("change",function(com,newValue,oldValue,opts){
                        // 将下一个cc的组件值设置为空
                        var nextComponent = me.component.getComponent(i+1);
                        nextComponent.setValue(null);
                        // 从nodehash中根据id获取响应的childrens
                        var storeArray = typeof me.holeConfig.nodeHash[newValue]=='undefined'?null:
                            me.holeConfig.nodeHash[newValue].children;
                        // 根据获取的children刷新下一个组件的store
                        if(storeArray==null){
                            nextComponent.getStore().loadData([]);
                        }else{
                            nextComponent.getStore().loadData(storeArray);
                        }

                    });
                }
            })(i,this.holeConfig.cascadeType.length);
        }
    },
    /**
     * 将创建的原生dom节点渲染到制定的dom节点上。
     * @param id
     *          被渲染的dom节点id 或者 dom节点。
     */
    render : function(id) {
        this.component.render(id);
    },
    /**
     *  获取级联组件的值。
     *  @return {Array}
     *      各组件相应的值
     *
     *      var result ={
     *          cclx :"yx", //院系
     *          id :101010101l,// 主键
     *          text : "建筑工程系"   // 显示值
     *      }
     *
     */
    getValue : function(){
        var values = [],
            items = this.component.items.items,
            itemData;
        for(var i=0;i<items.length;i++){
            itemData = items[i];
            if(itemData.displayTplData.length == 0){
               break;
            }
            values.push(itemData.displayTplData[0]);
        }
        return values;
    }
});/**
 * 定义实体导出组件类
 * @author wangyt
 * @class  NS.bussiness.EntityExport
 */
NS.define('NS.bussiness.EntityExport',{
    singleton : true,
    /**
     * 根据实体属性表以及NS.grid.Grid对象导出excel
     *
     *      NS.bussiness.EntityExport.exportExcel({
     *          grid : grid,
     *          entityName : 'TbXsjbxx',
     *          title : '导出Excel组件的',
     *          fileName : '',
     *          controller : this
     *      });
     *
     * @param {Object} config 配置对象
     */
    exportExcel : function(config){
        if(config.grid){
            var grid = config.grid,title = config.title,controller = config.controller,
                entityName = config.entityName,queryParams = grid.getQueryParams(),
                entityCN = '\"'+title+'\"';//设定标题
            var url = grid.key;//key值
            if(NS.isObject(url)){
                url = url.key
            }
            NS.apply(queryParams,{start:0,limit : 10000000});
            var service = controller.model.getServiceConfig(url);
            service = NS.isString(service)?service:service.service;
        }else{
            var title = config.title,controller = config.controller,
                entityName = config.entityName,queryParams = config.queryParams,
                serviceKey = config.serviceKey,//service对应的key值
                entityCN = '\"'+title+'\"';//设定标题
            NS.apply(queryParams,{start:0,limit : 10000000});
            var service = controller.model.getServiceConfig(serviceKey);
            service = NS.isString(service)?service:service.service;
        }
        var components = "components={'exp':{'request':\""+service+"\",'params':{}}}"
        var win = Ext.create('Ext.window.Window', {
            width : 100,
            height : 85,
            //margin : '5 10 5 10',
            closable : true,
//			preventHeader : true,
            modal : true,
            autoShow : true,
            autoDestroy : false,
            defaultType : 'radiofield',
            items : [{
                // xtype:'radio',
                name : 'exportType',
                boxLabel : '全部字段',
                inputValue : '1',
                id : 'state'
            }, {
                // xtype:'radio',
                name : 'exportType',
                boxLabel : '显示字段',
                inputValue : '0',
                id : 'state1'
            }],
            defaults : {
                listeners : {
                    click : {
                        element : 'el',
                        fn : function() {
                            var type = config.grid?Ext.getCmp('state').getGroupValue():'1'; // 待改
                            var queryCondition = Ext.encode(queryParams);
                            //添加代理数据请求参数
                            var proxyP = "proxyMenuId="+MainPage.proxyMenuId;
                            if(MainPage.proxyUserId) proxyP += "&proxyUserId"+MainPage.proxyUserId;

                            if ('0' == type) { // 只导出显示列
                                var showField = grid.getShowColumnNames();
                                // alert(showField.length)
                                var params = [];
                                for (var i = 0; i < showField.length; i++) {
                                    var field = showField[i].dataIndex
                                        .split(".");
                                    params.push("\'"+field+"\'");
                                }
                                var exportFields = '{"fields":['
                                    + params.join(",") + ']';
                                exportFields += ',"entityName":\"' + entityName+'\"';
                                exportFields += ',"entityCN":' + entityCN + '}';

                                window.location = 'baseAction.action?exportFields='
                                    + exportFields
                                    + "&queryCondition="
                                    + queryCondition
                                    +"&"+components
                                    +"&"+proxyP;
                                win.close();
                            } else if ('1' == type) { // 导出全部列
                                var exportFields = '{';
                                exportFields += 'fields:["all"]';
                                exportFields += ',entityName:"'
                                    + entityName.replace(/\"/g, "") + '"';
                                exportFields += ',entityCN:"'
                                    + entityCN.replace(/\"/g, "") + '"}';

                                window.location = 'baseAction.action?exportFields='
                                    + exportFields
                                    + "&queryCondition="
                                    + queryCondition
                                    +"&"+components
                                    +"&"+proxyP;
                                win.close();
                            }
                        }
                    }
                }
            }
        });
    }
});
(function(){
    var alias = NS.Function.alias;
    /**
     * @member NS
     * @method entityExcelExport
     * @inheritdoc NS.bussiness.EntityExport#exportExcel
     */
    NS.entityExcelExport = alias(NS.bussiness.EntityExport,'exportExcel');
})();