<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<jsp:include page="../topCssJs.jsp"></jsp:include>
<!--增加得css-->
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/zengjia.css">
<!--日期css-->
<link rel="stylesheet" href="${ctxStatic }/css/manager/jquery-ui-1.9.2.custom.css" type="text/css">
<!-- 引入学生消费习惯分析js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/student/stu_consumption_habit.js"></script>
<script type="text/javascript">
	$(function() {
		var deptId = '${dept_id}';
		loadTitle(deptId,false);
		var numDay = 15;
		var systemDate = new Date();
		var startDate = new Date(systemDate.getTime() - 24 * 60 * 60 * 1000 * (numDay - 1));
		var startDateStr = startDate.getFullYear() + "-" + (((startDate.getMonth() + 1) < 10) ? "0" + (startDate.getMonth() + 1) : (startDate.getMonth() + 1)) + "-" + ((startDate.getDate() < 10) ? "0" + startDate.getDate() : startDate.getDate());
		var endDateStr = systemDate.getFullYear() + "-" + (((systemDate.getMonth() + 1) < 10) ? "0" + (systemDate.getMonth() + 1) : (systemDate.getMonth() + 1)) + "-" + ((systemDate.getDate() < 10) ? "0" + systemDate.getDate() : systemDate.getDate());
		searchData(deptId, startDateStr, endDateStr);
		$.addDate({pid:"date_div", startDate: startDateStr, endDate:endDateStr},function(date){
			var start = date.start.split("-");
			var end = date.end.split("-");
			var startDate = new Date(start[0], start[1], start[2]);
			var endDate = new Date(end[0], end[1], end[2]);
			if(((endDate.getTime() - startDate.getTime())/1000/60/60/24) <= 30){
				searchData(deptId, date.start, date.end);
			}else{
				alert("对不起，最多只能查询30天的数据， 请重新选择日起。");
				$("#end_date").val("");
			}
		});
		
	});

	function searchData(deptId, startDateStr, endDateStr) {
		//日生均消费笔数 
		getStuPays(deptId, false, startDateStr, endDateStr);
		// 学生平均三餐就餐次数组成
		getStuEatNumbers(deptId, false, startDateStr, endDateStr);
		// 学生消费能力区间分布图
		getStuPayMoney(deptId, false, startDateStr, endDateStr);
	}

	// 日生均消费笔数 
	function getStuPays(deptId, flag, startDateStr, endDateStr) {
		var data = [ deptId, flag, startDateStr, endDateStr ];
		$.callService('stuPayHabitService', 'getStuPaysLog', data, function(d) {
			conHabitLineConfiration({
				"startDateStr" : startDateStr,
				"endDateStr" : endDateStr,
				"echartsTitle" : "日生均消费笔数"
			}, d);
		});
	}

	// 学生平均三餐就餐次数组成
	function getStuEatNumbers(deptId, flag, startDateStr, endDateStr) {
		var data = [ deptId, flag, startDateStr, endDateStr ];
		$.callService('stuPayHabitService', 'getStuEatNumbersLog', data, function(d) {
			conHabitPieConfiration({
				"startDateStr" : startDateStr,
				"endDateStr" : endDateStr,
				"echartsTitle" : "学生平均三餐就餐次数组成"
			}, d);
		});
	}

	// 学生消费能力区间分布图
	function getStuPayMoney(deptId, flag, startDateStr, endDateStr) {
		var data = [ deptId, flag, startDateStr, endDateStr ];
		$.callService('stuPayHabitService', 'getStuPayMoneyLog', data, function(d) {
			conHabitBarConfiration({
				"startDateStr" : startDateStr,
				"endDateStr" : endDateStr,
				"echartsTitle" : "学生消费能力区间分布图"
			}, d);
		});
	}
</script>
<meta name="description" content="">
<meta name="author" content="">
<title>学生系统-学生消费习惯分析</title>
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
				<div class="boxx">
					<div class="title_left">
						<h3>学生消费习惯分析</h3>
						<p>学校消费统计从学生日生均消费、消费习惯、消费区间分布三个主题展示学生的消费信息。</p>
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

				<div class="yearbox">
					<div class="year" id="date_div">
					</div>
				</div>
			</div>
			<!--右侧头部结束-->

			<!--专业及人数开始-->
			<div class="people" id="people">
				<script>
					function goMajor(deptId) {
						loadTitle(deptId, true);
						var startDateStr = "2015-03-01";
						var endDateStr = "2015-03-31";
						getStuPays(deptId,false,startDateStr, endDateStr );
						// 学生平均三餐就餐次数组成
						getStuEatNumbers(deptId,false,startDateStr, endDateStr );
						// 学生消费能力区间分布图
						getStuPayMoney(deptId,false,startDateStr, endDateStr);
					}
					function selected(deptId) {
						var startDateStr = "2015-03-01";
						var endDateStr = "2015-03-31";
						getStuPays(deptId,true,startDateStr, endDateStr );
						// 学生平均三餐就餐次数组成
						getStuEatNumbers(deptId,true,startDateStr, endDateStr );
						// 学生消费能力区间分布图
						getStuPayMoney(deptId,true,startDateStr, endDateStr);
					}
				</script>
			</div>
			<!--生源选择指标开始-->
			<div class="zhibiao_box">
				<div class="zhi_title">
					<span>选择学年</span>
					<div class="choice">
						<select name="selecta" id="selecta">
							<option>来校人数</option>
							<option>助学金</option>
							<option>获得</option>
							<option>人数</option>
						</select>
					</div>
				</div>
				<!-- 折线图 -->
				<div class="zhibiao_tu" style="height: 550px; width: 100%;">
					<div id="conHabitLine_main" style="height: 100%; width: 100%;"></div>
				</div>
				<!-- 饼状图 -->
				<div class="zhibiao_tu" style="height: 400px; width: 100%;">
					<div id="conHabitPie_main" style="height: 100%; width: 100%;"></div>
				</div>
				<!-- 柱状图 -->
				<div class="zhibiao_tu" style="height: 400px; width: 100%;">
					<div id="conHabitBar_main" style="height: 100%; width: 100%;"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
