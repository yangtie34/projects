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
    <title>上网类型分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body ng-controller="netTypeController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/net/js/netType.js"></script>   
  <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">上网类型分析</h3>
    <p class="xscz-default">对学校学生上网类型进行分析。</p>
  </div>
  

  <div class="xscz-box" style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
   <div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	
   
    
    <div class="xscz-nav">
      <!-- /* 统计*/-->
      
      <div class="xscz-tj">
        <div class="row row-20 text-center clearfix">
  			<ul class=" border-r-solid has-btm-border" ng-class="ulcla" ng-repeat="key in order ">
            	<li class="tit-row">
                	<span ><img src="${images}/all.png" alt=""></span>
                
                    <span>{{orderName[$index]}}</span>
                </li>
                 <li class="text-left">
                 <span class="icon-time" ng-click="qushiClick(key,'ALL_TIME');" style="float:right"title="趋势统计"></span>
                	<p class="xscz-ft-14">上网时长（单位：分钟）</p>
                    <p><b class="xscz-ft-22">{{vm[key].info.ALL_TIME}}</b></p>
                </li>
                 <li class="text-left">
                 <span class="icon-time" ng-click="qushiClick(key,'ALL_FLOW');" style="float:right"title="趋势统计"></span>
                	<p class="xscz-ft-14">上网流量（单位：MB）</p>
                    <p><b class="xscz-ft-22">{{vm[key].info.ALL_FLOW}}</b></p>
                </li>
<!--                  <li style="border-bottom: none;">
                	<div class="shw-pannel">
                    	<div class="shw-pannel-head">共计上网情况<a href="" class="shw-clock" ng-click="qushiClick(k,key,'bj');"title="趋势统计"></a></div>
                        <div class="shw-pannel-body" >
                        	<div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm"></span>时长</li>
                                    <li>
                                    	<div>分钟：<b class="xscz-ft-22 pull-right" >{{vm[key].info.ALL_TIME}}</b></div>
                                    </li>
                                </ul>
                            </div>
                            <div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm flow"></span>流量</li>
                                    <li>
                                    	<div>MB：<b class="xscz-ft-22 pull-right">{{vm[key].info.ALL_FLOW}}</b></div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </li>
                  <li style="border-bottom: none;">
                	<div class="shw-pannel">
                    	<div class="shw-pannel-head">人均单次上网情况<a href="" class="shw-clock" ng-click="qushiClick(k,key,'bj');"title="趋势统计"></a></div>
                        <div class="shw-pannel-body" >
                        	<div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm"></span>时长</li>
                                    <li>
                                    	<div>分钟：<b class="xscz-ft-22 pull-right" >{{vm[key].info.ONE_TIME}}</b></div>
                                    </li>
                                </ul>
                            </div>
                            <div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm flow"></span>流量</li>
                                    <li>
                                    	<div>MB：<b class="xscz-ft-22 pull-right">{{vm[key].info.ONE_FLOW}}</b></div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </li>
                  <li style="border-bottom: none;">
                	<div class="shw-pannel">
                    	<div class="shw-pannel-head">日均上网情况<a href="" class="shw-clock" ng-click="qushiClick(k,key,'bj');"title="趋势统计"></a></div>
                        <div class="shw-pannel-body" >
                        	<div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm"></span>时长</li>
                                    <li>
                                    	<div>分钟：<b class="xscz-ft-22 pull-right" >{{vm[key].info.DAY_TIME}}</b></div>
                                    </li>
                                </ul>
                            </div>
                            <div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm flow"></span>流量</li>
                                    <li>
                                    	<div>MB：<b class="xscz-ft-22 pull-right">{{vm[key].info.DAY_FLOW}}</b></div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </li> -->
                <li class="text-left">
                <span class="icon-time" ng-click="qushiClick(key,'STU_RATIO');" style="float:right"title="趋势统计"></span>
                	<p class="xscz-ft-14">上网人次占比</p>
                    <p><b class="xscz-ft-22">{{vm[key].STU_RATIO>100?100:vm[key].STU_RATIO}}%</b></p>
                    <div class="progress xscz-progress">
                      <div class="progress-bar progress-bar-{{echarColor[$index]}}" role="progressbar" 
                      aria-valuenow="{{vm[key].STU_RATIO}}" aria-valuemin="0" aria-valuemax="100" 
                      style="width:{{vm[key].STU_RATIO}}%;" ></div>
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">   分时段情况（单位：分钟）</p>
                    <div class="text-center">
                     <div stu-chart config="vm[key].fsd" style="height:310px;"class="img-responsive img-top"> </div>
                    <%-- 	<img src="${images}/one-card-02.png" alt=""> --%>
                    </div>
                </li>
            </ul>
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
