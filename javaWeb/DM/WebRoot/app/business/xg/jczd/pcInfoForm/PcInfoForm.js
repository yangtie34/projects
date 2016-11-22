/**
 * 学工-奖惩助贷-[批次详情form]
 * 组件 model
 */
NS.define('Business.xg.jczd.pcInfoForm.PcInfoForm',{
	extend : 'Template.Page',
    
    mixins : ['Pages.xg.Util'],
    
    requires:['Business.component.PageSubtitle'],
    
    tplRequires : [{fieldname:'pcTpl', 	 path:'app/business/xg/jczd/pcInfoForm/template/pc.html'},
                   {fieldname:'lxTpl',	 path:'app/business/xg/jczd/pcInfoForm/template/lx.html'},
                   {fieldname:'xsfwTpl', path:'app/business/xg/jczd/pcInfoForm/template/xsfw.html'}],
	cssRequires : ['app/business/xg/jczd/pcInfoForm/style/pcinfoForm.css'],
	
	modelConfig:{
		serviceConfig:{
          	//查询批次详情
			queryJzPcInfo : 'jzPcszService?queryJzPcInfo',
		}
	},
	show : function(pcId, sslx){
		if(this.tplready){
    		this.initFirst(pcId, sslx);
    	}else{
    		this.on('tplready', function(){
    			this.initFirst(pcId, sslx);
    		},this);
    	}
	},
	
	initFirst : function(pcId, sslx){
		this.initConstant();
		this.XgAjax('queryJzPcInfo', {id:pcId, sslx:sslx}, function(data){
			if(data.success){
				this.initData(data);
				this.initTpl();
				this.initComponent();
			}else{
				this.XgError();
			}
		},this);
	},

	initConstant : function(){
		//组件唯一标示ID前缀
    	this.idF  		= NS.id()+'-pcInfo_';
    	this.closeBtnId = this.idF+'closeBtn';
	},
	
	initData : function(data){
		this.pcTplData   = data.pc;
		this.lxTplData   = data.lx;
		this.xsfwTplData = {
			closeBtn : this.closeBtnId,
			length	 : data.xsfw.length,
			xsfw	 : data.xsfw
		};
	},
	
	initTpl : function(){
		this.pcTplCmp = new NS.Component({
			border : false,
			data   : this.pcTplData,
			tpl	   : this.pcTpl
		});
		this.lxTplCmp = new NS.Component({
			border : false,
			data   : this.lxTplData,
			tpl	   : this.lxTpl
		});
		this.xsfwTplCmp = new NS.Component({
			border : false,
			data   : this.xsfwTplData,
			tpl	   : this.xsfwTpl
		});
		this.xsfwTplCmp.on('click', function(event, target){
			if(target.id == this.closeBtnId){
				this.win.close();
			}
		},this);
	},
	
	initComponent : function(){
		//win 内容器组件
    	this.ctn = new NS.container.Container({
			style : {padding:'20px'},
			items : [Business.component.PageSubtitle.getInstance('批次信息'),
			         this.pcTplCmp, 
			         Business.component.PageSubtitle.getInstance('类型列表'),
			         this.lxTplCmp, 
			         Business.component.PageSubtitle.getInstance('学生范围'),
			         this.xsfwTplCmp]
		});
    	this.win = new NS.window.Window({ //win
			width : 500,
			height: 470,
			modal : true,
			items : this.ctn,
			autoScroll : true
		});
		this.win.show();
	}
	
});