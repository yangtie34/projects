<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />
<jsp:include page="../topCssJs.jsp"></jsp:include>
<title>学生系统</title>
</head>
<body>
	<div class="content">
		<!--左侧导航开始-->
		<jsp:include page="../menu.jsp"></jsp:include>
		<!--左侧导航结束-->
		<!--右侧内容开始-->
		<div class="con_right">
			<!--右侧头部开始-->
			<div class="right_title">
				<div class="title_left">
					<h3>在校生基本概况统计</h3>
					<p>从学生性别、年龄、民族、政治面貌、学历、类别、学制、户口性质、科类、年级等多方面分析在校生分布组成情况</p>
				</div>
				<div class="title_right">
					<img class="face" src="${ctxStatic }/images/manager/face.png" alt="face" />
					<div class="styled-select">
						<select name="select" id="select">
							<option>张广生，艺术学院副院长</option>
							<option>修改密码</option>
							<option>注销</option>
							<option>退出</option>
						</select>
					</div>
				</div>
			</div>
			<!--右侧头部结束-->

			<!--专业及人数开始-->
			<div class="people" id="people">
				
			</div>
		
		<div id="timeDiv" style="margin-top: 30%">
			
		</div>
		<div id="mapDivChao" style="width: 30%;height: 400px;margin-top: 2%">
			
		</div>
		<div id="mapDivYu" style="width: 30%;height: 400px;margin-top: 2%">
			
		</div>
		<div id="mapDivCan" style="width: 30%;height: 400px;margin-top: 2%">
			
		</div>
		
		<table id="table_chao" border="1">
		</table>
		<table id="table_yu" border="1">
		</table>
		<table id="table_can" border="1">
		</table>

			<!-- 分布表开始-->
			<!--右侧内容结束-->
	</div>
	</div>
		<script>
		//组织机构滚动回调函数
		function goMajor(deptId){
			loadTitle(deptId,true);
			loadCardData(deptId,false,'210','2015-03-01','2015-03-31');
			loadCardData(deptId,false,'211','2015-03-01','2015-03-31');
			loadCardData(deptId,false,'215','2015-03-01','2015-03-31');
		}
		function selected(deptId){
			loadCardData(deptId,false,'210','2015-03-01','2015-03-31');
			loadCardData(deptId,false,'211','2015-03-01','2015-03-31');
			loadCardData(deptId,false,'215','2015-03-01','2015-03-31');
		}
		function loadCardData(deptId,flag,type_code,startDate,endDate){
			var data=[deptId,false,type_code,startDate,endDate];
			$.callService('payTypeService','getPayDetailLog',data,function (d){
				var address="";
				var tdId="";
				var rows=0;
				if(type_code == '210'){
					$("#table_can").html("");
				}
				if(type_code == '215'){
					$("#table_chao").html("");
				}
				if(type_code == '211'){
					$("#table_yu").html("");
				}
				$("#table_can").append("<tr><td>地点</td><td>窗口</td><td>上午</td><td>下午</td><td>晚上</td><tr>");
				$("#table_chao").append("<tr><td>地点</td><td>窗口</td><td>上午</td><td>下午</td><td>晚上</td><tr>");
				$("#table_yu").append("<tr><td>地点</td><td>窗口</td><td>上午</td><td>下午</td><td>晚上</td><tr>");
				$.each(d,function(i,o){
					var html="";
					var html2="<td>"+o.NAME_+"</td><td>"+o.ZAO+"</td><td>"+o.ZHONG+"</td><td>"+o.WAN+"</td>";
					if(address!=o.PNAME){
						address=o.PNAME;
						if(tdId!=""){
							$("#"+tdId).attr("rowspan",rows);
							rows=0;
						}
						tdId="can_td_"+i;
						var html1="<td id='"+tdId+"'>"+address+"</td>";
						html = "<tr>"+html1+html2+"</tr>";
					}else{
						html = "<tr>"+html2+"</tr>";
					}
					rows++;
					if(type_code == '210'){
						$("#table_can").append(html);
					}
					if(type_code == '215'){
						$("#table_chao").append(html);
					}
					if(type_code == '211'){
						$("#table_yu").append(html);
					}
				});
				if(tdId!=""){
					$("#"+tdId).attr("rowspan",rows);
					rows=0;
				}
			 });
			$.callService('payTypeService','getPayLog',data,function (d){
				var dataString=[];
				var dataArray=[];
				for(var i=0;i<d.length;i++){
					var o={};
					o.value=d[i].PAY_MONEY;
					if(d[i].XFJD==1){
						dataString.push('早餐');
						o.name='早餐';
					}else if(d[i].XFJD==2){
						dataString.push('午餐');
						o.name='午餐';
					}else{
						dataString.push('晚餐');
						o.name='晚餐';
					}
					dataArray.push(o);
				}
				if(type_code == '210'){
					$("#mapDivCan").html("");
				}
				if(type_code == '215'){
					$("#mapDivChao").html("");
				}
				if(type_code == '211'){
					$("#mapDivYu").html("");
				}
				var option = {
					    title : {
					        text: '餐厅消费情况',
					        x:'center'
					    },
					    tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c}元 ({d}%)"
					    },
					    legend: {
					        orient : 'vertical',
					        x : 'left',
					        data:dataString
					    },
					    toolbox: {
					        show : true,
					        feature : {
					            mark : {show: false},
					            dataView : {show: false, readOnly: false},
					            restore : {show: true},
					            saveAsImage : {show: true}
					        }
					    },
					    calculable : true,
					    series : [
					        {
					            name:'分时段消费金额',
					            type:'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data:dataArray
					        }
					    ]
					};
				if(type_code == '210'){
					var ageCharts = echarts.init(document.getElementById("mapDivCan"));
				}
				if(type_code == '215'){
					
					var ageCharts = echarts.init(document.getElementById("mapDivChao"));
				}
				if(type_code == '211'){
					
					var ageCharts = echarts.init(document.getElementById("mapDivYu"));
				}
				ageCharts.setOption(option);                  
			});
		}
		$(function() {
			var deptId = '${ids}';//当前的组织机构的id
			loadTitle(deptId,false);
			
			loadCardData(deptId,false,'210','2015-03-01','2015-03-31');
			loadCardData(deptId,false,'211','2015-03-01','2015-03-31');
			loadCardData(deptId,false,'215','2015-03-01','2015-03-31');
		});
	</script>
</body>
</html>
