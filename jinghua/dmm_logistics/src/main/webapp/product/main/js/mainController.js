app.controller("mainController", [ "$scope","dialog",'mask','http',function(scope,dialog,mask,http,timeAlert) {
	scope.resList=resList1;
	scope.curPath=window.document.location.pathname;
	scope.showdd={};
	for(var key in resList1){
		scope.showdd[resList1[key].id]=true;
		break;
	}
	scope.showddClick=function(key){
		var bool=!scope.showdd[key];
		for(var a in resList1){
			scope.showdd[resList1[a].id]=false;
		}
		scope.showdd[key]=bool;
	};
	//scope.curPath=scope.curPath.substring(scope.curPath.indexOf('/')+1, scope.curPath.length);
	//scope.curPath=scope.curPath.substring(scope.curPath.indexOf('/')+1, scope.curPath.length);
	scope.curPathClick=function(url){
		scope.curPath=url;
	}
	for(var key in resList1){
		var url=resList1[key].children[0].url_;
		scope.curPathClick(url);
		 $("iframe").attr("src",base+url);
		break;
	}
}]);