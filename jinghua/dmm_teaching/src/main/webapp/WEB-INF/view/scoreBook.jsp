<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>男生女生学习成绩与图书馆出入分布</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/scoreAndBook/controller.js"></script>
<script type="text/javascript" src="product/scoreAndBook/service.js"></script>
<script type="text/javascript" src="product/scoreAndBook/constant.js"></script>

</head>
<body ng-controller="controller">
<div class="ss-mark-wrapper">
<div class="ss-mark-main">
<div class="header">
        <header class="header-tit">
            <b>男生女生学习成绩与图书馆出入分布</b>
        </header>
    </div>
<section class="gai-kuang-img">
<div >
<b>抽样数据来自 : 2015-2016学年 第二学期 全校考试成绩及图书馆门禁出入数据。</b>    
			<p class="block-tit"></p>
				<div class="img-box img-one">
        		<div Style="height:600px" echart config="chartCfg" class="center-block"></div>
            </div>
</div>
<div class="clearfix"></div>
</section>
</div>
</div>
</body>
</html>