/**
 * 迎新院系报到报表统计
 */
NS.define('Pages.yxlx.yxtj.YxLstdMd', {
	extend : 'Template.Page',
	modelConfig : {
	serviceConfig : {
			//查询迎新批次
			'queryYxPc' : 'yxlx_queryYxPc',
			'queryCascadeQuery':'yxlx_queryCascadeQuery',//院系专业班级级联
			'queryContent' :'yxlx_queryLstdMd'//查询绿色通道办理人员名单
			}
	
	},
	
	  init: function () {
      this.initData();
    },
	/**
	 * 初始化页面需要数据
	 */
	initData : function() {
		 this.callService([
		 {key:'queryYxPc'},
		 {key:'queryCascadeQuery'}
	        ]
	        ,function(data){
	        	this.initComponent(data);
	      })
		
	},
	/**
	 * 初始化组件
	 * @param {} tranData
	 * @param {} tabledata
	 */
	initComponent : function(data){
		
		this.initTbar(data);//初始化tbar
    	
    	NS.loadFile("app/pages/yxlx/template/count/lstdMd.html", function(text) {
							
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
		
			
	var button = new NS.button.Button({
		text: '查询', 
		name: 'query',
		iconCls : 'page-search'
		
	})
		button.on('click',function(){
			this.queryContent();
		},this)
		
	
	var obj = {
        holistic:true,
        cascadeType: [
            {
                cclx: "YX",
                labelName: "院系"
            },
            {
                cclx: "ZY",
                labelName: "专业"
            },{
                cclx: "BJ",
                labelName: "班级"
            }
        ],// 默认不给层次类型的话，系统自动计算层数。
        data: cdata.queryCascadeQuery,
        width:500,
        height:22,
        border: false
    };
    var cascadeQuery =this.casquery = new NS.appExpand.CascadeQuery(obj);
    
    var basic = {
        items: [
            pcCombox,
            '-',
            cascadeQuery,
            button
        ]
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
		
		this.callService([
		 {key:'queryContent',params:values}
	        ]
	        ,function(data){
	        	var Data = {};
	        	Data.pc = this.pcCombox.getRawValue();
	        	Data.data = data.queryContent.data;
	        	Data.sj = sj;
	        	Data.username = MainPage.getUserName();
	        	this.tpl.writeTo(this.component,Data);
	      })
	
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
		        
		        if(target.nodeName == "INPUT" && target.type == "button"){
		        	//打印
		        	if("yxtj_print"==target.name){
		        		
		        		this.createPage();//打印
		        		
					//导出	        	
		        	}else if("yxtj_export"==target.name){
		           		
		           		this.exportExcel();
		           	
		        	}
		      }
		},this);
		
		
		var viewlPanel= new NS.container.Panel({
			border : false,
			layout:'fit',
			tbar:this.tbar,
			items:component
			
        })
		
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
	}
})