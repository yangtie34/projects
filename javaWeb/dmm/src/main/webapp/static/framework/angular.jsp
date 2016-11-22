<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
<%@ page import="cn.gilight.framework.mvc.util.*" %>
<% String basePath = request.getContextPath();
   int port = request.getServerPort();
   String ip = request.getServerName();
   String projectPath = "http://"+ip+":"+port+basePath;
   //遍历获取request对象参数
   String requestParams = RequestUtils.getRequestParams(request);
%>
<script type="text/javascript">
	 <!-- 配置请求的基础Ajax路径 -->
	 var httpConfig = {
    		 basePath : "<%=projectPath%>",
    		 baseUrl : "<%=projectPath%>"+"/controller/core",
    		 uploadUrl : "<%=projectPath%>"+"/controller/upload_ng_fileupload",
    		 downloadUrl : "<%=projectPath%>"+"/controller/brower_common_download"
         };
</script>
<!-- 引入jquery -->
<script type="text/javascript" src="<%=projectPath %>/framework/jquery/jquery-1.9.1.min.js"></script>
<!-- 引入日历组件 -->
<script type="text/javascript" src="<%=projectPath %>/framework/components/My97DatePicker/WdatePicker.js"></script>
<!-- 引入Ext的模版对象 -->
<script type="text/javascript" src="<%=projectPath %>/framework/template/template.js"></script>
<!-- 引入AngularJS -->
<script type="text/javascript" src="<%=projectPath %>/framework/angular/angular.js"></script>
<!-- 引入AngularJS 我们的自定义扩展 -->
<script type="text/javascript" src="<%=projectPath %>/framework/expand/ns-custom-debug.js"></script>
<!-- 引入AngularJS-File-Upload -->
<script type="text/javascript" src="<%=projectPath %>/framework/angular/fileupload/angular-file-upload-all.min.js"></script>
<!-- 引入打印插件lodop检测 -->
<script type="text/javascript" src="<%=projectPath %>/framework/LodopFuncs.js"></script>
<script type="text/javascript" src="<%=projectPath %>/framework/angular/angular-animate.js"></script>
<script type="text/javascript" src="<%=projectPath %>/framework/angular/angular-route.js"></script>
<!-- highcharts js -->
<script type="text/javascript" src="<%=projectPath %>/framework/highchart/highcharts.js"></script>
<script type="text/javascript" src="<%=projectPath %>/framework/highchart/highcharts-more.js"></script>
<script type="text/javascript">
	//配置request对象
	angular.module("services").constant('request',angular.fromJson(<%=requestParams%>));
	//配置用户对象 	
	angular.module("services").constant('userObj',{});
</script>


<!-- 引入样式 -->
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/resource/basic/framework.css" />


