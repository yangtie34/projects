/**
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
})();