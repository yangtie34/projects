app.service("service_comm",[function(){
    return {
    	
    	mask : true,
    	
		showLoading : function(){
    		this.mask = true;
    	},
    	
    	hideLoading : function(){
    		this.mask = false;
    	}
    }
}]);