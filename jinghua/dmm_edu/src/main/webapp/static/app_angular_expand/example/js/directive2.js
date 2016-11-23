/**
 * 查询树组件
 * 
 * <div cg-combo-tree source="treeData" result="treeResult" ></div>
 * 
//   $scope.treeData = {
//		  id :1,
//		  name: "全校",
//		  conditionName : "组织机构",
//		  items : [
//		           {id:2,name:"计算机与信息工程学院",items:[{id:3,name:"计算机"},{id:4,name:"软件工程"}]},
//		           {id:3,name:"体育学院",items:[{id:3,name:'健美'},{id:4,name:'体操'}]},
//		           {id:4,name:"建筑工程工程学院",items:[{id:3,name:'造价'},{id:4,name:'城市工程'}]},
//		           {id:5,name:"会计学院",items:[{id:6,name:'国际贸易'},{id:7,name:'银行电算'}]}]
//	  }
//	  $scope.treeResult = {};
 * 
 */
charge.directive('cgComboTree', function() {
	return {
		restrict : 'AE',
		templateUrl : base + '/app_angular_expand/charge/directives/tpl/comboTree.html',
		scope : {
			source:"=",
			result:"="
		},
		link : function(scope, element, attrs) {
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			scope.packageSourceData = function(dt){
				dt.nodeId = nodeId;
				nodeId ++;
				if (dt.items) {
					for ( var i = 0; i < dt.items.length; i++) {
						dt.items[i].parentNodeId = dt.nodeId;
						scope.packageSourceData(dt.items[i])
					}
				}
			}
			
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				
				//选定的节点
				scope.result = {};
				
				// 当前查询条件名称
				scope.conditionName = scope.source.conditionName;
				
				//选定节点以及其所有的父节点
				scope.selectTree = [];
				scope.packageSourceData(scope.treeData);
				scope.selectTree.push(scope.treeData);
			});
			
			//寻找父节点,找到后将父节点塞进选定节点容器
			scope.findPanrentOfNode = function(node,tree){
				if (node.parentNodeId) {
					if (tree.nodeId == node.parentNodeId) {
						scope.selectTree.push(tree);
						scope.findPanrentOfNode(tree,scope.treeData)
					}else{
						if (tree.items) {
							for ( var i = 0; i < tree.items.length; i++) {
								scope.findPanrentOfNode(node,tree.items[i]);
							}
						}
					}
				}
			}
			
			//节点点击事件
			scope.cgComboTreeNodeClick = function(node){
				//置空容器
				scope.selectTree = [];
				if(node != scope.treeData){
					scope.selectTree.push(node);
					scope.findPanrentOfNode(node,scope.treeData);
				}
				scope.selectTree.push(scope.treeData);
				//由于生成的节点容器将树倒置，所以翻转容器
				scope.selectTree.reverse();
				angular.copy(node,scope.result);
				delete scope.result.items;
			}
		}
	}
});
