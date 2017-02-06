<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>助学金</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/subsidy/controller.js"></script>
<script type="text/javascript" src="product/scholarship/service_base.js"></script>
<script type="text/javascript" src="product/subsidy/constant.js"></script>
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
	<!-- 导航 -->
	<div ng-show="data.advance.show" class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone student-source xueji-buliang-yidong">
		<div cg-custom-comm source="data.advance.source" on-change="advanceChange($data)" expand="true" no-border="true"></div>
	</div>
	<!-- 导航2 -->
	<div class="xuegong-zhuti-content-main-tit xuegong-zhuti-content-main-tit-alone scholarship">
		<!-- 学年 -->
		<div class="xuegong-zhuti-dropdown-box">
			<div class="btn-group" cg-select data="data.bzdm_xn" on-change="changXn($value,$data)" ng-model="data.value_year" value-field="id" display-field="mc"></div>
		</div>
		<!-- 学历 -->
		<div class="xuegong-zhuti-dropdown-box">
			<div class="btn-group" cg-select data="data.bzdm_edu" on-change="changEdu($value,$data)" ng-model="data.value_edu"></div>
		</div>
		<div class="btn-basic">
			<a href="" ng-click="changXn(data.thisYear)" ng-class="{on:data.thisYear==data.value_year}" class="btn-blue">今年</a>
           	<a href="" ng-click="changXn(data.lastYear)" ng-class="{on:data.lastYear==data.value_year}" class="btn-blue">去年</a>
		</div>
		<div class="clearfix"></div>
	</div>
	
	<div class="xuegong-border scholarship-top-overview">
    	<p>{{data.dateName}}共有<b><a href="" class="text-green" ng-click="getAllStu('all')"> {{data.abstract_data.count}} </a></b>人获得助学金，共计<b><a href="" class="text-green" ng-click="getAllStu('all')"> {{data.abstract_data.money | number:0}} </a></b>元，覆盖率<b><span class="text-green"> {{data.abstract_data.scale}}%</span></b></p>
    	<div class="row">
        	<div class="col-md-6">	
            	 <div class="scholarship-top-overview-table mCustomScrollbar">
                 	<table class="table tab-many">
                    	<tbody>
                    		<tr ng-class="obj.cls" ng-repeat="obj in data.type.list">
                            	<td title="{{obj.name}}">{{obj.name}}</td>
                                <td><span class="cursor-hand" ng-click="getShipType(obj,'type')">{{obj.count}}</span>人</td>
                                <td>覆盖率：{{obj.scale}}%</td>
                            </tr>
                    	</tbody>
                    </table>
                  </div>
            </div>
            <div class="col-md-6">
            	<div class="img-box no-top">
            		<div style="height:300px;" echart config="data.type.radar"></div>
				</div>
            </div>
        </div>
    </div>
	
	<div class="xuegong-separate-tb scholarship-fenbu">
    	<div class="xuegong-zhuti-content-main-tit">
        	<b class="text-blue">助学金{{data.deptData.deptMc}}分布</b>
            <div class="xuegong-zhuti-dropdown-box">
                <div class="btn-group" cg-select data="data.deptData.bzdm" on-change="changeDeptData($value,$data)" ng-model="data.deptData.id"></div>
            </div>
        </div>
        <div class="xuegong-separate-con">
        	<div class="img-box"><div class="img-box-only"><div echart config="data.deptData.option"></div></div></div>
        </div>
	</div>
	
	<div class="xuegong-separate-tb scholarship-fenbu">
		<div class="xuegong-separate-con">
	    	<div class="pull-left scholarship-sthdent-behavior">
	        	<p class="text-blue tit-second"><b>获得助学金学生行为</b></p>
	        	<div class="img-box"><div class="img-box-only">
	            	<div style="height:310px;" echart config="data.behavior.radar"></div>
            	</div></div>
	        </div>
	        <div class="pull-left scholarship-coverage-rate">
	         	<p class="text-blue tit-second"><b>助学金覆盖率</b></p>
	            <div>
	            	<table class="table table-bordered xuegong-zhuti-table tab-no-btm">
	                    <tbody>
	                    	<tr>
	                        	<td><b>年级</b></td>
	                            <td><b>本次评选覆盖率</b></td>
	                            <td><b>累计覆盖率</b>
	                            	<span class="message-icon">
										<a href="" class="message-img"></a>
										<div class="question-area-box message-box question-area-box-top">
										    <div class="question-area">
											<img src="static/resource/images/xues-gognzuozhe/question-k.png">
											<p><b>累计覆盖率：</b>学生生涯获得助学金的比例</p>
										    </div>
										</div>
									</span>
	                            </td>
	                        </tr>
	                    	<tr ng-repeat="obj in data.coverageGrade">
	                        	<td>{{obj.name}}</td>
	                            <td><b class="text-green">{{obj.scale_one}}%</b></td>
	                            <td><b class="text-green">{{obj.scale_all}}%</b></td>
	                        </tr>
	                    </tbody>
	                </table>
	            </div>
	            <div ng-if="data.coverageGrade.length==0" class="no-date ng-scope">暂无数据...</div>
	        </div>
	        <div class="clearfix"></div>
        </div>
	</div>
	
	<div class="xuegong-separate-tb scholarship-cumulative-coverage">
    	<div class="xuegong-zhuti-content-main-tit">
        	<b class="text-blue">助学金{{data.coverageDept.deptMc}}累计覆盖率</b>
            <div class="xuegong-zhuti-dropdown-box">
                <div class="btn-group" cg-select data="data.coverageDept.bzdm" on-change="changeCoverageDept($value,$data)" ng-model="data.coverageDept.id"></div>
        	</div>
        </div>
        <div class="xuegong-separate-con"><div class="img-box">
        	<div class="img-box-only">
	        	<div echart config="data.coverageDept.option"></div>
        	</div>
            <p class="text-center"><i class="fa fa-warning text-pink"></i>
            {{data.coverageDept.mc}}累计覆盖率：
            	{{data.coverageDept.mc_max}}最高<b class="text-pink">（{{data.coverageDept.id_max}}%）</b>，
            	{{data.coverageDept.mc_min}}最低<b class="text-pink">（{{data.coverageDept.id_min}}%）</b>
            	<u class="pull-right"><a href="" class="text-blue"></a></u></p>
        </div></div>
    </div>
    
	<div class="xuegong-separate-tb scholarship-historical-change">
    	<div class="xuegong-zhuti-content-main-tit">
        	<b class="text-blue">历年助学金变化</b>
            <div class="xuegong-zhuti-dropdown-box">
                <div class="btn-group" cg-select data="data.history.bzdm" on-change="changeHistory($value,$data)" ng-model="data.history.id"></div>
           </div>
        </div>
        <div class="xuegong-separate-con">
        	<div class="img-box">
        		<div class="img-box-only"><div echart config="data.history.option"></div></div>
        	</div>
        </div>
    </div>
	
</div>
</div>
	    <div modal-form config="data.stu_detail.formConfig"></div>
</body>
</html>