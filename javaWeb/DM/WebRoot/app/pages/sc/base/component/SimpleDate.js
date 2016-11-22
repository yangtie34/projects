/**
 * 日期区间组件
 * User: zhangzg
 * Date: 14-7-22
 * Time: 下午5:52
 *
 */
Ext.define('Exp.component.SimpleDate',{
    extend:'Ext.container.Container',
    defaultDate:'',
    width:500,
    initComponent:function(){
        /*增加事件*/
        this.addEvents(
            /**
             * @event 日期组件校验通过事件。
             *         即开始日期不大于结束日期，并且开始日期和结束日期都不为空时触发的事件。
             * @param {Ext.Component} this
             */
            'validatepass'
        );
        this.callParent(arguments);

        this.createComponent();
    },
    createComponent:function(){
        var tpl ,me = this;
        if(me.defaultDate==''){
            tpl = new Ext.XTemplate(this.getTplStr());
        }else{
            tpl = new Ext.XTemplate(this.getTplStr(me.defaultDate));
        }
        this.compo = new Ext.Component({
            style: {"float": 'left'},
            tpl:tpl,
            data:{}
        });
        this.compo.on({
            click : {
                element : 'el',
                fn : function(event,el){
                    if(el.tagName=='A'){
                        me.clickFn(el);
                    }
                }
            }
        });
        this.createDateSection();
        this.add([this.compo,this.fromDateField,this.toDateField]);
    },
    createDateSection : function(){
        var me = this;
        this.fromDateField = Ext.create('Ext.form.field.Date', {
            width:100,
            style: {"float": 'left'},
            name: 'from_date',
            margin:'3 0 0 0',
            format:'Y-m-d',
            value: new Date(),
            editable:false
        });

        this.toDateField  = Ext.create('Ext.form.field.Date', {
            width:100,
            style: {"float": 'left'},
            name: 'to_date',
            margin:'3 0 0 0',
            format:'Y-m-d',
            value: new Date(),
            editable:false
        });
        this.toDateField.addListener('change',function(comp,newValue,oldValue){
            var toDate = comp.getRawValue(),
                fromDate = this.fromDateField.getRawValue();
            if(fromDate!=''){
                if(toDate>=fromDate){
                    me.fireEvent('validatepass');
                }else{
                    this.toDateField.focus();
                    this.toDateField.setValue(null);
                }
            }
        },this);
        this.fromDateField.addListener('change',function(comp,newValue,oldValue){
            this.toDateField.setValue(null);
            var fromDate = comp.getRawValue(),
                toDate = this.toDateField.getRawValue();
            this.toDateField.setMinValue(fromDate);
            if(toDate!=''){
                if(toDate>=fromDate){
                    me.fireEvent('validatepass');
                }else{
                    this.fromDateField.setValue(null);
                    this.fromDateField.focus();
                }
            }
        },this);
        switch(me.defaultDate){
            case 'jr' :
                var today = Exp.util.DateUtil.getDateStr(new Date(),0);
                me.setValue({from : today,to :today});
                break;
            case 'zr' :
                var today = Exp.util.DateUtil.getDateStr(new Date(),-1);
                me.setValue({from : today,to :today});
                break;
            case 'bz' :
                me.setValue(Exp.util.DateUtil.getWeekStartEnd(new Date()));
                break;
            case 'sz' :
                me.setValue(Exp.util.DateUtil.getPreWeekStartEnd(new Date()));
                break;
            case 'zjygy' :
                var start = Exp.util.DateUtil.getDateStr(new Date(),-30);
                var end = Exp.util.DateUtil.getDateStr(new Date(),0);
                me.setValue({from : start,to :end});
                break;
            default :
                var today = Exp.util.DateUtil.getDateStr(new Date(),0);
                me.setValue({from : today,to :today});
                break;
        }
    },
    /**
     * 获取模板
     */
    getTplStr : function(){
        return '<div>'+
            '<a class="jiaoxuerizhi-alink jiaoxuerizhi-alink-visited" href="#" dType="jr">今日</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="zr">昨日</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="bz">本周</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="sz">上周</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="zjygy">最近一个月</a></div>';
    },
    getTplStr : function(def){
        var jrClass='',zrClass='',bzClass='',szClass='',zjClass='';
        switch(def){
            case 'jr' :
                jrClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'zr' :
                zrClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'bz' :
                bzClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'sz' :
                szClass=' jiaoxuerizhi-alink-visited';
                break;
            case 'zjygy' :
                zjClass=' jiaoxuerizhi-alink-visited';
                break;
            default :
                jrClass=' jiaoxuerizhi-alink-visited';
                break;
        }

        return "<div>"+
            "<a class='jiaoxuerizhi-alink"+jrClass+"' href='#' dType='jr'>今日</a>"+
            "<a class='jiaoxuerizhi-alink"+zrClass+"' href='#' dType='zr'>昨日</a>"+
            "<a class='jiaoxuerizhi-alink"+bzClass+"' href='#' dType='bz'>本周</a>"+
            "<a class='jiaoxuerizhi-alink"+szClass+"' href='#' dType='sz'>上周</a>"+
            "<a class='jiaoxuerizhi-alink"+zjClass+"' href='#' dType='zjygy'>最近一个月</a></div>";
    },
    // 添加class
    addClass :function (ele,className){
        ele.className += " " + className; //以空格分开
    },
    // 移除class
    removeClass:function (ele,className){
        var tmpClassName = ele.className;
        ele.className = null;    //清除类名
        ele.className = tmpClassName.split(new RegExp(" " + className + "|" + className + " " + "|" + "^" + className + "$","ig")).join("");
    },
    clickFn:function(target){
        var dType = target.getAttribute('dType');
        var tagName = target.tagName;
        var childrens = target.parentNode.childNodes;
        for(var i=0;i<childrens.length;i++){
            this.removeClass(childrens[i],'jiaoxuerizhi-alink-visited')
        }
        if(tagName =='A'){
            this.addClass(target,'jiaoxuerizhi-alink-visited');
        }
        switch(dType){
            case 'jr' :
                var today = Exp.util.DateUtil.getDateStr(new Date(),0);
                this.setValue({from : today,to :today});
                break;
            case 'zr' :
                var today = Exp.util.DateUtil.getDateStr(new Date(),-1);
                this.setValue({from : today,to :today});
                break;
            case 'bz' :
                this.setValue(Exp.util.DateUtil.getWeekStartEnd(new Date()));
                break;
            case 'sz' :
                this.setValue(Exp.util.DateUtil.getPreWeekStartEnd(new Date()));
                break;
            case 'zjygy' :
                var start = Exp.util.DateUtil.getDateStr(new Date(),-30);
                var end = Exp.util.DateUtil.getDateStr(new Date(),0);
                this.setValue({from : start,to :end});
                break;
            default :
                break;
        }
    },

    /**
     * 获取组件的值
     * @return {
     *     from:'2014-06-16',
     *     to:'2014-06-16'
     * }
     */
    getValue : function(){
        return {
            from :this.fromDateField.getRawValue(),
            to : this.toDateField.getRawValue()
        }
    },
    /**
     * 设置组件的日期区间
     * @param config
     */
    setValue : function(config){
        this.fromDateField.setValue(new Date(config.from));
        this.toDateField.setValue(new Date(config.to));
    }
});