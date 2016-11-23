/**
 * 学生范围设置页面[zhangyc]
 */
NS.define('Business.xg.XsfwSet', {
    extend : 'Template.Page',
	//后台服务配置数据
    modelConfig:{
        serviceConfig:{
    	'queryJxzzjgAddBjxxMenuTree':'base_queryJxzzjgAddBjxxMenuTree',//查询院系专业班级树
    	'queryRxnj':'base_queryBM',//入学年级
        'queryXszt':'base_queryBM',//学生状态
        'queryXjzt':'base_queryBM',//学籍状态
        'queryXxfs':'base_queryBM',//学习方式
        'queryZsjd':'base_queryBM',//招生季度
        'saveXsfw':'tbTyShlcHjService?saveXsfw',//保存数据
        'queryXsfw':'tbTyShlcHjService?queryXsfw'//查询数据

        }
    },
    entityName :'TcXxbzdmjg',//实体属性名
    /**
     *  初始化构造函数
     */
    init: function () {
        this.panels = [];
        this.initData();//初始化组件数据
    },

    /**
     *初始化组件数据
     */
    initData : function(){
        //增加更新服务配置方法
        this.callService([
            {key:'queryJxzzjgAddBjxxMenuTree'},
            {key:'queryRxnj',params:{entityName:this.entityName,bzdm:'XXDM-RXNJ'}},
            {key:'queryXszt',params:{entityName:this.entityName,bzdm:'XXDM-XSZT'}},
            {key:'queryXjzt',params:{entityName:this.entityName,bzdm:'XXDM-XJZT'}},
            {key:'queryXxfs',params:{entityName:this.entityName,bzdm:'DM-XXFS'}},
            {key:'queryZsjd',params:{entityName:this.entityName,bzdm:'XXDM-ZSJD'}}
        ],function(data){
                this.data = data;
        });
    },
    /**
     * 获取按钮组件
     */
    getButton : function(){
        var button =  new NS.button.Button({
            text: '设置学生范围',
            name: 'add',
            iconCls : 'page-update'
        });
        button.on('click',function(){
            if(this.data ==undefined){
                NS.Msg.error('请选择一条记录!');
            }else{
                if(this.lsmk==undefined || this.lsmk=='' ){
                    NS.Msg.error('请选择一条记录!');
                }else{
                    this.initXsfwSetData();
                }
            }

        },this);

        return button;
    },
    getButtonData : function(lsmk,yyId,xnId,xqId){
        this.lsmk = lsmk;
        this.yyId = yyId;
        this.xnId = xnId;
        this.xqId = xqId;
    },
    clearButtonData : function(){
    	this.lsmk = "";
    	this.yyId = "";
        this.xnId = "";
        this.xqId = "";
    },
    /**
     * 初始化win数据
     */
    initXsfwSetData : function(){
        this.callService([
            {key:'queryXsfw',params:{lsmk:this.lsmk,yyId:this.yyId,xnId:this.xnId,xqId:this.xqId}}
        ],function(data){
            this.showXsfwSetWin();
            if(data.queryXsfw.success){
                this.addTermCom(data.queryXsfw.data);
            }
        });

    },
    /**
     * 显示设置学生范围win
     */
	showXsfwSetWin: function () {
        var basic = {
            title:'学生范围设置',
            height : 500,
            width : 600,
            border : false,
            autoShow : true,
            autoScroll:true,
            modal : true,
            resizable : false,
            tbar:this.createTbar()
        };
        var window = this.window = new NS.window.Window(basic);
        window.on('destroy',function(event,target){
            this.panels = [];
        },this);

    },
    /**
     * 创建tbar
     * @return {NS.toolbar.Toolbar}
     */
    createTbar : function(){
        var basic = {
            items: [
                {xtype: 'button', text: '新增条件范围', name: 'add',iconCls : 'page-add'},
                {xtype: 'button', text: '保存条件范围', name: 'save',iconCls : 'page-save'}
            ]
        };
        var tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
            'add'   : {event: 'click', fn: this.addTermOneCom, scope: this},
            'save'   : {event: 'click', fn: this.saveTermData, scope: this}
        });
        return tbar;
    },
    /**
     * 添加一个条件组件
     */
    addTermOneCom : function(){
        var panel = this.createTermPanel();
        this.panels.push(panel);
        this.window.add(panel);
    },

    /**
     * 添加条件范围组件
     */
    addTermCom : function(datas){
        for(var i=0;i<datas.length;i++){
            var data = datas[i];
            var panel = this.createTermPanel(data);
            this.panels.push(panel);
            this.window.add(panel);
        }
    },

    /**
     * 创建条件组件面板
     */
    createTermPanel : function(data){
        //入学年级
        var rxnjBasic = {
            name:'rxnj',
            fieldLabel : '入学年级',
            emptyText: '请选择入学年级...',
            data:NS.clone(this.data.queryRxnj)
        }
        var rxnjCombox =  this.createCombox(rxnjBasic);

        var yxZyBjBasic = {
            name:'yxZyBj',
            fieldLabel : '学院&专业&班级',
            width:280,
            labelWidth :95,
            expanded:false,
            rootVisible:false,
            isParentSelect:true,
            margin:'0 20 20 0 ',
            pickerWidth: 280,
            emptyText: '请选择院系或专业或班级...',
            treeData:NS.clone(this.data.queryJxzzjgAddBjxxMenuTree)
        }
        var yxZyBjComboxTree= new NS.form.field.ComboBoxTree(yxZyBjBasic);

        var xsztBasic ={
            name:'xszt',
            fieldLabel : '学生状态',
            emptyText: '请选择学生状态...',
            data :NS.clone(this.data.queryXszt)
        }
        var xsztCombox = this.createCombox(xsztBasic);

        var xjztBasic = {
            name:'xjzt',
            fieldLabel : '学籍状态',
            emptyText: '请选择学籍状态...',
            data:NS.clone(this.data.queryXjzt)
        }
        var xjztCombox = this.createCombox(xjztBasic);

        var xxfsBasic ={
            name:'xxfs',
            fieldLabel : '学习方式',
            emptyText: '请选择学习方式...',
            data:NS.clone(this.data.queryXxfs)
        }
         var xxfsCombox = this.createCombox(xxfsBasic);

        var zsjdBasic ={
            name:'zsjd',
            fieldLabel : '招生季度',
            emptyText: '请选择招生季度...',
            data:NS.clone(this.data.queryZsjd)
        }
        var zsjdCombox = this.createCombox(zsjdBasic);

        var button =  new NS.button.Button({
            text: '删除',
            name: 'del',
            iconCls : 'page-delete'
        });

        var panel = new NS.form.BasicForm({
            frame:true,
            margin : '5,5,0,5',
            padding : '10,0,0,0',
            layout :{
                type : 'table',
                columns : 2
            },
            items : [rxnjCombox,
                     yxZyBjComboxTree,
                     xsztCombox,
                     xjztCombox,
                     xxfsCombox,
                     zsjdCombox,
                     button]
        });
       if(data!=undefined){
        var comboxArr = [rxnjCombox,yxZyBjComboxTree,xsztCombox,xjztCombox,xxfsCombox,zsjdCombox];
        var keyArr = ['rxnj','yxZyBj','xszt','xjzt','xxfs','zsjd'];
        this.setComboxValue(data,comboxArr,keyArr);
       }
        button.on('click',function(){
            this.panels= NS.Array.remove(this.panels,panel);
            this.window.remove(panel,true);
        },this);

        return panel;

    },
    /**
     *  设置combox的值
     */
    setComboxValue : function(data,comboxArr,keyArr){
        for(var i=0;i<comboxArr.length;i++){
            var combox = comboxArr[i];
            var key = keyArr[i];
            if(data[key]!=undefined){
                combox.setValue(Number(data[key]));
            }

        }

    },

    createCombox : function(basic){
        var b = {
            margin:'0 20 20 0',
            queryMode:'local',
            width:200,
            labelWidth :60
        }
        NS.apply(basic,b);
        var combox = new NS.form.field.ComboBox(basic);
        return combox
    },

    saveTermData : function(){
        var p ={
            yyId:this.yyId,
            lsmk:this.lsmk,
            xnId:this.xnId,
            xqId:this.xqId,
            term:this.getPanelData()
        }
        this.callService({key:'saveXsfw',params:p},function(data){
            if(data.saveXsfw.success){
                this.window.close();
                NS.Msg.info("保存成功!");
            }else{
                NS.Msg.info("保存失败!");
            }

        });
    },

    getPanelData : function(){
        var values =[];
        for(var i=0;i<this.panels.length;i++){
            var panel = this.panels[i];
            var value = panel.getValues();
            values.push(value);
        }
        return values;

    }
});