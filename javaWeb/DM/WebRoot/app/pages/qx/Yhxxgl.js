/**
 * 权限管理－用户管理
 */
NS.define('Pages.qx.Yhxxgl', {
	extend : 'Pages.qx.model.TreeModel',
	/**
	 * 请求后台服务配置
	 */
	modelConfig: {
        serviceConfig: {
        	//删除，含批量Grid数据路径
        	deletes:'deleteTsuser',
        	//新增保存Grid数据路径
        	save:'saveUserInfo',
        	//更新保存Grid数据路径
        	update:'updateUserInfo',
        	//查询Grid表头数据
        	queryComponents: "base_queryForAddByEntityName",
            //查询Grid数据路径
            queryGridData: "queryTsUserList",
            queryTreeData:"queryJxzzjgTree"
        }
    },
    
 /*   baseFormItems:function(){
    	var items = [{name : 'id',hidden : true},'username',{name : 'zgId',hidden : true},'zgMc',{name : 'rylbId',hidden : true},{name :'bmId',hidden : true},'bmMc',{name :'ztId',hidden : true},'ztMc'
    		           ,{name :'jsId',hidden : true},'jsMc','groupPermiss',{name :'groupPermissId',hidden : true},'zzjgMc',{name :'zzjgId',hidden : true}];
    	return items||[];
    },*/
    items: [{name : 'id',hidden : true},'loginName',{name : 'zgMc',hidden : true},'zgId', 'rylbId',{name :'bmMc',hidden : true},'bmId',{name :'ztMc',hidden : true},'ztId'
	           ,{name :'jsMc',hidden : true},{name:'jsId',multiSelect:true},'groupPermissId',{name :'groupPermiss',hidden : true},'zzjgId',{name :'zzjgMc',hidden : true}],
    addFormItems:function(){
    	var arr = new Array();
    	this.items[this.items.length-1]={name:'username',sfkbj:1,readOnly:false};
    	return arr.concat(this.items||[]);
    },
    updateFormItems:function(){
    	var arr = new Array();
    	this.items[this.items.length-1]={name:'username',readOnly:true};
    	return arr.concat(this.items||[]);
    },
    entityName:"VUSER",
    
    formPropertys:{
    	width:300,
        height:300,
        columns:1
    },   
    /**
     * 重新刷新列表数据
     */
    reflash : function(){
		 this.grid.load();
    },
    /**
     * 因为Grid的数据分离请求：第一次交由统一数据请求一次过来，第二次交由Grid本身维护
     * 为了保持两则参数的一致性,因此这里维护了pageParams方法
     * 使用者可在此统一维护
     */
    pageParam:{
    	entityName:this.entityName,
    	start:0,
    	limit:25,
    	node:0
    },
    initTbar: function () {
    	var single = new NS.grid.query.SingleFieldQuery({
            data : this.tranData,
            grid : this.grid
        });
        var senior = new NS.grid.query.SeniorQuery({
            data : this.tranData,
            grid : this.grid
        });
    	//待完善...
        var basic = {
            items: [
                {xtype: 'button', text: '新增', name: 'add',iconCls:'page-add'},
                {xtype: 'button', text: '修改', name: 'update',iconCls:'page-update'},
                {xtype: 'button', text: '删除', name: 'deletes',iconCls:'page-delete'},
                {xtype: 'button', text: '重置密码', name: 'resetpassword'},
                single,senior
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
            'deletes': {event: 'click', fn: this.deleteIds, scope: this},
            'resetpassword': {event: 'click', fn: this.resetPassword, scope: this},
            'add': {event: 'click', fn: this.showAddForm, scope: this},
            'update': {event: 'click', fn: this.showUpdateForm, scope: this}
            
        }); 
    },
    /**
	 * @description 执行重置密码操作
	*/
	resetPassword : function() {
		var me = this;
		//重置密码参数	
	    var params = ['updatePassword,updateResetPassword,"singleReturnNoComponent":"1"'];							
		var selection = me.grid.getSelectRows();// 选择的行
		if (selection.length == 0) {
			Ext.Msg.alert('提示', '请选中至少一行数据！');
			return;
		}
		var idArray = [];
		Ext.each(selection, function(sel) {// 获取选择的行的id
			idArray.push(sel['id']);
		});
		// 获取删除参数
		var deletep = US.CommonUtil.generateParams([ params + ',"ids":"'+ idArray + '"' ]);
		Ext.MessageBox.confirm('提示', '确定要重置密码吗？', function(btn) {
			if (btn == 'yes') {
				Conn.request({
					params : 'components=' + deletep,
					success : function(response) {
						dataJson = Ext.JSON.decode(response.responseText);
						if (dataJson.success=='true') {
							Ext.Msg.alert('提示',  dataJson.info);
						} else {
							Ext.Msg.alert('提示',  dataJson.info);
						}					
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '重置密码失败！');
					}
				});
			}
		});
	},
    /**
     * 改变查询条件
     * @param slef
     * @param record
     */
    changeGridData: function(slef,record){
    	var me = this;
    	me.pageParam.node =record.id;
    	me.grid.load(me.pageParam);
    	/*me.callService({
            key : 'queryGridData',
            params : me.pageParam
       },function(data){
//       	console.log(data);
       },this);*/
    }			
})