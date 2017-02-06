<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jhnu.syspermiss.util.SysConfig"%>
<%
	String root = request.getContextPath();
	SysConfig sys = SysConfig.instance();
%>
<div style="position: relative; overflow: hidden;width: 100%;height: 100%;">
	<div style="position: absolute;height: 130px;top: 0px;z-index: 10;">
		<nav class="xuegong-zhuti-nav">
			<div class="xuegong-zhuti-nav-user" >
		    	<img src="<%=sys.getServerUrl() %>/getphoto" class="img-circle center-block" width="72" height="72">
<!-- 		    	<img src="UserUtil.getPhotoByUserName()" class="img-circle center-block" width="72" height="72"> -->
		        <a href="http://localhost:8088/dmm_sys/user/changepwd/page?username=admin" class="pull-left">修改密码</a>
		        <a href="<%=sys.getCasServerUrl() %>/logout?service=<%=sys.getCasServerLoginUrl()%>?service=<%=sys.getCasServerUrl()%>/" class="pull-right">退出登录</a>
		        <div class="clearfix"></div>
		    </div>
		</nav>
	</div>
	<div style="position: absolute;padding-top:130px; top:0px;z-index: 9;overflow:hidden;height:100%; background: #272e41;"> 
		<nav class="xuegong-zhuti-nav" style="height:inherit;overflow:auto;overflow-x:hidden;">
		    <ul id="_main_menu_container">
				<li check-permiss shirotag="xg:stuEnroll:*"><a href="stuEnroll"><span class=""></span>在籍生基本概况</a></li>
				<li check-permiss shirotag="xg:worker:*"><a href="worker"><span class="nav-icon-02"></span>学生工作者基本概况</a></li>
				<li check-permiss shirotag="xg:stuFrom:*"><a href="stuFrom"><span class="nav-icon-03"></span>生源地分析</a></li>
				<li check-permiss shirotag="xg:newStu:*"><a href="newStu"><span class="nav-icon-04"></span>新生报到</a></li>
				<li check-permiss shirotag="xg:change:*"><a href="change"><span class="nav-icon-05"></span>学籍异动</a></li>
				<li check-permiss shirotag="xg:changeBad:*"><a href="changeBad"><span class="nav-icon-17"></span>学籍特殊异动</a></li>
				<li check-permiss shirotag="xg:punish:*"><a href="punish"><span class="nav-icon-11"></span>违纪处分</a></li>
				<li check-permiss shirotag="xg:scholarship:*"><a href="scholarship"><span class="nav-icon-07"></span>奖学金</a></li>
				<li check-permiss shirotag="xg:subsidy:*"><a href="subsidy"><span class="nav-icon-08"></span>助学金</a></li>
				<li check-permiss shirotag="xg:studentLoans:*"><a href="studentLoans"><span class="nav-icon-09"></span>助学贷款</a></li>
				<li check-permiss shirotag="xg:feeRemission:*"><a href="feeRemission"><span class="nav-icon-10"></span>学费减免</a></li>
				<li check-permiss shirotag="xg:stuWarning:*"><a href="stuWarning"><span class="nav-icon-12"></span>考勤预警</a></li>
			    <li check-permiss shirotag="xg:stuHighCost:*"><a href="stuHighCost"><span class="nav-icon-13"></span>高消费分析</a></li>
			    <li check-permiss shirotag="xg:stuLowCost:*"><a href="stuLowCost"><span class="nav-icon-14"></span>低消费分析</a></li>
			    <li check-permiss shirotag="xg:failExamPredict:*"><a href="failExamPredict"><span class="nav-icon-15"></span>不及格预测</a></li>
			    <li check-permiss shirotag="xg:notGradDegree:*"><a href="notGradDegree"><span class="nav-icon-16"></span>学位预警</a></li>
				<li check-permiss shirotag="xg:feeWarning:*"><a href="feeWarning"><span class="nav-icon-06"></span>欠费预警</a></li>
		    </ul>
		</nav>
	</div>
</div>
