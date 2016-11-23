<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>学生上网预警</title>
</head>
<body ng-controller="netStuWarnController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/net/js/netStuWarn.js"></script>    
    
        <div class="content" >
       <div class="xscz-head content_head">
                <h3 class="xscz-fff">学生上网预警</h3> 
                <p class="xscz-default">对学校学生上网情况进行预警。</p> 
            </div>
         <div class="content_main">
            
            	<div class="jieyuepm_section">
                    	<div class="richangpm" ng-click="timeFlow('time');">
                        	<div class="fr">
                                <img src="${images}/books_images/border_blue_top.png" class="img-top" ng-show="netType=='time'">
                                <img src="${images}/border_15.png" class="img-top" ng-show="netType=='flow'">
                                <div ng-class="netType=='time'?'xuanzhong':'xuanzhong_no'" >
                                	<a href=""><div id="liub"><h4>上网时长</h4></div></a>
                                </div>
                                <img src="${images}/books_images/border_blue_bottom.png" class="img-top" ng-show="netType=='time'">
                                <img src="${images}/border_18.png" class="img-top" ng-show="netType=='flow'">
                                <p class="triangle_down" ng-show="netType=='time'"></p>
                            </div>      
                        </div>
                        <div class="niandupm" ng-click="timeFlow('flow');">
                        	<div class="fl">
                                 <img src="${images}/books_images/border_blue_top.png" class="img-top" ng-show="netType=='flow'">
                                <img src="${images}/border_15.png" class="img-top" ng-show="netType=='time'">
                                <div ng-class="netType=='time'?'xuanzhong_no':'xuanzhong'" >
                                	<a href=""><div id="liub"><h4>上网流量</h4></div></a>
                                </div>
                                   <img src="${images}/books_images/border_blue_bottom.png" class="img-top" ng-show="netType=='flow'">
                                <img src="${images}/border_18.png" class="img-top" ng-show="netType!='flow'">
                                 <p class="triangle_down" ng-show="netType=='flow'"></p>
                            </div>
                        </div>
                    </div>
                    <div >
<div class="booksjy_top_imges">
                	
                    <div class="clearfix"></div>
                     <div cg-combo-nyrtj result="date"></div>

                   
                    <div class="clearfix"></div>
                </div><!--------------------------------------------------- 以上为books-top-imges内容------------------------------------------------><div class="fenbu">
                	<div class="tusjy">
                        <div class="tusjy_con">
<span style="float:left">&nbsp;&nbsp;&nbsp;月度上网时长阈值：</span>
<input type="text" class="form-control" ng-model="value" ng-keyup="myKeyup($event)"style="
	float:left;
    width: 80px;
    height: 25px;
"><span style="float:left"> {{netType=='flow'?'MB':'分钟'}} &nbsp;</span>
<input type="button" class="" ng-click="sxreload();" value="计算"style="
							   float:left; width: 50px;height: 25px;">
 <div class="clearfix"></div>

                             
                             <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	
                             <div class="t"style="
						    text-align: left;
						    padding-bottom: 0px;
						">
            			<h4>学生预警名单</h4>
                	</div>
                                   <p class="pad-r-10 text-right"><a href="" ng-click="exportExcel();"title="导出列表"><img src="${images}/send.png" alt="">导出</a></p>
                                   <br>
                            <div class="tab">
                    			   	   <table class="table table-change"> 
                             <thead>
                                      <tr>
                                         <th></th>
                                         <th ng-repeat="item in titles">{{item}}</th>
                                      </tr>
                                   </thead>
                                   <tbody>
                                      <tr class="tab_dashed" ng-repeat="item in vm.items[0]">
                                         <td>{{$index+1}}</td>
                                         <th ng-repeat="ite in titlesCode">{{item[ite]}}</th>
                                      </tr>
                         
                                   </tbody>
                                </table>
                            </div>
                                  <!--分页-->
      		<div class="quanxian-pager"><span class="quanxian-pager-text-ccc">当前查询：共 {{page.totalPages}} 页，数据 {{page.totalRows}} 条</span>
		<div>
		<div class="table-info table-inline-block set-page-width fright">
				<div pagination total-items="page.totalRows" ng-model="page.currentPage" max-size="page.numPerPage" items-per-page="page.numPerPage"
				 class="pagination-sm pull-right" boundary-links="true"></div>
			</div>
			<div style="float: right;padding-top: 10px;" class="page_numPerPage">
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
                            <div class="clearfix"></div>
                        </div>
                    </div>
                <!------------------------------------------------- 以上图书借阅Top10 ---------------------------------------------->
                <div class="fenbu pdbtm_box">
                	<div class="t"style="
						    text-align: left;
						    padding-bottom: 0px;
						">
            			<h4>人员类型分布情况</h4>
                	</div>
                     <div class="fenbu_con rylxfb_con" >
                    	<div class="col-md-4" style="width:50%;">
                        	<h4>学历对比<span class="icon-time" ng-click="qushiClick(2);"></span></h4>
                        	<div stu-chart config="vm.items[1].xl" style="height:310px;"class="img-responsive img-top"> </div>
                        	
                        	<%-- <img src="${images}/books_images/rylxfb_lx.jpg" class="img-responsive img-top"> --%>
                        </div>
                        <div class="col-md-4 fenbu_con_m" style="width:50%;border-right: none;"   >
                        	<h4>性别对比<span class="icon-time" ng-click="qushiClick(4);"></span></h4>
                        	<div stu-chart config="vm.items[1].xb" style="height:310px; "class="img-responsive img-top"> </div>
                            <%-- <img src="${images}/books_images/rylxfb_xb.jpg" class="img-responsive img-top"> --%>
                        </div>
                      <%--    <div class="col-md-4">
                        	<h4>民族对比<span class="icon-time" ng-click="qushiClick(6);"></span></h4>
                        	<div stu-chart config="vm.items[1].allMz" style="height:310px;"class="img-responsive img-top"> </div>
                        	<img src="${images}/books_images/rylxfb_grade.jpg" class="img-responsive img-top">
                        </div>  --%>
                        <div class="clearfix"></div>
                    </div>
                     <div class="fenbu_con rylxfb_con">
                    <h4>民族对比<span class="icon-time" ng-click="qushiClick(8);"></span></h4>
                    <div stu-chart config="vm.items[1].allMz" style="height:310px; width:100%"class="img_marg img-top"> </div>
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