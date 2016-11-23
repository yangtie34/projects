<%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8"%>
<% String basePath = request.getContextPath();
   int port = request.getServerPort();
   String ip = request.getServerName();
   String projectPath = "http://"+ip+":"+port+basePath;
   //遍历获取request对象参数
 //  String requestParams = RequestUtils.getRequestParams(request);
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
<script type="text/javascript" src="<%=projectPath %>/static/framework/jquery/jquery-1.9.1.min.js"></script>
<!-- 引入日历组件 -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/components/My97DatePicker/WdatePicker.js"></script>
<!-- 引入Ext的模版对象 -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/template/template.js"></script>
<!-- 引入AngularJS -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/angular/angular.js"></script>
<!-- 引入AngularJS 我们的自定义扩展 -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/expand/ns-custom-debug.js"></script>
<!-- 引入AngularJS-File-Upload -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/angular/fileupload/angular-file-upload-all.min.js"></script>
<!-- 引入打印插件lodop检测 -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/LodopFuncs.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/framework/angular/angular-animate.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/framework/angular/angular-route.js"></script>
<!-- highcharts js -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/highchart/highcharts.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/framework/highchart/highcharts-more.js"></script>
<script type="text/javascript">
	//配置request对象
	//配置用户对象 	
	angular.module("services").constant('userObj',{});
	var jxpg = angular.module('jxpg',['services']);
</script>


<!-- 引入样式 -->
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/framework.css" />

<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="<%=projectPath %>/static/resource/basic/common/js/bootstrap.min.js"></script>
<link href="<%=projectPath %>/static/resource/basic/common/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/combotree.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/list-style.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/sushe-index.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/sushe-xinxi.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/app_angular_expand/example/css/tree.css">

<script type="text/javascript" src="../pc/directives/cgComboTree.js"></script>
<script type="text/javascript" src="../pc/directives/cgMulQueryComm.js"></script>
<script type="text/javascript" src="../pc/directives/cgCustomComm.js"></script>
<script type="text/javascript" src="../pc/directives/cgTree.js"></script>
<script type="text/javascript" src="../pc/directives/pagination.js"></script>
<script type="text/javascript" src="../pc/directives/queryComm.js"></script>
<script type="text/javascript" src="../pc/directives/cgComboBox.js"></script>
<script type="text/javascript" src="../pc/directives/cgComboCheckTree.js"></script>
<script type="text/javascript" src="../pc/directives/cgComboSelect.js"></script>
<script type="text/javascript" src="../pc/directives/cgCheckBoxTree.js"></script>