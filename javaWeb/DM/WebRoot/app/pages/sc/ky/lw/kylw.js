NS.define('Pages.sc.ky.lw.kylw',{
	extend:'Template.Page',
	
	modelConfig: {
		serviceConfig: {
			eINumsRanks : 'ranksNums?eINumsRanks',//EI收录排名
			iSTPNumsRanks : 'ranksNums?iSTPNumsRanks',// ISTP收录排名
			cPCINumsRanks : 'ranksNums?cPCINumsRanks',// CPCI收录排名
			allRanks : 'ranksNums?allRanks',// 所有排名
			eIYears : 'ranksNums?eIYears',// EI收录的所有年份
			iSYears : 'ranksNums?iSYears',// ISTP收录的所有年份
			cPYears : 'ranksNums?cPYears',// CPCI收录的所有年份
			departmentThesisNums : 'ranksNums?departmentThesisNums',// 各院系论文数
			awardThesisRate : 'ranksNums?awardThesisRate',// 各学院获奖论文的比率
			thesisInclude : 'ranksNums?thesisInclude',// 被收录的论文数量和院系名称
			queryAllCountsThesis : 'ranksNums?queryAllCountsThesis',// 查询所有的科研论文数
			pulishThesis : 'ranksNums?pulishThesis',// 查询某院系某年发布的期刊数量和名称
			awardThesisRanks : 'ranksNums?awardThesisRanks',// 查询某院系某年的获奖数量、等级以及名称
			eIInclude : 'ranksNums?eIInclude',// 查询某院系某年的EI收录数量
			iSInclude : 'ranksNums?iSInclude',// 查询某院系某年的ISTP收录数量
			cPInclude : 'ranksNums?cPInclude',// 查询某院系某年的CPCI-SI收录数量
			awardTrend : 'ranksNums?awardTrend',// 历年的获奖趋势
			includeTrend : 'ranksNums?includeTrend',// 发布的期刊数量和名称以及年份
			includeTrendNames : 'ranksNums?includeTrendNames',// 发布的期刊所有名称
			thesisTrend : 'ranksNums?thesisTrend',// 收录的期刊数量和名称以及年份
			awardRatesAll : 'ranksNums?awardRatesAll',// 根据学科门类查询所有获奖院系数量
			queryAllAwardCounts : 'ranksNums?queryAllAwardCounts',// 查询所有获奖数量
			queryAllIncludeCounts : 'ranksNums?queryAllIncludeCounts',// 查询所有收录的数量
			kindOfYearsDeptsCounts : 'ranksNums?kindOfYearsDeptsCounts', // 各年各院系的论文数量
			queryThesisLimitPage : {service : 'ranksNums?queryThesisLimitPage',
				params:{
	                limit:10,
	                start:0
            	}
			}, //根据某院系，某年，某种期刊进行分页查询
			queryAwardThesisLimitPage : {service : 'ranksNums?queryAwardThesisLimitPage',
				params:{
	                limit:10,
	                start:0
            	}
			},// 获奖论文分页
			queryEIThesisLimitPage : {service : 'ranksNums?queryEIThesisLimitPage',
				params:{
	                limit:10,
	                start:0
            	}
			},// EI收录论文分页
			queryISThesisLimitPage : {service : 'ranksNums?queryISThesisLimitPage',
				params:{
	                limit:10,
	                start:0
            	}
			},// IS收录论文分页
			queryCPThesisLimitPage : {service : 'ranksNums?queryCPThesisLimitPage',
				params:{
	                limit:10,
	                start:0
            	}
			}// CP-CI收录论文分页
		}
	},
	mixins:[],
	cssRequires : ['app/pages/sc/ky/lw/lwcss/lw.css','app/pages/sc/template/css/sc_base.css'],
    init: function() {
    	var me=this;
    	var htmlButton1 = "<div class='code_type1' style='width:50px;height:80px;border:1px solid black;display:none'><psna></span></div>";
    	var htmlButton2 = "<div class='code_type2' style='width:50px;height:80px;border:1px solid black;display:none'><psna></span></div>";
    	var departmentsThesisHtml = "<div id='test_2' class='curve_bar_ranks' style='width:100%;height:500px'></div>";
    	var htmls = "<div id='test_1' class='curve_bar_ranks' style='width:100%;height:500px'></div>";
    	var everyDeptIncludeHtml = "<div id='test_3' style='width:95%;height:850px;display:none;margin:0 0 0 50px'>" +
    	
    			"<div style='float:right;width:100%;height:20px'>" +
    			"<a id='back_test_2' style='float:right;' href='javascript:void(0)'>返回上一层</a>" +
    			"</div>" +
    			
    			"<div style='width:33.3%;height:800px;float:left;'>" +
    			
    			"<div style='padding:10px 15px;'>" +
    			"<div id='pilish_situation' class='curve_bar_ranks' style='width:100%;height:500px'></div>" +
    			"<div id='thesis_rates' class='curve_bar_ranks' style='width:100%;height:300px'></div>" +
    			"<div id='pilish_trend'></div>" +
    			"</div>" +
    			
    			"</div> " +
    			
    			"<div style='width:33.3%;height:800px; float:left;border-left:1px dashed #3399cc;'>" +
    			
    			"<div style=' padding:10px 15px;'>" +
    			"<div id='include_situation' class='curve_bar_ranks' style='width:100%;height:500px'></div>" +
    			"<div id='include_rates' class='curve_bar_ranks' style='width:100%;height:300px'></div>" +
    			"<div id='include_trend'></div>" +
    			"</div>" +
    			
    			"</div>" +
    			
    			"<div style='width:33.3%;height:800px; float:left;border-left:1px dashed #3399cc;'>" +
    			"<div  style='padding:10px 15px;'>" +
    			"<div id='award_situation' class='curve_bar_ranks' style='width:100%;height:500px'></div>" +
    			"<div id='award_rates' class='curve_bar_ranks' style='width:100%;height:300px'></div>" +
    			"</div>" +
    			
    			"<div id='award_trend'></div>" +
    			"</div>" +
    			"</div>";
    	var awardTrendHtml = "<div id='test_4' class='curve_bar_ranks' style='width:100%;height:500px;display:none'></div>";
    	var titleDescription = "<div style='color:blue;margin:10px 0 10px 20px'>据中国科学技术信息研究所提供的检索报告，我校历年的排名情况如下图：</div>";
    	var beforeThesisHtml = "<div id='look_before_thesis_showhide' style='width:100%;height:20px;'><a href='javascript:void(0)' id='look_before_thesises' style='float:right;'>论文获奖/收录率历年趋势</a></div>";
    	var allRateHtml = "<div id='show_or_hide_rates' style='width:100%;height:500px;display:none'>" +
    			"<div id='award_rate_all' class='curve_bar_ranks' style='width:49%;height:500px;float:left'></div>" +
    			"<div id='include_rate_all' class='curve_bar_ranks' style='width:49%;height:500px;float:left'></div>" +
    			"</div>";
    	
		"<hr style='margin-top: 5px;' color='#5299eb'></div>";
    	
    	var pagesLoading = "<div class='loading-indicator'>正在加载....</div>";
    	
    	var pageTitle = new Exp.component.PageTitle({
             data:{
                 pageName:'论文统计分析',
                 pageHelpInfo:'对各学院历年的论文分析。'}
         });
    	
         var titleDescriptionHtml = this.component = new NS.Component({
    	 border : true,
            baseCls : '',
            html : titleDescription,
            autoShow : true
         });
         var allRates = this.component = new NS.Component({
        	 border : true,
             baseCls : '',
             html : allRateHtml,
             autoShow : true
          });
         var beforeThesis = this.component = new NS.Component({
        	 border : true,
	          baseCls : '',
	          html : beforeThesisHtml,
	          autoShow : true
         });
         var awardThredHtml = this.component = new NS.Component({
        	 	border : true,
	            baseCls : '',
	            html : awardTrendHtml,
	            autoShow : true
         });
         var page = this.component= new NS.Component({
	            border : true,
	            baseCls : '',
	            html : htmls,
	            autoShow : true
         });
         var hButton1 = this.component = new NS.Component({
        	 border : true,
	            baseCls : '',
	            html : htmlButton1,
	            autoShow : true
         });
         var hButton2 = this.component = new NS.Component({
        	 border : true,
	            baseCls : '',
	            html : htmlButton2,
	            autoShow : true
         });
         var deptThesis = this.component= new NS.Component({
	            border : true,
	            baseCls : '',
	            html : departmentsThesisHtml,
	            autoShow : true
         });
         var  everyDepts = this.component = new NS.Component({
        	 border : true,
	         baseCls : '',
        	 html : everyDeptIncludeHtml,
        	 autoShow : true
         });
         var yearsController = me.yearController();
         var headClick = me.headerClick();
         var container = new NS.container.Container({
             padding:20,
             autoScroll:true,
             items:[pageTitle,headClick,me.title1,hButton1,hButton2,titleDescriptionHtml,page,me.titleSecond,yearsController,deptThesis,everyDepts,beforeThesis,awardThredHtml,allRates]
         });
         container.on('afterrender', function(){
        	 $(".curve_bar_ranks").html(pagesLoading);
        	 var typeCulture = {codeType:'110-180'};
        	 var chooseYear = {years:new Date().getFullYear()+''};
        	 var coditionsObj = {'typeCulture' : typeCulture, 'chooseYear':chooseYear};
        	 this.includeNumsRanks(typeCulture);
        	 this.deptThesis(coditionsObj);
        	 this.cultureTypeClick();	//自然学科和社会科学点击事件
        	 this.clickThesisBefore();
         },this);
         this.setPageComponent(container);
    },
    
    title1 : new Exp.chart.PicAndInfo({
		title : "我校历年被收录论文的全国高校排名",
		onlyTitle : true,
		margin : "10px 0px 0px 0px"
	}),
	
	 titleSecond : new Exp.chart.PicAndInfo({
			title : "各学院论文数",
			onlyTitle : true,
			margin : "10px 0px 0px 0px"
	}),
	
    headerClick : function() {
    	var cultureButton = "<div style='height:35px;' class='list-line'>" +
		"<div class='list-tab'>" +
		"<a id='culture_click_function' href='javascript:void(0)' class='list-selected'>自然科学类</a>" +
		"<a id='social_click_function' href='javascript:void(0)' class='list-default'>社会科学类</a>" +
		"</div>" +
		"</div>";
    	 var cultureButtonHtml = this.component = new NS.Component({
        	 	border : true,
	            baseCls : '',
	            html : cultureButton,
	            autoShow : true
         });
    	 var container = new NS.container.Container({
             padding:20,
             autoScroll:true,
             items:[cultureButtonHtml]
         });
    	return container;
    },
    
    cultureTypeClick : function() {
    	var me = this;
    	$("#culture_click_function").click(function() {
    		$("#show_or_hide_rates").hide();
    		$("#years_conponent").show();
    		$(".code_type1").attr('id','culture_code');
       	    $(".code_type2").attr('id','');
    		$(this).addClass('list-selected').removeClass('list-default');
    		$("#social_click_function").addClass('list-default').removeClass('list-selected');
    		 var typeCulture = {codeType:'110-180'};
        	 var chooseYear = {years:new Date().getFullYear()+''};
        	 var coditionsObj = {'typeCulture' : typeCulture, 'chooseYear':chooseYear};
        	 me.dealFreshShowHide();
        	 me.includeNumsRanks(typeCulture);
        	 me.deptThesis(coditionsObj);
        	 me.deptThesis(coditionsObj);
        	 me.callService([{key:'awardRatesAll',params:typeCulture},{key:'thesisTrend',params:typeCulture},{key:'kindOfYearsDeptsCounts',params:typeCulture}], function(datas){
				 if((datas.awardRatesAll.length == 0) && (datas.thesisTrend.length == 0)) {
					 $("#look_before_thesis_showhide").hide();
				 } else {
					 $("#look_before_thesis_showhide").show();
				 }
				  me.awardThesisRatesAll(datas);
				  me.includeRatesAll(datas);
			  });
        	 me.combobox.setValue(new Date().getFullYear()+'');    
    	});
    	$("#social_click_function").click(function() {
    		$("#show_or_hide_rates").hide();
    		$("#years_conponent").show();
    		$(".code_type1").attr('id','');
       	 	$(".code_type2").attr('id','social_code');
    		$(this).addClass('list-selected').removeClass('list-default');
    		$("#culture_click_function").addClass('list-default').removeClass('list-selected');
    		 var typeCulture = {codeType:'710-910'};
	       	 var chooseYear = {years:new Date().getFullYear()+''};
	       	 var coditionsObj = {'typeCulture' : typeCulture, 'chooseYear':chooseYear};
	       	 me.dealFreshShowHide();
	         me.includeNumsRanks(typeCulture);
	         me.deptThesis(coditionsObj);
	         me.callService([{key:'awardRatesAll',params:typeCulture},{key:'thesisTrend',params:typeCulture},{key:'kindOfYearsDeptsCounts',params:typeCulture}], function(datas){
				 if((datas.awardRatesAll.length == 0) || (datas.thesisTrend.length == 0)) {
					 $("#look_before_thesis_showhide").hide();
				 } else {
					 $("#look_before_thesis_showhide").show();
				 }
				  me.awardThesisRatesAll(datas);
				  me.includeRatesAll(datas);
			  });
	         me.combobox.setValue(new Date().getFullYear()+'');    
    	});
    },
    
    includeNumsRanks : function(codeType){
    	this.callService([{key:'eINumsRanks',params:codeType},{key:'iSTPNumsRanks',params:codeType},{key:'cPCINumsRanks',params:codeType},'allRanks','eIYears','iSYears','cPYears'],function(datas){
    		
    		this.thesisNumsRanks(datas);
        },this);
    },
    
    deptThesis : function(coditionsObj){
    	this.callService([{key:'departmentThesisNums',params:coditionsObj},{key:'awardThesisRate',params:coditionsObj},{key:'thesisInclude',params:coditionsObj}],function(datas){
    		this.awardDeptThesisNums(datas);
    	},this);
    },
    
    thesisNumsRanks : function(datas) {
   	var eINums = [];
   	var iSTPNums = [];
   	var cPCINums = [];
   	var eIRanks= [];
   	var iSRanks = [];
   	var cPRanks = [];
   	var eIRankData = [];
   	var iSRankData = [];
   	var cPRankData = [];
   	var yearArrays = [];
   	var xData = [];
   	yearArrays.push(Number(datas.eIYears[0].YEARS),Number(datas.eIYears[datas.eIYears.length-1].YEARS),Number(datas.iSYears[0].YEARS),Number(datas.iSYears[datas.iSYears.length-1].YEARS),Number(datas.cPYears[0].YEARS),Number(datas.cPYears[datas.cPYears.length-1].YEARS));
   	for(var i = 0; i < yearArrays.length; i++) {
   		for(var j = i+1; j < yearArrays.length; j++) {
   			if(yearArrays[i] > yearArrays[j]) {
   				var tmp = yearArrays[i];
   				yearArrays[i] = yearArrays[j];
   				yearArrays[j] = tmp;
   			}
   		}
   	}
   	var minYear = yearArrays[0];
   	var maxYear = yearArrays[yearArrays.length-1];
   	for(var i = minYear; i <= maxYear; i++) {
   		if(minYear <= maxYear) {
   			xData.push(minYear);
   		}
   		minYear++;
   	}
   	var dataEI={};
   	var dataIS={};
   	var dataCP={};
   	var eIDataRanks={};
   	var iSDataRanks={};
   	var cPDataRanks={};
   	
	for(var i = 0; i <datas.allRanks.length; i++) {
		if(datas.allRanks[i].INCLUDETPES == 'SCI/SCIE/EI') {
			eIRanks.push(datas.allRanks[i]);
		} 
		else if(datas.allRanks[i].INCLUDETPES == 'ISTP') {
			iSRanks.push(datas.allRanks[i]);
		} 
		else if(datas.allRanks[i].INCLUDETPES == 'CPCI-SI') {
			cPRanks.push(datas.allRanks[i]);
		}
   	}
	for(var i=0;i < eIRanks.length; i++) {
		var eI = eIRanks[i];
		eIDataRanks[eI.YEARS]=eI;
	}
	for(var i=0;i < iSRanks.length; i++) {
		var iSs = iSRanks[i];
		iSDataRanks[iSs.YEARS] = iSs;
	}
	for(var i=0;i < cPRanks.length; i++) {
		var cP = cPRanks[i];
		cPDataRanks[cP.YEARS] = cP;
	}
	
   	for(var i = 0; i < datas.eINumsRanks.length; i++) {
   		var o=datas.eINumsRanks[i];
   		dataEI[o.YEARS]=o;
   	}
   	
   	for(var j=0; j< datas.iSTPNumsRanks.length; j++) {
   		var iS = datas.iSTPNumsRanks[j];
   		dataIS[iS.YEARS] = iS;
   	}
   	
	for(var k=0; k< datas.cPCINumsRanks.length; k++) {
		var cP = datas.cPCINumsRanks[k];
		dataCP[cP.YEARS] = cP;
   	}
   	
   	for(var i = 0; i < xData.length; i++) {
   		var year=xData[i];
   		if(dataEI[year]){
   			eINums.push(dataEI[year].COUNTS);
   		}else{
   			eINums.push(0);
   		}
   		
   		if(dataIS[year]) {
   			iSTPNums.push(dataIS[year].COUNTS);
   		}else {
   			iSTPNums.push(0);
   		}
   		
   		if(dataCP[year]) {
   			cPCINums.push(dataCP[year].COUNTS);
   		} else {
   			cPCINums.push(0);
   		}
   		
   		if(eIDataRanks[year]) {
   			eIRankData.push(eIDataRanks[year].RANKS);
   		}else {
   			eIRankData.push(0);
   		}
   		
   		if(iSDataRanks[year]) {
   			iSRankData.push(iSDataRanks[year].RANKS);
   		}else {
   			iSRankData.push(0);
   		}
   		if(cPDataRanks[year]) {
   			cPRankData.push(cPDataRanks[year].RANKS);
   		} else {
   			cPRankData.push(0);
   		}
   	}
   	var myChart = echarts.init(document.getElementById('test_1'),'blue');
 option = {
 	
     tooltip : {
         trigger: 'axis',
       formatter: function(params) {
         var outS=params[0].name + '年<br/>';
         var len = params.length;
                 while(len--) {
                   var val= '';
                   if(params[len].value<=0){
                   	val= params[len].value*(-1) + ' (名)<br/>';
                   }else{
                   	val= params[len].value + ' (篇)<br/>';
                   }
                   outS+=params[len].seriesName + ' : ' +val;
                 }
             return outS;
         }
     },
     
    dataZoom : {
         show : true,
         realtime : true,
         start : 20,
         end : 80
     },
     calculable : true,
     legend: {
    	 // 默认这四项不显示
    	 /*selected:{
             '被EI收录论文数':false,
             '被SCI引用论文数':false,
             '被EI收录论文排名':false,
             '被SCI引用论文排名':false
         },*/
         data:['被SCI光盘版及SCIE收录论文数','被EI收录论文数',
               '被SCI引用论文数',
               '被SCI光盘版及SCIE收录论文排名','被EI收录论文排名',
              '被SCI引用论文排名']
     },
     toolbox: {
         show : true,
         feature : {
             saveAsImage : {show: true}
         }
     },
     xAxis : [
         {
             type : 'category',
             boundaryGap : true,
             axisLine: {onZero: false},
             data : function (){
                 var list = [];
                 for (var i = 0; i < xData.length; i++) {
                     list.push(xData[i]);
                 }
                 return list;
             }()
         }
     ],
     yAxis : [
         {
             type : 'value',
             name : '数量',
             axisLabel : {
                 formatter: '{value}'
             }
         },
         {
             name : '排名',
             type : 'value',
             axisLabel : {
                 formatter: function(v){
                	 if(v >= 0){
                		 return v;
                	 }else {
                		 return  - v;
                	 }
                     
                 }
             }
         }
     ],
     series : [

         {
             name:'被SCI光盘版及SCIE收录论文数',
             type:'bar',
             data:iSTPNums
         },
         {
             name:'被EI收录论文数',
             type:'bar',
             data:eINums
         },
       {
             name:'被SCI引用论文数',
             type:'bar',
             data:cPCINums
         },
        {
             name:'被SCI光盘版及SCIE收录论文排名',
             type:'line',
             yAxisIndex: 1,
             data : function (){
                var yData=iSRankData;
                var len = yData.length;
                 while(len--) {
                     yData[len] *= -1;
                 }
                 return yData;
             }()
         },
       {
             name:'被EI收录论文排名',
             type:'line',
        		 yAxisIndex: 1,
        		data : function (){
                var yData=eIRankData;
                var len = yData.length;
                 while(len--) {
                     yData[len] *= -1;
                 }
                 return yData;
             }()
         },
       {
             name:'被SCI引用论文排名',
             type:'line',
        	 yAxisIndex: 1,
         data : function (){
                var yData=cPRankData;
                var len = yData.length;
                 while(len--) {
                     yData[len] *= -1;
                 }
                 return yData;
             }()
         }
     ],
     noDataLoadingOption : {
    	  	text : '收录论文全国高校排名暂无数据',
    	    effectOption : null,
    	    effect : 'bubble',
    	    textStyle : {fontSize : 20},
    	    effectOption : {
    	    	effect : {n:'0'}
    	    }
    }
 };
      myChart.setOption(option);  
  },
  
  awardDeptThesisNums : function(datas) {
	  var deptNames = [];
	  var deptNums = [];
	  var awRates = [];
	  var cludesThesis = [];
	  var awards={};
	  var cludes={};
	  var awYear = datas.thesisInclude[0].year;
	  var awTypeCode = datas.thesisInclude[0].codeType;
	  for(var i = 0;i < datas.departmentThesisNums.length; i++) {
		  deptNames.push(datas.departmentThesisNums[i].NAMES);
		  deptNums.push(datas.departmentThesisNums[i].COUNTS);
	  }
	  
	  for(var i = 0; i < datas.awardThesisRate.length; i++) {
		  var aw = datas.awardThesisRate[i];
		  awards[aw.NAMES]=aw;
	  }
	  
	  for(var  i =0; i < datas.thesisInclude.length; i++) {
		  var cludeThesis = datas.thesisInclude[i];
		  cludes[cludeThesis.NAMES]=cludeThesis;
	  }
	  
	  for(var i =0; i < deptNames.length; i++) {
		  var names = deptNames[i];
		  if(awards[names]) {
			  awRates.push((awards[names].COUNTS/deptNums[i]).toFixed(3));
		  } else {
			  awRates.push(0);
		  }
		  if(cludes[names]) {
			  cludesThesis.push((cludes[names].COUNTS/deptNums[i]).toFixed(3));
		  } else {
			  cludesThesis.push(0);
		  }
	  }
	  
	  
	  
	  
	  var myChart = echarts.init(document.getElementById('test_2'),'blue');
	  
		var xData = deptNames,
	option = {
		
	    tooltip : {
	        trigger: 'axis',
	      formatter: function(params) {
	        var outS=params[0].name + '年<br/>';
	        var len = params.length;
	                while(len--) {
	                  var val= '';
	                  if(params[len].value<=0){
	                  	val= params[len].value*(-1) + ' <br/>';
	                  }else{
	                  	val= params[len].value + ' (篇)<br/>';
	                  }
	                  outS+=params[len].seriesName + ' : ' +val;
	                }
	            return outS;
	        }
	    },
	    
	   dataZoom : {
	        show : true,
	        realtime : true,
	        start : 20,
	        end : 60
	    },
	    legend: {
	        data:['论文数量','论文获奖率','论文收录率']
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            saveAsImage : {show: true}
	        }
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : true,
	            axisLine: {onZero: false},
	            data : function (){
	                var list = [];
	                for (var i = 0; i < xData.length; i++) {
	                    list.push(xData[i]);
	                }
	                return list;
	            }()
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            name : '数量',
	            axisLabel : {
	                formatter: '{value}'
	            }
	        },
	        {
	            name : '获奖/收录率',
	            type : 'value',
	            axisLabel : {
	                formatter: function(v){
	                	if(v >=0) {
	                		return v;
	                	}else {
	                		return  - v;
	                	}
	                    
	                }
	            }
	        }
	    ],
	    series : [

	        {
	            name:'论文数量',
	            type:'bar',
	            data:deptNums
	        },
	        
	       {
	            name:'论文获奖率',
	            type:'line',
	            yAxisIndex: 1,
	            data : function (){
	               var yData=awRates
	               var len = yData.length;
	                while(len--) {
	                    yData[len] *= -1;
	                }
	                return yData;
	            }()
	        },
	      {
	            name:'论文收录率',
	            type:'line',
	       		 yAxisIndex: 1,
	       		data : function (){
	               var yData=cludesThesis
	               var len = yData.length;
	                while(len--) {
	                    yData[len] *= -1;
	                }
	                return yData;
	            }()
	        }
	    ],
	    noDataLoadingOption : {
    	  	text : '各学院论文数暂无数据',
    	    effectOption : null,
    	    effect : 'bubble',
    	    textStyle : {fontSize : 20,fontWeight : 'normal'},
    	    effectOption : {
    	    	effect : {n:'0'}
    	    }
	    }
	};
	     myChart.setOption(option);               
	  
	  var me=this;
	  myChart.on(echarts.config.EVENT.CLICK, function(datas){
		  $(".curve_bar_ranks").html(this.pagesLoading);
		  $("#years_conponent").hide();
		  $("#test_2").hide();
		  $("#test_3").show();
		  $("#look_before_thesis_showhide").hide();
		  $("#show_or_hide_rates").hide();
		  var deptName = datas.name;
		  var years = { 'year' : awYear};
		  var deptNames = {'deptName': deptName};
		  var typeCodes = {'typeCode' : awTypeCode};
		  var coditionsObj = {'deptNames':deptNames,'years':years,'typeCodes':typeCodes};
		  me.dealBackToUp();
		  me.callService([{key:'pulishThesis',params:coditionsObj},{key:'awardThesisRanks',params:coditionsObj},{key:'eIInclude',params:coditionsObj},{key:'iSInclude',params:coditionsObj},{key:'cPInclude',params:coditionsObj},'queryAllCountsThesis'],function(datas){
			  me.publishSituation(datas);
			  me.awardSituation(datas);
			  me.includeSituation(datas);
			  me.thesisRate(datas);
			  me.awardThesisTable(datas);
			  me.includeThesisTable(datas);
		  });
	  });
  },
  
  publishSituation : function(datas) {
	  var publishNames = [];
	  for(var i = 0; i < datas.pulishThesis.length; i++) {
		  publishNames.push(datas.pulishThesis[i].name);
	  }
	  var myChart = echarts.init(document.getElementById('pilish_situation'),'blue');
		option = {
			title: {
				text: '论文发表情况',
				x: 'center'
			},
			tooltip: {
				show: true, //鼠标放在柱状图上，是否显示对应的文字说明（true:显示，false:不显示）
				trigger: 'item',
				//对应文字说明 显示格式(模板 格式：'{a} < br/>{b} : {c}') ,可计算平均值
				//a(标题名称) b(图例名称) c（图例值） d(计算的平均值) 
				formatter: "{a} <br/>{b} : {c} ({d}%)" 
			},
			//顶部图例 控制
			legend: {
				show: true,
				data: publishNames, 
				x: 'center',
				y : 'bottom',
				orient: 'horizontal'
			},
			
			toolbox: { 
				show: true,
				feature: {
					saveAsImage: {
						show: true//保存为图片
					}
				}
			},
			calculable: true, //在饼状图外面加一圈，灰色圈，看上去好看些（柱状图，没有这个效果，加了也没用）
			series: [{
				name: '发表论文',//鼠标放图上显示标题（这个标题最好与顶部图例标题一致）
				type: 'pie', //bar 柱状图，line 折线图  pie 饼状图   funnel漏斗图
				radius: '35%',
				center: ['50%', '55%'],
				data: datas.pulishThesis
			}],
			 noDataLoadingOption : {
				  	text : '论文发表情况暂无数据',
				    effectOption : null,
				    effect : 'bubble',
				    textStyle : {fontSize: 20},
				    effectOption : {
				    	effect : {n:'0'}
				    }
			}
		};
		myChart.setOption(option);
  }, 
  
  awardSituation : function(datas) {
	  var thesisRankNames = [];
	  var namesValues = [];
	  for(var i = 0; i < datas.awardThesisRanks.length; i++) {
		  namesValues.push({'name':datas.awardThesisRanks[i].NAMES,'value':datas.awardThesisRanks[i].COUNTS});
		  thesisRankNames.push(datas.awardThesisRanks[i].NAMES);
	  }
		var myChart = echarts.init(document.getElementById('award_situation'),'blue');
		option = {
			title: {
				text: '获奖论文情况',
				x: 'center'
			},
			tooltip: {
				show: true, //鼠标放在柱状图上，是否显示对应的文字说明（true:显示，false:不显示）
				trigger: 'item',
				//对应文字说明 显示格式(模板 格式：'{a} < br/>{b} : {c}') ,可计算平均值
				//a(标题名称) b(图例名称) c（图例值） d(计算的平均值) 
				formatter: "{a} <br/>{b} : {c} ({d}%)" 
			},
			//顶部图例 控制
			legend: {
				show: true,//true（显示） false（隐藏），默认值为true  
				data: thesisRankNames,
				x: 'center',
				y : 'bottom',
				orient: 'horizontal' //图例显示格式：（水平： horizontal）(垂直：vertical)
			},
			//右侧功能图标
			toolbox: { 
				show: true,
				feature: {
					saveAsImage: {
						show: true//保存为图片
					}
				}
			},
			calculable: true, //在饼状图外面加一圈，灰色圈，看上去好看些（柱状图，没有这个效果，加了也没用）
			series: [{
				name: '获奖论文情况',//鼠标放图上显示标题（这个标题最好与顶部图例标题一致）
				type: 'pie', //bar 柱状图，line 折线图  pie 饼状图   funnel漏斗图
				radius: '35%',
				center: ['50%', '55%'],
				data: namesValues
			}],
			 noDataLoadingOption : {
				  	text : '论文获奖情况暂无数据',
				    effectOption : null,
				    effect : 'bubble',
				    textStyle : {fontSize: 20},
				    effectOption : {
				    	effect : {n:'0'}
				    }
			}
		};
		myChart.setOption(option);
  }, 
  
  includeSituation : function(datas) {
	  var allInclude=[];
	  var allIncludeName = [];
	  if(datas.eIInclude[0].value == 0 && datas.iSInclude[0].value == 0 && datas.cPInclude[0].value == 0) {
		  allInclude.push();
		  allIncludeName.push();
	  } else {
		  allIncludeName.push(datas.eIInclude[0].name,datas.iSInclude[0].name,datas.cPInclude[0].name);
		  allInclude.push({name : datas.eIInclude[0].name, value : datas.eIInclude[0].value},{name : datas.iSInclude[0].name, value : datas.iSInclude[0].value},{name : datas.cPInclude[0].name, value : datas.cPInclude[0].value});
	  }
	  var myChart = echarts.init(document.getElementById('include_situation'),'blue');
		option = {
			title: {
				text: '论文收录情况',
				x: 'center'
			},
			tooltip: {
				show: true, //鼠标放在柱状图上，是否显示对应的文字说明（true:显示，false:不显示）
				trigger: 'item',
				//对应文字说明 显示格式(模板 格式：'{a} < br/>{b} : {c}') ,可计算平均值
				//a(标题名称) b(图例名称) c（图例值） d(计算的平均值) 
				formatter: "{a} <br/>{b} : {c} ({d}%)" 
			},
			//顶部图例 控制
			legend: {
				show: true,
				data: /*['SCI/SCIE/EI收录', 'ISTP收录', 'CPCI-S收录']*/allIncludeName,
				x: 'center',
				y :'bottom',
				orient: 'horizontal'
			},
			//右侧功能图标
			toolbox: { 
				show: true,
				feature: {
					saveAsImage: {
						show: true//保存为图片
					}
				}
			},
			calculable: true, //在饼状图外面加一圈，灰色圈，看上去好看些（柱状图，没有这个效果，加了也没用）
			series: [{
				name: '访问来源',//鼠标放图上显示标题（这个标题最好与顶部图例标题一致）
				type: 'pie', //bar 柱状图，line 折线图  pie 饼状图   funnel漏斗图
				radius: '35%',
				center: ['50%', '55%'],
				data: allInclude
			}],
			 noDataLoadingOption : {
				  	text : '论文收录情况暂无数据',
				    effectOption : null,
				    effect : 'bubble',
				    textStyle : {fontSize: 20},
				    effectOption : {
				    	effect : {n:'0'}
				    }
			}
		};
		myChart.setOption(option);
  },
  
  thesisRate : function(datas) {
	  if($("#thesis_rates") != null) {
		  $("#thesis_rates").empty();
	  }
	  if($("#pilish_trend") != null) {
		  $("#pilish_trend").empty();
	  }
	  var punishedHtml = "";
	  for(var i = 0; i < datas.pulishThesis.length; i++) {
		  punishedHtml += "<tr>" +
		  "<td>"+datas.pulishThesis[i].name+"</td>" +
		  "<td><a class='pilished_tables' href='javascript:void(0)' books_name='"+datas.pulishThesis[i].name+"'>"+datas.pulishThesis[i].value+"</a></td>" + 
		  "</tr>";
	  }
	  var html =  "<table width='100%' class='lunwen-table'>" +
		"<tr>" +
		"<th>期刊类别</th>" +
		"<th>论文数</th>" +
		"</tr>" +punishedHtml+
		"</table>";
	  if(datas.pulishThesis.length > 0) {
		  $("#thesis_rates").append(html);
		  $("#pilish_trend").append("<a href='javascript:void(0)' id='show_pilish_trend' style='float:right'>历年趋势</a>"); 
	  } else {
		  $("#thesis_rates").append("<h1 class='lunwen-table-nodate'>论文发表数量暂无数据</h1>");
	  }
	  var conditionCodeType = {'codeType':datas.pulishThesis[0].codeType};
	  var me = this;
	  $("#show_pilish_trend").click(function(){
		  $(".curve_bar_ranks").html(this.pagesLoading);
		  $("#test_4").show();
		  me.callService([{key:'includeTrend',params:conditionCodeType},{key:'includeTrendNames',params:conditionCodeType}],function(datas){
			  me.pilishThesisTrend(datas);
		  });
	  });
	  $(".pilished_tables").click(function(){
		  var booksNames = $(this).attr('books_name');
		  var bNames = {'booksNames' : booksNames};
		  var years = {year:datas.pulishThesis[0].years};
		  var deptName = {dName : datas.pulishThesis[0].deptName};
		  var typeCode = {codeType : datas.pulishThesis[0].codeType};
		  var queryConditions = {'years':years,'deptName':deptName,'typeCode':typeCode,'bNames':bNames,'start':0,'limit':15};
		  me.winPage(queryConditions);
	  });
  },
  
  awardThesisTable : function(datas) {
	  if($("#award_rates") != null) {
		  $("#award_rates").empty();
	  }
	  if($("#award_trend") != null) {
		  $("#award_trend").empty();
	  }
	  var awardHtml = "";
	  for(var i = 0; i < datas.awardThesisRanks.length; i++) {
		  awardHtml += "<tr>" +
		  "<td>"+datas.awardThesisRanks[i].NAMES+"</td>" +	
		  "<td><a class='award_thesis_tables' award_books_id='"+datas.awardThesisRanks[i].NAMES+"' href='javascript:void(0)'>"+datas.awardThesisRanks[i].COUNTS+"</a></td>" +		
		  "</tr>";
	  }
	  var html =  "<table width='100%' class='lunwen-table'>" +
		"<tr>" +
		"<th>获奖类别</th>" +
		"<th>论文数</th>" +
		"</tr>" +awardHtml+
		"</table>";
	  if(datas.awardThesisRanks.length > 0) {
		  $("#award_rates").append(html);
		  $("#award_trend").append("<a href='javascript:void(0)' id='show_award_trend' style='float:right'>历年趋势</a>");
	  } else {
		  $("#award_rates").append("<h1 class='lunwen-table-nodate'>论文获奖数量暂无数据</h1>");
	  }
	  var me = this;
	  var conditionCodeType = {'codeType':datas.pulishThesis[0].codeType};
	  $("#show_award_trend").click(function(){
		  $(".curve_bar_ranks").html(this.pagesLoading);
		  $("#test_4").show();
		  me.callService([{key:'awardTrend',params:conditionCodeType}], function(datas){
			  me.awardTrendSituation(datas);
		  });
	  });
	  $(".award_thesis_tables").click(function(){
		  var booksId = $(this).attr("award_books_id");
		  var years = {year:datas.awardThesisRanks[0].years};
		  var deptNames = {dName : datas.awardThesisRanks[0].deptName};
		  var typeCode = {'codeType' : datas.awardThesisRanks[0].codeType};
		  var awradId = {'awradId':booksId};
		  var queryConditions = {'years':years,'deptName':deptNames,'typeCode':typeCode,'awradId':awradId,'start':0,'limit':15};
		  me.awardWinPage(queryConditions);
	  });
  },
  
  includeThesisTable : function(datas) {
	  if($("#include_rates") != null) {
		  $("#include_rates").empty();
	  }
	  if($("#include_trend") != null) {
		  $("#include_trend").empty();
	  }
	  var eIHtml = "";
	  var iSHtml = "";
	  var cPHtml = "";
	  	if(datas.eIInclude[0].value != '0') {
	  		eIHtml = "<tr>" +
			"<td>"+datas.eIInclude[0].name+"</td>" +
			"<td><a class='ei_tables' href='javascript:void(0)'>"+datas.eIInclude[0].value+"</a></td>" +
			"</tr>";
		}
		
		if(datas.iSInclude[0].value != '0') {
			iSHtml = "<tr>" +
			"<td>"+datas.iSInclude[0].name+"</td>" +
			"<td><a class='is_tables' href='javascript:void(0)'>"+datas.iSInclude[0].value+"</a></td>" +
			"</tr>";
		}
		
		if(datas.cPInclude[0].value != '0') {
			cPHtml = "<tr>" +
			"<td>"+datas.cPInclude[0].name+"</td>" +
			"<td><a class='cp_tables' href='javascript:void(0)'>"+datas.cPInclude[0].value+"</a></td>"+
			"</tr>";
		}
		
		 var html =  "<table width='100%' class='lunwen-table'>" +
			"<tr>" +
			"<th>收录类别</th>" +
			"<th>论文数</th>" +
			"</tr>" +eIHtml+iSHtml+cPHtml+
			"</table>";
		
	  if(datas.iSInclude[0].value != 0 || datas.eIInclude[0].value != 0 || datas.cPInclude[0].value != 0) {
		  $("#include_rates").append(html);
		  $("#include_trend").append("<a href='javascript:void(0)' id='show_include_trend' style='float:right'>历年趋势</a>");
	  } else {
		  $("#include_rates").append("<h1 class='lunwen-table-nodate'>论文收录数量暂无数据</h1>");
	  }
	  var me = this;
	  var conditionCodeType = {'codeType':datas.pulishThesis[0].codeType};
	  $("#show_include_trend").click(function(){
		  $(".curve_bar_ranks").html(this.pagesLoading);
		  $("#test_4").show();
		  me.callService([{key:'thesisTrend',params:conditionCodeType}],function(datas){
			  me.includeThesisTrend(datas);
		  });
		 
	  });
	  var years = {year:datas.eIInclude[0].years};
	  var deptNames = {dName : datas.eIInclude[0].deptName};
	  var typeCode = {'codeType' : datas.eIInclude[0].codeType};
	  var queryConditions = {'years':years,'deptName':deptNames,'typeCode':typeCode,'start':0,'limit':15};
	  // EIclick弹窗
	  $(".ei_tables").click(function(){
		 me.eIIncludeWinPage(queryConditions);
	  });
	  // ISclick弹窗
	  $(".is_tables").click(function(){
		  me.iSIncludeWinPage(queryConditions);
	  });
	  // CPclick弹窗
	  $(".cp_tables").click(function(){
		  me.cPIncludeWinPage(queryConditions);
	  });
  }, 
  
  awardTrendSituation : function(datas) {
	  var dataNames = [];
	  var awardObj = [];
	  if(datas.awardTrend.length > 0) {
		  for(var i = 0; i < datas.awardTrend.length; i++) {
			  var names = datas.awardTrend[i].NAMES.split("-")[0];
			  awardObj.push({'NAMES':names+datas.awardTrend[i].RANKNAME, 'COUNTS' : datas.awardTrend[i].COUNTS, 'YEARS':datas.awardTrend[i].YEARS});
			  dataNames.push(names+datas.awardTrend[i].RANKNAME);
		  }
		  var hash = {},
		     len = dataNames.length,
		     result = [];
		 for (var i = 0; i < len; i++){
		     if (!hash[dataNames[i]]){
		         hash[dataNames[i]] = true;
		         result.push(dataNames[i]);
		     } 
		 }
		  
		 
		 var selectResult = {};
			if(result.length>5){
				for(var i=5;i<result.length;i++){
					selectResult[result[i]]=false;
				}
		}
		 
		  
		  var startYear = datas.awardTrend[0].YEARS;
		  var endYear = datas.awardTrend[datas.awardTrend.length - 1].YEARS;
		  var years = [];
		  for(var i = startYear; i <= endYear; i++) {
			  if(startYear <= endYear) {
				  years.push(Number(startYear));
			  }
			  startYear++;
		  }
		  var legents=[];
		  var years=[];
		  var legentMap={};
		  var legentData=[];//series
		  var tmpLegentMap={}; //legent到year临时数据
		  var yearMap={}; //year临时数据
		  
		  for(var i=0;i<awardObj.length;i++){
			  var d=awardObj[i];
			  if(legentMap[d.NAMES]==null){
				  legents.push(d.NAMES);
				  legentMap[d.NAMES]={name:d.NAMES,type:'line',data:[]};
				  tmpLegentMap[d.NAMES]={};
				  legentData.push(legentMap[d.NAMES]);
			  }
			  if(yearMap[d.YEARS]==null){
				  years.push(d.YEARS);
				  yearMap[d.YEARS]=d;
			  }
			  tmpLegentMap[d.NAMES][d.YEARS]=d;
		  }
		  for(var i=0;i<legents.length;i++){
			  var leg=legents[i];
			  var legMap=legentMap[leg];
			  var tmpMap=tmpLegentMap[leg];
			  for(var j=0;j<years.length;j++){
				  var year=years[j];
				  if(tmpMap[year]!=null){
					  legMap.data.push(tmpMap[year].COUNTS);
				  }else{
					  legMap.data.push(0);
				  }
			  }
		  }
	  } 
			var myChart = echarts.init(document.getElementById('test_4'),'blue');
			option = {
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:result,
		        selected:selectResult
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            saveAsImage : {show: true}
		        }
		    },
		  dataZoom: {
					        show: true,
					        start : 20,
					        end : 80
					    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            //boundaryGap : false,
		            data : years
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '论文数量'
		        }
		    ],
		    series :legentData
		};
			myChart.setOption(option);
  }, 
  
  pilishThesisTrend : function(datas){
	  var startYear = datas.includeTrend[0].YEARS;
	  var endYear = datas.includeTrend[datas.includeTrend.length-1].YEARS;
	  var includeYears = [];
	  var includeNames = [];
	  if(datas.includeTrend.length > 0) {
		  for(var i = 0; i < datas.includeTrendNames.length; i++) {
			  includeNames.push(datas.includeTrendNames[i].PERIODICAL);
		  }
		  
		  
		  var hash = {},
		     len = includeNames.length,
		     result = [];
		 for (var i = 0; i < len; i++){
		     if (!hash[includeNames[i]]){
		         hash[includeNames[i]] = true;
		         result.push(includeNames[i]);
		     } 
		 }
		 var selectResult = {};
			if(result.length>5){
				for(var i=5;i<result.length;i++){
					selectResult[result[i]]=false;
				}
		}
		  
		  for(var i = startYear; i <= endYear; i++) {
			  if(startYear <= endYear) {
				  includeYears.push(Number(startYear));
			  }
			  startYear++;
		  }
		  
		  var legents=[];
		  var years=[];
		  var legentMap={};
		  var legentData=[];//series
		  var tmpLegentMap={}; //legent到year临时数据
		  var yearMap={}; //year临时数据
		  
		  for(var i=0;i<datas.includeTrend.length;i++){
			  var d=datas.includeTrend[i];
			  if(legentMap[d.PERIODICAL]==null){
				  legents.push(d.PERIODICAL);
				  legentMap[d.PERIODICAL]={name:d.PERIODICAL,type:'line',data:[]};
				  tmpLegentMap[d.PERIODICAL]={};
				  legentData.push(legentMap[d.PERIODICAL]);
			  }
			  if(yearMap[d.YEARS]==null){
				  years.push(d.YEARS);
				  yearMap[d.YEARS]=d;
			  }
			  tmpLegentMap[d.PERIODICAL][d.YEARS]=d;
		  }
		  for(var i=0;i<legents.length;i++){
			  var leg=legents[i];
			  var legMap=legentMap[leg];
			  var tmpMap=tmpLegentMap[leg];
			  for(var j=0;j<years.length;j++){
				  var year=years[j];
				  if(tmpMap[year]!=null){
					  legMap.data.push(tmpMap[year].COUNTS);
				  }else{
					  legMap.data.push(0);
				  }
			  }
		  }
	  }
	  
	  var myChart = echarts.init(document.getElementById('test_4'),'blue');
	  option = {
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:result,
			        selected:selectResult
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            saveAsImage : {show: true}
			        }
			    },
			  dataZoom: {
						        show: true,
						        start : 20,
						        end : 80
						    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'category',
			            //boundaryGap : false,
			            data : includeYears
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            name : '论文数量'
			        }
			    ],
			    series :legentData
			};
	  myChart.setOption(option);
  },
  
  includeThesisTrend : function(datas) {
	  var includeYears = [];
	  var includeNames = [];
	  
	  if(datas.thesisTrend.length > 0) {
		  var startYear = datas.thesisTrend[0].YEARS;
		  var endYear = datas.thesisTrend[datas.thesisTrend.length-1].YEARS;
		  for(var i = 0; i < datas.thesisTrend.length; i++) {
			  includeNames.push(datas.thesisTrend[i].NAMES);
		  }
		  var hash = {},
		     len = includeNames.length,
		     result = [];
		 for (var i = 0; i < len; i++){
		     if (!hash[includeNames[i]]){
		         hash[includeNames[i]] = true;
		         result.push(includeNames[i]);
		     } 
		 }
		 var selectResult = {};
			if(result.length>5){
				for(var i=5;i<result.length;i++){
					selectResult[result[i]]=false;
				}
		}
		  
		  for(var i = startYear; i <= endYear; i++) {
			  if(startYear <= endYear) {
				  includeYears.push(Number(startYear));
			  }
			  startYear++;
		  }
		  var legents=[];
		  var years=[];
		  var legentMap={};
		  var legentData=[];//series
		  var tmpLegentMap={}; //legent到year临时数据
		  var yearMap={}; //year临时数据
		  
		  for(var i=0;i<datas.thesisTrend.length;i++){
			  var d=datas.thesisTrend[i];
			  if(legentMap[d.NAMES]==null){
				  legents.push(d.NAMES);
				  legentMap[d.NAMES]={name:d.NAMES,type:'line',data:[]};
				  tmpLegentMap[d.NAMES]={};
				  legentData.push(legentMap[d.NAMES]);
			  }
			  if(yearMap[d.YEARS]==null){
				  years.push(d.YEARS);
				  yearMap[d.YEARS]=d;
			  }
			  tmpLegentMap[d.NAMES][d.YEARS]=d;
		  }
		  for(var i=0;i<legents.length;i++){
			  var leg=legents[i];
			  var legMap=legentMap[leg];
			  var tmpMap=tmpLegentMap[leg];
			  for(var j=0;j<years.length;j++){
				  var year=years[j];
				  if(tmpMap[year]!=null){
					  legMap.data.push(tmpMap[year].COUNTS);
				  }else{
					  legMap.data.push(0);
				  }
			  }
		  }
	  } 
	  
	  var myChart = echarts.init(document.getElementById('test_4'),'blue');
		option = {
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:result,
	        selected:selectResult
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            saveAsImage : {show: true}
	        }
	    },
	  dataZoom: {
				        show: true,
				        start : 20,
				        end : 80
				    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            //boundaryGap : false,
	            data : includeYears
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            name : '论文数量'
	        }
	    ],
	    series :legentData
	};
		myChart.setOption(option);
  },
  
  dealBackToUp : function() {
	  var me = this;
	  $("#back_test_2").click(function(){
		  var typeCode = '';
		  $("#years_conponent").show();
		  $("#test_2").show();
		  $("#test_3").hide();
		  if($("#test_4").show()) {
			  $("#test_4").hide();
		  }
		  if($("#culture_code").length > 0) {
    		  typeCode = '110-180';
    	  }
    	  else if($("#social_code").length > 0) {
    		  typeCode = '710-910';
    	  } else if(($("#social_code").length == 0) && ($("#culture_code").length == 0)) {
    		  typeCode = '110-180';
    	  }
		  var typeCulture = {'codeType':typeCode};
		  me.callService([{key:'awardRatesAll',params:typeCulture},{key:'thesisTrend',params:typeCulture},'queryAllAwardCounts','queryAllIncludeCounts'], function(datas){
				 if((datas.awardRatesAll.length == 0) || (datas.thesisTrend.length == 0)) {
					 $("#look_before_thesis_showhide").hide();
				 } else {
					 $("#look_before_thesis_showhide").show();
				 }
		  });
	  });
  },
  
  dealFreshShowHide : function() {
	  $("#test_2").show();
	  $("#test_3").hide();
	  $("#test_4").hide();
  },
  
  yearController : function() {
	  var me = this;
	  var states = Ext.create('Ext.data.Store', {
          fields: ['id', 'mc'],
          data : function(){
          	var list = [];
          	var myDate = new Date();
          	var year = myDate.getFullYear();
          	for(var i=0;i<20;i++){
          		var y = {id:year-i,mc:year-i};
          		list.push(y);
          	}
          	return list;
          }()
      });
      var combobox = this.combobox = new Ext.form.ComboBox({
          width:200,
          labelWidth:60,
          store: states,
          fieldLabel:'选择年份',
          queryMode: 'local',
          id:'years_conponent',
          displayField: 'mc',
          margin:'10 0 10 0',
          columnWidth:0.5,
          valueField: 'id'
      });
      var yearContainer = new NS.container.Container({
          layout:"column",
          margin:'5 0 0 10',
          items:[this.combobox]
      });
      var myDate = new Date();
      var year = myDate.getFullYear();  
      combobox.setValue(year);
      combobox.on('change',function(){
    	  $(".curve_bar_ranks").html(this.pagesLoading);
    	  var newYear = combobox.getValue();
    	  var typeCode = '';
    	  if($("#culture_code").length > 0) {
    		  typeCode = '110-180';
    	  }
    	  else if($("#social_code").length > 0) {
    		  typeCode = '710-910';
    	  } else if(($("#social_code").length == 0) && ($("#culture_code").length == 0)) {
    		  typeCode = '110-180';
    	  }
    	  var typeCulture = {codeType:typeCode};
		  var chooseYear = {years:newYear+''};
		  var coditionsObj = {'typeCulture' : typeCulture, 'chooseYear':chooseYear};
		  me.deptThesis(coditionsObj);
      },this);
      return yearContainer;
  },
  
  clickThesisBefore : function() {
	  var me = this;
	  $("#look_before_thesises").click(function(){
		  $(".curve_bar_ranks").html(this.pagesLoading);
		  $("#show_or_hide_rates").toggle();
		  var typeCode = '';
    	  if($("#culture_code").length > 0) {
    		  typeCode = '110-180';
    	  }
    	  else if($("#social_code").length > 0) {
    		  typeCode = '710-910';
    	  } else if(($("#social_code").length == 0) && ($("#culture_code").length == 0)) {
    		  typeCode = '110-180';
    	  }
    	  var conditionCodeType = {'codeType':typeCode};
		  me.callService([{key:'awardRatesAll',params:conditionCodeType},{key:'thesisTrend',params:conditionCodeType},{key:'kindOfYearsDeptsCounts',params:conditionCodeType}], function(datas){
			  me.awardThesisRatesAll(datas);
			  me.includeRatesAll(datas);
		  });
	  });
  }, 
  awardThesisRatesAll : function(datas) {
	  var dataNames = [];
	  var awardObj = [];
	  if(datas.awardRatesAll.length > 0) {
		  for(var i = 0; i < datas.awardRatesAll.length; i++) {
			  dataNames.push(datas.awardRatesAll[i].NAMES);
		  }
		  
		  var hash = {},
		     len = dataNames.length,
		     result = [];
		 for (var i = 0; i < len; i++){
		     if (!hash[dataNames[i]]){
		         hash[dataNames[i]] = true;
		         result.push(dataNames[i]);
		     } 
		 }
		 var selectResult = {};
			if(result.length>5){
				for(var i=5;i<result.length;i++){
					selectResult[result[i]]=false;
				}
		}
		  for(var i =0; i < datas.kindOfYearsDeptsCounts.length; i++){
			  for(var j=0;j< datas.awardRatesAll.length;j++) {
				  if((datas.kindOfYearsDeptsCounts[i].YEARS == datas.awardRatesAll[j].YEARS) && (datas.kindOfYearsDeptsCounts[i].NAMES == datas.awardRatesAll[j].NAMES)){
					  awardObj.push({'NAMES':datas.awardRatesAll[j].NAMES,'COUNTS':(Number(datas.awardRatesAll[j].COUNTS)/Number(datas.kindOfYearsDeptsCounts[i].COUNTS)).toFixed(3),'YEARS':datas.awardRatesAll[j].YEARS});
				  }
			  }
		  }
		  var startYear = datas.awardRatesAll[0].YEARS;
		  var endYear = datas.awardRatesAll[datas.awardRatesAll.length - 1].YEARS;
		  var yearses = [];
		  for(var i = startYear; i <= endYear; i++) {
			  if(startYear <= endYear) {
				  yearses.push(Number(startYear));
			  }
			  startYear++;
		  }
		  var legents=[];
		  var years=[];
		  var legentMap={};
		  var legentData=[];//series
		  var tmpLegentMap={}; //legent到year临时数据
		  var yearMap={}; //year临时数据
		  
		  for(var i=0;i<awardObj.length;i++){
			  var d=awardObj[i];
			  if(legentMap[d.NAMES]==null){
				  legents.push(d.NAMES);
				  legentMap[d.NAMES]={name:d.NAMES,type:'line',data:[]};
				  tmpLegentMap[d.NAMES]={};
				  legentData.push(legentMap[d.NAMES]);
			  }
			  if(yearMap[d.YEARS]==null){
				  years.push(d.YEARS);
				  yearMap[d.YEARS]=d;
			  }
			  tmpLegentMap[d.NAMES][d.YEARS]=d;
		  }
		  for(var i=0;i<legents.length;i++){
			  var leg=legents[i];
			  var legMap=legentMap[leg];
			  var tmpMap=tmpLegentMap[leg];
			  for(var j=0;j<years.length;j++){
				  var year=years[j];
				  if(tmpMap[year]!=null){
					  legMap.data.push(tmpMap[year].COUNTS);
				  }else{
					  legMap.data.push(0);
				  }
			  }
		  }
	  } 
			var myChart = echarts.init(document.getElementById('award_rate_all'),'blue');
			option = {
		    tooltip : {
		        trigger: 'axis'
		    },
		    legend: {
		        data:result,
		        selected : selectResult
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            saveAsImage : {show: true}
		        }
		    },
		    grid:{
				y: 80
			},
		  dataZoom: {
					        show: true,
					        start : 20,
					        end : 80
					    },
		    calculable : true,
		    xAxis : [
		        {
		            type : 'category',
		            //boundaryGap : false,	//X轴从0开始
		            data : yearses
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            name : '获奖率'
		        }
		    ],
		    series :legentData,
		    noDataLoadingOption : {
	    	  	text : '论文获奖占比暂无数据',
	    	    effectOption : null,
	    	    effect : 'bubble',
	    	    textStyle : {fontSize : 20},
	    	    effectOption : {
	    	    	effect : {n:'0'}
	    	    }
	    }
		};
			myChart.setOption(option);
	  
  },
  includeRatesAll : function(datas) {
	  var includeYears = [];
	  var includeNames = [];
	  var includeObj = [];
	  
	  if(datas.thesisTrend.length > 0) {
		  var startYear = datas.thesisTrend[0].YEARS;
		  var endYear = datas.thesisTrend[datas.thesisTrend.length-1].YEARS;
		  for(var i = 0; i < datas.thesisTrend.length; i++) {
			  includeNames.push(datas.thesisTrend[i].NAMES);
		  }
		  
		  var hash = {},
		     len = includeNames.length,
		     result = [];
		 for (var i = 0; i < len; i++){
		     if (!hash[includeNames[i]]){
		         hash[includeNames[i]] = true;
		         result.push(includeNames[i]);
		     } 
		 }
		 var selectResult = {};
			if(result.length>5){
				for(var i=5;i<result.length;i++){
					selectResult[result[i]]=false;
				}
		}
		  
		  for(var i = startYear; i <= endYear; i++) {
			  if(startYear <= endYear) {
				  includeYears.push(Number(startYear));
			  }
			  startYear++;
		  }
		  
		  for(var i =0; i < datas.kindOfYearsDeptsCounts.length; i++){
			  for(var j=0;j< datas.thesisTrend.length;j++) {
				  if((datas.kindOfYearsDeptsCounts[i].YEARS == datas.thesisTrend[j].YEARS) && (datas.kindOfYearsDeptsCounts[i].NAMES == datas.thesisTrend[j].NAMES)){
					  includeObj.push({'NAMES':datas.thesisTrend[j].NAMES,'COUNTS':(Number(datas.thesisTrend[j].COUNTS)/Number(datas.kindOfYearsDeptsCounts[i].COUNTS)).toFixed(3),'YEARS':datas.thesisTrend[j].YEARS});
				  }
			  }
		  }
		  var legents=[];
		  var years=[];
		  var legentMap={};
		  var legentData=[];//series
		  var tmpLegentMap={}; //legent到year临时数据
		  var yearMap={}; //year临时数据
		  
		  for(var i=0;i<includeObj.length;i++){
			  var d=includeObj[i];
			  if(legentMap[d.NAMES]==null){
				  legents.push(d.NAMES);
				  legentMap[d.NAMES]={name:d.NAMES,type:'line',data:[]};
				  tmpLegentMap[d.NAMES]={};
				  legentData.push(legentMap[d.NAMES]);
			  }
			  if(yearMap[d.YEARS]==null){
				  years.push(d.YEARS);
				  yearMap[d.YEARS]=d;
			  }
			  tmpLegentMap[d.NAMES][d.YEARS]=d;
		  }
		  for(var i=0;i<legents.length;i++){
			  var leg=legents[i];
			  var legMap=legentMap[leg];
			  var tmpMap=tmpLegentMap[leg];
			  for(var j=0;j<years.length;j++){
				  var year=years[j];
				  if(tmpMap[year]!=null){
					  legMap.data.push(tmpMap[year].COUNTS);
				  }else{
					  legMap.data.push(0);
				  }
			  }
		  }
	  } 
	  
	  var myChart = echarts.init(document.getElementById('include_rate_all'),'blue');
		option = {
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:result,
	        selected : selectResult
	    },
	    grid:{
			y: 80
		},
	    toolbox: {
	        show : true,
	        feature : {
	            saveAsImage : {show: true}
	        }
	    },
	  dataZoom: {
				        show: true,
				        start : 20,
				        end : 80,
				    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            //boundaryGap : false,
	            data : includeYears
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            name : '收录率'
	        }
	    ],
	    series :legentData,
	    noDataLoadingOption : {
    	  	text : '论文收录占比暂无数据',
    	    effectOption : null,
    	    effect : 'bubble',
    	    textStyle : {fontSize : 20},
    	    effectOption : {
    	    	effect : {n:'0'}
    	    }
    }
	};
		myChart.setOption(option);
  },
  
  cPIncludeWinPage : function(queryConditions) {
	  var me = this;
	  var params = queryConditions;
	  var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
	  title="期刊论文";
	  gridFields="AUTHOR,DEPTNAME,NJQY,PERIODICAL,TITLE,YEARS".split(",");
	  textarrays="论文作者,院系名称,年卷期页,期刊类别,论文名称,发表年份".split(",");
	  widtharrays=[80,100,100,200,180,80];
	  hiddenarrays=[false,false,false,false,false,false];
	  this.callService({key:'queryCPThesisLimitPage',params:params},function(datas){
		  var data = datas.queryCPThesisLimitPage;
          var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
          var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'queryCPThesisLimitPage',false,true,15);
          var exportBtn1 = new NS.button.Button({
              text : "导出论文详情",
              handler : function(){
                  me.cPIncludeExport(params);
              },
              iconCls : "page-excel",
              border:true
          });
          var thesisNames = new NS.form.field.Text({
              width:240,
              fieldLabel:'论文名称',
              labelWidth:80,
              emptyText:'输入姓名检索'
          });
          var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({whereNames:thesisNames.getValue()});
					}
			};
          // 未填写教学日志导出
          var tbar = new NS.toolbar.Toolbar({
              items:[thesisNames,butt,'->',exportBtn1]
          });
		  this.win = new NS.window.Window({
              title:title+'详情',
              layout:'fit',
              modal:true,
              width:780,
              height:480,
              tbar:tbar,
              items:dxgrid
          });
          this.win.show();
	  });
  },
  
  cPIncludeExport : function(queryGridParams){
      // 导出
      var serviceAndParams ={
          servicAndMethod:'ranksNums?exportExcelCPInclude',
          params:queryGridParams
      } ;
      var strParams = JSON.stringify(serviceAndParams);
      window.document.open("customExportAction.action?serviceAndParams="+strParams,
          '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
  },
  
  iSIncludeWinPage : function(queryConditions) {
	  var me = this;
	  var params = queryConditions;
	  var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
	  title="期刊论文";
	  gridFields="AUTHOR,DEPTNAME,NJQY,PERIODICAL,TITLE,YEARS".split(",");
	  textarrays="论文作者,院系名称,年卷期页,期刊类别,论文名称,发表年份".split(",");
	  widtharrays=[80,100,100,200,180,80];
	  hiddenarrays=[false,false,false,false,false,false];
	  this.callService({key:'queryISThesisLimitPage',params:params},function(datas){
		  var data = datas.queryISThesisLimitPage;
          var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
          var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'queryISThesisLimitPage',false,true,15);
          var exportBtn1 = new NS.button.Button({
              text : "导出论文详情",
              handler : function(){
                  me.iSIncludeExport(params);
              },
              iconCls : "page-excel",
              border:true
          });
          var thesisNames = new NS.form.field.Text({
              width:240,
              fieldLabel:'论文名称',
              labelWidth:80,
              emptyText:'输入姓名检索'
          });
          var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({whereNames:thesisNames.getValue()});
					}
			};
          // 未填写教学日志导出
          var tbar = new NS.toolbar.Toolbar({
              items:[thesisNames,butt,'->',exportBtn1]
          });
		  this.win = new NS.window.Window({
              title:title+'详情',
              layout:'fit',
              modal:true,
              width:780,
              height:480,
              tbar:tbar,
              items:dxgrid
          });
          this.win.show();
	  });
  },
  
  iSIncludeExport : function(queryGridParams){
      // 导出
      var serviceAndParams ={
          servicAndMethod:'ranksNums?exportExcelISInclude',
          params:queryGridParams
      } ;
      var strParams = JSON.stringify(serviceAndParams);
      window.document.open("customExportAction.action?serviceAndParams="+strParams,
          '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
  },
  
  eIIncludeWinPage : function(queryConditions) {
	  var me = this;
	  var params = queryConditions;
	  var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
	  title="收录论文";
	  gridFields="AUTHOR,DEPTNAME,NJQY,PERIODICAL,TITLE,YEARS".split(",");
	  textarrays="论文作者,院系名称,年卷期页,期刊类别,论文名称,发表年份".split(",");
	  widtharrays=[80,100,100,200,180,80];
	  hiddenarrays=[false,false,false,false,false,false];
	  this.callService({key:'queryEIThesisLimitPage',params:params},function(datas){
		  var data = datas.queryEIThesisLimitPage;
          var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
          var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'queryEIThesisLimitPage',false,true,15);
          var exportBtn1 = new NS.button.Button({
              text : "导出论文详情",
              handler : function(){
                  me.eIIncludeExport(params);
              },
              iconCls : "page-excel",
              border:true
          });
          var thesisNames = new NS.form.field.Text({
              width:240,
              fieldLabel:'论文名称',
              labelWidth:80,
              emptyText:'输入姓名检索'
          });
          var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({whereNames:thesisNames.getValue()});
					}
			};
          // 未填写教学日志导出
          var tbar = new NS.toolbar.Toolbar({
              items:[thesisNames,butt,'->',exportBtn1]
          });
		  this.win = new NS.window.Window({
              title:title+'详情',
              layout:'fit',
              modal:true,
              width:780,
              height:480,
              tbar:tbar,
              items:dxgrid
          });
          this.win.show();
	  });
  },
  
  eIIncludeExport : function(queryGridParams){
      // 导出
      var serviceAndParams ={
          servicAndMethod:'ranksNums?exportExcelEIInclude',
          params:queryGridParams
      } ;
      var strParams = JSON.stringify(serviceAndParams);
      window.document.open("customExportAction.action?serviceAndParams="+strParams,
          '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
  },
  
  awardWinPage : function(queryConditions) {
	  var me = this;
	  var params = queryConditions;
	  var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
	  title="获奖论文";
	  gridFields="AUTHOR,DEPTNAME,AWARDNAME,NJQY,PERIODICAL,TITLE,YEARS".split(",");
	  textarrays="论文作者,院系名称,获奖名称,年卷期页,期刊类别,论文名称,发表年份".split(",");
	  widtharrays=[80,100,340,100,200,180,80];
	  hiddenarrays=[false,false,false,false,false,false,false];
	  this.callService({key:'queryAwardThesisLimitPage',params:params},function(datas){
		  var data = datas.queryAwardThesisLimitPage;
          var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
          var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'queryAwardThesisLimitPage',false,true,15);
          var exportBtn1 = new NS.button.Button({
              text : "导出论文详情",
              handler : function(){
                  me.awardExport(params);
              },
              iconCls : "page-excel",
              border:true
          });
          var thesisNames = new NS.form.field.Text({
              width:240,
              fieldLabel:'论文名称',
              labelWidth:80,
              emptyText:'输入姓名检索'
          });
          var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({whereNames:thesisNames.getValue()});
					}
			};
          // 未填写教学日志导出
          var tbar = new NS.toolbar.Toolbar({
              items:[thesisNames,butt,'->',exportBtn1]
          });
		  this.win = new NS.window.Window({
              title:title+'详情',
              layout:'fit',
              modal:true,
              width:780,
              height:480,
              tbar:tbar,
              items:dxgrid
          });
          this.win.show();
	  });
  },
  awardExport : function(queryGridParams){
      // 导出
      var serviceAndParams ={
          servicAndMethod:'ranksNums?exportExcelAward',
          params:queryGridParams
      } ;
      var strParams = JSON.stringify(serviceAndParams);
      window.document.open("customExportAction.action?serviceAndParams="+strParams,
          '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
  },
  
  winPage : function(queryConditions){
	  var me = this;
	  var params = queryConditions;
	  var gridFields=[],textarrays=[],widtharrays=[],hiddenarrays=[],title="";
	  title="期刊论文";
	  gridFields="AUTHOR,DEPTNAME,NJQY,PERIODICAL,TITLE,YEARS".split(",");
	  textarrays="论文作者,院系名称,年卷期页,期刊类别,论文名称,发表年份".split(",");
	  widtharrays=[80,100,100,200,180,80];
	  hiddenarrays=[false,false,false,false,false,false];
	  this.callService({key:'queryThesisLimitPage',params:params},function(datas){
		  var data = datas.queryThesisLimitPage;
          var columnCfgs = this.getColumnCfgsBzxmd(gridFields,textarrays,widtharrays,hiddenarrays);
          var dxgrid = this.dxgrid = this.initXqGrid(data,gridFields,columnCfgs,params,'queryThesisLimitPage',false,true,15);
          var exportBtn1 = new NS.button.Button({
              text : "导出论文详情",
              handler : function(){
                  me.exportMd(params);
              },
              iconCls : "page-excel",
              border:true
          });
          
          var thesisNames = new NS.form.field.Text({
              width:240,
              fieldLabel:'论文名称',
              labelWidth:80,
              emptyText:'输入姓名检索'
          });
          var butt = {
					xtype : "button",
					style : {
						marginLeft : '10px',
						marginRight : '20px'
					},
					text : "查询",
					handler : function() {
						 dxgrid.load({whereNames:thesisNames.getValue()});
					}
			};
          
          // 未填写教学日志导出
          var tbar = new NS.toolbar.Toolbar({
              items:[thesisNames,butt,'->',exportBtn1]
          });
		  this.win = new NS.window.Window({
              title:title+'详情',
              layout:'fit',
              modal:true,
              width:780,
              height:480,
              tbar:tbar,
              items:dxgrid
          });
          this.win.show();
	  });
  },
  initXqGrid : function(data,fields,columnConfig,queryParams,serviceKey,multiSelec,paging,pagesize){
      var lineNumber = Boolean(multiSelec)==true?false:true;
      var grid = new NS.grid.SimpleGrid({
          columnData : data,
          data:data,
          autoScroll: true,
          pageSize : pagesize||100,
          proxy : this.model,
          serviceKey:{
              key:serviceKey,
              params:queryParams
          },
          multiSelect: multiSelec||false,
          lineNumber:lineNumber,
          fields : fields,
          columnConfig :columnConfig,
          border: false,
          checked: multiSelec||false,
          paging:paging||false
      });
      return grid;
  },
  exportMd : function(queryGridParams){
      // 导出
      var serviceAndParams ={
          servicAndMethod:'ranksNums?exportExcelPilish',
          params:queryGridParams
      } ;
      var strParams = JSON.stringify(serviceAndParams);
      window.document.open("customExportAction.action?serviceAndParams="+strParams,
          '', 'width=500,height=300,menubar=yes,scrollbars=yes,resizable=yes');
  },
  getColumnCfgsBzxmd:function(gridFields,textarrays,widtharrays,hiddenarrays){
      var columns = [];
      for(var i=0;i<gridFields.length;i++){
          var basic = {
              xtype : 'column',
              name : gridFields[i],
              text : textarrays[i],
              width : widtharrays[i],
              hidden : hiddenarrays[i],
              align : 'center'
          };
          columns.push(basic);
      }
      return columns;
  }
  
 /* mainTable :  new Ext.Component({
		tpl : '<div style="width:500px;"><table class="table1"><thead>'+
				'<tr><th rowspan="2">论文发表期刊类别</th>'+
				'<th rowspan="2">论文数</th>'+
				'</tr>'+
			'</thead><tbody><tpl for="."><tr>'+
				'<td><a href="javascript:void(0)">{NAMES}</a></td>'+
			    '<td><a href="javascript:void(0)>{COUNTS}</a></td>'+
				'<td>{TEA_NO}</td>'+
				'<td>{TEA_NAME}</td>'+
				'<td>{DEPT_NAME}</td>'+
				'<td>{CGS}</td>'+
				'<td>{CGPS}</td>'+
				'<td><a href="javascript:void(0);" tea_no="{TEA_NO}" show_type="lws" >{LWS}</a></td>'+
				'<td>{LWPS}</td>'+
				'<td><a href="javascript:void(0);" tea_no="{TEA_NO}" show_type="xms" >{XMS}</a></td>'+
				'<td>{XMPS}</td>'+
				'<td><a href="javascript:void(0);" tea_no="{TEA_NO}" show_type="zls" >{ZLS}</a></td>'+
				'<td>{ZLPS}</td>'+
				'<td><a href="javascript:void(0);" tea_no="{TEA_NO}" show_type="zzs" >{ZZS}</a></td>'+
				'<td>{ZZPS}</td>'+
			'</tr></tpl>'+
			'</tbody></table></div><div id="main_table_fy"></div>',
		data : [],
		margin : '0px 10px 10px 0px'
	}),
	createTable : function() {
		var me = this;
		var container = new Ext.container.Container({
            items:[me.mainTable]
        });
		return container;
	}*/
   
  
  
  // 自定义多选下拉列表，关闭报错
  /*checkManySelect : function(){
	  var states = Ext.create('Ext.data.Store', {
          fields: ['id', 'mc'],
          data : function(){
          	var list = [];
          	var myDate = new Date();
          	var year = myDate.getFullYear();
          	for(var i=0;i<20;i++){
          		var y = {id:year-i,mc:year-i};
          		list.push(y);
          	}
          	return list;
          }()
      });
	  var basicBoxselect =  this.basicBoxselect= new Ext.ux.form.field.BoxSelect({
		  	fieldLabel: '测试多选下拉列表框',
	        displayField: 'mc',
	        width: 500,
	        labelWidth: 100,
	        store: states,
	        queryMode: 'local',
			emptyText: '点击选择',
			valueField: 'id'
	 });
	  basicBoxselect.setValue(new Date().getFullYear());
	  basicBoxselect.on('change',function(){
		  
	  });
	  var yearContainer = new NS.container.Container({
          layout:"column",
          margin:'5 0 0 10',
          items:[this.basicBoxselect]
      });
	  return yearContainer;
  },*/
  //多选下拉框，能全选
 /* testClickCheckAll : function() {
	  var me = this;
	  var states = Ext.create('Ext.data.Store', {
          fields: ['id', 'mc'],
          data : function(){
          	var list = [];
          	var myDate = new Date();
          	var year = myDate.getFullYear();
          	for(var i=0;i<20;i++){
          		var y = {id:year-i,mc:year-i};
          		list.push(y);
          	}
          	return list;
          }()
      });
	  var basicBoxselect =  new Ext.ux.CheckCombo({
		    fieldLabel: '测试多选下拉列表框',
			valueField: 'id',
			displayField: 'mc',
			width:180,
			allText:'全选',//默认字符是All
			store: states,
			emptyText: '点击选择',
			addAllSelector: true
		});
	  var yearContainer = new NS.container.Container({
          layout:"column",
          margin:'5 0 0 10',
          items:[basicBoxselect]
      });
	  basicBoxselect.on('change',function(){
		  var texxx = basicBoxselect.getValue();
		  me.testtest(texxx);
	  });
	  return yearContainer;
  },
  testtest : function(texxx) {
	  alert("我是测试程序"+texxx);
  }*/
});



