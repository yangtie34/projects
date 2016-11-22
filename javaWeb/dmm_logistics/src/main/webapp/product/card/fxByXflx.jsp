<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
  <head>
  <%--   <base href="<%=basePath%>"> --%>
    <jsp:include page="../../static/base.jsp"></jsp:include>
    <title>分消费类型消费习惯分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body ng-controller="fxByXflxController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/card/js/fxByXflx.js"></script>   
  <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">学生按消费类型消费习惯分析</h3>
    <p class="xscz-default">对学校学生消费习惯进行分析。</p>
  </div>
  

  <div class="xscz-box" style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
   <div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	
   
    
    <div class="xscz-nav">
      <!-- /* 统计*/-->
      
      <div class="xscz-tj">
        <div class="xscz-cit text-center">
        	<span ng-class="csjecode=='cs'?active:'normal'" ng-click="csje('cs');"><a href="">刷卡次数</a></span>
            <span ng-class="csjecode!='cs'?active:'normal'" ng-click="csje('je');"><a href="">消费金额</a></span>
        </div>
        <div class="row row-20 text-center clearfix">
  			<ul class="border-r-solid has-btm-border" ng-class="ulcla[0]"ng-repeat="key in vm.items[1].order " ng-show="vm.items[0].all.cs>0&&key!='all'?vm.items[1][key].show:true">
            	<li class="tit-row">
                	<span><img ng-src="${images}/{{photo[key]}}.png" alt=""></span>
                    <span>{{vm.items[1][key].name}}</span>
                </li>
                <li class="text-left">
                <span class="icon-time" style="float:right"ng-click="qushiClick(key);" title="趋势统计"></span>
                	<p class="xscz-ft-14">{{csjecode=='cs'?'刷卡次数（单位：笔）':'消费金额（单位：元）'}}</p>
                    <p><b class="xscz-ft-22">{{vm.items[1][key][csjecode]||0}}</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">日均{{csjecode=='cs'?'刷卡':'消费'}}占比</p>
                    <p><b class="xscz-ft-22">{{(vm.items[1][key][csjecode]*100/vm.items[1]['all'][csjecode]|number:2)||0}}%</b></p>
                    <div class="progress xscz-progress">
                      <div class="progress-bar progress-bar-{{echarColor[$index]}}" role="progressbar" 
                      aria-valuenow="{{vm.items[1][key][csjecode]*100/vm.items[1]['all'][csjecode]|number:2}}" aria-valuemin="0" aria-valuemax="100" 
                      style="width:{{vm.items[1][key][csjecode]*100/vm.items[1]['all'][csjecode]||0}}%;" ></div>
                    </div>
                </li>
                <li class="text-center">
                 <div stu-chart config="vm.items[1][key]['zzw'][csjecode]||''" style="height:310px;"class="img-responsive img-top"> </div>
                	<%-- <img src="${images}/one-card-01.jpg" alt=""> --%> 
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分时段统计{{csjecode=='cs'?'刷卡次数':'消费金额'}}</p>
                    <div class="text-center">
                     <div stu-chart config="vm.items[1][key]['fsd'][csjecode]||''" style="height:310px;"class="img-responsive img-top"> </div>
                    <%-- 	<img src="${images}/one-card-02.png" alt=""> --%>
                    </div>
                </li>
            </ul>
            <!--以上总体-->
 <%--            <ul class="col-md-3 border-r-solid has-btm-border">
            	<li class="tit-row">
                	<span><img ng-src="${images}/dinner.png" alt=""></span>
                    <span>餐厅</span>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">刷卡次数（单位：笔）</p>
                    <p><b class="xscz-ft-22">12000</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">日均刷卡人数占比</p>
                    <p><b class="xscz-ft-22">62%</b></p>
                    <div class="progress xscz-progress">
                      <div class="progress-bar progress-bar-blue" role="progressbar" aria-valuenow="62" aria-valuemin="0" aria-valuemax="100" style="width: 62%"></div>
                    </div>
                </li>
                <li class="text-center">
                	<img src="${images}/one-card-01.jpg" alt="">
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分时段统计刷卡次数</p>
                    <div class="text-center">
                    	<img src="${images}/one-card-02.png" alt="">
                    </div>
                </li>
            </ul>
             <!--以上餐厅-->
            <ul class="col-md-3 border-r-solid has-btm-border">
            	<li class="tit-row">
                	<span><img src="${images}/supermarket.png" alt=""></span>
                    <span>超市</span>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">刷卡次数（单位：笔）</p>
                    <p><b class="xscz-ft-22">6000</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">日均刷卡人数占比</p>
                    <p><b class="xscz-ft-22">59%</b></p>
                    <div class="progress xscz-progress">
                      <div class="progress-bar progress-bar-purple" role="progressbar" aria-valuenow="59" aria-valuemin="0" aria-valuemax="100" style="width: 59%"></div>
                    </div>
                </li>
                <li class="text-center">
                	<img src="${images}/one-card-01.jpg" alt="">
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分时段统计刷卡次数</p>
                    <div class="text-center">
                    	<img src="${images}/one-card-02.png" alt="">
                    </div>
                </li>
            </ul>
             <!--以上超市-->
            <ul class="col-md-3 has-btm-border">
            	<li class="tit-row">
                	<span><img src="${images}/bath.png" alt=""></span>
                    <span>洗浴</span>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">刷卡次数（单位：笔）</p>
                    <p><b class="xscz-ft-22">4000</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">日均刷卡人数占比</p>
                    <p><b class="xscz-ft-22">23%</b></p>
                    <div class="progress xscz-progress">
                      <div class="progress-bar progress-bar-pink" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 23%"></div>
                    </div>
                </li>
                <li class="text-center">
                	<img src="${images}/one-card-01.jpg" alt="">
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分时段统计刷卡次数</p>
                    <div class="text-center">
                    	<img src="${images}/one-card-02.png" alt="">
                    </div>
                </li>
            </ul> --%>
            <!--以上洗浴-->
        </div>       
      </div>
      <!-- /* 统计*/-->
    </div>
  </div>
  
 </div>
</div> 
  </body>
  <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
	<div stu-chart config="qushiData" class="qsdivc" ></div>
</div>
</html>
