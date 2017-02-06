/**
 * Bootstrap提示工具 : toolTip
 */
module.exports = function(system){
	require("../css/tooltip.css");
	var tpl = require("./tpl/tooltip.html")
	system.directive('toolTip', [function() {
		return {
			restrict : 'AE',
			transclude : true,
			template :tpl,
			scope : {
				placement : "@",
				hideIcon : "@"
			},
			link : function(scope, element, attrs) {
				element.addClass("tooltip-container");
				element.on("mouseenter",function(){
					var eleHeight = element.height();
					var eleWidth = element.width();
					var elePosition = element.position()
					var eleLeft = elePosition.left;
					var eleTop  = elePosition.top;

					var content = element.find(".popover");
					var contWidth = content.width();
					var contHeight = content.height();

					var contLeft = -200,contTop = -200;
					switch (scope.placement) {
						case 'top':
							contLeft = eleLeft +  (eleWidth - contWidth)/2 - 2;
							contTop = eleTop - contHeight - 4;
							break;
						case 'right':
							contLeft = eleLeft + eleWidth;
							contTop = eleTop + (eleHeight-contHeight)/2 - 4;
							break;
						case 'bottom':
							contLeft = eleLeft + (eleWidth - contWidth)/2 - 2;
							contTop = eleTop + eleHeight - 2;
							break;
						case 'left':
							contLeft = eleLeft - contWidth - 4;
							contTop =  eleTop + (eleHeight-contHeight)/2 - 4;
							break;
						default:
							contLeft = eleLeft + eleWidth - 2;
							contTop = eleTop + (eleHeight-contHeight)/2 - 2;
							break;
					}
					content.css({
						left : contLeft,
						top : contTop
					})
				});
			}
		};
	}]);
}