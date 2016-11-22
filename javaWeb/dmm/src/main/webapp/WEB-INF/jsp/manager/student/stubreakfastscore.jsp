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
<link rel="stylesheet" type="text/css" 　media="screen and (max-device-width: 400px)" 　href="tinyScreen.css" />
<link rel="stylesheet" type="text/css" 　media="screen and (min-width: 400px) and (max-device-width: 600px)" 　href="smallScreen.css" />

<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/leftnav.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/right.css" />
<link rel="stylesheet" type="text/css" href="${ctxStatic }/css/manager/xiala.css" />
<script src="${ctxStatic }/js/manager/jquery-1.3.2.js" type="text/javascript"></script>
<script src="${ctxStatic }/js/charts/echarts/dist/echarts.js" type="text/javascript"></script>

<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/echarts-all.js"></script>

<script type="text/javascript" src="${ctxStatic }/js/common/server.js"></script>

<!-- 引入早餐和成绩统计js -->
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/stu_bubble.js"></script>

<meta name="description" content="">
<meta name="author" content="">
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
					<h3>在校生成绩与早餐</h3>
					<p>全校考试成绩及学生早餐消费。</p>
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
				<script type="text/javascript" src="${ctxStatic }/js/manager/jquery-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/product/manager/dept.js"></script>
				<script>
					function goMajor(deptId){
						loadTitle(deptId,true);
						stusBftAndScore('2014-2015', '02', deptId, false);				 
					}
					function selected(deptId){
						stusBftAndScore('2014-2015', '02', deptId, true);
					}
				</script>
			</div>
			<!--专业及人数结束-->
			<div id="main7" style="width:1610px; height:600px; border:1px solid black">
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
		// 早餐和成绩统计
		function stusBftAndScore(school_year, term_code, dept_id, isLeaf) {
			var data = [school_year, term_code, dept_id, false];
			$.callService('scoreAndBreakfastService', 'getAllStusScoreAndBreakfastLog', data, function(d){
				var boyBreakfastAndScore = d[0].boyBreakfastAndScore;
				var girlBreakfastAndScore = d[0].girlBreakfastAndScore;
				bubles(boyBreakfastAndScore, girlBreakfastAndScore);
			});
		}
	
		$(function(){
			var dept_id = '${dept_id}';
			loadTitle(dept_id,false);
			stusBftAndScore('2014-2015', '02', dept_id, false);
		});
		
		
	</script>
</body>
</html>
