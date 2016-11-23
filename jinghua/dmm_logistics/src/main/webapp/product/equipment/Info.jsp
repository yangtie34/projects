<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<meta charset="UTF-8">
<jsp:include page="../../static/base.jsp"></jsp:include>
<title>仪器设备概况统计</title>
<style>
            .cursordiv{
            cursor: pointer;
            }
            </style>
</head>
<body ng-controller="InfoController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include>  --%>
 <script type="text/javascript" src="${ctx }/product/equipment/js/Info.js"></script>   
 <div class="content">
        
        	<div class="xscz-head content_head">
                <h3 class="xscz-fff">仪器设备概况统计</h3> 
                <p class="xscz-default">对学校使用的仪器设备进行分析，包括总量、新增量、贵重设备情况和相关趋势的分析统计。(*注：仪器设备单价不下于{{gzbz/10000}}万以下值称谓为贵重设备)</p> 
            </div>
            
            <div class="content_main">
            
            	<div class="gaikuang_top_imges">
                	<div class="zaiyong_l fl">
                    	<h3>共计</h3>
                        <div class="yiqishebei fl">
                        	<div id="octagon">
                                <div id="liub" ng-class="emTypeId==0?'':'cursordiv'"ng-click="emTypeClick(0);titleFB='在用'"><h4>仪器设备</h4></div>
                                <ul class="ul_1">
                                    <li>共拥有</li>
                                    <li>总价值</li>
                                </ul>
                                <ul class="ul_2">
                                    <li><a class="colorr" href="" ng-click="getxq(0);">{{vm.items[0][0].NUMS}}</a>件</li>
                                    <li>{{vm.items[0][0].MONEYS}}万元</li>
                                </ul>
                            </div>
                        </div>
                        <div class="else fl">
                             <div id="liub"><p>其中</p></div>
                             <div class="clearfix"></div>
                    	</div>
                        <div class="guizhong fr">
                            <div id="octagon">
                                <div id="liub" ng-class="emTypeId==1?'':'cursordiv'"ng-click="emTypeClick(1);titleFB='在用贵重'"><h4>贵重设备</h4></div>
                                <ul class="ul_1">
                                    <li>共拥有</li>
                                    <li>总价值</li>
                                </ul>
                                <ul class="ul_2">
                                    <li><a class="colorr" href="" ng-click="getxq(1);">{{vm.items[0][1].NUMS}}</a><!-- ({{vm.items[0][1].NUMS*100/vm.items[0][0].NUMS | number :2}}%) -->件</li>
                                    <li>{{vm.items[0][1].MONEYS}}万<!-- ({{vm.items[0][1].MONEYS*100/vm.items[0][0].MONEYS | number :2}}%) -->元</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="xinzeng_r  fr">
                    	<h3>今年新增</h3>
                        <div class="yiqishebei fl">
                        	<div id="octagon">
                                <div id="liub" ng-class="emTypeId==2?'':'cursordiv'" ng-click="emTypeClick(2);titleFB='今年在用'"><h4>仪器设备</h4></div>
                                <ul class="ul_1">
                                	<li>&nbsp;</li>
                                    <li>数量</li>
                                    <li>价值</li>
                                </ul>
                                <ul class="ul_1 ul_m">
                                	<li class="norm" >新增</li>
                                    <li><a class="colorr" href="" ng-click="getxq(2);">{{vm.items[0][2].NUMS}}</a>件</li>
                                    <li>{{vm.items[0][2].MONEYS}}万元</li>
                                </ul>
                              <!--   <ul class="ul_2">
                                	<li class="norm">增长率</li>
                                    <li>{{vm.items[0][2].NUMS*100/vm.items[0][0].NUMS | number :2}}%</li>
                                    <li>{{vm.items[0][2].MONEYS*100/vm.items[0][0].MONEYS | number :2}}%</li>
                                </ul> -->
                            </div>
                        </div>
                        <div class="else fl">
                             <div id="liub"><p>其中</p></div>
                             <div class="clearfix"></div>
                    	</div>
                        <div class="guizhong fr">
                            <div id="octagon">
                                <div id="liub"ng-class="emTypeId==3?'':'cursordiv'" ng-click="emTypeClick(3);titleFB='今年在用贵重'"><h4>贵重设备</h4></div>
                                <ul class="ul_1">
                                    <li>共拥有</li>
                                    <li>总价值</li>
                                </ul>
                                <ul class="ul_2">
                                    <li><a class="colorr" href="" ng-click="getxq(3);">{{vm.items[0][3].NUMS}}</a><!-- ({{vm.items[0][3].NUMS*100/vm.items[0][0].NUMS | number :2}}%) -->件</li>
                                    <li>{{vm.items[0][3].MONEYS}}万<!-- ({{vm.items[0][3].MONEYS*100/vm.items[0][0].MONEYS | number :2}}%) -->元</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix"></div>
                </div>
               <!--------------------------------------------------- 以上为top-imges内容------------------------------------------------>
                <div class="fenbu">
                	<div class="t">
            			<h5>{{titleFB}}设备分布</h5>
                	</div>
                    <div class="fenbu_con gqzysb_con">
                    	<div class="col-md-4">
                        	<h4>设备类型对比<span class="icon-time"ng-click="qushiClick(0);"></span></h4>
                        	<div stu-chart config="vm.items[1][0]" style="height:310px;"class="img-responsive img-top"> </div>
                        	<%-- <img src="${images}/shebei_21.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <div class="col-md-4 fenbu_con_m">
                        	<h4>经费组成对比<span class="icon-time"ng-click="qushiClick(1);"></span></h4>
                        	<div stu-chart config="vm.items[1][1]" style="height:310px;"class="img-responsive img-top"> </div>
                            <%-- <img src="${images}/jingfei_22.jpg" class="img-responsive img-top"> --%>
                            <!-- <p><i class="icon-info-sign"></i>经费组成中<b>教育事业费</b>共计<b>500元</b>，占总经费<b>60%</b>，以上是<b>其余经费</b>对比。</p>
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

                <div class="guoqi pdbtm_box">
                	<div class="t">
            			<h5>仪器设备年度变化趋势</h5>
                	</div>
                    <div class="radio radio-inline radio-change radio-blue">
						<input type="radio" name="radio2" id="radio3" value="0" checked="" ng-model="radio1id">
                        <label for="radio3" class="fw-nm">
                          设备数量
                        </label>
                    </div>
                    <div class="radio radio-inline radio-change radio-blue">
                        <input type="radio" name="radio2" id="radio4" value="1" ng-model="radio1id">
                        <label for="radio4" class="fw-nm">
                          设备增长量
                        </label>
                    </div>
                    <div stu-chart config="radio1data" style="height:310px;"class="img-responsive img-top"> </div>
                   <%--  <img src="${images}/shebeinum.jpg" class="img-responsive img-top"> --%>
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