/**
 * 拖动指令
 *  首先熟悉drag 事件 (源)dragstart - (目)enter  -  (目)dragover  -  (目)drop/dragleave - (源)dragend
 */
jxpg.directive('cgDrag', [ 'model', '$document', '$rootScope','$parse','$timeout',function(model, $document, $root,$parse,$timeout) {
	return {
		restrict : 'A',
		scope : {
			onDropSuccess : "&",
			drag : "="
		},
		link : function(scope, element, attrs) {

			element.attr("draggable", true);
			var dragElm = element[0];
			dragElm.ondragstart = function(ev) {
				try{
					ev.dataTransfer.setData("Text",ev.target.id);
				}catch(e){}
				ev.dataTransfer.effectAllowed = "move";
				eleDrag = ev.target;
				$root.dragdata = scope.drag;
				return true;
			};
			dragElm.ondragend = function(ev) {
				//ev.dataTransfer.clearData("text");
				eleDrag = null;
				
				if(attrs.onDropSuccess && $root.dropFlag){
					scope.$apply(function(){
						scope.onDropSuccess({
							$data : scope.drag
						});
					});
				}
				$root.dropFlag = false;
				$root.dragdata = null;
				return false;
			};
		}
	};
}]);
/**
 * 接收
 */
jxpg.directive('cgDrop', [ 'model','$rootScope','$timeout', function(model,$root,$timeout) {
	return {
		restrict : 'A',
		scope : {
			onDropSuccess : "&"
		},
		link : function(scope, element, attrs) {
			var dropElm = element[0];
			//进入
			dropElm.ondragenter = function(ev) {
				//element.addClass("");
				return true;
			};
			//出去
			dropElm.ondragover = function(ev) {
				ev.preventDefault();
				return true;
			};
			//放下
			dropElm.ondrop = function(ev) {
				if(attrs.onDropSuccess){
					scope.$apply(function(){
						scope.onDropSuccess({
							$data : angular.copy($root.dragdata)
						});
					});
					$root.dropFlag = true;
				}
				//element.removeClass("");
				return false;
			};
			dropElm.ondragleave = function(ev) {
				return false;
			};
		}
	};
} ]);