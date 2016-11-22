/**
 * @class NS.toolbar.Toolbar
 * @extends NS.container.Container
 *      工具栏容器
 */
NS.define('NS.toolbar.Toolbar', {
    extend: 'NS.container.Container',
    /**
     * 创建一个tbar
     * @param {Object} config 配置对象
     */
    initComponent: function (config) {
        this.component = Ext.create('Ext.toolbar.Toolbar', config);
    },
    /***
     * 处理嵌套的组件层次
     * @param {Array} items 子组件数组
     */
    processChildItems: function (config) {
        var item, items = config.items || [];
        if (NS.isArray(items)) {
            for (var i = 0, len = items.length; i < len; i++) {
                item = items[i];
                if (NS.isNSComponent(item)) {
                    items[i] = item.getLibComponent();
                } else if (item.items) {
                    items[i] = arguments.callee(item.items);
                } else if (NS.isObject(item)) {
                		if (item.xtype == "button") {
                            items[i] = new Ext.button.Button(item);
                        }else{
                        	items[i] = NS.util.FieldCreator.createField(item);
                        }
                }
            }
            return;
        } else if (NS.isNSComponent(items)) {
            config.items = items.getLibComponent();
            return;
        } else if (NS.isObject(items)) {
            var component = NS.util.FieldCreator.createField(item);
            if (component) {
                config.items = item;
            } else {
                if (items.xtype == "button") {
                    config.items = new Ext.button.Button(item);
                }
            }
            return;
        }
    },
    /**
     *获取tbar内所有field的值,如果field的值为空，则不返回
     * @return {Object}
     */
    getValues: function () {
        var fields = this.component.query("field"), i = 0, field, len, values = {}, value, name;
        for (i, len = fields.length; i < len; i++) {
            field = fields[i];
            value = field.getValue();
            name = field.name;
            if (value) {
                values[name] = value;
            }
        }
        return values;
    },
    /**
     *获取tbar内指定的name的field的值,如果field的值为空，则不返回
     * var tbar = new NS.toolbar.MultiLineTbar();
     * var values = tbar.getValuesByName('name1','name2','name3');
     * @param {String..} name 多个名称参数
     * @return {Object} 参数对象
     */
    getValuesByName: function (name) {
        var fields = this.component.query("field"), i = 0, field, len, values = {}, value, na;
        for (i, len = arguments.length; i < len; i++) {
            values[arguments[i]] = undefined;
        }
        for (i = 0, len = fields.length; i < len; i++) {
            field = fields[i];
            value = field.getValue();
            na = field.name;
            if (value && values.hasOwnProperty(na)) {
                values[na] = value;
                if (!values[na]) {
                    delete values[na];
                }
            }
        }
        return values;
    },
    /**
     * 将属性name为'name'的对象替换为obj
     * @param name 属性name的值
     * @param obj 待替换对象
     */
    replace:function(name,obj){
    	//实现思路：查找到name位置,先移除,再添加（因为关系到ext对象与封装对象转换问题这里仅提出实现思路）
    	//先将items里对象存储到map里,通过name为键 对象为值,同时动态添加index属性标识该对象所处的位置
    	//if(!this.configMap){this.comfigMap = this.configMapping();}
    	//将待移除对象的index取出，
    	//现将该对象移除 调用this.component.remove(对象,false);建议用false 是不销毁这个对象,因为不知道这个对象是否在之后还是用
    	//然后调用this.component.add(this.component,待替换对象,index);
    	//整个功能完成，注意事项：'-','->'这些均不在此替换范围,如果今后添加对这些对象支持,还需进一步处理
    },
    /**
     * items里参数对象映射表 name为键 参数对象为值,同时添加index属性标识位置,如果参数里包含'-','->'等目前
     * 封装对象暂不支持的对象，index++即可
     */
    configMapping:function(){
    	//遍历items 得到map
    }
});