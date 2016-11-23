/**
 * 科研项目概况统计
 * User: liyt
 * Date: 15-11-10
 * Time: 上午10:50
 *
 */
NS.define('Pages.sc.keyan.Keyanxmgktj',{
	extend:'Template.Page',
	//entityName:'',
	modelConfig:{
		serviceConfig:{
			queryJxzzjgTree: 'scService?getYxzzjgTree',//这是对应学校的所有地点（学院、招生部、等等）
			queryKyxm:'kyService?getKyxmlbData',// 科研项目2    类别（国家、省、市等等）
			queryKyxmJb:'kyService?getKyxmJbData',//级别  （一般、重点、重大）
			queryKyxmState:'kyService?getKyxmStateData',//项目状态（进行中、完成、终止、其他）
			getKyxmLnqsData:'kyService?getKyxmLnqsData',//历年项目数量变化趋势
			getKyxmXylnqsData:'kyService?getKyxmXylnqsData',//分学院历年项目数量变化趋势
			getKyLnxmData:'kyService?getKyLnxmData',//历年项目数据详细信息(弹出的table)
			queryGridContent4xq:'kyService?queryGridContent4xq'
		}
	},
	tplRequires : [],
    cssRequires : ['app/pages/zksf/css/keyanxmgktj.css','app/pages/sc/ky/lw/lwcss/lw.css'],
    //mixins:['Pages.sc.Scgj'],
    requires:[],
    params:{levelCode:"levelCode",codeType:"RES_PROJECT_LEVEL_CODE",start:1,limit:10},
    /**
     * 页面入口
     */
    init: function () {
    	var me = this;
    	var loadingHtml = "";
    	//三个饼状图
    	var htmlChina = "<div style='height:35px;' class='list-line'>" +
						"<div class='list-tab'>" +
						"<span id='qiehuan1' val='0' class='vavavavavav list-selected'>自然科学类</span>" +
						"<span id='qiehuan2' val='0' class='vavavavavav list-default'>社会科学类</span>" +
						"</div>" +
						"</div>"  +
						"<div style='margin-top:30px'>" +
						"<div style='margin-top:5px;'><span style='border:0px;border-left: 4px solid #3196fe;padding:0px 0px 0px 5px;color: #265efd;font-weight: bold;font-size: 16px;'>科研分布</span>" +
				"<hr color='#5299eb' style='margin-top: 5px;'></div>" +
    			        "<div id='bzt_01' style='margin-top:10px;height:400px;width:33%;float:left;'></div>";
    	var htmlNy = "<div id='bzt_02' style='margin-top:10px;height:400px;width:33%;float:left;'></div>";
    	var htmljxz = "<div id='bzt_03' style='margin-top:10px;height:400px;width:33%;float:left;'></div>" +
    			     "</div>" +
    			"<div id='qsView_01'>" +
    			"<div style='margin-top:5px;'><span id='span_01' style='border:0px;border-left: 4px solid #3196fe;padding:0px 0px 0px 5px;color: #265efd;font-weight: bold;font-size: 16px;'>历年数量项目变化趋势</span>" +
				"<hr color='#5299eb' style='margin-top: 5px;'></div>";
    	var htmlSpan = "<div id ='div_span' class='abs-block' style=' float:left;'>" +
    			"<a href='javascript:void(0);' id = 'bzt_001' style='visibility:hidden' class='abs-default'></a>" +
    			"<a href='javascript:void(0);' id = 'bzt_002' style='visibility:hidden' class='abs-default'></a>" +
    			"<a href='javascript:void(0);' id = 'bzt_003' style='visibility:hidden' class='abs-default'></a></div>";

    	//历年项目数量变化趋势           分学院历年项目数量变化趋势
    	var lnxmQs = "<div><div id='qxt_01' style='height:500px;width:99%;margin-bottom:70px;'></div>" +
    			"<div><a id='showPieTextGoodId' href='javascript:void(0);'  showhide='hide' >查看分学院历年项目数量变化趋势</a></div>";
    	var xylnxmQs = "<div id='qxt_02' style='display:none;height:600px;width:99%;margin-top:30px;'></div></div></div>";
        loadingHtml = htmlChina + htmlNy + htmljxz + htmlSpan + lnxmQs + xylnxmQs;//饼状图三个 + 两个折线图
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'科研项目概况统计',
                pageHelpInfo:'从学校、一级科研机构、二级科研机构逐级下钻按照日期统计已获得奖励、专利、在研人员、在研项目数、项目金额、完成项目数等的统计信息'
            }
        });
        var navigation = this.navigation = new Exp.component.Navigation();
        var page =this.component= new NS.Component({
     		border : true,
     		baseCls : '',
     		autoScroll : true,
     		html : loadingHtml,
     		autoShow : true
     	});
        var simpleDate = this.simpleDate = new Exp.component.SimpleYear(
			{
				start : 1990,
				defaultDate:'bz',
				margin : '0 0 0 10'
			});
        var containerDate = new Ext.container.Container({
				cls : 'student-sta-titlediv',
				layout : {
					type : 'hbox',
					align : 'left'
				},
				height : 30,
				margin : '5 0 5 0',
				items : [
						simpleDate,
						new Ext.Component(
								{
									html : '<span style="margin-left: 20px;height: 26px;line-height: 26px;color: #777;">☞选择开始年份和结束年份，统计该年份内的人数。</span>'
								}) ]
			});

     // 刷新时间组件
		simpleDate.on('validatepass', function() {
			var data = this.getValue();
			me.params.from = data.from;
			me.params.to = data.to;
			if(data.from == data.to){//今年、去年
				$("#qsView_01").hide();
			}else{
				$("#qsView_01").show();
			}
			me.initParams();
			me.fillCompoByData("110-180");
		});
        var container = this.pageContainer = new NS.container.Container({
            padding:20,
            autoScroll:true,//默认是否滚动
            items:[pageTitle,navigation,simpleDate,containerDate,page]
        });
        this.setPageComponent(container);
        // 刷新导航栏
        this.callService('queryJxzzjgTree',function(data){
            this.navigation.refreshTpl(data.queryJxzzjgTree);
            var i = 0;
            for(var key in data.queryJxzzjgTree){
                if(i==0){
                    var nodeId = data.queryJxzzjgTree[key].id;
                    this.navigation.setValue(nodeId);
                    this.params.zzjgId = nodeId;
                }
                i++;
            }
            this.initClick();
            this.initParams();
            //这里应该自动填充三个饼状图   1.国家级类型的   2.一般，重点，重大类型的   3.进行中，完成，终止的 类型
            this.fillCompoByData("110-180");
        },this);
        
        // 导航栏的点击事件
        navigation.on('click',function(){
            var data = this.getValue(),len = data.nodes.length;
            me.params.zzjgId = data.nodes[len-1].id;
            me.params.zzjgmc = data.nodes[len-1].mc;
            me.fillCompoByData("110-180");
        });
    },
    pmLineEcharts : null,
    getColumnCfgsBzxmd:function(gridFields){
        var textarrays = "项目编号,项目名称,承担单位,主持人员,项目类别,下达部门,开始时间,结束时间,经费数额,备注".split(",");
        var widtharrays = [80,120,120,90,120,120,90,90,90,120];
        var hiddenarrays = [false,false,false,false,false,false,false,,false,false,true];
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
    },
	lnGrid : function(nf){
		var me = this;
		me.params.startTime = nf;
		me.params.pName = "";
		me.params.mc = "";
		me.params.compere = "";
		me.params.fund = "";
		me.params.lnGrid = "lnGrid";//用语后台区分导出
        var params = {params:me.params,start:0,limit:15};
        me.callService({key:'getKyLnxmData',params:params},function(respData){
            var data = respData.getKyLnxmData;
            var gridFields = ["ID","PNAME","MC","COMPERE","TNAME","ISSUED_DEPT","START_TIME","END_TIME","FUND","REMARK"];
            var columnCfgs = me.getColumnCfgsBzxmd(gridFields);
            // 创建详情grid
            var dxgrid = me.dxgrid = me.initXqGrid(data,gridFields,columnCfgs,params,'getKyLnxmData',false,true,15);
            var title = {
    			xtype : "box",
    			layout : 'fit',
    			html : "<div style='margin:20px;text-align:center;font-size:24px;text-align:ceter'>"
    					+ me.params.from
    					+ "-"
    					+ me.params.to
    					+ "年"
    					+ "详细列表</div>"
    		};
            var cx = me.queryTest(params);//这个params 指的是 var params 
            me.win = new NS.window.Window({
            	title : ' 历年趋势列表详情',
				layout : 'fit',
				modal : true,
				width : 1080,
				height : 580,
				id:'win',
				items : [ {
						xtype : "form",
						defaultType : 'textfield',
						defaults : { anchor : '100%',},
						fieldDefaults : { labelWidth : 80, labelAlign : "left", flex : 1, width : 280 },
						height:105,
						items : [title,cx]
					},{
						id	:'grid',layout : 'fit',
						height:445,
						items : [dxgrid]
					} ]
            });
            me.win.show();
        });
    },
    xylnGrid : function(nf,xyName,codeType,levelCode){
    	var me = this;
		me.params.startTime = nf;
    	me.params.xyName = xyName;
    	me.params.codeType = codeType;
    	me.params.levelCode = levelCode;
    	me.params.pName = "";
  		me.params.mc = "";
  		me.params.compere = "";
  		me.params.fund = "";
  		me.params.lnGrid = "xylnGrid";//用语后台区分导出
    	var params = {params:me.params,start:0,limit:15};
    	me.callService({key:'queryGridContent4xq',params:params},function(respData){
    		var data = respData.queryGridContent4xq;
    		var gridFields = ["ID","PNAME","MC","COMPERE","TNAME","ISSUED_DEPT","START_TIME","END_TIME","FUND","REMARK"];
    		var columnCfgs = me.getColumnCfgsBzxmd(gridFields);
    		// 创建详情grid
    		var dxgrid = me.dxgrid = me.initXqGrid(data,gridFields,columnCfgs,params,'queryGridContent4xq',false,true,15);
    		var title = {
    				xtype : "box",
    				layout : "fit",//在fit布局下，对其子元素设置宽度是无效的
    				html : "<div style='margin:20px;text-align:center;font-size:24px;text-align:ceter'>"
        					+ me.params.from
        					+ "-"
        					+ me.params.to
        					+ "年"
        					+ "详细列表</div>"
    		};
    		var cx = me.queryTest(params);
    		me.win = new NS.window.Window({
            	title : ' 分学院历年趋势列表详情',
				layout : 'fit',
				modal : true,
				width : 1080,
				height : 580,
				id:'win',
				items : [ {
						xtype : "form",
						defaultType : 'textfield',
						defaults : { anchor : '100%',},
						fieldDefaults : { labelWidth : 80, labelAlign : "left", flex : 1, width : 280 },
						height:105,
						items : [title,cx]
					},{
						id	:'grid',layout : 'fit',
						height:445,
						items : [dxgrid]
					} ]
            });
    		me.win.show();
    	});
    },
    /***
     * 构建 查询的文本框、搜索、导出
     */
    queryTest : function(params){
    	var me=this;
    	var cx = {
              xtype: "container",
              layout: "hbox",
              items: [
                  { xtype: "textfield", name: "PNAME", id: "pName",width : 200, fieldLabel: "项目名称", allowBlank: false },
                  { xtype: "textfield", name: "MC", id: "mc",width : 200, fieldLabel: "承担单位", allowBlank: false },
                  { xtype: "textfield", name: "COMPERE", id: "compere", width : 200, fieldLabel: "主持人", allowBlank: false },
                  { xtype: "textfield", name: "FUND", id: "fund", width : 200, fieldLabel: "最小经费", allowBlank: false },
                  { xtype: "button", name: "sousuo", text: "搜索", handler : function() {
                  		me.params.pName = Ext.getCmp('pName').getValue();
                  		me.params.mc = Ext.getCmp('mc').getValue();
                  		me.params.compere = Ext.getCmp('compere').getValue();
                  		me.params.fund = Ext.getCmp('fund').getValue();
                  		me.dxgrid.load();//查询方法
                  	}
                  },
                  { xtype: "button", name: "daochu", id : "daochu_Excel", text: "导出EXCEL" ,iconCls : "page-excel", handler : function(){
                		me.exportMd(params);
                  	}
                  }
              ]
          };
    	return cx;
    },
    /***
     * 历史趋势导出EXCEL
     */
    exportMd : function(queryGridParams){
        // 导出
        var serviceAndParams ={
            servicAndMethod:'kyService?getWzsMdExport',
            params:queryGridParams
        } ;
        var strParams = JSON.stringify(serviceAndParams);
        //window.open(pageURL,name,parameters) 
        //menubar | yes/no | 菜单栏是否可见    //scrollbars | yes/no | 窗口是否可有滚动栏     //resizable | yes/no | 窗口大小是否可调整
        window.document.open("customExportAction.action?serviceAndParams="+strParams,
            '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
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
            pageSize : pagesize||20,
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
    showHide : function(){
    	$("#a_01").attr("style","visibility:hidden");
    	$("#a_02").attr("style","visibility:hidden");
    	$("#a_03").attr("style","visibility:hidden");
    },
    keyanHide : function(target,codeType){
    	var id = $(target).attr("id");//一个参数代表获取它的值
    	var me = this;
    	if(id == "bzt_001"){
    		$("#bzt_001").text("");
			$("#bzt_001").attr("levelCode",0);
			$("#bzt_001").attr("style","visibility:hidden");
			var b1 = ($("#bzt_001").attr("levelCode")) != undefined ? $("#bzt_001").attr("levelCode") : 0;
			var b2 = ($("#bzt_002").attr("rankCode")) != undefined ? $("#bzt_002").attr("rankCode") : 0;
			var b3 = ($("#bzt_003").attr("stateCode")) != undefined ? $("#bzt_003").attr("stateCode") : 0;
			var code = [];
			code.push(Number(b1));
			code.push(Number(b2));
			code.push(Number(b3));
			me.params.code = code;//调用那个就给后台传值，然后根据条件来筛选数据
			me.params.codeType = codeType;
			me.updateTu1("",b1,codeType);//levelCode是传过来的字符串
			me.updateTu2("",b1,codeType);//levelCode是传过来的字符串
    	}
    	if(id == "bzt_002"){
    		$("#bzt_002").text("");
			$("#bzt_002").attr("rankCode",0);
			$("#bzt_002").attr("style","visibility:hidden");
			var b1 = ($("#bzt_001").attr("levelCode")) != undefined ? $("#bzt_001").attr("levelCode") : 0;
			var b2 = ($("#bzt_002").attr("rankCode")) != undefined ? $("#bzt_002").attr("rankCode") : 0;
			var b3 = ($("#bzt_003").attr("stateCode")) != undefined ? $("#bzt_003").attr("stateCode") : 0;
			var code = [];
			code.push(Number(b1));
			code.push(Number(b2));
			code.push(Number(b3));
			me.params.code = code;//调用那个就给后台传值，然后根据条件来筛选数据
			me.params.codeType = codeType;
			me.updateTu1("",b2,codeType);//levelCode是传过来的字符串
			me.updateTu2("",b2,codeType);//levelCode是传过来的字符串
    	}
    	if(id == "bzt_003"){
    		$("#bzt_003").text("");
			$("#bzt_003").attr("stateCode",0);
			$("#bzt_003").attr("style","visibility:hidden");
			var b1 = ($("#bzt_001").attr("levelCode")) != undefined ? $("#bzt_001").attr("levelCode") : 0;
			var b2 = ($("#bzt_002").attr("rankCode")) != undefined ? $("#bzt_002").attr("rankCode") : 0;
			var b3 = ($("#bzt_003").attr("stateCode")) != undefined ? $("#bzt_003").attr("stateCode") : 0;
			var code = [];
			code.push(Number(b1));
			code.push(Number(b2));
			code.push(Number(b3));
			me.params.code = code;//调用那个就给后台传值，然后根据条件来筛选数据
			me.params.codeType = codeType;
			me.updateTu1("",b3,codeType);//levelCode是传过来的字符串
			me.updateTu2("",b3,codeType);//levelCode是传过来的字符串
    	}
    },
    /***
     * 分学院历史趋势图（显示隐藏）
     */
    fxyQs : function(target){
    	var me = this;
    	var showHdie = $("#showPieTextGoodId").attr("showhide");
    	$("#qxt_02").toggle();
    	if(showHdie=="hide"){
    		$("#showPieTextGoodId").html("隐藏分学院历年项目数量变化趋势");
    		$("#showPieTextGoodId").removeAttr("showhide");
    		$("#showPieTextGoodId").attr("showhide","show");
    		me.pmLineEcharts.resize();
    		return ;
    	}
    	if(showHdie=="show"){
    		$("#showPieTextGoodId").html("查看分学院历年项目数量变化趋势");
    		$("#showPieTextGoodId").removeAttr("showhide");
    		$("#showPieTextGoodId").attr("showhide","hide");
    		me.pmLineEcharts.resize();
    		return ;
    	}
    },
    /***
     * 切换科学分类
     */
    qieHuan : function(target){
    	var me = this;
    	var id = target.attr("id");
    	if(id == "qiehuan1"){
    		$("#qiehuan1").removeAttr("class");
    		$("#qiehuan2").removeAttr("class");
    		$("#qiehuan1").attr({class:"vavavavavav list-selected"});
    		$("#qiehuan2").attr({class:"vavavavavav list-default"});
    		me.fillCompoByData("110-180");
    	}
    	if(id == "qiehuan2"){
    		$("#qiehuan1").removeAttr("class");
    		$("#qiehuan2").removeAttr("class");
    		$("#qiehuan1").attr({class:"vavavavavav list-default"});
    		$("#qiehuan2").attr({class:"vavavavavav list-selected"});
    		me.fillCompoByData("710-910");
    	}
    },
    /**
     * 数据填充组件
     */
    fillCompoByData:function(kxfl){
    	var me=this;
    	var code =[0,0,0];
    	me.params.code = code;
    	if(kxfl == "110-180"){
    		me.params.projectId = '110-180';//默认是：自然科学类；   人文与社会科学类：710-910
    	}
    	if(kxfl == "710-910"){
    		me.params.projectId = '710-910';//默认是：自然科学类；   人文与社会科学类：710-910
    	}
    
    	me.callService({key:'queryKyxm',params: me.params},function(data){
        	var xmlb = [];
        	var lbName = [];
            for(var i=0;i<data.queryKyxm.length;i++){
                var obj = data.queryKyxm[i];
                xmlb.push({name:obj.NAME_,value:Number(obj.COUNTS),levelCode:obj.CODE_,codeType:obj.CODE_TYPE});
                xmlb.push(obj);
                lbName.push(obj.NAME_);
            }
     		me.viewBztLb(lbName,xmlb);
 		});
     	//级别
     	me.callService({key:'queryKyxmJb',params:me.params},function(data){
     		var xmjb = [];
     		var jbName = [];
     		for(var i=0; i<data.queryKyxmJb.length;i++){
     			var obj = data.queryKyxmJb[i];
     			xmjb.push({name:obj.NAME_,value:Number(obj.COUNTS),levelCode:obj.CODE_,codeType:obj.CODE_TYPE});
     			jbName.push(obj.NAME_);
     		}
     		me.viewBztJb(jbName,xmjb);
     	});
     	//这是状态
     	me.callService({key:'queryKyxmState',params:me.params},function(data){
     		var xmzt = [];
        	var ztName = [];
        	for(var i=0; i<data.queryKyxmState.length;i++){
        		var obj = data.queryKyxmState[i];
        		xmzt.push({name:obj.NAME_,value:Number(obj.COUNTS),levelCode:obj.CODE_,codeType:obj.CODE_TYPE});
        		ztName.push(obj.NAME_);
        	}
     		me.viewBztState(ztName,xmzt);
     	});
     	//刚进来的时候自动加载全部（历年趋势）
     	me.callService({key:'getKyxmLnqsData',params: me.params},function(data){
     		var startTime = [];
        	var dataNums = [];
        	var mName = "";
     		for(var i=0;i<data.getKyxmLnqsData.length;i++){
                var obj = data.getKyxmLnqsData[i];
                startTime.push(obj.STARTTIME);
                dataNums.push(obj.COUNTS);
            }
     		//重新加载 通过你的ID  updateTu1(startTime,dataNums)
            me.viewBztLsqs(mName,startTime,dataNums,'levelCode','');//这是初始化
 		});
     	//分学院历年趋势
     	//后台数据处理不对---------请咨询大哥
     	me.callService({key:'getKyxmXylnqsData',params: me.params},function(data){
     		var yxName=[];
     		var yxTime={};
     		var yxSericeArray=[];
     		var yxSeries={};//以院系的名字为key
     		var time=[];
     		var timeObj={};
     		for(var i=0;i<data.getKyxmXylnqsData.length;i++){
     			var obj = data.getKyxmXylnqsData[i];
                if(timeObj[obj.STARTTIME]==null){
                	timeObj[obj.STARTTIME]=obj.STARTTIME;
                	time.push(obj.STARTTIME);//时间轴的数据
                }
                if(yxSeries[obj.MC]==null){
                	yxTime[obj.MC]={};
                	yxName.push(obj.MC);
                	yxSeries[obj.MC]={name:obj.MC,type:'line',stack:'总量',data:[]};
                	yxSericeArray.push(yxSeries[obj.MC]);
                }
                yxTime[obj.MC][obj.STARTTIME]=obj;
//                yxSeries[obj.MC].data.push(obj.COUNTS);
            }
     		for(var i=0;i<time.length;i++){
     			var t=time[i];
     			for(var yxmc in yxSeries){
     				var yxSerie=yxSeries[yxmc];
     				if(yxTime[yxmc][t]==null){
     					yxSerie.data.push(0);
     				}else{
     					yxSerie.data.push(yxTime[yxmc][t].COUNTS);
     				}
     			}
     		}
            me.viewBztXylsqs(yxName,yxSericeArray,'',time,'levelCode','');//页面加载的时候自动加载全部
 		});
    },
    /**
     * 设置饼状图
     * 级别类型
     * 绑定click事件：点击里面的内容的时候，显示下面对应的 曲线图
     * */
    viewBztLb : function(lbName,xmlb) {
		var option = {
			title : {
		        x :'center',
		        text: "类别分布"
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{b} : {c} ({d}%)"
		    },
//		    grid:{
//	 			  height:400,
//	 			  y2:400
//	 		},
		    legend: {
		    	 x: 'center',
			     y: 'bottom',
			     orient : 'horizontal',
		        data : lbName
		    },
		    toolbox: {
		    	y : 'top',
		        show : true,
		        feature : {
//		            magicType : {
//		                show: true, 
//		                type: ['pie', 'funnel'],
//		                option: {
//		                    funnel: {
//		                        x: '10%',
//		                        width: '40%',
//		                        funnelAlign: 'left'
//		                    }
//		                }
//		            },
//		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		            type:'pie',
		            radius : '55%',
		            center: ['50%', '45%'],
		            data: xmlb
		        }
		    ],
		    noDataLoadingOption : {
	    	  	text : '科研项目类别暂无数据',
	    	    effectOption : null,
	    	    effect : 'bubble',
	    	    textStyle : {fontSize : 20},
	    	    effectOption : {
	    	    	effect : {n:'0'}
	    	    }
		    }
		};
		var myChart = echarts.init(document.getElementById("bzt_01"),'blue');
		myChart.setOption(option);
		var me=this;
		myChart.on(echarts.config.EVENT.CLICK, function(data){
			var name = data.name;
			var levelCode = data.levelCode;
			var codeType = data.CodeType;
			for(var i=0;i<xmlb.length;i++){
				var o = xmlb[i].name;
				if(o == name){
					levelCode = xmlb[i].levelCode;
					codeType = xmlb[i].codeType;
					break;
				}
			}
//			$("#bzt_001").text(name);
			$("#bzt_001").html(name+"<span class='abs-delete'></span>");
			$("#bzt_001").attr("levelCode",levelCode);	//这个只在第一个饼状图中填写值
			$("#bzt_001").attr("style","");
			$("#bzt_001").click(function(){
				me.keyanHide(this,codeType);
			});
			var b1 = ($("#bzt_001").attr("levelCode")) != undefined ? $("#bzt_001").attr("levelCode") : 0;
			var b2 = ($("#bzt_002").attr("rankCode")) != undefined ? $("#bzt_002").attr("rankCode") : 0;
			var b3 = ($("#bzt_003").attr("stateCode")) != undefined ? $("#bzt_003").attr("stateCode") : 0;
			var code = [];
			code.push(Number(b1));
			code.push(Number(b2));
			code.push(Number(b3));
			me.params.code = code;//调用那个就给后台传值，然后根据条件来筛选数据
			me.params.codeType = codeType;
			me.updateTu1(name,b1,codeType);//levelCode是传过来的字符串
			me.updateTu2(name,b1,codeType);//levelCode是传过来的字符串
		});
	},
	/**
     * 设置饼状图
     * 难易程度
     * */
	viewBztJb : function(jbName,xmjb) {
		var option = {
			title : {
		        x :'center',
		        text: "级别分布"
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{b} : {c} ({d}%)"
		    },
		    legend: {
		        x: 'center',
		        y: 'bottom',
		        orient : 'horizontal',
		        data:jbName
		    },
		    toolbox: {
		    	y : 'top',
		        show : true,
		        feature : {
//		            magicType : {
//		                show: true, 
//		                type: ['pie', 'funnel'],
//		                option: {
//		                    funnel: {
//		                        x: '10%',
//		                        width: '40%',
//		                        funnelAlign: 'left'
//		                    }
//		                }
//		            },
//		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		        	 type:'pie',
			         radius : '55%',
			         center: ['50%', '45%'],
		            data: xmjb
		        }
		    ],
		    noDataLoadingOption : {
	    	  	text : '科研项目级别暂无数据',
	    	    effectOption : null,
	    	    effect : 'bubble',
	    	    textStyle : {fontSize : 20},
	    	    effectOption : {
	    	    	effect : {n:'0'}
	    	    }
		    }
		};
		var myChart = echarts.init(document.getElementById("bzt_02"),'blue');
		myChart.setOption(option);
		var me=this;
		myChart.on(echarts.config.EVENT.CLICK, function(data){
			var name = data.name;
			var levelCode = data.levelCode;
			var codeType = data.CodeType;
			for(var i=0;i<xmjb.length;i++){
				var o = xmjb[i].name;
				if(o == name){
					levelCode = xmjb[i].levelCode;
					codeType = xmjb[i].codeType;
					break;
				}
			}
//			$("#bzt_002").text(name);
			$("#bzt_002").html(name+"<span class='abs-delete'></span>");
			$("#bzt_002").attr("rankCode",levelCode);
			$("#bzt_002").attr("style","");
			$("#bzt_002").click(function(){
				me.keyanHide(this,codeType);
			});
			var b1 = ($("#bzt_001").attr("levelCode")) != undefined ? $("#bzt_001").attr("levelCode") : 0;
			var b2 = ($("#bzt_002").attr("rankCode")) != undefined ? $("#bzt_002").attr("rankCode") : 0;
			var b3 = ($("#bzt_003").attr("stateCode")) != undefined ? $("#bzt_003").attr("stateCode") : 0;
			var code = [];
			code.push(Number(b1));
			code.push(Number(b2));
			code.push(Number(b3));
			me.params.code = code;//调用那个就给后台传值，然后根据条件来筛选数据
			me.params.codeType = codeType;
			me.updateTu1(name,b2,codeType);//levelCode是传过来的字符串
			me.updateTu2(name,b2,codeType);//levelCode是传过来的字符串
		});
	},
    
	/**
     * 设置饼状图
     * 完成情况
     * */
	viewBztState : function(ztName,xmzt) {
		var option = {
			title : {
		        x :'center',
		        text: "状态分布"
		    },
		    tooltip : {
		        trigger: 'item',
		        formatter: "{b} : {c} ({d}%)"
		    },
		    legend: {
		    	 x: 'center',
			     y: 'bottom',
			     orient : 'horizontal',
		        data: ztName
		    },
		    //这个是设置那个编辑的功能
		    toolbox: {
		    	y : 'top',
		        show : true,
		        feature : {
//		            magicType : {
//		                show: true, 
//		                type: ['pie', 'funnel'],
//		                option: {
//		                    funnel: {
//		                        x: '10%',
//		                        width: '40%',
//		                        funnelAlign: 'left',
//		                        max : 1000
//		                    }
//		                }
//		            },
//		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    series : [
		        {
		        	 type:'pie',
			         radius : '55%',
			         center: ['50%', '45%'],
		            data: xmzt
		        }
		    ],
		    noDataLoadingOption : {
	    	  	text : '科研项目状态暂无数据',
	    	    effectOption : null,
	    	    effect : 'bubble',
	    	    textStyle : {fontSize : 20},
	    	    effectOption : {
	    	    	effect : {n:'0'}
	    	    }
		    }
		};
		var myChart = echarts.init(document.getElementById("bzt_03"),'blue');
		myChart.setOption(option);
		var me=this;
		myChart.on(echarts.config.EVENT.CLICK, function(data){
			var name = data.name;
			var levelCode = data.levelCode;
			var codeType = data.CodeType;
			for(var i=0;i<xmzt.length;i++){
				var o = xmzt[i].name;
				if(o == name){
					levelCode = xmzt[i].levelCode;
					codeType = xmzt[i].codeType;
					break;
				}
			}
//			$("#bzt_003").text(name);
			$("#bzt_003").html(name+"<span class='abs-delete'></span>");
			$("#bzt_003").attr("stateCode",levelCode);
			$("#bzt_003").attr("style","");
			$("#bzt_003").click(function(){
				me.keyanHide(this,codeType);
			});
			var b1 = ($("#bzt_001").attr("levelCode")) != undefined ? $("#bzt_001").attr("levelCode") : 0;
			var b2 = ($("#bzt_002").attr("rankCode")) != undefined ? $("#bzt_002").attr("rankCode") : 0;
			var b3 = ($("#bzt_003").attr("stateCode")) != undefined ? $("#bzt_003").attr("stateCode") : 0;
			var code = [];
			code.push(Number(b1));
			code.push(Number(b2));
			code.push(Number(b3));
			me.params.code = code;//调用那个就给后台传值，然后根据条件来筛选数据
			me.params.codeType = codeType;
			me.updateTu1(name,b3,codeType);//levelCode是传过来的字符串
			me.updateTu2(name,b3,codeType);//levelCode是传过来的字符串
		});
	},
	/**
	 * 重新加载 通过你的ID  updateTu1(titles,levelCode,codeType)
	 * titles  点击并状态的一个属性，得到的值 如（一般、重点、重大）
	 * startTime  开始事件（x轴）
	 * dataNums  项目数量（y轴）
	 * */
	updateTu1 : function(titles,levelCode,codeType){
		var me = this;
		me.initClick();
		me.params.levelCode = levelCode;
		me.params.codeType = codeType;
		me.callService({key:'getKyxmLnqsData',params: me.params},function(data){
     		var startTime = [];
        	var dataNums = [];
     		for(var i=0;i<data.getKyxmLnqsData.length;i++){
                var obj = data.getKyxmLnqsData[i];
                startTime.push(obj.STARTTIME);
                dataNums.push(obj.COUNTS);//,levelCode,codeType);//给他添加两个变量
            }
     		var t1 = $("#bzt_001").text();
    		var t2 = $("#bzt_002").text();
    		var t3 = $("#bzt_003").text();
    		var name = "历年"+t1+t2+t3+"项目数量变化趋势";
    		$("#span_01").text(name);
    		var option = {
    		    legend: {
    		       y : 'bottom',
    		       data:['项目数量']
    		    },
    		    tooltip : {
    		        trigger: 'axis'
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
//    		            magicType : {
//    		              show: true, type: ['line', 'bar']
//    		            },
//    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    xAxis : [
    		        {
    		            type : 'category',
//    		            boundaryGap : false,   or    boundaryGap : true,
    		            data : startTime
    		        }
    		    ],
    		    yAxis : [
    		        {
		            	 type : 'value',
		                 name : '数量',
		                 axisLabel : {
		                     formatter: '{value}'
		                 }
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'项目数量',
    		            type:'line',
    		            stack: '总量',
    		            data:dataNums
    		        }
    		    ],
    		    noDataLoadingOption : {
    	    	  	text : '历年趋势暂无数据',
    	    	    effectOption : null,
    	    	    effect : 'bubble',
    	    	    textStyle : {fontSize : 20},
    	    	    effectOption : {
    	    	    	effect : {n:'0'}
    	    	    }
    		    }
    		};
    		var myChart = echarts.init(document.getElementById("qxt_01"),'blue');
    		myChart.setOption(option);
    		myChart.on(echarts.config.EVENT.CLICK, function(data){//这里面就已经丢失了参数数据
    			var name = data.name;
    			for(var i=0;i<dataNums.length;i++){
    				var o = dataNums[i].name;
    				if(o == name){
    					me.params.levelCode = dataNums[i].levelCode;
    					me.params.codeType = dataNums[i].codeType;
    					break;
    				}else{
    					me.params.levelCode = levelCode;//调用那个就给后台传值，然后根据条件来筛选数据
    					me.params.codeType = codeType;
    				}
    			}
				var nf = data.name;//(这个name 的值 是代表时间)
				me.lnGrid(nf);
    		});
 		});
	},
	/**
     * 分学院历年项目数量变化趋势
     * 点击之后自动刷新修改趋势图
     */
	updateTu2 : function(titles,levelCode,codeType){
		var me = this;
		me.initClick();
		me.params.levelCode = levelCode;
		me.params.codeType = codeType;
		me.callService({key:'getKyxmXylnqsData',params: me.params},function(data){
     		var yxName=[];
     		var yxTime={};
     		var yxSericeArray=[];
     		var yxSeries={};//以院系的名字为key
     		var time=[];
     		var timeObj={};
     		for(var i=0;i<data.getKyxmXylnqsData.length;i++){
     			var obj = data.getKyxmXylnqsData[i];
                if(timeObj[obj.STARTTIME]==null){
                	timeObj[obj.STARTTIME]=obj.STARTTIME;
                	time.push(obj.STARTTIME);//时间轴的数据
                }
                if(yxSeries[obj.MC]==null){
                	yxTime[obj.MC]={};
                	yxName.push(obj.MC);
                	yxSeries[obj.MC]={name:obj.MC,type:'line',stack:'总量',data:[]};
                	yxSericeArray.push(yxSeries[obj.MC]);
                }
                yxTime[obj.MC][obj.STARTTIME]=obj;
            }
     		for(var i=0;i<time.length;i++){
     			var t=time[i];
     			for(var yxmc in yxSeries){
     				var yxSerie=yxSeries[yxmc];
     				if(yxTime[yxmc][t]==null){
     					yxSerie.data.push(0);
     				}else{
     					yxSerie.data.push(yxTime[yxmc][t].COUNTS);
     				}
     			}
     		}
     		var me = this;
            var t1 = $("#bzt_001").text();
    		var t2 = $("#bzt_002").text();
    		var t3 = $("#bzt_003").text();
    		var name = "分学院历年"+t1+t2+t3+"项目数量变化趋势";
//    		$("#span_01").text(name);
    		var test = {};
    		if(yxName.length>4){
    			for(var i=4;i<yxName.length;i++){
    				test[yxName[i]]=false;
    			}
    		}
    		var option = {
//    		   title : {
//    		        x :'center',
//    		        text: name
//    		    },
    		    legend: {
    		       y : 'bottom',
    		       data: yxName,
    		       selected: test
    		    },
    		    tooltip : {
    		        trigger: 'axis'
    		    },
    		    grid:{
    	 			  height:350,
    	 			 y2:'50%'
    	 		},
    		    toolbox: {
    		        show : true,
    		        feature : {
//    		            magicType : {
//    		              show: true, type: ['line', 'bar']
//    		            },
//    		            restore : {show: true},
    		            saveAsImage : {show: true}
    		        }
    		    },
    		    xAxis : [
    		        {
    		            type : 'category',
//    		            boundaryGap : false,
    		            data : time
    		        }
    		    ],
    		    yAxis : [
    		        {
    		        	type : 'value',
		                 name : '数量',
		                 axisLabel : {
		                     formatter: '{value}'
		                 }
    		        }
    		    ],
    		    series : yxSericeArray,
    		    noDataLoadingOption : {
    	    	  	text : '学院历年趋势暂无数据',
    	    	    effectOption : null,
    	    	    effect : 'bubble',
    	    	    textStyle : {fontSize : 20},
    	    	    effectOption : {
    	    	    	effect : {n:'0'}
    	    	    }
    		    }
    		};
    		var myChart = echarts.init(document.getElementById("qxt_02"),'blue');
    		myChart.setOption(option);
//    		myChart.resize();
    		me.pmLineEcharts = myChart;
    		myChart.on(echarts.config.EVENT.CLICK, function(data){
    			var nf = data.name;//(这个name 的值 是代表时间)
				var xyName =data.seriesName;//获取学院对应的名称
				me.xylnGrid(nf,xyName,codeType,levelCode);
    		});
 		});
	},
	/**
     * 设置历史趋势图  
     * 初始化的时候加载数据
     * 历年项目数量变化趋势
     * */
	viewBztLsqs : function(titles,startTime,dataNums,levelCode,codeType) {
		var t1 = $("#bzt_001").text();
		var t2 = $("#bzt_002").text();
		var t3 = $("#bzt_003").text();
		var name = "历年"+t1+t2+t3+"项目数量变化趋势";
		$("#span_01").text(name);
		var option = {
		    legend: {
		       y : 'bottom',
		       data:['项目数量']
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    toolbox: {
		        show : true,
		        feature : {
//		            magicType : {
//		              show: true, type: ['line', 'bar']
//		            },
//		            restore : {show: true},
		            saveAsImage : {show: true}
		        }
		    },
		    xAxis : [
		        {
		            type : 'category',
//		            boundaryGap : false,
		            data : startTime
		        }
		    ],
		    yAxis : [
		        {
		        	type : 'value',
	                 name : '数量',
	                 axisLabel : {
	                     formatter: '{value}'
	                 }
		        }
		    ],
		    series : [
		        {
		            name:'项目数量',
		            type:'line',
		            stack: '总量',
		            data:dataNums
		        }
		    ],
		    noDataLoadingOption : {
	    	  	text : '历年趋势暂无数据',
	    	    effectOption : null,
	    	    effect : 'bubble',
	    	    textStyle : {fontSize : 20},
	    	    effectOption : {
	    	    	effect : {n:'0'}
	    	    }
		    }
		};
		var myChart = echarts.init(document.getElementById("qxt_01"),'blue');
		myChart.setOption(option);
		var me=this;
		myChart.on(echarts.config.EVENT.CLICK, function(data){
			var nf = data.name;//(这个name 的值 是代表时间)
			me.lnGrid(nf);
		});
	},
	/**
     * 设置分学院历年趋势变化图
     * 分学院历年项目数量变化趋势
     * */
	viewBztXylsqs : function(yxName,yxSericeArray,titles,time,levelCode,codeType) {
		var t1 = $("#bzt_001").text();
		var t2 = $("#bzt_002").text();
		var t3 = $("#bzt_003").text();
		var name = "分学院历年"+t1+t2+t3+"项目数量变化趋势";
//		$("#span_01").text(name);
		var test = {};
		if(yxName.length>4){
			for(var i=4;i<yxName.length;i++){
				test[yxName[i]]=false;
			}
		}
     	var option = {
// 		    title : {
// 		        x :'center',
// 		        text: name
// 		    },
 		    legend: {
 		       y : 440,
 		       data: yxName,
 		       selected: test
 		    },
 		    tooltip : {
 		        trigger: 'axis'
 		    },
 		    grid:{
 			   height:350,
 			   y2:'50%'
 		    },
 		    toolbox: {
 		        show : true,
 		        feature : {
// 		            magicType : {
// 		              show: true, type: ['line', 'bar']
// 		            },
// 		            restore : {show: true},
 		            saveAsImage : {show: true}
 		        }
 		    },
 		    xAxis : [
 		        {
 		            type : 'category',
// 		            boundaryGap : false,
 		            data : time
 		        }
 		    ],
 		    yAxis : [
 		        {
 		        	type : 'value',
	                 name : '数量',
	                 axisLabel : {
	                     formatter: '{value}'
	                 }
 		        }
 		    ],
 		    series : yxSericeArray,
 		   noDataLoadingOption : {
	    	  	text : '学院历年趋势暂无数据',
	    	    effectOption : null,
	    	    effect : 'bubble',
	    	    textStyle : {fontSize : 20},
	    	    effectOption : {
	    	    	effect : {n:'0'}
	    	    }
		    }
 		};
		var myChart = echarts.init(document.getElementById("qxt_02"),'blue');
		myChart.setOption(option);
		var me = this;
		me.pmLineEcharts = myChart;
		myChart.on(echarts.config.EVENT.CLICK, function(data){
			var nf = data.name;//(这个name 的值 是代表时间)
			var xyName =data.seriesName;//获取学院对应的名称
			me.xylnGrid(nf,xyName,codeType,levelCode);
		});
	},
	/***
	 * 隐藏折线图中的数据图
	 */
	getMax : function(yxSericeArray){
		var nameHide = {};
		var maxObj = [];
		var max = 0;
		for(var i=0;i<yxSericeArray.length;i++){
			var o = yxSericeArray[i].data;//获取数组中的对象[8,4,5,0,0],[8,4,5,0,0],[8,4,5,0,0],[8,4,5,0,0],[8,4,5,0,0]
			for(var j=0;j<o.length;j++){
				if(o[j]>max){
					max = o[j];
				}
			}
			maxObj.push({name:yxSericeArray[i].name,value:max});
		};
		var maxData = 0;
		for(var i=0;i<maxObj.length;i++){
			var o = maxObj[i].value;
			if(o>maxData){
				maxData = o;
			}
		}
		for(var i=0;i<maxObj.length;i++){
			if(maxObj[i].value == maxData){
				nameHide[maxObj[i].name]=false;//给数组中的某个属性赋值
			}
		}
		return nameHide;
	},
	/***
	 * 
	 */
	initClick : function(){
		var me = this;
		$("#qiehuan1").click(function(){
			me.qieHuan($(this));
		});
    	$("#qiehuan2").click(function(){
    		me.qieHuan($(this));
    	});
    	$("#showPieTextGoodId").click(function(){
    		me.fxyQs($(this));
    	});
	},
    /**
     * 初始化页面参数
     */
    initParams:function(){
        var navdata = this.navigation.getValue();
        this.params.zzjgId = navdata.nodes[0].id;
        var nfdata = this.simpleDate.getValue();
		this.params.from = nfdata.from;
		this.params.to = nfdata.to;
		var code =[0,0,0];
    	this.params.code = code;
    	$("#div_span").find("a").each(function(){
    		$(this).attr("style","visibility:hidden");
    	});
    	$("#span_01").text("历年数量项目变化趋势");
    	this.showHide();
    }
});