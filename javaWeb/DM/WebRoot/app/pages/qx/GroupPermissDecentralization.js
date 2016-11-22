NS.define('Pages.qx.GroupPermissDecentralization',{
    extend : 'Template.Page',
    modelConfig: {
        serviceConfig: {
            'save' : 'permiss_addGroupPermissForPerson',
            'update' : {service : 'permiss_updateGroupPermissForPerson'},
            'delete' : {service : 'permiss_deleteGroupPermissWithPerson'},
            query : {service : 'permiss_queryDevolvedGroupPermissPerson'},
            getCdIds : {service : 'permiss_getCdIds'}
        }
    },
    dataRequires : [
        {fieldname : 'data',key : 'query'}
    ],
    init : function(){
        this.initComponent();
    },
    initComponent : function(){
        var grid = this.initGrid();
        var tbar = this.initTbar(grid);
        var container = new NS.container.Panel({
            tbar : tbar,
            layout :'fit',
            items : [grid]
        });
        this.setPageComponent(container);
        grid.refresh();
    },
    initGrid : function(){
        var grid = new NS.grid.Grid({
            data : [],
            fields : ['xm','id','qxnum'],
            serviceKey : 'query',
            proxy : this.model,
            plugins: [
                new NS.grid.plugin.HeaderQuery()],
            columnConfig : [
                {text : '姓名',xtype : 'column',width : 100,name : 'xm'},
                {text : '赋予权限数量',xtype : 'column',width : 100,name : 'qxnum'}
            ]
        });
        return grid;
    },
    initTbar : function(grid){
        var tbar = new NS.toolbar.Toolbar({
            items : [
                {xtype: 'button', text: '新增用户权限', name: 'add',buttonType : 'button_add',iconCls : 'page-add'},
                {xtype: 'button', text: '修改用户权限', name: 'update',buttonType : 'button_add',iconCls : 'page-update'},
                {xtype: 'button', text: '删除', name: 'delete',buttonType : 'button_add',iconCls : 'page-delete'}
            ]
        });
        tbar.bindItemsEvent({
            'add' : {event : 'click',scope : this , fn : function(){
                this.add(grid);
            }},
            'update' : {event : 'click',scope : this , fn : function(){
                this.update(grid);
            }},
            'delete' : {event : 'click',scope : this , fn : function(){
                this.delete(grid);
            }}
        });
        return tbar;
    },
    add : function(grid){
        var window = this.createShowWindow(grid,null,'save');
        window.show();
    },
    update : function(grid){
        this.isSelected(grid,function(grid,rows){
            var row = rows[0];
            this.callSingle({
                key : 'getCdIds',
                params : {userId : row.id}
            },function(data){
                var window = this.createShowWindow(grid,data,'update',row.id,row.xm);
                window.show();
            });

        });
    },
    'delete' : function(grid){
        this.isSelected(grid,function(grid,rows){
            var row = rows[0];
            this.callSingle({
                key : 'delete',
                params : {userId : row.id}
            },function(data){
                if(data.success){
                    NS.Msg.info("用户授权删除成功!");
                    grid.refresh();
                }
            });
        });
    },
    /**
     * 创建显示窗口
     */
    createShowWindow : function(grid,checkedIds,service,userId,username){
        var input = new NS.form.field.ForumSearch({
            fieldLabel:"请输入教师",
            labelWidth: 70,
            width:220,
            alowBlank:true,
            service:{
                serviceName:'getSearchTeacherList'
            },
            fields: ['id', 'xm','zgh','xb'],
            pageSize:10,
            name:'id',
            queryParam:'queryStr',
            minChars:1,
            value :username,
            displayField:'xm',
            valueField:'id',
            getInnerTpl :function() {
                return '<a class="search-item">{xm}[{zgh}]{xb}</a>';
            },
            emptyText:'姓名/拼音/简拼'
        });
        var hidden  = new NS.form.field.Text({
            hidden : true,
            value : userId
        });
        input.on('select',function(com,values){
            //this.updateByJzg(values[0].id);
            hidden.setValue(values[0].id);
        },this);
        var treeData;
        if(checkedIds){
            treeData = this.translateCheckedTreeValues(MainPage.getMenuData(),checkedIds);
        }else{
            treeData = this.translate(MainPage.getMenuData());
        }
        var tree = new NS.container.Tree({
            treeData:treeData,
            title : '可以分配权限',
            rootVisible : false,
            height : 430,
            frame : true,
            border : true,
            multiple:	true,
            margin : '0 0 0 0',
            checkable : true// 是否是多选树
        });
        var window = new NS.window.Window({
            items : [
              input ,tree
            ],
            width : 300,
            height : 527,
            buttons : [
                {text : '保存授权',name : 'submit'},
                {text : '取消授权',name : 'cancel'}
            ]
        });
        window.bindItemsEvent({
            submit : {event : 'click',scope : this,fn : function(){
                this.submit(service,hidden.getValue(),tree.getChecked(),window,grid);
            }},
            cancel : {event : 'click',scope : this,fn : function(){window.close()}}
        });
        return window;
    },
    submit : function(service,yhId,checkedValues,window,grid){
        if(!yhId){NS.Msg.info("请输入要授权的用户!");return;}
        this.callSingle({
            key : service,params : {userId : yhId,menuIds : this.translateSubmitTreeValues(checkedValues)}
        },function(data){
            if(data.success){
                NS.Msg.info("用户授权成功!");
                window.close();
                grid.refresh();
            }
        });
    },
    translateSubmitTreeValues : function(nodes){
        var ids = '';
        for(var i=0;i<nodes.length;i++){
            var node = nodes[i];
            if(node && node.id){
               ids+=node.id;
               if(i != nodes.length -1)ids+=",";
            }
        }
        return ids;
    },
    translateCheckedTreeValues : function(data,checkedId){
        var iterator = [data], i,children,len,node;
        var nextIterator = [];
        var nodeHash = {};
        while(iterator.length != 0){
            for(i=0,len=iterator.length;i<len;i++){
                node = iterator[i];
                if(!node){
                    continue;
                }
                children =  node.children;
                node.checked = false;
                nodeHash[node.id] = node;
                nextIterator = nextIterator.concat(children);
            }
            iterator = nextIterator;
            nextIterator = [];
        }
        for(i=0,len = checkedId.length;i<len;i++){
            nodeHash[checkedId[i]].checked = true;
        }
        return data;
    },
    /**
     * 为树数据添加checked属性
     * @param data
     * @return {*}
     */
    translate : function(data){
        var iterator = [data], i,children,len,node;
        var nextIterator = [];
        while(iterator.length != 0){
            for(i=0,len=iterator.length;i<len;i++){
                node = iterator[i];
                if(!node){
                    continue;
                }
                children =  node.children;
                node.checked = false;
                nextIterator = nextIterator.concat(children);
            }
            iterator = nextIterator;
            nextIterator = [];
        }
        return data;
    },
    isSelected : function(grid,fn){
        var rows = grid.getSelectRows();
        if(rows.length == 0){
            NS.Msg.info("请选中一行数据!");
        }else{
            fn.call(this,grid,rows);
        }
    }
});