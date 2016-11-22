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
    <title>一卡通使用状况分析</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
			
  </head>
  
<body ng-controller="cardEmployController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/card/js/cardEmploy.js"></script>   
  <div class="content" >
  <div class="xscz-head content_head">
    <h3 class="xscz-fff">一卡通使用状况分析</h3>
    <p class="xscz-default">对学校学生消费习惯进行分析。(注：月刷卡次数不下于${cardUsed }称谓为：活跃)</p>
  </div>
  
<div class="xscz-box" style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
 <!--   <div cg-report-table resource="tableData"></div> -->
<div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	 
    
    <div class="xscz-nav">
      <!-- /* 统计*/-->
      
      <div class="xscz-tj">
        <div class="xscz-cit text-center"><span>一卡通学生活跃度分析</span></div>
        <div class="row text-center clearfix mar-btm-40">
				<div class="col-xs-2 wid-22 border-r-dash">
                	<ul class="list-unstyled no-mar-btm">
                    	<li class="height-50"><img src="${images}/zk-fx.png" alt=""></li>
                        <li class="xscz-green text-margin">
                        	<span class="xscz-ft-14">总体</span>
                            <span class="xscz-ft-22">{{vm.items[0].USE_RATE}}%</span>({{vm.items[0].USE_NUM}}人)<!-- {{vm.items[0].ALL_NUM}} -->
                        </li>
                        <li><a href="" ng-click="qushiClick('all','use_all');"><img src="${images}/clock.png" alt=""></a></li>
                    </ul>
                </div>
                <div class="col-xs-6 wid-52 border-r-dash">
                	<div class="row clearfix">
                    	<div ng-class="'col-xs-'+12/vm.items[2].length" ng-repeat="item in vm.items[2]">
                        	<ul class="list-unstyled no-mar-btm">
                                <li class="height-50"><img ng-src="${images}/grade-0{{$index+1}}.png" alt=""></li>
                                <li class="xscz-black-gray text-margin">
                                    <span class="xscz-ft-14">{{item.TYPE_NAME}}</span>
                                    <span class="xscz-ft-22">{{item.USE_RATE}}%</span>({{item.USE_NUM}}人)<!-- {{item.ALL_NUM}} -->
                                </li>
                                <li><a href="" ng-click="qushiClick(item.TYPE_CODE,'use_edu');"><img src="${images}/clock.png" alt=""></a></li>
                            </ul>
                        </div>
                    
                    </div>
                </div>
                <div class="col-xs-3 wid-26">
                	<div class="row clearfix">
                    	<div class="col-xs-6" ng-repeat="item in vm.items[1]">
                        	<ul class="list-unstyled no-mar-btm">
                                <li class="height-50 gender"><img ng-src="${images}/gender-{{item.TYPE_CODE=='1'?'man':'female'}}.png" alt=""></li>
                                <li class="xscz-blue-sky text-margin">
                                    <span class="xscz-ft-14">{{item.TYPE_NAME}}</span>
                                    <span class="xscz-ft-22">{{item.USE_RATE}}%</span>({{item.USE_NUM}}人)
                                </li>
                                <li><a href="" ng-click="qushiClick(item.TYPE_CODE,'use_sex');"><img src="${images}/clock.png" alt=""></a></li>
                            </ul>
                        </div>
                       
                    </div>
                </div> 
        </div>     
        <div class="row text-center">
        	<h4 class="xscz-ft-16">各个{{deptlname}}使用率情况</h4>
          	<div class="col-xs-12">
          	  <div stu-chart config="vm.items[3]" style="height:310px;"class="img-responsive img-top"> </div>
            <%--  <img src="${images}/01.jpg">  --%>
          </div> 
        </div>
      </div>
      <!-- /* 统计*/-->
	  <div class="xscz-tj">
	  	<div class="xscz-cit text-center"><span>一卡通非活跃学生名单</span></div>
      	<p class="pad-r-10 text-right"><a href="" ng-click="exportExcel();"title="导出列表"><img src="${images}/send.png" alt="">导出</a></p>
        <div class="row clearfix">
        	<div class="col-xs-12">
            	<table class="table text-center xscz-ft-14">
                	<thead>
                    	<tr>
                    	<td></td>
                        	<td>学号</td>
                            <td>姓名</td>
                            <td>性别</td>
                            <td>所属学院</td>
                            <td>专业</td>
                            <td>班级</td>
                            <td>刷卡次数</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr ng-repeat="item in vm.items[4]">
                    	<!-- <td class="xscz-yellow">{{item.STU_ID}}</td> -->
                    	<td>{{$index+1}}</td>
                        	<td>{{item.STU_ID}}</td>
                            <td>{{item.STU_NAME}}</td>
                            <td>{{item.SEX_NAME}}</td>
                            <td>{{item.DEPT_NAME}}</td>
                            <td>{{item.MAJOR_NAME}}</td>
                            <td>{{item.CLASS_NAME}}</td>
                            <td>{{item.PAY_COUNT}}</td>
                        </tr>
                    </tbody>
                </table>
                 <!--分页-->
      		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">当前查询：共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 23px;" class="page_numPerPage">
				<select ng-model="page.numPerPage" style="border: 1px solid #DDD;"><option
						value="5">5</option>
					<option value="10">10</option>
					<option value="20">20</option>
					<option value="50">50</option>
				</select> / 每页
			</div>
			<div style="clear: both;"></div>
			</div>
      	</div> 
<!--分页-->
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
