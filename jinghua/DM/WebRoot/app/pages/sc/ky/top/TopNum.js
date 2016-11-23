NS.define('Pages.sc.ky.top.TopNum',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            queryYxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据，宿舍组织结构
            querySsRzData:'sstjService?getSsRzData',
            getPeopleTypeByPie:'kyTopService?getPeopleTypeByPie',
            queryGridContent: {
                service:'kyTopService?getTopNum',
                params:{
                    limit:10,
                    start:0
                }
            },
            getDataMd :  {
                service:'kyTopService?getDataList',
                params:{
                    limit:15,
                    start:0
                }
            }
            
        }
    },
    tplRequires : [],
    cssRequires : ['app/pages/zksf/css/base.css','app/pages/zksf/css/table.css','app/pages/sc/ky/lw/lwcss/lw.css'],
    mixins:['Pages.sc.Scgj'],
    requires:['Pages.sc.common.Pages','Pages.sc.ky.pie.PieDisplay'],
    params:{},
    pageParam:{limit:10,start:0},
    showYx:false,
    loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
    init: function () {
        var me = this;
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'科研成果数量历年Top10',
                pageHelpInfo:'通过成果总数、科研项目、论文、专利、著作多角度查看历年科研成果数量最高人员的前10名次'}
        });
        var simpleDate = this.simpleDate = new Exp.component.SimpleYear({
            start:1900,
            defaultDate:'bz',
            margin:'0 0 0 10'
        });
        
        
        var container = new Ext.container.Container({
            cls:'student-sta-titlediv',
            layout:{
                type:'hbox',
                align:'middle'
            },
            height:40,
            margin:'5 0 5 0',
            items:[simpleDate,new Ext.Component({
                html:'<span style="margin-left: 20px;height: 26px;line-height: 26px;color: #777;">☞选择开始年份和结束年份，统计该年份内的科研成果信息。</span>'
            })]
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var containerx = this.mainContainer = this.createMain();

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,navigation,container,containerx]
        });
        this.setPageComponent(container);
        container.on('afterrender',function(){
	        // 刷新导航栏
	        this.callService('queryYxzzjgTree',function(data){
	            this.navigation.refreshTpl(data.queryYxzzjgTree);
	            var i = 0;
	            for(var key in data.queryYxzzjgTree){
	                if(i==0){
	                    var nodeId = data.queryYxzzjgTree[key].id;
	                    this.navigation.setValue(nodeId);
	                    this.params.zzjgId = nodeId;
	                    this.showYx=true;
	                }
	                i++;
	            }
	            this.initParams();
	            this.fillCompoByData();
	            this.bindEvents();
	        },this);
	
	        navigation.on('click',function(){
	            var data = this.getValue(),len = data.nodes.length;
	            me.params.zzjgId = data.nodes[len-1].id;
	            me.params.zzjgmc = data.nodes[len-1].mc;
	            if(me.params.zzjgId==0){
	            	me.showYx=true;
	            }else{
	            	me.showYx=false;
	            }
	            me.fillCompoByData();
	        });
	        simpleDate.on('validatepass',function(){
	            var data = this.getValue();
	            me.params.from = data.from;
	            me.params.to = data.to;
	            me.fillCompoByData();
	        });
        },this);
    },
    /**
     * 创建主题容器
     */
    mainTable :  new Ext.Component({
		tpl : '<div><table class="table1"><thead>'+
				'<tr><th rowspan="2">序号</th>'+
				'<th rowspan="2">名次</th>'+
				'<th rowspan="2">职工号</th>'+
				'<th rowspan="2">职工名称</th>'+
				'<th rowspan="2">部门名称</th>'+
				'<th colspan="2">成果</th>'+
				'<th colspan="2">论文</th>'+
				'<th colspan="2">科研项目</th>'+
				'<th colspan="2">专利</th>'+
				'<th colspan="2">著作</th></tr>'+
				'<tr><th>总数</th><th>平均每年数</th>'+
				'<th>总数</th><th>平均每年数</th>'+
				'<th>总数</th><th>平均每年数</th>'+
				'<th>总数</th><th>平均每年数</th>'+
				'<th>总数</th><th>平均每年数</th></tr>'+
			'</thead><tbody><tpl for="."><tr>'+
				'<td>{RSNUM}</td>'+
			    '<td>{RANK_}</td>'+
				'<td>{TEA_NO}</td>'+
				'<td>{TEA_NAME}</td>'+
				'<td>{DEPT_NAME}</td>'+
				'<td>{CGS}</td>'+
				'<td>{CGPS}</td>'+
				'<td><a href="javascript:void(0);" tea_no="{TEA_NO}" show_type="lws" >{LWS}</a></td>'+
				'<td>{LWPS}</td>'+
				'<td><a href="javascript:void(0);" tea_no="{TEA_NO}" show_type="xms" >{XMS}</a></td>'+
				'<td>{XMPS}</td>'+
				'<td><a href="javascript:void(0);" tea_no="{TEA_NO}" show_type="zls" >{ZLS}</a></td>'+
				'<td>{ZLPS}</td>'+
				'<td><a href="javascript:void(0);" tea_no="{TEA_NO}" show_type="zzs" >{ZZS}</a></td>'+
				'<td>{ZZPS}</td>'+
			'</tr></tpl>'+
			'</tbody></table></div><div id="main_table_fy"></div>',
		data : [],
		margin : '0px 10px 10px 0px'
	}),

	topType : new Ext.Component({
		 html:"<div style='height:35px;' class='list-line'><div class='list-tab'>" +
			"<a href='javascript:void(0)' class='list-selected topTypeClass' show_type='cgs' >所有成果</a>" +
			"<a href='javascript:void(0)' class='list-default topTypeClass' show_type='lws' >论文</a>" +
			"<a href='javascript:void(0)' class='list-default topTypeClass' show_type='xms' >科研项目</a>" +
			"<a href='javascript:void(0)' class='list-default topTypeClass' show_type='zls' >专利</a>" +
			"<a href='javascript:void(0)' class='list-default topTypeClass' show_type='zzs' >著作</a>" +
			"</div></div>",
		 margin : '0px 10px 10px 0px'
		 
	}),
	showPieText : new Ext.Component({
		 html:"<div><a id='showPieTextId' href='javascript:void(0);' showhide='hide' >查看该类人群的类型分布</a></div>",
		 margin : '0px 10px 10px 0px'
		 
	}),
	pieText : new Ext.Component({
		 html:"<div style='width:100%;height:350PX'><div id='xueWeiPieTop' class='echartsTop' style='float: left;width:33.3%;height:350PX''>" +
		 		"</div><div id='wenHuaChengDuPieTop' class='echartsTop' style='float: left;width:33.3%;height:350PX''>" +
		 		"</div><div id='zhichengPieTop' class='echartsTop' style='float: left;width:33.3%;height:350PX''></div></div>"+
				"<div id='danWeiTopDivId' >" +
				"<div style='margin:10px 10px 10px 0px;' class='x-component x-component-default' id='ext-comp-1080'>" +
				"<div style='margin-top:5px;'><span style='border:0px;border-left: 4px solid #3196fe;padding:0px 0px 0px 5px;color: #265efd;font-weight: bold;font-size: 16px;'>各单位人员分布</span>" +
				"<hr color='#5299eb' style='margin-top: 5px;'></div></div>"+
				"<div id='danWeiBarTop' class='echartsTop' style='float: left;width:100%;height:400PX'></div>" +
				"</div>",
				
		 margin : '0px 10px 10px 0px'
		 
	}),
    createMain:function(){
    	var me = this;
    	this.pieText.hide();
        var container = new Ext.container.Container({
            items:[me.topType,me.mainTable,me.showPieText,me.pieText]
        });
        return container;
    },
    /**
     * 数据填充组件
     */
    fillCompoByData:function(){
    	this.pageParam.start=0;
    	this.mainTableData();
    	this.updatePie();
    	if(this.showYx){
			$("#danWeiTopDivId").show();
		}else{
			$("#danWeiTopDivId").hide();
		}
    },
    initParams:function(){
        var nfdata = this.simpleDate.getValue();
        var navdata = this.navigation.getValue();
        this.params.from = nfdata.from;
        this.params.to = nfdata.to;
        this.params.zzjgId = navdata.nodes[0].id;
        this.params.kyt='cgs';
        this.params.people_type='num';
        this.params.rank=10;
    },
    updatePie : function(){
    	$(".echartsTop").html(this.loadingHtml);
    	this.params.people_count="count";
    	this.params.group_type='whcd';
     	this.wenHuaChengDuUpdate();
     	this.params.group_type='xw';
       	this.xueWeiUpdate();
       	this.params.group_type='zyjszw';
       	this.zhichengUpdate();
       	this.params.group_type='dwfb';
       	this.danWeiUpdate();
    },
    xueWeiUpdate : function(){
    	var me = this;
    	this.callService({key:'getPeopleTypeByPie',params:this.params},function(data){
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	var d = data.getPeopleTypeByPie;
    	 	var xdata = [];
    	 	var ydata = [];
    	 	for(var i=0;i<d.length;i++){
    	 		xdata.push(d[i].NAME_);
    	 		var item = {name : d[i].NAME_,value :d[i].COU};
    	 		ydata.push(item);
    	 	}
    	 	pieUtils.createPie({
	        	divId : 'xueWeiPieTop',
	        	title:'人员的学位分布',
	        	width:'33.3',
	        	height:'350',
	        	legendData:xdata,
	        	seriesData:ydata,
				clickfn: function(param){
					me.winPeoplePage('xw',param.name);
				}
	        });
        },this);
    	
    },
    
    wenHuaChengDuUpdate : function(){
    	var me = this;
		  this.callService({key:'getPeopleTypeByPie',params:this.params},function(data){
		 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
		 	var d = data.getPeopleTypeByPie;
		 	var xdata = [];
		 	var ydata = [];
		 	for(var i=0;i<d.length;i++){
		 		xdata.push(d[i].NAME_);
		 		var item = {name : d[i].NAME_,value :d[i].COU};
		 		ydata.push(item);
		 	}
		 	pieUtils.createPie({
	        	divId : 'wenHuaChengDuPieTop',
	        	title:'人员的文化程度分布',
	        	width:'33.3',
	        	height:'350',
	        	legendData:xdata,
	        	seriesData:ydata,
				clickfn: function(param){
					me.winPeoplePage('whcd',param.name);
				}
	        });
	     },this);
    	
    },
    
    zhichengUpdate : function(){
    	var me = this;
    	this.callService({key:'getPeopleTypeByPie',params:this.params},function(data){
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	var d = data.getPeopleTypeByPie;
    	 	var xdata = [];
    	 	ydata = [];
    	 	for(var i=0;i<d.length;i++){
    	 		xdata.push(d[i].NAME_);
    	 		var item = {name : d[i].NAME_,value :d[i].COU};
    	 		ydata.push(item);
    	 	}
    	 	pieUtils.createPie({
	        	divId : 'zhichengPieTop',
	        	title:'人员的职称分布',
	        	width:'33.3',
	        	height:'350',
	        	legendData:xdata,
	        	seriesData:ydata,
				clickfn: function(param){
					me.winPeoplePage('zyjszw',param.name);
				}
	        });
         },this);
    	
    },
    
    danWeiUpdate : function(){
    	var me = this;
    	this.callService({key:'getPeopleTypeByPie',params:this.params},function(data){
    		var d = data.getPeopleTypeByPie;
    		var xdata = [],ydata = [];
    	 	for(var i=0;i<d.length;i++){
    	 		xdata.push(d[i].NAME_);
    	 		ydata.push(d[i].COU);
    	 	}
			option = {
			    tooltip : {
			        trigger: 'axis', 
			        axisPointer : {            
			            type : 'shadow'        
			        }
			    },
			    legend: {
			    	show:false,
			    	orient : 'horizontal',
					x : 'center',
					y:'10%',
			        data:['人数']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            restore : {show: false},
			            saveAsImage : {show: true}
			        }
			    },
			    dataZoom : {
			        show : true,
			        realtime : true,
			        start : 0,
			        end : 50
			    },
			    noDataLoadingOption:{
			    	text :"各单位人员分布\n暂无数据",
			        effect : 'bubble',
			        effectOption:{effect: {n: 0}},
			        textStyle : {
			            fontSize : 20
			        }
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : true,
			            data : function (){
			                var list = [];
			                for (var i = 0; i < xdata.length; i++) {
			                    list.push(xdata[i]);
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
			            name:'人数',
			            type:'bar',
			            itemStyle: {
			                normal: {
			                    label: {
			                        show: true,
			                        position: 'top',
			                        formatter: '{c}人'
			                    }
			                }
			            },
			            data:ydata
			        }
			    ]
			};
            var divElement = document.getElementById("danWeiBarTop");
			var barEcharts = echarts.init(divElement,'blue');
			barEcharts.setOption(option);
			barEcharts.on(echarts.config.EVENT.CLICK, function(param){
				me.winPeoplePage('dwfb',param.name);
			});
    		
    	})
    },
    
    mainTablePage:function(divId,callback){
    	var page=new Pages.sc.common.Pages({pageParam:this.pageParam,callBack:callback,scope:this}).getComponent();
    	page.render(divId);
    },
    
    
    mainTableData:function(){
    	var me =this;
    	this.params.limit=this.pageParam.limit;
    	this.params.start=this.pageParam.start;
    	this.callService([{key:'queryGridContent',params:this.params}],
            function(respData){
    			 me.mainTable.update(respData.queryGridContent.data);
	    		 me.pageParam.recordCount=respData.queryGridContent.count;
	    		 me.mainTablePage("main_table_fy",me.mainTableData);
 			});
    },
    bindEvents : function(){
    	var me = this;
        $('#'+me.mainTable.id).on('click',function(evt){
        	var tar = evt.target;
	    	if(tar.tagName == 'A'){
	    		var tea_no=$(tar).attr("tea_no");
	    		var type=$(tar).attr("show_type");
	    		if(type != undefined){
	    			me.winPage(tea_no,type);
	    		}
	    	}
	   });
        $(".topTypeClass").on('click',function(evt){
        	var tar = evt.target;
        	$.each($(".topTypeClass"),function(i,o){
        		if($(o).attr("class").indexOf("list-selected")>=0){
        			$(o).toggleClass("list-selected list-default");
        		}
        	});
        	$(tar).toggleClass("list-selected list-default");
        	me.pageParam.start=0;
        	me.params.kyt=$(tar).attr("show_type");
        	me.fillCompoByData();
        });
        $("#showPieTextId").on('click',function(evt){
        	var tar = evt.target;
        	var $tar= $(tar);
        	if($(tar).attr("showhide")=='hide'){
        		$(tar).attr("showhide","show");
        		$(tar).html("隐藏该类人群的类型分布");
        		me.pieText.show();
        		me.pieText.show();
        		if(me.showYx){
        			$("#danWeiTopDivId").show();
        		}else{
        			$("#danWeiTopDivId").hide();
        		}
            	me.updatePie();
        	}else{
        		me.pieText.hide();
        		$(tar).attr("showhide","hide");
        		$(tar).html("查看该类人群的类型分布");
        		
        	}
        	
        });
        
    },
    
    winPeoplePage : function(group_type,wherename){
    	var me = this;
    	me.params.people_count="list";
		me.params.group_type=group_type;
		me.params.people_list_type=wherename;
        var params = {start:0,limit:15};
        params = Ext.apply(this.params,params);
        var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
        gridFields="ZGH,XM,MC".split(",");
    	textarrays="职工号,姓名,院系".split(",");
    	widtharrays=[200,100,200];
    	hiddenarrays=[false,false,false];
       	
        this.callService({key:'getPeopleTypeByPie',params:params},function(respData){
            var data = respData.getPeopleTypeByPie;
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getPeopleTypeByPie',false,true,15);
            var exportBtn1 = new NS.button.Button({
                text : "导出名单",
                handler : function(){
                    me.exportPeopleMd(params);
                },
                iconCls : "page-excel",
                border:true
            });
            var jzgxm = new NS.form.field.Text({
                width:240,
                fieldLabel:'教职工姓名',
                labelWidth:80,
                emptyText:'输入姓名检索'
            });
            var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({where_xm:jzgxm.getValue()});
					}
				};
            var tbar = new NS.toolbar.Toolbar({
                items:[jzgxm,butt,'->',exportBtn1]
            });
            this.win = new NS.window.Window({
                title:wherename+'的人员名单',
                layout:'fit',
                modal:true,
                width:780,
                height:480,
                tbar:tbar,
                items:dxgrid
            });
            this.win.show();

        });
    },
    
    winPage : function(tea_no,show_type){
    	var me = this;
        var params = {tea_no :tea_no,show_type:show_type,start:0,limit:15};
        params = Ext.apply(this.params,params);
        var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
        if(show_type=='lws'){
        	title="论文";
        	gridFields="TITLE_,AUTHORS,FOR_DEPT_NAME,PERIODICAL,NJQY,TYPE_NAME,PROJECT_NAME,REMARK".split(",");
        	textarrays="论文名称,参与人员,所属单位,发表期刊,年卷期页,期刊类别,所属学科门类,备注".split(",");
        	widtharrays=[200,100,150,200,100,100,80,80,100];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false];
        }else if(show_type=='xms'){
        	title="科研项目";
        	gridFields="PRO_NO,NAME_,FOR_DEPT_NAME,CATEGORY,LEVEL_NAME,RANK_NAME,ISSUED_DEPT,TEAMWORK_DEPT,START_TIME,END_TIME,FUND,STATE_NAME,PROJECT_NAME,REMARK".split(",");
        	textarrays="项目编号,项目名称,所属单位,项目类别,项目级别,项目等级,下达部门,合作单位,开始时间,结束时间,经费(万元),状态,所属学科门类,备注".split(",");
        	widtharrays=[100,200,150,80,80,80,150,150,80,80,80,80,80,150];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false,false,false,false,false];
        }else if(show_type=='zls'){
        	title="专利";
        	gridFields="PATENT_NO,NAME_,INVENTORS,FOR_DEPT_NAME,TYPE_NAME,STATE_NAME,ACCEPT_TIME,ACCREDIT_TIME,PROJECT_NAME,REMARK".split(",");
        	textarrays="专利编号,专利名称,参与人员,所属单位,专利类型,专利实施状态,受理日,授权日,所属学科门类,备注".split(",");
        	widtharrays=[80,200,100,150,100,80,80,80,100,100];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false];
        }else{
        	title="著作";
        	gridFields="WORK_NO,TITLE_,AUTHORS,FOR_DEPT_NAME,PRESS_NAME,PRESS_TIME,NUMBER_,PROJECT_NAME,REMARK".split(",");
        	textarrays="著作编号,著作名称,参与人员,所属单位,出版单位,出版时间,著作字数,所属学科门类,备注".split(",");
        	widtharrays=[80,200,100,150,200,100,100,80,80,100];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false];
        }
        this.callService({key:'getDataMd',params:params},function(respData){
            var data = respData.getDataMd;
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getDataMd',false,true,15);
            var exportBtn1 = new NS.button.Button({
                text : "导出名单",
                handler : function(){
                    me.exportMd(params);
                },
                iconCls : "page-excel",
                border:true
            });
            var cgxm = new NS.form.field.Text({
                width:240,
                fieldLabel:'成果名称',
                labelWidth:80,
                emptyText:'输入名称检索'
            });
            var dataStroe=function(){
            	var arrs=[],to = params.to;
            	var start=params.from;
            	if(to!=start){
            		arrs.push({id:"",mc:start+"年"+"---"+to+"年"})
            	}
                for(;start<=to;start++){
                    arrs.push({id:start,mc:start+"年"});
                }
                return arrs;
            };
            var year_ = new NS.form.field.ComboBox({
                width:240,
                labelWidth:60,
                style: {"float": 'left'},
                fieldLabel:'年份',
                editable:false,
                value:'',
                data:dataStroe()
            	});
            var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({where_cgxm:cgxm.getValue(),where_year:year_.getValue()});
					}
				};
            var tbar = new NS.toolbar.Toolbar({
                items:[cgxm,year_,butt,'->',exportBtn1]
            });
            this.win = new NS.window.Window({
                title:title+'名单',
                layout:'fit',
                modal:true,
                width:780,
                height:480,
                tbar:tbar,
                items:dxgrid
            });
            this.win.show();

        });
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
    exportMd : function(queryGridParams){
        // 导出
        var serviceAndParams ={
            servicAndMethod:'kyTopService?getDataMdExport',
            params:queryGridParams
        } ;
        var strParams = JSON.stringify(serviceAndParams);
        window.document.open("customExportAction.action?serviceAndParams="+strParams,
            '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
    },
    
    exportPeopleMd : function(queryGridParams){
        // 导出
        var serviceAndParams ={
            servicAndMethod:'kyTopService?getPeopleExport',
            params:queryGridParams
        } ;
        var strParams = JSON.stringify(serviceAndParams);
        window.document.open("customExportAction.action?serviceAndParams="+strParams,
            '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
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
            columns.push(basic);
        }
        return columns;
    }
    
});