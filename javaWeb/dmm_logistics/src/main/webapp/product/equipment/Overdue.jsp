<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>仪器设备过期情况分析</title>
 <style>
            .cursordiv{
            cursor: pointer;
            }
            </style>
</head>
<body ng-controller="OverdueController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/equipment/js/Overdue.js"></script>    
<div class="content">
        
        	<div class="xscz-head content_head">
                <h3 class="xscz-fff">仪器设备过期在用情况</h3> 
                <p class="xscz-default">对即将过期的设备进行分类展示。(*注：仪器设备单价不下于{{gzbz/10000}}万以下值称谓为贵重设备)</p> 
            </div>
            
            <div class="content_main">
            	<div class="top_imges">
                	<div class="left">
                    	<div class="fl" ng-class="emTypeIndex==0?'':'cursordiv'"ng-click="emTypeClick(0);">
                        	<img src="${images}/001_03.jpg" width="211" height="222" class="img-top">
                            <p>过期在用设备</p>
                        </div>
                        <ul class="fl">
                        	<li>数量</li>
                            <li class="m"><a class="colorr" href="" ng-click="getxq(0);">{{vm.items[0][0].NUMS}}</a></li>
                            <li>件</li>
                        </ul>
                        <ul class="fl">
                        	<li>价值</li>
                            <li class="m">{{vm.items[0][0].MONEYS}}</li>
                            <li>万元</li>
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                    <div class="else fl">
                    	 <div id="liub"><p>其中</p></div>
                         <div class="clearfix"></div>
                    </div>
                    <div class="rig fl">
                    	<div class="guizhong fl">
                            <div id="octagon">
                                <div id="liub" ng-class="emTypeIndex==1?'':'cursordiv'"ng-click="emTypeClick(1);"><h4>贵重设备</h4></div>
                                <ul class="ul_1">
                                    <li>共拥有</li>
                                    <li>总价值</li>
                                </ul>
                                <ul class="ul_2">
                                    <li><a class="colorr" href="" ng-click="getxq(1);">{{vm.items[0][1].NUMS}}</a><!-- ({{vm.items[0][1].NUMS*100/vm.items[0][0].NUMS | number :2}}%) -->件</li>
                                    <li>{{vm.items[0][1].MONEYS}}万<!-- (({{vm.items[0][1].MONEYS*100/vm.items[0][0].MONEYS | number :2}}%)) -->元</li>
                                </ul>
                            </div>
                        </div>
              <!--           <div class="fl add">+</div>
                        <div class="daxing">
                            <div id="octagon">
                                <div id="liub" ng-class="emTypeIndex==2?'':'cursordiv'"ng-click="emTypeClick(2);"><h4>大型设备</h4></div>
                                <ul class="ul_1">
                                    <li>共拥有</li>
                                    <li>总价值</li>
                                </ul>
                                <ul class="ul_2">
                                    <li><a class="colorr" href="" ng-click="getxq(2);">{{vm.items[0][2].NUMS}}</a>({{vm.items[0][2].NUMS*100/vm.items[0][0].NUMS | number :2}}%)件</li>
                                    <li>{{vm.items[0][2].MONEYS}}万(({{vm.items[0][2].MONEYS*100/vm.items[0][0].MONEYS | number :2}}%))元</li>
                                </ul>
                            </div>
                        </div> -->
                    	<div class="clearfix"></div>
                    </div>
                    <div class="clearfix"></div>
                </div>
               <!--------------------------------------------------- 以上为top-imges内容------------------------------------------------>
                <div class="fenbu">
                	<div class="t">
            			<h5>过期{{alltitle}}设备分布</h5>
                	</div>
                    <div class="fenbu_con gqzysb_con">
                    	<div class="col-md-4">
                        	<h4>设备类型对比<span class="icon-time"ng-click="qushiClick(0);"></span></h4>
                        	<div stu-chart config="vm.items[1][0]" style="height:310px;"class="img-responsive img-top"> </div>
                        	
                        <%-- 	<img src="${images}/shebei_21.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <div class="col-md-4 fenbu_con_m">
                        	<h4>经费组成对比<span class="icon-time"ng-click="qushiClick(1);"></span></h4>
                            <div stu-chart config="vm.items[1][1]" style="height:310px;"class="img-responsive img-top"> </div>
                        	
                           <%--  <img src="${images}/jingfei_22.jpg" class="img-responsive img-top"> --%>
                           <!--  <p><i class="icon-info-sign"></i>经费组成中<b>教育事业费</b>共计<b>500元</b>，占总经费<b>60%</b>，以上是<b>其余经费</b>对比。</p>
                        --> </div>
                        <div class="col-md-4">
                        	<h4>设备单价对比<span class="icon-time"ng-click="qushiClick(2);"></span></h4>
                        	<div stu-chart config="vm.items[1][2]" style="height:310px;"class="img-responsive img-top"> </div>
                        	
                        	<%-- <img src="${images}/shebeidanjia_24.gif" class="img-responsive img-top"> --%>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
                <!------------------------------------------------- 以上过期在用设备分布情况---------------------------------------------->
                <div class="suoshu">
                	<div class="t">
            			<h5>过期{{alltitle}}设备所属</h5>
                	</div>
                    <div class="suoshu_con">
                    	<div class="suoshu_section">
                        	<div class="col-md-3" style="width:20%" ng-repeat="item in vm.items[3]"
                        	ng-class="emTypeIndex==0?'':'cursordiv'"ng-click="DeptGroupClick(item);">
                                <img src="${images}/border_15.png" class="img-top">
                                <div class="xz_con">
                                    <ul class="ul_1">
                                        <li>过期</li>
                                        <li>价值</li>
                                    </ul>
                                    <ul class="ul_2 text-right">
                                        <li><a href="" class="colorr" ng-click="getEmDetilByDeptGroup(item.CODE);">{{item.NUMS}}</a>件</li>
                                        <li>{{item.MONEYS}}万元</li>
                                    </ul>
                                </div>
                                <img src="${images}/border_18.png" class="mtfw img-top">
                                <a href=""><div id="liub" ng-class="DeptGroup==item.CODE?'xuanzhong':''">
                                <h4>{{item.NAME}}</h4></div></a>
                            </div>
                 <%--            <div class="col-md-3">
                                <img src="${images}/border_15.png" class="img-top">
                                <div class="xz_con">
                                    <ul class="ul_1">
                                        <li>过期</li>
                                        <li>价值</li>
                                    </ul>
                                    <ul class="ul_2 text-right">
                                        <li><a class="colorr" href="">10</a>件</li>
                                        <li>6000元</li>
                                    </ul>
                                </div>
                                <img src="${images}/border_18.png" class="mtfw img-top">
                                <a href=""><div id="liub"><h4>行政机构</h4></div></a>
                            </div>
                            <div class="col-md-3">
                                <img src="${images}/border_15.png" class="img-top">
                                <div class="xz_con">
                                    <ul class="ul_1">
                                        <li>过期</li>
                                        <li>价值</li>
                                    </ul>
                                    <ul class="ul_2 text-right">
                                        <li><a class="colorr" href="">30</a>件</li>
                                        <li>30000元</li>
                                    </ul>
                                </div>
                                <img src="${images}/border_18.png" class="mtfw img-top">
                                <a href=""><div id="liub"><h4>科研机构</h4></div></a>
                            </div>
                            <div class="col-md-3">
                                <img src="${images}/border_15.png" class="img-top">
                                <div class="xz_con">
                                    <ul class="ul_1">
                                        <li>过期</li>
                                        <li>价值</li>
                                    </ul>
                                    <ul class="ul_2 text-right">
                                        <li><a class="colorr" href="">10</a>件</li>
                                        <li>8000元</li>
                                    </ul>
                                </div>
                                <img src="${images}/border_18.png" class="mtfw img-top">
                                <a href=""><div id="liub"><h4>其他机构</h4></div></a>
                            </div>  --%>                  
                        </div>
                        <div class="clearfix"></div>
                        <div class="jiaoxue">
                    		<h5>{{jgmc}}过期{{alltitle}}设备对比<!-- <span class="icon-time"></span> --></h5>
                    		<div stu-chart config="vm.items[4]" style="height:310px;"class="img-responsive img-top"> </div>
                            
                       <%--  <img src="${images}/tubiao_18.png" class="img-responsive img-top"> --%>
                    </div>
                    </div>
                </div>
                <!------------------------------------------------- 以上过期在用设备所属情况---------------------------------------------->
                <div class="guoqi pdbtm_box">
                	<div class="t">
            			<h5>仪器设备超期服役年限统计</h5>
                	</div>
                	<div stu-chart config="vm.items[5]" style="height:310px;"class="img-responsive img-top"> </div>
                            
                   <%--  <img src="${images}/tubiao_21.png" class="img-responsive img-top"> --%>
                    <div class="clearfix"></div>
                    </div>
                <!--------------------------------------------------以上仪器设备距离过期情况---------------------------------------------->
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