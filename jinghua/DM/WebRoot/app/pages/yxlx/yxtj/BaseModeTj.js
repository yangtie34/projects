/**
 * 迎新财务报表统计
 */
NS.define('Pages.yxlx.yxtj.BaseModeTj', {
	extend : 'Template.Page',
	entityName:'TbYxlxXsjbxx',
	htmlstr:'',//html路径
    init: function () {
      this.initData();
    },
	/**
	 * 初始化页面需要数据
	 */
	initData : function() {
		 this.callService({key:'queryYxPc'},function(data){
	        	this.initComponent(data);
	      });
	},
	/**
	 * 初始化组件
	 * @param {} tranData
	 * @param {} tabledata
	 */
	initComponent : function(data){
		
		this.initTbar(data.queryYxPc.data);//初始化tbar
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
		data:cdata
	});
	pcCombox.on('select',function(combox,records){
		this.queryContent();
	},this);
	var button = new NS.button.Button({
		text: '查询', 
		name: 'query',
		iconCls : 'page-search'	
	});
	button.on('click',function(){
			this.queryContent();
		},this);
	var basic = {
            items: [pcCombox,'-',button]
    };
    this.tbar = new NS.toolbar.Toolbar(basic);
	},
	/**
	 * 根据条件查询内容
	 */
	queryContent : function(){
		
		/***********制表日期******************/
        var myDate = new Date();
		var year = myDate.getFullYear();       //年
        var month = myDate.getMonth() + 1;     //月
        var day = myDate.getDate();            //日
		var sj = year+"-"+month+"-"+day;
		var values = this.tbar.getValues();
		
		this.callService({key:'queryContent',params:values},function(data){
	        	var Data = {};
	        	Data.pc = this.pcCombox.getRawValue();
	        	Data.data = data.queryContent.data;
	        	Data.sj = sj;
	        	Data.username = MainPage.getUserName();
	        	this.tpl.writeTo(this.component,Data);
	      });
	},
	
	/**
	 * 初始化页面
	 */
	initPage : function(tplData){
	  var component =this.component= new NS.Component({
	     	data : tplData,
           	tpl:this.tpl,
           	padding : 20,
           	autoScroll : true
	    });
	    
	    //添加监听事件   
		this.component.on('click',function(event,target){
		        //console.log(target);
		        var name = target.nodeName;
		        if(name == "INPUT" && target.type == "button"){
		        	if("yxtj_print"==target.name){
		        		this.createPage();//打印
		        	}else if("yxtj_export"==target.name){
		           		this.exportExcel();//导出
		        	}
			   }else if(name=='A'){
			       this.loadAndShowGridPanel2Win(target.id);
			   }
		},this);
		
		var viewlPanel= new NS.container.Panel({
			border : false,
			layout:'fit',
			tbar:this.tbar,
			items:component
        });
		
	   this.setPageComponent(viewlPanel);
	},
	/**
	 * 检查是否安装打印插件
	 */
	getLodop : function(oOBJECT,oEMBED){
		
		/**************************
		  本函数根据浏览器类型决定采用哪个对象作为控件实例：
		  IE系列、IE内核系列的浏览器采用oOBJECT，
		  其它浏览器(Firefox系列、Chrome系列、Opera系列、Safari系列等)采用oEMBED。
		**************************/
        var strHtml1="<br><font color='#FF00FF'>打印控件未安装!</font>";
        var strHtml2="<br><font color='#FF00FF'>打印控件需要升级!</font>";
        var strHtml3="<br><br><font color='#FF00FF'>(注：如曾安装过Lodop旧版附件npActiveXPLugin,请在【工具】->【附加组件】->【扩展】中先卸载它)</font>";
        var LODOP=oEMBED;		
	try{		     
	     if (navigator.appVersion.indexOf("MSIE")>=0) LODOP=oOBJECT;

	     if ((LODOP==null)) {
		 if (navigator.userAgent.indexOf('Firefox')>=0)
  	         document.documentElement.innerHTML=strHtml3+document.documentElement.innerHTML;
		 if (navigator.appVersion.indexOf("MSIE")>=0) document.write(strHtml1); else
		 document.documentElement.innerHTML=strHtml1+document.documentElement.innerHTML;
	     } else if (LODOP.VERSION<"6.0.5.6") {
		 if (navigator.appVersion.indexOf("MSIE")>=0) document.write(strHtml2); else
		 document.documentElement.innerHTML=strHtml2+document.documentElement.innerHTML; 
	     }
	     //*****如下空白位置适合调用统一功能:*********	     
			LODOP.SET_LICENSES("郑州索特信息技术有限公司","864677380837383919278901905623","","");

	     //*******************************************
	     return LODOP; 
	}catch(err){
	     document.documentElement.innerHTML="Error:"+strHtml1+document.documentElement.innerHTML;
	     return LODOP; 
	}
	
	},
	/**
	 * 打印配置
	 */
	createPage :function(){
		var LODOP=this.getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));  		
		LODOP.SET_PRINT_PAGESIZE(0,0,0,"A4");
		LODOP.add_print_htm(2,5,590,840,document.getElementById("yxtj_conent").innerHTML);
		LODOP.PRINT();//打印
		//LODOP.PREVIEW();//打印预览
	},
	tranCfg2Standard:function(cfg){//separative
		var array = cfg.split(",");
		return {
			id:array[0],
			cclx:array[1],
			zzztDm:array[2]
		};
	},
	loadAndShowGridPanel2Win:function(cfg){
		var gridCfg_ = this.gridCfg_ = this.tranCfg2Standard(cfg);
		
		gridCfg_.pcId=this.pcCombox.getValue();
		
		var gridParams =this.commonGridParams();
		
		NS.apply(gridParams,gridCfg_);
		//请求data第一次请求的数据
		if(this.xzWindow){//如果该窗口已经创建
			this.xzWindow.show();
			this.grid.load();
			return;
		}
		this.callService([{key:'queryBdztMd',params:gridParams}
		                 ,{key:'querTableHeader',params:{entityName:this.entityName}}],function(data){
			var tableData = this.tableData = NS.util.DataConverter.entitysToStandards(data['querTableHeader']);
	   	    var grid = this.grid = this.initGrid(tableData,data['queryBdztMd']);
	   	    grid.on('beforeload',function(grid_,params){
	   	         NS.apply(params,this.gridCfg_);
	   	    },this);
	   	    var tbar = this.initTbar2();
	   	    var xzWin = this.xzWindow =new NS.window.Window({
	   	    	autoShow:true,
	   	    	modal:true,
	   	    	width:800,
	   	    	height:400,
	   	    	closeAction:'hide',
	   	    	layout:'fit',
	   	    	tbar:tbar,
	   	    	items:[grid]
	   	    	//buttons:[{text:'关闭'}]
	   	    });
	   	    //xzWin.bindItems({});//hide();
		},this);
		
	},
	initTbar2:function(){
	//单字段查询
		 	var single = new NS.grid.query.SingleFieldQuery({
                data : this.tableData,
                grid : this.grid
            });
            
        var basic = {
            items: [
				single
            ]
        };
	   var tbar = new NS.toolbar.Toolbar(basic);

       return tbar;
	},
	/**
     * 初始化grid
     * @param {Array} columnData 表头（列）数据-这里是转换后的
     * @param {Array} gridData grid的表格的行数据
     * @param {Object} config 配置参数 用于覆盖默认配置
     * @return {NS.grid.Grid}
     */
    initGrid:function(columnData,gridData,config){
        var basic = {
        		plugins: [new NS.grid.plugin.HeaderQuery()],
                autoScroll: true,
                border:false,
                multiSelect: true,
                lineNumber: false,
        		columnData: columnData,
        		serviceKey:{
        			key:'queryBdztMd',
        			params:this.commonGridParams()
        		},
                modelConfig: {
                    data : gridData
                },
                proxy:this.model
        };
        if(config){
        	NS.apply(basic,config);
        }
        return new NS.grid.Grid(basic);
    },
    //统一的grid请求参数
    commonGridParams:function(cfg){
    	var basic = {
    			entityName:this.entityName,
    			start:0,
    			limit:25
    	};
    	if(cfg)NS.apply(basic,cfg);
    	if(this.gridCfg_)NS.apply(basic,this.gridCfg_);//20130816修复第一次显示grid的刷新问题
    	return basic;
    }
});