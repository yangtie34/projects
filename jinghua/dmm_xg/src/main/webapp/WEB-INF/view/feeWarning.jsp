<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html ng-app="app">
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <title>学费预警</title>
	<jsp:include page="/static/base.jsp"></jsp:include>
	<script type="text/javascript" src="product/feeWarning/controller.js"></script>
	<script type="text/javascript" src="product/feeWarning/service.js"></script>
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
            </header> -->
            
            <section> 
                 <div class="xuegong-separate-tb">
                 	<div class="tuition-warning">
                        <div class="xuegong-zhuti-content-main-tit"><b class="text-blue">欠费分类</b><h5>当前在籍学生<a href="" ng-click="abstractDetailClick('examrs','欠费（人数）学生名单')" class="text-pink"><b>{{XfyjCfg.COUNT_}}</b></a></b>人未全部缴费，共欠费<a href="" ng-click="abstractDetailClick('examrs','欠费（金额）学生名单')" class="text-pink"><b>{{XfyjCfg.MONEY_ | number:0}}</b></a>元<!-- </h5><span class="time-img pull-right"></span> --></div>
                        <div class="xuegong-separate-con">
                        	<!-- <img src="static/resource/images/student-source/xuegong-img-edit.jpg" class="pull-right" alt=""> -->
                            <div class="clearfix"></div>
                        	<div class="row">
                            	<div class="col-md-6">
                                	<!-- <div class="img-box"><img src="static/resource/images/tuition-warning/tuition-warning-t-l.png" class="center-block" alt=""></div> -->
                               <div echart config="XfflCfg"></div><!-- 按欠费类型分类 -->
                                </div>
                                <div class="col-md-6">
                                	<div class="img-box">
                                	<!-- <img src="static/resource/images/tuition-warning/tuition-warning-t-r.jpg" class="center-block" alt=""> -->
                                <div echart config="XsflCfg"></div> <!-- 按学生类型分类 -->
                                	</div>
                                </div>
                                <div class="clearfix"></div>
                            </div> 
                        </div>
                    </div>
                </div>
                
                <div class="xuegong-separate-tb">
                	<div class="owe-amount-proportion">
                        <div class="xuegong-zhuti-content-main-tit">
                            <b class="text-blue">历年欠费变化</b><a href=""><!-- <span class="time-img pull-right"> --></span></a>
                        </div>
                        <div class="xuegong-separate-con">
                            <div class="img-box img-center-pd">
                          <!--   <img src="static/resource/images/tuition-warning/owe-amount-proportion.png" class="center-block" alt=""> -->
                           <div echart config="QfradioCfg"></div> <!-- 历年欠费类型人数及比例 -->
                            </div>
                        </div>
                        <div class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-mt">
                     		<!-- 学历 -->
							<div class="xuegong-zhuti-dropdown-box">
								<div class="btn-group" cg-select data="data.bzdm_edu" on-change="changEdu($value,$data)" ng-model="data.value_edu"></div>
							</div>
                           <!-- 欠费类型 -->
							<div class="xuegong-zhuti-dropdown-box">
								<div class="btn-group" cg-select data="data.bzdm_lx" on-change="changQflx($value,$data)" ng-model="data.value_Qflx" value-field="id" display-field="mc"></div>
							</div>
                           <!-- 在籍状态-->
							<div class="xuegong-zhuti-dropdown-box">
								<div class="btn-group" cg-select data="data.bzdm_zjzt" on-change="changZjzt($value,$data)" ng-model="data.value_zjzt" value-field="id" display-field="mc"></div>
							</div>
                            <div class="checkbox checkbox-inline">
                             	<input type="checkbox" ng-click="onlyThisYear(tYear)" name="year"  value="" id="year" class="chk_1"> 
                                <label for="year">只看本学年</label>
                            </div>
                        </div>
                        
                        <div part-modal show-modal="data.mask[0]">
                        <div class="xuegong-zhuti-content-main-tab">
                			<table class="table table-bordered xuegong-zhuti-table tuition-warning-tab">
                                <thead>
                                    <tr>
                                        <!-- <td>组织机构</td> -->
                                        <td ng-repeat="item in QfTitle" init="i=0;">{{item}}
                                       	<div class="caret-icon" ng-show="$index>0">
                                                 <p><a href="" ng-click="changeOrder($index,'up')"  class="text-blue"><i class="fa fa-caret-up"></i></a></p>
                                                 <p><a href="" ng-click="changeOrder($index,'down')"  class="text-blue"><i class="fa fa-caret-down"></i></a></p>
                                            </div>
                                        </td>
                                        <td>
                                        <a href="" ng-if="data.statuss==2" ng-click="sendAll()" class="text-black"><i class="fa fa-send text-blue"></i>全部发送</a>
                                       	<a href="" ng-if="data.statuss==1" ng-click="sendAll()" class="text-black"><img src="static/resource/images/send-fail.png">发送失败</a>
                                        <span ng-if="data.statuss==0" class="text-black"><img src="static/resource/images/send-succeed.png">发送成功</span>
                                        </td>
                                        <td><a href="" ng-click="exportAll()"  class="text-black"><i class="fa fa-upload text-blue"></i>全部导出</a></td>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr ng-repeat="item in QfinfoCfg" init="i=0;" ng-show="$index<showData">
                                     <span ng-show="flase" class="label label-info">{{$index+1}}</span>
                                        <td ng-repeat="(key,value) in item" ng-show="key!='NUM'" ng-if="key!='CL05'">{{value}}</td>
                                        <td  ng-repeat="(key,value) in item" ng-show="key!='NUM'" ng-if="key=='CL05'">{{value | number:0}}</td> 
                                        <td>
                                        	<a href="" ng-if="data.status[$index]==2" ng-click="send('only',$index)" class="text-black"><img src="static/resource/images/send-icon.png">发送邮件</a>
                                        	<a href="" ng-if="data.status[$index]==1" ng-click="send('only',$index)" class="text-black"><img src="static/resource/images/send-fail.png">发送失败</a>
                                        	<span ng-if="data.status[$index]==0" class="text-black"><img src="static/resource/images/send-succeed.png">发送成功</span>
                                        </td>
                                        <td><a href="" ng-click="exportExcel($index)" class="text-black"><i class="fa fa-sign-out text-blue"></i>导出数据</a></td>
                                    </tr>
                                </tbody>
                    		</table>
                            <p class="text-center" >
                            <a href="" ng-show="showButton" ng-click="showMore(showData)" class="text-blue"><span>展开更多</span><i class="fa fa-angle-down"></i></a>
                            <a href="" ng-show="!showButton" class="text-blue angle-up" ng-click="showMore(00)"><span>收起</span><i class="fa fa-angle-up"></i></a>
                            </p>
                        </div> 
                        </div> 
                    </div>
                </div>
            </section>
        </div>
    </div>
</div>
</div>
<!--   <h5>当前在籍学生 {{XfyjCfg.COUNT_}}人未全部缴费，共欠费{{XfyjCfg.MONEY_}}元</h5>
  <hr>
   <h2>未缴费学费类型分类</h2><br>
  <div echart config="XfflCfg"></div>
  <hr>
  <h2>未缴费学生分类</h2><br>
  <div echart config="XsflCfg"></div> 
  <hr>
  <h2>历年欠费金额及人数比例</h2><br>
  <div echart config="QfradioCfg"></div> 
  <hr>
  <h2>欠费详细信息</h2><br>
  <div>
            <table class="table table-hover  jiaozhi-common-table" width="100%" border="0" cellspacing="0" cellpadding="0">
            <thead>
              <tr>
                <th scope="col" ng-repeat="item in QfTitle">{{item}}</th>
                <th><button ng-click="send('{{$index}}')">全部发送</button></th>
                <th><button ng-click="export('{{$index}}')">全部导出</button></th>                
              </tr>
            </thead>
            <tbody>
              <tr ng-repeat="item in QfinfoCfg" init="i=0;">
                <td><span class="label label-info">{{$index+1}}</span></td>
                <td ng-repeat="(key,value) in item" ng-show="key!='NUM'">{{value}}</td>
                <td><button ng-click="send($index)">发邮件</button></td>
                <td><button ng-click="exportExcel($index)">导出</button></td>
              </tr>
            </tbody>
          </table>
  </div> 
  <hr>
 -->
 <div modal-form config="data.abstract_detail.formConfig"></div>
    <%-- 学生 详情 --%>
<!-- 	<div cs-window show="data.abstract_detail.detail_show" auto-center="true" style="padding: 0;"> click-disappear="true"
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
