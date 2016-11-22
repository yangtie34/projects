<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images" />
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>教师账号预警</title>
<link rel='stylesheet'
	href='${ctx}/static/framework/bootstrap-table-demo/bootstrap-table/bootstrap-table.css' />
<script
	src='${ctx}/static/framework/bootstrap-table-demo/bootstrap-table/bootstrap-table.js'></script>
<script
	src='${ctx}/static/framework/bootstrap-table-demo/bootstrap-table/bootstrap-table-export.js'></script>
<script
	src='${ctx}/static/framework/bootstrap-table-demo/extends/tableExport/jquery.base64.js'></script>
<script
	src='${ctx}/static/framework/bootstrap-table-demo/extends/tableExport/tableExport.js'></script>
</head>
<body ng-controller="netStuRankController">
	<div id="wrapper">
		<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
		<script type="text/javascript"
			src="${ctx }/product/net/js/netTeaWarn.js"></script>

		<div class="content">
			<div class="xscz-head content_head">
				<h3 class="xscz-fff">教师账号预警</h3>
				<p class="xscz-default">对学校教师上网账号异常情况进行预警。(注:异常系数越大代表账号的的异常情况越大;工作时间为周一到周五8:00-20:00;公共ip指教师和学生都有登陆的ip,非公共ip只有教师登陆。)</p>
			</div>
			<div class="content_main">
					<div class="booksjy_top_imges">

						<div class="clearfix"></div>
						<div cg-combo-nyrtj result="date" yid=1></div>
				<div cg-mul-query-comm source="mutiSource" result="deptResult"
									noborder="true"></div>

						<div class="clearfix"></div>
					</div>
					<!--------------------------------------------------- 以上为books-top-imges内容------------------------------------------------>
								<div class="t"
									style="
						    text-align: left;
						    padding-bottom: 0px;
						        border-bottom: 1px solid #7a8aa3;
						">
									<h4 >教师账号预警名单&nbsp;<a style="font-size: 12px; margin-left: 10px; position: absolute; margin-top: 10px;" href="" ng-click="qsDiv=true" class="xscz-default">查看分析模型</a></h4>
								</div>
								
				<div cg-report-table resource="tableData"
							class=" xscz-ft-18"></div>				
								
			</div>
			<div class="clearfix"></div>
		</div>
</body>
 <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'决策树模型'" >
 <div style="    height: 500px;
    width: 930px;
    overflow-x: auto;
    overflow-y: auto;">
<div style="margin-left: 10px;">
<span class="xscz-default">参数说明：</span><br>
<span class="xscz-default">WP：工作时间公共ip登陆次数;<br>OWP：非工作时间公共ip登陆次数;<br>WNP：工作时间内非公共ip登陆次数;<br>OWNP：非工作时间非公共ip登陆次数;</span><br>
<span class="xscz-default">1：异常;<br>2：正常;<br>3：登陆次数较少。</span><br><br>

<span class="xscz-default">验证规则：</span><br>
<span class="xscz-default">WNP<21.5 & WP>=33.5 ==>账号异常</span><br>
<span class="xscz-default">WNP>=21.5 & WP>=42==>账号异常</span><br>
<span class="xscz-default">WNP< 21.5 & WP< 33.5 & OWP>=17.5==>账号异常</span><br><br>
<span class="xscz-default">决策树模型：</span><br>
</div>
<span></span>
	<img alt="决策树模型" src="${ctx }/product/net/netTeaWarnFxModle.png">
	</div>
 </div>
 <div cg-combo-xz data="pageXq" type=''></div>
</html>