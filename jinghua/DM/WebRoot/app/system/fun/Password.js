NS.define("System.fun.Password",{
    /**
     * 创建密码修改窗体
     */
    constructor : function(config){
        var basic = {
            title : '密码修改-<span style="color:red">密码修改后，请在2分钟以后重新登录！</span>',
            animCollapse : false,
            layout : 'fit',
            border : false,
            width:400,
            height:280,
            items : this.createPasswordOptions(),
            buttons : this.getButtons()
        };
        this.controller = config.controller;
        NS.apply(basic, config);
        this.component = Ext.create('Ext.window.Window', basic);
    },
    /***************************************************************************
     * 创建修改密码form表单子元素
     */
    createPasswordOptions : function() {
        return {
            xtype : 'form',
            fieldDefaults : {
                labelWidth : 55,
                inputType : 'password'
            },
            bodyPadding : 5,
            frame : true,
            defaultType : 'textfield',
            items : [ {
                fieldLabel : '旧密码',
                anchor : '100%',
                name : 'oldpassword'
            }, {
                fieldLabel : '新密码',
                anchor : '100%',
                name : 'newpassword'
            }, {
                fieldLabel : '确认密码',
                anchor : '100%',
                name : 'surepassword'
            } ]
        };
    },
    /***************************************************************************
     * 获取按钮
     *
     * @returns {Array}
     */
    getButtons : function() {
        var me = this;
        return [ {
            text : '设置密码',
            handler : function() {
                me.onSetPassword(me.component);
            }
        }, {
            text : '取消',
            handler : function() {
                me.component.close();
            }
        } ];
    },
    /***************************************************************************
     * 修改密码
     */
    onSetPassword : function(win) {
        var form = win.getComponent(0);
        var oldp = form.getComponent(0);
        var newp = form.getComponent(1);
        var surep = form.getComponent(2);
        if (newp.getValue() != surep.getValue()) {
            Ext.Msg.alert('提示', '两次输入的密码不一致,请重新输入');
        } else {
            this.controller.callSingle({
                key : 'updatePassword',
                params : {
                    'newpassword' : newp.getValue(),
                    'oldpassword' : oldp.getValue()
                }},function(result){
                    Ext.Msg.alert('提示', result.info);
                    if(Boolean(result.success) == true){
                        Ext.Msg.alert('提示','为了您的帐号的安全，请重新登录！');
                        win.close();
                    }else{
                        win.down('form').getForm().reset();
                    }
                }
            );
//            // 去掉大括号
//            var str = str.replace(/\{|}/g, '');
//            data[0] = 'update,updatePassword,' + [ str ]; // 修改密码
//            var result = Connection.request({
//                async : false,
//                method : "POST",
//                data : data
//            });
//            Ext.Msg.alert('提示', result.update.info);
//            if(result.update.success == true){
//                Ext.Msg.alert('提示','为了您的帐号的安全，请重新登录！');
//                win.close();
//            }else{
//                win.down('form').getForm().reset();
//            }
        }
    }
});