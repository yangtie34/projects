/**
 * 基础模板页面[xuebl]
 */
NS.define('Business.xg.model.BasicModel', {
	
	extend : 'Template.Page',
	//混合类
	mixins : ['Pages.xg.Util'], //工具
	//预加载tpl
	tplRequires : [],
	//css预加载
	cssRequires : ['app/business/xg/template/style/xg-common.css'],
	
	modelConfig : {
	    serviceConfig: {
        	//查询Grid表头数据
	    	queryTableHeader: {
	    		service : 'base_queryForAddByEntityName',
	    		params  : {sfky:1}
	    	},
            //查询Grid数据路径
        	queryTableData  : 'base_queryTableContent',
        	//新增保存Grid数据路径
        	baseSave		: 'base_save',
        	//更新保存Grid数据路径
        	baseUpdate		: 'base_update',
        	//删除，含批量Grid数据路径
        	baseDelete		: 'base_deleteByIds'
        }
	},
	
	//TODO config start
	requires : ['Business.component.PageHeader', //表头
	            'Business.component.PageBottom'], //底部
	menuName : '', //菜单名
	bottomName : '', //底部名称
	entityName : '', //实体名
	/**
	 * grid 配置
	 * TODO
	 */
	getGridParams : function(){
		return {start:0, limit:25};
    },
    /**
	 * 实体名配置
	 * @private
	 */
	getEntityParams : function(){
		return {entityName:this.entityName};
	},
	/**
	 * TODO 可以重写 更新serviceConfig {'update':'base_update'}
	 */
	addServiceConfig : {},
	/**
	 * TODO 可以重写 更新serviceParams return {'update':{entityName:'TbXxxx'}}
	 */
	addServiceParams : NS.emptyFn,
	/**
	 * TODO 初始化数据前
	 */
	beforeInit : NS.emptyFn,
	
	init : function(){
		this.beforeInit();
		//非常重要，一般不需要重写init。执行新增、修改sercieConfig方法,方法体内执行重写相应的代码即可
		this.updateServiceConfig(this.addServiceConfig);
		//更新serviceParams
		var p = this.addServiceParams();
		for(var key in p){
			this.updateServiceParams(key, p[key]);
		}
		this.initData();
	},
	/**
	 * TODO 初始化数据
	 */
	initData : function(){
		this.initComponent();
		this.initMask();
	},
	/**
	 * TODO 初始化组件
	 */
	initComponent : function(){
        this.initPage();
	},
    /**
     * TODO 初始化页面
     */
    initPage : function(){
    	var cmpAry = [];
    	if(this.menuName!=''){
    		cmpAry.push(Business.component.PageHeader.getInstance(this.getMenuName()));
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
    pageAfterRender : NS.emptyFn,
    

    /********************这是公共方法----可以重写**********************************/
	/**
	 * _loadGrid
	 */
    loadGrid : function(params){
		this.grid.load(params || {});
	},
	
    /********************这是公共方法----不可重写**********************************/
    /**
     * _获取菜单名称
     */
	getMenuName:function(){
		return this.menuName;
	},
	/**
	 * _获取底部说明名称
	 */
	getBottomName:function(){
		return this.bottomName;
	},
	/**
	 * _遮罩
	 */
	initMask : function(){
		this.mask = new NS.mask.LoadMask({
	         target : this.page,
	         msg : '数据加载中,请稍候...'
	     });
	}
	
});