/**
 * 下拉框组件
 * 
 * <div cg-combo-select source="treeData" result="treeResult" ></div>
 * 
//   $scope.treeData =  {
		  queryName : 'select',// 名称
	       queryCode : "comboSelect",
	       queryType : "comboSelect",
			items : [{
				mc:'xiala',
				children:[ 	{ id : '2015',mc : '2015年收费'}, 
				          {	id : '2014',mc : '2014年收费'},
				          {	id : '2013',mc : '2013年收费'},
				          {	id : '2012',mc : '2012年收费'},
				          {	id : '2011',mc : '2011年收费'},
				          {	id : '2010',mc : '2010年收费'},
				          {	id : '2009',mc : '2009年收费'}
				],
			},{
				mc:'xiala1',
				children:[ 	{ id : '2015',mc : '2015年收费'}, 
				          {	id : '2014',mc : '2014年收费'},
				          {	id : '2013',mc : '2013年收费'},
				          {	id : '2012',mc : '2012年收费'},
				          {	id : '2011',mc : '2011年收费'},
				          {	id : '2010',mc : '2010年收费'},
				          {	id : '2009',mc : '2009年收费'}
				],
			}]// 条件数据
   		}
//	  $scope.treeResult = {};
 * 
 */
jxpg.directive('cgComboSelect', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/comboSelect.html',
		scope : {
			source:"=",
			result:"=",
			code : "="
		},
		link : function(scope, element, attrs) {
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			scope.packageSourceData = function(dt){
				for ( var i = 0; i < dt.length; i++) {
					for ( var j = 0; j < dt.children.length; j++) {
						dt.children[i].nodeId = nodeId++;
					}
				}
			};
			scope.selectObj = [];
			
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				
				//选定的节点置空
				//scope.result = {};
				angular.copy({},scope.result);
				// 当前查询条件名称
				// scope.queryName = scope.source.queryName;
				
				//选定节点以及其所有的父节点
				//scope.packageSourceData(scope.treeData);
				
				scope.selectObj=scope.treeData;
			},true);
			//节点点击事件
			scope.cgComboTreeNodeClick = function(node){
				//设置输出结果为选定节点
				angular.copy(node,scope.result);
				delete scope.result.children;
				scope.result.queryCode = angular.copy(scope.code);
				scope.result.queryType = "comboSelect";
			};
		}
	};
}]);
