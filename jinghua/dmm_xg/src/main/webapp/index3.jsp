<%@ page language="java" isELIgnored="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html ng-app="app" class="panel-fit">
<head>
<base href="<%=root%>/index.jsp"/>
<jsp:include page="/static/base.jsp"></jsp:include>
<link href="static/resource/css/leftnav.css" rel="stylesheet">
<title>学生工作主题分析</title>
<script type="text/javascript" src="common/js/controller.js"></script>
<script type="text/javascript" src="common/js/service.js"></script>
<style>
	html{
	    height: 100%;
	    margin: 0;
	    padding: 0;
	    border: 0;
	    overflow: hidden;
	}
	.main-container{
        position: relative;
	    height: 100%;
	    width:100%;
	    margin: 0;
	    padding: 0;
	    border: 0;
	    overflow: hidden;
	}
	.main-west{
		width: 280px;
		height: 100%;
		position: absolute;
		left: 0px;
		z-index: 10;
	}
	
	.main-content{
		position: absolute;
		height: 100%;
		width: 100%;
		padding-left:280px;
		right: 0px;
		z-index: 9;
	}
	#_main_menu_container>li>a{
		text-overflow: ellipsis; 
		white-space: nowrap; 
		width: 100%; 
		overflow: hidden;
		text-decoration: none;
	}
	#_main_menu_container>li>a:HOVER{
		text-decoration: none;
	}
</style>
</head>
<body ng-controller="controller" class="main-container">
<%-- 这是页面的整体架子 --%>
	<div class="main-west">
		<div class="xscz-swith xscz-swith-on"><a href="javascript:void(0);" ng-click="hideWest()"></a></div> 
		<jsp:include page="common/menu.jsp"></jsp:include>
	</div>
	<div class="xscz-swith xscz-swith-off"><a href="javascript:void(0);" ng-click="showWest()"></a></div>
    <!----------------------------------------------以上为导航内容------------------------------------>
    <div class="main-content">
    	<iframe height="100%" width="100%" style="border: 0;overflow: auto;" src=""></iframe>
    </div>
</body>
</html>