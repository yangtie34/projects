/**
 * 页面头部统一组件。
 * User: zhangzg
 * Date: 14-7-23
 * Time: 上午9:40
 *
 */
Ext.define('Exp.component.PageTitle',{
    extend:'Ext.Component',
    tpl : '<div class="wbh-common-top">'+
        '<h1> <span class="wbh-common-model" style="background: none">{pageName}</span>' +
        '<div attr="help" class="another-add-help"></div> ' +
        '<span class="another-add-more">' +
        //'<a class="another-add-abg" href="#">下载报表</a>'+
        '</span></h1></div>'+
        '<div class="another-add-helpinfo">{pageHelpInfo}</div>',
    initComponent:function(){
        this.addEvents('click');
        this.callParent(arguments);
        this.bindEvents();
    },
    bindEvents:function(){
        var me = this;
        this.on({
            click : {
                element: 'el', //bind to the underlying el property on the panel
                fn: function(e){
                    //点击问号显示或者隐藏帮助信息
                    var tar = e.target;
                    if(tar.tagName == 'DIV' && (Ext.get(tar).getAttribute("attr")=="help")){
                        if($){
                            $(Ext.get(this.id).query(".another-add-helpinfo")).slideToggle();
                        }else{
                            var info = Ext.get(Ext.get(this.id).query(".another-add-helpinfo"));
                            info.setVisibilityMode(2);
                            info.toggle();
                        }
                    }
                    if(tar.tagName=='A'){
                        me.fireEvent('click');
                    }
                }
            }
        });
    }
});