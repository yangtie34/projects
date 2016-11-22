/**
 * 迎新办理-现场招录功能页面
 */
NS.define('Pages.yxlx.xcbl.xczl',{
	
	
	
	/*****************现场招录请求数据**********/
	initXczlData : function(lx){
		this.mask.show();//添加遮罩
		this.callService(
		 	{key : 'queryYxPc',params:{start : 0,limit : 25}}
	        ,function(data){
	        var num  = data.queryYxPc.count;
			if(num == 0){
				this.mask.hide();
		 	    this.page.removeAll();
		 		var com = new NS.Component({html:"<h2>当前没有可以使用的批次！</h2>"});
				this.page.add(com);
			 }else{
			 	this.callXczlData(lx);
			 }});
	},
	
	callXczlData : function(lx){
			if(lx=='U'){
				this.callService([
					{key : 'queryXsxx',params:{queryStr:this.xsId,pcId:this.pcId,dqhjId:this.dqhjId}},
					{key : 'queryXbCombox',params:{lx:'xb'}},
					{key : 'queryMzCombox',params:{lx:'mz'}},
					{key : 'queryYxCombox',params:{lx:'yx'}},
					{key : 'querySydCombox',params:{lx:'syd',cc:'2'}},
					{key : 'queryNjCombox',params:{lx:'nj'}},
					{key : 'queryXzCombox',params:{lx:'xz'}}
					]
			        ,function(data){
			        	data.queryXsxx = data.queryXsxx.data[0];
			        	this.mask.hide();//去掉遮罩
			        	this.window.hide();
			        	this.initXczlTpl(data);
			      });
			
			}else if(lx=='A'){
				this.callService([
					{key : 'queryXbCombox',params:{lx:'xb'}},
					{key : 'queryMzCombox',params:{lx:'mz'}},
					{key : 'queryYxCombox',params:{lx:'yx'}},
					{key : 'querySydCombox',params:{lx:'syd',cc:'2'}},
					{key : 'queryNjCombox',params:{lx:'nj'}},
					{key : 'queryXzCombox',params:{lx:'xz'}}
					]
			        ,function(data){
			        	this.mask.hide();//去掉遮罩
			        	this.initXczlTpl(data);
			        	
			      });
			}
		
	
		
	},
	
	/****现场招录tpl********/
	initXczlTpl :function(data){
		
		if(!this.xczlTpl){
			
			NS.loadFile('app/pages/yxlx/template/xczl.html', function(text) {
								
						this.xczlTpl = new NS.Template(text);
						
						this.initXczlWin(data);//初始化现场招录页面
						
							}, this);
		}else{
			
			this.clearXczlData();
			//设置下拉列表框的值
			this.setComboxData(data);	
			this.xczlWin.show();
		}
	
	},
	
	
	
	/*****************现场招录窗口**********/
	initXczlWin : function(data){
		
		data.pcId = this.pcId;
		
		this.xczlData = data;
		
		this.xczlTplCom = new NS.Component({
			  
			  	data : data,
			    
			    tpl:this.xczlTpl
			});
			
		 	this.xczlWin = new NS.window.Window({
				
				title:'现场招录',
	           
				layout : 'fit',
				
	            border:false,
	            
	            heigth:860,
	            
	            width:660,
	            
	            closeAction:'hide',
	            
	            autoShow: true,
	            
	            autoScroll:true,
	            
	            modal:true,
	           	
	           	items:this.xczlTplCom
	            
	        });
	        
	        //默认值为汉族
	        var mz = data.queryMzCombox.data;
	         
	        for(var j=0;j<mz.length;j++){
					var nation = mz[j];
					if(nation[1]=="汉族"){
					//民族
	                 document.getElementById('Nation').value = nation[0];
					}
				 }
		
	    //添加监听事件   
		this.xczlTplCom.on('click',function(event,target){
		        
		        if(target.nodeName == "INPUT" && target.type == "button"){
		        	//读卡按钮
		        	if("yx_dk"==target.name){
		        		 this.clearXczlData();
			           	 /**********读取身份证信息*******************/
			           	 this.readCardData();
			          //关闭按钮 	 
		           	}else if("yx_gb"==target.name){
		           	
		           		this.xczlWin.hide();
		           	//继续办理按钮
		           	}else if("yx_jxbl"==target.name){
	  					this.saveXsInfoHanlder();
	   					  //现场分班
		           	}else if("yxlx_xcfb"==target.name){
		           			NS.loadCss('app/pages/yxlx/template/css/fenban.css');
		           			this.getBjListData();
		           	
		           	//现场自动分班
		           	}else if("yxlx_xczdfb"==target.name){
		           			this.getZdfbData();
		           	}
		        }
		},this);
		
		
			//身份证框焦点失去事件
			var me = this;
			var sfzh = document.getElementById("CardNo");
			sfzh.onblur = function(){
			if(!me.xczlWin.isHidden()){
				var sfzhValue = document.getElementById("CardNo").value;
				if(sfzhValue.length!=18 || !me.isalphanumber(sfzhValue.substring(0,17))){
					NS.Msg.error('身份证位数不对或输入错误,请重新输入！');
					return;
				  }
				me.callService(
				{key : 'splitCard',params:{sfzh:sfzhValue}}
				,function(data){
				me.addXczlData(data.splitCard.data[0]);
				});
				}
			};
		
		//回车继续办理事件
		this.xczlWin.on('keydown',function(event,target){
			        		if(event.getKey() == 13){ 
				           		this.saveXsInfoHanlder();
			        		}
			},this);
			
			
       
		/*******************
		 院系下拉框监听事件
	    ********************/
		var yx = document.getElementById('yx_yx');
		 var me = this;
		 yx.onchange=function(){
		 var yxId = document.getElementById('yx_yx').value;
		
		 me.callService(
			{key : 'queryZyCombox',params:{lx:'zy',yxId:yxId}}
			,function(data){
			var zydata= data.queryZyCombox;
			var zy = document.getElementById('yx_zy');
			zy.innerHTML='';
			for(var i=0;i<zydata.count;i++){
			var op = document.createElement("option");
			op.setAttribute("value",zydata.data[i].ID);
			op.innerHTML=zydata.data[i].MC;
			zy.appendChild(op);
			}
			document.getElementById('yx_bjId').value="";
			document.getElementById('yx_bj').value="";
					        	
		 });
		};
	//设置下拉列表框的值
	this.setComboxData(data);	
	},
	//保存学生信息处理方法
	saveXsInfoHanlder:function(){
		var data = this.getXczlData();
		
		if(this.isNull(data.zyId)){
				NS.Msg.error('专业不能为空，请重新输入！');
				return;
		}
		
		
		if(this.isNull(data.yxId)){
				NS.Msg.error('院系不能为空，请重新输入！');
				return;
		}
		
		if(this.isNull(data.xbId)){
				NS.Msg.error('性别不能为空，请重新输入！');
				return;
		}
   		if(this.isNull(data.xm)){
				NS.Msg.error('姓名不能为空，请重新输入！');
				return;
		}
		if(data.sfzh.length!=18	|| !this.isalphanumber(data.sfzh.substring(0,17))){
				NS.Msg.error('身份证不能为空或输入不对，请重新输入！');
				return;
		}
        if(data['bjId']==""){
        	NS.Msg.error('请选择手动或者自动分配按钮给学生分配班级！');return;
        }
       
		this.callService({key:'insertBjJyHandler',params:data},function(reslutData){
			if(reslutData['insertBjJyHandler']['success']){
				
				this.callService({key : 'saveXsInfo',params:data},function(data_){
		        	if(data_.saveXsInfo.success){
		        		this.xczlWin.hide();// 隐藏现场招录窗口
						this.showDgblWin(this.queryStr);//显示单个办理窗口
					  }else{
						NS.Msg.error('录入信息错误,请重新录入！');
					  } 
		        });
			}else{
				NS.Msg.info(reslutData['insertBjJyHandler']['msg']);
			}
		},this);
	},
	/**
	 * 初始化现场招录数据
	 * @param {} data
	 */
	addXczlData : function(data){
		//先清除数据
		 this.clearXczlData1();
		if(data.jtdz!=null){
		 	document.getElementById('Address').value = data.jtdz;
		}
		 
		 document.getElementById('Sex').value = data.xb;
		 
		 document.getElementById('Born').value = data.csrq;  
		 
		 document.getElementById('yx_syd').value = data.syd; 
		
	},
	/**
	 * 读取身份证信息【华视设备】
	 */
	readCardData :function(){
		
		  var CVR_IDCard = document.getElementById("CVR_IDCard");
         				
		  var strReadResult = CVR_IDCard.ReadCard();
			           	
          if(strReadResult == "0"){ 
			 //姓名
			document.getElementById("Name").value = CVR_IDCard.Name; 
	         //出生日期
	        document.getElementById('Born').value = CVR_IDCard.Born;   
	       //地址
	        document.getElementById('Address').value = CVR_IDCard.Address;
	        //身份号码
	        document.getElementById('CardNo').value = CVR_IDCard.CardNo; 
	        //性别                
	       var xb = this.xczlData.queryXbCombox.data;
			//民族				  
		    var mz = this.xczlData.queryMzCombox.data;
							  
			for(var i=0;i<xb.length;i++){
								  	
				var sex = xb[i];
								  
				if(sex[1]==CVR_IDCard.Sex){
					//性别
			        document.getElementById('Sex').value = sex[0];
					}
			}
		    for(var j=0;j<mz.length;j++){
				var nation = mz[j];
								  	
				if(nation[1]==CVR_IDCard.Nation+"族"){
					//民族
		    		document.getElementById('Nation').value = nation[0];
				}
			}
	              //照片
	           //document.all['pic'].src = CVR_IDCard.Pic;
			
			
	//生源地		
	this.callService(
		{key : 'splitCard',params:{sfzh:CVR_IDCard.CardNo}}
		,function(data){
			document.getElementById('yx_syd').value = data.splitCard.data[0].syd; 
		});
			
	   }else{
	   	    this.clearXczlData();
	   		NS.Msg.error(strReadResult);
	   }
	
	},
	
	/**
	 * 获取现场招录数据
	 * @return {}
	 */
	getXczlData :function(){
	  var params = {};
	  //姓名
	  params.xm = document.getElementById("Name").value;
	  
	  // 性别
	  params.xbId = document.getElementById("Sex").value;
	 //民族
	  params.mzId = document.getElementById("Nation").value;
	 //出生日期
	  params.csrq = document.getElementById("Born").value;
	 //联系电话
	  params.lxdh = document.getElementById("lxdh").value;
	  //家庭地址
	 var jtdz = document.getElementById("Address").value;
	 
	 if(this.isNull(jtdz)){
	   params.jtdz = "";
	 }else{
	 	params.jtdz=jtdz;
	 }
	 // 身份证号
	  params.sfzh = this.queryStr=document.getElementById("CardNo").value;
	
	  // 生源地
	  params.sydId = document.getElementById("yx_syd").value;
	  //院系
	  params.yxId = document.getElementById("yx_yx").value;
	  //  专业
	  params.zyId = document.getElementById("yx_zy").value;
	  //  年级
	  params.rxnjId = document.getElementById("yx_nj").value;
	  //学制
	  params.xzId = document.getElementById("yx_xz").value;

	  var bjmc = document.getElementById("yx_bj").value;
	  if(bjmc!=""){
		 	params.bjId = document.getElementById("yx_bjId").value;
		 }else{
		 params.bjId="";
		 }
	  //迎新批次
	  params.pcId = this.pcId;
	  
	  return params;
	},
	/**
	 * 清除现场招录数据
	 */
	clearXczlData : function(){
		 //姓名
		 document.getElementById("Name").value = ""; 
		//性别
        document.getElementById('Sex').value = "";
        //民族
        //document.getElementById('Nation').value = ""; 
        //出生日期
        document.getElementById('Born').value = "";   
        //地址
        document.getElementById('Address').value = "";
        //身份号码
        document.getElementById('CardNo').value = ""; 
        //联系电话
        document.getElementById('lxdh').value = ""; 
        //生源地
        document.getElementById('yx_syd').value = ""; 
         //院系
        document.getElementById('yx_yx').value = ""; 
         //专业   
        document.getElementById('yx_zy').value = ""; 
         //年级     
       // document.getElementById('yx_nj').value = ""; 
        //班级  
        document.getElementById('yx_bj').value = ""; 
        
        
        
	},
		/**
	 * 清除现场招录数据
	 */
	clearXczlData1 : function(){
		 //姓名
		 document.getElementById("Name").value = ""; 
		//性别
        document.getElementById('Sex').value = "";
        //民族
        //document.getElementById('Nation').value = ""; 
        //出生日期
        document.getElementById('Born').value = "";   
        //地址
        document.getElementById('Address').value = "";
        //身份号码
        //document.getElementById('CardNo').value = ""; 
        //联系电话
        document.getElementById('lxdh').value = ""; 
        //生源地
        document.getElementById('yx_syd').value = ""; 
         //院系
        document.getElementById('yx_yx').value = ""; 
         //专业   
        document.getElementById('yx_zy').value = ""; 
         //年级     
        //document.getElementById('yx_nj').value = ""; 
        //班级  
        document.getElementById('yx_bj').value = ""; 
        
        
        
	},
	/**
	 * 验证是否是数字
	 * @param {} str
	 * @return {Boolean}
	 */
	isalphanumber: function (str){ 
		
		var result=str.match(/^[0-9]*[1-9][0-9]*$/); 
		
		if(result==null) return false; 
		
		return true; 
	},
	/**
	 * 验证是否为空
	 * @param {} str
	 */
	isNull :function(value){
		
		if(value.length==0) return true; 
		
		return false; 
	
	},
	/**
	 * 刷新批次数据
	 */
	loadPcData : function(){
			
			this.callServiceWithTimeOut(
		 	
		 	{key : 'queryYxPc',params:{start : 0,limit : 25}}
	        
	        ,function(data){
	        	
	        var num = this.pcNum = data.queryYxPc.count;
			if(num == 0){
		 	    this.page.removeAll();
		 		var com = new NS.Component({
				html:"<h2>当前没有可以使用的批次！</h2>"
					});
			this.page.add(com);
	       }
		        
	       },3000000);
	},
	//设置下拉列表框的值
	setComboxData :function(data){
		
		if(data.queryXsxx!=null){
		
		var sfzh = data.queryXsxx.sfzh;
		var csrq = data.queryXsxx.csrq;
		var lxdh = data.queryXsxx.lxdh;
		var jtdz = data.queryXsxx.jtdz;
		var xm = data.queryXsxx.xm;
		var xb = data.queryXsxx.xbid;
		var mz = data.queryXsxx.mzid;
		var syd = data.queryXsxx.sydid;
		var zyvalue = data.queryXsxx.zyid;
		var bj = data.queryXsxx.bjid;
		var bjmc = data.queryXsxx.bjmc;
		var yx = data.queryXsxx.yxid;
		var xz = data.queryXsxx.xzid;
		
		this.callService(
			{key : 'queryZyCombox',params:{lx:'zy',yxId:yx}}
			,function(data){
				var zydata= data.queryZyCombox;
				var zy = document.getElementById('yx_zy');
				zy.innerHTML='';
				for(var i=0;i<zydata.count;i++){
				var op = document.createElement("option");
				op.setAttribute("value",zydata.data[i].ID);
				op.innerHTML=zydata.data[i].MC;
				zy.appendChild(op);
				}
			
		if(sfzh!=null){
			document.getElementById('CardNo').value=sfzh;
		}
		if(xm!=null){
			document.getElementById('Name').value=xm;
		}
		if(lxdh!=null){
			document.getElementById('lxdh').value=lxdh;
		}
		if(csrq!=null){
			document.getElementById('Born').value=csrq;
		}
		if(jtdz!=null){
			document.getElementById('Address').value=jtdz;
		}
		if(xb!=null){
			document.getElementById('Sex').value=xb;
		}
		if(mz!=null){
			document.getElementById('Nation').value=mz;
		}
		
		if(syd!=null){
			document.getElementById('yx_syd').value=syd;
		}
		if(yx!=null){
			document.getElementById('yx_yx').value=yx;
		}
		if(zyvalue!=null){
			document.getElementById('yx_zy').value=zyvalue;
		}
		if(bj!=null){
		document.getElementById('yx_bjId').value=bj;
		}
		if(xz!=null){
			document.getElementById('yx_xz').value=xz;
		}
		if(bjmc!=null){
			document.getElementById('yx_bj').value=bjmc;
		}
			document.getElementById('CardNo').setAttribute('disabled','disabled');
		
		 });
		}else{
			document.getElementById('CardNo').removeAttribute('disabled');
		}
		
	}
	


});