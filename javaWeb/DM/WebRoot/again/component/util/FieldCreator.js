/**
 * @class NS.util.FieldCreator
 * @extends NS.Base
 * @private
 *    Field组件创建工具类
 */
NS.define('NS.util.FieldCreator',{
    singleton : true,
    /**
	 * 组件创建器可以创建的组件类型。
	 */
	supportedType :{
		timefield : 'Ext.form.field.Time',// 时间段组件
		combobox : 'Ext.form.field.ComboBox',// 下拉框
		datefield : 'Ext.form.field.Date',// 日期组件
		checkbox : 'Ext.form.field.Checkbox',// 复选框
		textarea :'Ext.form.field.TextArea',// 文本域
		tree : 'Ext.form.field.ComboBox',// 标识有级联功能的下拉框
		textfield : 'Ext.form.field.Text',// 文本框
		numberfield : 'Ext.form.field.Number',// 数字框
        button : 'Ext.button.Button',//按钮
		treecombobox : 'Ext.ux.comboBoxTree',// 树形下拉框（是否复选待定）
        forumSearch:'Ext.form.field.ForumSearch'
		
	},
	/**
	 * 将实体类配置转化成基本组件标准配置项。
	 * @param {Object} entityConfig
	 */
	initStandardConfig : function(entityConfig){
		/**
		 * 基本组件通用配置项。
		 */
		var standardConfig = {
			name : cdata.dataIndex,
			/**级联数据准备** */
			bmst : cdata.bmst,// 编码实体
			bmcc : cdata.bmcc,// 编码层次
			bmtype : cdata.componentType,// 数组类型
			data : componentData.data,// 存储的数据
			fieldLabel : cdata.header,// 属性字段标签
			allowBlank : cdata.allowBlank,// 是否允许为空
			maxLength : cdata.length,
			ssfl : cdata.ssfl,// 所属分组
			hidden : cdata.sfxs == 1 ? false : true
		};
		NS.apply(standardConfig,entityConfig);
		return standardConfig;
	},
	/**
	 * 根据传递的实体配置数组创建组件列表。
	 * @param {Array} 实体配置数据。
	 */
	createFields : function(standardDataSets){
		var components = new Array();
		if(standardDataSets instanceof Array && standardDataSets.length > 1){
			for(var i = 0 ;i<standardDataSets.length;i++){
				var aCompoConfig = this.initStandardConfig(standardDataSets[i]);
				components.push(this.createField(aCompoConfig));
			}
		}else {
			var aCompoConfig = this.initStandardConfig(standardDataSets[0]);
			components.push(this.createField(aCompoConfig));
		}
	},
	/**
	 * 根据组件类型和配置数据创建组件。
	 * @param {String} 组件类型。
	 */
	createField : function(C,config){
        var xtype = C.xtype,cName = this.supportedType[xtype],component;
        var defaultConfig= this.configConvert(C);
        NS.apply(defaultConfig,config);
        this.processOwnerProperty(defaultConfig);
        if(cName){
            var component = Ext.create(this.supportedType[xtype],defaultConfig);
        }else{
            NS.error({
                sourceClass : 'NS.util.FieldCreator',
                sourceMethod : 'createField',
                msg : '组件类型错误！类型为：'+xtype
            });
        }
        return component;
	},
    /**
     * 处理自定义的属性
     * @param config
     */
    processOwnerProperty : function(config){
//        if(config.editable === false){
//            config.fieldStyle = "background:#E6E6E6;";
//            config.readOnly = true;
//        }
        if(config.fields&&config.xtype!='forumSearch'){
           config.store.fields = config.fields;
           delete config.fields;
        }
        if(config.data && config.xtype == "combobox"){//处理数据
            config.store.data = config.data;
            delete config.data;
        }
    },
    /**
     * 配置参数转换
     * @param {Object} config 配置对象
     * @return {Object} 转换后的配置对象
     */
    configConvert : function(C){
        var basic,xtype = C.xtype;
        if(C.codeData){
            basic = {
                name : C.name,
                xtype : C.xtype,
                codeData : C.codeData,
                /** **级联数据准备** */
                bmst : C.codeData.bmst,// 编码实体
                bmcc : C.cc,// 编码层次
                fl :C.fieldsetGroup,//之前是Fieldset分组，目前暂时需要将其置为级联分组的标识
                bmtype : C.xtype,// 数组类型
                data : C.codeData.data,// 存储的数据
                allowBlank : C.isBlank,// 是否允许为空
                // vtype : cdata.vtype,// 校验类型---参照前台详细文档，校验类型
                maxLength : C.dbLength
            };
        }else{
            basic = C;
        }


        switch(xtype){
            case 'textfield': {
                if (C.regExp != null && C.regExp != "") {
                    NS.apply(basic, {
                        regex : new RegExp(C.regExp),// 正则表达式
                        regexText : C.regValidateErrorMesg // 正则表达式校验--错误信息
                    });
                }
                break;
            }
            case 'numberfield': {
                if (C.regExp != null && C.regExp != "") {
                    NS.apply(basic, {
                        regex : new RegExp(C.regExp),// 正则表达式
                        regexText : C.regValidateErrorMesg // 正则表达式校验--错误信息
                    });
                }
                break;
            }
            case 'combobox' : {
                NS.apply(basic, {
                    valueField : 'id',// 值域
                    displayField : 'mc',// 显示域
                    lastQuery : '',
                    queryMode : 'local',
                    editable : false,
                    store : {
//                        fields : [
//                            {name: 'id', type: 'string'},
//                            {name: 'mc', type: 'string'},
//                            {name: 'dm', type: 'string'}
//                        ],
                        fields : ['id','mc','dm','pid'],
                        data : C.codeData.data
                    }
                });
                break;
            }
            case 'tree': {
                NS.apply(basic, {
                    valueField : 'id',
                    displayField : 'text',
                    editable : false,
                    queryMode : 'local',
                    lastQuery : '',
                    store : {
//                        fields : [
//                            {name: 'id', type: 'string'},
//                            {name: 'text', type: 'string'},
//                            {name: 'pid', type: 'string'}
//                        ],
                        fields : ['id','text','dm','pid'],
                        data : C.codeData.data
                    }
                });
                break;
            }
            case 'timefield' : {
                NS.apply(basic, {
                    format :"G时i分",
                    submitFormat:"G:i"
                });
                break;
            }
            case 'textarea' : {
                NS.apply(basic, {
                    height : 50
                });
                break;
            }
            case "treecombobox" : {
                NS.apply(basic, {
//                	root : {
//                	   children :  NS.clone(C.codeData.data)
//                	},
                    editable : false,
                	treeData:NS.clone(C.codeData.data.children),//默认过滤第一个
                	//width:150,
                	autoWidth:true,
                    //isParentSelect : false,
                    rootVisible : false
                });
                delete basic['data'];
                delete basic['codeData'];
                break;
            }
            case 'checkbox' : {
                NS.apply(basic, {
                	boxLabel : '是',
                    inputValue : '1',
                    uncheckedValue : '0'
                });
                break;
            }
            case 'datefield' : {
            	NS.apply(basic,{
            		format : 'Y-m-d'
            	});
                break;
            }
            case 'forumSearch':{
                NS.apply(basic,{
                    service:{}
                });
                break;
            }
        }
        return basic;
    }
});