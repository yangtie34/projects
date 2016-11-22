/**
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
})();