/**
 * 男女比例组件。
 * User: zhangzg
 * Date: 14-7-29
 * Time: 下午4:02
 *
 */
Ext.define('Exp.component.NnvRatio',{
    extend:'Ext.Component',
    /*nnvbl:{ns:{
     count:208,
     zb:'35.88'
     },nvs:{
     count:209,
     zb:'64.12'
     },bl:'1:1',
     text:'地空导弹'},*/
    listeners:{
        resize:function(){
            var data = this.translateData();
            this.update(data);
        }
    },
    initComponent:function(){
        this.tpl ='<tpl if="success == true"> <div style="position: relative;"> ' +
            '<div id="{0}_fdk" class="student-sta-zongjie" style="display:none;">{xb} {count}人 <br /> 占 {bl}%</div>' +
            '<div class="student-sta-marginauto" style="padding-top: 80px;" >' +
            '<tpl for="imgs">' +
            '<img src="app/pages/sc/base/component/images/{img}.png" xb="{img}" width="{width}" height="70" style="margin-right:{marginRight}px;margin-left: {marginLeft}px" />' +
            '</tpl></div>' +
            '<div class="student-sta-marginauto student-sta-overFlow">' +
            '<div class="student-sta-floatLeft student-sta-list-red" ms="true" style="width:{nvsZb}%"></div>' +
            '<div class="student-sta-floatLeft student-sta-list-blue" ms="true" style="width:{nsZb}%"></div></div>' +
            '<div class="student-sta-clear"></div>' +
            '<div class="student-sta-overFlow">' +
            '<div class="student-sta-floatRight student-sta-weightbold "> {text}</div>' +
            '</div></div><tpl else><div align="center" style="font-size: 10px; color:#e4e4;">No data to display</div></tpl>';

        this.tpl = Ext.String.format(this.tpl,this.id);

        this.callParent(arguments);
        var me = this;
        this.on({
            mouseover:{
                element : 'el',
                fn : function(event,el){
                    if(el.tagName=='IMG'||event.getTarget().getAttribute('ms')){
                        var x = event.getX()-this.getX();
                        var xb = event.getTarget().getAttribute('xb');
                        if(xb=='girl'){
                            Ext.get(me.id+"_fdk").setStyle({left:x +"px",color:'red',display:'block'});
                            document.getElementById(me.id+"_fdk").innerHTML="女 "+me.nnvbl.nvs.count+"人<br/> 占 "+me.nnvbl.nvs.zb+"%";
                        }else{
                            Ext.get(me.id+"_fdk").setStyle({left:x +"px",color:'blue',display:'block'});
                            document.getElementById(me.id+"_fdk").innerHTML="男 "+me.nnvbl.ns.count+"人<br/> 占 "+me.nnvbl.ns.zb+"%";
                        }

                    }
                }
            }
        });
    },
    afterRender:function(){
        var data = this.translateData();
        this.update(data);
    },
    /*转换数据*/
    translateData:function(){
        var num = 10,width = this.getWidth(),ns = 0,nvs = 0,vagWidth = (width-10)/num;
        if(typeof this.nnvbl =='undefined'){
            return {success:false};
        }
        ns = Math.round(this.nnvbl.ns.zb/10);
        nvs = Math.round(this.nnvbl.nvs.zb/10);
        var imgs = [];
        for(var i = 0;i<nvs;i++){
            imgs.push({img:'girl',width:30,marginRight:(vagWidth-30)/2,marginLeft:(vagWidth-30)/2});
        }
        for(var j = 0;j<10-nvs;j++){
            imgs.push({img:'boy',width:30,marginRight:(vagWidth-30)/2,marginLeft:(vagWidth-30)/2});
        }

        return {
            success:true,
            imgs:imgs,
            text:this.nnvbl.text||'',
            nvsZb:this.nnvbl.nvs.zb,
            nsZb:this.nnvbl.ns.zb
        };
    },
    /**
     * 刷新tpl组件数据。
     * @param data
     */
    refreshTpl:function(data){
        this.nnvbl = data;
        data = this.translateData();
//        this.update(data);
        this.tpl.overwrite(this.id,data);
    }
});