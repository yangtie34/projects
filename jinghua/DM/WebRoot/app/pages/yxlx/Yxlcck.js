/**
 * 迎新流程查看
 */
NS.define('Pages.yxlx.Yxlcck', {
	 extend : 'Template.Page',
	
/*********************请求后台服务配置*********************/
	modelConfig : {
		serviceConfig : {}
	},

/*******************入口方法 ******************************/
	init: function () {
        this.initPage();
    },
	
	initPage : function(){
		
	this.page= new NS.Component({
		    
		    border : false,
		    
		    html: '<img style="margin-top:50px;" src="images/yxlxgl/yxlc.png"/>'
		
		});
	
		
		this.setPageComponent(this.page);
	}
	
	
	
	
	
})