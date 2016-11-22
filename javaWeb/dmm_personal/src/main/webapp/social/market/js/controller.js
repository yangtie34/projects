var app = angular.module('app', ['ngRoute','system'])
.controller("controller",['$scope','marketService','dialog','toastrService',
    function(scope,service,dialog,toastr){
    	dialog.showLoading();
    	service.queryCommodityNums().then(function(data){
    		scope.cm = data.result;
    	});
    	service.queryCommodityType().then(function(data){
    		scope.types = data.result;
    	});
    	var type = "";
    	var issold = "";
    	var keyword = scope.queryString;
    	scope.page = {
			pagesize: 5,
			curpage: 1,
			sumcount : 0,
			loading : false
		};
    	scope.commoditys = [];
    	scope.$watch("page.curpage",function(newval,oldval){
			scope.page.loading = true;
			scope.search();
		},true);
		scope.search = function(){
			service.queryAllCommodity(scope.page,keyword,type,issold).then(function(data){
				scope.commoditys = $.merge(scope.commoditys, data.result.result);   
				scope.page.sumcount = data.result.sumcount;
				scope.page.loading = false;
				if (scope.page.curpage == 1) {
					dialog.hideLoading();
				}
			});
		}
		$(window).scroll(function(e) {
			var toBottom = ($(window).scrollTop() + $(window).outerHeight() + 200 > $("body").height());
			if(scope.page.curpage * scope.page.pagesize < scope.page.sumcount && toBottom && !scope.onloading){
				scope.page.curpage =  scope.page.curpage + 1;
				scope.$apply();
			}
		});
		scope.queryAll = function(){
			scope.commoditys = [];
			$("#ctab-2").removeClass("rxs-ctab-check");
			$("#ctab-1").addClass("rxs-ctab-check");
			scope.commoditys = [];
			issold = "";
			scope.page.curpage = 1;
			scope.search();
		}
		scope.queryNotSold = function(){
			$("#ctab-1").removeClass("rxs-ctab-check");
			$("#ctab-2").addClass("rxs-ctab-check");
			scope.commoditys = [];
			issold = "notSold";
			scope.page.curpage = 1;
			scope.search();
		}
		scope.searchCommodity = function(){
			scope.commoditys = [];
			keyword = scope.queryString;
			scope.page.curpage = 1;
			scope.search();
		}
		scope.selectType = function(typeCode){
			scope.commoditys = [];
			type= typeCode;
			scope.page.curpage = 1;
			scope.search();
		}
    	dialog.hideLoading();
}]).controller("detailscontroller",['$scope','$routeParams','marketService','dialog',
    function(scope,params,service,dialog){
    	dialog.showLoading();
    	service.queryCommodityDetails(params.commodityId).then(function(data){
    		scope.cd = data.result;
    	});
    	dialog.hideLoading();
    	
}]).controller("mycommodityscontroller",['$scope','marketService','dialog',
    function(scope,service,dialog){
    	dialog.showLoading();
    	service.queryMyCommoditys().then(function(data){
    		scope.commoditys = data.result;
    	});
    	var is_sold = 0;
    	scope.modify = function(id){
    		is_sold = 1;
    		service.modifyCommodityState(id,is_sold).then(function(data){
    			if(data.success){
    				toastr.info("完成交易成功！");
    				service.queryMyCommoditys().then(function(data){
			    		scope.commoditys = data.result;
			    	});
    			}else{
    				toastr.error("完成交易失败！");
    			}
    		})
    	}
    	dialog.hideLoading();
    	
}]).controller("putcommoditycontroller",['$scope','$routeParams','marketService','dialog','locationService','toastrService',
    function(scope,params,service,dialog,location,toastr){
    	dialog.showLoading();
    	service.queryCommodityType().then(function(data){
    		scope.types = data.result;
    	});
    	//选择图片上传
		scope.selectImage = function(){
			$("#hideImageSelectButton").click();
		}
		$('#uploadImageForm').ajaxForm({
			dataType : 'json',
			beforeSend : function(){
				dialog.showLoading()
			},
	        success:function(data){
	        	dialog.hideLoading();
	     		var err_code = data.error;
	     		if(err_code == '1'){
	     			toastr.error("图片上传失败，"+data.message)
	     		}else{
		        	scope.uploadImageList.push(base + data.url);
		        	scope.$apply();
	     		}
	     		$('#uploadImageForm').clearForm();
	        }
	    });
		//上传图片集合
		scope.uploadImageList = [];
		scope.submit = function(){
			var desc_ = $("#desc_").val();
			var name_ = $("#name_").val();
			if(name_ == ""){
				toastr.clear();
				toastr.warning("商品名称不能为空！");
				return;
			}
			var keyword = $("#keyword").val();
			if(keyword == ""){
				toastr.clear();
				toastr.warning("关键字不能为空！");
				return;
			}
			var commodity_type_code = $("#commodity_type_code").val();
			if(commodity_type_code == ""){
				toastr.clear();
				toastr.warning("商品类型不能为空！");
				return;
			}
			var use_time = $("#use_time").val();
			if(use_time == ""){
				toastr.clear();
				toastr.warning("使用期限不能为空！");
				return;
			}
			var price = $("#price").val();
			if(price == ""){
				toastr.clear();
				toastr.warning("商品价格不能为空！");
				return;
			}
			var stu_name = $("#stu_name").val();
			if(stu_name == ""){
				toastr.clear();
				toastr.warning("联系人不能为空！");
				return;
			}
			var tel = $("#tel").val();
			if(tel == ""){
				toastr.clear();
				toastr.warning("联系电话不能为空！");
				return;
			}
			dialog.showLoading();
			service.saveCommodity({desc:desc_,name:name_,keyword:keyword,
			commodityTypeCode:commodity_type_code,useTime:use_time,
			price:price,stuName:stu_name,tel:tel,images : angular.toJson(scope.uploadImageList)}).then(function(data){
				dialog.hideLoading();
				if(data.success){
					location.redirect(base + "social/market/market.jsp#/"+params.backpage);
				}else{
					toastr.error(data.message);
				}
			});
		}
    	dialog.hideLoading();
}]);

app.config(['$routeProvider',function($routeProvider) {
	$routeProvider
	.when("/home", {
		templateUrl: "tpl/home.html",
		controller : "controller"
	})
	.when("/details/:commodityId", {
		templateUrl: "tpl/details.html",
		controller : "detailscontroller"
	})
	.when("/mycommoditys", {
		templateUrl: "tpl/mycommoditys.html",
		controller : "mycommodityscontroller"
	})
	.when("/putcommodity/:backpage", {
		templateUrl: "tpl/putcommodity.html",
		controller : "putcommoditycontroller"
	})
	.otherwise({
		redirectTo : "/home"
	});
}]);

