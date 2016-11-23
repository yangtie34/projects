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
					<h3>学生低消费</h3>
					<p>系统依据近三个月在餐厅就餐的学生消费明细，分析出长期处于低消费水平的学生名单。<br/>
							统计方式：<br/>
							第一步：分男、女计算近三个月学生吃早餐、午餐、晚餐的每日的平均消费值，并计算每位学生的当日、当次餐费是否为低消费<br/>
							第二步：利用均值与中位值确定“经常吃饭的”这个标准的阈值，例如3个月180顿饭，在餐厅吃86顿以上的学生即为经常吃饭的学生，86即为阈值。<br/>
							第三步：将经常在餐厅吃饭，并且总是处于低消费水平的学生计算出来。即低消费次数占消费次数的70%以上。</p>
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
				<script>
					function goMajor(deptId){
						loadTitle(deptId,true);
						yxEducationDistribute(deptId);
						educationNumsDistribute(deptId);			 
					}
					function selected(deptId){
						yxEducationDistribute(deptId);
						educationNumsDistribute(deptId);
					}
				</script>
					
			</div>
			<ul id="tab">
				<li class="current">低消费学生名单</li>
			</ul>
			<div id="content">
				<ul style="display: block;">
					<div class="diqu_bg">
						<table class="table" width="100%" border="0" cellpadding="0" cellspacing="0">
							<thead>
								<tr border="0">
									<th class="number_b">&nbsp;</th>
									<th>学号</th>
									<th>姓名</th>
									<th>院系</th>
									<th>专业</th>
									<th>性别</th>
									<th>年级</th>
									<th>吃饭次数</th>
									<th>低消费次数</th>
									<th>不达标率(%)</th>
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
		//组织机构滚动回调函数
		function goMajor(deptId) {
			loadTitle(deptId, true);
			lowConSumptionStu({
				startIndex : 1,
				numPerPage : 10,
				deptId:deptId
			});
		}

		function selected(deptId) {
			lowConSumptionStu({
				startIndex : 1,
				numPerPage : 10,
				deptId:deptId
			});
		}
		$(function() {
			var deptId = '${dept_id}';//当前的组织机构的id
			loadTitle(deptId, false);
			// 加载低消费学生名单
			lowConSumptionStu({
				startIndex : 1,
				numPerPage : 10
			}, deptId);
		});

		function lowConSumptionStu(pageParam, deptId) {
			var data_address = [ deptId, false, pageParam.currentPage || 1,pageParam.numPerPage || 1];
			$.callService("lowPayService", "getLowPayStu", data_address, function(d) {
				lowConSumptionStuCallback(d,deptId);
			});
		}
		function lowConSumptionStuCallback(d, deptId) {
			var domTable = $("tbody[bodyFlag='areaStat']")[0];
			if (domTable) {
				$(domTable).html('');
				$.each(d.resultList, function(i, o) {
					var str = "<tr><td>" + o.NUM + "</td>"
							+"<td class='huihui'>" + o.STU_ID + "</td>"  //学号
							+ "<td class='huihui'><a href=\"${ctx}/manager/lowPay/stu/deatil?stu_id="+o.STU_ID+"&stu_sex="+o.SEX_CODE+"\">" + o.STU_NAME + "</a></td>"//学生名字
							+ "<td class='huihui'>" + o.DEPT + "</td>"// 院系
							+ "<td class='huihui'>" + o.MAJOR + "</td>"//专业
							+ "<td class='huihui'>" + o.STU_SEX + "</td>"//性别
							+ "<td class='huihui'>" + o.ENROLL_GRADE + "</td>"//年级
							+ "<td class='huihui'>" + o.CFCS + "</td>"//吃饭次数
							+ "<td class='huihui'>" + o.BDBS + "</td>"//低消费次数
							+ "<td class='huihui'>" + o.BDBL + "%</td>"//不达标率
							+ "</tr>";
					$(domTable).append(str);
				});
				var page = new dmm.Page(d, function(pageParams){
					lowConSumptionStu(pageParams, deptId);
				}, window);
				page.render($("div[pageFlag='areaStat']")[0]);
			}
		}
	</script>
</body>
</html>
