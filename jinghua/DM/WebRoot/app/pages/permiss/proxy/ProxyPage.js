NS.define('Pages.permiss.proxy.ProxyPage',{
    initProxyPage : function(swc,container,row){
        var grid = this.getProxyGrid(),
            layoutCom  = this.proxyLayout = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.proxyPageTpl,
                renderNumber : 1,
                data :this.getUsernameAndBmmc()
            });

        layoutCom.register(grid,0);

        layoutCom.bindItemsEvent({
            'addProxy.click' : {scope : this,fn : function(){this.showAddProxy();}},
            'query.click' : {fn : function(){grid.load({'xfusername.like' : layoutCom.get$ByName('usertext').val()});}}
        });
        this.add(container,layoutCom);
    },
    /**
     * 获取代理Grid
     */
    getProxyGrid :  function(){
        var trandata = this.proxyGridTranData = NS.E2S(this.proxyStsx),
            grid = this.proxyGrid = new NS.grid.SimpleGrid({
                proxy : this.model,
                columnData : trandata,
                data : this.proxyData,
                serviceKey : 'queryProxy',
                columnConfig : [
                    {
                        xtype : 'linkcolumn',
                        name : 'caozuo',
                        text : '操作列',
                        align : 'center',
                        width : 220,
                        links : [
                            {linkText : '修改委派' },{linkText : '设置委派权限' }, { linkText : '取消委派' }
                        ]
                    }
                ]
            });
        grid.bindItemsEvent({
            caozuo : {event : 'linkclick',scope : this,fn : function(txt){
                this.selectGrid(grid,function(row){
                    switch(txt){
                        case '设置委派权限':this.swc.switchTo('proxyMenuSet',row,true);break;
                        case '取消委派':this.doDeleteProxy(row.id,function(){
                            grid.refresh();
                        });break;
                        case '修改委派' : this.showUpdateProxy(row); break;
                    }
                });
            }}
        });
        return grid;
    },
    /***********************第二层***********************/
    showAddProxy : function(){
        var form = this.getProxyForm(),
            values = {},
            layoutCom = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.addLayoutTpl,
                renderNumber : 1,
                data :{title :'权限委托管理',desc : '新增权限委托',columnTitle : '委托内容'}
            });

        layoutCom.register(form,0);
        layoutCom.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){
                if(!form.isValid())return;
                this.doAddProxy(form.getValues(),function(){
                    window.close();
                    this.proxyGrid.refresh();
                });
            }},
            'cancel.click' : {fn : function(){window.close();}}
        });

        form.setValues(values);

        var window = this.getBaseWindow({
            items : layoutCom.component
        });
        window.show();
    },
    showUpdateProxy : function(values){
        var form = this.getProxyForm(),
            layoutCom = new Pages.permiss.base.LayoutComponent({
                layoutTpl : this.addLayoutTpl,
                renderNumber : 1,
                data :{title :'权限委托管理',desc : '修改权限委托',columnTitle : '委托内容'}
            });

        layoutCom.register(form,0);
        layoutCom.bindItemsEvent({
            'submit.click' : {scope : this,fn : function(){
                if(!form.isValid())return;
                this.doUpdateProxy(form.getValues(),function(){
                    window.close();
                    this.proxyGrid.refresh();
                });
            }},
            'cancel.click' : {fn : function(){window.close();}}
        });

        form.setValues(values);

        var window = this.getBaseWindow({
            items : layoutCom.component
        });
        window.show();
    },
    getProxyForm : function(){
        var xfusernameField = new NS.form.field.ForumSearch({
            fieldLabel:'委托用户',
            service : {
                serviceName : 'proxyPermiss?queryUsers'
            },
            fields: ['id', 'ryId', 'username', 'loginname', 'rylbId', 'ghxh', 'bmmc'],
            pageSize:20,
            width:270,
            labelWidth:70,
            name:'xfusername',
            queryParam:'username',
            minChars:1,
            displayField:'username',
            valueField:'username',
            getInnerTpl :function() {
                return '<a class="search-item">用户名：<b>{[values.username?values.username:"无"]}</b>,登录名：{[values.loginname?values.loginname:"无"]}，工号/学号:{[values.ghxh?values.ghxh:"无"]},部门名称:{[values.bmmc?values.bmmc:"无"]}</a>';
            },
            emptyText:'姓名/工号学号/登录名/部门名称查找...'
        });
        xfusernameField.on('select',function(com,rows){
            var row = rows[0];
            form.getField('xfUserId').setValue(row.id);
            form.getField('xfloginname').setValue(row.loginname);
        });
        var form = NS.form.EntityForm.create({
            data : this.proxyGridTranData,
            frame : false,
            formType : 'add',
            columns : 1,
            border : false,
            items : [
                'id',
                'ownerUserId',
                {name : 'xfUserId',hidden : true},
//                'xfusername',
                xfusernameField,
                { name : 'startdate', vtype : 'daterange' },
                { name : 'enddate', vtype : 'daterange' },
                {name : 'xfloginname',readOnly : true},'bz'
            ]
        });
        var st = form.getField('startdate');
        var et = form.getField('enddate');
        st.setEndDateField(et);
        et.setStartDateField(st);

        return form;
    },
    /******************数据请求******************/
    doAddProxy : function(values,callback){
        this.doTipRequest({
            key : 'addProxy',
            params : values,
            successMsg : '委托添加成功!',
            failureMsg : '委托添加失败!',
            callback : callback
        });
    },
    doUpdateProxy : function(values,callback){
        this.doTipRequest({
            key : 'updateProxy',
            params : values,
            successMsg : '委托修改成功!',
            failureMsg : '委托修改失败!',
            callback : callback
        });
    },
    doDeleteProxy : function(proxyId,callback){
        this.doTipRequest({
            key : 'deleteProxy',
            confirm : true,
            confirmMsg : '您确定要删除选中的委托么？',
            params : {proxyId : proxyId},
            successMsg : '委托删除成功!',
            failureMsg : '委托删除失败!',
            callback : callback
        });
    }
});