/**
 * 年度区间组件
 * User: zhangzg
 * Date: 14-8-15
 * Time: 上午11:33
 *
 */
Ext.define('Exp.component.SimpleYear',{
    extend:'Exp.component.SimpleDate',
    width:500,
    defaultDate:'',
    start:1990,
    createDateSection : function(){
        var me = this;
        var store = new Ext.data.Store({
            fields: ['id', 'name'],
            data : this.getNfqj(this.start||1900)
        });
        this.fromDateField = Ext.create('Ext.form.ComboBox', {
            width:100,
            style: {"float": 'left'},
            name: 'from_date',
            margin:'3 0 0 0',
            valueField:'id',
            displayField: 'name',
            store:store,
            value: new Date().getFullYear(),
            editable:true
        });

        this.toDateField  = Ext.create('Ext.form.ComboBox', {
            width:100,
            style: {"float": 'left'},
            name: 'to_date',
            margin:'3 0 0 0',
            valueField:'id',
            displayField: 'name',
            store:Ext.clone(store),
            value: new Date().getFullYear(),
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

            if(toDate!=''){
                if(toDate>=fromDate){
                    me.fireEvent('validatepass');
                }else{
                    this.fromDateField.setValue(null);
                    this.fromDateField.focus();
                }
            }
        },this);
        var jn = new Date().getFullYear();
        switch(me.defaultDate){
            case 'jr' :
                me.setValue({from : jn,to :jn});
                break;
            case 'zr' :
                me.setValue({from : jn-1,to :jn-1});
                break;
            case 'bz' :
                me.setValue({from : jn-4,to :jn});
                break;
            case 'sz' :
                me.setValue({from : jn-9,to :jn});
                break;
            case 'zjygy' :
                me.setValue({from : jn-19,to :jn});
                break;
            default :
                me.setValue({from : jn,to :jn});
                break;
        }

    },
    /**
     * 获取模板
     */
    getTplStr : function(){
        return '<div>'+
            '<a class="jiaoxuerizhi-alink jiaoxuerizhi-alink-visited" href="#" dType="jr">今年</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="zr">去年</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="bz">近五年</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="sz">近十年</a>'+
            '<a class="jiaoxuerizhi-alink" href="#" dType="zjygy">近二十年</a></div>';
    },
    getTplStr : function(def){
        var jrClass='',zrClass='',bzClass='',szClass='',zjClass='';
        var jn = new Date().getFullYear();
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
            "<a class='jiaoxuerizhi-alink"+jrClass+"' href='#' dType='jr'>今年</a>"+
            "<a class='jiaoxuerizhi-alink"+zrClass+"' href='#' dType='zr'>去年</a>"+
            "<a class='jiaoxuerizhi-alink"+bzClass+"' href='#' dType='bz'>近五年</a>"+
            "<a class='jiaoxuerizhi-alink"+szClass+"' href='#' dType='sz'>近十年</a>"+
            "<a class='jiaoxuerizhi-alink"+zjClass+"' href='#' dType='zjygy'>近二十年</a></div>";
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
        var jn = new Date().getFullYear();
        switch(dType){
            case 'jr' :
                this.setValue({from : jn,to :jn});
                break;
            case 'zr' :
                this.setValue({from : jn-1,to :jn-1});
                break;
            case 'bz' :
                this.setValue({from:jn-4,to:jn});
                break;
            case 'sz' :
                this.setValue({from:jn-9,to:jn});
                break;
            case 'zjygy' :
                this.setValue({from:jn-19,to:jn});
                break;
            default :
                break;
        }
    },
    /**
     * 设置组件的日期区间
     * @param config
     */
    setValue : function(config){
        this.fromDateField.setValue(config.from);
        this.toDateField.setValue(config.to);
    },
    /**
     * 获取年份区间。
     */
    getNfqj:function(start){
        var arrs=[],to = new Date().getFullYear();
        for(;start<=to;start++){
            arrs.push({id:start,name:start+"年"});
        }
        return arrs;
    },
    /**
     * 获取组件的日期区间
     */
    getValue:function(){
        return {
            from:this.fromDateField.getValue(),
            to:this.toDateField.getValue()
        }
    }
});