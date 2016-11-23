/**
 * @class NS.mask.LoadMask
 * @extends NS.Component
 *    var mask = new NS.mask.LoadMask({
 *        target : component1,
 *        msg : '数据加载中'
 *    });
 *    mask.show();
 *    //....do something
 *    mask.hide
 */
NS.define("NS.mask.LoadMask",{
   extend : 'NS.Component',
    /**
     *@cfg {NS.Component} target 待使用遮罩的组件
     */
    /**
     * @cfg {String} msg 遮罩的提示信息
     */
   initComponent : function(config){
       this.config = config;
       var target = config.target;
       if(NS.isNSComponent(target)){
           target = target.getLibComponent();
           this.component = Ext.create('Ext.LoadMask',target,{msg : config.msg||"加载中..."});
       }else if(NS.isNSElement(target)){
           this.component = target.element;
       }

   },
    /***
     * 初始化组件所支持的属性的映射
     * @private
     */
    initConfigMapping : function(){
        this.callParent();
        this.addConfigMapping({
                target : true,//遮罩目标
                msg : true//提示信息
        });
    },
    /**
     * 显示遮罩
     */
   show : function(){
       if(this.component instanceof Ext.LoadMask){
          this.component.show();
       }else if(this.component instanceof Ext.dom.Element){
           this.component.mask(this.config.msg);
       }
   },
    /**
     * 隐藏遮罩遮罩
     */
   hide : function(){
        if(this.component instanceof Ext.LoadMask){
            this.component.hide();
        }else if(this.component instanceof Ext.dom.Element){
            this.component.unmask();
        }
   }
});