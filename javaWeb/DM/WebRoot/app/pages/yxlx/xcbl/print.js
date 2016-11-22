/**
 * 迎新办理-打印功能页面
 */
NS.define('Pages.yxlx.xcbl.print',{
	/**
	 * 打印tpl
	 * @param {} dgblData
	 */
	printTplData : function(dgblData){	
	
	if(!this.printWin){
		NS.loadFile('app/pages/yxlx/template/print.html', function(text) {
								
						this.printTpl = new NS.Template(text);
						
						this.initPrintWin(dgblData)
		
					},this);
					
	}else{
	
		this.printTpl.writeTo(this.printCom,dgblData);
			
		this.printWin.show();
		
		this.createPage();//打印
	}
		
	},
	
	
	
	/*
	 * 财务票据打印
	 */
	cwPrintTplData : function(falg){
		this.sflx = falg;//收费类型
	/***********制表日期******************/
        var myDate = new Date();
		var year = myDate.getFullYear();       //年
        var month = myDate.getMonth() + 1;     //月
        var day = myDate.getDate();            //日
		this.gnCwData.sj=this.sj = year+"-"+month+"-"+day;		
		this.gnCwData.username=this.username = MainPage.getUserName();
			var str = '';
			if(falg == "JF"){
			 str = 'app/pages/yxlx/template/cwPrint.html';
			}else if (falg == "TF"){
			 str = 'app/pages/yxlx/template/cwTfPrint.html';
			}
		
		NS.loadFile(str, function(text) {
								
						this.cwPrintTpl = new NS.Template(text);
						
						this.initCwPrintWin(this.gnCwData)
		
					},this);
		
		
	},
	
	
	/**
	 * 迎新单
	 * @param {} dgblData
	 */
	initPrintWin :function(dgblData){
	
	this.printCom = new NS.Component({
				   			
			data : dgblData,
				    
			tpl : this.printTpl
		})
		
		
		this.printWin = new NS.window.Window({
				
				title:'打印',
	           
				layout : 'fit',
				
	            border:false,
	            
	            closeAction:'hide',
	            
	            autoShow: true,
	            
	            modal:true,
	           	
	           	items:this.printCom
	            
	        });
	        
	      this.createPage();//打印
	
	},
	/**
	 * 收费明细tbar
	 * @param {} cdata
	 */
	initSfmxTbar : function(){
		var basic = {
			border:false,
            items: [
                {xtype: 'button', text: '保存', name: 'save'},
                {xtype: 'button', text: '取消', name: 'esc'}
            ]
        };
        var tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
             'save'   : {event: 'click', fn: this.sfmxSumbit, scope: this},
             'esc'   : {event: 'click', fn: this.sfmxWinHide, scope: this}
        });
	
	return tbar;
	},
	
	/**
	 * 财务票据打印
	 * @param {} dgblData
	 */
	initCwPrintWin :function(gnData){
		
		this.cwPrintCom = new NS.Component({
					   			
				data : gnData,
					    
				tpl : this.cwPrintTpl
			})
			
			
			this.cwPrintWin = new NS.window.Window({
					
					title:'财务信息明细',
		           
					layout : 'fit',
					
		            border:false,
		            
		            closeAction:'destroy',
		            
		            autoShow: true,
		            
		            modal:true,
		           	
		           	tbar:this.initSfmxTbar(),
		           	
		           	resizable : false,
		           
		           	items:this.cwPrintCom
		            
		        });
			
	/*	this.cwPrintWin.on('click',function(event,target){  
		     if(target.nodeName == "INPUT" && target.type == "text"){
		     	var sfmx = gnData.sfmx
		     	for(var i=0;i<sfmx.length;i++){
		     	//实收金额
		     	var ssjeName = "yxlx_ssje"+sfmx[i].sfxmId;
		     	var hjjeName = "yxlx_hjje"+sfmx[i].sfxmId;
		        if(ssjeName==target.name){
		        	//实收金额
		        	var ssje = document.getElementById(ssjeName);
		        	//缓缴金额
		        	var hjjeValue= document.getElementById(hjjeName).value;
		        	ssje.value = sfmx[i].ysje-hjjeValue;
		        
		        	//缓缴金额	
		        }else if(hjjeName==target.name){
		        	
		        	var hjje= document.getElementById(hjjeName);
		        	
		        	var ssjeValue = document.getElementById(ssjeName).value;
		        	
		        	hjje.value =  sfmx[i].ysje-ssjeValue;
		        }
		        
 			}	
 				var zssje = 0;
 				var zhjje=0;
 				for(var i=0;i<sfmx.length;i++){
 					var ssjeName = "yxlx_ssje"+sfmx[i].sfxmId;
		     		var hjjeName = "yxlx_hjje"+sfmx[i].sfxmId;
 			   	 	var ssjeValue = document.getElementById(ssjeName).value;
 			   	 	var hjjeValue= document.getElementById(hjjeName).value;
 			   	 	 zssje = zssje + Number(ssjeValue);
 			   	 	 zhjje = zhjje + Number(hjjeValue);
 			   	 
 				}
 					document.getElementById("yxlx_zssje").innerHTML=zssje;
		        	document.getElementById("yxlx_zhjje").innerHTML=zhjje;
		       }
		})*/

/*			//文本框焦点失去事件
			var me = this;
			var sfmx = this.gnCwData.sfmx
			var sfzh = document.getElementById("CardNo");
			
			for(var i=0;i<sfmx.length;i++){
			
			if(this.sflx=="JF"){
		 		var ssjeName = "yxlx_ssje"+sfmx[i].sfxmId;
				var hjjeName = "yxlx_hjje"+sfmx[i].sfxmId;
		 		var ssje = document.getElementById(ssjeName);
		 		var hjje= document.getElementById(hjjeName); 
		 		ssje.onblur = function(){
		 			me.getSfHjData();
		 		}
		 		hjje.onblur = function(){
		 			me.getSfHjData();
		 		}
			}else if(this.sflx=="TF"){
				var tfjeName = "yxlx_tfje"+sfmx[i].sfxmId;
		 		var tfje = document.getElementById(tfjeName);
		 		tfje.onblur = function(){
		 		me.getSfHjData();	
		 		}
	 	}
				
	}	*/
	
	this.cwPrintCom.on('keyup',function(event,target){
		this.getSfHjData();	
	},this);
	
	},
	/**
	 * 缴费明细窗口隐藏
	 */
	sfmxWinHide:function(){
		this.cwPrintWin.close();
	},
	/**
	 * 缴费明细窗口提交
	 */
	sfmxSumbit : function(){
		var sfmx = this.gnCwData.sfmx;
		var params = {};
		params.xsId =  this.xsId;
		params.sfbz = document.getElementById("yxlx_sfbz").value;
		var sfmxData=[];
		for(var i=0;i<sfmx.length;i++){
			var p = {};
			p.sfxmId = sfmx[i].sfxmId;
			if(this.sflx=="JF"){
		 		var ssjeName = "yxlx_ssje"+sfmx[i].sfxmId;
				var hjjeName = "yxlx_hjje"+sfmx[i].sfxmId;
		 		var ssjeValue = Number(document.getElementById(ssjeName).value);
		 		var hjjeValue= Number(document.getElementById(hjjeName).value); 
		 		p.ssje = ssjeValue;
		 		p.hjje = hjjeValue;
			}else if(this.sflx=="TF"){
				var tfjeName = "yxlx_tfje"+sfmx[i].sfxmId;
		 		var tfjeValue = Number(document.getElementById(tfjeName).value);
	 			p.tfje = tfjeValue;
			}
	 		sfmxData.push(p);
	 	}
	 	params.sfmxData=sfmxData;
	 	params.lx = this.sflx
		this.callService({
						key:'saveXsCwMxInfo',
						params:params
						},
						function(data){
							if(data.saveXsCwMxInfo.success){
							 	if(this.sflx=="JF"){
							 		this.blSubmit(1);
							 	}else if(this.sflx="TF"){
							 		this.window.hide();
							 	}
							 	
						   	}else{
								NS.Msg.error('办理失败！');
								this.sfmxWinHide();
						    } 
						});	
			
	},
	/**
	 * 获取收费合计的数据
	 */
	getSfHjData :function(){
		
		var sfmx = this.gnCwData.sfmx;
		var ssjeHj = 0;
 		var hjjeHj=0;
 		var tfjeHj=0;
			for(var i=0;i<sfmx.length;i++){
			if(this.sflx=="JF"){
		 		var ssjeName = "yxlx_ssje"+sfmx[i].sfxmId;
				var hjjeName = "yxlx_hjje"+sfmx[i].sfxmId;
		 		var ssjeValue = parseInt(document.getElementById(ssjeName).value);
		 		
		 		var hjjeValue= parseInt(document.getElementById(hjjeName).value); 
		 		ssjeHj = ssjeHj+ssjeValue;
		 		hjjeHj = hjjeHj+hjjeValue;
			
			}else if(this.sflx=="TF"){
				
				var tfjeName = "yxlx_tfje"+sfmx[i].sfxmId;
		 		var tfjeValue = Number(document.getElementById(tfjeName).value);
	 			tfjeHj = tfjeHj+tfjeValue;
			}

		}
		
		if(this.sflx=="JF"){
			document.getElementById("yxlx_zssjeHj").innerHTML=ssjeHj;
			
			document.getElementById("yxlx_zhjje").innerHTML=hjjeHj;
		}else if(this.sflx=="TF"){
			document.getElementById("yxlx_ztfje").innerHTML=tfjeHj;
		}
	
	},
	//加载财务打印数据
	loadCwPageData : function(){
		
		this.callService([
			{key : 'queryHjblByXs', params : {xsId:this.xsId,hjId:this.dqhjId}},
			{key : 'queryGnByHj',params:{xsId:this.xsId,hjId:this.dqhjId}}	
			]
	        ,function(data){
	          	 
	          	var gn = data.queryGnByHj.data;
				var gnCwdata = {};
				for(var i=0;i<gn.length;i++){
					var gndm = gn[i].gndm;
					if(gndm==2 || gndm==1){
						gnCwdata = data.queryGnByHj.data[i];
						gnCwdata.sfbz = data.queryGnByHj.data[i].sfmx[0].sfbz;
						gnCwdata.xm = this.xm;
						gnCwdata.bjmc = this.bjmc;
						gnCwdata.hjzt = data.queryHjblByXs.data[0].hjztdm;
					}
				}
				gnCwdata.sj = this.sj;
				gnCwdata.username = this.username;
				this.cwPrintTpl.writeTo(this.cwPrintCom,gnCwdata);
				this.createCwPage();
	       
	        });
	
	
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
        var LODOP=oEMBED;
	try{		     
	     if (navigator.appVersion.indexOf("MSIE")>=0) LODOP=oOBJECT;

	     if ((LODOP==null) ||(typeof(LODOP.VERSION)=="undefined")) {
                     if(confirm('打印控件未安装,是否安装?安装完成后，请重新重启浏览器！')){
                         window.location.href='lodop/install_lodop.exe';
                     }
            // NS.Msg.info("打印控件未安装!"+"<a href=''>执行打印插件安装</a>"+"安装完成后，请重新启动浏览器！");
             }
	     //*****如下空白位置适合调用统一功能:*********	     
			LODOP.SET_LICENSES("郑州索特信息技术有限公司","864677380837383919278901905623","","");

	     //*******************************************
	     return LODOP; 
	}catch(err){
	     return null;
	}
	
	},
	
	
	/**
	 * 打印配置
	 */
	createPage :function(){
		
		var str = "<img border='1' src='http://www.hnhgjx.cn:1234/MOSDC/images/student/twoCode/"+this.sfzh+".png'/>";
		this.printWin.hide();//打印窗口隐藏
        var LODOP=this.getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));
        if(LODOP!=null){
            LODOP.SET_PRINT_PAGESIZE(0,800,1150,"");
            LODOP.ADD_PRINT_HTM(0,100,80,35,document.getElementById("yxdy_title").innerHTML);
            LODOP.ADD_PRINT_IMAGE(45,70,200,200,str);
            LODOP.add_print_htm(195,5,600,850,document.getElementById("yxdy_conent").innerHTML);
            LODOP.PRINT();//打印
           //LODOP.PREVIEW();//打印预览
       }

	},
	/**
	 * 财务打印配置
	 */
	createCwPage : function(){
		var LODOP=this.getLodop(document.getElementById('LODOP'),document.getElementById('LODOP_EM'));  		
		LODOP.SET_PRINT_PAGESIZE(0,1220,1450,"");
		if(this.sflx=="TF"){
			LODOP.add_print_htm(5,0,800,1100,document.getElementById("yxcwTfdy_conent").innerHTML);
		}else if(this.sflx=="JF"){
			LODOP.add_print_htm(5,0,800,1100,document.getElementById("yxcwdy_conent").innerHTML);
		}
		 LODOP.PRINT();//打印
		//LODOP.PREVIEW();//打印预览
		this.sfmxWinHide();
	
	}
	
	
});