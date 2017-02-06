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
<script type="text/javascript" src="product/score/controller.js"></script>
<script type="text/javascript" src="product/score/service.js"></script>
<script type="text/javascript" src="product/score/constant.js"></script>

</head>
<body ng-controller="controller">
<div class="ss-mark-wrapper">
<div class="ss-mark-main">
   	<div class="header">
        <header class="header-tit">
            <b>成绩概况</b>
        </header>
        <div ng-show="!data.advance.show" ng-click="advanceShow()" title="高级查询" class="bak-orange" align="center"><a href="" class="sets"></a></div>
        
        <div ng-show="data.advance.show" class="header-con last-performance" style="margin-top:18px;">
	        <div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" noborder="false"></div>
	    </div>
	    <div ng-show="data.advance.show" ng-click="advanceShow()" title="收起" class="bak-orange bak-orange-up" align="center"><a href="#" class="sets up"><i class="fa fa-angle-up"></i></a></div>
    </div>
    
    <article class="article diamonds-area">
    	<ul class="list-unstyled diamonds">
        	<li>
            	<a href="" class="has-box-shadw" title="一组数据中所有数据之和再除以这组数据的个数">
                	<p>平均数</p>
                    <div class="round color-cheng">{{data.abstract_data.avg}}</div>
            	</a>
            </li>
            <li>
            	<a href="" class="has-box-shadw" title="把所有数据高低排序后正中间的一个为中位数&#10;一种衡量集中趋势的指标">
                	<p>中位数</p>
                    <div class="round color-blue">{{data.abstract_data.middle}}</div>
            	</a>
            </li>
            <li>
            	<a href="" class="has-box-shadw" title="一组数据中出现次数最多的数值叫众数&#10;就是一组数据中占比例最多的那个数">
                	<p class="two-words">众数</p>
                    <div class="round color-green">{{data.abstract_data.mode}}</div>
            	</a>
            </li>
            <li class="has-two">
            	<ul class="list-unstyled clearfix">
                    <li class="pull-left">
                        <a href="" class="has-box-shadw f-c pull-left" title="各个数据分别与其平均数之差的平方的和的平均数&#10;方差不仅仅表达了样本偏离均值的程度&#10;更是揭示了样本内部彼此波动的程度">
                            <p class="two-words">方差</p>
                            <div class="round color-ju-l">{{data.abstract_data.fc}}</div>
                        </a>
                        <a href="" class="has-box-shadw b-z-c pull-left" title="标准差是方差的算术平方根&#10;标准差能反映一个数据集的离散程度">
                            <p>标准差</p>
                            <div class="round color-greener">{{data.abstract_data.bzc}}</div>
                        </a>
                    	<span class="mid-line pull-left"></span>
                    </li>
                </ul>
           </li>
           <li class="last">
            	<p class="text-center">{{data.abstract_data.name}}</p>
                <div style="width:230px;" cg-select class="xueqi-drop" data="data.bzdm_xnxq" on-change="changeXnxq($value,$data)" ng-model="data.model.xnxq"></div>
                <div class="center-l-r">
                    <div class="pull-left ben-zhuan" style="width:100px;" cg-select data="data.bzdm_edu" on-change="changeEdu($value,$data)" ng-model="data.param.edu"></div>
                    <div class="cg-select-dropdown btn-group ji-dian pull-right" style="width:100px;">
                        <button type="button" class="btn btn-default ss-drop-input dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        	<span class="input-area">绩点</span>
                        	<span class="pull-right">
                                <span class="line"></span>
                                <span class="caret"></span>
                                <span class="sr-only">Toggle Dropdown</span>
                            </span>
                        </button>
                        <ul class="dropdown-menu">
                            <li class="active"><a href="">绩点</a></li>
                        </ul>
                    </div>
                </div>
            </li>
            <div class="clearfix"></div>
        </ul>
    </article>
    
    <section class="gai-kuang-img" part-modal show-modal="data.group.mask">
    	<div class="gai-k-l pull-left">
        	<div class="img-box img-one">
        		<div echart config="data.group.option" class="center-block"></div>
            </div>
        </div>
        <div class="gai-k-r pull-right">
        	<ul class="list-unstyled">
            	<li title="课程成绩大于等于{{data.group.better_name}}分的比例">
                	<p>优秀率</p>
                    <div class="num-block"><a href="">{{data.group.better}}%</a></div>
                </li>
                <li title="课程成绩小于{{data.group.fail_name}}分的比例">
                	<p>不及格率</p>
                    <div class="num-block"><a href="">{{data.group.fail}}%</a></div>
                </li>
                <li title="课程成绩小于{{data.group.rebuild_name}}分的比例">
                	<p>重修率</p>
                    <div class="num-block"><a href="">{{data.group.rebuild}}%</a></div>
                </li>
                <li title="平均绩点低于平均数的学生所占比例">
                	<p>低于平均数</p>
                    <div class="num-block"><a href="">{{data.group.under}}%</a></div>
                </li>
            </ul>
        </div>
        <div class="clearfix"></div>
    </section>
    
    <article class="article diamonds-area" part-modal show-modal="data.gpaCourse.mask">
    	<ul class="list-unstyled diamonds-tab clearfix">
        	<li ng-repeat="obj in data.gpaCourse.list" style="width:{{100/(data.gpaCourse.list.length+1)}}%;">
            	<a href="" class="has-box-shadw">
                	<p>{{obj.mc}}</p>
                    <div class="round" ng-class="obj.cls">{{obj.value}}</div>
            	</a>
            </li>
            <li class="ver-top" style="min-width:91px;">
            	<div style="width:91px;" class="pull-left" cg-select data="data.gpaCourse.bzdm" on-change="changeScoreType($value,$data)" ng-model="data.gpaCourse.type"></div>
            </li>
        </ul>
    </article>
    
    <section class="ss-mark-tab-box" part-modal show-modal="data.grid.mask">
    	<div class="ss-mark-tab">
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
                <p class="page pull-right" ng-if="data.grid.list.length>0">
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