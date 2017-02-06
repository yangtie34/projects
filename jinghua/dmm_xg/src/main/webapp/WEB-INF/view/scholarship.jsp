<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String base = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base+"/";
%>
<html ng-app="app">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>奖学金</title>
<jsp:include page="/static/base.jsp"></jsp:include>
<script type="text/javascript" src="product/scholarship/controller.js"></script>
<script type="text/javascript" src="product/scholarship/service_base.js"></script>
<script type="text/javascript" src="product/scholarship/constant.js"></script>

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
    	<p>{{data.dateName}}共有<b><a href="" class="text-green" ng-click="getAllStu('all')"> {{data.abstract_data.count}} </a></b>人获得奖学金，共计<b><a href="" class="text-green" ng-click="getAllStu('all')"> {{data.abstract_data.money | number:0}} </a></b>元，覆盖率<b><span class="text-green"> {{data.abstract_data.scale}}%</spqn></b></p>
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
        	<b class="text-blue">奖学金{{data.deptData.deptMc}}分布</b>
            <div class="xuegong-zhuti-dropdown-box">
                <div class="btn-group" cg-select data="data.deptData.bzdm" on-change="changeDeptData($value,$data)" ng-model="data.deptData.id"></div>
            </div>
        </div>
        <div class="xuegong-separate-con" part-modal show-modal="data.deptShow.show">
        	<div class="img-box"><div class="img-box-only"><div echart config="data.deptData.option"></div></div></div>
        </div>
	</div>
	
	<div class="xuegong-separate-tb scholarship-fenbu">
		<div class="xuegong-separate-con">
	    	<div class="pull-left scholarship-sthdent-behavior">
	        	<p class="text-blue tit-second"><b>获得奖学金学生行为</b></p>
	        	<div class="img-box"><div class="img-box-only">
	            	<div style="height:310px;" echart config="data.behavior.radar"></div>
            	</div></div>
	        </div>
	        <div class="pull-left scholarship-coverage-rate">
	         	<p class="text-blue tit-second"><b>奖学金覆盖率</b></p>
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
											<p><b>累计覆盖率：</b>学生生涯获得奖学金的比例</p>
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
        	<b class="text-blue">奖学金{{data.coverageDept.deptMc}}累计覆盖率</b>
            <div class="xuegong-zhuti-dropdown-box">
                <div class="btn-group" cg-select data="data.coverageDept.bzdm" on-change="changeCoverageDept($value,$data)" ng-model="data.coverageDept.id"></div>
        	</div>
        </div>
        <div class="xuegong-separate-con"><div class="img-box" part-modal show-modal="data.coverageDeptShow.show">
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
    
    <table class="separate-l-r">
		<tbody>
			<tr>
				<td class="scholarship-male-female-rate">
					<div class="xuegong-zhuti-content-main-tit"><b class="text-blue">奖学金男女生占比</b></div>
				</td>
				<td class="tab-cell"></td>
				<td>
					<div class="xuegong-zhuti-content-main-tit">
                    	<b class="text-blue">奖学金排行榜</b>
                        <span class="pull-right">
                            <a href="" class="page-mg" ng-class="{'text-blue':data.top.index!=1,'text-hui':data.top.index==1}" ng-click="clickTopPage(-1)"><i class="fa fa-2x fa-angle-left"></i></a>
                    		<a href="" class="page-mg" ng-class="{'text-blue':data.top.pageCount!=data.top.index,'text-hui':data.top.pageCount==data.top.index}" ng-click="clickTopPage(1)"><i class="fa fa-2x fa-angle-right"></i></a>
                        </span>
                    </div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="xuegong-separate-con">
                        <div class="img-box">
                            <div class="img-box-only">
                            	<div echart config="data.coverageGradeSex.option"></div>	
                            </div>
                            <p class="text-center" ng-if="data.coverageGradeSex.info"><i class="fa fa-warning text-pink"></i>{{data.coverageGradeSex.info}}</p>
                        </div>
                    </div>
				</td>
				<td class="tab-cell"></td>
				<td class="scholarship-ranking-list">
					<div class="xuegong-separate-con">
                        <table class="table table-bordered xuegong-zhuti-table inside tab-no-btm">
                            <tbody>
                                <tr>
                                    <td><b>获奖人</b></td>
                                    <td><b>获奖次数</b>&nbsp;<a title="按获奖次数排序" href="" ng-class="{on:data.top.desc_column=='count'}" ng-click="clickTop('count')"><i class="fa fa-caret-down"></i></a></td>
                                    <td><b>获奖金额</b>&nbsp;<a title="按获奖金额排序" href="" ng-class="{on:data.top.desc_column=='money'}" ng-click="clickTop('money')"><i class="fa fa-caret-down"></i></a></td>
                                    <td><b>连续获奖次数</b>&nbsp;<a title="按连续获奖次数排序" href="" ng-class="{on:data.top.desc_column=='continue_count'}" ng-click="clickTop('continue_count')"><i class="fa fa-caret-down"></i></a></td>
                                </tr>
                                <tr ng-repeat="obj in data.top.list">
                                    <td>{{obj.name}}</td>
                                    <td>{{obj.count}}</td>
                                    <td>{{obj.money}}</td>
                                    <td>{{obj.continue_count}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
	            	<div ng-if="data.top.list.length==0" class="no-date ng-scope">暂无数据...</div>
                </td>
            </tr>
		</tbody>
	</table>
    
	<div class="xuegong-separate-tb scholarship-historical-change">
    	<div class="xuegong-zhuti-content-main-tit">
        	<b class="text-blue">历年奖学金变化</b>
            <div class="xuegong-zhuti-dropdown-box">
                <div class="btn-group" cg-select data="data.history.bzdm" on-change="changeHistory($value,$data)" ng-model="data.history.id"></div>
           </div>
        </div>
        <div class="xuegong-separate-con" >
        	<div class="img-box">
        		<div class="img-box-only" part-modal show-modal="data.historyShow.show"><div echart config="data.history.option"></div></div>
        	</div>
        </div>
    </div>
	
</div>
</div>
	    <div modal-form config="data.stu_detail.formConfig"></div>
</body>
</html>