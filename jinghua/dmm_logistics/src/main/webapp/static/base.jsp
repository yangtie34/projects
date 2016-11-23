<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 导入angualr基础配置JSP -->
<% String basePath = request.getContextPath();
   int port = request.getServerPort();
   String ip = request.getServerName();
   String projectPath = "http://"+ip+":"+port+basePath;
   //遍历获取request对象参数
%>
<script type="text/javascript">
var base = "<%=projectPath%>/";
	 <!-- 配置请求的基础Ajax路径 -->
	 var httpConfig = {
    		 basePath : "<%=projectPath%>",
    		 baseUrl : "<%=projectPath%>"+"/common/getData",
    		 exportPageUrl : "<%=projectPath%>"+"/common/exportPage",
    		 uploadUrl : "<%=projectPath%>"+"/controller/upload_ng_fileupload",
    		 downloadUrl : "<%=projectPath%>"+"/controller/brower_common_download"
         };
</script>
<!-- 引入jquery -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=projectPath %>/product/clomun.js"></script> 
<!-- 引入日历组件 -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/components/My97DatePicker/WdatePicker.js"></script>
<!-- 引入Ext的模版对象 -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/template/template.js"></script>
<!-- 引入AngularJS -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/angular/angular.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/index.js"></script>
<!-- 引入AngularJS 我们的自定义扩展 -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/expand/ns-custom-debug.js"></script>
<!-- 引入AngularJS-File-Upload -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/angular/fileupload/angular-file-upload-all.min.js"></script>
<!-- 引入打印插件lodop检测 -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/LodopFuncs.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/framework/angular/angular-animate.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/framework/angular/angular-route.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/angular_expand/pc/ng-cg-custom-debug.js"></script> 
<!-- 引入AngularJS card专用 -->
<script type="text/javascript" src="<%=projectPath %>/static/angular_expand/pc/ng-card-zy.js"></script> 

<script type="text/javascript" src="<%=projectPath %>/static/js/charts/echarts/dist/echarts.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/js/charts/echarts/dist/echarts-all.js"></script>

<script type="text/javascript" src="<%=projectPath %>/static/framework/bootstrap-3.3.6/js/bootstrap.min.js"></script>

<!-- 引入样式 -->
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/framework/css/framework.css" />

<link href="<%=projectPath %>/static/framework/bootstrap-3.3.6/css/bootstrap.min.css" rel="stylesheet">
<%-- <link href="<%=projectPath %>/static/framework/bootstrap-3.3.6/css/bootstrap-theme.min.css" rel="stylesheet"> --%>

<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/angular_expand/css/list-style.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/angular_expand/css/combotree.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/angular_expand/css/ng-animation.css">
<!-- jxpg样式资源 -->
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/angular_expand/css/pingjiao-index.css" >
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/angular_expand/css/yf-little.css" >
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/angular_expand/example/css/tree.css">
<script type="text/javascript" src="<%=projectPath %>/static/echarts/echartsUtil.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/echarts/common.js"></script>
<link href="<%=projectPath %>/resource/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=projectPath %>/resource/css/yiqisbguoqiqkfx.css" rel="stylesheet">
<link href="<%=projectPath %>/resource/css/xscz-style.css" rel="stylesheet">
