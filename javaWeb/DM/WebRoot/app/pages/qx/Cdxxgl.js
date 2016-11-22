/**
 * 权限管理－菜单管理
 */
NS.define('Pages.qx.Cdxxgl', {
	extend : 'Pages.qx.model.TreeModel',
	/**
	 * 请求后台服务配置
	 */
	modelConfig: {
        serviceConfig: {
        	//删除，含批量Grid数据路径
        	deletes:'deleteMenu',
        	//新增保存Grid数据路径
        	save:'saveMenu',
        	//更新保存Grid数据路径
        	update:'base_update',
        	//查询Grid表头数据
        	queryComponents: "base_queryForAddByEntityName",
            //查询Grid数据路径
            queryGridData: "base_queryTableContent",
            queryTreeData:"getAllMenuTree"
        }
    },
    baseFormItems:function(){
        if(this.newTreeData){
            var items = [{name : 'id',hidden : true},'mc','cdssflId','pxh','sfky','anlxId','cdlj',{treeData : NS.clone(this.newTreeData),name : 'fjdId'}];
            return items||[];
        }else{
            var items = [{name : 'id',hidden : true},'mc','cdssflId','pxh','sfky','anlxId','cdlj', 'fjdId'];
            return items||[];
        }

    },
    entityName:"TsCdzy",
    
    formPropertys:{
    	width:300,
        height:300,
        columns:1
    },   
    /**
     * 显示新增form
     */
    showAddForm : function(){
        var node = this.tree.getSelectionModel();
        var items = this.addFormItems();
        var form = this.addForm = this.createBaseForm({
            width:this.formPropertys.width,
            height:this.formPropertys.height,
            buttons:[{text : '提交',name : 'add'},{text : '取消',name : 'cancel'}],
            items:items
        });
        form.setTitle('新增页面');
        form.bindItemsEvent({
            'add' : {event : 'click',fn : this.submitAdd,scope : this},
            'cancel' : {event : 'click',fn : function(){
                form.close();
            },scope : this}
        });

        if(node){
            this.addForm.setFieldValueByName('fjdId',node.id);
        }else{
            this.addForm.setFieldValueByName('fjdId',0);
        }

    },
    /**
     * 因为Grid的数据分离请求：第一次交由统一数据请求一次过来，第二次交由Grid本身维护
     * 为了保持两则参数的一致性,因此这里维护了pageParams方法
     * 使用者可在此统一维护
     */
    pageParams:function(){
    	return {
    		entityName:this.entityName,
    		start:0,
    		limit:25,
    		sfky:1,
    		fjdId:0
    	};
    },
    /**
     * 重新刷新列表数据
     */
    reflash : function(){
    	var me = this;
    	var node = me.tree.getSelectionModel();
    	 var obj = me.pageParams();
    	 me.callService({
             key : 'queryTreeData'
        },function(data){
            this.newTreeData = NS.clone(data.queryTreeData);
          	me.tree.refresh(data.queryTreeData);
        },this);
        if(node){
            obj.fjdId = node.id;
            me.grid.load(obj);
        }
    },
    /**
     * 新增或修改处理工具方法
     * @param {String} key 为配置的ServiceConfig键
     * @param {Function} fn 回调函数
     */
    saveOrUpdate:function(key,fn){
        if(this.form.isValid()){
            var values = this.form.getValues();
            if(values.fjdId == "")values.fjdId = 0;
            var basic = {entityName:this.entityName};
            NS.apply(basic,values);
            this.callService({key:key,params:basic},function(data){
                //这里根据新增和修改的数据格式将返回的key直接取出，保留返回的值给回调函数使用
                fn.call(fn,data[key]);
            },this);
        }
    }
				
})