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
    <title>学生消费能力</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
<body ng-controller="payAbilityController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/card/js/payAbility.js"></script>   
  <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">学生消费能力分析</h3>
    <p class="xscz-default">对学生消费能力进行分析。</p>
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
        	<div class="col-md-4"></div>
            <div class="col-md-8">
                <div class="xscz-cit text-center">
                    <span ng-class="xbxlcode=='xb'?active:'normal'" ng-click="xbxl('xb');"><a href="">按性别</a></span>
                    <span ng-class="xbxlcode!='xb'?active:'normal'" ng-click="xbxl('xl');"><a href="">按学历</a></span>
                </div>
            </div>
        </div>
        <div class="row row-20 text-center clearfix">
        <div ng-repeat="(key,value) in vm.items[0]" ng-show="key=='all'||key==xbxlcode">
  			<ul class="col-md-4 has-btm-border no-mar-btm" ng-class="key!='all'&&$index==0?'border-r-solid':''" ng-repeat="(k,v) in value" ng-show="k_xlxsids(k);">
            	<li class="tit-row">
                	<span class="tit-40"><img ng-src="${images}/gender-{{ico_title[key][k].ico}}.png" alt=""></span>
                    <span>{{ico_title[key][k].name}}</span>
                </li>
                <li class="text-left">
                 <div tool-tip placement="top" hide-icon="true" class="icon-time"style="float:right" ng-click="qushiClick(k,key,'all');">
									<div style="width: 60px">查看趋势</div>
								</div>
                <!--  <span class="icon-time" ng-click="qushiClick(k,key,'all');" style="float:right"title="趋势统计"></span> -->
                	<p class="xscz-ft-14">消费金额（单位：元）</p>
                    <p><b class="xscz-ft-22">{{v.xfnl.ALL_MONEY|number:2}}</b></p>
                </li>
                <li class="text-left">
                 <div tool-tip placement="top" hide-icon="true" class="icon-time"style="float:right" ng-click="qushiClick(k,key,'bj');">
									<div style="width: 60px">查看趋势</div>
								</div>
                <!--  <span class="icon-time" ng-click="qushiClick(k,key,'bj');" style="float:right"title="趋势统计"></span> -->
                	<p class="xscz-ft-14">人均单笔消费（单位：元）</p>
                    <p><b class="xscz-ft-22"
                      ng-class="v.xfnl.ONE_MONEY>vm.items[0].all.all.xfnl.ONE_MONEY?'xscz-red':v.xfnl.ONE_MONEY==vm.items[0].all.all.xfnl.ONE_MONEY?'':'xscz-greener'">{{v.xfnl.ONE_MONEY|number:2}}</b></p>
                    <p class="height-31"ng-show="key=='all'"></p>
                       <p class="text-right xscz-ft-14 height-31" ng-show="key!='all'">相对整体<b class="xscz-ft-22"
                     ng-class="v.xfnl.ONE_MONEY>vm.items[0].all.all.xfnl.ONE_MONEY?'grow-up':v.xfnl.ONE_MONEY==vm.items[0].all.all.xfnl.ONE_MONEY?'':'grow-down'">
                     {{v.xfnl.ONE_MONEY>vm.items[0].all.all.xfnl.ONE_MONEY?((v.xfnl.ONE_MONEY-vm.items[0].all.all.xfnl.ONE_MONEY)|number:2):((vm.items[0].all.all.xfnl.ONE_MONEY-v.xfnl.ONE_MONEY)|number:2)}}</b></p>
                </li>
                <li class="text-left">
                <div tool-tip placement="top" hide-icon="true" class="icon-time"style="float:right" ng-click="qushiClick(k,key,'rj');">
									<div style="width: 60px">查看趋势</div>
								</div>
                 <!--  <span class="icon-time" ng-click="qushiClick(k,key,'rj');" style="float:right"title="趋势统计"></span> -->
                	<p class="xscz-ft-14">人均日消费（单位：元）</p>
                    <p><b class="xscz-ft-22" 
                    ng-class="v.xfnl.DAY_MONEY>vm.items[0].all.all.xfnl.DAY_MONEY?'xscz-red':v.xfnl.DAY_MONEY==vm.items[0].all.all.xfnl.DAY_MONEY?'':'xscz-greener'">{{v.xfnl.DAY_MONEY|number:2}}</b></p>
                    <p class="height-31"ng-show="key=='all'"></p>
                    <p class="text-right xscz-ft-14 height-31" ng-show="key!='all'">相对整体<b class="xscz-ft-22"
                     ng-class="v.xfnl.DAY_MONEY>vm.items[0].all.all.xfnl.DAY_MONEY?'grow-up':v.xfnl.DAY_MONEY==vm.items[0].all.all.xfnl.DAY_MONEY?'':'grow-down'">
                     {{v.xfnl.DAY_MONEY>vm.items[0].all.all.xfnl.DAY_MONEY?((v.xfnl.DAY_MONEY-vm.items[0].all.all.xfnl.DAY_MONEY)|number:2):((vm.items[0].all.all.xfnl.DAY_MONEY-v.xfnl.DAY_MONEY)|number:2)}}</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">消费组成(单位：元)</p>
                    <div class="text-center">
                     <div stu-chart config="v.xfzc" style="height:310px;"class="img-responsive img-top"> </div>
                		<%-- <img src="${images}/04.jpg" alt=""> --%>
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">日均消费区间（单位：人数）</p>
                    <div class="text-center">
                      <div stu-chart config="v.xfqj" style="height:310px;"class="img-responsive img-top"> </div>
                    	<%-- <img src="${images}/xf-03.png" alt=""> --%>
                    </div>
                </li>
            </ul>
            </div>
            <!--以上整体-->
<%--             <ul class="col-md-4 border-r-solid  has-btm-border  no-mar-btm">
            	<li class="tit-row">
                	<span class="tit-40 gender"><img src="${images}/gender-man.png" alt=""></span>
                    <span>男生</span>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">消费金额（单位：元）</p>
                    <p><b class="xscz-ft-22">20000</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">人均单笔消费（单位：元）</p>
                    <p><b class="xscz-ft-22  xscz-red">11.2</b></p>
                    <p class="text-right xscz-ft-14 height-31">相对整体<b class="xscz-ft-22 grow-up">2.3</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">人均日消费（单位：元）</p>
                    <p><b class="xscz-ft-22  xscz-red">21.2</b></p>
                    <p class="text-right xscz-ft-14 height-31">相对整体<b class="xscz-ft-22 grow-up">2.3</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">消费组成(单位：元)</p>
                    <div class="text-center">
                		<img src="${images}/04.jpg" alt="">
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">日均消费区间（单位：人数）</p>
                    <div class="text-center">
                    	<img src="${images}/xf-03.png" alt="">
                    </div>
                </li>
            </ul>
             <!--以上男生-->
            <ul class="col-md-4  has-btm-border  no-mar-btm">
            	<li class="tit-row">
                	<span class="tit-40 gender"><img src="${images}/gender-female.png" alt=""></span>
                    <span>女生</span>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">消费金额（单位：元）</p>
                    <p><b class="xscz-ft-22">4000</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">人均单笔消费（单位：元）</p>
                    <p><b class="xscz-ft-22  xscz-greener">6.3</b></p>
                    <p class="text-right xscz-ft-14 height-31">相对整体<b class="xscz-ft-22 grow-down">2.6</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">人均日消费（单位：元）</p>
                    <p><b class="xscz-ft-22   xscz-greener">16.3</b></p>
                    <p class="text-right xscz-ft-14 height-31">相对整体<b class="xscz-ft-22 grow-down">2.6</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">消费组成(单位：元)</p>
                    <div class="text-center">
                		<img src="${images}/04.jpg" alt="">
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">日均消费区间（单位：人数）</p>
                    <div class="text-center">
                    	<img src="${images}/xf-03.png" alt="">
                    </div>
                </li>
            </ul> --%>
             <!--以上女生-->
        </div>       
      </div>
      <!-- /* 统计*/-->
      <div class="xscz-tj">
      	<div class="xscz-cit text-center"><span>分学院学生消费能力分析</span></div>
        <div class="row text-center">
          <div class="col-xs-12">
             <div class="xscz-slt text-left btm-15">
             	<input class="xscz-radio" type="radio" id="rd-11" name="rd1" checked="" value="0" ng-model="radio1id">
                <label for="rd-11">总消费金额</label>
                <input class="xscz-radio" type="radio" name="rd1" id="rd-12"value="1" ng-model="radio1id">
                <label for="rd-12">人均日消费金额</label>
                <input class="xscz-radio" type="radio" name="rd1" id="rd-13"value="2" ng-model="radio1id">
                <label for="rd-13">人均单笔消费金额</label>
             </div>
                       <div stu-chart config="radio1data" style="height:310px;"class="img-responsive img-top"> </div>
           <%--   <img src="${images}/01.jpg">  --%>
          </div>  
        </div>
      </div>
      <!-- /* 统计*/-->
      <div class="xscz-tj">
      	<div class="xscz-cit text-center"><span>分学年学生消费能力分析</span></div>
        <div class="row text-center">
          <div class="col-xs-12">
          <div stu-chart config="vm.items[10]" style="height:310px;"class="img-responsive img-top"> </div>
                
            <%--  <img src="${images}/xfnl-img-06.png">  --%>
          </div>  
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
