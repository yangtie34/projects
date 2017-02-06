<%@page import="com.jhnu.syspermiss.util.SysConfig"%>
<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
	SysConfig sys = SysConfig.instance();
	String logoutUrl =  sys.getCasServerUrl() +"/logout?service="+sys.getCasServerLoginUrl();// 直接退出到cas 。。 + "?service=" +sys.getServerUrl()+"/";
%>
<div style="position: relative; overflow: hidden;width: 100%;height: 100%;overflow:auto;background-color: #32323a;">
	<div style=" width: 100%;top: 0px;z-index: 10;">
		<div class="line_border">
	        <div class="face-line">
	        <div style="display:table-cell; width: 20px;">
	        <img src="<%=root%>/common/getphoto" class="img-circle center-block" width="50" height="50">
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
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="research:thesis:*">
				<div class="subNav currentDt">
					<em class="undefined">科研论文</em>
				</div>
				<ul class="navContent" style="margin: 0px;display: block;">
					<li check-permiss callback="afterPermissCheck()" shirotag="research:thesis:total:*"><a href="../thesis/total/index.jsp">科研论文发表量</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:thesis:publish:*"><a href="../thesis/publish/index.jsp">高影响力期刊发文分析</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:thesis:author:*"><a href="../thesis/include/index.jsp">高影响力期刊收录论文分析</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:thesis:author:*"><a href="../thesis/author/index.jsp">科研论文作者分析</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="research:project:*">
				<div class="subNav">
					<em class="undefined">科研项目</em>
				</div>
				<ul class="navContent" style="margin: 0px">
					<li check-permiss callback="afterPermissCheck()" shirotag="research:project:total:*"><a href="../project/total/index.jsp">科研项目总量分析</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:project:compere:*"><a href="../project/compere/index.jsp">科研项目主持人分析</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:project:fund:*"><a href="../project/fund/index.jsp">科研项目经费分析</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:project:progress:*"><a href="../project/progress/index.jsp">科研项目进度分析</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="research:achievement:*">
				<div class="subNav">
					<em class="undefined">科研成果专利</em>
				</div>
				<ul class="navContent" style="margin: 0px">
					<li check-permiss callback="afterPermissCheck()" shirotag="research:achievement:appraisal:*"><a href="../achievement/appraisal/index.jsp">科研鉴定成果分析</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:achievement:awards:*"><a href="../achievement/awards/index.jsp">科研获奖成果分析</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:achievement:patent:*"><a href="../achievement/patent/index.jsp">国家专利成果分析</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="research:writing:*">
				<div class="subNav">
					<em class="undefined">科研著作</em>
				</div>
				<ul class="navContent" style="margin: 0px">
					<li check-permiss callback="afterPermissCheck()" shirotag="research:writing:work:*"><a href="../writing/work/index.jsp">科研著作分析</a></li>
					<li check-permiss callback="afterPermissCheck()" shirotag="research:achievement:soft:*"><a href="../achievement/soft/index.jsp">计算机软件著作权分析</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;"  check-permiss shirotag="research:xkjs:*" >
				<div class="subNav">
					<em class="undefined">学科建设</em>
				</div>
				<ul class="navContent" style="margin: 0px">
					<li ><a href="../xkjs/index.jsp">全校学科建设情况</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;" check-permiss shirotag="research:kyjl:*">
				<div class="subNav">
					<em class="undefined">高层次科研奖励</em>
				</div>
				<ul class="navContent" style="margin: 0px">
					<li check-permiss shirotag="research:kyjl:award:*"><a href="../award/award/index.jsp">高层次研究成果奖励</a></li>
					<li check-permiss shirotag="research:kyjl:detail:*"><a href="../award/detail/index.jsp">高层次研究成果各项奖励</a></li>
					<li check-permiss shirotag="research:kyjl:personal:*"><a href="../award/personal/index.jsp">高层次研究成果个人奖励明细</a></li>
				</ul>
			</div>
			<div class="line_border" style="background-color: #32323a;"  check-permiss shirotag="research:mbrws:*">
				<div class="subNav">
					<em class="undefined">目标任务书</em>
				</div>
				<ul class="navContent" style="margin: 0px">
					<li check-permiss shirotag="research:mbrws:index:*"><a href="../../gd/mbrws/index.jsp">目标任务书</a></li>
				</ul>
			</div>
		</nav>
	</div>
</div>
