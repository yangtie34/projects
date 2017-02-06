<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生成绩历史分析</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/scoreHistory/controller.js"></script>
<script type="text/javascript" src="product/scoreHistory/service.js"></script>

</head>
<body ng-controller="controller">
<div class="ss-mark-wrapper">
<div class="ss-mark-main">
   	<div class="header">
        <header class="header-tit">
            <b>学生成绩历史分析</b>
        </header>
        <div ng-show="!data.advance.show" ng-click="advanceShow()" title="高级查询" class="bak-orange" align="center"><a href="" class="sets"></a></div>
    
	    <div ng-show="data.advance.show" class="header-con last-performance" style="margin-top:18px;">
	        <div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="false"></div>
	    </div>
	    <div ng-show="data.advance.show" ng-click="advanceShow()" title="收起" class="bak-orange bak-orange-up" align="center"><a href="#" class="sets up"><i class="fa fa-angle-up"></i></a></div>
    </div>
       
    <div class="performance-change">
    	<p class="block-tit">历年成绩变化</p>
        <div class="img-box img-one img-right">
        	<div echart config="data.history_year"></div>
        </div>
    </div>
    
    <div class="separate-lr">
    	<div class="row">
        	<div class="col-md-6">
            	<p class="block-tit">各性别成绩分析</p>
                <div class="img-box img-one">
                	<div echart config="data.sex"></div>
               	</div>
            </div>
            <div class="col-md-6 last-child">
            	<p class="block-tit">各年级成绩分析</p>
                <div class="img-box img-lf-per">
                	<div echart config="data.grade"></div>
                </div>
            </div>
            <div class="clearfix"></div>
        </div>
    </div>
    
    <section class="ss-mark-tab-box">
    	<div class="ss-mark-tab" part-modal show-modal="data.grid.mask">
    		<div class="ss-mark-tab-tit">
            	<a href="" ng-class="{active:tab.show}" ng-repeat="tab in data.tabs" ng-click="tab.show || changeTab(tab.id, tab)">{{tab.mc}}<span></span><div class="triangle"></div></a>
    		</div>
            <div class="ss-mark-tab-con">
            	<table class="table table-bordered">
                	<thead>
                    	<tr>
                    		<td><span class="two-words">{{data.gridParam.tab_mc}}</span></td>
                            <td ng-repeat="obj in data.grid.header">
                            	<div class="four-words">
                                	<p><a href="" ng-class="{on:obj.asc=='asc'}" ng-click="obj.asc=='asc' || order(obj,'asc')"><i class="fa fa-sort-up"></i></a></p>
                                    <span title="{{obj.title}}">{{obj.mc}}</span>
                                    <p><a href="" ng-class="{on:obj.asc=='desc'}" ng-click="obj.asc=='desc' || order(obj,'desc')"><i class="fa fa-sort-down"></i></a></p>
                                </div>
                            </td>
                        </tr>
                    </thead>
                    <tbody>
                      	<tr ng-repeat="obj in data.grid.list">
                			<td><span class="badge-order">{{obj._index}}</span>
                				<p>{{obj.name}}</p>
                			</td>
                			<td ng-class="{'td-active':data.grid.header[0].asc!=null}">{{obj.avg}}</td>
                            <td ng-class="{'td-active':data.grid.header[1].asc!=null}">{{obj.middle}}</td>
                            <td ng-class="{'td-active':data.grid.header[2].asc!=null}">{{obj.mode}}</td>
                            <td ng-class="{'td-active':data.grid.header[3].asc!=null}">{{obj.fc}}</td>
                            <td ng-class="{'td-active':data.grid.header[4].asc!=null}">{{obj.bzc}}</td>
                            <td ng-class="{'td-active':data.grid.header[5].asc!=null}">{{obj.better}}%</td>
                            <td ng-class="{'td-active':data.grid.header[6].asc!=null}">{{obj.fail}}%</td>
                            <td ng-class="{'td-active':data.grid.header[7].asc!=null}">{{obj.rebuild}}%</td>
                            <td ng-class="{'td-active':data.grid.header[8].asc!=null}">{{obj.under}}%</td>
                		</tr>
                    </tbody>
                </table>
                <div ng-if="data.grid.list.length==0" class="no-date ng-scope">暂无数据...</div>
                <p class="page pull-right">
                    <a href="" ng-click="data.gridParam.index==1 || clickPage(-1)" ng-class="{disable:data.gridParam.index<=1}" class="prevv"></a>
	             	<a href="" ng-click="data.gridParam.index==data.gridParam.pageCount || clickPage(1)" ng-class="{disable:data.gridParam.index>=data.gridParam.pageCount}" class="nextt"></a>
	            </p>
                <div class="clearfix"></div>
            </div>
        </div>
    </section>
    
</div>
</div>
</body>
</html>