/**
 * @class NS.container.TabSwitchContainer
 * @extends NS.container.Panel
 *  tab页切换容器，用于不同tab页之间的切换
 *
 *      var switch = new NS.container.TabSwitchContainer({
 *          items : [
 *              {
 *                  name : 'component1',
 *                  title : 'tab1',
 *                  scope : this,//init方法的作用域
 *                  init : this.initComponent1// init方法会接受到三个参数，第一个参数是SwitchContainer
 *              },                          // 第二个参数是：一个容器，你可以将你自己组装好的组件放置到该容器里
 *                                          //第三个参数是：配置参数（该参数是通过switchTo方法传递的）
 *              {
 *                  name : 'component2',
 *                  title : 'tab2',
 *                  scope : this,
 *                  init : this.initComponent2
 *              }
 *          ]
 *      });
 */
NS.define('NS.container.TabSwitchContainer',{
    extend : 'NS.container.Panel',
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping('defaultIndex');
    },
    /**
     *  创建一个tabpanel
     * @param config
     */
    initComponent : function(config) {
        this.component = Ext.create('Ext.tab.Panel', config);
        this.component.on('tabchange',function(tabpanel,newcard,oldcard){
              this.switchTo(newcard.name);
        },this);
        if(!config.defaultIndex)
            config.defaultIndex = 0;
        this.switchIndex(config.defaultIndex);
    },
    /**
     * 处理items项
     * @private
     * @param {配置参数}config
     */
    procressItems : function(config){
        var map = this.initMap = {},items = config.items||[],i= 0,len=items.length,item,container;
        for(i;i<len;i++){
            item = items[i];
            container = Ext.create('Ext.container.Container',{
                width : '100%',
                height : '100%',
                title : item.title,
                name : item.name,
                layout : 'fit'
            });
            map[item.name]  = {
                scope : item.scope,
                name : item.name,
                init : item.init,
                index : i,
                isInit : false,
                container : container
            }
            items[i] = container
        }
    },
    /**
     * 切换到name为指定值的页,默认是如果这个页面的组件已经被初始化，那么init方法将不在被调用，如果想强制调用init方法的话，
     *   需要多传递一个标识符forceToCall ，并且其值为true
     * @param {String} name 待切换的页面的name
     * @param {Object} params 需要给该页面传递的初始化函数的参数
     * @param {Boolean} [forceToCall] true强制调用初始化init方法,false则不调用init方法
     */
    switchTo : function(name,params,forceToCall){
        var component = this.component,item = this.initMap[name],container = item.container,packContainer;
        if(item){
            component.setActiveTab(container);
            if(!item.isInit || forceToCall){
                packContainer = NS.util.ComponentInstance.getInstance(container);
                item.init.call(item.scope||this,this,packContainer,params);
                item.isInit = true;
            }
        }
    },
    /**
     * 根据索引获取配置项
     * @param {Number}index
     * @return {Object}
     */
    getItemByIndex : function(index){
        var initMap = this.initMap, i,item;
        for(i in this.initMap){
            item = initMap[i]
            if(item['index'] == index){
                return item;
            }
        }
    },
    /**
     * 将页面切到items数组配置项中某个下标对应的页面上
     * @param {Number} index 组件索引
     * @param {Object} params 参数对象
     */
    switchIndex : function(index,params){
        var layout = this.component.getLayout(),item = this.getItemByIndex(index),container = item.container,packContainer;
        if(item){
            layout.setActiveItem(container);
            if(!item.isInit){
                packContainer = NS.util.ComponentInstance.getInstance(container);
                item.init.call(item.scope||this,this,packContainer,params||{});
                item.isInit = true;
            }
        }
    },
    /**
     * 设置该tab为当前显示页面
     * @private
     * @param {NS.component.Component} 将要被设置为激活tab页的组件
     */
    setActiveTab : function(component) {
        this.component.setActiveTab(component.getLibComponent());
    },
    /**
     *获取当前激活的tab页
     * @private
     */
    getActiveTab : function(){
        var tab = this.component.getActiveTab();
        return NS.util.ComponentInstance.getInstance(tab);
    }
});
