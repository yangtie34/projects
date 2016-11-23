function loadAllMoney(stuId){
	$("#cardTotal").remove();
	data = [stuId];
	$.callService('stuAllInfoService','getCardLog',data,function(d){
		var html = "<div class='title-bottom-line' id='cardTotal' style='border:none;'> "+ d.STU_NAME +"（"+ d.STU_ID +"） </div>"+
		   " <p class='p-title-ping'> 入学以来消费累计金额:<span style=\"color: #ff3399\">"+d.TOTAL_MONEY+"</span>元,日均消费金额:<span style=\"color: #ff3399\">"+d.AVG_MONEY+"</span>元</p><br>"+
		   " <div class='title-bottom-line'>消费统计图"+
		   "   <select style='margin-left:50px; width:205px;' id='year' onchange='refushData("+ d.STU_ID +")'>"+
		   "    <option>2014-2015学年</option>"+
		   "     <option>2013-2014学年</option>"+
		   "    <option>2012-2013学年</option>"+
		   "  </select>"+
		   "   <select style='margin-left:50px; width:205px;' id='term' onchange='refushData("+ d.STU_ID +")'>"+
		   "     <option>第一学期 </option>"+
		   "    <option>第二学期</option>"+
		   "  </select>"+
		   " </div>";
		$("#xiaofei-box").prepend(html);
	});
}
function loadCard(stuId,school_year,term_code){
	$("#cardYear").empty();
	data = [stuId,school_year,term_code];
	$.callService('stuAllInfoService','getCardByYear',data,function(d){
		var mc = [];
		var money = [];
		$.each(d,function(i,o){
			mc.push(o.STU_TYPE);
			money.push(o.AVG_MONEY);
		});
		var myChart = echarts.init(document.getElementById('cardYear'));
		option = {
		    title : {
		        text: '人均消费情况对比统计图'
		    },
		    tooltip : {
		        trigger: 'axis'
		    },
		    xAxis : [
		        {
		            type : 'category',
		            data : mc
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value'
		        }
		    ],
		    series : [
		        {
		            type:'bar',
		            data:money
		            
		        }
		    ]
		};
		myChart.setOption(option);		
		
	});
	
}

function refushData(stuId){
	var year = $("#year  option:selected").text();
	var term = $("#term  option:selected").text();
	loadCard(stuId,year,term);
	
}