/**
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
