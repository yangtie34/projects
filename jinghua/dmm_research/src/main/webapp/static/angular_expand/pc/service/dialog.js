/**
 * dialog 
 * 
 alert : dialog.alert("哈哈？");
		
		
 confirm:dialog.confirm("哈哈？",function(){
			alert("yes");
		},function(){
			alert("no")
		});
 */
system.factory('dialog',['$compile','$rootScope',function($compile,$rootScope){
	    //创建遮罩
		var body = $("body");//获取body元素
	    var mask = $('<div class="modal-backdrop fade in"/>');
	    mask.hide();
	    body.append(mask);
	    var confirmHtml = 
	    	'<div class="modal" style="display:none;">'+
	        	'<div class="modal-dialog" style="background-color: #FFF;">'+
	                '<div class="modal-header">'+
	                    '<button type="button" class="close" ng-click="$dialog_confirm_close()" style="opacity: 1;"><i class="fa fa-times text-danger"></i></button>'+
	                    '<h4 class="modal-title text-primary"><i class="fa fa-question"></i>&nbsp;提示</h4>'+
	                 '</div>'+
	                 '<div class="modal-body">'+
	                 	'{replacement}'+
	                 '</div>'+
	                 '<div class="modal-footer" style="text-align: center;">'+
	                 	'<button type="button" class="btn btn-primary" ng-click="$dialog_confirm_sure()">确定</button>'+
	                 	'<button type="button" class="btn btn-default" ng-click="$dialog_confirm_cancel()">关闭</button>'+
	                 '</div>'+
	             '</div>'+
	         '</div>';
	    var successHtml = '<div class="modal" style="display:none;">'+
		    '<div class="modal-dialog" style="background-color: #FFF;">'+
		         '<div class="modal-body" style="padding:30px 10px;">'+
		         	'{replacement}'+
		         '</div>'+
		         '<div class="modal-footer" style="text-align: center;">'+
		         	'<button type="button" class="btn btn-danger btn-sm" ng-click="$dialog_success_close()">关闭</button>'+
		         '</div>'+
		  '</div>';
	    var loadingHtml = '<div class="modal" style="display:none;">'+
	    	'<div style="position: absolute;left: 50%;top: 35%;margin-left: -30px;width: 60px;text-align: center;color: #FFF;"> <i class="fa fa-spinner fa-spin fa-4x" style=""></i></div>'
	    '</div>';
	    /**
	     * 把元素添加到body上，显示遮罩，并把dialog居中显示
	     * replace message,show mask,show dialog
	     * @param html
	     * @param message
	     * @returns {*|jQuery|HTMLElement}
	     */
	    var rsd = function(html,message){
	        var el = $($compile(html.replace("{replacement}",message))($rootScope));
	        mask.show();//显示遮罩
	        body.append(el);
	        el.fadeIn("fast");
	        return el;
	    }
	    var dialog = {
	    		
	    };
	    return {
	        alert : function(message,callback){
	            $rootScope.$dialog_success_close = function(){
	            	dialog.success.remove();
	                mask.hide();
	                delete dialog.success;
	                if(callback)callback()
	            };
	            dialog.success = dialog.success||rsd(successHtml,message);
	        },
	        
	        /**
	         * 显示一个确认窗口
	         * @param message
	         * @param doSure
	         * @param doFailure
	         */
	        confirm : function(message,doSure,doCancel){
	            $rootScope.$dialog_confirm_close = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	            };
	            $rootScope.$dialog_confirm_sure = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	                if(doSure)doSure();
	            };
	            $rootScope.$dialog_confirm_cancel = function(){
	            	dialog.confirm.remove();
	                mask.hide();
	                delete dialog.confirm;
	                if(doCancel)doCancel();
	            };
	            dialog.confirm = dialog.confirm||rsd(confirmHtml,message);
	       },
	       showLoading : function(){
	    	   if(dialog.loading){
	    		   mask.show();
	    		   dialog.loading.fadeIn('fast');
	    	   }else{
	    		   dialog.loading = rsd(loadingHtml,"");
	    	   }
	    	   
	       },
	       hideLoading : function(){
	    	   dialog.loading.fadeOut('fast');
               mask.fadeOut('fast');
	       }
	    }
	}]);