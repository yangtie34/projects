app.service("wechatMenuService",['httpService',function(http){
	return {
		getMenus : function(){
			var result = http.post({
				url : "wechat/menu/getMenus",
				data : {}
			});
			return result;
		},
		saveMenus : function(menus){
			var result = http.post({
				url : "wechat/menu/saveMenus",
				data : {
					menus : angular.toJson(menus)
				}
			});
			return result;
		},
		initMenus : function(){
			var result = http.post({
				url : "wechat/createMenu",
				data : {}
			});
			return result;
		}
	};
}]);