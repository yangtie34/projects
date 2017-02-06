
system.factory('advancedService',[function(){
	return {
		
		/**
		 * 获取高级查询组件参数 {Json类型}
		 */
		getParam : function(data){
			var paramList = [], obj;
			for(var i=0,len=data.length; i<len; i++){
				obj = data[i];
				paramList.push({
					code   : obj.queryCode,
					values : obj.id,
					group  : obj.group
				});
			}
			// 高级查询-参数
			return JSON.stringify(paramList);
		},
		/**
		 * 获取高级查询组件某一个条件的数据
		 */
		get : function(data, code){
			var obj;
			if(code != null){
				for(var i=0,len=data.length; i<len; i++){
					obj = data[i];
					if(code == obj.queryCode){
						return obj;
					};
				}
			}
			return obj;
		},
		
		/**
		 * 设置每一个查询条件的第一个值选中
		 */
		checkedFirst : function(data){
			if(data != null && data.length > 0){
				for(var i=0,len=data.length; i<len; i++){
					var obj = data[i];
					// 非树形组织机构
					if(obj.queryType != 'comboTree'){
						var items = obj.items;
						if(items.length > 0){
							items[0].checked = true;
						}
					}
				}
			}
		}
	
	}
	
}]);