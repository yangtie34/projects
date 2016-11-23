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
    <title>消费热度分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body ng-controller="payHotController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/card/js/payHot.js"></script>   
  <div class="content" >
  
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">消费热度分析</h3>
    <p class="xscz-default">对学校各个窗口的热度进行分析。</p>
  </div>
  
 <!--  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	 -->
 
  <div class="xscz-box content_main">
  <div cg-combo-nyrtj result="date" yid=1></div>
    <div class="xscz-nav">
      <!-- /* 统计*/-->
      
      <div class="xscz-tj mar-t-35">
          <table class="table dashed-tab text-center">
          	<thead>
            	<tr class="btm-dashed">
                	<td colspan="2"></td>
                    <td class="tit-row">
                    	<span><img src="${images}/all-7.png" alt=""></span>
                        <span>总体</span>
                    </td>
                    <td class="tit-row">
                    	<span><img src="${images}/dinner.png" alt=""></span>
                        <span>餐厅</span>
                    </td>
                    <td class="tit-row">
                    	<span><img src="${images}/supermarket.png" alt=""></span>
                        <span>超市</span>
                    </td>
                    <td class="tit-row">
                    	<span><img src="${images}/bath.png" alt=""></span>
                        <span>洗浴</span>
                    </td>
                </tr>
            </thead>
            <tbody class="btm-dashed">
            	<tr>
                	<td rowspan="3"><span class="green-pop">共计</span></td>
                    <td>热销<br>次数</td>
                    <td><b>1200</b>次</td>
                    <td><b>550</b>次</td>
                    <td><b>450</b>次</td>
                    <td><b>300</b>次</td>
                </tr>
                <tr class="text-org btm-dashed">
                    <td>持续<br>人数</td>
                    <td><b>150</b>人</td>
                    <td><b>60</b>人</td>
                    <td><b>50</b>人</td>
                    <td><b>40</b>人</td>
                </tr>
                 <tr class="btm-dashed">
                    <td>持续<br>时长</td>
                    <td><b>150</b>分</td>
                    <td><b>90</b>分</td>
                    <td><b>50</b>分</td>
                    <td><b>40</b>分</td>
                </tr>
                <tr class="text-org">
                	<td rowspan="2"><span class="red-pop">最高</span></td>
                    <td>发生<br>人数</td>
                    <td><b>100</b>人</td>
                    <td><b>50</b>人</td>
                    <td><b>30</b>人</td>
                    <td><b>20</b>人</td>
                </tr>
                <tr class="btm-dashed">
                    <td>持续<br>时长</td>
                    <td><b>150</b>分</td>
                    <td><b>90</b>分</td>
                    <td><b>50</b>分</td>
                    <td><b>40</b>分</td>
                </tr>
            </tbody>
          </table>
      </div>
      <!-- /* 统计*/ end-->
      <div class="xscz-tj">
      	<div class="xscz-cit text-center">
        	<span>各类型热度对比</span>
        </div>
        <div class="xscz-slt text-left">
        	<input class="xscz-radio" type="radio" id="rd-1" name="rd0" checked="">
            <label for="rd-1">共热销次数</label>
            <input class="xscz-radio" type="radio" name="rd0" id="rd-2">
            <label for="rd-2">共持续人数</label>
        </div>
        <div class="dis-tab mar-t-35 text-center">
        	<div class="dis-tab-cell wid-35 border-r-dash r-20">
            	<img src="${images}/img-7-1.jpg" alt="">
            </div>
            <div class="dis-tab-cell l-20">
            	<img src="${images}/img-7-2.png" alt="">
            </div>
        </div>
      </div>
      <!-- /* 统计*/ end-->
      <div class="xscz-tj">
      	<div class="xscz-cit text-center">
        	<span>餐厅窗口热销分析</span>
        </div>
        <div class="xscz-tab-org">
        	<a href="" class="active">所有餐厅</a>&nbsp;<a href="">A餐厅</a>&nbsp;<a href="">B餐厅</a>&nbsp;<a href="">C餐厅</a>&nbsp;<a href="">D餐厅</a>
        </div>
        <div class="dis-tab mar-t-35 text-center">
        	<div class="dis-tab-cell wid-35 border-r-dash r-20">
            	<h4 class="xscz-ft-16">所属餐厅对比情况</h4>
            	<img src="${images}/img-7-3.png" alt="">
            </div>
            <div class="dis-tab-cell l-20">
            	<h4 class="xscz-ft-16">热销窗口Top10
                	<div class="xscz-sort-row">
                    	<a href="" class="caret-left"></a>
                        <a href="" class="caret-right active"></a>
                    </div>
                </h4>
                <div>
            	<table class="table table-hover text-center xscz-ft-14 num-org no-mar-btm">
                	<thead>
                    	<tr>
                        	<td>&nbsp;</td>
                            <td>窗口名称</td>
                            <td>所属餐厅</td>
                            <td>总热销次数</td>
                            <td>总持续人数</td>
                            <td>总持续时间</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr>
                        	<td><span class="order-circle">1</span></td> 
                            <td>015窗口</td>
                            <td>A餐厅</td>
                            <td>120</td>
                            <td class="xscz-yellow">120</td>
                            <td>120</td>
                        </tr>
                        <tr>
                        	<td><span class="order-circle">2</span></td>
                            <td>010窗口</td>
                            <td>A餐厅</td>
                            <td>110</td>
                            <td class="xscz-yellow">110</td>
                            <td>110</td>
                        </tr>
                        <tr>
                        	<td><span class="order-circle">3</span></td>
                            <td>006窗口</td>
                            <td>B餐厅</td>
                            <td>100</td>
                            <td class="xscz-yellow">100</td>
                            <td>100</td>
                        </tr>
                        <tr>
                        	<td><span class="order-circle">4</span></td> 
                            <td>005窗口</td>
                            <td>B餐厅</td>
                            <td>90</td>
                            <td class="xscz-yellow">90</td>
                            <td>90</td>
                        </tr>
                        <tr>
                        	<td><span class="order-circle">5</span></td>
                            <td>019窗口</td>
                            <td>A餐厅</td>
                            <td>80</td>
                            <td class="xscz-yellow">80</td>
                            <td>80</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            </div>
        </div>
      </div>
      <!-- /* 统计*/ end-->
      <div class="xscz-tj">
      	<div class="xscz-cit text-center">
        	<span>窗口排名Top10</span>
        </div>
        <div class="text-center">
        	<img src="${images}/img-7-5.png" alt="">
        </div>
      </div>
      <!-- /* 统计*/ end-->
    </div>
  </div>
 </div>
</div> 
  </body>
  <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
	<div stu-chart config="qushiData" class="qsdivc" ></div>
</div>
</html>
