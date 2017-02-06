<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.jhnu.syspermiss.util.SysConfig"%>
<%
	String root = request.getContextPath();
	SysConfig sys = SysConfig.instance();
	String logoutUrl = sys.getCasServerUrl() +"/logout?service="+sys.getCasServerLoginUrl();
%>
<div style="position: relative; overflow: hidden;width: 100%;height: 100%;overflow:auto;background-color: #32323a;">
	<div style=" width: 100%;top: 0px;z-index: 10;">
		<div class="line_border">
	        <div class="face-line">
	        <div style="display:table-cell; width: 20px;">
	        <img src="<%=sys.getServerUrl() %>/getphoto" class="img-circle center-block" width="50" height="50">
	        </div>
	          <div class="face-name">{{loginInfo.loginUser}}</div>
	          <div class="face-windows-icon"><a class="face-windows-icon"></a>
	            <div class="face-windows-beside">
	              <ul class="face-windows">
	               <li> <a class="face-windows-edit" style="cursor:pointer" target="_blank" ng-href="{{loginInfo.changePasswdSysUrl}}/user/changepwd/page?username={{loginInfo.loginUser}}"> 修改密码 </a> </li> <!--  id="changePass" -->
	                <li> <a class="face-windows-exit" href="<%=logoutUrl %>">退出</a> </li>
	              </ul>
	            </div>
	          </div>
	          </div>
	    </div>
	</div>
	<div id="_main_menu_container" style=" z-index: 9;overflow:hidden; width:100%; background: #32323a;"> 
		<nav style="height:inherit;overflow:hidden;">
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="xg:groupStu:*">
				<div class="subNav">
					<em class="undefined">学生工作概况</em>
				</div>
				<ul class="navContent" style="margin: 0px;display: block;">
					<li check-permiss callback="callback()" shirotag="xg:stuEnroll:*"><a href="stuEnroll">在籍生基本概况</a></li>
					<li check-permiss callback="callback()" shirotag="xg:worker:*"><a href="worker">学生工作者基本概况</a></li>
					<li check-permiss callback="callback()" shirotag="xg:stuFrom:*"><a href="stuFrom">生源地分析</a></li>
					<li check-permiss callback="callback()" shirotag="xg:newStu:*"><a href="newStu">新生报到</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="xg:groupYdwjcf:*">
				<div class="subNav">
					<em class="undefined">异动违纪处分</em>
				</div>
				<ul class="navContent" style="margin: 0px;">
					<li check-permiss callback="callback()" shirotag="xg:change:*"><a href="change">学籍异动</a></li>
					<li check-permiss callback="callback()" shirotag="xg:changeBad:*"><a href="changeBad">学籍特殊异动</a></li>
					<li check-permiss callback="callback()" shirotag="xg:punish:*"><a href="punish">违纪处分</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="xg:groupGzdm:*">
				<div class="subNav">
					<em class="undefined">奖助贷免</em>
				</div>
				<ul class="navContent" style="margin: 0px;">
					<li check-permiss callback="callback()" shirotag="xg:scholarship:*"><a href="scholarship">奖学金</a></li>
					<li check-permiss callback="callback()" shirotag="xg:subsidy:*"><a href="subsidy">助学金</a></li>
					<li check-permiss callback="callback()" shirotag="xg:studentLoans:*"><a href="studentLoans">助学贷款</a></li>
					<li check-permiss callback="callback()" shirotag="xg:feeRemission:*"><a href="feeRemission">学费减免</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="xg:groupStuWarning:*">
				<div class="subNav">
					<em class="undefined">学生预警</em>
				</div>
				<ul class="navContent" style="margin: 0px;">
					<li check-permiss callback="callback()" shirotag="xg:stuWarning:*"><a href="stuWarning">失联预警</a></li>
					<li check-permiss callback="callback()" shirotag="xg:stuHighCost:*"><a href="stuHighCost">高消费分析</a></li>
					<li check-permiss callback="callback()" shirotag="xg:stuLowCost:*"><a href="stuLowCost">低消费分析</a></li>
					<li check-permiss callback="callback()" shirotag="xg:failExamPredict:*"><a href="failExamPredict">不及格预测</a></li>
					<li check-permiss callback="callback()" shirotag="xg:notGradDegree:*"><a href="notGradDegree">学位预警</a></li>
					<li check-permiss callback="callback()" shirotag="xg:feeWarning:*"><a href="feeWarning">欠费预警</a></li>
				</ul>
			</div>
		</nav>
	</div>
</div>
