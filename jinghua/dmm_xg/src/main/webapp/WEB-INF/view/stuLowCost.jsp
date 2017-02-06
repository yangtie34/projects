<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ base + "/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base href="<%=basePath%>" />
<title>低消费分析</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/stuLowCost/controller.js"></script>
<script type="text/javascript" src="product/common/service_gdxf.js"></script>
<script type="text/javascript" src="product/stuHighCost/service_base.js"></script>
<script type="text/javascript" src="product/stuLowCost/constant.js"></script>

<style type="text/css">
.inline {
	display: inline;
}
</style>

</head>
<body ng-controller="controller">
<div class="xuegong-zhuti-content">
    	 <header>
        	<div class="pull-right">
                <a href="" ng-click="data.advance.show=!data.advance.show"><span></span><p>低级查询</p></a>
                <i></i>
                <a href="" class="tansuo disable"><span></span><p>探索</p></a>
                <i></i>
                <a href="" class="disable"><span></span><p>导出</p></a>
            </div>
            <div class="clearfix"></div>
        </header>
        <div class="xuegong-zhuti-content-main">
        	<div ng-show="data.advance.show"
				class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source xueji-buliang-yidong">
				<div cg-custom-comm source="data.advance.source"
					on-change="advanceChange($data)" expand="true" no-border="true"></div>
			</div>
            <section>
            	<div class="xuegong-zhuti-content-main-tit">
                	<div class="xuegong-zhuti-dropdown-box pull-left">
                      <div class="btn-group" cg-select data="data.monthlist"
						on-change="monthSelect($value,$data)" ng-model="data.monthname"
						value-field="id" display-field="mc"></div>
                    </div>
                    <p  class="p-marg-left">第{{data.month}}月低消费<span>
                    <a href="" ng-click="data.lastmonthcount==0||weekDetailClick('lastmonth','低消费（第'+data.month+'月）学生名单')"ng-class="{'text-pink':data.lastmonthcount !=0,'text-pink cursor-default':data.lastmonthcount==0 }"><b>{{data.lastmonthcount}}</b></a>
                    </span>人，占总人数的<span><a href="" class="text-pink"><b>{{data.lastmonthscale}}%</b></a></span>{{data.lastmonthchange >0?'，环比上升':(data.lastmonthchange <=0?'，环比下降':'。')}}<span><a href="" ng-if="data.lastmonthchange !='--' " ng-class="{'text-pink':data.lastmonthchange>0,'text-purple':data.lastmonthchange ==0,'text-green':data.lastmonthchange<0}"><b>{{data.lastmonthchange|jdz}}%。</b></a>
                </span></p>
                    <a href=""><span class="question-img pull-right"></span></a>
                    <span class="question-icon pull-right" style="margin-top:8px" ng-mouseover="data.tip_show=true" ng-mouseleave="data.tip_show=false">
		                        <a class="question-img  pull-right" ></a>
		                        <div class="question-area-box question-area-box-top" ng-show="data.tip_show">
		                           <div class="question-area">
		                                <img src="static/resource/images/xues-gognzuozhe/question-k.png">
		                                <p><b>低消费计算规则：</b></p>
		                        		<p>上学期所有学生的消费经过算法计算出每天的低消费标准值是{{data.standard}}元，</p>
		                        		<p> 该月内日均消费低于{{data.standard}}元就是低消费。</p>
		                        		<p ng-if="data.standardMc != null"> <b>算法：</b>{{data.standardMc}}</p>
		                        		<p> 注：低消费目前只计算{{data.xflx}}消费，表格数据按月统计。</p>
		                        		<p>（表格数据来源截止到{{data.tableDate}}）</p>
		                            </div>
		                        </div>
		                    </span>
                    <div class="clearfix"></div>
                </div>
                <div class="xuegong-zhuti-content-main-tab">
                	<div part-modal show-modal="data.grid.mask">
                	<table class="table table-bordered xuegong-zhuti-table dixiaofei-table">
                    	<thead>
                        	<tr>
                            	<td>组织机构</td>
                                <td>在校生人数</td>
                                <td>低消费人数
                                	<div class="caret-icon">
                                         <p><a href="" ng-class="{'text-blue caret-icon-active':tabcolumn=='count'&&tabasc,'text-blue':tabcolumn!='count'||!tabasc}" ng-click="(tabcolumn=='count'&&tabasc)||tabSort('count',true)"><i class="fa fa-caret-up"></i></a></p>
                                         <p><a href="" ng-class="{'text-blue caret-icon-active':tabcolumn=='count'&&!tabasc,'text-blue':tabcolumn!='count'||tabasc}" ng-click="(tabcolumn=='count'&&!tabasc)||tabSort('count',false)"><i class="fa fa-caret-down"></i></a></p>
                                    </div>
                                </td>
                                <td>占比
                                	<div class="caret-icon">
                                         <p><a href="" ng-class="{'text-blue caret-icon-active':tabcolumn=='scale'&&tabasc,'text-blue':tabcolumn!='scale'||!tabasc}" ng-click="(tabcolumn=='scale'&&tabasc)||tabSort('scale',true)"><i class="fa fa-caret-up"></i></a></p>
                                         <p><a href="" ng-class="{'text-blue caret-icon-active':tabcolumn=='scale'&&!tabasc,'text-blue':tabcolumn!='scale'||tabasc}" ng-click="(tabcolumn=='scale'&&!tabasc)||tabSort('scale',false)"><i class="fa fa-caret-down"></i></a></p>
                                    </div>
                                </td>
                                <td>变化
                                	<div class="caret-icon">
                                         <p><a href="" ng-class="{'text-blue caret-icon-active':tabcolumn=='change'&&tabasc,'text-blue':tabcolumn!='change'||!tabasc}" ng-click="(tabcolumn=='change'&&tabasc)||tabSort('change',true)"><i class="fa fa-caret-up"></i></a></p>
                                         <p><a href="" ng-class="{'text-blue caret-icon-active':tabcolumn=='change'&&!tabasc,'text-blue':tabcolumn!='change'||tabasc}" ng-click="(tabcolumn=='change'&&!tabasc)||tabSort('change',false)"><i class="fa fa-caret-down"></i></a></p>
                                    </div>
                                </td>
                                <td><a href="" class="text-black" ng-click="sendAll()"><i class="fa fa-send text-blue"></i>全部发送</a></td>
                                <td><a href="" class="text-black" ng-click="exportAll()"><i class="fa fa-upload text-blue"></i>全部导出</a></td>
                            </tr>
                        </thead>
                        <tbody>
                        	<tr ng-repeat = "x in data.deptlist">
                            	<td class="text-blue">{{x.name}}</td>
                                <td><a href="" ng-class="{'text-blue':x.stucount>0,'text-blue cursor-default':x.stucount==0}" ng-click = "x.stucount==0||weekDetailClick('all',x.name+'学生名单',x.id)">{{x.stucount}}</a></td>
                                <td><a href="" ng-class="{'text-blue':x.count>0,'text-blue cursor-default':x.count==0}" ng-click = "x.count==0||weekDetailClick('','低消费（'+x.name+'）学生名单',x.id)">{{x.count}}</a></td>
                                <td><a href=""class="text-blue" ng-click="weekLvClick('scale',x.id,x.name)">{{x.scale}}%</a></td>
                                <td><a href="" ng-if="x.change>0"ng-class="{'text-pink':x.change>0,'text-purple':x.change ==0,'text-green':x.change<0}" ng-click="weekLvClick('change',x.id,x.name)">+{{x.change}}%</a>
                                <a href="" ng-click="weekLvClick('change',x.id,x.name)" ng-if="x.change<=0"ng-class="{'text-pink':x.change>0,'text-purple':x.change ==0,'text-green':x.change<0}">{{x.change}}%</a>
                                <a href="" ng-click="weekLvClick('change',x.id,x.name)" ng-if="x.change=='--'"ng-class="text-purple">{{x.change}}</a></td>
                                <td><a href="" ng-class="{'text-black send-fail':x.status ==0,'text-black send-succeed not-click':x.status ==1,'text-black send-wait':x.status ==2,'text-black send-disable not-click':x.count==0}" ng-click="x.count==0||x.status ==1||send('only',$index,x.name,x.id,true)">
                                <span class= "xg-send"></span>{{x.count>0?(x.status ==0?'发送失败':(x.status == 1?'发送成功':'未发送')):'未发送'}}</a>
                                </td>
                                <td><a href="" class="text-black" ng-click="exportExcel($index)"><i class="fa fa-sign-out text-blue"></i>导出数据</a></td>
                            </tr>
                        </tbody>
                    </table>
                    </div>
                    <p class="text-center" ng-if="vm1.page.index*vm1.page.size<vm1.page.total"><a href="" class="text-blue" ng-click="vm1.page.index*vm1.page.size>=vm1.page.total||PageUp1()"><span>展开全部</span><i class="fa fa-angle-down"></i></a></p>
                    <p class="text-center"ng-if="vm1.page.index*vm1.page.size>=vm1.page.total"><a href="" class="text-blue" ng-click="vm1.page.index*vm1.page.size<vm1.page.total||PageDown1()"><span>收起</span><i class="fa fa-angle-up"></i></a></p>
                </div>
            </section>
            <div class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone">
            	<p class="text-blue">低消费分析</p>
                <div class="xuegong-zhuti-dropdown-box">
            	 <div class="btn-group" cg-select data="data.termlist"
						on-change="termSelect($value,$data)" ng-model="data.termname"
						value-field="id" display-field="mc"></div>
                </div>
               <!--  <div class="btn-basic di-xiaofei-btn">
                    <a ng-class="{'on btn-blue cursor-default':data.yearstart==y.start&&data.isYear==true,'btn-blue':data.yearstart!=y.start}" ng-click="data.yearstart==y.start||yearSelect(y.start,y.end)" href="" ng-repeat="y in data.yearlist">{{y.name}}</a>
                </div> -->
                <span>数据来源截止到{{data.chartDate}}</span>
                 <span class="question-icon pull-right" style="margin-top:8px"ng-mouseover="data.tip_show=true" ng-mouseleave="data.tip_show=false">
		                        <a class="question-img  pull-right" ></a>
		                        <div class="question-area-box question-area-box-top" ng-show="data.tip_show">
		                            <div class="question-area">
		                                <img src="static/resource/images/xues-gognzuozhe/question-k.png">
				                        <p> 统计该学期低消费学生；</p>
		                        		<!-- <p ng-if="data.yearlist.length >0"> 切换近几年时，统计这些学年所有学期（各学期低消费标准不同）的低消费学生，一个学生可能多个学期都是低消费。</p> -->
		                            </div>
		                        </div>
		                    </span> 
                <div class="clearfix"></div>
            </div>
             <section class="xuegong-neirong no-bottom">
            	<div class="xuegong-dixiaofei row">
                	<div class="xuegong-dixiaofei-fenbu pull-left" >
                    	<div class="biaoti"><span ng-if="mealShow" class="text-blue">低消费分布</span><span ng-if="!mealShow"class="text-blue">各餐别低消费变化<br><small class=" text-black">（展示近几学期高消费变化，不随学期选择变化）</small></span><a href="" ng-click="mealSwich()"><span class="pull-right time-img"></span></a></div>
                         <div part-modal show-modal="data.gridMeal.mask">
                        <div class="img-box" style="height: 370px"ng-if="mealShow&&!data.isYear" echart config="data.distribute.termMealCfg" ></div>
                         <div class="img-box" style="height: 370px"ng-if="!mealShow" echart config="data.history.mealHistoryCfg"></div>
                         <div class="img-box" style="height: 370px" ng-if="mealShow&&data.isYear" echart config="data.distribute.yearMealCfg"></div>
                   </div>
                    </div>
                    <div class="xuegong-dixiaofei-xuesheng-fenbu pull-left">
                    	<div class="biaoti text-blue"><span ng-if="gradeShow" class="text-blue">低消费学生分布</span><span ng-if="!gradeShow" class="text-blue">各年级、性别低消费变化<br><small class=" text-black">（展示近几学期高消费变化，不随学期选择变化）</small></span><a href="" ng-click="gradeSwich()"><span class="pull-right time-img"></span></a></div>
                        <div class="img-box">
                        	<div class="img-box-l pull-left" style="height: 350px"ng-if="gradeShow&&!data.isYear" echart config="data.distribute.termGradeCfg"></div>
                            <div class="img-box-l pull-left" style="height: 350px"ng-if="!gradeShow" echart config="data.history.gradeHistoryCfg"></div>
                            <div class="img-box-l pull-left" style="height: 350px" ng-if="gradeShow&&data.isYear" echart config="data.distribute.yearGradeCfg"></div>
                            <div class="img-box-r pull-left" style="height: 350px"ng-if="gradeShow&&!data.isYear" echart config="data.distribute.termSexCfg"></div>
                            <div class="img-box-r pull-left" style="height: 350px"ng-if="!gradeShow" echart config="data.history.sexHistoryCfg"></div>
                            <div class="img-box-r pull-left" style="height: 350px"ng-if="gradeShow&&data.isYear" echart config="data.distribute.yearSexCfg"></div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </div> 
                <div class="xuegong-dixiaofei dixiaofei-xs row">
                	<div class="dixiaofei-xs-con">
                        <div class="biaoti"><small class=" text-black" style="margin-left:10px" ng-if="!loanShow">注:展示近几学期数据，不随学期选择变化</small><a href="" ng-click="loanSwich()"><span class="pull-right time-img"></span></a><div class="clearfix"></div></div>
                        <div class="row">
                            <div class="col-md-4">
                              <div id= "Xg_stuLowCost_subsidy" style="height:{{gd}}px"class="img-box" ng-if="loanShow&&!data.isYear" ></div> 
                             <div id= "Xg_stuLowCost_subsidy"style="height:{{gd}}px" class="img-box" ng-if="!loanShow" echart config="data.history.subsidyHistoryCfg"></div> 
                            <div id= "Xg_stuLowCost_subsidy" style="height:{{gd}}px"class="img-box"  ng-if="loanShow&&data.isYear" ></div> 
                            <!-- <div id= "Xg_stuLowCost_subsidy" style="height:{{gd}}px"class="img-box" ng-if="loanShow&&!data.isYear" ></div> 
                             <div id= "Xg_stuLowCost_subsidy"style="height:{{gd}}px" class="img-box" ng-if="!loanShow" ></div> 
                            <div id= "Xg_stuLowCost_subsidy" style="height:{{gd}}px"class="img-box"  ng-if="loanShow&&data.isYear" ></div>-->
                                <p ng-if="loanShow==true">获助学金学生中有{{data.subsidyscale}}%（<a href="" ng-class="{'text-green': !data.isYear,'text-green cursor-default':(data.isYear|| data.subsidyscale ==0)}" ng-click = "(data.isYear|| data.subsidyscale ==0)||loanClick('subsidy','低消费（获助学金）学生名单','3')"><b >{{data.subsidycount}}{{!data.isYear?'人':'人次'}}</b></a>）是低消费学生</p>
                            </div>
                             <div class="col-md-4">
                        <div  id= "Xg_stuLowCost_loan"style="height:{{gd}}px"class="img-box" ng-if="loanShow&&!data.isYear" ></div>
                            <div id= "Xg_stuLowCost_loan" style="height:{{gd}}px"class="img-box" ng-if="!loanShow" echart config="data.history.loanHistoryCfg"></div>
                            <div id= "Xg_stuLowCost_loan" style="height:{{gd}}px"class="img-box"  ng-if="loanShow&&data.isYear" ></div> 
                         <!--   <div  id= "Xg_stuLowCost_loan"style="height:{{gd}}px"class="img-box" ng-if="loanShow&&!data.isYear"></div>
                            <div id= "Xg_stuLowCost_loan" style="height:{{gd}}px"class="img-box" ng-if="!loanShow" ></div>
                            <div id= "Xg_stuLowCost_loan" style="height:{{gd}}px"class="img-box"  ng-if="loanShow&&data.isYear"></div>--> 
                                <p ng-if="loanShow==true">获助学贷款学生中有{{data.loanscale}}%（<a href="" ng-class="{'text-green': !data.isYear,'text-green cursor-default':(data.isYear|| data.loanscale ==0)}" ng-click = "(data.isYear|| data.loanscale ==0)||loanClick('loan','低消费（获助学贷款）学生名单','3')"><b ></b>{{data.loancount}}{{!data.isYear?'人':'人次'}}</a>）是低消费学生</p>
                            </div>
                            <div class="col-md-4">
                            <div id= "Xg_stuLowCost_jm" style="height:{{gd}}px"class="img-box" ng-if="loanShow&&!data.isYear" ></div>
                            <div id= "Xg_stuLowCost_jm" style="height:{{gd}}px"class="img-box" ng-if="!loanShow" echart config="data.history.jmHistoryCfg"></div>
                            <div id= "Xg_stuLowCost_jm" style="height:{{gd}}px"class="img-box"  ng-if="loanShow&&data.isYear" ></div>
                            <!--  <div id= "Xg_stuLowCost_jm" style="height:{{gd}}px"class="img-box" ng-if="loanShow&&!data.isYear"></div>
                            <div id= "Xg_stuLowCost_jm" style="height:{{gd}}px"class="img-box" ng-if="!loanShow" ></div>
                            <div id= "Xg_stuLowCost_jm" style="height:{{gd}}px"class="img-box"  ng-if="loanShow&&data.isYear" ></div> -->
                                <p ng-if="loanShow==true">获学费减免学生中有{{data.jmscale}}%（<a href="" ng-class="{'text-green': !data.isYear,'text-green cursor-default':(data.isYear|| data.jmscale ==0)}" ng-click = "(data.isYear|| data.jmscale ==0)||loanClick('jm','低消费（获学费减免）学生名单','3')"><b>{{data.jmcount}}{{!data.isYear?'人':'人次'}}</b></a>）是低消费学生</p>
                            </div>
                        </div>
                    </div>
                </div>
                 <div class="xuegong-bottom-section">
                	<div class="biaoti text-blue"><span class="text-blue">各院系低消费分布与占比</span></div>
                   <div class="img-box" ng-if="!data.isYear" echart config="data.distribute.termDeptCfg"></div>
                            <div class="img-box"  ng-if="data.isYear" echart config="data.distribute.yearDeptCfg"></div>
                    </div> 
                </div>
            </section>
        </div>
    </div>
      <%-- 分布 详情 --%>
    <div modal-form config="data.stu_detail.formConfig"></div>
      <%-- 分布 详情 --%>
    <div modal-form config="data.week_detail.formConfig"></div>
   <!--  <div cs-window show="data.stu_detail.detail_show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.stu_detail.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.stu_detail.detail_show=false">
		        	<img src="static/resource/css/image/popup-form-close.png" alt="">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix" part-modal show-modal="data.stu_detail.mask">
		    	<div class="popup-form-con clearfix">
			    	<div class="pull-right">
		            	<a href="" class="popup-form-export btn btn-default" ng-click="stuDetailDown()">
		                	<img src="static/resource/css/image/popup-form-export.png" alt="">
		                    <span>导出</span>
		                </a>
		            </div>
		    	</div>
		    	<div class="table-box">
			    	<table class="table table-bordered popup-form-table">
			        	<thead>
			            	<tr>
			                    <td ng-repeat="val in data.stu_detail.headers">{{val}}</td>
			                </tr>
			            </thead>
			            <tbody>
			            	<tr ng-repeat="obj in data.stu_detail.list">
			                    <td ng-repeat="val in data.stu_detail.fields">{{obj[val]}}</td>
			                </tr>
		                </tbody>
			        </table>
		        </div>
                 <div class="clearfix">
		    		<div class="pull-left" style="line-height: 38px;margin: 5px;">
		    			共{{data.stu_detail.page.pagecount}}页,{{data.stu_detail.page.sumcount}}条记录
		    		</div>
					<div pagination total-items="data.stu_detail.page.sumcount" ng-model="data.stu_detail.page.curpage" 
						items-per-page="data.stu_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
		    	</div>
			</div>
		</div>
	</div>
		 <div cs-window show="data.week_detail.detail_show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.week_detail.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.week_detail.detail_show=false">
		        	<img src="static/resource/css/image/popup-form-close.png" alt="">
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix" part-modal show-modal="data.week_detail.mask">
		    	<div class="popup-form-con clearfix">
			    	<div class="pull-right">
		            	<a href="" class="popup-form-export btn btn-default" ng-click="weekDetailDown()">
		                	<img src="static/resource/css/image/popup-form-export.png" alt="">
		                    <span>导出</span>
		                </a>
		            </div>
		    	</div>
		    	<div class="table-box">
			    	<table class="table table-bordered popup-form-table">
			        	<thead>
			            	<tr>
			                    <td ng-repeat="val in data.week_detail.headers">{{val}}</td>
			                </tr>
			            </thead>
			            <tbody>
			            	<tr ng-repeat="obj in data.week_detail.list">
			                    <td ng-repeat="val in data.week_detail.fields">{{obj[val]}}</td>
			                </tr>
		                </tbody>
			        </table>
		        </div>
			    <div class="clearfix">
		    		<div class="pull-left" style="line-height: 38px;margin: 5px;">
		    			共{{data.week_detail.page.pagecount}}页,{{data.week_detail.page.sumcount}}条记录
		    		</div>
					<div pagination total-items="data.week_detail.page.sumcount" ng-model="data.week_detail.page.curpage" 
						items-per-page="data.week_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div>
		    	</div>
			</div>
		</div>
	</div> -->
	<div cs-window show="data.scale_detail.detail_show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">近五月{{data.scale_detail.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.scale_detail.detail_show=false">
		        	<!-- <img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		 	<div ng-if="scaleshow" echart config="data.distribute.scaleLineCfg"
					style="height: 400px; width: 500px; margin: 0 auto"></div>
		    <div  ng-if="changeshow" echart config="data.distribute.changeLineCfg"
					style="height: 400px; width: 500px; margin: 0 auto"></div>
		</div>
	</div>
</body>
</html>