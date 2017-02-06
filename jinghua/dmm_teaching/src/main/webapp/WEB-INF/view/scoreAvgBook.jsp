<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ base + "/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生平均排名与平均进出图书馆次数的散列分布</title>
<base href="<%=basePath%>" />
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/scoreAvgAndBook/controller.js"></script>
<script type="text/javascript" src="product/scoreAvgAndBook/service.js"></script>
<script type="text/javascript" src="product/scoreAvgAndBook/constant.js"></script>
</head>
<body ng-controller="controller">
<div class="ss-mark-wrapper">
<div class="ss-mark-main">
<div class="header">
        <header class="header-tit">
            <b>学生平均排名与平均进出图书馆次数的散列分布</b>
        </header>
    </div>
<section class="gai-kuang-img">
<div >
<b>图书馆进入平均次数与成绩综合排名，系统随机从全部在校学生中抽取男生500名，女生500名，并计算他们的平均排名与平均出入图书馆次数。</b>    
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