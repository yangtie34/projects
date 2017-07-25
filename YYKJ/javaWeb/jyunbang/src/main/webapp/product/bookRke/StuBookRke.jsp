<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>学生出入图书馆分析</title>
<link rel='stylesheet'
	href='${ctx}/static/framework/bootstrap-table-demo/bootstrap-table/bootstrap-table.css' />
<script
	src='${ctx}/static/framework/bootstrap-table-demo/bootstrap-table/bootstrap-table.js'></script>
<script
	src='${ctx}/static/framework/bootstrap-table-demo/bootstrap-table/bootstrap-table-export.js'></script>
<script
	src='${ctx}/static/framework/bootstrap-table-demo/extends/tableExport/jquery.base64.js'></script>
<script
	src='${ctx}/static/framework/bootstrap-table-demo/extends/tableExport/tableExport.js'></script>
</head>
<body ng-controller="StuBookRkeController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/bookRke/js/StuBookRke.js"></script>    
    
        <div class="content" >
   <div class="xscz-head content_head">
    <h3 class="xscz-fff">学生出入图书馆分析</h3>
    <p class="xscz-default">通过图书馆门禁信息分析学生出入相关信息</p>
  </div>
  <div class="xscz-box" style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
<div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	 
  
  <div>
            
            	
               
                <div class="fenbu">
                	<div class="tusjy">
                    	<div class="t">
                        	<h5>学生出入图书馆活跃排名top{{pmInt}}</h5>
                        </div>
                        <div class="tusjy_con">
					<div cg-report-table resource="tableData"
							class=" xscz-ft-18"></div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                <div class="fenbu pdbtm_box">
                	<div class="t">
            			<h5>活跃学生分布情况</h5>
                	</div>
                     <div class="fenbu_con rylxfb">
                    	<div class="col-md-4">
                        	<h4>按学历统计人数比例<!-- <span class="icon-time"></span> --></h4>
                        	<div stu-chart config="vm.items[1]['xl']" style="height:310px;"
									class="img-responsive img-top"></div>
                        <%-- 	<img src="${images}/books_images/rylxfb_lx.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <div class="col-md-4 fenbu_con_m">
                        	<h4>按性别统计人数比例<!-- <span class="icon-time"></span> --></h4>
                        	<div stu-chart config="vm.items[1]['xb']" style="height:310px;"
									class="img-responsive img-top"></div>
                           <%--  <img src="${images}/books_images/rylxfb_xb.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <div class="col-md-4">
                        	<h4>按民族统计人数比例 <!-- <span class="icon-time"></span> --> </h4>
                        	<div stu-chart config="vm.items[1]['mz']" style="height:310px;"
									class="img-responsive img-top"></div>
                        	<%-- <img src="${images}/books_images/rylxfb_grade.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                    <hr>
  <div class="suoshu_con  ">
    <h4 style="text-align: center;">活跃用户按所属{{deptlname}}对比<!-- <span class="icon-time"></span> --></h4>
    	<div stu-chart config="vm.items[2]" style="height:310px;"
									class="img-responsive img-top"></div>
                   <%--  <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
  </div>

                    <div class="clearfix"></div>
                </div></div>
                

                <div class="fenbu pdbtm_box">
                	<div class="t">
            			<h5>非活跃用户按所属{{deptlname}}对比</h5>
                	</div>
                     
  <div class="guoqi pdbtm_box">
    <div stu-chart config="vm.items[3]" style="height:310px;"
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