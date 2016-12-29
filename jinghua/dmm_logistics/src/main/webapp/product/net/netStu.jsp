<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<!DOCTYPE html>
<html ng-app="app">
  <head>
  <%--   <base href="<%=basePath%>"> --%>
    <jsp:include page="../../static/base.jsp"></jsp:include>
    <title>学生上网习惯分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body ng-controller="netStuController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/net/js/netStu.js"></script>   
  <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">学生上网习惯分析</h3>
    <p class="xscz-default">对学生上网习惯分析。</p>
  </div>
  

  <div class="xscz-box"style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
    <div class="xscz-nav">
      <!-- /* 统计*/-->
      <div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	
  
      <div class="xscz-tj">
      	<div class="row  row-20 clearfix">
        	<div ng-class="ulcla[0]"></div>
            <div ng-class="ulcla[1]">
                <div class="xscz-cit text-center">
                    <span ng-class="xbxlcode=='xb'?active:'normal'" ng-click="xbxl('xb');"><a href="">按性别</a></span>
                    <span ng-class="xbxlcode!='xb'?active:'normal'" ng-click="xbxl('xl');"><a href="">按学历</a></span>
                </div>
            </div>
        </div>
        <div class="row row-20 text-center clearfix">
        <div ng-repeat="(key,value) in vm" ng-show="key=='all'||key==xbxlcode">
  			<ul class="has-btm-border no-mar-btm" ng-class="ulcla[0]" ng-class="key!='all'&&$index==0?'border-r-solid':''" ng-repeat="(k,v) in value" ng-show="k_xlxsids(key,k);">
            	<li class="tit-row" style="border-bottom: none;">
                	<span class="tit-40"><img ng-src="${images}/gender-{{ico_title[key][k].ico}}.png" alt=""></span>
                    <span>{{ico_title[key][k].name}}</span>
                </li>
                
                <li style="border-bottom: none;">
                	<div class="shw-pannel">
                    	<div class="shw-pannel-head">共计上网情况
                    	 <div tool-tip placement="top" hide-icon="true" class="shw-clock" ng-click="qushiClick(key,k,'all');">
									<div style="width: 160px">点击此处查看趋势</div>
								</div> 
                    	<!-- <a href="" class="shw-clock" ng-click="qushiClick(key,k,'all');"title="趋势统计"></a> --> </div>
                        <div class="shw-pannel-body" >
                        	<div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm"></span>时长</li>
                                    <li>
                                    	<div>分钟：<b class="xscz-ft-22 pull-right" >{{v.info.ALL_TIME}}</b></div>
                                    </li>
                                    <!--  <li ng-show="key!='all'">
                                    	<div>相对整体：<b class="xscz-ft-22 pull-right"ng-class="v.info.updown.ALL_TIMEcla[1]">{{v.info.updown.ALL_TIMEval}}</b></div>
                                    </li> -->
                                </ul>
                            </div>
                            <div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm flow"></span>流量</li>
                                    <li>
                                    	<div>MB：<b class="xscz-ft-22 pull-right">{{v.info.ALL_FLOW}}</b></div>
                                    </li>
                                      <!-- <li ng-show="key!='all'">
                                    	<div>相对整体：<b class="xscz-ft-22 pull-right" ng-class="v.info.updown.ALL_FLOWcla[1]">{{v.info.updown.ALL_FLOWval}}</b></div>
                                    </li> -->
                                </ul>
                            </div>
                        </div>
                    </div>
                </li>
                <li style="border-bottom: none;">
                	<div class="shw-pannel">
                    	<div class="shw-pannel-head">人均单次上网情况
                    	<div tool-tip placement="top" hide-icon="true" class="shw-clock" ng-click="qushiClick(key,k,'one');">
									<div style="width: 160px">点击此处查看趋势</div>
						</div> 
                    	<!-- <a href="" class="shw-clock" ng-click="qushiClick(key,k,'one');"title="趋势统计"></a> -->
                    	</div>
                        <div class="shw-pannel-body" ng-class="key=='all'?'hei-147':''">
                        	<div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm"></span>时长</li>
                                    <li ng-class="key=='all'?'mar-t-48':''">
                                    	<div>分钟：<b class="xscz-ft-22 pull-right" ng-class="key=='all'?'':v.info.updown.ONE_TIMEcla[0]">{{v.info.ONE_TIME}}</b></div>
                                    </li>
                                     <li ng-show="key!='all'">
                                    	<div>相对整体：<b class="xscz-ft-22 pull-right"ng-class="v.info.updown.ONE_TIMEcla[1]">{{v.info.updown.ONE_TIMEval|number:2}}</b></div>
                                    </li>
                                </ul>
                            </div>
                            <div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm flow"></span>流量</li>
                                    <li ng-class="key=='all'?'mar-t-48':''">
                                    	<div>MB：<b class="xscz-ft-22 pull-right" ng-class="key=='all'?'':v.info.updown.ONE_FLOWcla[0]">{{v.info.ONE_FLOW}}</b></div>
                                    </li>
                                      <li ng-show="key!='all'">
                                    	<div>相对整体：<b class="xscz-ft-22 pull-right "ng-class="v.info.updown.ONE_FLOWcla[1]">{{v.info.updown.ONE_FLOWval|number:2}}</b></div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </li>
               <li style="border-bottom: none;">
                	<div class="shw-pannel">
                    	<div class="shw-pannel-head">日均上网情况
                    	<div tool-tip placement="top" hide-icon="true" class="shw-clock" ng-click="qushiClick(key,k,'day');">
									<div style="width: 160px">点击此处查看趋势</div>
						</div> 
                    	<!-- <a href="" class="shw-clock" ng-click="qushiClick(key,k,'day');"title="趋势统计"></a> -->
                    	</div>
                        <div class="shw-pannel-body" ng-class="key=='all'?'hei-147':''">
                        	<div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm"></span>时长</li>
                                    <li ng-class="key=='all'?'mar-t-48':''">
                                    	<div>分钟：<b class="xscz-ft-22 pull-right"ng-class="key=='all'?'':v.info.updown.DAY_TIMEcla[0]">{{v.info.DAY_TIME}}</b></div>
                                    </li>
                                     <li ng-show="key!='all'">
                                    	<div>相对整体：<b class="xscz-ft-22 pull-right grow-up"ng-class="v.info.updown.DAY_TIMEcla[1]">{{v.info.updown.DAY_TIMEval|number:2}}</b></div>
                                    </li>
                                </ul>
                            </div>
                            <div class="wid-5">
                            	<ul class="list-unstyled">
                                	<li><span class="shw-icon-sm flow"></span>流量</li>
                                    <li ng-class="key=='all'?'mar-t-48':''">
                                    	<div>MB：<b class="xscz-ft-22 pull-right"ng-class="key=='all'?'':v.info.updown.DAY_TIMEcla[0]">{{v.info.DAY_FLOW}}</b></div>
                                    </li>
                                      <li ng-show="key!='all'">
                                    	<div>相对整体：<b class="xscz-ft-22 pull-right"ng-class="v.info.updown.DAY_FLOWcla[1]">{{v.info.updown.DAY_FLOWval|number:2}}</b></div>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分类型上网时长（单位：分钟）</p>
                    <div class="text-center">
                     <div stu-chart config="v.flx.time" style="height:310px;"class="img-responsive img-top"> </div>
                		<%-- <img src="${images}/04.jpg" alt=""> --%>
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分类型上网流量（单位：MB）</p>
                    <div class="text-center">
                     <div stu-chart config="v.flx.flow" style="height:310px;"class="img-responsive img-top"> </div>
                		<%-- <img src="${images}/04.jpg" alt=""> --%>
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分时段人数情况</p>
                    <div class="text-center">
                      <div stu-chart config="v.fsd" style="height:310px;"class="img-responsive img-top"> </div>
                    	<%-- <img src="${images}/xf-03.png" alt=""> --%>
                    </div>
                </li>
            </ul>
            </div>



        </div>       
      </div>
    </div>
  </div>
 </div>
</div> 
  </body>
  <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
	<div stu-chart config="qushiData" class="qsdivc" ></div>
</div>
</html>
