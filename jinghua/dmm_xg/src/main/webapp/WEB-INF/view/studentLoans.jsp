<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="app">
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>助学贷款</title>
	<jsp:include page="/static/base.jsp"></jsp:include>
	<script type="text/javascript" src="product/studentLoans/controller.js"></script>
	<script type="text/javascript" src="product/studentLoans/service.js"></script>
	<!-- <script type="text/javascript" src="product/studentLoans/common.js"></script> -->
  </head>
  
  <body ng-controller="controller">
  <div class="xuegong-zhuti-content" >
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
            </header> -->
            
            <div class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone scholarship">
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
                <div class="clearfix"></div> 
            </div>
            
        	<div class="xuegong-border student-loan-overview">
            	<div class="pull-left student-loan-overview-l">
                	<p>今年共有<a href="" ng-click="abstractDetailClick('rs','助学贷款（人数）学生名单')"><b class="text-green">{{zxdkCfg.zt.RS}}</b></a>人获得助学贷款，共计<a href="" ng-click="abstractDetailClick('je','助学贷款（金额）学生名单')"><b class="text-green">{{zxdkCfg.zt.ZMONEY_ | number:0}}</b></a>元，覆盖率<b class="text-green">{{zxdkCfg.zt.FGL}} %</b></p>
                    <div class="student-loan-overview-text" ng-repeat="item in zxdkCfg.fl">
                        <div class="student-loan-text" ng-show="$index%2==0">
                        <div class="img-box"><img src="static/resource/images/scholarship/student-loan-national-img.jpg" class="center-block"></div>
                    <!--     	<div class="row">
                            	<ul class="list-unstyled tab-color-green">
                                	<li class="col-md-4 text-center" style="height:60px;
                          line-height:40px;">{{item.NAME_}}</li>
                                    <li class="col-md-4 text-left" >人数： <span ng-click="abstractDetailClick('xrs',zxdkCfg.fl[$index].NAME_)" class="cursor-hand">{{item.RS}}</span>人<br>占比：{{item.ZB}}%</li>
                                    <li class="col-md-4 text-left">人均：{{item.AVG_ | number:2}}元<br>覆盖率：{{item.FGL}}%</li>
                                    <div class="clearfix"></div>
                                </ul>
                            </div> -->
                            <div class="row">
                                <div class="scholarship-top-overview">
                                    <div class="scholarship-top-overview-table">
                                        <table class="table tab-many">
                                            <tbody>
                                                <tr class="tab-color-green">
                                                    <td style="width:30%">{{item.NAME_}}</td>
                                                    <td>人数：<span ng-click="abstractDetailClick('xrs',zxdkCfg.fl[$index].NAME_)" class="cursor-hand">{{item.RS}}</span>人<br>占比：{{item.ZB}}%</td>
                                                    <td class="text-left">人均：{{item.AVG_ | number:2}}元<br>覆盖率：{{item.FGL}}%</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div><!--row-->
                        </div>
                    	<div class="student-loan-text" ng-show="$index%2==1">
							<div class="img-box"><img src="static/resource/images/scholarship/student-loan-source-img.jpg" class="center-block"></div>
                        	<!-- <div class="row"> 
                            	<ul class="list-unstyled tab-color-purple">
                                	<li class="col-md-4 text-center"  style="height:60px;
                          line-height:40px;">{{item.NAME_}}</li>标题中需要换行
                                    <li class="col-md-4 text-left">人数：<span ng-click="abstractDetailClick('xrs',zxdkCfg.fl[$index].NAME_)" class="cursor-hand">{{item.RS}}</span> 人<br>占比：{{item.ZB}}%</li>
                                    <li class="col-md-4 text-left">人均：{{item.AVG_ | number:2}}元<br>覆盖率：{{item.FGL}}%</li>
                                    <div class="clearfix"></div>
                                </ul>
                            </div> -->
                            <div class="row">
                                <div class="scholarship-top-overview">
                                    <div class="scholarship-top-overview-table">
                                        <table class="table tab-many">
                                            <tbody>
                                                <tr class="tab-color-purple">
                                                    <td style="width:30%">{{item.NAME_}}</td>
                                                    <td>人数：<span ng-click="abstractDetailClick('xrs',zxdkCfg.fl[$index].NAME_)" class="cursor-hand">{{item.RS}}</span>人<br>占比：{{item.ZB}}%</td>
                                                    <td class="text-left">人均：{{item.AVG_ | number:2}}元<br>覆盖率：{{item.FGL}}%</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div><!--row-->
                        </div> 
                    </div>
                </div>
                <div class="pull-left student-loan-overview-r">
                <p class="text-blue tit-second"><b>助学贷款学生行为</b></p>
	        	<div class="img-box"><div class="img-box-only">
	            	<div style="height:310px;" echart config="data.behavior.radar"></div>
            	</div></div>
     <!--            	 <p class="text-blue">助学金贷款学生行为</p>
                    <div class="img-box"><div echart config="zxxwCfg" ></div></div> -->
                </div>
                <div class="clearfix"></div>
            </div>
            
            <div class="xuegong-separate-tb scholarship-fenbu">
            	<div class="xuegong-zhuti-content-main-tit">
                	<b class="text-blue">助学贷款分布</b>
                	 <!-- 类型 -->
					<div class="xuegong-zhuti-dropdown-box">
						<div class="btn-group" cg-select data="data.bzdm_lx" on-change="changLx($value,$data)" ng-model="data.Type_lx"></div>
					</div>
<!--                      <div class="xuegong-zhuti-dropdown-box">
                        <div class="btn-group">
                              <a type="button" class="btn btn-noradius btn-shadow dropdown-toggle in-con" data-toggle="dropdown">{{zxfbInfo}}</a>
                              <a type="button" class="btn btn-noradius dropdown-toggle drown-icon" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="caret carett"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                              </a>
                              <ul class="dropdown-menu">
                                Dropdown menu links
                                <li role="presentation"><a ng-click="ZxfbType('rs',zxfbInfo)">按人数</a></li>
                                <li role="presentation"><a ng-click="ZxfbType('je',zxfbInfo)">按金额</a></li>
                                <li role="presentation"><a ng-click="ZxfbType('fg',zxfbInfo)">按覆盖率</a></li>
                              </ul>
                        </div>
                    </div> -->
                </div>
                 <div part-modal show-modal="data.mask[0]">
                <!--助学贷款分布图 -->
                <div class="xuegong-separate-con">
         			<div class="img-box"><div class="img-box-only"><div echart config="ZxfbCfg"></div></div></div>
                </div>
                </div>
            </div>
            
            <div class="xuegong-separate-tb scholarship-historical-change">
            	<div class="xuegong-zhuti-content-main-tit">
                	<b class="text-blue">历年助学贷款变化</b>
                	 <!-- 类型 -->
					<div class="xuegong-zhuti-dropdown-box">
						<div class="btn-group" cg-select data="data.bzdm_lx" on-change="changYearLx($value,$data)" ng-model="data.Type_year"></div>
					</div>
<!--                     <div class="xuegong-zhuti-dropdown-box">
                        <div class="btn-group">
                              <a type="button" class="btn btn-noradius btn-shadow dropdown-toggle in-con" data-toggle="dropdown">{{yearInfo}}</a>
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
                 <div part-modal show-modal="data.mask[1]">
              <!-- 历年助学贷款分布图 -->
                <div class="xuegong-separate-con">
            		<div class="img-box no-top"><div  echart config="yearZxCfg"></div></div>
                </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
 <!--  <h5>今年共有 {{zxdkCfg.zt.RS}}人获得学费减免，共计{{zxdkCfg.zt.ZMONEY_}}元，人均{{ zxdkCfg.zt.MONEY_}} 元，覆盖率 {{zxdkCfg.zt.FGL}} %</h5>
  <h5 ng-repeat="item in zxdkCfg.fl">{{item.NAME_}}&nbsp;&nbsp;共{{item.RS}}人，人均{{item.AVG_}}，占比{{item.ZB}},覆盖率{{item.FGL}}</h5>
  <hr>
  <h3>助学贷款学生行为</h3>
  <div echart config="zxxwCfg" ></div>
  <hr>
  <h2>助学贷款分布</h2><br>
  <button ng-click="ZxfbType('rs')">按人数</button>
  <button ng-click="ZxfbType('je')">按金额</button>
  <button ng-click="ZxfbType('fg')">按覆盖率</button>
  <button ng-click="ZxfbType('kz')">清空</button>
  <div echart config="ZxfbCfg"></div>
  <hr>
  <h2>历年助学贷款变化</h2><br>
  <button ng-click="changeType('rs')">按人数</button>
  <button ng-click="changeType('je')">按金额</button>
  <button ng-click="changeType('fg')">按覆盖率</button>
  <button ng-click="changeType('kz')">清空</button>
  <div echart config="yearZxCfg"></div> -->
  <div modal-form config="data.abstract_detail.formConfig"></div>
     <%-- 学生 详情 --%>
	<!-- <div cs-window show="data.abstract_detail.detail_show" auto-center="true" style="padding: 0;"> click-disappear="true"
		<div class="popup-form popup-form-blue" style="margin:0px">
		    <div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.abstract_detail.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.abstract_detail.detail_show=false">
		        	<img src="static/resource/css/image/popup-form-close.png" alt="">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix" part-modal show-modal="data.abstract_detail.mask">
		    	<div class="popup-form-con clearfix">
		            <div class="pull-right" ng-click="abstractDetailDown()">
		            	<a href="" class="popup-form-export btn btn-default">
		                	<img src="static/resource/css/image/popup-form-export.png" alt="">
		                    <span>导出</span>
		                </a>
		            </div>
		    	</div>
		    	<div class="table-box">
			    	<table class="table table-bordered popup-form-table">
			        	<thead>
			            	<tr>
			                    <td ng-repeat="val in data.abstract_detail.headers">{{val}}</td>
			                </tr>
			            </thead>
			            <tbody>
			            	<tr ng-repeat="obj in data.abstract_detail.list">
			                    <td ng-repeat="val in data.abstract_detail.fields">{{obj[val]}}</td>
			                </tr>
		                </tbody>
			        </table>
		        </div>
		        <div class="clearfix">
		          <div class="pull-left" style="line-height: 38px;margin: 5px;">
		    			共{{data.abstract_detail.page.pagecount}}页,{{data.abstract_detail.page.sumcount}}条记录
		    		</div>
				<div pagination total-items="data.abstract_detail.page.sumcount" ng-model="data.abstract_detail.page.curpage" 
					items-per-page="data.abstract_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
		    </div>
		    </div>
		</div>
	</div> -->
  </body>
</html>
