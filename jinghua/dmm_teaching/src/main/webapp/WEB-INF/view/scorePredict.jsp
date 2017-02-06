<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生成绩概况</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/scorePredict/controller.js"></script>
<script type="text/javascript" src="product/scorePredict/service.js"></script>

</head>
<body ng-controller="controller">
  <div class="xuegong-zhuti-content">
<div class="ss-mark-wrapper">
    <div class="ss-mark-main">
    	<div class="header">
            <header class="header-tit">
                <b>成绩预测（由逃课行为预测）（辅导员）</b>
            </header>
           <!--      <div ng-show="!data.advance.show" ng-click="advanceShow()" title="高级查询" class="bak-orange" align="center"><a href="" class="sets"></a></div>
        
        <div ng-show="data.advance.show" class="header-con last-performance" style="margin-top:18px;">
	        <div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="false"></div>
	    </div>
	    <div ng-show="data.advance.show" ng-click="advanceShow()" title="收起" class="bak-orange bak-orange-up" align="center"><a href="#" class="sets up"><i class="fa fa-angle-up"></i></a></div>
            -->
        </div>
        
        <div class="ss-mark-tab-box no-bottom">
            <div class="cg-select-dropdown btn-group dis-inb mar-r-6" style="width:130px">
  				<div class="btn-group" cg-select data="data.bzdm_edu" on-change="changEdu($value,$data)" ng-model="data.value_edu"></div>
            </div>
            <div class="dis-inb">
                <span class="qipao"></span>
                <span style="color:red">预测日期：{{data.DATE_NOW}}（数据来源截止{{data.DATE_PRO}}）</span>
            </div>
          <!--   <div class="jx-fz-12 postion-r jx-mar-b-10">
            <div class="btn-group" cg-select data="data.bzdm_xn" on-change="changXn($value,$data)" ng-model="data.value_year" value-field="id" display-field="mc"></div>
            </div> -->
            <div class="jx-fz-12 postion-r jx-mar-b-10">
            	<div class="jx-radio-box jx-has-more-r" >
                    <div ng-class="{'radio radio-inline jx-radio' : x.order == $index,'radio radio-inline jx-radio radio-l-widther': x.order != $index}" ng-repeat = " x in data.bzdm_xn" ng-click = "data.value_year == x.id ||changXn(x.id)" >
                        <input ng-if="x.id == data.value_year"type="radio" name="xueqi" value="" id="{{x.id}}" checked><label for="{{x.id}}">{{x.mc}}</p></label>
                        <input ng-if="x.id != data.value_year"type="radio" name="xueqi" value="" id="{{x.id}}" >
                    </div>
                </div>
                <a href="" class="more-r jx-more-org" ng-click = "loadMore()" ng-if="!data.timeshow && data.allTimeList.length > 6">更多 >></a>
             <a href="" class="more-r jx-more-org" ng-click = "loadMore()" ng-if="data.timeshow"><< 收起</a>
            </div>
        </div><!--ss-mark-tab-box-->
            
        <div class="text-center yc-tab-head"><a href=""  ng-repeat="item in data.majormc"  ng-click="majorTypeCk($index)" title="{{item.MAJORMC}}" class="{{data.majorcss[$index]}}">{{item.MAJORMC}}</a></div><!-- <a href="" class="active">机械专业</a> -->
        <div class="performance-change no-top">
            <div><span class="jx-note-green">我管理的班级有{{XfyjCfg.classsum.CLASSSUM}}个，共{{XfyjCfg.stusum.STUSUM}}人。</span></div>
            <div class="jx-mar-b-10">
                <span  class="dis-inb mar-r-6 jx-fz-16 jx-color-org">预测概况</span>
                <div class="cg-select-dropdown btn-group dis-inb mar-r-6" style="width:140px">
               		<div class="btn-group" cg-select data="data.coursetpye" on-change="changCourse($value,$data)" ng-model="data.value_courseType"></div>
                </div>
                <div class="cg-select-dropdown btn-group dis-inb" style="width:300px">
        			 <div class="btn-group" title="{{data.value_coursemc}}" cg-select data="data.coursedata" on-change="changCourseData($value,$data)" ng-model="data.value_courseData"></div>
                </div>
            </div>
            <div class="yc-tab">
            	<table class="table table-bordered">
                	<thead>
                    	<tr >
                        	<td rowspan="2" ng-repeat="item in gkTableTitle" ng-show="$index<3">{{item}}</td>
                    <!--         <td rowspan="2">人数</td>
                            <td rowspan="2">课程<br>（门）</td>
                            <td colspan="2">90-100分</td>
                            <td colspan="2">80-90分</td>
                            <td colspan="2">70-80分</td>
                            <td colspan="2">60-70分</td> -->
                            <td colspan="2"  ng-repeat="item in gkTableTitle" ng-show="$index>=3"> {{item}}</td>
                        </tr>
                        <tr>
                        	<td>门次</td>
                            <td>占比%</td>
                            <td>门次</td>
                            <td>占比%</td>
                            <td>门次</td>
                            <td>占比%</td>
                            <td>门次</td>
                            <td>占比%</td>
                            <td>门次</td>
                            <td>占比%</td>
                        </tr>
                    </thead>
                    <tbody>
                    	<tr ng-repeat="item in XfflCfg.scoreinfo">
                        	<td ng-repeat="(key,value) in item">
	                        	<span ng-if="key=='CL01'||key=='CL05'||key=='CL07'||key=='CL09'||key=='CL11'||key=='CL13'">{{value}}</span>
	                        	<a href="" class="text-green" ng-click="abstractDetailClick('gk',key,item)"  ng-if="key!='CL01'&&key!='CL05'&&key!='CL07'&&key!='CL09'&&key!='CL11'&&key!='CL13'">{{value}}</a>
                           </td>
                        </tr>
                        <tr ng-repeat="item in XfflCfg.count" ng-show="XfflCfg.scoreinfo.length!=0" >
                        	<td>合计</td>
                            <td ng-repeat="(key,value) in item">{{value}}</td>
                     <!--        <td >{{item.CL02}}</td>
                            <td >{{item.CL03}}</td>
                            <td >{{item.CL04}}</td>
                            <td >{{item.CL05}}</td>
                            <td >{{item.CL06}}</td>
                            <td >{{item.CL07}}</td>
                            <td >{{item.CL08}}</td>
                            <td >{{item.CL09}}</td>
                            <td >{{item.CL10}}</td>
                            <td >{{item.CL11}}</td>
                            <td >{{item.CL12}}</td> -->
                        </tr>
                    </tbody>
                </table>
                        <span ng-show="data.cjgk">暂无成绩预测概况数据</span>
            </div><!--yc-tab-->
            <div class="jx-fz-12">同年级同专业学生成绩<a href="" ng-click="checkType()" class="jx-more-org">查看 >></a></div>
        </div><!--performance-change-->
        
        <div class="performance-change">
        	<div class="jx-mar-b-10">
                <span  class="dis-inb mar-r-6 jx-fz-16 jx-color-org">成绩分布</span>
            </div>
            <div class="yc-tab">
            	<table class="table table-bordered">
                	<thead>
                    	<tr>
                        	<td rowspan="2">课程</td>
                            <td rowspan="2">课程<br>属性</td>
                            <td rowspan="2">平均<br>成绩</td>
                            <td rowspan="2">{{data.yczql}}<br>%</td>
                            <td colspan="2">90-100分</td>
                            <td colspan="2">80-90分</td>
                            <td colspan="2">70-80分</td>
                            <td colspan="2">60-70分</td>
                            <td colspan="2"> <60分</td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                        	<td>门次</td>
                            <td>占比%</td>
                            <td>门次</td>
                            <td>占比%</td>
                            <td>门次</td>
                            <td>占比%</td>
                            <td>门次</td>
                            <td>占比%</td>
                            <td>门次</td>
                            <td>占比%</td>
                            <td>&nbsp;</td>
                        </tr>
                    </thead>
                    <tbody>
                      	<tr ng-repeat="item in XffbCfg">
                        	<td  ng-repeat="(key,value) in item" >
                        	<span ng-if="key=='CL01'||key=='CL02'||key=='CL03'||key=='CL06'||key=='CL08'||key=='CL10'||key=='CL12'||key=='CL14'">{{value}}</span>
                        	<a href="" class="text-green" ng-click="accuracyCk(item)"  ng-if="key=='CL04'">{{value}}</a>
                        	<a href="" class="text-green" ng-click="abstractDetailClick('fb',key,item)"  ng-if="key!='CL01'&&key!='CL02'&&key!='CL04'&&key!='CL03'&&key!='CL06'&&key!='CL08'&&key!='CL10'&&key!='CL12'&&key!='CL14'">{{value}}</a>
                        	</td>
                            <td><a href="" ng-click="contrastCk($index)" class="text-green">去对比</a></td>
                        </tr>
                    </tbody>
                </table>
                <span ng-show="data.cjfb">暂无预测成绩分布数据</span>
            </div><!--yc-tab-->
        </div><!--performance-change-->
        
        <div class="performance-change no-border">
        	<div class="jx-mar-b-10">
                <span  class="dis-inb mar-r-6 jx-fz-16 jx-color-org">成绩分布</span>
            </div>
            <div class="text-center"><div echart config="XffbByCfg"></div>
        </div><!--performance-change-->
    
    </div><!--end ss-mark-main-->
</div>
   <%-- 下钻 详情 --%>
  <div modal-form config="data.abstract_detail.formConfig"></div>
 	<%--查看，去对比详情 --%>
	<div cs-window show="data.abstract_detail.detail_show" auto-center="true" style="padding: 0;"> <!-- click-disappear="true" -->
		<div class="popup-form popup-form-blue" style="margin:0px">
		    <div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">{{data.abstract_detail.title}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.abstract_detail.detail_show=false">
		        <!-- 	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="popup-form-body clearfix" part-modal show-modal="data.abstract_detail.mask">
		    	<div class="table-box">
			    	<table class="table table-bordered popup-form-table">
			        	<thead>
			            	<tr ng-show="showTitleRow1">
			                  <!--   <td ng-repeat="val in data.abstract_detail.headers">{{val}}</td> -->
			                <td rowspan="2" ng-show="$index<{{data.index}}" ng-repeat="val in data.abstract_detail.headers">{{val}}</td>
                            <td colspan="2" ng-show="$index>={{data.index}}" ng-repeat="val in data.abstract_detail.headers">{{val}}</td>
			                </tr>
			            	<tr ng-show="!showTitleRow1">
			                  <!--   <td ng-repeat="val in data.abstract_detail.headers">{{val}}</td> -->
			                <td ng-repeat="val in data.abstract_detail.headers">{{val}}</td>
			                </tr>
			            <tr ng-show="showTitleRow2">
                        	<td>人次</td>
                            <td>占比%</td>
                            <td>人次</td>
                            <td>占比%</td>
                            <td>人次</td>
                            <td>占比%</td>
                            <td>人次</td>
                            <td>占比%</td>
                            <td>人次</td>
                            <td>占比%</td>
                        </tr>
			            </thead>
			            <tbody>
			            	<tr ng-repeat="obj in data.abstract_detail.list">
			                    <td ng-repeat="(key,value) in obj" ng-show="key!='nm'">{{value}}</td>
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
	</div>
</body>
</html>