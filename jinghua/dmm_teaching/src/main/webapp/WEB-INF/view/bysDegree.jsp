<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ base + "/";
	String projectPath = request.getContextPath();
	int port = request.getServerPort();
	String ip = request.getServerName();
	String root = "http://"+ip+":"+port+projectPath+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>毕业及学位授予分析</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="<%=root%>/static/resource/css/new-pagination-style.css">
<script type="text/javascript" src="product/bysDegree/controller.js"></script>
<script type="text/javascript" src="product/bysDegree/service.js"></script>

</head>
<body ng-controller="controller">
    <div class="ss-mark-main">
    	<div class="header">
            <header class="header-tit">
                <b>毕业及学位授予分析</b>
            </header>
            <div ng-show="!data.advance.show" ng-click="advanceShow()"
				title="高级查询" class="bak-orange" align="center">
				<a href="" class="sets"></a>
			</div>

			<div ng-show="data.advance.show" class="header-con last-performance"
				style="margin-top: 18px;">
				<div cg-custom-comm source="data.advance.source"
					on-change="advanceChange($data)" expand="true" noborder="false"></div>
			</div>
			<div ng-show="data.advance.show" ng-click="advanceShow()" title="收起"
				class="bak-orange bak-orange-up" align="center">
				<a href="#" class="sets up"><i class="fa fa-angle-up"></i></a>
			</div>
        </div>
        
        <div class="ss-mark-tab-box no-bottom">
            <div>
            		<div class="cg-select-dropdown btn-group dis-inb mar-r-6"
				style="width: 170px" cg-select data="data.timeList"
				on-change="timeSelect($value)" ng-model="data.schoolYear"
				value-field="id" display-field="mc"></div>
					<div class="cg-select-dropdown btn-group dis-inb mar-r-6"
				style="width: 130px" cg-select data="data.typeList"
				on-change="typeSelect($value)" ng-model="data.type"
				value-field="id" display-field="mc"></div>
            </div>
        </div>
        	
            
        <div class="performance-change pad-t-15 pad-b-15">
            <ul class="jx-biye-row list-unstyled clearfix">
            	<li>
                	<div>
                    	<p title="应该毕业的学生总数">毕业生/人</p>
                        <div class="jx-rectangle"><span>{{topData.all}}</span></div>
                    </div>
                </li>
                <li class="jx-biye-zu">
                	<div>
                    	<p title="实际获得学位证学生数">毕业/人</p>
                        <div class="jx-rectangle"><span>{{topData.by}}</span></div>
                    </div>
                </li>
                <li>
                	<div>
                    	<p title="实际毕业学生数除以应该毕业的学生总数">毕业率</p>
                        <div class="jx-rectangle" ng-click="stuListClick(null,'bylv','毕业率',null,'1')"><span>{{topData.bylv|number:1}}%</span>
                        <span ng-if="topData.bybh !=null&&topData.bybh >0"class="text-greener jx-rectangle-add">+{{topData.bybh|number:1}}%</span>
                        <span ng-if="topData.bybh !=null&&topData.bybh ==0"class="jx-rectangle-add">{{topData.bybh|number:1}}%</span>
                        <span ng-if="topData.bybh ==null"class="jx-rectangle-add">--</span>
                        <span ng-if="topData.bybh !=null&&topData.bybh <0"class="text-red jx-rectangle-add">{{topData.bybh|number:1}}%</span>
                        </div>
                    </div>
                </li>
                <li class="jx-biye-zu">
                	<div>
                    	<p title="实际获得学位证学生数">学位授予/人</p>
                        <div class="jx-rectangle"><span>{{topData.sy}}</span></div>
                    </div>
                </li>
                <li>
                	<div>
                    	<p title="实际获得学位证学生数除以应该毕业的学生总数">学位授予率</p>
                        <div class="jx-rectangle" ng-click="stuListClick(null,'sylv','学位授予率',null,'1')"><span>{{topData.sylv|number:1}}%</span>
                        <span ng-if="topData.sybh !=null&&topData.sybh >0"class="text-greener jx-rectangle-add">+{{topData.sybh|number:1}}%</span>
                        <span ng-if="topData.sybh !=null&&topData.sybh ==0"class="jx-rectangle-add">{{topData.sybh|number:1}}%</span>
                        <span ng-if="topData.sybh ==null"class="jx-rectangle-add">--</span>
                        <span ng-if="topData.sybh !=null&&topData.sybh <0"class="text-red jx-rectangle-add">{{topData.sybh|number:1}}%</span>
                        </div>
                    </div>
                </li>
            </ul>
        </div><!--performance-change-->
        
        
        <div class="performance-change">
        	<div class="ss-mark-tab-tit">
                <a href="#" ng-class="{'active' : y.id ==data.tabLx }" ng-click="y.id ==data.tabLx||thSelect(y.id,y.mc)" ng-repeat="y in data.thList">按{{y.mc}}<span></span><div class="triangle"></div></a>
            </div>
			<div class="ss-mark-tab-con">
				<table class="table table-bordered class-tab">
                	<thead>
                    	<tr>
                        	<td style="width:16%"><span class="two-words">{{data.tabMc}}</span></td>
                        	<td ng-repeat = " z in data.fthList">
                            	<div>
                                	<p><a href="" ng-click= "(data.order == z.id && data.asc) ||fthSelect(z.id,true)"ng-class="{'on':data.order == z.id && data.asc}"><i class="fa fa-sort-up"></i></a></p>
                                	<span>{{z.mc}}</span>
                                	<p><a href="" ng-click= "(data.order == z.id && !data.asc) ||fthSelect(z.id,false)" ng-class="{'on':data.order == z.id && !data.asc}"><i class="fa fa-sort-down"></i></a></p>
                            	</div>
                            </td>
                       </tr>
                    </thead>
                                
                    <tbody>
                    	<tr ng-repeat="x in data.tableList">
                        	<td><span class="badge-order">{{(vm1.page.index-1)*vm1.page.size+$index+1}}</span>
                            	<p>{{x.name}}</p>
                            </td>
                            <td>{{x.all}}</td>
                            <td>{{x.by}}</td>
                            <td class= "jx-tab-h-green" ng-click="stuListClick('dept','bylv','毕业率',x.id,'1')">{{x.bylv|number:1}}%</td>
                            <td  ng-if="x.bybh!= null&&x.bybh >0" class="text-greener">+{{x.bybh|number:1}}%</td>
                            <td  ng-if="x.bybh!= null&& x.bybh ==0">{{x.bybh|number:1}}%</td>
                            <td  ng-if="x.bybh ==null">--</td>
                            <td  ng-if="x.bybh!= null&& x.bybh <0"class="text-red">{{x.bybh|number:1}}%</td>
                            <td>{{x.sy}}</td>
                            <td class= "jx-tab-h-green" ng-click="stuListClick('dept','sylv','学位授予率',x.id,'1')">{{x.sylv|number:1}}%</td>
                             <td  ng-if="x.sybh!= null&&x.sybh >0" class="text-greener">+{{x.sybh|number:1}}%</td>
                            <td  ng-if="x.sybh!= null&&x.sybh ==0"  class="text-greener">{{x.sybh|number:1}}%</td>
                             <td  ng-if="x.sybh ==null">--</td>
                            <td  ng-if="x.sybh!= null&&x.sybh <0"class="text-red">{{x.sybh|number:1}}%</td>
                        </tr>
					</tbody> 
				</table>
            </div><!--end ss-mark-tab-con-->
              <div class="jx-fz-12" clearfix>
            	<a href="" class="jx-more-org" ng-if="vm1.page.total>vm1.page.size && data.fyShow"ng-click="loadAll()">查看全部</a>
            	<a href="" class="jx-more-org" ng-if="!data.fyShow" ng-click="packUp()">收起</a>
		    	    <div  class="jx-page-box pull-right">
		    	    <div pagination total-items="vm1.page.total" ng-model="vm1.page.index" 
						items-per-page="vm1.page.size" max-size="5" class="jx-page pull-right" boundary-links="true"></div>
           		    	    	<span class="jx-page-text">共{{vm1.pagecount}}页，{{vm1.page.total}}条记录</span>
                          </div>
            </div>  
            <div><div class="center-block" echart config ="data.distribute.barDeptCfg"></div></div>
        </div><!--performance-change-->
       
    	<div class="performance-change">
        	<div class="block-tit">
            	<p class="dis-inb">历年毕业生成绩</p>
            	 <div style="margin-top:5px;" class="pull-right">
	        		    <div tool-tip placement="left" class="text-green">
			       		<p>平均成绩:每个学生每科成绩乘以该课程的学分的总和除以总学分的结果相加除以总学生数</p>
						<p> 中位数:把所有数据高低排序后正中间的一个为中位数，一种衡量集中趋势的指标;</p>
						<p>众数:一组数据中出现次数最多的数值叫众数，就是一组数据中占比例最多的那个数;</p>
						<p>方差:各个数据分别与其平均数之差的平方的和的平均数，方差不仅仅表达了样本偏离均值的程度，更是揭示了样本内部彼此波动的程度;</p>
						<p>标准差:标准差是方差的算术平方根，标准差能反映一个数据集的离散程度;</p>
	        	    </div></div> 
                <div class="radio radio-inline jx-radio jx-fz-12" ng-click="chartSelect(sb.id)"ng-repeat=" sb in data.chartList">
                	<input  ng-if="sb.id == data.chartLx" type="radio" name="chengji" value="" id="{{sb.id}}" checked><label for="{{sb.id}}">{{sb.mc}}</label>
                    <input  ng-if="sb.id != data.chartLx"type="radio" name="chengji" value="" id="{{sb.id}}" >
                </div>
            </div>
            <div><div class="center-block" echart config ="data.distribute.barScoreCfg"></div></div>
        </div>
        
        <div class="performance-change no-border">
        	<div class="block-tit">
            	<p class="dis-inb">历年毕业生绩点分布</p>
            </div>
            <div><div class="center-block" echart config ="data.distribute.barGpaCfg"></div></div>
        </div><!--performance-change-->
        
    </div><!--end ss-mark-main-->
    <div cs-window show="data.stu_detail.detail_show" autocenter="true" style="padding: 0;">
		<div class="popup-form popup-form-blue" style="margin:0px">
			<div class="popup-form-head clearfix">
		    	<h3 class="popup-form-title">历年{{tableLx}}</h3>
		        <a href="" class="popup-form-close" ng-click="data.stu_detail.detail_show=false">
		     <!--    	<img src="static/resource/css/image/popup-form-close.png" alt=""> -->
		            <img src="static/resource/css/image/popup-form-close-red.png" alt="">
		        </a>
		    </div>
		    <div class="center-block" style ="width:800px;height:500px" echart config ="data.distribute.lineCfg"></div>
		</div>
	</div>
</body>
</html>