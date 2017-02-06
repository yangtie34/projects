<%@page import="com.jhnu.syspermiss.util.SysConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" /> 
<!-- <meta name="apple-mobile-web-app-capable" content="yes" /> -->
<!-- <meta name="viewport" content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimal-ui" /> -->
<!-- <meta name="apple-mobile-web-app-status-bar-style" content="yes" /> -->
<%
	String projectPath = request.getContextPath();
	int port = request.getServerPort();
	String ip = request.getServerName();
	String root = "http://"+ip+":"+port+projectPath+"/";
	SysConfig sys = SysConfig.instance();
	String logoutUrl =  sys.getCasServerUrl() +"/logout?service="+sys.getCasServerLoginUrl() + "?service=" +sys.getServerUrl()+"/";
%>
<script type="text/javascript">
	var base = "<%=root %>";	
	var logoutUrl = "<%=logoutUrl %>";
</script>
<!-- angular and jquery -->
<script src="<%=root%>/static/jquery/jquery-1.9.1.min.js"></script>
<script src="<%=root%>/static/jquery/jquery.fileDownload.js"></script>
<script src="<%=root%>/static/angular/angular.min.js"></script>
<script src="<%=root%>/static/angular/angular-route.min.js"></script>
<script src="<%=root%>/static/angular/angular-animate.min.js"></script>
<!-- bootstrap -->
<link rel="stylesheet" type="text/css" href="<%=root%>/static/bootstrap-3.3.6/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/bootstrap-3.3.6/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="<%=root%>/static/font-awesome-4.5.0/css/font-awesome.min.css" />
<script src="<%=root%>/static/bootstrap-3.3.6/js/bootstrap.min.js"></script>
<!-- jquery 滚动条 -->
<link rel="stylesheet" type="text/css" href="<%=root%>/static/jquery/jquery.mCustomScrollbar.css" />
<script src="<%=root%>/static/jquery/jquery.mCustomScrollbar.concat.min.js"></script>
<!-- 引用插件 -->
<link rel="stylesheet" type="text/css" href="<%=root%>/static/toastr/toastr.min.css" />
<script src="<%=root%>/static/toastr/toastr.min.js"></script>
<script src="<%=root%>/static/echarts/2/echarts-all.js"></script>
<script src="<%=root%>/static/echarts/2/echarts.js"></script>

<!-- angular_expand css -->
<link rel="stylesheet" type="text/css" href="<%=root%>/static/resource/css/angular_expand/main.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/static/resource/basic/framework.css">
<!-- 自定义css -->
<link rel="stylesheet" type="text/css" href="<%=root%>/static/resource/css/student-mark-style.css">
<link rel="stylesheet" type="text/css" href="<%=root%>/static/resource/css/popup-form-style.css">
<!-- 自定义angular模块 -->
<%-- <script src="<%=root%>/static/angular_expand/pc/ng-system.js"></script> --%>
<script src="<%=root%>/static/angular_expand/pc/ng-system.min.js"></script>
<!-- 自定义工具 -->
<%-- <script src="<%=root%>/static/framework/Ice.all.js"></script> --%>
<script src="<%=root%>/static/framework/Ice.min.js"></script>
<!-- 打印组件 -->
<script src="<%=root%>/static/lodop/LodopFuncs.js"></script>
<!-- 日期组件 -->
<script src="<%=root%>/static/angular_expand/components/My97DatePicker/WdatePicker.js"></script>

<script>
	// jquery 滚动条
	(function($){
		$(window).on("load",function(){
			$(".dropdown-menu-scrollbar").mCustomScrollbar({
				theme:"minimal-dark",
				mouseWheel:{ preventDefault:true}
			});
		});
	})(jQuery);
</script>