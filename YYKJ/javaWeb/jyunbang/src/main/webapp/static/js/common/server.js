 /**
     * ajax封装
	 * 需要引入jQuery
     * dataType 预期服务器返回的数据类型，常用的如：xml、html、json、text
     * successfn 成功回调函数
     * errorfn 失败回调函数
	 * actionScope 作用域
	 * async 是否异步，如果不传值，默认是异步
	 * dataArray 所调用的方法名的参数必须是一一对应，而且顺序也必须要一致
	 * methodName 调用的方法名
	 * beanName spring所管理的bean的id
	 * beanAndMethod 所传过来的格式'spring管理的bean的id?调用的方法名' eg:'beanName?methodName'
	 * 
	 * demo
	 * 
	 * var data=['301',"${ids}",false];
	 * $.callService('internalStuInfoService','stusSexComposition',data,function(d){
	 *		sexConfigration(d);
	 *	});
     */
 		var basePath;
	   var  urlPath;
$(function(){
	
	 jQuery.callService=function(beanName, methodName, dataArray, successfn, errorfn, dataType, actionScope, async) {
		 var args={};
		 async = (async==null || typeof(async)=="undefined")? true : async;
		 if(beanName && methodName) {
			  args= {beanName : beanName, methodName : methodName};
			 if(dataArray) {
				 args.dataArray = dataArray;
			 };
		 }	
		 var getRootPath=function(){
		    //获取当前网址，如： http://localhost:8088/test/test.jsp
		    var curPath=window.document.location.href;
		    //获取主机地址之后的目录，如： test/test.jsp
		    var pathName=window.document.location.pathname;
		    var pos=curPath.indexOf(pathName);
		    //获取主机地址，如： http://localhost:8088
		    var localhostPaht=curPath.substring(0,pos);
		    //获取带"/"的项目名，如：/test
		    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		    return(localhostPaht+projectName);
		};
	     curPath=window.document.location.href;
	  
	    basePath=getRootPath();
	   var urlPath=curPath.replace(basePath,"");
        $.ajax({
            type: "POST",
            async: async,
            data: {params:JSON.stringify(args)},
            url: getRootPath()+"/common/getData",
            dataType: dataType || "json",
            success: function(d){
				if(successfn){
					successfn.call(actionScope, d);
				}
            },
            error: function(e){
                if(errorfn) {
					errorfn.call(actionScope, e);
				}
            }
        });
    };
	 
    
/*	 jQuery.callServiceOneParam=function(beanAndMethod, dataArray, successfn, errorfn, dataType, actionScope, scync) {
		 var args;
		 var c=beanAndMethod.split('?');
		 var beanName=c[0];
		 var methodName=c[1];
		 if(beanName && methodName) {
			  args= {beanName : beanName, methodName : methodName};
			 if(dataArray) {
				 args.dataArray = dataArray;
			 };
		 }	
		 
        $.ajax({
            type: "POST",
            async: scync || true,
            data: {params:JSON.stringify(args)},
            url: "../../common/getData",
            dataType: dataType || "json",
            success: function(d){
				if(successfn){
					successfn.call(actionScope, d);
				}
            },
            error: function(e){
                if(errorfn) {
					errorfn.call(actionScope, e);
				}
            }
        });
    };*/
});