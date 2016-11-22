NS.define('Business.xg.wjcf.tjbb.WjcfTjfx',{
	extend : 'Template.Page',
	mixins : [
	          'Business.xg.wjcf.tjbb.WjcfTjfx_grid',//grid
	          'Business.xg.wjcf.tjbb.WjcfTjfx_tbar',//tbar
	          'Business.xg.wjcf.tjbb.WjcfTjfx_mxWin',
	          'Business.xg.wjcf.tjbb.WjcfTjfx_tools'
	          ],
	cssRequires : ['app/business/xg/wjcf/template/style/wjcf-tongji.css',
	               'app/business/xg/wjcf/template/style/wjcftj-common.css'],
	               
	tplRequires : [{fieldname : 'tplCflxTjfx',path : 'app/business/xg/wjcf/template/tjbb/wjcftjfx.html'}],
	               
	modelConfig : {
		serviceConfig : {
			//表头数据
			'queryTableHeader' : 'base_queryForAddByEntityName',
            //查询grid
            'queryGridData' : 'wjcfTjService?queryWjcfTjfx',
            //
			'queryWjcfMx' : 'wjcgJgService?queryGrid',
            //查询处分类型
            'queryWjcf' :  'wjcfTjService?queryCflxList'
		}
	},
	//顶部菜单名称
	cdmc : '',
	//隶属模块
	lsmk : '',
	//实体名
	entityName : '',
	//页面参数
	gridParams:function(){
    	return {
    		start:0,
    		limit:25
    	};
    },
	init : function(){
		this.tplData = {};
		this.pageParams = this.gridParams();
		this.callService([
			  		        {key:'queryWjcf',params:{start:0,limit:50,lsmk:this.lsmk}}
			  		     ],function(data){
			this.tplData.cflxData = data.queryWjcf;
			if(data.queryWjcf.count <= 0){
				 var page= new NS.Component({

					 html:"<style> body{ background:#ececec}.style-lly{ width:300px; margin:0 auto; margin-top:20%; " +
			   			"background:url(app/business/xg/jczd/template/image/wupici.png) no-repeat left top; padding-left:150px; padding-top:10px; height:120px; font:bold 24px 'microsoft yahei'; color:#ff6600; text-shadow:1px 1px 1px #fff;}</style>"
			   			+"<div class='style-lly'>当前没有可以查询的违纪类型！</div>"

					});

				this.setPageComponent(page);
			}
			this.initData();
		});
	},
	initData : function(){
		this.callService([
		  		        {key:'queryGridData',params:{lsmk:this.lsmk,start:0,limit:25}}
		  		     ],function(data){
		  				this.initComponent(data);
		  		});
	},
	initComponent : function(data){
		if(data.queryGridData.count > 0){
			this.rangeArray = [{queryType:data.queryGridData.data[0].queryType,name : data.queryGridData.data[0].dwmc}];
		}else{
			this.rangeArray = [{queryType:'XX',name : '首页'}];
		}
		//学年学期
    	this.tplData.xnxq = this.getCurrentXnXq();
    	this.tplData.username = (MainPage.getUserName() ==""?MainPage.getLoginName():MainPage.getUserName());
    	this.tplData.cdmc = this.cdmc;
    	this.chartData = this.changeData(data.queryGridData.data);//grid转化为chart数据
    	//初始化图表
    	this.createChart();//图表组件
    	//初始化tbar
    	this.getTbar(data);
    	//初始化grid
    	this.initGrid(data);
    	//初始化主页面tpl
    	this.initTplComponent();

	},
	/**
	 * 初始化tpl组件
	 */
	initTplComponent : function(){
		this.tplZxjTjfxCom = new NS.Component({
			border : false,
			data : this.tplData,
			tpl : this.tplCflxTjfx//主页面tpl
		});
		this.tplZxjTjfxCom.on('click',function(event,target){
			if(target.nodeName == 'BUTTON' && target.name == 'wjcftj-export'){
				if("XS" != this.pageParams.queryType){
					this.exportExcel();
				}else{
					this.exportData();
					}
				}
		},this);
		this.initPage();
		this.initMask();
	},
	/**
	 * 根据批次类型刷新grid
	 */
	refreshDataByPclx : function(){
		this.callService([
			  		        {key:'queryGridData',params:{lsmk:this.pageParams.lsmk,start:0,limit:25,pcLxId:this.pageParams.pcLxId,lxId:this.pageParams.lxId}}
			  		     ],function(data){
			  if(data.queryGridData.success && data.queryGridData.length != 0){
				  var data = data.queryGridData.data[0];
                  var queryType = data.queryType;
                  switch(queryType){
                      case "END" : queryType="XS"
                          break;
                      case "XS" : queryType="BJ"
                          break;
                      case "BJ" : queryType="ZY"
                          break;
                      case "ZY" : queryType="YX"
                          break;
                      case "YX" : queryType="XX"
                          break;
                  }
                    this.rangeArray = [{queryType:queryType,name : '首页'}];
                    this.setColumnText(queryType);
                    this.loadGrid(queryType, data.id);
			  }
		});
	},
	loadGrid : function(queryType,nodeId){
		var params = {};
        params.pcId = this.pcId;
        params.lxId = this.lxId;
        if(nodeId != ''){
            params.queryStatus = this.rangeArray;
        }
        params.nodeId = nodeId;
        params.queryType = queryType;
        this.grid.load(params);
	},
	/**
	 * 初始化流程主页面
	 */
	initPage : function(){
		this.page = new NS.container.Container(
				{
					items:this.tplZxjTjfxCom,
					autoScroll: true
				});
		this.page.on('afterrender',function(){
			//渲染grid
			this.grid.render('wjcftjfx-grid');
			this.indexCom.render('wjcftjfx-select');
			//选择统计图
			this.fusionChart.render("wjcftjfx_char");
		},this);

		this.setPageComponent(this.page);
	},
	/**
	 * 初始化遮罩
	 */
	initMask : function(){
		this.mask = new NS.mask.LoadMask({
			target : this.page,
			msg : '数据加载中,请稍候...'
		});
	},
	exportExcel :function(){
        this.mask.show();
        var extraParams =this.pageParams;
        extraParams.lsmk = this.lsmk;
        // 导出
        var serviceAndParams ={
            servicAndMethod:"wjcfTjService?queryWorkbook",
            params:extraParams||{}
        } ;
        var strParams = JSON.stringify(serviceAndParams);
        window.location.href="xjda/exportXqzcTjfx.action?serviceAndParams="+strParams;
        this.mask.hide();
    },
    /**
	 * 导出学生注册信息
	 */
	exportData:function(){
		NS.entityExcelExport({
			grid: this.grid,
//			queryParams: params,
            entityName : 'VTbXgJzZxjjg',
            title :'助学金学生名单' ,
            controller : this
        });
	},
  //转化grid数据为图表数据
    changeData:function(data){
    	var xName ="学校";
    	 switch(this.rangeArray[this.rangeArray.length-1].queryType){
    	 case "XX" : xName="学校";break;
         case "YX" : xName="系部";break;
         case "ZY" : xName="专业";break;
         case "BJ" : xName="班级";break; }
    	var category = new Array();
    	var dsArray = new Array();
    	var colorArray = this.getColor();
    	for(var a in data){
    		category.push({"label":data[a].dwmc});
    	}
    	for(var i=0;i<this.tplData.cflxData.length;i++){
    		var cMc = this.tplData.cflxData[i].MC;
    		var d = new Array();
    		for(var a in data){
        		d.push({"value":data[a][i+1]});
    		}
    		dsArray.push({"seriesname":cMc, color:colorArray[i], "showvalues":"0", "data":d});
    	}
    	var dataset =dsArray;
    	var chartData = {"chart":{"caption" : this.cdmc, "xAxisName" : xName, "yAxisName" : "人数", "numberSuffix" : "人","formatNumber":"0","formatNumberScale":"0" },
						"categories":[{"category":category}],
						"dataset":[dataset]
						};
    	return chartData;
    },
    //创建图表
	createChart:function(){
        if (!this.fusionChart) {
            this.fusionChart = new FusionCharts("FusionCharts/charts/MSColumn3D.swf","wjlxtjfx_char1", "900", "400");
        }
        this.fusionChart.setJSONData(this.chartData);
	}
});