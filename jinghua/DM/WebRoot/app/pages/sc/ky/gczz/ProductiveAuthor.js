/**
 * 高产作者
 */
NS.define('Pages.sc.ky.gczz.ProductiveAuthor',{
	extend:'Template.Page',
	 modelConfig: {
        serviceConfig: {
            queryJxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据，教学组织结构
            getTotalByYear: 'productiveAuthorService?getTotalAndChangeByYear',
            getXwByYear: 'productiveAuthorService?getXwByYear',
            getXlByYear: 'productiveAuthorService?getXlByYear',
            getZcByYear: 'productiveAuthorService?getZcByYear',
            getLxsjByYear: 'productiveAuthorService?getLxsjByYear',
            getPylxByYear: 'productiveAuthorService?getPylxByYear',
            getXyByYear: 'productiveAuthorService?getXyByYear',
            getAgeScatterByYear: 'productiveAuthorService?getAgeScatterByYear',
            getLngczzsbhjsByYear: 'productiveAuthorService?getLngczzsbhjsByYear',
            getGdwdbByType:'productiveAuthorService?getGdwdbByType',
            getLnqsByType:'productiveAuthorService?getLnqsByType',
            getTableXueWeiPage:'productiveAuthorService?getTableXueWeiPage',
            getTableLngczzPage:'productiveAuthorService?getTableLngczzPage',
            getTableLwSexPage:'productiveAuthorService?getTableLwSexPage',
            getTable2:'productiveAuthorService?getTable2'
        }
    },
    mixins:['Pages.sc.Scgj'],
    requires:['Pages.sc.ky.pie.PieDisplay'],
    params:{},
    init: function() {
    	var me = this;
    	var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'论文高产作者分析',
                pageHelpInfo:'对高产作者进行分析。高产作者为一年发表三篇以上论文的作者'}
        });
        
        var navigation = this.navigation = new Exp.component.Navigation();
      	var containerx = this.mainContainer = this.createMain();
      	
      	var productiveAuthor_htmlTotal = '<div id=\'productiveAuthor_htmlTotal\'><span id=\'year_\'></span>年高产作者总数：<font color=\'green\' size=\'3\'><span id=\'total\'></span></font>人，较去年<font color=\'green\' size=\'3\'><span id=\'change\'></span></font>人</div>';
      	var productiveAuthor_html1 = this.component = new NS.Component({
  			margin : "10px 10px 10px 0px",
			html : productiveAuthor_htmlTotal
		});
		
		var productiveAuthor_htmlXueWei = 
				"<div>" +
					"<div style='float: left; width:33%; border-bottom:blue 1px dashed;border-right:blue 1px dashed;'>" +
						"<div id='productiveAuthor_xueWeiPie'></div>" +
						"<div style='margin-bottom:10px;'><span style='margin-left:50px;'><a href='javascript:void(0)' path='xueWeiPie' class='productiveAuthor_gdwdb'>各单位对比</a></span><span style='float: right;  margin-right:50px;'><a href='javascript:void(0)' class='productiveAuthor_lnqs' path='xueWeiPie'>历年趋势</a></span></div>" +
					"</div>" +
				
					"<div style='float: left; width:33%;margin-bottom:0px;border-bottom:blue 1px dashed;border-right:blue 1px dashed;'>" +
						"<div id='productiveAuthor_xueliPie'></div>" +
						"<div style='margin-bottom:10px;'><span style='margin-left:50px;'><a href='javascript:void(0)' path='xueliPie' class='productiveAuthor_gdwdb'>各单位对比</a></span><span style='float: right;  margin-right:50px;'><a href='javascript:void(0)' class='productiveAuthor_lnqs' path='xueliPie'>历年趋势</a></span></div>" +
					"</div>" +
				
					"<div style='float: left; width:33%;margin-bottom:0px;border-bottom:blue 1px dashed;'>" +
						"<div id='productiveAuthor_zhichengPie'></div>" +
						"<div style='margin-bottom:10px;'><span style='margin-left:50px;'><a href='javascript:void(0)' path='zhichengPie' class='productiveAuthor_gdwdb'>各单位对比</a></span><span style='float: right;  margin-right:50px;'><a href='javascript:void(0)' class='productiveAuthor_lnqs' path='zhichengPie'>历年趋势</a></span></div>" +
					"</div>" +
				"</div>"
				+"<div>" +
					"<div style='float: left; width:33%;border-right:blue 1px dashed;'>" +
						"<div id='productiveAuthor_laixiaoshijianPie'></div>" +
						"<div style='margin-bottom:10px;'><span style='margin-left:50px;'><a href='javascript:void(0)' path='laixiaoshijianPie' class='productiveAuthor_gdwdb'>各单位对比</a></span><span style='float: right;  margin-right:50px;'><a href='javascript:void(0)' class='productiveAuthor_lnqs' path='laixiaoshijianPie'>历年趋势</a></span></div>" +
					"</div>" +
				
					"<div style='float: left; width:33%; height: 378px; margin-bottom:10px;border-right:blue 1px dashed;'>" +
						"<div id='peiyangleixingPie'></div>" +
						"<div style='display:none;'><span style='margin-left:50px;'><a href='javascript:void(0)' path='peiyangleixingPie' class='productiveAuthor_gdwdb'>各单位对比</a></span><span style='float: right;  margin-right:50px;'><a href='javascript:void(0)' class='productiveAuthor_lnqs' path='peiyangleixingPie'>历年趋势</a></span></div>" +
					"</div>" +
				
					"<div style='float: left; width:33%; height: 378px;margin-bottom:10px;'>" +
						"<div id='xueyuanPie'></div>" +
						/*"<div><span style='margin-left:50px;'><a href='javascript:void(0)' path='xueyuanPie' class='productiveAuthor_gdwdb'>各单位对比</a></span><span style='float: right;  margin-right:50px;'><a href='javascript:void(0)' class='productiveAuthor_lnqs' path='xueyuanPie'>历年趋势</a></span></div>" +*/
					"</div>" +
				"</div>";
		var productiveAuthor_htmlXueWei_ = this.component = new NS.Component({
  			margin : "10px 10px 10px 0px",
			html : productiveAuthor_htmlXueWei
		});
		
		var productiveAuthor_htmlAge = "<div><div id='ageScatter'></div></div>";
		var productiveAuthor_htmlAge_ = this.component = new NS.Component({
  			margin : "10px 10px 10px 0px",
			html : productiveAuthor_htmlAge
		});
		
		var productiveAuthor_htmlLngczz = "<div><div id='lngczzMain'></div></div>";
		var productiveAuthor_htmlLngczz_ = this.component = new NS.Component({
  			margin : "10px 10px 10px 0px",
			html : productiveAuthor_htmlLngczz
		});
				
        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,navigation,containerx, productiveAuthor_html1, this.title1, productiveAuthor_htmlXueWei_, this.title2, productiveAuthor_htmlAge_, this.title3, productiveAuthor_htmlLngczz_]
        });
        this.setPageComponent(container);
        container.on('afterrender',function(){
            // 刷新导航栏
            this.callService('queryJxzzjgTree',function(data){
                this.navigation.refreshTpl(data.queryJxzzjgTree);
            },this);
            navigation.on('click',function(){   // 点击院系查询条件
            	var data = this.getValue();
            	var len = data.nodes.length;
            	me.params.deptId = data.nodes[len-1].id;
            	me.updateTotal();
            	me.updatePie();
            });
            me.params.year = new Date().getFullYear(); 
            me.params.deptId = '0'; 
            this.updateTotal();
            this.updatePie();
            this.lngczzsbhjs();
            
            $('.productiveAuthor_gdwdb').on('click', function(){
            	me.createBar($(this).attr('path'));
            });
            $('.productiveAuthor_lnqs').on('click', function(){
            	me.createLine($(this).attr('path'));
            });
        },this);
    },
    
    createLine:function(type){
   	 	var me = this;
    	this.win = new NS.window.Window({
            title:'历年趋势',
            layout:'fit',
            modal:true,
            width:780,
            height:480,
            html:'<div id="barTu" style="width:100%; height:400px;"></div>'
        });
        this.win.show();
        this.params.type=type;
        this.callService({key:'getLnqsByType',params:this.params},function(data){
        	var result = data.getLnqsByType;
        	var xData = result.xData;
			var serieData = result.serieData;
			var option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:result.legendData
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            saveAsImage : {show: true}
			        }
			    },
			  	dataZoom : {
			        show : true,
			        realtime : true,
			        start : 20,
			        end : 80
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : function (){
			                var list = [];
			                for (var i = 0; i < xData.length; i++) {
			                    list.push(xData[i]);
			                }
			                return list;
			            }()
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : serieData
			};
			var clickfn = function(params){
				me.params.year= params.name;
				var param = {data:{name:params.seriesName}};
				me.createTableXueWeiPage(param, type);
			}
        	var echartsBar = echarts.init(document.getElementById("barTu"), 'blue')
        	echartsBar.setOption(option);
        	echartsBar.on(echarts.config.EVENT.CLICK, clickfn);
        },this);
    },
    
    createBar: function(type){
    	var me = this;
    	this.win = new NS.window.Window({
            title:'各单位对比',
            layout:'fit',
            modal:true,
            width:780,
            height:480,
            html:'<div id="barTu" style="width:100%; height:443px;"></div>'
        });
        this.win.show();
        this.params.type=type;
        this.callService({key:'getGdwdbByType',params:this.params},function(data){
        	var result = data.getGdwdbByType;
        	var xData = result.xData;
			var serieData = result.serieData;
			var option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:result.legendData
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            saveAsImage : {show: true}
			        }
			    },
			  	dataZoom : {
			        show : true,
			        realtime : true,
			        start : 45,
			        end : 55
			    },
			    xAxis : [
			        {
			            type : 'category',
			            data : function (){
			                var list = [];
			                for (var i = 0; i <xData.length; i++) {
			                    list.push(xData[i]);
			                }
			                return list;
			            }()
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value'
			        }
			    ],
			    series : result.serieData
			};
			var clickfn = function(params){
				var param = {data:{name:params.seriesName,deptName:params.name}};
				me.createTableXueWeiPage(param, type);
			}
    		var echartsBar = echarts.init(document.getElementById("barTu"), 'blue');
    		echartsBar.setOption(option);
    		echartsBar.on(echarts.config.EVENT.CLICK, clickfn);
    		if(xData.length<=0){
				    echartsBar.showLoading({
			    	text :"暂无数据",
			        effect : 'bubble',
			        effectOption:{effect: {n: 0}},
			        textStyle : {
			            fontSize : 20
			        }
			    });		
    		}
        },this);
    },
    
    
    
    
    winPage: function(type){
    	var me = this;
    },
    
    updateTotal : function(){
    	 this.callService({key:'getTotalByYear',params:this.params},function(data){
    	 	$("#productiveAuthor_htmlTotal #year_").html(data.getTotalByYear.year);
    	 	$("#productiveAuthor_htmlTotal #total").html(data.getTotalByYear.currentYearTotal);
    	 	$("#productiveAuthor_htmlTotal #change").html(data.getTotalByYear.change);
         },this);
    },
    
    updatePie : function(){
       	this.xueWeiUpdate();
     	this.xueliUpdate();
       	this.zhichengUpdate();
       	this.laixiaoshijianUpdate();
       	this.peiyangleixingUpdate();
       	this.xueyuanUpdate();
       	this.ageScatterUpdate();
    },
    
    xueWeiUpdate : function(){
    	var me = this;
    	this.callService({key:'getXwByYear',params:this.params},function(data){
    			var legendData = [];
		    	var seriesData = [];
		    	if(data.getXwByYear){
					for(var i =0; i<data.getXwByYear.length;i++){
						var obj = data.getXwByYear[i];
						legendData.push(obj.NAME);
						seriesData.push({value:obj.VALUE, name: obj.NAME});
					}
		    	}
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	pieUtils.createPie({
	        	divId : 'productiveAuthor_xueWeiPie',
	        	title:'学位分布',
	        	width:'100',
	        	height:'350',
				legendData:legendData,
	        	seriesData:seriesData,
	        	clickfn: function(param){
					me.createTableXueWeiPage(param, 'xueWeiPie');
				}
	        });
        },this);
    },
    
    createTableXueWeiPage:function(param, show_type){
    	var me = this;
    	var data = param.data;
    	var name = data.name;
        var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
        var params = {data:data,type:show_type, start:0,limit:15};
        params = Ext.apply(this.params,params);
    	title=name+"高产作者";
    	gridFields=['MC','XM','VALUE','LWNUM', 'PEOPLE_ID'];
    	textarrays=['作者单位','作者姓名','学位','论文数','作者Id'];
    	widtharrays=[200,150,100,80,80];
    	hiddenarrays=[false,false,false,false,true];
        this.callService({key:'getTableXueWeiPage',params:params},function(respData){
            var data = respData.getTableXueWeiPage;
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getTableXueWeiPage',false,true,15);
            var jzgname = new NS.form.field.Text({
                width:240,
                fieldLabel:'作者姓名',
                labelWidth:80,
                emptyText:'作者姓名'
            });
            var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({jzgname:jzgname.getValue()});
					}
			};
            // 未填写教学日志导出
            var tbar = new NS.toolbar.Toolbar({
                items:[jzgname,butt,'->']
            });
            this.win = new NS.window.Window({
                title:title+'名单',
                layout:'fit',
                modal:true,
                width:830,
                height:480,
                tbar:tbar,
                items:dxgrid
            });
            this.win.show();
            dxgrid.bindItemsEvent({
                'LWNUM' :{event:'linkclick',fn:me.winPage2,scope:{me:me}}
            });
        });
    },
    
    winPage2 : function(text){
    	var me = this.me;
        var params = {prople_id:arguments[3].PEOPLE_ID,start:0,limit:15};
        params = Ext.apply(me.params,params);
        var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
    	title= arguments[3].XM + "的论文列表";
    	gridFields=['TITLE_','PERIODICAL','NJQY','PERIODICALNAME','ORDER_','REMARK'];
    	textarrays=['论文题目','期刊名称','年卷期页','期刊类别','我校名次','备注'];
    	widtharrays=[210,120,200,100,80,80];
    	hiddenarrays=[false,false,false,false,false,false];
        me.callService({key:'getTable2',params:params},function(respData){
            var data = respData.getTable2;
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getTable2',false,true,15);
            var lwtitle = new NS.form.field.Text({
                width:240,
                fieldLabel:'论文标题',
                labelWidth:80,
                emptyText:'论文标题'
            });
            var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({lwtitle:lwtitle.getValue()});
					}
			};
            // 未填写教学日志导出
            var tbar = new NS.toolbar.Toolbar({
                items:[lwtitle,butt,'->']
            });
            this.win = new NS.window.Window({
                title:title,
                layout:'fit',
                modal:true,
                width:830,
                height:480,
                tbar:tbar,
                items:dxgrid
            });
            this.win.show();
        });
    },
    
    xueliUpdate : function(){
    	var me = this;
    	this.callService({key:'getXlByYear',params:this.params},function(data){
		    	var legendData = [];
		    	var seriesData = [];
		    	if(data.getXlByYear){
					for(var i =0; i<data.getXlByYear.length;i++){
						var obj = data.getXlByYear[i];
						legendData.push(obj.NAME);
						seriesData.push({value:obj.VALUE, name: obj.NAME});
					}
		    	}
	    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
	    	 	pieUtils.createPie({
		        	divId : 'productiveAuthor_xueliPie',
		        	title:'文化程度分布',
		        	width:'100',
		        	height:'350',
		        	legendData:legendData,
		        	seriesData:seriesData,
					clickfn: function(param){
						me.createTableXueWeiPage(param, 'xueliPie');
					}
	        });
         },this);
    	
    },
    
    zhichengUpdate : function(){
    	var me = this;
    	this.callService({key:'getZcByYear',params:this.params},function(data){
    		var legendData = [];
		    	var seriesData = [];
		    	if(data.getZcByYear){
					for(var i =0; i<data.getZcByYear.length;i++){
						var obj = data.getZcByYear[i];
						legendData.push(obj.NAME);
						seriesData.push({value:obj.VALUE, name: obj.NAME});
					}
		    	}
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	pieUtils.createPie({
	        	divId : 'productiveAuthor_zhichengPie',
	        	title:'职称分布',
	        	width:'100',
	        	height:'350',
	        	legendData:legendData,
	        	seriesData:seriesData,
				clickfn: function(param){
					me.createTableXueWeiPage(param, 'zhichengPie');
				}
	        });
         },this);
    	
    },
       
    
   	laixiaoshijianUpdate : function(){
    	var me = this;
    	this.callService({key:'getLxsjByYear',params:this.params},function(data){
    	 	var legendData = [];
		    	var seriesData = [];
		    	if(data.getLxsjByYear){
					for(var i =0; i<data.getLxsjByYear.length;i++){
						var obj = data.getLxsjByYear[i];
						legendData.push(obj.NAME);
						seriesData.push({value:obj.VALUE, name: obj.NAME});
					}
		    	}
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	pieUtils.createPie({
	        	divId : 'productiveAuthor_laixiaoshijianPie',
	        	title:'来校时间分布',
	        	width:'100',
	        	height:'350',
	        	legendData:legendData,
	        	seriesData:seriesData,
				clickfn: function(param){
					me.createTableXueWeiPage(param, 'laixiaoshijianPie');
				}
	        });
        },this);
    	
    },
    
    
    peiyangleixingUpdate : function(){
    	  this.callService({key:'getPylxByYear',params:this.params},function(data){
    	  	var legendData = [];
		    	var seriesData = [];
		    	if(data.getPylxByYear){
					for(var i =0; i<data.getPylxByYear.length;i++){
						var obj = data.getPylxByYear[i];
						legendData.push(obj.name);
						seriesData.push({value:obj.value, name: obj.name});
					}
		    	}
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	pieUtils.createPie({
	        	divId : 'peiyangleixingPie',
	        	title:'培养类型分布',
	        	width:'100',
	        	height:'350',
	        	legendData:legendData,
	        	seriesData:seriesData,
				clickfn: function(param){
					console.info(param);
				}
	        });
         },this);
    	
    },
    
    xueyuanUpdate : function(){
    	this.callService({key:'getXyByYear',params:this.params},function(data){
    	 	var legendData = [];
		    	var seriesData = [];
		    	if(data.getXyByYear){
					for(var i =0; i<data.getXyByYear.length;i++){
						var obj = data.getXyByYear[i];
						legendData.push(obj.name);
						seriesData.push({value:obj.value, name: obj.name});
					}
		    	}
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	pieUtils.createPie({
	        	divId : 'xueyuanPie',
	        	title:'毕业院校分布',
	        	width:'100',
	        	height:'350',
	        	legendData:legendData,
	        	seriesData:seriesData,
				clickfn: function(param){
					console.info(param);
				}
	        });
         },this);
    },
    
    ageScatterUpdate : function(){
    	var me = this;
    	this.callService({key:'getAgeScatterByYear',params:this.params},function(data){
    		if(data.getAgeScatterByYear){
    			var manData = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0];
    			var wowenData = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0];
    			var noData = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0];
    			var xData = [];
    			for(var i = 0;i< data.getAgeScatterByYear.length; i++){
    				var obj = data.getAgeScatterByYear[i];
    				var num = 1;
    				if(obj.AGE<=20){// 20岁以下
    					if(obj.MC=='男'){
	    					manData[0] = manData[0] + 1;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[0] = wowenData[0] + num;
	    				}
	    				if(obj.MC=='未维护'){
	    					noData[0] = noData[0] + num;
	    				}
    				}else if(obj.AGE> 20 && obj.AGE <= 25){ // 20 - 25岁
						if(obj.MC=='男'){
	    					manData[1] = manData[1] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[1] = wowenData[1] + num;
	    				}
	    				if(obj.MC=='未维护'){
	    					noData[1] = noData[1] + num;
	    				}
    				}else if(obj.AGE> 25 && obj.AGE <= 30){ // 20 - 30岁
    					if(obj.MC=='男'){
	    					manData[2] = manData[2] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[2] = wowenData[2] + num;
	    				}
    					if(obj.MC=='未维护'){
	    					noData[2] = noData[2] + num;
	    				}
    				}else if(obj.AGE> 30 && obj.AGE <= 35){ // 30 - 35岁
    					if(obj.MC=='男'){
	    					manData[3] = manData[3] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[3] = wowenData[3] + num;
	    				}
	    				if(obj.MC=='未维护'){
	    					noData[3] = noData[3] + num;
	    				}
    				}else if(obj.AGE> 35 && obj.AGE <= 40){ // 35 - 40岁
    					if(obj.MC=='男'){
	    					manData[4] = manData[4] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[4] = wowenData[4] + num;
	    				}
    					if(obj.MC=='未维护'){
	    					noData[4] = noData[4] + num;
	    				}
    				}else if(obj.AGE> 40 && obj.AGE <= 45){ // 40 - 45岁
    					if(obj.MC=='男'){
	    					manData[5] = manData[5] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[5] = wowenData[5] + num;
	    				}
    					if(obj.MC=='未维护'){
	    					noData[5] = noData[5] + num;
	    				}
    				}else if(obj.AGE> 45 && obj.AGE <= 50){ // 45 - 50岁
    					if(obj.MC=='男'){
	    					manData[6] = manData[6] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[6] = wowenData[6] + num;
	    				}
    					if(obj.MC=='未维护'){
	    					noData[6] = noData[6] + num;
	    				}
    				}else if(obj.AGE> 50 && obj.AGE <= 55){ // 50 - 55岁
    					if(obj.MC=='男'){
	    					manData[7] = manData[7] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[7] = wowenData[7] + num;
	    				}
    					if(obj.MC=='未维护'){
	    					noData[7] = noData[7] + num;
	    				}
    				}else if(obj.AGE> 55 && obj.AGE <= 60){ // 55 - 60岁
    					if(obj.MC=='男'){
	    					manData[8] = manData[8] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[8] = wowenData[8] + num;
	    				}
    					if(obj.MC=='未维护'){
	    					noData[8] = noData[8] + num;
	    				}
    				}else if(obj.AGE> 60){  // 60岁以上
    					if(obj.MC=='男'){
	    					manData[9] = manData[9] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[9] = wowenData[9] + num;
	    				}
	    				if(obj.MC=='未维护'){
	    					noData[9] = noData[9] + num;
	    				}
    				}else{
    					if(obj.MC=='男'){
	    					manData[10] = manData[10] + num;
	    				}
	    				if(obj.MC=='女'){
	    					wowenData[10] = wowenData[10] + num;
	    				}
	    				if(obj.MC=='未维护'){
	    					noData[10] = noData[10] + num;
	    				}
    				}
    			}
	    		var	option = {
				    title : {
				    	show:false,
				        text: '高产作者年龄分布'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:['男性','女性','未维护']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            saveAsImage : {show: true}
				        }
				    },
				    xAxis : [
				        {
				            type : 'category',
				            data : ['20岁以下','20-25岁','25-30岁','30-35岁','35-40岁','40-45岁','45-50岁','50-55岁','55-60岁','60岁以上','未维护']
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value'
				        }
				    ],
				    series : [
				        {
				            name:'男性',
				            type:'bar',
				            data:manData
				        },
				        {
				            name:'女性',
				            type:'bar',
				            data:wowenData
				        },
				        {
				            name:'未维护',
				            type:'bar',
				            data:noData
				        }
				    ]
				};
				var clickfn = function(params){
					me.lwSexClickFn(params);
				}
				var divElement = document.getElementById("ageScatter");
				divElement.style.width = '100%';
				divElement.style.height = '500px';
				var pieEcharts = echarts.init(divElement, 'blue');
				pieEcharts.setOption(option);
				pieEcharts.on(echarts.config.EVENT.CLICK, clickfn);
    		}
         },this);
    	
    },
    
    lwSexClickFn : function(params){
    	var me = this;
    	var age = params.name;
    	var sex = params.seriesName;
        var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
        var params = {sex :sex, age:age, start:0,limit:15};
        params = Ext.apply(this.params,params);
    	title=name + sex +"高产作者";
    	gridFields=['MC','XM','VALUE','LWNUM','PEOPLE_ID'];
    	textarrays=['作者单位','作者姓名','学位','论文数','作者id'];
    	widtharrays=[200,150,100,80,80];
    	hiddenarrays=[false,false,false,false,true];
        this.callService({key:'getTableLwSexPage',params:params},function(respData){
            var data = respData.getTableLwSexPage;
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getTableLwSexPage',false,true,15);
            
            var jzgname = new NS.form.field.Text({
                width:240,
                fieldLabel:'作者姓名',
                labelWidth:80,
                emptyText:'作者姓名'
            });
            var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({jzgname:jzgname.getValue()});
					}
			};
            // 未填写教学日志导出
            var tbar = new NS.toolbar.Toolbar({
                items:[jzgname,butt,'->']
            });
            this.win = new NS.window.Window({
                title:age+title+'名单',
                layout:'fit',
                modal:true,
                width:830,
                height:480,
                tbar:tbar,
                items:dxgrid
            });
            this.win.show();
           dxgrid.bindItemsEvent({
                'LWNUM' :{event:'linkclick',fn:me.winPage2,scope:{me:me}}
            });
        });
    },
    
    lngczzsbhjs : function(){
    	var me = this;
    	this.callService({key:'getLngczzsbhjsByYear',params:this.params},function(data){
    		var result = data.getLngczzsbhjsByYear;
    		if(result){
				var	option = {
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					        data:['发表三篇','发表四篇','发表五篇及以上']
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            saveAsImage : {show: true}
					        }
					    },
					  	dataZoom: {
					        show: true,
					        start : 20,
					        end : 80
					    },
					    xAxis : [
					        {
					            type : 'category',
					            boundaryGap : true,
					            data :function (){
					                var list = [];
					              	for(var i=0; i<result.xData.length;i++){
					                	list.push(result.xData[i])
					                }
					                return list;
					            }() 
					        }
					    ],
					    yAxis : [
					        {
					            type : 'value'
					        }
					    ],
					    series : [
					        {
					            name:'发表三篇',
					            type:'line',
					            stack: '总量',
					            data:result.threeData
					        },
					        {
					            name:'发表四篇',
					            type:'line',
					            stack: '总量',
					            data:result.fourData
					        },
					        {
					            name:'发表五篇及以上',
					            type:'line',
					            stack: '总量',
					            data:result.fiveData
					        }
					    ]
					};
				var clickfn = function(params){
					me.lngczzClickFn(params);
				}
				var divElement = document.getElementById("lngczzMain");
				divElement.style.width = '100%';
				divElement.style.height = '500px';
				var pieEcharts = echarts.init(divElement, 'blue');
				pieEcharts.setOption(option);
				pieEcharts.on(echarts.config.EVENT.CLICK, clickfn);
    		}
         },this);
    },
    
    lngczzClickFn : function(params){
    	var me = this;
    	var year = params.name;
    	var name = params.seriesName;
        var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
        var params = {typeName :name, start:0,limit:15};
        this.params.year = year;
        params = Ext.apply(this.params,params);
    	title=name+"高产作者";
    	gridFields=['MC','XM','VALUE','LWNUM', 'PEOPLE_ID'];
    	textarrays=['作者单位','作者姓名','学位','论文数','作者id'];
    	widtharrays=[200,150,100,80, 80];
    	hiddenarrays=[false,false,false,false,true];
        this.callService({key:'getTableLngczzPage',params:params},function(respData){
            var data = respData.getTableLngczzPage;
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getTableLngczzPage',false,true,15);
            var jzgname = new NS.form.field.Text({
                width:240,
                fieldLabel:'作者姓名',
                labelWidth:80,
                emptyText:'作者姓名'
            });
            var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({jzgname:jzgname.getValue()});
					}
			};
            // 未填写教学日志导出
            var tbar = new NS.toolbar.Toolbar({
                items:[jzgname,butt,'->']
            });
            this.win = new NS.window.Window({
                title:title+'名单',
                layout:'fit',
                modal:true,
                width:830,
                height:480,
                tbar:tbar,
                items:dxgrid
            });
            this.win.show();
           dxgrid.bindItemsEvent({
                'LWNUM' :{event:'linkclick',fn:me.winPage2,scope:{me:me}}
            });            
        });
    },
    
    title1 : new Exp.chart.PicAndInfo({
        title : "高产作者分类",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "高产作者分布",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    
    title3 : new Exp.chart.PicAndInfo({
        title : "历年高产作者数变化趋势",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    
	/**
	 * 创建中部容器组件。
	 */
    createMain:function(){
       	this.createCombobox();
        var xnxqContainer = new Ext.container.Container({
            layout:"column",
            margin:'5 0 0 10',
            items:[this.combobox]
        });
       
        var containerx = new Ext.container.Container({
            items:[xnxqContainer]
        });
        return containerx;
    },
     /**
     * 初始化Grid
     */
    initXqGrid : function(data,fields,columnConfig,queryParams,serviceKey,multiSelec,paging,pagesize){
        var lineNumber = Boolean(multiSelec)==true?false:true;
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : pagesize||100,
            proxy : this.model,
            serviceKey:{
                key:serviceKey,
                params:queryParams
            },
            multiSelect: multiSelec||false,
            lineNumber:lineNumber,
            fields : fields,
            columnConfig :columnConfig,
            border: false,
            checked: multiSelec||false,
            paging:paging||false
        });
        return grid;
    },
        getColumnCfgsBzxmd:function(gridFields,textarrays,widtharrays,hiddenarrays){
        var columns = [];
        for(var i=0;i<gridFields.length;i++){
            var basic = {
                xtype : 'column',
                name : gridFields[i],
                text : textarrays[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            switch(gridFields[i]){
            case 'LWNUM':
                Ext.apply(basic,{
                    xtype : 'linkcolumn',
                    renderer:function(data){
                        return '<a href="javascript:void(0);">'+data+'</a>';
                    }
                });
                break;
            default:
                break;
            }
            columns.push(basic);
        }
        return columns;
    },
    // 创建一个下拉列表
    createCombobox:function(){
    	var systemYear = new Date().getFullYear();
    	var year = systemYear;
    	var dataArr = [];
    	for(var i = 0; i < 10;i++){
    		dataArr.push({id:year, mc:year+'年'});
    		year--;
    	}
        var states = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : dataArr
        });
        var combobox = this.combobox = new Ext.form.ComboBox({
            width:150,
            labelWidth:60,
            store: states,
            fieldLabel:'选择年份',
            queryMode: 'local',
            displayField: 'mc',
            margin:'10 0 0 0',
            columnWidth:0.5,
            valueField: 'id'
        });
        //把默认值赋值给下来菜单
        combobox.setValue(systemYear);
        //更改值的时候，触发的方法。
        combobox.on('change',function(){
        	this.params.year = combobox.getValue();
        	this.updateTotal();
        	this.updatePie();
        },this);
    }
});