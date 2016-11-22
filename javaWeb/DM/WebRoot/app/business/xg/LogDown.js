/**
 * 下载组件
 * @author xuebl
 * @Date 2014-06-26
 *  
 *  requires: ['Business.xg.LogDown']
 *  没有继承模板时，
 *  引入：cssRequires : ['app/business/xg/template/style/xg-common.css']
 *  混合：mixins : ['Pages.xg.Util']
 *  
 *  message:''     //'是否下载日志?'
 *  fileName:'' //temp.log
 *  menu:''     //'BXH' 编学号模块
 *  click:''    //true || fn  true:默认回调;fn:自定义回调
 *  scope:''    //this
 */
NS.define('Business.xg.LogDown', {
	
    extend : 'NS.Component',
    
    mixins : ['Pages.xg.Util'],
	
    initComponent : function(config){
    	var _message  = this['message'] || '下载',
    		_fileName = this['fileName'],
    		_menu  = this['menu'],
    		_click = this['click'],
    		me     = this,
    		_scope = this['scope'] || this;
    	
    	// 1 这种情况必须new Ext组件
//    	this.component = new Ext.Component({
//    		style : {
//    			margin : '0 10px 0'
//    		},
//    		html : '<a class="xhgl_download">'+_title+'</a>'
//    	});
    	
    	// 2 callParent 也是new Ext
    	NS.apply(config,{
    		style : {
				margin : '0 10px'
			},
			html : '<a class="xg-common-download">'+_message+'</a>'
    	});
    	this.callParent(arguments);
    	
        if(_click){
        	this.on('click', function(event, el){
        		if(typeof(_click) == 'function'){ //有方法执行
        			_click.call(_scope);
        		}else{ //没有执行默认方法
        			me.logDown(_fileName, _menu);
        		}
        	},_scope);
        }
    },
    
    
    
    
    
});