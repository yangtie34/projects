/**
 * 学工-奖助-设置[类型条件设置form]
 * 组件 model
 */
NS.define('Business.xg.jczd.TjszForm',{
    extend : 'Template.Page',
    //混合类
	mixins : ['Pages.xg.Util'], //工具
	//后台服务配置数据
    modelConfig:{
        serviceConfig:{
        	//查询类型对应的基本条件
    		queryJbtjByLxId  : 'jzLxszService?queryJzJbtjByLxId',
    		//更新条件参数
    		updateJbtjByLxId : 'jzLxszService?updateJzTjcsByLxId'
        }
    },
    
    nameFront : 'tjName_', //checkbox 前缀
    valueFront: 'tjValue_',//后面的组件 前缀
    /**
     * 提交后的回调方法，可重写
     */
    submit : NS.emptyFn,

    /**
     * 绑定回调方法
     */
    on : function(eventName, fn, scope){
    	if(typeof enentName == 'object'){
    		
    	}
    	this[eventName] =  function(){
    		fn.call(scope);
    	};
    },

    /**
     * 入口方法
     */
    show : function(id, lsmk){
    	this.id = id; //类型id
    	this.lsmk = lsmk; //隶属模块
    	this.XgAjax('queryJbtjByLxId',{id:id},function(data){
    		if(data.success){
    	    	var _data = data.data;
    	    	this.initComponent(_data);
    		}else{
    			this.XgError();
    		}
    	},this);
    },

    /**
     * 初始化组件
     */
    initComponent : function(data){
    	this.createForm(data);
    	this.initWin();
    },

    /**
     * 初始化form
     */
    createForm : function(data){
    	var items1 = [],
    		_width = 100; //右侧组件宽度
    	
    	//循环创建组件放入左右两个form
    	for(var i=0,len=data.length; i<len; i++){
    		var o 	 = data[i],
    			mc	 = o.mc,
    			tjlx = o.tjlx, //条件类型 textfield, checkbox, combobox, HKXZ
    			tjId = o.id,
    			zdbs = this.nameFront+tjId, //字段标示 前缀+id；PJCJ:100001，DKCJ:100002，HKXZ:100003，LPTK:100004
    			zdbsV= this.valueFront+tjId,//字段标示 这个是值
    			value= o.value, //
    			sfky = o.sfky; // 0/1
    		
    		//左边是checkbox
    		var checkbox = new NS.form.field.Checkbox({
    			boxLabel: mc,
				checked : sfky==1 ? true : false, //可用时置为选中
                inputValue : '1',
    			name : zdbs
    		});
    		items1.push(checkbox);
    		
    		//右边
    		var c, 
    			_disabled   = sfky==1 ? false : true, //是否disabled 常量
    			_allowBlank = false; //是否允许为空 常量
    		if(tjlx == 'textfield'){
    			c = new NS.form.field.BaseField({
    				disabled   : _disabled,
    				allowBlank : _allowBlank,
    				width : _width,
    				value : value,
    				name  : zdbsV
    			});
    		}else if(tjlx == 'numberfield'){
    			c = new NS.form.field.Number({
    				disabled   : _disabled,
    				allowBlank : _allowBlank,
    				minValue : 0,
    				width : _width,
    				value : value,
    				name  : zdbsV
    			});
    		}else if(tjlx == 'combobox'){
    			var valueList = o.valueList,
	    			c = new NS.form.field.ComboBox({
	    				disabled   : _disabled,
	    				allowBlank : _allowBlank,
	    				width : _width,
	    				name  : zdbsV,
	    				data  : valueList,
	    				value : sfky==1 ? value : '',
	    				editable  : false,
	    				emptyText : '请选择...',
	    				queryMode : 'local'
	    			});
    		}else if(tjlx == 'checkbox'){
    			c = new NS.Component({});
    		};
			items1.push(c);
			
    		checkbox.on('change', (function(c){
    			return function(scope, newValue, oldValue){
	    			if(!newValue || 0==newValue || '0'==newValue){
	    				return c.setDisabled(true);
	    			}else{
	    				return c.setDisabled(false);
	    			}
    			};
    		})(c), this);
    	}
		this.form = new NS.form.BasicForm({
	        border: false,
	        frame : false,
			layout: {
		        type: 'table',
		        columns: 2
		    },
		    style : {'margin':'10px 10px 0 20px'},
			items : items1
		});
    },
    /**
     * 初始化win
     */
    initWin : function(){
		var config = {
	    		title : '设置条件',
		        width : 320,
		        height: 300,
		        modal : true,
		        items : [this.form],
		        autoScroll : true,
		       	buttonAlign: 'right',
		       	bodyStyle : {'background':'#FFF'},
		       	buttons : [{text:'提交', name:'submit'}, {text:'取消', name:'cancel'}]
	       	};
		this.tjWin = new NS.window.Window(config);
		this.tjWin.bindItemsEvent({
            'submit' : {event:'click', fn:this.updateTjsz, scope:this},
            'cancel' : {event:'click', fn:this.cancelTjsz, scope:this}
        });
		this.tjWin.show();
    },
    
    /**
     * 更新类型条件参数
     */
    updateTjsz : function(){
    	var values = this.getValue();
    	if(values){
	    	this.XgAjax('updateJbtjByLxId',{id:this.id, values:values},function(data){
	    		if(data.success){
	    			NS.Msg.info('设置成功!');
	        		this.submit();
	    		}else{
	    			this.XgError();
	    		}
	    		this.cancelTjsz();
	    	},this);
    	}
    },
    /**
     * 关闭类型条件窗口
     */
    cancelTjsz : function(){
    	this.tjWin.close();
    },
    /**
     * 获取组件的值
     */
    getValue : function(){
    	if(this.form.isValid()){
    		var valueObj = this.form.getValues(), //form中所有的值
	    		regName	 = new RegExp('^'+this.nameFront), //checkbox的name 正则
    			obj 	 = {};
    		for(var tjlxName in valueObj){ //根据checkbox 的name组装数据
    			if(regName.test(tjlxName)){
    				var id		= tjlxName.substr(this.nameFront.length), //checkbox被选中的id
    					valueId = this.valueFront+id,
    					value	= valueObj[valueId] || 1;
    				obj[id] = value;
    			}
    		}
			return obj;
		}
    }
    
    
});