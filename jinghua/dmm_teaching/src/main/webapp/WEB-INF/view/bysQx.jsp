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
<title>毕业生去向</title>
<%-- <base href="<%=basePath%>"/> --%>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/bysQx/controller.js"></script>
<script type="text/javascript" src="product/bysQx/service.js"></script>

</head>
<body ng-controller="controller">
	<div class="ss-mark-main">
    	<div class="header">
            <header class="header-tit">
                <b>毕业生去向</b>
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
              <div class="ul-no-center">
                <ul class="list-unstyled top-center clearfix no-m-btm">
                    <li>
                    <div class="cg-select-dropdown year-drop btn-group"
						style="width: 170px" cg-select data="data.timeList"
						on-change="timeSelect($value)" ng-model="data.schoolYear"
						value-field="id" display-field="mc"></div>
                    </li>
                    <li ng-class="{'has-green':data.timeList[0].id==data.schoolYear}">
                    <a href="" ng-click="data.timeList[0].id==data.schoolYear||timeSelect(data.timeList[0].id)" class="bg-green years">今年</a>
                    </li>
                    <li ng-if= "data.timeList.length>1" ng-class="{'has-green':data.timeList[1].id==data.schoolYear}">
                    <a href="" ng-click="data.timeList[1].id==data.schoolYear||timeSelect(data.timeList[1].id)" class="bg-green years">去年</a>
                    </li>
                </ul>
            </div>  
        <div class="separate-lr">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="block-tit"><p class="dis-inb">毕业生分布</p></div>
                	<div class="img-box" style="height: 350px" echart config="data.distribute.pieQxCfg"></div>
                </div>
                <div class="col-md-6 last-child">
                	<div class="block-tit"><p class="dis-inb">近五年毕业生去向</p></div>
                   	<div class="img-box" echart config="data.distribute.barQxCfg"></div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        
        <div class="separate-lr">
        	<div class="row">
            	<div class="col-md-6">
                	<div class="block-tit"><p class="dis-inb">升造（考研、保研、出国）分布</p></div>
                    <div class="img-box" style="height: 350px"echart config="data.distribute.pieSzCfg"></div>
                </div>
                <div class="col-md-6 last-child">
                	<div class="block-tit"><p class="dis-inb">近五年升造学生去向占比</p></div>
                    <div class="img-box" echart config="data.distribute.barSzCfg"></div>
                </div>
                <div class="clearfix"></div>
            </div>
        </div>
        
        <div class="performance-change">
        	<div class="block-tit">
            	<p class="dis-inb">各{{data.deptName}}毕业生对比</p>
            </div>
            <div class="img-box" echart config="data.distribute.barDeptCfg"></div>
        </div>
        
        <div class="performance-change">
        	<div class="block-tit">
            	<p class="dis-inb">历年未就业主要原因占比</p>
            </div>
             <div class="img-box" echart config="data.distribute.barReasonCfg"></div>
        </div>
        
    </div>
     <%-- 分布 详情 --%>
    <div modal-form config="data.stu_detail.formConfig"></div>
<!--     <div cs-window show="data.stu_detail.detail_show" autocenter="true" style="padding: 0;">
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
		            	<a href="" class="popup-form-export btn btn-default" ng-click="stuListDown()">
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
	</div> -->
</body>
</html>