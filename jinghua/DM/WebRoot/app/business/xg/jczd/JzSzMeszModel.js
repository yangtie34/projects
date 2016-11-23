/**
 * 助学金设置-名额设置
 */
NS.define('Business.xg.jczd.JzSzMeszModel',{
	//混合类
	mixins : ['Pages.xg.Util'], //工具
	extend : 'Business.xg.jczd.JzModel',
	//顶部菜单名称
    cdmc:'名额设置',
    //底部描述
    ms:'点击名称下钻可继续分配下一级的名额，下钻之前请先保存数据;',
    //批次类型切换
    select :'Y',
    //选择状态为 启用还是不启用的批次
    sfqy:'N', //0:不可用的批次；1:可用的批次；N:全部批次
    //隶属模块
	lsmk : '', //TbXgJzZxj
    //后台服务配置数据
    serviceConfigData:{
    	'queryGridDate' : 'jzMeszService?queryGridDate',
    	//保存名额
    	'validMe'		: 'jzMeszService?validMe',
    	//保存名额
    	'updateMesz'	: 'jzMeszService?updateMesz',
    	//自动分配
		'updateAutoFp'	: 'jzMeszService?updateAutoFp',
        //查询当前类型批次的总名额
        'queryMe'		: 'jzMeszService?queryMeByPclxId'
    },
    getSslx : function(){
    	return this.lsmk;
    },
    baseGridParams: function(){
    	return {pclxId:this.pclxId, pcId:this.pcId, lxId:this.lxId};
    },
    getGridParams : function(){
    	var par = this.baseGridParams();
    	par.start = 0;
    	par.limit = 25;
		return par;
    },
    beforeInit : function(){
    	this.updateServiceParams('queryGridDate', {sslx:this.getSslx()});  //不是自己的模板，需要调用系统的 updateServiceParams
    },
    /**
     * 请求数据[重写]
     */
    initData : function(){
    	this.beforeInit();
        this.callService([
            {key:'queryGridDate',params:this.getGridParams()},
            {key:'queryMe',params:{pclxId:this.pclxId}}
        ],function(data){
            this.initComponent(data);//初始化组件
        });
    },
    /**
     * 初始化tbar[重写]
     */
    getTbar : function(data){
        if(data.queryGridDate.success && data.queryGridDate.data.length!=0){
            var _data = data.queryGridDate.data[0];
            var queryType = _data.queryType;
            switch(queryType){
                case "XS" : queryType="BJ";
                    break;
                case "BJ" : queryType="ZY";
                    break;
                case "ZY" : queryType="YX";
                    break;
                case "YX" : queryType="XX";
                    break;
            }
            this.cclx = queryType;
            this.rangeArray = [{queryType:queryType,name : '首页'}];
            var tpl = new NS.Template(
                "<tpl for='.'> " +
                    "<a  class='zxj_index' href='javascript:void(0);' " +
                    "index = '{[xindex-1]}' " +
                    "queryType='{queryType}' " +
                    "nodeId='{nodeId}'>{name}" +
                    "</a>" +
                    "<span style='color: #000000;'>>></span>" +
                    "</tpl>");
            var indexCom = this.indexCom = new NS.Component({
                tpl : tpl,
                style : {
                    fontSize : '14px'
                },
                padding: '7 0 2 80',
                height : 30,
                data : this.rangeArray
            });
            indexCom.on('click',function(event,htmlElement){
                if(htmlElement.nodeName == 'A'){
                    var el = NS.fly(htmlElement);
                    var queryType = el.getAttribute("queryType");
                    var nodeId = el.getAttribute("nodeId");
                    var index = el.getAttribute("index");
                    this.rangeArray = NS.Array.slice(this.rangeArray,0,parseInt(index));
                    if(queryType == 'null' || queryType==null){
                        queryType='XX';
                    }
                    if(nodeId=='null' || nodeId==null){
                        nodeId='';
                    }
                    this.setColumnText(queryType);
                    this.cclx = queryType;
                    this.nodeId = nodeId;
                    this.loadGrid(); //this.cclx, this.nodeId
                }
            },this);
            return indexCom;
        }
    },
    /**
     * 初始化统计组件[重写]
     */
    getCountCom : function(data){
        var tplData = {
            pcMc	: this.pcMc,
            lxMc	: this.lxMc
        };
        tplData = this.applyCountCom(tplData);
        if(data.queryMe.success && data.queryGridDate.success){
        	var me = data.queryMe.data,
        		maxCclx = data.queryMe.cclx;
                tplData.Me = me;
                tplData.SyMe = data.queryGridDate.syme;
                this.maxCclx = maxCclx;
        }

        var tpl =new NS.Template( '<div>'+
            '<span style="font-size: 14px">{pcMc}['+
            '<span >{lxMc}</span>] 共有名额：<span class="zxj_blue zxj_textbold zxj_fontSize">{Me}</span>'+
            '，当前层未分配名额：<span class="zxj_blue zxj_textbold zxj_fontSize">{SyMe}</span>'+
            '</span>'+
            '<button style="margin-left:10px;" type="button" id="{saveBtn}">全部保存</button>'+
            '<button style="margin-left:10px;" type="button" id="{autoBtn}">自动分配</button>'+
            '<label style="margin-left:10px; color:red; display:none;" id="{hideLab}">批次已经启用，不能再修改名额！</label>'+
            '</div>');
        var queryCom = this.countCom= new NS.Component({
            tpl : tpl,
            data : tplData
        });
        queryCom.on('click',function(event,target){
            //监听添加按钮
            if(target.nodeName == 'BUTTON' && target.id == this.saveBtn){
                this.saveMe();
            }
            if(target.nodeName == 'BUTTON' && target.id == this.autoBtn){
                this.autoFp();
            }
        },this);
        return queryCom;
    },
    /**
     * 页面加载之后，切换批次类型之后执行该方法
     */
    pageAfterRender : function(){
    	var bz, none='none';
    	if(this.sfqy==1){
    		bz = true;
    		none = '';
    	}else{
    		bz = false;
    	}
		$('#'+this.saveBtn).attr('disabled', bz);
		$('#'+this.autoBtn).attr('disabled', bz);
		$('#'+this.hideLab).css('display', none);
    },
    /**
     * 刷新数据为组件
     */
    refreshData2CountCom :function(data){
        var tplData = {
            pcMc	: this.pcMc,
            lxMc	: this.lxMc
        };
        tplData = this.applyCountCom(tplData);
        if(data.queryGridDate.success && data.queryMe.success){
        	    var me = data.queryMe.data,
	    		maxCclx = data.queryMe.cclx;
	            tplData.Me = me;
                tplData.SyMe = data.queryGridDate.syme;
	            this.maxCclx = maxCclx;
        }
        this.countCom.refreshTplData(tplData);
    },
    /**
     * 动态组装 统计数据和el
     */
    applyCountCom : function(tplData){
    	this.idF = NS.id()+'-pcsz_';
    	this.saveBtn = this.idF+'saveBtn';
    	this.autoBtn = this.idF+'autoBtn';
    	this.hideLab = this.idF+'hideLab';
    	NS.apply(tplData, {saveBtn:this.saveBtn, autoBtn:this.autoBtn, hideLab:this.hideLab});
        return tplData;
    },
    /**
     * 初始化grid[重写]
     */
    getGrid : function(data){
        if(data.queryGridDate.success){
            var basic = {
                data : data.queryGridDate,
                plugins: [
                    new NS.grid.plugin.CellEditor(),
                    new NS.grid.plugin.HeaderQuery()],
                proxy  : this.model,
                serviceKey : {
                    key : 'queryGridDate',
                    params:this.baseGridParams()
                },
                fields  : ['id','mc','zrs','me','bl','queryType','nodeId'],
                border  : false,
                checked : true,
                multiSelect: true,
                pageSize   : 2000,
                autoScroll : true,
                columnConfig : this.convertColumnConfig()//动态创建列表
            };
            var grid = this.grid = new NS.grid.SimpleGrid(basic);
            grid.bindItemsEvent({
                mc : {event : 'linkclick',scope : this,fn : this.onGridDrill}
            });
            this.setColumnText(data.queryGridDate.queryType, true);
            return grid;
        }
    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig : function(){
    	var arrays 		= ['id','mc','zrs','me','bl'],
        	textarrays  = "id,名称,参与评选人数,名额,名额比例(%),".split(","),
        	widtharrays = [120,150,150,150,150],
        	hiddenarrays= [true,false,false,false,false],
        	columns 	= [];
        for(var i=0;i<arrays.length;i++){
            var basic = {
                xtype : 'column',
                name  : arrays[i],
                text  : textarrays[i],
                width : widtharrays[i],
                hidden: hiddenarrays[i],
                align : 'center'
            };
            var mc = arrays[i];
            if(mc == "mc"){
                NS.apply(basic,{
                    xtype : 'linkcolumn',
                    renderer : function(v,data,rowIndex,colIndex){
                        if(data.queryType != 'END'){
                            var aht = "<a href='javascript:void(0);'>{0}</a>";
                            return NS.String.format(aht,v);
                        }else{
                            return v;
                        }
                    }
                });
            }else if(mc=="me"){
                NS.apply(basic, {editor : 'textfield'});
            }
            columns.push(basic);
        }
        return columns;
    },
    /**
     * 当表格被钻取
     */
    onGridDrill : function(txt,rowIndex,colIndex,data){
        this.rangeArray.push({queryType : data.queryType, nodeId : data.nodeId, name : txt});
        this.setColumnText(data.queryType);
        this.cclx = data.queryType;
        this.nodeId = data.nodeId;
        this.loadGrid(); //this.cclx, this.nodeId
    },
    /**
     *根据查询类型设置列表显示字段
     * @param queryType
     */
    setColumnText  : function(queryType, count){ //第一次也要处理列名，count有值即为第一次
    	if(!count){
    		this.indexCom.refreshTplData(this.rangeArray);
    	}
        switch(queryType){
            case "XX" : this.grid.getColumn("mc").setText("学校");
                break;
            case "YX" : this.grid.getColumn("mc").setText("系部");
                break;
            case "ZY" : this.grid.getColumn("mc").setText("专业");
                break;
            case "BJ" : this.grid.getColumn("mc").setText("班级");
                break;
            case null : this.grid.getColumn("mc").setText("首页");
                break;
        }
    },
    /**
     * 切换批次-刷新数据[重写]
     */
    refreshDataByPclx : function(){
        this.callService([
            {key:'queryGridDate',params:{pclxId:this.pclxId, pcId:this.pcId, lxId:this.lxId}},
            {key:'queryMe',params:{pclxId:this.pclxId}}
        ],function(data){
            if(data.queryGridDate.success && data.queryGridDate.data.length!=0){
                var _data = data.queryGridDate.data[0];
                var queryType = _data.queryType;
                switch(queryType){
                    case "XS" : 
                    	queryType="BJ"; break;
                    case "BJ" : 
                    	queryType="ZY"; break;
                    case "ZY" : 
                    	queryType="YX"; break;
                    case "YX" : 
                    	queryType="XX"; break;
                }
                this.rangeArray = [{queryType:queryType,name : '首页'}];
                this.setColumnText(queryType);
                this.cclx = data.queryType;
                this.nodeId = _data.id;
                this.grid.loadData(data.queryGridDate);
                this.refreshData2CountCom(data);
                //刷新要放在 刷新统计cmp之后
                this.pageAfterRender();
            }
        });
    },

    /**
     * 加载grid数据
     */
    loadGrid : function(queryTpye,nodeId){
        var params = {};
        params.sslx = this.getSslx(),
        params.pcId = this.pcId;
        params.lxId = this.lxId;
        params.pclxId = this.pclxId;
        params.queryType = this.cclx;
        params.nodeId = this.nodeId;

        this.callService([
            {key:'queryGridDate',params:params},
            {key:'queryMe',params:{pclxId:this.pclxId}}
        ],function(data){
            if(data.queryGridDate.success && data.queryMe.success){
                this.grid.loadData(data.queryGridDate);
                this.refreshData2CountCom(data);
                //刷新要放在 刷新统计cmp之后
                this.pageAfterRender();
            }
        });
    },
    
    /**
     * 保存名额
     * 校验名额正确性
     */
    saveMe : function(){
    	var modifyRows = this.grid.getModify(),
			len 	   = modifyRows.length;
		if(len < 1){
			NS.Msg.info('您没有更改数据，不需要保存!');
			return;
		}else{
			//first 当前用户权限的最大层次数据不允许修改  this.maxCclx;//max   this.cclx;//
			var b = false;
			switch(this.maxCclx){
				case "XS" : 
					break;
				case "BJ" : 
					b = this.cclx=='XS' ? true : false; 
					break;
				case "ZY" : 
					b = this.cclx=='XS' || this.cclx=='BJ' ? true : false; 
					break;
				case "YX" : 
					b = this.cclx=='XS' || this.cclx=='BJ' || this.cclx=='ZY' ? true : false; 
					break;
				case "XX" :
					b = this.cclx=='XS' || this.cclx=='BJ' || this.cclx=='ZY' || this.cclx=='YX' ? true : false;
					break;
	        }
			if(!b){
				this.XgError('您不能修改被上级指定的数据！可下钻到下一级进行修改。');
	    		return;
			}
			
			//1. 校验每一行都要 小于 该行总人数
			var validNextAry = [],
				validLastAry = [],
				validObj 	 = {},
				autoFpData	 = {};
			for(var i=0; i<len; i++){
				var da  = modifyRows[i],
					zrs = Number(da.zrs),
					me  = Number(da.me),
					nodeId = da.nodeId;
				if(me > zrs){
					this.XgError(da.mc+'的名额不能大于总人数！');
					return
				}
				validNextAry.push({
					nodeId : nodeId,
					me	   : me
				});
				autoFpData[nodeId] = me;
			}
			
	    	//2. 校验 各行名额相加 小于总名额
			var allRows = this.grid.getAllRow(),
				lenJ	= allRows.length,
				meThis	= 0; //所有行的名额数之和
			for(var j=0; j<lenJ; j++){
				var da  = allRows[j],
					nodeId = da.nodeId,
					mc	= da.mc,
					me  = da.me;
				meThis += Number(me);
				validLastAry.push(nodeId);
				validObj[nodeId] = mc;
			}
			
			//3. 检验下一级名额数 即当前名额不能小于下一级的名额总数
			var par = {pclxId:this.pclxId, cclx:this.cclx, dataLast:validLastAry, meLast:meThis};
			if(this.cclx=='BJ'){ //如果当前是班级层次，不用校验下级名额
				this.submitMe();
			}else{
				par.dataNext = validNextAry;
			}
			this.mask.show();
			this.XgAjax('validMe', par, function(data){
				this.mask.hide();
				if(data.success){
					if(data.info){
						if(data.last){ //名额数超了
							this.XgError('您设置的名额总和不能大于您可分配的名额数：'+data.last+'！');
						}else if(data.next){ //如果修改的名额数 小于 下一级的名额总数，需要提示其再次初始化
							var ary = data.next,
								lenK= ary.length,
								info= '';
							for(var i=0; i<lenK; i++){
								info += '<span class="wbh-common-orange">'+validObj[ary[i]]+'的名额数小于下一级节点的名额数之和。</span><br>';
							}
//							this.XgError(info+'<br>请先修改下一级数据再保存！');
//							this.autoFpParams = {pclxId:this.pclxId, pcId:this.pcId, cclx:this.cclx||'XX', autoFpData:autoFpData};
							this.autoFpParams = {autoFpData:autoFpData};
							this.autoSzWin(info+'<b>名额产生变化，您需要执行自动分配才能保证名额的正确性！</b>');
						}
					}else{
						this.submitMe();
					}
				}else{
					this.XgError();
				}
			},this);
		}
    },
    // 提交
    submitMe : function(){
        var rowData = this.grid.getAllRow(),
        	len = rowData.length;
        	ary = [];
    	NS.Msg.changeTip('提示','您确定要保存名额吗?',function(){
	        for(var i=0; i<len; i++){
	        	var data = rowData[i],
	        		zzjgId = data.nodeId,
	        		me = data.me;
	        	ary.push({zzjgId:zzjgId, me:me});
	        }
	        this.mask.show();
	        this.XgAjax('updateMesz',{pclxId:this.pclxId, data:ary},function(data){
		        this.mask.hide();
	        	if(data.success){
	        		NS.Msg.info('保存成功！');
	        		this.loadGrid(); //this.cclx, this.nodeId
	        	}else{
	        		this.XgError();
	        	}
	        },this);
    	},this);
    },
    
    /**
     * 初始化
     */
    autoFp : function(){
    	if(this.cclx=="BJ"){
    		NS.Msg.info('当前层次无需再自动分配！');
    	}else{
    		//是否有变更
	    	if(this.grid.getModify().length > 0){
	    		NS.Msg.info('请保存数据后再执行自动分配!');
	    		return;
	    	}
	    	//是否选择数据
	    	var rawsValues = this.grid.getSelectRows(),
	    		len = rawsValues.length,
	    		autoFpData = {};
	    	if(len < 1){
	    		NS.Msg.info('请至少选择一条数据执行自动分配！');
	    		return;
	    	}
	    	for(var i=0; i<len; i++){
	    		var data = rawsValues[i];
	    		if(data.me==0 || data.me==''){ //名额为空，不予分配
	    			NS.Msg.error('您选择的数据"'+data.mc+'"没有名额，<br>请返回上一层自动分配或直接分配名额之后再执行自动分配！');
	    			return;
	    		}
	    		autoFpData[data.nodeId] = Number(data.me);
	    	}
			this.autoFpParams = {autoFpData:autoFpData};
	        this.autoSzWin();
    	}
    },
    /**
     * win
     */
    autoSzWin : function(msg){
        var titleCom = new NS.Component({
	           html 	: msg || '',
	           colspan  : 2
	        });
        var bcCom = this.bcCom = new NS.form.field.Radio({
                name	:'fpld',
                boxLabel:'只分配下一层',
                checked:true,
                colspan : 1
            });
        var bjCom = this.bjCom = new NS.form.field.Radio({
                name	:'fpld',
                boxLabel:'分配到班级',
                colspan : 1
            });
        var htmlCom = new NS.Component({
	           html		:'说明：执行分配将更新已经分配的名额',
	           colspan  : 2
	        });
        var window = this.fpWin = new NS.window.Window({
            title  : '选择分配粒度',
            height : 200,
            width  : 280,
            border : false,
            modal  : true,
            autoShow  : true,
            autoScroll: true,
            items  : [new NS.container.Container({
            	layout : {
	                type    : 'table',
	                columns : 2
	            },
                margin: '15 15 10',
            	items : [titleCom, bcCom, bjCom, htmlCom]
            })],
            buttons : [
                {xtype : 'button',text : '执行分配',name : 'zxfp'},
                {xtype : 'button',text : '取消',name : 'cancel'}
            ]
        });
        window.bindItemsEvent({
            zxfp   : {event:'click', fn:this.autoSubmitData, scope:this},
            cancel : {event:'click', fn:this.closeWin,       scope:this}
        });
    },

    autoSubmitData : function(){
        var f 	 = this.bjCom.getValue(),
        	fpld = f ? 'BJ' : 'QT';
        this.autoFpParams.fpld = fpld;

    	var par  = this.baseGridParams();
    	par.cclx = this.cclx||'XX';
    	par.sslx = this.getSslx();
    	NS.apply(par, this.autoFpParams);
    	
        this.mask.show();
        this.XgAjax('updateAutoFp', par, function(data){
            this.mask.hide();
        	if(data.success){
        		NS.Msg.info('自动分配成功！');
        		this.loadGrid();
        	}else{
        		this.XgError();
        	}
    		this.closeWin();
        },this); 
    },
    
    closeWin : function(){
    	this.fpWin.close();
    }
	
});
    