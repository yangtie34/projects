/**
 * 教学评估系统，头部的封装
 * eg：
 * <any jxpg-title cg-title="我是标题"></any>
 */
jxpg.directive("jxpgTitle",["$window","dialog",function($window,dialog){
	return {
		restrict:'AE',
		replace:true,
		templateUrl:base + '/static/app_angular_expand/pc/directives/tpl/jxpgTitle.html',
		scope:{
			cgTitle:'@'
		},
		link:function(scope,ele,attrs){
			scope.quit=function(){
				dialog.confirm("确定退出吗？",function(){
					var logoutUrl=base+"/loginout";
					$window.location.href=logoutUrl;
				});
			};
			scope.index=base+"/app/jxpg/index.jsp";//首页
			scope.mainSrc=base+"/resource/jxpg/image/home.png";
			scope.quitSrc=base+"/resource/jxpg/image/exit.png";
		}
	}
}]);