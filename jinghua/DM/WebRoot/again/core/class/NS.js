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
})();