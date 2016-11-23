/**
 * http service
 * 简单封装jQuery的ajax
 */
system.factory('toastrService',[function(){
	toastr.options = {
	  "closeButton": false,
	  "debug": false,
	  "positionClass": "toast-bottom-full-width",
	  "onclick": null,
	  "showDuration": "100",
	  "hideDuration": "300",
	  "timeOut": "3000",
	  "extendedTimeOut": "500",
	  "showEasing": "linear",
	  "hideEasing": "linear",
	  "showMethod": "fadeIn",
	  "hideMethod": "fadeOut"
	}

    return {
        info : function(str){
        	toastr.info(str)
        },
        success : function(str){
        	toastr.success(str)
        },
        warning : function(str){
        	toastr.warning(str)
        },
        error : function(str){
        	toastr.error(str)
        },
        clear : function(){
        	toastr.clear()
        }
    }
}]);