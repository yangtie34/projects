NS.define('Manager.station.WriteLetter',{
    writeLetter : function(){
        this.zgIdMap = {};
        this.createLetterContainer();
    },
    createLetterContainer : function(){
        var createText = function(fieldLabel,name,colspan,hidden,width){
            return new NS.form.field.Text({name : name,labelWidth:60,width : width||300,fieldLabel : fieldLabel,colspan : colspan,hidden : hidden});
        };
        var sendTime = new NS.form.field.DateTime({
            format : 'Y-m-d H:i:s',
            width : 300,
            name : 'sendTime',
            labelWidth:60,
            fieldLabel : '发送时间'
        });
        var sendPerson = createText('发送人','fsr',1,false,600);
        this.processSendPerson(sendPerson);
        var content = new NS.form.field.HtmlEditor({
            name : 'content',
            width : 600,
            colspan : 1,
            labelWidth:60,
            height : 370,
            fieldLabel : '内容'
        });
        var form = new NS.form.BasicForm({
            frame : true,
            border : false,
            width : '75%',
            region : 'west',
            height : 580,
            name : 'form',
            border : false,
            bodyBorder : false,
            layout : {type : 'table',columns : 1},
            items : [
                createText('id','id',1,true),
                createText('标题','title',1,false,600),
                sendPerson,
                //sendTime,
                content
            ]
        });
        var input = new NS.form.field.Text({
            width : 168,
            style : {
                marginTop : '5px',
                marginLeft : '20px'
            }
        });
        input.addListener('change',function(){
            this.filterJzgByName(input.getValue());
        },this);
        var jzgPanel = new NS.container.Container({
            region : 'center',
            width : '25%',
            height : 580,
            style : {
                marginLeft : '10px'
            },
            items : [input,this.getJzgList(sendPerson)]
        });
        var window = new NS.window.Window({
            width : 850,
            height : 600,
            layout :'border',
            modal : true,
            autoShow : true,
            items : [
                form,jzgPanel
            ],
            buttons : [
                {text : '发送',name : 'submit'},
                {text : '取消',name : 'cancel'}
            ]
        });
        window.bindItemsEvent({
            submit : {event : 'click',fn : function(){this.submit(form,window);},scope : this},
            cancel : {event : 'click',fn : function(){this.cancel(window);},scope : this}
        });
        return window;
    },
    submit : function(form,window){
        var zgIdMap = this.zgIdMap;
        var ids = [];
        for(var i in zgIdMap){
            ids.push({jzgId : i,jzgxm : zgIdMap[i]});
        }
        var values = form.getValues();
        values.htmleditFlag = true;
        NS.apply(values,this.extraParams);
        this.saveWithForm({
            form : form,
            formContainer: window,
            serviceKey : 'add',
            info : "信件发送",
            grid : this.grid,
            params : {letter : values,jsr : ids,attach : {}},
            controller : this
        });
    },
    cancel : function(window){
        window.close();
    },
    /**
     * 获取教职工列表
     */
    getJzgList : function(input){
        var tpl = new NS.Template("<table class='station_jzg'><tpl for='.'><tr><td><a href='javascript:void(0);'  zgId = '{id}' style='text-decoration: none;margin-top: 2px;margin-left: 2px;'>{xm}-{mc}</a></td></tr></tpl></table>");
        var component = this.jzgListCom = new NS.Component({
            tpl : tpl,
            border : true,
            width : 168,
            height : 482,
            autoScroll : true,
            style : {
                marginLeft : '20px',
                backgroundColor : 'white'
            },
            data : this.jzgList
        });
        component.on('click',function(event,item){
            var zgIdMap = this.zgIdMap;
            if(item.tagName == 'A'){
                var zgId = NS.fly(item).getAttribute('zgId');
                var text = item.innerHTML;
                zgIdMap[zgId] = text;
                var values = "";
                for(var i in zgIdMap){
                    values += zgIdMap[i]+";";
                }
                input.setValue(values);
            }
        },this);
        return component;
    },
    /**
     * 根据教职工姓名，过滤教师信息
     * @param name
     */
    filterJzgByName : function(name){
        var array = this.jzgList,item,i= 0,len = array.length,filterData = [];
        var reg = new RegExp(name);
        for(;i<len;i++){
            item = array[i];
            if(reg.test(item.xm)){
                filterData.push(item);
            }
        }
        this.jzgListCom.refreshTplData(filterData);
    },
    getSendPersonInput : function(){
        var tpl = new NS.Template();
        var component = new NS.Component({
            tpl : tpl
        });
    },
    /*******************回复信件************************/
    replyLetter : function(){
        var grid = this.grid,
            rows = grid.getSelectRows();
            this.zgIdMap = {};

        if(rows.length == 0){
            NS.Msg.info('请选中一行记录!');
            return;
        }
        var window = this.writeLetter();

        var row = rows[0],jzgId = row.jzgId;

        this.extraParams = {
            isReply : true,
            replyLetterId : row.id
        };
        this.queryJzgXmAndBmById(jzgId);
        var v = this.zgIdMap[jzgId];

        var fsrField  = window.queryComponentByName('fsr');
        fsrField.setReadOnly(true);
        fsrField.setValue(v);
    },
    queryJzgXmAndBmById : function(jzgId){
        var jzgList = this.jzgList,i= 0,len = jzgList.length,item;
        for(;i<len;i++){
            item = jzgList[i];
            if(item.id == jzgId){
                this.zgIdMap[jzgId] = item.xm+"-"+item.mc;
            }
        }
    },
    /*******转发信件********/
    forwardingLetter : function(){
        var grid = this.grid,
            rows = grid.getSelectRows();
        if(rows.length == 0){
            NS.Msg.info('请选中一行记录!');
            return;
        }
        var row = rows[0];
        this.zgIdMap = {};
        var window = this.writeLetter();
        var content  = window.queryComponentByName('content');
        var title  = window.queryComponentByName('title');
        content.setValue(row.content);
        title.setValue(row.title);
    },
    /*************处理sendPersoninput组件*************/
    processSendPerson : function(input){
        input.on('blur',function(){
             var v = input.getValue();
             var array = v.split(";");
             this.comparedAndSetJzgMap(array);
             this.setSendPersonValues(input);
        },this);
    },
    comparedAndSetJzgMap : function(array){
        var jzglist = this.jzgList,
            jzgMap = this.zgIdMap = {},
            i = 0,item,len = jzglist.length,name, j,alen = array.length,mc;
        for(;i<len;i++){
            item = jzglist[i];
            name = item.xm+"-"+item.mc;
            for(j = 0;j<alen;j++){
                mc = array[j];
                if(name == mc){
                   jzgMap[item.id] = name;
                }
            }
        }
    },
    setSendPersonValues : function(input){
        var zgIdMap = this.zgIdMap;
        var values = "";
        for(var i in zgIdMap){
            values += zgIdMap[i]+";";
        }
        input.setValue(values);
    }
});