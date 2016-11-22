/**
 * 迎新离校现场办理
 */
NS.define('Pages.yxlx.YxlxXcbl', {
	 extend : 'Template.Page',
	 mixins : ['Pages.yxlx.xcbl.request',//请求后台服务配置
	 		   'Pages.yxlx.xcbl.dgbl',//单个办理
	 		   'Pages.yxlx.xcbl.xczl',//现场招录
	 		   'Pages.yxlx.xcbl.xcfb',//现场分班
	 		   'Pages.yxlx.xcbl.print'//打印
	 		  ],
	


/*******************入口方法 ******************************/
	init: function () {
		NS.loadCss('app/pages/yxlx/template/css/live.css');
        this.initPcData();
    },
  
    
  /****************现场办理页面处理strat******************************************************/  
 
    
  /*********************请求批次数据************************/
    
   initPcData :function(){
    
		 this.callServiceWithTimeOut(
		 	{key : 'queryYxPc',params:{start : 0,limit : 25}}
	        
	        ,function(data){
	        	
	        	if(data.queryYxPc.count >= 1){
	        		
		        	this.pcData = data.queryYxPc.data;
		        	
		        	this.pcId = this.pcData[0].ID;
		        	
		        	this.initHjData();
	        	
	        	}else{
	        	
			        var page= new NS.Component({
				    
				   		html:"<h2>当前没有可以使用的批次！</h2>"
				
					});
				
				this.setPageComponent(page);
	        	
	        	}
	       },3000000);
    
    
    },
 
    
    /******************环节请求数据***********************/
    initHjData : function(){
     //查看当前用是否有办理环节的权限
     this.callServiceWithTimeOut(
		  	{key : 'queryDqhj',params:{pcId:this.pcId}}
	        ,function(data){
		        this.dqhjData=data.queryDqhj.data;
		        	//当前环节id
		        if(this.dqhjData[0].hj!=null && this.dqhjData[0].hj.length!=0){
					
					this.dqhjId = this.dqhjData[0].hj[0].hjId;
					
					this.dqhjMc = this.dqhjData[0].hj[0].hjmc;
			        
			        this.initData();
	        	 }else{
	        	 	 var page= new NS.Component({
				    
				   		html:"<h2>没有可以使用的环节或环节无权限办理,请先设置！</h2>"
				
					});
				
				this.setPageComponent(page);
	        	 }
	        	
	       },3000000)
    
    },

 /**********************现场办理请求数据********************/
	initData : function() {
		 
		 this.callServiceWithTimeOut([
		 	
		 	{key:'querTableHeader',params:{entityName:'TbYxlxHjzt'}},
		 	
		 	{key : 'queryHjtj',params:{pcId:this.pcId,dqhjId:this.dqhjId,start : 0,limit : 25}},
		 	
		 	{key : 'queryBltj',params:{pcId:this.pcId,dqhjId:this.dqhjId}}
		 	
	        ]
	        ,function(data){
	        	this.initComponent(data);
	      },3000000)
	      },
	
/****************处理返回数据********************************/

	initComponent : function(data){
		
		this.xcblData = {};
		
		this.tranData = NS.E2S(data.querTableHeader);
		//迎新批次
		this.xcblData.yxPcData = this.pcData;
		//当前环节
		this.xcblData.dqhjData = this.dqhjData;
		//环节统计
		this.xcblData.hjtjData = data.queryHjtj.data;
		//办理统计
		this.xcblData.bltjData = data.queryBltj.data[0];
		
		this.initGrid();
		//现场办理tpl
		this.initXcblTpl();
	   
	},
	
	/*******************现场办理中部grid**********************************/		
	
	initGrid : function(){
	    var basic = {
           // plugins: [new NS.grid.plugin.HeaderQuery()],//表头查询
            columnData: this.tranData,
            serviceKey:'queryXsxx',
            proxy: this.model,
           	lineNumber: true,
            pageSize:5,
            columnConfig : [
                { text: '总办理进度',
                  name :'zbljd',
                  width:93,
                  xtype :'progresscolumn',
                  color : '#6495ED'
                  },{
                     text: '环节状态',
                     name : 'hjzt',
                     renderer : function(value){
                     	if(value == 1){
                           return "<img title='已通过' src='images/pass.gif'/>";
                     	}else{
                     	 return "<img title='未办理' src='images/notprocess.gif'/>";
                     	}
                     }
                  },{
				  text: '操作',
                  name :'operator',
                  xtype : 'linkcolumn',
                  width:85,
                  links  : [
                    {
                        linkText : '进入办理>>',
                        style : {
                            color : 'blue',
                            font : '18px',
                           'text-decoration' : 'none'
                        }
                    }
          		  ]
                  }
            ],
            autoScroll: true,
            multiSelect: true,
            lineNumber: false,
            border : true
        };
        this.grid = new NS.grid.SimpleGrid(basic);
        //this.grid = new NS.grid.Grid(basic);
     	this.grid.bindItemsEvent({
         'operator': {event: 'linkclick', fn: this.getClickData, scope: this}
        });
        
	},
	/**********响应点击事件***************************/
	getClickData : function(linkValue, recordIndex, cellIndex, data, eOpts){
			//处理学生信息
			this.processXsxx(data,data.xsId,0);
		
	},
	
	
	/****************获取现场办理html文件********************************/
	initXcblTpl : function(){	
		
		NS.loadCss('app/pages/yxlx/template/css/outstyle.css');
		
		NS.loadCss('app/pages/yxlx/template/css/zycstyle.css');
      
		NS.loadFile('app/pages/yxlx/template/xcbl.html', function(text) {
							
							this.xcblTpl = new NS.Template(text);
				
							 this.initPage();//初始化页面
							 
							 this.initMask();//初始化遮罩层
						
						}, this);
	},
	
	
	
	
	
	/**************现场办理页面***********************/

	initPage : function(){
		
		var comp =this.comp= new NS.Component({
		
			 border : false,
		 
		    data : this.xcblData,
		    
		    tpl:this.xcblTpl
			
		})
		
		this.page = new NS.container.Container({items:comp});
		
		this.page.on('afterrender',function(){
		
			this.grid.render('yxlx_xsgird');
			
			this.grid.load({pcId:this.pcId,dqhjId:this.dqhjId});  
		
		},this);	
		
		this.page.on('click',function(event,target){
		        if(target.nodeName == "INPUT" && target.type == "button"){
			        	if("yxlx_query"==target.name){//查询
					           	
					           	var queryStr = document.getElementById("yxlx_querystr").value;
					           	
					           	this.showDgblWin(queryStr);
			           
			           }else if("yxlx_load"==target.name){
			           		//刷新 现场办理整个页面数据
			           		this.loadXcblData();
		        			
			           	}else if("yx_add"==target.name){
			           		//现场招录
			           		this.initXczlData('A');
					}
		      
		        }
		},this);
		
		
		this.page.on('keydown',function(event,target){
		       			//重新批次加载批次数据，看是否有使用的批次
			        	if(event.getKey() == 13){ 
			        		this.loadPcData();
			        		  if(this.pcNum!=0){
	 								var queryStr = document.getElementById("yxlx_querystr").value;
			           				this.showDgblWin(queryStr);
			        		}
		        		}
		},this);
		this.setPageComponent(this.page);
		
		//文本框获取焦点
		var yxlx_querystr=document.getElementById("yxlx_querystr");  
		yxlx_querystr.value="";  
		yxlx_querystr.focus();
		
		this.addComboxListener();
		
	},
	
	//批次下拉列表框监听事件
	addComboxListener : function(){
		
		var pc = document.getElementById('yx_pc');
		var me = this;
		pc.onchange=function(){
		 
		 me.pcId = pc.value;
		
		 me.loadXcblData();
		
		}
		//环节下拉列表
		var hj = document.getElementById('yx_hj');
		
		if(hj!=null){
			hj.onchange=function(){
			
			me.dqhjId = hj.value;
			
			me.loadXcblData();
			
			}
		
		}
	
	
	},
	
	/**
	 *遮罩 
	*/
	initMask : function(){
	this.mask = new NS.mask.LoadMask({
         target : this.page,
         msg : '数据加载中,请稍候...'
     });
	}
	
	
})