charge.directive('cgQueryComm', function() {
	return {
		restrict : 'AE',
		templateUrl : base
				+ '/app_angular_expand/charge/directives/queryComm.html',
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
			}
			scope.cancleQuery = function(obj) {
				scope.result.pop(obj);
			}

			// 查询条件组合
			scope.queryArray = angular.copy(scope.source);

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
				var array = [];
				for ( var i in scope.result) {
					var tt = scope.result[i];
					// 将新的替换旧的
					if (tt.type != it.type) {
						array.push(tt);
					} else {
						array.push(it);
						itHasPushed = true;
					}
				}
				if (!itHasPushed) {
					array.push(it);
				}
				scope.result = array;
			};
			
			scope.$watch('result',
				function() {
					// 是否页面显示当前查询条件
					scope.isQuery = scope.result.length > 0 ? true : false;
				}
			, true);

			scope.$watch('source',
					function() {
						scope.queryArray = angular.copy(scope.source);
						scope.setDefaultChecked();
					}
			, true);
			
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
			scope.setDefaultChecked();
		}
	}
});