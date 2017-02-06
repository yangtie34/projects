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
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="teaching:groupTeach:*">
				<div class="subNav">
					<em class="undefined">师资队伍</em>
				</div>
				<ul class="navContent" style="margin: 0px;">
					<li check-permiss callback="callback()" shirotag="teaching:teacherGroup:*"><a href="teacherGroup">师资队伍</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="teaching:groupScore:*">
				<div class="subNav">
					<em class="undefined">成绩分析</em>
				</div>
				<ul class="navContent" style="margin: 0px;">
					<li check-permiss callback="callback()" shirotag="teaching:score:*"><a href="score">成绩概况</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:smart:*"><a href="smart">学霸</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:scoreByStuLb:*"><a href="scoreByStuLb">课程成绩分析</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:bysScore:*"><a href="bysScore">全周期成绩分析</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:scoreHistory:*"><a href="scoreHistory">学生历史成绩分析</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:failExamination:*"><a href="failExamination">不及格补考分析</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:scoreTea:*"><a href="scoreTea">成绩分析（任课教师）</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="teaching:groupPred:*">
				<div class="subNav">
					<em class="undefined">成绩预测</em>
				</div>
				<ul class="navContent" style="margin: 0px;">
					<li check-permiss callback="callback()" shirotag="teaching:scorePredict:*"><a href="scorePredict">成绩预测(辅导员)</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:scorePredictTea:*"><a href="scorePredictTea">成绩预测(任课教师)</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="teaching:groupStusource:*">
				<div class="subNav">
					<em class="undefined">生源及毕业分析</em>
				</div>
				<ul class="navContent" style="margin: 0px;">
					<li check-permiss callback="callback()" shirotag="teaching:studentsQuality:*"><a href="studentsQuality">生源质量</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:bysDegree:*"><a href="bysDegree">毕业及学位授予分析</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:bysQx:*"><a href="bysQx">毕业生去向</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="teaching:groupTeachhelp:*">
				<div class="subNav">
					<em class="undefined">教学辅助分析</em>
				</div>
				<ul class="navContent" style="margin: 0px;">
					<li check-permiss callback="callback()" shirotag="teaching:majorStatus:*"><a href="majorStatus">专业开设</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:teachingFunds:*"><a href="teachingFunds">教学经费分析</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:course:*"><a href="course">课程分析</a></li>
					<li check-permiss callback="callback()" shirotag="teaching:period:*"><a href="period">课时分析</a></li>
				</ul>
			</div>
		</nav>
	</div>
</div>
