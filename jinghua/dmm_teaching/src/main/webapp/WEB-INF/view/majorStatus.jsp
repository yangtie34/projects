<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>专业开设</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/majorStatus/controller.js"></script>
<script type="text/javascript" src="product/majorStatus/service.js"></script>
<script type="text/javascript" src="product/majorStatus/filter.js"></script>
</head>
<body ng-controller="controller">
<div class="ss-mark-wrapper" part-modal show-modal="data.mask">
<div class="ss-mark-main">
    <div class="header">
        <header class="header-tit">
            <b>专业开设</b>
        </header>
    </div>
    
    <div class="ss-mark-tab-box no-bottom pad-t-15">
        <div class="jx-fz-12 postion-r jx-mar-b-10">
        	<div class="jx-radio-box jx-has-more-r">
                <div class="radio radio-inline jx-radio" ng-repeat="obj in data.bzdm_xn" ng-show="data.model.xn_show || $index<5">
                    <input type="radio" ng-checked="obj.id==data.model.xn" ng-click="obj.id==data.model.xn || changeXn(obj.id,obj)" id="id_xn_{{obj.id}}">
                    <label for="id_xn_{{obj.id}}">{{obj.mc}}</label>
                </div>
            </div>
            <a href="" class="more-r jx-more-org" ng-click="data.model.xn_show=!data.model.xn_show">{{data.model.xn_show==true ? '收起'  : '更多'}} >></a>
        </div>
    </div>
    
    <div class="performance-change" ng-if="data.model.do_score">
        <div class="block-tit">
        	<p class="dis-inb">专业成绩</p>
<!--         	<a href=""><span class="time-icon-green pull-right"></span></a> -->
        </div>
        <div class="yc-tab">
        	<table class="table table-bordered" part-modal show-modal="data.grid_score.mask">
            	<thead>
                	<tr>
                        <td class="jx-form-slash" ng-class="{w10:data.grid_score.list.length==0}"><span>成绩</span><span>专业</span></td>
                        <td>平均成绩</td>
                        <td ng-repeat="attr in data.grid_score.bzdm_attr">{{attr.mc}}</td>
                        <td>平均成绩排名变化</td>
                        <td>近几年排名</td>
                    </tr>
                </thead>
                <tbody>
                	<tr ng-repeat="row in data.grid_score.list">
                        <td>{{row.name}}</td>
                        <td>{{row.value}}</td>
                        <td ng-repeat="attr_val in row.list track by $index">{{attr_val}}</td>
                        <td><span ng-class="{'jx-pmbh':row.ranking_change!=null&&row.ranking_change!=''&&row.ranking_change!=0,up:row.ranking_change>0,down:row.ranking_change<0}">{{row.ranking_change | positive}}</span></td>
                        <td><a href=""><i class="jx-paiming" ng-click="queryMajorScoreHis(row.id,row)"></i></a></td>
                    </tr>
                </tbody>
            </table>
    		<div ng-if="data.grid_score.list.length==0" class="no-date ng-scope">暂无数据...</div>
        </div><!--yc-tab-->
        <div class="jx-fz-12 jx-page">
<!--         	<a href="" class="jx-more-org">查看全部</a> -->
<!--             <p class="page pull-right"> -->
<!--             	<a href="" class="prevv"></a><a href="" class="nextt disable"></a> -->
<!--             </p> -->
            <div ng-if="data.grid_score.list.length!=0" pagination total-items="data.grid_score.page.sumcount" ng-model="data.grid_score.page.curpage" 
				items-per-page="data.grid_score.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
        </div>
    </div>
    <%-- 成绩详情 --%>
	<div cs-window show="data.grid_score.his_one.show" autocenter="true" style="padding:0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.grid_score.his_one.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.grid_score.his_one.show=false">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
			<div echart config="data.grid_score.his_one.option" style="width:500px;height:300px;"></div>
		</div>
	</div>
    
    
    <div class="performance-change" ng-if="data.model.do_failScale">
        <div class="block-tit">
        	<p class="dis-inb">专业不及格率</p>
<!--         	<a href=""><span class="time-icon-green pull-right"></span></a> -->
        </div>
        <div class="yc-tab">
        	<table class="table table-bordered" part-modal show-modal="data.grid_failScale.mask">
            	<thead>
                	<tr>
                        <td class="jx-form-slash" ng-class="{w10:data.grid_failScale.list.length==0}"><span>不及格</span><span>专业</span></td>
                        <td>平均不及格率</td>
                        <td ng-repeat="attr in data.grid_failScale.bzdm_attr">{{attr.mc}}</td>
                        <td>平均不及格率排名变化</td>
                        <td>近几年排名</td>
                    </tr>
                </thead>
                <tbody>
                	<tr ng-repeat="row in data.grid_failScale.list">
                        <td>{{row.name}}</td>
                        <td>{{row.value}}</td>
                        <td ng-repeat="attr_val in row.list track by $index">{{attr_val}}</td>
                        <td><span ng-class="{'jx-pmbh':row.ranking_change!=null&&row.ranking_change!=''&&row.ranking_change!=0,up:row.ranking_change>0,red:row.ranking_change>0,down:row.ranking_change<0,green:row.ranking_change<0}">{{row.ranking_change | positive}}</span></td>
                        <td><a href=""><i class="jx-paiming" ng-click="queryMajorFailScaleHis(row.id,row)"></i></a></td>
                    </tr>
                </tbody>
            </table>
    		<div ng-if="data.grid_failScale.list.length==0" class="no-date ng-scope">暂无数据...</div>
        </div><!--yc-tab-->
        <div class="jx-fz-12 jx-page">
<!--         	<a href="" class="jx-more-org">查看全部</a> -->
<!--             <p class="page pull-right"> -->
<!--             	<a href="" class="prevv"></a><a href="" class="nextt disable"></a> -->
<!--             </p> -->
            <div ng-if="data.grid_failScale.list.length!=0" pagination total-items="data.grid_failScale.page.sumcount" ng-model="data.grid_failScale.page.curpage" 
				items-per-page="data.grid_failScale.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
        </div>
    </div>
    <%-- 不及格率详情 --%>
	<div cs-window show="data.grid_failScale.his_one.show" autocenter="true" style="padding:0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.grid_failScale.his_one.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.grid_failScale.his_one.show=false">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
			<div echart config="data.grid_failScale.his_one.option" style="width:500px;height:300px;"></div>
		</div>
	</div>
    
    
    <div class="performance-change" ng-if="data.model.do_evaluateTeach">
        <div class="block-tit">
        	<p class="dis-inb">专业评教</p>
<!--         	<a href=""><span class="time-icon-green pull-right"></span></a> -->
        </div>
        <div class="yc-tab">
        	<table class="table table-bordered" part-modal show-modal="data.grid_evaluateTeach.mask">
            	<thead>
                	<tr>
                        <td class="jx-form-slash" ng-class="{w10:data.grid_evaluateTeach.list.length==0}"><span>评教</span><span>专业</span></td>
                        <td>评教平均分</td>
                        <td ng-repeat="attr in data.grid_evaluateTeach.bzdm_attr">{{attr.mc}}</td>
                        <td>评教平均分排名</td>
                        <td>近几年排名</td>
                    </tr>
                </thead>
                <tbody>
                	<tr ng-repeat="row in data.grid_evaluateTeach.list">
                        <td>{{row.name}}</td>
                        <td>{{row.value}}</td>
                        <td ng-repeat="attr_val in row.list track by $index">{{attr_val}}</td>
                        <td><span ng-class="{'jx-pmbh':row.ranking_change!=null&&row.ranking_change!=''&&row.ranking_change!=0,up:row.ranking_change>0,down:row.ranking_change<0}">{{row.ranking_change | positive}}</span></td>
                        <td><a href=""><i class="jx-paiming" ng-click="queryMajorEvaluateTeachHis(row.id,row)"></i></a></td>
                    </tr>
                </tbody>
            </table>
    		<div ng-if="data.grid_evaluateTeach.list.length==0" class="no-date ng-scope">暂无数据...</div>
        </div><!--yc-tab-->
        <div class="jx-fz-12 jx-page">
<!--         	<a href="" class="jx-more-org">查看全部</a> -->
<!--             <p class="page pull-right"> -->
<!--             	<a href="" class="prevv"></a><a href="" class="nextt disable"></a> -->
<!--             </p> -->
            <div ng-if="data.grid_evaluateTeach.list.length!=0" pagination total-items="data.grid_evaluateTeach.page.sumcount" ng-model="data.grid_evaluateTeach.page.curpage" 
				items-per-page="data.grid_evaluateTeach.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
        </div>
    </div>
    <%-- 评教详情 --%>
	<div cs-window show="data.grid_evaluateTeach.his_one.show" autocenter="true" style="padding:0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.grid_evaluateTeach.his_one.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.grid_evaluateTeach.his_one.show=false">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
			<div echart config="data.grid_evaluateTeach.his_one.option" style="width:500px;height:300px;"></div>
		</div>
	</div>
    
    
    <div class="performance-change" ng-if="data.model.do_byJy">
        <div class="block-tit">
        	<p class="dis-inb">专业毕业就业</p>
<!--         	<a href=""><span class="time-icon-green pull-right"></span></a> -->
        </div>
        <div class="yc-tab">
        	<table class="table table-bordered" part-modal show-modal="data.grid_byJy.mask">
            	<thead>
                	<tr>
                        <td class="jx-form-slash" ng-class="{w10:data.grid_byJy.list.length==0}"><span></span><span>专业</span></td>
                        <td>毕业率</td>
                        <td>毕业率排名</td>
                        <td>近几年排名</td>
                        <td>就业率</td>
                        <td>就业率排名</td>
                        <td>近几年排名</td>
                    </tr>
                </thead>
                <tbody>
                	<tr ng-repeat="row in data.grid_byJy.list">
                        <td>{{row.name}}</td>
                        <td>{{row.graduation_rate}}</td>
                        <td><span ng-class="{'jx-pmbh':row.graduation_ranking_change!=null&&row.graduation_ranking_change!=''&&row.graduation_ranking_change!=0,up:row.graduation_ranking_change>0,down:row.graduation_ranking_change<0}">{{row.graduation_ranking_change | positive}}</span></td>
    					<td><a href=""><i class="jx-paiming" ng-click="queryMajorByHis(row.id,row)"></i></a></td>
                        <td>{{row.employment_rate}}</td>
                        <td><span ng-class="{'jx-pmbh':row.employment_ranking_change!=null&&row.employment_ranking_change!=''&&row.employment_ranking_change!=0,up:row.employment_ranking_change>0,down:row.employment_ranking_change<0}">{{row.employment_ranking_change | positive}}</span></td>
                        <td><a href=""><i class="jx-paiming" ng-click="queryMajorJyHis(row.id,row)"></i></a></td>
                    </tr>
                </tbody>
            </table>
    		<div ng-if="data.grid_byJy.list.length==0" class="no-date ng-scope">暂无数据...</div>
        </div><!--yc-tab-->
        <div class="jx-fz-12 jx-page">
<!--         	<a href="" class="jx-more-org">查看全部</a> -->
<!--             <p class="page pull-right"> -->
<!--             	<a href="" class="prevv"></a><a href="" class="nextt disable"></a> -->
<!--             </p> -->
		    <div ng-if="data.grid_byJy.list.length!=0" pagination total-items="data.grid_byJy.page.sumcount" ng-model="data.grid_byJy.page.curpage" 
				items-per-page="data.grid_byJy.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
        </div>
    </div>
	<%-- 毕业率详情 --%>
	<div cs-window show="data.grid_byJy.by.show" autocenter="true" style="padding:0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.grid_byJy.by.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.grid_byJy.by.show=false">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
			<div echart config="data.grid_byJy.by.option" style="width:500px;height:300px;"></div>
		</div>
	</div>
	<%-- 就业率详情 --%>
	<div cs-window show="data.grid_byJy.jy.show" autocenter="true" style="padding:0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.grid_byJy.jy.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.grid_byJy.jy.show=false">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
			<div echart config="data.grid_byJy.jy.option" style="width:500px;height:300px;"></div>
		</div>
	</div>
    
    
	<div class="performance-change no-border">
       	<div class="jx-mar-b-10">
       		  <div class="dis-inb ben-zhuan" cg-select data="data.bzdm_year" ng-model="data.grid_search.model.year" style="width:140px"></div>
              <span class="dis-inb mar-r-6 jx-fz-16 jx-color-org">搜索</span>	  
          </div>
          <div><form name="myForm">
              <ul class="list-unstyled jx-btm-border-in">
                  <li ng-if="data.model.do_score">
                  	<div class="row clearfix">
                          <p class="jx-search-name col-sm-2">平均成绩</p>
                          <p class="jx-search-rate col-sm-2">最差的<input type="text" ng-class="{'input-wrong':myForm.score.$invalid}" ng-pattern="/^([0-9]|[1-9][0-9]|100)$/" name="score" ng-model="data.grid_search.model.score" class="jx-input form-control dis-inb input-sm" style="width:30px;">%</p>
                          <p class="jx-search-and col-sm-1"><span class="round round-30 color-green-gray">且</span></p>
                      </div>
                  </li>
                  <li ng-if="data.model.do_failScale">
                  	<div class="row clearfix">
                          <p class="jx-search-name col-sm-2">不及格率</p>
                          <p class="jx-search-rate col-sm-2">最高的<input type="text" ng-pattern="/^([0-9]|[1-9][0-9]|100)$/" name="failScale" ng-model="data.grid_search.model.failScale" class="jx-input form-control dis-inb input-sm" style="width:30px;">%</p>
                          <p class="jx-search-and col-sm-1"><span class="round round-30 color-green-gray">且</span></p>
                      </div>
                  </li>
                  <li ng-if="data.model.do_evaluateTeach">
                  	<div class="row clearfix">
                          <p class="jx-search-name col-sm-2">评教分</p>
                          <p class="jx-search-rate col-sm-2">最低的<input type="text" ng-pattern="/^([0-9]|[1-9][0-9]|100)$/" name="evaluateTeach" ng-model="data.grid_search.model.evaluateTeach" class="jx-input form-control dis-inb input-sm" style="width:30px;">%</p>
                          <p class="jx-search-and col-sm-1"><span class="round round-30 color-green-gray">且</span></p>
                      </div>
                  </li>
                  <li ng-if="data.model.do_byJy">
                  	<div class="row clearfix">
                          <p class="jx-search-name col-sm-2">毕业率</p>
                          <p class="jx-search-rate col-sm-2">最低的<input type="text" ng-pattern="/^([0-9]|[1-9][0-9]|100)$/" name="by" ng-model="data.grid_search.model.by" class="jx-input form-control dis-inb input-sm" style="width:30px;">%</p>
                          <p class="jx-search-and col-sm-1"><span class="round round-30 color-green-gray">且</span></p>
                      </div>
                  </li>
                  <li ng-if="data.model.do_byJy">
                  	<div class="row clearfix">
                          <p class="jx-search-name col-sm-2">就业率</p>
                          <p class="jx-search-rate col-sm-2">最低的<input type="text" ng-pattern="/^([0-9]|[1-9][0-9]|100)$/" name="jy" ng-model="data.grid_search.model.jy" class="jx-input form-control dis-inb input-sm" style="width:30px;">%</p>
                          <p class="jx-search-and col-sm-1"><span class="round round-30 color-green-gray">且</span></p>
                      </div>
                  </li>
              </ul>
              <p class="text-center pad-t-15"><a href="#search_list" name="search_list" class="bg-green jx-btn years" ng-click="myForm.$invalid || queryMajorSearchList()">查询</a></p>
          </form></div>
          <div name="search_list">
	       <div ng-if="myForm.$valid && data.grid_search.list.length==0" style="text-align: center;">暂无符合条件的专业</div>
		   <div class="yc-tab" ng-if="data.grid_search.list.length>0">
		   		符合条件的专业有 {{data.grid_search.list.length}} 个
	        	<table class="table table-bordered">
	            	<thead>
	                	<tr>
	                        <td><span>专业</span></td>
	                        <td>平均成绩</td>
	                        <td>平均不及格率</td>
	                        <td>评教平均分</td>
	                        <td>平均毕业率</td>
	                        <td>平均就业率</td>
	                    </tr>
	                </thead>
	                <tbody>
	                	<tr ng-repeat="row in data.grid_search.list">
	                        <td>{{row.name}}</td>
	                        <td>{{row.score_avg}}</td>
	                        <td>{{row.fail_class_rate}}</td>
	                        <td>{{row.evaluate_teaching_score_avg}}</td>
	                        <td>{{row.graduation_rate}}</td>
	                        <td>{{row.employment_rate}}</td>
	                    </tr>
	                </tbody>
	            </table>
	       </div>
	      </div>
    </div>
       
</div>
</div>
</body>
</html>