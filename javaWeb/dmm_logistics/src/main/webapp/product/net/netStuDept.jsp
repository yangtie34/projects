<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static" />
<c:set var="images" value="${ctx }/resource/images"/>
<html ng-app="app">
<head>
<jsp:include page="../../static/base.jsp"></jsp:include>
<meta charset="UTF-8">
<title>各院系学生上网对比</title>
</head>
<body ng-controller="netStuDeptController">
<div id="wrapper">
<%-- <jsp:include page="../../product/left.jsp"></jsp:include> --%>
 <script type="text/javascript" src="${ctx }/product/net/js/netStuDept.js"></script>    
    
        <div class="content" >
   <div class="xscz-head content_head">
    <h3 class="xscz-fff">各院系学生上网对比</h3>
    <p class="xscz-default">通过各院系分析学生上网对比相关信息</p>
  </div>
  <div class="xscz-box" style="padding: 20px;
    margin: 0 auto;
    background: #fff;">
<div cg-combo-nyrtj result="date" yid=1></div>
  <div cg-mul-query-comm source="mutiSource" result="deptResult" noborder="true"></div>	 
  
  <div>
					<div class="fenbu pdbtm_box">
						<div class="t">
							<h5>学生上网人数对比</h5>
						</div>

						<div class="guoqi pdbtm_box">
							<div stu-chart config="vm.items[0]" style="height:310px;"
								class="img-responsive img-top"></div>
							<%--  <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
						</div>

						<div class="clearfix"></div>
					</div>
					
						<div class="fenbu pdbtm_box">
						<div class="t">
							<h5>学生上网时长流量对比</h5>
						</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio01" id="all" value="all" checked="" ng-model="radio1id">
                            <label for="all" class="fw-nm">总体</label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio01" id="one" value="one"ng-model="radio1id">
                            <label for="one" class="fw-nm">人均</label>
                        </div>
                    </div>
						<div class="guoqi pdbtm_box">
							<div stu-chart config="vm.items[1]" style="height:310px;"
								class="img-responsive img-top"></div>
							<%--  <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
						</div>

						<div class="clearfix"></div>
					</div>
					
					<div class="fenbu pdbtm_box">
						<div class="t">
							<h5>学生上网时间对比</h5>
						</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio02" id="on" value="on" checked="" ng-model="radio2id">
                            <label for="on" class="fw-nm">经常上线时间</label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio02" id="out" value="out"ng-model="radio2id">
                            <label for="out" class="fw-nm">经常下线时间</label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio02" id="in" value="in"ng-model="radio2id">
                            <label for="in" class="fw-nm">经常在线时间</label>
                        </div>
                    </div>
						<div class="guoqi pdbtm_box">
							<div stu-chart config="vm.items[2]" style="height:310px;"
								class="img-responsive img-top"></div>
							<%--  <img src="${images}/books_images/yqzssyxfb_sl.png" class="img_marg img-top"> --%>
						</div>

						<div class="clearfix"></div>
					</div>
	<div class="fenbu pdbtm_box">
						<div class="t">
							<h5>预警学生人数对比</h5>
						</div>
                    <div class="danxuan">
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio03" id="time" value="time" checked="" ng-model="radio3id">
                            <label for="time" class="fw-nm">按时长</label>
                        </div>
                        <div class="radio radio-inline radio-change radio-blue">
                            <input type="radio" name="radio03" id="flow" value="flow"ng-model="radio3id">
                            <label for="flow" class="fw-nm">按流量</label>
                        </div>
                    <!-- </div>
             <div class="danxuan"> -->
             <div >
              <span style="float:left">*月度上网{{radio3id=='time'?'时长':'流量'}}阈值：</span>
                          <input type="text" class="form-control" ng-model="radio3idval[radio3id]" ng-keyup="myKeyup($event)"style="
							   float:left; width: 80px;height: 25px;">
                           <span style="float:left"> {{radio3id=='time'?'分钟':'MB'}}&nbsp;</span>
                           <input type="button" class="" ng-click="radio3idvalClick();" value="计算"style="
							   float:left; width: 50px;height: 25px;">
                        </div>
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