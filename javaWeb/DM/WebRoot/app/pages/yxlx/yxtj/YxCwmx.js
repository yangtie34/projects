/**
 * 迎新财务报表统计
 */
NS.define('Pages.yxlx.yxtj.YxCwmx', {
	extend : 'Pages.yxlx.yxtj.BaseModeTj',
	
	htmlstr :'app/pages/yxlx/template/count/cwmx.html',
	
	modelConfig : {
	serviceConfig : {
			//查询迎新批次
			'queryYxPc' : 'yxlx_queryYxPc',
			'queryContent' :'yxlx_queryCwmxByRq',//查询财务明细统计
			'queryCwsfxm':'yxlx_queryCwsfxm',
			'queryCwsfrq':'yxlx_queryCwsfrq'
			}
	
	},
	initData : function() {
		 this.callService([
		 {key:'queryYxPc'},
		 {key:'queryCwsfrq'}
	        ]
	        ,function(data){
	        	this.initComponent(data);
	      });
		
	},
	initComponent : function(data){
		
		this.initTbar(data);//初始化tbar
    	
    	NS.loadFile(this.htmlstr, function(text) {
							
							this.tpl = new NS.Template(text);
							 
							 var tplData=' ';
							 
							 this.initPage(tplData);//初始化页面
						
						}, this);
	
	},

	
	/**
	 * 初始化tbar
	 */
	initTbar : function(cdata){
		
		var pcCombox = this.pcCombox = new NS.form.field.ComboBox({
			fieldLabel : '批次',
			labelWidth :40,
			name :'pcId',
			fields:['ID','MC'],
			displayField : 'MC',
			valueField  : 'ID',
			data:cdata.queryYxPc.data
		})
		var sfrqCombox = this.sfrqCombox = new NS.form.field.ComboBox({
			fieldLabel : '收费日期',
			labelWidth :60,
			name :'sfrq',
			fields:['ID','SFRQ'],
			displayField : 'SFRQ',
			valueField  : 'ID',
			data:cdata.queryCwsfrq.data
		})
	var button = new NS.button.Button({
		text: '查询', 
		name: 'query',
		iconCls : 'page-search'
		
	})
	
	button.on('click',function(){
			this.queryContent();
		},this)
		
		var basic = {
            items: [
            	pcCombox,
            	sfrqCombox,
            	'-',
                button
                
            ]
        };
        this.tbar = new NS.toolbar.Toolbar(basic);
 
	},
	
	queryContent : function(){
		
		/***********制表日期******************/
        var myDate = new Date();
		var year = myDate.getFullYear();       //年
        var month = myDate.getMonth() + 1;     //月
        var day = myDate.getDate();            //日
		var sj = year+"-"+month+"-"+day;
		var values = this.tbar.getValues();
		if(values.pcId==null){
			NS.Msg.info('请选择批次！');
			return;
		}
		if(values.sfrq==null){
			NS.Msg.info('请选择日期！');
			return;
		}
		values.lx="cx";
		this.callService([
		 {key:'queryContent',params:values}
	        ]
	        ,function(data){
	        	var Data = {};
	        	Data.pc = this.pcCombox.getRawValue();
	        	Data.sfrq = this.sfrqCombox.getRawValue();
	        	Data.data = data.queryContent.data[1];
	        	Data.zdata = data.queryContent.data[0];
	        	Data.sj = sj;
	        	Data.username = MainPage.getUserName();
	        	this.tpl.writeTo(this.component,Data);
	      })
	      
	   
	
	},
	 exportExcel : function(){
	 	var values = this.tbar.getValues();
	 	
		if(values.pcId==null){
			NS.Msg.info('请选择批次！');
			return;
		}
		if(values.sfrq==null){
			NS.Msg.info('请选择日期！');
			return;
		}
	    NS.entityExcelExport({
            queryParams: values,
            entityName : 'VYxCwmx',
            title :'缴费情况明细' ,
            serviceKey : 'queryContent',
            controller : this
        });
	    }
})