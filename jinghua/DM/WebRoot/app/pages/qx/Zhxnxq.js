/**
 * 转换学年学期页面
 */
NS.define('Pages.qx.Zhxnxq', {
	extend: 'Template.Page',
    entityName:'entityName',
    modelConfig:{
    	serviceConfig:{
            xnData:'getXnBmbList',
            xqData:'getXqBmbList',
            currentXnxq:'getCurrentXnxq',
            switchXnxqResult:'switchXnxq',
            xnxqSjd:'getXnxqSjd'
            
    	}
    },
    /**
     * 基类定义的初始化入口
     */
    init:function(){
        this.callParent();
        this.callServiceWithTimeOut([{key:'currentXnxq',params:{}},{key:'xnData',params:{}},{key:'xqData',params:{}}], function(data) {
                  this.currentXnxq=data.currentXnxq;  
                  this.xnData=data.xnData;
                  this.xqData=data.xqData;
                  this.initAndDoLayoutPage();
              }, 60000,this);        
    },
    initAndDoLayoutPage:function(){
    	var items = this.createItems();
    	var component = this.component = this.createBaseForm({
    		height:210,
    		items:items,
    		buttons:[{text:'确定',name:'saveOrUpdate'}]
    	});
    	component.bindItemsEvent({
    		   saveOrUpdate:{event:'click',fn:this.updateHandler,scope:this}
	    });
        
        this.setPageComponent(component);
    },
    xnxqChange:function(event,target){
        var form=this.component;
        var values = form.getValues();
        var xnId=values['newXnId'];
        var xqId = values['newXqId'];
        if(xnId==undefined || xnId=='' || xqId==undefined || xqId==''){
            return ;
        }
       var params={xnId:values['newXnId'],xqId:values['newXqId']};
       this.callServiceWithTimeOut([{key:'xnxqSjd',params:params}], function(data) {
             var result=data.xnxqSjd;
             if(result.success==true){
                this.kssjDateField.setValue(result.xqkssj);
                this.jssjDateField.setValue(result.xqjssj);
             }
       }, 60000,this); 
    },
    updateHandler:function(){
        var form=this.component;
        if(form.isValid()){
           var values = form.getValues();
           if(values['oldXnId']==values['newXnId']&&values['oldXqId']==values['newXqId']){
               NS.Msg.info('已是当前学年学期,无需转换!');return;
           }
           var params={xnId:values['newXnId'],xqId:values['newXqId'],xqkssj:values['xqkssj'],xqjssj:values['xqjssj']};
           this.callServiceWithTimeOut([{key:'switchXnxqResult',params:params}], function(data) {
             var result=data.switchXnxqResult;
             if(result.success==true){
                NS.Msg.info('操作成功');
                this.component.setValues({oldXnId:values['newXnId'],oldXqId:values['newXqId']});
                this.newXnCombo.setValue(null);
                this.newXqCombo.setValue(null);
                this.kssjDateField.setValue(null);
                this.jssjDateField.setValue(null);
                
             }else{
                NS.Msg.error(result.msg);
             }
           }, 60000,this); 
        }
    },
    createItems:function(){
    	var oldXnCombo = this.createComboBox({
    		fieldLabel:'当前学年',
    		name:'oldXnId',
    		readOnly:true,
    		data:this.xnData
    	});
    	var oldXqCombo = this.createComboBox({
    		fieldLabel:'当前学期',
    		name:'oldXqId',
    		readOnly:true,
    		data:this.xqData
    	});
    	oldXnCombo.setValue(Number(this.currentXnxq.xnId));
    	oldXqCombo.setValue(Number(this.currentXnxq.xqId));
    	
    	var newXnCombo = this.newXnCombo = this.createComboBox({
    		fieldLabel:'转换学年',
    		name:'newXnId',
            data:this.xnData
    	});
    	var newXqCombo = this.newXqCombo = this.createComboBox({
    		fieldLabel:'转换学期',
    		name:'newXqId',
            data:this.xqData
    	});
        newXnCombo.on('change',function(event, target){
            this.xnxqChange(event,target)
        },this);
        newXqCombo.on('change',function(event, target){
            this.xnxqChange(event,target)
        },this);
        var kssjDateField =this.kssjDateField= new NS.form.field.Date({
            name:'xqkssj',
            allowBlank:false,
            fieldLabel:'学期开始时间',
            format:'Y-m-d'
        });
        
        var jssjDateField =this.jssjDateField= new NS.form.field.Date({
            name:'xqjssj',
            allowBlank:false,
            fieldLabel:'学期结束时间',
            format:'Y-m-d'
        });
    	
    	return [oldXnCombo,oldXqCombo,newXnCombo,newXqCombo,kssjDateField,jssjDateField];
    },
    /**
     * 新增或修改处理方法
     * @param {Object} cfg {form:form对象,fn:回调处理函数}
     * @param scope 作用域 一般应是this scope未定义的话this指向fn本身
     */
    saveOrUpdateUtil:function(cfg,scope){
    	var form = cfg.form,
    	    key = cfg.key,
    		fn = cfg.fn;
    	if(form.isValid()){
     	   var values = form.getValues();
     	   if(values['oldXnId']==values['newXnId']&&values['oldXqId']==values['newXqId']){
     		   NS.Msg.info('已是当前学年学期,无需转换!');return;
     	   }
           this.callServiceWithTimeOut([{key:'currentXnxq',params:{}},{key:'xnData',params:{}},{key:'xqData',params:{}}], function(data) {
                  this.currentXnxq=data.currentXnxq;  
                  this.xnData=data.xnData;
                  this.xqData=data.xqData;
                  this.initAndDoLayoutPage();
              }, 60000,this); 
           
     	   values.entityName = this.entityName;//添加entityName属性,供单表时使用,放在这里也不影响虚表操作
//     	   this.callService({key:cfg.key,params:values},function(data){
//                  fn.call(scope||this,data[key]);
     	   var data = {};
     	          data[key] ={success:false};
                  fn.call(scope||this,data[key]);
//          },this);
        }
    },
    /**
     * 创建baseForm
     * @param {Object} cfg 基础配置数据 用于覆盖默认form配置
     * @return
     */
    createBaseForm:function(cfg){
    	var basic = {
                autoScroll : true,
                border:false,
        		defaults:{
        			layout:'column',
        			columns:2
        		},
        		buttonAlign:'left'
            };
    	if(cfg){
    		NS.apply(basic,cfg);
    	}
    	return new NS.form.BasicForm(basic);
    },
    createComboBox:function(cfg){
    	var data =[];
    	if(cfg&&cfg.data){
    		data = NS.clone(cfg.data);
    		delete cfg.data;
    	}
    	var basic = {
    			data:data,
    			allowBlank:false,
    			queryMode:'local'
    	};
    	NS.apply(basic,cfg);
    	return new NS.form.field.ComboBox(basic);
    }
});
