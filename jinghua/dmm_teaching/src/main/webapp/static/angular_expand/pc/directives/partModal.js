/**
 * 局部遮罩指令
 */
system.directive('partModal',[function() {
	return {
		restrict : 'AE',
		scope: {
			showModal : "="
		},
		link : function(scope, element, attrs) {
			//生成遮罩层
			element.css({
				"position" : "relative"
			});
			var modal = $("<div>"+
					    	"<div style='position: absolute;left: 50%;top: 35%;margin-left: -30px;width: 60px;text-align: center;color: #FFF;'>" +
					    		" <i class='fa fa-spinner fa-pulse fa-2x'></i>" +
					    	"</div>"+
					    "</div>").css({
					    	"background-color" : "rgb(180, 180, 180)",
					    	opacity : "0.4"
					    });
			var html = $("<div />").css({
				position : "absolute",
				left : 0,
				top : 0,
			    "z-index" : 999
			});
			modal.appendTo(html);
			element.append(html);
			
			//监听遮罩层的显示控制
			scope.$watch('showModal',function(val){
				if(val){
					modal.css({
				    	height : "inherit",
				    	width : "inherit",
				    });
					html.css({
						height :  "100%",
						width :  "100%",
					})
					html.fadeIn('fast');
				}else{
					html.hide();
				}
			});
		}
	}
}]);
