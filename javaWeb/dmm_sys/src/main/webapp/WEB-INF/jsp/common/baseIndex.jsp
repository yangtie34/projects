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
	 <!-- 配置请求的基础Ajax路径 -->
	 var httpConfig = {
    		 basePath : "<%=projectPath%>",
    		 baseUrl : "<%=projectPath%>"+"/common/getData",
    		 uploadUrl : "<%=projectPath%>"+"/controller/upload_ng_fileupload",
    		 downloadUrl : "<%=projectPath%>"+"/controller/brower_common_download"
         };
</script>
<!-- 引入jquery -->
<script type="text/javascript" src="<%=projectPath %>/static/framework/jquery/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/resource/basic/common/js/server.js"></script>
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

<!-- 引入样式 -->
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/framework.css" />
<!-- import css -->
<script>
    // 其他非法路径-返回首页或指定url
    function ToHome(url){
        window.location.href = httpConfig.basePath+'/'+ (url ? url : 'index.html');
    }
    // 访问的数据不合法
    function Wrongful(){
    	alert('您访问的数据不合法！');
    }
    var base = "<%=projectPath %>";
  	//删除左右两端的空格
    var trim=trim||function(str){
    	return str.replace(/(^\s*)|(\s*$)/g, ""); //把空格替换为空
  	}
</script>
<!-- 导入组件javascript -->

<%-- <script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/cgComboTree.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/cgMulQueryComm.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/cgCustomComm.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/cgTree.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/pagination.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/queryComm.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/cgComboBox.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/cgComboCheckTree.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/cgComboSelect.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/directives/cgCheckBoxTree.js"></script>
 --%>
 <script type="text/javascript" src="<%=projectPath %>/static/app_angular_expand/pc/ng-cg-custom-debug.js"></script> 

<script type="text/javascript" src="<%=projectPath %>/static/resource/basic/common/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=projectPath %>/static/resource/basic/common/js/star-rating.min.js"></script><!-- 星级评分 -->
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<link href="<%=projectPath %>/static/resource/basic/common/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=projectPath %>/static/resource/basic/common/css/bootstrap-theme.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/list-style.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/combotree.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/ng-animation.css">
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/common/css/star-rating.min.css" media="all"><!-- 星级评分 -->
<!-- jxpg样式资源 -->
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/pingjiao-index.css" >
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/resource/basic/pc/css/yf-little.css" >
<link rel="stylesheet" type="text/css" href="<%=projectPath %>/static/app_angular_expand/example/css/tree.css">

<link href="<%=projectPath %>/static/resource/basic/common/css/toastr.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src='<%=projectPath %>/static/resource/basic/common/js/toastr.min.js'></script> 


