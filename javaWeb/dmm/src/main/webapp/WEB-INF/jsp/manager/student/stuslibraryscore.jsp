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
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/map3.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/ring.js"></script>
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/stu_sex.js"></script>

<script type="text/javascript" src="${ctxStatic }/js/common/server.js"></script>

<!-- 引入图书借阅和成绩统计js -->
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/stus_library_score.js"></script>

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
				<script type="text/javascript" src="${ctxStatic }/js/manager/jquery-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
				<script type="text/javascript" src="${ctxStatic }/product/manager/dept.js"></script>
				<script>
					function goMajor(deptId){
						loadTitle(deptId,true);
						stusLibraryAndScore('2014-2015', '02', deptId, false);				 
					}
					function selected(deptId){
						stusLibraryAndScore('2014-2015', '02', deptId, true);
					}
				</script>
			</div>
			<!--专业及人数结束-->
			<div id="main8" style="width:1610px; height:600px;">
			</div>
		</div>
	</div>
	<script type="text/javascript">
	
		// 图书借阅与成绩统计
		function stusLibraryAndScore(school_year, term_code, dept_id, isLeaf) {
			var data = [school_year, term_code, dept_id, false];
			$.callService('scoreAndLibraryService', 'getStusScoreAndLibraryLog', data, function(d){
				var boyScoreAndLibrary = d[0].boyScoreAndLibrary;
				var girlScoreAndLibrary = d[0].girlScoreAndLibrary;
				libraryScore(boyScoreAndLibrary, girlScoreAndLibrary);
			});
		}
	
		$(function(){
			var deptId = '${dept_id}';
			loadTitle(deptId);
			stusLibraryAndScore('2014-2015', '02', deptId, false);
		});
	</script>
</body>
</html>
