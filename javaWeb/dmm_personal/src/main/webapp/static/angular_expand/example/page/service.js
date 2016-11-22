app.service("pageService",[function(){
	return {
		getDataByPage:function(pg,callback){
			setTimeout(function(){
				var page = {};
				angular.copy(pg,page);
				page.total = 50;
				page.items = []
				for (var i = 1; i <= page.size; i++) {
					page.items.push({
						id : i + (page.index-1) * page.size,
						name : "第" + (i + (page.index-1) * page.size) + "条数据"
					});
				}
				callback(page);
			}, 500);
		}
	}
}]);
