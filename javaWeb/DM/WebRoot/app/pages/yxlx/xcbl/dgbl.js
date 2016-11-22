/**
 * 迎新办理-单个办理功能页面
 */
NS.define('Pages.yxlx.xcbl.dgbl',{
	
	
	/******************显示单个办理窗口 先请求数据**********/
	showDgblWin : function(queryStr){
		this.callService({
					key : 'queryXsxx',
					params : {queryStr:queryStr,pcId:this.pcId,dqhjId:this.dqhjId}
					},function(data){
						this.processXsxx(data.queryXsxx,queryStr,1);
			         });
		},
	
	/******************显示单个办理窗口 处理返回数据**********/
	processXsxx : function(Data,queryStr,f){
		this.callService(
		 	{key : 'queryYxPc',params:{start : 0,limit : 25}}
	        ,function(data){
	        var num  = data.queryYxPc.count;
			if(num == 0){
		 	    this.page.removeAll();
		 		var com = new NS.Component({html:"<h2>当前没有可以使用的批次！</h2>"});
				this.page.add(com);
			 }else{
				this.processXsxx1(Data,queryStr,f);	
		 	}
		});
		
	},
	processXsxx1 :function(data,queryStr,f){
		       /********文本框查询****/
		if(f==1 && data.count==1){
					
			this.getBlztData(data.data[0]);
					
			//this.grid.load({queryStr:queryStr,pcId:this.pcId,dqhjId:this.dqhjId});//重新加载grid
					
		}else if(f==1 && data.count>1){//查询学生人数大于1
					
			this.grid.load({queryStr:queryStr,pcId:this.pcId,dqhjId:this.dqhjId});
					
		}else if(f==0 && data!=null){/********直接点击[进入办理]gird****/
					
			this.getBlztData(data);
						
			//this.grid.load({queryStr:queryStr,pcId:this.pcId,dqhjId:this.dqhjId});//重新加载grid
				
		}
	
	},
	
	
	/*****************单个学生环节办理状态**********/
	getBlztData : function(Data){
		
		var xsId=this.xsId=Data.xsId;//学生id
		
		this.xbId = Data.xbid;//性别id
		
		var dqhjId=this.dqhjId = Data.hjId;//当前环节id
		
		var pcId=Data.pcId;//批次id
		
		var hjId = Data.hjId;//环节id
		
		
	this.callService([
			{key : 'queryHjblByXs', params : {xsId:xsId,hjId:dqhjId}},
			{key : 'queryBlqkByXs',params:{id:xsId,pcId:pcId}},
			{key : 'queryGnByHj',params:{xsId:xsId,hjId:hjId}}
	        ]
	        ,function(data){
	          	 this.processHjbl(data,Data);
	        });
	
	},
	
	/*****************处理环节办理状态数据**********/
	processHjbl : function(data,Data){
		
		var dgblData={};
		
		dgblData.xsData = Data;//学生信息
		
		this.sfzh = Data.sfzh;
		
		this.xm = Data.xm;
		
		this.bjmc = Data.bjmc;
		
		dgblData.blqkData = data.queryBlqkByXs.data;//办理情况
	
		dgblData.hjblData =data.queryHjblByXs.data[0];//环节办理
		
		dgblData.gnData = data.queryGnByHj.data; //功能
			
		var gn = data.queryGnByHj.data;
		
		for(var i=0;i<gn.length;i++){
			
			var gndm = gn[i].gndm;
			
			if(gndm==2 || gndm==1){
			
				this.gnCwData = data.queryGnByHj.data[i];
				
				this.gnCwData.sfbz = data.queryGnByHj.data[i].sfmx[0].sfbz;
				
				this.gnCwData.xm = Data.xm;
				
				this.gnCwData.bjmc = Data.bjmc;
				
				this.gnCwData.hjzt = data.queryHjblByXs.data[0].hjztdm;
				
			
			}else if(gndm==3 || gndm==4){
			
				this.gnDormData = data.queryGnByHj.data[i];
				
				this.gnDormData.wbqzhj=dgblData.hjblData.wbqzhj;
			}else if(gndm==5){
				this.hkxzselectdata=data.queryGnByHj.data[i].hkxzselectdata;
			}
		}
		
		this.zysje = this.gnCwData.zysje;//总应收
		
		this.initDgblTpl(dgblData);
		
	},
	
	/****单个办理html请求********/
	initDgblTpl :function(dgblData){
		
		if(!this.dgblTpl){
		
		NS.loadFile('app/pages/yxlx/template/dgbl.html', function(text) {
							
					this.dgblTpl = new NS.Template(text);
				
					this.initDgblWin(dgblData);//初始化单个办理页面
						
						}, this);
						
		}else{

		this.initDgblWin(dgblData);//初始化单个办理页面+
		
		}
	
	},
	
	
	
	/*****************初始化办理窗口**********/
	initDgblWin: function(dgblData){
		var me = this;
			me.dgblData = dgblData;
		if(!this.window){
		
		var tplCom = this.tplCom = new NS.Component({
		  
		  	data : dgblData,
		    
		    tpl:this.dgblTpl
		});
			
		
	 this.window = new NS.window.Window({
			
			title:'单个办理',
           
			layout : 'fit',
			
			closeAction:'hide',
			
			heigth:860,
	            
	        width:720,
            
            border:false,
            
            autoShow: true,
            
            modal:true,
            
            items:tplCom
        });
        
             this.tplCom.on('click',function(event,target){
             	var dgblData = me.dgblData;
		        if(target.nodeName == "INPUT" && target.type == "button"){
		          //确认办理
		        	if("yxlx_qrbl"==target.name){
		        		
		        		if(this.dqhjMc=="注册分班"){
		        			
		        			this.printTplData(dgblData);
		        			
		        			this.blSubmit(1);
		        		
		        		
		        		}else if(this.dqhjMc=="财务缴费"){
		        			
		        			this.blSubmit(1);
		        		
		        		}else if(this.dqhjMc=="绿色通道"){
			        		
			        		this.cwSubmit();
			        	
			        	
			        	}else if(this.dqhjMc=="宿舍分配"){
		        			
		        			//this.dormSubmit();
		        			
		        		}else if(this.dqhjMc=="费用审查"){
		        			this.dormSubmit();
		        			
		        			
		        		}else{
		        			this.blSubmit(1);
		        		}
		           // 撤销办理		
		           }else if("yxlx_cxbl"==target.name){
		           	
		           		this.blSubmit(0);
		          
		           }else if("yxlx_dy"==target.name){
		           	
		           		this.printTplData(dgblData);
		           //宿舍分配
		           }else if("yxlx_fbss"==target.name){
		           		//初始树数据
		           		this.initTreeData();
		           	//置空宿舍
		           }else if("yxlx_zkss"==target.name){
		           	
		           	this.clearDormInfo();
		           //缴费明细
		           }else if ("yx_jfmx"==target.name){
		           			
		           			this.cwPrintTplData('JF');
		           //退费明细
		           }else if("yx_tfmx"==target.name){
		           			
		           			this.cwPrintTplData('TF');
		           //招录信息修改
		           }else if("yxlx_xgxx"==target.name){
		           			//现场招录修改
			           		this.initXczlData('U');
		           }
		      
		        }else if(target.nodeName == "INPUT" && target.type == "text"){
		        //实收金额
		        if("yxlx_inputssje"==target.name){
		        	//实收金额
		        	var zssje = document.getElementById("yxlx_inputssje");
		        	//缓缴金额
		        	var zhjjeValue= document.getElementById("yxlx_inputhjje").value;
		        	
		        	zssje.value = this.zysje-zhjjeValue;
		        //缓缴金额	
		        }else if("yxlx_inputhjje"==target.name){
		        	//实收金额
		        	var zhjje= document.getElementById("yxlx_inputhjje");
		        	//缓缴金额
		        	var zssjeValue = document.getElementById("yxlx_inputssje").value;
		        	
		        	zhjje.value = this.zysje-zssjeValue;
		        
		        }
		        
		        }
		},this);
		
		//没有未办前置环节and窗口没有隐藏-加回车监听事件
		if(dgblData.xsData.wbqzhj==null && !this.window.isHidden()){
			//回车办理事件
			this.window.on('keydown',function(event,target){
			    if(event.getKey() == 13){ 
			 		var qrbl = document.getElementById("yxlx_qrbl");
			 		var cxbl = document.getElementById("yxlx_cxbl");
			        if(qrbl != null){
			        		if(this.dqhjMc=="注册分班"){
			        		
			        			this.printTplData(dgblData);
			        			this.blSubmit(1);
			        		
			        		}else if(this.dqhjMc=="财务缴费"){
			        			
			        			this.blSubmit(1);
			        			//this.cwSubmit();
			        		
			        		}else if(this.dqhjMc=="宿舍分配"){
			        			
			        			//this.dormSubmit();
			        			
			        		
			        		}else if(this.dqhjMc=="绿色通道"){
			        			
			        			//this.blSubmit(1);
			        			
			        			this.cwSubmit();
			        		
			        		}else if(this.dqhjMc=="费用审查"){
		        			this.dormSubmit();
		        			
		        			
		        			}else{
			        			this.blSubmit(1);
			        		}
			        	
			        }else if(cxbl != null) {
			        	
			        	this.blSubmit(0);
			       }
			      }
			},this);
			
		}
        
		}else{
		
			this.dgblTpl.writeTo(this.tplCom,dgblData);
				
			this.window.show();
		
		
		}
	//查询框内容置为空
     document.getElementById("yxlx_querystr").value="";  
	 var s = document.getElementById('yxlx_sfmjxf');
	 if(s!=null){
		s.value = this.hkxzselectdata;
	 }
     
	},
	/********按钮单击响应*************/
	blSubmit : function(data){
		this.callService({
				key:'updateHjzt',
				params:{hjId:this.dqhjId,hjztDm:data,xsId:this.xsId,pcId:this.pcId}
				},
				function(data){
					if(data.updateHjzt.success){
					 	this.window.hide();
					 	 var msg= NS.Msg.show({
					 		title:'提示',
					 		msg:'办理成功！',
					 		buttons:NS.Msg.OK
						 });	
					 	setTimeout(function(){
					 	if(!msg.isHidden()){msg.hide();
					 		 var yxlx_querystr=document.getElementById("yxlx_querystr");  
     	 						 yxlx_querystr.value="";  
      	 						 yxlx_querystr.focus();
					 	}
					 	},500);
					 	this.loadXcblData();
					 	if(this.dqhjMc=="财务缴费"){
					 		this.loadCwPageData();
					 	}
				   	}else{
						NS.Msg.error('办理失败！');
				    } 
				});	
				
	
	
	},
	/**
	 * 财务缴费提交
	 */
	cwSubmit : function(){
	var zssje = parseInt(document.getElementById('yxlx_inputssje').value);
	var zhjje = parseInt(document.getElementById('yxlx_inputhjje').value);
	
	if(this.zysje == zssje+zhjje){
		
		this.callService({
					key:'saveXsCwInfo',
					params:{xsId:this.xsId,zssje:zssje,zhjje:zhjje}
					},
					function(data){
						if(data.saveXsCwInfo.success){
						 	this.blSubmit(1);
					   	}else{
							NS.Msg.error('办理失败！');
					    } 
					});	
	}else{
	
		NS.MessageBox.confirm(
		'提示',
		'财务信息有误，是否继续办理！',
		function(btn) {
			if (btn == 'yes') {
			  	
			this.callService({
				key:'saveXsCwInfo',
				params:{xsId:this.xsId,zssje:zssje,zhjje:zhjje}
				},
				function(data){
					if(data.saveXsCwInfo.success){
					 	this.blSubmit(1);
				   	}else{
						NS.Msg.error('办理失败！');
				    } 
				});
				
			}
		},this);
	}
	},
	
	/**
	 * 是否免缴学费提交
	 */
	fyscSubmit : function(){
			var falg = document.getElementById('yxlx_sfmjxf').value;
			this.callService({
						key:'updateXsCwMxInfo',
						params:{xsId:this.xsId,hkxzId:falg}
						},
						function(data){
							if(data.updateXsCwMxInfo.success){
							 	this.blSubmit(1);
						   	}else{
								NS.Msg.error('学生免学费出错！');
						    } 
						});
	
	},
	
	
	/**
	 * 宿舍分配提交
	 */
	dormSubmit : function(){
	var ids = this.gnDormData.zyid;
	
	if(ids!=null){
		this.callService({
					key:'updateDormFp',
					params:{ids:ids,lx:'QR'}
					},
					function(data){
						if(data.updateDormFp.success){
							this.fyscSubmit();
							//this.blSubmit(1);
					   	}else{
							NS.Msg.error('办理失败！');
					    } 
					    
					});	
	}else{
		NS.MessageBox.confirm(
		'提示',
		'宿舍信息未分配，是否继续办理！',
		function(btn) {
			if (btn == 'yes') {
			  this.fyscSubmit();
			  //this.blSubmit(1);
			}
		},this)
	}
	},
	
	/**********宿舍重新分配处理start***********************/
		
	/**
	 * 获取树的数据
	**/
	initTreeData : function(){
		
		this.callService({
	            key : 'queryTree',
	            params:{cwzt:'0',xbId:this.xbId}
	        },function(data){
	        	this.initTree(data.queryTree);
	      })
	  
	},
	
		/***
	 * 显示宿舍树
	 * @param {} tdata
	 */
	initTree : function(treeData){
		
		var obj = {
					treeData:treeData,
					border : false,
					rootVisible: false,
					filter:false,
					margin : '0 0 0 0'
					
		};
		this.tree = new NS.container.Tree(obj);
		this.tree.addListener('itemclick',function(com,data,item){
			if(data.leaf){
				this.getcwId(data.id);
			}
 		},this);
 		
 		this.initWin();
		
	},
	//得到选择床位id
	getcwId : function(id){
		this.selectcwId=id;
	},
	//现场宿舍分房
	initWin : function(){	
		
		var basic = {
            items: [
                {xtype: 'button', text: '保存', name: 'save'},
                {xtype: 'button', text: '取消', name: 'cancel'}
            ]
        };
        var tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
             'save'   : {event: 'click', fn: this.submitSave, scope: this},
             'cancel'   : {event: 'click', fn: this.buttonCancel, scope: this}
        });
		var window  = this.ffwindow = new NS.window.Window({
			title:'重新分房',
            width : 300,
            height : 400,
            layout : 'fit',
            tbar:tbar,
            items : this.tree,
            modal:true,
            border:false,
            autoShow: true
        });
	},
	//关闭win
	buttonCancel : function(){
		this.ffwindow.close();
	},
	/**
	 * 响应保存按钮事件
	 */
	submitSave:function(){
	if(this.selectcwId !=null){
	this.callService({
	             key : 'saveDormInfo',
	             params : {cwId:this.selectcwId,xsId:this.xsId}
	        },function(data){
	        	if(data.saveDormInfo.success){
			 		 this.buttonCancel();
			 		 this.window.hide();
			 		 //NS.Msg.info('重新分配成功！');
			 		 //this.blSubmit(1);
			 		 this.showDgblWin(this.xsId);
			 		 
		    	   }else{
					NS.Msg.error('重新分配失败！');
				   } 
	        },this);
	        
	}else{
		NS.Msg.error('请选择床位！');
	}
	},
	/**********宿舍重新分配处理end***********************/
	
	
	/*********刷新数据-请求数据***************************/
	loadXcblData : function(){
		
		this.callService(
		 {key : 'queryYxPc'}
	        ,function(data){
	        var num  = data.queryYxPc.count;
			if(num == 0){
		 	    this.page.removeAll();
		 		var com = new NS.Component({html:"<h2>当前没有可以使用的批次！</h2>"});
				this.page.add(com);
			 }else{
			 	this.loadXcblData1();
			 
			 }
	   })
	},
	/*********请求数据2***************************/
	loadXcblData1 : function(){		
	 this.callService([
				 	
		{key : 'queryDqhj',params:{pcId:this.pcId}},
				 
		{key : 'queryHjtj',params:{pcId:this.pcId,dqhjId:this.dqhjId,start : 0,limit : 25}},
				 	
		{key : 'queryBltj',params:{pcId:this.pcId,dqhjId:this.dqhjId}}
			        
		 ]
		 ,function(data){
			        	
		this.loadData(data);
	 })
		
	},
	
	
	
	/**
	 * 处理返回数据
	 * @param {} data
	 */
	loadData : function(data){
		
		var xcblData = {};
		//迎新批次
		xcblData.yxPcData = this.pcData;
		//环节统计
		xcblData.hjtjData = data.queryHjtj.data;
		//办理统计
		xcblData.bltjData = data.queryBltj.data[0];
		//当前环节
		xcblData.dqhjData = data.queryDqhj.data;
		
		this.comp.refreshTplData(xcblData);
		//设置批次下拉列表框的值
		document.getElementById('yx_pc').value=this.pcId;
		//设置环节下拉列表框的值
		var hj = document.getElementById('yx_hj');
		
		if(hj!=null){
			hj.value=this.dqhjId;
		}
		
		
		this.addComboxListener();
		
		this.initGrid();
		
		this.grid.render('yxlx_xsgird');
		
		this.grid.load({queryStr:this.xsId,pcId:this.pcId,dqhjId:this.dqhjId,start:0,limit:5});
        
    	//文本框获取焦点
     	 var yxlx_querystr=document.getElementById("yxlx_querystr");  
     	 yxlx_querystr.value="";  
      	 yxlx_querystr.focus();
		
		
	},
	
	clearDormInfo : function(){
	
		this.callService({
	             key : 'updateDormForYx',
	             params : {xsId:this.xsId}
	        },function(data){
	        	if(data.updateDormForYx.success){
			 		 //this.buttonCancel();
			 		 this.window.hide();
			 		 //NS.Msg.info('重新分配成功！');
			 		 //this.blSubmit(1);
			 		 this.showDgblWin(this.xsId);
			 		 
		    	   }else{
					NS.Msg.error('置空失败！');
				   } 
	        },this);
	
	}
	
	

});