
jxpg.factory("timeAlert",function(){
		var defalutOption={
			  "closeButton": false,
			  "debug": false,
			  "positionClass": "toast-top-center",
			  "onclick": null,
			  "showDuration": "300",
			  "hideDuration": "1000",
			  "timeOut": "2000",
			  "extendedTimeOut": "1000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			}
	return {
		success:function(massage,option){
			toastr.options=defalutOption;
			toastr['success'](massage, "");
		},
		error:function(massage,option){
			toastr.options=defalutOption;
			toastr['error'](massage, "");
		},
		info:function(massage,option){
			toastr.options=defalutOption;
			toastr['info'](massage, "");
		},
		warning:function(massage,option){
			toastr.options=defalutOption;
			toastr['warning'](massage, "");
		}
	}
});