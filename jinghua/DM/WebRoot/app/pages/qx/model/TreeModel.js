/**
 * 树形模板的增删改查页面
 */
NS.define('Pages.qx.model.TreeModel', {
    extend: 'Pages.qx.model.BaseModel',
    entityName:'',
    formPropertys:{
    	width:300,
        height:350,
        columns:1
    },
    /**
     * 重写
     */
/*    addOrUpdateServiceConfig:function(){
    	this.hanlderServiceConfig({
    		  queryTreeData:""
           });
    },*/
    /**
     * 重写 
     * @return {Array}
     */
    baseFormItems:function(){
//    	var items = [{name : 'id',hidden : true},
//    	             'xh','xm','yyxId','xyxId','xzy',
//    	             'bjId','xnj','ydlb','ydyy','xxz',
//    	             'yzy','ybjId','ynj'];
    	return this.defaultFormItems||[];
    },
    initData: function () {
    	//执行新增、修改sercieConfig方法,方法体内执行重写相应的代码即可
//    	this.addOrUpdateServiceConfig();
    	this.pageParams().sfky=1;
    	var params = {entityName: this.entityName};
    	
        this.callService([
            {key: 'queryComponents', params: params},
            {key: 'queryGridData',params:this.pageParams()},
            {key:'queryTreeData',params:{entityName: this.entityName,fjdId:0}}
        ],
            function (data) {
        		this.initComponent(data);
            });
    },
    /**
     * 因为考虑到data传递之后,需要‘分发’,
     * 并且现在不确定‘分发’的key是否均相同,因此这里一般也需要重写
     * @param {Object} data
     */
    initComponent:function(data){
    	this.initGrid(data['queryComponents'],data['queryGridData']);
        this.initTbar();
        this.initTree(data['queryTreeData']);
        this.initPageLayout();
    },
    /**
     * 重新布局页面
     */
    initPageLayout:function(){
    	var gridPanel = new NS.container.Panel({
            layout : 'fit',
            tbar: this.tbar,
            border:false,
            items: this.grid
        });
        var component = new NS.container.Panel({
        	layout:'border',
        	border:false,
        	items:[{
        		region:'west',
        		border:false,
        		split : true,
	            collapsible : true,
				border:'1 1 0 1',
				margins: '5 0 0 5',
				autoScroll:true,
        		width:'20%',
        		layout:'fit',
        		items:this.tree
        	},{
        		region:'center',
        		border:false,
        		layout:'fit',
        		items:gridPanel
        	}]
        });
        this.setPageComponent(component);
    },
    /**
     * 显示新增form(模拟的重写,其他很多地方如果有需要,使用者可自行重写)
     */
    showAddForm : function(){
    	var items = this.addFormItems();
    	var form = this.addForm = this.createBaseForm({
    		width:this.formPropertys.width,
    		height:this.formPropertys.height,
    		buttons: [{text : '提交',name : 'add'},{text:'重置',name:'reset'},{text : '取消',name : 'cancel'}],
    		items:items
    	});
    	form.setTitle('新增页面');
        form.bindItemsEvent({
            'add' : {event : 'click',fn : this.submitAdd,scope : this},
            'cancel' : {event : 'click',fn : this.cancelForm,scope : this},
            'reset' : {event : 'click',fn : this.resetForm,scope : this}
        });
    },
    
    /**
     * 提交新增form
     * 功能：新增成功后关闭窗口、刷新Grid数据
     * 新增失败提示，但不关闭窗口
     * 
     */
    submitAdd : function(){
       var me = this;
       this.form = this.addForm;
       this.saveOrUpdate('save',function(backData){
    	   //具体处理业务
    	   if(backData.success){
    		   me.cancelForm();
    		   NS.Msg.info('新增成功！');
    		   me.reflash();
    	   }else{
				NS.Msg.error('新增失败，'+(backData.info ==''?'！':backData.info));
			}
       });
    },
    /**
     * 重新刷新列表数据
     */
    reflash : function(){
    	var me = this;
    	var node = me.tree.getSelectionModel();
    	 var obj = me.pageParams();
	     obj.fjdId = node.id;
		 me.grid.load(obj);
    },
    /**
     * 执行业务删除的方法
     */
    deleteIds:function(){
    	var me = this,data = this.grid.getSelectRows(),ids = NS.Array.pluck(data,'id');
    	if(ids.length > 0){
    		this.deleteUtil('deletes',ids,function(backData){
    			if(backData.success){
    				NS.Msg.info('删除成功！');
    				me.reflash();
    			}else{
    				NS.Msg.error('删除失败！');
    			} 
    		});
	    }else{
	    	 NS.Msg.warning({
	    		   msg:'您尚未选择任何数据!'
	    	   });
	    }
    },
    /**
     * 提交修改form
     * 功能：用于提交修改的form,
     * 修改成功后窗口关闭,刷新Grid数据
     * 修改失败则不关闭窗口
     */
    submitUpdate : function(){
    	var me = this;
    	me.form = me.updateForm;
    	me.saveOrUpdate('update',function(backData){
     	   //具体处理业务
    		if(backData.success){
    		   //判断是否允许关闭
    		   me.cancelForm();
    		   NS.Msg.info('修改成功！');
    		   me.reflash();
     	    }else{
				NS.Msg.error('修改失败！');
			} 
        });
    },
    /**
     * 初始化树形数据
     */
    initTree:function(data){
    	var me=this,obj = {
				treeData:data,
				border : 1,
				rootVisible : false,
				margin : '0 0 0 0',
				border:false,
				autoScroll:true,
				multiple:	true,
				multyFields:[{dataIndex:"text"}]/*,
				serviceKey:'queryTreeData'*/
		};
		this.tree= new NS.container.Tree(obj);
		this.tree.addListener('itemclick',function(slef,record){
			me.changeGridData(slef,record);
		});
    },
    changeGridData:function(slef,record){
    	var me = this;
    	//改变fjdId的值
    	//发送请求到后台查询table数据
    	var obj = me.pageParams();
    	obj.fjdId = record.id;
    	me.callService({
            key : 'queryGridData',
            params :obj
       },function(data){
         	me.grid.loadData(data.queryGridData);
       },this);
    }
});