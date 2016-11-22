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
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no, minimum-scale=1.0, maximum-scale=1.0" />

<link rel="stylesheet" type="text/css" 　media="screen and (max-device-width: 400px)" 　href="tinyScreen.css" />
<link rel="stylesheet" type="text/css" 　media="screen and (min-width: 400px) and (max-device-width: 600px)" 　href="smallScreen.css" />

<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/right.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/xiala.css" />
<!--增加得css-->
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/zengjia.css">
<!--日期css-->
<link rel="stylesheet" href="${ctxStatic }/css/manager/jquery-ui-1.9.2.custom.css" type="text/css">
<script src="${ctxStatic }/js/manager/jquery-1.3.2.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/echarts-all.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/common/server.js"></script>
<!-- 引入教职工教学任务统计饼图js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/teacher/teachingTask_pie.js"></script>
<!-- 引入教职工教学任务统计折线图js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/teacher/teachingTask_line.js"></script>
<script type="text/javascript">
	$(function() {
		var deptId = '${deptId}';
		loadTeaTitle(deptId,false);
		var schoolYear = "2013-2014";
		var termCode = "01";
		// 各编制类别承担教学任务
		getBzlbJxrw(schoolYear, termCode, deptId);
		// 各职称承担教学任务
		getZcJxrw(schoolYear, termCode, deptId );
		//按职称类别分学院对比各类教师承担教学任务
		getXyZcJxrw(deptId, schoolYear, termCode);
		//按编制类别分学院对比各类教师承担教学任务
		getXyBzlbJxrw(deptId, schoolYear, termCode );
	});

	// 各编制类别承担教学任务 
	function getBzlbJxrw(schoolYear, termCode, deptId ) {
		var data = [ schoolYear, termCode, deptId ];
		$.callService('teachingTaskStatisticsService', 'getBzlbJxrw', data, function(d) {
			teachingTaskPieConfiguration("BzlbJxrw_main", "各编制类别承担教学任务", d);
		});
	}
	//  各职称承担教学任务
	function getZcJxrw(schoolYear, termCode, deptId ) {
		var data = [ schoolYear, termCode, deptId ];
		$.callService('teachingTaskStatisticsService', 'getZcJxrw', data, function(d) {
			teachingTaskPieConfiguration("ZcJxrw_main", "各职称承担教学任务", d);
		});
	}

	//  按职称类别分学院对比各类教师承担教学任务
	function getXyZcJxrw(deptId, schoolYear, termCode ) {
		var data = [ deptId, schoolYear, termCode ];
		$.callService('teachingTaskStatisticsService', 'getXyZcJxrw', data, function(d) {
			teachingTaskLineConfiguration("XyZcJxrw_main", "按院系对比各职称下的教师的教学任务量", d);
		});
	}

	// 按编制类别分学院对比各类教师承担教学任务
	function getXyBzlbJxrw(deptId, schoolYear, termCode ) {
		var data = [ deptId, schoolYear, termCode ];
		$.callService('teachingTaskStatisticsService', 'getXyBzlbJxrw', data, function(d) {
			teachingTaskLineConfiguration("XyBzlbJxrw_main", "按院系对比各编制类别下的教师的教学任务量", d);
		});
	}
</script>
<meta name="description" content="">
<meta name="author" content="">
<title>学生系统-学生生源地统计</title>
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
						<h3>学生生源地统计</h3>
						<p>学生来源地及毕业学校统计。展示来我校上学的学生的来源地分布情况统计。切换时间可以统计该时段内的来源地分布情况。分性别、毕业学历等不同角度展示学生的来源地分布。</p>
					</div>
					<div class="title_right">
						<img class="face" src="images/face.png" alt="face" />
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
					<div class="year">
						统计区间： <input type="button" class="blue_year" value="起" /> <input type="text" class="ui-datepicker-time" readonly value="03/18/2015" /> <input type="button" class="blue_year" value="止" /> <input type="text" class="ui-datepicker-time" readonly value="03/18/2015" />
					</div>
				</div>
			</div>
			<!--右侧头部结束-->

			<!--专业及人数开始-->
			<!--专业及人数开始-->
			<div class="people" id="people">
				<script language="javascript" type="text/javascript" src="${ctxStatic }/js/manager/jquery-gd.js"></script>
				<script language="javascript" type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/product/manager/teadept.js"></script>
				<script type="text/javascript">
					function selected(deptId){
						// 各编制类别承担教学任务
						getBzlbJxrw();
						// 各职称承担教学任务
						getZcJxrw();
						//按职称类别分学院对比各类教师承担教学任务
						getXyZcJxrw();
						//按编制类别分学院对比各类教师承担教学任务
						getXyBzlbJxrw();
					}
				</script>	
			</div>
			<!--专业及人数结束-->
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
				<div class="zhibiao_tu" style="width: 100%;">
					<div class="zhi_map" style="height: 300px; width: 50%;">
						<!-- 各编制类别承担教学任务 -->
						<div id="BzlbJxrw_main" style="height: 100%; width: 100%;"></div>
					</div>
					<div class="zhi_tj" style="height: 300px; width: 50%;">
						<!-- 各职称承担教学任务 -->
						<div id="ZcJxrw_main" style="height: 100%; width: 100%;"></div>
					</div>
				</div>
				<!-- 按职称类别分学院对比各类教师承担教学任务 -->
				<div class="zhibiao_tu" style="height: 400px; width: 100%;">
					<div id="XyBzlbJxrw_main" style="height: 100%; width: 100%;"></div>
				</div>
				<!-- 按编制类别分学院对比各类教师承担教学任务 -->
				<div class="zhibiao_tu" style="height: 400px; width: 100%;">
					<div id="XyZcJxrw_main" style="height: 100%; width: 100%;"></div>
				</div>
			</div>
		</div>
		<!--右侧内容结束-->
	</div>
</body>
</html>
