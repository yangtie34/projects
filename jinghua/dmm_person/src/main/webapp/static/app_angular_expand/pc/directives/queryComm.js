/**
 * 查询组件
 */
jxpg.directive('cgQueryComm', function() {
	return {
		restrict : 'AE',
		templateUrl : base
				+ '/app_angular_expand/dorm/directives/tpl/queryComm.html',
		scope : {
			source : "=",
			result : "="
		},
		link : function(scope, element, attrs) {
			// 当前查询条件
			scope.result = [];
			// 清空查询条件
			scope.cancleQueryAll = function() {
				scope.result = [];
				for ( var i in scope.queryArray) {
					var item = scope.queryArray[i];
					for ( var j = 0; j < item.items.length; j++) {
						item.items[j].checked = false;
					}
				}
			}
			scope.cancleQuery = function(obj) {
				for ( var i = 0; i < scope.result.length; i++) {
					if(scope.result[i] == obj){
						scope.result.splice(i,1);
					}
				}
				for ( var i in scope.queryArray) {
					var item = scope.queryArray[i];
					if(obj.type == item.queryName){
						for ( var j = 0; j < item.items.length; j++) {
							item.items[j].checked = false;
						}
					}
				}
			}

			// 点击更多后，显示某组条件的每一个条件项
			scope.showAll = function(obj) {
				obj.isAll = true;
				for ( var i in obj.items) {
					obj.items[i].show = true;
				}
			};
			// 高级搜索
			scope.isExpandALL = false;
			scope.expandALL = function() {
				scope.isExpandALL = !scope.isExpandALL;
				for ( var i = 2; i < scope.queryArray.length; i++) {
					scope.queryArray[i].isShow = !scope.queryArray[i].isShow;
				}
			};
			// change
			scope.change = function(item, condition) {
				// 改变选中
				for ( var j = 0; j < condition.items.length; j++) {
					condition.items[j].checked = false;
				}
				item.checked = true;
				// 判断item所属的条件组是否有已选条件在result数组中,如果有将新的替换旧的
				var it = {}, itHasPushed = false;
				angular.copy(item, it);
				it.type = condition.queryName;
				for ( var i in scope.result) {
					var tt = scope.result[i];
					// 将新的替换旧的
					if (tt.type == it.type) {
						scope.result.splice(i,1,it)
						itHasPushed = true;
					}
				}
				if (!itHasPushed) {
					scope.result.push(it);
				}
			};
			
			//监控元数据的变化
			scope.$watch('source',function() {
				scope.queryArray = [];
				scope.queryArray = angular.copy(scope.source);
				scope.setDefaultChecked();
			}, true);
			
			scope.setDefaultChecked = function(){
				// 遍历条件组合数组，设置默认选中项和默认显示项
				for ( var i in scope.queryArray) {
					var item = scope.queryArray[i];
					// 当条件的组数大于2的时候，默认显示两组，其余的组合点击高级查询显示
					if (i < 2) {
						item.isShow = true;
					} else {
						item.isShow = false; 
					}
					// 当某组条件的数量大于5的时候，默认显示5个，其余的点击更多后展示
					item.isAll = item.items.length <= 5 ? true : false;
					for ( var j in item.items) {
						var inner = item.items[j];
						inner.show = true;
						if (i < 2 && j == 0) {
							inner.checked = true;
							scope.change(inner, item);
						}
						if (j >= 5) {
							inner.show = false;
						}
					}
				}
			};
		}
	}
});