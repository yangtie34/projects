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
		<title>不及格预测</title>
	<jsp:include page="/static/base.jsp"></jsp:include>
	<script type="text/javascript" src="product/failExamPredict/controller.js"></script>
	<script type="text/javascript" src="product/failExamPredict/service.js"></script>
  </head>
  
<body ng-controller="controller">
<div class="xuegong-zhuti-content" >
 <div class="xuegong-zhuti-content">
    	<header>
        	<div class="pull-right">
            	<a href=""><span></span><p>高级查询</p></a>
                <i></i>
                <a href="" class="tansuo"><span></span><p>探索</p></a>
                <i></i>
                <a href=""><span></span><p>导出</p></a>
            </div>
            <div class="clearfix"></div>
        </header>
        <div class="xuegong-zhuti-content-main">
            <div class="xuegong-separate-tb fail-situation-forecast">
            	<div class="xuegong-zhuti-content-main-tit">
                	<p><b class="text-blue">不及格情况预测</b></p>
                    <p class="p-marg-left">{{XnxqCfg.xnxq}}期末考试预测有<a href="" ng-click="abstractDetailClick('examrs','预测不及格人数学生名单')" class="text-pink"><b>{{XfyjCfg.COUNT_}}</b></a>人不及格 
                    	<span style="color:red">（预测日期：{{data.DATE_NOW}}，数据来源截止{{data.DATE_PRO}}）</span>
                    </p>
			
<!--                      <div class="xuegong-zhuti-dropdown-box">
                        <div class="btn-group">
                              <a type="button" class="btn btn-noradius btn-shadow in-con dropdown-toggle less-words" data-toggle="dropdown">{{XfyjCfg.GL}}%</a>
                              <a type="button" class="btn btn-noradius dropdown-toggle drown-icon" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <span class="caret carett"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                              </a>
                              <ul class="dropdown-menu">
                                Dropdown menu links
                                <li role="presentation"><a href="">{{XfyjCfg.GL}}%</a></li>
                                <li role="presentation"><a href="">{{XfyjCfg.GL}}%</a></li>
                                <li role="presentation"><a href="">{{XfyjCfg.GL}}%</a></li>
                              </ul>
                        </div> 
                    </div> -->
                   <!--  <p>的概率不及格</p> --> 
                     <a href=""><span class="question-img pull-right"></span></a>
                </div>
                <div class="xuegong-separate-con fail-situation-forecast-separate">
                	<div class="row">
                    	<div class="col-md-6">
                        	<div class="img-box">
                        	  <div echart config="XsflCfg"></div> 
                        	    
                            		<%-- <img src="<%=basePath %>/static/resource/images/Fail-situation-forecast/Fail-situation-forecast-grade.jpg" class="center-block vertical-center" alt=""> --%>
                            </div>
                        </div>
                        <div class="col-md-6">
                        	<div class="img-box">
                        	<div echart config="XfflCfg"></div>
                            		<%-- <img src="<%=basePath %>/static/resource/images/Fail-situation-forecast/Fail-situation-forecast-sex.jpg" class="center-block vertical-center" alt=""> --%>
                            </div>
                        </div>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
            
            <div class="xuegong-separate-tb">
            	<div class="xuegong-zhuti-content-main-tit">
                	<b class="text-blue">不同院系预测不及格人数与比例</b>
                </div>
                <div class="xuegong-separate-con fail-situation-forecast-bili">
                	<div class="img-box">
                    	<div class="img-box-only">
                    	  <div echart config="QfradioCfg"></div> </div>
                    	</div>
                    	<p class="text-center more"><a href="" ng-click="lookList()" class="text-blue">查看名单<i class="{{looklistcss}}"></i></a></p>
                    	
                    	 <table ng-show="showdetailtable" class="table table-bordered xuegong-zhuti-table fail-situation-forecast-table">
                    	<thead>
                        	<tr>
                        	    <td scope="col" ng-repeat="item in GkTitle">{{item}}</td>
                                <td>
                                    <a href="" ng-if="data.statuss==2" ng-click="sendAll()" class="text-black"><i class="fa fa-send text-blue"></i>全部发送</a>
                                    <a href="" ng-if="data.statuss==1" ng-click="sendAll()" class="text-black"><img src="static/resource/images/send-fail.png">发送失败</a>
                                    <span ng-if="data.statuss==0" class="text-black"><img src="static/resource/images/send-succeed.png">发送成功</span>
                                </td>
                                <td><a href="" class="text-black" ng-click="exportAll()"><i class="fa fa-upload text-blue"></i>全部导出</a></td>
                            </tr>
                        </thead>
                        <tbody>
                        	<tr ng-repeat="item in QfinfoCfg" init="i=0;"  ng-show="$index<showData">
                            	 <span ng-show="flase" class="label label-info">{{$index+1}}</span>
                                <td ng-repeat="(key,value) in item">{{value}}</td><!-- <a href="" class="text-blue"  > {{value}}</a>-->
                                <td>
                                    <a href="" ng-if="data.status[$index]==2" ng-click="send('only',$index)" class="text-black"><img src="static/resource/images/send-icon.png">发送邮件</a>
                                    <a href="" ng-if="data.status[$index]==1" ng-click="send('only',$index)" class="text-black"><img src="static/resource/images/send-fail.png">发送失败</a>
                                    <span ng-if="data.status[$index]==0" class="text-black"><img src="static/resource/images/send-succeed.png">发送成功</span>
                                </td>
                                <td><a href="" class="text-black" ng-click="exportExcel($index)"><i class="fa fa-sign-out text-blue"></i>导出数据</a></td>
                            </tr>
                        </tbody>
                    </table>
                    <p class="text-center" ng-show="showdetailtable">
                    <a href="" ng-show="showButton" class="text-blue" ng-click="showMore(showData)"><span>展开更多</span><i class="fa fa-angle-down"></i></a>
                    <a href="" class="text-blue angle-up" ng-click="showMore(00)"><span>收起</span><i class="fa fa-angle-up"></i></a>
               		</p>
                </div>
            </div>
            
        </div>
    </div>
</div>
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
