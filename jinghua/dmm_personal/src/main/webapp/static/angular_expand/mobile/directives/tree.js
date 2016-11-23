/**
 * 树指令
 * 
//*******************使用方法**********************  
//
//   <ul class="list1">
//	    <li cg-tree="node in items of treeData">
//	      <a ng-click="check(node)">{{node.name}}</a>
//	      <ul> <li cg-treecurse ></li> </ul>
//	    </li>
//	  </ul>
//
 *******************数据格式**********************
 * 
 * $scope.treeData = {
 *		  name: "Root",
 *		  items : [
 *		           {name:2,items:[{name:3},{name:4}]},
 *		           {name:5,items:[{name:6},{name:7}]}]
 * }
 ***********************************************
 */
system.directive('tree', ['$log', function($log) {
	return {
	   restrict: 'A',
	   scope: true,
	   controller: ['$scope', '$attrs',
	     function TreeController($scope, $attrs){
		   //***** 检测指令内的参数格式是否正确   ”node in items of treeData”
		   var expression = $attrs.cgTree;
		    var match = expression.match(/^\s*([\$\w]+)\s+in\s+([\S\s]*)\s+of\s+([\S\s]*)$/);
		    if (! match) {
		      throw new Error("Expected cgTree in form of"+ " '_item_ in _collection_ of _root_' but got '" + expression + "'.");
		    }
		    //******检测结束*******
		    
		    
	       var ident = this.ident = {value: match[1],collection: match[2],root: match[3]};
	       //$log.info("Parsed '%s' as %s", $attrs.cgTree, JSON.stringify(this.ident));
	       $scope.$watch(this.ident.root, function(v){
	         $scope[ident.value] = v;
	       });
	     }
	   ],
	   // Get the original element content HTML to use as the recursive template
	   compile: function cgTreecurseCompile(element){
	     var template = element.html();
	     return {
	       // set it in the pre-link so we can use it lower down
	       pre: function cgTreePreLink(scope, iterStartElement, attrs, controller){
	         controller.template = template;
	       }
	     };
	   }
	 };
	}]).directive('cgTreecurse', ['$compile', function($compile){
	return {
	   // 必须被cgTree指令包含
	   require: "^cgTree",
	   link: function cgTreecursePostLink(scope, iterStartElement, attrs, controller) {
	     // 使用父标签传入的参数进行解析，相当于使用递归
	     var build = [
	       '<', iterStartElement.context.tagName, ' ng-repeat="',
	       controller.ident.value, ' in ',
	       controller.ident.value, '.', controller.ident.collection,
	       '">',
	       controller.template,
	       '</', iterStartElement.context.tagName, '>'];
	     var el = angular.element(build.join(''));
	     // We swap out the element for our new one and tell angular to do its
	     // thing with that.
	     iterStartElement.replaceWith(el);
	     $compile(el)(scope);
	   }
	};
}]);