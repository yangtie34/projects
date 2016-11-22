/**
 * @class Pages.pj.component.BaseModel
 * @extends Template.Page
 * @author liucs(liucs@126.com)
 * 说明:
 *    headerData、showData、window.treeData(在tree继承页面)等都是模拟数据
 *    
 * 模块基础model
 * 该模块继承Template.Page模板为最基础模板基础上的扩展model模板
 * 如果复用该model,请注意：
 *  1、serviceConfig内部的每个key值请不要变更，value根据实际需求变更或保持你想用的配置值
 *  2、如果有key不想使用,你需要保证这个你的功能中不使用该model中使用key的方法。
 *  3、如果你添加新的key,请不要重复！
 *  4、您还需要且必须更改entityName的值,这个值可以是实体名、可以是你自定义的虚表名称(符合系统规范)
 *  5、因为这个model本身的限制,如果您的业务逻辑与默认的处理不同,你需要在你js类文件中重写这个业务处理的方法
 *  6、不懂请询问本类编写者(请慎用,编写者可能会根据个人情况,变更该类的处理,但会尽量保持方法名和对应功能不变，会继续扩展)
 *  
 */
NS.define('Pages.pj.component.BaseModel', {
	/**
	 * 基础的模板定义名称
	 */
    extend: 'Template.Page',
    /**
     * 实体名称配置(根据个人需求情况变更) 
     */
    entityName:'',//测试实体名
    /**
     * 添加或者修改sercie的配置方法(当serviceConfig需要变更的时候重写,如果变更太大,覆盖modelConfig.serviceConfig即可)
     */
    addOrUpdateServiceConfig:function(){
    	//示例代码:
    	/*this.hanlderServiceConfig({
    		add:'addUrl',//覆盖原有配置里的add
    		show:'showUrl'//添加新的配置show
    		//为了父类的安全，暂不提供删除配置属性方式,如果有这种需求,使用者可以不使用这个key的处理即可
    		//如果这个key在本父类中有方法调用,那请重写这个方法
    	});*/
    },
    /**
     * 说明:这个是基础的form的items内部元素的方法,并提供了addFormItems和updateFormItems
     * 两种方法,在默认情况下需要重写baseFormItems方法内部代码并返回数组形式的items
     * 其中addFormItems供新增form的items使用
     *    updateFormItems供修改form的items使用
     * 默认两个form是基于baseFormItems即相同的
     * 如果因页面特殊原因使用者可以重写相应的add或update的formItems.
     *    
     * 基础布局下的form的items内部数据的重写
     * @return {Array}
     */
    baseFormItems:function(){
    	return this.defaultFormItems||[];//其中[]是使用者可覆盖的，这时候请将||前面的去除。默认是两列多行
    },
    /**
     * 
     * @return
     */
    addFormItems:function(){
    	return this.baseFormItems();
    },
    /**
     * 
     * @return
     */
    updateFormItems:function(){
    	return this.baseFormItems();
    },
    /**
     * 初始化组件方法(当页面布局发生变化的时候可重写)
     * @param {Object} data
     */
    initComponent: function(data) {
    	//做个示例:(假设)
    	this.tranData=data['queryTableHeaderAllData'];
    	this.tableData=data['queryGridData'];
        this.initGrid(this.tranData,this.tableData);
        this.initTbar();
        this.initAndDoLayoutPage();
    },
    /**
     * 初始化并渲染这个页面
     */
    initAndDoLayoutPage:function(){
    	var component = this.component = new NS.container.Panel({
            layout : 'fit',
            tbar: this.tbar,
            border:false,
            items: this.grid
        });
        this.setPageComponent(component);
    },
//*********************一般情况下重写上述部分即可*************************    
    //暂时不可用
    //addOrUpdateBindItemsEvent:function(com){
    	//示例代码：
    	/* com为this.tbar时
    	 * com.bindItemsEvent({
            'update': {event: 'click', fn: this.showUpdateForm, scope: this}
        });//覆盖了tbar里name为update的组件的监听和其处理方法
    	 *com为this.form时
    	com.bindItemsEvent({
            'reset' : {event : 'click',fn : this.resetFn,scope : this}
        });//添加了form里name为reset的组件的监听和其处理方法,当然正常情况下这个组件应该存在！
    	 */
    //},
    /**
     * model的链接配置(如若重写可看addOrUpdateServiceConfig())
     */
    modelConfig: {
        serviceConfig: {
        	//删除，含批量Grid数据路径
        	deletes:'base_deleteByIds',
        	//新增保存Grid数据路径
        	save:'base_save',
        	//更新保存Grid数据路径
        	update:'base_update',
        	//查询Grid表头数据
            queryTableHeaderAllData: "base_queryForAddByEntityName",
            //查询Grid数据路径
            queryGridData: "base_queryTableContent"
        }
    },
    /**
     * 初始化方法(NS.mvc.Contorller类中执行的,必须要有的方法)
     */
    init: function () {
    	this.initData();
    },
    /**
     * 因为Grid的数据分离请求：第一次交由统一数据请求一次过来，第二次交由Grid本身维护
     * 为了保持两则参数的一致性,因此这里维护了commonGridParams方法
     * 使用者可在此统一维护
     */
    commonGridParams:function(){
    	return {
    		entityName:this.entityName,
    		start:0,
    		limit:25
    	};
    },
    /**
     * 初始化数据方法,在该方法内部调用了初始化组件(initComponent)方法，
     * 如果请求数据变更大的话可同时重写该方法和initComponent()
     */
    initData: function () {
    	//执行新增、修改sercieConfig方法,方法体内执行重写相应的代码即可
    	this.addOrUpdateServiceConfig();
    	var params = {entityName: this.entityName};
        this.callService([
            {key: 'queryTableHeaderAllData', params: params},
            {key: 'queryGridData',params:this.commonGridParams()}
        ],
            function (data) {
        		this.initComponent(data);
            });
    },
    /**
   * 取得当前日期
   * @param {} Date
   */
  getCurrentDate:function(flag){
  	var me=this;
  	var currentDate="";
  	var now= new Date();
	var year=now.getFullYear();
	var month=now.getMonth()+1;
	var day=now.getDate();
	var hour=now.getHours();
	var minute=now.getMinutes();
	var min="";
	if(minute.toString().length==1){
   		min="0"+minute;
	}else{
		min=minute;
	}
	if(flag==1){
   		me.currentDate=(year+"-"+month+"-"+day);
	}else{
	   me.currentDate=(year+"-"+month+"-"+day+" "+hour+":"+min);
	}
   return me.currentDate;
  },
    /**
     * 更新Grid的basic的配置参数
     * @param {Array} columnData 表头数据
     * @param {Array} gridData grid数据
     */
    updateGridBasicConfig:function(){
    	var basic = {
                plugins: [new NS.grid.plugin.HeaderQuery()],
                autoScroll: true,
                border:false,
                multiSelect: true,
                lineNumber: false,
                checked:true
            };
    	return basic;
    },
    /**
     * 用于初始化Grid
     * @param {Object} tableHeader 表头数据
     * @param {Object} gridData Grid数据
     */
    initGrid: function (tableHeader,gridData) {
    	//供form默认展现字段使用
    	this.defaultFormItems = NS.Array.pluck(tableHeader,'sx');//通过'sx'查找table里的各个字段名称
        //转换标准的表头数据
    	var columnData = this.tranData = NS.E2S(tableHeader);
        //如果要改变Grid的展现形式,请见updateGridBasicConfig
        var basic = this.updateGridBasicConfig();
        //列配置
         columnConfig:[{name : 'idxOneMc', hidden : true},{name : 'idxTwoMc', hidden : true},
            	{name : 'idxScope', hidden : true},{name : 'idxType', hidden : false,editable:false},{name : 'idxSykc', hidden : true},{name : 'idxXmfz', hidden : true},
            	{name : 'idxXmqz', hidden : true},{name : 'idxLevel', hidden : true},{name:'flag',hidden:true}]
        //Grid展现必须的配置项
        var mustCfg = {
        		columnData: columnData,
        		serviceKey:{
        			key:'queryGridData',
        			params:this.commonGridParams()
        		},
                modelConfig: {
                    data : gridData
                },
                proxy:this.model
        };
        NS.apply(mustCfg,basic);
        this.grid = new NS.grid.Grid(mustCfg);
    },
    /**
     * 初始化标准Tbar(待扩展)
     */
    initTbar: function () {
    	//待完善...
        var basic = {
            items: [
                {xtype: 'button', text: '新增', name: 'add',iconCls:'page-add'},
                {xtype: 'button', text: '修改', name: 'update',iconCls:'page-update'},
                {xtype: 'button', text: '删除', name: 'deletes',iconCls:'page-delete'},
                {xtype: 'button', text: '查询', name: 'query',iconCls:'page-search'}
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
        this.tbar.bindItemsEvent({
            'deletes': {event: 'click', fn: this.deleteIds, scope: this},
            'add': {event: 'click', fn: this.showAddForm, scope: this},
            'update': {event: 'click', fn: this.showUpdateForm, scope: this}
        }); 
    },
    /**
     * 绑定各个组件事件的各种监听函数
     */
    bindEvent: function () {

    },
    /**
     * 执行业务删除的方法
     */
    deleteIds:function(){
    	var me = this,
    	data = this.grid.getSelectRows(),
    	ids = NS.Array.pluck(data,'id');
    	if(ids.length > 0){
    		this.deleteUtil('deletes',ids,function(backData){
    			if(backData.success){
    				this.grid.load();
    				NS.Msg.info('删除成功！');
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
     * 显示新增form
     */
    showAddForm : function(){
    	if(this.addForm){
    		this.addForm.show();
    	}else{
    		var items = this.addFormItems();
        	var form = this.form = this.createBaseForm({
        		buttons:[{text : '保存',name : 'add'},{text : '取消',name : 'cancel'}],
        		items:items
        	});
        	form.setTitle('新增页面');
            form.bindItemsEvent({
                'add' : {event : 'click',fn : this.submitAdd,scope : this},
                'cancel' : {event : 'click',fn : this.cancelForm,scope : this}
            });
    	}
    },
    /**
     * 提交新增form
     * 功能：新增成功后关闭窗口、刷新Grid数据
     * 新增失败提示，但不关闭窗口
     * 
     */
    submitAdd : function(){
       var me = this;
       this.saveOrUpdate('save',function(backData){
    	   //具体处理业务
    	   if(backData.success){
    	   	   me.cancelForm();
    		   NS.Msg.info('新增成功！');
    		   this.grid.load();
    	   }else{
				NS.Msg.error('新增失败！');
			}
       });
    },
    /**
     * 显示修改form
     */
    showUpdateForm : function(){
    	var rawsValues = this.grid.getSelectRows();
    	if(rawsValues.length !=1){
    		NS.Msg.warning('请选择一行进行修改!');return;
    	}
    	if(this.updateForm){
    		this.updateForm.setValues(rawsValues[0]);
    		this.updateForm.close();
    	}else{
    		var items = this.updateFormItems();
            var form = this.form = this.createBaseForm({
            	formType:'update',
            	buttons : [{text : '修改',name : 'update'},{text : '取消',name : 'cancel'}],
            	items:items
            });
            form.setTitle('修改页面');
            form.setValues(rawsValues[0]);
            form.bindItemsEvent({
                'update' : {event : 'click',fn : this.submitUpdate,scope : this},
                'cancel' : {event : 'click',fn : this.cancelForm,scope : this}
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
    	this.saveOrUpdate('update',function(backData){
     	   //具体处理业务
    		if(backData.success){
    		   //判断是否允许关闭
     		   NS.Msg.info('修改成功！');
     		   me.cancelForm();
     		   this.grid.load();
     	    }else{
				NS.Msg.error('修改失败！');
			} 
        });
    },
    /**
     * 重置form方法
     */
    resetForm:function(){
    	this.addForm?this.form = this.addForm:this.form = this.updateForm;
    	this.form.reset();
    },
    /**
     * 取消(关闭)form方法
     */
    cancelForm : function(){
        if(this.updateForm){
        		this.updateForm.close();
        }
        if(this.addForm){
        	this.addForm.close();
        }
    },
//**********************以下为工具方法,可修缮后通用***************************
    /**
     * 删除的工具方法 将业务逻辑相对分离了(待优化)
     * @param {Array} ids 删除的ids数组
     * @param {Funtion} fn 回调函数
     */
    deleteUtil : function(key,ids,fn){
    	   var me = this,len = ids.length;
	       NS.Msg.changeTip('提示','您确定删除这  '+len+' 行数据吗?',function(){
	    	       me.callService({key:key, params: {entityName:me.entityName,ids:ids.toString()}}, function(backData){
	    	            fn.call(me,backData[key]);
	    	        });
	       });
    },
    /**
     * 新增或修改处理工具方法
     * @param {String} key 为配置的ServiceConfig键
     * @param {Function} fn 回调函数
     */
    saveOrUpdate:function(key,fn){
    	if(this.form.isValid()){
     	   var values = this.form.getValues();
     	   var basic = {entityName:this.entityName};
     	   NS.apply(basic,values);
     	   this.callService({key:key,params:basic},function(data){
     		         //这里根据新增和修改的数据格式将返回的key直接取出，保留返回的值给回调函数使用
                     fn.call(this,data[key]);
            },this);
        }	
    },
    /**
     * 创建基础Form
     * @param {Object} cfg 配置参数
     * @return
     */
    createBaseForm:function(cfg){
    	var basic = {
                data : this.tranData,
                formType:'add',//默认form类型为add
                autoScroll : true,
                autoShow:true,
                columns : 2,
                margin : 10,
                padding : 10,
                modal:true// 魔态，值为true是弹出窗口的。
            };
    	NS.apply(cfg,basic);
    	return NS.form.EntityForm.create(cfg);
    },
    /**
     * 处理serviceConfig配置属性，新增/修改的话直接覆盖就行了
     * @param {Object} cfg 格式: {add:"addUrl",del:"delUrl"}
     */
    hanlderServiceConfig:function(cfg){
    	var me = this;
    	NS.Object.each(this.modelConfig,function(key,value){
    		if(key=='serviceConfig'){
    			NS.Object.each(cfg,function(k,v){
    			   me.modelConfig[key][k] = v;
    			});
    			return false;//转换后就直接返回
    		}
    	});
    }
});