<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images" />
<html ng-app="app">
<head>
<%--   <base href="<%=basePath%>"> --%>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>餐厅窗口消费统计</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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

<body ng-controller="byPortController">
	<div id="wrapper">
		<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
		<script type="text/javascript" src="${ctx }/product/card/js/byPort.js"></script>
		<div class="content">
			<div class="xscz-head content_head">
				<h3 class="xscz-fff">餐厅窗口消费统计</h3>
				<p class="xscz-default">对学校各个业务窗口的消费情况进行统计分析。</p>
			</div>



			<!--  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	 -->

			<div class="xscz-box"
				style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
				<div class="xscz-tit">
					<span class="xscz-link-none"> 我校当前共有餐厅<span
						class="xscz-link xscz-active"> {{tjData.ct}}</span> 座。 包含窗口<span
						class="xscz-link xscz-active"> {{tjData.ck}} </span>个， 学生共在此消费<span
						class="xscz-link xscz-active"> {{tjData.bs}} </span>笔， 消费金额<span
						class="xscz-link xscz-active"> {{tjData.je|number:2}} </span>元。
					</span>
				</div>
				<div cg-combo-nyrtj result="date" yid=1></div>
				<div class="xscz-nav">
					<div class="xscz-restaurant">
						<div class="row clearfix text-center">
							<div class="col-xs-3" ng-repeat="item in queryTypeList">
								<a href="" class="xscz-plate "
									ng-class="queryTypeIndex==$index?'active':''"
									ng-click="queryTypeClick($index);">
									<div class="xscz-plate-top"></div>
									<div class="xscz-plate-down">
										<p>{{item.name}}</p>
									</div>
								</a>
							</div>
						</div>
					</div>
					<!-- /* 统计*/-->
					<div class="xscz-tj">
						<div class="xscz-cit text-center">
							<span ng-class="flagIndex==$index?'active':'normal'"
								ng-repeat="item in flagList"> <a href=""
								ng-click="flagClick($index);">{{item.name}}</a>
							</span>
						</div>
						<div cg-report-table resource="tableData[ctckCode]"
							class=" xscz-ft-18"></div>
					</div>
					<!-- /* 统计*/-->
					<div class="xscz-tj" ng-show="ydqsdiv">
						<div class="xscz-cit text-center">
							<span>{{ydqsName}}整体月度收入趋势</span>
						</div>
						<div class="row text-center">
							<div class="col-xs-12">
								<div stu-chart config="ydqs" style="height:310px;"
									class="img-responsive img-top"></div>
							</div>
						</div>
					</div>
					
					<!-- /* 统计*/-->
					<div class="xscz-tj xscz-ft-18">
						<div class="xscz-cit text-center">
							<span>窗口排名Top10</span>
						</div>
						<div class="row text-center pad-btm-40">
							<div class="col-xs-6">
								<h4 class="xscz-ft-16">人气窗口Top10</h4>
								<div>
									<table
										class="table table-hover text-center xscz-ft-14 num-org no-mar-btm">
										<thead>
											<tr>
												<td>&nbsp;</td>
												<td>窗口名称</td>
												<td>总消费(元)</td>
												<td>总消费次数</td>
												<td>日均消费(元)</td>
												<td>日均消费次数</td>
												<td>笔均消费金额(元)</td>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="item in ckrq" ng-show="trisshow($index,pagerq.currentPage,pagerq.numPerPage)">
												<td><span class="order-circle">{{item.RANK}}</span></td>
												<td>{{item.NAME}}</td>
												<td>{{item.ALL_MONEY|number:2}}</td>
												<td>{{item.ALL_COUNT}}</td>
												<td>{{item.DAY_MONEY}}</td>
												<td>{{item.DAY_COUNT}}</td>
												<td>{{item.ONE_MONEY}}</td>
											</tr>
										</tbody>
									</table>
      		<div class="quanxian-pager xscz-ft-14" ng-show="pagerq.numPerPage<pagerq.totalRows">
      		<span class="quanxian-pager-text-ccc pull-left">当前查询：共 {{rqys}} 页，数据 {{pagerq.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="pagerq.totalRows" ng-model="pagerq.currentPage" max-size="pagerq.numPerPage" items-per-page="pagerq.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
				<select ng-model="pagerq.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div> 

								</div>
							</div>
							<div class="col-xs-6">
								<h4 class="xscz-ft-16">土豪窗口Top10</h4>
								<div>
									<table
										class="table table-hover text-center xscz-ft-14 num-org no-mar-btm">
										<thead>
					<tr>
												<td>&nbsp;</td>
												<td>窗口名称</td>
												<td>总消费(元)</td>
												<td>总消费次数</td>
												<td>日均消费(元)</td>
												<td>日均消费次数</td>
												<td>笔均消费金额(元)</td>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="item in ckth" ng-show="trisshow($index,pageth.currentPage,pageth.numPerPage)">
												<td><span class="order-circle">{{item.RANK}}</span></td>
												<td>{{item.NAME}}</td>
												<td>{{item.ALL_MONEY|number:2}}</td>
												<td>{{item.ALL_COUNT}}</td>
												<td>{{item.DAY_MONEY}}</td>
												<td>{{item.DAY_COUNT}}</td>
												<td>{{item.ONE_MONEY}}</td>
											</tr>
										</tbody>
									</table>
      		<div class="quanxian-pager xscz-ft-14" ng-show="pageth.numPerPage<pageth.totalRows">
      		<span class="quanxian-pager-text-ccc pull-left">当前查询：共 {{thys}} 页，数据 {{pageth.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="pageth.totalRows" ng-model="pageth.currentPage" max-size="pageth.numPerPage" items-per-page="pageth.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
				<select ng-model="pageth.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div> 
								</div>
							</div>
						</div>
					</div>
					<!-- /* 统计*/-->

				</div>
			</div>
		</div>
	</div>
</body>
<div cs-window show="qsDiv" autoCenter="true" offset="offset"
	title="'趋势详情'">
	<div stu-chart config="qushiData" class="qsdivc"></div>
</div>
</html>
