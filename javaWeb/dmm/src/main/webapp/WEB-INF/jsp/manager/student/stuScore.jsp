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
<jsp:include page="../topCssJs.jsp"></jsp:include>
<link rel="stylesheet" href="${ctxStatic }/css/manager/zengjia.css">
<!-- 学生成绩统计  js -->
<script type="text/javascript" src="${ctxStatic }/product/manager/student/zhuxingtuScore.js"></script>
<meta name="description" content="">
<meta name="author" content="">
<title>学生成绩统计</title>
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
	                    <p>学生来源地及毕业学校统计。展示来我校上学的学生的来源地分布情况统计。切换时间可以统计该时段内的来源地分布情况。分性别、毕业学历等不同角度展示学生的来源地分布。 </p>
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
				<script type="text/javascript">
					function goMajor(deptId){
						loadTitle(deptId,true);
						var isLeaf = false;
						var schoolYear = "2012-2013";
						var termCode = "01";
						stuScoreCounts(deptId,isLeaf,schoolYear,termCode);//页面加载的时候自动填充一个table样式				 
					}
					function selected(deptId){
						var isLeaf = true;
						var schoolYear = "2012-2013";
						var termCode = "01";
						stuScoreCounts(deptId,isLeaf,schoolYear,termCode);
					}
				</script>	

			</div>
			<!--专业及人数结束-->
			<div class="people">
				<div class="title_box">
					<h2 class="title">各院系成绩情况对比</h2>
				</div>
				<!--系别-->
				<div id="box">
					<div class="diqu_bg" id="stu_score01" style="height: 320px; width: 100%; border: 1px solid #ccc; padding: 10px;"></div>
				</div>
			</div>
			
			<div class="people">
				<div class="title_box">
					<h2 class="title">各院系成绩情况对比</h2>
				</div>
				<!--系别-->
				<div id="box" >
					<div id="stu_score02" style="width: 100%; border: 1px solid #ccc; padding: 10px;"></div>
				</div>
			</div>
			<div id="Lsqs_Fb" class="people" style="display: none;">
				<div class="title_box">
					<h2 class="title">各院系成绩情况对比</h2>
				</div>
				<!--系别-->
				<div id="box" >
					<div id="stu_score03" style="height: 320px; width: 100%; border: 1px solid #ccc; padding: 10px;"></div>
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
					var html1 = "<table class='table' width=\"90%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">"+
							"<thead><tr border=\"0\"><th class='number_b'>&nbsp;</th><th>部门</th><th>合格率</th><th>平均分"+
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
			//TODO 这是获取时间的
			function stuDate(){
			}
			$(function() {
				
				
				var date_op={pid:"date_div"};
				$.addDate(date_op,function(date){
					alert(date.start+"------"+date.end);
				});
				
				var deptId = '${dept_id}';//当前的组织机构的id

				loadTitle(deptId,false);
				
				var isLeaf = false;
				var schoolYear = "2012-2013";
				var termCode = "01";
				stuScoreCounts(deptId,isLeaf,schoolYear,termCode);//页面加载的时候自动填充一个table样式
			});
		</script>
</body>
</html>
