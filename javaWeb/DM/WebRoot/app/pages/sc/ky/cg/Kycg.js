NS.define('Pages.sc.ky.cg.Kycg',{
	extend:'Template.Page',
	tplRequires : [],
	modelConfig: {
        serviceConfig: {
            queryYxzzjgTree: 'scService?getYxzzjgTree',// 获取导航栏数据，教学组织结构
            getZcFb:'scientificAchievementService?getZcFb',//获取职称分布
            getXwFb:'scientificAchievementService?getXwFb',
            getWhcdFb:'scientificAchievementService?getWhcdFb',
            getDwFb:'scientificAchievementService?getDwFb',
            getZcMxFb:'scientificAchievementService?getZcMxFb',
            getTable : 'scientificAchievementService?getTable',
            getTable2 : 'scientificAchievementService?getTable2'
            
        }
    },
    cssRequires : [],
    mixins:['Pages.sc.Scgj'],
    requires:['Pages.sc.ky.pie.PieDisplay'],
    params:{},
    loadingHtml : "<div class='loading-indicator'>正在加载....</div>",
    init: function() {
    	 var me = this;
         var pageTitle = new Exp.component.PageTitle({
             data:{
                 pageName:'各单位科研成果分析',
                 pageHelpInfo:'各单位科研成果（包括获奖成果，鉴定成果，著作，专利）的人员信息分布情况'}
         });
         var navigation = this.navigation = new Exp.component.Navigation();
         var containerx = this.mainContainer = this.createMain();
         var simpleDate = this.simpleDate = new Exp.component.SimpleYear({
            start:1900,
            margin:'0 0 0 10'
        });
        var hl = "<div style='width: 100%; height: 550px;'><div id='xueWeiPie' style='float: left;width: 33.3%; height: 550px;'></div><div id='wenHuaChengDuPie' style='float: left;width: 33.3%; height: 550px;'></div>" +
        		"<div id='zhichengPie' style='float:left;width: 33.3%; height: 550px;'></div><div id='xiazuan'  style='display:none;'>" +
        		"<a style='float:right;' href='javascript:void(0);' onclick='back();'>返回上一层</a>" +
        		"<div id='zhichengMingXiPie' style='float:left;'></div></div></div>";
        var h2 = "<div id='danWeiBar' style='float:left;'></div>";
		var htmlElement = this.component = new NS.Component({
  			margin : "10px 10px 10px 0px",
			html : hl
		});
		var htmlElement2 = this.component = new NS.Component({
  			margin : "10px 10px 10px 0px",
			html : h2
		});
		back = function(){
			$("#zhichengPie").html(me.loadingHtml);
			me.zhichengUpdate();
			document.getElementById("zhichengPie").style.display = '';
			document.getElementById("xiazuan").style.display = 'none';
		};
        var container = new Ext.container.Container({
            cls:'student-sta-titlediv',
            layout:{
                type:'hbox',
                align:'middle'
            },
            height:40,
            margin:'5 0 5 0',
            items:[simpleDate,new Ext.Component({
                html:'<span style="margin-left: 20px;height: 26px;line-height: 26px;color: #777;">☞选择开始年份和结束年份，统计该年份内的科研成果人员分布。</span>'
            })]
        });

        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,navigation,container,containerx,this.title1,htmlElement,this.title2,htmlElement2]
        });
        this.setPageComponent(container);
        this.callService('queryYxzzjgTree',function(data){
            this.navigation.refreshTpl(data.queryYxzzjgTree);
            var i = 0;
            for(var key in data.queryYxzzjgTree){
                if(i==0){
                    var nodeId = data.queryYxzzjgTree[key].id;
                    this.navigation.setValue(nodeId);
                    this.params.zzjgId = nodeId;
                }
                i++;
            }
            this.initParams();
            this.updatePie();
            this.updateBar();
        },this);

        navigation.on('click',function(){
            var data = this.getValue(),len = data.nodes.length;
            me.params.zzjgId = data.nodes[len-1].id;
            me.params.zzjgmc = data.nodes[len-1].mc;
            me.updatePie();
            $("#zhichengMingXiPie").html(me.loadingHtml);
            me.zcmxUpdate();
        });
        simpleDate.on('validatepass',function(){
            var data = this.getValue();
            me.params.from = data.from;
            me.params.to = data.to;
            me.updatePie();
            me.updateBar();
            $("#zhichengMingXiPie").html(me.loadingHtml);
            me.zcmxUpdate();
        });
         
    },
    updatePie : function(){
    	$("#xueWeiPie").html(this.loadingHtml);
    	$("#wenHuaChengDuPie").html(this.loadingHtml);
    	$("#zhichengPie").html(this.loadingHtml);
     	this.wenHuaChengDuUpdate();
       	this.xueWeiUpdate();
       	this.zhichengUpdate();
    },
    updateBar : function(){
    	$("#danWeiBar").html(this.loadingHtml);
    	this.danWeiUpdate();
    },
    danWeiUpdate : function(){
    	this.callService({key:'getDwFb',params:this.params},function(data){
    		var d = data.getDwFb;
    		var xdata = [];
    		var ydata = [];
    		for(var i=0;i<d.length;i++){
    			xdata.push(d[i].DEPT_NAME);
    			ydata.push(d[i].NUMS);
    		}
			option = {
			    title : {
			        text: '各单位科研人员分布',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['人数']
			    },
			    noDataLoadingOption:{
				    	text :"暂无数据",
				        effect : 'bubble',
				        effectOption:{effect: {n: 0}},
				        textStyle : {
				            fontSize : 20
				        }
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
			            data:ydata
			        }
			    ]
			};
            var divElement = document.getElementById("danWeiBar");
			divElement.style.width =  '100%';
			divElement.style.height = '500px';
			var barEcharts = echarts.init(divElement,'blue');
			barEcharts.setOption(option);
			var me = this;
			barEcharts.on(echarts.config.EVENT.CLICK, function(data){
			  var typecode = 'dw';
			  me.winPage(data.name,typecode);
			})
    	})
    },
    
    xueWeiUpdate : function(){
    
    	this.callService({key:'getXwFb',params:this.params},function(data){
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	var d = data.getXwFb;
    	 	var xdata = [];
    	 	var ydata = [];
    	 	for(var i=0;i<d.length;i++){
    	 		xdata.push(d[i].XW_NAME);
    	 		var item = {name:d[i].XW_NAME,value:d[i].NUMS};
    	 		ydata.push(item);
    	 	}
    	 	var me = this;
    	 	pieUtils.createPie({
	        	divId : 'xueWeiPie',
	        	title:'学位组成',
	        	width:'33.3',
	        	height:'550',
	        	legend_y:400,
	        	legendData:xdata,
	        	seriesData:ydata,
				clickfn: function(param){
					var typecode = 'xw';
					me.winPage(param.name,typecode);
				}
	        });
        },this);
    	
    },
    winPage : function(name,typecode){
    	var me = this;
    	var show_type=me.params.type;
        var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
        var params = {typeName :name,typecode : typecode,type:show_type,start:0,limit:15/*,is_count:false*/};
        params = Ext.apply(this.params,params);
    	title=name+"成果";
    	gridFields="ZGH,JZG_NAME,DEPT_NAME,TYPE_NAME,NUMS".split(",");
    	textarrays="职工号,教职工名字,单位名称,类型,成果数".split(",");
    	widtharrays=[100,100,150,60,100];
    	hiddenarrays=[false,false,false,false,false];
        this.callService({key:'getTable',params:params},function(respData){
            var data = respData.getTable;
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getTable',false,true,15);
            var exportBtn1 = new NS.button.Button({
                text : "导出名单",
                handler : function(){
                    me.exportMd1(params);
                },
                iconCls : "page-excel",
                border:true
            });
            var jzgname = new NS.form.field.Text({
                width:240,
                fieldLabel:'教职工名字',
                labelWidth:80,
                emptyText:'教职工名字'
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
                items:[jzgname,butt,'->',exportBtn1]
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
			dxgrid.bindItemsEvent({
                'NUMS' :{event:'linkclick',fn:me.winPage2,scope:{me:me,typeName:name,typecode : typecode,show_type:show_type}}
            });
        });
    },
     winPage2 : function(text){
    	var me = this.me;
    	var tea_no =this.tea_no;
    	var show_type=this.show_type;
        var params = {tea_no :tea_no,show_type:show_type,start:0,limit:15,zgh:arguments[3].ZGH};
        params = Ext.apply(me.params,params);
        var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
        if(show_type=='专利'){
        	title="专利";
        	gridFields="ZGH,JZG_NAME,DEPT_NAME,PATENT_NAME,INVENTORS,PATENT_DEPT,ACCREDIT_TIME,PATENT_TYPE,PATENT_STATE,PROJECT_NAME,PATENT_NO,CERTIFICATE_NO,".split(",");
        	textarrays="职工号,教职工名字,发明人单位,专利名称,发明人,专利权单位,受理日,专利类型,专利实施状态,学科门类,专利号,证书编号".split(",");
        	widtharrays=[80,80,100,200,150,100,100,100,100,100,100,100];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false,false,false];
        }else if(show_type=='鉴定成果'){
        	title="鉴定成果";
        	gridFields="ZGH,JZG_NAME,DEPT_NAME,TITLE_NAME,AUTHORS,APPRAISAL_DEPT,IDENTIFYMODE,IDENTIFYLEVEL,TIME_,IDENTIFY_NO,IDENTIFY_REGIST_NO,PROJECT_NAME,ORDER_".split(",");
        	textarrays="职工号,教职工名字,完成人单位,成果名称,完成人,鉴定部门,鉴定形式,鉴定水平,鉴定时间,鉴定证号,成果登记号,学科门类,我校排名".split(",");
        	widtharrays=[100,80,150,200,200,100,80,80,80,150,80,80,80];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false,false,false];
        }else if(show_type=='获奖成果'){
        	title="获奖成果";
        	gridFields="ZGH,JZG_NAME,DEPT_NAME,OUTCOME_NAME,PRIZEWINNER,AWARD_NAME,LEVEL_NAME,CATEGORY_NAME,RANK_NAME,PROJECT_NAME,AWARD_DEPT,AWARD_TIME,CERTIFICATE_NO".split(",");
        	textarrays="职工号,教职工名称,获奖人单位,成果名称,获奖人,获奖名称,获奖级别,获奖类别,获奖等级,学科门类,授奖单位,授奖时间,证书编号".split(",");
        	widtharrays=[100,80,150,200,200,150,80,80,80,100,80,80,100];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false,false,false,false];
        }else if(show_type=='科研项目'){
        	title="科研项目";
        	gridFields="ZGH,JZG_NAME,DEPT_NAME,PROJECT_TITLE,COMPERE,PROJECT_DEPT,PRO_NO,CATEGORY,LEVEL_NAME,RANK_NAME,STATE_NAME,PROJECT_NAME,ISSUED_DEPT,TEAMWORD_DEPT,START_TIME,END_TIME,FUND,SETUP_YEAR".split(",");
        	textarrays="职工号,教职工名称,主持人单位,项目名称,主持人,承担单位,项目编号,项目类别,项目级别,项目等级,项目状态,所属学科门类,下达部门,合作单位,开始时间,结束时间,经费数额（万元）,立项年度".split(",");
        	widtharrays=[100,80,150,200,150,80,80,80,80,80,80,80,80,80,80,80,80,80];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false];
        }else if(show_type == '著作'){
        	title="著作";
        	gridFields="ZGH,JZG_NAME,DEPT_NAME,WORK_NAME,AUTHORS,WORK_NO,PRESS_NAME,PRESS_TIME,NUMBER_,PROJECT_NAME,REMARK".split(",");
        	textarrays="职工号,教职工名称,作者单位,著作题目,作者姓名,著作书号,出版单位,出版时间,著作字数,所属学科门类,备注".split(",");
        	widtharrays=[100,80,150,200,150,80,80,80,100,100,100];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false,false];
        }else if(show_type == '发表论文'){
        	title="发表论文";
        	gridFields="ZGH,JZG_NAME,DEPT_NAME,THESIS_TITLE,AUTHORS,THESIS_DEPT,PERIODICAL,YEAR_,NJQY,PERIODICAL_TYPE,PROJECT_NAME,ORDER_".split(",");
        	textarrays="职工号,教职工名称,作者单位,论文题目,作者姓名,论文所属单位,发表期刊,发表年份,年卷期页,期刊类别,所属学科门类,我校排名".split(",");
        	widtharrays=[100,80,150,200,150,150,80,80,100,100,100,80];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false,false,false];
        }else if(show_type == '收录论文'){
        	title="收录论文";
        	gridFields="ZGH,JZG_NAME,DEPT_NAME,THESIS_TITLE,THESIS_DEPT,PERIODICAL,YEAR_,NJQY,ISSN,IMPACT_FACTOR,SCI_ZONE,PERIODICAL_TYPE".split(",");
        	textarrays="职工号,教职工名称,作者单位,论文题目,论文所属单位,发表期刊,收录年份,年卷期页,ISSN,影响因子,SCI分区,收录期刊类别".split(",");
        	widtharrays=[100,80,150,200,150,80,80,80,100,100,100];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false,false,false];
        }else if(show_type == '获奖论文'){
        	title="获奖论文";
        	gridFields="ZGH,JZG_NAME,DEPT_NAME,THESIS_TITLE,THESIS_DEPT,AWARD_NAME,RANK_NAME,AWARD_DEPT,AWARD_TIME,CERTIFICATE_NO".split(",");
        	textarrays="职工号,教职工名称,作者单位,论文题目,论文所属单位,获奖名称,获奖等级,授奖单位,授奖日期,证书编号".split(",");
        	widtharrays=[100,80,150,200,150,80,80,80,100];
        	hiddenarrays=[false,false,false,false,false,false,false,false,false];
        }else if(show_type == '全部'){
        	title="全部";
        	gridFields="ZGH,JZG_NAME,CG_ID,TITLE,KY_TYPE".split(",");
        	textarrays="职工号,教职工名称,成果ID,成果题目,科研类型".split(",");
        	widtharrays=[100,80,200,300,200];
        	hiddenarrays=[false,false,false,false,false];
        }
        me.callService({key:'getTable2',params:params},function(respData){
            var data = respData.getTable2;
            var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
            // 创建详情grid
            var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'getTable2',false,true,15);
            var exportBtn1 = new NS.button.Button({
                text : "导出名单",
                handler : function(){
                    me.exportMd2(params);
                },
                iconCls : "page-excel",
                border:true
            });
            var cgname = new NS.form.field.Text({
                width:240,
                fieldLabel:'成果名称',
                labelWidth:80,
                emptyText:'成果名称'
            });
            var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({cgname:cgname.getValue()});
					}
			};
            // 未填写教学日志导出
            var tbar = new NS.toolbar.Toolbar({
                items:[cgname,butt,'->',exportBtn1]
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
    exportMd1 : function(queryGridParams){
        // 导出
       var serviceAndParams ={
            servicAndMethod:'scientificAchievementService?getData1Export',
            params:queryGridParams
        } ;
        var strParams = JSON.stringify(serviceAndParams);
        window.document.open("customExportAction.action?serviceAndParams="+strParams,
            '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
    },
    exportMd2 : function(queryGridParams){
        // 导出
       var serviceAndParams ={
            servicAndMethod:'scientificAchievementService?getData2Export',
            params:queryGridParams
        } ;
        var strParams = JSON.stringify(serviceAndParams);
        window.document.open("customExportAction.action?serviceAndParams="+strParams,
            '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
    },
    getColumnCfgsBzxmd:function(gridFields,textarrays,widtharrays,hiddenarrays){
    	var me = this;
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
            case 'NUMS':
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
    wenHuaChengDuUpdate : function(){
    	  this.callService({key:'getWhcdFb',params:this.params},function(data){
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	var d = data.getWhcdFb;
    	 	var xdata = [];
    	 	var ydata = [];
    	 	for(var i=0;i<d.length;i++){
    	 		xdata.push(d[i].WHCD_NAME);
    	 		var item = {name:d[i].WHCD_NAME,value:d[i].NUMS};
    	 		ydata.push(item);
    	 	}
    	 	var me = this;
    	 	pieUtils.createPie({
	        	divId : 'wenHuaChengDuPie',
	        	title:'文化程度分布',
	        	width:'33.3',
	        	height:'550',
	        	legend_y:400,
	        	legendData:xdata,
	        	seriesData:ydata,
				clickfn: function(param){
					var typecode = 'whcd';
					me.winPage(param.name,typecode);
				}
	        });
         },this);
    	
    },
    
    zhichengUpdate : function(){
    	this.callService({key:'getZcFb',params:this.params},function(data){
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	var d = data.getZcFb;
    	 	var xdata = [];
    	 	ydata = [];
    	 	for(var i=0;i<d.length;i++){
    	 		xdata.push(d[i].ZYJSZW_NAME);
    	 		var item = {name : d[i].ZYJSZW_NAME,value :d[i].NUMS};
    	 		ydata.push(item);
    	 	}
    	 	var me = this;
    	 	pieUtils.createPie({
	        	divId : 'zhichengPie',
	        	title:'职称组成',
	        	width:'33.3',
	        	height:'550',
	        	legend_y:400,
	        	legendData:xdata,
	        	seriesData:ydata,
				clickfn: function(param){
					me.params.zc = param.name;
					if(param.name == '未维护'){
						var typecode = 'zc';
					    me.winPage(param.name,typecode);
					}else {
						$("#zhichengMingXiPie").html(me.loadingHtml);
						me.zcmxUpdate();
						document.getElementById("zhichengPie").style.display = 'none';
						document.getElementById("xiazuan").style.display = '';
					}
				}
	        });
         },this);
    	
    },
    zcmxUpdate : function(){
    	this.callService({key:'getZcMxFb',params:this.params},function(data){
    	 	var pieUtils = new Pages.sc.ky.pie.PieDisplay();
    	 	var d = data.getZcMxFb;
    	 	var xdata = [];
    	 	ydata = [];
    	 	for(var i=0;i<d.length;i++){
    	 		xdata.push(d[i].ZYJSZW_NAME);
    	 		var item = {name : d[i].ZYJSZW_NAME,value :d[i].NUMS};
    	 		ydata.push(item);
    	 	}
    	 	var me = this;
    	 	pieUtils.createPie({
	        	divId : 'zhichengMingXiPie',
	        	title:'职称组成',
	        	width:'33.3',
	        	height:'550',
	        	legend_y:400,
	        	legendData:xdata,
	        	seriesData:ydata,
				clickfn: function(param){
					var typecode = 'zc';
					me.winPage(param.name,typecode);
				}
	        });
         },this);
    },
    
    initParams:function(){
        var nfdata = this.simpleDate.getValue();
        var navdata = this.navigation.getValue();
        this.params.from = nfdata.from;
        this.params.to = nfdata.to;
        this.params.zzjgId = navdata.nodes[0].id;
        this.params.type= '全部'; 
    },
    title1 : new Exp.chart.PicAndInfo({
        title : "科研成果人员分布",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    title2 : new Exp.chart.PicAndInfo({
        title : "科研成果人员各单位分布",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    createMain:function(){
        var states = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : [{id:'全部',mc:'全部'},{id:'鉴定成果',mc:'鉴定成果'},{id:'获奖成果',mc:'获奖成果'},{id:'科研项目',mc:'科研项目'},{id:'发表论文',mc:'发表论文'},{id:'收录论文',mc:'收录论文'},{id:'获奖论文',mc:'获奖论文'},{id:'著作',mc:'著作'},{id:'专利',mc:'专利'}]
        });
        var combobox = this.combobox = new Ext.form.ComboBox({
            width:200,
            labelWidth:60,
            store: states,
            fieldLabel:'成果类型',
            queryMode: 'local',
            displayField: 'mc',
            margin:'10 0 0 0',
            columnWidth:0.5,
            valueField: 'id'
        });
        var typeContainer = new NS.container.Container({
            layout:"column",
            margin:'5 0 0 10',
            items:[this.combobox]
        });
        combobox.setValue("全部");
        combobox.on('change',function(compo,newValue,oldValue){
        	$("#zhichengMingXiPie").html(this.loadingHtml);
        	this.params.type=newValue;
            this.updatePie();
            this.updateBar();
            this.zcmxUpdate();
        },this);
        return typeContainer;
     }
	
});