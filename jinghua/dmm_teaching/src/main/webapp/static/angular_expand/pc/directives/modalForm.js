/*************************************************
 弹出层form 
 功能介绍 ： 
	  弹出form，分页显示数据
	  
	  config : {
	  	title : "",
		show : false,
		url : "",
		exportUrl : "",// 为空则不显示导出按钮
		heads : [],
		fields : [],
		params : {
			
		}
	  }
 例子见 ：static/angular_expand/example/modalForm.jsp
 ************************************************/
system.directive('modalForm',["httpService",function (http) {
    return {
        restrict: 'AE',
        templateUrl : base + '/static/angular_expand/pc/directives/tpl/modalForm.html',
        scope: {
        	config: "="
        },
        link : function(scope, element, attrs) {
        	//隐藏form弹出
        	scope.hideModalForm = function(){
        		scope.config.show = false;
        	}
        	//空白地方点击隐藏form
        	element.click(function(e){
        		scope.hideModalForm();
        		scope.$apply();
        	}).find(".modal-dialog").click(function(e){
        		e.stopPropagation();
        	});
        	
        	//监听显示控制，重置分页参数
        	scope.$watch("config.show",function(newval){
        		if(newval == true){
        			element.find(".fade").show().scrollTop(0).addClass("in");
        			scope.vm.curpage = 1;
        			scope.vm.rows  = [];
        			scope.vm.total = 0;
        			scope.sortColumn = null; // 每次打开重置排序字段
        		}else{
        			element.find(".fade").removeClass("in").fadeOut('fast');
        			scope.vm.curpage = 0;
        		}
        	});
        	//分页参数
        	scope.vm = {
    			showModal : false,
    			pagesize: 10,
    			curpage : 1,
    			rows  : [],
    			total : 0,
    			pageTotal : 0
        	};
        	//监听分页参数，请求后台数据
        	scope.$watch("vm.curpage",function(newval,oldval){
        		if(newval != 0){
        			scope.vm.showModal = true;
        			scope.queryPageItems(scope.config.params).then(function(data){
        				scope.vm.showModal = false;
        				scope.vm.rows  = data.rows;
        				scope.vm.total = data.total;
        				scope.vm.pageTotal = parseInt(scope.vm.total%scope.vm.pagesize == 0 ? scope.vm.total/scope.vm.pagesize : scope.vm.total/scope.vm.pagesize+1);
        			})
        		}
        	});
        	//请求数据方法
        	scope.queryPageItems = function(params){
        		var page = {
					curpage  : scope.vm.curpage,
					pagesize : scope.vm.pagesize,
					sumcount : scope.vm.total,
					sortColumn : scope.sortColumn,
    				order : scope.sortOrder
        		}
        		page = JSON.stringify(page);
        		return http.post({
        			url  : scope.config.url,
        			data : angular.extend({
        				page : page
    				},params)
    				/*data : angular.extend({
    					curpage : scope.vm.curpage,
    					pagesize : scope.vm.pagesize,
    					sortColumn : scope.sortColumn,
    					sortOrder : scope.sortOrder
    				},params)*/
        		});
        	}
        	//下载方法
        	scope.download = function(){
        		 /*$.fileDownload(base + scope.config.exportUrl,{
         			data : angular.extend({
     					curpage : 1,
         				pagesize : scope.vm.total,
         				heads : angular.toJson(scope.config.heads),
         				fields : angular.toJson(scope.config.fields),
         				filename : scope.config.title
     				},scope.config.params)
         		});*/
        		var page = {
					curpage  : 1,
					pagesize : scope.vm.total,
					sortColumn : scope.sortColumn,
    				order : scope.sortOrder
        		}
        		page = JSON.stringify(page);
         		var params = scope.config.params;
         		http.fileDownload({
              		url  : scope.config.exportUrl,
              		data : angular.extend({
              				fileName : scope.config.title,
              				page : page
 	    				},params),
             	})
        	}
        	//列排序方法
        	scope.orderByColumn = function(field){
        		if(scope.sortColumn == field ){
        			if(scope.sortOrder == 'asc')
        				scope.sortOrder = 'desc';
        			else if(scope.sortOrder == 'desc'){
        				scope.sortColumn = '';
        			}
        		}else{
        			scope.sortOrder = 'asc';
        			scope.sortColumn = field;
        		}
        		//请求数据
        		scope.vm.showModal = true;
    			scope.queryPageItems(scope.config.params).then(function(data){
    				scope.vm.showModal = false;
    				scope.vm.rows = data.rows;
    			});
        	}
        }
    };
}]);