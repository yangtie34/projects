<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学籍异动</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/change/controller.js"></script>
<script type="text/javascript" src="product/change/service.js"></script>
</head>
<body ng-controller="controller">

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
	<div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="false"></div>
</div>

<section class="student-xueji-yidong"> 
	<div class="xuegong-separate-tb">
	 	<div class="student-xueji-yidong-con">
	        <div class="xuegong-zhuti-content-main-tit"><b class="text-blue">在籍生学籍异动</b>
                <span href="" class="question-icon pull-right" ng-mouseover="data.tip_show=true" ng-mouseleave="data.tip_show=false">
                    <a class="question-img  pull-right"></a>
                    <div class="question-area-box question-area-box-top" ng-show="data.tip_show">
                        <div class="question-area">
                            <img src="static/resource/images/xues-gognzuozhe/question-k.png">
                            <p><b>学籍特殊异动</b>包括：{{data.change_abstract.name_bad}}</p>
                        </div>
                    </div>
                </span>
	        </div>
	        <div class="xuegong-separate-con">
	        	<div class="student-xueji-yidong-title yidong-tit">
	            	<div style="margin-bottom:10px;">当前在籍生学籍异动<b><span class="text-pink"><a href="" class="text-green" ng-click="getStuDetail('allChange')"> {{data.change_abstract.count_all}} </a></span></b>人次；
	            	<span class="message-icon">
                        <u><a href="" class="text-blue">学籍特殊异动</a></u>
                        <div class="question-area-box question-area-box-r question-area-box-top" style="left:45px;">
                            <div class="question-area">
                                <img src="static/resource/images/xues-gognzuozhe/question-k-r.png">
                                <p><b>学籍特殊异动</b>包括：{{data.change_abstract.name_bad}}</p>
                            </div>
                        </div>
                    </span>
	            	<b><span class="text-pink"><a href="" class="text-green" ng-click="getStuDetail('badChange')"> {{data.change_abstract.count_bad}}</a> </span></b>人次，占比<b><span class="text-pink"> {{data.change_abstract.scale}}%</span></b></div>
	                <div class="clearfix"></div>
	            </div>
	            <div class="student-xueji-yidong-con-img" ng-show="!data.changeSetting.history.show" part-modal show-modal="data.changeThis.show" >
	            	<div class="row">
	                	<div class="col-md-6"><div class="img-box">
<!--                         	<div class="img-box-only"> -->
                        		<div style="height:370px;" class="center-block" echart config="data.distribute.typeCfg"></div>
<!--                         	</div> -->
	                    </div></div>
	                    <div class="col-md-6">
<!-- 	                    	<div class="img-box"> -->
                        		<div style="height:370px;" class="center-block" echart config="data.distribute.gradeCfg"></div>
<!-- 	                        </div> -->
	                    </div>
	                    <div class="clearfix"></div>
	                </div>
	                <div class="exchange"><a href=""><span ng-click="changeHistory()" title="查看历史">
	                	<img ng-src="static/resource/images/student-xueji-yidong/xuegong-exchange-qushi.png" class="center-block">
	                </span></a></div>
	                <div class="row">
	                	<div class="col-md-6"><div class="img-box">
	                        <div class="center-block" echart config="data.distribute.subjectCfg"></div>
	                    </div></div>
	                    <div class="col-md-6"><div class="img-box">
	                        <div class="center-block" echart config="data.distribute.sexCfg"></div>
	                    </div></div>
	                    <div class="clearfix"></div>
	                </div>
	            </div>
	            <div class="student-xueji-yidong-con-img student-xueji-yidong-con-img-02" ng-show="data.changeSetting.history.show" part-modal show-modal="data.changeHistory.show">
	            	<div class="row">
	                   	<div class="col-md-6"><div class="img-box">
<!-- 	                        <div class="img-box-only"> -->
                        		<div class="center-block" style="height:290px;" echart config="data.history.typeCfg"></div>
<!--                             </div>  -->
                           	<p ng-show="false" class="text-center">转专业始终是占有最大比例；转专业、留降级、退学呈现上升趋势</p>
                        </div></div>
                        <div class="col-md-6"><div class="img-box">
<!--                            	<div class="img-box-only"> -->
                        		<div class="center-block" style="height:290px;" echart config="data.history.gradeCfg"></div>
<!--                            	</div> -->
                          	<p ng-show="false" class="text-center">各年级学籍异动均呈现上升趋势；一、二年级异动始终占有较大比例</p>
                        </div></div>
                        <div class="clearfix"></div>
                   </div>
                   <div class="exchange"><a href=""><span ng-click="changeThis()" title="查看分布">
                   	<img src="static/resource/images/student-xueji-yidong/xuegong-exchange-fenbu.png" class="center-block">
                   </span></a></div>
                   <div class="row">
                       <div class="col-md-6"><div class="img-box">
<!--                            <div class="img-box-only"> -->
                        		<div class="center-block" style="height:290px;" echart config="data.history.subjectCfg"></div>
<!--                            </div> -->
                           <p ng-show="false" class="text-center">工学、理学、文学学生异动占比较大；农学异动人次呈现上升趋势</p>
                       </div></div>
                       <div class="col-md-6"><div class="img-box">
<!--                            	<div class="img-box-only"> -->
                        		<div class="center-block" style="height:290px;" echart config="data.history.sexCfg"></div>
<!--                             </div> -->
                            <p ng-show="false" class="text-center">男生异动比例呈下降趋势；女生异动比例呈上升趋势</p>
                       </div></div>
                       <div class="clearfix"></div>
                   </div>
	            	
	            </div>
	        </div>
	    </div>
	</div>
	
	<div class="xuegong-separate-tb">
     	<div class="xueji-yidong">
            <div class="xuegong-zhuti-content-main-tit"><b class="text-blue">各{{data.deptMc}}异动人数与比例</b></div>
            <div class="xuegong-separate-con"><div class="img-box">
            	<div class="img-box-only">
                     <div class="center-block" echart config="data.deptCfg"></div>
                </div>
            </div></div>
        </div>
    </div>
	
	<div class="xuegong-separate-tb">
     	<div class="xueji-yidong yidong-bili">
            <div class="xuegong-zhuti-content-main-tit">
            	<b class="text-blue">学籍异动月份分布</b>
<!--             	<a href=""><span class="time-img pull-right"></span></a> -->
            </div>
            <div class="xuegong-separate-con"><div class="img-box">
            	<div class="img-box-only">
                     <div class="center-block" echart config="data.monthCfg"></div>
                </div>
                <p class="text-center" ng-if="data.info.high">
                	<i class="fa fa-warning text-pink"></i>数据来源：{{data.info.dataSource}}。
                	每年<b class="text-pink">{{data.info.high}}月</b>是学籍异动高发期
                	<span ng-if="data.info.infoList">，其中
                		<span ng-repeat="obj in data.info.infoList">
                			{{obj.name}}月发生的<b class="text-pink">{{obj.info}}</b>较多<span ng-if="$index!=data.info.infoList.length-1">，</span>
                		</span>
					</span>
                </p>
            </div></div>
        </div>
    </div>
    
    <div class="xuegong-separate-tb">
     	<div class="xueji-yidong">
            <div class="xuegong-zhuti-content-main-tit"><b class="text-blue">历年异动人数与比例</b></div>
            <div class="xuegong-separate-con"><div class="img-box">
            	<div class="img-box-only">
                	<div class="center-block" echart config="data.yearCfg"></div>
                </div>
            </div></div>
        </div>
    </div>
     <div class="xuegong-separate-tb scholarship-fenbu" part-modal show-modal="data.changeMajor.mask">
     	<div class="xueji-yidong">
            <div class="xuegong-zhuti-content-main-tit"><b class="text-blue">各{{data.deptMcTwo}}转专业（转入，转出）分布   </b>
            <!--  <div class="xuegong-zhuti-dropdown-box">
                <div class="btn-group" cg-select data="data.deptData.bzdm_target" on-change="changeDeptTarget($value,$data)" ng-model="data.deptData.id_target"></div>
            </div> -->
            <div class="xuegong-zhuti-dropdown-box" ng-show="data.btnShow">
				<div class="btn-group" cg-select data="data.sty" on-change="changeLevelType($value,$data)" ng-model="data.value_sty"></div>            
			</div>
			<div class="btn-group-follow-text" style="margin-left:15px;" ng-show="data.btnShow && data.infoShow">{{data.distribution_info}}</div>
            </div>
            <div class="xuegong-separate-con"><div class="img-box">
            	<div class="img-box-only">
                	<div class="center-block" echart config="data.deptOrMajor.option"></div>
                </div>
            </div></div>
        </div>
    </div>                
</section>
</div>
</div>
    <div modal-form config="data.stu_detail.formConfig"></div>
</body>
</html>