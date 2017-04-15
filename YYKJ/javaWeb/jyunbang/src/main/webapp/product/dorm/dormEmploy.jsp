<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>宿舍使用情况</title>
</head>
<body ng-controller="dormEmployController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/dorm/js/dormEmploy.js"></script>    
    
        <div class="content" >
   <div class="xscz-head content_head">
    <h3 class="xscz-fff">宿舍使用情况</h3>
    <p class="xscz-default">分析宿舍学生入住情况的相关信息</p>
  </div>
  <div class="xscz-box" style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	 
  
  <div>
     <div>
                    	<ul class="list-unstyled sushe-bed clearfix">
                        	<li>
                            	<div class="sushe-bed-head">宿舍楼数量</div>
                                <ul class="sushe-bed-body list-unstyled">
                                	<li class="sushe-fz-22"><a href="" class="colorr" ng-click="getxq('LY');">{{vm.items[0].LY_SUM}}</a></li>
                                    <li class="sushe-fz-16">栋</li>
                                </ul>
                            </li>
                            <li>
                            	<div class="sushe-bed-head bg-green-er">房间数量</div>
                                <ul class="sushe-bed-body list-unstyled">
                                	<li class="sushe-fz-22"><a href="" class="colorr" ng-click="getxq('QS');">{{vm.items[0].QS_SUM}}</a></li>
                                    <li class="sushe-fz-16">间</li>
                                </ul>
                            </li>
                            <li>
                            	<div class="sushe-bed-head bg-green-est">床位数</div>
                                <ul class="sushe-bed-body list-unstyled">
                                	<li class="sushe-fz-22"><a href="" class="colorr" ng-click="getxq('CW');">{{vm.items[0].CW_SUM}}</a></li>
                                    <li class="sushe-fz-16">床</li>
                                </ul>
                            </li>
                            <li>
                            	<div class="sushe-bed-head bg-org">空床位数</div>
                                <ul class="sushe-bed-body list-unstyled">
                                	<li class="sushe-fz-22"><a href="" class="colorr" ng-click="getxq('KCW');">{{vm.items[0].KCW_SUM}}</a></li>
                                    <li class="sushe-fz-16">床</li>
                                </ul>
                            </li>
                            <li>
                            	<div class="sushe-bed-head bg-red">入住率</div>
                                <ul class="sushe-bed-body list-unstyled">
                                	<li class="sushe-fz-22">{{vm.items[0].RZ_RATE}}</li>
                                    <li class="sushe-fz-16">%</li>
                                </ul>
                            </li>
                        </ul>
                    </div>       
            	
               
                <div class="fenbu">
                	<div class="tusjy">
                    	<!-- <div class="t">
                        </div> --><br><br>
                        <div class="tusjy_con">
			  <div class="suoshu_con  ">
    <h4 style="text-align: center;">学生入住情况<!-- <span class="icon-time"></span> --></h4>
    	<div stu-chart config="vm.items[1]" style="height:310px;"
									class="img-responsive img-top"></div>
                   <%--  <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
  </div>
    <div class="suoshu_con  ">
    <h4 style="text-align: center;">迎新可安排人数分布<!-- <span class="icon-time"></span> --></h4>
    	<div stu-chart config="vm.items[2]" style="height:310px;"
									class="img-responsive img-top"></div>
                   <%--  <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
  </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                <div class="fenbu pdbtm_box">
                	<div class="t">
            			<h5>人员分布情况</h5>
                	</div>
                     <div class="fenbu_con rylxfb">
                     <div class="col-md-4">
                        	<h4>按年级统计分布情况<!-- <span class="icon-time"></span> --></h4>
                        	<div stu-chart config="vm.items[3]['NJ']" style="height:310px;"
									class="img-responsive img-top"></div>
                        <%-- 	<img src="${images}/books_images/rylxfb_lx.jpg" class="img-responsive img-top"> --%>
                        </div>
                    	<div class="col-md-4 fenbu_con_m">
                        	<h4>按学历统计分布情况<!-- <span class="icon-time"></span> --></h4>
                        	<div stu-chart config="vm.items[3]['EDU']" style="height:310px;"
									class="img-responsive img-top"></div>
                        <%-- 	<img src="${images}/books_images/rylxfb_lx.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <div class="col-md-4 ">
                        	<h4>按性别统计分布情况<!-- <span class="icon-time"></span> --></h4>
                        	<div stu-chart config="vm.items[3]['SEX']" style="height:310px;"
									class="img-responsive img-top"></div>
                           <%--  <img src="${images}/books_images/rylxfb_xb.jpg" class="img-responsive img-top"> --%>
                        </div>

                        
                        <div class="clearfix"></div>
                    </div>
                    <hr>
  <div class="suoshu_con ">
    <h4 style="text-align: center;font-size: 16px;">按民族统计分布情况<!-- <span class="icon-time"></span> --></h4>
    	<div stu-chart config="vm.items[3]['MZ']" style="height:310px;"
									class="img-responsive img-top"></div>
                   <%--  <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
  </div>

                    <div class="clearfix"></div>
                </div></div>
                

                <div class="fenbu pdbtm_box">
                	<div class="t">
            			<h5>{{radio0=='DEPT'?'学院':radio0=='MAJOR'?'专业':'班级'}}分布情况</h5>
                	</div>
                     
  <div class="guoqi pdbtm_box">
   <input class="xscz-radio" type="radio" id="rd-1" name="rd0" value="DEPT" ng-model="radio0"><label for="rd-1">学院</label>
   <input class="xscz-radio" type="radio" name="rd0" id="rd-2" value="MAJOR" ng-model="radio0"><label for="rd-2">专业</label>
    <input class="xscz-radio" type="radio" name="rd0" id="rd-3" value="CLASS" ng-model="radio0"><label for="rd-3">班级</label></div>
            
    <div stu-chart config="vm.items[3][radio0]" style="height:310px;"
									class="img-responsive img-top"></div>
                   <%--  <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
  </div>

                    <div class="clearfix"></div>
                </div>
                
                
                
               
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