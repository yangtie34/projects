<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>图书馆门禁分析</title>
</head>
<body ng-controller="LibraryRkeController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/bookRke/js/LibraryRke.js"></script>    
    
        <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">图书馆门禁分析</h3>
    <p class="xscz-default">统计分析图书馆日常出入频次的相关信息</p>
  </div>
   <div class="xscz-box" style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
<div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	 
  <div class="xscz-nav">
      <!-- /* 统计*/-->
      
      <div class="xscz-tj">
      	<div class="row  row-20 clearfix">
        	<div class="col-md-4"></div>
            <div class="col-md-8">
                <div class="xscz-cit text-center">
                    <span ng-class="xbxlcode=='xb'?active:'normal'" ng-click="xbxl('xb');"><a href="">按性别</a></span>
                    <span ng-class="xbxlcode=='xl'?active:'normal'" ng-click="xbxl('xl');"><a href="">按学历</a></span>
                    <span ng-class="xbxlcode=='mz'?active:'normal'" ng-click="xbxl('mz');"><a href="">按民族</a></span>
                </div>
            </div>
        </div>
        <div class="row row-20 text-center clearfix">
      	 <div ng-repeat="key in ['all',xbxlcode]">
  			<ul class="has-btm-border no-mar-btm" ng-class="xbxlclass" ng-class="key!='all'?'border-r-solid':''" ng-repeat="(k,v) in vm.items[0][key]" ng-show="v.show">
            	<li class="tit-row">
                	<span class="tit-40"><img src="${images}/gender-{{v.ico}}.png" alt=""></span>
                    <span>{{v.name}}</span>
                </li>
                <li class="text-left">
               		<span class="icon-time" ng-click="qushiClick(k,key,'all');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">出入总次数（单位：次）</p>
                    <p><b class="xscz-ft-22">{{v.ALL_COUNT}}</b></p>
                </li>
                <li class="text-left">
               		<span class="icon-time" ng-click="qushiClick(k,key,'avg');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">人均出入次数（单位：次）</p>
                	 <p><b class="xscz-ft-22" ng-class="key!='all'?v.AVG_COUNT>vm.items[0].all.all.AVG_COUNT?'xscz-red':'xscz-greener':''">{{v.AVG_COUNT}}</b></p>
                       <p class="height-31"ng-show="key=='all'"></p>
                     <p class="text-right xscz-ft-14 height-31" ng-show="key!='all'">相对整体
                     <b class="xscz-ft-22 " ng-class="v.AVG_COUNT>vm.items[0].all.all.AVG_COUNT?'grow-up':'grow-down'">{{v.AVG_COUNT>vm.items[0].all.all.AVG_COUNT?(v.AVG_COUNT-vm.items[0].all.all.AVG_COUNT):(-v.AVG_COUNT+vm.items[0].all.all.AVG_COUNT)|number:2}}</b>
                     </p>
                    <p class="height-31"></p>
                </li>
                <li class="text-left">
               		<span class="icon-time" ng-click="qushiClick(k,key,'rate');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">入馆率（单位：%）</p>
                	<p><b class="xscz-ft-22" ng-class="key!='all'?v.INRATE>vm.items[0].all.all.INRATE?'xscz-red':'xscz-greener':''">{{v.INRATE}}</b></p>
                	  <p class="height-31"ng-show="key=='all'"></p>
                     <p class="text-right xscz-ft-14 height-31" ng-show="key!='all'">相对整体
                     <b class="xscz-ft-22 " ng-class="v.INRATE>vm.items[0].all.all.INRATE?'grow-up':'grow-down'">{{v.INRATE>vm.items[0].all.all.INRATE?(v.INRATE-vm.items[0].all.all.INRATE):(-v.INRATE+vm.items[0].all.all.INRATE)|number:2}}</b>
                     </p>
                    <p class="height-31"></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分周次统计出入频次（单位：次数）</p>
                    <div class="text-center">
                		<div stu-chart config="v.week" style="height:310px;"class="img-responsive img-top"> </div>
                	<%-- <img src="${images}/xf-03.png" alt=""> --%>
                    </div>
                </li>
                 <li class="text-left">
                	<p class="xscz-ft-14">分时段统计出入频次（单位：次数）</p>
                    <div class="text-center">
                		<div stu-chart config="v.hour" style="height:310px;"class="img-responsive img-top"> </div>
                	<%-- <img src="${images}/xf-03.png" alt=""> --%>
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">入馆次数区间分布（单位：次数）</p>
                    <div class="text-center">
                    	<div stu-chart config="v.csqj" style="height:310px;"class="img-responsive img-top"> </div>
                <%-- 	<img src="${images}/xf-03.png" alt=""> --%>
                    </div>
                </li>
            </ul>
            </div>
           <%--  <!--以上整体-->
            <ul class="col-md-4 border-r-solid  has-btm-border  no-mar-btm">
            	<li class="tit-row">
                	<span class="tit-40 gender"><img src="${images}/gender-man.png" alt=""></span>
                    <span>男生</span>
                </li>
                <li class="text-left">
               		<span class="icon-time" ng-click="qushiClick(k,key,'all');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">出入总次数（单位：次）</p>
                    <p><b class="xscz-ft-22">20000</b></p>
                </li>
                <li class="text-left">
               		<span class="icon-time" ng-click="qushiClick(k,key,'all');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">人均出入次数（单位：次）</p>
                    <p><b class="xscz-ft-22  xscz-red">11.2</b></p>
                    <p class="text-right xscz-ft-14 height-31">相对整体<b class="xscz-ft-22 grow-up">2.3</b></p>
                </li>
                <li class="text-left">
               		<span class="icon-time" ng-click="qushiClick(k,key,'all');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">入馆率（单位：%）</p>
                    <p><b class="xscz-ft-22  xscz-red">21.2</b></p>
                    <p class="text-right xscz-ft-14 height-31">相对整体<b class="xscz-ft-22 grow-up">2.3</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分周次统计出入频次（单位：次数）</p>
                    <div class="text-center">
                		<img src="${images}/xf-03.png" alt="">
                    </div>
                </li>
                 <li class="text-left">
                	<p class="xscz-ft-14">分时段统计出入频次（单位：次数）</p>
                    <div class="text-center">
                		<img src="${images}/xf-03.png" alt="">
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">入馆次数区间分布（单位：次数）</p>
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
               		<span class="icon-time" ng-click="qushiClick(k,key,'all');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">出入总次数（单位：次）</p>
                    <p><b class="xscz-ft-22">4000</b></p>
                </li>
                <li class="text-left">
               		<span class="icon-time" ng-click="qushiClick(k,key,'all');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">人均出入次数（单位：次）</p>
                    <p><b class="xscz-ft-22  xscz-greener">6.3</b></p>
                    <p class="text-right xscz-ft-14 height-31">相对整体<b class="xscz-ft-22 grow-down">2.6</b></p>
                </li>
                <li class="text-left">
               		<span class="icon-time" ng-click="qushiClick(k,key,'all');" style="float:right" title="趋势统计"></span>
                	<p class="xscz-ft-14">入馆率（单位：%）</p>
                    <p><b class="xscz-ft-22   xscz-greener">16.3</b></p>
                    <p class="text-right xscz-ft-14 height-31">相对整体<b class="xscz-ft-22 grow-down">2.6</b></p>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">分周次统计出入频次（单位：次数）</p>
                    <div class="text-center">
                		<img src="${images}/xf-03.png" alt="">
                    </div>
                </li>
                 <li class="text-left">
                	<p class="xscz-ft-14">分时段统计出入频次（单位：次数）</p>
                    <div class="text-center">
                		<img src="${images}/xf-03.png" alt="">
                    </div>
                </li>
                <li class="text-left">
                	<p class="xscz-ft-14">入馆次数区间分布（单位：次数）</p>
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
      	<div class="xscz-cit text-center"><span>分{{deptlname}}学生出入对比分析</span></div>
        <div class="row text-center">
          <div class="col-xs-12">
             <div class="xscz-slt text-left btm-15">
             	<input class="xscz-radio" type="radio" id="rd-11" name="rd1" value="0" ng-model="radio1id">
                <label for="rd-11">总出入次数</label>
                <input class="xscz-radio" type="radio" id="rd-12" name="rd1" value="1" ng-model="radio1id">
                <label for="rd-12">人均出入次数</label>
                <input class="xscz-radio" type="radio" id="rd-13" name="rd1" value="2" ng-model="radio1id">
                <label for="rd-13">入馆率(%)</label>
             </div>
               <div stu-chart config="radio1data" style="height:310px;"class="img-responsive img-top"> </div>
            <%--  <img src="${images}/01.jpg">  --%>
          </div>  
        </div>
      </div>
      <!-- /* 统计*/-->
      <div class="xscz-tj">
      	<div class="xscz-cit text-center"><span>分学年学生出入对比分析</span></div>
        <div class="row text-center">
          <div class="col-xs-12">
          <div stu-chart config="vm.items[2]" style="height:310px;"class="img-responsive img-top"> </div>
            <%--  <img src="${images}/xfnl-img-06.png">  --%>
          </div>  
        </div>
      </div>
      <!-- /* 统计*/-->
    </div>
    
  </div>            	
        </div>
        <div class="clearfix"></div>
    </div>
</body>
 <div cs-window show="qsDiv" autoCenter="true" offset="offset" title="'趋势详情'" >
 <div stu-chart config="qushiData" class="qsdivc" ></div>
 </div>
  <div cg-combo-xz data="pagexq" type=''></div>
</html>