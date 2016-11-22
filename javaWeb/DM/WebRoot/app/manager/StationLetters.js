NS.define('Manager.StationLetters',{
    extend : 'Template.Page',
    mixins : ['Manager.station.WriteLetter'],
    modelConfig : {
        serviceConfig : {
            'query' : {
                service : "station_queryLetters",
                params : {start : 0,limit : 25,type : 'Receive'}
            },
            add : 'station_saveLetter',
            delete : 'station_deleteLetter',
            update : 'station_updateLetter',
            logicDelete : 'station_deleteLetterLogic',
            physicalDelete : 'station_deleteLetterPhysical',
            queryJzg : 'station_queryJzg'
        }
    },
    cssRequires : [
        'app/manager/css/stationletter.css'
    ],
    dataRequires : [
        {fieldname : 'initGridData',key : 'query'}
    ],
    advanceDataRequires : [
        {fieldname : 'jzgList',key : 'queryJzg'}
    ],
    init : function(){
        this.initComponent();
    },
    initComponent : function(){
        var east = this.initEast();
        var center = this.initCenter();
        var container = new NS.container.Container({
            layout : 'border',
            items : [east,center]
        });
        this.setPageComponent(container);
    },
    initEast : function(){
        var tree = new NS.container.Tree({
            treeData:{
                text : '工具栏',
                id : 0,
                expanded : true,
                children : [
                    {text : '收件箱',id : 1,pid:0,leaf: true ,type : 'Receive'},
                    {text : '发件箱',id : 2,pid:0,leaf: true ,type : 'Send'},
                    //{text : '草稿箱',id : 3,pid:0,leaf: true,type : 'Draft' },
                    {text : '垃圾箱',id : 4,pid:0,leaf: true,type : 'Garbage' }
                ]
            },
            title : null,
            maxWidth : 200,
            rootVisible : true,
            border : true,
            region : 'west',
            width : '30%',
            margin : '0 0 0 0',
            multyFields:[{"columnName":"节点","dataIndex":"text"},
                {"columnName":"ID","dataIndex":"id"}]
        });
        tree.on('itemclick',function(tree,data){
            var type = data.type;
            this.queryLetters(type);
        },this);
        return tree;
    },
    initCenter : function(){
        var tbar = this.initTbar();
        var grid = this.grid = new NS.grid.Grid({
            data : this.initGridData,
            columnConfig : this.getColumnsConfig(),
            tbar : tbar,
            proxy : this.model,
            serviceKey : 'query',
            region : 'center',
            fields : ["id","title","content",'sendTime','jzgId','cjr','sjrmc']

        });
        var component = this.getGridTypeComponent();
        var container = new NS.container.Container({
            width : '70%',
            region : 'center',
            layout : 'border',
            items : [
                component,
                grid
            ]
        });
        return container;
    },
    getColumnsConfig : function(){
        var sxarray = ["id","sjrmc",'cjr','sendTime',"title","content"];
        var textarray = ['id','收件人','发件人','时间','标题','内容'];
        var widtharrays = [120,180,140,140,450];
        var hiddenarrays = [true,true,false,false,false];
        var columns = [];
        for(var i=0;i<sxarray.length;i++){
            var name = sxarray[i];
            var basic = {
                xtype : 'column',
                name : sxarray[i],
                text : textarray[i],
                width : widtharrays[i],
                hidden : hiddenarrays[i],
                align : 'center'
            };
            if(name == 'title'){
                basic.renderer = function(v){
                    return NS.String.ellipsis(v,60);
                };
            }else if(name == 'content'){
                basic.flex = 1;
                delete basic.width;
                basic.renderer = function(v){
                    return NS.String.ellipsis(v,100);
                };
            }
            columns.push(basic);
        }
        return columns;
    },
    getGridTypeComponent : function(){
        var component = this.typeComponent = new NS.Component({
            region : 'north',
            height : 50,
            style : {
              backgroundColor : 'white',
              paddingTop : '12px',
              fontSize : '20px'
            },
            tpl : new NS.Template("<div><span>{type}共(<span style='color: blue;'>{num}</span>)封</span></div>"),
            data : {type : '收件箱',num : this.initGridData.count}
        });
        return component;
    },
    /**
     * 初始化工具栏
     */
    initTbar : function(){
        var getBtn = function(text,name,cls){return new NS.button.Button({text:text,name : name,iconCls : cls});}
        var addButton = getBtn('写信','save','page-save');
        var replyButton = getBtn('回复','reply','page-update');
        var sendOthers = getBtn('转发','forwarding','page-reducation');
        var delButton = getBtn('删除','logicDelete','page-delete');
        var delPhyButton = getBtn('永久删除','physicalDelete','page-delete');

        var basic = {
            items: [
                addButton,replyButton,sendOthers,delButton,delPhyButton
            ]
        };
        var tbar = this.tbar = new NS.toolbar.Toolbar(basic);
        tbar.bindItemsEvent({
            'save': {event: 'click', fn: this.writeLetter, scope: this},
            'reply': {event: 'click', fn: this.replyLetter, scope: this},
            'forwarding': {event: 'click', fn: this.forwardingLetter, scope: this},
            'logicDelete': {event: 'click', fn: function(){
                this.deleteLetter('logicDelete','信件放入回收站成功!');
            }, scope: this},
            'physicalDelete': {event: 'click', fn: function(){
                this.deleteLetter('physicalDelete','信件永久删除成功!');
            }, scope: this}
        });
        return tbar;
    },
    /**
     * 查询信件
     * @param type
     */
    queryLetters : function(type,info){
        var name = "";
        if(type == "Garbage"){
            name = "垃圾箱";
            this.tbar.queryComponentByName('logicDelete').hide();
            this.tbar.queryComponentByName('physicalDelete').show();
            this.grid.queryComponentByName('sjrmc').hide();
            this.grid.queryComponentByName('cjr').show();
        }else if(type == "Send"){
            name = "发件箱";
            this.tbar.queryComponentByName('logicDelete').hide();
            this.tbar.queryComponentByName('reply').hide();
            this.tbar.queryComponentByName('physicalDelete').hide();
            this.grid.queryComponentByName('forwarding').hide();

            this.grid.queryComponentByName('sjrmc').show();
        }else if(type == "Receive"){
            name = "收件箱";
            this.tbar.queryComponentByName('logicDelete').show();
            this.tbar.queryComponentByName('physicalDelete').show();
            this.tbar.queryComponentByName('physicalDelete').show();
            this.grid.queryComponentByName('sjrmc').hide();
            this.grid.queryComponentByName('cjr').show();
        }
        this.updateServiceParams('query',{type :type,start : 0,limit : 25});
        this.grid.refresh();
        this.grid.on('load',function(json){
            this.typeComponent.refreshTplData({type : name,num : json.count});
        },this);
    },
    /**
     * 删除信件方法
     * @param key
     */
    deleteLetter : function(key){
        var grid = this.grid,
            rows = grid.getSelectRows();
//        if(rows.length == 0){
//            NS.Msg.info('请选中一行记录!');
//            return;
//        }
//        var row = rows[0];
//        var id = row.id;
//        this.callSingle({key : key,params :{id : id}},function(data){
//            if(data.success){
//                NS.Msg.info(info);
//            }
//        });
        this.deleteWithGrid({
              grid : grid,
              serviceKey : key,
              info : "信件删除",
              controller : this
        })
    }
});