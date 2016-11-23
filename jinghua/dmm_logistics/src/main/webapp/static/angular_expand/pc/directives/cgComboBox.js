jxpg.directive('cgComboBox', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/comboBox.html',
		scope : {
			source:"=",
			result:"=",
			inputClass :"@",
			height: "@",
			onSelect : "&"
		},
		link : function(scope, element, attrs) {
			scope.result = {};
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			/*scope.packageSourceData = function(dt){
				dt.nodeId = nodeId++;
				if (dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						dt.children[i].parentNodeId = dt.nodeId;
						scope.packageSourceData(dt.children[i]);
					}
				}
			};*/
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				//选定的节点置空
				angular.copy({},scope.result);
				//选定节点以及其所有的父节点
				//scope.packageSourceData(scope.treeData);
				scope.findeDefaultChecked(scope.treeData);
			},true);
			
			//节点点击事件
			scope.cgComboTreeNodeClick = function(node){
				//设置输出结果为选定节点
				angular.copy(node,scope.result);
				delete scope.result.children;
				scope.onSelect({
					$data : scope.result
				});
			};
			
			var defaultCheckedHasFind = false;
			//寻找默认选中节点
			scope.findeDefaultChecked = function(dt){
				if(!dt) return;
				if (dt.checked) { 
					defaultCheckedHasFind = true;
					scope.cgComboTreeNodeClick(dt);
				}else if(dt.children) {
					for ( var i = 0; i < dt.children.length; i++) {
						if (defaultCheckedHasFind) {
							defaultCheckedHasFind = false;
							break;
						}else{
							scope.findeDefaultChecked(dt.children[i]);
						}
					}
				}
			};
			scope.showBoxDetail  = false;
			scope.showDetail = function(e){
				e.stopPropagation();
				var tar = e.target;
				if(scope.showBoxDetail) return;
				scope.showBoxDetail = true;
				var hideDetail = null;
				hideDetail = function(e){
					scope.showBoxDetail = false;
					scope.$digest();
					$("html").not(tar).unbind("click",hideDetail);
				};
				$("html").not(tar).bind('click',hideDetail);
			};
		}
	};
}]);
