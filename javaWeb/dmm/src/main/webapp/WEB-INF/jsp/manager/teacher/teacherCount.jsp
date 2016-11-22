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
<script type="text/javascript" src="${ctxStatic }/js/charts/echarts/dist/stu_sex.js"></script>

<script type="text/javascript" src="${ctxStatic }/js/common/server.js"></script>

<!-- 学生成绩统计  js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/student/zhuxingtuScore.js"></script>

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
							<option>张广生,艺术学院副院长</option>
							<option>修改密码</option>
							<option>注销</option>
							<option>退出</option>
						</select>
					</div>
				</div>
			</div>
			<!--右侧头部结束-->

			<!--专业及人数开始-->
			<script type="text/javascript">
				//=点击展开关闭效果=
				function openShutManager(oSourceObj, oTargetObj, shutAble, oOpenTip, oShutTip) {
					var sourceObj = typeof oSourceObj == "string" ? document.getElementById(oSourceObj) : oSourceObj;
					var targetObj = typeof oTargetObj == "string" ? document.getElementById(oTargetObj) : oTargetObj;
					var openTip = oOpenTip || "";
					var shutTip = oShutTip || "";
					if (targetObj.style.display != "none") {
						if (shutAble)
							return;
						targetObj.style.display = "none";
						if (openTip && shutTip) {
							sourceObj.innerHTML = shutTip;
						}
					} else {
						targetObj.style.display = "block";
						if (openTip && shutTip) {
							sourceObj.innerHTML = openTip;
						}
					}
				}
			</script>
			<div class="people">
				<div class="title_box">
					<h2 class="title">周口师范学院：艺术学院 - 美术类专业(人数)</h2>
					<a class="open" href="###" onClick="openShutManager(this,'box')"> <img src="${ctxStatic }/images/manager/xtxi_01.png" alt="点击打开" />
					</a>
				</div>
				<!--系别-->
				<div id="box">
					<script language="javascript" type="text/javascript" src="${ctxStatic }/js/manager/jquery-gd.js"></script>
					<script language="javascript" type="text/javascript" src="${ctxStatic }/js/manager/ss-gd.js"></script>
					<div class="mr_frbox">
						<img class="mr_frBtnL prev" src="${ctxStatic }/images/manager/zuo.png" />
						<div class="mr_frUl">

							<ul>
								<c:forEach var="dept" items="${depts }">
									<li><c:if test="${dept.dept_level eq '2' }">
											<a href="#"><img src="${ctxStatic }/images/manager/msx.png" /></a>
										</c:if> <c:if test="${dept.dept_level eq '1' }">
											<a href="${ctx }/manager/student/stu/${dept.dept_id} "> <img src="${ctxStatic }/images/manager/msx.png" />
											</a>
										</c:if> <a class="department_name" href="#"> ${dept.dept_name }<br /> <span class="people_all"> ${dept.nums } </span>
									</a></li>
								</c:forEach>
							</ul>

						</div>
						<img class="mr_frBtnR next" src="${ctxStatic }/images/manager/you.png" />
					</div>
					<script language="javascript">
						$(".mr_frUl ul li img").hover(function() {
							$(this).css("border-color", "#A0C0EB");
						}, function() {
							$(this).css("border-color", "#d8d8d8")
						});
						jQuery(".mr_frbox").slide({
							titCell : "",
							mainCell : ".mr_frUl ul",
							autoPage : true,
							effect : "leftLoop",
							autoPlay : true,
							vis : 4
						});
					</script>
				</div>

			</div>
			<!--专业及人数结束-->
			<div class="people">
				<div class="title_box">
					<h2 class="title">各院系成绩情况对比</h2>
				</div>
				<!--系别-->
				<div id="box">
					<div id="stu_score01" style="height: 320px; width: 1300px; border: 1px solid #ccc; padding: 10px;"></div>
				</div>
			</div>
			<div class="people">
				<div class="title_box">
					<h2 class="title">各院系成绩情况对比</h2>
				</div>
				<!--系别-->
				<div id="box" >
					<div id="stu_score02" style="height: 320px; width: 1300px; border: 1px solid #ccc; padding: 10px;"></div>
				</div>
			</div>
			<div id="Lsqs_Fb" class="people" style="display: none;">
				<div class="title_box">
					<h2 class="title">各院系成绩情况对比</h2>
				</div>
				<!--系别-->
				<div id="box" >
					<div id="stu_score03" style="height: 320px; width: 1300px; border: 1px solid #ccc; padding: 10px;"></div>
				</div>
			</div>
			
			<!--右侧内容结束-->
		</div>
		<script>
			//通过临时表获取各院系某学期的成绩统计  第一个图
			function stuScoreCounts(deptId,isLeaf,schoolYear,termCode) {
				//document.getElementById("Lsqs_Fb").style.display="none";
				var data = [deptId,isLeaf,schoolYear,termCode];
				$.callService('stuScoreService', 'getScoreLog', data, function(d) {
					//调用这个方法的时候先填充一个全部的数据 
					viewStuScore(d,"stu_score01");
					//再用表格填充第二个
					var htmlTable = "";
					var html1 = "<table width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"+
							"<thead><tr border=\"0\"><th>&nbsp;</th><th>部门</th><th>合格率</th><th>平均分"+
		                     "</th><th>最高分</th><th>极差</th><th>中位数</th><th>方差</th><th>标准差</th><th>优秀率</th><th>历史趋势</th><th>成绩分布</th></tr><thead><tbody>";
		            var html2 = "";
					var nums = 0;
					for(var i=0;i<d.length;i++){
						nums++;
						html2 = html2 + "<tr><td>"+nums+"</td><td id=\"bai_number\">"+ d[i].MC+"</td><td  class=\"huihui\">"+ d[i].HGL +"</td><td>"+ d[i].AVG+"</td><td>"+d[i].MAX+
		                     "</td><td>"+ d[i].JC+"</td><td>"+ d[i].ZWS+"</td><td>"+ d[i].FC+"</td><td>"+ d[i].BZC+"</td><td>"+ d[i].YXL +"</td><td>"+
		                     "<a href='javascript:void(0);' _id='"+d[i].ID+"' onclick='stuLsqs(this)'>历史趋势</a></td><td><a href='javascript:void(0);' _id='"+d[i].ID+"' onclick='stuCjfb(this)'>成绩分布</a></td></tr>";
					}
					var html3 = "</tbody></table>";
					var htmlTable = html1+html2+html3;
					$("#stu_score02").empty();
					$("#stu_score02").append(htmlTable);
				});
			}
			//从临时表获取某院系或某专业的历年成绩  第一个按钮（历史趋势  需要部门id）
			function stuLsqs(target) {
				document.getElementById("Lsqs_Fb").style.display="";
				var id = $(target).attr("_id");
				var data = [id];
				$.callService('stuScoreService', 'getTermScoreByDept', data, function(d) {
					stuScoreQu(d,"stu_score03");
				});
			}
			//获取成绩分布  第二个按钮（成绩分布）
			function stuCjfb(target) {
				document.getElementById("Lsqs_Fb").style.display="";
				var id = $(target).attr("_id");
				var schoolYear = "2012-2013";
				var termCode = "01";
				var data = [id,schoolYear,termCode];
				$.callService('stuScoreService', 'getScoreFb', data, function(d) {
					stuScoreFb(d,"stu_score03");
				});
			}
			function getOtherData(deptId) {
				//stuLsqs(deptId);
			}

			$(function() {
				var deptId = '${dept_id}';//当前的组织机构的id
				//getOtherData(deptId);
				var isLeaf = false;
				var schoolYear = "2012-2013";
				var termCode = "01";
				stuScoreCounts("0",isLeaf,schoolYear,termCode);//页面加载的时候自动填充一个table样式
			});
		</script>
</body>
</html>
