/**
 * 迎新办理-现场招录-现场分班功能页面
 */
NS.define('Pages.yxlx.xcbl.xcfb',{
	
		//得到班级列表数据
	getBjListData : function(){
		//院系id
		var yxId = document.getElementById("yx_yx").value;
		//专业id
		var zyId = document.getElementById("yx_zy").value;
		//年级id
		var njId = document.getElementById("yx_nj").value;
		
		this.callServiceWithTimeOut(
		 	{key :'queryBjxx',params:{yxId:yxId,zyId:zyId,rxnjId:njId,tableName:'TB_YX_XSJBXX'}}
	        ,function(data){
				
				this.initXcfbTpl(data);
		        
	       },3000000)
	
	},
	
	/****现场分班********/
	initXcfbTpl :function(data){
		data.xm = document.getElementById("Name").value;
		data.yx = this.getSelectXsValue("yx_yx");
		data.zy = this.getSelectXsValue("yx_zy");
	
		if(!this.xcfbTpl){
			NS.loadFile('app/pages/yxlx/template/xcfb.html', function(text) {
								
						this.xcfbTpl = new NS.Template(text);
						
						this.initXcfbWin(data);//初始化现场分班页面
						
							}, this);
		}else{
			
			this.xcfbTpl.writeTo(this.xcfbTplCom,data);
			
			this.xcfbWin.show();
		}
	
	},
	/**
	 * 分班窗口
	 * @param {} data
	 */
	initXcfbWin : function(data){
		
		this.xcfbTplCom = new NS.Component({
			  
			  	data : data,
			    
			    tpl:this.xcfbTpl
			})
				
			
		 	this.xcfbWin = new NS.window.Window({
				
				title:'现场分班',
	           
				layout : 'fit',
				
	            border:false,
	            
	            heigth:500,
	            
	            width:470,
	            
	            closeAction:'hide',
	            
	            autoShow: true,
	            
	            modal:true,
	           	
	           	items:this.xcfbTplCom
	            
	        });
	        
	         //添加监听事件   
		this.xcfbTplCom.on('click',function(event,target){
		        
		        if(target.nodeName == "INPUT" && target.type == "button"){
		        	//确认分配
		        	if("yx_fbqd"==target.name){
		        		
		        	var bj = document.getElementById("yx_xzbj");
		        	
		        	var bjvalue = document.getElementById("yx_xzbj").value;
		      		
		      		document.getElementById("yx_bjId").value =  bjvalue;
		        	
		        	for(var i=0;i<bj.options.length;i++){
  						 
  						 if(bj.options[i].value == bjvalue){
  						 		
  						 		var arr = bj.options[i].text.split(" ");
   								document.getElementById("yx_bj").value=arr[0];
  							 } 
 						 }
 						 this.xcfbWin.hide();
		        		
			          //关闭按钮 	 
		           	}else if("yx_fbqx"==target.name){
		           	
		           		this.xcfbWin.hide();
		        	}
		      }
		},this);
		
	        
	         
	},
	
	getSelectXsValue : function(str){
	
		var obj = document.getElementById(str);
		var value = obj.value;
		      		 for(var i=0;i<obj.options.length;i++){
  						 if(obj.options[i].value == value){
  						 		return obj.options[i].text;
  							 } 
 						 }
		
	
	},
	//得到自动分班数据
	getZdfbData : function(){
		//专业id
		var zyId = document.getElementById("yx_zy").value;
		//年级id
		var njId = document.getElementById("yx_nj").value;
		
		var sfzh = document.getElementById("CardNo").value;
		if(zyId==""||zyId==null){
			NS.Msg.info('请选择录取院系后再选择录取专业.');return;
		}
		if(sfzh==""||sfzh==null){
			NS.Msg.info('身份证号不可为空.请输入正确的身份证号信息.');return;
		}
		this.callServiceWithTimeOut(
		 	{key :'randomFb',params:{zyId:zyId,njId:njId,sfzh:sfzh}}
	        ,function(data){
		        if(data.randomFb.success){
		        	document.getElementById("yx_bjId").value=data.randomFb.data.bjId;
		        	document.getElementById("yx_bj").value = data.randomFb.data.bm;
		        }else{
		        	NS.Msg.error(data.randomFb.errorMsg||data.randomFb.msg||'系统异常！');
		        }
	       },3000000)
	
	}
	

	
	
});