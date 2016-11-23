/**
 * @class EG
 * @author bianrongjun
 * EG 引擎类(Engine).
 */
(function(){


	var EG=window.EG= function EG(){

	};

	EG._className="EG";
	EG.debug = true;

//	var wE=window.onerror;
//	window.onerror = function(sMsg, sUrl, sLine) { // 错误提示
//		if(wE) wE();
//		EG.onError(sMsg, sUrl, sLine);
//	};

	/**
	 * ONERROR
	 * @param sMsg
	 * @param sUrl
	 * @param sLine
	 */
	EG.onError=function(sMsg, sUrl, sLine){
		if(EG.debug){
			var errorMsg = "<div style='text-align:left'><b>原因</b>:" + sMsg + "<br/>";
			errorMsg += "<b>行数</b>:" + sLine + "<br/>";
			errorMsg += "<b>URL</b>:" + sUrl + "<br/></div>";
			if(EG.Locker){
				EG.Locker._lock=true;
				EG.Locker.message({message:errorMsg,closeable:false,force:true});
			}
			else alert(errorMsg);
		}
	};

	/**
	 * @property {HTMLDocument} document高速索引
	 */
	EG.doc=document;

	/**
	 * @property {HTMLBodyElement} document.body高速索引
	 */
	EG.body=null;

	/**
	 * 获取Body
	 * @return {HTMLBodyElement}
	 */
	EG.getBody=function(){
		if(!EG.body) EG.body=EG.doc.body;
		return EG.body;
	};

	/**
	 * 返回非空的数值
	 * @returns {*}
	 */
	EG.unnull=function(){
		for(var i= 0;i<arguments.length;i++){
			if(arguments[i]!=null) return arguments[i];
		}
		return null;
	};

	/**
	 * 如果对象为空返回默认值,否则返回对象
	 * @param {*} o 对象
	 * @param {*} d 默认值
	 * @return {*}
	 */
	EG.n2d=function(o,d){
		if(o==null) return d;
		else return o;
	};

	/**
	 * 函数转对象,如果对象为null返回null,如果对象是函数返回函数执行结果，否则返回对象
	 * @param {*} o 对象
	 * @return {*}
	 */
	EG.f2o=function(o){
		if(o==null) return null;
		if(typeof(o)=="function"){
			return o();
		}else{
			return o;
		}
	};

	/**
	 * 获取脚本路径
	 * @param {String} scriptName 脚本名称
	 * @returns {String}
	 */
	EG.getScriptPath = function(scriptName) {
		var js = EG.doc.scripts;
		var jsPath;
		for ( var i = js.length; i > 0; i--) {
			if (js[i - 1].src.indexOf(scriptName) > -1) {
				jsPath = js[i - 1].src.substring(0, js[i - 1].src.lastIndexOf("/") + 1);
				if (jsPath.indexOf("http://") >= 0) {
					jsPath = jsPath.substring(jsPath.indexOf("/", 10));
				}
				return jsPath;
			}
		}
		return null;
	};

	var scriptPath="eg";

	/**
	 * 基础路径
	 * @property {String}
	 */
	EG.basePath = window._EG_BASEPATH_||EG.getScriptPath(scriptPath);

	EG.contextPath="";
//	/**
//	 * 继承
//	 * 仅仅对原型链上的function进行继承,使子类与父类prototype指向一致
//	 *
//	 * @param {Class} sCls 子类
//	 * @param {Class|Object} pCls 父对象(属性集)
//	 * @param {Boolean?} override
//	 * @param {Boolean?} withNative 是否带原生方法
//	 * @param {Boolean?} forceMark 是否强迫标注方法所属类
//	 */
//	EG.extend = function(sCls, pCls, override,withNative,forceMark) {
//		// 默认为不覆盖
//		EG.n2d(override,false);
//
//		var pProp=pCls;
//
//		//类
//		if(typeof(pCls)=="function") {
//			pProp=pCls.prototype;
//		}else if(typeof(pCls)=="object"){
//			pCls=null;
//		}else{
//			throw new Error("EG#extend:父类型错误:" + typeof (pCls));
//		}
//
//		EG._extend(sCls.prototype,pProp,override,withNative,forceMark,pCls,sCls);
//		return sCls;
//	};
//
//	/**
//	 * 方法用引用,字面量递归继承,其它类型Clone
//	 */
//	EG._extend=function(sObj,pObj,override,withNative,forceMark,pClass,sClass){
//		for(var k in pObj){
//			var pV=pObj[k];
//			var sTp=typeof(sObj[k]);
//			if (!override&&sTp!="undefined") continue;
//			if(typeof(pV)=="function"){
//				if(!withNative&&(k=="initConfig"||k=="getSuperclass"||k=="callSuper"||k=="getClass")){
//					continue;
//				}
//				//第一次被继承时标注原始类=>用来callSuper时使用
//				if(!pV._class){
//					if(pClass){
//						pV._class=pClass;
//					}else if(forceMark&&sClass){
//						pV._class=sClass;
//					}
//				}
//				sObj[k]=pV;
//			//如果属性是对象进行copy
//			}else if(pV&&pV.constructor==Object){
//				sObj[k]={};
//				EG._extend(sObj[k],pV,override,withNative,forceMark,pClass,sClass);
//				//非function类型数据使用clone
//			}else{
//				sObj[k]=EG.clone(pV);
//			}
//		}
//	}

	/**
	 * 复制
	 * @param {Object} obj 被赋值对象
	 * @param {Object} props 待赋值对象
	 * @param {Boolean?} override 是否覆盖
	 * @param {Boolean?} clone 是否使用clone
	 * @return {Object}
	 */
	EG.copy = function(obj, props, override,clone) {
		for (var key in props){
			if (override || typeof (obj[key]) == "undefined"){
				if(clone){
					obj[key] = EG.clone(props[key]);
				}else{
					obj[key] = props[key];
				}
			}
		}
		return obj;
	};

	/**
	 * 获取对象类型
	 * @param {*} o 任意类型
	 * @return {String}
	 */
	EG.getType=function(o){
		return Object.prototype.toString.call(o).slice(8,-1).toLowerCase();
	};


	EG.types={'undefined':1,'null':1,'number':1,'boolean':1,'string':1,'array':2,'function':3,'date':4};
	/**
	 * 克隆
	 * @param {*} dObj 目标
	 * @param {*} sObj 源
	 * @param {*} key 键值
	 * @private
	 */
	EG._clone=function(dObj,sObj,key){
		var val=sObj[key];
		var type=EG.types[EG.getType(val)];
		if(1==type) {			// undefined, null, number, boolean, string
			dObj[key]=val;
		}else if(4== type) {	// date
			dObj[key]=new Date();
			dObj[key].setTime(val.getTime());
		} else {				// object, array, or function
			dObj[key]=EG.clone(val);
		}
	};

	/**
	 * 克隆
	 * @param {*} obj 对象
	 * @returns {*}
	 */
	EG.clone=function(obj){
		if(obj==null) return null;
		if(typeof(obj)=="undefined") throw new Error("EG.clone#待clone参数未定义");

		var cObj,typeNum, i,il,fn_body, fn_args;
		var type=EG.getType(obj);
		typeNum = EG.types[type];
		cObj=obj;
		if (!typeNum) {
			// object
			if(type!="object"){
			//
			}else{
				if(obj==null||obj.constructor==null) alert(obj);
				cObj=new obj.constructor();	//?
				for(i in obj) EG._clone(cObj,obj,i);
			}
		// array
		}else if(2==typeNum) {
			cObj=[];
			for(i=0,il=obj.length;i<il;i++)	EG._clone(cObj,obj,i);

		// function
		}else if(3==typeNum) {
			cObj=obj+'';
			fn_args = cObj.substring(cObj.indexOf('(') + 1, cObj.indexOf(')')).replace(/\s/g, '');
			fn_body = cObj.substring(cObj.indexOf('{') + 1, cObj.lastIndexOf('}'));
			if ('[native code]' == fn_body) throw new Error('无法clone native方法 : ' + cObj);
			if (0<fn_args.il) {
				fn_args = fn_args.split(',');
				fn_args[fn_args.il] = fn_body;
				// let window point to this
				cObj = Function.constructor.apply(win, fn_args);
			} else
				cObj = new Function(fn_body);
		// date
		} else if (4 == typeNum) {
			cObj = new Date();
			cObj.setTime(obj.getTime());

		}//else{}// number, boolean, string

		return cObj;
	};

	EG.readyEvents={};
	/**
	 * 类加载完成后执行
	 * @param {String} className
	 * @param {Function} callback
	 */
	EG.onReady=function(className,callback){
		var c=EG.findClass(className);
		if(c){
			callback(c);
		}else{
			if(!EG.readyEvents[className]){
				EG.readyEvents[className]=[];
			}
			EG.readyEvents[className].push(callback);
		}
	};

	/**
	 * 类路径识别器
	 * @param className 模块名
	 * @return {String}
	 */
	EG.classUrlFinder=function(className){
		if(!EG.String.startWith(className,"EG")) throw new Error("模块名前缀不合法:"+className);

		var res= className.split(".");
		res.shift();
		var classFileName=res[res.length-1];
		res.pop();

		return EG.basePath+"src"+"/"+res.join("/").toLowerCase()+"/"+classFileName+".js";
	};

	/**
	 * 加载类
	 * @param {String} className 类名
	 * @param {Function} callback 加载后的回调
	 * @param {Function?} classUrlFinder 类路径寻找器
	 */
	EG.classLoader=function(className,callback,classUrlFinder){
		if(!classUrlFinder){
			classUrlFinder=EG._finders[className.substring(0,className.indexOf("."))];
		};
		//如果已加载直接执行
		var c;

		if((c=EG.findClass(className))&&c._className){
			return callback(c,className);
		}else{
			if(callback){
				EG.onReady(className,function(c){
					callback(c,className);
				});
			}
		}

		EG.Ajax.send({
			url:classUrlFinder(className),
			callback:function(text){
				eval(text);
			}
		});
	};

	//
	EG._finders={};
	/**
	 * 设置寻类器
	 * @param classPreName
	 * @param classUrlFinder
	 */
	EG.setFinders=function(classPreName,classUrlFinder){
		EG._finders[classPreName]=classUrlFinder;
	};
	EG.setFinders("EG",EG.classUrlFinder);

	/**
	 * 原生方法
	 */
	EG.nativeMethod={
		/**
		 * 初始化
		 * @param cfg
		 */
		initConfig:function(cfg){
			//如果this中含有xxConfig字样的成员,将cfg["xx"]赋值给xxConfig
			if(cfg){
				for(var key in cfg){
					var cK=key+"Config";
					if(typeof(this[cK])!="undefined"){
						this[cK] = cfg[key];
					}else{
						this[key] = cfg[key];
					}
				}
			}
		},

		/**
		 * 获取父类
		 */
		getSuperclass:function(){
			return this.getClass()._superClass;
		},

		/**
		 * 调用父类#不能被me调用
		 * 没有name时,调用构造函数
		 * 采用方法名标记进入的层次
		 * @param name 方法名
		 * @param args 参数
		 */
		callSuper:function(name,args){
			var o;

			//找到谁调用的callSuper,如果被me等闭包函数调用无法找到本层调用函数，如果被上层调用下层会乱
			var fn=arguments.callee.caller;

			//类名||或者所属类
			var sc=(fn._className?fn:fn._class)._superClass;

			if(!sc) throw new Error("没有父类");

			//分析方法名
			var methodName,params;
			if(arguments.length==0){
				methodName="$constructor";
				params=[];
			}else if(arguments.length==1){
				if(typeof(arguments[0])=="string"){
					methodName=arguments[0];
					params=[];
				}else if(EG.isArray(arguments[0])){
					methodName="$constructor";
					params=arguments[0];
				}
			}else{
				methodName=arguments[0];
				params=arguments[1];
			}

			//传递执行
			if(methodName=="$constructor"){
				//alert("执行前:sc:"+sc._className+" 相等?"+(sc._constructor==c._constructor))
				o=sc.apply(this,params);
			}else{
				o=sc.prototype[methodName].apply(this,params);
			}

			return o;
		},

		/**
		 * 获取到类
		 * @returns {Function}
		 */
		getClass:function(){
			return this._class;
		}
	};

	/**
	 * 类本体
	 * @returns {Function}
	 */
	EG._newConst=function(){
		return function(){
			//类本体
			var c=arguments.callee;
			var args=arguments;
			//初始化config中的固定变量到this中
			if(c._config){
				EG.copy(this,EG.clone(c._config));
			}

			//构造函数执行
			if(c._constructor){
				if(c._beforeConstructor){
					var me=this;
					return c._beforeConstructor.apply(this,[function(){
						return c._constructor.apply(me,args);
					}]);
				}else{
					return c._constructor.apply(this,args);
				}
			}

		};
	};

	/**
	 * 定义类过程
	 * @param cfg
	 * @param name
	 * @returns {Function}
	 * @private
	 */
	EG._defineClass=function(cfg,name){
		var statics		=cfg["statics"],
			config		=cfg["config"]||{},
			constructor	=cfg["constructor"],
			extend		=cfg["extend"],
			beforeConstructor=cfg["beforeConstructor"]
		;

		//保留预定义数据
		if(config){
			config=EG.clone(config);
		}

		//判断是否为Object的默认构造函数
		if({}.constructor==constructor) constructor=null;

		//定义命名空间
		//先定义A.B.C 后定义A.B ,需要不更改原A.B
		var clazz=name?null:EG.findClass(name);
		if(clazz){
			if(clazz._className){
				throw new Error("不能重新定义类:"+name);
			}
		}else{
			var _const=EG._newConst();
			clazz=!name?_const:EG.defineNamespace(name,_const);
		}

		//挂载使callSuper可识别
		if(constructor) constructor._class=clazz;
		//关联重要参数
		clazz._constructor		=constructor;
		clazz._config			=config;
		clazz._beforeConstructor=beforeConstructor;
		if(name){
			clazz._className=name;
		}

		//父类
		if(extend){

			//继承核心
			var f=function(){};
			f.prototype=extend.prototype;
			clazz.prototype=new f();

			//原型链继承
			clazz._superClass=extend;

			//参数继承
			if(extend._config){
				EG.copy(config,EG.clone(extend._config),false);
			}
		}

		var prototyps	=clazz.prototype;

		//原生方法
		EG.copy(prototyps,EG.nativeMethod);
		prototyps._class=clazz;

		//配置方法
		for(var key in cfg){
			if(key!="extend"&&
				key!="statics"&&
				key!="config"&&
				key!="constructor"&&
				key!="beforeConstructor"&&
				key!="require"){
				//此处若是状态变量则有风险。其作用于原型链
				prototyps[key]=cfg[key];

				if(typeof(cfg[key])=="function"){
					prototyps[key]._class=clazz;
				}
			}
		}

		//copy类的静态方法
		if(statics) EG.copy(clazz,statics,true);

		if(extend){
			//继承后动作
			if(extend.afterExtend){
				extend.afterExtend(clazz);
			}
		}

		//定义后执行
		if(name&&EG.readyEvents[name]){
			var ev;

			//保证多线程同时ready一个class时，用shift
			while((ev=EG.readyEvents[name].shift())){
				ev(clazz);
			}
		}

		return clazz;
	}

	/**
	 * 定义类
	 * 名称为空时创建一个匿名类
	 * 默认使用{EG.classLoader}加载类
	 *
	 * @param {String||Object} name 名称||cfg配置
	 * @param {Object?} cfg 配置
	 * @param {Function?} classLoader 类加载器
	 * @returns {Class}
	 */
	EG.define= function(name,cfg,classLoader){
		if(typeof(name)!="string"){cfg=arguments[0];classLoader=arguments[1];name=null;}
		classLoader=classLoader||EG.classLoader;

		var reqs	=[],
			extend	=cfg["extend"],
			require	=cfg["require"];

		//如果集成类是字串类型,进行load
		if(typeof(extend)=="string"){
			reqs.push(extend);
		}

		//如果含有require
		if(require){
			if(typeof(require)=="string") reqs.push(require);
			else reqs=reqs.concat(require);
		}

		//依赖性加载，无直接返回
		if(reqs.length>0){
			//TODO LoadRequeire 将来变换为EG.loadRequire
			var loadRequire=function(cls,clsName){
				if(clsName&&extend&&clsName==extend){
					cfg["extend"]=cls;
				}
				if(reqs.length==0){
					//alert("require完毕:准备定义:"+name);
					return EG._defineClass(cfg,name);
				}

				var req=reqs.shift();//alert("加载Require:"+req);
				var c=EG.findClass(req);
				if(c){
					if(req==extend){
						cfg["extend"]=c;
					}
					return loadRequire();
				}else{
					return classLoader(req,loadRequire);
				}
			};

			return loadRequire();
			//非依赖式加载直接返回
		}else{
			return EG._defineClass(cfg,name);
		}
	};


	/**
	 * 定义命名空间
	 * @param {String} namespace 命名空间
	 * @param {Object?} fn 对象
	 * @returns {Object}
	 */
	EG.defineNamespace=function(namespace,fn){
		if(!fn) fn=EG._newConst();;
		var ss=namespace.split(".");
		var p=window;
		for(var i=0;i<ss.length;i++){
			if(!p[ss[i]]){
				if(i<ss.length-1){
					p[ss[i]]=EG._newConst();
				}else{
					p[ss[i]]=fn;
				}
			}
			p=p[ss[i]];
		}
		return p;
	};

	/**
	 * 寻找类
	 * @param {String} classNameExp 类名表达式
	 * @return {Object}
	 */
	EG.findClass=function(classNameExp){
		return EG.findClasses(classNameExp,false)[0];
	};

	/**
	 * 寻找类
	 * @param classNameExp
	 * @param returnObj 返回类型
	 * @param pClass
	 * @param classes
	 * @param packageName
	 * @return {*}
	 */
	EG.findClasses=function(classNameExp,returnObj,pClass,classes,packageName){
		classes=classes||((returnObj)?{}:[]);
		pClass=pClass||window;
		packageName=packageName||"";
		if(!classNameExp) return classes;
		var classEles=classNameExp.split(".");
		//alert(classNameExp);

		var classNamePattern=classEles.shift().replace("*",".*");
		for(var key in pClass){
			if((typeof(pClass[key])=="function"||typeof(pClass[key])=="object")&&new RegExp("^"+classNamePattern+"$","g").test(key)){
				var fullKey=packageName?(packageName+"."+key):key;
				if(classEles.length>0){
					EG.findClasses(classEles.join("."),returnObj,pClass[key],classes,fullKey);
				}else{
					if(returnObj){
						classes[fullKey]=(pClass[key]);
					}else{
						classes.push(pClass[key]);
					}
				}
			}
		}
		return classes;
	};

	/**
	 * 寻找方法,支持用.*来匹配
	 * @param {Class} clazz 类
	 * @param {String} methodNameExp 名称表达式
	 * @param {?String} rangeType 范围类型,prototype||static||all
	 * @param {?String} returnObj 返回类型
	 * @return {*}
	 */
	EG.findMethods=function(clazz,methodNameExp,rangeType,returnObj){
		var ms=((returnObj)?{}:[]);
		rangeType=rangeType||"all";
		methodNameExp=methodNameExp.replace("*",".*");

		var f=function(range){
			for(var key in range){
				if(typeof(range[key])=="function"&&new RegExp("^"+methodNameExp+"$","g").test(key)){
					if(returnObj){
						ms[key]=range[key];
					}else{
						ms.push(range[key]);
					}
				}
			}
		};
		if(rangeType=="all"||rangeType=="prototype") 	f(clazz.prototype);
		if(rangeType=="all"||rangeType=="static") 		f(clazz);
		return ms;
	};

	/**
	 * 是否为某类的子类
	 * @param {Object} obj 对象
	 * @param {Class} klass 父类
	 * @return {Boolean}
	 */
	EG.isAssignbleFrom=function(obj,klass){
		var sc=obj.getClass();
		while(sc=sc._superClass){
			if(sc==klass) return true;
		}
		return false;
	};

	/**
	 * 存储
	 * @param {String} key 键值
	 * @param {Object} data 数值
	 */
	EG.put=function(key,data){
		var ks=key.split("."),obj=window;
		for(var i= 0,il=ks.length;i<il;i++){
			if(i==(il-1)){
				obj[ks[i]]=data;
			}else{
				if(!obj[ks[i]]) obj[ks[i]]={};
				obj=obj[ks[i]];
			}
		}
	};

	/**
	 * 获取值
	 * @param {String} key 键值
	 * @return {*}
	 */
	EG.get=function(key){
		var ks=key.split("."),obj=window;
		for(var i=0,il=ks.length;i<il;i++){
			if(!obj[ks[i]]) return null;
			obj=obj[ks[i]];
		}
		return obj;
	};

	EG._importCache={

	};

	/**
	 * 获取Refs
	 * @returns {string}
	 */
	EG.importPath=function(keys,flag){

	};

	EG.importPath_static=function(){
		return EG.importPath(arguments,2);
	};

	EG.importPath_class=function(){
		return EG.importPath(arguments,1);
	};


	/**
	 * 显示源
	 * @param text
	 */
	EG.showSource=function(text){
		if(!EG.showSourceEle){
			EG.showSourceEle=EG.CE({tn:"div",style:"position:absolute;z-index:9999",cn:[
				{tn:"textarea",style:"width:500px;height:500px"},
				{tn:"button",innerHTML:"hide",onclick:function(){EG.hide(EG.showSourceEle)}}
			]})
			EG.getBody().appendChild(EG.showSourceEle);
		}

		EG.showSourceEle.childNodes[0].value=text;
		EG.show(EG.showSourceEle);
	}
})();
/**
 * @class EG.String
 * @author bianrongjun
 * 字符串操作类
 */
(function(){
	EG.define("EG.String",{
		statics:{
			/**
			 * 是否为空字符串
			 * @param {String} str 字符串
			 */
			isString:function(str) {
				return typeof (str) === 'string';
			},
			/**
			 * NULL转换为空字符串
			 * @param {String} str 字符串
			 */
			n2e :function(str) {
				return str!==null?str:"";
			},
			/**
			 * 是否为空字符串
			 * @param {String} str 字符串
			 * @returns {Boolean}
			 */
			isBlank :function(str) {
				return (str == null||str === ""||EG.String.trim(str) === "");
			},
			/**
			 * 是否相等
			 * @param {String} str1 字符串1
			 * @param {String} str2 字符串2
			 * @param {Boolean} ignoreCase 是否忽略大小写
			 * @returns {Boolean}
			 */
			equals :function(str1,str2,ignoreCase){
				return ignoreCase==true?
					str1.toUpperCase()===str2.toUpperCase()
					:
					str1===str2;
			},
			/**
			 * 是否以什么开始
			 *
			 * @param {String} str 源字符串
			 * @param {String} prefix 结尾字符串
			 * @param {Boolean?} ignoreCase 忽略大小写
			 * @returns {Boolean}
			 */
			startWith :function(str, prefix,ignoreCase) {
				
				if(str==null||prefix==null) return false;
				
				var l=prefix.length;
				if(str.length<l) return false;
				
				if(ignoreCase){
					for(var i=0;i<l;i++){
						if(prefix.charAt(i).toLocaleLowerCase()!=str.charAt(i).toLocaleLowerCase()){
							return false;
						}
					}
				}else{
					for(var i=0;i<l;i++){
						if(prefix.charAt(i)!=str.charAt(i)){
							return false;
						}
					}
				}
				
				return true;
			},
			/**
			 * 是否以什么结尾
			 *
			 * @param {String} str 源字符串
			 * @param {String} endStr 结尾字符串
			 * @param {Boolean?} ignoreCase 忽略大小写
			 * @returns {Boolean}
			 */
			endWith :function(str,endStr,ignoreCase) {
				return (
					str!==null
					&&
					endStr!==null
					&&
					(ignoreCase==true?
						str.toUpperCase().lastIndexOf(endStr.toUpperCase())>0
						:
						str.lastIndexOf(endStr)>=(str.length-endStr.length)
					)
				);
			},
			/**
			 * 截两端空字符
			 *
			 * @param str 字符串
			 * @returns {String}
			 */
			trim :function(str) {
				return str.replace(/(^\s*)|(\s*$)/g, "");
			},
			/**
			 * 移除尾部字串
			 *
			 * @param {String} str 源字符串
			 * @param {String} endStr 结尾字符串
			 * @returns {String}
			 */
			removeEnd :function(str, endStr) {
				return (EG.String.endWith(str, endStr)) ? str.substring(0, str.length - endStr.length) : str;
			},
			/**
			 * 移除首部字串
			 *
			 * @param {String} str 源字符串
			 * @param {String} startStr 结尾字符串
			 * @returns {String}
			 */
			removeStart :function(str, startStr) {
				return (EG.String.startWith(str, startStr)) ? str.substring(startStr.length,str.length) : str;
			},
			/**
			 * 调换字符串
			 * @param str
			 * @param regex
			 * @param replacer 替换
			 */
			replaceAll : function(str,regex, replacer) {
				return str.replace(new RegExp(regex, "g"), replacer);
			}
	}
	});
	EG.isBlank	=EG.String.isBlank;
	EG.isString	=EG.String.isString;
})();
/**
 * @class EG.Date
 * @author bianrongjun
 * 日期操作类
 */
(function(){
	EG.define("EG.Date",{
		statics:{
			/**
			 * 把日期转为字符串
			 * @param {Date} date 日期
			 * @param {Boolean?} flag 标志
			 * @param {Boolean?} lang 是否使用汉字
			 * @return {String}
			 */
			d2s:function(date,flag,lang){
				if(!date) date=new Date();
				var year = date.getFullYear();
				var month= date.getMonth()+ 1;
				month	=month<10?("0"+month):month;
				var day= date.getDate();
				day 	= day<10?("0"+day):day;

				if(flag==null) flag=true;
				if(flag) {
					var hour=date.getHours();		hour	=hour<10?("0"+hour):hour;
					var minute=date.getMinutes();	minute	=minute<10?("0"+minute):minute;
					var second=date.getSeconds();	second	=second<10?("0"+second):second;
					if(lang) return(year+"年"+month+"月"+day+"日"+hour+" 小时:"+minute+"分"+second+"秒");
					else return(year+"-"+month+"-"+day+" " +hour+":"+minute+":"+second);
				}else{
					if(lang) return(year+"年"+month+"月"+day+"日");
					else return(year+"-"+month+"-"+day);
				}
			},
			/**
			 * 时间字符串转时间
			 * @param {String} str 字符串
			 * @return {Date} 时间
			 */
			s2d:function(str){
				//2012-08-19 11:22:33
				//1234567890123456789
				if(str.length==10){
					return new Date(str.substring(0,4),str.substring(5,7)-1,str.substring(8,10));
				}else if(str.length==13){
					return new Date(str.substring(0,4),str.substring(5,7)-1,str.substring(8,10),str.substring(11,13));
				}else if(str.length==16){
					return new Date(str.substring(0,4),str.substring(5,7)-1,str.substring(8,10),str.substring(11,13),str.substring(14,16));
				}else if(str.length==19){
					return new Date(str.substring(0,4),str.substring(5,7)-1,str.substring(8,10),str.substring(11,13),str.substring(14,16),str.substring(17,19));
				}else{
					throw new Error("参数不完整");
				}
			},
			/**
			 * 比较两个时间
			 * @param {String|Date} d1 时间1
			 * @param {String|Date} d2 时间2
			 * @returns {Number} 时间差
			 */
			compare:function(d1,d2){
				if(typeof(d1)=="string") d1=EG.Date.s2d(d1);
				if(typeof(d2)=="string") d2=EG.Date.s2d(d2);
				return d1.getTime()-d2.getTime();
			},
			/**
			 * 数字转时间
			 * @param {Number} l 数值
			 * @return {Date} 时间
			 */
			l2d:function(l){
				var d=new Date();
				d.setTime(l);
				return d;
			},
			/**
			 * 数字转时间串
			 * @param {Number} l 数值
			 * @return {String} 时间
			 */
			l2s:function(l){
				return EG.Date.d2s(EG.Date.l2d(l));
			}
		}
	});
})();/**
 * @class EG.Object
 * @author bianrongjun
 * 对象操作类
 */
(function() {
	EG.define("EG.Object",{
		statics:{
			/**
			 * 解压一个数组对象变为对象
			 *
			 * [
			 * 	 {name:"bobby",age:11},
			 * 	 {name:"rose",age:12}
			 * ]
			 *
			 * ==>
			 *
			 * {
			 * 	bobb:11,
			 * 	rose:12
			 * }
			 *
			 * @param {Array} arr 源数组
			 * @param {String} kKey 键值Key
			 * @param {String} vKey 数值Key
			 * @return {Object}
			 */
			extract:function(arr,kKey,vKey){
				var o={};
				for(var i=0,il=arr.length;i<il;i++){
					o[arr[i][kKey]]=arr[i][vKey];
				}
				return o;
			},
			/**
			 * 是否在某个组里
			 * @param {Object} obj 对象
			 * @param {Array} arr 数组
			 * @return {Boolean}
			 */
			$in:function(obj, arr) {
				if (arr == null) return false;
				for (var i=0,il=arr.length;i<il;i++){
					if (EG.Object.equals(arr[i],obj)) return true;
				}
				return false;
			},

			/**
			 * 是否为对象字面量
			 * @param {Object} obj 对象
			 * @return {Boolean}
			 */
			isLit:function(obj) {
				return obj.constructor==Object;
			},

			/**
			 * 是否相等
			 * @param {Object} o1 对象1
			 * @param {Object} o2 对象2
			 * @return {Boolean}
			 */
			equals:function(o1,o2){
				if(o1==null&&o2==null) return true;
				var ot=EG.getType(o1);
				if(ot!=EG.getType(o2)) return false;
				switch(ot){
					case "string":
					case "function":
					case "number":{
						return (o1===o2);
					}
					case "object":{
						for(var k in o1){
							if(!EG.Object.equals(o1[k],o2[k]))  return false;
						}
						return true;
					}
					case "array":{
						if(o1.length!=o2.length) return false;
						for(var i= 0,il=o1.length;i<il;i++){
							if(!EG.Object.equals(o1[i],o2[i])) return false;
						}
						return true;
					}
					default:{
						throw new Error("EG.Object#equals:暂不支持类型"+ot);
					}
				}
			}
		}
	});

	EG.$in=EG.Object.$in;
	EG.isLit=EG.Object.isLit;
})();/**
 * @class EG.Array
 * @author bianrongjun
 * 数组操作类
 */
(function(){
	EG.define("EG.Array",{
		statics:{
			/**
			 * 是否为数组或伪数组对象
			 * @param {Object} o 对象
			 */
			isArray:function(o){
				return o
					&&typeof o==='object'
					&&typeof o.length==='number'
					&&typeof o.splice==='function'
					&&!(o.propertyIsEnumerable('length'));
			},
			/**
			 * 删除某个位置的元素
			 * @param {Array} arr 源数组
			 * @param {Number} n 位置
			 * @returns {Array} 新数组
			 */
			del:function(arr,n){
				if(n==null){throw new Error("EG.Array#del:待删除坐标不能为null");}
				arr.splice(n,1);
				return arr;
			},
			/**
			 * 删除某个元素
			 * @param {Array} arr 源数组
			 * @param {*} obj 待插入元素
			 */
			remove:function(arr,obj){
				for(var i=arr.length;i>=0;i--){
					if(obj==arr[i]) {
						EG.Array.del(arr,i);
					}
				}
				return arr;
			},
			/**
			 * 指定位置掺入元素
			 * @param {Array} arr 源数组
			 * @param {Number} n 位置
			 * @param {Object} obj 待插入元素
			 * @returns {Array}
			 */
			insert:function(arr,n,obj){
				arr.splice(n,0,obj);
				return arr;
			},
			/**
			 * 第一个元素
			 * @param {Array} arr 源数组
			 * @returns {Object}
			 */
			first:function(arr){
				return EG.Array.get(arr,0);
			},
			/**
			 * 最后一个元素
			 * @param {Array} arr 源数组
			 * @returns {Object}
			 */
			last:function(arr){
				return EG.Array.get(arr,arr.length-1);
			},
			/**
			 * 获取指定位置的元素
			 * 支持传first,last,支持用负数逆向查找元素
			 * @param {Array} arr 源数组
			 * @param {Number|String} n 位置
			 */
			get:function(arr,n){
				if(typeof(n)=="number"){
					if(arr.length==0) return null;
					if(n<0) n=arr.length-1+n;
					return arr[n];
				}else if(typeof(n)=="string"){
					if(n=="n") n="last";
					return EG.Array[n](arr);
				}
				else throw new Error("EG.Array#get:参数错误"+n);
			},
			/**
			 * 是否有某个对象
			 * @param {Array} arr 源数组
			 * @param {*} obj 待插入元素
			 */
			has:function(arr,obj){
				if(!arr) return false;
				for(var i=0,l=arr.length;i<l;i++){if(obj===arr[i]) return true;}
				return false;
			},
			/**
			 * 每个对象迭代执行方法
			 * @param {Array} arr 源数组
			 * @param {Function} fn 待执行方法
			 * @param {Array?} args 参数
			 * @returns {Array}
			 */
			each:function(arr,fn,args){
				if(!arr||!fn) return null;
				if(!args) args=[];
				var r=null;
				for(var i=0,l=arr.length;i<l;i++) {
					r=fn.apply(arr[i],args);
					if(r!=null&&r===false) break;
				}
				return arr;
			},
			/**
			 * 抽取
			 * @param {Array} arr 数组
			 * @param {String|Number} key 键值
			 * @returns {Array}
			 */
			extract:function(arr,key){
				var vs=[];
				for(var i=0,il=arr.length;i<il;i++){
					vs.push(arr[i][key]);
				}
				return vs;
			},

			/**
			 * 抽取成2维数组
			 * @param {Array} arr 源数组
			 * @param {Array} keys 键值数组
			 * @param {Array?} vs 栈
			 * @return {Array}
			 */
			extract2Arrays:function(arr,keys,vs){
				if(!vs) vs=[];
				for(var i=0,il=arr.length;i<il;i++){
					var v=[];
					for(var j=0;j<keys.length;j++){
						v.push(arr[i][keys[j]]);
					}
					vs.push(v);
				}
				return vs;
			},
			/**
			 * 抽取成为键值对
			 * @param {Array} arr 数组
			 * @param {String} key 键值
			 * @return {Object}
			 */
			extract2Obj:function(arr,key){
				var obj={};
				for(var i=0,il=arr.length;i<il;i++){
					obj[arr[i][key]]=arr[i];
				}
				return obj;
			},

			/**
			 * 清空数组
			 * @param {Array} arr 数组
			 * @return {*}
			 */
			clear:function(arr){
				arr.splice(0,arr.length);
				return arr;
			},
			/**
			 * 截取数组
			 * @param arr
			 * @param start
			 * @param idx
			 * @returns {Array}
			 */
			sub:function(arr,start,idx){
				var nA=[];
				for(var i=start;i<idx;i++){
					nA.push(arr[i]);
				}
				return nA;
			},
			/**
			 * 添加数组
			 * @param srcA
			 * @param destA
			 */
			addAll:function(srcA,destA){
				for(var i=0;i<destA.length;i++){
					srcA.push(destA[i]);
				}
			}
		}
	});

	EG.isArray	=EG.Array.isArray;
	EG.del		=EG.Array.del;
	EG.remove	=EG.Array.remove;
})();/**
 * @class EG.RegExp
 * @author bianrongjun
 * 正则式操作类
 */
(function(){
	EG.define("EG.RegExp",{
		statics:{
			/**
			 * 是否匹配
			 * @param {String} str 字符串
			 * @param {String} regex 正则式
			 * @return {Boolean}
			 */
			match:function(str,regex){
				return new RegExp("^"+regex+"$").test(str);
			}
		}
	});
})();/**
 * @class EG.Ajax
 * @author bianrongjun
 * Ajax操作类
 */
(function(){
	EG.define("EG.Ajax", {
		statics:{
			/**
			 * 获取XMLHttpRequest对象
			 * @static
			 * @returns {XMLHttpRequest}
			 */
			getXMLHttpRequest:function () {
				var req = null;
				if (window.XMLHttpRequest) {
					req = new XMLHttpRequest();
				} else if (window.ActiveXObject) {
					try {
						req = new ActiveXObject("Msxml2.XMLHTTP");
					} catch (e1) {
						try {
							req = new ActiveXObject("Microsoft.XMLHTTP");
						} catch (e2) {
							req = null;
						}
					}
				}
				return req;
			},
			/**
			 * URL编码
			 * @static
			 * @param {String} str 字符串
			 * @returns {String}
			 */
			javaURLEncoding:function (str) {
				return window.encodeURIComponent(str).replace(/!/g, "%21").replace(/'/g, "%27").replace(/\(/g, "%28").replace(/\)/g, "%29").replace(/~/g, "%7E");
			},
			/**
			 * 发送HTTP请求
			 * <code>
			 * 	EG.Ajax.send({
			 * 	    url:"/mmvc/jsrpc/call",
			 * 	    charset:"utf-8",
			 * 	    httpPost:true,
			 * 	    content:"a=1&b=2",
			 * 	    callback:function(responseText,req){
			 *				alert(responseText);
			 * 	    },
			 * 	    erhandler:function(erhandler){
			 *				alert("Error");
			 * 	    }
			 * 	});
			 * </code>
			 * @static
			 * @param {Object} cfg 配置
			 */
			send:function (cfg) {
				if (cfg == null)	throw new Error("EG.Ajax.call#参数不能为空");

				var  url 		=cfg["url"],							//URL
					 charset 	=cfg["charset"]||"UTF-8",				//字符集
					 req 		=EG.Ajax.getXMLHttpRequest(),			//创建新的XMLHTTPRequest对象
					 httpPost 	=EG.unnull(cfg["httpPost"],true),		//POST方式:true||GET方式:false
					 async 		=EG.unnull(cfg["async"],true),			//异步传输
					 callback 	=EG.unnull(cfg["callback"],""),			//回调
					 erhandler 	=cfg["erhandler"],						//错误处理器 TODO 改为onError
					 content 	=EG.unnull(cfg["content"],"");			//内容

				if (async) {
					req.onreadystatechange=function(){
						if (req.readyState==4){
							if(req.status==200){
								if(callback) return callback(req.responseText, req);
							}else{
								if(erhandler) return erhandler(req.status, req);
								else throw new Error("EG.Ajax#send:网络访问失败 " + req.status + ": " + req.statusText);
							}
						}
						return null;
					};
				}

				//req.basePa

				if (httpPost) {
					req.open("POST",url,async);
					req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");//BUGFIX:Tomcat 如果不设定,Sevlet无法获取参数
					req.send(content);
				} else {
					req.open("GET", url, async);
					req.send();
				}

				//同步返回值
				if (!async&&callback) return callback(req.responseText, req);
				return null;
			}
		}
	});

	/**
	 * 共用的Request对象
	 * @property {XMLHttpRequest}
	 */
	EG.Ajax.globeRequest = EG.Ajax.getXMLHttpRequest();
})();/**
 * @class EG.Browser
 * @author bianrongjun
 * 浏览器对象
 */
(function(){
	EG.define("EG.Browser",{
		statics:{
			Sys:{},
			/**
			 * 是否为IE
			 * @returns {Boolean}
			 */
			isIE:function(){return Browser.Sys.ie!=null;},
			/**
			 * 是否为IE6
			 * @returns {Boolean}
			 */
			isIE6:function(){return Browser.isIE()&&Browser.getIEVersion()==6;},
			/**
			 * 是否为IE7
			 * @returns {Boolean}
			 */
			isIE7:function(){return Browser.isIE()&&Browser.getIEVersion()==7;},
			/**
			 * 是否为IE8
			 * @returns {Boolean}
			 */
			isIE8:function(){return Browser.isIE()&&Browser.getIEVersion()==8;},

			/**
			 * 获取IE主版本
			 * @return {Number}
			 */
			getIEVersion:function(){
				return parseInt(Browser.Sys.ie.substr(0,Browser.Sys.ie.indexOf(".")));
			},

			/**
			 * 是否为IE8之前
			 * @return {Boolean}
			 */
			isIE8Before:function(){
				return Browser.isIE()&&Browser.getVersion()<=8;
			},

			/**
			 * 是否为Firefox
			 * @returns {Boolean}
			 */
			isFirefox:function(){return Browser.Sys.firefox!=null;},
			/**
			 * 是否为 Chrome
			 * @returns {Boolean}
			 */
			isChrome:function(){return Browser.Sys.chrome!=null;},
			/**
			 * 是否为Safari
			 * @returns {Boolean}
			 */
			isSafari:function(){return Browser.Sys.safari!=null;},
			/**
			 * 是否为Opera
			 * @returns {Boolean}
			 */
			isOpera:function(){return Browser.Sys.opera!=null;},
			/**
			 * 浏览器版本
			 * @returns {String}
			 */
			getVersion:function(){
				return Browser.Sys.ie||Browser.Sys.firefox||Browser.Sys.chrome||Browser.Sys.safari||Browser.Sys.opera;
			},
			/**
			 * 获取域根地址
			 * @returns {String}
			 */
			getDomainAddress:function(){
				return window.location.href.substr(0,window.location.href.indexOf("/",10));
			}
		}
	});

	var Browser=EG.Browser;

	var ua = navigator.userAgent.toLowerCase();
	var s;
	(s = ua.match(/msie ([\d.]+)/))?Browser.Sys.ie=s[1]:
		(s = ua.match(/firefox\/([\d.]+)/))?Browser.Sys.firefox=s[1] :
			(s = ua.match(/chrome\/([\d.]+)/))?Browser.Sys.chrome=s[1] :
				(s = ua.match(/opera.([\d.]+)/))?Browser.Sys.opera=s[1] :
					(s = ua.match(/version\/([\d.]+).*safari/))?Browser.Sys.safari=s[1]:0;
})();/**
 * @class EG.Tools
 * @author bianrongjun
 * 工具操作类
 */
(function(){
	/**
	 * 工具类
	 */
	EG.define("EG.Tools",{
		statics:{
			/**
			 * 是否按住左键
			 * @param e
			 */
			isPressLeft:function(e){
				e=EG.Event.getEvent(e);
				if(e.which!=null){
					return e.which==1;
				}else{
					return e.button==1;
				}
			},

			/**
			 * 是否按住右键
			 * @param e
			 */
			isPressRight:function(e){
				e=EG.Event.getEvent(e);
				if(e.which!=null){
					return e.which==3;
				}else{
					return e.button==2;
				}
			},

			/**
			 * 获取路径参数
			 * @param {String} name 参数名称
			 * @returns {String}
			 */
			getParam:function(name){
				var params = EG.doc.location.search;
				params = params.substring(1);
				if(!params) return "";
				var paramArr = params.split("&");
				for(var i=0,il=paramArr.length; i<il; i++) {
					if(paramArr[i].indexOf(name) != -1) {return paramArr[i].substring(paramArr[i].indexOf("=") + 1);}
				}
				return null;
			},

			/**
			 * 获取所有参数
			 * @returns {Object}
			 */
			getParams:function(){
				var params={};
				var strParams = EG.doc.location.search;
				strParams = strParams.substring(1);
				if(!strParams) return {};
				var paramArr = strParams.split("&");
				for(var i=0,il=paramArr.length; i<il; i++) {
					params[paramArr[i].substring(0,paramArr[i].indexOf("="))]=paramArr[i].substring(paramArr[i].indexOf("=") + 1);
				}
				return params;
			},

			/**
			 * 转换为JSON字符串
			 * @param {*|String} obj 对象
			 * @returns {String}
			 */
			toJSON:function(obj){
				if(obj==null) return "null";
				if(typeof(obj)== "object"){
					var json = [];
					if(obj instanceof Array){
						for (var i=0; i < obj.length; i++) {
							json[i] = (obj[i] !== null) ? EG.Tools.toJSON(obj[i]): "null";
						}
						return "[" + json.join(", ") + "]";
					}else{
						for (var key in obj) {
							if (!obj.hasOwnProperty(key)) continue;
							json.push( "\""+ key + "\" : " + ((obj[key] != null) ? EG.Tools.toJSON(obj[key]) : "null"));
						}
						return "{\n " + json.join(",\n ") + "\n}";
					}
				}else if(typeof(obj)== "string"){
					return "'"+obj.replace(/\\/g,"\\\\").replace(/'/g,"\\'")+"'";
				}else if(typeof(obj)== "boolean"){
					return obj;
				}else if(typeof(obj)== "function"){
					return obj;
				}else if(typeof(obj)== "number"){
					return obj;
				}else if(typeof(obj)== "regexp"){
					return obj;
				}else throw new Error("Engin#toJSON:不支持类型:"+typeof(obj));
			},

			/**
			 * 获取脚本路径
			 */
			getScriptPath:EG.getScriptPath,

			/**
			 * 计算精确鼠标位置
			 * @param {Event} evt 事件
			 * @param {HTMLElement?} refer 参照物
			 */
			getMousePos:function(evt,refer){
				var mp;
				evt=EG.Event.getEvent(evt);
				if(evt.pageX || evt.pageY){
					mp={x:evt.pageX, y:evt.pageY};
				}else{
					mp={
						x:evt.clientX + EG.getBody().scrollLeft - EG.getBody().clientLeft,
						y:evt.clientY + EG.getBody().scrollTop  - EG.getBody().clientTop
					};
				}

				if(refer){
					var rp=EG.Tools.getElementPos(refer);
					mp={
						x:mp.x-rp.x,
						y:mp.y-rp.y
					};
				}

				return mp;
			},

			/**
			 * 获取元素相对屏幕的位置
			 * @param {HTMLElement} ele 元素
			 * @param {HTMLElement?} parent 元素
			 * @param {Boolean?} isOffsetParent 是否为offset父元素
			 */
			getElementPos:function(ele,parent,isOffsetParent){
				if(isOffsetParent==null) isOffsetParent=true;
				if(!parent) parent=EG.getBody();
				var t = ele.offsetTop;
				var l = ele.offsetLeft;
				while ((ele = isOffsetParent?ele.offsetParent:ele.parentNode)!=parent){//offsetParent!=parentNode
					t += ele.offsetTop;
					l += ele.offsetLeft;
				}
				return {x:l,y:t};
			},
//			getElementPos:function(ele,parent){
//				if(parent!=null){
//					var pe=EG.Tools.getElementPos(parent);
//					var e=EG.Tools.getElementPos(ele);
//					return {x:e.x-pe.x,y:e.y-pe.y};
//				}else{
//					var t = ele.offsetTop,l = ele.offsetLeft;
//					while(ele!= null){
//						t += ele.offsetTop;
//						l += ele.offsetLeft;
//						alert(ele.tagName);
//						ele = ele.offsetParent;
//					}
//					return {x:t,y:l};
//				}
//			},



			/**
			 * 调试对象
			 */
			debugObject:function(obj){
				var s="";
				for(var key in obj){
					s+=key+",";
				}
				alert(s);
			},

			/**
			 * AOP方法
			 * @param expression
			 * @param before
			 * @param after
			 */
			aop:function(expression,before,after){
				if(!before&&!after) return;

				var mType=-1;
				if(mType<0) mType=expression.indexOf("###")>0?2:-1;
				if(mType<0) mType=expression.indexOf("##")>0?1:-1;
				if(mType<0) mType=expression.indexOf("#")>0?0:-1;
				if(mType<0) throw new Error("EG#aop:表达式错误>"+expression);

				var classNameExp=expression.substr(0,expression.indexOf("#"));
				var methodNameExp=expression.substr(expression.lastIndexOf("#")+1);

				//寻找类
				var classes=EG.findClasses(classNameExp,true);
				var cms={};
				for(var className in classes){
					(function(){
						//寻找类方法
						var clazz=classes[className];
						var f=function(num,tp){
							var ms=EG.findMethods(clazz,methodNameExp,tp,"all");
							for(var methodName in ms){
								//alert(className+","+num+","+methodName);
								cms[className+","+num+","+methodName]=ms[methodName];
							}
						};

						if(mType==2||mType==1){
							f(1,"static");
						}
						if(mType==2||mType==0){
							f(0,"prototype");
						}
					})();
				}

				//植入前置和后置
				for(var cmName in cms){

					(function(){//循环时避免重叠

						var method		=cms[cmName];
	//				alert(cmName);
						var ks			=cmName.split(",");
						var className	=ks[0];
						var isProptype	=(ks[1]=="0");
						var methodName	=ks[2];
						var clazz		=classes[className];

						//过滤掉自身
						//if(className=="EG"&&methodName=="aop") continue;


						if(isProptype){
							clazz.prototype[methodName]=function(){
								if(before) before.apply(this,[clazz,className,method,methodName,isProptype]);
								var r=method.apply(this,arguments);
								if(after) after.apply(this,[clazz,className,method,methodName,isProptype]);
								return r;
							};
						}else{
							clazz[methodName]=function(){
								if(before) before(clazz,className,method,methodName,isProptype);

								//动态触发new
								var s="";
								for(var i=0,il=arguments.length;i<il;i++){
									if(s!=0) s+=",";
									s+=("arguments["+i+"]");
								}
								var r=null;
								var p="r=new method("+s+");";
								eval(p);

								if(after) after(clazz,className,method,methodName,isProptype);
								return r;
							};
						}
					})();
				}
			},

			/**
			 * 获取文本
			 * @param textvalues
			 * @param value
			 */
			getText:function(textvalues,value){
				for(var i=0;i<textvalues.length;i++){
					if(textvalues[i][1]==value) return textvalues[i][0];
				}
				return null;
			},

			/**
			 * 转换为省略词
			 * @param txts
			 * @param count
			 * @param endChar
			 * @returns {string}
			 */
			getEllipsis:function(txts,count,endChar){
				var t="";
				if(!endChar) endChar="";

				if(txts.length>count){
					t=EG.Array.sub(txts,0,count).join(",");
					t+="...("+(txts.length)+endChar+")";
				}else{
					t=txts.join(",");
				}
				return t;
			},

			/**
			 * 累计调用
			 * @param expression
			 */
			watch:function(expression){
				EG.Tools.aop(expression,EG.Tools.watch_f,null);
			},
			watch_f:function(clazz,className,method,methodName,isProptype){
				//监测
				if(!window.watchCount){

					window.watchBox=EG.doc.createElement("div");
					var ss="position:absolute;z-index:9999;left:0px;top:0px;border:1px solid red;font-size:12px;max-height:500px;overflow:auto;background-color:white".split(";");
					for(var i= 0,il=ss.length;i<il;i++){
						var sss=ss[i].split(":");
						watchBox.style[sss[0]]=sss[1];
					}
					EG.getBody().appendChild(watchBox);
					window.watchCount={};
					window.watchBoxs={};
				}
				var key=className+(isProptype?"#":"##")+methodName;

				if(!watchCount[key]){
					watchCount[key]=0;
					watchBoxs[key]=EG.doc.createElement("div");
					watchBox.appendChild(watchBoxs[key]);
				}

				watchCount[key]++;
				watchBoxs[key].innerHTML=(key+":"+watchCount[key]);
			}
		}
	});

	EG.toJSON=EG.Tools.toJSON;
})();
/**
 * @class EG.Word
 * @author bianrongjun
 * 单词操作类
 */
(function(){
	EG.define("EG.Word",{
		statics:{
			/**
			 * 首字母变大写
			 * @param {String} str 字符串
			 * @returns {String}
			 */
			first2Uppercase:function(str){
				return str.substring(0,1).toUpperCase()+str.substring(1);
			},

			/**
			 * 首字母变小写
			 * @param {String} str 字符串
			 * @returns {String}
			 */
			first2LowerCase:function(str){
				return str.substring(0,1).toLowerCase()+str.substring(1);
			}
		}
	});
})();/**
 * @class EG.Validate
 * @author bianrongjun
 * 校验操作类
 */
(function(){
	EG.define("EG.Validate",{
		statics:{
			/**
			 * 常用正则
			 */
			common_regex:{
				/** 邮箱 */
				email		:"([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9])+\\.)+([a-zA-Z0-9]{2,10})+",
				/** 手机号:13X,158,159号段 */
				phone		:"(13\\d|15[89])\\d{8}",
				/** QQ号 */
				qq			:"[1-9]\\d*",
				/** 电话号:国家代码-区域号码-电话号-分机号*/
				tel			:"(([0+]\\d{2,3}-)?(0d{2,3})-)(d{7,8})(-(d{3,}))?",
				/** 身份证号 */
				idcardNo	:"\\d{15}(\\d{3})?",
				/** 密码 */
				password	:"[0-9a-zA-Z]*",
				/** 字母数字 */
				wordnum		:"[0-9a-zA-Z]*"
			},

			common_comment:{
				/** 邮箱 */
				email		:"xxx@域名",
				/** 手机号:13X,158,159号段 */
				phone		:"13X,158,159开头的11位数字",
				/** QQ号 */
				qq			:"非0开头的纯数字",
				/** 电话号:国家代码-区域号码-电话号-分机号*/
				tel			:"国家(非必填)-区域(非必填)-电话-分机(非必填)",
				/** 身份证号 */
				idcardNo	:"15或18位数字",
				/** 密码 */
				password	:"字母(含大小写)和数字组合形式",
				/** 字母数字 */
				wordnum		:"字母(含大小写)和数字组合形式"
			},

			/**
			 * 获取电话正则
			 * @param {String} split 分隔符
			 * @return {String}
			 */
			getTelRegex:function(split){
				if(split) split="-";
				return "(([0+]\\d{2,3}"+split+")?(0\\d{2,3})"+split+")(\\d{7,8})("+split+"(\\d{3,}))?";
			},

			/**
			 * 是否为字母数字
			 * @param {String} str 字符串
			 * @return {Boolean}
			 */
			isWordnum:function(str){
				return EG.RegExp.match(str,EG.Validate.common_regex.wordnum);
			},

			/**
			 * 是否为邮箱
			 * @param {String} str 字符串
			 * @return {Boolean}
			 */
			isEmail:function(str){
				return EG.RegExp.match(str,EG.Validate.common_regex.email);
			},

			/**
			 * 是否为密码
			 * @param {String} str 字符串
			 * @return {Boolean}
			 */
			isPassword:function(str){
				return EG.RegExp.match(str,EG.Validate.common_regex.password);
			},

			/**
			 * 是否为手机号
			 * @param {String} str 字符串
			 * @return {Boolean}
			 */
			isPhone:function(str){
				return EG.RegExp.match(str,EG.Validate.common_regex.phone);
			},

			/**
			 * 是否为手机号
			 * @param {String} str 字符串
			 * @return {Boolean}
			 */
			isQq:function(str){
				return EG.RegExp.match(str,EG.Validate.common_regex.qq);
			},

			/**
			 * 是否为电话号
			 * @param {String} str 字符串
			 * @param {String} split 分隔符
			 * @return {Boolean}
			 */
			isTel:function(str,split){
				return EG.RegExp.match(str,EG.Validate.getTelRegex(split));
			},

			/**
			 * 是否为身份证号
			 * @param {String} str 字符串
			 * @return {Boolean}
			 */
			isIdcardNo:function(str){
				var len=str.length;
				return EG.RegExp.match(str,EG.Validate.common_regex.idcardNo);
			},

			/**
			 * 判断是否为type类型数据
			 * @param {String} type 类型
			 * @param {String} str 字符串
			 * @return {Boolean}
			 */
			$is:function(type,str){
				var m="is"+EG.Word.first2Uppercase(type);
				var f=EG.Validate[m];
				if(!f) throw new Error("EG.Validate:不支持函数"+m);
				return f(str);
			},

			/**
			 * 获取正则格式说明
			 * @param {String} type 类型
			 * @return {String}
			 */
			getComment:function(type){
				return EG.Validate.common_comment[type];
			}
		}
	});
})();/**
 * @class EG.Event
 * @author bianrongjun
 * 事件操作类
 */
(function(){
	EG.define("EG.Event",{
		statics:{
			/**
			 * 触发事件
			 * @param ele 元素
			 * @param action 事件
			 */
			fireEvent:function(ele,action){
				//去掉前缀on
				action=EG.String.removeStart(action,"on");

				if (document.all){
					eval("ele." + action + "();");
				}else{
					var e = document.createEvent('HTMLEvents');
					e.initEvent(action, false, false);
					ele.dispatchEvent(e);
				}
			},
			_eventFns:{


			},
			/**
			 * 获取事件的fn
			 * @param action
			 * @returns {*}
			 * @private
			 */
			_getEventFN:function(action){
				var eventKey="events_"+action;
				if(!EG.Event._eventFns.hasOwnProperty(eventKey)){
					EG.Event._eventFns[eventKey]=function(e){
						var rs=null;
						for(var i = 0,il=this[eventKey].length;i<il;i++){
							rs=this[eventKey][i].apply(this["on"+action+"Src"]||this,arguments);
						}
						return rs;
					};
				}
				return EG.Event._eventFns[eventKey];
			},

			/**
			 * 绑定事件,将事件处理添加到执行队列中
			 *
			 * @param {HTMLElement} ele 元素
			 * @param {String} action 动作
			 * @param {Function} handler 处理器
			 * @param {Boolean?} cap cap
			 */
			bindEvent : function(ele, action, handler, cap) {
				//去掉前缀on
				action=EG.String.removeStart(action,"on");
				var eventKey="events_"+action;
				//创建事件队列
				if(!ele[eventKey]){
					ele[eventKey]=[];
					//执行本体
					ele[action+"_fn"]=EG.Event._getEventFN(action);
					//添加事件

					if(EG.Browser.isIE()){
						ele["on" + action]=ele[action+"_fn"];
					}else if(ele.addEventListener){
						ele.addEventListener(action,ele[action+"_fn"], cap);
					}else{
						ele.attachEvent("on" + action,ele[action+"_fn"]);
					}
				}

				//存放到事件队列
				ele[eventKey].push(handler);

			},

			/**
			 * 是否有事件处理器
			 */
			hasEventHandle:function(ele,action,fn){
				return ele[action+"_fn"]&&EG.Array.has(ele[action+"_fn"],fn);
			},

			_fn:{
				false_return:function(){return false;}
			},

			/**
			 * 绑定不被选择IE
			 * @param ele
			 */
			bindUnselect:function(ele){
				if (EG.Browser.isIE()){
					ele.attachEvent("onselectstart",EG.Event._fn.false_return);
				}
			},

			/**
			 * 移除事件
 			 * @param ele
			 * @param action
			 * @param handler
			 */
			removeEvent:function(ele,action,handler){
				action=EG.String.removeStart(action,"on");
				var eventKey="events_"+action;
				if(!ele[eventKey]) return;
				EG.Array.remove(ele[eventKey],handler);
			},

			/**
			 * 启动事件
			 * @param {Function} fn 函数
			 */
			onload : function(fn) {
				EG.Event.bindEvent(window,"load",fn);
			},

			/**
			 * 获取Event事件
			 * @param {Event} e 元素
			 * @returns {*|Event}
			 */
			getEvent:function(e){
				return e||window.event;
			},
			/**
			 * 获取目标对象
			 * @param {Event} e 元素
			 * @returns {*|EventTarget|String}
			 */
			getTarget:function(e){
				return e ? e.target : window.event.srcElement;
			},

			/**
			 * 停止传播事件
			 * @param {Event} e 元素
			 */
			stopPropagation:function(e){
				e=EG.Event.getEvent(e);
				if (e&&e.stopPropagation) e.stopPropagation();
				else window.event.cancelBubble = true;

				if(e.preventDefault){
					e.preventDefault();
				}else{
					e.returnValue = false;
				}
			}
		}
	});

	// 注册到全局
	EG.bindEvent = EG.Event.bindEvent;
	EG.onload = EG.Event.onload;
})();
/**
 * @class EG.DOM
 * @author bianrongjun
 * DOM操作类
 */
(function(){
	//BUGFIX#FireFox 无outerHTML、canHaveChildren
	var fixOuterHTML4Firefox=false;
	if(fixOuterHTML4Firefox){
		if (!EG.Browser.isIE() && typeof (HTMLElement) != "undefined" && !window.opera) {
			//outerHTML
			HTMLElement.prototype.__defineGetter__("outerHTML", function() {
				var a = this.attributes, str = "<" + this.tagName;
				for ( var i = 0; i < a.length; i++) if (a[i].specified) str += "   " + a[i].name + '="' + a[i].value + '"';
				if (!this.canHaveChildren) return str + "   />";
				return str + ">" + this.innerHTML + "</" + this.tagName + ">";
			});
			HTMLElement.prototype.__defineSetter__("outerHTML", function(s) {
				var d = EG.doc.createElement("DIV");
				d.innerHTML = s;
				for ( var i = 0; i < d.childNodes.length; i++) this.parentNode.insertBefore(d.childNodes[i], this);
				this.parentNode.removeChild(this);
			});
			//canHaveChildren
			HTMLElement.prototype.__defineGetter__("canHaveChildren",function() {
				return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase());
			});
		}
	}
	EG.define("EG.DOM",{
		statics:{
			/**
			 * 添加子元素
			 * @param {HTMLElement}	ele 父元素
			 * @param {HTMLElement|Array|Object|EG.ui.Item} child 子元素
			 * @param {Number} idx 位置
			 */
			addChildren:function(ele,child,idx){
				var childs=(!EG.isArray(child))?[ child ]:child;
				for (var i = 0,il = childs.length; i < il; i++) {
					var c = childs[i];
					if (EG.isLit(c)) {
						c = EG.CE(c);
					}else if(c.getElement){
						c=c.getElement();
					}
					EG.DOM.insertChild(ele,c,idx);
					idx++;
				}
			},

			/**
			 * 在某个对象后面插入元素
			 * @param {HTMLElement} ele 待插入元素
			 * @param {HTMLElement} target 参考元素
			 */
			insertAfter:function(ele, target){
				var pn = target.parentNode;
				if(pn.lastChild == target){
					pn.appendChild(ele);
				}else{
					pn.insertBefore(ele,DOM.nextNode(target));
				}
			},

			/**
			 * 指定位置插入子元素
			 * @param {HTMLElement} pn 父元素
			 * @param {HTMLElement} ele 子元素
			 * @param {HTMLElement} target 参考元素
			 */
			insertChild:function(pn,ele,target){
				if(!target) return pn.appendChild(ele);

				if(typeof(target)=="number"){
					target=DOM.childNodes(pn)[target];
				}
				if(target){
					pn.insertBefore(ele,target);
				}else{//IE
					pn.appendChild(ele);
				}

			},

			/**
			 * 获取元素的outerHTML,只支持无父节点的节点
			 * @param {HTMLElement} ele 元素
			 */
			getOuterHTML:function(ele){
				if(ele.parentNode) throw new Error("EG.DOM#getOuterHTML:不支持有父节点的节点");
				return EG.CE({tn:"div",cn:[ele]}).innerHTML;
			},

			/**
			 * 查询元素
			 * @param {String|HTMLElement}	selector 字符串|表达式|元素
			 */
			$:function(selector) {
				if(DOM.isElement(selector)) return selector;

				if("string"==typeof(selector)){
					var e=EG.doc.getElementById(selector);
					if(e!=null) return e;
					else if((e=EG.doc.getElementsByName(selector))!=null&&e.length>0) return e;
					else return null;

					//TODO 待支持 JQuery的表达式索引 #id>aa
				}else if("object"==typeof(selector)){
					var objs=null,dObjs=[];

					if(selector["name"]){
						var ele=selector["ele"]||EG.doc;
						objs=ele.getElementsByName(selector["name"]);
					}else if(selector["tn"]){
						var ele=selector["ele"]||EG.doc;
						objs=ele.getElementsByTagName(selector["tn"]);
					}else{
						throw new Error("EG.DOM#$:name和tn不能同时为空");
					}

					var idx=selector["idx"];
					
					//TODO 待修复为不删除的判断
					delete selector["name"];
					delete selector["tn"];
					delete selector["ele"];
					delete selector["idx"];

					for( var i=0,il=objs.length;i<il;i++){
						var match=true;
						for(var key in selector){
							if(EG.$in("name","tn","ele","idx")) continue;
							if(selector[key]!=objs[i][key]){
								match=false;
								break;
							}
						}
						if(match) dObjs.push(objs[i]);
					}

					if(idx!=null){
						return EG.Array.get(dObjs,idx);
					}

					return dObjs;
				}
			},

			/**
			 * @property {Array} CE关键词
			 * @private
			 */
			CEKeywords:["ele","element","tn","tagName","pn","parentNode","cn","childNodes","style"],

			/**
			 * @property {Object} CE昵称
			 * @private
			 */
			CENicknames:{
				"cls":"className"
			},

			/**
			 * SVG 命名空间
			 */
			NS_SVG:"http://www.w3.org/2000/svg",

			/**
			 * SVG元素创建
			 * @param {Object} atrs 属性
			 * @returns {HTMLElement}
			 */
			CSVG:function(atrs){
				return DOM.CE(atrs,{svg:true});
			},

			/**
			 * VML元素创建
			 * @param {Object} atrs 属性
			 * @return {HTMLElement}
			 */
			CVML:function(atrs){
				return DOM.CE(atrs,{vml:true});
			},

			/**
			 * 创建元素 特殊属性:pn:父节点,tn:标签,cn:子节点,ele:元素,style:样式
			 *
			 * @param {Object} atrs 属性
			 * @param {?Object} ext 扩展
			 * @returns {HTMLElement}
			 */
			CE:function(atrs,ext) {
				var isSvg		=ext?ext["svg"]		:false;
				var isVml		=ext?ext["vml"]		:false;
				var setAtr		=ext?ext["setAtr"]	:false;
				if(typeof(atrs)=="string") 	return EG.doc.createElement(atrs);

				var ele		=EG.unnull(atrs["ele"],atrs["element"]),	//对象Element
					tn		=EG.unnull(atrs["tn"],atrs["tagName"]),		//标签名
					pn		=EG.unnull(atrs["pn"],atrs["parentNode"]),	//父节点
					cn		=EG.unnull(atrs["cn"],atrs["childNodes"]),	//子节点
					style	=atrs["style"];					//样式

				if(!ele&&!tn) throw new Error("EG.DOM#CE:标签名和ele不能同时为空");

				if(tn){
					//BUGFIX#IE6 iframe动态创建时name无法指定
					if(tn.toUpperCase()=="IFRAME"&&atrs["name"]&&EG.Browser.isIE6()){
						ele=EG.doc.createElement("<iframe name='"+atrs["name"]+"'></iframe>");
					}else{
						if(isSvg){
							ele=EG.doc.createElementNS(DOM.NS_SVG,tn);
						}else if(isVml){
							ele=EG.doc.createElement(tn);
						}else{
							ele=EG.doc.createElement(tn);
						}

						//VML命名空间
						if(isVml){
							ele.xmlns="urn:schemas-microsoft-com:vml";
							//ele.style.behavior='url(#default#VML);';
							//ele.style.behavior='url(#default#VML);';
							atrs["cls"]="vml";
						}

					}
				}

				//赋属性
				for(var key in atrs){
					//过滤关键字
					if(EG.$in(key,DOM.CEKeywords)) continue;

					var atrV=atrs[key];

					//设置Name
					if(key=="name"){
						ele.setAttribute("name",atrV);
						ele.name=atrV;
					//支持x$a方式的子属性赋值
					}else if(key.indexOf("$")>0){
						var si=key.indexOf("$"),
							pre=key.substring(0,si),
							end=key.substring(si+1,key.length);

						if(!ele[pre]){
							ele[pre]={};
						}

						ele[pre][end]=atrV;
					//事件绑定
					}else if(key.indexOf("on")==0&&!EG.String.endWith(key,"Src")){
						EG.bindEvent(ele,key.substr(2).toLowerCase(),atrV);
					//直接赋值
					}else{
						try{
							if(DOM.CENicknames[key]){
								key=DOM.CENicknames[key];
							}

							//SVG:使用attribute:svg的属性
							if(isSvg){
								if(key=="innerText"){
									DOM.removeChilds(ele);
									//ele.removeChilds();
									ele.appendChild(document.createTextNode(atrV));
								}else if(key=="DATA"){
									ele["DATA"]=atrV;
								}else{
									ele.setAttribute(key,atrV);
								}
//							}else if(isVml){
//								ele.setAttribute(key,atrV);
							//默认使用=号来赋值属性
							}else{
								//强行设定属性
								if(setAtr){
									ele.setAttribute(key,atrV);
								}
								ele[key]=atrV;
							}
						}catch(e){
							throw new Error("EG.DOM#CE:不支持对 "+ele.tagName+" 的 "+key+" 赋值。"+e.description);
						}
					}
				}

				//样式设定
				if(style) EG.css(ele,style);

				//父添加
				if(pn){
					if(pn.isItem){
						pn.getElement().appendChild(ele);
					}else{
						pn.appendChild(ele);
					}
				}

				//添加子
				if(cn){
					for(var i=0,il=cn.length;i<il;i++){
						if(EG.isLit(cn[i])) 		cn[i]=EG.CE(cn[i],ext);
						else if(cn[i].isItem) 		cn[i]=cn[i].getElement();
						ele.appendChild(cn[i]);
					}
				}
				return ele;
			},

			/**
			 * 是否为Element
			 * @param {Node} ele 元素
			 * @returns {Boolean}
			 */
			isElement:function(ele) {
				return ele&&ele.nodeType == 1;
			},

			/**
			 * 是否为多个元素数组
			 * @param {Node} ele 元素
			 * @returns {Boolean}
			 */
			isNodeList : function(ele){
				return ele!=null
					&&ele.nodeType==null	//不具有nodeType
					&&ele.length!=null
					&&typeof(ele.length)=="number"
					&&ele.item!=null
				;
			},

			/**
			 * 是否为指定标签的元素
			 *
			 * @param {HTMLElement} ele 元素
			 * @param {String} tagName 标签名
			 * @returns {Boolean}
			 */
			isTag:function(ele,tagName) {
				return ele.tagName.toUpperCase()==tagName.toUpperCase();
			},

			/**
			 * 是否已经被添加在document中
			 * @param {Node} ele 元素
			 * @return {Boolean}
			 */
			isActive:function(ele){
				return DOM.has(EG.getBody(),ele);
			},

			/**
			 * 父元素是否含有子元素
			 * @param {Node} pEle 父元素
			 * @param {Node} cEle 子元素
			 * @return {Boolean}
			 */
			has:function(pEle,cEle){
				var pNode=cEle.parentNode;
				while(pNode!=null){
					if(pNode==pEle) return true;
					pNode=pNode.parentNode;
				}
				return false;
			},

			remove:function(ele){
				if(!EG.Array.isArray(ele)){
					ele=[ele];
				}

				for(var i=0;i<ele.length;i++){
					ele[i].parentNode.removeChild(ele[i]);
				}

			},

			/**
			 * 删除所有子元素
			 * @param {Node} ele 父元素
			 */
			removeChilds:function(ele) {
				while(ele.childNodes&&ele.childNodes.length>0){
					ele.removeChild(ele.childNodes[0]);
				}
				return ele;
			},

			/**
			 * 前一个节点
			 * @param {Node} node 节点
			 */
			preNode:function(node) {
				return DOM._nextNode(node,-1);
			},

			/**
			 * 下一个节点
			 * @param {Node} node 节点
			 */
			nextNode : function(node) {
				return DOM._nextNode(node,1);
			},

			/**
			 * 下一N个节点
			 * @param {Node} node 节点
			 * @param {Number} n 相对位置
			 */
			_nextNode : function(node,n) {
				//检测节点及父节点
				if(!node||!node.parentNode) return null;
				//所有真实节点
				var cns=DOM.childNodes(node.parentNode);
				for(var i=0,il=cns.length;i<il;i++){
					if(cns[i]==node){
						return (i+n<il)?cns[i+n]:null;
					}
				}
			},

			/**
			 * 获取指定子节点
			 * 支持x_x_x_x索引子节点
			 * @param {Node} node 父节点
			 * @param {Number|String} n 坐标
			 */
			childNode:function(node,n){
				if(typeof(n)=="string"){
					var ns=n.split("_");
					for(var i=0,il=ns.length;i<il;i++){
						if(ns[i]!="n") ns[i]=parseInt(ns[i]);
						node=EG.Array.get(DOM.childNodes(node),ns[i]);
					}
					return node;
				}else if(typeof(n)=="number"){
					var cns=DOM.childNodes(node);
					return EG.Array.get(cns,n);
				}throw new Error("EG.DOM#childNode不支持索引类型:"+typeof(n));
			},

			/**
			 * 真实子节点(忽略空白节点、注释等其它节点)
			 * @param {Node} node 父节点
			 * @return {Array}
			 */
			childNodes : function(node) {
				var res=[],cns=node.childNodes;
				for( var i=0,il=cns.length;i<il;i++){
					if(cns[i].nodeType==1) res.push(cns[i]);
				}
				return res;
			},

			/**
			 * 获取Value
			 * @param {HTMLElement|HTMLInputElement} ele 元素
			 * @param {Object} ext 扩展选项,select:getText 获取文本 select:ignoreEmpty 忽略空值
			 * @return {String|Array}
			 */
			getValue : function(ele, ext) {
				// 扩展参数
				ext=ext||{};

				if(typeof(ele)=="string"){
					ele=DOM.$(ele);
				}

				if(ele==null){ throw new Error("EG.DOM#getValue:未找到匹配元素"); }

				var isArray=DOM.isNodeList(ele);
				var tn=(isArray?ele[0]:ele).tagName.toUpperCase();

				if(tn=="INPUT"){

					var type=(isArray?ele[0]:ele).type.toUpperCase();

					if(type=="TEXT"||type=="PASSWORD"||type=="HIDDEN"||type=="FILE"){
						if(isArray) throw new Error("EG.DOM#getValue:暂不支持数组Element INPUT "+type);
						return ele.value;
					}else if(type=="RADIO"){
						if(isArray){
							for( var i=0,il=ele.length;i<il;i++){
								if(ele[i].checked==true){ return ele[i].value; }
							}
						}else{
							return (ele.checked==true)?ele.value:null;
						}
					}else if(type=="CHECKBOX"){
						if(isArray){
							var vs=[];
							for( var i=0,il=ele.length;i<il;i++){
								if(ele[i].checked==true) vs.push(ele[i].value);
							}
							return vs;
						}else{
							return (ele.checked==true)?ele.value:null;
						}
					}else{
						throw new Error("EG.DOM#getValue:不支持input "+type+"类型");
					}
				}else if(tn=="SELECT"){
					if(isArray) throw new Error("EG.DOM#getValue:暂不支持数组Element SELECT");
					// 扩展
					var extGetText=ext["getText"]!=null?ext["getText"]:false; 				// 提取文本
					var extIgnoreEmpty=ext["ignoreEmpty"]!=null?ext["ignoreEmpty"]:false; 	// 是否忽略空值

					var opts=ele.options;
					var vs=[];
					var multiple=ele.multiple;
					for( var i=0,il=opts.length;i<il;i++){
						if(opts[i].selected==true){
							var v=null;
							if(extIgnoreEmpty&&opts[i].value=="") v=null;
							else v= (extGetText)?opts[i].text:opts[i].value;
							if(multiple){
								vs.push(v);
							}else{
								return v;
							}
						}
					}

					if(multiple) return vs;
					else return null;
				}else if(tn=="TEXTAREA"){
					if(isArray){
						var vs=[];
						for(var i=0,il=ele.length;i<il;i++){
							vs.push(ele.value);
						}
						return vs;
					}else{
						return ele.value;
					}
				}else if(ele.innerHTML!=null){
					if(isArray){
						var vs=[];
						for(var i=0,il=ele.length;i<il;i++){
							vs.push(ele.innerHTML);
						}
						return vs;
					}else{
						return ele.innerHTML;
					}
				}else{
					throw new Error(" EG.DOM#getValue:不支持其它类型值");
				}
				return null;
			},

			/**
			 * 获取输入组件的数值,返回字面量对象
			 * @param {HTMLElement} ele 容器
			 */
			getValues : function(ele) {
				var c={};
				var e=DOM.$(ele);
				var es=ele.getElementsByTagName("INPUT");
				es.concat(ele.getElementsByTagName("SELECT"));
				es.concat(ele.getElementsByTagName("TEXTAREA"));
				for( var i=0,il=es.length;i<il;i++){
					if(e[i].id==""&&e[i].name=="") continue;
					var key=e[i].id!=""?e[i].id:e[i].name;
					c[key]=DOM.getValue(key);
				}
				return c;
			},

			/**
			 * 设置数值
			 * @param {HTMLElement|HTMLInputElement} ele 元素
			 * @param {String|Array} value 数值
			 * @param {?Object} ext 扩展
			 */
			setValue : function(ele, value, ext) {
				ext=ext||{};

				if(typeof (ele)=="string"){
					ele=EG.$(ele);
				}
				if(ele==null) throw new Error("EG.DOM#setValue未找到匹配元素");

				var isArray=DOM.isNodeList(ele);
				var tn=(isArray?ele[0]:ele).tagName.toUpperCase();

				if(value==null) value="";

				if(tn=="INPUT"){
					var type=ele.type.toUpperCase();

					if(type=="TEXT"||type=="PASSWORD"||type=="HIDDEN"){
						ele.value=value;
					}else if(type=="RADIO"){
						if(isArray){
							for( var i=0,il=ele.length;i<il;i++)
								if(ele[i].value==value){
									ele[i].checked=true;
									break;
								}
						}else if(ele.value==value) ele.checked=true;
					}else if(type=="CHECKBOX"){
						var spliter=ext["spliter"]||",";
						if(value instanceof Array){
							if(!isArray) throw new Error("EG.DOM#setValue:值为数组时,Element类型必须为数组");
							for( var i=0,il=ele.length;i<il;i++){
								if(EG.Array.has(value,ele[i].value)){
									ele[i].checked=true;
								}
							}
						}else{
							if(isArray){
								for( var i=0,il=ele.length;i<il;i++){
									if(value==ele[i].value) ele[i].checked=true;
								}
							}else{
								if(ele.value==value) ele.checked=true;
							}
						}
					}else throw new Error("Engin#setValue:不支持该input "+type+"类型");
				}else if(tn=="SELECT"){
					var extCmpText=ext["cmpText"]!=null?ext["cmpText"]:false; // 文本比对
					var fireOnchange=ext["fireOnchange"]!=null?ext["fireOnchange"]:true;//自动触发onchange事件
                    var opts=ele.options;
					for( var i=0,il=opts.length;i<il;i++){
						if(		( extCmpText&&opts[i].text==value)
							||	(!extCmpText&&opts[i].value==value)){
							opts[i].selected=true;
							if(fireOnchange){
                                EG.Event.fireEvent(ele,"onchange");
                            }
							return;
						}
					}
				}else if(tn=="TEXTAREA"){
					ele.value=value;
				}else if(ele.innerHTML!=null){
					ele.innerHTML=value;
				}else{
					throw new Error(" EG.DOM#setValue:不支持对其它类型设值");
				}
			},

			/**
			 * 根据字面量对象，寻找组件并设置对应值
			 * @param {Object} data 数据
			 */
			setValues : function(data) {
				if(data!=null) {
					for(var key in data){
						DOM.setValue(key,data[key]);
					}
				}
			},

			/**
			 * 移除Select Options
			 * @param {HTMLElement} ele 元素
			 */
			removeOptions : function(ele) {
				ele = EG.$(ele);
				if(!DOM.isTag(ele, "SELECT")) throw new Error("EG.DOM#removeOptions:待删除的对象非Select组件");
				DOM.removeChilds(ele);
			},

			/**
			 * 添加option
			 * @param {HTMLElement} ele 元素
			 * @param {Array} data 数据
			 * @param {?String|?Function} textKey 文本键值
			 * @param {?String|?Function} valueKey 数据键值
			 * @param {?String|?Function} attributeName 属性名
			 * @param {?String|?Function} attributeKey 属性值
			 */
			addOptions 	: function(ele, data, textKey,valueKey, attributeName,attributeKey) {
				ele = EG.$(ele);
				valueKey = valueKey||1;
				textKey = textKey||0;
				for ( var i = 0, il = data.length; i < il; i++) {
					var opt = EG.CE({tn:"option",
						value		:(typeof(valueKey)=="function") ?valueKey(data[i])	:data[i][valueKey],
						innerHTML	:(typeof(textKey) =="function") ?textKey(data[i])	:data[i][textKey]
					});
					if (attributeName&&attributeKey){
						opt[attributeName]=(typeof (attributeKey) == "function") ? attributeKey(data[i]):data[i][attributeKey];
					}

					ele.options[ele.options.length] = opt;
				}
			},

			/**
			 * 获取值对应的Option
			 * @param {HTMLElement} ele 元素
			 * @param {String} val 数值
			 * @return {HTMLOptionElement}
			 */
			getOption:function(ele,val){
				var opts=ele.options;
				for(var i= 0,il=opts.length;i<il;i++){
					var opt=opts[i];
					if(opt.value==val) return opt;
				}
			},

			/**
			 * 获取已选中的Option
			 * @param {HTMLElement} ele 元素
			 * @return {HTMLOptionElement}
			 */
			getSelectedOption:function(ele){
				return ele.options[ele.options.selectedIndex];
			},

			/**
			 * 删除Option
			 * @param {HTMLElement} ele 元素
			 * @param {String|Number|Object} n 位置
			 */
			removeOption:function(ele,n){
				var model=null,v;
				if(typeof(n)=="string"){
					n={value:n};
				}

				if((v=n["idx"])!=null){
					model="idx";
				}else if((v=n["value"])!=null){
					model="value";
				}else if((v=n["text"])!=null){
					model="text";
				}else throw new Error("EG.DOM#removeOption:参数不正确");

				var opts=ele.options;
				//多重的删除
				if(EG.isArray(v)){
					for(var i=opts.length-1;i>=0;i--){
						for(var j=0,jl=v.length;j<jl;j++){
							if((   model=="idx"&&i==v[j])
								||(model=="value"&&v[j]==opts[i].value)
								||(model=="text"&&v[j]==opts[i].text)){
								ele.removeChild(opts[i]);
								break;
							}
						}
					}
				}else{
					for(var i=opts.length-1;i>=0;i--){
						if((   model=="idx"&&i==v)
							||(model=="value"&&v==opts[i].value)
							||(model=="text"&&v==opts[i].text)){
							ele.removeChild(opts[i]);
						}
					}
				}
			},

			/**
			 * 获取文本键值数组
			 * @param {HTMLElement} ele 元素
			 * @param {?Object} ext 扩展,{egnoreEmpty:true}
			 * @return {Array}
			 */
			getTextvalues:function(ele,ext){
				ext=ext||{};
				var egnoreEmpty=ext["egnoreEmpty"]!=null?ext["egnoreEmpty"]:true;
				var tvs=[];
				var opts=ele.options;
				for(var i=0,il=opts.length;i<il;i++){
					if(egnoreEmpty&&opts[i].value=="") continue;
					tvs.push([opts[i].text,opts[i].value]);
				}
				return tvs;
			},

			/**
			 * 批量选择box,寻找同name,同值的box设置
			 * @param {String} boxName box名称
			 * @param {?Boolean} checked 是否选择
			 * @param {?String} value 数值
			 */
			selectBoxes : function(boxName,checked,value) {
				if(!checked) checked=true;
				var boxes = EG.doc.getElementsByName(boxName);
				for ( var i = 0, il = boxes.length; i < il; i++) {
					if(value!=null) boxes[i].checked = (boxes[i].value = value)?checked:!checked;
					else boxes[i].checked=checked;
				}
			},

			/**
			 * 移除所有Row
			 * @param {HTMLElement} ele 元素
			 */
			removeAllRows : function(ele) {
				ele = EG.$(ele);
				if (ele == null) return;
				DOM.removeChilds(ele);
			},

			/**
			 * 获取隐藏处理的ActionFrame
			 * @return {HTMLIFrameElement}
			 */
			getActionFrame:function(){
				if (!EG.DOM.actionFrame) {
					EG.DOM.actionFrame=EG.CE({pn:EG.getBody(),tn:"iframe",id:"actionFrame",name:"actionFrame",style:"display:none"});
				}
				return EG.DOM.actionFrame;
			},

			/**
			 * 获取元素坐标
			 * @param ele
			 * @return {Number}
			 */
			getIdx:function(ele){
				var cns=DOM.childNodes(ele.parentNode);
				for(var i=0;i<cns.length;i++){
					if(cns[i]==ele) return i;
				}
				throw new Error("未找到索引");
			}
		}
	});

	var DOM=EG.DOM;
	EG.CSVG		=EG.DOM.CSVG;
	EG.CVML		=EG.DOM.CVML;
	EG.$ 		=EG.DOM.$;
	EG.CE 		=EG.DOM.CE;
	EG.getValue =EG.DOM.getValue;
	EG.setValue =EG.DOM.setValue;
}());


/**
 * @class EG.Style
 * @author bianrongjun
 * 样式操作类
 */
(function(){

	var getComputedStyle=window.getComputedStyle									//新版本 Firefox Safari Chrome Opera
		||(EG.doc&&EG.doc.defaultView&&EG.doc.defaultView.getComputedStyle);	//老版本
	EG.define("EG.Style",{
		statics:{
			/**
			 * CSS常用值
			 */
			c:{
				dv:"display:inline-block;vertical-align:middle;"
			},
			debugSize:function(ele){
				if(ele.getElement) ele=ele.getElement();
				alert(EG.toJSON(EG.getSize(ele)));
			},
			/**
			 * 解析transform
			 * @param {String} transform 值
			 * @return {Object}
			 */
			parseTransform:function(transform){
				var m={};
				var regex=new RegExp("([a-zA-Z0-9]+)\\(([^)]*)\\)","ig");
				if(!transform) return m;
				var as=transform.match(regex);
				if(!as) return m;
				for (var i=0;i<as.length ;i++ ){
					var a=as[i];
					var k=a.replace(regex,"$1");
					var vs=null;

					var ss=a.replace(regex,"$2");

					//FIX-IE:IE下transform中间用空格而不是逗号
					if(EG.Browser.isIE()){
						ss=ss.replace(new RegExp(" ","ig"),",");
					}

					//alert(a+"\n"+ss);
					eval("vs=["+ss+"]");
					m[k]=vs;
				}
				return m;
			},
			/**
			 * 尺寸转数字
			 * @param {String|Number} size 尺寸
			 * @param {Number} refferNum 参考尺寸
			 * @returns {Number}
			 */
			size2Num:function(size,refferNum){
				if(typeof(size)=="string"){
					if(EG.String.endWith(size,"%")){
						if(refferNum==null) Error("EG.Style#size2Num:百分比时参考尺寸不能为空.");
						return parseInt(refferNum*parseInt(size.substr(0,size.length-1))/100);
					}else if(EG.String.endWith(size,"px")){
						return parseInt(size.substr(0,size.length-2));
					}else if(size=="thin"){
						return 1;
					}else if(size=="medium"){
						return 3;
					}else if(size=="thick"){
						return 5;
					}else if(size=="auto"){//TODO 需要根据属性类型判定
						return 0;
					}else if(size==""){
						return 0;
					}else{
						throw new Error("EG.Style#size2Num:暂不支持数值转换."+size);
					}
				}else if(typeof(size)=="number"){
					return size;
				}else throw new Error("EG.Style#size2Num:参数类型无法识别."+typeof(size));
			},

			/**
			 * 获取元素的尺寸
			 *
			 * @param {HTMLElement|EG.ui.Item} ele 元素
			 * @param {Boolean?} css 使用CSS值计算
			 * @return {Object}
			 */
			getSize:function(ele,css){
				if(ele.getElement) ele=ele.getElement();
				var cs=Style.current(ele);
				var size={
					offsetWidth		:ele.offsetWidth,
					offsetHeight	:ele.offsetHeight,
					clientWidth		:css?cs.width:ele.clientWidth,
					clientHeight	:css?cs.height:ele.clientHeight,
					borderLeft		:(cs.borderLeftStyle	!="none")?(Style.size2Num(cs.borderLeftWidth))	:0,
					borderTop		:(cs.borderTopStyle		!="none")?(Style.size2Num(cs.borderTopWidth))	:0,
					borderRight		:(cs.borderRightStyle	!="none")?(Style.size2Num(cs.borderRightWidth))	:0,
					borderBottom	:(cs.borderBottomStyle	!="none")?(Style.size2Num(cs.borderBottomWidth)):0,
					paddingLeft		:(Style.size2Num(cs.paddingLeft)),
					paddingTop		:(Style.size2Num(cs.paddingTop)),
					paddingRight	:(Style.size2Num(cs.paddingRight)),
					paddingBottom	:(Style.size2Num(cs.paddingBottom))
				};

//				if(EG.Browser.isIE6()){
//					if(cs.marginLeft=="auto"){
//						//var pSize=Style.getSize(ele.parentNode);
//						//pSize
//					}
//				}else{

//				}
				size.marginLeft		=(Style.size2Num(cs.marginLeft));
				size.marginTop		=(Style.size2Num(cs.marginTop));
				size.marginRight	=(Style.size2Num(cs.marginRight));
				size.marginBottom	=(Style.size2Num(cs.marginBottom));

				size.vScrollWidth	=size.offsetWidth	-size.clientWidth	-size.borderLeft	-size.borderRight;//  size.offsetHeight	+size.marginTop		+size.marginBottom;
				size.hScrollWidth	=size.offsetHeight	-size.clientHeight	-size.borderTop		-size.borderBottom;//	+size.marginTop		+size.marginBottom;

				size.innerWidth		=size.clientWidth	-size.paddingLeft	-size.paddingRight;
				size.innerHeight	=size.clientHeight	-size.paddingTop	-size.paddingBottom;
				size.outerWidth		=size.offsetWidth	+size.marginLeft	+size.marginRight;
				size.outerHeight	=size.offsetHeight	+size.marginTop		+size.marginBottom;

				return size;
			},

			getInnerTop		:function(s){return Style.getInnerOut(s,"Top");},
			getInnerBottom	:function(s){return Style.getInnerOut(s,"Bottom");},
			getInnerLeft	:function(s){return Style.getInnerOut(s,"Left");},
			getInnerRight	:function(s){return Style.getInnerOut(s,"Right");},
			getInnerOut:function(s,type){
				return s["margin"+type]+s["padding"+type]+s["border"+type];
			}
			,
			/**
			 * 数字转尺寸
			 * @param {Number|String} num 数字
			 * @returns {String}
			 */
			num2Size:function(num){
				if(typeof(num)=="number"){
					return num+"px";
				}else if(typeof(num)=="string"){
					return num;
				}else throw new Error("EG.Style#num2Size:参数类型无法识别."+typeof(num));
			},
			
			/**
			 * 创建样式
			 * @param {Object} styles 样式
			 */
			create:function(styles) {
				var ss = "";
				for (var key in styles){
					ss += (key + "{" + styles[key] + "}\n");
				}

				if (Style.element.styleSheet)
					Style.element.styleSheet.cssText += ss;
				else
					Style.element.appendChild(EG.doc.createTextNode(ss));// IE6
			},
			
			/**
			 * 创建样式
			 * @param {String} url 路径
			 * @returns {HTMLStyleElement}
			 */
			createSheet:function(url) {
				var css=EG.CE({tn:"link",rel:"stylesheet",rev:"stylesheet",type:"text/css",media:"screen",href:url});
				EG.doc.getElementsByTagName("head")[0].appendChild(css);
				return css;
			},
			
			/**
			 * 获取Element的计算后的当前样式
			 * @param {HTMLElement} ele 元素
			 * @returns {Object}
			 */
			current:function(ele) {
				if(getComputedStyle){				//Support Firefox Safari Chrome Opera
					return getComputedStyle(ele);
				}else{
					return ele.currentStyle;		//IE6 IE7 IE8 Opera
				}
			}
			,
			/**
			 * 设置Element的样式
			 * @param {HTMLElement|EG.ui.Item} ele 元素
			 * @param {String|Object} style 样式
			 */
			css:function(ele, style) {

				if (ele == null) {
					throw new Error("EG.Style#set:ele不能为空");
				}else if(ele.getElement){
					ele=ele.getElement();
				}

				var m = {};
				if (typeof (style) == "string") {
					var ses = style.split(";");
					for(var i=0;i<ses.length;i++){
						var sp=EG.String.trim(ses[i]);
						if(sp=="") continue;
						var sess=sp.split(":");
						if(sess[0].indexOf("-")>0){// BUGFIX:line-height=>lineHeight,非IE6时 可以不用转换
							var sesss=sess[0].split("-");
							sess[0]=sesss[0]+sesss[1].substring(0,1).toUpperCase()+sesss[1].substring(1);
						}
						m[EG.String.trim(sess[0])]=EG.String.trim(sess[1]);
					}
				}else if(typeof (style)=="object"){
					m=style;
				}else{
					throw new Error("EG.Style#set:style类型错误");
				}

				//TODO 用setAttribute来可以屏蔽float特殊的兼容性
				for(var k in m){
					var v=m[k];
					if(k=="float"){//float是Javascript关键字
						if(EG.Browser.isFirefox()||EG.Browser.isChrome()) k="cssFloat";
						else k="styleFloat";
					}else if(k=="vertical-align") k="verticalAlign";// BUGFIX:Firefox下float为cssFloat

					try{
						ele.style[k]=v;
					}catch(e){
						throw new Error(ele.tagName+"不支持属性"+k+"的值为:"+v);
					}
				}
			},
			
			/**
			 * 是否Element已隐藏
			 * inherit 支持判断该节点是否因上层被隐藏
			 * @param ele {HTMLElement}
			 * @param inherit {Boolean?}
			 */
			isHide:function(ele,inherit) {
				if(!inherit){
					var cs=Style.current(ele);
					return cs.display == "none";
				}else{
					var p=ele;
					while(p){
						if(p==EG.getBody()) return false;

						var cs=Style.current(p);
						if(cs.display == "none"||cs.visible == "none") return true;
						p=p.parentNode;
					}
					return true;
				}
			},

			/**
			 * 显示Element 支持多个参数
			 */
			show:function() {
				Style.displays(arguments, true);
			},
			
			/**
			 * 隐藏Element 支持多个参数
			 */
			hide:function() {
				Style.displays(arguments, false);
			},
			
			/**
			 * 显示
			 * @param eles
			 * @param visible
			 */
			visibles:function(eles,visible){
				for(var i=0,il=eles.length;i<il;i++){
					Style.visible(eles[i],visible);
				}
			},
			
			/**
			 * 显示
			 * @param ele
			 * @param visible
			 */
			visible:function(ele,visible){
				ele.style.visibility=visible?"visible":"hidden";
			},
			
			/**
			 * 设置Element的display
			 * @param eles
			 * @param display
			 */
			displays:function(eles,display){
				for(var i=0,il=eles.length;i<il;i++){
					Style.display(eles[i],display);
				}
			},
			
			/**
			 * 设置Element的display
			 *
			 * @param ele {HTMLElement}
			 * @param display 是否显示
			 */
			display :function(ele, display) {

				if(ele.getElement) ele=ele.getElement();

				if(typeof(display)=="boolean"){
					var cs=Style.current(ele);
					if(!cs) return;
					if(!display){//隐藏
						if(cs.display!="none"){
							//if(ele.id=="AAA") alert(""+cs.display);
							ele.oDisplay=cs.display;
						}
						ele.style.display="none";
					}else{
						ele.style.display=ele.oDisplay||"";
					}
				}else if(typeof(display)=="string"){
					ele.style.display=display;
				}else{
					throw new Error("EG.Style#display:不支持"+display);
				}
			},

			/**
			 * 居中子元素
			 * @param {HTMLElement} pn 父元素
			 * @param {Boolean?} horizontal 是否横向并排
			 */
			centerChilds:function(pn,horizontal){
				//加起来所有子元素的outerWidth,设置第一个margin-left和最后一个的margin-right为0
				//var cns=this.container.getItemContainer().childNodes;
				var cns=pn.childNodes;
				if(cns.length>0){
					//水平
					if(horizontal){
						var mw=0;
						for(var i=0;i<cns.length;i++){
							var cn=cns[i];
							var s=EG.getSize(cn);
							if(i==0){
								mw+=s.outerWidth-s.marginLeft-s.marginRight;
							}else{
								var sl=EG.getSize(cns[i-1]);
								//取出最大的magrin重叠区
								var lm=Math.max(sl.marginRight,s.marginLeft);
								mw+=lm+s.outerWidth-s.marginRight-s.marginLeft;
							}
						}

						var m=parseInt((EG.getSize(pn).innerWidth-mw)/2);
						EG.css(cns[0],"margin-left:"+m+"px");
						EG.css(cns[cns.length-1],"margin-right:"+m+"px");
					}else{
						alert("暂时不支持");
					}
				}

			},

			/**
			 * 垂直居中
			 * @param {HTMLElement} pn 父元素
			 * @param {Boolean?} horizontal 是否横向并排
			 */
			middleChilds:function(pn,horizontal){
				var cns=pn.childNodes;
				var ps=EG.getSize(pn);
				if(cns.length>0){
					if(horizontal){
						//每个元素用垂直高度间隔的一半
						for(var i=0;i<cns.length;i++){
							var cn=cns[i];
							var s=EG.getSize(cn);
							var m=parseInt((ps.innerHeight-(s.outerHeight-s.marginTop-s.marginBottom))/2)
							EG.css(cn,"margin-top:"+m+"px;margin-bottom:"+m+"px");
						}
					}else{
						alert("暂不支持");
					}
				}
			},

			/**
			 * 将指定Element以某容器为参照物居中
			 *
			 * @param ele Element
			 * @param ct ct为参照物,默认为父元素,
			 */
			center:function(ele, ct) {
				var tn = ele.tagName.toUpperCase();
				if (tn == "DIV") {
					ct = ct||ele.parentNode;
					ele.style.position="absolute";
					ele.style.marginLeft="0px";
					//alert(EG.getSize(ct).innerWidth+":"+ele.clientWidth)
					ele.style.left = parseInt((EG.getSize(ct).innerWidth - EG.getSize(ele).outerWidth) / 2) + "px";
				} else
					throw new Error("EG.Style#center:暂不支持该元素:" + tn);
			},
			
			/**
			 * 垂直居中
			 * @param ele
			 * @param ct
			 */
			middle:function(ele, ct) {
				var tn = ele.tagName.toUpperCase();
				if (tn == "DIV") {
					ct = ct||ele.parentNode;
					ele.style.position="absolute";
					ele.style.marginTop="0px";
					ele.style.top = parseInt((ct.offsetHeight - EG.getSize(ele).offsetHeight) / 2) + "px";
				} else
					throw new Error("EG.Style#center:暂不支持该元素:" + tn);
			},
			
			/**
			 * 撑满对象
			 * @param ele
			 * @param ct
			 */
			full:function(ele, ct) {
				if(!ct) ct=ele.parentNode;
				if(!ct) throw new Error("EG.Style#full:父元素不能为空");
				ele.style.width=ct.clientWidth+"px";
				if(ct==EG.getBody()){
					ele.style.height=Math.max(
						(EG.doc.documentElement?EG.doc.documentElement.clientHeight:EG.getBody().clientHeight),
						EG.getBody().clientHeight)
						+"px";
				}else{
					ele.style.height=ct.clientHeight+"px";
				}
			},
			
			/**
			 * 移动到某坐标
			 * @param ele
			 * @param pos
			 */
			moveTo:function(ele, pos){
				if(pos.x) Style.css(ele,"left:"+pos.x+"px");
				if(pos.y) Style.css(ele,"top:"+pos.y+"px");
			},
			
			/**
			 * 渐变
			 *
			 * @param obj 对象
			 * @param start 起始值
			 * @param end 结束值
			 * @param callback 回调
			 * @param speed 速度
			 */
			fade:function(obj, start, end, callback, speed) {
				if (!speed) speed = 20;
				var _a = (start > end) ? -5 : 5;
				if (EG.doc.all) {
					obj.style.filter = "alpha(opacity=" + parseInt(start + _a) + ")";
				} else {
					obj.style.opacity = parseInt(start + _a) / 100;
				}
				if (start == end) {
					if (!callback) return;
					return callback();
				}
				window.setTimeout(function() {
					Style.fade(obj, start + _a, end, callback, speed);
				}, speed);
				return null;
			},

			/**
			 * 设置样式类
			 * 支持样式名前缀,例如 (el,[aa,bb],pre) 形成 pre-aa pre-bb
			 * @param ele
			 * @param cls
			 * @param clsPre
			 */
			setCls:function(ele,cls,clsPre){
				if(cls==null) return;
				clsPre=clsPre?(clsPre+"-"):"";
				if(!EG.isArray(cls)) cls=[cls];
				if(cls.length==0) return;
				var s="";
				for(var i=0;i<cls.length;i++){
					if(i!=0) s+=" ";
					s+=clsPre+cls[i];
				}
				ele.className = s;
			},

			/**
			 * 添加样式
			 * @param ele 元素
			 * @param cls 样式
			 */
			addCls:function(ele,cls){
				var clss = ele.className.split(' ');
				if(EG.Array.has(clss,cls)) return;
				clss.push(cls);
				ele.className = clss.join(' ');
			},

			/**
			 * 移除样式
			 * @param ele 元素
			 * @param cls 样式
			 */
			removeCls:function(ele,cls){
				var clss = ele.className.split(' ');
				if(!EG.Array.has(clss,cls)) return;
				EG.Array.remove(clss,cls);
				ele.className = clss.join(' ');
			}
		}
	});

	var Style=EG.Style;
	EG.getSize=Style.getSize;
	EG.debugSize=Style.debugSize;
	EG.css=Style.css;
	EG.hide=Style.hide;
	EG.show=Style.show;
	EG.setCls=Style.setCls;
	// 创建动态样式表
	EG.onload(function() {
		Style.element = EG.doc.createElement("style");
		Style.element.type = "text/css";
		EG.doc.getElementsByTagName("HEAD").item(0).appendChild(Style.element);
		//IE6附加
		if(EG.Browser.isIE6()){
			Style.c.dv+="*display:inline;zoom:1;";
		}
	});
	
})();
/**
 * @class EG.$Q
 * @author bianrongjun
 * JQuery仿真
 */
(function(){
	EG.define("EG.$Q",{
		constructor:function(selector){
			return new EG.$Q.prototype._init(selector);
		},
		/**
		 * 初始化
		 * @param {String|HTMLElement} selector 选择表达式
		 * @private
		 */
		_init:function(selector){
			this.ele=EG.$(selector);
			return this;
		},
		/**
		 * 获值&&设值
		 * @param {String} value 数值
		 */
		val:function(value){
			if(arguments.length==0) return EG.DOM.getValue(this.ele);
			else return EG.DOM.setValue(this.ele,value);
		},
		/**
		 * 显示
		 */
		show:				function()			{EG.show(this.ele);return this;},
		/**
		 * 隐藏
		 */
		hide: 				function()			{EG.hide(this.ele);return this;},
		/**
		 * 居中
		 */
		center:				function(ct)		{EG.Style.center(this.ele,ct);return this;},
		/**
		 * 是否有指定的样式
		 */
		hasClass:			function(className)	{return this.ele.className.indexOf(className)>=0;},
		/**
		 * 移除所有子节点
		 */
		removeChilds:		function()			{return EG.DOM.removeChilds(this.ele);},
		/**
		 * 增加子节点
		 * @param {HTMLElement} obj 子节点
		 */
		appendChild:		function(obj)		{this.ele.appendChild(obj);return this;},
		/**
		 * 设置innerHTML
		 * @param {String} html html
		 */
		innerHTML:			function(html)		{this.ele.innerHTML=html;return this;},
		/**
		 * 获取指定子
		 * @param {Number} n 数值
		 */
		getChild:			function(n)			{return this.ele.childNodes[n];},
		/**
		 * 设置样式
		 * @param {String} style 样式
		 */
		css:				function(style)		{EG.css(this.ele,style);return this;}
	});

	//增加事件
	(function(){
		var events=["click","dblclick","mouseover","mouseout"];
		for(var i=0;i<events.length;i++){
			EG.$Q.prototype[events[i]]=function(fn){
				var me=this;
				EG.bindEvent(this.ele,function(){
					fn.apply(me);	
				});
				return this;
			};
		}
	})();

    /**
     * 挂接
     */
    EG.$Q.prototype._init.prototype=EG.$Q.prototype;
})();

/**
 * @class EG.MMVC
 * @author bianrongjun
 * Nobject.org的MiniMVC框架的远程请求操作类
 */
(function(){
	EG.define("EG.MMVC",{
		statics:{
			mmvcPath:"/mmvc/"
			,
			/**
			 * 获取MMVC请求路径集合
			 * @returns {Object}
			 * */
			getPath:function(uri){
				if(uri==null) uri=EG.MMVC.mmvcPath;
				return {
					call		:uri+"jsonrpc/call",
					connect		:uri+"push/connect",
					moditor		:uri+"utils/profile",
					upload		:uri+"upload",
					download	:uri+"download"
				};
			}
			,
			/**
			 * 远程调度
			 * 是:正确结果->[0,返回结果],错误结果->[1,{exClass:XXX,exMsg:XXX}EG.MMVC.Exception]
			 * 否:正确结果->返回结果,错误结果->EG.MMVC.Exception
			 * @param {Object} cfg 请求参数
			 */
			call:function(cfg) {//TODO 将参数简化为onException,name,method,params,post
				cfg=cfg||{};
				var muti		=cfg["muti"],
					rpc			=cfg["rpc"],
					method		=cfg["method"],
					params		=EG.unnull(cfg["params"],[]),
					exHandler	=cfg["exHandler"],
					cb			=cfg["callback"],
					httpPost	=EG.unnull(cfg["httpPost"],[]),
					mutiReturn	=cfg["mutiReturn"]||"map"
				;

				if(!muti&&EG.String.isBlank(rpc)) 		throw new Error("EG#call:rpc不能为空");
				if(!muti&&EG.String.isBlank(method)) 	throw new Error("EG#call:method不能为空");

				var url			=cfg["callPath"]||EG.MMVC.getPath().call;
				
				//参数转换
				var strParams="";
				if(muti){
					strParams=(mutiReturn!="map"?"mutiReturn="+mutiReturn+"&":"")+"muti="+EG.Ajax.javaURLEncoding(EG.Tools.toJSON(muti));
				}else{
					strParams="rpc="+rpc+"&method="+method+"&params="+EG.Ajax.javaURLEncoding(EG.Tools.toJSON(params));
				}
						
				var content		=null;

				if(!httpPost) 	url+="?"+strParams;
				else content	=strParams;

				//处理异常
				var handleEx=function(ex){
					if(EG.MMVC.exClassHandlers[ex["exClass"]]){
						return EG.MMVC.exClassHandlers[ex["exClass"]](ex);
					}else{
						if(exHandler) return exHandler(ex);
						else if(EG.MMVC.defExHandler){
							return EG.MMVC.defExHandler(ex);
						}else{
							throw new Error(ex.exMsg);
						}
					}
				};

				//回调处理
				var callback=function(resText,req){
					var obj=null;
					if(resText != null && resText != "")  eval("obj=" + resText + ";");
					//打包分拆
					if(obj!=null){
						if(!EG.isArray(obj)) throw new Error("结构非数组:"+resText);
						if(obj[0]===0){
							return cb(obj[1]);
						}else{
							return handleEx(new EG.MMVC.Exception(obj[1]["exClass"],obj[1]["exMsg"]));
						}
					}else{
						return cb(obj);
					}
				};
				return EG.Ajax.send({
					url		:url,
					httpPost:httpPost,
					content	:content,
					callback:callback
				});
			}
			,
			/**
			 * 异常类型处理器
			 */
			exClassHandlers:{}
			,
			/**
			 * 默认异常处理器
			 */
			defExHandler:null
			,
			/**
			 * 发起一个直连请求
			 * @param {Object} cfg 连接参数
			 */
			connect:function(cfg){
				cfg=cfg||{};
				var actionFrame=cfg["actionFrame"];
				if(!actionFrame) actionFrame=EG.DOM.getActionFrame();
				actionFrame.src=EG.MMVC.getPath().connect;
			}
			,
			/**
			 * 发起一个下载请求
			 * @param {Object} cfg 连接参数
			 */
			download:function(cfg){
				var actionFrame	=cfg["actionFrame"];
				var policy		=cfg["policy"];
				var params		=cfg["params"]||{};
				var ps="";
				for(var key in params){
					ps+="&"+key+"="+EG.Ajax.javaURLEncoding(params[key]);
				}
				if(!actionFrame) actionFrame=EG.DOM.getActionFrame();

				actionFrame.src=EG.MMVC.getPath().download+"?policy="+policy+ps;
			}
		}
	});

	/**
	 * MMVC 异常类型
	 * @param {String} exClass 异常类型
	 * @param {String} exMsg 异常消息内容
	 * @constructor
	 */
	EG.MMVC.Exception=function(exClass,exMsg){
		this.exClass = exClass;this.exMsg = exMsg;
	};

	//快捷
	EG.call=EG.MMVC.call;
})();
