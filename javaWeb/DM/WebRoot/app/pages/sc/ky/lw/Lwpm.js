/**
 * 高质量论文排名
 */
NS.define('Pages.sc.ky.lw.Lwpm',{
    extend:'Template.Page',
    modelConfig: {
        serviceConfig: {
            getResearchDeptRank: 'researchPaperRankService?getResearchDeptRank',// 收录论文影响因子各单位排名
            getResearchDeptAvg:'researchPaperRankService?getResearchDeptAvg',// 全校平均影响因子
            getResearchPaperRank:{
            		service:'researchPaperRankService?getResearchPaperRank',
            		params:{
	                    limit:10,
	                    start:0
               	    }
            },
            getPast:'researchPaperRankService?getPast',//查看历史
            getYears:'researchPaperRankService?getYears',
            getDepts:'researchPaperRankService?getDepts',
            getAllDept:'researchPaperRankService?getAllDept'
        }
    },
    tplRequires : [],
    cssRequires : [],
    mixins : ['Pages.sc.Scgj'],
    requires:[],
    params:{},
    selectDept :{},
    isreflushGrid:true,
    init: function () {
        var me = this;
        var comps = this.createComps();
        var pageTitle = new Exp.component.PageTitle({
            data:{
                pageName:'高质量论文排名',
                pageHelpInfo:'对高质量论文进行排名（根据收录论文的影响因子）。'}
        });
        var hl = "<div><div id='bar'><div id='loadRank' style='width:100%;height:500px'>" +
        		"</div><span style='float:right;font-size:15px;'><a href='javascript:void(0);' onclick='lookpast()'>查看历史趋势</a></span></div>" +
        		"<div id='line' style='display:none;'>" +
        		"<div id='loadline' style='width:100%;height:500px'></div><span style='float:right;font-size:15px;'><a href='javascript:void(0);' onclick='returnlast()'>返回上一层</a></span></div></div>";
        var html1 = this.component = new NS.Component({
				html : hl
		});
		lookpast = function() {
			document.getElementById("bar").style.display = "none";
			document.getElementById("line").style.display = "";
			me.pmLineEcharts.resize();
		};
		returnlast = function(){
			var bar = document.getElementById("bar").style.display="";
			var line = document.getElementById("line").style.display="none";
			me.pMBarEcharts.resize();
		};
		loadSeach = function(){
			var authors = document.getElementById("authors").value;
			var thesisTitle = document.getElementById("thesisTitle").value;
			me.params.authors = authors;
			me.params.thesisTitle = thesisTitle;
			me.tplGrid.load(me.params);
		}
        var containerx = this.mainContainer = this.createMain();
        var container = new NS.container.Container({
            padding:20,
            autoScroll:true,
            items:[pageTitle,containerx,this.title1,html1,comps]
        });
        container.on("afterrender",function(){
	    	this.fillCompoByData();
	    },this);
        this.createGrid();
        this.setPageComponent(container);
    },
    title1 : new Exp.chart.PicAndInfo({
        title : "高质量论文各单位排名",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    seach : new NS.Component({
		html : "<div>论文作者：<input id='authors' type='text' />      论文题目：<input id='thesisTitle' type='text'/> <button onclick='loadSeach();'>搜索</button></div>"
	}),
    title2 : new Exp.chart.PicAndInfo({
        title : "高质量论文排名",
        margin : "10px 10px 10px 0px",
        onlyTitle : true
    }),
    table1 : new Ext.container.Container({
    	
    }),
    
    createMain:function(){
         var states = Ext.create('Ext.data.Store', {
            fields: ['id', 'mc'],
            data : function(){
            	var list = [];
            	var myDate = new Date();
            	var year = myDate.getFullYear();
            	for(var i=0;i<20;i++){
            		var y = {id:year-i,mc:year-i};
            		list.push(y);
            	}
            	return list;
            }()
        });
        var combobox = this.combobox = new Ext.form.ComboBox({
            width:200,
            labelWidth:60,
            store: states,
            fieldLabel:'选择年份',
            queryMode: 'local',
            displayField: 'mc',
            margin:'10 0 0 0',
            columnWidth:0.5,
            valueField: 'id'
        });
        var yearContainer = new NS.container.Container({
            layout:"column",
            margin:'5 0 0 10',
            items:[this.combobox]
        });
        var myDate = new Date();
        var year = myDate.getFullYear();  
        combobox.setValue(year);
        this.params.year = year;
        combobox.on('change',function(compo,newValue,oldValue){
            this.params.year = newValue;
            this.params.deptId = '';
            this.fillCompoByData();
            if(this.isreflushGrid){
            	this.tplGrid.load(this.params);
            }
        },this);
        return yearContainer;
     },
     pMBarEcharts:null,
     pmLineEcharts:null,
     fillCompoByData:function(){
    	var me=this;
    	this.callService({key:'getResearchDeptRank',params: this.params},function(data){
    		this.callService({key:'getResearchDeptAvg',params: this.params},function(avgdata){
    			var xdata = [];
				var ydata = [];
				var avgData = [];
		    	for(var i=data.getResearchDeptRank.length;i>0;i--){
		    		xdata.push("第"+i+"名："+data.getResearchDeptRank[i-1].DEPT_NAME);
		    		ydata.push(data.getResearchDeptRank[i-1].IMPACT_FACTOR_AVG);
		    		avgData.push(avgdata.getResearchDeptAvg[0].IMPACT_FACTOR_AVG);
		    	}
		    	var dept = data.getResearchDeptRank;
		    	yData = xdata;
				option = {
				    tooltip : {
				        trigger: 'axis'
				    },
				   dataZoom : {
				        show : true,
				       orient : 'vertical',
				        realtime : true,
				        start : 20,
				        end : 80
				    },
				    noDataLoadingOption:{
				    	text :"暂无数据",
				        effect : 'bubble',
				        effectOption:{effect: {n: 0}},
				        textStyle : {
				            fontSize : 20
				        }
					},
				    legend: {
				        data:['影响因子','全校平均']
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            saveAsImage : {show: true}
				        }
				    },
				    axisLine : false,
				    xAxis : [
				        {
				            type : 'value',
				            max: ydata[ydata.length-1]*1.2,
				            splitLine: {show:false},
				            axisLine: {show:false},
				            axisLabel : {show:false},
				            boundaryGap : [0, 0.01]
				        }
				    ],
				    yAxis : [
				        {
				            type : 'category',
				           	splitLine: {show:false},
				            axisLine: {show:false},
				            axisLabel : {show:false},
				            axisTick: {show:false},
				            data : function (){
				                var list = [];
				                for (var i = 0; i < yData.length; i++) {
				                    list.push(yData[i]);
				                }
				                return list;
				            }()
				        }
				    ],
				    series : [
				        {
				            name:'影响因子',
				            type:'bar',
				            itemStyle : {
				              	  normal : {
				                        label : {
				                            show : true,
				                            position: 'right',
				                            formatter:'{b} : {c}'
				                        }
				                    }
				            },
				            data:ydata
				        },
				      {
				            name:'全校平均',
				            type:'line',
				            itemStyle : {
				              	  normal : {
				                        color : '#0000ff'
				                  }
				            },
				            data:avgData
				        }
				      
				    ]
				};
    			var div = document.getElementById("loadRank");
    			var rankEcharts = echarts.init(div,'blue');
				rankEcharts.setOption(option);
				rankEcharts.on(echarts.config.EVENT.CLICK, function(data){
				  var deptId = '';
				  for(var i=0;i<dept.length;i++){
				  	if(data.name == dept[i].DEPT_NAME){
				  		deptId = dept[i].DEPT_ID;
				  	}
				  }
				  me.params.deptId = deptId;
				  me.tplGrid.load(me.params); 
			  });
				me.pMBarEcharts=rankEcharts;
    		});
    	});
    	this.callService({key:'getPast',params: this.params},function(data){
	    			var d = data.getPast;
	    			var xData = [];
	    			for(var i=0;i<d.length;i++){
	    				if(i==0){
	    					xData.push(d[0].INYEAR);
	    				}else{
	    					if(d[i].INYEAR != d[i-1].INYEAR){
	    						xData.push(d[i].INYEAR);
	    					}
	    				}
	    			}
	    			
	    			var dept = [];
	    			for(var i=0;i<d.length;i++){
	    				if(d[i].INYEAR == xData[0]){
	    					dept.push(d[i].DEPT_NAME);
	    				}
	    			}
	    			var test = {};
	    			if(dept.length>5){
	    				for(var i=5;i<dept.length;i++){
	    					test[dept[i]]=false;
	    				}
	    			
	    			}
					option = {
					    tooltip : {
					        trigger: 'axis'
					    },
					    legend: {
					    	selected : test,
					    	data : dept
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
					    series : function(){
					    	var its = [];
					    	for(var i=0;i<dept.length;i++){
					    		var dt = [];
					    		for(var j=0;j<d.length;j++){
						    		if(dept[i] == d[j].DEPT_NAME){
						    			dt.push(d[j].SUMS);
						    		}
					    		}
					    		var item = {name : dept[i],type:'line',data:dt}
					    		its.push(item);
					    	}
					    	return its;
					    }()
					};
					var div = document.getElementById("loadline");
	    			var lineEcharts = echarts.init(div,'blue');
					lineEcharts.setOption(option);
					me.selectDept = lineEcharts.getOption().legend.selected;
					lineEcharts.on(echarts.config.EVENT.LEGEND_SELECTED, function(param){
						var selected = param.selected;
						me.selectDept = selected;
					});
					lineEcharts.on(echarts.config.EVENT.CLICK, function(data){
						me.isreflushGrid=false;
						var year = data.name;
						me.combobox.setValue(year);
						me.params.year = year;
						var value = data.value;
						var depts = [];
						for(var j=0;j<d.length;j++){
							if(d[j].INYEAR == year && d[j].SUMS == value && me.selectDept[d[j].DEPT_NAME] != false){
								depts.push(d[j].DEPT_ID);
							}
						}
						var deptids = "";
						for(var i=0;i<depts.length;i++){
							if(i == 0){
								deptids = depts[0];
							}else{
								deptids =deptids+","+depts[i];
							}
						}
						me.params.deptId = deptids;
						me.tplGrid.load(me.params); 
						me.isreflushGrid=true;
					});
					me.pmLineEcharts=lineEcharts;
    	    	});
    },
   	createComps : function(){
		var me = this;
	   	var comp1 = new NS.container.Container({
	            items : [this.title2,this.seach,this.table1],
	    		columnWidth: 1
	     });
		return comp1;
   	},
    createGrid:function(){
        var params = {start:0,limit:10};
        Ext.apply(this.params,params);
        this.callService([{key:'getResearchPaperRank',params:this.params}],
            function(respData){
                this.tableData = respData.getResearchPaperRank;
                this.gridFields =["RM","AUTHORS","DEPT_NAME","THESIS_TITLE","PERIODICAL","NJQY","THESIS_YEAR","IN_YEAR","PERIODICAL_NAME","IMPACT_FACTOR","SCHOOL_ORDER"];

                this.tplGrid = this.initXqGrid(this.tableData,this.gridFields,this.convertColumnConfig(),this.params);

                this.table1.add({
                    xtype : 'container',
                    layout : {type : 'vbox',align : 'left'},
                    items : [this.tplGrid.getLibComponent()]
                });
            });
    },
    /**
     * 初始化Grid
     */
    initXqGrid : function(data,fields,columnConfig,queryParams){
        var grid = new NS.grid.SimpleGrid({
            columnData : data,
            data:data,
            autoScroll: true,
            pageSize : 10,
            proxy : this.model,
            serviceKey:{
                key:'getResearchPaperRank',
                params:queryParams
            },
            multiSelect: false,
            lineNumber: true,
            fields : fields,
            columnConfig :columnConfig,
            border: false,
            checked: false
        });
        return grid;
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig : function(){    
        var arrays = this.gridFields; 
        var textarrays = "排名,论文作者,单位名称,论文题目,发表期刊,年卷期页,发表日期,收录日期,收录类别,影响因子,我校排名".split(",");
        var widtharrays = [50,150,120,100,100,100,70,70,70,70,70];
        var hiddenarrays = [false,false,false,false,false,false,false,false,false,false,false];
        var columns = [];
        for(var i=0;i<arrays.length;i++){
            var basic = {
                xtype : 'column',
                name : arrays[i],
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