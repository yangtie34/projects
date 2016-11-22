/**
 * 基础模板页面[xuebl]
 * 	用于比较简单的 新增form、修改form、删除数据、单字段查询...
 * 	新版学工模式
 *  参考 Pages.xg.wjcf.cssz.Cflxsz 
 *  	Business.xg.jczd.JzSzLxszModel 
 *  	Business.xg.jczd.JzSzPcszModel
 *  
 *  ** 基本配置
 *  menuName   : ''
 *  bottomName : ''
 *  entityName : ''
 *  addServiceConfig : {queryTableData  : ''} //配置服务方法
 *  addServiceParams : function(){ //更新服务参数
 *  				   	'queryTableData' : {lx:''}
 *  				   }
 *  beforeInit : fn  //initData前
 *  afterInit  : fn  //initData后
 *  
 *  ** grid配置
 *  initGrid        : fn  //return grid {一般不重写}
 *  getGridConfig   : function(){  //grid 列配置
 *  				 	border : '',
 *  				 	colunms: [],
 *  				 	events : {'baseAdd':{} }
 *  				  }
 *  addGridEvents	: {this.grid.bindItemsEvent()} //非常不建议使用
 *  
 *  ** tbar配置
 *  initTbar        : fn  //return tbar {一般不重写}
 *  getTbarConfig   : function(){  //tbar 配置
 *  				  	items : [],
 *  					events: {'baseAdd':{} }
 *  				  }
 *  bindTbarEvents  : {this.tbar.bindItemsEvent()} //非常不建议使用
 *  
 *  ** 多行tbar
 *  addTbarRow		: fn  //return [] tbar 第二、三行... 
 *  
 *  ** 工具栏 {学号、姓名、身份证号查询框，小统计}
 *  initTools		: fn  //return cmp
 *  
 */
NS.define('Business.xg.model.EntityModelNew', {
	
	extend : 'Business.xg.model.EntityModel',
	
    /**
     * 初始化页面
     * @Override
     */
    initPage : function(){
    	var cmpAry = [];  //组件数组
    	if(this.menuName!=''){
    		cmpAry.push(Business.component.PageHeader.getInstance(this.getMenuName()));
    	}
		if(this.tbar){
    		cmpAry.push(new NS.container.Container({
	    		border : true,
	    		cls	   : 'xg-common_search',
	    		items  : this.tbar
    		}));
    	}
    	if(this.tools){
    		cmpAry.push(new NS.container.Container({
    			border: false,
    			cls	  : 'xg-common_tools',
    			items : this.tools
    		}));
    	}
    	if(this.grid){
    		cmpAry.push(new NS.container.Container({
	    		border : true,
	    		cls	   : 'xg-common_grid',
	            items  : this.grid
	        }));
    	}
    	if(this.bottomName!=''){
    		cmpAry.push(Business.component.PageBottom.getInstance(this.getBottomName()));
    	}
    	this.page = new NS.container.Container({
    		border : false,
    		cls	   : 'xg-common_container',
			items  : cmpAry,
			autoScroll : true
		});
    	this.page.on('afterrender',function(){
    		this.pageAfterRender();
		},this);
        this.setPageComponent(this.page);
    },
    
    /**
     * tbar config
     * @Override
     */
    _basicTbarConfig : {
    	style : 'background:#FFF !important; border:none;',
//    	bodyStyle : 'border:none; border-style:none;'
    }
});