/**
 * 年份选择组件(学年)
 * User: Sunwg
 * Date: 14-8-15
 * Time: 下午5:00
 */
Ext.define('Exp.chart.YearPicker',{
	/**
     * @cfg {number} num 下拉框年份数量
     */
	
	/**
     * @cfg {number} value 默认值
     */
	
    extend : 'Ext.Component',
    margin:0,
    listeners:{
        change : function(){}
    },
    initComponent : function(){
        var today = new Date(),
            thisYear = parseInt(today.getFullYear()),
            month = parseInt(today.getMonth());
        thisYear = month>7?thisYear:(thisYear-1);
        var options = "";
        var curXn = thisYear + '-' + (thisYear + 1);
        this.value = curXn;
        for(var i = 0;i< this.num;i++){
        	var xn = (thisYear-i) + "-" +(thisYear+1-i);
        	options += "<option value= '"+ xn +"' " + ((this.value== xn ?  "select='true'" : ""))+ ">" + xn+ "</option>";
        }
        this.callParent(arguments);
        this.html = "<div style='padding:10px;border: 1px solid #aaa;'>" +
        		"选择统计学年 ： <select id = " + this.id + "_years>" + options +"</select>"+
        		"<input type='button' name='"+ this.id +"_btn' value='本学年' year='"+thisYear+ "-" + (thisYear + 1)+"' style=' width: 50px; height: 25px;margin: 0px 10px;'/> "+
        		"<input type='button' name='"+ this.id +"_btn' value='上学年' year='"+(thisYear - 1)+ "-" + thisYear+"' style='width: 50px; height: 25px;margin: 0px 10px;'/> " +
        		"&nbsp;&nbsp;&nbsp;&nbsp;☞选择统计学年更新页面内容。"+
        		" </div>";
    },
    afterRender : function(){
    	 this.bindEvent();
    },
    num : 5,
    getValue : function(){
    	return this.value;
    },
    value : null,
    bindEvent : function(){
    	var me = this;
    	var sId = me.id + "_years";
    	var combo = Ext.get(sId);
    	combo.on('change',function(){
    		me.value = combo.getValue();
    		me.fireEvent("change",combo.getValue());
    	});
    	var compo = Ext.get(me.id);
    	var btns = compo.query("input[name="+me.id +"_btn]");
    	Ext.get(btns).on('click',function(event,tar){
    		var year = Ext.get(tar).getAttribute('year');
    		me.value = year;
    		me.fireEvent("change",year);
    		if($){
    			$("#" + sId).val(year);
    		}
    	});
    }
});