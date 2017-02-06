<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>师资队伍</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/teacherGroup/controller.js"></script>
<script type="text/javascript" src="product/teacherGroup/service.js"></script>
<script type="text/javascript" src="product/teacherGroup/constant.js"></script>

</head>
<body ng-controller="controller">

<div class="ss-mark-wrapper">
<div class="ss-mark-main">
	<div class="header">
        <header class="header-tit">
            <b>师资队伍</b>
        </header>
        <div ng-show="!data.advance.show" ng-click="advanceShow()" title="高级查询" class="bak-orange" align="center"><a href="" class="sets"></a></div>
        
        <div ng-show="data.advance.show" class="header-con last-performance" style="margin-top:18px;">
	        <div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="false"></div>
	    </div>
	    <div ng-show="data.advance.show" ng-click="advanceShow()" title="收起" class="bak-orange bak-orange-up" align="center"><a href="#" class="sets up"><i class="fa fa-angle-up"></i></a></div>
    </div>
	
	<section class="pad no-bottom">
        <div class="separate-lf-rt">
        	<div class="sepatate-lf">
                <ul class="list-unstyled shizi-item">
                	<li class="tab-li">
                    	<div>
                    		<p>教职工/人</p>
                            <a href=""><div ng-click="abstractDetailClick('','教职工')" class="round color-yellow round-64">{{data.abstract_data.count}}</div></a>
                        </div>
                        <div>
                        	<p>副高级以上职称/人</p>
                            <a href=""><div ng-click="abstractDetailClick('deputyAbove','副高级以上职称')" class="round color-red-pin round-64">{{data.abstract_data.deputyAbove}}</div></a>
                        </div>
                    </li>
                    <li class="tab-li">
                    	<div>
                    		<p>专任教师/人</p>
                            <a href=""><div ng-click="abstractDetailClick('zrjs','专任教师')" class="round color-purple-blue round-64">{{data.abstract_data.zrjs}}</div></a>
                        </div>
                        <div>
                        	<p>教授/人</p>
                            <a href=""><div ng-click="abstractDetailClick('professor','教授')" class="round color-green-g round-64">{{data.abstract_data.professor}}</div></a>
                        </div>
                    </li>
                    <li class="tab-li">
                    	<div>
                    		<p>外聘教师/人</p>
                            <a href=""><div ng-click="abstractDetailClick('wpjs','外聘教师')" class="round color-blue-qian round-64">{{data.abstract_data.wpjs}}</div></a>
                        </div>
                        <div>
                        	<p>高层次人才/人</p>
                            <a href=""><div ng-click="abstractDetailClick('senior','高层次人才')" class="round color-green-grass round-64">{{data.abstract_data.senior}}</div></a>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="separate-rt">
            	<ul class="list-unstyled name">
                	<li class="tab-li">
                    	<div>
                        	<span class="teacher-img"></span>
                        	<span class="drop-green-box">
                            	<a href="" class="drop-green"></a>
                                <div class="drop-down-out-box">
                                    <div class="drop-down-box">
                                        <p class="drop-box-gongshi text-green"><span class="drop-box-tit">生师比</span> = 折合在校生数 ÷ 教师总数</p>
                                        <div class="drop-box-texts">
                                            <span class="text-green">折合在校生数</span> = 普通本、专科（高职）生数 + 硕士生数×1.5 + 博士生数×2 + 留学生数×3 + 预科生数 + 进修生数 + 成人托产班学生数 + 夜大(业余)学生数×0.3 + 函授生数×0.1
                                        </div>
                                        <div class="drop-box-texts">
                                            <span class="text-green">教师总数</span> = 专任教师数 + 外聘教师数×0.5
                                        </div>
                                    </div>
                                </div>
                            </span>
                            <p class="bili">生师比</p>
                            <a href="" class="purple-btn cursor-default"><span>{{data.abstract_data.stuTeaScale}} : 1</span></a>
                        </div>
                        <div>
                        	<span class="teacher-img"></span>
                            <p class="bili">外聘教师与专任教师比</p>
                            <a href="" class="purple-btn cursor-default"><span>1 : {{data.abstract_data.wpjsZrjsScale}}</span></a>
                        </div>
                    </li>
                </ul>
                <ul ng-if="data.senior.list==null || data.senior.list.length<1" class="shizi-date-wwh"></ul>
                <ul class="list-unstyled item" ng-repeat="ary in data.senior.list" ng-show="$index==data.senior.index">
                	<li class="tab-li dis-block clearfix" ng-show="ary.length>0">
                    	<div>
                    		<p>{{ary[0].name}}/人</p>
                            <a href=""><div ng-click="abstractDetailClick('senior',ary[0].name,ary[0].code)" class="round color-green-gray round-64">{{ary[0].value}}</div></a>
                        </div>
                        <div ng-show="ary.length>1">
                        	<p>{{ary[1].name}}/人</p>
                            <a href=""><div ng-click="abstractDetailClick('senior',ary[1].name,ary[1].code)" class="round color-blue-gray round-64">{{ary[1].value}}</div></a>
                        </div>
                    </li>
                    <li class="tab-li dis-block clearfix" ng-show="ary.length>2">
                    	<div>
                    		<p>{{ary[2].name}}/人</p>
                            <a href=""><div ng-click="abstractDetailClick('senior',ary[2].name,ary[2].code)" class="round color-ju-gray round-64">{{ary[2].value}}</div></a>
                        </div>
                        <div ng-show="ary.length>3">
                        	<p>{{ary[3].name}}/人</p>
                            <a href=""><div ng-click="abstractDetailClick('senior',ary[3].name,ary[3].code)" class="round color-greener-gray round-64">{{ary[3].value}}</div></a>
                      </div>
                    </li>
                    <a href="" ng-if="$index!=0" ng-click="changeSenior(-1)"><div class="left-arrow"><i class="fa fa-angle-left"></i></div></a>
                    <a href="" ng-if="$index!=data.senior.list.length-1" ng-click="changeSenior(1)"><div class="right-arrow"><i class="fa fa-angle-right"></i></div></a>
                </ul>
            </div>
        </div>
    
    	<div class="shizi-tit has-bg-color">
        	<div>
                <span class="qipao"></span>
                <small class="teacher-ps"><span class="dis-inb">以下是</span>
                    <div class="btn-group cg-select-dropdown no-bg dis-inb">
                        <button type="button" class="btn ss-drop-input dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span class="input-area">{{data.model.lx_mc}}</span>
                            <span class="caret-box">
                                <span class="caret"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                            </span>
                        </button>
                        <ul class="dropdown-menu dropdown-menu-scrollbar" style="margin-top:0px;">
                            <li ng-class="{active:obj.id==data.model.lx_id}" ng-repeat="obj in data.lxAry"><a href="" ng-click="obj.id==data.model.lx_id || changeTeaLx(obj)">{{obj.mc}}</a></li>
                        </ul>
                    </div>
                    <span class="dis-inb">分析。高职称：副高级及以上职称；高学历：博士研究生及以上学历；高学位：博士及以上学位；青年教师：45岁及以下教师。</span>
                 </small>
            </div>
        </div>
        
        <div class="separate-lf-rt">
        	<div class="bili-l" part-modal show-modal="data.technical.mask">
            	<p class="fenbu-tit need-pad">职称分布<a href=""><span ng-click="changeHistoryTechnical()" class="time-icon-green pull-right"></span></a></p>
                <div class="bili-l-con">
                	<p class="zhanbi-tit"><small>高职称占比</small><span class="text-24">{{data.technical.scale}}%</span></p>
                    <div class="img-box">
        				<div echart config="data.technical.option" class="center-block"></div>
                    </div>
                </div>
            </div>
            <div class="bili-r" style="width:54%">
            	<p class="fenbu-tit need-pad-r">学科师资分布</p>
                <div class="bili-r-con">
                    <div class="img-box">
        				<div echart config="data.subjectOption" class="center-block"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="separate-lf-rt">
        	<div class="bili-l" part-modal show-modal="data.degree.mask">
            	<p class="fenbu-tit need-pad">学位分布<a href=""><span ng-click="changeHistoryDegree()" class="time-icon-green pull-right"></span></a></p>
                <div class="bili-l-con">
                	<p class="zhanbi-tit"><small>高学位占比</small><span class="text-24">{{data.degree.scale}}%</span></p>
                    <div class="img-box">
        				<div echart config="data.degree.option" class="center-block"></div>
                    </div>
                </div>
            </div>
            <div class="bili-r" style="width:54%">
            	<p class="fenbu-tit need-pad-r">年龄分布</p>
                <div class="bili-r-con">
                	<p class="zhanbi-tit"><small>青年教师占比</small><span class="text-24">{{data.ageScale}}%</span></p>
                    <div class="img-box">
        				<div echart config="data.ageOption" class="center-block"></div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="separate-lf-rt">
        	<div class="bili-l" part-modal show-modal="data.edu.mask">
            	<p class="fenbu-tit need-pad">学历分布<a href=""><span ng-click="changeHistoryEdu()" class="time-icon-green pull-right"></span></a></p>
                <div class="bili-l-con">
                	<p class="zhanbi-tit"><small>高学历占比</small><span class="text-24">{{data.edu.scale}}%</span></p>
                    <div class="img-box">
        				<div echart config="data.edu.option" class="center-block"></div>
                    </div>
                </div>
            </div>
            <div class="bili-r" style="width:54%">
            	<p class="fenbu-tit need-pad-r">教龄分布</p>
                <div class="bili-r-con">
                    <div class="img-box">
        				<div echart config="data.schoolAgeOption" class="center-block"></div>
                    </div>
                </div>
            </div>
        </div>
    </section>
	
    <div class="performance-change no-bottom">
        <p class="block-tit">各单位师资分布</p>
        <div class="img-box">
 			<div echart config="data.deptOption" class="center-block"></div>
        </div>
    </div>
    <div class="performance-change no-bottom">
        <p class="block-tit">历年师资变化</p>
        <div class="img-box">
        	<div echart config="data.historyOption" class="center-block"></div>
        </div>
    </div>
    <div class="performance-change no-border no-bottom">
        <p class="block-tit">各{{data.deptMc}}生师比</p>
        <div class="img-box">
        	<div echart config="data.deptScaleOption" class="center-block"></div>
        </div>
    </div>
    
    <%-- 摘要 详情 --%>
    <div modal-form config="data.abstract_detail.formConfig"></div>
    <%-- 分布 详情 --%>
    <div modal-form config="data.distribution_detail.formConfig"></div>
    <%-- 组织机构 分布 详情 --%>
    <div modal-form config="data.dept_detail.formConfig"></div>
<!-- 	<div cs-window show="data.dept_detail.detail_show" autocenter="true" style="padding: 0;"> -->
<!-- 		<div class="popup-form popup-form-blue" style="margin:0px"> -->
<!-- 			<div class="popup-form-head clearfix"> -->
<!-- 		    	<h3 class="popup-form-title">{{data.dept_detail.title}}</h3> -->
<!-- 		        <a href="" class="popup-form-close" ng-click="data.dept_detail.detail_show=false"> -->
<!-- 		            <img src="static/resource/css/image/popup-form-close-red.png" alt=""> -->
<!-- 		        </a> -->
<!-- 		    </div> -->
<!-- 		    <div class="popup-form-body clearfix" part-modal show-modal="data.dept_detail.mask"> -->
<!-- 		    	<div class="popup-form-con clearfix"> -->
<!-- 			    	<div class="pull-right"> -->
<!-- 		            	<a href="" class="popup-form-export btn btn-default" ng-click="deptDetailDown()"> -->
<!-- 		                	<img src="static/resource/css/image/popup-form-export.png" alt=""> -->
<!-- 		                    <span>导出</span> -->
<!-- 		                </a> -->
<!-- 		            </div> -->
<!-- 		    	</div> -->
<!-- 		    	<div class="table-box"> -->
<!-- 			    	<table class="table table-bordered popup-form-table"> -->
<!-- 			        	<thead> -->
<!-- 			            	<tr> -->
<!-- 			                    <td ng-repeat="val in data.dept_detail.headers">{{val}}</td> -->
<!-- 			                </tr> -->
<!-- 			            </thead> -->
<!-- 			            <tbody> -->
<!-- 			            	<tr ng-repeat="obj in data.dept_detail.list"> -->
<!-- 			                    <td ng-repeat="val in data.dept_detail.fields">{{obj[val]}}</td> -->
<!-- 			                </tr> -->
<!-- 		                </tbody> -->
<!-- 			        </table> -->
<!-- 		        </div> -->
<!-- 		        <div class="clearfix"> -->
<!-- 		    		<div class="pull-left" style="line-height: 38px;margin: 5px;"> -->
<!-- 		    			共{{data.dept_detail.page.pagecount}}页,{{data.dept_detail.page.sumcount}}条记录 -->
<!-- 		    		</div> -->
<!-- 					<div pagination total-items="data.dept_detail.page.sumcount" ng-model="data.dept_detail.page.curpage"  -->
<!-- 						items-per-page="data.dept_detail.page.pagesize" max-size="5" class="pagination-sm pull-right" boundary-links="true"></div> -->
<!-- 		    	</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	
</div>
</div>

</body>
</html>