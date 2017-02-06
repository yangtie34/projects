<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<base href="<%=root%>/ky/award/detail/index.jsp"/>
<jsp:include page="../../../static/base.jsp"></jsp:include>
<title>高层次研究成果各项奖励</title>
<link href="../../css/keyan-style.css" rel="stylesheet">
<link href="../../css/cg-select-dropdown.css" rel="stylesheet">
<script type="text/javascript" src="js/controller.js"></script>
<script type="text/javascript" src="js/service.js"></script>
</head>
<body>
<%-- 这是页面的整体架子 --%>
<div class="container-fluid">
	<div class="keyan-main"  ng-controller="tabController">
		<div class="header">
			<div class="main-tit">
				<h4 class="main-tit-b">高层次研究成果各项奖励</h4>
				<h5 class="main-tit-s">为适应新形势下学校科研发展的需要，营造良好的研究创新环境，鼓励我校教职工开展高水平科学研究和教学研究，推动我校科学研究的发展水平和层次，特制定本奖励办法。</h5>
			</div>
		</div>
		<div>
			<div class="keyan-condition" style="margin-bottom:10px">
				<dl class="clearfix">
                	<dt>学科门类：</dt>
                    <dd>
                    	<form action="" class="ky-form condition-radio">
                            <div class="radio-box radio-inline" ng-repeat ="it in queryCondition.subject" >
								<input type="radio" ng-model="condition.subject" ng-value="it" id="{{it}}">
                                <label for="{{it}}"> {{it.mc}}
								</label>
                            </div>
                        </form>
                    </dd>
                </dl>
                <dl class="clearfix">
                	<dt>完成人单位：</dt>
                    <dd>
                    	 <div cg-combo-tree source="queryCondition.dept" result="condition.dept" ></div> 
                    </dd>
                </dl>
		         <dl class="clearfix">
		         	<dt>统计时间：</dt>
		             <dd>
		             	 <div btn-dropdown style="display: inline-block;" source="queryCondition.year" on-change="change($data)" btn-class="btn-default" display-field="mc">
		             </dd>
		         </dl>
		    </div>
			<div class="p-top-10 pull-right" style="margin-right:30px;">
				<button class="btn btn-default" ng-click="refreshPageData()">刷新数据</button>
			</div>
			<!-- Nav tabs -->
			<ul class="nav nav-tabs p-top-10" role="tablist" style="margin-left:30px;margin-right:30px;">
		  	  <li ng-class="{active : currentTab == 1}"><a href="#/xmlx">高层次项目立项奖</a></li>
			  <li ng-class="{active : currentTab == 2}"><a href="#/xmjx">高层次项目结项奖</a></li>
			  <li ng-class="{active : currentTab == 3}"><a href="#/yjcg">高层次研究成果奖</a></li>
			  <li ng-class="{active : currentTab == 4}"><a href="#/xslw">高层次学术论文奖</a></li>
			  <li ng-class="{active : currentTab == 5}"><a href="#/fmzl">发明专利奖</a></li>
			  <li ng-class="{active : currentTab == 6}"><a href="#/kyjf">科研经费奖</a></li>
			  <li ng-class="{active : currentTab == 7}"><a href="#/cgzh">科研成果转化奖</a></li>
			</ul>
		</div>
		<ng-view></ng-view>
	</div>
</div>
</body>
</html>