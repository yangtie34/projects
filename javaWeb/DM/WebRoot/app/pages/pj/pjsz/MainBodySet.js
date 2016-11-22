/**
 * 评教设置-设置评教主体页
 * @author liucs
 * @
 */
NS.define('Pages.pj.pjsz.MainBodySet',{
	extend :'Pages.pj.component.BaseModel',
	entityName : 'TbPjMain',

	baseFormItems:function(){
		var formItems=new Array();
		
		formItems.push({name : 'id',hidden : true});
		formItems.push('pjztmc');
		formItems.push('sfky');
		formItems.push('bz');
		formItems.push({name :'czr',hidden : true});
		formItems.push({name :'czsj',hidden :true});
		
    	return formItems;
    },
   
    /**
     * rewrite-addFormItems
     * @return
     */
    addFormItems:function(){
    	return this.baseFormItems();
    },
     /**
     * rewrite-updateFormItems
     * @return
     */
    updateFormItems:function(){
    	return this.baseFormItems();
    },
    
     /**
      * 添加Form
      * reWrite - showAddForm
      */
    showAddForm : function(){
    	var items = this.addFormItems();
    	var form = this.form = this.createBaseForm({
    		title : '新增评教主体',
    		width :630,
    		columns:1,
    		padding : '10 10 10 10',
    		buttons:[{text : '保存',name : 'add'},{text : '取消',name : 'cancel'}],
    		items:items
    	});
        form.bindItemsEvent({
            'add' : {event : 'click',fn : this.submitAdd,scope : this},
            'cancel' : {event : 'click',fn : this.closeForm,scope : this}
        });
    },
     /**
     * 提交新增form
     */
    submitAdd : function(fn){
       var me = this;
       this.saveOrUpdate('save',function(backData){
    	   //具体处理业务
    	   if(backData.success){
    		   NS.Msg.info('保存成功！');
    		   this.grid.load();
    	   }else{
				NS.Msg.error('保存失败！');
			} 
    	    
       });
    },
     /**
      * 修改Form
      * reWrite - showUpdateForm
      */
    showUpdateForm : function(){
    	var items = this.updateFormItems();
    	var rawsValues = this.grid.getSelectRows();
    	if(rawsValues.length !=1){
    		NS.Msg.warning('请选择一行进行修改!');return;
    	}
        var form = this.form = this.createBaseForm({
        	title : '修改评教主体',
        	width :630,
        	formType:'update',
        	buttons : [{text : '修改',name : 'update'},{text : '取消',name : 'cancel'}],
        	items : items
        });
        var values = this.grid.getSelectRows()[0];
        form.setValues(values);
        form.bindItemsEvent({
            'update' : {event : 'click',fn : this.submitUpdate,scope : this},
            'cancel' : {event : 'click',fn : this.closeForm,scope : this}
        });
    },
     /**
     * 取消(关闭)form方法
     */
    closeForm : function(){
        if(this.form){
        	this.form.close();
        }
    },
	/**
 	 * reWrite-表格样式
 	 * @return {}
 	 */
 	  updateGridBasicConfig:function(){
 	  	var btn={
 	  					buttonText : '主体成员',
                        name :'setMemberBtn',
                        style : {
                            color : 'red',
                            font : '18px',
                            align:'center'
                        }
 	  	};
    	var basic = {
                plugins: [new NS.grid.plugin.HeaderQuery()],
                autoScroll: true,
                border:false,
//              multiSelect: true,
                checked:true,
                lineNumber: true
                /**
                columnConfig : [
                { text: '操作',
                  name :'operator',
                  width : 150,
                  xtype : 'buttoncolumn',
                  buttons : [btn]
          		 }
          		 ]*/
           };
    	return basic;
    },
     /**
     * 初始化标准Tbar
     */
    initTbar: function () {
        var basic = {
            items: [
                {xtype: 'button', text: '新增', name: 'add',iconCls:'page-add'},
                {xtype: 'button', text: '修改', name: 'update',iconCls:'page-update'},
                {xtype: 'button', text: '删除', name: 'deletes',iconCls:'page-delete'}
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
            'deletes': {event: 'click', fn: this.deleteIds, scope: this},
            'add': {event: 'click', fn: this.showAddForm, scope: this},
            'update': {event: 'click', fn: this.showUpdateForm, scope: this}
        }); 
        return this.tbar;
    },
      /**
     * 初始化并渲染这个页面
     */
    initAndDoLayoutPage:function(){
    	var component = new NS.container.Panel({
            layout : 'fit',
            tbar: this.tbar,
            border:false,
            items: this.grid
        });
         //给表格列增加绑定事件监听
        /**
        this.grid.bindItemsEvent({
 		 	'operator': {event: 'buttonclick', fn: this.createMemberForm, scope: this}
 		})
 		*/
        this.setPageComponent(component);
    },
    /**
     * 主体成员Form
     * @param {} id
     */
    createMemberForm : function(){
 	  	var row=this.grid.getSelectRows();
 	  	var id=row[0].id;
    	NS.Msg.alert('主体成员窗体',id);
    
    }
})