/**
 * 奖助审核-基础模板页面[zhangyc]
 */
NS.define('Business.xg.jczd.JzShModel', {
	extend : 'Business.xg.jczd.JzModel',
	requires:['Business.xg.jczd.CreateJzCom'],
    mixins:['Business.xg.jczd.JzShModel_win'],
    //底部描述
    ms:'点击名称可以下钻，查看下级审核信息',
    /**
     * 后台服务配置
     */
    serviceConfigData1:{
    	 'queryJzSh':'jzShService?queryJzInfo',//查询学金审核信息
         'queryStuInfo':'jzShService?queryStuInfoById',//根据学生id查询学生基础信息
         'queryStuTmInfo' :'jzShService?queryStuTmInfoById',//查询学生提名信息
         'queryStuHistory' :'jzShService?queryStuHistoryBylx',//查询奖学金历史
         'queryShHistory':'jzShService?queryShHistoryById',//查询审核历史记录
         'saveShInfo':'jzShService?saveShInfo'//审核
    },
    //批次类型切换
    select :'Y',
        /**
         * 请求数据[重写]
         */
    initData : function(){
        this.callService({key:'queryJzSh',
        	params:{pcId:this.pcId,lxId:this.lxId,lsmk:this.lsmk}},function(data){
            this.initComponent(data);//初始化组件
        });
    },
    /**
     * 初始化tbar[重写]
     */
    getTbar : function(data){
        if(data.queryJzSh.success && data.queryJzSh.data.length!=0){
            var data = data.queryJzSh.data[0];
            var queryType = data.queryType;
//            switch(queryType){
//                case "END" : queryType="XS";
//                    break;
//                case "XS" : queryType="BJ";
//                    break;
//                case "BJ" : queryType="ZY";
//                    break;
//                case "ZY" : queryType="YX";
//                    break;
//                case "YX" : queryType="XX";
//                    break;
//            }
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
                    this.loadGrid(queryType,nodeId);
                }
                //改变显示列表字段
                this.setColumnText(queryType);
            },this);
            return indexCom;
        }
    },
    /**
     * 初始化统计组件[重写]
     */
    getCountCom : function(data){
    	var queryStr=this.queryStr= this.lsmk+'_sh_queryStr';
    	var tm = this.lsmk+'_sh_tm';
    	var sh = this.lsmk+'_sh_sh';
        var html = '<div>'+
                        '<label>学号/姓名/身份证号：</label>' +
                        '<input id="'+queryStr+'" type="text" value="请输入内容回车查询..."' +
                         ' onfocus=\'javascript:if(this.value=="请输入内容回车查询...")this.value="";\'' +
                         ' onblur=\'javascript:if(this.value=="")this.value="请输入内容回车查询...";\' />'+
                        '<button style="margin-left:10px;" type="button" name="'+tm+'">提名</button>'+
                        '<button style="margin-left:10px;" type="button" name="'+sh+'">审核</button>'+
                    '</div>';
        var queryCom = new NS.Component({
           html:html
        });
        
    	queryCom.on('click',function(event,target){
			//监听添加按钮
			if(target.nodeName == 'BUTTON' && target.name == tm){
                this.createTmWin();//创建提名win
			}
			if(target.nodeName == 'BUTTON' && target.name == sh){
                var data = this.grid.getSelectRows();
                if(data.length>0){
                 this.queryType = data[0].queryType;
                 if(this.queryType=='END'){
                     this.zxjId=NS.Array.pluck(data,'ZXJID');
                 }else{
                     this.zxjId=NS.Array.pluck(data,'nodeId');
                 }
                this.createPlShWin();
                }else{
                    NS.Msg.info('请选择一条记录！');
                }
			}
		},this);
        queryCom.on('keydown',function(event,target){
                if(event.getKey() == 13){
                    this.doForChangeColumns("XS");//改变列
                    var queryStr = $('#'+this.queryStr).val();
                    var params = {};
                    params.pcId = this.pcId;
                    params.lxId = this.lxId;
                    params.queryStr = queryStr;
                    params.nodeId = '';
                    params.queryType = "XS";
                    params.lsmk = this.lsmk;
                    this.grid.load(params);

                }

         },this);

        
        return queryCom;
    },
    /**
     * 初始化grid[重写]
     */
    getGrid : function(data){
        if(data.queryJzSh.success){
            var basic = {
                data : data.queryJzSh,
                plugins: [
                    new NS.grid.plugin.HeaderQuery()],
                autoScroll: true,
                pageSize : 2000,
                proxy : this.model,
                serviceKey : {
                    key : 'queryJzSh',
                    params:{pcId:this.pcId,lxId:this.lxId,lsmk:this.lsmk}
                },
                multiSelect: true,
                lineNumber: false,
                checked:true,
                fields : ['ID','ZXJID','XH','XM','XBMC','SFZH','RXNJMC','JE','shzt',
                            'mc','zrs','tmrs','tgrs','dshrs','czl',
                            'queryType','nodeId'],
                columnConfig : this.convertColumnConfig()//动态创建列表
            };
            var grid=this.grid = new NS.grid.SimpleGrid(basic);
            grid.bindItemsEvent({
                mc : {event : 'linkclick',scope : this,fn : this.onGridDrill},
                czl: {event : 'linkclick',scope : this,fn : this.zxjShOperation}
            });
            return grid;
        }
    },
    /**
     * 刷新数据[重写]
     */
    refreshDataByPclx : function(){
        this.callService({key:'queryJzSh',params:{pcId:this.pcId,lxId:this.lxId,lsmk:this.lsmk}},function(data){
          if(data.queryJzSh.success && data.queryJzSh.data.length!=0){
              var data = data.queryJzSh.data[0];
              var queryType = data.queryType;
//              switch(queryType){
//                  case "END" : queryType="XS";
//                      break;
//                  case "XS" : queryType="BJ";
//                      break;
//                  case "BJ" : queryType="ZY";
//                      break;
//                  case "ZY" : queryType="YX";
//                      break;
//                  case "YX" : queryType="XX";
//                      break;
//              }
                this.rangeArray = [{queryType:queryType,name : '首页'}];
                this.setColumnText(queryType);
                this.loadGrid(queryType,data.id);
            }
        });

    },
    /**
     *  动态创建columns
     * @return {Array}
     */
    convertColumnConfig : function(){
        var arrays =['id','XH','XM','XBMC','SFZH','RXNJMC','JE','shzt',
                     'mc','zrs','tmrs','tgrs','dshrs','czl'];
        var textarrays = "ID,学号,姓名,性别,身份证号,入学年级,金额,审核状态,名称,总人数,提名人数,通过人数,待审核人数,操作,".split(",");
        var widtharrays = [120,150,150,120,200,120,120,120,120,120,120,120,120,150];
        var hiddenarrays = [true,true,true,true,true,true,true,true,false,false,false,false,false,false];
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
            }else if(mc=="zrs" || mc=="tmrs" || mc=="tgrs" || mc=="dshrs"){
                NS.apply(basic,{
                    xtype : 'linkcolumn',
                    renderer : function(v,data,rowIndex,colIndex){
                            if( v!='0'){
                                var aht = "<a href='javascript:void(0);'>{0}</a>";
                                return NS.String.format(aht,v);
                            }
                            return v;
                    }
                });
            }else if(mc == "czl"){
                NS.apply(basic,{
                    xtype : 'linkcolumn',
                    renderer : function(v,data,rowIndex,colIndex){
                        var queryType = data.queryType;
                        var array = v.split(','), i = 0, len = array.length, as = "";
                        if(data.dshrs!='0'){
                            var aht = "<a href='javascript:void(0);'>{0}</a>";
                            return NS.String.format(aht,array[0]);
                        }else{
                            return array[0];
                        }


                    }
                });
            }
            columns.push(basic);
        }
        return columns;
    },
    /**
     *  操作列
     */
    zxjShOperation : function(txt,rowIndex,colIndex,data){
        this.queryType = data.queryType;
        if("END"==data.queryType && "审核"==txt){
            this.zxjId = data.ZXJID;
            this.callService([
                {key:'queryStuInfo',params:{xsId:data.ID}},
                {key:'queryStuHistory',params:{xsId:data.ID,lsmk:this.lsmk}},
                {key:'queryStuTmInfo',params:{zxjId:data.ZXJID,lsmk:this.lsmk}},
                {key:'queryShHistory',params:{id:data.ZXJID,lsmk:this.lsmk}}
            ],function(data){
                if(data.queryStuInfo.success &&
                    data.queryStuHistory.success &&
                    data.queryStuTmInfo.success &&
                    data.queryShHistory.success){
                    this.createShWin(data);
                }
            },this);
        }else{
            this.zxjId = data.nodeId;
            this.createPlShWin();
        }

    },
    /**
     * 当表格被钻取
     */
    onGridDrill : function(txt,rowIndex,colIndex,data){
        this.rangeArray.push({queryType : data.queryType,nodeId : data.nodeId,name : txt});
        this.loadGrid(data.queryType,data.nodeId);
        this.setColumnText(data.queryType);
    },

    /**
     *根据查询类型设置列表显示字段
     * @param queryType
     */
    setColumnText  : function(queryType){
        this.indexCom.refreshTplData(this.rangeArray);
        if(queryType == 'XS'){
            this.grid.getColumn("mc").setText("姓名");
            this.doForChangeColumns("XS");
        }else{
            this.grid.getColumn("mc").setText("部门名称");
            this.doForChangeColumns("");
        }
//        switch(queryType){
//            case "XX" : this.grid.getColumn("mc").setText("学校");
//                this.doForChangeColumns("");
//                break;
//            case "YX" : this.grid.getColumn("mc").setText("系部");
//                this.doForChangeColumns("");
//                break;
//            case "ZY" : this.grid.getColumn("mc").setText("专业");
//                this.doForChangeColumns("");
//                break;
//            case "BJ" : this.grid.getColumn("mc").setText("班级");
//                this.doForChangeColumns("");
//                break;
//            case "XS" : this.grid.getColumn("mc").setText("姓名");
//                this.doForChangeColumns("XS");
//                break;
//            case null : this.grid.getColumn("mc").setText("首页");
//                this.doForChangeColumns("");
//                break;
//        }
    },
    /**
     * 加载grid数据
     */
    loadGrid : function(queryTpye,nodeId){
        var params = {};
        params.pcId = this.pcId;
        params.lxId = this.lxId;
        if(nodeId != ''){
            params.queryStatus = this.rangeArray;
        }
        params.nodeId = nodeId;
        params.queryType = queryTpye;
        params.lsmk = this.lsmk;
        this.grid.load(params);
    },
    /**
     * 改变列
     */
    doForChangeColumns : function(queryType){
        var arr1 = ['XH','XM','XBMC','SFZH','RXNJMC','JE','shzt'];
        var arr2 = ['mc','zrs','tmrs','tgrs','dshrs'];
        if(queryType == "XS"){
            this.grid_ColumnShow(arr1,this.grid);//显示
            this.grid_ColumnHide(arr2,this.grid);//隐藏
        }else{
            this.grid_ColumnShow(arr2,this.grid);//显示
            this.grid_ColumnHide(arr1,this.grid);//隐藏
        }
    },
    /**
     * grid列表显示
     * @param cArray
     * @param grid
     */
    grid_ColumnShow : function(cArray,grid){
        for(var i=0;i<cArray.length;i++){
            grid.getColumn(cArray[i]).show();
        }
    },
    /**
     * grid列表隐藏
     * @param cArray
     * @param grid
     */
    grid_ColumnHide : function(cArray,grid){
        for(var i=0;i<cArray.length;i++){
            grid.getColumn(cArray[i]).hide();
        }
    }
});