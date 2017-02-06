<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成绩预测</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/scorePredictTea/controller.js"></script>
<script type="text/javascript" src="product/scorePredictTea/constant.js"></script>
<script type="text/javascript" src="product/scorePredictTea/service_base.js"></script>
<style type="text/css">
.yc {
	font-weight: bold;
}
</style>
</head>
<body ng-controller="controller">
    <div class="ss-mark-main">
    	<div class="header">
            <header class="header-tit">
                <b>成绩预测（由学生行为预测）（任课教师）</b>
            </header>
            	<div ng-show="!data.advance.show" ng-click="advanceShow()"
						title="高级查询" class="bak-orange" align="center">
						<a href="" class="sets"></a>
					</div>

					<div ng-show="data.advance.show"
						class="header-con last-performance" style="margin-top: 18px;">
						<div cg-custom-comm source="data.advance.source"
							on-change="advanceChange($data)" expand="true" noborder="false"></div>
					</div>
					<div ng-show="data.advance.show" ng-click="advanceShow()"
						title="收起" class="bak-orange bak-orange-up" align="center">
						<a href="#" class="sets up"><i class="fa fa-angle-up"></i></a>
					</div>
        </div>
        
        <div class="ss-mark-tab-box no-bottom">
        <div class="cg-select-dropdown btn-group dis-inb mar-r-6" style="width:130px" cg-select data="data.eduList"
								on-change="eduSelect($value)" ng-model="data.edu"
								value-field="id" display-field="mc"></div>
            <div class="dis-inb">
                <span class="qipao"></span>
                <small class="teacher-ps">预测数据来源截止{{data.lyDate}}</small>
            </div>
            <div class="jx-fz-12 postion-r jx-mar-b-10">
            	<div class="jx-radio-box jx-has-more-r" >
                    <div ng-class="{'radio radio-inline jx-radio' : x.order == $index,'radio radio-inline jx-radio radio-l-widther': x.order != $index}" ng-repeat = " x in data.timeList" ng-click = "data.time == x.id ||timeSelect(x.id)" >
                        <input ng-if="x.id == data.time"type="radio" name="xueqi" value="" id="{{x.id}}" checked><label for="{{x.id}}">{{x.mc}}</label>
                        <input ng-if="x.id != data.time"type="radio" name="xueqi" value="" id="{{x.id}}" >
                    </div>
                </div>
                <a href="" class="more-r jx-more-org" ng-click = "loadMore()" ng-if="!data.timeshow && data.allTimeList.length > 6">更多 >></a>
             <a href="" class="more-r jx-more-org" ng-click = "loadMore()" ng-if="data.timeshow"><< 收起</a>
            </div>
        </div><!--ss-mark-tab-box-->
        	
            
        <div class="text-center yc-tab-head"><a ng-repeat = " y in data.courseList" ng-click ="data.course == y.id ||courseSelect(y.id,y.mc)" ng-class= "{'active':data.course == y.id}"href="">{{y.mc}}</a></div>
        <div class="performance-change no-top">
            <div><span class="jx-note-green">我代课的教学班有{{data.classCount}}个，共{{data.stuCount}}人。</span></div>
            <div class="jx-mar-b-10">
                <span  class="dis-inb mar-r-6 jx-fz-16 jx-color-org">预测概况</span>
            </div>
            <div class="yc-tab">
            
            	<div class="yc-tab-head-second"><a href="" ng-repeat="z in data.natureList" ng-class="{'on':z.id == data.nature}" ng-click = "data.nature == z.id ||natureSelect(z.id,z.mc)">{{z.mc}}</a></div>
            	<table class="table table-bordered">
                	<thead>
                    	<tr>
                        	<td rowspan="2">班级</td>
                            <td rowspan="2">人数</td>
                            <td rowspan="2">平均<br>成绩</td>
                            <td colspan="2" ng-repeat ="sb in data.thList">{{sb.mc}}</td>
                        </tr>
                        <tr>
                            <td ng-repeat = " ssjb in data.fthList" >
                            <p ng-if="$index % 2 ==0">人次</p>
                            <p ng-if = "$index % 2 == 1">占比%</p></td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr ng-class="{'yc':jb.mc == '合计'}" ng-repeat= "jb in data.tabList">
                        	<td >{{jb.mc}}</td>
                            <td >
                            <a href="" ng-if="jb.count>0" ng-click="stuListClick('class','th',jb.mc,jb.id,'all')">{{jb.count}}</a>
                            <p ng-if="jb.count == 0">{{jb.count}}</p>
                            </td>
                            <td>{{jb.avg|number:1}}</td>
                            <td ng-repeat = " ddb in jb.list" >
                            <a href="" ng-if = "$index % 2 == 0 && ddb.value > 0" ng-click="stuListClick('class','th',jb.mc,jb.id,ddb.code)">{{ddb.value}}</a>
                            <p  ng-if = "$index % 2 == 0 && ddb.value == 0" >{{ddb.value}}</p>
                            <p ng-if = "$index % 2 == 1">{{ddb.value|number:1}}%</p></td>
                        </tr>
                    </tbody>
                </table>
            </div><!--yc-tab-->
            <div class="jx-fz-12">同年级同课程属性学生成绩<a href="" class="jx-more-org" ng-click="tbClick()">查看 >></a>
            <span class="pull-right" ng-if="data.isnow == '0'">上学年预测准确率
            <a href="" ng-if="data.scale != '--'"class="jx-yc-h" ng-click="jzdClick()">{{data.scale|number:0}}%</a>
            <a href="" ng-if="data.scale == '--'" class="jx-yc-h" ng-click="jzdClick()">{{data.scale}}</a>
            </span>
            <span class="pull-right" ng-if="data.isnow == '1'">本学年预测准确率
            <a href="" ng-if="data.scale != '--'" class="jx-yc-h" ng-click="jzdClick()">{{data.scale|number:0}}%</a>
            <a href="" ng-if="data.scale == '--'"class="jx-yc-h" ng-click="jzdClick()">{{data.scale}}</a>
            </span>
            </div>
        </div><!--performance-change-->
        
        
        
        <div class="performance-change no-border" style="height:500px">
        	<div class="jx-mar-b-10">
                <span  class="dis-inb mar-r-6 jx-fz-16 jx-color-org">成绩分布</span>
            </div>
            <div class="text-center" echart config ="data.distribute.barCfg"></div>
        </div><!--performance-change-->
    
    </div>
     <div cs-window show="data.tbshow" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px;width:1000px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">同年级同课程同课程属性学生成绩</h3>
		        <a href="" class="popup-form-close" ng-click="data.tbshow=false">
		     <!--    	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix">
		    	<div class="table-box">
			    <table class="table table-bordered popup-form-table">
                	<thead>
                    	<tr>
                        	<td rowspan="2">班级</td>
                            <td rowspan="2">人数</td>
                            <td rowspan="2">平均<br>成绩</td>
                            <td colspan="2" ng-repeat ="dsb in data.thList">{{dsb.mc}}</td>
                        </tr>
                        <tr>
                        	<td ng-repeat = " sjb in data.fthList" >
                        	 <p ng-if = "$index % 2 == 0">人次</p>
                            <p ng-if = "$index % 2 == 1">占比%</p></td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr ng-repeat= "ssb in data.sameList">
                        	<td>{{ssb.mc}}</td>
                            <td>{{ssb.count}}</td>
                            <td>{{ssb.avg|number:1}}</td>
                            <td ng-repeat = " dddb in ssb.list">
                             <p ng-if = "$index % 2 == 0">{{dddb.value}}</p>
                            <p ng-if = "$index % 2 == 1">{{dddb.value|number:1}}%</p></td>
                        </tr>
                    </tbody>
                </table>
		        </div>
			</div>
		</div>
	</div>
	  <div cs-window show="data.jzdshow" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px;width:1000px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.courseName}} ({{data.natureName}})预测准确率</h3>
		        <a href="" class="popup-form-close" ng-click="data.jzdshow=false">
		     <!--    	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix">
		    	<div class="table-box">
			    <table class="table table-bordered popup-form-table">
                	<thead>
                    	<tr>
                            <td  ng-repeat ="sssb in data.th1List">{{sssb}}</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr ng-repeat= "dssb in data.precisionList">
                        	<td>{{dssb.name}}</td>
                            <td>{{data.natureName}}</td>
                            <td>
	                            <p ng-if="dssb.one != '--'">{{dssb.one|number:0}}%</p>
	                            <p ng-if="dssb.one == '--'">{{dssb.one}}</p>
                            </td>
                            <td>
                                <p ng-if="dssb.two != '--'">{{dssb.two|number:0}}%</p>
                                <p ng-if="dssb.two == '--'">{{dssb.two}}</p>
                            </td>
                            <td>
                                <p ng-if="dssb.three != '--'">{{dssb.three|number:0}}%</p>
                                <p ng-if="dssb.three == '--'">{{dssb.three}}</p>
                            </td>
                            <td>
	                            <p ng-if="dssb.four != '--'">{{dssb.four|number:0}}%</p>
	                            <p ng-if="dssb.four == '--'">{{dssb.four}}</p>
                            </td>
                            <td>
	                             <p ng-if="dssb.five != '--'">{{dssb.five|number:0}}%</p>
	                            <p ng-if="dssb.five == '--'">{{dssb.five}}</p>
                            </td>
                        </tr>
                    </tbody>
                </table>
		        </div>
			</div>
		</div>
	</div>
	  <%-- 分布 详情 --%>
    <div modal-form config="data.stu_detail.formConfig"></div>
</body>
</html>