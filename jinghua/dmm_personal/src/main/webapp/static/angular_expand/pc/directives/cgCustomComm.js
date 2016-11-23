system.directive('cgCustomComm', function() {
	return {
		restrict : 'AE', 
		templateUrl : base
				+ '/static/angular_expand/pc/directives/tpl/customComm.html',
		scope : {
			source : "=",
			expand : "@",
			noborder : "@",
			onChange : "&"
		},
		link : function(scope, element, attrs) {
			// 当前查询条件
			scope.result = [];
			
			//树的选择结果
			scope.innerTreeResult = {};
			
			//监听树的选择结果
			scope.$watch('innerTreeResult',function(val) {
				if (val.queryCode) {
					var innerTreeHasPushed = false;
					for ( var i = 0; i < scope.result.length; i++) {
						if (scope.result[i].queryCode == scope.innerTreeResult.queryCode) {
							scope.result.splice(i,1,angular.copy(scope.innerTreeResult));
							innerTreeHasPushed = true;
						}
					}
					if (!innerTreeHasPushed) {
						scope.result.push(angular.copy(scope.innerTreeResult));
					}
					scope.onChange({
						$data : scope.result
					});
				}
			}, true);
			
			// 清空查询条件
			scope.cancleQueryAll = function() {
				scope.result = [];
				for ( var i = 0; i < scope.queryArray.length; i++) {
					var item = scope.queryArray[i];
					if(scope.queryArray[i].queryType == "comboTree"){
						scope.queryArray[i].items.dataChangeDate = new Date();
					}else{
						for ( var j = 0; j < item.items.length; j++) {
							item.items[j].checked = false;
						}
					}
				}
				
			};
			scope.cancleQuery = function(obj) {
				for ( var i = 0; i < scope.result.length; i++) {
					if(scope.result[i] == obj){
						scope.result.splice(i,1);
					}
				}
				if(obj.queryType == "comboTree"){
					for ( var i = 0; i < scope.queryArray.length; i++) {
						if(scope.queryArray[i].queryCode == obj.queryCode){
							scope.queryArray[i].items.dataChangeDate = new Date();
						}
					}
				}else{
					for ( var i in scope.queryArray) {
						var item = scope.queryArray[i];
						if(obj.queryName == item.queryName){
							for ( var j = 0; j < item.items.length; j++) {
								item.items[j].checked = false;
							}
						}
					}
				}
			};

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
			scope.isExpandALL = scope.expand ? true : false;
			scope.expandALL = function() {
				scope.isExpandALL = !scope.isExpandALL;
				for ( var i = 1; i < scope.queryArray.length; i++) {
					scope.queryArray[i].isShow = !scope.queryArray[i].isShow;
				}
			};
			// change
			scope.change = function(item, condition) {
				scope.changeIt(item, condition);
				scope.onChange({
					$data : scope.result
				});
			};
			
			scope.$watch('source',function() {
				scope.queryArray = angular.copy(scope.source);
				scope.setDefaultChecked();
			}, true);
			
			// change
			scope.changeIt = function(item, condition) {
				// 改变选中
				for ( var j = 0; j < condition.items.length; j++) {
					condition.items[j].checked = false;
				}
				item.checked = true;
				// 判断item所属的条件组是否有已选条件在result数组中,如果有将新的替换旧的
				var it = {}, itHasPushed = false;
				angular.copy(item, it);
				it.queryName = condition.queryName;
				it.queryCode = condition.queryCode;
				var array = [];
				for ( var i in scope.result) {
					var tt = scope.result[i];
					// 将新的替换旧的
					if (tt.queryCode != it.queryCode) {
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
			
			scope.setDefaultChecked = function(){
				// 遍历条件组合数组，设置默认选中项和默认显示项
				for ( var i in scope.queryArray) {
					var item = scope.queryArray[i];
					// 当条件的组数大于1的时候，默认显示两组，其余的组合点击高级查询显示
					if (i < 1) {
						item.isShow = true;
					} else {
						item.isShow = scope.isExpandALL; 
					}
					// 当某组条件的数量大于5的时候，默认显示5个，其余的点击更多后展示
					item.isAll = item.items.length <= 5 ? true : false;
					if (item.queryType != "comboTree") {
						for ( var j in item.items) {
							var inner = item.items[j];
							inner.show = true;
							if (inner.checked) {
								scope.changeIt(inner, item);
							}
							if (j >= 5) {
								inner.show = false;
							}
						}
					}
				}
			};
			if(!scope.noborder){
				scope.noborder = true;
			}
		}
	};
});