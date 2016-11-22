/**
 * 权限管理－角色管理
 */
NS.define('Pages.qx.Jsxxgl', {
	extend : 'Pages.qx.model.BaseModel',
	/**
	 * 请求后台服务配置
	 */
	modelConfig: {
        serviceConfig: {
        	//删除，含批量Grid数据路径
        	deletes:'permiss_deleteRole',
        	//新增保存Grid数据路径
        	save:'permiss_saveRole',
        	//更新保存Grid数据路径
        	update:'permiss_updateRole',
        	//查询Grid表头数据
        	queryComponents: "base_queryForAddByEntityName",
            //查询Grid数据路径
            queryGridData: "base_queryTableContent",
//            queryTreeData:"getPermissionJson",
            queryTreeData : 'roleService?getPermissionJsonByRoleId',
            saveRolePermission:"saveRolePermission"
        }
    },
	
    //角色实体类
    entityName:"TsJs",
    //form参数设置
    formPropertys:{
    	width:300,
        height:140,
        columns:1
    },
    initData: function () {
    	//执行新增、修改sercieConfig方法,方法体内执行重写相应的代码即可
//    	this.addOrUpdateServiceConfig();
    	this.pageParams().sfky=1;
    	var params = {entityName: this.entityName};
        this.callService([
            {key: 'queryComponents', params: params},
            {key: 'queryGridData',params:this.pageParams()}
        ],
            function (data) {
        		this.initComponent(data);
            });
    },
    baseFormItems:function(){
    	var items = [{name : 'id',hidden : true},'jslxId','ms'];
    	return items||[];
    },
    initTbar: function () {
    	//待完善...
        var basic = {
            items: [
                {xtype: 'button', text: '新增', name: 'add',iconCls:'page-add'},
                {xtype: 'button', text: '修改', name: 'update',iconCls:'page-update'},
                {xtype: 'button', text: '删除', name: 'deletes',iconCls:'page-delete'},
                {xtype: 'button', text: '设置权限', name: 'setPermiss'}
                
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
            'deletes': {event: 'click', fn: this.deleteIds, scope: this},
            'add': {event: 'click', fn: this.showAddForm, scope: this},
            'update': {event: 'click', fn: this.showUpdateForm, scope: this},
            'setPermiss': {event: 'click', fn: this.initSetPanel, scope: this}
        }); 
    },
    /**
     * 页面布局
     */
    initPageLayout:function(){
    	var component = new NS.container.Panel({
            layout : 'fit',
            tbar: this.tbar,
            border:false,
            items: this.grid
        });
    	this.setPageComponent(component);
    },
    /**
     * 保存角色权限
     */
    savePermiss:function(){
    	//获取选中所有节点
    	var records =this.tree.getChecked(),
		checkIds = [];
//    	var selRecords = NS.Array.removeAt(records,0);
    	
//    	checkIds = NS.Array.pluck(selRecords,'id');
		/**
		 * 遍历选中的记录
		 */
		for(var i=0;i<records.length;i++){
			var record = records[i];
			if(record!=undefined && record.id != '0' ){
				checkIds.push(record.id);
			};
		};
		var selectRows =this.grid.getSelectRows();
		//获取表格请求参数
		var param = this.pageParams();
		//角色ID
		param.roleId = selectRows[0].id;
		param.singleReturnNoComponent=1;
		//菜单资源IDS
		param.menuIds = checkIds.toString() ;
		/*******用于权限树请求的参数*****************/
		this.callService({key:'saveRolePermission',params:param},function(data){
			if (data.success) {
				  NS.Msg.info('权限分配成功！');
				// 关闭当前窗体
				  this.acWindow.close();
			} else {
				NS.Msg.error('权限分配失败！');
			}
		});
		
    },
	/**
	 * 全部授权方法
	 */
	grantAll : function() {
		this.tree.checkAllNode(true);
	},
	/**
	 * 全部取消授权方法
	 */
	removeAllGrant : function() {
		this.tree.unCheckAllNode(false);
	},
	/**
	 * 
	 */
	closeWin:function(){
		if(this.acWindow){
            this.acWindow.close();
        }
	},
    /**
     * 对角色进行菜单资源设置
     * @param roleId
     * @return
     */
    initSetPanel : function() {
    	 //获取表格中选中的记录
		var record = this.grid.getSelectRows()[0];
		if(record == undefined){
			NS.Msg.warning('请选择一行再进行设置权限操作!');return;
		}
		//角色ID
		var roleId = record.id;
    	var settingBasic = {
        		items: [
    	                {xtype: 'button', text: '全部授权', name: 'seletAll',iconCls : 'page-add'},
    	                {xtype: 'button', text: '取消全部授权', name: 'cancalAll',iconCls : 'page-cancal'},
    	                {xtype: 'button', text: '保存', name: 'save',iconCls : 'page-save'},
    	                {xtype: 'button', text: '关闭', name: 'close',iconCls : 'page-close'}
    	            ]	
        };
    	this.settingTbar =new NS.toolbar.Toolbar(settingBasic);
    	this.settingTbar.bindItemsEvent({
            'seletAll': {event: 'click', fn: this.grantAll, scope: this},
            'cancalAll': {event: 'click', fn: this.removeAllGrant, scope: this},
            'save': {event: 'click', fn: this.savePermiss, scope: this},
            'close': {event: 'click', fn: this.closeWin, scope: this}
        });
       
		//获取表格请求参数
		var param = this.pageParams();
		
		param.roleId = roleId;
		/*******用于权限树请求的参数*****************/
		this.callService({key:'queryTreeData',params:param},function(data){
				this.initSettingTree(data,this.settingTbar);
		});
		
	},
	/**
	 * 初始化左边的菜单资源树
	 */
	initSettingTree:function(data,tbar){
		var obj = {
					treeData:data.queryTreeData,
					border : false,
					border : true,
					rootVisible : false,
					margin : '0 0 0 0',
					multiple:	true,
					multyFields:[{"dataIndex":"text"}]
					
		};
		
		var tree = this.tree = new NS.container.Tree(obj);
		tree.addListener('itemclick',function(com,data,item){
 			// 实现自己的逻辑
//			console.log(data);
			/*var param = this.pageParams();
			param.fjdId = data.id;
			this.callService({
	             key : 'queryGridData',
	             params : param
	        },function(data){
	        	this.grid.loadData(data.queryGridData);
	        },this);*/
 		});
		if(this.acWindow){
            this.acWindow.close();
        }
		
		this.acWindow =new NS.window.Window({
			title:'设置权限',
			width : 400,
            height : 500,
            layout : 'fit',
            modal:true,
			border : false,
			tbar : tbar,
			autoShow : true,
			items :tree
			});
		
		
	}
	
})