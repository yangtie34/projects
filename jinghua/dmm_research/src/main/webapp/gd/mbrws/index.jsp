<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/gd/mbrws/index.jsp"/>
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>教学单位目标任务书</title>
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
<%-- 这是页面的整体架子 --%>
<div class="container-fluid">
	<div class="keyan-main"  ng-controller="tabController">
		<div class="header">
			<div class="main-tit">
				<h4 class="main-tit-b">教学单位目标任务书</h4> 
			</div>
			<div class="keyan-condition">
                <dl class="clearfix">
                	<dt>组织机构：</dt>
                    <dd>
                    	 <div cg-combo-tree source="queryCondition.dept" result="condition.khdw" ></div> 
                    </dd>
                </dl>
                <dl class="clearfix">
                	<dt>统计时间：</dt>
                    <dd>
						<div btn-dropdown source="queryCondition.khjhlist" on-change="changeKhjh($data)" btn-class="btn-default" display-field="jhmc"></div> 
                    </dd>
                </dl>
            </div>
		</div>
		<div style="margin:0 30px;">
			<!-- Nav tabs -->
			<ul class="nav nav-tabs p-top-10" role="tablist">
		  	  <li ng-repeat="it in khztlist" ng-class="{active : currentTab == it.id}"><a ng-href="#/{{it.id == '0'? it.id:'other/'+ it.id}}">{{it.ztmc}}</a></li>
			</ul>
		</div>
		<ng-view></ng-view>
	</div>
</div>
</body>
</html>