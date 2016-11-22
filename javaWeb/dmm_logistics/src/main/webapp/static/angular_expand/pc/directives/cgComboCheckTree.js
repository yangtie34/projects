/**
 * 查询树组件
 * 
 * <div cg-combo-tree source="treeData" result="treeResult" ></div>
 * 
//   $scope.treeData = {
//		  id :1,
//		  name: "全校",
//		  children : [
//		           {id:2,name:"计算机与信息工程学院",children:[{id:3,name:"计算机"},{id:4,name:"软件工程"}]},
//		           {id:3,name:"体育学院",children:[{id:3,name:'健美'},{id:4,name:'体操'}]},
//		           {id:4,name:"建筑工程工程学院",children:[{id:3,name:'造价'},{id:4,name:'城市工程'}]},
//		           {id:5,name:"会计学院",children:[{id:6,name:'国际贸易'},{id:7,name:'银行电算'}]}]
//	  }
//	  $scope.treeResult = {};
 * 
 */
jxpg.directive('cgComboCheckTree', ['$interval',function($interval) {
	return {
		restrict : 'AE',
		templateUrl : base + '/static/app_angular_expand/pc/directives/tpl/comboCheckTree.html',
		scope : {
			source:"=",
			result:"=",
			code : "=",
			treeType:"&",
			checkType:"&"
		},
		link : function(scope, element, attrs) {
			//获取属性
			scope.treeType=attrs.treetype||'comboTree';
			scope.checkType=attrs.checktype||'checkbox';
			//生成节点的唯一ID和 父节点 ID
			var nodeId = 0;
			scope.packageSourceData = function(dt){
				dt.check=dt.check||false;
				dt.nodeId = nodeId++;
				if(dt.nodeId==0){if(scope.treeType=="listTree")dt.check=true;}
				
				if (dt.children&&dt.children.length>0) {
					for ( var i = 0; i < dt.children.length; i++) {
						dt.children[i].parentNodeId = dt.nodeId;
						scope.packageSourceData(dt.children[i]);
					}
					dt.children[dt.children.length-1].thelast=true;
				}
			};
			scope.selectTree = [];
			
			//监听元数据的变化，意在当元数据是通过异步请求得到的时候刷新数据
			scope.$watch("source",function(){
				scope.treeData = {};
				//元数据备份
				scope.treeData = angular.copy(scope.source);
				
				//选定的节点置空
				scope.result = [];
				//angular.copy([],scope.result);
				// 当前查询条件名称
				// scope.queryName = scope.source.queryName;
				
				//选定节点以及其所有的父节点
				scope.packageSourceData(scope.treeData);
				scope.selectTree.splice(0,scope.selectTree.length);
				scope.selectTree.push(scope.treeData);
				scope.findeDefaultChecked(scope.treeData);
				scope.cgComboCheckTreeClick(scope.treeData);
			},true);
			
			//寻找父节点,找到后将父节点塞进选定节点容器
			scope.findPanrentOfNode = function(node,tree){
				if(node.nodeId != tree.nodeId){
					if (node.parentNodeId == tree.nodeId) {
						scope.selectTree.push(tree);
						scope.findPanrentOfNode(tree,scope.treeData);
					}else{
						if (tree.children) {
							for ( var i = 0; i < tree.children.length; i++) {
								scope.findPanrentOfNode(node,tree.children[i]);
							}
						}
					}
				}
			};
			scope.switchclick=function($event){
				var a=$event.target;
				$(a).parent().find(">ul").toggle();
				if($(a).hasClass("root_open")){
					$(a).removeClass("root_open");
					$(a).addClass("root_close");
				}else{
					$(a).removeClass("root_close");
					$(a).addClass("root_open");
				};
			};
			//节点点击事件
			scope.cgComboCheckTreeClick = function(data){
				if(data.check==true){
					var data_=angular.copy(data);
					data_.children=true;
					scope.result.push(data_);}
				if (data.children) { 
				for(var i=0;i<data.children.length;i++){
					scope.cgComboCheckTreeClick (data.children[i]);
				}
				 }
			};
			
			scope.selectRadio=function(node){
				_checkNodeAllC(scope.treeData,false);
				node.check=true;
				scope.result=[];
				scope.cgComboCheckTreeClick(scope.treeData);
			}
			scope.selectBox=function(node){
				var ischeck=true;
				if(node.check){
					ischeck=false;
				}
				_checkNodeAllC(node,ischeck);
				parentChecked(scope.treeData);
				scope.result=[];
				scope.cgComboCheckTreeClick(scope.treeData);
			};
			scope.getParentCheck=function(node){
				return scope.nodeHasCheck(node);
			};
			//子节点是否有选中
			scope.nodeHasCheck=function(dt){
				var check=dt.check;
				if (dt.children) { 
					for(var i=0;i<dt.children.length;i++){
						check=scope.nodeHasCheck(dt.children[i]);
						if(dt.children[i].check)
							check=true;
						if(check)break;
					}
				}
				return check;
			}
			_checkNodeAllC=function(node,ischeck){
				node.check=ischeck;
				if (node.children) {
					for ( var i = 0; i < node.children.length; i++) {
						_checkNodeAllC(node.children[i],ischeck);
					}
				}
			};
			
			var defaultCheckedHasFind = false;
			//节点状态
			parentChecked = function(dt){
				var check=null;
				if (dt.children&&dt.children.length>0) { 
					for(var i=0;i<dt.children.length;i++){
						parentChecked(dt.children[i]);
						dt.children[i].check=dt.children[i].check||false;
						if(!dt.children[i].check)
							check=false;
					}
					if(check==false){dt.check=false}else{dt.check=true};
				}
				
			};
			//寻找默认选中节点
			scope.findeDefaultChecked = function(dt){
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
		}
	};
}]);
