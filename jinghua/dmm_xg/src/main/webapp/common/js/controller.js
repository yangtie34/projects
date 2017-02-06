var app = angular.module('app', ['ngRoute','system']);
app.controller("controller",['$scope','homeService','dialog',function(scope,service,dialog){
//	dialog.showLoading();
	$("#_main_menu_container").hide();
	scope.count = 17;
	scope.callback = function(){
		scope.count--;
		if(scope.count == 0){
			$("#_main_menu_container").show();
			//绑定菜单事件
			var menus = $("#_main_menu_container").find("li>a");
			menus.click(function(e){
				e.preventDefault();
				var $tar = $(e.target);
				if($tar.parent().hasClass("active")) return;
				if($tar.attr("href")){
					$(".main-content").find("iframe").attr("src",$tar.attr("href"));
					$tar.parent().parent().show();
				}
				menus.parent().removeClass("active");
				$tar.parent().addClass("active");
			});
			menus[0].click();
//			dialog.hideLoading();
		}
	}
	//展开菜单
	$(".subNav").click(function(){
		if($(this).next().is(":hidden") == false)return;
		$(".subNav").siblings().slideUp("fast");
		$(this).next().slideToggle("fast")
	})
	
	
	$(".main-west").hover(function(){
		$(".xscz-swith-on").animate({
			width : "show"
		},200);
	},function(){
		$(".xscz-swith-on").animate({
			width : "hide"
		},200);
	});
	
	scope.hideWest = function(){
		$(".main-west").animate({  
            width : "hide"
        },function(){
        	$(".xscz-swith-off").show();
        });
		$(".main-content").animate({  
            "padding-left" : 0
        });
	};
	scope.showWest = function(){
		$(".xscz-swith-off").hide();
		$(".main-west").animate({  
            width : "show"
        });
		$(".main-content").animate({  
            "padding-left" : 280
        });
	}
	service.queryLoginInfo().then(function(data){
		scope.loginInfo = data.result;
	});
}]);
