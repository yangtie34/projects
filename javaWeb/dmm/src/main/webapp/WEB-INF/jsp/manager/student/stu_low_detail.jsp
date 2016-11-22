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
<link rel="stylesheet" href="${ctxStatic }/css/manager/zengjia.css">
<script type="text/javascript" src="${ctxStatic }/product/common/page.js"></script>
<title>学生低消费详情</title>
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
						<h3>学生低消费</h3>
						<p>
							系统依据近三个月在餐厅就餐的学生消费明细，分析出长期处于低消费水平的学生名单。<br /> 统计方式：<br /> 第一步：分男、女计算近三个月学生吃早餐、午餐、晚餐的每日的平均消费值，并计算每位学生的当日、当次餐费是否为低消费<br /> 第二步：利用均值与中位值确定“经常吃饭的”这个标准的阈值，例如3个月180顿饭，在餐厅吃86顿以上的学生即为经常吃饭的学生，86即为阈值。<br /> 第三步：将经常在餐厅吃饭，并且总是处于低消费水平的学生计算出来。即低消费次数占消费次数的70%以上。
						</p>
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
					<div class="year" id="date_div"></div>
				</div>
			</div>
			<!--右侧头部结束-->
			<ul id="tab">
				<li class="current">低消费学生详情</li>
			</ul>
			<div id="stu_info" style="padding-left: 25px; height: 30px; font-size: 16px;"></div>
			<div id="content">
				<ul style="display: block;">
					<div class="diqu_bg">
						<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
							<thead>
								<tr border="0">
									<th class="number_b">&nbsp;</th>
									<th>消费日期</th>
									<th>餐别</th>
									<th>消费金额</th>
									<th>当日平均消费</th>
									<th>是否计入低消费</th>
								</tr>
							</thead>
							<tbody bodyFlag="areaStat">
							</tbody>
						</table>
						<div pageFlag="areaStat"></div>
					</div>
				</ul>
			</div>
		</div>

		<!-- 分布表开始-->
		<!--右侧内容结束-->
	</div>
	<script type="text/javascript">
		$(function() {
			var stuId = '${stu_id}';//当前的学生Id
			var stu_sex = '${stu_sex}';//当前的学生性别
			getStuInfo(stuId);
			var numDay = 90;
			var systemDate = new Date();
			var startDate = new Date(systemDate.getTime() - 24 * 60 * 60 * 1000 * (numDay - 1));
			var startDateStr = startDate.getFullYear() + "-" + (((startDate.getMonth() + 1) < 10) ? "0" + (startDate.getMonth() + 1) : (startDate.getMonth() + 1)) + "-" + ((startDate.getDate() < 10) ? "0" + startDate.getDate() : startDate.getDate());
			var endDateStr = systemDate.getFullYear() + "-" + (((systemDate.getMonth() + 1) < 10) ? "0" + (systemDate.getMonth() + 1) : (systemDate.getMonth() + 1)) + "-" + ((systemDate.getDate() < 10) ? "0" + systemDate.getDate() : systemDate.getDate());
			getCardLowDetail({
				startIndex : 1,
				numPerPage : 10
			}, stuId, stu_sex, startDateStr, endDateStr);
			$.addDate({
				pid : "date_div",
				startDate : startDateStr,
				endDate : endDateStr
			}, function(date) {
				var start = date.start.split("-");
				var end = date.end.split("-");
				var startDate = new Date(start[0], start[1], start[2]);
				var endDate = new Date(end[0], end[1], end[2]);
				if (((endDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24) <= 90) {
					getCardLowDetail({
						startIndex : 1,
						numPerPage : 10
					}, stuId, stu_sex, date.start, date.end);
				} else {
					alert("对不起，最多只能查询90天的数据， 请重新选择日起。");
					$("#end_date").val("");
				}
			});
		});

		function getStuInfo(stuId) {
			var data = [ stuId ];
			$.callService("stuAllInfoService", "getStuInfo", data, function(d) {
				if (d[0]) {
					var stuInfo = "<span>" + d[0].STU_ID + "， " + d[0].STU_NAME + "，" + (d[0].SEX_CODE == '1' ? '男' : '女') + "， " + d[0].CLASS_NAME + "</span>";
					$("#stu_info").html(stuInfo);
				}
			});
		}

		function getCardLowDetail(pageParam, stuId, stuSex, startDate, endDate) {
			var data_ = [ stuId, stuSex, startDate, endDate, pageParam.currentPage || 1, pageParam.numPerPage || 1 ];
			$.callService("lowPayService", "getLowPayDetailStu", data_, function(d) {
				getCardLowDetailCallback(d, stuId, stuSex, startDate, endDate);
			});

		}

		function getCardLowDetailCallback(d, stuId, stuSex, startDate, endDate) {
			var domTable = $("tbody[bodyFlag='areaStat']")[0];
			if (domTable) {
				$(domTable).html('');
				$.each(d.resultList, function(i, o) {
					console.info(o);
					var cbdm = "";
					if (o.CBDM == '1') {
						cbdm = "<font color='green'>早餐</font>";
					}
					if (o.CBDM == '2') {
						cbdm = "<font color='red'>中餐</font>";
					}
					if (o.CBDM == '3') {
						cbdm = "晚餐";
					}
					var str = "<tr><td>" + o.NUM + "</td>" + "<td class='huihui'>" + o.XFRQ + "</td>" //消费日期
							+ "<td class='huihui'>" + cbdm + "</td>"//餐别
							+ "<td class='huihui'>" + o.ZXFJE + "</td>"// 消费金额
							+ "<td class='huihui'>" + o.PJ2 + "</td>"//当日平均消费
							+ "<td class='huihui'>" + (o.SFGXF == '0' ? '<font color=red>是</font>' : '否') + "</td>"//是否计入低消费
							+ "</tr>";
					$(domTable).append(str);
				});
				var page = new dmm.Page(d, function(pageParams) {
					getCardLowDetail(pageParams, stuId, stuSex, startDate, endDate);
				}, window);
				page.render($("div[pageFlag='areaStat']")[0]);
			}
		}
	</script>
</body>
</html>
