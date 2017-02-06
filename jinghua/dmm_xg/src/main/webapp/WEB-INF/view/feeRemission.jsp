<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="app">
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>学费减免</title>
	<jsp:include page="/static/base.jsp"></jsp:include>
	<script type="text/javascript" src="product/feeRemission/controller.js"></script>
	<script type="text/javascript" src="product/feeRemission/service.js"></script>
  </head>
  
<body ng-controller="controller">
<div class="xuegong-zhuti-content" ><!--part-modal show-modal="data.mask">-->
<div class="xuegong-zhuti-wrapper">
    <div class="xuegong-zhuti-content">
    	<header>
        	<div class="pull-right">
            	<a href="" ng-click="data.advance.show=!data.advance.show"><span></span><p>高级查询</p></a>
                <i></i>
                <a href="" class="tansuo disable"><span></span><p>探索</p></a>
                <i></i>
                <a href="" class="disable"><span></span><p>导出</p></a>
            </div>
            <div class="clearfix"></div>
        </header>
        <div class="xuegong-zhuti-content-main">
        <div ng-show="data.advance.show" class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source xueji-buliang-yidong">
			<div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" no-border="true"></div>
		</div>
 <!--         	<header>
            	<div class="xueyuan-head no-bottom-border">
            	郑州轻工业学院<span>-></span>计算机学院
                <a href="" class="pull-right down";><i class="fa fa-angle-down"></i></a>
                </div>
                <div class="xueyuan-section"></div>
            </header>  -->
            
            <div class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone scholarship tuition-waiver">
        <!-- 学年 -->
		<div class="xuegong-zhuti-dropdown-box">
			<div class="btn-group" cg-select data="data.bzdm_xn" on-change="changXn($value,$data)" ng-model="data.value_year" value-field="id" display-field="mc"></div>
		</div>
		<!-- 学历 -->
		<div class="xuegong-zhuti-dropdown-box">
			<div class="btn-group" cg-select data="data.bzdm_edu" on-change="changEdu($value,$data)" ng-model="data.value_edu"></div>
		</div>
		<div class="btn-basic">
			<a href="" ng-click="changXn(data.thisYear)" ng-class="{on:data.thisYear==data.value_year}" class="btn-blue">今年</a>
           	<a href="" ng-show="data.lastBt" ng-click="changXn(data.lastYear)" ng-class="{on:data.lastYear==data.value_year}" class="btn-blue">去年</a>
		</div> 
		<!-- ----------------------------------- -->
                <p>今年共有<a href="" ng-click="abstractDetailClick('rs','学费减免（人数）学生名单')"><b class="text-green">{{XfjmCfg.RS}}</b></a>人获得学费减免，共计<a href="" ng-click="abstractDetailClick('je','学费减免（金额）学生名单')"><b class="text-green">{{XfjmCfg.ZMONEY_ | number:0}}</b></a>元，人均<b class="text-green">{{ XfjmCfg.MONEY_}}</b>元，覆盖率<b class="text-green">{{XfjmCfg.FGL}}%</b></p> 
                <div class="clearfix"></div>
            </div> 
            <div class="xuegong-separate-tb scholarship-fenbu">
            	<div class="xuegong-zhuti-content-main-tit">
                	<b class="text-blue">学费减免分布</b>
                	<!-- 类型 -->
					<div class="xuegong-zhuti-dropdown-box">
						<div class="btn-group" cg-select data="data.bzdm_lx" on-change="changLx($value,$data)" ng-model="data.Type_lx"></div>
					</div>
 <!--                     <div class="xuegong-zhuti-dropdown-box">
                        <div class="btn-group">
                              <a type="button" class="btn btn-noradius btn-shadow in-con dropdown-toggle three-words" data-toggle="dropdown">{{xffbInfo}}</a>
                          <a type="button" class="btn btn-noradius dropdown-toggle drown-icon" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="caret carett"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                              </a>
                              
                              <ul class="dropdown-menu">
                                Dropdown menu links
                                <li role="presentation"><a ng-click="xffbType('rs',xffbInfo)">按人数</a></li>
                                <li role="presentation"><a ng-click="xffbType('je',xffbInfo)">按金额</a></li>
                                <li role="presentation"><a ng-click="xffbType('fg',xffbInfo)">按覆盖率</a></li>
                              </ul>
                        </div>
                    </div> -->
                </div>
                <div part-modal show-modal="data.mask[0]">
                    <!-- 学费减免分布图 -->
                <div class="xuegong-separate-con">
                	<div class="img-box"><div class="img-box-only">
               		<!--  <div echart config="data.deptData.option"></div> -->
                		 <div echart config="XffbCfg"></div></div></div>
                </div>
                </div>
            </div>
            
            <div class="xuegong-separate-tb scholarship-historical-change">
            	<div class="xuegong-zhuti-content-main-tit">
                	<b class="text-blue">历年学费减免变化</b>
                	 <!-- 类型 -->
					<div class="xuegong-zhuti-dropdown-box">
						<div class="btn-group" cg-select data="data.bzdm_lx" on-change="changYearLx($value,$data)" ng-model="data.Type_year"></div>
					</div>
<!--                     <div class="xuegong-zhuti-dropdown-box">
                        <div class="btn-group">
                              <a type="button" class="btn btn-noradius btn-shadow in-con dropdown-toggle three-words" data-toggle="dropdown">{{yearInfo}}</a>
                          <a type="button" class="btn btn-noradius dropdown-toggle drown-icon" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="caret carett"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                              </a>
                              <ul class="dropdown-menu">
                                Dropdown menu links
                                <li role="presentation"><a ng-click="changeType('rs',yearInfo)">按人数</a></li>
                                <li role="presentation"><a ng-click="changeType('je',yearInfo)">按金额</a></li>
                                <li role="presentation"><a ng-click="changeType('fg',yearInfo)">按覆盖率</a></li>
                              </ul>
                        </div>
                   </div> -->
                </div>
                 <!-- 历年学费减免分布图 -->
                  <div part-modal show-modal="data.mask[1]">
                <div class="xuegong-separate-con">
               		 <div echart config="yearXfCfg"></div>
                </div>
                </div>
            </div>
            
        </div>
    </div>
</div>
</div>
  <div modal-form config="data.abstract_detail.formConfig"></div>
  </body>
</html>
