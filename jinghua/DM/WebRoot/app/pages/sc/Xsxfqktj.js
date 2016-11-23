/**
 *  学生消费情况统计
 */
NS.define('Pages.sc.Xsxfqktj', {
	extend: 'Template.Page',
	tplRequires : [
	                {fieldname : 'tplBhkCjlrPc',path : 'app/pages/sc/template/xsxfqktj_main.html'},
	                {fieldname : 'xsxfxxtop',path : 'app/pages/sc/template/xsxfqktj_top.html'},
	                {fieldname : 'xsxfxxtable',path : 'app/pages/sc/template/xsxfqktj_table.html'}
	              ],
	modelConfig : {
	      serviceConfig : {
	    	  xfgk : 'xsxfqktjService?queryXsxfgk',	//消费概况
	    	  rjxf : 'xsxfqktjService?queryRjxfqk', //日均消费概况
	    	  sjdxf : 'xsxfqktjService?querySjdxfqk' //时间段消费概况
	      }
	 },
   mixins : [
        "Pages.sc.Scgj"		//输出工具类
    ],
    cssRequires : ['app/pages/sc/template/css/xsxfqktj.css',
        'app/pages/sc/template/css/sc_base.css',
        'app/pages/sc/template/css/op.css',
        'app/pages/sc/template/css/another-add.css'],
    init : function() {
    	var page =this.component= new NS.Component({
    		border : true,
    		baseCls : '',
    		autoScroll : true,
    		html : this.tplBhkCjlrPc,
    		autoShow : true
    	});
    	page.on('click',function(event){
    		if ($(event.getTarget()).attr("attr") == 'help') {
    			$("#sc_xsxfqktj_help").slideToggle();
    		}
    	});
    	page.on('afterrender',function(){
    		this.createDateSection();
            var params = this.dateSection.getRawValue();
            params.init = true;
    		this.renderChildComp(params);
    	},this);
    	this.setPageComponent(page);
    }, 
    //渲染各个小组件
    renderChildComp : function(params){
    	 this.callService([{key:'xfgk',params:params},{key:'sjdxf',params:params}],function(data){
    		 this.renderTop(data.xfgk);
             this.createPieChartAndTable(data.sjdxf,parseFloat(data.xfgk.xfze[0].xfze));
         },this);
        this.callService([{key:'rjxf',params:params}],function(data){
            this.createChart(data.rjxf);
        },this);
    },
    renderTop : function(data){
    	 var xfze = parseFloat(data.xfze[0].xfze) || 0,
	 	 xbfl = data.xbfl,
	 	 hklxfl = data.hklxfl,
	 	 pklxfl = data.pklxfl;
    	 var nszb = 0,nvzb = 0,nchkzb = 0,fnhkzb = 0 ,pkzb = 0,fpkzb = 0; // 男、女、农村、非农、贫困、非贫困
		 for ( var i = 0; i < xbfl.length; i++) {
			if(xbfl[i].xb == '男') nszb =(100*parseFloat(xbfl[i].xfje)/xfze).toFixed(2);
			else if (xbfl[i].xb == '女') nvzb =(100*parseFloat(xbfl[i].xfje)/xfze).toFixed(2);
		 }
		 for ( var i = 0; i < hklxfl.length; i++) {
				if(hklxfl[i].hklx == '城市') nchkzb =(100*parseFloat(hklxfl[i].xfje)/xfze).toFixed(2);
				else if (hklxfl[i].hklx == '农村') fnhkzb =(100*parseFloat(hklxfl[i].xfje)/xfze).toFixed(2);
			 }
		 for ( var i = 0; i < pklxfl.length; i++) {
				if(pklxfl[i].zt == '贫困') pkzb =(100*parseFloat(pklxfl[i].xfje)/xfze).toFixed(2);
				else if (pklxfl[i].zt == '非贫困') fpkzb =(100*parseFloat(pklxfl[i].xfje)/xfze).toFixed(2);
			 }
		 var topdata = {xfze : xfze, nszb : nszb,  nvzb : nvzb, nchkzb : nchkzb,  fnhkzb : fnhkzb,  pkzb : pkzb,  fpkzb : fpkzb};
		 this.render("sc_xsxfqktj_top", this.xsxfxxtop, topdata);
    },
   
    /**
     * 创建日期区间组件。
     */
    createDateSection:function(){
    	var me = this,today = new Date();
        var dateSection = this.dateSection = new NS.appExpand.DateSection();
        me.dateSection.setValue({from : today - 14 * 3600* 24000,to : today});
        dateSection.addListener('validatepass',function(){
            var params = this.timeParams= this.dateSection.getRawValue();
            me.renderChildComp(params);
        },this);
        dateSection.render("sc_xsxfqktj_datepicker");
    },
    /**
     * 渲染底部pie图和Table
     */
    createPieChartAndTable:function(data,xfze){
    	 var cols = ['早上','中午','晚上'];
    	 var rows = [];
    	 var rowb = {};
    	 for ( var k = 0; k < data.length; k++) {
				var item = data[k].dd;
				if(!rowb[item]){
					rows.push(item);
					rowb[item] = true;
				}
			 }
    	 var pieData = [],tabData =[]; 
    	 //转型pie图数据
    	 for ( var i = 0; i < cols.length; i++) {
    		 var dd = {name : cols[i],y : 0};
    		 for ( var j = 0; j < rows.length; j++) {
    			for ( var k = 0; k < data.length; k++) {
    				var item = data[k];
    				if(item.dd == rows[j] && item.sj == cols[i]){
    					dd.y += parseFloat(item.xfje);
    				}
				}
    		 }
             dd.y = Number(dd.y.toFixed(2));
             console.log(dd.y);
    		 pieData.push(dd);
		 }
    	 //转型Table数据
    	 for ( var i = 0; i < rows.length; i++) {
    		 var tt = {name : rows[i],data : []};
    		 for ( var j = 0; j < cols.length; j++) {
    			 var dt = {name : cols[j],value : 0,scale : 0};
    			 for ( var k = 0; k < data.length; k++) {
    				var item = data[k];
     				if(item.dd == rows[i] && item.sj == cols[j]){
     					dt.value = parseFloat(item.xfje);
     					dt.scale = (100*parseFloat(item.xfje)/xfze).toFixed(2);
     				}
 				 }
    			 tt.data.push(dt);
    		 }
    		 tabData.push(tt);
		 }
    	 this.renderPieChart("sc_xsxfqktj_pie","消费时间情况","消费金额",pieData);
    	 this.render("sc_xsxfqktj_table", this.xsxfxxtable, tabData);
    },
    
    /**
     * 渲染中部chart。
     */
    createChart : function(data){
    	var cats = [],xfe = [],rjxfe = [],xfrs = [];
    	for ( var i = 0; i < data.length; i++) {
    		var item = data[i];
			cats.push(item.xfrq);
			xfe.push(parseFloat(item.xfje));
			xfrs.push(parseInt(item.xfrs));
			rjxfe.push(parseFloat(item.rjxfe));
		}
    	
	    $('#sc_xsxfqktj_chart').highcharts({
	        chart: {
	            zoomType: 'xy'
	        },
	        title: {
	            text: '学生消费情况统计'
	        },
	        credits : {// 不显示highchart标记
                enabled : false
            },
	        xAxis: [{
	            categories: cats,
	            labels: {
                    rotation: -60
                }

	        }],
	        yAxis: [ { // Primary yAxis
	            labels: {
	                format: '{value} 元',
	                style: { color: Highcharts.getOptions().colors[1]}
	            },
	            min: 0,
	            title: {
	                text: '消费额',
	                style: { color: Highcharts.getOptions().colors[1]  }
	            },
	            opposite: true
	        }, 
	        { // Secondary yAxis
	            gridLineWidth: 0,
	            min: 0,
	            labels: {
	            	format: '{value} 元/人',
	            	style: { 	color: Highcharts.getOptions().colors[0] }
	            },
	            title: {
	                text: '人均消费额',
	                style: { color: Highcharts.getOptions().colors[0]  }
	            }

	        },
	        { // Tertiary yAxis
	            gridLineWidth: 0,
	            min: 0,
	            labels: {
	            	format: '{value} 人',
	            	style: { color: Highcharts.getOptions().colors[2] }
	            },
	            title: {
	                text: '消费人数',
	                style: { color: Highcharts.getOptions().colors[2] }
	            },
	            opposite: true
	        }],
	        tooltip: {
	            shared: true
	        },
	        legend: {
                borderWidth: 1,
                borderRadius : 3
            },
	        series: [  {
	            name: '人均消费额',
	            type: 'column',
	            yAxis: 1,
	            data: rjxfe,
	            tooltip: {
	                valueSuffix: ' 元/人'
	            }

	        },{
	            name: '消费额',
	            type: 'spline',
	            data: xfe,
	            tooltip: {
	                valueSuffix: ' 元'
	            }
	        },{
	            name: '消费人数',
	            type: 'spline',
	            yAxis: 2,
	            data: xfrs,
	            marker: {
	                enabled: false
	            },
	            dashStyle: 'shortdot',
	            tooltip: {
	                valueSuffix: ' 人'
	            }

	        }]
	    });
    },
    clearDiv : function(domId){
        var dom=document.getElementById(domId);
        dom.innerHTML="";
    },
    render:function(nodeId,tpl,data){
        this.clearDiv(nodeId);
        var comp = new NS.Component({
            data : data,
            tpl : tpl
        });
        comp.render(nodeId);
    }
});
